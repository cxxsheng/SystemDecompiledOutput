package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class EndEntityType extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    public static final int app = 128;
    public static final int enrol = 64;
    private final com.android.internal.org.bouncycastle.asn1.ASN1BitString type;

    public EndEntityType(int i) {
        if (i != 128 && i != 64) {
            throw new java.lang.IllegalArgumentException("value out of range");
        }
        this.type = new com.android.internal.org.bouncycastle.asn1.DERBitString(i);
    }

    private EndEntityType(com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString) {
        this.type = dERBitString;
    }

    public static com.android.internal.org.bouncycastle.its.asn1.EndEntityType getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.EndEntityType) {
            return (com.android.internal.org.bouncycastle.its.asn1.EndEntityType) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.its.asn1.EndEntityType(com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(obj));
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.type;
    }
}
