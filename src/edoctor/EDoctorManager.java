package edoctor;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EDoctorManager {
    // Get all symptoms from database
    public List<Symptom> getAllSymptoms() throws SQLException {
        List<Symptom> symptoms = new ArrayList<>();
        String query = "SELECT * FROM symptoms";
        
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                symptoms.add(new Symptom(
                    rs.getInt("symptom_id"),
                    rs.getString("symptom_name"),
                    rs.getString("description")
                ));
            }
        }
        return symptoms;
    }
    
    // Get possible diseases based on selected symptoms
    public Map<Disease, Integer> getPossibleDiseases(List<Integer> symptomIds) throws SQLException {
        Map<Disease, Integer> diseaseMatches = new HashMap<>();
        
        if (symptomIds.isEmpty()) {
            return diseaseMatches;
        }
        
        // Create a string of comma-separated symptom IDs for the SQL query
        String symptomIdList = symptomIds.toString().replace("[", "").replace("]", "");
        
        String query = "SELECT d.disease_id, d.disease_name, d.description, COUNT(ds.symptom_id) as match_count " +
                      "FROM diseases d JOIN disease_symptoms ds ON d.disease_id = ds.disease_id " +
                      "WHERE ds.symptom_id IN (" + symptomIdList + ") " +
                      "GROUP BY d.disease_id, d.disease_name, d.description " +
                      "ORDER BY match_count DESC";
        
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Disease disease = new Disease(
                    rs.getInt("disease_id"),
                    rs.getString("disease_name"),
                    rs.getString("description")
                );
                diseaseMatches.put(disease, rs.getInt("match_count"));
            }
        }
        return diseaseMatches;
    }
    
    // Get treatment for a specific disease
    public Treatment getTreatment(int diseaseId) throws SQLException {
        String query = "SELECT * FROM treatments WHERE disease_id = ?";
        
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, diseaseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Treatment(
                        rs.getInt("disease_id"),
                        rs.getString("medication"),
                        rs.getString("immediate_relief"),
                        rs.getString("precautions")
                    );
                }
            }
        }
        return null;
    }
    
    // Get all symptoms for a specific disease (for diagnosis confirmation)
    public List<Symptom> getSymptomsForDisease(int diseaseId) throws SQLException {
        List<Symptom> symptoms = new ArrayList<>();
        String query = "SELECT s.symptom_id, s.symptom_name, s.description " +
                       "FROM symptoms s JOIN disease_symptoms ds ON s.symptom_id = ds.symptom_id " +
                       "WHERE ds.disease_id = ?";
        
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, diseaseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    symptoms.add(new Symptom(
                        rs.getInt("symptom_id"),
                        rs.getString("symptom_name"),
                        rs.getString("description")
                    ));
}
            }
        }
        return symptoms;
    }
}