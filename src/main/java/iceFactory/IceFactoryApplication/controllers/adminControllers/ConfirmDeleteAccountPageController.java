package iceFactory.IceFactoryApplication.controllers.adminControllers;

import iceFactory.IceFactoryApplication.model.Staff;
import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmDeleteAccountPageController {

    private AccountManagement accountManage;
    private IceFactoryAPIService service;
    private Staff selectedStaff;

    @FXML Button yesBtn,noBtn;
    @FXML Label accountNameLabel;


    @FXML public  void initialize()  {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                accountNameLabel.setText(selectedStaff.getUsername()+"?");
            }
        });
    }

    @FXML void handleYesBtn(ActionEvent event){
        service.deleteStaff(selectedStaff.getUsername());
        accountManage.getStaffMap().remove(selectedStaff.getUsername());
        Stage stage = (Stage) yesBtn.getScene().getWindow();
        stage.close();
    }

    @FXML void handleNoBtn(ActionEvent event){
        Stage stage = (Stage) noBtn.getScene().getWindow();
        stage.close();
    }



    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }

    public void setSelectedStaff(Staff selectedStaff) {
        this.selectedStaff = selectedStaff;
    }
}
