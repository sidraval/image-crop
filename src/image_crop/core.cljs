(ns image-crop.core
  (:use [jayq.core :only [$ width on document-ready css off position]]))

(enable-console-print!)

(def aspect-ratio 2)
(def differences (js-obj))
(defn img [] ($ :#image))
(defn box [] ($ :#frame))

(defn move-box [e]
  (do
    (css (box) {:left (- (.-pageX e) (.-x differences))})
    (css (box) {:top (- (.-pageY e) (.-y differences))})))

(defn drag-box []
  (on (box) :click move-box))

(defn bind-mousemove [e]
  (do
    (on (box) :mousemove move-box)
    (set! (.-y differences) (-  (.-pageY e) (:top (position (box)))))
    (set! (.-x differences) (-  (.-pageX e) (:left (position (box)))))))

(defn unbind-mousemove []
  (off (box) :mousemove))

(defn bind-mousedown []
  (do
    (on (box) :mousedown bind-mousemove)
    (on (box) :mouseup unbind-mousemove)))

(defn initialize []
  (bind-mousedown))

(document-ready initialize)
