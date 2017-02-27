(ns clj-suomi.validation.hetu
  "Validator for Finnish personal identifaction codes (henkilötunnus/hetu).

  > (require '[clj-suomi.validators.hetu :as hetu])
  > (hetu/valid? \"010170-0205\")
  true"
  #?(:cljs (:require goog.date.Date))
  #?(:clj (:import java.util.Calendar
                   java.util.GregorianCalendar)))

(defn- #?(:clj valid-date? :cljs ^boolean valid-date?) [d m y]
  #?(:clj
     (try
       (doto (GregorianCalendar.)
         (.setLenient false)
         (.set Calendar/YEAR y)
         (.set Calendar/MONTH (dec m))
         (.set Calendar/DAY_OF_MONTH d)
         (.getTime))
       true
       (catch IllegalArgumentException _
         false))
     :cljs
     (let [date (goog.date.Date. y (dec m) d)]
       (and (= d (.getDate date)) (= (dec m) (.getMonth date)) (= y (.getYear date))))))

(defn- parse-int [x] #?(:cljs (js/parseInt x) :clj (Integer/valueOf x)))

(let [->cent-number {"+" 1800, "-" 1900, "A" 2000}
      checkchars "0123456789ABCDEFHJKLMNPRSTUVWXY"
      checkchars-map (zipmap (range) (map str checkchars))
      hetu-pattern (re-pattern (str "^((\\d{2})(\\d{2})(\\d{2}))([\\-\\+A])(\\d{3})([" checkchars "])$"))]

  (defn- get-year [year-in-cent cent]
    (let [cent-number (->cent-number cent)]
      (+ cent-number (parse-int year-in-cent))))

  (defn #?(:clj valid? :cljs ^boolean valid?)
    "Check that s is a valid hetu string."
    [s]
    (boolean
      (when-let [parts (and s (re-matches hetu-pattern s))]
        (let [[_ date day month year-in-cent cent identifier checkchar] parts
              year (get-year year-in-cent cent)
              check-number (parse-int (str date identifier))
              check-reminder (rem check-number 31)]
          (and
            (valid-date? (parse-int day) (parse-int month) year)
            (= (checkchars-map check-reminder) checkchar)))))))


