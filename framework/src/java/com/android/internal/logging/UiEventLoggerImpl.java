package com.android.internal.logging;

/* loaded from: classes4.dex */
public class UiEventLoggerImpl implements com.android.internal.logging.UiEventLogger {
    @Override // com.android.internal.logging.UiEventLogger
    public void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum) {
        log(uiEventEnum, 0, null);
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, int i, java.lang.String str) {
        int id = uiEventEnum.getId();
        if (id > 0) {
            com.android.internal.util.FrameworkStatsLog.write(90, id, i, str, 0);
        }
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, com.android.internal.logging.InstanceId instanceId) {
        logWithInstanceId(uiEventEnum, 0, null, instanceId);
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void logWithInstanceId(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, int i, java.lang.String str, com.android.internal.logging.InstanceId instanceId) {
        int id = uiEventEnum.getId();
        if (id > 0 && instanceId != null) {
            com.android.internal.util.FrameworkStatsLog.write(90, id, i, str, instanceId.getId());
        } else if (id > 0) {
            log(uiEventEnum, i, str);
        }
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void logWithPosition(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, int i, java.lang.String str, int i2) {
        int id = uiEventEnum.getId();
        if (id > 0) {
            com.android.internal.util.FrameworkStatsLog.write(260, id, str, 0, i2, false);
        }
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void logWithInstanceIdAndPosition(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, int i, java.lang.String str, com.android.internal.logging.InstanceId instanceId, int i2) {
        int id = uiEventEnum.getId();
        if (id > 0 && instanceId != null) {
            com.android.internal.util.FrameworkStatsLog.write(260, id, str, instanceId.getId(), i2, false);
        } else if (id > 0) {
            logWithPosition(uiEventEnum, i, str, i2);
        }
    }
}
