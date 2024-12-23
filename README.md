# Programming Languages I

Students: Wesley Ng, Matthew De Jesus, Jody Orlando Jasim, Taylor Musso

This repo is a collection of submitted homework from the group. Under the README.md is a description of every homework assignment.

### Homework Descriptions

- **Homework 1 (Scripting)**: Lua, Python, JavaScript
  - First then Lower Case: A function that is given a sequence of strings _a_ and a predicate _p_ and returns the lowercase version of the first element of _a_ that satisfies _p_, returning nil, undefined, or None for each respective language if none can be found.
  - Powers Generator: A generator function or coroutine that yields successive powers of base _b_ starting at 0, stopping before the result exceeds the given limit.
  - Say: A "chainable" function that accepts one string per call and when it is called without arguments, it returns all of the previously passed words in order separated by a space.
  - Meaningful Line Count: A function that takes in a filename and returns the number of text lines that are not empty, made up entirely of whitespace, or whose first non whitespace character is #.
  - Quaternion: A class or table that functions as a Quaternion and is able to construct, add, multiply, obtain the list of coefficients, compute the conjugate, support value-based equality, and produce the string representation.
- **Homework 2 (Enterprise)** Java, Swift, and Kotlin
  - First then Lower Case: Similar to the function in Homework 1 where the function is given a sequence of strings _a_ and a prediccate _p_ and must return an optional string where if there is such word that follows _p_, return it lowercased; otherwise return a null. For Java, it uses streams. Both Swift and Kotlin will use the ?. operator and Swift itself will use the argument labels "of" for the array and "satisfying" for the predicate.
  - Say: Another "chainable" function, however instead of returning a function we are now returning an object with a method (and) and a read-only property (phrase), which returns a sentence with spaces in between each phrase.
  - Meaningful Line Count: Counts all of the lines that are not all whitespace, has non-whitespace characters, and the first non-whitespace character is not "#". Java and Kotlin will throw an IOException while Swift with return a suitable Result object.
  - Quaternion: An immutable object and all internal details hidden, though each programming language will create that object differently. Java must use a record, Kotlin uses data class, and Swift uses struct. They must also support addition, multiplication, getting the coefficients, getting the conjugate, equality, and a string representation (some languages must use operator overloading if so). In addition, all languages must include static constant members ZERO, I, J, and K.
  - Binary Search Tree: A generic, immutable, and persistent binary search tree that supports element count, insertion, and lookup (no deletion). They must also support string formmating. For Java and Kotlin, they must use sealed class or interface. Java will need to have top-level classes while Kotlin must have the empty and node variant in the data class. Swift, on the other hand, must use an indirect enum and must make sure that "size" is a computered property.
- **Homework 3 (Theory)**: Typescript, Haskell, OCaml
  - First then Apply: Functions that when given a list or array, predicate, and function, return the application of the function to the first element of the array/list that satisfies the predicate. Returns an optional.
  - Powers Generator: Implementations of the infinite sequence of powers of base _b_ starting at 0.
  - Meaningful Line Count: Functions that return the number of text lines from a text file that are neither empty, made up entirely of whitespace, or whose first non-whitespace character is #.
  - Shapes: Data types for three-dimensional shapes that can be either rectangular boxes or spheres, that are able to compute both the surface area and the volume, and also have a to-string operation.
  - Binary Search Tree: A generic, persistent, binary search tree implementation that supports insertion, lookup, count, inorder traversal, and for TypeScript and Haskell, the string description of the tree.
- **Homework 4 (Systems)** C, C++, Rust
  - Implement a user defined data type for the Stack data structure. C is a stack of strings, C++ and Rust are generic. C and C++ builds the stack with manually-managed array storage, resized by doubling capacity if adding an element beyond the current capacity, and shrunk when popping to a size below a quarter of the current capacity unless it is at 16 (the minimum). Rust wraps a Rust vector, which handles its own resizing.
  - For some additional reqirements, C and C++ have additional features as they are prone to memory leaks and security vulnerabilities. In both of these programs, it is up to the programmer to patch up these vulnerabilities to make these programs secure, however it can still be insecure as by putting human programmers to be responsible for protecting their code would inevitably lead to slip-ups.
- **Homewokr 5 (Concurrency)** Go
  - Implemented a restaurant simulation to demonstrate the use of Concurrency. Go is a language that has a lot of concurrency features that allows us to run multiple functions simultaneously. Go uses goroutines, which are basically lightweighted threads. This feature is used in the restaurant simulation, where we have 10 customers and 3 cooks. Each customer will order simultaneously to the waiter, who will then give orders to the cooks. The waiter can only hold a certain amount of customer orders at a time, but the customers can get impatient and leave. The goal is to get all customers their 5 meals before closing the restaurant.
