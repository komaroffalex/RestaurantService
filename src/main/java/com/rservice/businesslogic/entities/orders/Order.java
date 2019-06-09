package com.rservice.businesslogic.entities.orders;

import com.rservice.businesslogic.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Order {

    private int id;
    private Date deliveryTime;
    private List<Food> food;
    private float cost;
    private String address;
    private Status status;
    private int worker_id;
    private int client_id;

    public Order(int id, Date date, List<Food> food, String address, Status status, int workerId, int clientId) {
        this.id = id;
        this.deliveryTime = date;
        this.food = food;
        this.cost = 0;
        for (Food item : food) {
            this.cost += item.getCost();
        }
        this.address = address;
        this.status = status;
        this.worker_id = workerId;
        this.client_id = clientId;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Order otherOrder = (Order) obj;
        return id == otherOrder.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return deliveryTime;
    }

    public void setTime(Date time) {
        this.deliveryTime = time;
    }

    public List<Food> getFood() {
        return food;
    }

    public String getFoodAsStringId() {
        List<String> ids = new ArrayList<String>();
        for (Food item : food) {
            ids.add(Integer.toString(item.getId()));
        }
        return String.join(",", ids);
    }

    public String getFoodAsStringNames() {
        List<String> names = new ArrayList<String>();
        for (Food item : food) {
            names.add(item.getName());
        }
        return String.join(",", names);
    }

    public void setFood(List<Food> food) {
        this.food = food;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getWorkerId() {
        return worker_id;
    }

    public void setWorkerId(int id) {
        this.worker_id = id;
    }

    public int getClientId() {
        return client_id;
    }

    public void setClientId(int id) {
        this.client_id = id;
    }
}
