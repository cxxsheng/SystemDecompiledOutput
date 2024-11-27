package com.android.server;

/* loaded from: classes.dex */
public final class ResourcePressureUtil {
    private static final java.util.List<java.lang.String> PSI_FILES = java.util.Arrays.asList("/proc/pressure/memory", "/proc/pressure/cpu", "/proc/pressure/io");
    private static final java.lang.String PSI_ROOT = "/proc/pressure";
    private static final java.lang.String TAG = "ResourcePressureUtil";

    private ResourcePressureUtil() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String readResourcePsiState(java.lang.String str) {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        try {
            if (new java.io.File(str).exists()) {
                stringWriter.append((java.lang.CharSequence) ("----- Output from " + str + " -----\n"));
                stringWriter.append((java.lang.CharSequence) libcore.io.IoUtils.readFileAsString(str));
                stringWriter.append((java.lang.CharSequence) ("----- End output from " + str + " -----\n"));
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, " could not read " + str, e);
        }
        return stringWriter.toString();
    }

    public static java.lang.String currentPsiState() {
        android.os.StrictMode.ThreadPolicy allowThreadDiskReads = android.os.StrictMode.allowThreadDiskReads();
        final java.io.StringWriter stringWriter = new java.io.StringWriter();
        try {
            java.util.stream.Stream<R> map = PSI_FILES.stream().map(new java.util.function.Function() { // from class: com.android.server.ResourcePressureUtil$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String readResourcePsiState;
                    readResourcePsiState = com.android.server.ResourcePressureUtil.readResourcePsiState((java.lang.String) obj);
                    return readResourcePsiState;
                }
            });
            java.util.Objects.requireNonNull(stringWriter);
            map.forEach(new java.util.function.Consumer() { // from class: com.android.server.ResourcePressureUtil$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    stringWriter.append((java.lang.CharSequence) obj);
                }
            });
            android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
            java.lang.String obj = stringWriter.toString();
            if (obj.length() <= 0) {
                return obj;
            }
            return obj + "\n";
        } catch (java.lang.Throwable th) {
            android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
            throw th;
        }
    }
}
