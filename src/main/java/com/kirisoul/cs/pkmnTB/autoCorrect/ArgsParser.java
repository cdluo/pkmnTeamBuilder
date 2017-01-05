package com.kirisoul.cs.pkmnTB.autoCorrect;

/**.
 * ArgsParser taken from autocorrect
 * @author cdluo
 */
public class ArgsParser {

  public static final int PARSE_SIZE = 2;

  /**
   * Given a line of args: Removes all punctuation, and turns upper to lower
   * case. Then returns String[], where index 2 is the last word, 1 is the word
   * before it and 0 holds everything before toCorrect;
   * @param args
   *          the line to be parsed.
   * @return a String[] of size 3.
   */
  public static String[] parseArgs(String args) {
//    String parsed = args.toLowerCase();
    String parsed = args;
    parsed = parsed.replaceAll("[^a-zA-Z]", " "); // Eliminate Punctuation

    String[] argsTokens = parsed.split("\\s+");
    if (argsTokens.length == 0) {
      return null;
    }

    String toCorrect = argsTokens[argsTokens.length - 1];
    String oneBefore = null;

    if (argsTokens.length > 1) {
      oneBefore = argsTokens[argsTokens.length - 2];
    }

    String[] results = new String[PARSE_SIZE];
    results[0] = oneBefore;
    results[1] = toCorrect;

    return results;
  }
}
