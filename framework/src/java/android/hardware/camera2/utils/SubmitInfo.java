package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class SubmitInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.camera2.utils.SubmitInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.utils.SubmitInfo>() { // from class: android.hardware.camera2.utils.SubmitInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.utils.SubmitInfo createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.camera2.utils.SubmitInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.utils.SubmitInfo[] newArray(int i) {
            return new android.hardware.camera2.utils.SubmitInfo[i];
        }
    };
    private long mLastFrameNumber;
    private int mRequestId;

    public SubmitInfo() {
        this.mRequestId = -1;
        this.mLastFrameNumber = -1L;
    }

    public SubmitInfo(int i, long j) {
        this.mRequestId = i;
        this.mLastFrameNumber = j;
    }

    private SubmitInfo(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRequestId);
        parcel.writeLong(this.mLastFrameNumber);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mRequestId = parcel.readInt();
        this.mLastFrameNumber = parcel.readLong();
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public long getLastFrameNumber() {
        return this.mLastFrameNumber;
    }
}
