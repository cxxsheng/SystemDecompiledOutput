package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class BERApplicationSpecificParser implements com.android.internal.org.bouncycastle.asn1.ASN1ApplicationSpecificParser {
    private final com.android.internal.org.bouncycastle.asn1.ASN1StreamParser parser;
    private final int tag;

    BERApplicationSpecificParser(int i, com.android.internal.org.bouncycastle.asn1.ASN1StreamParser aSN1StreamParser) {
        this.tag = i;
        this.parser = aSN1StreamParser;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1ApplicationSpecificParser
    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable readObject() throws java.io.IOException {
        return this.parser.readObject();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getLoadedObject() throws java.io.IOException {
        return new com.android.internal.org.bouncycastle.asn1.BERApplicationSpecific(this.tag, this.parser.readVector());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.asn1.ASN1ParsingException(e.getMessage(), e);
        }
    }
}
