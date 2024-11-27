package com.android.server.tare;

/* loaded from: classes2.dex */
public class CompleteEconomicPolicy extends com.android.server.tare.EconomicPolicy {
    private static final java.lang.String TAG = "TARE-" + com.android.server.tare.CompleteEconomicPolicy.class.getSimpleName();
    private final android.util.SparseArray<com.android.server.tare.EconomicPolicy.Action> mActions;
    private int[] mCostModifiers;
    private final android.util.ArraySet<com.android.server.tare.EconomicPolicy> mEnabledEconomicPolicies;
    private int mEnabledEconomicPolicyIds;
    private long mInitialConsumptionLimit;
    private final com.android.server.tare.CompleteEconomicPolicy.CompleteInjector mInjector;
    private long mMaxConsumptionLimit;
    private long mMinConsumptionLimit;
    private final android.util.SparseArray<com.android.server.tare.EconomicPolicy.Reward> mRewards;

    CompleteEconomicPolicy(@android.annotation.NonNull com.android.server.tare.InternalResourceService internalResourceService) {
        this(internalResourceService, new com.android.server.tare.CompleteEconomicPolicy.CompleteInjector());
    }

    @com.android.internal.annotations.VisibleForTesting
    CompleteEconomicPolicy(@android.annotation.NonNull com.android.server.tare.InternalResourceService internalResourceService, @android.annotation.NonNull com.android.server.tare.CompleteEconomicPolicy.CompleteInjector completeInjector) {
        super(internalResourceService);
        this.mEnabledEconomicPolicies = new android.util.ArraySet<>();
        this.mActions = new android.util.SparseArray<>();
        this.mRewards = new android.util.SparseArray<>();
        this.mEnabledEconomicPolicyIds = 0;
        this.mCostModifiers = libcore.util.EmptyArray.INT;
        this.mInjector = completeInjector;
        if (this.mInjector.isPolicyEnabled(268435456, null)) {
            this.mEnabledEconomicPolicyIds |= 268435456;
            this.mEnabledEconomicPolicies.add(new com.android.server.tare.AlarmManagerEconomicPolicy(this.mIrs, this.mInjector));
        }
        if (this.mInjector.isPolicyEnabled(536870912, null)) {
            this.mEnabledEconomicPolicyIds |= 536870912;
            this.mEnabledEconomicPolicies.add(new com.android.server.tare.JobSchedulerEconomicPolicy(this.mIrs, this.mInjector));
        }
    }

