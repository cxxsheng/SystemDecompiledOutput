package com.android.server.print;

/* loaded from: classes2.dex */
final class RemotePrintService implements android.os.IBinder.DeathRecipient {
    private static final boolean DEBUG = false;
    private static final java.lang.String LOG_TAG = "RemotePrintService";
    private boolean mBinding;
    private final com.android.server.print.RemotePrintService.PrintServiceCallbacks mCallbacks;
    private final android.content.ComponentName mComponentName;
    private final android.content.Context mContext;
    private boolean mDestroyed;
    private java.util.List<android.print.PrinterId> mDiscoveryPriorityList;
    private boolean mHasActivePrintJobs;
    private boolean mHasPrinterDiscoverySession;
    private final android.content.Intent mIntent;
    private android.printservice.IPrintService mPrintService;
    private boolean mServiceDied;
    private final com.android.server.print.RemotePrintSpooler mSpooler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<android.print.PrinterId> mTrackedPrinterList;
    private final int mUserId;
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.List<java.lang.Runnable> mPendingCommands = new java.util.ArrayList();
    private final android.content.ServiceConnection mServiceConnection = new com.android.server.print.RemotePrintService.RemoteServiceConneciton();
    private final com.android.server.print.RemotePrintService.RemotePrintServiceClient mPrintServiceClient = new com.android.server.print.RemotePrintService.RemotePrintServiceClient(this);

    public interface PrintServiceCallbacks {
        void onCustomPrinterIconLoaded(android.print.PrinterId printerId, android.graphics.drawable.Icon icon);

        void onPrintersAdded(java.util.List<android.print.PrinterInfo> list);

        void onPrintersRemoved(java.util.List<android.print.PrinterId> list);

        void onServiceDied(com.android.server.print.RemotePrintService remotePrintService);
    }

