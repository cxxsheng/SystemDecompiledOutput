package com.android.internal.os;

/* loaded from: classes4.dex */
public class WrapperInit {
    private static final java.lang.String TAG = "AndroidRuntime";

    private WrapperInit() {
    }

    public static void main(java.lang.String[] strArr) {
        int parseInt = java.lang.Integer.parseInt(strArr[0], 10);
        int parseInt2 = java.lang.Integer.parseInt(strArr[1], 10);
        if (parseInt != 0) {
            java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
            try {
                try {
                    fileDescriptor.setInt$(parseInt);
                    java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(new java.io.FileOutputStream(fileDescriptor));
                    dataOutputStream.writeInt(android.os.Process.myPid());
                    dataOutputStream.close();
                } catch (java.io.IOException e) {
                    android.util.Slog.d(TAG, "Could not write pid of wrapped process to Zygote pipe.", e);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(fileDescriptor);
            }
        }
        com.android.internal.os.ZygoteInit.preload(new android.util.TimingsTraceLog("WrapperInitTiming", 16384L));
        int length = strArr.length - 2;
        java.lang.String[] strArr2 = new java.lang.String[length];
        java.lang.System.arraycopy(strArr, 2, strArr2, 0, length);
        wrapperInit(parseInt2, strArr2).run();
    }

    public static void execApplication(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) {
        java.lang.String str4;
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str);
        if (dalvik.system.VMRuntime.is64BitInstructionSet(str3)) {
            str4 = "/system/bin/app_process64";
        } else {
            str4 = "/system/bin/app_process32";
        }
        sb.append(' ');
        sb.append(str4);
        sb.append(" -Xcompiler-option --generate-mini-debug-info");
        sb.append(" /system/bin --application");
        if (str2 != null) {
            sb.append(" '--nice-name=").append(str2).append("'");
        }
        sb.append(" com.android.internal.os.WrapperInit ");
        sb.append(fileDescriptor != null ? fileDescriptor.getInt$() : 0);
        sb.append(' ');
        sb.append(i);
        com.android.internal.os.Zygote.appendQuotedShellArgs(sb, strArr);
        preserveCapabilities();
        com.android.internal.os.Zygote.execShell(sb.toString());
    }

    private static java.lang.Runnable wrapperInit(int i, java.lang.String[] strArr) {
        java.lang.ClassLoader classLoader;
        if (strArr != null && strArr.length > 2 && strArr[0].equals("-cp")) {
            classLoader = com.android.internal.os.ZygoteInit.createPathClassLoader(strArr[1], i);
            java.lang.Thread.currentThread().setContextClassLoader(classLoader);
            java.lang.String[] strArr2 = new java.lang.String[strArr.length - 2];
            java.lang.System.arraycopy(strArr, 2, strArr2, 0, strArr.length - 2);
            strArr = strArr2;
        } else {
            classLoader = null;
        }
        com.android.internal.os.Zygote.nativePreApplicationInit();
        return com.android.internal.os.RuntimeInit.applicationInit(i, null, strArr, classLoader);
    }

    private static void preserveCapabilities() {
        android.system.StructCapUserHeader structCapUserHeader = new android.system.StructCapUserHeader(android.system.OsConstants._LINUX_CAPABILITY_VERSION_3, 0);
        try {
            android.system.StructCapUserData[] capget = android.system.Os.capget(structCapUserHeader);
            if (capget[0].permitted != capget[0].inheritable || capget[1].permitted != capget[1].inheritable) {
                capget[0] = new android.system.StructCapUserData(capget[0].effective, capget[0].permitted, capget[0].permitted);
                capget[1] = new android.system.StructCapUserData(capget[1].effective, capget[1].permitted, capget[1].permitted);
                try {
                    android.system.Os.capset(structCapUserHeader, capget);
                } catch (android.system.ErrnoException e) {
                    android.util.Slog.e(TAG, "RuntimeInit: Failed capset", e);
                    return;
                }
            }
            for (int i = 0; i < 64; i++) {
                if ((capget[android.system.OsConstants.CAP_TO_INDEX(i)].inheritable & android.system.OsConstants.CAP_TO_MASK(i)) != 0) {
                    try {
                        android.system.Os.prctl(android.system.OsConstants.PR_CAP_AMBIENT, android.system.OsConstants.PR_CAP_AMBIENT_RAISE, i, 0L, 0L);
                    } catch (android.system.ErrnoException e2) {
                        android.util.Slog.e(TAG, "RuntimeInit: Failed to raise ambient capability " + i, e2);
                    }
                }
            }
        } catch (android.system.ErrnoException e3) {
            android.util.Slog.e(TAG, "RuntimeInit: Failed capget", e3);
        }
    }
}
