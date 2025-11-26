import java.util.Scanner;

public class CreateEmployee {

    private Scanner scanner = new Scanner(System.in);

    public Employee createEmployee() {

        System.out.print("Enter First Name: ");
        String Fname = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String Lname = scanner.nextLine();

        System.out.print("Enter Email: ");
        String Email = scanner.nextLine();

        System.out.print("Enter Salary: ");
        double Salary = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter SSN (no dashes): ");
        String SSN = scanner.nextLine();

        System.out.print("Enter Hire Date (YYYY-MM-DD): ");
        String HireDate = scanner.nextLine();

        // ID auto-generated in MySQL → use 0 as placeholder
        Employee newEmployee = new Employee(Fname, Lname, Email, 0, Salary, SSN, HireDate);

        System.out.println("\nEmployee created successfully!\n");

        return newEmployee;
    }
}
