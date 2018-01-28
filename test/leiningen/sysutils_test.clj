(ns leiningen.sysutils-test
  (:require [clojure.test :refer :all]
            [leiningen.sysutils :refer :all]))

(deftest assoc-java-version-simple-test
  (let [m (assoc-java-version-simple {})]
    (is (= "unknown" (:java-version-simple m))) "unknown java version")
  (let [m (assoc-java-version-simple {:is-java-1-1 true})]
    (is (= "1.1" (:java-version-simple m)) "known java version")))

(deftest keywordize-test
  (is (= :simple      (keywordize "SIMPLE")))
  (is (= :two-words   (keywordize "TWO_WORDS")))
  (is (= :digits-1234 (keywordize "DIGITS_1234")))
  (is (= :dots.ok     (keywordize "DOTS.OK"))))
