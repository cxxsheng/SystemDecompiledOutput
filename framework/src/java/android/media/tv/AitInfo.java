package android.media.tv;

/* loaded from: classes2.dex */
public final class AitInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.AitInfo> CREATOR = new android.os.Parcelable.Creator<android.media.tv.AitInfo>() { // from class: android.media.tv.AitInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.AitInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.AitInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.AitInfo[] newArray(int i) {
            return new android.media.tv.AitInfo[i];
        }
    };
    static final java.lang.String TAG = "AitInfo";
    private final int mType;
    private final int mVersion;

    private AitInfo(android.os.Parcel parcel) {
        this.mType = parcel.readInt();
        this.mVersion = parcel.readInt();
    }

    public AitInfo(int i, int i2) {
        this.mType = i;
        this.mVersion = i2;
    }

    public int getType() {
        return this.mType;
    }

    public int getVersion() {
        return this.mVersion;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mVersion);
    }

    public java.lang.String toString() {
        return "type=" + this.mType + ";version=" + this.mVersion;
    }
}
