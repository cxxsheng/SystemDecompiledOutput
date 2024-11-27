package android.service.notification;

/* loaded from: classes3.dex */
public class ZenModeConfig implements android.os.Parcelable {
    private static final java.lang.String ALLOW_ATT_ALARMS = "alarms";
    private static final java.lang.String ALLOW_ATT_CALLS = "calls";
    private static final java.lang.String ALLOW_ATT_CALLS_FROM = "callsFrom";
    private static final java.lang.String ALLOW_ATT_CHANNELS = "priorityChannelsAllowed";
    private static final java.lang.String ALLOW_ATT_CONV = "convos";
    private static final java.lang.String ALLOW_ATT_CONV_FROM = "convosFrom";
    private static final java.lang.String ALLOW_ATT_EVENTS = "events";
    private static final java.lang.String ALLOW_ATT_FROM = "from";
    private static final java.lang.String ALLOW_ATT_MEDIA = "media";
    private static final java.lang.String ALLOW_ATT_MESSAGES = "messages";
    private static final java.lang.String ALLOW_ATT_MESSAGES_FROM = "messagesFrom";
    private static final java.lang.String ALLOW_ATT_REMINDERS = "reminders";
    private static final java.lang.String ALLOW_ATT_REPEAT_CALLERS = "repeatCallers";
    private static final java.lang.String ALLOW_ATT_SCREEN_OFF = "visualScreenOff";
    private static final java.lang.String ALLOW_ATT_SCREEN_ON = "visualScreenOn";
    private static final java.lang.String ALLOW_ATT_SYSTEM = "system";
    private static final java.lang.String ALLOW_TAG = "allow";
    private static final java.lang.String AUTOMATIC_DELETED_TAG = "deleted";
    private static final java.lang.String AUTOMATIC_TAG = "automatic";
    private static final java.lang.String CONDITION_ATT_FLAGS = "flags";
    private static final java.lang.String CONDITION_ATT_ICON = "icon";
    private static final java.lang.String CONDITION_ATT_ID = "id";
    private static final java.lang.String CONDITION_ATT_LINE1 = "line1";
    private static final java.lang.String CONDITION_ATT_LINE2 = "line2";
    private static final java.lang.String CONDITION_ATT_SOURCE = "source";
    private static final java.lang.String CONDITION_ATT_STATE = "state";
    private static final java.lang.String CONDITION_ATT_SUMMARY = "summary";
    public static final java.lang.String COUNTDOWN_PATH = "countdown";
    private static final int DAY_MINUTES = 1440;
    private static final boolean DEFAULT_ALLOW_ALARMS = true;
    private static final boolean DEFAULT_ALLOW_CALLS = true;
    private static final boolean DEFAULT_ALLOW_CONV = true;
    private static final int DEFAULT_ALLOW_CONV_FROM = 2;
    private static final boolean DEFAULT_ALLOW_EVENTS = false;
    private static final boolean DEFAULT_ALLOW_MEDIA = true;
    private static final boolean DEFAULT_ALLOW_MESSAGES = true;
    private static final boolean DEFAULT_ALLOW_PRIORITY_CHANNELS = true;
    private static final boolean DEFAULT_ALLOW_REMINDERS = false;
    private static final boolean DEFAULT_ALLOW_REPEAT_CALLERS = true;
    private static final boolean DEFAULT_ALLOW_SYSTEM = false;
    private static final int DEFAULT_CALLS_SOURCE = 2;
    private static final boolean DEFAULT_CHANNELS_BYPASSING_DND = false;
    private static final int DEFAULT_SOURCE = 2;
    private static final int DEFAULT_SUPPRESSED_VISUAL_EFFECTS = 157;
    private static final java.lang.String DEVICE_EFFECT_DIM_WALLPAPER = "zdeDimWallpaper";
    private static final java.lang.String DEVICE_EFFECT_DISABLE_AUTO_BRIGHTNESS = "zdeDisableAutoBrightness";
    private static final java.lang.String DEVICE_EFFECT_DISABLE_TAP_TO_WAKE = "zdeDisableTapToWake";
    private static final java.lang.String DEVICE_EFFECT_DISABLE_TILT_TO_WAKE = "zdeDisableTiltToWake";
    private static final java.lang.String DEVICE_EFFECT_DISABLE_TOUCH = "zdeDisableTouch";
    private static final java.lang.String DEVICE_EFFECT_DISPLAY_GRAYSCALE = "zdeDisplayGrayscale";
    private static final java.lang.String DEVICE_EFFECT_EXTRAS = "zdeExtraEffects";
    private static final java.lang.String DEVICE_EFFECT_MAXIMIZE_DOZE = "zdeMaximizeDoze";
    private static final java.lang.String DEVICE_EFFECT_MINIMIZE_RADIO_USAGE = "zdeMinimizeRadioUsage";
    private static final java.lang.String DEVICE_EFFECT_SUPPRESS_AMBIENT_DISPLAY = "zdeSuppressAmbientDisplay";
    private static final java.lang.String DEVICE_EFFECT_USER_MODIFIED_FIELDS = "zdeUserModifiedFields";
    private static final java.lang.String DEVICE_EFFECT_USE_NIGHT_MODE = "zdeUseNightMode";
    private static final java.lang.String DISALLOW_ATT_VISUAL_EFFECTS = "visualEffects";
    private static final java.lang.String DISALLOW_TAG = "disallow";
    public static final java.lang.String EVENT_PATH = "event";
    public static final java.lang.String IS_ALARM_PATH = "alarm";
    private static final java.lang.String ITEM_SEPARATOR = ",";
    private static final java.lang.String ITEM_SEPARATOR_ESCAPE = "\\";
    public static final java.lang.String MANUAL_RULE_ID = "MANUAL_RULE";
    private static final java.lang.String MANUAL_TAG = "manual";
    public static final int MAX_SOURCE = 2;
    private static final int MINUTES_MS = 60000;
    private static final java.lang.String POLICY_USER_MODIFIED_FIELDS = "policyUserModifiedFields";
    private static final java.lang.String RULE_ATT_ALLOW_MANUAL = "userInvokable";
    private static final java.lang.String RULE_ATT_COMPONENT = "component";
    private static final java.lang.String RULE_ATT_CONDITION_ID = "conditionId";
    private static final java.lang.String RULE_ATT_CONFIG_ACTIVITY = "configActivity";
    private static final java.lang.String RULE_ATT_CREATION_TIME = "creationTime";
    private static final java.lang.String RULE_ATT_DELETION_INSTANT = "deletionInstant";
    private static final java.lang.String RULE_ATT_ENABLED = "enabled";
    private static final java.lang.String RULE_ATT_ENABLER = "enabler";
    private static final java.lang.String RULE_ATT_ICON = "rule_icon";
    private static final java.lang.String RULE_ATT_ID = "ruleId";
    private static final java.lang.String RULE_ATT_MODIFIED = "modified";
    private static final java.lang.String RULE_ATT_NAME = "name";
    private static final java.lang.String RULE_ATT_PKG = "pkg";
    private static final java.lang.String RULE_ATT_SNOOZING = "snoozing";
    private static final java.lang.String RULE_ATT_TRIGGER_DESC = "triggerDesc";
    private static final java.lang.String RULE_ATT_TYPE = "type";
    private static final java.lang.String RULE_ATT_USER_MODIFIED_FIELDS = "userModifiedFields";
    private static final java.lang.String RULE_ATT_ZEN = "zen";
    public static final java.lang.String SCHEDULE_PATH = "schedule";
    private static final int SECONDS_MS = 1000;
    private static final java.lang.String SHOW_ATT_AMBIENT = "showAmbient";
    private static final java.lang.String SHOW_ATT_BADGES = "showBadges";
    private static final java.lang.String SHOW_ATT_FULL_SCREEN_INTENT = "showFullScreenIntent";
    private static final java.lang.String SHOW_ATT_LIGHTS = "showLights";
    private static final java.lang.String SHOW_ATT_NOTIFICATION_LIST = "showNotificationList";
    private static final java.lang.String SHOW_ATT_PEEK = "shoePeek";
    private static final java.lang.String SHOW_ATT_STATUS_BAR_ICONS = "showStatusBarIcons";
    public static final int SOURCE_ANYONE = 0;
    public static final int SOURCE_CONTACT = 1;
    public static final int SOURCE_STAR = 2;
    private static final java.lang.String STATE_ATT_CHANNELS_BYPASSING_DND = "areChannelsBypassingDnd";
    private static final java.lang.String STATE_TAG = "state";
    public static final java.lang.String SYSTEM_AUTHORITY = "android";
    private static final java.lang.String TAG = "ZenModeConfig";
    public static final int UPDATE_ORIGIN_APP = 4;
    public static final int UPDATE_ORIGIN_INIT = 1;
    public static final int UPDATE_ORIGIN_INIT_USER = 2;
    public static final int UPDATE_ORIGIN_RESTORE_BACKUP = 6;
    public static final int UPDATE_ORIGIN_SYSTEM_OR_SYSTEMUI = 5;
    public static final int UPDATE_ORIGIN_UNKNOWN = 0;
    public static final int UPDATE_ORIGIN_USER = 3;
    private static final int XML_VERSION = 10;
    public static final int XML_VERSION_MODES_API = 11;
    public static final int XML_VERSION_ZEN_UPGRADE = 8;
    private static final java.lang.String ZEN_ATT_USER = "user";
    private static final java.lang.String ZEN_ATT_VERSION = "version";
    private static final java.lang.String ZEN_POLICY_TAG = "zen_policy";
    public static final java.lang.String ZEN_TAG = "zen";
    private static final int ZERO_VALUE_MS = 10000;
    public boolean allowAlarms;
    public boolean allowCalls;
    public int allowCallsFrom;
    public boolean allowConversations;
    public int allowConversationsFrom;
    public boolean allowEvents;
    public boolean allowMedia;
    public boolean allowMessages;
    public int allowMessagesFrom;
    public boolean allowPriorityChannels;
    public boolean allowReminders;
    public boolean allowRepeatCallers;
    public boolean allowSystem;
    public boolean areChannelsBypassingDnd;
    public android.util.ArrayMap<java.lang.String, android.service.notification.ZenModeConfig.ZenRule> automaticRules;
    public final android.util.ArrayMap<java.lang.String, android.service.notification.ZenModeConfig.ZenRule> deletedRules;
    public android.service.notification.ZenModeConfig.ZenRule manualRule;
    public int suppressedVisualEffects;
    public int user;
    public int version;
    public static final java.lang.String EVERY_NIGHT_DEFAULT_RULE_ID = "EVERY_NIGHT_DEFAULT_RULE";
    public static final java.lang.String EVENTS_DEFAULT_RULE_ID = "EVENTS_DEFAULT_RULE";
    public static final java.util.List<java.lang.String> DEFAULT_RULE_IDS = java.util.Arrays.asList(EVERY_NIGHT_DEFAULT_RULE_ID, EVENTS_DEFAULT_RULE_ID);
    public static final int[] ALL_DAYS = {1, 2, 3, 4, 5, 6, 7};
    public static final int[] MINUTE_BUCKETS = generateMinuteBuckets();
    private static final java.util.regex.Pattern ITEM_SPLITTER_REGEX = java.util.regex.Pattern.compile("(?<!\\\\),");
    public static final android.os.Parcelable.Creator<android.service.notification.ZenModeConfig> CREATOR = new android.os.Parcelable.Creator<android.service.notification.ZenModeConfig>() { // from class: android.service.notification.ZenModeConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.ZenModeConfig createFromParcel(android.os.Parcel parcel) {
            return new android.service.notification.ZenModeConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.ZenModeConfig[] newArray(int i) {
            return new android.service.notification.ZenModeConfig[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConfigChangeOrigin {
    }

    public ZenModeConfig() {
        this.allowAlarms = true;
        this.allowMedia = true;
        this.allowSystem = false;
        this.allowCalls = true;
        this.allowRepeatCallers = true;
        this.allowMessages = true;
        this.allowReminders = false;
        this.allowEvents = false;
        this.allowCallsFrom = 2;
        this.allowMessagesFrom = 2;
        this.allowConversations = true;
        this.allowConversationsFrom = 2;
        this.user = 0;
        this.suppressedVisualEffects = 157;
        this.areChannelsBypassingDnd = false;
        this.allowPriorityChannels = true;
        this.automaticRules = new android.util.ArrayMap<>();
        this.deletedRules = new android.util.ArrayMap<>();
    }

    public ZenModeConfig(android.os.Parcel parcel) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        this.allowAlarms = true;
        this.allowMedia = true;
        this.allowSystem = false;
        this.allowCalls = true;
        this.allowRepeatCallers = true;
        this.allowMessages = true;
        this.allowReminders = false;
        this.allowEvents = false;
        this.allowCallsFrom = 2;
        this.allowMessagesFrom = 2;
        this.allowConversations = true;
        this.allowConversationsFrom = 2;
        this.user = 0;
        this.suppressedVisualEffects = 157;
        this.areChannelsBypassingDnd = false;
        this.allowPriorityChannels = true;
        this.automaticRules = new android.util.ArrayMap<>();
        this.deletedRules = new android.util.ArrayMap<>();
        if (parcel.readInt() == 1) {
            z = true;
        } else {
            z = false;
        }
        this.allowCalls = z;
        if (parcel.readInt() == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.allowRepeatCallers = z2;
        if (parcel.readInt() == 1) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.allowMessages = z3;
        if (parcel.readInt() == 1) {
            z4 = true;
        } else {
            z4 = false;
        }
        this.allowReminders = z4;
        if (parcel.readInt() == 1) {
            z5 = true;
        } else {
            z5 = false;
        }
        this.allowEvents = z5;
        this.allowCallsFrom = parcel.readInt();
        this.allowMessagesFrom = parcel.readInt();
        this.user = parcel.readInt();
        this.manualRule = (android.service.notification.ZenModeConfig.ZenRule) parcel.readParcelable(null, android.service.notification.ZenModeConfig.ZenRule.class);
        readRulesFromParcel(this.automaticRules, parcel);
        if (android.app.Flags.modesApi()) {
            readRulesFromParcel(this.deletedRules, parcel);
        }
        if (parcel.readInt() == 1) {
            z6 = true;
        } else {
            z6 = false;
        }
        this.allowAlarms = z6;
        if (parcel.readInt() == 1) {
            z7 = true;
        } else {
            z7 = false;
        }
        this.allowMedia = z7;
        if (parcel.readInt() == 1) {
            z8 = true;
        } else {
            z8 = false;
        }
        this.allowSystem = z8;
        this.suppressedVisualEffects = parcel.readInt();
        this.areChannelsBypassingDnd = parcel.readInt() == 1;
        this.allowConversations = parcel.readBoolean();
        this.allowConversationsFrom = parcel.readInt();
        if (android.app.Flags.modesApi()) {
            this.allowPriorityChannels = parcel.readBoolean();
        }
    }

    private static void readRulesFromParcel(android.util.ArrayMap<java.lang.String, android.service.notification.ZenModeConfig.ZenRule> arrayMap, android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt > 0) {
            java.lang.String[] strArr = new java.lang.String[readInt];
            android.service.notification.ZenModeConfig.ZenRule[] zenRuleArr = new android.service.notification.ZenModeConfig.ZenRule[readInt];
            parcel.readStringArray(strArr);
            parcel.readTypedArray(zenRuleArr, android.service.notification.ZenModeConfig.ZenRule.CREATOR);
            for (int i = 0; i < readInt; i++) {
                arrayMap.put(strArr[i], zenRuleArr[i]);
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.allowCalls ? 1 : 0);
        parcel.writeInt(this.allowRepeatCallers ? 1 : 0);
        parcel.writeInt(this.allowMessages ? 1 : 0);
        parcel.writeInt(this.allowReminders ? 1 : 0);
        parcel.writeInt(this.allowEvents ? 1 : 0);
        parcel.writeInt(this.allowCallsFrom);
        parcel.writeInt(this.allowMessagesFrom);
        parcel.writeInt(this.user);
        parcel.writeParcelable(this.manualRule, 0);
        writeRulesToParcel(this.automaticRules, parcel);
        if (android.app.Flags.modesApi()) {
            writeRulesToParcel(this.deletedRules, parcel);
        }
        parcel.writeInt(this.allowAlarms ? 1 : 0);
        parcel.writeInt(this.allowMedia ? 1 : 0);
        parcel.writeInt(this.allowSystem ? 1 : 0);
        parcel.writeInt(this.suppressedVisualEffects);
        parcel.writeInt(this.areChannelsBypassingDnd ? 1 : 0);
        parcel.writeBoolean(this.allowConversations);
        parcel.writeInt(this.allowConversationsFrom);
        if (android.app.Flags.modesApi()) {
            parcel.writeBoolean(this.allowPriorityChannels);
        }
    }

    private static void writeRulesToParcel(android.util.ArrayMap<java.lang.String, android.service.notification.ZenModeConfig.ZenRule> arrayMap, android.os.Parcel parcel) {
        if (!arrayMap.isEmpty()) {
            int size = arrayMap.size();
            java.lang.String[] strArr = new java.lang.String[size];
            android.service.notification.ZenModeConfig.ZenRule[] zenRuleArr = new android.service.notification.ZenModeConfig.ZenRule[size];
            for (int i = 0; i < size; i++) {
                strArr[i] = arrayMap.keyAt(i);
                zenRuleArr[i] = arrayMap.valueAt(i);
            }
            parcel.writeInt(size);
            parcel.writeStringArray(strArr);
            parcel.writeTypedArray(zenRuleArr, 0);
            return;
        }
        parcel.writeInt(0);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder(android.service.notification.ZenModeConfig.class.getSimpleName()).append('[').append("user=").append(this.user).append(",allowAlarms=").append(this.allowAlarms).append(",allowMedia=").append(this.allowMedia).append(",allowSystem=").append(this.allowSystem).append(",allowReminders=").append(this.allowReminders).append(",allowEvents=").append(this.allowEvents).append(",allowCalls=").append(this.allowCalls).append(",allowRepeatCallers=").append(this.allowRepeatCallers).append(",allowMessages=").append(this.allowMessages).append(",allowConversations=").append(this.allowConversations).append(",allowCallsFrom=").append(sourceToString(this.allowCallsFrom)).append(",allowMessagesFrom=").append(sourceToString(this.allowMessagesFrom)).append(",allowConvFrom=").append(android.service.notification.ZenPolicy.conversationTypeToString(this.allowConversationsFrom)).append(",suppressedVisualEffects=").append(this.suppressedVisualEffects);
        if (android.app.Flags.modesApi()) {
            append.append(",hasPriorityChannels=").append(this.areChannelsBypassingDnd);
            append.append(",allowPriorityChannels=").append(this.allowPriorityChannels);
        } else {
            append.append(",areChannelsBypassingDnd=").append(this.areChannelsBypassingDnd);
        }
        append.append(",\nautomaticRules=").append(rulesToString(this.automaticRules)).append(",\nmanualRule=").append(this.manualRule);
        if (android.app.Flags.modesApi()) {
            append.append(",\ndeletedRules=").append(rulesToString(this.deletedRules));
        }
        return append.append(']').toString();
    }

    private static java.lang.String rulesToString(android.util.ArrayMap<java.lang.String, android.service.notification.ZenModeConfig.ZenRule> arrayMap) {
        if (arrayMap.isEmpty()) {
            return "{}";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(arrayMap.size() * 28);
        sb.append("{\n");
        for (int i = 0; i < arrayMap.size(); i++) {
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append(arrayMap.valueAt(i));
        }
        sb.append('}');
        return sb.toString();
    }

    public boolean isValid() {
        if (!isValidManualRule(this.manualRule)) {
            return false;
        }
        int size = this.automaticRules.size();
        for (int i = 0; i < size; i++) {
            if (!isValidAutomaticRule(this.automaticRules.valueAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidManualRule(android.service.notification.ZenModeConfig.ZenRule zenRule) {
        return zenRule == null || (android.provider.Settings.Global.isValidZenMode(zenRule.zenMode) && sameCondition(zenRule));
    }

    private static boolean isValidAutomaticRule(android.service.notification.ZenModeConfig.ZenRule zenRule) {
        return (zenRule == null || android.text.TextUtils.isEmpty(zenRule.name) || !android.provider.Settings.Global.isValidZenMode(zenRule.zenMode) || zenRule.conditionId == null || !sameCondition(zenRule)) ? false : true;
    }

    private static boolean sameCondition(android.service.notification.ZenModeConfig.ZenRule zenRule) {
        if (zenRule == null) {
            return false;
        }
        return zenRule.conditionId == null ? zenRule.condition == null : zenRule.condition == null || zenRule.conditionId.equals(zenRule.condition.id);
    }

    private static int[] generateMinuteBuckets() {
        int[] iArr = new int[15];
        iArr[0] = 15;
        iArr[1] = 30;
        iArr[2] = 45;
        for (int i = 1; i <= 12; i++) {
            iArr[i + 2] = i * 60;
        }
        return iArr;
    }

    public static java.lang.String sourceToString(int i) {
        switch (i) {
            case 0:
                return "anyone";
            case 1:
                return android.provider.Contacts.AUTHORITY;
            case 2:
                return "stars";
            default:
                return "UNKNOWN";
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.service.notification.ZenModeConfig)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        android.service.notification.ZenModeConfig zenModeConfig = (android.service.notification.ZenModeConfig) obj;
        boolean z = zenModeConfig.allowAlarms == this.allowAlarms && zenModeConfig.allowMedia == this.allowMedia && zenModeConfig.allowSystem == this.allowSystem && zenModeConfig.allowCalls == this.allowCalls && zenModeConfig.allowRepeatCallers == this.allowRepeatCallers && zenModeConfig.allowMessages == this.allowMessages && zenModeConfig.allowCallsFrom == this.allowCallsFrom && zenModeConfig.allowMessagesFrom == this.allowMessagesFrom && zenModeConfig.allowReminders == this.allowReminders && zenModeConfig.allowEvents == this.allowEvents && zenModeConfig.user == this.user && java.util.Objects.equals(zenModeConfig.automaticRules, this.automaticRules) && java.util.Objects.equals(zenModeConfig.manualRule, this.manualRule) && zenModeConfig.suppressedVisualEffects == this.suppressedVisualEffects && zenModeConfig.areChannelsBypassingDnd == this.areChannelsBypassingDnd && zenModeConfig.allowConversations == this.allowConversations && zenModeConfig.allowConversationsFrom == this.allowConversationsFrom;
        if (android.app.Flags.modesApi()) {
            return z && java.util.Objects.equals(zenModeConfig.deletedRules, this.deletedRules) && zenModeConfig.allowPriorityChannels == this.allowPriorityChannels;
        }
        return z;
    }

    public int hashCode() {
        if (android.app.Flags.modesApi()) {
            return java.util.Objects.hash(java.lang.Boolean.valueOf(this.allowAlarms), java.lang.Boolean.valueOf(this.allowMedia), java.lang.Boolean.valueOf(this.allowSystem), java.lang.Boolean.valueOf(this.allowCalls), java.lang.Boolean.valueOf(this.allowRepeatCallers), java.lang.Boolean.valueOf(this.allowMessages), java.lang.Integer.valueOf(this.allowCallsFrom), java.lang.Integer.valueOf(this.allowMessagesFrom), java.lang.Boolean.valueOf(this.allowReminders), java.lang.Boolean.valueOf(this.allowEvents), java.lang.Integer.valueOf(this.user), this.automaticRules, this.manualRule, java.lang.Integer.valueOf(this.suppressedVisualEffects), java.lang.Boolean.valueOf(this.areChannelsBypassingDnd), java.lang.Boolean.valueOf(this.allowConversations), java.lang.Integer.valueOf(this.allowConversationsFrom), java.lang.Boolean.valueOf(this.allowPriorityChannels));
        }
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.allowAlarms), java.lang.Boolean.valueOf(this.allowMedia), java.lang.Boolean.valueOf(this.allowSystem), java.lang.Boolean.valueOf(this.allowCalls), java.lang.Boolean.valueOf(this.allowRepeatCallers), java.lang.Boolean.valueOf(this.allowMessages), java.lang.Integer.valueOf(this.allowCallsFrom), java.lang.Integer.valueOf(this.allowMessagesFrom), java.lang.Boolean.valueOf(this.allowReminders), java.lang.Boolean.valueOf(this.allowEvents), java.lang.Integer.valueOf(this.user), this.automaticRules, this.manualRule, java.lang.Integer.valueOf(this.suppressedVisualEffects), java.lang.Boolean.valueOf(this.areChannelsBypassingDnd), java.lang.Boolean.valueOf(this.allowConversations), java.lang.Integer.valueOf(this.allowConversationsFrom));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String toDayList(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < iArr.length; i++) {
            if (i > 0) {
                sb.append('.');
            }
            sb.append(iArr[i]);
        }
        return sb.toString();
    }

    private static int[] tryParseDayList(java.lang.String str, java.lang.String str2) {
        if (str == null) {
            return null;
        }
        java.lang.String[] split = str.split(str2);
        if (split.length == 0) {
            return null;
        }
        int[] iArr = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            int tryParseInt = tryParseInt(split[i], -1);
            if (tryParseInt == -1) {
                return null;
            }
            iArr[i] = tryParseInt;
        }
        return iArr;
    }

    private static int tryParseInt(java.lang.String str, int i) {
        if (android.text.TextUtils.isEmpty(str)) {
            return i;
        }
        try {
            return java.lang.Integer.parseInt(str);
        } catch (java.lang.NumberFormatException e) {
            return i;
        }
    }

    private static long tryParseLong(java.lang.String str, long j) {
        if (android.text.TextUtils.isEmpty(str)) {
            return j;
        }
        try {
            return java.lang.Long.parseLong(str);
        } catch (java.lang.NumberFormatException e) {
            return j;
        }
    }

    private static java.lang.Long tryParseLong(java.lang.String str, java.lang.Long l) {
        if (android.text.TextUtils.isEmpty(str)) {
            return l;
        }
        try {
            return java.lang.Long.valueOf(java.lang.Long.parseLong(str));
        } catch (java.lang.NumberFormatException e) {
            return l;
        }
    }

    public static int getCurrentXmlVersion() {
        return android.app.Flags.modesApi() ? 11 : 10;
    }

    public static android.service.notification.ZenModeConfig readXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (typedXmlPullParser.getEventType() != 2 || !"zen".equals(typedXmlPullParser.getName())) {
            return null;
        }
        android.service.notification.ZenModeConfig zenModeConfig = new android.service.notification.ZenModeConfig();
        zenModeConfig.version = safeInt(typedXmlPullParser, "version", getCurrentXmlVersion());
        zenModeConfig.user = safeInt(typedXmlPullParser, "user", zenModeConfig.user);
        boolean z = false;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                java.lang.String name = typedXmlPullParser.getName();
                if (next == 3 && "zen".equals(name)) {
                    return zenModeConfig;
                }
                if (next == 2) {
                    if (ALLOW_TAG.equals(name)) {
                        zenModeConfig.allowCalls = safeBoolean(typedXmlPullParser, ALLOW_ATT_CALLS, true);
                        zenModeConfig.allowRepeatCallers = safeBoolean(typedXmlPullParser, ALLOW_ATT_REPEAT_CALLERS, true);
                        zenModeConfig.allowMessages = safeBoolean(typedXmlPullParser, ALLOW_ATT_MESSAGES, true);
                        zenModeConfig.allowReminders = safeBoolean(typedXmlPullParser, ALLOW_ATT_REMINDERS, false);
                        zenModeConfig.allowConversations = safeBoolean(typedXmlPullParser, ALLOW_ATT_CONV, true);
                        zenModeConfig.allowEvents = safeBoolean(typedXmlPullParser, ALLOW_ATT_EVENTS, false);
                        int safeInt = safeInt(typedXmlPullParser, ALLOW_ATT_FROM, -1);
                        int safeInt2 = safeInt(typedXmlPullParser, ALLOW_ATT_CALLS_FROM, -1);
                        int safeInt3 = safeInt(typedXmlPullParser, ALLOW_ATT_MESSAGES_FROM, -1);
                        zenModeConfig.allowConversationsFrom = safeInt(typedXmlPullParser, ALLOW_ATT_CONV_FROM, 2);
                        if (isValidSource(safeInt2) && isValidSource(safeInt3)) {
                            zenModeConfig.allowCallsFrom = safeInt2;
                            zenModeConfig.allowMessagesFrom = safeInt3;
                        } else if (isValidSource(safeInt)) {
                            android.util.Slog.i(TAG, "Migrating existing shared 'from': " + sourceToString(safeInt));
                            zenModeConfig.allowCallsFrom = safeInt;
                            zenModeConfig.allowMessagesFrom = safeInt;
                        } else {
                            zenModeConfig.allowCallsFrom = 2;
                            zenModeConfig.allowMessagesFrom = 2;
                        }
                        zenModeConfig.allowAlarms = safeBoolean(typedXmlPullParser, ALLOW_ATT_ALARMS, true);
                        zenModeConfig.allowMedia = safeBoolean(typedXmlPullParser, ALLOW_ATT_MEDIA, true);
                        zenModeConfig.allowSystem = safeBoolean(typedXmlPullParser, "system", false);
                        if (android.app.Flags.modesApi()) {
                            zenModeConfig.allowPriorityChannels = safeBoolean(typedXmlPullParser, ALLOW_ATT_CHANNELS, true);
                        }
                        java.lang.Boolean unsafeBoolean = unsafeBoolean(typedXmlPullParser, ALLOW_ATT_SCREEN_OFF);
                        java.lang.Boolean unsafeBoolean2 = unsafeBoolean(typedXmlPullParser, ALLOW_ATT_SCREEN_ON);
                        if (unsafeBoolean != null || unsafeBoolean2 != null) {
                            zenModeConfig.suppressedVisualEffects = 0;
                            z = true;
                        }
                        if (unsafeBoolean != null && !unsafeBoolean.booleanValue()) {
                            zenModeConfig.suppressedVisualEffects |= 140;
                        }
                        if (unsafeBoolean2 != null && !unsafeBoolean2.booleanValue()) {
                            zenModeConfig.suppressedVisualEffects |= 16;
                        }
                        if (z) {
                            android.util.Slog.d(TAG, "Migrated visual effects to " + zenModeConfig.suppressedVisualEffects);
                        }
                    } else if (DISALLOW_TAG.equals(name) && !z) {
                        zenModeConfig.suppressedVisualEffects = safeInt(typedXmlPullParser, DISALLOW_ATT_VISUAL_EFFECTS, 157);
                    } else if ("manual".equals(name)) {
                        zenModeConfig.manualRule = readRuleXml(typedXmlPullParser);
                    } else if (AUTOMATIC_TAG.equals(name) || (android.app.Flags.modesApi() && "deleted".equals(name))) {
                        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(null, RULE_ATT_ID);
                        android.service.notification.ZenModeConfig.ZenRule readRuleXml = readRuleXml(typedXmlPullParser);
                        if (attributeValue != null && readRuleXml != null) {
                            readRuleXml.id = attributeValue;
                            if (android.app.Flags.modesApi() && "deleted".equals(name)) {
                                java.lang.String deletedRuleKey = deletedRuleKey(readRuleXml);
                                if (deletedRuleKey != null) {
                                    zenModeConfig.deletedRules.put(deletedRuleKey, readRuleXml);
                                }
                            } else if (AUTOMATIC_TAG.equals(name)) {
                                zenModeConfig.automaticRules.put(attributeValue, readRuleXml);
                            }
                        }
                    } else if ("state".equals(name)) {
                        zenModeConfig.areChannelsBypassingDnd = safeBoolean(typedXmlPullParser, "areChannelsBypassingDnd", false);
                    }
                }
            } else {
                throw new java.lang.IllegalStateException("Failed to reach END_DOCUMENT");
            }
        }
    }

    public static java.lang.String deletedRuleKey(android.service.notification.ZenModeConfig.ZenRule zenRule) {
        if (zenRule.pkg != null && zenRule.conditionId != null) {
            return zenRule.pkg + android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + zenRule.conditionId.toString();
        }
        return null;
    }

    public void writeXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.Integer num, boolean z) throws java.io.IOException {
        int currentXmlVersion = getCurrentXmlVersion();
        typedXmlSerializer.startTag(null, "zen");
        typedXmlSerializer.attribute(null, "version", num == null ? java.lang.Integer.toString(currentXmlVersion) : java.lang.Integer.toString(num.intValue()));
        typedXmlSerializer.attributeInt(null, "user", this.user);
        typedXmlSerializer.startTag(null, ALLOW_TAG);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_CALLS, this.allowCalls);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_REPEAT_CALLERS, this.allowRepeatCallers);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_MESSAGES, this.allowMessages);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_REMINDERS, this.allowReminders);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_EVENTS, this.allowEvents);
        typedXmlSerializer.attributeInt(null, ALLOW_ATT_CALLS_FROM, this.allowCallsFrom);
        typedXmlSerializer.attributeInt(null, ALLOW_ATT_MESSAGES_FROM, this.allowMessagesFrom);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_ALARMS, this.allowAlarms);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_MEDIA, this.allowMedia);
        typedXmlSerializer.attributeBoolean(null, "system", this.allowSystem);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_CONV, this.allowConversations);
        typedXmlSerializer.attributeInt(null, ALLOW_ATT_CONV_FROM, this.allowConversationsFrom);
        if (android.app.Flags.modesApi()) {
            typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_CHANNELS, this.allowPriorityChannels);
        }
        typedXmlSerializer.endTag(null, ALLOW_TAG);
        typedXmlSerializer.startTag(null, DISALLOW_TAG);
        typedXmlSerializer.attributeInt(null, DISALLOW_ATT_VISUAL_EFFECTS, this.suppressedVisualEffects);
        typedXmlSerializer.endTag(null, DISALLOW_TAG);
        if (this.manualRule != null) {
            typedXmlSerializer.startTag(null, "manual");
            writeRuleXml(this.manualRule, typedXmlSerializer);
            typedXmlSerializer.endTag(null, "manual");
        }
        int size = this.automaticRules.size();
        for (int i = 0; i < size; i++) {
            java.lang.String keyAt = this.automaticRules.keyAt(i);
            android.service.notification.ZenModeConfig.ZenRule valueAt = this.automaticRules.valueAt(i);
            typedXmlSerializer.startTag(null, AUTOMATIC_TAG);
            typedXmlSerializer.attribute(null, RULE_ATT_ID, keyAt);
            writeRuleXml(valueAt, typedXmlSerializer);
            typedXmlSerializer.endTag(null, AUTOMATIC_TAG);
        }
        if (android.app.Flags.modesApi() && !z) {
            for (int i2 = 0; i2 < this.deletedRules.size(); i2++) {
                android.service.notification.ZenModeConfig.ZenRule valueAt2 = this.deletedRules.valueAt(i2);
                typedXmlSerializer.startTag(null, "deleted");
                typedXmlSerializer.attribute(null, RULE_ATT_ID, valueAt2.id);
                writeRuleXml(valueAt2, typedXmlSerializer);
                typedXmlSerializer.endTag(null, "deleted");
            }
        }
        typedXmlSerializer.startTag(null, "state");
        typedXmlSerializer.attributeBoolean(null, "areChannelsBypassingDnd", this.areChannelsBypassingDnd);
        typedXmlSerializer.endTag(null, "state");
        typedXmlSerializer.endTag(null, "zen");
    }

    public static android.service.notification.ZenModeConfig.ZenRule readRuleXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        android.service.notification.ZenModeConfig.ZenRule zenRule = new android.service.notification.ZenModeConfig.ZenRule();
        zenRule.enabled = safeBoolean(typedXmlPullParser, "enabled", true);
        zenRule.name = typedXmlPullParser.getAttributeValue(null, "name");
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(null, "zen");
        zenRule.zenMode = tryParseZenMode(attributeValue, -1);
        if (zenRule.zenMode == -1) {
            android.util.Slog.w(TAG, "Bad zen mode in rule xml:" + attributeValue);
            return null;
        }
        zenRule.conditionId = safeUri(typedXmlPullParser, "conditionId");
        zenRule.component = safeComponentName(typedXmlPullParser, "component");
        zenRule.configurationActivity = safeComponentName(typedXmlPullParser, RULE_ATT_CONFIG_ACTIVITY);
        zenRule.pkg = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "pkg");
        if (zenRule.pkg == null) {
            zenRule.pkg = zenRule.component != null ? zenRule.component.getPackageName() : null;
        }
        zenRule.creationTime = safeLong(typedXmlPullParser, "creationTime", 0L);
        zenRule.enabler = typedXmlPullParser.getAttributeValue(null, "enabler");
        zenRule.condition = readConditionXml(typedXmlPullParser);
        if (!android.app.Flags.modesApi() && zenRule.zenMode != 1 && android.service.notification.Condition.isValidId(zenRule.conditionId, "android")) {
            android.util.Slog.i(TAG, "Updating zenMode of automatic rule " + zenRule.name);
            zenRule.zenMode = 1;
        }
        zenRule.modified = safeBoolean(typedXmlPullParser, "modified", false);
        zenRule.zenPolicy = readZenPolicyXml(typedXmlPullParser);
        if (android.app.Flags.modesApi()) {
            zenRule.zenDeviceEffects = readZenDeviceEffectsXml(typedXmlPullParser);
            zenRule.allowManualInvocation = safeBoolean(typedXmlPullParser, RULE_ATT_ALLOW_MANUAL, false);
            zenRule.iconResName = typedXmlPullParser.getAttributeValue(null, RULE_ATT_ICON);
            zenRule.triggerDescription = typedXmlPullParser.getAttributeValue(null, RULE_ATT_TRIGGER_DESC);
            zenRule.type = safeInt(typedXmlPullParser, "type", -1);
            zenRule.userModifiedFields = safeInt(typedXmlPullParser, RULE_ATT_USER_MODIFIED_FIELDS, 0);
            zenRule.zenPolicyUserModifiedFields = safeInt(typedXmlPullParser, POLICY_USER_MODIFIED_FIELDS, 0);
            zenRule.zenDeviceEffectsUserModifiedFields = safeInt(typedXmlPullParser, DEVICE_EFFECT_USER_MODIFIED_FIELDS, 0);
            java.lang.Long tryParseLong = tryParseLong(typedXmlPullParser.getAttributeValue(null, RULE_ATT_DELETION_INSTANT), (java.lang.Long) null);
            if (tryParseLong != null) {
                zenRule.deletionInstant = java.time.Instant.ofEpochMilli(tryParseLong.longValue());
            }
        }
        return zenRule;
    }

    public static void writeRuleXml(android.service.notification.ZenModeConfig.ZenRule zenRule, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attributeBoolean(null, "enabled", zenRule.enabled);
        if (zenRule.name != null) {
            typedXmlSerializer.attribute(null, "name", zenRule.name);
        }
        typedXmlSerializer.attributeInt(null, "zen", zenRule.zenMode);
        if (zenRule.pkg != null) {
            typedXmlSerializer.attribute(null, "pkg", zenRule.pkg);
        }
        if (zenRule.component != null) {
            typedXmlSerializer.attribute(null, "component", zenRule.component.flattenToString());
        }
        if (zenRule.configurationActivity != null) {
            typedXmlSerializer.attribute(null, RULE_ATT_CONFIG_ACTIVITY, zenRule.configurationActivity.flattenToString());
        }
        if (zenRule.conditionId != null) {
            typedXmlSerializer.attribute(null, "conditionId", zenRule.conditionId.toString());
        }
        typedXmlSerializer.attributeLong(null, "creationTime", zenRule.creationTime);
        if (zenRule.enabler != null) {
            typedXmlSerializer.attribute(null, "enabler", zenRule.enabler);
        }
        if (zenRule.condition != null) {
            writeConditionXml(zenRule.condition, typedXmlSerializer);
        }
        if (zenRule.zenPolicy != null) {
            writeZenPolicyXml(zenRule.zenPolicy, typedXmlSerializer);
        }
        if (android.app.Flags.modesApi() && zenRule.zenDeviceEffects != null) {
            writeZenDeviceEffectsXml(zenRule.zenDeviceEffects, typedXmlSerializer);
        }
        typedXmlSerializer.attributeBoolean(null, "modified", zenRule.modified);
        if (android.app.Flags.modesApi()) {
            typedXmlSerializer.attributeBoolean(null, RULE_ATT_ALLOW_MANUAL, zenRule.allowManualInvocation);
            if (zenRule.iconResName != null) {
                typedXmlSerializer.attribute(null, RULE_ATT_ICON, zenRule.iconResName);
            }
            if (zenRule.triggerDescription != null) {
                typedXmlSerializer.attribute(null, RULE_ATT_TRIGGER_DESC, zenRule.triggerDescription);
            }
            typedXmlSerializer.attributeInt(null, "type", zenRule.type);
            typedXmlSerializer.attributeInt(null, RULE_ATT_USER_MODIFIED_FIELDS, zenRule.userModifiedFields);
            typedXmlSerializer.attributeInt(null, POLICY_USER_MODIFIED_FIELDS, zenRule.zenPolicyUserModifiedFields);
            typedXmlSerializer.attributeInt(null, DEVICE_EFFECT_USER_MODIFIED_FIELDS, zenRule.zenDeviceEffectsUserModifiedFields);
            if (zenRule.deletionInstant != null) {
                typedXmlSerializer.attributeLong(null, RULE_ATT_DELETION_INSTANT, zenRule.deletionInstant.toEpochMilli());
            }
        }
    }

    public static android.service.notification.Condition readConditionXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        android.net.Uri safeUri = safeUri(typedXmlPullParser, "id");
        if (safeUri == null) {
            return null;
        }
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(null, "summary");
        java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue(null, CONDITION_ATT_LINE1);
        java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue(null, CONDITION_ATT_LINE2);
        int safeInt = safeInt(typedXmlPullParser, "icon", -1);
        int safeInt2 = safeInt(typedXmlPullParser, "state", -1);
        int safeInt3 = safeInt(typedXmlPullParser, "flags", -1);
        try {
            if (android.app.Flags.modesApi()) {
                return new android.service.notification.Condition(safeUri, attributeValue, attributeValue2, attributeValue3, safeInt, safeInt2, safeInt(typedXmlPullParser, "source", 0), safeInt3);
            }
            return new android.service.notification.Condition(safeUri, attributeValue, attributeValue2, attributeValue3, safeInt, safeInt2, safeInt3);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.w(TAG, "Unable to read condition xml", e);
            return null;
        }
    }

    public static void writeConditionXml(android.service.notification.Condition condition, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attribute(null, "id", condition.id.toString());
        typedXmlSerializer.attribute(null, "summary", condition.summary);
        typedXmlSerializer.attribute(null, CONDITION_ATT_LINE1, condition.line1);
        typedXmlSerializer.attribute(null, CONDITION_ATT_LINE2, condition.line2);
        typedXmlSerializer.attributeInt(null, "icon", condition.icon);
        typedXmlSerializer.attributeInt(null, "state", condition.state);
        if (android.app.Flags.modesApi()) {
            typedXmlSerializer.attributeInt(null, "source", condition.source);
        }
        typedXmlSerializer.attributeInt(null, "flags", condition.flags);
    }

    public static android.service.notification.ZenPolicy readZenPolicyXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        boolean z;
        int safeInt;
        android.service.notification.ZenPolicy.Builder builder = new android.service.notification.ZenPolicy.Builder();
        int safeInt2 = safeInt(typedXmlPullParser, ALLOW_ATT_CALLS_FROM, 0);
        int safeInt3 = safeInt(typedXmlPullParser, ALLOW_ATT_MESSAGES_FROM, 0);
        int safeInt4 = safeInt(typedXmlPullParser, ALLOW_ATT_REPEAT_CALLERS, 0);
        int safeInt5 = safeInt(typedXmlPullParser, ALLOW_ATT_CONV_FROM, 0);
        int safeInt6 = safeInt(typedXmlPullParser, ALLOW_ATT_ALARMS, 0);
        int safeInt7 = safeInt(typedXmlPullParser, ALLOW_ATT_MEDIA, 0);
        int safeInt8 = safeInt(typedXmlPullParser, "system", 0);
        int safeInt9 = safeInt(typedXmlPullParser, ALLOW_ATT_EVENTS, 0);
        int safeInt10 = safeInt(typedXmlPullParser, ALLOW_ATT_REMINDERS, 0);
        boolean z2 = true;
        if (android.app.Flags.modesApi() && (safeInt = safeInt(typedXmlPullParser, ALLOW_ATT_CHANNELS, 0)) != 0) {
            builder.allowPriorityChannels(safeInt == 1);
            z = true;
        } else {
            z = false;
        }
        if (safeInt2 != 0) {
            builder.allowCalls(safeInt2);
            z = true;
        }
        if (safeInt3 != 0) {
            builder.allowMessages(safeInt3);
            z = true;
        }
        if (safeInt4 != 0) {
            builder.allowRepeatCallers(safeInt4 == 1);
            z = true;
        }
        if (safeInt5 != 0) {
            builder.allowConversations(safeInt5);
            z = true;
        }
        if (safeInt6 != 0) {
            builder.allowAlarms(safeInt6 == 1);
            z = true;
        }
        if (safeInt7 != 0) {
            builder.allowMedia(safeInt7 == 1);
            z = true;
        }
        if (safeInt8 != 0) {
            builder.allowSystem(safeInt8 == 1);
            z = true;
        }
        if (safeInt9 != 0) {
            builder.allowEvents(safeInt9 == 1);
            z = true;
        }
        if (safeInt10 != 0) {
            builder.allowReminders(safeInt10 == 1);
            z = true;
        }
        int safeInt11 = safeInt(typedXmlPullParser, SHOW_ATT_FULL_SCREEN_INTENT, 0);
        int safeInt12 = safeInt(typedXmlPullParser, SHOW_ATT_LIGHTS, 0);
        int safeInt13 = safeInt(typedXmlPullParser, SHOW_ATT_PEEK, 0);
        int safeInt14 = safeInt(typedXmlPullParser, SHOW_ATT_STATUS_BAR_ICONS, 0);
        int safeInt15 = safeInt(typedXmlPullParser, SHOW_ATT_BADGES, 0);
        int safeInt16 = safeInt(typedXmlPullParser, SHOW_ATT_AMBIENT, 0);
        int safeInt17 = safeInt(typedXmlPullParser, SHOW_ATT_NOTIFICATION_LIST, 0);
        if (safeInt11 != 0) {
            builder.showFullScreenIntent(safeInt11 == 1);
            z = true;
        }
        if (safeInt12 != 0) {
            builder.showLights(safeInt12 == 1);
            z = true;
        }
        if (safeInt13 != 0) {
            builder.showPeeking(safeInt13 == 1);
            z = true;
        }
        if (safeInt14 != 0) {
            builder.showStatusBarIcons(safeInt14 == 1);
            z = true;
        }
        if (safeInt15 != 0) {
            builder.showBadges(safeInt15 == 1);
            z = true;
        }
        if (safeInt16 != 0) {
            builder.showInAmbientDisplay(safeInt16 == 1);
            z = true;
        }
        if (safeInt17 == 0) {
            z2 = z;
        } else {
            builder.showInNotificationList(safeInt17 == 1);
        }
        if (z2) {
            return builder.build();
        }
        return null;
    }

    public static void writeZenPolicyXml(android.service.notification.ZenPolicy zenPolicy, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        writeZenPolicyState(ALLOW_ATT_CALLS_FROM, zenPolicy.getPriorityCallSenders(), typedXmlSerializer);
        writeZenPolicyState(ALLOW_ATT_MESSAGES_FROM, zenPolicy.getPriorityMessageSenders(), typedXmlSerializer);
        writeZenPolicyState(ALLOW_ATT_REPEAT_CALLERS, zenPolicy.getPriorityCategoryRepeatCallers(), typedXmlSerializer);
        writeZenPolicyState(ALLOW_ATT_CONV_FROM, zenPolicy.getPriorityConversationSenders(), typedXmlSerializer);
        writeZenPolicyState(ALLOW_ATT_ALARMS, zenPolicy.getPriorityCategoryAlarms(), typedXmlSerializer);
        writeZenPolicyState(ALLOW_ATT_MEDIA, zenPolicy.getPriorityCategoryMedia(), typedXmlSerializer);
        writeZenPolicyState("system", zenPolicy.getPriorityCategorySystem(), typedXmlSerializer);
        writeZenPolicyState(ALLOW_ATT_REMINDERS, zenPolicy.getPriorityCategoryReminders(), typedXmlSerializer);
        writeZenPolicyState(ALLOW_ATT_EVENTS, zenPolicy.getPriorityCategoryEvents(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_FULL_SCREEN_INTENT, zenPolicy.getVisualEffectFullScreenIntent(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_LIGHTS, zenPolicy.getVisualEffectLights(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_PEEK, zenPolicy.getVisualEffectPeek(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_STATUS_BAR_ICONS, zenPolicy.getVisualEffectStatusBar(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_BADGES, zenPolicy.getVisualEffectBadge(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_AMBIENT, zenPolicy.getVisualEffectAmbient(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_NOTIFICATION_LIST, zenPolicy.getVisualEffectNotificationList(), typedXmlSerializer);
        if (android.app.Flags.modesApi()) {
            writeZenPolicyState(ALLOW_ATT_CHANNELS, zenPolicy.getPriorityChannelsAllowed(), typedXmlSerializer);
        }
    }

    private static void writeZenPolicyState(java.lang.String str, int i, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        if (java.util.Objects.equals(str, ALLOW_ATT_CALLS_FROM) || java.util.Objects.equals(str, ALLOW_ATT_MESSAGES_FROM)) {
            if (i != 0) {
                typedXmlSerializer.attributeInt(null, str, i);
            }
        } else if (java.util.Objects.equals(str, ALLOW_ATT_CONV_FROM)) {
            if (i != 0) {
                typedXmlSerializer.attributeInt(null, str, i);
            }
        } else if (android.app.Flags.modesApi() && java.util.Objects.equals(str, ALLOW_ATT_CHANNELS)) {
            if (i != 0) {
                typedXmlSerializer.attributeInt(null, str, i);
            }
        } else if (i != 0) {
            typedXmlSerializer.attributeInt(null, str, i);
        }
    }

    private static android.service.notification.ZenDeviceEffects readZenDeviceEffectsXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        android.service.notification.ZenDeviceEffects build = new android.service.notification.ZenDeviceEffects.Builder().setShouldDisplayGrayscale(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_DISPLAY_GRAYSCALE, false)).setShouldSuppressAmbientDisplay(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_SUPPRESS_AMBIENT_DISPLAY, false)).setShouldDimWallpaper(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_DIM_WALLPAPER, false)).setShouldUseNightMode(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_USE_NIGHT_MODE, false)).setShouldDisableAutoBrightness(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_DISABLE_AUTO_BRIGHTNESS, false)).setShouldDisableTapToWake(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_DISABLE_TAP_TO_WAKE, false)).setShouldDisableTiltToWake(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_DISABLE_TILT_TO_WAKE, false)).setShouldDisableTouch(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_DISABLE_TOUCH, false)).setShouldMinimizeRadioUsage(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_MINIMIZE_RADIO_USAGE, false)).setShouldMaximizeDoze(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_MAXIMIZE_DOZE, false)).setExtraEffects(safeStringSet(typedXmlPullParser, DEVICE_EFFECT_EXTRAS)).build();
        if (build.hasEffects()) {
            return build;
        }
        return null;
    }

    private static void writeZenDeviceEffectsXml(android.service.notification.ZenDeviceEffects zenDeviceEffects, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_DISPLAY_GRAYSCALE, zenDeviceEffects.shouldDisplayGrayscale());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_SUPPRESS_AMBIENT_DISPLAY, zenDeviceEffects.shouldSuppressAmbientDisplay());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_DIM_WALLPAPER, zenDeviceEffects.shouldDimWallpaper());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_USE_NIGHT_MODE, zenDeviceEffects.shouldUseNightMode());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_DISABLE_AUTO_BRIGHTNESS, zenDeviceEffects.shouldDisableAutoBrightness());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_DISABLE_TAP_TO_WAKE, zenDeviceEffects.shouldDisableTapToWake());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_DISABLE_TILT_TO_WAKE, zenDeviceEffects.shouldDisableTiltToWake());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_DISABLE_TOUCH, zenDeviceEffects.shouldDisableTouch());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_MINIMIZE_RADIO_USAGE, zenDeviceEffects.shouldMinimizeRadioUsage());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_MAXIMIZE_DOZE, zenDeviceEffects.shouldMaximizeDoze());
        writeStringSet(typedXmlSerializer, DEVICE_EFFECT_EXTRAS, zenDeviceEffects.getExtraEffects());
    }

    private static void writeBooleanIfTrue(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, boolean z) throws java.io.IOException {
        if (z) {
            typedXmlSerializer.attributeBoolean(null, str, true);
        }
    }

    private static void writeStringSet(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.util.Set<java.lang.String> set) throws java.io.IOException {
        if (set.isEmpty()) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<java.lang.String> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().replace(ITEM_SEPARATOR_ESCAPE, "\\\\").replace(",", "\\,"));
        }
        typedXmlSerializer.attribute(null, str, java.lang.String.join(",", arrayList));
    }

    public static boolean isValidHour(int i) {
        return i >= 0 && i < 24;
    }

    public static boolean isValidMinute(int i) {
        return i >= 0 && i < 60;
    }

    private static boolean isValidSource(int i) {
        return i >= 0 && i <= 2;
    }

    private static java.lang.Boolean unsafeBoolean(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        try {
            return java.lang.Boolean.valueOf(typedXmlPullParser.getAttributeBoolean(null, str));
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    private static boolean safeBoolean(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, boolean z) {
        return typedXmlPullParser.getAttributeBoolean(null, str, z);
    }

    private static boolean safeBoolean(java.lang.String str, boolean z) {
        return android.text.TextUtils.isEmpty(str) ? z : java.lang.Boolean.parseBoolean(str);
    }

    private static int safeInt(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, int i) {
        return typedXmlPullParser.getAttributeInt(null, str, i);
    }

    private static android.content.ComponentName safeComponentName(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (android.text.TextUtils.isEmpty(attributeValue)) {
            return null;
        }
        return android.content.ComponentName.unflattenFromString(attributeValue);
    }

    private static android.net.Uri safeUri(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (attributeValue == null) {
            return null;
        }
        return android.net.Uri.parse(attributeValue);
    }

    private static long safeLong(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, long j) {
        return tryParseLong(typedXmlPullParser.getAttributeValue(null, str), j);
    }

    private static java.util.Set<java.lang.String> safeStringSet(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (!android.text.TextUtils.isEmpty(attributeValue)) {
            for (java.lang.String str2 : ITEM_SPLITTER_REGEX.split(attributeValue)) {
                hashSet.add(str2.replace("\\\\", ITEM_SEPARATOR_ESCAPE).replace("\\,", ","));
            }
        }
        return hashSet;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.service.notification.ZenModeConfig copy() {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            return new android.service.notification.ZenModeConfig(obtain);
        } finally {
            obtain.recycle();
        }
    }

    public android.service.notification.ZenPolicy toZenPolicy() {
        int i;
        int i2;
        android.service.notification.ZenPolicy.Builder builder = new android.service.notification.ZenPolicy.Builder();
        if (this.allowCalls) {
            i = getZenPolicySenders(this.allowCallsFrom);
        } else {
            i = 4;
        }
        android.service.notification.ZenPolicy.Builder allowRepeatCallers = builder.allowCalls(i).allowRepeatCallers(this.allowRepeatCallers);
        if (this.allowMessages) {
            i2 = getZenPolicySenders(this.allowMessagesFrom);
        } else {
            i2 = 4;
        }
        android.service.notification.ZenPolicy.Builder allowConversations = allowRepeatCallers.allowMessages(i2).allowReminders(this.allowReminders).allowEvents(this.allowEvents).allowAlarms(this.allowAlarms).allowMedia(this.allowMedia).allowSystem(this.allowSystem).allowConversations(this.allowConversations ? this.allowConversationsFrom : 3);
        if (this.suppressedVisualEffects == 0) {
            allowConversations.showAllVisualEffects();
        } else {
            allowConversations.showFullScreenIntent((this.suppressedVisualEffects & 4) == 0);
            allowConversations.showLights((this.suppressedVisualEffects & 8) == 0);
            allowConversations.showPeeking((this.suppressedVisualEffects & 16) == 0);
            allowConversations.showStatusBarIcons((this.suppressedVisualEffects & 32) == 0);
            allowConversations.showBadges((this.suppressedVisualEffects & 64) == 0);
            allowConversations.showInAmbientDisplay((this.suppressedVisualEffects & 128) == 0);
            allowConversations.showInNotificationList((this.suppressedVisualEffects & 256) == 0);
        }
        if (android.app.Flags.modesApi()) {
            allowConversations.allowPriorityChannels(this.allowPriorityChannels);
        }
        return allowConversations.build();
    }

    public android.app.NotificationManager.Policy toNotificationPolicy(android.service.notification.ZenPolicy zenPolicy) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        android.app.NotificationManager.Policy notificationPolicy = toNotificationPolicy();
        int i6 = notificationPolicy.priorityCallSenders;
        int i7 = notificationPolicy.priorityMessageSenders;
        int i8 = notificationPolicy.priorityConversationSenders;
        int i9 = 0;
        if (!zenPolicy.isCategoryAllowed(0, isPriorityCategoryEnabled(1, notificationPolicy))) {
            i = 0;
        } else {
            i = 1;
        }
        if (zenPolicy.isCategoryAllowed(1, isPriorityCategoryEnabled(2, notificationPolicy))) {
            i |= 2;
        }
        if (!zenPolicy.isCategoryAllowed(2, isPriorityCategoryEnabled(4, notificationPolicy))) {
            i2 = i7;
        } else {
            i |= 4;
            i2 = getNotificationPolicySenders(zenPolicy.getPriorityMessageSenders(), i7);
        }
        if (zenPolicy.isCategoryAllowed(8, isPriorityCategoryEnabled(256, notificationPolicy))) {
            i |= 256;
            i3 = getConversationSendersWithDefault(zenPolicy.getPriorityConversationSenders(), i8);
        } else {
            i3 = 3;
        }
        if (zenPolicy.isCategoryAllowed(3, isPriorityCategoryEnabled(8, notificationPolicy))) {
            i |= 8;
            i6 = getNotificationPolicySenders(zenPolicy.getPriorityCallSenders(), i6);
        }
        if (zenPolicy.isCategoryAllowed(4, isPriorityCategoryEnabled(16, notificationPolicy))) {
            i |= 16;
        }
        if (zenPolicy.isCategoryAllowed(5, isPriorityCategoryEnabled(32, notificationPolicy))) {
            i |= 32;
        }
        if (zenPolicy.isCategoryAllowed(6, isPriorityCategoryEnabled(64, notificationPolicy))) {
            i |= 64;
        }
        if (zenPolicy.isCategoryAllowed(7, isPriorityCategoryEnabled(128, notificationPolicy))) {
            i |= 128;
        }
        boolean z = !zenPolicy.isVisualEffectAllowed(0, isVisualEffectAllowed(4, notificationPolicy));
        boolean z2 = !zenPolicy.isVisualEffectAllowed(1, isVisualEffectAllowed(8, notificationPolicy));
        boolean z3 = !zenPolicy.isVisualEffectAllowed(5, isVisualEffectAllowed(128, notificationPolicy));
        if (z && z2 && z3) {
            i9 = 1;
        }
        if (z) {
            i9 |= 4;
        }
        if (z2) {
            i9 |= 8;
        }
        if (!zenPolicy.isVisualEffectAllowed(2, isVisualEffectAllowed(16, notificationPolicy))) {
            i9 = i9 | 16 | 2;
        }
        if (!zenPolicy.isVisualEffectAllowed(3, isVisualEffectAllowed(32, notificationPolicy))) {
            i9 |= 32;
        }
        if (!zenPolicy.isVisualEffectAllowed(4, isVisualEffectAllowed(64, notificationPolicy))) {
            i9 |= 64;
        }
        if (z3) {
            i9 |= 128;
        }
        if (zenPolicy.isVisualEffectAllowed(6, isVisualEffectAllowed(256, notificationPolicy))) {
            i4 = i9;
        } else {
            i4 = i9 | 256;
        }
        int i10 = notificationPolicy.state;
        if (!android.app.Flags.modesApi()) {
            i5 = i10;
        } else {
            i5 = android.app.NotificationManager.Policy.policyState(notificationPolicy.hasPriorityChannels(), android.service.notification.ZenPolicy.stateToBoolean(zenPolicy.getPriorityChannelsAllowed(), true));
        }
        return new android.app.NotificationManager.Policy(i, i6, i2, i4, i5, i3);
    }

    private boolean isPriorityCategoryEnabled(int i, android.app.NotificationManager.Policy policy) {
        return (i & policy.priorityCategories) != 0;
    }

    private boolean isVisualEffectAllowed(int i, android.app.NotificationManager.Policy policy) {
        return (i & policy.suppressedVisualEffects) == 0;
    }

    private static int getNotificationPolicySenders(int i, int i2) {
        switch (i) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            default:
                return i2;
        }
    }

    private static int getConversationSendersWithDefault(int i, int i2) {
        switch (i) {
            case 1:
            case 2:
            case 3:
                return i;
            default:
                return i2;
        }
    }

    public static int getZenPolicySenders(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            default:
                return 3;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1, types: [int] */
    /* JADX WARN: Type inference failed for: r6v2 */
    public android.app.NotificationManager.Policy toNotificationPolicy() {
        int i;
        int i2;
        ?? r6;
        if (!this.allowConversations) {
            i = 0;
        } else {
            i = 256;
        }
        if (this.allowCalls) {
            i |= 8;
        }
        if (this.allowMessages) {
            i |= 4;
        }
        if (this.allowEvents) {
            i |= 2;
        }
        if (this.allowReminders) {
            i |= 1;
        }
        if (this.allowRepeatCallers) {
            i |= 16;
        }
        if (this.allowAlarms) {
            i |= 32;
        }
        if (this.allowMedia) {
            i |= 64;
        }
        if (!this.allowSystem) {
            i2 = i;
        } else {
            i2 = i | 128;
        }
        int sourceToPrioritySenders = sourceToPrioritySenders(this.allowCallsFrom, 1);
        int sourceToPrioritySenders2 = sourceToPrioritySenders(this.allowMessagesFrom, 1);
        int conversationSendersWithDefault = getConversationSendersWithDefault(this.allowConversationsFrom, 2);
        boolean z = this.areChannelsBypassingDnd;
        if (!android.app.Flags.modesApi()) {
            r6 = z;
        } else {
            r6 = android.app.NotificationManager.Policy.policyState(this.areChannelsBypassingDnd, this.allowPriorityChannels);
        }
        return new android.app.NotificationManager.Policy(i2, sourceToPrioritySenders, sourceToPrioritySenders2, this.suppressedVisualEffects, r6, conversationSendersWithDefault);
    }

    public static android.service.notification.ScheduleCalendar toScheduleCalendar(android.net.Uri uri) {
        android.service.notification.ZenModeConfig.ScheduleInfo tryParseScheduleConditionId = tryParseScheduleConditionId(uri);
        if (tryParseScheduleConditionId == null || tryParseScheduleConditionId.days == null || tryParseScheduleConditionId.days.length == 0) {
            return null;
        }
        android.service.notification.ScheduleCalendar scheduleCalendar = new android.service.notification.ScheduleCalendar();
        scheduleCalendar.setSchedule(tryParseScheduleConditionId);
        scheduleCalendar.setTimeZone(java.util.TimeZone.getDefault());
        return scheduleCalendar;
    }

    private static int sourceToPrioritySenders(int i, int i2) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return i2;
        }
    }

    private static int prioritySendersToSource(int i, int i2) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return i2;
        }
    }

    private static int normalizePrioritySenders(int i, int i2) {
        if (i != 1 && i != 2 && i != 0) {
            return i2;
        }
        return i;
    }

    private static int normalizeConversationSenders(boolean z, int i, int i2) {
        if (!z) {
            return 3;
        }
        if (i != 1 && i != 2 && i != 3) {
            return i2;
        }
        return i;
    }

    public void applyNotificationPolicy(android.app.NotificationManager.Policy policy) {
        if (policy == null) {
            return;
        }
        this.allowAlarms = (policy.priorityCategories & 32) != 0;
        this.allowMedia = (policy.priorityCategories & 64) != 0;
        this.allowSystem = (policy.priorityCategories & 128) != 0;
        this.allowEvents = (policy.priorityCategories & 2) != 0;
        this.allowReminders = (policy.priorityCategories & 1) != 0;
        this.allowCalls = (policy.priorityCategories & 8) != 0;
        this.allowMessages = (policy.priorityCategories & 4) != 0;
        this.allowRepeatCallers = (policy.priorityCategories & 16) != 0;
        this.allowCallsFrom = normalizePrioritySenders(policy.priorityCallSenders, this.allowCallsFrom);
        this.allowMessagesFrom = normalizePrioritySenders(policy.priorityMessageSenders, this.allowMessagesFrom);
        if (policy.suppressedVisualEffects != -1) {
            this.suppressedVisualEffects = policy.suppressedVisualEffects;
        }
        this.allowConversations = (policy.priorityCategories & 256) != 0;
        this.allowConversationsFrom = normalizeConversationSenders(this.allowConversations, policy.priorityConversationSenders, this.allowConversationsFrom);
        if (policy.state != -1) {
            this.areChannelsBypassingDnd = (policy.state & 1) != 0;
            if (android.app.Flags.modesApi()) {
                this.allowPriorityChannels = policy.allowPriorityChannels();
            }
        }
    }

    public static android.service.notification.Condition toTimeCondition(android.content.Context context, int i, int i2) {
        return toTimeCondition(context, i, i2, false);
    }

    public static android.service.notification.Condition toTimeCondition(android.content.Context context, int i, int i2, boolean z) {
        return toTimeCondition(context, java.lang.System.currentTimeMillis() + (i == 0 ? android.app.job.JobInfo.MIN_BACKOFF_MILLIS : 60000 * i), i, i2, z);
    }

    public static android.service.notification.Condition toTimeCondition(android.content.Context context, long j, int i, int i2, boolean z) {
        java.lang.String string;
        java.lang.String str;
        java.lang.String str2;
        java.lang.CharSequence formattedTime = getFormattedTime(context, j, isToday(j), i2);
        android.content.res.Resources resources = context.getResources();
        java.util.HashMap hashMap = new java.util.HashMap();
        if (i < 60) {
            int i3 = z ? com.android.internal.R.string.zen_mode_duration_minutes_summary_short : com.android.internal.R.string.zen_mode_duration_minutes_summary;
            hashMap.put("count", java.lang.Integer.valueOf(i));
            hashMap.put("formattedTime", formattedTime);
            java.lang.String format = android.util.PluralsMessageFormatter.format(resources, hashMap, i3);
            java.lang.String format2 = android.util.PluralsMessageFormatter.format(resources, hashMap, z ? com.android.internal.R.string.zen_mode_duration_minutes_short : com.android.internal.R.string.zen_mode_duration_minutes);
            str2 = resources.getString(com.android.internal.R.string.zen_mode_until, formattedTime);
            string = format;
            str = format2;
        } else if (i < 1440) {
            int round = java.lang.Math.round(i / 60.0f);
            int i4 = z ? com.android.internal.R.string.zen_mode_duration_hours_summary_short : com.android.internal.R.string.zen_mode_duration_hours_summary;
            hashMap.put("count", java.lang.Integer.valueOf(round));
            hashMap.put("formattedTime", formattedTime);
            java.lang.String format3 = android.util.PluralsMessageFormatter.format(resources, hashMap, i4);
            java.lang.String format4 = android.util.PluralsMessageFormatter.format(resources, hashMap, z ? com.android.internal.R.string.zen_mode_duration_hours_short : com.android.internal.R.string.zen_mode_duration_hours);
            str2 = resources.getString(com.android.internal.R.string.zen_mode_until, formattedTime);
            string = format3;
            str = format4;
        } else {
            string = resources.getString(com.android.internal.R.string.zen_mode_until_next_day, formattedTime);
            str = string;
            str2 = str;
        }
        return new android.service.notification.Condition(toCountdownConditionId(j, false), string, str, str2, 0, 1, 1);
    }

    public static android.service.notification.Condition toNextAlarmCondition(android.content.Context context, long j, int i) {
        return new android.service.notification.Condition(toCountdownConditionId(j, true), "", context.getResources().getString(com.android.internal.R.string.zen_mode_until, getFormattedTime(context, j, isToday(j), i)), "", 0, 1, 1);
    }

    public static java.lang.CharSequence getFormattedTime(android.content.Context context, long j, boolean z, int i) {
        return android.text.format.DateFormat.format(android.text.format.DateFormat.getBestDateTimePattern(java.util.Locale.getDefault(), (!z ? "EEE " : "") + (android.text.format.DateFormat.is24HourFormat(context, i) ? "Hm" : "hma")), j);
    }

    public static boolean isToday(long j) {
        java.util.GregorianCalendar gregorianCalendar = new java.util.GregorianCalendar();
        java.util.GregorianCalendar gregorianCalendar2 = new java.util.GregorianCalendar();
        gregorianCalendar2.setTimeInMillis(j);
        if (gregorianCalendar.get(1) == gregorianCalendar2.get(1) && gregorianCalendar.get(2) == gregorianCalendar2.get(2) && gregorianCalendar.get(5) == gregorianCalendar2.get(5)) {
            return true;
        }
        return false;
    }

    public static android.net.Uri toCountdownConditionId(long j, boolean z) {
        return new android.net.Uri.Builder().scheme("condition").authority("android").appendPath(COUNTDOWN_PATH).appendPath(java.lang.Long.toString(j)).appendPath("alarm").appendPath(java.lang.Boolean.toString(z)).build();
    }

    public static long tryParseCountdownConditionId(android.net.Uri uri) {
        if (!android.service.notification.Condition.isValidId(uri, "android") || uri.getPathSegments().size() < 2 || !COUNTDOWN_PATH.equals(uri.getPathSegments().get(0))) {
            return 0L;
        }
        try {
            return java.lang.Long.parseLong(uri.getPathSegments().get(1));
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.w(TAG, "Error parsing countdown condition: " + uri, e);
            return 0L;
        }
    }

    public static boolean isValidCountdownConditionId(android.net.Uri uri) {
        return tryParseCountdownConditionId(uri) != 0;
    }

    public static boolean isValidCountdownToAlarmConditionId(android.net.Uri uri) {
        if (tryParseCountdownConditionId(uri) == 0 || uri.getPathSegments().size() < 4 || !"alarm".equals(uri.getPathSegments().get(2))) {
            return false;
        }
        try {
            return java.lang.Boolean.parseBoolean(uri.getPathSegments().get(3));
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.w(TAG, "Error parsing countdown alarm condition: " + uri, e);
            return false;
        }
    }

    public static android.net.Uri toScheduleConditionId(android.service.notification.ZenModeConfig.ScheduleInfo scheduleInfo) {
        return new android.net.Uri.Builder().scheme("condition").authority("android").appendPath(SCHEDULE_PATH).appendQueryParameter("days", toDayList(scheduleInfo.days)).appendQueryParameter("start", scheduleInfo.startHour + android.media.MediaMetrics.SEPARATOR + scheduleInfo.startMinute).appendQueryParameter("end", scheduleInfo.endHour + android.media.MediaMetrics.SEPARATOR + scheduleInfo.endMinute).appendQueryParameter("exitAtAlarm", java.lang.String.valueOf(scheduleInfo.exitAtAlarm)).build();
    }

    public static boolean isValidScheduleConditionId(android.net.Uri uri) {
        try {
            android.service.notification.ZenModeConfig.ScheduleInfo tryParseScheduleConditionId = tryParseScheduleConditionId(uri);
            if (tryParseScheduleConditionId == null || tryParseScheduleConditionId.days == null || tryParseScheduleConditionId.days.length == 0) {
                return false;
            }
            return true;
        } catch (java.lang.ArrayIndexOutOfBoundsException | java.lang.NullPointerException e) {
            return false;
        }
    }

    public static boolean isValidScheduleConditionId(android.net.Uri uri, boolean z) {
        try {
            android.service.notification.ZenModeConfig.ScheduleInfo tryParseScheduleConditionId = tryParseScheduleConditionId(uri);
            if (tryParseScheduleConditionId != null) {
                if (z) {
                    return true;
                }
                if (tryParseScheduleConditionId.days != null && tryParseScheduleConditionId.days.length != 0) {
                    return true;
                }
            }
            return false;
        } catch (java.lang.ArrayIndexOutOfBoundsException | java.lang.NullPointerException e) {
            return false;
        }
    }

    public static android.service.notification.ZenModeConfig.ScheduleInfo tryParseScheduleConditionId(android.net.Uri uri) {
        if (!(uri != null && "condition".equals(uri.getScheme()) && "android".equals(uri.getAuthority()) && uri.getPathSegments().size() == 1 && SCHEDULE_PATH.equals(uri.getPathSegments().get(0)))) {
            return null;
        }
        int[] tryParseHourAndMinute = tryParseHourAndMinute(uri.getQueryParameter("start"));
        int[] tryParseHourAndMinute2 = tryParseHourAndMinute(uri.getQueryParameter("end"));
        if (tryParseHourAndMinute == null || tryParseHourAndMinute2 == null) {
            return null;
        }
        android.service.notification.ZenModeConfig.ScheduleInfo scheduleInfo = new android.service.notification.ZenModeConfig.ScheduleInfo();
        scheduleInfo.days = tryParseDayList(uri.getQueryParameter("days"), "\\.");
        scheduleInfo.startHour = tryParseHourAndMinute[0];
        scheduleInfo.startMinute = tryParseHourAndMinute[1];
        scheduleInfo.endHour = tryParseHourAndMinute2[0];
        scheduleInfo.endMinute = tryParseHourAndMinute2[1];
        scheduleInfo.exitAtAlarm = safeBoolean(uri.getQueryParameter("exitAtAlarm"), false);
        return scheduleInfo;
    }

    public static android.content.ComponentName getScheduleConditionProvider() {
        return new android.content.ComponentName("android", "ScheduleConditionProvider");
    }

    public static class ScheduleInfo {
        public int[] days;
        public int endHour;
        public int endMinute;
        public boolean exitAtAlarm;
        public long nextAlarm;
        public int startHour;
        public int startMinute;

        public int hashCode() {
            return 0;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.service.notification.ZenModeConfig.ScheduleInfo)) {
                return false;
            }
            android.service.notification.ZenModeConfig.ScheduleInfo scheduleInfo = (android.service.notification.ZenModeConfig.ScheduleInfo) obj;
            return android.service.notification.ZenModeConfig.toDayList(this.days).equals(android.service.notification.ZenModeConfig.toDayList(scheduleInfo.days)) && this.startHour == scheduleInfo.startHour && this.startMinute == scheduleInfo.startMinute && this.endHour == scheduleInfo.endHour && this.endMinute == scheduleInfo.endMinute && this.exitAtAlarm == scheduleInfo.exitAtAlarm;
        }

        public android.service.notification.ZenModeConfig.ScheduleInfo copy() {
            android.service.notification.ZenModeConfig.ScheduleInfo scheduleInfo = new android.service.notification.ZenModeConfig.ScheduleInfo();
            if (this.days != null) {
                scheduleInfo.days = new int[this.days.length];
                java.lang.System.arraycopy(this.days, 0, scheduleInfo.days, 0, this.days.length);
            }
            scheduleInfo.startHour = this.startHour;
            scheduleInfo.startMinute = this.startMinute;
            scheduleInfo.endHour = this.endHour;
            scheduleInfo.endMinute = this.endMinute;
            scheduleInfo.exitAtAlarm = this.exitAtAlarm;
            scheduleInfo.nextAlarm = this.nextAlarm;
            return scheduleInfo;
        }

        public java.lang.String toString() {
            return "ScheduleInfo{days=" + java.util.Arrays.toString(this.days) + ", startHour=" + this.startHour + ", startMinute=" + this.startMinute + ", endHour=" + this.endHour + ", endMinute=" + this.endMinute + ", exitAtAlarm=" + this.exitAtAlarm + ", nextAlarm=" + ts(this.nextAlarm) + '}';
        }

        protected static java.lang.String ts(long j) {
            return new java.util.Date(j) + " (" + j + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static android.net.Uri toEventConditionId(android.service.notification.ZenModeConfig.EventInfo eventInfo) {
        return new android.net.Uri.Builder().scheme("condition").authority("android").appendPath("event").appendQueryParameter("userId", java.lang.Long.toString(eventInfo.userId)).appendQueryParameter("calendar", eventInfo.calName != null ? eventInfo.calName : "").appendQueryParameter("calendarId", eventInfo.calendarId != null ? eventInfo.calendarId.toString() : "").appendQueryParameter("reply", java.lang.Integer.toString(eventInfo.reply)).build();
    }

    public static boolean isValidEventConditionId(android.net.Uri uri) {
        return tryParseEventConditionId(uri) != null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0034, code lost:
    
        if ("event".equals(r5.getPathSegments().get(0)) != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.service.notification.ZenModeConfig.EventInfo tryParseEventConditionId(android.net.Uri uri) {
        boolean z;
        if (uri != null && "condition".equals(uri.getScheme()) && "android".equals(uri.getAuthority())) {
            z = true;
            if (uri.getPathSegments().size() == 1) {
            }
        }
        z = false;
        if (!z) {
            return null;
        }
        android.service.notification.ZenModeConfig.EventInfo eventInfo = new android.service.notification.ZenModeConfig.EventInfo();
        eventInfo.userId = tryParseInt(uri.getQueryParameter("userId"), -10000);
        eventInfo.calName = uri.getQueryParameter("calendar");
        if (android.text.TextUtils.isEmpty(eventInfo.calName)) {
            eventInfo.calName = null;
        }
        eventInfo.calendarId = tryParseLong(uri.getQueryParameter("calendarId"), (java.lang.Long) null);
        eventInfo.reply = tryParseInt(uri.getQueryParameter("reply"), 0);
        return eventInfo;
    }

    public static android.content.ComponentName getEventConditionProvider() {
        return new android.content.ComponentName("android", "EventConditionProvider");
    }

    public static class EventInfo {
        public static final int REPLY_ANY_EXCEPT_NO = 0;
        public static final int REPLY_YES = 2;
        public static final int REPLY_YES_OR_MAYBE = 1;
        public java.lang.String calName;
        public java.lang.Long calendarId;
        public int reply;
        public int userId = -10000;

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.userId), this.calName, this.calendarId, java.lang.Integer.valueOf(this.reply));
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.service.notification.ZenModeConfig.EventInfo)) {
                return false;
            }
            android.service.notification.ZenModeConfig.EventInfo eventInfo = (android.service.notification.ZenModeConfig.EventInfo) obj;
            return this.userId == eventInfo.userId && java.util.Objects.equals(this.calName, eventInfo.calName) && this.reply == eventInfo.reply && java.util.Objects.equals(this.calendarId, eventInfo.calendarId);
        }

        public android.service.notification.ZenModeConfig.EventInfo copy() {
            android.service.notification.ZenModeConfig.EventInfo eventInfo = new android.service.notification.ZenModeConfig.EventInfo();
            eventInfo.userId = this.userId;
            eventInfo.calName = this.calName;
            eventInfo.reply = this.reply;
            eventInfo.calendarId = this.calendarId;
            return eventInfo;
        }

        public static int resolveUserId(int i) {
            return i == -10000 ? android.app.ActivityManager.getCurrentUser() : i;
        }
    }

    private static int[] tryParseHourAndMinute(java.lang.String str) {
        int indexOf;
        if (android.text.TextUtils.isEmpty(str) || (indexOf = str.indexOf(46)) < 1 || indexOf >= str.length() - 1) {
            return null;
        }
        int tryParseInt = tryParseInt(str.substring(0, indexOf), -1);
        int tryParseInt2 = tryParseInt(str.substring(indexOf + 1), -1);
        if (isValidHour(tryParseInt) && isValidMinute(tryParseInt2)) {
            return new int[]{tryParseInt, tryParseInt2};
        }
        return null;
    }

    private static int tryParseZenMode(java.lang.String str, int i) {
        int tryParseInt = tryParseInt(str, i);
        return android.provider.Settings.Global.isValidZenMode(tryParseInt) ? tryParseInt : i;
    }

    public static java.lang.String newRuleId() {
        return java.util.UUID.randomUUID().toString().replace(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE, "");
    }

    public static java.lang.String getOwnerCaption(android.content.Context context, java.lang.String str) {
        java.lang.CharSequence loadLabel;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        try {
            android.content.pm.ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
            if (applicationInfo != null && (loadLabel = applicationInfo.loadLabel(packageManager)) != null) {
                java.lang.String trim = loadLabel.toString().trim();
                if (trim.length() > 0) {
                    return trim;
                }
                return "";
            }
            return "";
        } catch (java.lang.Throwable th) {
            android.util.Slog.w(TAG, "Error loading owner caption", th);
            return "";
        }
    }

    public static java.lang.String getConditionSummary(android.content.Context context, android.service.notification.ZenModeConfig zenModeConfig, int i, boolean z) {
        return getConditionLine(context, zenModeConfig, i, false, z);
    }

    private static java.lang.String getConditionLine(android.content.Context context, android.service.notification.ZenModeConfig zenModeConfig, int i, boolean z, boolean z2) {
        java.lang.String str;
        java.lang.String str2 = "";
        if (zenModeConfig == null) {
            return "";
        }
        if (zenModeConfig.manualRule != null) {
            android.net.Uri uri = zenModeConfig.manualRule.conditionId;
            if (zenModeConfig.manualRule.enabler != null) {
                str2 = getOwnerCaption(context, zenModeConfig.manualRule.enabler);
            } else if (uri == null) {
                str2 = context.getString(com.android.internal.R.string.zen_mode_forever);
            } else {
                long tryParseCountdownConditionId = tryParseCountdownConditionId(uri);
                android.service.notification.Condition condition = zenModeConfig.manualRule.condition;
                if (tryParseCountdownConditionId > 0) {
                    condition = toTimeCondition(context, tryParseCountdownConditionId, java.lang.Math.round((tryParseCountdownConditionId - java.lang.System.currentTimeMillis()) / 60000.0f), i, z2);
                }
                if (condition == null) {
                    str = "";
                } else {
                    str = z ? condition.line1 : condition.summary;
                }
                if (!android.text.TextUtils.isEmpty(str)) {
                    str2 = str;
                }
            }
        }
        for (android.service.notification.ZenModeConfig.ZenRule zenRule : zenModeConfig.automaticRules.values()) {
            if (zenRule.isAutomaticActive()) {
                if (str2.isEmpty()) {
                    str2 = zenRule.name;
                } else {
                    str2 = context.getResources().getString(com.android.internal.R.string.zen_mode_rule_name_combination, str2, zenRule.name);
                }
            }
        }
        return str2;
    }

    public static class ZenRule implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.service.notification.ZenModeConfig.ZenRule> CREATOR = new android.os.Parcelable.Creator<android.service.notification.ZenModeConfig.ZenRule>() { // from class: android.service.notification.ZenModeConfig.ZenRule.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.service.notification.ZenModeConfig.ZenRule createFromParcel(android.os.Parcel parcel) {
                return new android.service.notification.ZenModeConfig.ZenRule(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.service.notification.ZenModeConfig.ZenRule[] newArray(int i) {
                return new android.service.notification.ZenModeConfig.ZenRule[i];
            }
        };
        public boolean allowManualInvocation;
        public android.content.ComponentName component;
        public android.service.notification.Condition condition;
        public android.net.Uri conditionId;
        public android.content.ComponentName configurationActivity;
        public long creationTime;
        public java.time.Instant deletionInstant;
        public boolean enabled;
        public java.lang.String enabler;
        public java.lang.String iconResName;
        public java.lang.String id;
        public boolean modified;
        public java.lang.String name;
        public java.lang.String pkg;
        public boolean snoozing;
        public java.lang.String triggerDescription;
        public int type;
        public int userModifiedFields;
        public android.service.notification.ZenDeviceEffects zenDeviceEffects;
        public int zenDeviceEffectsUserModifiedFields;
        public int zenMode;
        public android.service.notification.ZenPolicy zenPolicy;
        public int zenPolicyUserModifiedFields;

        public ZenRule() {
            this.type = -1;
        }

        public ZenRule(android.os.Parcel parcel) {
            this.type = -1;
            this.enabled = parcel.readInt() == 1;
            this.snoozing = parcel.readInt() == 1;
            if (parcel.readInt() == 1) {
                this.name = parcel.readString();
            }
            this.zenMode = parcel.readInt();
            this.conditionId = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
            this.condition = (android.service.notification.Condition) parcel.readParcelable(null, android.service.notification.Condition.class);
            this.component = (android.content.ComponentName) parcel.readParcelable(null, android.content.ComponentName.class);
            this.configurationActivity = (android.content.ComponentName) parcel.readParcelable(null, android.content.ComponentName.class);
            if (parcel.readInt() == 1) {
                this.id = parcel.readString();
            }
            this.creationTime = parcel.readLong();
            if (parcel.readInt() == 1) {
                this.enabler = parcel.readString();
            }
            this.zenPolicy = (android.service.notification.ZenPolicy) parcel.readParcelable(null, android.service.notification.ZenPolicy.class);
            if (android.app.Flags.modesApi()) {
                this.zenDeviceEffects = (android.service.notification.ZenDeviceEffects) parcel.readParcelable(null, android.service.notification.ZenDeviceEffects.class);
            }
            this.modified = parcel.readInt() == 1;
            this.pkg = parcel.readString();
            if (android.app.Flags.modesApi()) {
                this.allowManualInvocation = parcel.readBoolean();
                this.iconResName = parcel.readString();
                this.triggerDescription = parcel.readString();
                this.type = parcel.readInt();
                this.userModifiedFields = parcel.readInt();
                this.zenPolicyUserModifiedFields = parcel.readInt();
                this.zenDeviceEffectsUserModifiedFields = parcel.readInt();
                if (parcel.readInt() == 1) {
                    this.deletionInstant = java.time.Instant.ofEpochMilli(parcel.readLong());
                }
            }
        }

        public boolean canBeUpdatedByApp() {
            return this.userModifiedFields == 0 && this.zenPolicyUserModifiedFields == 0 && this.zenDeviceEffectsUserModifiedFields == 0;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.enabled ? 1 : 0);
            parcel.writeInt(this.snoozing ? 1 : 0);
            if (this.name != null) {
                parcel.writeInt(1);
                parcel.writeString(this.name);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeInt(this.zenMode);
            parcel.writeParcelable(this.conditionId, 0);
            parcel.writeParcelable(this.condition, 0);
            parcel.writeParcelable(this.component, 0);
            parcel.writeParcelable(this.configurationActivity, 0);
            if (this.id != null) {
                parcel.writeInt(1);
                parcel.writeString(this.id);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeLong(this.creationTime);
            if (this.enabler != null) {
                parcel.writeInt(1);
                parcel.writeString(this.enabler);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeParcelable(this.zenPolicy, 0);
            if (android.app.Flags.modesApi()) {
                parcel.writeParcelable(this.zenDeviceEffects, 0);
            }
            parcel.writeInt(this.modified ? 1 : 0);
            parcel.writeString(this.pkg);
            if (android.app.Flags.modesApi()) {
                parcel.writeBoolean(this.allowManualInvocation);
                parcel.writeString(this.iconResName);
                parcel.writeString(this.triggerDescription);
                parcel.writeInt(this.type);
                parcel.writeInt(this.userModifiedFields);
                parcel.writeInt(this.zenPolicyUserModifiedFields);
                parcel.writeInt(this.zenDeviceEffectsUserModifiedFields);
                if (this.deletionInstant != null) {
                    parcel.writeInt(1);
                    parcel.writeLong(this.deletionInstant.toEpochMilli());
                } else {
                    parcel.writeInt(0);
                }
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder append = new java.lang.StringBuilder(android.service.notification.ZenModeConfig.ZenRule.class.getSimpleName()).append('[').append("id=").append(this.id).append(",state=").append(this.condition == null ? "STATE_FALSE" : android.service.notification.Condition.stateToString(this.condition.state)).append(",enabled=").append(java.lang.String.valueOf(this.enabled).toUpperCase()).append(",snoozing=").append(this.snoozing).append(",name=").append(this.name).append(",zenMode=").append(android.provider.Settings.Global.zenModeToString(this.zenMode)).append(",conditionId=").append(this.conditionId).append(",pkg=").append(this.pkg).append(",component=").append(this.component).append(",configActivity=").append(this.configurationActivity).append(",creationTime=").append(this.creationTime).append(",enabler=").append(this.enabler).append(",zenPolicy=").append(this.zenPolicy).append(",modified=").append(this.modified).append(",condition=").append(this.condition);
            if (android.app.Flags.modesApi()) {
                append.append(",deviceEffects=").append(this.zenDeviceEffects).append(",allowManualInvocation=").append(this.allowManualInvocation).append(",iconResName=").append(this.iconResName).append(",triggerDescription=").append(this.triggerDescription).append(",type=").append(this.type);
                if (this.userModifiedFields != 0) {
                    append.append(",userModifiedFields=").append(android.app.AutomaticZenRule.fieldsToString(this.userModifiedFields));
                }
                if (this.zenPolicyUserModifiedFields != 0) {
                    append.append(",zenPolicyUserModifiedFields=").append(android.service.notification.ZenPolicy.fieldsToString(this.zenPolicyUserModifiedFields));
                }
                if (this.zenDeviceEffectsUserModifiedFields != 0) {
                    append.append(",zenDeviceEffectsUserModifiedFields=").append(android.service.notification.ZenDeviceEffects.fieldsToString(this.zenDeviceEffectsUserModifiedFields));
                }
                if (this.deletionInstant != null) {
                    append.append(",deletionInstant=").append(this.deletionInstant);
                }
            }
            return append.append(']').toString();
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1138166333441L, this.id);
            protoOutputStream.write(1138166333442L, this.name);
            protoOutputStream.write(1112396529667L, this.creationTime);
            protoOutputStream.write(1133871366148L, this.enabled);
            protoOutputStream.write(1138166333445L, this.enabler);
            protoOutputStream.write(1133871366150L, this.snoozing);
            protoOutputStream.write(1159641169927L, this.zenMode);
            if (this.conditionId != null) {
                protoOutputStream.write(1138166333448L, this.conditionId.toString());
            }
            if (this.condition != null) {
                this.condition.dumpDebug(protoOutputStream, 1146756268041L);
            }
            if (this.component != null) {
                this.component.dumpDebug(protoOutputStream, 1146756268042L);
            }
            if (this.zenPolicy != null) {
                this.zenPolicy.dumpDebug(protoOutputStream, 1146756268043L);
            }
            protoOutputStream.write(1133871366156L, this.modified);
            protoOutputStream.end(start);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.service.notification.ZenModeConfig.ZenRule)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            android.service.notification.ZenModeConfig.ZenRule zenRule = (android.service.notification.ZenModeConfig.ZenRule) obj;
            boolean z = zenRule.enabled == this.enabled && zenRule.snoozing == this.snoozing && java.util.Objects.equals(zenRule.name, this.name) && zenRule.zenMode == this.zenMode && java.util.Objects.equals(zenRule.conditionId, this.conditionId) && java.util.Objects.equals(zenRule.condition, this.condition) && java.util.Objects.equals(zenRule.component, this.component) && java.util.Objects.equals(zenRule.configurationActivity, this.configurationActivity) && java.util.Objects.equals(zenRule.id, this.id) && java.util.Objects.equals(zenRule.enabler, this.enabler) && java.util.Objects.equals(zenRule.zenPolicy, this.zenPolicy) && java.util.Objects.equals(zenRule.pkg, this.pkg) && zenRule.modified == this.modified;
            if (android.app.Flags.modesApi()) {
                return z && java.util.Objects.equals(zenRule.zenDeviceEffects, this.zenDeviceEffects) && zenRule.allowManualInvocation == this.allowManualInvocation && java.util.Objects.equals(zenRule.iconResName, this.iconResName) && java.util.Objects.equals(zenRule.triggerDescription, this.triggerDescription) && zenRule.type == this.type && zenRule.userModifiedFields == this.userModifiedFields && zenRule.zenPolicyUserModifiedFields == this.zenPolicyUserModifiedFields && zenRule.zenDeviceEffectsUserModifiedFields == this.zenDeviceEffectsUserModifiedFields && java.util.Objects.equals(zenRule.deletionInstant, this.deletionInstant);
            }
            return z;
        }

        public int hashCode() {
            if (android.app.Flags.modesApi()) {
                return java.util.Objects.hash(java.lang.Boolean.valueOf(this.enabled), java.lang.Boolean.valueOf(this.snoozing), this.name, java.lang.Integer.valueOf(this.zenMode), this.conditionId, this.condition, this.component, this.configurationActivity, this.pkg, this.id, this.enabler, this.zenPolicy, this.zenDeviceEffects, java.lang.Boolean.valueOf(this.modified), java.lang.Boolean.valueOf(this.allowManualInvocation), this.iconResName, this.triggerDescription, java.lang.Integer.valueOf(this.type), java.lang.Integer.valueOf(this.userModifiedFields), java.lang.Integer.valueOf(this.zenPolicyUserModifiedFields), java.lang.Integer.valueOf(this.zenDeviceEffectsUserModifiedFields), this.deletionInstant);
            }
            return java.util.Objects.hash(java.lang.Boolean.valueOf(this.enabled), java.lang.Boolean.valueOf(this.snoozing), this.name, java.lang.Integer.valueOf(this.zenMode), this.conditionId, this.condition, this.component, this.configurationActivity, this.pkg, this.id, this.enabler, this.zenPolicy, java.lang.Boolean.valueOf(this.modified));
        }

        public android.service.notification.ZenModeConfig.ZenRule copy() {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            try {
                writeToParcel(obtain, 0);
                obtain.setDataPosition(0);
                return new android.service.notification.ZenModeConfig.ZenRule(obtain);
            } finally {
                obtain.recycle();
            }
        }

        public boolean isAutomaticActive() {
            return this.enabled && !this.snoozing && getPkg() != null && isTrueOrUnknown();
        }

        public java.lang.String getPkg() {
            if (!android.text.TextUtils.isEmpty(this.pkg)) {
                return this.pkg;
            }
            if (this.component != null) {
                return this.component.getPackageName();
            }
            if (this.configurationActivity != null) {
                return this.configurationActivity.getPackageName();
            }
            return null;
        }

        public boolean isTrueOrUnknown() {
            return this.condition != null && (this.condition.state == 1 || this.condition.state == 2);
        }
    }

    public static boolean areAllPriorityOnlyRingerSoundsMuted(android.app.NotificationManager.Policy policy) {
        boolean z = (policy.priorityCategories & 1) != 0;
        boolean z2 = (policy.priorityCategories & 8) != 0;
        boolean z3 = (policy.priorityCategories & 4) != 0;
        boolean z4 = (policy.priorityCategories & 2) != 0;
        boolean z5 = (policy.priorityCategories & 16) != 0;
        boolean z6 = (policy.priorityConversationSenders & 256) != 0;
        boolean z7 = (policy.state & 1) != 0;
        if (android.app.Flags.modesApi()) {
            z7 = policy.hasPriorityChannels() && policy.allowPriorityChannels();
        }
        return (z || z2 || z3 || z4 || z5 || z7 || ((policy.priorityCategories & 128) != 0) || z6) ? false : true;
    }

    public static boolean areAllZenBehaviorSoundsMuted(android.app.NotificationManager.Policy policy) {
        return (((policy.priorityCategories & 32) != 0) || ((policy.priorityCategories & 64) != 0) || !areAllPriorityOnlyRingerSoundsMuted(policy)) ? false : true;
    }

    public static boolean isZenOverridingRinger(int i, android.app.NotificationManager.Policy policy) {
        if (i == 2 || i == 3) {
            return true;
        }
        return i == 1 && areAllPriorityOnlyRingerSoundsMuted(policy);
    }

    public static boolean areAllPriorityOnlyRingerSoundsMuted(android.service.notification.ZenModeConfig zenModeConfig) {
        boolean z = zenModeConfig.areChannelsBypassingDnd;
        if (android.app.Flags.modesApi()) {
            z = zenModeConfig.areChannelsBypassingDnd && zenModeConfig.allowPriorityChannels;
        }
        return (zenModeConfig.allowReminders || zenModeConfig.allowCalls || zenModeConfig.allowMessages || zenModeConfig.allowEvents || zenModeConfig.allowRepeatCallers || z || zenModeConfig.allowSystem) ? false : true;
    }

    public static boolean areAllZenBehaviorSoundsMuted(android.service.notification.ZenModeConfig zenModeConfig) {
        return (zenModeConfig.allowAlarms || zenModeConfig.allowMedia || !areAllPriorityOnlyRingerSoundsMuted(zenModeConfig)) ? false : true;
    }

    public static java.lang.String getDescription(android.content.Context context, boolean z, android.service.notification.ZenModeConfig zenModeConfig, boolean z2) {
        java.lang.String str;
        if (!z || zenModeConfig == null) {
            return null;
        }
        long j = -1;
        if (zenModeConfig.manualRule == null) {
            str = "";
        } else {
            android.net.Uri uri = zenModeConfig.manualRule.conditionId;
            if (zenModeConfig.manualRule.enabler != null) {
                str = getOwnerCaption(context, zenModeConfig.manualRule.enabler);
                if (str.isEmpty()) {
                    str = "";
                }
            } else {
                if (uri == null) {
                    if (!z2) {
                        return null;
                    }
                    return context.getString(com.android.internal.R.string.zen_mode_forever);
                }
                j = tryParseCountdownConditionId(uri);
                if (j <= 0) {
                    str = "";
                } else {
                    str = context.getString(com.android.internal.R.string.zen_mode_until, getFormattedTime(context, j, isToday(j), context.getUserId()));
                }
            }
        }
        for (android.service.notification.ZenModeConfig.ZenRule zenRule : zenModeConfig.automaticRules.values()) {
            if (zenRule.isAutomaticActive()) {
                if (isValidEventConditionId(zenRule.conditionId) || isValidScheduleConditionId(zenRule.conditionId)) {
                    long parseAutomaticRuleEndTime = parseAutomaticRuleEndTime(context, zenRule.conditionId);
                    if (parseAutomaticRuleEndTime > j) {
                        str = zenRule.name;
                        j = parseAutomaticRuleEndTime;
                    }
                } else {
                    return zenRule.name;
                }
            }
        }
        if (str.equals("")) {
            return null;
        }
        return str;
    }

    private static long parseAutomaticRuleEndTime(android.content.Context context, android.net.Uri uri) {
        if (isValidEventConditionId(uri)) {
            return Long.MAX_VALUE;
        }
        if (isValidScheduleConditionId(uri)) {
            android.service.notification.ScheduleCalendar scheduleCalendar = toScheduleCalendar(uri);
            long nextChangeTime = scheduleCalendar.getNextChangeTime(java.lang.System.currentTimeMillis());
            if (scheduleCalendar.exitAtAlarm()) {
                long nextAlarm = getNextAlarm(context);
                scheduleCalendar.maybeSetNextAlarm(java.lang.System.currentTimeMillis(), nextAlarm);
                if (scheduleCalendar.shouldExitForAlarm(nextChangeTime)) {
                    return nextAlarm;
                }
            }
            return nextChangeTime;
        }
        return -1L;
    }

    private static long getNextAlarm(android.content.Context context) {
        android.app.AlarmManager.AlarmClockInfo nextAlarmClock = ((android.app.AlarmManager) context.getSystemService("alarm")).getNextAlarmClock(context.getUserId());
        if (nextAlarmClock != null) {
            return nextAlarmClock.getTriggerTime();
        }
        return 0L;
    }
}
