package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class PowerManagerWrapper {
    private static final java.lang.String TAG = "PowerManagerWrapper";
    private final android.os.PowerManager mPowerManager;

    public PowerManagerWrapper(android.content.Context context) {
        this.mPowerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
    }

    boolean isInteractive() {
        return this.mPowerManager.isInteractive();
    }

    void wakeUp(long j, int i, java.lang.String str) {
        this.mPowerManager.wakeUp(j, i, str);
    }

    void goToSleep(long j, int i, int i2) {
        this.mPowerManager.goToSleep(j, i, i2);
    }

    com.android.server.hdmi.WakeLockWrapper newWakeLock(int i, java.lang.String str) {
        return new com.android.server.hdmi.PowerManagerWrapper.DefaultWakeLockWrapper(this.mPowerManager.newWakeLock(i, str));
    }

    public static class DefaultWakeLockWrapper implements com.android.server.hdmi.WakeLockWrapper {
        private static final java.lang.String TAG = "DefaultWakeLockWrapper";
        private final android.os.PowerManager.WakeLock mWakeLock;

        private DefaultWakeLockWrapper(android.os.PowerManager.WakeLock wakeLock) {
            this.mWakeLock = wakeLock;
        }

        @Override // com.android.server.hdmi.WakeLockWrapper
        public void acquire(long j) {
            this.mWakeLock.acquire(j);
        }

        @Override // com.android.server.hdmi.WakeLockWrapper
        public void acquire() {
            this.mWakeLock.acquire();
        }

        @Override // com.android.server.hdmi.WakeLockWrapper
        public void release() throws java.lang.RuntimeException {
            this.mWakeLock.release();
        }

        @Override // com.android.server.hdmi.WakeLockWrapper
        public boolean isHeld() {
            return this.mWakeLock.isHeld();
        }

        @Override // com.android.server.hdmi.WakeLockWrapper
        public void setReferenceCounted(boolean z) {
            this.mWakeLock.setReferenceCounted(z);
        }
    }
}
