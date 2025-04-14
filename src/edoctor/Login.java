package edoctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {
    private JTextField nidField;
    private JButton loginButton;

    public Login() {
        setTitle("E-Doctor Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel setup
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 250, 255));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Enter Your NID Card Number", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        nidField = new JTextField();
        nidField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(nidField, gbc);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nid = nidField.getText().trim();
                if (nid.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "Please enter your NID Card Number.", "Input Required", JOptionPane.WARNING_MESSAGE);
                } else {
                    // You can validate against DB or predefined list here
                    dispose();
                    new EDoctorUI().setVisible(true);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {}
            new Login().setVisible(true);
        });
    }
}
