package com.android.server.tare;

/* loaded from: classes2.dex */
public abstract class EconomicPolicy {
    static final int ALL_POLICIES = 805306368;
    static final int MASK_EVENT = 268435455;
    static final int MASK_POLICY = 805306368;
    static final int MASK_TYPE = -1073741824;
    public static final int POLICY_ALARM = 268435456;
    public static final int POLICY_JOB = 536870912;
    static final int REGULATION_BASIC_INCOME = 0;
    static final int REGULATION_BG_RESTRICTED = 5;
    static final int REGULATION_BG_UNRESTRICTED = 6;
    static final int REGULATION_BIRTHRIGHT = 1;
    static final int REGULATION_DEMOTION = 4;
    static final int REGULATION_FORCE_STOP = 8;
    static final int REGULATION_PROMOTION = 3;
    static final int REGULATION_WEALTH_RECLAMATION = 2;
    static final int REWARD_NOTIFICATION_INTERACTION = -2147483647;
    static final int REWARD_NOTIFICATION_SEEN = Integer.MIN_VALUE;
    static final int REWARD_OTHER_USER_INTERACTION = -2147483644;
    static final int REWARD_TOP_ACTIVITY = -2147483646;
    static final int REWARD_WIDGET_INTERACTION = -2147483645;
    private static final int SHIFT_POLICY = 28;
    private static final int SHIFT_TYPE = 30;
    static final int TYPE_ACTION = 1073741824;
    static final int TYPE_REGULATION = 0;
    static final int TYPE_REWARD = Integer.MIN_VALUE;
    protected final com.android.server.tare.InternalResourceService mIrs;
    protected final com.android.server.utils.UserSettingDeviceConfigMediator mUserSettingDeviceConfigMediator = new com.android.server.utils.UserSettingDeviceConfigMediator.SettingsOverridesAllMediator(',');
    private static final java.lang.String TAG = "TARE-" + com.android.server.tare.EconomicPolicy.class.getSimpleName();
    private static final com.android.server.tare.Modifier[] COST_MODIFIER_BY_INDEX = new com.android.server.tare.Modifier[4];

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AppAction {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Policy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UtilityReward {
    }

    @android.annotation.Nullable
    abstract com.android.server.tare.EconomicPolicy.Action getAction(int i);

    @android.annotation.NonNull
    abstract int[] getCostModifiers();

    abstract long getInitialSatiatedConsumptionLimit();

    abstract long getMaxSatiatedBalance(int i, @android.annotation.NonNull java.lang.String str);

    abstract long getMaxSatiatedConsumptionLimit();

    abstract long getMinSatiatedBalance(int i, @android.annotation.NonNull java.lang.String str);

    abstract long getMinSatiatedConsumptionLimit();

    @android.annotation.Nullable
    abstract com.android.server.tare.EconomicPolicy.Reward getReward(int i);

    static class Action {
        public final long basePrice;
        public final long costToProduce;
        public final int id;
        public final boolean respectsStockLimit;

        Action(int i, long j, long j2) {
            this(i, j, j2, true);
        }

        Action(int i, long j, long j2, boolean z) {
            this.id = i;
            this.costToProduce = j;
            this.basePrice = j2;
            this.respectsStockLimit = z;
        }
    }

    static class Reward {
        public final int id;
        public final long instantReward;
        public final long maxDailyReward;
        public final long ongoingRewardPerSecond;

        Reward(int i, long j, long j2, long j3) {
            this.id = i;
            this.instantReward = j;
            this.ongoingRewardPerSecond = j2;
            this.maxDailyReward = j3;
        }
    }

    static class Cost {
        public final long costToProduce;
        public final long price;

        Cost(long j, long j2) {
            this.costToProduce = j;
            this.price = j2;
        }
    }

    EconomicPolicy(@android.annotation.NonNull com.android.server.tare.InternalResourceService internalResourceService) {
        this.mIrs = internalResourceService;
        for (int i : getCostModifiers()) {
            initModifier(i, internalResourceService);
        }
    }

    void setup(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
        for (int i = 0; i < 4; i++) {
            com.android.server.tare.Modifier modifier = COST_MODIFIER_BY_INDEX[i];
            if (modifier != null) {
                modifier.setup();
            }
        }
    }

    void tearDown() {
        for (int i = 0; i < 4; i++) {
            com.android.server.tare.Modifier modifier = COST_MODIFIER_BY_INDEX[i];
            if (modifier != null) {
                modifier.tearDown();
            }
        }
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
    }

    @android.annotation.NonNull
    final com.android.server.tare.EconomicPolicy.Cost getCostOfAction(int i, int i2, @android.annotation.NonNull java.lang.String str) {
        com.android.server.tare.EconomicPolicy.Action action = getAction(i);
        if (action == null || this.mIrs.isVip(i2, str)) {
            return new com.android.server.tare.EconomicPolicy.Cost(0L, 0L);
        }
        long j = action.costToProduce;
        long j2 = action.basePrice;
        long j3 = j;
        long j4 = j2;
        boolean z = false;
        for (int i3 : getCostModifiers()) {
            if (i3 == 3) {
                z = true;
            } else {
                com.android.server.tare.Modifier modifier = getModifier(i3);
                long modifiedCostToProduce = modifier.getModifiedCostToProduce(j3);
                j4 = modifier.getModifiedPrice(j4);
                j3 = modifiedCostToProduce;
            }
        }
        if (z) {
            j4 = ((com.android.server.tare.ProcessStateModifier) getModifier(3)).getModifiedPrice(i2, str, j3, j4);
        }
        return new com.android.server.tare.EconomicPolicy.Cost(j3, j4);
    }

    private static void initModifier(int i, @android.annotation.NonNull com.android.server.tare.InternalResourceService internalResourceService) {
        com.android.server.tare.Modifier chargingModifier;
        if (i < 0 || i >= COST_MODIFIER_BY_INDEX.length) {
            throw new java.lang.IllegalArgumentException("Invalid modifier id " + i);
        }
        if (COST_MODIFIER_BY_INDEX[i] == null) {
            switch (i) {
                case 0:
                    chargingModifier = new com.android.server.tare.ChargingModifier(internalResourceService);
                    break;
                case 1:
                    chargingModifier = new com.android.server.tare.DeviceIdleModifier(internalResourceService);
                    break;
                case 2:
                    chargingModifier = new com.android.server.tare.PowerSaveModeModifier(internalResourceService);
                    break;
                case 3:
                    chargingModifier = new com.android.server.tare.ProcessStateModifier(internalResourceService);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid modifier id " + i);
            }
            COST_MODIFIER_BY_INDEX[i] = chargingModifier;
        }
    }

    @android.annotation.NonNull
    private static com.android.server.tare.Modifier getModifier(int i) {
        if (i < 0 || i >= COST_MODIFIER_BY_INDEX.length) {
            throw new java.lang.IllegalArgumentException("Invalid modifier id " + i);
        }
        com.android.server.tare.Modifier modifier = COST_MODIFIER_BY_INDEX[i];
        if (modifier == null) {
            throw new java.lang.IllegalStateException("Modifier #" + i + " was never initialized");
        }
        return modifier;
    }

    static int getEventType(int i) {
        return i & (-1073741824);
    }

    static boolean isReward(int i) {
        return getEventType(i) == Integer.MIN_VALUE;
    }

    @android.annotation.NonNull
    static java.lang.String eventToString(int i) {
        switch ((-1073741824) & i) {
            case Integer.MIN_VALUE:
                return rewardToString(i);
            case 0:
                return regulationToString(i);
            case 1073741824:
                return actionToString(i);
            default:
                return "UNKNOWN_EVENT:" + java.lang.Integer.toHexString(i);
        }
    }

    @android.annotation.NonNull
    static java.lang.String actionToString(int i) {
        switch (805306368 & i) {
            case 268435456:
                switch (i) {
                    case com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE /* 1342177280 */:
                        return "ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE";
                    case com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT /* 1342177281 */:
                        return "ALARM_WAKEUP_EXACT";
                    case com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_INEXACT_ALLOW_WHILE_IDLE /* 1342177282 */:
                        return "ALARM_WAKEUP_INEXACT_ALLOW_WHILE_IDLE";
                    case com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_INEXACT /* 1342177283 */:
                        return "ALARM_WAKEUP_INEXACT";
                    case com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_NONWAKEUP_EXACT_ALLOW_WHILE_IDLE /* 1342177284 */:
                        return "ALARM_NONWAKEUP_EXACT_ALLOW_WHILE_IDLE";
                    case com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_NONWAKEUP_EXACT /* 1342177285 */:
                        return "ALARM_NONWAKEUP_EXACT";
                    case com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_NONWAKEUP_INEXACT_ALLOW_WHILE_IDLE /* 1342177286 */:
                        return "ALARM_NONWAKEUP_INEXACT_ALLOW_WHILE_IDLE";
                    case com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_NONWAKEUP_INEXACT /* 1342177287 */:
                        return "ALARM_NONWAKEUP_INEXACT";
                    case com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_CLOCK /* 1342177288 */:
                        return "ALARM_CLOCK";
                }
            case 536870912:
                switch (i) {
                    case com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MAX_START /* 1610612736 */:
                        return "JOB_MAX_START";
                    case com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MAX_RUNNING /* 1610612737 */:
                        return "JOB_MAX_RUNNING";
                    case com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_HIGH_START /* 1610612738 */:
                        return "JOB_HIGH_START";
                    case com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_HIGH_RUNNING /* 1610612739 */:
                        return "JOB_HIGH_RUNNING";
                    case com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_START /* 1610612740 */:
                        return "JOB_DEFAULT_START";
                    case com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_RUNNING /* 1610612741 */:
                        return "JOB_DEFAULT_RUNNING";
                    case com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_LOW_START /* 1610612742 */:
                        return "JOB_LOW_START";
                    case com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_LOW_RUNNING /* 1610612743 */:
                        return "JOB_LOW_RUNNING";
                    case com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MIN_START /* 1610612744 */:
                        return "JOB_MIN_START";
                    case com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MIN_RUNNING /* 1610612745 */:
                        return "JOB_MIN_RUNNING";
                    case com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_TIMEOUT /* 1610612746 */:
                        return "JOB_TIMEOUT";
                }
        }
        return "UNKNOWN_ACTION:" + java.lang.Integer.toHexString(i);
    }

    @android.annotation.NonNull
    static java.lang.String regulationToString(int i) {
        switch (i) {
            case 0:
                return "BASIC_INCOME";
            case 1:
                return "BIRTHRIGHT";
            case 2:
                return "WEALTH_RECLAMATION";
            case 3:
                return "PROMOTION";
            case 4:
                return "DEMOTION";
            case 5:
                return "BG_RESTRICTED";
            case 6:
                return "BG_UNRESTRICTED";
            case 7:
            default:
                return "UNKNOWN_REGULATION:" + java.lang.Integer.toHexString(i);
            case 8:
                return "FORCE_STOP";
        }
    }

    @android.annotation.NonNull
    static java.lang.String rewardToString(int i) {
        switch (i) {
            case Integer.MIN_VALUE:
                return "REWARD_NOTIFICATION_SEEN";
            case -2147483647:
                return "REWARD_NOTIFICATION_INTERACTION";
            case -2147483646:
                return "REWARD_TOP_ACTIVITY";
            case -2147483645:
                return "REWARD_WIDGET_INTERACTION";
            case -2147483644:
                return "REWARD_OTHER_USER_INTERACTION";
            case com.android.server.tare.JobSchedulerEconomicPolicy.REWARD_APP_INSTALL /* -1610612736 */:
                return "REWARD_JOB_APP_INSTALL";
            default:
                return "UNKNOWN_REWARD:" + java.lang.Integer.toHexString(i);
        }
    }

    protected long getConstantAsCake(java.lang.String str, long j) {
        return getConstantAsCake(str, j, 0L);
    }

    protected long getConstantAsCake(java.lang.String str, long j, long j2) {
        return java.lang.Math.max(j2, android.app.tare.EconomyManager.parseCreditValue(this.mUserSettingDeviceConfigMediator.getString(str, null), j));
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        @android.annotation.Nullable
        java.lang.String getSettingsGlobalString(@android.annotation.NonNull android.content.ContentResolver contentResolver, @android.annotation.NonNull java.lang.String str) {
            return android.provider.Settings.Global.getString(contentResolver, str);
        }
    }

    protected static void dumpActiveModifiers(android.util.IndentingPrintWriter indentingPrintWriter) {
        for (int i = 0; i < 4; i++) {
            indentingPrintWriter.print("Modifier ");
            indentingPrintWriter.println(i);
            indentingPrintWriter.increaseIndent();
            com.android.server.tare.Modifier modifier = COST_MODIFIER_BY_INDEX[i];
            if (modifier != null) {
                modifier.dump(indentingPrintWriter);
            } else {
                indentingPrintWriter.println("NOT ACTIVE");
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    protected static void dumpAction(android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull com.android.server.tare.EconomicPolicy.Action action) {
        indentingPrintWriter.print(actionToString(action.id));
        indentingPrintWriter.print(": ");
        indentingPrintWriter.print("ctp=");
        indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(action.costToProduce));
        indentingPrintWriter.print(", basePrice=");
        indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(action.basePrice));
        indentingPrintWriter.println();
    }

    protected static void dumpReward(android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull com.android.server.tare.EconomicPolicy.Reward reward) {
        indentingPrintWriter.print(rewardToString(reward.id));
        indentingPrintWriter.print(": ");
        indentingPrintWriter.print("instant=");
        indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(reward.instantReward));
        indentingPrintWriter.print(", ongoing/sec=");
        indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(reward.ongoingRewardPerSecond));
        indentingPrintWriter.print(", maxDaily=");
        indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(reward.maxDailyReward));
        indentingPrintWriter.println();
    }
}
