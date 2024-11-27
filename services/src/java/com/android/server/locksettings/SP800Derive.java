package com.android.server.locksettings;

/* loaded from: classes2.dex */
class SP800Derive {
    private final byte[] mKeyBytes;

    SP800Derive(byte[] bArr) {
        this.mKeyBytes = bArr;
    }

    private javax.crypto.Mac getMac() {
        try {
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
            mac.init(new javax.crypto.spec.SecretKeySpec(this.mKeyBytes, mac.getAlgorithm()));
            return mac;
        } catch (java.security.InvalidKeyException | java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private static void update32(javax.crypto.Mac mac, int i) {
        mac.update(java.nio.ByteBuffer.allocate(4).putInt(i).array());
    }

    public byte[] fixedInput(byte[] bArr) {
        javax.crypto.Mac mac = getMac();
        update32(mac, 1);
        mac.update(bArr);
        return mac.doFinal();
    }

    public byte[] withContext(byte[] bArr, byte[] bArr2) {
        javax.crypto.Mac mac = getMac();
        update32(mac, 1);
        mac.update(bArr);
        mac.update((byte) 0);
        mac.update(bArr2);
        update32(mac, bArr2.length * 8);
        update32(mac, 256);
        return mac.doFinal();
    }
}
