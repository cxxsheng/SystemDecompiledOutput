package android.os;

/* loaded from: classes3.dex */
public class SystemVibratorManager extends android.os.VibratorManager {
    private static final java.lang.String TAG = "VibratorManager";
    private final android.content.Context mContext;
    private final android.util.ArrayMap<android.os.Vibrator.OnVibratorStateChangedListener, android.os.SystemVibratorManager.OnVibratorStateChangedListenerDelegate> mListeners;
    private final java.lang.Object mLock;
    private final android.os.IVibratorManagerService mService;
    private final android.os.Binder mToken;
    private final int mUid;
    private int[] mVibratorIds;
    private final android.util.SparseArray<android.os.Vibrator> mVibrators;

    public SystemVibratorManager(android.content.Context context) {
        super(context);
        this.mToken = new android.os.Binder();
        this.mLock = new java.lang.Object();
        this.mVibrators = new android.util.SparseArray<>();
        this.mListeners = new android.util.ArrayMap<>();
        this.mContext = context;
        this.mUid = android.os.Process.myUid();
        this.mService = android.os.IVibratorManagerService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.VIBRATOR_MANAGER_SERVICE));
    }

    @Override // android.os.VibratorManager
    public int[] getVibratorIds() {
        synchronized (this.mLock) {
            if (this.mVibratorIds != null) {
                return this.mVibratorIds;
            }
            try {
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (this.mService == null) {
                android.util.Log.w(TAG, "Failed to retrieve vibrator ids; no vibrator manager service.");
                return new int[0];
            }
            int[] vibratorIds = this.mService.getVibratorIds();
            this.mVibratorIds = vibratorIds;
            return vibratorIds;
        }
    }

    @Override // android.os.VibratorManager
    public android.os.Vibrator getVibrator(int i) {
        android.os.Vibrator nullVibrator;
        synchronized (this.mLock) {
            android.os.Vibrator vibrator = this.mVibrators.get(i);
            if (vibrator != null) {
                return vibrator;
            }
            android.os.VibratorInfo vibratorInfo = null;
            try {
                if (this.mService == null) {
                    android.util.Log.w(TAG, "Failed to retrieve vibrator; no vibrator manager service.");
                } else {
                    vibratorInfo = this.mService.getVibratorInfo(i);
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (vibratorInfo != null) {
                nullVibrator = new android.os.SystemVibratorManager.SingleVibrator(vibratorInfo);
                this.mVibrators.put(i, nullVibrator);
            } else {
                nullVibrator = android.os.NullVibrator.getInstance();
            }
            return nullVibrator;
        }
    }

    @Override // android.os.VibratorManager
    public android.os.Vibrator getDefaultVibrator() {
        return (android.os.Vibrator) this.mContext.getSystemService(android.os.Vibrator.class);
    }

    @Override // android.os.VibratorManager
    public boolean setAlwaysOnEffect(int i, java.lang.String str, int i2, android.os.CombinedVibration combinedVibration, android.os.VibrationAttributes vibrationAttributes) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to set always-on effect; no vibrator manager service.");
            return false;
        }
        try {
            return this.mService.setAlwaysOnEffect(i, str, i2, combinedVibration, vibrationAttributes);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to set always-on effect.", e);
            return false;
        }
    }

    @Override // android.os.VibratorManager
    public void vibrate(int i, java.lang.String str, android.os.CombinedVibration combinedVibration, java.lang.String str2, android.os.VibrationAttributes vibrationAttributes) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to vibrate; no vibrator manager service.");
            return;
        }
        try {
            this.mService.vibrate(i, this.mContext.getDeviceId(), str, combinedVibration, vibrationAttributes, str2, this.mToken);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to vibrate.", e);
        }
    }

    @Override // android.os.VibratorManager
    public void performHapticFeedback(int i, boolean z, java.lang.String str, boolean z2) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to perform haptic feedback; no vibrator manager service.");
            return;
        }
        try {
            this.mService.performHapticFeedback(this.mUid, this.mContext.getDeviceId(), this.mPackageName, i, z, str, z2);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to perform haptic feedback.", e);
        }
    }

    @Override // android.os.VibratorManager
    public void cancel() {
        cancelVibration(-1);
    }

    @Override // android.os.VibratorManager
    public void cancel(int i) {
        cancelVibration(i);
    }

    private void cancelVibration(int i) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to cancel vibration; no vibrator manager service.");
            return;
        }
        try {
            this.mService.cancelVibrate(i, this.mToken);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to cancel vibration.", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnVibratorStateChangedListenerDelegate extends android.os.IVibratorStateListener.Stub {
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
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.SystemVibratorManager$OnVibratorStateChangedListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.SystemVibratorManager.OnVibratorStateChangedListenerDelegate.this.lambda$onVibrating$0(z);
                }
            });
        }
    }

    private final class SingleVibrator extends android.os.Vibrator {
        private final android.os.VibratorInfo mVibratorInfo;

        SingleVibrator(android.os.VibratorInfo vibratorInfo) {
            this.mVibratorInfo = vibratorInfo;
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
        public boolean hasAmplitudeControl() {
            return this.mVibratorInfo.hasAmplitudeControl();
        }

        @Override // android.os.Vibrator
        public boolean setAlwaysOnEffect(int i, java.lang.String str, int i2, android.os.VibrationEffect vibrationEffect, android.os.VibrationAttributes vibrationAttributes) {
            return android.os.SystemVibratorManager.this.setAlwaysOnEffect(i, str, i2, android.os.CombinedVibration.startParallel().addVibrator(this.mVibratorInfo.getId(), vibrationEffect).combine(), vibrationAttributes);
        }

        @Override // android.os.Vibrator
        public void vibrate(int i, java.lang.String str, android.os.VibrationEffect vibrationEffect, java.lang.String str2, android.os.VibrationAttributes vibrationAttributes) {
            android.os.SystemVibratorManager.this.vibrate(i, str, android.os.CombinedVibration.startParallel().addVibrator(this.mVibratorInfo.getId(), vibrationEffect).combine(), str2, vibrationAttributes);
        }

        @Override // android.os.Vibrator
        public void performHapticFeedback(int i, boolean z, java.lang.String str, boolean z2) {
            android.os.SystemVibratorManager.this.performHapticFeedback(i, z, str, z2);
        }

        @Override // android.os.Vibrator
        public void cancel() {
            android.os.SystemVibratorManager.this.cancel();
        }

        @Override // android.os.Vibrator
        public void cancel(int i) {
            android.os.SystemVibratorManager.this.cancel(i);
        }

        @Override // android.os.Vibrator
        public boolean isVibrating() {
            if (android.os.SystemVibratorManager.this.mService == null) {
                android.util.Log.w(android.os.SystemVibratorManager.TAG, "Failed to check status of vibrator " + this.mVibratorInfo.getId() + "; no vibrator service.");
                return false;
            }
            try {
                return android.os.SystemVibratorManager.this.mService.isVibrating(this.mVibratorInfo.getId());
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                return false;
            }
        }

        @Override // android.os.Vibrator
        public void addVibratorStateListener(android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
            java.util.Objects.requireNonNull(onVibratorStateChangedListener);
            if (android.os.SystemVibratorManager.this.mContext == null) {
                android.util.Log.w(android.os.SystemVibratorManager.TAG, "Failed to add vibrate state listener; no vibrator context.");
            } else {
                addVibratorStateListener(android.os.SystemVibratorManager.this.mContext.getMainExecutor(), onVibratorStateChangedListener);
            }
        }

        @Override // android.os.Vibrator
        public void addVibratorStateListener(java.util.concurrent.Executor executor, android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
            android.os.SystemVibratorManager.OnVibratorStateChangedListenerDelegate onVibratorStateChangedListenerDelegate;
            java.util.Objects.requireNonNull(onVibratorStateChangedListener);
            java.util.Objects.requireNonNull(executor);
            if (android.os.SystemVibratorManager.this.mService == null) {
                android.util.Log.w(android.os.SystemVibratorManager.TAG, "Failed to add vibrate state listener to vibrator " + this.mVibratorInfo.getId() + "; no vibrator service.");
                return;
            }
            synchronized (android.os.SystemVibratorManager.this.mLock) {
                if (android.os.SystemVibratorManager.this.mListeners.containsKey(onVibratorStateChangedListener)) {
                    android.util.Log.w(android.os.SystemVibratorManager.TAG, "Listener already registered.");
                    return;
                }
                try {
                    onVibratorStateChangedListenerDelegate = new android.os.SystemVibratorManager.OnVibratorStateChangedListenerDelegate(onVibratorStateChangedListener, executor);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
                if (!android.os.SystemVibratorManager.this.mService.registerVibratorStateListener(this.mVibratorInfo.getId(), onVibratorStateChangedListenerDelegate)) {
                    android.util.Log.w(android.os.SystemVibratorManager.TAG, "Failed to add vibrate state listener to vibrator " + this.mVibratorInfo.getId());
                } else {
                    android.os.SystemVibratorManager.this.mListeners.put(onVibratorStateChangedListener, onVibratorStateChangedListenerDelegate);
                }
            }
        }

        @Override // android.os.Vibrator
        public void removeVibratorStateListener(android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
            java.util.Objects.requireNonNull(onVibratorStateChangedListener);
            if (android.os.SystemVibratorManager.this.mService == null) {
                android.util.Log.w(android.os.SystemVibratorManager.TAG, "Failed to remove vibrate state listener from vibrator " + this.mVibratorInfo.getId() + "; no vibrator service.");
                return;
            }
            synchronized (android.os.SystemVibratorManager.this.mLock) {
                if (android.os.SystemVibratorManager.this.mListeners.containsKey(onVibratorStateChangedListener)) {
                    try {
                        if (!android.os.SystemVibratorManager.this.mService.unregisterVibratorStateListener(this.mVibratorInfo.getId(), (android.os.SystemVibratorManager.OnVibratorStateChangedListenerDelegate) android.os.SystemVibratorManager.this.mListeners.get(onVibratorStateChangedListener))) {
                            android.util.Log.w(android.os.SystemVibratorManager.TAG, "Failed to remove vibrate state listener from vibrator " + this.mVibratorInfo.getId());
                            return;
                        }
                        android.os.SystemVibratorManager.this.mListeners.remove(onVibratorStateChangedListener);
                    } catch (android.os.RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }
}
