package com.android.server.pm.verify.domain;

/* loaded from: classes2.dex */
public interface DomainVerificationManagerInternal {
    public static final int APPROVAL_LEVEL_DISABLED = -3;
    public static final int APPROVAL_LEVEL_INSTANT_APP = 5;
    public static final int APPROVAL_LEVEL_LEGACY_ALWAYS = 2;
    public static final int APPROVAL_LEVEL_LEGACY_ASK = 1;
    public static final int APPROVAL_LEVEL_NONE = 0;
    public static final int APPROVAL_LEVEL_NOT_INSTALLED = -4;
    public static final int APPROVAL_LEVEL_SELECTION = 3;
    public static final int APPROVAL_LEVEL_UNDECLARED = -2;
    public static final int APPROVAL_LEVEL_UNVERIFIED = -1;
    public static final int APPROVAL_LEVEL_VERIFIED = 4;
    public static final java.util.UUID DISABLED_ID = new java.util.UUID(0, 0);

    public @interface ApprovalLevel {
    }

    public interface Connection extends com.android.server.pm.verify.domain.DomainVerificationEnforcer.Callback {
        int[] getAllUserIds();

        int getCallingUid();

        int getCallingUserId();

        void schedule(int i, @android.annotation.Nullable java.lang.Object obj);

        void scheduleWriteSettings();

        @android.annotation.NonNull
        com.android.server.pm.Computer snapshot();
    }

    void addLegacySetting(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.IntentFilterVerificationInfo intentFilterVerificationInfo);

    void addPackage(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, @android.annotation.Nullable android.content.pm.verify.domain.DomainSet domainSet);

    @com.android.server.pm.verify.domain.DomainVerificationManagerInternal.ApprovalLevel
    int approvalLevelForDomain(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, @android.annotation.NonNull android.content.Intent intent, long j, int i);

    void clearPackage(@android.annotation.NonNull java.lang.String str);

    void clearPackageForUser(@android.annotation.NonNull java.lang.String str, int i);

    void clearUser(int i);

    @android.annotation.NonNull
    android.util.Pair<java.util.List<android.content.pm.ResolveInfo>, java.lang.Integer> filterToApprovedApp(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull java.util.List<android.content.pm.ResolveInfo> list, int i, @android.annotation.NonNull java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function);

    @android.annotation.NonNull
    java.util.UUID generateNewId();

    @android.annotation.NonNull
    com.android.server.pm.verify.domain.DomainVerificationCollector getCollector();

    @android.annotation.RequiresPermission(anyOf = {"android.permission.DOMAIN_VERIFICATION_AGENT", "android.permission.UPDATE_DOMAIN_VERIFICATION_USER_SELECTION"})
    @android.annotation.Nullable
    android.content.pm.verify.domain.DomainVerificationInfo getDomainVerificationInfo(@android.annotation.NonNull java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException;

    @android.annotation.Nullable
    java.util.UUID getDomainVerificationInfoId(@android.annotation.NonNull java.lang.String str);

    int getLegacyState(@android.annotation.NonNull java.lang.String str, int i);

    @android.annotation.NonNull
    com.android.server.pm.verify.domain.proxy.DomainVerificationProxy getProxy();

    @android.annotation.NonNull
    com.android.server.pm.verify.domain.DomainVerificationShell getShell();

    void migrateState(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal2, @android.annotation.Nullable android.content.pm.verify.domain.DomainSet domainSet);

    void printState(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.Integer num) throws android.content.pm.PackageManager.NameNotFoundException;

    void readLegacySettings(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException;

    void readSettings(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException;

    void restoreSettings(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException;

    boolean runMessage(int i, java.lang.Object obj);

    void setConnection(@android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationManagerInternal.Connection connection);

    @android.content.pm.verify.domain.DomainVerificationManager.Error
    @android.annotation.RequiresPermission("android.permission.DOMAIN_VERIFICATION_AGENT")
    int setDomainVerificationStatusInternal(int i, @android.annotation.NonNull java.util.UUID uuid, @android.annotation.NonNull java.util.Set<java.lang.String> set, int i2) throws android.content.pm.PackageManager.NameNotFoundException;

    boolean setLegacyUserState(@android.annotation.NonNull java.lang.String str, int i, int i2);

    void setProxy(@android.annotation.NonNull com.android.server.pm.verify.domain.proxy.DomainVerificationProxy domainVerificationProxy);

    void writeSettings(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, boolean z, int i) throws java.io.IOException;

    static java.lang.String approvalLevelToDebugString(@com.android.server.pm.verify.domain.DomainVerificationManagerInternal.ApprovalLevel int i) {
        switch (i) {
            case -4:
                return "NOT_INSTALLED";
            case -3:
                return "DISABLED";
            case -2:
                return "UNDECLARED";
            case -1:
                return "UNVERIFIED";
            case 0:
                return "NONE";
            case 1:
                return "LEGACY_ASK";
            case 2:
                return "LEGACY_ALWAYS";
            case 3:
                return "USER_SELECTION";
            case 4:
                return "VERIFIED";
            case 5:
                return "INSTANT_APP";
            default:
                return "UNKNOWN";
        }
    }
}
