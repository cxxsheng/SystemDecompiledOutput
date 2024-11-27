package com.android.server.am;

/* loaded from: classes.dex */
final class BroadcastFilter extends android.content.IntentFilter {
    final boolean exported;
    final java.lang.String featureId;
    final boolean instantApp;
    final int owningUid;
    final int owningUserId;
    final java.lang.String packageName;
    final java.lang.String receiverId;
    final com.android.server.am.ReceiverList receiverList;
    final java.lang.String requiredPermission;
    final boolean visibleToInstantApp;

    BroadcastFilter(android.content.IntentFilter intentFilter, com.android.server.am.ReceiverList receiverList, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i, int i2, boolean z, boolean z2, boolean z3) {
        super(intentFilter);
        this.receiverList = receiverList;
        this.packageName = str;
        this.featureId = str2;
        this.receiverId = str3;
        this.requiredPermission = str4;
        this.owningUid = i;
        this.owningUserId = i2;
        this.instantApp = z;
        this.visibleToInstantApp = z2;
        this.exported = z3;
    }

    @android.annotation.Nullable
    public java.lang.String getReceiverClassName() {
        int lastIndexOf;
        if (this.receiverId != null && (lastIndexOf = this.receiverId.lastIndexOf(64)) > 0) {
            return this.receiverId.substring(0, lastIndexOf);
        }
        return null;
    }

    @dalvik.annotation.optimization.NeverCompile
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L);
        if (this.requiredPermission != null) {
            protoOutputStream.write(1138166333442L, this.requiredPermission);
        }
        protoOutputStream.write(1138166333443L, java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        protoOutputStream.write(1120986464260L, this.owningUserId);
        protoOutputStream.end(start);
    }

    @dalvik.annotation.optimization.NeverCompile
    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        dumpInReceiverList(printWriter, new android.util.PrintWriterPrinter(printWriter), str);
        this.receiverList.dumpLocal(printWriter, str);
    }

    @dalvik.annotation.optimization.NeverCompile
    public void dumpBrief(java.io.PrintWriter printWriter, java.lang.String str) {
        dumpBroadcastFilterState(printWriter, str);
    }

    @dalvik.annotation.optimization.NeverCompile
    public void dumpInReceiverList(java.io.PrintWriter printWriter, android.util.Printer printer, java.lang.String str) {
        super.dump(printer, str);
        dumpBroadcastFilterState(printWriter, str);
    }

    @dalvik.annotation.optimization.NeverCompile
    void dumpBroadcastFilterState(java.io.PrintWriter printWriter, java.lang.String str) {
        if (this.requiredPermission != null) {
            printWriter.print(str);
            printWriter.print("requiredPermission=");
            printWriter.println(this.requiredPermission);
        }
    }

    public java.lang.String toString() {
        return "BroadcastFilter{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + ' ' + this.owningUid + "/u" + this.owningUserId + ' ' + this.receiverList + '}';
    }
}
