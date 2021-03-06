package iceFactory.IceFactoryApplication.controllers.adminControllers;

import iceFactory.IceFactoryApplication.controllers.shareControllers.ConfirmDeleteAccountPageController;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffListPageControllers {

    private AccountManagement accountManage;
    private IceFactoryAPIService service;
    private Staff selectedStaff=null;

    @FXML private Button deleteAccountBtn,editBtn;
    @FXML private TableColumn<Staff,String> nameCol,lNameCol,usernameCol,phoneCol,addrCol,loginTimeCol;
    @FXML private TableView<Staff> staffTableView;


    @FXML public  void initialize()  {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createStaffListTable();
            }
        });
    }

    public void createStaffListTable(){
        ObservableList<Staff> staffObservableList = FXCollections.observableArrayList(accountManage.getStaffMap().values());
        nameCol.setCellValueFactory(new PropertyValueFactory<Staff,String>("firstName"));
        lNameCol.setCellValueFactory(new PropertyValueFactory<Staff,String>("lastName"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<Staff,String>("username"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Staff,String>("phoneNumber"));
        addrCol.setCellValueFactory(new PropertyValueFactory<Staff,String>("address"));
        loginTimeCol.setCellValueFactory(new PropertyValueFactory<Staff,String>("dateTime"));
        staffTableView.setItems(staffObservableList);
        deleteAccountBtn.setDisable(true);
        editBtn.setDisable(true);
        selectedStaff=null;
        staffTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Staff a = (Staff) newValue;
                selectedStaff=a;
                editBtn.setDisable(false);
                deleteAccountBtn.setDisable(false);
            }
        });
    }

    @FXML public void handleBackBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/adminPages/admin_page.fxml")
        );
        stage.setScene(new Scene(loader.load(), 1354, 756));
        AdminPageController adminPageController = loader.getController();
        adminPageController.setService(service);
        adminPageController.setAccountManage(accountManage);
        stage.show();
    }

    @FXML void handleAddAccountBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminPages/add_staff.fxml"));
        stage.setScene(new Scene(loader.load(), 935, 587));
        stage.setTitle("Add account");
        stage.getIcons().add(new Image("/ImageAndIcon/etc/iceIcon.png"));
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        AddStaffController addStaffController = loader.getController();
        addStaffController.setAccountManage(accountManage);
        addStaffController.setService(service);
        stage.showAndWait();
        createStaffListTable();
    }

    @FXML void handleDeleteAccountBtnOnAction(ActionEvent event)throws IOException{
        if(selectedStaff!=null){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sharePages/confirm_delete.fxml"));
            stage.setScene(new Scene(loader.load(), 468, 233));
            stage.setTitle("Delete account");
            stage.getIcons().add(new Image("/ImageAndIcon/etc/iceIcon.png"));
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            ConfirmDeleteAccountPageController confirmDeleteAccountPageController = loader.getController();
            confirmDeleteAccountPageController.setAccountManage(accountManage);
            confirmDeleteAccountPageController.setService(service);
            confirmDeleteAccountPageController.setSelectedStaff(selectedStaff);
            stage.showAndWait();
            createStaffListTable();
        }
    }

    @FXML public void handleEditBtnOnAction(ActionEvent event) throws IOException {
        if(selectedStaff!=null){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminPages/edit_staff.fxml"));
            stage.setScene(new Scene(loader.load(), 935, 587));
            stage.setTitle("Edit account");
            stage.getIcons().add(new Image("/ImageAndIcon/etc/iceIcon.png"));
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            EditStaffController editStaffController = loader.getController();
            editStaffController.setAccountManage(accountManage);
            editStaffController.setService(service);
            editStaffController.setStaff(selectedStaff);
            stage.showAndWait();
            staffTableView.refresh();
            createStaffListTable();
        }
    }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }

}
