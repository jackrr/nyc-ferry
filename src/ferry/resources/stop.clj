(ns ferry.resources.stop
  (:require
   [clojure.string :as str]
   [ferry.resources.base :refer [get-data]]))

(def schema [{:db/ident ::id
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident ::name
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one}])

(defn- stop-data []
  (get-data :stops))

(defn entities []
  (map
   #(do {::name (:stop_name %)
         ::id (:stop_id %)})
   (stop-data)))

(defn stops-matching
  [search]
  (filter
   #(str/includes?
     (str/lower-case (:stop_name %)) (str/lower-case search))
   (stop-data)))

(defn next-departure
  [stop]
  "TODO")

(comment
  (vals (first (stop-data)))
  (entities)
  (stops-matching "DUMBO"))
