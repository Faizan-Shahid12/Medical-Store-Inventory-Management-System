package Backend;

import Database.DBHandler;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Pharmacist extends User
{
	int PharmaId;
	String Status;
	String LicenseNumber;
	ArrayList<Prescription> Prescriptions;
        
        DBHandler DB = DBHandler.getInstance();
        
        public Pharmacist()
        {
            
        }
        
	public Pharmacist(String Us, String Pass, String Nam, String Gender1, int age1, String Ema, LocalDate DateofBirth,
			int pharmaId,String licenseNumber, String status) 
	{
		super(Us, Pass, Nam, Gender1, age1, Ema, DateofBirth);
		PharmaId = pharmaId;
		Status = status;
		LicenseNumber = licenseNumber;
                Prescriptions = new ArrayList<>();
	}
	
        public String getStatus()
        {
            return Status;
        }
        
        public int getPharmaId()
        {
            return PharmaId;
        }
        
        public void setPrescriptions(ArrayList<Prescription> pres)
        {
            Prescriptions = pres;
        }
        
        public ArrayList<Prescription> getPrescription()
        {
            return Prescriptions;
        }
        
        public String getLicenseNumber() {
            return LicenseNumber;
        }

        public void setLicenseNumber(String LicenseNumber) {
            this.LicenseNumber = LicenseNumber;
        }
        
        public void AddPrescription(int Quantity, LocalDate AddDate, String PatientName, String Type, String DoctorName, String HospitalName, ArrayList<Medicine> Meds) throws SQLException
        {
            int PharId = DB.getLastPrescriptionId();
            PharId++;
            Prescription Pres = new Prescription(PharId,Quantity,AddDate,PatientName,Type,DoctorName,HospitalName);
            Pres.setMeds(Meds);
            Prescriptions.add(Pres);
            DB.AddPrescription(Pres);
        }
        
        public double CalculateTotal(ArrayList<Medicine> Meds, int quan)
        {
            double total = 0.0;
            
            for(Medicine Med: Meds)
            {
                total += Med.getPrice();
            }
            
            return total*quan;
        }
        
        public double CalculateTotal(SoldMedicine Med)
        {
            double total = Med.getQuantity() * Med.getPrice();
            
            return total;
        }
        
        public double CalculateTotal(int Quan, double Price)
        {
            return Quan*Price;
        }
	
        public void DeletePrescription(Prescription Pres) throws SQLException
        {
            Prescriptions.remove(Pres);
            DB.RemovePrescription(Pres);
        }
        
        public boolean CheckQuan(ArrayList<Medicine> Meds, int Quan)
        {
            for(Medicine Med: Meds)
            {
                if( Med.getQuantity() != 0 && Med.getQuantity() >= Quan)
                {
                    continue;
                }
                
                return false;
            }
            
            return true;
        }
        
        public void SellMedicine(ArrayList<SoldMedicine> Meds,ArrayList<Medicine> Meds1) throws SQLException
        {
            for(SoldMedicine Med : Meds)
            {
                for(Medicine Med1 : Meds1)
                {
                    if(Med1.getMedicineId() == Med.getMedicineId())
                    {
                        int quan = Med1.getQuantity() - Med.getQuantity();
                        Med1.setQuantity(quan);
                        Med.setStatus("Sold");
                        Med.setSupplyStatus("Stopped");
                        
                        DB.RestockMedicine(Med1);

                        break;
                    }
                }
            }
            
            DB.AddSoldMedicine(Meds);
        }
        
        public void SellPrescription(Prescription Pres) throws SQLException
        {
            Controller Cont = new Controller();
            
            for(Prescription Pres1 : Prescriptions)
            {
                if(Pres1.getPresciId() == Pres.getPresciId())
                {
                    ArrayList<SoldMedicine> Meds = new ArrayList<>();
                
                    for(Medicine Med : Pres.getMeds())
                    {
                        SoldMedicine Med1 = new SoldMedicine(Med,Cont.getSupName(Med.getMedicineId()));
                        Med1.setQuantity(Med.getQuantity() - Pres.getQuantity());
                        Med.setQuantity(Med.getQuantity()-Pres.getQuantity());
                        
                        Med.setStatus("Sold");
                        Med.setSupplyStatus("Stopped");
                        
                        DB.RestockMedicine(Med);
                        Meds.add(Med1);
                    }
                    
                    DB.AddSoldMedicine(Meds);
                    
                    break;
                }
                
            }
        }
        
}
