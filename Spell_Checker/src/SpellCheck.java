/**
 * Spell Checker - an object which can handle any text file,
 * and check his spelling according to a given dictionary.
 * afterwards suggests correct word to use instead of the wrong spelled ones.
 *
 * @author Gal Toubul and David kipnis
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SpellCheck {

    private Dictionary _dict;
    final static String DICTIONARY_PATH = new File("").getAbsolutePath() + "\\src\\dictionary.txt"; // "C:\\Users\\user1\\mmn 15\\src\\dictionary.txt";
    private RedBlackTree _RBTree;

    /**
     * Initialize the spell checker - creates the dictionary
     * for the spelling check.
     * Initialize the red black tree to contain the input words.
     *
     * @throws FileNotFoundException
     */
    public SpellCheck() throws FileNotFoundException {
        _dict = new Dictionary();
        //step 1.a - reading dictionary file and inserting its words to a hash table
        _dict.build(DICTIONARY_PATH);
        _RBTree = new RedBlackTree();
    }

    /**
     * The actual spell checking based on txt file.
     * Iterate through a given text.
     * and suggest more likely word to use based on the built-in dictionary
     * @param textPath - the system path to the txt file.
     * @throws FileNotFoundException
     */
    public void run(String textPath) throws FileNotFoundException {
        _RBTree = new RedBlackTree();
        Scanner sc = new Scanner(new File(textPath));
        sc.useDelimiter("(\\s+|\\n+)");//separating words at the text file using ENTER or whitespace as buffers
        while (sc.hasNext()) {
            String curr_scanned = sc.next();
            if (!curr_scanned.equals(""))
                //step 1.b - inserting the words from the file to RB-Tree
                _RBTree.insert(_RBTree.getRoot(), curr_scanned);
        }
        /*
        step 2 - deleting all the words that exists at the dictionary and
        printing all the words that may have a spelling mistakes with their
        correcting suggestion
         */
        deleteFromRBT(_RBTree, _RBTree.getRoot());
    }

    /**
     * Delete all the words that exists at the dictionary and prints
     * the words that may have a spelling mistakes with their correcting suggestion
     *
     * @param inputTree - the tree which contains the words from our text file
     * @param x - pointer to the root of inputTree
     */
    public void deleteFromRBT(RedBlackTree inputTree, RedBlackNode x) {
        if (x != null && (!(x.getValue().equals("")))) {
            deleteFromRBT(inputTree, x.getLeftSon());
            if (this._dict.contains(x.getValue())) {
                inputTree.delete(x, x.getValue());
            } else {
                System.out.println("\"" + x.getValue() + "\"" + " wasn't found in the dictionary, did you mean: \"" + getSuggestion(x.getValue()) + "\"?");
            }
            deleteFromRBT(inputTree, x.getRightSon());
        }
    }

    /**
     * When the word is not in the dictionary, this function will suggest
     * a fixed word for you.
     * Based on two principles -
     *  a. word length -
     *      1. same with raise the potential by 2
     *      2. a difference by 1 will raise the potential by 1
     *  b. The amount of similar letters in the same order
     *     Each letter will gain 1 point to the potential count of the word.
     *
     * @param input - Word
     * @return a String that is the potential word
     */
    public String getSuggestion(String input) {
        String potentialWord = "";
        int max = -1;
        int currPotential;
        int lengthDiff = 0;
        ArrayList<String>[] copiedDictionary = _dict.getDict();
        for (int i = 0; i < copiedDictionary.length; i++) {
            for (String word : copiedDictionary[i]) {
                // handle b. type of potential
                currPotential = potential(word, input, 0); // the amount of similar characters in order
                // handle a. type of potential
                lengthDiff = Math.abs(word.length() - input.length());
                if (currPotential > 0) {
                    currPotential += (lengthDiff < 1) ? 1 : 0; // in case of no difference between word's size
                    currPotential += (lengthDiff < 2) ? 1 : 0; // in case of difference by 1
                }
                if (currPotential > max) {
                    max = currPotential;
                    potentialWord = word;
                }
            }
        }
        return potentialWord;
    }

    /**
     * Calculate the potential of two strings to be the same word
     * By counting how many similar characters both string contains.
     *
     * @param str1 - first word to compare
     * @param str2 - second word to compare
     * @param max  - the maximum number of same characters in the two words.
     * @return max => the potential of second word to be the actual first with mistakes.
     */
    public int potential(String str1, String str2, int max) {
        if (str1.equals("") || str2.equals("")) {
            return max;
        }
        int option1 = potential(str1.substring(1), str2, max);
        int option2 = potential(str1, str2.substring(1), max);
        int option3 = 0;
        if (str1.charAt(0) == str2.charAt(0)) {
            option3 = potential(str1.substring(1), str2.substring(1), max + 1);
        }
        return tripleMax(option1, option2, option3);
    }

    /**
     * Calculate the max number out of 3 given inputs
     *
     * @param a - Integer 1
     * @param b - Integer 2
     * @param c - Integer 3
     * @return - the max of 1,2,3
     */
    public int tripleMax(int a, int b, int c) {
        if (a > b) {
            return (a > c) ? a : c;
        } else {
            return (b > c) ? b : c;
        }
    }
}

