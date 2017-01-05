package com.kirisoul.cs.pkmnTB.autoCorrect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.sql.SQLException;

import com.kirisoul.cs.pkmnTB.database.PKMNDB;

/**
 * Autocorrecter incorporates most of the main functionality of autocorrect
 * into one concise class.
 * @author cdluo
 *
 */
public class AutoCorrecter {

  /**
   * . The trie to be used.
   */
  private static Trie trie;
  /**
   * . the results
   */
  private static List<String> results;
  /**
   * . The results size
   */
  private static final int RESULT_SIZE = 3;

  /**.
   * Constructor
   * @param sq the SQLQuery class passed in
   * @throws SQLException exception
   */
  public AutoCorrecter(PKMNDB sq) throws SQLException {
    trie = new Trie();
    results = new ArrayList<String>();
    List<String> pkmnNames = sq.getAllPkmnNames();
    List<String> toAdd = new ArrayList<String>();
    for (String name : pkmnNames) {
      toAdd.add(name);
    }

    String old = "";

    for (String next : toAdd) {

      trie.insertBigram(old, next);
      trie.insertWrapper(trie.getRoot(), next);
      old = next;
    }
  }

  /**
   * Given a prefix, finds all the possible words after it. Then adds them to
   * results.
   * @param prefix
   *          the prefix
   */
  public static void autoComplete(String prefix) {
    TrieNode dfsRoot = trie.find(trie.getRoot(), prefix);
    if (dfsRoot == null) {
      return;
    }
    trieDFS(dfsRoot, prefix);
  }

  /**
   * Appends to the results arraylist all words within the LED of the word
   * parameter.
   * @param word
   *          the word (usually mispelled) to find the distance to.
   * @param distance
   *          the distance to find the levenstein to.
   */
  public static void levenstein(String word, int distance) {
    // Will be using the dynamic approach (seen in CS16).
    // Creating the first row of the table.
    List<Integer> firstRow = new ArrayList<Integer>();

    for (int i = 0; i < word.length() + 1; i++) {
      firstRow.add(i);
    }

    HashMap<Character, TrieNode> gen1 = trie.getRoot().getChildren();
    for (Character k : gen1.keySet()) {
      levHelper(gen1.get(k), k, word, firstRow, distance);
    }
  }

  /**
   * Recursive helper method for performing the levestein search.
   * @param tn
   *          the TrieNode of our current recursion.
   * @param letter
   *          the letter of the tn.
   * @param word
   *          the word to find the distance to.
   * @param prevRow
   *          the previous row of the levenstein table.
   * @param distance
   *          the max levenstein distance allowed.
   */
  public static void levHelper(TrieNode tn, Character letter, String word,
      List<Integer> prevRow, int distance) {

    int columns = word.length() + 1;
    List<Integer> newRow = new ArrayList<Integer>();
    newRow.add(prevRow.get(0) + 1);

    // Constructing the next row of the table.
    for (int i = 1; i < columns; i++) {
      int insertCost = newRow.get(i - 1) + 1;
      int deleteCost = prevRow.get(i) + 1;
      int replaceCost = prevRow.get(i - 1);

      if (word.charAt(i - 1) != letter) {
        replaceCost += 1;
      }

      newRow.add(Math.min(insertCost, Math.min(deleteCost, replaceCost)));
    }

    // Comparing if the last element of this row (led from word to
    // word found from trie) has a led <=distance.
    if (newRow.get(newRow.size() - 1) <= distance && tn.getEnd()) {
      if (!results.contains(tn.getWord())) {
        results.add(tn.getWord());
      }
    }

    // Keep searching if the row had a value less than or equal to
    // the max distance. Since one of the children rows may be a word
    // that is under the max distance.
    if (Collections.min(newRow) <= distance) {
      HashMap<Character, TrieNode> children = tn.getChildren();
      for (Character k : children.keySet()) { // Multiple nodes under same key!
        levHelper(children.get(k), k, word, newRow, distance);
      }
    }
  }

