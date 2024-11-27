package android.security;

/* loaded from: classes3.dex */
public class KeyStoreException extends java.lang.Exception {
    public static final int ERROR_ATTESTATION_CHALLENGE_TOO_LARGE = 9;
    public static final int ERROR_ATTESTATION_KEYS_UNAVAILABLE = 16;
    public static final int ERROR_DEVICE_REQUIRES_UPGRADE_FOR_ATTESTATION = 17;
    public static final int ERROR_ID_ATTESTATION_FAILURE = 8;
    public static final int ERROR_INCORRECT_USAGE = 13;
    public static final int ERROR_INTERNAL_SYSTEM_ERROR = 4;
    public static final int ERROR_KEYMINT_FAILURE = 10;
    public static final int ERROR_KEYSTORE_FAILURE = 11;
    public static final int ERROR_KEYSTORE_UNINITIALIZED = 3;
    public static final int ERROR_KEY_CORRUPTED = 7;
    public static final int ERROR_KEY_DOES_NOT_EXIST = 6;
    public static final int ERROR_KEY_NOT_TEMPORALLY_VALID = 14;
    public static final int ERROR_KEY_OPERATION_EXPIRED = 15;
    public static final int ERROR_OTHER = 1;
    public static final int ERROR_PERMISSION_DENIED = 5;
    public static final int ERROR_UNIMPLEMENTED = 12;
    public static final int ERROR_USER_AUTHENTICATION_REQUIRED = 2;
    private static final int IS_SYSTEM_ERROR = 2;
    private static final int IS_TRANSIENT_ERROR = 4;
    private static final int REQUIRES_USER_AUTHENTICATION = 8;
    public static final int RETRY_AFTER_NEXT_REBOOT = 4;
    public static final int RETRY_NEVER = 1;
    public static final int RETRY_WHEN_CONNECTIVITY_AVAILABLE = 3;
    public static final int RETRY_WITH_EXPONENTIAL_BACKOFF = 2;
    public static final int RKP_FETCHING_PENDING_CONNECTIVITY = 3;
    public static final int RKP_FETCHING_PENDING_SOFTWARE_REBOOT = 4;
    public static final int RKP_SERVER_REFUSED_ISSUANCE = 2;
    public static final int RKP_SUCCESS = 0;
    public static final int RKP_TEMPORARILY_UNAVAILABLE = 1;
    private static final java.lang.String TAG = "KeyStoreException";
    private final int mErrorCode;
    private final int mRkpStatus;
    private static final android.security.KeyStoreException.PublicErrorInformation GENERAL_KEYMINT_ERROR = new android.security.KeyStoreException.PublicErrorInformation(0, 10);
    private static final android.security.KeyStoreException.PublicErrorInformation GENERAL_KEYSTORE_ERROR = new android.security.KeyStoreException.PublicErrorInformation(0, 11);
    private static final android.security.KeyStoreException.PublicErrorInformation KEYMINT_UNIMPLEMENTED_ERROR = new android.security.KeyStoreException.PublicErrorInformation(2, 12);
    private static final android.security.KeyStoreException.PublicErrorInformation KEYMINT_RETRYABLE_ERROR = new android.security.KeyStoreException.PublicErrorInformation(6, 10);
    private static final android.security.KeyStoreException.PublicErrorInformation KEYMINT_INCORRECT_USAGE_ERROR = new android.security.KeyStoreException.PublicErrorInformation(0, 13);
    private static final android.security.KeyStoreException.PublicErrorInformation KEYMINT_TEMPORAL_VALIDITY_ERROR = new android.security.KeyStoreException.PublicErrorInformation(0, 14);
    private static final java.util.Map<java.lang.Integer, android.security.KeyStoreException.PublicErrorInformation> sErrorCodeToFailureInfo = new java.util.HashMap();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PublicErrorCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RetryPolicy {
    }

    private static int initializeRkpStatusForRegularErrors(int i) {
        if (i == 22) {
            android.util.Log.e(TAG, "RKP error code without RKP status");
            return 2;
        }
        return 0;
    }

    public KeyStoreException(int i, java.lang.String str) {
        super(str);
        this.mErrorCode = i;
        this.mRkpStatus = initializeRkpStatusForRegularErrors(i);
    }

