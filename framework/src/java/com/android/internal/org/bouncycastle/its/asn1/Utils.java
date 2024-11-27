package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
class Utils {
    Utils() {
    }

    static byte[] octetStringFixed(byte[] bArr, int i) {
        if (bArr.length != i) {
            throw new java.lang.IllegalArgumentException("octet string out of range");
        }
        return bArr;
    }

    static byte[] octetStringFixed(byte[] bArr) {
        if (bArr.length < 1 || bArr.length > 32) {
            throw new java.lang.IllegalArgumentException("octet string out of range");
        }
        return com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
    }
}
