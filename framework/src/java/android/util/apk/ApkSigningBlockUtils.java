package android.util.apk;

/* loaded from: classes3.dex */
public final class ApkSigningBlockUtils {
    private static final long APK_SIG_BLOCK_MAGIC_HI = 3617552046287187010L;
    private static final long APK_SIG_BLOCK_MAGIC_LO = 2334950737559900225L;
    private static final int APK_SIG_BLOCK_MIN_SIZE = 32;
    private static final int CHUNK_SIZE_BYTES = 1048576;
    public static final int CONTENT_DIGEST_CHUNKED_SHA256 = 1;
    public static final int CONTENT_DIGEST_CHUNKED_SHA512 = 2;
    public static final int CONTENT_DIGEST_SHA256 = 4;
    public static final int CONTENT_DIGEST_VERITY_CHUNKED_SHA256 = 3;
    static final int SIGNATURE_DSA_WITH_SHA256 = 769;
    static final int SIGNATURE_ECDSA_WITH_SHA256 = 513;
    static final int SIGNATURE_ECDSA_WITH_SHA512 = 514;
    static final int SIGNATURE_RSA_PKCS1_V1_5_WITH_SHA256 = 259;
    static final int SIGNATURE_RSA_PKCS1_V1_5_WITH_SHA512 = 260;
    static final int SIGNATURE_RSA_PSS_WITH_SHA256 = 257;
    static final int SIGNATURE_RSA_PSS_WITH_SHA512 = 258;
    static final int SIGNATURE_VERITY_DSA_WITH_SHA256 = 1061;
    static final int SIGNATURE_VERITY_ECDSA_WITH_SHA256 = 1059;
    static final int SIGNATURE_VERITY_RSA_PKCS1_V1_5_WITH_SHA256 = 1057;

    private ApkSigningBlockUtils() {
    }

    static android.util.apk.SignatureInfo findSignature(java.io.RandomAccessFile randomAccessFile, int i) throws java.io.IOException, android.util.apk.SignatureNotFoundException {
        android.util.Pair<java.nio.ByteBuffer, java.lang.Long> eocd = getEocd(randomAccessFile);
        java.nio.ByteBuffer byteBuffer = eocd.first;
        long longValue = eocd.second.longValue();
        if (android.util.apk.ZipUtils.isZip64EndOfCentralDirectoryLocatorPresent(randomAccessFile, longValue)) {
            throw new android.util.apk.SignatureNotFoundException("ZIP64 APK not supported");
        }
        long centralDirOffset = getCentralDirOffset(byteBuffer, longValue);
        android.util.Pair<java.nio.ByteBuffer, java.lang.Long> findApkSigningBlock = findApkSigningBlock(randomAccessFile, centralDirOffset);
        java.nio.ByteBuffer byteBuffer2 = findApkSigningBlock.first;
        return new android.util.apk.SignatureInfo(findApkSignatureSchemeBlock(byteBuffer2, i), findApkSigningBlock.second.longValue(), centralDirOffset, longValue, byteBuffer);
    }

