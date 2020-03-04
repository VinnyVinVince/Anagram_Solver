import java.util.*;

public class AnagramSolver {

    private List<String> dictionary;

    private Stack<String> answers;

    private Set<String> prunedDict;

    private Map<String, LetterInventory> words;

    public AnagramSolver(List<String> list) {

        this.dictionary = list;
        this.answers = new Stack<>();
        this.prunedDict = new TreeSet<>();
        this.words = new HashMap<>();

        for (String word : this.dictionary) {

            this.words.put(word, new LetterInventory(word));

        }

    }

    public void print(String s, int max) {

        if (max < 0) {

            throw new IllegalArgumentException();

        }

        LetterInventory input = new LetterInventory(s);

        for (String word : this.dictionary) {

            if (input.subtract(this.words.get(word)) != null) {

                this.prunedDict.add(word);

            }

        }

        explore(s, max);
        this.prunedDict.clear();

    }

    private void explore(String s, int max) {

        LetterInventory current;

        if (this.words.containsKey(s)) {

            current = this.words.get(s);

        } else {

            current = new LetterInventory(s);

        }

        if (current.isEmpty() && (max == 0 || this.answers.size() == max)) {

            System.out.println(this.answers);

        } else {

            for (String word : this.prunedDict) {

                LetterInventory difference = current.subtract(this.words.get(word));

                if (difference != null) {

                    this.answers.push(word);

                    if (max == 0 || this.answers.size() <= max) {

                        explore(difference.toString(), max);

                    }

                    this.answers.pop();

                }

            }

        }

    }

}