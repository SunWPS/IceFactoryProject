package iceFactory.IceFactoryApplication.controllers.staffControllers.deliveredController;

import iceFactory.IceFactoryApplication.controllers.staffControllers.StaffPageController;
import iceFactory.IceFactoryApplication.model.CustomerOrder;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DeliveredController {
    private AccountManagement accountManage;
    private IceFactoryAPIService service;
    private CustomerOrder selectedCustomerOrder;
    ObservableList<CustomerOrder> customerOrdersData;

    @FXML private TableView orderListTable;
    @FXML private TableColumn timeColumn, orderIdColumn, customerColumn, customerTypeColumn, phoneColumn, addressColumn;
    @FXML private Button deliveryBtn;
    @FXML private Label orderIdLaBel;

    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                showCustomerOrder();
            }
        });

        deliveryBtn.setDisable(true);

        orderListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                deliveryBtn.setDisable(false);
                CustomerOrder a = (CustomerOrder) newValue;
                selectedCustomerOrder =  a;
                orderIdLaBel.setText(selectedCustomerOrder.getOrderId().toString());
            }
        });
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

    @FXML public void handleDeliveryBtnOnAction(ActionEvent event) throws IOException{
        selectedCustomerOrder.changeStatus();
        service.updateCustomerOrder(selectedCustomerOrder);
        showCustomerOrder();
        orderIdLaBel.setText("");
        selectedCustomerOrder= null;

    }
    private void showCustomerOrder(){

        List<CustomerOrder> customerOrderList = service.getCustomerOrderAll();
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTypeColumn.setCellValueFactory(new PropertyValueFactory<>("customerType"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

        ArrayList<CustomerOrder> ordersThatNotPrepared = new ArrayList<>();
        for(CustomerOrder order : customerOrderList){
            if(order.getOrderStatus().equals(CustomerOrder.Status.WaitForDelivery.toString()))
                ordersThatNotPrepared.add(order);
        }
        List<CustomerOrder> sorted = (ArrayList<CustomerOrder>)ordersThatNotPrepared.clone();
        sorted.sort(Comparator.comparing(CustomerOrder::getOrderDate));
        customerOrdersData = FXCollections.observableList(sorted);
        orderListTable.setItems(customerOrdersData);
    }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
}
