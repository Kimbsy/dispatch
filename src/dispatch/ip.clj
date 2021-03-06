(ns dispatch.ip
  (:gen-class)
  (:require [dispatch.notify :as n]
            [org.httpkit.client :as http]))

(defn get-ip!
  []
  (prn "IP address requested")
  (let [external-ip (-> @(http/get "http://checkip.amazonaws.com")
                        :body
                        slurp)]
    (n/notify-sms "blackbird external ip" external-ip)))
