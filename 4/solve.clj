(ns advent.2020.4
  (:require [clojure.set :as cset]
            [clojure.string :as s]))

(def expected-keys #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"})

(defn parse-line [input]
  (let [result (re-seq #"(\S+):(\S+)" input)]
    (->> (for [[_ k v] result] [k v])
         (into {}))))

(defn parse [input]
  (->> input
       (#(s/split % #"\n\n")) ; test cases are separated by blank line
       (map parse-line)))

(def input (->> (slurp "input.dat") parse))

;; Part 1

(defn check-1 [test-case]
  (->> test-case
       (map first) ; select the keys
       (into #{}) ; cast to set
       (cset/intersection expected-keys) ; determine valid keys
       count
       (= 7)))

(defn solve-1 []
  (->> input
       (map check-1)
       (filter true?)
       count))

(solve-1)

;; Part 2

(defn valid? [x]
  (let [[k v] x]
    (case k
      "byr" (and (some? (re-matches #"\d{4}" v)) (<= 1920 (parse-long v) 2002))
      "iyr" (and (some? (re-matches #"\d{4}" v)) (<= 2010 (parse-long v) 2020))
      "eyr" (and (some? (re-matches #"\d{4}" v)) (<= 2020 (parse-long v) 2030))
      "hgt" (let [[_ cm] (re-find #"(\d+)cm" v) [_ in] (re-find #"(\d+)in" v)]
              (cond
                cm (<= 150 (parse-long cm) 193)
                in (<= 59 (parse-long in) 76)
                :else false))
      "hcl" (some? (re-matches #"#[0-9a-f]{6}" v))
      "ecl" (some? (#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} v))
      "pid" (some? (re-matches #"\d{9}" v))
      "cid" true)))

(defn check-2 [test-case]
  (->> test-case
       (filter valid?) ; select key-value pairs that pass the test
       (map first) ; select the keys
       (into #{}) ; cast to set
       (cset/intersection expected-keys) ; determine valid keys
       count
       (= 7)))

(defn solve-2 []
  (->> input
       (map check-2)
       (filter true?)
       count))

(solve-2)
