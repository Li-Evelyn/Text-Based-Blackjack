import java.util.*;

public class Blackjack {
    static double amount = 1000;
    static double bet;
    static Deck deck = new Deck();
    static Scanner s = new Scanner(System.in);
    static String winner = "";
    static Hand player = new Hand(false);
    static Hand dealer = new Hand(true);

    public static void getBet() {
        while (true) {
            System.out.print("Enter an amount to bet for this round: ");
            bet = s.nextDouble();
            if (bet > amount) {
                System.out.println("You don't have those funds available.");
            } else {
                amount -= bet;
                break;
            }
        }
    }

    public static void playerTurn(Hand player, Hand dealer, Deck deck) {
        String response;
        Boolean exit = false;
        while (true) {
            System.out.println("Would you like to (hit) or (stand)?");
            response = s.next();
            switch (response) {
                case "hit":
                    player.addCard(deck.draw());
                    System.out.println(player.toString());
                    if (player.getTotalValue() > 21) {
                        System.out.println("You've busted.\n");
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
            }
            if (exit) {
                break;
            }
        }
    }

    public static void dealerTurn(Hand dealer, Hand player, Deck deck) {
        while (dealer.getTotalValue() <= 16) // draw on all 16s, stand on all 17s
        {
            dealer.addCard(deck.draw());
        }
        System.out.println(dealer.toString(true));
        if (dealer.getTotalValue() > 21) {
            winner = "Player";
            System.out.println("The dealer has busted.");
        } else if (dealer.getTotalValue() < player.getTotalValue()) {
            winner = "Player";
            System.out.println("You've scored higher than the dealer.");
        }
        else if (dealer.getTotalValue() == player.getTotalValue()) {
            winner = "It's a tie!";
        }
        else {
            winner = "Dealer";
            System.out.println("The dealer has a higher score than you.");
        }
    }

    public static void initialize(Deck d, Hand play, Hand deal) {
        d.shuffle();
        for (int i = 0; i < 2; i++) {
            play.addCard(d.draw());
            deal.addCard(d.draw());
        }
    }

    public static void main(String[] args) {
        while (amount > 0) {
            System.out.println("Welcome to Blackjack. You have $" + amount + " available.");
            winner = "";
            getBet();
            System.out.println("\n");
            Deck deck = new Deck();
            Hand player = new Hand(false);
            Hand dealer = new Hand(true);
            initialize(deck, player, dealer);
            System.out.println(player.toString());
            System.out.println(dealer.toString());
            if (player.checkBlackjack()) {
                System.out.println("Blackjack!");
                System.out.println("You have won this round.");
                amount += 2.5 * bet;
            } else {
                Double insurance_bet = 0.0;
                if (dealer.getCard(1).getRank() == 1) {
                    while(true) {
                        System.out.println("The dealer is showing an ace. Would you like insurance? (yes/no)");
                        String response = s.nextLine();
                        Boolean exit = false;
                        switch (response) {
                            case "yes":
                                Boolean exit_inner = false;
                                while (true) {
                                    System.out.println("How much would you like to bet?");
                                    insurance_bet = s.nextDouble();
                                    if (insurance_bet > amount) {
                                        System.out.println("You don't have enough funds.");
                                    } else {
                                        amount -= insurance_bet;
                                        exit_inner = true;
                                        System.out.println("Bet recorded.");
                                    }
                                    if (exit_inner) {
                                        break;
                                    }
                                }
                                exit = true;
                                break;
                            case "no":
                                System.out.println("No insurance bet was added.");
                                exit = true;
                                break;
                            default:
                        }
                        if (exit) {
                            break;
                        }
                    }

                }
                playerTurn(player, dealer, deck);
                if (winner == "") {
                    if (dealer.getTotalValue() == 21) {
                        winner = "Dealer";
                        amount += 2 * insurance_bet;
                        System.out.println("The dealer has a Blackjack.");
                    }
                    dealerTurn(dealer, player, deck);
                }
                if (winner == "Player")
                {
                    System.out.println("You've won this round.");
                    amount += 2 * bet;
                }
                else if (winner == "Dealer")
                {
                    System.out.println("The Dealer has won this round.");
                }
                else
                {
                    System.out.println(winner);
                    amount += bet;
                }
            }
            System.out.println("\n");
        }
        System.out.println("I'm sorry, but you have depleted your funds.");
        System.out.println("Please exit the casino.");
    }

}