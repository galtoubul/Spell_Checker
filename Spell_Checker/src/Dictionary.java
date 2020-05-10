/**
 * The dictionary class, An object representing a dictionary
 * which words can be added to the dictionary by Text file.
 * this object Saved by Hash table data set
 *
 * @author Gal Toubul and David kipnis
 * @version 1.0
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Dictionary {
    // recommended to use at least third of the total amount of words for the table size
    private final int HASH_TABLE_SIZE = 1349; //for better results use a prime number
    private ArrayList<String>[] _array;

    /**
     * Initialize a dictionary with hash table
     */
    public Dictionary() {
        _array = new ArrayList[HASH_TABLE_SIZE];
        for (int i = 0; i < HASH_TABLE_SIZE; i++) {
            _array[i] = new ArrayList<>(); // <String>
        }
    }

    /**
     * Constructing a dictionary by a list of words saved in a text file
     * @param path
     * @throws FileNotFoundException
     */
    public void build(String path) throws FileNotFoundException {

        File file = new File(path);
        Scanner sc = new Scanner(file);

        // The next call stops when it reaches a new line or a space
        sc.useDelimiter("(\\n+|\\s+)");
        while (sc.hasNext()) {
            String newWord = sc.nextLine();
            if (newWord.length() > 0) {
                add(newWord);
            }
        }
    }

    /**
     * Hash function, for each char in a string -
     * the hash number will be shift bitwise 5 times, reduced by the previous value
     * and added on top of it the char ASCII integer value.
     * Inspired by Dan Bernshtein's hash function.
     * @param key - input word
     * @return the desired integer value linked to this key
     */
    public int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = key.charAt(i) + ((hash << 5) - hash);
        }
        return Math.abs(hash) % HASH_TABLE_SIZE;
    }

    /**
     * Adding a key to the dictionary
     * @param key - input word
     */
    public void add(String key) {
        key = key.toLowerCase();
        _array[hash(key)].add(key);
        Collections.sort(_array[hash(key)]);
    }

    /**
     * Check if the dictionary contains a certain word
     * @param key - input word
     * @return true when the dictionary contains the word, false otherwise
     */
    public boolean contains(String key) {
        key = key.toLowerCase();
        ArrayList<String> list = _array[hash(key)];
        return (Collections.binarySearch(list, key)!=-1);
    }

    public ArrayList<String>[] getDict() {
        return _array.clone();
    }

}
