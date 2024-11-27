package com.android.server.appbinding;

/* loaded from: classes.dex */
public class AppBindingService extends android.os.Binder {
    public static final boolean DEBUG = false;
    public static final java.lang.String TAG = "AppBindingService";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.appbinding.finders.AppServiceFinder> mApps;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.appbinding.AppBindingService.AppServiceConnection> mConnections;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.appbinding.AppBindingConstants mConstants;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final android.content.pm.IPackageManager mIPackageManager;
    private final com.android.server.appbinding.AppBindingService.Injector mInjector;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.VisibleForTesting
    final android.content.BroadcastReceiver mPackageUserMonitor;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mRunningUsers;
    private final android.database.ContentObserver mSettingsObserver;

    static class Injector {
        Injector() {
        }

        public android.content.pm.IPackageManager getIPackageManager() {
            return android.app.AppGlobals.getPackageManager();
        }

        public java.lang.String getGlobalSettingString(android.content.ContentResolver contentResolver, java.lang.String str) {
            return android.provider.Settings.Global.getString(contentResolver, str);
        }
    }

    public static class Lifecycle extends com.android.server.SystemService {
        final com.android.server.appbinding.AppBindingService mService;

        public Lifecycle(android.content.Context context) {
            this(context, new com.android.server.appbinding.AppBindingService.Injector());
        }

        Lifecycle(android.content.Context context, com.android.server.appbinding.AppBindingService.Injector injector) {
            super(context);
            this.mService = new com.android.server.appbinding.AppBindingService(injector, context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            publishBinderService("app_binding", this.mService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            this.mService.onBootPhase(i);
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onStartUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onUnlockUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onStopUser(targetUser.getUserIdentifier());
        }
    }

    private AppBindingService(com.android.server.appbinding.AppBindingService.Injector injector, android.content.Context context) {
        this.mLock = new java.lang.Object();
        this.mRunningUsers = new android.util.SparseBooleanArray(2);
        this.mApps = new java.util.ArrayList<>();
        this.mConnections = new java.util.ArrayList<>();
        this.mSettingsObserver = new android.database.ContentObserver(null) { // from class: com.android.server.appbinding.AppBindingService.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.appbinding.AppBindingService.this.refreshConstants();
            }
        };
        this.mPackageUserMonitor = new android.content.BroadcastReceiver() { // from class: com.android.server.appbinding.AppBindingService.2
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Code restructure failed: missing block: B:19:0x006b, code lost:
            
                if (r0.equals("android.intent.action.PACKAGE_ADDED") != false) goto L27;
             */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
                if (intExtra == -10000) {
                    android.util.Slog.w(com.android.server.appbinding.AppBindingService.TAG, "Intent broadcast does not contain user handle: " + intent);
                }
                java.lang.String action = intent.getAction();
                if ("android.intent.action.USER_REMOVED".equals(action)) {
                    com.android.server.appbinding.AppBindingService.this.onUserRemoved(intExtra);
                    return;
                }
                android.net.Uri data = intent.getData();
                java.lang.String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                if (schemeSpecificPart == null) {
                    android.util.Slog.w(com.android.server.appbinding.AppBindingService.TAG, "Intent broadcast does not contain package name: " + intent);
                    return;
                }
                char c = 0;
                boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                switch (action.hashCode()) {
                    case 172491798:
                        if (action.equals("android.intent.action.PACKAGE_CHANGED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1544582882:
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        if (booleanExtra) {
                            com.android.server.appbinding.AppBindingService.this.handlePackageAddedReplacing(schemeSpecificPart, intExtra);
                            break;
                        }
                        break;
                    case 1:
                        com.android.server.appbinding.AppBindingService.this.handlePackageAddedReplacing(schemeSpecificPart, intExtra);
                        break;
                }
            }
        };
        this.mInjector = injector;
        this.mContext = context;
        this.mIPackageManager = injector.getIPackageManager();
        this.mHandler = com.android.internal.os.BackgroundThread.getHandler();
        this.mApps.add(new com.android.server.appbinding.finders.CarrierMessagingClientServiceFinder(context, new java.util.function.BiConsumer() { // from class: com.android.server.appbinding.AppBindingService$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.appbinding.AppBindingService.this.onAppChanged((com.android.server.appbinding.finders.AppServiceFinder) obj, ((java.lang.Integer) obj2).intValue());
            }
        }, this.mHandler));
        this.mConstants = com.android.server.appbinding.AppBindingConstants.initializeFromString("");
    }

