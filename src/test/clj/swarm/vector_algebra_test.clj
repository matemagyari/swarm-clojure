(ns swarm.vector-algebra-test
  (:require [clojure.test :as test]
            [swarm.vector-algebra :refer :all]
            [swarm.tests-common :refer [is=]]))

(defn is-close-enough [vec-1 vec-2]
  (test/is (> 0.001
              (magnitude
                (merge-with - vec-1 vec-2)))))

(test/deftest some-tests
              (is= 5.0 (magnitude {:x 4 :y 3}))
              (is= 5.0 (distance {:x 1 :y 3} {:x 4 :y 7}))
              (is= {:x 1.0 :y 0.0} (normalize {:x 4 :y 0}))
              (is= {:x 0.0 :y 1.0} (normalize {:x 0 :y 6}))
              (is-close-enough {:x (Math/sqrt 0.5) :y (Math/sqrt 0.5)} (normalize {:x 8 :y 8}))
              (is= {:x 0.0 :y 1.0} (direction-vector {:x 2 :y 6} {:x 2 :y 8}))
              (is= {:x -1.0 :y 0.0} (direction-vector {:x 8 :y 6} {:x 5 :y 6}))
              (is= {:x 15 :y 20} (v+ {:x 4 :y 6} {:x 6 :y 10} {:x 5 :y 4})))


(test/deftest cartesian->polar-test
              (is= {:angle 0.0, :magnitude 1.0} (cartesian->polar {:x 1 :y 0}))
              (is= {:angle Math/PI, :magnitude 1.0} (cartesian->polar {:x -1 :y 0}))
              (is-close-enough {:x 3 :y 7} (-> {:x 3 :y 7} cartesian->polar polar->cartesian)))

(test/deftest rotate-test
              (is-close-enough {:x 0 :y 1} (rotate-cartesian {:x 1 :y 0} (/ Math/PI 2)))
              (is-close-enough {:x -1 :y 0} (rotate-cartesian {:x 1 :y 0} Math/PI))
              (is-close-enough {:x 0 :y -1} (rotate-cartesian {:x -1 :y 0} (/ Math/PI 2)))
              (is-close-enough {:x 1 :y 0} (rotate-cartesian {:x -1 :y 0} Math/PI))
              )

(test/deftest weight-point-test
              (is-close-enough {:x 4 :y 6} (weight-point {:x 1 :y 4} {:x 7 :y 8})))


;(test/run-tests 'swarm.vector-algebra)


