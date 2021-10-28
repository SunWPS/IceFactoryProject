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
    private OrderItem selectedOrderItem;
    private ArrayList<OrderItem> orderItemArrayList ;
    private Customer selectedCustomer;
    @FXML private Button deleteBtn;
    @FXML private ComboBox<String> productComboBox;
    @FXML private TextField quantityTextField;
    @FXML private TableView productTable;
    @FXML private TableColumn quantityColumn;
    @FXML private TableColumn productColumn;
    @FXML private Label addErrorLabel,submitErrorLabel;
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
        productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                deleteBtn.setDisable(false);
                OrderItem a = (OrderItem) newValue;
                selectedOrderItem =  a;
            }
        });
    }

    public void setUpCustomerLabel(){
            customerId.setText(selectedCustomer.getCustomerId().toString());
            customerName.setText(selectedCustomer.getName());
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
        if(selectCustomer.isCheck_selected()) {
            setSelectedCustomer(selectCustomer.sentSelectedCustomer());
            setUpCustomerLabel();
        }
    }

    @FXML public void handleAddProductBtnOnAction(ActionEvent event) throws IOException {
        try {
                String pName = productComboBox.getValue();
                String quantity = quantityTextField.getText();


                    addErrorLabel.setText("");
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderQuantity(Integer.parseInt(quantity));
                    orderItem.setProduct(service.getProductByPName(pName));
                    boolean check = true;
                    if(Integer.parseInt(quantity) > 200){
                        addErrorLabel.setText("Order quantity is too much!");
                        return;
                    }
                    for(OrderItem i : orderItemArrayList){
                        if(i.getPName().equals(pName)){
                            if(i.getOrderQuantity() + Integer.parseInt(quantity) > 200){
                                addErrorLabel.setText("Order quantity is too much!");
                                return;
                            }
                                i.addOrderQuantity(Integer.parseInt(quantity));
                                check = false;
                                break;
                        }
                    }

                    if(check){
                        orderItemArrayList.add(orderItem);
                        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
                        productColumn.setCellValueFactory(new PropertyValueFactory<>("pName"));
                        productTable.getItems().add(orderItem);
                    }
                    productTable.refresh();



            } catch (IllegalArgumentException e) {
                addErrorLabel.setText("Please add quantity");
            }
        }


    @FXML public void handleSubmitBtnOnAction(ActionEvent event)  throws IOException{
        if (selectedCustomer == null ){
            submitErrorLabel.setText("Please select customer first.");
        }
        else if(orderItemArrayList.isEmpty()){submitErrorLabel.setText("Please select product first.");}
        else{
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomer(selectedCustomer);
        customerOrder.setStaff(accountManage.getCurrentStaff());
        customerOrder.timeStamp();
        customerOrder = service.addCustomerOrder(customerOrder);
            for(OrderItem i : orderItemArrayList){
                i.setCustomerOrder(customerOrder);
                i.setPrice();
                service.addOrderItem(i);
                }

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sharePages/add_finished.fxml"));
        stage.setScene(new Scene(loader.load(), 487, 243));
        stage.setTitle("Add order finished");
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        productTable.getItems().clear();
        orderItemArrayList.clear();
        quantityTextField.setText("");
        customerId.setText("");
        customerName.setText("");
        selectedCustomer=null;}
    }
    @FXML public void handleDeleteBtnOnAction(ActionEvent event){
        orderItemArrayList.remove(selectedOrderItem);
        productTable.getItems().remove(selectedOrderItem);

    }


    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
}
