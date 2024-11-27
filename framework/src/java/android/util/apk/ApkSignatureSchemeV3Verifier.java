package android.util.apk;

/* loaded from: classes3.dex */
public class ApkSignatureSchemeV3Verifier {
    static final int APK_SIGNATURE_SCHEME_V31_BLOCK_ID = 462663009;
    static final int APK_SIGNATURE_SCHEME_V3_BLOCK_ID = -262969152;
    private static final int PROOF_OF_ROTATION_ATTR_ID = 1000370060;
    private static final int ROTATION_MIN_SDK_VERSION_ATTR_ID = 1436519170;
    private static final int ROTATION_ON_DEV_RELEASE_ATTR_ID = -1029262406;
    public static final int SF_ATTRIBUTE_ANDROID_APK_SIGNED_ID = 3;
    private final java.io.RandomAccessFile mApk;
    private int mBlockId;
    private java.util.OptionalInt mOptionalRotationMinSdkVersion = java.util.OptionalInt.empty();
    private int mSignerMinSdkVersion;
    private final boolean mVerifyIntegrity;

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

    public static android.util.apk.ApkSignatureSchemeV3Verifier.VerifiedSigner verify(java.lang.String str) throws android.util.apk.SignatureNotFoundException, java.lang.SecurityException, java.io.IOException {
        return verify(str, true);
    }

    public static android.util.apk.ApkSignatureSchemeV3Verifier.VerifiedSigner unsafeGetCertsWithoutVerification(java.lang.String str) throws android.util.apk.SignatureNotFoundException, java.lang.SecurityException, java.io.IOException {
        return verify(str, false);
    }

