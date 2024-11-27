package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class HdmiLogger {
    private static final long ERROR_LOG_DURATION_MILLIS = 20000;
    private static final java.lang.String TAG = "HDMI";
    private static final boolean DEBUG = android.util.Log.isLoggable("HDMI", 3);
    private static final java.lang.ThreadLocal<com.android.server.hdmi.HdmiLogger> sLogger = new java.lang.ThreadLocal<>();
    private final java.util.HashMap<java.lang.String, android.util.Pair<java.lang.Long, java.lang.Integer>> mWarningTimingCache = new java.util.HashMap<>();
    private final java.util.HashMap<java.lang.String, android.util.Pair<java.lang.Long, java.lang.Integer>> mErrorTimingCache = new java.util.HashMap<>();

    private HdmiLogger() {
    }

    static final void warning(java.lang.String str, java.lang.Object... objArr) {
        getLogger().warningInternal(toLogString(str, objArr));
    }

    private void warningInternal(java.lang.String str) {
        java.lang.String updateLog = updateLog(this.mWarningTimingCache, str);
        if (!updateLog.isEmpty()) {
            android.util.Slog.w("HDMI", updateLog);
        }
    }

    static final void error(java.lang.String str, java.lang.Object... objArr) {
        getLogger().errorInternal(toLogString(str, objArr));
    }

    static final void error(java.lang.String str, java.lang.Exception exc, java.lang.Object... objArr) {
        getLogger().errorInternal(toLogString(str + exc, objArr));
    }

    private void errorInternal(java.lang.String str) {
        java.lang.String updateLog = updateLog(this.mErrorTimingCache, str);
        if (!updateLog.isEmpty()) {
            android.util.Slog.e("HDMI", updateLog);
        }
    }

    static final void debug(java.lang.String str, java.lang.Object... objArr) {
        getLogger().debugInternal(toLogString(str, objArr));
    }

    private void debugInternal(java.lang.String str) {
        if (DEBUG) {
            android.util.Slog.d("HDMI", str);
        }
    }

    private static final java.lang.String toLogString(java.lang.String str, java.lang.Object[] objArr) {
        if (objArr.length > 0) {
            return java.lang.String.format(str, objArr);
        }
        return str;
    }

    private static com.android.server.hdmi.HdmiLogger getLogger() {
        com.android.server.hdmi.HdmiLogger hdmiLogger = sLogger.get();
        if (hdmiLogger == null) {
            com.android.server.hdmi.HdmiLogger hdmiLogger2 = new com.android.server.hdmi.HdmiLogger();
            sLogger.set(hdmiLogger2);
            return hdmiLogger2;
        }
        return hdmiLogger;
    }

    private static java.lang.String updateLog(java.util.HashMap<java.lang.String, android.util.Pair<java.lang.Long, java.lang.Integer>> hashMap, java.lang.String str) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.util.Pair<java.lang.Long, java.lang.Integer> pair = hashMap.get(str);
        if (shouldLogNow(pair, uptimeMillis)) {
            java.lang.String buildMessage = buildMessage(str, pair);
            hashMap.put(str, new android.util.Pair<>(java.lang.Long.valueOf(uptimeMillis), 1));
            return buildMessage;
        }
        increaseLogCount(hashMap, str);
        return "";
    }

    private static java.lang.String buildMessage(java.lang.String str, @android.annotation.Nullable android.util.Pair<java.lang.Long, java.lang.Integer> pair) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("[");
        sb.append(pair == null ? 1 : ((java.lang.Integer) pair.second).intValue());
        sb.append("]:");
        sb.append(str);
        return sb.toString();
    }

    private static void increaseLogCount(java.util.HashMap<java.lang.String, android.util.Pair<java.lang.Long, java.lang.Integer>> hashMap, java.lang.String str) {
        android.util.Pair<java.lang.Long, java.lang.Integer> pair = hashMap.get(str);
        if (pair != null) {
            hashMap.put(str, new android.util.Pair<>((java.lang.Long) pair.first, java.lang.Integer.valueOf(((java.lang.Integer) pair.second).intValue() + 1)));
        }
    }

    private static boolean shouldLogNow(@android.annotation.Nullable android.util.Pair<java.lang.Long, java.lang.Integer> pair, long j) {
        return pair == null || j - ((java.lang.Long) pair.first).longValue() > ERROR_LOG_DURATION_MILLIS;
    }
}
