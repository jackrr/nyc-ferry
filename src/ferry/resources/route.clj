(ns ferry.resources.route
  (:require
   [clojure.string :as str]
   [ferry.resources.base :refer [->entities get-data]]))

(def schema [{:db/ident ::id
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident ::name
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one}])

(defn- route-data []
  (get-data :routes))

(defn routes-matching
  [search]
  (filter
   #(str/includes?
     (str/lower-case (:route_long_name %)) (str/lower-case search))
   (route-data)))

(defn entities []
  (->entities (route-data) *ns*))

(comment
  (entities)
  (routes-matching "Astoria"))
