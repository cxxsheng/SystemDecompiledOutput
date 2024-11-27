package android.provider;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class TimeZoneRulesDataContract {
    public static final java.lang.String AUTHORITY = "com.android.timezone";
    private static final android.net.Uri AUTHORITY_URI = android.net.Uri.parse("content://com.android.timezone");

    private TimeZoneRulesDataContract() {
    }

    public static final class Operation {
        public static final java.lang.String COLUMN_DISTRO_MAJOR_VERSION = "distro_major_version";
        public static final java.lang.String COLUMN_DISTRO_MINOR_VERSION = "distro_minor_version";
        public static final java.lang.String COLUMN_REVISION = "revision";
        public static final java.lang.String COLUMN_RULES_VERSION = "rules_version";
        public static final java.lang.String COLUMN_TYPE = "type";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.TimeZoneRulesDataContract.AUTHORITY_URI, "operation");
        public static final java.lang.String TYPE_INSTALL = "INSTALL";
        public static final java.lang.String TYPE_NO_OP = "NOOP";
        public static final java.lang.String TYPE_UNINSTALL = "UNINSTALL";

        private Operation() {
        }
    }
}
