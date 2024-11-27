package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

/* loaded from: classes4.dex */
public class PBESecretKeyFactory extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory implements com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE {
    private int digest;
    private boolean forCipher;
    private int ivSize;
    private int keySize;
    private int scheme;

    public PBESecretKeyFactory(java.lang.String str, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, int i, int i2, int i3, int i4) {
        super(str, aSN1ObjectIdentifier);
        this.forCipher = z;
        this.scheme = i;
        this.digest = i2;
        this.keySize = i3;
        this.ivSize = i4;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory, javax.crypto.SecretKeyFactorySpi
    protected javax.crypto.SecretKey engineGenerateSecret(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        com.android.internal.org.bouncycastle.crypto.CipherParameters makePBEMacParameters;
        if (keySpec instanceof javax.crypto.spec.PBEKeySpec) {
            javax.crypto.spec.PBEKeySpec pBEKeySpec = (javax.crypto.spec.PBEKeySpec) keySpec;
            if (pBEKeySpec.getSalt() == null) {
                return new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey(this.algName, this.algOid, this.scheme, this.digest, this.keySize, this.ivSize, pBEKeySpec, null);
            }
            if (this.forCipher) {
                makePBEMacParameters = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(pBEKeySpec, this.scheme, this.digest, this.keySize, this.ivSize);
            } else {
                makePBEMacParameters = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEMacParameters(pBEKeySpec, this.scheme, this.digest, this.keySize);
            }
            return new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey(this.algName, this.algOid, this.scheme, this.digest, this.keySize, this.ivSize, pBEKeySpec, makePBEMacParameters);
        }
        throw new java.security.spec.InvalidKeySpecException("Invalid KeySpec");
    }
}
