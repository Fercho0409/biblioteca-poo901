package vistas;

import dao.DocumentoDAO;
import dao.PrestamoDAO;
import dao.UsuarioDAO;
import modelo.Prestamo;
import modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PrestamosForm extends JFrame {

    private PrestamoDAO prestamoDAO   = new PrestamoDAO();
    private UsuarioDAO  usuarioDAO    = new UsuarioDAO();
    private DocumentoDAO documentoDAO = new DocumentoDAO();
    private Usuario usuarioActual;
    private JTable tabla;
    private DefaultTableModel modelo;

    public PrestamosForm(Usuario u) {
        this.usuarioActual = u;
        setTitle("Gestionar Préstamos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelNorte = new JPanel(new FlowLayout());
        JButton btnNuevo    = new JButton("Nuevo Préstamo");
        JButton btnDevolver = new JButton("Registrar Devolución");
        JButton btnRefrescar = new JButton("Refrescar");
        panelNorte.add(btnNuevo);
        panelNorte.add(btnDevolver);
        panelNorte.add(btnRefrescar);

        modelo = new DefaultTableModel(
            new String[]{"ID", "Usuario", "Código", "Título", "Fecha Préstamo", "Fecha Límite", "Mora", "Estado"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.getColumnModel().getColumn(0).setMaxWidth(50);
        JScrollPane scroll = new JScrollPane(tabla);

        add(panelNorte, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnNuevo.addActionListener(e -> nuevoPrestamo());
        btnDevolver.addActionListener(e -> devolver());
        btnRefrescar.addActionListener(e -> cargarTabla());

        cargarTabla();
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Prestamo> lista = prestamoDAO.listarActivos();
        for (Prestamo p : lista) {
            modelo.addRow(new Object[]{
                p.getIdPrestamo(), p.getNombreUsuario(),
                p.getCodigoInterno(), p.getTituloDocumento(),
                p.getFechaPrestamo(), p.getFechaLimite(),
                "$" + p.getMoraAcumulada(), p.getEstado()
            });
        }
    }

    private void nuevoPrestamo() {
        String emailUsuario = JOptionPane.showInputDialog(this, "Email del usuario:");
        if (emailUsuario == null || emailUsuario.trim().isEmpty()) return;

        Usuario u = usuarioDAO.login(emailUsuario, "");
        // Buscar por email directamente
        List<Usuario> todos = usuarioDAO.listarTodos();
        Usuario destinatario = null;
        for (Usuario us : todos) {
            if (us.getEmail().equalsIgnoreCase(emailUsuario.trim())) {
                destinatario = us;
                break;
            }
        }

        if (destinatario == null) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuarioDAO.tieneMora(destinatario.getIdUsuario())) {
            JOptionPane.showMessageDialog(this, "El usuario tiene mora pendiente. No puede realizar préstamos.",
                "Mora pendiente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maxPrestamos = destinatario.getTipoUsuario().equals("PROFESOR") ? 6 : 3;
        if (prestamoDAO.contarPrestamosActivos(destinatario.getIdUsuario()) >= maxPrestamos) {
            JOptionPane.showMessageDialog(this, "El usuario alcanzó el límite de préstamos (" + maxPrestamos + ").",
                "Límite alcanzado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = JOptionPane.showInputDialog(this, "Código del documento (ej: LIB00001):");
        if (codigo == null || codigo.trim().isEmpty()) return;

        int idDoc = documentoDAO.buscarIdPorCodigo(codigo.trim());
        if (idDoc == -1) {
            JOptionPane.showMessageDialog(this, "Documento no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!documentoDAO.hayDisponibilidad(idDoc)) {
            JOptionPane.showMessageDialog(this, "No hay unidades disponibles.", "Sin disponibilidad", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int diasPrestamo = destinatario.getTipoUsuario().equals("PROFESOR") ? 15 : 7;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, diasPrestamo);
        Date fechaLimite = cal.getTime();

        Prestamo p = new Prestamo();
        p.setIdUsuario(destinatario.getIdUsuario());
        p.setIdDocumento(idDoc);
        p.setFechaLimite(fechaLimite);

        if (prestamoDAO.insertar(p)) {
            documentoDAO.actualizarDisponibilidad(idDoc, -1);
            JOptionPane.showMessageDialog(this, "Préstamo registrado exitosamente.\nFecha límite: " + fechaLimite);
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void devolver() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un préstamo de la tabla.");
            return;
        }
        int idPrestamo  = (int) modelo.getValueAt(fila, 0);
        int idDocumento = prestamoDAO.listarActivos().get(fila).getIdDocumento();

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Confirmar devolución?", "Devolución", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        if (prestamoDAO.devolver(idPrestamo)) {
            documentoDAO.actualizarDisponibilidad(idDocumento, 1);
            JOptionPane.showMessageDialog(this, "Devolución registrada exitosamente.");
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar devolución.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}