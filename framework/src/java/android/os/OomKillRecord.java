package android.os;

/* loaded from: classes3.dex */
public final class OomKillRecord {
    private short mOomScoreAdj;
    private int mPid;
    private java.lang.String mProcessName;
    private long mTimeStampInMillis;
    private int mUid;

    public OomKillRecord(long j, int i, int i2, java.lang.String str, short s) {
        this.mTimeStampInMillis = j;
        this.mPid = i;
        this.mUid = i2;
        this.mProcessName = str;
        this.mOomScoreAdj = s;
    }

    public void logKillOccurred() {
        com.android.internal.util.FrameworkStatsLog.write(754, this.mUid, this.mPid, this.mOomScoreAdj, this.mTimeStampInMillis, this.mProcessName);
    }

    public long getTimestampMilli() {
        return this.mTimeStampInMillis;
    }

    public int getPid() {
        return this.mPid;
    }

    public int getUid() {
        return this.mUid;
    }

    public java.lang.String getProcessName() {
        return this.mProcessName;
    }

    public short getOomScoreAdj() {
        return this.mOomScoreAdj;
    }
}
