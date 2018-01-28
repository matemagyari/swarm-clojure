(ns swarm.swarm-test
  (:require [swarm.swarm :refer :all]
            [clojure.test :as test]
            [swarm.vector-algebra :as va]))

(defn is= [a b]
  (test/is (= a b)))

(test/deftest deviation-test
  (is= 3.0 (deviation {:random-max 10.0 :constant 3} 0.5))
  (is= 13.0 (deviation {:random-max 10.0 :constant 3} 1))
  (is= -7.0 (deviation {:random-max 10.0 :constant 3} 0.0))
  )

(test/deftest next-positions-test
  (let [sheep-1 {:position {:x 0 :y 0}
                 :speed    10
                 :g-map    (get-in global-constants [:gravity-constants :sheep])
                 :type     :sheep}
        sheep-2 {:position {:x 0 :y 0}
                 :speed    10
                 :g-map    (get-in global-constants [:gravity-constants :sheep])
                 :type     :sheep}]
    ))

;(test/run-tests 'swarm.swarm)
