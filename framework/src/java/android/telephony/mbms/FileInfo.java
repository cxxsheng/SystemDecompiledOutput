package android.telephony.mbms;

/* loaded from: classes3.dex */
public final class FileInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.mbms.FileInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.mbms.FileInfo>() { // from class: android.telephony.mbms.FileInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.mbms.FileInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.mbms.FileInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.mbms.FileInfo[] newArray(int i) {
            return new android.telephony.mbms.FileInfo[i];
        }
    };
    private final java.lang.String mimeType;
    private final android.net.Uri uri;

    @android.annotation.SystemApi
    public FileInfo(android.net.Uri uri, java.lang.String str) {
        this.uri = uri;
        this.mimeType = str;
    }

    private FileInfo(android.os.Parcel parcel) {
        this.uri = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
        this.mimeType = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.uri, i);
        parcel.writeString(this.mimeType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.net.Uri getUri() {
        return this.uri;
    }

    public java.lang.String getMimeType() {
        return this.mimeType;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.mbms.FileInfo fileInfo = (android.telephony.mbms.FileInfo) obj;
        if (java.util.Objects.equals(this.uri, fileInfo.uri) && java.util.Objects.equals(this.mimeType, fileInfo.mimeType)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.uri, this.mimeType);
    }
}
