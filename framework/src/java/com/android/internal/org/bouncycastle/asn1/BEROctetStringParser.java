package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class BEROctetStringParser implements com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser {
    private com.android.internal.org.bouncycastle.asn1.ASN1StreamParser _parser;

    BEROctetStringParser(com.android.internal.org.bouncycastle.asn1.ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser
    public java.io.InputStream getOctetStream() {
        return new com.android.internal.org.bouncycastle.asn1.ConstructedOctetStream(this._parser);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getLoadedObject() throws java.io.IOException {
        return new com.android.internal.org.bouncycastle.asn1.BEROctetString(com.android.internal.org.bouncycastle.util.io.Streams.readAll(getOctetStream()));
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
