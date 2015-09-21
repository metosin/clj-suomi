(ns clj-suomi.codesets.postalcodes
  "Retrieve postal code data from Posti FTP server.

   - [Source](http://www.posti.fi/yritysasiakkaat/laheta/postinumeropalvelut/postinumerotiedostot.html)
   - [Service description and terms](http://www.posti.fi/liitteet-yrityksille/ehdot/postinumeropalvelut-palvelukuvaus-ja-kayttoehdot.pdf)"
  (:require [clojure.string :as string]
            [clojure.java.io :as io]
            [clj-suomi.parsers.fixed-length-text :as fixed-length-text]
            [clj-suomi.utils.zip :as zip])
  (:import [java.time LocalDate]
           [java.time.format DateTimeFormatter]))

; FIXME: The filename changes everyweek
; Also available uncompressed. Is it faster to download compressed and
; decompress or download larger file?
(def url "ftp://postcode:postcode@ftp2.itella.com/PCF_20150921.zip")

(defn date
  "Parse date in format `yyyyMMdd` to LocalDate"
  [s]
  (LocalDate/parse s (DateTimeFormatter/ofPattern "yyyyMMdd")))

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
     [:start-date 8 date]
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

(defn load-postalcodes
  "Download postal code data from Posti FTP and parse to Clojure maps."
  []
  (with-open [rdr (io/reader (zip/first-entry (zip/decode-stream (io/input-stream url)))
                             :encoding "ISO-8859-1")]
    (doall (map ->postalcode (line-seq rdr)))))
