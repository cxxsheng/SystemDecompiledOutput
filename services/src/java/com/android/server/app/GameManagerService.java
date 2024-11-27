package com.android.server.app;

/* loaded from: classes.dex */
public final class GameManagerService extends android.app.IGameManagerService.Stub {
    static final int CANCEL_GAME_LOADING_MODE = 5;
    private static final java.lang.String EVENT_ON_USER_STARTING = "ON_USER_STARTING";
    private static final java.lang.String EVENT_ON_USER_STOPPING = "ON_USER_STOPPING";
    private static final java.lang.String EVENT_ON_USER_SWITCHING = "ON_USER_SWITCHING";
    private static final java.lang.String EVENT_RECEIVE_SHUTDOWN_INDENT = "RECEIVE_SHUTDOWN_INDENT";
    private static final java.lang.String EVENT_SET_GAME_MODE = "SET_GAME_MODE";
    private static final java.lang.String EVENT_UPDATE_CUSTOM_GAME_MODE_CONFIG = "UPDATE_CUSTOM_GAME_MODE_CONFIG";
    private static final java.lang.String GAME_MODE_INTERVENTION_LIST_FILE_NAME = "game_mode_intervention.list";
    static final int LOADING_BOOST_MAX_DURATION = 5000;
    private static final java.lang.String PACKAGE_NAME_MSG_KEY = "packageName";
    static final int POPULATE_GAME_MODE_SETTINGS = 3;
    static final java.lang.String PROPERTY_DEBUG_GFX_GAME_DEFAULT_FRAME_RATE_DISABLED = "debug.graphics.game_default_frame_rate.disabled";
    static final java.lang.String PROPERTY_RO_SURFACEFLINGER_GAME_DEFAULT_FRAME_RATE = "ro.surface_flinger.game_default_frame_rate_override";
    static final int REMOVE_SETTINGS = 2;
    static final int SET_GAME_STATE = 4;
    public static final java.lang.String TAG = "GameManagerService";
    private static final java.lang.String USER_ID_MSG_KEY = "userId";
    static final int WRITE_DELAY_MILLIS = 10000;
    static final int WRITE_GAME_MODE_INTERVENTION_LIST_FILE = 6;
    static final int WRITE_SETTINGS = 1;

    @com.android.internal.annotations.GuardedBy({"mDeviceConfigLock"})
    private final android.util.ArrayMap<java.lang.String, com.android.server.app.GameManagerService.GamePackageConfiguration> mConfigs;
    private final android.content.Context mContext;
    private com.android.server.app.GameManagerService.DeviceConfigListener mDeviceConfigListener;
    private final java.lang.Object mDeviceConfigLock;
    private float mGameDefaultFrameRateValue;

    @com.android.internal.annotations.GuardedBy({"mUidObserverLock"})
    private final java.util.Set<java.lang.Integer> mGameForegroundUids;

    @com.android.internal.annotations.VisibleForTesting
    final android.util.AtomicFile mGameModeInterventionListFile;
    private final java.lang.Object mGameModeListenerLock;

    @com.android.internal.annotations.GuardedBy({"mGameModeListenerLock"})
    private final android.util.ArrayMap<android.app.IGameModeListener, java.lang.Integer> mGameModeListeners;

    @android.annotation.Nullable
    private final com.android.server.app.GameServiceController mGameServiceController;
    private final java.lang.Object mGameStateListenerLock;

    @com.android.internal.annotations.GuardedBy({"mGameStateListenerLock"})
    private final android.util.ArrayMap<android.app.IGameStateListener, java.lang.Integer> mGameStateListeners;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    final android.os.Handler mHandler;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mUidObserverLock"})
    private final java.util.Set<java.lang.Integer> mNonGameForegroundUids;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.os.PowerManagerInternal mPowerManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.Integer, com.android.server.app.GameManagerSettings> mSettings;
    private final com.android.server.app.GameManagerServiceSystemPropertiesWrapper mSysProps;

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    final com.android.server.app.GameManagerService.MyUidObserver mUidObserver;
    private final java.lang.Object mUidObserverLock;
    private final android.os.UserManager mUserManager;

    private static native void nativeSetGameDefaultFrameRateOverride(int i, float f);

