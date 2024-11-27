package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public abstract class ASN1Object implements com.android.internal.org.bouncycastle.asn1.ASN1Encodable, com.android.internal.org.bouncycastle.util.Encodable {
    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public abstract com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive();

    public void encodeTo(java.io.OutputStream outputStream) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1OutputStream.create(outputStream).writeObject(this);
    }

    public void encodeTo(java.io.OutputStream outputStream, java.lang.String str) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1OutputStream.create(outputStream, str).writeObject(this);
    }

    @Override // com.android.internal.org.bouncycastle.util.Encodable
    public byte[] getEncoded() throws java.io.IOException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        encodeTo(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getEncoded(java.lang.String str) throws java.io.IOException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        encodeTo(byteArrayOutputStream, str);
        return byteArrayOutputStream.toByteArray();
    }

    public int hashCode() {
        return toASN1Primitive().hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Encodable)) {
            return false;
        }
        return toASN1Primitive().equals(((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) obj).toASN1Primitive());
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Object() {
        return toASN1Primitive();
    }

    protected static boolean hasEncodedTagValue(java.lang.Object obj, int i) {
        return (obj instanceof byte[]) && ((byte[]) obj)[0] == i;
    }
}
