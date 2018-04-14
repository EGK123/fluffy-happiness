/////////////////////////////////////////////////////////////////////////////
// Semester: CS400 Spring 2018
// PROJECT: X-Team Exercise #4, Dictionary Graph
// FILES: Graph.java
// GraphTest.java
// GraphProcessor.java
// GraphProcessorTest.java
// WordProcessor.java
//
// Authors: Patrick Lacina (placina@wisc.edu), Jong Kim (kim532@wisc.edu)
// Due date: 10:00 PM on Monday, April 16th
// Outside sources: None
//
// Instructor: Deb Deppeler (deppeler@cs.wisc.edu)
// Bugs: No known bugs
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class contains some utility helper methods
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class WordProcessor {

  /**
   * Gets a Stream of words from the filepath.
   * 
   * The Stream should only contain trimmed, non-empty and UPPERCASE words.
   * 
   * @see <a href=
   *      "http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">java8
   *      stream blog</a>
   * 
   * @param filepath file path to the dictionary file
   * @return Stream<String> stream of words read from the filepath
   * @throws IOException exception resulting from accessing the filepath
   */
  public static Stream<String> getWordStream(String filepath) throws IOException {
    /**
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html">java.nio.file.Files</a>
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/nio/file/Paths.html">java.nio.file.Paths</a>
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html">java.nio.file.Path</a>
     * @see <a href=
     *      "https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html">java.util.stream.Stream</a>
     * 
     *      class Files has a method lines() which accepts an interface Path object and produces a
     *      Stream<String> object via which one can read all the lines from a file as a Stream.
     * 
     *      class Paths has a method get() which accepts one or more strings (filepath), joins them
     *      if required and produces a interface Path object
     * 
     *      Combining these two methods: Files.lines(Paths.get(<string filepath>)) produces a Stream
     *      of lines read from the filepath
     * 
     *      Once this Stream of lines is available, you can use the powerful operations available
     *      for Stream objects to combine multiple pre-processing operations of each line in a
     *      single statement.
     * 
     *      Few of these features: 1. map( ) [changes a line to the result of the applied function.
     *      Mathematically, line = operation(line)] - trim all the lines - convert all the lines to
     *      UpperCase - example takes each of the lines one by one and apply the function toString
     *      on them as line.toString() and returns the Stream: streamOfLines =
     *      streamOfLines.map(String::toString)
     * 
     *      2. filter( ) [keeps only lines which satisfy the provided condition] - can be used to
     *      only keep non-empty lines and drop empty lines - example below removes all the lines
     *      from the Stream which do not equal the string "apple" and returns the Stream:
     *      streamOfLines = streamOfLines.filter(x -> x != "apple");
     * 
     *      3. collect( ) [collects all the lines into a java.util.List object] - can be used in the
     *      function which will invoke this method to convert Stream<String> of lines to
     *      List<String> of lines - example below collects all the elements of the Stream into a
     *      List and returns the List: List<String> listOfLines =
     *      streamOfLines.collect(Collectors::toList);
     * 
     *      Note: since map and filter return the updated Stream objects, they can chained together
     *      as: streamOfLines.map(...).filter(a -> ...).map(...) and so on
     */

    Stream<String> stream = Files.lines(Paths.get(filepath));

    stream = stream.map(String::trim).filter(word -> word != null && !word.equals(""))
        .map(String::toUpperCase);

    return stream;
  }

  /**
   * Adjacency between word1 and word2 is defined by: if the difference between word1 and word2 is
   * of 1 char replacement 1 char addition 1 char deletion then word1 and word2 are adjacent else
   * word1 and word2 are not adjacent
   * 
   * Note: if word1 is equal to word2, they are not adjacent
   * 
   * @param word1 first word
   * @param word2 second word
   * @return true if word1 and word2 are adjacent else false
   */
  public static boolean isAdjacent(String word1, String word2) {
    // makes word1 the longer word, if it isn't already
    if (word2.length() > word1.length()) {
      String tmp = word1;
      word1 = word2;
      word2 = tmp;
    }
    
    if (word1.length() == word2.length()) {
      // can only be adjacent if there was a single substitution
      if (getNumberOfDifferentLetters(word1, word2) == 1) {
        return true;
      }
    } else if (word2.length() + 1 == word1.length()) {
      // can only be adjacent if a letter was added to word1
      for (int i = 0; i < word2.length(); i++) {
        if (word1.charAt(i) != word2.charAt(i)) {
          word1 = word1.substring(0, i) + word1.substring(i + 1, word1.length());
          if (getNumberOfDifferentLetters(word1, word2) == 0) {
            // the words have to be exactly that same after removing the added letter for the
        	// words to be adjacent
            return true;
          } else {
            return false;
          }
        }
      }
      // word1 is the same as word2 with one letter added on to the end
      return true;
    }
    // all other cases return false
    return false;
  }

  /**
   * Returns the number of letters that are different between the two strings.
   * Precondition: word1 and word2 must have the same number of letters
   * 
   * @author Patrick
   * @param word1 the first word
   * @param word2 the second word
   * @return the number of letters different between the words
   */
  private static int getNumberOfDifferentLetters(String word1, String word2) {
    int count = 0;
    for (int i = 0; i < word1.length(); i++) {
      if (word1.charAt(i) != word2.charAt(i)) {
        count++;
      }
    }
    return count;
  }
}
