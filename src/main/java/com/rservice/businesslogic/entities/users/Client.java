package com.rservice.businesslogic.entities.users;

import com.rservice.businesslogic.Role;
import com.rservice.businesslogic.Status;
import com.rservice.businesslogic.entities.orders.Food;
import com.rservice.businesslogic.entities.orders.Order;
import com.rservice.businesslogic.entities.orders.Reservation;
import com.rservice.businesslogic.entities.orders.Table;
import com.rservice.businesslogic.exceptions.RServiceAppException;
import com.rservice.storage.Repository;

import java.util.Date;
import java.util.List;

public class Client extends AbstractUser {

    public Client(int id, String login, String password, String name) {
        super(id, login, password, name);
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

    public List<Order> getAllClientOrders (Repository repository) {
        return repository.getAllUserOrdersById(this.getId());
    }

    public List<Reservation> getAllClientReservation (Repository repository) {
        return repository.getAllUserReservationsById(this.getId());
    }

    public Order confirmRecievedOrder(Repository repository, Object order) throws RServiceAppException {
        if(order==null) throw new RServiceAppException("Failed to confirm Order!");
        Order orderToConfirm = (Order) order;
        orderToConfirm.setStatus(Status.CLOSED);
        repository.update(orderToConfirm);
        return orderToConfirm;
    }

    @Override
    public Role getRole() {
        return Role.CLIENT;
    }
}
