(ns clj-suomi.codesets.postalcodes-test
  (:require [clojure.test :refer :all]
            [clj-suomi.codesets.postalcodes :refer :all])
  (:import [java.time LocalDate]
           [java.time.format DateTimeFormatter]))

(deftest str->date-test
  (is (= (LocalDate/of 2015 1 1) (str->date "20150101"))))

(deftest ->postalcode-test
  (testing "full-data"
    (is (= {:code "79700"
            :name "HEINÄVESI"
            :name-swe "HEINÄVESI"
            :short nil
            :short-swe nil
            :start-date (LocalDate/of 1988 11 1)
            :type :normal
            :admistrative-area-code "FI131"
            :admistrative-area-name "Etelä-Savo"
            :admistrative-area-name-swe "Södra Savolax"
            :municipality-code "090"
            :municipality-name "Heinävesi"
            :municipality-name-swe "Heinävesi"
            :language :finnish}
           (->postalcode (str "PONOT20150921"
                              "79700"
                              "HEINÄVESI                     "
                              "HEINÄVESI                     "
                              "                        "
                              "19881101" "1" "FI131"
                              "Etelä-Savo                    "
                              "Södra Savolax                 "
                              "090" "Heinävesi           " "Heinävesi           "
                              "1"))
           ))))

(def postalcodes (load-postalcodes))

(println (format "[postalcodes] Loaded %d codes" (count postalcodes)))

(deftest smoke-test
  (testing "There exists about 3100 (normal) postal codes"
    (is (<= 3000 (count (filter #(= :normal (:type %)) postalcodes)) 3100))))
