package com.kirisoul.cs.pkmnTB.autoCorrect;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Sorts the results array according to the specified criteria.
 * @author cdluo
 */
public class RankComparator implements Comparator<String> {
  /**
   * . inner bigram of the word we were correcting
   */
  private HashMap<String, Integer> innerGram;
  /**
   * . unigram map from the trie
   */
  private HashMap<String, Integer> unigram;

  /**
   * Constructs this comparator.
   * @param innerGram1
   *          the bigram
   * @param unigram1
   *          the unigram
   */
  public RankComparator(HashMap<String, Integer> innerGram1,
      HashMap<String, Integer> unigram1) {
    innerGram = innerGram1;
    unigram = unigram1;
  }

  /**
   * Compares the two strings.
   * @return int
   */
  @Override
  public int compare(String s1, String s2) {
    if (s1 == null && s2 != null) {
      return -1;
    } else if (s1 == null && s2 == null) {
      return 0;
    } else if (s1 != null && s2 == null) {
      return 1;
    }

    if (s1.contains(" ")) {
      s1 = s1.split(" ")[0];
    }

    Integer ufrq1 = unigram.get(s1);
    Integer ufrq2 = unigram.get(s2);

    if (innerGram == null) { // User input only 1 word, so use unigram only.
      // ufreq comparison squite (make seperate method?)
      if (ufrq1 == null && ufrq2 == null) {
        if (s1 != s2) {
          return -s1.compareTo(s2);
        } else { // Alphabetical Tie
          return 0;
        }
      } else if (ufrq1 != null && ufrq2 == null) {
        return 1;
      } else if (ufrq1 == null && ufrq2 != null) {
        return -1;
      }

      if (ufrq1 == ufrq2) { // Tie for unigram
        if (s1 != s2) {
          return -s1.compareTo(s2); // Use negated natural string ordering
        } else { // Alphabetical Tie //(since this will be flipped)
          return 0;
        }
      } else if (ufrq1 > ufrq2) {
        return 1;
      } else {
        return -1;
      }
    } else { // Use bigram and unigram ordering
      Integer frq1 = innerGram.get(s1);
      Integer frq2 = innerGram.get(s2);

      if (frq1 == null && frq2 == null) {
        // ufrq suite
        if (ufrq1 == null && ufrq2 == null) {
          if (s1 != s2) {
            return -s1.compareTo(s2);
          } else { // Alphabetical Tie
            return 0;
          }
        } else if (ufrq1 != null && ufrq2 == null) {
          return 1;
        } else if (ufrq1 == null && ufrq2 != null) {
          return -1;
        }
        if (ufrq1 == ufrq2) { // Tie for unigram
          if (s1 != s2) {
            return -s1.compareTo(s2);
          } else { // Alphabetical Tie
            return 0;
          }
        } else if (ufrq1 > ufrq2) {
          return 1;
        } else {
          return -1;
        }
      } else if (frq1 != null && frq2 == null) {
        return 1;
      } else if (frq1 == null && frq2 != null) {
        return -1;
      }

      if (frq1 == frq2) { // Tie for bigram
        if (ufrq1 == ufrq2) { // Tie for unigram
          if (s1 != s2) {
            return -s1.compareTo(s2);
          } else { // Alphabetical Tie
            return 0;
          }
        } else if (ufrq1 > ufrq2) {
          return 1;
        } else {
          return -1;
        }
      } else if (frq1 > frq2) {
        return 1;
      } else {
        return -1;
      }
    }
  }
}
