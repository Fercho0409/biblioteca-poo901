package com.mycompany.bibliotecadb;

import vistas.LoginForm;
import javax.swing.SwingUtilities;

public class BibliotecaDB {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}