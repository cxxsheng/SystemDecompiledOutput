package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class DEROctetStringParser implements com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser {
    private com.android.internal.org.bouncycastle.asn1.DefiniteLengthInputStream stream;

    DEROctetStringParser(com.android.internal.org.bouncycastle.asn1.DefiniteLengthInputStream definiteLengthInputStream) {
        this.stream = definiteLengthInputStream;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser
    public java.io.InputStream getOctetStream() {
        return this.stream;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getLoadedObject() throws java.io.IOException {
        return new com.android.internal.org.bouncycastle.asn1.DEROctetString(this.stream.toByteArray());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.asn1.ASN1ParsingException("IOException converting stream to byte array: " + e.getMessage(), e);
        }
    }
}
