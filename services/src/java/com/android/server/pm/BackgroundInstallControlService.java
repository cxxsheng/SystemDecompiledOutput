package com.android.server.pm;

/* loaded from: classes2.dex */
public class BackgroundInstallControlService extends com.android.server.SystemService {
    private static final java.lang.String DISK_DIR_NAME = "bic";
    private static final java.lang.String DISK_FILE_NAME = "states";
    private static final java.lang.String ENFORCE_PERMISSION_ERROR_MSG = "User is not permitted to call service: ";
    private static final int MAX_FOREGROUND_TIME_FRAMES_SIZE = 10;
    private static final int MSG_PACKAGE_ADDED = 1;
    private static final int MSG_PACKAGE_REMOVED = 2;
    private static final int MSG_USAGE_EVENT_RECEIVED = 0;
    private static final java.lang.String TAG = "BackgroundInstallControlService";
    private android.util.SparseSetArray<java.lang.String> mBackgroundInstalledPackages;
    private final com.android.server.pm.BackgroundInstallControlService.BinderService mBinderService;
    private final com.android.server.pm.BackgroundInstallControlCallbackHelper mCallbackHelper;
    private final android.content.Context mContext;
    private final java.io.File mDiskFile;
    private final android.os.Handler mHandler;
    private final android.util.SparseArrayMap<java.lang.String, java.util.TreeSet<com.android.server.pm.BackgroundInstallControlService.ForegroundTimeFrame>> mInstallerForegroundTimeFrames;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;

    @com.android.internal.annotations.VisibleForTesting
    protected final android.content.pm.PackageManagerInternal.PackageListObserver mPackageObserver;
    private final com.android.server.pm.permission.PermissionManagerServiceInternal mPermissionManager;

    interface Injector {
        com.android.server.pm.BackgroundInstallControlCallbackHelper getBackgroundInstallControlCallbackHelper();

        android.content.Context getContext();

        java.io.File getDiskFile();

        android.os.Looper getLooper();

        android.content.pm.PackageManager getPackageManager();

        android.content.pm.PackageManagerInternal getPackageManagerInternal();

        com.android.server.pm.permission.PermissionManagerServiceInternal getPermissionManager();

        android.app.usage.UsageStatsManagerInternal getUsageStatsManagerInternal();
    }

    public BackgroundInstallControlService(@android.annotation.NonNull android.content.Context context) {
        this(new com.android.server.pm.BackgroundInstallControlService.InjectorImpl(context));
    }

