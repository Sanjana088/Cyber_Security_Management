package Users;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private UserType userType;

    public User(int id, String username, String password, UserType userType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public User(String username, String password) {
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public UserType getUserType(){
        return userType;
    }

    @Override
    public String toString(){
        return "User{" +
                "username='"+ username +'\'' +
                ",password='" + password +'\'' +
                '}';
    }
}
