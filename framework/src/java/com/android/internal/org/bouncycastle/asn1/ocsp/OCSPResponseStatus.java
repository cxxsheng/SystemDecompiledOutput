package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class OCSPResponseStatus extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    public static final int INTERNAL_ERROR = 2;
    public static final int MALFORMED_REQUEST = 1;
    public static final int SIG_REQUIRED = 5;
    public static final int SUCCESSFUL = 0;
    public static final int TRY_LATER = 3;
    public static final int UNAUTHORIZED = 6;
    private com.android.internal.org.bouncycastle.asn1.ASN1Enumerated value;

    public OCSPResponseStatus(int i) {
        this(new com.android.internal.org.bouncycastle.asn1.ASN1Enumerated(i));
    }

    private OCSPResponseStatus(com.android.internal.org.bouncycastle.asn1.ASN1Enumerated aSN1Enumerated) {
        this.value = aSN1Enumerated;
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponseStatus getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponseStatus) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponseStatus) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponseStatus(com.android.internal.org.bouncycastle.asn1.ASN1Enumerated.getInstance(obj));
        }
        return null;
    }

    public int getIntValue() {
        return this.value.intValueExact();
    }

    public java.math.BigInteger getValue() {
        return this.value.getValue();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.value;
    }
}
