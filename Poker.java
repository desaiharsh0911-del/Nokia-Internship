/**
 * A game of Texas Hold'em Poker using GUI's.
 *
 * @author (Harsh Desai)
 * @version (12/14/2025)
 */
//import arraylist, collections, random and libraries needed for guis.
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.*;
public class Poker {
    //instance variables needed for the game
    //variables to store the players information and variables
    //keeping track of the game's progression.
    public static int intPlayersPoints;
    public static int intPlayersCurrentBet;
    private Player playerName;
    private byte bytStage;
    private byte bytTurnCount;
    public static int intHighestBet = 0;
    
    //arraylists and arrays to store cards.
    private ArrayList<Cards> alCommunityCards = new ArrayList<>();
    private static ArrayList<Poker> alPlayersIn = new ArrayList<>();
    private ArrayList<Cards> alDeckOne = new ArrayList<>();
    
    //booleans for if the player has made a move yet.
    public boolean bolHasNotChoseAction = true;
    public boolean bolPlayerFolded = false;
    
    //create the player and cpu objects
    Poker user;
    Poker cpuOne;
    Poker cpuTwo;
    Poker cpuThree;
    
    //create variables to store the hand rankings.
    byte bytPlayerHandRanking;
    byte bytCPUOneHand;
    byte bytCPUTwoHand;
    byte bytCPUThreeHand;
    
    //create a random to generate random order of play.
    Random random = new Random();
    int intPlayersNum = random.nextInt(4) + 1;
    
    //declare the jframe for the game.
    public JFrame fMainGame;
    JLabel lPlayersPoints = new JLabel();
    JButton bFold = new JButton("Fold!");
    JButton bCall = new JButton("Call!");
    JButton bRaise = new JButton("Raise!");
    JButton bCheck = new JButton("Check!");
    static JPanel pCommCards = new JPanel();
    

    //constructor to initialize all needed instance variables.
    public Poker(Player n, int p) {
        playerName = n;
        intPlayersPoints = p;
    }
    
    //for the subclasses to use superclass variables without needing to
    //initialize player information.
    public Poker() {

    }
    
    //a method to populate a deck and shuffle it.
    public void shuffleDeck(ArrayList<Cards> alDeck) {
        String[] aSuits = {"H", "S", "D", "C"};
        Byte[] aRanks = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        for(byte i = 0; i < aSuits.length; i++) {
            for(byte j = 0; j < aRanks.length; j++) {
                alDeck.add(new Cards(aSuits[i], aRanks[j]));
            }
        }
        Collections.shuffle(alDeck);
    }
    
    //methods for fold, raise, check, or call. raise and check are player specific.
    public void fold(byte bytPlayer) {
        if (bytPlayer == 5) {
            bolPlayerFolded = true;
            JOptionPane.showMessageDialog(null, "You Lost!\nNew Amount of Points: "
            + Poker.intPlayersPoints);
        } else {
            ((PokerCPU)(alPlayersIn.get(bytPlayer))).isOut(bytPlayer);
        }
    }
    public void raise(int intBetIncrease) {
        //Calculate the total of the new bet amount needed
        int intNewBetAmount = Poker.intHighestBet + intBetIncrease;

        //Calculate the amount needed ot reach the new bet amount
        int intAmountToPay = intNewBetAmount - intPlayersCurrentBet;
        
        //if the player goes all-in
        if (intAmountToPay > intPlayersPoints) {
            intAmountToPay = intPlayersPoints;
            intNewBetAmount = intPlayersCurrentBet + intPlayersPoints;
        }
        
        //Update the amount of points the player has to bet and how much they have bet, also update
        //the highest bet, accounting for if the player goes all in without going higher than current highest.
        if (intNewBetAmount > Poker.intHighestBet) {
            Poker.intHighestBet = intNewBetAmount;
        }
        intPlayersCurrentBet = intNewBetAmount;
        intPlayersPoints -= intAmountToPay;   
    }
    public void check() {
        //signal that the player has made a move.
        bolHasNotChoseAction = true;
    }
    public void call() {
        //Calculate the total of the new bet amount needed
        int intNewBetAmount = Poker.intHighestBet;

        //Calculate the amount needed ot reach the new bet amount
        int intAmountToPay = intNewBetAmount - intPlayersCurrentBet;
        
        if (intNewBetAmount >= intPlayersPoints) {
            //The player does not have enough points. They must go all-in.
            intAmountToPay = intPlayersPoints;
        }
        
        //Update the amount of points the player has to bet and how much they have bet.
        intPlayersCurrentBet = intNewBetAmount;
        intPlayersPoints -= intAmountToPay;  
    }
    
