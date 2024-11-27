package android.media.tv.tuner;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class Lnb implements java.lang.AutoCloseable {
    public static final int EVENT_TYPE_DISEQC_RX_OVERFLOW = 0;
    public static final int EVENT_TYPE_DISEQC_RX_PARITY_ERROR = 2;
    public static final int EVENT_TYPE_DISEQC_RX_TIMEOUT = 1;
    public static final int EVENT_TYPE_LNB_OVERLOAD = 3;
    public static final int POSITION_A = 1;
    public static final int POSITION_B = 2;
    public static final int POSITION_UNDEFINED = 0;
    public static final int TONE_CONTINUOUS = 1;
    public static final int TONE_NONE = 0;
    public static final int VOLTAGE_11V = 2;
    public static final int VOLTAGE_12V = 3;
    public static final int VOLTAGE_13V = 4;
    public static final int VOLTAGE_14V = 5;
    public static final int VOLTAGE_15V = 6;
    public static final int VOLTAGE_18V = 7;
    public static final int VOLTAGE_19V = 8;
    public static final int VOLTAGE_5V = 1;
    public static final int VOLTAGE_NONE = 0;
    int mClientId;
    private long mNativeContext;
    android.media.tv.tuner.Tuner mOwner;
    android.media.tv.tunerresourcemanager.TunerResourceManager mTunerResourceManager;
    private static final java.lang.String TAG = "Lnb";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    java.util.Map<android.media.tv.tuner.LnbCallback, java.util.concurrent.Executor> mCallbackMap = new java.util.HashMap();
    private final java.lang.Object mCallbackLock = new java.lang.Object();
    private java.lang.Boolean mIsClosed = false;
    private final java.lang.Object mLock = new java.lang.Object();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Position {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Tone {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Voltage {
    }

    private native int nativeClose();

    private native int nativeSendDiseqcMessage(byte[] bArr);

    private native int nativeSetSatellitePosition(int i);

    private native int nativeSetTone(int i);

    private native int nativeSetVoltage(int i);

    private Lnb() {
    }

    void setCallbackAndOwner(android.media.tv.tuner.Tuner tuner, java.util.concurrent.Executor executor, android.media.tv.tuner.LnbCallback lnbCallback) {
        synchronized (this.mCallbackLock) {
            if (lnbCallback != null && executor != null) {
                addCallback(executor, lnbCallback);
            }
        }
        setOwner(tuner);
        if (this.mOwner != null) {
            this.mTunerResourceManager = this.mOwner.getTunerResourceManager();
            this.mClientId = this.mOwner.getClientId();
        }
    }

    public void addCallback(java.util.concurrent.Executor executor, android.media.tv.tuner.LnbCallback lnbCallback) {
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(lnbCallback, "callback must not be null");
        synchronized (this.mCallbackLock) {
            this.mCallbackMap.put(lnbCallback, executor);
        }
    }

    public boolean removeCallback(android.media.tv.tuner.LnbCallback lnbCallback) {
        boolean z;
        java.util.Objects.requireNonNull(lnbCallback, "callback must not be null");
        synchronized (this.mCallbackLock) {
            z = this.mCallbackMap.remove(lnbCallback) != null;
        }
        return z;
    }

    void setOwner(android.media.tv.tuner.Tuner tuner) {
        java.util.Objects.requireNonNull(tuner, "newOwner must not be null");
        synchronized (this.mLock) {
            this.mOwner = tuner;
            this.mTunerResourceManager = tuner.getTunerResourceManager();
            this.mClientId = tuner.getClientId();
        }
    }

    private void onEvent(final int i) {
        synchronized (this.mCallbackLock) {
            for (final android.media.tv.tuner.LnbCallback lnbCallback : this.mCallbackMap.keySet()) {
                java.util.concurrent.Executor executor = this.mCallbackMap.get(lnbCallback);
                if (lnbCallback != null && executor != null) {
                    executor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Lnb$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.tuner.Lnb.this.lambda$onEvent$0(lnbCallback, i);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEvent$0(android.media.tv.tuner.LnbCallback lnbCallback, int i) {
        synchronized (this.mCallbackLock) {
            if (lnbCallback != null) {
                lnbCallback.onEvent(i);
            }
        }
    }

    private void onDiseqcMessage(final byte[] bArr) {
        synchronized (this.mCallbackLock) {
            for (final android.media.tv.tuner.LnbCallback lnbCallback : this.mCallbackMap.keySet()) {
                java.util.concurrent.Executor executor = this.mCallbackMap.get(lnbCallback);
                if (lnbCallback != null && executor != null) {
                    executor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Lnb$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.tuner.Lnb.this.lambda$onDiseqcMessage$1(lnbCallback, bArr);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDiseqcMessage$1(android.media.tv.tuner.LnbCallback lnbCallback, byte[] bArr) {
        synchronized (this.mCallbackLock) {
            if (lnbCallback != null) {
                lnbCallback.onDiseqcMessage(bArr);
            }
        }
    }

    boolean isClosed() {
        boolean booleanValue;
        synchronized (this.mLock) {
            booleanValue = this.mIsClosed.booleanValue();
        }
        return booleanValue;
    }

    void closeInternal() {
        synchronized (this.mLock) {
            if (this.mIsClosed.booleanValue()) {
                return;
            }
            int nativeClose = nativeClose();
            if (nativeClose != 0) {
                android.media.tv.tuner.TunerUtils.throwExceptionForResult(nativeClose, "Failed to close LNB");
            } else {
                this.mIsClosed = true;
                if (this.mOwner != null) {
                    this.mOwner.releaseLnb();
                    this.mOwner = null;
                }
                this.mCallbackMap.clear();
            }
        }
    }

    public int setVoltage(int i) {
        int nativeSetVoltage;
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed.booleanValue());
            nativeSetVoltage = nativeSetVoltage(i);
        }
        return nativeSetVoltage;
    }

    public int setTone(int i) {
        int nativeSetTone;
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed.booleanValue());
            nativeSetTone = nativeSetTone(i);
        }
        return nativeSetTone;
    }

    public int setSatellitePosition(int i) {
        int nativeSetSatellitePosition;
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed.booleanValue());
            nativeSetSatellitePosition = nativeSetSatellitePosition(i);
        }
        return nativeSetSatellitePosition;
    }

    public int sendDiseqcMessage(byte[] bArr) {
        int nativeSendDiseqcMessage;
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed.booleanValue());
            nativeSendDiseqcMessage = nativeSendDiseqcMessage(bArr);
        }
        return nativeSendDiseqcMessage;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        acquireTRMSLock("close()");
        try {
            closeInternal();
        } finally {
            releaseTRMSLock();
        }
    }

    private void acquireTRMSLock(java.lang.String str) {
        if (DEBUG) {
            android.util.Log.d(TAG, "ATTEMPT:acquireLock() in " + str + "for clientId:" + this.mClientId);
        }
        if (!this.mTunerResourceManager.acquireLock(this.mClientId)) {
            android.util.Log.e(TAG, "FAILED:acquireLock() in " + str + " for clientId:" + this.mClientId + " - this can cause deadlock between Tuner API calls and onReclaimResources()");
        }
    }

    private void releaseTRMSLock() {
        this.mTunerResourceManager.releaseLock(this.mClientId);
    }
}
