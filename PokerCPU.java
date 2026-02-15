/**
 * Description: The class that stores the logic that allows the CPU to play.
 *
 * @author (Harsh Desai)
 * @version (12/14/2025)
 */
//import arraylist, collections, and random
//random is for random decision making.
//also import guis 
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.*;
public class PokerCPU extends Poker{
    //instance variables for the gameplay of the CPU
    private ArrayList<Cards> alHand = new ArrayList<>();
    public byte bytPlayer;
    private boolean bolIsBluffing = false;
    private int intPoints;
    private byte bytHandRanking = 11;
    private int intCurrentBet;
    public byte bytCPUNum;
    private ArrayList<Byte> alRanksInHand = new ArrayList<>();

    //create a variable to randomize numbers for random decision making and point bets.
    Random random = new Random();
    int randomNumber = 0;
    
    //static booleans to keep track of which cpu's are still in the round.
    static boolean bolCPUOnePlaying = true;
    static boolean bolCPUTwoPlaying = true;
    static boolean bolCPUThreePlaying = true;

    //static jlabels for each cpu's points and bets.
    static JLabel lCPUOne = new JLabel();
    static JLabel lCPUTwo = new JLabel();
    static JLabel lCPUThree = new JLabel();
    
    //static jpanels for each cpu's cards.
    static JPanel pCpuOneCards = new JPanel();
    static JPanel pCpuTwoCards = new JPanel();
    static JPanel pCpuThreeCards = new JPanel();

    //constructor to initialize the variables needed for the game.
    public PokerCPU(byte p, int m, JFrame fMainGame, byte n) {
        //initialize instance variables and set current bet to 0.
        this.bytPlayer = p;
        this.intPoints = m;
        this.bytCPUNum = n;
        intCurrentBet = 0;
        JPanel targetPanel;
        //create the corresponding cpu's labels and jpanels.
        if (bytCPUNum == 1) {
            lCPUOne.setText("<html>Points: " + this.intPoints + "<br>Current Bet: " + this.intCurrentBet + "</html>");
            lCPUOne.setFont(new Font("Times New Roman", Font.BOLD, 30));
            lCPUOne.setSize(500, 200);
            lCPUOne.setLocation(5, 300);
            fMainGame.add(lCPUOne);
            
            pCpuOneCards.setLayout(new FlowLayout());
            pCpuOneCards.setBounds(-35, 450, 350, 120);
            pCpuOneCards.setBackground(new Color(0, 120, 0));
            fMainGame.add(pCpuOneCards);
            targetPanel = pCpuOneCards;
        } else if (bytCPUNum == 2) {
            lCPUTwo.setText("<html>Points: " + this.intPoints + "<br>Current Bet: " + this.intCurrentBet + "</html>");
            lCPUTwo.setFont(new Font("Times New Roman", Font.BOLD, 30));
            lCPUTwo.setSize(500, 100);
            lCPUTwo.setLocation(500, 1);
            fMainGame.add(lCPUTwo);
            
            pCpuTwoCards.setLayout(new FlowLayout());
            pCpuTwoCards.setBounds(425, 85, 350, 120);
            pCpuTwoCards.setBackground(new Color(0, 120, 0));
            fMainGame.add(pCpuTwoCards);
            targetPanel = pCpuTwoCards;
        } else {
            lCPUThree.setText("<html>Points: " + this.intPoints + "<br>Current Bet: " + this.intCurrentBet + "</html>");
            lCPUThree.setFont(new Font("Times New Roman", Font.BOLD, 30));
            lCPUThree.setSize(500, 100);
            lCPUThree.setLocation(955, 400);
            fMainGame.add(lCPUThree);
            
            pCpuThreeCards.setLayout(new FlowLayout());
            pCpuThreeCards.setBounds(900, 500, 350, 120);
            pCpuThreeCards.setBackground(new Color(0, 120, 0));
            fMainGame.add(pCpuThreeCards);
            targetPanel = pCpuThreeCards;
        }
        
        //add the back of the cards to the gui.
        for (byte i = 0; i < 2; i++) {
            targetPanel.add(new JLabel(new ImageIcon("cards/BACK.png")));
        }
        
        //update the gui.
        updateGUI();
    }

