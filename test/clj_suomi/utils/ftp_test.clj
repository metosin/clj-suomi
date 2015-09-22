(ns clj-suomi.utils.ftp-test
  (:require [clojure.test :refer :all]
            [clj-suomi.utils.ftp :refer :all]
            [clojure.java.io :as io]
            [clojure.set :as set]))

(def test-ftp "ftp://ftp.funet.fi")

(deftest parse-line-test
  (is (= "favicon.ico"
         (parse-line "-rwxr-xr-x    1 0          0                1078 Apr  7      2006 favicon.ico")))
  (is (= "pub"
         (parse-line "drwxrwxr-x   45 108        42                 57 Mar 27  2013 pub")))
  (is (= "java-is-now-under-pub-languages-java"
         (parse-line "drwxrwsr-x    5 819        50019               5 Mar 31  2003 java-is-now-under-pub-languages-java"))))

(deftest list-file-names-test
  (is (set/subset?
          #{"README" "dev" "favicon.ico" "ftp" "incoming" "index" "pub" "rfc"}
          (set (list-file-names test-ftp)))))
