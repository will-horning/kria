(ns kria.server-test
  (:require [clojure.test :refer :all]
            [kria.test-helpers :as h]
            [kria.client :as c]
            [kria.server :as s]))

(defn connect
  []
  (let [p (promise)
        conn (c/connect nil "127.0.0.1" 8087 (h/cb-fn p))]
    @p
    conn))

(deftest info-test
  (testing "info"
    (let [conn (connect)
          p (promise)]
      (s/info conn (h/cb-fn p))
      (let [[asc e a] @p]
        (is (nil? e))
        (is (= "riak@127.0.0.1" (:node a)))
        (is (= "2.0.0pre11" (:server-version a)))))))

(deftest ping-test
  (testing "ping-info"
    (let [conn (connect)
          p (promise)]
      (s/ping conn (h/cb-fn p))
      (is (= @p [conn nil true])))))