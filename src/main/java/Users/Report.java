package Users;

public class Report {
    private int id;
    private int hackerId;
    private String title;
    private String summary;
    private String vulnerableUrl;
    private String details;
    private String status;

    // Constructor
    public Report(int id, int hackerId, String title, String summary, String vulnerableUrl, String details, String status) {
        this.id = id;
        this.hackerId = hackerId;
        this.title = title;
        this.summary = summary;
        this.vulnerableUrl = vulnerableUrl;
        this.details = details;
        this.status = status;
    }

    // Getters and setters for each field
    // ...

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getVulnerableUrl() {
        return vulnerableUrl;
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }

    public int getHackerId() {
        return hackerId;
    }

    @Override
    public String toString(){
        return "Report ID: " + id + ", Title: " + title + ", Summary: " + summary;

    }

}
