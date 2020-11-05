package main.java.com.bsu;

import java.time.LocalDate;
import java.util.Objects;

public class Hostel {
    private int id;
    private String chain;//сеть
    private String name;
    private LocalDate openingDate;
    private int numberOfRooms;
    private int rank;

    public Hostel(int id, String chain, String name, LocalDate openingDate, int numberOfRooms, int rank) {
        this.id = id;
        this.chain = chain;
        this.name = name;
        this.openingDate = openingDate;
        this.numberOfRooms = numberOfRooms;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return id + " " + chain + " " + name + " " + openingDate.toString() + " " + numberOfRooms + " " + rank;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Hostel o = (Hostel) obj;
        return (id == o.id) && Objects.equals(chain, o.chain) &&
                Objects.equals(name, o.name) && Objects.equals(openingDate, o.openingDate) &&
                (numberOfRooms == o.numberOfRooms) && (rank == o.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chain, name, openingDate, numberOfRooms, rank);
    }

    public int getId() {
        return id;
    }

    public String getChain() {
        return chain;
    }

    public int getRank() {
        return rank;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }
}

