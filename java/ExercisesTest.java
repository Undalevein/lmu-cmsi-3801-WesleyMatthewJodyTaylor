import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.concurrent.Callable;

public class ExercisesTest {

    //////////////////////////////////////////////////////////////////////////////////////
    HashMap<Boolean, Integer> counts = new HashMap<>(Map.of(true, 0, false, 0));

    void suite(String name) {
        System.out.printf("\nTesting %s", name);
    }

    void expect(boolean condition) {
        counts.put(condition, counts.get(condition) + 1);
        System.out.print(condition ? " ." : " F");
    }

    void expectToThrow(Callable<?> c, Class<? extends Exception> exception, String message) {
        try {
            c.call();
            expect(false);
        } catch (Exception e) {
            expect(exception.isInstance(e) && e.getMessage().contains(message));
        }
    }

    public static void main(String[] args) throws IOException{
        new ExercisesTest().run();
    }
    //////////////////////////////////////////////////////////////////////////////////////

    void run() throws IOException {
        suite("change");
        expectToThrow(() -> Exercises.change(-50),
                IllegalArgumentException.class, "Amount cannot be negative");
        expect(Exercises.change(0).equals(
                Map.of(25, 0L, 10, 0L, 5, 0L, 1, 0L)));
        expect(Exercises.change(1).equals(
                Map.of(25, 0L, 10, 0L, 5, 0L, 1, 1L)));
        expect(Exercises.change(8).equals(
                Map.of(25, 0L, 10, 0L, 5, 1L, 1, 3L)));
        expect(Exercises.change(42).equals(
                Map.of(25, 1L, 10, 1L, 5, 1L, 1, 2L)));
        expect(Exercises.change(99).equals(
                Map.of(25, 3L, 10, 2L, 5, 0L, 1, 4L)));
        expect(Exercises.change(144).equals(
                Map.of(25, 5L, 10, 1L, 5, 1L, 1, 4L)));
        expect(Exercises.change(250).equals(
                Map.of(25, 10L, 10, 0L, 5, 0L, 1, 0L)));
        expect(Exercises.change(100000000037L).equals(
                Map.of(25, 4000000001L, 10, 1L, 5, 0L, 1, 2L)));
        expect(Exercises.change(10000000000005L).equals(
                Map.of(25, 400000000000L, 10, 0L, 5, 1L, 1, 0L)));

        // Uncomment the following tests as you complete the exercises

        suite("firstThenLowerCase");
        expect(Exercises.firstThenLowerCase(
                List.of(), s -> !s.isEmpty()).isEmpty());
        expect(Exercises.firstThenLowerCase(
                List.of("", "A", "B"), s -> !s.isEmpty()).get().equals("a"));
        expect(Exercises.firstThenLowerCase(
                List.of("", "A", "ABC"), s -> s.length() > 3).isEmpty());
        expect(Exercises.firstThenLowerCase(
                List.of("ABC", "ABCD", "ABCDE"), s -> s.length() > 3).get().equals("abcd"));

        suite("say");
        expect(Exercises.say().phrase().equals(""));
        expect(Exercises.say("hi").phrase().equals("hi"));
        expect(Exercises.say("Oh").and("kay").phrase().equals("Oh kay"));
        expect(Exercises.say("hello").and("my").and("name").and("is").and("Colette").phrase()
                .equals("hello my name is Colette"));
        expect(Exercises.say("h i").phrase().equals("h i"));
        expect(Exercises.say("hi ").and("   there").phrase().equals("hi     there"));
        expect(Exercises.say("").and("").and("dog").and("").and("go").phrase()
                .equals("  dog  go"));
        expect(Exercises.say("🐤🦇").and("$🦊👏🏽").and("!").phrase().equals("🐤🦇 $🦊👏🏽 !"));
        expect(Exercises.say("😄🤗").and("💀👊🏾").phrase().equals("😄🤗 💀👊🏾"));
        // Ensure there is no sharing of partial states!
        var greet = Exercises.say("Hello").and("there");
        expect(greet.and("nice").and("person").phrase().equals("Hello there nice person"));
        expect(greet.and("Swift").phrase().equals("Hello there Swift"));

        suite("meaningfulLineCount");
        expectToThrow(
            () -> Exercises.meaningfulLineCount("no-such-file.txt"),
            FileNotFoundException.class,
            "No such file");
        expect(Exercises.meaningfulLineCount("../test-for-line-count.txt") == 5);

        suite("Quaternion");
        expectToThrow(() -> new Quaternion(Double.NaN, 0, 0, 0),
                IllegalArgumentException.class, "Coefficients cannot be NaN");
        expectToThrow(() -> new Quaternion(0, Double.NaN, 0, 0),
                IllegalArgumentException.class, "Coefficients cannot be NaN");
        expectToThrow(() -> new Quaternion(0, 0, Double.NaN, 0),
                IllegalArgumentException.class, "Coefficients cannot be NaN");
        expectToThrow(() -> new Quaternion(0, 0, 0, Double.NaN),
                IllegalArgumentException.class, "Coefficients cannot be NaN");

        var q = new Quaternion(3.5, 2.25, -100, -1.25);
        expect(q.a() == 3.5);
        expect(q.b() == 2.25);
        expect(q.c() == -100.0);
        expect(q.d() == -1.25);

        var q1 = new Quaternion(1, 3, 5, 2);
        var q2 = new Quaternion(-2, 2, 8, -1);
        var q3 = new Quaternion(-1, 5, 13, 1);
        var q4 = new Quaternion(-46, -25, 5, 9);

        expect(Quaternion.ZERO.coefficients().equals(List.of(0.0, 0.0, 0.0, 0.0)));
        expect(Quaternion.K.coefficients().equals(List.of(0.0, 0.0, 0.0, 1.0)));
        expect(q2.coefficients().equals(List.of(-2.0, 2.0, 8.0, -1.0)));

        expect(q1.plus(q2).equals(q3));
        expect(q1.times(q2).equals(q4));
        expect(q1.plus(Quaternion.ZERO).equals(q1));
        expect(q1.times(Quaternion.ZERO).equals(Quaternion.ZERO));

        expect(Quaternion.I.times(Quaternion.J).equals(Quaternion.K));
        expect(Quaternion.J.times(Quaternion.K).equals(Quaternion.I));
        expect(Quaternion.J.plus(Quaternion.I).equals(new Quaternion(0, 1, 1, 0)));

        expect(("" + Quaternion.ZERO).equals("0"));
        expect(("" + Quaternion.J).equals("j"));
        expect(("" + Quaternion.K.conjugate()).equals("-k"));
        expect(("" + Quaternion.J.conjugate().times(new Quaternion(2, 0, 0, 0))).equals("-2.0j"));
        expect(("" + Quaternion.J.plus(Quaternion.K)).equals("j+k"));
        expect(("" + new Quaternion(0, -1, 0, 2.25)).equals("-i+2.25k"));
        expect(("" + new Quaternion(-20, -1.75, 13, -2.25)).equals("-20.0-1.75i+13.0j-2.25k"));
        expect(("" + new Quaternion(-1, -2, 0, 0)).equals("-1.0-2.0i"));
        expect(("" + new Quaternion(1, 0, -2, 5)).equals("1.0-2.0j+5.0k"));

        suite("BinarySearchTree");
        BinarySearchTree t = new Empty();
        expect(t.size() == 0);
        expect(!t.contains("A"));
        expect(t.toString().equals("()"));
        t = t.insert("G");
        expect(t.size() == 1);
        expect(t.contains("G"));
        expect(!t.contains("A"));
        expect(t.toString().equals("(G)"));
        t = t.insert("B");
        expect(t.toString().equals("((B)G)"));
        t = t.insert("D");
        expect(t.toString().equals("((B(D))G)"));
        t = t.insert("H");
        expect(t.toString().equals("((B(D))G(H))"));
        t = t.insert("A");
        expect(t.toString().equals("(((A)B(D))G(H))"));
        t = t.insert("C");
        t = t.insert("J");
        expect(t.size() == 7);
        expect(t.contains("J"));
        expect(!t.contains("Z"));
        expect(t.toString().equals("(((A)B((C)D))G(H(J)))"));
        // Test immutability
        BinarySearchTree t2 = t;
        t2 = t2.insert("F");
        expect(t2.size() == 8);
        expect(t.size() == 7);

        System.out.printf("\n%d passed, %d failed\n", counts.get(true), counts.get(false));
    }
}
