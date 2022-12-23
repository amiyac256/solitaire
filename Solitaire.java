import java.util.*;
/**
 * Solitaire class which makes up the game Solitaire 
 * 
 * @author Amiya Chokhawala 
 * @version January 6, 2022 
 */
public class Solitaire
{
    /**
     * @param args main method parameter
     */
    public static void main(String[] args)
    {
        new Solitaire();
    }

    private Stack<Card> stock;
    private Stack<Card> waste;
    private Stack<Card>[] foundations;
    private Stack<Card>[] piles;
    private SolitaireDisplay display;
    
    /**
     * Constructor for the Solitaire class
     */
    public Solitaire()
    {
        foundations = new Stack[4];
        piles = new Stack[7];

        for (int i=0; i<7; i++)
        {
            piles[i] = new Stack<Card>();
        }

        for (int i=0; i<4; i++)
        {
            foundations[i] = new Stack<Card>();
        }

        stock = new Stack<Card>();
        waste = new Stack<Card>();
        createStock();
        deal();

        display = new SolitaireDisplay(this);
    }

    /**
     * @return the card on top of the stock,
     *         or null if the stock is empty
     */
    public Card getStockCard()
    {
        if (stock.isEmpty())
            return null;
        return stock.peek();
    }

    /**
     * @return the card on top of the waste,
     * or null if the waste is empty
     */
    public Card getWasteCard()
    {
        if (waste.isEmpty())
            return null;
        return waste.peek();
    }

     /**
     * @precondition:  0 <= index < 4
     * @postcondition: returns the card on top of the given
     * foundation, or null if the foundationis empty
     * 
     * @return card the card at the top of the foundation stack at the index 
     * @param index the index of the foundation stack
     */
    public Card getFoundationCard(int index)
    {
        if (foundations[index].isEmpty())
            return null;
        return foundations[index].peek();
    }

    /**
     * @precondition: 0 <= index < 7
     * @postcondition: Returns a reference to the given pile.
     * 
     * @return the stack of card of the type which has a stack of cards
     * @param index the index of which piles' stack
     */
    public Stack<Card> getPile(int index)
    {
        return piles[index];
    }
    
    /**
     * createStock() to help Debug and Test 
     */
    public void createStockDemo()
    {
        ArrayList<Card> list = new ArrayList<Card>();
        String[] suit = {"c", "d", "h", "s"};
        int numOfSuits = suit.length;
                    for (int a = 1; a < 14; a++){

        for (int i = 0; i < numOfSuits; i++)

            {
                list.add(new Card(a, suit[i]));
            }
        }

        int counter = 52;
        while (list.size()!= 0)
        {
            stock.push(list.remove(0));
            counter--;
        }
    }

    /**
     * Replenish the stock with the randomly shuffled cards
     */
    public void createStock()
    {
        ArrayList<Card> list = new ArrayList<Card>();
        String[] suit = {"c", "d", "h", "s"};
        int numOfSuits = suit.length;
        for (int i = 0; i < numOfSuits; i++)
        {
            for (int a = 1; a < 14; a++)
            {
                list.add(new Card(a, suit[i]));
            }
        }

        int counter = 52;
        while (list.size()!= 0)
        {
            stock.push(list.remove((int) (100 *  Math.random()) % counter));
            counter--;
        }
    }
    
