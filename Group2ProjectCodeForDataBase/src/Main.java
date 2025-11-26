import java.util.Scanner;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        EmployeeDao Dao = new EmployeeDao();
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        System.out.println("=== EMPLOYEE SYSTEM ===");

        while (choice != 0) {
            System.out.println("\n-----------------------------------------");
            System.out.println("1. Search Employee (Name, ID, or SSN) [Req 2]");
            System.out.println("2. Update Employee Info [Req 3]");
            System.out.println("3. Apply 3.2% Salary Raise [Req 4]");
            System.out.println("4. Add New Employee [Deliverable Item 2]");
            System.out.println("5. Report: Total Pay by Division [Report 3]");
            System.out.println("6. Report: Total Pay by Job Title [Report 2]");
            System.out.println("7. Report: Employee History [Report 1]");
            System.out.println("8. Print All Employees");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.println();
                        System.out.println("Employee Directory please input One of These Name,SSN,ID");
                        String Searchkey = scanner.nextLine();
                        Dao.Serach_Employee(Searchkey);
                        break;

                    case 2: // Update Employee Info
                        System.out.print("Enter Employee ID to Update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();

                        Employee empToUpdate = Dao.getEmployeeById(updateId);

                        if (empToUpdate == null) {
                            System.out.println("❌ Employee not found. You must Create a new Employee First");
                        } else {
                            System.out.println("Editing: " + empToUpdate.getFname() + " " + empToUpdate.getLname());

                            boolean updating = true;

                            while (updating) {
                                int subChoice = getUpdateChoice(scanner);

                                switch (subChoice) {
                                    case 1:
                                        System.out.print("Enter New First Name: ");
                                        empToUpdate.SetFname(scanner.nextLine());
                                        break;
                                    case 2:
                                        System.out.print("Enter New Last Name: ");
                                        empToUpdate.setLname(scanner.nextLine());
                                        break;
                                    case 3:
                                        System.out.print("Enter New Email: ");
                                        empToUpdate.setEmail(scanner.nextLine());
                                        break;
                                    case 4:
                                        System.out.print("Enter New Hire Date (YYYY-MM-DD): ");
                                        empToUpdate.setHireDate(scanner.nextLine());
                                        break;
                                    case 5:
                                        System.out.print("Enter New Salary: ");
                                        empToUpdate.setSalary(scanner.nextDouble());
                                        scanner.nextLine();
                                        break;
                                    case 6:
                                        System.out.print("Enter New SSN: ");
                                        empToUpdate.setSSN(scanner.nextLine());
                                        break;
                                    case 0:
                                        updating = false;
                                        break;
                                    default:
                                        System.out.println("Invalid selection.");
                                }

                                if (subChoice != 0) {
                                    Dao.Update_Employee(empToUpdate);
                                    System.out.println("✅ Change saved!");
                                }
                            }
                        }
                        break;

                    case 3:
                        System.out.println("How much do you want to increase the Salary of Employee Give LowerBound and UpperBound. (Do not do the math just give me the Percentage ex: 10% you would just enter 10)");
                        System.out.println("Percentage");
                        double percentage = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.println("LowerBound");
                        double LowerBound=scanner.nextDouble();
                        scanner.nextLine();
                        System.out.println("UpperBound");
                        double UpperBound=scanner.nextDouble();
                        Dao.applySalaryRaise(percentage, LowerBound, UpperBound);
                        break;

                    case 4:
                        addEmployeeUI(scanner, Dao);
                        break;

                    case 5:
                        System.out.println("Report: Total Pay By Division");
                        Dao.printDivisionReport();
                        break;

                    case 6:
                        System.out.println("Report: Total Pay By Job Title");
                        Dao.printJobTitleReport();
                        break;

                    case 7:
                        System.out.print("Enter Employee ID to view history: ");
                        int historyId = scanner.nextInt();
                        scanner.nextLine();
                        Dao.printEmployeeHistoryReport(historyId);
                        break;

                    case 8:
                        Dao.PrintOutEmployeeTable();
                        break;

                    case 0:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        scanner.close();
    }

    public static int getUpdateChoice(Scanner scanner) {
        System.out.println("\n--- UPDATE PORTAL ---");
        System.out.println("1. Update First Name");
        System.out.println("2. Update Last Name");
        System.out.println("3. Update Email");
        System.out.println("4. Update Hire Date");
        System.out.println("5. Update Salary");
        System.out.println("6. Update SSN");
        System.out.println("0. Finish Updating");
        System.out.print("Select field to update: ");

        int subChoice = scanner.nextInt();
        scanner.nextLine();
        return subChoice;
    }

    public static void addEmployeeUI(Scanner scanner, EmployeeDao dao) {
        System.out.println("\n--- ADD NEW EMPLOYEE ---");

        System.out.print("Enter First Name: ");
        String fname = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lname = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter SSN: ");
        String ssn = scanner.nextLine();

        System.out.print("Enter Hire Date (YYYY-MM-DD): ");
        String hireDate = scanner.nextLine();

        Employee newEmployee = new Employee(fname, lname, email, 0, salary, ssn, hireDate);

        dao.addEmployee(newEmployee);
        System.out.println(" Employee added successfully!");
    }
}