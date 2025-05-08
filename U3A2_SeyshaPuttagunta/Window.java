import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Window extends JFrame {
    Books books = new Books();
    JTextArea[] outputFlds = {new JTextArea(), new JTextArea()};
    Window() {
        setLayout(new GridBagLayout());
        FormattedPanel pnlInputs = new FormattedPanel();
        FormattedPanel pnlOutputs = new FormattedPanel();
        GridBagUtlity gbc = new GridBagUtlity(0, 0);
        JButton btnList = new JButton("List Books");
        JButton[] btnFinds = { new JButton("Find it!"), new JButton("Find it!"), new JButton("Find it!") };
        JComponent[] inputFlds = { new GeneralInput(10, new Dimension(190,20)), new GeneralInput(200, new Dimension(190, 20)),
                new TextInput(200, new Dimension(190, 20)) };
        JComponent[][] inputElements = { { new JLabel("ISBN#: "), inputFlds[0], btnFinds[0] },
                { new JLabel("Title: "), inputFlds[1], btnFinds[1] }, { new JLabel("Author: "), inputFlds[2], btnFinds[2] } };
        pnlInputs.addElements(inputElements);

        JScrollPane[] pns = {new JScrollPane(outputFlds[0]),new JScrollPane(outputFlds[1])};
        for(JTextArea i:outputFlds){
            i.setEditable(false);
        }
        for(JScrollPane i : pns){
            i.setPreferredSize(new Dimension(550,100));
            i.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        String[] keys = {"ISBN","Name","Author"};
        for(int i = 0; i < inputFlds.length; i++){
            final Integer innerI = Integer.valueOf(i);
            btnFinds[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {               
                    String key = ((JTextField)inputFlds[innerI]).getText().toLowerCase();
                    processInput(keys[innerI], key);}});
        }
        
        btnList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputFlds[0].setText("");
                outputFlds[1].setText("");
                for(int i = 0; i < books.books.size(); i++){
                    outputFlds[0].append(books.books.get(i)+"\n");
                    outputFlds[1].append(books.books.get(i)+"\n");
                }
            }
            
        });
        

        JComponent[][] outputElements = {{new JLabel("Linear Search: "),pns[0]},{new JLabel("Binary Search: "), pns[1]}};
        pnlOutputs.addElements(outputElements);
        add(new JLabel("Children's Classics Book Finder"), gbc);
        gbc.nextY();
        add(pnlInputs, gbc);
        gbc.nextY();
        add(btnList,gbc);
        gbc.nextY();
        add(pnlOutputs,gbc);
        pack();
        setVisible(true);
    }
    private int[] binarySearch(String[] toSearch, int left, int right, String key) {
        if (toSearch.length == 0) {
            return null; // Key not found.
        }
        if (left> right){
            return null;
        }
        if(key == null ){
            return null;
        }
        int middle = (left+right)/ 2;
        
        if (toSearch[middle].equals(key)) {
            return collect(toSearch, middle, key,left);
        } else if (key.compareTo(toSearch[middle]) > 0) {
            return binarySearch(toSearch, middle+1,right ,key);
        } else {
            
            
                return binarySearch(toSearch,left,middle-1, key);
           
        }
    }
    private int[] collect(String[] toCollect, int index, String key, int offset){
        int[] indexes;
        int start = index;
        int end = index;
        while(start > 0 && toCollect[start-1].equals(key)){
            start --;
        }
        while(end < toCollect.length-1 && toCollect[end+1].equals(key)){
            end++;
        }
        indexes = new int[end-start+1];
        for(int i = 0; i < indexes.length; i++){
            indexes[i] = start + i;
        }
        return indexes;
    }
    private int[] originalIndicesBinary(String[] toSort, String key){
        ArrayList<Map.Entry<String, Integer>> pairedList = new ArrayList<>();
                for (int i = 0; i < toSort.length; i++) {
                    pairedList.add(new AbstractMap.SimpleEntry<>(toSort[i].toLowerCase(), i));
                }
                pairedList.sort(Map.Entry.comparingByKey());
                String[] toSearch = new String[toSort.length];
                for(int i = 0; i < toSort.length; i++){
                    toSearch[i] = pairedList.get(i).getKey();
                }
                int[] foundIndexes = binarySearch(toSearch,0,toSearch.length-1, key);
                if(foundIndexes == null){
                    return null;
                }
                ArrayList<Integer> originalIndices = new ArrayList<>();
                for (int index : foundIndexes) {
                    originalIndices.add(pairedList.get(index).getValue());
                }
                // Convert the original indices list to an array.
                return originalIndices.stream().mapToInt(i -> i).toArray();
    }
    private Book[] linearSearch(Book[] books, String key, String Category){
        ArrayList<Book> foundBooks = new ArrayList<Book>();
        if (Category.equals("ISBN")){
            for(Book i: books){
                if(i.getISBN().toLowerCase().equals(key.toLowerCase())){
                    foundBooks.add(i);
                }
            }
        }
        else if(Category.equals("Author")){
            for(Book i: books){
                if(i.getAuthor().toLowerCase().equals(key.toLowerCase())){
                    foundBooks.add(i);
                }
            }
        } else {
            for(Book i: books){
                if(i.getName().toLowerCase().equals(key.toLowerCase())){
                    foundBooks.add(i);
                }
            }
        }
        return foundBooks.toArray(new Book[foundBooks.size()]);
    }
    private void processInput(String Category, String key){
        outputFlds[0].setText("");
        outputFlds[1].setText("");
        String[] toSearch;
        if(Category.equals("ISBN")){
            toSearch = books.getISBNs();
        }
        else if(Category.equals("Name")){
            toSearch = books.getNames();
        } else {
            toSearch = books.getAuthors();
        }
        int[] foundIndecesBin = originalIndicesBinary(toSearch, key);
        if(foundIndecesBin == null){
            outputFlds[1].setText("Book not found!");
        }else{
            for(int i = 0; i < foundIndecesBin.length; i ++){
                outputFlds[1].append(books.books.get(foundIndecesBin[i]).toString() + "\n");
            }
        }
        
        Book[] booksLin = books.books.toArray(new Book[books.books.size()]);
        Book[] foundBooksLin = linearSearch(booksLin, key, Category);
        if(foundBooksLin.length == 0){
            outputFlds[0].setText("Book not found!");;
        }else {
            for(int i = 0; i < foundBooksLin.length; i ++){
                outputFlds[0].append(foundBooksLin[i].toString() + "\n");
            } 
        }
    }
}
