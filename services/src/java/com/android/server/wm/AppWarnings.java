package com.android.server.wm;

/* loaded from: classes3.dex */
class AppWarnings {
    private static final java.lang.String CONFIG_FILE_NAME = "packages-warnings.xml";
    public static final int FLAG_HIDE_COMPILE_SDK = 2;
    public static final int FLAG_HIDE_DEPRECATED_ABI = 8;
    public static final int FLAG_HIDE_DEPRECATED_SDK = 4;
    public static final int FLAG_HIDE_DISPLAY_SIZE = 1;
    private static final java.lang.String TAG = "AppWarnings";
    private final com.android.server.wm.ActivityTaskManagerService mAtm;
    private final android.util.AtomicFile mConfigFile;
    private com.android.server.wm.DeprecatedAbiDialog mDeprecatedAbiDialog;
    private com.android.server.wm.DeprecatedTargetSdkVersionDialog mDeprecatedTargetSdkVersionDialog;
    private final android.content.Context mUiContext;
    private final com.android.server.wm.AppWarnings.UiHandler mUiHandler;
    private com.android.server.wm.UnsupportedCompileSdkDialog mUnsupportedCompileSdkDialog;
    private com.android.server.wm.UnsupportedDisplaySizeDialog mUnsupportedDisplaySizeDialog;

    @com.android.internal.annotations.GuardedBy({"mPackageFlags"})
    private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mPackageFlags = new android.util.ArrayMap<>();
    private final android.util.ArraySet<android.content.ComponentName> mAlwaysShowUnsupportedCompileSdkWarningActivities = new android.util.ArraySet<>();
    private final com.android.server.wm.AppWarnings.WriteConfigTask mWriteConfigTask = new com.android.server.wm.AppWarnings.WriteConfigTask();

    void alwaysShowUnsupportedCompileSdkWarning(android.content.ComponentName componentName) {
        this.mAlwaysShowUnsupportedCompileSdkWarningActivities.add(componentName);
    }

    public AppWarnings(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, android.content.Context context, android.os.Handler handler, android.os.Handler handler2, java.io.File file) {
        this.mAtm = activityTaskManagerService;
        this.mUiContext = context;
        this.mUiHandler = new com.android.server.wm.AppWarnings.UiHandler(handler2.getLooper());
        this.mConfigFile = new android.util.AtomicFile(new java.io.File(file, CONFIG_FILE_NAME), "warnings-config");
        readConfigFromFileAmsThread();
    }

    public void showUnsupportedDisplaySizeDialogIfNeeded(com.android.server.wm.ActivityRecord activityRecord) {
        android.content.res.Configuration globalConfiguration = this.mAtm.getGlobalConfiguration();
        if (globalConfiguration.densityDpi != android.util.DisplayMetrics.DENSITY_DEVICE_STABLE && activityRecord.info.applicationInfo.requiresSmallestWidthDp > globalConfiguration.smallestScreenWidthDp) {
            this.mUiHandler.showUnsupportedDisplaySizeDialog(activityRecord);
        }
    }

