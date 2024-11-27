package android.media.tv;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class DvbDeviceInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.DvbDeviceInfo> CREATOR = new android.os.Parcelable.Creator<android.media.tv.DvbDeviceInfo>() { // from class: android.media.tv.DvbDeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.DvbDeviceInfo createFromParcel(android.os.Parcel parcel) {
            try {
                return new android.media.tv.DvbDeviceInfo(parcel);
            } catch (java.lang.Exception e) {
                android.util.Log.e(android.media.tv.DvbDeviceInfo.TAG, "Exception creating DvbDeviceInfo from parcel", e);
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.DvbDeviceInfo[] newArray(int i) {
            return new android.media.tv.DvbDeviceInfo[i];
        }
    };
    static final java.lang.String TAG = "DvbDeviceInfo";
    private final int mAdapterId;
    private final int mDeviceId;

    private DvbDeviceInfo(android.os.Parcel parcel) {
        this.mAdapterId = parcel.readInt();
        this.mDeviceId = parcel.readInt();
    }

    public DvbDeviceInfo(int i, int i2) {
        this.mAdapterId = i;
        this.mDeviceId = i2;
    }

    public int getAdapterId() {
        return this.mAdapterId;
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAdapterId);
        parcel.writeInt(this.mDeviceId);
    }
}
