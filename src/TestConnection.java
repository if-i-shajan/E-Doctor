public class TestConnection {
    public static void main(String[] args) {
        if (DatabaseConnection.getConnection() != null) {
            System.out.println("Database Connection Successful!");
        } else {
            System.out.println("Database Connection Failed!");
        }
    }
}