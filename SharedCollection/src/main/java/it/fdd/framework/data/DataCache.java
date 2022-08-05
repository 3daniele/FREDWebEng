package it.fdd.framework.data;

import java.util.HashMap;
import java.util.Map;

public class DataCache {

    public Map<Class, Map<Object, Object>> cache;

    public DataCache() {
        this.cache = new HashMap<>();
    }

    public <C extends DataItem> void add(Class<C> c, C o) {
        //Logger.getLogger("DataCache").log(Level.INFO, "Cache add: object of class {0} with key {1}", new Object[]{c.getName(), o.getKey()});
        if (!cache.containsKey(c)) {
            cache.put(c, new HashMap<>());
        }
        cache.get(c).put(o.getKey(), o);
    }

    public <C extends DataItem> void delete(Class<C> c, C o) {
        if (has(c, o.getKey())) {
            cache.get(c).remove(o.getKey());
        }
    }

    public <C extends DataItem> boolean has(Class<C> c, C o) {
        //Logger.getLogger("DataCache").log(Level.INFO, "Cache lookup: object of class {0} with key {1}", new Object[]{c.getName(), o.getKey()});
        return cache.containsKey(c) && cache.get(c).containsKey(o.getKey());
    }

    public <C extends DataItem> C get(Class<C> c, Object key) {
        if (has(c, key)) {
            //Logger.getLogger("DataCache").log(Level.INFO, "Cache hit: object of class {0} with key {1}", new Object[]{c.getName(), key});
            return (C) cache.get(c).get(key);
        } else {
            return null;
        }
    }

    public boolean has(Class c, Object key) {
        //Logger.getLogger("DataCache").log(Level.INFO, "Cache lookup: object of class {0} with key {1}", new Object[]{c.getName(), key});
        return cache.containsKey(c) && cache.get(c).containsKey(key);
    }

    public void delete(Class c, Object key) {
        if (has(c, key)) {
            cache.get(c).remove(key);
        }
    }

}
