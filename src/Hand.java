import java.util.*;

public class Hand {
    ArrayList<Card> hand = new ArrayList<Card>();
    protected int totalValue;
    protected boolean over21;
    protected boolean isDealer;
    protected boolean ace = false;

    public Hand(boolean b) {
        this.totalValue = 0;
        this.isDealer = b;
    }

    public Card getCard (int i) {
        if (i < hand.size()) {
            return hand.get(i);
        } else {
            return null;
        }
    }

    public void addCard(Card c) {
        hand.add(new Card(c.getRank(), c.getSuit()));
        if (c.getRank() == 1) {
            if (totalValue + 11 > 21) {
                totalValue += 1;
            } else {
                totalValue += 11;
                ace = true;
            }
        } else {
            totalValue += c.getValue();
        }
        if (totalValue > 21 && ace == true) {
            totalValue -= 10;
            ace = false;
        }
    }

    public int getTotalValue() {
        return totalValue;
    }

    public String toString() {
        String player;
        if (isDealer) {
            player = "Dealer's Hand:\n";
            player += "\tCard: ?????\n";
        } else {
            player = "Player's Hand:\n";
            player += "\t" + hand.get(0).toString() + "\n";
        }

        for (int i = 1; i < hand.size(); i++) {
            player += "\t" + hand.get(i).toString() + "\n";
        }
        if (!isDealer) {
            player += "\tTotal: " + getTotalValue() + "\n";
        }
        return player;

    }

    public boolean checkBlackjack() {
        return getTotalValue() == 21;
    }

    public String toString (boolean b)
    {
        String dealer;
        dealer = "Dealer's Hand:\n";
        for (int i = 0; i < hand.size(); i++)
        {
            dealer += "\t" + hand.get(i).toString() + "\n";
        }
        dealer += "\tTotal: " + getTotalValue() + "\n";
        return dealer;
    }

}