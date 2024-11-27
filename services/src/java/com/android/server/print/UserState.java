package com.android.server.print;

/* loaded from: classes2.dex */
final class UserState implements com.android.server.print.RemotePrintSpooler.PrintSpoolerCallbacks, com.android.server.print.RemotePrintService.PrintServiceCallbacks, com.android.server.print.RemotePrintServiceRecommendationService.RemotePrintServiceRecommendationServiceCallbacks {
    private static final char COMPONENT_NAME_SEPARATOR = ':';
    private static final boolean DEBUG = false;
    private static final java.lang.String LOG_TAG = "UserState";
    private static final int SERVICE_RESTART_DELAY_MILLIS = 500;
    private final android.content.Context mContext;
    private boolean mDestroyed;
    private boolean mIsInstantServiceAllowed;
    private final java.lang.Object mLock;
    private java.util.List<com.android.server.print.UserState.PrintJobStateChangeListenerRecord> mPrintJobStateChangeListenerRecords;
    private java.util.List<android.printservice.recommendation.RecommendationInfo> mPrintServiceRecommendations;
    private java.util.List<com.android.server.print.UserState.ListenerRecord<android.printservice.recommendation.IRecommendationsChangeListener>> mPrintServiceRecommendationsChangeListenerRecords;
    private com.android.server.print.RemotePrintServiceRecommendationService mPrintServiceRecommendationsService;
    private java.util.List<com.android.server.print.UserState.ListenerRecord<android.print.IPrintServicesChangeListener>> mPrintServicesChangeListenerRecords;
    private com.android.server.print.UserState.PrinterDiscoverySessionMediator mPrinterDiscoverySession;
    private final com.android.server.print.RemotePrintSpooler mSpooler;
    private final int mUserId;
    private final android.text.TextUtils.SimpleStringSplitter mStringColonSplitter = new android.text.TextUtils.SimpleStringSplitter(COMPONENT_NAME_SEPARATOR);
    private final android.content.Intent mQueryIntent = new android.content.Intent("android.printservice.PrintService");
    private final android.util.ArrayMap<android.content.ComponentName, com.android.server.print.RemotePrintService> mActiveServices = new android.util.ArrayMap<>();
    private final java.util.List<android.printservice.PrintServiceInfo> mInstalledServices = new java.util.ArrayList();
    private final java.util.Set<android.content.ComponentName> mDisabledServices = new android.util.ArraySet();
    private final com.android.server.print.UserState.PrintJobForAppCache mPrintJobForAppCache = new com.android.server.print.UserState.PrintJobForAppCache();

    public UserState(android.content.Context context, int i, java.lang.Object obj, boolean z) {
        this.mContext = context;
        this.mUserId = i;
        this.mLock = obj;
        this.mSpooler = new com.android.server.print.RemotePrintSpooler(context, i, z, this);
        synchronized (this.mLock) {
            readInstalledPrintServicesLocked();
            upgradePersistentStateIfNeeded();
            readDisabledPrintServicesLocked();
        }
        prunePrintServices();
        onConfigurationChanged();
    }

    public void increasePriority() {
        this.mSpooler.increasePriority();
    }

