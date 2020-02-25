/**
 * @author Vincent Do <Vincent.Do@EastsideCatholicSchool.org>
 * @version 1.2 (9/24/2019)
 *
 * case-insensitive alphabetical letter-counting inventory program for input Strings,
 * can be added/subtracted with other LetterInventory objects
 */

public class LetterInventory
{
    private int[] count;
    private int size; 						// auto-initializes to 0

    public static final int ALPHABET_LENGTH = 26;	// in case Client needs to refer to alphabet size

    /**
     * precondition: 	n/a
     *
     * postcondition: 	constructed LetterInventory object with this.size & corresponding
     * 					this.count elements incremented if @param data contains alphabetical characters
     */
    public LetterInventory(String data)
    {
        this.count = new int[ALPHABET_LENGTH];
        String temp = data.toUpperCase();

        for (int i = 0; i < temp.length(); i++)
        {
            char letter = temp.charAt(i);

            if (letter >= 'A' && letter <= 'Z')
            {
                this.count[letter - 'A']++;
                this.size++;
            }
        }
    }

    /**
     * precondition: 	@param letter is alphabetical
     *
     * postcondition: 	@returns int value of this.count element corresponding to @param letter
     *
     * @throws IllegalArgumentException if @param letter is non-alphabetical
     */
    public int get(char letter)
    {
        letter = Character.toUpperCase(letter);
        letterCheck(letter);

        return this.count[letter - 'A'];
    }

    /**
     * precondition:	@param letter is alphabetical, @param value is non-negative
     *
     * postcondition:	sets this.count element corresponding to @param letter to @value,
     * 					adjusts this.size accordingly
     *
     * @throws IllegalArgumentException if @param letter is non-alphabetical and/or
     * @param value is negative
     */
    public void set(char letter, int value)
    {
        letter = Character.toUpperCase(letter);
        letterCheck(letter);
        if (value < 0)
            throw new IllegalArgumentException("value must be positive");

        this.size = this.size - count[letter - 'A'] + value;
        this.count[letter - 'A'] = value;
    }

    /**
     * precondition:	@param letter is alphabetical
     *
     * postcondition:	n/a
     *
     * @throws IllegalArgumentException if @param letter is non-alphabetical
     */
    private void letterCheck(char letter)
    {
        if (letter < 'A' || letter > 'Z')
            throw new IllegalArgumentException("letter must be alphabetical");
    }

    /**
     * precondition:	LetterInventory object constructed
     *
     * postcondition:	@returns int value of this.size
     */
    public int size()
    {
        return this.size;
    }

    /**
     * precondition:	LetterInventory object constructed
     *
     * postcondition:	@returns true if this.size is equal to 0, false if this.size not equal to 0
     */
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    /**
     * precondition:	LetterInventory object constructed
     *
     * postcondition:	@returns String of lower-case letters based on this.count,
     * 					times each letter added to String equal to corresponding elements in this.count,
     * 					@returns "[]" if this.size is zero
     */
    public String toString()
    {
        String output = "[";

        for (int i = 0; i < ALPHABET_LENGTH; i++)
        {
            for (int j = 0; j < this.count[i]; j++)
            {
                char letter = (char) (i + 'a');
                output += letter;
            }
        }

        output += "]";

        return output;
    }

    /**
     * precondition:	LetterInventory object constructed, @param other constructed
     *
     * postcondition:	@returns LetterInventory object with count field elements equaling corresponding
     * 					elements of this.count and other.count added together,
     * 					sets size for returned object as sum of this.size and other.size
     */
    public LetterInventory add(LetterInventory other)
    {
        LetterInventory sum = new LetterInventory("");

        for (int i = 0; i < ALPHABET_LENGTH; i++)
        {
            char letter = (char) (i + 'A');
            int value = this.count[i] + other.get(letter);
            int size = this.size + other.size();

            sum.set(letter, value);
            sum.size = size;
        }

        return sum;
    }

    /**
     * precondition:	LetterInventory object constructed, @param other constructed
     *
     * postcondition:	@returns LetterInventory object with count field elements equaling corresponding
     * 					elements of this.count minus corresponding elements of other.count,
     * 					sets size for returned object as difference of this.size and other.size
     * 					@returns null if any difference between corresponding this.count and other.count
     * 					elements is negative
     */
    public LetterInventory subtract(LetterInventory other)
    {
        LetterInventory difference = new LetterInventory("");

        for (int i = 0; i < ALPHABET_LENGTH; i++)
        {
            char letter = (char) (i + 'A');
            int value = this.count[i] - other.get(letter);
            int size = this.size - other.size();

            if (value < 0)
                return null;

            difference.set(letter, value);
            difference.size = size;
        }

        return difference;
    }
}