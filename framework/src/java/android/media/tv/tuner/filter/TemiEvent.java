package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class TemiEvent extends android.media.tv.tuner.filter.FilterEvent {
    private final byte[] mDescrData;
    private final byte mDescrTag;
    private final long mPts;

    private TemiEvent(long j, byte b, byte[] bArr) {
        this.mPts = j;
        this.mDescrTag = b;
        this.mDescrData = bArr;
    }

    public long getPts() {
        return this.mPts;
    }

    public byte getDescriptorTag() {
        return this.mDescrTag;
    }

    public byte[] getDescriptorData() {
        return this.mDescrData;
    }
}
