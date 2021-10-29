package iceFactory.IceFactoryApplication.controllers.shareControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AddFinishedController {

    @FXML private Button okBtn;

    @FXML
    public void handleOkBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }

}