    /**
     * deal() to help Debug and Test 
     */
    public void dealDemo ()
    {
        int numCards = 1;
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 13; j++)
            {
                foundations[i].push(stock.pop());
            }
        }
    }
    
    /**
     * Moves the right amount of cards to 7 piles from the stock
     */
    public void deal ()
    {
        int numCards = 1;
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < numCards; j++)
            {
                piles[i].push(stock.pop());
            }
            numCards++;
        }

        for (int a = 0; a < 7; a++)
        {
            piles[a].peek().turnUp();
        }
    }

    /**
     * Moves up to 3 cards from the stock to the waste
     */
    private void dealThreeCards ()
    {
        for (int i = 0; i < 1; i++)
        {
            if (!(stock.isEmpty()))
            {
                stock.peek().turnUp();
                waste.push(stock.pop());
            }
        }
    }
    
    /**
     * Moves all cards back from the waste to stock
     */
    private void resetStock ()
    {
        while (!(waste.isEmpty()))
        {
            waste.peek().turnDown();
            stock.push(waste.pop());
        }
    }

    /**
     * Invoked when stock is clicked. 
     * It either moves up to 3 cards to waste. If stock is empty, reset the stock.
     */
    public void stockClicked()
    {
        if ((display.isPileSelected() == false || 
            display.isWasteSelected() == false))
        {
            System.out.println("stock clicked");

            if (!stock.isEmpty())
            {
                dealThreeCards();
            }
            else
            {
                resetStock();
            }
        }
    }

    /**
     * Called when the waste is clicked
     * Invoked when waste is clicked. It toggles between selected and not-selected.
     */
    public void wasteClicked()
    {
        if (!(waste.isEmpty()) && !(display.isPileSelected()) 
        && !(display.isWasteSelected()))
        {
            display.selectWaste();
        }
        else if (display.isWasteSelected())
        {
            display.unselect();
        }
        System.out.println("waste clicked");
    }

    /**
     * @precondition: 0 <= index < 4
     * @postcondition: Invoked when any foundation is clicked.
     *                 Card is moved from either the pile or waste to foundation,
     *                 depending on what is alredy selected. Also, it checks if move
     *                 meets the conditions of moving to the foundation.
     * @param index the index of the stack of cards
     */
    public void foundationClicked(int index)
    {
        Card card = new Card(1, "h");
        boolean isSomethingSelected = false;
        if (display.isPileSelected())
        {
            int selectedPileIndex = display.selectedPile();
            card = piles[selectedPileIndex].peek();
            if (canAddToFoundation(card, index))
            {
                piles[selectedPileIndex].pop();
                foundations[index].push(card);
            }
            isSomethingSelected = true;
        }
        else if (display.isWasteSelected())
        {
            card = waste.peek();
            if (canAddToFoundation(card, index))
            {
                waste.pop();
                foundations[index].push(card);
            }
            isSomethingSelected = true;
        }

        if (isSomethingSelected)
        {
            display.unselect();

            boolean didNotWin = false;
            for (int i=0; i<4; i++)
            {
                if (foundations[i].size() < 13)
                {
                    didNotWin = true;
                }
            }
            
            
            
            if (!(didNotWin))
            {
                display.didWin(!didNotWin);
                System.out.println("Yay, you did it!!.");
            }
        }
        else
        {
            if (display.isFoundationSelected())
            {
                int fIndex = display.selectedFoundation();
                display.unselect();
            }
            else if (!foundations[index].isEmpty())
            {
                display.selectFoundation(index);
            }
        }

        System.out.println("foundation #" + index + " clicked");
    }

    /**
     * @precondition: 0 <= index < 7
     * @postcondition: Invoked when any pile is clicked. If it is first click, it selects
     *                 the pile. If it is second click (i.e. another pile is already
     *                 selected, it will move all faceup cards from selected pile to
     *                 clicked pile - after checking if it is legal to move.
     * @param index the index of the stack of cards
     */
    public void pileClicked(int index)
    {
        if (display.isPileSelected())
        {
            int selectedPileIndex = display.selectedPile();
            if (selectedPileIndex == index)
            {
                display.unselect();
                return;
            }
            Stack<Card> stack = removeFaceUpCards(selectedPileIndex);
            if (canAddToPile(stack.peek(), index))
            {
                addToPile(stack, index);
            }
            else
            {
                addToPile(stack, selectedPileIndex);              
            }
            display.unselect();
            return;
        }

        if (display.isWasteSelected())
        {
            if (canAddToPile(waste.peek(), index))
            {
                piles[index].push(waste.pop());
                display.unselect();
            }
            return;
        }

        if (display.isFoundationSelected())
        {
            int fIndex = display.selectedFoundation();

            if (canAddToPile(foundations[fIndex].peek(), index))
            {

                piles[index].push(foundations[fIndex].pop());
            }
            else
            {

            }
            display.unselect();
            return;
        }

        if (piles[index].isEmpty())
        {
            return;
        }

        if (!(piles[index].peek().isFaceUp()))
        {
            piles[index].peek().turnUp();
        }
        else
        {
            display.selectPile(index);
        }
        System.out.println("pile #" + index + " clicked");
    }

    /**
     * @precondition 0 <= index < 7
     * @postcondition Returns true if the given card can be
     *                legally moved to the top of the given
     *                pile
     * @return true if card can be added to the pile; else false
     * @param card the card of which this method is determining whether it can be added or not
     * @param index the index of which pile stack it is checking for
     */    
    private boolean canAddToPile (Card card, int index)
    {
        if ((piles[index].isEmpty()))
        {
            if (card.getRank() != 13) 
            {
                return false;
            }
            return true;
        }

        if ((piles[index].peek().isFaceUp()) == false )
        {
            return false;
        }

        if ((piles[index].peek().getRank() == 1) && 
        (card.getRank() == 13))
        {
            if ((piles[index].peek().isRed()) && !(card.isRed()))
                return true;
            if (!(piles[index].peek().isRed()) && (card.isRed()))
                return true;
        }

        if (piles[index].peek().getRank() - 1 == card.getRank())
        {
            if ((piles[index].peek().isRed()) && !(card.isRed()))
                return true;
            if (!(piles[index].peek().isRed()) && (card.isRed()))
                return true;
        }

        return false;
    }

     /**
     * @precondition: 0 <= index < 7
     * @postcondition: Removes all face-up cards
     *                 on the top of the given pile;
     *                 returns a stack containing these cards
     */
    private Stack<Card> removeFaceUpCards(int index)
    {
        Stack<Card> stack = new Stack<Card>();

        while (!(piles[index].isEmpty()) &&  piles[index].peek().isFaceUp())
        {
            stack.push(piles[index].pop());
        }

        return stack;
    }
    
     /**
     * @precondition: 0 <= index < 7
     * @postcondition: Removes elements from cards, and adds
     *                 them to the given pile.
     */
    private void addToPile (Stack<Card> cards, int index)
    {
        while (!(cards.isEmpty()))
        {
            piles[index].push(cards.pop());
        }
    }

     /**
     * @precondition: 0 <= index < 4
     * @postcondition: Returns true if the given card can be
     *                 legally moved to the top of the given
     *                 foundation
     */
    private boolean canAddToFoundation (Card card, int index)
    {
        if (foundations[index].isEmpty())
        {
            if (card.getRank() == 1)
            {
                return true;
            }
            return false;
        }

        if (foundations[index].peek().getRank()+1 == card.getRank())
            if (foundations[index].peek().getSuit().equals(card.getSuit()))
                return true;
        return false;
    }

}