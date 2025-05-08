package com.athabasca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FileHelper is a utility class that provides methods for reading from and writing to files.
 */
public class FileHelper {

    /**
     * Reads all lines from a file and returns them as a list of strings.
     *
     * @param file the path to the file to be read
     * @return a list of strings, each representing a line from the file
     * @throws IOException if an I/O error occurs
     */
    public static List<String> readFile(String file) throws IOException {
        List<String> list = new ArrayList<>();

        // Try-with-resources to ensure the BufferedReader is closed after use
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            // Read each line from the file and add it to the list
            while ((s = br.readLine()) != null) {
                list.add(s);
            }
        }

        return list;
    }

    /**
     * Writes a single line to a file. If the file does not exist, it will be created.
     *
     * @param file the path to the file to be written to
     * @param line the line to be written to the file
     * @throws IOException if an I/O error occurs
     */
    public static void writeLine(String file, String line) throws IOException {
        // Try-with-resources to ensure the BufferedWriter is closed after use
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(line); // Write the line to the file
            bw.newLine(); // Add a new line
        }
    }
}