package android.security.identity;

/* loaded from: classes3.dex */
public class Util {
    private static final java.lang.String TAG = "Util";

    static byte[] stripLeadingZeroes(byte[] bArr) {
        int i = 0;
        int i2 = 0;
        while (i2 < bArr.length && bArr[i2] == 0) {
            i2++;
        }
        byte[] bArr2 = new byte[bArr.length - i2];
        while (i2 < bArr.length) {
            bArr2[i] = bArr[i2];
            i++;
            i2++;
        }
        return bArr2;
    }

    static byte[] publicKeyEncodeUncompressedForm(java.security.PublicKey publicKey) {
        java.security.spec.ECPoint w = ((java.security.interfaces.ECPublicKey) publicKey).getW();
        java.math.BigInteger affineX = w.getAffineX();
        java.math.BigInteger affineY = w.getAffineY();
        if (affineX.compareTo(java.math.BigInteger.ZERO) < 0) {
            throw new java.lang.RuntimeException("X is negative");
        }
        if (affineY.compareTo(java.math.BigInteger.ZERO) < 0) {
            throw new java.lang.RuntimeException("Y is negative");
        }
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            byteArrayOutputStream.write(4);
            byte[] stripLeadingZeroes = stripLeadingZeroes(affineX.toByteArray());
            if (stripLeadingZeroes.length > 32) {
                throw new java.lang.RuntimeException("xBytes is " + stripLeadingZeroes.length + " which is unexpected");
            }
            for (int i = 0; i < 32 - stripLeadingZeroes.length; i++) {
                byteArrayOutputStream.write(0);
            }
            byteArrayOutputStream.write(stripLeadingZeroes);
            byte[] stripLeadingZeroes2 = stripLeadingZeroes(affineY.toByteArray());
            if (stripLeadingZeroes2.length > 32) {
                throw new java.lang.RuntimeException("yBytes is " + stripLeadingZeroes2.length + " which is unexpected");
            }
            for (int i2 = 0; i2 < 32 - stripLeadingZeroes2.length; i2++) {
                byteArrayOutputStream.write(0);
            }
            byteArrayOutputStream.write(stripLeadingZeroes2);
            return byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Unexpected IOException", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0052 A[Catch: InvalidKeyException -> 0x0061, LOOP:0: B:8:0x0040->B:10:0x0052, LOOP_END, TryCatch #1 {InvalidKeyException -> 0x0061, blocks: (B:20:0x0010, B:23:0x0014, B:7:0x002b, B:8:0x0040, B:10:0x0052, B:12:0x005b, B:6:0x001d), top: B:19:0x0010 }] */
    /* JADX WARN: Removed duplicated region for block: B:11:0x005b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static byte[] computeHkdf(java.lang.String str, byte[] bArr, byte[] bArr2, byte[] bArr3, int i) {
        byte[] bArr4;
        byte[] bArr5;
        int i2;
        int i3;
        try {
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance(str);
            if (i > mac.getMacLength() * 255) {
                throw new java.lang.RuntimeException("size too large");
            }
            if (bArr2 != null) {
                try {
                    if (bArr2.length != 0) {
                        mac.init(new javax.crypto.spec.SecretKeySpec(bArr2, str));
                        bArr4 = new byte[i];
                        mac.init(new javax.crypto.spec.SecretKeySpec(mac.doFinal(bArr), str));
                        bArr5 = new byte[0];
                        i2 = 1;
                        i3 = 0;
                        while (true) {
                            mac.update(bArr5);
                            mac.update(bArr3);
                            mac.update((byte) i2);
                            bArr5 = mac.doFinal();
                            if (bArr5.length + i3 >= i) {
                                java.lang.System.arraycopy(bArr5, 0, bArr4, i3, bArr5.length);
                                i3 += bArr5.length;
                                i2++;
                            } else {
                                java.lang.System.arraycopy(bArr5, 0, bArr4, i3, i - i3);
                                return bArr4;
                            }
                        }
                    }
                } catch (java.security.InvalidKeyException e) {
                    throw new java.lang.RuntimeException("Error MACing", e);
                }
            }
            mac.init(new javax.crypto.spec.SecretKeySpec(new byte[mac.getMacLength()], str));
            bArr4 = new byte[i];
            mac.init(new javax.crypto.spec.SecretKeySpec(mac.doFinal(bArr), str));
            bArr5 = new byte[0];
            i2 = 1;
            i3 = 0;
            while (true) {
                mac.update(bArr5);
                mac.update(bArr3);
                mac.update((byte) i2);
                bArr5 = mac.doFinal();
                if (bArr5.length + i3 >= i) {
                }
                java.lang.System.arraycopy(bArr5, 0, bArr4, i3, bArr5.length);
                i3 += bArr5.length;
                i2++;
            }
        } catch (java.security.NoSuchAlgorithmException e2) {
            throw new java.lang.RuntimeException("No such algorithm: " + str, e2);
        }
    }

    private Util() {
    }
}
