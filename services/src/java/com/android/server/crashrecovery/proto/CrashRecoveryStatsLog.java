package com.android.server.crashrecovery.proto;

/* loaded from: classes.dex */
public final class CrashRecoveryStatsLog {
    public static final byte ANNOTATION_ID_DEFAULT_STATE = 6;
    public static final byte ANNOTATION_ID_EXCLUSIVE_STATE = 4;
    public static final byte ANNOTATION_ID_IS_UID = 1;
    public static final byte ANNOTATION_ID_PRIMARY_FIELD = 3;
    public static final byte ANNOTATION_ID_PRIMARY_FIELD_FIRST_UID = 5;
    public static final byte ANNOTATION_ID_STATE_NESTED = 8;
    public static final byte ANNOTATION_ID_TRIGGER_STATE_RESET = 7;
    public static final byte ANNOTATION_ID_TRUNCATE_TIMESTAMP = 2;
    public static final int RESCUE_PARTY_RESET_REPORTED = 122;
    public static final int WATCHDOG_ROLLBACK_OCCURRED = 147;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_REASON__REASON_APP_CRASH = 3;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_REASON__REASON_APP_NOT_RESPONDING = 4;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_REASON__REASON_BOOT_LOOPING = 7;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_REASON__REASON_EXPLICIT_HEALTH_CHECK = 2;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_REASON__REASON_NATIVE_CRASH = 1;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_REASON__REASON_NATIVE_CRASH_DURING_BOOT = 5;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_REASON__REASON_NETWORK_RELATED_CRASH = 6;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_REASON__REASON_UNKNOWN = 0;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_TYPE__ROLLBACK_BOOT_TRIGGERED = 4;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_TYPE__ROLLBACK_FAILURE = 3;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_TYPE__ROLLBACK_INITIATE = 1;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_TYPE__ROLLBACK_SUCCESS = 2;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_TYPE__UNKNOWN = 0;

    public static void write(int i, int i2, java.lang.String str) {
        android.util.StatsEvent.Builder newBuilder = android.util.StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeInt(i2);
        newBuilder.writeString(str);
        newBuilder.usePooledBuffer();
        android.util.StatsLog.write(newBuilder.build());
    }

    public static void write(int i, int i2, java.lang.String str, int i3, int i4, java.lang.String str2, byte[] bArr) {
        android.util.StatsEvent.Builder newBuilder = android.util.StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeInt(i2);
        newBuilder.writeString(str);
        newBuilder.writeInt(i3);
        newBuilder.writeInt(i4);
        newBuilder.writeString(str2);
        if (bArr == null) {
            bArr = new byte[0];
        }
        newBuilder.writeByteArray(bArr);
        newBuilder.usePooledBuffer();
        android.util.StatsLog.write(newBuilder.build());
    }
}