    static void verifyIntegrity(java.util.Map<java.lang.Integer, byte[]> map, java.io.RandomAccessFile randomAccessFile, android.util.apk.SignatureInfo signatureInfo) throws java.lang.SecurityException {
        if (map.isEmpty()) {
            throw new java.lang.SecurityException("No digests provided");
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        boolean z = true;
        if (map.containsKey(1)) {
            arrayMap.put(1, map.get(1));
        }
        if (map.containsKey(2)) {
            arrayMap.put(2, map.get(2));
        }
        boolean z2 = false;
        if (!arrayMap.isEmpty()) {
            try {
                verifyIntegrityFor1MbChunkBasedAlgorithm(arrayMap, randomAccessFile.getFD(), signatureInfo);
                z = false;
            } catch (java.io.IOException e) {
                throw new java.lang.SecurityException("Cannot get FD", e);
            }
        }
        if (!map.containsKey(3)) {
            z2 = z;
        } else {
            verifyIntegrityForVerityBasedAlgorithm(map.get(3), randomAccessFile, signatureInfo);
        }
        if (z2) {
            throw new java.lang.SecurityException("No known digest exists for integrity check");
        }
    }

    static boolean isSupportedSignatureAlgorithm(int i) {
        switch (i) {
            case 257:
            case 258:
            case 259:
            case 260:
            case 513:
            case 514:
            case 769:
            case 1057:
            case SIGNATURE_VERITY_ECDSA_WITH_SHA256 /* 1059 */:
            case 1061:
                return true;
            default:
                return false;
        }
    }

    private static void verifyIntegrityFor1MbChunkBasedAlgorithm(java.util.Map<java.lang.Integer, byte[]> map, java.io.FileDescriptor fileDescriptor, android.util.apk.SignatureInfo signatureInfo) throws java.lang.SecurityException {
        int size = map.size();
        int[] iArr = new int[size];
        java.util.Iterator<java.lang.Integer> it = map.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = it.next().intValue();
            i++;
        }
        try {
            byte[][] computeContentDigestsPer1MbChunk = computeContentDigestsPer1MbChunk(iArr, fileDescriptor, signatureInfo);
            for (int i2 = 0; i2 < size; i2++) {
                int i3 = iArr[i2];
                if (!java.security.MessageDigest.isEqual(map.get(java.lang.Integer.valueOf(i3)), computeContentDigestsPer1MbChunk[i2])) {
                    throw new java.lang.SecurityException(getContentDigestAlgorithmJcaDigestAlgorithm(i3) + " digest of contents did not verify");
                }
            }
        } catch (java.security.DigestException e) {
            throw new java.lang.SecurityException("Failed to compute digest(s) of contents", e);
        }
    }

