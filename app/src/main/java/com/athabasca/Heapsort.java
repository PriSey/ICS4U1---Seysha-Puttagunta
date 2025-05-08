package com.athabasca;

import java.util.List;
import java.util.Map;

public class Heapsort 
{
    /**
     * Sorts a list of strings using the heapsort algorithm.
     *
     * Time complexity: O(nlogn)
     *
     * @param list The list of strings to sort.
     * @return The sorted list.
     */
    public static List<Map.Entry<String, Integer>> sort(List<Map.Entry<String, Integer>> list)
    {
        int n = list.size();

        // Build the max heap
        for(int i = n/2 - 1; i >= 0; i--) // Loops n/2 times, where n is the size of the list -> O(n/2)
            heapify(list, i, n);

        // Extract elements from the heap one by one
        for(int i = n - 1; i >= 0; i--) // Loop n times where n is the size of the list -> O(n)
        {
            // Swap the root (maximum element) with the last element in the heap
            Map.Entry<String, Integer> swap = list.get(0);
            list.set(0, list.get(i));
            list.set(i, swap);

            // Restore the max heap property for the reduced heap
            heapify(list, 0, i);
        }

        return list;
    }

    /**
     * Restores the max heap property for the subtree rooted at the given index.
     *
     * @param list The list representing the heap.
     * @param index The root index of the subtree.
     * @param size The size of the heap.
     */
    private static void heapify(List<Map.Entry<String, Integer>> list, int index, int size)
    {
        int largest = index; // Initialize the largest element as the root
        int left = 2 * index + 1; // Left index
        int right = 2 * index + 2; // Right index

        // Check if the left index is larger than the root
        if(left < size && list.get(left).getKey().compareTo(list.get(largest).getKey()) > 0)
            largest = left;
        
        // Check if the right index is larger than the current largest
        if(right < size && list.get(right).getKey().compareTo(list.get(largest).getKey()) > 0)
            largest = right;
            
        // If the largest is not the root, swap and continue heapifying
        if(largest != index) 
        {
            Map.Entry<String, Integer> swap = list.get(largest);
            list.set(largest, list.get(index));
            list.set(index, swap);

            // Recursively heapify the affected subtree
            heapify(list, largest, size);
        }
    }
}