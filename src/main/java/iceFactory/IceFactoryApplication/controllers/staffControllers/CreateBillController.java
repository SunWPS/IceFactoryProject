package iceFactory.IceFactoryApplication.controllers.staffControllers;


import iceFactory.IceFactoryApplication.controllers.shareControllers.MainPopup;
import iceFactory.IceFactoryApplication.model.Bill;
import iceFactory.IceFactoryApplication.model.Customer;
import iceFactory.IceFactoryApplication.model.CustomerOrder;
import iceFactory.IceFactoryApplication.model.OrderItem;
import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.CreateBill;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CreateBillController {

    private AccountManagement accountManage;
    private IceFactoryAPIService service;
    private CustomerOrder selectedCustomerOrder;
    private List<CustomerOrder> customerOrderList;
    private List<OrderItem> itemsLists;
    private float totalPrice;

    @FXML private TableView<CustomerOrder> orderListTable;
    @FXML private TableColumn<CustomerOrder, String> timeColumn, customerColumn, customerTypeColumn;
    @FXML private TableColumn<Customer, UUID> orderIdColumn;
    @FXML private TableView<OrderItem> itemsTable;
    @FXML private TableColumn<OrderItem, String> productColumn;
    @FXML private TableColumn<OrderItem, Integer> quantityColumn;
    @FXML private TableColumn<OrderItem, Float> priceColumn, sumColumn;
    @FXML private Button createBillBtn;
    @FXML private Label orderLabel, nameLabel, totalLabel, typeLabel, errorLabel;


    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                customerOrderList = service.getCustomerOrderAll();
                showCustomerOrder();
            }
        });

        orderListTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null){
                createBillBtn.setDisable(false);
                CustomerOrder a = (CustomerOrder) newValue;
                selectedCustomerOrder = a;
                orderLabel.setText(selectedCustomerOrder.getOrderId().toString());
                nameLabel.setText(selectedCustomerOrder.getCustomerName());
                typeLabel.setText(selectedCustomerOrder.getCustomerType());
                itemsLists = selectedCustomerOrder.getOrderItemList();
                showItemsTable();
                totalPrice = getTotalPirce();
                totalLabel.setText("" + totalPrice);
                errorLabel.setText("");
            }
        }));
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

    @FXML
    public void handleCreateBillOnAction(ActionEvent event) throws IOException {
        if(selectedCustomerOrder != null) {
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String createDate = dtf.format(now);
            UUID billId = UUID.randomUUID();

            try {
                List<Bill> billList = service.getBillAll();
                boolean check = true;
                for(Bill bill : billList){
                    if(bill.getOrder().getOrderId().equals(selectedCustomerOrder.getOrderId())) {
                        CreateBill.createBill(bill.getOrder(), totalPrice, createDate, bill.getBillId().toString(), stage);
                        check = false;
                        break;
                    }
                }
                if(check) {
                    CreateBill.createBill(selectedCustomerOrder, totalPrice, createDate, billId.toString(), stage);
                    Bill bill = new Bill();
                    bill.setBillId(billId);
                    bill.setCreateDate(createDate);
                    bill.setOrder(selectedCustomerOrder);
                    bill.setTotalPrice(totalPrice);

                    service.addBill(bill);
                }

                Stage stage2 = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sharePages/free_popup.fxml"));
                stage2.setScene(new Scene(loader.load(), 487, 243));
                stage2.setTitle("Create Bill Finished");
                stage2.centerOnScreen();
                stage2.initModality(Modality.APPLICATION_MODAL);
                stage2.setResizable(false);
                MainPopup mainPopup = loader.getController();
                mainPopup.setShowText("???????????????????????????????????? PDF ???????????????????????????");
                stage2.showAndWait();

            } catch (Exception e) {
                //
            }
        } else {
            errorLabel.setText("???????????????????????????????????? order !!!");
        }

    }

    private void showCustomerOrder(){
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTypeColumn.setCellValueFactory(new PropertyValueFactory<>("customerType"));

        ArrayList<CustomerOrder> ordersThatNotPrepared = new ArrayList<>();
        for(CustomerOrder order : customerOrderList){
            if(order.getOrderStatus().equals(CustomerOrder.Status.WaitForDelivery.toString()))
                ordersThatNotPrepared.add(order);
        }
        List<CustomerOrder> sorted = (ArrayList<CustomerOrder>)ordersThatNotPrepared.clone();
        sorted.sort(Comparator.comparing(CustomerOrder::getOrderDate));
        ObservableList<CustomerOrder> customerOrdersData = FXCollections.observableList(sorted);
        orderListTable.setItems(customerOrdersData);
    }

    private void showItemsTable(){
        productColumn.setCellValueFactory(new PropertyValueFactory<>("pName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        sumColumn.setCellValueFactory(new PropertyValueFactory<>("sumPrice"));

        ObservableList<OrderItem> itemsData = FXCollections.observableList(itemsLists);
        itemsTable.setItems(itemsData);
        itemsTable.refresh();
    }

    private float getTotalPirce(){
        float total = 0;
        for(OrderItem item : itemsLists)
            total += item.getSumPrice();
        return total;
    }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
}
