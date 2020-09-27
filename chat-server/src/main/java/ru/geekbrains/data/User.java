package ru.geekbrains.data;

import ru.geekbrains.core.DBConn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private PreparedStatement ps = null;

    private String login;
    private String password;
    private String nickname;

    public User() {
    }

    public User(String login, String password, String nickname) {
        this.login = login;
        this.password = password;
        this.nickname = nickname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addUser(User user) throws SQLException {
        ps = DBConn
                .getInstance()
                .connection()
                .prepareStatement("" +
                        "INSERT INTO Users " +
                        "(Login, Password, NickName) " +
                        "VALUE (?, ?, ?) ");
        ps.setString(1, user.getLogin());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getNickname());
        int a = ps.executeUpdate();

    }

    public User getByNickName(String nickname) throws SQLException {
        ps = DBConn
                .getInstance()
                .connection()
                .prepareStatement(
                        "SELECT * FROM Users WHERE NickName = ?"
                );
        ps.setString(Integer.parseInt(nickname), nickname);

        ResultSet resultSet = ps.executeQuery();

        User user = new User();

        if (resultSet.next()) {
            user.setLogin(resultSet.getString("Login"));
            user.setPassword(resultSet.getString("Password"));
            user.setNickname(resultSet.getString("NickName"));
        }

        return user;
    }

    public User changeByNickName(String nickname) throws SQLException {
        ps = DBConn
                .getInstance()
                .connection()
                .prepareStatement(
                        "UPDATE * FROM Users WHERE NickName = ?"
                );
        ps.setString(Integer.parseInt(nickname), nickname);

        ResultSet resultSet = ps.executeQuery();

        User user = new User();

        if (resultSet.next()) {
            user.setLogin(resultSet.getString("Login"));
            user.setPassword(resultSet.getString("Password"));
            user.setNickname(resultSet.getString("NickName"));
        }

        return user;
    }


    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }
}
