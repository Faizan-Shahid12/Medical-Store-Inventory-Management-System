/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SupplierController;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import Backend.*;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class SupplierAddStockController implements Initializable {

    @FXML
    private AnchorPane addStockPane;
    @FXML
    private Text pageTitle;
    @FXML
    private TextField medicineNameField;
    @FXML
    private ComboBox<String> medicineTypeCombo;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;
    @FXML
    private DatePicker manufactureDatePicker;
    @FXML
    private DatePicker expiryDatePicker;
    @FXML
    private Button submitRequestButton;
    @FXML
    private TableView<Medicine> SupplyTable;
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
    private TableColumn<Medicine, String> expDateColumn1;
    @FXML
    private Button submitRequestButton1;
    
    Controller Cont = new Controller();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
        ObservableList<String> medicineTypes = FXCollections.observableArrayList(
            "Painkiller",
            "Antibiotic",
            "Antiseptic",
            "Antipyretic",
            "Antacid",
            "Antidepressant",
            "Antiviral",
            "Antifungal",
            "Antihistamine",
            "Anti-inflammatory",
            "Sedative",
            "Diuretic",
            "Cough Suppressant",
            "Vaccine",
            "Antimalarial",
            "Antidiabetic",
            "Antihypertensive",
            "Laxative"
        );

        medicineTypeCombo.setItems(medicineTypes);

        LoadTable();
        
        manufactureDatePicker.setDayCellFactory(picker -> new DateCell() 
        {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isAfter(LocalDate.now())) 
                {
                    setDisable(true);
                    setStyle("-fx-background-color: #d3d3d3;");
                }
            }
        });
        
        expiryDatePicker.disableProperty().set(true);
    } 
    public void LoadTable()
    {
        ObservableList<Medicine> dummyMedicines = FXCollections.observableArrayList(Cont.GetPendingRequest());

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
    private void submitRequest(ActionEvent event) throws SQLException 
    {
        String Name = medicineNameField.getText();
        String Type = medicineTypeCombo.getSelectionModel().getSelectedItem();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();
        LocalDate Mfg = manufactureDatePicker.getValue();
        LocalDate Exp = expiryDatePicker.getValue();

        if (Name == null || Name.trim().isEmpty() || Type == null || Type.trim().isEmpty() || quantityText == null || quantityText.trim().isEmpty() || priceText == null || priceText.trim().isEmpty() || Mfg == null)
        {
            showAlert(Alert.AlertType.ERROR,"Input Validation Error","Please make sure:\n- All fields are filled\n- Quantity is a number\n- Price is a valid amount\n- Dates are selected correctly");
            return;
        }
        
        if (!Name.matches("[a-zA-Z]+"))
        {
            showAlert(Alert.AlertType.ERROR, "Invalid Medicine Name", "Medicine Name must only contain alphabets.");
            return;
        }

        if (!quantityText.matches("\\d+")) 
        {
            showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Quantity must be a positive integer.");
            return;
        }

        if (!priceText.matches("\\d+(\\.\\d+)?")) 
        { 
            showAlert(Alert.AlertType.ERROR, "Invalid Price", "Price must be a positive integer or positive decimal.");
            return;
        }
        
        double Price = Double.parseDouble(priceText);
        int Quan = Integer.parseInt(quantityText);
        
        if(Quan > 100)
        {
            showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please make sure:\n- Quantity is a number and atmost 100");
            return;
        }
        else if (Quan <= 0)
        {
            showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please make sure:\n- Quantity is a number and greater 1");
            return;
        }
        
        if(Quan > 100) ////Price
        {
            showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please make sure:\n- Price is less 100.0");
            return;
        }
        else if (Quan <= 0)
        {
            showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please make sure:\n- Price is greater 1.0");
            return;
        }
        
        
        Cont.AddRequest(Name, Type, Quan, Price, Exp, Mfg);
        
        medicineNameField.clear();
        medicineTypeCombo.getSelectionModel().clearSelection();
        quantityField.clear();
        priceField.clear();
        manufactureDatePicker.setValue(null);
        expiryDatePicker.setValue(null);
        
        LoadTable();
        SupplyTable.refresh();
    }

    @FXML
    private void RemoveRequest(ActionEvent event) throws SQLException 
    {
        Medicine Med = SupplyTable.getSelectionModel().getSelectedItem();
        
        if(Med != null)
        {
            Cont.RemoveMedicineDB(Med);
        }
        
        LoadTable();
        SupplyTable.refresh();
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) 
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void setexpiry(ActionEvent event) 
    {
       if( manufactureDatePicker.getValue() == null)
       {
           return;
       }
        
       LocalDate ex = manufactureDatePicker.getValue();
       ex = ex.plusYears(5);
       expiryDatePicker.setValue(ex);
    }
}
