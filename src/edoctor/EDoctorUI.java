package edoctor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EDoctorUI extends JFrame {
    private JPanel mainPanel;
    private JCheckBox[] symptomCheckboxes;
    private JButton diagnoseButton;
    
    // Sample data - in a real app, this would come from a database
    private String[] symptoms = {
        "Fever", "Headache", "Cough", "Sore Throat", "Runny Nose",
        "Fatigue", "Muscle Aches", "Nausea", "Diarrhea", "Shortness of Breath"
    };
    
    private String[] diseases = {
        "Common Cold", "Flu", "Allergies", "Strep Throat", "Food Poisoning"
    };
    
    private String[][] diseaseSymptoms = {
        {"Fever", "Cough", "Runny Nose", "Sore Throat"},          // Common Cold
        {"Fever", "Headache", "Muscle Aches", "Fatigue"},         // Flu
        {"Runny Nose", "Sore Throat"},                            // Allergies
        {"Sore Throat", "Fever", "Headache"},                     // Strep Throat
        {"Nausea", "Diarrhea", "Fatigue"}                         // Food Poisoning
    };
    
    private String[] treatments = {
        "Rest, drink fluids, take acetaminophen",                 // Common Cold
        "Rest, fluids, antiviral medication if early",            // Flu
        "Antihistamines, avoid allergens",                        // Allergies
        "Antibiotics prescribed by doctor",                       // Strep Throat
        "Stay hydrated, bland diet, rest"                         // Food Poisoning
    };

    public EDoctorUI() {
        setTitle("Simple E-Doctor");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set application icon
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/edoctor/medical-icon.png")));
        } catch (Exception e) {
            // Icon not found - continue without it
        }
        
        createUI();
    }
    
    private void createUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // AliceBlue background
        
        // Title
        JLabel titleLabel = new JLabel("Select your symptoms:", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 204)); // Dark blue text
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Symptoms panel
        JPanel symptomsPanel = new JPanel();
        symptomsPanel.setLayout(new GridLayout(0, 2));
        symptomsPanel.setBackground(new Color(240, 248, 255)); // AliceBlue background
        symptomsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        symptomCheckboxes = new JCheckBox[symptoms.length];
        for (int i = 0; i < symptoms.length; i++) {
            symptomCheckboxes[i] = new JCheckBox(symptoms[i]);
            symptomCheckboxes[i].setFont(new Font("Arial", Font.PLAIN, 14));
            symptomCheckboxes[i].setBackground(new Color(240, 248, 255)); // AliceBlue background
            symptomCheckboxes[i].setForeground(new Color(0, 51, 102)); // Dark blue text
            symptomsPanel.add(symptomCheckboxes[i]);
        }
        
        JScrollPane scrollPane = new JScrollPane(symptomsPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 255), 2));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Diagnose button
        diagnoseButton = new JButton("Diagnose");
        diagnoseButton.setFont(new Font("Arial", Font.BOLD, 14));
        diagnoseButton.setBackground(new Color(0, 102, 204)); // Dark blue background
        diagnoseButton.setForeground(Color.BLACK);
        diagnoseButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        diagnoseButton.setFocusPainted(false);
        diagnoseButton.addActionListener(this::diagnoseAction);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 248, 255)); // AliceBlue background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        buttonPanel.add(diagnoseButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void diagnoseAction(ActionEvent e) {
        // Get selected symptoms
        ArrayList<String> selectedSymptoms = new ArrayList<>();
        for (JCheckBox checkbox : symptomCheckboxes) {
            if (checkbox.isSelected()) {
                selectedSymptoms.add(checkbox.getText());
            }
        }
        
        if (selectedSymptoms.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one symptom.", 
                "No Symptoms Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Find matching diseases
        ArrayList<String> possibleDiseases = new ArrayList<>();
        ArrayList<String> possibleTreatments = new ArrayList<>();
        
        for (int i = 0; i < diseases.length; i++) {
            int matchCount = 0;
            for (String symptom : diseaseSymptoms[i]) {
                if (selectedSymptoms.contains(symptom)) {
                    matchCount++;
                }
            }
            
            // If at least half symptoms match, consider it possible
            if (matchCount >= diseaseSymptoms[i].length / 2) {
                possibleDiseases.add(diseases[i]);
                possibleTreatments.add(treatments[i]);
            }
        }
        
        if (possibleDiseases.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No matching conditions found.", 
                "No Diagnosis", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Show results
        showResults(possibleDiseases, possibleTreatments);
    }
    
    private void showResults(ArrayList<String> diseases, ArrayList<String> treatments) {
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(new Color(240, 248, 255)); // AliceBlue background
        
        // Create disease list
        JTextArea diseaseArea = new JTextArea();
        diseaseArea.setEditable(false);
        diseaseArea.setFont(new Font("Arial", Font.PLAIN, 14));
        diseaseArea.setBackground(new Color(255, 255, 240)); // Light yellow background
        diseaseArea.setForeground(new Color(0, 51, 102)); // Dark blue text
        diseaseArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        diseaseArea.append("Possible Conditions:\n\n");
        
        for (int i = 0; i < diseases.size(); i++) {
            diseaseArea.append((i+1) + ". " + diseases.get(i) + "\n");
            diseaseArea.append("   Treatment: " + treatments.get(i) + "\n\n");
        }
        
        JScrollPane scrollPane = new JScrollPane(diseaseArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 255), 2));
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Customize the option pane
        UIManager.put("OptionPane.background", new Color(240, 248, 255));
        UIManager.put("Panel.background", new Color(240, 248, 255));
        
        JOptionPane.showMessageDialog(this, resultPanel, "Diagnosis Results", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // If setting look and feel fails, continue with default
            }
            
            EDoctorUI app = new EDoctorUI();
            app.setVisible(true);
        });
    }
}