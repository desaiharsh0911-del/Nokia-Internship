/**
 * Description: This class creates a Card object that stores suit, rank, and the card value
 *
 * @author (Harsh)
 * @version (12/4/2025)
 */
public class Cards
{
    //instance variables of type string to store suit and rank
    private String strSuit, strRank;
    
    //instance variable of tpe byte to store the value of the cards
    private byte bytValue, bytRank;

    public Cards(String s,String r, byte v) {
        //inastailize the instance variables
        this.strSuit = s;
        this.strRank=r;
        this.bytValue=v;
    }
    
    public Cards(String s, byte r) {
        //initialize the instance variables
        this.strSuit = s;
        this.bytRank = r;
    }
    
    //returns the card value
    public byte getValue(){
        return this.bytValue;
    }
    
    public byte getBytRank() {
        return this.bytRank;
    }
    
    //returns the suit
    public String getSuit(){
        return this.strSuit;
    }
    
    //returns the rank
    public String getRank(){
        return this.strRank;
    }
    
    //returns image
    public String getImage(){
        return "cards/" + this.strRank + "-" + this.strSuit + ".png";
    }
    
    //a method that returns the image for the poker class because the ranks are in numbers
    public String getImagePath() {
        //if the card is an ace, king, queen, or j, convert it into a string to get the image
        if (bytRank == 1){
            strRank = "A";
            return "cards/" + this.strRank+ "-" + this.strSuit + ".png";
        }
        if (bytRank == 11){
            strRank = "J";
            return "cards/" + this.strRank + "-" + this.strSuit + ".png";
        }
        if (bytRank == 12){
            strRank = "Q";
            return "cards/" + this.strRank + "-" + this.strSuit + ".png";
        }
        if (bytRank == 13){
            strRank = "K";
            return "cards/" + this.strRank + "-" + this.strSuit + ".png";
        }
        //return the image path
        return "cards/" + this.bytRank + "-" + this.strSuit + ".png";
    }
}