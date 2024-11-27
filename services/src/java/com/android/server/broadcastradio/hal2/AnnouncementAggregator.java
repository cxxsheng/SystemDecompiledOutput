package com.android.server.broadcastradio.hal2;

/* loaded from: classes.dex */
public class AnnouncementAggregator extends android.hardware.radio.ICloseHandle.Stub {
    private static final java.lang.String TAG = "BcRadio2Srv.AnnAggr";

    @android.annotation.NonNull
    private final android.hardware.radio.IAnnouncementListener mListener;
    private final java.lang.Object mLock;
    private final android.os.IBinder.DeathRecipient mDeathRecipient = new com.android.server.broadcastradio.hal2.AnnouncementAggregator.DeathRecipient();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Collection<com.android.server.broadcastradio.hal2.AnnouncementAggregator.ModuleWatcher> mModuleWatchers = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsClosed = false;

    public AnnouncementAggregator(@android.annotation.NonNull android.hardware.radio.IAnnouncementListener iAnnouncementListener, @android.annotation.NonNull java.lang.Object obj) {
        java.util.Objects.requireNonNull(iAnnouncementListener);
        this.mListener = iAnnouncementListener;
        java.util.Objects.requireNonNull(obj);
        this.mLock = obj;
        try {
            iAnnouncementListener.asBinder().linkToDeath(this.mDeathRecipient, 0);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private class ModuleWatcher extends android.hardware.radio.IAnnouncementListener.Stub {

        @android.annotation.NonNull
        public java.util.List<android.hardware.radio.Announcement> currentList;

        @android.annotation.Nullable
        private android.hardware.radio.ICloseHandle mCloseHandle;

        private ModuleWatcher() {
            this.currentList = new java.util.ArrayList();
        }

        public void onListUpdated(java.util.List<android.hardware.radio.Announcement> list) {
            java.util.Objects.requireNonNull(list);
            this.currentList = list;
            com.android.server.broadcastradio.hal2.AnnouncementAggregator.this.onListUpdated();
        }

        public void setCloseHandle(@android.annotation.NonNull android.hardware.radio.ICloseHandle iCloseHandle) {
            java.util.Objects.requireNonNull(iCloseHandle);
            this.mCloseHandle = iCloseHandle;
        }

        public void close() throws android.os.RemoteException {
            if (this.mCloseHandle != null) {
                this.mCloseHandle.close();
            }
        }
    }

    private class DeathRecipient implements android.os.IBinder.DeathRecipient {
        private DeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            try {
                com.android.server.broadcastradio.hal2.AnnouncementAggregator.this.close();
            } catch (android.os.RemoteException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onListUpdated() {
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    android.util.Slog.e(TAG, "Announcement aggregator is closed, it shouldn't receive callbacks");
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.util.Iterator<com.android.server.broadcastradio.hal2.AnnouncementAggregator.ModuleWatcher> it = this.mModuleWatchers.iterator();
                while (it.hasNext()) {
                    arrayList.addAll(it.next().currentList);
                }
                try {
                    this.mListener.onListUpdated(arrayList);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "mListener.onListUpdated() failed: ", e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void watchModule(@android.annotation.NonNull com.android.server.broadcastradio.hal2.RadioModule radioModule, @android.annotation.NonNull int[] iArr) {
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    throw new java.lang.IllegalStateException("Failed to watch modulesince announcement aggregator has already been closed");
                }
                com.android.server.broadcastradio.hal2.AnnouncementAggregator.ModuleWatcher moduleWatcher = new com.android.server.broadcastradio.hal2.AnnouncementAggregator.ModuleWatcher();
                try {
                    moduleWatcher.setCloseHandle(radioModule.addAnnouncementListener(iArr, moduleWatcher));
                    this.mModuleWatchers.add(moduleWatcher);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "Failed to add announcement listener", e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void close() throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    return;
                }
                this.mIsClosed = true;
                this.mListener.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
                java.util.Iterator<com.android.server.broadcastradio.hal2.AnnouncementAggregator.ModuleWatcher> it = this.mModuleWatchers.iterator();
                while (it.hasNext()) {
                    it.next().close();
                }
                this.mModuleWatchers.clear();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
