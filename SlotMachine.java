
/**
 * Description: This class simulates a slot machine game where a player place bets,
 * spin the reels, earn payout, and track their stats.
 *
 * Harsh Desai
 * Dec. 5, 2025
 */

import java.util.ArrayList;
import java.io.*;
import java.util.*;


public class SlotMachine
{
   //Array of symbols on the slot machine
   Symbol[] aSymbols;
   
   //declare variable of type arraylist and store recent payout history
   private ArrayList<Integer> alRecentPayout = new ArrayList<>();
   
   //declare variable of type int to store the points and the player bets per spin
   private int intPoints;
   private int intSpinCost;
   
   //declare varibale of type String to store the level of difficulty and player's name
   private String strLevel;
   private String strPName;
   
   
   //default constructor - initializes player's name, level, starting points, spin cost, recent payout and symbols
   public SlotMachine()
   {
       intPoints = 0;
       intSpinCost = 0;
       strLevel = "";
       strPName = "";
       alRecentPayout = new ArrayList<>();
       aSymbols = null;
   }
   

   //loads an array of symbols used in slot machine - each emoji has a certain value
   public void loadSymbols()
   {
       aSymbols = new Symbol[]{
           new Symbol("🍒", 10),
           new Symbol("?", 30),
           new Symbol("🔔", 50),
           new Symbol("💎", 100),
           new Symbol("★", 60),
           new Symbol("🌶", 20)
       };
   }  
   
   //randomly selects and returns a symbol from the symbol list
   public Symbol pickSymbol()
   {
        int index = (int)(Math.random()*aSymbols.length);
        return aSymbols[index];
   }
   
   //recursively prints the spinning animation
   public void animateSpin(int n)
   {
       if(n==0)
       {
           return;
       }
       
       System.out.println("Spinning....." + pickSymbol() + ","  + pickSymbol() + "," + pickSymbol() + "," );
       
       animateSpin(n-1);
   }
   
   //finds number of matches and returns highest value symbol involved
   public Symbol findingWinSymbol(Symbol a, Symbol b, Symbol c)
   {
        if(a.getName().equals(b.getName()) && b.getName().equals(c.getName()))
        {
            return a;
        }
           
        else if(a.getName().equals(b.getName()))
        {
            return a;
        }
           
        else if(a.getName().equals(c.getName()))
        {
            return a;
        }
        else if(b.getName().equals(c.getName()))
        {
            return b;
        }
       
        return null;
   }
   
   //checks for matching symbol - returns 3 if all match, 2 if two match
   //and 0 if no match
   public int checkWin(Symbol a, Symbol b, Symbol c)
   {
       if(a.getName().equals(b.getName()) && b.getName().equals(c.getName()))
       {
           return 3;
       }
       
       else if(a.getName().equals(b.getName()) || a.getName().equals(c.getName()) || b.getName().equals(c.getName()))
       {
           return 2;
       }
       return 0;
   }
   
   //calculates payout based on matches and game level
   //Easy mode pays more, and Hard pays less
   public int calculatePayout(int matches, Symbol s)
   {
       if(matches == 0 || s == null )
       {
           return 0;
       }
       
       int base = s.getValue();

       if(strLevel.equals("Easy"))
       {
           if(matches == 3)
           {
               return base*3;
           }
           
           if(matches == 2)
           {
               return base*2;
           }
       }
       
       else
       {
           if(matches == 3)
           {
               return base*2;
           }
           if(matches == 2)
           {
               return base/2;
           }
       }
       
       return 0;
   }
   
   //performs the spin - deducts cose, shows spin results, calculate payouts.
   public void spin()
   {
       System.out.println(strPName + " is spinning...");
       
       //stops betting more than the available points
       if(intSpinCost>intPoints)
       {
           System.out.println("Not enough points for spin.");
           intSpinCost = intPoints;
        }
       
       if(intPoints <= 0)
        {
            System.out.println("No points left.");
            return;
        }
       
       //deducts the spin cost
       intPoints-=intSpinCost;
         
       //pick 3 symbols
       Symbol s1 = pickSymbol();
       Symbol s2 = pickSymbol();
       Symbol s3 = pickSymbol();
         
       animateSpin(3);
         
       System.out.println("Result: " + s1 + "," + s2 + "," + s3);
         
       int intMatches = checkWin(s1, s2, s3);
       Symbol winningSymbol = findingWinSymbol(s1, s2, s3);
       
       int intPayout = calculatePayout(intMatches, winningSymbol);
         
       //add payouts to points
       intPoints += intPayout;
       alRecentPayout.add(intPayout);  
         
       if(intPayout>0)
       {
              System.out.println("You won: " + intPayout +"points!");
       }
         
       else
       {
              System.out.println("No Win!");
       }
         
       System.out.println("Current Points: " + intPoints);
   }
   
   
   //main game loop - handles betting, spinning, and quitting
   public void play()
   {
       Scanner in = new Scanner(System.in);
       loadSymbols();
       System.out.println("Welcome " +  strPName + " ! Starting points: " + intPoints);
       
       String strInput = " ";
       while(!strInput.equalsIgnoreCase("quit") && intPoints > 0)
       {
            System.out.println("\nCurrent Points: " + intPoints);
            System.out.print("Enter points to bet this spin: ");
            intSpinCost = in.nextInt();
            in.nextLine();
       
               
            if(intSpinCost>intPoints)
            {
               System.out.println("You don't have enough points! Betting all remaining points instead.");
               intSpinCost = intPoints;
            }
             
            spin();
           
            if(intPoints <= 0)
            {
                System.out.println("No points left. Game over.");
                strInput = "quit";
            }
            else
            {
                // Ask if the player wants to continue
                System.out.print("Press ENTER to spin again or type 'quit' to exit: ");
                strInput= in.nextLine();
           
            }
               
       }
           
       System.out.println("Exiting Slot Machine. Current points: " + intPoints);
    }

   //Play slotMachine for a specific player
   public int run(Player player)
   {
       Scanner in = new Scanner(System.in);
       
       System.out.println("Choose difficulty for " + player.getName() + " (Easy or Hard): ");
       strLevel = in.nextLine();

       
       this.strPName = player.getName();
       int startingPoints= player.getTickets();
       this.intPoints = startingPoints;
       
       if (this.intPoints == 0)
       {
            this.intPoints = 100; // default starting points for the game
       }
       play();
       
       // returns the points earned
       return intPoints-startingPoints;
    }
   
   //returns current player points
   public int getPoints()
   {
        return intPoints;
   }
   
   //returns list of recent payouts
   public ArrayList<Integer> getRecentPayouts()
   {
       return alRecentPayout;
   }
   
   //returns spin cost
   public int getSpinCost()
   {
        return intSpinCost;
   }
   
   //returns game difficulty level
   public String getlevel()
   {
       return strLevel;
   }
   
   //returns player name
   public String getName()
   {
       return strPName;
   }

}
