import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
    
    // Write your line count function here
    public static int meaningfulLineCount(String filename) throws IOException {
        try (FileReader in = new FileReader(filename)) {
            BufferedReader br = new BufferedReader(in);
            return (int)br.lines().filter(line -> !line.trim().isEmpty()).filter(line -> line.trim().charAt(0) != '#').count();
        } 
    }
}

// Write your Quaternion record class here

// Write your BinarySearchTree sealed interface and its implementations here
