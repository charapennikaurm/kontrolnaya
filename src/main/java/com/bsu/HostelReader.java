package main.java.com.bsu;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HostelReader {
    private File file;
    private String dateFormat;

    public HostelReader(File file, String dateFormat) {
        this.file = file;
        this.dateFormat = dateFormat;
    }

    public List<Hostel> readAllRecords() {
        List<Hostel> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                records.add(buildRecord(scanner.nextLine()));
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Can't find file with hostels info");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return records;
    }

    public Hostel buildRecord(String str) throws IllegalArgumentException {
        String[] fields = str.split(";");
        if (fields.length < 6) {
            throw new IllegalArgumentException("Not enough info");
        }
        int id;
        String chain = fields[1];
        String name = fields[2];
        LocalDate openingDate;
        int numberOfRooms;
        int rank;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            openingDate = LocalDate.parse(fields[3], formatter);
            id = Integer.parseInt(fields[0]);
            numberOfRooms = Integer.parseInt(fields[4]);
            rank = Integer.parseInt(fields[5]);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Can't parse record");
        }

        return new Hostel(id,chain,name,openingDate,numberOfRooms,rank);
    }

}
