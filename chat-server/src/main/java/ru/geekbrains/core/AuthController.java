package ru.geekbrains.core;

import ru.geekbrains.data.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class AuthController {

    private PreparedStatement ps = null;

    HashMap<String, User> users = new HashMap<>();

    public void init() {
        createTable();
        for (User user : receiveUsers()) {
            users.put(user.getLogin(), user);
        }
    }

    private void createTable() {
        try {
            DBConn.getInstance().connection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Users ("
                            + "UserID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                            + "Login TEXT NOT NULL, "
                            + "Password TEXT NOT NULL, "
                            + "NickName TEXT)"
            ).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getNickname(String login, String password) {
        User user = users.get(login);
        if (user != null && user.isPasswordCorrect(password)) {
            return user.getNickname();
        }
        return null;
    }

    public ArrayList<User> receiveUsers() {

        ArrayList<User> usersArr = new ArrayList<>();

        try {
            ps = DBConn
                    .getInstance()
                    .connection()
                    .prepareStatement(
                            "SELECT * FROM Users"
                    );

            ResultSet resultSet = ps.executeQuery();

            /* нужно отключить стандартный коммит и
             * включить пакетный коммит по записям*/
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("Login"));
                user.setPassword(resultSet.getString("Password"));
                user.setNickname(resultSet.getString("NickName"));
                usersArr.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace(); // нужено добавить роллбэк
        }
        usersArr.add(new User("admin","admin","sysRoot")); // админка))
        return usersArr;
    }

}
