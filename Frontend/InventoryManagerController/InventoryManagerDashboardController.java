package InventoryManagerController;

import Backend.Medicine;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Database.*;
import Backend.Controller;
import java.sql.SQLException;

public class InventoryManagerDashboardController implements Initializable {

    @FXML
    public BorderPane rootPane;
    @FXML
    public AnchorPane sidePanel;
    @FXML
    public Button manageMedicinesBtn;
    @FXML
    public Button viewReportsBtn;
    @FXML
    public Button restockMedicinesBtn;
    @FXML
    public Button expiredMedicinesBtn;
    @FXML
    public Button logoutButton;
    @FXML
    public Text welcome;
    @FXML
    public Text name;
    @FXML
    public Text userRole;
    @FXML
    public AnchorPane mainContent;
    @FXML
    public Pane MedicinePane;
    @FXML
    public TableView<List<String>> requestTable;
    @FXML
    public TableColumn<List<String>, String> medicineIdColumn;
    @FXML
    public TableColumn<List<String>, String> medicineNameColumn;
    @FXML
    public TableColumn<List<String>, String> quantityColumn;
    @FXML
    public TableColumn<List<String>, String> medicineTypeColumn;
    @FXML
    public TableColumn<List<String>, String> expiryDateColumn;
    @FXML
    public Text pageTitle;
    @FXML
    public Button addButton;
    @FXML
    public Text pageTitle1;
    @FXML
    public TextField searchBar;
    @FXML
    public TableView<List<String>> medicineTable;
    @FXML
    public TableColumn<List<String>, String> statusColumn;
    @FXML
    public TableColumn<List<String>, String> medicineIdColumn1;
    @FXML
    public TableColumn<List<String>, String> medicineNameColumn1;
    @FXML
    public TableColumn<List<String>, String> quantityColumn1;
    @FXML
    public TableColumn<List<String>, String> medicineTypeColumn1;
    @FXML
    public TableColumn<List<String>, String> expiryDateColumn1;
    @FXML
    public TableColumn<List<String>, String> PriceColumn1;
    @FXML
    public TableColumn<List<String>, String> statusColumn1;
    @FXML
    public TableColumn<List<String>, String> PriceColumn;
    
    public Controller Cont = new Controller();
    
