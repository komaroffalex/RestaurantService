package com.rservice.businesslogic.entities.orders;

import com.rservice.businesslogic.Status;

import java.util.Date;

public class Reservation {

    private int id;
    private Date reservationTime;
    private int persons;
    private Table table;
    private float cost;
    private Status status;
    private int client_id;

    public Reservation(int id, Date time, int persons, Table table, Status status, int clientId) {
        this.id = id;
        this.reservationTime = time;
        this.persons = persons;
        this.table = table;
        this.status = status;
        this.client_id = clientId;
        if (persons > 3) {
            this.cost = 500*persons;
        }
        else this.cost = 0;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation otherReservation = (Reservation) obj;
        return id == otherReservation.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return reservationTime;
    }

    public void setTime(Date time) {
        this.reservationTime = time;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getClientId() {
        return client_id;
    }

    public void setClientId(int id) {
        this.client_id = id;
    }
}
