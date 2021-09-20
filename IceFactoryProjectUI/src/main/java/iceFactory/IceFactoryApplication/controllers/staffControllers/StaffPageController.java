package iceFactory.IceFactoryApplication.controllers.staffControllers;

import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffPageController {
    @FXML private Label usernameLabel;
    @FXML private Button changePasswordBtn, logoutBtn;
    private AccountManagement accountManage;
    private IceFactoryAPIService service;


    @FXML public  void initialize()  {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                usernameLabel.setText(accountManage.getCurrentStaff().getUsername());
            }
        });


    }

    @FXML public void handleLogoutBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/login.fxml")
        );
        stage.setScene(new Scene(loader.load(), 1354, 756));
        stage.show();
    }

    @FXML public void handletChangePasswordBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/sharePages/change_password.fxml")
        );
        stage.setScene(new Scene(loader.load(), 1354, 756));
        stage.show();
    }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
}
