package com.android.server.contentcapture;

/* loaded from: classes.dex */
public class ContentCaptureManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.contentcapture.ContentCaptureManagerService, com.android.server.contentcapture.ContentCapturePerUserService> {
    private static final java.lang.String CONTENT_PROTECTION_GROUP_CONFIG_SEPARATOR_GROUP = ";";
    private static final java.lang.String CONTENT_PROTECTION_GROUP_CONFIG_SEPARATOR_VALUE = ",";
    private static final int DATA_SHARE_BYTE_BUFFER_LENGTH = 1024;
    private static final int EVENT__DATA_SHARE_ERROR_CONCURRENT_REQUEST = 14;
    private static final int EVENT__DATA_SHARE_ERROR_TIMEOUT_INTERRUPTED = 15;
    private static final int EVENT__DATA_SHARE_WRITE_FINISHED = 9;
    private static final int MAX_CONCURRENT_FILE_SHARING_REQUESTS = 10;
    private static final int MAX_DATA_SHARE_FILE_DESCRIPTORS_TTL_MS = 300000;
    private static final int MAX_TEMP_SERVICE_DURATION_MS = 120000;
    static final java.lang.String RECEIVER_BUNDLE_EXTRA_SESSIONS = "sessions";
    private static final java.lang.String TAG = com.android.server.contentcapture.ContentCaptureManagerService.class.getSimpleName();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.app.ActivityManagerInternal mAm;
    private final android.os.RemoteCallbackList<android.view.contentcapture.IContentCaptureOptionsCallback> mCallbacks;
    private final com.android.server.contentcapture.ContentCaptureManagerService.ContentCaptureManagerServiceStub mContentCaptureManagerServiceStub;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.contentprotection.ContentProtectionAllowlistManager mContentProtectionAllowlistManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.contentprotection.ContentProtectionConsentManager mContentProtectionConsentManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.content.ComponentName mContentProtectionServiceComponentName;
    private final java.util.concurrent.Executor mDataShareExecutor;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long mDevCfgContentProtectionAllowlistDelayMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long mDevCfgContentProtectionAllowlistTimeoutMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long mDevCfgContentProtectionAutoDisconnectTimeoutMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int mDevCfgContentProtectionBufferSize;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    java.util.List<java.util.List<java.lang.String>> mDevCfgContentProtectionOptionalGroups;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int mDevCfgContentProtectionOptionalGroupsThreshold;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    java.util.List<java.util.List<java.lang.String>> mDevCfgContentProtectionRequiredGroups;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mDevCfgDisableFlushForViewTreeAppearing;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mDevCfgEnableContentProtectionReceiver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int mDevCfgIdleFlushingFrequencyMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int mDevCfgIdleUnbindTimeoutMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int mDevCfgLogHistorySize;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int mDevCfgLoggingLevel;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int mDevCfgMaxBufferSize;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int mDevCfgTextChangeFlushingFrequencyMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private boolean mDisabledByDeviceConfig;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.util.SparseBooleanArray mDisabledBySettings;
    final com.android.server.contentcapture.ContentCaptureManagerService.GlobalContentCaptureOptions mGlobalContentCaptureOptions;
    private final android.os.Handler mHandler;
    private final com.android.server.contentcapture.ContentCaptureManagerService.LocalService mLocalService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Set<java.lang.String> mPackagesWithShareRequests;

    @android.annotation.Nullable
    final android.util.LocalLog mRequestsHistory;