    @com.android.internal.annotations.VisibleForTesting
    BackgroundInstallControlService(@android.annotation.NonNull com.android.server.pm.BackgroundInstallControlService.Injector injector) {
        super(injector.getContext());
        this.mBackgroundInstalledPackages = null;
        this.mInstallerForegroundTimeFrames = new android.util.SparseArrayMap<>();
        this.mPackageObserver = new android.content.pm.PackageManagerInternal.PackageListObserver() { // from class: com.android.server.pm.BackgroundInstallControlService.1
            @Override // android.content.pm.PackageManagerInternal.PackageListObserver
            public void onPackageAdded(java.lang.String str, int i) {
                com.android.server.pm.BackgroundInstallControlService.this.mHandler.obtainMessage(1, android.os.UserHandle.getUserId(i), 0, str).sendToTarget();
            }

            @Override // android.content.pm.PackageManagerInternal.PackageListObserver
            public void onPackageRemoved(java.lang.String str, int i) {
                com.android.server.pm.BackgroundInstallControlService.this.mHandler.obtainMessage(2, android.os.UserHandle.getUserId(i), 0, str).sendToTarget();
            }
        };
        this.mPackageManager = injector.getPackageManager();
        this.mPackageManagerInternal = injector.getPackageManagerInternal();
        this.mPermissionManager = injector.getPermissionManager();
        this.mHandler = new com.android.server.pm.BackgroundInstallControlService.EventHandler(injector.getLooper(), this);
        this.mDiskFile = injector.getDiskFile();
        this.mContext = injector.getContext();
        this.mCallbackHelper = injector.getBackgroundInstallControlCallbackHelper();
        injector.getUsageStatsManagerInternal().registerListener(new android.app.usage.UsageStatsManagerInternal.UsageEventListener() { // from class: com.android.server.pm.BackgroundInstallControlService$$ExternalSyntheticLambda2
            @Override // android.app.usage.UsageStatsManagerInternal.UsageEventListener
            public final void onUsageEvent(int i, android.app.usage.UsageEvents.Event event) {
                com.android.server.pm.BackgroundInstallControlService.this.lambda$new$0(i, event);
            }
        });
        this.mBinderService = new com.android.server.pm.BackgroundInstallControlService.BinderService(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, android.app.usage.UsageEvents.Event event) {
        this.mHandler.obtainMessage(0, i, 0, event).sendToTarget();
    }

    private static final class BinderService extends android.content.pm.IBackgroundInstallControlService.Stub {
        final com.android.server.pm.BackgroundInstallControlService mService;

        BinderService(com.android.server.pm.BackgroundInstallControlService backgroundInstallControlService) {
            this.mService = backgroundInstallControlService;
        }

        public android.content.pm.ParceledListSlice<android.content.pm.PackageInfo> getBackgroundInstalledPackages(long j, int i) {
            if (android.app.Flags.bicClient()) {
                this.mService.enforceCallerPermissions();
            }
            if (!android.os.Build.IS_DEBUGGABLE) {
                return this.mService.getBackgroundInstalledPackages(j, i);
            }
            java.lang.String str = android.os.SystemProperties.get("debug.transparency.bg-install-apps");
            if (android.text.TextUtils.isEmpty(str)) {
                return this.mService.getBackgroundInstalledPackages(j, i);
            }
            return this.mService.getMockBackgroundInstalledPackages(str);
        }

        public void registerBackgroundInstallCallback(android.os.IRemoteCallback iRemoteCallback) {
            this.mService.mCallbackHelper.registerBackgroundInstallCallback(iRemoteCallback);
        }

        public void unregisterBackgroundInstallCallback(android.os.IRemoteCallback iRemoteCallback) {
            this.mService.mCallbackHelper.unregisterBackgroundInstallCallback(iRemoteCallback);
        }
    }

    @android.annotation.RequiresPermission("android.permission.GET_BACKGROUND_INSTALLED_PACKAGES")
    void enforceCallerPermissions() throws java.lang.SecurityException {
        this.mContext.enforceCallingOrSelfPermission("android.permission.GET_BACKGROUND_INSTALLED_PACKAGES", "User is not permitted to call service: android.permission.GET_BACKGROUND_INSTALLED_PACKAGES");
    }

    @com.android.internal.annotations.VisibleForTesting
    android.content.pm.ParceledListSlice<android.content.pm.PackageInfo> getBackgroundInstalledPackages(long j, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(android.content.pm.PackageManager.PackageInfoFlags.of(j), i);
            initBackgroundInstalledPackages();
            java.util.ListIterator listIterator = installedPackagesAsUser.listIterator();
            while (listIterator.hasNext()) {
                if (!this.mBackgroundInstalledPackages.contains(i, ((android.content.pm.PackageInfo) listIterator.next()).packageName)) {
                    listIterator.remove();
                }
            }
            android.content.pm.ParceledListSlice<android.content.pm.PackageInfo> parceledListSlice = new android.content.pm.ParceledListSlice<>(installedPackagesAsUser);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return parceledListSlice;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @android.annotation.NonNull
    android.content.pm.ParceledListSlice<android.content.pm.PackageInfo> getMockBackgroundInstalledPackages(@android.annotation.NonNull java.lang.String str) {
        java.lang.String[] split = str.split(",");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str2 : split) {
            try {
                arrayList.add(this.mPackageManager.getPackageInfo(str2, android.content.pm.PackageManager.PackageInfoFlags.of(131072L)));
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.w(TAG, "Package's PackageInfo not found " + str2);
            }
        }
        return new android.content.pm.ParceledListSlice<>(arrayList);
    }

    private static class EventHandler extends android.os.Handler {
        private final com.android.server.pm.BackgroundInstallControlService mService;

        EventHandler(android.os.Looper looper, com.android.server.pm.BackgroundInstallControlService backgroundInstallControlService) {
            super(looper);
            this.mService = backgroundInstallControlService;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    this.mService.handleUsageEvent((android.app.usage.UsageEvents.Event) message.obj, message.arg1);
                    break;
                case 1:
                    this.mService.handlePackageAdd((java.lang.String) message.obj, message.arg1);
                    break;
                case 2:
                    this.mService.handlePackageRemove((java.lang.String) message.obj, message.arg1);
                    break;
                default:
                    android.util.Slog.w(com.android.server.pm.BackgroundInstallControlService.TAG, "Unknown message: " + message.what);
                    break;
            }
        }
    }

