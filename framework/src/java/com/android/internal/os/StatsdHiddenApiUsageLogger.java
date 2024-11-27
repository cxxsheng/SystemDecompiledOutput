package com.android.internal.os;

/* loaded from: classes4.dex */
class StatsdHiddenApiUsageLogger implements dalvik.system.VMRuntime.HiddenApiUsageLogger {
    private static final com.android.internal.os.StatsdHiddenApiUsageLogger sInstance = new com.android.internal.os.StatsdHiddenApiUsageLogger();
    private final com.android.internal.logging.MetricsLogger mMetricsLogger = new com.android.internal.logging.MetricsLogger();
    private int mHiddenApiAccessLogSampleRate = 0;
    private int mHiddenApiAccessStatslogSampleRate = 0;

    StatsdHiddenApiUsageLogger() {
    }

    static void setHiddenApiAccessLogSampleRates(int i, int i2) {
        sInstance.mHiddenApiAccessLogSampleRate = i;
        sInstance.mHiddenApiAccessStatslogSampleRate = i2;
    }

    static com.android.internal.os.StatsdHiddenApiUsageLogger getInstance() {
        return sInstance;
    }

    public void hiddenApiUsed(int i, java.lang.String str, java.lang.String str2, int i2, boolean z) {
        if (i < this.mHiddenApiAccessLogSampleRate) {
            logUsage(str, str2, i2, z);
        }
        if (i < this.mHiddenApiAccessStatslogSampleRate) {
            newLogUsage(str2, i2, z);
        }
    }

    private void logUsage(java.lang.String str, java.lang.String str2, int i, boolean z) {
        int i2 = 0;
        switch (i) {
            case 1:
                i2 = 1;
                break;
            case 2:
                i2 = 2;
                break;
            case 3:
                i2 = 3;
                break;
        }
        android.metrics.LogMaker addTaggedData = new android.metrics.LogMaker(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_HIDDEN_API_ACCESSED).setPackageName(str).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_HIDDEN_API_SIGNATURE, str2).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_HIDDEN_API_ACCESS_METHOD, java.lang.Integer.valueOf(i2));
        if (z) {
            addTaggedData.addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_HIDDEN_API_ACCESS_DENIED, 1);
        }
        this.mMetricsLogger.write(addTaggedData);
    }

    private void newLogUsage(java.lang.String str, int i, boolean z) {
        int i2 = 0;
        switch (i) {
            case 1:
                i2 = 1;
                break;
            case 2:
                i2 = 2;
                break;
            case 3:
                i2 = 3;
                break;
        }
        com.android.internal.util.FrameworkStatsLog.write(178, android.os.Process.myUid(), str, i2, z);
    }
}
