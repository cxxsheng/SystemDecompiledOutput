package com.android.server.am;

/* loaded from: classes.dex */
public final class HostingRecord {
    private static final int APP_ZYGOTE = 2;
    public static final java.lang.String HOSTING_TYPE_ACTIVITY = "activity";
    public static final java.lang.String HOSTING_TYPE_ADDED_APPLICATION = "added application";
    public static final java.lang.String HOSTING_TYPE_BACKUP = "backup";
    public static final java.lang.String HOSTING_TYPE_BROADCAST = "broadcast";
    public static final java.lang.String HOSTING_TYPE_CONTENT_PROVIDER = "content provider";
    public static final java.lang.String HOSTING_TYPE_EMPTY = "";
    public static final java.lang.String HOSTING_TYPE_LINK_FAIL = "link fail";
    public static final java.lang.String HOSTING_TYPE_NEXT_ACTIVITY = "next-activity";
    public static final java.lang.String HOSTING_TYPE_NEXT_TOP_ACTIVITY = "next-top-activity";
    public static final java.lang.String HOSTING_TYPE_ON_HOLD = "on-hold";
    public static final java.lang.String HOSTING_TYPE_RESTART = "restart";
    public static final java.lang.String HOSTING_TYPE_SERVICE = "service";
    public static final java.lang.String HOSTING_TYPE_SYSTEM = "system";
    public static final java.lang.String HOSTING_TYPE_TOP_ACTIVITY = "top-activity";
    private static final int REGULAR_ZYGOTE = 0;
    public static final java.lang.String TRIGGER_TYPE_ALARM = "alarm";
    public static final java.lang.String TRIGGER_TYPE_JOB = "job";
    public static final java.lang.String TRIGGER_TYPE_PUSH_MESSAGE = "push_message";
    public static final java.lang.String TRIGGER_TYPE_PUSH_MESSAGE_OVER_QUOTA = "push_message_over_quota";
    public static final java.lang.String TRIGGER_TYPE_UNKNOWN = "unknown";
    private static final int WEBVIEW_ZYGOTE = 1;

    @android.annotation.Nullable
    private final java.lang.String mAction;
    private final java.lang.String mDefiningPackageName;
    private final java.lang.String mDefiningProcessName;
    private final int mDefiningUid;
    private final java.lang.String mHostingName;

    @android.annotation.NonNull
    private final java.lang.String mHostingType;
    private final int mHostingZygote;
    private final boolean mIsTopApp;

    @android.annotation.NonNull
    private final java.lang.String mTriggerType;

    public HostingRecord(@android.annotation.NonNull java.lang.String str) {
        this(str, null, 0, null, -1, false, null, null, "unknown");
    }

    public HostingRecord(@android.annotation.NonNull java.lang.String str, android.content.ComponentName componentName) {
        this(str, componentName, 0);
    }

    public HostingRecord(@android.annotation.NonNull java.lang.String str, android.content.ComponentName componentName, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3) {
        this(str, componentName.toShortString(), 0, null, -1, false, null, str2, str3);
    }

    public HostingRecord(@android.annotation.NonNull java.lang.String str, android.content.ComponentName componentName, java.lang.String str2, int i, java.lang.String str3, java.lang.String str4) {
        this(str, componentName.toShortString(), 0, str2, i, false, str3, null, str4);
    }

    public HostingRecord(@android.annotation.NonNull java.lang.String str, android.content.ComponentName componentName, boolean z) {
        this(str, componentName.toShortString(), 0, null, -1, z, null, null, "unknown");
    }

    public HostingRecord(@android.annotation.NonNull java.lang.String str, java.lang.String str2) {
        this(str, str2, 0);
    }

    private HostingRecord(@android.annotation.NonNull java.lang.String str, android.content.ComponentName componentName, int i) {
        this(str, componentName.toShortString(), i);
    }

    private HostingRecord(@android.annotation.NonNull java.lang.String str, java.lang.String str2, int i) {
        this(str, str2, i, null, -1, false, null, null, "unknown");
    }

