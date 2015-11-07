(set-env!
  :resource-paths #{"src"}
  :source-paths #{"test-resources" "test"}
  :dependencies '[[org.clojure/clojure "1.7.0" :scope "provided"]
                  [adzerk/boot-test "1.0.4" :scope "test"]])

(require '[adzerk.boot-test :refer [test]])

(task-options!
  pom {:project 'metosin/clj-suomi
       :version "0.2.0-SNAPSHOT"
       :description "Access Finnish code sets"
       :url "https://github.com/metosin/clj-suomi"
       :license {"Eclipse Public License" "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask dev []
  (comp
    (watch)
    (repl :server true)
    (pom)
    (jar)
    (install)))

(deftask run-tests []
  (test))
