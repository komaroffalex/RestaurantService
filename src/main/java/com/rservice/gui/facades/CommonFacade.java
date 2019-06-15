package com.rservice.gui.facades;

import com.rservice.businesslogic.entities.orders.Food;
import com.rservice.businesslogic.entities.orders.Order;
import com.rservice.businesslogic.entities.orders.Reservation;
import com.rservice.businesslogic.entities.users.Administrator;
import com.rservice.businesslogic.entities.users.Client;
import com.rservice.businesslogic.entities.users.User;
import com.rservice.businesslogic.entities.users.Worker;
import com.rservice.businesslogic.exceptions.RServiceAppException;
import com.rservice.businesslogic.exceptions.LogInErrorException;
import com.rservice.businesslogic.exceptions.NotFoundException;
import com.rservice.storage.MappersRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.List;

public class CommonFacade implements Facade {

    private MappersRepository repository;
    private int loggedInUserId;

    public CommonFacade() {
        repository = new MappersRepository();
    }



    /**
     * Tries to log in user with login and password.
     *
     * @param login
     * @param realPassword
     * @return id of logged in user
     * @throws LogInErrorException if login or password incorrect
     */
    @Override
    public int logInUser(String login, String realPassword) throws LogInErrorException {
        User user = repository.logInUser(login, realPassword);
        if (user == null) throw new LogInErrorException("Incorrect login or password.");
        loggedInUserId = user.getId();
        return user.getId();
    }

    @Override
    public String getUserRole(int userId) throws NotFoundException {
        User user = repository.findUserById(userId);
        if (user == null) throw new NotFoundException("User with ID " + userId + " not found.");
        return user.getRole().getRoleName();
    }

    @Override
    public String getUserLogin(int userId) throws NotFoundException {
        User user = repository.findUserById(userId);
        if (user == null) throw new NotFoundException("User with ID " + userId + " not found.");
        return user.getLogin();
    }

    @Override
    public String getUserName(int userId) throws NotFoundException {
        User user = repository.findUserById(userId);
        if (user == null) throw new NotFoundException("User with ID " + userId + " not found.");
        return user.getName();
    }

    @Override
    public User getUser(int userId) throws NotFoundException {
        User user = repository.findUserById(userId);
        if (user == null) throw new NotFoundException("User with ID " + userId + " not found.");
        return user;
    }

    @Override
    public Food getFood(int foodId) throws NotFoundException {
        Food book = repository.findFoodById(foodId);
        if (book == null) throw new NotFoundException("Food with ID " + foodId + " not found.");
        return book;
    }

    @Override
    public List<Food> getAllFood() throws NotFoundException {
        List<Food> food = repository.getAllFood();
        if (food == null) throw new NotFoundException("All food not found.");
        return food;
    }

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = repository.getAllReservations();
        return reservations;
    }

    @Override
    public List<Reservation> getAllUserReservations(int userId) {
        List<Reservation> reservations = repository.getAllUserReservationsById(userId);
        return reservations;
    }

    @Override
    public List<Reservation> getAllClientReservations(int userId) {
        List<Reservation> reservations = null;
        try {
            reservations = ((Client) getUser(loggedInUserId)).getAllClientReservation(repository);
        }
        catch (NotFoundException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public List<Order> getAllClientOrders(int userId) {
        List<Order> orders = null;
        try {
            orders = ((Client) getUser(loggedInUserId)).getAllClientOrders(repository);
        }
        catch (NotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> getAllWorkerOrdersById(int userId) {
        List<Order> orders = null;
        try {
            orders = ((Worker) getUser(loggedInUserId)).getAllWorkerOrders(repository);
        }
        catch (NotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = repository.getAllOrders();
        return orders;
    }

    @Override
    public List<Order> getAllUserOrders(int userId) {
        List<Order> orders = repository.getAllUserOrdersById(userId);
        return orders;
    }

    @Override
    public List<User> getAllUsers() throws NotFoundException {
        List<User> users = repository.getAllUsers();
        if (users == null) throw new NotFoundException("All users not found.");
        return users;
    }

    @Override
    public void addNewUser(String login, String password, String name, String role) {
        try {
            ((Administrator) getUser(loggedInUserId)).addNewUser(repository, login, password, name, role);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerNewUser(String login, String password, String name, String role) {
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
    }

    @Override
    public void addNewFood(String name, Float cost) {
        try {
            ((Administrator) getUser(loggedInUserId)).addNewFood(repository, name, cost);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewOrder(Date delTime, List<Food> food, String address) {
        try {
            ((Client) getUser(loggedInUserId)).placeOrder(repository, delTime, food, address);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewReservation(Date resTime, int persons, String location) {
        try {
            ((Client) getUser(loggedInUserId)).placeReservation(repository, resTime, persons, repository.findTableByLocation(location));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirmOrder(Object order) throws RServiceAppException {
        try {
            ((Administrator) getUser(loggedInUserId)).confirmOrder(repository, order);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeOrder(Object order) throws RServiceAppException {
        try {
            ((Client) getUser(loggedInUserId)).confirmRecievedOrder(repository, order);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declineWorkerOrder(Object order) throws RServiceAppException {
        try {
            ((Worker) getUser(loggedInUserId)).declineOrder(repository, order);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finishOrder(Object order) throws RServiceAppException {
        try {
            ((Worker) getUser(loggedInUserId)).finishOrder(repository, order);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declineOrder(Object order) throws RServiceAppException {
        try {
            ((Administrator) getUser(loggedInUserId)).declineOrder(repository, order);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirmReservation(Object reservation) throws RServiceAppException {
        try {
            ((Administrator) getUser(loggedInUserId)).confirmReservation(repository, reservation);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declineReservation(Object reservation) throws RServiceAppException {
        try {
            ((Administrator) getUser(loggedInUserId)).declineReservation(repository, reservation);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        repository.updateAll();
    }
}
