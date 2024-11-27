package android.media.tv;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class TvContentRatingSystemInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.TvContentRatingSystemInfo> CREATOR = new android.os.Parcelable.Creator<android.media.tv.TvContentRatingSystemInfo>() { // from class: android.media.tv.TvContentRatingSystemInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TvContentRatingSystemInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.TvContentRatingSystemInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TvContentRatingSystemInfo[] newArray(int i) {
            return new android.media.tv.TvContentRatingSystemInfo[i];
        }
    };
    private final android.content.pm.ApplicationInfo mApplicationInfo;
    private final android.net.Uri mXmlUri;

    public static final android.media.tv.TvContentRatingSystemInfo createTvContentRatingSystemInfo(int i, android.content.pm.ApplicationInfo applicationInfo) {
        return new android.media.tv.TvContentRatingSystemInfo(new android.net.Uri.Builder().scheme(android.content.ContentResolver.SCHEME_ANDROID_RESOURCE).authority(applicationInfo.packageName).appendPath(java.lang.String.valueOf(i)).build(), applicationInfo);
    }

    private TvContentRatingSystemInfo(android.net.Uri uri, android.content.pm.ApplicationInfo applicationInfo) {
        this.mXmlUri = uri;
        this.mApplicationInfo = applicationInfo;
    }

    public final boolean isSystemDefined() {
        return (this.mApplicationInfo.flags & 1) != 0;
    }

    public final android.net.Uri getXmlUri() {
        return this.mXmlUri;
    }

    private TvContentRatingSystemInfo(android.os.Parcel parcel) {
        this.mXmlUri = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
        this.mApplicationInfo = (android.content.pm.ApplicationInfo) parcel.readParcelable(null, android.content.pm.ApplicationInfo.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mXmlUri, i);
        parcel.writeParcelable(this.mApplicationInfo, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
