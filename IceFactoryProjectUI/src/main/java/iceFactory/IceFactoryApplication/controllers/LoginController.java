package iceFactory.IceFactoryApplication.controllers;

import iceFactory.IceFactoryApplication.controllers.adminControllers.AdmimPageController;
import iceFactory.IceFactoryApplication.controllers.staffControllers.StaffPageController;
import iceFactory.IceFactoryApplication.model.Owner;
import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.List;

//@Component
//@FxmlView("login.fxml")

@Controller
public class LoginController {

    @FXML private Button loginBtn;
    @FXML private RadioButton staffBtn;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private AccountManagement accountManage;

    @Autowired
    private IceFactoryAPIService service ;

    @FXML public  void initialize()  {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                accountManage = new AccountManagement();
                accountManage.setOwnerMapFromList(service.getOwnerAll());
                // accountManage.setStaffMapFromList(service.getStaffAll());
            }
        });

    }
    @FXML public void handleLoginBtnOnAction(ActionEvent event) throws IOException{
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        if(!staffBtn.isSelected()){
            try{
            accountManage.checkOwnerAccount(usernameField.getText(),passwordField.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminPages/admin_page.fxml"));
            stage.setScene(new Scene(loader.load(), 1354, 756));
            AdmimPageController adminPage = loader.getController();
            adminPage.setAccountManage(accountManage);
            adminPage.setService(service);
            stage.show();}
            catch (IllegalArgumentException e){
                errorLabel.setText(e.getMessage()); }
    }
        else
            try{
                accountManage.checkStaffAccount(usernameField.getText(),passwordField.getText());
                accountManage.getCurrentStaff().checkIn();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/staff_page.fxml"));
                stage.setScene(new Scene(loader.load(), 1354, 756));
                StaffPageController staffPage = loader.getController();
                staffPage.setAccountManage(accountManage);
                staffPage.setService(service);
                stage.show();
            }
            catch (IllegalArgumentException e){
                errorLabel.setText(e.getMessage()); }

    }



}
