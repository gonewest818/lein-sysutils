(ns leiningen.sysutils-test
  (:require [clojure.test :refer :all]
            [leiningen.sysutils :refer :all]))

(deftest assoc-java-version-simple-test
  (let [m (assoc-java-version-simple {})]
    (is (= "unknown" (:java-version-simple m))) "unknown java version")
  (let [m (assoc-java-version-simple {:is-java-1-1 true})]
    (is (= "1.1" (:java-version-simple m)) "known java version")))

(deftest add-java-versions-test
  (let [m (add-java-versions {:java-specification-version "11"})]
    (is (false? (:is-java-10 m)))
    (is (true? (:is-java-11 m)))
    (is (false? (:is-java-12 m))))
  (let [m (add-java-versions {:is-java-10 false
                              :is-java-12 true})]
    (is (false? (:is-java-10 m))
        (true? (:is-java-12 m)))))

(deftest extend-is-java-x-test
  (let [m (sysutils-map)]
    (is (= (:java-specification-version m)
           (:java-version-simple m)))))

(deftest keywordize-test
  (is (= :simple      (keywordize "SIMPLE")))
  (is (= :two-words   (keywordize "TWO_WORDS")))
  (is (= :digits-1234 (keywordize "DIGITS_1234")))
  (is (= :dots.ok     (keywordize "DOTS.OK"))))

(deftest format-output-test
  (let [m {:a true :b "quoted string"}]
    (is (= "{:a true, :b \"quoted string\"}\n"
           (with-out-str (format-output m :edn))))
    (is (= ":a true\n:b quoted string\n"
           (with-out-str (format-output m nil))))))

(deftest help-test
  (is (= "Query system information via Apache commons SystemUtils"
         (first (clojure.string/split-lines (help))))))

(deftest sysutils-map-test
  (with-redefs [sysutils-public-fields (constantly `("FOO" "BAR" "MY_FIELD"))
                sysutils-lookup {"FOO" "foo" "BAR" "bar" "MY_FIELD" true}]
    (let [m (sysutils-map)]
      (is (= "foo" (:foo m)))
      (is (= "bar" (:bar m)))
      (is (= true (:my-field m)))
      (is (= "unknown" (:java-version-simple m))))))

(deftest sysutils-lookup-test
  (is (= (System/getenv "USER") (sysutils-lookup "USER_NAME")))
  (is (= false (sysutils-lookup "IS_JAVA_1_1"))))

(deftest main-test
  (with-redefs [sysutils-public-fields (constantly `("FOO" "BAR" "MY_FIELD"))
                sysutils-lookup {"FOO" "foo" "BAR" "bar" "MY_FIELD" true}]
    (is (= ":foo foo\n"
           (with-out-str (sysutils {} ":foo"))))
    (is (= "{:foo \"foo\"}\n"
           (with-out-str (sysutils {} ":edn" ":foo"))))
    ;; currently can't guarantee order of results
    (is (#{":foo foo\n:bar bar\n" ":bar bar\n:foo foo\n"}
         (with-out-str (sysutils {} ":foo" ":bar"))))
    ;; currently can't guarantee order of results
    (is (#{"{:foo \"foo\", :bar \"bar\"}\n" "{:bar \"bar\", :foo \"foo\"}\n"}
         (with-out-str (sysutils {} ":edn" ":foo" ":bar"))))))
