package InventoryManagerController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import Database.DBHandler;
import Backend.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewReportsController {
    
    @FXML private AnchorPane mainContent;
    @FXML private Text pageTitle;
    @FXML private ComboBox<String> reportTypeCombo;
    @FXML private TextField searchBar;
    @FXML private Button refreshButton;
    @FXML private TableView<Medicine> reportTable;

    private String userRoleStr;
    private int userId;
    private DBHandler DB = DBHandler.getInstance();
    
    Controller Cont = new Controller();

    public void initialize() 
    {
       
    }

    public void setUserDetails(String userRoleStr, int userId) {
        this.userRoleStr = userRoleStr;
        this.userId = userId;
        setupReportTypeOptions();
    }

    private void setupReportTypeOptions() {
        ObservableList<String> reportTypes = FXCollections.observableArrayList();
        
        switch (userRoleStr) 
        {
            case "Owner" : 
                            reportTypes.addAll("Sales Summary", "Inventory Status");
                            break;
            case "Supplier":
                             reportTypes.addAll("Supplied Medicines", "Pending Orders");
                             break;
            case "Inventory Manager" :
                                        reportTypes.addAll("Stock Levels", "Restock Needs");
                                        break;
        }
        reportTypeCombo.setItems(reportTypes);
    }

    @FXML
    private void loadReport() {
        String selectedReport = reportTypeCombo.getValue();
        if (selectedReport == null) 
            return;

        reportTable.getColumns().clear();
        reportTable.getItems().clear();

        try 
        {
            if(selectedReport.equalsIgnoreCase("Stock Levels"))
            {
                ObservableList<Medicine> data = FXCollections.observableArrayList(Cont.GetAllMedicines());
                setupMedicineTable(); 
                
                data.removeIf(med -> med.getStatus().equalsIgnoreCase("Rejected"));
                data.removeIf(med -> med.getStatus().equalsIgnoreCase("Pending"));

                if (!getSearchText().isEmpty()) 
                {
                    String search = getSearchText().toLowerCase();
                    data = data.filtered(med -> med.getName().toLowerCase().contains(search));
                }

                reportTable.setItems(data);
            }
            else if (selectedReport.equalsIgnoreCase("Restock Needs"))
            {
                ObservableList<Medicine> data = FXCollections.observableArrayList(Cont.GetAllMedicines());
                setupMedicineTable(); 
                
                data.removeIf(med -> med.getStatus().equalsIgnoreCase("Rejected"));
                data.removeIf(med -> med.getStatus().equalsIgnoreCase("Pending"));
                data.removeIf(med -> med.getQuantity() > 10);


                if (!getSearchText().isEmpty()) 
                {
                    String search = getSearchText().toLowerCase();
                    data = data.filtered(med -> med.getName().toLowerCase().contains(search));
                }

                reportTable.setItems(data);
            }
            else if (selectedReport.equalsIgnoreCase("Supplied Medicines"))
            {
                ObservableList<Medicine> data = FXCollections.observableArrayList(Cont.getSupMedicine());
                setupMedicineTable(); 
                
                data.removeIf(med -> med.getStatus().equalsIgnoreCase("Rejected"));
                data.removeIf(med -> med.getStatus().equalsIgnoreCase("Pending"));


                if (!getSearchText().isEmpty()) 
                {
                    String search = getSearchText().toLowerCase();
                    data = data.filtered(med -> med.getName().toLowerCase().contains(search));
                }

                reportTable.setItems(data);
            }
            else if (selectedReport.equalsIgnoreCase("Pending Orders"))
            {
                ObservableList<Medicine> data = FXCollections.observableArrayList(Cont.getSupMedicine());
                setupMedicineTable(); 
                
                data.removeIf(med -> med.getStatus().equalsIgnoreCase("Added"));
                data.removeIf(med -> med.getStatus().equalsIgnoreCase("Approved"));


                if (!getSearchText().isEmpty()) 
                {
                    String search = getSearchText().toLowerCase();
                    data = data.filtered(med -> med.getName().toLowerCase().contains(search));
                }

                reportTable.setItems(data);
            }
            else if (selectedReport.equalsIgnoreCase("Inventory Status"))
            {
                ObservableList<Medicine> data = FXCollections.observableArrayList(Cont.GetAllMedicines());
                setupMedicineTable(); 
                
                data.removeIf(med -> med.getStatus().equalsIgnoreCase("Rejected"));
                data.removeIf(med -> med.getStatus().equalsIgnoreCase("Pending"));

                if (!getSearchText().isEmpty()) 
                {
                    String search = getSearchText().toLowerCase();
                    data = data.filtered(med -> med.getName().toLowerCase().contains(search));
                }

                reportTable.setItems(data);
            }
            else if (selectedReport.equalsIgnoreCase("Sales Summary"))
            {
                ArrayList<SoldMedicine> Meds = Cont.GetSoldMedicine();
                ObservableList<Medicine> data = FXCollections.observableArrayList();
                reportTable.getColumns().clear();
                setupMedicineTable();

                TableColumn<Medicine, Number> totalCol = new TableColumn<>("Total");
                totalCol.setCellValueFactory(cellData -> {
                    Medicine med = cellData.getValue();
                    double total = Cont.CalculateTotal(med.getQuantity(),med.getPrice());
                    return new javafx.beans.property.SimpleDoubleProperty(total);
                });
                
                reportTable.getColumns().add(totalCol); 

                for(SoldMedicine Med : Meds)
                {
                    Medicine Med1 = new Medicine(Med.getMedicineId(),Med.getQuantity(),Med.getPrice(),Med.getExpiry(),          
                        Med.getManufactor(),Med.getName(), Med.getStatus(),Med.getSupplyStatus(),Med.getType(),Med.getSup());      
                    
                    data.add(Med1);
                }
                
                if (!getSearchText().isEmpty()) 
                {
                    String search = getSearchText().toLowerCase();
                    data = data.filtered(med -> med.getName().toLowerCase().contains(search));
                }

                reportTable.setItems(data);
            }

        }
        catch (Exception e) 
        {
            showAlert("Query Error", "Failed to load report: " + e.getMessage());
        }
    }

    private String getSearchText() {
        return searchBar.getText() != null ? searchBar.getText().trim() : "";
    }

    @FXML
    private void filterReport() 
    {
        loadReport();
    }

    @FXML
    private void refreshReport() {
        searchBar.clear();
        loadReport();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // ====== Table Setup ======
    private void setupMedicineTable() {
        TableColumn<Medicine, Number> medIdCol = new TableColumn<>("Medicine ID");
        medIdCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getMedicineId()));

        TableColumn<Medicine, String> nameCol = new TableColumn<>("Medicine Name");
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Medicine, Number> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity()));

        TableColumn<Medicine, Number> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrice()));

        TableColumn<Medicine, String> expiryCol = new TableColumn<>("Expiry Date");
        expiryCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getExpiry() != null ? cellData.getValue().getExpiry().toString() : ""
        ));

        TableColumn<Medicine, String> manufactorCol = new TableColumn<>("Manufacture Date");
        manufactorCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getManufactor() != null ? cellData.getValue().getManufactor().toString() : ""
        ));

        TableColumn<Medicine, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));

        TableColumn<Medicine, String> supplyStatusCol = new TableColumn<>("Supply Status");
        supplyStatusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSupplyStatus()));

        TableColumn<Medicine, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getType()));

        TableColumn<Medicine, String> supplierNameCol = new TableColumn<>("Supplier Name");
        supplierNameCol.setCellValueFactory(cellData -> {
            int supId = cellData.getValue().getSup();
            String supplierName = "Unknown";
            try 
            {
                supplierName = Cont.getSupName(supId);
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(ViewReportsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new javafx.beans.property.SimpleStringProperty(supplierName != null ? supplierName : "Unknown");
        });

        reportTable.getColumns().addAll(
            medIdCol, nameCol, typeCol, quantityCol, priceCol, manufactorCol, expiryCol,
            statusCol, supplyStatusCol, supplierNameCol
        );
    }
}
