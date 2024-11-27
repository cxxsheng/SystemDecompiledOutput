package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
class StreamUtil {
    StreamUtil() {
    }

    static int findLimit(java.io.InputStream inputStream) {
        if (inputStream instanceof com.android.internal.org.bouncycastle.asn1.LimitedInputStream) {
            return ((com.android.internal.org.bouncycastle.asn1.LimitedInputStream) inputStream).getLimit();
        }
        if (inputStream instanceof com.android.internal.org.bouncycastle.asn1.ASN1InputStream) {
            return ((com.android.internal.org.bouncycastle.asn1.ASN1InputStream) inputStream).getLimit();
        }
        if (inputStream instanceof java.io.ByteArrayInputStream) {
            return ((java.io.ByteArrayInputStream) inputStream).available();
        }
        if (inputStream instanceof java.io.FileInputStream) {
            try {
                java.nio.channels.FileChannel channel = ((java.io.FileInputStream) inputStream).getChannel();
                long size = channel != null ? channel.size() : 2147483647L;
                if (size < 2147483647L) {
                    return (int) size;
                }
            } catch (java.io.IOException e) {
            }
        }
        long maxMemory = java.lang.Runtime.getRuntime().maxMemory();
        if (maxMemory > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) maxMemory;
    }

    static int calculateBodyLength(int i) {
        int i2 = 1;
        if (i > 127) {
            int i3 = 1;
            while (true) {
                i >>>= 8;
                if (i == 0) {
                    break;
                }
                i3++;
            }
            for (int i4 = (i3 - 1) * 8; i4 >= 0; i4 -= 8) {
                i2++;
            }
        }
        return i2;
    }

    static int calculateTagLength(int i) throws java.io.IOException {
        if (i < 31) {
            return 1;
        }
        if (i < 128) {
            return 2;
        }
        byte[] bArr = new byte[5];
        int i2 = 4;
        bArr[4] = (byte) (i & 127);
        do {
            i >>= 7;
            i2--;
            bArr[i2] = (byte) ((i & 127) | 128);
        } while (i > 127);
        return 1 + (5 - i2);
    }
}
