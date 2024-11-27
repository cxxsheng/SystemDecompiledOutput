package android.os;

/* loaded from: classes3.dex */
public class SystemVibrator extends android.os.Vibrator {
    private static final java.lang.String TAG = "Vibrator";
    private final java.util.ArrayList<android.os.SystemVibrator.MultiVibratorStateListener> mBrokenListeners;
    private final android.content.Context mContext;
    private final java.lang.Object mLock;
    private final android.util.ArrayMap<android.os.Vibrator.OnVibratorStateChangedListener, android.os.SystemVibrator.MultiVibratorStateListener> mRegisteredListeners;
    private android.os.VibratorInfo mVibratorInfo;
    private final android.os.VibratorManager mVibratorManager;

    public SystemVibrator(android.content.Context context) {
        super(context);
        this.mBrokenListeners = new java.util.ArrayList<>();
        this.mRegisteredListeners = new android.util.ArrayMap<>();
        this.mLock = new java.lang.Object();
        this.mContext = context;
        this.mVibratorManager = (android.os.VibratorManager) this.mContext.getSystemService(android.os.VibratorManager.class);
    }

    @Override // android.os.Vibrator
    public android.os.VibratorInfo getInfo() {
        synchronized (this.mLock) {
            if (this.mVibratorInfo != null) {
                return this.mVibratorInfo;
            }
            if (this.mVibratorManager == null) {
                android.util.Log.w(TAG, "Failed to retrieve vibrator info; no vibrator manager.");
                return android.os.VibratorInfo.EMPTY_VIBRATOR_INFO;
            }
            int[] vibratorIds = this.mVibratorManager.getVibratorIds();
            if (vibratorIds.length == 0) {
                android.os.VibratorInfo vibratorInfo = android.os.VibratorInfo.EMPTY_VIBRATOR_INFO;
                this.mVibratorInfo = vibratorInfo;
                return vibratorInfo;
            }
            android.os.VibratorInfo[] vibratorInfoArr = new android.os.VibratorInfo[vibratorIds.length];
            for (int i = 0; i < vibratorIds.length; i++) {
                android.os.Vibrator vibrator = this.mVibratorManager.getVibrator(vibratorIds[i]);
                if (vibrator instanceof android.os.NullVibrator) {
                    android.util.Log.w(TAG, "Vibrator manager service not ready; Info not yet available for vibrator: " + vibratorIds[i]);
                    return android.os.VibratorInfo.EMPTY_VIBRATOR_INFO;
                }
                vibratorInfoArr[i] = vibrator.getInfo();
            }
            android.os.VibratorInfo create = android.os.vibrator.VibratorInfoFactory.create(-1, vibratorInfoArr);
            this.mVibratorInfo = create;
            return create;
        }
    }

    @Override // android.os.Vibrator
    public boolean hasVibrator() {
        if (this.mVibratorManager != null) {
            return this.mVibratorManager.getVibratorIds().length > 0;
        }
        android.util.Log.w(TAG, "Failed to check if vibrator exists; no vibrator manager.");
        return false;
    }

