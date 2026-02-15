/**
 * Description: The main functional test class.
 *
 * @author (Harsh Desai)
 * @version (December 4 - 15th, 2025)
 */

//public int play(Player p)
//{
   // return 20
//}

//import ArrayLists for player list
import java.util.ArrayList;

//import Scanner class for user input
import java.util.Scanner;

public class Arcade
{
    //declare and initialize a private arraylist of type player to hold all the players
    private ArrayList<Player> alPlayers = new ArrayList<>();
    
    //create game objects for the 3 options
    private SlotMachine slotmachine = new SlotMachine();
    private Poker poker;
    private Blackjack blackjacks = new Blackjack();
    
    //public static void main to print out
    public static void main(String[] args)
    {
        //declare and initalize and arcade object
        Arcade arcade = new Arcade();
        
        //call welcome method to out message
        System.out.println(arcade.welcomeMessage() + "\n");
        
        //call setPlayers method to fill the arraylist
        arcade.setPlayers();
        
        //call the processPayments method to fill and validate their payments
        arcade.processPayments();
        
        //call the Menu method to run the games
        arcade.Menu();
        
    }
    
    //code a void method for the menu
    public void Menu()
    {
        //declare and initialize a variable of type boolean to keep the loop going
        boolean bolRun = true;
        
        //declare and initialize temporary variable for the users choice from the menu
        byte bytChoice = 0;
        
        //declare and intialize temporary boolean variable for the do whiel loop
        boolean bolDoWhile = false;
        
        //declare a temp variable of type player to store the player selected to play the game
        Player selectedPlayer;
        
        //declare  and initalize temp variable of type int to hold the points earned by the player
        int intPointsEarned = 0;
        
        //code while loop
        while(bolRun)
        {
            //code do while loop for the users input of the menu till its valid
            do
            {
                //code try catch block to make sure the users input is ok
                try
                {   
                    //print out menu options to the user in the terminal window
                    System.out.println("\n------------- Arcade Menu ------------- \n1. Slot Machines \n2. Poker \n3. Blackjacks \n4. Chess" 
                    + "\n5. Show scoreboard \n6. Exit \n\nPlease choose an option");
                    
                    //save user input to temporary variable
                    bytChoice = new Scanner(System.in).nextByte();
                    
                    //code if statement to check if input is between 1 and 5
                    if(bytChoice >= 1 && bytChoice <= 6)
                    {
                        //update bol variable to end loop
                        bolDoWhile = false;
                    }
                    else
                    {
                        //output error message
                        System.out.println("Error - please enter a valid number");
                        
                        //update bol variable
                        bolDoWhile = true;
                    }        
                }catch(Exception e)
                {
                    //output error message
                    System.out.println("Error - please enter a valid number");
                    
                    //update bol variable
                    bolDoWhile = true;
                }        
            }while(bolDoWhile);
                
            //code switch statement for menu options
            switch(bytChoice)
            {
                case 1: 
                case 2:
                case 3:
                case 4://logic/code for all three cases below
                
                    //intialize variable to the return value from the choose player method
                    selectedPlayer = choosePlayer();
                    
                    //code if statement to see if the variable is holding nothing
                    if(selectedPlayer == null)
                    {
                        //output error message
                        System.out.println("Invalid player");
                        
                        //break from the switch statement
                        break;
                    }
                    
                    if(bytChoice == 1)
                    {
                        //output game message to the user
                        System.out.println(selectedPlayer.getName() + " is playing Slot Machine");
                        
                        //update earned points variable by calling play method in the game
                        intPointsEarned = slotmachine.run(selectedPlayer);
                        
                    }
                    else if(bytChoice == 2)
                    {
                        //output game message to the user
                        System.out.println(selectedPlayer.getName() + " is playing Poker");
                        
                        if(selectedPlayer.getTickets()<=0){
                            selectedPlayer.addTickets(100);
                        }
                        
                        //update earned points variable by calling play method in the game
                        poker = new Poker(selectedPlayer, selectedPlayer.getTickets());
                        intPointsEarned = poker.runGame();
                        
                    }
                    else if(bytChoice == 3)
                    {
                        //output game message to the user
                        System.out.println(selectedPlayer.getName() + " is playing Blackjacks");
                        
                        //update earned points variable by calling play method in the game
                        intPointsEarned = blackjacks.play(selectedPlayer);
                        
                    }else if(bytChoice == 4){
                        System.out.println(selectedPlayer.getName() + " is playing Chess");
                        new StartMenu().run();
                        try{
                            Thread.sleep(15000);
                        }catch(InterruptedException e){
                            
                        }
                    }
                    
                    //call addtickets method to update the players ticket balance
                    selectedPlayer.addTickets(intPointsEarned);
                    
                    //output message to the screen of the points earned by the selected player
                    System.out.println(selectedPlayer.getName() + " earned " + intPointsEarned + " points!");
                    
                    //break from the switch statement
                    break;
                
                case 5:
                    //call printScoreBoard method to print the sorted arraylist
                    printScoreBoard();
                    
                    //break from the switch statement
                    break;
                
                case 6:
                    //update bol run variable to false to exit from while loop
                    bolRun = false;
                    
                    //break from the switch statement
                    break;
                    
                default:
                    //output error message
                    System.out.println("Invalid choice");
            }
        }
            
        }
        