    private static native void nativeSetGameModeFrameRateOverride(int i, float f);

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        public com.android.server.app.GameManagerServiceSystemPropertiesWrapper createSystemPropertiesWrapper() {
            return new com.android.server.app.GameManagerServiceSystemPropertiesWrapper() { // from class: com.android.server.app.GameManagerService.Injector.1
                @Override // com.android.server.app.GameManagerServiceSystemPropertiesWrapper
                public java.lang.String get(java.lang.String str, java.lang.String str2) {
                    return android.os.SystemProperties.get(str, str2);
                }

                @Override // com.android.server.app.GameManagerServiceSystemPropertiesWrapper
                public boolean getBoolean(java.lang.String str, boolean z) {
                    return android.os.SystemProperties.getBoolean(str, z);
                }

                @Override // com.android.server.app.GameManagerServiceSystemPropertiesWrapper
                public int getInt(java.lang.String str, int i) {
                    return android.os.SystemProperties.getInt(str, i);
                }

                @Override // com.android.server.app.GameManagerServiceSystemPropertiesWrapper
                public void set(java.lang.String str, java.lang.String str2) {
                    android.os.SystemProperties.set(str, str2);
                }
            };
        }
    }

    public GameManagerService(android.content.Context context) {
        this(context, createServiceThread().getLooper());
    }

    GameManagerService(android.content.Context context, android.os.Looper looper) {
        this(context, looper, android.os.Environment.getDataDirectory(), new com.android.server.app.GameManagerService.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    GameManagerService(android.content.Context context, android.os.Looper looper, java.io.File file, com.android.server.app.GameManagerService.Injector injector) {
        super(android.os.PermissionEnforcer.fromContext(context));
        this.mLock = new java.lang.Object();
        this.mDeviceConfigLock = new java.lang.Object();
        this.mGameModeListenerLock = new java.lang.Object();
        this.mGameStateListenerLock = new java.lang.Object();
        this.mSettings = new android.util.ArrayMap<>();
        this.mConfigs = new android.util.ArrayMap<>();
        this.mGameModeListeners = new android.util.ArrayMap<>();
        this.mGameStateListeners = new android.util.ArrayMap<>();
        this.mUidObserverLock = new java.lang.Object();
        this.mGameForegroundUids = new java.util.HashSet();
        this.mNonGameForegroundUids = new java.util.HashSet();
        this.mContext = context;
        this.mHandler = new com.android.server.app.GameManagerService.SettingsHandler(looper);
        this.mPackageManager = this.mContext.getPackageManager();
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        this.mPowerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
        java.io.File file2 = new java.io.File(file, "system");
        file2.mkdirs();
        android.os.FileUtils.setPermissions(file2.toString(), 509, -1, -1);
        this.mGameModeInterventionListFile = new android.util.AtomicFile(new java.io.File(file2, GAME_MODE_INTERVENTION_LIST_FILE_NAME));
        android.os.FileUtils.setPermissions(this.mGameModeInterventionListFile.getBaseFile().getAbsolutePath(), com.android.internal.util.FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, -1, -1);
        if (this.mPackageManager.hasSystemFeature("android.software.game_service")) {
            this.mGameServiceController = new com.android.server.app.GameServiceController(context, com.android.internal.os.BackgroundThread.getExecutor(), new com.android.server.app.GameServiceProviderSelectorImpl(context.getResources(), this.mPackageManager), new com.android.server.app.GameServiceProviderInstanceFactoryImpl(context));
        } else {
            this.mGameServiceController = null;
        }
        this.mUidObserver = new com.android.server.app.GameManagerService.MyUidObserver();
        try {
            android.app.ActivityManager.getService().registerUidObserver(this.mUidObserver, 3, -1, (java.lang.String) null);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Could not register UidObserver");
        }
        this.mSysProps = injector.createSystemPropertiesWrapper();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.app.GameManagerShellCommand().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump GameManagerService from from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " without permission android.permission.DUMP");
            return;
        }
        if (strArr == null || strArr.length == 0) {
            printWriter.println("*Dump GameManagerService*");
            dumpAllGameConfigs(printWriter);
        }
    }

    private void dumpAllGameConfigs(java.io.PrintWriter printWriter) {
        int currentUser = android.app.ActivityManager.getCurrentUser();
        for (java.lang.String str : getInstalledGamePackageNames(currentUser)) {
            printWriter.println(getInterventionList(str, currentUser));
        }
    }

    class SettingsHandler extends android.os.Handler {
        SettingsHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            doHandleMessage(message);
        }

        void doHandleMessage(android.os.Message message) {
            int i;
            switch (message.what) {
                case 1:
                    int intValue = ((java.lang.Integer) message.obj).intValue();
                    if (intValue < 0) {
                        android.util.Slog.wtf(com.android.server.app.GameManagerService.TAG, "Attempt to write settings for invalid user: " + intValue);
                        synchronized (com.android.server.app.GameManagerService.this.mLock) {
                            removeEqualMessages(1, message.obj);
                        }
                        return;
                    }
                    android.os.Process.setThreadPriority(0);
                    synchronized (com.android.server.app.GameManagerService.this.mLock) {
                        try {
                            removeEqualMessages(1, message.obj);
                            if (com.android.server.app.GameManagerService.this.mSettings.containsKey(java.lang.Integer.valueOf(intValue))) {
                                ((com.android.server.app.GameManagerSettings) com.android.server.app.GameManagerService.this.mSettings.get(java.lang.Integer.valueOf(intValue))).writePersistentDataLocked();
                            }
                        } finally {
                        }
                    }
                    android.os.Process.setThreadPriority(10);
                    return;
                case 2:
                    int intValue2 = ((java.lang.Integer) message.obj).intValue();
                    if (intValue2 < 0) {
                        android.util.Slog.wtf(com.android.server.app.GameManagerService.TAG, "Attempt to write settings for invalid user: " + intValue2);
                        synchronized (com.android.server.app.GameManagerService.this.mLock) {
                            removeEqualMessages(1, message.obj);
                            removeEqualMessages(2, message.obj);
                        }
                        return;
                    }
                    synchronized (com.android.server.app.GameManagerService.this.mLock) {
                        try {
                            removeEqualMessages(1, message.obj);
                            removeEqualMessages(2, message.obj);
                            if (com.android.server.app.GameManagerService.this.mSettings.containsKey(java.lang.Integer.valueOf(intValue2))) {
                                com.android.server.app.GameManagerSettings gameManagerSettings = (com.android.server.app.GameManagerSettings) com.android.server.app.GameManagerService.this.mSettings.get(java.lang.Integer.valueOf(intValue2));
                                com.android.server.app.GameManagerService.this.mSettings.remove(java.lang.Integer.valueOf(intValue2));
                                gameManagerSettings.writePersistentDataLocked();
                            }
                        } finally {
                        }
                    }
                    return;
                case 3:
                    removeEqualMessages(3, message.obj);
                    int intValue3 = ((java.lang.Integer) message.obj).intValue();
                    com.android.server.app.GameManagerService.this.updateConfigsForUser(intValue3, false, com.android.server.app.GameManagerService.this.getInstalledGamePackageNames(intValue3));
                    return;
                case 4:
                    android.app.GameState gameState = (android.app.GameState) message.obj;
                    boolean isLoading = gameState.isLoading();
                    android.os.Bundle data = message.getData();
                    java.lang.String string = data.getString("packageName");
                    int i2 = data.getInt("userId");
                    boolean z = com.android.server.app.GameManagerService.this.getGameMode(string, i2) == 2;
                    try {
                        i = com.android.server.app.GameManagerService.this.mPackageManager.getPackageUidAsUser(string, i2);
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        android.util.Slog.v(com.android.server.app.GameManagerService.TAG, "Failed to get package metadata");
                        i = -1;
                    }
                    com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.GAME_STATE_CHANGED, string, i, z, com.android.server.app.GameManagerService.gameStateModeToStatsdGameState(gameState.getMode()), isLoading, gameState.getLabel(), gameState.getQuality());
                    if (z) {
                        if (com.android.server.app.GameManagerService.this.mPowerManagerInternal == null) {
                            android.util.Slog.d(com.android.server.app.GameManagerService.TAG, "Error setting loading mode for package " + string + " and userId " + i2);
                            return;
                        }
                        if (com.android.server.app.GameManagerService.this.mHandler.hasMessages(5)) {
                            com.android.server.app.GameManagerService.this.mHandler.removeMessages(5);
                        }
                        android.util.Slog.v(com.android.server.app.GameManagerService.TAG, java.lang.String.format("Game loading power mode %s (game state change isLoading=%b)", isLoading ? "ON" : "OFF", java.lang.Boolean.valueOf(isLoading)));
                        com.android.server.app.GameManagerService.this.mPowerManagerInternal.setPowerMode(16, isLoading);
                        if (isLoading) {
                            int loadingBoostDuration = com.android.server.app.GameManagerService.this.getLoadingBoostDuration(string, i2);
                            if (loadingBoostDuration <= 0) {
                                loadingBoostDuration = 5000;
                            }
                            com.android.server.app.GameManagerService.this.mHandler.sendMessageDelayed(com.android.server.app.GameManagerService.this.mHandler.obtainMessage(5), loadingBoostDuration);
                        }
                    }
                    synchronized (com.android.server.app.GameManagerService.this.mGameStateListenerLock) {
                        for (android.app.IGameStateListener iGameStateListener : com.android.server.app.GameManagerService.this.mGameStateListeners.keySet()) {
                            try {
                                iGameStateListener.onGameStateChanged(string, gameState, i2);
                            } catch (android.os.RemoteException e2) {
                                android.util.Slog.w(com.android.server.app.GameManagerService.TAG, "Cannot notify game state change for listener added by " + com.android.server.app.GameManagerService.this.mGameStateListeners.get(iGameStateListener));
                            }
                        }
                    }
                    return;
                case 5:
                    android.util.Slog.v(com.android.server.app.GameManagerService.TAG, "Game loading power mode OFF (loading boost ended)");
                    com.android.server.app.GameManagerService.this.mPowerManagerInternal.setPowerMode(16, false);
                    return;
                case 6:
                    int intValue4 = ((java.lang.Integer) message.obj).intValue();
                    if (intValue4 < 0) {
                        android.util.Slog.wtf(com.android.server.app.GameManagerService.TAG, "Attempt to write setting for invalid user: " + intValue4);
                        synchronized (com.android.server.app.GameManagerService.this.mLock) {
                            removeEqualMessages(6, message.obj);
                        }
                        return;
                    }
                    android.os.Process.setThreadPriority(0);
                    removeEqualMessages(6, message.obj);
                    com.android.server.app.GameManagerService.this.writeGameModeInterventionsToFile(intValue4);
                    android.os.Process.setThreadPriority(10);
                    return;
                default:
                    return;
            }
        }
    }

    private class DeviceConfigListener implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        DeviceConfigListener() {
            android.provider.DeviceConfig.addOnPropertiesChangedListener("game_overlay", com.android.server.app.GameManagerService.this.mContext.getMainExecutor(), this);
        }

        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            java.lang.String[] strArr = (java.lang.String[]) properties.getKeyset().toArray(new java.lang.String[0]);
            android.util.Slog.v(com.android.server.app.GameManagerService.TAG, "Device config changed for packages: " + java.util.Arrays.toString(strArr));
            com.android.server.app.GameManagerService.this.updateConfigsForUser(android.app.ActivityManager.getCurrentUser(), true, strArr);
        }

        public void finalize() {
            android.provider.DeviceConfig.removeOnPropertiesChangedListener(this);
        }
    }

    public void setGameState(java.lang.String str, @android.annotation.NonNull android.app.GameState gameState, int i) {
        if (!lambda$updateConfigsForUser$0(str, i)) {
            android.util.Slog.d(TAG, "No-op for attempt to set game state for non-game app: " + str);
            return;
        }
        android.os.Message obtainMessage = this.mHandler.obtainMessage(4);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("packageName", str);
        bundle.putInt("userId", i);
        obtainMessage.setData(bundle);
        obtainMessage.obj = gameState;
        this.mHandler.sendMessage(obtainMessage);
    }

    public static class GamePackageConfiguration {
        private static final java.lang.String GAME_MODE_CONFIG_NODE_NAME = "game-mode-config";
        public static final java.lang.String METADATA_ANGLE_ALLOW_ANGLE = "com.android.graphics.intervention.angle.allowAngle";
        public static final java.lang.String METADATA_BATTERY_MODE_ENABLE = "com.android.app.gamemode.battery.enabled";
        public static final java.lang.String METADATA_GAME_MODE_CONFIG = "android.game_mode_config";
        public static final java.lang.String METADATA_PERFORMANCE_MODE_ENABLE = "com.android.app.gamemode.performance.enabled";
        public static final java.lang.String METADATA_WM_ALLOW_DOWNSCALE = "com.android.graphics.intervention.wm.allowDownscale";
        public static final java.lang.String TAG = "GameManagerService_GamePackageConfiguration";
        private boolean mAllowAngle;
        private boolean mAllowDownscale;
        private boolean mAllowFpsOverride;
        private boolean mBatteryModeOverridden;
        private final java.lang.Object mModeConfigLock;

        @com.android.internal.annotations.GuardedBy({"mModeConfigLock"})
        private final android.util.ArrayMap<java.lang.Integer, com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration> mModeConfigs;
        private final java.lang.String mPackageName;
        private boolean mPerfModeOverridden;

        GamePackageConfiguration(java.lang.String str) {
            this.mModeConfigLock = new java.lang.Object();
            this.mModeConfigs = new android.util.ArrayMap<>();
            this.mPerfModeOverridden = false;
            this.mBatteryModeOverridden = false;
            this.mAllowDownscale = true;
            this.mAllowAngle = true;
            this.mAllowFpsOverride = true;
            this.mPackageName = str;
        }

        GamePackageConfiguration(android.content.pm.PackageManager packageManager, java.lang.String str, int i) {
            this.mModeConfigLock = new java.lang.Object();
            this.mModeConfigs = new android.util.ArrayMap<>();
            this.mPerfModeOverridden = false;
            this.mBatteryModeOverridden = false;
            this.mAllowDownscale = true;
            this.mAllowAngle = true;
            this.mAllowFpsOverride = true;
            this.mPackageName = str;
            try {
                android.content.pm.ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(str, 128, i);
                if (!parseInterventionFromXml(packageManager, applicationInfoAsUser, str) && applicationInfoAsUser.metaData != null) {
                    this.mPerfModeOverridden = applicationInfoAsUser.metaData.getBoolean(METADATA_PERFORMANCE_MODE_ENABLE);
                    this.mBatteryModeOverridden = applicationInfoAsUser.metaData.getBoolean(METADATA_BATTERY_MODE_ENABLE);
                    this.mAllowDownscale = applicationInfoAsUser.metaData.getBoolean(METADATA_WM_ALLOW_DOWNSCALE, true);
                    this.mAllowAngle = applicationInfoAsUser.metaData.getBoolean(METADATA_ANGLE_ALLOW_ANGLE, true);
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.v(TAG, "Failed to get package metadata");
            }
            java.lang.String property = android.provider.DeviceConfig.getProperty("game_overlay", str);
            if (property != null) {
                for (java.lang.String str2 : property.split(":")) {
                    try {
                        android.util.KeyValueListParser keyValueListParser = new android.util.KeyValueListParser(',');
                        keyValueListParser.setString(str2);
                        addModeConfig(new com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration(keyValueListParser));
                    } catch (java.lang.IllegalArgumentException e2) {
                        android.util.Slog.e(TAG, "Invalid config string");
                    }
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x0084 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private boolean parseInterventionFromXml(android.content.pm.PackageManager packageManager, android.content.pm.ApplicationInfo applicationInfo, java.lang.String str) {
            boolean z;
            boolean z2;
            int next;
            try {
                android.content.res.XmlResourceParser loadXmlMetaData = applicationInfo.loadXmlMetaData(packageManager, METADATA_GAME_MODE_CONFIG);
                if (loadXmlMetaData == null) {
                    try {
                        android.util.Slog.v(TAG, "No android.game_mode_config meta-data found for package " + this.mPackageName);
                        z = false;
                    } catch (java.lang.Throwable th) {
                        th = th;
                        z2 = false;
                        try {
                            if (loadXmlMetaData != null) {
                                try {
                                    loadXmlMetaData.close();
                                } catch (java.lang.Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                            z = z2;
                            this.mPerfModeOverridden = false;
                            this.mBatteryModeOverridden = false;
                            this.mAllowDownscale = true;
                            this.mAllowAngle = true;
                            this.mAllowFpsOverride = true;
                            android.util.Slog.e(TAG, "Error while parsing XML meta-data for android.game_mode_config");
                            return z;
                        }
                    }
                } else {
                    try {
                        android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(str);
                        android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                        do {
                            next = loadXmlMetaData.next();
                            if (next == 1) {
                                break;
                            }
                        } while (next != 2);
                        if (GAME_MODE_CONFIG_NODE_NAME.equals(loadXmlMetaData.getName())) {
                            android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.GameModeConfig);
                            this.mPerfModeOverridden = obtainAttributes.getBoolean(1, false);
                            this.mBatteryModeOverridden = obtainAttributes.getBoolean(0, false);
                            this.mAllowDownscale = obtainAttributes.getBoolean(3, true);
                            this.mAllowAngle = obtainAttributes.getBoolean(2, true);
                            this.mAllowFpsOverride = obtainAttributes.getBoolean(4, true);
                            obtainAttributes.recycle();
                        } else {
                            android.util.Slog.w(TAG, "Meta-data does not start with game-mode-config tag");
                        }
                        z = true;
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        z2 = true;
                        if (loadXmlMetaData != null) {
                        }
                        throw th;
                    }
                }
                if (loadXmlMetaData != null) {
                    try {
                        loadXmlMetaData.close();
                    } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
                        this.mPerfModeOverridden = false;
                        this.mBatteryModeOverridden = false;
                        this.mAllowDownscale = true;
                        this.mAllowAngle = true;
                        this.mAllowFpsOverride = true;
                        android.util.Slog.e(TAG, "Error while parsing XML meta-data for android.game_mode_config");
                        return z;
                    }
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e3) {
                z = false;
            }
            return z;
        }

        com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration getOrAddDefaultGameModeConfiguration(int i) {
            com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration gameModeConfiguration;
            synchronized (this.mModeConfigLock) {
                this.mModeConfigs.putIfAbsent(java.lang.Integer.valueOf(i), new com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration(i));
                gameModeConfiguration = this.mModeConfigs.get(java.lang.Integer.valueOf(i));
            }
            return gameModeConfiguration;
        }

        boolean hasActiveGameModeConfig() {
            boolean z;
            synchronized (this.mModeConfigLock) {
                z = !this.mModeConfigs.isEmpty();
            }
            return z;
        }

        public class GameModeConfiguration {
            public static final java.lang.String ANGLE_KEY = "useAngle";
            public static final java.lang.String DEFAULT_FPS = "";
            public static final int DEFAULT_LOADING_BOOST_DURATION = -1;
            public static final float DEFAULT_SCALING = -1.0f;
            public static final boolean DEFAULT_USE_ANGLE = false;
            public static final java.lang.String FPS_KEY = "fps";
            public static final java.lang.String LOADING_BOOST_KEY = "loadingBoost";
            public static final java.lang.String MODE_KEY = "mode";
            public static final java.lang.String SCALING_KEY = "downscaleFactor";
            public static final java.lang.String TAG = "GameManagerService_GameModeConfiguration";
            private java.lang.String mFps;
            private final int mGameMode;
            private int mLoadingBoostDuration;
            private float mScaling;
            private boolean mUseAngle;

            GameModeConfiguration(int i) {
                this.mScaling = -1.0f;
                this.mFps = "";
                this.mGameMode = i;
                this.mUseAngle = false;
                this.mLoadingBoostDuration = -1;
            }

            GameModeConfiguration(android.util.KeyValueListParser keyValueListParser) {
                float f = -1.0f;
                this.mScaling = -1.0f;
                java.lang.String str = "";
                this.mFps = "";
                boolean z = false;
                this.mGameMode = keyValueListParser.getInt(MODE_KEY, 0);
                if (com.android.server.app.GameManagerService.GamePackageConfiguration.this.mAllowDownscale && !com.android.server.app.GameManagerService.GamePackageConfiguration.this.willGamePerformOptimizations(this.mGameMode)) {
                    f = keyValueListParser.getFloat(SCALING_KEY, -1.0f);
                }
                this.mScaling = f;
                if (com.android.server.app.GameManagerService.GamePackageConfiguration.this.mAllowFpsOverride && !com.android.server.app.GameManagerService.GamePackageConfiguration.this.willGamePerformOptimizations(this.mGameMode)) {
                    str = keyValueListParser.getString(FPS_KEY, "");
                }
                this.mFps = str;
                if (com.android.server.app.GameManagerService.GamePackageConfiguration.this.mAllowAngle && !com.android.server.app.GameManagerService.GamePackageConfiguration.this.willGamePerformOptimizations(this.mGameMode) && keyValueListParser.getBoolean(ANGLE_KEY, false)) {
                    z = true;
                }
                this.mUseAngle = z;
                this.mLoadingBoostDuration = com.android.server.app.GameManagerService.GamePackageConfiguration.this.willGamePerformOptimizations(this.mGameMode) ? -1 : keyValueListParser.getInt(LOADING_BOOST_KEY, -1);
            }

            public int getGameMode() {
                return this.mGameMode;
            }

            public synchronized float getScaling() {
                return this.mScaling;
            }

            public synchronized int getFps() {
                try {
                } catch (java.lang.NumberFormatException e) {
                    return 0;
                }
                return java.lang.Integer.parseInt(this.mFps);
            }

            synchronized java.lang.String getFpsStr() {
                return this.mFps;
            }

            public synchronized boolean getUseAngle() {
                return this.mUseAngle;
            }

            public synchronized int getLoadingBoostDuration() {
                return this.mLoadingBoostDuration;
            }

            public synchronized void setScaling(float f) {
                this.mScaling = f;
            }

            public synchronized void setFpsStr(java.lang.String str) {
                this.mFps = str;
            }

            public synchronized void setUseAngle(boolean z) {
                this.mUseAngle = z;
            }

            public synchronized void setLoadingBoostDuration(int i) {
                this.mLoadingBoostDuration = i;
            }

            public boolean isActive() {
                return (this.mGameMode == 1 || this.mGameMode == 2 || this.mGameMode == 3 || this.mGameMode == 4) && !com.android.server.app.GameManagerService.GamePackageConfiguration.this.willGamePerformOptimizations(this.mGameMode);
            }

            android.app.GameModeConfiguration toPublicGameModeConfig() {
                int i;
                try {
                    i = java.lang.Integer.parseInt(this.mFps);
                } catch (java.lang.NumberFormatException e) {
                    i = 0;
                }
                return new android.app.GameModeConfiguration.Builder().setScalingFactor(this.mScaling == -1.0f ? 1.0f : this.mScaling).setFpsOverride(i > 0 ? i : 0).build();
            }

            void updateFromPublicGameModeConfig(android.app.GameModeConfiguration gameModeConfiguration) {
                this.mScaling = gameModeConfiguration.getScalingFactor();
                this.mFps = java.lang.String.valueOf(gameModeConfiguration.getFpsOverride());
            }

            public java.lang.String toString() {
                return "[Game Mode:" + this.mGameMode + ",Scaling:" + this.mScaling + ",Use Angle:" + this.mUseAngle + ",Fps:" + this.mFps + ",Loading Boost Duration:" + this.mLoadingBoostDuration + "]";
            }
        }

        public java.lang.String getPackageName() {
            return this.mPackageName;
        }

        public boolean willGamePerformOptimizations(int i) {
            return (this.mBatteryModeOverridden && i == 3) || (this.mPerfModeOverridden && i == 2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getAvailableGameModesBitfield() {
            int modeToBitmask = com.android.server.app.GameManagerService.modeToBitmask(4) | com.android.server.app.GameManagerService.modeToBitmask(1);
            synchronized (this.mModeConfigLock) {
                try {
                    java.util.Iterator<java.lang.Integer> it = this.mModeConfigs.keySet().iterator();
                    while (it.hasNext()) {
                        modeToBitmask |= com.android.server.app.GameManagerService.modeToBitmask(it.next().intValue());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (this.mBatteryModeOverridden) {
                modeToBitmask |= com.android.server.app.GameManagerService.modeToBitmask(3);
            }
            if (this.mPerfModeOverridden) {
                return modeToBitmask | com.android.server.app.GameManagerService.modeToBitmask(2);
            }
            return modeToBitmask;
        }

        public int[] getAvailableGameModes() {
            int availableGameModesBitfield = getAvailableGameModesBitfield();
            int[] iArr = new int[java.lang.Integer.bitCount(availableGameModesBitfield)];
            int numberOfTrailingZeros = java.lang.Integer.numberOfTrailingZeros(java.lang.Integer.highestOneBit(availableGameModesBitfield));
            int i = 0;
            for (int i2 = 0; i2 <= numberOfTrailingZeros; i2++) {
                if (((availableGameModesBitfield >> i2) & 1) != 0) {
                    iArr[i] = i2;
                    i++;
                }
            }
            return iArr;
        }

        public int[] getOverriddenGameModes() {
            if (this.mBatteryModeOverridden && this.mPerfModeOverridden) {
                return new int[]{3, 2};
            }
            if (this.mBatteryModeOverridden) {
                return new int[]{3};
            }
            if (this.mPerfModeOverridden) {
                return new int[]{2};
            }
            return new int[0];
        }

        public com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration getGameModeConfiguration(int i) {
            com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration gameModeConfiguration;
            synchronized (this.mModeConfigLock) {
                gameModeConfiguration = this.mModeConfigs.get(java.lang.Integer.valueOf(i));
            }
            return gameModeConfiguration;
        }

        public void addModeConfig(com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration gameModeConfiguration) {
            if (gameModeConfiguration.isActive()) {
                synchronized (this.mModeConfigLock) {
                    this.mModeConfigs.put(java.lang.Integer.valueOf(gameModeConfiguration.getGameMode()), gameModeConfiguration);
                }
            } else {
                android.util.Slog.w(TAG, "Attempt to add inactive game mode config for " + this.mPackageName + ":" + gameModeConfiguration.toString());
            }
        }

        public void removeModeConfig(int i) {
            synchronized (this.mModeConfigLock) {
                this.mModeConfigs.remove(java.lang.Integer.valueOf(i));
            }
        }

        public boolean isActive() {
            boolean z;
            synchronized (this.mModeConfigLock) {
                try {
                    z = this.mModeConfigs.size() > 0 || this.mBatteryModeOverridden || this.mPerfModeOverridden;
                } finally {
                }
            }
            return z;
        }

        com.android.server.app.GameManagerService.GamePackageConfiguration copyAndApplyOverride(com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration) {
            com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration2 = new com.android.server.app.GameManagerService.GamePackageConfiguration(this.mPackageName);
            gamePackageConfiguration2.mPerfModeOverridden = this.mPerfModeOverridden && (gamePackageConfiguration == null || gamePackageConfiguration.getGameModeConfiguration(2) == null);
            gamePackageConfiguration2.mBatteryModeOverridden = this.mBatteryModeOverridden && (gamePackageConfiguration == null || gamePackageConfiguration.getGameModeConfiguration(3) == null);
            gamePackageConfiguration2.mAllowDownscale = this.mAllowDownscale || gamePackageConfiguration != null;
            gamePackageConfiguration2.mAllowAngle = this.mAllowAngle || gamePackageConfiguration != null;
            gamePackageConfiguration2.mAllowFpsOverride = this.mAllowFpsOverride || gamePackageConfiguration != null;
            if (gamePackageConfiguration != null) {
                synchronized (gamePackageConfiguration2.mModeConfigLock) {
                    synchronized (this.mModeConfigLock) {
                        try {
                            for (java.util.Map.Entry<java.lang.Integer, com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration> entry : this.mModeConfigs.entrySet()) {
                                gamePackageConfiguration2.mModeConfigs.put(entry.getKey(), entry.getValue());
                            }
                        } finally {
                        }
                    }
                    synchronized (gamePackageConfiguration.mModeConfigLock) {
                        try {
                            for (java.util.Map.Entry<java.lang.Integer, com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration> entry2 : gamePackageConfiguration.mModeConfigs.entrySet()) {
                                gamePackageConfiguration2.mModeConfigs.put(entry2.getKey(), entry2.getValue());
                            }
                        } finally {
                        }
                    }
                }
            }
            return gamePackageConfiguration2;
        }

        public java.lang.String toString() {
            java.lang.String str;
            synchronized (this.mModeConfigLock) {
                str = "[Name:" + this.mPackageName + " Modes: " + this.mModeConfigs.toString() + "]";
            }
            return str;
        }
    }

    private final class LocalService extends android.app.GameManagerInternal implements com.android.server.wm.CompatScaleProvider {
        private LocalService() {
        }

        public float getResolutionScalingFactor(java.lang.String str, int i) {
            return com.android.server.app.GameManagerService.this.getResolutionScalingFactorInternal(str, com.android.server.app.GameManagerService.this.getGameModeFromSettingsUnchecked(str, i), i);
        }

        @Override // com.android.server.wm.CompatScaleProvider
        @android.annotation.Nullable
        public android.content.res.CompatibilityInfo.CompatScale getCompatScale(@android.annotation.NonNull java.lang.String str, int i) {
            float resolutionScalingFactor = getResolutionScalingFactor(str, android.os.UserHandle.getUserHandleForUid(i).getIdentifier());
            if (resolutionScalingFactor > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                return new android.content.res.CompatibilityInfo.CompatScale(1.0f / resolutionScalingFactor);
            }
            return null;
        }
    }

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.app.GameManagerService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
            this.mService = new com.android.server.app.GameManagerService(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            publishBinderService("game", this.mService);
            this.mService.publishLocalService();
            this.mService.registerDeviceConfigListener();
            this.mService.registerPackageReceiver();
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 1000) {
                this.mService.onBootCompleted();
                this.mService.registerStatsCallbacks();
            }
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            android.util.Slog.d(com.android.server.app.GameManagerService.TAG, "Starting user " + targetUser.getUserIdentifier());
            this.mService.onUserStarting(targetUser, android.os.Environment.getDataSystemDeDirectory(targetUser.getUserIdentifier()));
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onUserUnlocking(targetUser);
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onUserStopping(targetUser);
        }

        @Override // com.android.server.SystemService
        public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
            this.mService.onUserSwitching(targetUser, targetUser2);
        }
    }

    private boolean isValidPackageName(java.lang.String str, int i) {
        try {
            return this.mPackageManager.getPackageUidAsUser(str, i) == android.os.Binder.getCallingUid();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void checkPermission(java.lang.String str) throws java.lang.SecurityException {
        if (this.mContext.checkCallingOrSelfPermission(str) != 0) {
            throw new java.lang.SecurityException("Access denied to process: " + android.os.Binder.getCallingPid() + ", must have permission " + str);
        }
    }

    private int[] getAvailableGameModesUnchecked(java.lang.String str, int i) {
        com.android.server.app.GameManagerService.GamePackageConfiguration config = getConfig(str, i);
        if (config == null) {
            return new int[]{1, 4};
        }
        return config.getAvailableGameModes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: isPackageGame, reason: merged with bridge method [inline-methods] */
    public boolean lambda$updateConfigsForUser$0(java.lang.String str, int i) {
        try {
            return this.mPackageManager.getApplicationInfoAsUser(str, 131072, i).category == 0;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_GAME_MODE")
    public int[] getAvailableGameModes(java.lang.String str, int i) throws java.lang.SecurityException {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        if (!lambda$updateConfigsForUser$0(str, i)) {
            return new int[0];
        }
        return getAvailableGameModesUnchecked(str, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getGameModeFromSettingsUnchecked(java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                if (!this.mSettings.containsKey(java.lang.Integer.valueOf(i))) {
                    android.util.Slog.d(TAG, "User ID '" + i + "' does not have a Game Mode selected for package: '" + str + "'");
                    return 1;
                }
                return this.mSettings.get(java.lang.Integer.valueOf(i)).getGameModeLocked(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getGameMode(@android.annotation.NonNull java.lang.String str, int i) throws java.lang.SecurityException {
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, true, "getGameMode", "com.android.server.app.GameManagerService");
        if (!lambda$updateConfigsForUser$0(str, handleIncomingUser)) {
            return 0;
        }
        if (isValidPackageName(str, handleIncomingUser)) {
            return getGameModeFromSettingsUnchecked(str, handleIncomingUser);
        }
        checkPermission("android.permission.MANAGE_GAME_MODE");
        return getGameModeFromSettingsUnchecked(str, handleIncomingUser);
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_GAME_MODE")
    @android.annotation.Nullable
    public android.app.GameModeInfo getGameModeInfo(@android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration gameModeConfiguration;
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, true, "getGameModeInfo", "com.android.server.app.GameManagerService");
        checkPermission("android.permission.MANAGE_GAME_MODE");
        if (!lambda$updateConfigsForUser$0(str, handleIncomingUser)) {
            return null;
        }
        int gameModeFromSettingsUnchecked = getGameModeFromSettingsUnchecked(str, handleIncomingUser);
        com.android.server.app.GameManagerService.GamePackageConfiguration config = getConfig(str, handleIncomingUser);
        if (config != null) {
            int[] overriddenGameModes = config.getOverriddenGameModes();
            int[] availableGameModes = config.getAvailableGameModes();
            android.app.GameModeInfo.Builder fpsOverrideAllowed = new android.app.GameModeInfo.Builder().setActiveGameMode(gameModeFromSettingsUnchecked).setAvailableGameModes(availableGameModes).setOverriddenGameModes(overriddenGameModes).setDownscalingAllowed(config.mAllowDownscale).setFpsOverrideAllowed(config.mAllowFpsOverride);
            for (int i2 : availableGameModes) {
                if (!config.willGamePerformOptimizations(i2) && (gameModeConfiguration = config.getGameModeConfiguration(i2)) != null) {
                    fpsOverrideAllowed.setGameModeConfiguration(i2, gameModeConfiguration.toPublicGameModeConfig());
                }
            }
            return fpsOverrideAllowed.build();
        }
        return new android.app.GameModeInfo.Builder().setActiveGameMode(gameModeFromSettingsUnchecked).setAvailableGameModes(getAvailableGameModesUnchecked(str, handleIncomingUser)).build();
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_GAME_MODE")
    public void setGameMode(java.lang.String str, int i, int i2) throws java.lang.SecurityException {
        int i3;
        checkPermission("android.permission.MANAGE_GAME_MODE");
        if (i == 0) {
            android.util.Slog.d(TAG, "No-op for attempt to set UNSUPPORTED mode for app: " + str);
            return;
        }
        if (!lambda$updateConfigsForUser$0(str, i2)) {
            android.util.Slog.d(TAG, "No-op for attempt to set game mode for non-game app: " + str);
            return;
        }
        synchronized (this.mLock) {
            try {
                int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2, false, true, "setGameMode", "com.android.server.app.GameManagerService");
                if (!this.mSettings.containsKey(java.lang.Integer.valueOf(handleIncomingUser))) {
                    android.util.Slog.d(TAG, "Failed to set game mode for package " + str + " as user " + handleIncomingUser + " is not started");
                    return;
                }
                com.android.server.app.GameManagerSettings gameManagerSettings = this.mSettings.get(java.lang.Integer.valueOf(handleIncomingUser));
                int gameModeLocked = gameManagerSettings.getGameModeLocked(str);
                gameManagerSettings.setGameModeLocked(str, i);
                updateInterventions(str, i, handleIncomingUser);
                synchronized (this.mGameModeListenerLock) {
                    for (android.app.IGameModeListener iGameModeListener : this.mGameModeListeners.keySet()) {
                        android.os.Binder.allowBlocking(iGameModeListener.asBinder());
                        try {
                            iGameModeListener.onGameModeChanged(str, gameModeLocked, i, handleIncomingUser);
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.w(TAG, "Cannot notify game mode change for listener added by " + this.mGameModeListeners.get(iGameModeListener));
                        }
                    }
                }
                sendUserMessage(handleIncomingUser, 1, EVENT_SET_GAME_MODE, 10000);
                sendUserMessage(handleIncomingUser, 6, EVENT_SET_GAME_MODE, 0);
                try {
                    i3 = this.mPackageManager.getPackageUidAsUser(str, handleIncomingUser);
                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                    android.util.Slog.d(TAG, "Cannot find the UID for package " + str + " under user " + handleIncomingUser);
                    i3 = -1;
                }
                com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.GAME_MODE_CHANGED, i3, android.os.Binder.getCallingUid(), gameModeToStatsdGameMode(gameModeLocked), gameModeToStatsdGameMode(i));
            } finally {
            }
        }
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_GAME_MODE")
    public boolean isAngleEnabled(java.lang.String str, int i) throws java.lang.SecurityException {
        int gameMode = getGameMode(str, i);
        if (gameMode == 0) {
            return false;
        }
        synchronized (this.mDeviceConfigLock) {
            try {
                com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration = this.mConfigs.get(str);
                if (gamePackageConfiguration == null) {
                    return false;
                }
                com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration gameModeConfiguration = gamePackageConfiguration.getGameModeConfiguration(gameMode);
                if (gameModeConfiguration == null) {
                    return false;
                }
                return gameModeConfiguration.getUseAngle();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getLoadingBoostDuration(java.lang.String str, int i) throws java.lang.SecurityException {
        com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration;
        com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration gameModeConfiguration;
        int gameMode = getGameMode(str, i);
        if (gameMode == 0) {
            return -1;
        }
        synchronized (this.mDeviceConfigLock) {
            gamePackageConfiguration = this.mConfigs.get(str);
        }
        if (gamePackageConfiguration == null || (gameModeConfiguration = gamePackageConfiguration.getGameModeConfiguration(gameMode)) == null) {
            return -1;
        }
        return gameModeConfiguration.getLoadingBoostDuration();
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_GAME_MODE")
    public void notifyGraphicsEnvironmentSetup(java.lang.String str, int i) throws java.lang.SecurityException {
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, true, "notifyGraphicsEnvironmentSetup", "com.android.server.app.GameManagerService");
        if (!isValidPackageName(str, handleIncomingUser)) {
            android.util.Slog.d(TAG, "No-op for attempt to notify graphics env setup for different packagethan caller with uid: " + android.os.Binder.getCallingUid());
            return;
        }
        if (getGameMode(str, handleIncomingUser) == 0) {
            android.util.Slog.d(TAG, "No-op for attempt to notify graphics env setup for non-game app: " + str);
            return;
        }
        int loadingBoostDuration = getLoadingBoostDuration(str, handleIncomingUser);
        if (loadingBoostDuration != -1) {
            if (loadingBoostDuration == 0 || loadingBoostDuration > 5000) {
                loadingBoostDuration = 5000;
            }
            if (this.mHandler.hasMessages(5)) {
                this.mHandler.removeMessages(5);
            } else {
                android.util.Slog.v(TAG, "Game loading power mode ON (loading boost on game start)");
                this.mPowerManagerInternal.setPowerMode(16, true);
            }
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(5), loadingBoostDuration);
        }
    }

    @android.annotation.RequiresPermission("android.permission.SET_GAME_SERVICE")
    public void setGameServiceProvider(@android.annotation.Nullable java.lang.String str) throws java.lang.SecurityException {
        checkPermission("android.permission.SET_GAME_SERVICE");
        if (this.mGameServiceController == null) {
            return;
        }
        this.mGameServiceController.setGameServiceProvider(str);
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_GAME_MODE")
    public void updateResolutionScalingFactor(java.lang.String str, int i, float f, int i2) throws java.lang.SecurityException, java.lang.IllegalArgumentException {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        synchronized (this.mLock) {
            if (!this.mSettings.containsKey(java.lang.Integer.valueOf(i2))) {
                throw new java.lang.IllegalArgumentException("User " + i2 + " wasn't started");
            }
        }
        setGameModeConfigOverride(str, i2, i, null, java.lang.Float.toString(f));
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_GAME_MODE")
    public float getResolutionScalingFactor(java.lang.String str, int i, int i2) throws java.lang.SecurityException, java.lang.IllegalArgumentException {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        synchronized (this.mLock) {
            if (!this.mSettings.containsKey(java.lang.Integer.valueOf(i2))) {
                throw new java.lang.IllegalArgumentException("User " + i2 + " wasn't started");
            }
        }
        return getResolutionScalingFactorInternal(str, i, i2);
    }

    float getResolutionScalingFactorInternal(java.lang.String str, int i, int i2) {
        com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration gameModeConfiguration;
        com.android.server.app.GameManagerService.GamePackageConfiguration config = getConfig(str, i2);
        if (config == null || (gameModeConfiguration = config.getGameModeConfiguration(i)) == null) {
            return -1.0f;
        }
        return gameModeConfiguration.getScaling();
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_GAME_MODE")
    public void updateCustomGameModeConfiguration(java.lang.String str, android.app.GameModeConfiguration gameModeConfiguration, int i) throws java.lang.SecurityException, java.lang.IllegalArgumentException {
        int i2;
        checkPermission("android.permission.MANAGE_GAME_MODE");
        if (!lambda$updateConfigsForUser$0(str, i)) {
            android.util.Slog.d(TAG, "No-op for attempt to update custom game mode for non-game app: " + str);
            return;
        }
        synchronized (this.mLock) {
            if (!this.mSettings.containsKey(java.lang.Integer.valueOf(i))) {
                throw new java.lang.IllegalArgumentException("User " + i + " wasn't started");
            }
        }
        synchronized (this.mLock) {
            try {
                if (this.mSettings.containsKey(java.lang.Integer.valueOf(i))) {
                    com.android.server.app.GameManagerSettings gameManagerSettings = this.mSettings.get(java.lang.Integer.valueOf(i));
                    com.android.server.app.GameManagerService.GamePackageConfiguration configOverride = gameManagerSettings.getConfigOverride(str);
                    if (configOverride == null) {
                        configOverride = new com.android.server.app.GameManagerService.GamePackageConfiguration(str);
                        gameManagerSettings.setConfigOverride(str, configOverride);
                    }
                    com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration orAddDefaultGameModeConfiguration = configOverride.getOrAddDefaultGameModeConfiguration(4);
                    float scaling = orAddDefaultGameModeConfiguration.getScaling();
                    int fps = orAddDefaultGameModeConfiguration.getFps();
                    orAddDefaultGameModeConfiguration.updateFromPublicGameModeConfig(gameModeConfiguration);
                    sendUserMessage(i, 1, EVENT_UPDATE_CUSTOM_GAME_MODE_CONFIG, 10000);
                    sendUserMessage(i, 6, EVENT_UPDATE_CUSTOM_GAME_MODE_CONFIG, 10000);
                    int gameMode = getGameMode(str, i);
                    if (gameMode == 4) {
                        updateInterventions(str, gameMode, i);
                    }
                    android.util.Slog.i(TAG, "Updated custom game mode config for package: " + str + " with FPS=" + orAddDefaultGameModeConfiguration.getFps() + ";Scaling=" + orAddDefaultGameModeConfiguration.getScaling() + " under user " + i);
                    try {
                        i2 = this.mPackageManager.getPackageUidAsUser(str, i);
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        android.util.Slog.d(TAG, "Cannot find the UID for package " + str + " under user " + i);
                        i2 = -1;
                    }
                    com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.GAME_MODE_CONFIGURATION_CHANGED, i2, android.os.Binder.getCallingUid(), gameModeToStatsdGameMode(4), scaling, gameModeConfiguration.getScalingFactor(), fps, gameModeConfiguration.getFpsOverride());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_GAME_MODE")
    public void addGameModeListener(@android.annotation.NonNull final android.app.IGameModeListener iGameModeListener) {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        try {
            final android.os.IBinder asBinder = iGameModeListener.asBinder();
            asBinder.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.app.GameManagerService.1
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    com.android.server.app.GameManagerService.this.removeGameModeListenerUnchecked(iGameModeListener);
                    asBinder.unlinkToDeath(this, 0);
                }
            }, 0);
            synchronized (this.mGameModeListenerLock) {
                this.mGameModeListeners.put(iGameModeListener, java.lang.Integer.valueOf(android.os.Binder.getCallingUid()));
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to link death recipient for IGameModeListener from caller " + android.os.Binder.getCallingUid() + ", abandoned its listener registration", e);
        }
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_GAME_MODE")
    public void removeGameModeListener(@android.annotation.NonNull android.app.IGameModeListener iGameModeListener) {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        removeGameModeListenerUnchecked(iGameModeListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeGameModeListenerUnchecked(android.app.IGameModeListener iGameModeListener) {
        synchronized (this.mGameModeListenerLock) {
            this.mGameModeListeners.remove(iGameModeListener);
        }
    }

    public void addGameStateListener(@android.annotation.NonNull final android.app.IGameStateListener iGameStateListener) {
        try {
            final android.os.IBinder asBinder = iGameStateListener.asBinder();
            asBinder.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.app.GameManagerService.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    com.android.server.app.GameManagerService.this.removeGameStateListenerUnchecked(iGameStateListener);
                    asBinder.unlinkToDeath(this, 0);
                }
            }, 0);
            synchronized (this.mGameStateListenerLock) {
                this.mGameStateListeners.put(iGameStateListener, java.lang.Integer.valueOf(android.os.Binder.getCallingUid()));
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to link death recipient for IGameStateListener from caller " + android.os.Binder.getCallingUid() + ", abandoned its listener registration", e);
        }
    }

    public void removeGameStateListener(@android.annotation.NonNull android.app.IGameStateListener iGameStateListener) {
        removeGameStateListenerUnchecked(iGameStateListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeGameStateListenerUnchecked(android.app.IGameStateListener iGameStateListener) {
        synchronized (this.mGameStateListenerLock) {
            this.mGameStateListeners.remove(iGameStateListener);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void onBootCompleted() {
        android.util.Slog.d(TAG, "onBootCompleted");
        if (this.mGameServiceController != null) {
            this.mGameServiceController.onBootComplete();
        }
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.app.GameManagerService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                    synchronized (com.android.server.app.GameManagerService.this.mLock) {
                        try {
                            java.util.Iterator it = com.android.server.app.GameManagerService.this.mSettings.entrySet().iterator();
                            while (it.hasNext()) {
                                int intValue = ((java.lang.Integer) ((java.util.Map.Entry) it.next()).getKey()).intValue();
                                com.android.server.app.GameManagerService.this.sendUserMessage(intValue, 1, com.android.server.app.GameManagerService.EVENT_RECEIVE_SHUTDOWN_INDENT, 0);
                                com.android.server.app.GameManagerService.this.sendUserMessage(intValue, 6, com.android.server.app.GameManagerService.EVENT_RECEIVE_SHUTDOWN_INDENT, 0);
                            }
                        } finally {
                        }
                    }
                }
            }
        }, new android.content.IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
        android.util.Slog.v(TAG, "Game loading power mode OFF (game manager service start/restart)");
        this.mPowerManagerInternal.setPowerMode(16, false);
        android.util.Slog.v(TAG, "Game power mode OFF (game manager service start/restart)");
        this.mPowerManagerInternal.setPowerMode(15, false);
        this.mGameDefaultFrameRateValue = this.mSysProps.getInt(PROPERTY_RO_SURFACEFLINGER_GAME_DEFAULT_FRAME_RATE, 60);
        android.util.Slog.v(TAG, "Game Default Frame Rate : " + this.mGameDefaultFrameRateValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUserMessage(int i, int i2, java.lang.String str, int i3) {
        if (!this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(i2, java.lang.Integer.valueOf(i)), i3)) {
            android.util.Slog.e(TAG, "Failed to send user message " + i2 + " on " + str);
        }
    }

    void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser, java.io.File file) {
        int userIdentifier = targetUser.getUserIdentifier();
        synchronized (this.mLock) {
            try {
                if (!this.mSettings.containsKey(java.lang.Integer.valueOf(userIdentifier))) {
                    com.android.server.app.GameManagerSettings gameManagerSettings = new com.android.server.app.GameManagerSettings(file);
                    this.mSettings.put(java.lang.Integer.valueOf(userIdentifier), gameManagerSettings);
                    gameManagerSettings.readPersistentDataLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        sendUserMessage(userIdentifier, 3, EVENT_ON_USER_STARTING, 0);
        if (this.mGameServiceController != null) {
            this.mGameServiceController.notifyUserStarted(targetUser);
        }
    }

    void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        if (this.mGameServiceController != null) {
            this.mGameServiceController.notifyUserUnlocking(targetUser);
        }
    }

    void onUserStopping(com.android.server.SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        synchronized (this.mLock) {
            try {
                if (this.mSettings.containsKey(java.lang.Integer.valueOf(userIdentifier))) {
                    sendUserMessage(userIdentifier, 2, EVENT_ON_USER_STOPPING, 0);
                    if (this.mGameServiceController != null) {
                        this.mGameServiceController.notifyUserStopped(targetUser);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onUserSwitching(com.android.server.SystemService.TargetUser targetUser, com.android.server.SystemService.TargetUser targetUser2) {
        sendUserMessage(targetUser2.getUserIdentifier(), 3, EVENT_ON_USER_SWITCHING, 0);
        if (this.mGameServiceController != null) {
            this.mGameServiceController.notifyNewForegroundUser(targetUser2);
        }
    }

    private void resetFps(java.lang.String str, int i) {
        try {
            setGameModeFrameRateOverride(this.mPackageManager.getPackageUidAsUser(str, i), com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int modeToBitmask(int i) {
        return 1 << i;
    }

    private boolean bitFieldContainsModeBitmask(int i, int i2) {
        return (i & modeToBitmask(i2)) != 0;
    }

    @android.annotation.RequiresPermission("android.permission.WRITE_SECURE_SETTINGS")
    private void updateUseAngle(java.lang.String str, int i) {
    }

    private void updateFps(com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration, java.lang.String str, int i, int i2) {
        if (gamePackageConfiguration.getGameModeConfiguration(i) == null) {
            android.util.Slog.d(TAG, "Game mode " + i + " not found for " + str);
            return;
        }
        try {
            setGameModeFrameRateOverride(this.mPackageManager.getPackageUidAsUser(str, i2), r1.getFps());
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        }
    }

    private void updateInterventions(java.lang.String str, int i, int i2) {
        com.android.server.app.GameManagerService.GamePackageConfiguration config = getConfig(str, i2);
        if (i == 1 || i == 0 || config == null || config.willGamePerformOptimizations(i) || config.getGameModeConfiguration(i) == null) {
            resetFps(str, i2);
            if (config == null) {
                android.util.Slog.v(TAG, "Package configuration not found for " + str);
                return;
            }
        } else {
            updateFps(config, str, i, i2);
        }
        updateUseAngle(str, i);
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_GAME_MODE")
    @com.android.internal.annotations.VisibleForTesting
    public void setGameModeConfigOverride(java.lang.String str, int i, int i2, java.lang.String str2, java.lang.String str3) throws java.lang.SecurityException {
        int i3;
        checkPermission("android.permission.MANAGE_GAME_MODE");
        try {
            i3 = this.mPackageManager.getPackageUidAsUser(str, i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.d(TAG, "Cannot find the UID for package " + str + " under user " + i);
            i3 = -1;
        }
        com.android.server.app.GameManagerService.GamePackageConfiguration config = getConfig(str, i);
        if (config != null && config.getGameModeConfiguration(i2) != null) {
            com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration gameModeConfiguration = config.getGameModeConfiguration(i2);
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.GAME_MODE_CONFIGURATION_CHANGED, i3, android.os.Binder.getCallingUid(), gameModeToStatsdGameMode(i2), gameModeConfiguration.getScaling(), str3 == null ? gameModeConfiguration.getScaling() : java.lang.Float.parseFloat(str3), gameModeConfiguration.getFps(), str2 == null ? gameModeConfiguration.getFps() : java.lang.Integer.parseInt(str2));
        } else {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.GAME_MODE_CONFIGURATION_CHANGED, i3, android.os.Binder.getCallingUid(), gameModeToStatsdGameMode(i2), -1.0f, str3 == null ? -1.0f : java.lang.Float.parseFloat(str3), 0, str2 == null ? 0 : java.lang.Integer.parseInt(str2));
        }
        synchronized (this.mLock) {
            try {
                if (this.mSettings.containsKey(java.lang.Integer.valueOf(i))) {
                    com.android.server.app.GameManagerSettings gameManagerSettings = this.mSettings.get(java.lang.Integer.valueOf(i));
                    com.android.server.app.GameManagerService.GamePackageConfiguration configOverride = gameManagerSettings.getConfigOverride(str);
                    if (configOverride == null) {
                        configOverride = new com.android.server.app.GameManagerService.GamePackageConfiguration(str);
                        gameManagerSettings.setConfigOverride(str, configOverride);
                    }
                    com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration orAddDefaultGameModeConfiguration = configOverride.getOrAddDefaultGameModeConfiguration(i2);
                    if (str2 != null) {
                        orAddDefaultGameModeConfiguration.setFpsStr(str2);
                    } else {
                        orAddDefaultGameModeConfiguration.setFpsStr("");
                    }
                    if (str3 != null) {
                        orAddDefaultGameModeConfiguration.setScaling(java.lang.Float.parseFloat(str3));
                    }
                    android.util.Slog.i(TAG, "Package Name: " + str + " FPS: " + java.lang.String.valueOf(orAddDefaultGameModeConfiguration.getFps()) + " Scaling: " + orAddDefaultGameModeConfiguration.getScaling());
                    setGameMode(str, i2, i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_GAME_MODE")
    @com.android.internal.annotations.VisibleForTesting
    public void resetGameModeConfigOverride(java.lang.String str, int i, int i2) throws java.lang.SecurityException {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        synchronized (this.mLock) {
            try {
                if (this.mSettings.containsKey(java.lang.Integer.valueOf(i))) {
                    com.android.server.app.GameManagerSettings gameManagerSettings = this.mSettings.get(java.lang.Integer.valueOf(i));
                    if (i2 != -1) {
                        com.android.server.app.GameManagerService.GamePackageConfiguration configOverride = gameManagerSettings.getConfigOverride(str);
                        if (configOverride == null) {
                            return;
                        }
                        if (!bitFieldContainsModeBitmask(configOverride.getAvailableGameModesBitfield(), i2)) {
                            return;
                        }
                        configOverride.removeModeConfig(i2);
                        if (!configOverride.hasActiveGameModeConfig()) {
                            gameManagerSettings.removeConfigOverride(str);
                        }
                    } else {
                        gameManagerSettings.removeConfigOverride(str);
                    }
                    int gameMode = getGameMode(str, i);
                    if (gameMode != getNewGameMode(gameMode, getConfig(str, i))) {
                        setGameMode(str, 1, i);
                    } else {
                        setGameMode(str, gameMode, i);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int getNewGameMode(int i, com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration) {
        if (gamePackageConfiguration == null) {
            return 1;
        }
        if (!bitFieldContainsModeBitmask(gamePackageConfiguration.getAvailableGameModesBitfield() & (~modeToBitmask(0)), i)) {
            i = 1;
        }
        return i;
    }

    @android.annotation.RequiresPermission("android.permission.QUERY_ALL_PACKAGES")
    public java.lang.String getInterventionList(java.lang.String str, int i) {
        checkPermission("android.permission.QUERY_ALL_PACKAGES");
        com.android.server.app.GameManagerService.GamePackageConfiguration config = getConfig(str, i);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (config == null) {
            sb.append("\n No intervention found for package ");
            sb.append(str);
            return sb.toString();
        }
        sb.append("\n");
        sb.append(config.toString());
        return sb.toString();
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateConfigsForUser(final int i, boolean z, java.lang.String... strArr) {
        com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration;
        if (z) {
            strArr = (java.lang.String[]) java.util.Arrays.stream(strArr).filter(new java.util.function.Predicate() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$updateConfigsForUser$0;
                    lambda$updateConfigsForUser$0 = com.android.server.app.GameManagerService.this.lambda$updateConfigsForUser$0(i, (java.lang.String) obj);
                    return lambda$updateConfigsForUser$0;
                }
            }).toArray(new java.util.function.IntFunction() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda4
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i2) {
                    java.lang.String[] lambda$updateConfigsForUser$1;
                    lambda$updateConfigsForUser$1 = com.android.server.app.GameManagerService.lambda$updateConfigsForUser$1(i2);
                    return lambda$updateConfigsForUser$1;
                }
            });
        }
        try {
            synchronized (this.mDeviceConfigLock) {
                try {
                    for (java.lang.String str : strArr) {
                        com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration2 = new com.android.server.app.GameManagerService.GamePackageConfiguration(this.mPackageManager, str, i);
                        if (gamePackageConfiguration2.isActive()) {
                            android.util.Slog.v(TAG, "Adding config: " + gamePackageConfiguration2.toString());
                            this.mConfigs.put(str, gamePackageConfiguration2);
                        } else {
                            android.util.Slog.v(TAG, "Inactive package config for " + gamePackageConfiguration2.getPackageName() + ":" + gamePackageConfiguration2.toString());
                            this.mConfigs.remove(str);
                        }
                    }
                } finally {
                }
            }
            synchronized (this.mLock) {
                try {
                    if (this.mSettings.containsKey(java.lang.Integer.valueOf(i))) {
                        for (java.lang.String str2 : strArr) {
                            int gameMode = getGameMode(str2, i);
                            synchronized (this.mDeviceConfigLock) {
                                gamePackageConfiguration = this.mConfigs.get(str2);
                            }
                            int newGameMode = getNewGameMode(gameMode, gamePackageConfiguration);
                            if (newGameMode != gameMode) {
                                setGameMode(str2, newGameMode, i);
                            } else {
                                updateInterventions(str2, gameMode, i);
                            }
                        }
                        sendUserMessage(i, 6, "UPDATE_CONFIGS_FOR_USERS", 0);
                    }
                } finally {
                }
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to update configs for user " + i + ": " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$updateConfigsForUser$1(int i) {
        return new java.lang.String[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeGameModeInterventionsToFile(int i) {
        java.io.FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.mGameModeInterventionListFile.startWrite();
            java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(fileOutputStream, java.nio.charset.Charset.defaultCharset()));
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (java.lang.String str : getInstalledGamePackageNamesByAllUsers(i)) {
                com.android.server.app.GameManagerService.GamePackageConfiguration config = getConfig(str, i);
                if (config != null) {
                    sb.append(str);
                    sb.append("\t");
                    sb.append(this.mPackageManager.getPackageUidAsUser(str, i));
                    sb.append("\t");
                    sb.append(getGameMode(str, i));
                    sb.append("\t");
                    for (int i2 : config.getAvailableGameModes()) {
                        com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration gameModeConfiguration = config.getGameModeConfiguration(i2);
                        if (gameModeConfiguration != null) {
                            sb.append(i2);
                            sb.append("\t");
                            sb.append(android.text.TextUtils.formatSimple("angle=%d", new java.lang.Object[]{java.lang.Integer.valueOf(gameModeConfiguration.getUseAngle() ? 1 : 0)}));
                            sb.append(",");
                            float scaling = gameModeConfiguration.getScaling();
                            sb.append("scaling=");
                            sb.append(scaling);
                            sb.append(",");
                            sb.append(android.text.TextUtils.formatSimple("fps=%d", new java.lang.Object[]{java.lang.Integer.valueOf(gameModeConfiguration.getFps())}));
                            sb.append("\t");
                        }
                    }
                    sb.append("\n");
                }
            }
            bufferedWriter.append((java.lang.CharSequence) sb);
            bufferedWriter.flush();
            android.os.FileUtils.sync(fileOutputStream);
            this.mGameModeInterventionListFile.finishWrite(fileOutputStream);
        } catch (java.lang.Exception e) {
            this.mGameModeInterventionListFile.failWrite(fileOutputStream);
            android.util.Slog.wtf(TAG, "Failed to write game_mode_intervention.list, exception " + e);
        }
    }

    private int[] getAllUserIds(int i) {
        java.util.List users = this.mUserManager.getUsers();
        int size = users.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = ((android.content.pm.UserInfo) users.get(i2)).id;
        }
        if (i != -1) {
            return com.android.internal.util.ArrayUtils.appendInt(iArr, i);
        }
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String[] getInstalledGamePackageNames(int i) {
        return (java.lang.String[]) this.mPackageManager.getInstalledPackagesAsUser(0, i).stream().filter(new java.util.function.Predicate() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getInstalledGamePackageNames$2;
                lambda$getInstalledGamePackageNames$2 = com.android.server.app.GameManagerService.lambda$getInstalledGamePackageNames$2((android.content.pm.PackageInfo) obj);
                return lambda$getInstalledGamePackageNames$2;
            }
        }).map(new java.util.function.Function() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String str;
                str = ((android.content.pm.PackageInfo) obj).packageName;
                return str;
            }
        }).toArray(new java.util.function.IntFunction() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i2) {
                java.lang.String[] lambda$getInstalledGamePackageNames$4;
                lambda$getInstalledGamePackageNames$4 = com.android.server.app.GameManagerService.lambda$getInstalledGamePackageNames$4(i2);
                return lambda$getInstalledGamePackageNames$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getInstalledGamePackageNames$2(android.content.pm.PackageInfo packageInfo) {
        return packageInfo.applicationInfo != null && packageInfo.applicationInfo.category == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$getInstalledGamePackageNames$4(int i) {
        return new java.lang.String[i];
    }

    private java.util.List<java.lang.String> getInstalledGamePackageNamesByAllUsers(int i) {
        java.util.HashSet hashSet = new java.util.HashSet();
        for (int i2 : getAllUserIds(i)) {
            hashSet.addAll(java.util.Arrays.asList(getInstalledGamePackageNames(i2)));
        }
        return new java.util.ArrayList(hashSet);
    }

    public com.android.server.app.GameManagerService.GamePackageConfiguration getConfig(java.lang.String str, int i) {
        com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration;
        com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration2;
        synchronized (this.mDeviceConfigLock) {
            gamePackageConfiguration = this.mConfigs.get(str);
        }
        synchronized (this.mLock) {
            try {
                if (!this.mSettings.containsKey(java.lang.Integer.valueOf(i))) {
                    gamePackageConfiguration2 = null;
                } else {
                    gamePackageConfiguration2 = this.mSettings.get(java.lang.Integer.valueOf(i)).getConfigOverride(str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (gamePackageConfiguration2 == null || gamePackageConfiguration == null) {
            return gamePackageConfiguration2 == null ? gamePackageConfiguration : gamePackageConfiguration2;
        }
        return gamePackageConfiguration.copyAndApplyOverride(gamePackageConfiguration2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerPackageReceiver() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiverForAllUsers(new android.content.BroadcastReceiver() { // from class: com.android.server.app.GameManagerService.4
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.Intent intent) {
                int sendingUserId;
                char c;
                android.net.Uri data = intent.getData();
                try {
                    sendingUserId = getSendingUserId();
                } catch (java.lang.NullPointerException e) {
                    android.util.Slog.e(com.android.server.app.GameManagerService.TAG, "Failed to get package name for new package");
                }
                if (sendingUserId != android.app.ActivityManager.getCurrentUser()) {
                    return;
                }
                java.lang.String schemeSpecificPart = data.getSchemeSpecificPart();
                try {
                    if (com.android.server.app.GameManagerService.this.mPackageManager.getApplicationInfoAsUser(schemeSpecificPart, 131072, sendingUserId).category != 0) {
                        return;
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                }
                java.lang.String action = intent.getAction();
                switch (action.hashCode()) {
                    case 525384130:
                        if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1544582882:
                        if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                            c = 0;
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
                        com.android.server.app.GameManagerService.this.updateConfigsForUser(sendingUserId, true, schemeSpecificPart);
                        return;
                    case 1:
                        if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            synchronized (com.android.server.app.GameManagerService.this.mDeviceConfigLock) {
                                com.android.server.app.GameManagerService.this.mConfigs.remove(schemeSpecificPart);
                            }
                            synchronized (com.android.server.app.GameManagerService.this.mLock) {
                                try {
                                    if (com.android.server.app.GameManagerService.this.mSettings.containsKey(java.lang.Integer.valueOf(sendingUserId))) {
                                        ((com.android.server.app.GameManagerSettings) com.android.server.app.GameManagerService.this.mSettings.get(java.lang.Integer.valueOf(sendingUserId))).removeGame(schemeSpecificPart);
                                    }
                                    com.android.server.app.GameManagerService.this.sendUserMessage(sendingUserId, 1, "android.intent.action.PACKAGE_REMOVED", 10000);
                                    com.android.server.app.GameManagerService.this.sendUserMessage(sendingUserId, 6, "android.intent.action.PACKAGE_REMOVED", 10000);
                                } finally {
                                }
                            }
                            return;
                        }
                        return;
                    default:
                        return;
                }
                android.util.Slog.e(com.android.server.app.GameManagerService.TAG, "Failed to get package name for new package");
            }
        }, intentFilter, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerDeviceConfigListener() {
        this.mDeviceConfigListener = new com.android.server.app.GameManagerService.DeviceConfigListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void publishLocalService() {
        com.android.server.app.GameManagerService.LocalService localService = new com.android.server.app.GameManagerService.LocalService();
        ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).registerCompatScaleProvider(1, localService);
        com.android.server.LocalServices.addService(android.app.GameManagerInternal.class, localService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerStatsCallbacks() {
        android.app.StatsManager statsManager = (android.app.StatsManager) this.mContext.getSystemService(android.app.StatsManager.class);
        statsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.GAME_MODE_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, new android.app.StatsManager.StatsPullAtomCallback() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda5
            public final int onPullAtom(int i, java.util.List list) {
                int onPullAtom;
                onPullAtom = com.android.server.app.GameManagerService.this.onPullAtom(i, list);
                return onPullAtom;
            }
        });
        statsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.GAME_MODE_CONFIGURATION, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, new android.app.StatsManager.StatsPullAtomCallback() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda5
            public final int onPullAtom(int i, java.util.List list) {
                int onPullAtom;
                onPullAtom = com.android.server.app.GameManagerService.this.onPullAtom(i, list);
                return onPullAtom;
            }
        });
        statsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.GAME_MODE_LISTENER, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, new android.app.StatsManager.StatsPullAtomCallback() { // from class: com.android.server.app.GameManagerService$$ExternalSyntheticLambda5
            public final int onPullAtom(int i, java.util.List list) {
                int onPullAtom;
                onPullAtom = com.android.server.app.GameManagerService.this.onPullAtom(i, list);
                return onPullAtom;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int onPullAtom(int i, @android.annotation.NonNull java.util.List<android.util.StatsEvent> list) {
        java.util.Set<java.lang.String> keySet;
        int i2;
        if (i == 10165 || i == 10166) {
            int currentUser = android.app.ActivityManager.getCurrentUser();
            synchronized (this.mDeviceConfigLock) {
                keySet = this.mConfigs.keySet();
            }
            for (java.lang.String str : keySet) {
                com.android.server.app.GameManagerService.GamePackageConfiguration config = getConfig(str, currentUser);
                if (config != null) {
                    try {
                        i2 = this.mPackageManager.getPackageUidAsUser(str, currentUser);
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        android.util.Slog.d(TAG, "Cannot find UID for package " + str + " under user handle id " + currentUser);
                        i2 = -1;
                    }
                    if (i == 10165) {
                        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.GAME_MODE_INFO, i2, gameModesToStatsdGameModes(config.getOverriddenGameModes()), gameModesToStatsdGameModes(config.getAvailableGameModes())));
                    } else if (i == 10166) {
                        for (int i3 : config.getAvailableGameModes()) {
                            com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration gameModeConfiguration = config.getGameModeConfiguration(i3);
                            if (gameModeConfiguration != null) {
                                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.GAME_MODE_CONFIGURATION, i2, gameModeToStatsdGameMode(i3), gameModeConfiguration.getFps(), gameModeConfiguration.getScaling()));
                            }
                        }
                    }
                }
            }
        } else if (i == 10167) {
            synchronized (this.mGameModeListenerLock) {
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.GAME_MODE_LISTENER, this.mGameModeListeners.size()));
            }
        }
        return 0;
    }

    private static int[] gameModesToStatsdGameModes(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int[] iArr2 = new int[iArr.length];
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            iArr2[i2] = gameModeToStatsdGameMode(iArr[i]);
            i++;
            i2++;
        }
        return iArr2;
    }

    private static int gameModeToStatsdGameMode(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            default:
                return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int gameStateModeToStatsdGameState(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            default:
                return 0;
        }
    }

    private static com.android.server.ServiceThread createServiceThread() {
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(TAG, 10, true);
        serviceThread.start();
        return serviceThread;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setGameModeFrameRateOverride(int i, float f) {
        nativeSetGameModeFrameRateOverride(i, f);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setGameDefaultFrameRateOverride(int i, float f) {
        android.util.Slog.v(TAG, "setDefaultFrameRateOverride : " + i + " , " + f);
        nativeSetGameDefaultFrameRateOverride(i, f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getGameDefaultFrameRate(boolean z) {
        return (android.server.app.Flags.gameDefaultFrameRate() && z) ? this.mGameDefaultFrameRateValue : com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_GAME_MODE")
    public void toggleGameDefaultFrameRate(boolean z) {
        toggleGameDefaultFrameRate_enforcePermission();
        if (android.server.app.Flags.gameDefaultFrameRate()) {
            android.util.Slog.v(TAG, "toggleGameDefaultFrameRate : " + z);
            toggleGameDefaultFrameRateUnchecked(z);
        }
    }

    private void toggleGameDefaultFrameRateUnchecked(boolean z) {
        synchronized (this.mLock) {
            try {
                if (z) {
                    this.mSysProps.set(PROPERTY_DEBUG_GFX_GAME_DEFAULT_FRAME_RATE_DISABLED, "false");
                } else {
                    this.mSysProps.set(PROPERTY_DEBUG_GFX_GAME_DEFAULT_FRAME_RATE_DISABLED, "true");
                }
            } finally {
            }
        }
        synchronized (this.mUidObserverLock) {
            try {
                java.util.Iterator<java.lang.Integer> it = this.mGameForegroundUids.iterator();
                while (it.hasNext()) {
                    setGameDefaultFrameRateOverride(it.next().intValue(), getGameDefaultFrameRate(z));
                }
            } finally {
            }
        }
    }

    final class MyUidObserver extends android.app.UidObserver {
        MyUidObserver() {
        }

        public void onUidGone(int i, boolean z) {
            synchronized (com.android.server.app.GameManagerService.this.mUidObserverLock) {
                handleUidMovedOffTop(i);
            }
        }

        public void onUidStateChanged(int i, int i2, long j, int i3) {
            switch (i2) {
                case 2:
                    handleUidMovedToTop(i);
                    break;
                default:
                    handleUidMovedOffTop(i);
                    break;
            }
        }

        private void handleUidMovedToTop(int i) {
            java.lang.String[] packagesForUid = com.android.server.app.GameManagerService.this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid == null || packagesForUid.length == 0) {
                return;
            }
            final int userId = com.android.server.app.GameManagerService.this.mContext.getUserId();
            boolean noneMatch = java.util.Arrays.stream(packagesForUid).noneMatch(new java.util.function.Predicate() { // from class: com.android.server.app.GameManagerService$MyUidObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$handleUidMovedToTop$0;
                    lambda$handleUidMovedToTop$0 = com.android.server.app.GameManagerService.MyUidObserver.this.lambda$handleUidMovedToTop$0(userId, (java.lang.String) obj);
                    return lambda$handleUidMovedToTop$0;
                }
            });
            synchronized (com.android.server.app.GameManagerService.this.mUidObserverLock) {
                try {
                    if (noneMatch) {
                        if (android.server.app.Flags.disableGameModeWhenAppTop()) {
                            if (!com.android.server.app.GameManagerService.this.mGameForegroundUids.isEmpty() && com.android.server.app.GameManagerService.this.mNonGameForegroundUids.isEmpty()) {
                                android.util.Slog.v(com.android.server.app.GameManagerService.TAG, "Game power mode OFF (first non-game in foreground)");
                                com.android.server.app.GameManagerService.this.mPowerManagerInternal.setPowerMode(15, false);
                            }
                            com.android.server.app.GameManagerService.this.mNonGameForegroundUids.add(java.lang.Integer.valueOf(i));
                        }
                        return;
                    }
                    if (com.android.server.app.GameManagerService.this.mGameForegroundUids.isEmpty() && (!android.server.app.Flags.disableGameModeWhenAppTop() || com.android.server.app.GameManagerService.this.mNonGameForegroundUids.isEmpty())) {
                        android.util.Slog.v(com.android.server.app.GameManagerService.TAG, "Game power mode ON (first game in foreground)");
                        com.android.server.app.GameManagerService.this.mPowerManagerInternal.setPowerMode(15, true);
                    }
                    com.android.server.app.GameManagerService.this.setGameDefaultFrameRateOverride(i, com.android.server.app.GameManagerService.this.getGameDefaultFrameRate(com.android.server.app.GameManagerService.this.mSysProps.getBoolean(com.android.server.app.GameManagerService.PROPERTY_DEBUG_GFX_GAME_DEFAULT_FRAME_RATE_DISABLED, false) ? false : true));
                    com.android.server.app.GameManagerService.this.mGameForegroundUids.add(java.lang.Integer.valueOf(i));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$handleUidMovedToTop$0(int i, java.lang.String str) {
            return com.android.server.app.GameManagerService.this.lambda$updateConfigsForUser$0(str, i);
        }

        private void handleUidMovedOffTop(int i) {
            synchronized (com.android.server.app.GameManagerService.this.mUidObserverLock) {
                try {
                    if (com.android.server.app.GameManagerService.this.mGameForegroundUids.contains(java.lang.Integer.valueOf(i))) {
                        com.android.server.app.GameManagerService.this.mGameForegroundUids.remove(java.lang.Integer.valueOf(i));
                        if (com.android.server.app.GameManagerService.this.mGameForegroundUids.isEmpty()) {
                            if (android.server.app.Flags.disableGameModeWhenAppTop()) {
                                if (com.android.server.app.GameManagerService.this.mNonGameForegroundUids.isEmpty()) {
                                }
                            }
                            android.util.Slog.v(com.android.server.app.GameManagerService.TAG, "Game power mode OFF (no games in foreground)");
                            com.android.server.app.GameManagerService.this.mPowerManagerInternal.setPowerMode(15, false);
                        }
                    } else if (android.server.app.Flags.disableGameModeWhenAppTop() && com.android.server.app.GameManagerService.this.mNonGameForegroundUids.contains(java.lang.Integer.valueOf(i))) {
                        com.android.server.app.GameManagerService.this.mNonGameForegroundUids.remove(java.lang.Integer.valueOf(i));
                        if (com.android.server.app.GameManagerService.this.mNonGameForegroundUids.isEmpty() && !com.android.server.app.GameManagerService.this.mGameForegroundUids.isEmpty()) {
                            android.util.Slog.v(com.android.server.app.GameManagerService.TAG, "Game power mode ON (only games in foreground)");
                            com.android.server.app.GameManagerService.this.mPowerManagerInternal.setPowerMode(15, true);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
