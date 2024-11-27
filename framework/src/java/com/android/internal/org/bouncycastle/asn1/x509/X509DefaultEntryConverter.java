package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class X509DefaultEntryConverter extends com.android.internal.org.bouncycastle.asn1.x509.X509NameEntryConverter {
    @Override // com.android.internal.org.bouncycastle.asn1.x509.X509NameEntryConverter
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getConvertedValue(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
        if (str.length() != 0 && str.charAt(0) == '#') {
            try {
                return convertHexEncoded(str, 1);
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException("can't recode value for oid " + aSN1ObjectIdentifier.getId());
            }
        }
        if (str.length() != 0 && str.charAt(0) == '\\') {
            str = str.substring(1);
        }
        if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.X509Name.EmailAddress) || aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.X509Name.DC)) {
            return new com.android.internal.org.bouncycastle.asn1.DERIA5String(str);
        }
        if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.X509Name.DATE_OF_BIRTH)) {
            return new com.android.internal.org.bouncycastle.asn1.DERGeneralizedTime(str);
        }
        if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.X509Name.C) || aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.X509Name.SN) || aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.X509Name.DN_QUALIFIER) || aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.X509Name.TELEPHONE_NUMBER)) {
            return new com.android.internal.org.bouncycastle.asn1.DERPrintableString(str);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERUTF8String(str);
    }
}
