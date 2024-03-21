package Users;

import java.util.ArrayList;
import java.util.List;

public class ReportWithComments {
    private int reportId;
    private String title;
    private String summary;
    private String vulnerableUrl;
    private String details;
    private String status;

    private List<String> comments; // List to hold comments

    // Constructor
    public ReportWithComments() {
        this.comments = new ArrayList<>();
    }

    // Getters and Setters
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getVulnerableUrl() {
        return vulnerableUrl;
    }

    public void setVulnerableUrl(String vulnerableUrl) {
        this.vulnerableUrl = vulnerableUrl;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<String> getComments() {
        return comments;
    }

    // No setter for comments, as we manage comments through the list's methods
    public void addComment(String comment) {
        this.comments.add(comment);
    }
}
