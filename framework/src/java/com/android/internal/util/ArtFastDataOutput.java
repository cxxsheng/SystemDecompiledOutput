package com.android.internal.util;

/* loaded from: classes5.dex */
public class ArtFastDataOutput extends com.android.modules.utils.FastDataOutput {
    private static java.util.concurrent.atomic.AtomicReference<com.android.internal.util.ArtFastDataOutput> sOutCache = new java.util.concurrent.atomic.AtomicReference<>();
    private static dalvik.system.VMRuntime sRuntime = dalvik.system.VMRuntime.getRuntime();
    private final long mBufferPtr;

    public ArtFastDataOutput(java.io.OutputStream outputStream, int i) {
        super(outputStream, i);
        this.mBufferPtr = sRuntime.addressOf(this.mBuffer);
    }

    public static com.android.internal.util.ArtFastDataOutput obtain(java.io.OutputStream outputStream) {
        com.android.internal.util.ArtFastDataOutput andSet = sOutCache.getAndSet(null);
        if (andSet != null) {
            andSet.setOutput(outputStream);
            return andSet;
        }
        return new com.android.internal.util.ArtFastDataOutput(outputStream, 32768);
    }

    @Override // com.android.modules.utils.FastDataOutput
    public void release() {
        super.release();
        if (this.mBufferCap == 32768) {
            sOutCache.compareAndSet(null, this);
        }
    }

    @Override // com.android.modules.utils.FastDataOutput
    public byte[] newByteArray(int i) {
        return (byte[]) sRuntime.newNonMovableArray(java.lang.Byte.TYPE, i);
    }

    @Override // com.android.modules.utils.FastDataOutput, java.io.DataOutput
    public void writeUTF(java.lang.String str) throws java.io.IOException {
        if (this.mBufferCap - this.mBufferPos < str.length() + 2) {
            drain();
        }
        int modifiedUtf8Bytes = android.util.CharsetUtils.toModifiedUtf8Bytes(str, this.mBufferPtr, this.mBufferPos + 2, this.mBufferCap);
        if (java.lang.Math.abs(modifiedUtf8Bytes) > 65535) {
            throw new java.io.IOException("Modified UTF-8 length too large: " + modifiedUtf8Bytes);
        }
        if (modifiedUtf8Bytes >= 0) {
            writeShort(modifiedUtf8Bytes);
            this.mBufferPos += modifiedUtf8Bytes;
            return;
        }
        int i = -modifiedUtf8Bytes;
        byte[] bArr = (byte[]) sRuntime.newNonMovableArray(java.lang.Byte.TYPE, i + 1);
        android.util.CharsetUtils.toModifiedUtf8Bytes(str, sRuntime.addressOf(bArr), 0, bArr.length);
        writeShort(i);
        write(bArr, 0, i);
    }
}
