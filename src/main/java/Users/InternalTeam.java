package Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InternalTeam extends User{

    public InternalTeam(int id, String username, String password, UserType userType) {
        super(id, username, password, userType);
    }
    public void reviewReports() {
        Scanner scanner = new Scanner(System.in);

        // Fetch submitted reports from the database
        List<Submission> submissions = getSubmittedReports();

        if (submissions.isEmpty()) {
            System.out.println("No submitted reports available.");
            return;
        }

        System.out.println("List of Submitted Reports:");
        for (Submission submission : submissions) {
            System.out.println("ID: " + submission.getId() + ", Title: " + submission.getTitle());
        }

        System.out.println("Enter the ID of the report you want to review:");
        int reportId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Submission selectedReport = findReportById(reportId, submissions);

        if (selectedReport != null) {
            System.out.println("Selected Report:");
            System.out.println(selectedReport);

            System.out.println("Enter your comments for the report:");
            String comments = scanner.nextLine();

            // Add comments to the selected report in the database
            addCommentsToReport(selectedReport, comments);

            System.out.println("Comments added successfully.");
        } else {
            System.out.println("Report not found with ID: " + reportId);
        }
    }
    private List<Submission> getSubmittedReports() {
        List<Submission> submissions = new ArrayList<>();
        String selectReportsQuery = "SELECT * FROM submissions WHERE status = 'Submitted'";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectReportsQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String statusStr = resultSet.getString("status");
                int hackerId = resultSet.getInt("hacker_id");
                Status status = Status.valueOf(statusStr);


                Submission disclosedReport = new Submission(id, title, description, status, hackerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return submissions;
    }

    private Submission findReportById(int reportId, List<Submission> submissions) {
        for (Submission submission : submissions) {
            if (submission.getId() == reportId) {
                return submission;
            }
        }
        return null;
    }

    private void addCommentsToReport(Submission report, String comments) {
        // Update the report in the database with the comments
        String updateReportQuery = "UPDATE submissions SET comments = ? WHERE id = ?";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateReportQuery)) {
            preparedStatement.setString(1, comments);
            preparedStatement.setInt(2, report.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
