package iceFactory.IceFactoryApplication.controllers.staffControllers.addOrderController;


import iceFactory.IceFactoryApplication.model.Customer;
import iceFactory.IceFactoryApplication.model.Staff;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class SelectCustomer {

    private IceFactoryAPIService service;
    private Customer selectedCustomer;
    private boolean check_selected;
    ObservableList<Customer> customersData;
    FilteredList<Customer> customersFilteredList;

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, UUID> customerId;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> typeColumn;
    @FXML private TableColumn<Customer, String> phoneColumn;
    @FXML private TableColumn<Customer, String> addressColumn;
    @FXML private Button selectBtn;
    @FXML private TextField nameSearch;

    @FXML public void initialize()  {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createCustomerListTable();
                check_selected = false;
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
        check_selected = true;
        stage.close();
    }

    public Customer sentSelectedCustomer(){
        return  selectedCustomer;
    }
    public boolean isCheck_selected() {return check_selected;}
    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
}
