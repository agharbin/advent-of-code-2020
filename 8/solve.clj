(ns advent.2020.8
  (:require [clojure.string :as s]))

(defn parse-file [f]
  (->> f
       slurp
       (#(s/split % #"\n"))
       (map #(s/split % #" "))
       (mapv (fn [[x y]] [x (Integer/parseInt y)]))))

;; Part 1

(defn exec-program-1 [after-last-instr program-vec]
  (loop [addr 0
         acc  0
         executed #{}]
    (cond
      (executed addr) acc ; terminate with error
      :else (let [[instruction arg] (program-vec addr)
                   next-executed (conj executed addr)]
              (case instruction
                "nop" (recur (inc addr)   acc         next-executed)
                "acc" (recur (inc addr)   (+ acc arg) next-executed)
                "jmp" (recur (+ addr arg) acc         next-executed))))))

(defn solve-1 [input]
  (let [after-last-instr (count input)]
    (exec-program-1 after-last-instr input)))

(->> (parse-file "input.dat")
     solve-1)

;; Part 2

(defn change-n [n input]
  (if (seq input)
    (let [[op arg] (first input)
          remaining (rest input)
          is-nop (= op "nop")
          is-jmp (= op "jmp")]
      (cond
        (and (= n 1) is-nop)             (conj (change-n 0 remaining) ["jmp" arg])
        (and (= n 1) is-jmp)             (conj (change-n 0 remaining) ["nop" arg])
        (and (> n 1) (or is-nop is-jmp)) (conj (change-n (dec n) remaining) [op arg])
        :else                            (conj (change-n n remaining) [op arg])))
    '()))

(defn exec-program-2 [after-last-instr program-vec]
  (loop [addr 0
         acc  0
         executed #{}]
    (cond
      (executed addr) nil ; terminate with error
      (= after-last-instr addr) acc ; terminate with success
      :else (let [[instruction arg] (program-vec addr)
                   next-executed (conj executed addr)]
              (case instruction
                "nop" (recur (inc addr)   acc         next-executed)
                "acc" (recur (inc addr)   (+ acc arg) next-executed)
                "jmp" (recur (+ addr arg) acc         next-executed))))))

(defn solve-2 [input]
  (let [after-last-instr (count input)]
    (loop [n 1]
      (let [changed-input-vec (vec (change-n n input))
            result (exec-program-2 after-last-instr changed-input-vec)]
        (if result
          result
          (recur (inc n)))))))

(->> (parse-file "input.dat")
     solve-2)
