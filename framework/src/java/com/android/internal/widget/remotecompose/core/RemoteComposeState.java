package com.android.internal.widget.remotecompose.core;

/* loaded from: classes5.dex */
public class RemoteComposeState {
    private static int sNextId = 42;
    private final com.android.internal.widget.remotecompose.core.operations.utilities.IntMap<java.lang.Object> mIntDataMap = new com.android.internal.widget.remotecompose.core.operations.utilities.IntMap<>();
    private final com.android.internal.widget.remotecompose.core.operations.utilities.IntMap<java.lang.Boolean> mIntWrittenMap = new com.android.internal.widget.remotecompose.core.operations.utilities.IntMap<>();
    private final java.util.HashMap<java.lang.Object, java.lang.Integer> mDataIntMap = new java.util.HashMap<>();

    public java.lang.Object getFromId(int i) {
        return this.mIntDataMap.get(i);
    }

    public boolean containsId(int i) {
        return this.mIntDataMap.get(i) != null;
    }

    public int dataGetId(java.lang.Object obj) {
        java.lang.Integer num = this.mDataIntMap.get(obj);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public int cache(java.lang.Object obj) {
        int nextId = nextId();
        this.mDataIntMap.put(obj, java.lang.Integer.valueOf(nextId));
        this.mIntDataMap.put(nextId, obj);
        return nextId;
    }

    public void cache(int i, java.lang.Object obj) {
        this.mDataIntMap.put(obj, java.lang.Integer.valueOf(i));
        this.mIntDataMap.put(i, obj);
    }

    public boolean wasNotWritten(int i) {
        return !this.mIntWrittenMap.get(i).booleanValue();
    }

    public void markWritten(int i) {
        this.mIntWrittenMap.put(i, true);
    }

    void reset() {
        this.mIntWrittenMap.clear();
    }

    public static int nextId() {
        int i = sNextId;
        sNextId = i + 1;
        return i;
    }

    public static void setNextId(int i) {
        sNextId = i;
    }
}
