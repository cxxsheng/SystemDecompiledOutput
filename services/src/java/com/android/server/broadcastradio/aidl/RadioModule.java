package com.android.server.broadcastradio.aidl;

/* loaded from: classes.dex */
final class RadioModule {
    private static final int RADIO_EVENT_LOGGER_QUEUE_SIZE = 25;
    private static final java.lang.String TAG = "BcRadioAidlSrv.module";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.Boolean mAntennaConnected;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.hardware.radio.RadioManager.ProgramInfo mCurrentProgramInfo;
    private final android.os.Handler mHandler;
    private final com.android.server.broadcastradio.aidl.RadioLogger mLogger;
    private final android.hardware.radio.RadioManager.ModuleProperties mProperties;
    private final android.hardware.broadcastradio.IBroadcastRadio mService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.hardware.radio.ProgramList.Filter mUnionOfAidlProgramFilters;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.broadcastradio.aidl.ProgramInfoCache mProgramInfoCache = new com.android.server.broadcastradio.aidl.ProgramInfoCache(null);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<com.android.server.broadcastradio.aidl.TunerSession> mAidlTunerSessions = new android.util.ArraySet<>();
    private final android.hardware.broadcastradio.ITunerCallback mHalTunerCallback = new com.android.server.broadcastradio.aidl.RadioModule.AnonymousClass1();

    interface AidlCallbackRunnable {
        void run(android.hardware.radio.ITunerCallback iTunerCallback, int i) throws android.os.RemoteException;
    }