    private static android.util.apk.ApkSignatureSchemeV3Verifier.VerifiedSigner verify(java.lang.String str, boolean z) throws android.util.apk.SignatureNotFoundException, java.lang.SecurityException, java.io.IOException {
        java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(str, "r");
        try {
            android.util.apk.ApkSignatureSchemeV3Verifier.VerifiedSigner verify = verify(randomAccessFile, z);
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

    private static android.util.apk.ApkSignatureSchemeV3Verifier.VerifiedSigner verify(java.io.RandomAccessFile randomAccessFile, boolean z) throws android.util.apk.SignatureNotFoundException, java.lang.SecurityException, java.io.IOException {
        android.util.apk.ApkSignatureSchemeV3Verifier apkSignatureSchemeV3Verifier = new android.util.apk.ApkSignatureSchemeV3Verifier(randomAccessFile, z);
        try {
            return apkSignatureSchemeV3Verifier.verify(findSignature(randomAccessFile, APK_SIGNATURE_SCHEME_V31_BLOCK_ID), APK_SIGNATURE_SCHEME_V31_BLOCK_ID);
        } catch (android.util.apk.ApkSignatureSchemeV3Verifier.PlatformNotSupportedException | android.util.apk.SignatureNotFoundException e) {
            try {
                return apkSignatureSchemeV3Verifier.verify(findSignature(randomAccessFile, APK_SIGNATURE_SCHEME_V3_BLOCK_ID), APK_SIGNATURE_SCHEME_V3_BLOCK_ID);
            } catch (android.util.apk.ApkSignatureSchemeV3Verifier.PlatformNotSupportedException e2) {
                throw new java.lang.SecurityException(e2);
            }
        }
    }

    public static android.util.apk.SignatureInfo findSignature(java.io.RandomAccessFile randomAccessFile) throws java.io.IOException, android.util.apk.SignatureNotFoundException {
        return findSignature(randomAccessFile, APK_SIGNATURE_SCHEME_V3_BLOCK_ID);
    }

    private static android.util.apk.SignatureInfo findSignature(java.io.RandomAccessFile randomAccessFile, int i) throws java.io.IOException, android.util.apk.SignatureNotFoundException {
        return android.util.apk.ApkSigningBlockUtils.findSignature(randomAccessFile, i);
    }

    private ApkSignatureSchemeV3Verifier(java.io.RandomAccessFile randomAccessFile, boolean z) {
        this.mApk = randomAccessFile;
        this.mVerifyIntegrity = z;
    }

    private android.util.apk.ApkSignatureSchemeV3Verifier.VerifiedSigner verify(android.util.apk.SignatureInfo signatureInfo, int i) throws java.lang.SecurityException, java.io.IOException, android.util.apk.ApkSignatureSchemeV3Verifier.PlatformNotSupportedException {
        this.mBlockId = i;
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        try {
            java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("X.509");
            try {
                java.nio.ByteBuffer lengthPrefixedSlice = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(signatureInfo.signatureBlock);
                int i2 = 0;
                android.util.Pair<java.security.cert.X509Certificate[], android.util.apk.ApkSigningBlockUtils.VerifiedProofOfRotation> pair = null;
                while (lengthPrefixedSlice.hasRemaining()) {
                    try {
                        pair = verifySigner(android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice), arrayMap, certificateFactory);
                        i2++;
                    } catch (android.util.apk.ApkSignatureSchemeV3Verifier.PlatformNotSupportedException e) {
                    } catch (java.io.IOException | java.lang.SecurityException | java.nio.BufferUnderflowException e2) {
                        throw new java.lang.SecurityException("Failed to parse/verify signer #" + i2 + " block", e2);
                    }
                }
                if (i2 < 1 || pair == null) {
                    if (i == APK_SIGNATURE_SCHEME_V3_BLOCK_ID) {
                        throw new java.lang.SecurityException("No signers found");
                    }
                    throw new android.util.apk.ApkSignatureSchemeV3Verifier.PlatformNotSupportedException("None of the signers support the current platform version");
                }
                if (i2 != 1) {
                    throw new java.lang.SecurityException("APK Signature Scheme V3 only supports one signer: multiple signers found.");
                }
                if (arrayMap.isEmpty()) {
                    throw new java.lang.SecurityException("No content digests found");
                }
                if (this.mVerifyIntegrity) {
                    android.util.apk.ApkSigningBlockUtils.verifyIntegrity(arrayMap, this.mApk, signatureInfo);
                }
                return new android.util.apk.ApkSignatureSchemeV3Verifier.VerifiedSigner(pair.first, pair.second, arrayMap.containsKey(3) ? android.util.apk.ApkSigningBlockUtils.parseVerityDigestAndVerifySourceLength(arrayMap.get(3), this.mApk.getChannel().size(), signatureInfo) : null, arrayMap, i);
            } catch (java.io.IOException e3) {
                throw new java.lang.SecurityException("Failed to read list of signers", e3);
            }
        } catch (java.security.cert.CertificateException e4) {
            throw new java.lang.RuntimeException("Failed to obtain X.509 CertificateFactory", e4);
        }
    }

    private android.util.Pair<java.security.cert.X509Certificate[], android.util.apk.ApkSigningBlockUtils.VerifiedProofOfRotation> verifySigner(java.nio.ByteBuffer byteBuffer, java.util.Map<java.lang.Integer, byte[]> map, java.security.cert.CertificateFactory certificateFactory) throws java.lang.SecurityException, java.io.IOException, android.util.apk.ApkSignatureSchemeV3Verifier.PlatformNotSupportedException {
        java.nio.ByteBuffer lengthPrefixedSlice = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
        int i = byteBuffer.getInt();
        int i2 = byteBuffer.getInt();
        if (android.os.Build.VERSION.SDK_INT < i || android.os.Build.VERSION.SDK_INT > i2) {
            if (this.mBlockId == APK_SIGNATURE_SCHEME_V31_BLOCK_ID && (!this.mOptionalRotationMinSdkVersion.isPresent() || this.mOptionalRotationMinSdkVersion.getAsInt() > i)) {
                this.mOptionalRotationMinSdkVersion = java.util.OptionalInt.of(i);
            }
            throw new android.util.apk.ApkSignatureSchemeV3Verifier.PlatformNotSupportedException("Signer not supported by this platform version. This platform: " + android.os.Build.VERSION.SDK_INT + ", signer minSdkVersion: " + i + ", maxSdkVersion: " + i2);
        }
        java.nio.ByteBuffer lengthPrefixedSlice2 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
        byte[] readLengthPrefixedByteArray = android.util.apk.ApkSigningBlockUtils.readLengthPrefixedByteArray(byteBuffer);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i3 = -1;
        int i4 = 0;
        byte[] bArr = null;
        while (true) {
            int i5 = 8;
            if (lengthPrefixedSlice2.hasRemaining()) {
                i4++;
                try {
                    java.nio.ByteBuffer lengthPrefixedSlice3 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice2);
                    if (lengthPrefixedSlice3.remaining() < 8) {
                        throw new java.lang.SecurityException("Signature record too short");
                    }
                    int i6 = lengthPrefixedSlice3.getInt();
                    arrayList.add(java.lang.Integer.valueOf(i6));
                    if (android.util.apk.ApkSigningBlockUtils.isSupportedSignatureAlgorithm(i6)) {
                        if (i3 == -1 || android.util.apk.ApkSigningBlockUtils.compareSignatureAlgorithm(i6, i3) > 0) {
                            bArr = android.util.apk.ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice3);
                            i3 = i6;
                        }
                    }
                } catch (java.io.IOException | java.nio.BufferUnderflowException e) {
                    throw new java.lang.SecurityException("Failed to parse signature record #" + i4, e);
                }
            } else {
                if (i3 == -1) {
                    if (i4 == 0) {
                        throw new java.lang.SecurityException("No signatures found");
                    }
                    throw new java.lang.SecurityException("No supported signatures found");
                }
                java.lang.String signatureAlgorithmJcaKeyAlgorithm = android.util.apk.ApkSigningBlockUtils.getSignatureAlgorithmJcaKeyAlgorithm(i3);
                android.util.Pair<java.lang.String, ? extends java.security.spec.AlgorithmParameterSpec> signatureAlgorithmJcaSignatureAlgorithm = android.util.apk.ApkSigningBlockUtils.getSignatureAlgorithmJcaSignatureAlgorithm(i3);
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
                    if (!signature.verify(bArr)) {
                        throw new java.lang.SecurityException(str + " signature did not verify");
                    }
                    lengthPrefixedSlice.clear();
                    java.nio.ByteBuffer lengthPrefixedSlice4 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
                    java.util.ArrayList arrayList2 = new java.util.ArrayList();
                    int i7 = 0;
                    byte[] bArr2 = null;
                    while (lengthPrefixedSlice4.hasRemaining()) {
                        i7++;
                        try {
                            java.nio.ByteBuffer lengthPrefixedSlice5 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice4);
                            if (lengthPrefixedSlice5.remaining() < i5) {
                                throw new java.io.IOException("Record too short");
                            }
                            int i8 = lengthPrefixedSlice5.getInt();
                            arrayList2.add(java.lang.Integer.valueOf(i8));
                            if (i8 == i3) {
                                bArr2 = android.util.apk.ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice5);
                            }
                            i5 = 8;
                        } catch (java.io.IOException | java.nio.BufferUnderflowException e2) {
                            throw new java.io.IOException("Failed to parse digest record #" + i7, e2);
                        }
                    }
                    if (!arrayList.equals(arrayList2)) {
                        throw new java.lang.SecurityException("Signature algorithms don't match between digests and signatures records");
                    }
                    int signatureAlgorithmContentDigestAlgorithm = android.util.apk.ApkSigningBlockUtils.getSignatureAlgorithmContentDigestAlgorithm(i3);
                    byte[] put = map.put(java.lang.Integer.valueOf(signatureAlgorithmContentDigestAlgorithm), bArr2);
                    if (put != null && !java.security.MessageDigest.isEqual(put, bArr2)) {
                        throw new java.lang.SecurityException(android.util.apk.ApkSigningBlockUtils.getContentDigestAlgorithmJcaDigestAlgorithm(signatureAlgorithmContentDigestAlgorithm) + " contents digest does not match the digest specified by a preceding signer");
                    }
                    java.nio.ByteBuffer lengthPrefixedSlice6 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
                    java.util.ArrayList arrayList3 = new java.util.ArrayList();
                    int i9 = 0;
                    while (lengthPrefixedSlice6.hasRemaining()) {
                        i9++;
                        byte[] readLengthPrefixedByteArray2 = android.util.apk.ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice6);
                        try {
                            arrayList3.add(new android.util.apk.VerbatimX509Certificate((java.security.cert.X509Certificate) certificateFactory.generateCertificate(new java.io.ByteArrayInputStream(readLengthPrefixedByteArray2)), readLengthPrefixedByteArray2));
                        } catch (java.security.cert.CertificateException e3) {
                            throw new java.lang.SecurityException("Failed to decode certificate #" + i9, e3);
                        }
                    }
                    if (arrayList3.isEmpty()) {
                        throw new java.lang.SecurityException("No certificates listed");
                    }
                    if (!java.util.Arrays.equals(readLengthPrefixedByteArray, arrayList3.get(0).getPublicKey().getEncoded())) {
                        throw new java.lang.SecurityException("Public key mismatch between certificate and signature record");
                    }
                    int i10 = lengthPrefixedSlice.getInt();
                    if (i10 != i) {
                        throw new java.lang.SecurityException("minSdkVersion mismatch between signed and unsigned in v3 signer block.");
                    }
                    this.mSignerMinSdkVersion = i10;
                    if (lengthPrefixedSlice.getInt() != i2) {
                        throw new java.lang.SecurityException("maxSdkVersion mismatch between signed and unsigned in v3 signer block.");
                    }
                    return verifyAdditionalAttributes(android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice), arrayList3, certificateFactory);
                } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | java.security.SignatureException | java.security.spec.InvalidKeySpecException e4) {
                    throw new java.lang.SecurityException("Failed to verify " + str + " signature", e4);
                }
            }
        }
    }

    private android.util.Pair<java.security.cert.X509Certificate[], android.util.apk.ApkSigningBlockUtils.VerifiedProofOfRotation> verifyAdditionalAttributes(java.nio.ByteBuffer byteBuffer, java.util.List<java.security.cert.X509Certificate> list, java.security.cert.CertificateFactory certificateFactory) throws java.io.IOException, android.util.apk.ApkSignatureSchemeV3Verifier.PlatformNotSupportedException {
        java.security.cert.X509Certificate[] x509CertificateArr = (java.security.cert.X509Certificate[]) list.toArray(new java.security.cert.X509Certificate[list.size()]);
        android.util.apk.ApkSigningBlockUtils.VerifiedProofOfRotation verifiedProofOfRotation = null;
        while (byteBuffer.hasRemaining()) {
            java.nio.ByteBuffer lengthPrefixedSlice = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
            if (lengthPrefixedSlice.remaining() < 4) {
                throw new java.io.IOException("Remaining buffer too short to contain additional attribute ID. Remaining: " + lengthPrefixedSlice.remaining());
            }
            switch (lengthPrefixedSlice.getInt()) {
                case ROTATION_ON_DEV_RELEASE_ATTR_ID /* -1029262406 */:
                    if (this.mBlockId == APK_SIGNATURE_SCHEME_V31_BLOCK_ID && android.os.Build.VERSION.SDK_INT == this.mSignerMinSdkVersion && "REL".equals(android.os.Build.VERSION.CODENAME)) {
                        this.mOptionalRotationMinSdkVersion = java.util.OptionalInt.of(this.mSignerMinSdkVersion);
                        throw new android.util.apk.ApkSignatureSchemeV3Verifier.PlatformNotSupportedException("The device is running a release version of " + this.mSignerMinSdkVersion + ", but the signer is targeting a dev release");
                    }
                    break;
                case PROOF_OF_ROTATION_ATTR_ID /* 1000370060 */:
                    if (verifiedProofOfRotation != null) {
                        throw new java.lang.SecurityException("Encountered multiple Proof-of-rotation records when verifying APK Signature Scheme v3 signature");
                    }
                    verifiedProofOfRotation = android.util.apk.ApkSigningBlockUtils.verifyProofOfRotationStruct(lengthPrefixedSlice, certificateFactory);
                    try {
                        if (verifiedProofOfRotation.certs.size() > 0 && !java.util.Arrays.equals(verifiedProofOfRotation.certs.get(verifiedProofOfRotation.certs.size() - 1).getEncoded(), x509CertificateArr[0].getEncoded())) {
                            throw new java.lang.SecurityException("Terminal certificate in Proof-of-rotation record does not match APK signing certificate");
                        }
                    } catch (java.security.cert.CertificateEncodingException e) {
                        throw new java.lang.SecurityException("Failed to encode certificate when comparing Proof-of-rotation record and signing certificate", e);
                    }
                    break;
                case ROTATION_MIN_SDK_VERSION_ATTR_ID /* 1436519170 */:
                    if (lengthPrefixedSlice.remaining() < 4) {
                        throw new java.io.IOException("Remaining buffer too short to contain rotation minSdkVersion value. Remaining: " + lengthPrefixedSlice.remaining());
                    }
                    int i = lengthPrefixedSlice.getInt();
                    if (!this.mOptionalRotationMinSdkVersion.isPresent()) {
                        throw new java.lang.SecurityException("Expected a v3.1 signing block targeting SDK version " + i + ", but a v3.1 block was not found");
                    }
                    int asInt = this.mOptionalRotationMinSdkVersion.getAsInt();
                    if (asInt != i) {
                        throw new java.lang.SecurityException("Expected a v3.1 signing block targeting SDK version " + i + ", but the v3.1 block was targeting " + asInt);
                    }
                    break;
            }
        }
        return android.util.Pair.create(x509CertificateArr, verifiedProofOfRotation);
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
        public final int blockId;
        public final java.security.cert.X509Certificate[] certs;
        public final java.util.Map<java.lang.Integer, byte[]> contentDigests;
        public final android.util.apk.ApkSigningBlockUtils.VerifiedProofOfRotation por;
        public final byte[] verityRootHash;

        public VerifiedSigner(java.security.cert.X509Certificate[] x509CertificateArr, android.util.apk.ApkSigningBlockUtils.VerifiedProofOfRotation verifiedProofOfRotation, byte[] bArr, java.util.Map<java.lang.Integer, byte[]> map, int i) {
            this.certs = x509CertificateArr;
            this.por = verifiedProofOfRotation;
            this.verityRootHash = bArr;
            this.contentDigests = map;
            this.blockId = i;
        }
    }

    private static class PlatformNotSupportedException extends java.lang.Exception {
        PlatformNotSupportedException(java.lang.String str) {
            super(str);
        }
    }
}
