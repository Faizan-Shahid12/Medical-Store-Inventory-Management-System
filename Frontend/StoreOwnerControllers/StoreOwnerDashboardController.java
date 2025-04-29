/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StoreOwnerControllers;

import InventoryManagerController.ViewReportsController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Backend.*;
/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class StoreOwnerDashboardController implements Initializable {

    @FXML
    private BorderPane rootPane;
    @FXML
    private AnchorPane sidePanel;
    @FXML
    private Button manageUsersBtn;
    @FXML
    private Button reviewRestockBtn;
    @FXML
    private Button viewReportsBtn;
    @FXML
    private Button logoutButton;
    @FXML
    private Text welcome;
    @FXML
    private Text name;
    @FXML
    private Text userRole;
    @FXML
    private AnchorPane mainContent;
    
    Controller Cont = new Controller();
    @FXML
    private Button manageUsersBtn1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
      
    }    


    @FXML
    private void loadViewReports(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryManagerViews/ViewReports.fxml"));
        Parent view = loader.load();

        ViewReportsController viewReportsController = loader.getController();
        viewReportsController.setUserDetails("Owner", Cont.getOwnerId());  

        mainContent.getChildren().clear();
        mainContent.getChildren().add(view);
    }

    @FXML
    private void loadLogout(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupplierViews/Login.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void loadManageUsers(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StoreOwnerViews/StoreOwnerManageUsers.fxml"));
        Parent view = loader.load();
        mainContent.getChildren().clear();
        mainContent.getChildren().add(view);
    }

    @FXML
    private void loadReviewRequest(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StoreOwnerViews/StoreOwnerReviewStocks.fxml"));
        Parent view = loader.load();
        mainContent.getChildren().clear();
        mainContent.getChildren().add(view);
    }

    @FXML
    private void loadAddUsers(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StoreOwnerViews/StoreOwnerAddUser.fxml"));
        Parent view = loader.load();
        mainContent.getChildren().clear();
        mainContent.getChildren().add(view);
    }
    
}
