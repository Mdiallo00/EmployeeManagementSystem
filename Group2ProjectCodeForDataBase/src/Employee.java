public class Employee extends Person{
    private int ID;
    private double Salary;
    private String SSN;
    private String HireDate;


    public Employee(String Fname,String Lname,String Email,int ID,double Salary,String SSN,String Hiredate){
        super(Fname,Lname,Email);
        this.ID=ID;
        this.Salary=Salary;
        this.SSN=SSN;
        this.HireDate=Hiredate;
    }

    public int getID(){
        return ID;
    }

    public double getSalary(){
        return Salary;

    }
    public void setSalary(double NewSalary){
        this.Salary=NewSalary;
    }
    public String getSSN(){
        return SSN;
    }
    public void setSSN(String NewSSN){
        this.SSN=NewSSN;
    }


    public String getHireDate(){
        return HireDate;
    }
    public void setHireDate(String NewHireDate){
        this.HireDate=NewHireDate;
    }


    @Override
    public String GetRole(String Role) {
        return "Full Time Employee";

    }
}
