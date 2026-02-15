/**
 * Description: A game of Blackjack using GUI's.
 *
 * @author (Harsh)
 * @version (12/05/2025)
 */
//import stuff used in class
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Blackjack
{
    //instance variables
    private ArrayList<Cards> alDealerHand;
    private ArrayList<  Cards> alPlayerHand;
    private ArrayList<Cards> alDeck;
    private byte bytPlayerValue;
    private byte bytDealerValue;
    private int intBet;
    private int intBalance;
    
    //global var for game
    boolean bolRoundOver; 
    //globle var for gui
    //declear the game screen
    private JFrame fGameScreen;
    //declare a button called hit
    private JButton bHit;
    //declare a button called stand
    private JButton bStand;
    //declear a label of type jlabel that shows balance
    private JLabel lBalance;
    //declear 2 labels of type jlabel that shows card values of player and dealer
    private JLabel lPlayerValue;
    private JLabel lDealerValue;
    //declear a panel to show player cards
    private JPanel pPlayerCards;
    //declear a panel to showw dealer cards
    private JPanel pDealerCards;

    //constructor
    public Blackjack()
    {
        //initialize all instance variables
        this.alDealerHand = new ArrayList<>();
        this.alPlayerHand = new ArrayList<>();
        this.alDeck = new ArrayList<>();
        intBet = 0;
        intBalance = 0;
        this.bolRoundOver = false;
        this.fGameScreen = new JFrame("Blackjack Game");
        this.bHit = new JButton("Hit!");
        this.bStand = new JButton("Stand");
        this.lBalance = new JLabel("<html>Balance: " + intBalance +"<br>Bet: "+intBet+"</html>");
        this.lPlayerValue = new JLabel();
        this.lDealerValue = new JLabel();
        this.pPlayerCards = new JPanel();
        this.pDealerCards = new JPanel();
       
        //sets up guis
        guiSetup(); 
        //create game deck
        newDeck();
    }
   
    //method to create a deck of cards
    public void newDeck(){
        //checks if the deck is empty, if not emptys it
        if(this.alDeck.isEmpty() == false){
            //empties the deck
            this.alDeck.clear();
        }
        String[] aSuit = {"C","D","H","S"};
        byte[] aValues = {11,10,10,10,10,9,8,7,6,5,4,3,2};
        String[] aRank = {"A","K","Q","J","10","9","8","7","6","5","4","3","2"};
       
        for(byte j = 0; j < aSuit.length; j++) {
            for(byte k = 0; k < aRank.length; k++) {
                this.alDeck.add(new Cards(aSuit[j],aRank[k], aValues[k]));
            }
        }
        //shuffles the deck
        Collections.shuffle(this.alDeck);
    }
   
    //method to draw the card at the top of the deck
    public Cards draw(){
        //if the deck has less than half the cards remaining, refills it
        if (this.alDeck.size() <= 26) {
            //reshuffle if the deck runs out mid-game
            newDeck();
        }
        //draws a card from the deck
        Cards tempCard = this.alDeck.get(0);
        this.alDeck.remove(0);
       
        return tempCard;
    }
    
    //method to place bet
    public void bet(){
        //boolean for the try catch
        boolean bolError = false;
        //looped try catch for catching invalid input.
        do {
            try {
                this.intBet = Integer.parseInt(JOptionPane.showInputDialog("Enter your bet.\nYour balance is "+ intBalance));
                bolError = false;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid bet, try again.");
                bolError = true;
            }
            if (intBet > intBalance || intBet <= 0 ) {
                JOptionPane.showMessageDialog(null, "Invalid bet, try again.");
                bolError = true;
            }
        } while (bolError);
    }
    
    //method to setup gui's
    public void guiSetup(){
        //hide the game screen
        this.fGameScreen.setVisible(false);
        //set the size of the screen
        this.fGameScreen.setSize(1200,1000);
        //set layout to null so it is 1:1 to memorise board
        this.fGameScreen.setLayout(null);
        //set colour
        this.fGameScreen.getContentPane().setBackground(new Color(0, 100, 0));
       
        //set size
        this.lBalance.setSize(625, 200);
        //set the location
        this.lBalance.setLocation(0, 0);
        //set the font
        this.lBalance.setFont(new Font("Arial", Font.BOLD, 30));
        //position it in the middle
        this.lBalance.setHorizontalAlignment(JLabel.CENTER);
        //set text color
        this.lBalance.setForeground(new Color(240,240,240));
        //add it to game screen
        this.fGameScreen.add(this.lBalance);
        
        //set size
        this.lDealerValue.setSize(500, 60);
        //set the location
        this.lDealerValue.setLocation(350, 390);
        //set the font
        this.lDealerValue.setFont(new Font("Arial", Font.BOLD, 30));
        //position it in the middle
        this.lDealerValue.setHorizontalAlignment(JLabel.CENTER);
        //set text color
        this.lDealerValue.setForeground(new Color(240,240,240));
        //add it to game screen
        this.fGameScreen.add(this.lDealerValue);
        
        //set size
        this.lPlayerValue.setSize(500, 60);
        //set the location
        this.lPlayerValue.setLocation(350, 700);
        //set the font
        this.lPlayerValue.setFont(new Font("Arial", Font.BOLD, 30));
        //position text in the middle
        this.lPlayerValue.setHorizontalAlignment(JLabel.CENTER);
        //set text color
        this.lPlayerValue.setForeground(new Color(240,240,240));
        //add it to game screen
        this.fGameScreen.add(this.lPlayerValue);
       
       
        //set up hit button
        //set size
        this.bHit.setSize(600, 150);
        //set location to bottem
        this.bHit.setLocation(0, 800);
        //set font to arial
        this.bHit.setFont(new Font("Arial", Font.BOLD, 60));
        //set the colour of the backgrond and text
        this.bHit.setBackground(new Color(70, 70, 70));
        this.bHit.setForeground(new Color(240,240,240));
        //add action lisitioner
        this.bHit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //call hit method
                hit();
            }
        });
        //add it to game screen
        this.fGameScreen.add(this.bHit);
       
        //set up stand button
        this.bStand.setSize(600, 150);
        //set location to bottem
        this.bStand.setLocation(600, 800);
        //set font to arial
        this.bStand.setFont(new Font("Arial", Font.BOLD, 60));
        //set the colour of the backgrond and text
        this.bStand.setBackground(new Color(70, 70, 70));
        this.bStand.setForeground(new Color(240,240,240));
        //add action lisitioner
        this.bStand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //call stand method
                stand();        
            }
        });
        //add it to game screen
        this.fGameScreen.add(this.bStand);
       
        //player cards panel
        //set layout to flow
        this.pPlayerCards.setLayout(new FlowLayout());
        //set size
        this.pPlayerCards.setBounds(200, 500, 800, 200);
        //set colour
        this.pPlayerCards.setBackground(new Color(0, 120, 0));
        //add it game screen
        this.fGameScreen.add(this.pPlayerCards);

        //dealer cards panel
        //set layout to flow
        this.pDealerCards.setLayout(new FlowLayout());
        //set size
        this.pDealerCards.setBounds(200, 200, 800, 200);
        //set colour
        this.pDealerCards.setBackground(new Color(0, 100, 0));
        //add it game screen
        this.fGameScreen.add(this.pDealerCards);
    }
    
    //mehtod that will run/start the round
    public void startRound(){
        //hide the game screen
        this.fGameScreen.setVisible(false);
        //clear old hands
        this.alDealerHand.clear();
        this.alPlayerHand.clear();
        
        //enables all buttons
        this.bHit.setEnabled(true);
        this.bStand.setEnabled(true);
        //place bet
        bet();
        //update jlabel
        this.lBalance.setText("<html>Balance: " + (intBalance - intBet)+"<br>Bet: "+intBet+"</html>");
        //show the game screen
        this.fGameScreen.setVisible(true);
        //both player and dealer draw 2 cards
        for(byte i=0;i<2;i++){
            this.alDealerHand.add(draw());
            this.alPlayerHand.add(draw());
        }
        //updates gui
        updateCardDisplay(false);
    }
    
    //method for the hit button
    public void hit(){
        //adds a card to the pl;ayers hand
        this.alPlayerHand.add(draw());
        //update gui
        updateCardDisplay(false);
        this.bytPlayerValue = getValue(this.alPlayerHand);
        //checks if player busted or not
        if(this.bytPlayerValue > 21){
            //hide the game screen
            this.fGameScreen.setVisible(false);
            //lose the bet
            intBalance -= intBet;
            JOptionPane.showMessageDialog(null, "--- Round Results ---\nPlayer value:  " + this.bytPlayerValue + " Dealer value: " + getValue(this.alDealerHand)+"\nPlayer BUST \nDealer Wins"+"\nNew Balance: " + intBalance);
            //update jlabel
            this.lBalance.setText("<html>Balance: " + intBalance +"<br>Bet: "+intBet+"</html>");
            //ask play again
            playAgain();
        }
    }
    
    //method that calculate the value of the dealer/player's hand
    public byte getValue(ArrayList<Cards> alHand){
       byte bytValue = 0;
       byte bytAceCount = 0;
       for(byte i =0; i < alHand.size(); i++){
           //increce value by card value
           bytValue += alHand.get(i).getValue();
           
           //checks if there is an ace in the hand
           if(alHand.get(i).getValue() == 11){
               bytAceCount++;
           }
       }
       //changes value of aces if the total is over 21
       while(bytValue > 21 && bytAceCount > 0){
           //uses hard ace
           bytValue -= 10;
           bytAceCount--;
       }
       
       return bytValue;
    }
   
    //method for player to stand, dealer logic
    public void stand(){
       this.bytDealerValue = getValue(this.alDealerHand);
       this.bytPlayerValue = getValue(this.alPlayerHand);
       //delear will draw a card till value is greater than 17
       while(this.bytDealerValue < 17){
           this.alDealerHand.add(draw());
           this.bytDealerValue = getValue(this.alDealerHand);
           //updates gui
           updateCardDisplay(true);
       }
       
       //hide the game screen
       this.fGameScreen.setVisible(false);  
       //find winner and update balance
       if(this.bytDealerValue > 21){
           //1x the bet
           intBalance += intBet;
           JOptionPane.showMessageDialog(null, "--- Round Results ---\nPlayer value:  " + this.bytPlayerValue + " Dealer value: " + this.bytDealerValue+"\nDealer BUST \nPlayer Wins"+"\nNew Balance: " + intBalance);
           
       }else if(this.bytDealerValue < this.bytPlayerValue){
           //win the bet
           intBalance += intBet;
           JOptionPane.showMessageDialog(null, "--- Round Results ---\nPlayer value:  " + this.bytPlayerValue + " Dealer value: " + this.bytDealerValue+"\nPlayer Wins"+"\nNew Balance: " + intBalance);
       }else if(this.bytDealerValue == this.bytPlayerValue){
           JOptionPane.showMessageDialog(null, "--- Round Results ---\nPlayer value:  " + this.bytPlayerValue + " Dealer value: " + this.bytDealerValue+"\nIt's a push/tie. \nYour bet is returned"+"\nNew Balance: " + intBalance);
       }else{
           //lose the bet
           intBalance -= intBet;
           //checks if this.bytDealerValue != this.bytPlayerValue
           JOptionPane.showMessageDialog(null, "--- Round Results ---\nPlayer value:  " + this.bytPlayerValue + " Dealer value: " + this.bytDealerValue+"\nDealer Wins"+"\nNew Balance: " + intBalance);
       }
       //update jlabel
       this.lBalance.setText("<html>Balance: " + intBalance +"<br>Bet: "+intBet+"</html>");
       playAgain();
    }
   
    //method that asks for play again
    public void playAgain(){
        //checks if balance is greater than 0 else won't ask for play again
        if (intBalance > 0){
            byte bytChoice = (byte)JOptionPane.showConfirmDialog(null,"Do you want to play again?","Play Again",JOptionPane.YES_NO_OPTION);
           
            if (bytChoice == JOptionPane.YES_OPTION) {
                //play again
                startRound();  
            } else {
                //hide game
                this.fGameScreen.setVisible(false);
                this.bolRoundOver = true;
            }
        }else{
            //hide game
            this.fGameScreen.setVisible(false);
            this.bolRoundOver = true;
        }
    }
    
    //method to update gui's
    public void updateCardDisplay(boolean bolDealerTurn) {
        //removes all cards from the Jpanel
        this.pPlayerCards.removeAll();
        this.pDealerCards.removeAll();
        
        //adds cards to Jpanel
        //player cards
        for(byte i =0;i < this.alPlayerHand.size(); i++ ) {
            this.pPlayerCards.add(new JLabel(new ImageIcon(this.alPlayerHand.get(i).getImage())));
            //update the ui
            this.pPlayerCards.updateUI();  
        }
        //update jlabel for player
        this.lPlayerValue.setText("Value: " + getValue(this.alPlayerHand));
        
        //makes sure only one card of the dealer is revealed till the player stands
        if(bolDealerTurn){
            //dealer cards get added to Jpanel
            for (byte i =0;i < this.alDealerHand.size(); i++ ) {
                this.pDealerCards.add(new JLabel(new ImageIcon(this.alDealerHand.get(i).getImage())));
                
                //update jlabel
                this.lDealerValue.setText("Value: " + getValue(this.alDealerHand));
                
                //shows the change
                this.pDealerCards.updateUI();
            }
        }else{
            //only one card is shown
            this.pDealerCards.add(new JLabel(new ImageIcon(this.alDealerHand.get(0).getImage())));
            //show back of other card
            this.pDealerCards.add(new JLabel(new ImageIcon("cards/BACK.png")));
            
            //only gives value of the shown card
            this.lDealerValue.setText("Value: " + this.alDealerHand.get(0).getValue());
            
            //shows the change
            this.pDealerCards.updateUI();
        }
    }
    
    //play method for arcade class
    public int play(Player p){
        //set round over to false
        this.bolRoundOver = false;
        
        //check if tickets is equal/less than 0
        if (p.getTickets() <= 0){
            //give 100 
            this.intBalance = 100;    
        }else{
            this.intBalance = p.getTickets();
        }
        //starts the round
        startRound();
        
        //waits till the player runs out of points or says no to play again before returning the points
        while (!bolRoundOver) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
        }
        //return the change in points
        return intBalance - p.getTickets();
    }
}
