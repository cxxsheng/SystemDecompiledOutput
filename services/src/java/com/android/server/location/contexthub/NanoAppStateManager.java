package com.android.server.location.contexthub;

/* loaded from: classes2.dex */
class NanoAppStateManager {
    private static final boolean ENABLE_LOG_DEBUG = true;
    private static final java.lang.String TAG = "NanoAppStateManager";
    private final java.util.HashMap<java.lang.Integer, android.hardware.location.NanoAppInstanceInfo> mNanoAppHash = new java.util.HashMap<>();
    private int mNextHandle = 0;

    NanoAppStateManager() {
    }

    @android.annotation.Nullable
    synchronized android.hardware.location.NanoAppInstanceInfo getNanoAppInstanceInfo(int i) {
        return this.mNanoAppHash.get(java.lang.Integer.valueOf(i));
    }

    synchronized void foreachNanoAppInstanceInfo(java.util.function.Consumer<android.hardware.location.NanoAppInstanceInfo> consumer) {
        java.util.Iterator<android.hardware.location.NanoAppInstanceInfo> it = this.mNanoAppHash.values().iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }

    synchronized int getNanoAppHandle(int i, long j) {
        for (android.hardware.location.NanoAppInstanceInfo nanoAppInstanceInfo : this.mNanoAppHash.values()) {
            if (nanoAppInstanceInfo.getContexthubId() == i && nanoAppInstanceInfo.getAppId() == j) {
                return nanoAppInstanceInfo.getHandle();
            }
        }
        return -1;
    }

    synchronized void addNanoAppInstance(int i, long j, int i2) {
        removeNanoAppInstance(i, j);
        if (this.mNanoAppHash.size() == Integer.MAX_VALUE) {
            android.util.Log.e(TAG, "Error adding nanoapp instance: max limit exceeded");
            return;
        }
        int i3 = this.mNextHandle;
        int i4 = 0;
        while (true) {
            if (i4 > Integer.MAX_VALUE) {
                break;
            }
            if (!this.mNanoAppHash.containsKey(java.lang.Integer.valueOf(i3))) {
                this.mNanoAppHash.put(java.lang.Integer.valueOf(i3), new android.hardware.location.NanoAppInstanceInfo(i3, j, i2, i));
                this.mNextHandle = i3 != Integer.MAX_VALUE ? i3 + 1 : 0;
            } else {
                i3 = i3 == Integer.MAX_VALUE ? 0 : i3 + 1;
                i4++;
            }
        }
        android.util.Log.v(TAG, "Added app instance with handle " + i3 + " to hub " + i + ": ID=0x" + java.lang.Long.toHexString(j) + ", version=0x" + java.lang.Integer.toHexString(i2));
    }

    synchronized void removeNanoAppInstance(int i, long j) {
        this.mNanoAppHash.remove(java.lang.Integer.valueOf(getNanoAppHandle(i, j)));
    }

    synchronized void updateCache(int i, java.util.List<android.hardware.location.NanoAppState> list) {
        try {
            java.util.HashSet hashSet = new java.util.HashSet();
            for (android.hardware.location.NanoAppState nanoAppState : list) {
                handleQueryAppEntry(i, nanoAppState.getNanoAppId(), (int) nanoAppState.getNanoAppVersion());
                hashSet.add(java.lang.Long.valueOf(nanoAppState.getNanoAppId()));
            }
            java.util.Iterator<android.hardware.location.NanoAppInstanceInfo> it = this.mNanoAppHash.values().iterator();
            while (it.hasNext()) {
                android.hardware.location.NanoAppInstanceInfo next = it.next();
                if (next.getContexthubId() == i && !hashSet.contains(java.lang.Long.valueOf(next.getAppId()))) {
                    it.remove();
                }
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private void handleQueryAppEntry(int i, long j, int i2) {
        int nanoAppHandle = getNanoAppHandle(i, j);
        if (nanoAppHandle == -1) {
            addNanoAppInstance(i, j, i2);
            return;
        }
        if (this.mNanoAppHash.get(java.lang.Integer.valueOf(nanoAppHandle)).getAppVersion() != i2) {
            this.mNanoAppHash.put(java.lang.Integer.valueOf(nanoAppHandle), new android.hardware.location.NanoAppInstanceInfo(nanoAppHandle, j, i2, i));
            android.util.Log.v(TAG, "Updated app instance with handle " + nanoAppHandle + " at hub " + i + ": ID=0x" + java.lang.Long.toHexString(j) + ", version=0x" + java.lang.Integer.toHexString(i2));
        }
    }
}
