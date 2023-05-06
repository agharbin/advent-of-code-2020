(ns advent.2020.2
  (:require [clojure.string :as s]))

(defn parse [rule]
  (let [[_ low high c s] (re-matches #"(\d+)-(\d+) (\w): (\w+)" rule)]
    [(parse-long low) (parse-long high) (first c) s]))

(defn solve-1 [rule]
  (let [[low high c s] (parse rule)
        freqs (frequencies s)]
    (if (freqs c)
      (<= low (freqs c) high)
      false)))

(defn xor [cond-a cond-b]
  (if cond-a
    (not cond-b)
    cond-b))

(defn solve-2 [rule]
  (let [[index-1 index-2 c s] (parse rule)
        first-char (nth s (dec index-1))
        second-char (nth s (dec index-2))]
    (xor (= first-char c) (= second-char c))))

;; Part 1

(->> (slurp "input.dat")
     s/split-lines
     (map solve-1)
     (filter true?)
     count)

;; Part 2

(->> (slurp "input.dat")
     s/split-lines
     (map solve-2)
     (filter true?)
     count)
