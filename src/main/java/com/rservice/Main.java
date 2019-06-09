package com.rservice;

import com.rservice.businesslogic.exceptions.NotFoundException;
import com.rservice.gui.controllers.MainAdministratorViewController;
import com.rservice.gui.controllers.MainClientViewController;
import com.rservice.gui.controllers.MainWorkerViewController;
import com.rservice.gui.facades.CommonFacade;
import com.rservice.gui.facades.Facade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static final Facade FACADE = new CommonFacade();
    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setTitle("RService");

        showLogInDialog();
    }

    public static void showLogInDialog() {
        try {
            AnchorPane root = FXMLLoader.load(Main.class.getResource("/view/LogInDialog.fxml"));

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRegisterDialog() {
        try {
            AnchorPane root = FXMLLoader.load(Main.class.getResource("/view/RegistrationDialog.fxml"));

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMainView(int userId) {
        String userRole = "";
        try {
            userRole = FACADE.getUserRole(userId);
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
        switch (userRole) {
            case "Administrator":
                showMainAdministratorView(userId);
                break;
            case "Client":
                showMainClientView(userId);
                break;
            case "Worker":
                showMainWorkerView(userId);
                break;
            default:
                //TODO
        }
    }

    private static void showMainAdministratorView(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/MainAdministratorView.fxml"));
            AnchorPane root = loader.load();
            MainAdministratorViewController administratorViewController = loader.getController();
            administratorViewController.init(userId);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();    //TODO
        }
    }

    private static void showMainClientView(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/MainClientView.fxml"));
            AnchorPane root = loader.load();
            MainClientViewController clientViewController = loader.getController();
            clientViewController.init(userId);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();    //TODO
        }
    }

    private static void showMainWorkerView(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/MainWorkerView.fxml"));
            AnchorPane root = loader.load();
            MainWorkerViewController workerViewController = loader.getController();
            workerViewController.init(userId);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();    //TODO
        }
    }
}
