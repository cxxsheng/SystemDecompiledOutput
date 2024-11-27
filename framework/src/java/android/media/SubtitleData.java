package android.media;

/* loaded from: classes2.dex */
public final class SubtitleData {
    private static final java.lang.String TAG = "SubtitleData";
    private byte[] mData;
    private long mDurationUs;
    private long mStartTimeUs;
    private int mTrackIndex;

    public SubtitleData(android.os.Parcel parcel) {
        if (!parseParcel(parcel)) {
            throw new java.lang.IllegalArgumentException("parseParcel() fails");
        }
    }

    public SubtitleData(int i, long j, long j2, byte[] bArr) {
        if (bArr == null) {
            throw new java.lang.IllegalArgumentException("null data is not allowed");
        }
        this.mTrackIndex = i;
        this.mStartTimeUs = j;
        this.mDurationUs = j2;
        this.mData = bArr;
    }

    public int getTrackIndex() {
        return this.mTrackIndex;
    }

    public long getStartTimeUs() {
        return this.mStartTimeUs;
    }

    public long getDurationUs() {
        return this.mDurationUs;
    }

    public byte[] getData() {
        return this.mData;
    }

    private boolean parseParcel(android.os.Parcel parcel) {
        parcel.setDataPosition(0);
        if (parcel.dataAvail() == 0) {
            return false;
        }
        this.mTrackIndex = parcel.readInt();
        this.mStartTimeUs = parcel.readLong();
        this.mDurationUs = parcel.readLong();
        this.mData = new byte[parcel.readInt()];
        parcel.readByteArray(this.mData);
        return true;
    }
}
