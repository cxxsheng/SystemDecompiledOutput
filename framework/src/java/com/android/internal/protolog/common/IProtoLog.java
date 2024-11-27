package com.android.internal.protolog.common;

/* loaded from: classes5.dex */
public interface IProtoLog {
    boolean isProtoEnabled();

    void log(com.android.internal.protolog.common.LogLevel logLevel, com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, java.lang.String str, java.lang.Object[] objArr);

    int startLoggingToLogcat(java.lang.String[] strArr, com.android.internal.protolog.common.ILogger iLogger);

    int stopLoggingToLogcat(java.lang.String[] strArr, com.android.internal.protolog.common.ILogger iLogger);
}
