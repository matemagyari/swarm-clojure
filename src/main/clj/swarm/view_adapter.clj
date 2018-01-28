(ns swarm.view-adapter)

(defn- entity->view
  "Convert an entity to a view - a tuple"
  [entity]
  (let [position (get entity :position)]
    [(:type entity)
     (-> position :x int)
     (-> position :y int)]))

(defn entities->view
  "Converts a sequence of entitites to views, grouped by rows"
  [entities]
  (let [e-views (map entity->view entities)
        groups (group-by second e-views)]
    (vals groups)))

