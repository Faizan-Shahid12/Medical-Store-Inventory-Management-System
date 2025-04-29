package Database;


import Backend.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class DBHandler 
{
    String userName = "root"; //The username of the sql database
    String pass = "1980"; //The pass of your database
    Connection con; //Connection object
    Statement st; //Statement Obj
    
    private static DBHandler DB;
    
    private DBHandler()
    {
        //Connect to DB
        try
        {       
                String connectionUrl = "jdbc:mysql://localhost:3306/se_project";
                con = DriverManager.getConnection(connectionUrl,userName,pass);  
                System.out.println("Connected to DB");
        }
        catch(SQLException e)
        {
                System.out.println("Connection Failed");
                e.printStackTrace();
        }
    }
    
    public static DBHandler getInstance()
    {
        if (DB == null)
            DB = new DBHandler();
        return DB;
    }
    
    public ArrayList<Medicine> GetAllMedicines() throws SQLException
    {
            Statement st1 = con.createStatement();
            String query1 = "Select * From Medicine ";
            ResultSet rs1 = st1.executeQuery(query1);

            ArrayList<Medicine> MedList = new ArrayList<>();

            while(rs1.next())
            {
                Medicine med = new Medicine(rs1.getInt("MedicineId"),rs1.getInt("Quantity"),rs1.getDouble("Price"),rs1.getDate("Expiry").toLocalDate(),rs1.getDate("Manufactor").toLocalDate(),rs1.getString("Name"),
                         rs1.getString("Status"),rs1.getString("SupplyStatus"),rs1.getString("Type"),rs1.getInt("SupplierId"));
                MedList.add(med);
            }
            
            return MedList;
    }
    
    public ArrayList<Medicine> GetAddedMedicines() throws SQLException
    {
            Statement st1 = con.createStatement();
            String query1 = "Select * From Medicine Where Status = 'Added'";
            ResultSet rs1 = st1.executeQuery(query1);

            ArrayList<Medicine> MedList = new ArrayList<>();

            while(rs1.next())
            {
                Medicine med = new Medicine(rs1.getInt("MedicineId"),rs1.getInt("Quantity"),rs1.getDouble("Price"),rs1.getDate("Expiry").toLocalDate(),rs1.getDate("Manufactor").toLocalDate(),rs1.getString("Name"),
                         rs1.getString("Status"),rs1.getString("SupplyStatus"),rs1.getString("Type"),rs1.getInt("SupplierId"));
                MedList.add(med);
            }
            
            return MedList;
    }
    
    public ArrayList<SoldMedicine> GetSoldMedicine() throws SQLException
    {
            Statement st1 = con.createStatement();
            String query1 = "Select * From SoldMedicine ";
            ResultSet rs1 = st1.executeQuery(query1);

            ArrayList<SoldMedicine> MedList = new ArrayList<>();

            while(rs1.next())
            {
                SoldMedicine med = new SoldMedicine(rs1.getInt("MedicineId"),rs1.getInt("Quantity"),rs1.getDouble("Price"),rs1.getDate("Expiry").toLocalDate(),rs1.getDate("Manufactor").toLocalDate(),rs1.getString("Name"),
                         rs1.getString("Status"),rs1.getString("SupplyStatus"),rs1.getString("Type"),rs1.getInt("SupplierId"), GetSupplierName(rs1.getInt("SupplierId")));
                MedList.add(med);
            }
            
            return MedList;
    }
    
    public String GetSupplierName(int SupId) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Select * From UserList Join Supplier on UserList.UserId = Supplier.UserId Where SupId = " + SupId;
        ResultSet rs1 = st1.executeQuery(query1);
        
        String Manager = null;
        
        while(rs1.next())
        {
            Manager = rs1.getString("Name");
        }

        System.out.println("Supplier Name Successfully Fetched");
        
        return Manager;
    }
    
    public ArrayList<Medicine> GetApprovedMedicines() throws SQLException
    {
            Statement st1 = con.createStatement();
            String query1 = "Select * From Medicine Where Status = 'Approved'";
            ResultSet rs1 = st1.executeQuery(query1);

            ArrayList<Medicine> MedList = new ArrayList<>();

            while(rs1.next())
            {
                Medicine med = new Medicine(rs1.getInt("MedicineId"),rs1.getInt("Quantity"),rs1.getDouble("Price"),rs1.getDate("Expiry").toLocalDate(),rs1.getDate("Manufactor").toLocalDate(),rs1.getString("Name"),
                         rs1.getString("Status"),rs1.getString("SupplyStatus"),rs1.getString("Type"),rs1.getInt("SupplierId"));
                MedList.add(med);
            }
            
            return MedList;
    }
    
    public ArrayList<Medicine> GetPendingMedicines() throws SQLException
    {
            Statement st1 = con.createStatement();
            String query1 = "Select * From Medicine Where Status = 'Pending' OR Status = 'Rejected'";
            ResultSet rs1 = st1.executeQuery(query1);

            ArrayList<Medicine> MedList = new ArrayList<>();

            while(rs1.next())
            {
                Medicine med = new Medicine(rs1.getInt("MedicineId"),rs1.getInt("Quantity"),rs1.getDouble("Price"),rs1.getDate("Expiry").toLocalDate(),rs1.getDate("Manufactor").toLocalDate(),rs1.getString("Name"),
                         rs1.getString("Status"),rs1.getString("SupplyStatus"),rs1.getString("Type"),rs1.getInt("SupplierId"));
                MedList.add(med);
            }
            
            return MedList;
    }
    
    public ArrayList<Medicine> GetSupMedicines(int Id) throws SQLException
    {
            Statement st1 = con.createStatement();
            String query1 = "Select * From Medicine Where SupplierId = " + Id;
            ResultSet rs1 = st1.executeQuery(query1);

            ArrayList<Medicine> MedList = new ArrayList<>();

            while(rs1.next())
            {
                Medicine med = new Medicine(rs1.getInt("MedicineId"),rs1.getInt("Quantity"),rs1.getDouble("Price"),rs1.getDate("Expiry").toLocalDate(),rs1.getDate("Manufactor").toLocalDate(),rs1.getString("Name"),
                         rs1.getString("Status"),rs1.getString("SupplyStatus"),rs1.getString("Type"),rs1.getInt("SupplierId"));
                MedList.add(med);
            }
            
            return MedList;
    }
    
    public int getLastMedId() throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "SELECT MAX(MedicineId) FROM Medicine";
        ResultSet rs1 = st1.executeQuery(query1);

        int lastId = 0;
        if (rs1.next()) 
        {
            lastId = rs1.getInt(1);
        }
        
        return lastId;
    }
    
    public void AddMedicine(Medicine Med) throws SQLException
    {
        Statement st1 = con.createStatement();
        
        PreparedStatement ps = con.prepareStatement(
        "INSERT INTO Medicine (Name, Type, Quantity, Price, Expiry, Manufactor, Status, SupplyStatus, SupplierId) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        ps.setString(1, Med.getName());
        ps.setString(2, Med.getType());
        ps.setInt(3, Med.getQuantity());
        ps.setDouble(4, Med.getPrice());
        ps.setDate(5, java.sql.Date.valueOf(Med.getExpiry())); 
        ps.setDate(6, java.sql.Date.valueOf(Med.getManufactor()));
        ps.setString(7, Med.getStatus());
        ps.setString(8, Med.getSupplyStatus());
        ps.setInt(9, Med.getSup());

        ps.executeUpdate();

        System.out.println("Medicine Successfully Added");

    }
    
    public void AddSoldMedicine(ArrayList<SoldMedicine> Meds) throws SQLException
    {
        for(SoldMedicine Med: Meds)
        {
            AddSoldMedicine(Med);
        }
    }
    
    public void AddSoldMedicine(SoldMedicine Med) throws SQLException
    {
        
        Statement st2 = con.createStatement();
        
        String query1 = "Select MedicineId, Quantity From SoldMedicine Where MedicineId = " + Med.getMedicineId();
        
        ResultSet rs1 = st2.executeQuery(query1);

        if(rs1.next())
        {
            query1 = "Update SoldMedicine Set Quantity = " + (rs1.getInt("Quantity") + Med.getQuantity()) + " Where MedicineId = " + Med.getMedicineId();
            st2.executeUpdate(query1);
            
            return ;
        }
        
        Statement st1 = con.createStatement();
        
        PreparedStatement ps = con.prepareStatement(
        "INSERT INTO SoldMedicine (MedicineId,Name, Type, Quantity, Price, Expiry, Manufactor, Status, SupplyStatus, SupplierId, SupplierName) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        
        ps.setInt(1,Med.getMedicineId());
        ps.setString(2, Med.getName());
        ps.setString(3, Med.getType());
        ps.setInt(4, Med.getQuantity());
        ps.setDouble(5, Med.getPrice());
        ps.setDate(6, java.sql.Date.valueOf(Med.getExpiry())); 
        ps.setDate(7, java.sql.Date.valueOf(Med.getManufactor()));
        ps.setString(8, Med.getStatus());
        ps.setString(9, Med.getSupplyStatus());
        ps.setInt(10, Med.getSup());
        ps.setString(11, Med.getSupName());

        ps.executeUpdate();

        System.out.println("SoldMedicine Successfully Added");

    }
    
    public void RemoveMedicine(Medicine Med) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Update Medicine Set Status = 'Approved' Where MedicineId = " + Med.getMedicineId();
        st1.executeUpdate(query1);
        
        System.out.println("Medicine Successfully Removed");
        
    }
    
    
    public void DeleteMedicine(Medicine Med) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Delete From Medicine Where MedicineId = " + Med.getMedicineId();
        st1.executeUpdate(query1);
        
        System.out.println("Medicine Successfully Deleted");
        
    }
    
    public void AddMedicineStore(Medicine Med) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Update Medicine Set Status = 'Added' Where MedicineId = " + Med.getMedicineId();
        st1.executeUpdate(query1);
        
        System.out.println("Medicine Successfully Added to Store");
    }
    
    public void RestockMedicine(Medicine Med) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Update Medicine Set Quantity = " + Med.getQuantity() + " Where MedicineId = " + Med.getMedicineId();
        st1.executeUpdate(query1);
        
        System.out.println("Medicine Successfully Restocked");
    }
    
    public ArrayList<InventoryManager> GetAllManagers() throws SQLException
    {
       Statement st1 = con.createStatement();
        String query1 = "Select * From UserList Join InventoryManager on UserList.UserId = InventoryManager.UserId";
        ResultSet rs1 = st1.executeQuery(query1);
        
      
        ArrayList<Medicine> MedList = GetAllMedicines();
        ArrayList<InventoryManager> Manager = new ArrayList<>();

        while(rs1.next())
        {
            InventoryManager Man = new InventoryManager(
                rs1.getString("Username"),
                rs1.getString("Password"),
                rs1.getString("Name"),
                rs1.getString("Gender"),
                rs1.getInt("Age"),
                rs1.getString("Email"),
                rs1.getDate("DOB").toLocalDate(),
                rs1.getInt("IMId"),
                rs1.getString("Status"),
                MedList
            );

            Manager.add(Man);
        }

        System.out.println("InventoryManagers Successfully Fetched");
        
        return Manager;
    }
    
    public void ApproveManager(int Id) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Update InventoryManager Set Status = 'Approved' Where ImId = " + Id;
        st1.executeUpdate(query1);
        
        System.out.println("InventoryManager Successfully Approved");
    }
    
    public void RejectManager(int Id) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Update InventoryManager Set Status = 'Rejected' Where ImId = " + Id;
        st1.executeUpdate(query1);
        
        System.out.println("InventoryManager Successfully Rejected");
    }
    
    public void DeleteManager(int Id) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Delete UserList From UserList Join InventoryManager on InventoryManager.UserId = UserList.UserId Where InventoryManager.ImId = " + Id;
        st1.executeUpdate(query1);
        
        System.out.println("InventoryManager Successfully Deleted");
    }
    
    public ArrayList<Supplier> GetAllSupplier() throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Select * From UserList Join Supplier on UserList.UserId = Supplier.UserId";
        ResultSet rs1 = st1.executeQuery(query1);
        
        ArrayList<Supplier> Manager = new ArrayList<>();

        while(rs1.next())
        {
            Supplier Man = new Supplier(
                rs1.getString("Username"),
                rs1.getString("Password"),
                rs1.getString("Name"),
                rs1.getString("Gender"),
                rs1.getInt("Age"),
                rs1.getString("Email"),
                rs1.getDate("DOB").toLocalDate(),
                rs1.getInt("SupId"),
                rs1.getString("LicenseNumber"),
                rs1.getString("Status")
            );
            
            ArrayList<Medicine> MedList = GetSupMedicines(rs1.getInt("SupId"));
            Man.setSupMed(MedList);
            
            Manager.add(Man);
        }

        System.out.println("Suppliers Successfully Fetched");
        
        return Manager;
    }
    public void ApproveSupplier(int Id) throws SQLException 
    {
        Statement st1 = con.createStatement();
        String query1 = "UPDATE Supplier SET Status = 'Approved' WHERE SupId = " + Id;
        st1.executeUpdate(query1);

        System.out.println("Supplier Successfully Approved");
    }

    public void RejectSupplier(int Id) throws SQLException {
        Statement st1 = con.createStatement();
        String query1 = "UPDATE Supplier SET Status = 'Rejected' WHERE SupId = " + Id;
        st1.executeUpdate(query1);

        System.out.println("Supplier Successfully Rejected");
    }

    public void DeleteSupplier(int Id) throws SQLException {
        Statement st1 = con.createStatement();
        String query1 = "DELETE UserList FROM UserList JOIN Supplier ON Supplier.UserId = UserList.UserId WHERE Supplier.SupId = " + Id;
        st1.executeUpdate(query1);

        System.out.println("Supplier Successfully Deleted");
    }
    
    public ArrayList<Pharmacist> GetAllPharmacist() throws SQLException 
    {
        Statement st1 = con.createStatement();
        String query1 = "SELECT * FROM UserList JOIN Pharmacist ON UserList.UserId = Pharmacist.UserId";
        ResultSet rs1 = st1.executeQuery(query1);

        ArrayList<Pharmacist> pharmacistList = new ArrayList<>();
        ArrayList<Prescription> Pres = GetAllPrescriptions();

        while (rs1.next()) {
            Pharmacist pharmacist = new Pharmacist(
                rs1.getString("Username"),
                rs1.getString("Password"),
                rs1.getString("Name"),
                rs1.getString("Gender"),
                rs1.getInt("Age"),
                rs1.getString("Email"),
                rs1.getDate("DOB").toLocalDate(),
                rs1.getInt("PharmaId"),
                rs1.getString("LicenseNumber"),
                rs1.getString("Status")
            );

            pharmacist.setPrescriptions(Pres);
            
            pharmacistList.add(pharmacist);
        }

        System.out.println("Pharmacists Successfully Fetched");

        return pharmacistList;
    }
    
    public ArrayList<Medicine> GetPresMed(int Pid) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Select Medicine.* From Medicine Join PresMed on PresMed.MedicineId = Medicine.MedicineId "
                + "where PresMed.PresciId = " + Pid;
        ResultSet rs1 = st1.executeQuery(query1);

        ArrayList<Medicine> MedList = new ArrayList<>();

        while(rs1.next())
        {
            Medicine med = new Medicine(rs1.getInt("MedicineId"),rs1.getInt("Quantity"),rs1.getDouble("Price"),rs1.getDate("Expiry").toLocalDate(),rs1.getDate("Manufactor").toLocalDate(),rs1.getString("Name"),
                     rs1.getString("Status"),rs1.getString("SupplyStatus"),rs1.getString("Type"),rs1.getInt("SupplierId"));
            MedList.add(med);
        }

        return MedList;
    }
    
    public ArrayList<Prescription> GetAllPrescriptions() throws SQLException
    {
        Statement st = con.createStatement();

        String query = "SELECT * FROM Prescription";

        ResultSet rs = st.executeQuery(query);
        
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        
        while (rs.next()) 
        {
            int presciId = rs.getInt("PresciId");
            int quantity = rs.getInt("Quantity");
            LocalDate addDate = rs.getDate("AddDate").toLocalDate();  
            String patientName = rs.getString("PatientName");
            String type = rs.getString("Type");
            String doctorName = rs.getString("DoctorName");
            String hospitalName = rs.getString("HospitalName");

            Prescription p = new Prescription(presciId, quantity, addDate, patientName, type, doctorName, hospitalName);
            
            ArrayList<Medicine> Meds = GetPresMed(presciId);
            
            p.setMeds(Meds);
            
            prescriptions.add(p);
        }
        
        System.out.println("Prescriptions Successfully Fetched");
        
        return prescriptions;
    }
    
    public void RemovePrescription(Prescription Pres) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Delete From Prescription Where PresciId = " + Pres.getPresciId();
        st1.executeUpdate(query1);
        
        System.out.println("Prescription Successfully Deleted");
    }
    
    public ArrayList<Owner> GetAllOwner() throws SQLException 
    {
        Statement st1 = con.createStatement();
        String query1 = "SELECT * FROM UserList JOIN Owner ON UserList.UserId = Owner.UserId";
        ResultSet rs1 = st1.executeQuery(query1);

        ArrayList<Owner> ownerList = new ArrayList<>();

        while (rs1.next()) {
            Owner owner = new Owner(
                rs1.getString("Username"),
                rs1.getString("Password"),
                rs1.getString("Name"),
                rs1.getString("Gender"),
                rs1.getInt("Age"),
                rs1.getString("Email"),
                rs1.getDate("DOB").toLocalDate(),
                rs1.getInt("OwnerId")
            );

            ownerList.add(owner);
        }

        System.out.println("Owners Successfully Fetched");

        return ownerList;
    }

    
    public void ApprovePharmacist(int Id) throws SQLException {
        Statement st1 = con.createStatement();
        String query1 = "UPDATE Pharmacist SET Status = 'Approved' WHERE PharmaId = " + Id;
        st1.executeUpdate(query1);

        System.out.println("Pharmacist Successfully Approved");
    }

    public void RejectPharmacist(int Id) throws SQLException {
        Statement st1 = con.createStatement();
        String query1 = "UPDATE Pharmacist SET Status = 'Rejected' WHERE PharmaId = " + Id;
        st1.executeUpdate(query1);

        System.out.println("Pharmacist Successfully Rejected");
    }

    public void DeletePharmacist(int Id) throws SQLException {
        Statement st1 = con.createStatement();
        String query1 = "DELETE UserList FROM UserList JOIN Pharmacist ON Pharmacist.UserId = UserList.UserId WHERE Pharmacist.PharmaId = " + Id;
        st1.executeUpdate(query1);

        System.out.println("Pharmacist Successfully Deleted");
    }
    
    public void ApproveMedicine(int Id) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Update Medicine Set Status = 'Approved' Where MedicineId = " + Id;
        st1.executeUpdate(query1);
        
        System.out.println("Medicine Successfully Approved");
    }
    
    public void RejectMedicine(int Id) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Update Medicine Set Status = 'Rejected' Where MedicineId = " + Id;
        st1.executeUpdate(query1);
        
        System.out.println("Medicine Successfully Rejected");
    }
    
    public void StartSupply(int Id) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Update Medicine Set SupplyStatus = 'Started' Where MedicineId = " + Id;
        st1.executeUpdate(query1);
        
        System.out.println("Medicine Successfully Started");
    }
    public void StopSupply(int Id) throws SQLException
    {
        Statement st1 = con.createStatement();
        String query1 = "Update Medicine Set SupplyStatus = 'Stopped' Where MedicineId = " + Id;
        st1.executeUpdate(query1);
        
        System.out.println("Medicine Successfully Stopped");
    }
    
    public int getLastPrescriptionId() throws SQLException 
    {
        Statement st1 = con.createStatement();
        String query1 = "SELECT MAX(PresciId) FROM Prescription";  
        ResultSet rs1 = st1.executeQuery(query1);

        int lastId = 0;
        if (rs1.next()) 
        {
            lastId = rs1.getInt(1);  
        }

        return lastId;
    }
    
    public void AddPrescription(Prescription Pres) throws SQLException 
    {
        String query = "INSERT INTO Prescription (PresciId, Quantity, AddDate, PatientName, Type, DoctorName, HospitalName) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(query)) 
        {
            stmt.setInt(1, Pres.getPresciId());
            stmt.setInt(2, Pres.getQuantity());          // Quantity
            stmt.setDate(3, Date.valueOf(Pres.getAddDate()));  // Add Date (LocalDate to SQL Date)
            stmt.setString(4, Pres.getPatientName());    // Patient Name
            stmt.setString(5, Pres.getType());           // Type
            stmt.setString(6, Pres.getDoctorName());     // Doctor Name
            stmt.setString(7, Pres.getHospitalName()); 

            int rowsAffected = stmt.executeUpdate();
            
            AddPrescMed(Pres.getPresciId(),Pres.getMeds());
            
            
            if (rowsAffected > 0) 
            {
                System.out.println("Prescription successfully added.");
            } 
            else 
            {
                System.out.println("Failed to add prescription.");
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("Error adding prescription: " + e.getMessage());
            throw e; 
        }
    }

    public void AddPrescMed(int PresciId, ArrayList<Medicine> Med) throws SQLException
    {
        String query = "INSERT INTO PresMed (PresciId, MedicineId) VALUES (?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(query)) 
        {
            for (Medicine medicine : Med) 
            {
                stmt.setInt(1, PresciId);  
                stmt.setInt(2, medicine.getMedicineId()); 

                stmt.addBatch(); 
            }

            int[] rowsAffected = stmt.executeBatch();

            System.out.println("Successfully added " + rowsAffected.length + " medicines to PresMed.");
        } 
        catch (SQLException e) 
        {
            System.out.println("Error adding medicines to PresMed: " + e.getMessage());
            throw e;  
        }
    }
    
    public boolean CheckUserName(String name) throws SQLException
    {
       String query = "SELECT COUNT(*) FROM UserList WHERE LOWER(username) = LOWER(?)";
    
       PreparedStatement ps = con.prepareStatement(query);
       
       ps.setString(1, name);

       try (ResultSet rs = ps.executeQuery()) 
       {
            if (rs.next() && rs.getInt(1) > 0) 
            {
                return false;
            }
       }
       
       return true;
    }
    
    public boolean CheckEmail(String name) throws SQLException
    {
       String query = "SELECT COUNT(*) FROM UserList WHERE LOWER(Email) = LOWER(?)";
    
       PreparedStatement ps = con.prepareStatement(query);
       
       ps.setString(1, name);

       try (ResultSet rs = ps.executeQuery()) 
       {
            if (rs.next() && rs.getInt(1) > 0) 
            {
                return false;
            }
       }
       
       return true;
    }
    
    public boolean CheckLicenseNumberSup(String License) throws SQLException
    {
       String query = "SELECT COUNT(*) FROM Supplier WHERE LOWER(LicenseNumber) = LOWER(?)";
    
       PreparedStatement ps = con.prepareStatement(query);
       
       ps.setString(1, License);

       try (ResultSet rs = ps.executeQuery()) 
       {
            if (rs.next() && rs.getInt(1) > 0) 
            {
                return false;
            }
       }
       
       return true;
    }
    
    public boolean CheckLicenseNumberPhar(String License) throws SQLException
    {
       String query = "SELECT COUNT(*) FROM Pharmacist WHERE LOWER(LicenseNumber) = LOWER(?)";
    
       PreparedStatement ps = con.prepareStatement(query);
       
       ps.setString(1, License);

       try (ResultSet rs = ps.executeQuery()) 
       {
            if (rs.next() && rs.getInt(1) > 0) 
            {
                return false;
            }
       }
       
       return true;
    }
    
    public void WriteUser(String username, String password, String name, String gender, int age, String email, LocalDate dob) throws SQLException
    {
            String query = "INSERT INTO UserList (username, password, name, gender, age, email, dob) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, name);
            ps.setString(4, gender);
            ps.setInt(5, age);
            ps.setString(6, email);
            ps.setDate(7, java.sql.Date.valueOf(dob));

            ps.executeUpdate();
    }
    
    public int GetLastIMId() throws SQLException 
    {
        Statement st1 = con.createStatement();
        String query1 = "SELECT MAX(IMId) FROM InventoryManager";  
        ResultSet rs1 = st1.executeQuery(query1);

        int lastId = 0;
        if (rs1.next()) 
        {
            lastId = rs1.getInt(1);  
        }

        return lastId;
    }
    
    public int GetLastSupId() throws SQLException 
    {
        Statement st1 = con.createStatement();
        String query1 = "SELECT MAX(SupId) FROM Supplier";  
        ResultSet rs1 = st1.executeQuery(query1);

        int lastId = 0;
        if (rs1.next()) 
        {
            lastId = rs1.getInt(1);  
        }

        return lastId;
    }
    
    public int GetLastPharmaId() throws SQLException 
    {
        Statement st1 = con.createStatement();
        String query1 = "SELECT MAX(PharmaId) FROM Pharmacist";  
        ResultSet rs1 = st1.executeQuery(query1);

        int lastId = 0;
        if (rs1.next()) 
        {
            lastId = rs1.getInt(1);  
        }

        return lastId;
    }
    
    public int getUserIdFromUsername(String username) throws SQLException 
    {
        String query = "SELECT * FROM UserList WHERE Lower(username) = ?";
 
        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, username.toLowerCase());

        try (ResultSet rs = ps.executeQuery()) 
        {
            if (rs.next()) 
            {
                return rs.getInt("UserId");
            }
        }
        return -1; 
    }
    
    public void WriteInventoryManager(InventoryManager Manager) throws SQLException
    {
        int userId = getUserIdFromUsername(Manager.getUsername());

        if (userId != -1) 
        {
            String query = "INSERT INTO InventoryManager (UserId, Status) VALUES (?, ?)";
 
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setString(2, Manager.getStatus());
            
             ps.executeUpdate();
        } 
        else 
        {
            System.out.println("User not found. Please check the username.");
        }
    }
    
    public void WriteSupplier(Supplier Manager) throws SQLException
    {
        int userId = getUserIdFromUsername(Manager.getUsername());

        if (userId != -1) 
        {
            String query = "INSERT INTO Supplier (UserId, LicenseNumber, Status) VALUES (?, ?, ?)";
 
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setString(2, Manager.getLicenseNumber());
            ps.setString(3, Manager.getStatus());
            
            ps.executeUpdate();
        } 
        else 
        {
            System.out.println("User not found. Please check the username.");
        }
    }
    
    public void WritePharmacist(Pharmacist Manager) throws SQLException
    {
        int userId = getUserIdFromUsername(Manager.getUsername());

        if (userId != -1) 
        {
            String query = "INSERT INTO Pharmacist (UserId, LicenseNumber, Status) VALUES (?, ?, ?)";
 
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setString(2, Manager.getLicenseNumber());
            ps.setString(3, Manager.getStatus());
            
            ps.executeUpdate();
        } 
        else 
        {
            System.out.println("User not found. Please check the username.");
        }
    }
    
};