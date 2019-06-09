package com.rservice.storage;

import com.rservice.businesslogic.Role;
import com.rservice.businesslogic.entities.orders.Food;
import com.rservice.businesslogic.entities.orders.Order;
import com.rservice.businesslogic.entities.orders.Reservation;
import com.rservice.businesslogic.entities.orders.Table;
import com.rservice.businesslogic.entities.users.Administrator;
import com.rservice.businesslogic.entities.users.Client;
import com.rservice.businesslogic.entities.users.User;
import com.rservice.businesslogic.entities.users.Worker;
import com.rservice.businesslogic.exceptions.NotFoundException;
import com.rservice.storage.mappers.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MappersRepository implements Repository {

    private static TableMapper tableMapper;
    private static UserMapper userMapper;
    private static OrderMapper orderMapper;
    private static ReservationMapper reservationMapper;
    private static FoodMapper foodMapper;

    public MappersRepository() {
        try {
            if (tableMapper == null) tableMapper = new TableMapper();
            if (userMapper == null) userMapper = new UserMapper();
            if (orderMapper == null) orderMapper = new OrderMapper();
            if (reservationMapper == null) reservationMapper = new ReservationMapper();
            if (foodMapper == null) foodMapper = new FoodMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User logInUser(String login, String realPassword) {
        User loggedInUser = null;
        try {
            loggedInUser = userMapper.findByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (loggedInUser != null && loggedInUser.logIn(realPassword)) {
            return loggedInUser;
        }
        return null;
    }

    @Override
    public User findUserById(int id) {
        try {
            return userMapper.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUserByLogin(String login) {
        try {
            return userMapper.findByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Table findTableById(int id) {
        try {
            return tableMapper.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Table findTableByLocation(String location) throws NotFoundException {
        List<Table> allTables = getAllTables();
        for (Table item : allTables) {
            if(item.getLocation().equals(location)) return item;
        }
        throw new NotFoundException("Table not found.");
    }

    @Override
    public Food findFoodById(int id) {
        try {
            return foodMapper.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Table> getAllTables() {
        try {
            return new ArrayList<>(tableMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Food> getAllFood() {
        try {
            return new ArrayList<>(foodMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        try {
            return new ArrayList<>(orderMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getAllUserOrders(Client client) {
        List<Order> allOrders = null;
        try {
            allOrders = new ArrayList<>(orderMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        List<Order> userOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (((Client) findUserById(order.getClientId())).equals(client)) userOrders.add(order);
        }
        return userOrders;
    }

    @Override
    public List<Reservation> getAllReservations() {
        try {
            return new ArrayList<>(reservationMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reservation> getAllUserReservations(Client client) {
        List<Reservation> allReservations = null;
        try {
            allReservations = new ArrayList<>(reservationMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        List<Reservation> userReservations = new ArrayList<>();
        for (Reservation reservation : allReservations) {
            if (((Client) findUserById(reservation.getClientId())).equals(client)) userReservations.add(reservation);
        }
        return userReservations;
    }

    @Override
    public List<Reservation> getAllUserReservationsById(int clientId) {
        List<Reservation> allReservations = null;
        try {
            allReservations = new ArrayList<>(reservationMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        List<Reservation> userReservations = new ArrayList<>();
        for (Reservation reservation : allReservations) {
            if (reservation.getClientId() == clientId) userReservations.add(reservation);
        }
        return userReservations;
    }

    @Override
    public List<Order> getAllUserOrdersById(int clientId) {
        List<Order> allOrders = null;
        try {
            allOrders = new ArrayList<>(orderMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        List<Order> userOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getClientId() == clientId) userOrders.add(order);
        }
        return userOrders;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return new ArrayList<>(userMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Worker> getAllWorkers() {
        List<User> allUsers = null;
        try {
            allUsers = new ArrayList<>(userMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Worker> allWorkers = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getRole() == Role.WORKER) allWorkers.add((Worker)user);
        }
        return allWorkers;
    }

    @Override
    public List<Client> getAllClients() {
        List<User> allUsers = null;
        try {
            allUsers = new ArrayList<>(userMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Client> allClients = new ArrayList<>();
        for (User user : allClients) {
            if (user.getRole() == Role.CLIENT) allClients.add((Client)user);
        }
        return allClients;
    }

    @Override
    public List<Administrator> getAllAdministrators() {
        List<User> allUsers = null;
        try {
            allUsers = new ArrayList<>(userMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Administrator> allAdministrators = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getRole() == Role.ADMINISTRATOR) allAdministrators.add((Administrator) user);
        }
        return allAdministrators;
    }

    @Override
    public List<Order> getAllWorkerOrders(Worker worker) {
        List<Order> allOrders = null;
        try {
            allOrders = new ArrayList<>(orderMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        List<Order> workerOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (((Worker) findUserById(order.getWorkerId())).equals(worker)) workerOrders.add(order);
        }
        return workerOrders;
    }

    @Override
    public List<Order> getAllWorkerOrdersById(int workerId) {
        List<Order> allOrders = null;
        try {
            allOrders = new ArrayList<>(orderMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        List<Order> workerOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getWorkerId() == workerId) workerOrders.add(order);
        }
        return workerOrders;
    }

    @Override
    public void addNewUser(User newUser) {
        try {
            userMapper.addUser(newUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewTable(Table table) {
        try {
            tableMapper.addTable(table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewFood(Food food) {
        try {
            foodMapper.addFood(food);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewOrder(Order order) {
        try {
            Worker leastBusyWorker = (Worker) findUserById(findLeastBusyWorkerId());
            order.setWorkerId(leastBusyWorker.getId());
            leastBusyWorker.setOrdersTaken(leastBusyWorker.getOrdersTaken()+1);
            update(leastBusyWorker);
            orderMapper.addOrder(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewReservation(Reservation reservation) {
        try {
            reservationMapper.addReservation(reservation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object obj) {
        String name = obj.getClass().getSimpleName();
        if (name.equals("Administrator") || name.equals("Client") || name.equals("Worker")) {
            name = "User";
        }
        try {
            switch (name) {
                case "User":
                    User user = (User) obj;
                    userMapper.update(user);
                    break;
                case "Food":
                    Food food = (Food) obj;
                    foodMapper.update(food);
                    break;
                case "Table":
                    Table table = (Table) obj;
                    tableMapper.update(table);
                    break;
                case "Order":
                    Order order = (Order) obj;
                    orderMapper.update(order);
                    break;
                case "Reservation":
                    Reservation reservation = (Reservation) obj;
                    reservationMapper.update(reservation);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAll() {
        try {
            tableMapper.update();
            userMapper.update();
            foodMapper.update();
            orderMapper.update();
            reservationMapper.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearAll() {
        tableMapper.clear();
        userMapper.clear();
        foodMapper.clear();
        orderMapper.clear();
        reservationMapper.clear();
    }

    @Override
    public void dropAll() {
        try {
            DataGateway.getInstance().dropAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clearAll();
    }

    public int findLeastBusyWorkerId(){
        List<Worker> workers = getAllWorkers();
        int minimalOrdersTaken = workers.get(0).getOrdersTaken();
        int minimalOrdersWorkerId = workers.get(0).getId();
        for (Worker item : workers) {
             if (minimalOrdersTaken > item.getOrdersTaken()) {
                 minimalOrdersTaken = item.getOrdersTaken();
                 minimalOrdersWorkerId = item.getId();
             }
        }
        return minimalOrdersWorkerId;
    }
}
