package Users;

import java.util.List;

public class Manager extends User {
    public Manager(int id, String username, String password, UserType userType) {
        super(id, username, password, userType);
    }

    // Method to remove a user by ID
    public void removeUser(int userId, List<User> userList) {
        userList.removeIf(user -> user.getId() == userId);
        System.out.println("User with ID " + userId + " removed.");
    }

    // Method to delete a program by ID
    public void deleteProgram(int programId, List<Program> programList) {
        programList.removeIf(program -> program.getId() == programId);
        System.out.println("Program with ID " + programId + " deleted.");
    }
}
