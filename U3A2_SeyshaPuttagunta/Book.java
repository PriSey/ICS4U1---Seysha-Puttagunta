public class Book {
    private String ISBN;
    private String name;
    private String author;

    Book(String ISBN, String name, String author){
        this.ISBN = ISBN;
        this.name = name;
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public String toString() {
        return "|ISBN: " + ISBN + "| |Name: " + name + "| |Author: " + author + "|";
    }
}