    @Override // android.os.Vibrator
    public boolean isVibrating() {
        if (this.mVibratorManager == null) {
            android.util.Log.w(TAG, "Failed to vibrate; no vibrator manager.");
            return false;
        }
        for (int i : this.mVibratorManager.getVibratorIds()) {
            if (this.mVibratorManager.getVibrator(i).isVibrating()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Vibrator
    public void addVibratorStateListener(android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
        java.util.Objects.requireNonNull(onVibratorStateChangedListener);
        if (this.mContext == null) {
            android.util.Log.w(TAG, "Failed to add vibrate state listener; no vibrator context.");
        } else {
            addVibratorStateListener(this.mContext.getMainExecutor(), onVibratorStateChangedListener);
        }
    }

    @Override // android.os.Vibrator
    public void addVibratorStateListener(java.util.concurrent.Executor executor, android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
        java.util.Objects.requireNonNull(onVibratorStateChangedListener);
        java.util.Objects.requireNonNull(executor);
        if (this.mVibratorManager == null) {
            android.util.Log.w(TAG, "Failed to add vibrate state listener; no vibrator manager.");
            return;
        }
        android.os.SystemVibrator.MultiVibratorStateListener multiVibratorStateListener = null;
        try {
            synchronized (this.mRegisteredListeners) {
                try {
                    if (this.mRegisteredListeners.containsKey(onVibratorStateChangedListener)) {
                        android.util.Log.w(TAG, "Listener already registered.");
                        tryUnregisterBrokenListeners();
                        return;
                    }
                    android.os.SystemVibrator.MultiVibratorStateListener multiVibratorStateListener2 = new android.os.SystemVibrator.MultiVibratorStateListener(executor, onVibratorStateChangedListener);
                    try {
                        multiVibratorStateListener2.register(this.mVibratorManager);
                        this.mRegisteredListeners.put(onVibratorStateChangedListener, multiVibratorStateListener2);
                        tryUnregisterBrokenListeners();
                    } catch (java.lang.Throwable th) {
                        th = th;
                        multiVibratorStateListener = multiVibratorStateListener2;
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                }
            }
        } catch (java.lang.Throwable th3) {
            if (multiVibratorStateListener != null && multiVibratorStateListener.hasRegisteredListeners()) {
                synchronized (this.mBrokenListeners) {
                    this.mBrokenListeners.add(multiVibratorStateListener);
                }
            }
            tryUnregisterBrokenListeners();
            throw th3;
        }
    }

    @Override // android.os.Vibrator
    public void removeVibratorStateListener(android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
        java.util.Objects.requireNonNull(onVibratorStateChangedListener);
        if (this.mVibratorManager == null) {
            android.util.Log.w(TAG, "Failed to remove vibrate state listener; no vibrator manager.");
            return;
        }
        synchronized (this.mRegisteredListeners) {
            if (this.mRegisteredListeners.containsKey(onVibratorStateChangedListener)) {
                this.mRegisteredListeners.get(onVibratorStateChangedListener).unregister(this.mVibratorManager);
                this.mRegisteredListeners.remove(onVibratorStateChangedListener);
            }
        }
        tryUnregisterBrokenListeners();
    }

    @Override // android.os.Vibrator
    public boolean hasAmplitudeControl() {
        return getInfo().hasAmplitudeControl();
    }

    @Override // android.os.Vibrator
    public boolean setAlwaysOnEffect(int i, java.lang.String str, int i2, android.os.VibrationEffect vibrationEffect, android.os.VibrationAttributes vibrationAttributes) {
        if (this.mVibratorManager == null) {
            android.util.Log.w(TAG, "Failed to set always-on effect; no vibrator manager.");
            return false;
        }
        return this.mVibratorManager.setAlwaysOnEffect(i, str, i2, android.os.CombinedVibration.createParallel(vibrationEffect), vibrationAttributes);
    }

    @Override // android.os.Vibrator
    public void vibrate(int i, java.lang.String str, android.os.VibrationEffect vibrationEffect, java.lang.String str2, android.os.VibrationAttributes vibrationAttributes) {
        if (this.mVibratorManager == null) {
            android.util.Log.w(TAG, "Failed to vibrate; no vibrator manager.");
        } else {
            this.mVibratorManager.vibrate(i, str, android.os.CombinedVibration.createParallel(vibrationEffect), str2, vibrationAttributes);
        }
    }

    @Override // android.os.Vibrator
    public void performHapticFeedback(int i, boolean z, java.lang.String str, boolean z2) {
        if (this.mVibratorManager == null) {
            android.util.Log.w(TAG, "Failed to perform haptic feedback; no vibrator manager.");
        } else {
            this.mVibratorManager.performHapticFeedback(i, z, str, z2);
        }
    }

    @Override // android.os.Vibrator
    public void cancel() {
        if (this.mVibratorManager == null) {
            android.util.Log.w(TAG, "Failed to cancel vibrate; no vibrator manager.");
        } else {
            this.mVibratorManager.cancel();
        }
    }

    @Override // android.os.Vibrator
    public void cancel(int i) {
        if (this.mVibratorManager == null) {
            android.util.Log.w(TAG, "Failed to cancel vibrate; no vibrator manager.");
        } else {
            this.mVibratorManager.cancel(i);
        }
    }

    private void tryUnregisterBrokenListeners() {
        synchronized (this.mBrokenListeners) {
            try {
                int size = this.mBrokenListeners.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    }
                    this.mBrokenListeners.get(size).unregister(this.mVibratorManager);
                    this.mBrokenListeners.remove(size);
                }
            } catch (java.lang.RuntimeException e) {
                android.util.Log.w(TAG, "Failed to unregister broken listener", e);
            }
        }
    }

    private static class SingleVibratorStateListener implements android.os.Vibrator.OnVibratorStateChangedListener {
        private final android.os.SystemVibrator.MultiVibratorStateListener mAllVibratorsListener;
        private final int mVibratorIdx;

        SingleVibratorStateListener(android.os.SystemVibrator.MultiVibratorStateListener multiVibratorStateListener, int i) {
            this.mAllVibratorsListener = multiVibratorStateListener;
            this.mVibratorIdx = i;
        }

        @Override // android.os.Vibrator.OnVibratorStateChangedListener
        public void onVibratorStateChanged(boolean z) {
            this.mAllVibratorsListener.onVibrating(this.mVibratorIdx, z);
        }
    }

    public static class MultiVibratorStateListener {
        private final android.os.Vibrator.OnVibratorStateChangedListener mDelegate;
        private final java.util.concurrent.Executor mExecutor;
        private int mInitializedMask;
        private int mVibratingMask;
        private final java.lang.Object mLock = new java.lang.Object();
        private final android.util.SparseArray<android.os.SystemVibrator.SingleVibratorStateListener> mVibratorListeners = new android.util.SparseArray<>();

        public MultiVibratorStateListener(java.util.concurrent.Executor executor, android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
            this.mExecutor = executor;
            this.mDelegate = onVibratorStateChangedListener;
        }

        public boolean hasRegisteredListeners() {
            boolean z;
            synchronized (this.mLock) {
                z = this.mVibratorListeners.size() > 0;
            }
            return z;
        }

        public void register(android.os.VibratorManager vibratorManager) {
            int[] vibratorIds = vibratorManager.getVibratorIds();
            synchronized (this.mLock) {
                for (int i = 0; i < vibratorIds.length; i++) {
                    int i2 = vibratorIds[i];
                    android.os.SystemVibrator.SingleVibratorStateListener singleVibratorStateListener = new android.os.SystemVibrator.SingleVibratorStateListener(this, i);
                    try {
                        vibratorManager.getVibrator(i2).addVibratorStateListener(this.mExecutor, singleVibratorStateListener);
                        this.mVibratorListeners.put(i2, singleVibratorStateListener);
                    } catch (java.lang.RuntimeException e) {
                        try {
                            unregister(vibratorManager);
                        } catch (java.lang.RuntimeException e2) {
                            android.util.Log.w(android.os.SystemVibrator.TAG, "Failed to unregister listener while recovering from a failed register call", e2);
                        }
                        throw e;
                    }
                }
            }
        }

        public void unregister(android.os.VibratorManager vibratorManager) {
            synchronized (this.mLock) {
                int size = this.mVibratorListeners.size();
                while (true) {
                    size--;
                    if (size >= 0) {
                        vibratorManager.getVibrator(this.mVibratorListeners.keyAt(size)).removeVibratorStateListener(this.mVibratorListeners.valueAt(size));
                        this.mVibratorListeners.removeAt(size);
                    }
                }
            }
        }

        public void onVibrating(final int i, final boolean z) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.SystemVibrator$MultiVibratorStateListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.SystemVibrator.MultiVibratorStateListener.this.lambda$onVibrating$0(i, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVibrating$0(int i, boolean z) {
            boolean z2;
            boolean z3;
            synchronized (this.mLock) {
                z2 = true;
                int size = (1 << this.mVibratorListeners.size()) - 1;
                boolean z4 = this.mVibratingMask != 0;
                boolean z5 = this.mInitializedMask == size;
                int i2 = 1 << i;
                this.mInitializedMask |= i2;
                if (((this.mVibratingMask & i2) != 0) != z) {
                    this.mVibratingMask = i2 ^ this.mVibratingMask;
                }
                z3 = this.mVibratingMask != 0;
                boolean z6 = this.mInitializedMask == size;
                boolean z7 = z4 != z3;
                if (!z6 || (z5 && !z7)) {
                    z2 = false;
                }
            }
            if (z2) {
                this.mDelegate.onVibratorStateChanged(z3);
            }
        }
    }
}
