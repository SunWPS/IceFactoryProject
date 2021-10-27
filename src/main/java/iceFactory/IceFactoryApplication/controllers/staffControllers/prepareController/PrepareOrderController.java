package iceFactory.IceFactoryApplication.controllers.staffControllers.prepareController;

import iceFactory.IceFactoryApplication.controllers.staffControllers.StaffPageController;
import iceFactory.IceFactoryApplication.model.Customer;
import iceFactory.IceFactoryApplication.model.CustomerOrder;
import iceFactory.IceFactoryApplication.model.OrderItem;
import iceFactory.IceFactoryApplication.model.Product;
import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;

public class PrepareOrderController {
    private AccountManagement accountManage;
    private IceFactoryAPIService service;
    private CustomerOrder selectedCustomerOrder;
    ObservableList<Product> productsData;
    ObservableList<CustomerOrder> customerOrdersData;
    @FXML private Label orderIdLabel;

    @FXML private Button prepareFinishBtn;
    @FXML private TableView orderListTable, stockTable, orderProductTable;

    // oderListTable
    @FXML private TableColumn timeColumn, orderIdColumn, customerColumn, customerTypeColumn;

    // stockTable
    @FXML private TableColumn stockProductColumn, stockQuantityColumn;

    //orderProductTable
    @FXML private TableColumn productColumn, quantityColumn, missColumn;

    @FXML private Label needMoreLabel;

    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                showProduct();
                showCustomerOrder();

            }
        });

        prepareFinishBtn.setDisable(true);

        orderListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                prepareFinishBtn.setDisable(false);
                CustomerOrder a = (CustomerOrder) newValue;
                selectedCustomerOrder =  a;
                setupOrderProductTable();
                orderIdLabel.setText(a.getOrderId().toString());
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

    @FXML public void handleUpdateStockBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/prepare/update_stock.fxml"));
        stage.setScene(new Scene(loader.load(), 935, 587));
        stage.setTitle("Updata Stock");
        stage.getIcons().add(new Image("/ImageAndIcon/etc/iceIcon.png"));
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        UpdateStockController updateStockController = loader.getController();
        updateStockController.setService(service);
        stage.showAndWait();
        showProduct();
    }

    @FXML public void handlePrepareFinishBtnOnAction(ActionEvent event)throws IOException{
        try{
        selectedCustomerOrder.PrepareOrder();
        service.updateCustomerOrder(selectedCustomerOrder);
        for(OrderItem i : selectedCustomerOrder.getOrderItemList())
            service.updateProduct(i.getProduct());
        }
        catch (IllegalArgumentException e){
            needMoreLabel.setText("Need More Item");
        }
        finally {
            selectedCustomerOrder = null;
        }
        showCustomerOrder();
        showProduct();
        orderIdLabel.setText("");
        orderProductTable.getItems().clear();

    }


    private void showCustomerOrder(){

            List<CustomerOrder> customerOrderList = service.getCustomerOrderAll();

            timeColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
            orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerTypeColumn.setCellValueFactory(new PropertyValueFactory<>("customerType"));

            ArrayList<CustomerOrder> ordersThatNotPrepared = new ArrayList<>();
            for(CustomerOrder order : customerOrderList){
                if(order.getOrderStatus().equals(CustomerOrder.Status.PrepareProduct.toString()))
                    ordersThatNotPrepared.add(order);
            }
            List<CustomerOrder> sorted = (ArrayList<CustomerOrder>)ordersThatNotPrepared.clone();
            sorted.sort(Comparator.comparing(CustomerOrder::getOrderDate));
            customerOrdersData = FXCollections.observableList(sorted);
            orderListTable.setItems(customerOrdersData);


    }

    private void showProduct(){
        List<Product> productList = service.getProductAll();
        productsData = FXCollections.observableList(productList);
        stockProductColumn.setCellValueFactory(new PropertyValueFactory<>("pName"));
        stockQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        stockTable.setItems(productsData);
    }

    public void setupOrderProductTable(){
        productColumn.setCellValueFactory(new PropertyValueFactory<>("pName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        missColumn.setCellValueFactory(new PropertyValueFactory<>("missing"));

        List<OrderItem> itemsList = selectedCustomerOrder.getOrderItemList();
        ObservableList<OrderItem> itemsData = FXCollections.observableList(itemsList);
        orderProductTable.setItems(itemsData);
        orderProductTable.refresh();
    }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
}
