package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class DERSequenceParser implements com.android.internal.org.bouncycastle.asn1.ASN1SequenceParser {
    private com.android.internal.org.bouncycastle.asn1.ASN1StreamParser _parser;

    DERSequenceParser(com.android.internal.org.bouncycastle.asn1.ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1SequenceParser
    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable readObject() throws java.io.IOException {
        return this._parser.readObject();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getLoadedObject() throws java.io.IOException {
        return new com.android.internal.org.bouncycastle.asn1.DLSequence(this._parser.readVector());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException(e.getMessage());
        }
    }
}
