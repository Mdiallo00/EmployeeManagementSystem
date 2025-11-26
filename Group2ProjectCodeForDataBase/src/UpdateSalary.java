import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdateSalary implements IEmployeeDao {

    private static final String URL = "jdbc:mysql://localhost:3306/group2erdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Bangster1862";

    @Override
    public void applySalaryRaise(double percentage, double lowerBound, double upperBound) {

        String sql = "UPDATE employees "
                   + "SET Salary = Salary + (Salary * (? / 100)) "
                   + "WHERE Salary >= ? AND Salary < ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, percentage);     // percentage increase
            stmt.setDouble(2, lowerBound);     
            stmt.setDouble(3, upperBound);     

            int affected = stmt.executeUpdate();

            System.out.println("Salary raise applied successfully!");
            System.out.println("Employees updated: " + affected);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
