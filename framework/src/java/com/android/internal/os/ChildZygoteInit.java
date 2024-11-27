package com.android.internal.os;

/* loaded from: classes4.dex */
public class ChildZygoteInit {
    private static final java.lang.String TAG = "ChildZygoteInit";

    static java.lang.String parseSocketNameFromArgs(java.lang.String[] strArr) {
        for (java.lang.String str : strArr) {
            if (str.startsWith(com.android.internal.os.Zygote.CHILD_ZYGOTE_SOCKET_NAME_ARG)) {
                return str.substring(com.android.internal.os.Zygote.CHILD_ZYGOTE_SOCKET_NAME_ARG.length());
            }
        }
        return null;
    }

    static java.lang.String parseAbiListFromArgs(java.lang.String[] strArr) {
        for (java.lang.String str : strArr) {
            if (str.startsWith(com.android.internal.os.Zygote.CHILD_ZYGOTE_ABI_LIST_ARG)) {
                return str.substring(com.android.internal.os.Zygote.CHILD_ZYGOTE_ABI_LIST_ARG.length());
            }
        }
        return null;
    }

    static int parseIntFromArg(java.lang.String[] strArr, java.lang.String str) {
        int i = -1;
        for (java.lang.String str2 : strArr) {
            if (str2.startsWith(str)) {
                java.lang.String substring = str2.substring(str2.indexOf(61) + 1);
                try {
                    i = java.lang.Integer.parseInt(substring);
                } catch (java.lang.NumberFormatException e) {
                    throw new java.lang.IllegalArgumentException("Invalid int argument: " + substring, e);
                }
            }
        }
        return i;
    }

    static void runZygoteServer(com.android.internal.os.ZygoteServer zygoteServer, java.lang.String[] strArr) {
        java.lang.String parseSocketNameFromArgs = parseSocketNameFromArgs(strArr);
        if (parseSocketNameFromArgs == null) {
            throw new java.lang.NullPointerException("No socketName specified");
        }
        java.lang.String parseAbiListFromArgs = parseAbiListFromArgs(strArr);
        if (parseAbiListFromArgs == null) {
            throw new java.lang.NullPointerException("No abiList specified");
        }
        try {
            android.system.Os.prctl(android.system.OsConstants.PR_SET_NO_NEW_PRIVS, 1L, 0L, 0L, 0L);
            int parseIntFromArg = parseIntFromArg(strArr, com.android.internal.os.Zygote.CHILD_ZYGOTE_UID_RANGE_START);
            int parseIntFromArg2 = parseIntFromArg(strArr, com.android.internal.os.Zygote.CHILD_ZYGOTE_UID_RANGE_END);
            if (parseIntFromArg == -1 || parseIntFromArg2 == -1) {
                throw new java.lang.RuntimeException("Couldn't parse UID range start/end");
            }
            if (parseIntFromArg > parseIntFromArg2) {
                throw new java.lang.RuntimeException("Passed in UID range is invalid, min > max.");
            }
            if (parseIntFromArg < 90000) {
                throw new java.lang.RuntimeException("Passed in UID range does not map to isolated processes.");
            }
            com.android.internal.os.Zygote.nativeInstallSeccompUidGidFilter(parseIntFromArg, parseIntFromArg2);
            try {
                try {
                    zygoteServer.registerServerSocketAtAbstractName(parseSocketNameFromArgs);
                    com.android.internal.os.Zygote.nativeAllowFileAcrossFork("ABSTRACT/" + parseSocketNameFromArgs);
                    java.lang.Runnable runSelectLoop = zygoteServer.runSelectLoop(parseAbiListFromArgs);
                    if (runSelectLoop != null) {
                        runSelectLoop.run();
                    }
                } catch (java.lang.RuntimeException e) {
                    android.util.Log.e(TAG, "Fatal exception:", e);
                    throw e;
                }
            } finally {
                zygoteServer.closeServerSocket();
            }
        } catch (android.system.ErrnoException e2) {
            throw new java.lang.RuntimeException("Failed to set PR_SET_NO_NEW_PRIVS", e2);
        }
    }
}
