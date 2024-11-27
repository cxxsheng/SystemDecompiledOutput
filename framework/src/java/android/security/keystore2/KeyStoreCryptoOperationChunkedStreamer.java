package android.security.keystore2;

/* loaded from: classes3.dex */
class KeyStoreCryptoOperationChunkedStreamer implements android.security.keystore2.KeyStoreCryptoOperationStreamer {
    private static final int DEFAULT_CHUNK_SIZE_MAX = 32768;
    private static final int DEFAULT_CHUNK_SIZE_THRESHOLD = 2048;
    private final byte[] mChunk;
    private int mChunkLength;
    private final int mChunkSizeMax;
    private final int mChunkSizeThreshold;
    private long mConsumedInputSizeBytes;
    private final android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream mKeyStoreStream;
    private long mProducedOutputSizeBytes;

    interface Stream {
        byte[] finish(byte[] bArr, byte[] bArr2) throws android.security.KeyStoreException;

        byte[] update(byte[] bArr) throws android.security.KeyStoreException;
    }

    KeyStoreCryptoOperationChunkedStreamer(android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream stream) {
        this(stream, 2048, 32768);
    }

    KeyStoreCryptoOperationChunkedStreamer(android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream stream, int i) {
        this(stream, i, 32768);
    }

    KeyStoreCryptoOperationChunkedStreamer(android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream stream, int i, int i2) {
        this.mChunkLength = 0;
        this.mChunkLength = 0;
        this.mConsumedInputSizeBytes = 0L;
        this.mProducedOutputSizeBytes = 0L;
        this.mKeyStoreStream = stream;
        this.mChunkSizeMax = i2;
        if (i <= 0) {
            this.mChunkSizeThreshold = 1;
        } else if (i > i2) {
            this.mChunkSizeThreshold = i2;
        } else {
            this.mChunkSizeThreshold = i;
        }
        this.mChunk = new byte[this.mChunkSizeMax];
    }

    @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
    public byte[] update(byte[] bArr, int i, int i2) throws android.security.KeyStoreException {
        if (i2 == 0 || bArr == null) {
            return libcore.util.EmptyArray.BYTE;
        }
        if (i2 < 0 || i < 0 || i + i2 > bArr.length) {
            throw new android.security.KeyStoreException(-1000, "Input offset and length out of bounds of input array");
        }
        byte[] bArr2 = libcore.util.EmptyArray.BYTE;
        if (this.mChunkLength > 0) {
            int copy = android.security.keystore.ArrayUtils.copy(bArr, i, this.mChunk, this.mChunkLength, i2);
            i2 -= copy;
            i += copy;
            this.mChunkLength += copy;
            if (this.mChunkLength < this.mChunkSizeMax) {
                return bArr2;
            }
            byte[] update = this.mKeyStoreStream.update(this.mChunk);
            if (update != null) {
                bArr2 = android.security.keystore.ArrayUtils.concat(bArr2, update);
            }
            this.mConsumedInputSizeBytes += copy;
            this.mChunkLength = 0;
        }
        while (i2 >= this.mChunkSizeThreshold) {
            int i3 = i2 < this.mChunkSizeMax ? i2 : this.mChunkSizeMax;
            byte[] update2 = this.mKeyStoreStream.update(android.security.keystore.ArrayUtils.subarray(bArr, i, i3));
            i2 -= i3;
            i += i3;
            this.mConsumedInputSizeBytes += i3;
            if (update2 != null) {
                bArr2 = android.security.keystore.ArrayUtils.concat(bArr2, update2);
            }
        }
        if (i2 > 0) {
            this.mChunkLength = android.security.keystore.ArrayUtils.copy(bArr, i, this.mChunk, 0, i2);
            this.mConsumedInputSizeBytes += i2;
        }
        this.mProducedOutputSizeBytes += bArr2.length;
        return bArr2;
    }

    @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
    public byte[] doFinal(byte[] bArr, int i, int i2, byte[] bArr2) throws android.security.KeyStoreException {
        byte[] update = update(bArr, i, i2);
        byte[] finish = this.mKeyStoreStream.finish(android.security.keystore.ArrayUtils.subarray(this.mChunk, 0, this.mChunkLength), bArr2);
        if (finish != null) {
            this.mProducedOutputSizeBytes += finish.length;
            if (update != null) {
                return android.security.keystore.ArrayUtils.concat(update, finish);
            }
            return finish;
        }
        return update;
    }

    @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
    public long getConsumedInputSizeBytes() {
        return this.mConsumedInputSizeBytes;
    }

    @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
    public long getProducedOutputSizeBytes() {
        return this.mProducedOutputSizeBytes;
    }

    public static class MainDataStream implements android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream {
        private final android.security.KeyStoreOperation mOperation;

        MainDataStream(android.security.KeyStoreOperation keyStoreOperation) {
            this.mOperation = keyStoreOperation;
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream
        public byte[] update(byte[] bArr) throws android.security.KeyStoreException {
            return this.mOperation.update(bArr);
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream
        public byte[] finish(byte[] bArr, byte[] bArr2) throws android.security.KeyStoreException {
            return this.mOperation.finish(bArr, bArr2);
        }
    }
}
