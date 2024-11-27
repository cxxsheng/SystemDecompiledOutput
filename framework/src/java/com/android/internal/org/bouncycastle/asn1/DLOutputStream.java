package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
class DLOutputStream extends com.android.internal.org.bouncycastle.asn1.ASN1OutputStream {
    DLOutputStream(java.io.OutputStream outputStream) {
        super(outputStream);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OutputStream
    void writePrimitive(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive, boolean z) throws java.io.IOException {
        aSN1Primitive.toDLObject().encode(this, z);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OutputStream
    com.android.internal.org.bouncycastle.asn1.ASN1OutputStream getDLSubStream() {
        return this;
    }
}
