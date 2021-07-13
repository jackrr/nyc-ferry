(ns ferry.resources.base
  (:require [clojure.java.shell :refer [sh]]
            [clojure.data.csv :refer [read-csv]]
            [clojure.java.io :as io]))

(def files
  ["calendar_dates"
   "calendar"
   "routes"
   "shapes"
   "stops"
   "stop_times"
   "trips"])

(def parsed-files (atom {}))

(defn csv-data->maps [csv-data]
  (map zipmap
       (->> (first csv-data)
            (map keyword)
            repeat)
       (rest csv-data)))

(defn load-file!
  "Loads csv file filename into memory as a seq of hashmaps"
  [filename]
  (with-open [reader (io/reader (str "data/" filename ".txt"))]
    ;; Fix: Skip byte order mark: https://github.com/clojure/data.csv#byte-order-mark
    (.skip reader 1)
    (let [filedata (doall (csv-data->maps (read-csv reader)))]
      (swap! parsed-files
             #(assoc % (keyword filename) filedata))
      (identity filedata))))

(defn- refresh-loaded-files!
  "Re-syncs in-memory CSV from file"
  []
  (for [filename files]
    (load-file! filename)))

(defn refresh-static!
  "Downloads and unzips GTFS static data"
  []
  (sh "scripts/download-static")
  (refresh-loaded-files!))

(defn refresh-realtime!
  "Downloads latest GTFS realtime alerts and tripupdates"
  []
  (sh "scripts/download-realtime"))

(defn refresh-all!
  []
  (refresh-static!)
  (refresh-realtime!))

(defn get-data
  [filekey]
  (or (filekey parsed-files)
      (load-file! (name filekey))))

(comment
  (refresh-static!)
  (first (get-data :routes)))
