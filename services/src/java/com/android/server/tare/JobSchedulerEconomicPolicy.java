package com.android.server.tare;

/* loaded from: classes2.dex */
public class JobSchedulerEconomicPolicy extends com.android.server.tare.EconomicPolicy {
    public static final int ACTION_JOB_DEFAULT_RUNNING = 1610612741;
    public static final int ACTION_JOB_DEFAULT_START = 1610612740;
    public static final int ACTION_JOB_HIGH_RUNNING = 1610612739;
    public static final int ACTION_JOB_HIGH_START = 1610612738;
    public static final int ACTION_JOB_LOW_RUNNING = 1610612743;
    public static final int ACTION_JOB_LOW_START = 1610612742;
    public static final int ACTION_JOB_MAX_RUNNING = 1610612737;
    public static final int ACTION_JOB_MAX_START = 1610612736;
    public static final int ACTION_JOB_MIN_RUNNING = 1610612745;
    public static final int ACTION_JOB_MIN_START = 1610612744;
    public static final int ACTION_JOB_TIMEOUT = 1610612746;
    public static final int REWARD_APP_INSTALL = -1610612736;
    private final android.util.SparseArray<com.android.server.tare.EconomicPolicy.Action> mActions;
    private long mInitialSatiatedConsumptionLimit;
    private final com.android.server.tare.EconomicPolicy.Injector mInjector;
    private long mMaxSatiatedBalance;
    private long mMaxSatiatedConsumptionLimit;
    private long mMinSatiatedBalanceExempted;
    private long mMinSatiatedBalanceHeadlessSystemApp;
    private long mMinSatiatedBalanceIncrementalAppUpdater;
    private long mMinSatiatedBalanceOther;
    private long mMinSatiatedConsumptionLimit;
    private final android.util.SparseArray<com.android.server.tare.EconomicPolicy.Reward> mRewards;
    private static final java.lang.String TAG = "TARE- " + com.android.server.tare.JobSchedulerEconomicPolicy.class.getSimpleName();
    private static final int[] COST_MODIFIERS = {0, 1, 2, 3};

    JobSchedulerEconomicPolicy(com.android.server.tare.InternalResourceService internalResourceService, com.android.server.tare.EconomicPolicy.Injector injector) {
        super(internalResourceService);
        this.mActions = new android.util.SparseArray<>();
        this.mRewards = new android.util.SparseArray<>();
        this.mInjector = injector;
        loadConstants("", null);
    }

    @Override // com.android.server.tare.EconomicPolicy
    void setup(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
        super.setup(properties);
        loadConstants(this.mInjector.getSettingsGlobalString(this.mIrs.getContext().getContentResolver(), "tare_job_scheduler_constants"), properties);
    }

    @Override // com.android.server.tare.EconomicPolicy
    long getMinSatiatedBalance(int i, @android.annotation.NonNull java.lang.String str) {
        long j;
        if (this.mIrs.isPackageRestricted(i, str)) {
            return 0L;
        }
        if (this.mIrs.isPackageExempted(i, str)) {
            j = this.mMinSatiatedBalanceExempted;
        } else if (this.mIrs.isHeadlessSystemApp(i, str)) {
            j = this.mMinSatiatedBalanceHeadlessSystemApp;
        } else {
            j = this.mMinSatiatedBalanceOther;
        }
        return java.lang.Math.min(j + (this.mIrs.getAppUpdateResponsibilityCount(i, str) * this.mMinSatiatedBalanceIncrementalAppUpdater), this.mMaxSatiatedBalance);
    }

    @Override // com.android.server.tare.EconomicPolicy
    long getMaxSatiatedBalance(int i, @android.annotation.NonNull java.lang.String str) {
        if (this.mIrs.isPackageRestricted(i, str)) {
            return 0L;
        }
        com.android.server.tare.InstalledPackageInfo installedPackageInfo = this.mIrs.getInstalledPackageInfo(i, str);
        if (installedPackageInfo == null) {
            android.util.Slog.wtfStack(TAG, "Tried to get max balance of invalid app: " + com.android.server.tare.TareUtils.appToString(i, str));
        } else if (installedPackageInfo.isSystemInstaller) {
            if (this.mIrs.getRealtimeSinceFirstSetupMs() < com.android.server.usage.UnixCalendar.WEEK_IN_MILLIS) {
                return this.mMaxSatiatedConsumptionLimit;
            }
        }
        return this.mMaxSatiatedBalance;
    }

