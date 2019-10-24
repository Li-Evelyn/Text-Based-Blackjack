public class Card {
    protected int suit;
    protected int value;
    protected int rank;

    public Card(int r, int s) {
        this.suit = s;
        this.rank = r;
        if (rank <= 10) {
            this.value = rank;
        } else {
            value = 10;
        }

    }

    public int getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public int getRank() {
        return rank;
    }

    public void setSuit(int s) {
        this.suit = s;
    }

    public void setRank(int r) {
        this.rank = r;
        if (rank <= 10) {
            this.value = rank;
        } else {
            value = 10;
        }
    }

    public void setValue(int v) {
        this.value = v;
    }

    public String toString() {
        String s = "";
        String r = "";
        if (rank > 1 && rank < 11) {
            r = Integer.toString(rank);
        } else {
            switch (rank) {
                case 1:
                    r = "Ace";
                    break;
                case 11:
                    r = "Jack";
                    break;
                case 12:
                    r = "Queen";
                    break;
                case 13:
                    r = "King";
                    break;
                default:
            }

        }
        switch (suit) {
            case 1:
                s = "Spades";
                break;
            case 2:
                s = "Hearts";
                break;
            case 3:
                s = "Clubs";
                break;
            case 4:
                s = "Diamonds";
                break;
            default:
        }
        return "Card: " + r + " of " + s;
    }

}