package android.os.incremental;

/* loaded from: classes3.dex */
public class V4Signature {
    public static final java.lang.String EXT = ".idsig";
    public static final int HASHING_ALGORITHM_SHA256 = 1;
    public static final int INCFS_MAX_SIGNATURE_SIZE = 8096;
    public static final byte LOG2_BLOCK_SIZE_4096_BYTES = 12;
    public static final int SUPPORTED_VERSION = 2;
    public final byte[] hashingInfo;
    public final byte[] signingInfos;
    public final int version;

    public static class HashingInfo {
        public final int hashAlgorithm;
        public final byte log2BlockSize;
        public final byte[] rawRootHash;
        public final byte[] salt;

        HashingInfo(int i, byte b, byte[] bArr, byte[] bArr2) {
            this.hashAlgorithm = i;
            this.log2BlockSize = b;
            this.salt = bArr;
            this.rawRootHash = bArr2;
        }

        public static android.os.incremental.V4Signature.HashingInfo fromByteArray(byte[] bArr) throws java.io.IOException {
            java.nio.ByteBuffer order = java.nio.ByteBuffer.wrap(bArr).order(java.nio.ByteOrder.LITTLE_ENDIAN);
            return new android.os.incremental.V4Signature.HashingInfo(order.getInt(), order.get(), android.os.incremental.V4Signature.readBytes(order), android.os.incremental.V4Signature.readBytes(order));
        }
    }

    public static class SigningInfo {
        public final byte[] additionalData;
        public final byte[] apkDigest;
        public final byte[] certificate;
        public final byte[] publicKey;
        public final byte[] signature;
        public final int signatureAlgorithmId;

        SigningInfo(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, int i, byte[] bArr5) {
            this.apkDigest = bArr;
            this.certificate = bArr2;
            this.additionalData = bArr3;
            this.publicKey = bArr4;
            this.signatureAlgorithmId = i;
            this.signature = bArr5;
        }

        public static android.os.incremental.V4Signature.SigningInfo fromByteArray(byte[] bArr) throws java.io.IOException {
            return fromByteBuffer(java.nio.ByteBuffer.wrap(bArr).order(java.nio.ByteOrder.LITTLE_ENDIAN));
        }

        public static android.os.incremental.V4Signature.SigningInfo fromByteBuffer(java.nio.ByteBuffer byteBuffer) throws java.io.IOException {
            return new android.os.incremental.V4Signature.SigningInfo(android.os.incremental.V4Signature.readBytes(byteBuffer), android.os.incremental.V4Signature.readBytes(byteBuffer), android.os.incremental.V4Signature.readBytes(byteBuffer), android.os.incremental.V4Signature.readBytes(byteBuffer), byteBuffer.getInt(), android.os.incremental.V4Signature.readBytes(byteBuffer));
        }
    }

    public static class SigningInfoBlock {
        public final int blockId;
        public final byte[] signingInfo;

        public SigningInfoBlock(int i, byte[] bArr) {
            this.blockId = i;
            this.signingInfo = bArr;
        }

        static android.os.incremental.V4Signature.SigningInfoBlock fromByteBuffer(java.nio.ByteBuffer byteBuffer) throws java.io.IOException {
            return new android.os.incremental.V4Signature.SigningInfoBlock(byteBuffer.getInt(), android.os.incremental.V4Signature.readBytes(byteBuffer));
        }
    }

    public static class SigningInfos {
        public final android.os.incremental.V4Signature.SigningInfo signingInfo;
        public final android.os.incremental.V4Signature.SigningInfoBlock[] signingInfoBlocks;

        public SigningInfos(android.os.incremental.V4Signature.SigningInfo signingInfo) {
            this.signingInfo = signingInfo;
            this.signingInfoBlocks = new android.os.incremental.V4Signature.SigningInfoBlock[0];
        }

        public SigningInfos(android.os.incremental.V4Signature.SigningInfo signingInfo, android.os.incremental.V4Signature.SigningInfoBlock... signingInfoBlockArr) {
            this.signingInfo = signingInfo;
            this.signingInfoBlocks = signingInfoBlockArr;
        }

        public static android.os.incremental.V4Signature.SigningInfos fromByteArray(byte[] bArr) throws java.io.IOException {
            java.nio.ByteBuffer order = java.nio.ByteBuffer.wrap(bArr).order(java.nio.ByteOrder.LITTLE_ENDIAN);
            android.os.incremental.V4Signature.SigningInfo fromByteBuffer = android.os.incremental.V4Signature.SigningInfo.fromByteBuffer(order);
            if (!order.hasRemaining()) {
                return new android.os.incremental.V4Signature.SigningInfos(fromByteBuffer);
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(1);
            while (order.hasRemaining()) {
                arrayList.add(android.os.incremental.V4Signature.SigningInfoBlock.fromByteBuffer(order));
            }
            return new android.os.incremental.V4Signature.SigningInfos(fromByteBuffer, (android.os.incremental.V4Signature.SigningInfoBlock[]) arrayList.toArray(new android.os.incremental.V4Signature.SigningInfoBlock[arrayList.size()]));
        }
    }

