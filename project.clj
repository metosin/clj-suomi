(defproject metosin/clj-suomi "0.1.0-SNAPSHOT"
  :description "Access Finnish code sets"
  :url "https://github.com/metosin/clj-suomi"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :profiles {:dev {:plugins [[funcool/codeina "0.3.0"]]
                   :resource-paths ["test-resources"]}}
  :codeina {:sources ["src"]
            :target "gh-pages"
            :src-dir-uri "http://github.com/metosin/clj-suomi/blob/master/"
            :src-linenum-anchor-prefix "L" })
