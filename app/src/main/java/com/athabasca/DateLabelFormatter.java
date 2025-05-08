package com.athabasca;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 * DateLabelFormatter is a custom formatter for JFormattedTextField that
 * formats dates to and from a specific pattern.
 */
public class DateLabelFormatter extends AbstractFormatter {

    // The date pattern to be used for formatting and parsing dates
    private String datePattern = "yyyy-MM-dd";
    // The SimpleDateFormat instance to handle the date formatting and parsing
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    /**
     * Converts a string to a date object.
     *
     * @param text the string to be converted to a date
     * @return the parsed date object
     * @throws ParseException if the string cannot be parsed to a date
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    /**
     * Converts a date object to a string.
     *
     * @param value the date object to be converted to a string
     * @return the formatted date string
     * @throws ParseException if the date object cannot be formatted to a string
     */
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}