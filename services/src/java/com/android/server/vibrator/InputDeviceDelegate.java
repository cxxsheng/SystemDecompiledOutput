package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class InputDeviceDelegate implements android.hardware.input.InputManager.InputDeviceListener {
    private static final java.lang.String TAG = "InputDeviceDelegate";
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.hardware.input.InputManager mInputManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mShouldVibrateInputDevices;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.os.VibratorManager> mInputDeviceVibrators = new android.util.SparseArray<>();

    InputDeviceDelegate(android.content.Context context, android.os.Handler handler) {
        this.mHandler = handler;
        this.mContext = context;
    }

    public void onSystemReady() {
        synchronized (this.mLock) {
            this.mInputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceAdded(int i) {
        updateInputDevice(i);
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int i) {
        updateInputDevice(i);
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceRemoved(int i) {
        synchronized (this.mLock) {
            this.mInputDeviceVibrators.remove(i);
        }
    }

    public boolean isAvailable() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mInputDeviceVibrators.size() > 0;
        }
        return z;
    }

    public boolean vibrateIfAvailable(com.android.server.vibrator.Vibration.CallerInfo callerInfo, android.os.CombinedVibration combinedVibration) {
        boolean z;
        synchronized (this.mLock) {
            for (int i = 0; i < this.mInputDeviceVibrators.size(); i++) {
                try {
                    this.mInputDeviceVibrators.valueAt(i).vibrate(callerInfo.uid, callerInfo.opPkg, combinedVibration, callerInfo.reason, callerInfo.attrs);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            z = this.mInputDeviceVibrators.size() > 0;
        }
        return z;
    }

    public boolean cancelVibrateIfAvailable() {
        boolean z;
        synchronized (this.mLock) {
            for (int i = 0; i < this.mInputDeviceVibrators.size(); i++) {
                try {
                    this.mInputDeviceVibrators.valueAt(i).cancel();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            z = this.mInputDeviceVibrators.size() > 0;
        }
        return z;
    }

    public boolean updateInputDeviceVibrators(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mInputManager == null) {
                    return false;
                }
                if (z == this.mShouldVibrateInputDevices) {
                    return false;
                }
                this.mShouldVibrateInputDevices = z;
                this.mInputDeviceVibrators.clear();
                if (z) {
                    this.mInputManager.registerInputDeviceListener(this, this.mHandler);
                    for (int i : this.mInputManager.getInputDeviceIds()) {
                        android.view.InputDevice inputDevice = this.mInputManager.getInputDevice(i);
                        if (inputDevice != null) {
                            android.os.VibratorManager vibratorManager = inputDevice.getVibratorManager();
                            if (vibratorManager.getVibratorIds().length > 0) {
                                this.mInputDeviceVibrators.put(inputDevice.getId(), vibratorManager);
                            }
                        }
                    }
                } else {
                    this.mInputManager.unregisterInputDeviceListener(this);
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void updateInputDevice(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mInputManager == null) {
                    return;
                }
                if (this.mShouldVibrateInputDevices) {
                    android.view.InputDevice inputDevice = this.mInputManager.getInputDevice(i);
                    if (inputDevice == null) {
                        this.mInputDeviceVibrators.remove(i);
                        return;
                    }
                    android.os.VibratorManager vibratorManager = inputDevice.getVibratorManager();
                    if (vibratorManager.getVibratorIds().length > 0) {
                        this.mInputDeviceVibrators.put(inputDevice.getId(), vibratorManager);
                    } else {
                        this.mInputDeviceVibrators.remove(i);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
