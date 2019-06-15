package com.rservice.gui.controllers;

import com.rservice.Main;
import com.rservice.Util;
import com.rservice.businesslogic.Status;
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

public class MainClientViewController {

    private Facade facade = Main.FACADE;
    private int currentClientId;
    private String currentClientLogin;
    private Object lastChosenRow = null;

    @FXML private Label currentClientIdLabel;
    @FXML private Label currentClientLoginLabel;
    @FXML private Label currentClientNameLabel;

    @FXML private TextField errorTextField;

    private ObservableList<Reservation> myReservationsObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Reservation> reservationsTable1View;
    @FXML private TableColumn<Reservation, Integer> t1IdTableColumn;
    @FXML private TableColumn<Reservation, String> t1ResTimeTableColumn;
    @FXML private TableColumn<Reservation, String> t1PersonsTableColumn;
    @FXML private TableColumn<Reservation, String> t1TableLocationTableColumn;
    @FXML private TableColumn<Reservation, String> t1CostTableColumn;
    @FXML private TableColumn<Reservation, String> t1StatusTableColumn;
    @FXML private TableColumn<Reservation, String> t1ClientIdTableColumn;

    private ObservableList<Order> myOrdersObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Order> ordersTable2View;
    @FXML private TableColumn<Order, Integer> t2IdTableColumn;
    @FXML private TableColumn<Order, String> t2DelTimeTableColumn;
    @FXML private TableColumn<Order, String> t2FoodNameTableColumn;
    @FXML private TableColumn<Order, String> t2CostTableColumn;
    @FXML private TableColumn<Order, String> t2AddressTableColumn;
    @FXML private TableColumn<Order, String> t2StatusTableColumn;
    @FXML private TableColumn<Order, String> t2WorkerIdTableColumn;
    @FXML private TableColumn<Order, String> t2ClientIdTableColumn;

    private ObservableList<Food> availableFoodObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Food> foodTable3View;
    @FXML private TableColumn<Food, Integer> t3IdTableColumn;
    @FXML private TableColumn<Food, String> t3FoodNameTableColumn;
    @FXML private TableColumn<Food, String> t3PriceTableColumn;

    private ObservableList<Food> addedFoodObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Food> addedFoodTable4View;
    @FXML private TableColumn<Food, Integer> t4IdTableColumn;
    @FXML private TableColumn<Food, String> t4FoodNameTableColumn;
    @FXML private TableColumn<Food, String> t4PriceTableColumn;

    @FXML private TextField delTimeTextField;
    @FXML private TextField addressTextField;

    @FXML private TextField resTimeTextField;
    @FXML private TextField personsTextField;
    @FXML private TextField tableLocationTextField;

    public MainClientViewController() {
    }

    @FXML
    private void initialize() {
    }

