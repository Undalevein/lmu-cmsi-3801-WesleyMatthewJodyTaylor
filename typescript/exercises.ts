/**
 * Author: Wesley, Jody, Matthew, and Taylor
 * Collaborators: Ray Toal
 */

import { FileHandle, open } from "node:fs/promises"

export function change(amount: bigint): Map<bigint, bigint> {
  if (amount < 0) {
    throw new RangeError("Amount cannot be negative")
  }
  let counts: Map<bigint, bigint> = new Map()
  let remaining = amount
  for (const denomination of [25n, 10n, 5n, 1n]) {
    counts.set(denomination, remaining / denomination)
    remaining %= denomination
  }
  return counts
}

export function firstThenApply<T, U>(
  elements: T[],
  predicate: (element: T) => boolean,
  consumer: (element: T) => U,
): U | undefined {
  const firstElement: T | undefined = elements.filter(predicate)[0]
  if (firstElement) {
    return consumer(firstElement)
  }
  return undefined
}

export function* powersGenerator(base: bigint): IterableIterator<BigInt> {
  let exponent: bigint = 1n;
  while (true) {
    yield exponent
    exponent *= base
  }
}

export async function meaningfulLineCount(filePath: string): Promise<number> {
  const file: FileHandle = await open(filePath, "r");
  let validLines: number = 0
  if (!file) {
    throw new Error(`File ${filePath} cannot be found.`)
  }
  for await (const line of file.readLines()) {
    let editedLine: string = line.trim()
    if (editedLine.length > 0 && editedLine.charAt(0) !== '#') {
      validLines++
    }
  }
  return validLines
}

/*
 * Notice: Because there was no tests in exercises.test.ts for testing the Shape's
 * immutability, equality, or string casting for problem #4, there are some methods
 * here that are included that won't get used. I intend to leave these here because
 * these are technically required for the problem.
 */
type Immutable<T> = {
  readonly [Key in keyof T]: Immutable<T[Key]>
}

export type Shape = Box | Sphere
type Sphere = Immutable<{ kind: string; radius: number }>
type Box = Immutable<{ kind: string; width: number; length: number; depth: number }>

function isSphere(shape: Shape): shape is Sphere {
  return "radius" in shape
}

export function volume(shape: Shape): number {
  if (isSphere(shape)) {
    return (4 / 3) * Math.PI * shape.radius ** 3
  } else {
    return shape.width * shape.length * shape.depth
  }
}

export function surfaceArea(shape: Shape): number {
  if (isSphere(shape)) {
    return 4 * Math.PI * shape.radius ** 2
  } else {
    return (2 * (shape.width * shape.length +
      shape.width * shape.depth +
      shape.length * shape.depth))
  }
}

export function areShapesEqual(shape1: Shape, shape2: Shape): boolean {
  if (isSphere(shape1) && isSphere(shape2)) {
    return shape1.radius === shape2.radius
  } else if (!isSphere(shape1) && !isSphere(shape2)) {
    return (shape1.width === shape2.width &&
      shape1.length === shape2.length &&
      shape1.depth === shape2.depth)
  } else {
    return false
  }
}

export function shapeToString(shape: Shape): string {
  if (isSphere(shape)) {
    return `${shape.kind} ${shape.radius}`
  } else {
    return `${shape.kind} ${shape.width} ${shape.length} ${shape.depth}`
  }
}

export type BinarySearchTree<T> = Node<T> | Empty<T>

class Node<T> {
  private nodeItem: T
  private left: BinarySearchTree<T>
  private right: BinarySearchTree<T>

  public constructor(nodeItem: T, left: BinarySearchTree<T>, right: BinarySearchTree<T>) {
    this.nodeItem = nodeItem
    this.left = left
    this.right = right
  }

  public size(): number {
    return this.left.size() + 1 + this.right.size()
  }

  public contains(target: T): boolean {
    if (target === this.nodeItem) {
      return true
    }
    switch (typeof target) {
      case "number":
        if (target < (this.nodeItem as number)) {
          return this.left.contains(target)
        } else {
          return this.right.contains(target)
        }
      case "string":
        if (target.localeCompare(this.nodeItem as string) < 0) {
          return this.left.contains(target)
        } else {
          return this.right.contains(target)
        }
      case "boolean":
        // Since booleans have only 2 possible values, we'll return true
        // if one of the child nodes is not empty.
        return this.left instanceof Node || this.right instanceof Node
      default:
        return false
    }
  }

  public insert(item: T): BinarySearchTree<T> {
    if (item === this.nodeItem) {
      return new Node(this.nodeItem, this.left, this.right)
    } else if (typeof item !== typeof this.nodeItem) {
      throw new Error(`TypeError: ${item} is not a ${typeof this.nodeItem}.`)
    }

    switch (typeof item) {
      case "number":
        if (item < (this.nodeItem as number)) {
          return new Node(this.nodeItem, this.left.insert(item), this.right)
        } else {
          return new Node(this.nodeItem, this.left, this.right.insert(item))
        }
      case "string":
        if (item.localeCompare(this.nodeItem as string) < 0) {
          return new Node(this.nodeItem, this.left.insert(item), this.right)
        } else {
          return new Node(this.nodeItem, this.left, this.right.insert(item))
        }
      case "boolean":
        if (this.nodeItem as boolean) {
          return new Node(this.nodeItem, this.left, this.right.insert(item))
        } else {
          return new Node(this.nodeItem, this.left.insert(item), this.right)
        }
      default:
        // Assuming that the types for item and this.item are the same 
        // but the method does not support their variable types, then
        // return the current Node.
        return new Node(this.nodeItem, this.left, this.right)
    }
  }

  public *inorder(): IterableIterator<T> {
    yield* this.left.inorder()
    yield this.nodeItem
    yield* this.right.inorder()
  }

  public toString(): string {
    const leftItem = (this.left instanceof Empty) ? "" : `${this.left}`
    const rightItem = (this.right instanceof Empty) ? "" : `${this.right}`
    return `(${leftItem}${this.nodeItem}${rightItem})`
  }
}

export class Empty<T> {

  public size(): number {
    return 0
  }

  public contains(_: T): boolean {
    return false
  }

  public insert(item: T): BinarySearchTree<T> {
    return new Node(item, new Empty(), new Empty())
  }

  public *inorder(): IterableIterator<T> { }

  public toString(): string {
    return "()"
  }

}