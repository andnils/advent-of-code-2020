(ns aoc2020.day03
  (:require [clojure.java.io :as io]))

(def url
  (io/resource "day03.txt"))

(def test-url
  (io/resource "day03-test.txt"))

(defn tree? [c]
  (= c \#))


(defn make-reducer-fn [increment skip-line?]
  (fn [state line]
    (if (skip-line? state)
      ;; if skip, just increment the row-counter
      (update state :row inc)
      ;; else: check if tree and then increment tree-count
      (let [at-tree? (tree? (.charAt line (:pos state)))
            new-pos (mod (+ (:pos state) increment) (count line))]
        (-> state
            (assoc :pos new-pos)
            (update :count (if at-tree? inc identity))
            (update :row inc))))))


(def initial-state
  {:pos 0
   :row 0
   :count 0})


(defn- move-one-row-at-a-time
  "This is the default fn for deciding
  whether to skip a row.
  Always return false -> don't skip the row."
  [_]
  false)


(defn- move-two-rows-at-a-time
  "This function skips every odd-numbered row."
  [line]
  (let [line-number (:row line)]
    (odd? line-number)))


(defn move-with-step
  ([step]
   (move-with-step step move-one-row-at-a-time))
  ([step skip-line-fn]
   (:count
    (with-open [rdr (io/reader url)]
      (reduce (make-reducer-fn step skip-line-fn)
              initial-state
              (line-seq rdr))))))


(defn solve-part-1 []
  (move-with-step 3))


(defn solve-part-2 []
  (* (move-with-step 1)
     (move-with-step 3)
     (move-with-step 5)
     (move-with-step 7)
     (move-with-step 1 move-two-rows-at-a-time)))

(comment
  (solve-part-1)
  (solve-part-2)
  )
