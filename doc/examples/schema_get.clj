(defn conn-cb [asc e a] (println (if e e "connected")))
(def conn (client/connect nil "127.0.0.1" 8087 conn-cb))
(schema/get conn "S-600" result-cb)
(pprint @result)