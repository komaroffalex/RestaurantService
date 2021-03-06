package com.rservice.gui.controllers;

import com.rservice.Main;
import com.rservice.businesslogic.exceptions.LogInErrorException;
import com.rservice.gui.facades.Facade;
import com.rservice.gui.facades.ServiceFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInDialogController {

    private Facade facade = Main.FACADE;
    private ServiceFacade serviceFacade = Main.ServiceFACADE;

    @FXML private TextField loginTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private Label errorLabel;

    public LogInDialogController() {
    }

    @FXML
    private void initialize() {
        errorLabel.setText("");
    }

    @FXML
    private void onClickLogInButton() {
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        if (login.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Enter login and password.");
            return;
        }
        try {
            int userId = facade.logInUser(login, password);
            serviceFacade.setUserId(userId);
            Main.showMainView(userId);
        } catch (LogInErrorException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onClickRegisterButton() {
        Main.showRegisterDialog();
    }
}
