(ns ferry.data
  (:require [datahike.api :as d]
            [ferry.resources.route :as route]
            [ferry.resources.stop :as stop]))

(def schema (concat stop/schema route/schema))

(def cfg {:store {:backend :file :path "/tmp/ferry"}
          :initial-tx schema})

(when-not (d/database-exists? cfg)
  (d/create-database cfg))

(def conn (d/connect cfg))

(defn sync! []
  (d/transact conn (route/entities))
  (d/transact conn (stop/entities)))

(comment
  (sync!))
