(ns
  swarm.gravity
  (:require [swarm.vector-algebra :as va]))

(defn gravity-vector
  "Gravity vector from point A to B. Direction depends on the points, the length
   is the square root of the distance between them and the gravitation constant"
  [point-from point-to grav-constant min-distance]
  (let [dist (Math/max
               min-distance
               (va/distance point-from point-to))]
    (if (= 0.0 dist)
      {:x 0.0 :y 0.0}
      (let [dir (va/direction-vector point-from point-to)
            multiplier (/ grav-constant (va/square dist))]
        (va/v* multiplier dir)))))

(defn sum-gravity-vector
  "Calculates the total gravity force vector imposed on the subject-entity by the other entitites"
  [subject-entity entities global-constants]
  (let [g-map (:g-map subject-entity)
        to-force (fn [entity]
                   (gravity-vector
                     (:position subject-entity)
                     (:position entity)
                     (get g-map (:type entity))
                     (:min-proximity global-constants)))
        forces (pmap to-force entities)]
    (apply va/v+ forces)))

(def global-constants
  {:gravity-constants {:sheep {:sheep 1
                               :dead-sheep 0
                               :wolf -2
                               :wall -1}
                       :wolf {:sheep 2
                              :dead-sheep 0
                              :wolf 1
                              :wall -1}}
   :min-proximity 1.0})