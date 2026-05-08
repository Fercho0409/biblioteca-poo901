package vistas;

import dao.DocumentoDAO;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DocumentosForm extends JFrame {

    private DocumentoDAO dao = new DocumentoDAO();
    private JTable tabla;
    private DefaultTableModel modelo;
    private JComboBox<String> cmbTipo;

    public DocumentosForm(Usuario u) {
        setTitle("Gestionar Documentos");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelNorte = new JPanel(new FlowLayout());
        panelNorte.add(new JLabel("Tipo:"));
        cmbTipo = new JComboBox<>(new String[]{"Libros", "Revistas", "CDs de Audio", "DVDs"});
        panelNorte.add(cmbTipo);

        JButton btnVer      = new JButton("Ver");
        JButton btnAgregar  = new JButton("Agregar");
        panelNorte.add(btnVer);
        panelNorte.add(btnAgregar);

        modelo = new DefaultTableModel();
        tabla  = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        add(panelNorte, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnVer.addActionListener(e -> cargarTabla());
        btnAgregar.addActionListener(e -> agregar());

        cargarTabla();
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        modelo.setColumnCount(0);
        int tipo = cmbTipo.getSelectedIndex();

        if (tipo == 0) {
            modelo.addColumn("Código"); modelo.addColumn("Título");
            modelo.addColumn("Autor"); modelo.addColumn("Editorial");
            modelo.addColumn("ISBN"); modelo.addColumn("Disponibles");
            List<Libro> lista = dao.listarLibros();
            for (Libro l : lista) {
                modelo.addRow(new Object[]{l.getCodigoInterno(), l.getTitulo(),
                    l.getAutor(), l.getEditorial(), l.getIsbn(), l.getUnidadesDisponibles()});
            }
        } else if (tipo == 1) {
            modelo.addColumn("Código"); modelo.addColumn("Título");
            modelo.addColumn("Editorial"); modelo.addColumn("Periodicidad");
            modelo.addColumn("Disponibles");
            List<Revista> lista = dao.listarRevistas();
            for (Revista r : lista) {
                modelo.addRow(new Object[]{r.getCodigoInterno(), r.getTitulo(),
                    r.getEditorial(), r.getPeriodicidad(), r.getUnidadesDisponibles()});
            }
        } else if (tipo == 2) {
            modelo.addColumn("Código"); modelo.addColumn("Título");
            modelo.addColumn("Artista"); modelo.addColumn("Género");
            modelo.addColumn("Canciones"); modelo.addColumn("Disponibles");
            List<CdAudio> lista = dao.listarCds();
            for (CdAudio c : lista) {
                modelo.addRow(new Object[]{c.getCodigoInterno(), c.getTitulo(),
                    c.getArtista(), c.getGenero(), c.getNumCanciones(), c.getUnidadesDisponibles()});
            }
        } else {
            modelo.addColumn("Código"); modelo.addColumn("Título");
            modelo.addColumn("Director"); modelo.addColumn("Género");
            modelo.addColumn("Duración"); modelo.addColumn("Disponibles");
            List<Dvd> lista = dao.listarDvds();
            for (Dvd d : lista) {
                modelo.addRow(new Object[]{d.getCodigoInterno(), d.getTitulo(),
                    d.getDirector(), d.getGenero(), d.getDuracionMin(), d.getUnidadesDisponibles()});
            }
        }
    }

    private void agregar() {
        int tipo = cmbTipo.getSelectedIndex();
        if (tipo == 0) agregarLibro();
        else if (tipo == 1) agregarRevista();
        else if (tipo == 2) agregarCd();
        else agregarDvd();
    }

    private void agregarLibro() {
        String titulo    = JOptionPane.showInputDialog(this, "Título:");
        if (titulo == null || titulo.trim().isEmpty()) return;
        String autor     = JOptionPane.showInputDialog(this, "Autor:");
        if (autor == null || autor.trim().isEmpty()) return;
        String editorial = JOptionPane.showInputDialog(this, "Editorial:");
        String isbn      = JOptionPane.showInputDialog(this, "ISBN:");
        String pags      = JOptionPane.showInputDialog(this, "Número de páginas:");
        String anio      = JOptionPane.showInputDialog(this, "Año de publicación:");
        String ubicacion = JOptionPane.showInputDialog(this, "Ubicación física:");
        String unidades  = JOptionPane.showInputDialog(this, "Unidades disponibles:");

        try {
            String codigo = dao.generarCodigo("LIB", 1);
            Libro l = new Libro(0, codigo, titulo, ubicacion,
                Integer.parseInt(unidades), Integer.parseInt(unidades),
                true, editorial, autor, isbn,
                Integer.parseInt(pags), Integer.parseInt(anio));
            if (dao.insertarLibro(l)) {
                JOptionPane.showMessageDialog(this, "Libro agregado exitosamente.");
                cargarTabla();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingresa valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarRevista() {
        String titulo      = JOptionPane.showInputDialog(this, "Título:");
        if (titulo == null || titulo.trim().isEmpty()) return;
        String editorial   = JOptionPane.showInputDialog(this, "Editorial:");
        String periodicidad = JOptionPane.showInputDialog(this, "Periodicidad:");
        String ubicacion   = JOptionPane.showInputDialog(this, "Ubicación física:");
        String unidades    = JOptionPane.showInputDialog(this, "Unidades disponibles:");

        try {
            String codigo = dao.generarCodigo("REV", 2);
            Revista r = new Revista(0, codigo, titulo, ubicacion,
                Integer.parseInt(unidades), Integer.parseInt(unidades),
                true, editorial, periodicidad, null);
            if (dao.insertarRevista(r)) {
                JOptionPane.showMessageDialog(this, "Revista agregada exitosamente.");
                cargarTabla();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingresa valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarCd() {
        String titulo    = JOptionPane.showInputDialog(this, "Título:");
        if (titulo == null || titulo.trim().isEmpty()) return;
        String artista   = JOptionPane.showInputDialog(this, "Artista:");
        String genero    = JOptionPane.showInputDialog(this, "Género:");
        String canciones = JOptionPane.showInputDialog(this, "Número de canciones:");
        String duracion  = JOptionPane.showInputDialog(this, "Duración (minutos):");
        String ubicacion = JOptionPane.showInputDialog(this, "Ubicación física:");
        String unidades  = JOptionPane.showInputDialog(this, "Unidades disponibles:");

        try {
            String codigo = dao.generarCodigo("CDA", 3);
            CdAudio cd = new CdAudio(0, codigo, titulo, ubicacion,
                Integer.parseInt(unidades), Integer.parseInt(unidades),
                true, Integer.parseInt(duracion), genero, artista,
                Integer.parseInt(canciones));
            if (dao.insertarCd(cd)) {
                JOptionPane.showMessageDialog(this, "CD agregado exitosamente.");
                cargarTabla();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingresa valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarDvd() {
        String titulo    = JOptionPane.showInputDialog(this, "Título:");
        if (titulo == null || titulo.trim().isEmpty()) return;
        String director  = JOptionPane.showInputDialog(this, "Director:");
        String genero    = JOptionPane.showInputDialog(this, "Género:");
        String duracion  = JOptionPane.showInputDialog(this, "Duración (minutos):");
        String ubicacion = JOptionPane.showInputDialog(this, "Ubicación física:");
        String unidades  = JOptionPane.showInputDialog(this, "Unidades disponibles:");

        try {
            String codigo = dao.generarCodigo("DVD", 4);
            Dvd dvd = new Dvd(0, codigo, titulo, ubicacion,
                Integer.parseInt(unidades), Integer.parseInt(unidades),
                true, Integer.parseInt(duracion), genero, director);
            if (dao.insertarDvd(dvd)) {
                JOptionPane.showMessageDialog(this, "DVD agregado exitosamente.");
                cargarTabla();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingresa valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}