package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

/* loaded from: classes4.dex */
public abstract class BaseAgreementSpi extends javax.crypto.KeyAgreementSpi {
    protected final java.lang.String kaAlgorithm;
    protected final com.android.internal.org.bouncycastle.crypto.DerivationFunction kdf;
    protected byte[] ukmParameters;
    private static final java.util.Map<java.lang.String, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier> defaultOids = new java.util.HashMap();
    private static final java.util.Map<java.lang.String, java.lang.Integer> keySizes = new java.util.HashMap();
    private static final java.util.Map<java.lang.String, java.lang.String> nameTable = new java.util.HashMap();
    private static final java.util.Hashtable oids = new java.util.Hashtable();
    private static final java.util.Hashtable des = new java.util.Hashtable();

    protected abstract byte[] calcSecret();

    static {
        java.lang.Integer valueOf = com.android.internal.org.bouncycastle.util.Integers.valueOf(64);
        java.lang.Integer valueOf2 = com.android.internal.org.bouncycastle.util.Integers.valueOf(128);
        java.lang.Integer valueOf3 = com.android.internal.org.bouncycastle.util.Integers.valueOf(192);
        java.lang.Integer valueOf4 = com.android.internal.org.bouncycastle.util.Integers.valueOf(256);
        keySizes.put("DES", valueOf);
        keySizes.put("DESEDE", valueOf3);
        keySizes.put("BLOWFISH", valueOf2);
        keySizes.put(android.security.keystore.KeyProperties.KEY_ALGORITHM_AES, valueOf4);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_ECB.getId(), valueOf2);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes192_ECB.getId(), valueOf3);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_ECB.getId(), valueOf4);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_CBC.getId(), valueOf2);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes192_CBC.getId(), valueOf3);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_CBC.getId(), valueOf4);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_CFB.getId(), valueOf2);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes192_CFB.getId(), valueOf3);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_CFB.getId(), valueOf4);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_OFB.getId(), valueOf2);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes192_OFB.getId(), valueOf3);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_OFB.getId(), valueOf4);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_wrap.getId(), valueOf2);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes192_wrap.getId(), valueOf3);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_wrap.getId(), valueOf4);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_CCM.getId(), valueOf2);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes192_CCM.getId(), valueOf3);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_CCM.getId(), valueOf4);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_GCM.getId(), valueOf2);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes192_GCM.getId(), valueOf3);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_GCM.getId(), valueOf4);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia128_wrap.getId(), valueOf2);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia192_wrap.getId(), valueOf3);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia256_wrap.getId(), valueOf4);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.kisa.KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap.getId(), valueOf2);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId(), valueOf3);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.des_EDE3_CBC.getId(), valueOf3);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.desCBC.getId(), valueOf);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA1.getId(), com.android.internal.org.bouncycastle.util.Integers.valueOf(160));
        keySizes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA256.getId(), valueOf4);
        keySizes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA384.getId(), com.android.internal.org.bouncycastle.util.Integers.valueOf(384));
        keySizes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA512.getId(), com.android.internal.org.bouncycastle.util.Integers.valueOf(512));
        defaultOids.put("DESEDE", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.des_EDE3_CBC);
        defaultOids.put(android.security.keystore.KeyProperties.KEY_ALGORITHM_AES, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_CBC);
        defaultOids.put("CAMELLIA", com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia256_cbc);
        defaultOids.put("SEED", com.android.internal.org.bouncycastle.asn1.kisa.KISAObjectIdentifiers.id_seedCBC);
        defaultOids.put("DES", com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.desCBC);
        nameTable.put(com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.cast5CBC.getId(), "CAST5");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.as_sys_sec_alg_ideaCBC.getId(), "IDEA");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.cryptlib_algorithm_blowfish_ECB.getId(), "Blowfish");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CBC.getId(), "Blowfish");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CFB.getId(), "Blowfish");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.cryptlib_algorithm_blowfish_OFB.getId(), "Blowfish");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.desECB.getId(), "DES");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.desCBC.getId(), "DES");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.desCFB.getId(), "DES");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.desOFB.getId(), "DES");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.desEDE.getId(), android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES);
        nameTable.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.des_EDE3_CBC.getId(), android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES);
        nameTable.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId(), android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES);
        nameTable.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_alg_CMSRC2wrap.getId(), "RC2");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA1.getId(), android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA1);
        nameTable.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA224.getId(), android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA224);
        nameTable.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA256.getId(), android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA256);
        nameTable.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA384.getId(), android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA384);
        nameTable.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA512.getId(), android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA512);
        nameTable.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia128_cbc.getId(), "Camellia");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia192_cbc.getId(), "Camellia");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia256_cbc.getId(), "Camellia");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia128_wrap.getId(), "Camellia");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia192_wrap.getId(), "Camellia");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia256_wrap.getId(), "Camellia");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.kisa.KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap.getId(), "SEED");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.kisa.KISAObjectIdentifiers.id_seedCBC.getId(), "SEED");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.kisa.KISAObjectIdentifiers.id_seedMAC.getId(), "SEED");
        nameTable.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_wrap.getId(), android.security.keystore.KeyProperties.KEY_ALGORITHM_AES);
        nameTable.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_CCM.getId(), android.security.keystore.KeyProperties.KEY_ALGORITHM_AES);
        nameTable.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_CCM.getId(), android.security.keystore.KeyProperties.KEY_ALGORITHM_AES);
        oids.put("DESEDE", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.des_EDE3_CBC);
        oids.put(android.security.keystore.KeyProperties.KEY_ALGORITHM_AES, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_CBC);
        oids.put("DES", com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.desCBC);
        des.put("DES", "DES");
        des.put("DESEDE", "DES");
        des.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.desCBC.getId(), "DES");
        des.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.des_EDE3_CBC.getId(), "DES");
        des.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId(), "DES");
    }

    public BaseAgreementSpi(java.lang.String str, com.android.internal.org.bouncycastle.crypto.DerivationFunction derivationFunction) {
        this.kaAlgorithm = str;
        this.kdf = derivationFunction;
    }

    protected static java.lang.String getAlgorithm(java.lang.String str) {
        if (str.indexOf(91) > 0) {
            return str.substring(0, str.indexOf(91));
        }
        if (str.startsWith(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.aes.getId())) {
            return android.security.keystore.KeyProperties.KEY_ALGORITHM_AES;
        }
        java.lang.String str2 = nameTable.get(com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str));
        if (str2 != null) {
            return str2;
        }
        return str;
    }

    protected static int getKeySize(java.lang.String str) {
        if (str.indexOf(91) > 0) {
            return java.lang.Integer.parseInt(str.substring(str.indexOf(91) + 1, str.indexOf(93)));
        }
        java.lang.String upperCase = com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str);
        if (!keySizes.containsKey(upperCase)) {
            return -1;
        }
        return keySizes.get(upperCase).intValue();
    }

    protected static byte[] trimZeroes(byte[] bArr) {
        if (bArr[0] != 0) {
            return bArr;
        }
        int i = 0;
        while (i < bArr.length && bArr[i] == 0) {
            i++;
        }
        int length = bArr.length - i;
        byte[] bArr2 = new byte[length];
        java.lang.System.arraycopy(bArr, i, bArr2, 0, length);
        return bArr2;
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected byte[] engineGenerateSecret() throws java.lang.IllegalStateException {
        if (this.kdf != null) {
            byte[] calcSecret = calcSecret();
            try {
                return getSharedSecretBytes(calcSecret, null, calcSecret.length * 8);
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new java.lang.IllegalStateException(e.getMessage());
            }
        }
        return calcSecret();
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected int engineGenerateSecret(byte[] bArr, int i) throws java.lang.IllegalStateException, javax.crypto.ShortBufferException {
        byte[] engineGenerateSecret = engineGenerateSecret();
        if (bArr.length - i < engineGenerateSecret.length) {
            throw new javax.crypto.ShortBufferException(this.kaAlgorithm + " key agreement: need " + engineGenerateSecret.length + " bytes");
        }
        java.lang.System.arraycopy(engineGenerateSecret, 0, bArr, i, engineGenerateSecret.length);
        return engineGenerateSecret.length;
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected javax.crypto.SecretKey engineGenerateSecret(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        java.lang.String str2;
        java.lang.String upperCase = com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str);
        if (!oids.containsKey(upperCase)) {
            str2 = str;
        } else {
            str2 = ((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oids.get(upperCase)).getId();
        }
        byte[] sharedSecretBytes = getSharedSecretBytes(calcSecret(), str2, getKeySize(str2));
        java.lang.String algorithm = getAlgorithm(str);
        if (des.containsKey(algorithm)) {
            com.android.internal.org.bouncycastle.crypto.params.DESParameters.setOddParity(sharedSecretBytes);
        }
        return new javax.crypto.spec.SecretKeySpec(sharedSecretBytes, algorithm);
    }

    private byte[] getSharedSecretBytes(byte[] bArr, java.lang.String str, int i) throws java.security.NoSuchAlgorithmException {
        if (this.kdf != null) {
            if (i < 0) {
                throw new java.security.NoSuchAlgorithmException("unknown algorithm encountered: " + str);
            }
            int i2 = i / 8;
            byte[] bArr2 = new byte[i2];
            this.kdf.init(new com.android.internal.org.bouncycastle.crypto.params.KDFParameters(bArr, this.ukmParameters));
            this.kdf.generateBytes(bArr2, 0, i2);
            com.android.internal.org.bouncycastle.util.Arrays.clear(bArr);
            return bArr2;
        }
        if (i > 0) {
            int i3 = i / 8;
            byte[] bArr3 = new byte[i3];
            java.lang.System.arraycopy(bArr, 0, bArr3, 0, i3);
            com.android.internal.org.bouncycastle.util.Arrays.clear(bArr);
            return bArr3;
        }
        return bArr;
    }
}
