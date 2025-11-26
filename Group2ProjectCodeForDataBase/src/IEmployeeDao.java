public interface IEmployeeDao {

    void Serach_Employee(String fname);

     void Update_Employee(Employee emp);


     void applySalaryRaise(double percentage, double lowerBound, double upperBound);

    public void printDivisionReport();

    public void addEmployee(Employee emp);

    public void printEmployeeHistoryReport(int targetId) ;


    public void printJobTitleReport();



        Employee getEmployeeById(int id);
}
