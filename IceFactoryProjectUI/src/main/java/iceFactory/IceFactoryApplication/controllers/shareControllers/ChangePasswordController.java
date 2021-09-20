package iceFactory.IceFactoryApplication.controllers.shareControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePasswordController {
    @FXML
    public void handleBackBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/adminPages/admin_page.fxml")
        );
        stage.setScene(new Scene(loader.load(), 1354, 756));
        stage.show();
    }

    @FXML void handleSubmitBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/sharePages/confirm_change_pass.fxml")
        );
        stage.setScene(new Scene(loader.load(), 487, 243));
        stage.setTitle("Confirm change password");
        stage.getIcons().add(new Image("/ImageAndIcon/etc/iceIcon.png"));
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        // load controller
        stage.setResizable(false);
        stage.showAndWait();
        // if finish
    }
}
