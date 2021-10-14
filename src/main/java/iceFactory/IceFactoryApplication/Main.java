package iceFactory.IceFactoryApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("Ice factory");
        primaryStage.setScene(new Scene(root, 1354, 756));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/ImageAndIcon/etc/iceIcon.png"));
        primaryStage.show();
    }


    public static void main(String[] args) { launch(args); }
}
