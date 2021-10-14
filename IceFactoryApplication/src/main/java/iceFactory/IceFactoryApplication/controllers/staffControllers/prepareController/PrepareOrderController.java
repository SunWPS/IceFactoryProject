package iceFactory.IceFactoryApplication.controllers.staffControllers.prepareController;

import iceFactory.IceFactoryApplication.controllers.staffControllers.StaffPageController;
import iceFactory.IceFactoryApplication.model.Customer;
import iceFactory.IceFactoryApplication.model.CustomerOrder;
import iceFactory.IceFactoryApplication.model.Product;
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
import java.util.List;

public class PrepareOrderController {
    private AccountManagement accountManage;
    private IceFactoryAPIService service;
    private CustomerOrder selectedCustomerOrder;
    ObservableList<Product> productsData;
    ObservableList<CustomerOrder> customerOrdersData;

    @FXML private Button prepareFinishBtn;
    @FXML private TableView orderListTable, stockTable, orderProductTable;

    // oderListTable
    @FXML private TableColumn timeColumn, orderIdColumn, customerColumn, customerTypeColumn;

    // stockTable
    @FXML private TableColumn stockProductColumn, stockQuantityColumn;

    //orderProductTable
    @FXML private TableColumn productColumn, quantityColumn, missColumn;


    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                showProduct();
            }
        });

        prepareFinishBtn.setDisable(true);

        orderListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                prepareFinishBtn.setDisable(false);
                CustomerOrder a = (CustomerOrder) newValue;
                selectedCustomerOrder =  a;
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
        stage.showAndWait();
    }

    private void showCustomerOrder(){
//        List<CustomerOrder> customerOrderList = service.getCustomerOrderAll();
//        timeColumn.setCellValueFactory(new PropertyValueFactory<>(""));
//        orderIdColumn.setCellValueFactory();
//        customerColumn.setCellValueFactory();
//        customerTypeColumn.setCellValueFactory(new PropertyValueFactory<>(""));

    }

    private void showProduct(){
//        List<Product> productList = service.getProductAll();
//        productsData = FXCollections.observableList(productList);
//        System.out.println(productList.get(0).getPName());
//        stockProductColumn.setCellValueFactory(new PropertyValueFactory<>("pName"));
//        stockQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        stockTable.setItems(productsData);
    }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
}
