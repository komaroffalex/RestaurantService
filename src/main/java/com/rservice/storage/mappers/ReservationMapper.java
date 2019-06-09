package com.rservice.storage.mappers;

import com.rservice.Util;
import com.rservice.businesslogic.Status;
import com.rservice.businesslogic.entities.orders.Reservation;
import com.rservice.businesslogic.entities.orders.Table;
import com.rservice.storage.DataGateway;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ReservationMapper implements Mapper<Reservation> {

    // COLUMNS = " id, restime, persons, table_id, status, client_id ";
    private static Map<Integer, Reservation> loadedReservationMap = new HashMap<>();
    private Connection connection;

    private TableMapper tableMapper;

    public ReservationMapper() throws SQLException {
        connection = DataGateway.getInstance().getDataSource().getConnection();
        tableMapper = new TableMapper();
    }

    public void addReservation(Reservation reservation) throws SQLException {
        if (loadedReservationMap.values().contains(reservation)) {
            update(reservation);
        } else {
            String insertSQL = "INSERT INTO reservations(restime, persons, table_id, status, client_id) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, Util.getStringFromFormattedDate(reservation.getTime()));
            preparedStatement.setInt(2, reservation.getPersons());
            preparedStatement.setInt(3, reservation.getTable().getId());
            preparedStatement.setInt(4, reservation.getStatus().getStatusId());
            preparedStatement.setInt(5, reservation.getClientId());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                reservation.setId(resultSet.getInt(1));
            }

            loadedReservationMap.put(reservation.getId(), reservation);
        }
    }

    @Override
    public Reservation findById(int id) throws SQLException {
        for (int loadedReservationId : loadedReservationMap.keySet()) {
            if (loadedReservationId == id)
                return loadedReservationMap.get(loadedReservationId);
        }

        String selectSQL = "SELECT * FROM reservations WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) return null;

        Date resDate = Util.getDateFromFormattedString(resultSet.getString("restime"));
        int resPersons = resultSet.getInt("persons");
        int tableId = resultSet.getInt("table_id");
        Status resStatus = Status.valueOf(resultSet.getInt("status"));
        int resClientId = resultSet.getInt("client_id");

        Table table = tableMapper.findById(tableId);
        Reservation newRes = new Reservation(id, resDate, resPersons, table, resStatus, resClientId);

        loadedReservationMap.put(id, newRes);

        return newRes;
    }

    @Override
    public Map<Integer, Reservation> findAll() throws SQLException {
        Map<Integer, Reservation> allReservations = new HashMap<>();

        String selectSQL = "SELECT id FROM reservations;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);

        while (resultSet.next()) {
            allReservations.put(resultSet.getInt("id"), findById(resultSet.getInt("id")));
        }

        return allReservations;
    }

    @Override
    public void update(Reservation item) throws SQLException {
        if (loadedReservationMap.values().contains(item)) {
            String updateSQL = "UPDATE reservations SET  restime = ?, persons = ?, table_id = ?, status = ?, client_id = ?  WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, Util.getStringFromFormattedDate(item.getTime()));
            preparedStatement.setInt(2, item.getPersons());
            preparedStatement.setInt(3, item.getTable().getId());
            preparedStatement.setInt(4, item.getStatus().getStatusId());
            preparedStatement.setInt(5, item.getClientId());
            preparedStatement.setInt(6, item.getId());
            preparedStatement.execute();

            tableMapper.update(item.getTable());
        } else {
            addReservation(item);
        }
    }

    @Override
    public void update() throws SQLException {
        tableMapper.update();
        for (Reservation order : loadedReservationMap.values())
            update(order);
    }

    @Override
    public void closeConnection() throws SQLException {
        tableMapper.closeConnection();
        connection.close();
    }

    @Override
    public void clear() {
        tableMapper.clear();
        loadedReservationMap.clear();
    }
}
