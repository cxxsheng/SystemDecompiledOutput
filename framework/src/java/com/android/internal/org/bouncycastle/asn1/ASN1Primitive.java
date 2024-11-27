package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public abstract class ASN1Primitive extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    abstract boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive);

    abstract void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException;

    abstract int encodedLength() throws java.io.IOException;

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public abstract int hashCode();

    abstract boolean isConstructed();

    ASN1Primitive() {
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public void encodeTo(java.io.OutputStream outputStream) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1OutputStream.create(outputStream).writeObject(this);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public void encodeTo(java.io.OutputStream outputStream, java.lang.String str) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1OutputStream.create(outputStream, str).writeObject(this);
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Primitive fromByteArray(byte[] bArr) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1InputStream aSN1InputStream = new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(bArr);
        try {
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive readObject = aSN1InputStream.readObject();
            if (aSN1InputStream.available() != 0) {
                throw new java.io.IOException("Extra data detected in stream");
            }
            return readObject;
        } catch (java.lang.ClassCastException e) {
            throw new java.io.IOException("cannot recognise object in stream");
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Encodable) && asn1Equals(((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) obj).toASN1Primitive());
    }

    public final boolean equals(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        return this == aSN1Encodable || (aSN1Encodable != null && asn1Equals(aSN1Encodable.toASN1Primitive()));
    }

    public final boolean equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        return this == aSN1Primitive || asn1Equals(aSN1Primitive);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public final com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this;
    }

    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        return this;
    }

    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return this;
    }
}
