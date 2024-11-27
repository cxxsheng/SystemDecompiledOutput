package com.android.server.notification;

/* loaded from: classes2.dex */
public class ZenModeHelper {
    private static final java.lang.String IMPLICIT_RULE_ID_PREFIX = "implicit_";
    private static final java.lang.String PACKAGE_ANDROID = "android";
    private static final int RULE_INSTANCE_GRACE_PERIOD = 259200000;
    static final int RULE_LIMIT_PER_PACKAGE = 100;
    static final long SEND_ACTIVATION_AZR_STATUSES = 308673617;
    public static final long SUPPRESSED_EFFECT_ALL = 3;
    public static final long SUPPRESSED_EFFECT_CALLS = 2;
    public static final long SUPPRESSED_EFFECT_NOTIFICATIONS = 1;
    private final android.app.AppOpsManager mAppOps;

    @com.android.internal.annotations.VisibleForTesting
    protected android.media.AudioManagerInternal mAudioManager;
    private final java.time.Clock mClock;

    @com.android.internal.annotations.VisibleForTesting
    protected final com.android.server.notification.ZenModeConditions mConditions;

    @com.android.internal.annotations.GuardedBy({"mConfigLock"})
    @com.android.internal.annotations.VisibleForTesting
    protected android.service.notification.ZenModeConfig mConfig;

    @com.android.internal.annotations.VisibleForTesting
    protected android.app.NotificationManager.Policy mConsolidatedPolicy;
    private final android.content.Context mContext;
    private final android.service.notification.ZenModeConfig mDefaultConfig;

    @com.android.internal.annotations.GuardedBy({"mConfigLock"})
    private android.service.notification.DeviceEffectsApplier mDeviceEffectsApplier;
    private final com.android.server.notification.ZenModeFiltering mFiltering;
    private final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver mFlagResolver;
    private final com.android.server.notification.ZenModeHelper.H mHandler;

    @com.android.internal.annotations.VisibleForTesting
    protected boolean mIsSystemServicesReady;
    private final android.app.NotificationManager mNotificationManager;
    protected android.content.pm.PackageManager mPm;
    private java.lang.String[] mPriorityOnlyDndExemptPackages;
    private final com.android.server.notification.ManagedServices.Config mServiceConfig;
    private final com.android.server.notification.ZenModeHelper.SettingsObserver mSettingsObserver;
    private long mSuppressedEffects;

    @com.android.internal.annotations.VisibleForTesting
    protected int mZenMode;
    private final com.android.server.notification.ZenModeEventLogger mZenModeEventLogger;
    static final java.lang.String TAG = "ZenModeHelper";
    static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final java.time.Duration DELETED_RULE_KEPT_FOR = java.time.Duration.ofDays(30);

    @com.android.internal.annotations.VisibleForTesting
    protected final android.util.ArrayMap<java.lang.String, java.lang.Integer> mRulesUidCache = new android.util.ArrayMap<>();
    private final java.util.ArrayList<com.android.server.notification.ZenModeHelper.Callback> mCallbacks = new java.util.ArrayList<>();
    private final com.android.server.notification.ZenModeHelper.RingerModeDelegate mRingerModeDelegate = new com.android.server.notification.ZenModeHelper.RingerModeDelegate();
    private final java.lang.Object mConfigsArrayLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mConfigsArrayLock"})
    @com.android.internal.annotations.VisibleForTesting
    final android.util.SparseArray<android.service.notification.ZenModeConfig> mConfigs = new android.util.SparseArray<>();
    private final com.android.server.notification.ZenModeHelper.Metrics mMetrics = new com.android.server.notification.ZenModeHelper.Metrics();

    @com.android.internal.annotations.GuardedBy({"mConfigLock"})
    private android.service.notification.ZenDeviceEffects mConsolidatedDeviceEffects = new android.service.notification.ZenDeviceEffects.Builder().build();
    private int mUser = 0;
    private final java.lang.Object mConfigLock = new java.lang.Object();

    public ZenModeHelper(android.content.Context context, android.os.Looper looper, java.time.Clock clock, com.android.server.notification.ConditionProviders conditionProviders, com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver flagResolver, com.android.server.notification.ZenModeEventLogger zenModeEventLogger) {
        this.mContext = context;
        this.mHandler = new com.android.server.notification.ZenModeHelper.H(looper);
        this.mClock = clock;
        addCallback(this.mMetrics);
        this.mAppOps = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        this.mNotificationManager = (android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class);
        this.mDefaultConfig = readDefaultConfig(this.mContext.getResources());
        updateDefaultAutomaticRuleNames();
        if (android.app.Flags.modesApi()) {
            updateDefaultAutomaticRulePolicies();
        }
        this.mConfig = this.mDefaultConfig.copy();
        synchronized (this.mConfigsArrayLock) {
            this.mConfigs.put(0, this.mConfig);
        }
        this.mConsolidatedPolicy = this.mConfig.toNotificationPolicy();
        this.mSettingsObserver = new com.android.server.notification.ZenModeHelper.SettingsObserver(this.mHandler);
        this.mSettingsObserver.observe();
        this.mFiltering = new com.android.server.notification.ZenModeFiltering(this.mContext);
        this.mConditions = new com.android.server.notification.ZenModeConditions(this, conditionProviders);
        this.mServiceConfig = conditionProviders.getConfig();
        this.mFlagResolver = flagResolver;
        this.mZenModeEventLogger = zenModeEventLogger;
    }

    public android.os.Looper getLooper() {
        return this.mHandler.getLooper();
    }

    public java.lang.String toString() {
        return TAG;
    }

    public boolean matchesCallFilter(android.os.UserHandle userHandle, android.os.Bundle bundle, com.android.server.notification.ValidateNotificationPeople validateNotificationPeople, int i, float f, int i2) {
        boolean matchesCallFilter;
        synchronized (this.mConfigLock) {
            matchesCallFilter = com.android.server.notification.ZenModeFiltering.matchesCallFilter(this.mContext, this.mZenMode, this.mConsolidatedPolicy, userHandle, bundle, validateNotificationPeople, i, f, i2);
        }
        return matchesCallFilter;
    }

    public boolean isCall(com.android.server.notification.NotificationRecord notificationRecord) {
        return this.mFiltering.isCall(notificationRecord);
    }

    public void recordCaller(com.android.server.notification.NotificationRecord notificationRecord) {
        this.mFiltering.recordCall(notificationRecord);
    }

    protected void cleanUpCallersAfter(long j) {
        this.mFiltering.cleanUpCallersAfter(j);
    }

    public boolean shouldIntercept(com.android.server.notification.NotificationRecord notificationRecord) {
        boolean shouldIntercept;
        synchronized (this.mConfigLock) {
            shouldIntercept = this.mFiltering.shouldIntercept(this.mZenMode, this.mConsolidatedPolicy, notificationRecord);
        }
        return shouldIntercept;
    }

    public void addCallback(com.android.server.notification.ZenModeHelper.Callback callback) {
        this.mCallbacks.add(callback);
    }

    public void removeCallback(com.android.server.notification.ZenModeHelper.Callback callback) {
        this.mCallbacks.remove(callback);
    }

    public void initZenMode() {
        if (DEBUG) {
            android.util.Log.d(TAG, "initZenMode");
        }
        synchronized (this.mConfigLock) {
            updateConfigAndZenModeLocked(this.mConfig, 1, "init", true, 1000);
        }
    }

    public void onSystemReady() {
        if (DEBUG) {
            android.util.Log.d(TAG, "onSystemReady");
        }
        this.mAudioManager = (android.media.AudioManagerInternal) com.android.server.LocalServices.getService(android.media.AudioManagerInternal.class);
        if (this.mAudioManager != null) {
            this.mAudioManager.setRingerModeDelegate(this.mRingerModeDelegate);
        }
        this.mPm = this.mContext.getPackageManager();
        this.mHandler.postMetricsTimer();
        cleanUpZenRules();
        this.mIsSystemServicesReady = true;
        showZenUpgradeNotification(this.mZenMode);
    }

    void setDeviceEffectsApplier(@android.annotation.NonNull android.service.notification.DeviceEffectsApplier deviceEffectsApplier) {
        if (!android.app.Flags.modesApi()) {
            return;
        }
        synchronized (this.mConfigLock) {
            if (this.mDeviceEffectsApplier != null) {
                throw new java.lang.IllegalStateException("Already set up a DeviceEffectsApplier!");
            }
            this.mDeviceEffectsApplier = deviceEffectsApplier;
        }
        applyConsolidatedDeviceEffects(1);
    }

    public void onUserSwitched(int i) {
        loadConfigForUser(i, "onUserSwitched");
    }

