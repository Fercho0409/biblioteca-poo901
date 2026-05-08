package vistas;

import dao.PrestamoDAO;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private Usuario usuarioActual;
    private PrestamoDAO prestamoDAO = new PrestamoDAO();

    public MenuPrincipal(Usuario u) {
        this.usuarioActual = u;
        setTitle("Biblioteca - Bienvenido " + u.getNombre());
        setSize(500, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JLabel lblBienvenida = new JLabel("Bienvenido: " + u.getNombre() + " " + u.getApellido());
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnDocumentos  = new JButton("Gestionar Documentos");
        JButton btnUsuarios    = new JButton("Gestionar Usuarios");
        JButton btnPrestamos   = new JButton("Gestionar Préstamos");
        JButton btnMora        = new JButton("Calcular Mora");
        JButton btnSalir       = new JButton("Salir");

        panel.add(lblBienvenida);
        panel.add(btnDocumentos);
        panel.add(btnPrestamos);
        panel.add(btnMora);

        if (u.getTipoUsuario().equals("ADMINISTRADOR")) {
            panel.add(btnUsuarios);
        }

        panel.add(btnSalir);
        add(panel);

        btnDocumentos.addActionListener(e -> new DocumentosForm(usuarioActual).setVisible(true));
        btnUsuarios.addActionListener(e -> new UsuariosForm(usuarioActual).setVisible(true));
        btnPrestamos.addActionListener(e -> new PrestamosForm(usuarioActual).setVisible(true));

        btnMora.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea calcular y actualizar la mora de todos los préstamos vencidos?",
                "Calcular Mora", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                prestamoDAO.actualizarMoras(0.25);
                JOptionPane.showMessageDialog(this,
                    "Mora calculada exitosamente.\nSe actualizaron todos los préstamos vencidos.",
                    "Mora actualizada", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnSalir.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });
    }
}