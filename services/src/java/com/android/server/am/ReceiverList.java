package com.android.server.am;

/* loaded from: classes.dex */
final class ReceiverList extends java.util.ArrayList<com.android.server.am.BroadcastFilter> implements android.os.IBinder.DeathRecipient {

    @android.annotation.Nullable
    public final com.android.server.am.ProcessRecord app;
    com.android.server.am.BroadcastRecord curBroadcast = null;
    boolean linkedToDeath = false;
    final com.android.server.am.ActivityManagerService owner;
    public final int pid;
    public final android.content.IIntentReceiver receiver;
    java.lang.String stringName;
    public final int uid;
    public final int userId;

    ReceiverList(com.android.server.am.ActivityManagerService activityManagerService, @android.annotation.Nullable com.android.server.am.ProcessRecord processRecord, int i, int i2, int i3, android.content.IIntentReceiver iIntentReceiver) {
        this.owner = activityManagerService;
        this.receiver = iIntentReceiver;
        this.app = processRecord;
        this.pid = i;
        this.uid = i2;
        this.userId = i3;
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.Collection, java.util.List
    public boolean equals(java.lang.Object obj) {
        return this == obj;
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.Collection, java.util.List
    public int hashCode() {
        return java.lang.System.identityHashCode(this);
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        this.linkedToDeath = false;
        this.owner.unregisterReceiver(this.receiver);
    }

    public boolean containsFilter(android.content.IntentFilter intentFilter) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (android.content.IntentFilter.filterEquals(get(i), intentFilter)) {
                return true;
            }
        }
        return false;
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        if (this.app != null) {
            this.app.dumpDebug(protoOutputStream, 1146756268033L);
            protoOutputStream.write(1120986464265L, this.app.mReceivers.numberOfReceivers());
        }
        protoOutputStream.write(1120986464258L, this.pid);
        protoOutputStream.write(1120986464259L, this.uid);
        protoOutputStream.write(1120986464260L, this.userId);
        if (this.curBroadcast != null) {
            this.curBroadcast.dumpDebug(protoOutputStream, 1146756268037L);
        }
        protoOutputStream.write(1133871366150L, this.linkedToDeath);
        int size = size();
        for (int i = 0; i < size; i++) {
            get(i).dumpDebug(protoOutputStream, 2246267895815L);
        }
        protoOutputStream.write(1138166333448L, java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        protoOutputStream.end(start);
    }

    void dumpLocal(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("app=");
        printWriter.print(this.app != null ? this.app.toShortString() : null);
        printWriter.print(" pid=");
        printWriter.print(this.pid);
        printWriter.print(" uid=");
        printWriter.print(this.uid);
        printWriter.print(" user=");
        printWriter.print(this.userId);
        if (this.app != null) {
            printWriter.print(" #receivers=");
            printWriter.print(this.app.mReceivers.numberOfReceivers());
        }
        printWriter.println();
        if (this.curBroadcast != null || this.linkedToDeath) {
            printWriter.print(str);
            printWriter.print("curBroadcast=");
            printWriter.print(this.curBroadcast);
            printWriter.print(" linkedToDeath=");
            printWriter.println(this.linkedToDeath);
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        android.util.PrintWriterPrinter printWriterPrinter = new android.util.PrintWriterPrinter(printWriter);
        dumpLocal(printWriter, str);
        java.lang.String str2 = str + "  ";
        int size = size();
        for (int i = 0; i < size; i++) {
            com.android.server.am.BroadcastFilter broadcastFilter = get(i);
            printWriter.print(str);
            printWriter.print("Filter #");
            printWriter.print(i);
            printWriter.print(": BroadcastFilter{");
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(broadcastFilter)));
            printWriter.println('}');
            broadcastFilter.dumpInReceiverList(printWriter, printWriterPrinter, str2);
        }
    }

    @Override // java.util.AbstractCollection
    public java.lang.String toString() {
        if (this.stringName != null) {
            return this.stringName;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("ReceiverList{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        sb.append(this.pid);
        sb.append(' ');
        sb.append(this.app != null ? this.app.processName : "(unknown name)");
        sb.append('/');
        sb.append(this.uid);
        sb.append("/u");
        sb.append(this.userId);
        sb.append(this.receiver.asBinder() instanceof android.os.Binder ? " local:" : " remote:");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.receiver.asBinder())));
        sb.append('}');
        java.lang.String sb2 = sb.toString();
        this.stringName = sb2;
        return sb2;
    }
}
