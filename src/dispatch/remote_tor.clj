(ns dispatch.remote-tor
  (:use [clojure.java.shell :only [sh]]))

(defn run-remote-torrent!
  [{:keys [url target]}]
  (prn "running remote-tor...")
  (prn "target file:" target)
  (sh "download-magnet" url target))
