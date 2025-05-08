import javax.swing.*;
import javax.swing.text.NumberFormatter;
public class IntegerInput extends JSpinner {
    IntegerInput(int initial, int min,int max){
        setModel(new SpinnerNumberModel(initial, min, max,1));
        JFormattedTextField txt = ((JSpinner.NumberEditor) this.getEditor()).getTextField(); // Get the text field
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false); // Prevent invalid input
    }
}
