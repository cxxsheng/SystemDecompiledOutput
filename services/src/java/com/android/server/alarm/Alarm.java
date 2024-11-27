package com.android.server.alarm;

/* loaded from: classes.dex */
class Alarm {
    public static final int APP_STANDBY_POLICY_INDEX = 1;
    public static final int BATTERY_SAVER_POLICY_INDEX = 3;
    public static final int DEVICE_IDLE_POLICY_INDEX = 2;
    static final int EXACT_ALLOW_REASON_ALLOW_LIST = 1;
    static final int EXACT_ALLOW_REASON_COMPAT = 2;
    static final int EXACT_ALLOW_REASON_LISTENER = 4;
    static final int EXACT_ALLOW_REASON_NOT_APPLICABLE = -1;
    static final int EXACT_ALLOW_REASON_PERMISSION = 0;
    static final int EXACT_ALLOW_REASON_POLICY_PERMISSION = 3;
    static final int EXACT_ALLOW_REASON_PRIORITIZED = 5;

    @com.android.internal.annotations.VisibleForTesting
    public static final int NUM_POLICIES = 5;
    public static final int REQUESTER_POLICY_INDEX = 0;
    public static final int TARE_POLICY_INDEX = 4;
    public final android.app.AlarmManager.AlarmClockInfo alarmClock;
    public int count;
    public final int creatorUid;
    public int exactAllowReason;
    public final int flags;
    public final android.app.IAlarmListener listener;
    public final java.lang.String listenerTag;
    public android.os.Bundle mIdleOptions;
    private long mMaxWhenElapsed;
    private long[] mPolicyWhenElapsed;
    public boolean mUsingReserveQuota;
    private long mWhenElapsed;
    public final android.app.PendingIntent operation;
    public final long origWhen;
    public final java.lang.String packageName;
    public int priorityClass;
    public final long repeatInterval;
    public final java.lang.String sourcePackage;
    public final java.lang.String statsTag;
    public final int type;
    public final int uid;
    public final boolean wakeup;
    public final long windowLength;
    public final android.os.WorkSource workSource;

    Alarm(int i, long j, long j2, long j3, long j4, android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener, java.lang.String str, android.os.WorkSource workSource, int i2, android.app.AlarmManager.AlarmClockInfo alarmClockInfo, int i3, java.lang.String str2, android.os.Bundle bundle, int i4) {
        this.type = i;
        this.origWhen = j;
        this.wakeup = i == 2 || i == 0;
        this.mPolicyWhenElapsed = new long[5];
        this.mPolicyWhenElapsed[0] = j2;
        this.mWhenElapsed = j2;
        this.windowLength = j3;
        this.mMaxWhenElapsed = com.android.server.alarm.AlarmManagerService.addClampPositive(j2, j3);
        this.repeatInterval = j4;
        this.operation = pendingIntent;
        this.listener = iAlarmListener;
        this.listenerTag = str;
        this.statsTag = makeTag(pendingIntent, str, i);
        this.workSource = workSource;
        this.flags = i2;
        this.alarmClock = alarmClockInfo;
        this.uid = i3;
        this.packageName = str2;
        this.mIdleOptions = bundle;
        this.exactAllowReason = i4;
        this.sourcePackage = this.operation != null ? this.operation.getCreatorPackage() : this.packageName;
        this.creatorUid = this.operation != null ? this.operation.getCreatorUid() : this.uid;
        this.mUsingReserveQuota = false;
        this.priorityClass = 2;
    }

    public static java.lang.String makeTag(android.app.PendingIntent pendingIntent, java.lang.String str, int i) {
        java.lang.String str2 = (i == 2 || i == 0) ? "*walarm*:" : "*alarm*:";
        if (pendingIntent != null) {
            return pendingIntent.getTag(str2);
        }
        return str2 + str;
    }

    public boolean matches(android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener) {
        if (this.operation != null) {
            return this.operation.equals(pendingIntent);
        }
        return iAlarmListener != null && this.listener.asBinder().equals(iAlarmListener.asBinder());
    }

    public boolean matches(java.lang.String str) {
        return str.equals(this.sourcePackage);
    }

    @com.android.internal.annotations.VisibleForTesting
    long getPolicyElapsed(int i) {
        return this.mPolicyWhenElapsed[i];
    }

    public long getRequestedElapsed() {
        return this.mPolicyWhenElapsed[0];
    }

    public long getWhenElapsed() {
        return this.mWhenElapsed;
    }

    public long getMaxWhenElapsed() {
        return this.mMaxWhenElapsed;
    }

    public boolean setPolicyElapsed(int i, long j) {
        this.mPolicyWhenElapsed[i] = j;
        return updateWhenElapsed();
    }

