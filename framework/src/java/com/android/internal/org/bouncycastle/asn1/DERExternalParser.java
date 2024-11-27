package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class DERExternalParser implements com.android.internal.org.bouncycastle.asn1.ASN1Encodable, com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable {
    private com.android.internal.org.bouncycastle.asn1.ASN1StreamParser _parser;

    public DERExternalParser(com.android.internal.org.bouncycastle.asn1.ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable readObject() throws java.io.IOException {
        return this._parser.readObject();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getLoadedObject() throws java.io.IOException {
        try {
            return new com.android.internal.org.bouncycastle.asn1.DLExternal(this._parser.readVector());
        } catch (java.lang.IllegalArgumentException e) {
            throw new com.android.internal.org.bouncycastle.asn1.ASN1Exception(e.getMessage(), e);
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.asn1.ASN1ParsingException("unable to get DER object", e);
        } catch (java.lang.IllegalArgumentException e2) {
            throw new com.android.internal.org.bouncycastle.asn1.ASN1ParsingException("unable to get DER object", e2);
        }
    }
}
