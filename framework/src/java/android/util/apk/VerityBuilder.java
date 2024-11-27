package android.util.apk;

/* loaded from: classes3.dex */
public abstract class VerityBuilder {
    private static final int CHUNK_SIZE_BYTES = 4096;
    private static final byte[] DEFAULT_SALT = new byte[8];
    private static final int DIGEST_SIZE_BYTES = 32;
    private static final int FSVERITY_HEADER_SIZE_BYTES = 64;
    private static final java.lang.String JCA_DIGEST_ALGORITHM = "SHA-256";
    private static final int MMAP_REGION_SIZE_BYTES = 1048576;
    private static final int ZIP_EOCD_CENTRAL_DIR_OFFSET_FIELD_OFFSET = 16;
    private static final int ZIP_EOCD_CENTRAL_DIR_OFFSET_FIELD_SIZE = 4;

    private VerityBuilder() {
    }

    public static class VerityResult {
        public final int merkleTreeSize;
        public final byte[] rootHash;
        public final java.nio.ByteBuffer verityData;

        private VerityResult(java.nio.ByteBuffer byteBuffer, int i, byte[] bArr) {
            this.verityData = byteBuffer;
            this.merkleTreeSize = i;
            this.rootHash = bArr;
        }
    }

    public static android.util.apk.VerityBuilder.VerityResult generateApkVerityTree(java.io.RandomAccessFile randomAccessFile, android.util.apk.SignatureInfo signatureInfo, android.util.apk.ByteBufferFactory byteBufferFactory) throws java.io.IOException, java.lang.SecurityException, java.security.NoSuchAlgorithmException, java.security.DigestException {
        return generateVerityTreeInternal(randomAccessFile, byteBufferFactory, signatureInfo);
    }

    private static android.util.apk.VerityBuilder.VerityResult generateVerityTreeInternal(java.io.RandomAccessFile randomAccessFile, android.util.apk.ByteBufferFactory byteBufferFactory, android.util.apk.SignatureInfo signatureInfo) throws java.io.IOException, java.lang.SecurityException, java.security.NoSuchAlgorithmException, java.security.DigestException {
        int[] calculateVerityLevelOffset = calculateVerityLevelOffset(randomAccessFile.getChannel().size() - (signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset));
        int i = calculateVerityLevelOffset[calculateVerityLevelOffset.length - 1];
        java.nio.ByteBuffer create = byteBufferFactory.create(i + 4096);
        create.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        return new android.util.apk.VerityBuilder.VerityResult(create, i, generateVerityTreeInternal(randomAccessFile, signatureInfo, DEFAULT_SALT, calculateVerityLevelOffset, slice(create, 0, i)));
    }

    static void generateApkVerityFooter(java.io.RandomAccessFile randomAccessFile, android.util.apk.SignatureInfo signatureInfo, java.nio.ByteBuffer byteBuffer) throws java.io.IOException {
        byteBuffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        generateApkVerityHeader(byteBuffer, randomAccessFile.getChannel().size(), DEFAULT_SALT);
        generateApkVerityExtensions(byteBuffer, signatureInfo.apkSigningBlockOffset, signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset, signatureInfo.eocdOffset);
    }

