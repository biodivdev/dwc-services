(ns dwc-services.web-wrap
  (:use ring.util.response))

(defn wrap-jsonp
  "JSONP Wrapper"
  [handler] 
   (fn [req] 
     (let [q (:query-string req)
           response (handler req)]
       (if (nil? q) response
         (if-not (.contains q "callback=") response
           (let [callback (second
                           (re-find #"callback=([a-zA-Z0-9-_]+)" 
                            (:query-string req)))]
               (assoc response :body (str callback "(" (:body response) ");")))
           )))))

(defn wrap-options
  "CORS Options Wrapper"
  [handler]
   (fn [req]
     (if (= :options (:request-method req))
       {:headers {"Allow" "GET,POST,PUT,OPTIONS" 
                  "Access-Control-Allow-Origin" ((:headers req) "origin")
                  "Access-Control-Allow-Methods" "GET,POST,PUT,OPTIONS" 
                  "Access-Control-Allow-Headers" (str "x-requested-with" "," ((:headers req) "access-control-request-headers" ) )}
        :status 200}
       (let [res (handler req)]
         (assoc res :headers
            (merge (:headers res)
               {"Allow" "GET,POST,PUT,OPTIONS" 
                "Access-Control-Allow-Origin" ((:headers req) "origin")
                "Access-Control-Allow-Methods" "GET,POST,PUT,OPTIONS" 
                "Access-Control-Allow-Headers" (str "x-requested-with" "," ((:headers req) "access-control-request-headers" ) )}
             ))
         ))))

(def context-path (atom nil))

(def proxy-path (atom (or (System/getenv "PROXY") nil)))

(defn- get-context-path
    "Returns the context path when running as a servlet"
    ([] @context-path)
    ([servlet-req]
          (if (nil? @context-path)
             (reset! context-path
               (.getContextPath servlet-req)))
          @context-path))

(defn wrap-context
  ""
  [handler]
  (fn [req] 
    (if-let [servlet-req (:servlet-request req)]
      (let [context (get-context-path servlet-req)
            uri (:uri req)]
        (if (.startsWith uri context)
          (handler (assoc req :uri (.substring uri (.length context))))
          (handler req)))
      (handler req))))

(defn wrap-context-redir
  ""
  [handler] 
  (fn [req]
    (let [res (handler req)]
      (if (= 302 (:status res))
        (assoc-in res [:headers "Location"] (str @context-path (get-in res [:headers "Location"])))
        res))))

(defn wrap-proxy-redir
  ""
  [handler] 
  (fn [req]
    (let [res (handler req)]
      (if (= 302 (:status res))
        (let [location (get-in res [:headers "Location"])]
          (assoc-in res [:headers "Location"]
           (str (or (System/getenv "PROXY") "")
             location)))
        res))))

