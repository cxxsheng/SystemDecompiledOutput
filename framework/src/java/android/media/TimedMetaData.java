package android.media;

/* loaded from: classes2.dex */
public final class TimedMetaData {
    private static final java.lang.String TAG = "TimedMetaData";
    private byte[] mMetaData;
    private long mTimestampUs;

    static android.media.TimedMetaData createTimedMetaDataFromParcel(android.os.Parcel parcel) {
        return new android.media.TimedMetaData(parcel);
    }

    private TimedMetaData(android.os.Parcel parcel) {
        if (!parseParcel(parcel)) {
            throw new java.lang.IllegalArgumentException("parseParcel() fails");
        }
    }

    public TimedMetaData(long j, byte[] bArr) {
        if (bArr == null) {
            throw new java.lang.IllegalArgumentException("null metaData is not allowed");
        }
        this.mTimestampUs = j;
        this.mMetaData = bArr;
    }

    public long getTimestamp() {
        return this.mTimestampUs;
    }

    public byte[] getMetaData() {
        return this.mMetaData;
    }

    private boolean parseParcel(android.os.Parcel parcel) {
        parcel.setDataPosition(0);
        if (parcel.dataAvail() == 0) {
            return false;
        }
        this.mTimestampUs = parcel.readLong();
        this.mMetaData = new byte[parcel.readInt()];
        parcel.readByteArray(this.mMetaData);
        return true;
    }
}