    @Override // com.android.server.tare.EconomicPolicy
    void setup(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
        super.setup(properties);
        this.mActions.clear();
        this.mRewards.clear();
        this.mEnabledEconomicPolicies.clear();
        this.mEnabledEconomicPolicyIds = 0;
        if (this.mInjector.isPolicyEnabled(268435456, properties)) {
            this.mEnabledEconomicPolicyIds |= 268435456;
            this.mEnabledEconomicPolicies.add(new com.android.server.tare.AlarmManagerEconomicPolicy(this.mIrs, this.mInjector));
        }
        if (this.mInjector.isPolicyEnabled(536870912, properties)) {
            this.mEnabledEconomicPolicyIds |= 536870912;
            this.mEnabledEconomicPolicies.add(new com.android.server.tare.JobSchedulerEconomicPolicy(this.mIrs, this.mInjector));
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (int i = 0; i < this.mEnabledEconomicPolicies.size(); i++) {
            for (int i2 : this.mEnabledEconomicPolicies.valueAt(i).getCostModifiers()) {
                arraySet.add(java.lang.Integer.valueOf(i2));
            }
        }
        this.mCostModifiers = com.android.internal.util.jobs.ArrayUtils.convertToIntArray((android.util.ArraySet<java.lang.Integer>) arraySet);
        for (int i3 = 0; i3 < this.mEnabledEconomicPolicies.size(); i3++) {
            this.mEnabledEconomicPolicies.valueAt(i3).setup(properties);
        }
        updateLimits();
    }

    private void updateLimits() {
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        for (int i = 0; i < this.mEnabledEconomicPolicies.size(); i++) {
            com.android.server.tare.EconomicPolicy valueAt = this.mEnabledEconomicPolicies.valueAt(i);
            j += valueAt.getInitialSatiatedConsumptionLimit();
            j3 += valueAt.getMinSatiatedConsumptionLimit();
            j2 += valueAt.getMaxSatiatedConsumptionLimit();
        }
        this.mInitialConsumptionLimit = j;
        this.mMinConsumptionLimit = j3;
        this.mMaxConsumptionLimit = j2;
    }

    @Override // com.android.server.tare.EconomicPolicy
    long getMinSatiatedBalance(int i, @android.annotation.NonNull java.lang.String str) {
        long j = 0;
        for (int i2 = 0; i2 < this.mEnabledEconomicPolicies.size(); i2++) {
            j += this.mEnabledEconomicPolicies.valueAt(i2).getMinSatiatedBalance(i, str);
        }
        return j;
    }

    @Override // com.android.server.tare.EconomicPolicy
    long getMaxSatiatedBalance(int i, @android.annotation.NonNull java.lang.String str) {
        long j = 0;
        for (int i2 = 0; i2 < this.mEnabledEconomicPolicies.size(); i2++) {
            j += this.mEnabledEconomicPolicies.valueAt(i2).getMaxSatiatedBalance(i, str);
        }
        return j;
    }

    @Override // com.android.server.tare.EconomicPolicy
    long getInitialSatiatedConsumptionLimit() {
        return this.mInitialConsumptionLimit;
    }

    @Override // com.android.server.tare.EconomicPolicy
    long getMinSatiatedConsumptionLimit() {
        return this.mMinConsumptionLimit;
    }

    @Override // com.android.server.tare.EconomicPolicy
    long getMaxSatiatedConsumptionLimit() {
        return this.mMaxConsumptionLimit;
    }

    @Override // com.android.server.tare.EconomicPolicy
    @android.annotation.NonNull
    int[] getCostModifiers() {
        return this.mCostModifiers == null ? libcore.util.EmptyArray.INT : this.mCostModifiers;
    }

    @Override // com.android.server.tare.EconomicPolicy
    @android.annotation.Nullable
    com.android.server.tare.EconomicPolicy.Action getAction(int i) {
        if (this.mActions.contains(i)) {
            return this.mActions.get(i);
        }
        long j = 0;
        long j2 = 0;
        boolean z = false;
        for (int i2 = 0; i2 < this.mEnabledEconomicPolicies.size(); i2++) {
            com.android.server.tare.EconomicPolicy.Action action = this.mEnabledEconomicPolicies.valueAt(i2).getAction(i);
            if (action != null) {
                j += action.costToProduce;
                j2 += action.basePrice;
                z = true;
            }
        }
        com.android.server.tare.EconomicPolicy.Action action2 = z ? new com.android.server.tare.EconomicPolicy.Action(i, j, j2) : null;
        this.mActions.put(i, action2);
        return action2;
    }

    @Override // com.android.server.tare.EconomicPolicy
    @android.annotation.Nullable
    com.android.server.tare.EconomicPolicy.Reward getReward(int i) {
        if (this.mRewards.contains(i)) {
            return this.mRewards.get(i);
        }
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        boolean z = false;
        for (int i2 = 0; i2 < this.mEnabledEconomicPolicies.size(); i2++) {
            com.android.server.tare.EconomicPolicy.Reward reward = this.mEnabledEconomicPolicies.valueAt(i2).getReward(i);
            if (reward != null) {
                j += reward.instantReward;
                j2 += reward.ongoingRewardPerSecond;
                j3 += reward.maxDailyReward;
                z = true;
            }
        }
        com.android.server.tare.EconomicPolicy.Reward reward2 = z ? new com.android.server.tare.EconomicPolicy.Reward(i, j, j2, j3) : null;
        this.mRewards.put(i, reward2);
        return reward2;
    }

    boolean isPolicyEnabled(int i) {
        return (this.mEnabledEconomicPolicyIds & i) == i;
    }

    int getEnabledPolicyIds() {
        return this.mEnabledEconomicPolicyIds;
    }

    @com.android.internal.annotations.VisibleForTesting
    static class CompleteInjector extends com.android.server.tare.EconomicPolicy.Injector {
        CompleteInjector() {
        }

        boolean isPolicyEnabled(int i, @android.annotation.Nullable android.provider.DeviceConfig.Properties properties) {
            java.lang.String str;
            switch (i) {
                case 268435456:
                    str = "enable_policy_alarm";
                    break;
                case 536870912:
                    str = "enable_policy_job";
                    break;
                default:
                    android.util.Slog.wtf(com.android.server.tare.CompleteEconomicPolicy.TAG, "Unknown policy: " + i);
                    return false;
            }
            if (properties == null) {
                return true;
            }
            return properties.getBoolean(str, true);
        }
    }

    @Override // com.android.server.tare.EconomicPolicy
    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        com.android.server.tare.EconomicPolicy.dumpActiveModifiers(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.println(getClass().getSimpleName() + ":");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Cached actions:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mActions.size(); i++) {
            com.android.server.tare.EconomicPolicy.Action valueAt = this.mActions.valueAt(i);
            if (valueAt != null) {
                com.android.server.tare.EconomicPolicy.dumpAction(indentingPrintWriter, valueAt);
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("Cached rewards:");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < this.mRewards.size(); i2++) {
            com.android.server.tare.EconomicPolicy.Reward valueAt2 = this.mRewards.valueAt(i2);
            if (valueAt2 != null) {
                com.android.server.tare.EconomicPolicy.dumpReward(indentingPrintWriter, valueAt2);
            }
        }
        indentingPrintWriter.decreaseIndent();
        for (int i3 = 0; i3 < this.mEnabledEconomicPolicies.size(); i3++) {
            com.android.server.tare.EconomicPolicy valueAt3 = this.mEnabledEconomicPolicies.valueAt(i3);
            indentingPrintWriter.println();
            indentingPrintWriter.print("(Includes) ");
            indentingPrintWriter.println(valueAt3.getClass().getSimpleName() + ":");
            indentingPrintWriter.increaseIndent();
            valueAt3.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }
}
