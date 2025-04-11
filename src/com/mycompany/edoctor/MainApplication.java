package com.mycompany.edoctor;

import javax.swing.*;
import java.awt.*;

public class MainApplication extends javax.swing.JFrame {
    private String username;

    public MainApplication(String username) {
        this.username = username;
        initComponents();
        setTitle("E-Doctor - Welcome " + username);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window

        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to E-Doctor, " + username);
        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);

        // Add menu bar (optional)
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> {
            new LogInPage().setVisible(true);
            this.dispose();
        });
        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        add(mainPanel);
    }

    public static void main(String args[]) {
        // For testing without login
        SwingUtilities.invokeLater(() -> {
            new MainApplication("TestUser").setVisible(true);
        });
    }
}