(ns dispatch.ip
  (:gen-class)
  (:require [dispatch.notify :as n]
            [org.httpkit.client :as http]))

(defn get-ip!
  []
  (let [external-ip (-> @(http/get "http://checkip.amazonaws.com")
                        :body
                        slurp)]
    (n/notify-email "blackbird external ip" external-ip)))
