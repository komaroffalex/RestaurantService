package com.rservice.businesslogic.entities.orders;

public class Table {

    private int id;
    private int seats;
    private String location;

    public Table(int id, int seats, String location) {
        this.id = id;
        this.seats = seats;
        this.location = location;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Table otherTable = (Table) obj;
        return id == otherTable.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
