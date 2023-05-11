(ns advent.2020.7
  (:require [clojure.string :as s]))

(def line-regex #"(\w+ \w+) bags contain (.*).")
(def bags-regex #"no other bags|(\d+) (\w+ \w+) bag")

(defn parse-tail [s]
  (->> s
       (re-seq bags-regex)
       (map #(drop 1 %))
       (#(if (= [[nil nil]] %) [] %))
       (map (fn [[x y]] [(parse-long x) y]))))

(defn parse-line [s]
  (->> s
    (re-find line-regex)
    ((fn [[_ x y]] [x (parse-tail y)]))))

(defn build-graph [s]
  (->> s
       (#(s/split % #"\n"))
       (map parse-line)
       (into {})))

(defn find-bag [bag graph]
  (if (= bag "shiny gold")
    true
    (loop [children (graph bag)]
      (if (seq children)
        (if (find-bag (second (first children)) graph)
          true
          (recur (rest children)))
        false))))

;; Part 1

(let [graph (->> (slurp "input.dat") build-graph)
      bags (keys graph)]
  (->> (map #(find-bag % graph) bags)
       (filter true?)
       count
       dec))

(defn count-bags [bag graph]
  (if (seq (graph bag))
    (apply + (for [[n child-bag] (graph bag)]
               (+ n (* n (count-bags child-bag graph)))))
    0))

;; Part 2

(let [graph (->> (slurp "input.dat") build-graph)
      bags (keys graph)]
  (count-bags "shiny gold" graph))
