package com.rservice.gui.facades;

import com.rservice.businesslogic.entities.orders.Food;
import com.rservice.businesslogic.entities.orders.Order;
import com.rservice.businesslogic.entities.orders.Reservation;
import com.rservice.businesslogic.entities.users.User;
import com.rservice.businesslogic.exceptions.RServiceAppException;
import com.rservice.businesslogic.exceptions.LogInErrorException;
import com.rservice.businesslogic.exceptions.NotFoundException;

import java.util.Date;
import java.util.List;

public interface Facade {

    int logInUser(String login, String realPassword) throws LogInErrorException;
    void registerNewUser(String login, String password, String name, String role);

    String getUserRole(int userId) throws NotFoundException;
    String getUserLogin(int userId) throws NotFoundException;
    String getUserName(int userId) throws NotFoundException;

    User getUser(int userId) throws NotFoundException;
    Food getFood(int foodId) throws NotFoundException;

    List<Food> getAllFood() throws NotFoundException;
    List<Reservation> getAllReservations();

    List<Order> getAllOrders();

    List<User> getAllUsers() throws NotFoundException;

    // Functions of Administrator
    void addNewUser(String login, String password, String name, String role);
    void addNewFood(String foodName, Float foodCost);
    void confirmOrder(Object order) throws RServiceAppException;
    void declineOrder(Object order) throws RServiceAppException;
    void confirmReservation(Object order) throws RServiceAppException;
    void declineReservation(Object order) throws RServiceAppException;

    // Functions of CLient
    List<Order> getAllUserOrders(int userId);
    List<Reservation> getAllUserReservations(int userId);
    void addNewOrder(Date delTime, List<Food> food, String address) throws NotFoundException;
    void addNewReservation(Date resTime, int persons, String location) throws NotFoundException;

    // Functions of Worker
    List<Order> getAllWorkerOrdersById(int userId);
    void finishOrder(Object order) throws RServiceAppException;
    void declineWorkerOrder(Object order) throws RServiceAppException;
    void update();
}
