package com.rservice.businesslogic.entities.orders;

public class Food {

    private int id;
    private float cost;
    private String name;

    public Food(int id, String name, float cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Food otherFood = (Food) obj;
        return id == otherFood.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
