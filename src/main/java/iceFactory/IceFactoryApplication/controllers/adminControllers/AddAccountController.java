package iceFactory.IceFactoryApplication.controllers.adminControllers;

import iceFactory.IceFactoryApplication.model.Staff;
import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddAccountController {

    private AccountManagement accountManage;
    private IceFactoryAPIService service;

    @FXML private TextField staffUsernameTextField,staffFnameTextField,staffLnameTextField,staffPhoneTextField;
    @FXML private PasswordField staffPasswordPasswordField, staffConfirmPasswordPasswordField;
    @FXML private TextArea staffAddressTextArea;
    @FXML private Label addAccountErrorLabel;


    @FXML public void handleSubmitStaffAccountBtn(ActionEvent event){
        if(!isFieldsEmpty()){
        if(staffPasswordPasswordField.getText().equals(staffConfirmPasswordPasswordField.getText())) {
            try {
                Staff newStaff = accountManage.getCurrentOwner().createStaff(accountManage.getStaffMap(), staffUsernameTextField.getText(), staffPasswordPasswordField.getText(),
                        staffFnameTextField.getText(), staffLnameTextField.getText(), staffPhoneTextField.getText(),
                        staffAddressTextArea.getText());
                service.addStaff(newStaff);
                accountManage.getStaffMap().put(newStaff.getUsername(),newStaff);
                clearTextField();
            } catch(IllegalArgumentException e) {
                addAccountErrorLabel.setText(e.getMessage());
            }
        }
        else addAccountErrorLabel.setText("Password didn't match.");}
        else addAccountErrorLabel.setText("Some fields are empty.");

    }

    @FXML private void clearTextField(){
        staffUsernameTextField.setText("");
        staffFnameTextField.setText("");
        staffLnameTextField.setText("");
        staffPhoneTextField.setText("");
        staffAddressTextArea.setText("");
        staffPasswordPasswordField.setText("");
        staffConfirmPasswordPasswordField.setText("");
        addAccountErrorLabel.setText("");
    }

    @FXML private boolean isFieldsEmpty(){
        return staffUsernameTextField.getText().isEmpty() || staffFnameTextField.getText().isEmpty() || staffAddressTextArea.getText().isEmpty() ||
                staffLnameTextField.getText().isEmpty() || staffPasswordPasswordField.getText().isEmpty() || staffConfirmPasswordPasswordField.getText().isEmpty()
                || staffPhoneTextField.getText().isEmpty();
    }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
}
