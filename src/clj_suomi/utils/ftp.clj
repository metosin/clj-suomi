(ns clj-suomi.utils.ftp
  (:import [org.apache.commons.net.ftp FTPClient FTPFile]
           [java.io IOException Closeable]))

(defn ^FTPClient closable-ftp-client []
  (proxy [FTPClient Closeable] []
    (close []
      (when (.isConnected ^FTPClient this)
        (try
          (.logout ^FTPClient this)
          (catch IOException _ nil))
        (try
          (.disconnect ^FTPClient this)
          (catch IOException _ nil))))))

(defn client
  "Create FTPClient with given options.

   Remember to close the connection either manually or by
   using this with `with-open`."
  [{:keys [host port user pass]
    :or {port 21}}]
  {:pre [(string? host) (number? port) (string? user) (string? pass)]}
  (doto (closable-ftp-client)
    (.connect host port)
    (.login user pass)))

(defn list-files [^FTPClient client]
  (.listFiles client))

(defn list-file-names [^FTPClient client]
  (map #(.getName ^FTPFile %) (list-files client)))
