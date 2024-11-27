package com.android.server.tare;

/* loaded from: classes2.dex */
public class AlarmManagerEconomicPolicy extends com.android.server.tare.EconomicPolicy {
    public static final int ACTION_ALARM_CLOCK = 1342177288;
    public static final int ACTION_ALARM_NONWAKEUP_EXACT = 1342177285;
    public static final int ACTION_ALARM_NONWAKEUP_EXACT_ALLOW_WHILE_IDLE = 1342177284;
    public static final int ACTION_ALARM_NONWAKEUP_INEXACT = 1342177287;
    public static final int ACTION_ALARM_NONWAKEUP_INEXACT_ALLOW_WHILE_IDLE = 1342177286;
    public static final int ACTION_ALARM_WAKEUP_EXACT = 1342177281;
    public static final int ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE = 1342177280;
    public static final int ACTION_ALARM_WAKEUP_INEXACT = 1342177283;
    public static final int ACTION_ALARM_WAKEUP_INEXACT_ALLOW_WHILE_IDLE = 1342177282;
    private final android.util.SparseArray<com.android.server.tare.EconomicPolicy.Action> mActions;
    private long mInitialSatiatedConsumptionLimit;
    private final com.android.server.tare.EconomicPolicy.Injector mInjector;
    private long mMaxSatiatedBalance;
    private long mMaxSatiatedConsumptionLimit;
    private long mMinSatiatedBalanceExempted;
    private long mMinSatiatedBalanceHeadlessSystemApp;
    private long mMinSatiatedBalanceOther;
    private long mMinSatiatedConsumptionLimit;
    private final android.util.SparseArray<com.android.server.tare.EconomicPolicy.Reward> mRewards;
    private static final java.lang.String TAG = "TARE- " + com.android.server.tare.AlarmManagerEconomicPolicy.class.getSimpleName();
    private static final int[] COST_MODIFIERS = {0, 1, 2, 3};

    AlarmManagerEconomicPolicy(com.android.server.tare.InternalResourceService internalResourceService, com.android.server.tare.EconomicPolicy.Injector injector) {
        super(internalResourceService);
        this.mActions = new android.util.SparseArray<>();
        this.mRewards = new android.util.SparseArray<>();
        this.mInjector = injector;
        loadConstants("", null);
    }

    @Override // com.android.server.tare.EconomicPolicy
    void setup(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
        super.setup(properties);
        loadConstants(this.mInjector.getSettingsGlobalString(this.mIrs.getContext().getContentResolver(), "tare_alarm_manager_constants"), properties);
    }

    @Override // com.android.server.tare.EconomicPolicy
    long getMinSatiatedBalance(int i, @android.annotation.NonNull java.lang.String str) {
        if (this.mIrs.isPackageRestricted(i, str)) {
            return 0L;
        }
        if (this.mIrs.isPackageExempted(i, str)) {
            return this.mMinSatiatedBalanceExempted;
        }
        if (this.mIrs.isHeadlessSystemApp(i, str)) {
            return this.mMinSatiatedBalanceHeadlessSystemApp;
        }
        return this.mMinSatiatedBalanceOther;
    }

