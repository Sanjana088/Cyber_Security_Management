package Users;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.*;
import java.util.Scanner;
import org.apache.commons.codec.digest.DigestUtils; // You may need to add the Apache Commons Codec library to your project for this


public class DatabaseService {
    private static final String DATABASE_URL = "jdbc:sqlite:vulzero.db";

    static{
        try{
            Class.forName("org.sqlite.JDBC");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void createTables(){
        createDatabaseIfNotExists();
        String createUserTableQuery =
                "CREATE TABLE IF NOT EXISTS users (" +
                        "id SERIAL PRIMARY KEY," +
                        "username VARCHAR(255) NOT NULL UNIQUE," +
                        "password VARCHAR(255) NOT NULL," +
                        "user_type VARCHAR(50) NOT NULL" +
                        ")";

        String createHackerTableQuery =
                "CREATE TABLE IF NOT EXISTS hackers (" +
                        "id SERIAL PRIMARY KEY," +
                        "user_id INTEGER NOT NULL UNIQUE," +
                        "FOREIGN KEY (user_id) REFERENCES users(id)" +
                        ")";
        String createPointsTableQuery =
                "CREATE TABLE IF NOT EXISTS hacker_points (" +
                        "id SERIAL PRIMARY KEY," +
                        "hacker_id INTEGER NOT NULL UNIQUE," +
                        "points INTEGER DEFAULT 0," +
                        "FOREIGN KEY (hacker_id) REFERENCES hackers(id)" +
                        ")";


        String createSubmissionTableQuery =
                "CREATE TABLE IF NOT EXISTS submissions (" +
                        "id SERIAL PRIMARY KEY," +
                        "title VARCHAR(255) NOT NULL," +
                        "description TEXT NOT NULL," +
                        "status VARCHAR(50) NOT NULL," +
                        "hacker_id INTEGER NOT NULL," +
                        "FOREIGN KEY (hacker_id) REFERENCES hackers(id)" +
                        ")";

        String createReportTableQuery =
                "CREATE TABLE IF NOT EXISTS reports (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "hacker_id INTEGER NOT NULL," +
                        "title VARCHAR(255) NOT NULL," +
                        "summary TEXT NOT NULL," +
                        "vulnerable_url VARCHAR(255) NOT NULL," +
                        "details TEXT NOT NULL," +
                        "status VARCHAR(50) NOT NULL," +
                        "FOREIGN KEY (hacker_id) REFERENCES users(id)" +
                        ")";



        String createCommentsTableQuery = "CREATE TABLE IF NOT EXISTS comments (" +
                "id SERIAL PRIMARY KEY," +
                "report_id INTEGER NOT NULL," +
                "internal_team_id INTEGER NOT NULL," +
                "comment TEXT NOT NULL," +
                "FOREIGN KEY (report_id) REFERENCES reports(id)," +
                "FOREIGN KEY (internal_team_id) REFERENCES users(id)" +
                ")";

        String createProgramsTableQuery = "CREATE TABLE IF NOT EXISTS programs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," + // Changed to AUTOINCREMENT for SQLite
                "company_id INTEGER NOT NULL," +
                "scope TEXT NOT NULL," +
                "rules TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "rewards_amount DOUBLE NOT NULL," +
                "FOREIGN KEY (company_id) REFERENCES users(id)" + // Assuming companies are stored in the users table
                ")";


        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(createUserTableQuery);
            statement.execute(createHackerTableQuery);
            statement.execute(createPointsTableQuery);
            statement.execute(createSubmissionTableQuery);
            statement.execute(createReportTableQuery);
            statement.execute(createCommentsTableQuery);
            statement.execute(createProgramsTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void createDatabaseIfNotExists() {
        try (Connection connection = getConnection()) {
            // This will create an empty database file if it doesn't exist
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void submitReport(int hackerId, String title, String summary, String vulnerableUrl, String details) {
        String query = "INSERT INTO reports (hacker_id, title, summary, vulnerable_url, details, status) VALUES (?, ?, ?, ?, ?, 'Open')";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, hackerId);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, summary);
            preparedStatement.setString(4, vulnerableUrl);
            preparedStatement.setString(5, details);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void createOrUpdateProgram(int companyId, Program program) {
        if (hasProgram(companyId)) {
            Integer programId = getProgramIdByCompanyId(companyId);
            if (programId != null) {
                updateProgram(companyId, programId, program);
            } else {
                // Handle the case where programId is not found
            }
        } else {
            createProgram(companyId, program);
        }
    }


    // Method to add a comment to a report
    public static void addComment(int reportId, int internalTeamId, String comment) {
        String query = "INSERT INTO comments (report_id, internal_team_id, comment) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reportId);
            preparedStatement.setInt(2, internalTeamId);
            preparedStatement.setString(3, comment);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<ReportWithComments> getReportsWithComments(int hackerId) {
        List<ReportWithComments> reportsWithComments = new ArrayList<>();

        String query = "SELECT r.id, r.title, r.summary, r.vulnerable_url, r.details, r.status, c.comment " +
                "FROM reports r " +
                "LEFT JOIN comments c ON r.id = c.report_id " +
                "WHERE r.hacker_id = ?";


        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, hackerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            HashMap<Integer, ReportWithComments> reportMap = new HashMap<>();

            while (resultSet.next()) {
                int reportId = resultSet.getInt("id");
                ReportWithComments report = reportMap.getOrDefault(reportId, new ReportWithComments(/* parameters to initialize */));

                report.setReportId(reportId);
                report.setTitle(resultSet.getString("title"));
                report.setSummary(resultSet.getString("summary"));
                report.setVulnerableUrl(resultSet.getString("vulnerable_url"));
                report.setDetails(resultSet.getString("details"));
                report.setStatus(resultSet.getString("status"));


                String comment = resultSet.getString("comment");
                if (comment != null) {
                    report.getComments().add(comment);
                }

                reportMap.put(reportId, report);
            }

            reportsWithComments.addAll(reportMap.values());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportsWithComments;
    }



    public static User registerUser(String username, String password, UserType userType) {
        String hashedPassword = hashPassword(password);

        String insertUserQuery = "INSERT INTO users (username, password, user_type) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, userType.name());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                // Return a new User object without the generated ID
                return new User(0, username, hashedPassword, userType); // 0 or another placeholder for ID
            } else {
                System.out.println("Registration failed. No rows affected.");
            }
        } catch (SQLException e) {
            System.err.println("Error during user registration: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }



    private static String hashPassword(String password) {
        // Use a secure hashing algorithm (e.g., SHA-256) to hash the password
        return DigestUtils.sha256Hex(password);
    }



    public static User loginUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        System.out.println("Choose User Type:");
        System.out.println("1. Hacker");
        System.out.println("2. Company");
        System.out.println("3. Internal Team");
        System.out.println("4. Manager");

        int userTypeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        UserType userType;

        switch (userTypeChoice) {
            case 1:
                userType = UserType.HACKER;
                break;
            case 2:
                userType = UserType.COMPANY;
                break;
            case 3:
                userType = UserType.INTERNAL_TEAM;
                break;
            case 4:
                userType = UserType.MANAGER;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Hacker.");
                userType = UserType.HACKER;
        }

        return DatabaseService.loginUser(username, password, userType);
    }

    public static User loginUser(String inputUsername, String inputPassword, UserType userType) {

        String hashedPassword = hashPassword(inputPassword); // hash the password before checking th query

        String selectUserQuery = "SELECT * FROM users WHERE username = ? AND password = ? AND user_type = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectUserQuery)) {
            preparedStatement.setString(1, inputUsername);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, userType.name());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                return switch (userType) {
                    case HACKER -> new Hacker(id, inputUsername, inputPassword, userType);
                    case COMPANY -> new Company(id, inputUsername, inputPassword, userType);
                    case INTERNAL_TEAM -> new InternalTeam(id, inputUsername, inputPassword, userType);
                    case MANAGER -> new Manager(id, inputUsername, inputPassword, userType);
                };
            } else {
                System.out.println("Login failed. Invalid username, password, or user type.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean hasUsers() {
        String countUsersQuery = "SELECT COUNT(*) FROM users";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(countUsersQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Check if there is at least one user
            return resultSet.next() && resultSet.getInt(1) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static List<Hacker> getHackersRanking() {

        String query = "SELECT h.id AS hacker_id, u.username, hp.points " +
                "FROM hackers h " +
                "JOIN users u ON h.user_id = u.id " +
                "JOIN hacker_points hp ON h.id = hp.hacker_id " +
                "ORDER BY hp.points DESC";

        List<Hacker> hackers = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int hackerId = resultSet.getInt("hacker_id");
                String username = resultSet.getString("username");
                int points = resultSet.getInt("points");

                Hacker hacker = new Hacker(0, username, "password-not-needed", UserType.HACKER);
                hacker.setId(hackerId);
                hacker.setPoints(points);

                hackers.add(hacker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hackers;
    }

    public static boolean hasProgram(int companyId) {
        String query = "SELECT COUNT(*) FROM programs WHERE company_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void createProgram(int companyId, Program program) {
        String query = "INSERT INTO programs (company_id, scope, rules, description, rewards_amount) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, companyId);
            preparedStatement.setString(2, program.getScope());
            preparedStatement.setString(3, program.getRules());
            preparedStatement.setString(4, program.getDescription());
            preparedStatement.setDouble(5, program.getRewardsAmount());

            preparedStatement.executeUpdate();
            System.out.println("Program created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Program> getProgramsByCompanyId(int companyId) {
        List<Program> programs = new ArrayList<>();
        String query = "SELECT * FROM programs WHERE company_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id"); // Retrieve the id
                String scope = resultSet.getString("scope");
                String rules = resultSet.getString("rules");
                String description = resultSet.getString("description");
                double rewardsAmount = resultSet.getDouble("rewards_amount");

                programs.add(new Program(id, scope, rules, description, rewardsAmount)); // Initialize Program with id
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }



    public static Integer getProgramIdByCompanyId(int companyId) {
        String query = "SELECT id FROM programs WHERE company_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null or handle as per your requirement
    }


    public static void updateProgram(int companyId, int programId, Program updatedProgram) {
        String query = "UPDATE programs SET scope = ?, rules = ?, description = ?, rewards_amount = ? WHERE id = ? AND company_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, updatedProgram.getScope());
            preparedStatement.setString(2, updatedProgram.getRules());
            preparedStatement.setString(3, updatedProgram.getDescription());
            preparedStatement.setDouble(4, updatedProgram.getRewardsAmount());
            preparedStatement.setInt(5, programId);
            preparedStatement.setInt(6, companyId);

            preparedStatement.executeUpdate();
            System.out.println("Program updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteProgram(int companyId, int programId) {
        String query = "DELETE FROM programs WHERE id = ? AND company_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, programId);
            preparedStatement.setInt(2, companyId);

            preparedStatement.executeUpdate();
            System.out.println("Program deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Submission> getSubmissionsByHackerId(int hackerId) {
        List<Submission> submissions = new ArrayList<>();
        String selectSubmissionsQuery = "SELECT * FROM submissions WHERE hacker_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSubmissionsQuery)) {
            preparedStatement.setInt(1, hackerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String statusStr = resultSet.getString("status");
                Status status = Status.valueOf(statusStr);
                submissions.add(new Submission(id, title, description, status, hackerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return submissions;
    }


    public static List<Submission> getclosedReports(int hackerID) {
        List<Submission> closedReports = new ArrayList<>();
        String selectclosedReportQuery = "SELECT * FROM submissions WHERE hacker_id = ? AND status = 'CLOSED'";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectclosedReportQuery)) {
            preparedStatement.setInt(1, hackerID);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String statusStr = resultSet.getString("status");
                int hackerId = resultSet.getInt("hacker_id");
                Status status = Status.valueOf(statusStr);

                Submission closedReport = new Submission(id, title, description, status, hackerId);

                closedReports.add(closedReport);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return closedReports;
    }





    public static List<Report> getAllReports() {
        List<Report> reports = new ArrayList<>();
        String query = "SELECT * FROM reports";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int hackerId = resultSet.getInt("hacker_id");
                String title = resultSet.getString("title");
                String summary = resultSet.getString("summary");
                String vulnerableUrl = resultSet.getString("vulnerable_url");
                String details = resultSet.getString("details");
                String status = resultSet.getString("status");

                Report report = new Report(id, hackerId, title, summary, vulnerableUrl, details, status);
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }


    public static void addCommentToReport(int reportId, int internalTeamId, String comment) {
        String query = "INSERT INTO comments (report_id, internal_team_id, comment) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reportId);
            preparedStatement.setInt(2, internalTeamId);
            preparedStatement.setString(3, comment);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateReportStatus(int reportId, Status newStatus) {
        String updateQuery = "UPDATE reports SET status = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newStatus.name()); // Convert enum to string
            preparedStatement.setInt(2, reportId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateReportStatusAndAwardPoints(int reportId, String newStatus, int points, int hackerId) {
        try (Connection connection = getConnection()) {
            // Update report status
            String updateReportQuery = "UPDATE reports SET status = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateReportQuery)) {
                preparedStatement.setString(1, newStatus);
                preparedStatement.setInt(2, reportId);
                preparedStatement.executeUpdate();
            }

            // If the report is closed, award points
            if (Status.CLOSED.toString().equalsIgnoreCase(newStatus)) {
                String updatePointsQuery = "UPDATE hacker_points SET points = points + ? WHERE hacker_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updatePointsQuery)) {
                    preparedStatement.setInt(1, points);
                    preparedStatement.setInt(2, hackerId);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean doesReportExist(int reportId) {
        String query = "SELECT COUNT(*) FROM reports WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reportId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String userTypeString = rs.getString("user_type");

                // Convert the String to UserType enum
                UserType userType = UserType.valueOf(userTypeString.toUpperCase());

                // Assuming User class has a constructor that matches these fields
                User user = new User(id, username, password, userType);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid user type in database.");
        }
        return users;
    }


    public static List<Program> getAllPrograms() {
        List<Program> programs = new ArrayList<>();
        String sql = "SELECT * FROM programs";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                // Assuming the company_id field is not required to construct a Program object
                String scope = rs.getString("scope");
                String rules = rs.getString("rules");
                String description = rs.getString("description");
                double rewardsAmount = rs.getDouble("rewards_amount");

                Program program = new Program(id, scope, rules, description, rewardsAmount);
                programs.add(program);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }


    public static boolean removeUserById(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteProgramById(int programId) {
        String sql = "DELETE FROM programs WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, programId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }







}

