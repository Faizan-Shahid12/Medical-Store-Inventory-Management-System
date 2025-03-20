package Backend;

import java.time.LocalDate;
import java.util.ArrayList;

public class InventoryManager extends User
{
	int IMId;
	String Status;
	ArrayList<Medicine> Medicines;
	
	public InventoryManager()
	{
		Medicines = new ArrayList<Medicine>();
	}
	
	public InventoryManager(String Us, String Pass, String Nam, String Gender1, int age1, String Ema,
			LocalDate DateofBirth, int iMId, String status, ArrayList<Medicine> medicines) 
	{
		super(Us, Pass, Nam, Gender1, age1, Ema, DateofBirth);
		IMId = iMId;
		Status = status;
		Medicines = medicines;
	}
	
	public void AddMedicine(Medicine Med)
	{
		for(Medicine Med1 : Medicines)
		{
			if(Med1 == Med)
			{
				Med1.setStatus("Added");
			}
		}
		//In DB (Update Status)
	}
	
	public void RemoveMedicine(Medicine Med)
	{
		//In DB
		Medicines.remove(Med);
	}
	
	public ArrayList<Medicine> IdentifyExpired()
	{
		ArrayList<Medicine> Expired = new ArrayList<Medicine>();
		
		for(Medicine Med: Medicines)
		{
			if(Med.getExpiry().isAfter(LocalDate.now()))
			{
				Expired.add(Med);
			}
		}
		
		return Expired;
	}
	
	public void Restock(Medicine Med, int quantity)
	{
		for(Medicine Med1: Medicines)
		{
			if(Med1 == Med)
			{
				int Quan = Med1.getQuantity();
				Quan += quantity;
				Med1.setQuantity(Quan);
				//DB
				break;
			}
		}
	}

}
