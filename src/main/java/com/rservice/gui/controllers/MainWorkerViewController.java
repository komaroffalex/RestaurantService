package com.rservice.gui.controllers;

import com.rservice.Main;
import com.rservice.Util;
import com.rservice.businesslogic.entities.orders.Food;
import com.rservice.businesslogic.entities.orders.Order;
import com.rservice.businesslogic.entities.orders.Reservation;
import com.rservice.businesslogic.exceptions.RServiceAppException;
import com.rservice.businesslogic.exceptions.NotFoundException;
import com.rservice.gui.facades.Facade;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainWorkerViewController {

    private Facade facade = Main.FACADE;
    private int currentWorkerId;
    private String currentWorkerLogin;
    private Object lastChosenRow = null;

    @FXML private Label currentWorkerIdLabel;
    @FXML private Label currentWorkerLoginLabel;
    @FXML private Label currentWorkerNameLabel;

    @FXML private TextField errorTextField;

    private ObservableList<Reservation> allReservationsObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Reservation> reservationsTable2View;
    @FXML private TableColumn<Reservation, Integer> t2IdTableColumn;
    @FXML private TableColumn<Reservation, String> t2ResTimeTableColumn;
    @FXML private TableColumn<Reservation, String> t2PersonsTableColumn;
    @FXML private TableColumn<Reservation, String> t2TableIdTableColumn;
    @FXML private TableColumn<Reservation, String> t2CostTableColumn;
    @FXML private TableColumn<Reservation, String> t2StatusTableColumn;
    @FXML private TableColumn<Reservation, String> t2ClientIdTableColumn;

    private ObservableList<Order> myOrdersObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Order> ordersTable3View;
    @FXML private TableColumn<Order, Integer> t3IdTableColumn;
    @FXML private TableColumn<Order, String> t3DelTimeTableColumn;
    @FXML private TableColumn<Order, String> t3FoodNameTableColumn;
    @FXML private TableColumn<Order, String> t3CostTableColumn;
    @FXML private TableColumn<Order, String> t3AddressTableColumn;
    @FXML private TableColumn<Order, String> t3StatusTableColumn;
    @FXML private TableColumn<Order, String> t3WorkerIdTableColumn;
    @FXML private TableColumn<Order, String> t3ClientIdTableColumn;

    private ObservableList<Food> allFoodObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Food> foodTable1View;
    @FXML private TableColumn<Food, Integer> t1IdTableColumn;
    @FXML private TableColumn<Food, String> t1FoodNameTableColumn;
    @FXML private TableColumn<Food, String> t1FoodCostTableColumn;

    public MainWorkerViewController() {
    }

    @FXML
    private void initialize() {
    }

    public void init(int userId) {
        currentWorkerId = userId;
        try {
            currentWorkerLogin = facade.getUserLogin(userId);
            currentWorkerIdLabel.setText(String.valueOf(currentWorkerId));
            currentWorkerLoginLabel.setText(String.valueOf(currentWorkerLogin));
            currentWorkerNameLabel.setText(facade.getUserName(userId));
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }

        setUpAllFoodTable();
        setUpAllReservationsTable();
        setUpMyOrdersTable();

        reservationsTable2View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    lastChosenRow = newValue;
                });
        ordersTable3View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    lastChosenRow = newValue;
                });
        foodTable1View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    lastChosenRow = newValue;
                });
        onClickRefreshButton();
    }

    private void setUpAllFoodTable() {
        t1IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t1FoodNameTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        t1FoodCostTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCost())));
        try {
            allFoodObservableList.addAll(facade.getAllFood());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
            return;
        }
        foodTable1View.setItems(allFoodObservableList);
    }

    private void setUpAllReservationsTable() {
        t2IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t2ResTimeTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getTime())));
        t2PersonsTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getPersons())));
        t2TableIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTable().getLocation()));
        t2CostTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCost())));
        t2StatusTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus().toString()));
        t2ClientIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getClientId())));

        allReservationsObservableList.addAll(facade.getAllUserReservations(currentWorkerId));

        reservationsTable2View.setItems(allReservationsObservableList);
    }

    private void setUpMyOrdersTable() {
        t3IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t3DelTimeTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getTime())));
        t3FoodNameTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFoodAsStringNames()));
        t3CostTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCost())));
        t3AddressTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAddress()));
        t3StatusTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus().toString()));
        t3WorkerIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getWorkerId())));
        t3ClientIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getClientId())));

        myOrdersObservableList.addAll(facade.getAllWorkerOrdersById(currentWorkerId));

        ordersTable3View.setItems(myOrdersObservableList);
    }

    private void updateAllFoodTable() {
        allFoodObservableList.removeAll(allFoodObservableList);
        try {
            allFoodObservableList.addAll(facade.getAllFood());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
    }

    private void updateAllReservationsTable() {
        allReservationsObservableList.removeAll(allReservationsObservableList);
        allReservationsObservableList.addAll(facade.getAllReservations());
    }

    private void updateMyOrdersTable() {
        myOrdersObservableList.removeAll(myOrdersObservableList);
        myOrdersObservableList.addAll(facade.getAllWorkerOrdersById(currentWorkerId));
    }

    @FXML
    public void onClickRefreshButton() {
        updateAllFoodTable();
        updateAllReservationsTable();
        updateMyOrdersTable();
    }

    @FXML
    public void onClickLogOutButton() {
        Main.showLogInDialog();
    }

    @FXML
    public void onClickFinishOrderButton() {
        try {
            facade.finishOrder(lastChosenRow);
        } catch (RServiceAppException e) {
            errorTextField.setText(e.getMessage());
            return;
        }
        errorTextField.setText("Order finished!");
        onClickRefreshButton();
    }

    @FXML
    public void onClickDeclineOrderButton() {
        try {
            facade.declineWorkerOrder(lastChosenRow);
        } catch (RServiceAppException e) {
            errorTextField.setText(e.getMessage());
            return;
        }
        errorTextField.setText("Order declined!");
        onClickRefreshButton();
    }
}
