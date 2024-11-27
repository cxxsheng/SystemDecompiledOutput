package com.android.server.broadcastradio.hal2;

/* loaded from: classes.dex */
final class RadioModule {
    private static final int RADIO_EVENT_LOGGER_QUEUE_SIZE = 25;
    private static final java.lang.String TAG = "BcRadio2Srv.module";

    @android.annotation.NonNull
    private final com.android.server.broadcastradio.hal2.RadioEventLogger mEventLogger;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.hardware.broadcastradio.V2_0.ITunerSession mHalTunerSession;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final android.hardware.radio.RadioManager.ModuleProperties mProperties;

    @android.annotation.NonNull
    private final android.hardware.broadcastradio.V2_0.IBroadcastRadio mService;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.Boolean mAntennaConnected = null;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.hardware.radio.RadioManager.ProgramInfo mCurrentProgramInfo = null;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.broadcastradio.hal2.ProgramInfoCache mProgramInfoCache = new com.android.server.broadcastradio.hal2.ProgramInfoCache(null);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.hardware.radio.ProgramList.Filter mUnionOfAidlProgramFilters = null;
    private final android.hardware.broadcastradio.V2_0.ITunerCallback mHalTunerCallback = new com.android.server.broadcastradio.hal2.RadioModule.AnonymousClass1();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Set<com.android.server.broadcastradio.hal2.TunerSession> mAidlTunerSessions = new java.util.HashSet();

    interface AidlCallbackRunnable {
        void run(android.hardware.radio.ITunerCallback iTunerCallback) throws android.os.RemoteException;
    }

    /* renamed from: com.android.server.broadcastradio.hal2.RadioModule$1, reason: invalid class name */
    class AnonymousClass1 extends android.hardware.broadcastradio.V2_0.ITunerCallback.Stub {
        AnonymousClass1() {
        }

