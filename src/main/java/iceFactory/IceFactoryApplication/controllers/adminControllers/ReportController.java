package iceFactory.IceFactoryApplication.controllers.adminControllers;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReportController {
    private AccountManagement accountManage;
    private IceFactoryAPIService service;
    private CustomerOrder selectedCustomerOrder;
    ObservableList<CustomerOrder> customerOrdersData;

    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                showCustomerOrder();
            }
        });


        orderListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                CustomerOrder a = (CustomerOrder) newValue;
                selectedCustomerOrder =  a;
            }
        });
    }

    @FXML private TableView orderListTable;
    @FXML private TableColumn timeColumn, orderIdColumn, customerColumn, customerTypeColumn, phoneColumn, addressColumn;

    @FXML
    public void handleBackBtnOnAction(ActionEvent event) throws IOException {
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
            if(order.getOrderStatus().equals(CustomerOrder.Status.CloseOrder.toString()))
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
