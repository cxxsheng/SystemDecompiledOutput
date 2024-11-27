package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class GeneralNames extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private final com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] names;

    private static com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] copy(com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] generalNameArr) {
        com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] generalNameArr2 = new com.android.internal.org.bouncycastle.asn1.x509.GeneralName[generalNameArr.length];
        java.lang.System.arraycopy(generalNameArr, 0, generalNameArr2, 0, generalNameArr.length);
        return generalNameArr2;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.GeneralNames getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.GeneralNames) {
            return (com.android.internal.org.bouncycastle.asn1.x509.GeneralNames) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.GeneralNames(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.GeneralNames getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return new com.android.internal.org.bouncycastle.asn1.x509.GeneralNames(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.GeneralNames fromExtensions(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.x509.Extensions.getExtensionParsedValue(extensions, aSN1ObjectIdentifier));
    }

    public GeneralNames(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName) {
        this.names = new com.android.internal.org.bouncycastle.asn1.x509.GeneralName[]{generalName};
    }

    public GeneralNames(com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] generalNameArr) {
        this.names = copy(generalNameArr);
    }

    private GeneralNames(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.names = new com.android.internal.org.bouncycastle.asn1.x509.GeneralName[aSN1Sequence.size()];
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            this.names[i] = com.android.internal.org.bouncycastle.asn1.x509.GeneralName.getInstance(aSN1Sequence.getObjectAt(i));
        }
    }

    public com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] getNames() {
        return copy(this.names);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(this.names);
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        stringBuffer.append("GeneralNames:");
        stringBuffer.append(lineSeparator);
        for (int i = 0; i != this.names.length; i++) {
            stringBuffer.append("    ");
            stringBuffer.append(this.names[i]);
            stringBuffer.append(lineSeparator);
        }
        return stringBuffer.toString();
    }
}
