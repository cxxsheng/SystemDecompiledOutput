package com.android.server.broadcastradio.hal2;

/* loaded from: classes.dex */
public final class BroadcastRadioService {
    private static final java.lang.String TAG = "BcRadio2Srv";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mNextModuleId;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<java.lang.String, java.lang.Integer> mServiceNameToModuleIdMap = new java.util.HashMap();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<java.lang.Integer, com.android.server.broadcastradio.hal2.RadioModule> mModules = new java.util.HashMap();
    private android.hidl.manager.V1_0.IServiceNotification.Stub mServiceListener = new android.hidl.manager.V1_0.IServiceNotification.Stub() { // from class: com.android.server.broadcastradio.hal2.BroadcastRadioService.1
        @Override // android.hidl.manager.V1_0.IServiceNotification
        public void onRegistration(java.lang.String str, java.lang.String str2, boolean z) {
            boolean z2;
            android.util.Slog.v(com.android.server.broadcastradio.hal2.BroadcastRadioService.TAG, "onRegistration(" + str + ", " + str2 + ", " + z + ")");
            synchronized (com.android.server.broadcastradio.hal2.BroadcastRadioService.this.mLock) {
                try {
                    java.lang.Integer num = (java.lang.Integer) com.android.server.broadcastradio.hal2.BroadcastRadioService.this.mServiceNameToModuleIdMap.get(str2);
                    if (num != null) {
                        z2 = false;
                    } else {
                        num = java.lang.Integer.valueOf(com.android.server.broadcastradio.hal2.BroadcastRadioService.this.mNextModuleId);
                        z2 = true;
                    }
                    com.android.server.broadcastradio.hal2.RadioModule tryLoadingModule = com.android.server.broadcastradio.hal2.RadioModule.tryLoadingModule(num.intValue(), str2);
                    if (tryLoadingModule == null) {
                        return;
                    }
                    android.util.Slog.v(com.android.server.broadcastradio.hal2.BroadcastRadioService.TAG, "loaded broadcast radio module " + num + ": " + str2 + " (HAL 2.0)");
                    com.android.server.broadcastradio.hal2.RadioModule radioModule = (com.android.server.broadcastradio.hal2.RadioModule) com.android.server.broadcastradio.hal2.BroadcastRadioService.this.mModules.put(num, tryLoadingModule);
                    if (radioModule != null) {
                        radioModule.closeSessions(0);
                    }
                    if (z2) {
                        com.android.server.broadcastradio.hal2.BroadcastRadioService.this.mServiceNameToModuleIdMap.put(str2, num);
                        com.android.server.broadcastradio.hal2.BroadcastRadioService.this.mNextModuleId++;
                    }
                    try {
                        tryLoadingModule.getService().linkToDeath(com.android.server.broadcastradio.hal2.BroadcastRadioService.this.mDeathRecipient, num.intValue());
                    } catch (android.os.RemoteException e) {
                        com.android.server.broadcastradio.hal2.BroadcastRadioService.this.mModules.remove(num);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    };
    private android.os.IHwBinder.DeathRecipient mDeathRecipient = new android.os.IHwBinder.DeathRecipient() { // from class: com.android.server.broadcastradio.hal2.BroadcastRadioService.2
        public void serviceDied(long j) {
            android.util.Slog.v(com.android.server.broadcastradio.hal2.BroadcastRadioService.TAG, "serviceDied(" + j + ")");
            synchronized (com.android.server.broadcastradio.hal2.BroadcastRadioService.this.mLock) {
                int i = (int) j;
                try {
                    com.android.server.broadcastradio.hal2.RadioModule radioModule = (com.android.server.broadcastradio.hal2.RadioModule) com.android.server.broadcastradio.hal2.BroadcastRadioService.this.mModules.remove(java.lang.Integer.valueOf(i));
                    if (radioModule != null) {
                        radioModule.closeSessions(0);
                    }
                    for (java.util.Map.Entry entry : com.android.server.broadcastradio.hal2.BroadcastRadioService.this.mServiceNameToModuleIdMap.entrySet()) {
                        if (((java.lang.Integer) entry.getValue()).intValue() == i) {
                            android.util.Slog.i(com.android.server.broadcastradio.hal2.BroadcastRadioService.TAG, "service " + ((java.lang.String) entry.getKey()) + " died; removed RadioModule with ID " + i);
                            return;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    };

    public BroadcastRadioService(int i) {
        this.mNextModuleId = i;
        try {
            android.hidl.manager.V1_0.IServiceManager service = android.hidl.manager.V1_0.IServiceManager.getService();
            if (service == null) {
                android.util.Slog.e(TAG, "failed to get HIDL Service Manager");
            } else {
                service.registerForNotifications(android.hardware.broadcastradio.V2_0.IBroadcastRadio.kInterfaceName, "", this.mServiceListener);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "failed to register for service notifications: ", e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    BroadcastRadioService(int i, android.hidl.manager.V1_0.IServiceManager iServiceManager) {
        this.mNextModuleId = i;
        java.util.Objects.requireNonNull(iServiceManager, "Service manager cannot be null");
        try {
            iServiceManager.registerForNotifications(android.hardware.broadcastradio.V2_0.IBroadcastRadio.kInterfaceName, "", this.mServiceListener);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to register for service notifications: ", e);
        }
    }

    @android.annotation.NonNull
    public java.util.Collection<android.hardware.radio.RadioManager.ModuleProperties> listModules() {
        java.util.Collection<android.hardware.radio.RadioManager.ModuleProperties> collection;
        android.util.Slog.v(TAG, "List HIDL 2.0 modules");
        synchronized (this.mLock) {
            collection = (java.util.Collection) this.mModules.values().stream().map(new java.util.function.Function() { // from class: com.android.server.broadcastradio.hal2.BroadcastRadioService$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    android.hardware.radio.RadioManager.ModuleProperties properties;
                    properties = ((com.android.server.broadcastradio.hal2.RadioModule) obj).getProperties();
                    return properties;
                }
            }).collect(java.util.stream.Collectors.toList());
        }
        return collection;
    }

    public boolean hasModule(int i) {
        boolean containsKey;
        synchronized (this.mLock) {
            containsKey = this.mModules.containsKey(java.lang.Integer.valueOf(i));
        }
        return containsKey;
    }

    public boolean hasAnyModules() {
        boolean z;
        synchronized (this.mLock) {
            z = !this.mModules.isEmpty();
        }
        return z;
    }

    public android.hardware.radio.ITuner openSession(int i, @android.annotation.Nullable android.hardware.radio.RadioManager.BandConfig bandConfig, boolean z, @android.annotation.NonNull android.hardware.radio.ITunerCallback iTunerCallback) throws android.os.RemoteException {
        com.android.server.broadcastradio.hal2.RadioModule radioModule;
        android.util.Slog.v(TAG, "Open HIDL 2.0 session with module id " + i);
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.e(TAG, "Cannot open tuner on HAL 2.0 client for non-current user");
            throw new java.lang.IllegalStateException("Cannot open session for non-current user");
        }
        java.util.Objects.requireNonNull(iTunerCallback);
        if (!z) {
            throw new java.lang.IllegalArgumentException("Non-audio sessions not supported with HAL 2.0");
        }
        synchronized (this.mLock) {
            radioModule = this.mModules.get(java.lang.Integer.valueOf(i));
            if (radioModule == null) {
                throw new java.lang.IllegalArgumentException("Invalid module ID");
            }
        }
        com.android.server.broadcastradio.hal2.TunerSession openSession = radioModule.openSession(iTunerCallback);
        if (bandConfig != null) {
            openSession.setConfiguration(bandConfig);
        }
        return openSession;
    }

    public android.hardware.radio.ICloseHandle addAnnouncementListener(@android.annotation.NonNull int[] iArr, @android.annotation.NonNull android.hardware.radio.IAnnouncementListener iAnnouncementListener) {
        boolean z;
        android.util.Slog.v(TAG, "Add announcementListener");
        com.android.server.broadcastradio.hal2.AnnouncementAggregator announcementAggregator = new com.android.server.broadcastradio.hal2.AnnouncementAggregator(iAnnouncementListener, this.mLock);
        synchronized (this.mLock) {
            java.util.Iterator<com.android.server.broadcastradio.hal2.RadioModule> it = this.mModules.values().iterator();
            z = false;
            while (it.hasNext()) {
                try {
                    announcementAggregator.watchModule(it.next(), iArr);
                    z = true;
                } catch (java.lang.UnsupportedOperationException e) {
                    android.util.Slog.v(TAG, "Announcements not supported for this module", e);
                }
            }
        }
        if (!z) {
            android.util.Slog.i(TAG, "There are no HAL modules that support announcements");
        }
        return announcementAggregator;
    }

    public void dumpInfo(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.printf("Next module id available: %d\n", new java.lang.Object[]{java.lang.Integer.valueOf(this.mNextModuleId)});
                indentingPrintWriter.printf("ServiceName to module id map:\n", new java.lang.Object[0]);
                indentingPrintWriter.increaseIndent();
                for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : this.mServiceNameToModuleIdMap.entrySet()) {
                    indentingPrintWriter.printf("Service name: %s, module id: %d\n", new java.lang.Object[]{entry.getKey(), entry.getValue()});
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.printf("Radio modules:\n", new java.lang.Object[0]);
                indentingPrintWriter.increaseIndent();
                for (java.util.Map.Entry<java.lang.Integer, com.android.server.broadcastradio.hal2.RadioModule> entry2 : this.mModules.entrySet()) {
                    indentingPrintWriter.printf("Module id=%d:\n", new java.lang.Object[]{entry2.getKey()});
                    indentingPrintWriter.increaseIndent();
                    entry2.getValue().dumpInfo(indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
