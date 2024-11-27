package android.content.pm;

/* loaded from: classes.dex */
public final class SigningInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.SigningInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.SigningInfo>() { // from class: android.content.pm.SigningInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.SigningInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.SigningInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.SigningInfo[] newArray(int i) {
            return new android.content.pm.SigningInfo[i];
        }
    };
    private final android.content.pm.SigningDetails mSigningDetails;

    public SigningInfo() {
        this.mSigningDetails = android.content.pm.SigningDetails.UNKNOWN;
    }

    public SigningInfo(int i, java.util.Collection<android.content.pm.Signature> collection, java.util.Collection<java.security.PublicKey> collection2, java.util.Collection<android.content.pm.Signature> collection3) {
        android.content.pm.Signature[] signatureArr;
        android.content.pm.Signature[] signatureArr2;
        if (i <= 0 || collection == null) {
            this.mSigningDetails = android.content.pm.SigningDetails.UNKNOWN;
            return;
        }
        android.util.ArraySet arraySet = null;
        if (collection != null && !collection.isEmpty()) {
            signatureArr = (android.content.pm.Signature[]) collection.toArray(new android.content.pm.Signature[collection.size()]);
        } else {
            signatureArr = null;
        }
        if (collection3 != null && !collection3.isEmpty()) {
            signatureArr2 = (android.content.pm.Signature[]) collection3.toArray(new android.content.pm.Signature[collection3.size()]);
        } else {
            signatureArr2 = null;
        }
        signatureArr2 = android.content.pm.Signature.areExactArraysMatch(signatureArr, signatureArr2) ? null : signatureArr2;
        if (collection2 != null && !collection2.isEmpty()) {
            arraySet = new android.util.ArraySet(collection2);
        }
        this.mSigningDetails = new android.content.pm.SigningDetails(signatureArr, i, arraySet, signatureArr2);
    }

    public SigningInfo(android.content.pm.SigningDetails signingDetails) {
        this.mSigningDetails = new android.content.pm.SigningDetails(signingDetails);
    }

    public SigningInfo(android.content.pm.SigningInfo signingInfo) {
        this.mSigningDetails = new android.content.pm.SigningDetails(signingInfo.mSigningDetails);
    }

    private SigningInfo(android.os.Parcel parcel) {
        this.mSigningDetails = android.content.pm.SigningDetails.CREATOR.createFromParcel(parcel);
    }

    public boolean hasMultipleSigners() {
        return this.mSigningDetails.getSignatures() != null && this.mSigningDetails.getSignatures().length > 1;
    }

    public boolean hasPastSigningCertificates() {
        return this.mSigningDetails.getPastSigningCertificates() != null && this.mSigningDetails.getPastSigningCertificates().length > 0;
    }

    public android.content.pm.Signature[] getSigningCertificateHistory() {
        if (hasMultipleSigners()) {
            return null;
        }
        if (!hasPastSigningCertificates()) {
            return this.mSigningDetails.getSignatures();
        }
        return this.mSigningDetails.getPastSigningCertificates();
    }

    public android.content.pm.Signature[] getApkContentsSigners() {
        return this.mSigningDetails.getSignatures();
    }

    public int getSchemeVersion() {
        return this.mSigningDetails.getSignatureSchemeVersion();
    }

    public java.util.Collection<java.security.PublicKey> getPublicKeys() {
        return this.mSigningDetails.getPublicKeys();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mSigningDetails.writeToParcel(parcel, i);
    }

    public android.content.pm.SigningDetails getSigningDetails() {
        return this.mSigningDetails;
    }
}
