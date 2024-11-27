package android.util.apk;

/* loaded from: classes3.dex */
public class ApkSignatureVerifier {
    private static final java.util.concurrent.atomic.AtomicReference<byte[]> sBuffer = new java.util.concurrent.atomic.AtomicReference<>();

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> verify(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, int i) {
        return verifySignatures(parseInput, str, i, true);
    }

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> unsafeGetCertsWithoutVerification(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, int i) {
        return verifySignatures(parseInput, str, i, false);
    }

    private static android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> verifySignatures(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, int i, boolean z) {
        android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> verifySignaturesInternal = verifySignaturesInternal(parseInput, str, i, z);
        if (verifySignaturesInternal.isError()) {
            return parseInput.error(verifySignaturesInternal);
        }
        return parseInput.success(verifySignaturesInternal.getResult().signingDetails);
    }

    public static android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> verifySignaturesInternal(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, int i, boolean z) {
        if (i > 4) {
            return parseInput.error(-103, "No signature found in package of version " + i + " or newer for package " + str);
        }
        try {
            return verifyV4Signature(parseInput, str, i, z);
        } catch (android.util.apk.SignatureNotFoundException e) {
            if (i >= 4) {
                return parseInput.error(-103, "No APK Signature Scheme v4 signature in package " + str, e);
            }
            if (i > 3) {
                return parseInput.error(-103, "No signature found in package of version " + i + " or newer for package " + str);
            }
            return verifyV3AndBelowSignatures(parseInput, str, i, z);
        }
    }

