package android.util.apk;

/* loaded from: classes3.dex */
public class ApkSignatureSchemeV2Verifier {
    private static final int APK_SIGNATURE_SCHEME_V2_BLOCK_ID = 1896449818;
    private static final int MAX_V2_SIGNERS = 10;
    public static final int SF_ATTRIBUTE_ANDROID_APK_SIGNED_ID = 2;
    private static final int STRIPPING_PROTECTION_ATTR_ID = -1091571699;

    public static boolean hasSignature(java.lang.String str) throws java.io.IOException {
        try {
            java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(str, "r");
            try {
                findSignature(randomAccessFile);
                randomAccessFile.close();
                return true;
            } finally {
            }
        } catch (android.util.apk.SignatureNotFoundException e) {
            return false;
        }
    }

    public static java.security.cert.X509Certificate[][] verify(java.lang.String str) throws android.util.apk.SignatureNotFoundException, java.lang.SecurityException, java.io.IOException {
        return verify(str, true).certs;
    }

    public static java.security.cert.X509Certificate[][] unsafeGetCertsWithoutVerification(java.lang.String str) throws android.util.apk.SignatureNotFoundException, java.lang.SecurityException, java.io.IOException {
        return verify(str, false).certs;
    }

    public static android.util.apk.ApkSignatureSchemeV2Verifier.VerifiedSigner verify(java.lang.String str, boolean z) throws android.util.apk.SignatureNotFoundException, java.lang.SecurityException, java.io.IOException {
        java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(str, "r");
        try {
            android.util.apk.ApkSignatureSchemeV2Verifier.VerifiedSigner verify = verify(randomAccessFile, z);
            randomAccessFile.close();
            return verify;
        } catch (java.lang.Throwable th) {
            try {
                randomAccessFile.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static android.util.apk.ApkSignatureSchemeV2Verifier.VerifiedSigner verify(java.io.RandomAccessFile randomAccessFile, boolean z) throws android.util.apk.SignatureNotFoundException, java.lang.SecurityException, java.io.IOException {
        return verify(randomAccessFile, findSignature(randomAccessFile), z);
    }

    public static android.util.apk.SignatureInfo findSignature(java.io.RandomAccessFile randomAccessFile) throws java.io.IOException, android.util.apk.SignatureNotFoundException {
        return android.util.apk.ApkSigningBlockUtils.findSignature(randomAccessFile, APK_SIGNATURE_SCHEME_V2_BLOCK_ID);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static android.util.apk.ApkSignatureSchemeV2Verifier.VerifiedSigner verify(java.io.RandomAccessFile randomAccessFile, android.util.apk.SignatureInfo signatureInfo, boolean z) throws java.lang.SecurityException, java.io.IOException {
        byte[] bArr;
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("X.509");
            try {
                java.nio.ByteBuffer lengthPrefixedSlice = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(signatureInfo.signatureBlock);
                int i = 0;
                while (lengthPrefixedSlice.hasRemaining()) {
                    i++;
                    if (i > 10) {
                        throw new java.lang.SecurityException("APK Signature Scheme v2 only supports a maximum of 10 signers");
                    }
                    try {
                        arrayList.add(verifySigner(android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice), arrayMap, certificateFactory));
                    } catch (java.io.IOException | java.lang.SecurityException | java.nio.BufferUnderflowException e) {
                        throw new java.lang.SecurityException("Failed to parse/verify signer #" + i + " block", e);
                    }
                }
                if (i < 1) {
                    throw new java.lang.SecurityException("No signers found");
                }
                if (arrayMap.isEmpty()) {
                    throw new java.lang.SecurityException("No content digests found");
                }
                if (z) {
                    android.util.apk.ApkSigningBlockUtils.verifyIntegrity(arrayMap, randomAccessFile, signatureInfo);
                }
                if (!arrayMap.containsKey(3)) {
                    bArr = null;
                } else {
                    bArr = android.util.apk.ApkSigningBlockUtils.parseVerityDigestAndVerifySourceLength((byte[]) arrayMap.get(3), randomAccessFile.getChannel().size(), signatureInfo);
                }
                return new android.util.apk.ApkSignatureSchemeV2Verifier.VerifiedSigner((java.security.cert.X509Certificate[][]) arrayList.toArray(new java.security.cert.X509Certificate[arrayList.size()][]), bArr, arrayMap);
            } catch (java.io.IOException e2) {
                throw new java.lang.SecurityException("Failed to read list of signers", e2);
            }
        } catch (java.security.cert.CertificateException e3) {
            throw new java.lang.RuntimeException("Failed to obtain X.509 CertificateFactory", e3);
        }
    }

    private static java.security.cert.X509Certificate[] verifySigner(java.nio.ByteBuffer byteBuffer, java.util.Map<java.lang.Integer, byte[]> map, java.security.cert.CertificateFactory certificateFactory) throws java.lang.SecurityException, java.io.IOException {
        java.nio.ByteBuffer lengthPrefixedSlice = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
        java.nio.ByteBuffer lengthPrefixedSlice2 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
        byte[] readLengthPrefixedByteArray = android.util.apk.ApkSigningBlockUtils.readLengthPrefixedByteArray(byteBuffer);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        byte[] bArr = null;
        int i = 0;
        int i2 = -1;
        byte[] bArr2 = null;
        while (lengthPrefixedSlice2.hasRemaining()) {
            i++;
            try {
                java.nio.ByteBuffer lengthPrefixedSlice3 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice2);
                if (lengthPrefixedSlice3.remaining() < 8) {
                    throw new java.lang.SecurityException("Signature record too short");
                }
                int i3 = lengthPrefixedSlice3.getInt();
                arrayList.add(java.lang.Integer.valueOf(i3));
                if (android.util.apk.ApkSigningBlockUtils.isSupportedSignatureAlgorithm(i3)) {
                    if (i2 == -1 || android.util.apk.ApkSigningBlockUtils.compareSignatureAlgorithm(i3, i2) > 0) {
                        bArr2 = android.util.apk.ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice3);
                        i2 = i3;
                    }
                }
            } catch (java.io.IOException | java.nio.BufferUnderflowException e) {
                throw new java.lang.SecurityException("Failed to parse signature record #" + i, e);
            }
        }
        if (i2 == -1) {
            if (i == 0) {
                throw new java.lang.SecurityException("No signatures found");
            }
            throw new java.lang.SecurityException("No supported signatures found");
        }
        java.lang.String signatureAlgorithmJcaKeyAlgorithm = android.util.apk.ApkSigningBlockUtils.getSignatureAlgorithmJcaKeyAlgorithm(i2);
        android.util.Pair<java.lang.String, ? extends java.security.spec.AlgorithmParameterSpec> signatureAlgorithmJcaSignatureAlgorithm = android.util.apk.ApkSigningBlockUtils.getSignatureAlgorithmJcaSignatureAlgorithm(i2);
        java.lang.String str = signatureAlgorithmJcaSignatureAlgorithm.first;
        java.security.spec.AlgorithmParameterSpec algorithmParameterSpec = (java.security.spec.AlgorithmParameterSpec) signatureAlgorithmJcaSignatureAlgorithm.second;
        try {
            java.security.PublicKey generatePublic = java.security.KeyFactory.getInstance(signatureAlgorithmJcaKeyAlgorithm).generatePublic(new java.security.spec.X509EncodedKeySpec(readLengthPrefixedByteArray));
            java.security.Signature signature = java.security.Signature.getInstance(str);
            signature.initVerify(generatePublic);
            if (algorithmParameterSpec != null) {
                signature.setParameter(algorithmParameterSpec);
            }
            signature.update(lengthPrefixedSlice);
            if (!signature.verify(bArr2)) {
                throw new java.lang.SecurityException(str + " signature did not verify");
            }
            lengthPrefixedSlice.clear();
            java.nio.ByteBuffer lengthPrefixedSlice4 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            int i4 = 0;
            while (lengthPrefixedSlice4.hasRemaining()) {
                i4++;
                try {
                    java.nio.ByteBuffer lengthPrefixedSlice5 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice4);
                    if (lengthPrefixedSlice5.remaining() < 8) {
                        throw new java.io.IOException("Record too short");
                    }
                    int i5 = lengthPrefixedSlice5.getInt();
                    arrayList2.add(java.lang.Integer.valueOf(i5));
                    if (i5 == i2) {
                        bArr = android.util.apk.ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice5);
                    }
                } catch (java.io.IOException | java.nio.BufferUnderflowException e2) {
                    throw new java.io.IOException("Failed to parse digest record #" + i4, e2);
                }
            }
            if (!arrayList.equals(arrayList2)) {
                throw new java.lang.SecurityException("Signature algorithms don't match between digests and signatures records");
            }
            int signatureAlgorithmContentDigestAlgorithm = android.util.apk.ApkSigningBlockUtils.getSignatureAlgorithmContentDigestAlgorithm(i2);
            byte[] put = map.put(java.lang.Integer.valueOf(signatureAlgorithmContentDigestAlgorithm), bArr);
            if (put != null && !java.security.MessageDigest.isEqual(put, bArr)) {
                throw new java.lang.SecurityException(android.util.apk.ApkSigningBlockUtils.getContentDigestAlgorithmJcaDigestAlgorithm(signatureAlgorithmContentDigestAlgorithm) + " contents digest does not match the digest specified by a preceding signer");
            }
            java.nio.ByteBuffer lengthPrefixedSlice6 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
            java.util.ArrayList arrayList3 = new java.util.ArrayList();
            int i6 = 0;
            while (lengthPrefixedSlice6.hasRemaining()) {
                i6++;
                byte[] readLengthPrefixedByteArray2 = android.util.apk.ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice6);
                try {
                    arrayList3.add(new android.util.apk.VerbatimX509Certificate((java.security.cert.X509Certificate) certificateFactory.generateCertificate(new java.io.ByteArrayInputStream(readLengthPrefixedByteArray2)), readLengthPrefixedByteArray2));
                } catch (java.security.cert.CertificateException e3) {
                    throw new java.lang.SecurityException("Failed to decode certificate #" + i6, e3);
                }
            }
            if (arrayList3.isEmpty()) {
                throw new java.lang.SecurityException("No certificates listed");
            }
            if (!java.util.Arrays.equals(readLengthPrefixedByteArray, ((java.security.cert.X509Certificate) arrayList3.get(0)).getPublicKey().getEncoded())) {
                throw new java.lang.SecurityException("Public key mismatch between certificate and signature record");
            }
            verifyAdditionalAttributes(android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice));
            return (java.security.cert.X509Certificate[]) arrayList3.toArray(new java.security.cert.X509Certificate[arrayList3.size()]);
        } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | java.security.SignatureException | java.security.spec.InvalidKeySpecException e4) {
            throw new java.lang.SecurityException("Failed to verify " + str + " signature", e4);
        }
    }

    private static void verifyAdditionalAttributes(java.nio.ByteBuffer byteBuffer) throws java.lang.SecurityException, java.io.IOException {
        while (byteBuffer.hasRemaining()) {
            java.nio.ByteBuffer lengthPrefixedSlice = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
            if (lengthPrefixedSlice.remaining() < 4) {
                throw new java.io.IOException("Remaining buffer too short to contain additional attribute ID. Remaining: " + lengthPrefixedSlice.remaining());
            }
            switch (lengthPrefixedSlice.getInt()) {
                case STRIPPING_PROTECTION_ATTR_ID /* -1091571699 */:
                    if (lengthPrefixedSlice.remaining() < 4) {
                        throw new java.io.IOException("V2 Signature Scheme Stripping Protection Attribute  value too small.  Expected 4 bytes, but found " + lengthPrefixedSlice.remaining());
                    }
                    if (lengthPrefixedSlice.getInt() == 3) {
                        throw new java.lang.SecurityException("V2 signature indicates APK is signed using APK Signature Scheme v3, but none was found. Signature stripped?");
                    }
                    break;
            }
        }
    }

    static byte[] getVerityRootHash(java.lang.String str) throws java.io.IOException, android.util.apk.SignatureNotFoundException, java.lang.SecurityException {
        java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(str, "r");
        try {
            findSignature(randomAccessFile);
            byte[] bArr = verify(randomAccessFile, false).verityRootHash;
            randomAccessFile.close();
            return bArr;
        } catch (java.lang.Throwable th) {
            try {
                randomAccessFile.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    static byte[] generateApkVerity(java.lang.String str, android.util.apk.ByteBufferFactory byteBufferFactory) throws java.io.IOException, android.util.apk.SignatureNotFoundException, java.lang.SecurityException, java.security.DigestException, java.security.NoSuchAlgorithmException {
        java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(str, "r");
        try {
            byte[] generateApkVerity = android.util.apk.VerityBuilder.generateApkVerity(str, byteBufferFactory, findSignature(randomAccessFile));
            randomAccessFile.close();
            return generateApkVerity;
        } catch (java.lang.Throwable th) {
            try {
                randomAccessFile.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static class VerifiedSigner {
        public final java.security.cert.X509Certificate[][] certs;
        public final java.util.Map<java.lang.Integer, byte[]> contentDigests;
        public final byte[] verityRootHash;

        public VerifiedSigner(java.security.cert.X509Certificate[][] x509CertificateArr, byte[] bArr, java.util.Map<java.lang.Integer, byte[]> map) {
            this.certs = x509CertificateArr;
            this.verityRootHash = bArr;
            this.contentDigests = map;
        }
    }
}