    void handlePackageAdd(java.lang.String str, int i) {
        try {
            android.content.pm.ApplicationInfo applicationInfoAsUser = this.mPackageManager.getApplicationInfoAsUser(str, android.content.pm.PackageManager.ApplicationInfoFlags.of(0L), i);
            try {
                android.content.pm.InstallSourceInfo installSourceInfo = this.mPackageManager.getInstallSourceInfo(str);
                java.lang.String installingPackageName = installSourceInfo.getInstallingPackageName();
                java.lang.String initiatingPackageName = installSourceInfo.getInitiatingPackageName();
                if (this.mPermissionManager.checkPermission(installingPackageName, "android.permission.INSTALL_PACKAGES", "default:0", i) != 0) {
                    return;
                }
                long currentTimeMillis = java.lang.System.currentTimeMillis() - (android.os.SystemClock.uptimeMillis() - retrieveInstallStartTimestamp(str, i, applicationInfoAsUser));
                if (installedByAdb(initiatingPackageName) || wasForegroundInstallation(installingPackageName, i, currentTimeMillis)) {
                    return;
                }
                initBackgroundInstalledPackages();
                this.mBackgroundInstalledPackages.add(i, str);
                this.mCallbackHelper.notifyAllCallbacks(i, str);
                writeBackgroundInstalledPackagesToDisk();
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.w(TAG, "Package's installer not found " + str);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            android.util.Slog.w(TAG, "Package's appInfo not found " + str);
        }
    }

    private long retrieveInstallStartTimestamp(java.lang.String str, int i, android.content.pm.ApplicationInfo applicationInfo) {
        long j = applicationInfo.createTimestamp;
        try {
            java.util.Optional<android.content.pm.PackageInstaller.SessionInfo> latestInstallSession = getLatestInstallSession(str, i);
            if (latestInstallSession.isEmpty()) {
                android.util.Slog.w(TAG, "Package's historical install session not found, falling back to appInfo.createTimestamp: " + str);
            } else {
                j = latestInstallSession.get().getCreatedMillis();
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Retrieval of install time from historical session failed, falling back to appInfo.createTimestamp");
            android.util.Slog.w(TAG, android.util.Log.getStackTraceString(e));
        }
        return j;
    }

    private java.util.Optional<android.content.pm.PackageInstaller.SessionInfo> getLatestInstallSession(final java.lang.String str, int i) {
        return this.mPackageManagerInternal.getHistoricalSessions(i).getList().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.pm.BackgroundInstallControlService$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getLatestInstallSession$1;
                lambda$getLatestInstallSession$1 = com.android.server.pm.BackgroundInstallControlService.lambda$getLatestInstallSession$1(str, (android.content.pm.PackageInstaller.SessionInfo) obj);
                return lambda$getLatestInstallSession$1;
            }
        }).max(java.util.Comparator.comparingLong(new java.util.function.ToLongFunction() { // from class: com.android.server.pm.BackgroundInstallControlService$$ExternalSyntheticLambda1
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(java.lang.Object obj) {
                return ((android.content.pm.PackageInstaller.SessionInfo) obj).getCreatedMillis();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getLatestInstallSession$1(java.lang.String str, android.content.pm.PackageInstaller.SessionInfo sessionInfo) {
        return str.equals(sessionInfo.getAppPackageName());
    }

    private boolean installedByAdb(java.lang.String str) {
        return com.android.server.pm.PackageManagerServiceUtils.isInstalledByAdb(str);
    }

    private boolean wasForegroundInstallation(java.lang.String str, int i, long j) {
        java.util.TreeSet treeSet = (java.util.TreeSet) this.mInstallerForegroundTimeFrames.get(i, str);
        if (treeSet == null) {
            return false;
        }
        java.util.Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            com.android.server.pm.BackgroundInstallControlService.ForegroundTimeFrame foregroundTimeFrame = (com.android.server.pm.BackgroundInstallControlService.ForegroundTimeFrame) it.next();
            if (foregroundTimeFrame.startTimeStampMillis <= j && (!foregroundTimeFrame.isDone() || j <= foregroundTimeFrame.endTimeStampMillis)) {
                return true;
            }
        }
        return false;
    }

    void handlePackageRemove(java.lang.String str, int i) {
        initBackgroundInstalledPackages();
        this.mBackgroundInstalledPackages.remove(i, str);
        writeBackgroundInstalledPackagesToDisk();
    }

    void handleUsageEvent(android.app.usage.UsageEvents.Event event, int i) {
        if ((event.mEventType != 1 && event.mEventType != 2 && event.mEventType != 23) || !isInstaller(event.mPackage, i)) {
            return;
        }
        if (!this.mInstallerForegroundTimeFrames.contains(i, event.mPackage)) {
            this.mInstallerForegroundTimeFrames.add(i, event.mPackage, new java.util.TreeSet());
        }
        java.util.TreeSet treeSet = (java.util.TreeSet) this.mInstallerForegroundTimeFrames.get(i, event.mPackage);
        if (treeSet.size() == 0 || ((com.android.server.pm.BackgroundInstallControlService.ForegroundTimeFrame) treeSet.last()).isDone()) {
            if (event.mEventType != 1) {
                return;
            } else {
                treeSet.add(new com.android.server.pm.BackgroundInstallControlService.ForegroundTimeFrame(event.mTimeStamp));
            }
        }
        ((com.android.server.pm.BackgroundInstallControlService.ForegroundTimeFrame) treeSet.last()).addEvent(event);
        if (treeSet.size() > 10) {
            treeSet.pollFirst();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void writeBackgroundInstalledPackagesToDisk() {
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(this.mDiskFile);
        try {
            java.io.FileOutputStream startWrite = atomicFile.startWrite();
            try {
                android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(startWrite);
                for (int i = 0; i < this.mBackgroundInstalledPackages.size(); i++) {
                    int keyAt = this.mBackgroundInstalledPackages.keyAt(i);
                    java.util.Iterator it = this.mBackgroundInstalledPackages.get(keyAt).iterator();
                    while (it.hasNext()) {
                        java.lang.String str = (java.lang.String) it.next();
                        long start = protoOutputStream.start(2246267895809L);
                        protoOutputStream.write(1138166333441L, str);
                        protoOutputStream.write(1120986464258L, keyAt + 1);
                        protoOutputStream.end(start);
                    }
                }
                protoOutputStream.flush();
                atomicFile.finishWrite(startWrite);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Failed to finish write to states protobuf.", e);
                atomicFile.failWrite(startWrite);
            }
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Failed to start write to states protobuf.", e2);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void initBackgroundInstalledPackages() {
        if (this.mBackgroundInstalledPackages != null) {
            return;
        }
        this.mBackgroundInstalledPackages = new android.util.SparseSetArray<>();
        if (!this.mDiskFile.exists()) {
            return;
        }
        try {
            java.io.FileInputStream openRead = new android.util.AtomicFile(this.mDiskFile).openRead();
            try {
                android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(openRead);
                while (protoInputStream.nextField() != -1) {
                    if (protoInputStream.getFieldNumber() == 1) {
                        long start = protoInputStream.start(2246267895809L);
                        java.lang.String str = null;
                        int i = -10000;
                        while (protoInputStream.nextField() != -1) {
                            switch (protoInputStream.getFieldNumber()) {
                                case 1:
                                    str = protoInputStream.readString(1138166333441L);
                                    break;
                                case 2:
                                    i = protoInputStream.readInt(1120986464258L) - 1;
                                    break;
                                default:
                                    android.util.Slog.w(TAG, "Undefined field in proto: " + protoInputStream.getFieldNumber());
                                    break;
                            }
                        }
                        protoInputStream.end(start);
                        if (str == null || i == -10000) {
                            android.util.Slog.w(TAG, "Fails to get packageName or UserId from proto file");
                        } else {
                            this.mBackgroundInstalledPackages.add(i, str);
                        }
                    }
                }
                if (openRead != null) {
                    openRead.close();
                }
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Error reading state from the disk", e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.util.SparseSetArray<java.lang.String> getBackgroundInstalledPackages() {
        return this.mBackgroundInstalledPackages;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.util.SparseArrayMap<java.lang.String, java.util.TreeSet<com.android.server.pm.BackgroundInstallControlService.ForegroundTimeFrame>> getInstallerForegroundTimeFrames() {
        return this.mInstallerForegroundTimeFrames;
    }

    private boolean isInstaller(java.lang.String str, int i) {
        return this.mInstallerForegroundTimeFrames.contains(i, str) || this.mPermissionManager.checkPermission(str, "android.permission.INSTALL_PACKAGES", "default:0", i) == 0;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        onStart(false);
    }

    @com.android.internal.annotations.VisibleForTesting
    void onStart(boolean z) {
        if (!z) {
            publishBinderService("background_install_control", this.mBinderService);
        }
        this.mPackageManagerInternal.getPackageList(this.mPackageObserver);
    }

    static final class ForegroundTimeFrame implements java.lang.Comparable<com.android.server.pm.BackgroundInstallControlService.ForegroundTimeFrame> {
        public final long startTimeStampMillis;
        public long endTimeStampMillis = 0;
        public final java.util.Set<java.lang.Integer> activities = new android.util.ArraySet();

        @Override // java.lang.Comparable
        public int compareTo(com.android.server.pm.BackgroundInstallControlService.ForegroundTimeFrame foregroundTimeFrame) {
            int compare = java.lang.Long.compare(this.startTimeStampMillis, foregroundTimeFrame.startTimeStampMillis);
            return compare != 0 ? compare : java.lang.Integer.compare(hashCode(), foregroundTimeFrame.hashCode());
        }

        ForegroundTimeFrame(long j) {
            this.startTimeStampMillis = j;
        }

        public boolean isDone() {
            return this.endTimeStampMillis != 0;
        }

        public void addEvent(android.app.usage.UsageEvents.Event event) {
            switch (event.mEventType) {
                case 1:
                    this.activities.add(java.lang.Integer.valueOf(event.mInstanceId));
                    break;
                case 2:
                case 23:
                    if (this.activities.contains(java.lang.Integer.valueOf(event.mInstanceId))) {
                        this.activities.remove(java.lang.Integer.valueOf(event.mInstanceId));
                        if (this.activities.size() == 0) {
                            this.endTimeStampMillis = event.mTimeStamp;
                            break;
                        }
                    }
                    break;
            }
        }
    }

    private static final class InjectorImpl implements com.android.server.pm.BackgroundInstallControlService.Injector {
        private final android.content.Context mContext;

        InjectorImpl(android.content.Context context) {
            this.mContext = context;
        }

        @Override // com.android.server.pm.BackgroundInstallControlService.Injector
        public android.content.Context getContext() {
            return this.mContext;
        }

        @Override // com.android.server.pm.BackgroundInstallControlService.Injector
        public android.content.pm.PackageManager getPackageManager() {
            return this.mContext.getPackageManager();
        }

        @Override // com.android.server.pm.BackgroundInstallControlService.Injector
        public android.content.pm.PackageManagerInternal getPackageManagerInternal() {
            return (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        }

        @Override // com.android.server.pm.BackgroundInstallControlService.Injector
        public android.app.usage.UsageStatsManagerInternal getUsageStatsManagerInternal() {
            return (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
        }

        @Override // com.android.server.pm.BackgroundInstallControlService.Injector
        public com.android.server.pm.permission.PermissionManagerServiceInternal getPermissionManager() {
            return (com.android.server.pm.permission.PermissionManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInternal.class);
        }

        @Override // com.android.server.pm.BackgroundInstallControlService.Injector
        public android.os.Looper getLooper() {
            com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(com.android.server.pm.BackgroundInstallControlService.TAG, -2, true);
            serviceThread.start();
            return serviceThread.getLooper();
        }

        @Override // com.android.server.pm.BackgroundInstallControlService.Injector
        public java.io.File getDiskFile() {
            return new java.io.File(new java.io.File(android.os.Environment.getDataSystemDirectory(), com.android.server.pm.BackgroundInstallControlService.DISK_DIR_NAME), com.android.server.pm.BackgroundInstallControlService.DISK_FILE_NAME);
        }

        @Override // com.android.server.pm.BackgroundInstallControlService.Injector
        public com.android.server.pm.BackgroundInstallControlCallbackHelper getBackgroundInstallControlCallbackHelper() {
            return new com.android.server.pm.BackgroundInstallControlCallbackHelper();
        }
    }
}
