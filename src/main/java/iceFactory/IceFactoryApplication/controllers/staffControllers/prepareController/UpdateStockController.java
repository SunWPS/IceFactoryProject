package iceFactory.IceFactoryApplication.controllers.staffControllers.prepareController;

import iceFactory.IceFactoryApplication.model.CustomerOrder;
import iceFactory.IceFactoryApplication.model.Product;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateStockController {
    private IceFactoryAPIService service;
    private ArrayList<Product> currentAdd;
    private Product selectedProduct;
    @FXML private Button addBtn;
    @FXML private Button submitBtn;
    @FXML private Button deleteBtn;
    @FXML private ComboBox productComboBox;
    @FXML private TextField quantityTextField;
    @FXML private Label errorLabel;
    @FXML private TableView  addTable;
    @FXML private TableColumn productColumn;
    @FXML private TableColumn quantityColumn;

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }

    public void setUpComboBox(){

        productComboBox.getItems().addAll("น้ำแข็งหลอดเล็ก", "น้ำแข็งหลอดใหญ่", "น้ำแข็งป่น", "น้ำแข็งแพ็ค");
        productComboBox.setValue("น้ำแข็งหลอดเล็ก");
    }
    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setUpComboBox();
                errorLabel.setVisible(false);
                currentAdd = new ArrayList<>();
            }
        });
        addTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                deleteBtn.setDisable(false);
                Product a = (Product) newValue;
                selectedProduct =  a;
            }
        });
    }
    @FXML public void handleAddBtnOnAction(ActionEvent event){
        try {
            Product product = new Product(); //fake object for show in table
            product.setPName((String) productComboBox.getValue());
            if(Integer.parseInt(quantityTextField.getText()) < 0){
                errorLabel.setText("Invalid Quantity");
                return;
            }
            product.increaseStock(Integer.parseInt(quantityTextField.getText()));
            productColumn.setCellValueFactory(new PropertyValueFactory<>("pName"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            addTable.getItems().add(product);
            currentAdd.add(product);
            errorLabel.setVisible(false);

        }
        catch (NumberFormatException e){
            errorLabel.setText("Invalid Quantity");
        }

    }
    @FXML public void handleSubmitBtnOnAction(ActionEvent event) throws IOException {
        for(Product i :currentAdd){
            Product stock = service.getProductByPName(i.getPName());
            stock.increaseStock(i.getQuantity());
            service.updateProduct(stock);
        }

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sharePages/add_finished.fxml"));
        stage.setScene(new Scene(loader.load(), 487, 243));
        stage.setTitle("Add product finished");
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }
    @FXML public void handleDeleteBtnOnAction(ActionEvent event){
        currentAdd.remove(selectedProduct);
        addTable.getItems().remove(selectedProduct);

    }

}
