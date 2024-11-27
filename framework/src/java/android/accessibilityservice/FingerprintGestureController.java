package android.accessibilityservice;

/* loaded from: classes.dex */
public final class FingerprintGestureController {
    public static final int FINGERPRINT_GESTURE_SWIPE_DOWN = 8;
    public static final int FINGERPRINT_GESTURE_SWIPE_LEFT = 2;
    public static final int FINGERPRINT_GESTURE_SWIPE_RIGHT = 1;
    public static final int FINGERPRINT_GESTURE_SWIPE_UP = 4;
    private static final java.lang.String LOG_TAG = "FingerprintGestureController";
    private final android.accessibilityservice.IAccessibilityServiceConnection mAccessibilityServiceConnection;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.ArrayMap<android.accessibilityservice.FingerprintGestureController.FingerprintGestureCallback, android.os.Handler> mCallbackHandlerMap = new android.util.ArrayMap<>(1);

    public FingerprintGestureController(android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection) {
        this.mAccessibilityServiceConnection = iAccessibilityServiceConnection;
    }

    public boolean isGestureDetectionAvailable() {
        try {
            return this.mAccessibilityServiceConnection.isFingerprintGestureDetectionAvailable();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "Failed to check if fingerprint gestures are active", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void registerFingerprintGestureCallback(android.accessibilityservice.FingerprintGestureController.FingerprintGestureCallback fingerprintGestureCallback, android.os.Handler handler) {
        synchronized (this.mLock) {
            this.mCallbackHandlerMap.put(fingerprintGestureCallback, handler);
        }
    }

    public void unregisterFingerprintGestureCallback(android.accessibilityservice.FingerprintGestureController.FingerprintGestureCallback fingerprintGestureCallback) {
        synchronized (this.mLock) {
            this.mCallbackHandlerMap.remove(fingerprintGestureCallback);
        }
    }

    public void onGestureDetectionActiveChanged(final boolean z) {
        android.util.ArrayMap arrayMap;
        synchronized (this.mLock) {
            arrayMap = new android.util.ArrayMap(this.mCallbackHandlerMap);
        }
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            final android.accessibilityservice.FingerprintGestureController.FingerprintGestureCallback fingerprintGestureCallback = (android.accessibilityservice.FingerprintGestureController.FingerprintGestureCallback) arrayMap.keyAt(i);
            android.os.Handler handler = (android.os.Handler) arrayMap.valueAt(i);
            if (handler != null) {
                handler.post(new java.lang.Runnable() { // from class: android.accessibilityservice.FingerprintGestureController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.accessibilityservice.FingerprintGestureController.FingerprintGestureCallback.this.onGestureDetectionAvailabilityChanged(z);
                    }
                });
            } else {
                fingerprintGestureCallback.onGestureDetectionAvailabilityChanged(z);
            }
        }
    }

    public void onGesture(final int i) {
        android.util.ArrayMap arrayMap;
        synchronized (this.mLock) {
            arrayMap = new android.util.ArrayMap(this.mCallbackHandlerMap);
        }
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            final android.accessibilityservice.FingerprintGestureController.FingerprintGestureCallback fingerprintGestureCallback = (android.accessibilityservice.FingerprintGestureController.FingerprintGestureCallback) arrayMap.keyAt(i2);
            android.os.Handler handler = (android.os.Handler) arrayMap.valueAt(i2);
            if (handler != null) {
                handler.post(new java.lang.Runnable() { // from class: android.accessibilityservice.FingerprintGestureController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.accessibilityservice.FingerprintGestureController.FingerprintGestureCallback.this.onGestureDetected(i);
                    }
                });
            } else {
                fingerprintGestureCallback.onGestureDetected(i);
            }
        }
    }

    public static abstract class FingerprintGestureCallback {
        public void onGestureDetectionAvailabilityChanged(boolean z) {
        }

        public void onGestureDetected(int i) {
        }
    }
}
