import { describe, it } from "node:test"
import { deepEqual, throws, rejects } from "node:assert/strict"
import {
  change,
  firstThenApply,
  powersGenerator,
  meaningfulLineCount,
  Shape,
  BinarySearchTree,
  Empty,
  volume,
  surfaceArea,
  areShapesEqual,
  shapeToString,
} from "./exercises.js"

function expectChange(
  amount: bigint,
  [q, d, n, p]: [bigint, bigint, bigint, bigint]
) {
  deepEqual(
    change(amount),
    new Map([
      [25n, q],
      [10n, d],
      [5n, n],
      [1n, p],
    ])
  )
}

describe("The change function", () => {
  it("throws on negative", () => {
    throws(() => change(-50n), /RangeError/)
  })
  it("works for 0", () => {
    expectChange(0n, [0n, 0n, 0n, 0n])
  })
  it("works for the usual cases", () => {
    expectChange(1n, [0n, 0n, 0n, 1n])
    expectChange(8n, [0n, 0n, 1n, 3n])
    expectChange(42n, [1n, 1n, 1n, 2n])
    expectChange(99n, [3n, 2n, 0n, 4n])
    expectChange(144n, [5n, 1n, 1n, 4n])
    expectChange(250n, [10n, 0n, 0n, 0n])
  })
  it("can handle really big values", () => {
    expectChange(100000000037n, [4000000001n, 1n, 0n, 2n])
    expectChange(10000000000005n, [400000000000n, 0n, 1n, 0n])
  })
})

// Uncomment the following tests as you complete the exercises

describe("The firstThenApply function", () => {
  const nonEmpty = (s: string) => s !== ""
  const lengthGreaterThan3 = (s: string) => s.length > 3
  const lower = (s: string) => s.toLowerCase()
  const square = (n: number) => n * n
  it("works", () => {
    deepEqual(firstThenApply([], nonEmpty, lower), undefined)
    deepEqual(firstThenApply(["", "A", "B"], nonEmpty, lower), "a")
    deepEqual(
      firstThenApply(["", "A", "ABC"], lengthGreaterThan3, lower),
      undefined
    )
    deepEqual(
      firstThenApply(["ABC", "ABCD", "ABCDE"], lengthGreaterThan3, lower),
      "abcd"
    )
    deepEqual(
      firstThenApply([1, 2, 3], (n) => n > 1, square),
      4
    )
    deepEqual(
      firstThenApply([1, 2, 3], (n) => n > 3, square),
      undefined
    )
  })
})

describe("The powers generator", () => {
  it("works as expected", () => {
    const g1 = powersGenerator(2n)
    deepEqual(g1.next(), { value: 1n, done: false })
    deepEqual(g1.next(), { value: 2n, done: false })
    for (let i = 0; i < 98; i++) g1.next()
    deepEqual(g1.next(), {
      value: 1267650600228229401496703205376n,
      done: false,
    })

    const g2 = powersGenerator(3n)
    deepEqual(g2.next(), { value: 1n, done: false })
    deepEqual(g2.next(), { value: 3n, done: false })
    deepEqual(g2.next(), { value: 9n, done: false })
    deepEqual(g2.next(), { value: 27n, done: false })
    deepEqual(g2.next(), { value: 81n, done: false })
  })
})

describe("The meaningfulLineCount function", async () => {
  await it("throws if no such file", async () => {
    rejects(async () => await meaningfulLineCount("NoSuchFile.txt"), /Error/)
  })
  await it("correctly counts lines for the test file", async () => {
    const count = await meaningfulLineCount("../test-for-line-count.txt")
    deepEqual(count, 5)
  })
})

describe("The shape functions", () => {
  const sphere: Shape = { kind: "Sphere", radius: 5 }
  const box: Shape = { kind: "Box", width: 3, length: 4, depth: 5 }
  it("calculates volumes correctly", () => {
    deepEqual(volume(sphere), 523.5987755982989)
    deepEqual(volume(box), 60)
  })
  it("calculates surface areas correctly", () => {
    deepEqual(surfaceArea(sphere), 314.1592653589793)
    deepEqual(surfaceArea(box), 94)
  })
})

describe("The BinarySearchTree class", () => {
  let t: BinarySearchTree<string>
  it("starts empty", () => {
    t = new Empty()
    deepEqual(t.size(), 0)
    deepEqual(t.contains("A"), false)
    deepEqual(`${t}`, "()")
  })
  it("can insert elements", () => {
    t = t.insert("G")
    deepEqual(t.size(), 1)
    deepEqual(t.contains("G"), true)
    deepEqual(t.contains("A"), false)
    deepEqual(`${t}`, "(G)")
    t = t.insert("B")
    deepEqual(`${t}`, "((B)G)")
    t = t.insert("D")
    deepEqual(`${t}`, "((B(D))G)")
    t = t.insert("H")
    deepEqual(`${t}`, "((B(D))G(H))")
    t = t.insert("A")
    deepEqual(`${t}`, "(((A)B(D))G(H))")
    t = t.insert("C")
    t = t.insert("J")
    deepEqual(t.size(), 7)
    deepEqual(t.contains("J"), true)
    deepEqual(t.contains("Z"), false)
    deepEqual(`${t}`, "(((A)B((C)D))G(H(J)))")
  })
  it("is immutable", () => {
    let t2 = t
    t2 = t2.insert("F")
    deepEqual(t2.size(), 8)
    deepEqual(t.size(), 7)
  })
  it("can iterate in order", () => {
    deepEqual([...t.inorder()], ["A", "B", "C", "D", "G", "H", "J"])
    let t2: BinarySearchTree<number> = new Empty()
    deepEqual([...t2.inorder()], [])
    t2 = t2.insert(5)
    deepEqual([...t2.inorder()], [5])
    t2 = t2.insert(3)
    t2 = t2.insert(8)
    deepEqual([...t2.inorder()], [3, 5, 8])
  })
  it("ignores insertions if values are already present", () => {
    let t: BinarySearchTree<boolean> = new Empty()
    t = t.insert(true)
    t = t.insert(false)
    t = t.insert(true)
    deepEqual(t.size(), 2)
  })
})
