/**
 * Author: Wesley Ng, Jody, and Matthew
 * Collaborators: None
 * 
 */
import { open } from "node:fs/promises"

export function change(amount) {
  /**
   *  Returns the correct amount of coins equilvalent to the
   *  amount needed to give back. The amount of coins given back
   *  must be efficient, so if you need to give back 25 cents, giving
   *  back twenty-five 1 cent coins is the wrong answer.
   */
  if (!Number.isInteger(amount)) {
    throw new TypeError("Amount must be an integer")
  }
  if (amount < 0) {
    throw new RangeError("Amount cannot be negative")
  }
  let [counts, remaining] = [{}, amount]
  for (const denomination of [25, 10, 5, 1]) {
    counts[denomination] = Math.floor(remaining / denomination)
    remaining %= denomination
  }
  return counts
}

// Write your first then lower case function here
export function firstThenLowerCase(wordList, wordPredicate) {
  /**
   *  Finds the first word in wordList that fits the wordPredicate,
   *  then lowercases that word.
   */
  return wordList?.filter(word => wordPredicate(word))[0]?.toLowerCase();
}

// Write your powers generator here
export function* powersGenerator(powers) {
  /**
   *  Generates every exponent from b^0 to b^n, where b^n <= upTo 
   *  and b^(n+1) > upTo won't be yielded and instead ending the generator.
   */
  const { ofBase, upTo } = powers;
  let exponent = 1;
  while (upTo >= exponent) {
    yield exponent;
    exponent *= ofBase;
  }
}

// Write your say function here
export function say(word = null) {
  /**
   *  Returns a string of words in the parameter; however, it 
   *  supports "chainable" functions.
   *  i.e. say("hi")("there")("Mr.")("Johnson") will output 
   *  "hi there Mr. Johnson".
   */
  let phrase = "";
  function nextPhrase(currentWord = null) {
    if (currentWord === null) {
      return phrase.substring(0, phrase.length - 1);
    }
    phrase += currentWord + " ";
    return nextPhrase;
  }
  return nextPhrase(word);
}

// Write your line count function here
export async function meaningfulLineCount(filePath) {
  /**
   *  Reads the lines of a file and outputs how many lines
   *  in that file contains a non-whitespace character BUT 
   *  does not start with #. Whitespace is ignored in the file.
   */
  const file = await open(filePath, "r");
  let validLines = 0;
  if (file === null) {
    throw new Error("File " + filePath + " cannot be found.");
  }
  for await (const line of file.readLines()) {
    let editedLine = line.trim();
    if (editedLine.length > 0 && editedLine.charAt(0) !== '#') {
      // Counts up if line does not start with # and 
      // has non-whitespace character.
      validLines++;
    }
  }
  file.close()
  return validLines;
}

// Write your Quaternion class here
export class Quaternion {
  constructor(a, b, c, d) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
    Object.freeze(this);
  }

  get coefficients() {
    return [this.a, this.b, this.c, this.d];
  }

  get conjugate() {
    return new Quaternion(this.a, -this.b, -this.c, -this.d);
  }

  plus(other) {
    if (other instanceof Quaternion) {
      return new Quaternion(
        this.a + other.a,
        this.b + other.b,
        this.c + other.c,
        this.d + other.d
      );
    }
  }

  times(other) {
    if (other instanceof Quaternion) {
      return new Quaternion(
        this.a * other.a - this.b * other.b - this.c * other.c - this.d * other.d,
        this.a * other.b + this.b * other.a + this.c * other.d - this.d * other.c,
        this.a * other.c + this.c * other.a - this.b * other.d + this.d * other.b,
        this.a * other.d + this.d * other.a + this.b * other.c - this.c * other.b
      )
    }
  }

  toString() {
    let equation = "";
    const variables = ["", "i", "j", "k"];

    for (let i = 0; i < 4; i++) {
      if (this.coefficients[i] !== 0) {
        // Adds the sign to the nominal.
        if (this.coefficients[i] > 0 && equation.length !== 0) {
          equation += "+";
        } else if (this.coefficients[i] < 0) {
          equation += "-";
        }
        if (Math.abs(this.coefficients[i]) > 1 || i === 0) {
          // Adds coefficient if the coefficient is greater than 1 or is
          // a constant.
          equation += Math.abs(this.coefficients[i]);
        }
        // Adds the variable as long as the coefficient is not 0.
        equation += variables[i];
      }
    }

    // If the equation is 0, then return "0".
    return (equation) ? equation : "0";
  }
}