/**
 * Author: Wesley, Jody, Matthew, and Taylor
 * Collaborators: Ray Toal
 */

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

fun firstThenLowerCase(words: List<String>, predicate: (it: String) -> Boolean): String? {
    /**
     *  Finds the first word in the list that follows the given predicate,
     *  then returns that word lowercased.
     */
    return words.filter(predicate).getOrNull(0)?.lowercase()
}

data class PhraseConstructor(val phrase: String) {
    fun and(excerpt: String): PhraseConstructor {
        return PhraseConstructor(phrase + " " + excerpt)
    }   
}

fun say(phrase: String = "") : PhraseConstructor {
    /**
     *  A compounding method starting with say functions, then multiple
     *  and functions and ends with calling phrase. Creates a sentence where
     *  each phrase is added to the sentence with spaces in between.
     */
    return PhraseConstructor(phrase)
}

@Throws(IOException::class)
fun meaningfulLineCount(filePath: String): Long {
    /**
     *  Counts all of the lines in a given file where it contains
     *  characters (not whitespaces) and does not begin with a hashtag
     *  symbol (#).
     */
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

data class Quaternion(
    val a: Double, 
    val b: Double, 
    val c: Double, 
    val d: Double
) {
    init {
        require(!a.isNaN() && !b.isNaN() && !c.isNaN() && !d.isNaN()){
            "Numbers can not be NaN"
        }
    }
    
    fun coefficients() : List<Double> {
        return listOf(a, b, c, d)
    }

    fun conjugate() : Quaternion {
        return Quaternion(a, -b, -c, -d)
    }

    operator fun plus(other: Quaternion) : Quaternion {
        return Quaternion(
            a + other.a,
            b + other.b,
            c + other.c,
            d + other.d
        )
    }

    operator fun times(other: Quaternion) : Quaternion {
        return Quaternion(
            a * other.a - b * other.b - c * other.c - d * other.d,
            a * other.b + b * other.a + c * other.d - d * other.c,
            a * other.c + c * other.a - b * other.d + d * other.b,
            a * other.d + d * other.a + b * other.c - c * other.b
        )
    }

    override fun equals(other: Any?) : Boolean {
        if (this === other) return true
        if (other !is Quaternion) return false
        return a == other.a && b == other.b && c == other.c && d == other.d
    }

    override fun toString() : String {
        // Base case: 
        if (this == Quaternion.ZERO) {
            return "0"
        }

        // Creates a (almost) perfect Quaternion string!
        val constant: String = if (a != 0.0) "${a}" else ""
        val equation: String = constant + innerNominal(b, "i") + innerNominal(c, "j") + innerNominal(d, "k")

        // If the equation begins with a "+", remove it before returning
        return if (equation.firstOrNull() == '+') equation.substring(1) else equation
    }

    private fun innerNominal(num: Double, variable: String) : String{
        val sign: String = if (num > 0.0) "+" else (if (num < 0.0) "-" else "")
        val nominal: String = if (num == 0.0) "" else (if (Math.abs(num) == 1.0) "$variable" else "${Math.abs(num)}$variable")
        return sign + nominal
    }
    
    companion object {
        val ZERO = Quaternion(0.0, 0.0, 0.0, 0.0)
        val I = Quaternion(0.0, 1.0, 0.0, 0.0)
        val J = Quaternion(0.0, 0.0, 1.0, 0.0)
        val K = Quaternion(0.0, 0.0, 0.0, 1.0)
    }
}

sealed interface BinarySearchTree {
    fun size(): Int
    fun contains(target: String): Boolean
    fun insert(itemValue: String) : BinarySearchTree
    override fun toString(): String

    data class TreeNode(
        val itemValue: String, 
        val left: BinarySearchTree = BinarySearchTree.Empty, 
        val right: BinarySearchTree = BinarySearchTree.Empty
    ) : BinarySearchTree {

        override fun size() : Int {
            return left.size() + 1 + right.size()  
        }

        override fun contains(target: String) : Boolean {
            return itemValue == target || left.contains(target) || right.contains(target)
        }

        override fun insert(newitemValue: String) : BinarySearchTree { 
            val comparison = newitemValue.compareTo(itemValue)
            if (comparison < 0) {
                return TreeNode(this.itemValue, left.insert(newitemValue), right)
            } else if (comparison > 0) {
                return TreeNode(this.itemValue, left, right.insert(newitemValue))
            } else {
                return TreeNode(this.itemValue, left, right)
            }
        }

        override fun toString(): String {
            val leftString = if (left != BinarySearchTree.Empty) "${left}" else "" 
            val rightString = if (right != BinarySearchTree.Empty) "${right}" else "" 
            return "(${leftString}${itemValue}${rightString})"
        }
    }

    object Empty : BinarySearchTree {
        override fun size() : Int {
            return 0
        }
        
        override fun contains(target: String) : Boolean {
            return false
        }

        override fun insert(itemValue: String) : BinarySearchTree {
            val root: BinarySearchTree = TreeNode(itemValue)
            return root
        }

        override fun toString() : String {
            return "()"
        }
    }

}