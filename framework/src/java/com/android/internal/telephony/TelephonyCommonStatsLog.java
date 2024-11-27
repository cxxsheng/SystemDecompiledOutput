package com.android.internal.telephony;

/* loaded from: classes5.dex */
public final class TelephonyCommonStatsLog {
    public static final byte ANNOTATION_ID_DEFAULT_STATE = 6;
    public static final byte ANNOTATION_ID_EXCLUSIVE_STATE = 4;
    public static final byte ANNOTATION_ID_IS_UID = 1;
    public static final byte ANNOTATION_ID_PRIMARY_FIELD = 3;
    public static final byte ANNOTATION_ID_PRIMARY_FIELD_FIRST_UID = 5;
    public static final byte ANNOTATION_ID_STATE_NESTED = 8;
    public static final byte ANNOTATION_ID_TRIGGER_STATE_RESET = 7;
    public static final byte ANNOTATION_ID_TRUNCATE_TIMESTAMP = 2;
    public static final int DEVICE_IDENTIFIER_ACCESS_DENIED = 172;

    public static void write(int i, java.lang.String str, java.lang.String str2, boolean z, boolean z2) {
        android.util.StatsEvent.Builder newBuilder = android.util.StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeString(str);
        newBuilder.writeString(str2);
        newBuilder.writeBoolean(z);
        newBuilder.writeBoolean(z2);
        newBuilder.usePooledBuffer();
        android.util.StatsLog.write(newBuilder.build());
    }
}
