package iceFactory.IceFactoryApplication.controllers.staffControllers;

import iceFactory.IceFactoryApplication.controllers.shareControllers.ChangePasswordController;
import iceFactory.IceFactoryApplication.controllers.staffControllers.addOrderController.AddOrderController;
import iceFactory.IceFactoryApplication.controllers.staffControllers.deliveredController.DeliveredController;
import iceFactory.IceFactoryApplication.controllers.staffControllers.prepareController.PrepareOrderController;
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
                accountManage.getCurrentStaff().checkIn();
                service.updateStaff(accountManage.getCurrentStaff());
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

    @FXML public void handletChangePasswordBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sharePages/change_password.fxml"));
        stage.setScene(new Scene(loader.load(), 1354, 756));
        ChangePasswordController changePasswordController = loader.getController();
        changePasswordController.setService(service);
        changePasswordController.setAccountManage(accountManage);
        stage.show();
    }

    @FXML public void handleAddOrderBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/addOrder/add_order.fxml"));
        stage.setScene(new Scene(loader.load(), 1354, 756));
        AddOrderController addOrderController = loader.getController();
        addOrderController.setService(service);
        addOrderController.setAccountManage(accountManage);
        stage.show();
    }

    @FXML public void handlePrepareBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/prepare/prepare_order.fxml"));
        stage.setScene(new Scene(loader.load(), 1354, 756));
        PrepareOrderController prepareOrderController = loader.getController();
        prepareOrderController.setService(service);
        prepareOrderController.setAccountManage(accountManage);
        stage.show();
    }

    @FXML public void handleCreateBillBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/create_bill.fxml"));
        stage.setScene(new Scene(loader.load(), 1354, 756));
        CreateBillController createBillController = loader.getController();
        createBillController.setService(service);
        createBillController.setAccountManage(accountManage);
        stage.show();
    }

    @FXML public void handleDeliveredBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/delivered/delivered.fxml"));
        stage.setScene(new Scene(loader.load(), 1354, 756));
        DeliveredController deliveredController = loader.getController();
        deliveredController.setService(service);
        deliveredController.setAccountManage(accountManage);
        stage.show();
    }

    @FXML public void handleCustomerListBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/customer_page.fxml"));
        stage.setScene(new Scene(loader.load(), 1354, 756));
        CustomerPage customerPage = loader.getController();
        customerPage.setService(service);
        customerPage.setAccountManage(accountManage);
        stage.show();
    }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
}
