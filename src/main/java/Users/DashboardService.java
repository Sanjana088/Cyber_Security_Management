package Users;



import java.util.List;

public class DashboardService {

    public static void showHackerDashboard(Hacker hacker){
        System.out.println("Hacker Dashboard");
        showSubmittedReports(hacker);
        showRanking();
    }

    public static void showSubmittedReports(Hacker hacker) {
        List<ReportWithComments> reports = DatabaseService.getReportsWithComments(hacker.getId());
        if (reports.isEmpty()) {
            System.out.println("No Submitted Reports.");
        } else {
            for (ReportWithComments report : reports) {
                System.out.println("Report ID: " + report.getReportId() + ", Title: " + report.getTitle() +
                        ", Summary: " + report.getSummary() + ", Vulnerable URL: " + report.getVulnerableUrl() +
                        ", Details: " + report.getDetails() + ", Status: " + report.getStatus());
                // Display comments if any
                if (!report.getComments().isEmpty()) {
                    System.out.println("Comments:");
                    report.getComments().forEach(System.out::println);
                }
            }
        }
    }

    public static void showclosedReports(User user) {
        if (user instanceof Hacker) {
            Hacker hacker = (Hacker) user;
            List<Submission> disclosedReports = DatabaseService.getclosedReports(hacker.getId());

            System.out.println("Disclosed Reports:");
        if (disclosedReports.isEmpty()) {
            System.out.println("No disclosed reports.");
        } else {
            for (Submission report : disclosedReports) {
                System.out.println("Report ID: " + report.getId() + ", Title: " + report.getTitle() +
                        ", Status: " + report.getStatus() + ", Description: " + report.getDescription() +
                        ", Hacker ID: " + report.getHackerId());
                // Add more details if needed
            }
        }
    } else {
            System.out.println("Invalid user type for viewing disclosed reports.");
        }
    }


    public static void showRanking(){
        List<Hacker> hackers = DatabaseService.getHackersRanking();
        System.out.println("Hackers Ranking");
        int rank = 1;

        for (Hacker hacker: hackers){
            System.out.println(rank + ". Hacker ID: " + hacker.getId() + ", Username: " + hacker.getUsername() +
                    ", Points: " +hacker.getPoints());

            rank++;
        }
    }
}
