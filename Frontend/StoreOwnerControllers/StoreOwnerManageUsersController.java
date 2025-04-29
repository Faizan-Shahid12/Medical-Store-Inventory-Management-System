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
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class StoreOwnerManageUsersController implements Initializable {

    @FXML
    private AnchorPane userManagementPane;
    @FXML
    private Text pageTitle;
    @FXML
    private Button suppliersBtn;
    @FXML
    private Button pharmacistsBtn;
    @FXML
    private Button inventoryManagersBtn;
    @FXML
    private TableView<List<String>> userTable;
    @FXML
    private TableColumn<List<String>, String> nameColumn;
    @FXML
    private TableColumn<List<String>, Integer> ageColumn;
    @FXML
    private Button addUserButton;
    @FXML
    private TableView<List<String>> userTable1;
    @FXML
    private TableColumn<List<String>, String> nameColumn1;
    @FXML
    private TableColumn<List<String>, Integer> ageColumn1;
    @FXML
    private TableColumn<List<String>, String> GenderColumn;
    @FXML
    private TableColumn<List<String>, String> DOBColumn1;
    @FXML
    private TableColumn<List<String>, String> EmailColumn1;
    @FXML
    private TableColumn<List<String>, String> StatusColumn1;
    @FXML
    private Button addUserButton1;
    @FXML
    private TableColumn<List<String>, String> DOBColumn;
    @FXML
    private TableColumn<List<String>, String> EmailColumn;
    @FXML
    private TableColumn<List<String>, String> StatusColumn;
    @FXML
    private Button addUserButton2;
    
    Controller Cont = new Controller();
    @FXML
    private TableColumn<List<String>, String> GenderColumn1;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    

    @FXML
    private void showSuppliers(ActionEvent event) throws SQLException 
    {
        ArrayList<Supplier> Manager = Cont.GetSupplier();
        
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));  // Name
        ageColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(Integer.parseInt(cellData.getValue().get(1))).asObject());  // Age
        GenderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));  // Gender
        DOBColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));  // DOB (LocalDate to String)
        EmailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));  // Email
        StatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));  // Status
        
        nameColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));  // Name
        ageColumn1.setCellValueFactory(cellData -> new SimpleIntegerProperty(Integer.parseInt(cellData.getValue().get(1))).asObject());  // Age
        GenderColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));  // Gender
        DOBColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));  // DOB (LocalDate to String)
        EmailColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));  // Email
        StatusColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));  // Status

        ObservableList<List<String>> data = FXCollections.observableArrayList();
        ObservableList<List<String>> data1 = FXCollections.observableArrayList();
        
        for (Supplier user : Manager) 
        {
            List<String> rowData = Arrays.asList(
            user.getName(), //0
            String.valueOf(user.getAge()), //1
            user.getGender(), //2
            user.getDOB().toString(), //3
            user.getEmail(), //4
            user.getStatus(), //5
            String.valueOf(user.getSupId()), //6
            "Supplier" //7
        );
            if("Approved".equalsIgnoreCase(user.getStatus()) || user.getStatus().equalsIgnoreCase("Rejected"))
            {
                data1.add(rowData);
            }
            else
            {
                data.add(rowData);
            }
        }

        userTable.setItems(data);
        userTable1.setItems(data1);
    }

    @FXML
    private void showPharmacists(ActionEvent event) throws SQLException 
    {
         ArrayList<Pharmacist> Manager = Cont.GetPharmacist();
        
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));  // Name
        ageColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(Integer.parseInt(cellData.getValue().get(1))).asObject());  // Age
        GenderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));  // Gender
        DOBColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));  // DOB (LocalDate to String)
        EmailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));  // Email
        StatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));  // Status
        
        nameColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));  // Name
        ageColumn1.setCellValueFactory(cellData -> new SimpleIntegerProperty(Integer.parseInt(cellData.getValue().get(1))).asObject());  // Age
        GenderColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));  // Gender
        DOBColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));  // DOB (LocalDate to String)
        EmailColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));  // Email
        StatusColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));  // Status

        ObservableList<List<String>> data = FXCollections.observableArrayList();
        ObservableList<List<String>> data1 = FXCollections.observableArrayList();
        
        for (Pharmacist user : Manager) 
        {
            List<String> rowData = Arrays.asList(
            user.getName(), //0
            String.valueOf(user.getAge()), //1
            user.getGender(), //2
            user.getDOB().toString(), //3
            user.getEmail(), //4
            user.getStatus(), //5
            String.valueOf(user.getPharmaId()), //6
            "Pharmacist" //7
        );
            if("Approved".equalsIgnoreCase(user.getStatus()) || user.getStatus().equalsIgnoreCase("Rejected"))
            {
                data1.add(rowData);
            }
            else
            {
                data.add(rowData);
            }
        }

        userTable.setItems(data);
        userTable1.setItems(data1);
    }

    @FXML
    private void showInventoryManagers(ActionEvent event) throws SQLException 
    {
        ArrayList<InventoryManager> Manager = Cont.GetInventoryManager();
        
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));  // Name
        ageColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(Integer.parseInt(cellData.getValue().get(1))).asObject());  // Age
        GenderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));  // Gender
        DOBColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));  // DOB (LocalDate to String)
        EmailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));  // Email
        StatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));  // Status
        
        nameColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));  // Name
        ageColumn1.setCellValueFactory(cellData -> new SimpleIntegerProperty(Integer.parseInt(cellData.getValue().get(1))).asObject());  // Age
        GenderColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));  // Gender
        DOBColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));  // DOB (LocalDate to String)
        EmailColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));  // Email
        StatusColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));  // Status

        ObservableList<List<String>> data = FXCollections.observableArrayList();
        ObservableList<List<String>> data1 = FXCollections.observableArrayList();
        
        for (InventoryManager user : Manager) 
        {
            List<String> rowData = Arrays.asList(
            user.getName(), //0
            String.valueOf(user.getAge()), //1
            user.getGender(), //2
            user.getDOB().toString(), //3
            user.getEmail(), //4
            user.getStatus(), //5
            String.valueOf(user.getImId()), //6
            "InventoryManager" //7
        );
            if("Approved".equalsIgnoreCase(user.getStatus()) || user.getStatus().equalsIgnoreCase("Rejected"))
            {
                data1.add(rowData);
            }
            else
            {
                data.add(rowData);
            }
        }

        userTable.setItems(data);
        userTable1.setItems(data1);
        
    }

    @FXML
    private void addUser(ActionEvent event) throws SQLException 
    {
        if(userTable.getSelectionModel().getSelectedItem() != null)
        {
            int ImdId = Integer.parseInt(userTable.getSelectionModel().getSelectedItem().get(6));
            String role = userTable.getSelectionModel().getSelectedItem().get(7);

            if(!role.isEmpty() && role.equalsIgnoreCase("InventoryManager"))
            {
                Cont.ApproveManager(ImdId);
                showInventoryManagers(event);
            }
            else if (!role.isEmpty() && role.equalsIgnoreCase("Supplier"))
            {
                Cont.ApproveSupplier(ImdId);
                showSuppliers(event);
            }
            else if (!role.isEmpty() && role.equalsIgnoreCase("Pharmacist"))
            {
                Cont.ApprovePharmacist(ImdId);
                showPharmacists(event);

            }
        }
    }

    @FXML
    private void RejectUser(ActionEvent event) throws SQLException 
    {
        
        if(userTable.getSelectionModel().getSelectedItem() != null)
        {
            int ImdId = Integer.parseInt(userTable.getSelectionModel().getSelectedItem().get(6));
            String role = userTable.getSelectionModel().getSelectedItem().get(7);

            if(!role.isEmpty() && role.equalsIgnoreCase("InventoryManager"))
            {
                Cont.RejectManager(ImdId);
                showInventoryManagers(event);
            }
            else if (!role.isEmpty() && role.equalsIgnoreCase("Supplier"))
            {
                Cont.RejectSupplier(ImdId);
                showSuppliers(event);

            }
            else if (!role.isEmpty() && role.equalsIgnoreCase("Pharmacist"))
            {
                Cont.RejectPharmacist(ImdId);
                showPharmacists(event);

            }
        }
    }

    @FXML
    private void RemoveUser(ActionEvent event) throws SQLException 
    {
        
        if(userTable1.getSelectionModel().getSelectedItem() != null)
        {
            int ImdId = Integer.parseInt(userTable1.getSelectionModel().getSelectedItem().get(6));
            String role = userTable1.getSelectionModel().getSelectedItem().get(7);

            if(!role.isEmpty() && role.equalsIgnoreCase("InventoryManager"))
            {
                Cont.DeleteManager(ImdId);
                showInventoryManagers(event);
            }
            else if (!role.isEmpty() && role.equalsIgnoreCase("Supplier"))
            {
                Cont.DeleteSupplier(ImdId);
                showSuppliers(event);

            }
            else if (!role.isEmpty() && role.equalsIgnoreCase("Pharmacist"))
            {
                Cont.DeletePharmacist(ImdId);
                showPharmacists(event);
            }
        }
    }
    
}
