package android.security.keymaster;

/* loaded from: classes3.dex */
public final class KeymasterDefs {
    public static final int HW_AUTH_BIOMETRIC = 2;
    public static final int HW_AUTH_PASSWORD = 1;
    public static final int KM_ALGORITHM_3DES = 33;
    public static final int KM_ALGORITHM_AES = 32;
    public static final int KM_ALGORITHM_EC = 3;
    public static final int KM_ALGORITHM_HMAC = 128;
    public static final int KM_ALGORITHM_RSA = 1;
    public static final int KM_BIGNUM = Integer.MIN_VALUE;
    public static final int KM_BLOB_REQUIRES_FILE_SYSTEM = 1;
    public static final int KM_BLOB_STANDALONE = 0;
    public static final int KM_BOOL = 1879048192;
    public static final int KM_BYTES = -1879048192;
    public static final int KM_DATE = 1610612736;
    public static final int KM_DIGEST_MD5 = 1;
    public static final int KM_DIGEST_NONE = 0;
    public static final int KM_DIGEST_SHA1 = 2;
    public static final int KM_DIGEST_SHA_2_224 = 3;
    public static final int KM_DIGEST_SHA_2_256 = 4;
    public static final int KM_DIGEST_SHA_2_384 = 5;
    public static final int KM_DIGEST_SHA_2_512 = 6;
    public static final int KM_ENUM = 268435456;
    public static final int KM_ENUM_REP = 536870912;
    public static final int KM_ERROR_ATTESTATION_APPLICATION_ID_MISSING = -65;
    public static final int KM_ERROR_ATTESTATION_CHALLENGE_MISSING = -63;
    public static final int KM_ERROR_ATTESTATION_IDS_NOT_PROVISIONED = -75;
    public static final int KM_ERROR_ATTESTATION_KEYS_NOT_PROVISIONED = -74;
    public static final int KM_ERROR_BOOT_LEVEL_EXCEEDED = -84;
    public static final int KM_ERROR_CALLER_NONCE_PROHIBITED = -55;
    public static final int KM_ERROR_CANNOT_ATTEST_IDS = -66;
    public static final int KM_ERROR_CONCURRENT_ACCESS_CONFLICT = -47;
    public static final int KM_ERROR_CONCURRENT_PROOF_OF_PRESENCE_REQUESTED = -70;
    public static final int KM_ERROR_DELEGATION_NOT_ALLOWED = -23;
    public static final int KM_ERROR_DEVICE_LOCKED = -72;
    public static final int KM_ERROR_EARLY_BOOT_ENDED = -73;
    public static final int KM_ERROR_HARDWARE_NOT_YET_AVAILABLE = -85;
    public static final int KM_ERROR_HARDWARE_TYPE_UNAVAILABLE = -68;
    public static final int KM_ERROR_IMPORTED_KEY_DECRYPTION_FAILED = -35;
    public static final int KM_ERROR_IMPORTED_KEY_NOT_ENCRYPTED = -34;
    public static final int KM_ERROR_IMPORTED_KEY_NOT_SIGNED = -36;
    public static final int KM_ERROR_IMPORTED_KEY_VERIFICATION_FAILED = -37;
    public static final int KM_ERROR_IMPORT_PARAMETER_MISMATCH = -44;
    public static final int KM_ERROR_INCOMPATIBLE_ALGORITHM = -5;
    public static final int KM_ERROR_INCOMPATIBLE_BLOCK_MODE = -8;
    public static final int KM_ERROR_INCOMPATIBLE_DIGEST = -13;
    public static final int KM_ERROR_INCOMPATIBLE_KEY_FORMAT = -18;
    public static final int KM_ERROR_INCOMPATIBLE_MGF_DIGEST = -78;
    public static final int KM_ERROR_INCOMPATIBLE_PADDING_MODE = -11;
    public static final int KM_ERROR_INCOMPATIBLE_PURPOSE = -3;
    public static final int KM_ERROR_INSUFFICIENT_BUFFER_SPACE = -29;
    public static final int KM_ERROR_INVALID_ARGUMENT = -38;
    public static final int KM_ERROR_INVALID_AUTHORIZATION_TIMEOUT = -16;
    public static final int KM_ERROR_INVALID_EXPIRATION_TIME = -14;
    public static final int KM_ERROR_INVALID_INPUT_LENGTH = -21;
    public static final int KM_ERROR_INVALID_ISSUER_SUBJECT = -83;
    public static final int KM_ERROR_INVALID_KEY_BLOB = -33;
    public static final int KM_ERROR_INVALID_MAC_LENGTH = -57;
    public static final int KM_ERROR_INVALID_NONCE = -52;
    public static final int KM_ERROR_INVALID_OPERATION = -76;
    public static final int KM_ERROR_INVALID_OPERATION_HANDLE = -28;
    public static final int KM_ERROR_INVALID_TAG = -40;
    public static final int KM_ERROR_INVALID_USER_ID = -15;
    public static final int KM_ERROR_KEYMINT_NOT_CONFIGURED = -64;
    public static final int KM_ERROR_KEY_EXPIRED = -25;
    public static final int KM_ERROR_KEY_EXPORT_OPTIONS_INVALID = -22;
    public static final int KM_ERROR_KEY_MAX_OPS_EXCEEDED = -56;
    public static final int KM_ERROR_KEY_NOT_YET_VALID = -24;
    public static final int KM_ERROR_KEY_RATE_LIMIT_EXCEEDED = -54;
    public static final int KM_ERROR_KEY_USER_NOT_AUTHENTICATED = -26;
    public static final int KM_ERROR_MEMORY_ALLOCATION_FAILED = -41;
    public static final int KM_ERROR_MISSING_ISSUER_SUBJECT = -82;
    public static final int KM_ERROR_MISSING_MAC_LENGTH = -53;
    public static final int KM_ERROR_MISSING_MIN_MAC_LENGTH = -58;
    public static final int KM_ERROR_MISSING_NONCE = -51;
    public static final int KM_ERROR_MISSING_NOT_AFTER = -81;
    public static final int KM_ERROR_MISSING_NOT_BEFORE = -80;
    public static final int KM_ERROR_NO_USER_CONFIRMATION = -71;
    public static final int KM_ERROR_OK = 0;
    public static final int KM_ERROR_OPERATION_CANCELLED = -46;
    public static final int KM_ERROR_OUTPUT_PARAMETER_NULL = -27;
    public static final int KM_ERROR_PROOF_OF_PRESENCE_REQUIRED = -69;
    public static final int KM_ERROR_ROLLBACK_RESISTANCE_UNAVAILABLE = -67;
    public static final int KM_ERROR_ROOT_OF_TRUST_ALREADY_SET = -1;
    public static final int KM_ERROR_SECURE_HW_ACCESS_DENIED = -45;
    public static final int KM_ERROR_SECURE_HW_BUSY = -48;
    public static final int KM_ERROR_SECURE_HW_COMMUNICATION_FAILED = -49;
    public static final int KM_ERROR_STORAGE_KEY_UNSUPPORTED = -77;
    public static final int KM_ERROR_TOO_MANY_OPERATIONS = -31;
    public static final int KM_ERROR_UNEXPECTED_NULL_POINTER = -32;
    public static final int KM_ERROR_UNIMPLEMENTED = -100;
    public static final int KM_ERROR_UNKNOWN_ERROR = -1000;
    public static final int KM_ERROR_UNSUPPORTED_ALGORITHM = -4;
    public static final int KM_ERROR_UNSUPPORTED_BLOCK_MODE = -7;
    public static final int KM_ERROR_UNSUPPORTED_DIGEST = -12;
    public static final int KM_ERROR_UNSUPPORTED_EC_CURVE = -61;
    public static final int KM_ERROR_UNSUPPORTED_EC_FIELD = -50;
    public static final int KM_ERROR_UNSUPPORTED_KDF = -60;
    public static final int KM_ERROR_UNSUPPORTED_KEY_ENCRYPTION_ALGORITHM = -19;
    public static final int KM_ERROR_UNSUPPORTED_KEY_FORMAT = -17;
    public static final int KM_ERROR_UNSUPPORTED_KEY_SIZE = -6;
    public static final int KM_ERROR_UNSUPPORTED_KEY_VERIFICATION_ALGORITHM = -20;
    public static final int KM_ERROR_UNSUPPORTED_MAC_LENGTH = -9;
    public static final int KM_ERROR_UNSUPPORTED_MGF_DIGEST = -79;
    public static final int KM_ERROR_UNSUPPORTED_MIN_MAC_LENGTH = -59;
    public static final int KM_ERROR_UNSUPPORTED_PADDING_MODE = -10;
    public static final int KM_ERROR_UNSUPPORTED_PURPOSE = -2;
    public static final int KM_ERROR_UNSUPPORTED_TAG = -39;
    public static final int KM_ERROR_VERIFICATION_FAILED = -30;
    public static final int KM_ERROR_VERSION_MISMATCH = -101;
    public static final int KM_INVALID = 0;
    public static final int KM_KEY_FORMAT_PKCS8 = 1;
    public static final int KM_KEY_FORMAT_RAW = 3;
    public static final int KM_KEY_FORMAT_X509 = 0;
    public static final int KM_MODE_CBC = 2;
    public static final int KM_MODE_CTR = 3;
    public static final int KM_MODE_ECB = 1;
    public static final int KM_MODE_GCM = 32;
    public static final int KM_ORIGIN_DERIVED = 1;
    public static final int KM_ORIGIN_GENERATED = 0;
    public static final int KM_ORIGIN_IMPORTED = 2;
    public static final int KM_ORIGIN_SECURELY_IMPORTED = 4;
    public static final int KM_ORIGIN_UNKNOWN = 3;
    public static final int KM_PAD_NONE = 1;
    public static final int KM_PAD_PKCS7 = 64;
    public static final int KM_PAD_RSA_OAEP = 2;
    public static final int KM_PAD_RSA_PKCS1_1_5_ENCRYPT = 4;
    public static final int KM_PAD_RSA_PKCS1_1_5_SIGN = 5;
    public static final int KM_PAD_RSA_PSS = 3;
    public static final int KM_PURPOSE_AGREE_KEY = 6;
    public static final int KM_PURPOSE_ATTEST_KEY = 7;
    public static final int KM_PURPOSE_DECRYPT = 1;
    public static final int KM_PURPOSE_ENCRYPT = 0;
    public static final int KM_PURPOSE_SIGN = 2;
    public static final int KM_PURPOSE_VERIFY = 3;
    public static final int KM_PURPOSE_WRAP = 5;
    public static final int KM_SECURITY_LEVEL_SOFTWARE = 0;
    public static final int KM_SECURITY_LEVEL_STRONGBOX = 2;
    public static final int KM_SECURITY_LEVEL_TRUSTED_ENVIRONMENT = 1;
    public static final int KM_TAG_ACTIVE_DATETIME = 1610613136;
    public static final int KM_TAG_ALGORITHM = 268435458;
    public static final int KM_TAG_ALLOW_WHILE_ON_BODY = 1879048698;
    public static final int KM_TAG_APPLICATION_ID = -1879047591;
    public static final int KM_TAG_ATTESTATION_CHALLENGE = -1879047484;
    public static final int KM_TAG_ATTESTATION_ID_BRAND = -1879047482;
    public static final int KM_TAG_ATTESTATION_ID_DEVICE = -1879047481;
    public static final int KM_TAG_ATTESTATION_ID_IMEI = -1879047478;
    public static final int KM_TAG_ATTESTATION_ID_MANUFACTURER = -1879047476;
    public static final int KM_TAG_ATTESTATION_ID_MEID = -1879047477;
    public static final int KM_TAG_ATTESTATION_ID_MODEL = -1879047475;
    public static final int KM_TAG_ATTESTATION_ID_PRODUCT = -1879047480;
    public static final int KM_TAG_ATTESTATION_ID_SECOND_IMEI = -1879047469;
    public static final int KM_TAG_ATTESTATION_ID_SERIAL = -1879047479;
    public static final int KM_TAG_AUTH_TIMEOUT = 805306873;
    public static final int KM_TAG_BLOCK_MODE = 536870916;
    public static final int KM_TAG_BOOT_PATCHLEVEL = 805307087;
    public static final int KM_TAG_CALLER_NONCE = 1879048199;
    public static final int KM_TAG_CERTIFICATE_NOT_AFTER = 1610613745;
    public static final int KM_TAG_CERTIFICATE_NOT_BEFORE = 1610613744;
    public static final int KM_TAG_CERTIFICATE_SERIAL = -2147482642;
    public static final int KM_TAG_CERTIFICATE_SUBJECT = -1879047185;
    public static final int KM_TAG_CONFIRMATION_TOKEN = -1879047187;
    public static final int KM_TAG_CREATION_DATETIME = 1610613437;
    public static final int KM_TAG_DEVICE_UNIQUE_ATTESTATION = 1879048912;
    public static final int KM_TAG_DIGEST = 536870917;
    public static final int KM_TAG_EC_CURVE = 268435466;
    public static final int KM_TAG_INCLUDE_UNIQUE_ID = 1879048394;
    public static final int KM_TAG_INVALID = 0;
    public static final int KM_TAG_KEY_SIZE = 805306371;
    public static final int KM_TAG_MAC_LENGTH = 805307371;
    public static final int KM_TAG_MAX_USES_PER_BOOT = 805306772;
    public static final int KM_TAG_MIN_MAC_LENGTH = 805306376;
    public static final int KM_TAG_MIN_SECONDS_BETWEEN_OPS = 805306771;
    public static final int KM_TAG_NONCE = -1879047191;
    public static final int KM_TAG_NO_AUTH_REQUIRED = 1879048695;
    public static final int KM_TAG_ORIGIN = 268436158;
    public static final int KM_TAG_ORIGINATION_EXPIRE_DATETIME = 1610613137;
    public static final int KM_TAG_PADDING = 536870918;
    public static final int KM_TAG_PURPOSE = 536870913;
    public static final int KM_TAG_RESET_SINCE_ID_ROTATION = 1879049196;
    public static final int KM_TAG_ROLLBACK_RESISTANT = 1879048495;
    public static final int KM_TAG_ROOT_OF_TRUST = -1879047488;
    public static final int KM_TAG_RSA_OAEP_MGF_DIGEST = 536871115;
    public static final int KM_TAG_RSA_PUBLIC_EXPONENT = 1342177480;
    public static final int KM_TAG_TRUSTED_CONFIRMATION_REQUIRED = 1879048700;
    public static final int KM_TAG_TRUSTED_USER_PRESENCE_REQUIRED = 1879048699;
    public static final int KM_TAG_UNIQUE_ID = -1879047485;
    public static final int KM_TAG_UNLOCKED_DEVICE_REQUIRED = 1879048701;
    public static final int KM_TAG_USAGE_COUNT_LIMIT = 805306773;
    public static final int KM_TAG_USAGE_EXPIRE_DATETIME = 1610613138;
    public static final int KM_TAG_USER_AUTH_TYPE = 268435960;
    public static final int KM_TAG_USER_ID = 805306869;
    public static final int KM_TAG_USER_SECURE_ID = -1610612234;
    public static final int KM_TAG_VENDOR_PATCHLEVEL = 805307086;
    public static final int KM_UINT = 805306368;
    public static final int KM_UINT_REP = 1073741824;
    public static final int KM_ULONG = 1342177280;
    public static final int KM_ULONG_REP = -1610612736;
    public static final java.util.Map<java.lang.Integer, java.lang.String> sErrorCodeToString = new java.util.HashMap();

