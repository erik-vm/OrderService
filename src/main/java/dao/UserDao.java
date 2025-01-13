package dao;

import model.User;

public class UserDao {

    public User getUserByUserName(String userName) {
        return switch (userName) {
            case "user" -> new User("user");
            case "admin" -> new User("admin");
            default -> null;
        };
    }
}
