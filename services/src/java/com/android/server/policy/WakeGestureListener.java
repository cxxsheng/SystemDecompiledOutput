package com.android.server.policy;

/* loaded from: classes2.dex */
public abstract class WakeGestureListener {
    private static final java.lang.String TAG = "WakeGestureListener";
    private final android.os.Handler mHandler;
    private android.hardware.Sensor mSensor;
    private final android.hardware.SensorManager mSensorManager;
    private boolean mTriggerRequested;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.hardware.TriggerEventListener mListener = new android.hardware.TriggerEventListener() { // from class: com.android.server.policy.WakeGestureListener.1
        @Override // android.hardware.TriggerEventListener
        public void onTrigger(android.hardware.TriggerEvent triggerEvent) {
            synchronized (com.android.server.policy.WakeGestureListener.this.mLock) {
                com.android.server.policy.WakeGestureListener.this.mTriggerRequested = false;
                com.android.server.policy.WakeGestureListener.this.mHandler.post(com.android.server.policy.WakeGestureListener.this.mWakeUpRunnable);
            }
        }
    };
    private final java.lang.Runnable mWakeUpRunnable = new java.lang.Runnable() { // from class: com.android.server.policy.WakeGestureListener.2
        @Override // java.lang.Runnable
        public void run() {
            com.android.server.policy.WakeGestureListener.this.onWakeUp();
        }
    };

    public abstract void onWakeUp();

    public WakeGestureListener(android.content.Context context, android.os.Handler handler) {
        this.mSensorManager = (android.hardware.SensorManager) context.getSystemService("sensor");
        this.mHandler = handler;
        this.mSensor = this.mSensorManager.getDefaultSensor(23);
    }

    public boolean isSupported() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSensor != null;
        }
        return z;
    }

    public void requestWakeUpTrigger() {
        synchronized (this.mLock) {
            try {
                if (this.mSensor != null && !this.mTriggerRequested) {
                    this.mTriggerRequested = true;
                    this.mSensorManager.requestTriggerSensor(this.mListener, this.mSensor);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void cancelWakeUpTrigger() {
        synchronized (this.mLock) {
            try {
                if (this.mSensor != null && this.mTriggerRequested) {
                    this.mTriggerRequested = false;
                    this.mSensorManager.cancelTriggerSensor(this.mListener, this.mSensor);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        synchronized (this.mLock) {
            printWriter.println(str + TAG);
            java.lang.String str2 = str + "  ";
            printWriter.println(str2 + "mTriggerRequested=" + this.mTriggerRequested);
            printWriter.println(str2 + "mSensor=" + this.mSensor);
        }
    }
}
