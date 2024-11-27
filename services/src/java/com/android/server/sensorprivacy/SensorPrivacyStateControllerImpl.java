package com.android.server.sensorprivacy;

/* loaded from: classes2.dex */
class SensorPrivacyStateControllerImpl extends com.android.server.sensorprivacy.SensorPrivacyStateController {
    private static final java.lang.String SENSOR_PRIVACY_XML_FILE = "sensor_privacy_impl.xml";
    private static com.android.server.sensorprivacy.SensorPrivacyStateControllerImpl sInstance;
    private com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyListener mListener;
    private android.os.Handler mListenerHandler;
    private com.android.server.sensorprivacy.PersistedState mPersistedState = com.android.server.sensorprivacy.PersistedState.fromFile(SENSOR_PRIVACY_XML_FILE);

    static com.android.server.sensorprivacy.SensorPrivacyStateController getInstance() {
        if (sInstance == null) {
            sInstance = new com.android.server.sensorprivacy.SensorPrivacyStateControllerImpl();
        }
        return sInstance;
    }

    private SensorPrivacyStateControllerImpl() {
        persistAll();
    }

    @Override // com.android.server.sensorprivacy.SensorPrivacyStateController
    com.android.server.sensorprivacy.SensorState getStateLocked(int i, int i2, int i3) {
        com.android.server.sensorprivacy.SensorState state = this.mPersistedState.getState(i, i2, i3);
        if (state != null) {
            return new com.android.server.sensorprivacy.SensorState(state);
        }
        return getDefaultSensorState();
    }

    private static com.android.server.sensorprivacy.SensorState getDefaultSensorState() {
        return new com.android.server.sensorprivacy.SensorState(false);
    }

    @Override // com.android.server.sensorprivacy.SensorPrivacyStateController
    void setStateLocked(int i, int i2, int i3, boolean z, android.os.Handler handler, com.android.server.sensorprivacy.SensorPrivacyStateController.SetStateResultCallback setStateResultCallback) {
        com.android.server.sensorprivacy.SensorState state = this.mPersistedState.getState(i, i2, i3);
        if (state == null) {
            if (!z) {
                com.android.server.sensorprivacy.SensorPrivacyStateController.sendSetStateCallback(handler, setStateResultCallback, false);
                return;
            } else if (z) {
                com.android.server.sensorprivacy.SensorState sensorState = new com.android.server.sensorprivacy.SensorState(true);
                this.mPersistedState.setState(i, i2, i3, sensorState);
                notifyStateChangeLocked(i, i2, i3, sensorState);
                com.android.server.sensorprivacy.SensorPrivacyStateController.sendSetStateCallback(handler, setStateResultCallback, true);
                return;
            }
        }
        if (state.setEnabled(z)) {
            notifyStateChangeLocked(i, i2, i3, state);
            com.android.server.sensorprivacy.SensorPrivacyStateController.sendSetStateCallback(handler, setStateResultCallback, true);
        } else {
            com.android.server.sensorprivacy.SensorPrivacyStateController.sendSetStateCallback(handler, setStateResultCallback, false);
        }
    }

    @Override // com.android.server.sensorprivacy.SensorPrivacyStateController
    void setStateLocked(int i, int i2, int i3, int i4, android.os.Handler handler, com.android.server.sensorprivacy.SensorPrivacyStateController.SetStateResultCallback setStateResultCallback) {
        com.android.server.sensorprivacy.SensorState state = this.mPersistedState.getState(i, i2, i3);
        if (state == null) {
            if (i4 == 2) {
                com.android.server.sensorprivacy.SensorPrivacyStateController.sendSetStateCallback(handler, setStateResultCallback, false);
                return;
            }
            com.android.server.sensorprivacy.SensorState sensorState = new com.android.server.sensorprivacy.SensorState(i4);
            this.mPersistedState.setState(i, i2, i3, sensorState);
            notifyStateChangeLocked(i, i2, i3, sensorState);
            com.android.server.sensorprivacy.SensorPrivacyStateController.sendSetStateCallback(handler, setStateResultCallback, true);
            return;
        }
        if (state.setState(i4)) {
            notifyStateChangeLocked(i, i2, i3, state);
            com.android.server.sensorprivacy.SensorPrivacyStateController.sendSetStateCallback(handler, setStateResultCallback, true);
        } else {
            com.android.server.sensorprivacy.SensorPrivacyStateController.sendSetStateCallback(handler, setStateResultCallback, false);
        }
    }

    private void notifyStateChangeLocked(int i, int i2, int i3, com.android.server.sensorprivacy.SensorState sensorState) {
        if (this.mListenerHandler != null && this.mListener != null) {
            this.mListenerHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.sensorprivacy.SensorPrivacyStateControllerImpl$$ExternalSyntheticLambda1
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyListener) obj).onSensorPrivacyChanged(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), (com.android.server.sensorprivacy.SensorState) obj5);
                }
            }, this.mListener, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), new com.android.server.sensorprivacy.SensorState(sensorState)));
        }
        schedulePersistLocked();
    }

    @Override // com.android.server.sensorprivacy.SensorPrivacyStateController
    void setSensorPrivacyListenerLocked(android.os.Handler handler, com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyListener sensorPrivacyListener) {
        java.util.Objects.requireNonNull(handler);
        java.util.Objects.requireNonNull(sensorPrivacyListener);
        if (this.mListener != null) {
            throw new java.lang.IllegalStateException("Listener is already set");
        }
        this.mListener = sensorPrivacyListener;
        this.mListenerHandler = handler;
    }

    @Override // com.android.server.sensorprivacy.SensorPrivacyStateController
    void schedulePersistLocked() {
        this.mPersistedState.schedulePersist();
    }

    @Override // com.android.server.sensorprivacy.SensorPrivacyStateController
    void forEachStateLocked(final com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyStateConsumer sensorPrivacyStateConsumer) {
        com.android.server.sensorprivacy.PersistedState persistedState = this.mPersistedState;
        java.util.Objects.requireNonNull(sensorPrivacyStateConsumer);
        persistedState.forEachKnownState(new com.android.internal.util.function.QuadConsumer() { // from class: com.android.server.sensorprivacy.SensorPrivacyStateControllerImpl$$ExternalSyntheticLambda0
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyStateConsumer.this.accept(((java.lang.Integer) obj).intValue(), ((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), (com.android.server.sensorprivacy.SensorState) obj4);
            }
        });
    }

    @Override // com.android.server.sensorprivacy.SensorPrivacyStateController
    void resetForTestingImpl() {
        this.mPersistedState.resetForTesting();
        this.mListener = null;
        this.mListenerHandler = null;
        sInstance = null;
    }

    @Override // com.android.server.sensorprivacy.SensorPrivacyStateController
    void dumpLocked(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream) {
        this.mPersistedState.dump(dualDumpOutputStream);
    }
}