    //a method to show all the hands at the end of the game.
    public void showCards() {
        if (bytCPUNum == 1) {
            pCpuOneCards.removeAll();
            pCpuOneCards.add(new JLabel(new ImageIcon(this.alHand.get(0).getImagePath())));
            pCpuOneCards.add(new JLabel(new ImageIcon(this.alHand.get(1).getImagePath())));
            pCpuOneCards.updateUI();
        } else if (bytCPUNum == 2 ) {
            pCpuTwoCards.removeAll();
            pCpuTwoCards.add(new JLabel(new ImageIcon(this.alHand.get(0).getImagePath())));
            pCpuTwoCards.add(new JLabel(new ImageIcon(this.alHand.get(1).getImagePath())));
            pCpuTwoCards.updateUI();
        } else {
            pCpuThreeCards.removeAll();
            pCpuThreeCards.add(new JLabel(new ImageIcon(this.alHand.get(0).getImagePath())));
            pCpuThreeCards.add(new JLabel(new ImageIcon(this.alHand.get(1).getImagePath())));
            pCpuThreeCards.updateUI();

        }
    }
    
    //method to draw cards for the cpu.
    @Override
    public void drawCards(ArrayList<Cards> alDeck) {
        alHand.add(alDeck.get(0));
        alHand.add(alDeck.get(1));
        alDeck.remove(0);
        alDeck.remove(1);
    }
    //different raise method for cpu
    @Override
    public void raise(int intBetIncrease) {
        //Calculate the total of the new bet amount needed
        int intNewBetAmount = Poker.intHighestBet + intBetIncrease;

        //Calculate the amount needed to reach the new bet amount
        int intAmountToPay = intNewBetAmount - this.intCurrentBet;
        
        if (intAmountToPay > this.intPoints) {
            intAmountToPay = this.intPoints;
            intNewBetAmount = this.intCurrentBet + this.intPoints;
        }
            
        //Update the amount of points the player has to bet and how much they have bet.
        //also update the highest bet amount, accounting for if the player goes all-in and doesn't go higher than the highest bet.
        if (intNewBetAmount > Poker.intHighestBet) {
            Poker.intHighestBet = intNewBetAmount;
        }
        this.intCurrentBet = intNewBetAmount;
        this.intPoints -= intAmountToPay;  
    }
    //different check method for cpu, it just does nothing
    @Override
    public void check() {   
    }
    
    //separate call method for cpu to edit their own amount of points.
    @Override
    public void call() {
        //Calculate the total of the new bet amount needed
        int intNewBetAmount = Poker.intHighestBet;

        //Calculate the amount needed to reach the new bet amount
        int intAmountToPay = intNewBetAmount - this.intCurrentBet;
        
        if (intAmountToPay >= this.intPoints) {
            // The player does not have enough points. They must go all-in.
            intAmountToPay = this.intPoints;
        }
        
        //Update the amount of points the player has to bet and how much they have bet.
        this.intCurrentBet = intNewBetAmount;
        this.intPoints -= intAmountToPay;      
    }
    
    //a method randomize a starting bet up to a quarter of the player's total points.
    @Override
    public void preflopBet() {
        randomNumber = random.nextInt((Poker.intPlayersPoints + 4) / 4) + 1;
        this.intCurrentBet = (int)randomNumber;
        
        //update bet numbers and gui.
        Poker.intHighestBet = this.intCurrentBet;
        this.intPoints -= intCurrentBet;
        updateGUI();
    }
    
    //a method to update the labels showing the points of the player.
    @Override
    public void updateGUI() {
        if (bytCPUNum == 1) {
            lCPUOne.setText("<html>Points: " + this.intPoints + "<br>Current Bet: " + this.intCurrentBet + "</html>");
        } else if (bytCPUNum == 2){
            lCPUTwo.setText("<html>Points: " + this.intPoints + "<br>Current Bet: " + this.intCurrentBet + "</html>");
        } else {
            lCPUThree.setText("<html>Points: " + this.intPoints + "<br>Current Bet: " + this.intCurrentBet + "</html>");
        }
    }
    
    //a method to set the current cpu to be out of the round if they fold.
    public void isOut(byte bytPlayerNum) {
        if (bytPlayerNum == 1) {
            bolCPUOnePlaying = false;
            lCPUOne.setText("<html>Points: Folded" + "<br>Current Bet: " + this.intCurrentBet + "</html>");
        } else if (bytPlayerNum == 2) {
            bolCPUTwoPlaying = false;
            lCPUTwo.setText("<html>Points: Folded" + "<br>Current Bet: " + this.intCurrentBet + "</html>");
        } else {
            bolCPUThreePlaying = false;
            lCPUThree.setText("<html>Points: Folded" + "<br>Current Bet: " + this.intCurrentBet + "</html>");
        }
    }
    
