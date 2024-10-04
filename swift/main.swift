import Foundation

////////////////////////////////////////////////////////////////////////////////
var (passed, failed) = (0, 0)

func suite(_ title: String) {
    print("\nTesting \(title)", terminator: "")
}

func expect(_ condition: Bool) {
    if condition { passed += 1 } else { failed += 1 }
    print(condition ? " ." : " F", terminator: "")
}

func expectSuccess<S:Equatable, E>(_ r: Result<S, E>, _ expected: S) {
    switch r {
        case .success(let s): expect(s == expected)
        case .failure(_): expect(false)
    }
}

func expectFailure<S, E>(_ r: Result<S, E>) {
    switch r {
        case .success(_): expect(false)
        case .failure(_): expect(true)
    }
}
////////////////////////////////////////////////////////////////////////////////

suite("change")
expectFailure(change(-50))
expectSuccess(change(0), [25: 0, 10: 0, 5: 0, 1: 0])
expectSuccess(change(1), [25: 0, 10: 0, 5: 0, 1: 1])
expectSuccess(change(8), [25: 0, 10: 0, 5: 1, 1: 3])
expectSuccess(change(42), [25: 1, 10: 1, 5: 1, 1: 2])
expectSuccess(change(99), [25: 3, 10: 2, 5: 0, 1: 4])
expectSuccess(change(144), [25: 5, 10: 1, 5: 1, 1: 4])
expectSuccess(change(250), [25: 10, 10: 0, 5: 0, 1: 0])
expectSuccess(change(100000000037), [25: 4000000001, 10: 1, 5: 0, 1: 2])
expectSuccess(change(10000000000005), [25: 400000000000, 10: 0, 5: 1, 1: 0])

// Uncomment the following tests as you complete the exercises

suite("first_then_lower_case")
expect(firstThenLowerCase(of: [], satisfying: { $0 != "" }) == nil)
expect(firstThenLowerCase(of: ["", "A", "B"], satisfying: { $0 != "" }) == "a")
expect(firstThenLowerCase(of: ["", "A", "ABC"], satisfying: { $0.count > 3 }) == nil)
expect(firstThenLowerCase(of: ["ABC", "ABCD", "ABCDE"], satisfying: { $0.count > 3 }) == "abcd")

suite("say")
expect(say().phrase == "")
expect(say("hi").phrase == "hi")
expect(say("Oh").and("kay").phrase == "Oh kay")
expect(say("hello").and("my").and("name").and("is").and("Colette").phrase ==
    "hello my name is Colette")
expect(say("h i").phrase == "h i")
expect(say("hi ").and("   there").phrase == "hi     there")
expect(say("").and("").and("dog").and("").and("go").phrase == "  dog  go")
expect(say("🐤🦇").and("$🦊👏🏽").and("!").phrase == "🐤🦇 $🦊👏🏽 !")
expect(say("😄🤗").and("💀👊🏾").phrase == "😄🤗 💀👊🏾")
// Ensure there is no sharing of partial states!
var greet = say("Hello").and("there")
expect(greet.and("nice").and("person").phrase == "Hello there nice person")
expect(greet.and("Swift").phrase == "Hello there Swift")

suite("meaningfulLineCount");
await expectFailure(meaningfulLineCount("NoSuchFile.txt"))
await expectSuccess(meaningfulLineCount("../test-for-line-count.txt"), 5)

suite("Quaternion")
let q = Quaternion(a: 3.5, b: 2.25, c: -100, d: -1.25)
expect(q.a == 3.5)
expect(q.b == 2.25)
expect(q.c == -100.0)
expect(q.d == -1.25)
let q1 = Quaternion(a: 1, b: 3, c: 5, d: 2)
let q2 = Quaternion(a: -2, b: 2, c: 8, d: -1)
let q3 = Quaternion(a: -1, b: 5, c: 13, d: 1)
let q4 = Quaternion(a: -46, b: -25, c: 5, d: 9)
expect(Quaternion.ZERO.coefficients == [0, 0, 0, 0])
expect(Quaternion.K.coefficients == [0, 0, 0, 1])
expect(q2.coefficients == [-2, 2, 8, -1])
expect(q1 + q2 == q3)
expect(q1 * q2 == q4)
expect(q1 + Quaternion.ZERO == q1)
expect(q1 * Quaternion.ZERO == Quaternion.ZERO)
expect(Quaternion.I * Quaternion.J == Quaternion.K)
expect(Quaternion.J * Quaternion.K == Quaternion.I)
expect(Quaternion.I * Quaternion.K == Quaternion.J.conjugate)
expect(Quaternion.J + Quaternion.I == Quaternion(b: 1, c: 1))
expect("\(Quaternion.ZERO)" == "0")
expect("\(Quaternion.J)" == "j")
expect("\(Quaternion.K.conjugate)" == "-k")
expect("\(Quaternion.J.conjugate * Quaternion(a: 2))" == "-2.0j")
expect("\(Quaternion.J + Quaternion.K)" == "j+k")
expect("\(Quaternion(a: 0, b: -1, c:  0, d: 2.25))" == "-i+2.25k")
expect("\(Quaternion(a: -20, b: -1.75, c: 13, d: -2.25))" == "-20.0-1.75i+13.0j-2.25k")
expect("\(Quaternion(a: -1, b: -2, c: 0, d: 0))" == "-1.0-2.0i")
expect("\(Quaternion(a: 1, b: 0, c: -2, d: 5))" == "1.0-2.0j+5.0k")

suite("BinarySearchTree");
var t: BinarySearchTree = .empty
expect(t.size == 0);
expect(!t.contains("A"));
expect("\(t)" == "()")
t = t.insert("G");
expect(t.size == 1);
expect(t.contains("G"));
expect(!t.contains("A"));
expect("\(t)" == "(G)")
t = t.insert("B");
expect("\(t)" == "((B)G)")
t = t.insert("D");
expect("\(t)" == "((B(D))G)")
t = t.insert("H");
expect("\(t)" == "((B(D))G(H))")
t = t.insert("A");
expect("\(t)" == "(((A)B(D))G(H))")
t = t.insert("C");
t = t.insert("J");
expect(t.size == 7);
expect(t.contains("J"));
expect(!t.contains("Z"));
expect("\(t)" == "(((A)B((C)D))G(H(J)))")
// Test immutability
var t2: BinarySearchTree = t;
t2 = t2.insert("F");
expect(t2.size == 8);
expect(t.size == 7);

print("\n\(passed) passed, \(failed) failed")