    @Override // com.android.server.print.RemotePrintSpooler.PrintSpoolerCallbacks
    public void onPrintJobQueued(android.print.PrintJobInfo printJobInfo) {
        com.android.server.print.RemotePrintService remotePrintService;
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            remotePrintService = this.mActiveServices.get(printJobInfo.getPrinterId().getServiceName());
        }
        if (remotePrintService != null) {
            remotePrintService.onPrintJobQueued(printJobInfo);
        } else {
            this.mSpooler.setPrintJobState(printJobInfo.getId(), 6, this.mContext.getString(android.R.string.postalTypeWork));
        }
    }

    @Override // com.android.server.print.RemotePrintSpooler.PrintSpoolerCallbacks
    public void onAllPrintJobsForServiceHandled(android.content.ComponentName componentName) {
        com.android.server.print.RemotePrintService remotePrintService;
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            remotePrintService = this.mActiveServices.get(componentName);
        }
        if (remotePrintService != null) {
            remotePrintService.onAllPrintJobsHandled();
        }
    }

    public void removeObsoletePrintJobs() {
        this.mSpooler.removeObsoletePrintJobs();
    }

    public android.os.Bundle print(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.print.IPrintDocumentAdapter iPrintDocumentAdapter, @android.annotation.Nullable android.print.PrintAttributes printAttributes, @android.annotation.NonNull java.lang.String str2, int i) {
        android.print.PrintJobInfo printJobInfo = new android.print.PrintJobInfo();
        printJobInfo.setId(new android.print.PrintJobId());
        printJobInfo.setAppId(i);
        printJobInfo.setLabel(str);
        printJobInfo.setAttributes(printAttributes);
        printJobInfo.setState(1);
        printJobInfo.setCopies(1);
        printJobInfo.setCreationTime(java.lang.System.currentTimeMillis());
        if (!this.mPrintJobForAppCache.onPrintJobCreated(iPrintDocumentAdapter.asBinder(), i, printJobInfo)) {
            return null;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.Intent intent = new android.content.Intent("android.print.PRINT_DIALOG");
            intent.setData(android.net.Uri.fromParts("printjob", printJobInfo.getId().flattenToString(), null));
            intent.putExtra("android.print.intent.extra.EXTRA_PRINT_DOCUMENT_ADAPTER", iPrintDocumentAdapter.asBinder());
            intent.putExtra("android.print.intent.extra.EXTRA_PRINT_JOB", printJobInfo);
            intent.putExtra("android.intent.extra.PACKAGE_NAME", str2);
            android.content.IntentSender intentSender = android.app.PendingIntent.getActivityAsUser(this.mContext, 0, intent, 1409286144, android.app.ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(2).toBundle(), new android.os.UserHandle(this.mUserId)).getIntentSender();
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("android.print.intent.extra.EXTRA_PRINT_JOB", printJobInfo);
            bundle.putParcelable("android.print.intent.extra.EXTRA_PRINT_DIALOG_INTENT", intentSender);
            return bundle;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public java.util.List<android.print.PrintJobInfo> getPrintJobInfos(int i) {
        java.util.List<android.print.PrintJobInfo> printJobs = this.mPrintJobForAppCache.getPrintJobs(i);
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        int size = printJobs.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.print.PrintJobInfo printJobInfo = printJobs.get(i2);
            arrayMap.put(printJobInfo.getId(), printJobInfo);
            printJobInfo.setTag(null);
            printJobInfo.setAdvancedOptions(null);
        }
        java.util.List<android.print.PrintJobInfo> printJobInfos = this.mSpooler.getPrintJobInfos(null, -1, i);
        if (printJobInfos != null) {
            int size2 = printJobInfos.size();
            for (int i3 = 0; i3 < size2; i3++) {
                android.print.PrintJobInfo printJobInfo2 = printJobInfos.get(i3);
                arrayMap.put(printJobInfo2.getId(), printJobInfo2);
                printJobInfo2.setTag(null);
                printJobInfo2.setAdvancedOptions(null);
            }
        }
        return new java.util.ArrayList(arrayMap.values());
    }

    public android.print.PrintJobInfo getPrintJobInfo(@android.annotation.NonNull android.print.PrintJobId printJobId, int i) {
        android.print.PrintJobInfo printJob = this.mPrintJobForAppCache.getPrintJob(printJobId, i);
        if (printJob == null) {
            printJob = this.mSpooler.getPrintJobInfo(printJobId, i);
        }
        if (printJob != null) {
            printJob.setTag(null);
            printJob.setAdvancedOptions(null);
        }
        return printJob;
    }

    @android.annotation.Nullable
    public android.graphics.drawable.Icon getCustomPrinterIcon(@android.annotation.NonNull android.print.PrinterId printerId) {
        com.android.server.print.RemotePrintService remotePrintService;
        android.graphics.drawable.Icon customPrinterIcon = this.mSpooler.getCustomPrinterIcon(printerId);
        if (customPrinterIcon == null && (remotePrintService = this.mActiveServices.get(printerId.getServiceName())) != null) {
            remotePrintService.requestCustomPrinterIcon(printerId);
        }
        return customPrinterIcon;
    }

    public void cancelPrintJob(@android.annotation.NonNull android.print.PrintJobId printJobId, int i) {
        com.android.server.print.RemotePrintService remotePrintService;
        android.print.PrintJobInfo printJobInfo = this.mSpooler.getPrintJobInfo(printJobId, i);
        if (printJobInfo == null) {
            return;
        }
        this.mSpooler.setPrintJobCancelling(printJobId, true);
        if (printJobInfo.getState() != 6) {
            android.print.PrinterId printerId = printJobInfo.getPrinterId();
            if (printerId != null) {
                android.content.ComponentName serviceName = printerId.getServiceName();
                synchronized (this.mLock) {
                    remotePrintService = this.mActiveServices.get(serviceName);
                }
                if (remotePrintService == null) {
                    return;
                }
                remotePrintService.onRequestCancelPrintJob(printJobInfo);
                return;
            }
            return;
        }
        this.mSpooler.setPrintJobState(printJobId, 7, null);
    }

    public void restartPrintJob(@android.annotation.NonNull android.print.PrintJobId printJobId, int i) {
        android.print.PrintJobInfo printJobInfo = getPrintJobInfo(printJobId, i);
        if (printJobInfo == null || printJobInfo.getState() != 6) {
            return;
        }
        this.mSpooler.setPrintJobState(printJobId, 2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0046 A[Catch: all -> 0x004c, TryCatch #0 {all -> 0x004c, blocks: (B:4:0x0004, B:6:0x000e, B:8:0x003a, B:12:0x0051, B:14:0x0046, B:15:0x004e, B:17:0x003f, B:22:0x0054), top: B:3:0x0004 }] */
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public java.util.List<android.printservice.PrintServiceInfo> getPrintServices(int i) {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            try {
                int size = this.mInstalledServices.size();
                arrayList = null;
                for (int i2 = 0; i2 < size; i2++) {
                    android.printservice.PrintServiceInfo printServiceInfo = this.mInstalledServices.get(i2);
                    printServiceInfo.setIsEnabled(this.mActiveServices.containsKey(new android.content.ComponentName(printServiceInfo.getResolveInfo().serviceInfo.packageName, printServiceInfo.getResolveInfo().serviceInfo.name)));
                    if (printServiceInfo.isEnabled()) {
                        if ((i & 1) == 0) {
                        }
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(printServiceInfo);
                    } else {
                        if ((i & 2) == 0) {
                        }
                        if (arrayList == null) {
                        }
                        arrayList.add(printServiceInfo);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public void setPrintServiceEnabled(@android.annotation.NonNull android.content.ComponentName componentName, boolean z) {
        boolean z2;
        synchronized (this.mLock) {
            try {
                if (z) {
                    z2 = this.mDisabledServices.remove(componentName);
                } else {
                    int size = this.mInstalledServices.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            z2 = false;
                            break;
                        } else if (!this.mInstalledServices.get(i).getComponentName().equals(componentName)) {
                            i++;
                        } else {
                            this.mDisabledServices.add(componentName);
                            z2 = true;
                            break;
                        }
                    }
                }
                if (z2) {
                    writeDisabledPrintServicesLocked(this.mDisabledServices);
                    com.android.internal.logging.MetricsLogger.action(this.mContext, 511, z ? 0 : 1);
                    onConfigurationChangedLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isPrintServiceEnabled(@android.annotation.NonNull android.content.ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                return !this.mDisabledServices.contains(componentName);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public java.util.List<android.printservice.recommendation.RecommendationInfo> getPrintServiceRecommendations() {
        return this.mPrintServiceRecommendations;
    }

    public void createPrinterDiscoverySession(@android.annotation.NonNull android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver) {
        this.mSpooler.clearCustomPrinterIconCache();
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mPrinterDiscoverySession == null) {
                    this.mPrinterDiscoverySession = new com.android.server.print.UserState.PrinterDiscoverySessionMediator() { // from class: com.android.server.print.UserState.1
                        @Override // com.android.server.print.UserState.PrinterDiscoverySessionMediator
                        public void onDestroyed() {
                            com.android.server.print.UserState.this.mPrinterDiscoverySession = null;
                        }
                    };
                    this.mPrinterDiscoverySession.addObserverLocked(iPrinterDiscoveryObserver);
                } else {
                    this.mPrinterDiscoverySession.addObserverLocked(iPrinterDiscoveryObserver);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void destroyPrinterDiscoverySession(@android.annotation.NonNull android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver) {
        synchronized (this.mLock) {
            try {
                if (this.mPrinterDiscoverySession == null) {
                    return;
                }
                this.mPrinterDiscoverySession.removeObserverLocked(iPrinterDiscoveryObserver);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void startPrinterDiscovery(@android.annotation.NonNull android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, @android.annotation.Nullable java.util.List<android.print.PrinterId> list) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mPrinterDiscoverySession == null) {
                    return;
                }
                this.mPrinterDiscoverySession.startPrinterDiscoveryLocked(iPrinterDiscoveryObserver, list);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void stopPrinterDiscovery(@android.annotation.NonNull android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mPrinterDiscoverySession == null) {
                    return;
                }
                this.mPrinterDiscoverySession.stopPrinterDiscoveryLocked(iPrinterDiscoveryObserver);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void validatePrinters(@android.annotation.NonNull java.util.List<android.print.PrinterId> list) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mActiveServices.isEmpty()) {
                    return;
                }
                if (this.mPrinterDiscoverySession == null) {
                    return;
                }
                this.mPrinterDiscoverySession.validatePrintersLocked(list);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void startPrinterStateTracking(@android.annotation.NonNull android.print.PrinterId printerId) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mActiveServices.isEmpty()) {
                    return;
                }
                if (this.mPrinterDiscoverySession == null) {
                    return;
                }
                this.mPrinterDiscoverySession.startPrinterStateTrackingLocked(printerId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void stopPrinterStateTracking(android.print.PrinterId printerId) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mActiveServices.isEmpty()) {
                    return;
                }
                if (this.mPrinterDiscoverySession == null) {
                    return;
                }
                this.mPrinterDiscoverySession.stopPrinterStateTrackingLocked(printerId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void addPrintJobStateChangeListener(@android.annotation.NonNull android.print.IPrintJobStateChangeListener iPrintJobStateChangeListener, int i) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mPrintJobStateChangeListenerRecords == null) {
                    this.mPrintJobStateChangeListenerRecords = new java.util.ArrayList();
                }
                this.mPrintJobStateChangeListenerRecords.add(new com.android.server.print.UserState.PrintJobStateChangeListenerRecord(iPrintJobStateChangeListener, i) { // from class: com.android.server.print.UserState.2
                    @Override // com.android.server.print.UserState.PrintJobStateChangeListenerRecord
                    public void onBinderDied() {
                        synchronized (com.android.server.print.UserState.this.mLock) {
                            try {
                                if (com.android.server.print.UserState.this.mPrintJobStateChangeListenerRecords != null) {
                                    com.android.server.print.UserState.this.mPrintJobStateChangeListenerRecords.remove(this);
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removePrintJobStateChangeListener(@android.annotation.NonNull android.print.IPrintJobStateChangeListener iPrintJobStateChangeListener) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mPrintJobStateChangeListenerRecords == null) {
                    return;
                }
                int size = this.mPrintJobStateChangeListenerRecords.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    com.android.server.print.UserState.PrintJobStateChangeListenerRecord printJobStateChangeListenerRecord = this.mPrintJobStateChangeListenerRecords.get(i);
                    if (!printJobStateChangeListenerRecord.listener.asBinder().equals(iPrintJobStateChangeListener.asBinder())) {
                        i++;
                    } else {
                        printJobStateChangeListenerRecord.destroy();
                        this.mPrintJobStateChangeListenerRecords.remove(i);
                        break;
                    }
                }
                if (this.mPrintJobStateChangeListenerRecords.isEmpty()) {
                    this.mPrintJobStateChangeListenerRecords = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void addPrintServicesChangeListener(@android.annotation.NonNull android.print.IPrintServicesChangeListener iPrintServicesChangeListener) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mPrintServicesChangeListenerRecords == null) {
                    this.mPrintServicesChangeListenerRecords = new java.util.ArrayList();
                }
                this.mPrintServicesChangeListenerRecords.add(new com.android.server.print.UserState.ListenerRecord<android.print.IPrintServicesChangeListener>(iPrintServicesChangeListener) { // from class: com.android.server.print.UserState.3
                    @Override // com.android.server.print.UserState.ListenerRecord
                    public void onBinderDied() {
                        synchronized (com.android.server.print.UserState.this.mLock) {
                            try {
                                if (com.android.server.print.UserState.this.mPrintServicesChangeListenerRecords != null) {
                                    com.android.server.print.UserState.this.mPrintServicesChangeListenerRecords.remove(this);
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removePrintServicesChangeListener(@android.annotation.NonNull android.print.IPrintServicesChangeListener iPrintServicesChangeListener) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mPrintServicesChangeListenerRecords == null) {
                    return;
                }
                int size = this.mPrintServicesChangeListenerRecords.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    com.android.server.print.UserState.ListenerRecord<android.print.IPrintServicesChangeListener> listenerRecord = this.mPrintServicesChangeListenerRecords.get(i);
                    if (!listenerRecord.listener.asBinder().equals(iPrintServicesChangeListener.asBinder())) {
                        i++;
                    } else {
                        listenerRecord.destroy();
                        this.mPrintServicesChangeListenerRecords.remove(i);
                        break;
                    }
                }
                if (this.mPrintServicesChangeListenerRecords.isEmpty()) {
                    this.mPrintServicesChangeListenerRecords = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void addPrintServiceRecommendationsChangeListener(@android.annotation.NonNull android.printservice.recommendation.IRecommendationsChangeListener iRecommendationsChangeListener) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mPrintServiceRecommendationsChangeListenerRecords == null) {
                    this.mPrintServiceRecommendationsChangeListenerRecords = new java.util.ArrayList();
                    this.mPrintServiceRecommendationsService = new com.android.server.print.RemotePrintServiceRecommendationService(this.mContext, android.os.UserHandle.of(this.mUserId), this);
                }
                this.mPrintServiceRecommendationsChangeListenerRecords.add(new com.android.server.print.UserState.ListenerRecord<android.printservice.recommendation.IRecommendationsChangeListener>(iRecommendationsChangeListener) { // from class: com.android.server.print.UserState.4
                    @Override // com.android.server.print.UserState.ListenerRecord
                    public void onBinderDied() {
                        synchronized (com.android.server.print.UserState.this.mLock) {
                            try {
                                if (com.android.server.print.UserState.this.mPrintServiceRecommendationsChangeListenerRecords != null) {
                                    com.android.server.print.UserState.this.mPrintServiceRecommendationsChangeListenerRecords.remove(this);
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removePrintServiceRecommendationsChangeListener(@android.annotation.NonNull android.printservice.recommendation.IRecommendationsChangeListener iRecommendationsChangeListener) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mPrintServiceRecommendationsChangeListenerRecords == null) {
                    return;
                }
                int size = this.mPrintServiceRecommendationsChangeListenerRecords.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    com.android.server.print.UserState.ListenerRecord<android.printservice.recommendation.IRecommendationsChangeListener> listenerRecord = this.mPrintServiceRecommendationsChangeListenerRecords.get(i);
                    if (!listenerRecord.listener.asBinder().equals(iRecommendationsChangeListener.asBinder())) {
                        i++;
                    } else {
                        listenerRecord.destroy();
                        this.mPrintServiceRecommendationsChangeListenerRecords.remove(i);
                        break;
                    }
                }
                if (this.mPrintServiceRecommendationsChangeListenerRecords.isEmpty()) {
                    this.mPrintServiceRecommendationsChangeListenerRecords = null;
                    this.mPrintServiceRecommendations = null;
                    this.mPrintServiceRecommendationsService.close();
                    this.mPrintServiceRecommendationsService = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.print.RemotePrintSpooler.PrintSpoolerCallbacks
    public void onPrintJobStateChanged(android.print.PrintJobInfo printJobInfo) {
        this.mPrintJobForAppCache.onPrintJobStateChanged(printJobInfo);
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.print.UserState$$ExternalSyntheticLambda3
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.print.UserState) obj).handleDispatchPrintJobStateChanged((android.print.PrintJobId) obj2, (com.android.internal.util.function.pooled.PooledSupplier.OfInt) obj3);
            }
        }, this, printJobInfo.getId(), com.android.internal.util.function.pooled.PooledLambda.obtainSupplier(printJobInfo.getAppId()).recycleOnUse()));
    }

    public void onPrintServicesChanged() {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.print.UserState$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.print.UserState) obj).handleDispatchPrintServicesChanged();
            }
        }, this));
    }

    @Override // com.android.server.print.RemotePrintServiceRecommendationService.RemotePrintServiceRecommendationServiceCallbacks
    public void onPrintServiceRecommendationsUpdated(java.util.List<android.printservice.recommendation.RecommendationInfo> list) {
        android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.UserState$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.print.UserState) obj).handleDispatchPrintServiceRecommendationsUpdated((java.util.List) obj2);
            }
        }, this, list));
    }

    @Override // com.android.server.print.RemotePrintService.PrintServiceCallbacks
    public void onPrintersAdded(java.util.List<android.print.PrinterInfo> list) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mActiveServices.isEmpty()) {
                    return;
                }
                if (this.mPrinterDiscoverySession == null) {
                    return;
                }
                this.mPrinterDiscoverySession.onPrintersAddedLocked(list);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.print.RemotePrintService.PrintServiceCallbacks
    public void onPrintersRemoved(java.util.List<android.print.PrinterId> list) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mActiveServices.isEmpty()) {
                    return;
                }
                if (this.mPrinterDiscoverySession == null) {
                    return;
                }
                this.mPrinterDiscoverySession.onPrintersRemovedLocked(list);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.print.RemotePrintService.PrintServiceCallbacks
    public void onCustomPrinterIconLoaded(android.print.PrinterId printerId, android.graphics.drawable.Icon icon) {
        this.mSpooler.onCustomPrinterIconLoaded(printerId, icon);
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mPrinterDiscoverySession == null) {
                    return;
                }
                this.mPrinterDiscoverySession.onCustomPrinterIconLoadedLocked(printerId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.print.RemotePrintService.PrintServiceCallbacks
    public void onServiceDied(com.android.server.print.RemotePrintService remotePrintService) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mActiveServices.isEmpty()) {
                    return;
                }
                failActivePrintJobsForService(remotePrintService.getComponentName());
                remotePrintService.onAllPrintJobsHandled();
                this.mActiveServices.remove(remotePrintService.getComponentName());
                android.os.Handler.getMain().sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.print.UserState$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.print.UserState) obj).onConfigurationChanged();
                    }
                }, this), 500L);
                if (this.mPrinterDiscoverySession == null) {
                    return;
                }
                this.mPrinterDiscoverySession.onServiceDiedLocked(remotePrintService);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void updateIfNeededLocked() {
        throwIfDestroyedLocked();
        readConfigurationLocked();
        onConfigurationChangedLocked();
    }

    public void destroyLocked() {
        throwIfDestroyedLocked();
        this.mSpooler.destroy();
        java.util.Iterator<com.android.server.print.RemotePrintService> it = this.mActiveServices.values().iterator();
        while (it.hasNext()) {
            it.next().destroy();
        }
        this.mActiveServices.clear();
        this.mInstalledServices.clear();
        this.mDisabledServices.clear();
        if (this.mPrinterDiscoverySession != null) {
            this.mPrinterDiscoverySession.destroyLocked();
            this.mPrinterDiscoverySession = null;
        }
        this.mDestroyed = true;
    }

    public void dump(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream) {
        synchronized (this.mLock) {
            try {
                dualDumpOutputStream.write("user_id", 1120986464257L, this.mUserId);
                int size = this.mInstalledServices.size();
                for (int i = 0; i < size; i++) {
                    long start = dualDumpOutputStream.start("installed_services", 2246267895810L);
                    android.printservice.PrintServiceInfo printServiceInfo = this.mInstalledServices.get(i);
                    android.content.pm.ResolveInfo resolveInfo = printServiceInfo.getResolveInfo();
                    com.android.internal.util.dump.DumpUtils.writeComponentName(dualDumpOutputStream, "component_name", 1146756268033L, new android.content.ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name));
                    com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "settings_activity", 1138166333442L, printServiceInfo.getSettingsActivityName());
                    com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "add_printers_activity", 1138166333443L, printServiceInfo.getAddPrintersActivityName());
                    com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "advanced_options_activity", 1138166333444L, printServiceInfo.getAdvancedOptionsActivityName());
                    dualDumpOutputStream.end(start);
                }
                java.util.Iterator<android.content.ComponentName> it = this.mDisabledServices.iterator();
                while (it.hasNext()) {
                    com.android.internal.util.dump.DumpUtils.writeComponentName(dualDumpOutputStream, "disabled_services", 2246267895811L, it.next());
                }
                int size2 = this.mActiveServices.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    long start2 = dualDumpOutputStream.start("actives_services", 2246267895812L);
                    this.mActiveServices.valueAt(i2).dump(dualDumpOutputStream);
                    dualDumpOutputStream.end(start2);
                }
                this.mPrintJobForAppCache.dumpLocked(dualDumpOutputStream);
                if (this.mPrinterDiscoverySession != null) {
                    long start3 = dualDumpOutputStream.start("discovery_service", 2246267895814L);
                    this.mPrinterDiscoverySession.dumpLocked(dualDumpOutputStream);
                    dualDumpOutputStream.end(start3);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        long start4 = dualDumpOutputStream.start("print_spooler_state", 1146756268039L);
        this.mSpooler.dump(dualDumpOutputStream);
        dualDumpOutputStream.end(start4);
    }

    private void readConfigurationLocked() {
        readInstalledPrintServicesLocked();
        readDisabledPrintServicesLocked();
    }

    private void readInstalledPrintServicesLocked() {
        int i;
        java.util.HashSet hashSet = new java.util.HashSet();
        if (!this.mIsInstantServiceAllowed) {
            i = 268435588;
        } else {
            i = 276824196;
        }
        java.util.List queryIntentServicesAsUser = this.mContext.getPackageManager().queryIntentServicesAsUser(this.mQueryIntent, i, this.mUserId);
        int size = queryIntentServicesAsUser.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) queryIntentServicesAsUser.get(i2);
            if (!"android.permission.BIND_PRINT_SERVICE".equals(resolveInfo.serviceInfo.permission)) {
                android.util.Slog.w(LOG_TAG, "Skipping print service " + new android.content.ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name).flattenToShortString() + " since it does not require permission android.permission.BIND_PRINT_SERVICE");
            } else {
                hashSet.add(android.printservice.PrintServiceInfo.create(this.mContext, resolveInfo));
            }
        }
        this.mInstalledServices.clear();
        this.mInstalledServices.addAll(hashSet);
    }

    private void upgradePersistentStateIfNeeded() {
        if (android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "enabled_print_services", this.mUserId) != null) {
            java.util.Set<android.content.ComponentName> hashSet = new java.util.HashSet<>();
            readPrintServicesFromSettingLocked("enabled_print_services", hashSet);
            android.util.ArraySet arraySet = new android.util.ArraySet();
            int size = this.mInstalledServices.size();
            for (int i = 0; i < size; i++) {
                android.content.ComponentName componentName = this.mInstalledServices.get(i).getComponentName();
                if (!hashSet.contains(componentName)) {
                    arraySet.add(componentName);
                }
            }
            writeDisabledPrintServicesLocked(arraySet);
            android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "enabled_print_services", null, this.mUserId);
        }
    }

    private void readDisabledPrintServicesLocked() {
        java.util.HashSet hashSet = new java.util.HashSet();
        readPrintServicesFromSettingLocked("disabled_print_services", hashSet);
        if (!hashSet.equals(this.mDisabledServices)) {
            this.mDisabledServices.clear();
            this.mDisabledServices.addAll(hashSet);
        }
    }

    private void readPrintServicesFromSettingLocked(java.lang.String str, java.util.Set<android.content.ComponentName> set) {
        android.content.ComponentName unflattenFromString;
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), str, this.mUserId);
        if (!android.text.TextUtils.isEmpty(stringForUser)) {
            android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = this.mStringColonSplitter;
            simpleStringSplitter.setString(stringForUser);
            while (simpleStringSplitter.hasNext()) {
                java.lang.String next = simpleStringSplitter.next();
                if (!android.text.TextUtils.isEmpty(next) && (unflattenFromString = android.content.ComponentName.unflattenFromString(next)) != null) {
                    set.add(unflattenFromString);
                }
            }
        }
    }

    private void writeDisabledPrintServicesLocked(java.util.Set<android.content.ComponentName> set) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (android.content.ComponentName componentName : set) {
            if (sb.length() > 0) {
                sb.append(COMPONENT_NAME_SEPARATOR);
            }
            sb.append(componentName.flattenToShortString());
        }
        android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "disabled_print_services", sb.toString(), this.mUserId);
    }

    private java.util.ArrayList<android.content.ComponentName> getInstalledComponents() {
        java.util.ArrayList<android.content.ComponentName> arrayList = new java.util.ArrayList<>();
        int size = this.mInstalledServices.size();
        for (int i = 0; i < size; i++) {
            android.content.pm.ResolveInfo resolveInfo = this.mInstalledServices.get(i).getResolveInfo();
            arrayList.add(new android.content.ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name));
        }
        return arrayList;
    }

    public void prunePrintServices() {
        java.util.ArrayList<android.content.ComponentName> installedComponents;
        synchronized (this.mLock) {
            try {
                installedComponents = getInstalledComponents();
                if (this.mDisabledServices.retainAll(installedComponents)) {
                    writeDisabledPrintServicesLocked(this.mDisabledServices);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mSpooler.pruneApprovedPrintServices(installedComponents);
    }

    private void onConfigurationChangedLocked() {
        java.util.ArrayList<android.content.ComponentName> installedComponents = getInstalledComponents();
        int size = installedComponents.size();
        for (int i = 0; i < size; i++) {
            android.content.ComponentName componentName = installedComponents.get(i);
            if (!this.mDisabledServices.contains(componentName)) {
                if (!this.mActiveServices.containsKey(componentName)) {
                    addServiceLocked(new com.android.server.print.RemotePrintService(this.mContext, componentName, this.mUserId, this.mSpooler, this));
                }
            } else {
                com.android.server.print.RemotePrintService remove = this.mActiveServices.remove(componentName);
                if (remove != null) {
                    removeServiceLocked(remove);
                }
            }
        }
        java.util.Iterator<java.util.Map.Entry<android.content.ComponentName, com.android.server.print.RemotePrintService>> it = this.mActiveServices.entrySet().iterator();
        while (it.hasNext()) {
            java.util.Map.Entry<android.content.ComponentName, com.android.server.print.RemotePrintService> next = it.next();
            android.content.ComponentName key = next.getKey();
            com.android.server.print.RemotePrintService value = next.getValue();
            if (!installedComponents.contains(key)) {
                removeServiceLocked(value);
                it.remove();
            }
        }
        onPrintServicesChanged();
    }

    private void addServiceLocked(com.android.server.print.RemotePrintService remotePrintService) {
        this.mActiveServices.put(remotePrintService.getComponentName(), remotePrintService);
        if (this.mPrinterDiscoverySession != null) {
            this.mPrinterDiscoverySession.onServiceAddedLocked(remotePrintService);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeServiceLocked(com.android.server.print.RemotePrintService remotePrintService) {
        failActivePrintJobsForService(remotePrintService.getComponentName());
        if (this.mPrinterDiscoverySession != null) {
            this.mPrinterDiscoverySession.onServiceRemovedLocked(remotePrintService);
        } else {
            remotePrintService.destroy();
        }
    }

    private void failActivePrintJobsForService(android.content.ComponentName componentName) {
        if (android.os.Looper.getMainLooper().isCurrentThread()) {
            com.android.internal.os.BackgroundThread.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.UserState$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((com.android.server.print.UserState) obj).failScheduledPrintJobsForServiceInternal((android.content.ComponentName) obj2);
                }
            }, this, componentName));
        } else {
            failScheduledPrintJobsForServiceInternal(componentName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void failScheduledPrintJobsForServiceInternal(android.content.ComponentName componentName) {
        java.util.List<android.print.PrintJobInfo> printJobInfos = this.mSpooler.getPrintJobInfos(componentName, -4, -2);
        if (printJobInfos == null) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int size = printJobInfos.size();
            for (int i = 0; i < size; i++) {
                this.mSpooler.setPrintJobState(printJobInfos.get(i).getId(), 6, this.mContext.getString(android.R.string.postalTypeWork));
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void throwIfDestroyedLocked() {
        if (this.mDestroyed) {
            throw new java.lang.IllegalStateException("Cannot interact with a destroyed instance.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDispatchPrintJobStateChanged(android.print.PrintJobId printJobId, java.util.function.IntSupplier intSupplier) {
        int asInt = intSupplier.getAsInt();
        synchronized (this.mLock) {
            try {
                if (this.mPrintJobStateChangeListenerRecords == null) {
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mPrintJobStateChangeListenerRecords);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.print.UserState.PrintJobStateChangeListenerRecord printJobStateChangeListenerRecord = (com.android.server.print.UserState.PrintJobStateChangeListenerRecord) arrayList.get(i);
                    if (printJobStateChangeListenerRecord.appId == -2 || printJobStateChangeListenerRecord.appId == asInt) {
                        try {
                            printJobStateChangeListenerRecord.listener.onPrintJobStateChanged(printJobId);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(LOG_TAG, "Error notifying for print job state change", e);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDispatchPrintServicesChanged() {
        synchronized (this.mLock) {
            try {
                if (this.mPrintServicesChangeListenerRecords == null) {
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mPrintServicesChangeListenerRecords);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    try {
                        ((com.android.server.print.UserState.ListenerRecord) arrayList.get(i)).listener.onPrintServicesChanged();
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(LOG_TAG, "Error notifying for print services change", e);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDispatchPrintServiceRecommendationsUpdated(@android.annotation.Nullable java.util.List<android.printservice.recommendation.RecommendationInfo> list) {
        synchronized (this.mLock) {
            try {
                if (this.mPrintServiceRecommendationsChangeListenerRecords == null) {
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mPrintServiceRecommendationsChangeListenerRecords);
                this.mPrintServiceRecommendations = list;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    try {
                        ((com.android.server.print.UserState.ListenerRecord) arrayList.get(i)).listener.onRecommendationsChanged();
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(LOG_TAG, "Error notifying for print service recommendations change", e);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConfigurationChanged() {
        synchronized (this.mLock) {
            onConfigurationChangedLocked();
        }
    }

    public boolean getBindInstantServiceAllowed() {
        return this.mIsInstantServiceAllowed;
    }

    public void setBindInstantServiceAllowed(boolean z) {
        synchronized (this.mLock) {
            this.mIsInstantServiceAllowed = z;
            updateIfNeededLocked();
        }
    }

    private abstract class PrintJobStateChangeListenerRecord implements android.os.IBinder.DeathRecipient {
        final int appId;

        @android.annotation.NonNull
        final android.print.IPrintJobStateChangeListener listener;

        public abstract void onBinderDied();

        public PrintJobStateChangeListenerRecord(@android.annotation.NonNull android.print.IPrintJobStateChangeListener iPrintJobStateChangeListener, int i) throws android.os.RemoteException {
            this.listener = iPrintJobStateChangeListener;
            this.appId = i;
            iPrintJobStateChangeListener.asBinder().linkToDeath(this, 0);
        }

        public void destroy() {
            this.listener.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.listener.asBinder().unlinkToDeath(this, 0);
            onBinderDied();
        }
    }

    private abstract class ListenerRecord<T extends android.os.IInterface> implements android.os.IBinder.DeathRecipient {

        @android.annotation.NonNull
        final T listener;

        public abstract void onBinderDied();

        public ListenerRecord(@android.annotation.NonNull T t) throws android.os.RemoteException {
            this.listener = t;
            t.asBinder().linkToDeath(this, 0);
        }

        public void destroy() {
            this.listener.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.listener.asBinder().unlinkToDeath(this, 0);
            onBinderDied();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class PrinterDiscoverySessionMediator {
        private boolean mIsDestroyed;
        private final android.util.ArrayMap<android.print.PrinterId, android.print.PrinterInfo> mPrinters = new android.util.ArrayMap<>();
        private final android.os.RemoteCallbackList<android.print.IPrinterDiscoveryObserver> mDiscoveryObservers = new android.os.RemoteCallbackList<android.print.IPrinterDiscoveryObserver>() { // from class: com.android.server.print.UserState.PrinterDiscoverySessionMediator.1
            @Override // android.os.RemoteCallbackList
            public void onCallbackDied(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver) {
                synchronized (com.android.server.print.UserState.this.mLock) {
                    com.android.server.print.UserState.PrinterDiscoverySessionMediator.this.stopPrinterDiscoveryLocked(iPrinterDiscoveryObserver);
                    com.android.server.print.UserState.PrinterDiscoverySessionMediator.this.removeObserverLocked(iPrinterDiscoveryObserver);
                }
            }
        };
        private final java.util.List<android.os.IBinder> mStartedPrinterDiscoveryTokens = new java.util.ArrayList();
        private final java.util.List<android.print.PrinterId> mStateTrackedPrinters = new java.util.ArrayList();

        PrinterDiscoverySessionMediator() {
            android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((com.android.server.print.UserState.PrinterDiscoverySessionMediator) obj).handleDispatchCreatePrinterDiscoverySession((java.util.ArrayList) obj2);
                }
            }, this, new java.util.ArrayList(com.android.server.print.UserState.this.mActiveServices.values())));
        }

        public void addObserverLocked(@android.annotation.NonNull android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver) {
            this.mDiscoveryObservers.register(iPrinterDiscoveryObserver);
            if (!this.mPrinters.isEmpty()) {
                android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda8
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((com.android.server.print.UserState.PrinterDiscoverySessionMediator) obj).handlePrintersAdded((android.print.IPrinterDiscoveryObserver) obj2, (java.util.ArrayList) obj3);
                    }
                }, this, iPrinterDiscoveryObserver, new java.util.ArrayList(this.mPrinters.values())));
            }
        }

        public void removeObserverLocked(@android.annotation.NonNull android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver) {
            this.mDiscoveryObservers.unregister(iPrinterDiscoveryObserver);
            if (this.mDiscoveryObservers.getRegisteredCallbackCount() == 0) {
                destroyLocked();
            }
        }

        public final void startPrinterDiscoveryLocked(@android.annotation.NonNull android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, @android.annotation.Nullable java.util.List<android.print.PrinterId> list) {
            if (this.mIsDestroyed) {
                android.util.Log.w(com.android.server.print.UserState.LOG_TAG, "Not starting dicovery - session destroyed");
                return;
            }
            boolean z = !this.mStartedPrinterDiscoveryTokens.isEmpty();
            this.mStartedPrinterDiscoveryTokens.add(iPrinterDiscoveryObserver.asBinder());
            if (!z || list == null || list.isEmpty()) {
                if (this.mStartedPrinterDiscoveryTokens.size() > 1) {
                    return;
                }
                android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda6
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((com.android.server.print.UserState.PrinterDiscoverySessionMediator) obj).handleDispatchStartPrinterDiscovery((java.util.ArrayList) obj2, (java.util.List) obj3);
                    }
                }, this, new java.util.ArrayList(com.android.server.print.UserState.this.mActiveServices.values()), list));
                return;
            }
            com.android.server.print.UserState.this.validatePrinters(list);
        }

        public final void stopPrinterDiscoveryLocked(@android.annotation.NonNull android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver) {
            if (this.mIsDestroyed) {
                android.util.Log.w(com.android.server.print.UserState.LOG_TAG, "Not stopping dicovery - session destroyed");
            } else {
                if (!this.mStartedPrinterDiscoveryTokens.remove(iPrinterDiscoveryObserver.asBinder()) || !this.mStartedPrinterDiscoveryTokens.isEmpty()) {
                    return;
                }
                android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda10
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((com.android.server.print.UserState.PrinterDiscoverySessionMediator) obj).handleDispatchStopPrinterDiscovery((java.util.ArrayList) obj2);
                    }
                }, this, new java.util.ArrayList(com.android.server.print.UserState.this.mActiveServices.values())));
            }
        }

        public void validatePrintersLocked(@android.annotation.NonNull java.util.List<android.print.PrinterId> list) {
            if (this.mIsDestroyed) {
                android.util.Log.w(com.android.server.print.UserState.LOG_TAG, "Not validating pritners - session destroyed");
                return;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(list);
            while (!arrayList.isEmpty()) {
                java.util.Iterator it = arrayList.iterator();
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                android.content.ComponentName componentName = null;
                while (it.hasNext()) {
                    android.print.PrinterId printerId = (android.print.PrinterId) it.next();
                    if (printerId != null) {
                        if (arrayList2.isEmpty()) {
                            arrayList2.add(printerId);
                            componentName = printerId.getServiceName();
                            it.remove();
                        } else if (printerId.getServiceName().equals(componentName)) {
                            arrayList2.add(printerId);
                            it.remove();
                        }
                    }
                }
                com.android.server.print.RemotePrintService remotePrintService = (com.android.server.print.RemotePrintService) com.android.server.print.UserState.this.mActiveServices.get(componentName);
                if (remotePrintService != null) {
                    android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda12
                        public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                            ((com.android.server.print.UserState.PrinterDiscoverySessionMediator) obj).handleValidatePrinters((com.android.server.print.RemotePrintService) obj2, (java.util.List) obj3);
                        }
                    }, this, remotePrintService, arrayList2));
                }
            }
        }

        public final void startPrinterStateTrackingLocked(@android.annotation.NonNull android.print.PrinterId printerId) {
            com.android.server.print.RemotePrintService remotePrintService;
            if (this.mIsDestroyed) {
                android.util.Log.w(com.android.server.print.UserState.LOG_TAG, "Not starting printer state tracking - session destroyed");
                return;
            }
            if (this.mStartedPrinterDiscoveryTokens.isEmpty()) {
                return;
            }
            boolean contains = this.mStateTrackedPrinters.contains(printerId);
            this.mStateTrackedPrinters.add(printerId);
            if (contains || (remotePrintService = (com.android.server.print.RemotePrintService) com.android.server.print.UserState.this.mActiveServices.get(printerId.getServiceName())) == null) {
                return;
            }
            android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda11
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((com.android.server.print.UserState.PrinterDiscoverySessionMediator) obj).handleStartPrinterStateTracking((com.android.server.print.RemotePrintService) obj2, (android.print.PrinterId) obj3);
                }
            }, this, remotePrintService, printerId));
        }

        public final void stopPrinterStateTrackingLocked(android.print.PrinterId printerId) {
            com.android.server.print.RemotePrintService remotePrintService;
            if (this.mIsDestroyed) {
                android.util.Log.w(com.android.server.print.UserState.LOG_TAG, "Not stopping printer state tracking - session destroyed");
            } else {
                if (this.mStartedPrinterDiscoveryTokens.isEmpty() || !this.mStateTrackedPrinters.remove(printerId) || (remotePrintService = (com.android.server.print.RemotePrintService) com.android.server.print.UserState.this.mActiveServices.get(printerId.getServiceName())) == null) {
                    return;
                }
                android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda13
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((com.android.server.print.UserState.PrinterDiscoverySessionMediator) obj).handleStopPrinterStateTracking((com.android.server.print.RemotePrintService) obj2, (android.print.PrinterId) obj3);
                    }
                }, this, remotePrintService, printerId));
            }
        }

        public void onDestroyed() {
        }

        public void destroyLocked() {
            if (this.mIsDestroyed) {
                android.util.Log.w(com.android.server.print.UserState.LOG_TAG, "Not destroying - session destroyed");
                return;
            }
            this.mIsDestroyed = true;
            int size = this.mStateTrackedPrinters.size();
            for (int i = 0; i < size; i++) {
                com.android.server.print.UserState.this.stopPrinterStateTracking(this.mStateTrackedPrinters.get(i));
            }
            int size2 = this.mStartedPrinterDiscoveryTokens.size();
            for (int i2 = 0; i2 < size2; i2++) {
                stopPrinterDiscoveryLocked(android.print.IPrinterDiscoveryObserver.Stub.asInterface(this.mStartedPrinterDiscoveryTokens.get(i2)));
            }
            android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda7
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((com.android.server.print.UserState.PrinterDiscoverySessionMediator) obj).handleDispatchDestroyPrinterDiscoverySession((java.util.ArrayList) obj2);
                }
            }, this, new java.util.ArrayList(com.android.server.print.UserState.this.mActiveServices.values())));
        }

        public void onPrintersAddedLocked(java.util.List<android.print.PrinterInfo> list) {
            if (this.mIsDestroyed) {
                android.util.Log.w(com.android.server.print.UserState.LOG_TAG, "Not adding printers - session destroyed");
                return;
            }
            int size = list.size();
            java.util.ArrayList arrayList = null;
            for (int i = 0; i < size; i++) {
                android.print.PrinterInfo printerInfo = list.get(i);
                android.print.PrinterInfo put = this.mPrinters.put(printerInfo.getId(), printerInfo);
                if (put == null || !put.equals(printerInfo)) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(printerInfo);
                }
            }
            if (arrayList != null) {
                android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda9
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((com.android.server.print.UserState.PrinterDiscoverySessionMediator) obj).handleDispatchPrintersAdded((java.util.List) obj2);
                    }
                }, this, arrayList));
            }
        }

        public void onPrintersRemovedLocked(java.util.List<android.print.PrinterId> list) {
            if (this.mIsDestroyed) {
                android.util.Log.w(com.android.server.print.UserState.LOG_TAG, "Not removing printers - session destroyed");
                return;
            }
            int size = list.size();
            java.util.ArrayList arrayList = null;
            for (int i = 0; i < size; i++) {
                android.print.PrinterId printerId = list.get(i);
                if (this.mPrinters.remove(printerId) != null) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(printerId);
                }
            }
            if (arrayList != null) {
                android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda5(), this, arrayList));
            }
        }

        public void onServiceRemovedLocked(com.android.server.print.RemotePrintService remotePrintService) {
            if (this.mIsDestroyed) {
                android.util.Log.w(com.android.server.print.UserState.LOG_TAG, "Not updating removed service - session destroyed");
            } else {
                removePrintersForServiceLocked(remotePrintService.getComponentName());
                remotePrintService.destroy();
            }
        }

        public void onCustomPrinterIconLoadedLocked(android.print.PrinterId printerId) {
            if (this.mIsDestroyed) {
                android.util.Log.w(com.android.server.print.UserState.LOG_TAG, "Not updating printer - session destroyed");
                return;
            }
            android.print.PrinterInfo printerInfo = this.mPrinters.get(printerId);
            if (printerInfo != null) {
                android.print.PrinterInfo build = new android.print.PrinterInfo.Builder(printerInfo).incCustomPrinterIconGen().build();
                this.mPrinters.put(printerId, build);
                java.util.ArrayList arrayList = new java.util.ArrayList(1);
                arrayList.add(build);
                android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((com.android.server.print.UserState.PrinterDiscoverySessionMediator) obj).handleDispatchPrintersAdded((java.util.ArrayList) obj2);
                    }
                }, this, arrayList));
            }
        }

        public void onServiceDiedLocked(com.android.server.print.RemotePrintService remotePrintService) {
            com.android.server.print.UserState.this.removeServiceLocked(remotePrintService);
        }

        public void onServiceAddedLocked(com.android.server.print.RemotePrintService remotePrintService) {
            if (this.mIsDestroyed) {
                android.util.Log.w(com.android.server.print.UserState.LOG_TAG, "Not updating added service - session destroyed");
                return;
            }
            android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.print.RemotePrintService) obj).createPrinterDiscoverySession();
                }
            }, remotePrintService));
            if (!this.mStartedPrinterDiscoveryTokens.isEmpty()) {
                android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((com.android.server.print.RemotePrintService) obj).startPrinterDiscovery((java.util.List) obj2);
                    }
                }, remotePrintService, (java.lang.Object) null));
            }
            int size = this.mStateTrackedPrinters.size();
            for (int i = 0; i < size; i++) {
                android.print.PrinterId printerId = this.mStateTrackedPrinters.get(i);
                if (printerId.getServiceName().equals(remotePrintService.getComponentName())) {
                    android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda4
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            ((com.android.server.print.RemotePrintService) obj).startPrinterStateTracking((android.print.PrinterId) obj2);
                        }
                    }, remotePrintService, printerId));
                }
            }
        }

        public void dumpLocked(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream) {
            dualDumpOutputStream.write("is_destroyed", 1133871366145L, com.android.server.print.UserState.this.mDestroyed);
            dualDumpOutputStream.write("is_printer_discovery_in_progress", 1133871366146L, !this.mStartedPrinterDiscoveryTokens.isEmpty());
            int beginBroadcast = this.mDiscoveryObservers.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                dualDumpOutputStream.write("printer_discovery_observers", 2237677961219L, this.mDiscoveryObservers.getBroadcastItem(i).toString());
            }
            this.mDiscoveryObservers.finishBroadcast();
            int size = this.mStartedPrinterDiscoveryTokens.size();
            for (int i2 = 0; i2 < size; i2++) {
                dualDumpOutputStream.write("discovery_requests", 2237677961220L, this.mStartedPrinterDiscoveryTokens.get(i2).toString());
            }
            int size2 = this.mStateTrackedPrinters.size();
            for (int i3 = 0; i3 < size2; i3++) {
                com.android.internal.print.DumpUtils.writePrinterId(dualDumpOutputStream, "tracked_printer_requests", 2246267895813L, this.mStateTrackedPrinters.get(i3));
            }
            int size3 = this.mPrinters.size();
            for (int i4 = 0; i4 < size3; i4++) {
                com.android.internal.print.DumpUtils.writePrinterInfo(com.android.server.print.UserState.this.mContext, dualDumpOutputStream, "printer", 2246267895814L, this.mPrinters.valueAt(i4));
            }
        }

        private void removePrintersForServiceLocked(android.content.ComponentName componentName) {
            if (this.mPrinters.isEmpty()) {
                return;
            }
            int size = this.mPrinters.size();
            java.util.ArrayList arrayList = null;
            for (int i = 0; i < size; i++) {
                android.print.PrinterId keyAt = this.mPrinters.keyAt(i);
                if (keyAt.getServiceName().equals(componentName)) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(keyAt);
                }
            }
            if (arrayList != null) {
                int size2 = arrayList.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    this.mPrinters.remove(arrayList.get(i2));
                }
                android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.print.UserState$PrinterDiscoverySessionMediator$$ExternalSyntheticLambda5(), this, arrayList));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleDispatchPrintersAdded(java.util.List<android.print.PrinterInfo> list) {
            int beginBroadcast = this.mDiscoveryObservers.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                handlePrintersAdded(this.mDiscoveryObservers.getBroadcastItem(i), list);
            }
            this.mDiscoveryObservers.finishBroadcast();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleDispatchPrintersRemoved(java.util.List<android.print.PrinterId> list) {
            int beginBroadcast = this.mDiscoveryObservers.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                handlePrintersRemoved(this.mDiscoveryObservers.getBroadcastItem(i), list);
            }
            this.mDiscoveryObservers.finishBroadcast();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleDispatchCreatePrinterDiscoverySession(java.util.List<com.android.server.print.RemotePrintService> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                list.get(i).createPrinterDiscoverySession();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleDispatchDestroyPrinterDiscoverySession(java.util.List<com.android.server.print.RemotePrintService> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                list.get(i).destroyPrinterDiscoverySession();
            }
            onDestroyed();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleDispatchStartPrinterDiscovery(java.util.List<com.android.server.print.RemotePrintService> list, java.util.List<android.print.PrinterId> list2) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                list.get(i).startPrinterDiscovery(list2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleDispatchStopPrinterDiscovery(java.util.List<com.android.server.print.RemotePrintService> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                list.get(i).stopPrinterDiscovery();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleValidatePrinters(com.android.server.print.RemotePrintService remotePrintService, java.util.List<android.print.PrinterId> list) {
            remotePrintService.validatePrinters(list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleStartPrinterStateTracking(@android.annotation.NonNull com.android.server.print.RemotePrintService remotePrintService, @android.annotation.NonNull android.print.PrinterId printerId) {
            remotePrintService.startPrinterStateTracking(printerId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleStopPrinterStateTracking(com.android.server.print.RemotePrintService remotePrintService, android.print.PrinterId printerId) {
            remotePrintService.stopPrinterStateTracking(printerId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handlePrintersAdded(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, java.util.List<android.print.PrinterInfo> list) {
            try {
                iPrinterDiscoveryObserver.onPrintersAdded(new android.content.pm.ParceledListSlice(list));
            } catch (android.os.RemoteException e) {
                android.util.Log.e(com.android.server.print.UserState.LOG_TAG, "Error sending added printers", e);
            }
        }

        private void handlePrintersRemoved(android.print.IPrinterDiscoveryObserver iPrinterDiscoveryObserver, java.util.List<android.print.PrinterId> list) {
            try {
                iPrinterDiscoveryObserver.onPrintersRemoved(new android.content.pm.ParceledListSlice(list));
            } catch (android.os.RemoteException e) {
                android.util.Log.e(com.android.server.print.UserState.LOG_TAG, "Error sending removed printers", e);
            }
        }
    }

    private final class PrintJobForAppCache {
        private final android.util.SparseArray<java.util.List<android.print.PrintJobInfo>> mPrintJobsForRunningApp;

        private PrintJobForAppCache() {
            this.mPrintJobsForRunningApp = new android.util.SparseArray<>();
        }

        public boolean onPrintJobCreated(final android.os.IBinder iBinder, final int i, android.print.PrintJobInfo printJobInfo) {
            try {
                iBinder.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.print.UserState.PrintJobForAppCache.1
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        iBinder.unlinkToDeath(this, 0);
                        synchronized (com.android.server.print.UserState.this.mLock) {
                            com.android.server.print.UserState.PrintJobForAppCache.this.mPrintJobsForRunningApp.remove(i);
                        }
                    }
                }, 0);
                synchronized (com.android.server.print.UserState.this.mLock) {
                    try {
                        java.util.List<android.print.PrintJobInfo> list = this.mPrintJobsForRunningApp.get(i);
                        if (list == null) {
                            list = new java.util.ArrayList<>();
                            this.mPrintJobsForRunningApp.put(i, list);
                        }
                        list.add(printJobInfo);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return true;
            } catch (android.os.RemoteException e) {
                return false;
            }
        }

        public void onPrintJobStateChanged(android.print.PrintJobInfo printJobInfo) {
            synchronized (com.android.server.print.UserState.this.mLock) {
                try {
                    java.util.List<android.print.PrintJobInfo> list = this.mPrintJobsForRunningApp.get(printJobInfo.getAppId());
                    if (list == null) {
                        return;
                    }
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        if (list.get(i).getId().equals(printJobInfo.getId())) {
                            list.set(i, printJobInfo);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.print.PrintJobInfo getPrintJob(android.print.PrintJobId printJobId, int i) {
            synchronized (com.android.server.print.UserState.this.mLock) {
                try {
                    java.util.List<android.print.PrintJobInfo> list = this.mPrintJobsForRunningApp.get(i);
                    if (list == null) {
                        return null;
                    }
                    int size = list.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        android.print.PrintJobInfo printJobInfo = list.get(i2);
                        if (printJobInfo.getId().equals(printJobId)) {
                            return printJobInfo;
                        }
                    }
                    return null;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public java.util.List<android.print.PrintJobInfo> getPrintJobs(int i) {
            synchronized (com.android.server.print.UserState.this.mLock) {
                java.util.ArrayList arrayList = null;
                try {
                    if (i == -2) {
                        int size = this.mPrintJobsForRunningApp.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            java.util.List<android.print.PrintJobInfo> valueAt = this.mPrintJobsForRunningApp.valueAt(i2);
                            if (arrayList == null) {
                                arrayList = new java.util.ArrayList();
                            }
                            arrayList.addAll(valueAt);
                        }
                    } else {
                        java.util.List<android.print.PrintJobInfo> list = this.mPrintJobsForRunningApp.get(i);
                        if (list != null) {
                            arrayList = new java.util.ArrayList();
                            arrayList.addAll(list);
                        }
                    }
                    if (arrayList != null) {
                        return arrayList;
                    }
                    return java.util.Collections.emptyList();
                } finally {
                }
            }
        }

        public void dumpLocked(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream) {
            int size = this.mPrintJobsForRunningApp.size();
            int i = 0;
            while (i < size) {
                int keyAt = this.mPrintJobsForRunningApp.keyAt(i);
                java.util.List<android.print.PrintJobInfo> valueAt = this.mPrintJobsForRunningApp.valueAt(i);
                int size2 = valueAt.size();
                int i2 = 0;
                while (i2 < size2) {
                    long start = dualDumpOutputStream.start("cached_print_jobs", 2246267895813L);
                    dualDumpOutputStream.write("app_id", 1120986464257L, keyAt);
                    com.android.internal.print.DumpUtils.writePrintJobInfo(com.android.server.print.UserState.this.mContext, dualDumpOutputStream, "print_job", 1146756268034L, valueAt.get(i2));
                    dualDumpOutputStream.end(start);
                    i2++;
                    i = i;
                }
                i++;
            }
        }
    }
}
