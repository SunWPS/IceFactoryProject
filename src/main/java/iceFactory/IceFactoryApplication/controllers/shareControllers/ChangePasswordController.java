package iceFactory.IceFactoryApplication.controllers.shareControllers;

import iceFactory.IceFactoryApplication.controllers.adminControllers.AdminPageController;
import iceFactory.IceFactoryApplication.controllers.staffControllers.StaffPageController;
import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePasswordController {

    private AccountManagement accountManage;
    private IceFactoryAPIService service;

    @FXML private PasswordField oldPassField,newPassField,newCfPassField;
    @FXML private Label errorLabel;


    @FXML
    public void handleBackBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader;

        if(accountManage.getCurrentOwner()!=null){
            loader = new FXMLLoader(getClass().getResource("/fxml/adminPages/admin_page.fxml"));
            stage.setScene(new Scene(loader.load(), 1354, 756));
            AdminPageController adminPageController = loader.getController();
            adminPageController.setAccountManage(accountManage);
            adminPageController.setService(service);
        }
        else{
            loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/staff_page.fxml"));
            stage.setScene(new Scene(loader.load(), 1354, 756));
            StaffPageController staffPageController = loader.getController();
            staffPageController.setAccountManage(accountManage);
            staffPageController.setService(service);
        }
        stage.show();
    }

    @FXML void handleSubmitBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sharePages/confirm_change_pass.fxml"));
        stage.setScene(new Scene(loader.load(), 487, 243));
        stage.setTitle("Confirm change password");
        stage.getIcons().add(new Image("/ImageAndIcon/etc/iceIcon.png"));
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        ConfirmChangePassController confirmChangePassController = loader.getController();
        confirmChangePassController.setAccountManage(accountManage);
        confirmChangePassController.setService(service);

        if(!oldPassField.getText().isEmpty()&&!newPassField.getText().isEmpty()&&!newCfPassField.getText().isEmpty()){
            if(accountManage.getCurrentOwner()!=null){
                if(accountManage.getCurrentOwner().getPassword().equals(oldPassField.getText())){
                    if(newPassField.getText().equals(newCfPassField.getText())){
                        confirmChangePassController.setNewPassword(newPassField.getText());
                        stage.showAndWait();}
                    else errorLabel.setText("Password ใหม่ไม่ตรงกัน");
                }
                else errorLabel.setText("กรอก Password ปัจจุบันไม่ถูกต้อง");
        }
        else if(accountManage.getCurrentStaff().getPassword().equals(oldPassField.getText())){
            if(newPassField.getText().equals(newCfPassField.getText())){
                confirmChangePassController.setNewPassword(newPassField.getText());
                stage.showAndWait();
                errorLabel.setText("");
                oldPassField.setText("");
                newPassField.setText("");
                newCfPassField.setText("");

            }
            else errorLabel.setText("Password ใหม่ไม่ตรงกัน");
        }
        else errorLabel.setText("กรอก Password ปัจจุบันไม่ถูกต้อง");
        }
        else errorLabel.setText("กรอกข้อมูลไม่ครบ");
    }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }
}
