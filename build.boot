(set-env!
 :source-paths #{"src"}
 :dependencies '[[org.clojure/clojure "1.7.0" :scope "provided"]
                 [org.clojure/clojurescript "1.7.228" :scope "provided"]
                 [adzerk/boot-cljs "1.7.170-3" :scope "test"]
                 [adzerk/boot-test "1.0.7" :scope "test"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]
                 [crisptrutski/boot-cljs-test "0.2.1" :scope "test"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-test :refer [test]]
         '[adzerk.bootlaces :refer [bootlaces! build-jar push-snapshot]]
         '[crisptrutski.boot-cljs-test :refer [test-cljs]])

(def +version+ "0.4.0-SNAPSHOT")
(bootlaces! +version+)

(task-options!
 push {:ensure-branch nil}
 pom {:project 'org.clojars.cgdub/valip
      :version "0.4.0-SNAPSHOT"
      :description "Functional validation library for Clojure and ClojureScript,
                    forked from https://github.com/weavejester/valip"
      :url "http://github.com/cgdub/valip"
      :scm {:url "http://github.com/cgdub/valip"}
      :license {"Eclipse Public License" "http://www.eclipse.org/legal/epl-v10.html"}}
 test {:namespaces #{'valip.test.core 'valip.test.predicates}}
 test-cljs {:namespaces #{'valip.test.core 'valip.test.predicates}})

(deftask testing
  []
  (merge-env! :source-paths #{"test"})
  identity)

(deftask tdd
  "Launch a CLJ TDD Environment"
  []
  (comp
   (testing)
   (watch)
   (test-cljs)
   (test)))

(deftask install-jar
  []
  (merge-env! :resource-paths #{"src"})
  (comp
   (pom)
   (jar)
   (install)))