    private HostingRecord(@android.annotation.NonNull java.lang.String str, java.lang.String str2, int i, java.lang.String str3, int i2, boolean z, java.lang.String str4, @android.annotation.Nullable java.lang.String str5, java.lang.String str6) {
        this.mHostingType = str;
        this.mHostingName = str2;
        this.mHostingZygote = i;
        this.mDefiningPackageName = str3;
        this.mDefiningUid = i2;
        this.mIsTopApp = z;
        this.mDefiningProcessName = str4;
        this.mAction = str5;
        this.mTriggerType = str6;
    }

    @android.annotation.NonNull
    public java.lang.String getType() {
        return this.mHostingType;
    }

    public java.lang.String getName() {
        return this.mHostingName;
    }

    public boolean isTopApp() {
        return this.mIsTopApp;
    }

    public int getDefiningUid() {
        return this.mDefiningUid;
    }

    public java.lang.String getDefiningPackageName() {
        return this.mDefiningPackageName;
    }

    public java.lang.String getDefiningProcessName() {
        return this.mDefiningProcessName;
    }

    @android.annotation.Nullable
    public java.lang.String getAction() {
        return this.mAction;
    }

    @android.annotation.NonNull
    public java.lang.String getTriggerType() {
        return this.mTriggerType;
    }

    public static com.android.server.am.HostingRecord byWebviewZygote(android.content.ComponentName componentName, java.lang.String str, int i, java.lang.String str2) {
        return new com.android.server.am.HostingRecord("", componentName.toShortString(), 1, str, i, false, str2, null, "unknown");
    }

    public static com.android.server.am.HostingRecord byAppZygote(android.content.ComponentName componentName, java.lang.String str, int i, java.lang.String str2) {
        return new com.android.server.am.HostingRecord("", componentName.toShortString(), 2, str, i, false, str2, null, "unknown");
    }

    public boolean usesAppZygote() {
        return this.mHostingZygote == 2;
    }

    public boolean usesWebviewZygote() {
        return this.mHostingZygote == 1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getHostingTypeIdStatsd(@android.annotation.NonNull java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1726126969:
                if (str.equals(HOSTING_TYPE_TOP_ACTIVITY)) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1682898044:
                if (str.equals(HOSTING_TYPE_LINK_FAIL)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1655966961:
                if (str.equals(HOSTING_TYPE_ACTIVITY)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1618876223:
                if (str.equals("broadcast")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1526161119:
                if (str.equals(HOSTING_TYPE_NEXT_TOP_ACTIVITY)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1396673086:
                if (str.equals(HOSTING_TYPE_BACKUP)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1372333075:
                if (str.equals(HOSTING_TYPE_ON_HOLD)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1355707223:
                if (str.equals(HOSTING_TYPE_NEXT_ACTIVITY)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -887328209:
                if (str.equals("system")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 0:
                if (str.equals("")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 1097506319:
                if (str.equals(HOSTING_TYPE_RESTART)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1418439096:
                if (str.equals(HOSTING_TYPE_CONTENT_PROVIDER)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1637159472:
                if (str.equals(HOSTING_TYPE_ADDED_APPLICATION)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1984153269:
                if (str.equals(HOSTING_TYPE_SERVICE)) {
                    c = '\n';
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
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            case 7:
                return 8;
            case '\b':
                return 9;
            case '\t':
                return 10;
            case '\n':
                return 11;
            case 11:
                return 12;
            case '\f':
                return 13;
            case '\r':
                return 14;
            default:
                return 0;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getTriggerTypeForStatsd(@android.annotation.NonNull java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -2000959542:
                if (str.equals(TRIGGER_TYPE_PUSH_MESSAGE_OVER_QUOTA)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 105405:
                if (str.equals(TRIGGER_TYPE_JOB)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 92895825:
                if (str.equals(TRIGGER_TYPE_ALARM)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 679713762:
                if (str.equals(TRIGGER_TYPE_PUSH_MESSAGE)) {
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
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            default:
                return 0;
        }
    }
}
