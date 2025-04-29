package Backend;

import java.time.LocalDate;

public abstract class User 
{
    private String Username;
    private String Password;
    private String Name;
    private String Gender;
    private int Age;
    private String Email;
    private LocalDate DOB;
    
    User()
    {
    }
    
    User(String Us,String Pass,String Nam,String Gender1,int age1,String Ema,LocalDate DateofBirth)
    {
        Username = Us;
        Password = Pass;
        Name = Nam;
        Gender = Gender1;
        Age = age1;
        Email = Ema;
        DOB = DateofBirth;
    }
    
    public void setUsername(String Username)
    {
        this.Username = Username;
    }

    public void setPassword(String Password) 
    {
        this.Password = Password;
    }

    public void setName(String Name) 
    {
        this.Name = Name;
    }

    public void setAge(int Age)
    {
        this.Age = Age;
    }

    public void setEmail(String Email)
    {
        this.Email = Email;
    }

    public void setDOB(LocalDate DOB) 
    {
        this.DOB = DOB;
    }

    public String getUsername() 
    {
        return Username;
    }

    public String getPassword() 
    {
        return Password;
    }

    public String getName() 
    {
        return Name;
    }

    public int getAge() 
    {
        return Age;
    }

    public String getEmail() 
    {
        return Email;
    }

    public LocalDate getDOB() 
    {
        return DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }
}
