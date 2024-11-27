package com.android.internal.security;

/* loaded from: classes5.dex */
public abstract class VerityUtils {
    public static final java.lang.String FSVERITY_SIGNATURE_FILE_EXTENSION = ".fsv_sig";
    private static final int HASH_SIZE_BYTES = 32;
    private static final java.lang.String TAG = "VerityUtils";

    private static native int enableFsverityForFdNative(int i);

    private static native int enableFsverityNative(java.lang.String str);

    private static native int measureFsverityNative(java.lang.String str, byte[] bArr);

    private static native int statxForFsverityNative(java.lang.String str);

    public static boolean isFsVeritySupported() {
        return android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT >= 30 || android.os.SystemProperties.getInt("ro.apk_verity.mode", 0) == 2;
    }

    public static boolean isFsveritySignatureFile(java.io.File file) {
        return file.getName().endsWith(FSVERITY_SIGNATURE_FILE_EXTENSION);
    }

    public static java.lang.String getFsveritySignatureFilePath(java.lang.String str) {
        return str + FSVERITY_SIGNATURE_FILE_EXTENSION;
    }

    public static void setUpFsverity(java.lang.String str) throws java.io.IOException {
        int enableFsverityNative = enableFsverityNative(str);
        if (enableFsverityNative != 0) {
            throw new java.io.IOException("Failed to enable fs-verity on " + str + ": " + android.system.Os.strerror(enableFsverityNative));
        }
    }

    public static void setUpFsverity(int i) throws java.io.IOException {
        int enableFsverityForFdNative = enableFsverityForFdNative(i);
        if (enableFsverityForFdNative != 0) {
            throw new java.io.IOException("Failed to enable fs-verity on FD(" + i + "): " + android.system.Os.strerror(enableFsverityForFdNative));
        }
    }

    public static boolean hasFsverity(java.lang.String str) {
        int statxForFsverityNative = statxForFsverityNative(str);
        if (statxForFsverityNative < 0) {
            android.util.Slog.e(TAG, "Failed to check whether fs-verity is enabled, errno " + (-statxForFsverityNative) + ": " + str);
            return false;
        }
        if (statxForFsverityNative != 1) {
            return false;
        }
        return true;
    }

    public static boolean verifyPkcs7DetachedSignature(byte[] bArr, byte[] bArr2, java.io.InputStream inputStream) {
        if (bArr2.length != 32) {
            android.util.Slog.w(TAG, "Only sha256 is currently supported");
            return false;
        }
        try {
            com.android.internal.org.bouncycastle.cms.CMSSignedData cMSSignedData = new com.android.internal.org.bouncycastle.cms.CMSSignedData(new com.android.internal.org.bouncycastle.cms.CMSProcessableByteArray(toFormattedDigest(bArr2)), bArr);
            if (!cMSSignedData.isDetachedSignature()) {
                android.util.Slog.w(TAG, "Expect only detached siganture");
                return false;
            }
            if (!cMSSignedData.getCertificates().getMatches(null).isEmpty()) {
                android.util.Slog.w(TAG, "Expect no certificate in signature");
                return false;
            }
            if (!cMSSignedData.getCRLs().getMatches(null).isEmpty()) {
                android.util.Slog.w(TAG, "Expect no CRL in signature");
                return false;
            }
            com.android.internal.org.bouncycastle.cms.SignerInformationVerifier build = new com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder().build((java.security.cert.X509Certificate) java.security.cert.CertificateFactory.getInstance("X.509").generateCertificate(inputStream));
            for (com.android.internal.org.bouncycastle.cms.SignerInformation signerInformation : cMSSignedData.getSignerInfos().getSigners()) {
                if (signerInformation.getSignedAttributes() != null && signerInformation.getSignedAttributes().size() > 0) {
                    android.util.Slog.w(TAG, "Unexpected signed attributes");
                    return false;
                }
                if (signerInformation.getUnsignedAttributes() != null && signerInformation.getUnsignedAttributes().size() > 0) {
                    android.util.Slog.w(TAG, "Unexpected unsigned attributes");
                    return false;
                }
                if (!com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256.getId().equals(signerInformation.getDigestAlgOID())) {
                    android.util.Slog.w(TAG, "Unsupported digest algorithm OID: " + signerInformation.getDigestAlgOID());
                    return false;
                }
                if (!com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption.getId().equals(signerInformation.getEncryptionAlgOID())) {
                    android.util.Slog.w(TAG, "Unsupported encryption algorithm OID: " + signerInformation.getEncryptionAlgOID());
                    return false;
                }
                if (signerInformation.verify(build)) {
                    return true;
                }
            }
            return false;
        } catch (com.android.internal.org.bouncycastle.cms.CMSException | com.android.internal.org.bouncycastle.operator.OperatorCreationException | java.security.cert.CertificateException e) {
            android.util.Slog.w(TAG, "Error occurred during the PKCS#7 signature verification", e);
            return false;
        }
    }

    public static byte[] getFsverityDigest(java.lang.String str) {
        byte[] bArr = new byte[32];
        int measureFsverityNative = measureFsverityNative(str, bArr);
        if (measureFsverityNative < 0) {
            if (measureFsverityNative != (-android.system.OsConstants.ENODATA)) {
                android.util.Slog.e(TAG, "Failed to measure fs-verity, errno " + (-measureFsverityNative) + ": " + str);
                return null;
            }
            return null;
        }
        return bArr;
    }

    public static byte[] generateFsVerityDigest(long j, android.os.incremental.V4Signature.HashingInfo hashingInfo) throws java.security.DigestException, java.security.NoSuchAlgorithmException {
        if (hashingInfo.rawRootHash == null || hashingInfo.rawRootHash.length != 32) {
            throw new java.lang.IllegalArgumentException("Expect a 32-byte rootHash for SHA256");
        }
        if (hashingInfo.log2BlockSize != 12) {
            throw new java.lang.IllegalArgumentException("Unsupported log2BlockSize: " + ((int) hashingInfo.log2BlockSize));
        }
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(256);
        allocate.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        allocate.put((byte) 1);
        allocate.put((byte) 1);
        allocate.put(hashingInfo.log2BlockSize);
        allocate.put((byte) 0);
        allocate.putInt(0);
        allocate.putLong(j);
        allocate.put(hashingInfo.rawRootHash);
        return java.security.MessageDigest.getInstance("SHA-256").digest(allocate.array());
    }

    public static byte[] toFormattedDigest(byte[] bArr) {
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(bArr.length + 12);
        allocate.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        allocate.put("FSVerity".getBytes(java.nio.charset.StandardCharsets.US_ASCII));
        allocate.putShort((short) 1);
        allocate.putShort((short) bArr.length);
        allocate.put(bArr);
        return allocate.array();
    }
}
