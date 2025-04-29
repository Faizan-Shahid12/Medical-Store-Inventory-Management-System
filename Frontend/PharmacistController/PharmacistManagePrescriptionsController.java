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
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class PharmacistManagePrescriptionsController implements Initializable {

    @FXML
    private Text pageTitle;
    @FXML
    private AnchorPane userManagementPane;
    @FXML
    private TableView<Prescription> prescriptionTable;
    @FXML
    private TableColumn<Prescription, String> patientColumn;
    @FXML
    private TableColumn<Prescription, String> doctorColumn;
    @FXML
    private TableColumn<Prescription, String> HospitalColumn;
    @FXML
    private TableColumn<Prescription, String> TypeColumn;
    @FXML
    private TableColumn<Prescription, Integer> quantityColumn;
    @FXML
    private TableColumn<Prescription, LocalDate> dateColumn;
    @FXML
    private TextField searchPatientField;
    @FXML
    private DatePicker searchDatePicker;
    @FXML
    private TableView<Medicine> medicineTable;
    @FXML
    private TableColumn<Medicine, Integer> medicineIdColumn1;
    @FXML
    private TableColumn<Medicine, String> medicineNameColumn1;
    @FXML
    private TableColumn<Medicine, Integer> quantityColumn1;
    @FXML
    private TableColumn<Medicine, String> medicineTypeColumn1;
    @FXML
    private TableColumn<Medicine, Double> PriceColumn1;
    @FXML
    private TableColumn<Medicine, LocalDate> expiryDateColumn1;
    @FXML
    private Text pageTitle1;
    
    Controller Cont = new Controller();
    @FXML
    private TableColumn<Prescription, Integer> PIdColumn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        LoadTable(null);
    }    
    
    public void LoadTable(ArrayList<Prescription> Prescription)
    {
        
        if(Prescription == null)
        {
            Prescription = Cont.GetAllPrescription();
        }
        
        ObservableList<Prescription> Presc = FXCollections.observableArrayList(Prescription);
                
        PIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPresciId()));
        patientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientName()));
        doctorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDoctorName()));
        HospitalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHospitalName()));
        TypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantity()));
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAddDate()));

        prescriptionTable.setItems(Presc);
        prescriptionTable.refresh();

    }
    
    @FXML
    public void LoadMedTable()
    {
        ObservableList<Medicine> dummyMedicines = FXCollections.observableArrayList(prescriptionTable.getSelectionModel().getSelectedItem().getMeds());

        medicineNameColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        quantityColumn1.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantity()));
        medicineTypeColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        expiryDateColumn1.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpiry()));
        PriceColumn1.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
        medicineIdColumn1.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMedicineId()));
                
        medicineTable.setItems(dummyMedicines);
        medicineTable.refresh();
        
        double Total = Cont.CalculateTotal(prescriptionTable.getSelectionModel().getSelectedItem().getMeds(),prescriptionTable.getSelectionModel().getSelectedItem().getQuantity());
        
        pageTitle1.setText(String.valueOf(Total));
    }
    
    @FXML
    private void handleDelete(ActionEvent event) throws SQLException
    {
        if(prescriptionTable.getSelectionModel().getSelectedItem() != null)
        {
            Cont.DeletePrescription(prescriptionTable.getSelectionModel().getSelectedItem());
            System.out.println("Prescription Deleted Successfully");
        }
        
        LoadTable(null);
    }

    @FXML
    private void handleSearch(ActionEvent event) throws SQLException 
    {
        String filter = searchPatientField.getText();
        
        if(!filter.isEmpty() && !filter.trim().isEmpty())
        {
            ArrayList<Prescription> PrescriptionList = Cont.GetAllPrescription();

            ArrayList<Prescription> FilteredPrescription = new ArrayList<>();

            for(Prescription Pres : PrescriptionList)
            {

                if (Pres.getPatientName().toLowerCase().contains(filter.toLowerCase()))
                {
                    FilteredPrescription.add(Pres);
                }

            }
            
            LoadTable(FilteredPrescription);
        }
        else
        {
            LoadTable(null);
        }
    }

    @FXML
    private void BuyMedicine(ActionEvent event) throws SQLException 
    {
        if(prescriptionTable.getSelectionModel().getSelectedItem() != null)
        {
            if(Cont.CheckQuan(prescriptionTable.getSelectionModel().getSelectedItem().getMeds(), prescriptionTable.getSelectionModel().getSelectedItem().getQuantity()) == true)
            {
                
                Cont.SellPrescription(prescriptionTable.getSelectionModel().getSelectedItem());
                System.out.println("Medicine Successfully Sold");
                LoadMedTable();

            }
            else
            {
                System.out.println("Medicines do not have enough Quantity");
            }
        }
    }
    
}