    //method to make a decision on whether to check, raise, fold, or call
    //the hand will be ranked from 10 to 1, with 10 being the lowest and 1 being the highest
    //every poker hand will be checked.
    //the hand ranking will only change if the current ranking is worse.
    public void makeDecision(ArrayList<Cards> alCommunityCards) {
        //create variables to store how many pairs of cards, cards of same ranks, and
        //cards of consecutive ranks, the current rank of a card, and the count of each suit.
        //also resetting everything for accuracy.
        byte bytDistinctPairs = 0;
        byte bytDistinctThreeOfAKind = 0;
        byte bytDistinctFourOfAKind = 0;
        byte bytConsecutive = 0;
        this.bytHandRanking = 11;
        byte bytCurrentRank = 0;
        byte bytDCount = 0;
        byte bytHCount = 0;
        byte bytCCount = 0;
        byte bytSCount = 0;
        //reset
        alRanksInHand.removeAll(alRanksInHand);
        
        //if the cpu is out of the round, then exit the method.
        if (bolCPUOnePlaying == false && this.bytCPUNum == 1) {
            return;
        } else if (bolCPUTwoPlaying == false && this.bytCPUNum == 2) {
            return;
        } else if (bolCPUThreePlaying == false && this.bytCPUNum == 3) {
            return;
        }
        
        //create an arraylist to store the cards as bytes to sort and check more easily.
        alRanksInHand.add(alHand.get(0).getBytRank());
        alRanksInHand.add(alHand.get(1).getBytRank());
        for(byte i = 0; i < alCommunityCards.size(); i++) {
            alHand.add(alCommunityCards.get(i));
            alRanksInHand.add(alCommunityCards.get(i).getBytRank());
        }
        
        //use collections.sort to sort the hand by ascending order
        Collections.sort(alRanksInHand);
        //check through the entire hand
        for(byte i = 0; i < alHand.size(); i++) {
            //keeping track of current rank and how many times it appears.
            bytCurrentRank = alRanksInHand.get(i);
            byte bytOccurrences = 1;
            //goes through and checks for cards of the same rank, and updating the amount of pairs
            //and cards of the same rank. checks cards after the current card being compared
            for(byte j = (byte)(i + 1); j < alHand.size(); j++) {
                if (alRanksInHand.get(j) == bytCurrentRank) {
                    bytOccurrences++;
                } else {
                    break;
                }
            }
            if (bytOccurrences == 2) {
                bytDistinctPairs++;
            } else if (bytOccurrences == 3) {
                bytDistinctThreeOfAKind++;
            } else if (bytOccurrences == 4) {
                bytDistinctFourOfAKind++;
            }
            //makes sure no cards are double counted.
            i = (byte)(i + (bytOccurrences - 1));
        }
        
        //if there is an ace, it also counts as the rank after K
        if (alRanksInHand.get(0) == 1) {
            alRanksInHand.add((byte)14);
        }
        //check for consecutive ranks in the hand.
        for(byte i = 0; i < alRanksInHand.size() - 1; i++) {
            if (alRanksInHand.get(i)+1 == alRanksInHand.get(i + 1)) {
                //add one to the counter.
                bytConsecutive++;                
                //exit if 5 consecutive cards are found.
                if (bytConsecutive == 4) {
                    break;
                }
            } else if (alRanksInHand.get(i) == alRanksInHand.get(i + 1)) {
                //nothing happens if the same rank appears.
            } else {
                //reset the counter if its no longer consecutive.
                bytConsecutive = 0;
            }
        }
        //if there are 5 consecutive cards, check for which hand it is.
        if (bytConsecutive == 4) {
            for (byte i = 0; i < alHand.size(); i++) {
                if (alHand.get(i).getSuit().equalsIgnoreCase("D")) {
                    bytDCount++;
                } else if (alHand.get(i).getSuit().equalsIgnoreCase("S")) {
                    bytSCount++;
                } else if (alHand.get(i).getSuit().equalsIgnoreCase("H")) {
                    bytHCount++;
                } else {
                    bytCCount++;
                }
            } 
            if (bytDCount  >= 5 || bytCCount >= 5 || bytHCount >= 5 || bytSCount >= 5) {
                //checking for royal flush, ace at the front, K at the end.
                if (alRanksInHand.get(6) == 13 && alRanksInHand.get(0) == 1) {
                    this.bytHandRanking = 1;
                } else {
                    //otherwise 5 consecutive cards and 5 cards of the same suit is a straight flush
                    this.bytHandRanking = 2;
                }
            } else {
                //if 5 suits of the same kind are not found, 5 consecutive cards is a straight.
                if (this.bytHandRanking > 6) {
                    this.bytHandRanking = 6;
                }
            }
        }
        //if the hand is a flush then there are 5 cards of the same suit
        if (bytDCount  >= 5 || bytCCount >= 5 || bytHCount >= 5 || bytSCount >= 5) {
            if (this.bytHandRanking > 5) {
                this.bytHandRanking = 5;
            }
        }
        
        //Full House
        //if the hand isn't better than the current ranking, change it.
        if (bytDistinctPairs == 2 & bytDistinctThreeOfAKind >= 1) {
            if (this.bytHandRanking > 4) {
                this.bytHandRanking = 4;
            }
        }

        //One pair
        //if the hand isn't better than the current ranking, change it.
        if (bytDistinctPairs == 1) {
            if (this.bytHandRanking > 9) {
                //one pair
                this.bytHandRanking = 9;
            }
            //Two Pairs
        } else if (bytDistinctPairs >= 2) {
            //if the hand isn't better than the current ranking, change it.
            if (this.bytHandRanking > 8) {
                //two pairs
                this.bytHandRanking = 8;
            }
        }
        //Three of a kind
        if (bytDistinctThreeOfAKind >= 1) {
            //if the hand isn't better than the current ranking, change it.
            //3 of a kind.
            if (this.bytHandRanking > 7) {
                this.bytHandRanking = 7;
            }
        } else if (bytDistinctFourOfAKind >= 1) {
            //if the hand isn't better than the current ranking, change it.
            //four of a kind.
            if (this.bytHandRanking > 3) {
                this.bytHandRanking = 3;
            }
        }
         
        //if the hand isn't any of the other hands, it becomes the lowest rank.
        if (this.bytHandRanking == 11) {
                this.bytHandRanking = 10;
        }
        
        //choose whether to raise, check, fold, or call based on how good the hand is
        //if the hand is bad, randomly choose whether or not to bluff.
        if (bolIsBluffing) {
            //create a random number to raise by up to 1.5x the difference between the current bet and the highest bet.
            if (this.intPoints >  0) {
                float fltBetIncrease = (Poker.intHighestBet - this.intCurrentBet) * 1.5f;
                //check if the bet increase is 0 or less, and change it if it is.
                if (fltBetIncrease <= 0) {
                    fltBetIncrease = 5.0f;
                }
                randomNumber = random.nextInt((int)fltBetIncrease) + 1;
                raise(randomNumber);
            }
        } else if (this.bytHandRanking <= 4) {
            //create a random number to raise by up to 1.5x the difference between the current bet and the highest bet.
            if (this.intPoints >  0) {
                float fltBetIncrease = (Poker.intHighestBet - this.intCurrentBet) * 1.5f;
                //check if the bet increase is 0 or less, and change it if it is.
                if (fltBetIncrease <= 0) {
                    fltBetIncrease = 5.0f;
                }
                randomNumber = random.nextInt((int)fltBetIncrease) + 1;
                raise(randomNumber);
            }
        } else if (this.bytHandRanking <= 7) {
            //if hand is mediocre, call.
            call();
        } else {
            //randomly choose to bluff or not
            randomNumber = random.nextInt(4) + 1;
            if (randomNumber > 2) {
                bolIsBluffing = true;
                //create a random number to raise by up to 1.5x the difference between the current bet and the highest bet.
                if (this.intPoints >  0) {
                    float fltBetIncrease = (Poker.intHighestBet - this.intCurrentBet) * 1.5f;
                    //check if the bet increase is 0 or less, and change it if it is.
                    if (fltBetIncrease <= 0) {
                        fltBetIncrease = 5.0f;
                    }
                    randomNumber = random.nextInt((int)fltBetIncrease) + 1;
                    raise(randomNumber);
                }
            } else {
                if (this.bytHandRanking != 10) {
                    //if the hand is not the worst, then check if possible
                    //if not then call
                    if (this.intCurrentBet == Poker.intHighestBet) {
                        //call check method
                        check();
                    } else {
                        //call the call method.
                        call();
                    }
                } else {
                    //otherwise just fold
                    fold((byte)(this.bytPlayer));
                }
            }
        }
        //update the gui.
        updateGUI();

        //remove community cards from hand so there aren't repeats when getting the community cards.
        for (int i = alHand.size() - 1; i >= 0; i--) {
            if (i == 1) {
                break;
            }
            alHand.remove(i); 
        }
    }
    //a getter for the hand ranking of the cpu.
    public byte getHandRanking() {
        return this.bytHandRanking;
    }
    //a getter for the cpu's current bet.
    public int getCurrentBet() {
        return this.intCurrentBet;
    }
}