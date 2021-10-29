package iceFactory.IceFactoryApplication.controllers;

import iceFactory.IceFactoryApplication.config.ComponentConfig;
import iceFactory.IceFactoryApplication.controllers.adminControllers.AdminPageController;
import iceFactory.IceFactoryApplication.controllers.staffControllers.StaffPageController;
import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;


import java.io.IOException;

//@Component
//@FxmlView("login.fxml")

@Component
public class LoginController {

    private AccountManagement accountManage;
    private IceFactoryAPIService service ;

    @FXML private RadioButton ownerBtn;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;


    @FXML public  void initialize()  {
        accountManage = new AccountManagement();
        ApplicationContext context = new AnnotationConfigApplicationContext(ComponentConfig.class);
        service = context.getBean(IceFactoryAPIService.class);
        accountManage.setOwnerMapFromList(service.getOwnerAll());
        accountManage.setStaffMapFromList(service.getStaffAll());
    }

    @FXML public void handleLoginBtnOnAction(ActionEvent event) throws IOException{
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        if(ownerBtn.isSelected()){
            try{
                accountManage.checkOwnerAccount(usernameField.getText(),passwordField.getText());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminPages/admin_page.fxml"));
                stage.setScene(new Scene(loader.load(), 1354, 756));
                AdminPageController adminPage = loader.getController();
                adminPage.setAccountManage(accountManage);
                adminPage.setService(service);
                    System.out.println(accountManage.getCurrentOwner().getUsername());
                stage.show();
            }
            catch (IllegalArgumentException e){
                errorLabel.setText(e.getMessage()); }
    }
        else
            try{
                accountManage.checkStaffAccount(usernameField.getText(),passwordField.getText());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staffPages/staff_page.fxml"));
                stage.setScene(new Scene(loader.load(), 1354, 756));
                StaffPageController staffPage = loader.getController();
                staffPage.setAccountManage(accountManage);
                staffPage.setService(service);
                System.out.println(accountManage.getCurrentStaff().getUsername());
                stage.show();
            }
            catch (IllegalArgumentException e){
                errorLabel.setText(e.getMessage());
            }
    }

}
