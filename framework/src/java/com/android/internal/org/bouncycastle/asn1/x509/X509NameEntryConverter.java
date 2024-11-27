package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public abstract class X509NameEntryConverter {
    public abstract com.android.internal.org.bouncycastle.asn1.ASN1Primitive getConvertedValue(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str);

    protected com.android.internal.org.bouncycastle.asn1.ASN1Primitive convertHexEncoded(java.lang.String str, int i) throws java.io.IOException {
        return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict(str, i, str.length() - i));
    }

    protected boolean canBePrintable(java.lang.String str) {
        return com.android.internal.org.bouncycastle.asn1.DERPrintableString.isPrintableString(str);
    }
}
