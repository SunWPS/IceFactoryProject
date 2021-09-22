package iceFactory.IceFactoryApplication.controllers.adminControllers;

import iceFactory.IceFactoryApplication.controllers.shareControllers.ChangePasswordController;
import iceFactory.IceFactoryApplication.controllers.staffControllers.StaffPageController;
import iceFactory.IceFactoryApplication.model.Owner;
import iceFactory.IceFactoryApplication.model.Staff;
import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;


public class AdminPageController {
    @FXML private Label usernameLabel;
    @FXML private Button staffListBtn, changePasswordBtn, logoutBtn;

    private AccountManagement accountManage;
    private IceFactoryAPIService service;

    @FXML public  void initialize()  {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                usernameLabel.setText(accountManage.getCurrentOwner().getUsername());

            }
        });


    }



    @FXML public void handleLogoutBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        stage.setScene(new Scene(loader.load(), 1354, 756));
        stage.show();
    }

    @FXML public void handletStaffListBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminPages/staff_list_page.fxml"));
        stage.setScene(new Scene(loader.load(), 1354, 756));
        StaffListPageControllers staffListPageController = loader.getController();
        staffListPageController.setService(service);
        staffListPageController.setAccountManage(accountManage);
        stage.show();
    }

    @FXML public void handletChangePasswordBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sharePages/change_password.fxml"));
        stage.setScene(new Scene(loader.load(), 1354, 756));
        ChangePasswordController changePasswordController = loader.getController();
        changePasswordController.setAccountManage(accountManage);
        changePasswordController.setService(service);
        stage.show();
    }



    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
}
