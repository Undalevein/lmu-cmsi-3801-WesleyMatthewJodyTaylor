import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

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
@throwIOError
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
    //companion object {
    //    const val ZERO = Quaternion(0.0, 0.0, 0.0, 0.0)
    //    const val I = Quaternion(0.0, 1.0, 0.0, 0.0)
    //    const val J = Quaternion(0.0, 0.0, 1.0, 0.0)
    //    const val K = Quaternion(0.0, 0.0, 0.0, 1.0)
    //}
    
    fun coefficients(): List<Double> {
        return listOf(a, b, c, d)
    }

    
}

// Write your Binary Search Tree interface and implementing classes here
sealed interface BinarySearchTree {

    fun size(): Int 
    fun contains(item: String): Boolean
    fun insert(item: String): None
    override fun toString(): String

}

data class Empty(
    override val 
) : BinarySearchTree
