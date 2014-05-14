(ns image-crop.core
  (:use [jayq.core :only [$ width on document-ready css off position]]))

(enable-console-print!)

(def aspect-ratio 2)
(defn img [] ($ :#image))
(defn box [] ($ :#frame))

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

(defn bind-mousedown []
  (do
    (on (box) :mousedown bind-mousemove)
    (on (box) :mouseup unbind-mousemove)))

(defn initialize []
  (bind-mousedown))

(document-ready initialize)
