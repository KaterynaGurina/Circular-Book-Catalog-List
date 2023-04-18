// -----------------------------------------------------
// Assignment 4
// Written by: Kateryna Gurina_40188793
// -----------------------------------------------------

/**
 * This method is the main method and is used to interact with the user through a menu.
 * It reads book records from a file, stores them in a custom linked list, and provides various operations to manipulate the list.
 * The operations include extracting and storing books of a specific year, creating a new list for a given author,
 * inserting a book before or between two books with specified ISBN numbers, swapping the positions of two books,
 * and committing the updated list to a file.
 * It also checks for books with invalid years and stores them in a separate file.
 *
 * @param args an array of command-line arguments for the program (not used)
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        ArrayList<Book> arrLst = new ArrayList<>();
        BookList bkLst = new BookList();
        String filePath = "Books.txt";
        Scanner scanner = new Scanner(System.in);

        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Book book = Book.parseBook(line);
                if (book == null) {
                    continue;
                }

                if (book.getYear() < 1450 || book.getYear() > 2023) {
                    arrLst.add(book);
                } else {
                    bkLst.insert(book);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (!arrLst.isEmpty()) {
            try (PrintWriter pw = new PrintWriter(new File("YearErr.txt"))) {
                for (Book book : arrLst) {
                    pw.println(book.toString());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        System.out.println("YearErr.txt File Created");
        System.out.println("Here are the contents of the bkLst:");
        System.out.println("***************************");
        bkLst.displayContent();
        System.out.println("==> head");

        boolean run = true;
        while (run) {
            displayMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
	                System.out.print("Enter the year: ");
	                int year = scanner.nextInt();
//	                scanner.nextLine(); // Consume the leftover end-of-line character
	                bkLst.storeRecordsByYear(year);
	                break;
                case 2:
                    bkLst.delConsecutiveRepeatedRecords();
                    System.out.println("Consecutive repeated records have been deleted.");
                    System.out.println("Here are the contents of the list after the action:");
                    System.out.println("*********************************************************");
                    bkLst.displayContent();
                    break;
                case 3:
                    System.out.print("Enter the author's name: ");
                    scanner.nextLine(); // Clear the scanner buffer
                    String author = scanner.nextLine();
                    BookList authorList = bkLst.extractAuthList(author);
                    authorList.displayContent();
                    break;
                case 4:
                    System.out.print("Enter the ISBN of the book to insert before: ");
                    long isbnToInsertBefore = scanner.nextLong();
                    System.out.print("Enter the book details (format: Title, Author, Price, ISBN, Genre, Year): ");
//                    scanner.nextLine(); // Clear the scanner buffer
                    String bookDetails = scanner.nextLine();
                    Book bookToInsert = Book.parseBook(bookDetails);
                    bkLst.insertBefore(isbnToInsertBefore, bookToInsert);
                    bkLst.displayContent();
                    break;
                case 5:
                    System.out.print("Enter the first ISBN: ");
                    long isbn1 = scanner.nextLong();
                    System.out.print("Enter the second ISBN: ");
                    long isbn2 = scanner.nextLong();
                    System.out.print("Enter the book details (format: Title, Author, Price, ISBN, Genre, Year): ");
//                    scanner.nextLine(); // Clear the scanner buffer
                    String bookDetailsToInsert = scanner.nextLine();
                    Book bookToInsertBetween = Book.parseBook(bookDetailsToInsert);
                    bkLst.insertBetween(isbn1, isbn2, bookToInsertBetween);
                    bkLst.displayContent();
                    break;
                case 6:
                    System.out.print("Enter the first ISBN: ");
                    long isbnToSwap1 = scanner.nextLong();
                    System.out.print("Enter the second ISBN: ");
                    long isbnToSwap2 = scanner.nextLong();
                    bkLst.swap(isbnToSwap1, isbnToSwap2);
                    bkLst.displayContent();
                    break;
                case 7:
                    bkLst.commit();
                    break;
                case 8:
                    run = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
    private static void displayMenu() {
        System.out.println("Tell me what you want me to do? Let’s Chat since this is trending now! Here are the options:");
        System.out.println("1) Give me a year # and I would extract all records of that year and store them in a file for that year;");
        System.out.println("2) Ask me to delete all consecutive repeated records;");
        System.out.println("3) Give me an author name and I will create a new list with the records of this author and display them;");
        System.out.println("4) Give me an ISBN number and a Book object, and I will insert Node with the book before the record with this ISBN;");
        System.out.println("5) Give me 2 ISBN numbers and a Book object, and I will insert a Node between them, if I find them!");
        System.out.println("6) Give me 2 ISBN numbers and I will swap them in the list for rearrangement of records; of course if they exist!");
        System.out.println("7) Tell me to COMMIT! Your command is my wish. I will commit your list to a file called Updated_Books;");
        System.out.println("8) Tell me to STOP TALKING. Remember, if you do not commit, I will not!");
        System.out.print("Enter your Selection: ");
    }    
}