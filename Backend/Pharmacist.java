package Backend;

import java.time.LocalDate;

public class Pharmacist extends User
{
	int PharmaId;
	String Status;
	String LicenseNumber;
	
	public Pharmacist(String Us, String Pass, String Nam, String Gender1, int age1, String Ema, LocalDate DateofBirth,
			int pharmaId, String status, String licenseNumber) 
	{
		super(Us, Pass, Nam, Gender1, age1, Ema, DateofBirth);
		PharmaId = pharmaId;
		Status = status;
		LicenseNumber = licenseNumber;
	}
	
	
}
