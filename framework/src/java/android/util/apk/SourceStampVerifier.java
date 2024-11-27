package android.util.apk;

/* loaded from: classes3.dex */
public abstract class SourceStampVerifier {
    private static final int APK_SIGNATURE_SCHEME_V2_BLOCK_ID = 1896449818;
    private static final int APK_SIGNATURE_SCHEME_V3_BLOCK_ID = -262969152;
    private static final int PROOF_OF_ROTATION_ATTR_ID = -1654455305;
    private static final int SOURCE_STAMP_BLOCK_ID = 1845461005;
    private static final java.lang.String SOURCE_STAMP_CERTIFICATE_HASH_ZIP_ENTRY_NAME = "stamp-cert-sha256";
    private static final java.lang.String TAG = "SourceStampVerifier";
    private static final int VERSION_APK_SIGNATURE_SCHEME_V2 = 2;
    private static final int VERSION_APK_SIGNATURE_SCHEME_V3 = 3;
    private static final int VERSION_JAR_SIGNATURE_SCHEME = 1;

    private SourceStampVerifier() {
    }

    public static android.util.apk.SourceStampVerificationResult verify(java.util.List<java.lang.String> list) {
        java.util.List<? extends java.security.cert.Certificate> emptyList = java.util.Collections.emptyList();
        java.util.Iterator<java.lang.String> it = list.iterator();
        java.security.cert.Certificate certificate = null;
        while (it.hasNext()) {
            android.util.apk.SourceStampVerificationResult verify = verify(it.next());
            if (!verify.isPresent() || !verify.isVerified()) {
                return verify;
            }
            if (certificate != null && (!certificate.equals(verify.getCertificate()) || !emptyList.equals(verify.getCertificateLineage()))) {
                return android.util.apk.SourceStampVerificationResult.notVerified();
            }
            certificate = verify.getCertificate();
            emptyList = verify.getCertificateLineage();
        }
        return android.util.apk.SourceStampVerificationResult.verified(certificate, emptyList);
    }

    public static android.util.apk.SourceStampVerificationResult verify(java.lang.String str) {
        android.util.jar.StrictJarFile strictJarFile;
        android.util.jar.StrictJarFile strictJarFile2 = null;
        try {
            try {
                java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(str, "r");
                try {
                    strictJarFile = new android.util.jar.StrictJarFile(str, false, false);
                } catch (java.lang.Throwable th) {
                    th = th;
                }
                try {
                    byte[] sourceStampCertificateDigest = getSourceStampCertificateDigest(strictJarFile);
                    try {
                        if (sourceStampCertificateDigest == null) {
                            android.util.apk.SourceStampVerificationResult notPresent = android.util.apk.SourceStampVerificationResult.notPresent();
                            randomAccessFile.close();
                            closeApkJar(strictJarFile);
                            return notPresent;
                        }
                        android.util.apk.SourceStampVerificationResult verify = verify(randomAccessFile, sourceStampCertificateDigest, getManifestBytes(strictJarFile));
                        randomAccessFile.close();
                        closeApkJar(strictJarFile);
                        return verify;
                    } catch (java.io.IOException e) {
                        strictJarFile2 = strictJarFile;
                        android.util.apk.SourceStampVerificationResult notPresent2 = android.util.apk.SourceStampVerificationResult.notPresent();
                        closeApkJar(strictJarFile2);
                        return notPresent2;
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        strictJarFile2 = strictJarFile;
                        closeApkJar(strictJarFile2);
                        throw th;
                    }
                } catch (java.lang.Throwable th3) {
                    th = th3;
                    strictJarFile2 = strictJarFile;
                    try {
                        randomAccessFile.close();
                    } catch (java.lang.Throwable th4) {
                        th.addSuppressed(th4);
                    }
                    throw th;
                }
            } catch (java.io.IOException e2) {
            }
        } catch (java.lang.Throwable th5) {
            th = th5;
        }
    }

    private static android.util.apk.SourceStampVerificationResult verify(java.io.RandomAccessFile randomAccessFile, byte[] bArr, byte[] bArr2) {
        try {
            try {
                return verify(android.util.apk.ApkSigningBlockUtils.findSignature(randomAccessFile, SOURCE_STAMP_BLOCK_ID), getSignatureSchemeDigests(getSignatureSchemeApkContentDigests(randomAccessFile, bArr2)), bArr);
            } catch (java.io.IOException | java.lang.RuntimeException e) {
                return android.util.apk.SourceStampVerificationResult.notVerified();
            }
        } catch (android.util.apk.SignatureNotFoundException | java.io.IOException | java.lang.RuntimeException e2) {
            return android.util.apk.SourceStampVerificationResult.notPresent();
        }
    }

