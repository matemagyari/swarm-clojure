(ns
  ^{:doc "Simple vector algebra"}
  swarm.vector-algebra
  (:require [clojure.core.typed :as typed]))

;(typed/defalias Point '{:x typed/Num :y typed/Num})
(typed/defalias Point
  (typed/HMap :mandatory {:x Double, :y Double} :complete? true))
(typed/defalias CartesianVector
  (typed/HMap :mandatory {:x Double, :y Double} :complete? true))
(typed/defalias PolarVector
  (typed/HMap :mandatory {:angle Double, :magnitude Double} :complete? true))

;(typed/ann ^:no-check clojure.core/* [Double Double -> Double])
(typed/ann ^:no-check s* [Double Double -> Double])
(defn s* "Multiply "
  [x y]
  (* x y))

(typed/ann ^:no-check square [typed/Num -> Double])
(defn square "Square of"
  [num]
  (s* num num))


(typed/ann v* [Double CartesianVector -> CartesianVector])
(defn v* "Multiplication for vectors"
  [scalar a-vector]
  {:x (s* scalar (:x a-vector))
   :y (s* scalar (:y a-vector))})

(typed/ann ^:no-check v+ [CartesianVector * -> CartesianVector])
(defn v+ "Sum vector of vectors"
  [& vectors]
  (apply merge-with + vectors))

(typed/ann ^:no-check v- [CartesianVector CartesianVector -> CartesianVector])
(defn v- "Diff vector of vectors"
  [v1 v2]
  (merge-with - v1 v2))

(typed/ann magnitude [CartesianVector -> Double])
(defn magnitude [{x :x y :y}] "Length of a vector"
  (Math/sqrt
    (+ (square x) (square y))))

(typed/ann polar->cartesian [PolarVector -> CartesianVector])
(defn polar->cartesian
  "Transforming a polar vector representation to a cartesian one"
  [{angle :angle magnitude :magnitude}]
  {:x (s* magnitude (Math/cos angle))
   :y (s* magnitude (Math/sin angle))})

(typed/ann cartesian->polar [CartesianVector -> PolarVector])
(defn cartesian->polar
  "Transforming a cartesian vector representation to a polar one"
  [v]
  {:angle     (Math/atan2 (:y v) (:x v))
   :magnitude (magnitude v)})

;(typed/ann ^:no-check clojure.core/update-in [CartesianVector CartesianVector -> CartesianVector])
(typed/ann ^:no-check rotate-cartesian [CartesianVector Double -> CartesianVector])
(defn rotate-cartesian "Rotating a vector"
  [v angle]
  (-> v
      cartesian->polar
      (update-in [:angle] + angle)
      polar->cartesian))

(typed/ann ^:no-check distance [Point Point -> Double])
(defn distance "Distance between 2 points"
  [point-from point-to]
  (magnitude
    (merge-with - point-to point-from)))

(typed/ann null-vector? [CartesianVector -> Boolean])
(defn null-vector? "Checks whether the vector is a null vector"
  [v]
  (or (= v {:x 0 :y 0})
      (= v {:x 0.0 :y 0.0})))

(defn normalize "Normalize the vector"
  [a-vector]
  (let [len (magnitude a-vector)]
    (if (= 0.0 len)
      a-vector                                              ; null vector simply returned
      (let [div-len #(/ % len)]
        (-> a-vector
            (update-in [:x] div-len)
            (update-in [:y] div-len))))))

(defn direction-vector
  "(Normalized) direction vector from point A to point B"
  [point-from point-to]
  (normalize (merge-with - point-to point-from)))

(defn weight-point "Calculates the weight point of the points"
  [& points]
  (let [n (count points)
        sums (apply v+ points)]
    (-> sums
        (update-in [:x] #(/ % n))
        (update-in [:y] #(/ % n)))))
