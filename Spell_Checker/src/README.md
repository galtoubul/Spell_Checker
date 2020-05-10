# Spell Checker

This program was written as part of a data structures course by David kipnis and Gal Toubul

## Mission

Finding misspellings at a given text.

## Data Structures

1. Hash table
2. Red Black Tree

The hash table will contain the supplied dictionary.
The red black tree will contain the given text.

## Stages of execution

1. Reading the dictionary's word and inserting them into the hash table.
2. Sorting the words in the hash table using base sort 
3. Reading the input file and inserting its words into the red black tree.
4. Deleting every word at the red black tree which occurs at the dictionary (hash table)
5. Printing the word that were left at the red black tree and giving a suggestion for fixing the misspellings.

## Complexity

n is the number of words in the dictionary and m is the number of words in the given text

Time: O(log(max(n, m))) 
                        Hash Table:
                        inserting is done at O(1)
                        searching in the hash table with aמ even distribution is done at amortized time O(1)
                        and O(log(n)) at WC since we can use binary search 
                        base sort is done at O(n)
                        RBT: all actions are done at O(log(m)) at WC                                                

Space: O(n + m) for storing the dictionary and the given text
