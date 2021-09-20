package iceFactory.IceFactoryApplication.controllers.adminControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffListPageControllers {
    @FXML private Button backBtn;

    @FXML public void handleBackBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/adminPages/admin_page.fxml")
        );
        stage.setScene(new Scene(loader.load(), 1354, 756));
        stage.show();
    }

    @FXML void handleAddAccountBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/adminPages/add_account.fxml")
        );
        stage.setScene(new Scene(loader.load(), 935, 587));
        stage.setTitle("Add account");
        stage.getIcons().add(new Image("/ImageAndIcon/etc/iceIcon.png"));
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        // load controller
        stage.setResizable(false);
        stage.showAndWait();
        // if finish
    }
}
