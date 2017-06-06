(ns clj-suomi.codesets.postalcodes
  "Retrieve postal code data from Posti HTTP server.

   - [Source](http://www.posti.fi/yritysasiakkaat/laheta/postinumeropalvelut/postinumerotiedostot.html)
   - [Service description and terms](http://www.posti.fi/liitteet-yrityksille/ehdot/postinumeropalvelut-palvelukuvaus-ja-kayttoehdot.pdf)"
  (:require [clojure.string :as string]
            [clojure.java.io :as io]
            [clj-suomi.parsers.fixed-length-text :as fixed-length-text]
            [clj-suomi.utils.zip :as zip])
  (:import [java.time LocalDate]
           [java.time.format DateTimeFormatter]))

(def base-url "https://www.posti.fi/webpcode/")

(defn url [filename]
  (format "%s/%s" base-url filename))

(def date-formatter (DateTimeFormatter/ofPattern "yyyyMMdd"))

(defn str->date
  "Parse date in format `yyyyMMdd` to LocalDate"
  [s]
  (LocalDate/parse s date-formatter))

(def types
  {"1" :normal
   "2" :post-office-box
   "3" :company
   "4" :aggregation-service
   "5" :reply-mail
   "6" :smartpost
   "7" :pick-up
   "8" :technical})

; FIXME: Instead of one field, add :finnish? and :swedish? fields ?
(def languages
  {"1" :finnish
   "2" :both
   "3" :both
   "4" :swedish})

(def ->postalcode
  (fixed-length-text/parser
    [[nil 5] ; "PONOT"
     [nil 8] ; The same date as in filename
     [:code 5]
     [:name 30]
     [:name-swe 30]
     [:short 12]
     [:short-swe 12]
     [:start-date 8 str->date]
     [:type 1 types]
     [:admistrative-area-code 5]
     [:admistrative-area-name 30]
     [:admistrative-area-name-swe 30]
     [:municipality-code 3]
     [:municipality-name 20]
     [:municipality-name-swe 20]
     [:language 1 languages]]
    ; Character widths break with UTF-8
    {:encoding "ISO-8859-1"}))

(defn find-latest-file
  "Finds a href to PCF_ file in the directory root."
  []
  (->> (slurp base-url)
       (re-find #"\"http[s]?://www\.posti\.fi/webpcode/(PCF_.*\.zip)\"")
       second))

(comment
  (find-latest-file))

(defn load-postalcodes
  "Download postal code data from Posti HTTP and parse to Clojure maps.

   Options:
   - :filename - (optional) Filename to download. By default check the server root for latest PCF_*.zip file.
     If you want to use specific version, provide path to file in `arch` folder."
  ([] (load-postalcodes nil))
  ([{:keys [filename now?]}]
   (let [filename (or filename (find-latest-file))]
     (with-open [rdr (io/reader (zip/first-entry (zip/decode-stream (io/input-stream (url filename))))
                                :encoding "ISO-8859-1")]
       (doall (map ->postalcode (line-seq rdr)))))))
