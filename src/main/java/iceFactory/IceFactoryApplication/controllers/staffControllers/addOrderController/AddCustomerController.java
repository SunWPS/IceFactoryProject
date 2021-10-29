package iceFactory.IceFactoryApplication.controllers.staffControllers.addOrderController;


import iceFactory.IceFactoryApplication.model.Customer;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class AddCustomerController {

    private IceFactoryAPIService service;

    @FXML private Button backBtn;
    @FXML private TextField nameTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextArea addressTextArea;
    @FXML private Label errorLabel;
    @FXML private ComboBox<String> typeComboBox;


    @FXML public  void initialize()  {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                errorLabel.setDisable(true);
                setUpCustomerTypeComboBox();
            }
        });
    }

    @FXML
    public void handleBackBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/addOrder/select_customer.fxml"));
        stage.setScene(new Scene(loader.load(), 935, 587));
        stage.setTitle("Select customer");
        SelcectCustomer selcectCustomer = loader.getController();
        selcectCustomer.setService(service);
        stage.show();
    }

    @FXML
    public void handleSubmitBtnOnAction(ActionEvent event) throws IOException{
        List<Customer> customerList = service.getCustomerAll();
        Customer customer = new Customer();
        UUID customerId = UUID.randomUUID();
        customer.setCustomerId(customerId);
        //check duplicate name
        for(Customer i : customerList){
            if(i.getName().equalsIgnoreCase(nameTextField.getText())){
                errorLabel.setText("This name is already used.");
                break;
            }
        }

        customer.setPhoneNumber(phoneTextField.getText());
        if(!errorLabel.getText().equals("This name is already used.")){
            customer.setName(nameTextField.getText());
        }
        customer.setType((String) typeComboBox.getValue());
        customer.setAddress(addressTextArea.getText());
        service.addCustomer(customer);
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sharePages/add_finished.fxml"));
        stage.setScene(new Scene(loader.load(), 487, 243));
        stage.setTitle("Add customer finished");
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        nameTextField.setText("");
        phoneTextField.setText("");
        addressTextArea.setText("");
    }

    private void setUpCustomerTypeComboBox(){
        typeComboBox.getItems().addAll("pick up", "delivery");
        typeComboBox.setValue("pick up");
    }

    public void setupBackBtn(){
        backBtn.setVisible(false);
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }



}
