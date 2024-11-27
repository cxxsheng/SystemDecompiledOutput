package com.android.modules.expresslog;

/* loaded from: classes5.dex */
public final class StatsExpressLog {
    public static final byte ANNOTATION_ID_DEFAULT_STATE = 6;
    public static final byte ANNOTATION_ID_EXCLUSIVE_STATE = 4;
    public static final byte ANNOTATION_ID_IS_UID = 1;
    public static final byte ANNOTATION_ID_PRIMARY_FIELD = 3;
    public static final byte ANNOTATION_ID_PRIMARY_FIELD_FIRST_UID = 5;
    public static final byte ANNOTATION_ID_STATE_NESTED = 8;
    public static final byte ANNOTATION_ID_TRIGGER_STATE_RESET = 7;
    public static final byte ANNOTATION_ID_TRUNCATE_TIMESTAMP = 2;
    public static final int EXPRESS_EVENT_REPORTED = 528;
    public static final int EXPRESS_HISTOGRAM_SAMPLE_REPORTED = 593;
    public static final int EXPRESS_UID_EVENT_REPORTED = 644;
    public static final int EXPRESS_UID_HISTOGRAM_SAMPLE_REPORTED = 658;

    public static void write(int i, long j, long j2) {
        android.util.StatsEvent.Builder newBuilder = android.util.StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeLong(j);
        newBuilder.writeLong(j2);
        newBuilder.usePooledBuffer();
        android.util.StatsLog.write(newBuilder.build());
    }

    public static void write(int i, long j, long j2, int i2) {
        android.util.StatsEvent.Builder newBuilder = android.util.StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeLong(j);
        newBuilder.writeLong(j2);
        newBuilder.writeInt(i2);
        if (644 == i) {
            newBuilder.addBooleanAnnotation((byte) 1, true);
        }
        newBuilder.usePooledBuffer();
        android.util.StatsLog.write(newBuilder.build());
    }

    public static void write(int i, long j, long j2, int i2, int i3) {
        android.util.StatsEvent.Builder newBuilder = android.util.StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeLong(j);
        newBuilder.writeLong(j2);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        if (658 == i) {
            newBuilder.addBooleanAnnotation((byte) 1, true);
        }
        newBuilder.usePooledBuffer();
        android.util.StatsLog.write(newBuilder.build());
    }
}
