import java.io.IOException;

public class U2A3_SeyshaPuttagunta {
    public static void main(String[] args) {
        try{
            Items.init();
            Items.readInv();
        } catch (IOException e){
            System.out.println(e);
        }
        new MainWindow();
    }
}