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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class PharmacistNewPrescriptionController implements Initializable {

    @FXML
    private Label pageTitle;
    @FXML
    private TextField patientNameField;
    @FXML
    private TextField doctorNameField;
    @FXML
    private TextField HospitalField;
    @FXML
    private ComboBox<String> Typebox;
    @FXML
    private TextField quantityField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button submitBtn;
    @FXML
    private TableView<Medicine> medicineTable;
    @FXML
    private TableColumn<Medicine, Integer> medicineIdColumn1;
    @FXML
    private TableColumn<Medicine, String> medicineNameColumn1;
    @FXML
    private TableColumn<Medicine, String> medicineTypeColumn1;
    @FXML
    private TableColumn<Medicine, Integer> quantityColumn1;
    @FXML
    private TableColumn<Medicine, Double> PriceColumn1;
    
    Controller Cont = new Controller();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        medicineTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        Typebox.getItems().add("Restricted");
        Typebox.getItems().add("Non-Restricted");
        
         datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>()
         {
            @Override
            public DateCell call(DatePicker param) 
            {
                return new DateCell() 
                {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) 
                    {
                        super.updateItem(item, empty);

                        if (item.isBefore(LocalDate.now())) 
                        {
                            setDisable(true);
                            setStyle("-fx-background-color: #f0f0f0;");
                        }

                        if (item.isAfter(LocalDate.now())) 
                        {
                            setDisable(true);
                            setStyle("-fx-background-color: #f0f0f0;"); 
                        }
                    }
                };
            }
        });
                

        try {
            ObservableList<Medicine> medData = FXCollections.observableArrayList(Cont.getAddedMeds());
            
            medicineIdColumn1.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMedicineId()).asObject());
            medicineNameColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));  // For String
            quantityColumn1.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());  // For int as IntegerProperty
            medicineTypeColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));  // For String
            PriceColumn1.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());  // For double as DoubleProperty

            medicineTable.setItems(medData);
        } catch (SQLException ex) {
            Logger.getLogger(PharmacistNewPrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void Add_Prescription(ActionEvent event) throws SQLException 
    {
        String patientName = patientNameField.getText().trim();
        String doctorName = doctorNameField.getText().trim();
        String hospitalName = HospitalField.getText().trim();
        String type = Typebox.getValue();
        String quantityText = quantityField.getText().trim();
        int quantity = 0;

        if (patientName.isEmpty() || doctorName.isEmpty() || hospitalName.isEmpty() || type == null || type.trim().isEmpty() || quantityText.isEmpty() || datePicker.getValue() == null
                || medicineTable.getSelectionModel().getSelectedItem() == null || medicineTable.getSelectionModel().getSelectedItems() == null)
        {
            showAlert("Validation Error", "All fields are required.");
            return;
        }
        else 
        {   
            String regex = "^[A-Za-z\\s]+$";

            if (patientName.isEmpty() || !patientName.matches(regex)) 
            {
                showAlert("Validation Error", "Patient name should contain only alphabets and spaces and cannot be empty.");
                return;
            }

            if (doctorName.isEmpty() || !doctorName.matches(regex)) 
            {
                showAlert("Validation Error", "Doctor name should contain only alphabets and spaces and cannot be empty.");
                return;

            }

            if (hospitalName.isEmpty() || !hospitalName.matches(regex))
            {
                showAlert("Validation Error", "Hospital name should contain only alphabets and spaces and cannot be empty.");
                return;
            }
            
            try 
            {
                quantity = Integer.parseInt(quantityText);
                
                if (quantity <= 0) 
                {
                    showAlert("Validation Error", "Quantity must be a positive number.");
                    return;

                }
                else if (quantity > 50)
                {
                    showAlert("Validation Error", "Quantity must be less than 50");
                    return;

                }
            } 
            catch (NumberFormatException e) 
            {
                showAlert("Validation Error", "Quantity must be a valid integer.");
            }
            
            if(datePicker.getValue().isAfter(LocalDate.now()) || datePicker.getValue().isBefore(LocalDate.now()))
            {
                showAlert("Validation Error", "Date of Prescription is not correct");
                return;
            }
        }
        
        ArrayList<Medicine> Meds = new ArrayList<>(medicineTable.getSelectionModel().getSelectedItems());
        Cont.AddPrescription(quantity, datePicker.getValue(), patientName, type, doctorName, hospitalName, Meds);
        
        System.out.println("Prescription has been added");
    }
    
    private void showAlert(String title, String message)
    {
        Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
