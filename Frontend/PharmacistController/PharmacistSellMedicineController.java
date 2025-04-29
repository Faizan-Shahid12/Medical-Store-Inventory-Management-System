/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PharmacistController;

import Backend.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class PharmacistSellMedicineController implements Initializable {

    @FXML
    private AnchorPane userManagementPane;
    @FXML
    private Text pageTitle;
    @FXML
    private TextField searchPatientField;
    @FXML
    private Text pageTitle1;
    @FXML
    private TableView<List<String>> medicineTable;
    @FXML
    private TableView<SoldMedicine> SelectTable;
    @FXML
    private TableColumn<SoldMedicine, Integer> medicineIdColumn;
    @FXML
    private TableColumn<SoldMedicine, String> medicineNameColumn;
    @FXML
    private TableColumn<SoldMedicine, Integer> quantityColumn;
    @FXML
    private TableColumn<SoldMedicine, String> medicineTypeColumn;
    @FXML
    private TableColumn<SoldMedicine, Double> PriceColumn;
    @FXML
    private TableColumn<SoldMedicine, LocalDate> expiryDateColumn;
    @FXML
    private TableColumn<SoldMedicine, String> statusColumn;
    @FXML
    private TableColumn<List<String>, String> medicineIdColumn1;
    @FXML
    private TableColumn<List<String>, String> medicineNameColumn1;
    @FXML
    private TableColumn<List<String>, String> quantityColumn1;
    @FXML
    private TableColumn<List<String>, String> medicineTypeColumn1;
    @FXML
    private TableColumn<List<String>, String> PriceColumn1;
    @FXML
    private TableColumn<List<String>, String> expiryDateColumn1;
    @FXML
    private TableColumn<List<String>, String> statusColumn1;
    @FXML
    private TableColumn<SoldMedicine, String> BuyQuantityColumn;

    Controller Cont = new Controller();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        try {
            LoadRequestTable(null);
            
            medicineIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMedicineId()).asObject());
            medicineNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
            medicineTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
            expiryDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getExpiry()));
            statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupName()));
            PriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        } catch (SQLException ex) {
            Logger.getLogger(PharmacistSellMedicineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void LoadRequestTable(ArrayList<Medicine> Meds) throws SQLException
    {
        if(Meds == null)
        {
            Meds = Cont.getAddedMeds();
        }
        
        ObservableList<List<String>> medData = FXCollections.observableArrayList();

        for (Medicine med : Meds) 
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

        medicineIdColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        medicineNameColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));
        quantityColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        medicineTypeColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(8)));
        expiryDateColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        statusColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(10)));
        PriceColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        
        medicineTable.setItems(medData);

    }
    
    
    @FXML
    private void handleSearch(ActionEvent event) throws SQLException 
    {
        String filter = searchPatientField.getText();
        
        if(!filter.isEmpty() && !filter.contains(" "))
        {
            ArrayList<Medicine> MedList = Cont.getAddedMeds();

            ArrayList<Medicine> FilteredMedicines = new ArrayList<>();

            for(Medicine Med : MedList)
            {

                if (Med.getName().toLowerCase().contains(filter.toLowerCase()))
                {
                    FilteredMedicines.add(Med);
                }

            }
            
            LoadRequestTable(FilteredMedicines);
        }
        else
        {
            LoadRequestTable(null);
        }
    }

    @FXML
    private void BuyMedicine(ActionEvent event) throws SQLException 
    {
        ArrayList<SoldMedicine> Meds = new ArrayList<>(SelectTable.getItems());
        Cont.SellMedicine(Meds);
        SelectTable.getItems().clear();
        LoadRequestTable(null);
    }
    
    
    ObservableList<SoldMedicine> medData1 = FXCollections.observableArrayList();
    
    @FXML
    private void ToSelectTable(MouseEvent event) 
    {
        if (event.getClickCount() == 2) 
        {
            List<String> Med1 = medicineTable.getSelectionModel().getSelectedItem();
  
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
            String SupName = Med1.get(10);
                    
            SoldMedicine Med = new SoldMedicine(medicineId, quantity, price, expiry, manufactor, name, status, supplyStatus, type, supId,SupName);
            
            boolean alreadyExists = false;
            for (SoldMedicine sm : medData1) 
            {
               if (sm.getMedicineId() == Med.getMedicineId()) 
               {
                   alreadyExists = true;
                   break;
               }
            }
            
            TextInputDialog dialog = new TextInputDialog("1");  // Default 1
            dialog.setTitle("Enter Quantity");
            dialog.setHeaderText("Enter the quantity you want to sell:");
            dialog.setContentText("Quantity:");

            Optional<String> result = dialog.showAndWait();
            
            int userQuantity = 0;
            
            if(result.isPresent())
            {
                userQuantity = Integer.parseInt(result.get());

                if (userQuantity <= 0)
                {
                    showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Quantity must be greater than 0");
                    return;
                }
                else if (userQuantity > 50)
                {
                    showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Quantity must be less than 50");
                    return;
                }
                
            }
            else
            {
                return;
            }
            
            if (!alreadyExists)
            {
                if(userQuantity <= quantity)
                {
                    Med.setQuantity(userQuantity);
                    medData1.add(Med);
                }
                else
                {
                    showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Quantity is greater than total Stock.");
                }
            }
            else
            {
                for (SoldMedicine sm : medData1) 
                {
                   if (sm.getMedicineId() == Med.getMedicineId()) 
                   {
                       sm.setQuantity(sm.getQuantity() + userQuantity);
                       
                       if(sm.getQuantity() > quantity)
                       {
                        sm.setQuantity(sm.getQuantity() - userQuantity);
                        
                        showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Quantity is greater than total Stock.");
                       }
                       
                       break;
                   }
                }
            }
            
            double Total = 0;
            
            for(SoldMedicine Med2: medData1)
            {
                Total += Cont.CalculateTotal(Med2);
            }
            
            pageTitle1.setText(String.format("%.2f", Total));
            
            SelectTable.setItems(medData1);
            SelectTable.refresh();
        }
    }

    @FXML
    private void ToMedicineTable(MouseEvent event) 
    {
        if (event.getClickCount() == 2) 
        {
            medData1.remove(SelectTable.getSelectionModel().getSelectedItem());
            SelectTable.refresh();
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
