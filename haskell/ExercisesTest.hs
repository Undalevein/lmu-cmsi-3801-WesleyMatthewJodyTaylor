import Data.Map (Map, toDescList)
import Data.Text (toLower, pack, unpack)
import Text.Printf (printf)
import Exercises

test_change :: Integer -> (Integer, Integer, Integer, Integer) -> Bool
test_change amount (q, d, n, p) = 
    case (change amount) of
        Left message -> False
        Right mapping -> toDescList mapping == [(25, q), (10, d), (5, n), (1, p)]

fixture :: [(String, Bool)]
fixture =
    [ ( "change detects negatives", change (-50) == Left "amount cannot be negative" )
    , ( "change 0", test_change 0 (0, 0, 0, 0) )
    , ( "change 1", test_change 1 (0, 0, 0, 1) )
    , ( "change 8", test_change 8 (0, 0, 1, 3) )
    , ( "change 42", test_change 42 (1, 1, 1, 2) )
    , ( "change 99", test_change 99 (3, 2, 0, 4) )
    , ( "change 144", test_change 144 (5, 1, 1, 4) )
    , ( "change 250", test_change 250 (10, 0, 0, 0) )
    , ( "change 100000000037", test_change 100000000037 (4000000001, 1, 0, 2) )
    , ( "change 10000000000005", test_change 10000000000005 (400000000000, 0, 1, 0) )
    -- Uncomment the following lines as you implement the functions
    , ( "firstThenApply empty list", (firstThenApply [] (/= "") lower) == Nothing )
    , ( "firstThenApply lowercase ok", (firstThenApply ["", "A", "B"] (/= "") lower) == Just "a" )
    , ( "firstThenApply lengthOver3 no match"
      , (firstThenApply ["", "A", "ABC"] lengthOverThree lower == Nothing ))
    , ( "firstThenApply lengthOver3 match"
      , (firstThenApply ["ABC", "ABCD", "ABCDE"] lengthOverThree lower == Just "abcd" ))
    , ( "firstThenApply square no match", (firstThenApply [1, 2, 3] (> 3) (^ 2) == Nothing ))
    , ( "firstThenApply square match", (firstThenApply [1, 2, 3] (> 1) (^ 2) == Just 4 ))
    , ( "powers of 2", take 10 (powers 2) == [1,2,4,8,16,32,64,128,256,512])
    , ( "powers of 3", take 5 (powers 3) == [1,3,9,27,81])
    , ( "powers of 1000000", (powers (1000000::Integer) !! 30) == 10^180)
    , ( "powers of -1", take 10 (powers (-1)) == [1,-1,1,-1,1,-1,1,-1,1,-1])
    , ( "volume of sphere radius 1", volume (Sphere 1) `is_approx` (4 * pi / 3))
    , ( "volume of sphere radius 2", volume (Sphere 2) `is_approx` (32 * pi / 3))
    , ( "surfaceArea of sphere radius 1", surfaceArea (Sphere 1) `is_approx` (4 * pi))
    , ( "surfaceArea of sphere radius 2", surfaceArea (Sphere 2) `is_approx` (16 * pi))
    , ( "box volume", volume (Box 1.5 3 20) == 90)
    , ( "box surfaceArea", surfaceArea (Box 1.5 3 20) == 189)
    , ( "spheres are equatable", (Sphere 6.28) == (Sphere 6.28))
    , ( "boxes are equatable", (Box 2 10 3) == (Box 2 10 3))
    , ( "spheres can be shown", (show $ Sphere 3) == "Sphere 3.0")
    , ( "boxes can be shown", (show $ Box 3 1 2) == "Box 3.0 1.0 2.0")
    , ( "new tree size is 0", size Empty == 0)
    , ( "new tree contains nothing", not $ contains 5 Empty)
    , ( "new tree shows as ()", show (Empty::BST Int) == "()")
    , ( "inserting G", show g == "(\"G\")")
    , ( "inserting G then B", show gb == "((\"B\")\"G\")")
    , ( "inserting G then B then D", show gbd == "((\"B\"(\"D\"))\"G\")")
    , ( "number tree", show tree_52381 == "(((1)2(3))5(8))")
    , ( "number tree with extra", show (insert 0 tree_52381) == "((((0)1)2(3))5(8))")
    , ( "number tree did not change", show tree_52381 == "(((1)2(3))5(8))")
    , ( "number tree inoder empty", inorder (Empty::BST Int) == [])
    , ( "number tree inorder", inorder tree_52381 == [1, 2, 3, 5, 8])
    ]
    -- Uncomment the following as needed as you implement your tests
    where
        lower = unpack . toLower . pack
        lengthOverThree = (> 3) . length
        x `is_approx` y = abs (x - y) < 0.0000001
        g = insert "G" Empty
        gb = insert "B" g
        gbd = insert "D" gb
        tree_52381 = insert 1 $ insert 8 $ insert 3 $ insert 2 $ insert 5 Empty

main =
    -- You'll have to do some uncommenting here too!
    let results = map test fixture in do
        putStrLn $ unlines $ map fst results
        failed <- return $ sum $ map snd results
        passed <- return $ length fixture - failed
        shouldBe5 <- meaningfulLineCount "../test-for-line-count.txt"
        passed <- return $ passed + (if shouldBe5 == 5 then 1 else 0)
        failed <- return $ failed + (if shouldBe5 == 5 then 0 else 1)
        printf "%d passed, %d failed\n" passed failed
        where
            test (message, condition) =
                ( message ++ ": " ++ (if condition then "SUCCESS" else "FAIL")
                , if condition then 0 else 1
                )
