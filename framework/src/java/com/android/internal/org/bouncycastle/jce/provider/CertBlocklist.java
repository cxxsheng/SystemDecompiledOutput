package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class CertBlocklist {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(com.android.internal.org.bouncycastle.jce.provider.CertBlocklist.class.getName());
    public final java.util.Set<byte[]> pubkeyBlocklist;
    public final java.util.Set<java.math.BigInteger> serialBlocklist;

    public CertBlocklist() {
        java.lang.String str = java.lang.System.getenv("ANDROID_DATA") + "/misc/keychain/";
        java.lang.String str2 = str + "pubkey_blacklist.txt";
        this.pubkeyBlocklist = readPublicKeyBlockList(str2);
        this.serialBlocklist = readSerialBlockList(str + "serial_blacklist.txt");
    }

    public CertBlocklist(java.lang.String str, java.lang.String str2) {
        this.pubkeyBlocklist = readPublicKeyBlockList(str);
        this.serialBlocklist = readSerialBlockList(str2);
    }

    private static boolean isHex(java.lang.String str) {
        try {
            new java.math.BigInteger(str, 16);
            return true;
        } catch (java.lang.NumberFormatException e) {
            logger.log(java.util.logging.Level.WARNING, "Could not parse hex value " + str, (java.lang.Throwable) e);
            return false;
        }
    }

    private static boolean isPubkeyHash(java.lang.String str) {
        if (str.length() != 40) {
            logger.log(java.util.logging.Level.WARNING, "Invalid pubkey hash length: " + str.length());
            return false;
        }
        return isHex(str);
    }

    private static java.lang.String readBlocklist(java.lang.String str) {
        try {
            return readFileAsString(str);
        } catch (java.io.FileNotFoundException e) {
            return "";
        } catch (java.io.IOException e2) {
            logger.log(java.util.logging.Level.WARNING, "Could not read blocklist", (java.lang.Throwable) e2);
            return "";
        }
    }

    private static java.lang.String readFileAsString(java.lang.String str) throws java.io.IOException {
        return readFileAsBytes(str).toString(android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
    }

    private static java.io.ByteArrayOutputStream readFileAsBytes(java.lang.String str) throws java.io.IOException {
        java.io.RandomAccessFile randomAccessFile;
        java.io.RandomAccessFile randomAccessFile2 = null;
        try {
            randomAccessFile = new java.io.RandomAccessFile(str, "r");
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream((int) randomAccessFile.length());
            byte[] bArr = new byte[8192];
            while (true) {
                int read = randomAccessFile.read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    closeQuietly(randomAccessFile);
                    return byteArrayOutputStream;
                }
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            randomAccessFile2 = randomAccessFile;
            closeQuietly(randomAccessFile2);
            throw th;
        }
    }

    private static void closeQuietly(java.io.Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (java.lang.RuntimeException e) {
                throw e;
            } catch (java.lang.Exception e2) {
            }
        }
    }

    private static java.util.Set<java.math.BigInteger> readSerialBlockList(java.lang.String str) {
        java.util.HashSet hashSet = new java.util.HashSet(java.util.Arrays.asList(new java.math.BigInteger("077a59bcd53459601ca6907267a6dd1c", 16), new java.math.BigInteger("047ecbe9fca55f7bd09eae36e10cae1e", 16), new java.math.BigInteger("d8f35f4eb7872b2dab0692e315382fb0", 16), new java.math.BigInteger("b0b7133ed096f9b56fae91c874bd3ac0", 16), new java.math.BigInteger("9239d5348f40d1695a745470e1f23f43", 16), new java.math.BigInteger("e9028b9578e415dc1a710a2b88154447", 16), new java.math.BigInteger("d7558fdaf5f1105bb213282b707729a3", 16), new java.math.BigInteger("f5c86af36162f13a64f54f6dc9587c06", 16), new java.math.BigInteger("392a434f0e07df1f8aa305de34e0c229", 16), new java.math.BigInteger("3e75ced46b693021218830ae86a82a71", 16)));
        java.lang.String readBlocklist = readBlocklist(str);
        if (!readBlocklist.equals("")) {
            for (java.lang.String str2 : readBlocklist.split(",")) {
                try {
                    hashSet.add(new java.math.BigInteger(str2, 16));
                } catch (java.lang.NumberFormatException e) {
                    logger.log(java.util.logging.Level.WARNING, "Tried to blocklist invalid serial number " + str2, (java.lang.Throwable) e);
                }
            }
        }
        return java.util.Collections.unmodifiableSet(hashSet);
    }

    private static java.util.Set<byte[]> readPublicKeyBlockList(java.lang.String str) {
        java.util.HashSet hashSet = new java.util.HashSet(java.util.Arrays.asList("410f36363258f30b347d12ce4863e433437806a8".getBytes(), "ba3e7bd38cd7e1e6b9cd4c219962e59d7a2f4e37".getBytes(), "e23b8d105f87710a68d9248050ebefc627be4ca6".getBytes(), "7b2e16bc39bcd72b456e9f055d1de615b74945db".getBytes(), "e8f91200c65cee16e039b9f883841661635f81c5".getBytes(), "0129bcd5b448ae8d2496d1c3e19723919088e152".getBytes(), "5f3ab33d55007054bc5e3e5553cd8d8465d77c61".getBytes(), "783333c9687df63377efceddd82efa9101913e8e".getBytes(), "3ecf4bbbe46096d514bb539bb913d77aa4ef31bf".getBytes()));
        java.lang.String readBlocklist = readBlocklist(str);
        if (!readBlocklist.equals("")) {
            for (java.lang.String str2 : readBlocklist.split(",")) {
                java.lang.String trim = str2.trim();
                if (isPubkeyHash(trim)) {
                    hashSet.add(trim.getBytes());
                } else {
                    logger.log(java.util.logging.Level.WARNING, "Tried to blocklist invalid pubkey " + trim);
                }
            }
        }
        return hashSet;
    }

    public boolean isPublicKeyBlockListed(java.security.PublicKey publicKey) {
        byte[] encoded = publicKey.getEncoded();
        com.android.internal.org.bouncycastle.crypto.Digest sha1 = com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1();
        sha1.update(encoded, 0, encoded.length);
        byte[] bArr = new byte[sha1.getDigestSize()];
        sha1.doFinal(bArr, 0);
        java.util.Iterator<byte[]> it = this.pubkeyBlocklist.iterator();
        while (it.hasNext()) {
            if (java.util.Arrays.equals(it.next(), com.android.internal.org.bouncycastle.util.encoders.Hex.encode(bArr))) {
                return true;
            }
        }
        return false;
    }

    public boolean isSerialNumberBlockListed(java.math.BigInteger bigInteger) {
        return this.serialBlocklist.contains(bigInteger);
    }
}
