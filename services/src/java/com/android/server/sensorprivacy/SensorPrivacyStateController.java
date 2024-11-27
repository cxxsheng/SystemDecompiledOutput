package com.android.server.sensorprivacy;

/* loaded from: classes2.dex */
abstract class SensorPrivacyStateController {
    private static com.android.server.sensorprivacy.SensorPrivacyStateController sInstance;
    com.android.server.sensorprivacy.AllSensorStateController mAllSensorStateController = com.android.server.sensorprivacy.AllSensorStateController.getInstance();
    private final java.lang.Object mLock = new java.lang.Object();

    interface AllSensorPrivacyListener {
        void onAllSensorPrivacyChanged(boolean z);
    }

    interface SensorPrivacyListener {
        void onSensorPrivacyChanged(int i, int i2, int i3, com.android.server.sensorprivacy.SensorState sensorState);
    }

    interface SensorPrivacyStateConsumer {
        void accept(int i, int i2, int i3, com.android.server.sensorprivacy.SensorState sensorState);
    }

    interface SetStateResultCallback {
        void callback(boolean z);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract void dumpLocked(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract void forEachStateLocked(com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyStateConsumer sensorPrivacyStateConsumer);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract com.android.server.sensorprivacy.SensorState getStateLocked(int i, int i2, int i3);

    abstract void resetForTestingImpl();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract void schedulePersistLocked();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract void setSensorPrivacyListenerLocked(android.os.Handler handler, com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyListener sensorPrivacyListener);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract void setStateLocked(int i, int i2, int i3, int i4, android.os.Handler handler, com.android.server.sensorprivacy.SensorPrivacyStateController.SetStateResultCallback setStateResultCallback);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract void setStateLocked(int i, int i2, int i3, boolean z, android.os.Handler handler, com.android.server.sensorprivacy.SensorPrivacyStateController.SetStateResultCallback setStateResultCallback);

    SensorPrivacyStateController() {
    }

    static com.android.server.sensorprivacy.SensorPrivacyStateController getInstance() {
        if (sInstance == null) {
            sInstance = com.android.server.sensorprivacy.SensorPrivacyStateControllerImpl.getInstance();
        }
        return sInstance;
    }

    com.android.server.sensorprivacy.SensorState getState(int i, int i2, int i3) {
        com.android.server.sensorprivacy.SensorState stateLocked;
        synchronized (this.mLock) {
            stateLocked = getStateLocked(i, i2, i3);
        }
        return stateLocked;
    }

    void setState(int i, int i2, int i3, boolean z, android.os.Handler handler, com.android.server.sensorprivacy.SensorPrivacyStateController.SetStateResultCallback setStateResultCallback) {
        synchronized (this.mLock) {
            setStateLocked(i, i2, i3, z, handler, setStateResultCallback);
        }
    }

    void setState(int i, int i2, int i3, int i4, android.os.Handler handler, com.android.server.sensorprivacy.SensorPrivacyStateController.SetStateResultCallback setStateResultCallback) {
        synchronized (this.mLock) {
            setStateLocked(i, i2, i3, i4, handler, setStateResultCallback);
        }
    }

    void setSensorPrivacyListener(android.os.Handler handler, com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyListener sensorPrivacyListener) {
        synchronized (this.mLock) {
            setSensorPrivacyListenerLocked(handler, sensorPrivacyListener);
        }
    }

    boolean getAllSensorState() {
        boolean allSensorStateLocked;
        synchronized (this.mLock) {
            allSensorStateLocked = this.mAllSensorStateController.getAllSensorStateLocked();
        }
        return allSensorStateLocked;
    }

    void setAllSensorState(boolean z) {
        synchronized (this.mLock) {
            this.mAllSensorStateController.setAllSensorStateLocked(z);
        }
    }

    void setAllSensorPrivacyListener(android.os.Handler handler, com.android.server.sensorprivacy.SensorPrivacyStateController.AllSensorPrivacyListener allSensorPrivacyListener) {
        synchronized (this.mLock) {
            this.mAllSensorStateController.setAllSensorPrivacyListenerLocked(handler, allSensorPrivacyListener);
        }
    }

    void persistAll() {
        synchronized (this.mLock) {
            this.mAllSensorStateController.schedulePersistLocked();
            schedulePersistLocked();
        }
    }

    void forEachState(com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyStateConsumer sensorPrivacyStateConsumer) {
        synchronized (this.mLock) {
            forEachStateLocked(sensorPrivacyStateConsumer);
        }
    }

    void dump(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream) {
        synchronized (this.mLock) {
            this.mAllSensorStateController.dumpLocked(dualDumpOutputStream);
            dumpLocked(dualDumpOutputStream);
        }
        dualDumpOutputStream.flush();
    }

    public void atomic(java.lang.Runnable runnable) {
        synchronized (this.mLock) {
            runnable.run();
        }
    }

    static void sendSetStateCallback(android.os.Handler handler, com.android.server.sensorprivacy.SensorPrivacyStateController.SetStateResultCallback setStateResultCallback, boolean z) {
        handler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.sensorprivacy.SensorPrivacyStateController$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.sensorprivacy.SensorPrivacyStateController.SetStateResultCallback) obj).callback(((java.lang.Boolean) obj2).booleanValue());
            }
        }, setStateResultCallback, java.lang.Boolean.valueOf(z)));
    }

    void resetForTesting() {
        this.mAllSensorStateController.resetForTesting();
        resetForTestingImpl();
        sInstance = null;
    }
}
