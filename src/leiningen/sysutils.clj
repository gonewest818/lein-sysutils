(ns leiningen.sysutils
  (:require [clojure.reflect :refer [reflect]])
  (:import (org.apache.commons.lang3 SystemUtils)))

(defn assoc-java-version-simple
  "Calculate what is colloquially thought of as the \"version\" of the
  JDK for purposes of integration. These are strings like \"1.6\",
  \"1.7\", \"1.8\" and \"9\".  Associate this value in the provided
  map with the key `:java-version-simple`."
  [m]
  (let [sv (cond
             (:is-java-1-1 m) "1.1"
             (:is-java-1-2 m) "1.2"
             (:is-java-1-3 m) "1.3"
             (:is-java-1-4 m) "1.4"
             (:is-java-1-5 m) "1.5"
             (:is-java-1-6 m) "1.6"
             (:is-java-1-7 m) "1.7"
             (:is-java-1-8 m) "1.8"
             (:is-java-9   m)   "9" ; new naming per JEP 223
             (:is-java-10  m)  "10" ; anticipating commons-lang >= 3.7
             :else            "unknown")]
    (assoc m :java-version-simple sv)))

(defn sysutils-public-fields
  "Find the names of the public fields in SystemUtils via reflection."
  []
  (->> (reflect SystemUtils) 
       :members
       (filter (partial instance? clojure.reflect.Field))
       (filter (comp :public :flags))
       (map :name)))

(defn keywordize
  "Convert a Java field name to an idiomatic Clojure keyword."
  [n]
  (-> (clojure.string/replace n #"_" "-") clojure.string/lower-case keyword))

(defn sysutils-map
  "Return the SystemUtils data as a map. The keys are the field names
  translated to idiomatic Clojure keywords. For example the resulting
  map will have the key `:is-java-9` associated with the value of
  `SystemUtils/IS_JAVA_9`, etc.  Constructing the map dynamically
  allows the available values to adjust to the implementation of the
  commons-lang3 library we are built upon."
  []
  (let [names  (sysutils-public-fields)
        keys   (map keywordize names)
        values (map #(eval (symbol (str "org.apache.commons.lang3.SystemUtils/" %))) names)]
    (assoc-java-version-simple (zipmap keys values))))

(defn format-output
  "Print the map m. If the edn parameter is false then print exactly one
  key and value pair per line. If the edn parameter is true then print
  the results as a map."
  [m edn]
  (if edn
    (println (pr-str m))
    (doseq [[k v] m]
      (println k v))))

(defn ^:no-project-needed sysutils
  "Look up system information via the Apache commons-lang3 API."
  [project & args]
  (let [all     (set (->> args
                          (map clojure.edn/read-string)
                          (filter keyword?))) ; ignore non-keywords for now
        edn     (:edn all)
        queries (remove #{:edn} all)]
    (format-output (select-keys (sysutils-map) queries) edn)))
