(ns clj-suomi.utils.ftp
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn parse-line [line]
  (-> line
      (string/split #" ")
      last))

(defn list-file-names
  "Parses FTP directory listing and returns the filenames."
  [url]
  (with-open [is (io/input-stream url)]
    (->> (io/reader is)
         line-seq
         (map parse-line)
         doall)))
