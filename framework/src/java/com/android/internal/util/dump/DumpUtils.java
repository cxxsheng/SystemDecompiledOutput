package com.android.internal.util.dump;

/* loaded from: classes5.dex */
public class DumpUtils {
    public static void writeStringIfNotNull(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j, java.lang.String str2) {
        if (str2 != null) {
            dualDumpOutputStream.write(str, j, str2);
        }
    }

    public static void writeComponentName(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j, android.content.ComponentName componentName) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("package_name", 1138166333441L, componentName.getPackageName());
        dualDumpOutputStream.write("class_name", 1138166333442L, componentName.getClassName());
        dualDumpOutputStream.end(start);
    }
}