    public static android.os.incremental.V4Signature readFrom(android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor.dup());
        try {
            android.os.incremental.V4Signature readFrom = readFrom(autoCloseInputStream);
            autoCloseInputStream.close();
            return readFrom;
        } catch (java.lang.Throwable th) {
            try {
                autoCloseInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static android.os.incremental.V4Signature readFrom(byte[] bArr) throws java.io.IOException {
        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
        try {
            android.os.incremental.V4Signature readFrom = readFrom(byteArrayInputStream);
            byteArrayInputStream.close();
            return readFrom;
        } catch (java.lang.Throwable th) {
            try {
                byteArrayInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public byte[] toByteArray() {
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            try {
                writeTo(byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            } finally {
            }
        } catch (java.io.IOException e) {
            return null;
        }
    }

    public static byte[] getSignedData(long j, android.os.incremental.V4Signature.HashingInfo hashingInfo, android.os.incremental.V4Signature.SigningInfo signingInfo) {
        int bytesSize = bytesSize(hashingInfo.salt) + 17 + bytesSize(hashingInfo.rawRootHash) + bytesSize(signingInfo.apkDigest) + bytesSize(signingInfo.certificate) + bytesSize(signingInfo.additionalData);
        java.nio.ByteBuffer order = java.nio.ByteBuffer.allocate(bytesSize).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        order.putInt(bytesSize);
        order.putLong(j);
        order.putInt(hashingInfo.hashAlgorithm);
        order.put(hashingInfo.log2BlockSize);
        writeBytes(order, hashingInfo.salt);
        writeBytes(order, hashingInfo.rawRootHash);
        writeBytes(order, signingInfo.apkDigest);
        writeBytes(order, signingInfo.certificate);
        writeBytes(order, signingInfo.additionalData);
        return order.array();
    }

    public boolean isVersionSupported() {
        return this.version == 2;
    }

    private V4Signature(int i, byte[] bArr, byte[] bArr2) {
        this.version = i;
        this.hashingInfo = bArr;
        this.signingInfos = bArr2;
    }

    public static android.os.incremental.V4Signature readFrom(java.io.InputStream inputStream) throws java.io.IOException {
        int readIntLE = readIntLE(inputStream);
        int i = INCFS_MAX_SIGNATURE_SIZE;
        byte[] readBytes = readBytes(inputStream, INCFS_MAX_SIGNATURE_SIZE);
        if (readBytes != null) {
            i = INCFS_MAX_SIGNATURE_SIZE - readBytes.length;
        }
        return new android.os.incremental.V4Signature(readIntLE, readBytes, readBytes(inputStream, i));
    }

    private void writeTo(java.io.OutputStream outputStream) throws java.io.IOException {
        writeIntLE(outputStream, this.version);
        writeBytes(outputStream, this.hashingInfo);
        writeBytes(outputStream, this.signingInfos);
    }

    private static int bytesSize(byte[] bArr) {
        return (bArr == null ? 0 : bArr.length) + 4;
    }

    private static void readFully(java.io.InputStream inputStream, byte[] bArr) throws java.io.IOException {
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            int read = inputStream.read(bArr, i, length - i);
            if (read < 0) {
                throw new java.io.EOFException();
            }
            i += read;
        }
    }

    private static int readIntLE(java.io.InputStream inputStream) throws java.io.IOException {
        byte[] bArr = new byte[4];
        readFully(inputStream, bArr);
        return java.nio.ByteBuffer.wrap(bArr).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
    }

    private static void writeIntLE(java.io.OutputStream outputStream, int i) throws java.io.IOException {
        outputStream.write(java.nio.ByteBuffer.wrap(new byte[4]).order(java.nio.ByteOrder.LITTLE_ENDIAN).putInt(i).array());
    }

    private static byte[] readBytes(java.io.InputStream inputStream, int i) throws java.io.IOException {
        try {
            int readIntLE = readIntLE(inputStream);
            if (readIntLE > i) {
                throw new java.io.IOException("Signature is too long. Max allowed is 8096");
            }
            byte[] bArr = new byte[readIntLE];
            readFully(inputStream, bArr);
            return bArr;
        } catch (java.io.EOFException e) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] readBytes(java.nio.ByteBuffer byteBuffer) throws java.io.IOException {
        if (byteBuffer.remaining() < 4) {
            throw new java.io.EOFException();
        }
        int i = byteBuffer.getInt();
        if (byteBuffer.remaining() < i) {
            throw new java.io.EOFException();
        }
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return bArr;
    }

    private static void writeBytes(java.io.OutputStream outputStream, byte[] bArr) throws java.io.IOException {
        if (bArr == null) {
            writeIntLE(outputStream, 0);
        } else {
            writeIntLE(outputStream, bArr.length);
            outputStream.write(bArr);
        }
    }

    private static void writeBytes(java.nio.ByteBuffer byteBuffer, byte[] bArr) {
        if (bArr == null) {
            byteBuffer.putInt(0);
        } else {
            byteBuffer.putInt(bArr.length);
            byteBuffer.put(bArr);
        }
    }
}