    private void forAllAppsLocked(java.util.function.Consumer<com.android.server.appbinding.finders.AppServiceFinder> consumer) {
        for (int i = 0; i < this.mApps.size(); i++) {
            consumer.accept(this.mApps.get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBootPhase(int i) {
        switch (i) {
            case 550:
                onPhaseActivityManagerReady();
                break;
            case 600:
                onPhaseThirdPartyAppsCanStart();
                break;
        }
    }

    private void onPhaseActivityManagerReady() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiverAsUser(this.mPackageUserMonitor, android.os.UserHandle.ALL, intentFilter, null, this.mHandler);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiverAsUser(this.mPackageUserMonitor, android.os.UserHandle.ALL, intentFilter2, null, this.mHandler);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("app_binding_constants"), false, this.mSettingsObserver);
        refreshConstants();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshConstants() {
        java.lang.String globalSettingString = this.mInjector.getGlobalSettingString(this.mContext.getContentResolver(), "app_binding_constants");
        synchronized (this.mLock) {
            try {
                if (android.text.TextUtils.equals(this.mConstants.sourceSettings, globalSettingString)) {
                    return;
                }
                android.util.Slog.i(TAG, "Updating constants with: " + globalSettingString);
                this.mConstants = com.android.server.appbinding.AppBindingConstants.initializeFromString(globalSettingString);
                rebindAllLocked("settings update");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void onPhaseThirdPartyAppsCanStart() {
        synchronized (this.mLock) {
            forAllAppsLocked(new java.util.function.Consumer() { // from class: com.android.server.appbinding.AppBindingService$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.appbinding.finders.AppServiceFinder) obj).startMonitoring();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStartUser(int i) {
        synchronized (this.mLock) {
            this.mRunningUsers.append(i, true);
            bindServicesLocked(i, null, "user start");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUnlockUser(int i) {
        synchronized (this.mLock) {
            bindServicesLocked(i, null, "user unlock");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStopUser(int i) {
        synchronized (this.mLock) {
            unbindServicesLocked(i, null, "user stop");
            this.mRunningUsers.delete(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserRemoved(final int i) {
        synchronized (this.mLock) {
            forAllAppsLocked(new java.util.function.Consumer() { // from class: com.android.server.appbinding.AppBindingService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.appbinding.finders.AppServiceFinder) obj).onUserRemoved(i);
                }
            });
            this.mRunningUsers.delete(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAppChanged(com.android.server.appbinding.finders.AppServiceFinder appServiceFinder, int i) {
        synchronized (this.mLock) {
            java.lang.String str = appServiceFinder.getAppDescription() + " changed";
            unbindServicesLocked(i, appServiceFinder, str);
            bindServicesLocked(i, appServiceFinder, str);
        }
    }

    @android.annotation.Nullable
    private com.android.server.appbinding.finders.AppServiceFinder findFinderLocked(int i, @android.annotation.NonNull java.lang.String str) {
        for (int i2 = 0; i2 < this.mApps.size(); i2++) {
            com.android.server.appbinding.finders.AppServiceFinder appServiceFinder = this.mApps.get(i2);
            if (str.equals(appServiceFinder.getTargetPackage(i))) {
                return appServiceFinder;
            }
        }
        return null;
    }

    @android.annotation.Nullable
    private com.android.server.appbinding.AppBindingService.AppServiceConnection findConnectionLock(int i, @android.annotation.NonNull com.android.server.appbinding.finders.AppServiceFinder appServiceFinder) {
        for (int i2 = 0; i2 < this.mConnections.size(); i2++) {
            com.android.server.appbinding.AppBindingService.AppServiceConnection appServiceConnection = this.mConnections.get(i2);
            if (appServiceConnection.getUserId() == i && appServiceConnection.getFinder() == appServiceFinder) {
                return appServiceConnection;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackageAddedReplacing(java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.appbinding.finders.AppServiceFinder findFinderLocked = findFinderLocked(i, str);
                if (findFinderLocked != null) {
                    unbindServicesLocked(i, findFinderLocked, "package update");
                    bindServicesLocked(i, findFinderLocked, "package update");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void rebindAllLocked(java.lang.String str) {
        for (int i = 0; i < this.mRunningUsers.size(); i++) {
            if (this.mRunningUsers.valueAt(i)) {
                int keyAt = this.mRunningUsers.keyAt(i);
                unbindServicesLocked(keyAt, null, str);
                bindServicesLocked(keyAt, null, str);
            }
        }
    }

    private void bindServicesLocked(int i, @android.annotation.Nullable com.android.server.appbinding.finders.AppServiceFinder appServiceFinder, @android.annotation.NonNull java.lang.String str) {
        for (int i2 = 0; i2 < this.mApps.size(); i2++) {
            com.android.server.appbinding.finders.AppServiceFinder appServiceFinder2 = this.mApps.get(i2);
            if (appServiceFinder == null || appServiceFinder == appServiceFinder2) {
                if (findConnectionLock(i, appServiceFinder2) != null) {
                    unbindServicesLocked(i, appServiceFinder, str);
                }
                android.content.pm.ServiceInfo findService = appServiceFinder2.findService(i, this.mIPackageManager, this.mConstants);
                if (findService != null) {
                    com.android.server.appbinding.AppBindingService.AppServiceConnection appServiceConnection = new com.android.server.appbinding.AppBindingService.AppServiceConnection(this.mContext, i, this.mConstants, this.mHandler, appServiceFinder2, findService.getComponentName());
                    this.mConnections.add(appServiceConnection);
                    appServiceConnection.bind();
                }
            }
        }
    }

    private void unbindServicesLocked(int i, @android.annotation.Nullable com.android.server.appbinding.finders.AppServiceFinder appServiceFinder, @android.annotation.NonNull java.lang.String str) {
        for (int size = this.mConnections.size() - 1; size >= 0; size--) {
            com.android.server.appbinding.AppBindingService.AppServiceConnection appServiceConnection = this.mConnections.get(size);
            if (appServiceConnection.getUserId() == i && (appServiceFinder == null || appServiceConnection.getFinder() == appServiceFinder)) {
                this.mConnections.remove(size);
                appServiceConnection.unbind();
            }
        }
    }

    private static class AppServiceConnection extends com.android.server.am.PersistentConnection<android.os.IInterface> {
        private final com.android.server.appbinding.AppBindingConstants mConstants;
        private final com.android.server.appbinding.finders.AppServiceFinder mFinder;

        AppServiceConnection(android.content.Context context, int i, com.android.server.appbinding.AppBindingConstants appBindingConstants, android.os.Handler handler, com.android.server.appbinding.finders.AppServiceFinder appServiceFinder, @android.annotation.NonNull android.content.ComponentName componentName) {
            super(com.android.server.appbinding.AppBindingService.TAG, context, handler, i, componentName, appBindingConstants.SERVICE_RECONNECT_BACKOFF_SEC, appBindingConstants.SERVICE_RECONNECT_BACKOFF_INCREASE, appBindingConstants.SERVICE_RECONNECT_MAX_BACKOFF_SEC, appBindingConstants.SERVICE_STABLE_CONNECTION_THRESHOLD_SEC);
            this.mFinder = appServiceFinder;
            this.mConstants = appBindingConstants;
        }

        @Override // com.android.server.am.PersistentConnection
        protected int getBindFlags() {
            return this.mFinder.getBindFlags(this.mConstants);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.server.am.PersistentConnection
        public android.os.IInterface asInterface(android.os.IBinder iBinder) {
            return this.mFinder.asInterface(iBinder);
        }

        public com.android.server.appbinding.finders.AppServiceFinder getFinder() {
            return this.mFinder;
        }
    }

    @Override // android.os.Binder
    public void dump(java.io.FileDescriptor fileDescriptor, final java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            if (strArr.length > 0 && "-s".equals(strArr[0])) {
                dumpSimple(printWriter);
                return;
            }
            synchronized (this.mLock) {
                try {
                    this.mConstants.dump("  ", printWriter);
                    printWriter.println();
                    printWriter.print("  Running users:");
                    for (int i = 0; i < this.mRunningUsers.size(); i++) {
                        if (this.mRunningUsers.valueAt(i)) {
                            printWriter.print(" ");
                            printWriter.print(this.mRunningUsers.keyAt(i));
                        }
                    }
                    printWriter.println();
                    printWriter.println("  Connections:");
                    for (int i2 = 0; i2 < this.mConnections.size(); i2++) {
                        com.android.server.appbinding.AppBindingService.AppServiceConnection appServiceConnection = this.mConnections.get(i2);
                        printWriter.print("    App type: ");
                        printWriter.print(appServiceConnection.getFinder().getAppDescription());
                        printWriter.println();
                        appServiceConnection.dump("      ", printWriter);
                    }
                    if (this.mConnections.size() == 0) {
                        printWriter.println("    None:");
                    }
                    printWriter.println();
                    printWriter.println("  Finders:");
                    forAllAppsLocked(new java.util.function.Consumer() { // from class: com.android.server.appbinding.AppBindingService$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((com.android.server.appbinding.finders.AppServiceFinder) obj).dump("    ", printWriter);
                        }
                    });
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void dumpSimple(final java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mConnections.size(); i++) {
                try {
                    com.android.server.appbinding.AppBindingService.AppServiceConnection appServiceConnection = this.mConnections.get(i);
                    printWriter.print("conn,");
                    printWriter.print(appServiceConnection.getFinder().getAppDescription());
                    printWriter.print(",");
                    printWriter.print(appServiceConnection.getUserId());
                    printWriter.print(",");
                    printWriter.print(appServiceConnection.getComponentName().getPackageName());
                    printWriter.print(",");
                    printWriter.print(appServiceConnection.getComponentName().getClassName());
                    printWriter.print(",");
                    printWriter.print(appServiceConnection.isBound() ? "bound" : "not-bound");
                    printWriter.print(",");
                    printWriter.print(appServiceConnection.isConnected() ? "connected" : "not-connected");
                    printWriter.print(",#con=");
                    printWriter.print(appServiceConnection.getNumConnected());
                    printWriter.print(",#dis=");
                    printWriter.print(appServiceConnection.getNumDisconnected());
                    printWriter.print(",#died=");
                    printWriter.print(appServiceConnection.getNumBindingDied());
                    printWriter.print(",backoff=");
                    printWriter.print(appServiceConnection.getNextBackoffMs());
                    printWriter.println();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            forAllAppsLocked(new java.util.function.Consumer() { // from class: com.android.server.appbinding.AppBindingService$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.appbinding.finders.AppServiceFinder) obj).dumpSimple(printWriter);
                }
            });
        }
    }

    com.android.server.appbinding.AppBindingConstants getConstantsForTest() {
        return this.mConstants;
    }
}