    private static android.util.apk.SourceStampVerificationResult verify(android.util.apk.SignatureInfo signatureInfo, java.util.Map<java.lang.Integer, byte[]> map, byte[] bArr) throws java.lang.SecurityException, java.io.IOException {
        java.nio.ByteBuffer lengthPrefixedSlice = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(signatureInfo.signatureBlock);
        java.security.cert.X509Certificate verifySourceStampCertificate = verifySourceStampCertificate(lengthPrefixedSlice, bArr);
        java.nio.ByteBuffer lengthPrefixedSlice2 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
        java.util.HashMap hashMap = new java.util.HashMap();
        while (lengthPrefixedSlice2.hasRemaining()) {
            java.nio.ByteBuffer lengthPrefixedSlice3 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice2);
            hashMap.put(java.lang.Integer.valueOf(lengthPrefixedSlice3.getInt()), lengthPrefixedSlice3);
        }
        for (java.util.Map.Entry<java.lang.Integer, byte[]> entry : map.entrySet()) {
            if (!hashMap.containsKey(entry.getKey())) {
                throw new java.lang.SecurityException(java.lang.String.format("No signatures found for signature scheme %d", entry.getKey()));
            }
            verifySourceStampSignature(entry.getValue(), verifySourceStampCertificate, android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice((java.nio.ByteBuffer) hashMap.get(entry.getKey())));
        }
        java.util.List<java.security.cert.X509Certificate> emptyList = java.util.Collections.emptyList();
        if (lengthPrefixedSlice.hasRemaining()) {
            java.nio.ByteBuffer lengthPrefixedSlice4 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
            java.nio.ByteBuffer lengthPrefixedSlice5 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
            byte[] bArr2 = new byte[lengthPrefixedSlice4.remaining()];
            lengthPrefixedSlice4.get(bArr2);
            lengthPrefixedSlice4.flip();
            verifySourceStampSignature(bArr2, verifySourceStampCertificate, lengthPrefixedSlice5);
            android.util.apk.ApkSigningBlockUtils.VerifiedProofOfRotation verifySourceStampAttributes = verifySourceStampAttributes(lengthPrefixedSlice4, verifySourceStampCertificate);
            if (verifySourceStampAttributes != null) {
                emptyList = verifySourceStampAttributes.certs;
            }
        }
        return android.util.apk.SourceStampVerificationResult.verified(verifySourceStampCertificate, emptyList);
    }

    private static java.security.cert.X509Certificate verifySourceStampCertificate(java.nio.ByteBuffer byteBuffer, byte[] bArr) throws java.io.IOException {
        try {
            java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("X.509");
            byte[] readLengthPrefixedByteArray = android.util.apk.ApkSigningBlockUtils.readLengthPrefixedByteArray(byteBuffer);
            try {
                java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) certificateFactory.generateCertificate(new java.io.ByteArrayInputStream(readLengthPrefixedByteArray));
                if (!java.util.Arrays.equals(bArr, computeSha256Digest(readLengthPrefixedByteArray))) {
                    throw new java.lang.SecurityException("Certificate mismatch between APK and signature block");
                }
                return new android.util.apk.VerbatimX509Certificate(x509Certificate, readLengthPrefixedByteArray);
            } catch (java.security.cert.CertificateException e) {
                throw new java.lang.SecurityException("Failed to decode certificate", e);
            }
        } catch (java.security.cert.CertificateException e2) {
            throw new java.lang.RuntimeException("Failed to obtain X.509 CertificateFactory", e2);
        }
    }

    private static void verifySourceStampSignature(byte[] bArr, java.security.cert.X509Certificate x509Certificate, java.nio.ByteBuffer byteBuffer) throws java.io.IOException {
        int i = 0;
        byte[] bArr2 = null;
        int i2 = -1;
        while (byteBuffer.hasRemaining()) {
            i++;
            try {
                java.nio.ByteBuffer lengthPrefixedSlice = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
                if (lengthPrefixedSlice.remaining() < 8) {
                    throw new java.lang.SecurityException("Signature record too short");
                }
                int i3 = lengthPrefixedSlice.getInt();
                if (android.util.apk.ApkSigningBlockUtils.isSupportedSignatureAlgorithm(i3)) {
                    if (i2 == -1 || android.util.apk.ApkSigningBlockUtils.compareSignatureAlgorithm(i3, i2) > 0) {
                        bArr2 = android.util.apk.ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice);
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
        android.util.Pair<java.lang.String, ? extends java.security.spec.AlgorithmParameterSpec> signatureAlgorithmJcaSignatureAlgorithm = android.util.apk.ApkSigningBlockUtils.getSignatureAlgorithmJcaSignatureAlgorithm(i2);
        java.lang.String str = signatureAlgorithmJcaSignatureAlgorithm.first;
        java.security.spec.AlgorithmParameterSpec algorithmParameterSpec = (java.security.spec.AlgorithmParameterSpec) signatureAlgorithmJcaSignatureAlgorithm.second;
        java.security.PublicKey publicKey = x509Certificate.getPublicKey();
        try {
            java.security.Signature signature = java.security.Signature.getInstance(str);
            signature.initVerify(publicKey);
            if (algorithmParameterSpec != null) {
                signature.setParameter(algorithmParameterSpec);
            }
            signature.update(bArr);
            if (!signature.verify(bArr2)) {
                throw new java.lang.SecurityException(str + " signature did not verify");
            }
        } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | java.security.SignatureException e2) {
            throw new java.lang.SecurityException("Failed to verify " + str + " signature", e2);
        }
    }

    private static java.util.Map<java.lang.Integer, java.util.Map<java.lang.Integer, byte[]>> getSignatureSchemeApkContentDigests(java.io.RandomAccessFile randomAccessFile, byte[] bArr) throws java.io.IOException {
        java.util.HashMap hashMap = new java.util.HashMap();
        try {
            hashMap.put(3, getApkContentDigestsFromSignatureBlock(android.util.apk.ApkSigningBlockUtils.findSignature(randomAccessFile, APK_SIGNATURE_SCHEME_V3_BLOCK_ID).signatureBlock));
        } catch (android.util.apk.SignatureNotFoundException e) {
        }
        try {
            hashMap.put(2, getApkContentDigestsFromSignatureBlock(android.util.apk.ApkSigningBlockUtils.findSignature(randomAccessFile, APK_SIGNATURE_SCHEME_V2_BLOCK_ID).signatureBlock));
        } catch (android.util.apk.SignatureNotFoundException e2) {
        }
        if (bArr != null) {
            java.util.HashMap hashMap2 = new java.util.HashMap();
            hashMap2.put(4, computeSha256Digest(bArr));
            hashMap.put(1, hashMap2);
        }
        return hashMap;
    }

    private static java.util.Map<java.lang.Integer, byte[]> getApkContentDigestsFromSignatureBlock(java.nio.ByteBuffer byteBuffer) throws java.io.IOException {
        java.util.HashMap hashMap = new java.util.HashMap();
        java.nio.ByteBuffer lengthPrefixedSlice = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
        while (lengthPrefixedSlice.hasRemaining()) {
            java.nio.ByteBuffer lengthPrefixedSlice2 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice)));
            while (lengthPrefixedSlice2.hasRemaining()) {
                java.nio.ByteBuffer lengthPrefixedSlice3 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice2);
                int i = lengthPrefixedSlice3.getInt();
                hashMap.put(java.lang.Integer.valueOf(android.util.apk.ApkSigningBlockUtils.getSignatureAlgorithmContentDigestAlgorithm(i)), android.util.apk.ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice3));
            }
        }
        return hashMap;
    }

    private static java.util.Map<java.lang.Integer, byte[]> getSignatureSchemeDigests(java.util.Map<java.lang.Integer, java.util.Map<java.lang.Integer, byte[]>> map) {
        java.util.HashMap hashMap = new java.util.HashMap();
        for (java.util.Map.Entry<java.lang.Integer, java.util.Map<java.lang.Integer, byte[]>> entry : map.entrySet()) {
            hashMap.put(entry.getKey(), encodeApkContentDigests(getApkDigests(entry.getValue())));
        }
        return hashMap;
    }

    private static java.util.List<android.util.Pair<java.lang.Integer, byte[]>> getApkDigests(java.util.Map<java.lang.Integer, byte[]> map) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.util.Map.Entry<java.lang.Integer, byte[]> entry : map.entrySet()) {
            arrayList.add(android.util.Pair.create(entry.getKey(), entry.getValue()));
        }
        arrayList.sort(java.util.Comparator.comparing(new java.util.function.Function() { // from class: android.util.apk.SourceStampVerifier$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.util.apk.SourceStampVerifier.lambda$getApkDigests$0((android.util.Pair) obj);
            }
        }));
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ java.lang.Integer lambda$getApkDigests$0(android.util.Pair pair) {
        return (java.lang.Integer) pair.first;
    }

    private static byte[] getSourceStampCertificateDigest(android.util.jar.StrictJarFile strictJarFile) throws java.io.IOException {
        java.util.zip.ZipEntry findEntry = strictJarFile.findEntry(SOURCE_STAMP_CERTIFICATE_HASH_ZIP_ENTRY_NAME);
        if (findEntry == null) {
            return null;
        }
        return libcore.io.Streams.readFully(strictJarFile.getInputStream(findEntry));
    }

    private static byte[] getManifestBytes(android.util.jar.StrictJarFile strictJarFile) throws java.io.IOException {
        java.util.zip.ZipEntry findEntry = strictJarFile.findEntry("META-INF/MANIFEST.MF");
        if (findEntry == null) {
            return null;
        }
        return libcore.io.Streams.readFully(strictJarFile.getInputStream(findEntry));
    }

    private static byte[] encodeApkContentDigests(java.util.List<android.util.Pair<java.lang.Integer, byte[]>> list) {
        java.util.Iterator<android.util.Pair<java.lang.Integer, byte[]>> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().second.length + 12;
        }
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(i);
        allocate.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        for (android.util.Pair<java.lang.Integer, byte[]> pair : list) {
            byte[] bArr = pair.second;
            allocate.putInt(bArr.length + 8);
            allocate.putInt(pair.first.intValue());
            allocate.putInt(bArr.length);
            allocate.put(bArr);
        }
        return allocate.array();
    }

    private static android.util.apk.ApkSigningBlockUtils.VerifiedProofOfRotation verifySourceStampAttributes(java.nio.ByteBuffer byteBuffer, java.security.cert.X509Certificate x509Certificate) throws java.io.IOException {
        try {
            java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("X.509");
            java.nio.ByteBuffer lengthPrefixedSlice = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
            android.util.apk.ApkSigningBlockUtils.VerifiedProofOfRotation verifiedProofOfRotation = null;
            while (lengthPrefixedSlice.hasRemaining()) {
                java.nio.ByteBuffer lengthPrefixedSlice2 = android.util.apk.ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
                if (lengthPrefixedSlice2.getInt() == PROOF_OF_ROTATION_ATTR_ID) {
                    if (verifiedProofOfRotation != null) {
                        throw new java.lang.SecurityException("Encountered multiple Proof-of-rotation records when verifying source stamp signature");
                    }
                    verifiedProofOfRotation = android.util.apk.ApkSigningBlockUtils.verifyProofOfRotationStruct(lengthPrefixedSlice2, certificateFactory);
                    try {
                        if (verifiedProofOfRotation.certs.size() > 0 && !java.util.Arrays.equals(verifiedProofOfRotation.certs.get(verifiedProofOfRotation.certs.size() - 1).getEncoded(), x509Certificate.getEncoded())) {
                            throw new java.lang.SecurityException("Terminal certificate in Proof-of-rotation record does not match source stamp certificate");
                        }
                    } catch (java.security.cert.CertificateEncodingException e) {
                        throw new java.lang.SecurityException("Failed to encode certificate when comparing Proof-of-rotation record and source stamp certificate", e);
                    }
                }
            }
            return verifiedProofOfRotation;
        } catch (java.security.cert.CertificateException e2) {
            throw new java.lang.RuntimeException("Failed to obtain X.509 CertificateFactory", e2);
        }
    }

    private static byte[] computeSha256Digest(byte[] bArr) {
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException("Failed to find SHA-256", e);
        }
    }

    private static void closeApkJar(android.util.jar.StrictJarFile strictJarFile) {
        if (strictJarFile == null) {
            return;
        }
        try {
            strictJarFile.close();
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Could not close APK jar", e);
        }
    }
}
