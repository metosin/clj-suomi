(set-env!
  :resource-paths #{"src"}
  :source-paths #{"test-resources" "test"}
  :dependencies '[[org.clojure/clojure "1.8.0" :scope "provided"]
                  [org.clojure/clojurescript "1.9.293" :scope "provided"]

                  [adzerk/boot-cljs "2.0.0-SNAPSHOT" :scope "test"]
                  [crisptrutski/boot-cljs-test "0.3.0" :scope "test"]
                  [doo "0.1.7" :scope "test"]
                  [metosin/boot-alt-test "0.3.0" :scope "test"]])

(require
  '[adzerk.boot-cljs            :refer [cljs]]
  '[metosin.boot-alt-test       :refer [alt-test]]
  '[crisptrutski.boot-cljs-test :refer [test-cljs prep-cljs-tests run-cljs-tests]])

(def +version+ "0.2.0-SNAPSHOT")

(task-options!
  pom {:project 'metosin/clj-suomi
       :version +version+
       :description "Access Finnish code sets"
       :url "https://github.com/metosin/clj-suomi"
       :scm {:url "https://github.com/metosin/clj-suomi"}
       :license {"Eclipse Public License" "http://www.eclipse.org/legal/epl-v10.html"}})

(ns-unmap *ns* 'test)

(deftask test []
  (comp
    (alt-test)
    (test-cljs)))

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
