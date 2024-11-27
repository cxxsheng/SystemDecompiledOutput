package com.android.server.notification;

/* loaded from: classes2.dex */
class ZenModeEventLogger {
    protected static final int ACTIVE_RULE_TYPE_MANUAL = 999;
    private static final java.lang.String TAG = "ZenModeEventLogger";
    static final int ZEN_MODE_UNKNOWN = -1;
    com.android.server.notification.ZenModeEventLogger.ZenStateChanges mChangeState = new com.android.server.notification.ZenModeEventLogger.ZenStateChanges();
    private final android.content.pm.PackageManager mPm;

    ZenModeEventLogger(android.content.pm.PackageManager packageManager) {
        this.mPm = packageManager;
    }

    enum ZenStateChangedEvent implements com.android.internal.logging.UiEventLogger.UiEventEnum {
        DND_TURNED_ON(1368),
        DND_TURNED_OFF(1369),
        DND_POLICY_CHANGED(1370),
        DND_ACTIVE_RULES_CHANGED(1371);

        private final int mId;

        ZenStateChangedEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    public final void maybeLogZenChange(com.android.server.notification.ZenModeEventLogger.ZenModeInfo zenModeInfo, com.android.server.notification.ZenModeEventLogger.ZenModeInfo zenModeInfo2, int i, int i2) {
        this.mChangeState.init(zenModeInfo, zenModeInfo2, i, i2);
        if (this.mChangeState.shouldLogChanges()) {
            maybeReassignCallingUid();
            logChanges();
        }
        this.mChangeState = new com.android.server.notification.ZenModeEventLogger.ZenStateChanges();
    }

    private void maybeReassignCallingUid() {
        java.lang.String str;
        int i;
        android.util.Pair rulePackageAndUser;
        if (this.mChangeState.getChangedRuleType() != 1) {
            str = null;
            i = -1;
        } else {
            if (!this.mChangeState.isFromSystemOrSystemUi() || this.mChangeState.getNewManualRuleEnabler() == null) {
                return;
            }
            str = this.mChangeState.getNewManualRuleEnabler();
            i = this.mChangeState.mNewConfig.user;
        }
        if (this.mChangeState.getChangedRuleType() == 2) {
            if (this.mChangeState.getIsUserAction() || !this.mChangeState.isFromSystemOrSystemUi()) {
                return;
            }
            android.util.ArrayMap changedAutomaticRules = this.mChangeState.getChangedAutomaticRules();
            if (changedAutomaticRules.size() != 1 || (rulePackageAndUser = this.mChangeState.getRulePackageAndUser((java.lang.String) changedAutomaticRules.keyAt(0), (android.service.notification.ZenModeDiff.RuleDiff) changedAutomaticRules.valueAt(0))) == null || ((java.lang.String) rulePackageAndUser.first).equals(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME)) {
                return;
            }
            java.lang.String str2 = (java.lang.String) rulePackageAndUser.first;
            i = ((java.lang.Integer) rulePackageAndUser.second).intValue();
            str = str2;
        }
        if (i == -1 || str == null) {
            return;
        }
        try {
            this.mChangeState.mCallingUid = this.mPm.getPackageUidAsUser(str, i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "unable to find package name " + str + " " + i);
        }
    }

    void logChanges() {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DND_STATE_CHANGED, this.mChangeState.getEventId().getId(), this.mChangeState.mNewZenMode, this.mChangeState.mPrevZenMode, this.mChangeState.getChangedRuleType(), this.mChangeState.getNumRulesActive(), this.mChangeState.getIsUserAction(), this.mChangeState.getPackageUid(), this.mChangeState.getDNDPolicyProto(), this.mChangeState.getAreChannelsBypassing(), this.mChangeState.getActiveRuleTypes());
    }

    public static class ZenModeInfo {
        final android.service.notification.ZenModeConfig mConfig;
        final android.app.NotificationManager.Policy mPolicy;
        final int mZenMode;

        ZenModeInfo(int i, android.service.notification.ZenModeConfig zenModeConfig, android.app.NotificationManager.Policy policy) {
            this.mZenMode = i;
            this.mConfig = zenModeConfig != null ? zenModeConfig.copy() : null;
            this.mPolicy = policy != null ? policy.copy() : null;
        }
    }

    static class ZenStateChanges {
        android.service.notification.ZenModeConfig mNewConfig;
        android.app.NotificationManager.Policy mNewPolicy;
        android.service.notification.ZenModeConfig mPrevConfig;
        android.app.NotificationManager.Policy mPrevPolicy;
        int mPrevZenMode = -1;
        int mNewZenMode = -1;
        int mCallingUid = -1;
        int mOrigin = 0;

