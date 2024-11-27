package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class BERTaggedObjectParser implements com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser {
    private boolean _constructed;
    private com.android.internal.org.bouncycastle.asn1.ASN1StreamParser _parser;
    private int _tagNumber;

    BERTaggedObjectParser(boolean z, int i, com.android.internal.org.bouncycastle.asn1.ASN1StreamParser aSN1StreamParser) {
        this._constructed = z;
        this._tagNumber = i;
        this._parser = aSN1StreamParser;
    }

    public boolean isConstructed() {
        return this._constructed;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagNo() {
        return this._tagNumber;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getObjectParser(int i, boolean z) throws java.io.IOException {
        if (z) {
            if (!this._constructed) {
                throw new java.io.IOException("Explicit tags must be constructed (see X.690 8.14.2)");
            }
            return this._parser.readObject();
        }
        return this._parser.readImplicit(this._constructed, i);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getLoadedObject() throws java.io.IOException {
        return this._parser.readTaggedObject(this._constructed, this._tagNumber);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.asn1.ASN1ParsingException(e.getMessage());
        }
    }
}
