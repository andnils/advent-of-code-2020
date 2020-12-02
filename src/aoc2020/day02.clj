(ns aoc2020.day02
  (:require [aoc2020.utils :refer [xor] :as utils]))


(def testdata ["1-3 a: abcde"
               "1-3 b: cdefg"
               "2-9 c: ccccccccc"])

(defn count-chars
  "Count number of occurances of char C
  in string S"
  [c s]
  (->> s
       (filter #(= c %))
       (count)))

(defn parse [policy]
  (let [[_ MIN MAX CHAR PWD] (re-matches #"(\d+)-(\d+) (\w): (\w+)" policy)]
    [(Integer/parseInt MIN)
     (Integer/parseInt MAX)
     (first CHAR)
     PWD]))

(defn valid? [policy]
  (let [[min max c pwd] (parse policy)]
    (<= min (count-chars c pwd) max)))

(defn solve-with [validator-fn]
  (->>  (utils/read-file "day02.txt" :line-fn validator-fn)
        (filter identity)
        (count)))


(defn solve-part-1 []
  (solve-with valid?))


(defn valid-v2? [policy]
  (let [[p1 p2 c pwd] (parse policy)
        c1 (.charAt pwd (dec p1))
        c2 (.charAt pwd (dec p2))]
    (xor (= c c1)
         (= c c2))))

(defn solve-part-2 []
  (solve-with valid-v2?))


(comment
  (solve-part-1)
  (solve-part-2)
  )



