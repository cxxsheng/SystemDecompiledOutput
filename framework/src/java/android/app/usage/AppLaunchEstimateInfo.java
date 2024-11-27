package android.app.usage;

/* loaded from: classes.dex */
public final class AppLaunchEstimateInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.usage.AppLaunchEstimateInfo> CREATOR = new android.os.Parcelable.Creator<android.app.usage.AppLaunchEstimateInfo>() { // from class: android.app.usage.AppLaunchEstimateInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.AppLaunchEstimateInfo createFromParcel(android.os.Parcel parcel) {
            return new android.app.usage.AppLaunchEstimateInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.AppLaunchEstimateInfo[] newArray(int i) {
            return new android.app.usage.AppLaunchEstimateInfo[i];
        }
    };
    public final long estimatedLaunchTime;
    public final java.lang.String packageName;

    private AppLaunchEstimateInfo(android.os.Parcel parcel) {
        this.packageName = parcel.readString();
        this.estimatedLaunchTime = parcel.readLong();
    }

    public AppLaunchEstimateInfo(java.lang.String str, long j) {
        this.packageName = str;
        this.estimatedLaunchTime = j;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeLong(this.estimatedLaunchTime);
    }
}
