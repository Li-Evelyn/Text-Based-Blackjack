import java.util.*;

public class Blackjack_Improved {
    static private double amount = 1000;
    static private double bet = 0;
    static private double insurance_bet = 0;
    static private Deck deck = new Deck();
    static private Scanner s = new Scanner(System.in);
    static private String winner = "";
    static private boolean two_hands;
    static private Hand player;
    static private Hand player2;
    static private Hand dealer;

    private static boolean validateBet(double bet) {
        if (bet > amount) {
            System.out.println("You don't have those funds available.");
            return false;
        } else if (bet <= 0) {
            System.out.println("Please enter a positive bet value.");
            return false;
        } else {
            return true;
        }
    }

    private static void getBet() {
        while (true) {
            System.out.print("Enter an amount to bet for this round: ");
            bet = Math.round(s.nextDouble() * 100)/100;
            if (validateBet(bet)) {
                amount -= bet;
                System.out.println();
                break;
            }
        }
    }

    private static void playerTurn(Hand player, Hand dealer, Deck deck) {
        String response;
        boolean exit = false;
        while (!exit) {
            System.out.print("Would you like to (hit) or (stand)? ");
            response = s.next();
            switch (response) {
                case "hit":
                    player.addCard(deck.draw());
                    System.out.println(player.toString());
                    if (player.getTotalValue() > 21) {
                        System.out.println("You've busted.");
                        System.out.println(dealer.toString(true));
                        winner = "Dealer";
                        exit = true;
                    }
                    break;
                case "stand":
                    exit = true;
                    System.out.println(player.toString());
                    break;
                default:
                    System.out.println("Please enter either 'hit' or 'stand'.");
            }
        }
    }

    private static void dealerTurn(Hand dealer, Hand player, Deck deck) {
        while (dealer.getTotalValue() <= 16) { // draw on all 16s, stand on all 17s
            dealer.addCard(deck.draw());
        }
        System.out.println(dealer.toString(true));
        if (dealer.getTotalValue() > 21) {
            winner = "Player";
            System.out.println("The dealer has busted.");
        } else if (dealer.getTotalValue() < player.getTotalValue()) {
            winner = "Player";
            System.out.println("You've scored higher than the dealer on this hand.");
        } else if (dealer.getTotalValue() == player.getTotalValue()) {
            winner = "Tie";
        } else {
            winner = "Dealer";
            System.out.println("The dealer has a higher score than you.");
        }
    }

    private static void insuranceBet() {
        System.out.println("The dealer is showing an ace.");
        String response;
        boolean exit = false;
        while (!exit) {
            System.out.println("Would you like insurance? (yes/no)");
            response = s.nextLine();
            switch(response) {
                case "yes":
                    System.out.println("Great!");
                    while (true) {
                        System.out.println("How much would you like to bet?");
                        insurance_bet = s.nextDouble();
                        if (validateBet(insurance_bet)) {
                            amount -= insurance_bet;
                            System.out.println("Insurance bet recorded.");
                            exit = true;
                            break;
                        }
                    }
                    break;
                case "no":
                    System.out.println("No insurance bet was recorded.");
                    exit = true;
                    break;
                default:
            }
        }
    }

    private static boolean can_split(Hand player) {
        if (player.getCard(0).getRank() == player.getCard(1).getRank()) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean will_split() {
        String response;
        while (true) {
            System.out.println("Would you like to split? (yes/no)");
            response = s.nextLine();
            switch (response) {
                case "yes":
                    System.out.println("You have chosen to split.");
                    return true;
                case "no":
                    System.out.println("You have chosen not to split.");
                    return false;
                default:
            }
        }
    }

    private static void initialize() {
        two_hands = false;
        deck = new Deck();
        player = new Hand(false);
        player2 = new Hand(false);
        dealer = new Hand(true);
        deck.shuffle();
        for (int i = 0; i < 2; i++) {
            player.addCard(deck.draw());
            dealer.addCard(deck.draw());
        }
    }

    public static void main(String[] args) {
        while (amount > 0) {
            System.out.println("Welcome to Blackjack. You have $" + amount + " available.");
            winner = "";
            getBet();
            initialize();
            System.out.println(player.toString());
            System.out.println(dealer.toString());
            if (player.checkBlackjack()) {
                System.out.println("Blackjack!\nYou have won this round.");
                amount += Math.round(2.5 * bet * 100) / 100;
            } else {
                if (dealer.getCard(1).getRank() == 1) {
                    insuranceBet();
                }
                if (can_split(player)) {
                    if (will_split()) {
                        player2.addCard(player.removeCard());
                        player.addCard(deck.draw());
                        player2.addCard(deck.draw());
                        two_hands = true;
                        System.out.println("Current board:");
                        System.out.println(player.toString());
                        System.out.println(player2.toString());
                        System.out.println(dealer.toString());
                    }
                }
            }
        }
        System.out.println("I'm sorry, but you have depleted your funds.");
        System.out.println("Please exit the casino.");
    }
}
