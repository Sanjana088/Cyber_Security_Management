package Users;

import java.util.Scanner;

class SubmissionService {

    public static void createSubmission(Scanner scanner, User user){
        System.out.println("Enter Submission Title:");
        String title = scanner.nextLine();

        System.out.println("Enter Submission Description:");
        String description = scanner.nextLine();

        // Assuming an additional argument is needed, e.g., a URL or detailed description.
        System.out.println("Enter Additional Details:");
        String additionalDetails = scanner.nextLine();

        // converting status enum to String
        String statusString = Status.OPEN.name();

        // Now calling the submitReport method with 5 arguments
        DatabaseService.submitReport(user.getId(), title, description, additionalDetails, statusString);
        System.out.println("Report Submitted Successfully");
    }
}