    private static android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> verifyV3AndBelowSignatures(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, int i, boolean z) {
        try {
            return verifyV3Signature(parseInput, str, z);
        } catch (android.util.apk.SignatureNotFoundException e) {
            if (i >= 3) {
                return parseInput.error(-103, "No APK Signature Scheme v3 signature in package " + str, e);
            }
            if (i > 2) {
                return parseInput.error(-103, "No signature found in package of version " + i + " or newer for package " + str);
            }
            try {
                return verifyV2Signature(parseInput, str, z);
            } catch (android.util.apk.SignatureNotFoundException e2) {
                if (i >= 2) {
                    return parseInput.error(-103, "No APK Signature Scheme v2 signature in package " + str, e2);
                }
                if (i > 1) {
                    return parseInput.error(-103, "No signature found in package of version " + i + " or newer for package " + str);
                }
                return verifyV1Signature(parseInput, str, z);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0, types: [java.security.cert.Certificate[][]] */
    private static android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> verifyV4Signature(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, int i, boolean z) throws android.util.apk.SignatureNotFoundException {
        java.util.Map<java.lang.Integer, byte[]> map;
        int i2;
        android.content.pm.Signature[] signatureArr;
        android.os.Trace.traceBegin(262144L, z ? "verifyV4" : "certsOnlyV4");
        try {
            try {
                try {
                    android.util.Pair<android.os.incremental.V4Signature.HashingInfo, android.os.incremental.V4Signature.SigningInfos> extractSignature = android.util.apk.ApkSignatureSchemeV4Verifier.extractSignature(str);
                    android.os.incremental.V4Signature.HashingInfo hashingInfo = extractSignature.first;
                    android.os.incremental.V4Signature.SigningInfos signingInfos = extractSignature.second;
                    android.content.pm.Signature[] signatureArr2 = null;
                    java.security.cert.X509Certificate[][] x509CertificateArr = null;
                    signatureArr2 = null;
                    boolean z2 = true;
                    if (z || signingInfos.signingInfoBlocks.length > 0) {
                        try {
                            android.util.apk.ApkSignatureSchemeV3Verifier.VerifiedSigner unsafeGetCertsWithoutVerification = android.util.apk.ApkSignatureSchemeV3Verifier.unsafeGetCertsWithoutVerification(str);
                            java.util.Map<java.lang.Integer, byte[]> map2 = unsafeGetCertsWithoutVerification.contentDigests;
                            ?? r12 = {unsafeGetCertsWithoutVerification.certs};
                            if (unsafeGetCertsWithoutVerification.por != null) {
                                int size = unsafeGetCertsWithoutVerification.por.certs.size();
                                signatureArr2 = new android.content.pm.Signature[size];
                                for (int i3 = 0; i3 < size; i3++) {
                                    signatureArr2[i3] = new android.content.pm.Signature(unsafeGetCertsWithoutVerification.por.certs.get(i3).getEncoded());
                                    signatureArr2[i3].setFlags(unsafeGetCertsWithoutVerification.por.flagsList.get(i3).intValue());
                                }
                            }
                            int i4 = unsafeGetCertsWithoutVerification.blockId;
                            signatureArr = signatureArr2;
                            x509CertificateArr = r12;
                            i2 = i4;
                            map = map2;
                        } catch (android.util.apk.SignatureNotFoundException e) {
                            try {
                                android.util.apk.ApkSignatureSchemeV2Verifier.VerifiedSigner verify = android.util.apk.ApkSignatureSchemeV2Verifier.verify(str, false);
                                map = verify.contentDigests;
                                i2 = -1;
                                android.content.pm.Signature[] signatureArr3 = signatureArr2;
                                x509CertificateArr = verify.certs;
                                signatureArr = signatureArr3;
                            } catch (android.util.apk.SignatureNotFoundException e2) {
                                throw new java.lang.SecurityException("V4 verification failed to collect V2/V3 certificates from : " + str, e2);
                            }
                        }
                    } else {
                        signatureArr = null;
                        map = null;
                        i2 = -1;
                    }
                    android.util.apk.ApkSignatureSchemeV4Verifier.VerifiedSigner verify2 = android.util.apk.ApkSignatureSchemeV4Verifier.verify(str, hashingInfo, signingInfos, i2);
                    android.content.pm.Signature[] convertToSignatures = convertToSignatures(new java.security.cert.Certificate[][]{verify2.certs});
                    if (z) {
                        android.content.pm.Signature[] convertToSignatures2 = convertToSignatures(x509CertificateArr);
                        if (convertToSignatures2.length != convertToSignatures.length) {
                            throw new java.lang.SecurityException("Invalid number of certificates: " + convertToSignatures2.length);
                        }
                        int length = convertToSignatures.length;
                        for (int i5 = 0; i5 < length; i5++) {
                            if (!convertToSignatures2[i5].equals(convertToSignatures[i5])) {
                                throw new java.lang.SecurityException("V4 signature certificate does not match V2/V3");
                            }
                        }
                        java.util.Iterator<byte[]> it = map.values().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z2 = false;
                                break;
                            }
                            if (com.android.internal.util.ArrayUtils.equals(verify2.apkDigest, it.next(), verify2.apkDigest.length)) {
                                break;
                            }
                        }
                        if (!z2) {
                            throw new java.lang.SecurityException("APK digest in V4 signature does not match V2/V3");
                        }
                    }
                    android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> success = parseInput.success(new android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests(new android.content.pm.SigningDetails(convertToSignatures, 4, signatureArr), verify2.contentDigests));
                    android.os.Trace.traceEnd(262144L);
                    return success;
                } catch (java.lang.Exception e3) {
                    android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> error = parseInput.error(-103, "Failed to collect certificates from " + str + " using APK Signature Scheme v4", e3);
                    android.os.Trace.traceEnd(262144L);
                    return error;
                }
            } catch (android.util.apk.SignatureNotFoundException e4) {
                throw e4;
            }
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(262144L);
            throw th;
        }
    }

    private static android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> verifyV3Signature(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, boolean z) throws android.util.apk.SignatureNotFoundException {
        android.content.pm.Signature[] signatureArr;
        android.os.Trace.traceBegin(262144L, z ? "verifyV3" : "certsOnlyV3");
        try {
            try {
                android.util.apk.ApkSignatureSchemeV3Verifier.VerifiedSigner verify = z ? android.util.apk.ApkSignatureSchemeV3Verifier.verify(str) : android.util.apk.ApkSignatureSchemeV3Verifier.unsafeGetCertsWithoutVerification(str);
                android.content.pm.Signature[] convertToSignatures = convertToSignatures(new java.security.cert.Certificate[][]{verify.certs});
                if (verify.por != null) {
                    int size = verify.por.certs.size();
                    signatureArr = new android.content.pm.Signature[size];
                    for (int i = 0; i < size; i++) {
                        signatureArr[i] = new android.content.pm.Signature(verify.por.certs.get(i).getEncoded());
                        signatureArr[i].setFlags(verify.por.flagsList.get(i).intValue());
                    }
                } else {
                    signatureArr = null;
                }
                android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> success = parseInput.success(new android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests(new android.content.pm.SigningDetails(convertToSignatures, 3, signatureArr), verify.contentDigests));
                android.os.Trace.traceEnd(262144L);
                return success;
            } catch (android.util.apk.SignatureNotFoundException e) {
                throw e;
            } catch (java.lang.Exception e2) {
                android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> error = parseInput.error(-103, "Failed to collect certificates from " + str + " using APK Signature Scheme v3", e2);
                android.os.Trace.traceEnd(262144L);
                return error;
            }
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(262144L);
            throw th;
        }
    }

    private static android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> verifyV2Signature(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, boolean z) throws android.util.apk.SignatureNotFoundException {
        android.os.Trace.traceBegin(262144L, z ? "verifyV2" : "certsOnlyV2");
        try {
            try {
                android.util.apk.ApkSignatureSchemeV2Verifier.VerifiedSigner verify = android.util.apk.ApkSignatureSchemeV2Verifier.verify(str, z);
                android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> success = parseInput.success(new android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests(new android.content.pm.SigningDetails(convertToSignatures(verify.certs), 2), verify.contentDigests));
                android.os.Trace.traceEnd(262144L);
                return success;
            } catch (android.util.apk.SignatureNotFoundException e) {
                throw e;
            } catch (java.lang.Exception e2) {
                android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> error = parseInput.error(-103, "Failed to collect certificates from " + str + " using APK Signature Scheme v2", e2);
                android.os.Trace.traceEnd(262144L);
                return error;
            }
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(262144L);
            throw th;
        }
    }

    private static android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> verifyV1Signature(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, boolean z) {
        android.util.jar.StrictJarFile strictJarFile;
        android.util.jar.StrictJarFile strictJarFile2;
        android.util.jar.StrictJarFile strictJarFile3;
        android.util.jar.StrictJarFile strictJarFile4;
        try {
            try {
                android.os.Trace.traceBegin(262144L, "strictJarFileCtor");
                strictJarFile4 = new android.util.jar.StrictJarFile(str, true, z);
            } catch (java.io.IOException | java.lang.RuntimeException e) {
                e = e;
                strictJarFile3 = null;
            } catch (java.security.GeneralSecurityException e2) {
                e = e2;
                strictJarFile2 = null;
            } catch (java.lang.Throwable th) {
                th = th;
                strictJarFile = null;
            }
            try {
                try {
                    java.util.ArrayList<java.util.zip.ZipEntry> arrayList = new java.util.ArrayList();
                    java.util.zip.ZipEntry findEntry = strictJarFile4.findEntry("AndroidManifest.xml");
                    if (findEntry == null) {
                        android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> error = parseInput.error(-101, "Package " + str + " has no manifest");
                        android.os.Trace.traceEnd(262144L);
                        closeQuietly(strictJarFile4);
                        return error;
                    }
                    android.content.pm.parsing.result.ParseResult<java.security.cert.Certificate[][]> loadCertificates = loadCertificates(parseInput, strictJarFile4, findEntry);
                    if (loadCertificates.isError()) {
                        android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> error2 = parseInput.error((android.content.pm.parsing.result.ParseResult<?>) loadCertificates);
                        android.os.Trace.traceEnd(262144L);
                        closeQuietly(strictJarFile4);
                        return error2;
                    }
                    java.security.cert.Certificate[][] result = loadCertificates.getResult();
                    if (com.android.internal.util.ArrayUtils.isEmpty(result)) {
                        android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> error3 = parseInput.error(-103, "Package " + str + " has no certificates at entry AndroidManifest.xml");
                        android.os.Trace.traceEnd(262144L);
                        closeQuietly(strictJarFile4);
                        return error3;
                    }
                    android.content.pm.Signature[] convertToSignatures = convertToSignatures(result);
                    if (z) {
                        java.util.Iterator<java.util.zip.ZipEntry> it = strictJarFile4.iterator();
                        while (it.hasNext()) {
                            java.util.zip.ZipEntry next = it.next();
                            if (!next.isDirectory()) {
                                java.lang.String name = next.getName();
                                if (!name.startsWith("META-INF/") && !name.equals("AndroidManifest.xml")) {
                                    arrayList.add(next);
                                }
                            }
                        }
                        for (java.util.zip.ZipEntry zipEntry : arrayList) {
                            android.content.pm.parsing.result.ParseResult<java.security.cert.Certificate[][]> loadCertificates2 = loadCertificates(parseInput, strictJarFile4, zipEntry);
                            if (loadCertificates2.isError()) {
                                android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> error4 = parseInput.error((android.content.pm.parsing.result.ParseResult<?>) loadCertificates2);
                                android.os.Trace.traceEnd(262144L);
                                closeQuietly(strictJarFile4);
                                return error4;
                            }
                            java.security.cert.Certificate[][] result2 = loadCertificates2.getResult();
                            if (com.android.internal.util.ArrayUtils.isEmpty(result2)) {
                                android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> error5 = parseInput.error(-103, "Package " + str + " has no certificates at entry " + zipEntry.getName());
                                android.os.Trace.traceEnd(262144L);
                                closeQuietly(strictJarFile4);
                                return error5;
                            }
                            if (!java.util.Arrays.equals(convertToSignatures, convertToSignatures(result2))) {
                                android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> error6 = parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES, "Package " + str + " has mismatched certificates at entry " + zipEntry.getName());
                                android.os.Trace.traceEnd(262144L);
                                closeQuietly(strictJarFile4);
                                return error6;
                            }
                        }
                    }
                    android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> success = parseInput.success(new android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests(new android.content.pm.SigningDetails(convertToSignatures, 1), null));
                    android.os.Trace.traceEnd(262144L);
                    closeQuietly(strictJarFile4);
                    return success;
                } catch (java.security.GeneralSecurityException e3) {
                    e = e3;
                    strictJarFile2 = strictJarFile4;
                    android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> error7 = parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING, "Failed to collect certificates from " + str, e);
                    android.os.Trace.traceEnd(262144L);
                    closeQuietly(strictJarFile2);
                    return error7;
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    strictJarFile = strictJarFile4;
                    android.os.Trace.traceEnd(262144L);
                    closeQuietly(strictJarFile);
                    throw th;
                }
            } catch (java.io.IOException | java.lang.RuntimeException e4) {
                e = e4;
                strictJarFile3 = strictJarFile4;
                android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> error8 = parseInput.error(-103, "Failed to collect certificates from " + str, e);
                android.os.Trace.traceEnd(262144L);
                closeQuietly(strictJarFile3);
                return error8;
            }
        } catch (java.lang.Throwable th3) {
            th = th3;
        }
    }

    private static android.content.pm.parsing.result.ParseResult<java.security.cert.Certificate[][]> loadCertificates(android.content.pm.parsing.result.ParseInput parseInput, android.util.jar.StrictJarFile strictJarFile, java.util.zip.ZipEntry zipEntry) {
        java.io.InputStream inputStream = null;
        try {
            inputStream = strictJarFile.getInputStream(zipEntry);
            readFullyIgnoringContents(inputStream);
            return parseInput.success(strictJarFile.getCertificateChains(zipEntry));
        } catch (java.io.IOException | java.lang.RuntimeException e) {
            return parseInput.error(-102, "Failed reading " + zipEntry.getName() + " in " + strictJarFile, e);
        } finally {
            libcore.io.IoUtils.closeQuietly(inputStream);
        }
    }

    private static void readFullyIgnoringContents(java.io.InputStream inputStream) throws java.io.IOException {
        byte[] andSet = sBuffer.getAndSet(null);
        if (andSet == null) {
            andSet = new byte[4096];
        }
        while (inputStream.read(andSet, 0, andSet.length) != -1) {
        }
        sBuffer.set(andSet);
    }

    private static android.content.pm.Signature[] convertToSignatures(java.security.cert.Certificate[][] certificateArr) throws java.security.cert.CertificateEncodingException {
        android.content.pm.Signature[] signatureArr = new android.content.pm.Signature[certificateArr.length];
        for (int i = 0; i < certificateArr.length; i++) {
            signatureArr[i] = new android.content.pm.Signature(certificateArr[i]);
        }
        return signatureArr;
    }

    private static void closeQuietly(android.util.jar.StrictJarFile strictJarFile) {
        if (strictJarFile != null) {
            try {
                strictJarFile.close();
            } catch (java.lang.Exception e) {
            }
        }
    }

    public static int getMinimumSignatureSchemeVersionForTargetSdk(int i) {
        if (i >= 30) {
            return 2;
        }
        return 1;
    }

    public static class Result {
        public final java.security.cert.Certificate[][] certs;
        public final int signatureSchemeVersion;
        public final android.content.pm.Signature[] sigs;

        public Result(java.security.cert.Certificate[][] certificateArr, android.content.pm.Signature[] signatureArr, int i) {
            this.certs = certificateArr;
            this.sigs = signatureArr;
            this.signatureSchemeVersion = i;
        }
    }

    public static byte[] getVerityRootHash(java.lang.String str) throws java.io.IOException, java.lang.SecurityException {
        try {
            return android.util.apk.ApkSignatureSchemeV3Verifier.getVerityRootHash(str);
        } catch (android.util.apk.SignatureNotFoundException e) {
            try {
                return android.util.apk.ApkSignatureSchemeV2Verifier.getVerityRootHash(str);
            } catch (android.util.apk.SignatureNotFoundException e2) {
                return null;
            }
        }
    }

    public static byte[] generateApkVerity(java.lang.String str, android.util.apk.ByteBufferFactory byteBufferFactory) throws java.io.IOException, android.util.apk.SignatureNotFoundException, java.lang.SecurityException, java.security.DigestException, java.security.NoSuchAlgorithmException {
        try {
            return android.util.apk.ApkSignatureSchemeV3Verifier.generateApkVerity(str, byteBufferFactory);
        } catch (android.util.apk.SignatureNotFoundException e) {
            return android.util.apk.ApkSignatureSchemeV2Verifier.generateApkVerity(str, byteBufferFactory);
        }
    }

    public static class SigningDetailsWithDigests {
        public final java.util.Map<java.lang.Integer, byte[]> contentDigests;
        public final android.content.pm.SigningDetails signingDetails;

        SigningDetailsWithDigests(android.content.pm.SigningDetails signingDetails, java.util.Map<java.lang.Integer, byte[]> map) {
            this.signingDetails = signingDetails;
            this.contentDigests = map;
        }
    }
}
