(ns ferry.core
  (:require [ferry.resources.stop :as stop])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (stop/next-departure (first (stop/stops-matching "DUMBO")))))
