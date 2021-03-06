(ns
  ^{:doc "Swarm dynamic, based on gravitational forces between entitites"}
  swarm.swarm
  (:require [swarm.vector-algebra :as va]
            [swarm.gravity :as g]))

(defn deviation
  "Random double in [-max-deviaton +max-deviaton]"
  [stray-tendency rand-num]
  (let [random-max (:random-max stray-tendency)
        random (- (* 2 random-max rand-num) random-max)]
    (+ (:constant stray-tendency) random)))

(defn next-position
  "Calculation of the next position based on the entities around and a random element"
  [global-constants entity entities rand-num]
  (let [speed (:speed entity)
        position (:position entity)]
    (cond (pos? speed)                                      ;; don't bother with entities with zero speed
          (let [total-g-vector (g/sum-gravity-vector entity entities global-constants)
                adjusted-dir-vector (va/rotate-cartesian total-g-vector (deviation (:stray-tendency entity) rand-num))
                result-vector (va/v* speed (va/normalize adjusted-dir-vector))]
            ;(println "DEV" total-g-vector " " adjusted-dir-vector)
            (va/v+ (:position entity) result-vector))
          :else position)))

(defn next-positions
  "Calculate the next positions of the entitites"
  [entitites global-constants rand-factor]
  (for [entity entitites
        :let [others (remove #(= (:id entity) (:id %))
                             entitites)
              next-pos (next-position global-constants entity others (rand-factor))]]
    (assoc entity :position next-pos)))

(def global-constants
  {:gravity-constants {:sheep {:sheep 1
                               :wolf  -2
                               :wall  -1}
                       :wolf  {:sheep 5
                               :wolf  1
                               :wall  -1}}
   :min-proximity     1.0})
