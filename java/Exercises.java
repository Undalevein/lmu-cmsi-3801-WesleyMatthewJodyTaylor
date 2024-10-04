
/**
 * Author: Wesley, Jody, Matthew, and Taylor
 * Collaborators: Ray Toal
 */

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.Math;

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

    static Optional<String> firstThenLowerCase(List<String> strings, Predicate<String> predicate) {
        return strings.stream().filter(predicate).findFirst().map(String::toLowerCase);
    }

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
        return new Sayer("");
    }

    public static Sayer say(String word) {
        return new Sayer(word);
    }

    static int meaningfulLineCount(String filename) throws IOException {
        try (FileReader in = new FileReader(filename)) {
            BufferedReader br = new BufferedReader(in);
            return (int) br.lines()
                           .filter(line -> !line.trim().isEmpty())
                           .filter(line -> line.trim().charAt(0) != '#')
                           .count();
        } catch (IOException e) {
            throw new FileNotFoundException("No such file");
        }
    }
}

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
                d + other.d);
    }

    public Quaternion times(Quaternion other) {
        return new Quaternion(
                a * other.a - b * other.b - c * other.c - d * other.d,
                a * other.b + b * other.a + c * other.d - d * other.c,
                a * other.c - b * other.d + c * other.a + d * other.b,
                a * other.d + b * other.c - c * other.b + d * other.a);
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

        double[] coeffs = { b, c, d };
        String[] symbols = { "i", "j", "k" };
        for (int i = 0; i < coeffs.length; i++) {
            double coeff = coeffs[i];
            String symbol = symbols[i];
            if (coeff != 0) {
                if (coeff > 0 && sb.length() > 0) {
                    sb.append("+");
                }
                if (Math.abs(coeff) == 1) {
                    sb.append(coeff == -1 ? "-" + symbol : symbol);
                } else {
                    sb.append(coeff + symbol);
                }
            }
        }

        if (sb.length() == 0) {
            return "0";
        }
        return sb.toString();
    }

}

sealed interface BinarySearchTree permits Empty, Node {
    int size();

    boolean contains(String value);

    BinarySearchTree insert(String value);
}

final record Empty() implements BinarySearchTree {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(String value) {
        return false;
    }

    @Override
    public BinarySearchTree insert(String value) {
        return new Node(value, this, this);
    }

    @Override
    public String toString() {
        return "()";
    }
}

final class Node implements BinarySearchTree {
    private final String value;
    private final BinarySearchTree left;
    private final BinarySearchTree right;

    Node(String value, BinarySearchTree left, BinarySearchTree right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    @Override
    public int size() {
        return 1 + left.size() + right.size();
    }

    @Override
    public boolean contains(String value) {
        return this.value.equals(value) || left.contains(value) || right.contains(value);
    }

    @Override
    public BinarySearchTree insert(String value) {
        if (value.compareTo(this.value) > 0) {
            return new Node(this.value, left, right.insert(value));
        } else {
            return new Node(this.value, left.insert(value), right);
        }
    }

    @Override
    public String toString() {
        String leftStr = (left instanceof Empty) ? "" : left.toString();
        String rightStr = (right instanceof Empty) ? "" : right.toString();
        return "(" + leftStr + value + rightStr + ")";
    }
}