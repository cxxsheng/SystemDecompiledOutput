package com.android.internal.org.bouncycastle.jcajce;

/* loaded from: classes4.dex */
public class CompositePublicKey implements java.security.PublicKey {
    private final java.util.List<java.security.PublicKey> keys;

    public CompositePublicKey(java.security.PublicKey... publicKeyArr) {
        if (publicKeyArr == null || publicKeyArr.length == 0) {
            throw new java.lang.IllegalArgumentException("at least one public key must be provided");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(publicKeyArr.length);
        for (int i = 0; i != publicKeyArr.length; i++) {
            arrayList.add(publicKeyArr[i]);
        }
        this.keys = java.util.Collections.unmodifiableList(arrayList);
    }

    public java.util.List<java.security.PublicKey> getPublicKeys() {
        return this.keys;
    }

    @Override // java.security.Key
    public java.lang.String getAlgorithm() {
        return "Composite";
    }

    @Override // java.security.Key
    public java.lang.String getFormat() {
        return "X.509";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        for (int i = 0; i != this.keys.size(); i++) {
            aSN1EncodableVector.add(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(this.keys.get(i).getEncoded()));
        }
        try {
            return new com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.id_alg_composite), new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector)).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
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
        if (obj instanceof com.android.internal.org.bouncycastle.jcajce.CompositePublicKey) {
            return this.keys.equals(((com.android.internal.org.bouncycastle.jcajce.CompositePublicKey) obj).keys);
        }
        return false;
    }
}
