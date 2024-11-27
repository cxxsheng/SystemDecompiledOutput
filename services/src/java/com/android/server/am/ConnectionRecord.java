package com.android.server.am;

/* loaded from: classes.dex */
final class ConnectionRecord {
    private static final int[] BIND_ORIG_ENUMS = {1, 2, 4, 8388608, 8, 16, 32, 64, 128, 33554432, 67108864, 134217728, 268435456, 536870912, 1073741824, 256, 4096, 512};
    private static final int[] BIND_PROTO_ENUMS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 18};
    final com.android.server.wm.ActivityServiceConnectionsHolder<com.android.server.am.ConnectionRecord> activity;

    @android.annotation.Nullable
    final android.content.ComponentName aliasComponent;
    public com.android.internal.app.procstats.AssociationState.SourceState association;
    final com.android.server.am.AppBindRecord binding;
    final android.app.PendingIntent clientIntent;
    final int clientLabel;
    final java.lang.String clientPackageName;
    final java.lang.String clientProcessName;
    final int clientUid;
    final android.app.IServiceConnection conn;
    private final long flags;
    private java.lang.Object mProcStatsLock;
    boolean serviceDead;
    java.lang.String stringName;

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "binding=" + this.binding);
        if (this.activity != null) {
            this.activity.dump(printWriter, str);
        }
        printWriter.println(str + "conn=" + this.conn.asBinder() + " flags=0x" + java.lang.Long.toHexString(this.flags));
    }

    ConnectionRecord(com.android.server.am.AppBindRecord appBindRecord, com.android.server.wm.ActivityServiceConnectionsHolder<com.android.server.am.ConnectionRecord> activityServiceConnectionsHolder, android.app.IServiceConnection iServiceConnection, long j, int i, android.app.PendingIntent pendingIntent, int i2, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName) {
        this.binding = appBindRecord;
        this.activity = activityServiceConnectionsHolder;
        this.conn = iServiceConnection;
        this.flags = j;
        this.clientLabel = i;
        this.clientIntent = pendingIntent;
        this.clientUid = i2;
        this.clientProcessName = str;
        this.clientPackageName = str2;
        this.aliasComponent = componentName;
    }

    public long getFlags() {
        return this.flags;
    }

    public boolean hasFlag(int i) {
        return (this.flags & java.lang.Integer.toUnsignedLong(i)) != 0;
    }

    public boolean hasFlag(long j) {
        return (j & this.flags) != 0;
    }

    public boolean notHasFlag(int i) {
        return !hasFlag(i);
    }

    public boolean notHasFlag(long j) {
        return !hasFlag(j);
    }

    public void startAssociationIfNeeded() {
        if (this.association == null && this.binding.service.app != null) {
            if (this.binding.service.appInfo.uid != this.clientUid || !this.binding.service.processName.equals(this.clientProcessName)) {
                com.android.internal.app.procstats.ProcessStats.ProcessStateHolder processStateHolder = this.binding.service.app.getPkgList().get(this.binding.service.instanceName.getPackageName());
                if (processStateHolder == null) {
                    android.util.Slog.wtf("ActivityManager", "No package in referenced service " + this.binding.service.shortInstanceName + ": proc=" + this.binding.service.app);
                    return;
                }
                if (processStateHolder.pkg == null) {
                    android.util.Slog.wtf("ActivityManager", "Inactive holder in referenced service " + this.binding.service.shortInstanceName + ": proc=" + this.binding.service.app);
                    return;
                }
                this.mProcStatsLock = this.binding.service.app.mService.mProcessStats.mLock;
                synchronized (this.mProcStatsLock) {
                    this.association = processStateHolder.pkg.getAssociationStateLocked(processStateHolder.state, this.binding.service.instanceName.getClassName()).startSource(this.clientUid, this.clientProcessName, this.clientPackageName);
                }
            }
        }
    }

    public void trackProcState(int i, int i2) {
        if (this.association != null) {
            synchronized (this.mProcStatsLock) {
                this.association.trackProcState(i, i2, android.os.SystemClock.uptimeMillis());
            }
        }
    }

    public void stopAssociation() {
        if (this.association != null) {
            synchronized (this.mProcStatsLock) {
                this.association.stop();
            }
            this.association = null;
        }
    }

    public java.lang.String toString() {
        if (this.stringName != null) {
            return this.stringName;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("ConnectionRecord{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(" u");
        sb.append(this.binding.client.userId);
        sb.append(' ');
        if (hasFlag(1)) {
            sb.append("CR ");
        }
        if (hasFlag(2)) {
            sb.append("DBG ");
        }
        if (hasFlag(4)) {
            sb.append("!FG ");
        }
        if (hasFlag(8388608)) {
            sb.append("IMPB ");
        }
        if (hasFlag(8)) {
            sb.append("ABCLT ");
        }
        if (hasFlag(16)) {
            sb.append("OOM ");
        }
        if (hasFlag(32)) {
            sb.append("WPRI ");
        }
        if (hasFlag(64)) {
            sb.append("IMP ");
        }
        if (hasFlag(128)) {
            sb.append("WACT ");
        }
        if (hasFlag(33554432)) {
            sb.append("FGSA ");
        }
        if (hasFlag(67108864)) {
            sb.append("FGS ");
        }
        if (hasFlag(134217728)) {
            sb.append("LACT ");
        }
        if (hasFlag(524288)) {
            sb.append("SLTA ");
        }
        if (hasFlag(268435456)) {
            sb.append("VFGS ");
        }
        if (hasFlag(536870912)) {
            sb.append("UI ");
        }
        if (hasFlag(1073741824)) {
            sb.append("!VIS ");
        }
        if (hasFlag(256)) {
            sb.append("!PRCP ");
        }
        if (hasFlag(512)) {
            sb.append("BALF ");
        }
        if (hasFlag(4096)) {
            sb.append("CAPS ");
        }
        if (this.serviceDead) {
            sb.append("DEAD ");
        }
        sb.append(this.binding.service.shortInstanceName);
        sb.append(":@");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.conn.asBinder())));
        sb.append(" flags=0x" + java.lang.Long.toHexString(this.flags));
        sb.append('}');
        java.lang.String sb2 = sb.toString();
        this.stringName = sb2;
        return sb2;
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        if (this.binding == null) {
            return;
        }
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        if (this.binding.client != null) {
            protoOutputStream.write(1120986464258L, this.binding.client.userId);
        }
        android.util.proto.ProtoUtils.writeBitWiseFlagsToProtoEnum(protoOutputStream, 2259152797699L, this.flags, BIND_ORIG_ENUMS, BIND_PROTO_ENUMS);
        if (this.serviceDead) {
            protoOutputStream.write(2259152797699L, 15);
        }
        if (this.binding.service != null) {
            protoOutputStream.write(1138166333444L, this.binding.service.shortInstanceName);
        }
        protoOutputStream.end(start);
    }
}
