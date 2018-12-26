(ns dispatch.core
  (:require [clojure.data.json :as json]
            [amazonica.aws.sqs :as sqs])
  (:use [clojure.java.shell :only [sh]]))

(def running (atom true))

(defn run-remote-torrent!
  [{:keys [url target]}]
  (prn "running remote-tor...")
  (prn "magnet link:" url)
  (prn "target file:" target)
  (sh "download-magnet" url target "&"))

(defn remote-torrent-complete
  [data]
  (prn "completed torrenting:" (:target data)))

(defn do-exit!
  []
  (prn "exiting...")
  (reset! running false))

(defn unrecognised
  [body]
  (prn "unrecognised action:" (:action body)))

(defn process-message
  [m]
  (when-let [body (some-> m
                          :body
                          (json/read-str :key-fn keyword))]
    (prn body)
    (case (:action body)
      "remote-torrent"
      (run-remote-torrent! (:data body))

      "remote-torrent-complete"
      (remote-torrent-complete (:data body))

      "exit"
      (do-exit!)

      (unrecognised body))))

(defn -main
  [& args]
  (while @running
    (let [dispatch-queue (sqs/find-queue "dispatch-queue")
          response       (sqs/receive-message :queue-url dispatch-queue
                                              :wait-time-seconds 10
                                              :max-number-of-messages 10
                                              :delete true
                                              :atribute-names ["ALL"])]
      (doseq [m (:messages response)]
        (process-message m)))))
