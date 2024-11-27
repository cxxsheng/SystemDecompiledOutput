package android.security.keystore2;

/* loaded from: classes3.dex */
public abstract class KeymasterUtils {
    private KeymasterUtils() {
    }

    static int getDigestOutputSizeBits(int i) {
        switch (i) {
            case 0:
                return -1;
            case 1:
                return 128;
            case 2:
                return 160;
            case 3:
                return 224;
            case 4:
                return 256;
            case 5:
                return 384;
            case 6:
                return 512;
            default:
                throw new java.lang.IllegalArgumentException("Unknown digest: " + i);
        }
    }

    static boolean isKeymasterBlockModeIndCpaCompatibleWithSymmetricCrypto(int i) {
        switch (i) {
            case 1:
                return false;
            case 2:
            case 3:
            case 32:
                return true;
            default:
                throw new java.lang.IllegalArgumentException("Unsupported block mode: " + i);
        }
    }

    static boolean isKeymasterPaddingSchemeIndCpaCompatibleWithAsymmetricCrypto(int i) {
        switch (i) {
            case 1:
                return false;
            case 2:
            case 4:
                return true;
            case 3:
            default:
                throw new java.lang.IllegalArgumentException("Unsupported asymmetric encryption padding scheme: " + i);
        }
    }

    public static void addMinMacLengthAuthorizationIfNecessary(android.security.keymaster.KeymasterArguments keymasterArguments, int i, int[] iArr, int[] iArr2) {
        switch (i) {
            case 32:
                if (com.android.internal.util.ArrayUtils.contains(iArr, 32)) {
                    keymasterArguments.addUnsignedInt(805306376, 96L);
                    return;
                }
                return;
            case 128:
                if (iArr2.length != 1) {
                    throw new java.security.ProviderException("Unsupported number of authorized digests for HMAC key: " + iArr2.length + ". Exactly one digest must be authorized");
                }
                int i2 = iArr2[0];
                int digestOutputSizeBits = getDigestOutputSizeBits(i2);
                if (digestOutputSizeBits != -1) {
                    keymasterArguments.addUnsignedInt(805306376, digestOutputSizeBits);
                    return;
                }
                throw new java.security.ProviderException("HMAC key authorized for unsupported digest: " + android.security.keystore.KeyProperties.Digest.fromKeymaster(i2));
            default:
                return;
        }
    }

    static java.lang.String getEcCurveFromKeymaster(int i) {
        switch (i) {
            case 0:
                return "secp224r1";
            case 1:
                return "secp256r1";
            case 2:
                return "secp384r1";
            case 3:
                return "secp521r1";
            default:
                return "";
        }
    }

    static int getKeymasterEcCurve(java.lang.String str) {
        if (str.equals("secp224r1")) {
            return 0;
        }
        if (str.equals("secp256r1")) {
            return 1;
        }
        if (str.equals("secp384r1")) {
            return 2;
        }
        if (str.equals("secp521r1")) {
            return 3;
        }
        return -1;
    }

    static java.security.spec.ECParameterSpec getCurveSpec(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.spec.InvalidParameterSpecException {
        java.security.AlgorithmParameters algorithmParameters = java.security.AlgorithmParameters.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_EC);
        algorithmParameters.init(new java.security.spec.ECGenParameterSpec(str));
        return (java.security.spec.ECParameterSpec) algorithmParameters.getParameterSpec(java.security.spec.ECParameterSpec.class);
    }

    static java.lang.String getCurveName(java.security.spec.ECParameterSpec eCParameterSpec) {
        if (isECParameterSpecOfCurve(eCParameterSpec, "secp224r1")) {
            return "secp224r1";
        }
        if (isECParameterSpecOfCurve(eCParameterSpec, "secp256r1")) {
            return "secp256r1";
        }
        if (isECParameterSpecOfCurve(eCParameterSpec, "secp384r1")) {
            return "secp384r1";
        }
        if (isECParameterSpecOfCurve(eCParameterSpec, "secp521r1")) {
            return "secp521r1";
        }
        return null;
    }

    private static boolean isECParameterSpecOfCurve(java.security.spec.ECParameterSpec eCParameterSpec, java.lang.String str) {
        try {
            java.security.spec.ECParameterSpec curveSpec = getCurveSpec(str);
            if (curveSpec.getCurve().equals(eCParameterSpec.getCurve()) && curveSpec.getOrder().equals(eCParameterSpec.getOrder())) {
                if (curveSpec.getGenerator().equals(eCParameterSpec.getGenerator())) {
                    return true;
                }
            }
            return false;
        } catch (java.security.NoSuchAlgorithmException | java.security.spec.InvalidParameterSpecException e) {
            return false;
        }
    }
}
