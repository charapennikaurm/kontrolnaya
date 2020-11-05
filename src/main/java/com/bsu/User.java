package main.java.com.bsu;

import java.util.Objects;

public class User {
    private String username;
    private String login;
    private String email;
    private String password;
    private String role;

    public User(String username, String login, String email, String password, String role) {
        this.username = username;
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return username + ";" + login + ";" + email + ";" + password + ";" + role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User o = (User) obj;
        return Objects.equals(username, o.username) && Objects.equals(login,o.login) &&
                Objects.equals(email,o.email) && Objects.equals(password, o.password) &&
                Objects.equals(role, o.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, login, email, password, role);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
