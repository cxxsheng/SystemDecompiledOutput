package android.view;

/* loaded from: classes4.dex */
public final class CrossWindowBlurListeners {
    private static final java.lang.String TAG = "CrossWindowBlurListeners";
    private static volatile android.view.CrossWindowBlurListeners sInstance;
    private boolean mCrossWindowBlurEnabled;
    private static final java.lang.String BLUR_PROPERTY = "ro.surface_flinger.supports_background_blur";
    public static final boolean CROSS_WINDOW_BLUR_SUPPORTED = android.os.SystemProperties.getBoolean(BLUR_PROPERTY, false);
    private static final java.lang.Object sLock = new java.lang.Object();
    private final android.view.CrossWindowBlurListeners.BlurEnabledListenerInternal mListenerInternal = new android.view.CrossWindowBlurListeners.BlurEnabledListenerInternal();
    private final android.util.ArrayMap<java.util.function.Consumer<java.lang.Boolean>, java.util.concurrent.Executor> mListeners = new android.util.ArrayMap<>();
    private final android.os.Handler mMainHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private boolean mInternalListenerAttached = false;

    private CrossWindowBlurListeners() {
    }

    public static android.view.CrossWindowBlurListeners getInstance() {
        android.view.CrossWindowBlurListeners crossWindowBlurListeners = sInstance;
        if (crossWindowBlurListeners == null) {
            synchronized (sLock) {
                crossWindowBlurListeners = sInstance;
                if (crossWindowBlurListeners == null) {
                    crossWindowBlurListeners = new android.view.CrossWindowBlurListeners();
                    sInstance = crossWindowBlurListeners;
                }
            }
        }
        return crossWindowBlurListeners;
    }

    public boolean isCrossWindowBlurEnabled() {
        boolean z;
        synchronized (sLock) {
            attachInternalListenerIfNeededLocked();
            z = this.mCrossWindowBlurEnabled;
        }
        return z;
    }

    public void addListener(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
        com.android.internal.util.Preconditions.checkNotNull(consumer, "listener cannot be null");
        com.android.internal.util.Preconditions.checkNotNull(executor, "executor cannot be null");
        synchronized (sLock) {
            attachInternalListenerIfNeededLocked();
            this.mListeners.put(consumer, executor);
            notifyListener(consumer, executor, this.mCrossWindowBlurEnabled);
        }
    }

    public void removeListener(java.util.function.Consumer<java.lang.Boolean> consumer) {
        com.android.internal.util.Preconditions.checkNotNull(consumer, "listener cannot be null");
        synchronized (sLock) {
            this.mListeners.remove(consumer);
            if (this.mInternalListenerAttached && this.mListeners.size() == 0) {
                try {
                    android.view.WindowManagerGlobal.getWindowManagerService().unregisterCrossWindowBlurEnabledListener(this.mListenerInternal);
                    this.mInternalListenerAttached = false;
                } catch (android.os.RemoteException e) {
                    android.util.Log.d(TAG, "Could not unregister ICrossWindowBlurEnabledListener");
                }
            }
        }
    }

    private void attachInternalListenerIfNeededLocked() {
        if (!this.mInternalListenerAttached) {
            try {
                this.mCrossWindowBlurEnabled = android.view.WindowManagerGlobal.getWindowManagerService().registerCrossWindowBlurEnabledListener(this.mListenerInternal);
                this.mInternalListenerAttached = true;
            } catch (android.os.RemoteException e) {
                android.util.Log.d(TAG, "Could not register ICrossWindowBlurEnabledListener");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyListener(final java.util.function.Consumer<java.lang.Boolean> consumer, java.util.concurrent.Executor executor, final boolean z) {
        executor.execute(new java.lang.Runnable() { // from class: android.view.CrossWindowBlurListeners$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(java.lang.Boolean.valueOf(z));
            }
        });
    }

    private final class BlurEnabledListenerInternal extends android.view.ICrossWindowBlurEnabledListener.Stub {
        private BlurEnabledListenerInternal() {
        }

        @Override // android.view.ICrossWindowBlurEnabledListener
        public void onCrossWindowBlurEnabledChanged(boolean z) {
            synchronized (android.view.CrossWindowBlurListeners.sLock) {
                android.view.CrossWindowBlurListeners.this.mCrossWindowBlurEnabled = z;
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                for (int i = 0; i < android.view.CrossWindowBlurListeners.this.mListeners.size(); i++) {
                    try {
                        android.view.CrossWindowBlurListeners.this.notifyListener((java.util.function.Consumer) android.view.CrossWindowBlurListeners.this.mListeners.keyAt(i), (java.util.concurrent.Executor) android.view.CrossWindowBlurListeners.this.mListeners.valueAt(i), z);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        }
    }
}