    @Override // com.android.server.tare.EconomicPolicy
    long getMaxSatiatedBalance(int i, @android.annotation.NonNull java.lang.String str) {
        if (this.mIrs.isPackageRestricted(i, str)) {
            return 0L;
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
        this.mMinSatiatedBalanceOther = getConstantAsCake("am_min_satiated_balance_other_app", android.app.tare.EconomyManager.DEFAULT_AM_MIN_SATIATED_BALANCE_OTHER_APP_CAKES);
        this.mMinSatiatedBalanceHeadlessSystemApp = getConstantAsCake("am_min_satiated_balance_headless_system_app", android.app.tare.EconomyManager.DEFAULT_AM_MIN_SATIATED_BALANCE_HEADLESS_SYSTEM_APP_CAKES, this.mMinSatiatedBalanceOther);
        this.mMinSatiatedBalanceExempted = getConstantAsCake("am_min_satiated_balance_exempted", android.app.tare.EconomyManager.DEFAULT_AM_MIN_SATIATED_BALANCE_EXEMPTED_CAKES, this.mMinSatiatedBalanceHeadlessSystemApp);
        this.mMaxSatiatedBalance = getConstantAsCake("am_max_satiated_balance", android.app.tare.EconomyManager.DEFAULT_AM_MAX_SATIATED_BALANCE_CAKES, java.lang.Math.max(android.app.tare.EconomyManager.arcToCake(1), this.mMinSatiatedBalanceExempted));
        this.mMinSatiatedConsumptionLimit = getConstantAsCake("am_minimum_consumption_limit", android.app.tare.EconomyManager.DEFAULT_AM_MIN_CONSUMPTION_LIMIT_CAKES, android.app.tare.EconomyManager.arcToCake(1));
        this.mInitialSatiatedConsumptionLimit = getConstantAsCake("am_initial_consumption_limit", android.app.tare.EconomyManager.DEFAULT_AM_INITIAL_CONSUMPTION_LIMIT_CAKES, this.mMinSatiatedConsumptionLimit);
        this.mMaxSatiatedConsumptionLimit = getConstantAsCake("am_maximum_consumption_limit", android.app.tare.EconomyManager.DEFAULT_AM_MAX_CONSUMPTION_LIMIT_CAKES, this.mInitialSatiatedConsumptionLimit);
        this.mActions.put(ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE, new com.android.server.tare.EconomicPolicy.Action(ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE, getConstantAsCake("am_action_alarm_allow_while_idle_exact_wakeup_ctp", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_EXACT_WAKEUP_CTP_CAKES), getConstantAsCake("am_action_alarm_allow_while_idle_exact_wakeup_base_price", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_EXACT_WAKEUP_BASE_PRICE_CAKES), false));
        this.mActions.put(ACTION_ALARM_WAKEUP_EXACT, new com.android.server.tare.EconomicPolicy.Action(ACTION_ALARM_WAKEUP_EXACT, getConstantAsCake("am_action_alarm_exact_wakeup_ctp", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_EXACT_WAKEUP_CTP_CAKES), getConstantAsCake("am_action_alarm_exact_wakeup_base_price", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_EXACT_WAKEUP_BASE_PRICE_CAKES), false));
        this.mActions.put(ACTION_ALARM_WAKEUP_INEXACT_ALLOW_WHILE_IDLE, new com.android.server.tare.EconomicPolicy.Action(ACTION_ALARM_WAKEUP_INEXACT_ALLOW_WHILE_IDLE, getConstantAsCake("am_action_alarm_allow_while_idle_inexact_wakeup_ctp", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_INEXACT_WAKEUP_CTP_CAKES), getConstantAsCake("am_action_alarm_allow_while_idle_inexact_wakeup_base_price", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_INEXACT_WAKEUP_BASE_PRICE_CAKES), false));
        this.mActions.put(ACTION_ALARM_WAKEUP_INEXACT, new com.android.server.tare.EconomicPolicy.Action(ACTION_ALARM_WAKEUP_INEXACT, getConstantAsCake("am_action_alarm_inexact_wakeup_ctp", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_INEXACT_WAKEUP_CTP_CAKES), getConstantAsCake("am_action_alarm_inexact_wakeup_base_price", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_INEXACT_WAKEUP_BASE_PRICE_CAKES), false));
        this.mActions.put(ACTION_ALARM_NONWAKEUP_EXACT_ALLOW_WHILE_IDLE, new com.android.server.tare.EconomicPolicy.Action(ACTION_ALARM_NONWAKEUP_EXACT_ALLOW_WHILE_IDLE, getConstantAsCake("am_action_alarm_allow_while_idle_exact_nonwakeup_ctp", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_EXACT_NONWAKEUP_CTP_CAKES), getConstantAsCake("am_action_alarm_allow_while_idle_exact_nonwakeup_base_price", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_INEXACT_NONWAKEUP_BASE_PRICE_CAKES), false));
        this.mActions.put(ACTION_ALARM_NONWAKEUP_EXACT, new com.android.server.tare.EconomicPolicy.Action(ACTION_ALARM_NONWAKEUP_EXACT, getConstantAsCake("am_action_alarm_exact_nonwakeup_ctp", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_EXACT_NONWAKEUP_CTP_CAKES), getConstantAsCake("am_action_alarm_exact_nonwakeup_base_price", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_EXACT_NONWAKEUP_BASE_PRICE_CAKES), false));
        long constantAsCake = getConstantAsCake("am_action_alarm_allow_while_idle_inexact_nonwakeup_base_price", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_INEXACT_NONWAKEUP_BASE_PRICE_CAKES);
        this.mActions.put(ACTION_ALARM_NONWAKEUP_INEXACT_ALLOW_WHILE_IDLE, new com.android.server.tare.EconomicPolicy.Action(ACTION_ALARM_NONWAKEUP_INEXACT_ALLOW_WHILE_IDLE, getConstantAsCake("am_action_alarm_allow_while_idle_inexact_nonwakeup_ctp", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_INEXACT_NONWAKEUP_CTP_CAKES), constantAsCake));
        this.mActions.put(ACTION_ALARM_NONWAKEUP_INEXACT, new com.android.server.tare.EconomicPolicy.Action(ACTION_ALARM_NONWAKEUP_INEXACT, getConstantAsCake("am_action_alarm_inexact_nonwakeup_ctp", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_INEXACT_NONWAKEUP_CTP_CAKES), getConstantAsCake("am_action_alarm_inexact_nonwakeup_base_price", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_INEXACT_NONWAKEUP_BASE_PRICE_CAKES)));
        this.mActions.put(ACTION_ALARM_CLOCK, new com.android.server.tare.EconomicPolicy.Action(ACTION_ALARM_CLOCK, getConstantAsCake("am_action_alarm_alarmclock_ctp", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_ALARMCLOCK_CTP_CAKES), getConstantAsCake("am_action_alarm_alarmclock_base_price", android.app.tare.EconomyManager.DEFAULT_AM_ACTION_ALARM_ALARMCLOCK_BASE_PRICE_CAKES), false));
        this.mRewards.put(android.hardware.audio.common.V2_0.AudioDevice.IN_AMBIENT, new com.android.server.tare.EconomicPolicy.Reward(android.hardware.audio.common.V2_0.AudioDevice.IN_AMBIENT, getConstantAsCake("am_reward_top_activity_instant", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_TOP_ACTIVITY_INSTANT_CAKES), getConstantAsCake("am_reward_top_activity_ongoing", 10000000L), getConstantAsCake("am_reward_top_activity_max", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_TOP_ACTIVITY_MAX_CAKES)));
        this.mRewards.put(Integer.MIN_VALUE, new com.android.server.tare.EconomicPolicy.Reward(Integer.MIN_VALUE, getConstantAsCake("am_reward_notification_seen_instant", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_NOTIFICATION_SEEN_INSTANT_CAKES), getConstantAsCake("am_reward_notification_seen_ongoing", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_NOTIFICATION_SEEN_ONGOING_CAKES), getConstantAsCake("am_reward_notification_seen_max", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_NOTIFICATION_SEEN_MAX_CAKES)));
        this.mRewards.put(-2147483647, new com.android.server.tare.EconomicPolicy.Reward(-2147483647, getConstantAsCake("am_reward_notification_interaction_instant", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_NOTIFICATION_INTERACTION_INSTANT_CAKES), getConstantAsCake("am_reward_notification_interaction_ongoing", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_NOTIFICATION_INTERACTION_ONGOING_CAKES), getConstantAsCake("am_reward_notification_interaction_max", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_NOTIFICATION_INTERACTION_MAX_CAKES)));
        this.mRewards.put(android.hardware.audio.common.V2_0.AudioChannelMask.INDEX_MASK_2, new com.android.server.tare.EconomicPolicy.Reward(android.hardware.audio.common.V2_0.AudioChannelMask.INDEX_MASK_2, getConstantAsCake("am_reward_widget_interaction_instant", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_WIDGET_INTERACTION_INSTANT_CAKES), getConstantAsCake("am_reward_widget_interaction_ongoing", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_WIDGET_INTERACTION_ONGOING_CAKES), getConstantAsCake("am_reward_widget_interaction_max", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_WIDGET_INTERACTION_MAX_CAKES)));
        this.mRewards.put(android.hardware.audio.common.V2_0.AudioDevice.IN_BUILTIN_MIC, new com.android.server.tare.EconomicPolicy.Reward(android.hardware.audio.common.V2_0.AudioDevice.IN_BUILTIN_MIC, getConstantAsCake("am_reward_other_user_interaction_instant", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_OTHER_USER_INTERACTION_INSTANT_CAKES), getConstantAsCake("am_reward_other_user_interaction_ongoing", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_OTHER_USER_INTERACTION_ONGOING_CAKES), getConstantAsCake("am_reward_other_user_interaction_max", android.app.tare.EconomyManager.DEFAULT_AM_REWARD_OTHER_USER_INTERACTION_MAX_CAKES)));
    }

    @Override // com.android.server.tare.EconomicPolicy
    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Min satiated balances:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("Exempted", com.android.server.tare.TareUtils.cakeToString(this.mMinSatiatedBalanceExempted)).println();
        indentingPrintWriter.print("Other", com.android.server.tare.TareUtils.cakeToString(this.mMinSatiatedBalanceOther)).println();
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
