(ns user
  (:require
    [clojure.pprint :refer (pprint)]
    [clojure.repl :refer :all]
    [clojure.string :as str]
    [clojure.test :refer [run-tests run-all-tests]]
    [clojure.tools.namespace.repl :refer (refresh)]
    [kria.bucket :as bucket]
    [kria.client :as client]
    [kria.conversions :as conv]
    [kria.core :as core]
    [kria.index :as index]
    [kria.polling :as p]
    [kria.object :as object]
    [kria.schema :as schema]
    [kria.search :as search]
    [kria.server :as server]))

(set! *warn-on-reflection* true)

(def result (atom nil))

(defn result-cb
  [asc e a]
  (if-not e
    (reset! result a)
    (if (instance? Throwable e)
      (.printStackTrace ^Throwable e)
      (println e))))

(def streaming (atom []))

(def stream-result (promise))

(defn stream-cb
  [coll]
  (if coll
    (swap! streaming concat coll)
    (deliver stream-result @streaming)))