    public void init(int userId) {
        currentClientId = userId;
        try {
            currentClientLogin = facade.getUserLogin(userId);
            currentClientIdLabel.setText(String.valueOf(currentClientId));
            currentClientLoginLabel.setText(String.valueOf(currentClientLogin));
            currentClientNameLabel.setText(facade.getUserName(userId));
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }

        setUpAvailableFoodTable();
        setUpCurrentOrderFoodTable();
        setUpMyReservationsTable();
        setUpMyOrdersTable();

        reservationsTable1View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    lastChosenRow = newValue;
                });
        ordersTable2View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    lastChosenRow = newValue;
                });
        foodTable3View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    lastChosenRow = newValue;
                });
        onClickRefreshButton();
    }

    private void setUpAvailableFoodTable() {
        t3IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t3FoodNameTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        t3PriceTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCost())));
        try {
            availableFoodObservableList.addAll(facade.getAllFood());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
            return;
        }
        foodTable3View.setItems(availableFoodObservableList);
    }

    private void setUpCurrentOrderFoodTable() {
        t4IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t4FoodNameTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        t4PriceTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCost())));
    }

    private void setUpMyReservationsTable() {
        t1IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t1ResTimeTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getTime())));
        t1PersonsTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getPersons())));
        t1TableLocationTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTable().getLocation()));
        t1CostTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCost())));
        t1StatusTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus().toString()));
        t1ClientIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getClientId())));

        myReservationsObservableList.addAll(facade.getAllUserReservations(currentClientId));

        reservationsTable1View.setItems(myReservationsObservableList);
    }

    private void setUpMyOrdersTable() {
        t2IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t2DelTimeTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getTime())));
        t2FoodNameTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFoodAsStringNames()));
        t2CostTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCost())));
        t2AddressTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAddress()));
        t2StatusTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus().toString()));
        t2WorkerIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getWorkerId())));
        t2ClientIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getClientId())));

        myOrdersObservableList.addAll(facade.getAllUserOrders(currentClientId));

        ordersTable2View.setItems(myOrdersObservableList);
    }

    private void updateAvailableFoodTable() {
        availableFoodObservableList.removeAll(availableFoodObservableList);
        try {
            availableFoodObservableList.addAll(facade.getAllFood());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO

        }
    }

    private void updateMyReservationsTable() {
        myReservationsObservableList.removeAll(myReservationsObservableList);

        myReservationsObservableList.addAll(facade.getAllClientReservations(currentClientId));
    }

    private void updateMyOrdersTable() {
        myOrdersObservableList.removeAll(myOrdersObservableList);
        myOrdersObservableList.addAll(facade.getAllClientOrders(currentClientId));
    }

    private void clearCurrentOrderFoodTable() {
        addedFoodObservableList.removeAll(addedFoodObservableList);
        //addedFoodTable4View.setItems(addedFoodObservableList);
    }

    @FXML
    public void onClickRefreshButton() {
        updateAvailableFoodTable();
        updateMyReservationsTable();
        updateMyOrdersTable();
        clearCurrentOrderFoodTable();
    }

    @FXML
    public void onClickLogOutButton() {
        Main.showLogInDialog();
    }

    @FXML
    public void onClickAddFoodToTheOrderButton() {
        if(lastChosenRow.getClass().getSimpleName().equals("Food")) {
            addedFoodObservableList.add((Food) lastChosenRow);
            addedFoodTable4View.setItems(addedFoodObservableList);
        }
        else {
            errorTextField.setText("You must select entry from the food table.");
            return;
        }
    }

    @FXML
    public void onClickAddOrderButton() {
        if(addedFoodObservableList.isEmpty() || delTimeTextField.getText().isEmpty() || addressTextField.getText().isEmpty()) {
            errorTextField.setText("You must fill all fields and select food.");
            return;
        }
        List<Food> orderedFood = new ArrayList<Food>();
        orderedFood.addAll(addedFoodObservableList);
        Date delTime = Util.getDateFromFormattedString(delTimeTextField.getText());
        String address = addressTextField.getText();

        //Order newOrder = new Order(-1,delTime,orderedFood,address, Status.SUBMITTED,-1,currentClientId);
        try {
            facade.addNewOrder(delTime,orderedFood,address);
        }
        catch (NotFoundException e) {
            errorTextField.setText(e.getMessage());
            return;
        }
        errorTextField.setText("Order succesfully added.");
        clearCurrentOrderFoodTable();
        delTimeTextField.clear();
        addressTextField.clear();
    }

    @FXML
    public void onClickAddReservationButton() {
        if(resTimeTextField.getText().isEmpty() || personsTextField.getText().isEmpty() || tableLocationTextField.getText().isEmpty()) {
            errorTextField.setText("You must fill all fields.");
            return;
        }
        Date resTime = Util.getDateFromFormattedString(resTimeTextField.getText());
        int persons = Integer.parseInt(personsTextField.getText());
        String tableLocation = tableLocationTextField.getText();

        try {
            facade.addNewReservation(resTime,persons,tableLocation);
        }
        catch (NotFoundException e) {
            errorTextField.setText(e.getMessage());
            return;
        }
        errorTextField.setText("Reservation succesfully added.");
        resTimeTextField.clear();
        personsTextField.clear();
        tableLocationTextField.clear();
    }

    @FXML
    public void onClickOrderRecieved() {
        try {
            facade.closeOrder(lastChosenRow);
        } catch (RServiceAppException e) {
            errorTextField.setText(e.getMessage());
            return;
        }
        errorTextField.setText("Order closed!");
        onClickRefreshButton();
    }
}
