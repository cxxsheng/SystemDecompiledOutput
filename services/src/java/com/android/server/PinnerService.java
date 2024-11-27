package com.android.server;

/* loaded from: classes.dex */
public final class PinnerService extends com.android.server.SystemService {
    public static final java.lang.String ANON_REGION_STAT_NAME = "[anon]";
    private static final boolean DEBUG = false;
    private static final java.lang.String DEVICE_CONFIG_KEY_ANON_SIZE = "pin_shared_anon_size";
    private static final java.lang.String DEVICE_CONFIG_NAMESPACE_ANON_SIZE = "runtime_native";
    private static final int KEY_ASSISTANT = 2;
    private static final int KEY_CAMERA = 0;
    private static final int KEY_HOME = 1;
    private static final int MATCH_FLAGS = 851968;
    private static final long MAX_ANON_SIZE = 2147483648L;
    private static final int MAX_ASSISTANT_PIN_SIZE = 62914560;
    private static final int MAX_CAMERA_PIN_SIZE = 83886080;
    private static final int MAX_HOME_PIN_SIZE = 6291456;
    private static final java.lang.String PIN_META_FILENAME = "pinlist.meta";
    private static final java.lang.String TAG = "PinnerService";
    private final android.app.IActivityManager mAm;
    private final android.app.ActivityManagerInternal mAmInternal;
    private final com.android.server.wm.ActivityTaskManagerInternal mAtmInternal;
    private com.android.server.PinnerService.BinderService mBinderService;
    private final android.content.BroadcastReceiver mBroadcastReceiver;
    private final boolean mConfiguredToPinAssistant;
    private final boolean mConfiguredToPinCamera;
    private final boolean mConfiguredToPinHome;
    private final int mConfiguredWebviewPinBytes;
    private final android.content.Context mContext;
    private long mCurrentlyPinnedAnonSize;
    private final android.provider.DeviceConfig.OnPropertiesChangedListener mDeviceConfigAnonSizeListener;
    private final android.provider.DeviceConfigInterface mDeviceConfigInterface;
    private final com.android.server.PinnerService.Injector mInjector;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.ArrayMap<java.lang.Integer, java.lang.Integer> mPendingRepin;
    private long mPinAnonAddress;
    private long mPinAnonSize;

    @com.android.internal.annotations.GuardedBy({"this"})
    private android.util.ArraySet<java.lang.Integer> mPinKeys;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.ArrayMap<java.lang.Integer, com.android.server.PinnerService.PinnedApp> mPinnedApps;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.ArrayMap<java.lang.String, com.android.server.PinnerService.PinnedFile> mPinnedFiles;
    private com.android.server.PinnerService.PinnerHandler mPinnerHandler;
    private android.app.SearchManager mSearchManager;
    private final android.os.UserManager mUserManager;
    private static final int PAGE_SIZE = (int) android.system.Os.sysconf(android.system.OsConstants._SC_PAGESIZE);
    private static boolean PROP_PIN_PINLIST = android.os.SystemProperties.getBoolean("pinner.use_pinlist", true);
    private static final long DEFAULT_ANON_SIZE = android.os.SystemProperties.getLong("pinner.pin_shared_anon_size", 0);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AppKey {
    }

    static final class PinRange {
        int length;
        int start;

        PinRange() {
        }
    }

    private static abstract class PinRangeSource {
        private PinRangeSource() {
        }

        abstract boolean read(com.android.server.PinnerService.PinRange pinRange);
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        protected android.provider.DeviceConfigInterface getDeviceConfigInterface() {
            return android.provider.DeviceConfigInterface.REAL;
        }

        protected void publishBinderService(com.android.server.PinnerService pinnerService, android.os.Binder binder) {
            pinnerService.publishBinderService("pinner", binder);
        }

        protected com.android.server.PinnerService.PinnedFile pinFileInternal(java.lang.String str, int i, boolean z) {
            return com.android.server.PinnerService.pinFileInternal(str, i, z);
        }
    }

