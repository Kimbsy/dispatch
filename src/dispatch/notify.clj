(ns dispatch.notify
  (:require [amazonica.aws.sns :as sns]))

(defn notify-email
  [subject message]
  (sns/publish :topic-arn (System/getenv "DISPATCH_NOTIFY_EMAIL_ARN")
               :subject subject
               :message message))
