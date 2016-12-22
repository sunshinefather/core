package com.palmlink.core.platform.web.session;

import com.palmlink.core.collection.Key;
import com.palmlink.core.collection.KeyMap;
import com.palmlink.core.util.ReadOnly;

public class SessionContext {
    private final ReadOnly<String> id = new ReadOnly<String>();
    private final KeyMap session = new KeyMap();
    private boolean changed;
    private boolean invalidated;

    public <T> T get(Key<T> key) {
        return session.get(key);
    }

    public <T> void set(Key<T> key, T value) {
        session.put(key, value);
        changed = true;
    }

    public void invalidate() {
        session.clear();
        changed = true;
        invalidated = true;
    }

    boolean changed() {
        return changed;
    }

    boolean invalidated() {
        return invalidated;
    }

    public String getId() {
        return id.value();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    void loadSessionData(String sessionData) {
        session.deserialize(sessionData);
    }

    public String getSessionData() {
        return session.serialize();
    }

    void saved() {
        changed = false;
    }

    void requireNewSessionId() {
        changed = true;
    }
}