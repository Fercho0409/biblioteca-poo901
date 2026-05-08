package vistas;

import dao.UsuarioDAO;
import modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuariosForm extends JFrame {

    private UsuarioDAO dao = new UsuarioDAO();
    private JTable tabla;
    private DefaultTableModel modelo;

    public UsuariosForm(Usuario u) {
        setTitle("Gestionar Usuarios");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelNorte = new JPanel(new FlowLayout());
        JButton btnAgregar     = new JButton("Agregar");
        JButton btnRestablecer = new JButton("Restablecer Contraseña");
        JButton btnRefrescar   = new JButton("Refrescar");
        panelNorte.add(btnAgregar);
        panelNorte.add(btnRestablecer);
        panelNorte.add(btnRefrescar);

        modelo = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Apellido", "Email", "Tipo", "Activo"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.getColumnModel().getColumn(0).setMaxWidth(50);
        JScrollPane scroll = new JScrollPane(tabla);

        add(panelNorte, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> agregar());
        btnRestablecer.addActionListener(e -> restablecer());
        btnRefrescar.addActionListener(e -> cargarTabla());

        cargarTabla();
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Usuario> lista = dao.listarTodos();
        for (Usuario u : lista) {
            modelo.addRow(new Object[]{
                u.getIdUsuario(), u.getNombre(), u.getApellido(),
                u.getEmail(), u.getTipoUsuario(), u.isActivo() ? "Si" : "No"
            });
        }
    }

    private void agregar() {
        String nombre   = JOptionPane.showInputDialog(this, "Nombre:");
        if (nombre == null || nombre.trim().isEmpty()) return;
        String apellido = JOptionPane.showInputDialog(this, "Apellido:");
        if (apellido == null || apellido.trim().isEmpty()) return;
        String email    = JOptionPane.showInputDialog(this, "Email:");
        if (email == null || email.trim().isEmpty()) return;
        String password = JOptionPane.showInputDialog(this, "Contraseña:");
        if (password == null || password.trim().isEmpty()) return;

        String[] tipos = {"ALUMNO", "PROFESOR", "ADMINISTRADOR"};
        String tipo = (String) JOptionPane.showInputDialog(this,
            "Tipo de usuario:", "Tipo", JOptionPane.QUESTION_MESSAGE,
            null, tipos, tipos[0]);
        if (tipo == null) return;

        Usuario u = new Usuario(0, nombre, apellido, email, password, tipo, true);
        if (dao.insertar(u)) {
            JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente.");
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void restablecer() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario de la tabla.");
            return;
        }
        int id = (int) modelo.getValueAt(fila, 0);
        String nueva = JOptionPane.showInputDialog(this, "Nueva contraseña:");
        if (nueva == null || nueva.trim().isEmpty()) return;

        if (dao.restablecerPassword(id, nueva)) {
            JOptionPane.showMessageDialog(this, "Contraseña restablecida exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al restablecer contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}