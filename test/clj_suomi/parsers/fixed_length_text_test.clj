(ns clj-suomi.parsers.fixed-length-text-test
  (:require [clojure.test :refer :all]
            [clj-suomi.parsers.fixed-length-text :refer :all]))

(def a (parser [[:a 5]
                [:b 5]]))

(def b (parser [[:a 5 clojure.string/upper-case]]))

(def c (parser [[:a 3]]
               {:encoding "UTF-8"}))

(def d (parser [[:a 3]]
               {:encoding "ISO-8859-1"}))

(deftest parser-test
  (testing "No enough input"
    (is (thrown-with-msg? IllegalStateException #"\[field: :a\] Tried to read 5 bytes but read 4"
                          (a "abcd"))))
  (testing "Ok"
    (is {:a "abcde"
         :b "12345"}
        (a "abcde12345")))
  (testing "optional function"
    (is {:a "ABCDE"}
        (b "abcde"))))

(deftest encoding-test
  (testing "utf-8 chars don't work"
    (is (= {:a "ä�"}
           (c "äöå"))))
  (testing "iso-8859-1 works"
    (is (= {:a "äöå"}
           (d "äöå")))))
