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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class InventoryManagerExpiredMedController implements Initializable {

    @FXML
    private AnchorPane stockPane;
    @FXML
    private Text pageTitle;
    @FXML
    private TextField searchBar;
    @FXML
    private TableView<List<String>> stockTable;
    @FXML
    private TableColumn<List<String>, String> medicineIdColumn;
    @FXML
    private TableColumn<List<String>, String> medicineNameColumn;
    @FXML
    private TableColumn<List<String>, String> medicineTypeColumn;
    @FXML
    private TableColumn<List<String>, String> quantityColumn;
    @FXML
    private TableColumn<List<String>, String> PriceColumn;
    @FXML
    private TableColumn<List<String>, String> expiryDateColumn;
    @FXML
    private TableColumn<List<String>, String> SupName;
    @FXML
    private Button removeButton;
    
    Controller Cont = new Controller();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        try 
        {
            LoadTable(Cont.GetExpiredMedicine());
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(InventoryManagerExpiredMedController.class.getName()).log(Level.SEVERE, null, ex);
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
        quantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        medicineTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(8)));
        expiryDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        SupName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(10)));
        PriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));

        stockTable.getItems().clear();
        stockTable.setItems(medData);
    }

    @FXML
    private void removeMedicine(ActionEvent event) throws SQLException 
    {
        List<String> Med1 = stockTable.getSelectionModel().getSelectedItem();
        
        if(Med1 != null)
        {   
            int medicineId = Integer.parseInt(Med1.get(0));  
            int quantity = Integer.parseInt(Med1.get(1));    
            double price = Double.parseDouble(Med1.get(2));  
            LocalDate expiry = LocalDate.parse(Med1.get(3));  
            LocalDate manufactor = LocalDate.parse(Med1.get(4)); 
            String name = Med1.get(5);  
            String status = Med1.get(6);  
            String supplyStatus = Med1.get(7);  
            String type = Med1.get(8); 
            int supId = Integer.parseInt(Med1.get(9)); 
            
            Medicine Med = new Medicine(medicineId, quantity, price, expiry, manufactor, name, status, supplyStatus, type, supId);
           
            Cont.DeleteMedicine(Med);         
            LoadTable(Cont.GetExpiredMedicine());
        }
    }

    @FXML
    private void FilterMedicine(ActionEvent event) throws SQLException 
    {
        String filter = searchBar.getText();
        
        if(!filter.isEmpty() && !filter.contains(" "))
        {
            ArrayList<Medicine> MedList = Cont.GetExpiredMedicine();

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
            ArrayList<Medicine> Meds =new ArrayList<>(Cont.GetExpiredMedicine());

            LoadTable(Meds);
        }
    }
    
}
