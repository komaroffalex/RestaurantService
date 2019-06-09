package com.rservice.storage.mappers;

import com.rservice.businesslogic.Status;
import com.rservice.businesslogic.entities.orders.Table;
import com.rservice.storage.DataGateway;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class TableMapper implements Mapper<Table> {

    // COLUMNS = " id, seats, location ";
    private static Map<Integer, Table> loadedTableMap = new HashMap<>();
    private Connection connection;

    public TableMapper() throws SQLException {
        connection = DataGateway.getInstance().getDataSource().getConnection();
    }

    public void addTable(Table table) throws SQLException {
        if (loadedTableMap.values().contains(table)) {
            update(table);
        } else {
            String insertSQL = "INSERT INTO tables(seats, location) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, table.getSeats());
            preparedStatement.setString(2, table.getLocation());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                table.setId(resultSet.getInt(1));
            }

            loadedTableMap.put(table.getId(), table);
        }
    }

    @Override
    public Table findById(int id) throws SQLException {
        for (int loadedTableId : loadedTableMap.keySet()) {
            if (loadedTableId == id)
                return loadedTableMap.get(loadedTableId);
        }

        // Book not found, extract from database
        String selectSQL = "SELECT * FROM tables WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) return null;

        int seats = resultSet.getInt("seats");
        String location = resultSet.getString("location");

        Table newTable = new Table(id, seats, location);

        loadedTableMap.put(id, newTable);

        return newTable;
    }

    @Override
    public Map<Integer, Table> findAll() throws SQLException {
        Map<Integer, Table> allTables = new HashMap<>();

        String selectSQL = "SELECT id FROM tables;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);

        while (resultSet.next()) {
            allTables.put(resultSet.getInt("id"), findById(resultSet.getInt("id")));
        }

        return allTables;
    }

    @Override
    public void update(Table item) throws SQLException {
        if (loadedTableMap.values().contains(item)) {
            String updateSQL = "UPDATE tables SET  seats = ?, location = ?  WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, item.getSeats());
            preparedStatement.setString(2, item.getLocation());
            preparedStatement.setInt(3, item.getId());
            preparedStatement.execute();

            loadedTableMap.replace(item.getId(), item);
        } else {
            addTable(item);
        }
    }

    @Override
    public void update() throws SQLException {
        for (Table table : loadedTableMap.values())
            update(table);
    }

    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public void clear() {
        loadedTableMap.clear();
    }
}
