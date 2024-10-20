module Exercises
    ( change,
      firstThenApply
    ) where

import qualified Data.Map as Map
import Data.Text (pack, unpack, replace)
import Data.List(isPrefixOf, find)
import Data.Char(isSpace)

change :: Integer -> Either String (Map.Map Integer Integer)
change amount
    | amount < 0 = Left "amount cannot be negative"
    | otherwise = Right $ changeHelper [25, 10, 5, 1] amount Map.empty
        where
          changeHelper [] remaining counts = counts
          changeHelper (d:ds) remaining counts =
            changeHelper ds newRemaining newCounts
              where
                (count, newRemaining) = remaining `divMod` d
                newCounts = Map.insert d count counts

-- Write your first then apply function here
firstThenApply :: [a] -> (a -> Bool) -> (a -> b) -> Maybe B
firstThenApply [] __ = Nothing
firstThenApply (x:xs) p failed
    | p x = Just (f x)
    | otherwise = firstThenApply xs p f
 
-- Write your infinite powers generator here

-- Write your line count function here

-- Write your shape data type here

-- Write your binary search tree algebraic type here
