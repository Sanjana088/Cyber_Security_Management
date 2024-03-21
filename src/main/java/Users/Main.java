package Users;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {



        Scanner scanner = new Scanner(System.in);
        // Initialize the database
        DatabaseService.createTables();


        while (true){
            System.out.println("Select an Option");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character


            switch (choice){
                case 1:
                    AuthenticationService.registerUser(scanner);
                    break;
                case 2:
                    User user = AuthenticationService.loginUser();
                    if (user != null) {
                        handleDashboard(user, scanner);
                    }
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    private static void registerUser(Scanner scanner){
        User user = AuthenticationService.registerUser(scanner);
        if(user !=null){
            System.out.println("Register Successful");
            handleDashboard(user, scanner);
        } else {
            System.out.println("Registration Failed.");
        }
    }

    private static void handleDashboard(User user, Scanner scanner) {
        switch (user.getUserType()) {
            case HACKER:
                showHackerDashboard(user, scanner);
                break;
            case COMPANY:
                handleCompanyDashboard((Company) user, scanner);
                break;
            case INTERNAL_TEAM:
                showInternalTeamDashboard((InternalTeam) user, scanner);
                break;
            case MANAGER:
                showManagerDashboard(user, scanner);
                break;
            default:
                System.out.println("Unrecognized user type.");
                break;
        }
    }

    private static void showHackerDashboard(User user, Scanner scanner) {
        // Hacker Specific Functionalities

        // check if the suer is indedd a hacker

        if (!(user instanceof Hacker)){
            System.out.println("Invalid user type for hacker dashboard");
            return;
        }

        Hacker hacker = (Hacker) user;
        System.out.println("Welcome to the Hacker Dashboard, " + user.getUsername());

        while (true){
            System.out.println("\nHacker Dashboard Options:");
            System.out.println("1. View Submitted Reports");
          //  System.out.println("2. View Closed Reports");
           // System.out.println("2. View Hackers Ranking");
            System.out.println("2. Submit New Report");
            System.out.println("3. Logout");

            System.out.println("Enter your Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline charactere


            switch (choice){
                case 1:
                    DashboardService.showSubmittedReports(hacker);
                    break;
                /*case 2:
                    DashboardService.showclosedReports(hacker);
                    break;
                case 2:
                    DashboardService.showRanking();
                    break;*/
                case 2:
                    submitReport(hacker,scanner);
                    break;
                case 3:
                    System.out.println("Logging out.......");
                    return; // exit the dashboard loo[
                default:
                    System.out.println("Invalid Choice. Please Try again.");
                    break;
            }
        }
    }
    private static void submitReport(Hacker hacker, Scanner scanner) {
        System.out.println("Submit New Report");

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Summary: ");
        String summary = scanner.nextLine();

        System.out.print("Enter Vulnerable URL: ");
        String vulnerableUrl = scanner.nextLine();

        System.out.print("Enter Details: ");
        String details = scanner.nextLine();

        DatabaseService.submitReport(hacker.getId(), title, summary, vulnerableUrl, details);
        System.out.println("Report submitted successfully.");
    }
    private static void handleCompanyDashboard(Company company, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Company Dashboard");
            System.out.println("1. View Your Program");
            System.out.println("2. Create/Edit Your Program");
            System.out.println("4. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    viewCompanyProgram(company);
                    break;
                case 2:
                    createOrEditCompanyProgram(company, scanner);
                    break;

                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void createOrEditCompanyProgram(Company company, Scanner scanner) {
        if (DatabaseService.hasProgram(company.getId())) {
            System.out.println("Editing your existing program:");
        } else {
            System.out.println("Creating a new program:");
        }
        Program program = Program.createProgram(scanner);
        company.setProgram(program);
        DatabaseService.createOrUpdateProgram(company.getId(), program);
    }
    private static void viewCompanyProgram(Company company) {
        Program program = company.getProgram();
        if (program != null) {
            System.out.println("Your Program Details:");
            System.out.println(program);
        } else {
            System.out.println("You have not created a program yet.");
        }
    }


    private static void viewAllPrograms(Company company) {
        System.out.println("Viewing all programs");

        List<Program> programs = DatabaseService.getProgramsByCompanyId(company.getId());
        if (programs.isEmpty()) {
            System.out.println("No programs found.");
            return;
        }

        for (Program program : programs) {
            System.out.println("ID: " + program.getId() + ", Scope: " + program.getScope() +
                    ", Rules: " + program.getRules() + ", Description: " + program.getDescription() +
                    ", Rewards Amount: " + program.getRewardsAmount());
        }
    }


    private static void showManagerDashboard(User user, Scanner scanner) {
        if (!(user instanceof Manager)) {
            System.out.println("Invalid user type for manager dashboard");
            return;
        }

        Manager manager = (Manager) user;
        System.out.println("Welcome to the Manager Dashboard, " + user.getUsername());

        while (true) {
            System.out.println("\nManager Dashboard Options:");
            System.out.println("1. Remove User");
            System.out.println("2. Delete Program");
            System.out.println("3. Logout");

            System.out.print("Enter your Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    removeUser(manager, scanner);
                    break;
                case 2:
                    deleteProgram(manager, scanner);
                    break;
                case 3:
                    System.out.println("Logging out.......");
                    return; // exit the dashboard loop
                default:
                    System.out.println("Invalid Choice. Please Try again.");
                    break;
            }
        }
    }
    private static void removeUser(Manager manager, Scanner scanner) {
        // Display all users with their IDs
        List<User> users = DatabaseService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users available.");
            return;
        }

        System.out.println("Available Users:");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Username: " + user.getUsername());
        }

        System.out.print("Enter User ID to remove: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        // Assuming DatabaseService has a method to remove a user by ID
        boolean isRemoved = DatabaseService.removeUserById(userId);
        if (isRemoved) {
            System.out.println("User with ID " + userId + " removed.");
        } else {
            System.out.println("No user found with ID " + userId + ".");
        }
    }

    private static void deleteProgram(Manager manager, Scanner scanner) {
        // Display all programs with their IDs
        List<Program> programs = DatabaseService.getAllPrograms();
        if (programs.isEmpty()) {
            System.out.println("No programs available.");
            return;
        }

        System.out.println("Available Programs:");
        for (Program program : programs) {
            System.out.println("ID: " + program.getId() + ", Name: " + program.getScope());
        }

        System.out.print("Enter Program ID to delete: ");
        int programId = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        // Assuming DatabaseService has a method to delete a program by ID
        boolean isDeleted = DatabaseService.deleteProgramById(programId);
        if (isDeleted) {
            System.out.println("Program with ID " + programId + " deleted.");
        } else {
            System.out.println("No program found with ID " + programId + ".");
        }
    }


    private static void showInternalTeamDashboard(InternalTeam internalTeam, Scanner scanner) {
        while (true) {
            System.out.println("Internal Team Dashboard");
            System.out.println("1. View All Reports");
            System.out.println("2. Add Comment to Report");
            System.out.println("3. Mark Report as In Progress");
            System.out.println("4. Close Report and Award Points");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayAllReports();
                    break;
                case 2:
                    addCommentToReport(internalTeam, scanner);
                    break;
                case 3:
                    changeReportStatus(internalTeam, scanner, Status.IN_PROGRESS);
                    break;
                case 4:
                    closeReportAndAwardPoints(internalTeam, scanner);
                    break;
                case 5:
                    return; // Exit the dashboard loop
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void closeReportAndAwardPoints(InternalTeam internalTeam, Scanner scanner) {
        System.out.print("Enter Report ID to close: ");
        int reportIdForClosure = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (DatabaseService.doesReportExist(reportIdForClosure)) {
            System.out.print("Enter points to award: ");
            int points = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            DatabaseService.updateReportStatusAndAwardPoints(reportIdForClosure, Status.CLOSED.toString(), points, internalTeam.getId());
            System.out.println("Report closed and points awarded.");
        } else {
            System.out.println("Report ID not found.");
        }
    }
    private static void addCommentToReport(InternalTeam internalTeam, Scanner scanner) {
        System.out.println("Available Reports:");
        displayAllReports();

        System.out.print("Enter Report ID to comment on: ");
        int reportId = getInputInt(scanner); // Get valid integer input for report ID

        if (DatabaseService.doesReportExist(reportId)) {
            System.out.print("Enter your comment: ");
            String comment = scanner.nextLine();
            DatabaseService.addCommentToReport(reportId, internalTeam.getId(), comment);
            System.out.println("Comment added successfully.");
        } else {
            System.out.println("Report ID not found. Please try again with a valid report ID.");
        }
    }

    private static int getInputInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer.");
            scanner.next(); // consume the incorrect input
        }
        int number = scanner.nextInt();
        scanner.nextLine(); // consume the newline character after the number
        return number;
    }
    private static void changeReportStatus(InternalTeam internalTeam, Scanner scanner, Status status) {
        System.out.println("Available Reports:");
        displayAllReports();

        System.out.print("Enter Report ID to mark as " + status + ": ");
        int reportId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (DatabaseService.doesReportExist(reportId)) {
            DatabaseService.updateReportStatus(reportId, status);
            System.out.println("Report status updated to " + status + ".");
        } else {
            System.out.println("Report ID not found. Please try again with a valid report ID.");
        }
    }


    private static void displayAllReports() {
        List<Report> reports = DatabaseService.getAllReports();
        for (Report report : reports) {
            System.out.println(report); // Assuming Report class has a toString() method
        }
    }



    private static void showRanking() {
        List<Hacker> hackers = DatabaseService.getHackersRanking();
        System.out.println("Hackers Ranking");
        int rank = 1;

        for (Hacker hacker : hackers) {
            System.out.println(rank + ". Hacker ID: " + hacker.getId() +
                    ", Username: " + hacker.getUsername() +
                    ", Points: " + hacker.getPoints());

            rank++;
        }
    }





    public static User loginUser(Scanner scanner) {
        return DatabaseService.loginUser();
    }



}