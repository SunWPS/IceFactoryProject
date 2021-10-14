package iceFactory.IceFactoryApplication.controllers.staffControllers.addOrderController;

import iceFactory.IceFactoryApplication.controllers.staffControllers.StaffPageController;

import iceFactory.IceFactoryApplication.model.*;

import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AddOrderController {
    private AccountManagement accountManage;
    private IceFactoryAPIService service;

    private ArrayList<OrderItem> orderItemArrayList ;
    private Customer selectedCustomer;
    @FXML private ComboBox<String> productComboBox;
    @FXML private TextField quantityTextField;
    @FXML private TableView productTable;
    @FXML private TableColumn quantityColumn;
    @FXML private TableColumn productColumn;
    @FXML private Label addErrorLabel;
    @FXML private Label customerId;
    @FXML private Label customerName;



    //set up comboBox
    public void setSelectedCustomer(Customer selectedCustomer){
        this.selectedCustomer = selectedCustomer;
    }
    @FXML public  void initialize()  {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setUpComboBox();
                orderItemArrayList = new ArrayList<>();
            }
        });


    }
    public void setUpCustomerLabel(){
        try{
            customerId.setText(selectedCustomer.getCustomerId().toString());
            customerName.setText(selectedCustomer.getName());
        }
        catch (NullPointerException e){
            customerName.setVisible(false);
            customerId.setVisible(false);
        }
    }
    public void setUpComboBox(){

        productComboBox.getItems().addAll("น้ำแข็งหลอดเล็ก", "น้ำแข็งหลอดใหญ่", "น้ำแข็งป่น", "น้ำแข็งแพ็ค");
        productComboBox.setValue("น้ำแข็งหลอดเล็ก");
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

    @FXML public void handleSelectCustomerBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/addOrder/select_customer.fxml"));
        stage.setScene(new Scene(loader.load(), 935, 587));
        stage.setTitle("Select customer");
        stage.getIcons().add(new Image("/ImageAndIcon/etc/iceIcon.png"));
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        SelcectCustomer selectCustomer = loader.getController();
        selectCustomer.setService(service);

        stage.showAndWait();
        setSelectedCustomer(selectCustomer.sentSelectedCustomer());
        setUpCustomerLabel();
    }

    @FXML public void handleAddProductBtnOnAction(ActionEvent event) throws IOException {
        try {
                String pName = productComboBox.getValue();

                String quantity = quantityTextField.getText();


                OrderItem orderItem = new OrderItem();
                orderItem.setPName(pName);
                orderItem.setOrderQuantity(Integer.parseInt(quantity));
                orderItemArrayList.add(orderItem);
                quantityColumn.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
                productColumn.setCellValueFactory(new PropertyValueFactory<>("pName"));
                productTable.getItems().add(orderItem);
            } catch (IllegalArgumentException e) {
                addErrorLabel.setText("Please add quantity");
            }
        }


    @FXML public void handleSubmitBtnOnAction(ActionEvent event)  throws IOException{
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomer(selectedCustomer);
        customerOrder.setStaff(accountManage.getCurrentStaff());
        for(OrderItem i : orderItemArrayList)
            customerOrder.addOrder(i);
        service.addCustomerOrder(customerOrder);
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sharePages/add_finished.fxml"));
        stage.setScene(new Scene(loader.load(), 487, 243));
        stage.setTitle("Add order finished");
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        productTable.getItems().clear();
        orderItemArrayList = new ArrayList<>();
        quantityTextField.setText("");
        customerId.setText("");
        customerName.setText("");


    }


    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
}
