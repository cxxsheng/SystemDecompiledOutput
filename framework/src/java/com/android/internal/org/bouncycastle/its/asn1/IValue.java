package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class IValue extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private final java.math.BigInteger value;

    private IValue(com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer) {
        int intValueExact = com.android.internal.org.bouncycastle.util.BigIntegers.intValueExact(aSN1Integer.getValue());
        if (intValueExact < 0 || intValueExact > 65535) {
            throw new java.lang.IllegalArgumentException("value out of range");
        }
        this.value = aSN1Integer.getValue();
    }

    public static com.android.internal.org.bouncycastle.its.asn1.IValue getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.IValue) {
            return (com.android.internal.org.bouncycastle.its.asn1.IValue) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.its.asn1.IValue(com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(obj));
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.value);
    }
}
