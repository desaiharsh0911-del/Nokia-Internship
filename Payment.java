/**
 * Description: a class that holds an object payment that stores the information for the card they're paying with, 
 * and has methods to check and verify their information - each player object will have a copy of this
 *
 * @author (Harsh Desai)
 * @version (Dec 5 - 15th, 2025)
 */
public class Payment
{
    //declare instance variables
    private String strCardNum;
    private String strExpiry;
    private String strCVV;
    
    //code a constructor  
    public Payment(String cn, String e, String cvv)
    {
        this.strCardNum = cn;
        this.strExpiry = e;
        this.strCVV = cvv;
    }
    
    public Payment()
    {
        
    }
    //code a method of return type boolean to check if the card info is valid
    public boolean isValid()
    {
        //declare instance variable of type boolean to return if it is valid
        boolean bolValid = true;
        
        //if statement that checks each of the card details 
        if(this.strCardNum == null || this.strExpiry == null || this.strCVV == null) //checking to see if the variables are empty
        {
            bolValid = false;
        }
        
        else if(this.strCardNum.length() != 16)
        {
            bolValid = false;
        }
        
        else if(this.strExpiry.length() != 5 || this.strExpiry.charAt(2) != '/')
        {
            bolValid = false;
        }
        
        else if(this.strCVV.length() != 3)
        {
            bolValid = false;
        }
        
        return bolValid;
    }
    
    //code setters for all three instance variables
    public void setCardNum(String cn)
    {
        this.strCardNum = cn;
    }
    
    public void setExpiry(String e)
    {
        this.strExpiry = e;
    }
    
    public void setCVV(String cvv)
    {
        this.strCVV = cvv;
    }
    
    //code getters for all three instance variables
    public String getCardNum()
    {
        return this.strCardNum;
    }
    
    public String getExpiry()
    {
        return this.strExpiry;
    }
    
    public String getCVV()
    {
        return this.strCVV;
    }

}















