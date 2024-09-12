# Programming Languages I
Students: Wesley Ng, Matthew de Jesus, Jody Orlando Jasim, Taylor Musso

This repo is a collection of submitted homework from the group. Under the README.md is a description of every homework assignment.

### Homework Descriptions
+ **Homework 1 (Scripting)**: Lua, Python, JavaScript
    + First then Lower Case: A function that is given a sequence of strings *a* and a predicate *p* and returns the lowercase version of the first element of *a* that satisfies *p*, returning nil, undefined, or None for each respective language if none can be found.
    + Powers Generator: A generator function or coroutine that yields successive powers of base *b* starting at 0, stopping before the result exceeds the given limit.
    + Say: A "chainable" function that accepts one string per call and when it is called without arguments, it returns all of the previously passed words in order separated by a space.
    + Meaningful Line Count: A function that takes in a filename and returns the number of text lines that are not epty, made up entirely of whitespace, or whose first non whitespace character is #.
    + Quaternion: A class or table that functions as a Quaternion and is able to construct, add, multiply, obtain the list of coefficients, compute the conjugate, support value-based equality, and produce the string representation.

## Instructions

Fork this repo for your homework submissions. Make sure your fork has a nice, descriptive name. Leaving the name as “lmu-cmsi-3801-template” is misleading, and keeping it indicates you are not taking sufficient pride in your work. After forking, **please replace the contents of this readme** file with information about your submissions, including the name(s) of the students, and a description of each assignment (as they are turned in).

Don’t bother with notes to the graders. Such notes go into your BrightSpace submissions, not your GitHub repository.

Your homework submissions will consist of programs in the following languages. To keep things simple, there is a separate folder for each language.

- **Homework 1 (Scripting)**: Lua, Python, JavaScript
- **Homework 2 (Enterprise)**: Java, Kotlin, Swift
- **Homework 3 (Theory)**: TypeScript, OCaml, Haskell
- **Homework 4 (Systems)**: C, C++, Rust
- **Homework 5 (Concurrency)**: Go

At each homework deadline, the graders will clone your repo and run the tests. I will be inspecting the source code, grading your work on style, clarity, and appropriate use of language idioms. Do not throw away points in these areas: **use code formatters and linters**. Please consider it a moral obligation to use these tools. Not doing so is a violation of professional ethics. _You must respect the naming, capitalization, formatting, spacing, and indentation conventions of each language_.

## The Test Suites

The test files are included in the repo already. They are available for YOU! They will help you not only learn the languages and concepts covered in this course, but to help you with professional practice. You should get accustomed to writing code to make tests pass. As you grow in your profession, you will get used to writing your tests early.

The test suites are run like so (assuming you have a Unix-like shell, modify as necessary if you have Windows):

### Lua

```
lua exercises_test.lua
```

### Python

```
python3 exercises_test.py
```

### JavaScript

```
npm test
```

### Java

```
javac *.java && java ExercisesTest
```

### Kotlin

```
kotlinc *.kt -include-runtime -d test.jar && java -jar test.jar
```

### Swift

```
swiftc -o main exercises.swift main.swift && ./main
```

### TypeScript

```
npm test
```

### OCaml

```
ocamlc exercises.ml exercises_test.ml && ./a.out
```

### Haskell

```
ghc ExercisesTest.hs && ./ExercisesTest
```

### C

```
gcc string_stack.c string_stack_test.c && ./a.out
```

### C++

```
g++ -std=c++20 stack_test.cpp && ./a.out
```

### Rust

```
cargo test
```

### Go

```
go run restaurant.go
```

## Grading Notes

Your grade is a reflection not only of your ability to write code to pass existing tests, but also of your ability to construct software in a professional setting. Therefore, the following will contribute rather heavily to your score:

- **Following all submission instructions**! Pay attention to every requirement such as replacing the contents of this readme file and including the names of all participants of any group work.
- **Keeping a pristine GitHub repository**. Do not push any file that does not belong (including but not limited to that silly `DS_Store`). Make sure all generated executables, intermediate files, third-party libraries, etc. are not committed. Your repo contents should be limited to your solution files, tests, configuration files, and `.gitignore` files.
- **Adherence to naming and formatting conventions for the language you are writing in**. Inconsistent indentation, for example, has no place in professional or student software. Neither does end-of-line whitespace, huge chunks of contiguous blank lines, and other types of messy coding practices that show friends, family, colleagues, and potential employers that you don’t care about your work.
- (As always) The **readability and maintainability** of your code.