    public RemotePrintService(android.content.Context context, android.content.ComponentName componentName, int i, com.android.server.print.RemotePrintSpooler remotePrintSpooler, com.android.server.print.RemotePrintService.PrintServiceCallbacks printServiceCallbacks) {
        this.mContext = context;
        this.mCallbacks = printServiceCallbacks;
        this.mComponentName = componentName;
        this.mIntent = new android.content.Intent().setComponent(this.mComponentName);
        this.mUserId = i;
        this.mSpooler = remotePrintSpooler;
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public void destroy() {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.print.RemotePrintService) obj).handleDestroy();
            }
        }, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDestroy() {
        stopTrackingAllPrinters();
        if (this.mDiscoveryPriorityList != null) {
            handleStopPrinterDiscovery();
        }
        if (this.mHasPrinterDiscoverySession) {
            handleDestroyPrinterDiscoverySession();
        }
        ensureUnbound();
        this.mDestroyed = true;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.print.RemotePrintService) obj).handleBinderDied();
            }
        }, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBinderDied() {
        if (this.mPrintService != null) {
            this.mPrintService.asBinder().unlinkToDeath(this, 0);
        }
        this.mPrintService = null;
        this.mServiceDied = true;
        this.mCallbacks.onServiceDied(this);
    }

    public void onAllPrintJobsHandled() {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.print.RemotePrintService) obj).handleOnAllPrintJobsHandled();
            }
        }, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnAllPrintJobsHandled() {
        this.mHasActivePrintJobs = false;
        if (!isBound()) {
            if (this.mServiceDied && !this.mHasPrinterDiscoverySession) {
                ensureUnbound();
                return;
            } else {
                ensureBound();
                this.mPendingCommands.add(new java.lang.Runnable() { // from class: com.android.server.print.RemotePrintService.1
                    @Override // java.lang.Runnable
                    public void run() {
                        com.android.server.print.RemotePrintService.this.handleOnAllPrintJobsHandled();
                    }
                });
                return;
            }
        }
        if (!this.mHasPrinterDiscoverySession) {
            ensureUnbound();
        }
    }

    public void onRequestCancelPrintJob(android.print.PrintJobInfo printJobInfo) {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda10
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.print.RemotePrintService) obj).handleRequestCancelPrintJob((android.print.PrintJobInfo) obj2);
            }
        }, this, printJobInfo));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRequestCancelPrintJob(final android.print.PrintJobInfo printJobInfo) {
        if (!isBound()) {
            ensureBound();
            this.mPendingCommands.add(new java.lang.Runnable() { // from class: com.android.server.print.RemotePrintService.2
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.print.RemotePrintService.this.handleRequestCancelPrintJob(printJobInfo);
                }
            });
        } else {
            try {
                this.mPrintService.requestCancelPrintJob(printJobInfo);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error canceling a pring job.", e);
            }
        }
    }

    public void onPrintJobQueued(android.print.PrintJobInfo printJobInfo) {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.print.RemotePrintService) obj).handleOnPrintJobQueued((android.print.PrintJobInfo) obj2);
            }
        }, this, printJobInfo));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnPrintJobQueued(final android.print.PrintJobInfo printJobInfo) {
        this.mHasActivePrintJobs = true;
        if (!isBound()) {
            ensureBound();
            this.mPendingCommands.add(new java.lang.Runnable() { // from class: com.android.server.print.RemotePrintService.3
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.print.RemotePrintService.this.handleOnPrintJobQueued(printJobInfo);
                }
            });
        } else {
            try {
                this.mPrintService.onPrintJobQueued(printJobInfo);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error announcing queued pring job.", e);
            }
        }
    }

    public void createPrinterDiscoverySession() {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.print.RemotePrintService) obj).handleCreatePrinterDiscoverySession();
            }
        }, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCreatePrinterDiscoverySession() {
        this.mHasPrinterDiscoverySession = true;
        if (!isBound()) {
            ensureBound();
            this.mPendingCommands.add(new java.lang.Runnable() { // from class: com.android.server.print.RemotePrintService.4
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.print.RemotePrintService.this.handleCreatePrinterDiscoverySession();
                }
            });
        } else {
            try {
                this.mPrintService.createPrinterDiscoverySession();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error creating printer discovery session.", e);
            }
        }
    }

    public void destroyPrinterDiscoverySession() {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.print.RemotePrintService) obj).handleDestroyPrinterDiscoverySession();
            }
        }, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDestroyPrinterDiscoverySession() {
        this.mHasPrinterDiscoverySession = false;
        if (!isBound()) {
            if (this.mServiceDied && !this.mHasActivePrintJobs) {
                ensureUnbound();
                return;
            } else {
                ensureBound();
                this.mPendingCommands.add(new java.lang.Runnable() { // from class: com.android.server.print.RemotePrintService.5
                    @Override // java.lang.Runnable
                    public void run() {
                        com.android.server.print.RemotePrintService.this.handleDestroyPrinterDiscoverySession();
                    }
                });
                return;
            }
        }
        try {
            this.mPrintService.destroyPrinterDiscoverySession();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(LOG_TAG, "Error destroying printer dicovery session.", e);
        }
        if (!this.mHasActivePrintJobs) {
            ensureUnbound();
        }
    }

    public void startPrinterDiscovery(java.util.List<android.print.PrinterId> list) {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.print.RemotePrintService) obj).handleStartPrinterDiscovery((java.util.List) obj2);
            }
        }, this, list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStartPrinterDiscovery(final java.util.List<android.print.PrinterId> list) {
        this.mDiscoveryPriorityList = new java.util.ArrayList();
        if (list != null) {
            this.mDiscoveryPriorityList.addAll(list);
        }
        if (!isBound()) {
            ensureBound();
            this.mPendingCommands.add(new java.lang.Runnable() { // from class: com.android.server.print.RemotePrintService.6
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.print.RemotePrintService.this.handleStartPrinterDiscovery(list);
                }
            });
        } else {
            try {
                this.mPrintService.startPrinterDiscovery(list);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error starting printer dicovery.", e);
            }
        }
    }

    public void stopPrinterDiscovery() {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.print.RemotePrintService) obj).handleStopPrinterDiscovery();
            }
        }, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStopPrinterDiscovery() {
        this.mDiscoveryPriorityList = null;
        if (!isBound()) {
            ensureBound();
            this.mPendingCommands.add(new java.lang.Runnable() { // from class: com.android.server.print.RemotePrintService.7
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.print.RemotePrintService.this.handleStopPrinterDiscovery();
                }
            });
            return;
        }
        stopTrackingAllPrinters();
        try {
            this.mPrintService.stopPrinterDiscovery();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(LOG_TAG, "Error stopping printer discovery.", e);
        }
    }

    public void validatePrinters(java.util.List<android.print.PrinterId> list) {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.print.RemotePrintService) obj).handleValidatePrinters((java.util.List) obj2);
            }
        }, this, list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleValidatePrinters(final java.util.List<android.print.PrinterId> list) {
        if (!isBound()) {
            ensureBound();
            this.mPendingCommands.add(new java.lang.Runnable() { // from class: com.android.server.print.RemotePrintService.8
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.print.RemotePrintService.this.handleValidatePrinters(list);
                }
            });
        } else {
            try {
                this.mPrintService.validatePrinters(list);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error requesting printers validation.", e);
            }
        }
    }

    public void startPrinterStateTracking(@android.annotation.NonNull android.print.PrinterId printerId) {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.print.RemotePrintService) obj).handleStartPrinterStateTracking((android.print.PrinterId) obj2);
            }
        }, this, printerId));
    }

    public void requestCustomPrinterIcon(@android.annotation.NonNull android.print.PrinterId printerId) {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.print.RemotePrintService) obj).lambda$handleRequestCustomPrinterIcon$0((android.print.PrinterId) obj2);
            }
        }, this, printerId));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleRequestCustomPrinterIcon, reason: merged with bridge method [inline-methods] */
    public void lambda$handleRequestCustomPrinterIcon$0(@android.annotation.NonNull final android.print.PrinterId printerId) {
        if (!isBound()) {
            ensureBound();
            this.mPendingCommands.add(new java.lang.Runnable() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.print.RemotePrintService.this.lambda$handleRequestCustomPrinterIcon$0(printerId);
                }
            });
            return;
        }
        try {
            this.mPrintService.requestCustomPrinterIcon(printerId);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(LOG_TAG, "Error requesting icon for " + printerId, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStartPrinterStateTracking(@android.annotation.NonNull final android.print.PrinterId printerId) {
        synchronized (this.mLock) {
            try {
                if (this.mTrackedPrinterList == null) {
                    this.mTrackedPrinterList = new java.util.ArrayList();
                }
                this.mTrackedPrinterList.add(printerId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (!isBound()) {
            ensureBound();
            this.mPendingCommands.add(new java.lang.Runnable() { // from class: com.android.server.print.RemotePrintService.9
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.print.RemotePrintService.this.handleStartPrinterStateTracking(printerId);
                }
            });
        } else {
            try {
                this.mPrintService.startPrinterStateTracking(printerId);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error requesting start printer tracking.", e);
            }
        }
    }

    public void stopPrinterStateTracking(android.print.PrinterId printerId) {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.RemotePrintService$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.print.RemotePrintService) obj).handleStopPrinterStateTracking((android.print.PrinterId) obj2);
            }
        }, this, printerId));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStopPrinterStateTracking(final android.print.PrinterId printerId) {
        synchronized (this.mLock) {
            try {
                if (this.mTrackedPrinterList == null || !this.mTrackedPrinterList.remove(printerId)) {
                    return;
                }
                if (this.mTrackedPrinterList.isEmpty()) {
                    this.mTrackedPrinterList = null;
                }
                if (!isBound()) {
                    ensureBound();
                    this.mPendingCommands.add(new java.lang.Runnable() { // from class: com.android.server.print.RemotePrintService.10
                        @Override // java.lang.Runnable
                        public void run() {
                            com.android.server.print.RemotePrintService.this.handleStopPrinterStateTracking(printerId);
                        }
                    });
                } else {
                    try {
                        this.mPrintService.stopPrinterStateTracking(printerId);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(LOG_TAG, "Error requesting stop printer tracking.", e);
                    }
                }
            } finally {
            }
        }
    }

    private void stopTrackingAllPrinters() {
        synchronized (this.mLock) {
            try {
                if (this.mTrackedPrinterList == null) {
                    return;
                }
                for (int size = this.mTrackedPrinterList.size() - 1; size >= 0; size--) {
                    android.print.PrinterId printerId = this.mTrackedPrinterList.get(size);
                    if (printerId.getServiceName().equals(this.mComponentName)) {
                        handleStopPrinterStateTracking(printerId);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream) {
        com.android.internal.util.dump.DumpUtils.writeComponentName(dualDumpOutputStream, "component_name", 1146756268033L, this.mComponentName);
        dualDumpOutputStream.write("is_destroyed", 1133871366146L, this.mDestroyed);
        dualDumpOutputStream.write("is_bound", 1133871366147L, isBound());
        dualDumpOutputStream.write("has_discovery_session", 1133871366148L, this.mHasPrinterDiscoverySession);
        dualDumpOutputStream.write("has_active_print_jobs", 1133871366149L, this.mHasActivePrintJobs);
        dualDumpOutputStream.write("is_discovering_printers", 1133871366150L, this.mDiscoveryPriorityList != null);
        synchronized (this.mLock) {
            try {
                if (this.mTrackedPrinterList != null) {
                    int size = this.mTrackedPrinterList.size();
                    for (int i = 0; i < size; i++) {
                        com.android.internal.print.DumpUtils.writePrinterId(dualDumpOutputStream, "tracked_printers", 2246267895815L, this.mTrackedPrinterList.get(i));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isBound() {
        return this.mPrintService != null;
    }

    private void ensureBound() {
        if (isBound() || this.mBinding) {
            return;
        }
        this.mBinding = true;
        if (!this.mContext.bindServiceAsUser(this.mIntent, this.mServiceConnection, 71307265, new android.os.UserHandle(this.mUserId))) {
            this.mBinding = false;
            if (!this.mServiceDied) {
                handleBinderDied();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensureUnbound() {
        if (!isBound() && !this.mBinding) {
            return;
        }
        this.mBinding = false;
        this.mPendingCommands.clear();
        this.mHasActivePrintJobs = false;
        this.mHasPrinterDiscoverySession = false;
        this.mDiscoveryPriorityList = null;
        synchronized (this.mLock) {
            this.mTrackedPrinterList = null;
        }
        if (isBound()) {
            try {
                this.mPrintService.setClient((android.printservice.IPrintServiceClient) null);
            } catch (android.os.RemoteException e) {
            }
            this.mPrintService.asBinder().unlinkToDeath(this, 0);
            this.mPrintService = null;
            this.mContext.unbindService(this.mServiceConnection);
        }
    }

    private class RemoteServiceConneciton implements android.content.ServiceConnection {
        private RemoteServiceConneciton() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            if (com.android.server.print.RemotePrintService.this.mDestroyed || !com.android.server.print.RemotePrintService.this.mBinding) {
                com.android.server.print.RemotePrintService.this.mContext.unbindService(com.android.server.print.RemotePrintService.this.mServiceConnection);
                return;
            }
            com.android.server.print.RemotePrintService.this.mBinding = false;
            com.android.server.print.RemotePrintService.this.mPrintService = android.printservice.IPrintService.Stub.asInterface(iBinder);
            try {
                iBinder.linkToDeath(com.android.server.print.RemotePrintService.this, 0);
                try {
                    com.android.server.print.RemotePrintService.this.mPrintService.setClient(com.android.server.print.RemotePrintService.this.mPrintServiceClient);
                    if (com.android.server.print.RemotePrintService.this.mServiceDied && com.android.server.print.RemotePrintService.this.mHasPrinterDiscoverySession) {
                        com.android.server.print.RemotePrintService.this.handleCreatePrinterDiscoverySession();
                    }
                    if (com.android.server.print.RemotePrintService.this.mServiceDied && com.android.server.print.RemotePrintService.this.mDiscoveryPriorityList != null) {
                        com.android.server.print.RemotePrintService.this.handleStartPrinterDiscovery(com.android.server.print.RemotePrintService.this.mDiscoveryPriorityList);
                    }
                    synchronized (com.android.server.print.RemotePrintService.this.mLock) {
                        try {
                            if (com.android.server.print.RemotePrintService.this.mServiceDied && com.android.server.print.RemotePrintService.this.mTrackedPrinterList != null) {
                                int size = com.android.server.print.RemotePrintService.this.mTrackedPrinterList.size();
                                for (int i = 0; i < size; i++) {
                                    com.android.server.print.RemotePrintService.this.handleStartPrinterStateTracking((android.print.PrinterId) com.android.server.print.RemotePrintService.this.mTrackedPrinterList.get(i));
                                }
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    while (!com.android.server.print.RemotePrintService.this.mPendingCommands.isEmpty()) {
                        ((java.lang.Runnable) com.android.server.print.RemotePrintService.this.mPendingCommands.remove(0)).run();
                    }
                    if (!com.android.server.print.RemotePrintService.this.mHasPrinterDiscoverySession && !com.android.server.print.RemotePrintService.this.mHasActivePrintJobs) {
                        com.android.server.print.RemotePrintService.this.ensureUnbound();
                    }
                    com.android.server.print.RemotePrintService.this.mServiceDied = false;
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.print.RemotePrintService.LOG_TAG, "Error setting client for: " + iBinder, e);
                    com.android.server.print.RemotePrintService.this.handleBinderDied();
                }
            } catch (android.os.RemoteException e2) {
                com.android.server.print.RemotePrintService.this.handleBinderDied();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            com.android.server.print.RemotePrintService.this.mBinding = true;
        }
    }

    private static final class RemotePrintServiceClient extends android.printservice.IPrintServiceClient.Stub {
        private final java.lang.ref.WeakReference<com.android.server.print.RemotePrintService> mWeakService;

        public RemotePrintServiceClient(com.android.server.print.RemotePrintService remotePrintService) {
            this.mWeakService = new java.lang.ref.WeakReference<>(remotePrintService);
        }

        public java.util.List<android.print.PrintJobInfo> getPrintJobInfos() {
            com.android.server.print.RemotePrintService remotePrintService = this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return remotePrintService.mSpooler.getPrintJobInfos(remotePrintService.mComponentName, -4, -2);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return null;
        }

        public android.print.PrintJobInfo getPrintJobInfo(android.print.PrintJobId printJobId) {
            com.android.server.print.RemotePrintService remotePrintService = this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return remotePrintService.mSpooler.getPrintJobInfo(printJobId, -2);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return null;
        }

        public boolean setPrintJobState(android.print.PrintJobId printJobId, int i, java.lang.String str) {
            com.android.server.print.RemotePrintService remotePrintService = this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return remotePrintService.mSpooler.setPrintJobState(printJobId, i, str);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return false;
        }

        public boolean setPrintJobTag(android.print.PrintJobId printJobId, java.lang.String str) {
            com.android.server.print.RemotePrintService remotePrintService = this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return remotePrintService.mSpooler.setPrintJobTag(printJobId, str);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return false;
        }

        public void writePrintJobData(android.os.ParcelFileDescriptor parcelFileDescriptor, android.print.PrintJobId printJobId) {
            com.android.server.print.RemotePrintService remotePrintService = this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    remotePrintService.mSpooler.writePrintJobData(parcelFileDescriptor, printJobId);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public void setProgress(@android.annotation.NonNull android.print.PrintJobId printJobId, float f) {
            com.android.server.print.RemotePrintService remotePrintService = this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    remotePrintService.mSpooler.setProgress(printJobId, f);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public void setStatus(@android.annotation.NonNull android.print.PrintJobId printJobId, @android.annotation.Nullable java.lang.CharSequence charSequence) {
            com.android.server.print.RemotePrintService remotePrintService = this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    remotePrintService.mSpooler.setStatus(printJobId, charSequence);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public void setStatusRes(@android.annotation.NonNull android.print.PrintJobId printJobId, int i, @android.annotation.NonNull java.lang.CharSequence charSequence) {
            com.android.server.print.RemotePrintService remotePrintService = this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    remotePrintService.mSpooler.setStatus(printJobId, i, charSequence);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public void onPrintersAdded(android.content.pm.ParceledListSlice parceledListSlice) {
            com.android.server.print.RemotePrintService remotePrintService = this.mWeakService.get();
            if (remotePrintService != null) {
                java.util.List<android.print.PrinterInfo> list = parceledListSlice.getList();
                throwIfPrinterIdsForPrinterInfoTampered(remotePrintService.mComponentName, list);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    remotePrintService.mCallbacks.onPrintersAdded(list);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public void onPrintersRemoved(android.content.pm.ParceledListSlice parceledListSlice) {
            com.android.server.print.RemotePrintService remotePrintService = this.mWeakService.get();
            if (remotePrintService != null) {
                java.util.List<android.print.PrinterId> list = parceledListSlice.getList();
                throwIfPrinterIdsTampered(remotePrintService.mComponentName, list);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    remotePrintService.mCallbacks.onPrintersRemoved(list);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        private void throwIfPrinterIdsForPrinterInfoTampered(android.content.ComponentName componentName, java.util.List<android.print.PrinterInfo> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                throwIfPrinterIdTampered(componentName, list.get(i).getId());
            }
        }

        private void throwIfPrinterIdsTampered(android.content.ComponentName componentName, java.util.List<android.print.PrinterId> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                throwIfPrinterIdTampered(componentName, list.get(i));
            }
        }

        private void throwIfPrinterIdTampered(android.content.ComponentName componentName, android.print.PrinterId printerId) {
            if (printerId == null || !printerId.getServiceName().equals(componentName)) {
                throw new java.lang.IllegalArgumentException("Invalid printer id: " + printerId);
            }
        }

        public void onCustomPrinterIconLoaded(android.print.PrinterId printerId, android.graphics.drawable.Icon icon) throws android.os.RemoteException {
            com.android.server.print.RemotePrintService remotePrintService = this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    remotePrintService.mCallbacks.onCustomPrinterIconLoaded(printerId, icon);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }
}
