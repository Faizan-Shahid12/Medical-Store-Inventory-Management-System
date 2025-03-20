package Backend;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller 
{
	private static InventoryManager Manager;
	private static Supplier Sup;
	
	public Controller()
	{
		
	}
	
	public void AddRequest(String Name, String Type, int quan,double price, LocalDate expiry, LocalDate Manufac)
	{
		Medicine Med = Sup.AddRequest(Name, Type, quan, price, expiry, Manufac);
		
	}
	
	public void RemoveRequest(Medicine Med)
	{
		Sup.RemoveRequest(Med);
	}
	
	public void StopSupply(Medicine Med)
	{
		Sup.StopSupply(Med);
		Med.setSupplyStatus("Stopped");
	}
	public void StartSupply(Medicine Med)
	{
		Sup.StartSupply(Med);
		Med.setSupplyStatus("Started");
	}
	
	public void AddMedicine(Medicine Med)
	{
		Manager.AddMedicine(Med);
	}
	
}
