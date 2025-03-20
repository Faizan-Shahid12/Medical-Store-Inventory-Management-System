package Backend;

import java.time.LocalDate;
import java.util.ArrayList;

public class Owner extends User
{
	int OwnerId;

	public Owner() 
	{
		super();

	}

	public Owner(String Us, String Pass, String Nam, String Gender1, int age1, String Ema, LocalDate DateofBirth, int ownerId) 
	{
		super(Us, Pass, Nam, Gender1, age1, Ema, DateofBirth);
		OwnerId = ownerId;
	}
	
	public void CreateInventoryManager(String Us, String Pass, String Nam, String Gender,int age1, String Ema, LocalDate DateofBirth,int Id, ArrayList<Medicine> Meds) 
    {
       // DBHandler DB = DBHandler.getInstance();
        
       // DB.WriteUser(Us, Pass, Nam, Gender, age1, Ema, DateofBirth);
        
        //int Id = DB.GetLastIMId() + 1;
        
        InventoryManager Manage = new InventoryManager(Us,Pass,Nam,Gender,age1,Ema,DateofBirth,Id, "Pending", Meds);
        
        //DB
        
    }
	
	public void CreateSupplier(String Us, String Pass, String Nam, String Gender1, int age1, String Ema, LocalDate DateofBirth, int supId, String licenseNumber, String status) 
    {
		// DBHandler DB = DBHandler.getInstance();
        
        // DB.WriteUser(Us, Pass, Nam, Gender, age1, Ema, DateofBirth);
        
        // int Id = DB.GetLastSuId() + 1;
        
        Supplier Sup = new Supplier(Us,Pass,Nam,Gender1,age1,Ema,DateofBirth,supId, licenseNumber ,"Pending");
        
        //DB
    }
	
	public void CreatePharmacist(String Us, String Pass, String Nam, String Gender1, int age1, String Ema, LocalDate DateofBirth, int PharmaId, String licenseNumber, String status) 
    {
		// DBHandler DB = DBHandler.getInstance();
        
        // DB.WriteUser(Us, Pass, Nam, Gender, age1, Ema, DateofBirth);
        
        // int Id = DB.GetLastPharmaId() + 1;
        
		Pharmacist  Pharma = new Pharmacist(Us,Pass,Nam,Gender1,age1,Ema,DateofBirth,PharmaId,"Pending", licenseNumber);
        
        //DB
    }
}
