package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class PBES2Parameters extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers {
    private com.android.internal.org.bouncycastle.asn1.pkcs.KeyDerivationFunc func;
    private com.android.internal.org.bouncycastle.asn1.pkcs.EncryptionScheme scheme;

    public static com.android.internal.org.bouncycastle.asn1.pkcs.PBES2Parameters getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.PBES2Parameters) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.PBES2Parameters) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.PBES2Parameters(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public PBES2Parameters(com.android.internal.org.bouncycastle.asn1.pkcs.KeyDerivationFunc keyDerivationFunc, com.android.internal.org.bouncycastle.asn1.pkcs.EncryptionScheme encryptionScheme) {
        this.func = keyDerivationFunc;
        this.scheme = encryptionScheme;
    }

    private PBES2Parameters(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) objects.nextElement()).toASN1Primitive());
        if (aSN1Sequence2.getObjectAt(0).equals(id_PBKDF2)) {
            this.func = new com.android.internal.org.bouncycastle.asn1.pkcs.KeyDerivationFunc(id_PBKDF2, com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params.getInstance(aSN1Sequence2.getObjectAt(1)));
        } else {
            this.func = com.android.internal.org.bouncycastle.asn1.pkcs.KeyDerivationFunc.getInstance(aSN1Sequence2);
        }
        this.scheme = com.android.internal.org.bouncycastle.asn1.pkcs.EncryptionScheme.getInstance(objects.nextElement());
    }

    public com.android.internal.org.bouncycastle.asn1.pkcs.KeyDerivationFunc getKeyDerivationFunc() {
        return this.func;
    }

    public com.android.internal.org.bouncycastle.asn1.pkcs.EncryptionScheme getEncryptionScheme() {
        return this.scheme;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.func);
        aSN1EncodableVector.add(this.scheme);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
