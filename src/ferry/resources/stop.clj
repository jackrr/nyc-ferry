(ns ferry.resources.stop
  (:require
   [clojure.string :as str]
   [ferry.resources.base :refer [get-data]]))

(defn- stop-data []
  (get-data :stops))

(defn stops-matching
  [search]
  (filter
   #(str/includes?
     (str/lower-case (:stop_name %)) (str/lower-case search))
   (stop-data)))

(defn next-departure
  [stop]
  "TODO"
  )

(comment
  (stops-matching "DUMBO")
  )
