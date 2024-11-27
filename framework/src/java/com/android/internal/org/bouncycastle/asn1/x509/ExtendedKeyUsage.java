package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class ExtendedKeyUsage extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ASN1Sequence seq;
    java.util.Hashtable usageTable = new java.util.Hashtable();

    public static com.android.internal.org.bouncycastle.asn1.x509.ExtendedKeyUsage getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.ExtendedKeyUsage getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.ExtendedKeyUsage) {
            return (com.android.internal.org.bouncycastle.asn1.x509.ExtendedKeyUsage) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.ExtendedKeyUsage(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.ExtendedKeyUsage fromExtensions(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.x509.Extensions.getExtensionParsedValue(extensions, com.android.internal.org.bouncycastle.asn1.x509.Extension.extendedKeyUsage));
    }

    public ExtendedKeyUsage(com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId keyPurposeId) {
        this.seq = new com.android.internal.org.bouncycastle.asn1.DERSequence(keyPurposeId);
        this.usageTable.put(keyPurposeId, keyPurposeId);
    }

    private ExtendedKeyUsage(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.seq = aSN1Sequence;
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable = (com.android.internal.org.bouncycastle.asn1.ASN1Encodable) objects.nextElement();
            if (!(aSN1Encodable.toASN1Primitive() instanceof com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier)) {
                throw new java.lang.IllegalArgumentException("Only ASN1ObjectIdentifiers allowed in ExtendedKeyUsage.");
            }
            this.usageTable.put(aSN1Encodable, aSN1Encodable);
        }
    }

    public ExtendedKeyUsage(com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId[] keyPurposeIdArr) {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(keyPurposeIdArr.length);
        for (int i = 0; i != keyPurposeIdArr.length; i++) {
            aSN1EncodableVector.add(keyPurposeIdArr[i]);
            this.usageTable.put(keyPurposeIdArr[i], keyPurposeIdArr[i]);
        }
        this.seq = new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    public ExtendedKeyUsage(java.util.Vector vector) {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(vector.size());
        java.util.Enumeration elements = vector.elements();
        while (elements.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId keyPurposeId = com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId.getInstance(elements.nextElement());
            aSN1EncodableVector.add(keyPurposeId);
            this.usageTable.put(keyPurposeId, keyPurposeId);
        }
        this.seq = new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    public boolean hasKeyPurposeId(com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId keyPurposeId) {
        return this.usageTable.get(keyPurposeId) != null;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId[] getUsages() {
        com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId[] keyPurposeIdArr = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId[this.seq.size()];
        java.util.Enumeration objects = this.seq.getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            keyPurposeIdArr[i] = com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId.getInstance(objects.nextElement());
            i++;
        }
        return keyPurposeIdArr;
    }

    public int size() {
        return this.usageTable.size();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.seq;
    }
}
