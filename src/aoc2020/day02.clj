(ns aoc2020.day02
  (:require [aoc2020.utils :refer [xor ->int] :as utils]))


(def testdata ["1-3 a: abcde"
               "1-3 b: cdefg"
               "2-9 c: ccccccccc"])

(defn count-chars
  "Count number of occurances of char c in password"
  [c password]
  (->> password
       (filter #(= c %))
       (count)))

(defn parse
  "Parse a line with policy and password.
  Returns vector of type [int int char String]"
  [policy-and-password]
  (let [[_ x y c pwd] (re-matches #"(\d+)-(\d+) (\w): (\w+)" policy-and-password)]
    [(->int x)
     (->int y)
     (.charAt c 0)      ; convert length-1-string to char
     pwd]))

(defn valid? [policy-and-password]
  (let [[min max c pwd] (parse policy-and-password)]
    (<= min (count-chars c pwd) max)))

(defn solve-with [validator-fn]
  (->>  (utils/read-file "day02.txt" :parse-lines-with validator-fn)
        (filter identity)
        (count)))


(defn solve-part-1 []
  (solve-with valid?))


(defn valid-v2? [policy-and-password]
  (let [[p1 p2 c pwd] (parse policy-and-password)
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


