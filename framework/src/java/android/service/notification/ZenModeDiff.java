package android.service.notification;

/* loaded from: classes3.dex */
public class ZenModeDiff {
    public static final int ADDED = 1;
    public static final int NONE = 0;
    public static final int REMOVED = 2;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ExistenceChange {
    }

    public static class FieldDiff<T> {
        private final T mFrom;
        private final T mTo;

        public FieldDiff(T t, T t2) {
            this.mFrom = t;
            this.mTo = t2;
        }

        public T from() {
            return this.mFrom;
        }

        public T to() {
            return this.mTo;
        }

        public java.lang.String toString() {
            return this.mFrom + android.telecom.Logging.Session.SUBSESSION_SEPARATION_CHAR + this.mTo;
        }

        public boolean hasDiff() {
            return !java.util.Objects.equals(this.mFrom, this.mTo);
        }
    }

    private static abstract class BaseDiff {
        private int mExists;
        private android.util.ArrayMap<java.lang.String, android.service.notification.ZenModeDiff.FieldDiff> mFields = new android.util.ArrayMap<>();

        public abstract boolean hasDiff();

        public abstract java.lang.String toString();

        BaseDiff(java.lang.Object obj, java.lang.Object obj2) {
            this.mExists = 0;
            if (obj == null) {
                if (obj2 != null) {
                    this.mExists = 1;
                }
            } else if (obj2 == null) {
                this.mExists = 2;
            }
        }

        final void addField(java.lang.String str, android.service.notification.ZenModeDiff.FieldDiff fieldDiff) {
            this.mFields.put(str, fieldDiff);
        }

        public final boolean wasAdded() {
            return this.mExists == 1;
        }

        public final boolean wasRemoved() {
            return this.mExists == 2;
        }

        public final boolean hasExistenceChange() {
            return this.mExists != 0;
        }

        public final boolean hasFieldDiffs() {
            return this.mFields.size() > 0;
        }

        public final android.service.notification.ZenModeDiff.FieldDiff getDiffForField(java.lang.String str) {
            return this.mFields.getOrDefault(str, null);
        }

        public final java.util.Set<java.lang.String> fieldNamesWithDiff() {
            return this.mFields.keySet();
        }
    }

    public static class ConfigDiff extends android.service.notification.ZenModeDiff.BaseDiff {
        public static final java.lang.String FIELD_ALLOW_ALARMS = "allowAlarms";
        public static final java.lang.String FIELD_ALLOW_CALLS = "allowCalls";
        public static final java.lang.String FIELD_ALLOW_CONVERSATIONS = "allowConversations";
        public static final java.lang.String FIELD_ALLOW_CONVERSATIONS_FROM = "allowConversationsFrom";
        public static final java.lang.String FIELD_ALLOW_EVENTS = "allowEvents";
        public static final java.lang.String FIELD_ALLOW_MEDIA = "allowMedia";
        public static final java.lang.String FIELD_ALLOW_MESSAGES = "allowMessages";
        public static final java.lang.String FIELD_ALLOW_PRIORITY_CHANNELS = "allowPriorityChannels";
        public static final java.lang.String FIELD_ALLOW_REMINDERS = "allowReminders";
        public static final java.lang.String FIELD_ALLOW_REPEAT_CALLERS = "allowRepeatCallers";
        public static final java.lang.String FIELD_ALLOW_SYSTEM = "allowSystem";
        public static final java.lang.String FIELD_ARE_CHANNELS_BYPASSING_DND = "areChannelsBypassingDnd";
        public static final java.lang.String FIELD_SUPPRESSED_VISUAL_EFFECTS = "suppressedVisualEffects";
        public static final java.lang.String FIELD_USER = "user";
        private final android.util.ArrayMap<java.lang.String, android.service.notification.ZenModeDiff.RuleDiff> mAutomaticRulesDiff;
        private android.service.notification.ZenModeDiff.RuleDiff mManualRuleDiff;
        public static final java.lang.String FIELD_ALLOW_CALLS_FROM = "allowCallsFrom";
        public static final java.lang.String FIELD_ALLOW_MESSAGES_FROM = "allowMessagesFrom";
        private static final java.util.Set<java.lang.String> PEOPLE_TYPE_FIELDS = java.util.Set.of(FIELD_ALLOW_CALLS_FROM, FIELD_ALLOW_MESSAGES_FROM);