    //code a void method to sort the array list from greatest to smallest as a scoreboard of the players tickets
    public void printScoreBoard()
    {
        //declare temp variables to hold the player were moving and that players tickets
        Player playerMoving;
        int intMovingTickets;
        
        //decalre temp variable j 
        int j;
        
        //code for loop to move throught the arraylist
        //starts at index 1, compares backwards, compares the players, and then inserts the players in the right spot
        for(byte i = 1; i < alPlayers.size(); i++)
        {
            //intialize the temp variable to the player in the array list at counter
            playerMoving = alPlayers.get(i);
            
            //intialize the temp variable to the player thats movings tickets
            intMovingTickets = playerMoving.getTickets();
            
            //intialize j to equal i minus i
            j = i - 1;
            
            //move players with less tickets to the right using while loop
            while(j >= 0 && alPlayers.get(j).getTickets() < intMovingTickets)
            {
                //shift player to the right in the arraylist
                alPlayers.set(j + 1, alPlayers.get(j));
                
                //increment the j variable minus 1
                j--;

            }
            
            //insert player into the right spot
            alPlayers.set(j + 1, playerMoving);
        }
        
        //print the scoreboard
        System.out.println("----------- Scoreboard -----------");
        
        //for each loop to loop throught the array lsit
        for(Player p: alPlayers)
        {
            //print the player and their tickets
            System.out.println(p.getName() + ": " + p.getTickets() + " tickets");
        }
    }
        
    //code a private method of type Player to get the player that is playing the game
    private Player choosePlayer()
    {
        //declare and initialize temp variable to hold the player num they choose
        byte bytNum = 0;
        
        //declare and intialize temp variable boolean for the dowhile loop
        boolean bolDoWhile = false;
        
        //output message
        System.out.println("Pick a player: \n");
        
        //code a for loop to loop throught the array list of players and output them
        for(byte i = 0; i < alPlayers.size(); i++)
        {
            //output the player
            System.out.println((i + 1) + ". " + alPlayers.get(i).getName());
        }
        
        //code do while loop for user input
        do 
        {
            //code try catch block to make sure user input is valid
            try
            {
                //output message
                System.out.println("\nEnter player number: ");
                
                //save user input to variable
                bytNum = new Scanner(System.in).nextByte();
                
                //code if statment to make sure that the input is inbetween the num of players
                if(bytNum >= 1 && bytNum <= alPlayers.size())
                {
                    //update bol variable to force stop loop
                    bolDoWhile = false;
                }
                else
                {
                    //output error message
                    System.out.println("Error - invalid input");
                    
                    //update bol variable to continue loop
                    bolDoWhile = true;
                }
                
            }catch(Exception e)
            { 
                //output error message
                System.out.println("Error - invalid input");
                    
                //update bol variable to continue loop
                bolDoWhile = true;
            }
            
        }while(bolDoWhile);
        
        //return the valid of the player num
        return alPlayers.get(bytNum - 1);
    }
    
    //code a void method to take in the players payment information
    public void processPayments()
    {
        //declare temporary variables for the instance variables of the payment object
        String strCardNum;
        String strExpiry;
        String strCVV;
        
        //code for each loop to take in the info
        for (Player p: alPlayers)
        {
            if(p.getPayment() == null)
            {
                p.setPayment(new Payment());
            }
            
            //while loop that will only loop if the players payment is invalid
            while(!p.getPayment().isValid())
            {
                //prompt user to enter the payment info to a specific person
                System.out.println("Please enter the payment information for " + p.getName() + ":");
                
                //prompt user for their credit card number
                System.out.println("Card Number (16 digits)");
                
                //save input to variable
                strCardNum = new Scanner(System.in).nextLine();
                
                //prompt user for their expiry date 
                System.out.println("Expiry date (MM/YY)");
                
                //save input to variable
                strExpiry = new Scanner(System.in).nextLine();
                
                //prompt user for their cvv
                System.out.println("CVV (3 digits)");
                
                //save input to variable
                strCVV = new Scanner(System.in).nextLine();
                
                p.getPayment().setCardNum(strCardNum);
                p.getPayment().setExpiry(strExpiry);
                p.getPayment().setCVV(strCVV);
                
                if(!p.getPayment().isValid())
                {
                    //output message
                    System.out.println("Payment invalid");
                }
            }
            
            //output message
            System.out.println("Payment accepted");
        }
            
        
        
    }
    
    //code a method of type void to to take in the amount of players to store in the arraylist
    public void setPlayers()
    {
        //declare and initialize a temporary boolean variable for the do while loop
        boolean bolDoWhile = false;
        
        //declare a temporary variable to hold the num of players
        byte bytNumPlayers = 0;
        
        //code do while group incase the user enters something invalid and needs to input again
        do
        {
            //code try catch block to catch input errors
            try
            {
                //prompt user to enter the amount of players they want to add
                System.out.println("How many players would you like to add? It must be between 1-5");
                
                //save user input to variable
                bytNumPlayers = new Scanner(System.in).nextByte();
                
                //if statement to make sure that the input is between 1 and 5
                if(bytNumPlayers >= 1 && bytNumPlayers <= 5)
                {
                    //update bol variable to stop loop
                    bolDoWhile = false;
                }
                else
                {
                    //output error message
                    System.out.println("Error - Please enter a valid number");
                    
                    //update bol variable to continue loop
                    bolDoWhile = true;
                }
            }
            catch(Exception e)
            {
                //output error message
                System.out.println("Error - Please enter a valid number");
                
                //update bol variable to continue loop
                bolDoWhile = true;
            }
            
        }while(bolDoWhile);
        
        //code a for loop to populate the arraylist
        for(byte i = 0; i < bytNumPlayers; i++)
        {
            //prompt for players name
            System.out.println("Please enter the name of player " + (i + 1));
            
            //hold users name to temporary variable
            String strName = new Scanner(System.in).nextLine();
            
            //add new player to list
            alPlayers.add(new Player(strName));
        }
    }
    
    //code a method of return type string to welcome the user
    public String welcomeMessage()
    {
        return "Welcome! This is an arcade game, where you and up to 4 friends can play 3 different games!";
    }
}