        @Override // android.hardware.broadcastradio.V2_0.ITunerCallback
        public void onTuneFailed(final int i, final android.hardware.broadcastradio.V2_0.ProgramSelector programSelector) {
            com.android.server.broadcastradio.hal2.RadioModule.this.fireLater(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.hal2.RadioModule$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.broadcastradio.hal2.RadioModule.AnonymousClass1.this.lambda$onTuneFailed$1(programSelector, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTuneFailed$1(android.hardware.broadcastradio.V2_0.ProgramSelector programSelector, int i) {
            final android.hardware.radio.ProgramSelector programSelectorFromHal = com.android.server.broadcastradio.hal2.Convert.programSelectorFromHal(programSelector);
            final int halResultToTunerResult = com.android.server.broadcastradio.hal2.Convert.halResultToTunerResult(i);
            synchronized (com.android.server.broadcastradio.hal2.RadioModule.this.mLock) {
                com.android.server.broadcastradio.hal2.RadioModule.this.fanoutAidlCallbackLocked(new com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.hal2.RadioModule$1$$ExternalSyntheticLambda0
                    @Override // com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable
                    public final void run(android.hardware.radio.ITunerCallback iTunerCallback) {
                        iTunerCallback.onTuneFailed(halResultToTunerResult, programSelectorFromHal);
                    }
                });
            }
        }

        @Override // android.hardware.broadcastradio.V2_0.ITunerCallback
        public void onCurrentProgramInfoChanged(final android.hardware.broadcastradio.V2_0.ProgramInfo programInfo) {
            com.android.server.broadcastradio.hal2.RadioModule.this.fireLater(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.hal2.RadioModule$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.broadcastradio.hal2.RadioModule.AnonymousClass1.this.lambda$onCurrentProgramInfoChanged$3(programInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCurrentProgramInfoChanged$3(android.hardware.broadcastradio.V2_0.ProgramInfo programInfo) {
            synchronized (com.android.server.broadcastradio.hal2.RadioModule.this.mLock) {
                com.android.server.broadcastradio.hal2.RadioModule.this.mCurrentProgramInfo = com.android.server.broadcastradio.hal2.Convert.programInfoFromHal(programInfo);
                final android.hardware.radio.RadioManager.ProgramInfo programInfo2 = com.android.server.broadcastradio.hal2.RadioModule.this.mCurrentProgramInfo;
                com.android.server.broadcastradio.hal2.RadioModule.this.fanoutAidlCallbackLocked(new com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.hal2.RadioModule$1$$ExternalSyntheticLambda7
                    @Override // com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable
                    public final void run(android.hardware.radio.ITunerCallback iTunerCallback) {
                        iTunerCallback.onCurrentProgramInfoChanged(programInfo2);
                    }
                });
            }
        }

        @Override // android.hardware.broadcastradio.V2_0.ITunerCallback
        public void onProgramListUpdated(final android.hardware.broadcastradio.V2_0.ProgramListChunk programListChunk) {
            com.android.server.broadcastradio.hal2.RadioModule.this.fireLater(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.hal2.RadioModule$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.broadcastradio.hal2.RadioModule.AnonymousClass1.this.lambda$onProgramListUpdated$4(programListChunk);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProgramListUpdated$4(android.hardware.broadcastradio.V2_0.ProgramListChunk programListChunk) {
            synchronized (com.android.server.broadcastradio.hal2.RadioModule.this.mLock) {
                try {
                    com.android.server.broadcastradio.hal2.RadioModule.this.mProgramInfoCache.filterAndApplyChunk(programListChunk);
                    java.util.Iterator it = com.android.server.broadcastradio.hal2.RadioModule.this.mAidlTunerSessions.iterator();
                    while (it.hasNext()) {
                        ((com.android.server.broadcastradio.hal2.TunerSession) it.next()).onMergedProgramListUpdateFromHal(programListChunk);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.hardware.broadcastradio.V2_0.ITunerCallback
        public void onAntennaStateChange(final boolean z) {
            com.android.server.broadcastradio.hal2.RadioModule.this.fireLater(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.hal2.RadioModule$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.broadcastradio.hal2.RadioModule.AnonymousClass1.this.lambda$onAntennaStateChange$6(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAntennaStateChange$6(final boolean z) {
            synchronized (com.android.server.broadcastradio.hal2.RadioModule.this.mLock) {
                com.android.server.broadcastradio.hal2.RadioModule.this.mAntennaConnected = java.lang.Boolean.valueOf(z);
                com.android.server.broadcastradio.hal2.RadioModule.this.fanoutAidlCallbackLocked(new com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.hal2.RadioModule$1$$ExternalSyntheticLambda5
                    @Override // com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable
                    public final void run(android.hardware.radio.ITunerCallback iTunerCallback) {
                        iTunerCallback.onAntennaState(z);
                    }
                });
            }
        }

        @Override // android.hardware.broadcastradio.V2_0.ITunerCallback
        public void onParametersUpdated(final java.util.ArrayList<android.hardware.broadcastradio.V2_0.VendorKeyValue> arrayList) {
            com.android.server.broadcastradio.hal2.RadioModule.this.fireLater(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.hal2.RadioModule$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.broadcastradio.hal2.RadioModule.AnonymousClass1.this.lambda$onParametersUpdated$8(arrayList);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onParametersUpdated$8(java.util.ArrayList arrayList) {
            final java.util.Map<java.lang.String, java.lang.String> vendorInfoFromHal = com.android.server.broadcastradio.hal2.Convert.vendorInfoFromHal(arrayList);
            synchronized (com.android.server.broadcastradio.hal2.RadioModule.this.mLock) {
                com.android.server.broadcastradio.hal2.RadioModule.this.fanoutAidlCallbackLocked(new com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable() { // from class: com.android.server.broadcastradio.hal2.RadioModule$1$$ExternalSyntheticLambda1
                    @Override // com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable
                    public final void run(android.hardware.radio.ITunerCallback iTunerCallback) {
                        iTunerCallback.onParametersUpdated(vendorInfoFromHal);
                    }
                });
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    RadioModule(@android.annotation.NonNull android.hardware.broadcastradio.V2_0.IBroadcastRadio iBroadcastRadio, @android.annotation.NonNull android.hardware.radio.RadioManager.ModuleProperties moduleProperties) {
        java.util.Objects.requireNonNull(moduleProperties);
        this.mProperties = moduleProperties;
        java.util.Objects.requireNonNull(iBroadcastRadio);
        this.mService = iBroadcastRadio;
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        this.mEventLogger = new com.android.server.broadcastradio.hal2.RadioEventLogger(TAG, 25);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @android.annotation.Nullable
    static com.android.server.broadcastradio.hal2.RadioModule tryLoadingModule(int i, @android.annotation.NonNull java.lang.String str) {
        try {
            android.util.Slog.i(TAG, "Try loading module for idx " + i + ", fqName " + str);
            android.hardware.broadcastradio.V2_0.IBroadcastRadio service = android.hardware.broadcastradio.V2_0.IBroadcastRadio.getService(str);
            if (service == null) {
                android.util.Slog.w(TAG, "No service found for fqName " + str);
                return null;
            }
            final com.android.server.broadcastradio.hal2.Mutable mutable = new com.android.server.broadcastradio.hal2.Mutable();
            service.getAmFmRegionConfig(false, new android.hardware.broadcastradio.V2_0.IBroadcastRadio.getAmFmRegionConfigCallback() { // from class: com.android.server.broadcastradio.hal2.RadioModule$$ExternalSyntheticLambda4
                @Override // android.hardware.broadcastradio.V2_0.IBroadcastRadio.getAmFmRegionConfigCallback
                public final void onValues(int i2, android.hardware.broadcastradio.V2_0.AmFmRegionConfig amFmRegionConfig) {
                    com.android.server.broadcastradio.hal2.RadioModule.lambda$tryLoadingModule$0(com.android.server.broadcastradio.hal2.Mutable.this, i2, amFmRegionConfig);
                }
            });
            final com.android.server.broadcastradio.hal2.Mutable mutable2 = new com.android.server.broadcastradio.hal2.Mutable();
            service.getDabRegionConfig(new android.hardware.broadcastradio.V2_0.IBroadcastRadio.getDabRegionConfigCallback() { // from class: com.android.server.broadcastradio.hal2.RadioModule$$ExternalSyntheticLambda5
                @Override // android.hardware.broadcastradio.V2_0.IBroadcastRadio.getDabRegionConfigCallback
                public final void onValues(int i2, java.util.ArrayList arrayList) {
                    com.android.server.broadcastradio.hal2.RadioModule.lambda$tryLoadingModule$1(com.android.server.broadcastradio.hal2.Mutable.this, i2, arrayList);
                }
            });
            return new com.android.server.broadcastradio.hal2.RadioModule(service, com.android.server.broadcastradio.hal2.Convert.propertiesFromHal(i, str, service.getProperties(), (android.hardware.broadcastradio.V2_0.AmFmRegionConfig) mutable.value, (java.util.List) mutable2.value));
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to load module " + str, e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void lambda$tryLoadingModule$0(com.android.server.broadcastradio.hal2.Mutable mutable, int i, android.hardware.broadcastradio.V2_0.AmFmRegionConfig amFmRegionConfig) {
        if (i == 0) {
            mutable.value = amFmRegionConfig;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void lambda$tryLoadingModule$1(com.android.server.broadcastradio.hal2.Mutable mutable, int i, java.util.ArrayList arrayList) {
        if (i == 0) {
            mutable.value = arrayList;
        }
    }

    @android.annotation.NonNull
    android.hardware.broadcastradio.V2_0.IBroadcastRadio getService() {
        return this.mService;
    }

    public android.hardware.radio.RadioManager.ModuleProperties getProperties() {
        return this.mProperties;
    }

    com.android.server.broadcastradio.hal2.TunerSession openSession(@android.annotation.NonNull android.hardware.radio.ITunerCallback iTunerCallback) throws android.os.RemoteException {
        com.android.server.broadcastradio.hal2.TunerSession tunerSession;
        this.mEventLogger.logRadioEvent("Open TunerSession", new java.lang.Object[0]);
        synchronized (this.mLock) {
            try {
                if (this.mHalTunerSession == null) {
                    final com.android.server.broadcastradio.hal2.Mutable mutable = new com.android.server.broadcastradio.hal2.Mutable();
                    this.mService.openSession(this.mHalTunerCallback, new android.hardware.broadcastradio.V2_0.IBroadcastRadio.openSessionCallback() { // from class: com.android.server.broadcastradio.hal2.RadioModule$$ExternalSyntheticLambda6
                        @Override // android.hardware.broadcastradio.V2_0.IBroadcastRadio.openSessionCallback
                        public final void onValues(int i, android.hardware.broadcastradio.V2_0.ITunerSession iTunerSession) {
                            com.android.server.broadcastradio.hal2.RadioModule.this.lambda$openSession$2(mutable, i, iTunerSession);
                        }
                    });
                    android.hardware.broadcastradio.V2_0.ITunerSession iTunerSession = (android.hardware.broadcastradio.V2_0.ITunerSession) mutable.value;
                    java.util.Objects.requireNonNull(iTunerSession);
                    this.mHalTunerSession = iTunerSession;
                }
                tunerSession = new com.android.server.broadcastradio.hal2.TunerSession(this, this.mHalTunerSession, iTunerCallback);
                this.mAidlTunerSessions.add(tunerSession);
                if (this.mAntennaConnected != null) {
                    iTunerCallback.onAntennaState(this.mAntennaConnected.booleanValue());
                }
                if (this.mCurrentProgramInfo != null) {
                    iTunerCallback.onCurrentProgramInfoChanged(this.mCurrentProgramInfo);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return tunerSession;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$openSession$2(com.android.server.broadcastradio.hal2.Mutable mutable, int i, android.hardware.broadcastradio.V2_0.ITunerSession iTunerSession) {
        com.android.server.broadcastradio.hal2.Convert.throwOnError("openSession", i);
        mutable.value = iTunerSession;
        this.mEventLogger.logRadioEvent("New HIDL 2.0 tuner session is opened", new java.lang.Object[0]);
    }

    void closeSessions(java.lang.Integer num) {
        int size;
        com.android.server.broadcastradio.hal2.TunerSession[] tunerSessionArr;
        this.mEventLogger.logRadioEvent("Close TunerSessions", new java.lang.Object[0]);
        synchronized (this.mLock) {
            size = this.mAidlTunerSessions.size();
            tunerSessionArr = new com.android.server.broadcastradio.hal2.TunerSession[size];
            this.mAidlTunerSessions.toArray(tunerSessionArr);
            this.mAidlTunerSessions.clear();
        }
        for (int i = 0; i < size; i++) {
            tunerSessionArr[i].close(num);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.hardware.radio.ProgramList.Filter buildUnionOfTunerSessionFiltersLocked() {
        java.util.Iterator<com.android.server.broadcastradio.hal2.TunerSession> it = this.mAidlTunerSessions.iterator();
        boolean z = false;
        boolean z2 = true;
        java.util.HashSet hashSet = null;
        java.util.HashSet hashSet2 = null;
        while (it.hasNext()) {
            android.hardware.radio.ProgramList.Filter programListFilter = it.next().getProgramListFilter();
            if (programListFilter != null) {
                if (hashSet == null) {
                    hashSet = new java.util.HashSet(programListFilter.getIdentifierTypes());
                    hashSet2 = new java.util.HashSet(programListFilter.getIdentifiers());
                    z = programListFilter.areCategoriesIncluded();
                    z2 = programListFilter.areModificationsExcluded();
                } else {
                    if (!hashSet.isEmpty()) {
                        if (programListFilter.getIdentifierTypes().isEmpty()) {
                            hashSet.clear();
                        } else {
                            hashSet.addAll(programListFilter.getIdentifierTypes());
                        }
                    }
                    if (!hashSet2.isEmpty()) {
                        if (programListFilter.getIdentifiers().isEmpty()) {
                            hashSet2.clear();
                        } else {
                            hashSet2.addAll(programListFilter.getIdentifiers());
                        }
                    }
                    z |= programListFilter.areCategoriesIncluded();
                    z2 &= programListFilter.areModificationsExcluded();
                }
            }
        }
        if (hashSet == null) {
            return null;
        }
        return new android.hardware.radio.ProgramList.Filter(hashSet, hashSet2, z, z2);
    }

    void onTunerSessionProgramListFilterChanged(@android.annotation.Nullable com.android.server.broadcastradio.hal2.TunerSession tunerSession) {
        synchronized (this.mLock) {
            onTunerSessionProgramListFilterChangedLocked(tunerSession);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onTunerSessionProgramListFilterChangedLocked(@android.annotation.Nullable com.android.server.broadcastradio.hal2.TunerSession tunerSession) {
        android.hardware.radio.ProgramList.Filter buildUnionOfTunerSessionFiltersLocked = buildUnionOfTunerSessionFiltersLocked();
        if (buildUnionOfTunerSessionFiltersLocked == null) {
            if (this.mUnionOfAidlProgramFilters == null) {
                return;
            }
            this.mUnionOfAidlProgramFilters = null;
            try {
                this.mHalTunerSession.stopProgramListUpdates();
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "mHalTunerSession.stopProgramListUpdates() failed: ", e);
                return;
            }
        }
        if (buildUnionOfTunerSessionFiltersLocked.equals(this.mUnionOfAidlProgramFilters)) {
            if (tunerSession != null) {
                tunerSession.updateProgramInfoFromHalCache(this.mProgramInfoCache);
            }
        } else {
            this.mUnionOfAidlProgramFilters = buildUnionOfTunerSessionFiltersLocked;
            try {
                com.android.server.broadcastradio.hal2.Convert.throwOnError("startProgramListUpdates", this.mHalTunerSession.startProgramListUpdates(com.android.server.broadcastradio.hal2.Convert.programFilterToHal(buildUnionOfTunerSessionFiltersLocked)));
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(TAG, "mHalTunerSession.startProgramListUpdates() failed: ", e2);
            }
        }
    }

    void onTunerSessionClosed(com.android.server.broadcastradio.hal2.TunerSession tunerSession) {
        synchronized (this.mLock) {
            onTunerSessionsClosedLocked(tunerSession);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onTunerSessionsClosedLocked(com.android.server.broadcastradio.hal2.TunerSession... tunerSessionArr) {
        for (com.android.server.broadcastradio.hal2.TunerSession tunerSession : tunerSessionArr) {
            this.mAidlTunerSessions.remove(tunerSession);
        }
        onTunerSessionProgramListFilterChanged(null);
        if (this.mAidlTunerSessions.isEmpty() && this.mHalTunerSession != null) {
            this.mEventLogger.logRadioEvent("Closing HAL tuner session", new java.lang.Object[0]);
            try {
                this.mHalTunerSession.close();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "mHalTunerSession.close() failed: ", e);
            }
            this.mHalTunerSession = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fireLater(final java.lang.Runnable runnable) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.hal2.RadioModule$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                runnable.run();
            }
        });
    }

    void fanoutAidlCallback(final com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable aidlCallbackRunnable) {
        fireLater(new java.lang.Runnable() { // from class: com.android.server.broadcastradio.hal2.RadioModule$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.broadcastradio.hal2.RadioModule.this.lambda$fanoutAidlCallback$4(aidlCallbackRunnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fanoutAidlCallback$4(com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable aidlCallbackRunnable) {
        synchronized (this.mLock) {
            fanoutAidlCallbackLocked(aidlCallbackRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void fanoutAidlCallbackLocked(com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable aidlCallbackRunnable) {
        int currentUser = com.android.server.broadcastradio.RadioServiceUserController.getCurrentUser();
        java.util.ArrayList arrayList = null;
        for (com.android.server.broadcastradio.hal2.TunerSession tunerSession : this.mAidlTunerSessions) {
            if (tunerSession.mUserId == currentUser || tunerSession.mUserId == 0) {
                try {
                    aidlCallbackRunnable.run(tunerSession.mCallback);
                } catch (android.os.DeadObjectException e) {
                    android.util.Slog.e(TAG, "Removing dead TunerSession");
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(tunerSession);
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.e(TAG, "Failed to invoke ITunerCallback: ", e2);
                }
            }
        }
        if (arrayList != null) {
            onTunerSessionsClosedLocked((com.android.server.broadcastradio.hal2.TunerSession[]) arrayList.toArray(new com.android.server.broadcastradio.hal2.TunerSession[arrayList.size()]));
        }
    }

    android.hardware.radio.ICloseHandle addAnnouncementListener(int[] iArr, android.hardware.radio.IAnnouncementListener iAnnouncementListener) throws android.os.RemoteException {
        this.mEventLogger.logRadioEvent("Add AnnouncementListener", new java.lang.Object[0]);
        java.util.ArrayList<java.lang.Byte> arrayList = new java.util.ArrayList<>();
        for (int i : iArr) {
            arrayList.add(java.lang.Byte.valueOf((byte) i));
        }
        final android.util.MutableInt mutableInt = new android.util.MutableInt(1);
        final com.android.server.broadcastradio.hal2.Mutable mutable = new com.android.server.broadcastradio.hal2.Mutable();
        this.mService.registerAnnouncementListener(arrayList, new com.android.server.broadcastradio.hal2.RadioModule.AnonymousClass2(iAnnouncementListener), new android.hardware.broadcastradio.V2_0.IBroadcastRadio.registerAnnouncementListenerCallback() { // from class: com.android.server.broadcastradio.hal2.RadioModule$$ExternalSyntheticLambda3
            @Override // android.hardware.broadcastradio.V2_0.IBroadcastRadio.registerAnnouncementListenerCallback
            public final void onValues(int i2, android.hardware.broadcastradio.V2_0.ICloseHandle iCloseHandle) {
                com.android.server.broadcastradio.hal2.RadioModule.lambda$addAnnouncementListener$5(mutableInt, mutable, i2, iCloseHandle);
            }
        });
        com.android.server.broadcastradio.hal2.Convert.throwOnError("addAnnouncementListener", mutableInt.value);
        return new android.hardware.radio.ICloseHandle.Stub() { // from class: com.android.server.broadcastradio.hal2.RadioModule.3
            public void close() {
                try {
                    ((android.hardware.broadcastradio.V2_0.ICloseHandle) mutable.value).close();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.broadcastradio.hal2.RadioModule.TAG, "Failed closing announcement listener", e);
                }
                mutable.value = null;
            }
        };
    }

    /* renamed from: com.android.server.broadcastradio.hal2.RadioModule$2, reason: invalid class name */
    class AnonymousClass2 extends android.hardware.broadcastradio.V2_0.IAnnouncementListener.Stub {
        final /* synthetic */ android.hardware.radio.IAnnouncementListener val$listener;

        AnonymousClass2(android.hardware.radio.IAnnouncementListener iAnnouncementListener) {
            this.val$listener = iAnnouncementListener;
        }

        @Override // android.hardware.broadcastradio.V2_0.IAnnouncementListener
        public void onListUpdated(java.util.ArrayList<android.hardware.broadcastradio.V2_0.Announcement> arrayList) throws android.os.RemoteException {
            this.val$listener.onListUpdated((java.util.List) arrayList.stream().map(new java.util.function.Function() { // from class: com.android.server.broadcastradio.hal2.RadioModule$2$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    android.hardware.radio.Announcement announcementFromHal;
                    announcementFromHal = com.android.server.broadcastradio.hal2.Convert.announcementFromHal((android.hardware.broadcastradio.V2_0.Announcement) obj);
                    return announcementFromHal;
                }
            }).collect(java.util.stream.Collectors.toList()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void lambda$addAnnouncementListener$5(android.util.MutableInt mutableInt, com.android.server.broadcastradio.hal2.Mutable mutable, int i, android.hardware.broadcastradio.V2_0.ICloseHandle iCloseHandle) {
        mutableInt.value = i;
        mutable.value = iCloseHandle;
    }

    android.graphics.Bitmap getImage(final int i) {
        this.mEventLogger.logRadioEvent("Get image for id %d", java.lang.Integer.valueOf(i));
        if (i == 0) {
            throw new java.lang.IllegalArgumentException("Image ID is missing");
        }
        java.util.List list = (java.util.List) com.android.server.broadcastradio.hal2.Utils.maybeRethrow(new com.android.server.broadcastradio.hal2.Utils.FuncThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal2.RadioModule$$ExternalSyntheticLambda0
            @Override // com.android.server.broadcastradio.hal2.Utils.FuncThrowingRemoteException
            public final java.lang.Object exec() {
                java.util.ArrayList lambda$getImage$6;
                lambda$getImage$6 = com.android.server.broadcastradio.hal2.RadioModule.this.lambda$getImage$6(i);
                return lambda$getImage$6;
            }
        });
        int size = list.size();
        byte[] bArr = new byte[size];
        for (int i2 = 0; i2 < list.size(); i2++) {
            bArr[i2] = ((java.lang.Byte) list.get(i2)).byteValue();
        }
        if (size == 0) {
            return null;
        }
        return android.graphics.BitmapFactory.decodeByteArray(bArr, 0, size);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.ArrayList lambda$getImage$6(int i) throws android.os.RemoteException {
        return this.mService.getImage(i);
    }

    void dumpInfo(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.printf("RadioModule\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("BroadcastRadioService: %s\n", new java.lang.Object[]{this.mService});
        indentingPrintWriter.printf("Properties: %s\n", new java.lang.Object[]{this.mProperties});
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.printf("HIDL 2.0 HAL TunerSession: %s\n", new java.lang.Object[]{this.mHalTunerSession});
                indentingPrintWriter.printf("Is antenna connected? ", new java.lang.Object[0]);
                if (this.mAntennaConnected == null) {
                    indentingPrintWriter.printf("null\n", new java.lang.Object[0]);
                } else {
                    indentingPrintWriter.printf("%s\n", new java.lang.Object[]{this.mAntennaConnected.booleanValue() ? "Yes" : "No"});
                }
                indentingPrintWriter.printf("Current ProgramInfo: %s\n", new java.lang.Object[]{this.mCurrentProgramInfo});
                indentingPrintWriter.printf("ProgramInfoCache: %s\n", new java.lang.Object[]{this.mProgramInfoCache});
                indentingPrintWriter.printf("Union of AIDL ProgramFilters: %s\n", new java.lang.Object[]{this.mUnionOfAidlProgramFilters});
                indentingPrintWriter.printf("AIDL TunerSessions:\n", new java.lang.Object[0]);
                indentingPrintWriter.increaseIndent();
                java.util.Iterator<com.android.server.broadcastradio.hal2.TunerSession> it = this.mAidlTunerSessions.iterator();
                while (it.hasNext()) {
                    it.next().dumpInfo(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.printf("Radio module events:\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        this.mEventLogger.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }
}
