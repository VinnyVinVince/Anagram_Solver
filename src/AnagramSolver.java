import java.util.*;

/*
 *  Vincent Do
 *  Data Structures
 *  Project 5
 *  An AnagramSolver prints all possible solutions of anagram word combinations from a given dictionary
 *  for a given phrase.
 */
public class AnagramSolver {

    // All possible words in an anagram combination, each mapped to associated letter counts.
    private Map<String, LetterInventory> dictionary;

    // Temporary storage for possible word combinations.
    private Stack<String> answers;

    // Shortened dictionary specific to a phrase for faster anagram generation.
    private Set<String> prunedDict;

    /*
     *  Pre:  List is assumed to be non-empty and contains no duplicates as per project specifications.
     *
     *  Post: Assigns the dictionary for the object and maps letter counts to each individual word in the list.
     */
    public AnagramSolver(List<String> list) {

        this.answers = new Stack<>();
        this.prunedDict = new TreeSet<>();
        this.dictionary = new HashMap<>();

        for (String word : list) {

            this.dictionary.put(word, new LetterInventory(word));

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
        for (String word : this.dictionary.keySet()) {

            if (input.subtract(this.dictionary.get(word)) != null) {

                this.prunedDict.add(word);

            }

        }

        explore(input, max);

        // Resetting temporary dictionary after method execution.
        this.prunedDict.clear();

    }

    // Private pair with print method. Recursively backtracks to find valid solutions for given phrase and maximum.
    private void explore(LetterInventory input, int max) {

        if (input.isEmpty() && (max == 0 || this.answers.size() == max)) {

            // To exclude answer groupings with less words than the target max, the grouping size must be
            // equal to the target size (excluding the 0-case) to be printed.
            System.out.println(this.answers);

        } else {

            for (String word : this.prunedDict) {

                LetterInventory difference = input.subtract(this.dictionary.get(word));

                if (difference != null) {

                    this.answers.push(word);

                    // If the current answer grouping is larger than the max (excluding the 0-case),
                    // it will not be getting closer to the base case, so it will be skipped over.
                    if (max == 0 || this.answers.size() <= max) {

                        explore(difference, max);

                    }

                    this.answers.pop();

                }

            }

        }

    }

}