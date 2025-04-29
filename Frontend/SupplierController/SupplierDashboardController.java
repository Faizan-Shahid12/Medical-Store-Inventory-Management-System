/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SupplierController;

import InventoryManagerController.ViewReportsController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
public class SupplierDashboardController implements Initializable {

    @FXML
    private BorderPane rootPane;
    @FXML
    private AnchorPane sidePanel;
    @FXML
    private Button manageMedicinesBtn;
    @FXML
    private Button viewReportsBtn;
    @FXML
    private Button requestNewStockBtn;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void loadManageMedicines(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupplierViews/SupplierManageStock.fxml"));
        Parent view = loader.load();
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
    private void loadNewMedicines(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupplierViews/SupplierAddStock.fxml"));
        Parent view = loader.load();
        mainContent.getChildren().clear();
        mainContent.getChildren().add(view);
    }

    @FXML
    private void loadReport(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryManagerViews/ViewReports.fxml"));
        Parent view = loader.load();

        ViewReportsController viewReportsController = loader.getController();
        viewReportsController.setUserDetails("Supplier", Cont.getSupId());  

        mainContent.getChildren().clear();
        mainContent.getChildren().add(view);
    }
    
}
