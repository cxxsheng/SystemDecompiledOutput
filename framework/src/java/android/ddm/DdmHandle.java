package android.ddm;

/* loaded from: classes.dex */
public abstract class DdmHandle extends org.apache.harmony.dalvik.ddmc.ChunkHandler {
    public static java.lang.String getString(java.nio.ByteBuffer byteBuffer, int i) {
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = byteBuffer.getChar();
        }
        return new java.lang.String(cArr);
    }

    public static void putString(java.nio.ByteBuffer byteBuffer, java.lang.String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            byteBuffer.putChar(str.charAt(i));
        }
    }
}
