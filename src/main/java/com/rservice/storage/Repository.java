package com.rservice.storage;

import com.rservice.businesslogic.entities.orders.Food;
import com.rservice.businesslogic.entities.orders.Order;
import com.rservice.businesslogic.entities.orders.Reservation;
import com.rservice.businesslogic.entities.orders.Table;
import com.rservice.businesslogic.entities.users.Administrator;
import com.rservice.businesslogic.entities.users.Client;
import com.rservice.businesslogic.entities.users.User;
import com.rservice.businesslogic.entities.users.Worker;
import com.rservice.businesslogic.exceptions.NotFoundException;

import java.util.List;

public interface Repository {

    User logInUser(String login, String realPassword);
    User findUserById(int id);
    User findUserByLogin(String login);
    Table findTableById(int id);
    Table findTableByLocation(String location) throws NotFoundException;
    Food findFoodById(int id);

    List<Table> getAllTables();
    List<Food> getAllFood();
    List<Order> getAllOrders();
    List<Order> getAllUserOrders(Client client);
    List<Reservation> getAllReservations();
    List<Reservation> getAllUserReservations(Client client);
    List<Reservation> getAllUserReservationsById(int clientId);
    List<Order> getAllUserOrdersById(int clientId);
    List<User> getAllUsers();
    List<Worker> getAllWorkers();
    List<Client> getAllClients();
    List<Administrator> getAllAdministrators();
    List<Order> getAllWorkerOrders(Worker worker);
    List<Order> getAllWorkerOrdersById(int workerId);

    void addNewUser(User newUser);
    void addNewTable(Table table);
    void addNewFood(Food food);
    void addNewOrder(Order order);
    void addNewReservation(Reservation reservation);

    void update(Object obj);
    void updateAll();
    void clearAll();
    void dropAll();
}
