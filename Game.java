
import javax.swing.*;
import java.util.ArrayList;

public class Game{
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
}
