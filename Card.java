
/**
 * Write a description of class Card here.
 *
 * @author Amiya Chokhawala 
 * @version November 21, 2021 
 */
public class Card
{
    // instance variables - replace the example below with your own
    private int rank;
    private String suit; 
    private boolean isFaceUp; 

    /**
     * Constructor for objects of class Card
     */
    public Card(int r, String s)
    {
        rank = r;
        suit = s;
        isFaceUp = false;        
    }

    /**
     * @return rank the number for the card
     */
    public int getRank()
    {
        return rank;
    }

    /**
     * @return suit the suit of the card
     */
    public String getSuit()
    {
        return suit;
    }

    /**
     * @return true if card's color is red
     */
    public boolean isRed()
    {
        if (suit.equals("h") || suit.equals("d"))
        {
            return true;
        }
        return false;
    }

    /**
     * @return true ifthe card is facing up
     */    
    public boolean isFaceUp()
    {
        return isFaceUp;   
    }
    
    /**
     * Turns the card face down
     */
    public void turnDown()
    {
        isFaceUp = false;
    }
        
    /**
     * Turns the card face up
     */
    public void turnUp()
    {
        isFaceUp = true;
    }
    
    /**
     * Returns file name along with directory of the card
     *                 based on the rank and the suit
     * @return the filename and the image for each card
     */
    public String getFileName ()
    {

        if (!(isFaceUp))
            return "cards/back.gif";

        if ((rank > 1) && (rank <= 9))
        {
            return "cards/" + rank + suit + ".gif";
        }

        if (rank == 1)
            return "cards/a" + suit + ".gif";

        if (rank == 10)
            return "cards/t" + suit + ".gif";

        if (rank == 11)
            return "cards/j" + suit + ".gif";

        if (rank == 12)
            return "cards/q" + suit + ".gif";

        return "cards/k" + suit + ".gif";
    }
}
    


