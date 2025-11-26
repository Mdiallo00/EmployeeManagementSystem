import java.net.ConnectException;
import java.sql.*;

public class EmployeeDao implements IEmployeeDao {
    private static final String URL = "jdbc:mysql://localhost:3306/group2erdb";
    private static final String user = "root";
    private static final String Password = "Bangster1862";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, user, Password);
    }

    @Override
    public void Serach_Employee(String Searchkey) {
        // Fixed SQL - removed extra commas
        String sql = "SELECT * FROM employees WHERE Fname = ? OR Lname = ? OR SSN = ? OR empid = ?";

        try (Connection conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, Searchkey);
            psmt.setString(2, Searchkey);
            psmt.setString(3, Searchkey);

            try {
                int empId = Integer.parseInt(Searchkey);
                psmt.setInt(4, empId);
            } catch (NumberFormatException e) {
                psmt.setInt(4, -1);
            }

            try (ResultSet resultSet = psmt.executeQuery()) {
                boolean foundAny = false;

                while (resultSet.next()) {
                    foundAny = true;

                    int empid = resultSet.getInt("empid");
                    String fName = resultSet.getString("Fname");
                    String lName = resultSet.getString("Lname");
                    String email = resultSet.getString("email");
                    String hireDate = resultSet.getString("HireDate");
                    double salary = resultSet.getDouble("Salary");
                    String ssn = resultSet.getString("SSN");

                    System.out.println("--------------------------------------------------");
                    System.out.println("FOUND EMPLOYEE: " + fName + " " + lName);
                    System.out.println("   ID: " + empid + " | SSN: " + ssn);
                    System.out.println("   Email: " + email);
                    System.out.println("   Hired: " + hireDate);
                    System.out.printf("   Salary: $%.2f%n", salary);
                    System.out.println("--------------------------------------------------");
                }

                if (!foundAny) {
                    System.out.println("No records found Try Again ");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Update_Employee(Employee emp) {
        String SQL = "UPDATE employees SET Fname=?, Lname=?, email=?, Salary=?, SSN=? WHERE empid=?";
        try (Connection conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(SQL)) {

            psmt.setString(1, emp.getFname());
            psmt.setString(2, emp.getLname());
            psmt.setString(3, emp.getEmail());
            psmt.setDouble(4, emp.getSalary());
            psmt.setString(5, emp.getSSN());
            psmt.setInt(6, emp.getID());

            int rowsUpdated = psmt.executeUpdate();
            System.out.println(rowsUpdated + " employee(s) updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void applySalaryRaise(double percentage, double lowerBound, double upperBound) {
        String sql = "UPDATE employees "
                + "SET Salary = Salary + (Salary * (? / 100)) "
                + "WHERE Salary >= ? AND Salary < ?";

        try (Connection conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setDouble(1, percentage);
            psmt.setDouble(2, lowerBound);
            psmt.setDouble(3, upperBound);

            int affected = psmt.executeUpdate();

            System.out.println("Salary raise applied successfully!");
            System.out.println("Employees updated: " + affected);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee getEmployeeById(int id) {
        String SQL = "SELECT * FROM employees WHERE empid = ?";

        try (Connection conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(SQL)) {

            psmt.setInt(1, id);
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                return new Employee(
                        rs.getString("Fname"),
                        rs.getString("Lname"),
                        rs.getString("email"),
                        rs.getInt("empid"),
                        rs.getDouble("Salary"),
                        rs.getString("SSN"),
                        rs.getString("HireDate")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void PrintOutEmployeeTable() {
        String SQL = "SELECT * FROM employees";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("==================================================================");
            System.out.println("                           ALL EMPLOYEES");
            System.out.println("==================================================================");

            boolean hasEmployees = false;

            while (resultSet.next()) {
                hasEmployees = true;

                int empid = resultSet.getInt("empid");
                String fName = resultSet.getString("Fname");
                String lName = resultSet.getString("Lname");
                String email = resultSet.getString("email");
                String hireDate = resultSet.getString("HireDate");
                double salary = resultSet.getDouble("Salary");
                String ssn = resultSet.getString("SSN");

                System.out.printf("ID: %-5d | Name: %-15s %-15s%n", empid, fName, lName);
                System.out.printf("SSN: %-11s | Email: %-25s%n", ssn, email);
                System.out.printf("Hire Date: %-12s | Salary: $%-10.2f%n", hireDate, salary);
                System.out.println("------------------------------------------------------------------");
            }

            if (!hasEmployees) {
                System.out.println("No employees found in the database.");
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void printEmployeeHistoryReport(int targetId) {
        System.out.println("\n--- EMPLOYEE PAY HISTORY REPORT ---");

        String sql = "SELECT " +
                "  e.Fname, e.Lname, e.SSN, e.HireDate, " +
                "  p.pay_date, p.earnings, p.fed_tax, p.state_tax " +
                "FROM " +
                "  employees e " +
                "JOIN " +
                "  payroll p ON e.empid = p.empid " +
                "WHERE " +
                "  e.empid = ? " +
                "ORDER BY " +
                "  p.pay_date DESC";

        int lastPrintedId = -1;

        try (Connection conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, targetId);

            try (ResultSet rs = psmt.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    int currentId = targetId;

                    if (currentId != lastPrintedId) {
                        String fName = rs.getString("Fname");
                        String lName = rs.getString("Lname");
                        String ssn = rs.getString("SSN");
                        String hireDate = rs.getString("HireDate");

                        System.out.println("==========================================================");
                        System.out.println("Employee: " + fName + " " + lName + " (ID: " + currentId + ")");
                        System.out.println("SSN: " + ssn + " | Hired: " + hireDate);
                        System.out.println("Status: Full Time Employee (Per System Constraint)");
                        System.out.println("----------------------------------------------------------");
                        System.out.printf("%-15s | %-12s | %-12s | %-12s%n",
                                "PAY DATE", "EARNINGS", "FED TAX", "STATE TAX");
                        System.out.println("----------------------------------------------------------");

                        lastPrintedId = currentId;
                    }

                    String payDate = rs.getString("pay_date");
                    double earnings = rs.getDouble("earnings");
                    double fedTax = rs.getDouble("fed_tax");
                    double stateTax = rs.getDouble("state_tax");

                    System.out.printf("%-15s | $%,-11.2f | $%,-11.2f | $%,-11.2f%n",
                            payDate, earnings, fedTax, stateTax);
                }

                if (!found) {
                    System.out.println("No employee found with ID " + targetId + " or no payroll history exists.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void printJobTitleReport() {
        System.out.println("\n--- REPORT: TOTAL PAY BY JOB TITLE ---");

        String sql = "SELECT jt.job_title, SUM(p.earnings) AS TotalPay FROM job_titles jt JOIN employee_job_titles ejt ON jt.job_title_id = ejt.job_title_id JOIN employees e ON ejt.empid = e.empid JOIN payroll p ON e.empid = p.empid GROUP BY jt.job_title";

        try (Connection conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            System.out.printf("%-30s | %-15s%n", "JOB TITLE", "TOTAL PAY");
            System.out.println("------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                String jobTitle = rs.getString("job_title");
                double total = rs.getDouble("TotalPay");

                System.out.printf("%-30s | $%,.2f%n", jobTitle, total);
            }

            if (!found) {
                System.out.println("No payroll data found for any job titles.");
            }

        } catch (SQLException e) {
            System.out.println("Database Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void printDivisionReport() {
        System.out.println("\n--- REPORT: TOTAL PAY BY DIVISION ---");

        String sql = "SELECT d.Name, SUM(p.earnings) AS TotalPay FROM division d JOIN employee_division ed ON d.ID = ed.div_ID JOIN employees e ON ed.empid = e.empid JOIN payroll p ON e.empid = p.empid GROUP BY d.Name ORDER BY TotalPay DESC";

        try (Connection conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            System.out.printf("%-20s | %-15s%n", "DIVISION", "TOTAL PAY");
            System.out.println("----------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                String divName = rs.getString("Name");
                double total = rs.getDouble("TotalPay");

                System.out.printf("%-20s | $%,.2f%n", divName, total);
            }

            if (!found) {
                System.out.println("No payroll data found to generate division report.");
            }

        } catch (SQLException e) {
            System.out.println("Database Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (Fname, Lname, Email, Salary, SSN, HireDate) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, emp.getFname());
            psmt.setString(2, emp.getLname());
            psmt.setString(3, emp.getEmail());
            psmt.setDouble(4, emp.getSalary());
            psmt.setString(5, emp.getSSN());
            psmt.setString(6, emp.getHireDate());

            psmt.executeUpdate();
            System.out.println("Employee added to database successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}