    @FXML
    public Button DeleteButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            LoadRequestTable();
            LoadMedicineTable(Cont.getAddedMeds());
        } catch (SQLException ex) {
            Logger.getLogger(InventoryManagerDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void LoadRequestTable() throws SQLException {
        ObservableList<List<String>> medData = FXCollections.observableArrayList();

        for (Medicine med : Cont.getApprovedMeds()) {
            List<String> rowData = Arrays.asList(
                String.valueOf(med.getMedicineId()), 
                String.valueOf(med.getQuantity()), 
                String.valueOf(med.getPrice()), 
                med.getExpiry().toString(), 
                med.getManufactor().toString(), 
                med.getName(), 
                med.getStatus(), 
                med.getSupplyStatus(), 
                med.getType(), 
                String.valueOf(med.getSup()), 
                Cont.getSupName(med.getSup())
            );
            medData.add(rowData);
        }

        medicineIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        medicineNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));
        quantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        medicineTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(8)));
        expiryDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(10)));
        PriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));

        requestTable.setItems(medData);
    }
    
    public void LoadMedicineTable(ArrayList<Medicine> MedList) throws SQLException {
        ObservableList<List<String>> medData = FXCollections.observableArrayList();

        for (Medicine med : MedList) {
            List<String> rowData = Arrays.asList(
                String.valueOf(med.getMedicineId()), 
                String.valueOf(med.getQuantity()), 
                String.valueOf(med.getPrice()), 
                med.getExpiry().toString(), 
                med.getManufactor().toString(), 
                med.getName(), 
                med.getStatus(), 
                med.getSupplyStatus(), 
                med.getType(), 
                String.valueOf(med.getSup()), 
                Cont.getSupName(med.getSup())
            );
            medData.add(rowData);
        }

        medicineIdColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        medicineNameColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));
        quantityColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        medicineTypeColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(8)));
        expiryDateColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        statusColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(10)));
        PriceColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));

        medicineTable.setItems(medData);
    }
    
    @FXML
    public void loadManageMedicines(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryManagerViews/InventoryManagerDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadRestockMedicines(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryManagerViews/InventoryManagerRestock.fxml"));
        Parent view = loader.load();
        mainContent.getChildren().clear();
        mainContent.getChildren().add(view);
    }

    @FXML
    public void loadExpiredMedicines(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryManagerViews/InventoryManagerExpiredMed.fxml"));
        Parent view = loader.load();
        mainContent.getChildren().clear();
        mainContent.getChildren().add(view);
    }

    @FXML
    public void loadLogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupplierViews/Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void addMedicine(ActionEvent event) throws SQLException {
        List<String> Med1 = requestTable.getSelectionModel().getSelectedItem();
        
        if (Med1 != null) {
            Medicine Med = createMedicineFromRow(Med1);
            Cont.AddMedicine(Med);
            LoadRequestTable();
            LoadMedicineTable(Cont.getAddedMeds());
        }
    }

    @FXML
    public void FilterMedicine(ActionEvent event) throws SQLException {
        String filter = searchBar.getText();
        
        if (!filter.isEmpty() && !filter.contains(" ")) {
            ArrayList<Medicine> MedList = Cont.getAddedMeds();
            ArrayList<Medicine> FilteredMedicines = new ArrayList<>();

            for (Medicine Med : MedList) {
                if (Med.getName().toLowerCase().contains(filter.toLowerCase())) {
                    FilteredMedicines.add(Med);
                }
            }
            LoadMedicineTable(FilteredMedicines);
        } else {
            LoadMedicineTable(new ArrayList<>(Cont.getAddedMeds()));
        }
    }

    @FXML
    public void DeleteMedicine(ActionEvent event) throws SQLException {
        List<String> Med1 = medicineTable.getSelectionModel().getSelectedItem();
        
        if (Med1 != null) {
            Medicine Med = createMedicineFromRow(Med1);
            Cont.DeleteMedicine(Med);
            LoadRequestTable();
            LoadMedicineTable(Cont.getAddedMeds());
        }
    }

    @FXML
    public void RemoveMedicine(ActionEvent event) throws SQLException {
        List<String> Med1 = medicineTable.getSelectionModel().getSelectedItem();
        
        if (Med1 != null) {
            Medicine Med = createMedicineFromRow(Med1);
            Cont.RemoveMedicine(Med);
            LoadRequestTable();
            LoadMedicineTable(Cont.getAddedMeds());
        }
    }

    public Controller getCont() {
        return Cont;
    }

    public Medicine createMedicineFromRow(List<String> row) {
        int medicineId = Integer.parseInt(row.get(0));  
        int quantity = Integer.parseInt(row.get(1));    
        double price = Double.parseDouble(row.get(2));  
        LocalDate expiry = LocalDate.parse(row.get(3));  
        LocalDate manufactor = LocalDate.parse(row.get(4)); 
        String name = row.get(5);  
        String status = row.get(6);  
        String supplyStatus = row.get(7);  
        String type = row.get(8); 
        int supId = Integer.parseInt(row.get(9)); 
        
        return new Medicine(medicineId, quantity, price, expiry, manufactor, name, status, supplyStatus, type, supId);
    }

    @FXML
    private void loadreport(ActionEvent event) throws IOException
    {
       
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryManagerViews/ViewReports.fxml"));
            Parent view = loader.load();

            ViewReportsController viewReportsController = loader.getController();
            viewReportsController.setUserDetails("Inventory Manager",Cont.getInvUserId());  

            mainContent.getChildren().clear();
            mainContent.getChildren().add(view);
         
    }
}