    public void showUnsupportedCompileSdkDialogIfNeeded(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.info.applicationInfo.compileSdkVersion == 0 || activityRecord.info.applicationInfo.compileSdkVersionCodename == null || !this.mAlwaysShowUnsupportedCompileSdkWarningActivities.contains(activityRecord.mActivityComponent)) {
            return;
        }
        int i = activityRecord.info.applicationInfo.compileSdkVersion;
        int i2 = android.os.Build.VERSION.SDK_INT;
        boolean z = !"REL".equals(activityRecord.info.applicationInfo.compileSdkVersionCodename);
        boolean z2 = !"REL".equals(android.os.Build.VERSION.CODENAME);
        if ((z && i < i2) || ((z2 && i2 < i) || (z && z2 && i2 == i && !android.os.Build.VERSION.CODENAME.equals(activityRecord.info.applicationInfo.compileSdkVersionCodename)))) {
            this.mUiHandler.showUnsupportedCompileSdkDialog(activityRecord);
        }
    }

    public void showDeprecatedTargetDialogIfNeeded(com.android.server.wm.ActivityRecord activityRecord) {
        boolean z = android.os.SystemProperties.getBoolean("debug.wm.disable_deprecated_target_sdk_dialog", false);
        if (activityRecord.info.applicationInfo.targetSdkVersion < android.os.Build.VERSION.MIN_SUPPORTED_TARGET_SDK_INT && !z) {
            this.mUiHandler.showDeprecatedTargetDialog(activityRecord);
        }
    }

    public void showDeprecatedAbiDialogIfNeeded(com.android.server.wm.ActivityRecord activityRecord) {
        if (((activityRecord.info.applicationInfo.privateFlagsExt & 32) != 0) || android.os.SystemProperties.getBoolean("debug.wm.disable_deprecated_abi_dialog", false)) {
            return;
        }
        java.lang.String str = activityRecord.info.applicationInfo.primaryCpuAbi;
        boolean z = (str == null || activityRecord.info.applicationInfo.secondaryCpuAbi != null || str.contains("64")) ? false : true;
        if ((com.android.internal.util.ArrayUtils.find(android.os.Build.SUPPORTED_ABIS, new java.util.function.Predicate() { // from class: com.android.server.wm.AppWarnings$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$showDeprecatedAbiDialogIfNeeded$0;
                lambda$showDeprecatedAbiDialogIfNeeded$0 = com.android.server.wm.AppWarnings.lambda$showDeprecatedAbiDialogIfNeeded$0((java.lang.String) obj);
                return lambda$showDeprecatedAbiDialogIfNeeded$0;
            }
        }) != null) && z) {
            this.mUiHandler.showDeprecatedAbiDialog(activityRecord);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$showDeprecatedAbiDialogIfNeeded$0(java.lang.String str) {
        return str.contains("64");
    }

    public void onStartActivity(com.android.server.wm.ActivityRecord activityRecord) {
        showUnsupportedCompileSdkDialogIfNeeded(activityRecord);
        showUnsupportedDisplaySizeDialogIfNeeded(activityRecord);
        showDeprecatedTargetDialogIfNeeded(activityRecord);
        showDeprecatedAbiDialogIfNeeded(activityRecord);
    }

    public void onResumeActivity(com.android.server.wm.ActivityRecord activityRecord) {
        showUnsupportedDisplaySizeDialogIfNeeded(activityRecord);
    }

    public void onPackageDataCleared(java.lang.String str) {
        removePackageAndHideDialogs(str);
    }

    public void onPackageUninstalled(java.lang.String str) {
        removePackageAndHideDialogs(str);
    }

    public void onDensityChanged() {
        this.mUiHandler.hideUnsupportedDisplaySizeDialog();
    }

    private void removePackageAndHideDialogs(java.lang.String str) {
        this.mUiHandler.hideDialogsForPackage(str);
        synchronized (this.mPackageFlags) {
            try {
                if (this.mPackageFlags.remove(str) != null) {
                    this.mWriteConfigTask.schedule();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideUnsupportedDisplaySizeDialogUiThread() {
        if (this.mUnsupportedDisplaySizeDialog != null) {
            this.mUnsupportedDisplaySizeDialog.dismiss();
            this.mUnsupportedDisplaySizeDialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUnsupportedDisplaySizeDialogUiThread(com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mUnsupportedDisplaySizeDialog != null) {
            this.mUnsupportedDisplaySizeDialog.dismiss();
            this.mUnsupportedDisplaySizeDialog = null;
        }
        if (activityRecord != null && !hasPackageFlag(activityRecord.packageName, 1)) {
            this.mUnsupportedDisplaySizeDialog = new com.android.server.wm.UnsupportedDisplaySizeDialog(this, this.mUiContext, activityRecord.info.applicationInfo);
            this.mUnsupportedDisplaySizeDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUnsupportedCompileSdkDialogUiThread(com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mUnsupportedCompileSdkDialog != null) {
            this.mUnsupportedCompileSdkDialog.dismiss();
            this.mUnsupportedCompileSdkDialog = null;
        }
        if (activityRecord != null && !hasPackageFlag(activityRecord.packageName, 2)) {
            this.mUnsupportedCompileSdkDialog = new com.android.server.wm.UnsupportedCompileSdkDialog(this, this.mUiContext, activityRecord.info.applicationInfo);
            this.mUnsupportedCompileSdkDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeprecatedTargetSdkDialogUiThread(com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mDeprecatedTargetSdkVersionDialog != null) {
            this.mDeprecatedTargetSdkVersionDialog.dismiss();
            this.mDeprecatedTargetSdkVersionDialog = null;
        }
        if (activityRecord != null && !hasPackageFlag(activityRecord.packageName, 4)) {
            this.mDeprecatedTargetSdkVersionDialog = new com.android.server.wm.DeprecatedTargetSdkVersionDialog(this, this.mUiContext, activityRecord.info.applicationInfo);
            this.mDeprecatedTargetSdkVersionDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeprecatedAbiDialogUiThread(com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mDeprecatedAbiDialog != null) {
            this.mDeprecatedAbiDialog.dismiss();
            this.mDeprecatedAbiDialog = null;
        }
        if (activityRecord != null && !hasPackageFlag(activityRecord.packageName, 8)) {
            this.mDeprecatedAbiDialog = new com.android.server.wm.DeprecatedAbiDialog(this, this.mUiContext, activityRecord.info.applicationInfo);
            this.mDeprecatedAbiDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideDialogsForPackageUiThread(java.lang.String str) {
        if (this.mUnsupportedDisplaySizeDialog != null && (str == null || str.equals(this.mUnsupportedDisplaySizeDialog.mPackageName))) {
            this.mUnsupportedDisplaySizeDialog.dismiss();
            this.mUnsupportedDisplaySizeDialog = null;
        }
        if (this.mUnsupportedCompileSdkDialog != null && (str == null || str.equals(this.mUnsupportedCompileSdkDialog.mPackageName))) {
            this.mUnsupportedCompileSdkDialog.dismiss();
            this.mUnsupportedCompileSdkDialog = null;
        }
        if (this.mDeprecatedTargetSdkVersionDialog != null && (str == null || str.equals(this.mDeprecatedTargetSdkVersionDialog.mPackageName))) {
            this.mDeprecatedTargetSdkVersionDialog.dismiss();
            this.mDeprecatedTargetSdkVersionDialog = null;
        }
        if (this.mDeprecatedAbiDialog != null) {
            if (str == null || str.equals(this.mDeprecatedAbiDialog.mPackageName)) {
                this.mDeprecatedAbiDialog.dismiss();
                this.mDeprecatedAbiDialog = null;
            }
        }
    }

    boolean hasPackageFlag(java.lang.String str, int i) {
        return (getPackageFlags(str) & i) == i;
    }

    void setPackageFlag(java.lang.String str, int i, boolean z) {
        synchronized (this.mPackageFlags) {
            try {
                int packageFlags = getPackageFlags(str);
                int i2 = z ? i | packageFlags : (~i) & packageFlags;
                if (packageFlags != i2) {
                    if (i2 != 0) {
                        this.mPackageFlags.put(str, java.lang.Integer.valueOf(i2));
                    } else {
                        this.mPackageFlags.remove(str);
                    }
                    this.mWriteConfigTask.schedule();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int getPackageFlags(java.lang.String str) {
        int intValue;
        synchronized (this.mPackageFlags) {
            intValue = this.mPackageFlags.getOrDefault(str, 0).intValue();
        }
        return intValue;
    }

    private final class UiHandler extends android.os.Handler {
        private static final int MSG_HIDE_DIALOGS_FOR_PACKAGE = 4;
        private static final int MSG_HIDE_UNSUPPORTED_DISPLAY_SIZE_DIALOG = 2;
        private static final int MSG_SHOW_DEPRECATED_ABI_DIALOG = 6;
        private static final int MSG_SHOW_DEPRECATED_TARGET_SDK_DIALOG = 5;
        private static final int MSG_SHOW_UNSUPPORTED_COMPILE_SDK_DIALOG = 3;
        private static final int MSG_SHOW_UNSUPPORTED_DISPLAY_SIZE_DIALOG = 1;

        public UiHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.wm.AppWarnings.this.showUnsupportedDisplaySizeDialogUiThread((com.android.server.wm.ActivityRecord) message.obj);
                    break;
                case 2:
                    com.android.server.wm.AppWarnings.this.hideUnsupportedDisplaySizeDialogUiThread();
                    break;
                case 3:
                    com.android.server.wm.AppWarnings.this.showUnsupportedCompileSdkDialogUiThread((com.android.server.wm.ActivityRecord) message.obj);
                    break;
                case 4:
                    com.android.server.wm.AppWarnings.this.hideDialogsForPackageUiThread((java.lang.String) message.obj);
                    break;
                case 5:
                    com.android.server.wm.AppWarnings.this.showDeprecatedTargetSdkDialogUiThread((com.android.server.wm.ActivityRecord) message.obj);
                    break;
                case 6:
                    com.android.server.wm.AppWarnings.this.showDeprecatedAbiDialogUiThread((com.android.server.wm.ActivityRecord) message.obj);
                    break;
            }
        }

        public void showUnsupportedDisplaySizeDialog(com.android.server.wm.ActivityRecord activityRecord) {
            removeMessages(1);
            obtainMessage(1, activityRecord).sendToTarget();
        }

        public void hideUnsupportedDisplaySizeDialog() {
            removeMessages(2);
            sendEmptyMessage(2);
        }

        public void showUnsupportedCompileSdkDialog(com.android.server.wm.ActivityRecord activityRecord) {
            removeMessages(3);
            obtainMessage(3, activityRecord).sendToTarget();
        }

        public void showDeprecatedTargetDialog(com.android.server.wm.ActivityRecord activityRecord) {
            removeMessages(5);
            obtainMessage(5, activityRecord).sendToTarget();
        }

        public void showDeprecatedAbiDialog(com.android.server.wm.ActivityRecord activityRecord) {
            removeMessages(6);
            obtainMessage(6, activityRecord).sendToTarget();
        }

        public void hideDialogsForPackage(java.lang.String str) {
            obtainMessage(4, str).sendToTarget();
        }
    }

    static class BaseDialog {
        private android.content.BroadcastReceiver mCloseReceiver;
        android.app.AlertDialog mDialog;
        final com.android.server.wm.AppWarnings mManager;
        final java.lang.String mPackageName;

        BaseDialog(com.android.server.wm.AppWarnings appWarnings, java.lang.String str) {
            this.mManager = appWarnings;
            this.mPackageName = str;
        }

        void show() {
            if (this.mDialog == null) {
                return;
            }
            if (this.mCloseReceiver == null) {
                this.mCloseReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.wm.AppWarnings.BaseDialog.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(android.content.Context context, android.content.Intent intent) {
                        if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                            com.android.server.wm.AppWarnings.BaseDialog.this.mManager.mUiHandler.hideDialogsForPackage(com.android.server.wm.AppWarnings.BaseDialog.this.mPackageName);
                        }
                    }
                };
                this.mManager.mUiContext.registerReceiver(this.mCloseReceiver, new android.content.IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), 2);
            }
            android.util.Slog.w(com.android.server.wm.AppWarnings.TAG, "Showing " + getClass().getSimpleName() + " for package " + this.mPackageName);
            this.mDialog.show();
        }

        void dismiss() {
            if (this.mDialog == null) {
                return;
            }
            if (this.mCloseReceiver != null) {
                this.mManager.mUiContext.unregisterReceiver(this.mCloseReceiver);
                this.mCloseReceiver = null;
            }
            this.mDialog.dismiss();
            this.mDialog = null;
        }
    }

    private final class WriteConfigTask implements java.lang.Runnable {
        private static final long WRITE_CONFIG_DELAY_MS = 10000;
        final java.util.concurrent.atomic.AtomicReference<android.util.ArrayMap<java.lang.String, java.lang.Integer>> mPendingPackageFlags;

        private WriteConfigTask() {
            this.mPendingPackageFlags = new java.util.concurrent.atomic.AtomicReference<>();
        }

        @Override // java.lang.Runnable
        public void run() {
            android.util.ArrayMap<java.lang.String, java.lang.Integer> andSet = this.mPendingPackageFlags.getAndSet(null);
            if (andSet != null) {
                com.android.server.wm.AppWarnings.this.writeConfigToFile(andSet);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mPackageFlags"})
        void schedule() {
            if (this.mPendingPackageFlags.getAndSet(new android.util.ArrayMap<>(com.android.server.wm.AppWarnings.this.mPackageFlags)) == null) {
                com.android.server.IoThread.getHandler().postDelayed(this, 10000L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeConfigToFile(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap) {
        java.io.FileOutputStream fileOutputStream = null;
        try {
            java.io.FileOutputStream startWrite = this.mConfigFile.startWrite();
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                resolveSerializer.startTag((java.lang.String) null, "packages");
                for (int i = 0; i < arrayMap.size(); i++) {
                    java.lang.String keyAt = arrayMap.keyAt(i);
                    int intValue = arrayMap.valueAt(i).intValue();
                    if (intValue != 0) {
                        resolveSerializer.startTag((java.lang.String) null, com.android.server.pm.Settings.ATTR_PACKAGE);
                        resolveSerializer.attribute((java.lang.String) null, "name", keyAt);
                        resolveSerializer.attributeInt((java.lang.String) null, "flags", intValue);
                        resolveSerializer.endTag((java.lang.String) null, com.android.server.pm.Settings.ATTR_PACKAGE);
                    }
                }
                resolveSerializer.endTag((java.lang.String) null, "packages");
                resolveSerializer.endDocument();
                this.mConfigFile.finishWrite(startWrite);
            } catch (java.io.IOException e) {
                e = e;
                fileOutputStream = startWrite;
                android.util.Slog.w(TAG, "Error writing package metadata", e);
                if (fileOutputStream != null) {
                    this.mConfigFile.failWrite(fileOutputStream);
                }
            }
        } catch (java.io.IOException e2) {
            e = e2;
        }
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x001f: MOVE (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:71:0x001f */
    private void readConfigFromFileAmsThread() {
        java.io.FileInputStream fileInputStream;
        java.io.FileInputStream fileInputStream2;
        org.xmlpull.v1.XmlPullParserException e;
        java.io.IOException e2;
        com.android.modules.utils.TypedXmlPullParser resolvePullParser;
        int eventType;
        java.lang.String attributeValue;
        java.io.FileInputStream fileInputStream3 = null;
        try {
            try {
                try {
                    fileInputStream2 = this.mConfigFile.openRead();
                } catch (java.io.IOException e3) {
                    fileInputStream2 = null;
                    e2 = e3;
                } catch (org.xmlpull.v1.XmlPullParserException e4) {
                    fileInputStream2 = null;
                    e = e4;
                } catch (java.lang.Throwable th) {
                    th = th;
                    if (fileInputStream3 != null) {
                        try {
                            fileInputStream3.close();
                        } catch (java.io.IOException e5) {
                        }
                    }
                    throw th;
                }
                try {
                    resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream2);
                    eventType = resolvePullParser.getEventType();
                    while (eventType != 2 && eventType != 1) {
                        eventType = resolvePullParser.next();
                    }
                } catch (java.io.IOException e6) {
                    e2 = e6;
                    if (fileInputStream2 != null) {
                        android.util.Slog.w(TAG, "Error reading package metadata", e2);
                    }
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    return;
                } catch (org.xmlpull.v1.XmlPullParserException e7) {
                    e = e7;
                    android.util.Slog.w(TAG, "Error reading package metadata", e);
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    return;
                }
                if (eventType == 1) {
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                            return;
                        } catch (java.io.IOException e8) {
                            return;
                        }
                    }
                    return;
                }
                if ("packages".equals(resolvePullParser.getName())) {
                    int next = resolvePullParser.next();
                    do {
                        if (next == 2) {
                            java.lang.String name = resolvePullParser.getName();
                            if (resolvePullParser.getDepth() == 2 && com.android.server.pm.Settings.ATTR_PACKAGE.equals(name) && (attributeValue = resolvePullParser.getAttributeValue((java.lang.String) null, "name")) != null) {
                                this.mPackageFlags.put(attributeValue, java.lang.Integer.valueOf(resolvePullParser.getAttributeInt((java.lang.String) null, "flags", 0)));
                            }
                        }
                        next = resolvePullParser.next();
                    } while (next != 1);
                }
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
            } catch (java.io.IOException e9) {
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            fileInputStream3 = fileInputStream;
        }
    }
}
