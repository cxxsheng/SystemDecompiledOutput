package com.android.server.am;

/* loaded from: classes.dex */
final class AppBindRecord {
    final com.android.server.am.ProcessRecord attributedClient;
    final com.android.server.am.ProcessRecord client;
    final android.util.ArraySet<com.android.server.am.ConnectionRecord> connections = new android.util.ArraySet<>();
    final com.android.server.am.IntentBindRecord intent;
    final com.android.server.am.ServiceRecord service;

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "service=" + this.service);
        printWriter.println(str + "client=" + this.client);
        printWriter.println(str + "attributedClient=" + this.attributedClient);
        dumpInIntentBind(printWriter, str);
    }

    void dumpInIntentBind(java.io.PrintWriter printWriter, java.lang.String str) {
        int size = this.connections.size();
        if (size > 0) {
            printWriter.println(str + "Per-process Connections:");
            for (int i = 0; i < size; i++) {
                printWriter.println(str + "  " + this.connections.valueAt(i));
            }
        }
    }

    AppBindRecord(com.android.server.am.ServiceRecord serviceRecord, com.android.server.am.IntentBindRecord intentBindRecord, com.android.server.am.ProcessRecord processRecord, com.android.server.am.ProcessRecord processRecord2) {
        this.service = serviceRecord;
        this.intent = intentBindRecord;
        this.client = processRecord;
        this.attributedClient = processRecord2;
    }

    public java.lang.String toString() {
        return "AppBindRecord{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.service.shortInstanceName + ":" + this.client.processName + "}";
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.service.shortInstanceName);
        protoOutputStream.write(1138166333442L, this.client.processName);
        int size = this.connections.size();
        for (int i = 0; i < size; i++) {
            protoOutputStream.write(2237677961219L, java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.connections.valueAt(i))));
        }
        protoOutputStream.end(start);
    }
}