    @Override // com.android.server.tare.EconomicPolicy
    long getInitialSatiatedConsumptionLimit() {
        return this.mInitialSatiatedConsumptionLimit;
    }

    @Override // com.android.server.tare.EconomicPolicy
    long getMinSatiatedConsumptionLimit() {
        return this.mMinSatiatedConsumptionLimit;
    }

    @Override // com.android.server.tare.EconomicPolicy
    long getMaxSatiatedConsumptionLimit() {
        return this.mMaxSatiatedConsumptionLimit;
    }

    @Override // com.android.server.tare.EconomicPolicy
    @android.annotation.NonNull
    int[] getCostModifiers() {
        return COST_MODIFIERS;
    }

    @Override // com.android.server.tare.EconomicPolicy
    @android.annotation.Nullable
    com.android.server.tare.EconomicPolicy.Action getAction(int i) {
        return this.mActions.get(i);
    }

    @Override // com.android.server.tare.EconomicPolicy
    @android.annotation.Nullable
    com.android.server.tare.EconomicPolicy.Reward getReward(int i) {
        return this.mRewards.get(i);
    }

    private void loadConstants(java.lang.String str, @android.annotation.Nullable android.provider.DeviceConfig.Properties properties) {
        this.mActions.clear();
        this.mRewards.clear();
        try {
            this.mUserSettingDeviceConfigMediator.setSettingsString(str);
            this.mUserSettingDeviceConfigMediator.setDeviceConfigProperties(properties);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Global setting key incorrect: ", e);
        }
        this.mMinSatiatedBalanceOther = getConstantAsCake("js_min_satiated_balance_other_app", android.app.tare.EconomyManager.DEFAULT_JS_MIN_SATIATED_BALANCE_OTHER_APP_CAKES);
        this.mMinSatiatedBalanceHeadlessSystemApp = getConstantAsCake("js_min_satiated_balance_headless_system_app", android.app.tare.EconomyManager.DEFAULT_JS_MIN_SATIATED_BALANCE_HEADLESS_SYSTEM_APP_CAKES, this.mMinSatiatedBalanceOther);
        this.mMinSatiatedBalanceExempted = getConstantAsCake("js_min_satiated_balance_exempted", android.app.tare.EconomyManager.DEFAULT_JS_MIN_SATIATED_BALANCE_EXEMPTED_CAKES, this.mMinSatiatedBalanceHeadlessSystemApp);
        this.mMinSatiatedBalanceIncrementalAppUpdater = getConstantAsCake("js_min_satiated_balance_increment_updater", android.app.tare.EconomyManager.DEFAULT_JS_MIN_SATIATED_BALANCE_INCREMENT_APP_UPDATER_CAKES);
        this.mMaxSatiatedBalance = getConstantAsCake("js_max_satiated_balance", android.app.tare.EconomyManager.DEFAULT_JS_MAX_SATIATED_BALANCE_CAKES, java.lang.Math.max(android.app.tare.EconomyManager.arcToCake(1), this.mMinSatiatedBalanceExempted));
        this.mMinSatiatedConsumptionLimit = getConstantAsCake("js_minimum_consumption_limit", android.app.tare.EconomyManager.DEFAULT_JS_MIN_CONSUMPTION_LIMIT_CAKES, android.app.tare.EconomyManager.arcToCake(1));
        this.mInitialSatiatedConsumptionLimit = getConstantAsCake("js_initial_consumption_limit", android.app.tare.EconomyManager.DEFAULT_JS_INITIAL_CONSUMPTION_LIMIT_CAKES, this.mMinSatiatedConsumptionLimit);
        this.mMaxSatiatedConsumptionLimit = getConstantAsCake("js_maximum_consumption_limit", android.app.tare.EconomyManager.DEFAULT_JS_MAX_CONSUMPTION_LIMIT_CAKES, this.mInitialSatiatedConsumptionLimit);
        this.mActions.put(ACTION_JOB_MAX_START, new com.android.server.tare.EconomicPolicy.Action(ACTION_JOB_MAX_START, getConstantAsCake("js_action_job_max_start_ctp", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_MAX_START_CTP_CAKES), getConstantAsCake("js_action_job_max_start_base_price", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_MAX_START_BASE_PRICE_CAKES)));
        this.mActions.put(ACTION_JOB_MAX_RUNNING, new com.android.server.tare.EconomicPolicy.Action(ACTION_JOB_MAX_RUNNING, getConstantAsCake("js_action_job_max_running_ctp", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_MAX_RUNNING_CTP_CAKES), getConstantAsCake("js_action_job_max_running_base_price", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_MAX_RUNNING_BASE_PRICE_CAKES)));
        this.mActions.put(ACTION_JOB_HIGH_START, new com.android.server.tare.EconomicPolicy.Action(ACTION_JOB_HIGH_START, getConstantAsCake("js_action_job_high_start_ctp", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_HIGH_START_CTP_CAKES), getConstantAsCake("js_action_job_high_start_base_price", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_HIGH_START_BASE_PRICE_CAKES)));
        this.mActions.put(ACTION_JOB_HIGH_RUNNING, new com.android.server.tare.EconomicPolicy.Action(ACTION_JOB_HIGH_RUNNING, getConstantAsCake("js_action_job_high_running_ctp", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_HIGH_RUNNING_CTP_CAKES), getConstantAsCake("js_action_job_high_running_base_price", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_HIGH_RUNNING_BASE_PRICE_CAKES)));
        this.mActions.put(ACTION_JOB_DEFAULT_START, new com.android.server.tare.EconomicPolicy.Action(ACTION_JOB_DEFAULT_START, getConstantAsCake("js_action_job_default_start_ctp", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_DEFAULT_START_CTP_CAKES), getConstantAsCake("js_action_job_default_start_base_price", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_DEFAULT_START_BASE_PRICE_CAKES)));
        this.mActions.put(ACTION_JOB_DEFAULT_RUNNING, new com.android.server.tare.EconomicPolicy.Action(ACTION_JOB_DEFAULT_RUNNING, getConstantAsCake("js_action_job_default_running_ctp", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_DEFAULT_RUNNING_CTP_CAKES), getConstantAsCake("js_action_job_default_running_base_price", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_DEFAULT_RUNNING_BASE_PRICE_CAKES)));
        this.mActions.put(ACTION_JOB_LOW_START, new com.android.server.tare.EconomicPolicy.Action(ACTION_JOB_LOW_START, getConstantAsCake("js_action_job_low_start_ctp", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_LOW_START_CTP_CAKES), getConstantAsCake("js_action_job_low_start_base_price", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_LOW_START_BASE_PRICE_CAKES)));
        this.mActions.put(ACTION_JOB_LOW_RUNNING, new com.android.server.tare.EconomicPolicy.Action(ACTION_JOB_LOW_RUNNING, getConstantAsCake("js_action_job_low_running_ctp", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_LOW_RUNNING_CTP_CAKES), getConstantAsCake("js_action_job_low_running_base_price", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_LOW_RUNNING_BASE_PRICE_CAKES)));
        this.mActions.put(ACTION_JOB_MIN_START, new com.android.server.tare.EconomicPolicy.Action(ACTION_JOB_MIN_START, getConstantAsCake("js_action_job_min_start_ctp", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_MIN_START_CTP_CAKES), getConstantAsCake("js_action_job_min_start_base_price", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_MIN_START_BASE_PRICE_CAKES)));
        this.mActions.put(ACTION_JOB_MIN_RUNNING, new com.android.server.tare.EconomicPolicy.Action(ACTION_JOB_MIN_RUNNING, getConstantAsCake("js_action_job_min_running_ctp", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_MIN_RUNNING_CTP_CAKES), getConstantAsCake("js_action_job_min_running_base_price", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_MIN_RUNNING_BASE_PRICE_CAKES)));
        this.mActions.put(ACTION_JOB_TIMEOUT, new com.android.server.tare.EconomicPolicy.Action(ACTION_JOB_TIMEOUT, getConstantAsCake("js_action_job_timeout_penalty_ctp", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_TIMEOUT_PENALTY_CTP_CAKES), getConstantAsCake("js_action_job_timeout_penalty_base_price", android.app.tare.EconomyManager.DEFAULT_JS_ACTION_JOB_TIMEOUT_PENALTY_BASE_PRICE_CAKES)));
        this.mRewards.put(android.hardware.audio.common.V2_0.AudioDevice.IN_AMBIENT, new com.android.server.tare.EconomicPolicy.Reward(android.hardware.audio.common.V2_0.AudioDevice.IN_AMBIENT, getConstantAsCake("js_reward_top_activity_instant", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_TOP_ACTIVITY_INSTANT_CAKES), getConstantAsCake("js_reward_top_activity_ongoing", 500000000L), getConstantAsCake("js_reward_top_activity_max", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_TOP_ACTIVITY_MAX_CAKES)));
        this.mRewards.put(Integer.MIN_VALUE, new com.android.server.tare.EconomicPolicy.Reward(Integer.MIN_VALUE, getConstantAsCake("js_reward_notification_seen_instant", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_NOTIFICATION_SEEN_INSTANT_CAKES), getConstantAsCake("js_reward_notification_seen_ongoing", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_NOTIFICATION_SEEN_ONGOING_CAKES), getConstantAsCake("js_reward_notification_seen_max", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_NOTIFICATION_SEEN_MAX_CAKES)));
        this.mRewards.put(-2147483647, new com.android.server.tare.EconomicPolicy.Reward(-2147483647, getConstantAsCake("js_reward_notification_interaction_instant", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_NOTIFICATION_INTERACTION_INSTANT_CAKES), getConstantAsCake("js_reward_notification_interaction_ongoing", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_NOTIFICATION_INTERACTION_ONGOING_CAKES), getConstantAsCake("js_reward_notification_interaction_max", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_NOTIFICATION_INTERACTION_MAX_CAKES)));
        this.mRewards.put(android.hardware.audio.common.V2_0.AudioChannelMask.INDEX_MASK_2, new com.android.server.tare.EconomicPolicy.Reward(android.hardware.audio.common.V2_0.AudioChannelMask.INDEX_MASK_2, getConstantAsCake("js_reward_widget_interaction_instant", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_WIDGET_INTERACTION_INSTANT_CAKES), getConstantAsCake("js_reward_widget_interaction_ongoing", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_WIDGET_INTERACTION_ONGOING_CAKES), getConstantAsCake("js_reward_widget_interaction_max", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_WIDGET_INTERACTION_MAX_CAKES)));
        this.mRewards.put(android.hardware.audio.common.V2_0.AudioDevice.IN_BUILTIN_MIC, new com.android.server.tare.EconomicPolicy.Reward(android.hardware.audio.common.V2_0.AudioDevice.IN_BUILTIN_MIC, getConstantAsCake("js_reward_other_user_interaction_instant", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_OTHER_USER_INTERACTION_INSTANT_CAKES), getConstantAsCake("js_reward_other_user_interaction_ongoing", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_OTHER_USER_INTERACTION_ONGOING_CAKES), getConstantAsCake("js_reward_other_user_interaction_max", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_OTHER_USER_INTERACTION_MAX_CAKES)));
        this.mRewards.put(REWARD_APP_INSTALL, new com.android.server.tare.EconomicPolicy.Reward(REWARD_APP_INSTALL, getConstantAsCake("js_reward_app_install_instant", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_APP_INSTALL_INSTANT_CAKES), getConstantAsCake("js_reward_app_install_ongoing", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_APP_INSTALL_ONGOING_CAKES), getConstantAsCake("js_reward_app_install_max", android.app.tare.EconomyManager.DEFAULT_JS_REWARD_APP_INSTALL_MAX_CAKES)));
    }

