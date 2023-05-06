(ns advent.2020.5
  (:require [clojure.string :as s]))

(defn to-row [bits]
  (let [[b1 b2 b3 b4 b5 b6 b7] bits]
    (+ (* b1 64) (* b2 32) (* b3 16) (* b4 8) (* b5 4) (* b6 2) (* b7 1))))

(defn to-col [bits]
  (let [[b1 b2 b3] bits]
    (+ (* b1 4) (* b2 2) (* b3 1))))

(defn seat-num [x]
  (let [[row col] x]
    (+ (* 8 row) col)))

(defn solve [s]
  (->> s
       (replace {\F 0 \B 1 \L 0 \R 1})
       ((juxt #(to-row (take 7 %)) #(to-col (drop 7 %))))
       seat-num))

(defn compute [seats]
  (loop [xs (range)]
    (if-let [x (first xs)]
      (if (and (seats (inc x)) (seats (dec x)) (not (seats x)))
        x
        (recur (rest xs)))
      nil)))

(def input (->> (slurp "input.dat") s/split-lines))

(defn solve-1 []
  (->> input
       (map solve)
       (apply max)))

(solve-1)

(defn solve-2 []
  (->> input
       (map solve)
       (into #{})
       compute
       ))

(solve-2)