    private KeymasterDefs() {
    }

    static {
        sErrorCodeToString.put(0, "OK");
        sErrorCodeToString.put(-2, "Unsupported purpose");
        sErrorCodeToString.put(-3, "Incompatible purpose");
        sErrorCodeToString.put(-4, "Unsupported algorithm");
        sErrorCodeToString.put(-5, "Incompatible algorithm");
        sErrorCodeToString.put(-6, "Unsupported key size");
        sErrorCodeToString.put(-7, "Unsupported block mode");
        sErrorCodeToString.put(-8, "Incompatible block mode");
        sErrorCodeToString.put(-9, "Unsupported MAC or authentication tag length");
        sErrorCodeToString.put(-10, "Unsupported padding mode");
        sErrorCodeToString.put(-11, "Incompatible padding mode");
        sErrorCodeToString.put(-12, "Unsupported digest");
        sErrorCodeToString.put(-13, "Incompatible digest");
        sErrorCodeToString.put(-14, "Invalid expiration time");
        sErrorCodeToString.put(-15, "Invalid user ID");
        sErrorCodeToString.put(-16, "Invalid user authorization timeout");
        sErrorCodeToString.put(-17, "Unsupported key format");
        sErrorCodeToString.put(-18, "Incompatible key format");
        sErrorCodeToString.put(-21, "Invalid input length");
        sErrorCodeToString.put(-24, "Key not yet valid");
        sErrorCodeToString.put(-25, "Key expired");
        sErrorCodeToString.put(-26, "Key user not authenticated");
        sErrorCodeToString.put(-28, "Invalid operation handle");
        sErrorCodeToString.put(-30, "Signature/MAC verification failed");
        sErrorCodeToString.put(-31, "Too many operations");
        sErrorCodeToString.put(-33, "Invalid key blob");
        sErrorCodeToString.put(-38, "Invalid argument");
        sErrorCodeToString.put(-39, "Unsupported tag");
        sErrorCodeToString.put(-40, "Invalid tag");
        sErrorCodeToString.put(-41, "Memory allocation failed");
        sErrorCodeToString.put(-50, "Unsupported EC field");
        sErrorCodeToString.put(-51, "Required IV missing");
        sErrorCodeToString.put(-52, "Invalid IV");
        sErrorCodeToString.put(-55, "Caller-provided IV not permitted");
        sErrorCodeToString.put(-57, "Invalid MAC or authentication tag length");
        sErrorCodeToString.put(-66, "Unable to attest device ids");
        sErrorCodeToString.put(-68, "Requested security level (likely Strongbox) is not available.");
        sErrorCodeToString.put(-72, "Device locked");
        sErrorCodeToString.put(-100, "Not implemented");
        sErrorCodeToString.put(-1000, "Unknown error");
    }

    public static int getTagType(int i) {
        return i & android.content.pm.PackageManager.FLAGS_PERMISSION_RESERVED_PERMISSION_CONTROLLER;
    }

    public static java.lang.String getErrorMessage(int i) {
        java.lang.String str = sErrorCodeToString.get(java.lang.Integer.valueOf(i));
        if (str != null) {
            return str;
        }
        return java.lang.String.valueOf(i);
    }
}
