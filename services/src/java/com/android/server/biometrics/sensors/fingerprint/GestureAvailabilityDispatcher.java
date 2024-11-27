package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public class GestureAvailabilityDispatcher {
    private static final java.lang.String TAG = "GestureAvailabilityTracker";
    private boolean mIsActive;
    private final java.util.concurrent.CopyOnWriteArrayList<android.hardware.fingerprint.IFingerprintClientActiveCallback> mClientActiveCallbacks = new java.util.concurrent.CopyOnWriteArrayList<>();
    private final java.util.Map<java.lang.Integer, java.lang.Boolean> mActiveSensors = new java.util.HashMap();

    GestureAvailabilityDispatcher() {
    }

    public boolean isAnySensorActive() {
        return this.mIsActive;
    }

    public void markSensorActive(int i, boolean z) {
        boolean z2;
        this.mActiveSensors.put(java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(z));
        boolean z3 = this.mIsActive;
        java.util.Iterator<java.lang.Boolean> it = this.mActiveSensors.values().iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = false;
                break;
            } else if (it.next().booleanValue()) {
                z2 = true;
                break;
            }
        }
        if (z3 != z2) {
            android.util.Slog.d(TAG, "Notifying gesture availability, active=" + this.mIsActive);
            this.mIsActive = z2;
            notifyClientActiveCallbacks(this.mIsActive);
        }
    }

    void registerCallback(android.hardware.fingerprint.IFingerprintClientActiveCallback iFingerprintClientActiveCallback) {
        this.mClientActiveCallbacks.add(iFingerprintClientActiveCallback);
    }

    void removeCallback(android.hardware.fingerprint.IFingerprintClientActiveCallback iFingerprintClientActiveCallback) {
        this.mClientActiveCallbacks.remove(iFingerprintClientActiveCallback);
    }

    private void notifyClientActiveCallbacks(boolean z) {
        java.util.Iterator<android.hardware.fingerprint.IFingerprintClientActiveCallback> it = this.mClientActiveCallbacks.iterator();
        while (it.hasNext()) {
            android.hardware.fingerprint.IFingerprintClientActiveCallback next = it.next();
            try {
                next.onClientActiveChanged(z);
            } catch (android.os.RemoteException e) {
                this.mClientActiveCallbacks.remove(next);
            }
        }
    }
}
