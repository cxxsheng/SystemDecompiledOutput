package com.android.server.display.utils;

/* loaded from: classes.dex */
public class DebugUtils {
    public static final boolean DEBUG_ALL = android.util.Log.isLoggable("DisplayManager_All", 3);

    public static boolean isDebuggable(java.lang.String str) {
        return android.util.Log.isLoggable(str, 3) || DEBUG_ALL;
    }
}
