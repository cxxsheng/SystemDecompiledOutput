package android.security.keystore;

/* loaded from: classes3.dex */
public abstract class KeyProperties {
    public static final int AUTH_BIOMETRIC_STRONG = 2;
    public static final int AUTH_DEVICE_CREDENTIAL = 1;
    public static final java.lang.String BLOCK_MODE_CBC = "CBC";
    public static final java.lang.String BLOCK_MODE_CTR = "CTR";
    public static final java.lang.String BLOCK_MODE_ECB = "ECB";
    public static final java.lang.String BLOCK_MODE_GCM = "GCM";
    public static final java.lang.String DIGEST_MD5 = "MD5";
    public static final java.lang.String DIGEST_NONE = "NONE";
    public static final java.lang.String DIGEST_SHA1 = "SHA-1";
    public static final java.lang.String DIGEST_SHA224 = "SHA-224";
    public static final java.lang.String DIGEST_SHA256 = "SHA-256";
    public static final java.lang.String DIGEST_SHA384 = "SHA-384";
    public static final java.lang.String DIGEST_SHA512 = "SHA-512";
    public static final java.lang.String ENCRYPTION_PADDING_NONE = "NoPadding";
    public static final java.lang.String ENCRYPTION_PADDING_PKCS7 = "PKCS7Padding";
    public static final java.lang.String ENCRYPTION_PADDING_RSA_OAEP = "OAEPPadding";
    public static final java.lang.String ENCRYPTION_PADDING_RSA_PKCS1 = "PKCS1Padding";

    @java.lang.Deprecated
    public static final java.lang.String KEY_ALGORITHM_3DES = "DESede";
    public static final java.lang.String KEY_ALGORITHM_AES = "AES";
    public static final java.lang.String KEY_ALGORITHM_EC = "EC";
    public static final java.lang.String KEY_ALGORITHM_HMAC_SHA1 = "HmacSHA1";
    public static final java.lang.String KEY_ALGORITHM_HMAC_SHA224 = "HmacSHA224";
    public static final java.lang.String KEY_ALGORITHM_HMAC_SHA256 = "HmacSHA256";
    public static final java.lang.String KEY_ALGORITHM_HMAC_SHA384 = "HmacSHA384";
    public static final java.lang.String KEY_ALGORITHM_HMAC_SHA512 = "HmacSHA512";
    public static final java.lang.String KEY_ALGORITHM_RSA = "RSA";
    public static final java.lang.String KEY_ALGORITHM_XDH = "XDH";

    @android.annotation.SystemApi
    public static final int NAMESPACE_APPLICATION = -1;
    public static final int NAMESPACE_LOCKSETTINGS = 103;

