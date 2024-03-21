package Users;

import java.util.ArrayList;
import java.util.List;

public class Hacker extends User {

    private List<Submission> submittedReports;
    private List<Submission> inbox;

    private int points;

    public Hacker(int id, String username, String password, UserType userType) {
        super(id, username, password, userType);
        this.submittedReports = new ArrayList<>();
        this.inbox = new ArrayList<>();
        this.points = 0; // initializing points
    }

    public List<Submission> getSubmittedReports() {
        return submittedReports;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }



    public List<Submission> getInbox() {
        return inbox;
    }

    public void submitReport(Submission report) {
        int hackerId = getId();
        String title = report.getTitle();
        String summary = report.getDescription();
        String vulnerableUrl = report.getVulnerableUrl();
        String details = report.getDetails();

        DatabaseService.submitReport(hackerId, title, summary, vulnerableUrl, details);
        submittedReports.add(report);
    }



    public void receiveReport(Submission report) {
        inbox.add(report);
    }

/*
    public void viewCompanyPages(Company company) {
        System.out.println("Accessing " + company.getUsername() + "'s pages");
    }*/
}