    /* renamed from: com.android.server.broadcastradio.aidl.RadioModule$1, reason: invalid class name */
    class AnonymousClass1 extends android.hardware.broadcastradio.ITunerCallback.Stub {
        AnonymousClass1() {
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public int getInterfaceVersion() {
            return 2;
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public java.lang.String getInterfaceHash() {
            return "bff68a8bc8b7cc191ab62bee10f7df8e79494467";
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public void onTuneFailed(final int i, final android.hardware.broadcastradio.ProgramSelector programSelector) {
            com.android.server.broadcastradio.aidl.RadioModule.this.fireLater(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.broadcastradio.aidl.RadioModule.AnonymousClass1.this.lambda$onTuneFailed$1(programSelector, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTuneFailed$1(android.hardware.broadcastradio.ProgramSelector programSelector, int i) {
            final android.hardware.radio.ProgramSelector programSelectorFromHalProgramSelector = com.android.server.broadcastradio.aidl.ConversionUtils.programSelectorFromHalProgramSelector(programSelector);
            final int halResultToTunerResult = com.android.server.broadcastradio.aidl.ConversionUtils.halResultToTunerResult(i);
            synchronized (com.android.server.broadcastradio.aidl.RadioModule.this.mLock) {
                com.android.server.broadcastradio.aidl.RadioModule.this.fanoutAidlCallbackLocked(new com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda8
                    @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
                    public final void run(android.hardware.radio.ITunerCallback iTunerCallback, int i2) {
                        com.android.server.broadcastradio.aidl.RadioModule.AnonymousClass1.lambda$onTuneFailed$0(programSelectorFromHalProgramSelector, halResultToTunerResult, iTunerCallback, i2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onTuneFailed$0(android.hardware.radio.ProgramSelector programSelector, int i, android.hardware.radio.ITunerCallback iTunerCallback, int i2) throws android.os.RemoteException {
            if (programSelector != null && !com.android.server.broadcastradio.aidl.ConversionUtils.programSelectorMeetsSdkVersionRequirement(programSelector, i2)) {
                com.android.server.utils.Slogf.e(com.android.server.broadcastradio.aidl.RadioModule.TAG, "onTuneFailed: cannot send program selector requiring higher target SDK version");
            } else {
                iTunerCallback.onTuneFailed(i, programSelector);
            }
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public void onCurrentProgramInfoChanged(final android.hardware.broadcastradio.ProgramInfo programInfo) {
            com.android.server.broadcastradio.aidl.RadioModule.this.fireLater(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.broadcastradio.aidl.RadioModule.AnonymousClass1.this.lambda$onCurrentProgramInfoChanged$3(programInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCurrentProgramInfoChanged$3(android.hardware.broadcastradio.ProgramInfo programInfo) {
            final android.hardware.radio.RadioManager.ProgramInfo programInfoFromHalProgramInfo = com.android.server.broadcastradio.aidl.ConversionUtils.programInfoFromHalProgramInfo(programInfo);
            java.util.Objects.requireNonNull(programInfoFromHalProgramInfo, "Program info from AIDL HAL is invalid");
            synchronized (com.android.server.broadcastradio.aidl.RadioModule.this.mLock) {
                com.android.server.broadcastradio.aidl.RadioModule.this.mCurrentProgramInfo = programInfoFromHalProgramInfo;
                com.android.server.broadcastradio.aidl.RadioModule.this.fanoutAidlCallbackLocked(new com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda1
                    @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
                    public final void run(android.hardware.radio.ITunerCallback iTunerCallback, int i) {
                        com.android.server.broadcastradio.aidl.RadioModule.AnonymousClass1.lambda$onCurrentProgramInfoChanged$2(programInfoFromHalProgramInfo, iTunerCallback, i);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onCurrentProgramInfoChanged$2(android.hardware.radio.RadioManager.ProgramInfo programInfo, android.hardware.radio.ITunerCallback iTunerCallback, int i) throws android.os.RemoteException {
            if (!com.android.server.broadcastradio.aidl.ConversionUtils.programInfoMeetsSdkVersionRequirement(programInfo, i)) {
                com.android.server.utils.Slogf.e(com.android.server.broadcastradio.aidl.RadioModule.TAG, "onCurrentProgramInfoChanged: cannot send program info requiring higher target SDK version");
            } else {
                iTunerCallback.onCurrentProgramInfoChanged(programInfo);
            }
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public void onProgramListUpdated(final android.hardware.broadcastradio.ProgramListChunk programListChunk) {
            com.android.server.broadcastradio.aidl.RadioModule.this.fireLater(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.broadcastradio.aidl.RadioModule.AnonymousClass1.this.lambda$onProgramListUpdated$4(programListChunk);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProgramListUpdated$4(android.hardware.broadcastradio.ProgramListChunk programListChunk) {
            synchronized (com.android.server.broadcastradio.aidl.RadioModule.this.mLock) {
                try {
                    com.android.server.broadcastradio.aidl.RadioModule.this.mProgramInfoCache.filterAndApplyChunk(programListChunk);
                    for (int i = 0; i < com.android.server.broadcastradio.aidl.RadioModule.this.mAidlTunerSessions.size(); i++) {
                        ((com.android.server.broadcastradio.aidl.TunerSession) com.android.server.broadcastradio.aidl.RadioModule.this.mAidlTunerSessions.valueAt(i)).onMergedProgramListUpdateFromHal(programListChunk);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public void onAntennaStateChange(final boolean z) {
            com.android.server.broadcastradio.aidl.RadioModule.this.fireLater(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.broadcastradio.aidl.RadioModule.AnonymousClass1.this.lambda$onAntennaStateChange$6(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAntennaStateChange$6(final boolean z) {
            synchronized (com.android.server.broadcastradio.aidl.RadioModule.this.mLock) {
                com.android.server.broadcastradio.aidl.RadioModule.this.mAntennaConnected = java.lang.Boolean.valueOf(z);
                com.android.server.broadcastradio.aidl.RadioModule.this.fanoutAidlCallbackLocked(new com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda0
                    @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
                    public final void run(android.hardware.radio.ITunerCallback iTunerCallback, int i) {
                        iTunerCallback.onAntennaState(z);
                    }
                });
            }
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public void onConfigFlagUpdated(final int i, final boolean z) {
            com.android.server.broadcastradio.aidl.RadioModule.this.fireLater(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.broadcastradio.aidl.RadioModule.AnonymousClass1.this.lambda$onConfigFlagUpdated$8(i, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfigFlagUpdated$8(final int i, final boolean z) {
            synchronized (com.android.server.broadcastradio.aidl.RadioModule.this.mLock) {
                com.android.server.broadcastradio.aidl.RadioModule.this.fanoutAidlCallbackLocked(new com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda2
                    @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
                    public final void run(android.hardware.radio.ITunerCallback iTunerCallback, int i2) {
                        com.android.server.broadcastradio.aidl.RadioModule.AnonymousClass1.lambda$onConfigFlagUpdated$7(i, z, iTunerCallback, i2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onConfigFlagUpdated$7(int i, boolean z, android.hardware.radio.ITunerCallback iTunerCallback, int i2) throws android.os.RemoteException {
            if (!com.android.server.broadcastradio.aidl.ConversionUtils.configFlagMeetsSdkVersionRequirement(i, i2)) {
                com.android.server.utils.Slogf.e(com.android.server.broadcastradio.aidl.RadioModule.TAG, "onConfigFlagUpdated: cannot send program info requiring higher target SDK version");
            } else {
                iTunerCallback.onConfigFlagUpdated(i, z);
            }
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public void onParametersUpdated(final android.hardware.broadcastradio.VendorKeyValue[] vendorKeyValueArr) {
            com.android.server.broadcastradio.aidl.RadioModule.this.fireLater(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.broadcastradio.aidl.RadioModule.AnonymousClass1.this.lambda$onParametersUpdated$10(vendorKeyValueArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onParametersUpdated$10(android.hardware.broadcastradio.VendorKeyValue[] vendorKeyValueArr) {
            synchronized (com.android.server.broadcastradio.aidl.RadioModule.this.mLock) {
                final java.util.Map<java.lang.String, java.lang.String> vendorInfoFromHalVendorKeyValues = com.android.server.broadcastradio.aidl.ConversionUtils.vendorInfoFromHalVendorKeyValues(vendorKeyValueArr);
                com.android.server.broadcastradio.aidl.RadioModule.this.fanoutAidlCallbackLocked(new com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda6
                    @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
                    public final void run(android.hardware.radio.ITunerCallback iTunerCallback, int i) {
                        iTunerCallback.onParametersUpdated(vendorInfoFromHalVendorKeyValues);
                    }
                });
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    RadioModule(android.hardware.broadcastradio.IBroadcastRadio iBroadcastRadio, android.hardware.radio.RadioManager.ModuleProperties moduleProperties) {
        java.util.Objects.requireNonNull(moduleProperties, "properties cannot be null");
        this.mProperties = moduleProperties;
        java.util.Objects.requireNonNull(iBroadcastRadio, "service cannot be null");
        this.mService = iBroadcastRadio;
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        this.mLogger = new com.android.server.broadcastradio.aidl.RadioLogger(TAG, 25);
    }

    @android.annotation.Nullable
    static com.android.server.broadcastradio.aidl.RadioModule tryLoadingModule(int i, java.lang.String str, android.os.IBinder iBinder) {
        android.hardware.broadcastradio.AmFmRegionConfig amFmRegionConfig;
        android.hardware.broadcastradio.DabTableEntry[] dabTableEntryArr;
        try {
            com.android.server.utils.Slogf.i(TAG, "Try loading module for module id = %d, module name = %s", java.lang.Integer.valueOf(i), str);
            android.hardware.broadcastradio.IBroadcastRadio asInterface = android.hardware.broadcastradio.IBroadcastRadio.Stub.asInterface(iBinder);
            if (asInterface == null) {
                com.android.server.utils.Slogf.w(TAG, "Module %s is null", str);
                return null;
            }
            try {
                amFmRegionConfig = asInterface.getAmFmRegionConfig(false);
            } catch (java.lang.RuntimeException e) {
                com.android.server.utils.Slogf.i(TAG, "Module %s does not has AMFM config", str);
                amFmRegionConfig = null;
            }
            try {
                dabTableEntryArr = asInterface.getDabRegionConfig();
            } catch (java.lang.RuntimeException e2) {
                com.android.server.utils.Slogf.i(TAG, "Module %s does not has DAB config", str);
                dabTableEntryArr = null;
            }
            return new com.android.server.broadcastradio.aidl.RadioModule(asInterface, com.android.server.broadcastradio.aidl.ConversionUtils.propertiesFromHalProperties(i, str, asInterface.getProperties(), amFmRegionConfig, dabTableEntryArr));
        } catch (android.os.RemoteException e3) {
            com.android.server.utils.Slogf.e(TAG, e3, "Failed to load module %s", str);
            return null;
        }
    }

    android.hardware.broadcastradio.IBroadcastRadio getService() {
        return this.mService;
    }

    android.hardware.radio.RadioManager.ModuleProperties getProperties() {
        return this.mProperties;
    }

    void setInternalHalCallback() throws android.os.RemoteException {
        this.mService.setTunerCallback(this.mHalTunerCallback);
    }

    com.android.server.broadcastradio.aidl.TunerSession openSession(android.hardware.radio.ITunerCallback iTunerCallback) throws android.os.RemoteException {
        com.android.server.broadcastradio.aidl.TunerSession tunerSession;
        java.lang.Boolean bool;
        android.hardware.radio.RadioManager.ProgramInfo programInfo;
        this.mLogger.logRadioEvent("Open TunerSession", new java.lang.Object[0]);
        synchronized (this.mLock) {
            tunerSession = new com.android.server.broadcastradio.aidl.TunerSession(this, this.mService, iTunerCallback);
            this.mAidlTunerSessions.add(tunerSession);
            bool = this.mAntennaConnected;
            programInfo = this.mCurrentProgramInfo;
        }
        if (bool != null) {
            iTunerCallback.onAntennaState(bool.booleanValue());
        }
        if (programInfo != null) {
            iTunerCallback.onCurrentProgramInfoChanged(programInfo);
        }
        return tunerSession;
    }

    void closeSessions(int i) {
        int size;
        com.android.server.broadcastradio.aidl.TunerSession[] tunerSessionArr;
        this.mLogger.logRadioEvent("Close TunerSessions %d", java.lang.Integer.valueOf(i));
        synchronized (this.mLock) {
            size = this.mAidlTunerSessions.size();
            tunerSessionArr = new com.android.server.broadcastradio.aidl.TunerSession[size];
            this.mAidlTunerSessions.toArray(tunerSessionArr);
            this.mAidlTunerSessions.clear();
        }
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.broadcastradio.aidl.TunerSession tunerSession = tunerSessionArr[i2];
            try {
                tunerSession.close(java.lang.Integer.valueOf(i));
            } catch (java.lang.Exception e) {
                com.android.server.utils.Slogf.e(TAG, "Failed to close TunerSession %s: %s", tunerSession, e);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.hardware.radio.ProgramList.Filter buildUnionOfTunerSessionFiltersLocked() {
        android.util.ArraySet arraySet = null;
        android.util.ArraySet arraySet2 = null;
        boolean z = true;
        boolean z2 = false;
        for (int i = 0; i < this.mAidlTunerSessions.size(); i++) {
            android.hardware.radio.ProgramList.Filter programListFilter = this.mAidlTunerSessions.valueAt(i).getProgramListFilter();
            if (programListFilter != null) {
                if (arraySet == null) {
                    arraySet = new android.util.ArraySet(programListFilter.getIdentifierTypes());
                    arraySet2 = new android.util.ArraySet(programListFilter.getIdentifiers());
                    z2 = programListFilter.areCategoriesIncluded();
                    z = programListFilter.areModificationsExcluded();
                } else {
                    if (!arraySet.isEmpty()) {
                        if (programListFilter.getIdentifierTypes().isEmpty()) {
                            arraySet.clear();
                        } else {
                            arraySet.addAll(programListFilter.getIdentifierTypes());
                        }
                    }
                    if (!arraySet2.isEmpty()) {
                        if (programListFilter.getIdentifiers().isEmpty()) {
                            arraySet2.clear();
                        } else {
                            arraySet2.addAll(programListFilter.getIdentifiers());
                        }
                    }
                    z2 |= programListFilter.areCategoriesIncluded();
                    z &= programListFilter.areModificationsExcluded();
                }
            }
        }
        if (arraySet == null) {
            return null;
        }
        return new android.hardware.radio.ProgramList.Filter(arraySet, arraySet2, z2, z);
    }

    void onTunerSessionProgramListFilterChanged(@android.annotation.Nullable com.android.server.broadcastradio.aidl.TunerSession tunerSession) {
        synchronized (this.mLock) {
            onTunerSessionProgramListFilterChangedLocked(tunerSession);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onTunerSessionProgramListFilterChangedLocked(@android.annotation.Nullable com.android.server.broadcastradio.aidl.TunerSession tunerSession) {
        android.hardware.radio.ProgramList.Filter buildUnionOfTunerSessionFiltersLocked = buildUnionOfTunerSessionFiltersLocked();
        if (buildUnionOfTunerSessionFiltersLocked == null) {
            if (this.mUnionOfAidlProgramFilters == null) {
                return;
            }
            this.mUnionOfAidlProgramFilters = null;
            try {
                this.mService.stopProgramListUpdates();
                return;
            } catch (android.os.RemoteException e) {
                com.android.server.utils.Slogf.e(TAG, e, "mHalTunerSession.stopProgramListUpdates() failed", new java.lang.Object[0]);
                return;
            }
        }
        synchronized (this.mLock) {
            try {
                if (buildUnionOfTunerSessionFiltersLocked.equals(this.mUnionOfAidlProgramFilters)) {
                    if (tunerSession != null) {
                        tunerSession.updateProgramInfoFromHalCache(this.mProgramInfoCache);
                    }
                    return;
                }
                this.mUnionOfAidlProgramFilters = buildUnionOfTunerSessionFiltersLocked;
                try {
                    this.mService.startProgramListUpdates(com.android.server.broadcastradio.aidl.ConversionUtils.filterToHalProgramFilter(buildUnionOfTunerSessionFiltersLocked));
                } catch (android.os.RemoteException e2) {
                    com.android.server.utils.Slogf.e(TAG, e2, "mHalTunerSession.startProgramListUpdates() failed", new java.lang.Object[0]);
                } catch (java.lang.RuntimeException e3) {
                    throw com.android.server.broadcastradio.aidl.ConversionUtils.throwOnError(e3, "Start Program ListUpdates");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onTunerSessionClosed(com.android.server.broadcastradio.aidl.TunerSession tunerSession) {
        synchronized (this.mLock) {
            onTunerSessionsClosedLocked(tunerSession);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onTunerSessionsClosedLocked(com.android.server.broadcastradio.aidl.TunerSession... tunerSessionArr) {
        for (com.android.server.broadcastradio.aidl.TunerSession tunerSession : tunerSessionArr) {
            this.mAidlTunerSessions.remove(tunerSession);
        }
        onTunerSessionProgramListFilterChanged(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fireLater(final java.lang.Runnable runnable) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.aidl.RadioModule$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                runnable.run();
            }
        });
    }

    void fanoutAidlCallback(final com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable aidlCallbackRunnable) {
        fireLater(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.aidl.RadioModule$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.broadcastradio.aidl.RadioModule.this.lambda$fanoutAidlCallback$1(aidlCallbackRunnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fanoutAidlCallback$1(com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable aidlCallbackRunnable) {
        synchronized (this.mLock) {
            fanoutAidlCallbackLocked(aidlCallbackRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void fanoutAidlCallbackLocked(com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable aidlCallbackRunnable) {
        int currentUser = com.android.server.broadcastradio.RadioServiceUserController.getCurrentUser();
        java.util.ArrayList arrayList = null;
        for (int i = 0; i < this.mAidlTunerSessions.size(); i++) {
            if (this.mAidlTunerSessions.valueAt(i).mUserId == currentUser || this.mAidlTunerSessions.valueAt(i).mUserId == 0) {
                try {
                    aidlCallbackRunnable.run(this.mAidlTunerSessions.valueAt(i).mCallback, this.mAidlTunerSessions.valueAt(i).getUid());
                } catch (android.os.DeadObjectException e) {
                    com.android.server.utils.Slogf.e(TAG, "Removing dead TunerSession");
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(this.mAidlTunerSessions.valueAt(i));
                } catch (android.os.RemoteException e2) {
                    com.android.server.utils.Slogf.e(TAG, e2, "Failed to invoke ITunerCallback", new java.lang.Object[0]);
                }
            }
        }
        if (arrayList != null) {
            onTunerSessionsClosedLocked((com.android.server.broadcastradio.aidl.TunerSession[]) arrayList.toArray(new com.android.server.broadcastradio.aidl.TunerSession[arrayList.size()]));
        }
    }

    android.hardware.radio.ICloseHandle addAnnouncementListener(final android.hardware.radio.IAnnouncementListener iAnnouncementListener, int[] iArr) throws android.os.RemoteException {
        this.mLogger.logRadioEvent("Add AnnouncementListener", new java.lang.Object[0]);
        int length = iArr.length;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) iArr[i];
        }
        final android.hardware.broadcastradio.ICloseHandle[] iCloseHandleArr = {null};
        try {
            iCloseHandleArr[0] = this.mService.registerAnnouncementListener(new android.hardware.broadcastradio.IAnnouncementListener.Stub() { // from class: com.android.server.broadcastradio.aidl.RadioModule.2
                @Override // android.hardware.broadcastradio.IAnnouncementListener
                public int getInterfaceVersion() {
                    return 2;
                }

                @Override // android.hardware.broadcastradio.IAnnouncementListener
                public java.lang.String getInterfaceHash() {
                    return "bff68a8bc8b7cc191ab62bee10f7df8e79494467";
                }

                @Override // android.hardware.broadcastradio.IAnnouncementListener
                public void onListUpdated(android.hardware.broadcastradio.Announcement[] announcementArr) throws android.os.RemoteException {
                    java.util.ArrayList arrayList = new java.util.ArrayList(announcementArr.length);
                    for (android.hardware.broadcastradio.Announcement announcement : announcementArr) {
                        arrayList.add(com.android.server.broadcastradio.aidl.ConversionUtils.announcementFromHalAnnouncement(announcement));
                    }
                    iAnnouncementListener.onListUpdated(arrayList);
                }
            }, bArr);
            return new android.hardware.radio.ICloseHandle.Stub() { // from class: com.android.server.broadcastradio.aidl.RadioModule.3
                public void close() {
                    try {
                        iCloseHandleArr[0].close();
                    } catch (android.os.RemoteException e) {
                        com.android.server.utils.Slogf.e(com.android.server.broadcastradio.aidl.RadioModule.TAG, e, "Failed closing announcement listener", new java.lang.Object[0]);
                    }
                    iCloseHandleArr[0] = null;
                }
            };
        } catch (java.lang.RuntimeException e) {
            throw com.android.server.broadcastradio.aidl.ConversionUtils.throwOnError(e, "AnnouncementListener");
        }
    }

    android.graphics.Bitmap getImage(int i) {
        this.mLogger.logRadioEvent("Get image for id = %d", java.lang.Integer.valueOf(i));
        if (i == 0) {
            throw new java.lang.IllegalArgumentException("Image ID is missing");
        }
        try {
            byte[] image = this.mService.getImage(i);
            if (image == null || image.length == 0) {
                return null;
            }
            return android.graphics.BitmapFactory.decodeByteArray(image, 0, image.length);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void dumpInfo(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.printf("RadioModule\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.printf("BroadcastRadioServiceImpl: %s\n", new java.lang.Object[]{this.mService});
                indentingPrintWriter.printf("Properties: %s\n", new java.lang.Object[]{this.mProperties});
                indentingPrintWriter.printf("Antenna state: ", new java.lang.Object[0]);
                if (this.mAntennaConnected == null) {
                    indentingPrintWriter.printf("undetermined\n", new java.lang.Object[0]);
                } else {
                    indentingPrintWriter.printf("%s\n", new java.lang.Object[]{this.mAntennaConnected.booleanValue() ? "connected" : "not connected"});
                }
                indentingPrintWriter.printf("current ProgramInfo: %s\n", new java.lang.Object[]{this.mCurrentProgramInfo});
                indentingPrintWriter.printf("ProgramInfoCache: %s\n", new java.lang.Object[]{this.mProgramInfoCache});
                indentingPrintWriter.printf("Union of AIDL ProgramFilters: %s\n", new java.lang.Object[]{this.mUnionOfAidlProgramFilters});
                indentingPrintWriter.printf("AIDL TunerSessions [%d]:\n", new java.lang.Object[]{java.lang.Integer.valueOf(this.mAidlTunerSessions.size())});
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mAidlTunerSessions.size(); i++) {
                    this.mAidlTunerSessions.valueAt(i).dumpInfo(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.printf("Radio module events:\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        this.mLogger.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }
}
