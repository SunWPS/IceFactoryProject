package iceFactory.IceFactoryApplication.controllers.shareControllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class FinishPopupController {

    private String orderId;

    @FXML private Label orderIdLabel;
    @FXML private Button okBtn;


    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                orderIdLabel.setText(orderId);
                orderIdLabel.setTextAlignment(TextAlignment.CENTER);
                orderIdLabel.setAlignment(Pos.CENTER);
            }
        });
    }

    @FXML
    public void handleOkBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