        ZenStateChanges() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void init(com.android.server.notification.ZenModeEventLogger.ZenModeInfo zenModeInfo, com.android.server.notification.ZenModeEventLogger.ZenModeInfo zenModeInfo2, int i, int i2) {
            this.mPrevZenMode = zenModeInfo.mZenMode;
            this.mNewZenMode = zenModeInfo2.mZenMode;
            this.mPrevConfig = zenModeInfo.mConfig;
            this.mNewConfig = zenModeInfo2.mConfig;
            this.mPrevPolicy = zenModeInfo.mPolicy;
            this.mNewPolicy = zenModeInfo2.mPolicy;
            this.mCallingUid = i;
            this.mOrigin = i2;
        }

        private boolean hasPolicyDiff() {
            return (this.mPrevPolicy == null || java.util.Objects.equals(this.mPrevPolicy, this.mNewPolicy)) ? false : true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean shouldLogChanges() {
            if (zenModeFlipped()) {
                return true;
            }
            if (android.app.Flags.modesApi() && hasActiveRuleCountDiff()) {
                return true;
            }
            if (this.mNewZenMode == 0) {
                return false;
            }
            return hasPolicyDiff() || hasActiveRuleCountDiff();
        }

        private boolean zenModeFlipped() {
            if (this.mPrevZenMode == this.mNewZenMode) {
                return false;
            }
            return this.mPrevZenMode == 0 || this.mNewZenMode == 0;
        }

        com.android.server.notification.ZenModeEventLogger.ZenStateChangedEvent getEventId() {
            if (!shouldLogChanges()) {
                android.util.Log.wtf(com.android.server.notification.ZenModeEventLogger.TAG, "attempt to get DNDStateChanged fields without shouldLog=true");
            }
            if (zenModeFlipped()) {
                if (this.mPrevZenMode == 0) {
                    return com.android.server.notification.ZenModeEventLogger.ZenStateChangedEvent.DND_TURNED_ON;
                }
                return com.android.server.notification.ZenModeEventLogger.ZenStateChangedEvent.DND_TURNED_OFF;
            }
            if (android.app.Flags.modesApi() && this.mNewZenMode == 0) {
                if (hasPolicyDiff() || hasChannelsBypassingDiff()) {
                    android.util.Log.wtf(com.android.server.notification.ZenModeEventLogger.TAG, "Detected policy diff even though DND is OFF and not toggled");
                }
                return com.android.server.notification.ZenModeEventLogger.ZenStateChangedEvent.DND_ACTIVE_RULES_CHANGED;
            }
            if (hasPolicyDiff() || hasChannelsBypassingDiff()) {
                return com.android.server.notification.ZenModeEventLogger.ZenStateChangedEvent.DND_POLICY_CHANGED;
            }
            return com.android.server.notification.ZenModeEventLogger.ZenStateChangedEvent.DND_ACTIVE_RULES_CHANGED;
        }

        int getChangedRuleType() {
            android.service.notification.ZenModeDiff.ConfigDiff configDiff = new android.service.notification.ZenModeDiff.ConfigDiff(this.mPrevConfig, this.mNewConfig);
            if (!configDiff.hasDiff()) {
                return 0;
            }
            android.service.notification.ZenModeDiff.RuleDiff manualRuleDiff = configDiff.getManualRuleDiff();
            if (manualRuleDiff != null && manualRuleDiff.hasDiff() && (manualRuleDiff.wasAdded() || manualRuleDiff.wasRemoved())) {
                return 1;
            }
            android.util.ArrayMap allAutomaticRuleDiffs = configDiff.getAllAutomaticRuleDiffs();
            if (allAutomaticRuleDiffs != null) {
                for (android.service.notification.ZenModeDiff.RuleDiff ruleDiff : allAutomaticRuleDiffs.values()) {
                    if (ruleDiff != null && ruleDiff.hasDiff() && (ruleDiff.becameActive() || ruleDiff.becameInactive())) {
                        return 2;
                    }
                }
            }
            return 0;
        }

        private boolean hasActiveRuleCountDiff() {
            return numActiveRulesInConfig(this.mPrevConfig) != numActiveRulesInConfig(this.mNewConfig);
        }

        @android.annotation.NonNull
        @android.annotation.SuppressLint({"WrongConstant"})
        java.util.List<android.service.notification.ZenModeConfig.ZenRule> activeRulesList(android.service.notification.ZenModeConfig zenModeConfig) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (zenModeConfig == null) {
                return arrayList;
            }
            if (zenModeConfig.manualRule != null) {
                android.service.notification.ZenModeConfig.ZenRule copy = zenModeConfig.manualRule.copy();
                copy.type = 999;
                arrayList.add(copy);
            }
            if (zenModeConfig.automaticRules != null) {
                for (android.service.notification.ZenModeConfig.ZenRule zenRule : zenModeConfig.automaticRules.values()) {
                    if (zenRule != null && zenRule.isAutomaticActive()) {
                        arrayList.add(zenRule);
                    }
                }
            }
            return arrayList;
        }

