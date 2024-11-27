package com.android.server.broadcastradio.aidl;

/* loaded from: classes.dex */
final class RadioLogger {
    private final boolean mDebug;
    private final android.util.LocalLog mEventLogger;
    private final java.lang.String mTag;

    RadioLogger(java.lang.String str, int i) {
        this.mTag = str;
        this.mDebug = android.util.Log.isLoggable(this.mTag, 3);
        this.mEventLogger = new android.util.LocalLog(i);
    }

    void logRadioEvent(java.lang.String str, java.lang.Object... objArr) {
        this.mEventLogger.log(android.text.TextUtils.formatSimple(str, objArr));
        if (this.mDebug) {
            com.android.server.utils.Slogf.d(this.mTag, str, objArr);
        }
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        this.mEventLogger.dump(indentingPrintWriter);
    }
}
