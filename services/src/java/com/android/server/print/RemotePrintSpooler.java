package com.android.server.print;

/* loaded from: classes2.dex */
final class RemotePrintSpooler {
    private static final long BIND_SPOOLER_SERVICE_TIMEOUT;
    private static final boolean DEBUG = false;
    private static final java.lang.String LOG_TAG = "RemotePrintSpooler";
    private final com.android.server.print.RemotePrintSpooler.PrintSpoolerCallbacks mCallbacks;
    private boolean mCanUnbind;
    private final android.content.Context mContext;
    private boolean mDestroyed;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsBinding;
    private boolean mIsLowPriority;
    private android.print.IPrintSpooler mRemoteInstance;
    private final android.os.UserHandle mUserHandle;
    private final java.lang.Object mLock = new java.lang.Object();
    private final com.android.server.print.RemotePrintSpooler.GetPrintJobInfosCaller mGetPrintJobInfosCaller = new com.android.server.print.RemotePrintSpooler.GetPrintJobInfosCaller();
    private final com.android.server.print.RemotePrintSpooler.GetPrintJobInfoCaller mGetPrintJobInfoCaller = new com.android.server.print.RemotePrintSpooler.GetPrintJobInfoCaller();
    private final com.android.server.print.RemotePrintSpooler.SetPrintJobStateCaller mSetPrintJobStatusCaller = new com.android.server.print.RemotePrintSpooler.SetPrintJobStateCaller();
    private final com.android.server.print.RemotePrintSpooler.SetPrintJobTagCaller mSetPrintJobTagCaller = new com.android.server.print.RemotePrintSpooler.SetPrintJobTagCaller();
    private final com.android.server.print.RemotePrintSpooler.OnCustomPrinterIconLoadedCaller mCustomPrinterIconLoadedCaller = new com.android.server.print.RemotePrintSpooler.OnCustomPrinterIconLoadedCaller();
    private final com.android.server.print.RemotePrintSpooler.ClearCustomPrinterIconCacheCaller mClearCustomPrinterIconCache = new com.android.server.print.RemotePrintSpooler.ClearCustomPrinterIconCacheCaller();
    private final com.android.server.print.RemotePrintSpooler.GetCustomPrinterIconCaller mGetCustomPrinterIconCaller = new com.android.server.print.RemotePrintSpooler.GetCustomPrinterIconCaller();
    private final android.content.ServiceConnection mServiceConnection = new com.android.server.print.RemotePrintSpooler.MyServiceConnection();
    private final com.android.server.print.RemotePrintSpooler.PrintSpoolerClient mClient = new com.android.server.print.RemotePrintSpooler.PrintSpoolerClient(this);
    private final android.content.Intent mIntent = new android.content.Intent();

    public interface PrintSpoolerCallbacks {
        void onAllPrintJobsForServiceHandled(android.content.ComponentName componentName);

        void onPrintJobQueued(android.print.PrintJobInfo printJobInfo);

        void onPrintJobStateChanged(android.print.PrintJobInfo printJobInfo);
    }

    static {
        BIND_SPOOLER_SERVICE_TIMEOUT = android.os.Build.IS_ENG ? 120000L : com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
    }

    public RemotePrintSpooler(android.content.Context context, int i, boolean z, com.android.server.print.RemotePrintSpooler.PrintSpoolerCallbacks printSpoolerCallbacks) {
        this.mContext = context;
        this.mUserHandle = new android.os.UserHandle(i);
        this.mCallbacks = printSpoolerCallbacks;
        this.mIsLowPriority = z;
        this.mIntent.setComponent(new android.content.ComponentName("com.android.printspooler", "com.android.printspooler.model.PrintSpoolerService"));
    }

    public void increasePriority() {
        if (this.mIsLowPriority) {
            this.mIsLowPriority = false;
            synchronized (this.mLock) {
                throwIfDestroyedLocked();
                while (!this.mCanUnbind) {
                    try {
                        this.mLock.wait();
                    } catch (java.lang.InterruptedException e) {
                        android.util.Slog.e(LOG_TAG, "Interrupted while waiting for operation to complete");
                    }
                }
                unbindLocked();
            }
        }
    }

