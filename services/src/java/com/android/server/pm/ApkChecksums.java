package com.android.server.pm;

/* loaded from: classes2.dex */
public class ApkChecksums {
    static final java.lang.String ALGO_MD5 = "MD5";
    static final java.lang.String ALGO_SHA1 = "SHA1";
    static final java.lang.String ALGO_SHA256 = "SHA256";
    static final java.lang.String ALGO_SHA512 = "SHA512";
    private static final java.lang.String DIGESTS_FILE_EXTENSION = ".digests";
    private static final java.lang.String DIGESTS_SIGNATURE_FILE_EXTENSION = ".signature";
    private static final java.security.cert.Certificate[] EMPTY_CERTIFICATE_ARRAY = new java.security.cert.Certificate[0];
    static final int MAX_BUFFER_SIZE = 131072;
    private static final int MAX_SIGNATURE_SIZE_BYTES = 35840;
    static final int MIN_BUFFER_SIZE = 4096;
    private static final long PROCESS_REQUIRED_CHECKSUMS_DELAY_MILLIS = 1000;
    private static final long PROCESS_REQUIRED_CHECKSUMS_TIMEOUT_MILLIS = 86400000;
    static final java.lang.String TAG = "ApkChecksums";

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static class Injector {
        private final com.android.server.pm.ApkChecksums.Injector.Producer<android.content.Context> mContext;
        private final com.android.server.pm.ApkChecksums.Injector.Producer<android.os.Handler> mHandlerProducer;
        private final com.android.server.pm.ApkChecksums.Injector.Producer<android.os.incremental.IncrementalManager> mIncrementalManagerProducer;
        private final com.android.server.pm.ApkChecksums.Injector.Producer<android.content.pm.PackageManagerInternal> mPackageManagerInternalProducer;

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
        interface Producer<T> {
            T produce();
        }

        Injector(com.android.server.pm.ApkChecksums.Injector.Producer<android.content.Context> producer, com.android.server.pm.ApkChecksums.Injector.Producer<android.os.Handler> producer2, com.android.server.pm.ApkChecksums.Injector.Producer<android.os.incremental.IncrementalManager> producer3, com.android.server.pm.ApkChecksums.Injector.Producer<android.content.pm.PackageManagerInternal> producer4) {
            this.mContext = producer;
            this.mHandlerProducer = producer2;
            this.mIncrementalManagerProducer = producer3;
            this.mPackageManagerInternalProducer = producer4;
        }

        public android.content.Context getContext() {
            return this.mContext.produce();
        }

        public android.os.Handler getHandler() {
            return this.mHandlerProducer.produce();
        }

        public android.os.incremental.IncrementalManager getIncrementalManager() {
            return this.mIncrementalManagerProducer.produce();
        }

        public android.content.pm.PackageManagerInternal getPackageManagerInternal() {
            return this.mPackageManagerInternalProducer.produce();
        }
    }

    public static java.lang.String buildDigestsPathForApk(java.lang.String str) {
        if (!android.content.pm.parsing.ApkLiteParseUtils.isApkPath(str)) {
            throw new java.lang.IllegalStateException("Code path is not an apk " + str);
        }
        return str.substring(0, str.length() - ".apk".length()) + DIGESTS_FILE_EXTENSION;
    }

    public static java.lang.String buildSignaturePathForDigests(java.lang.String str) {
        return str + DIGESTS_SIGNATURE_FILE_EXTENSION;
    }

    public static boolean isDigestOrDigestSignatureFile(java.io.File file) {
        java.lang.String name = file.getName();
        return name.endsWith(DIGESTS_FILE_EXTENSION) || name.endsWith(DIGESTS_SIGNATURE_FILE_EXTENSION);
    }

    public static java.io.File findDigestsForFile(java.io.File file) {
        java.io.File file2 = new java.io.File(buildDigestsPathForApk(file.getAbsolutePath()));
        if (file2.exists()) {
            return file2;
        }
        return null;
    }

    public static java.io.File findSignatureForDigests(java.io.File file) {
        java.io.File file2 = new java.io.File(buildSignaturePathForDigests(file.getAbsolutePath()));
        if (file2.exists()) {
            return file2;
        }
        return null;
    }

