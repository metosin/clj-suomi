(ns clj-suomi.utils.zip
  (:import [java.util.zip ZipInputStream]))

(defn decode-stream [is]
  ; FIXME: Presumes that zip only contains one file
  (doto (ZipInputStream. is)
    (.getNextEntry)))
