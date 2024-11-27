package com.android.internal.util;

/* loaded from: classes5.dex */
public class ArtFastDataInput extends com.android.modules.utils.FastDataInput {
    private static java.util.concurrent.atomic.AtomicReference<com.android.internal.util.ArtFastDataInput> sInCache = new java.util.concurrent.atomic.AtomicReference<>();
    private static dalvik.system.VMRuntime sRuntime = dalvik.system.VMRuntime.getRuntime();
    private final long mBufferPtr;

    public ArtFastDataInput(java.io.InputStream inputStream, int i) {
        super(inputStream, i);
        this.mBufferPtr = sRuntime.addressOf(this.mBuffer);
    }

    public static com.android.internal.util.ArtFastDataInput obtain(java.io.InputStream inputStream) {
        com.android.internal.util.ArtFastDataInput andSet = sInCache.getAndSet(null);
        if (andSet != null) {
            andSet.setInput(inputStream);
            return andSet;
        }
        return new com.android.internal.util.ArtFastDataInput(inputStream, 32768);
    }

    @Override // com.android.modules.utils.FastDataInput
    public void release() {
        super.release();
        if (this.mBufferCap == 32768) {
            sInCache.compareAndSet(null, this);
        }
    }

    @Override // com.android.modules.utils.FastDataInput
    public byte[] newByteArray(int i) {
        return (byte[]) sRuntime.newNonMovableArray(java.lang.Byte.TYPE, i);
    }

    @Override // com.android.modules.utils.FastDataInput, java.io.DataInput
    public java.lang.String readUTF() throws java.io.IOException {
        int readUnsignedShort = readUnsignedShort();
        if (this.mBufferCap > readUnsignedShort) {
            if (this.mBufferLim - this.mBufferPos < readUnsignedShort) {
                fill(readUnsignedShort);
            }
            java.lang.String fromModifiedUtf8Bytes = android.util.CharsetUtils.fromModifiedUtf8Bytes(this.mBufferPtr, this.mBufferPos, readUnsignedShort);
            this.mBufferPos += readUnsignedShort;
            return fromModifiedUtf8Bytes;
        }
        byte[] bArr = (byte[]) sRuntime.newNonMovableArray(java.lang.Byte.TYPE, readUnsignedShort + 1);
        readFully(bArr, 0, readUnsignedShort);
        return android.util.CharsetUtils.fromModifiedUtf8Bytes(sRuntime.addressOf(bArr), 0, readUnsignedShort);
    }
}
