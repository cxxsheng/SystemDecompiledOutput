package com.android.internal.org.bouncycastle.jcajce.provider.util;

/* loaded from: classes4.dex */
public class SecretKeyUtil {
    private static java.util.Map keySizes = new java.util.HashMap();

    static {
        keySizes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.des_EDE3_CBC.getId(), com.android.internal.org.bouncycastle.util.Integers.valueOf(192));
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_CBC, com.android.internal.org.bouncycastle.util.Integers.valueOf(128));
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes192_CBC, com.android.internal.org.bouncycastle.util.Integers.valueOf(192));
        keySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_CBC, com.android.internal.org.bouncycastle.util.Integers.valueOf(256));
        keySizes.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia128_cbc, com.android.internal.org.bouncycastle.util.Integers.valueOf(128));
        keySizes.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia192_cbc, com.android.internal.org.bouncycastle.util.Integers.valueOf(192));
        keySizes.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia256_cbc, com.android.internal.org.bouncycastle.util.Integers.valueOf(256));
    }

    public static int getKeySize(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.lang.Integer num = (java.lang.Integer) keySizes.get(aSN1ObjectIdentifier);
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }
}
