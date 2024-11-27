package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreSecretKeyFactorySpi extends javax.crypto.SecretKeyFactorySpi {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.crypto.SecretKeyFactorySpi
    protected java.security.spec.KeySpec engineGetKeySpec(javax.crypto.SecretKey secretKey, java.lang.Class cls) throws java.security.spec.InvalidKeySpecException {
        if (cls == null) {
            throw new java.security.spec.InvalidKeySpecException("keySpecClass == null");
        }
        if (!(secretKey instanceof android.security.keystore2.AndroidKeyStoreSecretKey)) {
            throw new java.security.spec.InvalidKeySpecException("Only Android KeyStore secret keys supported: " + (secretKey != 0 ? secretKey.getClass().getName() : "null"));
        }
        if (javax.crypto.spec.SecretKeySpec.class.isAssignableFrom(cls)) {
            throw new java.security.spec.InvalidKeySpecException("Key material export of Android KeyStore keys is not supported");
        }
        if (!android.security.keystore.KeyInfo.class.equals(cls)) {
            throw new java.security.spec.InvalidKeySpecException("Unsupported key spec: " + cls.getName());
        }
        return getKeyInfo((android.security.keystore2.AndroidKeyStoreKey) secretKey);
    }

    /* JADX WARN: Removed duplicated region for block: B:94:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0232  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static android.security.keystore.KeyInfo getKeyInfo(android.security.keystore2.AndroidKeyStoreKey androidKeyStoreKey) {
        long j;
        java.util.Date date;
        java.util.Date date2;
        java.util.Date date3;
        int i;
        int i2;
        int i3;
        int i4;
        boolean z;
        int i5;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z7;
        boolean z8;
        int i10;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        java.util.ArrayList arrayList4 = new java.util.ArrayList();
        java.util.ArrayList arrayList5 = new java.util.ArrayList();
        try {
            j = 0;
            date = null;
            date2 = null;
            date3 = null;
            i = -1;
            i2 = 0;
            i4 = -1;
            z = false;
            i5 = 0;
            z2 = true;
            z3 = false;
            z4 = false;
            z5 = false;
            z6 = false;
            i6 = 0;
            i7 = -1;
            i8 = 0;
        } catch (java.lang.IllegalArgumentException e) {
            throw new java.security.ProviderException("Unsupported key characteristic", e);
        }
        for (android.system.keystore2.Authorization authorization : androidKeyStoreKey.getAuthorizations()) {
            switch (authorization.keyParameter.tag) {
                case -1610612234:
                    arrayList3.add(android.security.keymaster.KeymasterArguments.toUint64(authorization.keyParameter.value.getLongInteger()));
                    continue;
                case 268435960:
                    int hardwareAuthenticatorType = authorization.keyParameter.value.getHardwareAuthenticatorType();
                    if (android.security.keystore2.KeyStore2ParameterUtils.isSecureHardware(authorization.securityLevel)) {
                        i2 = hardwareAuthenticatorType;
                        continue;
                    } else {
                        i8 = hardwareAuthenticatorType;
                    }
                case 268436158:
                    z = android.security.keystore2.KeyStore2ParameterUtils.isSecureHardware(authorization.securityLevel);
                    int i11 = authorization.securityLevel;
                    i = android.security.keystore.KeyProperties.Origin.fromKeymaster(authorization.keyParameter.value.getOrigin());
                    i6 = i11;
                    continue;
                case 536870913:
                    i5 |= android.security.keystore.KeyProperties.Purpose.fromKeymaster(authorization.keyParameter.value.getKeyPurpose());
                    continue;
                case 536870916:
                    arrayList2.add(android.security.keystore.KeyProperties.BlockMode.fromKeymaster(authorization.keyParameter.value.getBlockMode()));
                    continue;
                case 536870917:
                    arrayList.add(android.security.keystore.KeyProperties.Digest.fromKeymaster(authorization.keyParameter.value.getDigest()));
                    continue;
                case 536870918:
                    int paddingMode = authorization.keyParameter.value.getPaddingMode();
                    if (paddingMode == 5 || paddingMode == 3) {
                        arrayList5.add(android.security.keystore.KeyProperties.SignaturePadding.fromKeymaster(paddingMode));
                    } else {
                        try {
                            arrayList4.add(android.security.keystore.KeyProperties.EncryptionPadding.fromKeymaster(paddingMode));
                        } catch (java.lang.IllegalArgumentException e2) {
                            throw new java.security.ProviderException("Unsupported padding: " + paddingMode);
                        }
                    }
                    break;
                case 805306371:
                    long unsignedInt = android.security.keystore2.KeyStore2ParameterUtils.getUnsignedInt(authorization);
                    if (unsignedInt > 2147483647L) {
                        throw new java.security.ProviderException("Key too large: " + unsignedInt + " bits");
                    }
                    i4 = (int) unsignedInt;
                    continue;
                case 805306773:
                    long unsignedInt2 = android.security.keystore2.KeyStore2ParameterUtils.getUnsignedInt(authorization);
                    if (unsignedInt2 > 2147483647L) {
                        throw new java.security.ProviderException("Usage count of limited use key too long: " + unsignedInt2);
                    }
                    i7 = (int) unsignedInt2;
                    continue;
                case 805306873:
                    long unsignedInt3 = android.security.keystore2.KeyStore2ParameterUtils.getUnsignedInt(authorization);
                    if (unsignedInt3 <= 2147483647L) {
                        j = unsignedInt3;
                        continue;
                    } else {
                        throw new java.security.ProviderException("User authentication timeout validity too long: " + unsignedInt3 + " seconds");
                    }
                case 1610613136:
                    date = android.security.keystore2.KeyStore2ParameterUtils.getDate(authorization);
                    continue;
                case 1610613137:
                    date2 = android.security.keystore2.KeyStore2ParameterUtils.getDate(authorization);
                    continue;
                case 1610613138:
                    date3 = android.security.keystore2.KeyStore2ParameterUtils.getDate(authorization);
                    continue;
                case 1879048695:
                    z2 = false;
                    continue;
                case 1879048698:
                    z3 = android.security.keystore2.KeyStore2ParameterUtils.isSecureHardware(authorization.securityLevel);
                    continue;
                case 1879048699:
                    z5 = android.security.keystore2.KeyStore2ParameterUtils.isSecureHardware(authorization.securityLevel);
                    continue;
                case 1879048700:
                    z6 = android.security.keystore2.KeyStore2ParameterUtils.isSecureHardware(authorization.securityLevel);
                    continue;
                case 1879048701:
                    z4 = true;
                    continue;
                default:
                    continue;
            }
            throw new java.security.ProviderException("Unsupported key characteristic", e);
        }
        if (i4 == -1) {
            throw new java.security.ProviderException("Key size not available");
        }
        if (i == -1) {
            throw new java.security.ProviderException("Key origin not available");
        }
        java.lang.String[] strArr = (java.lang.String[]) arrayList4.toArray(new java.lang.String[0]);
        java.lang.String[] strArr2 = (java.lang.String[]) arrayList5.toArray(new java.lang.String[0]);
        if (!z2 || i2 == 0) {
            i9 = i8;
        } else {
            i9 = i8;
            if (i9 == 0) {
                z7 = true;
                java.lang.String[] strArr3 = (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
                java.lang.String[] strArr4 = (java.lang.String[]) arrayList2.toArray(new java.lang.String[0]);
                if (i9 == 2 && i2 != 2) {
                    z8 = false;
                } else {
                    z8 = arrayList3.isEmpty() && !arrayList3.contains(getGateKeeperSecureUserId());
                }
                java.lang.String str = androidKeyStoreKey.getUserKeyDescriptor().alias;
                int i12 = (int) j;
                if (!z7) {
                    i10 = i2;
                } else {
                    i10 = i9;
                }
                return new android.security.keystore.KeyInfo(str, z, i, i4, date, date2, date3, i5, strArr, strArr2, strArr3, strArr4, z2, i12, i10, z7, z3, z4, z5, z8, z6, i6, i7);
            }
        }
        z7 = false;
        java.lang.String[] strArr32 = (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
        java.lang.String[] strArr42 = (java.lang.String[]) arrayList2.toArray(new java.lang.String[0]);
        if (i9 == 2) {
        }
        z8 = arrayList3.isEmpty() && !arrayList3.contains(getGateKeeperSecureUserId());
        java.lang.String str2 = androidKeyStoreKey.getUserKeyDescriptor().alias;
        int i122 = (int) j;
        if (!z7) {
        }
        return new android.security.keystore.KeyInfo(str2, z, i, i4, date, date2, date3, i5, strArr, strArr2, strArr32, strArr42, z2, i122, i10, z7, z3, z4, z5, z8, z6, i6, i7);
    }

    private static java.math.BigInteger getGateKeeperSecureUserId() throws java.security.ProviderException {
        try {
            return java.math.BigInteger.valueOf(android.security.GateKeeper.getSecureUserId());
        } catch (java.lang.IllegalStateException e) {
            throw new java.security.ProviderException("Failed to get GateKeeper secure user ID", e);
        }
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected javax.crypto.SecretKey engineGenerateSecret(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        throw new java.security.spec.InvalidKeySpecException("To generate secret key in Android Keystore, use KeyGenerator initialized with " + android.security.keystore.KeyGenParameterSpec.class.getName());
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected javax.crypto.SecretKey engineTranslateKey(javax.crypto.SecretKey secretKey) throws java.security.InvalidKeyException {
        if (secretKey == null) {
            throw new java.security.InvalidKeyException("key == null");
        }
        if (!(secretKey instanceof android.security.keystore2.AndroidKeyStoreSecretKey)) {
            throw new java.security.InvalidKeyException("To import a secret key into Android Keystore, use KeyStore.setEntry");
        }
        return secretKey;
    }
}
