package Users;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.SortedMap;

class AuthenticationService {

    public static User registerUser(Scanner scanner) {

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

        System.out.println("Enter Username:");
        String username = scanner.nextLine();

        System.out.println("Enter Password:");
        String password = scanner.nextLine();

        User registeredUser = DatabaseService.registerUser(username, password, userType);

        if (registeredUser != null) {
            System.out.println("Register Successful");
        } else {
            System.out.println("Registration Failed.");
        }

        return registeredUser;
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

        User loggedInUser = DatabaseService.loginUser(username, password, userType);

        if (loggedInUser != null) {
            System.out.println("Login successful");
        } else {
            System.out.println("Login Failed. Invalid username, password, or user type.");
        }

        return loggedInUser;
    }




}
