import java.util.*;

public class AnagramSolver {

    private List<String> dictionary;

    private List<String> answers;

    public AnagramSolver(List<String> list) {

        this.dictionary = list;
        this.answers = new ArrayList<>();

    }

    public void print(String s, int max) {

        if (max < 0) {

            throw new IllegalArgumentException();

        }

        explore(s, max);

    }

    private void explore(String s, int num) {

        LetterInventory current = new LetterInventory(s);

        if (current.isEmpty()) {

            System.out.println(this.answers);

        } else {

            for (String word : this.dictionary) {

                LetterInventory difference = current.subtract(new LetterInventory(word));

                if (difference != null) {

                    this.answers.add(word);
                    explore(difference.toString(), num--);
                    this.answers.remove(word);

                }

            }

        }

    }

}