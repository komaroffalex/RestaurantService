package com.rservice.businesslogic.entities.users;

import com.rservice.businesslogic.Role;
import com.rservice.businesslogic.Status;
import com.rservice.businesslogic.entities.orders.Order;
import com.rservice.businesslogic.exceptions.RServiceAppException;
import com.rservice.storage.Repository;

public class Worker extends AbstractUser {

    private int countOrdersTaken;

    public Worker(int id, String login, String password, String name, int ordersTaken) {
        super(id, login, password, name);
        countOrdersTaken = ordersTaken;
    }

    public Order finishOrder(Repository repository, Object order) throws RServiceAppException {
        if(order==null) throw new RServiceAppException("Failed to finish Order!");
        Order orderToFinish = (Order) order;
        orderToFinish.setStatus(Status.READY);
        repository.update(orderToFinish);
        return orderToFinish;
    }

    public Order declineOrder(Repository repository, Object order) throws RServiceAppException {
        if(order==null) throw new RServiceAppException("Failed to decline Order!");
        Order orderToDecline = (Order) order;
        orderToDecline.setStatus(Status.DENIED);
        repository.update(orderToDecline);
        return orderToDecline;
    }

    @Override
    public Role getRole() {
        return Role.WORKER;
    }

    public int getOrdersTaken() {return countOrdersTaken;}

    public void setOrdersTaken(int numOfOrders) {this.countOrdersTaken = numOfOrders;}
}
