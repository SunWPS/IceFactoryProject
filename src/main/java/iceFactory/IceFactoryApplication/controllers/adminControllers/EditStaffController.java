package iceFactory.IceFactoryApplication.controllers.adminControllers;

import iceFactory.IceFactoryApplication.controllers.shareControllers.MainPopup;
import iceFactory.IceFactoryApplication.model.Staff;
import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class EditStaffController {

    private AccountManagement accountManage;
    private IceFactoryAPIService service;
    private Staff staff;

    @FXML private Label editAccountErrorLabel;
    @FXML private TextField staffFnameTextField, staffLnameTextField, staffPhoneTextField;
    @FXML private TextArea staffAddressTextArea;
    @FXML private Button submitBtn;


    @FXML public void initialize() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                staffFnameTextField.setText(staff.getFirstName());
                staffLnameTextField.setText(staff.getLastName());
                staffPhoneTextField.setText(staff.getPhoneNumber());
                staffAddressTextArea.setText(staff.getAddress());
            }
        });
    }

    @FXML public void handleSubmitBtnOnAction(ActionEvent event){
        if (staffFnameTextField.getText().equals("") || staffLnameTextField.getText().equals("") ||
            staffPhoneTextField.getText().equals("") || staffAddressTextArea.getText().equals("")){
            editAccountErrorLabel.setText("กรอกข้อมูลไม่ครบ");
        } else {
            staff.setFirstName(staffFnameTextField.getText());
            staff.setLastName(staffLnameTextField.getText());
            staff.setPhoneNumber(staffPhoneTextField.getText());
            staff.setAddress(staffAddressTextArea.getText());
            service.updateStaff(staff);

            Stage stage2 = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sharePages/free_popup.fxml"));
            try {
                stage2.setScene(new Scene(loader.load(), 487, 243));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage2.setTitle("Edit Staff Finished");
            stage2.centerOnScreen();
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.setResizable(false);
            MainPopup mainPopup = loader.getController();
            mainPopup.setShowText("แก้ไข account พนักงานเสร็จสิ้น");
            stage2.showAndWait();

            accountManage.getStaffMap().remove(staff.getUsername());
            accountManage.getStaffMap().put(staff.getUsername(), staff);
            Stage stage = (Stage) submitBtn.getScene().getWindow();
            stage.close();
        }
    }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

}
