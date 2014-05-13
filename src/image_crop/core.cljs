(ns image-crop.core
  (:use [jayq.core :only [$ width on document-ready css off position]]))

(enable-console-print!)

(def aspect-ratio 2)
(defn img [] ($ :#image))
(defn box [] ($ :#frame))

(defn move-box [e]
  (do
    (css (box) {:left (- (.-pageX e) (.-x differences))})
    (css (box) {:top (- (.-pageY e) (.-y differences))})))

(defn drag-box []
  (on (box) :click move-box))

(defn bind-mousemove [e]
  (on (box) :mousemove ((fn [x y]
                          (fn [ev]
                            (do
                              (css (box) {:left (- (.-pageX ev) x)})
                              (css (box) {:top (- (.-pageY ev) y)}))))
                        (- (.-pageX e) (:left (position (box)))) (- (.-pageY e) (:top (position (box)))))))

(defn unbind-mousemove []
  (off (box) :mousemove))

(defn bind-mousedown []
  (do
    (on (box) :mousedown bind-mousemove)
    (on (box) :mouseup unbind-mousemove)))

(defn initialize []
  (bind-mousedown))

(document-ready initialize)
