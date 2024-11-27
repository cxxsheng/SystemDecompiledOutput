package com.android.server.broadcastradio.hal2;

/* loaded from: classes.dex */
class TunerSession extends android.hardware.radio.ITuner.Stub {
    private static final java.lang.String TAG = "BcRadio2Srv.session";
    private static final int TUNER_EVENT_LOGGER_QUEUE_SIZE = 25;
    private static final java.lang.String kAudioDeviceName = "Radio tuner source";
    final android.hardware.radio.ITunerCallback mCallback;

    @android.annotation.NonNull
    private final com.android.server.broadcastradio.hal2.RadioEventLogger mEventLogger;
    private final android.hardware.broadcastradio.V2_0.ITunerSession mHwSession;
    private final com.android.server.broadcastradio.hal2.RadioModule mModule;
    final int mUserId;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsClosed = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsMuted = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.broadcastradio.hal2.ProgramInfoCache mProgramInfoCache = null;
    private android.hardware.radio.RadioManager.BandConfig mDummyConfig = null;

    TunerSession(@android.annotation.NonNull com.android.server.broadcastradio.hal2.RadioModule radioModule, @android.annotation.NonNull android.hardware.broadcastradio.V2_0.ITunerSession iTunerSession, @android.annotation.NonNull android.hardware.radio.ITunerCallback iTunerCallback) {
        java.util.Objects.requireNonNull(radioModule);
        this.mModule = radioModule;
        java.util.Objects.requireNonNull(iTunerSession);
        this.mHwSession = iTunerSession;
        this.mUserId = android.os.Binder.getCallingUserHandle().getIdentifier();
        java.util.Objects.requireNonNull(iTunerCallback);
        this.mCallback = iTunerCallback;
        this.mEventLogger = new com.android.server.broadcastradio.hal2.RadioEventLogger(TAG, 25);
    }

    public void close() {
        this.mEventLogger.logRadioEvent("Close", new java.lang.Object[0]);
        close(null);
    }

