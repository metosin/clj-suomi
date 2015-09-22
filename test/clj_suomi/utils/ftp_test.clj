(ns clj-suomi.utils.ftp-test
  (:require [clojure.test :refer :all]
            [clj-suomi.utils.ftp :refer :all]
            [clojure.java.io :as io]))

(def test-ftp {:host "ftp.funet.fi"
               :user "anonymous"
               :pass ""})

(deftest test-list-file-names
  (with-open [conn (client test-ftp)]
    (is (clojure.set/subset?
          #{"README" "dev" "favicon.ico" "ftp" "incoming" "index" "pub" "rfc"}
          (set (list-file-names conn))))))

(deftest is-closed-test
  (let [conn (client test-ftp)]
    (with-open [conn conn]
      (is (.isConnected conn)))
    (is (not (.isConnected conn)))))
