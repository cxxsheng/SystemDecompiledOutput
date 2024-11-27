package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class LinkageValue extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private final byte[] value;

    private LinkageValue(com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this.value = com.android.internal.org.bouncycastle.util.Arrays.clone(com.android.internal.org.bouncycastle.its.asn1.Utils.octetStringFixed(aSN1OctetString.getOctets(), 9));
    }

    public static com.android.internal.org.bouncycastle.its.asn1.LinkageValue getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.LinkageValue) {
            return (com.android.internal.org.bouncycastle.its.asn1.LinkageValue) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.its.asn1.LinkageValue(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(obj));
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.DEROctetString(com.android.internal.org.bouncycastle.util.Arrays.clone(this.value));
    }
}
