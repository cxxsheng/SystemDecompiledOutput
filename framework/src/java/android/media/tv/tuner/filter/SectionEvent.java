package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class SectionEvent extends android.media.tv.tuner.filter.FilterEvent {
    private final long mDataLength;
    private final int mSectionNum;
    private final int mTableId;
    private final int mVersion;

    private SectionEvent(int i, int i2, int i3, long j) {
        this.mTableId = i;
        this.mVersion = i2;
        this.mSectionNum = i3;
        this.mDataLength = j;
    }

    public int getTableId() {
        return this.mTableId;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public int getSectionNumber() {
        return this.mSectionNum;
    }

    @java.lang.Deprecated
    public int getDataLength() {
        return (int) getDataLengthLong();
    }

    public long getDataLengthLong() {
        return this.mDataLength;
    }
}
