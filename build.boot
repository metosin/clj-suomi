(set-env!
  :resource-paths #{"src"}
  :source-paths #{"test-resources" "test"}
  :dependencies '[[org.clojure/clojure "1.8.0" :scope "provided"]
                  [metosin/boot-alt-test "0.3.0" :scope "test"]])

(require '[metosin.boot-alt-test :refer [alt-test]])

(def +version+ "0.2.0-SNAPSHOT")

(task-options!
  pom {:project 'metosin/clj-suomi
       :version +version+
       :description "Access Finnish code sets"
       :url "https://github.com/metosin/clj-suomi"
       :license {"Eclipse Public License" "http://www.eclipse.org/legal/epl-v10.html"}})

(ns-unmap *ns* 'test)

(deftask test []
  (alt-test))

(deftask build []
  (comp
    (pom)
    (jar)))

(deftask dev []
  (comp
    (repl :server true)
    (watch)
    (test)
    (build)
    (install)))

(deftask deploy []
  (comp
    (build)
    (push :repo "clojars" :gpg-sign (not (.endsWith +version+ "-SNAPSHOT")))))
