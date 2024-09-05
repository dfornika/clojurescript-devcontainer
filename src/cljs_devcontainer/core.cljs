(ns cljs-devcontainer.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))


(defonce db (r/atom {}))


(defn header
  "Header component."
  []
  [:header {:style {:display "grid"
                    :grid-template-columns "repeat(2, 1fr)"
                    :align-items "center"
                    :height "48px"}}
   [:div {:style {:display "grid"
                  :grid-template-columns "repeat(2, 1fr)"
                  :align-items "center"}}
    [:h1 {:style {:font-family "Arial" :color "#004a87" :margin "0px"}} "Header"]]
   [:div {:style {:display "grid" :align-self "center" :justify-self "end"}}
    [:img {:src "images/logo.svg" :height "48px"}]]])


(defn app
  "Top-level app component."
  []
  [:div {:style {:display "grid"
                 :grid-template-columns "1fr"
                 :grid-gap "4px 4px"
                 :height "100%"}}
   [header]
   [:div {:style {:display "grid"
                  :grid-template-columns "1fr"
                  :grid-template-rows "1fr"
                  :gap "4px"
                  :height "800px"}}]])


(defn render
  "Render the app component inside the 'app' div."
  []
  (rdom/render [app] (js/document.getElementById "app")))


(defn ^:dev/after-load re-render
  "Re-render the site when the source code is updated."
  []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code.
  ;; This function is called implicitly by its annotation.
  (render))


(defn main
  "Main entrypoint."
  []
  (render))


(defn init
  "Initialize the application."
  []
  (set! (.-onload js/window) main))
