/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StoreOwnerControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import Backend.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class StoreOwnerReviewStocksController implements Initializable {

    @FXML
    private AnchorPane reviewStockRequestsPane;
    @FXML
    private Text pageTitle;
    @FXML
    private Button filterButton;
    @FXML
    private TableView<Medicine> stockRequestsTable;
    @FXML
    private TableColumn<Medicine, Integer> medicineIdColumn;
    @FXML
    private TableColumn<Medicine, String> nameColumn;
    @FXML
    private TableColumn<Medicine, Integer> quantityColumn;
    @FXML
    private TableColumn<Medicine, Double> priceColumn;
    @FXML
    private TableColumn<Medicine, LocalDate> manufactureDateColumn;
    @FXML
    private TableColumn<Medicine, LocalDate> expiryDateColumn;
    @FXML
    private TableColumn<Medicine, String> supplierNameColumn;
    @FXML
    private Button approveButton;
    @FXML
    private Button rejectButton;
    @FXML
    private TextField searchBar;
    
    Controller Cont = new Controller();
    @FXML
    private TableColumn<Medicine, String> StatusColumn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        try {
            LoadTable();
        } catch (SQLException ex) {
            Logger.getLogger(StoreOwnerReviewStocksController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void LoadTable() throws SQLException
    {
        
        ObservableList<Medicine> dummyMedicines = FXCollections.observableArrayList(Cont.getPendingMeds());
        
        ArrayList<Supplier> SupList = Cont.GetSupplier();
        
        medicineIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMedicineId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantity()));
        manufactureDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getManufactor()));
        expiryDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpiry()));
        supplierNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupplierName(SupList)));
        priceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
        StatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        stockRequestsTable.setItems(dummyMedicines);
    }
    
    @FXML
    private void approveRequest(ActionEvent event) throws SQLException 
    {
        Medicine Med =  stockRequestsTable.getSelectionModel().getSelectedItem();
        
        if(Med != null)
        Cont.ApproveMedicine(Med);
    
        LoadTable();
    }

    @FXML
    private void rejectRequest(ActionEvent event) throws SQLException 
    {
        Medicine Med =  stockRequestsTable.getSelectionModel().getSelectedItem();
        
        if(Med != null)
        Cont.RejectMedicine(Med);
        
        LoadTable();
    }

    @FXML
    private void FilterMedicine(ActionEvent event)
    {
        //KAL
    }
    
}
