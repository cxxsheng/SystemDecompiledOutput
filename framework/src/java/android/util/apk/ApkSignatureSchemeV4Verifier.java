package android.util.apk;

/* loaded from: classes3.dex */
public class ApkSignatureSchemeV4Verifier {
    static final int APK_SIGNATURE_SCHEME_DEFAULT = -1;

    public static android.util.apk.ApkSignatureSchemeV4Verifier.VerifiedSigner extractCertificates(java.lang.String str) throws android.util.apk.SignatureNotFoundException, java.security.SignatureException, java.lang.SecurityException {
        android.util.Pair<android.os.incremental.V4Signature.HashingInfo, android.os.incremental.V4Signature.SigningInfos> extractSignature = extractSignature(str);
        return verify(str, extractSignature.first, extractSignature.second, -1);
    }

    public static android.util.Pair<android.os.incremental.V4Signature.HashingInfo, android.os.incremental.V4Signature.SigningInfos> extractSignature(java.lang.String str) throws android.util.apk.SignatureNotFoundException, java.security.SignatureException {
        android.os.incremental.V4Signature readFrom;
        boolean z;
        try {
            try {
                try {
                    java.io.File file = new java.io.File(str);
                    byte[] unsafeGetFileSignature = android.os.incremental.IncrementalManager.unsafeGetFileSignature(file.getAbsolutePath());
                    if (unsafeGetFileSignature != null && unsafeGetFileSignature.length > 0) {
                        readFrom = android.os.incremental.V4Signature.readFrom(unsafeGetFileSignature);
                        z = false;
                    } else if (android.security.Flags.extendVbChainToUpdatedApk()) {
                        try {
                            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(new java.io.File(file.getAbsolutePath() + android.os.incremental.V4Signature.EXT).getAbsolutePath());
                            try {
                                readFrom = android.os.incremental.V4Signature.readFrom(fileInputStream);
                                fileInputStream.close();
                                z = true;
                            } catch (java.lang.Throwable th) {
                                try {
                                    fileInputStream.close();
                                } catch (java.lang.Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        } catch (java.io.IOException e) {
                            throw new android.util.apk.SignatureNotFoundException("Failed to obtain signature bytes from .idsig");
                        }
                    } else {
                        throw new android.util.apk.SignatureNotFoundException("Failed to obtain signature bytes from IncFS.");
                    }
                    if (!readFrom.isVersionSupported()) {
                        throw new java.lang.SecurityException("v4 signature version " + readFrom.version + " is not supported");
                    }
                    android.os.incremental.V4Signature.HashingInfo fromByteArray = android.os.incremental.V4Signature.HashingInfo.fromByteArray(readFrom.hashingInfo);
                    android.os.incremental.V4Signature.SigningInfos fromByteArray2 = android.os.incremental.V4Signature.SigningInfos.fromByteArray(readFrom.signingInfos);
                    if (z) {
                        byte[] fsverityDigest = com.android.internal.security.VerityUtils.getFsverityDigest(file.getAbsolutePath());
                        if (fsverityDigest == null) {
                            throw new java.lang.SecurityException("The APK does not have fs-verity");
                        }
                        if (!java.util.Arrays.equals(com.android.internal.security.VerityUtils.generateFsVerityDigest(file.length(), fromByteArray), fsverityDigest)) {
                            throw new java.security.SignatureException("Actual digest does not match the v4 signature");
                        }
                    }
                    return android.util.Pair.create(fromByteArray, fromByteArray2);
                } catch (java.security.DigestException | java.security.NoSuchAlgorithmException e2) {
                    throw new java.lang.SecurityException("Failed to calculate the digest", e2);
                }
            } catch (java.io.IOException e3) {
                throw new android.util.apk.SignatureNotFoundException("Failed to read V4 signature.", e3);
            }
        } catch (java.io.EOFException e4) {
            throw new java.security.SignatureException("V4 signature is invalid.", e4);
        }
    }

    public static android.util.apk.ApkSignatureSchemeV4Verifier.VerifiedSigner verify(java.lang.String str, android.os.incremental.V4Signature.HashingInfo hashingInfo, android.os.incremental.V4Signature.SigningInfos signingInfos, int i) throws android.util.apk.SignatureNotFoundException, java.lang.SecurityException {
        android.os.incremental.V4Signature.SigningInfo findSigningInfoForBlockId = findSigningInfoForBlockId(signingInfos, i);
        android.util.Pair<java.security.cert.Certificate, byte[]> verifySigner = verifySigner(findSigningInfoForBlockId, android.os.incremental.V4Signature.getSignedData(new java.io.File(str).length(), hashingInfo, findSigningInfoForBlockId));
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        arrayMap.put(java.lang.Integer.valueOf(convertToContentDigestType(hashingInfo.hashAlgorithm)), hashingInfo.rawRootHash);
        return new android.util.apk.ApkSignatureSchemeV4Verifier.VerifiedSigner(new java.security.cert.Certificate[]{verifySigner.first}, verifySigner.second, arrayMap);
    }

    private static android.os.incremental.V4Signature.SigningInfo findSigningInfoForBlockId(android.os.incremental.V4Signature.SigningInfos signingInfos, int i) throws android.util.apk.SignatureNotFoundException {
        if (i == -1 || i == -262969152) {
            return signingInfos.signingInfo;
        }
        for (android.os.incremental.V4Signature.SigningInfoBlock signingInfoBlock : signingInfos.signingInfoBlocks) {
            if (i == signingInfoBlock.blockId) {
                try {
                    return android.os.incremental.V4Signature.SigningInfo.fromByteArray(signingInfoBlock.signingInfo);
                } catch (java.io.IOException e) {
                    throw new java.lang.SecurityException("Failed to read V4 signature block: " + signingInfoBlock.blockId, e);
                }
            }
        }
        throw new java.lang.SecurityException("Failed to find V4 signature block corresponding to V3 blockId: " + i);
    }

    private static android.util.Pair<java.security.cert.Certificate, byte[]> verifySigner(android.os.incremental.V4Signature.SigningInfo signingInfo, byte[] bArr) throws java.lang.SecurityException {
        if (!android.util.apk.ApkSigningBlockUtils.isSupportedSignatureAlgorithm(signingInfo.signatureAlgorithmId)) {
            throw new java.lang.SecurityException("No supported signatures found");
        }
        int i = signingInfo.signatureAlgorithmId;
        byte[] bArr2 = signingInfo.signature;
        byte[] bArr3 = signingInfo.publicKey;
        byte[] bArr4 = signingInfo.certificate;
        java.lang.String signatureAlgorithmJcaKeyAlgorithm = android.util.apk.ApkSigningBlockUtils.getSignatureAlgorithmJcaKeyAlgorithm(i);
        android.util.Pair<java.lang.String, ? extends java.security.spec.AlgorithmParameterSpec> signatureAlgorithmJcaSignatureAlgorithm = android.util.apk.ApkSigningBlockUtils.getSignatureAlgorithmJcaSignatureAlgorithm(i);
        java.lang.String str = signatureAlgorithmJcaSignatureAlgorithm.first;
        java.security.spec.AlgorithmParameterSpec algorithmParameterSpec = (java.security.spec.AlgorithmParameterSpec) signatureAlgorithmJcaSignatureAlgorithm.second;
        try {
            java.security.PublicKey generatePublic = java.security.KeyFactory.getInstance(signatureAlgorithmJcaKeyAlgorithm).generatePublic(new java.security.spec.X509EncodedKeySpec(bArr3));
            java.security.Signature signature = java.security.Signature.getInstance(str);
            signature.initVerify(generatePublic);
            if (algorithmParameterSpec != null) {
                signature.setParameter(algorithmParameterSpec);
            }
            signature.update(bArr);
            if (!signature.verify(bArr2)) {
                throw new java.lang.SecurityException(str + " signature did not verify");
            }
            try {
                try {
                    android.util.apk.VerbatimX509Certificate verbatimX509Certificate = new android.util.apk.VerbatimX509Certificate((java.security.cert.X509Certificate) java.security.cert.CertificateFactory.getInstance("X.509").generateCertificate(new java.io.ByteArrayInputStream(bArr4)), bArr4);
                    if (!java.util.Arrays.equals(bArr3, verbatimX509Certificate.getPublicKey().getEncoded())) {
                        throw new java.lang.SecurityException("Public key mismatch between certificate and signature record");
                    }
                    return android.util.Pair.create(verbatimX509Certificate, signingInfo.apkDigest);
                } catch (java.security.cert.CertificateException e) {
                    throw new java.lang.SecurityException("Failed to decode certificate", e);
                }
            } catch (java.security.cert.CertificateException e2) {
                throw new java.lang.RuntimeException("Failed to obtain X.509 CertificateFactory", e2);
            }
        } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | java.security.SignatureException | java.security.spec.InvalidKeySpecException e3) {
            throw new java.lang.SecurityException("Failed to verify " + str + " signature", e3);
        }
    }

    private static int convertToContentDigestType(int i) throws java.lang.SecurityException {
        if (i == 1) {
            return 3;
        }
        throw new java.lang.SecurityException("Unsupported hashAlgorithm: " + i);
    }

    public static class VerifiedSigner {
        public final byte[] apkDigest;
        public final java.security.cert.Certificate[] certs;
        public final java.util.Map<java.lang.Integer, byte[]> contentDigests;

        public VerifiedSigner(java.security.cert.Certificate[] certificateArr, byte[] bArr, java.util.Map<java.lang.Integer, byte[]> map) {
            this.certs = certificateArr;
            this.apkDigest = bArr;
            this.contentDigests = map;
        }
    }
}
