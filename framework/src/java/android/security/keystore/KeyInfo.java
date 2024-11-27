package android.security.keystore;

/* loaded from: classes3.dex */
public class KeyInfo implements java.security.spec.KeySpec {
    private final java.lang.String[] mBlockModes;
    private final java.lang.String[] mDigests;
    private final java.lang.String[] mEncryptionPaddings;
    private final boolean mInsideSecureHardware;
    private final boolean mInvalidatedByBiometricEnrollment;
    private final int mKeySize;
    private final java.util.Date mKeyValidityForConsumptionEnd;
    private final java.util.Date mKeyValidityForOriginationEnd;
    private final java.util.Date mKeyValidityStart;
    private final java.lang.String mKeystoreAlias;
    private final int mOrigin;
    private final int mPurposes;
    private final int mRemainingUsageCount;
    private final int mSecurityLevel;
    private final java.lang.String[] mSignaturePaddings;
    private final boolean mTrustedUserPresenceRequired;
    private final boolean mUnlockedDeviceRequired;
    private final boolean mUserAuthenticationRequired;
    private final boolean mUserAuthenticationRequirementEnforcedBySecureHardware;
    private final int mUserAuthenticationType;
    private final boolean mUserAuthenticationValidWhileOnBody;
    private final int mUserAuthenticationValidityDurationSeconds;
    private final boolean mUserConfirmationRequired;

    public KeyInfo(java.lang.String str, boolean z, int i, int i2, java.util.Date date, java.util.Date date2, java.util.Date date3, int i3, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, java.lang.String[] strArr4, boolean z2, int i4, int i5, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i6, int i7) {
        this.mKeystoreAlias = str;
        this.mInsideSecureHardware = z;
        this.mOrigin = i;
        this.mKeySize = i2;
        this.mKeyValidityStart = android.security.keystore.Utils.cloneIfNotNull(date);
        this.mKeyValidityForOriginationEnd = android.security.keystore.Utils.cloneIfNotNull(date2);
        this.mKeyValidityForConsumptionEnd = android.security.keystore.Utils.cloneIfNotNull(date3);
        this.mPurposes = i3;
        this.mEncryptionPaddings = android.security.keystore.ArrayUtils.cloneIfNotEmpty(android.security.keystore.ArrayUtils.nullToEmpty(strArr));
        this.mSignaturePaddings = android.security.keystore.ArrayUtils.cloneIfNotEmpty(android.security.keystore.ArrayUtils.nullToEmpty(strArr2));
        this.mDigests = android.security.keystore.ArrayUtils.cloneIfNotEmpty(android.security.keystore.ArrayUtils.nullToEmpty(strArr3));
        this.mBlockModes = android.security.keystore.ArrayUtils.cloneIfNotEmpty(android.security.keystore.ArrayUtils.nullToEmpty(strArr4));
        this.mUserAuthenticationRequired = z2;
        this.mUserAuthenticationValidityDurationSeconds = i4;
        this.mUserAuthenticationType = i5;
        this.mUserAuthenticationRequirementEnforcedBySecureHardware = z3;
        this.mUserAuthenticationValidWhileOnBody = z4;
        this.mUnlockedDeviceRequired = z5;
        this.mTrustedUserPresenceRequired = z6;
        this.mInvalidatedByBiometricEnrollment = z7;
        this.mUserConfirmationRequired = z8;
        this.mSecurityLevel = i6;
        this.mRemainingUsageCount = i7;
    }

    public java.lang.String getKeystoreAlias() {
        return this.mKeystoreAlias;
    }

    @java.lang.Deprecated
    public boolean isInsideSecureHardware() {
        return this.mInsideSecureHardware;
    }

    public int getOrigin() {
        return this.mOrigin;
    }

    public int getKeySize() {
        return this.mKeySize;
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

    public java.lang.String[] getBlockModes() {
        return android.security.keystore.ArrayUtils.cloneIfNotEmpty(this.mBlockModes);
    }

    public java.lang.String[] getEncryptionPaddings() {
        return android.security.keystore.ArrayUtils.cloneIfNotEmpty(this.mEncryptionPaddings);
    }

    public java.lang.String[] getSignaturePaddings() {
        return android.security.keystore.ArrayUtils.cloneIfNotEmpty(this.mSignaturePaddings);
    }

    public java.lang.String[] getDigests() {
        return android.security.keystore.ArrayUtils.cloneIfNotEmpty(this.mDigests);
    }

    public boolean isUserAuthenticationRequired() {
        return this.mUserAuthenticationRequired;
    }

    public boolean isUnlockedDeviceRequired() {
        return this.mUnlockedDeviceRequired;
    }

    public boolean isUserConfirmationRequired() {
        return this.mUserConfirmationRequired;
    }

    public int getUserAuthenticationValidityDurationSeconds() {
        return this.mUserAuthenticationValidityDurationSeconds;
    }

    public int getUserAuthenticationType() {
        return this.mUserAuthenticationType;
    }

    public boolean isUserAuthenticationRequirementEnforcedBySecureHardware() {
        return this.mUserAuthenticationRequirementEnforcedBySecureHardware;
    }

    public boolean isUserAuthenticationValidWhileOnBody() {
        return this.mUserAuthenticationValidWhileOnBody;
    }

    public boolean isInvalidatedByBiometricEnrollment() {
        return this.mInvalidatedByBiometricEnrollment;
    }

    public boolean isTrustedUserPresenceRequired() {
        return this.mTrustedUserPresenceRequired;
    }

    public int getSecurityLevel() {
        return this.mSecurityLevel;
    }

    public int getRemainingUsageCount() {
        return this.mRemainingUsageCount;
    }
}
