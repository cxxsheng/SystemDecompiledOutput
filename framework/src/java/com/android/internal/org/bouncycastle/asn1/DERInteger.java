package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class DERInteger extends com.android.internal.org.bouncycastle.asn1.ASN1Integer {
    public DERInteger(byte[] bArr) {
        super(bArr, true);
    }

    public DERInteger(java.math.BigInteger bigInteger) {
        super(bigInteger);
    }

    public DERInteger(long j) {
        super(j);
    }
}
