package com.android.server.wm;

/* loaded from: classes3.dex */
public final class CompatModePackages {
    private static final int COMPAT_FLAG_DONT_ASK = 1;
    private static final int COMPAT_FLAG_ENABLED = 2;
    public static final long DOWNSCALED = 168419799;
    public static final long DOWNSCALED_INVERSE = 273564678;
    public static final long DOWNSCALE_30 = 189970040;
    public static final long DOWNSCALE_35 = 189969749;
    public static final long DOWNSCALE_40 = 189970038;
    public static final long DOWNSCALE_45 = 189969782;
    public static final long DOWNSCALE_50 = 176926741;
    public static final long DOWNSCALE_55 = 189970036;
    public static final long DOWNSCALE_60 = 176926771;
    public static final long DOWNSCALE_65 = 189969744;
    public static final long DOWNSCALE_70 = 176926829;
    public static final long DOWNSCALE_75 = 189969779;
    public static final long DOWNSCALE_80 = 176926753;
    public static final long DOWNSCALE_85 = 189969734;
    public static final long DOWNSCALE_90 = 182811243;
    private static final long DO_NOT_DOWNSCALE_TO_1080P_ON_TV = 157629738;
    private static final int MSG_WRITE = 300;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private final android.util.AtomicFile mFile;
    private final com.android.server.wm.CompatModePackages.CompatHandler mHandler;
    private final com.android.server.wm.ActivityTaskManagerService mService;
    private final java.util.HashMap<java.lang.String, java.lang.Integer> mPackages = new java.util.HashMap<>();
    private final android.util.SparseBooleanArray mLegacyScreenCompatPackages = new android.util.SparseBooleanArray();
    private final android.util.SparseArray<com.android.server.wm.CompatScaleProvider> mProviders = new android.util.SparseArray<>();

