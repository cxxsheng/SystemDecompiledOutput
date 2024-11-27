package android.telephony.mbms;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class UriPathPair implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.mbms.UriPathPair> CREATOR = new android.os.Parcelable.Creator<android.telephony.mbms.UriPathPair>() { // from class: android.telephony.mbms.UriPathPair.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.mbms.UriPathPair createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.mbms.UriPathPair(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.mbms.UriPathPair[] newArray(int i) {
            return new android.telephony.mbms.UriPathPair[i];
        }
    };
    private final android.net.Uri mContentUri;
    private final android.net.Uri mFilePathUri;

    public UriPathPair(android.net.Uri uri, android.net.Uri uri2) {
        if (uri == null || !"file".equals(uri.getScheme())) {
            throw new java.lang.IllegalArgumentException("File URI must have file scheme");
        }
        if (uri2 == null || !"content".equals(uri2.getScheme())) {
            throw new java.lang.IllegalArgumentException("Content URI must have content scheme");
        }
        this.mFilePathUri = uri;
        this.mContentUri = uri2;
    }

    private UriPathPair(android.os.Parcel parcel) {
        this.mFilePathUri = (android.net.Uri) parcel.readParcelable(android.net.Uri.class.getClassLoader(), android.net.Uri.class);
        this.mContentUri = (android.net.Uri) parcel.readParcelable(android.net.Uri.class.getClassLoader(), android.net.Uri.class);
    }

    public android.net.Uri getFilePathUri() {
        return this.mFilePathUri;
    }

    public android.net.Uri getContentUri() {
        return this.mContentUri;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mFilePathUri, i);
        parcel.writeParcelable(this.mContentUri, i);
    }
}
