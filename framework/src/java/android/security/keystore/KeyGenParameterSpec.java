package android.security.keystore;

/* loaded from: classes3.dex */
public final class KeyGenParameterSpec implements java.security.spec.AlgorithmParameterSpec, android.security.keystore.UserAuthArgs {
    private final java.lang.String mAttestKeyAlias;
    private final byte[] mAttestationChallenge;
    private final int[] mAttestationIds;
    private final java.lang.String[] mBlockModes;
    private final long mBoundToSecureUserId;
    private final java.util.Date mCertificateNotAfter;
    private final java.util.Date mCertificateNotBefore;
    private final java.math.BigInteger mCertificateSerialNumber;
    private final javax.security.auth.x500.X500Principal mCertificateSubject;
    private final boolean mCriticalToDeviceEncryption;
    private final boolean mDevicePropertiesAttestationIncluded;
    private final java.lang.String[] mDigests;
    private final java.lang.String[] mEncryptionPaddings;
    private final boolean mInvalidatedByBiometricEnrollment;
    private final boolean mIsStrongBoxBacked;
    private final int mKeySize;
    private final java.util.Date mKeyValidityForConsumptionEnd;
    private final java.util.Date mKeyValidityForOriginationEnd;
    private final java.util.Date mKeyValidityStart;
    private final java.lang.String mKeystoreAlias;
    private final int mMaxUsageCount;
    private final java.util.Set<java.lang.String> mMgf1Digests;
    private final int mNamespace;
    private final int mPurposes;
    private final boolean mRandomizedEncryptionRequired;
    private final java.lang.String[] mSignaturePaddings;
    private final java.security.spec.AlgorithmParameterSpec mSpec;
    private final boolean mUniqueIdIncluded;
    private final boolean mUnlockedDeviceRequired;
    private final boolean mUserAuthenticationRequired;
    private final int mUserAuthenticationType;
    private final boolean mUserAuthenticationValidWhileOnBody;
    private final int mUserAuthenticationValidityDurationSeconds;
    private final boolean mUserConfirmationRequired;
    private final boolean mUserPresenceRequired;
    private static final javax.security.auth.x500.X500Principal DEFAULT_ATTESTATION_CERT_SUBJECT = new javax.security.auth.x500.X500Principal("CN=Android Keystore Key");
    private static final javax.security.auth.x500.X500Principal DEFAULT_SELF_SIGNED_CERT_SUBJECT = new javax.security.auth.x500.X500Principal("CN=Fake");
    private static final java.math.BigInteger DEFAULT_CERT_SERIAL_NUMBER = new java.math.BigInteger("1");
    private static final java.util.Date DEFAULT_CERT_NOT_BEFORE = new java.util.Date(0);
    private static final java.util.Date DEFAULT_CERT_NOT_AFTER = new java.util.Date(2461449600000L);

