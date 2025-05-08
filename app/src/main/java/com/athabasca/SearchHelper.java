package com.athabasca;

import java.util.AbstractMap;        // Import for Map.Entry implementation
import java.util.ArrayList;          // Import for dynamic array (List)
import java.util.List;               // Import for List interface
import java.util.Map;                // Import for Map interface

// Class providing helper methods for searching within an array
public class SearchHelper {

    /**
     * Performs a binary search on a sorted array of strings.
     * @param toSearch The sorted array to search within.
     * @param left The left boundary of the search range.
     * @param right The right boundary of the search range.
     * @param key The target string to search for.
     * @return An array of indices where the key is found.
     */
    private int[] binarySearch(String[] toSearch, int left, int right, String key) {
        // Handle edge cases: empty array or null key
        if (toSearch.length == 0 || left > right || key == null) {
            return new int[0]; // Return empty array if key not found or invalid input
        }

        // Find the middle index of the current search range
        int middle = (left + right) / 2;

        // Check if the middle element matches the key
        if (toSearch[middle].equals(key)) {
            // If match is found, collect all occurrences
            return collect(toSearch, middle, key, left);
        } else if (key.compareTo(toSearch[middle]) > 0) {
            // If the key is greater, search in the right half
            return binarySearch(toSearch, middle + 1, right, key);
        } else {
            // If the key is smaller, search in the left half
            return binarySearch(toSearch, left, middle - 1, key);
        }
    }

    /**
     * Collects all indices where the key occurs in the sorted array.
     * @param toCollect The sorted array.
     * @param index The index of the current match.
     * @param key The target string.
     * @param offset The starting index of the current search range.
     * @return An array of indices where the key is found.
     */
    private int[] collect(String[] toCollect, int index, String key, int offset) {
        int start = index; // Start of the matching range
        int end = index;   // End of the matching range

        // Expand to the left to find the first occurrence of the key
        while (start > 0 && toCollect[start - 1].equals(key)) {
            start--;
        }

        // Expand to the right to find the last occurrence of the key
        while (end < toCollect.length - 1 && toCollect[end + 1].equals(key)) {
            end++;
        }

        // Store all indices of the matching range
        int[] indexes = new int[end - start + 1];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = start + i;
        }
        return indexes;
    }

    /**
     * Searches for all occurrences of a key in the original unsorted array and returns their original indices.
     * @param toSort The original array (unsorted).
     * @param key The target string to search for.
     * @return An array of original indices where the key is found.
     */
    public int[] originalIndicesBinary(String[] toSort, String key) {
        // Pair each element with its original index and convert to lowercase for case-insensitive search
        List<Map.Entry<String, Integer>> pairedList = new ArrayList<>();
        for (int i = 0; i < toSort.length; i++) {
            pairedList.add(new AbstractMap.SimpleEntry<>(toSort[i].toLowerCase(), i));
        }

        // Sort the paired list using Heapsort (assumes Heapsort.sort is implemented)
        pairedList = Heapsort.sort(pairedList);

        // Extract the sorted strings into a separate array
        String[] toSearch = new String[toSort.length];
        for (int i = 0; i < toSort.length; i++) {
            toSearch[i] = pairedList.get(i).getKey();
        }

        // Perform binary search on the sorted array
        int[] foundIndexes = binarySearch(toSearch, 0, toSearch.length - 1, key.toLowerCase());
        if (foundIndexes == null) {
            return new int[0]; // Return empty array if key is not found
        }

        // Map found indices back to their original indices
        ArrayList<Integer> originalIndices = new ArrayList<>();
        for (int index : foundIndexes) {
            originalIndices.add(pairedList.get(index).getValue());
        }

        // Convert the list of original indices to an array
        return originalIndices.stream().mapToInt(i -> i).toArray();
    }
}
