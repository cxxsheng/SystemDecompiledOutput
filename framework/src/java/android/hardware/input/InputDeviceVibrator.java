package android.hardware.input;

/* loaded from: classes2.dex */
final class InputDeviceVibrator extends android.os.Vibrator {
    private static final java.lang.String TAG = "InputDeviceVibrator";
    private final int mDeviceId;
    private final android.os.VibratorInfo mVibratorInfo;
    private final android.util.ArrayMap<android.os.Vibrator.OnVibratorStateChangedListener, android.hardware.input.InputDeviceVibrator.OnVibratorStateChangedListenerDelegate> mDelegates = new android.util.ArrayMap<>();
    private final android.hardware.input.InputManagerGlobal mGlobal = android.hardware.input.InputManagerGlobal.getInstance();
    private final android.os.Binder mToken = new android.os.Binder();

    InputDeviceVibrator(int i, int i2) {
        this.mDeviceId = i;
        this.mVibratorInfo = new android.os.VibratorInfo.Builder(i2).setCapabilities(4L).setSupportedEffects(new int[0]).setSupportedBraking(new int[0]).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class OnVibratorStateChangedListenerDelegate extends android.os.IVibratorStateListener.Stub {
        private final java.util.concurrent.Executor mExecutor;
        private final android.os.Vibrator.OnVibratorStateChangedListener mListener;

        OnVibratorStateChangedListenerDelegate(android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener, java.util.concurrent.Executor executor) {
            this.mExecutor = executor;
            this.mListener = onVibratorStateChangedListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVibrating$0(boolean z) {
            this.mListener.onVibratorStateChanged(z);
        }

        @Override // android.os.IVibratorStateListener
        public void onVibrating(final boolean z) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.input.InputDeviceVibrator$OnVibratorStateChangedListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.input.InputDeviceVibrator.OnVibratorStateChangedListenerDelegate.this.lambda$onVibrating$0(z);
                }
            });
        }
    }

    @Override // android.os.Vibrator
    public android.os.VibratorInfo getInfo() {
        return this.mVibratorInfo;
    }

    @Override // android.os.Vibrator
    public boolean hasVibrator() {
        return true;
    }

    @Override // android.os.Vibrator
    public boolean isVibrating() {
        return this.mGlobal.isVibrating(this.mDeviceId);
    }

    @Override // android.os.Vibrator
    public void addVibratorStateListener(android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
        com.android.internal.util.Preconditions.checkNotNull(onVibratorStateChangedListener);
        addVibratorStateListener(android.app.ActivityThread.currentApplication().getMainExecutor(), onVibratorStateChangedListener);
    }

    @Override // android.os.Vibrator
    public void addVibratorStateListener(java.util.concurrent.Executor executor, android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
        com.android.internal.util.Preconditions.checkNotNull(onVibratorStateChangedListener);
        com.android.internal.util.Preconditions.checkNotNull(executor);
        synchronized (this.mDelegates) {
            if (this.mDelegates.containsKey(onVibratorStateChangedListener)) {
                android.util.Log.w(TAG, "Listener already registered.");
                return;
            }
            android.hardware.input.InputDeviceVibrator.OnVibratorStateChangedListenerDelegate onVibratorStateChangedListenerDelegate = new android.hardware.input.InputDeviceVibrator.OnVibratorStateChangedListenerDelegate(onVibratorStateChangedListener, executor);
            if (!this.mGlobal.registerVibratorStateListener(this.mDeviceId, onVibratorStateChangedListenerDelegate)) {
                android.util.Log.w(TAG, "Failed to register vibrate state listener");
            } else {
                this.mDelegates.put(onVibratorStateChangedListener, onVibratorStateChangedListenerDelegate);
            }
        }
    }

    @Override // android.os.Vibrator
    public void removeVibratorStateListener(android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
        com.android.internal.util.Preconditions.checkNotNull(onVibratorStateChangedListener);
        synchronized (this.mDelegates) {
            if (this.mDelegates.containsKey(onVibratorStateChangedListener)) {
                if (!this.mGlobal.unregisterVibratorStateListener(this.mDeviceId, this.mDelegates.get(onVibratorStateChangedListener))) {
                    android.util.Log.w(TAG, "Failed to unregister vibrate state listener");
                    return;
                }
                this.mDelegates.remove(onVibratorStateChangedListener);
            }
        }
    }

    @Override // android.os.Vibrator
    public boolean hasAmplitudeControl() {
        return this.mVibratorInfo.hasCapability(4L);
    }

    @Override // android.os.Vibrator
    public void vibrate(int i, java.lang.String str, android.os.VibrationEffect vibrationEffect, java.lang.String str2, android.os.VibrationAttributes vibrationAttributes) {
        this.mGlobal.vibrate(this.mDeviceId, vibrationEffect, this.mToken);
    }

    @Override // android.os.Vibrator
    public void cancel() {
        this.mGlobal.cancelVibrate(this.mDeviceId, this.mToken);
    }

    @Override // android.os.Vibrator
    public void cancel(int i) {
        cancel();
    }
}
