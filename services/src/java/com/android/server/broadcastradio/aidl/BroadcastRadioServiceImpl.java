package com.android.server.broadcastradio.aidl;

/* loaded from: classes.dex */
public final class BroadcastRadioServiceImpl {
    private static final java.lang.String TAG = "BcRadioAidlSrv";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<java.lang.String, java.lang.Integer> mServiceNameToModuleIdMap = new android.util.ArrayMap();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.broadcastradio.aidl.RadioModule> mModules = new android.util.SparseArray<>();
    private final android.os.IServiceCallback.Stub mServiceListener = new android.os.IServiceCallback.Stub() { // from class: com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.1
        public void onRegistration(java.lang.String str, android.os.IBinder iBinder) {
            boolean z;
            com.android.server.utils.Slogf.i(com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.TAG, "onRegistration for %s", str);
            synchronized (com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.this.mLock) {
                try {
                    java.lang.Integer num = (java.lang.Integer) com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.this.mServiceNameToModuleIdMap.get(str);
                    if (num != null) {
                        z = false;
                    } else {
                        num = java.lang.Integer.valueOf(com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.this.mNextModuleId);
                        z = true;
                    }
                    com.android.server.broadcastradio.aidl.RadioModule tryLoadingModule = com.android.server.broadcastradio.aidl.RadioModule.tryLoadingModule(num.intValue(), str, iBinder);
                    if (tryLoadingModule == null) {
                        com.android.server.utils.Slogf.w(com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.TAG, "No module %s with id %d (HAL AIDL)", str, num);
                        return;
                    }
                    try {
                        tryLoadingModule.setInternalHalCallback();
                        if (com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.DEBUG) {
                            com.android.server.utils.Slogf.d(com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.TAG, "Loaded broadcast radio module %s with id %d (HAL AIDL)", str, num);
                        }
                        com.android.server.broadcastradio.aidl.RadioModule radioModule = (com.android.server.broadcastradio.aidl.RadioModule) com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.this.mModules.get(num.intValue());
                        com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.this.mModules.put(num.intValue(), tryLoadingModule);
                        if (radioModule != null) {
                            radioModule.closeSessions(0);
                        }
                        if (z) {
                            com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.this.mServiceNameToModuleIdMap.put(str, num);
                            com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.this.mNextModuleId++;
                        }
                        try {
                            tryLoadingModule.getService().asBinder().linkToDeath(com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.this.new BroadcastRadioDeathRecipient(num.intValue()), num.intValue());
                        } catch (android.os.RemoteException e) {
                            com.android.server.utils.Slogf.w(com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.TAG, "Service has already died, so remove its entry from mModules.");
                            com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.this.mModules.remove(num.intValue());
                        }
                    } catch (android.os.RemoteException e2) {
                        com.android.server.utils.Slogf.wtf(com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.TAG, e2, "Broadcast radio module %s with id %d (HAL AIDL) cannot register HAL callback", str, num);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    };

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mNextModuleId = 0;

    private final class BroadcastRadioDeathRecipient implements android.os.IBinder.DeathRecipient {
        private final int mModuleId;

        BroadcastRadioDeathRecipient(int i) {
            this.mModuleId = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.utils.Slogf.i(com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.TAG, "ServiceDied for module id %d", java.lang.Integer.valueOf(this.mModuleId));
            synchronized (com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.this.mLock) {
                try {
                    com.android.server.broadcastradio.aidl.RadioModule radioModule = (com.android.server.broadcastradio.aidl.RadioModule) com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.this.mModules.removeReturnOld(this.mModuleId);
                    if (radioModule != null) {
                        radioModule.closeSessions(0);
                    }
                    for (java.util.Map.Entry entry : com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.this.mServiceNameToModuleIdMap.entrySet()) {
                        if (((java.lang.Integer) entry.getValue()).intValue() == this.mModuleId) {
                            com.android.server.utils.Slogf.w(com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.TAG, "Service %s died, removed RadioModule with ID %d", entry.getKey(), java.lang.Integer.valueOf(this.mModuleId));
                            return;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public BroadcastRadioServiceImpl(java.util.ArrayList<java.lang.String> arrayList) {
        if (DEBUG) {
            com.android.server.utils.Slogf.d(TAG, "Initializing BroadcastRadioServiceImpl %s", android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
        }
        for (int i = 0; i < arrayList.size(); i++) {
            try {
                android.os.ServiceManager.registerForNotifications(arrayList.get(i), this.mServiceListener);
            } catch (android.os.RemoteException e) {
                com.android.server.utils.Slogf.e(TAG, e, "failed to register for service notifications for service %s", arrayList.get(i));
            }
        }
    }

    public java.util.List<android.hardware.radio.RadioManager.ModuleProperties> listModules() {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            try {
                arrayList = new java.util.ArrayList(this.mModules.size());
                for (int i = 0; i < this.mModules.size(); i++) {
                    arrayList.add(this.mModules.valueAt(i).getProperties());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public boolean hasModule(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mModules.contains(i);
        }
        return contains;
    }

    public boolean hasAnyModules() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mModules.size() != 0;
        }
        return z;
    }

    @android.annotation.Nullable
    public android.hardware.radio.ITuner openSession(int i, @android.annotation.Nullable android.hardware.radio.RadioManager.BandConfig bandConfig, boolean z, android.hardware.radio.ITunerCallback iTunerCallback) throws android.os.RemoteException {
        if (DEBUG) {
            com.android.server.utils.Slogf.d(TAG, "Open AIDL radio session");
        }
        if (!com.android.server.broadcastradio.RadioServiceUserController.isCurrentOrSystemUser()) {
            com.android.server.utils.Slogf.e(TAG, "Cannot open tuner on AIDL HAL client for non-current user");
            throw new java.lang.IllegalStateException("Cannot open session for non-current user");
        }
        java.util.Objects.requireNonNull(iTunerCallback);
        if (!z) {
            throw new java.lang.IllegalArgumentException("Non-audio sessions not supported with AIDL HAL");
        }
        synchronized (this.mLock) {
            try {
                com.android.server.broadcastradio.aidl.RadioModule radioModule = this.mModules.get(i);
                if (radioModule == null) {
                    com.android.server.utils.Slogf.e(TAG, "Invalid module ID %d", java.lang.Integer.valueOf(i));
                    return null;
                }
                com.android.server.broadcastradio.aidl.TunerSession openSession = radioModule.openSession(iTunerCallback);
                if (bandConfig != null) {
                    openSession.setConfiguration(bandConfig);
                }
                return openSession;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.hardware.radio.ICloseHandle addAnnouncementListener(int[] iArr, android.hardware.radio.IAnnouncementListener iAnnouncementListener) {
        boolean z;
        if (DEBUG) {
            com.android.server.utils.Slogf.d(TAG, "Add AnnouncementListener with enable types %s", java.util.Arrays.toString(iArr));
        }
        com.android.server.broadcastradio.aidl.AnnouncementAggregator announcementAggregator = new com.android.server.broadcastradio.aidl.AnnouncementAggregator(iAnnouncementListener, this.mLock);
        synchronized (this.mLock) {
            z = false;
            for (int i = 0; i < this.mModules.size(); i++) {
                try {
                    announcementAggregator.watchModule(this.mModules.valueAt(i), iArr);
                    z = true;
                } catch (java.lang.UnsupportedOperationException e) {
                    com.android.server.utils.Slogf.w(TAG, e, "Announcements not supported for this module", new java.lang.Object[0]);
                }
            }
        }
        if (!z) {
            com.android.server.utils.Slogf.w(TAG, "There are no HAL modules that support announcements");
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
                indentingPrintWriter.printf("Radio modules [%d]:\n", new java.lang.Object[]{java.lang.Integer.valueOf(this.mModules.size())});
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mModules.size(); i++) {
                    indentingPrintWriter.printf("Module id=%d:\n", new java.lang.Object[]{java.lang.Integer.valueOf(this.mModules.keyAt(i))});
                    indentingPrintWriter.increaseIndent();
                    this.mModules.valueAt(i).dumpInfo(indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
