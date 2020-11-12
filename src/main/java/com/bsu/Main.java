package main.java.com.bsu;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static User currentUser = new User("", "", "", "", "");
    public static void main(String[] args) {
        File userInfo = new File(".\\src\\files\\users.txt");
        File hostelInfo = new File(".\\src\\files\\hostels.txt");
        final String dateFormat = "dd.MM.yyyy";
        try (Scanner scanner = new Scanner(System.in)) {
            HostelReader hostelReader = new HostelReader(hostelInfo, dateFormat);
            UserReader userReader = new UserReader(userInfo);
            Map<String,User> users = userReader.readAllRecords();
            List<Hostel> hostels = hostelReader.readAllRecords();
            FileWriter userWriter = new FileWriter(".\\src\\files\\users.txt", true);
            showMenu();
            int req = scanner.nextInt();
            while (req != 7) {
                workWithRequests(hostels, users, scanner, userWriter, dateFormat, req);
                showMenu();
                req = scanner.nextInt();
            }
            userWriter.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void showMenu() {
        System.out.println("1)Add new user");
        System.out.println("2)Login as existing user");
        System.out.println("3)Show all records");
        System.out.println("4)Find hotel chain by hotel id(admin only)");
        System.out.println("5)Hotel chain statistics");
        System.out.println("6)Top hotels by number of Rooms");
        System.out.println("7)Exit");
    }

    public static void workWithRequests(List<Hostel> hostels, Map<String,User> users, Scanner scanner,
                                        FileWriter userWriter, String dateFormat, int req) {
        scanner.nextLine();
        if (currentUser.getRole().isEmpty()){
            if(req != 1 && req != 2){
                System.out.println("You need to login first");
                return;
            }
        }
        switch (RequestProcessor.Request.getRequestFromInt(req)) {
            case ADD_USER:
                RequestProcessor.addUser(users, userWriter, scanner);
                break;
            case LOGIN:
                currentUser = RequestProcessor.login(users, scanner);
                break;
            case VIEW_ALL_RECORDS:
                RequestProcessor.showAllHostelRecords(hostels);
                break;
            case FIND_CHAIN_BY_ID:
                if(currentUser.getRole().equals("USER")){
                    System.out.println("Admin access only ");
                }else {
                    try {
                        System.out.println("Enter id: ");
                        int id = scanner.nextInt();
                        System.out.println(RequestProcessor.findHotelChainByHotelId(hostels,id));
                    }catch (Exception ex){
                        System.out.println("Wrong id");
                    }
                }
                break;
            case CHAIN_STATS:
                System.out.println("Enter chain name:");
                String chain = scanner.nextLine();
                RequestProcessor.chainStats(hostels,chain);
                break;
            case TOP_HOTELS:
                try{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                    int n; LocalDate ub; LocalDate lb;
                    System.out.println("Enter " + "n:");
                    n = scanner.nextInt(); scanner.nextLine();
                    System.out.println("Enter date lower bound(dd.MM.yyyy)");
                    String lbstr = scanner.nextLine();
                    lb = LocalDate.parse(lbstr,formatter);
                    System.out.println("Enter date upper bound(dd.MM.yyyy)");
                    String ubstr = scanner.nextLine();
                    ub = LocalDate.parse(ubstr,formatter);
                    RequestProcessor.topHotels(hostels,lb,ub,n);
                }catch (Exception ex){
                    System.out.println("Can't parse expressions");
                }
                break;
            case DEFAULT:
                System.out.println("Unknown request");
                break;
        }
    }
}