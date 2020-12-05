(ns aoc2020.day03
  (:require [clojure.java.io :as io]))

(def url
  (io/resource "day03.txt"))

(def test-url
  (io/resource "day03-test.txt"))

(defn tree? [c]
  (= c \#))


(defn reducer-fn [state line]
  (let [current (.charAt line (:pos state))
        new-pos (mod (+ (:pos state) 3) (count line))]
    (-> state
        (assoc :pos new-pos)
        (update :count (if (tree? current) inc identity)))))


(def initial-state
  {:pos 0
   :count 0})

(defn solve-part-1 []
  (with-open [rdr (io/reader url)]
    (reduce reducer-fn initial-state (line-seq rdr))))


(comment
  (solve-part-1)
  )
