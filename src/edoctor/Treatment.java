package edoctor;

public class Treatment {
    private int diseaseId;
    private String medication;
    private String immediateRelief;
    private String precautions;
    
    public Treatment(int diseaseId, String medication, String immediateRelief, String precautions) {
        this.diseaseId = diseaseId;
        this.medication = medication;
        this.immediateRelief = immediateRelief;
        this.precautions = precautions;
    }
    
    // Getters
    public int getDiseaseId() { return diseaseId; }
    public String getMedication() { return medication; }
    public String getImmediateRelief() { return immediateRelief; }
    public String getPrecautions() { return precautions; }
}