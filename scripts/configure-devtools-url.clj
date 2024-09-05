#!/usr/bin/env bb

(ns script
  (:require [babashka.cli :as cli]
            [clojure.edn :as edn]
            [clojure.pprint :refer [pprint]]))

(def cli-arg-spec {:config {:default "shadow-cljs.edn"}
                   :port {:default 9630}})

(def args (cli/parse-opts *command-line-args* {:spec cli-arg-spec}))

(def config-file (:config args))

(def original-config (edn/read-string (slurp config-file)))

(def port (:port args))

(def codespace-name (System/getenv "CODESPACE_NAME"))

(def github-codespaces-port-forwarding-domain (System/getenv "GITHUB_CODESPACES_PORT_FORWARDING_DOMAIN"))

(def devtools-url (str "https://" codespace-name "-" port "." github-codespaces-port-forwarding-domain))

(def updated-config (assoc-in original-config [:builds :app :devtools :devtools-url] devtools-url))

(spit config-file (with-out-str (pprint updated-config)))



