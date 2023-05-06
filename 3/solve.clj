(ns advent.2020.3
  (:require [clojure.string :as s]))

(def input
  (->> (slurp "input.dat")
       s/split-lines
       (mapv vec)))

(defn solve [x-stride y-stride input]
  (let [height (count input)
        width  (count (input 0))]
    (loop [x 0
           y 0
           n 0]
      (if (>= y height)
        n
        (if (= \# (get-in input [y x]))
          (recur (mod (+ x x-stride) width) (+ y y-stride) (inc n))
          (recur (mod (+ x x-stride) width) (+ y y-stride) n))))))

(defn solve-1 []
  (solve 3 1 input))

(solve-1)

(defn solve-2 []
  (apply * 
         (for [[x y] [[1 1] [3 1] [5 1] [7 1] [1 2]]] (solve x y input))))

(solve-2)
