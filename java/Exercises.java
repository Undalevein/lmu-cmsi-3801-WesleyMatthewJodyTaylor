import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Exercises {
    static Map<Integer, Long> change(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        var counts = new HashMap<Integer, Long>();
        for (var denomination : List.of(25, 10, 5, 1)) {
            counts.put(denomination, amount / denomination);
            amount %= denomination;
        }
        return counts;
    }

    // Write your first then lower case function here
    public static Optional<String> firstThenLowerCase(List<String> strings, Predicate<String> predicate) {
        return strings.stream().filter(predicate).findFirst().map(String::toLowerCase);
    }

    // Write your say function here
    static record Sayer(String phrase) {

        Sayer and(String word) {
            // word = "" --> add space
            if (word.isEmpty()) {
                return new Sayer(this.phrase + " ");
            }
            // phrase starts with no words --> add 1st word
            if (this.phrase.isEmpty()) {
                return new Sayer(word);
            }
            // add a space + new word to the already made phrase
            return new Sayer(this.phrase + " " + word);
        }
    }

    public static Sayer say() {
        return new Sayer ("");
    }

    public static Sayer say(String word) {
        return new Sayer(word);
    }

    // Write your line count function here
    public static int meaningfulLineCount(String filename) throws IOException {
        try (FileReader in = new FileReader(filename)) {
            BufferedReader br = new BufferedReader(in);
            return (int) br.lines().filter(line -> !line.trim().isEmpty()).filter(line -> line.trim().charAt(0) != '#').count();
        } catch (IOException e) {
            throw new FileNotFoundException("No such file");
        }
    }
}

// Write your Quaternion record class here

// Write your BinarySearchTree sealed interface and its implementations here
