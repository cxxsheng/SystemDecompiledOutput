package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
class LazyEncodedSequence extends com.android.internal.org.bouncycastle.asn1.ASN1Sequence {
    private byte[] encoded;

    LazyEncodedSequence(byte[] bArr) throws java.io.IOException {
        this.encoded = bArr;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    public synchronized com.android.internal.org.bouncycastle.asn1.ASN1Encodable getObjectAt(int i) {
        force();
        return super.getObjectAt(i);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    public synchronized java.util.Enumeration getObjects() {
        if (this.encoded != null) {
            return new com.android.internal.org.bouncycastle.asn1.LazyConstructionEnumeration(this.encoded);
        }
        return super.getObjects();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public synchronized int hashCode() {
        force();
        return super.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.util.Iterable, java.lang.Iterable
    public synchronized java.util.Iterator<com.android.internal.org.bouncycastle.asn1.ASN1Encodable> iterator() {
        force();
        return super.iterator();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    public synchronized int size() {
        force();
        return super.size();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    public synchronized com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] toArray() {
        force();
        return super.toArray();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence
    com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] toArrayInternal() {
        force();
        return super.toArrayInternal();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    synchronized int encodedLength() throws java.io.IOException {
        if (this.encoded != null) {
            return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateBodyLength(this.encoded.length) + 1 + this.encoded.length;
        }
        return super.toDLObject().encodedLength();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    synchronized void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        if (this.encoded != null) {
            aSN1OutputStream.writeEncoded(z, 48, this.encoded);
        } else {
            super.toDLObject().encode(aSN1OutputStream, z);
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    synchronized com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        force();
        return super.toDERObject();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    synchronized com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        force();
        return super.toDLObject();
    }

    private void force() {
        if (this.encoded != null) {
            com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
            com.android.internal.org.bouncycastle.asn1.LazyConstructionEnumeration lazyConstructionEnumeration = new com.android.internal.org.bouncycastle.asn1.LazyConstructionEnumeration(this.encoded);
            while (lazyConstructionEnumeration.hasMoreElements()) {
                aSN1EncodableVector.add((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) lazyConstructionEnumeration.nextElement());
            }
            this.elements = aSN1EncodableVector.takeElements();
            this.encoded = null;
        }
    }
}
