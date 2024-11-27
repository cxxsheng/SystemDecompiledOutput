package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

/* loaded from: classes4.dex */
public class BaseMac extends javax.crypto.MacSpi implements com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE {
    private static final java.lang.Class gcmSpecClass = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil.loadClass(com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac.class, "javax.crypto.spec.GCMParameterSpec");
    private int keySize;
    private com.android.internal.org.bouncycastle.crypto.Mac macEngine;
    private int pbeHash;
    private int scheme;

    protected BaseMac(com.android.internal.org.bouncycastle.crypto.Mac mac) {
        this.scheme = 2;
        this.pbeHash = 1;
        this.keySize = 160;
        this.macEngine = mac;
    }

    protected BaseMac(com.android.internal.org.bouncycastle.crypto.Mac mac, int i, int i2, int i3) {
        this.scheme = 2;
        this.pbeHash = 1;
        this.keySize = 160;
        this.macEngine = mac;
        this.scheme = i;
        this.pbeHash = i2;
        this.keySize = i3;
    }

    @Override // javax.crypto.MacSpi
    protected void engineInit(java.security.Key key, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        int i;
        int i2;
        com.android.internal.org.bouncycastle.crypto.CipherParameters makePBEMacParameters;
        com.android.internal.org.bouncycastle.crypto.params.KeyParameter keyParameter;
        if (key == null) {
            throw new java.security.InvalidKeyException("key is null");
        }
        if (key instanceof com.android.internal.org.bouncycastle.jcajce.PKCS12Key) {
            try {
                javax.crypto.SecretKey secretKey = (javax.crypto.SecretKey) key;
                try {
                    javax.crypto.spec.PBEParameterSpec pBEParameterSpec = (javax.crypto.spec.PBEParameterSpec) algorithmParameterSpec;
                    if ((secretKey instanceof javax.crypto.interfaces.PBEKey) && pBEParameterSpec == null) {
                        javax.crypto.interfaces.PBEKey pBEKey = (javax.crypto.interfaces.PBEKey) secretKey;
                        pBEParameterSpec = new javax.crypto.spec.PBEParameterSpec(pBEKey.getSalt(), pBEKey.getIterationCount());
                    }
                    if ((this.macEngine instanceof com.android.internal.org.bouncycastle.crypto.macs.HMac) && !this.macEngine.getAlgorithmName().startsWith("SHA-1")) {
                        if (this.macEngine.getAlgorithmName().startsWith(android.security.keystore.KeyProperties.DIGEST_SHA224)) {
                            i = 7;
                            i2 = 224;
                        } else if (this.macEngine.getAlgorithmName().startsWith("SHA-256")) {
                            i = 4;
                            i2 = 256;
                        } else if (this.macEngine.getAlgorithmName().startsWith(android.security.keystore.KeyProperties.DIGEST_SHA384)) {
                            i = 8;
                            i2 = 384;
                        } else if (this.macEngine.getAlgorithmName().startsWith(android.security.keystore.KeyProperties.DIGEST_SHA512)) {
                            i = 9;
                            i2 = 512;
                        } else {
                            throw new java.security.InvalidAlgorithmParameterException("no PKCS12 mapping for HMAC: " + this.macEngine.getAlgorithmName());
                        }
                    } else {
                        i = 1;
                        i2 = 160;
                    }
                    makePBEMacParameters = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEMacParameters(secretKey, 2, i, i2, pBEParameterSpec);
                } catch (java.lang.Exception e) {
                    throw new java.security.InvalidAlgorithmParameterException("PKCS12 requires a PBEParameterSpec");
                }
            } catch (java.lang.Exception e2) {
                throw new java.security.InvalidKeyException("PKCS12 requires a SecretKey/PBEKey");
            }
        } else if (key instanceof com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) {
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey bCPBEKey = (com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) key;
            if (bCPBEKey.getParam() != null) {
                makePBEMacParameters = bCPBEKey.getParam();
            } else if (algorithmParameterSpec instanceof javax.crypto.spec.PBEParameterSpec) {
                makePBEMacParameters = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEMacParameters(bCPBEKey, algorithmParameterSpec);
            } else {
                throw new java.security.InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
            }
        } else {
            if (algorithmParameterSpec instanceof javax.crypto.spec.PBEParameterSpec) {
                throw new java.security.InvalidAlgorithmParameterException("inappropriate parameter type: " + algorithmParameterSpec.getClass().getName());
            }
            makePBEMacParameters = new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(key.getEncoded());
        }
        if (makePBEMacParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
            keyParameter = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) makePBEMacParameters).getParameters();
        } else {
            keyParameter = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) makePBEMacParameters;
        }
        if (algorithmParameterSpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.AEADParameterSpec) {
            com.android.internal.org.bouncycastle.jcajce.spec.AEADParameterSpec aEADParameterSpec = (com.android.internal.org.bouncycastle.jcajce.spec.AEADParameterSpec) algorithmParameterSpec;
            makePBEMacParameters = new com.android.internal.org.bouncycastle.crypto.params.AEADParameters(keyParameter, aEADParameterSpec.getMacSizeInBits(), aEADParameterSpec.getNonce(), aEADParameterSpec.getAssociatedData());
        } else if (algorithmParameterSpec instanceof javax.crypto.spec.IvParameterSpec) {
            makePBEMacParameters = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(keyParameter, ((javax.crypto.spec.IvParameterSpec) algorithmParameterSpec).getIV());
        } else if (algorithmParameterSpec == null) {
            makePBEMacParameters = new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(key.getEncoded());
        } else if (gcmSpecClass != null && gcmSpecClass.isAssignableFrom(algorithmParameterSpec.getClass())) {
            makePBEMacParameters = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.extractAeadParameters(keyParameter, algorithmParameterSpec);
        } else if (!(algorithmParameterSpec instanceof javax.crypto.spec.PBEParameterSpec)) {
            throw new java.security.InvalidAlgorithmParameterException("unknown parameter type: " + algorithmParameterSpec.getClass().getName());
        }
        try {
            this.macEngine.init(makePBEMacParameters);
        } catch (java.lang.Exception e3) {
            throw new java.security.InvalidAlgorithmParameterException("cannot initialize MAC: " + e3.getMessage());
        }
    }

    @Override // javax.crypto.MacSpi
    protected int engineGetMacLength() {
        return this.macEngine.getMacSize();
    }

    @Override // javax.crypto.MacSpi
    protected void engineReset() {
        this.macEngine.reset();
    }

    @Override // javax.crypto.MacSpi
    protected void engineUpdate(byte b) {
        this.macEngine.update(b);
    }

    @Override // javax.crypto.MacSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        this.macEngine.update(bArr, i, i2);
    }

    @Override // javax.crypto.MacSpi
    protected byte[] engineDoFinal() {
        byte[] bArr = new byte[engineGetMacLength()];
        this.macEngine.doFinal(bArr, 0);
        return bArr;
    }

    private static java.util.Hashtable copyMap(java.util.Map map) {
        java.util.Hashtable hashtable = new java.util.Hashtable();
        for (java.lang.Object obj : map.keySet()) {
            hashtable.put(obj, map.get(obj));
        }
        return hashtable;
    }
}