    public final java.util.List<android.print.PrintJobInfo> getPrintJobInfos(android.content.ComponentName componentName, int i, int i2) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                java.util.List<android.print.PrintJobInfo> printJobInfos = this.mGetPrintJobInfosCaller.getPrintJobInfos(getRemoteInstanceLazy(), componentName, i, i2);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
                return printJobInfos;
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error getting print jobs.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                    return null;
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void createPrintJob(android.print.PrintJobInfo printJobInfo) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        boolean z = true;
        z = true;
        try {
            try {
                getRemoteInstanceLazy().createPrintJob(printJobInfo);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    java.lang.Object obj = this.mLock;
                    obj.notifyAll();
                    z = obj;
                }
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error creating print job.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    java.lang.Object obj2 = this.mLock;
                    obj2.notifyAll();
                    z = obj2;
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = z;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final void writePrintJobData(android.os.ParcelFileDescriptor parcelFileDescriptor, android.print.PrintJobId printJobId) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                getRemoteInstanceLazy().writePrintJobData(parcelFileDescriptor, printJobId);
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error writing print job data.", e);
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            }
        } catch (java.lang.Throwable th) {
            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final android.print.PrintJobInfo getPrintJobInfo(android.print.PrintJobId printJobId, int i) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                android.print.PrintJobInfo printJobInfo = this.mGetPrintJobInfoCaller.getPrintJobInfo(getRemoteInstanceLazy(), printJobId, i);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
                return printJobInfo;
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error getting print job info.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                    return null;
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final boolean setPrintJobState(android.print.PrintJobId printJobId, int i, java.lang.String str) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                boolean printJobState = this.mSetPrintJobStatusCaller.setPrintJobState(getRemoteInstanceLazy(), printJobId, i, str);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
                return printJobState;
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error setting print job state.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                    return false;
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final void setProgress(@android.annotation.NonNull android.print.PrintJobId printJobId, float f) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                getRemoteInstanceLazy().setProgress(printJobId, f);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error setting progress.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final void setStatus(@android.annotation.NonNull android.print.PrintJobId printJobId, @android.annotation.Nullable java.lang.CharSequence charSequence) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                getRemoteInstanceLazy().setStatus(printJobId, charSequence);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error setting status.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final void setStatus(@android.annotation.NonNull android.print.PrintJobId printJobId, int i, @android.annotation.NonNull java.lang.CharSequence charSequence) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                getRemoteInstanceLazy().setStatusRes(printJobId, i, charSequence);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error setting status.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final void onCustomPrinterIconLoaded(@android.annotation.NonNull android.print.PrinterId printerId, @android.annotation.Nullable android.graphics.drawable.Icon icon) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                this.mCustomPrinterIconLoadedCaller.onCustomPrinterIconLoaded(getRemoteInstanceLazy(), printerId, icon);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error loading new custom printer icon.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public final android.graphics.drawable.Icon getCustomPrinterIcon(@android.annotation.NonNull android.print.PrinterId printerId) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                android.graphics.drawable.Icon customPrinterIcon = this.mGetCustomPrinterIconCaller.getCustomPrinterIcon(getRemoteInstanceLazy(), printerId);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
                return customPrinterIcon;
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error getting custom printer icon.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                    return null;
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public void clearCustomPrinterIconCache() {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                this.mClearCustomPrinterIconCache.clearCustomPrinterIconCache(getRemoteInstanceLazy());
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error clearing custom printer icon cache.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final boolean setPrintJobTag(android.print.PrintJobId printJobId, java.lang.String str) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                boolean printJobTag = this.mSetPrintJobTagCaller.setPrintJobTag(getRemoteInstanceLazy(), printJobId, str);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
                return printJobTag;
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error setting print job tag.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                    return false;
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final void setPrintJobCancelling(android.print.PrintJobId printJobId, boolean z) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                getRemoteInstanceLazy().setPrintJobCancelling(printJobId, z);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error setting print job cancelling.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void pruneApprovedPrintServices(java.util.List<android.content.ComponentName> list) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        boolean z = true;
        z = true;
        try {
            try {
                getRemoteInstanceLazy().pruneApprovedPrintServices(list);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    java.lang.Object obj = this.mLock;
                    obj.notifyAll();
                    z = obj;
                }
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error pruning approved print services.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    java.lang.Object obj2 = this.mLock;
                    obj2.notifyAll();
                    z = obj2;
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = z;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void removeObsoletePrintJobs() {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        boolean z = true;
        z = true;
        try {
            try {
                getRemoteInstanceLazy().removeObsoletePrintJobs();
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    java.lang.Object obj = this.mLock;
                    obj.notifyAll();
                    z = obj;
                }
            } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.e(LOG_TAG, "Error removing obsolete print jobs .", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    java.lang.Object obj2 = this.mLock;
                    obj2.notifyAll();
                    z = obj2;
                }
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = z;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final void destroy() {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            unbindLocked();
            this.mDestroyed = true;
            this.mCanUnbind = false;
        }
    }

    public void dump(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream) {
        synchronized (this.mLock) {
            dualDumpOutputStream.write("is_destroyed", 1133871366145L, this.mDestroyed);
            dualDumpOutputStream.write("is_bound", 1133871366146L, this.mRemoteInstance != null);
        }
        try {
            if (dualDumpOutputStream.isProto()) {
                dualDumpOutputStream.write((java.lang.String) null, 1146756268035L, com.android.internal.os.TransferPipe.dumpAsync(getRemoteInstanceLazy().asBinder(), new java.lang.String[]{"--proto"}));
            } else {
                dualDumpOutputStream.writeNested("internal_state", com.android.internal.os.TransferPipe.dumpAsync(getRemoteInstanceLazy().asBinder(), new java.lang.String[0]));
            }
        } catch (android.os.RemoteException | java.io.IOException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
            android.util.Slog.e(LOG_TAG, "Failed to dump remote instance", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAllPrintJobsHandled() {
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            unbindLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPrintJobStateChanged(android.print.PrintJobInfo printJobInfo) {
        this.mCallbacks.onPrintJobStateChanged(printJobInfo);
    }

    private android.print.IPrintSpooler getRemoteInstanceLazy() throws java.util.concurrent.TimeoutException, java.lang.InterruptedException {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteInstance != null) {
                    return this.mRemoteInstance;
                }
                bindLocked();
                return this.mRemoteInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void bindLocked() throws java.util.concurrent.TimeoutException, java.lang.InterruptedException {
        int i;
        while (this.mIsBinding) {
            this.mLock.wait();
        }
        if (this.mRemoteInstance != null) {
            return;
        }
        this.mIsBinding = true;
        try {
            if (this.mIsLowPriority) {
                i = 1;
            } else {
                i = android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN;
            }
            this.mContext.bindServiceAsUser(this.mIntent, this.mServiceConnection, i, this.mUserHandle);
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            while (this.mRemoteInstance == null) {
                long uptimeMillis2 = BIND_SPOOLER_SERVICE_TIMEOUT - (android.os.SystemClock.uptimeMillis() - uptimeMillis);
                if (uptimeMillis2 <= 0) {
                    throw new java.util.concurrent.TimeoutException("Cannot get spooler!");
                }
                this.mLock.wait(uptimeMillis2);
            }
            this.mCanUnbind = true;
        } finally {
            this.mIsBinding = false;
            this.mLock.notifyAll();
        }
    }

    private void unbindLocked() {
        if (this.mRemoteInstance == null) {
            return;
        }
        while (!this.mCanUnbind) {
            try {
                this.mLock.wait();
            } catch (java.lang.InterruptedException e) {
            }
        }
        clearClientLocked();
        this.mRemoteInstance = null;
        this.mContext.unbindService(this.mServiceConnection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setClientLocked() {
        try {
            this.mRemoteInstance.setClient(this.mClient);
        } catch (android.os.RemoteException e) {
            android.util.Slog.d(LOG_TAG, "Error setting print spooler client", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearClientLocked() {
        try {
            this.mRemoteInstance.setClient((android.print.IPrintSpoolerClient) null);
        } catch (android.os.RemoteException e) {
            android.util.Slog.d(LOG_TAG, "Error clearing print spooler client", e);
        }
    }

    private void throwIfDestroyedLocked() {
        if (this.mDestroyed) {
            throw new java.lang.IllegalStateException("Cannot interact with a destroyed instance.");
        }
    }

    private void throwIfCalledOnMainThread() {
        if (java.lang.Thread.currentThread() == this.mContext.getMainLooper().getThread()) {
            throw new java.lang.RuntimeException("Cannot invoke on the main thread");
        }
    }

    private final class MyServiceConnection implements android.content.ServiceConnection {
        private MyServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            synchronized (com.android.server.print.RemotePrintSpooler.this.mLock) {
                com.android.server.print.RemotePrintSpooler.this.mRemoteInstance = android.print.IPrintSpooler.Stub.asInterface(iBinder);
                com.android.server.print.RemotePrintSpooler.this.setClientLocked();
                com.android.server.print.RemotePrintSpooler.this.mLock.notifyAll();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            synchronized (com.android.server.print.RemotePrintSpooler.this.mLock) {
                try {
                    if (com.android.server.print.RemotePrintSpooler.this.mRemoteInstance != null) {
                        com.android.server.print.RemotePrintSpooler.this.clearClientLocked();
                        com.android.server.print.RemotePrintSpooler.this.mRemoteInstance = null;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private static final class GetPrintJobInfosCaller extends android.util.TimedRemoteCaller<java.util.List<android.print.PrintJobInfo>> {
        private final android.print.IPrintSpoolerCallbacks mCallback;

        public GetPrintJobInfosCaller() {
            super(5000L);
            this.mCallback = new com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks() { // from class: com.android.server.print.RemotePrintSpooler.GetPrintJobInfosCaller.1
                @Override // com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks
                public void onGetPrintJobInfosResult(java.util.List<android.print.PrintJobInfo> list, int i) {
                    com.android.server.print.RemotePrintSpooler.GetPrintJobInfosCaller.this.onRemoteMethodResult(list, i);
                }
            };
        }

        public java.util.List<android.print.PrintJobInfo> getPrintJobInfos(android.print.IPrintSpooler iPrintSpooler, android.content.ComponentName componentName, int i, int i2) throws android.os.RemoteException, java.util.concurrent.TimeoutException {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.getPrintJobInfos(this.mCallback, componentName, i, i2, onBeforeRemoteCall);
            return (java.util.List) getResultTimed(onBeforeRemoteCall);
        }
    }

    private static final class GetPrintJobInfoCaller extends android.util.TimedRemoteCaller<android.print.PrintJobInfo> {
        private final android.print.IPrintSpoolerCallbacks mCallback;

        public GetPrintJobInfoCaller() {
            super(5000L);
            this.mCallback = new com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks() { // from class: com.android.server.print.RemotePrintSpooler.GetPrintJobInfoCaller.1
                @Override // com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks
                public void onGetPrintJobInfoResult(android.print.PrintJobInfo printJobInfo, int i) {
                    com.android.server.print.RemotePrintSpooler.GetPrintJobInfoCaller.this.onRemoteMethodResult(printJobInfo, i);
                }
            };
        }

        public android.print.PrintJobInfo getPrintJobInfo(android.print.IPrintSpooler iPrintSpooler, android.print.PrintJobId printJobId, int i) throws android.os.RemoteException, java.util.concurrent.TimeoutException {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.getPrintJobInfo(printJobId, this.mCallback, i, onBeforeRemoteCall);
            return (android.print.PrintJobInfo) getResultTimed(onBeforeRemoteCall);
        }
    }

    private static final class SetPrintJobStateCaller extends android.util.TimedRemoteCaller<java.lang.Boolean> {
        private final android.print.IPrintSpoolerCallbacks mCallback;

        public SetPrintJobStateCaller() {
            super(5000L);
            this.mCallback = new com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks() { // from class: com.android.server.print.RemotePrintSpooler.SetPrintJobStateCaller.1
                @Override // com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks
                public void onSetPrintJobStateResult(boolean z, int i) {
                    com.android.server.print.RemotePrintSpooler.SetPrintJobStateCaller.this.onRemoteMethodResult(java.lang.Boolean.valueOf(z), i);
                }
            };
        }

        public boolean setPrintJobState(android.print.IPrintSpooler iPrintSpooler, android.print.PrintJobId printJobId, int i, java.lang.String str) throws android.os.RemoteException, java.util.concurrent.TimeoutException {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.setPrintJobState(printJobId, i, str, this.mCallback, onBeforeRemoteCall);
            return ((java.lang.Boolean) getResultTimed(onBeforeRemoteCall)).booleanValue();
        }
    }

    private static final class SetPrintJobTagCaller extends android.util.TimedRemoteCaller<java.lang.Boolean> {
        private final android.print.IPrintSpoolerCallbacks mCallback;

        public SetPrintJobTagCaller() {
            super(5000L);
            this.mCallback = new com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks() { // from class: com.android.server.print.RemotePrintSpooler.SetPrintJobTagCaller.1
                @Override // com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks
                public void onSetPrintJobTagResult(boolean z, int i) {
                    com.android.server.print.RemotePrintSpooler.SetPrintJobTagCaller.this.onRemoteMethodResult(java.lang.Boolean.valueOf(z), i);
                }
            };
        }

        public boolean setPrintJobTag(android.print.IPrintSpooler iPrintSpooler, android.print.PrintJobId printJobId, java.lang.String str) throws android.os.RemoteException, java.util.concurrent.TimeoutException {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.setPrintJobTag(printJobId, str, this.mCallback, onBeforeRemoteCall);
            return ((java.lang.Boolean) getResultTimed(onBeforeRemoteCall)).booleanValue();
        }
    }

    private static final class OnCustomPrinterIconLoadedCaller extends android.util.TimedRemoteCaller<java.lang.Void> {
        private final android.print.IPrintSpoolerCallbacks mCallback;

        public OnCustomPrinterIconLoadedCaller() {
            super(5000L);
            this.mCallback = new com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks() { // from class: com.android.server.print.RemotePrintSpooler.OnCustomPrinterIconLoadedCaller.1
                @Override // com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks
                public void onCustomPrinterIconCached(int i) {
                    com.android.server.print.RemotePrintSpooler.OnCustomPrinterIconLoadedCaller.this.onRemoteMethodResult(null, i);
                }
            };
        }

        public java.lang.Void onCustomPrinterIconLoaded(android.print.IPrintSpooler iPrintSpooler, android.print.PrinterId printerId, android.graphics.drawable.Icon icon) throws android.os.RemoteException, java.util.concurrent.TimeoutException {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.onCustomPrinterIconLoaded(printerId, icon, this.mCallback, onBeforeRemoteCall);
            return (java.lang.Void) getResultTimed(onBeforeRemoteCall);
        }
    }

    private static final class ClearCustomPrinterIconCacheCaller extends android.util.TimedRemoteCaller<java.lang.Void> {
        private final android.print.IPrintSpoolerCallbacks mCallback;

        public ClearCustomPrinterIconCacheCaller() {
            super(5000L);
            this.mCallback = new com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks() { // from class: com.android.server.print.RemotePrintSpooler.ClearCustomPrinterIconCacheCaller.1
                @Override // com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks
                public void customPrinterIconCacheCleared(int i) {
                    com.android.server.print.RemotePrintSpooler.ClearCustomPrinterIconCacheCaller.this.onRemoteMethodResult(null, i);
                }
            };
        }

        public java.lang.Void clearCustomPrinterIconCache(android.print.IPrintSpooler iPrintSpooler) throws android.os.RemoteException, java.util.concurrent.TimeoutException {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.clearCustomPrinterIconCache(this.mCallback, onBeforeRemoteCall);
            return (java.lang.Void) getResultTimed(onBeforeRemoteCall);
        }
    }

    private static final class GetCustomPrinterIconCaller extends android.util.TimedRemoteCaller<android.graphics.drawable.Icon> {
        private final android.print.IPrintSpoolerCallbacks mCallback;

        public GetCustomPrinterIconCaller() {
            super(5000L);
            this.mCallback = new com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks() { // from class: com.android.server.print.RemotePrintSpooler.GetCustomPrinterIconCaller.1
                @Override // com.android.server.print.RemotePrintSpooler.BasePrintSpoolerServiceCallbacks
                public void onGetCustomPrinterIconResult(android.graphics.drawable.Icon icon, int i) {
                    com.android.server.print.RemotePrintSpooler.GetCustomPrinterIconCaller.this.onRemoteMethodResult(icon, i);
                }
            };
        }

        public android.graphics.drawable.Icon getCustomPrinterIcon(android.print.IPrintSpooler iPrintSpooler, android.print.PrinterId printerId) throws android.os.RemoteException, java.util.concurrent.TimeoutException {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.getCustomPrinterIcon(printerId, this.mCallback, onBeforeRemoteCall);
            return (android.graphics.drawable.Icon) getResultTimed(onBeforeRemoteCall);
        }
    }

    private static abstract class BasePrintSpoolerServiceCallbacks extends android.print.IPrintSpoolerCallbacks.Stub {
        private BasePrintSpoolerServiceCallbacks() {
        }

        public void onGetPrintJobInfosResult(java.util.List<android.print.PrintJobInfo> list, int i) {
        }

        public void onGetPrintJobInfoResult(android.print.PrintJobInfo printJobInfo, int i) {
        }

        public void onCancelPrintJobResult(boolean z, int i) {
        }

        public void onSetPrintJobStateResult(boolean z, int i) {
        }

        public void onSetPrintJobTagResult(boolean z, int i) {
        }

        public void onCustomPrinterIconCached(int i) {
        }

        public void onGetCustomPrinterIconResult(@android.annotation.Nullable android.graphics.drawable.Icon icon, int i) {
        }

        public void customPrinterIconCacheCleared(int i) {
        }
    }

    private static final class PrintSpoolerClient extends android.print.IPrintSpoolerClient.Stub {
        private final java.lang.ref.WeakReference<com.android.server.print.RemotePrintSpooler> mWeakSpooler;

        public PrintSpoolerClient(com.android.server.print.RemotePrintSpooler remotePrintSpooler) {
            this.mWeakSpooler = new java.lang.ref.WeakReference<>(remotePrintSpooler);
        }

        public void onPrintJobQueued(android.print.PrintJobInfo printJobInfo) {
            com.android.server.print.RemotePrintSpooler remotePrintSpooler = this.mWeakSpooler.get();
            if (remotePrintSpooler != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    remotePrintSpooler.mCallbacks.onPrintJobQueued(printJobInfo);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public void onAllPrintJobsForServiceHandled(android.content.ComponentName componentName) {
            com.android.server.print.RemotePrintSpooler remotePrintSpooler = this.mWeakSpooler.get();
            if (remotePrintSpooler != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    remotePrintSpooler.mCallbacks.onAllPrintJobsForServiceHandled(componentName);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public void onAllPrintJobsHandled() {
            com.android.server.print.RemotePrintSpooler remotePrintSpooler = this.mWeakSpooler.get();
            if (remotePrintSpooler != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    remotePrintSpooler.onAllPrintJobsHandled();
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public void onPrintJobStateChanged(android.print.PrintJobInfo printJobInfo) {
            com.android.server.print.RemotePrintSpooler remotePrintSpooler = this.mWeakSpooler.get();
            if (remotePrintSpooler != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    remotePrintSpooler.onPrintJobStateChanged(printJobInfo);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }
}
