(ns advent.2020.1
  (:require [clojure.string :as s]))

(defn solve-1 [nums]
  (filter some?
    (let [cnt (count nums)]
      (for [x (range cnt) y (range cnt) :when (< x y)]
        (if (= 2020 (+ (nums x) (nums y)))
          (* (nums x) (nums y))
          nil)))))

(defn solve-2 [nums]
  (filter some?
    (let [cnt (count nums)]
      (for [x (range cnt) y (range cnt) z (range cnt) :when (< x y z)]
        (if (= 2020 (+ (nums x) (nums y) (nums z)))
          (* (nums x) (nums y) (nums z))
          nil)))))

(def input-data
  (->> (slurp "input.dat")
       (s/split-lines)
       (map parse-long)
       (into [])))

(solve-1 input-data)
(solve-2 input-data)
