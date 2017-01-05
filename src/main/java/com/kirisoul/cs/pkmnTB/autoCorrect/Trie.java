package com.kirisoul.cs.pkmnTB.autoCorrect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A Trie data structure for organizing the dictionary given by the user's txt
 * file.
 * @author cdluo
 */
public class Trie {

  /**.
   * The trie
   */
  private List<TrieNode> trie;
  /**.
   * The root
   */
  private TrieNode root;
  /**.
   * A word to be inserted
   */
  private String insertWord;
  /**.
   * Unigram Hashmap
   */
  private HashMap<String, Integer> unigram;
  /**
   * Bigram map using two hashmaps.
   */
  private HashMap<String, HashMap<String, Integer>> outerBigram; // Format:
  // <word1, <word2, # of occurences>>
  /**
   * The number 5. Very versatile.
   */
  private static final int FIVE = 5;
  /**
   * Instanstiates the Trie.
   */
  public Trie() {
    trie = new ArrayList<TrieNode>();
    root = new TrieNode('/');
    trie.add(root);
    unigram = new HashMap<String, Integer>();
    outerBigram = new HashMap<String, HashMap<String, Integer>>();
  }

  /**
   * Wrapper for insert. It saves the word to be inserted into insertWord and
   * the unigram hashmap before it gets truncated in the recursive method.
   * @param node
   *          the root node to start insertion
   * @param word
   *          the word to be inserted.
   */
  public void insertWrapper(TrieNode node, String word) {
    insertWord = word;

    if (unigram.get(word) == null) {
      unigram.put(word, 1);
    } else {
      int uniFreq = unigram.get(word);
      unigram.put(word, uniFreq + 1);
    }

    insert(node, word);
//     System.out.println("Inserted: " + word);
  }

  /**
   * Recursively inserts a word into the Trie.
   * @param node
   *          the current letter node we're on
   * @param word
   *          the word to insert (gradually loses its first letters.)
   */
  public void insert(TrieNode node, String word) {
    if (word.length() == 0) {
      node.setEndOfWord();
      node.setWord(insertWord);
      return;
    }

    char firstLetter = word.charAt(0);

    if (!node.childrenContains(firstLetter)) {
      trie.add(node.insertChild(firstLetter));
    }

    String noFirstLetter = word.substring(1, word.length());
    insert(node.getChildren().get(firstLetter), noFirstLetter);
  }

  /**
   * Given two words, adds them into the outerBigram appropiately.
   * @param word1
   *          the first word of the bigram.
   * @param word2
   *          the second word.
   */
  public void insertBigram(String word1, String word2) {
    HashMap<String, Integer> innerBigram;

    if (outerBigram.get(word1) == null) { // word1 appearing for the first time.
      innerBigram = new HashMap<String, Integer>();
      innerBigram.put(word2, 1);
      outerBigram.put(word1, innerBigram);
    } else if (outerBigram.get(word1).get(word2) == null) {
      // word 2 appearing after word1 for the first time.
      innerBigram = outerBigram.get(word1);
      innerBigram.put(word2, 1);
      outerBigram.put(word1, innerBigram);
    } else { // word2 has already appeared after word1, increment word2.
      innerBigram = outerBigram.get(word1);
      innerBigram.put(word2, innerBigram.get(word2) + 1);
      outerBigram.put(word1, innerBigram);
    }
  }

  /**
   * Prints 5 bigrams using outer and inner bigram instances. Used for testing
   * purposes.
   */
  public void print5Bigram() {

    int i = 0;
    HashMap<String, Integer> inner;

    for (String s : outerBigram.keySet()) {
      if (i < FIVE) {
        inner = outerBigram.get(s);
        for (String s2 : inner.keySet()) {
          System.out.println("Bigrams for: " + s + "|" + s2 + "/"
              + inner.get(s2));
        }
      }
      i++;
    }
  }

  /**
   * Given a prefix, tries to find that prefix in the trie, then returns the
   * node of the last letter in the prefix. Useful for testing.
   * @param node
   *          the node the recursion is on.
   * @param prefix
   *          the prefix to find (gradually gets shorter)
   * @return the node if found, null otherwise.
   */
  public TrieNode find(TrieNode node, String prefix) {
    if (prefix.length() == 0) {
      return node;
    }

    char firstLetter = prefix.charAt(0);
    if (!node.childrenContains(firstLetter)) {
      return null;
    }

    String noFirstLetter = prefix.substring(1, prefix.length());
    return find(node.getChildren().get(firstLetter), noFirstLetter);
  }

  /**
   * Checks if the Trie contains the word using the unigram hashmap.
   * @param word
   *          to be checked for
   * @return boolean
   */
  public boolean contains(String word) {
    if (unigram.get(word) != null) {
      return true;
    }
    return false;
  }

  /**
   * Returns the root of the Trie.
   * @return the root.
   */
  public TrieNode getRoot() {
    return root;
  }

  /**.
   * Given a word (outerBigram key), returns the Hashmap associated with that
   * word (the innerBigram.)
   * @param word
   *          the word to get the inner bigam to.
   * @return a hashmap
   */
  public HashMap<String, Integer> getBigram(String word) {
    return outerBigram.get(word);
  }

  /**
   * Returns the unigram Hashmap.
   * @return a hashmap
   */
  public HashMap<String, Integer> getUnigram() {
    return unigram;
  }

  /**
   * Standard toString method.
   * @return string
   */
  public String toString() {
    String result = "";

    for (TrieNode t : trie) {
      result += t.toString();
      result += "\n";
    }

    return result;
  }

  /**
   * Returns a string representing only the first layer of the trie. Used for
   * print lines throughout development.
   * @return string
   */
  public String toStringLayer1() {
    String result = "";

    for (TrieNode t : root.getChildren().values()) {
      result += t.toString();
      result += "\n";
    }

    return result;
  }

  /**
   * Auto-generated hash function.
   * @return int
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((insertWord == null) ? 0 : insertWord.hashCode());
    result = prime * result
        + ((outerBigram == null) ? 0 : outerBigram.hashCode());
    result = prime * result + ((root == null) ? 0 : root.hashCode());
    result = prime * result + ((trie == null) ? 0 : trie.hashCode());
    result = prime * result + ((unigram == null) ? 0 : unigram.hashCode());
    return result;
  }

  /**
   * Auto generated equals function.
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
    Trie other = (Trie) obj;
    if (insertWord == null) {
      if (other.insertWord != null) {
        return false;
      }
    } else if (!insertWord.equals(other.insertWord)) {
      return false;
    }
    if (outerBigram == null) {
      if (other.outerBigram != null) {
        return false;
      }
    } else if (!outerBigram.equals(other.outerBigram)) {
      return false;
    }
    if (root == null) {
      if (other.root != null) {
        return false;
      }
    } else if (!root.equals(other.root)) {
      return false;
    }
    if (trie == null) {
      if (other.trie != null) {
        return false;
      }
    } else if (!trie.equals(other.trie)) {
      return false;
    }
    if (unigram == null) {
      if (other.unigram != null) {
        return false;
      }
    } else if (!unigram.equals(other.unigram)) {
      return false;
    }
    return true;
  }
}
