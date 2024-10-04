import java.io.IOException

////////////////////////////////////////////////////////////////////////////////
var passed = 0
var failed = 0

fun suite(name: String) {
    print("\nTesting $name")
}

fun expect(condition: Boolean) {
    if (condition) { passed += 1 } else { failed += 1 }
    print(if (condition) " ." else " F")
}

fun expectToThrow(c: () -> Any, exception: Class<out Exception>, message: String) {
    try {
        c()
        expect(false)
    } catch (e: Exception) {
        expect(exception.isInstance(e) && e.message!!.contains(message))
    }
}
////////////////////////////////////////////////////////////////////////////////

fun main() {
    suite("change")
    expectToThrow({ change(-50) }, IllegalArgumentException::class.java, "Amount cannot be negative")
    expect(change(0) == mapOf(25 to 0L, 10 to 0L, 5 to 0L, 1 to 0L))
    expect(change(1) == mapOf(25 to 0L, 10 to 0L, 5 to 0L, 1 to 1L))
    expect(change(8) == mapOf(25 to 0L, 10 to 0L, 5 to 1L, 1 to 3L))
    expect(change(42) == mapOf(25 to 1L, 10 to 1L, 5 to 1L, 1 to 2L))
    expect(change(99) == mapOf(25 to 3L, 10 to 2L, 5 to 0L, 1 to 4L))
    expect(change(144) == mapOf(25 to 5L, 10 to 1L, 5 to 1L, 1 to 4L))
    expect(change(250) == mapOf(25 to 10L, 10 to 0L, 5 to 0L, 1 to 0L))
    expect(change(100000000037) == mapOf(25 to 4000000001L, 10 to 1L, 5 to 0L, 1 to 2L))
    expect(change(10000000000005) == mapOf(25 to 400000000000L, 10 to 0L, 5 to 1L, 1 to 0L))

    // Uncomment the following tests as you complete the exercises

    suite("firstThenLowerCase");
    expect(firstThenLowerCase(listOf(), { !it.isEmpty() }) == null)
    expect(firstThenLowerCase(listOf("", "A", "B"), { !it.isEmpty() }) == "a")
    expect(firstThenLowerCase(listOf("", "A", "ABC"), { it.length > 3 }) == null)
    expect(firstThenLowerCase(listOf("ABC", "ABCD", "ABCDE"), { it.length > 3 }) == "abcd")

    suite("say")
    expect(say().phrase == "")
    expect(say("hi").phrase == "hi")
    expect(say("Oh").and("kay").phrase == "Oh kay")
    expect(say("hello").and("my").and("name").and("is").and("Colette").phrase == "hello my name is Colette")
    expect(say("h i").phrase == "h i")
    expect(say("hi ").and("   there").phrase == "hi     there")
    expect(say("").and("").and("dog").and("").and("go").phrase == "  dog  go")
    expect(say("🐤🦇").and("$🦊👏🏽").and("!").phrase == "🐤🦇 $🦊👏🏽 !")
    expect(say("😄🤗").and("💀👊🏾").phrase == "😄🤗 💀👊🏾")
    // Ensure there is no sharing of partial states!
    val greet = say("Hello").and("there")
    expect(greet.and("nice").and("person").phrase == "Hello there nice person")
    expect(greet.and("Swift").phrase == "Hello there Swift")

    suite("meaningfulLineCount")
    expectToThrow({ meaningfulLineCount("no-such-file.txt") }, IOException::class.java, "file")
    expect(meaningfulLineCount("../test-for-line-count.txt") == 5L)

    suite("Quaternion")
    val q = Quaternion(3.5, 2.25, -100.0, -1.25)
    expect(q.a == 3.5)
    expect(q.b == 2.25)
    expect(q.c == -100.0)
    expect(q.d == -1.25)

    val q1 = Quaternion(1.0, 3.0, 5.0, 2.0);
    val q2 = Quaternion(-2.0, 2.0, 8.0, -1.0);
    val q3 = Quaternion(-1.0, 5.0, 13.0, 1.0);
    val q4 = Quaternion(-46.0, -25.0, 5.0, 9.0);

    expect(Quaternion.ZERO.coefficients() == listOf(0.0, 0.0, 0.0, 0.0))
    expect(Quaternion.K.coefficients() == listOf(0.0, 0.0, 0.0, 1.0))
    expect(q2.coefficients() == listOf(-2.0, 2.0, 8.0, -1.0))

    expect(q1 + q2 == q3)
    expect(q1 * q2 == q4)
    expect(q1 + Quaternion.ZERO == q1)
    expect(q1 * Quaternion.ZERO == Quaternion.ZERO)
    expect(Quaternion.I * Quaternion.J == Quaternion.K)
    expect(Quaternion.J * Quaternion.K == Quaternion.I)
    expect(Quaternion.J + Quaternion.I == Quaternion(0.0, 1.0, 1.0, 0.0))

    expect("${Quaternion.ZERO}" == "0")
    expect("${Quaternion.J}" == "j")
    expect("${Quaternion.K.conjugate()}" == "-k")
    expect("${Quaternion.J.conjugate() * Quaternion(2.0, 0.0, 0.0, 0.0)}" == "-2.0j")
    expect("${Quaternion.J + Quaternion.K}" == "j+k")
    expect("${Quaternion(0.0, -1.0, 0.0, 2.25)}" == "-i+2.25k")
    expect("${Quaternion(-20.0, -1.75, 13.0, -2.25)}" == "-20.0-1.75i+13.0j-2.25k")
    expect("${Quaternion(-1.0, -2.0, 0.0, 0.0)}" == "-1.0-2.0i")
    expect("${Quaternion(1.0, 0.0, -2.0, 5.0)}" == "1.0-2.0j+5.0k")

    suite("BinarySearchTree")
    var t: BinarySearchTree = BinarySearchTree.Empty
    expect(t.size() == 0)
    expect(!t.contains("A"))
    expect(t.toString() == "()")
    t = t.insert("G")
    expect(t.size() == 1)
    expect(t.contains("G"))
    expect(!t.contains("A"))
    expect(t.toString() == "(G)")
    t = t.insert("B")
    expect(t.toString() == "((B)G)")
    t = t.insert("D")
    expect(t.toString() == "((B(D))G)")
    t = t.insert("H")
    expect(t.toString() == "((B(D))G(H))")
    t = t.insert("A")
    expect(t.toString() == "(((A)B(D))G(H))")
    t = t.insert("C")
    t = t.insert("J")
    expect(t.size() == 7)
    expect(t.contains("J"))
    expect(!t.contains("Z"))
    expect(t.toString() == "(((A)B((C)D))G(H(J)))")
    // // Test immutability
    var t2: BinarySearchTree = t;
    t2 = t2.insert("F");
    expect(t2.size() == 8);
    expect(t.size() == 7);

    println("\n$passed passed, $failed failed")
}
