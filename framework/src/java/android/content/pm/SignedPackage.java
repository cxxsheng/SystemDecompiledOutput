package android.content.pm;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes.dex */
public class SignedPackage {
    private final android.content.pm.SignedPackageParcel mData;

    public SignedPackage(java.lang.String str, byte[] bArr) {
        android.content.pm.SignedPackageParcel signedPackageParcel = new android.content.pm.SignedPackageParcel();
        signedPackageParcel.packageName = str;
        signedPackageParcel.certificateDigest = bArr;
        this.mData = signedPackageParcel;
    }

    public SignedPackage(android.content.pm.SignedPackageParcel signedPackageParcel) {
        this.mData = signedPackageParcel;
    }

    public final android.content.pm.SignedPackageParcel getData() {
        return this.mData;
    }

    public java.lang.String getPackageName() {
        return this.mData.packageName;
    }

    public byte[] getCertificateDigest() {
        return this.mData.certificateDigest;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.content.pm.SignedPackage)) {
            return false;
        }
        android.content.pm.SignedPackage signedPackage = (android.content.pm.SignedPackage) obj;
        return this.mData.packageName.equals(signedPackage.mData.packageName) && java.util.Arrays.equals(this.mData.certificateDigest, signedPackage.mData.certificateDigest);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mData.packageName, java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mData.certificateDigest)));
    }
}
