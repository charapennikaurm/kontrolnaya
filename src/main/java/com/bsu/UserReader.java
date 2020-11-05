package main.java.com.bsu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserReader {
    private File file;

    public UserReader(File file) {
        this.file = file;
    }

    public List<User> readAllRecords() {
        List<User> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                records.add(buildRecord(scanner.nextLine()));
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
