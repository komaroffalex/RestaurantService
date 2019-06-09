package com.rservice.storage.mappers;

import com.rservice.businesslogic.entities.orders.Food;
import com.rservice.storage.DataGateway;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class FoodMapper implements Mapper<Food> {

    // COLUMNS = " id, food_name, cost ";
    private static Map<Integer, Food> loadedFoodMap = new HashMap<>();
    private Connection connection;

    public FoodMapper() throws SQLException {
        connection = DataGateway.getInstance().getDataSource().getConnection();
    }

    public void addFood(Food food) throws SQLException {
        if (loadedFoodMap.values().contains(food)) {
            update(food);
        } else {
            String insertSQL = "INSERT INTO food(food_name, cost) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, food.getName());
            preparedStatement.setFloat(2, food.getCost());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                food.setId(resultSet.getInt(1));
            }

            loadedFoodMap.put(food.getId(), food);
        }
    }

    @Override
    public Food findById(int id) throws SQLException {
        for (int loadedFoodId : loadedFoodMap.keySet()) {
            if (loadedFoodId == id)
                return loadedFoodMap.get(loadedFoodId);
        }

        // Book not found, extract from database
        String selectSQL = "SELECT * FROM food WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) return null;

        String name = resultSet.getString("food_name");
        float cost = resultSet.getFloat("cost");

        Food newFood = new Food(id, name, cost);

        loadedFoodMap.put(id, newFood);

        return newFood;
    }

    @Override
    public Map<Integer, Food> findAll() throws SQLException {
        Map<Integer, Food> allFood = new HashMap<>();

        String selectSQL = "SELECT id FROM food;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);

        while (resultSet.next()) {
            allFood.put(resultSet.getInt("id"), findById(resultSet.getInt("id")));
        }

        return allFood;
    }

    @Override
    public void update(Food item) throws SQLException {
        if (loadedFoodMap.values().contains(item)) {
            String updateSQL = "UPDATE food SET  food_name = ?, cost = ?  WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setFloat(2, item.getCost());
            preparedStatement.setInt(3, item.getId());
            preparedStatement.execute();

            loadedFoodMap.replace(item.getId(), item);
        } else {
            addFood(item);
        }
    }

    @Override
    public void update() throws SQLException {
        for (Food food : loadedFoodMap.values())
            update(food);
    }

    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public void clear() {
        loadedFoodMap.clear();
    }
}
