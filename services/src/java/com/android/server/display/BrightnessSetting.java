package com.android.server.display;

/* loaded from: classes.dex */
public class BrightnessSetting {
    private static final int MSG_BRIGHTNESS_CHANGED = 1;
    private static final java.lang.String TAG = "BrightnessSetting";

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private float mBrightness;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: com.android.server.display.BrightnessSetting.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (message.what == 1) {
                com.android.server.display.BrightnessSetting.this.notifyListeners(java.lang.Float.intBitsToFloat(message.arg1));
            }
        }
    };
    private final java.util.concurrent.CopyOnWriteArraySet<com.android.server.display.BrightnessSetting.BrightnessSettingListener> mListeners = new java.util.concurrent.CopyOnWriteArraySet<>();
    private final com.android.server.display.LogicalDisplay mLogicalDisplay;
    private final com.android.server.display.PersistentDataStore mPersistentDataStore;
    private final com.android.server.display.DisplayManagerService.SyncRoot mSyncRoot;
    private int mUserSerial;

    public interface BrightnessSettingListener {
        void onBrightnessChanged(float f);
    }

    BrightnessSetting(int i, @android.annotation.NonNull com.android.server.display.PersistentDataStore persistentDataStore, @android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay, com.android.server.display.DisplayManagerService.SyncRoot syncRoot) {
        this.mPersistentDataStore = persistentDataStore;
        this.mLogicalDisplay = logicalDisplay;
        this.mUserSerial = i;
        this.mBrightness = this.mPersistentDataStore.getBrightness(this.mLogicalDisplay.getPrimaryDisplayDeviceLocked(), i);
        this.mSyncRoot = syncRoot;
    }

    public float getBrightness() {
        float f;
        synchronized (this.mSyncRoot) {
            f = this.mBrightness;
        }
        return f;
    }

    public void registerListener(com.android.server.display.BrightnessSetting.BrightnessSettingListener brightnessSettingListener) {
        if (this.mListeners.contains(brightnessSettingListener)) {
            android.util.Slog.wtf(TAG, "Duplicate Listener added");
        }
        this.mListeners.add(brightnessSettingListener);
    }

    public void unregisterListener(com.android.server.display.BrightnessSetting.BrightnessSettingListener brightnessSettingListener) {
        this.mListeners.remove(brightnessSettingListener);
    }

    public void setUserSerial(int i) {
        this.mUserSerial = i;
    }

    public void setBrightness(float f) {
        if (java.lang.Float.isNaN(f)) {
            android.util.Slog.w(TAG, "Attempting to set invalid brightness");
            return;
        }
        synchronized (this.mSyncRoot) {
            try {
                if (f != this.mBrightness) {
                    this.mPersistentDataStore.setBrightness(this.mLogicalDisplay.getPrimaryDisplayDeviceLocked(), f, this.mUserSerial);
                }
                this.mBrightness = f;
                this.mHandler.sendMessage(this.mHandler.obtainMessage(1, java.lang.Float.floatToIntBits(this.mBrightness), 0));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public float getBrightnessNitsForDefaultDisplay() {
        return this.mPersistentDataStore.getBrightnessNitsForDefaultDisplay();
    }

    public void setBrightnessNitsForDefaultDisplay(float f) {
        this.mPersistentDataStore.setBrightnessNitsForDefaultDisplay(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyListeners(float f) {
        java.util.Iterator<com.android.server.display.BrightnessSetting.BrightnessSettingListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onBrightnessChanged(f);
        }
    }
}