    public void close(@android.annotation.Nullable java.lang.Integer num) {
        this.mEventLogger.logRadioEvent("Close on error %d", num);
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    return;
                }
                this.mIsClosed = true;
                if (num != null) {
                    try {
                        this.mCallback.onError(num.intValue());
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "mCallback.onError() failed: ", e);
                    }
                }
                this.mModule.onTunerSessionClosed(this);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isClosed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsClosed;
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void checkNotClosedLocked() {
        if (this.mIsClosed) {
            throw new java.lang.IllegalStateException("Tuner is closed, no further operations are allowed");
        }
    }

    public void setConfiguration(final android.hardware.radio.RadioManager.BandConfig bandConfig) {
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot set configuration for HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            java.util.Objects.requireNonNull(bandConfig);
            android.hardware.radio.RadioManager.BandConfig bandConfig2 = bandConfig;
            this.mDummyConfig = bandConfig;
        }
        android.util.Slog.i(TAG, "Ignoring setConfiguration - not applicable for broadcastradio HAL 2.0");
        this.mModule.fanoutAidlCallback(new com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.hal2.TunerSession$$ExternalSyntheticLambda4
            @Override // com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable
            public final void run(android.hardware.radio.ITunerCallback iTunerCallback) {
                iTunerCallback.onConfigurationChanged(bandConfig);
            }
        });
    }

    public android.hardware.radio.RadioManager.BandConfig getConfiguration() {
        android.hardware.radio.RadioManager.BandConfig bandConfig;
        synchronized (this.mLock) {
            checkNotClosedLocked();
            bandConfig = this.mDummyConfig;
        }
        return bandConfig;
    }

    public void setMuted(boolean z) {
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
        synchronized (this.mLock) {
            checkNotClosedLocked();
            z = this.mIsMuted;
        }
        return z;
    }

    public void step(boolean z, boolean z2) throws android.os.RemoteException {
        this.mEventLogger.logRadioEvent("Step with direction %s, skipSubChannel?  %s", z ? android.net.INetd.IF_STATE_DOWN : android.net.INetd.IF_STATE_UP, z2 ? com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_YES : com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_NO);
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot step on HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            com.android.server.broadcastradio.hal2.Convert.throwOnError("step", this.mHwSession.step(!z));
        }
    }

    public void seek(boolean z, boolean z2) throws android.os.RemoteException {
        this.mEventLogger.logRadioEvent("Seek with direction %s, skipSubChannel? %s", z ? android.net.INetd.IF_STATE_DOWN : android.net.INetd.IF_STATE_UP, z2 ? com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_YES : com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_NO);
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot scan on HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            com.android.server.broadcastradio.hal2.Convert.throwOnError("step", this.mHwSession.scan(!z, z2));
        }
    }

    public void tune(android.hardware.radio.ProgramSelector programSelector) throws android.os.RemoteException {
        this.mEventLogger.logRadioEvent("Tune with selector %s", programSelector);
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot tune on HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            com.android.server.broadcastradio.hal2.Convert.throwOnError("tune", this.mHwSession.tune(com.android.server.broadcastradio.hal2.Convert.programSelectorToHal(programSelector)));
        }
    }

    public void cancel() {
        android.util.Slog.i(TAG, "Cancel");
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot cancel on HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            final android.hardware.broadcastradio.V2_0.ITunerSession iTunerSession = this.mHwSession;
            java.util.Objects.requireNonNull(iTunerSession);
            com.android.server.broadcastradio.hal2.Utils.maybeRethrow(new com.android.server.broadcastradio.hal2.Utils.VoidFuncThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal2.TunerSession$$ExternalSyntheticLambda5
                @Override // com.android.server.broadcastradio.hal2.Utils.VoidFuncThrowingRemoteException
                public final void exec() {
                    android.hardware.broadcastradio.V2_0.ITunerSession.this.cancel();
                }
            });
        }
    }

    public void cancelAnnouncement() {
        android.util.Slog.w(TAG, "Announcements control doesn't involve cancelling at the HAL level in HAL 2.0");
    }

    public android.graphics.Bitmap getImage(int i) {
        this.mEventLogger.logRadioEvent("Get image for %d", java.lang.Integer.valueOf(i));
        return this.mModule.getImage(i);
    }

    public boolean startBackgroundScan() {
        android.util.Slog.w(TAG, "Explicit background scan trigger is not supported with HAL 2.0");
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot start background scan on HAL 2.0 client from non-current user");
            return false;
        }
        this.mModule.fanoutAidlCallback(new com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.hal2.TunerSession$$ExternalSyntheticLambda1
            @Override // com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable
            public final void run(android.hardware.radio.ITunerCallback iTunerCallback) {
                iTunerCallback.onBackgroundScanComplete();
            }
        });
        return true;
    }

    public void startProgramListUpdates(android.hardware.radio.ProgramList.Filter filter) throws android.os.RemoteException {
        this.mEventLogger.logRadioEvent("start programList updates %s", filter);
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot start program list updates on HAL 2.0 client from non-current user");
            return;
        }
        if (filter == null) {
            filter = new android.hardware.radio.ProgramList.Filter(new java.util.HashSet(), new java.util.HashSet(), true, false);
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            this.mProgramInfoCache = new com.android.server.broadcastradio.hal2.ProgramInfoCache(filter);
        }
        this.mModule.onTunerSessionProgramListFilterChanged(this);
    }

    android.hardware.radio.ProgramList.Filter getProgramListFilter() {
        android.hardware.radio.ProgramList.Filter filter;
        synchronized (this.mLock) {
            filter = this.mProgramInfoCache == null ? null : this.mProgramInfoCache.getFilter();
        }
        return filter;
    }

    void onMergedProgramListUpdateFromHal(android.hardware.broadcastradio.V2_0.ProgramListChunk programListChunk) {
        synchronized (this.mLock) {
            try {
                if (this.mProgramInfoCache == null) {
                    return;
                }
                dispatchClientUpdateChunks(this.mProgramInfoCache.filterAndApplyChunk(programListChunk));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void updateProgramInfoFromHalCache(com.android.server.broadcastradio.hal2.ProgramInfoCache programInfoCache) {
        synchronized (this.mLock) {
            try {
                if (this.mProgramInfoCache == null) {
                    return;
                }
                dispatchClientUpdateChunks(this.mProgramInfoCache.filterAndUpdateFrom(programInfoCache, true));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void dispatchClientUpdateChunks(@android.annotation.Nullable java.util.List<android.hardware.radio.ProgramList.Chunk> list) {
        if (list == null) {
            return;
        }
        java.util.Iterator<android.hardware.radio.ProgramList.Chunk> it = list.iterator();
        while (it.hasNext()) {
            try {
                this.mCallback.onProgramListUpdated(it.next());
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "mCallback.onProgramListUpdated() failed: ", e);
            }
        }
    }

    public void stopProgramListUpdates() throws android.os.RemoteException {
        this.mEventLogger.logRadioEvent("Stop programList updates", new java.lang.Object[0]);
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot stop program list updates on HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            this.mProgramInfoCache = null;
        }
        this.mModule.onTunerSessionProgramListFilterChanged(this);
    }

    public boolean isConfigFlagSupported(int i) {
        try {
            isConfigFlagSet(i);
            return true;
        } catch (java.lang.IllegalStateException e) {
            return true;
        } catch (java.lang.UnsupportedOperationException e2) {
            return false;
        }
    }

    public boolean isConfigFlagSet(int i) {
        boolean z;
        this.mEventLogger.logRadioEvent("Is ConfigFlagSet for %s", android.hardware.broadcastradio.V2_0.ConfigFlag.toString(i));
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                final android.util.MutableInt mutableInt = new android.util.MutableInt(1);
                final android.util.MutableBoolean mutableBoolean = new android.util.MutableBoolean(false);
                try {
                    this.mHwSession.isConfigFlagSet(i, new android.hardware.broadcastradio.V2_0.ITunerSession.isConfigFlagSetCallback() { // from class: com.android.server.broadcastradio.hal2.TunerSession$$ExternalSyntheticLambda0
                        @Override // android.hardware.broadcastradio.V2_0.ITunerSession.isConfigFlagSetCallback
                        public final void onValues(int i2, boolean z2) {
                            com.android.server.broadcastradio.hal2.TunerSession.lambda$isConfigFlagSet$2(mutableInt, mutableBoolean, i2, z2);
                        }
                    });
                    com.android.server.broadcastradio.hal2.Convert.throwOnError("isConfigFlagSet", mutableInt.value);
                    z = mutableBoolean.value;
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException("Failed to check flag " + android.hardware.broadcastradio.V2_0.ConfigFlag.toString(i), e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$isConfigFlagSet$2(android.util.MutableInt mutableInt, android.util.MutableBoolean mutableBoolean, int i, boolean z) {
        mutableInt.value = i;
        mutableBoolean.value = z;
    }

    public void setConfigFlag(int i, boolean z) throws android.os.RemoteException {
        this.mEventLogger.logRadioEvent("Set ConfigFlag  %s = %b", android.hardware.broadcastradio.V2_0.ConfigFlag.toString(i), java.lang.Boolean.valueOf(z));
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot set config flag for HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            com.android.server.broadcastradio.hal2.Convert.throwOnError("setConfigFlag", this.mHwSession.setConfigFlag(i, z));
        }
    }

    public java.util.Map<java.lang.String, java.lang.String> setParameters(final java.util.Map<java.lang.String, java.lang.String> map) {
        java.util.Map<java.lang.String, java.lang.String> vendorInfoFromHal;
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot set parameters for HAL 2.0 client from non-current user");
            return new android.util.ArrayMap();
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            vendorInfoFromHal = com.android.server.broadcastradio.hal2.Convert.vendorInfoFromHal((java.util.List) com.android.server.broadcastradio.hal2.Utils.maybeRethrow(new com.android.server.broadcastradio.hal2.Utils.FuncThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal2.TunerSession$$ExternalSyntheticLambda2
                @Override // com.android.server.broadcastradio.hal2.Utils.FuncThrowingRemoteException
                public final java.lang.Object exec() {
                    java.util.ArrayList lambda$setParameters$3;
                    lambda$setParameters$3 = com.android.server.broadcastradio.hal2.TunerSession.this.lambda$setParameters$3(map);
                    return lambda$setParameters$3;
                }
            }));
        }
        return vendorInfoFromHal;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.ArrayList lambda$setParameters$3(java.util.Map map) throws android.os.RemoteException {
        return this.mHwSession.setParameters(com.android.server.broadcastradio.hal2.Convert.vendorInfoToHal(map));
    }

    public java.util.Map<java.lang.String, java.lang.String> getParameters(final java.util.List<java.lang.String> list) {
        java.util.Map<java.lang.String, java.lang.String> vendorInfoFromHal;
        synchronized (this.mLock) {
            checkNotClosedLocked();
            vendorInfoFromHal = com.android.server.broadcastradio.hal2.Convert.vendorInfoFromHal((java.util.List) com.android.server.broadcastradio.hal2.Utils.maybeRethrow(new com.android.server.broadcastradio.hal2.Utils.FuncThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal2.TunerSession$$ExternalSyntheticLambda3
                @Override // com.android.server.broadcastradio.hal2.Utils.FuncThrowingRemoteException
                public final java.lang.Object exec() {
                    java.util.ArrayList lambda$getParameters$4;
                    lambda$getParameters$4 = com.android.server.broadcastradio.hal2.TunerSession.this.lambda$getParameters$4(list);
                    return lambda$getParameters$4;
                }
            }));
        }
        return vendorInfoFromHal;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.ArrayList lambda$getParameters$4(java.util.List list) throws android.os.RemoteException {
        return this.mHwSession.getParameters(com.android.server.broadcastradio.hal2.Convert.listToArrayList(list));
    }

    void dumpInfo(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.printf("TunerSession\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("HIDL HAL Session: %s\n", new java.lang.Object[]{this.mHwSession});
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.printf("Is session closed? %s\n", new java.lang.Object[]{this.mIsClosed ? "Yes" : "No"});
                indentingPrintWriter.printf("Is muted? %s\n", new java.lang.Object[]{this.mIsMuted ? "Yes" : "No"});
                indentingPrintWriter.printf("ProgramInfoCache: %s\n", new java.lang.Object[]{this.mProgramInfoCache});
                indentingPrintWriter.printf("Config: %s\n", new java.lang.Object[]{this.mDummyConfig});
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.printf("Tuner session events:\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        this.mEventLogger.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }
}
