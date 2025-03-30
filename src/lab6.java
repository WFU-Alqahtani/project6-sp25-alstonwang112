import java.util.Scanner;

public class lab6 {

    public static LinkedList initialize_deck() {

        LinkedList deck = new LinkedList();

        // populate linked list with a single deck of cards
        for (Card.suites s : Card.suites.values()) {
            for(Card.ranks r : Card.ranks.values()) {
                if (r != Card.ranks.NULL && s != Card.suites.NULL) {
                    Card newCard = new Card(s, r);
                    //newCard.print_card();
                    deck.add_at_tail(newCard);
                }
            }
        }

        return deck;
    }

    private static void play_blind_mans_bluff(LinkedList player1, LinkedList computer, LinkedList deck) {
        System.out.println("\nStarting Blind Man's Bluff \n");

        Scanner scnr = new Scanner(System.in);
        char input;

        Card computerCard;
        Card playerCard;

        int victories = 0;
        int losses = 0;

        for (int i = 0; i < 5; i++) {
            computerCard = computer.remove_from_head();
            playerCard = player1.remove_from_head();

            System.out.print("Your opponent's card is: ");
            computerCard.print_card();
            System.out.println();
            System.out.print("Is your card higher (H) or lower (L)? ");

            input = scnr.nextLine().charAt(0);
            while (input != 'H' && input != 'h' && input != 'L' && input != 'l') {
                System.out.print("Invalid input. Try again: ");
                input = scnr.nextLine().charAt(0);
            }

            if (input == 'h' || input == 'H') {
                if (playerCard.getRank().compareTo(computerCard.getRank()) > 0) {
                    victories++;
                } else if (playerCard.getRank().compareTo(computerCard.getRank()) < 0) {
                    losses++;
                } else {
                    if (playerCard.getSuit().compareTo(computerCard.getSuit()) > 0) {
                        victories++;
                    } else {
                        losses++;
                    }
                }
            } else {
                if (playerCard.getRank().compareTo(computerCard.getRank()) < 0) {
                    victories++;
                } else if (playerCard.getRank().compareTo(computerCard.getRank()) > 0) {
                    losses++;
                } else {
                    if (playerCard.getSuit().compareTo(computerCard.getSuit()) > 0) {
                        victories++;
                    } else {
                        losses++;
                    }
                }
            }

            deck.add_at_tail(playerCard);
            deck.add_at_tail(computerCard);
            deck.sanity_check();

            if (victories == 3) {
                break;
            }
        }

        if (victories == 3) {
            rage_quit(player1, computer, deck);
        } else {
            System.out.println("Game Statistics");
            System.out.println("Wins: " + victories);
            System.out.println("Losses: " + losses);
        }
    }

    public static void rage_quit(LinkedList player1, LinkedList computer, LinkedList deck) {
        deck.shuffle(512);
        deck.print();
        deck.sanity_check();

        int num_cards_dealt = 5;
        for (int i = 0; i < num_cards_dealt; i++) {
            player1.add_at_tail(deck.remove_from_head());
            deck.sanity_check();
            computer.add_at_tail(deck.remove_from_head());
            deck.sanity_check();
        }

        play_blind_mans_bluff(player1, computer, deck);
    }

    public static void main(String[] args) {

        // create a deck (in order)
        LinkedList deck = initialize_deck();
        deck.print();
        deck.sanity_check(); // because we can all use one

        // shuffle the deck (random order)
        deck.shuffle(512);
        deck.print();
        deck.sanity_check(); // because we can all use one

        // cards for player 1 (hand)
        LinkedList player1 = new LinkedList();
        // cards for player 2 (hand)
        LinkedList computer = new LinkedList();

        int num_cards_dealt = 5;
        for (int i = 0; i < num_cards_dealt; i++) {
            // player removes a card from the deck and adds to their hand
            player1.add_at_tail(deck.remove_from_head());
            deck.sanity_check();
            computer.add_at_tail(deck.remove_from_head());
            deck.sanity_check();
        }

        // let the games begin!
        play_blind_mans_bluff(player1, computer, deck);
    }
}
