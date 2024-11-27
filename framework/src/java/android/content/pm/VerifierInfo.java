package android.content.pm;

/* loaded from: classes.dex */
public class VerifierInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.VerifierInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.VerifierInfo>() { // from class: android.content.pm.VerifierInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.VerifierInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.VerifierInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.VerifierInfo[] newArray(int i) {
            return new android.content.pm.VerifierInfo[i];
        }
    };
    public final java.lang.String packageName;
    public final java.security.PublicKey publicKey;

    public VerifierInfo(java.lang.String str, java.security.PublicKey publicKey) {
        if (str == null || str.length() == 0) {
            throw new java.lang.IllegalArgumentException("packageName must not be null or empty");
        }
        if (publicKey == null) {
            throw new java.lang.IllegalArgumentException("publicKey must not be null");
        }
        this.packageName = str;
        this.publicKey = publicKey;
    }

    private VerifierInfo(android.os.Parcel parcel) {
        this.packageName = parcel.readString();
        this.publicKey = (java.security.PublicKey) parcel.readSerializable(java.security.PublicKey.class.getClassLoader(), java.security.PublicKey.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeSerializable(this.publicKey);
    }
}
