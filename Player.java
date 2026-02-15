/**
 * Description: a class for player objects to add to the arraylist of all the players that includes their name, ticket balance, and payment(its own 
 * object)
 *
 * @author (Harsh Desai)
 * @version (Dec 5 - 15th, 2025)
 */
public class Player
{
    //declare instance variables
    private String strName;
    private int intTickets;
    private Payment payment;
    
    //code a constructor
    public Player(String n)
    {
        //intialize name variable with incoming input, and set the other two to default
        this.strName = n;
        this.intTickets = 0;
        this.payment = null;
    }
    
    //code getters for the name and tickets and payment
    public String getName()
    {
        return this.strName;
    }
    
    public Payment getPayment()
    {
        return this.payment;
    }
    
    public int getTickets()
    {
        return this.intTickets;
    }
    
    //code a void method that adds incoming tickets to the users balance
    public void addTickets(int amount)
    {
        //add to the balance
        this.intTickets += amount;
    }
    
    //code a void method to store the players credit card information to the their player object
    public void setPayment(Payment payment)
    {
        this.payment = payment;
    }
    
    
}