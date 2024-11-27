package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class DistributionPointName extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.ASN1Choice {
    public static final int FULL_NAME = 0;
    public static final int NAME_RELATIVE_TO_CRL_ISSUER = 1;
    com.android.internal.org.bouncycastle.asn1.ASN1Encodable name;
    int type;

    public static com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1TaggedObject, true));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName)) {
            return (com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            return new com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) obj);
        }
        throw new java.lang.IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public DistributionPointName(int i, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.type = i;
        this.name = aSN1Encodable;
    }

    public DistributionPointName(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames) {
        this(0, generalNames);
    }

    public int getType() {
        return this.type;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getName() {
        return this.name;
    }

    public DistributionPointName(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject) {
        this.type = aSN1TaggedObject.getTagNo();
        if (this.type == 0) {
            this.name = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(aSN1TaggedObject, false);
        } else {
            this.name = com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(aSN1TaggedObject, false);
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, this.type, this.name);
    }

    public java.lang.String toString() {
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("DistributionPointName: [");
        stringBuffer.append(lineSeparator);
        if (this.type == 0) {
            appendObject(stringBuffer, lineSeparator, "fullName", this.name.toString());
        } else {
            appendObject(stringBuffer, lineSeparator, "nameRelativeToCRLIssuer", this.name.toString());
        }
        stringBuffer.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        stringBuffer.append(lineSeparator);
        return stringBuffer.toString();
    }

    private void appendObject(java.lang.StringBuffer stringBuffer, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        stringBuffer.append("    ");
        stringBuffer.append(str2);
        stringBuffer.append(":");
        stringBuffer.append(str);
        stringBuffer.append("    ");
        stringBuffer.append("    ");
        stringBuffer.append(str3);
        stringBuffer.append(str);
    }
}
