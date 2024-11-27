package com.android.server.broadcastradio.hal1;

/* loaded from: classes.dex */
public class BroadcastRadioService {
    private static final java.lang.String TAG = "BcRadio1Srv";
    private final long mNativeContext = nativeInit();
    private final java.lang.Object mLock = new java.lang.Object();

    private native void nativeFinalize(long j);

    private native long nativeInit();

    private native java.util.List<android.hardware.radio.RadioManager.ModuleProperties> nativeLoadModules(long j);

    private native com.android.server.broadcastradio.hal1.Tuner nativeOpenTuner(long j, int i, android.hardware.radio.RadioManager.BandConfig bandConfig, boolean z, android.hardware.radio.ITunerCallback iTunerCallback);

    protected void finalize() throws java.lang.Throwable {
        nativeFinalize(this.mNativeContext);
        super.finalize();
    }

    @android.annotation.NonNull
    public java.util.List<android.hardware.radio.RadioManager.ModuleProperties> loadModules() {
        java.util.List<android.hardware.radio.RadioManager.ModuleProperties> list;
        synchronized (this.mLock) {
            java.util.List<android.hardware.radio.RadioManager.ModuleProperties> nativeLoadModules = nativeLoadModules(this.mNativeContext);
            java.util.Objects.requireNonNull(nativeLoadModules);
            list = nativeLoadModules;
        }
        return list;
    }

    public android.hardware.radio.ITuner openTuner(int i, android.hardware.radio.RadioManager.BandConfig bandConfig, boolean z, android.hardware.radio.ITunerCallback iTunerCallback) {
        com.android.server.broadcastradio.hal1.Tuner nativeOpenTuner;
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.e(TAG, "Cannot open tuner on HAL 1.x client for non-current user");
            throw new java.lang.IllegalStateException("Cannot open tuner for non-current user");
        }
        synchronized (this.mLock) {
            nativeOpenTuner = nativeOpenTuner(this.mNativeContext, i, bandConfig, z, iTunerCallback);
        }
        return nativeOpenTuner;
    }
}
