open Exercises;;

(* -------------------------------------------------------------------------- *)
let passes = ref 0;;
let failures = ref 0;;

let suite name = Printf.printf "\nTesting %s" name;;

let expect condition =
  if condition then
    (Printf.printf " .";
    passes := !passes + 1)
  else
    (Printf.printf " F";
    failures := !failures + 1);;

let expect_equal_lists a b =
  expect (List.for_all2 (fun x y -> x = y) a b);;
(* -------------------------------------------------------------------------- *)

suite "change";;
match change (-50) with
  | exception Negative_Amount -> expect true
  | result -> expect false;;
expect (change 0 = [0; 0; 0; 0]);;
expect (change(1) = [0; 0; 0; 1]);;
expect (change(42) = [1; 1; 1; 2]);;
expect (change(99) = [3; 2; 0; 4]);;
expect (change(100000000037) = [4000000001; 1; 0; 2]);;
expect (change(10000000000005) = [400000000000; 0; 1; 0]);;

(* Uncomment the following tests as you complete the exercises *)

let non_empty s = s <> "";;
let length_greater_than_3 s = String.length s > 3;;
let lower s = Some (String.lowercase_ascii s);;
let square n = Some(n * n);;

suite "first_then_apply";;
expect (first_then_apply [] non_empty lower = None);;
expect (first_then_apply [""; "A"; "B"] non_empty lower = Some "a");;
expect (first_then_apply [""; "A"; "ABC"] length_greater_than_3 lower = None);;
expect (first_then_apply ["ABC"; "ABCD"; "ABCDE"] length_greater_than_3 lower = Some "abcd");;
expect (first_then_apply [1; 2; 3] (fun n -> n > 1) square = Some 4);;
expect (first_then_apply [1; 2; 3] (fun n -> n > 3) square = None);;

suite "powers_generator";;
let g1 = powers_generator 1 in (
  expect_equal_lists (Seq.take 3 g1 |> List.of_seq) [1; 1; 1];
  expect_equal_lists (Seq.take 0 g1 |> List.of_seq) []
);;
let g2 = powers_generator 2 in ( 
  expect_equal_lists (Seq.take 10 g2 |> List.of_seq) [1; 2; 4; 8; 16; 32; 64; 128; 256; 512];
  expect_equal_lists (Seq.take 3 g2 |> List.of_seq) [1; 2; 4];
  expect_equal_lists (Seq.take 0 g2 |> List.of_seq) []
);;
let g3 = powers_generator 3 in (
  expect_equal_lists (Seq.take 10 g3 |> List.of_seq) [1; 3; 9; 27; 81; 243; 729; 2187; 6561; 19683];
  expect_equal_lists (Seq.take 3 g3 |> List.of_seq) [1; 3; 9];
  expect_equal_lists (Seq.take 0 g3 |> List.of_seq) []
);;

suite "meaningful_line_count";;
try (meaningful_line_count("no-such-file.txt") |> fun _ -> expect false) with
  | Sys_error _ -> expect true;;
expect(meaningful_line_count("../test-for-line-count.txt") == 5);;

suite "shape";;
let s1 = Sphere 5.0 in
let s2 = Box (3.0, 4.0, 5.0) in (
  expect (volume s1 = 523.5987755982989);
  expect (volume s2 = 60.0);
  expect (surface_area s1 = 314.1592653589793);
  expect (surface_area s2 = 94.0);
);;

suite "binary search tree";;
let t1 = Empty in
let t2 = insert 5 t1 in
let t3 = insert 3 t2 in
let t4 = insert 7 t3 in
let t5 = insert 4 t4 in (
  expect (size t1 = 0);
  expect (size t2 = 1);
  expect (size t3 = 2);
  expect (size t4 = 3);
  expect (size t5 = 4);
  expect (contains 7 t1 = false);
  expect (contains 7 t2 = false);
  expect (contains 7 t3 = false);
  expect (contains 7 t4 = true);
  expect (contains 7 t5 = true);
  expect (inorder t1 = []);
  expect (inorder t2 = [5]);
  expect (inorder t3 = [3; 5]);
  expect (inorder t4 = [3; 5; 7]);
  expect (inorder t5 = [3; 4; 5; 7]);
);;

Printf.printf "\n%d passed, %d failed\n" !passes !failures;;
