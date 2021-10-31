package iceFactory.IceFactoryApplication.controllers.adminControllers;

import iceFactory.IceFactoryApplication.controllers.shareControllers.MainPopupController;
import iceFactory.IceFactoryApplication.model.Staff;
import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddStaffController {

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

                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sharePages/free_popup.fxml"));
                stage.setScene(new Scene(loader.load(), 487, 243));
                stage.setTitle("Add Staff Account finished");
                stage.centerOnScreen();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                MainPopupController mainPopupController = loader.getController();
                mainPopupController.setShowText("เพิ่ม account พนักงานเสร็จสิ้น");
                stage.showAndWait();

                clearTextField();
            } catch(IllegalArgumentException e) {
                addAccountErrorLabel.setText(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else addAccountErrorLabel.setText("Password ไม่ตรงกัน");}
        else addAccountErrorLabel.setText("กรอกข้อมูลไม่ครบ");

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
