
/**
 * Description: This class represents a symbol in the slot machine. Each symbol 
 * has a emoji and a value that determines the payout
 *
 * Harsh Desai
 * Dec. 4, 2025
 */
public class Symbol
{
   //declare a vraibale of type string to store the symbol's name - emoji
   private String strName;
   
   //declare a variable of type int to store its value 
   private  int intValue;
   
   //constructor 
   public Symbol(String n, int v)
   {
       this.strName = n;
       this.intValue = v;
   }

   //returns the name of the symbol
   public String getName()
   {
       return this.strName;
   }
   
   //returns the value fo teh symbol
   public int getValue()
   {
       return this.intValue;
   }
   
   //toString method to ouput the string's name 
   public String toString()
   {
       return this.strName;
   }
   
}