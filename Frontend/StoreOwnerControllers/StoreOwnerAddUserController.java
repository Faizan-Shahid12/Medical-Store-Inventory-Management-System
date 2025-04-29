/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StoreOwnerControllers;

import Backend.Controller;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class StoreOwnerAddUserController implements Initializable {

    @FXML
    private AnchorPane addStockPane;
    @FXML
    private Text pageTitle;
    @FXML
    private TextField UserName;
    @FXML
    private ComboBox<String> RoleCombo;
    @FXML
    private TextField PasswordField;
    @FXML
    private TextField NameField;
    @FXML
    private DatePicker DOB;
    @FXML
    private Button submitRequestButton;
    @FXML
    private TextField EmailField;
    @FXML
    private ComboBox<String> GenderCombo;
    @FXML
    private TextField AgeField;

    Controller Cont = new Controller();
    
    @FXML
    private TextField LicenseField;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
       ObservableList<String> Roles = FXCollections.observableArrayList();
       Roles.add("Inventory Manager");
       Roles.add("Supplier");
       Roles.add("Pharmacist");
       RoleCombo.setItems(Roles);
       
       ObservableList<String> GenderL = FXCollections.observableArrayList();
       GenderL.add("Male");
       GenderL.add("Female");
       GenderL.add("Other");
       GenderCombo.setItems(GenderL);
       
       LicenseField.setVisible(false);
    }    

    @FXML
    private void SetAge(ActionEvent event) 
    {
        LocalDate dob = DOB.getValue();
        
        if (dob != null)
        {
            LocalDate today = LocalDate.now();
            int age = Period.between(dob, today).getYears();
            AgeField.setText(String.valueOf(age));
        } 
        else 
        {
            AgeField.setText("");
        }
    }

    @FXML
    private void submitRequest(ActionEvent event) throws SQLException 
    {
        String username = UserName.getText().trim();
        String password = PasswordField.getText().trim();
        String name = NameField.getText().trim();
        String email = EmailField.getText().trim();
        String ageText = AgeField.getText().trim();
        String role = RoleCombo.getValue();
        String gender = GenderCombo.getValue();
        LocalDate dob = DOB.getValue();

        if (username.isEmpty())
        {
            showAlert(AlertType.ERROR, "Validation Error", "Username is required.");
            return;
        } 
        else if (!Cont.CheckUserName(username)) 
        {
            showAlert(AlertType.ERROR, "Validation Error", "Username already exists. Please choose another one.");
            return;
        }

        if (password.isEmpty())
        {
            showAlert(AlertType.ERROR, "Validation Error", "Password is required.");
            return;
        } 
        else if (password.length() < 6) 
        {
            showAlert(AlertType.ERROR, "Validation Error", "Password must be at least 6 characters long.");
            return;
        }

        if (name.isEmpty()) 
        {
            showAlert(AlertType.ERROR, "Validation Error", "Name is required.");
            return;
        }

        if (email.isEmpty()) 
        {
            showAlert(AlertType.ERROR, "Validation Error", "Email is required.");
            return;
        } 
        else if (!email.matches("^\\S+@\\S+\\.\\S+$")) 
        {
            showAlert(AlertType.ERROR, "Validation Error", "Invalid email format.");
            return;
        }
        else if (!Cont.CheckEmail(email))
        {
            showAlert(AlertType.ERROR, "Validation Error", "Email already exists. Please choose another one.");
            return;
        }

        if (dob == null) 
        {
            showAlert(AlertType.ERROR, "Validation Error", "Date of Birth is required.");
            return;
        } 
        else 
        {
            try 
            {
                int age = Integer.parseInt(ageText);
                if (age < 18 || age > 50) 
                {
                    showAlert(AlertType.ERROR, "Validation Error", "Invalid age.");
                    return;
                }
            } 
            catch (NumberFormatException e) 
            {
                showAlert(AlertType.ERROR, "Validation Error", "Age must be a number.");
                return;
            }
        }

        if (role == null || role.isEmpty()) 
        {
            showAlert(AlertType.ERROR, "Validation Error", "Role must be selected.");
            return;
        }

        if (gender == null || gender.isEmpty()) 
        {
            showAlert(AlertType.ERROR, "Validation Error", "Gender must be selected.");
            return;
        }
        
        
        if(RoleCombo.getValue().equalsIgnoreCase("Inventory Manager"))
        {
            Cont.WriteInventoryManager(username, password, name, gender, Integer.parseInt(ageText), email, dob);
        }
        else if (RoleCombo.getValue().equalsIgnoreCase("Supplier"))
        {
            String license = LicenseField.getText().trim();

            if (license.isEmpty()) 
            {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Supplier license number is required.");
                return;
            }

            if (!license.matches("^SUP-\\d{5}$")) 
            {
                 showAlert(Alert.AlertType.WARNING, "Validation Error", "License must be SUP-xxxxx");
                return;
            }

            if (!Cont.CheckLicenseNumberSup(license)) 
            {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Supplier license number already exists.");
                return;
            }
            
            Cont.CreateSupplier(username, password, name, gender, Integer.parseInt(ageText), email, dob, license);
        }
        else if (RoleCombo.getValue().equalsIgnoreCase("Pharmacist"))
        {
            String license = LicenseField.getText().trim();

            if (license.isEmpty()) 
            {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Pharmacist license number is required.");
                return;
            }

            if (!license.matches("^PHAR-\\d{5}$")) 
            {
                 showAlert(Alert.AlertType.WARNING, "Validation Error", "License must be PHAR-xxxxx");
                return;
            }

            if (!Cont.CheckLicenseNumberPhar(license)) 
            {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Pharmacist license number already exists.");
                return;
            }
            
            Cont.CreatePharmacist(username, password, name, gender, Integer.parseInt(ageText), email, dob, license);
        }
        
        resetForm();
        
    }
    
    private void resetForm() 
    {
        UserName.clear();
        PasswordField.clear();
        NameField.clear();
        EmailField.clear();
        AgeField.clear();
        LicenseField.clear();
        RoleCombo.setValue(null);
        GenderCombo.setValue(null);
        DOB.setValue(null);
    }

    private void showAlert(AlertType type, String title, String content) 
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void ShowField(ActionEvent event) 
    {
        if(RoleCombo.getValue().equalsIgnoreCase("Inventory Manager"))
        {
            LicenseField.setVisible(false);
            return;
        }
        
        LicenseField.setVisible(true);

    }
    
}
