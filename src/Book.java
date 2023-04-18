// -----------------------------------------------------
// Assignment 4
// Written by: Kateryna Gurina_40188793
// -----------------------------------------------------

/**
 * A class representing a book with title, author, price, ISBN, genre, and year attributes.
 */
public class Book {
    private String title;
    private String author;
    private double price;
    private long ISBN;
    private String genre;
    private int year;

    /**
     * Constructor to initialize a Book object with given parameters.
     *
     * @param title  The title of the book.
     * @param author The author of the book.
     * @param price  The price of the book.
     * @param ISBN   The ISBN of the book.
     * @param genre  The genre of the book.
     * @param year   The year of the book.
     */
    public Book(String title, String author, double price, long ISBN, String genre, int year) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.ISBN = ISBN;
        this.genre = genre;
        this.year = year;
    }

    // Accessors
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public long getISBN() {
        return ISBN;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    // Mutators
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns a string representation of the Book object.
     *
     * @return A string containing the book's title, author, price, ISBN, genre, and year.
     */
    @Override
    public String toString() {
        return title + ", " + author +", " + price +", " + ISBN +", " + genre +", " + year;
    }

    /**
     * Checks if two Book objects are equal by comparing their ISBNs.
     *
     * @param obj The object to be compared with.
     * @return True if both objects have the same ISBN, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return ISBN == book.ISBN;
    }
    /**
     * Parses a book from a string line with the format "ISBN;Title;Author;Year;Price".
     *
     * @param line The string line containing the book information.
     * @return A Book object representing the book in the line.
     * @throws NumberFormatException If the year or price cannot be parsed as an integer or double, respectively.
     */

    public static Book parseBook(String inputLine) {
        String[] parts = inputLine.split(",");

        String title = parts[0].substring(1, parts[0].length() - 1);
        String author = parts[1];
        double price = Double.parseDouble(parts[2]);
        long isbn = Long.parseLong(parts[3]);
        String genre = parts[4];
        int year;
        try {
            year = Integer.parseInt(parts[5]);
        } catch (NumberFormatException e) {
            year = -1;
        }

        return new Book(title, author, price, isbn, genre, year);
    }
}