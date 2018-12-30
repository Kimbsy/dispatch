(ns dispatch.core
  (:gen-class)
  (:require [amazonica.aws.sqs :as sqs]
            [clojure.data.json :as json]
            [dispatch.remote-tor :as rt]
            [dispatch.ip :as ip]))

(def running (atom true))

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
    (case (:action body)
      "remote-torrent"
      (rt/run-remote-torrent! (:data body))

      "get-ip"
      (ip/get-ip!)

      "exit"
      (do-exit!)

      (unrecognised body))))

(defn -main
  [& args]

  (prn (System/getenv "DISPATCH_NOTIFY_EMAIL_ARN"))

  (while @running
    (let [dispatch-queue (sqs/find-queue "dispatch-queue.fifo")
          response       (sqs/receive-message :queue-url dispatch-queue
                                              :wait-time-seconds 14 ; polling every 14 seconds means ~200,000 requests per month.
                                              :max-number-of-messages 10
                                              :delete true
                                              :atribute-names ["ALL"])]
      (doseq [m (:messages response)]
        (process-message m)))))
