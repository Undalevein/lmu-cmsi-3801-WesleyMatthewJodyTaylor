import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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
    static Optional<String> firstThenLowerCase(List<String> strings, Predicate<String> predicate) {
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
    static int meaningfulLineCount(String filename) throws IOException {
        try (FileReader in = new FileReader(filename)) {
            BufferedReader br = new BufferedReader(in);
            return (int) br.lines().filter(line -> !line.trim().isEmpty()).filter(line -> line.trim().charAt(0) != '#').count();
        } catch (IOException e) {
            throw new FileNotFoundException("No such file");
        }
    }
}

// Write your Quaternion record class here

record Quaternion(double a, double b, double c, double d) {
    static final Quaternion ZERO = new Quaternion(0, 0, 0, 0);
    static final Quaternion I = new Quaternion(0, 1, 0, 0);
    static final Quaternion J = new Quaternion(0, 0, 1, 0);
    static final Quaternion K = new Quaternion(0, 0, 0, 1);

    public Quaternion {
        if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Coefficients cannot be NaN");
        }
    }

    public Quaternion plus(Quaternion other) {
        return new Quaternion(
            a + other.a,
            b + other.b,
            c + other.c,
            d + other.d
        );
    }

    public Quaternion times(Quaternion other) {
        return new Quaternion(
            a * other.a - b * other.b - c * other.c - d * other.d, 
            a * other.b + b * other.a + c * other.d - d * other.c, 
            a * other.c - b * other.d + c * other.a + d * other.b, 
            a * other.d + b * other.c - c * other.b + d * other.a  
        );
    }

    public List<Double> coefficients() {
        return List.of(a, b, c, d);
    }

    public Quaternion conjugate() {
        return new Quaternion(a, -b, -c, -d);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        if (a != 0) {
            sb.append(a);
        }
        if (b != 0) {
            if (b > 0 && sb.length() > 0) {
                sb.append("+");
            }
            sb.append(b == 1 ? "i" : (b == -1 ? "-i" : b + "i")); 
        }
        if (c != 0) {
            if (c > 0 && sb.length() > 0) {
                sb.append("+"); 
            }
            sb.append(c == 1 ? "j" : (c == -1 ? "-j" : c + "j"));
        }
        if (d != 0) {
            if (d > 0 && sb.length() > 0) {
                sb.append("+"); 
            }
            sb.append(d == 1 ? "k" : (d == -1 ? "-k" : d + "k")); 
        }
    return sb.length() > 0 ? sb.toString() : "0";
    }

}

// Write your BinarySearchTree sealed interface and its implementations here
