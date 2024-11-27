package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class DERUTCTime extends com.android.internal.org.bouncycastle.asn1.ASN1UTCTime {
    DERUTCTime(byte[] bArr) {
        super(bArr);
    }

    public DERUTCTime(java.util.Date date) {
        super(date);
    }

    public DERUTCTime(java.lang.String str) {
        super(str);
    }
}
