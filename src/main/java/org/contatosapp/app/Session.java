package org.contatosapp.app;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private Session() {
        attributes = new HashMap<>();
    }

    private Map<String, Object> attributes;

    private static Session instance = null;

    public static Session getInstance() {

        if (instance == null)
            instance = new Session();

        return instance;
    }

    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return this.attributes.getOrDefault(key, null);
    }

    public void clearAttributes() {
        this.attributes.clear();
    }
}
