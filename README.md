# Programming Languages I
Students: Wesley Ng, Matthew De Jesus, Jody Orlando Jasim, Taylor Musso

This repo is a collection of submitted homework from the group. Under the README.md is a description of every homework assignment.

### Homework Descriptions
+ **Homework 1 (Scripting)**: Lua, Python, JavaScript
    + First then Lower Case: A function that is given a sequence of strings *a* and a predicate *p* and returns the lowercase version of the first element of *a* that satisfies *p*, returning nil, undefined, or None for each respective language if none can be found.
    + Powers Generator: A generator function or coroutine that yields successive powers of base *b* starting at 0, stopping before the result exceeds the given limit.
    + Say: A "chainable" function that accepts one string per call and when it is called without arguments, it returns all of the previously passed words in order separated by a space.
    + Meaningful Line Count: A function that takes in a filename and returns the number of text lines that are not empty, made up entirely of whitespace, or whose first non whitespace character is #.
    + Quaternion: A class or table that functions as a Quaternion and is able to construct, add, multiply, obtain the list of coefficients, compute the conjugate, support value-based equality, and produce the string representation.
+ **Homework 2 (Enterprise)** Java, Swift, and Kotlin
    + First then Lower Case: Similar to the function in Homework 1  where the function is given a sequence of strings *a* and a prediccate *p* and must return an optional string where if there is such word that follows *p*, return it lowercased; otherwise return a null. For Java, it uses streams. Both Swift and Kotlin will use the ?. operator and Swift itself will use the argument labels "of" for the array and "satisfying" for the predicate.
    + Say: Another "chainable" function, however instead of returning a function we are now returning an object with a method (and) and a read-only property (phrase), which returns a sentence with spaces in between each phrase.
    + Meaningful Line Count: Counts all of the lines that are not all whitespace, has non-whitespace characters, and the first non-whitespace character is not "#". Java and Kotlin will throw an IOException while Swift with return a suitable Result object.
    + Quaternion: An immutable object and all internal details hidden, though each programming language will create that object differently. Java must use a record, Kotlin uses data class, and Swift uses struct. They must also support addition, multiplication, getting the coefficients, getting the conjugate, equality, and a string representation (some languages must use operator overloading if so). In addition, all languages must include static constant members ZERO, I, J, and K.
    + Binary Search Tree: A generic, immutable, and persistent binary search tree that supports element count, insertion, and lookup (no deletion). They must also support string formmating. For Java and Kotlin, they must use sealed class or interface. Java will need to have top-level classes while Kotlin must have the empty and node variant in the data class. Swift, on the other hand, must use an indirect enum and must make sure that "size" is a computered property.