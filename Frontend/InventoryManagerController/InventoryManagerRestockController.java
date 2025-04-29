/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryManagerController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import Backend.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class InventoryManagerRestockController implements Initializable {

    @FXML
    private AnchorPane restockPane;
    @FXML
    private Text pageTitle;
    @FXML
    private TextField searchBar;
    @FXML
    private TextField addQuantityField;
    @FXML
    private TableView<List<String>> restockTable;
    @FXML
    private TableColumn<List<String>, String> medicineIdColumn;
    @FXML
    private TableColumn<List<String>, String> medicineNameColumn;
    @FXML
    private TableColumn<List<String>, String> medicineTypeColumn;
    @FXML
    private TableColumn<List<String>, String> expiryDateColumn;
    @FXML
    private TableColumn<List<String>, String> currentQuantityColumn;
    @FXML
    private TableColumn<List<String>, String> restockQuantityColumn;
    @FXML
    private TableColumn<List<String>, String>  PriceColumn;
    @FXML
    private Button restockButton;
    
    Controller Cont = new Controller();
    @FXML
    private TableColumn<List<String>, String> StatusColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
        try {
            ArrayList<Medicine> MedList = Cont.getAddedMeds();
            ArrayList<Medicine> App = Cont.getApprovedMeds();

            MedList.addAll(App);
            
            LoadTable(MedList);
            
        } catch (SQLException ex) {
            Logger.getLogger(InventoryManagerRestockController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void LoadTable(ArrayList<Medicine> MedList) throws SQLException
    {
        
        ObservableList<List<String>> medData = FXCollections.observableArrayList();

        for (Medicine med : MedList) 
        {
            List<String> rowData = Arrays.asList(
                String.valueOf(med.getMedicineId()),      // 0 - MedicineId
                String.valueOf(med.getQuantity()),        // 1 - Quantity
                String.valueOf(med.getPrice()),           // 2 - Price
                med.getExpiry().toString(),               // 3 - Expiry
                med.getManufactor().toString(),           // 4 - Manufactor
                med.getName(),                            // 5 - Name
                med.getStatus(),                          // 6 - Status
                med.getSupplyStatus(),                    // 7 - SupplyStatus
                med.getType(),                            // 8 - Type
                String.valueOf(med.getSup()),             // 9 - SupId
                Cont.getSupName(med.getSup())            // 10 - SupName
            );
            medData.add(rowData);
        }

        medicineIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        medicineNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));
        currentQuantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        medicineTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(8)));
        expiryDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        restockQuantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(10)));
        PriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        StatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(7)));

        restockTable.getItems().clear();
        restockTable.setItems(medData);
    }
    
    @FXML
    private void FilterMedicine(ActionEvent event) throws SQLException
    {
         String filter = searchBar.getText();
        
        if(!filter.isEmpty() && !filter.contains(" "))
        {
            
            ArrayList<Medicine> MedList = Cont.getAddedMeds();
            ArrayList<Medicine> App = Cont.getApprovedMeds();

            MedList.addAll(App);

            ArrayList<Medicine> FilteredMedicines = new ArrayList<>();

            for(Medicine Med : MedList)
            {

                if (Med.getName().toLowerCase().contains(filter.toLowerCase()))
                {
                    FilteredMedicines.add(Med);
                }

            }
            
            LoadTable(FilteredMedicines);
        }
        else
        {
            
            ArrayList<Medicine> MedList = Cont.getAddedMeds();
            ArrayList<Medicine> App = Cont.getApprovedMeds();

            MedList.addAll(App);

            LoadTable(MedList);
        }
    }

    @FXML
    private void restockMedicine(ActionEvent event) throws SQLException 
    {
        if(restockTable.getSelectionModel().getSelectedItem() != null)
        {
            String AddQuan = addQuantityField.getText();

            if(AddQuan.isEmpty() || AddQuan.trim().isEmpty() || !AddQuan.matches("\\d+") || Integer.parseInt(AddQuan) > 100)
            {   
                showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please make sure:\n- Quantity is a number and atmost 100");
                return;
            }
            
            if(Integer.parseInt(AddQuan) <= 0)
            {
                showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please make sure:\n- Quantity is a number and greater 1");
                return;
            }
            
            if(restockTable.getSelectionModel().getSelectedItem().get(7).equalsIgnoreCase("Stopped"))
            {
                showAlert(Alert.AlertType.ERROR, "Restock Error", "The Supply of this medicine is stopped. Please contact the supplier");
                return;
            }
            if(Integer.parseInt(restockTable.getSelectionModel().getSelectedItem().get(1)) + Integer.parseInt(AddQuan) > 500)
            {
                showAlert(Alert.AlertType.ERROR, "Restock Error", "The Restock Quantity is exceeding the Maximum Limit of Quantity of Medicine");
                return;
            }
                
            int Quan = Integer.parseInt(AddQuan);

            Cont.RestockMedicine(Integer.parseInt(restockTable.getSelectionModel().getSelectedItem().get(0)),Quan);
            
            ArrayList<Medicine> MedList = Cont.getAddedMeds();
            ArrayList<Medicine> App = Cont.getApprovedMeds();

            MedList.addAll(App);

            LoadTable(MedList);
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) 
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
