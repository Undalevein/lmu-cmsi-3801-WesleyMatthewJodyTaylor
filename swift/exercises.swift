/**
 * Author: Wesley, Jody, Matthew, and Taylor
 * Collaborators: Ray Toal
 */

import Foundation

struct NegativeAmountError: Error {}
struct NoSuchFileError: Error {}

func change(_ amount: Int) -> Result<[Int:Int], NegativeAmountError> {
    if amount < 0 {
        return .failure(NegativeAmountError())
    }
    var (counts, remaining) = ([Int:Int](), amount)
    for denomination in [25, 10, 5, 1] {
        (counts[denomination], remaining) = 
            remaining.quotientAndRemainder(dividingBy: denomination)
    }
    return .success(counts)
}

func firstThenLowerCase(of strings: [String], satisfying predicate: (String) -> Bool) -> String?{
    return strings.first(where: predicate)?.lowercased()
}

class say {
    private var words: [String] = []

    init(_ word: String? = nil) {
        self.words = word != nil ? [word!] : []
    }

    @discardableResult
    func and(_ word: String) -> say {
        let newSay = say()
        newSay.words = self.words + [word]
        return newSay
    }

    var phrase: String {
        return words.joined(separator: " ")
    }
}

func meaningfulLineCount(_ filename: String) async -> Result<Int, Error> {
    let url = URL(fileURLWithPath: filename)
    
    do {
        let fileHandle = try FileHandle(forReadingFrom: url)
        defer {
            try? fileHandle.close()
        }
        
        var count = 0
        for try await line in fileHandle.bytes.lines {
            let trimmedLine = line.trimmingCharacters(in: .whitespacesAndNewlines)
            if !trimmedLine.isEmpty && !trimmedLine.hasPrefix("#") {
                count += 1
            }
        }
        
        return .success(count)
    } catch {
        return .failure(error)
    }
}

struct Quaternion: Equatable, CustomStringConvertible {
    let a: Double
    let b: Double
    let c: Double
    let d: Double

    static let ZERO = Quaternion(a: 0, b: 0, c: 0, d: 0)
    static let I = Quaternion(a: 0, b: 1, c: 0, d: 0)
    static let J = Quaternion(a: 0, b: 0, c: 1, d: 0)
    static let K = Quaternion(a: 0, b: 0, c: 0, d: 1)

    var coefficients: [Double] {
        return [a, b, c, d]
    }

    var conjugate: Quaternion {
        return Quaternion(a: a, b: -b, c: -c, d: -d)
    }

    init(a: Double = 0, b: Double = 0, c: Double = 0, d: Double = 0) {
        self.a = a
        self.b = b
        self.c = c
        self.d = d
    }

    static func + (lhs: Quaternion, rhs: Quaternion) -> Quaternion {
        return Quaternion(
            a: lhs.a + rhs.a,
            b: lhs.b + rhs.b,
            c: lhs.c + rhs.c,
            d: lhs.d + rhs.d
        )
    }

    static func * (lhs: Quaternion, rhs: Quaternion) -> Quaternion {
        let a1 = lhs.a, b1 = lhs.b, c1 = lhs.c, d1 = lhs.d
        let a2 = rhs.a, b2 = rhs.b, c2 = rhs.c, d2 = rhs.d
        return Quaternion(
            a: a1 * a2 - b1 * b2 - c1 * c2 - d1 * d2,
            b: a1 * b2 + b1 * a2 + c1 * d2 - d1 * c2,
            c: a1 * c2 - b1 * d2 + c1 * a2 + d1 * b2,
            d: a1 * d2 + b1 * c2 - c1 * b2 + d1 * a2
        )
    }

    static func == (lhs: Quaternion, rhs: Quaternion) -> Bool {
        return lhs.coefficients == rhs.coefficients
    }

    var description: String {
        var parts: [String] = []

        if a != 0 {
            parts.append("\(a)")
        }
        
        for (coeff, symbol) in [(b, "i"), (c, "j"), (d, "k")] {
            if coeff != 0 {
                let part: String
                if abs(coeff) == 1 {
                    part = "\(coeff < 0 ? "-" : "")\(symbol)"
                } else {
                    part = "\(coeff)\(symbol)"
                }
                parts.append(part)
            }
        }

        if parts.isEmpty {
            return "0"
        }

        var result = parts[0]
        for part in parts.dropFirst() {
            result += part.hasPrefix("-") ? part : "+" + part
        }
        return result
    }
}

enum BinarySearchTree: CustomStringConvertible{
    case empty
    indirect case node(BinarySearchTree, String, BinarySearchTree)

    var size: Int {
        switch self {
        case .empty:
            return 0
        case .node(let left, _, let right):
            return 1 + left.size + right.size
        }
    }

    func contains(_ value: String) -> Bool {
        switch self {
        case .empty:
            return false
        case let .node(left, v, right):
            if value < v {
                return left.contains(value)
            } else if value > v {
                return right.contains(value)
            } else {
                return true
            }
        }
    }

    func insert(_ value: String) -> BinarySearchTree {
        switch self {
        case .empty:
            return .node(.empty, value, .empty)
        case let .node(left, v, right):
            if value < v {
                return .node(left.insert(value), v, right)
            } else if value > v {
                return .node(left, v, right.insert(value))
            } else {
                return self
            }
        }
    }

    var description: String {
        switch self {
        case .empty:
            return "()"
        case let .node(left, value, right):
            let leftDesc = left.description == "()" ? "":left.description
            let rightDesc = right.description == "()" ? "":right.description
            return "(\(leftDesc)\(value)\(rightDesc))"
        }
    }
}
