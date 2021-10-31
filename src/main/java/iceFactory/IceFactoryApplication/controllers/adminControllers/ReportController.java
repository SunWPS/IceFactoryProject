package iceFactory.IceFactoryApplication.controllers.adminControllers;

import iceFactory.IceFactoryApplication.controllers.shareControllers.MainPopupController;
import iceFactory.IceFactoryApplication.model.CustomerOrder;
import iceFactory.IceFactoryApplication.model.OrderItem;
import iceFactory.IceFactoryApplication.service.AccountManagement;
import iceFactory.IceFactoryApplication.service.CreateReport;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import iceFactory.IceFactoryApplication.service.SummaryOrderItems;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportController {

    private AccountManagement accountManage;
    private IceFactoryAPIService service;
    private List<CustomerOrder> orderList;
    private List<OrderItem> deliveryItems;
    private List<OrderItem> pickupItems;
    private List<OrderItem> summaryItems;
    private float dTotal, pTotal, sTotal;
    private String date;

    @FXML private TableView<OrderItem> dItemsTable, pItemsTable, sItemsTable;
    @FXML private TableColumn<OrderItem, String> dProductColumn, pProductColumn, sProductColumn;
    @FXML private TableColumn<OrderItem, Integer> dQuantityColumn, pQuantityColumn, sQuantityColumn;
    @FXML private TableColumn<OrderItem, Float> dPriceColumn, dSumColumn,  pPriceColumn, pSumColumn, sSumColumn;
    @FXML private DatePicker datePicker;
    @FXML private Label errorLabel, dTotalLabel, pTotalLabel, sTotalLabel;


    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                orderList = service.getCustomerOrderAll();
                date = "";
            }
        });
    }

    @FXML
    public void handleBackBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/adminPages/admin_page.fxml")
        );
        stage.setScene(new Scene(loader.load(), 1354, 756));
        AdminPageController adminPageController = loader.getController();
        adminPageController.setService(service);
        adminPageController.setAccountManage(accountManage);
        stage.show();
    }

    @FXML
    public void handleSearchBtnOnAction(ActionEvent event) throws IOException {
        try {
            DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate d = datePicker.getValue();
            date = dft.format(d);

            deliveryItems = SummaryOrderItems.summaryByTypeAndDate(orderList, "delivery", date);
            pickupItems = SummaryOrderItems.summaryByTypeAndDate(orderList, "pick up", date);
            summaryItems = SummaryOrderItems.summaryByDate(orderList, date);

            showDeliveryTable();
            showPickUpTable();
            showSummaryTable();

            dTotal = SummaryOrderItems.totalPrice(deliveryItems);
            pTotal = SummaryOrderItems.totalPrice(pickupItems);
            sTotal = SummaryOrderItems.totalSummaryPrice(summaryItems);

            dTotalLabel.setText("" + dTotal);
            pTotalLabel.setText("" + pTotal);
            sTotalLabel.setText("" + sTotal);
            errorLabel.setText("");

        }catch (NullPointerException e) {
            errorLabel.setText("กรุณาเลือกวันที่");
        }
    }

    @FXML
    public void handleSaveBtnOnAction(ActionEvent event) throws IOException {
        if (!date.equals("")){
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            try {
                CreateReport.createReport(deliveryItems, pickupItems, summaryItems, dTotal, pTotal, sTotal, date, stage);
    
                Stage stage2 = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sharePages/free_popup.fxml"));
                stage2.setScene(new Scene(loader.load(), 487, 243));
                stage2.setTitle("Save Report Finished");
                stage2.centerOnScreen();
                stage2.initModality(Modality.APPLICATION_MODAL);
                stage2.setResizable(false);
                MainPopupController mainPopupController = loader.getController();
                mainPopupController.setShowText("Save รายงาน เสร็จสิ้น");
                stage2.showAndWait();
    
            } catch (Exception e){
                //
            }
        } else {
            errorLabel.setText("กรุณาเลือกวันที่");
        }
    }

    private void showDeliveryTable(){
        dProductColumn.setCellValueFactory(new PropertyValueFactory<>("pName"));
        dQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        dPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        dSumColumn.setCellValueFactory(new PropertyValueFactory<>("sumPrice"));

        ObservableList<OrderItem> itemsData = FXCollections.observableList(deliveryItems);
        dItemsTable.setItems(itemsData);
        dItemsTable.refresh();
    }

    private void showPickUpTable(){
        pProductColumn.setCellValueFactory(new PropertyValueFactory<>("pName"));
        pQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        pPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        pSumColumn.setCellValueFactory(new PropertyValueFactory<>("sumPrice"));

        ObservableList<OrderItem> itemsData = FXCollections.observableList(pickupItems);
        pItemsTable.setItems(itemsData);
        pItemsTable.refresh();
    }

    private void showSummaryTable(){
        sProductColumn.setCellValueFactory(new PropertyValueFactory<>("pName"));
        sQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        sSumColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<OrderItem> itemsData = FXCollections.observableList(summaryItems);
        sItemsTable.setItems(itemsData);
        sItemsTable.refresh();
    }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

    public void setService(IceFactoryAPIService service) {
        this.service = service;
    }

}