    public void onUserRemoved(int i) {
        if (i < 0) {
            return;
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "onUserRemoved u=" + i);
        }
        synchronized (this.mConfigsArrayLock) {
            this.mConfigs.remove(i);
        }
    }

    public void onUserUnlocked(int i) {
        loadConfigForUser(i, "onUserUnlocked");
    }

    void setPriorityOnlyDndExemptPackages(java.lang.String[] strArr) {
        this.mPriorityOnlyDndExemptPackages = strArr;
    }

    private void loadConfigForUser(int i, java.lang.String str) {
        android.service.notification.ZenModeConfig zenModeConfig;
        android.service.notification.ZenModeConfig zenModeConfig2;
        if (this.mUser == i || i < 0) {
            return;
        }
        this.mUser = i;
        if (DEBUG) {
            android.util.Log.d(TAG, str + " u=" + i);
        }
        synchronized (this.mConfigsArrayLock) {
            try {
                if (this.mConfigs.get(i) == null) {
                    zenModeConfig = null;
                } else {
                    zenModeConfig = this.mConfigs.get(i).copy();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (zenModeConfig != null) {
            zenModeConfig2 = zenModeConfig;
        } else {
            if (DEBUG) {
                android.util.Log.d(TAG, str + " generating default config for user " + i);
            }
            android.service.notification.ZenModeConfig copy = this.mDefaultConfig.copy();
            copy.user = i;
            zenModeConfig2 = copy;
        }
        synchronized (this.mConfigLock) {
            setConfigLocked(zenModeConfig2, null, 2, str, 1000);
        }
        cleanUpZenRules();
    }

    public int getZenModeListenerInterruptionFilter() {
        return android.app.NotificationManager.zenModeToInterruptionFilter(this.mZenMode);
    }

    public void requestFromListener(android.content.ComponentName componentName, int i, int i2, boolean z) {
        int zenModeFromInterruptionFilter = android.app.NotificationManager.zenModeFromInterruptionFilter(i, -1);
        if (zenModeFromInterruptionFilter != -1) {
            int i3 = z ? 5 : 4;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("listener:");
            sb.append(componentName != null ? componentName.flattenToShortString() : null);
            setManualZenMode(zenModeFromInterruptionFilter, null, i3, sb.toString(), componentName != null ? componentName.getPackageName() : null, i2);
        }
    }

    public void setSuppressedEffects(long j) {
        if (this.mSuppressedEffects == j) {
            return;
        }
        this.mSuppressedEffects = j;
        applyRestrictions();
    }

    public long getSuppressedEffects() {
        return this.mSuppressedEffects;
    }

    public int getZenMode() {
        return this.mZenMode;
    }

    public java.util.List<android.service.notification.ZenModeConfig.ZenRule> getZenRules() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return arrayList;
                }
                for (android.service.notification.ZenModeConfig.ZenRule zenRule : this.mConfig.automaticRules.values()) {
                    if (canManageAutomaticZenRule(zenRule)) {
                        arrayList.add(zenRule);
                    }
                }
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    java.util.Map<java.lang.String, android.app.AutomaticZenRule> getAutomaticZenRules() {
        java.util.List<android.service.notification.ZenModeConfig.ZenRule> zenRules = getZenRules();
        java.util.HashMap hashMap = new java.util.HashMap(zenRules.size());
        for (android.service.notification.ZenModeConfig.ZenRule zenRule : zenRules) {
            hashMap.put(zenRule.id, zenRuleToAutomaticZenRule(zenRule));
        }
        return hashMap;
    }

    public android.app.AutomaticZenRule getAutomaticZenRule(java.lang.String str) {
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return null;
                }
                android.service.notification.ZenModeConfig.ZenRule zenRule = (android.service.notification.ZenModeConfig.ZenRule) this.mConfig.automaticRules.get(str);
                if (zenRule != null && canManageAutomaticZenRule(zenRule)) {
                    return zenRuleToAutomaticZenRule(zenRule);
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.lang.String addAutomaticZenRule(java.lang.String str, android.app.AutomaticZenRule automaticZenRule, int i, java.lang.String str2, int i2) {
        java.lang.String str3;
        requirePublicOrigin("addAutomaticZenRule", i);
        if (!"android".equals(str)) {
            android.content.pm.PackageItemInfo serviceInfo = getServiceInfo(automaticZenRule.getOwner());
            if (serviceInfo == null) {
                serviceInfo = getActivityInfo(automaticZenRule.getConfigurationActivity());
            }
            if (serviceInfo == null) {
                throw new java.lang.IllegalArgumentException("Lacking enabled CPS or config activity");
            }
            int i3 = serviceInfo.metaData != null ? serviceInfo.metaData.getInt("android.service.zen.automatic.ruleInstanceLimit", -1) : -1;
            int currentInstanceCount = getCurrentInstanceCount(automaticZenRule.getOwner()) + getCurrentInstanceCount(automaticZenRule.getConfigurationActivity()) + 1;
            if (getPackageRuleCount(str) + 1 > 100 || (i3 > 0 && i3 < currentInstanceCount)) {
                throw new java.lang.IllegalArgumentException("Rule instance limit exceeded");
            }
        }
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    throw new android.util.AndroidRuntimeException("Could not create rule");
                }
                if (DEBUG) {
                    android.util.Log.d(TAG, "addAutomaticZenRule rule= " + automaticZenRule + " reason=" + str2);
                }
                android.service.notification.ZenModeConfig copy = this.mConfig.copy();
                android.service.notification.ZenModeConfig.ZenRule zenRule = new android.service.notification.ZenModeConfig.ZenRule();
                populateZenRule(str, automaticZenRule, zenRule, i, true);
                android.service.notification.ZenModeConfig.ZenRule maybeRestoreRemovedRule = maybeRestoreRemovedRule(copy, zenRule, automaticZenRule, i);
                copy.automaticRules.put(maybeRestoreRemovedRule.id, maybeRestoreRemovedRule);
                maybeReplaceDefaultRule(copy, automaticZenRule);
                if (setConfigLocked(copy, i, str2, maybeRestoreRemovedRule.component, true, i2)) {
                    str3 = maybeRestoreRemovedRule.id;
                } else {
                    throw new android.util.AndroidRuntimeException("Could not create rule");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return str3;
    }

    private android.service.notification.ZenModeConfig.ZenRule maybeRestoreRemovedRule(android.service.notification.ZenModeConfig zenModeConfig, android.service.notification.ZenModeConfig.ZenRule zenRule, android.app.AutomaticZenRule automaticZenRule, int i) {
        if (!android.app.Flags.modesApi()) {
            return zenRule;
        }
        java.lang.String deletedRuleKey = android.service.notification.ZenModeConfig.deletedRuleKey(zenRule);
        if (deletedRuleKey == null) {
            return zenRule;
        }
        android.service.notification.ZenModeConfig.ZenRule zenRule2 = (android.service.notification.ZenModeConfig.ZenRule) zenModeConfig.deletedRules.get(deletedRuleKey);
        if (zenRule2 == null) {
            return zenRule;
        }
        zenModeConfig.deletedRules.remove(deletedRuleKey);
        zenRule2.deletionInstant = null;
        if (i != 4) {
            return zenRule;
        }
        populateZenRule(zenRule2.pkg, automaticZenRule, zenRule2, i, false);
        return zenRule2;
    }

    private static void maybeReplaceDefaultRule(android.service.notification.ZenModeConfig zenModeConfig, android.app.AutomaticZenRule automaticZenRule) {
        android.service.notification.ZenModeConfig.ZenRule zenRule;
        if (android.app.Flags.modesApi() && automaticZenRule.getType() == 3 && (zenRule = (android.service.notification.ZenModeConfig.ZenRule) zenModeConfig.automaticRules.get("EVERY_NIGHT_DEFAULT_RULE")) != null && !zenRule.enabled && zenRule.canBeUpdatedByApp()) {
            zenModeConfig.automaticRules.remove("EVERY_NIGHT_DEFAULT_RULE");
        }
    }

    public boolean updateAutomaticZenRule(java.lang.String str, android.app.AutomaticZenRule automaticZenRule, int i, java.lang.String str2, int i2) {
        int i3;
        requirePublicOrigin("updateAutomaticZenRule", i);
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return false;
                }
                if (DEBUG) {
                    android.util.Log.d(TAG, "updateAutomaticZenRule zenRule=" + automaticZenRule + " reason=" + str2);
                }
                android.service.notification.ZenModeConfig copy = this.mConfig.copy();
                if (str == null) {
                    throw new java.lang.IllegalArgumentException("Rule doesn't exist");
                }
                android.service.notification.ZenModeConfig.ZenRule zenRule = (android.service.notification.ZenModeConfig.ZenRule) copy.automaticRules.get(str);
                if (zenRule == null || !canManageAutomaticZenRule(zenRule)) {
                    throw new java.lang.SecurityException("Cannot update rules not owned by your condition provider");
                }
                if (!android.app.Flags.modesApi() && zenRule.enabled != automaticZenRule.isEnabled()) {
                    int i4 = this.mConfig.user;
                    java.lang.String pkg = zenRule.getPkg();
                    if (automaticZenRule.isEnabled()) {
                        i3 = 1;
                    } else {
                        i3 = 2;
                    }
                    dispatchOnAutomaticRuleStatusChanged(i4, pkg, str, i3);
                }
                populateZenRule(zenRule.pkg, automaticZenRule, zenRule, i, false);
                return setConfigLocked(copy, i, str2, zenRule.component, true, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void applyGlobalZenModeAsImplicitZenRule(java.lang.String str, int i, int i2) {
        if (!android.app.Flags.modesApi()) {
            android.util.Log.wtf(TAG, "applyGlobalZenModeAsImplicitZenRule called with flag off!");
            return;
        }
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return;
                }
                android.service.notification.ZenModeConfig copy = this.mConfig.copy();
                android.service.notification.ZenModeConfig.ZenRule zenRule = (android.service.notification.ZenModeConfig.ZenRule) copy.automaticRules.get(implicitRuleId(str));
                if (i2 == 0) {
                    if (zenRule != null) {
                        setAutomaticZenRuleStateLocked(copy, java.util.Collections.singletonList(zenRule), new android.service.notification.Condition(zenRule.conditionId, this.mContext.getString(android.R.string.window_magnification_prompt_title), 0), 4, i);
                    }
                } else {
                    if (zenRule == null) {
                        zenRule = newImplicitZenRule(str);
                        zenRule.zenPolicy = this.mConfig.toZenPolicy();
                        copy.automaticRules.put(zenRule.id, zenRule);
                    }
                    if ((zenRule.userModifiedFields & 2) == 0) {
                        zenRule.zenMode = i2;
                    }
                    zenRule.snoozing = false;
                    zenRule.condition = new android.service.notification.Condition(zenRule.conditionId, this.mContext.getString(android.R.string.window_magnification_prompt_content), 1);
                    setConfigLocked(copy, null, 4, "applyGlobalZenModeAsImplicitZenRule", i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void applyGlobalPolicyAsImplicitZenRule(java.lang.String str, int i, android.app.NotificationManager.Policy policy) {
        boolean z;
        if (!android.app.Flags.modesApi()) {
            android.util.Log.wtf(TAG, "applyGlobalPolicyAsImplicitZenRule called with flag off!");
            return;
        }
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return;
                }
                android.service.notification.ZenModeConfig copy = this.mConfig.copy();
                android.service.notification.ZenModeConfig.ZenRule zenRule = (android.service.notification.ZenModeConfig.ZenRule) copy.automaticRules.get(implicitRuleId(str));
                if (zenRule != null) {
                    z = false;
                } else {
                    zenRule = newImplicitZenRule(str);
                    z = true;
                    zenRule.zenMode = 1;
                    copy.automaticRules.put(zenRule.id, zenRule);
                }
                if (zenRule.zenPolicyUserModifiedFields == 0) {
                    android.service.notification.ZenPolicy notificationPolicyToZenPolicy = com.android.server.notification.ZenAdapters.notificationPolicyToZenPolicy(policy);
                    if (z) {
                        notificationPolicyToZenPolicy = this.mConfig.toZenPolicy().overwrittenWith(notificationPolicyToZenPolicy);
                    }
                    updatePolicy(zenRule, notificationPolicyToZenPolicy, false, z);
                    setConfigLocked(copy, null, 4, "applyGlobalPolicyAsImplicitZenRule", i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    android.app.NotificationManager.Policy getNotificationPolicyFromImplicitZenRule(java.lang.String str) {
        if (!android.app.Flags.modesApi()) {
            android.util.Log.wtf(TAG, "getNotificationPolicyFromImplicitZenRule called with flag off!");
            return getNotificationPolicy();
        }
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return null;
                }
                android.service.notification.ZenModeConfig.ZenRule zenRule = (android.service.notification.ZenModeConfig.ZenRule) this.mConfig.automaticRules.get(implicitRuleId(str));
                if (zenRule != null && zenRule.zenPolicy != null) {
                    return this.mConfig.toNotificationPolicy(zenRule.zenPolicy);
                }
                return getNotificationPolicy();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.service.notification.ZenModeConfig.ZenRule newImplicitZenRule(final java.lang.String str) {
        final android.service.notification.ZenModeConfig.ZenRule zenRule = new android.service.notification.ZenModeConfig.ZenRule();
        zenRule.id = implicitRuleId(str);
        zenRule.pkg = str;
        zenRule.creationTime = this.mClock.millis();
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.ZenModeHelper$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                com.android.server.notification.ZenModeHelper.this.lambda$newImplicitZenRule$0(str, zenRule);
            }
        });
        zenRule.type = 0;
        zenRule.triggerDescription = this.mContext.getString(android.R.string.wireless_display_route_description, zenRule.name);
        zenRule.condition = null;
        zenRule.conditionId = new android.net.Uri.Builder().scheme("condition").authority("android").appendPath("implicit").appendPath(str).build();
        zenRule.enabled = true;
        zenRule.modified = false;
        zenRule.component = null;
        zenRule.configurationActivity = null;
        return zenRule;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$newImplicitZenRule$0(java.lang.String str, android.service.notification.ZenModeConfig.ZenRule zenRule) throws java.lang.Exception {
        try {
            android.content.pm.ApplicationInfo applicationInfo = this.mPm.getApplicationInfo(str, 0);
            zenRule.name = applicationInfo.loadLabel(this.mPm).toString();
            zenRule.iconResName = drawableResIdToResName(str, applicationInfo.icon);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, "Package not found for creating implicit zen rule");
            zenRule.name = "Unknown";
        }
    }

    private static java.lang.String implicitRuleId(java.lang.String str) {
        return IMPLICIT_RULE_ID_PREFIX + str;
    }

    static boolean isImplicitRuleId(@android.annotation.NonNull java.lang.String str) {
        return str.startsWith(IMPLICIT_RULE_ID_PREFIX);
    }

    boolean removeAutomaticZenRule(java.lang.String str, int i, java.lang.String str2, int i2) {
        requirePublicOrigin("removeAutomaticZenRule", i);
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return false;
                }
                android.service.notification.ZenModeConfig copy = this.mConfig.copy();
                android.service.notification.ZenModeConfig.ZenRule zenRule = (android.service.notification.ZenModeConfig.ZenRule) copy.automaticRules.get(str);
                if (zenRule == null) {
                    return false;
                }
                if (canManageAutomaticZenRule(zenRule)) {
                    copy.automaticRules.remove(str);
                    maybePreserveRemovedRule(copy, zenRule, i);
                    if (zenRule.getPkg() != null && !"android".equals(zenRule.getPkg())) {
                        for (android.service.notification.ZenModeConfig.ZenRule zenRule2 : copy.automaticRules.values()) {
                            if (zenRule2.getPkg() != null && zenRule2.getPkg().equals(zenRule.getPkg())) {
                                break;
                            }
                        }
                        this.mRulesUidCache.remove(getPackageUserKey(zenRule.getPkg(), copy.user));
                    }
                    if (DEBUG) {
                        android.util.Log.d(TAG, "removeZenRule zenRule=" + str + " reason=" + str2);
                    }
                    dispatchOnAutomaticRuleStatusChanged(this.mConfig.user, zenRule.getPkg(), str, 3);
                    return setConfigLocked(copy, i, str2, null, true, i2);
                }
                throw new java.lang.SecurityException("Cannot delete rules not owned by your condition provider");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean removeAutomaticZenRules(java.lang.String str, int i, java.lang.String str2, int i2) {
        requirePublicOrigin("removeAutomaticZenRules", i);
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return false;
                }
                android.service.notification.ZenModeConfig copy = this.mConfig.copy();
                for (int size = copy.automaticRules.size() - 1; size >= 0; size--) {
                    android.service.notification.ZenModeConfig.ZenRule zenRule = (android.service.notification.ZenModeConfig.ZenRule) copy.automaticRules.get(copy.automaticRules.keyAt(size));
                    if (java.util.Objects.equals(zenRule.getPkg(), str) && canManageAutomaticZenRule(zenRule)) {
                        copy.automaticRules.removeAt(size);
                        maybePreserveRemovedRule(copy, zenRule, i);
                    }
                }
                if (i == 5) {
                    for (int size2 = copy.deletedRules.size() - 1; size2 >= 0; size2--) {
                        if (java.util.Objects.equals(((android.service.notification.ZenModeConfig.ZenRule) copy.deletedRules.get(copy.deletedRules.keyAt(size2))).getPkg(), str)) {
                            copy.deletedRules.removeAt(size2);
                        }
                    }
                }
                return setConfigLocked(copy, i, str2, null, true, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void maybePreserveRemovedRule(android.service.notification.ZenModeConfig zenModeConfig, android.service.notification.ZenModeConfig.ZenRule zenRule, int i) {
        java.lang.String deletedRuleKey;
        if (android.app.Flags.modesApi() && i == 4 && !zenRule.canBeUpdatedByApp() && !"android".equals(zenRule.pkg) && (deletedRuleKey = android.service.notification.ZenModeConfig.deletedRuleKey(zenRule)) != null) {
            android.service.notification.ZenModeConfig.ZenRule copy = zenRule.copy();
            copy.deletionInstant = java.time.Instant.now(this.mClock);
            copy.snoozing = false;
            copy.condition = null;
            zenModeConfig.deletedRules.put(deletedRuleKey, copy);
        }
    }

    int getAutomaticZenRuleState(java.lang.String str) {
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return 2;
                }
                android.service.notification.ZenModeConfig.ZenRule zenRule = (android.service.notification.ZenModeConfig.ZenRule) this.mConfig.automaticRules.get(str);
                if (zenRule == null || !canManageAutomaticZenRule(zenRule)) {
                    return 2;
                }
                return zenRule.condition != null ? zenRule.condition.state : 0;
            } finally {
            }
        }
    }

    void setAutomaticZenRuleState(java.lang.String str, android.service.notification.Condition condition, int i, int i2) {
        requirePublicOrigin("setAutomaticZenRuleState", i);
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return;
                }
                android.service.notification.ZenModeConfig copy = this.mConfig.copy();
                android.service.notification.ZenModeConfig.ZenRule zenRule = (android.service.notification.ZenModeConfig.ZenRule) copy.automaticRules.get(str);
                if (android.app.Flags.modesApi()) {
                    if (zenRule != null && canManageAutomaticZenRule(zenRule)) {
                        setAutomaticZenRuleStateLocked(copy, java.util.Collections.singletonList(zenRule), condition, i, i2);
                    }
                } else {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    arrayList.add(zenRule);
                    setAutomaticZenRuleStateLocked(copy, arrayList, condition, i, i2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setAutomaticZenRuleState(android.net.Uri uri, android.service.notification.Condition condition, int i, int i2) {
        requirePublicOrigin("setAutomaticZenRuleState", i);
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return;
                }
                android.service.notification.ZenModeConfig copy = this.mConfig.copy();
                java.util.List<android.service.notification.ZenModeConfig.ZenRule> findMatchingRules = findMatchingRules(copy, uri, condition);
                if (android.app.Flags.modesApi()) {
                    for (int size = findMatchingRules.size() - 1; size >= 0; size--) {
                        if (!canManageAutomaticZenRule(findMatchingRules.get(size))) {
                            findMatchingRules.remove(size);
                        }
                    }
                }
                setAutomaticZenRuleStateLocked(copy, findMatchingRules, condition, i, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mConfigLock"})
    private void setAutomaticZenRuleStateLocked(android.service.notification.ZenModeConfig zenModeConfig, java.util.List<android.service.notification.ZenModeConfig.ZenRule> list, android.service.notification.Condition condition, int i, int i2) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (android.app.Flags.modesApi() && condition.source == 1) {
            i = 3;
        }
        for (android.service.notification.ZenModeConfig.ZenRule zenRule : list) {
            zenRule.condition = condition;
            updateSnoozing(zenRule);
            setConfigLocked(zenModeConfig, zenRule.component, i, "conditionChanged", i2);
        }
    }

    private static java.util.List<android.service.notification.ZenModeConfig.ZenRule> findMatchingRules(android.service.notification.ZenModeConfig zenModeConfig, android.net.Uri uri, android.service.notification.Condition condition) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (ruleMatches(uri, condition, zenModeConfig.manualRule)) {
            arrayList.add(zenModeConfig.manualRule);
        } else {
            for (android.service.notification.ZenModeConfig.ZenRule zenRule : zenModeConfig.automaticRules.values()) {
                if (ruleMatches(uri, condition, zenRule)) {
                    arrayList.add(zenRule);
                }
            }
        }
        return arrayList;
    }

    private static boolean ruleMatches(android.net.Uri uri, android.service.notification.Condition condition, android.service.notification.ZenModeConfig.ZenRule zenRule) {
        if (uri == null || zenRule == null || zenRule.conditionId == null || !zenRule.conditionId.equals(uri) || java.util.Objects.equals(condition, zenRule.condition)) {
            return false;
        }
        return true;
    }

    private boolean updateSnoozing(android.service.notification.ZenModeConfig.ZenRule zenRule) {
        if (zenRule == null || !zenRule.snoozing || zenRule.isTrueOrUnknown()) {
            return false;
        }
        zenRule.snoozing = false;
        if (DEBUG) {
            android.util.Log.d(TAG, "Snoozing reset for " + zenRule.conditionId);
            return true;
        }
        return true;
    }

    public int getCurrentInstanceCount(android.content.ComponentName componentName) {
        int i = 0;
        if (componentName == null) {
            return 0;
        }
        synchronized (this.mConfigLock) {
            try {
                for (android.service.notification.ZenModeConfig.ZenRule zenRule : this.mConfig.automaticRules.values()) {
                    if (componentName.equals(zenRule.component) || componentName.equals(zenRule.configurationActivity)) {
                        i++;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    private int getPackageRuleCount(java.lang.String str) {
        int i = 0;
        if (str == null) {
            return 0;
        }
        synchronized (this.mConfigLock) {
            try {
                java.util.Iterator it = this.mConfig.automaticRules.values().iterator();
                while (it.hasNext()) {
                    if (str.equals(((android.service.notification.ZenModeConfig.ZenRule) it.next()).getPkg())) {
                        i++;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public boolean canManageAutomaticZenRule(android.service.notification.ZenModeConfig.ZenRule zenRule) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid == 0 || callingUid == 1000 || this.mContext.checkCallingPermission("android.permission.MANAGE_NOTIFICATIONS") == 0) {
            return true;
        }
        java.lang.String[] packagesForUid = this.mPm.getPackagesForUid(android.os.Binder.getCallingUid());
        if (packagesForUid != null) {
            for (java.lang.String str : packagesForUid) {
                if (str.equals(zenRule.getPkg())) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void updateDefaultZenRules(int i) {
        updateDefaultAutomaticRuleNames();
        synchronized (this.mConfigLock) {
            try {
                for (android.service.notification.ZenModeConfig.ZenRule zenRule : this.mDefaultConfig.automaticRules.values()) {
                    android.service.notification.ZenModeConfig.ZenRule zenRule2 = (android.service.notification.ZenModeConfig.ZenRule) this.mConfig.automaticRules.get(zenRule.id);
                    if (zenRule2 != null && !zenRule2.modified && !zenRule2.enabled && !zenRule.name.equals(zenRule2.name) && canManageAutomaticZenRule(zenRule2)) {
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "Locale change - updating default zen rule name from " + zenRule2.name + " to " + zenRule.name);
                        }
                        zenRule2.name = zenRule.name;
                        updateAutomaticZenRule(zenRule.id, zenRuleToAutomaticZenRule(zenRule2), 5, "locale changed", i);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.content.pm.ServiceInfo getServiceInfo(android.content.ComponentName componentName) {
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(componentName);
        java.util.List queryIntentServicesAsUser = this.mPm.queryIntentServicesAsUser(intent, 132, android.os.UserHandle.getCallingUserId());
        if (queryIntentServicesAsUser != null) {
            int size = queryIntentServicesAsUser.size();
            for (int i = 0; i < size; i++) {
                android.content.pm.ServiceInfo serviceInfo = ((android.content.pm.ResolveInfo) queryIntentServicesAsUser.get(i)).serviceInfo;
                if (this.mServiceConfig.bindPermission.equals(serviceInfo.permission)) {
                    return serviceInfo;
                }
            }
            return null;
        }
        return null;
    }

    private android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName componentName) {
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(componentName);
        java.util.List queryIntentActivitiesAsUser = this.mPm.queryIntentActivitiesAsUser(intent, 129, android.os.UserHandle.getCallingUserId());
        if (queryIntentActivitiesAsUser != null && queryIntentActivitiesAsUser.size() > 0) {
            return ((android.content.pm.ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo;
        }
        return null;
    }

    private void populateZenRule(java.lang.String str, android.app.AutomaticZenRule automaticZenRule, android.service.notification.ZenModeConfig.ZenRule zenRule, int i, boolean z) {
        if (android.app.Flags.modesApi()) {
            if (z) {
                zenRule.id = android.service.notification.ZenModeConfig.newRuleId();
                zenRule.creationTime = this.mClock.millis();
                zenRule.component = automaticZenRule.getOwner();
                zenRule.pkg = str;
            }
            zenRule.condition = null;
            zenRule.conditionId = automaticZenRule.getConditionId();
            if (zenRule.enabled != automaticZenRule.isEnabled()) {
                zenRule.snoozing = false;
            }
            zenRule.enabled = automaticZenRule.isEnabled();
            zenRule.configurationActivity = automaticZenRule.getConfigurationActivity();
            zenRule.allowManualInvocation = automaticZenRule.isManualInvocationAllowed();
            zenRule.iconResName = drawableResIdToResName(zenRule.pkg, automaticZenRule.getIconResId());
            zenRule.triggerDescription = automaticZenRule.getTriggerDescription();
            zenRule.type = automaticZenRule.getType();
            zenRule.modified = automaticZenRule.isModified();
            java.lang.String str2 = zenRule.name;
            if (z || doesOriginAlwaysUpdateValues(i) || (zenRule.userModifiedFields & 1) == 0) {
                zenRule.name = automaticZenRule.getName();
            }
            if (!(z || doesOriginAlwaysUpdateValues(i) || zenRule.canBeUpdatedByApp())) {
                return;
            }
            boolean z2 = i == 3;
            if (z2 && !android.text.TextUtils.equals(str2, automaticZenRule.getName())) {
                zenRule.userModifiedFields |= 1;
            }
            int zenModeFromInterruptionFilter = android.app.NotificationManager.zenModeFromInterruptionFilter(automaticZenRule.getInterruptionFilter(), 0);
            if (z2 && zenRule.zenMode != zenModeFromInterruptionFilter) {
                zenRule.userModifiedFields |= 2;
            }
            zenRule.zenMode = zenModeFromInterruptionFilter;
            updatePolicy(zenRule, automaticZenRule.getZenPolicy(), z2, z);
            updateZenDeviceEffects(zenRule, automaticZenRule.getDeviceEffects(), i == 4, z2);
            return;
        }
        if (zenRule.enabled != automaticZenRule.isEnabled()) {
            zenRule.snoozing = false;
        }
        zenRule.name = automaticZenRule.getName();
        zenRule.condition = null;
        zenRule.conditionId = automaticZenRule.getConditionId();
        zenRule.enabled = automaticZenRule.isEnabled();
        zenRule.modified = automaticZenRule.isModified();
        zenRule.zenPolicy = automaticZenRule.getZenPolicy();
        zenRule.zenMode = android.app.NotificationManager.zenModeFromInterruptionFilter(automaticZenRule.getInterruptionFilter(), 0);
        zenRule.configurationActivity = automaticZenRule.getConfigurationActivity();
        if (z) {
            zenRule.id = android.service.notification.ZenModeConfig.newRuleId();
            zenRule.creationTime = java.lang.System.currentTimeMillis();
            zenRule.component = automaticZenRule.getOwner();
            zenRule.pkg = str;
        }
    }

    private static boolean doesOriginAlwaysUpdateValues(int i) {
        return i == 3 || i == 5;
    }

    private void updatePolicy(android.service.notification.ZenModeConfig.ZenRule zenRule, @android.annotation.Nullable android.service.notification.ZenPolicy zenPolicy, boolean z, boolean z2) {
        if (zenPolicy == null) {
            if (z2) {
                zenRule.zenPolicy = this.mDefaultConfig.toZenPolicy();
                return;
            }
            return;
        }
        android.service.notification.ZenPolicy zenPolicy2 = zenRule.zenPolicy != null ? zenRule.zenPolicy : this.mDefaultConfig.toZenPolicy();
        android.service.notification.ZenPolicy overwrittenWith = zenPolicy2.overwrittenWith(zenPolicy);
        zenRule.zenPolicy = overwrittenWith;
        if (z) {
            int i = zenRule.zenPolicyUserModifiedFields;
            if (zenPolicy2.getPriorityMessageSenders() != overwrittenWith.getPriorityMessageSenders()) {
                i |= 1;
            }
            if (zenPolicy2.getPriorityCallSenders() != overwrittenWith.getPriorityCallSenders()) {
                i |= 2;
            }
            if (zenPolicy2.getPriorityConversationSenders() != overwrittenWith.getPriorityConversationSenders()) {
                i |= 4;
            }
            if (zenPolicy2.getPriorityChannelsAllowed() != overwrittenWith.getPriorityChannelsAllowed()) {
                i |= 8;
            }
            if (zenPolicy2.getPriorityCategoryReminders() != overwrittenWith.getPriorityCategoryReminders()) {
                i |= 16;
            }
            if (zenPolicy2.getPriorityCategoryEvents() != overwrittenWith.getPriorityCategoryEvents()) {
                i |= 32;
            }
            if (zenPolicy2.getPriorityCategoryRepeatCallers() != overwrittenWith.getPriorityCategoryRepeatCallers()) {
                i |= 64;
            }
            if (zenPolicy2.getPriorityCategoryAlarms() != overwrittenWith.getPriorityCategoryAlarms()) {
                i |= 128;
            }
            if (zenPolicy2.getPriorityCategoryMedia() != overwrittenWith.getPriorityCategoryMedia()) {
                i |= 256;
            }
            if (zenPolicy2.getPriorityCategorySystem() != overwrittenWith.getPriorityCategorySystem()) {
                i |= 512;
            }
            if (zenPolicy2.getVisualEffectFullScreenIntent() != overwrittenWith.getVisualEffectFullScreenIntent()) {
                i |= 1024;
            }
            if (zenPolicy2.getVisualEffectLights() != overwrittenWith.getVisualEffectLights()) {
                i |= 2048;
            }
            if (zenPolicy2.getVisualEffectPeek() != overwrittenWith.getVisualEffectPeek()) {
                i |= 4096;
            }
            if (zenPolicy2.getVisualEffectStatusBar() != overwrittenWith.getVisualEffectStatusBar()) {
                i |= 8192;
            }
            if (zenPolicy2.getVisualEffectBadge() != overwrittenWith.getVisualEffectBadge()) {
                i |= 16384;
            }
            if (zenPolicy2.getVisualEffectAmbient() != overwrittenWith.getVisualEffectAmbient()) {
                i |= 32768;
            }
            if (zenPolicy2.getVisualEffectNotificationList() != overwrittenWith.getVisualEffectNotificationList()) {
                i |= 65536;
            }
            zenRule.zenPolicyUserModifiedFields = i;
        }
    }

    private static void updateZenDeviceEffects(android.service.notification.ZenModeConfig.ZenRule zenRule, @android.annotation.Nullable android.service.notification.ZenDeviceEffects zenDeviceEffects, boolean z, boolean z2) {
        android.service.notification.ZenDeviceEffects build;
        if (zenDeviceEffects == null) {
            return;
        }
        if (zenRule.zenDeviceEffects != null) {
            build = zenRule.zenDeviceEffects;
        } else {
            build = new android.service.notification.ZenDeviceEffects.Builder().build();
        }
        if (z) {
            zenDeviceEffects = new android.service.notification.ZenDeviceEffects.Builder(zenDeviceEffects).setShouldDisableAutoBrightness(build.shouldDisableAutoBrightness()).setShouldDisableTapToWake(build.shouldDisableTapToWake()).setShouldDisableTiltToWake(build.shouldDisableTiltToWake()).setShouldDisableTouch(build.shouldDisableTouch()).setShouldMinimizeRadioUsage(build.shouldMinimizeRadioUsage()).setShouldMaximizeDoze(build.shouldMaximizeDoze()).setExtraEffects(build.getExtraEffects()).build();
        }
        zenRule.zenDeviceEffects = zenDeviceEffects;
        if (z2) {
            int i = zenRule.zenDeviceEffectsUserModifiedFields;
            if (build.shouldDisplayGrayscale() != zenDeviceEffects.shouldDisplayGrayscale()) {
                i |= 1;
            }
            if (build.shouldSuppressAmbientDisplay() != zenDeviceEffects.shouldSuppressAmbientDisplay()) {
                i |= 2;
            }
            if (build.shouldDimWallpaper() != zenDeviceEffects.shouldDimWallpaper()) {
                i |= 4;
            }
            if (build.shouldUseNightMode() != zenDeviceEffects.shouldUseNightMode()) {
                i |= 8;
            }
            if (build.shouldDisableAutoBrightness() != zenDeviceEffects.shouldDisableAutoBrightness()) {
                i |= 16;
            }
            if (build.shouldDisableTapToWake() != zenDeviceEffects.shouldDisableTapToWake()) {
                i |= 32;
            }
            if (build.shouldDisableTiltToWake() != zenDeviceEffects.shouldDisableTiltToWake()) {
                i |= 64;
            }
            if (build.shouldDisableTouch() != zenDeviceEffects.shouldDisableTouch()) {
                i |= 128;
            }
            if (build.shouldMinimizeRadioUsage() != zenDeviceEffects.shouldMinimizeRadioUsage()) {
                i |= 256;
            }
            if (build.shouldMaximizeDoze() != zenDeviceEffects.shouldMaximizeDoze()) {
                i |= 512;
            }
            if (!java.util.Objects.equals(build.getExtraEffects(), zenDeviceEffects.getExtraEffects())) {
                i |= 1024;
            }
            zenRule.zenDeviceEffectsUserModifiedFields = i;
        }
    }

    private android.app.AutomaticZenRule zenRuleToAutomaticZenRule(android.service.notification.ZenModeConfig.ZenRule zenRule) {
        android.app.AutomaticZenRule automaticZenRule;
        if (android.app.Flags.modesApi()) {
            automaticZenRule = new android.app.AutomaticZenRule.Builder(zenRule.name, zenRule.conditionId).setManualInvocationAllowed(zenRule.allowManualInvocation).setCreationTime(zenRule.creationTime).setIconResId(drawableResNameToResId(zenRule.pkg, zenRule.iconResName)).setType(zenRule.type).setZenPolicy(zenRule.zenPolicy).setDeviceEffects(zenRule.zenDeviceEffects).setEnabled(zenRule.enabled).setInterruptionFilter(android.app.NotificationManager.zenModeToInterruptionFilter(zenRule.zenMode)).setOwner(zenRule.component).setConfigurationActivity(zenRule.configurationActivity).setTriggerDescription(zenRule.triggerDescription).build();
        } else {
            automaticZenRule = new android.app.AutomaticZenRule(zenRule.name, zenRule.component, zenRule.configurationActivity, zenRule.conditionId, zenRule.zenPolicy, android.app.NotificationManager.zenModeToInterruptionFilter(zenRule.zenMode), zenRule.enabled, zenRule.creationTime);
        }
        automaticZenRule.setPackageName(zenRule.pkg);
        return automaticZenRule;
    }

    @android.annotation.SuppressLint({"MissingPermission"})
    void scheduleActivationBroadcast(java.lang.String str, int i, java.lang.String str2, boolean z) {
        int i2;
        if (android.app.compat.CompatChanges.isChangeEnabled(SEND_ACTIVATION_AZR_STATUSES, str, android.os.UserHandle.of(i))) {
            if (z) {
                i2 = 4;
            } else {
                i2 = 5;
            }
            dispatchOnAutomaticRuleStatusChanged(i, str, str2, i2);
            return;
        }
        dispatchOnAutomaticRuleStatusChanged(i, str, str2, -1);
    }

    void scheduleEnabledBroadcast(java.lang.String str, int i, java.lang.String str2, boolean z) {
        int i2;
        if (z) {
            i2 = 1;
        } else {
            i2 = 2;
        }
        dispatchOnAutomaticRuleStatusChanged(i, str, str2, i2);
    }

    void setManualZenMode(int i, android.net.Uri uri, int i2, java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3) {
        setManualZenMode(i, uri, i2, str, str2, true, i3);
        android.provider.Settings.Secure.putInt(this.mContext.getContentResolver(), "show_zen_settings_suggestion", 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setManualZenMode(int i, android.net.Uri uri, int i2, java.lang.String str, @android.annotation.Nullable java.lang.String str2, boolean z, int i3) {
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return;
                }
                if (android.provider.Settings.Global.isValidZenMode(i)) {
                    if (DEBUG) {
                        android.util.Log.d(TAG, "setManualZenMode " + android.provider.Settings.Global.zenModeToString(i) + " conditionId=" + uri + " reason=" + str + " setRingerMode=" + z);
                    }
                    android.service.notification.ZenModeConfig copy = this.mConfig.copy();
                    if (i == 0) {
                        copy.manualRule = null;
                        for (android.service.notification.ZenModeConfig.ZenRule zenRule : copy.automaticRules.values()) {
                            if (zenRule.isAutomaticActive()) {
                                zenRule.snoozing = true;
                            }
                        }
                    } else {
                        android.service.notification.ZenModeConfig.ZenRule zenRule2 = new android.service.notification.ZenModeConfig.ZenRule();
                        zenRule2.enabled = true;
                        zenRule2.zenMode = i;
                        zenRule2.conditionId = uri;
                        zenRule2.enabler = str2;
                        if (android.app.Flags.modesApi()) {
                            zenRule2.allowManualInvocation = true;
                        }
                        copy.manualRule = zenRule2;
                    }
                    setConfigLocked(copy, i2, str, null, z, i3);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dump(android.util.proto.ProtoOutputStream protoOutputStream) {
        protoOutputStream.write(1159641169921L, this.mZenMode);
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig.manualRule != null) {
                    this.mConfig.manualRule.dumpDebug(protoOutputStream, 2246267895810L);
                }
                for (android.service.notification.ZenModeConfig.ZenRule zenRule : this.mConfig.automaticRules.values()) {
                    if (zenRule.enabled && zenRule.condition != null && zenRule.condition.state == 1 && !zenRule.snoozing) {
                        zenRule.dumpDebug(protoOutputStream, 2246267895810L);
                    }
                }
                this.mConfig.toNotificationPolicy().dumpDebug(protoOutputStream, 1146756268037L);
                protoOutputStream.write(1120986464259L, this.mSuppressedEffects);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("mZenMode=");
        printWriter.println(android.provider.Settings.Global.zenModeToString(this.mZenMode));
        printWriter.print(str);
        printWriter.println("mConsolidatedPolicy=" + this.mConsolidatedPolicy.toString());
        synchronized (this.mConfigsArrayLock) {
            try {
                int size = this.mConfigs.size();
                for (int i = 0; i < size; i++) {
                    dump(printWriter, str, "mConfigs[u=" + this.mConfigs.keyAt(i) + "]", this.mConfigs.valueAt(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        printWriter.print(str);
        printWriter.print("mUser=");
        printWriter.println(this.mUser);
        synchronized (this.mConfigLock) {
            dump(printWriter, str, "mConfig", this.mConfig);
        }
        printWriter.print(str);
        printWriter.print("mSuppressedEffects=");
        printWriter.println(this.mSuppressedEffects);
        this.mFiltering.dump(printWriter, str);
        this.mConditions.dump(printWriter, str);
    }

    private static void dump(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, android.service.notification.ZenModeConfig zenModeConfig) {
        printWriter.print(str);
        printWriter.print(str2);
        printWriter.print('=');
        if (zenModeConfig == null) {
            printWriter.println(zenModeConfig);
            return;
        }
        printWriter.printf("allow(alarms=%b,media=%b,system=%b,calls=%b,callsFrom=%s,repeatCallers=%b,messages=%b,messagesFrom=%s,conversations=%b,conversationsFrom=%s,events=%b,reminders=%b", java.lang.Boolean.valueOf(zenModeConfig.allowAlarms), java.lang.Boolean.valueOf(zenModeConfig.allowMedia), java.lang.Boolean.valueOf(zenModeConfig.allowSystem), java.lang.Boolean.valueOf(zenModeConfig.allowCalls), android.service.notification.ZenModeConfig.sourceToString(zenModeConfig.allowCallsFrom), java.lang.Boolean.valueOf(zenModeConfig.allowRepeatCallers), java.lang.Boolean.valueOf(zenModeConfig.allowMessages), android.service.notification.ZenModeConfig.sourceToString(zenModeConfig.allowMessagesFrom), java.lang.Boolean.valueOf(zenModeConfig.allowConversations), android.service.notification.ZenPolicy.conversationTypeToString(zenModeConfig.allowConversationsFrom), java.lang.Boolean.valueOf(zenModeConfig.allowEvents), java.lang.Boolean.valueOf(zenModeConfig.allowReminders));
        if (android.app.Flags.modesApi()) {
            printWriter.printf(",priorityChannels=%b", java.lang.Boolean.valueOf(zenModeConfig.allowPriorityChannels));
        }
        int i = 0;
        printWriter.printf(")\n", new java.lang.Object[0]);
        printWriter.print(str);
        printWriter.printf("  disallow(visualEffects=%s)\n", java.lang.Integer.valueOf(zenModeConfig.suppressedVisualEffects));
        printWriter.print(str);
        printWriter.print("  manualRule=");
        printWriter.println(zenModeConfig.manualRule);
        if (zenModeConfig.automaticRules.isEmpty()) {
            return;
        }
        int size = zenModeConfig.automaticRules.size();
        while (i < size) {
            printWriter.print(str);
            printWriter.print(i == 0 ? "  automaticRules=" : "                 ");
            printWriter.println(zenModeConfig.automaticRules.valueAt(i));
            i++;
        }
    }

    public void readXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, boolean z, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        boolean z2;
        java.lang.String str;
        android.service.notification.ZenModeConfig readXml = android.service.notification.ZenModeConfig.readXml(typedXmlPullParser);
        if (readXml != null) {
            if (z) {
                readXml.user = i;
                readXml.manualRule = null;
            }
            boolean containsAll = readXml.automaticRules.containsAll(android.service.notification.ZenModeConfig.DEFAULT_RULE_IDS);
            long millis = android.app.Flags.modesApi() ? this.mClock.millis() : java.lang.System.currentTimeMillis();
            if (readXml.automaticRules != null && readXml.automaticRules.size() > 0) {
                z2 = true;
                for (android.service.notification.ZenModeConfig.ZenRule zenRule : readXml.automaticRules.values()) {
                    if (z) {
                        zenRule.snoozing = false;
                        zenRule.condition = null;
                        zenRule.creationTime = millis;
                    }
                    z2 &= !zenRule.enabled;
                    if (android.app.Flags.modesApi() && readXml.version < 11) {
                        android.service.notification.ZenPolicy zenPolicy = readXml.toZenPolicy();
                        if (zenRule.zenPolicy == null) {
                            zenRule.zenPolicy = zenPolicy;
                        } else {
                            zenRule.zenPolicy = zenPolicy.overwrittenWith(zenRule.zenPolicy);
                        }
                    }
                }
            } else {
                z2 = true;
            }
            if (containsAll || !z2 || (!z && readXml.version >= 8)) {
                str = "readXml";
            } else {
                readXml.automaticRules = new android.util.ArrayMap();
                for (android.service.notification.ZenModeConfig.ZenRule zenRule2 : this.mDefaultConfig.automaticRules.values()) {
                    readXml.automaticRules.put(zenRule2.id, zenRule2);
                }
                str = "readXml, reset to default rules";
            }
            if (i == -1) {
                i = 0;
            }
            if (readXml.version < 8) {
                android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "show_zen_upgrade_notification", 1, i);
            } else {
                android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "zen_settings_updated", 1, i);
            }
            if (android.app.Flags.modesApi() && z) {
                readXml.deletedRules.clear();
            }
            if (DEBUG) {
                android.util.Log.d(TAG, str);
            }
            synchronized (this.mConfigLock) {
                setConfigLocked(readXml, null, z ? 6 : 1, str, 1000);
            }
        }
    }

    public void writeXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, boolean z, java.lang.Integer num, int i) throws java.io.IOException {
        synchronized (this.mConfigsArrayLock) {
            try {
                int size = this.mConfigs.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (!z || this.mConfigs.keyAt(i2) == i) {
                        this.mConfigs.valueAt(i2).writeXml(typedXmlSerializer, num, z);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.app.NotificationManager.Policy getNotificationPolicy() {
        android.app.NotificationManager.Policy notificationPolicy;
        synchronized (this.mConfigLock) {
            notificationPolicy = getNotificationPolicy(this.mConfig);
        }
        return notificationPolicy;
    }

    private static android.app.NotificationManager.Policy getNotificationPolicy(android.service.notification.ZenModeConfig zenModeConfig) {
        if (zenModeConfig == null) {
            return null;
        }
        return zenModeConfig.toNotificationPolicy();
    }

    public void setNotificationPolicy(android.app.NotificationManager.Policy policy, int i, int i2) {
        synchronized (this.mConfigLock) {
            if (policy != null) {
                try {
                    if (this.mConfig != null) {
                        android.service.notification.ZenModeConfig copy = this.mConfig.copy();
                        copy.applyNotificationPolicy(policy);
                        setConfigLocked(copy, null, i, "setNotificationPolicy", i2);
                    }
                } finally {
                }
            }
        }
    }

    private void cleanUpZenRules() {
        java.time.Instant minus = this.mClock.instant().minus((java.time.temporal.TemporalAmount) DELETED_RULE_KEPT_FOR);
        synchronized (this.mConfigLock) {
            try {
                android.service.notification.ZenModeConfig copy = this.mConfig.copy();
                deleteRulesWithoutOwner(copy.automaticRules);
                if (android.app.Flags.modesApi()) {
                    deleteRulesWithoutOwner(copy.deletedRules);
                    for (int size = copy.deletedRules.size() - 1; size >= 0; size--) {
                        android.service.notification.ZenModeConfig.ZenRule zenRule = (android.service.notification.ZenModeConfig.ZenRule) copy.deletedRules.valueAt(size);
                        if (zenRule.deletionInstant != null && !zenRule.deletionInstant.isBefore(minus)) {
                        }
                        copy.deletedRules.removeAt(size);
                    }
                }
                if (!copy.equals(this.mConfig)) {
                    setConfigLocked(copy, null, 5, "cleanUpZenRules", 1000);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void deleteRulesWithoutOwner(android.util.ArrayMap<java.lang.String, android.service.notification.ZenModeConfig.ZenRule> arrayMap) {
        long millis = android.app.Flags.modesApi() ? this.mClock.millis() : java.lang.System.currentTimeMillis();
        if (arrayMap != null) {
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                android.service.notification.ZenModeConfig.ZenRule valueAt = arrayMap.valueAt(size);
                if (259200000 < millis - valueAt.creationTime) {
                    try {
                        if (valueAt.getPkg() != null) {
                            this.mPm.getPackageInfo(valueAt.getPkg(), 4194304);
                        }
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        arrayMap.removeAt(size);
                    }
                }
            }
        }
    }

    public android.service.notification.ZenModeConfig getConfig() {
        android.service.notification.ZenModeConfig copy;
        synchronized (this.mConfigLock) {
            copy = this.mConfig.copy();
        }
        return copy;
    }

    public android.app.NotificationManager.Policy getConsolidatedNotificationPolicy() {
        return this.mConsolidatedPolicy.copy();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.service.notification.ZenPolicy getDefaultZenPolicy() {
        return this.mDefaultConfig.toZenPolicy();
    }

    @com.android.internal.annotations.GuardedBy({"mConfigLock"})
    private boolean setConfigLocked(android.service.notification.ZenModeConfig zenModeConfig, android.content.ComponentName componentName, int i, java.lang.String str, int i2) {
        return setConfigLocked(zenModeConfig, i, str, componentName, true, i2);
    }

    void setConfig(android.service.notification.ZenModeConfig zenModeConfig, android.content.ComponentName componentName, int i, java.lang.String str, int i2) {
        synchronized (this.mConfigLock) {
            setConfigLocked(zenModeConfig, componentName, i, str, i2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mConfigLock"})
    private boolean setConfigLocked(android.service.notification.ZenModeConfig zenModeConfig, int i, java.lang.String str, android.content.ComponentName componentName, boolean z, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (zenModeConfig != null) {
                if (zenModeConfig.isValid()) {
                    if (zenModeConfig.user != this.mUser) {
                        synchronized (this.mConfigsArrayLock) {
                            this.mConfigs.put(zenModeConfig.user, zenModeConfig);
                        }
                        if (DEBUG) {
                            android.util.Log.d(TAG, "setConfigLocked: store config for user " + zenModeConfig.user);
                        }
                        return true;
                    }
                    this.mConditions.evaluateConfig(zenModeConfig, null, false);
                    synchronized (this.mConfigsArrayLock) {
                        this.mConfigs.put(zenModeConfig.user, zenModeConfig);
                    }
                    if (DEBUG) {
                        android.util.Log.d(TAG, "setConfigLocked reason=" + str, new java.lang.Throwable());
                    }
                    com.android.server.notification.ZenLog.traceConfig(str, componentName, this.mConfig, zenModeConfig, i2);
                    android.app.NotificationManager.Policy notificationPolicy = getNotificationPolicy(zenModeConfig);
                    if (!java.util.Objects.equals(getNotificationPolicy(this.mConfig), notificationPolicy)) {
                        dispatchOnPolicyChanged(notificationPolicy);
                    }
                    updateConfigAndZenModeLocked(zenModeConfig, i, str, z, i2);
                    this.mConditions.evaluateConfig(zenModeConfig, componentName, true);
                    return true;
                }
            }
            android.util.Log.w(TAG, "Invalid config in setConfigLocked; " + zenModeConfig);
            return false;
        } catch (java.lang.SecurityException e) {
            android.util.Log.wtf(TAG, "Invalid rule in config", e);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mConfigLock"})
    private void updateConfigAndZenModeLocked(android.service.notification.ZenModeConfig zenModeConfig, int i, java.lang.String str, boolean z, int i2) {
        boolean isEnabled = this.mFlagResolver.isEnabled(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.NotificationFlags.LOG_DND_STATE_EVENTS);
        com.android.server.notification.ZenModeEventLogger.ZenModeInfo zenModeInfo = new com.android.server.notification.ZenModeEventLogger.ZenModeInfo(this.mZenMode, this.mConfig, this.mConsolidatedPolicy);
        if (!zenModeConfig.equals(this.mConfig)) {
            if (android.app.Flags.modesApi() && i != 1) {
                for (android.service.notification.ZenModeConfig.ZenRule zenRule : zenModeConfig.automaticRules.values()) {
                    android.service.notification.ZenModeConfig.ZenRule zenRule2 = (android.service.notification.ZenModeConfig.ZenRule) this.mConfig.automaticRules.get(zenRule.id);
                    if (zenRule2 != null) {
                        if (zenRule2.enabled != zenRule.enabled) {
                            scheduleEnabledBroadcast(zenRule.getPkg(), zenModeConfig.user, zenRule.id, zenRule.enabled);
                        }
                        if (zenRule2.isAutomaticActive() != zenRule.isAutomaticActive()) {
                            scheduleActivationBroadcast(zenRule.getPkg(), zenModeConfig.user, zenRule.id, zenRule.isAutomaticActive());
                        }
                    }
                }
            }
            this.mConfig = zenModeConfig;
            dispatchOnConfigChanged();
            updateAndApplyConsolidatedPolicyAndDeviceEffects(i, str);
        }
        android.provider.Settings.Global.putString(this.mContext.getContentResolver(), "zen_mode_config_etag", java.lang.Integer.toString(zenModeConfig.hashCode()));
        evaluateZenModeLocked(i, str, z);
        if (isEnabled) {
            this.mZenModeEventLogger.maybeLogZenChange(zenModeInfo, new com.android.server.notification.ZenModeEventLogger.ZenModeInfo(this.mZenMode, this.mConfig, this.mConsolidatedPolicy), i2, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getZenModeSetting() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "zen_mode", 0);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void setZenModeSetting(int i) {
        android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "zen_mode", i);
        com.android.server.notification.ZenLog.traceSetZenMode(android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "zen_mode", -1), "updated setting");
        showZenUpgradeNotification(i);
    }

    private int getPreviousRingerModeSetting() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "zen_mode_ringer_level", 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPreviousRingerModeSetting(java.lang.Integer num) {
        android.provider.Settings.Global.putString(this.mContext.getContentResolver(), "zen_mode_ringer_level", num == null ? null : java.lang.Integer.toString(num.intValue()));
    }

    @com.android.internal.annotations.GuardedBy({"mConfigLock"})
    @com.android.internal.annotations.VisibleForTesting
    protected void evaluateZenModeLocked(int i, java.lang.String str, boolean z) {
        if (DEBUG) {
            android.util.Log.d(TAG, "evaluateZenMode");
        }
        if (this.mConfig == null) {
            return;
        }
        boolean z2 = false;
        int hashCode = this.mConsolidatedPolicy == null ? 0 : this.mConsolidatedPolicy.hashCode();
        int i2 = this.mZenMode;
        int computeZenMode = computeZenMode();
        com.android.server.notification.ZenLog.traceSetZenMode(computeZenMode, str);
        this.mZenMode = computeZenMode;
        setZenModeSetting(this.mZenMode);
        updateAndApplyConsolidatedPolicyAndDeviceEffects(i, str);
        if (z && (computeZenMode != i2 || (computeZenMode == 1 && hashCode != this.mConsolidatedPolicy.hashCode()))) {
            z2 = true;
        }
        this.mHandler.postUpdateRingerAndAudio(z2);
        if (computeZenMode != i2) {
            this.mHandler.postDispatchOnZenModeChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRingerAndAudio(boolean z) {
        if (this.mAudioManager != null) {
            this.mAudioManager.updateRingerModeAffectedStreamsInternal();
        }
        if (z) {
            applyZenToRingerMode();
        }
        applyRestrictions();
    }

    private int computeZenMode() {
        synchronized (this.mConfigLock) {
            try {
                int i = 0;
                if (this.mConfig == null) {
                    return 0;
                }
                if (this.mConfig.manualRule != null) {
                    return this.mConfig.manualRule.zenMode;
                }
                for (android.service.notification.ZenModeConfig.ZenRule zenRule : this.mConfig.automaticRules.values()) {
                    if (zenRule.isAutomaticActive() && zenSeverity(zenRule.zenMode) > zenSeverity(i)) {
                        if (android.provider.Settings.Secure.getInt(this.mContext.getContentResolver(), "zen_settings_suggestion_viewed", 1) == 0) {
                            android.provider.Settings.Secure.putInt(this.mContext.getContentResolver(), "show_zen_settings_suggestion", 1);
                        }
                        i = zenRule.zenMode;
                    }
                }
                return i;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mConfigLock"})
    private void applyCustomPolicy(android.service.notification.ZenPolicy zenPolicy, android.service.notification.ZenModeConfig.ZenRule zenRule, boolean z) {
        if (zenRule.zenMode == 2) {
            zenPolicy.apply(new android.service.notification.ZenPolicy.Builder().disallowAllSounds().allowPriorityChannels(false).build());
            return;
        }
        if (zenRule.zenMode == 3) {
            zenPolicy.apply(new android.service.notification.ZenPolicy.Builder().disallowAllSounds().allowAlarms(true).allowMedia(true).allowPriorityChannels(false).build());
            return;
        }
        if (zenRule.zenPolicy != null) {
            zenPolicy.apply(zenRule.zenPolicy);
            return;
        }
        if (android.app.Flags.modesApi()) {
            if (z) {
                zenPolicy.apply(this.mConfig.toZenPolicy());
                return;
            }
            android.util.Log.wtf(TAG, "active automatic rule found with no specified policy: " + zenRule);
            zenPolicy.apply(this.mDefaultConfig.toZenPolicy());
            return;
        }
        zenPolicy.apply(this.mConfig.toZenPolicy());
    }

    @com.android.internal.annotations.GuardedBy({"mConfigLock"})
    private void updateAndApplyConsolidatedPolicyAndDeviceEffects(int i, java.lang.String str) {
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return;
                }
                android.service.notification.ZenPolicy zenPolicy = new android.service.notification.ZenPolicy();
                android.service.notification.ZenDeviceEffects.Builder builder = new android.service.notification.ZenDeviceEffects.Builder();
                if (this.mConfig.manualRule != null) {
                    applyCustomPolicy(zenPolicy, this.mConfig.manualRule, true);
                    if (android.app.Flags.modesApi()) {
                        builder.add(this.mConfig.manualRule.zenDeviceEffects);
                    }
                }
                for (android.service.notification.ZenModeConfig.ZenRule zenRule : this.mConfig.automaticRules.values()) {
                    if (zenRule.isAutomaticActive()) {
                        if (!android.app.Flags.modesApi() || zenRule.zenMode != 0) {
                            applyCustomPolicy(zenPolicy, zenRule, false);
                        }
                        if (android.app.Flags.modesApi()) {
                            builder.add(zenRule.zenDeviceEffects);
                        }
                    }
                }
                android.app.NotificationManager.Policy notificationPolicy = this.mConfig.toNotificationPolicy(zenPolicy);
                if (!java.util.Objects.equals(this.mConsolidatedPolicy, notificationPolicy)) {
                    this.mConsolidatedPolicy = notificationPolicy;
                    dispatchOnConsolidatedPolicyChanged(notificationPolicy);
                    com.android.server.notification.ZenLog.traceSetConsolidatedZenPolicy(this.mConsolidatedPolicy, str);
                }
                if (android.app.Flags.modesApi()) {
                    android.service.notification.ZenDeviceEffects build = builder.build();
                    if (!build.equals(this.mConsolidatedDeviceEffects)) {
                        this.mConsolidatedDeviceEffects = build;
                        this.mHandler.postApplyDeviceEffects(i);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyConsolidatedDeviceEffects(int i) {
        android.service.notification.DeviceEffectsApplier deviceEffectsApplier;
        android.service.notification.ZenDeviceEffects zenDeviceEffects;
        if (!android.app.Flags.modesApi()) {
            return;
        }
        synchronized (this.mConfigLock) {
            deviceEffectsApplier = this.mDeviceEffectsApplier;
            zenDeviceEffects = this.mConsolidatedDeviceEffects;
        }
        if (deviceEffectsApplier != null) {
            deviceEffectsApplier.apply(zenDeviceEffects, i);
        }
    }

    private void updateDefaultAutomaticRuleNames() {
        for (android.service.notification.ZenModeConfig.ZenRule zenRule : this.mDefaultConfig.automaticRules.values()) {
            if ("EVENTS_DEFAULT_RULE".equals(zenRule.id)) {
                zenRule.name = this.mContext.getResources().getString(android.R.string.whichOpenLinksWithApp);
            } else if ("EVERY_NIGHT_DEFAULT_RULE".equals(zenRule.id)) {
                zenRule.name = this.mContext.getResources().getString(android.R.string.whichSendApplication);
            }
        }
    }

    private void updateDefaultAutomaticRulePolicies() {
        if (!android.app.Flags.modesApi()) {
            return;
        }
        android.service.notification.ZenPolicy zenPolicy = this.mDefaultConfig.toZenPolicy();
        for (android.service.notification.ZenModeConfig.ZenRule zenRule : this.mDefaultConfig.automaticRules.values()) {
            if (android.service.notification.ZenModeConfig.DEFAULT_RULE_IDS.contains(zenRule.id) && zenRule.zenPolicy == null) {
                zenRule.zenPolicy = zenPolicy.copy();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void applyRestrictions() {
        boolean z = this.mZenMode != 0;
        boolean z2 = this.mZenMode == 1;
        boolean z3 = this.mZenMode == 2;
        boolean z4 = this.mZenMode == 3;
        boolean z5 = this.mConsolidatedPolicy.allowCalls() && this.mConsolidatedPolicy.allowCallsFrom() == 0;
        boolean allowRepeatCallers = this.mConsolidatedPolicy.allowRepeatCallers();
        boolean allowSystem = this.mConsolidatedPolicy.allowSystem();
        boolean allowMedia = this.mConsolidatedPolicy.allowMedia();
        boolean allowAlarms = this.mConsolidatedPolicy.allowAlarms();
        boolean z6 = z || (this.mSuppressedEffects & 1) != 0;
        boolean z7 = z4 || !((!z2 || z5 || allowRepeatCallers) && (this.mSuppressedEffects & 2) == 0);
        boolean z8 = z2 && !allowAlarms;
        boolean z9 = z2 && !allowMedia;
        boolean z10 = z4 || (z2 && !allowSystem);
        boolean z11 = z3 || (z2 && android.service.notification.ZenModeConfig.areAllZenBehaviorSoundsMuted(this.mConsolidatedPolicy));
        for (int i : android.media.AudioAttributes.SDK_USAGES.toArray()) {
            int i2 = android.media.AudioAttributes.SUPPRESSIBLE_USAGES.get(i);
            if (i2 == 3) {
                applyRestrictions(z2, false, i);
            } else if (i2 == 1) {
                applyRestrictions(z2, z6 || z11, i);
            } else if (i2 == 2) {
                applyRestrictions(z2, z7 || z11, i);
            } else if (i2 == 4) {
                applyRestrictions(z2, z8 || z11, i);
            } else if (i2 == 5) {
                applyRestrictions(z2, z9 || z11, i);
            } else if (i2 != 6) {
                applyRestrictions(z2, z11, i);
            } else if (i == 13) {
                applyRestrictions(z2, z10 || z11, i, 28);
                applyRestrictions(z2, false, i, 3);
            } else {
                applyRestrictions(z2, z10 || z11, i);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void applyRestrictions(boolean z, boolean z2, int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mAppOps.setRestriction(i2, i, z2 ? 1 : 0, z ? this.mPriorityOnlyDndExemptPackages : null);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void applyRestrictions(boolean z, boolean z2, int i) {
        applyRestrictions(z, z2, i, 3);
        applyRestrictions(z, z2, i, 28);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void applyZenToRingerMode() {
        if (this.mAudioManager == null) {
            return;
        }
        int ringerModeInternal = this.mAudioManager.getRingerModeInternal();
        switch (this.mZenMode) {
            case 0:
                if (ringerModeInternal == 0) {
                    ringerModeInternal = getPreviousRingerModeSetting();
                    setPreviousRingerModeSetting(null);
                    break;
                }
                break;
            case 2:
            case 3:
                if (ringerModeInternal != 0) {
                    setPreviousRingerModeSetting(java.lang.Integer.valueOf(ringerModeInternal));
                    ringerModeInternal = 0;
                    break;
                }
                break;
        }
        if (ringerModeInternal != -1) {
            this.mAudioManager.setRingerModeInternal(ringerModeInternal, TAG);
        }
    }

    private void dispatchOnConfigChanged() {
        java.util.Iterator<com.android.server.notification.ZenModeHelper.Callback> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onConfigChanged();
        }
    }

    private void dispatchOnPolicyChanged(android.app.NotificationManager.Policy policy) {
        java.util.Iterator<com.android.server.notification.ZenModeHelper.Callback> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onPolicyChanged(policy);
        }
    }

    private void dispatchOnConsolidatedPolicyChanged(android.app.NotificationManager.Policy policy) {
        java.util.Iterator<com.android.server.notification.ZenModeHelper.Callback> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onConsolidatedPolicyChanged(policy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnZenModeChanged() {
        java.util.Iterator<com.android.server.notification.ZenModeHelper.Callback> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onZenModeChanged();
        }
    }

    private void dispatchOnAutomaticRuleStatusChanged(int i, java.lang.String str, java.lang.String str2, int i2) {
        java.util.Iterator<com.android.server.notification.ZenModeHelper.Callback> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onAutomaticRuleStatusChanged(i, str, str2, i2);
        }
    }

    private android.service.notification.ZenModeConfig readDefaultConfig(android.content.res.Resources resources) {
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xmlResourceParser = resources.getXml(android.R.xml.default_zen_mode_config);
                while (xmlResourceParser.next() != 1) {
                    android.service.notification.ZenModeConfig readXml = android.service.notification.ZenModeConfig.readXml(com.android.internal.util.XmlUtils.makeTyped(xmlResourceParser));
                    if (readXml != null) {
                        return readXml;
                    }
                }
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "Error reading default zen mode config from resource", e);
            }
            return new android.service.notification.ZenModeConfig();
        } finally {
            libcore.io.IoUtils.closeQuietly(xmlResourceParser);
        }
    }

    private static int zenSeverity(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 2;
            default:
                return 0;
        }
    }

    public void pullRules(java.util.List<android.util.StatsEvent> list) {
        synchronized (this.mConfigsArrayLock) {
            try {
                int size = this.mConfigs.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = this.mConfigs.keyAt(i);
                    android.service.notification.ZenModeConfig valueAt = this.mConfigs.valueAt(i);
                    list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.DND_MODE_RULE, keyAt, valueAt.manualRule != null, valueAt.areChannelsBypassingDnd, -1, "", 1000, valueAt.toZenPolicy().toProto(), 0, 0, 0, -1));
                    if (valueAt.manualRule != null) {
                        ruleToProtoLocked(keyAt, valueAt.manualRule, true, list);
                    }
                    java.util.Iterator it = valueAt.automaticRules.values().iterator();
                    while (it.hasNext()) {
                        ruleToProtoLocked(keyAt, (android.service.notification.ZenModeConfig.ZenRule) it.next(), false, list);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mConfigsArrayLock"})
    private void ruleToProtoLocked(int i, android.service.notification.ZenModeConfig.ZenRule zenRule, boolean z, java.util.List<android.util.StatsEvent> list) {
        java.lang.String str;
        int i2;
        byte[] bArr;
        java.lang.String str2 = zenRule.id == null ? "" : zenRule.id;
        if (!android.service.notification.ZenModeConfig.DEFAULT_RULE_IDS.contains(str2)) {
            str2 = "";
        }
        java.lang.String pkg = zenRule.getPkg() != null ? zenRule.getPkg() : "";
        if (zenRule.enabler != null) {
            pkg = zenRule.enabler;
        }
        int i3 = zenRule.type;
        if (!z) {
            str = str2;
            i2 = i3;
        } else {
            str = "MANUAL_RULE";
            i2 = 999;
        }
        byte[] bArr2 = new byte[0];
        if (zenRule.zenPolicy == null) {
            bArr = bArr2;
        } else {
            bArr = zenRule.zenPolicy.toProto();
        }
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.DND_MODE_RULE, i, zenRule.enabled, false, zenRule.zenMode, str, getPackageUid(pkg, i), bArr, zenRule.userModifiedFields, zenRule.zenPolicyUserModifiedFields, zenRule.zenDeviceEffectsUserModifiedFields, i2));
    }

    private int getPackageUid(java.lang.String str, int i) {
        if ("android".equals(str)) {
            return 1000;
        }
        java.lang.String packageUserKey = getPackageUserKey(str, i);
        if (this.mRulesUidCache.get(packageUserKey) == null) {
            try {
                this.mRulesUidCache.put(packageUserKey, java.lang.Integer.valueOf(this.mPm.getPackageUidAsUser(str, i)));
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }
        return this.mRulesUidCache.getOrDefault(packageUserKey, -1).intValue();
    }

    private static java.lang.String getPackageUserKey(java.lang.String str, int i) {
        return str + "|" + i;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected final class RingerModeDelegate implements android.media.AudioManagerInternal.RingerModeDelegate {
        protected RingerModeDelegate() {
        }

        public java.lang.String toString() {
            return com.android.server.notification.ZenModeHelper.TAG;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public int onSetRingerModeInternal(int i, int i2, @android.annotation.Nullable java.lang.String str, int i3, android.media.VolumePolicy volumePolicy) {
            int i4;
            int i5 = 0;
            boolean z = i != i2;
            if (com.android.server.notification.ZenModeHelper.this.mZenMode == 0 || (com.android.server.notification.ZenModeHelper.this.mZenMode == 1 && !areAllPriorityOnlyRingerSoundsMuted())) {
                com.android.server.notification.ZenModeHelper.this.setPreviousRingerModeSetting(java.lang.Integer.valueOf(i2));
            }
            switch (i2) {
                case 0:
                    if (z && volumePolicy.doNotDisturbWhenSilent) {
                        if (com.android.server.notification.ZenModeHelper.this.mZenMode != 0) {
                            i5 = -1;
                        } else {
                            i5 = 1;
                        }
                        com.android.server.notification.ZenModeHelper.this.setPreviousRingerModeSetting(java.lang.Integer.valueOf(i));
                        i4 = i2;
                        break;
                    }
                    i4 = i2;
                    i5 = -1;
                    break;
                case 1:
                case 2:
                    if (z && i == 0 && (com.android.server.notification.ZenModeHelper.this.mZenMode == 2 || com.android.server.notification.ZenModeHelper.this.mZenMode == 3 || (com.android.server.notification.ZenModeHelper.this.mZenMode == 1 && areAllPriorityOnlyRingerSoundsMuted()))) {
                        i4 = i2;
                        break;
                    } else {
                        if (com.android.server.notification.ZenModeHelper.this.mZenMode != 0) {
                            i4 = 0;
                            i5 = -1;
                            break;
                        }
                        i4 = i2;
                        i5 = -1;
                        break;
                    }
                default:
                    i4 = i2;
                    i5 = -1;
                    break;
            }
            if (i5 != -1) {
                com.android.server.notification.ZenModeHelper.this.setManualZenMode(i5, null, 5, "ringerModeInternal", null, false, 1000);
            }
            if (z || i5 != -1 || i3 != i4) {
                com.android.server.notification.ZenLog.traceSetRingerModeInternal(i, i2, str, i3, i4);
            }
            return i4;
        }

        private boolean areAllPriorityOnlyRingerSoundsMuted() {
            boolean areAllPriorityOnlyRingerSoundsMuted;
            synchronized (com.android.server.notification.ZenModeHelper.this.mConfigLock) {
                areAllPriorityOnlyRingerSoundsMuted = android.service.notification.ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(com.android.server.notification.ZenModeHelper.this.mConfig);
            }
            return areAllPriorityOnlyRingerSoundsMuted;
        }

        public int onSetRingerModeExternal(int i, int i2, @android.annotation.Nullable java.lang.String str, int i3, android.media.VolumePolicy volumePolicy) {
            int i4;
            int i5;
            int i6;
            boolean z = i != i2;
            boolean z2 = i3 == 1;
            switch (i2) {
                case 0:
                    if (z) {
                        if (com.android.server.notification.ZenModeHelper.this.mZenMode != 0) {
                            i6 = -1;
                        } else {
                            i6 = 1;
                        }
                        i4 = z2 ? 1 : 0;
                        i5 = i6;
                        break;
                    } else {
                        i4 = i3;
                        i5 = -1;
                        break;
                    }
                case 1:
                case 2:
                    if (com.android.server.notification.ZenModeHelper.this.mZenMode != 0) {
                        i5 = 0;
                        i4 = i2;
                        break;
                    }
                default:
                    i4 = i2;
                    i5 = -1;
                    break;
            }
            if (i5 != -1) {
                com.android.server.notification.ZenModeHelper.this.setManualZenMode(i5, null, 5, "ringerModeExternal", str, false, 1000);
            }
            com.android.server.notification.ZenLog.traceSetRingerModeExternal(i, i2, str, i3, i4);
            return i4;
        }

        public boolean canVolumeDownEnterSilent() {
            return com.android.server.notification.ZenModeHelper.this.mZenMode == 0;
        }

        public int getRingerModeAffectedStreams(int i) {
            int i2 = i | 38;
            if (com.android.server.notification.ZenModeHelper.this.mZenMode == 2) {
                return i2 | 2072;
            }
            return i2 & (-2073);
        }
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        private final android.net.Uri ZEN_MODE;

        public SettingsObserver(android.os.Handler handler) {
            super(handler);
            this.ZEN_MODE = android.provider.Settings.Global.getUriFor("zen_mode");
        }

        public void observe() {
            com.android.server.notification.ZenModeHelper.this.mContext.getContentResolver().registerContentObserver(this.ZEN_MODE, false, this);
            update(null);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            update(uri);
        }

        public void update(android.net.Uri uri) {
            if (this.ZEN_MODE.equals(uri) && com.android.server.notification.ZenModeHelper.this.mZenMode != com.android.server.notification.ZenModeHelper.this.getZenModeSetting()) {
                if (com.android.server.notification.ZenModeHelper.DEBUG) {
                    android.util.Log.d(com.android.server.notification.ZenModeHelper.TAG, "Fixing zen mode setting");
                }
                com.android.server.notification.ZenModeHelper.this.setZenModeSetting(com.android.server.notification.ZenModeHelper.this.mZenMode);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0033, code lost:
    
        if (android.provider.Settings.Secure.getInt(r5.mContext.getContentResolver(), "zen_settings_updated", 0) != 1) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void showZenUpgradeNotification(int i) {
        boolean z;
        boolean hasSystemFeature = this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch");
        if (this.mIsSystemServicesReady && i != 0 && !hasSystemFeature && android.provider.Settings.Secure.getInt(this.mContext.getContentResolver(), "show_zen_upgrade_notification", 0) != 0) {
            z = true;
        }
        z = false;
        if (hasSystemFeature) {
            android.provider.Settings.Secure.putInt(this.mContext.getContentResolver(), "show_zen_upgrade_notification", 0);
        }
        if (z) {
            this.mNotificationManager.notify(TAG, 48, createZenUpgradeNotification());
            android.provider.Settings.Secure.putInt(this.mContext.getContentResolver(), "show_zen_upgrade_notification", 0);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.app.Notification createZenUpgradeNotification() {
        int i;
        int i2;
        int i3;
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("android.substName", this.mContext.getResources().getString(android.R.string.foreground_service_app_in_background));
        if (!android.app.NotificationManager.Policy.areAllVisualEffectsSuppressed(getConsolidatedNotificationPolicy().suppressedVisualEffects)) {
            i = android.R.string.work_profile_deleted_description_dpm_wipe;
            i2 = android.R.string.work_profile_deleted;
            i3 = android.R.drawable.ic_wifi_signal_2;
        } else {
            i = android.R.string.work_profile_deleted_reason_maximum_password_failure;
            i2 = android.R.string.work_profile_deleted_details;
            i3 = android.R.drawable.ic_dialog_time;
        }
        android.content.Intent intent = new android.content.Intent("android.settings.ZEN_MODE_ONBOARDING");
        intent.addFlags(268468224);
        return new android.app.Notification.Builder(this.mContext, com.android.internal.notification.SystemNotificationChannels.DO_NOT_DISTURB).setAutoCancel(true).setSmallIcon(android.R.drawable.ic_search_api_material).setLargeIcon(android.graphics.drawable.Icon.createWithResource(this.mContext, i3)).setContentTitle(this.mContext.getResources().getString(i)).setContentText(this.mContext.getResources().getString(i2)).setContentIntent(android.app.PendingIntent.getActivity(this.mContext, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD)).setAutoCancel(true).setLocalOnly(true).addExtras(bundle).setStyle(new android.app.Notification.BigTextStyle()).build();
    }

    private int drawableResNameToResId(java.lang.String str, java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str2)) {
            return 0;
        }
        try {
            return this.mPm.getResourcesForApplication(str).getIdentifier(str2, null, null);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.w(TAG, "cannot load rule icon for pkg", e);
            return 0;
        }
    }

    private java.lang.String drawableResIdToResName(java.lang.String str, int i) {
        if (i == 0) {
            return null;
        }
        java.util.Objects.requireNonNull(str);
        try {
            return this.mPm.getResourcesForApplication(str).getResourceName(i);
        } catch (android.content.pm.PackageManager.NameNotFoundException | android.content.res.Resources.NotFoundException e) {
            android.util.Slog.e(TAG, "Resource name for ID=" + i + " not found in package " + str + ". Resource IDs may change when the application is upgraded, and the system may not be able to find the correct resource.");
            return null;
        }
    }

    private static void requirePublicOrigin(java.lang.String str, int i) {
        if (!android.app.Flags.modesApi()) {
            return;
        }
        com.android.internal.util.Preconditions.checkArgument(i == 4 || i == 5 || i == 3, "Expected one of UPDATE_ORIGIN_APP, UPDATE_ORIGIN_SYSTEM_OR_SYSTEMUI, or UPDATE_ORIGIN_USER for %s, but received '%s'.", new java.lang.Object[]{str, java.lang.Integer.valueOf(i)});
    }

    private final class Metrics extends com.android.server.notification.ZenModeHelper.Callback {
        private static final java.lang.String COUNTER_MODE_PREFIX = "dnd_mode_";
        private static final java.lang.String COUNTER_RULE = "dnd_rule_count";
        private static final java.lang.String COUNTER_TYPE_PREFIX = "dnd_type_";
        private static final int DND_OFF = 0;
        private static final int DND_ON_AUTOMATIC = 2;
        private static final int DND_ON_MANUAL = 1;
        private static final long MINIMUM_LOG_PERIOD_MS = 60000;
        private long mModeLogTimeMs;
        private int mNumZenRules;
        private int mPreviousZenMode;
        private int mPreviousZenType;
        private long mRuleCountLogTime;
        private long mTypeLogTimeMs;

        private Metrics() {
            this.mPreviousZenMode = -1;
            this.mModeLogTimeMs = 0L;
            this.mNumZenRules = -1;
            this.mRuleCountLogTime = 0L;
            this.mPreviousZenType = -1;
            this.mTypeLogTimeMs = 0L;
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        void onZenModeChanged() {
            emit();
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        void onConfigChanged() {
            emit();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void emit() {
            com.android.server.notification.ZenModeHelper.this.mHandler.postMetricsTimer();
            emitZenMode();
            emitRules();
            emitDndType();
        }

        private void emitZenMode() {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            long j = elapsedRealtime - this.mModeLogTimeMs;
            if (this.mPreviousZenMode != com.android.server.notification.ZenModeHelper.this.mZenMode || j > 60000) {
                if (this.mPreviousZenMode != -1) {
                    com.android.internal.logging.MetricsLogger.count(com.android.server.notification.ZenModeHelper.this.mContext, COUNTER_MODE_PREFIX + this.mPreviousZenMode, (int) j);
                }
                this.mPreviousZenMode = com.android.server.notification.ZenModeHelper.this.mZenMode;
                this.mModeLogTimeMs = elapsedRealtime;
            }
        }

        private void emitRules() {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - this.mRuleCountLogTime;
            synchronized (com.android.server.notification.ZenModeHelper.this.mConfigLock) {
                try {
                    int size = com.android.server.notification.ZenModeHelper.this.mConfig.automaticRules.size();
                    if (this.mNumZenRules != size || elapsedRealtime > 60000) {
                        if (this.mNumZenRules != -1) {
                            com.android.internal.logging.MetricsLogger.count(com.android.server.notification.ZenModeHelper.this.mContext, COUNTER_RULE, size - this.mNumZenRules);
                        }
                        this.mNumZenRules = size;
                        this.mRuleCountLogTime = elapsedRealtime;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void emitDndType() {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            long j = elapsedRealtime - this.mTypeLogTimeMs;
            synchronized (com.android.server.notification.ZenModeHelper.this.mConfigLock) {
                try {
                    int i = 0;
                    if (com.android.server.notification.ZenModeHelper.this.mZenMode != 0) {
                        i = com.android.server.notification.ZenModeHelper.this.mConfig.manualRule != null ? 1 : 2;
                    }
                    if (i != this.mPreviousZenType || j > 60000) {
                        if (this.mPreviousZenType != -1) {
                            com.android.internal.logging.MetricsLogger.count(com.android.server.notification.ZenModeHelper.this.mContext, COUNTER_TYPE_PREFIX + this.mPreviousZenType, (int) j);
                        }
                        this.mTypeLogTimeMs = elapsedRealtime;
                        this.mPreviousZenType = i;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class H extends android.os.Handler {
        private static final long METRICS_PERIOD_MS = 21600000;
        private static final int MSG_APPLY_EFFECTS = 6;
        private static final int MSG_DISPATCH = 1;
        private static final int MSG_METRICS = 2;
        private static final int MSG_RINGER_AUDIO = 5;

        private H(android.os.Looper looper) {
            super(looper);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void postDispatchOnZenModeChanged() {
            removeMessages(1);
            sendEmptyMessage(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void postMetricsTimer() {
            removeMessages(2);
            sendEmptyMessageDelayed(2, METRICS_PERIOD_MS);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void postUpdateRingerAndAudio(boolean z) {
            removeMessages(5);
            sendMessage(obtainMessage(5, java.lang.Boolean.valueOf(z)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void postApplyDeviceEffects(int i) {
            removeMessages(6);
            sendMessage(obtainMessage(6, i, 0));
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.notification.ZenModeHelper.this.dispatchOnZenModeChanged();
                    break;
                case 2:
                    com.android.server.notification.ZenModeHelper.this.mMetrics.emit();
                    break;
                case 5:
                    com.android.server.notification.ZenModeHelper.this.updateRingerAndAudio(((java.lang.Boolean) message.obj).booleanValue());
                    break;
                case 6:
                    com.android.server.notification.ZenModeHelper.this.applyConsolidatedDeviceEffects(message.arg1);
                    break;
            }
        }
    }

    public static class Callback {
        void onConfigChanged() {
        }

        void onZenModeChanged() {
        }

        void onPolicyChanged(android.app.NotificationManager.Policy policy) {
        }

        void onConsolidatedPolicyChanged(android.app.NotificationManager.Policy policy) {
        }

        void onAutomaticRuleStatusChanged(int i, java.lang.String str, java.lang.String str2, int i2) {
        }
    }
}
