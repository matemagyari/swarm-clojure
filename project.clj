(defproject swarm "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src/main/clj"]
  :test-paths ["src/test/clj"]
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/test.check "0.6.1"]
                 [org.clojure/core.typed "0.2.77"]]
  :main swarm.main)
