package com.kirisoul.cs.pkmnTB.autoCorrect;

import java.util.HashMap;
import java.util.Set;

/**
 * Each node holds a key value pair. The key is the letter of the node, The
 * value is a hashmap of this node's children (which associates a letter to a
 * node).
 * @author cdluo
 */
public class TrieNode {

  /**.
   * Whether or not this trienode is the end of a word
   */
  private boolean endOfWord = false;
  /**.
   * Children of this node
   */
  private HashMap<Character, TrieNode> children;
  /**.
   * letter/key of this node.
   */
  private char key;
  /**.
   * Word this node is the end of(set if endofWord is true)
   */
  private String word;
  /**.
   * Hashcode number
   */
  private static final int HASH_1 = 1231;
  /**.
   * Hashcode number2
   */
  private static final int HASH_2 = 1237;

  /**
   * Instantiates this TrieNode.
   * @param letter
   *          the letter this node represents.
   */
  public TrieNode(char letter) {
    key = letter;
    children = new HashMap<Character, TrieNode>();
  }

  /**
   * Indicates that this node is the end of a word.
   */
  public void setEndOfWord() {
    endOfWord = true;
  }

  /**.
   * Sets the word
   * @param word1 the word to be set
   */
  public void setWord(String word1) {
    word = word1;
  }

  /**
   * Checks if our children hashmap contains a value for the key c.
   * @param c
   *          a character key to check.
   * @return boolean
   */
  public boolean childrenContains(char c) {
    if (children.containsKey(c)) {
      return true;
    }
    return false;
  }

  /**
   * Inserts a child into our children map.
   * @param c
   *          the key for the new child node.
   * @return a trie node
   */
  public TrieNode insertChild(char c) {
    TrieNode newChild = new TrieNode(c);
    children.put(c, newChild);
    return newChild;
  }

  /**
   * Returns the child map.
   * @return the children hashmap.
   */
  public HashMap<Character, TrieNode> getChildren() {
    return children;
  }

  /**
   * Returns the boolean indicating if this node is the end of a word.
   * @return boolean
   */
  public boolean getEnd() {
    return endOfWord;
  }

  /**
   * Returns the character this trieNode has as its key.
   * @return char
   */
  public char getKey() {
    return key;
  }

  /**.
   * Gets the word
   * @return the word
   */
  public String getWord() {
    return word;
  }

  /**
   * Standard toString method.
   * @return string representation of this node.
   */
  public String toString() {
    String result = "Key: " + key + "| Children: ";
    Set<Character> keys = children.keySet();

    for (Character c : keys) {
      result += c;
      result += " ";
    }

    result += "End: " + word;

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
    result = prime * result + ((children == null) ? 0 : children.hashCode());
    result = prime * result + (endOfWord ? HASH_1 : HASH_2);
    result = prime * result + key;
    result = prime * result + ((word == null) ? 0 : word.hashCode());
    return result;
  }

  /**
   * Auto-generated equals function.
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
    TrieNode other = (TrieNode) obj;
    if (children == null) {
      if (other.children != null) {
        return false;
      }
    } else if (!children.equals(other.children)) {
      return false;
    }
    if (endOfWord != other.endOfWord) {
      return false;
    }
    if (key != other.key) {
      return false;
    }
    if (word == null) {
      if (other.word != null) {
        return false;
      }
    } else if (!word.equals(other.word)) {
      return false;
    }
    return true;
  }
}

