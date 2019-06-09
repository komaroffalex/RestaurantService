package com.rservice.gui.controllers;

import com.rservice.Main;
import com.rservice.Util;
import com.rservice.businesslogic.Role;
import com.rservice.businesslogic.Status;
import com.rservice.businesslogic.entities.orders.Food;
import com.rservice.businesslogic.entities.orders.Order;
import com.rservice.businesslogic.entities.orders.Reservation;
import com.rservice.businesslogic.entities.users.User;
import com.rservice.businesslogic.exceptions.RServiceAppException;
import com.rservice.businesslogic.exceptions.NotFoundException;
import com.rservice.gui.facades.Facade;
import com.rservice.gui.facades.ServiceFacade;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class MainAdministratorViewController {

    private Facade facade = Main.FACADE;
    private int currentAdministratorId;
    private String currentAdministratorLogin;
    private Object lastChosenRow = null;
    private ServiceFacade serviceFacade = new ServiceFacade();

    @FXML private Label currentAdministratorIdLabel;
    @FXML private Label currentAdministratorLoginLabel;
    @FXML private Label currentAdministratorNameLabel;

    @FXML private Label errorLabel;
    @FXML private TextField errorTextField;

    private ObservableList<Food> allFoodObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Food> allFoodTable1View;
    @FXML private TableColumn<Food, Integer> t1IdTableColumn;
    @FXML private TableColumn<Food, String> t1FoodNameTableColumn;
    @FXML private TableColumn<Food, String> t1CostTableColumn;

    private ObservableList<Reservation> allReservationsObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Reservation> reservationsTable2View;
    @FXML private TableColumn<Reservation, Integer> t2IdTableColumn;
    @FXML private TableColumn<Reservation, String> t2ResTimeTableColumn;
    @FXML private TableColumn<Reservation, String> t2PersonsTableColumn;
    @FXML private TableColumn<Reservation, String> t2TableLocationTableColumn;
    @FXML private TableColumn<Reservation, String> t2CostTableColumn;
    @FXML private TableColumn<Reservation, String> t2StatusTableColumn;
    @FXML private TableColumn<Reservation, String> t2ClientIdTableColumn;

    private ObservableList<Order> allOrdersObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Order> ordersTable3View;
    @FXML private TableColumn<Order, Integer> t3IdTableColumn;
    @FXML private TableColumn<Order, String> t3DeliveryTimeTableColumn;
    @FXML private TableColumn<Order, String> t3FoodTableColumn;
    @FXML private TableColumn<Order, String> t3CostTableColumn;
    @FXML private TableColumn<Order, String> t3AddressTableColumn;
    @FXML private TableColumn<Order, String> t3StatusTableColumn;
    @FXML private TableColumn<Order, String> t3WorkerIdTableColumn;
    @FXML private TableColumn<Order, String> t3ClientIdTableColumn1;

    private ObservableList<User> usersObservableList = FXCollections.observableArrayList();
    @FXML private TableView<User> usersTable4View;
    @FXML private TableColumn<User, Integer> t4IdTableColumn;
    @FXML private TableColumn<User, String>  t4LoginTableColumn;
    @FXML private TableColumn<User, String>  t4NameTableColumn;
    @FXML private TableColumn<User, String>  t4RoleTableColumn;

    @FXML private TextField loginTextField;
    @FXML private TextField nameTextField;
    @FXML private ChoiceBox<String> roleChoiceBox;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField repeatPasswordField;

    @FXML private TextField foodNameTextField;
    @FXML private TextField foodCostTextField;

    @FXML private TextField distanceTextField;

    private ObservableList<String> roles = FXCollections.observableArrayList(Role.ADMINISTRATOR.getRoleName(),
            Role.CLIENT.getRoleName(), Role.WORKER.getRoleName());

    public MainAdministratorViewController() {
    }

    @FXML
    private void initialize() {
    }

    public void init(int userId) {
        currentAdministratorId = userId;
        try {
            currentAdministratorLogin = facade.getUserLogin(userId);
            currentAdministratorIdLabel.setText(String.valueOf(currentAdministratorId));
            currentAdministratorLoginLabel.setText(String.valueOf(currentAdministratorLogin));
            currentAdministratorNameLabel.setText(facade.getUserName(userId));
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }

        setUpAllFoodTable();
        setUpAllReservationsTable();
        setUpAllOrdersTable();

        setUpUsersTable();

        allFoodTable1View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    lastChosenRow = newValue;
                });
        reservationsTable2View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    lastChosenRow = newValue;
                });
        ordersTable3View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    lastChosenRow = newValue;
                });
        usersTable4View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    lastChosenRow = newValue;
                });

        roleChoiceBox.setItems(roles);
        roleChoiceBox.setValue(Role.CLIENT.getRoleName());

        onClickRefreshButton();
    }

    private void setUpAllFoodTable() {
        t1IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t1FoodNameTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        t1CostTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCost())));
        try {
            allFoodObservableList.addAll(facade.getAllFood());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
            return;
        }
        allFoodTable1View.setItems(allFoodObservableList);
    }

    private void setUpAllReservationsTable() {
        t2IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t2ResTimeTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getTime())));
        t2PersonsTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getPersons())));
        t2TableLocationTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTable().getLocation()));
        t2CostTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCost())));
        t2StatusTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus().toString()));
        t2ClientIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getClientId())));

        allReservationsObservableList.addAll(facade.getAllReservations());

        reservationsTable2View.setItems(allReservationsObservableList);
    }

    private void setUpAllOrdersTable() {
        t3IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t3DeliveryTimeTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getTime())));
        t3FoodTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFoodAsStringNames()));
        t3CostTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCost())));
        t3AddressTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAddress()));
        t3StatusTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus().toString()));
        t3WorkerIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getWorkerId())));
        t3ClientIdTableColumn1.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getClientId())));

        allOrdersObservableList.addAll(facade.getAllOrders());

        ordersTable3View.setItems(allOrdersObservableList);
    }



    private void setUpUsersTable() {
        t4IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t4LoginTableColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        t4NameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        t4RoleTableColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        try {
            usersObservableList.addAll(facade.getAllUsers());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
        usersTable4View.setItems(usersObservableList);
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

    private void updateAllOrdersTableTable() {
        allOrdersObservableList.removeAll(allOrdersObservableList);
        allOrdersObservableList.addAll(facade.getAllOrders());
    }

    private void updateUsersTable() {
        usersObservableList.removeAll(usersObservableList);
        try {
            usersObservableList.addAll(facade.getAllUsers());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
    }

    @FXML
    public void onClickRefreshButton() {
        updateAllFoodTable();
        updateAllReservationsTable();
        updateAllOrdersTableTable();
        updateUsersTable();
    }

    @FXML
    public void onClickLogOutButton() {
        Main.showLogInDialog();
    }

    @FXML
    public void onClickRegisterNewUserButton() {
        String login, name, role, password, repeatPassword;
        login = loginTextField.getText();
        name = nameTextField.getText();
        role = roleChoiceBox.getValue();
        password = passwordField.getText();
        repeatPassword = repeatPasswordField.getText();

        if (login.isEmpty() || name.isEmpty() || role.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            errorTextField.setText("Input all information.");
            return;
        }

        if (!password.equals(repeatPassword)) {
            errorTextField.setText("The password must be the same in both fields.");
            return;
        }
        errorTextField.setText("User added.");
        facade.addNewUser(login, password, name, role);
    }

    @FXML
    public void onClickConfirmOrderButton() {
        try {
            facade.confirmOrder(lastChosenRow);
        } catch (RServiceAppException e) {
            errorTextField.setText(e.getMessage());
            return;
        }
        errorTextField.setText("Order confirmed!");
        onClickRefreshButton();
    }

    @FXML
    public void onClickDeclineOrderButton() {
        try {
            facade.declineOrder(lastChosenRow);
        } catch (RServiceAppException e) {
            errorTextField.setText(e.getMessage());
            return;
        }
        errorTextField.setText("Order declined!");
        onClickRefreshButton();
    }

    @FXML
    public void onClickConfirmReservationButton() {
        try {
            facade.confirmReservation(lastChosenRow);
        } catch (RServiceAppException e) {
            errorTextField.setText(e.getMessage());
            return;
        }
        errorTextField.setText("Reservation confirmed!");
        onClickRefreshButton();
    }

    @FXML
    public void onClickDeclineReservationButton() {
        try {
            facade.declineReservation(lastChosenRow);
        } catch (RServiceAppException e) {
            errorTextField.setText(e.getMessage());
            return;
        }
        errorTextField.setText("Reservation declined!");
        onClickRefreshButton();
    }

    @FXML
    public void onClickAddNewFoodButton() {
        String foodName;
        Float foodCost;
        foodName = foodNameTextField.getText();
        foodCost = Float.parseFloat(foodCostTextField.getText());

        if (foodName.isEmpty() || foodCost.isNaN()) {
            errorTextField.setText("Enter valid name or cost!");
            return;
        }

        errorTextField.setText("Food added.");
        facade.addNewFood(foodName, foodCost);
    }

    @FXML
    public void onClickDistanceButton() {
        if(lastChosenRow.getClass().getSimpleName().equals("Order")) {
            String distance = serviceFacade.searchGoogleMapDistance(((Order) lastChosenRow).getAddress());
            distanceTextField.setText(distance);
        }
        else {
            errorTextField.setText("You must choose order from the order table.");
        }
    }
}
