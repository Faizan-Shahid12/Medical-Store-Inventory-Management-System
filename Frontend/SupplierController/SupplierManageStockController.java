/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SupplierController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import Backend.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class SupplierManageStockController implements Initializable {

    @FXML
    private AnchorPane manageStockPane;
    @FXML
    private Text pageTitle;
    @FXML
    private TextField searchMedicineField;
    @FXML
    private Button searchButton;
    @FXML
    private TableColumn<Medicine, String> nameColumn;
    @FXML
    private TableColumn<Medicine, String> typeColumn;
    @FXML
    private TableColumn<Medicine, Integer> quantityColumn;
    @FXML
    private TableColumn<Medicine, Double> priceColumn;
    @FXML
    private TableColumn<Medicine, LocalDate> mfgDateColumn;
    @FXML
    private TableColumn<Medicine, LocalDate> expDateColumn;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<Medicine> SupplyTable;
    @FXML
    private TableColumn<Medicine, String> expDateColumn1;
    @FXML
    private Button deleteButton1;
    
    private Controller Cont = new Controller();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        LoadTable();
    }    

    public void LoadTable()
    {
        ObservableList<Medicine> dummyMedicines = FXCollections.observableArrayList();
        
        for(Medicine Med: Cont.getSupMedicine())
        {
            if (!Med.getStatus().equalsIgnoreCase("Pending"))
            {
                dummyMedicines.add(Med);
            }
        }

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantity()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        expDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpiry()));
        expDateColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupplyStatus()));
        priceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
        mfgDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getManufactor()));

        SupplyTable.setItems(dummyMedicines);
    }
    
    @FXML
    private void handleSearch(ActionEvent event) 
    {
        String MedName = searchMedicineField.getText();
        
        ArrayList<Medicine> MedList = Cont.getSupMedicine();
        
        ObservableList<Medicine> MedList1 = FXCollections.observableArrayList();
        SupplyTable.getSelectionModel().clearSelection();
        
        for(Medicine Med : MedList)
        {
            if(Med.getName().contains(MedName))
            {
                MedList1.add(Med);
            }
        }
        
        SupplyTable.setItems(MedList1);
        
    }

    @FXML
    private void StopSupply(ActionEvent event) throws SQLException
    {
        Medicine Med = SupplyTable.getSelectionModel().getSelectedItem();
        
        if(Med != null)
        {
            Cont.StopSupply(Med);
        }
        
        SupplyTable.refresh();

    }

    @FXML
    private void StartSupply(ActionEvent event) throws SQLException 
    {
        Medicine Med = SupplyTable.getSelectionModel().getSelectedItem();
        
        if(Med != null)
        {
            Cont.StartSupply(Med);
        }
        SupplyTable.refresh(); 

    }
    
}
