package android.security.keystore;

/* loaded from: classes3.dex */
public final class KeyProtection implements java.security.KeyStore.ProtectionParameter, android.security.keystore.UserAuthArgs {
    private final java.lang.String[] mBlockModes;
    private final long mBoundToSecureUserId;
    private final boolean mCriticalToDeviceEncryption;
    private final java.lang.String[] mDigests;
    private final java.lang.String[] mEncryptionPaddings;
    private final boolean mInvalidatedByBiometricEnrollment;
    private final boolean mIsStrongBoxBacked;
    private final java.util.Date mKeyValidityForConsumptionEnd;
    private final java.util.Date mKeyValidityForOriginationEnd;
    private final java.util.Date mKeyValidityStart;
    private final int mMaxUsageCount;
    private final java.util.Set<java.lang.String> mMgf1Digests;
    private final int mPurposes;
    private final boolean mRandomizedEncryptionRequired;
    private final boolean mRollbackResistant;
    private final java.lang.String[] mSignaturePaddings;
    private final boolean mUnlockedDeviceRequired;
    private final boolean mUserAuthenticationRequired;
    private final int mUserAuthenticationType;
    private final boolean mUserAuthenticationValidWhileOnBody;
    private final int mUserAuthenticationValidityDurationSeconds;
    private final boolean mUserConfirmationRequired;
    private final boolean mUserPresenceRequred;

