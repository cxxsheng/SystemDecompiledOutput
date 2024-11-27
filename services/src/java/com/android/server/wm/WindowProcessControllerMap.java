package com.android.server.wm;

/* loaded from: classes3.dex */
final class WindowProcessControllerMap {
    private final android.util.SparseArray<com.android.server.wm.WindowProcessController> mPidMap = new android.util.SparseArray<>();
    private final java.util.Map<java.lang.Integer, android.util.ArraySet<com.android.server.wm.WindowProcessController>> mUidMap = new java.util.HashMap();

    WindowProcessControllerMap() {
    }

    com.android.server.wm.WindowProcessController getProcess(int i) {
        return this.mPidMap.get(i);
    }

    android.util.ArraySet<com.android.server.wm.WindowProcessController> getProcesses(int i) {
        return this.mUidMap.get(java.lang.Integer.valueOf(i));
    }

    android.util.SparseArray<com.android.server.wm.WindowProcessController> getPidMap() {
        return this.mPidMap;
    }

    void put(int i, com.android.server.wm.WindowProcessController windowProcessController) {
        com.android.server.wm.WindowProcessController windowProcessController2 = this.mPidMap.get(i);
        if (windowProcessController2 != null) {
            removeProcessFromUidMap(windowProcessController2);
        }
        this.mPidMap.put(i, windowProcessController);
        int i2 = windowProcessController.mUid;
        android.util.ArraySet<com.android.server.wm.WindowProcessController> orDefault = this.mUidMap.getOrDefault(java.lang.Integer.valueOf(i2), new android.util.ArraySet<>());
        orDefault.add(windowProcessController);
        this.mUidMap.put(java.lang.Integer.valueOf(i2), orDefault);
    }

    void remove(int i) {
        com.android.server.wm.WindowProcessController windowProcessController = this.mPidMap.get(i);
        if (windowProcessController != null) {
            this.mPidMap.remove(i);
            removeProcessFromUidMap(windowProcessController);
            windowProcessController.destroy();
        }
    }

    private void removeProcessFromUidMap(com.android.server.wm.WindowProcessController windowProcessController) {
        if (windowProcessController == null) {
            return;
        }
        int i = windowProcessController.mUid;
        android.util.ArraySet<com.android.server.wm.WindowProcessController> arraySet = this.mUidMap.get(java.lang.Integer.valueOf(i));
        if (arraySet != null) {
            arraySet.remove(windowProcessController);
            if (arraySet.isEmpty()) {
                this.mUidMap.remove(java.lang.Integer.valueOf(i));
            }
        }
    }
}
