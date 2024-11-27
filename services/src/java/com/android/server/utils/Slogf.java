package com.android.server.utils;

/* loaded from: classes2.dex */
public final class Slogf {

    @com.android.internal.annotations.GuardedBy({"sMessageBuilder"})
    private static final java.util.Formatter sFormatter;

    @com.android.internal.annotations.GuardedBy({"sMessageBuilder"})
    private static final java.lang.StringBuilder sMessageBuilder;

    static {
        android.util.TimingsTraceLog timingsTraceLog = new android.util.TimingsTraceLog("SLog", 524288L);
        timingsTraceLog.traceBegin("static_init");
        sMessageBuilder = new java.lang.StringBuilder();
        sFormatter = new java.util.Formatter(sMessageBuilder, java.util.Locale.ENGLISH);
        timingsTraceLog.traceEnd();
    }

    private Slogf() {
        throw new java.lang.UnsupportedOperationException("provides only static methods");
    }

    public static int v(java.lang.String str, java.lang.String str2) {
        return android.util.Slog.v(str, str2);
    }

    public static int v(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Slog.v(str, str2, th);
    }

    public static int d(java.lang.String str, java.lang.String str2) {
        return android.util.Slog.d(str, str2);
    }

    public static int d(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Slog.d(str, str2, th);
    }

    public static int i(java.lang.String str, java.lang.String str2) {
        return android.util.Slog.i(str, str2);
    }

    public static int i(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Slog.i(str, str2, th);
    }

    public static int w(java.lang.String str, java.lang.String str2) {
        return android.util.Slog.w(str, str2);
    }

    public static int w(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Slog.w(str, str2, th);
    }

    public static int w(java.lang.String str, java.lang.Throwable th) {
        return android.util.Slog.w(str, th);
    }

    public static int e(java.lang.String str, java.lang.String str2) {
        return android.util.Slog.e(str, str2);
    }

    public static int e(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Slog.e(str, str2, th);
    }

    public static int wtf(java.lang.String str, java.lang.String str2) {
        return android.util.Slog.wtf(str, str2);
    }

    public static void wtfQuiet(java.lang.String str, java.lang.String str2) {
        android.util.Slog.wtfQuiet(str, str2);
    }

    public static int wtfStack(java.lang.String str, java.lang.String str2) {
        return android.util.Slog.wtfStack(str, str2);
    }

    public static int wtf(java.lang.String str, java.lang.Throwable th) {
        return android.util.Slog.wtf(str, th);
    }

    public static int wtf(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Slog.wtf(str, str2, th);
    }

    public static int println(int i, java.lang.String str, java.lang.String str2) {
        return android.util.Slog.println(i, str, str2);
    }

    public static void v(java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.Object... objArr) {
        v(str, getMessage(str2, objArr));
    }

    public static void v(java.lang.String str, java.lang.Throwable th, java.lang.String str2, @android.annotation.Nullable java.lang.Object... objArr) {
        v(str, getMessage(str2, objArr), th);
    }

    public static void d(java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.Object... objArr) {
        d(str, getMessage(str2, objArr));
    }

    public static void d(java.lang.String str, java.lang.Throwable th, java.lang.String str2, @android.annotation.Nullable java.lang.Object... objArr) {
        d(str, getMessage(str2, objArr), th);
    }

    public static void i(java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.Object... objArr) {
        i(str, getMessage(str2, objArr));
    }

    public static void i(java.lang.String str, java.lang.Throwable th, java.lang.String str2, @android.annotation.Nullable java.lang.Object... objArr) {
        i(str, getMessage(str2, objArr), th);
    }

    public static void w(java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.Object... objArr) {
        w(str, getMessage(str2, objArr));
    }

    public static void w(java.lang.String str, java.lang.Throwable th, java.lang.String str2, @android.annotation.Nullable java.lang.Object... objArr) {
        w(str, getMessage(str2, objArr), th);
    }

    public static void e(java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.Object... objArr) {
        e(str, getMessage(str2, objArr));
    }

    public static void e(java.lang.String str, java.lang.Throwable th, java.lang.String str2, @android.annotation.Nullable java.lang.Object... objArr) {
        e(str, getMessage(str2, objArr), th);
    }

    public static void wtf(java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.Object... objArr) {
        wtf(str, getMessage(str2, objArr));
    }

    public static void wtf(java.lang.String str, java.lang.Throwable th, java.lang.String str2, @android.annotation.Nullable java.lang.Object... objArr) {
        wtf(str, getMessage(str2, objArr), th);
    }

    private static java.lang.String getMessage(java.lang.String str, @android.annotation.Nullable java.lang.Object... objArr) {
        java.lang.String sb;
        synchronized (sMessageBuilder) {
            sFormatter.format(str, objArr);
            sb = sMessageBuilder.toString();
            sMessageBuilder.setLength(0);
        }
        return sb;
    }
}
