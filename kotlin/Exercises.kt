import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import kotlin.math.abs

fun change(amount: Long): Map<Int, Long> {
    require(amount >= 0) { "Amount cannot be negative" }
    
    val counts = mutableMapOf<Int, Long>()
    var remaining = amount
    for (denomination in listOf(25, 10, 5, 1)) {
        counts[denomination] = remaining / denomination
        remaining %= denomination
    }
    return counts
}

// Write your first then lower case function here
fun firstThenLowerCase(stringList: List<String>, predicate: (it: String) -> Boolean): String? {
    return stringList.filter(predicate).getOrNull(0)?.lowercase()
}

// Write your say function here
data class PhraseConstructor(val phrase: String){
    fun and(excerpt: String): PhraseConstructor {
        return PhraseConstructor(phrase + " " + excerpt)
    }   
}

fun say(firstWord: String = ""): PhraseConstructor{
    return PhraseConstructor(firstWord)
}

// Write your meaningfulLineCount function here
// NOTE: The test for error throw is because I am working on Windows.
@Throws(IOException::class)
fun meaningfulLineCount(filePath: String): Long {
    var counter: Long = 0L
    BufferedReader(FileReader(filePath)).use {
        input ->
        var line: String? 
        while (input.readLine().also {line = it} != null) {
            val stripped: String = line!!.replace("\\s".toRegex(),"")
            if (stripped.length > 0 && stripped[0] != '#') {
                ++counter
            }
        }
    }
    return counter
}

// Write your Quaternion data class here
data class Quaternion(
    val a: Double, 
    val b: Double, 
    val c: Double, 
    val d: Double
){
    init {
        require(!a.isNaN() && !b.isNaN() && !c.isNaN() && !d.isNaN()){
            "Numbers can not be NaN"
        }
    }

    companion object {
        val ZERO = Quaternion(0.0, 0.0, 0.0, 0.0)
        val I = Quaternion(0.0, 1.0, 0.0, 0.0)
        val J = Quaternion(0.0, 0.0, 1.0, 0.0)
        val K = Quaternion(0.0, 0.0, 0.0, 1.0)
    }
    
    fun coefficients(): List<Double> {
        return listOf(this.a, this.b, this.c, this.d)
    }

    fun conjugate(): Quaternion {
        return Quaternion(this.a, -this.b, -this.c, -this.d)
    }

    operator fun plus(other: Quaternion): Quaternion {
        return Quaternion(
            this.a + other.a,
            this.b + other.b,
            this.c + other.c,
            this.d + other.d
        )
    }

    operator fun times(other: Quaternion): Quaternion {
        return Quaternion(
            this.a * other.a - this.b * other.b - this.c * other.c - this.d * other.d,
            this.a * other.b + this.b * other.a + this.c * other.d - this.d * other.c,
            this.a * other.c + this.c * other.a - this.b * other.d + this.d * other.b,
            this.a * other.d + this.d * other.a + this.b * other.c - this.c * other.b
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Quaternion) return false
        return this.a == other.a && this.b == other.b && this.c == other.c && this.d == other.d
    }

    override fun toString(): String {
        // Base case: 
        if (this == Quaternion.ZERO) {
            return "0"
        }

        // Creates a (almost) perfect Quaternion string!
        val constant: String = if (this.a != 0.0) "${this.a}" else ""
        val equation: String = constant + innerNominal(this.b, "i") + innerNominal(this.c, "j") + innerNominal(this.d, "k")

        // If the equation begins with a "+", remove it before returning
        return if (equation.firstOrNull() == '+') equation.substring(1) else equation
    }

    private fun innerNominal(num: Double, variable: String): String{
        val sign: String = if (num > 0.0) "+" else (if (num < 0.0) "-" else "")
        val nominal: String = if (num == 0.0) "" else (if (Math.abs(num) == 1.0) "$variable" else "${Math.abs(num)}$variable")
        return sign + nominal
    }
}



// Write your Binary Search Tree interface and implementing classes here
    // data class Node (
    //     val parent: Node
    //     var item: String

    //     operator fun compareTo(other: Node): Boolean = this.item.compareTo(other.item) < 0
    // ) {

    // }

    // sealed interface BinarySearchTree {

    //     val root: Node

    //     fun size(): Int 
    //     fun contains(item: String): Boolean
    //     fun insert(item: String): None
    //     override fun toString(): String

    // }

    // data class Empty(
    //     override val root = null
    // ) : BinarySearchTree
