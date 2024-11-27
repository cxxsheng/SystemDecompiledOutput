package com.android.internal.org.bouncycastle.x509.extension;

/* loaded from: classes4.dex */
public class X509ExtensionUtil {
    public static com.android.internal.org.bouncycastle.asn1.ASN1Primitive fromExtensionValue(byte[] bArr) throws java.io.IOException {
        return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bArr)).getOctets());
    }

    public static java.util.Collection getIssuerAlternativeNames(java.security.cert.X509Certificate x509Certificate) throws java.security.cert.CertificateParsingException {
        return getAlternativeNames(x509Certificate.getExtensionValue(com.android.internal.org.bouncycastle.asn1.x509.Extension.issuerAlternativeName.getId()));
    }

    public static java.util.Collection getSubjectAlternativeNames(java.security.cert.X509Certificate x509Certificate) throws java.security.cert.CertificateParsingException {
        return getAlternativeNames(x509Certificate.getExtensionValue(com.android.internal.org.bouncycastle.asn1.x509.Extension.subjectAlternativeName.getId()));
    }

    private static java.util.Collection getAlternativeNames(byte[] bArr) throws java.security.cert.CertificateParsingException {
        if (bArr == null) {
            return java.util.Collections.EMPTY_LIST;
        }
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Enumeration objects = com.android.internal.org.bouncycastle.asn1.DERSequence.getInstance(fromExtensionValue(bArr)).getObjects();
            while (objects.hasMoreElements()) {
                com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName = com.android.internal.org.bouncycastle.asn1.x509.GeneralName.getInstance(objects.nextElement());
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                arrayList2.add(com.android.internal.org.bouncycastle.util.Integers.valueOf(generalName.getTagNo()));
                switch (generalName.getTagNo()) {
                    case 0:
                    case 3:
                    case 5:
                        arrayList2.add(generalName.getName().toASN1Primitive());
                        break;
                    case 1:
                    case 2:
                    case 6:
                        arrayList2.add(((com.android.internal.org.bouncycastle.asn1.ASN1String) generalName.getName()).getString());
                        break;
                    case 4:
                        arrayList2.add(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(generalName.getName()).toString());
                        break;
                    case 7:
                        arrayList2.add(com.android.internal.org.bouncycastle.asn1.DEROctetString.getInstance(generalName.getName()).getOctets());
                        break;
                    case 8:
                        arrayList2.add(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(generalName.getName()).getId());
                        break;
                    default:
                        throw new java.io.IOException("Bad tag number: " + generalName.getTagNo());
                }
                arrayList.add(arrayList2);
            }
            return java.util.Collections.unmodifiableCollection(arrayList);
        } catch (java.lang.Exception e) {
            throw new java.security.cert.CertificateParsingException(e.getMessage());
        }
    }
}
