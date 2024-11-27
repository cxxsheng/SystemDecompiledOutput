package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class RtpHeaderExtension implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.RtpHeaderExtension> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.RtpHeaderExtension>() { // from class: android.telephony.ims.RtpHeaderExtension.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.RtpHeaderExtension createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.RtpHeaderExtension(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.RtpHeaderExtension[] newArray(int i) {
            return new android.telephony.ims.RtpHeaderExtension[i];
        }
    };
    private byte[] mExtensionData;
    private int mLocalIdentifier;

    public RtpHeaderExtension(int i, byte[] bArr) {
        if (i < 1 || i > 13) {
            throw new java.lang.IllegalArgumentException("localIdentifier must be in range 1-14");
        }
        if (bArr == null) {
            throw new java.lang.NullPointerException("extensionDa is required.");
        }
        this.mLocalIdentifier = i;
        this.mExtensionData = bArr;
    }

    private RtpHeaderExtension(android.os.Parcel parcel) {
        this.mLocalIdentifier = parcel.readInt();
        this.mExtensionData = parcel.createByteArray();
    }

    public int getLocalIdentifier() {
        return this.mLocalIdentifier;
    }

    public byte[] getExtensionData() {
        return this.mExtensionData;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mLocalIdentifier);
        parcel.writeByteArray(this.mExtensionData);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.ims.RtpHeaderExtension rtpHeaderExtension = (android.telephony.ims.RtpHeaderExtension) obj;
        if (this.mLocalIdentifier == rtpHeaderExtension.mLocalIdentifier && java.util.Arrays.equals(this.mExtensionData, rtpHeaderExtension.mExtensionData)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (java.util.Objects.hash(java.lang.Integer.valueOf(this.mLocalIdentifier)) * 31) + java.util.Arrays.hashCode(this.mExtensionData);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("RtpHeaderExtension{mLocalIdentifier=");
        sb.append(this.mLocalIdentifier);
        sb.append(", mData=");
        for (byte b : this.mExtensionData) {
            sb.append(java.lang.Integer.toBinaryString(b));
            sb.append("b_");
        }
        sb.append("}");
        return sb.toString();
    }
}
