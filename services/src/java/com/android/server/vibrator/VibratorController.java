package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class VibratorController {
    private static final java.lang.String TAG = "VibratorController";
    private volatile float mCurrentAmplitude;
    private volatile boolean mIsUnderExternalControl;
    private volatile boolean mIsVibrating;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.vibrator.VibratorController.NativeWrapper mNativeWrapper;
    private volatile android.os.VibratorInfo mVibratorInfo;
    private volatile boolean mVibratorInfoLoadSuccessful;
    private final android.os.RemoteCallbackList<android.os.IVibratorStateListener> mVibratorStateListeners;

    public interface OnVibrationCompleteListener {
        void onComplete(int i, long j);
    }

    VibratorController(int i, com.android.server.vibrator.VibratorController.OnVibrationCompleteListener onVibrationCompleteListener) {
        this(i, onVibrationCompleteListener, new com.android.server.vibrator.VibratorController.NativeWrapper());
    }

    @com.android.internal.annotations.VisibleForTesting
    VibratorController(int i, com.android.server.vibrator.VibratorController.OnVibrationCompleteListener onVibrationCompleteListener, com.android.server.vibrator.VibratorController.NativeWrapper nativeWrapper) {
        this.mLock = new java.lang.Object();
        this.mVibratorStateListeners = new android.os.RemoteCallbackList<>();
        this.mNativeWrapper = nativeWrapper;
        this.mNativeWrapper.init(i, onVibrationCompleteListener);
        android.os.VibratorInfo.Builder builder = new android.os.VibratorInfo.Builder(i);
        this.mVibratorInfoLoadSuccessful = this.mNativeWrapper.getInfo(builder);
        this.mVibratorInfo = builder.build();
        if (!this.mVibratorInfoLoadSuccessful) {
            android.util.Slog.e(TAG, "Vibrator controller initialization failed to load some HAL info for vibrator " + i);
        }
    }

    public boolean registerVibratorStateListener(android.os.IVibratorStateListener iVibratorStateListener) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (this.mVibratorStateListeners.register(iVibratorStateListener)) {
                    lambda$notifyListenerOnVibrating$0(iVibratorStateListener, this.mIsVibrating);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean unregisterVibratorStateListener(android.os.IVibratorStateListener iVibratorStateListener) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mVibratorStateListeners.unregister(iVibratorStateListener);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void reloadVibratorInfoIfNeeded() {
        if (this.mVibratorInfoLoadSuccessful) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (this.mVibratorInfoLoadSuccessful) {
                    return;
                }
                int id = this.mVibratorInfo.getId();
                android.os.VibratorInfo.Builder builder = new android.os.VibratorInfo.Builder(id);
                this.mVibratorInfoLoadSuccessful = this.mNativeWrapper.getInfo(builder);
                this.mVibratorInfo = builder.build();
                if (!this.mVibratorInfoLoadSuccessful) {
                    android.util.Slog.e(TAG, "Failed retry of HAL getInfo for vibrator " + id);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isVibratorInfoLoadSuccessful() {
        return this.mVibratorInfoLoadSuccessful;
    }

    public android.os.VibratorInfo getVibratorInfo() {
        return this.mVibratorInfo;
    }

    public boolean isVibrating() {
        return this.mIsVibrating;
    }

    public float getCurrentAmplitude() {
        return this.mCurrentAmplitude;
    }

    public boolean isUnderExternalControl() {
        return this.mIsUnderExternalControl;
    }

    public boolean hasCapability(long j) {
        return this.mVibratorInfo.hasCapability(j);
    }

    public boolean isAvailable() {
        boolean isAvailable;
        synchronized (this.mLock) {
            isAvailable = this.mNativeWrapper.isAvailable();
        }
        return isAvailable;
    }

    public void setExternalControl(boolean z) {
        if (!this.mVibratorInfo.hasCapability(8L)) {
            return;
        }
        synchronized (this.mLock) {
            this.mIsUnderExternalControl = z;
            this.mNativeWrapper.setExternalControl(z);
        }
    }

    public void updateAlwaysOn(int i, @android.annotation.Nullable android.os.vibrator.PrebakedSegment prebakedSegment) {
        if (!this.mVibratorInfo.hasCapability(64L)) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (prebakedSegment == null) {
                    this.mNativeWrapper.alwaysOnDisable(i);
                } else {
                    this.mNativeWrapper.alwaysOnEnable(i, prebakedSegment.getEffectId(), prebakedSegment.getEffectStrength());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setAmplitude(float f) {
        synchronized (this.mLock) {
            try {
                if (this.mVibratorInfo.hasCapability(4L)) {
                    this.mNativeWrapper.setAmplitude(f);
                }
                if (this.mIsVibrating) {
                    this.mCurrentAmplitude = f;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public long on(long j, long j2) {
        long on;
        synchronized (this.mLock) {
            try {
                on = this.mNativeWrapper.on(j, j2);
                if (on > 0) {
                    this.mCurrentAmplitude = -1.0f;
                    notifyListenerOnVibrating(true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return on;
    }

    public long on(android.os.vibrator.PrebakedSegment prebakedSegment, long j) {
        long perform;
        synchronized (this.mLock) {
            try {
                perform = this.mNativeWrapper.perform(prebakedSegment.getEffectId(), prebakedSegment.getEffectStrength(), j);
                if (perform > 0) {
                    this.mCurrentAmplitude = -1.0f;
                    notifyListenerOnVibrating(true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return perform;
    }

    public long on(android.os.vibrator.PrimitiveSegment[] primitiveSegmentArr, long j) {
        long compose;
        if (!this.mVibratorInfo.hasCapability(32L)) {
            return 0L;
        }
        synchronized (this.mLock) {
            try {
                compose = this.mNativeWrapper.compose(primitiveSegmentArr, j);
                if (compose > 0) {
                    this.mCurrentAmplitude = -1.0f;
                    notifyListenerOnVibrating(true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return compose;
    }

    public long on(android.os.vibrator.RampSegment[] rampSegmentArr, long j) {
        long composePwle;
        if (!this.mVibratorInfo.hasCapability(1024L)) {
            return 0L;
        }
        synchronized (this.mLock) {
            try {
                composePwle = this.mNativeWrapper.composePwle(rampSegmentArr, this.mVibratorInfo.getDefaultBraking(), j);
                if (composePwle > 0) {
                    this.mCurrentAmplitude = -1.0f;
                    notifyListenerOnVibrating(true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return composePwle;
    }

    public void off() {
        synchronized (this.mLock) {
            this.mNativeWrapper.off();
            this.mCurrentAmplitude = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            notifyListenerOnVibrating(false);
        }
    }

    public void reset() {
        setExternalControl(false);
        off();
    }

    public java.lang.String toString() {
        return "VibratorController{mVibratorInfo=" + this.mVibratorInfo + ", mVibratorInfoLoadSuccessful=" + this.mVibratorInfoLoadSuccessful + ", mIsVibrating=" + this.mIsVibrating + ", mCurrentAmplitude=" + this.mCurrentAmplitude + ", mIsUnderExternalControl=" + this.mIsUnderExternalControl + ", mVibratorStateListeners count=" + this.mVibratorStateListeners.getRegisteredCallbackCount() + '}';
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Vibrator (id=" + this.mVibratorInfo.getId() + "):");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("isVibrating = " + this.mIsVibrating);
        indentingPrintWriter.println("isUnderExternalControl = " + this.mIsUnderExternalControl);
        indentingPrintWriter.println("currentAmplitude = " + this.mCurrentAmplitude);
        indentingPrintWriter.println("vibratorInfoLoadSuccessful = " + this.mVibratorInfoLoadSuccessful);
        indentingPrintWriter.println("vibratorStateListener size = " + this.mVibratorStateListeners.getRegisteredCallbackCount());
        this.mVibratorInfo.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyListenerOnVibrating(final boolean z) {
        if (this.mIsVibrating != z) {
            this.mIsVibrating = z;
            this.mVibratorStateListeners.broadcast(new java.util.function.Consumer() { // from class: com.android.server.vibrator.VibratorController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.vibrator.VibratorController.this.lambda$notifyListenerOnVibrating$0(z, (android.os.IVibratorStateListener) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: notifyStateListener, reason: merged with bridge method [inline-methods] */
    public void lambda$notifyListenerOnVibrating$0(android.os.IVibratorStateListener iVibratorStateListener, boolean z) {
        try {
            iVibratorStateListener.onVibrating(z);
        } catch (android.os.RemoteException | java.lang.RuntimeException e) {
            android.util.Slog.e(TAG, "Vibrator state listener failed to call", e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class NativeWrapper {
        private long mNativePtr = 0;

        private static native void alwaysOnDisable(long j, long j2);

        private static native void alwaysOnEnable(long j, long j2, long j3, long j4);

        private static native boolean getInfo(long j, android.os.VibratorInfo.Builder builder);

        private static native long getNativeFinalizer();

        private static native boolean isAvailable(long j);

        private static native long nativeInit(int i, com.android.server.vibrator.VibratorController.OnVibrationCompleteListener onVibrationCompleteListener);

        private static native void off(long j);

        private static native long on(long j, long j2, long j3);

        private static native long performComposedEffect(long j, android.os.vibrator.PrimitiveSegment[] primitiveSegmentArr, long j2);

        private static native long performEffect(long j, long j2, long j3, long j4);

        private static native long performPwleEffect(long j, android.os.vibrator.RampSegment[] rampSegmentArr, int i, long j2);

        private static native void setAmplitude(long j, float f);

        private static native void setExternalControl(long j, boolean z);

        public void init(int i, com.android.server.vibrator.VibratorController.OnVibrationCompleteListener onVibrationCompleteListener) {
            this.mNativePtr = nativeInit(i, onVibrationCompleteListener);
            long nativeFinalizer = getNativeFinalizer();
            if (nativeFinalizer != 0) {
                libcore.util.NativeAllocationRegistry.createMalloced(com.android.server.vibrator.VibratorController.class.getClassLoader(), nativeFinalizer).registerNativeAllocation(this, this.mNativePtr);
            }
        }

        public boolean isAvailable() {
            return isAvailable(this.mNativePtr);
        }

        public long on(long j, long j2) {
            return on(this.mNativePtr, j, j2);
        }

        public void off() {
            off(this.mNativePtr);
        }

        public void setAmplitude(float f) {
            setAmplitude(this.mNativePtr, f);
        }

        public long perform(long j, long j2, long j3) {
            return performEffect(this.mNativePtr, j, j2, j3);
        }

        public long compose(android.os.vibrator.PrimitiveSegment[] primitiveSegmentArr, long j) {
            return performComposedEffect(this.mNativePtr, primitiveSegmentArr, j);
        }

        public long composePwle(android.os.vibrator.RampSegment[] rampSegmentArr, int i, long j) {
            return performPwleEffect(this.mNativePtr, rampSegmentArr, i, j);
        }

        public void setExternalControl(boolean z) {
            setExternalControl(this.mNativePtr, z);
        }

        public void alwaysOnEnable(long j, long j2, long j3) {
            alwaysOnEnable(this.mNativePtr, j, j2, j3);
        }

        public void alwaysOnDisable(long j) {
            alwaysOnDisable(this.mNativePtr, j);
        }

        public boolean getInfo(android.os.VibratorInfo.Builder builder) {
            return getInfo(this.mNativePtr, builder);
        }
    }
}
