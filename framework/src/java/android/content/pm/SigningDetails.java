package android.content.pm;

/* loaded from: classes.dex */
public final class SigningDetails implements android.os.Parcelable {
    private static final int PAST_CERT_EXISTS = 0;
    private static final java.lang.String TAG = "SigningDetails";
    private final android.content.pm.Signature[] mPastSigningCertificates;
    private final android.util.ArraySet<java.security.PublicKey> mPublicKeys;
    private final int mSignatureSchemeVersion;
    private final android.content.pm.Signature[] mSignatures;
    public static final android.content.pm.SigningDetails UNKNOWN = new android.content.pm.SigningDetails(null, 0, null, null);
    public static final android.os.Parcelable.Creator<android.content.pm.SigningDetails> CREATOR = new android.os.Parcelable.Creator<android.content.pm.SigningDetails>() { // from class: android.content.pm.SigningDetails.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.SigningDetails createFromParcel(android.os.Parcel parcel) {
            if (parcel.readBoolean()) {
                return android.content.pm.SigningDetails.UNKNOWN;
            }
            return new android.content.pm.SigningDetails(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.SigningDetails[] newArray(int i) {
            return new android.content.pm.SigningDetails[i];
        }
    };

    public @interface CapabilityMergeRule {
        public static final int MERGE_OTHER_CAPABILITY = 1;
        public static final int MERGE_RESTRICTED_CAPABILITY = 2;
        public static final int MERGE_SELF_CAPABILITY = 0;
    }

    public @interface CertCapabilities {
        public static final int AUTH = 16;
        public static final int INSTALLED_DATA = 1;
        public static final int PERMISSION = 4;
        public static final int ROLLBACK = 8;
        public static final int SHARED_USER_ID = 2;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SignatureSchemeVersion {
        public static final int JAR = 1;
        public static final int SIGNING_BLOCK_V2 = 2;
        public static final int SIGNING_BLOCK_V3 = 3;
        public static final int SIGNING_BLOCK_V4 = 4;
        public static final int UNKNOWN = 0;
    }

    public SigningDetails(android.content.pm.Signature[] signatureArr, int i, android.util.ArraySet<java.security.PublicKey> arraySet, android.content.pm.Signature[] signatureArr2) {
        this.mSignatures = signatureArr;
        this.mSignatureSchemeVersion = i;
        this.mPublicKeys = arraySet;
        this.mPastSigningCertificates = signatureArr2;
    }

    public SigningDetails(android.content.pm.Signature[] signatureArr, int i, android.content.pm.Signature[] signatureArr2) throws java.security.cert.CertificateException {
        this(signatureArr, i, toSigningKeys(signatureArr), signatureArr2);
    }

    public SigningDetails(android.content.pm.Signature[] signatureArr, int i) throws java.security.cert.CertificateException {
        this(signatureArr, i, null);
    }

    public SigningDetails(android.content.pm.SigningDetails signingDetails) {
        if (signingDetails != null) {
            if (signingDetails.mSignatures != null) {
                this.mSignatures = (android.content.pm.Signature[]) signingDetails.mSignatures.clone();
            } else {
                this.mSignatures = null;
            }
            this.mSignatureSchemeVersion = signingDetails.mSignatureSchemeVersion;
            this.mPublicKeys = new android.util.ArraySet<>((android.util.ArraySet) signingDetails.mPublicKeys);
            if (signingDetails.mPastSigningCertificates != null) {
                this.mPastSigningCertificates = (android.content.pm.Signature[]) signingDetails.mPastSigningCertificates.clone();
                return;
            } else {
                this.mPastSigningCertificates = null;
                return;
            }
        }
        this.mSignatures = null;
        this.mSignatureSchemeVersion = 0;
        this.mPublicKeys = null;
        this.mPastSigningCertificates = null;
    }

    public android.content.pm.SigningDetails mergeLineageWith(android.content.pm.SigningDetails signingDetails) {
        return mergeLineageWith(signingDetails, 1);
    }

    public android.content.pm.SigningDetails mergeLineageWith(android.content.pm.SigningDetails signingDetails, int i) {
        if (!hasPastSigningCertificates()) {
            return (signingDetails.hasPastSigningCertificates() && signingDetails.hasAncestorOrSelf(this)) ? signingDetails : this;
        }
        if (!signingDetails.hasPastSigningCertificates()) {
            return this;
        }
        android.content.pm.SigningDetails descendantOrSelf = getDescendantOrSelf(signingDetails);
        if (descendantOrSelf == null) {
            return this;
        }
        if (descendantOrSelf == this) {
            return mergeLineageWithAncestorOrSelf(signingDetails, i);
        }
        switch (i) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0094, code lost:
    
        if (r8 < 0) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0096, code lost:
    
        return r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.content.pm.SigningDetails mergeLineageWithAncestorOrSelf(android.content.pm.SigningDetails signingDetails, int i) {
        int i2;
        int i3;
        int length = this.mPastSigningCertificates.length - 1;
        int length2 = signingDetails.mPastSigningCertificates.length - 1;
        if (length < 0 || length2 < 0) {
            return this;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (length >= 0 && !this.mPastSigningCertificates[length].equals(signingDetails.mPastSigningCertificates[length2])) {
            arrayList.add(new android.content.pm.Signature(this.mPastSigningCertificates[length]));
            length--;
        }
        if (length >= 0) {
            boolean z = false;
            while (true) {
                i2 = length - 1;
                android.content.pm.Signature signature = this.mPastSigningCertificates[length];
                i3 = length2 - 1;
                android.content.pm.Signature signature2 = signingDetails.mPastSigningCertificates[length2];
                android.content.pm.Signature signature3 = new android.content.pm.Signature(signature);
                if (signature.getFlags() != signature2.getFlags()) {
                    switch (i) {
                        case 0:
                            signature3.setFlags(signature.getFlags());
                            break;
                        case 1:
                            signature3.setFlags(signature2.getFlags());
                            break;
                        case 2:
                            signature3.setFlags(signature.getFlags() & signature2.getFlags());
                            break;
                    }
                    z = true;
                }
                arrayList.add(signature3);
                if (i2 >= 0 && i3 >= 0 && this.mPastSigningCertificates[i2].equals(signingDetails.mPastSigningCertificates[i3])) {
                    length = i2;
                    length2 = i3;
                }
            }
            while (i3 >= 0) {
                arrayList.add(new android.content.pm.Signature(signingDetails.mPastSigningCertificates[i3]));
                i3--;
            }
            while (i2 >= 0) {
                arrayList.add(new android.content.pm.Signature(this.mPastSigningCertificates[i2]));
                i2--;
            }
            if (arrayList.size() == this.mPastSigningCertificates.length && !z) {
                return this;
            }
            java.util.Collections.reverse(arrayList);
            try {
                return new android.content.pm.SigningDetails(new android.content.pm.Signature[]{new android.content.pm.Signature(this.mSignatures[0])}, this.mSignatureSchemeVersion, (android.content.pm.Signature[]) arrayList.toArray(new android.content.pm.Signature[0]));
            } catch (java.security.cert.CertificateException e) {
                android.util.Slog.e(TAG, "Caught an exception creating the merged lineage: ", e);
                return this;
            }
        }
        return this;
    }

    public boolean hasCommonAncestor(android.content.pm.SigningDetails signingDetails) {
        if (!hasPastSigningCertificates()) {
            return signingDetails.hasAncestorOrSelf(this);
        }
        if (signingDetails.hasPastSigningCertificates()) {
            return getDescendantOrSelf(signingDetails) != null;
        }
        return hasAncestorOrSelf(signingDetails);
    }

    public boolean hasAncestorOrSelfWithDigest(java.util.Set<java.lang.String> set) {
        if (this == UNKNOWN || set == null || set.size() == 0) {
            return false;
        }
        if (this.mSignatures.length > 1) {
            if (set.size() < this.mSignatures.length) {
                return false;
            }
            for (android.content.pm.Signature signature : this.mSignatures) {
                if (!set.contains(android.util.PackageUtils.computeSha256Digest(signature.toByteArray()))) {
                    return false;
                }
            }
            return true;
        }
        if (set.contains(android.util.PackageUtils.computeSha256Digest(this.mSignatures[0].toByteArray()))) {
            return true;
        }
        if (hasPastSigningCertificates()) {
            for (int i = 0; i < this.mPastSigningCertificates.length - 1; i++) {
                if (set.contains(android.util.PackageUtils.computeSha256Digest(this.mPastSigningCertificates[i].toByteArray()))) {
                    return true;
                }
            }
        }
        return false;
    }

    private android.content.pm.SigningDetails getDescendantOrSelf(android.content.pm.SigningDetails signingDetails) {
        android.content.pm.SigningDetails signingDetails2;
        if (hasAncestorOrSelf(signingDetails)) {
            signingDetails2 = signingDetails;
            signingDetails = this;
        } else {
            if (!signingDetails.hasAncestor(this)) {
                return null;
            }
            signingDetails2 = this;
        }
        int length = signingDetails.mPastSigningCertificates.length - 1;
        int length2 = signingDetails2.mPastSigningCertificates.length - 1;
        while (length >= 0 && !signingDetails.mPastSigningCertificates[length].equals(signingDetails2.mPastSigningCertificates[length2])) {
            length--;
        }
        if (length < 0) {
            return null;
        }
        do {
            length--;
            length2--;
            if (length < 0 || length2 < 0) {
                break;
            }
        } while (signingDetails.mPastSigningCertificates[length].equals(signingDetails2.mPastSigningCertificates[length2]));
        if (length < 0 || length2 < 0) {
            return signingDetails;
        }
        return null;
    }

    public boolean hasSignatures() {
        return this.mSignatures != null && this.mSignatures.length > 0;
    }

    public boolean hasPastSigningCertificates() {
        return this.mPastSigningCertificates != null && this.mPastSigningCertificates.length > 0;
    }

    public boolean hasAncestorOrSelf(android.content.pm.SigningDetails signingDetails) {
        if (this == UNKNOWN || signingDetails == UNKNOWN) {
            return false;
        }
        if (signingDetails.mSignatures.length > 1) {
            return signaturesMatchExactly(signingDetails);
        }
        return hasCertificate(signingDetails.mSignatures[0]);
    }

    public boolean hasAncestor(android.content.pm.SigningDetails signingDetails) {
        if (this != UNKNOWN && signingDetails != UNKNOWN && hasPastSigningCertificates() && signingDetails.mSignatures.length == 1) {
            for (int i = 0; i < this.mPastSigningCertificates.length - 1; i++) {
                if (this.mPastSigningCertificates[i].equals(signingDetails.mSignatures[0])) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasCommonSignerWithCapability(android.content.pm.SigningDetails signingDetails, int i) {
        if (this == UNKNOWN || signingDetails == UNKNOWN) {
            return false;
        }
        if (this.mSignatures.length > 1 || signingDetails.mSignatures.length > 1) {
            return signaturesMatchExactly(signingDetails);
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        if (signingDetails.hasPastSigningCertificates()) {
            arraySet.addAll(java.util.Arrays.asList(signingDetails.mPastSigningCertificates));
        } else {
            arraySet.addAll(java.util.Arrays.asList(signingDetails.mSignatures));
        }
        if (arraySet.contains(this.mSignatures[0])) {
            return true;
        }
        if (hasPastSigningCertificates()) {
            for (int i2 = 0; i2 < this.mPastSigningCertificates.length - 1; i2++) {
                if (arraySet.contains(this.mPastSigningCertificates[i2]) && (this.mPastSigningCertificates[i2].getFlags() & i) == i) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkCapability(android.content.pm.SigningDetails signingDetails, int i) {
        if (this == UNKNOWN || signingDetails == UNKNOWN) {
            return false;
        }
        if (signingDetails.mSignatures.length > 1) {
            return signaturesMatchExactly(signingDetails);
        }
        return hasCertificate(signingDetails.mSignatures[0], i);
    }

    public boolean checkCapabilityRecover(android.content.pm.SigningDetails signingDetails, int i) throws java.security.cert.CertificateException {
        if (signingDetails == UNKNOWN || this == UNKNOWN) {
            return false;
        }
        if (hasPastSigningCertificates() && signingDetails.mSignatures.length == 1) {
            for (int i2 = 0; i2 < this.mPastSigningCertificates.length; i2++) {
                if (android.content.pm.Signature.areEffectiveMatch(signingDetails.mSignatures[0], this.mPastSigningCertificates[i2]) && this.mPastSigningCertificates[i2].getFlags() == i) {
                    return true;
                }
            }
            return false;
        }
        return android.content.pm.Signature.areEffectiveMatch(signingDetails, this);
    }

    public boolean hasCertificate(android.content.pm.Signature signature) {
        return hasCertificateInternal(signature, 0);
    }

    public boolean hasCertificate(android.content.pm.Signature signature, int i) {
        return hasCertificateInternal(signature, i);
    }

    public boolean hasCertificate(byte[] bArr) {
        return hasCertificate(new android.content.pm.Signature(bArr));
    }

    private boolean hasCertificateInternal(android.content.pm.Signature signature, int i) {
        if (this == UNKNOWN) {
            return false;
        }
        if (hasPastSigningCertificates()) {
            for (int i2 = 0; i2 < this.mPastSigningCertificates.length - 1; i2++) {
                if (this.mPastSigningCertificates[i2].equals(signature) && (i == 0 || (this.mPastSigningCertificates[i2].getFlags() & i) == i)) {
                    return true;
                }
            }
        }
        return this.mSignatures.length == 1 && this.mSignatures[0].equals(signature);
    }

    public boolean checkCapability(java.lang.String str, int i) {
        if (this == UNKNOWN || android.text.TextUtils.isEmpty(str)) {
            return false;
        }
        if (hasSha256Certificate(libcore.util.HexEncoding.decode(str, false), i)) {
            return true;
        }
        return android.util.PackageUtils.computeSignaturesSha256Digest(android.util.PackageUtils.computeSignaturesSha256Digests(this.mSignatures)).equals(str);
    }

    public boolean hasSha256Certificate(byte[] bArr) {
        return hasSha256CertificateInternal(bArr, 0);
    }

    public boolean hasSha256Certificate(byte[] bArr, int i) {
        return hasSha256CertificateInternal(bArr, i);
    }

    private boolean hasSha256CertificateInternal(byte[] bArr, int i) {
        if (this == UNKNOWN) {
            return false;
        }
        if (hasPastSigningCertificates()) {
            for (int i2 = 0; i2 < this.mPastSigningCertificates.length - 1; i2++) {
                if (java.util.Arrays.equals(bArr, android.util.PackageUtils.computeSha256DigestBytes(this.mPastSigningCertificates[i2].toByteArray())) && (i == 0 || (this.mPastSigningCertificates[i2].getFlags() & i) == i)) {
                    return true;
                }
            }
        }
        if (this.mSignatures.length == 1) {
            return java.util.Arrays.equals(bArr, android.util.PackageUtils.computeSha256DigestBytes(this.mSignatures[0].toByteArray()));
        }
        return false;
    }

    public boolean signaturesMatchExactly(android.content.pm.SigningDetails signingDetails) {
        return android.content.pm.Signature.areExactMatch(this, signingDetails);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        boolean z = UNKNOWN == this;
        parcel.writeBoolean(z);
        if (z) {
            return;
        }
        parcel.writeTypedArray(this.mSignatures, i);
        parcel.writeInt(this.mSignatureSchemeVersion);
        parcel.writeArraySet(this.mPublicKeys);
        parcel.writeTypedArray(this.mPastSigningCertificates, i);
    }

    protected SigningDetails(android.os.Parcel parcel) {
        java.lang.ClassLoader classLoader = java.lang.Object.class.getClassLoader();
        this.mSignatures = (android.content.pm.Signature[]) parcel.createTypedArray(android.content.pm.Signature.CREATOR);
        this.mSignatureSchemeVersion = parcel.readInt();
        this.mPublicKeys = parcel.readArraySet(classLoader);
        this.mPastSigningCertificates = (android.content.pm.Signature[]) parcel.createTypedArray(android.content.pm.Signature.CREATOR);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.content.pm.SigningDetails)) {
            return false;
        }
        android.content.pm.SigningDetails signingDetails = (android.content.pm.SigningDetails) obj;
        if (this.mSignatureSchemeVersion != signingDetails.mSignatureSchemeVersion || !android.content.pm.Signature.areExactMatch(this, signingDetails)) {
            return false;
        }
        if (this.mPublicKeys != null) {
            if (!this.mPublicKeys.equals(signingDetails.mPublicKeys)) {
                return false;
            }
        } else if (signingDetails.mPublicKeys != null) {
            return false;
        }
        if (!java.util.Arrays.equals(this.mPastSigningCertificates, signingDetails.mPastSigningCertificates)) {
            return false;
        }
        if (this.mPastSigningCertificates != null) {
            for (int i = 0; i < this.mPastSigningCertificates.length; i++) {
                if (this.mPastSigningCertificates[i].getFlags() != signingDetails.mPastSigningCertificates[i].getFlags()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        return (((((java.util.Arrays.hashCode(this.mSignatures) * 31) + this.mSignatureSchemeVersion) * 31) + (this.mPublicKeys != null ? this.mPublicKeys.hashCode() : 0)) * 31) + java.util.Arrays.hashCode(this.mPastSigningCertificates);
    }

    public static class Builder {
        private android.content.pm.Signature[] mPastSigningCertificates;
        private int mSignatureSchemeVersion = 0;
        private android.content.pm.Signature[] mSignatures;

        public android.content.pm.SigningDetails.Builder setSignatures(android.content.pm.Signature[] signatureArr) {
            this.mSignatures = signatureArr;
            return this;
        }

        public android.content.pm.SigningDetails.Builder setSignatureSchemeVersion(int i) {
            this.mSignatureSchemeVersion = i;
            return this;
        }

        public android.content.pm.SigningDetails.Builder setPastSigningCertificates(android.content.pm.Signature[] signatureArr) {
            this.mPastSigningCertificates = signatureArr;
            return this;
        }

        private void checkInvariants() {
            if (this.mSignatures == null) {
                throw new java.lang.IllegalStateException("SigningDetails requires the current signing certificates.");
            }
        }

        public android.content.pm.SigningDetails build() throws java.security.cert.CertificateException {
            checkInvariants();
            return new android.content.pm.SigningDetails(this.mSignatures, this.mSignatureSchemeVersion, this.mPastSigningCertificates);
        }
    }

    public static android.util.ArraySet<java.security.PublicKey> toSigningKeys(android.content.pm.Signature[] signatureArr) throws java.security.cert.CertificateException {
        android.util.ArraySet<java.security.PublicKey> arraySet = new android.util.ArraySet<>(signatureArr.length);
        for (android.content.pm.Signature signature : signatureArr) {
            arraySet.add(signature.getPublicKey());
        }
        return arraySet;
    }

    public android.content.pm.Signature[] getSignatures() {
        return this.mSignatures;
    }

    public int getSignatureSchemeVersion() {
        return this.mSignatureSchemeVersion;
    }

    public android.util.ArraySet<java.security.PublicKey> getPublicKeys() {
        return this.mPublicKeys;
    }

    public android.content.pm.Signature[] getPastSigningCertificates() {
        return this.mPastSigningCertificates;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
