package com.android.server.broadcastradio.hal2;

/* loaded from: classes.dex */
final class RadioEventLogger {
    private final android.util.LocalLog mEventLogger;
    private final java.lang.String mTag;

    RadioEventLogger(java.lang.String str, int i) {
        this.mTag = str;
        this.mEventLogger = new android.util.LocalLog(i);
    }

    void logRadioEvent(java.lang.String str, java.lang.Object... objArr) {
        java.lang.String format = java.lang.String.format(str, objArr);
        this.mEventLogger.log(format);
        if (android.util.Log.isLoggable(this.mTag, 3)) {
            android.util.Slog.d(this.mTag, format);
        }
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        this.mEventLogger.dump(indentingPrintWriter);
    }
}
