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
      (update state :row inc)
      (let [current (.charAt line (:pos state))
            new-pos (mod (+ (:pos state) increment) (count line))]
        (-> state
            (assoc :pos new-pos)
            (update :count (if (tree? current) inc identity))
            (update :row inc))))))


(def initial-state
  {:pos 0
   :row 0
   :count 0})


(defn solver
  ([step]
   (solver step (constantly false)))
  ([step skip-line-fn]
   (:count
    (with-open [rdr (io/reader url)]
      (reduce (make-reducer-fn step skip-line-fn)
              initial-state
              (line-seq rdr))))))

(defn solve-part-1 []
  (solver 3))

(defn solve-part-2 []
  (* (solver 1)
     (solver 3)
     (solver 5)
     (solver 7)
     (solver 1 (fn [{:keys [row]}] (odd? row)))))

(comment
  (solve-part-1)
  (solve-part-2)  ;; => 3584591857
  )
