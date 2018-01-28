(ns swarm.gravity-test
  (:require [swarm.gravity :refer :all]
            [clojure.test :as test]
            [swarm.vector-algebra :as va]
            [swarm.tests-common :refer [is=]]))


(defn is-close-enough [vec-1 vec-2]
  (test/is (> 0.001
              (va/magnitude
                (merge-with - vec-1 vec-2)))))

(test/deftest some-tests
  (is= {:x 0.0 :y 0.0} (gravity-vector {:x 1 :y 1} {:x 1 :y 1} 5 1.0))
  (is= {:x 0.0 :y 2.5} (gravity-vector {:x 0 :y 0} {:x 0 :y 2} 10 1.0))
  (is= {:x -0.4 :y 0.0} (gravity-vector {:x 5 :y 2} {:x 0 :y 2} 10 1.0))
  )

(test/deftest sum-gravity-vector-test
  (let [center {:position {:x 0 :y 0}
                :g-map    (get-in global-constants [:gravity-constants :sheep])
                :type     :sheep}
        wolf-1 {:position {:x 2 :y 0}
                :g-map    (get-in global-constants [:gravity-constants :wolf])
                :type     :wolf}
        sheep-1 {:position {:x 2 :y 0}
                 :type     :sheep
                 :speed    10
                 :g-map    (get-in global-constants [:gravity-constants :sheep])}]
    (is-close-enough {:x -0.25 :y 0.0} (sum-gravity-vector center [wolf-1 sheep-1] global-constants))
    (is-close-enough {:x -0.5 :y 0.0} (sum-gravity-vector center [wolf-1] global-constants))
    (is-close-enough {:x 0.25 :y 0.0} (sum-gravity-vector center [sheep-1] global-constants))))

