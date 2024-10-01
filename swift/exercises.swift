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

// Write your first then lower case function here
func firstThenLowerCase<T>(of array: [T], satisfying predicate: (T) -> Bool) -> String? where T: StringProtocol {
    for element in array {
        if predicate(element) {
            return element.lowercased()
        }
    }
    return nil
}

// Write your say function here
class say {
    private var words: [String] = []

    // Initializer for starting with an optional initial word
    init(_ word: String? = nil) {
        self.words = word != nil ? [word!] : []
    }

    // Method to add a word to the chain
    @discardableResult
    func and(_ word: String) -> say {
        let newSay = say()
        newSay.words = self.words + [word]
        return newSay
    }

    // Read-only property to get the accumulated phrase
    var phrase: String {
        return words.joined(separator: " ")
    }
}

// Write your meaningfulLineCount function here

// Write your Quaternion struct here

// Write your Binary Search Tree enum here
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
