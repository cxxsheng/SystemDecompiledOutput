package com.android.internal.org.bouncycastle.jcajce;

/* loaded from: classes4.dex */
public class CompositePrivateKey implements java.security.PrivateKey {
    private final java.util.List<java.security.PrivateKey> keys;

    public CompositePrivateKey(java.security.PrivateKey... privateKeyArr) {
        if (privateKeyArr == null || privateKeyArr.length == 0) {
            throw new java.lang.IllegalArgumentException("at least one public key must be provided");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(privateKeyArr.length);
        for (int i = 0; i != privateKeyArr.length; i++) {
            arrayList.add(privateKeyArr[i]);
        }
        this.keys = java.util.Collections.unmodifiableList(arrayList);
    }

    public java.util.List<java.security.PrivateKey> getPrivateKeys() {
        return this.keys;
    }

    @Override // java.security.Key
    public java.lang.String getAlgorithm() {
        return "Composite";
    }

    @Override // java.security.Key
    public java.lang.String getFormat() {
        return "PKCS#8";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        for (int i = 0; i != this.keys.size(); i++) {
            aSN1EncodableVector.add(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(this.keys.get(i).getEncoded()));
        }
        try {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.id_alg_composite), new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector)).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("unable to encode composite key: " + e.getMessage());
        }
    }

    public int hashCode() {
        return this.keys.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.jcajce.CompositePrivateKey) {
            return this.keys.equals(((com.android.internal.org.bouncycastle.jcajce.CompositePrivateKey) obj).keys);
        }
        return false;
    }
}