    public PinnerService(android.content.Context context) {
        this(context, new com.android.server.PinnerService.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    PinnerService(android.content.Context context, com.android.server.PinnerService.Injector injector) {
        super(context);
        this.mPinnedFiles = new android.util.ArrayMap<>();
        this.mPinnedApps = new android.util.ArrayMap<>();
        this.mPendingRepin = new android.util.ArrayMap<>();
        this.mPinnerHandler = null;
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.PinnerService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if ("android.intent.action.PACKAGE_REPLACED".equals(intent.getAction())) {
                    java.lang.String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
                    arraySet.add(schemeSpecificPart);
                    com.android.server.PinnerService.this.update(arraySet, true);
                }
            }
        };
        this.mDeviceConfigAnonSizeListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.PinnerService.2
            public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                if (com.android.server.PinnerService.DEVICE_CONFIG_NAMESPACE_ANON_SIZE.equals(properties.getNamespace()) && properties.getKeyset().contains(com.android.server.PinnerService.DEVICE_CONFIG_KEY_ANON_SIZE)) {
                    com.android.server.PinnerService.this.refreshPinAnonConfig();
                }
            }
        };
        this.mContext = context;
        this.mInjector = injector;
        this.mDeviceConfigInterface = this.mInjector.getDeviceConfigInterface();
        this.mConfiguredToPinCamera = context.getResources().getBoolean(android.R.bool.config_pdp_reject_enable_retry);
        this.mConfiguredToPinHome = context.getResources().getBoolean(android.R.bool.config_performantAuthDefault);
        this.mConfiguredToPinAssistant = context.getResources().getBoolean(android.R.bool.config_pauseWallpaperRenderWhenStateChangeEnabled);
        this.mConfiguredWebviewPinBytes = context.getResources().getInteger(android.R.integer.config_pauseRotationWhenUnfolding_maxHingeAngle);
        this.mPinKeys = createPinKeys();
        this.mPinnerHandler = new com.android.server.PinnerService.PinnerHandler(com.android.internal.os.BackgroundThread.get().getLooper());
        this.mAtmInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
        this.mAmInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mAm = android.app.ActivityManager.getService();
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
        registerUidListener();
        registerUserSetupCompleteListener();
        this.mDeviceConfigInterface.addOnPropertiesChangedListener(DEVICE_CONFIG_NAMESPACE_ANON_SIZE, new android.os.HandlerExecutor(this.mPinnerHandler), this.mDeviceConfigAnonSizeListener);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        this.mBinderService = new com.android.server.PinnerService.BinderService();
        this.mInjector.publishBinderService(this, this.mBinderService);
        publishLocalService(com.android.server.PinnerService.class, this);
        this.mPinnerHandler.obtainMessage(4001).sendToTarget();
        sendPinAppsMessage(0);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            this.mSearchManager = (android.app.SearchManager) this.mContext.getSystemService("search");
            sendPinAppsMessage(0);
        }
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        int userIdentifier = targetUser2.getUserIdentifier();
        if (!this.mUserManager.isManagedProfile(userIdentifier)) {
            sendPinAppsMessage(userIdentifier);
        }
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        if (!this.mUserManager.isManagedProfile(userIdentifier)) {
            sendPinAppsMessage(userIdentifier);
        }
    }

    public void update(android.util.ArraySet<java.lang.String> arraySet, boolean z) {
        android.util.ArraySet<java.lang.Integer> pinKeys = getPinKeys();
        int currentUser = android.app.ActivityManager.getCurrentUser();
        for (int size = pinKeys.size() - 1; size >= 0; size--) {
            int intValue = pinKeys.valueAt(size).intValue();
            android.content.pm.ApplicationInfo infoForKey = getInfoForKey(intValue, currentUser);
            if (infoForKey != null && arraySet.contains(infoForKey.packageName)) {
                android.util.Slog.i(TAG, "Updating pinned files for " + infoForKey.packageName + " force=" + z);
                sendPinAppMessage(intValue, currentUser, z);
            }
        }
    }

    public java.util.List<com.android.server.PinnerService.PinnedFileStats> dumpDataForStatsd() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this) {
            try {
                java.util.Iterator<com.android.server.PinnerService.PinnedFile> it = this.mPinnedFiles.values().iterator();
                while (it.hasNext()) {
                    arrayList.add(new com.android.server.PinnerService.PinnedFileStats(1000, it.next()));
                }
                java.util.Iterator<java.lang.Integer> it2 = this.mPinnedApps.keySet().iterator();
                while (it2.hasNext()) {
                    int intValue = it2.next().intValue();
                    com.android.server.PinnerService.PinnedApp pinnedApp = this.mPinnedApps.get(java.lang.Integer.valueOf(intValue));
                    java.util.Iterator<com.android.server.PinnerService.PinnedFile> it3 = this.mPinnedApps.get(java.lang.Integer.valueOf(intValue)).mFiles.iterator();
                    while (it3.hasNext()) {
                        arrayList.add(new com.android.server.PinnerService.PinnedFileStats(pinnedApp.uid, it3.next()));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public static class PinnedFileStats {
        public final java.lang.String filename;
        public final int sizeKb;
        public final int uid;

        protected PinnedFileStats(int i, com.android.server.PinnerService.PinnedFile pinnedFile) {
            this.uid = i;
            this.filename = pinnedFile.fileName.substring(pinnedFile.fileName.lastIndexOf(47) + 1);
            this.sizeKb = pinnedFile.bytesPinned / 1024;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePinOnStart() {
        for (java.lang.String str : this.mContext.getResources().getStringArray(android.R.array.config_defaultNotificationVibePattern)) {
            com.android.server.PinnerService.PinnedFile pinFileInternal = this.mInjector.pinFileInternal(str, Integer.MAX_VALUE, false);
            if (pinFileInternal == null) {
                android.util.Slog.e(TAG, "Failed to pin file = " + str);
            } else {
                synchronized (this) {
                    this.mPinnedFiles.put(pinFileInternal.fileName, pinFileInternal);
                }
                pinFileInternal.groupName = "system";
                pinOptimizedDexDependencies(pinFileInternal, Integer.MAX_VALUE, null);
            }
        }
        refreshPinAnonConfig();
    }

    private void registerUserSetupCompleteListener() {
        final android.net.Uri uriFor = android.provider.Settings.Secure.getUriFor("user_setup_complete");
        this.mContext.getContentResolver().registerContentObserver(uriFor, false, new android.database.ContentObserver(null) { // from class: com.android.server.PinnerService.3
            @Override // android.database.ContentObserver
            public void onChange(boolean z, android.net.Uri uri) {
                if (uriFor.equals(uri) && com.android.server.PinnerService.this.mConfiguredToPinHome) {
                    com.android.server.PinnerService.this.sendPinAppMessage(1, android.app.ActivityManager.getCurrentUser(), true);
                }
            }
        }, -1);
    }

    /* renamed from: com.android.server.PinnerService$4, reason: invalid class name */
    class AnonymousClass4 extends android.app.UidObserver {
        AnonymousClass4() {
        }

        public void onUidGone(int i, boolean z) {
            com.android.server.PinnerService.this.mPinnerHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.PinnerService$4$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((com.android.server.PinnerService) obj).handleUidGone(((java.lang.Integer) obj2).intValue());
                }
            }, com.android.server.PinnerService.this, java.lang.Integer.valueOf(i)));
        }

        public void onUidActive(int i) {
            com.android.server.PinnerService.this.mPinnerHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.PinnerService$4$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((com.android.server.PinnerService) obj).handleUidActive(((java.lang.Integer) obj2).intValue());
                }
            }, com.android.server.PinnerService.this, java.lang.Integer.valueOf(i)));
        }
    }

    private void registerUidListener() {
        try {
            this.mAm.registerUidObserver(new com.android.server.PinnerService.AnonymousClass4(), 10, 0, (java.lang.String) null);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to register uid observer", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUidGone(int i) {
        updateActiveState(i, false);
        synchronized (this) {
            try {
                int intValue = this.mPendingRepin.getOrDefault(java.lang.Integer.valueOf(i), -1).intValue();
                if (intValue == -1) {
                    return;
                }
                this.mPendingRepin.remove(java.lang.Integer.valueOf(i));
                pinApp(intValue, android.app.ActivityManager.getCurrentUser(), false);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUidActive(int i) {
        updateActiveState(i, true);
    }

    private void updateActiveState(int i, boolean z) {
        synchronized (this) {
            try {
                for (int size = this.mPinnedApps.size() - 1; size >= 0; size--) {
                    com.android.server.PinnerService.PinnedApp valueAt = this.mPinnedApps.valueAt(size);
                    if (valueAt.uid == i) {
                        valueAt.active = z;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unpinApps() {
        android.util.ArraySet<java.lang.Integer> pinKeys = getPinKeys();
        for (int size = pinKeys.size() - 1; size >= 0; size--) {
            unpinApp(pinKeys.valueAt(size).intValue());
        }
    }

    private void unpinApp(int i) {
        synchronized (this) {
            try {
                com.android.server.PinnerService.PinnedApp pinnedApp = this.mPinnedApps.get(java.lang.Integer.valueOf(i));
                if (pinnedApp == null) {
                    return;
                }
                this.mPinnedApps.remove(java.lang.Integer.valueOf(i));
                java.util.Iterator it = new java.util.ArrayList(pinnedApp.mFiles).iterator();
                while (it.hasNext()) {
                    unpinFile(((com.android.server.PinnerService.PinnedFile) it.next()).fileName);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isResolverActivity(android.content.pm.ActivityInfo activityInfo) {
        return com.android.internal.app.ResolverActivity.class.getName().equals(activityInfo.name);
    }

    public int getWebviewPinQuota() {
        if (!com.android.server.flags.Flags.pinWebview()) {
            return 0;
        }
        int i = this.mConfiguredWebviewPinBytes;
        int i2 = android.os.SystemProperties.getInt("pinner.pin_webview_size", -1);
        if (i2 != -1) {
            return i2;
        }
        return i;
    }

    private android.content.pm.ApplicationInfo getCameraInfo(int i) {
        android.content.pm.ApplicationInfo applicationInfoForIntent = getApplicationInfoForIntent(new android.content.Intent("android.media.action.STILL_IMAGE_CAMERA"), i, false);
        if (applicationInfoForIntent == null) {
            applicationInfoForIntent = getApplicationInfoForIntent(new android.content.Intent("android.media.action.STILL_IMAGE_CAMERA_SECURE"), i, false);
        }
        if (applicationInfoForIntent == null) {
            return getApplicationInfoForIntent(new android.content.Intent("android.media.action.STILL_IMAGE_CAMERA"), i, true);
        }
        return applicationInfoForIntent;
    }

    private android.content.pm.ApplicationInfo getHomeInfo(int i) {
        return getApplicationInfoForIntent(this.mAtmInternal.getHomeIntent(), i, false);
    }

    private android.content.pm.ApplicationInfo getAssistantInfo(int i) {
        if (this.mSearchManager != null) {
            return getApplicationInfoForIntent(this.mSearchManager.getAssistIntent(false), i, true);
        }
        return null;
    }

    private android.content.pm.ApplicationInfo getApplicationInfoForIntent(android.content.Intent intent, int i, boolean z) {
        android.content.pm.ResolveInfo resolveActivityAsUser;
        if (intent == null || (resolveActivityAsUser = this.mContext.getPackageManager().resolveActivityAsUser(intent, MATCH_FLAGS, i)) == null) {
            return null;
        }
        if (!isResolverActivity(resolveActivityAsUser.activityInfo)) {
            return resolveActivityAsUser.activityInfo.applicationInfo;
        }
        if (!z) {
            return null;
        }
        android.content.pm.ApplicationInfo applicationInfo = null;
        for (android.content.pm.ResolveInfo resolveInfo : this.mContext.getPackageManager().queryIntentActivitiesAsUser(intent, MATCH_FLAGS, i)) {
            if ((resolveInfo.activityInfo.applicationInfo.flags & 1) != 0) {
                if (applicationInfo != null) {
                    return null;
                }
                applicationInfo = resolveInfo.activityInfo.applicationInfo;
            }
        }
        return applicationInfo;
    }

    private void sendPinAppsMessage(int i) {
        this.mPinnerHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.PinnerService$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.PinnerService) obj).pinApps(((java.lang.Integer) obj2).intValue());
            }
        }, this, java.lang.Integer.valueOf(i)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPinAppsWithUpdatedKeysMessage(int i) {
        this.mPinnerHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.PinnerService$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.PinnerService) obj).pinAppsWithUpdatedKeys(((java.lang.Integer) obj2).intValue());
            }
        }, this, java.lang.Integer.valueOf(i)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUnpinAppsMessage() {
        this.mPinnerHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.PinnerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.PinnerService) obj).unpinApps();
            }
        }, this));
    }

    private android.util.ArraySet<java.lang.Integer> createPinKeys() {
        android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
        if (this.mConfiguredToPinCamera && this.mDeviceConfigInterface.getBoolean("runtime_native_boot", "pin_camera", android.os.SystemProperties.getBoolean("pinner.pin_camera", true))) {
            arraySet.add(0);
        }
        if (this.mConfiguredToPinHome) {
            arraySet.add(1);
        }
        if (this.mConfiguredToPinAssistant) {
            arraySet.add(2);
        }
        return arraySet;
    }

    private synchronized android.util.ArraySet<java.lang.Integer> getPinKeys() {
        return this.mPinKeys;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pinApps(int i) {
        pinAppsInternal(i, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pinAppsWithUpdatedKeys(int i) {
        pinAppsInternal(i, true);
    }

    private void pinAppsInternal(int i, boolean z) {
        if (z) {
            android.util.ArraySet<java.lang.Integer> createPinKeys = createPinKeys();
            synchronized (this) {
                try {
                    if (!this.mPinnedApps.isEmpty()) {
                        android.util.Slog.e(TAG, "Attempted to update a list of apps, but apps were already pinned. Skipping.");
                        return;
                    }
                    this.mPinKeys = createPinKeys;
                } finally {
                }
            }
        }
        android.util.ArraySet<java.lang.Integer> pinKeys = getPinKeys();
        for (int size = pinKeys.size() - 1; size >= 0; size--) {
            pinApp(pinKeys.valueAt(size).intValue(), i, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPinAppMessage(int i, int i2, boolean z) {
        this.mPinnerHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: com.android.server.PinnerService$$ExternalSyntheticLambda4
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                ((com.android.server.PinnerService) obj).pinApp(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), ((java.lang.Boolean) obj4).booleanValue());
            }
        }, this, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Boolean.valueOf(z)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pinApp(int i, int i2, boolean z) {
        int uidForKey = getUidForKey(i);
        if (!z && uidForKey != -1) {
            synchronized (this) {
                this.mPendingRepin.put(java.lang.Integer.valueOf(uidForKey), java.lang.Integer.valueOf(i));
            }
            return;
        }
        unpinApp(i);
        android.content.pm.ApplicationInfo infoForKey = getInfoForKey(i, i2);
        if (infoForKey != null) {
            pinApp(i, infoForKey);
        }
    }

    private int getUidForKey(int i) {
        int i2;
        synchronized (this) {
            try {
                com.android.server.PinnerService.PinnedApp pinnedApp = this.mPinnedApps.get(java.lang.Integer.valueOf(i));
                if (pinnedApp != null && pinnedApp.active) {
                    i2 = pinnedApp.uid;
                } else {
                    i2 = -1;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i2;
    }

    @android.annotation.Nullable
    private android.content.pm.ApplicationInfo getInfoForKey(int i, int i2) {
        switch (i) {
            case 0:
                return getCameraInfo(i2);
            case 1:
                return getHomeInfo(i2);
            case 2:
                return getAssistantInfo(i2);
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getNameForKey(int i) {
        switch (i) {
            case 0:
                return "Camera";
            case 1:
                return "Home";
            case 2:
                return "Assistant";
            default:
                return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshPinAnonConfig() {
        long max = java.lang.Math.max(0L, java.lang.Math.min(this.mDeviceConfigInterface.getLong(DEVICE_CONFIG_NAMESPACE_ANON_SIZE, DEVICE_CONFIG_KEY_ANON_SIZE, DEFAULT_ANON_SIZE), MAX_ANON_SIZE));
        if (max != this.mPinAnonSize) {
            this.mPinAnonSize = max;
            pinAnonRegion();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0144  */
    /* JADX WARN: Type inference failed for: r7v14, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void pinAnonRegion() {
        long j;
        long j2;
        if (this.mPinAnonSize == 0) {
            android.util.Slog.d(TAG, "pinAnonRegion: releasing pinned region");
            unpinAnonRegion();
            return;
        }
        long j3 = this.mPinAnonSize;
        if (j3 % PAGE_SIZE != 0) {
            j3 -= j3 % PAGE_SIZE;
            android.util.Slog.e(TAG, "pinAnonRegion: aligning size to " + j3);
        }
        long j4 = this.mPinAnonAddress;
        long j5 = j4;
        if (j4 != 0) {
            if (this.mCurrentlyPinnedAnonSize == j3) {
                android.util.Slog.d(TAG, "pinAnonRegion: already pinned region of size " + j3);
                return;
            }
            android.util.Slog.d(TAG, "pinAnonRegion: resetting pinned region for new size " + j3);
            unpinAnonRegion();
            j5 = "pinAnonRegion: resetting pinned region for new size ";
        }
        try {
            try {
                j2 = android.system.Os.mmap(0L, j3, android.system.OsConstants.PROT_READ | android.system.OsConstants.PROT_WRITE, android.system.OsConstants.MAP_SHARED | android.system.OsConstants.MAP_ANONYMOUS, new java.io.FileDescriptor(), 0L);
            } catch (java.lang.Exception e) {
                e = e;
                j2 = 0;
            } catch (java.lang.Throwable th) {
                th = th;
                j = 0;
                if (j >= 0) {
                }
                throw th;
            }
            try {
                sun.misc.Unsafe unsafe = null;
                for (java.lang.reflect.Field field : sun.misc.Unsafe.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    java.lang.Object obj = field.get(null);
                    if (sun.misc.Unsafe.class.isInstance(obj)) {
                        unsafe = (sun.misc.Unsafe) sun.misc.Unsafe.class.cast(obj);
                    }
                }
                if (unsafe == null) {
                    throw new java.lang.Exception("Couldn't get Unsafe");
                }
                sun.misc.Unsafe.class.getMethod("setMemory", java.lang.Long.TYPE, java.lang.Long.TYPE, java.lang.Byte.TYPE).invoke(unsafe, java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3), (byte) 1);
                android.system.Os.mlock(j2, j3);
                this.mCurrentlyPinnedAnonSize = j3;
                this.mPinAnonAddress = j2;
                android.util.Slog.w(TAG, "pinAnonRegion success, size=" + this.mCurrentlyPinnedAnonSize);
            } catch (java.lang.Exception e2) {
                e = e2;
                android.util.Slog.e(TAG, "Could not pin anon region of size " + j3, e);
                if (j2 >= 0) {
                    safeMunmap(j2, j3);
                }
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            j = j5;
            if (j >= 0) {
                safeMunmap(j, j3);
            }
            throw th;
        }
    }

    private void unpinAnonRegion() {
        if (this.mPinAnonAddress != 0) {
            safeMunmap(this.mPinAnonAddress, this.mCurrentlyPinnedAnonSize);
        }
        this.mPinAnonAddress = 0L;
        this.mCurrentlyPinnedAnonSize = 0L;
    }

    private int getSizeLimitForKey(int i) {
        switch (i) {
            case 0:
                return 83886080;
            case 1:
                return MAX_HOME_PIN_SIZE;
            case 2:
                return MAX_ASSISTANT_PIN_SIZE;
            default:
                return 0;
        }
    }

    private void pinApp(int i, @android.annotation.Nullable android.content.pm.ApplicationInfo applicationInfo) {
        if (applicationInfo == null) {
            return;
        }
        com.android.server.PinnerService.PinnedApp pinnedApp = new com.android.server.PinnerService.PinnedApp(applicationInfo);
        synchronized (this) {
            this.mPinnedApps.put(java.lang.Integer.valueOf(i), pinnedApp);
        }
        int sizeLimitForKey = getSizeLimitForKey(i);
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList();
        arrayList.add(applicationInfo.sourceDir);
        if (applicationInfo.splitSourceDirs != null) {
            for (java.lang.String str : applicationInfo.splitSourceDirs) {
                arrayList.add(str);
            }
        }
        for (java.lang.String str2 : arrayList) {
            if (sizeLimitForKey <= 0) {
                android.util.Slog.w(TAG, "Reached to the pin size limit. Skipping: " + str2);
            } else {
                com.android.server.PinnerService.PinnedFile pinFileInternal = this.mInjector.pinFileInternal(str2, sizeLimitForKey, true);
                if (pinFileInternal == null) {
                    android.util.Slog.e(TAG, "Failed to pin " + str2);
                } else {
                    pinFileInternal.groupName = getNameForKey(i);
                    synchronized (this) {
                        pinnedApp.mFiles.add(pinFileInternal);
                    }
                    sizeLimitForKey -= pinFileInternal.bytesPinned;
                    if (str2.equals(applicationInfo.sourceDir)) {
                        pinOptimizedDexDependencies(pinFileInternal, sizeLimitForKey, applicationInfo);
                    }
                }
            }
        }
    }

    public int pinFile(java.lang.String str, int i, @android.annotation.Nullable android.content.pm.ApplicationInfo applicationInfo, @android.annotation.Nullable java.lang.String str2) {
        com.android.server.PinnerService.PinnedFile pinnedFile;
        synchronized (this) {
            pinnedFile = this.mPinnedFiles.get(str);
        }
        if (pinnedFile != null) {
            if (pinnedFile.bytesPinned == i) {
                return 0;
            }
            unpinFile(str);
        }
        com.android.server.PinnerService.PinnedFile pinFileInternal = this.mInjector.pinFileInternal(str, i, str.endsWith(".apk"));
        if (pinFileInternal == null) {
            android.util.Slog.e(TAG, "Failed to pin file = " + str);
            return 0;
        }
        if (str2 == null) {
            str2 = "";
        }
        pinFileInternal.groupName = str2;
        int i2 = i - 0;
        int i3 = pinFileInternal.bytesPinned + 0;
        synchronized (this) {
            this.mPinnedFiles.put(pinFileInternal.fileName, pinFileInternal);
        }
        if (i2 > 0) {
            pinOptimizedDexDependencies(pinFileInternal, i2, applicationInfo);
        }
        return i3;
    }

    private int pinOptimizedDexDependencies(com.android.server.PinnerService.PinnedFile pinnedFile, int i, @android.annotation.Nullable android.content.pm.ApplicationInfo applicationInfo) {
        java.lang.String str;
        if (pinnedFile == null || !(pinnedFile.fileName.endsWith(".jar") | pinnedFile.fileName.endsWith(".apk"))) {
            return 0;
        }
        java.lang.String[] strArr = null;
        if (applicationInfo == null) {
            str = null;
        } else {
            str = applicationInfo.primaryCpuAbi;
        }
        if (str == null) {
            str = android.os.Build.SUPPORTED_ABIS[0];
        }
        try {
            strArr = dalvik.system.DexFile.getDexFileOutputPaths(pinnedFile.fileName, dalvik.system.VMRuntime.getInstructionSet(str));
        } catch (java.io.IOException e) {
        }
        if (strArr == null) {
            return 0;
        }
        int i2 = 0;
        for (java.lang.String str2 : strArr) {
            unpinFile(str2);
            com.android.server.PinnerService.PinnedFile pinFileInternal = this.mInjector.pinFileInternal(str2, Integer.MAX_VALUE, false);
            if (pinFileInternal == null) {
                android.util.Slog.i(TAG, "Failed to pin ART file = " + str2);
                return i2;
            }
            pinFileInternal.groupName = pinnedFile.groupName;
            pinnedFile.pinnedDeps.add(pinFileInternal);
            i2 += pinFileInternal.bytesPinned;
            synchronized (this) {
                this.mPinnedFiles.put(pinFileInternal.fileName, pinFileInternal);
            }
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.server.PinnerService.PinnedFile pinFileInternal(java.lang.String str, int i, boolean z) {
        java.util.zip.ZipFile zipFile;
        java.io.InputStream inputStream = null;
        if (z) {
            try {
                zipFile = maybeOpenZip(str);
            } catch (java.lang.Throwable th) {
                th = th;
                zipFile = null;
                safeClose(inputStream);
                safeClose(zipFile);
                throw th;
            }
        } else {
            zipFile = null;
        }
        if (zipFile != null) {
            try {
                inputStream = maybeOpenPinMetaInZip(zipFile, str);
            } catch (java.lang.Throwable th2) {
                th = th2;
                safeClose(inputStream);
                safeClose(zipFile);
                throw th;
            }
        }
        boolean z2 = inputStream != null;
        com.android.server.PinnerService.PinnedFile pinFileRanges = pinFileRanges(str, i, z2 ? new com.android.server.PinnerService.PinRangeSourceStream(inputStream) : new com.android.server.PinnerService.PinRangeSourceStatic(0, Integer.MAX_VALUE));
        if (pinFileRanges != null) {
            pinFileRanges.used_pinlist = z2;
        }
        safeClose(inputStream);
        safeClose(zipFile);
        return pinFileRanges;
    }

    private static java.util.zip.ZipFile maybeOpenZip(java.lang.String str) {
        try {
            return new java.util.zip.ZipFile(str);
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, java.lang.String.format("could not open \"%s\" as zip: pinning as blob", str), e);
            return null;
        }
    }

    private static java.io.InputStream maybeOpenPinMetaInZip(java.util.zip.ZipFile zipFile, java.lang.String str) {
        if (!PROP_PIN_PINLIST) {
            return null;
        }
        java.util.zip.ZipEntry entry = zipFile.getEntry(PIN_META_FILENAME);
        if (entry == null) {
            entry = zipFile.getEntry("assets/pinlist.meta");
        }
        if (entry != null) {
            try {
                return zipFile.getInputStream(entry);
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, java.lang.String.format("error reading pin metadata \"%s\": pinning as blob", str), e);
                return null;
            }
        }
        android.util.Slog.w(TAG, java.lang.String.format("Could not find pinlist.meta for \"%s\": pinning as blob", str));
        return null;
    }

    private static final class PinRangeSourceStatic extends com.android.server.PinnerService.PinRangeSource {
        private boolean mDone;
        private final int mPinLength;
        private final int mPinStart;

        PinRangeSourceStatic(int i, int i2) {
            super();
            this.mDone = false;
            this.mPinStart = i;
            this.mPinLength = i2;
        }

        @Override // com.android.server.PinnerService.PinRangeSource
        boolean read(com.android.server.PinnerService.PinRange pinRange) {
            pinRange.start = this.mPinStart;
            pinRange.length = this.mPinLength;
            boolean z = this.mDone;
            this.mDone = true;
            return !z;
        }
    }

    private static final class PinRangeSourceStream extends com.android.server.PinnerService.PinRangeSource {
        private boolean mDone;
        private final java.io.DataInputStream mStream;

        PinRangeSourceStream(java.io.InputStream inputStream) {
            super();
            this.mDone = false;
            this.mStream = new java.io.DataInputStream(inputStream);
        }

        @Override // com.android.server.PinnerService.PinRangeSource
        boolean read(com.android.server.PinnerService.PinRange pinRange) {
            if (!this.mDone) {
                try {
                    pinRange.start = this.mStream.readInt();
                    pinRange.length = this.mStream.readInt();
                } catch (java.io.IOException e) {
                    this.mDone = true;
                }
            }
            return !this.mDone;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static com.android.server.PinnerService.PinnedFile pinFileRanges(java.lang.String str, int i, com.android.server.PinnerService.PinRangeSource pinRangeSource) {
        int min;
        long mmap;
        int i2;
        int i3;
        int i4;
        java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
        long j = -1;
        int i5 = 0;
        try {
            try {
                java.io.FileDescriptor open = android.system.Os.open(str, android.system.OsConstants.O_RDONLY | android.system.OsConstants.O_CLOEXEC, 0);
                try {
                    min = (int) java.lang.Math.min(android.system.Os.fstat(open).st_size, 2147483647L);
                    try {
                        mmap = android.system.Os.mmap(0L, min, android.system.OsConstants.PROT_READ, android.system.OsConstants.MAP_SHARED, open, 0L);
                        try {
                            com.android.server.PinnerService.PinRange pinRange = new com.android.server.PinnerService.PinRange();
                            if (i % PAGE_SIZE != 0) {
                                try {
                                    i3 = i - (i % PAGE_SIZE);
                                    i4 = 0;
                                } catch (android.system.ErrnoException e) {
                                    e = e;
                                    i5 = min;
                                    j = mmap;
                                    fileDescriptor = open;
                                    android.util.Slog.e(TAG, "Could not pin file " + str, e);
                                    safeClose(fileDescriptor);
                                    if (j >= 0) {
                                    }
                                } catch (java.lang.Throwable th) {
                                    th = th;
                                    i5 = min;
                                    j = mmap;
                                    fileDescriptor = open;
                                    safeClose(fileDescriptor);
                                    if (j >= 0) {
                                    }
                                    throw th;
                                }
                            } else {
                                i3 = i;
                                i4 = 0;
                            }
                            while (i4 < i3) {
                                if (!pinRangeSource.read(pinRange)) {
                                    break;
                                }
                                int i6 = pinRange.start;
                                int i7 = pinRange.length;
                                int clamp = clamp(0, i6, min);
                                int i8 = i3 - i4;
                                int min2 = java.lang.Math.min(i8, clamp(0, i7, min - clamp)) + (clamp % PAGE_SIZE);
                                int i9 = clamp - (clamp % PAGE_SIZE);
                                if (min2 % PAGE_SIZE != 0) {
                                    min2 += PAGE_SIZE - (min2 % PAGE_SIZE);
                                }
                                int clamp2 = clamp(0, min2, i8);
                                if (clamp2 > 0) {
                                    android.system.Os.mlock(i9 + mmap, clamp2);
                                }
                                i4 += clamp2;
                            }
                            i2 = min;
                        } catch (android.system.ErrnoException e2) {
                            e = e2;
                            i2 = min;
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            i2 = min;
                        }
                    } catch (android.system.ErrnoException e3) {
                        e = e3;
                        i5 = min;
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        i5 = min;
                    }
                } catch (android.system.ErrnoException e4) {
                    e = e4;
                } catch (java.lang.Throwable th4) {
                    th = th4;
                }
                try {
                    com.android.server.PinnerService.PinnedFile pinnedFile = new com.android.server.PinnerService.PinnedFile(mmap, min, str, i4);
                    safeClose(open);
                    return pinnedFile;
                } catch (android.system.ErrnoException e5) {
                    e = e5;
                    i5 = i2;
                    j = mmap;
                    fileDescriptor = open;
                    android.util.Slog.e(TAG, "Could not pin file " + str, e);
                    safeClose(fileDescriptor);
                    if (j >= 0) {
                        return null;
                    }
                    safeMunmap(j, i5);
                    return null;
                } catch (java.lang.Throwable th5) {
                    th = th5;
                    i5 = i2;
                    j = mmap;
                    fileDescriptor = open;
                    safeClose(fileDescriptor);
                    if (j >= 0) {
                        safeMunmap(j, i5);
                    }
                    throw th;
                }
            } catch (android.system.ErrnoException e6) {
                e = e6;
            }
        } catch (java.lang.Throwable th6) {
            th = th6;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<com.android.server.PinnerService.PinnedFile> getAllPinsForGroup(final java.lang.String str) {
        java.util.List<com.android.server.PinnerService.PinnedFile> list;
        synchronized (this) {
            list = this.mPinnedFiles.values().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.PinnerService$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getAllPinsForGroup$0;
                    lambda$getAllPinsForGroup$0 = com.android.server.PinnerService.lambda$getAllPinsForGroup$0(str, (com.android.server.PinnerService.PinnedFile) obj);
                    return lambda$getAllPinsForGroup$0;
                }
            }).toList();
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAllPinsForGroup$0(java.lang.String str, com.android.server.PinnerService.PinnedFile pinnedFile) {
        return pinnedFile.groupName.equals(str);
    }

    public void unpinGroup(java.lang.String str) {
        java.util.Iterator<com.android.server.PinnerService.PinnedFile> it = getAllPinsForGroup(str).iterator();
        while (it.hasNext()) {
            unpinFile(it.next().fileName);
        }
    }

    public void unpinFile(java.lang.String str) {
        com.android.server.PinnerService.PinnedFile pinnedFile;
        synchronized (this) {
            pinnedFile = this.mPinnedFiles.get(str);
        }
        if (pinnedFile == null) {
            return;
        }
        pinnedFile.close();
        synchronized (this) {
            try {
                this.mPinnedFiles.remove(pinnedFile.fileName);
                java.util.Iterator<com.android.server.PinnerService.PinnedFile> it = pinnedFile.pinnedDeps.iterator();
                while (it.hasNext()) {
                    com.android.server.PinnerService.PinnedFile next = it.next();
                    if (next != null) {
                        this.mPinnedFiles.remove(next.fileName);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static int clamp(int i, int i2, int i3) {
        return java.lang.Math.max(i, java.lang.Math.min(i2, i3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void safeMunmap(long j, long j2) {
        try {
            android.system.Os.munmap(j, j2);
        } catch (android.system.ErrnoException e) {
            android.util.Slog.w(TAG, "ignoring error in unmap", e);
        }
    }

    private static void safeClose(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor) {
        if (fileDescriptor != null && fileDescriptor.valid()) {
            try {
                android.system.Os.close(fileDescriptor);
            } catch (android.system.ErrnoException e) {
                if (e.errno == android.system.OsConstants.EBADF) {
                    throw new java.lang.AssertionError(e);
                }
            }
        }
    }

    private static void safeClose(@android.annotation.Nullable java.io.Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "ignoring error closing resource: " + closeable, e);
            }
        }
    }

    public java.util.List<android.app.pinner.PinnedFileStat> getPinnerStats() {
        java.util.Collection<com.android.server.PinnerService.PinnedApp> values;
        java.util.Collection<com.android.server.PinnerService.PinnedFile> values2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this) {
            values = this.mPinnedApps.values();
        }
        java.util.Iterator<com.android.server.PinnerService.PinnedApp> it = values.iterator();
        while (it.hasNext()) {
            java.util.Iterator<com.android.server.PinnerService.PinnedFile> it2 = it.next().mFiles.iterator();
            while (it2.hasNext()) {
                com.android.server.PinnerService.PinnedFile next = it2.next();
                arrayList.add(new android.app.pinner.PinnedFileStat(next.fileName, next.bytesPinned, next.groupName));
            }
        }
        synchronized (this) {
            values2 = this.mPinnedFiles.values();
        }
        for (com.android.server.PinnerService.PinnedFile pinnedFile : values2) {
            arrayList.add(new android.app.pinner.PinnedFileStat(pinnedFile.fileName, pinnedFile.bytesPinned, pinnedFile.groupName));
        }
        if (this.mCurrentlyPinnedAnonSize > 0) {
            arrayList.add(new android.app.pinner.PinnedFileStat(ANON_REGION_STAT_NAME, this.mCurrentlyPinnedAnonSize, ANON_REGION_STAT_NAME));
        }
        return arrayList;
    }

    public final class BinderService extends android.app.pinner.IPinnerService.Stub {
        public BinderService() {
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.PinnerService.this.mContext, com.android.server.PinnerService.TAG, printWriter)) {
                java.util.HashSet hashSet = new java.util.HashSet();
                java.util.HashSet hashSet2 = new java.util.HashSet();
                synchronized (com.android.server.PinnerService.this) {
                    try {
                        java.util.Iterator it = com.android.server.PinnerService.this.mPinnedApps.keySet().iterator();
                        long j = 0;
                        while (true) {
                            int i = 1048576;
                            if (!it.hasNext()) {
                                break;
                            }
                            int intValue = ((java.lang.Integer) it.next()).intValue();
                            com.android.server.PinnerService.PinnedApp pinnedApp = (com.android.server.PinnerService.PinnedApp) com.android.server.PinnerService.this.mPinnedApps.get(java.lang.Integer.valueOf(intValue));
                            printWriter.print(com.android.server.PinnerService.this.getNameForKey(intValue));
                            printWriter.print(" uid=");
                            printWriter.print(pinnedApp.uid);
                            printWriter.print(" active=");
                            printWriter.print(pinnedApp.active);
                            printWriter.println();
                            java.util.Iterator<com.android.server.PinnerService.PinnedFile> it2 = ((com.android.server.PinnerService.PinnedApp) com.android.server.PinnerService.this.mPinnedApps.get(java.lang.Integer.valueOf(intValue))).mFiles.iterator();
                            while (it2.hasNext()) {
                                com.android.server.PinnerService.PinnedFile next = it2.next();
                                printWriter.print("  ");
                                printWriter.format("%s pinned:%d bytes (%d MB) pinlist:%b\n", next.fileName, java.lang.Integer.valueOf(next.bytesPinned), java.lang.Integer.valueOf(next.bytesPinned / i), java.lang.Boolean.valueOf(next.used_pinlist));
                                j += next.bytesPinned;
                                hashSet.add(next);
                                java.util.Iterator<com.android.server.PinnerService.PinnedFile> it3 = next.pinnedDeps.iterator();
                                while (it3.hasNext()) {
                                    com.android.server.PinnerService.PinnedFile next2 = it3.next();
                                    printWriter.print("  ");
                                    printWriter.format("%s pinned:%d bytes (%d MB) pinlist:%b (Dependency)\n", next2.fileName, java.lang.Integer.valueOf(next2.bytesPinned), java.lang.Integer.valueOf(next2.bytesPinned / i), java.lang.Boolean.valueOf(next2.used_pinlist));
                                    j += next2.bytesPinned;
                                    hashSet.add(next2);
                                    i = 1048576;
                                }
                                i = 1048576;
                            }
                        }
                        printWriter.println();
                        for (com.android.server.PinnerService.PinnedFile pinnedFile : com.android.server.PinnerService.this.mPinnedFiles.values()) {
                            if (!hashSet2.contains(pinnedFile.groupName)) {
                                hashSet2.add(pinnedFile.groupName);
                            }
                        }
                        java.util.Iterator it4 = hashSet2.iterator();
                        boolean z = true;
                        while (it4.hasNext()) {
                            java.lang.String str = (java.lang.String) it4.next();
                            for (com.android.server.PinnerService.PinnedFile pinnedFile2 : com.android.server.PinnerService.this.getAllPinsForGroup(str)) {
                                if (!hashSet.contains(pinnedFile2)) {
                                    if (z) {
                                        printWriter.print("Group:" + str);
                                        printWriter.println();
                                        z = false;
                                    }
                                    printWriter.format("  %s pinned:%d bytes (%d MB) pinlist:%b\n", pinnedFile2.fileName, java.lang.Integer.valueOf(pinnedFile2.bytesPinned), java.lang.Integer.valueOf(pinnedFile2.bytesPinned / 1048576), java.lang.Boolean.valueOf(pinnedFile2.used_pinlist));
                                    j += pinnedFile2.bytesPinned;
                                }
                            }
                        }
                        printWriter.println();
                        if (com.android.server.PinnerService.this.mPinAnonAddress != 0) {
                            printWriter.format("Pinned anon region: %d (%d MB)\n", java.lang.Long.valueOf(com.android.server.PinnerService.this.mCurrentlyPinnedAnonSize), java.lang.Long.valueOf(com.android.server.PinnerService.this.mCurrentlyPinnedAnonSize / 1048576));
                            j += com.android.server.PinnerService.this.mCurrentlyPinnedAnonSize;
                        }
                        printWriter.format("Total pinned: %s bytes (%s MB)\n", java.lang.Long.valueOf(j), java.lang.Long.valueOf(j / 1048576));
                        printWriter.println();
                        if (!com.android.server.PinnerService.this.mPendingRepin.isEmpty()) {
                            printWriter.print("Pending repin: ");
                            java.util.Iterator it5 = com.android.server.PinnerService.this.mPendingRepin.values().iterator();
                            while (it5.hasNext()) {
                                printWriter.print(com.android.server.PinnerService.this.getNameForKey(((java.lang.Integer) it5.next()).intValue()));
                                printWriter.print(' ');
                            }
                            printWriter.println();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        private void repin() {
            com.android.server.PinnerService.this.sendUnpinAppsMessage();
            com.android.server.PinnerService.this.sendPinAppsWithUpdatedKeysMessage(0);
        }

        private void printError(java.io.FileDescriptor fileDescriptor, java.lang.String str) {
            java.io.PrintWriter printWriter = new java.io.PrintWriter(new java.io.FileOutputStream(fileDescriptor));
            printWriter.println(str);
            printWriter.flush();
        }

        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            boolean z;
            if (strArr.length < 1) {
                printError(fileDescriptor2, "Command is not given.");
                resultReceiver.send(-1, null);
            }
            java.lang.String str = strArr[0];
            switch (str.hashCode()) {
                case 108401282:
                    if (str.equals("repin")) {
                        z = false;
                        break;
                    }
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    repin();
                    resultReceiver.send(0, null);
                    break;
                default:
                    printError(fileDescriptor2, java.lang.String.format("Unknown pinner command: %s. Supported commands: repin", str));
                    resultReceiver.send(-1, null);
                    break;
            }
        }

        @android.annotation.EnforcePermission("android.permission.DUMP")
        public java.util.List<android.app.pinner.PinnedFileStat> getPinnerStats() {
            getPinnerStats_enforcePermission();
            return com.android.server.PinnerService.this.getPinnerStats();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static final class PinnedFile implements java.lang.AutoCloseable {
        final int bytesPinned;
        final java.lang.String fileName;
        private long mAddress;
        final int mapSize;
        boolean used_pinlist;
        java.lang.String groupName = "";
        java.util.ArrayList<com.android.server.PinnerService.PinnedFile> pinnedDeps = new java.util.ArrayList<>();

        PinnedFile(long j, int i, java.lang.String str, int i2) {
            this.mAddress = j;
            this.mapSize = i;
            this.fileName = str;
            this.bytesPinned = i2;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            if (this.mAddress >= 0) {
                com.android.server.PinnerService.safeMunmap(this.mAddress, this.mapSize);
                this.mAddress = -1L;
            }
            java.util.Iterator<com.android.server.PinnerService.PinnedFile> it = this.pinnedDeps.iterator();
            while (it.hasNext()) {
                com.android.server.PinnerService.PinnedFile next = it.next();
                if (next != null) {
                    next.close();
                }
            }
        }

        public void finalize() {
            close();
        }
    }

    private final class PinnedApp {
        boolean active;
        final java.util.ArrayList<com.android.server.PinnerService.PinnedFile> mFiles;
        final int uid;

        private PinnedApp(android.content.pm.ApplicationInfo applicationInfo) {
            this.mFiles = new java.util.ArrayList<>();
            this.uid = applicationInfo.uid;
            this.active = com.android.server.PinnerService.this.mAmInternal.isUidActive(this.uid);
        }
    }

    final class PinnerHandler extends android.os.Handler {
        static final int PIN_ONSTART_MSG = 4001;

        public PinnerHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case PIN_ONSTART_MSG /* 4001 */:
                    com.android.server.PinnerService.this.handlePinOnStart();
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
        }
    }
}
