import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
public class Books {
    public ArrayList<Book> books = new ArrayList<Book>();
    Books(){
        try (BufferedReader rd = new BufferedReader(new FileReader("BookList.txt"))) {
            String line;
            while((line = rd.readLine()) != null){
                books.add(new Book(line.trim(), rd.readLine().trim(), rd.readLine().trim().substring(1)));
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String[] getISBNs(){
        String[] ISBNs = new String[books.size()];
        for(int i = 0; i < books.size(); i++ ){
            ISBNs[i] = books.get(i).getISBN();
        }
        return ISBNs;
    }
    public String[] getNames(){
        String[] ISBNs = new String[books.size()];
        for(int i = 0; i < books.size(); i++ ){
            ISBNs[i] = books.get(i).getName();
        }
        return ISBNs;
    }
    public String[] getAuthors(){
        String[] ISBNs = new String[books.size()];
        for(int i = 0; i < books.size(); i++ ){
            ISBNs[i] = books.get(i).getAuthor();
        }
        return ISBNs;
    }
}
