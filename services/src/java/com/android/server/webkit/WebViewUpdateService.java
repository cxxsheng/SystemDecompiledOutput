package com.android.server.webkit;

/* loaded from: classes.dex */
public class WebViewUpdateService extends com.android.server.SystemService {
    static final int PACKAGE_ADDED = 1;
    static final int PACKAGE_ADDED_REPLACED = 2;
    static final int PACKAGE_CHANGED = 0;
    static final int PACKAGE_REMOVED = 3;
    private static final java.lang.String TAG = "WebViewUpdateService";
    private com.android.server.webkit.WebViewUpdateServiceInterface mImpl;
    private android.content.BroadcastReceiver mWebViewUpdatedReceiver;

    public WebViewUpdateService(android.content.Context context) {
        super(context);
        if (android.webkit.Flags.updateServiceV2()) {
            this.mImpl = new com.android.server.webkit.WebViewUpdateServiceImpl2(context, com.android.server.webkit.SystemImpl.getInstance());
        } else {
            this.mImpl = new com.android.server.webkit.WebViewUpdateServiceImpl(context, com.android.server.webkit.SystemImpl.getInstance());
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        this.mWebViewUpdatedReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.webkit.WebViewUpdateService.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                char c;
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
                java.lang.String action = intent.getAction();
                switch (action.hashCode()) {
                    case -2061058799:
                        if (action.equals("android.intent.action.USER_REMOVED")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -755112654:
                        if (action.equals("android.intent.action.USER_STARTED")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 172491798:
                        if (action.equals("android.intent.action.PACKAGE_CHANGED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 525384130:
                        if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1544582882:
                        if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        if (!intent.getExtras().getBoolean("android.intent.extra.REPLACING")) {
                            com.android.server.webkit.WebViewUpdateService.this.mImpl.packageStateChanged(com.android.server.webkit.WebViewUpdateService.packageNameFromIntent(intent), 3, intExtra);
                            break;
                        }
                        break;
                    case 1:
                        if (com.android.server.webkit.WebViewUpdateService.entirePackageChanged(intent)) {
                            com.android.server.webkit.WebViewUpdateService.this.mImpl.packageStateChanged(com.android.server.webkit.WebViewUpdateService.packageNameFromIntent(intent), 0, intExtra);
                            break;
                        }
                        break;
                    case 2:
                        com.android.server.webkit.WebViewUpdateService.this.mImpl.packageStateChanged(com.android.server.webkit.WebViewUpdateService.packageNameFromIntent(intent), intent.getExtras().getBoolean("android.intent.extra.REPLACING") ? 2 : 1, intExtra);
                        break;
                    case 3:
                        com.android.server.webkit.WebViewUpdateService.this.mImpl.handleNewUser(intExtra);
                        break;
                    case 4:
                        com.android.server.webkit.WebViewUpdateService.this.mImpl.handleUserRemoved(intExtra);
                        break;
                }
            }
        };
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        for (android.webkit.WebViewProviderInfo webViewProviderInfo : this.mImpl.getWebViewPackages()) {
            intentFilter.addDataSchemeSpecificPart(webViewProviderInfo.packageName, 0);
        }
        getContext().registerReceiverAsUser(this.mWebViewUpdatedReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_STARTED");
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        getContext().registerReceiverAsUser(this.mWebViewUpdatedReceiver, android.os.UserHandle.ALL, intentFilter2, null, null);
        publishBinderService("webviewupdate", new com.android.server.webkit.WebViewUpdateService.BinderService(), true);
    }

    public void prepareWebViewInSystemServer() {
        this.mImpl.prepareWebViewInSystemServer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String packageNameFromIntent(android.content.Intent intent) {
        return intent.getDataString().substring("package:".length());
    }

    public static boolean entirePackageChanged(android.content.Intent intent) {
        return java.util.Arrays.asList(intent.getStringArrayExtra("android.intent.extra.changed_component_name_list")).contains(intent.getDataString().substring("package:".length()));
    }

    private class BinderService extends android.webkit.IWebViewUpdateService.Stub {
        private BinderService() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            if (android.webkit.Flags.updateServiceV2()) {
                new com.android.server.webkit.WebViewUpdateServiceShellCommand2(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            } else {
                new com.android.server.webkit.WebViewUpdateServiceShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            }
        }

        public void notifyRelroCreationCompleted() {
            if (android.os.Binder.getCallingUid() != 1037 && android.os.Binder.getCallingUid() != 1000) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.webkit.WebViewUpdateService.this.mImpl.notifyRelroCreationCompleted();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.webkit.WebViewProviderResponse waitForAndGetProvider() {
            if (android.os.Binder.getCallingPid() == android.os.Process.myPid()) {
                throw new java.lang.IllegalStateException("Cannot create a WebView from the SystemServer");
            }
            android.webkit.WebViewProviderResponse waitForAndGetProvider = com.android.server.webkit.WebViewUpdateService.this.mImpl.waitForAndGetProvider();
            if (waitForAndGetProvider.packageInfo != null) {
                grantVisibilityToCaller(waitForAndGetProvider.packageInfo.packageName, android.os.Binder.getCallingUid());
            }
            return waitForAndGetProvider;
        }

        private void grantVisibilityToCaller(java.lang.String str, int i) {
            android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
            packageManagerInternal.grantImplicitAccess(android.os.UserHandle.getUserId(i), null, android.os.UserHandle.getAppId(i), packageManagerInternal.getPackageUid(str, 0L, android.os.UserHandle.getUserId(i)), true);
        }

        public java.lang.String changeProviderAndSetting(java.lang.String str) {
            if (com.android.server.webkit.WebViewUpdateService.this.getContext().checkCallingPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
                java.lang.String str2 = "Permission Denial: changeProviderAndSetting() from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " requires android.permission.WRITE_SECURE_SETTINGS";
                android.util.Slog.w(com.android.server.webkit.WebViewUpdateService.TAG, str2);
                throw new java.lang.SecurityException(str2);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.webkit.WebViewUpdateService.this.mImpl.changeProviderAndSetting(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.webkit.WebViewProviderInfo[] getValidWebViewPackages() {
            return com.android.server.webkit.WebViewUpdateService.this.mImpl.getValidWebViewPackages();
        }

        public android.webkit.WebViewProviderInfo getDefaultWebViewPackage() {
            return com.android.server.webkit.WebViewUpdateService.this.mImpl.getDefaultWebViewPackage();
        }

        public android.webkit.WebViewProviderInfo[] getAllWebViewPackages() {
            return com.android.server.webkit.WebViewUpdateService.this.mImpl.getWebViewPackages();
        }

        public java.lang.String getCurrentWebViewPackageName() {
            android.content.pm.PackageInfo currentWebViewPackage = getCurrentWebViewPackage();
            if (currentWebViewPackage == null) {
                return null;
            }
            return currentWebViewPackage.packageName;
        }

        public android.content.pm.PackageInfo getCurrentWebViewPackage() {
            android.content.pm.PackageInfo currentWebViewPackage = com.android.server.webkit.WebViewUpdateService.this.mImpl.getCurrentWebViewPackage();
            if (currentWebViewPackage != null) {
                grantVisibilityToCaller(currentWebViewPackage.packageName, android.os.Binder.getCallingUid());
            }
            return currentWebViewPackage;
        }

        public boolean isMultiProcessEnabled() {
            if (android.webkit.Flags.updateServiceV2()) {
                throw new java.lang.IllegalStateException("isMultiProcessEnabled shouldn't be called if update_service_v2 flag is set.");
            }
            return com.android.server.webkit.WebViewUpdateService.this.mImpl.isMultiProcessEnabled();
        }

        public void enableMultiProcess(boolean z) {
            if (android.webkit.Flags.updateServiceV2()) {
                throw new java.lang.IllegalStateException("enableMultiProcess shouldn't be called if update_service_v2 flag is set.");
            }
            if (com.android.server.webkit.WebViewUpdateService.this.getContext().checkCallingPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
                java.lang.String str = "Permission Denial: enableMultiProcess() from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " requires android.permission.WRITE_SECURE_SETTINGS";
                android.util.Slog.w(com.android.server.webkit.WebViewUpdateService.TAG, str);
                throw new java.lang.SecurityException(str);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.webkit.WebViewUpdateService.this.mImpl.enableMultiProcess(z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.webkit.WebViewUpdateService.this.getContext(), com.android.server.webkit.WebViewUpdateService.TAG, printWriter)) {
                com.android.server.webkit.WebViewUpdateService.this.mImpl.dumpState(printWriter);
            }
        }
    }
}