    public ContentCaptureManagerService(@android.annotation.NonNull android.content.Context context) {
        super(context, new com.android.server.infra.FrameworkResourcesServiceNameResolver(context, android.R.string.config_defaultAppPredictionService), "no_content_capture", 1);
        this.mLocalService = new com.android.server.contentcapture.ContentCaptureManagerService.LocalService();
        this.mContentCaptureManagerServiceStub = new com.android.server.contentcapture.ContentCaptureManagerService.ContentCaptureManagerServiceStub();
        this.mDataShareExecutor = java.util.concurrent.Executors.newCachedThreadPool();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        this.mPackagesWithShareRequests = new java.util.HashSet();
        this.mCallbacks = new android.os.RemoteCallbackList<>();
        this.mGlobalContentCaptureOptions = new com.android.server.contentcapture.ContentCaptureManagerService.GlobalContentCaptureOptions();
        this.mDevCfgContentProtectionRequiredGroups = android.view.contentcapture.ContentCaptureManager.DEFAULT_CONTENT_PROTECTION_REQUIRED_GROUPS;
        this.mDevCfgContentProtectionOptionalGroups = android.view.contentcapture.ContentCaptureManager.DEFAULT_CONTENT_PROTECTION_OPTIONAL_GROUPS;
        android.provider.DeviceConfig.addOnPropertiesChangedListener("content_capture", android.app.ActivityThread.currentApplication().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.contentcapture.ContentCaptureManagerService$$ExternalSyntheticLambda1
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.contentcapture.ContentCaptureManagerService.this.lambda$new$0(properties);
            }
        });
        setDeviceConfigProperties();
        if (this.mDevCfgLogHistorySize > 0) {
            if (this.debug) {
                android.util.Slog.d(TAG, "log history size: " + this.mDevCfgLogHistorySize);
            }
            this.mRequestsHistory = new android.util.LocalLog(this.mDevCfgLogHistorySize);
        } else {
            if (this.debug) {
                android.util.Slog.d(TAG, "disabled log history because size is " + this.mDevCfgLogHistorySize);
            }
            this.mRequestsHistory = null;
        }
        java.util.List<android.content.pm.UserInfo> supportedUsers = getSupportedUsers();
        for (int i = 0; i < supportedUsers.size(); i++) {
            int i2 = supportedUsers.get(i).id;
            if (!isEnabledBySettings(i2)) {
                android.util.Slog.i(TAG, "user " + i2 + " disabled by settings");
                if (this.mDisabledBySettings == null) {
                    this.mDisabledBySettings = new android.util.SparseBooleanArray(1);
                }
                this.mDisabledBySettings.put(i2, true);
            }
            this.mGlobalContentCaptureOptions.setServiceInfo(i2, this.mServiceNameResolver.getServiceName(i2), this.mServiceNameResolver.isTemporary(i2));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.contentcapture.ContentCapturePerUserService newServiceLocked(int i, boolean z) {
        return new com.android.server.contentcapture.ContentCapturePerUserService(this, this.mLock, z, i);
    }

    @Override // com.android.server.SystemService
    public boolean isUserSupported(com.android.server.SystemService.TargetUser targetUser) {
        return targetUser.isFull() || targetUser.isProfile();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("content_capture", this.mContentCaptureManagerServiceStub);
        publishLocalService(com.android.server.contentcapture.ContentCaptureManagerInternal.class, this.mLocalService);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public void onServiceRemoved(@android.annotation.NonNull com.android.server.contentcapture.ContentCapturePerUserService contentCapturePerUserService, int i) {
        contentCapturePerUserService.destroyLocked();
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageUpdatingLocked(int i) {
        com.android.server.contentcapture.ContentCapturePerUserService serviceForUserLocked = getServiceForUserLocked(i);
        if (serviceForUserLocked != null) {
            serviceForUserLocked.onPackageUpdatingLocked();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageUpdatedLocked(int i) {
        com.android.server.contentcapture.ContentCapturePerUserService serviceForUserLocked = getServiceForUserLocked(i);
        if (serviceForUserLocked != null) {
            serviceForUserLocked.onPackageUpdatedLocked();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServiceNameChanged(int i, @android.annotation.NonNull java.lang.String str, boolean z) {
        this.mGlobalContentCaptureOptions.setServiceInfo(i, str, z);
        super.onServiceNameChanged(i, str, z);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.MANAGE_CONTENT_CAPTURE", TAG);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected int getMaximumTemporaryServiceDurationMs() {
        return MAX_TEMP_SERVICE_DURATION_MS;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void registerForExtraSettingsChanges(@android.annotation.NonNull android.content.ContentResolver contentResolver, @android.annotation.NonNull android.database.ContentObserver contentObserver) {
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("content_capture_enabled"), false, contentObserver, -1);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onSettingsChanged(int i, @android.annotation.NonNull java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -322385022:
                if (str.equals("content_capture_enabled")) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                setContentCaptureFeatureEnabledBySettingsForUser(i, isEnabledBySettings(i));
                break;
            default:
                android.util.Slog.w(TAG, "Unexpected property (" + str + "); updating cache instead");
                break;
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected boolean isDisabledLocked(int i) {
        return this.mDisabledByDeviceConfig || isDisabledBySettingsLocked(i) || super.isDisabledLocked(i);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void assertCalledByPackageOwner(@android.annotation.NonNull java.lang.String str) {
        try {
            super.assertCalledByPackageOwner(str);
        } catch (java.lang.SecurityException e) {
            int callingUid = android.os.Binder.getCallingUid();
            android.service.voice.VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity hotwordDetectionServiceIdentity = ((android.service.voice.VoiceInteractionManagerInternal) com.android.server.LocalServices.getService(android.service.voice.VoiceInteractionManagerInternal.class)).getHotwordDetectionServiceIdentity();
            if (callingUid != hotwordDetectionServiceIdentity.getIsolatedUid()) {
                super.assertCalledByPackageOwner(str);
                return;
            }
            java.lang.String[] packagesForUid = getContext().getPackageManager().getPackagesForUid(hotwordDetectionServiceIdentity.getOwnerUid());
            if (packagesForUid != null) {
                for (java.lang.String str2 : packagesForUid) {
                    if (str.equals(str2)) {
                        return;
                    }
                }
            }
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDisabledBySettingsLocked(int i) {
        return this.mDisabledBySettings != null && this.mDisabledBySettings.get(i);
    }

    private boolean isEnabledBySettings(int i) {
        return android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "content_capture_enabled", 1, i) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* renamed from: onDeviceConfigChange, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
        char c;
        for (java.lang.String str : properties.getKeyset()) {
            switch (str.hashCode()) {
                case -2119665698:
                    if (str.equals("disable_flush_for_view_tree_appearing")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -2017771757:
                    if (str.equals("enable_content_protection_receiver")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -1970239836:
                    if (str.equals("logging_level")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1455054002:
                    if (str.equals("content_protection_optional_groups_config")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -1293819494:
                    if (str.equals("content_protection_allowlist_delay_ms")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -801940800:
                    if (str.equals("content_protection_buffer_size")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -302650995:
                    if (str.equals("service_explicitly_enabled")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -148969820:
                    if (str.equals("text_change_flush_frequency")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -33314483:
                    if (str.equals("content_protection_required_groups_config")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 227845607:
                    if (str.equals("log_history_size")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 850213087:
                    if (str.equals("content_protection_optional_groups_threshold")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 1119140421:
                    if (str.equals("max_buffer_size")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1127959292:
                    if (str.equals("content_protection_allowlist_timeout_ms")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 1559908375:
                    if (str.equals("content_protection_auto_disconnect_timeout_ms")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 1568835651:
                    if (str.equals("idle_unbind_timeout")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 2068460406:
                    if (str.equals("idle_flush_frequency")) {
                        c = 3;
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
                    setDisabledByDeviceConfig(properties.getString(str, (java.lang.String) null));
                    return;
                case 1:
                    setLoggingLevelFromDeviceConfig();
                    return;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case '\b':
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                case 14:
                case 15:
                    setFineTuneParamsFromDeviceConfig();
                    return;
                default:
                    android.util.Slog.i(TAG, "Ignoring change on " + str);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    protected void setFineTuneParamsFromDeviceConfig() {
        boolean z;
        boolean deviceConfigEnableContentProtectionReceiver;
        java.lang.String string;
        java.lang.String string2;
        int i;
        long j;
        long j2;
        com.android.server.contentprotection.ContentProtectionAllowlistManager contentProtectionAllowlistManager;
        com.android.server.contentprotection.ContentProtectionAllowlistManager contentProtectionAllowlistManager2;
        com.android.server.contentprotection.ContentProtectionConsentManager contentProtectionConsentManager;
        synchronized (this.mLock) {
            try {
                this.mDevCfgMaxBufferSize = android.provider.DeviceConfig.getInt("content_capture", "max_buffer_size", 500);
                this.mDevCfgIdleFlushingFrequencyMs = android.provider.DeviceConfig.getInt("content_capture", "idle_flush_frequency", 5000);
                this.mDevCfgTextChangeFlushingFrequencyMs = android.provider.DeviceConfig.getInt("content_capture", "text_change_flush_frequency", 1000);
                this.mDevCfgLogHistorySize = android.provider.DeviceConfig.getInt("content_capture", "log_history_size", 20);
                this.mDevCfgIdleUnbindTimeoutMs = android.provider.DeviceConfig.getInt("content_capture", "idle_unbind_timeout", 0);
                this.mDevCfgDisableFlushForViewTreeAppearing = android.provider.DeviceConfig.getBoolean("content_capture", "disable_flush_for_view_tree_appearing", false);
                z = this.mDevCfgEnableContentProtectionReceiver;
                deviceConfigEnableContentProtectionReceiver = getDeviceConfigEnableContentProtectionReceiver();
                this.mDevCfgContentProtectionBufferSize = android.provider.DeviceConfig.getInt("content_capture", "content_protection_buffer_size", 150);
                string = android.provider.DeviceConfig.getString("content_capture", "content_protection_required_groups_config", "");
                string2 = android.provider.DeviceConfig.getString("content_capture", "content_protection_optional_groups_config", "");
                i = android.provider.DeviceConfig.getInt("content_capture", "content_protection_optional_groups_threshold", 0);
                j = android.provider.DeviceConfig.getLong("content_capture", "content_protection_allowlist_delay_ms", 30000L);
                j2 = android.provider.DeviceConfig.getLong("content_capture", "content_protection_allowlist_timeout_ms", 250L);
                this.mDevCfgContentProtectionAutoDisconnectTimeoutMs = android.provider.DeviceConfig.getLong("content_capture", "content_protection_auto_disconnect_timeout_ms", com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS);
                contentProtectionAllowlistManager = this.mContentProtectionAllowlistManager;
                if (this.verbose) {
                    android.util.Slog.v(TAG, "setFineTuneParamsFromDeviceConfig(): bufferSize=" + this.mDevCfgMaxBufferSize + ", idleFlush=" + this.mDevCfgIdleFlushingFrequencyMs + ", textFlush=" + this.mDevCfgTextChangeFlushingFrequencyMs + ", logHistory=" + this.mDevCfgLogHistorySize + ", idleUnbindTimeoutMs=" + this.mDevCfgIdleUnbindTimeoutMs + ", disableFlushForViewTreeAppearing=" + this.mDevCfgDisableFlushForViewTreeAppearing + ", enableContentProtectionReceiver=" + deviceConfigEnableContentProtectionReceiver + ", contentProtectionBufferSize=" + this.mDevCfgContentProtectionBufferSize + ", contentProtectionRequiredGroupsConfig=" + string + ", contentProtectionOptionalGroupsConfig=" + string2 + ", contentProtectionOptionalGroupsThreshold=" + i + ", contentProtectionAllowlistDelayMs=" + j + ", contentProtectionAllowlistTimeoutMs=" + j2 + ", contentProtectionAutoDisconnectTimeoutMs=" + this.mDevCfgContentProtectionAutoDisconnectTimeoutMs);
                }
            } finally {
            }
        }
        java.util.List<java.util.List<java.lang.String>> parseContentProtectionGroupsConfig = parseContentProtectionGroupsConfig(string);
        java.util.List<java.util.List<java.lang.String>> parseContentProtectionGroupsConfig2 = parseContentProtectionGroupsConfig(string2);
        if (contentProtectionAllowlistManager != null && !deviceConfigEnableContentProtectionReceiver) {
            contentProtectionAllowlistManager.stop();
        }
        android.content.ComponentName componentName = null;
        if (!z && deviceConfigEnableContentProtectionReceiver) {
            android.content.ComponentName contentProtectionServiceComponentName = getContentProtectionServiceComponentName();
            if (contentProtectionServiceComponentName == null) {
                contentProtectionAllowlistManager2 = null;
                contentProtectionConsentManager = null;
                componentName = contentProtectionServiceComponentName;
            } else {
                com.android.server.contentprotection.ContentProtectionAllowlistManager createContentProtectionAllowlistManager = createContentProtectionAllowlistManager(j2);
                createContentProtectionAllowlistManager.start(j);
                contentProtectionConsentManager = createContentProtectionConsentManager();
                contentProtectionAllowlistManager2 = createContentProtectionAllowlistManager;
                componentName = contentProtectionServiceComponentName;
            }
        } else {
            contentProtectionAllowlistManager2 = null;
            contentProtectionConsentManager = null;
        }
        synchronized (this.mLock) {
            try {
                this.mDevCfgEnableContentProtectionReceiver = deviceConfigEnableContentProtectionReceiver;
                this.mDevCfgContentProtectionRequiredGroups = parseContentProtectionGroupsConfig;
                this.mDevCfgContentProtectionOptionalGroups = parseContentProtectionGroupsConfig2;
                this.mDevCfgContentProtectionOptionalGroupsThreshold = i;
                this.mDevCfgContentProtectionAllowlistDelayMs = j;
                if (z ^ deviceConfigEnableContentProtectionReceiver) {
                    this.mContentProtectionServiceComponentName = componentName;
                    this.mContentProtectionAllowlistManager = contentProtectionAllowlistManager2;
                    this.mContentProtectionConsentManager = contentProtectionConsentManager;
                }
            } finally {
            }
        }
    }

    private void setLoggingLevelFromDeviceConfig() {
        this.mDevCfgLoggingLevel = android.provider.DeviceConfig.getInt("content_capture", "logging_level", android.view.contentcapture.ContentCaptureHelper.getDefaultLoggingLevel());
        android.view.contentcapture.ContentCaptureHelper.setLoggingLevel(this.mDevCfgLoggingLevel);
        this.verbose = android.view.contentcapture.ContentCaptureHelper.sVerbose;
        this.debug = android.view.contentcapture.ContentCaptureHelper.sDebug;
        if (this.verbose) {
            android.util.Slog.v(TAG, "setLoggingLevelFromDeviceConfig(): level=" + this.mDevCfgLoggingLevel + ", debug=" + this.debug + ", verbose=" + this.verbose);
        }
    }

    private void setDeviceConfigProperties() {
        setLoggingLevelFromDeviceConfig();
        setFineTuneParamsFromDeviceConfig();
        setDisabledByDeviceConfig(android.provider.DeviceConfig.getProperty("content_capture", "service_explicitly_enabled"));
    }

    private void setDisabledByDeviceConfig(@android.annotation.Nullable java.lang.String str) {
        boolean z;
        if (this.verbose) {
            android.util.Slog.v(TAG, "setDisabledByDeviceConfig(): explicitlyEnabled=" + str);
        }
        java.util.List<android.content.pm.UserInfo> supportedUsers = getSupportedUsers();
        if (str != null && str.equalsIgnoreCase("false")) {
            z = true;
        } else {
            z = false;
        }
        synchronized (this.mLock) {
            try {
                if (this.mDisabledByDeviceConfig == z) {
                    if (this.verbose) {
                        android.util.Slog.v(TAG, "setDisabledByDeviceConfig(): already " + z);
                    }
                    return;
                }
                this.mDisabledByDeviceConfig = z;
                android.util.Slog.i(TAG, "setDisabledByDeviceConfig(): set to " + this.mDisabledByDeviceConfig);
                for (int i = 0; i < supportedUsers.size(); i++) {
                    int i2 = supportedUsers.get(i).id;
                    boolean z2 = this.mDisabledByDeviceConfig || isDisabledBySettingsLocked(i2);
                    java.lang.String str2 = TAG;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("setDisabledByDeviceConfig(): updating service for user ");
                    sb.append(i2);
                    sb.append(" to ");
                    sb.append(z2 ? "'disabled'" : "'enabled'");
                    android.util.Slog.i(str2, sb.toString());
                    updateCachedServiceLocked(i2, z2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void setContentCaptureFeatureEnabledBySettingsForUser(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mDisabledBySettings == null) {
                    this.mDisabledBySettings = new android.util.SparseBooleanArray();
                }
                boolean z2 = true;
                if (!((!this.mDisabledBySettings.get(i)) ^ z)) {
                    if (this.debug) {
                        android.util.Slog.d(TAG, "setContentCaptureFeatureEnabledForUser(): already " + z);
                    }
                    return;
                }
                if (z) {
                    android.util.Slog.i(TAG, "setContentCaptureFeatureEnabled(): enabling service for user " + i);
                    this.mDisabledBySettings.delete(i);
                } else {
                    android.util.Slog.i(TAG, "setContentCaptureFeatureEnabled(): disabling service for user " + i);
                    this.mDisabledBySettings.put(i, true);
                }
                if (z && !this.mDisabledByDeviceConfig) {
                    z2 = false;
                }
                updateCachedServiceLocked(i, z2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void destroySessions(int i, @android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) {
        android.util.Slog.i(TAG, "destroySessions() for userId " + i);
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            try {
                if (i != -1) {
                    com.android.server.contentcapture.ContentCapturePerUserService peekServiceForUserLocked = peekServiceForUserLocked(i);
                    if (peekServiceForUserLocked != null) {
                        peekServiceForUserLocked.destroySessionsLocked();
                    }
                } else {
                    visitServicesLocked(new com.android.server.infra.AbstractMasterSystemService.Visitor() { // from class: com.android.server.contentcapture.ContentCaptureManagerService$$ExternalSyntheticLambda2
                        @Override // com.android.server.infra.AbstractMasterSystemService.Visitor
                        public final void visit(java.lang.Object obj) {
                            ((com.android.server.contentcapture.ContentCapturePerUserService) obj).destroySessionsLocked();
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        try {
            iResultReceiver.send(0, new android.os.Bundle());
        } catch (android.os.RemoteException e) {
        }
    }

    void listSessions(int i, com.android.internal.os.IResultReceiver iResultReceiver) {
        android.util.Slog.i(TAG, "listSessions() for userId " + i);
        enforceCallingPermissionForManagement();
        android.os.Bundle bundle = new android.os.Bundle();
        final java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        synchronized (this.mLock) {
            try {
                if (i != -1) {
                    com.android.server.contentcapture.ContentCapturePerUserService peekServiceForUserLocked = peekServiceForUserLocked(i);
                    if (peekServiceForUserLocked != null) {
                        peekServiceForUserLocked.listSessionsLocked(arrayList);
                    }
                } else {
                    visitServicesLocked(new com.android.server.infra.AbstractMasterSystemService.Visitor() { // from class: com.android.server.contentcapture.ContentCaptureManagerService$$ExternalSyntheticLambda0
                        @Override // com.android.server.infra.AbstractMasterSystemService.Visitor
                        public final void visit(java.lang.Object obj) {
                            ((com.android.server.contentcapture.ContentCapturePerUserService) obj).listSessionsLocked(arrayList);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        bundle.putStringArrayList(RECEIVER_BUNDLE_EXTRA_SESSIONS, arrayList);
        try {
            iResultReceiver.send(0, bundle);
        } catch (android.os.RemoteException e) {
        }
    }

    void updateOptions(final java.lang.String str, final android.content.ContentCaptureOptions contentCaptureOptions) {
        this.mCallbacks.broadcast(new java.util.function.BiConsumer() { // from class: com.android.server.contentcapture.ContentCaptureManagerService$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.contentcapture.ContentCaptureManagerService.lambda$updateOptions$3(str, contentCaptureOptions, (android.view.contentcapture.IContentCaptureOptionsCallback) obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateOptions$3(java.lang.String str, android.content.ContentCaptureOptions contentCaptureOptions, android.view.contentcapture.IContentCaptureOptionsCallback iContentCaptureOptionsCallback, java.lang.Object obj) {
        if (obj.equals(str)) {
            try {
                iContentCaptureOptionsCallback.setContentCaptureOptions(contentCaptureOptions);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Unable to send setContentCaptureOptions(): " + e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.ActivityManagerInternal getAmInternal() {
        synchronized (this.mLock) {
            try {
                if (this.mAm == null) {
                    this.mAm = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mAm;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void assertCalledByServiceLocked(@android.annotation.NonNull java.lang.String str) {
        if (!isCalledByServiceLocked(str)) {
            throw new java.lang.SecurityException("caller is not user's ContentCapture service");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean isCalledByServiceLocked(@android.annotation.NonNull java.lang.String str) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        int callingUid = android.os.Binder.getCallingUid();
        java.lang.String serviceName = this.mServiceNameResolver.getServiceName(callingUserId);
        if (serviceName == null) {
            android.util.Slog.e(TAG, str + ": called by UID " + callingUid + ", but there's no service set for user " + callingUserId);
            return false;
        }
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(serviceName);
        if (unflattenFromString == null) {
            android.util.Slog.w(TAG, str + ": invalid service name: " + serviceName);
            return false;
        }
        try {
            int packageUidAsUser = getContext().getPackageManager().getPackageUidAsUser(unflattenFromString.getPackageName(), android.os.UserHandle.getCallingUserId());
            if (callingUid != packageUidAsUser) {
                android.util.Slog.e(TAG, str + ": called by UID " + callingUid + ", but service UID is " + packageUidAsUser);
                return false;
            }
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.w(TAG, str + ": could not verify UID for " + serviceName);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean throwsSecurityException(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver, @android.annotation.NonNull java.lang.Runnable runnable) {
        try {
            runnable.run();
            return false;
        } catch (java.lang.SecurityException e) {
            try {
                iResultReceiver.send(-1, com.android.internal.util.SyncResultReceiver.bundleFor(e.getMessage()));
                return true;
            } catch (android.os.RemoteException e2) {
                android.util.Slog.w(TAG, "Unable to send security exception (" + e + "): ", e2);
                return true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean isDefaultServiceLocked(int i) {
        java.lang.String defaultServiceName = this.mServiceNameResolver.getDefaultServiceName(i);
        if (defaultServiceName == null) {
            return false;
        }
        return defaultServiceName.equals(this.mServiceNameResolver.getServiceName(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpLocked(java.lang.String str, java.io.PrintWriter printWriter) {
        super.dumpLocked(str, printWriter);
        java.lang.String str2 = str + "  ";
        printWriter.print(str);
        printWriter.print("Users disabled by Settings: ");
        printWriter.println(this.mDisabledBySettings);
        printWriter.print(str);
        printWriter.println("DeviceConfig Settings: ");
        printWriter.print(str2);
        printWriter.print("disabled: ");
        printWriter.println(this.mDisabledByDeviceConfig);
        printWriter.print(str2);
        printWriter.print("loggingLevel: ");
        printWriter.println(this.mDevCfgLoggingLevel);
        printWriter.print(str2);
        printWriter.print("maxBufferSize: ");
        printWriter.println(this.mDevCfgMaxBufferSize);
        printWriter.print(str2);
        printWriter.print("idleFlushingFrequencyMs: ");
        printWriter.println(this.mDevCfgIdleFlushingFrequencyMs);
        printWriter.print(str2);
        printWriter.print("textChangeFlushingFrequencyMs: ");
        printWriter.println(this.mDevCfgTextChangeFlushingFrequencyMs);
        printWriter.print(str2);
        printWriter.print("logHistorySize: ");
        printWriter.println(this.mDevCfgLogHistorySize);
        printWriter.print(str2);
        printWriter.print("idleUnbindTimeoutMs: ");
        printWriter.println(this.mDevCfgIdleUnbindTimeoutMs);
        printWriter.print(str2);
        printWriter.print("disableFlushForViewTreeAppearing: ");
        printWriter.println(this.mDevCfgDisableFlushForViewTreeAppearing);
        printWriter.print(str2);
        printWriter.print("enableContentProtectionReceiver: ");
        printWriter.println(this.mDevCfgEnableContentProtectionReceiver);
        printWriter.print(str2);
        printWriter.print("contentProtectionBufferSize: ");
        printWriter.println(this.mDevCfgContentProtectionBufferSize);
        printWriter.print(str2);
        printWriter.print("contentProtectionRequiredGroupsSize: ");
        printWriter.println(this.mDevCfgContentProtectionRequiredGroups.size());
        printWriter.print(str2);
        printWriter.print("contentProtectionOptionalGroupsSize: ");
        printWriter.println(this.mDevCfgContentProtectionOptionalGroups.size());
        printWriter.print(str2);
        printWriter.print("contentProtectionOptionalGroupsThreshold: ");
        printWriter.println(this.mDevCfgContentProtectionOptionalGroupsThreshold);
        printWriter.print(str2);
        printWriter.print("contentProtectionAllowlistDelayMs: ");
        printWriter.println(this.mDevCfgContentProtectionAllowlistDelayMs);
        printWriter.print(str2);
        printWriter.print("contentProtectionAllowlistTimeoutMs: ");
        printWriter.println(this.mDevCfgContentProtectionAllowlistTimeoutMs);
        printWriter.print(str2);
        printWriter.print("contentProtectionAutoDisconnectTimeoutMs: ");
        printWriter.println(this.mDevCfgContentProtectionAutoDisconnectTimeoutMs);
        printWriter.print(str);
        printWriter.println("Global Options:");
        this.mGlobalContentCaptureOptions.dump(str2, printWriter);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    protected boolean getDeviceConfigEnableContentProtectionReceiver() {
        return android.provider.DeviceConfig.getBoolean("content_capture", "enable_content_protection_receiver", false);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    protected com.android.server.contentprotection.ContentProtectionAllowlistManager createContentProtectionAllowlistManager(long j) {
        return new com.android.server.contentprotection.ContentProtectionAllowlistManager(this, com.android.internal.os.BackgroundThread.getHandler(), j);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    protected com.android.server.contentprotection.ContentProtectionConsentManager createContentProtectionConsentManager() {
        return new com.android.server.contentprotection.ContentProtectionConsentManager(com.android.internal.os.BackgroundThread.getHandler(), getContext().getContentResolver(), android.app.admin.DevicePolicyCache.getInstance());
    }

    @android.annotation.Nullable
    private android.content.ComponentName getContentProtectionServiceComponentName() {
        java.lang.String contentProtectionServiceFlatComponentName = getContentProtectionServiceFlatComponentName();
        if (contentProtectionServiceFlatComponentName == null) {
            return null;
        }
        return android.content.ComponentName.unflattenFromString(contentProtectionServiceFlatComponentName);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    @android.annotation.Nullable
    protected java.lang.String getContentProtectionServiceFlatComponentName() {
        return getContext().getString(android.R.string.config_defaultAssistantAccessComponent);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    protected android.service.contentcapture.ContentCaptureServiceInfo createContentProtectionServiceInfo(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        return new android.service.contentcapture.ContentCaptureServiceInfo(getContext(), componentName, false, android.os.UserHandle.getCallingUserId());
    }

    @android.annotation.Nullable
    public com.android.server.contentprotection.RemoteContentProtectionService createRemoteContentProtectionService() {
        synchronized (this.mLock) {
            if (!this.mDevCfgEnableContentProtectionReceiver || this.mContentProtectionServiceComponentName == null) {
                return null;
            }
            android.content.ComponentName componentName = this.mContentProtectionServiceComponentName;
            long j = this.mDevCfgContentProtectionAutoDisconnectTimeoutMs;
            try {
                createContentProtectionServiceInfo(componentName);
                return createRemoteContentProtectionService(componentName, j);
            } catch (java.lang.Exception e) {
                return null;
            }
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    protected com.android.server.contentprotection.RemoteContentProtectionService createRemoteContentProtectionService(@android.annotation.NonNull android.content.ComponentName componentName, long j) {
        return new com.android.server.contentprotection.RemoteContentProtectionService(getContext(), componentName, android.os.UserHandle.getCallingUserId(), isBindInstantServiceAllowed(), j);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    protected com.android.server.contentcapture.ContentCaptureManagerService.ContentCaptureManagerServiceStub getContentCaptureManagerServiceStub() {
        return this.mContentCaptureManagerServiceStub;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    protected java.util.List<java.util.List<java.lang.String>> parseContentProtectionGroupsConfig(@android.annotation.Nullable java.lang.String str) {
        if (this.verbose) {
            android.util.Slog.v(TAG, "parseContentProtectionGroupsConfig: " + str);
        }
        if (str == null) {
            return java.util.Collections.emptyList();
        }
        return java.util.Arrays.stream(str.split(CONTENT_PROTECTION_GROUP_CONFIG_SEPARATOR_GROUP)).map(new java.util.function.Function() { // from class: com.android.server.contentcapture.ContentCaptureManagerService$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.util.List parseContentProtectionGroupConfigValues;
                parseContentProtectionGroupConfigValues = com.android.server.contentcapture.ContentCaptureManagerService.this.parseContentProtectionGroupConfigValues((java.lang.String) obj);
                return parseContentProtectionGroupConfigValues;
            }
        }).filter(new java.util.function.Predicate() { // from class: com.android.server.contentcapture.ContentCaptureManagerService$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$parseContentProtectionGroupsConfig$4;
                lambda$parseContentProtectionGroupsConfig$4 = com.android.server.contentcapture.ContentCaptureManagerService.lambda$parseContentProtectionGroupsConfig$4((java.util.List) obj);
                return lambda$parseContentProtectionGroupsConfig$4;
            }
        }).toList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$parseContentProtectionGroupsConfig$4(java.util.List list) {
        return !list.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<java.lang.String> parseContentProtectionGroupConfigValues(@android.annotation.NonNull java.lang.String str) {
        return java.util.Arrays.stream(str.split(CONTENT_PROTECTION_GROUP_CONFIG_SEPARATOR_VALUE)).filter(new java.util.function.Predicate() { // from class: com.android.server.contentcapture.ContentCaptureManagerService$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$parseContentProtectionGroupConfigValues$5;
                lambda$parseContentProtectionGroupConfigValues$5 = com.android.server.contentcapture.ContentCaptureManagerService.lambda$parseContentProtectionGroupConfigValues$5((java.lang.String) obj);
                return lambda$parseContentProtectionGroupConfigValues$5;
            }
        }).toList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$parseContentProtectionGroupConfigValues$5(java.lang.String str) {
        return !str.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean isContentProtectionEnabledLocked() {
        return (!this.mDevCfgEnableContentProtectionReceiver || this.mContentProtectionServiceComponentName == null || this.mContentProtectionAllowlistManager == null || this.mContentProtectionConsentManager == null || (this.mDevCfgContentProtectionRequiredGroups.isEmpty() && this.mDevCfgContentProtectionOptionalGroups.isEmpty())) ? false : true;
    }

    final class ContentCaptureManagerServiceStub extends android.view.contentcapture.IContentCaptureManager.Stub {
        ContentCaptureManagerServiceStub() {
        }

        public void startSession(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2, @android.annotation.NonNull android.content.ComponentName componentName, int i, int i2, @android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) {
            java.util.Objects.requireNonNull(iBinder);
            java.util.Objects.requireNonNull(iBinder2);
            int callingUserId = android.os.UserHandle.getCallingUserId();
            android.content.pm.ActivityPresentationInfo activityPresentationInfo = com.android.server.contentcapture.ContentCaptureManagerService.this.getAmInternal().getActivityPresentationInfo(iBinder);
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                try {
                    com.android.server.contentcapture.ContentCapturePerUserService contentCapturePerUserService = (com.android.server.contentcapture.ContentCapturePerUserService) com.android.server.contentcapture.ContentCaptureManagerService.this.getServiceForUserLocked(callingUserId);
                    if (!com.android.server.contentcapture.ContentCaptureManagerService.this.isDefaultServiceLocked(callingUserId) && !com.android.server.contentcapture.ContentCaptureManagerService.this.isCalledByServiceLocked("startSession()")) {
                        android.service.contentcapture.ContentCaptureService.setClientState(iResultReceiver, 4, (android.os.IBinder) null);
                    } else {
                        contentCapturePerUserService.startSessionLocked(iBinder, iBinder2, activityPresentationInfo, i, android.os.Binder.getCallingUid(), i2, iResultReceiver);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void finishSession(int i) {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                ((com.android.server.contentcapture.ContentCapturePerUserService) com.android.server.contentcapture.ContentCaptureManagerService.this.getServiceForUserLocked(callingUserId)).finishSessionLocked(i);
            }
        }

        public void getServiceComponentName(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) {
            android.content.ComponentName serviceComponentName;
            int callingUserId = android.os.UserHandle.getCallingUserId();
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                serviceComponentName = ((com.android.server.contentcapture.ContentCapturePerUserService) com.android.server.contentcapture.ContentCaptureManagerService.this.getServiceForUserLocked(callingUserId)).getServiceComponentName();
            }
            try {
                iResultReceiver.send(0, com.android.internal.util.SyncResultReceiver.bundleFor(serviceComponentName));
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Unable to send service component name: " + e);
            }
        }

        public void removeData(@android.annotation.NonNull android.view.contentcapture.DataRemovalRequest dataRemovalRequest) {
            java.util.Objects.requireNonNull(dataRemovalRequest);
            com.android.server.contentcapture.ContentCaptureManagerService.this.assertCalledByPackageOwner(dataRemovalRequest.getPackageName());
            int callingUserId = android.os.UserHandle.getCallingUserId();
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                ((com.android.server.contentcapture.ContentCapturePerUserService) com.android.server.contentcapture.ContentCaptureManagerService.this.getServiceForUserLocked(callingUserId)).removeDataLocked(dataRemovalRequest);
            }
        }

        public void shareData(@android.annotation.NonNull android.view.contentcapture.DataShareRequest dataShareRequest, @android.annotation.NonNull android.view.contentcapture.IDataShareWriteAdapter iDataShareWriteAdapter) {
            java.util.Objects.requireNonNull(dataShareRequest);
            java.util.Objects.requireNonNull(iDataShareWriteAdapter);
            com.android.server.contentcapture.ContentCaptureManagerService.this.assertCalledByPackageOwner(dataShareRequest.getPackageName());
            int callingUserId = android.os.UserHandle.getCallingUserId();
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                com.android.server.contentcapture.ContentCapturePerUserService contentCapturePerUserService = (com.android.server.contentcapture.ContentCapturePerUserService) com.android.server.contentcapture.ContentCaptureManagerService.this.getServiceForUserLocked(callingUserId);
                if (com.android.server.contentcapture.ContentCaptureManagerService.this.mPackagesWithShareRequests.size() < 10 && !com.android.server.contentcapture.ContentCaptureManagerService.this.mPackagesWithShareRequests.contains(dataShareRequest.getPackageName())) {
                    contentCapturePerUserService.onDataSharedLocked(dataShareRequest, new com.android.server.contentcapture.ContentCaptureManagerService.DataShareCallbackDelegate(dataShareRequest, iDataShareWriteAdapter, com.android.server.contentcapture.ContentCaptureManagerService.this));
                    return;
                }
                try {
                    com.android.server.contentcapture.ContentCaptureMetricsLogger.writeServiceEvent(14, ((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mServiceNameResolver.getServiceName(callingUserId));
                    iDataShareWriteAdapter.error(2);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to send error message to client");
                }
            }
        }

        public void isContentCaptureFeatureEnabled(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                try {
                    if (com.android.server.contentcapture.ContentCaptureManagerService.this.throwsSecurityException(iResultReceiver, new java.lang.Runnable() { // from class: com.android.server.contentcapture.ContentCaptureManagerService$ContentCaptureManagerServiceStub$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.contentcapture.ContentCaptureManagerService.ContentCaptureManagerServiceStub.this.lambda$isContentCaptureFeatureEnabled$0();
                        }
                    })) {
                        return;
                    }
                    try {
                        iResultReceiver.send(!com.android.server.contentcapture.ContentCaptureManagerService.this.mDisabledByDeviceConfig && !com.android.server.contentcapture.ContentCaptureManagerService.this.isDisabledBySettingsLocked(android.os.UserHandle.getCallingUserId()) ? 1 : 2, (android.os.Bundle) null);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Unable to send isContentCaptureFeatureEnabled(): " + e);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$isContentCaptureFeatureEnabled$0() {
            com.android.server.contentcapture.ContentCaptureManagerService.this.assertCalledByServiceLocked("isContentCaptureFeatureEnabled()");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getServiceSettingsActivity$1() {
            com.android.server.contentcapture.ContentCaptureManagerService.this.enforceCallingPermissionForManagement();
        }

        public void getServiceSettingsActivity(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) {
            if (com.android.server.contentcapture.ContentCaptureManagerService.this.throwsSecurityException(iResultReceiver, new java.lang.Runnable() { // from class: com.android.server.contentcapture.ContentCaptureManagerService$ContentCaptureManagerServiceStub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.contentcapture.ContentCaptureManagerService.ContentCaptureManagerServiceStub.this.lambda$getServiceSettingsActivity$1();
                }
            })) {
                return;
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                try {
                    com.android.server.contentcapture.ContentCapturePerUserService contentCapturePerUserService = (com.android.server.contentcapture.ContentCapturePerUserService) com.android.server.contentcapture.ContentCaptureManagerService.this.getServiceForUserLocked(callingUserId);
                    if (contentCapturePerUserService == null) {
                        return;
                    }
                    android.content.ComponentName serviceSettingsActivityLocked = contentCapturePerUserService.getServiceSettingsActivityLocked();
                    try {
                        iResultReceiver.send(0, com.android.internal.util.SyncResultReceiver.bundleFor(serviceSettingsActivityLocked));
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Unable to send getServiceSettingsIntent(): " + e);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getContentCaptureConditions$2(java.lang.String str) {
            com.android.server.contentcapture.ContentCaptureManagerService.this.assertCalledByPackageOwner(str);
        }

        public void getContentCaptureConditions(@android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) {
            java.util.ArrayList list;
            if (com.android.server.contentcapture.ContentCaptureManagerService.this.throwsSecurityException(iResultReceiver, new java.lang.Runnable() { // from class: com.android.server.contentcapture.ContentCaptureManagerService$ContentCaptureManagerServiceStub$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.contentcapture.ContentCaptureManagerService.ContentCaptureManagerServiceStub.this.lambda$getContentCaptureConditions$2(str);
                }
            })) {
                return;
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                com.android.server.contentcapture.ContentCapturePerUserService contentCapturePerUserService = (com.android.server.contentcapture.ContentCapturePerUserService) com.android.server.contentcapture.ContentCaptureManagerService.this.getServiceForUserLocked(callingUserId);
                list = contentCapturePerUserService == null ? null : android.view.contentcapture.ContentCaptureHelper.toList(contentCapturePerUserService.getContentCaptureConditionsLocked(str));
            }
            try {
                iResultReceiver.send(0, com.android.internal.util.SyncResultReceiver.bundleFor(list));
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Unable to send getServiceComponentName(): " + e);
            }
        }

        public void registerContentCaptureOptionsCallback(@android.annotation.NonNull java.lang.String str, android.view.contentcapture.IContentCaptureOptionsCallback iContentCaptureOptionsCallback) {
            com.android.server.contentcapture.ContentCaptureManagerService.this.assertCalledByPackageOwner(str);
            com.android.server.contentcapture.ContentCaptureManagerService.this.mCallbacks.register(iContentCaptureOptionsCallback, str);
            android.content.ContentCaptureOptions options = com.android.server.contentcapture.ContentCaptureManagerService.this.mGlobalContentCaptureOptions.getOptions(android.os.UserHandle.getCallingUserId(), str);
            if (options != null) {
                try {
                    iContentCaptureOptionsCallback.setContentCaptureOptions(options);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Unable to send setContentCaptureOptions(): " + e);
                }
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            char c;
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.contentcapture.ContentCaptureManagerService.this.getContext(), com.android.server.contentcapture.ContentCaptureManagerService.TAG, printWriter)) {
                boolean z = true;
                if (strArr != null) {
                    boolean z2 = true;
                    for (java.lang.String str : strArr) {
                        switch (str.hashCode()) {
                            case 1098711592:
                                if (str.equals("--no-history")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1333069025:
                                if (str.equals("--help")) {
                                    c = 1;
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
                                z2 = false;
                                break;
                            case 1:
                                printWriter.println("Usage: dumpsys content_capture [--no-history]");
                                return;
                            default:
                                android.util.Slog.w(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Ignoring invalid dump arg: " + str);
                                break;
                        }
                    }
                    z = z2;
                }
                synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                    com.android.server.contentcapture.ContentCaptureManagerService.this.dumpLocked("", printWriter);
                }
                printWriter.print("Requests history: ");
                if (com.android.server.contentcapture.ContentCaptureManagerService.this.mRequestsHistory == null) {
                    printWriter.println("disabled by device config");
                } else {
                    if (z) {
                        printWriter.println();
                        com.android.server.contentcapture.ContentCaptureManagerService.this.mRequestsHistory.reverseDump(fileDescriptor, printWriter, strArr);
                        printWriter.println();
                        return;
                    }
                    printWriter.println();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
            new com.android.server.contentcapture.ContentCaptureManagerServiceShellCommand(com.android.server.contentcapture.ContentCaptureManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public void resetTemporaryService(int i) {
            com.android.server.contentcapture.ContentCaptureManagerService.this.resetTemporaryService(i);
        }

        public void setTemporaryService(int i, @android.annotation.NonNull java.lang.String str, int i2) {
            com.android.server.contentcapture.ContentCaptureManagerService.this.setTemporaryService(i, str, i2);
        }

        public void setDefaultServiceEnabled(int i, boolean z) {
            com.android.server.contentcapture.ContentCaptureManagerService.this.setDefaultServiceEnabled(i, z);
        }

        public void onLoginDetected(@android.annotation.NonNull android.content.pm.ParceledListSlice<android.view.contentcapture.ContentCaptureEvent> parceledListSlice) {
            com.android.server.contentprotection.RemoteContentProtectionService createRemoteContentProtectionService = com.android.server.contentcapture.ContentCaptureManagerService.this.createRemoteContentProtectionService();
            if (createRemoteContentProtectionService == null) {
                return;
            }
            try {
                createRemoteContentProtectionService.onLoginDetected(parceledListSlice);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to call remote service", e);
            }
        }
    }

    private final class LocalService extends com.android.server.contentcapture.ContentCaptureManagerInternal {
        private LocalService() {
        }

        @Override // com.android.server.contentcapture.ContentCaptureManagerInternal
        public boolean isContentCaptureServiceForUser(int i, int i2) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                try {
                    com.android.server.contentcapture.ContentCapturePerUserService contentCapturePerUserService = (com.android.server.contentcapture.ContentCapturePerUserService) com.android.server.contentcapture.ContentCaptureManagerService.this.peekServiceForUserLocked(i2);
                    if (contentCapturePerUserService != null) {
                        return contentCapturePerUserService.isContentCaptureServiceForUserLocked(i);
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.contentcapture.ContentCaptureManagerInternal
        public boolean sendActivityAssistData(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.Bundle bundle) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                try {
                    com.android.server.contentcapture.ContentCapturePerUserService contentCapturePerUserService = (com.android.server.contentcapture.ContentCapturePerUserService) com.android.server.contentcapture.ContentCaptureManagerService.this.peekServiceForUserLocked(i);
                    if (contentCapturePerUserService != null) {
                        return contentCapturePerUserService.sendActivityAssistDataLocked(iBinder, bundle);
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.contentcapture.ContentCaptureManagerInternal
        public android.content.ContentCaptureOptions getOptionsForPackage(int i, @android.annotation.NonNull java.lang.String str) {
            return com.android.server.contentcapture.ContentCaptureManagerService.this.mGlobalContentCaptureOptions.getOptions(i, str);
        }

        @Override // com.android.server.contentcapture.ContentCaptureManagerInternal
        public void notifyActivityEvent(int i, @android.annotation.NonNull android.content.ComponentName componentName, int i2, @android.annotation.NonNull android.app.assist.ActivityId activityId) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                try {
                    com.android.server.contentcapture.ContentCapturePerUserService contentCapturePerUserService = (com.android.server.contentcapture.ContentCapturePerUserService) com.android.server.contentcapture.ContentCaptureManagerService.this.peekServiceForUserLocked(i);
                    if (contentCapturePerUserService != null) {
                        contentCapturePerUserService.onActivityEventLocked(activityId, componentName, i2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    final class GlobalContentCaptureOptions extends com.android.internal.infra.GlobalWhitelistState {

        @com.android.internal.annotations.GuardedBy({"mGlobalWhitelistStateLock"})
        private final android.util.SparseArray<java.lang.String> mServicePackages = new android.util.SparseArray<>();

        @com.android.internal.annotations.GuardedBy({"mGlobalWhitelistStateLock"})
        private final android.util.SparseBooleanArray mTemporaryServices = new android.util.SparseBooleanArray();

        GlobalContentCaptureOptions() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setServiceInfo(int i, @android.annotation.Nullable java.lang.String str, boolean z) {
            synchronized (((com.android.internal.infra.GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
                try {
                    if (z) {
                        this.mTemporaryServices.put(i, true);
                    } else {
                        this.mTemporaryServices.delete(i);
                    }
                    if (str != null) {
                        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
                        if (unflattenFromString == null) {
                            android.util.Slog.w(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "setServiceInfo(): invalid name: " + str);
                            this.mServicePackages.remove(i);
                        } else {
                            this.mServicePackages.put(i, unflattenFromString.getPackageName());
                        }
                    } else {
                        this.mServicePackages.remove(i);
                    }
                } finally {
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mGlobalWhitelistStateLock"})
        @android.annotation.Nullable
        public android.content.ContentCaptureOptions getOptions(int i, @android.annotation.NonNull java.lang.String str) {
            android.util.ArraySet arraySet;
            android.content.ContentCaptureOptions contentCaptureOptions;
            boolean isContentProtectionReceiverEnabled = isContentProtectionReceiverEnabled(i, str);
            synchronized (((com.android.internal.infra.GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
                try {
                    boolean isContentCaptureReceiverEnabled = isContentCaptureReceiverEnabled(i, str);
                    if (isContentCaptureReceiverEnabled) {
                        arraySet = null;
                    } else {
                        android.util.ArraySet whitelistedComponents = getWhitelistedComponents(i, str);
                        if (!isContentProtectionReceiverEnabled && whitelistedComponents == null && str.equals(this.mServicePackages.get(i))) {
                            if (com.android.server.contentcapture.ContentCaptureManagerService.this.verbose) {
                                android.util.Slog.v(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "getOptionsForPackage() lite for " + str);
                            }
                            return new android.content.ContentCaptureOptions(com.android.server.contentcapture.ContentCaptureManagerService.this.mDevCfgLoggingLevel);
                        }
                        arraySet = whitelistedComponents;
                    }
                    if (android.os.Build.IS_USER && ((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mServiceNameResolver.isTemporary(i) && !str.equals(this.mServicePackages.get(i))) {
                        android.util.Slog.w(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Ignoring package " + str + " while using temporary service " + this.mServicePackages.get(i));
                        return null;
                    }
                    if (!isContentCaptureReceiverEnabled && !isContentProtectionReceiverEnabled && arraySet == null) {
                        if (com.android.server.contentcapture.ContentCaptureManagerService.this.verbose) {
                            android.util.Slog.v(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "getOptionsForPackage(" + str + "): not whitelisted");
                        }
                        return null;
                    }
                    synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                        try {
                            contentCaptureOptions = new android.content.ContentCaptureOptions(com.android.server.contentcapture.ContentCaptureManagerService.this.mDevCfgLoggingLevel, com.android.server.contentcapture.ContentCaptureManagerService.this.mDevCfgMaxBufferSize, com.android.server.contentcapture.ContentCaptureManagerService.this.mDevCfgIdleFlushingFrequencyMs, com.android.server.contentcapture.ContentCaptureManagerService.this.mDevCfgTextChangeFlushingFrequencyMs, com.android.server.contentcapture.ContentCaptureManagerService.this.mDevCfgLogHistorySize, com.android.server.contentcapture.ContentCaptureManagerService.this.mDevCfgDisableFlushForViewTreeAppearing, isContentCaptureReceiverEnabled || arraySet != null, new android.content.ContentCaptureOptions.ContentProtectionOptions(isContentProtectionReceiverEnabled, com.android.server.contentcapture.ContentCaptureManagerService.this.mDevCfgContentProtectionBufferSize, com.android.server.contentcapture.ContentCaptureManagerService.this.mDevCfgContentProtectionRequiredGroups, com.android.server.contentcapture.ContentCaptureManagerService.this.mDevCfgContentProtectionOptionalGroups, com.android.server.contentcapture.ContentCaptureManagerService.this.mDevCfgContentProtectionOptionalGroupsThreshold), arraySet);
                            if (com.android.server.contentcapture.ContentCaptureManagerService.this.verbose) {
                                android.util.Slog.v(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "getOptionsForPackage(" + str + "): " + contentCaptureOptions);
                            }
                        } finally {
                        }
                    }
                    return contentCaptureOptions;
                } finally {
                }
            }
        }

        public void dump(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.PrintWriter printWriter) {
            super.dump(str, printWriter);
            synchronized (((com.android.internal.infra.GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
                try {
                    if (this.mServicePackages.size() > 0) {
                        printWriter.print(str);
                        printWriter.print("Service packages: ");
                        printWriter.println(this.mServicePackages);
                    }
                    if (this.mTemporaryServices.size() > 0) {
                        printWriter.print(str);
                        printWriter.print("Temp services: ");
                        printWriter.println(this.mTemporaryServices);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean isWhitelisted(int i, @android.annotation.NonNull java.lang.String str) {
            return isContentCaptureReceiverEnabled(i, str) || isContentProtectionReceiverEnabled(i, str);
        }

        public boolean isWhitelisted(int i, @android.annotation.NonNull android.content.ComponentName componentName) {
            return super.isWhitelisted(i, componentName) || isContentProtectionReceiverEnabled(i, componentName.getPackageName());
        }

        private boolean isContentCaptureReceiverEnabled(int i, @android.annotation.NonNull java.lang.String str) {
            return super.isWhitelisted(i, str);
        }

        private boolean isContentProtectionReceiverEnabled(int i, @android.annotation.NonNull java.lang.String str) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentcapture.ContentCaptureManagerService.this).mLock) {
                try {
                    if (com.android.server.contentcapture.ContentCaptureManagerService.this.isContentProtectionEnabledLocked()) {
                        return com.android.server.contentcapture.ContentCaptureManagerService.this.mContentProtectionConsentManager.isConsentGranted(i) && com.android.server.contentcapture.ContentCaptureManagerService.this.mContentProtectionAllowlistManager.isAllowed(str);
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DataShareCallbackDelegate extends android.service.contentcapture.IDataShareCallback.Stub {

        @android.annotation.NonNull
        private final android.view.contentcapture.IDataShareWriteAdapter mClientAdapter;

        @android.annotation.NonNull
        private final android.view.contentcapture.DataShareRequest mDataShareRequest;

        @android.annotation.NonNull
        private final java.util.concurrent.atomic.AtomicBoolean mLoggedWriteFinish = new java.util.concurrent.atomic.AtomicBoolean(false);

        @android.annotation.NonNull
        private final com.android.server.contentcapture.ContentCaptureManagerService mParentService;

        DataShareCallbackDelegate(@android.annotation.NonNull android.view.contentcapture.DataShareRequest dataShareRequest, @android.annotation.NonNull android.view.contentcapture.IDataShareWriteAdapter iDataShareWriteAdapter, com.android.server.contentcapture.ContentCaptureManagerService contentCaptureManagerService) {
            this.mDataShareRequest = dataShareRequest;
            this.mClientAdapter = iDataShareWriteAdapter;
            this.mParentService = contentCaptureManagerService;
        }

        public void accept(@android.annotation.NonNull final android.service.contentcapture.IDataShareReadAdapter iDataShareReadAdapter) {
            android.util.Slog.i(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Data share request accepted by Content Capture service");
            logServiceEvent(7);
            android.util.Pair<android.os.ParcelFileDescriptor, android.os.ParcelFileDescriptor> createPipe = createPipe();
            if (createPipe == null) {
                logServiceEvent(12);
                sendErrorSignal(this.mClientAdapter, iDataShareReadAdapter, 1);
                return;
            }
            final android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) createPipe.second;
            final android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) createPipe.first;
            android.util.Pair<android.os.ParcelFileDescriptor, android.os.ParcelFileDescriptor> createPipe2 = createPipe();
            if (createPipe2 == null) {
                logServiceEvent(13);
                bestEffortCloseFileDescriptors(parcelFileDescriptor, parcelFileDescriptor2);
                sendErrorSignal(this.mClientAdapter, iDataShareReadAdapter, 1);
                return;
            }
            final android.os.ParcelFileDescriptor parcelFileDescriptor3 = (android.os.ParcelFileDescriptor) createPipe2.second;
            final android.os.ParcelFileDescriptor parcelFileDescriptor4 = (android.os.ParcelFileDescriptor) createPipe2.first;
            synchronized (((com.android.server.infra.AbstractMasterSystemService) this.mParentService).mLock) {
                this.mParentService.mPackagesWithShareRequests.add(this.mDataShareRequest.getPackageName());
            }
            if (!setUpSharingPipeline(this.mClientAdapter, iDataShareReadAdapter, parcelFileDescriptor, parcelFileDescriptor4)) {
                sendErrorSignal(this.mClientAdapter, iDataShareReadAdapter, 1);
                bestEffortCloseFileDescriptors(parcelFileDescriptor, parcelFileDescriptor2, parcelFileDescriptor3, parcelFileDescriptor4);
                synchronized (((com.android.server.infra.AbstractMasterSystemService) this.mParentService).mLock) {
                    this.mParentService.mPackagesWithShareRequests.remove(this.mDataShareRequest.getPackageName());
                }
                return;
            }
            bestEffortCloseFileDescriptors(parcelFileDescriptor, parcelFileDescriptor4);
            this.mParentService.mDataShareExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.contentcapture.ContentCaptureManagerService$DataShareCallbackDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.contentcapture.ContentCaptureManagerService.DataShareCallbackDelegate.this.lambda$accept$0(parcelFileDescriptor2, parcelFileDescriptor3, iDataShareReadAdapter);
                }
            });
            this.mParentService.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.contentcapture.ContentCaptureManagerService$DataShareCallbackDelegate$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.contentcapture.ContentCaptureManagerService.DataShareCallbackDelegate.this.lambda$accept$1(parcelFileDescriptor, parcelFileDescriptor2, parcelFileDescriptor3, parcelFileDescriptor4, iDataShareReadAdapter);
                }
            }, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:121:0x0063 -> B:34:0x00f6). Please report as a decompilation issue!!! */
        public /* synthetic */ void lambda$accept$0(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.service.contentcapture.IDataShareReadAdapter iDataShareReadAdapter) {
            android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream;
            android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
            boolean z = false;
            try {
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to call finish() the service operation", e);
            }
            try {
                try {
                    autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
                    try {
                        autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor2);
                    } catch (java.lang.Throwable th) {
                        th = th;
                        try {
                            autoCloseInputStream.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (java.io.IOException e2) {
                    e = e2;
                }
                try {
                    byte[] bArr = new byte[1024];
                    boolean z2 = false;
                    while (true) {
                        try {
                            int read = autoCloseInputStream.read(bArr);
                            if (read == -1) {
                                try {
                                    break;
                                } catch (java.lang.Throwable th3) {
                                    th = th3;
                                    z = z2;
                                    autoCloseInputStream.close();
                                    throw th;
                                }
                            }
                            autoCloseOutputStream.write(bArr, 0, read);
                            z2 = true;
                        } catch (java.lang.Throwable th4) {
                            th = th4;
                            z = z2;
                            try {
                                autoCloseOutputStream.close();
                            } catch (java.lang.Throwable th5) {
                                th.addSuppressed(th5);
                            }
                            throw th;
                        }
                    }
                    autoCloseOutputStream.close();
                    try {
                        autoCloseInputStream.close();
                        synchronized (((com.android.server.infra.AbstractMasterSystemService) this.mParentService).mLock) {
                            this.mParentService.mPackagesWithShareRequests.remove(this.mDataShareRequest.getPackageName());
                        }
                    } catch (java.io.IOException e3) {
                        e = e3;
                        z = z2;
                        android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to pipe client and service streams", e);
                        logServiceEvent(10);
                        sendErrorSignal(this.mClientAdapter, iDataShareReadAdapter, 1);
                        synchronized (((com.android.server.infra.AbstractMasterSystemService) this.mParentService).mLock) {
                            this.mParentService.mPackagesWithShareRequests.remove(this.mDataShareRequest.getPackageName());
                        }
                        if (z) {
                            if (!this.mLoggedWriteFinish.get()) {
                                logServiceEvent(9);
                                this.mLoggedWriteFinish.set(true);
                            }
                            try {
                                this.mClientAdapter.finish();
                            } catch (android.os.RemoteException e4) {
                                android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to call finish() the client operation", e4);
                            }
                            iDataShareReadAdapter.finish();
                            return;
                        }
                        logServiceEvent(11);
                        sendErrorSignal(this.mClientAdapter, iDataShareReadAdapter, 1);
                    } catch (java.lang.Throwable th6) {
                        th = th6;
                        z = z2;
                        synchronized (((com.android.server.infra.AbstractMasterSystemService) this.mParentService).mLock) {
                            this.mParentService.mPackagesWithShareRequests.remove(this.mDataShareRequest.getPackageName());
                        }
                        if (z) {
                            if (!this.mLoggedWriteFinish.get()) {
                                logServiceEvent(9);
                                this.mLoggedWriteFinish.set(true);
                            }
                            try {
                                this.mClientAdapter.finish();
                            } catch (android.os.RemoteException e5) {
                                android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to call finish() the client operation", e5);
                            }
                            try {
                                iDataShareReadAdapter.finish();
                            } catch (android.os.RemoteException e6) {
                                android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to call finish() the service operation", e6);
                            }
                        } else {
                            logServiceEvent(11);
                            sendErrorSignal(this.mClientAdapter, iDataShareReadAdapter, 1);
                        }
                        throw th;
                    }
                    if (z2) {
                        if (!this.mLoggedWriteFinish.get()) {
                            logServiceEvent(9);
                            this.mLoggedWriteFinish.set(true);
                        }
                        try {
                            this.mClientAdapter.finish();
                        } catch (android.os.RemoteException e7) {
                            android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to call finish() the client operation", e7);
                        }
                        iDataShareReadAdapter.finish();
                        return;
                    }
                    logServiceEvent(11);
                    sendErrorSignal(this.mClientAdapter, iDataShareReadAdapter, 1);
                } catch (java.lang.Throwable th7) {
                    th = th7;
                }
            } catch (java.lang.Throwable th8) {
                th = th8;
            }
        }

        public void reject() {
            android.util.Slog.i(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Data share request rejected by Content Capture service");
            logServiceEvent(8);
            try {
                this.mClientAdapter.rejected();
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to call rejected() the client operation", e);
                try {
                    this.mClientAdapter.error(1);
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.w(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to call error() the client operation", e2);
                }
            }
        }

        private boolean setUpSharingPipeline(android.view.contentcapture.IDataShareWriteAdapter iDataShareWriteAdapter, android.service.contentcapture.IDataShareReadAdapter iDataShareReadAdapter, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2) {
            try {
                iDataShareWriteAdapter.write(parcelFileDescriptor);
                try {
                    iDataShareReadAdapter.start(parcelFileDescriptor2);
                    return true;
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to call start() the service operation", e);
                    logServiceEvent(13);
                    return false;
                }
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to call write() the client operation", e2);
                logServiceEvent(12);
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: enforceDataSharingTtl, reason: merged with bridge method [inline-methods] */
        public void lambda$accept$1(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3, android.os.ParcelFileDescriptor parcelFileDescriptor4, android.service.contentcapture.IDataShareReadAdapter iDataShareReadAdapter) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) this.mParentService).mLock) {
                try {
                    this.mParentService.mPackagesWithShareRequests.remove(this.mDataShareRequest.getPackageName());
                    boolean z = (parcelFileDescriptor2.getFileDescriptor().valid() || parcelFileDescriptor3.getFileDescriptor().valid()) ? false : true;
                    if (z) {
                        if (!this.mLoggedWriteFinish.get()) {
                            logServiceEvent(9);
                            this.mLoggedWriteFinish.set(true);
                        }
                        android.util.Slog.i(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Content capture data sharing session terminated successfully for package '" + this.mDataShareRequest.getPackageName() + "'");
                    } else {
                        logServiceEvent(15);
                        android.util.Slog.i(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Reached the timeout of Content Capture data sharing session for package '" + this.mDataShareRequest.getPackageName() + "', terminating the pipe.");
                    }
                    bestEffortCloseFileDescriptors(parcelFileDescriptor, parcelFileDescriptor2, parcelFileDescriptor3, parcelFileDescriptor4);
                    if (!z) {
                        sendErrorSignal(this.mClientAdapter, iDataShareReadAdapter, 3);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private android.util.Pair<android.os.ParcelFileDescriptor, android.os.ParcelFileDescriptor> createPipe() {
            try {
                android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
                if (createPipe.length != 2) {
                    android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to create a content capture data-sharing pipe, unexpected number of file descriptors");
                    return null;
                }
                if (!createPipe[0].getFileDescriptor().valid() || !createPipe[1].getFileDescriptor().valid()) {
                    android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to create a content capture data-sharing pipe, didn't receive a pair of valid file descriptors.");
                    return null;
                }
                return android.util.Pair.create(createPipe[0], createPipe[1]);
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to create a content capture data-sharing pipe", e);
                return null;
            }
        }

        private void bestEffortCloseFileDescriptor(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            try {
                parcelFileDescriptor.close();
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to close a file descriptor", e);
            }
        }

        private void bestEffortCloseFileDescriptors(android.os.ParcelFileDescriptor... parcelFileDescriptorArr) {
            for (android.os.ParcelFileDescriptor parcelFileDescriptor : parcelFileDescriptorArr) {
                bestEffortCloseFileDescriptor(parcelFileDescriptor);
            }
        }

        private static void sendErrorSignal(android.view.contentcapture.IDataShareWriteAdapter iDataShareWriteAdapter, android.service.contentcapture.IDataShareReadAdapter iDataShareReadAdapter, int i) {
            try {
                iDataShareWriteAdapter.error(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to call error() the client operation", e);
            }
            try {
                iDataShareReadAdapter.error(i);
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(com.android.server.contentcapture.ContentCaptureManagerService.TAG, "Failed to call error() the service operation", e2);
            }
        }

        private void logServiceEvent(int i) {
            com.android.server.contentcapture.ContentCaptureMetricsLogger.writeServiceEvent(i, ((com.android.server.infra.AbstractMasterSystemService) this.mParentService).mServiceNameResolver.getServiceName(android.os.UserHandle.getCallingUserId()));
        }
    }
}
