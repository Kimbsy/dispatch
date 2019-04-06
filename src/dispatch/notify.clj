(ns dispatch.notify
  (:require [amazonica.aws.sns :as sns]))

(defn- notify
  [subject message arn]
  (sns/publish :topic-arn arn
               :subject subject
               :message message))

(defn notify-email
  [subject message]
  (prn "sending email notification...")
  (notify subject message (System/getenv "DISPATCH_NOTIFY_EMAIL_ARN")))

(defn notify-sms
  [subject message]
  (prn "sending sms notification...")
  (notify subject message (System/getenv "DISPATCH_NOTIFY_SMS_ARN")))
