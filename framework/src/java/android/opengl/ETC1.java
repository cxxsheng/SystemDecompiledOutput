package android.opengl;

/* loaded from: classes3.dex */
public class ETC1 {
    public static final int DECODED_BLOCK_SIZE = 48;
    public static final int ENCODED_BLOCK_SIZE = 8;
    public static final int ETC1_RGB8_OES = 36196;
    public static final int ETC_PKM_HEADER_SIZE = 16;

    public static native void decodeBlock(java.nio.Buffer buffer, java.nio.Buffer buffer2);

    public static native void decodeImage(java.nio.Buffer buffer, java.nio.Buffer buffer2, int i, int i2, int i3, int i4);

    public static native void encodeBlock(java.nio.Buffer buffer, int i, java.nio.Buffer buffer2);

    public static native void encodeImage(java.nio.Buffer buffer, int i, int i2, int i3, int i4, java.nio.Buffer buffer2);

    public static native void formatHeader(java.nio.Buffer buffer, int i, int i2);

    public static native int getEncodedDataSize(int i, int i2);

    public static native int getHeight(java.nio.Buffer buffer);

    public static native int getWidth(java.nio.Buffer buffer);

    public static native boolean isValid(java.nio.Buffer buffer);
}
