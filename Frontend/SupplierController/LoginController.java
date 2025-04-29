/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SupplierController;

import Backend.Medicine;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import Backend.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane loginLeft;
    @FXML
    private Text leftPaneHeading;
    @FXML
    private Text leftText;
    @FXML
    private ImageView leftImage;
    @FXML
    private AnchorPane loginRight;
    @FXML
    private Text loginHeading;
    @FXML
    private Text rightText;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<String> dropDown;
    @FXML
    private Hyperlink forgotText;
    @FXML
    private Button loginButton;
    @FXML
    private TextField username;
    @FXML
    private Label warning;
    
    Controller Cont = new Controller();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       ObservableList<String> list = FXCollections.observableArrayList();
       list.add("Store Owner");
       list.add("Supplier");
       list.add("Inventory Manager");
       list.add("Pharmacist");
       dropDown.setItems(list);
    }    

    @FXML
    private void resetWarning(ActionEvent event) 
    {
    }

    @FXML
    private void setLoginButton(ActionEvent event) throws SQLException, IOException 
    {
        String Username = username.getText();
        String Password = password.getText();
        String Role = dropDown.getSelectionModel().getSelectedItem();
        
        if (Username.isEmpty() || Password.isEmpty() || Role == null) 
        {
            showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please make sure all fields are filled");
            return;
        }
        
        if(Role.equalsIgnoreCase("Inventory Manager"))
        {
            ArrayList<InventoryManager> Man = Cont.GetInventoryManager();
            boolean chk = false;
            
            for(InventoryManager Man1 : Man)
            {
                if(Man1.getUsername().equalsIgnoreCase(Username) && Man1.getPassword().equalsIgnoreCase(Password))
                {
                    Cont.setInventoryManager(Man1);
                    chk = true;
                    break;
                }
            }
            
            if(chk == true)
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryManagerViews/InventoryManagerDashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            
        }
        else if (Role.equalsIgnoreCase("Supplier"))
        {
            ArrayList<Supplier> Man = Cont.GetSupplier();
            boolean chk = false;
            
            for(Supplier Man1 : Man)
            {
                if(Man1.getUsername().equalsIgnoreCase(Username) && Man1.getPassword().equalsIgnoreCase(Password))
                {
                    Cont.setSupplier(Man1);
                    chk = true;
                    break;
                }
            }
            
            if(chk == true)
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupplierViews/SupplierDashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else 
            {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password. Please try again.");
            }
        }
        else if (Role.equalsIgnoreCase("Store Owner"))
        {
            ArrayList<Owner> Man = Cont.getOwner();
            boolean chk = false;
            
            for(Owner Man1 : Man)
            {
                if(Man1.getUsername().equalsIgnoreCase(Username) && Man1.getPassword().equalsIgnoreCase(Password))
                {
                    Cont.setStoreOwner(Man1);
                    chk = true;
                    break;
                }
            }
            
            if(chk == true)
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/StoreOwnerViews/StoreOwnerDashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else 
            {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password. Please try again.");
            }
        }
        else if (Role.equalsIgnoreCase("Pharmacist"))
        {
            ArrayList<Pharmacist> Man = Cont.getPharmacist();
            boolean chk = false;
            
            for(Pharmacist Man1 : Man)
            {
                if(Man1.getUsername().equalsIgnoreCase(Username) && Man1.getPassword().equalsIgnoreCase(Password))
                {
                    Cont.setPharmacist(Man1);
                    chk = true;
                    break;
                }
            }
            
            if(chk == true)
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PharmacistViews/PharmacistDashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else 
            {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password. Please try again.");
            }
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
