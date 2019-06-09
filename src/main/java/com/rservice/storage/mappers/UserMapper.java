package com.rservice.storage.mappers;

import com.rservice.businesslogic.Role;
import com.rservice.businesslogic.entities.orders.Order;
import com.rservice.businesslogic.entities.users.Administrator;
import com.rservice.businesslogic.entities.users.Client;
import com.rservice.businesslogic.entities.users.User;
import com.rservice.businesslogic.entities.users.Worker;
import com.rservice.storage.DataGateway;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapper implements Mapper<User> {

    // COLUMNS = " id, login, password, name, role ";
    private static Map<Integer, User> loadedUserMap = new HashMap<>();
    private Connection connection;

    private OrderMapper orderMapper;

    public UserMapper() throws SQLException {
        connection = DataGateway.getInstance().getDataSource().getConnection();
        orderMapper = new OrderMapper();
    }

    public void addUser(User user) throws SQLException {
        if (loadedUserMap.values().contains(user)) {
            update(user);
        } else {
            String insertSQL = "INSERT INTO user(login, user.password, user.name, role) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setInt(4, user.getRole().getRoleId());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }

            loadedUserMap.put(user.getId(), user);
        }
    }

    @Override
    public User findById(final int id) throws SQLException {
        for (int loadedUserId : loadedUserMap.keySet()) {
            if (loadedUserId == id)
                return loadedUserMap.get(loadedUserId);
        }

        // User not found, extract from database
        String selectSQL = "SELECT * FROM user WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) return null;

        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        String name = resultSet.getString("name");
        int role = resultSet.getInt("role");

        User newUser = getNewUserOnRole(id, login, password, name, role);

        loadedUserMap.put(id, newUser);

        return newUser;
    }

    public User findByLogin(final String login) throws SQLException {
        for (User loadedUser : loadedUserMap.values()) {
            if (loadedUser.getLogin().equals(login))
                return loadedUser;
        }

        // User not found, extract from database
        String selectSQL = "SELECT * FROM user WHERE login = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) return null;

        int id = resultSet.getInt("id");
        String password = resultSet.getString("password");
        String name = resultSet.getString("name");
        int role = resultSet.getInt("role");

        User newUser = getNewUserOnRole(id, login, password, name, role);

        loadedUserMap.put(id, newUser);

        return newUser;
    }

    @Override
    public Map<Integer, User> findAll() throws SQLException {
        Map<Integer, User> allUsers = new HashMap<>();

        String selectSQL = "SELECT id FROM user;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);

        while (resultSet.next()) {
            allUsers.put(resultSet.getInt("id"), findById(resultSet.getInt("id")));
        }

        return allUsers;
    }

    @Override
    public void update(User item) throws SQLException {
        if (loadedUserMap.values().contains(item)) {
            String updateSQL = "UPDATE user SET  user.password = ?, user.name = ?, role = ?  WHERE login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, item.getPassword());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setInt(3, item.getRole().getRoleId());
            preparedStatement.setString(4, item.getLogin());
            preparedStatement.execute();
            int realId = findByLogin(item.getLogin()).getId();
            item.setId(realId);
            loadedUserMap.replace(realId, item);
        } else {
            addUser(item);
        }
    }

    @Override
    public void update() throws SQLException {
        for (User user : loadedUserMap.values())
            update(user);
    }

    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public void clear() {
        loadedUserMap.clear();
    }

    private User getNewUserOnRole(int id, String login, String password, String name, int role) throws SQLException {
        switch (Role.valueOf(role)) {
            case ADMINISTRATOR:
                return new Administrator(id, login, password, name);
            case CLIENT:
                return new Client(id, login, password, name);
            case WORKER:
                Worker worker = new Worker(id, login, password, name,-1);
                worker.setOrdersTaken(getWorkerOrdersNum(worker));
                return worker;
            default:    // unreachable
                return new Client(id, login, password, name);
        }
    }

    private int getWorkerOrdersNum(Worker worker) throws SQLException {
        int numOrdersTaken = 0;
        Map<Integer, Order> allOrders = orderMapper.findAll();
        for (Map.Entry<Integer, Order> entry : allOrders.entrySet()) {
            if(entry.getValue().getWorkerId() == worker.getId()) numOrdersTaken++;
        }
        return numOrdersTaken;
    }
}
