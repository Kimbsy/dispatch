(defproject dispatch "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[amazonica "0.3.136"]
                 [http-kit "2.3.0"]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/data.json "0.2.6"]]

  :main ^:skip-aot dispatch.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
