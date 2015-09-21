(ns clj-suomi.utils.zip
  (:import [java.util.zip ZipInputStream]))

(defn first-entry
  "Select the first entry for reading."
  [^ZipInputStream zip-is]
  (when-not (.getNextEntry zip-is)
    (throw (IllegalStateException. "Entry not found.")))
  zip-is)

(defn find-entry
  "Select an entry for reading by predicate.

   Predicate is called with entry bean."
  [^ZipInputStream zip-is p]
  (loop []
    (let [entry (.getNextEntry zip-is)]
      (if entry
        (if (p (bean entry))
          entry
          (recur))
        (throw (IllegalStateException. "Entry not found")))))
  zip-is)

(defn get-entry
  "Select an entry for reading by name."
  [zip-is name]
  (find-entry zip-is #(= name (:name %))))

(defn decode-stream
  "Create ZIP stream from stream of encoded data.

   You have to select an entry using [find-entry] or [get-entry]
   before trying to read from the stream.
   Alternatively you can go through all the entries with..."
  [is]
  (ZipInputStream. is))
