package com.android.server.broadcastradio.aidl;

/* loaded from: classes.dex */
public final class AnnouncementAggregator extends android.hardware.radio.ICloseHandle.Stub {

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsClosed;
    private final android.hardware.radio.IAnnouncementListener mListener;
    private final java.lang.Object mLock;
    private static final java.lang.String TAG = "BcRadioAidlSrv.AnnAggr";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private final android.os.IBinder.DeathRecipient mDeathRecipient = new com.android.server.broadcastradio.aidl.AnnouncementAggregator.DeathRecipient();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<com.android.server.broadcastradio.aidl.AnnouncementAggregator.ModuleWatcher> mModuleWatchers = new java.util.ArrayList();

    public AnnouncementAggregator(android.hardware.radio.IAnnouncementListener iAnnouncementListener, java.lang.Object obj) {
        java.util.Objects.requireNonNull(iAnnouncementListener, "listener cannot be null");
        this.mListener = iAnnouncementListener;
        java.util.Objects.requireNonNull(obj, "lock cannot be null");
        this.mLock = obj;
        try {
            iAnnouncementListener.asBinder().linkToDeath(this.mDeathRecipient, 0);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private final class ModuleWatcher extends android.hardware.radio.IAnnouncementListener.Stub {

        @android.annotation.Nullable
        private android.hardware.radio.ICloseHandle mCloseHandle;
        public java.util.List<android.hardware.radio.Announcement> mCurrentList;

        private ModuleWatcher() {
            this.mCurrentList = new java.util.ArrayList();
        }

        public void onListUpdated(java.util.List<android.hardware.radio.Announcement> list) {
            if (com.android.server.broadcastradio.aidl.AnnouncementAggregator.DEBUG) {
                com.android.server.utils.Slogf.d(com.android.server.broadcastradio.aidl.AnnouncementAggregator.TAG, "onListUpdate for %s", list);
            }
            java.util.Objects.requireNonNull(list, "active cannot be null");
            this.mCurrentList = list;
            com.android.server.broadcastradio.aidl.AnnouncementAggregator.this.onListUpdated();
        }

        public void setCloseHandle(android.hardware.radio.ICloseHandle iCloseHandle) {
            if (com.android.server.broadcastradio.aidl.AnnouncementAggregator.DEBUG) {
                com.android.server.utils.Slogf.d(com.android.server.broadcastradio.aidl.AnnouncementAggregator.TAG, "Set close handle %s", iCloseHandle);
            }
            java.util.Objects.requireNonNull(iCloseHandle, "closeHandle cannot be null");
            this.mCloseHandle = iCloseHandle;
        }

        public void close() throws android.os.RemoteException {
            if (com.android.server.broadcastradio.aidl.AnnouncementAggregator.DEBUG) {
                com.android.server.utils.Slogf.d(com.android.server.broadcastradio.aidl.AnnouncementAggregator.TAG, "Close module watcher.");
            }
            if (this.mCloseHandle != null) {
                this.mCloseHandle.close();
            }
        }

        public void dumpInfo(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.printf("ModuleWatcher:\n", new java.lang.Object[0]);
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.printf("Close handle: %s\n", new java.lang.Object[]{this.mCloseHandle});
            indentingPrintWriter.printf("Current announcement list: %s\n", new java.lang.Object[]{this.mCurrentList});
            indentingPrintWriter.decreaseIndent();
        }
    }

    private class DeathRecipient implements android.os.IBinder.DeathRecipient {
        private DeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            try {
                com.android.server.broadcastradio.aidl.AnnouncementAggregator.this.close();
            } catch (android.os.RemoteException e) {
                com.android.server.utils.Slogf.e(com.android.server.broadcastradio.aidl.AnnouncementAggregator.TAG, e, "Cannot close Announcement aggregator for DeathRecipient", new java.lang.Object[0]);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onListUpdated() {
        if (DEBUG) {
            com.android.server.utils.Slogf.d(TAG, "onListUpdated()");
        }
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    com.android.server.utils.Slogf.e(TAG, "Announcement aggregator is closed, it shouldn't receive callbacks");
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mModuleWatchers.size());
                for (int i = 0; i < this.mModuleWatchers.size(); i++) {
                    arrayList.addAll(this.mModuleWatchers.get(i).mCurrentList);
                }
                try {
                    this.mListener.onListUpdated(arrayList);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(TAG, e, "mListener.onListUpdated() failed", new java.lang.Object[0]);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void watchModule(com.android.server.broadcastradio.aidl.RadioModule radioModule, int[] iArr) {
        if (DEBUG) {
            com.android.server.utils.Slogf.d(TAG, "Watch module for %s with enabled types %s", radioModule, java.util.Arrays.toString(iArr));
        }
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    throw new java.lang.IllegalStateException("Failed to watch modulesince announcement aggregator has already been closed");
                }
                com.android.server.broadcastradio.aidl.AnnouncementAggregator.ModuleWatcher moduleWatcher = new com.android.server.broadcastradio.aidl.AnnouncementAggregator.ModuleWatcher();
                try {
                    moduleWatcher.setCloseHandle(radioModule.addAnnouncementListener(moduleWatcher, iArr));
                    this.mModuleWatchers.add(moduleWatcher);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(TAG, e, "Failed to add announcement listener", new java.lang.Object[0]);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void close() throws android.os.RemoteException {
        if (DEBUG) {
            com.android.server.utils.Slogf.d(TAG, "Close watchModule");
        }
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    com.android.server.utils.Slogf.w(TAG, "Announcement aggregator has already been closed.");
                    return;
                }
                this.mListener.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
                for (int i = 0; i < this.mModuleWatchers.size(); i++) {
                    com.android.server.broadcastradio.aidl.AnnouncementAggregator.ModuleWatcher moduleWatcher = this.mModuleWatchers.get(i);
                    try {
                        moduleWatcher.close();
                    } catch (java.lang.Exception e) {
                        com.android.server.utils.Slogf.e(TAG, "Failed to close module watcher %s: %s", moduleWatcher, e);
                    }
                }
                this.mModuleWatchers.clear();
                this.mIsClosed = true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
        indentingPrintWriter.printf("AnnouncementAggregator\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.printf("Is session closed? %s\n", new java.lang.Object[]{this.mIsClosed ? "Yes" : "No"});
                indentingPrintWriter.printf("Module Watchers [%d]:\n", new java.lang.Object[]{java.lang.Integer.valueOf(this.mModuleWatchers.size())});
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mModuleWatchers.size(); i++) {
                    this.mModuleWatchers.get(i).dumpInfo(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }
}
