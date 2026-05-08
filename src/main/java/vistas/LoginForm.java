package vistas;

import dao.UsuarioDAO;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnEntrar;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public LoginForm() {
        setTitle("Biblioteca - Iniciar Sesión");
        setSize(380, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        btnEntrar = new JButton("Entrar");
        panel.add(new JLabel(""));
        panel.add(btnEntrar);

        add(panel);

        btnEntrar.addActionListener(e -> login());
        txtPassword.addActionListener(e -> login());
    }

    private void login() {
        String email = txtEmail.getText().trim();
        String pass  = new String(txtPassword.getPassword()).trim();

        if (email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa email y contraseña.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario u = usuarioDAO.login(email, pass);
        if (u != null) {
            new MenuPrincipal(u).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Email o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}