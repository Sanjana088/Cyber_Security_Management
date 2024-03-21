package Users;

public class Company extends User {
    private Program program;

    public Company(int id, String username, String password, UserType userType) {
        super(id, username, password, userType);
        // Initialize the program with default values or fetch from the database if needed
        this.program = new Program("Default Scope", "Default Rules", "Default Description", 0.0);
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public void viewCompanyPages() {
        System.out.println("Accessing " + getUsername() + "'s pages");
    }

    public void sampleCompanyFunctionality() {
        System.out.println("Sample functionality for the Company class");
    }
}
