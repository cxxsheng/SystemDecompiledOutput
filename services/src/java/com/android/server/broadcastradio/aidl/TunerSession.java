package com.android.server.broadcastradio.aidl;

/* loaded from: classes.dex */
final class TunerSession extends android.hardware.radio.ITuner.Stub {
    private static final java.lang.String TAG = "BcRadioAidlSrv.session";
    private static final int TUNER_EVENT_LOGGER_QUEUE_SIZE = 25;
    final android.hardware.radio.ITunerCallback mCallback;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsClosed;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsMuted;
    private final java.lang.Object mLock = new java.lang.Object();
    private final com.android.server.broadcastradio.aidl.RadioLogger mLogger;
    private final com.android.server.broadcastradio.aidl.RadioModule mModule;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.hardware.radio.RadioManager.BandConfig mPlaceHolderConfig;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.broadcastradio.aidl.ProgramInfoCache mProgramInfoCache;
    private final android.hardware.broadcastradio.IBroadcastRadio mService;
    private final int mUid;
    final int mUserId;

    TunerSession(com.android.server.broadcastradio.aidl.RadioModule radioModule, android.hardware.broadcastradio.IBroadcastRadio iBroadcastRadio, android.hardware.radio.ITunerCallback iTunerCallback) {
        java.util.Objects.requireNonNull(radioModule, "radioModule cannot be null");
        this.mModule = radioModule;
        java.util.Objects.requireNonNull(iBroadcastRadio, "service cannot be null");
        this.mService = iBroadcastRadio;
        this.mUserId = android.os.Binder.getCallingUserHandle().getIdentifier();
        java.util.Objects.requireNonNull(iTunerCallback, "callback cannot be null");
        this.mCallback = iTunerCallback;
        this.mUid = android.os.Binder.getCallingUid();
        this.mLogger = new com.android.server.broadcastradio.aidl.RadioLogger(TAG, 25);
    }

    public void close() {
        this.mLogger.logRadioEvent("Close tuner", new java.lang.Object[0]);
        close(null);
    }

