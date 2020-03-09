import java.util.*;

/*
 *  Vincent Do
 *  Data Structures
 *  Project 5
 *  An AnagramSolver prints all possible solutions of anagram word combinations from a given dictionary
 *  for a given phrase.
 */
public class AnagramSolver {

    // List of all possible words in an anagram combination.
    private List<String> dictionary;

    // Temporary storage for possible word combinations.
    private Stack<String> answers;

    // Shortened dictionary specific to a phrase for faster anagram generation.
    private Set<String> prunedDict;

    // A mapping of words to associated letter counts for faster operations.
    private Map<String, LetterInventory> words;

    /*
     *  Pre:  List is assumed to be non-empty and contains no duplicates as per project specifications.
     *
     *  Post: Assigns the dictionary for the object and maps letter counts to each individual word in the list.
     */
    public AnagramSolver(List<String> list) {

        this.dictionary = list;
        this.answers = new Stack<>();
        this.prunedDict = new TreeSet<>();
        this.words = new HashMap<>();

        for (String word : this.dictionary) {

            this.words.put(word, new LetterInventory(word));

        }

    }

    /*
     *  Pre:  Max must be non-negative, throws IllegalArgumentException otherwise.
     *
     *  Post: Prints all anagram combination solutions for the given phrase and target ("max") amount of words
     *        per solution. If max is 0, there is no limit to the amount of words in each solution.
     */
    public void print(String s, int max) {

        if (max < 0) {

            throw new IllegalArgumentException("Max is too small.");

        }

        LetterInventory input = new LetterInventory(s);

        // Pruning dictionary and populating temporary short dictionary.
        for (String word : this.dictionary) {

            if (input.subtract(this.words.get(word)) != null) {

                this.prunedDict.add(word.toLowerCase());

            }

        }

        explore(s.toLowerCase(), max);

        // Resetting temporary dictionary after method execution.
        this.prunedDict.clear();

    }

    // Private pair with print method. Recursively backtracks to find valid solutions for given phrase and maximum.
    private void explore(String s, int max) {

        LetterInventory current;

        if (this.words.containsKey(s)) {

            // If passed-in phrase is present in dictionary as to not create duplicate letter counts.
            current = this.words.get(s);

        } else {

            current = new LetterInventory(s);

        }

        if (current.isEmpty() && (max == 0 || this.answers.size() == max)) {

            // To exclude answer groupings with less words than the target max, the grouping size must be
            // equal to the target size (excluding the 0-case) to be printed.
            System.out.println(this.answers);

        } else {

            for (String word : this.prunedDict) {

                LetterInventory difference = current.subtract(this.words.get(word));

                if (difference != null) {

                    this.answers.push(word);

                    // If the current answer grouping is larger than the max (excluding the 0-case),
                    // it will not be getting closer to the base case, so it will be skipped over.
                    if (max == 0 || this.answers.size() <= max) {

                        explore(difference.toString(), max);

                    }

                    this.answers.pop();

                }

            }

        }

    }

}