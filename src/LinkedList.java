import java.util.Random;

// linked list class for a deck of cards
public class LinkedList {

    public Node head;
    public Node tail;
    public int size = 0;

    LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void shuffle(int shuffle_count) {
        Random rand = new Random();

        for (int i = 0; i < shuffle_count; i++) {
            // pick two random integers
            int r1 = rand.nextInt(52);
            int r2 = rand.nextInt(52);

            swap(r1, r2); // swap nodes at these indices
        }
    }

    // remove a card from a specific index
    public Card remove_from_index(int index) {
        Node curr = head;
        int count = 0;

        while (curr != null) {
            if (count == index) {
                if (index == 0) {
                    return remove_from_head();
                } else if (curr.next == null) {
                    tail = curr.prev;
                    tail.next = null;
                    curr.prev = null;
                } else {
                    curr.prev.next = curr.next;
                    curr.next.prev = curr.prev;
                }
                size--;
                return curr.data;
            } else {
                count++;
                curr = curr.next;
            }
        }

        return null;
    }

    // insert a card at a specific index
    public void insert_at_index(Card x, int index) {
        int count = 0;
        Node curr = head;
        Node n = new Node(x);

        while (curr != null) {
            if (count == index) {
                if (index == 0) {
                    n.next = head;
                    head.prev = n;
                    head = n;
                    size++;
                    return;
                } else {
                    n.next = curr;
                    n.prev = curr.prev;
                    curr.prev.next = n;
                    curr.prev = n;
                    size++;
                    return;
                }
            } else {
                count++;
                curr = curr.next;
                if (curr.next == null) {
                    tail.next = n;
                    n.prev = tail;
                    tail = n;
                    size++;
                    return;
                }
            }
        }
    }

    // swap two cards in the deck at the specific indices
    public void swap(int index1, int index2) {
        if (index1 != index2) {
            Card card1;
            Card card2;

            if (index1 < index2) {
                card2 = remove_from_index(index2);
                card1 = remove_from_index(index1);

                if (card1 != null && card2 != null) {
                    insert_at_index(card2, index1);
                    insert_at_index(card1, index2);
                }
            } else {
                card1 = remove_from_index(index1);
                card2 = remove_from_index(index2);

                if (card1 != null && card2 != null) {
                    insert_at_index(card1, index2);
                    insert_at_index(card2, index1);
                }
            }
        }
    }

    // add card at the end of the list
    public void add_at_tail(Card data) {
        Node n = new Node(data);

        if (head == null) {
            head = n;
            tail = n;
        } else if (head == tail) {
            tail = n;
            head.next = tail;
            tail.prev = head;
        } else {
            tail.next = n;
            n.prev = tail;
            tail = n;
        }

        size++;
    }

    // remove a card from the beginning of the list
    public Card remove_from_head() {
        if (head == null) {
            return null;
        } else {
            Card c = head.data;
            if (head != tail) {
                head = head.next;
                head.prev = null;
            } else {
                head = tail = null;
            }
            size--;
            return c;
        }
    }

    // check to make sure the linked list is implemented correctly by iterating forwards and backwards
    // and verifying that the size of the list is the same when counted both ways.
    // 1) if a node is incorrectly removed
    // 2) and head and tail are correctly updated
    // 3) each node's prev and next elements are correctly updated
    public void sanity_check() {
        // count nodes, counting forward
        Node curr = head;
        int count_forward = 0;
        while (curr != null) {
            curr = curr.next;
            count_forward++;
        }

        // count nodes, counting backward
        curr = tail;
        int count_backward = 0;
        while (curr != null) {
            curr = curr.prev;
            count_backward++;
        }

        // check that forward count, backward count, and internal size of the list match
        if (count_backward == count_forward && count_backward == size) {
            System.out.println("Basic sanity Checks passed");
        }
        else {
            // there was an error, here are the stats
            System.out.println("Count forward:  " + count_forward);
            System.out.println("Count backward: " + count_backward);
            System.out.println("Size of LL:     " + size);
            System.out.println("Sanity checks failed");
            System.exit(-1);
        }
    }

    // print the deck
    public void print() {
        Node curr = head;
        int i = 1;
        while (curr != null) {
            curr.data.print_card();
            if(curr.next != null)
                System.out.print(" -->  ");
            else
                System.out.println(" X");

            if (i % 7 == 0) System.out.println("");
            i = i + 1;
            curr = curr.next;
        }
        System.out.println("");
    }
}