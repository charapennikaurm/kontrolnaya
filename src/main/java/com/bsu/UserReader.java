package main.java.com.bsu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class UserReader {
    private File file;

    public UserReader(File file) {
        this.file = file;
    }

    public Map<String,User> readAllRecords() {
        Map<String,User> records = new HashMap<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                User user = buildRecord(scanner.nextLine());
                records.put(user.getLogin(),user);
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Can't find file with user info");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return records;
    }

    public User buildRecord(String str) throws IllegalArgumentException {
        String[] fields = str.split(";");
        if (fields.length < 5) {
            throw new IllegalArgumentException("Not enough info");
        }
        String username = fields[0];
        String login = fields[1];
        String email = fields[2];
        String password = fields[3];
        String role = fields[4];
        return new User(username,login,email,password,role);
    }


}