  /**
   * Given a conjoined word (EX:mousecheese), adds suggestions of the two
   * separated words to the results array.
   * @param joinedWord
   *          the "word" to split.
   */
  public static void whiteSpace(String joinedWord) {
    char[] word = joinedWord.toCharArray();
    String possibleWord = "";
    String possibleWord2;
    String toAdd; // If possibleWord and possibleWord2 are both in the trie,
    // this will hold their concatenation.

    for (int i = 0; i < word.length; i++) {
      possibleWord += word[i];
      if (trie.contains(possibleWord)) {
        possibleWord2 = new String(Arrays.copyOfRange(word, i + 1,
            word.length));
        if (trie.contains(possibleWord2)) {
          toAdd = possibleWord + " " + possibleWord2;
          if (!results.contains(toAdd)) {
            results.add(toAdd);
          }
        }
      }
    }
  }

  /**
   * Ranks the results arraylist by simple criteria: 1. Exact Match 2. Bigram
   * probability 3. Unigram probability 4. Alphabetical
   * @param prevWord
   *          the word before the word we're correcting.
   */
  public static void simpleRank(String prevWord) {
    HashMap<String, Integer> prevWordGram = trie.getBigram(prevWord);
    HashMap<String, Integer> unigram = trie.getUnigram();
    RankComparator rc = new RankComparator(prevWordGram, unigram);
    Collections.sort(results, rc);
    Collections.reverse(results); // Reverse for descending order.
  }

  /**
   * Adds all children words of the prefix to results.
   * @param node
   *          the current node of recursion
   * @param prefixPlus
   *          the prefix, which is appended to.
   */
  public static void trieDFS(TrieNode node, String prefixPlus) {
    if (node.getEnd()) {
      if (!results.contains(prefixPlus)) {
        results.add(prefixPlus);
      }
    }

    for (TrieNode t : node.getChildren().values()) {
      trieDFS(t, prefixPlus + t.getKey());
    }

    return;
  }

  /**
   * Calls on the appropiate functions in the functions class to populate the
   * results array. Then returns a string with the final results.
   * @param args2
   *          the input text from the user.
   * @return String
   */
  public static List<String> generateSuggestions(String args2) {
    results.clear();
    // this localResults arraylist bypasses most concurrentModification errors
    // since the generateSuggestions doesn't directly operate on results,
    // besides clearing it at the beginning. I was getting constant concurrent
    // modificationexceptions before putting this in.
    List<String> localResults = new ArrayList<String>();
    ArgsParser ap = new ArgsParser();

    String toCorrect = ap.parseArgs(args2)[1];
    String oneBefore = ap.parseArgs(args2)[0];
    // System.out.println(toCorrect + " | " + oneBefore + " | " + beforeWord);

    if (trie.contains(toCorrect)) {
      localResults.add(toCorrect);
    }

    autoComplete(toCorrect);
    levenstein(toCorrect, 2);
    simpleRank(oneBefore);
    
    for (String s : results) {
      localResults.add(s);
    }

    List<String> finalResults = new ArrayList<String>();
    if (localResults.contains(toCorrect)) {
      finalResults.add(toCorrect);
    }

    for (int j = 0; j < localResults.size(); j++) {
      if (finalResults.size() <= RESULT_SIZE) {
        if (!finalResults.contains(localResults.get(j))) {
          finalResults.add(localResults.get(j));
        }
      }
    }

    return finalResults;
  }

  /**
   * Generates a hashcode for the function.
   * @return int
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((results == null) ? 0 : results.hashCode());
    result = prime * result + ((trie == null) ? 0 : trie.hashCode());
    return result;
  }

  /**
   * Checks for equality.
   * @return boolean
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AutoCorrecter other = (AutoCorrecter) obj;
    if (results == null) {
      if (other.results != null) {
        return false;
      }
    } else if (!results.equals(other.results)) {
      return false;
    }
    if (trie == null) {
      if (other.trie != null) {
        return false;
      }
    } else if (!trie.equals(other.trie)) {
      return false;
    }
    return true;
  }
}
