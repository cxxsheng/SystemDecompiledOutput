package com.android.server.am;

/* loaded from: classes.dex */
final class IntentBindRecord {
    final android.util.ArrayMap<com.android.server.am.ProcessRecord, com.android.server.am.AppBindRecord> apps = new android.util.ArrayMap<>();
    android.os.IBinder binder;
    boolean doRebind;
    boolean hasBound;
    final android.content.Intent.FilterComparison intent;
    boolean received;
    boolean requested;
    final com.android.server.am.ServiceRecord service;
    java.lang.String stringName;

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("service=");
        printWriter.println(this.service);
        dumpInService(printWriter, str);
    }

    void dumpInService(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("intent={");
        printWriter.print(this.intent.getIntent().toShortString(false, true, false, false));
        printWriter.println('}');
        printWriter.print(str);
        printWriter.print("binder=");
        printWriter.println(this.binder);
        printWriter.print(str);
        printWriter.print("requested=");
        printWriter.print(this.requested);
        printWriter.print(" received=");
        printWriter.print(this.received);
        printWriter.print(" hasBound=");
        printWriter.print(this.hasBound);
        printWriter.print(" doRebind=");
        printWriter.println(this.doRebind);
        for (int i = 0; i < this.apps.size(); i++) {
            com.android.server.am.AppBindRecord valueAt = this.apps.valueAt(i);
            printWriter.print(str);
            printWriter.print("* Client AppBindRecord{");
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(valueAt)));
            printWriter.print(' ');
            printWriter.print(valueAt.client);
            printWriter.println('}');
            valueAt.dumpInIntentBind(printWriter, str + "  ");
        }
    }

    IntentBindRecord(com.android.server.am.ServiceRecord serviceRecord, android.content.Intent.FilterComparison filterComparison) {
        this.service = serviceRecord;
        this.intent = filterComparison;
    }

    long collectFlags() {
        long j = 0;
        for (int size = this.apps.size() - 1; size >= 0; size--) {
            android.util.ArraySet<com.android.server.am.ConnectionRecord> arraySet = this.apps.valueAt(size).connections;
            for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                j |= arraySet.valueAt(size2).getFlags();
            }
        }
        return j;
    }

    public java.lang.String toString() {
        if (this.stringName != null) {
            return this.stringName;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("IntentBindRecord{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        if ((collectFlags() & 1) != 0) {
            sb.append("CR ");
        }
        sb.append(this.service.shortInstanceName);
        sb.append(':');
        if (this.intent != null) {
            this.intent.getIntent().toShortString(sb, false, false, false, false);
        }
        sb.append('}');
        java.lang.String sb2 = sb.toString();
        this.stringName = sb2;
        return sb2;
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        if (this.intent != null) {
            this.intent.getIntent().dumpDebug(protoOutputStream, 1146756268033L, false, true, false, false);
        }
        if (this.binder != null) {
            protoOutputStream.write(1138166333442L, this.binder.toString());
        }
        protoOutputStream.write(1133871366147L, (collectFlags() & 1) != 0);
        protoOutputStream.write(1133871366148L, this.requested);
        protoOutputStream.write(1133871366149L, this.received);
        protoOutputStream.write(1133871366150L, this.hasBound);
        protoOutputStream.write(1133871366151L, this.doRebind);
        int size = this.apps.size();
        for (int i = 0; i < size; i++) {
            com.android.server.am.AppBindRecord valueAt = this.apps.valueAt(i);
            if (valueAt != null) {
                valueAt.dumpDebug(protoOutputStream, 2246267895816L);
            }
        }
        protoOutputStream.end(start);
    }
}
