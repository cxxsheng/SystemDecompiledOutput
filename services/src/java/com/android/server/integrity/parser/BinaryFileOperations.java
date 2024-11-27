package com.android.server.integrity.parser;

/* loaded from: classes2.dex */
public class BinaryFileOperations {
    public static java.lang.String getStringValue(com.android.server.integrity.model.BitInputStream bitInputStream) throws java.io.IOException {
        return getStringValue(bitInputStream, bitInputStream.getNext(8), bitInputStream.getNext(1) == 1);
    }

    public static java.lang.String getStringValue(com.android.server.integrity.model.BitInputStream bitInputStream, int i, boolean z) throws java.io.IOException {
        if (!z) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            while (true) {
                int i2 = i - 1;
                if (i > 0) {
                    sb.append((char) bitInputStream.getNext(8));
                    i = i2;
                } else {
                    return sb.toString();
                }
            }
        } else {
            java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(i);
            while (true) {
                int i3 = i - 1;
                if (i > 0) {
                    allocate.put((byte) (bitInputStream.getNext(8) & 255));
                    i = i3;
                } else {
                    return android.content.integrity.IntegrityUtils.getHexDigest(allocate.array());
                }
            }
        }
    }

    public static int getIntValue(com.android.server.integrity.model.BitInputStream bitInputStream) throws java.io.IOException {
        return bitInputStream.getNext(32);
    }

    public static boolean getBooleanValue(com.android.server.integrity.model.BitInputStream bitInputStream) throws java.io.IOException {
        return bitInputStream.getNext(1) == 1;
    }
}
