package iceFactory.IceFactoryApplication.controllers.shareControllers;

import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConfirmChangePassController {
    private AccountManagement accountManage;
    private IceFactoryAPIService service;
    private String newPassword;
    @FXML Button confirmBtn,cancelBtn;


    @FXML public void handleConfirmBtn(){
        if(accountManage.getCurrentOwner()!=null){
            accountManage.getCurrentOwner().setPassword(newPassword);
            service.updateOwner(accountManage.getCurrentOwner());
            Stage stage = (Stage) confirmBtn.getScene().getWindow();
            stage.close();
        }
        else{
            accountManage.getCurrentStaff().setPassword(newPassword);
            service.updateStaff(accountManage.getCurrentStaff());
            Stage stage = (Stage) confirmBtn.getScene().getWindow();
            stage.close();
        }
    }

    @FXML public void handleCancelBtn(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


}
