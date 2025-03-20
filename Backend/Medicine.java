package Backend;

import java.time.LocalDate;

public class Medicine 
{
	int MedicineId;
	int Quantity;
	double Price;
	LocalDate Expiry;
	LocalDate Manufactor;
	String Name;
	String Status = "Pending";
	String SupplyStatus = "Started";
	String Type;
	Supplier Sup; 
	
	// Status = Pending, Approved, Added
	// SupplyStatus = Started, Stopped
	
	public Medicine()
	{
		
	}

	public Medicine(int medicineId, int quantity, double price, LocalDate expiry, LocalDate manufactor, String name,
			String status, String supplyStatus, String type, Supplier sup) 
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
		Sup = sup;
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

	public Supplier getSup() {
		return Sup;
	}

	public void setSup(Supplier sup) {
		Sup = sup;
	}

	
	
}
