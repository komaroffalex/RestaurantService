package com.rservice.businesslogic.entities.users;

import com.rservice.businesslogic.Role;
import com.rservice.businesslogic.Status;
import com.rservice.businesslogic.entities.orders.Food;
import com.rservice.businesslogic.entities.orders.Order;
import com.rservice.businesslogic.entities.orders.Reservation;
import com.rservice.businesslogic.entities.orders.Table;
import com.rservice.businesslogic.exceptions.RServiceAppException;
import com.rservice.gui.facades.ServiceFacade;
import com.rservice.storage.Repository;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.List;

public class Administrator extends AbstractUser {

    public Administrator(int id, String login, String password, String name) {
        super(id, login, password, name);
    }

    public Food addNewFood(Repository repository, String foodName, float foodCost) {
        Food newFood = new Food(-1, foodName, foodCost);
        repository.addNewFood(newFood);
        return newFood;
    }

    public Table addNewTable(Repository repository, int seats, String location) {
        Table newTable = new Table(-1, seats, location);
        repository.addNewTable(newTable);
        return newTable;
    }

    public User addNewUser(Repository repository, String login, String password, String name, String role) {
        User newUser = null;
        switch (role) {
            case "Administrator":
                newUser = new Administrator(-1, login, DigestUtils.sha1Hex(password), name);
                break;
            case "Client":
                newUser = new Client(-1, login, DigestUtils.sha1Hex(password), name);
                break;
            case "Worker":
                newUser = new Worker(-1, login, DigestUtils.sha1Hex(password), name, -1);
                break;
        }
        repository.addNewUser(newUser);
        return newUser;
    }

    public Order placeOrder(Repository repository, Date delTime, List<Food> food, String address) {
        Order placedOrder = new Order(-1, delTime, food, address, Status.SUBMITTED, -1, this.getId());
        repository.addNewOrder(placedOrder);
        return placedOrder;
    }

    public Reservation placeReservation(Repository repository, Date time, int persons, Table table) {
        Reservation placedReservation = new Reservation(-1,time,persons,table,Status.SUBMITTED,this.getId());
        repository.addNewReservation(placedReservation);
        return placedReservation;
    }

    public Order confirmOrder(Repository repository, Object order) throws RServiceAppException {
        if(order==null) throw new RServiceAppException("Failed to confirm Order!");
        Order orderToConfirm = (Order) order;
        orderToConfirm.setStatus(Status.PREPARING);
        repository.update(orderToConfirm);
        return orderToConfirm;
    }

    public Order declineOrder(Repository repository, Object order) throws RServiceAppException {
        if(order==null) throw new RServiceAppException("Failed to decline Order!");
        Order orderToDecline = (Order) order;
        orderToDecline.setStatus(Status.DENIED);
        repository.update(orderToDecline);
        return orderToDecline;
    }

    public Reservation confirmReservation(Repository repository, Object reservation) throws RServiceAppException {
        if(reservation==null) throw new RServiceAppException("Failed to confirm Reservation!");
        Reservation reservationToConfirm = (Reservation) reservation;
        reservationToConfirm.setStatus(Status.READY);
        repository.update(reservationToConfirm);
        return reservationToConfirm;
    }

    public Reservation declineReservation(Repository repository, Object reservation) throws RServiceAppException {
        if(reservation==null) throw new RServiceAppException("Failed to decline Reservation!");
        Reservation reservationToDecline = (Reservation) reservation;
        reservationToDecline.setStatus(Status.DENIED);
        repository.update(reservationToDecline);
        return reservationToDecline;
    }

    @Override
    public Role getRole() {
        return Role.ADMINISTRATOR;
    }
}
