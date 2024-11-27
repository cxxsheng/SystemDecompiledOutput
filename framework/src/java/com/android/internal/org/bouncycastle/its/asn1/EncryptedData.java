package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class EncryptedData {
    private EncryptedData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
    }

    public static com.android.internal.org.bouncycastle.its.asn1.EncryptedData getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.EncryptedData) {
            return (com.android.internal.org.bouncycastle.its.asn1.EncryptedData) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.its.asn1.EncryptedData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }
}
