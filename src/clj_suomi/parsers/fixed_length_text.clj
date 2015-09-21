(ns clj-suomi.parsers.fixed-length-text
  (:require [clojure.string :as string])
  (:import [java.io ByteArrayInputStream]))

(defn trim
  "Return trimmed string or `nil` if the string is empty."
  [s]
  (let [s (string/trim s)]
    (if (seq s)
      s)))

(defn parser
  "Create parser fn for parsing fixed length fields from strings.

   Spec should be vector of tuples.

   Spec tuples have two mandatory fields and third optional field.

   - key: If truthy the read value will be added to the resulting map under this key.
   - length: Length of the field in bytes.
   - (optional) transform: Transformation function, takes in the read and trimmed string.

   Options:

   - `:encoding` - (optional) Encoding to be used when strings are converted to and from byte-arrays."
  ([spec] (parser spec nil))
  ([spec {:keys [encoding]
          :or {encoding "UTF-8"}}]
   (fn [^String line]
     (let [ss (ByteArrayInputStream. (.getBytes line (str encoding)))
           ^bytes buffer (make-array Byte/TYPE 256)]
       (reduce (fn [acc [k n m]]
                 (let [read-n (.read ss buffer 0 n)]
                   (when (not= read-n n)
                     (throw (IllegalStateException. (format "[field: %s] Tried to read %d bytes but read %d" k n read-n))))
                   (if k
                     (assoc acc k ((or m identity) (trim (String. buffer 0 (int n) (str encoding)))))
                     acc)))
               {}
               spec)))))
