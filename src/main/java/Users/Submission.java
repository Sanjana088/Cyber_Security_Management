package Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static Users.DatabaseService.getConnection;

public class Submission {
    private int id;
    private String title;
    private String description;
    private Status status;
    private int hackerId;

    private String vulnerableUrl;
    private String details;
    private List<String> comments;

    public Submission(int id, String title, String description, Status status, int hackerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.hackerId = hackerId;
        // Initialize comments if needed
    }

    // Constructor with all fields
    public Submission(int id, String title, String description, Status status, int hackerId, String vulnerableUrl, String details) {
        this(id, title, description, status, hackerId); // Call the other constructor
        this.vulnerableUrl = vulnerableUrl;
        this.details = details;
    }

    public String getVulnerableUrl(){
        return vulnerableUrl;
    }
    public String getDetails() {
        return details;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;

    }


    public Status getStatus(){
        return status;
    }

    public int getHackerId(){
        return hackerId;
    }

    public List<String> getComments(){
        return comments;
    }

    public void addComments(String comment){
        this.comments.add(comment);
    }

}