    private boolean updateWhenElapsed() {
        long j = this.mWhenElapsed;
        this.mWhenElapsed = 0L;
        for (int i = 0; i < 5; i++) {
            this.mWhenElapsed = java.lang.Math.max(this.mWhenElapsed, this.mPolicyWhenElapsed[i]);
        }
        long j2 = this.mMaxWhenElapsed;
        this.mMaxWhenElapsed = java.lang.Math.max(com.android.server.alarm.AlarmManagerService.addClampPositive(this.mPolicyWhenElapsed[0], this.windowLength), this.mWhenElapsed);
        return (j == this.mWhenElapsed && j2 == this.mMaxWhenElapsed) ? false : true;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("Alarm{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(" type ");
        sb.append(this.type);
        sb.append(" origWhen ");
        sb.append(this.origWhen);
        sb.append(" whenElapsed ");
        sb.append(getWhenElapsed());
        sb.append(" ");
        sb.append(this.sourcePackage);
        sb.append('}');
        return sb.toString();
    }

    static java.lang.String policyIndexToString(int i) {
        switch (i) {
            case 0:
                return "requester";
            case 1:
                return "app_standby";
            case 2:
                return "device_idle";
            case 3:
                return "battery_saver";
            case 4:
                return "tare";
            default:
                return "--unknown(" + i + ")--";
        }
    }

    private static java.lang.String exactReasonToString(int i) {
        switch (i) {
            case -1:
                return "N/A";
            case 0:
                return "permission";
            case 1:
                return "allow-listed";
            case 2:
                return "compat";
            case 3:
                return "policy_permission";
            case 4:
                return "listener";
            case 5:
                return "prioritized";
            default:
                return "--unknown--";
        }
    }

    public static java.lang.String typeToString(int i) {
        switch (i) {
            case 0:
                return "RTC_WAKEUP";
            case 1:
                return "RTC";
            case 2:
                return "ELAPSED_WAKEUP";
            case 3:
                return "ELAPSED";
            default:
                return "--unknown--";
        }
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter, long j, java.text.SimpleDateFormat simpleDateFormat) {
        boolean z = true;
        if (this.type != 1 && this.type != 0) {
            z = false;
        }
        indentingPrintWriter.print("tag=");
        indentingPrintWriter.println(this.statsTag);
        indentingPrintWriter.print("type=");
        indentingPrintWriter.print(typeToString(this.type));
        indentingPrintWriter.print(" origWhen=");
        if (z) {
            indentingPrintWriter.print(simpleDateFormat.format(new java.util.Date(this.origWhen)));
        } else {
            android.util.TimeUtils.formatDuration(this.origWhen, j, indentingPrintWriter);
        }
        indentingPrintWriter.print(" window=");
        android.util.TimeUtils.formatDuration(this.windowLength, indentingPrintWriter);
        if (this.exactAllowReason != -1) {
            indentingPrintWriter.print(" exactAllowReason=");
            indentingPrintWriter.print(exactReasonToString(this.exactAllowReason));
        }
        indentingPrintWriter.print(" repeatInterval=");
        indentingPrintWriter.print(this.repeatInterval);
        indentingPrintWriter.print(" count=");
        indentingPrintWriter.print(this.count);
        indentingPrintWriter.print(" flags=0x");
        indentingPrintWriter.println(java.lang.Integer.toHexString(this.flags));
        indentingPrintWriter.print("policyWhenElapsed:");
        for (int i = 0; i < 5; i++) {
            indentingPrintWriter.print(" " + policyIndexToString(i) + "=");
            android.util.TimeUtils.formatDuration(this.mPolicyWhenElapsed[i], j, indentingPrintWriter);
        }
        indentingPrintWriter.println();
        indentingPrintWriter.print("whenElapsed=");
        android.util.TimeUtils.formatDuration(getWhenElapsed(), j, indentingPrintWriter);
        indentingPrintWriter.print(" maxWhenElapsed=");
        android.util.TimeUtils.formatDuration(this.mMaxWhenElapsed, j, indentingPrintWriter);
        if (this.mUsingReserveQuota) {
            indentingPrintWriter.print(" usingReserveQuota=true");
        }
        indentingPrintWriter.println();
        if (this.alarmClock != null) {
            indentingPrintWriter.println("Alarm clock:");
            indentingPrintWriter.print("  triggerTime=");
            indentingPrintWriter.println(simpleDateFormat.format(new java.util.Date(this.alarmClock.getTriggerTime())));
            indentingPrintWriter.print("  showIntent=");
            indentingPrintWriter.println(this.alarmClock.getShowIntent());
        }
        if (this.operation != null) {
            indentingPrintWriter.print("operation=");
            indentingPrintWriter.println(this.operation);
        }
        if (this.listener != null) {
            indentingPrintWriter.print("listener=");
            indentingPrintWriter.println(this.listener.asBinder());
        }
        if (this.mIdleOptions != null) {
            indentingPrintWriter.print("idle-options=");
            indentingPrintWriter.println(this.mIdleOptions.toString());
        }
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.statsTag);
        protoOutputStream.write(1159641169922L, this.type);
        protoOutputStream.write(1112396529667L, getWhenElapsed() - j2);
        protoOutputStream.write(1112396529668L, this.windowLength);
        protoOutputStream.write(1112396529669L, this.repeatInterval);
        protoOutputStream.write(1120986464262L, this.count);
        protoOutputStream.write(1120986464263L, this.flags);
        if (this.alarmClock != null) {
            this.alarmClock.dumpDebug(protoOutputStream, 1146756268040L);
        }
        if (this.operation != null) {
            this.operation.dumpDebug(protoOutputStream, 1146756268041L);
        }
        if (this.listener != null) {
            protoOutputStream.write(1138166333450L, this.listener.asBinder().toString());
        }
        protoOutputStream.end(start);
    }

    static class Snapshot {
        final long[] mPolicyWhenElapsed;
        final java.lang.String mTag;
        final int mType;

        Snapshot(com.android.server.alarm.Alarm alarm) {
            this.mType = alarm.type;
            this.mTag = alarm.statsTag;
            this.mPolicyWhenElapsed = java.util.Arrays.copyOf(alarm.mPolicyWhenElapsed, 5);
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter, long j) {
            indentingPrintWriter.print(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, com.android.server.alarm.Alarm.typeToString(this.mType));
            indentingPrintWriter.print("tag", this.mTag);
            indentingPrintWriter.println();
            indentingPrintWriter.print("policyWhenElapsed:");
            for (int i = 0; i < 5; i++) {
                indentingPrintWriter.print(" " + com.android.server.alarm.Alarm.policyIndexToString(i) + "=");
                android.util.TimeUtils.formatDuration(this.mPolicyWhenElapsed[i], j, indentingPrintWriter);
            }
            indentingPrintWriter.println();
        }
    }
}