    private final class CompatHandler extends android.os.Handler {
        public CompatHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 300:
                    com.android.server.wm.CompatModePackages.this.saveCompatModes();
                    break;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00d9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r10v2, types: [android.os.Looper] */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r10v9, types: [java.io.FileInputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CompatModePackages(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, java.io.File file, android.os.Handler handler) {
        java.lang.Throwable th;
        java.io.FileInputStream fileInputStream;
        org.xmlpull.v1.XmlPullParserException e;
        java.io.IOException e2;
        com.android.modules.utils.TypedXmlPullParser resolvePullParser;
        int eventType;
        java.lang.String attributeValue;
        this.mService = activityTaskManagerService;
        this.mFile = new android.util.AtomicFile(new java.io.File(file, "packages-compat.xml"), "compat-mode");
        ?? looper = handler.getLooper();
        this.mHandler = new com.android.server.wm.CompatModePackages.CompatHandler(looper);
        try {
            try {
                try {
                    fileInputStream = this.mFile.openRead();
                } catch (java.io.IOException e3) {
                    fileInputStream = null;
                    e2 = e3;
                } catch (org.xmlpull.v1.XmlPullParserException e4) {
                    fileInputStream = null;
                    e = e4;
                } catch (java.lang.Throwable th2) {
                    looper = 0;
                    th = th2;
                    if (looper != 0) {
                    }
                    throw th;
                }
                try {
                    resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                    eventType = resolvePullParser.getEventType();
                    while (eventType != 2 && eventType != 1) {
                        eventType = resolvePullParser.next();
                    }
                } catch (java.io.IOException e5) {
                    e2 = e5;
                    if (fileInputStream != null) {
                        android.util.Slog.w(TAG, "Error reading compat-packages", e2);
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    return;
                } catch (org.xmlpull.v1.XmlPullParserException e6) {
                    e = e6;
                    android.util.Slog.w(TAG, "Error reading compat-packages", e);
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    return;
                }
                if (eventType == 1) {
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                            return;
                        } catch (java.io.IOException e7) {
                            return;
                        }
                    }
                    return;
                }
                if ("compat-packages".equals(resolvePullParser.getName())) {
                    int next = resolvePullParser.next();
                    do {
                        if (next == 2) {
                            java.lang.String name = resolvePullParser.getName();
                            if (resolvePullParser.getDepth() == 2 && "pkg".equals(name) && (attributeValue = resolvePullParser.getAttributeValue((java.lang.String) null, "name")) != null) {
                                this.mPackages.put(attributeValue, java.lang.Integer.valueOf(resolvePullParser.getAttributeInt((java.lang.String) null, com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration.MODE_KEY, 0)));
                            }
                        }
                        next = resolvePullParser.next();
                    } while (next != 1);
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (java.lang.Throwable th3) {
                th = th3;
                if (looper != 0) {
                    try {
                        looper.close();
                    } catch (java.io.IOException e8) {
                    }
                }
                throw th;
            }
        } catch (java.io.IOException e9) {
        }
    }

    public java.util.HashMap<java.lang.String, java.lang.Integer> getPackages() {
        return this.mPackages;
    }

    private int getPackageFlags(java.lang.String str) {
        java.lang.Integer num = this.mPackages.get(str);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public void handlePackageDataClearedLocked(java.lang.String str) {
        removePackage(str);
    }

    public void handlePackageUninstalledLocked(java.lang.String str) {
        removePackage(str);
    }

    private void removePackage(java.lang.String str) {
        if (this.mPackages.containsKey(str)) {
            this.mPackages.remove(str);
            scheduleWrite();
        }
        this.mLegacyScreenCompatPackages.delete(str.hashCode());
    }

    public void handlePackageAddedLocked(java.lang.String str, boolean z) {
        android.content.pm.ApplicationInfo applicationInfo;
        boolean z2 = false;
        try {
            applicationInfo = android.app.AppGlobals.getPackageManager().getApplicationInfo(str, 0L, 0);
        } catch (android.os.RemoteException e) {
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            return;
        }
        android.content.res.CompatibilityInfo compatibilityInfoForPackageLocked = compatibilityInfoForPackageLocked(applicationInfo);
        if (!compatibilityInfoForPackageLocked.alwaysSupportsScreen() && !compatibilityInfoForPackageLocked.neverSupportsScreen()) {
            z2 = true;
        }
        if (z && !z2 && this.mPackages.containsKey(str)) {
            this.mPackages.remove(str);
            scheduleWrite();
        }
    }

    private void scheduleWrite() {
        this.mHandler.removeMessages(300);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(300), com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
    }

    boolean useLegacyScreenCompatMode(java.lang.String str) {
        if (this.mLegacyScreenCompatPackages.size() == 0) {
            return false;
        }
        return this.mLegacyScreenCompatPackages.get(str.hashCode());
    }

    public android.content.res.CompatibilityInfo compatibilityInfoForPackageLocked(android.content.pm.ApplicationInfo applicationInfo) {
        float compatScale;
        boolean packageCompatModeEnabledLocked = getPackageCompatModeEnabledLocked(applicationInfo);
        android.content.res.CompatibilityInfo.CompatScale compatScaleFromProvider = getCompatScaleFromProvider(applicationInfo.packageName, applicationInfo.uid);
        if (compatScaleFromProvider != null) {
            compatScale = compatScaleFromProvider.mScaleFactor;
        } else {
            compatScale = getCompatScale(applicationInfo.packageName, applicationInfo.uid, false);
        }
        float f = compatScaleFromProvider != null ? compatScaleFromProvider.mDensityScaleFactor : compatScale;
        android.content.res.Configuration globalConfiguration = this.mService.getGlobalConfiguration();
        android.content.res.CompatibilityInfo compatibilityInfo = new android.content.res.CompatibilityInfo(applicationInfo, globalConfiguration.screenLayout, globalConfiguration.smallestScreenWidthDp, packageCompatModeEnabledLocked, compatScale, f);
        if (applicationInfo.flags != 0 && applicationInfo.sourceDir != null) {
            if (!compatibilityInfo.supportsScreen() && !com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(applicationInfo.packageName)) {
                android.util.Slog.i(TAG, "Use legacy screen compat mode: " + applicationInfo.packageName);
                this.mLegacyScreenCompatPackages.put(applicationInfo.packageName.hashCode(), true);
            } else if (this.mLegacyScreenCompatPackages.size() > 0) {
                this.mLegacyScreenCompatPackages.delete(applicationInfo.packageName.hashCode());
            }
        }
        return compatibilityInfo;
    }

    float getCompatScale(java.lang.String str, int i) {
        return getCompatScale(str, i, true);
    }

    private android.content.res.CompatibilityInfo.CompatScale getCompatScaleFromProvider(java.lang.String str, int i) {
        for (int i2 = 0; i2 < this.mProviders.size(); i2++) {
            android.content.res.CompatibilityInfo.CompatScale compatScale = this.mProviders.valueAt(i2).getCompatScale(str, i);
            if (compatScale != null) {
                return compatScale;
            }
        }
        return null;
    }

    private float getCompatScale(java.lang.String str, int i, boolean z) {
        android.content.res.CompatibilityInfo.CompatScale compatScaleFromProvider;
        if (z && (compatScaleFromProvider = getCompatScaleFromProvider(str, i)) != null) {
            return compatScaleFromProvider.mScaleFactor;
        }
        android.os.UserHandle userHandleForUid = android.os.UserHandle.getUserHandleForUid(i);
        boolean isChangeEnabled = android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALED, str, userHandleForUid);
        boolean isChangeEnabled2 = android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALED_INVERSE, str, userHandleForUid);
        if (isChangeEnabled || isChangeEnabled2) {
            float scalingFactor = getScalingFactor(str, userHandleForUid);
            if (scalingFactor != 1.0f) {
                return isChangeEnabled2 ? scalingFactor : 1.0f / scalingFactor;
            }
        }
        if (this.mService.mHasLeanbackFeature) {
            android.content.res.Configuration globalConfiguration = this.mService.getGlobalConfiguration();
            int i2 = (int) ((globalConfiguration.smallestScreenWidthDp * (globalConfiguration.densityDpi / 160.0f)) + 0.5f);
            if (i2 > 1080 && !android.app.compat.CompatChanges.isChangeEnabled(DO_NOT_DOWNSCALE_TO_1080P_ON_TV, str, userHandleForUid)) {
                return i2 / 1080.0f;
            }
        }
        return 1.0f;
    }

    void registerCompatScaleProvider(int i, @android.annotation.NonNull com.android.server.wm.CompatScaleProvider compatScaleProvider) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mProviders.contains(i)) {
                    throw new java.lang.IllegalArgumentException("Duplicate id provided: " + i);
                }
                if (compatScaleProvider == null) {
                    throw new java.lang.IllegalArgumentException("The passed CompatScaleProvider can not be null");
                }
                if (!com.android.server.wm.CompatScaleProvider.isValidOrderId(i)) {
                    throw new java.lang.IllegalArgumentException("Provided id " + i + " is not in range of valid ids for system services [0,2]");
                }
                this.mProviders.put(i, compatScaleProvider);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void unregisterCompatScaleProvider(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!this.mProviders.contains(i)) {
                    throw new java.lang.IllegalArgumentException("CompatScaleProvider with id (" + i + ") is not registered");
                }
                this.mProviders.remove(i);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    private static float getScalingFactor(java.lang.String str, android.os.UserHandle userHandle) {
        if (android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALE_90, str, userHandle)) {
            return 0.9f;
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALE_85, str, userHandle)) {
            return 0.85f;
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALE_80, str, userHandle)) {
            return 0.8f;
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALE_75, str, userHandle)) {
            return 0.75f;
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALE_70, str, userHandle)) {
            return 0.7f;
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALE_65, str, userHandle)) {
            return 0.65f;
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALE_60, str, userHandle)) {
            return 0.6f;
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALE_55, str, userHandle)) {
            return 0.55f;
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALE_50, str, userHandle)) {
            return 0.5f;
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALE_45, str, userHandle)) {
            return 0.45f;
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALE_40, str, userHandle)) {
            return 0.4f;
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALE_35, str, userHandle)) {
            return 0.35f;
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(DOWNSCALE_30, str, userHandle)) {
            return 0.3f;
        }
        return 1.0f;
    }

    public int computeCompatModeLocked(android.content.pm.ApplicationInfo applicationInfo) {
        android.content.res.CompatibilityInfo compatibilityInfoForPackageLocked = compatibilityInfoForPackageLocked(applicationInfo);
        if (compatibilityInfoForPackageLocked.alwaysSupportsScreen()) {
            return -2;
        }
        if (compatibilityInfoForPackageLocked.neverSupportsScreen()) {
            return -1;
        }
        return getPackageCompatModeEnabledLocked(applicationInfo) ? 1 : 0;
    }

    public boolean getPackageAskCompatModeLocked(java.lang.String str) {
        return (getPackageFlags(str) & 1) == 0;
    }

    public void setPackageAskCompatModeLocked(java.lang.String str, boolean z) {
        setPackageFlagLocked(str, 1, z);
    }

    private boolean getPackageCompatModeEnabledLocked(android.content.pm.ApplicationInfo applicationInfo) {
        return (getPackageFlags(applicationInfo.packageName) & 2) != 0;
    }

    private void setPackageFlagLocked(java.lang.String str, int i, boolean z) {
        int packageFlags = getPackageFlags(str);
        int i2 = z ? (~i) & packageFlags : i | packageFlags;
        if (packageFlags != i2) {
            if (i2 != 0) {
                this.mPackages.put(str, java.lang.Integer.valueOf(i2));
            } else {
                this.mPackages.remove(str);
            }
            scheduleWrite();
        }
    }

    public int getPackageScreenCompatModeLocked(java.lang.String str) {
        android.content.pm.ApplicationInfo applicationInfo;
        try {
            applicationInfo = android.app.AppGlobals.getPackageManager().getApplicationInfo(str, 0L, 0);
        } catch (android.os.RemoteException e) {
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            return -3;
        }
        return computeCompatModeLocked(applicationInfo);
    }

    public void setPackageScreenCompatModeLocked(java.lang.String str, int i) {
        android.content.pm.ApplicationInfo applicationInfo;
        try {
            applicationInfo = android.app.AppGlobals.getPackageManager().getApplicationInfo(str, 0L, 0);
        } catch (android.os.RemoteException e) {
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            android.util.Slog.w(TAG, "setPackageScreenCompatMode failed: unknown package " + str);
            return;
        }
        setPackageScreenCompatModeLocked(applicationInfo, i);
    }

    void setPackageScreenCompatModeLocked(android.content.pm.ApplicationInfo applicationInfo, int i) {
        boolean z;
        int i2;
        final java.lang.String str = applicationInfo.packageName;
        int packageFlags = getPackageFlags(str);
        int i3 = 0;
        switch (i) {
            case 0:
                z = false;
                break;
            case 1:
                z = true;
                break;
            case 2:
                if ((packageFlags & 2) == 0) {
                    z = true;
                    break;
                } else {
                    z = false;
                    break;
                }
            default:
                android.util.Slog.w(TAG, "Unknown screen compat mode req #" + i + "; ignoring");
                return;
        }
        if (z) {
            i2 = packageFlags | 2;
        } else {
            i2 = packageFlags & (-3);
        }
        android.content.res.CompatibilityInfo compatibilityInfoForPackageLocked = compatibilityInfoForPackageLocked(applicationInfo);
        if (compatibilityInfoForPackageLocked.alwaysSupportsScreen()) {
            android.util.Slog.w(TAG, "Ignoring compat mode change of " + str + "; compatibility never needed");
            i2 = 0;
        }
        if (compatibilityInfoForPackageLocked.neverSupportsScreen()) {
            android.util.Slog.w(TAG, "Ignoring compat mode change of " + str + "; compatibility always needed");
        } else {
            i3 = i2;
        }
        if (i3 != packageFlags) {
            if (i3 != 0) {
                this.mPackages.put(str, java.lang.Integer.valueOf(i3));
            } else {
                this.mPackages.remove(str);
            }
            android.content.res.CompatibilityInfo compatibilityInfoForPackageLocked2 = compatibilityInfoForPackageLocked(applicationInfo);
            scheduleWrite();
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            this.mService.mRootWindowContainer.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.CompatModePackages$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.CompatModePackages.lambda$setPackageScreenCompatModeLocked$0(str, arrayList, (com.android.server.wm.WindowState) obj);
                }
            }, true);
            android.util.SparseArray<com.android.server.wm.WindowProcessController> pidMap = this.mService.mProcessMap.getPidMap();
            for (int size = pidMap.size() - 1; size >= 0; size--) {
                com.android.server.wm.WindowProcessController valueAt = pidMap.valueAt(size);
                if (valueAt.containsPackage(str) && !arrayList.contains(valueAt)) {
                    try {
                        if (valueAt.hasThread()) {
                            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, -74949168947384056L, 0, null, java.lang.String.valueOf(valueAt.mName), java.lang.String.valueOf(compatibilityInfoForPackageLocked2));
                            valueAt.getThread().updatePackageCompatibilityInfo(str, compatibilityInfoForPackageLocked2);
                        }
                    } catch (java.lang.Exception e) {
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setPackageScreenCompatModeLocked$0(java.lang.String str, java.util.ArrayList arrayList, com.android.server.wm.WindowState windowState) {
        com.android.server.wm.ActivityRecord activityRecord = windowState.mActivityRecord;
        if (activityRecord != null) {
            if (activityRecord.packageName.equals(str) && !arrayList.contains(activityRecord.app)) {
                activityRecord.restartProcessIfVisible();
                arrayList.add(activityRecord.app);
                return;
            }
            return;
        }
        if (windowState.getProcess().mInfo.packageName.equals(str)) {
            windowState.updateGlobalScale();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveCompatModes() {
        java.util.HashMap hashMap;
        java.io.FileOutputStream fileOutputStream;
        java.io.IOException e;
        android.content.pm.ApplicationInfo applicationInfo;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                hashMap = new java.util.HashMap(this.mPackages);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        try {
            fileOutputStream = this.mFile.startWrite();
        } catch (java.io.IOException e2) {
            fileOutputStream = null;
            e = e2;
        }
        try {
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(fileOutputStream);
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            resolveSerializer.startTag((java.lang.String) null, "compat-packages");
            android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
            for (java.util.Map.Entry entry : hashMap.entrySet()) {
                java.lang.String str = (java.lang.String) entry.getKey();
                int intValue = ((java.lang.Integer) entry.getValue()).intValue();
                if (intValue != 0) {
                    try {
                        applicationInfo = packageManager.getApplicationInfo(str, 0L, 0);
                    } catch (android.os.RemoteException e3) {
                        applicationInfo = null;
                    }
                    if (applicationInfo != null) {
                        android.content.res.CompatibilityInfo compatibilityInfoForPackageLocked = compatibilityInfoForPackageLocked(applicationInfo);
                        if (!compatibilityInfoForPackageLocked.alwaysSupportsScreen() && !compatibilityInfoForPackageLocked.neverSupportsScreen()) {
                            resolveSerializer.startTag((java.lang.String) null, "pkg");
                            resolveSerializer.attribute((java.lang.String) null, "name", str);
                            resolveSerializer.attributeInt((java.lang.String) null, com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration.MODE_KEY, intValue);
                            resolveSerializer.endTag((java.lang.String) null, "pkg");
                        }
                    }
                }
            }
            resolveSerializer.endTag((java.lang.String) null, "compat-packages");
            resolveSerializer.endDocument();
            this.mFile.finishWrite(fileOutputStream);
        } catch (java.io.IOException e4) {
            e = e4;
            android.util.Slog.w(TAG, "Error writing compat packages", e);
            if (fileOutputStream != null) {
                this.mFile.failWrite(fileOutputStream);
            }
        }
    }
}
