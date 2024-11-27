package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class SensorList<T> {
    private static final java.lang.String TAG = "SensorList";
    private final android.app.IActivityManager mActivityManager;
    private final android.util.SparseArray<T> mSensors = new android.util.SparseArray<>();

    public SensorList(android.app.IActivityManager iActivityManager) {
        this.mActivityManager = iActivityManager;
    }

    public void addSensor(int i, T t, int i2, android.app.SynchronousUserSwitchObserver synchronousUserSwitchObserver) {
        this.mSensors.put(i, t);
        registerUserSwitchObserver(i2, synchronousUserSwitchObserver);
    }

    private void registerUserSwitchObserver(int i, android.app.SynchronousUserSwitchObserver synchronousUserSwitchObserver) {
        try {
            this.mActivityManager.registerUserSwitchObserver(synchronousUserSwitchObserver, TAG);
            if (i == -10000) {
                synchronousUserSwitchObserver.onUserSwitching(0);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to register user switch observer");
        }
    }

    public T valueAt(int i) {
        return this.mSensors.valueAt(i);
    }

    public T get(int i) {
        return this.mSensors.get(i);
    }

    public int keyAt(int i) {
        return this.mSensors.keyAt(i);
    }

    public int size() {
        return this.mSensors.size();
    }

    public boolean contains(int i) {
        return this.mSensors.contains(i);
    }
}