    @android.annotation.SystemApi
    public static final int NAMESPACE_WIFI = 102;
    public static final int ORIGIN_GENERATED = 1;
    public static final int ORIGIN_IMPORTED = 2;
    public static final int ORIGIN_SECURELY_IMPORTED = 8;
    public static final int ORIGIN_UNKNOWN = 4;
    public static final int PURPOSE_AGREE_KEY = 64;
    public static final int PURPOSE_ATTEST_KEY = 128;
    public static final int PURPOSE_DECRYPT = 2;
    public static final int PURPOSE_ENCRYPT = 1;
    public static final int PURPOSE_SIGN = 4;
    public static final int PURPOSE_VERIFY = 8;
    public static final int PURPOSE_WRAP_KEY = 32;
    public static final int SECURITY_LEVEL_SOFTWARE = 0;
    public static final int SECURITY_LEVEL_STRONGBOX = 2;
    public static final int SECURITY_LEVEL_TRUSTED_ENVIRONMENT = 1;
    public static final int SECURITY_LEVEL_UNKNOWN = -2;
    public static final int SECURITY_LEVEL_UNKNOWN_SECURE = -1;
    public static final java.lang.String SIGNATURE_PADDING_RSA_PKCS1 = "PKCS1";
    public static final java.lang.String SIGNATURE_PADDING_RSA_PSS = "PSS";
    public static final int UNRESTRICTED_USAGE_COUNT = -1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AuthEnum {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BlockModeEnum {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DigestEnum {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EncryptionPaddingEnum {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface KeyAlgorithmEnum {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Namespace {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OriginEnum {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PurposeEnum {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SecurityLevelEnum {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SignaturePaddingEnum {
    }

    private KeyProperties() {
    }

    public static abstract class Purpose {
        private Purpose() {
        }

        public static int toKeymaster(int i) {
            switch (i) {
                case 1:
                    return 0;
                case 2:
                    return 1;
                case 4:
                    return 2;
                case 8:
                    return 3;
                case 32:
                    return 5;
                case 64:
                    return 6;
                case 128:
                    return 7;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown purpose: " + i);
            }
        }

        public static int fromKeymaster(int i) {
            switch (i) {
                case 0:
                    return 1;
                case 1:
                    return 2;
                case 2:
                    return 4;
                case 3:
                    return 8;
                case 4:
                default:
                    throw new java.lang.IllegalArgumentException("Unknown purpose: " + i);
                case 5:
                    return 32;
                case 6:
                    return 64;
                case 7:
                    return 128;
            }
        }

        public static int[] allToKeymaster(int i) {
            int[] setFlags = android.security.keystore.KeyProperties.getSetFlags(i);
            for (int i2 = 0; i2 < setFlags.length; i2++) {
                setFlags[i2] = toKeymaster(setFlags[i2]);
            }
            return setFlags;
        }

        public static int allFromKeymaster(java.util.Collection<java.lang.Integer> collection) {
            java.util.Iterator<java.lang.Integer> it = collection.iterator();
            int i = 0;
            while (it.hasNext()) {
                i |= fromKeymaster(it.next().intValue());
            }
            return i;
        }
    }

    public static abstract class KeyAlgorithm {
        private KeyAlgorithm() {
        }

        public static int toKeymasterAsymmetricKeyAlgorithm(java.lang.String str) {
            if (android.security.keystore.KeyProperties.KEY_ALGORITHM_EC.equalsIgnoreCase(str) || android.security.keystore.KeyProperties.KEY_ALGORITHM_XDH.equalsIgnoreCase(str)) {
                return 3;
            }
            if (android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA.equalsIgnoreCase(str)) {
                return 1;
            }
            throw new java.lang.IllegalArgumentException("Unsupported key algorithm: " + str);
        }

        public static java.lang.String fromKeymasterAsymmetricKeyAlgorithm(int i) {
            switch (i) {
                case 1:
                    return android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA;
                case 2:
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported key algorithm: " + i);
                case 3:
                    return android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
            }
        }

        public static int toKeymasterSecretKeyAlgorithm(java.lang.String str) {
            if (android.security.keystore.KeyProperties.KEY_ALGORITHM_AES.equalsIgnoreCase(str)) {
                return 32;
            }
            if (android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES.equalsIgnoreCase(str)) {
                return 33;
            }
            if (str.toUpperCase(java.util.Locale.US).startsWith("HMAC")) {
                return 128;
            }
            throw new java.lang.IllegalArgumentException("Unsupported secret key algorithm: " + str);
        }

        public static java.lang.String fromKeymasterSecretKeyAlgorithm(int i, int i2) {
            switch (i) {
                case 32:
                    return android.security.keystore.KeyProperties.KEY_ALGORITHM_AES;
                case 33:
                    return android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES;
                case 128:
                    switch (i2) {
                        case 2:
                            return android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA1;
                        case 3:
                            return android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA224;
                        case 4:
                            return android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA256;
                        case 5:
                            return android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA384;
                        case 6:
                            return android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA512;
                        default:
                            throw new java.lang.IllegalArgumentException("Unsupported HMAC digest: " + android.security.keystore.KeyProperties.Digest.fromKeymaster(i2));
                    }
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported key algorithm: " + i);
            }
        }

        public static int toKeymasterDigest(java.lang.String str) {
            java.lang.String upperCase = str.toUpperCase(java.util.Locale.US);
            char c = 65535;
            if (!upperCase.startsWith("HMAC")) {
                return -1;
            }
            java.lang.String substring = upperCase.substring("HMAC".length());
            switch (substring.hashCode()) {
                case -1850268184:
                    if (substring.equals("SHA224")) {
                        c = 1;
                        break;
                    }
                    break;
                case -1850268089:
                    if (substring.equals("SHA256")) {
                        c = 2;
                        break;
                    }
                    break;
                case -1850267037:
                    if (substring.equals("SHA384")) {
                        c = 3;
                        break;
                    }
                    break;
                case -1850265334:
                    if (substring.equals("SHA512")) {
                        c = 4;
                        break;
                    }
                    break;
                case 2543909:
                    if (substring.equals("SHA1")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    return 2;
                case 1:
                    return 3;
                case 2:
                    return 4;
                case 3:
                    return 5;
                case 4:
                    return 6;
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported HMAC digest: " + substring);
            }
        }
    }

    public static abstract class BlockMode {
        private BlockMode() {
        }

        public static int toKeymaster(java.lang.String str) {
            if (android.security.keystore.KeyProperties.BLOCK_MODE_ECB.equalsIgnoreCase(str)) {
                return 1;
            }
            if (android.security.keystore.KeyProperties.BLOCK_MODE_CBC.equalsIgnoreCase(str)) {
                return 2;
            }
            if (android.security.keystore.KeyProperties.BLOCK_MODE_CTR.equalsIgnoreCase(str)) {
                return 3;
            }
            if (android.security.keystore.KeyProperties.BLOCK_MODE_GCM.equalsIgnoreCase(str)) {
                return 32;
            }
            throw new java.lang.IllegalArgumentException("Unsupported block mode: " + str);
        }

        public static java.lang.String fromKeymaster(int i) {
            switch (i) {
                case 1:
                    return android.security.keystore.KeyProperties.BLOCK_MODE_ECB;
                case 2:
                    return android.security.keystore.KeyProperties.BLOCK_MODE_CBC;
                case 3:
                    return android.security.keystore.KeyProperties.BLOCK_MODE_CTR;
                case 32:
                    return android.security.keystore.KeyProperties.BLOCK_MODE_GCM;
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported block mode: " + i);
            }
        }

        public static java.lang.String[] allFromKeymaster(java.util.Collection<java.lang.Integer> collection) {
            if (collection == null || collection.isEmpty()) {
                return libcore.util.EmptyArray.STRING;
            }
            java.lang.String[] strArr = new java.lang.String[collection.size()];
            java.util.Iterator<java.lang.Integer> it = collection.iterator();
            int i = 0;
            while (it.hasNext()) {
                strArr[i] = fromKeymaster(it.next().intValue());
                i++;
            }
            return strArr;
        }

        public static int[] allToKeymaster(java.lang.String[] strArr) {
            if (strArr == null || strArr.length == 0) {
                return libcore.util.EmptyArray.INT;
            }
            int[] iArr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                iArr[i] = toKeymaster(strArr[i]);
            }
            return iArr;
        }
    }

    public static abstract class EncryptionPadding {
        private EncryptionPadding() {
        }

        public static int toKeymaster(java.lang.String str) {
            if (android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE.equalsIgnoreCase(str)) {
                return 1;
            }
            if (android.security.keystore.KeyProperties.ENCRYPTION_PADDING_PKCS7.equalsIgnoreCase(str)) {
                return 64;
            }
            if (android.security.keystore.KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1.equalsIgnoreCase(str)) {
                return 4;
            }
            if (android.security.keystore.KeyProperties.ENCRYPTION_PADDING_RSA_OAEP.equalsIgnoreCase(str)) {
                return 2;
            }
            throw new java.lang.IllegalArgumentException("Unsupported encryption padding scheme: " + str);
        }

        public static java.lang.String fromKeymaster(int i) {
            switch (i) {
                case 1:
                    return android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE;
                case 2:
                    return android.security.keystore.KeyProperties.ENCRYPTION_PADDING_RSA_OAEP;
                case 4:
                    return android.security.keystore.KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1;
                case 64:
                    return android.security.keystore.KeyProperties.ENCRYPTION_PADDING_PKCS7;
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported encryption padding: " + i);
            }
        }

        public static int[] allToKeymaster(java.lang.String[] strArr) {
            if (strArr == null || strArr.length == 0) {
                return libcore.util.EmptyArray.INT;
            }
            int[] iArr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                iArr[i] = toKeymaster(strArr[i]);
            }
            return iArr;
        }
    }

    public static abstract class SignaturePadding {
        private SignaturePadding() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public static int toKeymaster(java.lang.String str) {
            char c;
            java.lang.String upperCase = str.toUpperCase(java.util.Locale.US);
            switch (upperCase.hashCode()) {
                case 79536:
                    if (upperCase.equals(android.security.keystore.KeyProperties.SIGNATURE_PADDING_RSA_PSS)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 76183014:
                    if (upperCase.equals(android.security.keystore.KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return 5;
                case 1:
                    return 3;
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported signature padding scheme: " + str);
            }
        }

        public static java.lang.String fromKeymaster(int i) {
            switch (i) {
                case 3:
                    return android.security.keystore.KeyProperties.SIGNATURE_PADDING_RSA_PSS;
                case 4:
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported signature padding: " + i);
                case 5:
                    return android.security.keystore.KeyProperties.SIGNATURE_PADDING_RSA_PKCS1;
            }
        }

        public static int[] allToKeymaster(java.lang.String[] strArr) {
            if (strArr == null || strArr.length == 0) {
                return libcore.util.EmptyArray.INT;
            }
            int[] iArr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                iArr[i] = toKeymaster(strArr[i]);
            }
            return iArr;
        }
    }

    public static abstract class Digest {
        private Digest() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public static int toKeymaster(java.lang.String str) {
            char c;
            java.lang.String upperCase = str.toUpperCase(java.util.Locale.US);
            switch (upperCase.hashCode()) {
                case -1523887821:
                    if (upperCase.equals(android.security.keystore.KeyProperties.DIGEST_SHA224)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1523887726:
                    if (upperCase.equals("SHA-256")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1523886674:
                    if (upperCase.equals(android.security.keystore.KeyProperties.DIGEST_SHA384)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1523884971:
                    if (upperCase.equals(android.security.keystore.KeyProperties.DIGEST_SHA512)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 76158:
                    if (upperCase.equals(android.security.keystore.KeyProperties.DIGEST_MD5)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 2402104:
                    if (upperCase.equals(android.security.keystore.KeyProperties.DIGEST_NONE)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 78861104:
                    if (upperCase.equals("SHA-1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return 2;
                case 1:
                    return 3;
                case 2:
                    return 4;
                case 3:
                    return 5;
                case 4:
                    return 6;
                case 5:
                    return 0;
                case 6:
                    return 1;
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported digest algorithm: " + str);
            }
        }

        public static java.lang.String fromKeymaster(int i) {
            switch (i) {
                case 0:
                    return android.security.keystore.KeyProperties.DIGEST_NONE;
                case 1:
                    return android.security.keystore.KeyProperties.DIGEST_MD5;
                case 2:
                    return "SHA-1";
                case 3:
                    return android.security.keystore.KeyProperties.DIGEST_SHA224;
                case 4:
                    return "SHA-256";
                case 5:
                    return android.security.keystore.KeyProperties.DIGEST_SHA384;
                case 6:
                    return android.security.keystore.KeyProperties.DIGEST_SHA512;
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported digest algorithm: " + i);
            }
        }

        public static java.security.spec.AlgorithmParameterSpec fromKeymasterToMGF1ParameterSpec(int i) {
            switch (i) {
                case 3:
                    return java.security.spec.MGF1ParameterSpec.SHA224;
                case 4:
                    return java.security.spec.MGF1ParameterSpec.SHA256;
                case 5:
                    return java.security.spec.MGF1ParameterSpec.SHA384;
                case 6:
                    return java.security.spec.MGF1ParameterSpec.SHA512;
                default:
                    return java.security.spec.MGF1ParameterSpec.SHA1;
            }
        }

        public static java.lang.String fromKeymasterToSignatureAlgorithmDigest(int i) {
            switch (i) {
                case 0:
                    return android.security.keystore.KeyProperties.DIGEST_NONE;
                case 1:
                    return android.security.keystore.KeyProperties.DIGEST_MD5;
                case 2:
                    return "SHA1";
                case 3:
                    return "SHA224";
                case 4:
                    return "SHA256";
                case 5:
                    return "SHA384";
                case 6:
                    return "SHA512";
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported digest algorithm: " + i);
            }
        }

        public static java.lang.String[] allFromKeymaster(java.util.Collection<java.lang.Integer> collection) {
            if (collection.isEmpty()) {
                return libcore.util.EmptyArray.STRING;
            }
            java.lang.String[] strArr = new java.lang.String[collection.size()];
            java.util.Iterator<java.lang.Integer> it = collection.iterator();
            int i = 0;
            while (it.hasNext()) {
                strArr[i] = fromKeymaster(it.next().intValue());
                i++;
            }
            return strArr;
        }

        public static int[] allToKeymaster(java.lang.String[] strArr) {
            if (strArr == null || strArr.length == 0) {
                return libcore.util.EmptyArray.INT;
            }
            int[] iArr = new int[strArr.length];
            int i = 0;
            for (java.lang.String str : strArr) {
                iArr[i] = toKeymaster(str);
                i++;
            }
            return iArr;
        }
    }

    public static abstract class Origin {
        private Origin() {
        }

        public static int fromKeymaster(int i) {
            switch (i) {
                case 0:
                    return 1;
                case 1:
                default:
                    throw new java.lang.IllegalArgumentException("Unknown origin: " + i);
                case 2:
                    return 2;
                case 3:
                    return 4;
                case 4:
                    return 8;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int[] getSetFlags(int i) {
        if (i == 0) {
            return libcore.util.EmptyArray.INT;
        }
        int[] iArr = new int[getSetBitCount(i)];
        int i2 = 1;
        int i3 = 0;
        while (i != 0) {
            if ((i & 1) != 0) {
                iArr[i3] = i2;
                i3++;
            }
            i >>>= 1;
            i2 <<= 1;
        }
        return iArr;
    }

    private static int getSetBitCount(int i) {
        int i2 = 0;
        if (i == 0) {
            return 0;
        }
        while (i != 0) {
            if ((i & 1) != 0) {
                i2++;
            }
            i >>>= 1;
        }
        return i2;
    }

    public static abstract class SecurityLevel {
        private SecurityLevel() {
        }

        public static int toKeymaster(int i) {
            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported security level: " + i);
            }
        }

        public static int fromKeymaster(int i) {
            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported security level: " + i);
            }
        }
    }

    public static abstract class EcCurve {
        private EcCurve() {
        }

        public static int toKeymasterCurve(java.security.spec.ECParameterSpec eCParameterSpec) {
            switch (eCParameterSpec.getCurve().getField().getFieldSize()) {
                case 224:
                    return 0;
                case 256:
                    return 1;
                case 384:
                    return 2;
                case 521:
                    return 3;
                default:
                    return -1;
            }
        }

        public static int fromKeymasterCurve(int i) {
            switch (i) {
                case 0:
                    return 224;
                case 1:
                case 4:
                    return 256;
                case 2:
                    return 384;
                case 3:
                    return 521;
                default:
                    return -1;
            }
        }
    }

    public static int namespaceToLegacyUid(int i) {
        switch (i) {
            case -1:
                return -1;
            case 102:
                return 1010;
            default:
                throw new java.lang.IllegalArgumentException("No UID corresponding to namespace " + i);
        }
    }

    public static int legacyUidToNamespace(int i) {
        switch (i) {
            case -1:
                return -1;
            case 1010:
                return 102;
            default:
                throw new java.lang.IllegalArgumentException("No namespace corresponding to uid " + i);
        }
    }
}
