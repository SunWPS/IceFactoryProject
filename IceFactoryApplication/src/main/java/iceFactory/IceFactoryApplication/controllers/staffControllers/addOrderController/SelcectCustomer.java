package iceFactory.IceFactoryApplication.controllers.staffControllers.addOrderController;


import iceFactory.IceFactoryApplication.model.Customer;
import iceFactory.IceFactoryApplication.model.Staff;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SelcectCustomer {

    private IceFactoryAPIService service;
    private Customer selectedCustomer;
    @FXML private TableView customerTable;
    @FXML private TableColumn customerId;
    @FXML private TableColumn nameColumn;
    @FXML private TableColumn typeColumn;
    @FXML private TableColumn phoneColumn;
    @FXML private Button selectBtn;

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
    @FXML public  void initialize()  {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createCustomerListTable();
            }
        });

        selectBtn.setDisable(true);

        customerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectBtn.setDisable(false);
                Customer a = (Customer) newValue;
                selectedCustomer = a;
            }
        });

    }


    public void createCustomerListTable(){
        List<Customer> customerList = service.getCustomerAll();
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        for(Customer i : customerList)
         customerTable.getItems().add(i);




    }

    @FXML
    public void handleAddCustomerBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/addOrder/add_customer.fxml"));
        stage.setScene(new Scene(loader.load(), 935, 587));
        stage.setTitle("Add customer");
        AddCustomerController addCustomerController= loader.getController();
        addCustomerController.setService(service);
        stage.show();
    }
    @FXML
    public void handleSelectBtnOnAction(ActionEvent event) throws IOException{
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        stage.close();
    }

    public Customer sentSelectedCustomer(){
        return  selectedCustomer;
    }
}
