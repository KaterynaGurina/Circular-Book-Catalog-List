// -----------------------------------------------------
// Assignment 4
// Written by: Kateryna Gurina_40188793
// -----------------------------------------------------

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * A class representing a circular BookList with a Node inner class.
 */
public class BookList {
    private Node head;

    /**
     * Constructor to initialize an empty BookList.
     */
    public BookList() {
        head = null;
    }

    /**
     * A private inner class representing a node in the BookList.
     */
    private class Node {
        private Book b;
        private Node next;

        /**
         * Constructor to initialize a Node with a Book object.
         *
         * @param b The Book object to be stored in the node.
         */
        public Node(Book b) {
            this.b = b;
            this.next = null;
        }
    }

    /**
     * Adds a Node with the passed Book object at the start (head) of the list.
     *
     * @param b The Book object to be added.
     */
    public void addToStart(Book b) {
        Node newNode = new Node(b);
        if (head == null) {
            head = newNode;
            head.next = head;
        } else {
            Node temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            newNode.next = head;
            temp.next = newNode;
            head = newNode;
        }
    }

    /**
     * Finds all the book records based on a given year, and stores them in a file called yr.txt.
     *
     * @param yr The year to search for.
     */
    public void storeRecordsByYear(int yr) {
        if (head == null) {
            return;
        }

        StringBuilder records = new StringBuilder();
        Node current = head;

        do {
            if (current.b.getYear() == yr) {
                records.append(current.b.toString()).append(System.lineSeparator());
            }
            current = current.next;
        } while (current != head);

        if (records.length() > 0) {
            try (PrintWriter pw = new PrintWriter(new File(yr + ".txt"))) {
                pw.write(records.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Inserts a new node with the given book at the end of the list.
     *
     * @param b The book to be inserted.
     */
    public void insert(Book b) {
        if (head == null) {
            addToStart(b);
        } else {
            Node newNode = new Node(b);
            Node current = head;
            while (current.next != head) {
                current = current.next;
            }
            newNode.next = head;
            current.next = newNode;
        }
    }
    
    /**
     * Inserts a new Node holding a Book object before a Node with a specified ISBN.
     *
     * @param isbn The ISBN to search for.
     * @param b    The Book object to insert.
     * @return True if the insertion was successful, false otherwise.
     */
    public boolean insertBefore(long isbn, Book b) {
        if (head == null) {
            return false;
        }

        Node current = head;
        Node prev = null;

        do {
            if (current.b.getISBN() == isbn) {
                Node newNode = new Node(b);

                if (current == head) {
                    newNode.next = head;
                    head = newNode;
                } else {
                    prev.next = newNode;
                    newNode.next = current;
                }
                return true;
            }

            prev = current;
            current = current.next;
        } while (current != head);

        return false;
    }
    
    /**
     * Inserts a new node with the given book between two existing nodes with the specified ISBNs.
     *
     * @param isbn1 The ISBN of the first book.
     * @param isbn2 The ISBN of the second book.
     * @param b     The book to be inserted.
     * @return true if the insertion was successful, false otherwise.
     */
    public boolean insertBetween(long isbn1, long isbn2, Book b) {
        if (head == null) {
            return false;
        }

        Node current = head;
        do {
            if (current.b.getISBN() == isbn1 && current.next.b.getISBN() == isbn2) {
                Node newNode = new Node(b);
                newNode.next = current.next;
                current.next = newNode;
                return true;
            }
            current = current.next;
        } while (current != head);

        return false;
    }

    /**
     * Displays the content of the list.
     */
    public void displayContent() {
        if (head == null) {
            return;
        }

        Node current = head;
        do {
            System.out.print(current.b.toString() + " ==>\n");
            current = current.next;
        } while (current != head);

        System.out.println(" head");
    }

    /**
     * Deletes consecutive repeated records in the list.
     *
     * @return true if any records were deleted, false otherwise.
     */
    public void delConsecutiveRepeatedRecords() {
        if (head == null) {
            return;
        }

        Node current = head;
        do {
            if (current.next != null && current.b.getISBN() == current.next.b.getISBN()) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        } while (current.next != head);
    }

    /**
     * Extracts a new list containing books by the specified author.
     *
     * @param aut The author to search for.
     * @return A new BookList containing books by the specified author.
     */
    public BookList extractAuthList(String aut) {
        BookList authorList = new BookList();

        if (head == null) {
            return authorList;
        }

        Node current = head;
        do {
            if (current.b.getAuthor().equals(aut)) {
                authorList.addToStart(current.b);
            }
            current = current.next;
        } while (current != head);

        return authorList;
    }

    /**
     * Swaps the positions of two nodes containing books with the specified ISBNs.
     *
     * @param isbn1 The ISBN of the first book.
     * @param isbn2 The ISBN of the second book.
     * @return true if the swap was successful, false otherwise.
     */
    public boolean swap(long isbn1, long isbn2) {
        if (head == null) {
            return false;
        }

        Node current = head;
        Node prev = null;
        Node first = null;
        Node second = null;
        Node firstPrev = null;
        Node secondPrev = null;

        do {
            if (current.b.getISBN() == isbn1) {
                first = current;
                firstPrev = prev;
            } else if (current.b.getISBN() == isbn2) {
                second = current;
                secondPrev = prev;
            }

            prev = current;
            current = current.next;
        } while (current != head && (first == null || second == null));

        if (first != null && second != null) {
            if (firstPrev != null) {
                firstPrev.next = second;
            } else {
                head = second;
            }

            if (secondPrev != null) {
                secondPrev.next = first;
            } else {
                head = first;
            }

            Node temp = first.next;
            first.next = second.next;
            second.next = temp;
            return true;
        }

        return false;
    }

    /**
     * Commits the contents of the list to a file named "Update_Books.txt".
     */
    public void commit() {
        if (head == null) {
            return;
        }

        try (PrintWriter pw = new PrintWriter(new File("Update_Books.txt"))) {
            Node current = head;
            do {
                pw.println(current.b.toString());
                current = current.next;
            } while (current != head);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