    public static void writeChecksums(java.io.OutputStream outputStream, android.content.pm.Checksum[] checksumArr) throws java.io.IOException {
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(outputStream);
        try {
            for (android.content.pm.Checksum checksum : checksumArr) {
                android.content.pm.Checksum.writeToStream(dataOutputStream, checksum);
            }
            dataOutputStream.close();
        } catch (java.lang.Throwable th) {
            try {
                dataOutputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static android.content.pm.Checksum[] readChecksums(java.io.File file) throws java.io.IOException {
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
        try {
            android.content.pm.Checksum[] readChecksums = readChecksums(fileInputStream);
            fileInputStream.close();
            return readChecksums;
        } catch (java.lang.Throwable th) {
            try {
                fileInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static android.content.pm.Checksum[] readChecksums(java.io.InputStream inputStream) throws java.io.IOException {
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(inputStream);
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i = 0; i < 100; i++) {
                try {
                    arrayList.add(android.content.pm.Checksum.readFromStream(dataInputStream));
                } catch (java.io.EOFException e) {
                }
            }
            android.content.pm.Checksum[] checksumArr = (android.content.pm.Checksum[]) arrayList.toArray(new android.content.pm.Checksum[arrayList.size()]);
            dataInputStream.close();
            return checksumArr;
        } catch (java.lang.Throwable th) {
            try {
                dataInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @android.annotation.NonNull
    public static java.security.cert.Certificate[] verifySignature(android.content.pm.Checksum[] checksumArr, byte[] bArr) throws java.security.NoSuchAlgorithmException, java.io.IOException, java.security.SignatureException {
        if (bArr == null || bArr.length > MAX_SIGNATURE_SIZE_BYTES) {
            throw new java.security.SignatureException("Invalid signature");
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            writeChecksums(byteArrayOutputStream, checksumArr);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            sun.security.pkcs.PKCS7 pkcs7 = new sun.security.pkcs.PKCS7(bArr);
            java.security.cert.X509Certificate[] certificates = pkcs7.getCertificates();
            if (certificates == null || certificates.length == 0) {
                throw new java.security.SignatureException("Signature missing certificates");
            }
            sun.security.pkcs.SignerInfo[] verify = pkcs7.verify(byteArray);
            if (verify == null || verify.length == 0) {
                throw new java.security.SignatureException("Verification failed");
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(verify.length);
            for (sun.security.pkcs.SignerInfo signerInfo : verify) {
                java.util.ArrayList certificateChain = signerInfo.getCertificateChain(pkcs7);
                if (certificateChain == null) {
                    throw new java.security.SignatureException("Verification passed, but certification chain is empty.");
                }
                arrayList.addAll(certificateChain);
            }
            return (java.security.cert.Certificate[]) arrayList.toArray(new java.security.cert.Certificate[arrayList.size()]);
        } catch (java.lang.Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static void getChecksums(java.util.List<android.util.Pair<java.lang.String, java.io.File>> list, int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.security.cert.Certificate[] certificateArr, @android.annotation.NonNull android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener, @android.annotation.NonNull com.android.server.pm.ApkChecksums.Injector injector) {
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        int size = list.size();
        for (int i3 = 0; i3 < size; i3++) {
            java.lang.String str2 = (java.lang.String) list.get(i3).first;
            java.io.File file = (java.io.File) list.get(i3).second;
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            arrayList.add(arrayMap);
            try {
                getAvailableApkChecksums(str2, file, i | i2, str, certificateArr, arrayMap, injector);
            } catch (java.lang.Throwable th) {
                android.util.Slog.e(TAG, "Preferred checksum calculation error", th);
            }
        }
        processRequiredChecksums(list, arrayList, i2, iOnChecksumsReadyListener, injector, android.os.SystemClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void processRequiredChecksums(final java.util.List<android.util.Pair<java.lang.String, java.io.File>> list, final java.util.List<java.util.Map<java.lang.Integer, android.content.pm.ApkChecksum>> list2, final int i, @android.annotation.NonNull final android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener, @android.annotation.NonNull final com.android.server.pm.ApkChecksums.Injector injector, final long j) {
        java.util.List<android.util.Pair<java.lang.String, java.io.File>> list3 = list;
        boolean z = android.os.SystemClock.uptimeMillis() - j >= 86400000;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = list.size();
        int i2 = 0;
        while (i2 < size) {
            java.lang.String str = (java.lang.String) list3.get(i2).first;
            java.io.File file = (java.io.File) list3.get(i2).second;
            java.util.Map<java.lang.Integer, android.content.pm.ApkChecksum> map = list2.get(i2);
            if (!z || i != 0) {
                try {
                    if (needToWait(file, i, map, injector)) {
                        injector.getHandler().postDelayed(new java.lang.Runnable() { // from class: com.android.server.pm.ApkChecksums$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.pm.ApkChecksums.processRequiredChecksums(list, list2, i, iOnChecksumsReadyListener, injector, j);
                            }
                        }, 1000L);
                        return;
                    }
                    getRequiredApkChecksums(str, file, i, map);
                } catch (java.lang.Throwable th) {
                    android.util.Slog.e(TAG, "Required checksum calculation error", th);
                }
            }
            arrayList.addAll(map.values());
            i2++;
            list3 = list;
        }
        try {
            iOnChecksumsReadyListener.onChecksumsReady(arrayList);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, e);
        }
    }

    private static void getAvailableApkChecksums(java.lang.String str, java.io.File file, int i, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.security.cert.Certificate[] certificateArr, java.util.Map<java.lang.Integer, android.content.pm.ApkChecksum> map, @android.annotation.NonNull com.android.server.pm.ApkChecksums.Injector injector) {
        java.util.Map<java.lang.Integer, android.content.pm.ApkChecksum> extractHashFromV2V3Signature;
        android.content.pm.ApkChecksum extractHashFromFS;
        if (!file.exists()) {
            return;
        }
        java.lang.String absolutePath = file.getAbsolutePath();
        if (isRequired(1, i, map) && (extractHashFromFS = extractHashFromFS(str, absolutePath)) != null) {
            map.put(java.lang.Integer.valueOf(extractHashFromFS.getType()), extractHashFromFS);
        }
        if ((isRequired(32, i, map) || isRequired(64, i, map)) && (extractHashFromV2V3Signature = extractHashFromV2V3Signature(str, absolutePath, i)) != null) {
            map.putAll(extractHashFromV2V3Signature);
        }
        getInstallerChecksums(str, file, i, str2, certificateArr, map, injector);
    }

    private static void getInstallerChecksums(java.lang.String str, java.io.File file, int i, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.security.cert.Certificate[] certificateArr, java.util.Map<java.lang.Integer, android.content.pm.ApkChecksum> map, @android.annotation.NonNull com.android.server.pm.ApkChecksums.Injector injector) {
        java.io.File findDigestsForFile;
        android.content.pm.Checksum[] readChecksums;
        int i2;
        android.content.pm.Signature[] signatures;
        android.content.pm.Signature[] pastSigningCertificates;
        if (com.android.server.pm.PackageManagerServiceUtils.isInstalledByAdb(str2)) {
            return;
        }
        if ((certificateArr != null && certificateArr.length == 0) || (findDigestsForFile = findDigestsForFile(file)) == null) {
            return;
        }
        java.io.File findSignatureForDigests = findSignatureForDigests(findDigestsForFile);
        try {
            readChecksums = readChecksums(findDigestsForFile);
            if (findSignatureForDigests != null) {
                java.security.cert.Certificate[] verifySignature = verifySignature(readChecksums, java.nio.file.Files.readAllBytes(findSignatureForDigests.toPath()));
                if (verifySignature == null || verifySignature.length == 0) {
                    android.util.Slog.e(TAG, "Error validating signature");
                    return;
                }
                signatures = new android.content.pm.Signature[verifySignature.length];
                int length = verifySignature.length;
                for (int i3 = 0; i3 < length; i3++) {
                    signatures[i3] = new android.content.pm.Signature(verifySignature[i3].getEncoded());
                }
                pastSigningCertificates = null;
            } else {
                com.android.server.pm.pkg.AndroidPackage androidPackage = injector.getPackageManagerInternal().getPackage(str2);
                if (androidPackage == null) {
                    android.util.Slog.e(TAG, "Installer package not found.");
                    return;
                } else {
                    signatures = androidPackage.getSigningDetails().getSignatures();
                    pastSigningCertificates = androidPackage.getSigningDetails().getPastSigningCertificates();
                }
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Error reading .digests or .signature", e);
        } catch (java.security.InvalidParameterException | java.security.NoSuchAlgorithmException | java.security.SignatureException e2) {
            android.util.Slog.e(TAG, "Error validating digests. Invalid digests will be removed", e2);
            try {
                java.nio.file.Files.deleteIfExists(findDigestsForFile.toPath());
                if (findSignatureForDigests != null) {
                    java.nio.file.Files.deleteIfExists(findSignatureForDigests.toPath());
                }
            } catch (java.io.IOException e3) {
            }
        } catch (java.security.cert.CertificateEncodingException e4) {
            android.util.Slog.e(TAG, "Error encoding trustedInstallers", e4);
            return;
        }
        if (signatures == null || signatures.length == 0 || signatures[0] == null) {
            android.util.Slog.e(TAG, "Can't obtain certificates.");
            return;
        }
        byte[] byteArray = signatures[0].toByteArray();
        java.util.Set<android.content.pm.Signature> convertToSet = convertToSet(certificateArr);
        if (convertToSet != null && !convertToSet.isEmpty()) {
            android.content.pm.Signature isTrusted = isTrusted(signatures, convertToSet);
            if (isTrusted == null) {
                isTrusted = isTrusted(pastSigningCertificates, convertToSet);
            }
            if (isTrusted == null) {
                return;
            } else {
                byteArray = isTrusted.toByteArray();
            }
        }
        for (android.content.pm.Checksum checksum : readChecksums) {
            android.content.pm.ApkChecksum apkChecksum = map.get(java.lang.Integer.valueOf(checksum.getType()));
            if (apkChecksum != null && !java.util.Arrays.equals(apkChecksum.getValue(), checksum.getValue())) {
                throw new java.security.InvalidParameterException("System digest " + checksum.getType() + " mismatch, can't bind installer-provided digests to the APK.");
            }
        }
        for (android.content.pm.Checksum checksum2 : readChecksums) {
            if (isRequired(checksum2.getType(), i, map)) {
                map.put(java.lang.Integer.valueOf(checksum2.getType()), new android.content.pm.ApkChecksum(str, checksum2, str2, byteArray));
            }
        }
    }

    private static boolean needToWait(java.io.File file, int i, java.util.Map<java.lang.Integer, android.content.pm.ApkChecksum> map, @android.annotation.NonNull com.android.server.pm.ApkChecksums.Injector injector) throws java.io.IOException {
        if (!isRequired(1, i, map) && !isRequired(2, i, map) && !isRequired(4, i, map) && !isRequired(8, i, map) && !isRequired(16, i, map) && !isRequired(32, i, map) && !isRequired(64, i, map)) {
            return false;
        }
        java.lang.String absolutePath = file.getAbsolutePath();
        if (!android.os.incremental.IncrementalManager.isIncrementalPath(absolutePath)) {
            return false;
        }
        android.os.incremental.IncrementalManager incrementalManager = injector.getIncrementalManager();
        if (incrementalManager == null) {
            android.util.Slog.e(TAG, "IncrementalManager is missing.");
            return false;
        }
        if (incrementalManager.openStorage(absolutePath) != null) {
            return !r4.isFileFullyLoaded(absolutePath);
        }
        android.util.Slog.e(TAG, "IncrementalStorage is missing for a path on IncFs: " + absolutePath);
        return false;
    }

    private static void getRequiredApkChecksums(java.lang.String str, java.io.File file, int i, java.util.Map<java.lang.Integer, android.content.pm.ApkChecksum> map) {
        java.lang.String absolutePath = file.getAbsolutePath();
        if (isRequired(1, i, map)) {
            try {
                map.put(1, new android.content.pm.ApkChecksum(str, 1, verityHashForFile(file, android.util.apk.VerityBuilder.generateFsVerityRootHash(absolutePath, (byte[]) null, new android.util.apk.ByteBufferFactory() { // from class: com.android.server.pm.ApkChecksums.1
                    public java.nio.ByteBuffer create(int i2) {
                        return java.nio.ByteBuffer.allocate(i2);
                    }
                }))));
            } catch (java.io.IOException | java.security.DigestException | java.security.NoSuchAlgorithmException e) {
                android.util.Slog.e(TAG, "Error calculating WHOLE_MERKLE_ROOT_4K_SHA256", e);
            }
        }
        calculateChecksumIfRequested(map, str, file, i, 2);
        calculateChecksumIfRequested(map, str, file, i, 4);
        calculateChecksumIfRequested(map, str, file, i, 8);
        calculateChecksumIfRequested(map, str, file, i, 16);
        calculatePartialChecksumsIfRequested(map, str, file, i);
    }

    private static boolean isRequired(int i, int i2, java.util.Map<java.lang.Integer, android.content.pm.ApkChecksum> map) {
        return ((i2 & i) == 0 || map.containsKey(java.lang.Integer.valueOf(i))) ? false : true;
    }

    private static java.util.Set<android.content.pm.Signature> convertToSet(@android.annotation.Nullable java.security.cert.Certificate[] certificateArr) throws java.security.cert.CertificateEncodingException {
        if (certificateArr == null) {
            return null;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(certificateArr.length);
        for (java.security.cert.Certificate certificate : certificateArr) {
            arraySet.add(new android.content.pm.Signature(certificate.getEncoded()));
        }
        return arraySet;
    }

    private static android.content.pm.Signature isTrusted(android.content.pm.Signature[] signatureArr, java.util.Set<android.content.pm.Signature> set) {
        if (signatureArr == null) {
            return null;
        }
        for (android.content.pm.Signature signature : signatureArr) {
            if (set.contains(signature)) {
                return signature;
            }
        }
        return null;
    }

    private static android.content.pm.ApkChecksum extractHashFromFS(java.lang.String str, java.lang.String str2) {
        byte[] fsverityDigest;
        if (com.android.internal.security.VerityUtils.hasFsverity(str2) && (fsverityDigest = com.android.internal.security.VerityUtils.getFsverityDigest(str2)) != null) {
            return new android.content.pm.ApkChecksum(str, 1, fsverityDigest);
        }
        try {
            byte[] bArr = (byte[]) android.util.apk.ApkSignatureSchemeV4Verifier.extractCertificates(str2).contentDigests.getOrDefault(3, null);
            if (bArr != null) {
                return new android.content.pm.ApkChecksum(str, 1, verityHashForFile(new java.io.File(str2), bArr));
            }
        } catch (android.util.apk.SignatureNotFoundException e) {
        } catch (java.lang.SecurityException | java.security.SignatureException e2) {
            android.util.Slog.e(TAG, "V4 signature error", e2);
        }
        return null;
    }

    static byte[] verityHashForFile(java.io.File file, byte[] bArr) {
        try {
            java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(256);
            allocate.order(java.nio.ByteOrder.LITTLE_ENDIAN);
            allocate.put((byte) 1);
            allocate.put((byte) 1);
            allocate.put((byte) 12);
            allocate.put((byte) 0);
            allocate.putInt(0);
            allocate.putLong(file.length());
            allocate.put(bArr);
            for (int i = 0; i < 208; i++) {
                allocate.put((byte) 0);
            }
            allocate.flip();
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance(ALGO_SHA256);
            messageDigest.update(allocate);
            return messageDigest.digest();
        } catch (java.security.NoSuchAlgorithmException e) {
            android.util.Slog.e(TAG, "Device does not support MessageDigest algorithm", e);
            return null;
        }
    }

    private static java.util.Map<java.lang.Integer, android.content.pm.ApkChecksum> extractHashFromV2V3Signature(java.lang.String str, java.lang.String str2, int i) {
        java.util.Map map;
        byte[] bArr;
        byte[] bArr2;
        android.content.pm.parsing.result.ParseResult verifySignaturesInternal = android.util.apk.ApkSignatureVerifier.verifySignaturesInternal(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing(), str2, 2, false);
        if (verifySignaturesInternal.isError()) {
            if (!(verifySignaturesInternal.getException() instanceof android.util.apk.SignatureNotFoundException)) {
                android.util.Slog.e(TAG, "Signature verification error", verifySignaturesInternal.getException());
            }
            map = null;
        } else {
            map = ((android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests) verifySignaturesInternal.getResult()).contentDigests;
        }
        if (map == null) {
            return null;
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        if ((i & 32) != 0 && (bArr2 = (byte[]) map.getOrDefault(1, null)) != null) {
            arrayMap.put(32, new android.content.pm.ApkChecksum(str, 32, bArr2));
        }
        if ((i & 64) != 0 && (bArr = (byte[]) map.getOrDefault(2, null)) != null) {
            arrayMap.put(64, new android.content.pm.ApkChecksum(str, 64, bArr));
        }
        return arrayMap;
    }

    private static java.lang.String getMessageDigestAlgoForChecksumKind(int i) throws java.security.NoSuchAlgorithmException {
        switch (i) {
            case 2:
                return ALGO_MD5;
            case 4:
                return ALGO_SHA1;
            case 8:
                return ALGO_SHA256;
            case 16:
                return ALGO_SHA512;
            default:
                throw new java.security.NoSuchAlgorithmException("Invalid checksum type: " + i);
        }
    }

    private static void calculateChecksumIfRequested(java.util.Map<java.lang.Integer, android.content.pm.ApkChecksum> map, java.lang.String str, java.io.File file, int i, int i2) {
        byte[] apkChecksum;
        if ((i & i2) != 0 && !map.containsKey(java.lang.Integer.valueOf(i2)) && (apkChecksum = getApkChecksum(file, i2)) != null) {
            map.put(java.lang.Integer.valueOf(i2), new android.content.pm.ApkChecksum(str, i2, apkChecksum));
        }
    }

    private static byte[] getApkChecksum(java.io.File file, int i) {
        int max = (int) java.lang.Math.max(4096L, java.lang.Math.min(131072L, file.length()));
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
            try {
                byte[] bArr = new byte[max];
                java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance(getMessageDigestAlgoForChecksumKind(i));
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read != -1) {
                        messageDigest.update(bArr, 0, read);
                    } else {
                        byte[] digest = messageDigest.digest();
                        fileInputStream.close();
                        return digest;
                    }
                }
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Error reading " + file.getAbsolutePath() + " to compute hash.", e);
            return null;
        } catch (java.security.NoSuchAlgorithmException e2) {
            android.util.Slog.e(TAG, "Device does not support MessageDigest algorithm", e2);
            return null;
        }
    }

    private static int[] getContentDigestAlgos(boolean z, boolean z2) {
        if (z && z2) {
            return new int[]{1, 2};
        }
        if (z) {
            return new int[]{1};
        }
        return new int[]{2};
    }

    private static int getChecksumKindForContentDigestAlgo(int i) {
        switch (i) {
            case 1:
                return 32;
            case 2:
                return 64;
            default:
                return -1;
        }
    }

    private static void calculatePartialChecksumsIfRequested(java.util.Map<java.lang.Integer, android.content.pm.ApkChecksum> map, java.lang.String str, java.io.File file, int i) {
        android.util.apk.SignatureInfo signatureInfo;
        boolean z = ((i & 32) == 0 || map.containsKey(32)) ? false : true;
        boolean z2 = ((i & 64) == 0 || map.containsKey(64)) ? false : true;
        if (z || z2) {
            try {
                java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(file, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
                try {
                    try {
                        signatureInfo = android.util.apk.ApkSignatureSchemeV3Verifier.findSignature(randomAccessFile);
                    } catch (java.lang.Throwable th) {
                        try {
                            randomAccessFile.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (android.util.apk.SignatureNotFoundException e) {
                    try {
                        signatureInfo = android.util.apk.ApkSignatureSchemeV2Verifier.findSignature(randomAccessFile);
                    } catch (android.util.apk.SignatureNotFoundException e2) {
                        signatureInfo = null;
                    }
                }
                if (signatureInfo == null) {
                    android.util.Slog.e(TAG, "V2/V3 signatures not found in " + file.getAbsolutePath());
                    randomAccessFile.close();
                    return;
                }
                int[] contentDigestAlgos = getContentDigestAlgos(z, z2);
                byte[][] computeContentDigestsPer1MbChunk = android.util.apk.ApkSigningBlockUtils.computeContentDigestsPer1MbChunk(contentDigestAlgos, randomAccessFile.getFD(), signatureInfo);
                int length = contentDigestAlgos.length;
                for (int i2 = 0; i2 < length; i2++) {
                    int checksumKindForContentDigestAlgo = getChecksumKindForContentDigestAlgo(contentDigestAlgos[i2]);
                    if (checksumKindForContentDigestAlgo != -1) {
                        map.put(java.lang.Integer.valueOf(checksumKindForContentDigestAlgo), new android.content.pm.ApkChecksum(str, checksumKindForContentDigestAlgo, computeContentDigestsPer1MbChunk[i2]));
                    }
                }
                randomAccessFile.close();
            } catch (java.io.IOException | java.security.DigestException e3) {
                android.util.Slog.e(TAG, "Error computing hash.", e3);
            }
        }
    }
}