    @Override // com.android.server.tare.EconomicPolicy
    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Min satiated balance:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("Exempted", com.android.server.tare.TareUtils.cakeToString(this.mMinSatiatedBalanceExempted)).println();
        indentingPrintWriter.print("Other", com.android.server.tare.TareUtils.cakeToString(this.mMinSatiatedBalanceOther)).println();
        indentingPrintWriter.print("+App Updater", com.android.server.tare.TareUtils.cakeToString(this.mMinSatiatedBalanceIncrementalAppUpdater)).println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.print("Max satiated balance", com.android.server.tare.TareUtils.cakeToString(this.mMaxSatiatedBalance)).println();
        indentingPrintWriter.print("Consumption limits: [");
        indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(this.mMinSatiatedConsumptionLimit));
        indentingPrintWriter.print(", ");
        indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(this.mInitialSatiatedConsumptionLimit));
        indentingPrintWriter.print(", ");
        indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(this.mMaxSatiatedConsumptionLimit));
        indentingPrintWriter.println("]");
        indentingPrintWriter.println();
        indentingPrintWriter.println("Actions:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mActions.size(); i++) {
            com.android.server.tare.EconomicPolicy.dumpAction(indentingPrintWriter, this.mActions.valueAt(i));
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("Rewards:");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < this.mRewards.size(); i2++) {
            com.android.server.tare.EconomicPolicy.dumpReward(indentingPrintWriter, this.mRewards.valueAt(i2));
        }
        indentingPrintWriter.decreaseIndent();
    }
}
