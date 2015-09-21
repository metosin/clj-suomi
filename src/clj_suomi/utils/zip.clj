(ns clj-suomi.utils.zip
  (:import [java.util.zip ZipInputStream]))

(defn first-entry
  "Select the first entry for reading."
  [zip-file-or-is]
  (when-not (.getNextEntry zip-file-or-is)
    (throw (IllegalStateException. "Entry not found.")))
  zip-file-or-is)

(defn find-entry
  "Select an entry for reading by predicate."
  [zip-file-or-is p]
  (loop []
    (let [entry (.getNextEntry zip-file-or-is)]
      (if entry
        (if (p (bean entry))
          entry
          (recur))
        (throw (IllegalStateException. "Entry not found")))))
  zip-file-or-is)

(defn get-entry
  "Select an entry for reading by name."
  [zip-file-or-is name]
  (find-entry zip-file-or-is #(= name (:name %))))

(defn decode-stream
  "Create ZIP stream from stream of encoded data.

   You have to select an entry using [find-entry] or [get-entry]
   before trying to read from the stream.
   Alternatively you can go through all the entries with..."
  [is]
  (ZipInputStream. is))
