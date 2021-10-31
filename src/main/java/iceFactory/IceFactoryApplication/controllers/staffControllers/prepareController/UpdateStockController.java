package iceFactory.IceFactoryApplication.controllers.staffControllers.prepareController;


import iceFactory.IceFactoryApplication.controllers.shareControllers.MainPopupController;
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

    @FXML private Button deleteBtn;
    @FXML private ComboBox<String> productComboBox;
    @FXML private TextField quantityTextField;
    @FXML private Label errorLabel;
    @FXML private TableView<Product>  addTable;
    @FXML private TableColumn<Product, String> productColumn;
    @FXML private TableColumn<Product, Integer> quantityColumn;


    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setUpComboBox();
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
            Product product = new Product();
            product.setPName((String) productComboBox.getValue());
            if(Integer.parseInt(quantityTextField.getText()) < 0){
                errorLabel.setText("จำนวนสินค้าไม่ถูกต้อง");
                return;
            }
            if(Integer.parseInt(quantityTextField.getText()) > 200){
                errorLabel.setText("จำนวสินค้ามากเกินไป");
                return;
            }

            boolean check  = true;
            for(Product i : currentAdd){
                if(i.getPName().equals( product.getPName())) {
                    if (i.getQuantity() + Integer.parseInt(quantityTextField.getText()) > 200) {
                        errorLabel.setText("จำนวสินค้ามากเกินไป");
                        return;
                    }
                    i.increaseStock(Integer.parseInt(quantityTextField.getText()));
                    errorLabel.setText("");
                    addTable.refresh();
                    check = false;
                }

            }
            if(check) {
            product.increaseStock(Integer.parseInt(quantityTextField.getText()));
            productColumn.setCellValueFactory(new PropertyValueFactory<>("pName"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

                addTable.getItems().add(product);
                currentAdd.add(product);
                errorLabel.setText("");
            }
        }
        catch (NumberFormatException e){
            errorLabel.setText("จำนวนสินค้าไม่ถูกต้อง");
        }
    }

    @FXML public void handleSubmitBtnOnAction(ActionEvent event) throws IOException {
        for(Product i :currentAdd){
            Product stock = service.getProductByPName(i.getPName());
            stock.increaseStock(i.getQuantity());
            service.updateProduct(stock);
        }
        if(currentAdd.isEmpty()){
            errorLabel.setText("กรุณาระบุสินค้าที่ผลิตเพิ่ม");
            return;
        }

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sharePages/free_popup.fxml"));
        stage.setScene(new Scene(loader.load(), 487, 243));
        stage.setTitle("Update Stock Finished");
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        MainPopupController mainPopupController = loader.getController();
        mainPopupController.setShowText("อัพเดท stock เสร็จสิ้น");
        stage.showAndWait();
        productComboBox.setValue("น้ำแข็งหลอดเล็ก");
        quantityTextField.setText("");
        addTable.getItems().clear();
        errorLabel.setText("");
    }

    @FXML public void handleDeleteBtnOnAction(ActionEvent event){
        currentAdd.remove(selectedProduct);
        addTable.getItems().remove(selectedProduct);
    }

    private void setUpComboBox(){
        List<Product> productList = service.getProductAll();
        for(Product product: productList){
            productComboBox.getItems().add(product.getPName());
        }
        productComboBox.setValue(productList.get(0).getPName());
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }

}