    public static byte[][] computeContentDigestsPer1MbChunk(int[] iArr, java.io.FileDescriptor fileDescriptor, android.util.apk.SignatureInfo signatureInfo) throws java.security.DigestException {
        android.util.apk.DataSource create = android.util.apk.DataSource.create(fileDescriptor, 0L, signatureInfo.apkSigningBlockOffset);
        android.util.apk.DataSource create2 = android.util.apk.DataSource.create(fileDescriptor, signatureInfo.centralDirOffset, signatureInfo.eocdOffset - signatureInfo.centralDirOffset);
        java.nio.ByteBuffer duplicate = signatureInfo.eocd.duplicate();
        duplicate.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        android.util.apk.ZipUtils.setZipEocdCentralDirectoryOffset(duplicate, signatureInfo.apkSigningBlockOffset);
        return computeContentDigestsPer1MbChunk(iArr, new android.util.apk.DataSource[]{create, create2, new android.util.apk.ByteBufferDataSource(duplicate)});
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x010e, code lost:
    
        r1 = r3;
        r12 = r12 + r1;
        r10 = r10 - r1;
        r15 = r15 + 1;
        r2 = r2;
        r3 = 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static byte[][] computeContentDigestsPer1MbChunk(int[] iArr, android.util.apk.DataSource[] dataSourceArr) throws java.security.DigestException {
        java.lang.String str;
        android.util.apk.DataSource[] dataSourceArr2 = dataSourceArr;
        long j = 0;
        int i = 0;
        long j2 = 0;
        for (android.util.apk.DataSource dataSource : dataSourceArr2) {
            j2 += getChunkCount(dataSource.size());
        }
        if (j2 >= 2097151) {
            throw new java.security.DigestException("Too many chunks: " + j2);
        }
        int i2 = (int) j2;
        byte[][] bArr = new byte[iArr.length][];
        for (int i3 = 0; i3 < iArr.length; i3++) {
            byte[] bArr2 = new byte[(getContentDigestAlgorithmOutputSizeBytes(iArr[i3]) * i2) + 5];
            bArr2[0] = 90;
            setUnsignedInt32LittleEndian(i2, bArr2, 1);
            bArr[i3] = bArr2;
        }
        byte[] bArr3 = new byte[5];
        bArr3[0] = -91;
        int length = iArr.length;
        java.security.MessageDigest[] messageDigestArr = new java.security.MessageDigest[length];
        int i4 = 0;
        while (true) {
            str = " digest not supported";
            if (i4 >= iArr.length) {
                break;
            }
            java.lang.String contentDigestAlgorithmJcaDigestAlgorithm = getContentDigestAlgorithmJcaDigestAlgorithm(iArr[i4]);
            try {
                messageDigestArr[i4] = java.security.MessageDigest.getInstance(contentDigestAlgorithmJcaDigestAlgorithm);
                i4++;
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new java.lang.RuntimeException(contentDigestAlgorithmJcaDigestAlgorithm + " digest not supported", e);
            }
        }
        android.util.apk.ApkSigningBlockUtils.MultipleDigestDataDigester multipleDigestDataDigester = new android.util.apk.ApkSigningBlockUtils.MultipleDigestDataDigester(messageDigestArr);
        int length2 = dataSourceArr2.length;
        int i5 = 0;
        int i6 = 0;
        while (i5 < length2) {
            android.util.apk.DataSource dataSource2 = dataSourceArr2[i5];
            int i7 = length2;
            java.lang.String str2 = str;
            long j3 = j;
            android.util.apk.ApkSigningBlockUtils.MultipleDigestDataDigester multipleDigestDataDigester2 = multipleDigestDataDigester;
            long size = dataSource2.size();
            while (size > j) {
                int min = (int) java.lang.Math.min(size, 1048576L);
                setUnsignedInt32LittleEndian(min, bArr3, 1);
                for (int i8 = 0; i8 < length; i8++) {
                    messageDigestArr[i8].update(bArr3);
                }
                android.util.apk.ApkSigningBlockUtils.MultipleDigestDataDigester multipleDigestDataDigester3 = multipleDigestDataDigester2;
                try {
                    dataSource2.feedIntoDataDigester(multipleDigestDataDigester3, j3, min);
                    int i9 = 0;
                    while (true) {
                        multipleDigestDataDigester2 = multipleDigestDataDigester3;
                        if (i9 < iArr.length) {
                            int i10 = iArr[i9];
                            byte[] bArr4 = bArr3;
                            byte[] bArr5 = bArr[i9];
                            int contentDigestAlgorithmOutputSizeBytes = getContentDigestAlgorithmOutputSizeBytes(i10);
                            int i11 = length;
                            java.security.MessageDigest messageDigest = messageDigestArr[i9];
                            java.security.MessageDigest[] messageDigestArr2 = messageDigestArr;
                            int digest = messageDigest.digest(bArr5, (i6 * contentDigestAlgorithmOutputSizeBytes) + 5, contentDigestAlgorithmOutputSizeBytes);
                            if (digest == contentDigestAlgorithmOutputSizeBytes) {
                                i9++;
                                multipleDigestDataDigester3 = multipleDigestDataDigester2;
                                bArr3 = bArr4;
                                length = i11;
                                messageDigestArr = messageDigestArr2;
                            } else {
                                throw new java.lang.RuntimeException("Unexpected output size of " + messageDigest.getAlgorithm() + " digest: " + digest);
                            }
                        }
                    }
                } catch (java.io.IOException e2) {
                    throw new java.security.DigestException("Failed to digest chunk #" + i6 + " of section #" + i, e2);
                }
            }
            i++;
            i5++;
            dataSourceArr2 = dataSourceArr;
            multipleDigestDataDigester = multipleDigestDataDigester2;
            str = str2;
            length2 = i7;
            j = 0;
        }
        java.lang.String str3 = str;
        byte[][] bArr6 = new byte[iArr.length][];
        for (int i12 = 0; i12 < iArr.length; i12++) {
            int i13 = iArr[i12];
            byte[] bArr7 = bArr[i12];
            java.lang.String contentDigestAlgorithmJcaDigestAlgorithm2 = getContentDigestAlgorithmJcaDigestAlgorithm(i13);
            try {
                bArr6[i12] = java.security.MessageDigest.getInstance(contentDigestAlgorithmJcaDigestAlgorithm2).digest(bArr7);
            } catch (java.security.NoSuchAlgorithmException e3) {
                throw new java.lang.RuntimeException(contentDigestAlgorithmJcaDigestAlgorithm2 + str3, e3);
            }
        }
        return bArr6;
    }

    static byte[] parseVerityDigestAndVerifySourceLength(byte[] bArr, long j, android.util.apk.SignatureInfo signatureInfo) throws java.lang.SecurityException {
        if (bArr.length != 40) {
            throw new java.lang.SecurityException("Verity digest size is wrong: " + bArr.length);
        }
        java.nio.ByteBuffer order = java.nio.ByteBuffer.wrap(bArr).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        order.position(32);
        if (order.getLong() == j - (signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset)) {
            return java.util.Arrays.copyOfRange(bArr, 0, 32);
        }
        throw new java.lang.SecurityException("APK content size did not verify");
    }

    private static void verifyIntegrityForVerityBasedAlgorithm(byte[] bArr, java.io.RandomAccessFile randomAccessFile, android.util.apk.SignatureInfo signatureInfo) throws java.lang.SecurityException {
        try {
            if (!java.util.Arrays.equals(parseVerityDigestAndVerifySourceLength(bArr, randomAccessFile.getChannel().size(), signatureInfo), android.util.apk.VerityBuilder.generateApkVerityTree(randomAccessFile, signatureInfo, new android.util.apk.ByteBufferFactory() { // from class: android.util.apk.ApkSigningBlockUtils.1
                @Override // android.util.apk.ByteBufferFactory
                public java.nio.ByteBuffer create(int i) {
                    return java.nio.ByteBuffer.allocate(i);
                }
            }).rootHash)) {
                throw new java.lang.SecurityException("APK verity digest of contents did not verify");
            }
        } catch (java.io.IOException | java.security.DigestException | java.security.NoSuchAlgorithmException e) {
            throw new java.lang.SecurityException("Error during verification", e);
        }
    }

    static android.util.Pair<java.nio.ByteBuffer, java.lang.Long> getEocd(java.io.RandomAccessFile randomAccessFile) throws java.io.IOException, android.util.apk.SignatureNotFoundException {
        android.util.Pair<java.nio.ByteBuffer, java.lang.Long> findZipEndOfCentralDirectoryRecord = android.util.apk.ZipUtils.findZipEndOfCentralDirectoryRecord(randomAccessFile);
        if (findZipEndOfCentralDirectoryRecord == null) {
            throw new android.util.apk.SignatureNotFoundException("Not an APK file: ZIP End of Central Directory record not found");
        }
        return findZipEndOfCentralDirectoryRecord;
    }

    static long getCentralDirOffset(java.nio.ByteBuffer byteBuffer, long j) throws android.util.apk.SignatureNotFoundException {
        long zipEocdCentralDirectoryOffset = android.util.apk.ZipUtils.getZipEocdCentralDirectoryOffset(byteBuffer);
        if (zipEocdCentralDirectoryOffset > j) {
            throw new android.util.apk.SignatureNotFoundException("ZIP Central Directory offset out of range: " + zipEocdCentralDirectoryOffset + ". ZIP End of Central Directory offset: " + j);
        }
        if (android.util.apk.ZipUtils.getZipEocdCentralDirectorySizeBytes(byteBuffer) + zipEocdCentralDirectoryOffset != j) {
            throw new android.util.apk.SignatureNotFoundException("ZIP Central Directory is not immediately followed by End of Central Directory");
        }
        return zipEocdCentralDirectoryOffset;
    }

    private static long getChunkCount(long j) {
        return ((j + 1048576) - 1) / 1048576;
    }

    static int compareSignatureAlgorithm(int i, int i2) {
        return compareContentDigestAlgorithm(getSignatureAlgorithmContentDigestAlgorithm(i), getSignatureAlgorithmContentDigestAlgorithm(i2));
    }

    private static int compareContentDigestAlgorithm(int i, int i2) {
        switch (i) {
            case 1:
                switch (i2) {
                    case 1:
                        return 0;
                    case 2:
                    case 3:
                        return -1;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown digestAlgorithm2: " + i2);
                }
            case 2:
                switch (i2) {
                    case 1:
                    case 3:
                        return 1;
                    case 2:
                        return 0;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown digestAlgorithm2: " + i2);
                }
            case 3:
                switch (i2) {
                    case 1:
                        return 1;
                    case 2:
                        return -1;
                    case 3:
                        return 0;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown digestAlgorithm2: " + i2);
                }
            default:
                throw new java.lang.IllegalArgumentException("Unknown digestAlgorithm1: " + i);
        }
    }

    static int getSignatureAlgorithmContentDigestAlgorithm(int i) {
        switch (i) {
            case 257:
            case 259:
            case 513:
            case 769:
                return 1;
            case 258:
            case 260:
            case 514:
                return 2;
            case 1057:
            case SIGNATURE_VERITY_ECDSA_WITH_SHA256 /* 1059 */:
            case 1061:
                return 3;
            default:
                throw new java.lang.IllegalArgumentException("Unknown signature algorithm: 0x" + java.lang.Long.toHexString(i & (-1)));
        }
    }

    static java.lang.String getContentDigestAlgorithmJcaDigestAlgorithm(int i) {
        switch (i) {
            case 1:
            case 3:
                return "SHA-256";
            case 2:
                return android.security.keystore.KeyProperties.DIGEST_SHA512;
            default:
                throw new java.lang.IllegalArgumentException("Unknown content digest algorthm: " + i);
        }
    }

    private static int getContentDigestAlgorithmOutputSizeBytes(int i) {
        switch (i) {
            case 1:
            case 3:
                return 32;
            case 2:
                return 64;
            default:
                throw new java.lang.IllegalArgumentException("Unknown content digest algorthm: " + i);
        }
    }

    static java.lang.String getSignatureAlgorithmJcaKeyAlgorithm(int i) {
        switch (i) {
            case 257:
            case 258:
            case 259:
            case 260:
            case 1057:
                return android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA;
            case 513:
            case 514:
            case SIGNATURE_VERITY_ECDSA_WITH_SHA256 /* 1059 */:
                return android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
            case 769:
            case 1061:
                return "DSA";
            default:
                throw new java.lang.IllegalArgumentException("Unknown signature algorithm: 0x" + java.lang.Long.toHexString(i & (-1)));
        }
    }

    static android.util.Pair<java.lang.String, ? extends java.security.spec.AlgorithmParameterSpec> getSignatureAlgorithmJcaSignatureAlgorithm(int i) {
        switch (i) {
            case 257:
                return android.util.Pair.create("SHA256withRSA/PSS", new java.security.spec.PSSParameterSpec("SHA-256", "MGF1", java.security.spec.MGF1ParameterSpec.SHA256, 32, 1));
            case 258:
                return android.util.Pair.create("SHA512withRSA/PSS", new java.security.spec.PSSParameterSpec(android.security.keystore.KeyProperties.DIGEST_SHA512, "MGF1", java.security.spec.MGF1ParameterSpec.SHA512, 64, 1));
            case 259:
            case 1057:
                return android.util.Pair.create("SHA256withRSA", null);
            case 260:
                return android.util.Pair.create("SHA512withRSA", null);
            case 513:
            case SIGNATURE_VERITY_ECDSA_WITH_SHA256 /* 1059 */:
                return android.util.Pair.create("SHA256withECDSA", null);
            case 514:
                return android.util.Pair.create("SHA512withECDSA", null);
            case 769:
            case 1061:
                return android.util.Pair.create("SHA256withDSA", null);
            default:
                throw new java.lang.IllegalArgumentException("Unknown signature algorithm: 0x" + java.lang.Long.toHexString(i & (-1)));
        }
    }

    static java.nio.ByteBuffer sliceFromTo(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("start: " + i);
        }
        if (i2 < i) {
            throw new java.lang.IllegalArgumentException("end < start: " + i2 + " < " + i);
        }
        int capacity = byteBuffer.capacity();
        if (i2 > byteBuffer.capacity()) {
            throw new java.lang.IllegalArgumentException("end > capacity: " + i2 + " > " + capacity);
        }
        int limit = byteBuffer.limit();
        int position = byteBuffer.position();
        try {
            byteBuffer.position(0);
            byteBuffer.limit(i2);
            byteBuffer.position(i);
            java.nio.ByteBuffer slice = byteBuffer.slice();
            slice.order(byteBuffer.order());
            return slice;
        } finally {
            byteBuffer.position(0);
            byteBuffer.limit(limit);
            byteBuffer.position(position);
        }
    }

