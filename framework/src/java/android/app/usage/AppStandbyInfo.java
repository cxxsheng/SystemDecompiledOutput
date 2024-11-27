package android.app.usage;

/* loaded from: classes.dex */
public final class AppStandbyInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.usage.AppStandbyInfo> CREATOR = new android.os.Parcelable.Creator<android.app.usage.AppStandbyInfo>() { // from class: android.app.usage.AppStandbyInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.AppStandbyInfo createFromParcel(android.os.Parcel parcel) {
            return new android.app.usage.AppStandbyInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.AppStandbyInfo[] newArray(int i) {
            return new android.app.usage.AppStandbyInfo[i];
        }
    };
    public java.lang.String mPackageName;
    public int mStandbyBucket;

    private AppStandbyInfo(android.os.Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mStandbyBucket = parcel.readInt();
    }

    public AppStandbyInfo(java.lang.String str, int i) {
        this.mPackageName = str;
        this.mStandbyBucket = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mStandbyBucket);
    }
}
