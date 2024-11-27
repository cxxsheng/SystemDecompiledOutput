package com.android.server;

/* loaded from: classes.dex */
public class RuntimeService extends android.os.Binder {
    private static final java.lang.String TAG = "RuntimeService";
    private final android.content.Context mContext;

    public RuntimeService(android.content.Context context) {
        this.mContext = context;
    }

    @Override // android.os.Binder
    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        android.util.proto.ProtoOutputStream protoOutputStream;
        if (!com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, TAG, printWriter)) {
            return;
        }
        boolean hasOption = hasOption(strArr, "--proto");
        com.android.i18n.timezone.DebugInfo debugInfo = com.android.i18n.timezone.I18nModuleDebug.getDebugInfo();
        if (hasOption) {
            protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
            reportTimeZoneInfoProto(debugInfo, protoOutputStream);
        } else {
            reportTimeZoneInfo(debugInfo, printWriter);
            protoOutputStream = null;
        }
        if (hasOption) {
            protoOutputStream.flush();
        }
    }

    private static boolean hasOption(java.lang.String[] strArr, java.lang.String str) {
        for (java.lang.String str2 : strArr) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    private static void reportTimeZoneInfo(com.android.i18n.timezone.DebugInfo debugInfo, java.io.PrintWriter printWriter) {
        printWriter.println("Core Library Debug Info: ");
        for (com.android.i18n.timezone.DebugInfo.DebugEntry debugEntry : debugInfo.getDebugEntries()) {
            printWriter.print(debugEntry.getKey());
            printWriter.print(": \"");
            printWriter.print(debugEntry.getStringValue());
            printWriter.println("\"");
        }
    }

    private static void reportTimeZoneInfoProto(com.android.i18n.timezone.DebugInfo debugInfo, android.util.proto.ProtoOutputStream protoOutputStream) {
        for (com.android.i18n.timezone.DebugInfo.DebugEntry debugEntry : debugInfo.getDebugEntries()) {
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1138166333441L, debugEntry.getKey());
            protoOutputStream.write(1138166333442L, debugEntry.getStringValue());
            protoOutputStream.end(start);
        }
    }
}