    public void close(@android.annotation.Nullable java.lang.Integer num) {
        if (num == null) {
            this.mLogger.logRadioEvent("Close tuner session on error null", new java.lang.Object[0]);
        } else {
            this.mLogger.logRadioEvent("Close tuner session on error %d", num);
        }
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
                        com.android.server.utils.Slogf.w(TAG, e, "mCallback.onError(%s) failed", num);
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
            com.android.server.utils.Slogf.w(TAG, "Cannot set configuration for AIDL HAL client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            java.util.Objects.requireNonNull(bandConfig, "config cannot be null");
            android.hardware.radio.RadioManager.BandConfig bandConfig2 = bandConfig;
            this.mPlaceHolderConfig = bandConfig;
        }
        com.android.server.utils.Slogf.i(TAG, "Ignoring setConfiguration - not applicable for broadcastradio HAL AIDL");
        this.mModule.fanoutAidlCallback(new com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.aidl.TunerSession$$ExternalSyntheticLambda1
            @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
            public final void run(android.hardware.radio.ITunerCallback iTunerCallback, int i) {
                iTunerCallback.onConfigurationChanged(bandConfig);
            }
        });
    }

    public android.hardware.radio.RadioManager.BandConfig getConfiguration() {
        android.hardware.radio.RadioManager.BandConfig bandConfig;
        synchronized (this.mLock) {
            checkNotClosedLocked();
            bandConfig = this.mPlaceHolderConfig;
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
                com.android.server.utils.Slogf.w(TAG, "Mute %b via RadioService is not implemented - please handle it via app", java.lang.Boolean.valueOf(z));
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
        this.mLogger.logRadioEvent("Step with direction %s, skipSubChannel?  %s", z ? android.net.INetd.IF_STATE_DOWN : android.net.INetd.IF_STATE_UP, z2 ? com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_YES : com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_NO);
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot step on AIDL HAL client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    this.mService.step(!z);
                } catch (java.lang.RuntimeException e) {
                    throw com.android.server.broadcastradio.aidl.ConversionUtils.throwOnError(e, "step");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void seek(boolean z, boolean z2) throws android.os.RemoteException {
        this.mLogger.logRadioEvent("Seek with direction %s, skipSubChannel? %s", z ? android.net.INetd.IF_STATE_DOWN : android.net.INetd.IF_STATE_UP, z2 ? com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_YES : com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_NO);
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot scan on AIDL HAL client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    this.mService.seek(!z, z2);
                } catch (java.lang.RuntimeException e) {
                    throw com.android.server.broadcastradio.aidl.ConversionUtils.throwOnError(e, "seek");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void tune(android.hardware.radio.ProgramSelector programSelector) throws android.os.RemoteException {
        this.mLogger.logRadioEvent("Tune with selector %s", programSelector);
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot tune on AIDL HAL client from non-current user");
            return;
        }
        android.hardware.broadcastradio.ProgramSelector programSelectorToHalProgramSelector = com.android.server.broadcastradio.aidl.ConversionUtils.programSelectorToHalProgramSelector(programSelector);
        if (programSelectorToHalProgramSelector == null) {
            throw new java.lang.IllegalArgumentException("tune: INVALID_ARGUMENTS for program selector");
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    this.mService.tune(programSelectorToHalProgramSelector);
                } catch (java.lang.RuntimeException e) {
                    throw com.android.server.broadcastradio.aidl.ConversionUtils.throwOnError(e, "tune");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void cancel() {
        com.android.server.utils.Slogf.i(TAG, "Cancel");
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot cancel on AIDL HAL client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    this.mService.cancel();
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(TAG, "Failed to cancel tuner session");
                    throw e.rethrowFromSystemServer();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void cancelAnnouncement() {
        com.android.server.utils.Slogf.w(TAG, "Announcements control doesn't involve cancelling at the HAL level in AIDL");
    }

    public android.graphics.Bitmap getImage(int i) {
        this.mLogger.logRadioEvent("Get image for %d", java.lang.Integer.valueOf(i));
        return this.mModule.getImage(i);
    }

    public boolean startBackgroundScan() {
        com.android.server.utils.Slogf.w(TAG, "Explicit background scan trigger is not supported with HAL AIDL");
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot start background scan on AIDL HAL client from non-current user");
            return false;
        }
        this.mModule.fanoutAidlCallback(new com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.aidl.TunerSession$$ExternalSyntheticLambda0
            @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
            public final void run(android.hardware.radio.ITunerCallback iTunerCallback, int i) {
                iTunerCallback.onBackgroundScanComplete();
            }
        });
        return true;
    }

    public void startProgramListUpdates(android.hardware.radio.ProgramList.Filter filter) throws android.os.RemoteException {
        this.mLogger.logRadioEvent("Start programList updates %s", filter);
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot start program list updates on AIDL HAL client from non-current user");
            return;
        }
        if (filter == null) {
            filter = new android.hardware.radio.ProgramList.Filter(new android.util.ArraySet(), new android.util.ArraySet(), true, false);
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            this.mProgramInfoCache = new com.android.server.broadcastradio.aidl.ProgramInfoCache(filter);
        }
        this.mModule.onTunerSessionProgramListFilterChanged(this);
    }

    int getUid() {
        return this.mUid;
    }

    android.hardware.radio.ProgramList.Filter getProgramListFilter() {
        android.hardware.radio.ProgramList.Filter filter;
        synchronized (this.mLock) {
            filter = this.mProgramInfoCache == null ? null : this.mProgramInfoCache.getFilter();
        }
        return filter;
    }

    void onMergedProgramListUpdateFromHal(android.hardware.broadcastradio.ProgramListChunk programListChunk) {
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

    void updateProgramInfoFromHalCache(com.android.server.broadcastradio.aidl.ProgramInfoCache programInfoCache) {
        synchronized (this.mLock) {
            try {
                if (this.mProgramInfoCache == null) {
                    return;
                }
                dispatchClientUpdateChunks(this.mProgramInfoCache.filterAndUpdateFromInternal(programInfoCache, true));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void dispatchClientUpdateChunks(@android.annotation.Nullable java.util.List<android.hardware.radio.ProgramList.Chunk> list) {
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            try {
                if (!com.android.server.broadcastradio.aidl.ConversionUtils.isAtLeastU(getUid())) {
                    this.mCallback.onProgramListUpdated(com.android.server.broadcastradio.aidl.ConversionUtils.convertChunkToTargetSdkVersion(list.get(i), getUid()));
                } else {
                    this.mCallback.onProgramListUpdated(list.get(i));
                }
            } catch (android.os.RemoteException e) {
                com.android.server.utils.Slogf.w(TAG, e, "mCallback.onProgramListUpdated() failed", new java.lang.Object[0]);
            }
        }
    }

    public void stopProgramListUpdates() throws android.os.RemoteException {
        this.mLogger.logRadioEvent("Stop programList updates", new java.lang.Object[0]);
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot stop program list updates on AIDL HAL client from non-current user");
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
        } catch (java.lang.IllegalStateException | java.lang.UnsupportedOperationException e) {
            return false;
        }
    }

    public boolean isConfigFlagSet(int i) {
        boolean isConfigFlagSet;
        this.mLogger.logRadioEvent("is ConfigFlag %s set? ", android.hardware.broadcastradio.ConfigFlag$$.toString(i));
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    isConfigFlagSet = this.mService.isConfigFlagSet(i);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException("Failed to check flag " + android.hardware.broadcastradio.ConfigFlag$$.toString(i), e);
                } catch (java.lang.RuntimeException e2) {
                    throw com.android.server.broadcastradio.aidl.ConversionUtils.throwOnError(e2, "isConfigFlagSet");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return isConfigFlagSet;
    }

    public void setConfigFlag(int i, boolean z) throws android.os.RemoteException {
        this.mLogger.logRadioEvent("set ConfigFlag %s to %b ", android.hardware.broadcastradio.ConfigFlag$$.toString(i), java.lang.Boolean.valueOf(z));
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot set config flag for AIDL HAL client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    this.mService.setConfigFlag(i, z);
                } catch (java.lang.RuntimeException e) {
                    throw com.android.server.broadcastradio.aidl.ConversionUtils.throwOnError(e, "setConfigFlag");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.Map<java.lang.String, java.lang.String> setParameters(java.util.Map<java.lang.String, java.lang.String> map) {
        java.util.Map<java.lang.String, java.lang.String> vendorInfoFromHalVendorKeyValues;
        this.mLogger.logRadioEvent("Set parameters ", new java.lang.Object[0]);
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.w(TAG, "Cannot set parameters for AIDL HAL client from non-current user");
            return new android.util.ArrayMap();
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    vendorInfoFromHalVendorKeyValues = com.android.server.broadcastradio.aidl.ConversionUtils.vendorInfoFromHalVendorKeyValues(this.mService.setParameters(com.android.server.broadcastradio.aidl.ConversionUtils.vendorInfoToHalVendorKeyValues(map)));
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return vendorInfoFromHalVendorKeyValues;
    }

    public java.util.Map<java.lang.String, java.lang.String> getParameters(java.util.List<java.lang.String> list) {
        java.util.Map<java.lang.String, java.lang.String> vendorInfoFromHalVendorKeyValues;
        this.mLogger.logRadioEvent("Get parameters ", new java.lang.Object[0]);
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    vendorInfoFromHalVendorKeyValues = com.android.server.broadcastradio.aidl.ConversionUtils.vendorInfoFromHalVendorKeyValues(this.mService.getParameters((java.lang.String[]) list.toArray(new java.lang.String[0])));
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return vendorInfoFromHalVendorKeyValues;
    }

    void dumpInfo(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.printf("TunerSession\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.printf("Is session closed? %s\n", new java.lang.Object[]{this.mIsClosed ? "Yes" : "No"});
                indentingPrintWriter.printf("Is muted? %s\n", new java.lang.Object[]{this.mIsMuted ? "Yes" : "No"});
                indentingPrintWriter.printf("ProgramInfoCache: %s\n", new java.lang.Object[]{this.mProgramInfoCache});
                indentingPrintWriter.printf("Config: %s\n", new java.lang.Object[]{this.mPlaceHolderConfig});
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.printf("Tuner session events:\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        this.mLogger.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }
}
