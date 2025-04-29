package Backend;

import java.time.LocalDate;
import java.util.ArrayList;

public class SoldMedicine 
{
	int MedicineId;
	int Quantity;
	double Price;
	LocalDate Expiry;
	LocalDate Manufactor;
	String Name;
	String Status = "Solds";
	String SupplyStatus = "Stopped";
	String Type;
        int SupId;
	String SupName; 
	
	// Status = Sold
	// SupplyStatus = Started, Stopped
	
	public SoldMedicine()
	{
		
	}

	public SoldMedicine(int medicineId, int quantity, double price, LocalDate expiry, LocalDate manufactor, String name,
			String status, String supplyStatus, String type, int sup, String Sup) 
	{
		super();
		MedicineId = medicineId;
		Quantity = quantity;
		Price = price;
		Expiry = expiry;
		Manufactor = manufactor;
		Name = name;
		Status = status;
		SupplyStatus = supplyStatus;
		Type = type;
		SupId = sup;
                SupName = Sup;
	}
        
        public SoldMedicine(Medicine other,String SupName) 
        {
            this.MedicineId = other.MedicineId;
            this.Quantity = other.Quantity;
            this.Price = other.Price;
            this.Expiry = other.Expiry;
            this.Manufactor = other.Manufactor;
            this.Name = other.Name;
            this.Status = other.Status;
            this.SupplyStatus = other.SupplyStatus;
            this.Type = other.Type;
            this.SupId = other.SupId;
            this.SupName = SupName;
        }

        
        public String getSupplierName(ArrayList<Supplier> SupList)
        {
            String Name = "";
            
            for(Supplier Sup: SupList)
            {
                if(Sup.getSupId() == SupId)
                {
                    Name = Sup.getName();
                    break;
                }
            }
            
            
            return Name;
        }
        
        
	public int getMedicineId() {
		return MedicineId;
	}

	public void setMedicineId(int medicineId) {
		MedicineId = medicineId;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		Price = price;
	}

	public LocalDate getExpiry() {
		return Expiry;
	}

	public void setExpiry(LocalDate expiry) {
		Expiry = expiry;
	}

	public LocalDate getManufactor() {
		return Manufactor;
	}

	public void setManufactor(LocalDate manufactor) {
		Manufactor = manufactor;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getSupplyStatus() {
		return SupplyStatus;
	}

	public void setSupplyStatus(String supplyStatus) {
		SupplyStatus = supplyStatus;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public int getSup() {
		return SupId;
	}

	public void setSup(int sup) {
		SupId = sup;
	}
        
        public String getSupName()
        {
            return SupName;
        }
		
}
