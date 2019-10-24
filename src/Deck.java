import java.util.*;
public class Deck {
    ArrayList<Card> deck = new ArrayList<Card>();

    public Deck()
    {
        for (int i = 1; i <= 4; i++)
        {
            for (int j = 1; j <= 13; j++)
            {
                deck.add(new Card(j, i));
            }
        }
    }

    public void shuffle()
    {
        Random r = new Random();
        for (int i = 0; i < 1000; i++)
        {
            int a = r.nextInt(51) + 1;
            int b = r.nextInt(51) + 1;
            Card p = new Card(deck.get(a).getRank(), deck.get(a).getSuit());
            deck.get(a).setSuit(deck.get(b).getSuit());
            deck.get(a).setRank(deck.get(b).getRank());
            deck.get(b).setSuit(p.getSuit());
            deck.get(b).setRank(p.getRank());
        }
    }

    public Card draw()
    {
        Card p = deck.get(deck.size() - 1);
        deck.remove(deck.size() - 1);
        return p;
    }

}