    //draws the 3 community cards in the first stage.
    public void drawCards(ArrayList<Cards> alDeckOne) {
        alCommunityCards.add(alDeckOne.get(0));
        alDeckOne.remove(0);
        alCommunityCards.add(alDeckOne.get(0));
        alDeckOne.remove(0);
        alCommunityCards.add(alDeckOne.get(0));
        alDeckOne.remove(0);
        //update gui
        updateRiver();
    }
    
    //gets the player to bet their pre-flop bet as big/small blind.
    public void preflopBet() {
        int intNewBet = 0;
        //boolean for the try catch
        boolean bolError = false;
        //looped try catch for catching invalid input.
        do {
            try {
                intNewBet = Integer.parseInt(JOptionPane.showInputDialog("Enter your bet."));
                bolError = false;
            } catch (Exception e) {
                bolError = true;
            }
            if (intNewBet <= Poker.intHighestBet) {
                JOptionPane.showMessageDialog(null, "Invalid bet, try again.");
                bolError = true;
            }
        } while (bolError);
        //update the highest/current bet and the players points to bet.
        intPlayersCurrentBet = intNewBet;
        intHighestBet = intPlayersCurrentBet;
        intPlayersPoints -= intPlayersCurrentBet;
    }
    
    //method to set up the guis.
    public void guiSetup() {
        //set up the jframe and buttons.
        fMainGame = new JFrame("Poker");
        fMainGame.setSize(1225, 975);
        fMainGame.getContentPane().setBackground(new Color(0, 120, 0));
        fMainGame.setLayout(null);
        
        bFold.setSize(150, 150);
        bFold.setLocation(225, 750);
        fMainGame.add(bFold);
        
        bCall.setSize(150, 150);
        bCall.setLocation(425, 750);
        fMainGame.add(bCall);
        
        bRaise.setSize(150, 150);
        bRaise.setLocation(625, 750);
        fMainGame.add(bRaise);
        
        bCheck.setSize(150, 150);
        bCheck.setLocation(825, 750);
        fMainGame.add(bCheck);
        
        //setup the action listeners for the player's buttons of fold, call, raise, and check
        bFold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fold((byte)5);
                bolHasNotChoseAction = false;             
            }
        });
        
        bCall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                call();
                bolHasNotChoseAction = false;             
            }
        });
        
        bRaise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //boolean for try catch
                boolean bolError = false;
                //variable to store the amount to raise by.
                int intBetIncrease = 0;
                do {
                    try {
                        //ask the user to input the raise amount.
                        intBetIncrease = Integer.parseInt(JOptionPane.showInputDialog("Enter how much to raise by."));
                        //call the raise method 
                        raise(intBetIncrease);
                        bolError = false;
                    } catch (Exception f) {
                        bolError = true;
                    }
                    //if the bet increase is too small or too large
                    if (intBetIncrease <= 0 || intBetIncrease > intPlayersPoints) {
                        //output an error message
                        JOptionPane.showMessageDialog(null, "Invalid bet, try again.");
                        bolError = true;
                    }
                } while (bolError);         
                bolHasNotChoseAction = false;             
            }
        });
        
        bCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                check();
                bolHasNotChoseAction = false;             
            }
        });
        
        //setup JLabels to show points and bets.
        lPlayersPoints.setText("<html>Points: " + intPlayersPoints + "<br>Current Bet: " + intPlayersCurrentBet + "</html>");
        lPlayersPoints.setFont(new Font("Times New Roman", Font.BOLD, 30));
        lPlayersPoints.setSize(500, 200);
        lPlayersPoints.setLocation(425, 600);
        fMainGame.add(lPlayersPoints);
        
        
        //set layout to flow
        pCommCards.setLayout(new FlowLayout());
        //set size
        pCommCards.setBounds(300, 250, 600, 300);
        //set colour
        pCommCards.setBackground(new Color(0, 120, 0));
        //add it game screen
        fMainGame.add(pCommCards);
        for (byte i = 0; i < 5; i++) {
            pCommCards.add(new JLabel(new ImageIcon("cards/BACK.png")));
        }
        
    }
    
    //method to update the label of the player's points and current bet
    public void updateGUI() {
        lPlayersPoints.setText("<html>Points: " + intPlayersPoints + "<br>Current Bet: " + intPlayersCurrentBet + "</html>");
    }
    
    //the main method that will run the game.
    public int runGame() {
        //call the method to setup guis.
        guiSetup();
        
        //randomize the turns of which the game will follow
        if (intPlayersNum == 1) {
            user = new PokerPlayer((byte)0,fMainGame);
            alPlayersIn.add(user);
            
            cpuOne = new PokerCPU((byte)1, intPlayersPoints, fMainGame, (byte)1);
            alPlayersIn.add(cpuOne);

            cpuTwo = new PokerCPU((byte)2, intPlayersPoints, fMainGame, (byte)2);
            alPlayersIn.add(cpuTwo);
            
            cpuThree = new PokerCPU((byte)3, intPlayersPoints, fMainGame, (byte)3);
            alPlayersIn.add(cpuThree);

        } else if (intPlayersNum == 2) {
            cpuThree = new PokerCPU((byte)0, intPlayersPoints, fMainGame, (byte)3);
            alPlayersIn.add(cpuThree);

            user = new PokerPlayer((byte)1,fMainGame);
            alPlayersIn.add(user);

            cpuOne = new PokerCPU((byte)2, intPlayersPoints, fMainGame, (byte)1);
            alPlayersIn.add(cpuOne);
            
            cpuTwo = new PokerCPU((byte)3, intPlayersPoints, fMainGame, (byte)2);
            alPlayersIn.add(cpuTwo);
        } else if (intPlayersNum == 3) {
            cpuTwo = new PokerCPU((byte)0, intPlayersPoints, fMainGame, (byte)2);
            alPlayersIn.add(cpuTwo);

            cpuThree = new PokerCPU((byte)1, intPlayersPoints, fMainGame, (byte)3);
            alPlayersIn.add(cpuThree);
            
            user = new PokerPlayer((byte)2,fMainGame);
            alPlayersIn.add(user);
            
            cpuOne = new PokerCPU((byte)3, intPlayersPoints, fMainGame, (byte)1);
            alPlayersIn.add(cpuOne);
        } else {
            cpuOne = new PokerCPU((byte)0, intPlayersPoints, fMainGame, (byte)1);
            alPlayersIn.add(cpuOne);

            cpuTwo = new PokerCPU((byte)1, intPlayersPoints, fMainGame, (byte)2);
            alPlayersIn.add(cpuTwo);

            cpuThree = new PokerCPU((byte)2, intPlayersPoints, fMainGame, (byte)3);
            alPlayersIn.add(cpuThree);
            
            user = new PokerPlayer((byte)3,fMainGame);
            alPlayersIn.add(user);
        }
        
        //show the game window.
        fMainGame.setVisible(true);
        
        //populate and shuffle the deck
        shuffleDeck(alDeckOne);
        
        //draw cards for all players and get starting bets.
        alPlayersIn.get(0).drawCards(alDeckOne);
        alPlayersIn.get(1).drawCards(alDeckOne);
        alPlayersIn.get(2).drawCards(alDeckOne);
        alPlayersIn.get(3).drawCards(alDeckOne);
        alPlayersIn.get(2).preflopBet();
        alPlayersIn.get(3).preflopBet();
        
        //nested for loop for the turn based gameplay
        for(bytStage = 1; bytStage < 4; bytStage++) {
            //draw a certain amount of cards corresponding to the stage, and updating the gui's every time.
            if (bytStage == 1) {
                drawCards(alDeckOne);
            } else if (bytStage == 2) {
                alCommunityCards.add(alDeckOne.get(0));
                alDeckOne.remove(0);
                updateRiver();
            } else {
                alCommunityCards.add(alDeckOne.get(0));
                alDeckOne.remove(0);
                updateRiver();
            }
            //for loop to keep track of turns.
            for(bytTurnCount = 0; bytTurnCount < alPlayersIn.size(); bytTurnCount++) {
                //update the guis.
                updateGUI();
                //check if its the players turn.
                if (bytTurnCount == intPlayersNum-1) {
                    //let the player know its their turn.
                    JOptionPane.showMessageDialog(null, "Your Turn.");
                    //check if the user has the option to check, enable/disable based on whether or not they can.
                    if (intPlayersCurrentBet == Poker.intHighestBet) {
                        bCheck.setEnabled(true);
                    } else {
                        bCheck.setEnabled(false);
                    }
                    //do while loop that runs until the user has played an action.
                    do {
                        try {
                            //Sleep for 100 milliseconds while waiting for player to pick an action
                            Thread.sleep(100); 
                        } catch (InterruptedException e) {
                            //when the player interrupts the sleep,.
                            //signal the interruption
                            Thread.currentThread().interrupt();
                            //the while condition will change after a button is pressed, so the loop will exit.
                        }
                    } while(bolHasNotChoseAction);
                    //check if the player has folded.
                    if (bolPlayerFolded) {
                        fMainGame.setVisible(false);
                        return intPlayersPoints;
                    }
                    //reset the boolean so the loop will run when it's the players turn again.
                    bolHasNotChoseAction = true;
                } else {
                    //get the current cpu to make a decision.
                    ((PokerCPU)(alPlayersIn.get(bytTurnCount))).makeDecision(alCommunityCards);
                }
            }
        }
        //get all the hand rankings to compare
        bytPlayerHandRanking = ((PokerPlayer)(user)).handRanking(alCommunityCards);
        bytCPUOneHand = ((PokerCPU)(cpuOne)).getHandRanking();
        bytCPUTwoHand = ((PokerCPU)(cpuTwo)).getHandRanking();
        bytCPUThreeHand = ((PokerCPU)(cpuThree)).getHandRanking();
        
        //show all the hands for 3 seconds then close the window.
        try {
            //Sleep for 1000 and show all the hands.
            ((PokerCPU)(cpuOne)).showCards();
            ((PokerCPU)(cpuTwo)).showCards();
            ((PokerCPU)(cpuThree)).showCards();
            Thread.sleep(3000); 
        } catch (InterruptedException e) {
            //if the the sleep is interrupted.
            Thread.currentThread().interrupt();
        }
        //close the window
        fMainGame.setVisible(false);
        
        
        //compare the hand rankings to see if the player wins, ties, or loses.
        if (bytPlayerHandRanking < bytCPUOneHand && bytPlayerHandRanking < bytCPUTwoHand && bytPlayerHandRanking < bytCPUThreeHand) {
            //win
            //output a message while returning the amount of points the user has after winning.
            JOptionPane.showMessageDialog(null, "You Won!\nYou now have: " + (intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
            + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet()) + " points.");

            return intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
            + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet();

        } else if (bytPlayerHandRanking == bytCPUOneHand) {
            //tie against one player
            if (bytPlayerHandRanking > bytCPUTwoHand && bytPlayerHandRanking > bytCPUThreeHand) {
                //output a message while returning the amount of points the user has after tieing.
                JOptionPane.showMessageDialog(null, "You Tied!\nYou now have: " +(intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
                + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet()) / 2 + " points.");

                return (intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
                + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet()) / 2;
                //if you tie against 2 players 
            } else {
                //output a message while returning the amount of points the user has after tieing.
                JOptionPane.showMessageDialog(null, "You Tied!\nYou now have: " + (intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
                + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet()) / 3 + " points.");

                return (intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
                + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet()) / 3;
            }
        } else if (bytPlayerHandRanking == bytCPUTwoHand) {
            //tie against 1
            if (bytPlayerHandRanking > bytCPUOneHand && bytPlayerHandRanking > bytCPUThreeHand) {
                //output a message while returning the amount of points the user has after tieing.
                JOptionPane.showMessageDialog(null, "You Tied!\nYou now have: " + (intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
                + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet()) / 2 + " points.");

                return (intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
                + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet()) / 2;

            } else {
                //tie against 2
                //output a message while returning the amount of points the user has after tieing.
                JOptionPane.showMessageDialog(null, "You Tied!\nYou now have: " + (intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
                + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet()) / 3 + " points.");

                return (intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
                + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet()) / 3;
            }
        } else if (bytPlayerHandRanking == bytCPUThreeHand) {
            //tie against 1 player
            if (bytPlayerHandRanking > bytCPUTwoHand && bytPlayerHandRanking > bytCPUOneHand) {
                //output a message while returning the amount of points the user has after tieing.
                JOptionPane.showMessageDialog(null, "You Tied!\nYou now have: " + (intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
                + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet()) / 2 + " points.");

                return (intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
                + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet()) / 2;
                //tie against 2 players
            } else {
                //output a message while returning the amount of points the user has after tieing.
                JOptionPane.showMessageDialog(null, "You Tied!\nYou now have: " + (intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
                + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet()) / 3 + " points.");

                return (intPlayersCurrentBet + ((PokerCPU)(cpuOne)).getCurrentBet()
                + ((PokerCPU)(cpuTwo)).getCurrentBet() + ((PokerCPU)(cpuThree)).getCurrentBet()) / 3;
            }
            //you lose
        } else {
            //output a message while returning how many points a player has left.
            JOptionPane.showMessageDialog(null, "You Lost!\nYou now have: " + intPlayersPoints + " points");
            return intPlayersCurrentBet * -1;
        }
    }
    
    //method by Harsh
    //will update river cards for gui
    public void updateRiver(){
        //clear the current river panel
        pCommCards.removeAll();
        //checks if there are less than 5 community cards if so shows the backs of those cards
        if(alCommunityCards.size() < 5){
            for (byte i = 0;i< (5 - this.alCommunityCards.size()); i++) {
                pCommCards.add(new JLabel(new ImageIcon("cards/BACK.png")));
            }
        }
        for (byte i = 0;i< this.alCommunityCards.size(); i++) {
            pCommCards.add(new JLabel(new ImageIcon(this.alCommunityCards.get(i).getImagePath())));
        }
        
        //shows the change
        pCommCards.updateUI();
    }
}