        int numActiveRulesInConfig(android.service.notification.ZenModeConfig zenModeConfig) {
            return activeRulesList(zenModeConfig).size();
        }

        int getNumRulesActive() {
            if (!android.app.Flags.modesApi() && this.mNewZenMode == 0) {
                return 0;
            }
            return numActiveRulesInConfig(this.mNewConfig);
        }

        int[] getActiveRuleTypes() {
            if (!android.app.Flags.modesApi() || this.mNewZenMode == 0) {
                return new int[0];
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.List<android.service.notification.ZenModeConfig.ZenRule> activeRulesList = activeRulesList(this.mNewConfig);
            if (activeRulesList.size() == 0) {
                return new int[0];
            }
            java.util.Iterator<android.service.notification.ZenModeConfig.ZenRule> it = activeRulesList.iterator();
            while (it.hasNext()) {
                arrayList.add(java.lang.Integer.valueOf(it.next().type));
            }
            java.util.Collections.sort(arrayList);
            int[] iArr = new int[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                iArr[i] = ((java.lang.Integer) arrayList.get(i)).intValue();
            }
            return iArr;
        }

        boolean getIsUserAction() {
            if (android.app.Flags.modesApi()) {
                return this.mOrigin == 3;
            }
            switch (getChangedRuleType()) {
                case 1:
                    return isFromSystemOrSystemUi() && getNewManualRuleEnabler() == null;
                case 2:
                    for (android.service.notification.ZenModeDiff.RuleDiff ruleDiff : getChangedAutomaticRules().values()) {
                        if (ruleDiff.wasAdded() || ruleDiff.wasRemoved()) {
                            return isFromSystemOrSystemUi();
                        }
                        android.service.notification.ZenModeDiff.FieldDiff diffForField = ruleDiff.getDiffForField(com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED);
                        if (diffForField != null && diffForField.hasDiff()) {
                            return true;
                        }
                        android.service.notification.ZenModeDiff.FieldDiff diffForField2 = ruleDiff.getDiffForField("snoozing");
                        if (diffForField2 != null && diffForField2.hasDiff() && ((java.lang.Boolean) diffForField2.to()).booleanValue()) {
                            return true;
                        }
                    }
                    return false;
                default:
                    return (hasPolicyDiff() || hasChannelsBypassingDiff()) && this.mCallingUid == 1000;
            }
        }

        boolean isFromSystemOrSystemUi() {
            return this.mOrigin == 1 || this.mOrigin == 2 || this.mOrigin == 5 || this.mOrigin == 6;
        }

        int getPackageUid() {
            return this.mCallingUid;
        }

        @android.annotation.Nullable
        byte[] getDNDPolicyProto() {
            int i;
            if (android.app.Flags.modesApi() && this.mNewZenMode == 0) {
                return null;
            }
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(byteArrayOutputStream);
            if (this.mNewPolicy != null) {
                protoOutputStream.write(1159641169921L, toState(this.mNewPolicy.allowCalls()));
                protoOutputStream.write(1159641169922L, toState(this.mNewPolicy.allowRepeatCallers()));
                protoOutputStream.write(1159641169923L, toState(this.mNewPolicy.allowMessages()));
                protoOutputStream.write(1159641169924L, toState(this.mNewPolicy.allowConversations()));
                protoOutputStream.write(1159641169925L, toState(this.mNewPolicy.allowReminders()));
                protoOutputStream.write(1159641169926L, toState(this.mNewPolicy.allowEvents()));
                protoOutputStream.write(1159641169927L, toState(this.mNewPolicy.allowAlarms()));
                protoOutputStream.write(1159641169928L, toState(this.mNewPolicy.allowMedia()));
                protoOutputStream.write(1159641169929L, toState(this.mNewPolicy.allowSystem()));
                protoOutputStream.write(1159641169930L, toState(this.mNewPolicy.showFullScreenIntents()));
                protoOutputStream.write(1159641169931L, toState(this.mNewPolicy.showLights()));
                protoOutputStream.write(1159641169932L, toState(this.mNewPolicy.showPeeking()));
                protoOutputStream.write(1159641169933L, toState(this.mNewPolicy.showStatusBarIcons()));
                protoOutputStream.write(1159641169934L, toState(this.mNewPolicy.showBadges()));
                protoOutputStream.write(1159641169935L, toState(this.mNewPolicy.showAmbient()));
                protoOutputStream.write(1159641169936L, toState(this.mNewPolicy.showInNotificationList()));
                protoOutputStream.write(1159641169937L, android.service.notification.ZenModeConfig.getZenPolicySenders(this.mNewPolicy.allowCallsFrom()));
                protoOutputStream.write(1159641169938L, android.service.notification.ZenModeConfig.getZenPolicySenders(this.mNewPolicy.allowMessagesFrom()));
                protoOutputStream.write(1159641169939L, this.mNewPolicy.allowConversationsFrom());
                if (android.app.Flags.modesApi()) {
                    if (this.mNewPolicy.allowPriorityChannels()) {
                        i = 1;
                    } else {
                        i = 2;
                    }
                    protoOutputStream.write(1159641169940L, i);
                }
            } else {
                android.util.Log.wtf(com.android.server.notification.ZenModeEventLogger.TAG, "attempted to write zen mode log event with null policy");
            }
            protoOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        }

        boolean getAreChannelsBypassing() {
            return (this.mNewPolicy == null || (this.mNewPolicy.state & 1) == 0) ? false : true;
        }

        private boolean hasChannelsBypassingDiff() {
            return (this.mPrevPolicy != null && (this.mPrevPolicy.state & 1) != 0) != getAreChannelsBypassing();
        }

        private int toState(boolean z) {
            return z ? 1 : 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        public android.util.ArrayMap<java.lang.String, android.service.notification.ZenModeDiff.RuleDiff> getChangedAutomaticRules() {
            android.util.ArrayMap<java.lang.String, android.service.notification.ZenModeDiff.RuleDiff> arrayMap = new android.util.ArrayMap<>();
            android.service.notification.ZenModeDiff.ConfigDiff configDiff = new android.service.notification.ZenModeDiff.ConfigDiff(this.mPrevConfig, this.mNewConfig);
            if (!configDiff.hasDiff()) {
                return arrayMap;
            }
            android.util.ArrayMap<java.lang.String, android.service.notification.ZenModeDiff.RuleDiff> allAutomaticRuleDiffs = configDiff.getAllAutomaticRuleDiffs();
            if (allAutomaticRuleDiffs != null) {
                return allAutomaticRuleDiffs;
            }
            return arrayMap;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.util.Pair<java.lang.String, java.lang.Integer> getRulePackageAndUser(java.lang.String str, android.service.notification.ZenModeDiff.RuleDiff ruleDiff) {
            android.service.notification.ZenModeConfig.ZenRule zenRule;
            android.service.notification.ZenModeConfig zenModeConfig = this.mNewConfig;
            if (ruleDiff.wasRemoved()) {
                zenModeConfig = this.mPrevConfig;
            }
            if (zenModeConfig != null && (zenRule = (android.service.notification.ZenModeConfig.ZenRule) zenModeConfig.automaticRules.getOrDefault(str, null)) != null) {
                if (zenRule.component != null) {
                    return new android.util.Pair<>(zenRule.component.getPackageName(), java.lang.Integer.valueOf(zenModeConfig.user));
                }
                if (zenRule.configurationActivity != null) {
                    return new android.util.Pair<>(zenRule.configurationActivity.getPackageName(), java.lang.Integer.valueOf(zenModeConfig.user));
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String getNewManualRuleEnabler() {
            if (this.mNewConfig == null || this.mNewConfig.manualRule == null) {
                return null;
            }
            return this.mNewConfig.manualRule.enabler;
        }

        protected com.android.server.notification.ZenModeEventLogger.ZenStateChanges copy() {
            com.android.server.notification.ZenModeEventLogger.ZenStateChanges zenStateChanges = new com.android.server.notification.ZenModeEventLogger.ZenStateChanges();
            zenStateChanges.mPrevZenMode = this.mPrevZenMode;
            zenStateChanges.mNewZenMode = this.mNewZenMode;
            zenStateChanges.mPrevConfig = this.mPrevConfig.copy();
            zenStateChanges.mNewConfig = this.mNewConfig.copy();
            zenStateChanges.mPrevPolicy = this.mPrevPolicy.copy();
            zenStateChanges.mNewPolicy = this.mNewPolicy.copy();
            zenStateChanges.mCallingUid = this.mCallingUid;
            zenStateChanges.mOrigin = this.mOrigin;
            return zenStateChanges;
        }
    }
}
