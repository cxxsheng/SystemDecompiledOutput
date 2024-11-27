package com.android.server.broadcastradio.hal1;

/* loaded from: classes.dex */
class Tuner extends android.hardware.radio.ITuner.Stub {
    private static final java.lang.String TAG = "BcRadio1Srv.Tuner";

    @android.annotation.NonNull
    private final android.hardware.radio.ITunerCallback mClientCallback;
    private final long mNativeContext;
    private int mRegion;

    @android.annotation.NonNull
    private final com.android.server.broadcastradio.hal1.TunerCallback mTunerCallback;
    private final boolean mWithAudio;
    private final java.lang.Object mLock = new java.lang.Object();
    private boolean mIsClosed = false;
    private boolean mIsMuted = false;

    @android.annotation.NonNull
    private final android.os.IBinder.DeathRecipient mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.broadcastradio.hal1.Tuner$$ExternalSyntheticLambda0
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            com.android.server.broadcastradio.hal1.Tuner.this.close();
        }
    };

    private native void nativeCancel(long j);

    private native void nativeCancelAnnouncement(long j);

    private native void nativeClose(long j);

    private native void nativeFinalize(long j);

    private native android.hardware.radio.RadioManager.BandConfig nativeGetConfiguration(long j, int i);

    private native byte[] nativeGetImage(long j, int i);

    private native java.util.List<android.hardware.radio.RadioManager.ProgramInfo> nativeGetProgramList(long j, java.util.Map<java.lang.String, java.lang.String> map);

    private native long nativeInit(int i, boolean z, int i2);

    private native boolean nativeIsAnalogForced(long j);

    private native void nativeScan(long j, boolean z, boolean z2);

    private native void nativeSetAnalogForced(long j, boolean z);

    private native void nativeSetConfiguration(long j, @android.annotation.NonNull android.hardware.radio.RadioManager.BandConfig bandConfig);

    private native boolean nativeStartBackgroundScan(long j);

    private native void nativeStep(long j, boolean z, boolean z2);

    private native void nativeTune(long j, @android.annotation.NonNull android.hardware.radio.ProgramSelector programSelector);

    Tuner(@android.annotation.NonNull android.hardware.radio.ITunerCallback iTunerCallback, int i, int i2, boolean z, int i3) {
        this.mClientCallback = iTunerCallback;
        this.mTunerCallback = new com.android.server.broadcastradio.hal1.TunerCallback(this, iTunerCallback, i);
        this.mRegion = i2;
        this.mWithAudio = z;
        this.mNativeContext = nativeInit(i, z, i3);
        try {
            this.mClientCallback.asBinder().linkToDeath(this.mDeathRecipient, 0);
        } catch (android.os.RemoteException e) {
            close();
        }
    }

    protected void finalize() throws java.lang.Throwable {
        nativeFinalize(this.mNativeContext);
        super/*java.lang.Object*/.finalize();
    }

    public void close() {
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    return;
                }
                this.mIsClosed = true;
                this.mTunerCallback.detach();
                this.mClientCallback.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
                nativeClose(this.mNativeContext);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    private void checkNotClosedLocked() {
        if (this.mIsClosed) {
            throw new java.lang.IllegalStateException("Tuner is closed, no further operations are allowed");
        }
    }

    private boolean checkConfiguredLocked() {
        if (this.mTunerCallback.isInitialConfigurationDone()) {
            return true;
        }
        android.util.Slog.w(TAG, "Initial configuration is still pending, skipping the operation");
        return false;
    }

    public void setConfiguration(android.hardware.radio.RadioManager.BandConfig bandConfig) {
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot set configuration for HAL 1.x client from non-current user");
            return;
        }
        if (bandConfig == null) {
            throw new java.lang.IllegalArgumentException("The argument must not be a null pointer");
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            nativeSetConfiguration(this.mNativeContext, bandConfig);
            this.mRegion = bandConfig.getRegion();
        }
    }

    public android.hardware.radio.RadioManager.BandConfig getConfiguration() {
        android.hardware.radio.RadioManager.BandConfig nativeGetConfiguration;
        synchronized (this.mLock) {
            checkNotClosedLocked();
            nativeGetConfiguration = nativeGetConfiguration(this.mNativeContext, this.mRegion);
        }
        return nativeGetConfiguration;
    }

    public void setMuted(boolean z) {
        if (!this.mWithAudio) {
            throw new java.lang.IllegalStateException("Can't operate on mute - no audio requested");
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                if (this.mIsMuted == z) {
                    return;
                }
                this.mIsMuted = z;
                android.util.Slog.w(TAG, "Mute via RadioService is not implemented - please handle it via app");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isMuted() {
        boolean z;
        if (!this.mWithAudio) {
            android.util.Slog.w(TAG, "Tuner did not request audio, pretending it was muted");
            return true;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            z = this.mIsMuted;
        }
        return z;
    }

    public void step(boolean z, boolean z2) {
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot step on HAL 1.x client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                if (checkConfiguredLocked()) {
                    nativeStep(this.mNativeContext, z, z2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void seek(boolean z, boolean z2) {
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot seek on HAL 1.x client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                if (checkConfiguredLocked()) {
                    nativeScan(this.mNativeContext, z, z2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void tune(android.hardware.radio.ProgramSelector programSelector) {
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot tune on HAL 1.x client from non-current user");
            return;
        }
        if (programSelector == null) {
            throw new java.lang.IllegalArgumentException("The argument must not be a null pointer");
        }
        android.util.Slog.i(TAG, "Tuning to " + programSelector);
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                if (checkConfiguredLocked()) {
                    nativeTune(this.mNativeContext, programSelector);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void cancel() {
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot cancel on HAL 1.x client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            nativeCancel(this.mNativeContext);
        }
    }

    public void cancelAnnouncement() {
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot cancel announcement on HAL 1.x client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            nativeCancelAnnouncement(this.mNativeContext);
        }
    }

    public android.graphics.Bitmap getImage(int i) {
        byte[] nativeGetImage;
        if (i == 0) {
            throw new java.lang.IllegalArgumentException("Image ID is missing");
        }
        synchronized (this.mLock) {
            nativeGetImage = nativeGetImage(this.mNativeContext, i);
        }
        if (nativeGetImage == null || nativeGetImage.length == 0) {
            return null;
        }
        return android.graphics.BitmapFactory.decodeByteArray(nativeGetImage, 0, nativeGetImage.length);
    }

    public boolean startBackgroundScan() {
        boolean nativeStartBackgroundScan;
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot start background scan on HAL 1.x client from non-current user");
            return false;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            nativeStartBackgroundScan = nativeStartBackgroundScan(this.mNativeContext);
        }
        return nativeStartBackgroundScan;
    }

    java.util.List<android.hardware.radio.RadioManager.ProgramInfo> getProgramList(java.util.Map map) {
        java.util.List<android.hardware.radio.RadioManager.ProgramInfo> nativeGetProgramList;
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                nativeGetProgramList = nativeGetProgramList(this.mNativeContext, map);
                if (nativeGetProgramList == null) {
                    throw new java.lang.IllegalStateException("Program list is not ready");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return nativeGetProgramList;
    }

    public void startProgramListUpdates(android.hardware.radio.ProgramList.Filter filter) {
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot start program list updates on HAL 1.x client from non-current user");
        } else {
            this.mTunerCallback.startProgramListUpdates(filter);
        }
    }

    public void stopProgramListUpdates() {
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot stop program list updates on HAL 1.x client from non-current user");
        } else {
            this.mTunerCallback.stopProgramListUpdates();
        }
    }

    public boolean isConfigFlagSupported(int i) {
        return i == 2;
    }

    public boolean isConfigFlagSet(int i) {
        boolean nativeIsAnalogForced;
        if (i == 2) {
            synchronized (this.mLock) {
                checkNotClosedLocked();
                nativeIsAnalogForced = nativeIsAnalogForced(this.mNativeContext);
            }
            return nativeIsAnalogForced;
        }
        throw new java.lang.UnsupportedOperationException("Not supported by HAL 1.x");
    }

    public void setConfigFlag(int i, boolean z) {
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot set config flag for HAL 1.x client from non-current user");
        } else {
            if (i == 2) {
                synchronized (this.mLock) {
                    checkNotClosedLocked();
                    nativeSetAnalogForced(this.mNativeContext, z);
                }
                return;
            }
            throw new java.lang.UnsupportedOperationException("Not supported by HAL 1.x");
        }
    }

    public java.util.Map<java.lang.String, java.lang.String> setParameters(java.util.Map<java.lang.String, java.lang.String> map) {
        throw new java.lang.UnsupportedOperationException("Not supported by HAL 1.x");
    }

    public java.util.Map<java.lang.String, java.lang.String> getParameters(java.util.List<java.lang.String> list) {
        throw new java.lang.UnsupportedOperationException("Not supported by HAL 1.x");
    }
}
