package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class RtpHeaderExtensionType implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.RtpHeaderExtensionType> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.RtpHeaderExtensionType>() { // from class: android.telephony.ims.RtpHeaderExtensionType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.RtpHeaderExtensionType createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.RtpHeaderExtensionType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.RtpHeaderExtensionType[] newArray(int i) {
            return new android.telephony.ims.RtpHeaderExtensionType[i];
        }
    };
    private int mLocalIdentifier;
    private android.net.Uri mUri;

    public RtpHeaderExtensionType(int i, android.net.Uri uri) {
        if (i < 1 || i > 13) {
            throw new java.lang.IllegalArgumentException("localIdentifier must be in range 1-14");
        }
        if (uri == null) {
            throw new java.lang.NullPointerException("uri is required.");
        }
        this.mLocalIdentifier = i;
        this.mUri = uri;
    }

    private RtpHeaderExtensionType(android.os.Parcel parcel) {
        this.mLocalIdentifier = parcel.readInt();
        this.mUri = (android.net.Uri) parcel.readParcelable(android.net.Uri.class.getClassLoader(), android.net.Uri.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mLocalIdentifier);
        parcel.writeParcelable(this.mUri, i);
    }

    public int getLocalIdentifier() {
        return this.mLocalIdentifier;
    }

    public android.net.Uri getUri() {
        return this.mUri;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.ims.RtpHeaderExtensionType rtpHeaderExtensionType = (android.telephony.ims.RtpHeaderExtensionType) obj;
        if (this.mLocalIdentifier == rtpHeaderExtensionType.mLocalIdentifier && this.mUri.equals(rtpHeaderExtensionType.mUri)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mLocalIdentifier), this.mUri);
    }

    public java.lang.String toString() {
        return "RtpHeaderExtensionType{mLocalIdentifier=" + this.mLocalIdentifier + ", mUri=" + this.mUri + "}";
    }
}
