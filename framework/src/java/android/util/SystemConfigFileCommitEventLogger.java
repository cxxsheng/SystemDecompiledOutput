package android.util;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class SystemConfigFileCommitEventLogger {
    private final java.lang.String mName;
    private long mStartTime;

    public SystemConfigFileCommitEventLogger(java.lang.String str) {
        this.mName = str;
    }

    public void setStartTime(long j) {
        this.mStartTime = j;
    }

    public void onStartWrite() {
        if (this.mStartTime == 0) {
            this.mStartTime = android.os.SystemClock.uptimeMillis();
        }
    }

    public void onFinishWrite() {
        writeLogRecord(android.os.SystemClock.uptimeMillis() - this.mStartTime);
    }

    public void writeLogRecord(long j) {
        com.android.internal.logging.EventLogTags.writeCommitSysConfigFile(this.mName, j);
    }
}
