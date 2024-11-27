package com.android.internal.logging;

/* loaded from: classes4.dex */
public interface UiEventLogger {

    public interface UiEventEnum {
        public static final int RESERVE_NEW_UI_EVENT_ID = Integer.MIN_VALUE;

        int getId();
    }

    void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum);

    void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, int i, java.lang.String str);

    void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, com.android.internal.logging.InstanceId instanceId);

    void logWithInstanceId(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, int i, java.lang.String str, com.android.internal.logging.InstanceId instanceId);

    void logWithInstanceIdAndPosition(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, int i, java.lang.String str, com.android.internal.logging.InstanceId instanceId, int i2);

    void logWithPosition(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, int i, java.lang.String str, int i2);
}
