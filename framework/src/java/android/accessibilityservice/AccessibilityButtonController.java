package android.accessibilityservice;

/* loaded from: classes.dex */
public final class AccessibilityButtonController {
    private static final java.lang.String LOG_TAG = "A11yButtonController";
    private android.util.ArrayMap<android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback, android.os.Handler> mCallbacks;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.accessibilityservice.IAccessibilityServiceConnection mServiceConnection;

    AccessibilityButtonController(android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection) {
        this.mServiceConnection = iAccessibilityServiceConnection;
    }

    public boolean isAccessibilityButtonAvailable() {
        if (this.mServiceConnection == null) {
            return false;
        }
        try {
            return this.mServiceConnection.isAccessibilityButtonAvailable();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(LOG_TAG, "Failed to get accessibility button availability.", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void registerAccessibilityButtonCallback(android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback accessibilityButtonCallback) {
        registerAccessibilityButtonCallback(accessibilityButtonCallback, new android.os.Handler(android.os.Looper.getMainLooper()));
    }

    public void registerAccessibilityButtonCallback(android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback accessibilityButtonCallback, android.os.Handler handler) {
        java.util.Objects.requireNonNull(accessibilityButtonCallback);
        java.util.Objects.requireNonNull(handler);
        synchronized (this.mLock) {
            if (this.mCallbacks == null) {
                this.mCallbacks = new android.util.ArrayMap<>();
            }
            this.mCallbacks.put(accessibilityButtonCallback, handler);
        }
    }

    public void unregisterAccessibilityButtonCallback(android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback accessibilityButtonCallback) {
        java.util.Objects.requireNonNull(accessibilityButtonCallback);
        synchronized (this.mLock) {
            if (this.mCallbacks == null) {
                return;
            }
            int indexOfKey = this.mCallbacks.indexOfKey(accessibilityButtonCallback);
            if (indexOfKey >= 0) {
                this.mCallbacks.removeAt(indexOfKey);
            }
        }
    }

    void dispatchAccessibilityButtonClicked() {
        synchronized (this.mLock) {
            if (this.mCallbacks != null && !this.mCallbacks.isEmpty()) {
                android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mCallbacks);
                int size = arrayMap.size();
                for (int i = 0; i < size; i++) {
                    final android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback accessibilityButtonCallback = (android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback) arrayMap.keyAt(i);
                    ((android.os.Handler) arrayMap.valueAt(i)).post(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityButtonController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.accessibilityservice.AccessibilityButtonController.this.lambda$dispatchAccessibilityButtonClicked$0(accessibilityButtonCallback);
                        }
                    });
                }
                return;
            }
            android.util.Slog.w(LOG_TAG, "Received accessibility button click with no callbacks!");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchAccessibilityButtonClicked$0(android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback accessibilityButtonCallback) {
        accessibilityButtonCallback.onClicked(this);
    }

    void dispatchAccessibilityButtonAvailabilityChanged(final boolean z) {
        synchronized (this.mLock) {
            if (this.mCallbacks != null && !this.mCallbacks.isEmpty()) {
                android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mCallbacks);
                int size = arrayMap.size();
                for (int i = 0; i < size; i++) {
                    final android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback accessibilityButtonCallback = (android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback) arrayMap.keyAt(i);
                    ((android.os.Handler) arrayMap.valueAt(i)).post(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityButtonController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.accessibilityservice.AccessibilityButtonController.this.lambda$dispatchAccessibilityButtonAvailabilityChanged$1(accessibilityButtonCallback, z);
                        }
                    });
                }
                return;
            }
            android.util.Slog.w(LOG_TAG, "Received accessibility button availability change with no callbacks!");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchAccessibilityButtonAvailabilityChanged$1(android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback accessibilityButtonCallback, boolean z) {
        accessibilityButtonCallback.onAvailabilityChanged(this, z);
    }

    public static abstract class AccessibilityButtonCallback {
        public void onClicked(android.accessibilityservice.AccessibilityButtonController accessibilityButtonController) {
        }

        public void onAvailabilityChanged(android.accessibilityservice.AccessibilityButtonController accessibilityButtonController, boolean z) {
        }
    }
}