    public KeyGenParameterSpec(java.lang.String str, int i, int i2, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, javax.security.auth.x500.X500Principal x500Principal, java.math.BigInteger bigInteger, java.util.Date date, java.util.Date date2, java.util.Date date3, java.util.Date date4, java.util.Date date5, int i3, java.lang.String[] strArr, java.util.Set<java.lang.String> set, java.lang.String[] strArr2, java.lang.String[] strArr3, java.lang.String[] strArr4, boolean z, boolean z2, int i4, int i5, boolean z3, byte[] bArr, boolean z4, int[] iArr, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, int i6, java.lang.String str2, long j) {
        javax.security.auth.x500.X500Principal x500Principal2;
        java.util.Date date6;
        java.util.Date date7;
        java.math.BigInteger bigInteger2;
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("keyStoreAlias must not be empty");
        }
        if (x500Principal != null) {
            x500Principal2 = x500Principal;
        } else if (bArr == null) {
            x500Principal2 = DEFAULT_SELF_SIGNED_CERT_SUBJECT;
        } else {
            x500Principal2 = DEFAULT_ATTESTATION_CERT_SUBJECT;
        }
        if (date != null) {
            date6 = date;
        } else {
            date6 = DEFAULT_CERT_NOT_BEFORE;
        }
        if (date2 != null) {
            date7 = date2;
        } else {
            date7 = DEFAULT_CERT_NOT_AFTER;
        }
        if (bigInteger != null) {
            bigInteger2 = bigInteger;
        } else {
            bigInteger2 = DEFAULT_CERT_SERIAL_NUMBER;
        }
        if (!date7.before(date6)) {
            this.mKeystoreAlias = str;
            this.mNamespace = i;
            this.mKeySize = i2;
            this.mSpec = algorithmParameterSpec;
            this.mCertificateSubject = x500Principal2;
            this.mCertificateSerialNumber = bigInteger2;
            this.mCertificateNotBefore = android.security.keystore.Utils.cloneIfNotNull(date6);
            this.mCertificateNotAfter = android.security.keystore.Utils.cloneIfNotNull(date7);
            this.mKeyValidityStart = android.security.keystore.Utils.cloneIfNotNull(date3);
            this.mKeyValidityForOriginationEnd = android.security.keystore.Utils.cloneIfNotNull(date4);
            this.mKeyValidityForConsumptionEnd = android.security.keystore.Utils.cloneIfNotNull(date5);
            this.mPurposes = i3;
            this.mDigests = android.security.keystore.ArrayUtils.cloneIfNotEmpty(strArr);
            this.mMgf1Digests = set != null ? set : java.util.Collections.emptySet();
            this.mEncryptionPaddings = android.security.keystore.ArrayUtils.cloneIfNotEmpty(android.security.keystore.ArrayUtils.nullToEmpty(strArr2));
            this.mSignaturePaddings = android.security.keystore.ArrayUtils.cloneIfNotEmpty(android.security.keystore.ArrayUtils.nullToEmpty(strArr3));
            this.mBlockModes = android.security.keystore.ArrayUtils.cloneIfNotEmpty(android.security.keystore.ArrayUtils.nullToEmpty(strArr4));
            this.mRandomizedEncryptionRequired = z;
            this.mUserAuthenticationRequired = z2;
            this.mUserPresenceRequired = z3;
            this.mUserAuthenticationValidityDurationSeconds = i4;
            this.mUserAuthenticationType = i5;
            this.mAttestationChallenge = android.security.keystore.Utils.cloneIfNotNull(bArr);
            this.mDevicePropertiesAttestationIncluded = z4;
            this.mAttestationIds = iArr;
            this.mUniqueIdIncluded = z5;
            this.mUserAuthenticationValidWhileOnBody = z6;
            this.mInvalidatedByBiometricEnrollment = z7;
            this.mIsStrongBoxBacked = z8;
            this.mUserConfirmationRequired = z9;
            this.mUnlockedDeviceRequired = z10;
            this.mCriticalToDeviceEncryption = z11;
            this.mMaxUsageCount = i6;
            this.mAttestKeyAlias = str2;
            this.mBoundToSecureUserId = j;
            return;
        }
        throw new java.lang.IllegalArgumentException("certificateNotAfter < certificateNotBefore");
    }

    public java.lang.String getKeystoreAlias() {
        return this.mKeystoreAlias;
    }

    @java.lang.Deprecated
    public int getUid() {
        try {
            return android.security.keystore.KeyProperties.namespaceToLegacyUid(this.mNamespace);
        } catch (java.lang.IllegalArgumentException e) {
            throw new java.lang.IllegalStateException("getUid called on KeyGenParameterSpec with non legacy keystore namespace.", e);
        }
    }

    @android.annotation.SystemApi
    public int getNamespace() {
        return this.mNamespace;
    }

    public int getKeySize() {
        return this.mKeySize;
    }

    public java.security.spec.AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return this.mSpec;
    }

    public javax.security.auth.x500.X500Principal getCertificateSubject() {
        return this.mCertificateSubject;
    }

    public java.math.BigInteger getCertificateSerialNumber() {
        return this.mCertificateSerialNumber;
    }

    public java.util.Date getCertificateNotBefore() {
        return android.security.keystore.Utils.cloneIfNotNull(this.mCertificateNotBefore);
    }

    public java.util.Date getCertificateNotAfter() {
        return android.security.keystore.Utils.cloneIfNotNull(this.mCertificateNotAfter);
    }

    public java.util.Date getKeyValidityStart() {
        return android.security.keystore.Utils.cloneIfNotNull(this.mKeyValidityStart);
    }

    public java.util.Date getKeyValidityForConsumptionEnd() {
        return android.security.keystore.Utils.cloneIfNotNull(this.mKeyValidityForConsumptionEnd);
    }

    public java.util.Date getKeyValidityForOriginationEnd() {
        return android.security.keystore.Utils.cloneIfNotNull(this.mKeyValidityForOriginationEnd);
    }

    public int getPurposes() {
        return this.mPurposes;
    }

    public java.lang.String[] getDigests() {
        if (this.mDigests == null) {
            throw new java.lang.IllegalStateException("Digests not specified");
        }
        return android.security.keystore.ArrayUtils.cloneIfNotEmpty(this.mDigests);
    }

    public boolean isDigestsSpecified() {
        return this.mDigests != null;
    }

    public java.util.Set<java.lang.String> getMgf1Digests() {
        if (this.mMgf1Digests.isEmpty()) {
            throw new java.lang.IllegalStateException("Mask generation function (MGF) not specified");
        }
        return new java.util.HashSet(this.mMgf1Digests);
    }

    public boolean isMgf1DigestsSpecified() {
        return !this.mMgf1Digests.isEmpty();
    }

    public java.lang.String[] getEncryptionPaddings() {
        return android.security.keystore.ArrayUtils.cloneIfNotEmpty(this.mEncryptionPaddings);
    }

    public java.lang.String[] getSignaturePaddings() {
        return android.security.keystore.ArrayUtils.cloneIfNotEmpty(this.mSignaturePaddings);
    }

    public java.lang.String[] getBlockModes() {
        return android.security.keystore.ArrayUtils.cloneIfNotEmpty(this.mBlockModes);
    }

    public boolean isRandomizedEncryptionRequired() {
        return this.mRandomizedEncryptionRequired;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUserAuthenticationRequired() {
        return this.mUserAuthenticationRequired;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUserConfirmationRequired() {
        return this.mUserConfirmationRequired;
    }

    @Override // android.security.keystore.UserAuthArgs
    public int getUserAuthenticationValidityDurationSeconds() {
        return this.mUserAuthenticationValidityDurationSeconds;
    }

    @Override // android.security.keystore.UserAuthArgs
    public int getUserAuthenticationType() {
        return this.mUserAuthenticationType;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUserPresenceRequired() {
        return this.mUserPresenceRequired;
    }

    public byte[] getAttestationChallenge() {
        return android.security.keystore.Utils.cloneIfNotNull(this.mAttestationChallenge);
    }

    public boolean isDevicePropertiesAttestationIncluded() {
        return this.mDevicePropertiesAttestationIncluded;
    }

    @android.annotation.SystemApi
    public int[] getAttestationIds() {
        return (int[]) this.mAttestationIds.clone();
    }

    public boolean isUniqueIdIncluded() {
        return this.mUniqueIdIncluded;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUserAuthenticationValidWhileOnBody() {
        return this.mUserAuthenticationValidWhileOnBody;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isInvalidatedByBiometricEnrollment() {
        return this.mInvalidatedByBiometricEnrollment;
    }

    public boolean isStrongBoxBacked() {
        return this.mIsStrongBoxBacked;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUnlockedDeviceRequired() {
        return this.mUnlockedDeviceRequired;
    }

    @Override // android.security.keystore.UserAuthArgs
    public long getBoundToSpecificSecureUserId() {
        return this.mBoundToSecureUserId;
    }

    public boolean isCriticalToDeviceEncryption() {
        return this.mCriticalToDeviceEncryption;
    }

    public int getMaxUsageCount() {
        return this.mMaxUsageCount;
    }

    public java.lang.String getAttestKeyAlias() {
        return this.mAttestKeyAlias;
    }

    public static final class Builder {
        private java.lang.String mAttestKeyAlias;
        private byte[] mAttestationChallenge;
        private int[] mAttestationIds;
        private java.lang.String[] mBlockModes;
        private long mBoundToSecureUserId;
        private java.util.Date mCertificateNotAfter;
        private java.util.Date mCertificateNotBefore;
        private java.math.BigInteger mCertificateSerialNumber;
        private javax.security.auth.x500.X500Principal mCertificateSubject;
        private boolean mCriticalToDeviceEncryption;
        private boolean mDevicePropertiesAttestationIncluded;
        private java.lang.String[] mDigests;
        private java.lang.String[] mEncryptionPaddings;
        private boolean mInvalidatedByBiometricEnrollment;
        private boolean mIsStrongBoxBacked;
        private int mKeySize;
        private java.util.Date mKeyValidityForConsumptionEnd;
        private java.util.Date mKeyValidityForOriginationEnd;
        private java.util.Date mKeyValidityStart;
        private final java.lang.String mKeystoreAlias;
        private int mMaxUsageCount;
        private java.util.Set<java.lang.String> mMgf1Digests;
        private int mNamespace;
        private int mPurposes;
        private boolean mRandomizedEncryptionRequired;
        private java.lang.String[] mSignaturePaddings;
        private java.security.spec.AlgorithmParameterSpec mSpec;
        private boolean mUniqueIdIncluded;
        private boolean mUnlockedDeviceRequired;
        private boolean mUserAuthenticationRequired;
        private int mUserAuthenticationType;
        private boolean mUserAuthenticationValidWhileOnBody;
        private int mUserAuthenticationValidityDurationSeconds;
        private boolean mUserConfirmationRequired;
        private boolean mUserPresenceRequired;

        public Builder(java.lang.String str, int i) {
            this.mNamespace = -1;
            this.mKeySize = -1;
            this.mMgf1Digests = java.util.Collections.emptySet();
            this.mRandomizedEncryptionRequired = true;
            this.mUserAuthenticationValidityDurationSeconds = 0;
            this.mUserAuthenticationType = 2;
            this.mUserPresenceRequired = false;
            this.mAttestationChallenge = null;
            this.mDevicePropertiesAttestationIncluded = false;
            this.mAttestationIds = new int[0];
            this.mUniqueIdIncluded = false;
            this.mInvalidatedByBiometricEnrollment = true;
            this.mIsStrongBoxBacked = false;
            this.mUnlockedDeviceRequired = false;
            this.mCriticalToDeviceEncryption = false;
            this.mMaxUsageCount = -1;
            this.mAttestKeyAlias = null;
            this.mBoundToSecureUserId = 0L;
            if (str == null) {
                throw new java.lang.NullPointerException("keystoreAlias == null");
            }
            if (str.isEmpty()) {
                throw new java.lang.IllegalArgumentException("keystoreAlias must not be empty");
            }
            this.mKeystoreAlias = str;
            this.mPurposes = i;
        }

        public Builder(android.security.keystore.KeyGenParameterSpec keyGenParameterSpec) {
            this(keyGenParameterSpec.getKeystoreAlias(), keyGenParameterSpec.getPurposes());
            this.mNamespace = keyGenParameterSpec.getNamespace();
            this.mKeySize = keyGenParameterSpec.getKeySize();
            this.mSpec = keyGenParameterSpec.getAlgorithmParameterSpec();
            this.mCertificateSubject = keyGenParameterSpec.getCertificateSubject();
            this.mCertificateSerialNumber = keyGenParameterSpec.getCertificateSerialNumber();
            this.mCertificateNotBefore = keyGenParameterSpec.getCertificateNotBefore();
            this.mCertificateNotAfter = keyGenParameterSpec.getCertificateNotAfter();
            this.mKeyValidityStart = keyGenParameterSpec.getKeyValidityStart();
            this.mKeyValidityForOriginationEnd = keyGenParameterSpec.getKeyValidityForOriginationEnd();
            this.mKeyValidityForConsumptionEnd = keyGenParameterSpec.getKeyValidityForConsumptionEnd();
            this.mPurposes = keyGenParameterSpec.getPurposes();
            if (keyGenParameterSpec.isDigestsSpecified()) {
                this.mDigests = keyGenParameterSpec.getDigests();
            }
            if (keyGenParameterSpec.isMgf1DigestsSpecified()) {
                this.mMgf1Digests = keyGenParameterSpec.getMgf1Digests();
            }
            this.mEncryptionPaddings = keyGenParameterSpec.getEncryptionPaddings();
            this.mSignaturePaddings = keyGenParameterSpec.getSignaturePaddings();
            this.mBlockModes = keyGenParameterSpec.getBlockModes();
            this.mRandomizedEncryptionRequired = keyGenParameterSpec.isRandomizedEncryptionRequired();
            this.mUserAuthenticationRequired = keyGenParameterSpec.isUserAuthenticationRequired();
            this.mUserAuthenticationValidityDurationSeconds = keyGenParameterSpec.getUserAuthenticationValidityDurationSeconds();
            this.mUserAuthenticationType = keyGenParameterSpec.getUserAuthenticationType();
            this.mUserPresenceRequired = keyGenParameterSpec.isUserPresenceRequired();
            this.mAttestationChallenge = keyGenParameterSpec.getAttestationChallenge();
            this.mDevicePropertiesAttestationIncluded = keyGenParameterSpec.isDevicePropertiesAttestationIncluded();
            this.mAttestationIds = keyGenParameterSpec.getAttestationIds();
            this.mUniqueIdIncluded = keyGenParameterSpec.isUniqueIdIncluded();
            this.mUserAuthenticationValidWhileOnBody = keyGenParameterSpec.isUserAuthenticationValidWhileOnBody();
            this.mInvalidatedByBiometricEnrollment = keyGenParameterSpec.isInvalidatedByBiometricEnrollment();
            this.mIsStrongBoxBacked = keyGenParameterSpec.isStrongBoxBacked();
            this.mUserConfirmationRequired = keyGenParameterSpec.isUserConfirmationRequired();
            this.mUnlockedDeviceRequired = keyGenParameterSpec.isUnlockedDeviceRequired();
            this.mCriticalToDeviceEncryption = keyGenParameterSpec.isCriticalToDeviceEncryption();
            this.mMaxUsageCount = keyGenParameterSpec.getMaxUsageCount();
            this.mAttestKeyAlias = keyGenParameterSpec.getAttestKeyAlias();
            this.mBoundToSecureUserId = keyGenParameterSpec.getBoundToSpecificSecureUserId();
        }

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public android.security.keystore.KeyGenParameterSpec.Builder setUid(int i) {
            this.mNamespace = android.security.keystore.KeyProperties.legacyUidToNamespace(i);
            return this;
        }

        @android.annotation.SystemApi
        public android.security.keystore.KeyGenParameterSpec.Builder setNamespace(int i) {
            this.mNamespace = i;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setKeySize(int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("keySize < 0");
            }
            this.mKeySize = i;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setAlgorithmParameterSpec(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) {
            if (algorithmParameterSpec == null) {
                throw new java.lang.NullPointerException("spec == null");
            }
            this.mSpec = algorithmParameterSpec;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setCertificateSubject(javax.security.auth.x500.X500Principal x500Principal) {
            if (x500Principal == null) {
                throw new java.lang.NullPointerException("subject == null");
            }
            this.mCertificateSubject = x500Principal;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setCertificateSerialNumber(java.math.BigInteger bigInteger) {
            if (bigInteger == null) {
                throw new java.lang.NullPointerException("serialNumber == null");
            }
            this.mCertificateSerialNumber = bigInteger;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setCertificateNotBefore(java.util.Date date) {
            if (date == null) {
                throw new java.lang.NullPointerException("date == null");
            }
            this.mCertificateNotBefore = android.security.keystore.Utils.cloneIfNotNull(date);
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setCertificateNotAfter(java.util.Date date) {
            if (date == null) {
                throw new java.lang.NullPointerException("date == null");
            }
            this.mCertificateNotAfter = android.security.keystore.Utils.cloneIfNotNull(date);
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setKeyValidityStart(java.util.Date date) {
            this.mKeyValidityStart = android.security.keystore.Utils.cloneIfNotNull(date);
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setKeyValidityEnd(java.util.Date date) {
            setKeyValidityForOriginationEnd(date);
            setKeyValidityForConsumptionEnd(date);
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setKeyValidityForOriginationEnd(java.util.Date date) {
            this.mKeyValidityForOriginationEnd = android.security.keystore.Utils.cloneIfNotNull(date);
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setKeyValidityForConsumptionEnd(java.util.Date date) {
            this.mKeyValidityForConsumptionEnd = android.security.keystore.Utils.cloneIfNotNull(date);
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setDigests(java.lang.String... strArr) {
            this.mDigests = android.security.keystore.ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setMgf1Digests(java.lang.String... strArr) {
            this.mMgf1Digests = java.util.Set.of((java.lang.Object[]) strArr);
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setEncryptionPaddings(java.lang.String... strArr) {
            this.mEncryptionPaddings = android.security.keystore.ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setSignaturePaddings(java.lang.String... strArr) {
            this.mSignaturePaddings = android.security.keystore.ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setBlockModes(java.lang.String... strArr) {
            this.mBlockModes = android.security.keystore.ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setRandomizedEncryptionRequired(boolean z) {
            this.mRandomizedEncryptionRequired = z;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setUserAuthenticationRequired(boolean z) {
            this.mUserAuthenticationRequired = z;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setUserConfirmationRequired(boolean z) {
            this.mUserConfirmationRequired = z;
            return this;
        }

        @java.lang.Deprecated
        public android.security.keystore.KeyGenParameterSpec.Builder setUserAuthenticationValidityDurationSeconds(int i) {
            if (i < -1) {
                throw new java.lang.IllegalArgumentException("seconds must be -1 or larger");
            }
            if (i == -1) {
                return setUserAuthenticationParameters(0, 2);
            }
            return setUserAuthenticationParameters(i, 3);
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setUserAuthenticationParameters(int i, int i2) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("timeout must be 0 or larger");
            }
            this.mUserAuthenticationValidityDurationSeconds = i;
            this.mUserAuthenticationType = i2;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setUserPresenceRequired(boolean z) {
            this.mUserPresenceRequired = z;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setAttestationChallenge(byte[] bArr) {
            this.mAttestationChallenge = bArr;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setDevicePropertiesAttestationIncluded(boolean z) {
            this.mDevicePropertiesAttestationIncluded = z;
            return this;
        }

        @android.annotation.SystemApi
        public android.security.keystore.KeyGenParameterSpec.Builder setAttestationIds(int[] iArr) {
            this.mAttestationIds = iArr;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setUniqueIdIncluded(boolean z) {
            this.mUniqueIdIncluded = z;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setUserAuthenticationValidWhileOnBody(boolean z) {
            this.mUserAuthenticationValidWhileOnBody = z;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setInvalidatedByBiometricEnrollment(boolean z) {
            this.mInvalidatedByBiometricEnrollment = z;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setIsStrongBoxBacked(boolean z) {
            this.mIsStrongBoxBacked = z;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setUnlockedDeviceRequired(boolean z) {
            this.mUnlockedDeviceRequired = z;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setCriticalToDeviceEncryption(boolean z) {
            this.mCriticalToDeviceEncryption = z;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setMaxUsageCount(int i) {
            if (i == -1 || i > 0) {
                this.mMaxUsageCount = i;
                return this;
            }
            throw new java.lang.IllegalArgumentException("maxUsageCount is not valid");
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setAttestKeyAlias(java.lang.String str) {
            this.mAttestKeyAlias = str;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec.Builder setBoundToSpecificSecureUserId(long j) {
            this.mBoundToSecureUserId = j;
            return this;
        }

        public android.security.keystore.KeyGenParameterSpec build() {
            return new android.security.keystore.KeyGenParameterSpec(this.mKeystoreAlias, this.mNamespace, this.mKeySize, this.mSpec, this.mCertificateSubject, this.mCertificateSerialNumber, this.mCertificateNotBefore, this.mCertificateNotAfter, this.mKeyValidityStart, this.mKeyValidityForOriginationEnd, this.mKeyValidityForConsumptionEnd, this.mPurposes, this.mDigests, this.mMgf1Digests, this.mEncryptionPaddings, this.mSignaturePaddings, this.mBlockModes, this.mRandomizedEncryptionRequired, this.mUserAuthenticationRequired, this.mUserAuthenticationValidityDurationSeconds, this.mUserAuthenticationType, this.mUserPresenceRequired, this.mAttestationChallenge, this.mDevicePropertiesAttestationIncluded, this.mAttestationIds, this.mUniqueIdIncluded, this.mUserAuthenticationValidWhileOnBody, this.mInvalidatedByBiometricEnrollment, this.mIsStrongBoxBacked, this.mUserConfirmationRequired, this.mUnlockedDeviceRequired, this.mCriticalToDeviceEncryption, this.mMaxUsageCount, this.mAttestKeyAlias, this.mBoundToSecureUserId);
        }
    }
}
