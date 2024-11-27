package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreKeyFactorySpi extends java.security.KeyFactorySpi {
    @Override // java.security.KeyFactorySpi
    protected <T extends java.security.spec.KeySpec> T engineGetKeySpec(java.security.Key key, java.lang.Class<T> cls) throws java.security.spec.InvalidKeySpecException {
        if (key == null) {
            throw new java.security.spec.InvalidKeySpecException("key == null");
        }
        boolean z = key instanceof android.security.keystore2.AndroidKeyStorePrivateKey;
        if (!z && !(key instanceof android.security.keystore2.AndroidKeyStorePublicKey)) {
            throw new java.security.spec.InvalidKeySpecException("Unsupported key type: " + key.getClass().getName() + ". This KeyFactory supports only Android Keystore asymmetric keys");
        }
        if (cls == null) {
            throw new java.security.spec.InvalidKeySpecException("keySpecClass == null");
        }
        if (android.security.keystore.KeyInfo.class.equals(cls)) {
            if (!z) {
                throw new java.security.spec.InvalidKeySpecException("Unsupported key type: " + key.getClass().getName() + ". KeyInfo can be obtained only for Android Keystore private keys");
            }
            return android.security.keystore2.AndroidKeyStoreSecretKeyFactorySpi.getKeyInfo((android.security.keystore2.AndroidKeyStorePrivateKey) key);
        }
        if (java.security.spec.X509EncodedKeySpec.class.equals(cls)) {
            if (!(key instanceof android.security.keystore2.AndroidKeyStorePublicKey)) {
                throw new java.security.spec.InvalidKeySpecException("Unsupported key type: " + key.getClass().getName() + ". X509EncodedKeySpec can be obtained only for Android Keystore public keys");
            }
            return new java.security.spec.X509EncodedKeySpec(((android.security.keystore2.AndroidKeyStorePublicKey) key).getEncoded());
        }
        if (java.security.spec.PKCS8EncodedKeySpec.class.equals(cls)) {
            if (z) {
                throw new java.security.spec.InvalidKeySpecException("Key material export of Android Keystore private keys is not supported");
            }
            throw new java.security.spec.InvalidKeySpecException("Cannot export key material of public key in PKCS#8 format. Only X.509 format (X509EncodedKeySpec) supported for public keys.");
        }
        boolean equals = java.security.spec.RSAPublicKeySpec.class.equals(cls);
        java.lang.String str = android.os.storage.VolumeInfo.ID_PRIVATE_INTERNAL;
        if (equals) {
            if (key instanceof android.security.keystore2.AndroidKeyStoreRSAPublicKey) {
                android.security.keystore2.AndroidKeyStoreRSAPublicKey androidKeyStoreRSAPublicKey = (android.security.keystore2.AndroidKeyStoreRSAPublicKey) key;
                return new java.security.spec.RSAPublicKeySpec(androidKeyStoreRSAPublicKey.getModulus(), androidKeyStoreRSAPublicKey.getPublicExponent());
            }
            java.lang.StringBuilder append = new java.lang.StringBuilder().append("Obtaining RSAPublicKeySpec not supported for ").append(key.getAlgorithm()).append(" ");
            if (!z) {
                str = "public";
            }
            throw new java.security.spec.InvalidKeySpecException(append.append(str).append(" key").toString());
        }
        if (java.security.spec.ECPublicKeySpec.class.equals(cls)) {
            if (key instanceof android.security.keystore2.AndroidKeyStoreECPublicKey) {
                android.security.keystore2.AndroidKeyStoreECPublicKey androidKeyStoreECPublicKey = (android.security.keystore2.AndroidKeyStoreECPublicKey) key;
                return new java.security.spec.ECPublicKeySpec(androidKeyStoreECPublicKey.getW(), androidKeyStoreECPublicKey.getParams());
            }
            java.lang.StringBuilder append2 = new java.lang.StringBuilder().append("Obtaining ECPublicKeySpec not supported for ").append(key.getAlgorithm()).append(" ");
            if (!z) {
                str = "public";
            }
            throw new java.security.spec.InvalidKeySpecException(append2.append(str).append(" key").toString());
        }
        throw new java.security.spec.InvalidKeySpecException("Unsupported key spec: " + cls.getName());
    }

    @Override // java.security.KeyFactorySpi
    protected java.security.PrivateKey engineGeneratePrivate(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        throw new java.security.spec.InvalidKeySpecException("To generate a key pair in Android Keystore, use KeyPairGenerator initialized with " + android.security.keystore.KeyGenParameterSpec.class.getName());
    }

    @Override // java.security.KeyFactorySpi
    protected java.security.PublicKey engineGeneratePublic(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        throw new java.security.spec.InvalidKeySpecException("To generate a key pair in Android Keystore, use KeyPairGenerator initialized with " + android.security.keystore.KeyGenParameterSpec.class.getName());
    }

    @Override // java.security.KeyFactorySpi
    protected java.security.Key engineTranslateKey(java.security.Key key) throws java.security.InvalidKeyException {
        if (key == null) {
            throw new java.security.InvalidKeyException("key == null");
        }
        if (!(key instanceof android.security.keystore2.AndroidKeyStorePrivateKey) && !(key instanceof android.security.keystore2.AndroidKeyStorePublicKey)) {
            throw new java.security.InvalidKeyException("To import a key into Android Keystore, use KeyStore.setEntry");
        }
        return key;
    }
}
