(ns aoc2020.utils
  (:require [clojure.java.io]
            [clojure.edn]))


(defn abs
  "Absolute value of `i`"
  [^long i]
  (Math/abs i))

(defn ->int [s]
  (Integer/parseInt (str s)))

(def sum
  "Sum of numbers"
  (partial reduce +))

(defn read-edn-string [filename]
  (-> filename
      (clojure.java.io/resource)
      (slurp)
      (clojure.edn/read-string)))

(defn read-file [filename & {:keys [parse-lines-with]
                             :or {parse-lines-with identity}}]
  (->> (clojure.java.io/resource filename)
       (slurp)
       (clojure.string/split-lines)
       (map parse-lines-with)))

(defmacro xor 
  ([] nil)
  ([a] a)
  ([a b]
    `(let [a# ~a
           b# ~b]
      (if a# 
        (if b# nil a#)
        (if b# b# nil)))))
