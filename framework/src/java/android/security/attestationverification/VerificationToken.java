package android.security.attestationverification;

/* loaded from: classes3.dex */
public final class VerificationToken implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.attestationverification.VerificationToken> CREATOR;
    static com.android.internal.util.Parcelling<java.time.Instant> sParcellingForVerificationTime;
    private final android.security.attestationverification.AttestationProfile mAttestationProfile;
    private final byte[] mHmac;
    private final int mLocalBindingType;
    private final android.os.Bundle mRequirements;
    private int mUid;
    private final int mVerificationResult;
    private final java.time.Instant mVerificationTime;

    VerificationToken(android.security.attestationverification.AttestationProfile attestationProfile, int i, android.os.Bundle bundle, int i2, java.time.Instant instant, byte[] bArr, int i3) {
        this.mAttestationProfile = attestationProfile;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAttestationProfile);
        this.mLocalBindingType = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.security.attestationverification.AttestationVerificationManager.LocalBindingType.class, (java.lang.annotation.Annotation) null, this.mLocalBindingType);
        this.mRequirements = bundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mRequirements);
        this.mVerificationResult = i2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.security.attestationverification.AttestationVerificationManager.VerificationResult.class, (java.lang.annotation.Annotation) null, this.mVerificationResult);
        this.mVerificationTime = instant;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mVerificationTime);
        this.mHmac = bArr;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHmac);
        this.mUid = i3;
    }

    public android.security.attestationverification.AttestationProfile getAttestationProfile() {
        return this.mAttestationProfile;
    }

    public int getLocalBindingType() {
        return this.mLocalBindingType;
    }

    public android.os.Bundle getRequirements() {
        return this.mRequirements;
    }

    public int getVerificationResult() {
        return this.mVerificationResult;
    }

    public java.time.Instant getVerificationTime() {
        return this.mVerificationTime;
    }

    public byte[] getHmac() {
        return this.mHmac;
    }

    public int getUid() {
        return this.mUid;
    }

    static {
        sParcellingForVerificationTime = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForInstant.class);
        if (sParcellingForVerificationTime == null) {
            sParcellingForVerificationTime = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForInstant());
        }
        CREATOR = new android.os.Parcelable.Creator<android.security.attestationverification.VerificationToken>() { // from class: android.security.attestationverification.VerificationToken.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.security.attestationverification.VerificationToken[] newArray(int i) {
                return new android.security.attestationverification.VerificationToken[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.security.attestationverification.VerificationToken createFromParcel(android.os.Parcel parcel) {
                return new android.security.attestationverification.VerificationToken(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mAttestationProfile, i);
        parcel.writeInt(this.mLocalBindingType);
        parcel.writeBundle(this.mRequirements);
        parcel.writeInt(this.mVerificationResult);
        sParcellingForVerificationTime.parcel(this.mVerificationTime, parcel, i);
        parcel.writeByteArray(this.mHmac);
        parcel.writeInt(this.mUid);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VerificationToken(android.os.Parcel parcel) {
        android.security.attestationverification.AttestationProfile attestationProfile = (android.security.attestationverification.AttestationProfile) parcel.readTypedObject(android.security.attestationverification.AttestationProfile.CREATOR);
        int readInt = parcel.readInt();
        android.os.Bundle readBundle = parcel.readBundle();
        int readInt2 = parcel.readInt();
        java.time.Instant unparcel = sParcellingForVerificationTime.unparcel(parcel);
        byte[] createByteArray = parcel.createByteArray();
        int readInt3 = parcel.readInt();
        this.mAttestationProfile = attestationProfile;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAttestationProfile);
        this.mLocalBindingType = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.security.attestationverification.AttestationVerificationManager.LocalBindingType.class, (java.lang.annotation.Annotation) null, this.mLocalBindingType);
        this.mRequirements = readBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mRequirements);
        this.mVerificationResult = readInt2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.security.attestationverification.AttestationVerificationManager.VerificationResult.class, (java.lang.annotation.Annotation) null, this.mVerificationResult);
        this.mVerificationTime = unparcel;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mVerificationTime);
        this.mHmac = createByteArray;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHmac);
        this.mUid = readInt3;
    }

    public static final class Builder {
        private android.security.attestationverification.AttestationProfile mAttestationProfile;
        private long mBuilderFieldsSet = 0;
        private byte[] mHmac;
        private int mLocalBindingType;
        private android.os.Bundle mRequirements;
        private int mUid;
        private int mVerificationResult;
        private java.time.Instant mVerificationTime;

        public Builder(android.security.attestationverification.AttestationProfile attestationProfile, int i, android.os.Bundle bundle, int i2, java.time.Instant instant, byte[] bArr, int i3) {
            this.mAttestationProfile = attestationProfile;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAttestationProfile);
            this.mLocalBindingType = i;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.security.attestationverification.AttestationVerificationManager.LocalBindingType.class, (java.lang.annotation.Annotation) null, this.mLocalBindingType);
            this.mRequirements = bundle;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mRequirements);
            this.mVerificationResult = i2;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.security.attestationverification.AttestationVerificationManager.VerificationResult.class, (java.lang.annotation.Annotation) null, this.mVerificationResult);
            this.mVerificationTime = instant;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mVerificationTime);
            this.mHmac = bArr;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHmac);
            this.mUid = i3;
        }

        public android.security.attestationverification.VerificationToken.Builder setAttestationProfile(android.security.attestationverification.AttestationProfile attestationProfile) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mAttestationProfile = attestationProfile;
            return this;
        }

        public android.security.attestationverification.VerificationToken.Builder setLocalBindingType(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mLocalBindingType = i;
            return this;
        }

        public android.security.attestationverification.VerificationToken.Builder setRequirements(android.os.Bundle bundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mRequirements = bundle;
            return this;
        }

        public android.security.attestationverification.VerificationToken.Builder setVerificationResult(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mVerificationResult = i;
            return this;
        }

        public android.security.attestationverification.VerificationToken.Builder setVerificationTime(java.time.Instant instant) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mVerificationTime = instant;
            return this;
        }

        public android.security.attestationverification.VerificationToken.Builder setHmac(byte... bArr) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mHmac = bArr;
            return this;
        }

        public android.security.attestationverification.VerificationToken.Builder setUid(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mUid = i;
            return this;
        }

        public android.security.attestationverification.VerificationToken build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            return new android.security.attestationverification.VerificationToken(this.mAttestationProfile, this.mLocalBindingType, this.mRequirements, this.mVerificationResult, this.mVerificationTime, this.mHmac, this.mUid);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 128) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