        public ConfigDiff(android.service.notification.ZenModeConfig zenModeConfig, android.service.notification.ZenModeConfig zenModeConfig2) {
            super(zenModeConfig, zenModeConfig2);
            this.mAutomaticRulesDiff = new android.util.ArrayMap<>();
            if ((zenModeConfig == null && zenModeConfig2 == null) || hasExistenceChange()) {
                return;
            }
            if (zenModeConfig.user != zenModeConfig2.user) {
                addField("user", new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Integer.valueOf(zenModeConfig.user), java.lang.Integer.valueOf(zenModeConfig2.user)));
            }
            if (zenModeConfig.allowAlarms != zenModeConfig2.allowAlarms) {
                addField(FIELD_ALLOW_ALARMS, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenModeConfig.allowAlarms), java.lang.Boolean.valueOf(zenModeConfig2.allowAlarms)));
            }
            if (zenModeConfig.allowMedia != zenModeConfig2.allowMedia) {
                addField(FIELD_ALLOW_MEDIA, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenModeConfig.allowMedia), java.lang.Boolean.valueOf(zenModeConfig2.allowMedia)));
            }
            if (zenModeConfig.allowSystem != zenModeConfig2.allowSystem) {
                addField(FIELD_ALLOW_SYSTEM, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenModeConfig.allowSystem), java.lang.Boolean.valueOf(zenModeConfig2.allowSystem)));
            }
            if (zenModeConfig.allowCalls != zenModeConfig2.allowCalls) {
                addField(FIELD_ALLOW_CALLS, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenModeConfig.allowCalls), java.lang.Boolean.valueOf(zenModeConfig2.allowCalls)));
            }
            if (zenModeConfig.allowReminders != zenModeConfig2.allowReminders) {
                addField(FIELD_ALLOW_REMINDERS, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenModeConfig.allowReminders), java.lang.Boolean.valueOf(zenModeConfig2.allowReminders)));
            }
            if (zenModeConfig.allowEvents != zenModeConfig2.allowEvents) {
                addField(FIELD_ALLOW_EVENTS, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenModeConfig.allowEvents), java.lang.Boolean.valueOf(zenModeConfig2.allowEvents)));
            }
            if (zenModeConfig.allowRepeatCallers != zenModeConfig2.allowRepeatCallers) {
                addField(FIELD_ALLOW_REPEAT_CALLERS, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenModeConfig.allowRepeatCallers), java.lang.Boolean.valueOf(zenModeConfig2.allowRepeatCallers)));
            }
            if (zenModeConfig.allowMessages != zenModeConfig2.allowMessages) {
                addField(FIELD_ALLOW_MESSAGES, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenModeConfig.allowMessages), java.lang.Boolean.valueOf(zenModeConfig2.allowMessages)));
            }
            if (zenModeConfig.allowConversations != zenModeConfig2.allowConversations) {
                addField(FIELD_ALLOW_CONVERSATIONS, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenModeConfig.allowConversations), java.lang.Boolean.valueOf(zenModeConfig2.allowConversations)));
            }
            if (zenModeConfig.allowCallsFrom != zenModeConfig2.allowCallsFrom) {
                addField(FIELD_ALLOW_CALLS_FROM, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Integer.valueOf(zenModeConfig.allowCallsFrom), java.lang.Integer.valueOf(zenModeConfig2.allowCallsFrom)));
            }
            if (zenModeConfig.allowMessagesFrom != zenModeConfig2.allowMessagesFrom) {
                addField(FIELD_ALLOW_MESSAGES_FROM, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Integer.valueOf(zenModeConfig.allowMessagesFrom), java.lang.Integer.valueOf(zenModeConfig2.allowMessagesFrom)));
            }
            if (zenModeConfig.allowConversationsFrom != zenModeConfig2.allowConversationsFrom) {
                addField(FIELD_ALLOW_CONVERSATIONS_FROM, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Integer.valueOf(zenModeConfig.allowConversationsFrom), java.lang.Integer.valueOf(zenModeConfig2.allowConversationsFrom)));
            }
            if (zenModeConfig.suppressedVisualEffects != zenModeConfig2.suppressedVisualEffects) {
                addField(FIELD_SUPPRESSED_VISUAL_EFFECTS, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Integer.valueOf(zenModeConfig.suppressedVisualEffects), java.lang.Integer.valueOf(zenModeConfig2.suppressedVisualEffects)));
            }
            if (zenModeConfig.areChannelsBypassingDnd != zenModeConfig2.areChannelsBypassingDnd) {
                addField(FIELD_ARE_CHANNELS_BYPASSING_DND, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenModeConfig.areChannelsBypassingDnd), java.lang.Boolean.valueOf(zenModeConfig2.areChannelsBypassingDnd)));
            }
            if (android.app.Flags.modesApi() && zenModeConfig.allowPriorityChannels != zenModeConfig2.allowPriorityChannels) {
                addField(FIELD_ALLOW_PRIORITY_CHANNELS, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenModeConfig.allowPriorityChannels), java.lang.Boolean.valueOf(zenModeConfig2.allowPriorityChannels)));
            }
            android.util.ArraySet arraySet = new android.util.ArraySet();
            addKeys(arraySet, zenModeConfig.automaticRules);
            addKeys(arraySet, zenModeConfig2.automaticRules);
            int size = arraySet.size();
            for (int i = 0; i < size; i++) {
                java.lang.String str = (java.lang.String) arraySet.valueAt(i);
                android.service.notification.ZenModeDiff.RuleDiff ruleDiff = new android.service.notification.ZenModeDiff.RuleDiff(zenModeConfig.automaticRules != null ? zenModeConfig.automaticRules.get(str) : null, zenModeConfig2.automaticRules != null ? zenModeConfig2.automaticRules.get(str) : null);
                if (ruleDiff.hasDiff()) {
                    this.mAutomaticRulesDiff.put(str, ruleDiff);
                }
            }
            android.service.notification.ZenModeDiff.RuleDiff ruleDiff2 = new android.service.notification.ZenModeDiff.RuleDiff(zenModeConfig.manualRule, zenModeConfig2.manualRule);
            if (ruleDiff2.hasDiff()) {
                this.mManualRuleDiff = ruleDiff2;
            }
        }

        private static <T> void addKeys(android.util.ArraySet<T> arraySet, android.util.ArrayMap<T, ?> arrayMap) {
            if (arrayMap != null) {
                for (int i = 0; i < arrayMap.size(); i++) {
                    arraySet.add(arrayMap.keyAt(i));
                }
            }
        }

        @Override // android.service.notification.ZenModeDiff.BaseDiff
        public boolean hasDiff() {
            return hasExistenceChange() || hasFieldDiffs() || this.mManualRuleDiff != null || this.mAutomaticRulesDiff.size() > 0;
        }

        @Override // android.service.notification.ZenModeDiff.BaseDiff
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("Diff[");
            if (!hasDiff()) {
                sb.append("no changes");
            }
            if (hasExistenceChange()) {
                if (wasAdded()) {
                    sb.append("added");
                } else if (wasRemoved()) {
                    sb.append(android.os.Environment.MEDIA_REMOVED);
                }
            }
            boolean z = true;
            for (java.lang.String str : fieldNamesWithDiff()) {
                android.service.notification.ZenModeDiff.FieldDiff diffForField = getDiffForField(str);
                if (diffForField != null) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(",\n");
                    }
                    if (PEOPLE_TYPE_FIELDS.contains(str)) {
                        sb.append(str);
                        sb.append(":");
                        sb.append(android.service.notification.ZenModeConfig.sourceToString(((java.lang.Integer) diffForField.from()).intValue()));
                        sb.append(android.telecom.Logging.Session.SUBSESSION_SEPARATION_CHAR);
                        sb.append(android.service.notification.ZenModeConfig.sourceToString(((java.lang.Integer) diffForField.to()).intValue()));
                    } else if (str.equals(FIELD_ALLOW_CONVERSATIONS_FROM)) {
                        sb.append(str);
                        sb.append(":");
                        sb.append(android.service.notification.ZenPolicy.conversationTypeToString(((java.lang.Integer) diffForField.from()).intValue()));
                        sb.append(android.telecom.Logging.Session.SUBSESSION_SEPARATION_CHAR);
                        sb.append(android.service.notification.ZenPolicy.conversationTypeToString(((java.lang.Integer) diffForField.to()).intValue()));
                    } else {
                        sb.append(str);
                        sb.append(":");
                        sb.append(diffForField);
                    }
                }
            }
            if (this.mManualRuleDiff != null && this.mManualRuleDiff.hasDiff()) {
                if (z) {
                    z = false;
                } else {
                    sb.append(",\n");
                }
                sb.append("manualRule:");
                sb.append(this.mManualRuleDiff);
            }
            for (java.lang.String str2 : this.mAutomaticRulesDiff.keySet()) {
                android.service.notification.ZenModeDiff.RuleDiff ruleDiff = this.mAutomaticRulesDiff.get(str2);
                if (ruleDiff != null && ruleDiff.hasDiff()) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(",\n");
                    }
                    sb.append("automaticRule[");
                    sb.append(str2);
                    sb.append("]:");
                    sb.append(ruleDiff);
                }
            }
            return sb.append(']').toString();
        }

        public android.service.notification.ZenModeDiff.RuleDiff getManualRuleDiff() {
            return this.mManualRuleDiff;
        }

        public android.util.ArrayMap<java.lang.String, android.service.notification.ZenModeDiff.RuleDiff> getAllAutomaticRuleDiffs() {
            if (this.mAutomaticRulesDiff.size() > 0) {
                return this.mAutomaticRulesDiff;
            }
            return null;
        }
    }

    public static class RuleDiff extends android.service.notification.ZenModeDiff.BaseDiff {
        public static final java.lang.String FIELD_ALLOW_MANUAL = "allowManualInvocation";
        public static final java.lang.String FIELD_COMPONENT = "component";
        public static final java.lang.String FIELD_CONDITION = "condition";
        public static final java.lang.String FIELD_CONDITION_ID = "conditionId";
        public static final java.lang.String FIELD_CONFIGURATION_ACTIVITY = "configurationActivity";
        public static final java.lang.String FIELD_CREATION_TIME = "creationTime";
        public static final java.lang.String FIELD_ENABLED = "enabled";
        public static final java.lang.String FIELD_ENABLER = "enabler";
        public static final java.lang.String FIELD_ICON_RES = "iconResName";
        public static final java.lang.String FIELD_ID = "id";
        public static final java.lang.String FIELD_MODIFIED = "modified";
        public static final java.lang.String FIELD_NAME = "name";
        public static final java.lang.String FIELD_PKG = "pkg";
        public static final java.lang.String FIELD_SNOOZING = "snoozing";
        public static final java.lang.String FIELD_TRIGGER_DESCRIPTION = "triggerDescription";
        public static final java.lang.String FIELD_TYPE = "type";
        public static final java.lang.String FIELD_ZEN_DEVICE_EFFECTS = "zenDeviceEffects";
        public static final java.lang.String FIELD_ZEN_MODE = "zenMode";
        public static final java.lang.String FIELD_ZEN_POLICY = "zenPolicy";
        android.service.notification.ZenModeDiff.FieldDiff<java.lang.Boolean> mActiveDiff;

        public RuleDiff(android.service.notification.ZenModeConfig.ZenRule zenRule, android.service.notification.ZenModeConfig.ZenRule zenRule2) {
            super(zenRule, zenRule2);
            if (zenRule == null && zenRule2 == null) {
                return;
            }
            boolean isAutomaticActive = zenRule != null ? zenRule.isAutomaticActive() : false;
            boolean isAutomaticActive2 = zenRule2 != null ? zenRule2.isAutomaticActive() : false;
            if (isAutomaticActive != isAutomaticActive2) {
                this.mActiveDiff = new android.service.notification.ZenModeDiff.FieldDiff<>(java.lang.Boolean.valueOf(isAutomaticActive), java.lang.Boolean.valueOf(isAutomaticActive2));
            }
            if (hasExistenceChange()) {
                return;
            }
            if (zenRule.enabled != zenRule2.enabled) {
                addField("enabled", new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenRule.enabled), java.lang.Boolean.valueOf(zenRule2.enabled)));
            }
            if (zenRule.snoozing != zenRule2.snoozing) {
                addField(FIELD_SNOOZING, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenRule.snoozing), java.lang.Boolean.valueOf(zenRule2.snoozing)));
            }
            if (!java.util.Objects.equals(zenRule.name, zenRule2.name)) {
                addField("name", new android.service.notification.ZenModeDiff.FieldDiff(zenRule.name, zenRule2.name));
            }
            if (zenRule.zenMode != zenRule2.zenMode) {
                addField(FIELD_ZEN_MODE, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Integer.valueOf(zenRule.zenMode), java.lang.Integer.valueOf(zenRule2.zenMode)));
            }
            if (!java.util.Objects.equals(zenRule.conditionId, zenRule2.conditionId)) {
                addField(FIELD_CONDITION_ID, new android.service.notification.ZenModeDiff.FieldDiff(zenRule.conditionId, zenRule2.conditionId));
            }
            if (!java.util.Objects.equals(zenRule.condition, zenRule2.condition)) {
                addField("condition", new android.service.notification.ZenModeDiff.FieldDiff(zenRule.condition, zenRule2.condition));
            }
            if (!java.util.Objects.equals(zenRule.component, zenRule2.component)) {
                addField(FIELD_COMPONENT, new android.service.notification.ZenModeDiff.FieldDiff(zenRule.component, zenRule2.component));
            }
            if (!java.util.Objects.equals(zenRule.configurationActivity, zenRule2.configurationActivity)) {
                addField(FIELD_CONFIGURATION_ACTIVITY, new android.service.notification.ZenModeDiff.FieldDiff(zenRule.configurationActivity, zenRule2.configurationActivity));
            }
            if (!java.util.Objects.equals(zenRule.id, zenRule2.id)) {
                addField("id", new android.service.notification.ZenModeDiff.FieldDiff(zenRule.id, zenRule2.id));
            }
            if (zenRule.creationTime != zenRule2.creationTime) {
                addField("creationTime", new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Long.valueOf(zenRule.creationTime), java.lang.Long.valueOf(zenRule2.creationTime)));
            }
            if (!java.util.Objects.equals(zenRule.enabler, zenRule2.enabler)) {
                addField(FIELD_ENABLER, new android.service.notification.ZenModeDiff.FieldDiff(zenRule.enabler, zenRule2.enabler));
            }
            if (!java.util.Objects.equals(zenRule.zenPolicy, zenRule2.zenPolicy)) {
                addField(FIELD_ZEN_POLICY, new android.service.notification.ZenModeDiff.FieldDiff(zenRule.zenPolicy, zenRule2.zenPolicy));
            }
            if (zenRule.modified != zenRule2.modified) {
                addField("modified", new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenRule.modified), java.lang.Boolean.valueOf(zenRule2.modified)));
            }
            if (!java.util.Objects.equals(zenRule.pkg, zenRule2.pkg)) {
                addField("pkg", new android.service.notification.ZenModeDiff.FieldDiff(zenRule.pkg, zenRule2.pkg));
            }
            if (android.app.Flags.modesApi()) {
                if (!java.util.Objects.equals(zenRule.zenDeviceEffects, zenRule2.zenDeviceEffects)) {
                    addField(FIELD_ZEN_DEVICE_EFFECTS, new android.service.notification.ZenModeDiff.FieldDiff(zenRule.zenDeviceEffects, zenRule2.zenDeviceEffects));
                }
                if (!java.util.Objects.equals(zenRule.triggerDescription, zenRule2.triggerDescription)) {
                    addField(FIELD_TRIGGER_DESCRIPTION, new android.service.notification.ZenModeDiff.FieldDiff(zenRule.triggerDescription, zenRule2.triggerDescription));
                }
                if (zenRule.type != zenRule2.type) {
                    addField("type", new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Integer.valueOf(zenRule.type), java.lang.Integer.valueOf(zenRule2.type)));
                }
                if (zenRule.allowManualInvocation != zenRule2.allowManualInvocation) {
                    addField(FIELD_ALLOW_MANUAL, new android.service.notification.ZenModeDiff.FieldDiff(java.lang.Boolean.valueOf(zenRule.allowManualInvocation), java.lang.Boolean.valueOf(zenRule2.allowManualInvocation)));
                }
                if (!java.util.Objects.equals(zenRule.iconResName, zenRule2.iconResName)) {
                    addField("iconResName", new android.service.notification.ZenModeDiff.FieldDiff(zenRule.iconResName, zenRule2.iconResName));
                }
            }
        }

        @Override // android.service.notification.ZenModeDiff.BaseDiff
        public boolean hasDiff() {
            return hasExistenceChange() || hasFieldDiffs();
        }

        @Override // android.service.notification.ZenModeDiff.BaseDiff
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("ZenRuleDiff{");
            if (!hasDiff()) {
                sb.append("no changes");
            }
            if (hasExistenceChange()) {
                if (wasAdded()) {
                    sb.append("added");
                } else if (wasRemoved()) {
                    sb.append(android.os.Environment.MEDIA_REMOVED);
                }
            }
            boolean z = true;
            for (java.lang.String str : fieldNamesWithDiff()) {
                android.service.notification.ZenModeDiff.FieldDiff diffForField = getDiffForField(str);
                if (diffForField != null) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(str);
                    sb.append(":");
                    sb.append(diffForField);
                }
            }
            if (becameActive()) {
                if (!z) {
                    sb.append(", ");
                }
                sb.append("(->active)");
            } else if (becameInactive()) {
                if (!z) {
                    sb.append(", ");
                }
                sb.append("(->inactive)");
            }
            return sb.append("}").toString();
        }

        public boolean becameActive() {
            return this.mActiveDiff != null && this.mActiveDiff.to().booleanValue();
        }

        public boolean becameInactive() {
            return (this.mActiveDiff == null || this.mActiveDiff.to().booleanValue()) ? false : true;
        }
    }
}
