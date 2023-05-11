(ns advent.2020.6
  (:require [clojure.set :as cset]
            [clojure.string :as s]))

(defn solve-case-1 [c]
  (->> c
       (map #(into #{} %))
       (apply cset/union)
       count))

(defn solve-1 []
  (->> (slurp "input.dat")
       (#(s/split % #"\n\n"))
       (map #(s/split % #"\n"))
       (map solve-case-1)
       (apply +)))

(solve-1)

(defn solve-case-2 [c]
  (->> c
       (map #(into #{} %))
       (apply cset/intersection)
       count))

(defn solve-2 []
  (->> (slurp "input.dat")
       (#(s/split % #"\n\n"))
       (map #(s/split % #"\n"))
       (map solve-case-2)
       (apply +)))

(solve-2)
