package android.media.projection;

/* loaded from: classes2.dex */
public final class MediaProjectionInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.projection.MediaProjectionInfo> CREATOR = new android.os.Parcelable.Creator<android.media.projection.MediaProjectionInfo>() { // from class: android.media.projection.MediaProjectionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.projection.MediaProjectionInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.projection.MediaProjectionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.projection.MediaProjectionInfo[] newArray(int i) {
            return new android.media.projection.MediaProjectionInfo[i];
        }
    };
    private final android.app.ActivityOptions.LaunchCookie mLaunchCookie;
    private final java.lang.String mPackageName;
    private final android.os.UserHandle mUserHandle;

    public MediaProjectionInfo(java.lang.String str, android.os.UserHandle userHandle, android.app.ActivityOptions.LaunchCookie launchCookie) {
        this.mPackageName = str;
        this.mUserHandle = userHandle;
        this.mLaunchCookie = launchCookie;
    }

    public MediaProjectionInfo(android.os.Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mUserHandle = android.os.UserHandle.readFromParcel(parcel);
        this.mLaunchCookie = android.app.ActivityOptions.LaunchCookie.readFromParcel(parcel);
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.os.UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public android.app.ActivityOptions.LaunchCookie getLaunchCookie() {
        return this.mLaunchCookie;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.media.projection.MediaProjectionInfo)) {
            return false;
        }
        android.media.projection.MediaProjectionInfo mediaProjectionInfo = (android.media.projection.MediaProjectionInfo) obj;
        return java.util.Objects.equals(mediaProjectionInfo.mPackageName, this.mPackageName) && java.util.Objects.equals(mediaProjectionInfo.mUserHandle, this.mUserHandle) && java.util.Objects.equals(mediaProjectionInfo.mLaunchCookie, this.mLaunchCookie);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPackageName, this.mUserHandle);
    }

    public java.lang.String toString() {
        return "MediaProjectionInfo{mPackageName=" + this.mPackageName + ", mUserHandle=" + this.mUserHandle + ", mLaunchCookie=" + this.mLaunchCookie + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        android.os.UserHandle.writeToParcel(this.mUserHandle, parcel);
        android.app.ActivityOptions.LaunchCookie.writeToParcel(this.mLaunchCookie, parcel);
    }
}
