from dataclasses import dataclass
from collections.abc import Callable
from typing import Optional, Iterator
import os


def change(amount: int) -> dict[int, int]:
    if not isinstance(amount, int):
        raise TypeError('Amount must be an integer')
    if amount < 0:
        raise ValueError('Amount cannot be negative')
    counts, remaining = {}, amount
    for denomination in (25, 10, 5, 1):
        counts[denomination], remaining = divmod(remaining, denomination)
    return counts


# Write your first then lower case function here
def first_then_lower_case(a: list[str], p: Callable[[str], bool], /) -> Optional[str]:
    for string in a:
        if p(string):
            return string.lower()
    return None


# Write your powers generator here
def powers_generator(*, base: int, limit: int) -> Iterator[int]:
    exponent = 0
    while True:
        limit_test = base**exponent
        if limit_test > limit:
            break
        yield limit_test
        exponent += 1


# Write your say function here
def say(word=None):
    created_string = []

    def next_word(new_word=None):
        if new_word == "":  # empty string - add "" to end of list
            created_string.append((""))
            return next_word

        if new_word:  # non-empty string - add new_word to end of created string
            created_string.append(new_word)
            return next_word

        else:  # say() - return joined list
            return " ".join(created_string)

    return next_word(word)


# Write your line count function here
def meaningful_line_count(file_path: str, /) -> int:
    if not os.path.exists(file_path):
        raise FileNotFoundError("No such file")

    valid_lines = 0
    with open(file_path, "r", encoding="utf-8") as opened_file:
        # without encoding -> UnicodeDecodeError: 'charmap' codec can't decode byte 0x81 in position 16: character maps to <undefined>
        # solution found at: https://stackoverflow.com/questions/9233027/unicodedecodeerror-charmap-codec-cant-decode-byte-x-in-position-y-character
        for line in opened_file:
            stripped_line = line.strip()
            # check if not empty and not starting with "#"
            if stripped_line and not stripped_line.startswith("#"):
                valid_lines += 1
        opened_file.close()

    return valid_lines


# Write your Quaternion class here
@dataclass(frozen=True)
class Quaternion:
    a: float
    b: float
    c: float
    d: float

    @property
    def coefficients(self):
        return (self.a, self.b, self.c, self.d)

    @property
    def conjugate(self):
        return Quaternion(self.a, -self.b, -self.c, -self.d)

    def __add__(self, other):
        return Quaternion(
            self.a + other.a,
            self.b + other.b,
            self.c + other.c,
            self.d + other.d
        )

    def __mul__(self, other):
        a1, b1, c1, d1 = self.a, self.b, self.c, self.d
        a2, b2, c2, d2 = other.a, other.b, other.c, other.d
        return Quaternion(
            a1 * a2 - b1 * b2 - c1 * c2 - d1 * d2,
            a1 * b2 + b1 * a2 + c1 * d2 - d1 * c2,
            a1 * c2 - b1 * d2 + c1 * a2 + d1 * b2,
            a1 * d2 + b1 * c2 - c1 * b2 + d1 * a2
        )

    def __eq__(self, other):
        return (self.a, self.b, self.c, self.d) == (other.a, other.b, other.c, other.d)

    def __repr__(self):
        parts = []
        if self.a != 0:
            parts.append(f"{self.a}")
        for coeff, symbol in [(self.b, 'i'), (self.c, 'j'), (self.d, 'k')]:
            if coeff != 0:
                if abs(coeff) == 1:
                    part = f"{'-' if coeff < 0 else ''}{symbol}"
                else:
                    part = f"{coeff}{symbol}"
                parts.append(part)
        if not parts:
            return '0'
        result = parts[0]
        for part in parts[1:]:
            if part.startswith('-'):
                result += part
            else:
                result += '+' + part
        return result
