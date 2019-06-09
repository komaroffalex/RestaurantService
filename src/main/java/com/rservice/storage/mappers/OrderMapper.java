package com.rservice.storage.mappers;

import com.rservice.Util;
import com.rservice.businesslogic.Status;
import com.rservice.businesslogic.entities.orders.Food;
import com.rservice.businesslogic.entities.orders.Order;
import com.rservice.storage.DataGateway;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class OrderMapper implements Mapper<Order> {

    // COLUMNS = " id, deltime, food_ids, cost, address, status, worker_id, client_id ";
    private static Map<Integer, Order> loadedOrderMap = new HashMap<>();
    private Connection connection;

    private FoodMapper foodMapper;

    public OrderMapper() throws SQLException {
        connection = DataGateway.getInstance().getDataSource().getConnection();
        foodMapper = new FoodMapper();
    }

    public void addOrder(Order order) throws SQLException {
        if (loadedOrderMap.values().contains(order)) {
            update(order);
        } else {
            String insertSQL = "INSERT INTO orders(deltime, food_ids, cost, address, status, worker_id, client_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, Util.getStringFromFormattedDate(order.getTime()));
            preparedStatement.setString(2, order.getFoodAsStringId());
            preparedStatement.setFloat(3, order.getCost());
            preparedStatement.setString(4, order.getAddress());
            preparedStatement.setInt(5, order.getStatus().getStatusId());
            preparedStatement.setInt(6, order.getWorkerId());
            preparedStatement.setInt(7, order.getClientId());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                order.setId(resultSet.getInt(1));
            }

            loadedOrderMap.put(order.getId(), order);
        }
    }

    @Override
    public Order findById(int id) throws SQLException {
        for (int loadedOrderId : loadedOrderMap.keySet()) {
            if (loadedOrderId == id)
                return loadedOrderMap.get(loadedOrderId);
        }

        String selectSQL = "SELECT * FROM orders WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) return null;

        Date orderDate = Util.getDateFromFormattedString(resultSet.getString("deltime"));
        List<String> orderFoodStr = Arrays.asList(resultSet.getString("food_ids").split("\\s*,\\s*"));
        List<Food> orderFood = new ArrayList<Food>();
        for (String item : orderFoodStr) {
            orderFood.add(foodMapper.findById(Integer.valueOf(item)));
        }
        String orderAddress = resultSet.getString("address");
        int orderStatus = resultSet.getInt("status");
        int orderWorkerId = resultSet.getInt("worker_id");
        int orderClientId = resultSet.getInt("client_id");

        Order newOrder = new Order(id, orderDate, orderFood, orderAddress, Status.valueOf(orderStatus), orderWorkerId, orderClientId);

        loadedOrderMap.put(id, newOrder);

        return newOrder;
    }

    @Override
    public Map<Integer, Order> findAll() throws SQLException {
        Map<Integer, Order> allOrders = new HashMap<>();

        String selectSQL = "SELECT id FROM orders;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);

        while (resultSet.next()) {
            allOrders.put(resultSet.getInt("id"), findById(resultSet.getInt("id")));
        }

        return allOrders;
    }

    @Override
    public void update(Order item) throws SQLException {
        if (loadedOrderMap.values().contains(item)) {
            String updateSQL = "UPDATE orders SET  deltime = ?, food_ids = ?, cost = ?, address = ?, status = ?, worker_id = ?, client_id = ?  WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, Util.getStringFromFormattedDate(item.getTime()));
            preparedStatement.setString(2, item.getFoodAsStringId());
            preparedStatement.setFloat(3, item.getCost());
            preparedStatement.setString(4, item.getAddress());
            preparedStatement.setInt(5, item.getStatus().getStatusId());
            preparedStatement.setInt(6, item.getWorkerId());
            preparedStatement.setInt(7, item.getClientId());
            preparedStatement.setInt(8, item.getId());
            preparedStatement.execute();

        } else {
            addOrder(item);
        }
    }

    @Override
    public void update() throws SQLException {
        for (Order order : loadedOrderMap.values())
            update(order);
    }

    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public void clear() {
        loadedOrderMap.clear();
    }
}
