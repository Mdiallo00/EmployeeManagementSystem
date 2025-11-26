import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddEmployee {

    //  MySQL setup
    private static final String URL = "jdbc:mysql://localhost:3306/group2erdb";
    private static final String USER = "root";      
    private static final String PASSWORD = "Bangster1862";

    public void addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (Fname, Lname, Email, Salary, SSN, HireDate) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getFname());
            stmt.setString(2, emp.getLname());
            stmt.setString(3, emp.getEmail());
            stmt.setDouble(4, emp.getSalary());
            stmt.setString(5, emp.SSN());         // your getter is named SSN()
            stmt.setString(6, emp.getHireDate());

            stmt.executeUpdate();
            System.out.println("Employee added to database successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 