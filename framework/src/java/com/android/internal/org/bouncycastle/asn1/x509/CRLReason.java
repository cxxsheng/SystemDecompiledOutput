package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class CRLReason extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    public static final int AA_COMPROMISE = 10;
    public static final int AFFILIATION_CHANGED = 3;
    public static final int CA_COMPROMISE = 2;
    public static final int CERTIFICATE_HOLD = 6;
    public static final int CESSATION_OF_OPERATION = 5;
    public static final int KEY_COMPROMISE = 1;
    public static final int PRIVILEGE_WITHDRAWN = 9;
    public static final int REMOVE_FROM_CRL = 8;
    public static final int SUPERSEDED = 4;
    public static final int UNSPECIFIED = 0;
    public static final int aACompromise = 10;
    public static final int affiliationChanged = 3;
    public static final int cACompromise = 2;
    public static final int certificateHold = 6;
    public static final int cessationOfOperation = 5;
    public static final int keyCompromise = 1;
    public static final int privilegeWithdrawn = 9;
    public static final int removeFromCRL = 8;
    public static final int superseded = 4;
    public static final int unspecified = 0;
    private com.android.internal.org.bouncycastle.asn1.ASN1Enumerated value;
    private static final java.lang.String[] reasonString = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};
    private static final java.util.Hashtable table = new java.util.Hashtable();

    public static com.android.internal.org.bouncycastle.asn1.x509.CRLReason getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.CRLReason) {
            return (com.android.internal.org.bouncycastle.asn1.x509.CRLReason) obj;
        }
        if (obj != null) {
            return lookup(com.android.internal.org.bouncycastle.asn1.ASN1Enumerated.getInstance(obj).intValueExact());
        }
        return null;
    }

    private CRLReason(int i) {
        this.value = new com.android.internal.org.bouncycastle.asn1.ASN1Enumerated(i);
    }

    public java.lang.String toString() {
        java.lang.String str;
        int intValue = getValue().intValue();
        if (intValue < 0 || intValue > 10) {
            str = "invalid";
        } else {
            str = reasonString[intValue];
        }
        return "CRLReason: " + str;
    }

    public java.math.BigInteger getValue() {
        return this.value.getValue();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.value;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.CRLReason lookup(int i) {
        java.lang.Integer valueOf = com.android.internal.org.bouncycastle.util.Integers.valueOf(i);
        if (!table.containsKey(valueOf)) {
            table.put(valueOf, new com.android.internal.org.bouncycastle.asn1.x509.CRLReason(i));
        }
        return (com.android.internal.org.bouncycastle.asn1.x509.CRLReason) table.get(valueOf);
    }
}
