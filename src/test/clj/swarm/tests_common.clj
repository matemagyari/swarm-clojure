(ns swarm.tests-common
  (:require [clojure.test :as test]))

(defn is= [a b]
  (test/is (= a b)))
