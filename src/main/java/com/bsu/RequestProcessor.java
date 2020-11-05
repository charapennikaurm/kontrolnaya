package main.java.com.bsu;

import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

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

    public static boolean addUser(List<User> users, FileWriter writer, Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter login:");
        String login = scanner.nextLine();
        for (User user : users) {
            if (login.equals(user.getLogin())) {
                System.out.println("Can't add user(this login is already taken)");
                return false;
            }
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
        users.add(newUser);
        return true;
    }

    public static User login(List<User> users, Scanner scanner) {
        System.out.println("Enter login:");
        String login = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        for (User user : users) {
            if (login.equals(user.getLogin()) && password.equals(user.getPassword())) {
                System.out.println("Welcome " + user.getUsername());
                return user;
            }
        }
        System.out.println("Try again");
        return new User("", "", "", "", "USER");
    }

    public static void showAllHostelRecords(List<Hostel> hostels) {
        for (Hostel hostel : hostels) {
            System.out.println(hostel.toString());
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
        hostels.stream().sorted(new Comparator<Hostel>() {
            @Override
            public int compare(Hostel o1, Hostel o2) {
                return o2.getRank() - o1.getRank();
            }
        }).filter(new Predicate<Hostel>() {
            @Override
            public boolean test(Hostel hostel) {
                LocalDate od = hostel.getOpeningDate();
                if ((od.compareTo(lb) >= 0) && (od.compareTo(ub) <= 0)){
                    return true;
                }
                return false;
            }
        }).limit(n).forEach(System.out::println);
    }
}
