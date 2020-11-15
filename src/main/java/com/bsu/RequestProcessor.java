package main.java.com.bsu;

import java.io.FileWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RequestProcessor {
    enum Request {
        ADD_USER(1),
        LOGIN(2),
        VIEW_ALL_RECORDS(3),
        FIND_CHAIN_BY_ID(4),
        CHAIN_STATS(5),
        TOP_HOTELS(6),
        DEFAULT(0);

        private int value;

        Request(int val) {
            this.value = val;
        }

        public int getValue() {
            return value;
        }

        public static Request getRequestFromInt(int val) {
            for (Request req : values()) {
                if (req.value == val) return req;
            }
            return Request.DEFAULT;
        }

    }

    public static boolean addUser(Map<String, User> users, FileWriter writer, Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter login:");
        String login = scanner.nextLine();
        if (users.containsKey(login)) {
            System.out.println("Can't add user(this login is already taken)");
            return false;
        }
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        User newUser = new User(username, login, email, password, "USER");
        try {
            writer.write(newUser.toString() + System.lineSeparator());
        } catch (Exception ex) {
            System.out.println("Can't add user");
            return false;
        }
        users.put(newUser.getLogin(), newUser);
        return true;
    }

    public static User login(Map<String, User> users, Scanner scanner) {
        System.out.println("Enter login:");
        String login = scanner.nextLine();
        if (!users.containsKey(login)) {
            System.out.println("Unknown username. Try again");
            return new User("", "", "", "", "USER");
        } else {
            User user = users.get(login);
            System.out.println("Enter password:");
            String password = scanner.nextLine();
            if (user.getPassword().equals(password))
                return user;
        }
        System.out.println("Wrong Password. Try again");
        return new User("", "", "", "", "USER");
    }

    public static void showAllHostelRecords(List<Hostel> hostels) {
        for (Hostel hostel : hostels) {
            System.out.println(hostel);
        }
    }

    public static String findHotelChainByHotelId(List<Hostel> hostels, int id) {
        for (Hostel hostel : hostels) {
            if (hostel.getId() == id) {
                return hostel.getChain();
            }
        }
        return "Nothing found";
    }

    public static void chainStats(List<Hostel> hostels, String chain) {
        double rank = 0;
        int numberOfHotels = 0;
        for (Hostel hostel : hostels) {
            if (hostel.getChain().equals(chain)) {
                rank += hostel.getRank();
                numberOfHotels++;
            }
        }
        if (numberOfHotels == 0) {
            System.out.println("Unknown chain");
        } else {
            rank /= numberOfHotels;
            System.out.println("Number of hotels: " + numberOfHotels + ", average rank: " + rank);
        }
    }

    public static void topHotels(List<Hostel> hostels, LocalDate lb, LocalDate ub, int n) {
        hostels.stream().sorted((o1, o2) -> o2.getRank() - o1.getRank()).
                filter(h -> h.getOpeningDate().compareTo(lb) >= 0 && h.getOpeningDate().compareTo(ub) <= 0)
                .limit(n).forEach(System.out::println);
    }
}