    public KeyStoreException(int i, java.lang.String str, java.lang.String str2) {
        super(str + " (internal Keystore code: " + i + " message: " + str2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        this.mErrorCode = i;
        this.mRkpStatus = initializeRkpStatusForRegularErrors(i);
    }

    public KeyStoreException(int i, java.lang.String str, int i2) {
        super(str);
        this.mErrorCode = i;
        this.mRkpStatus = i2;
        if (this.mErrorCode != 22) {
            android.util.Log.e(TAG, "Providing RKP status for error code " + i + " has no effect.");
        }
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public int getNumericErrorCode() {
        return getErrorInformation(this.mErrorCode).errorCode;
    }

    public boolean isTransientFailure() {
        android.security.KeyStoreException.PublicErrorInformation errorInformation = getErrorInformation(this.mErrorCode);
        if (this.mRkpStatus == 0 || this.mErrorCode != 22) {
            return (errorInformation.indicators & 4) != 0;
        }
        switch (this.mRkpStatus) {
            case 1:
            case 3:
            case 4:
                return true;
            case 2:
            default:
                return false;
        }
    }

    public boolean requiresUserAuthentication() {
        return (getErrorInformation(this.mErrorCode).indicators & 8) != 0;
    }

    public boolean isSystemError() {
        return (getErrorInformation(this.mErrorCode).indicators & 2) != 0;
    }

    public int getRetryPolicy() {
        android.security.KeyStoreException.PublicErrorInformation errorInformation = getErrorInformation(this.mErrorCode);
        if (this.mRkpStatus == 0) {
            switch (this.mErrorCode) {
                case 23:
                    return 4;
                case 24:
                    return 3;
                default:
                    return (errorInformation.indicators & 4) != 0 ? 2 : 1;
            }
        }
        switch (this.mRkpStatus) {
            case 1:
                return 2;
            case 2:
                return 1;
            case 3:
                return 3;
            case 4:
                return 4;
            default:
                return (errorInformation.indicators & 4) != 0 ? 2 : 1;
        }
    }

    @Override // java.lang.Throwable
    public java.lang.String toString() {
        return super.toString() + java.lang.String.format(" (public error code: %d internal Keystore code: %d)", java.lang.Integer.valueOf(getNumericErrorCode()), java.lang.Integer.valueOf(this.mErrorCode));
    }

    private static android.security.KeyStoreException.PublicErrorInformation getErrorInformation(int i) {
        android.security.KeyStoreException.PublicErrorInformation publicErrorInformation = sErrorCodeToFailureInfo.get(java.lang.Integer.valueOf(i));
        if (publicErrorInformation != null) {
            return publicErrorInformation;
        }
        if (i > 0) {
            return GENERAL_KEYSTORE_ERROR;
        }
        return GENERAL_KEYMINT_ERROR;
    }

    private static final class PublicErrorInformation {
        public final int errorCode;
        public final int indicators;

        PublicErrorInformation(int i, int i2) {
            this.indicators = i;
            this.errorCode = i2;
        }
    }

    static {
        sErrorCodeToFailureInfo.put(0, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-1, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-2, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-3, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-4, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-5, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-6, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-7, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-8, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-9, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-10, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-11, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-12, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-13, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-14, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-15, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-16, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-17, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-18, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-19, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-20, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-21, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-22, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-23, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-24, KEYMINT_TEMPORAL_VALIDITY_ERROR);
        sErrorCodeToFailureInfo.put(-25, KEYMINT_TEMPORAL_VALIDITY_ERROR);
        sErrorCodeToFailureInfo.put(-26, new android.security.KeyStoreException.PublicErrorInformation(8, 2));
        sErrorCodeToFailureInfo.put(-27, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-28, new android.security.KeyStoreException.PublicErrorInformation(6, 15));
        sErrorCodeToFailureInfo.put(-29, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-30, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-31, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-32, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-33, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-34, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-35, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-36, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-37, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-38, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-39, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-40, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-41, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-44, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-45, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-46, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-47, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-48, KEYMINT_RETRYABLE_ERROR);
        sErrorCodeToFailureInfo.put(-49, KEYMINT_RETRYABLE_ERROR);
        sErrorCodeToFailureInfo.put(-50, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-51, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-52, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-53, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-54, KEYMINT_RETRYABLE_ERROR);
        sErrorCodeToFailureInfo.put(-55, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-56, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-57, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-58, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-59, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-60, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-61, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-63, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-64, new android.security.KeyStoreException.PublicErrorInformation(2, 10));
        sErrorCodeToFailureInfo.put(-65, KEYMINT_RETRYABLE_ERROR);
        sErrorCodeToFailureInfo.put(-66, new android.security.KeyStoreException.PublicErrorInformation(2, 8));
        sErrorCodeToFailureInfo.put(-67, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-68, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-69, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-70, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-71, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-72, new android.security.KeyStoreException.PublicErrorInformation(10, 2));
        sErrorCodeToFailureInfo.put(-73, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-74, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-75, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-76, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-77, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-78, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-79, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-80, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-81, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-82, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-83, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-84, KEYMINT_INCORRECT_USAGE_ERROR);
        sErrorCodeToFailureInfo.put(-85, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(-100, KEYMINT_UNIMPLEMENTED_ERROR);
        sErrorCodeToFailureInfo.put(-1000, new android.security.KeyStoreException.PublicErrorInformation(2, 10));
        sErrorCodeToFailureInfo.put(-101, GENERAL_KEYMINT_ERROR);
        sErrorCodeToFailureInfo.put(2, new android.security.KeyStoreException.PublicErrorInformation(8, 2));
        sErrorCodeToFailureInfo.put(3, new android.security.KeyStoreException.PublicErrorInformation(2, 3));
        sErrorCodeToFailureInfo.put(4, new android.security.KeyStoreException.PublicErrorInformation(2, 4));
        sErrorCodeToFailureInfo.put(6, new android.security.KeyStoreException.PublicErrorInformation(0, 5));
        sErrorCodeToFailureInfo.put(7, new android.security.KeyStoreException.PublicErrorInformation(0, 6));
        sErrorCodeToFailureInfo.put(8, new android.security.KeyStoreException.PublicErrorInformation(0, 7));
        sErrorCodeToFailureInfo.put(17, new android.security.KeyStoreException.PublicErrorInformation(0, 6));
        sErrorCodeToFailureInfo.put(22, new android.security.KeyStoreException.PublicErrorInformation(2, 16));
        sErrorCodeToFailureInfo.put(23, new android.security.KeyStoreException.PublicErrorInformation(6, 17));
        sErrorCodeToFailureInfo.put(24, new android.security.KeyStoreException.PublicErrorInformation(6, 16));
        sErrorCodeToFailureInfo.put(25, new android.security.KeyStoreException.PublicErrorInformation(6, 16));
        sErrorCodeToFailureInfo.put(26, new android.security.KeyStoreException.PublicErrorInformation(2, 16));
    }

    public static boolean hasFailureInfoForError(int i) {
        return sErrorCodeToFailureInfo.containsKey(java.lang.Integer.valueOf(i));
    }
}
