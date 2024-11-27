package android.security.keystore;

/* loaded from: classes3.dex */
public final class ParcelableKeyGenParameterSpec implements android.os.Parcelable {
    private static final int ALGORITHM_PARAMETER_SPEC_EC = 3;
    private static final int ALGORITHM_PARAMETER_SPEC_NONE = 1;
    private static final int ALGORITHM_PARAMETER_SPEC_RSA = 2;
    public static final android.os.Parcelable.Creator<android.security.keystore.ParcelableKeyGenParameterSpec> CREATOR = new android.os.Parcelable.Creator<android.security.keystore.ParcelableKeyGenParameterSpec>() { // from class: android.security.keystore.ParcelableKeyGenParameterSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.ParcelableKeyGenParameterSpec createFromParcel(android.os.Parcel parcel) {
            return new android.security.keystore.ParcelableKeyGenParameterSpec(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.ParcelableKeyGenParameterSpec[] newArray(int i) {
            return new android.security.keystore.ParcelableKeyGenParameterSpec[i];
        }
    };
    private final android.security.keystore.KeyGenParameterSpec mSpec;

    public ParcelableKeyGenParameterSpec(android.security.keystore.KeyGenParameterSpec keyGenParameterSpec) {
        this.mSpec = keyGenParameterSpec;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static void writeOptionalDate(android.os.Parcel parcel, java.util.Date date) {
        if (date != null) {
            parcel.writeBoolean(true);
            parcel.writeLong(date.getTime());
        } else {
            parcel.writeBoolean(false);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mSpec.getKeystoreAlias());
        parcel.writeInt(this.mSpec.getPurposes());
        parcel.writeInt(this.mSpec.getNamespace());
        parcel.writeInt(this.mSpec.getKeySize());
        java.security.spec.AlgorithmParameterSpec algorithmParameterSpec = this.mSpec.getAlgorithmParameterSpec();
        if (algorithmParameterSpec == null) {
            parcel.writeInt(1);
        } else if (algorithmParameterSpec instanceof java.security.spec.RSAKeyGenParameterSpec) {
            java.security.spec.RSAKeyGenParameterSpec rSAKeyGenParameterSpec = (java.security.spec.RSAKeyGenParameterSpec) algorithmParameterSpec;
            parcel.writeInt(2);
            parcel.writeInt(rSAKeyGenParameterSpec.getKeysize());
            parcel.writeByteArray(rSAKeyGenParameterSpec.getPublicExponent().toByteArray());
        } else if (algorithmParameterSpec instanceof java.security.spec.ECGenParameterSpec) {
            parcel.writeInt(3);
            parcel.writeString(((java.security.spec.ECGenParameterSpec) algorithmParameterSpec).getName());
        } else {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Unknown algorithm parameter spec: %s", algorithmParameterSpec.getClass()));
        }
        parcel.writeByteArray(this.mSpec.getCertificateSubject().getEncoded());
        parcel.writeByteArray(this.mSpec.getCertificateSerialNumber().toByteArray());
        parcel.writeLong(this.mSpec.getCertificateNotBefore().getTime());
        parcel.writeLong(this.mSpec.getCertificateNotAfter().getTime());
        writeOptionalDate(parcel, this.mSpec.getKeyValidityStart());
        writeOptionalDate(parcel, this.mSpec.getKeyValidityForOriginationEnd());
        writeOptionalDate(parcel, this.mSpec.getKeyValidityForConsumptionEnd());
        if (this.mSpec.isDigestsSpecified()) {
            parcel.writeStringArray(this.mSpec.getDigests());
        } else {
            parcel.writeStringArray(null);
        }
        if (this.mSpec.isMgf1DigestsSpecified()) {
            parcel.writeStringList(java.util.List.copyOf(this.mSpec.getMgf1Digests()));
        } else {
            parcel.writeStringList(null);
        }
        parcel.writeStringArray(this.mSpec.getEncryptionPaddings());
        parcel.writeStringArray(this.mSpec.getSignaturePaddings());
        parcel.writeStringArray(this.mSpec.getBlockModes());
        parcel.writeBoolean(this.mSpec.isRandomizedEncryptionRequired());
        parcel.writeBoolean(this.mSpec.isUserAuthenticationRequired());
        parcel.writeInt(this.mSpec.getUserAuthenticationValidityDurationSeconds());
        parcel.writeInt(this.mSpec.getUserAuthenticationType());
        parcel.writeBoolean(this.mSpec.isUserPresenceRequired());
        parcel.writeByteArray(this.mSpec.getAttestationChallenge());
        parcel.writeBoolean(this.mSpec.isDevicePropertiesAttestationIncluded());
        parcel.writeIntArray(this.mSpec.getAttestationIds());
        parcel.writeBoolean(this.mSpec.isUniqueIdIncluded());
        parcel.writeBoolean(this.mSpec.isUserAuthenticationValidWhileOnBody());
        parcel.writeBoolean(this.mSpec.isInvalidatedByBiometricEnrollment());
        parcel.writeBoolean(this.mSpec.isStrongBoxBacked());
        parcel.writeBoolean(this.mSpec.isUserConfirmationRequired());
        parcel.writeBoolean(this.mSpec.isUnlockedDeviceRequired());
        parcel.writeBoolean(this.mSpec.isCriticalToDeviceEncryption());
        parcel.writeInt(this.mSpec.getMaxUsageCount());
        parcel.writeString(this.mSpec.getAttestKeyAlias());
        parcel.writeLong(this.mSpec.getBoundToSpecificSecureUserId());
    }

    private static java.util.Date readDateOrNull(android.os.Parcel parcel) {
        if (parcel.readBoolean()) {
            return new java.util.Date(parcel.readLong());
        }
        return null;
    }

    private ParcelableKeyGenParameterSpec(android.os.Parcel parcel) {
        java.security.spec.AlgorithmParameterSpec eCGenParameterSpec;
        java.lang.String readString = parcel.readString();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        int readInt4 = parcel.readInt();
        if (readInt4 == 1) {
            eCGenParameterSpec = null;
        } else if (readInt4 == 2) {
            eCGenParameterSpec = new java.security.spec.RSAKeyGenParameterSpec(parcel.readInt(), new java.math.BigInteger(parcel.createByteArray()));
        } else if (readInt4 == 3) {
            eCGenParameterSpec = new java.security.spec.ECGenParameterSpec(parcel.readString());
        } else {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Unknown algorithm parameter spec: %d", java.lang.Integer.valueOf(readInt4)));
        }
        javax.security.auth.x500.X500Principal x500Principal = new javax.security.auth.x500.X500Principal(parcel.createByteArray());
        java.math.BigInteger bigInteger = new java.math.BigInteger(parcel.createByteArray());
        java.util.Date date = new java.util.Date(parcel.readLong());
        java.util.Date date2 = new java.util.Date(parcel.readLong());
        java.util.Date readDateOrNull = readDateOrNull(parcel);
        java.util.Date readDateOrNull2 = readDateOrNull(parcel);
        java.util.Date readDateOrNull3 = readDateOrNull(parcel);
        java.lang.String[] createStringArray = parcel.createStringArray();
        java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
        this.mSpec = new android.security.keystore.KeyGenParameterSpec(readString, readInt2, readInt3, eCGenParameterSpec, x500Principal, bigInteger, date, date2, readDateOrNull, readDateOrNull2, readDateOrNull3, readInt, createStringArray, createStringArrayList != null ? java.util.Set.copyOf(createStringArrayList) : java.util.Collections.emptySet(), parcel.createStringArray(), parcel.createStringArray(), parcel.createStringArray(), parcel.readBoolean(), parcel.readBoolean(), parcel.readInt(), parcel.readInt(), parcel.readBoolean(), parcel.createByteArray(), parcel.readBoolean(), parcel.createIntArray(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readInt(), parcel.readString(), parcel.readLong());
    }

    public android.security.keystore.KeyGenParameterSpec getSpec() {
        return this.mSpec;
    }
}