    private KeyProtection(java.util.Date date, java.util.Date date2, java.util.Date date3, int i, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, java.util.Set<java.lang.String> set, java.lang.String[] strArr4, boolean z, boolean z2, int i2, int i3, boolean z3, boolean z4, boolean z5, long j, boolean z6, boolean z7, boolean z8, boolean z9, int i4, boolean z10) {
        this.mKeyValidityStart = android.security.keystore.Utils.cloneIfNotNull(date);
        this.mKeyValidityForOriginationEnd = android.security.keystore.Utils.cloneIfNotNull(date2);
        this.mKeyValidityForConsumptionEnd = android.security.keystore.Utils.cloneIfNotNull(date3);
        this.mPurposes = i;
        this.mEncryptionPaddings = android.security.keystore.ArrayUtils.cloneIfNotEmpty(android.security.keystore.ArrayUtils.nullToEmpty(strArr));
        this.mSignaturePaddings = android.security.keystore.ArrayUtils.cloneIfNotEmpty(android.security.keystore.ArrayUtils.nullToEmpty(strArr2));
        this.mDigests = android.security.keystore.ArrayUtils.cloneIfNotEmpty(strArr3);
        this.mMgf1Digests = set;
        this.mBlockModes = android.security.keystore.ArrayUtils.cloneIfNotEmpty(android.security.keystore.ArrayUtils.nullToEmpty(strArr4));
        this.mRandomizedEncryptionRequired = z;
        this.mUserAuthenticationRequired = z2;
        this.mUserAuthenticationType = i2;
        this.mUserAuthenticationValidityDurationSeconds = i3;
        this.mUserPresenceRequred = z3;
        this.mUserAuthenticationValidWhileOnBody = z4;
        this.mInvalidatedByBiometricEnrollment = z5;
        this.mBoundToSecureUserId = j;
        this.mCriticalToDeviceEncryption = z6;
        this.mUserConfirmationRequired = z7;
        this.mUnlockedDeviceRequired = z8;
        this.mIsStrongBoxBacked = z9;
        this.mMaxUsageCount = i4;
        this.mRollbackResistant = z10;
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

    public java.lang.String[] getEncryptionPaddings() {
        return android.security.keystore.ArrayUtils.cloneIfNotEmpty(this.mEncryptionPaddings);
    }

    public java.lang.String[] getSignaturePaddings() {
        return android.security.keystore.ArrayUtils.cloneIfNotEmpty(this.mSignaturePaddings);
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
    public int getUserAuthenticationType() {
        return this.mUserAuthenticationType;
    }

    @Override // android.security.keystore.UserAuthArgs
    public int getUserAuthenticationValidityDurationSeconds() {
        return this.mUserAuthenticationValidityDurationSeconds;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUserPresenceRequired() {
        return this.mUserPresenceRequred;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUserAuthenticationValidWhileOnBody() {
        return this.mUserAuthenticationValidWhileOnBody;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isInvalidatedByBiometricEnrollment() {
        return this.mInvalidatedByBiometricEnrollment;
    }

    @Override // android.security.keystore.UserAuthArgs
    public long getBoundToSpecificSecureUserId() {
        return this.mBoundToSecureUserId;
    }

    public boolean isCriticalToDeviceEncryption() {
        return this.mCriticalToDeviceEncryption;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUnlockedDeviceRequired() {
        return this.mUnlockedDeviceRequired;
    }

    public boolean isStrongBoxBacked() {
        return this.mIsStrongBoxBacked;
    }

    public int getMaxUsageCount() {
        return this.mMaxUsageCount;
    }

    public boolean isRollbackResistant() {
        return this.mRollbackResistant;
    }

    public static final class Builder {
        private java.lang.String[] mBlockModes;
        private java.lang.String[] mDigests;
        private java.lang.String[] mEncryptionPaddings;
        private java.util.Date mKeyValidityForConsumptionEnd;
        private java.util.Date mKeyValidityForOriginationEnd;
        private java.util.Date mKeyValidityStart;
        private int mPurposes;
        private java.lang.String[] mSignaturePaddings;
        private boolean mUserAuthenticationRequired;
        private boolean mUserAuthenticationValidWhileOnBody;
        private boolean mUserConfirmationRequired;
        private java.util.Set<java.lang.String> mMgf1Digests = java.util.Collections.emptySet();
        private boolean mRandomizedEncryptionRequired = true;
        private int mUserAuthenticationValidityDurationSeconds = 0;
        private int mUserAuthenticationType = 2;
        private boolean mUserPresenceRequired = false;
        private boolean mInvalidatedByBiometricEnrollment = true;
        private boolean mUnlockedDeviceRequired = false;
        private long mBoundToSecureUserId = 0;
        private boolean mCriticalToDeviceEncryption = false;
        private boolean mIsStrongBoxBacked = false;
        private int mMaxUsageCount = -1;
        private java.lang.String mAttestKeyAlias = null;
        private boolean mRollbackResistant = false;

        public Builder(int i) {
            this.mPurposes = i;
        }

        public android.security.keystore.KeyProtection.Builder setKeyValidityStart(java.util.Date date) {
            this.mKeyValidityStart = android.security.keystore.Utils.cloneIfNotNull(date);
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setKeyValidityEnd(java.util.Date date) {
            setKeyValidityForOriginationEnd(date);
            setKeyValidityForConsumptionEnd(date);
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setKeyValidityForOriginationEnd(java.util.Date date) {
            this.mKeyValidityForOriginationEnd = android.security.keystore.Utils.cloneIfNotNull(date);
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setKeyValidityForConsumptionEnd(java.util.Date date) {
            this.mKeyValidityForConsumptionEnd = android.security.keystore.Utils.cloneIfNotNull(date);
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setEncryptionPaddings(java.lang.String... strArr) {
            this.mEncryptionPaddings = android.security.keystore.ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setSignaturePaddings(java.lang.String... strArr) {
            this.mSignaturePaddings = android.security.keystore.ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setDigests(java.lang.String... strArr) {
            this.mDigests = android.security.keystore.ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setMgf1Digests(java.lang.String... strArr) {
            this.mMgf1Digests = java.util.Set.of((java.lang.Object[]) strArr);
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setBlockModes(java.lang.String... strArr) {
            this.mBlockModes = android.security.keystore.ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setRandomizedEncryptionRequired(boolean z) {
            this.mRandomizedEncryptionRequired = z;
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setUserAuthenticationRequired(boolean z) {
            this.mUserAuthenticationRequired = z;
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setUserConfirmationRequired(boolean z) {
            this.mUserConfirmationRequired = z;
            return this;
        }

        @java.lang.Deprecated
        public android.security.keystore.KeyProtection.Builder setUserAuthenticationValidityDurationSeconds(int i) {
            if (i < -1) {
                throw new java.lang.IllegalArgumentException("seconds must be -1 or larger");
            }
            if (i == -1) {
                return setUserAuthenticationParameters(0, 2);
            }
            return setUserAuthenticationParameters(i, 3);
        }

        public android.security.keystore.KeyProtection.Builder setUserAuthenticationParameters(int i, int i2) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("timeout must be 0 or larger");
            }
            this.mUserAuthenticationValidityDurationSeconds = i;
            this.mUserAuthenticationType = i2;
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setUserPresenceRequired(boolean z) {
            this.mUserPresenceRequired = z;
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setUserAuthenticationValidWhileOnBody(boolean z) {
            this.mUserAuthenticationValidWhileOnBody = z;
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setInvalidatedByBiometricEnrollment(boolean z) {
            this.mInvalidatedByBiometricEnrollment = z;
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setBoundToSpecificSecureUserId(long j) {
            this.mBoundToSecureUserId = j;
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setCriticalToDeviceEncryption(boolean z) {
            this.mCriticalToDeviceEncryption = z;
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setUnlockedDeviceRequired(boolean z) {
            this.mUnlockedDeviceRequired = z;
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setIsStrongBoxBacked(boolean z) {
            this.mIsStrongBoxBacked = z;
            return this;
        }

        public android.security.keystore.KeyProtection.Builder setMaxUsageCount(int i) {
            if (i == -1 || i > 0) {
                this.mMaxUsageCount = i;
                return this;
            }
            throw new java.lang.IllegalArgumentException("maxUsageCount is not valid");
        }

        public android.security.keystore.KeyProtection.Builder setRollbackResistant(boolean z) {
            this.mRollbackResistant = z;
            return this;
        }

        public android.security.keystore.KeyProtection build() {
            return new android.security.keystore.KeyProtection(this.mKeyValidityStart, this.mKeyValidityForOriginationEnd, this.mKeyValidityForConsumptionEnd, this.mPurposes, this.mEncryptionPaddings, this.mSignaturePaddings, this.mDigests, this.mMgf1Digests, this.mBlockModes, this.mRandomizedEncryptionRequired, this.mUserAuthenticationRequired, this.mUserAuthenticationType, this.mUserAuthenticationValidityDurationSeconds, this.mUserPresenceRequired, this.mUserAuthenticationValidWhileOnBody, this.mInvalidatedByBiometricEnrollment, this.mBoundToSecureUserId, this.mCriticalToDeviceEncryption, this.mUserConfirmationRequired, this.mUnlockedDeviceRequired, this.mIsStrongBoxBacked, this.mMaxUsageCount, this.mRollbackResistant);
        }
    }
}
