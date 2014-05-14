(ns image-crop.core
  (:use [jayq.core :only [$ width height on document-ready css off position]]))

(enable-console-print!)

(def aspect-ratio 2)
(defn img [] ($ :#image))
(defn box [] ($ :#frame))
(defn resize [] ($ :#resize))

(defn move-box [x y e]
  (do
    (css (box) {:left (- (.-pageX e) x)})
    (css (box) {:top (- (.-pageY e) y)})))

(defn drag-box []
  (on (box) :click move-box))

(defn bind-mousemove [e]
  (on (box) :mousemove (partial move-box (click-difference (.-pageX e) :left) (click-difference (.-pageY e) :top))))

(defn click-difference [mouse-coord corner]
  (- mouse-coord (corner (position (box)))))

(defn unbind-mousemove []
  (off (box) :mousemove))

(defn bind-resize []
  (do
    (on (img) :mousemove #(println "YES"))
    false))

(defn change-width []
  )

(defn unbind-resize []
  (off (img) :mousemove))

(defn bind-mousedown []
  (do
    (on (box) :mousedown bind-mousemove)
    (on (box) :mouseup unbind-mousemove)
    (on (resize) :mousedown bind-resize)
    (on (resize) :mouseup unbind-resize)))

(defn initialize []
  (bind-mousedown))

(document-ready initialize)
