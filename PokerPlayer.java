/**
 * A class for anything player/user specific, such as drawing cards.
 *
 * @author (Harsh Desai)
 * @version (12/14/2025)
 */
//import arraylist, collections, and gui libraries.
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
public class PokerPlayer extends Poker{
    //create the hand arraylist and an instance variable that separates players, as well as 
    //a variable to store the hand ranking, arraylist to check the hand, and a jpanel for the user's cards.
    private ArrayList<Cards> alHand = new ArrayList<>();
    public byte bytPlayer; 
    private byte bytHandRanking = 11;
    private ArrayList<Byte> alRanksInHand = new ArrayList<>();
    JPanel pPlayerCards = new JPanel();

    //constructor to give the player a number
        public PokerPlayer(byte p, JFrame frame) {
        this.bytPlayer = p;
        this.fMainGame = frame;
        setupPlayerGUI();
    }

    //draw 2 cards from the deck and remove them from the deck.
    @Override
    public void drawCards(ArrayList<Cards> alDeck) {
        alHand.add(alDeck.get(0));
        alHand.add(alDeck.get(1));
        alDeck.remove(0);
        alDeck.remove(0);
        //clear panel
        pPlayerCards.removeAll();

        for (byte i = 0; i < alHand.size(); i++) {
            pPlayerCards.add(new JLabel(new ImageIcon(this.alHand.get(i).getImagePath())));
        }
        //shows the change
        pPlayerCards.updateUI();
    }
    
    public byte handRanking(ArrayList<Cards> alCommunityCards) {
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
        //return the hand ranking.
        return bytHandRanking;
    }
    
    //method to setup the players cards.
    public void setupPlayerGUI() {
        //setup the panel for the players cards
        pPlayerCards.setLayout(new FlowLayout());
        pPlayerCards.setBounds(425, 550, 350, 120);
        pPlayerCards.setBackground(new Color(0, 120, 0));
        
        //show the back of the cards.
        for (int i = 0; i < 2; i++) {
            pPlayerCards.add(new JLabel(new ImageIcon("cards/BACK.png")));
        }
        
        //add the panel to the main window.
        fMainGame.add(pPlayerCards);
    }
}