    public static byte[] generateFsVerityRootHash(java.lang.String str, byte[] bArr, android.util.apk.ByteBufferFactory byteBufferFactory) throws java.io.IOException, java.security.NoSuchAlgorithmException, java.security.DigestException {
        java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(str, "r");
        try {
            int[] calculateVerityLevelOffset = calculateVerityLevelOffset(randomAccessFile.length());
            int i = calculateVerityLevelOffset[calculateVerityLevelOffset.length - 1];
            java.nio.ByteBuffer create = byteBufferFactory.create(i + 4096);
            create.order(java.nio.ByteOrder.LITTLE_ENDIAN);
            byte[] generateFsVerityTreeInternal = generateFsVerityTreeInternal(randomAccessFile, bArr, calculateVerityLevelOffset, slice(create, 0, i));
            randomAccessFile.close();
            return generateFsVerityTreeInternal;
        } catch (java.lang.Throwable th) {
            try {
                randomAccessFile.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    static byte[] generateApkVerity(java.lang.String str, android.util.apk.ByteBufferFactory byteBufferFactory, android.util.apk.SignatureInfo signatureInfo) throws java.io.IOException, android.util.apk.SignatureNotFoundException, java.lang.SecurityException, java.security.DigestException, java.security.NoSuchAlgorithmException {
        java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(str, "r");
        try {
            android.util.apk.VerityBuilder.VerityResult generateVerityTreeInternal = generateVerityTreeInternal(randomAccessFile, byteBufferFactory, signatureInfo);
            java.nio.ByteBuffer slice = slice(generateVerityTreeInternal.verityData, generateVerityTreeInternal.merkleTreeSize, generateVerityTreeInternal.verityData.limit());
            generateApkVerityFooter(randomAccessFile, signatureInfo, slice);
            slice.putInt(slice.position() + 4);
            generateVerityTreeInternal.verityData.limit(generateVerityTreeInternal.merkleTreeSize + slice.position());
            byte[] bArr = generateVerityTreeInternal.rootHash;
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

    private static class BufferedDigester implements android.util.apk.DataDigester {
        private static final int BUFFER_SIZE = 4096;
        private int mBytesDigestedSinceReset;
        private final byte[] mDigestBuffer;
        private final java.security.MessageDigest mMd;
        private final java.nio.ByteBuffer mOutput;
        private final byte[] mSalt;

        private BufferedDigester(byte[] bArr, java.nio.ByteBuffer byteBuffer) throws java.security.NoSuchAlgorithmException {
            this.mDigestBuffer = new byte[32];
            this.mSalt = bArr;
            this.mOutput = byteBuffer.slice();
            this.mMd = java.security.MessageDigest.getInstance("SHA-256");
            if (this.mSalt != null) {
                this.mMd.update(this.mSalt);
            }
            this.mBytesDigestedSinceReset = 0;
        }

        @Override // android.util.apk.DataDigester
        public void consume(java.nio.ByteBuffer byteBuffer) throws java.security.DigestException {
            byteBuffer.position();
            int remaining = byteBuffer.remaining();
            while (remaining > 0) {
                int min = java.lang.Math.min(remaining, 4096 - this.mBytesDigestedSinceReset);
                byteBuffer.limit(byteBuffer.position() + min);
                this.mMd.update(byteBuffer);
                remaining -= min;
                this.mBytesDigestedSinceReset += min;
                if (this.mBytesDigestedSinceReset == 4096) {
                    this.mMd.digest(this.mDigestBuffer, 0, this.mDigestBuffer.length);
                    this.mOutput.put(this.mDigestBuffer);
                    if (this.mSalt != null) {
                        this.mMd.update(this.mSalt);
                    }
                    this.mBytesDigestedSinceReset = 0;
                }
            }
        }

        public void assertEmptyBuffer() throws java.security.DigestException {
            if (this.mBytesDigestedSinceReset != 0) {
                throw new java.lang.IllegalStateException("Buffer is not empty: " + this.mBytesDigestedSinceReset);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fillUpLastOutputChunk() {
            int position = this.mOutput.position() % 4096;
            if (position == 0) {
                return;
            }
            this.mOutput.put(java.nio.ByteBuffer.allocate(4096 - position));
        }
    }

    private static void consumeByChunk(android.util.apk.DataDigester dataDigester, android.util.apk.DataSource dataSource, int i) throws java.io.IOException, java.security.DigestException {
        long size = dataSource.size();
        long j = 0;
        while (size > 0) {
            int min = (int) java.lang.Math.min(size, i);
            dataSource.feedIntoDataDigester(dataDigester, j, min);
            long j2 = min;
            j += j2;
            size -= j2;
        }
    }

    private static void generateFsVerityDigestAtLeafLevel(java.io.RandomAccessFile randomAccessFile, byte[] bArr, java.nio.ByteBuffer byteBuffer) throws java.io.IOException, java.security.NoSuchAlgorithmException, java.security.DigestException {
        android.util.apk.VerityBuilder.BufferedDigester bufferedDigester = new android.util.apk.VerityBuilder.BufferedDigester(bArr, byteBuffer);
        consumeByChunk(bufferedDigester, android.util.apk.DataSource.create(randomAccessFile.getFD(), 0L, randomAccessFile.length()), 1048576);
        int length = (int) (randomAccessFile.length() % 4096);
        if (length != 0) {
            bufferedDigester.consume(java.nio.ByteBuffer.allocate(4096 - length));
        }
        bufferedDigester.assertEmptyBuffer();
        bufferedDigester.fillUpLastOutputChunk();
    }

    private static void generateApkVerityDigestAtLeafLevel(java.io.RandomAccessFile randomAccessFile, android.util.apk.SignatureInfo signatureInfo, byte[] bArr, java.nio.ByteBuffer byteBuffer) throws java.io.IOException, java.security.NoSuchAlgorithmException, java.security.DigestException {
        android.util.apk.VerityBuilder.BufferedDigester bufferedDigester = new android.util.apk.VerityBuilder.BufferedDigester(bArr, byteBuffer);
        consumeByChunk(bufferedDigester, android.util.apk.DataSource.create(randomAccessFile.getFD(), 0L, signatureInfo.apkSigningBlockOffset), 1048576);
        long j = signatureInfo.eocdOffset + 16;
        consumeByChunk(bufferedDigester, android.util.apk.DataSource.create(randomAccessFile.getFD(), signatureInfo.centralDirOffset, j - signatureInfo.centralDirOffset), 1048576);
        java.nio.ByteBuffer order = java.nio.ByteBuffer.allocate(4).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        order.putInt(java.lang.Math.toIntExact(signatureInfo.apkSigningBlockOffset));
        order.flip();
        bufferedDigester.consume(order);
        long j2 = j + 4;
        consumeByChunk(bufferedDigester, android.util.apk.DataSource.create(randomAccessFile.getFD(), j2, randomAccessFile.getChannel().size() - j2), 1048576);
        int size = (int) (randomAccessFile.getChannel().size() % 4096);
        if (size != 0) {
            bufferedDigester.consume(java.nio.ByteBuffer.allocate(4096 - size));
        }
        bufferedDigester.assertEmptyBuffer();
        bufferedDigester.fillUpLastOutputChunk();
    }

    private static byte[] generateFsVerityTreeInternal(java.io.RandomAccessFile randomAccessFile, byte[] bArr, int[] iArr, java.nio.ByteBuffer byteBuffer) throws java.io.IOException, java.security.NoSuchAlgorithmException, java.security.DigestException {
        generateFsVerityDigestAtLeafLevel(randomAccessFile, bArr, slice(byteBuffer, iArr[iArr.length - 2], iArr[iArr.length - 1]));
        int length = iArr.length - 3;
        while (true) {
            if (length >= 0) {
                int i = length + 1;
                java.nio.ByteBuffer slice = slice(byteBuffer, iArr[i], iArr[length + 2]);
                java.nio.ByteBuffer slice2 = slice(byteBuffer, iArr[length], iArr[i]);
                android.util.apk.ByteBufferDataSource byteBufferDataSource = new android.util.apk.ByteBufferDataSource(slice);
                android.util.apk.VerityBuilder.BufferedDigester bufferedDigester = new android.util.apk.VerityBuilder.BufferedDigester(bArr, slice2);
                consumeByChunk(bufferedDigester, byteBufferDataSource, 4096);
                bufferedDigester.assertEmptyBuffer();
                bufferedDigester.fillUpLastOutputChunk();
                length--;
            } else {
                byte[] bArr2 = new byte[32];
                android.util.apk.VerityBuilder.BufferedDigester bufferedDigester2 = new android.util.apk.VerityBuilder.BufferedDigester(bArr, java.nio.ByteBuffer.wrap(bArr2));
                bufferedDigester2.consume(slice(byteBuffer, 0, 4096));
                bufferedDigester2.assertEmptyBuffer();
                return bArr2;
            }
        }
    }

    private static byte[] generateVerityTreeInternal(java.io.RandomAccessFile randomAccessFile, android.util.apk.SignatureInfo signatureInfo, byte[] bArr, int[] iArr, java.nio.ByteBuffer byteBuffer) throws java.io.IOException, java.security.NoSuchAlgorithmException, java.security.DigestException {
        assertSigningBlockAlignedAndHasFullPages(signatureInfo);
        generateApkVerityDigestAtLeafLevel(randomAccessFile, signatureInfo, bArr, slice(byteBuffer, iArr[iArr.length - 2], iArr[iArr.length - 1]));
        int length = iArr.length - 3;
        while (true) {
            if (length >= 0) {
                int i = length + 1;
                java.nio.ByteBuffer slice = slice(byteBuffer, iArr[i], iArr[length + 2]);
                java.nio.ByteBuffer slice2 = slice(byteBuffer, iArr[length], iArr[i]);
                android.util.apk.ByteBufferDataSource byteBufferDataSource = new android.util.apk.ByteBufferDataSource(slice);
                android.util.apk.VerityBuilder.BufferedDigester bufferedDigester = new android.util.apk.VerityBuilder.BufferedDigester(bArr, slice2);
                consumeByChunk(bufferedDigester, byteBufferDataSource, 4096);
                bufferedDigester.assertEmptyBuffer();
                bufferedDigester.fillUpLastOutputChunk();
                length--;
            } else {
                byte[] bArr2 = new byte[32];
                android.util.apk.VerityBuilder.BufferedDigester bufferedDigester2 = new android.util.apk.VerityBuilder.BufferedDigester(bArr, java.nio.ByteBuffer.wrap(bArr2));
                bufferedDigester2.consume(slice(byteBuffer, 0, 4096));
                bufferedDigester2.assertEmptyBuffer();
                return bArr2;
            }
        }
    }

    private static java.nio.ByteBuffer generateApkVerityHeader(java.nio.ByteBuffer byteBuffer, long j, byte[] bArr) {
        if (bArr.length != 8) {
            throw new java.lang.IllegalArgumentException("salt is not 8 bytes long");
        }
        byteBuffer.put("TrueBrew".getBytes());
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 12);
        byteBuffer.put((byte) 7);
        byteBuffer.putShort((short) 1);
        byteBuffer.putShort((short) 1);
        byteBuffer.putInt(0);
        byteBuffer.putInt(0);
        byteBuffer.putLong(j);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 0);
        byteBuffer.put(bArr);
        skip(byteBuffer, 22);
        return byteBuffer;
    }

    private static java.nio.ByteBuffer generateApkVerityExtensions(java.nio.ByteBuffer byteBuffer, long j, long j2, long j3) {
        byteBuffer.putInt(24);
        byteBuffer.putShort((short) 1);
        skip(byteBuffer, 2);
        byteBuffer.putLong(j);
        byteBuffer.putLong(j2);
        byteBuffer.putInt(20);
        byteBuffer.putShort((short) 2);
        skip(byteBuffer, 2);
        byteBuffer.putLong(j3 + 16);
        byteBuffer.putInt(java.lang.Math.toIntExact(j));
        skip(byteBuffer, 4);
        return byteBuffer;
    }

    private static int[] calculateVerityLevelOffset(long j) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        do {
            j = divideRoundup(j, 4096L) * 32;
            arrayList.add(java.lang.Long.valueOf(divideRoundup(j, 4096L) * 4096));
        } while (j > 4096);
        int[] iArr = new int[arrayList.size() + 1];
        int i = 0;
        iArr[0] = 0;
        while (i < arrayList.size()) {
            int i2 = i + 1;
            iArr[i2] = iArr[i] + java.lang.Math.toIntExact(((java.lang.Long) arrayList.get((arrayList.size() - i) - 1)).longValue());
            i = i2;
        }
        return iArr;
    }

    private static void assertSigningBlockAlignedAndHasFullPages(android.util.apk.SignatureInfo signatureInfo) {
        if (signatureInfo.apkSigningBlockOffset % 4096 != 0) {
            throw new java.lang.IllegalArgumentException("APK Signing Block does not start at the page boundary: " + signatureInfo.apkSigningBlockOffset);
        }
        if ((signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset) % 4096 != 0) {
            throw new java.lang.IllegalArgumentException("Size of APK Signing Block is not a multiple of 4096: " + (signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset));
        }
    }

    private static java.nio.ByteBuffer slice(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        java.nio.ByteBuffer duplicate = byteBuffer.duplicate();
        duplicate.position(0);
        duplicate.limit(i2);
        duplicate.position(i);
        return duplicate.slice();
    }

    private static void skip(java.nio.ByteBuffer byteBuffer, int i) {
        byteBuffer.position(byteBuffer.position() + i);
    }

    private static long divideRoundup(long j, long j2) {
        return ((j + j2) - 1) / j2;
    }
}
