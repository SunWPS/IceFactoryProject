package iceFactory.IceFactoryApplication.controllers.staffControllers;

import iceFactory.IceFactoryApplication.controllers.staffControllers.addOrderController.AddCustomerController;
import iceFactory.IceFactoryApplication.model.Customer;
import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CustomerPage {

    private AccountManagement accountManage;
    private IceFactoryAPIService service;
    private Customer selectedCustomer;
    ObservableList<Customer> customersData;
    FilteredList<Customer> customersFilteredList;

    @FXML private TableView customerTable;
    @FXML private TableColumn customerId;
    @FXML private TableColumn nameColumn;
    @FXML private TableColumn typeColumn;
    @FXML private TableColumn phoneColumn;
    @FXML private TableColumn addressColumn;
    @FXML private TextField nameSearch;
    @FXML private Button deleteBtn;

    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createCustomerListTable();
            }
        });

        deleteBtn.setDisable(true);

        customerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                deleteBtn.setDisable(false);
                Customer a = (Customer) newValue;
                selectedCustomer = a;
            }
        });
    }

    private void createCustomerListTable(){
        List<Customer> customerList = service.getCustomerAll();
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        customersData = FXCollections.observableList(customerList);
        customersFilteredList = new FilteredList<>(customersData, b->true);

        nameSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            customersFilteredList.setPredicate(customer ->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String data = newValue.toLowerCase();
                return customer.getName().contains(data);
            });
        }));

        customerTable.setItems(customersFilteredList);
    }


    @FXML
    public void handleBackBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/staff_page.fxml"));
        stage.setScene(new Scene(loader.load(), 1354, 756));
        StaffPageController staffPageController = loader.getController();
        staffPageController.setAccountManage(accountManage);
        staffPageController.setService(service);
        stage.show();
    }


    @FXML public void handleAddCustomerBtnOnAction(ActionEvent event) throws IOException {
        deleteBtn.setDisable(true);
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/addOrder/add_customer.fxml"));
        stage.setScene(new Scene(loader.load(), 935, 587));
        stage.setTitle("Add customer");
        stage.getIcons().add(new Image("/ImageAndIcon/etc/iceIcon.png"));
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        AddCustomerController addCustomerController = loader.getController();
        addCustomerController.setupBackBtn();
        addCustomerController.setService(service);
        stage.showAndWait();
        createCustomerListTable();
        customerTable.refresh();
    }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
}
