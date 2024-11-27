package com.android.internal.logging.testing;

/* loaded from: classes4.dex */
public class UiEventLoggerFake implements com.android.internal.logging.UiEventLogger {
    private java.util.List<com.android.internal.logging.testing.UiEventLoggerFake.FakeUiEvent> mLogs = new java.util.LinkedList();

    public static class FakeUiEvent {
        public final int eventId;
        public final com.android.internal.logging.InstanceId instanceId;
        public final java.lang.String packageName;
        public final int position;
        public final int uid;

        FakeUiEvent(int i, int i2, java.lang.String str) {
            this.eventId = i;
            this.uid = i2;
            this.packageName = str;
            this.instanceId = null;
            this.position = 0;
        }

        FakeUiEvent(int i, int i2, java.lang.String str, com.android.internal.logging.InstanceId instanceId) {
            this.eventId = i;
            this.uid = i2;
            this.packageName = str;
            this.instanceId = instanceId;
            this.position = 0;
        }

        FakeUiEvent(int i, int i2, java.lang.String str, com.android.internal.logging.InstanceId instanceId, int i3) {
            this.eventId = i;
            this.uid = i2;
            this.packageName = str;
            this.instanceId = instanceId;
            this.position = i3;
        }
    }

    public java.util.List<com.android.internal.logging.testing.UiEventLoggerFake.FakeUiEvent> getLogs() {
        return this.mLogs;
    }

    public int numLogs() {
        return this.mLogs.size();
    }

    public com.android.internal.logging.testing.UiEventLoggerFake.FakeUiEvent get(int i) {
        return this.mLogs.get(i);
    }

    public int eventId(int i) {
        return this.mLogs.get(i).eventId;
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum) {
        log(uiEventEnum, 0, null);
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, com.android.internal.logging.InstanceId instanceId) {
        logWithInstanceId(uiEventEnum, 0, null, instanceId);
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, int i, java.lang.String str) {
        int id = uiEventEnum.getId();
        if (id > 0) {
            this.mLogs.add(new com.android.internal.logging.testing.UiEventLoggerFake.FakeUiEvent(id, i, str));
        }
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void logWithInstanceId(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, int i, java.lang.String str, com.android.internal.logging.InstanceId instanceId) {
        int id = uiEventEnum.getId();
        if (id > 0) {
            this.mLogs.add(new com.android.internal.logging.testing.UiEventLoggerFake.FakeUiEvent(id, i, str, instanceId));
        }
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void logWithPosition(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, int i, java.lang.String str, int i2) {
        int id = uiEventEnum.getId();
        if (id > 0) {
            this.mLogs.add(new com.android.internal.logging.testing.UiEventLoggerFake.FakeUiEvent(id, i, str, null, i2));
        }
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void logWithInstanceIdAndPosition(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, int i, java.lang.String str, com.android.internal.logging.InstanceId instanceId, int i2) {
        int id = uiEventEnum.getId();
        if (id > 0) {
            this.mLogs.add(new com.android.internal.logging.testing.UiEventLoggerFake.FakeUiEvent(id, i, str, instanceId, i2));
        }
    }
}