    static java.nio.ByteBuffer getByteBuffer(java.nio.ByteBuffer byteBuffer, int i) throws java.nio.BufferUnderflowException {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("size: " + i);
        }
        int limit = byteBuffer.limit();
        int position = byteBuffer.position();
        int i2 = i + position;
        if (i2 < position || i2 > limit) {
            throw new java.nio.BufferUnderflowException();
        }
        byteBuffer.limit(i2);
        try {
            java.nio.ByteBuffer slice = byteBuffer.slice();
            slice.order(byteBuffer.order());
            byteBuffer.position(i2);
            return slice;
        } finally {
            byteBuffer.limit(limit);
        }
    }

    static java.nio.ByteBuffer getLengthPrefixedSlice(java.nio.ByteBuffer byteBuffer) throws java.io.IOException {
        if (byteBuffer.remaining() < 4) {
            throw new java.io.IOException("Remaining buffer too short to contain length of length-prefixed field. Remaining: " + byteBuffer.remaining());
        }
        int i = byteBuffer.getInt();
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Negative length");
        }
        if (i > byteBuffer.remaining()) {
            throw new java.io.IOException("Length-prefixed field longer than remaining buffer. Field length: " + i + ", remaining: " + byteBuffer.remaining());
        }
        return getByteBuffer(byteBuffer, i);
    }

    static byte[] readLengthPrefixedByteArray(java.nio.ByteBuffer byteBuffer) throws java.io.IOException {
        int i = byteBuffer.getInt();
        if (i < 0) {
            throw new java.io.IOException("Negative length");
        }
        if (i > byteBuffer.remaining()) {
            throw new java.io.IOException("Underflow while reading length-prefixed value. Length: " + i + ", available: " + byteBuffer.remaining());
        }
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return bArr;
    }

    static void setUnsignedInt32LittleEndian(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i & 255);
        bArr[i2 + 1] = (byte) ((i >>> 8) & 255);
        bArr[i2 + 2] = (byte) ((i >>> 16) & 255);
        bArr[i2 + 3] = (byte) ((i >>> 24) & 255);
    }

    static android.util.Pair<java.nio.ByteBuffer, java.lang.Long> findApkSigningBlock(java.io.RandomAccessFile randomAccessFile, long j) throws java.io.IOException, android.util.apk.SignatureNotFoundException {
        if (j < 32) {
            throw new android.util.apk.SignatureNotFoundException("APK too small for APK Signing Block. ZIP Central Directory offset: " + j);
        }
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(24);
        allocate.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        randomAccessFile.seek(j - allocate.capacity());
        randomAccessFile.readFully(allocate.array(), allocate.arrayOffset(), allocate.capacity());
        if (allocate.getLong(8) != APK_SIG_BLOCK_MAGIC_LO || allocate.getLong(16) != APK_SIG_BLOCK_MAGIC_HI) {
            throw new android.util.apk.SignatureNotFoundException("No APK Signing Block before ZIP Central Directory");
        }
        long j2 = allocate.getLong(0);
        if (j2 < allocate.capacity() || j2 > 2147483639) {
            throw new android.util.apk.SignatureNotFoundException("APK Signing Block size out of range: " + j2);
        }
        int i = (int) (8 + j2);
        long j3 = j - i;
        if (j3 < 0) {
            throw new android.util.apk.SignatureNotFoundException("APK Signing Block offset out of range: " + j3);
        }
        java.nio.ByteBuffer allocate2 = java.nio.ByteBuffer.allocate(i);
        allocate2.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        randomAccessFile.seek(j3);
        randomAccessFile.readFully(allocate2.array(), allocate2.arrayOffset(), allocate2.capacity());
        long j4 = allocate2.getLong(0);
        if (j4 != j2) {
            throw new android.util.apk.SignatureNotFoundException("APK Signing Block sizes in header and footer do not match: " + j4 + " vs " + j2);
        }
        return android.util.Pair.create(allocate2, java.lang.Long.valueOf(j3));
    }

    static java.nio.ByteBuffer findApkSignatureSchemeBlock(java.nio.ByteBuffer byteBuffer, int i) throws android.util.apk.SignatureNotFoundException {
        checkByteOrderLittleEndian(byteBuffer);
        java.nio.ByteBuffer sliceFromTo = sliceFromTo(byteBuffer, 8, byteBuffer.capacity() - 24);
        int i2 = 0;
        while (sliceFromTo.hasRemaining()) {
            i2++;
            if (sliceFromTo.remaining() < 8) {
                throw new android.util.apk.SignatureNotFoundException("Insufficient data to read size of APK Signing Block entry #" + i2);
            }
            long j = sliceFromTo.getLong();
            if (j < 4 || j > 2147483647L) {
                throw new android.util.apk.SignatureNotFoundException("APK Signing Block entry #" + i2 + " size out of range: " + j);
            }
            int i3 = (int) j;
            int position = sliceFromTo.position() + i3;
            if (i3 > sliceFromTo.remaining()) {
                throw new android.util.apk.SignatureNotFoundException("APK Signing Block entry #" + i2 + " size out of range: " + i3 + ", available: " + sliceFromTo.remaining());
            }
            if (sliceFromTo.getInt() == i) {
                return getByteBuffer(sliceFromTo, i3 - 4);
            }
            sliceFromTo.position(position);
        }
        throw new android.util.apk.SignatureNotFoundException("No block with ID " + i + " in APK Signing Block.");
    }

    private static void checkByteOrderLittleEndian(java.nio.ByteBuffer byteBuffer) {
        if (byteBuffer.order() != java.nio.ByteOrder.LITTLE_ENDIAN) {
            throw new java.lang.IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
    }

    private static class MultipleDigestDataDigester implements android.util.apk.DataDigester {
        private final java.security.MessageDigest[] mMds;

        MultipleDigestDataDigester(java.security.MessageDigest[] messageDigestArr) {
            this.mMds = messageDigestArr;
        }

        @Override // android.util.apk.DataDigester
        public void consume(java.nio.ByteBuffer byteBuffer) {
            java.nio.ByteBuffer slice = byteBuffer.slice();
            for (java.security.MessageDigest messageDigest : this.mMds) {
                slice.position(0);
                messageDigest.update(slice);
            }
        }
    }

    static android.util.apk.ApkSigningBlockUtils.VerifiedProofOfRotation verifyProofOfRotationStruct(java.nio.ByteBuffer byteBuffer, java.security.cert.CertificateFactory certificateFactory) throws java.lang.SecurityException, java.io.IOException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int i = 0;
        try {
            byteBuffer.getInt();
            java.util.HashSet hashSet = new java.util.HashSet();
            int i2 = -1;
            android.util.apk.VerbatimX509Certificate verbatimX509Certificate = null;
            while (byteBuffer.hasRemaining()) {
                i++;
                java.nio.ByteBuffer lengthPrefixedSlice = getLengthPrefixedSlice(byteBuffer);
                java.nio.ByteBuffer lengthPrefixedSlice2 = getLengthPrefixedSlice(lengthPrefixedSlice);
                int i3 = lengthPrefixedSlice.getInt();
                int i4 = lengthPrefixedSlice.getInt();
                byte[] readLengthPrefixedByteArray = readLengthPrefixedByteArray(lengthPrefixedSlice);
                if (verbatimX509Certificate != null) {
                    android.util.Pair<java.lang.String, ? extends java.security.spec.AlgorithmParameterSpec> signatureAlgorithmJcaSignatureAlgorithm = getSignatureAlgorithmJcaSignatureAlgorithm(i2);
                    java.security.PublicKey publicKey = verbatimX509Certificate.getPublicKey();
                    java.security.Signature signature = java.security.Signature.getInstance(signatureAlgorithmJcaSignatureAlgorithm.first);
                    signature.initVerify(publicKey);
                    if (signatureAlgorithmJcaSignatureAlgorithm.second != 0) {
                        signature.setParameter((java.security.spec.AlgorithmParameterSpec) signatureAlgorithmJcaSignatureAlgorithm.second);
                    }
                    signature.update(lengthPrefixedSlice2);
                    if (!signature.verify(readLengthPrefixedByteArray)) {
                        throw new java.lang.SecurityException("Unable to verify signature of certificate #" + i + " using " + signatureAlgorithmJcaSignatureAlgorithm.first + " when verifying Proof-of-rotation record");
                    }
                }
                lengthPrefixedSlice2.rewind();
                byte[] readLengthPrefixedByteArray2 = readLengthPrefixedByteArray(lengthPrefixedSlice2);
                int i5 = lengthPrefixedSlice2.getInt();
                if (verbatimX509Certificate != null && i2 != i5) {
                    throw new java.lang.SecurityException("Signing algorithm ID mismatch for certificate #" + i + " when verifying Proof-of-rotation record");
                }
                verbatimX509Certificate = new android.util.apk.VerbatimX509Certificate((java.security.cert.X509Certificate) certificateFactory.generateCertificate(new java.io.ByteArrayInputStream(readLengthPrefixedByteArray2)), readLengthPrefixedByteArray2);
                if (hashSet.contains(verbatimX509Certificate)) {
                    throw new java.lang.SecurityException("Encountered duplicate entries in Proof-of-rotation record at certificate #" + i + ".  All signing certificates should be unique");
                }
                hashSet.add(verbatimX509Certificate);
                arrayList.add(verbatimX509Certificate);
                arrayList2.add(java.lang.Integer.valueOf(i3));
                i2 = i4;
            }
            return new android.util.apk.ApkSigningBlockUtils.VerifiedProofOfRotation(arrayList, arrayList2);
        } catch (java.io.IOException | java.nio.BufferUnderflowException e) {
            throw new java.io.IOException("Failed to parse Proof-of-rotation record", e);
        } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | java.security.SignatureException e2) {
            throw new java.lang.SecurityException("Failed to verify signature over signed data for certificate #0 when verifying Proof-of-rotation record", e2);
        } catch (java.security.cert.CertificateException e3) {
            throw new java.lang.SecurityException("Failed to decode certificate #0 when verifying Proof-of-rotation record", e3);
        }
    }

    public static class VerifiedProofOfRotation {
        public final java.util.List<java.security.cert.X509Certificate> certs;
        public final java.util.List<java.lang.Integer> flagsList;

        public VerifiedProofOfRotation(java.util.List<java.security.cert.X509Certificate> list, java.util.List<java.lang.Integer> list2) {
            this.certs = list;
            this.flagsList = list2;
        }
    }
}
