package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class ByteBufferWriter {
    private static final float BUFFER_REALLOCATION_THRESHOLD = 0.5f;
    private static final int MAX_CACHED_BUFFER_SIZE = 16384;
    private static final int MIN_CACHED_BUFFER_SIZE = 1024;
    private static final java.lang.ThreadLocal<java.lang.ref.SoftReference<byte[]>> BUFFER = new java.lang.ThreadLocal<>();
    private static final java.lang.Class<?> FILE_OUTPUT_STREAM_CLASS = safeGetClass("java.io.FileOutputStream");
    private static final long CHANNEL_FIELD_OFFSET = getChannelFieldOffset(FILE_OUTPUT_STREAM_CLASS);

    private ByteBufferWriter() {
    }

    static void clearCachedBuffer() {
        BUFFER.set(null);
    }

    static void write(java.nio.ByteBuffer byteBuffer, java.io.OutputStream outputStream) throws java.io.IOException {
        int position = byteBuffer.position();
        try {
            if (byteBuffer.hasArray()) {
                outputStream.write(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            } else if (!writeToChannel(byteBuffer, outputStream)) {
                byte[] orCreateBuffer = getOrCreateBuffer(byteBuffer.remaining());
                while (byteBuffer.hasRemaining()) {
                    int min = java.lang.Math.min(byteBuffer.remaining(), orCreateBuffer.length);
                    byteBuffer.get(orCreateBuffer, 0, min);
                    outputStream.write(orCreateBuffer, 0, min);
                }
            }
        } finally {
            byteBuffer.position(position);
        }
    }

    private static byte[] getOrCreateBuffer(int i) {
        int max = java.lang.Math.max(i, 1024);
        byte[] buffer = getBuffer();
        if (buffer == null || needToReallocate(max, buffer.length)) {
            buffer = new byte[max];
            if (max <= 16384) {
                setBuffer(buffer);
            }
        }
        return buffer;
    }

    private static boolean needToReallocate(int i, int i2) {
        return i2 < i && ((float) i2) < ((float) i) * 0.5f;
    }

    private static byte[] getBuffer() {
        java.lang.ref.SoftReference<byte[]> softReference = BUFFER.get();
        if (softReference == null) {
            return null;
        }
        return softReference.get();
    }

    private static void setBuffer(byte[] bArr) {
        BUFFER.set(new java.lang.ref.SoftReference<>(bArr));
    }

    private static boolean writeToChannel(java.nio.ByteBuffer byteBuffer, java.io.OutputStream outputStream) throws java.io.IOException {
        java.nio.channels.WritableByteChannel writableByteChannel;
        if (CHANNEL_FIELD_OFFSET >= 0 && FILE_OUTPUT_STREAM_CLASS.isInstance(outputStream)) {
            try {
                writableByteChannel = (java.nio.channels.WritableByteChannel) com.android.framework.protobuf.UnsafeUtil.getObject(outputStream, CHANNEL_FIELD_OFFSET);
            } catch (java.lang.ClassCastException e) {
                writableByteChannel = null;
            }
            if (writableByteChannel != null) {
                writableByteChannel.write(byteBuffer);
                return true;
            }
            return false;
        }
        return false;
    }

    private static java.lang.Class<?> safeGetClass(java.lang.String str) {
        try {
            return java.lang.Class.forName(str);
        } catch (java.lang.ClassNotFoundException e) {
            return null;
        }
    }

    private static long getChannelFieldOffset(java.lang.Class<?> cls) {
        if (cls != null) {
            try {
                if (com.android.framework.protobuf.UnsafeUtil.hasUnsafeArrayOperations()) {
                    return com.android.framework.protobuf.UnsafeUtil.objectFieldOffset(cls.getDeclaredField(android.media.tv.TvContract.PARAM_CHANNEL));
                }
                return -1L;
            } catch (java.lang.Throwable th) {
                return -1L;
            }
        }
        return -1L;
    }
}
