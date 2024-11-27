package com.android.internal.app;

/* loaded from: classes4.dex */
public class ChooserActivityLoggerImpl implements com.android.internal.app.ChooserActivityLogger {
    private static final int SHARESHEET_INSTANCE_ID_MAX = 8192;
    private static com.android.internal.logging.InstanceIdSequence sInstanceIdSequence;
    private com.android.internal.logging.InstanceId mInstanceId;
    private com.android.internal.logging.UiEventLogger mUiEventLogger = new com.android.internal.logging.UiEventLoggerImpl();

    @Override // com.android.internal.app.ChooserActivityLogger
    public void logShareStarted(int i, java.lang.String str, java.lang.String str2, int i2, int i3, boolean z, int i4, java.lang.String str3) {
        com.android.internal.util.FrameworkStatsLog.write(259, com.android.internal.app.ChooserActivityLogger.SharesheetStartedEvent.SHARE_STARTED.getId(), str, getInstanceId().getId(), str2, i2, i3, z, typeFromPreviewInt(i4), typeFromIntentString(str3), 0, false);
    }

    @Override // com.android.internal.app.ChooserActivityLogger
    public void logShareTargetSelected(int i, java.lang.String str, int i2, boolean z) {
        com.android.internal.util.FrameworkStatsLog.write(260, com.android.internal.app.ChooserActivityLogger.SharesheetTargetSelectedEvent.fromTargetType(i).getId(), str, getInstanceId().getId(), i2, z);
    }

    @Override // com.android.internal.app.ChooserActivityLogger
    public void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, com.android.internal.logging.InstanceId instanceId) {
        this.mUiEventLogger.logWithInstanceId(uiEventEnum, 0, null, instanceId);
    }

    @Override // com.android.internal.app.ChooserActivityLogger
    public com.android.internal.logging.InstanceId getInstanceId() {
        if (this.mInstanceId == null) {
            if (sInstanceIdSequence == null) {
                sInstanceIdSequence = new com.android.internal.logging.InstanceIdSequence(8192);
            }
            this.mInstanceId = sInstanceIdSequence.newInstanceId();
        }
        return this.mInstanceId;
    }
}
