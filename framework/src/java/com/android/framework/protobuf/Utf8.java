package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class Utf8 {
    private static final long ASCII_MASK_LONG = -9187201950435737472L;
    static final int COMPLETE = 0;
    static final int MALFORMED = -1;
    static final int MAX_BYTES_PER_CHAR = 3;
    private static final int UNSAFE_COUNT_ASCII_THRESHOLD = 16;
    private static final com.android.framework.protobuf.Utf8.Processor processor;

    static {
        com.android.framework.protobuf.Utf8.Processor safeProcessor;
        if (com.android.framework.protobuf.Utf8.UnsafeProcessor.isAvailable() && !com.android.framework.protobuf.Android.isOnAndroidDevice()) {
            safeProcessor = new com.android.framework.protobuf.Utf8.UnsafeProcessor();
        } else {
            safeProcessor = new com.android.framework.protobuf.Utf8.SafeProcessor();
        }
        processor = safeProcessor;
    }

    static boolean isValidUtf8(byte[] bArr) {
        return processor.isValidUtf8(bArr, 0, bArr.length);
    }

    static boolean isValidUtf8(byte[] bArr, int i, int i2) {
        return processor.isValidUtf8(bArr, i, i2);
    }

    static int partialIsValidUtf8(int i, byte[] bArr, int i2, int i3) {
        return processor.partialIsValidUtf8(i, bArr, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(int i) {
        if (i > -12) {
            return -1;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(int i, int i2) {
        if (i > -12 || i2 > -65) {
            return -1;
        }
        return i ^ (i2 << 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(int i, int i2, int i3) {
        if (i > -12 || i2 > -65 || i3 > -65) {
            return -1;
        }
        return (i ^ (i2 << 8)) ^ (i3 << 16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(byte[] bArr, int i, int i2) {
        byte b = bArr[i - 1];
        switch (i2 - i) {
            case 0:
                return incompleteStateFor(b);
            case 1:
                return incompleteStateFor(b, bArr[i]);
            case 2:
                return incompleteStateFor(b, bArr[i], bArr[i + 1]);
            default:
                throw new java.lang.AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(java.nio.ByteBuffer byteBuffer, int i, int i2, int i3) {
        switch (i3) {
            case 0:
                return incompleteStateFor(i);
            case 1:
                return incompleteStateFor(i, byteBuffer.get(i2));
            case 2:
                return incompleteStateFor(i, byteBuffer.get(i2), byteBuffer.get(i2 + 1));
            default:
                throw new java.lang.AssertionError();
        }
    }

    static class UnpairedSurrogateException extends java.lang.IllegalArgumentException {
        UnpairedSurrogateException(int i, int i2) {
            super("Unpaired surrogate at index " + i + " of " + i2);
        }
    }

    static int encodedLength(java.lang.CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) < 128) {
            i++;
        }
        int i2 = length;
        while (true) {
            if (i < length) {
                char charAt = charSequence.charAt(i);
                if (charAt < 2048) {
                    i2 += (127 - charAt) >>> 31;
                    i++;
                } else {
                    i2 += encodedLengthGeneral(charSequence, i);
                    break;
                }
            } else {
                break;
            }
        }
        if (i2 < length) {
            throw new java.lang.IllegalArgumentException("UTF-8 length does not fit in int: " + (i2 + 4294967296L));
        }
        return i2;
    }

    private static int encodedLengthGeneral(java.lang.CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < 2048) {
                i2 += (127 - charAt) >>> 31;
            } else {
                i2 += 2;
                if (55296 <= charAt && charAt <= 57343) {
                    if (java.lang.Character.codePointAt(charSequence, i) < 65536) {
                        throw new com.android.framework.protobuf.Utf8.UnpairedSurrogateException(i, length);
                    }
                    i++;
                }
            }
            i++;
        }
        return i2;
    }

    static int encode(java.lang.CharSequence charSequence, byte[] bArr, int i, int i2) {
        return processor.encodeUtf8(charSequence, bArr, i, i2);
    }

    static boolean isValidUtf8(java.nio.ByteBuffer byteBuffer) {
        return processor.isValidUtf8(byteBuffer, byteBuffer.position(), byteBuffer.remaining());
    }

    static int partialIsValidUtf8(int i, java.nio.ByteBuffer byteBuffer, int i2, int i3) {
        return processor.partialIsValidUtf8(i, byteBuffer, i2, i3);
    }

    static java.lang.String decodeUtf8(java.nio.ByteBuffer byteBuffer, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return processor.decodeUtf8(byteBuffer, i, i2);
    }

    static java.lang.String decodeUtf8(byte[] bArr, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return processor.decodeUtf8(bArr, i, i2);
    }

    static void encodeUtf8(java.lang.CharSequence charSequence, java.nio.ByteBuffer byteBuffer) {
        processor.encodeUtf8(charSequence, byteBuffer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int estimateConsecutiveAscii(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        int i3 = i2 - 7;
        int i4 = i;
        while (i4 < i3 && (byteBuffer.getLong(i4) & ASCII_MASK_LONG) == 0) {
            i4 += 8;
        }
        return i4 - i;
    }

    static abstract class Processor {
        abstract java.lang.String decodeUtf8(byte[] bArr, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException;

        abstract java.lang.String decodeUtf8Direct(java.nio.ByteBuffer byteBuffer, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException;

        abstract int encodeUtf8(java.lang.CharSequence charSequence, byte[] bArr, int i, int i2);

        abstract void encodeUtf8Direct(java.lang.CharSequence charSequence, java.nio.ByteBuffer byteBuffer);

        abstract int partialIsValidUtf8(int i, byte[] bArr, int i2, int i3);

        abstract int partialIsValidUtf8Direct(int i, java.nio.ByteBuffer byteBuffer, int i2, int i3);

        Processor() {
        }

        final boolean isValidUtf8(byte[] bArr, int i, int i2) {
            return partialIsValidUtf8(0, bArr, i, i2) == 0;
        }

        final boolean isValidUtf8(java.nio.ByteBuffer byteBuffer, int i, int i2) {
            return partialIsValidUtf8(0, byteBuffer, i, i2) == 0;
        }

        final int partialIsValidUtf8(int i, java.nio.ByteBuffer byteBuffer, int i2, int i3) {
            if (byteBuffer.hasArray()) {
                int arrayOffset = byteBuffer.arrayOffset();
                return partialIsValidUtf8(i, byteBuffer.array(), i2 + arrayOffset, arrayOffset + i3);
            }
            if (byteBuffer.isDirect()) {
                return partialIsValidUtf8Direct(i, byteBuffer, i2, i3);
            }
            return partialIsValidUtf8Default(i, byteBuffer, i2, i3);
        }

        /* JADX WARN: Code restructure failed: missing block: B:51:0x0093, code lost:
        
            if (r8.get(r7) > (-65)) goto L55;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        final int partialIsValidUtf8Default(int i, java.nio.ByteBuffer byteBuffer, int i2, int i3) {
            byte b;
            int i4;
            if (i != 0) {
                if (i2 >= i3) {
                    return i;
                }
                byte b2 = (byte) i;
                if (b2 < -32) {
                    if (b2 >= -62) {
                        int i5 = i2 + 1;
                        if (byteBuffer.get(i2) <= -65) {
                            i2 = i5;
                        }
                    }
                    return -1;
                }
                if (b2 < -16) {
                    byte b3 = (byte) (~(i >> 8));
                    if (b3 == 0) {
                        int i6 = i2 + 1;
                        byte b4 = byteBuffer.get(i2);
                        if (i6 >= i3) {
                            return com.android.framework.protobuf.Utf8.incompleteStateFor(b2, b4);
                        }
                        i2 = i6;
                        b3 = b4;
                    }
                    if (b3 <= -65 && ((b2 != -32 || b3 >= -96) && (b2 != -19 || b3 < -96))) {
                        int i7 = i2 + 1;
                        if (byteBuffer.get(i2) <= -65) {
                            i2 = i7;
                        }
                    }
                    return -1;
                }
                byte b5 = (byte) (~(i >> 8));
                if (b5 == 0) {
                    i4 = i2 + 1;
                    b5 = byteBuffer.get(i2);
                    if (i4 >= i3) {
                        return com.android.framework.protobuf.Utf8.incompleteStateFor(b2, b5);
                    }
                    b = 0;
                } else {
                    b = (byte) (i >> 16);
                    i4 = i2;
                }
                if (b == 0) {
                    int i8 = i4 + 1;
                    byte b6 = byteBuffer.get(i4);
                    if (i8 >= i3) {
                        return com.android.framework.protobuf.Utf8.incompleteStateFor(b2, b5, b6);
                    }
                    b = b6;
                    i4 = i8;
                }
                if (b5 <= -65 && (((b2 << 28) + (b5 + 112)) >> 30) == 0 && b <= -65) {
                    i2 = i4 + 1;
                }
                return -1;
            }
            return partialIsValidUtf8(byteBuffer, i2, i3);
        }

        private static int partialIsValidUtf8(java.nio.ByteBuffer byteBuffer, int i, int i2) {
            int estimateConsecutiveAscii = i + com.android.framework.protobuf.Utf8.estimateConsecutiveAscii(byteBuffer, i, i2);
            while (estimateConsecutiveAscii < i2) {
                int i3 = estimateConsecutiveAscii + 1;
                byte b = byteBuffer.get(estimateConsecutiveAscii);
                if (b >= 0) {
                    estimateConsecutiveAscii = i3;
                } else if (b < -32) {
                    if (i3 >= i2) {
                        return b;
                    }
                    if (b < -62 || byteBuffer.get(i3) > -65) {
                        return -1;
                    }
                    estimateConsecutiveAscii = i3 + 1;
                } else if (b < -16) {
                    if (i3 >= i2 - 1) {
                        return com.android.framework.protobuf.Utf8.incompleteStateFor(byteBuffer, b, i3, i2 - i3);
                    }
                    int i4 = i3 + 1;
                    byte b2 = byteBuffer.get(i3);
                    if (b2 > -65 || ((b == -32 && b2 < -96) || ((b == -19 && b2 >= -96) || byteBuffer.get(i4) > -65))) {
                        return -1;
                    }
                    estimateConsecutiveAscii = i4 + 1;
                } else {
                    if (i3 >= i2 - 2) {
                        return com.android.framework.protobuf.Utf8.incompleteStateFor(byteBuffer, b, i3, i2 - i3);
                    }
                    int i5 = i3 + 1;
                    byte b3 = byteBuffer.get(i3);
                    if (b3 <= -65 && (((b << 28) + (b3 + 112)) >> 30) == 0) {
                        int i6 = i5 + 1;
                        if (byteBuffer.get(i5) <= -65) {
                            int i7 = i6 + 1;
                            if (byteBuffer.get(i6) <= -65) {
                                estimateConsecutiveAscii = i7;
                            }
                        }
                    }
                    return -1;
                }
            }
            return 0;
        }

        final java.lang.String decodeUtf8(java.nio.ByteBuffer byteBuffer, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if (byteBuffer.hasArray()) {
                return decodeUtf8(byteBuffer.array(), byteBuffer.arrayOffset() + i, i2);
            }
            if (byteBuffer.isDirect()) {
                return decodeUtf8Direct(byteBuffer, i, i2);
            }
            return decodeUtf8Default(byteBuffer, i, i2);
        }

        final java.lang.String decodeUtf8Default(java.nio.ByteBuffer byteBuffer, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if ((i | i2 | ((byteBuffer.limit() - i) - i2)) < 0) {
                throw new java.lang.ArrayIndexOutOfBoundsException(java.lang.String.format("buffer limit=%d, index=%d, limit=%d", java.lang.Integer.valueOf(byteBuffer.limit()), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
            }
            int i3 = i + i2;
            char[] cArr = new char[i2];
            int i4 = 0;
            while (i < i3) {
                byte b = byteBuffer.get(i);
                if (!com.android.framework.protobuf.Utf8.DecodeUtil.isOneByte(b)) {
                    break;
                }
                i++;
                com.android.framework.protobuf.Utf8.DecodeUtil.handleOneByte(b, cArr, i4);
                i4++;
            }
            int i5 = i4;
            while (i < i3) {
                int i6 = i + 1;
                byte b2 = byteBuffer.get(i);
                if (com.android.framework.protobuf.Utf8.DecodeUtil.isOneByte(b2)) {
                    int i7 = i5 + 1;
                    com.android.framework.protobuf.Utf8.DecodeUtil.handleOneByte(b2, cArr, i5);
                    while (i6 < i3) {
                        byte b3 = byteBuffer.get(i6);
                        if (!com.android.framework.protobuf.Utf8.DecodeUtil.isOneByte(b3)) {
                            break;
                        }
                        i6++;
                        com.android.framework.protobuf.Utf8.DecodeUtil.handleOneByte(b3, cArr, i7);
                        i7++;
                    }
                    i = i6;
                    i5 = i7;
                } else if (com.android.framework.protobuf.Utf8.DecodeUtil.isTwoBytes(b2)) {
                    if (i6 >= i3) {
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
                    }
                    com.android.framework.protobuf.Utf8.DecodeUtil.handleTwoBytes(b2, byteBuffer.get(i6), cArr, i5);
                    i = i6 + 1;
                    i5++;
                } else if (com.android.framework.protobuf.Utf8.DecodeUtil.isThreeBytes(b2)) {
                    if (i6 >= i3 - 1) {
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
                    }
                    int i8 = i6 + 1;
                    com.android.framework.protobuf.Utf8.DecodeUtil.handleThreeBytes(b2, byteBuffer.get(i6), byteBuffer.get(i8), cArr, i5);
                    i = i8 + 1;
                    i5++;
                } else {
                    if (i6 >= i3 - 2) {
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
                    }
                    int i9 = i6 + 1;
                    byte b4 = byteBuffer.get(i6);
                    int i10 = i9 + 1;
                    com.android.framework.protobuf.Utf8.DecodeUtil.handleFourBytes(b2, b4, byteBuffer.get(i9), byteBuffer.get(i10), cArr, i5);
                    i = i10 + 1;
                    i5 = i5 + 1 + 1;
                }
            }
            return new java.lang.String(cArr, 0, i5);
        }

        final void encodeUtf8(java.lang.CharSequence charSequence, java.nio.ByteBuffer byteBuffer) {
            if (byteBuffer.hasArray()) {
                int arrayOffset = byteBuffer.arrayOffset();
                byteBuffer.position(com.android.framework.protobuf.Utf8.encode(charSequence, byteBuffer.array(), byteBuffer.position() + arrayOffset, byteBuffer.remaining()) - arrayOffset);
            } else if (byteBuffer.isDirect()) {
                encodeUtf8Direct(charSequence, byteBuffer);
            } else {
                encodeUtf8Default(charSequence, byteBuffer);
            }
        }

        final void encodeUtf8Default(java.lang.CharSequence charSequence, java.nio.ByteBuffer byteBuffer) {
            int length = charSequence.length();
            int position = byteBuffer.position();
            int i = 0;
            while (i < length) {
                try {
                    char charAt = charSequence.charAt(i);
                    if (charAt >= 128) {
                        break;
                    }
                    byteBuffer.put(position + i, (byte) charAt);
                    i++;
                } catch (java.lang.IndexOutOfBoundsException e) {
                    throw new java.lang.ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i) + " at index " + (byteBuffer.position() + java.lang.Math.max(i, (position - byteBuffer.position()) + 1)));
                }
            }
            if (i == length) {
                byteBuffer.position(position + i);
                return;
            }
            position += i;
            while (i < length) {
                char charAt2 = charSequence.charAt(i);
                if (charAt2 < 128) {
                    byteBuffer.put(position, (byte) charAt2);
                } else if (charAt2 < 2048) {
                    int i2 = position + 1;
                    try {
                        byteBuffer.put(position, (byte) ((charAt2 >>> 6) | 192));
                        byteBuffer.put(i2, (byte) ((charAt2 & '?') | 128));
                        position = i2;
                    } catch (java.lang.IndexOutOfBoundsException e2) {
                        position = i2;
                        throw new java.lang.ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i) + " at index " + (byteBuffer.position() + java.lang.Math.max(i, (position - byteBuffer.position()) + 1)));
                    }
                } else {
                    if (charAt2 >= 55296 && 57343 >= charAt2) {
                        int i3 = i + 1;
                        if (i3 != length) {
                            try {
                                char charAt3 = charSequence.charAt(i3);
                                if (java.lang.Character.isSurrogatePair(charAt2, charAt3)) {
                                    int codePoint = java.lang.Character.toCodePoint(charAt2, charAt3);
                                    int i4 = position + 1;
                                    try {
                                        byteBuffer.put(position, (byte) ((codePoint >>> 18) | 240));
                                        int i5 = i4 + 1;
                                        byteBuffer.put(i4, (byte) (((codePoint >>> 12) & 63) | 128));
                                        int i6 = i5 + 1;
                                        byteBuffer.put(i5, (byte) (((codePoint >>> 6) & 63) | 128));
                                        byteBuffer.put(i6, (byte) ((codePoint & 63) | 128));
                                        position = i6;
                                        i = i3;
                                    } catch (java.lang.IndexOutOfBoundsException e3) {
                                        position = i4;
                                        i = i3;
                                        throw new java.lang.ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i) + " at index " + (byteBuffer.position() + java.lang.Math.max(i, (position - byteBuffer.position()) + 1)));
                                    }
                                } else {
                                    i = i3;
                                }
                            } catch (java.lang.IndexOutOfBoundsException e4) {
                            }
                        }
                        throw new com.android.framework.protobuf.Utf8.UnpairedSurrogateException(i, length);
                    }
                    int i7 = position + 1;
                    byteBuffer.put(position, (byte) ((charAt2 >>> '\f') | 224));
                    position = i7 + 1;
                    byteBuffer.put(i7, (byte) (((charAt2 >>> 6) & 63) | 128));
                    byteBuffer.put(position, (byte) ((charAt2 & '?') | 128));
                }
                i++;
                position++;
            }
            byteBuffer.position(position);
        }
    }

    static final class SafeProcessor extends com.android.framework.protobuf.Utf8.Processor {
        SafeProcessor() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:51:0x0087, code lost:
        
            if (r8[r7] > (-65)) goto L55;
         */
        @Override // com.android.framework.protobuf.Utf8.Processor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        int partialIsValidUtf8(int i, byte[] bArr, int i2, int i3) {
            byte b;
            int i4;
            if (i != 0) {
                if (i2 >= i3) {
                    return i;
                }
                byte b2 = (byte) i;
                if (b2 < -32) {
                    if (b2 >= -62) {
                        int i5 = i2 + 1;
                        if (bArr[i2] <= -65) {
                            i2 = i5;
                        }
                    }
                    return -1;
                }
                if (b2 < -16) {
                    byte b3 = (byte) (~(i >> 8));
                    if (b3 == 0) {
                        int i6 = i2 + 1;
                        byte b4 = bArr[i2];
                        if (i6 >= i3) {
                            return com.android.framework.protobuf.Utf8.incompleteStateFor(b2, b4);
                        }
                        i2 = i6;
                        b3 = b4;
                    }
                    if (b3 <= -65 && ((b2 != -32 || b3 >= -96) && (b2 != -19 || b3 < -96))) {
                        int i7 = i2 + 1;
                        if (bArr[i2] <= -65) {
                            i2 = i7;
                        }
                    }
                    return -1;
                }
                byte b5 = (byte) (~(i >> 8));
                if (b5 == 0) {
                    i4 = i2 + 1;
                    b5 = bArr[i2];
                    if (i4 >= i3) {
                        return com.android.framework.protobuf.Utf8.incompleteStateFor(b2, b5);
                    }
                    b = 0;
                } else {
                    b = (byte) (i >> 16);
                    i4 = i2;
                }
                if (b == 0) {
                    int i8 = i4 + 1;
                    byte b6 = bArr[i4];
                    if (i8 >= i3) {
                        return com.android.framework.protobuf.Utf8.incompleteStateFor(b2, b5, b6);
                    }
                    b = b6;
                    i4 = i8;
                }
                if (b5 <= -65 && (((b2 << 28) + (b5 + 112)) >> 30) == 0 && b <= -65) {
                    i2 = i4 + 1;
                }
                return -1;
            }
            return partialIsValidUtf8(bArr, i2, i3);
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        int partialIsValidUtf8Direct(int i, java.nio.ByteBuffer byteBuffer, int i2, int i3) {
            return partialIsValidUtf8Default(i, byteBuffer, i2, i3);
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        java.lang.String decodeUtf8(byte[] bArr, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if ((i | i2 | ((bArr.length - i) - i2)) < 0) {
                throw new java.lang.ArrayIndexOutOfBoundsException(java.lang.String.format("buffer length=%d, index=%d, size=%d", java.lang.Integer.valueOf(bArr.length), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
            }
            int i3 = i + i2;
            char[] cArr = new char[i2];
            int i4 = 0;
            while (i < i3) {
                byte b = bArr[i];
                if (!com.android.framework.protobuf.Utf8.DecodeUtil.isOneByte(b)) {
                    break;
                }
                i++;
                com.android.framework.protobuf.Utf8.DecodeUtil.handleOneByte(b, cArr, i4);
                i4++;
            }
            int i5 = i4;
            while (i < i3) {
                int i6 = i + 1;
                byte b2 = bArr[i];
                if (com.android.framework.protobuf.Utf8.DecodeUtil.isOneByte(b2)) {
                    int i7 = i5 + 1;
                    com.android.framework.protobuf.Utf8.DecodeUtil.handleOneByte(b2, cArr, i5);
                    while (i6 < i3) {
                        byte b3 = bArr[i6];
                        if (!com.android.framework.protobuf.Utf8.DecodeUtil.isOneByte(b3)) {
                            break;
                        }
                        i6++;
                        com.android.framework.protobuf.Utf8.DecodeUtil.handleOneByte(b3, cArr, i7);
                        i7++;
                    }
                    i = i6;
                    i5 = i7;
                } else if (com.android.framework.protobuf.Utf8.DecodeUtil.isTwoBytes(b2)) {
                    if (i6 >= i3) {
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
                    }
                    com.android.framework.protobuf.Utf8.DecodeUtil.handleTwoBytes(b2, bArr[i6], cArr, i5);
                    i = i6 + 1;
                    i5++;
                } else if (com.android.framework.protobuf.Utf8.DecodeUtil.isThreeBytes(b2)) {
                    if (i6 >= i3 - 1) {
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
                    }
                    int i8 = i6 + 1;
                    com.android.framework.protobuf.Utf8.DecodeUtil.handleThreeBytes(b2, bArr[i6], bArr[i8], cArr, i5);
                    i = i8 + 1;
                    i5++;
                } else {
                    if (i6 >= i3 - 2) {
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
                    }
                    int i9 = i6 + 1;
                    byte b4 = bArr[i6];
                    int i10 = i9 + 1;
                    com.android.framework.protobuf.Utf8.DecodeUtil.handleFourBytes(b2, b4, bArr[i9], bArr[i10], cArr, i5);
                    i = i10 + 1;
                    i5 = i5 + 1 + 1;
                }
            }
            return new java.lang.String(cArr, 0, i5);
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        java.lang.String decodeUtf8Direct(java.nio.ByteBuffer byteBuffer, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return decodeUtf8Default(byteBuffer, i, i2);
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x001f, code lost:
        
            return r10 + r0;
         */
        @Override // com.android.framework.protobuf.Utf8.Processor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        int encodeUtf8(java.lang.CharSequence charSequence, byte[] bArr, int i, int i2) {
            int i3;
            int i4;
            char charAt;
            int length = charSequence.length();
            int i5 = i2 + i;
            int i6 = 0;
            while (i6 < length && (i4 = i6 + i) < i5 && (charAt = charSequence.charAt(i6)) < 128) {
                bArr[i4] = (byte) charAt;
                i6++;
            }
            int i7 = i + i6;
            while (i6 < length) {
                char charAt2 = charSequence.charAt(i6);
                if (charAt2 < 128 && i7 < i5) {
                    bArr[i7] = (byte) charAt2;
                    i7++;
                } else if (charAt2 < 2048 && i7 <= i5 - 2) {
                    int i8 = i7 + 1;
                    bArr[i7] = (byte) ((charAt2 >>> 6) | 960);
                    i7 = i8 + 1;
                    bArr[i8] = (byte) ((charAt2 & '?') | 128);
                } else if ((charAt2 < 55296 || 57343 < charAt2) && i7 <= i5 - 3) {
                    int i9 = i7 + 1;
                    bArr[i7] = (byte) ((charAt2 >>> '\f') | 480);
                    int i10 = i9 + 1;
                    bArr[i9] = (byte) (((charAt2 >>> 6) & 63) | 128);
                    bArr[i10] = (byte) ((charAt2 & '?') | 128);
                    i7 = i10 + 1;
                } else {
                    if (i7 <= i5 - 4) {
                        int i11 = i6 + 1;
                        if (i11 != charSequence.length()) {
                            char charAt3 = charSequence.charAt(i11);
                            if (!java.lang.Character.isSurrogatePair(charAt2, charAt3)) {
                                i6 = i11;
                            } else {
                                int codePoint = java.lang.Character.toCodePoint(charAt2, charAt3);
                                int i12 = i7 + 1;
                                bArr[i7] = (byte) ((codePoint >>> 18) | 240);
                                int i13 = i12 + 1;
                                bArr[i12] = (byte) (((codePoint >>> 12) & 63) | 128);
                                int i14 = i13 + 1;
                                bArr[i13] = (byte) (((codePoint >>> 6) & 63) | 128);
                                i7 = i14 + 1;
                                bArr[i14] = (byte) ((codePoint & 63) | 128);
                                i6 = i11;
                            }
                        }
                        throw new com.android.framework.protobuf.Utf8.UnpairedSurrogateException(i6 - 1, length);
                    }
                    if (55296 <= charAt2 && charAt2 <= 57343 && ((i3 = i6 + 1) == charSequence.length() || !java.lang.Character.isSurrogatePair(charAt2, charSequence.charAt(i3)))) {
                        throw new com.android.framework.protobuf.Utf8.UnpairedSurrogateException(i6, length);
                    }
                    throw new java.lang.ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + i7);
                }
                i6++;
            }
            return i7;
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        void encodeUtf8Direct(java.lang.CharSequence charSequence, java.nio.ByteBuffer byteBuffer) {
            encodeUtf8Default(charSequence, byteBuffer);
        }

        private static int partialIsValidUtf8(byte[] bArr, int i, int i2) {
            while (i < i2 && bArr[i] >= 0) {
                i++;
            }
            if (i >= i2) {
                return 0;
            }
            return partialIsValidUtf8NonAscii(bArr, i, i2);
        }

        private static int partialIsValidUtf8NonAscii(byte[] bArr, int i, int i2) {
            while (i < i2) {
                int i3 = i + 1;
                byte b = bArr[i];
                if (b >= 0) {
                    i = i3;
                } else {
                    if (b < -32) {
                        if (i3 >= i2) {
                            return b;
                        }
                        if (b >= -62) {
                            i = i3 + 1;
                            if (bArr[i3] > -65) {
                            }
                        }
                        return -1;
                    }
                    if (b < -16) {
                        if (i3 >= i2 - 1) {
                            return com.android.framework.protobuf.Utf8.incompleteStateFor(bArr, i3, i2);
                        }
                        int i4 = i3 + 1;
                        byte b2 = bArr[i3];
                        if (b2 <= -65 && ((b != -32 || b2 >= -96) && (b != -19 || b2 < -96))) {
                            i = i4 + 1;
                            if (bArr[i4] > -65) {
                            }
                        }
                        return -1;
                    }
                    if (i3 >= i2 - 2) {
                        return com.android.framework.protobuf.Utf8.incompleteStateFor(bArr, i3, i2);
                    }
                    int i5 = i3 + 1;
                    byte b3 = bArr[i3];
                    if (b3 <= -65 && (((b << 28) + (b3 + 112)) >> 30) == 0) {
                        int i6 = i5 + 1;
                        if (bArr[i5] <= -65) {
                            int i7 = i6 + 1;
                            if (bArr[i6] <= -65) {
                                i = i7;
                            }
                        }
                    }
                    return -1;
                }
            }
            return 0;
        }
    }

    static final class UnsafeProcessor extends com.android.framework.protobuf.Utf8.Processor {
        UnsafeProcessor() {
        }

        static boolean isAvailable() {
            return com.android.framework.protobuf.UnsafeUtil.hasUnsafeArrayOperations() && com.android.framework.protobuf.UnsafeUtil.hasUnsafeByteBufferOperations();
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        int partialIsValidUtf8(int i, byte[] bArr, int i2, int i3) {
            byte b;
            if ((i2 | i3 | (bArr.length - i3)) < 0) {
                throw new java.lang.ArrayIndexOutOfBoundsException(java.lang.String.format("Array length=%d, index=%d, limit=%d", java.lang.Integer.valueOf(bArr.length), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3)));
            }
            long j = i2;
            long j2 = i3;
            if (i != 0) {
                if (j >= j2) {
                    return i;
                }
                byte b2 = (byte) i;
                if (b2 < -32) {
                    if (b2 >= -62) {
                        long j3 = 1 + j;
                        if (com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j) <= -65) {
                            j = j3;
                        }
                    }
                    return -1;
                }
                if (b2 < -16) {
                    byte b3 = (byte) (~(i >> 8));
                    if (b3 == 0) {
                        long j4 = j + 1;
                        b3 = com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j);
                        if (j4 >= j2) {
                            return com.android.framework.protobuf.Utf8.incompleteStateFor(b2, b3);
                        }
                        j = j4;
                    }
                    if (b3 <= -65 && ((b2 != -32 || b3 >= -96) && (b2 != -19 || b3 < -96))) {
                        long j5 = j + 1;
                        if (com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j) <= -65) {
                            j = j5;
                        }
                    }
                    return -1;
                }
                byte b4 = (byte) (~(i >> 8));
                if (b4 == 0) {
                    long j6 = j + 1;
                    b4 = com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j);
                    if (j6 >= j2) {
                        return com.android.framework.protobuf.Utf8.incompleteStateFor(b2, b4);
                    }
                    b = 0;
                    j = j6;
                } else {
                    b = (byte) (i >> 16);
                }
                if (b == 0) {
                    long j7 = j + 1;
                    b = com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j);
                    if (j7 >= j2) {
                        return com.android.framework.protobuf.Utf8.incompleteStateFor(b2, b4, b);
                    }
                    j = j7;
                }
                if (b4 <= -65 && (((b2 << 28) + (b4 + 112)) >> 30) == 0 && b <= -65) {
                    long j8 = j + 1;
                    if (com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j) <= -65) {
                        j = j8;
                    }
                }
                return -1;
            }
            return partialIsValidUtf8(bArr, j, (int) (j2 - j));
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        int partialIsValidUtf8Direct(int i, java.nio.ByteBuffer byteBuffer, int i2, int i3) {
            byte b;
            if ((i2 | i3 | (byteBuffer.limit() - i3)) < 0) {
                throw new java.lang.ArrayIndexOutOfBoundsException(java.lang.String.format("buffer limit=%d, index=%d, limit=%d", java.lang.Integer.valueOf(byteBuffer.limit()), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3)));
            }
            long addressOffset = com.android.framework.protobuf.UnsafeUtil.addressOffset(byteBuffer) + i2;
            long j = (i3 - i2) + addressOffset;
            if (i != 0) {
                if (addressOffset >= j) {
                    return i;
                }
                byte b2 = (byte) i;
                if (b2 < -32) {
                    if (b2 >= -62) {
                        long j2 = 1 + addressOffset;
                        if (com.android.framework.protobuf.UnsafeUtil.getByte(addressOffset) <= -65) {
                            addressOffset = j2;
                        }
                    }
                    return -1;
                }
                if (b2 < -16) {
                    byte b3 = (byte) (~(i >> 8));
                    if (b3 == 0) {
                        long j3 = addressOffset + 1;
                        b3 = com.android.framework.protobuf.UnsafeUtil.getByte(addressOffset);
                        if (j3 >= j) {
                            return com.android.framework.protobuf.Utf8.incompleteStateFor(b2, b3);
                        }
                        addressOffset = j3;
                    }
                    if (b3 <= -65 && ((b2 != -32 || b3 >= -96) && (b2 != -19 || b3 < -96))) {
                        long j4 = 1 + addressOffset;
                        if (com.android.framework.protobuf.UnsafeUtil.getByte(addressOffset) <= -65) {
                            addressOffset = j4;
                        }
                    }
                    return -1;
                }
                byte b4 = (byte) (~(i >> 8));
                if (b4 == 0) {
                    long j5 = addressOffset + 1;
                    b4 = com.android.framework.protobuf.UnsafeUtil.getByte(addressOffset);
                    if (j5 >= j) {
                        return com.android.framework.protobuf.Utf8.incompleteStateFor(b2, b4);
                    }
                    b = 0;
                    addressOffset = j5;
                } else {
                    b = (byte) (i >> 16);
                }
                if (b == 0) {
                    long j6 = addressOffset + 1;
                    b = com.android.framework.protobuf.UnsafeUtil.getByte(addressOffset);
                    if (j6 >= j) {
                        return com.android.framework.protobuf.Utf8.incompleteStateFor(b2, b4, b);
                    }
                    addressOffset = j6;
                }
                if (b4 <= -65 && (((b2 << 28) + (b4 + 112)) >> 30) == 0 && b <= -65) {
                    long j7 = 1 + addressOffset;
                    if (com.android.framework.protobuf.UnsafeUtil.getByte(addressOffset) <= -65) {
                        addressOffset = j7;
                    }
                }
                return -1;
            }
            return partialIsValidUtf8(addressOffset, (int) (j - addressOffset));
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        java.lang.String decodeUtf8(byte[] bArr, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            java.lang.String str = new java.lang.String(bArr, i, i2, com.android.framework.protobuf.Internal.UTF_8);
            if (!str.contains("�")) {
                return str;
            }
            if (java.util.Arrays.equals(str.getBytes(com.android.framework.protobuf.Internal.UTF_8), java.util.Arrays.copyOfRange(bArr, i, i2 + i))) {
                return str;
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        java.lang.String decodeUtf8Direct(java.nio.ByteBuffer byteBuffer, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if ((i | i2 | ((byteBuffer.limit() - i) - i2)) >= 0) {
                long addressOffset = com.android.framework.protobuf.UnsafeUtil.addressOffset(byteBuffer) + i;
                long j = i2 + addressOffset;
                char[] cArr = new char[i2];
                int i3 = 0;
                while (addressOffset < j) {
                    byte b = com.android.framework.protobuf.UnsafeUtil.getByte(addressOffset);
                    if (!com.android.framework.protobuf.Utf8.DecodeUtil.isOneByte(b)) {
                        break;
                    }
                    addressOffset++;
                    com.android.framework.protobuf.Utf8.DecodeUtil.handleOneByte(b, cArr, i3);
                    i3++;
                }
                int i4 = i3;
                while (addressOffset < j) {
                    long j2 = addressOffset + 1;
                    byte b2 = com.android.framework.protobuf.UnsafeUtil.getByte(addressOffset);
                    if (com.android.framework.protobuf.Utf8.DecodeUtil.isOneByte(b2)) {
                        int i5 = i4 + 1;
                        com.android.framework.protobuf.Utf8.DecodeUtil.handleOneByte(b2, cArr, i4);
                        while (j2 < j) {
                            byte b3 = com.android.framework.protobuf.UnsafeUtil.getByte(j2);
                            if (!com.android.framework.protobuf.Utf8.DecodeUtil.isOneByte(b3)) {
                                break;
                            }
                            j2++;
                            com.android.framework.protobuf.Utf8.DecodeUtil.handleOneByte(b3, cArr, i5);
                            i5++;
                        }
                        i4 = i5;
                        addressOffset = j2;
                    } else if (com.android.framework.protobuf.Utf8.DecodeUtil.isTwoBytes(b2)) {
                        if (j2 >= j) {
                            throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
                        }
                        com.android.framework.protobuf.Utf8.DecodeUtil.handleTwoBytes(b2, com.android.framework.protobuf.UnsafeUtil.getByte(j2), cArr, i4);
                        i4++;
                        addressOffset = j2 + 1;
                    } else if (com.android.framework.protobuf.Utf8.DecodeUtil.isThreeBytes(b2)) {
                        if (j2 >= j - 1) {
                            throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
                        }
                        long j3 = j2 + 1;
                        com.android.framework.protobuf.Utf8.DecodeUtil.handleThreeBytes(b2, com.android.framework.protobuf.UnsafeUtil.getByte(j2), com.android.framework.protobuf.UnsafeUtil.getByte(j3), cArr, i4);
                        addressOffset = j3 + 1;
                        i4++;
                    } else {
                        if (j2 >= j - 2) {
                            throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
                        }
                        long j4 = j2 + 1;
                        long j5 = j4 + 1;
                        com.android.framework.protobuf.Utf8.DecodeUtil.handleFourBytes(b2, com.android.framework.protobuf.UnsafeUtil.getByte(j2), com.android.framework.protobuf.UnsafeUtil.getByte(j4), com.android.framework.protobuf.UnsafeUtil.getByte(j5), cArr, i4);
                        i4 = i4 + 1 + 1;
                        addressOffset = j5 + 1;
                    }
                }
                return new java.lang.String(cArr, 0, i4);
            }
            throw new java.lang.ArrayIndexOutOfBoundsException(java.lang.String.format("buffer limit=%d, index=%d, limit=%d", java.lang.Integer.valueOf(byteBuffer.limit()), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        int encodeUtf8(java.lang.CharSequence charSequence, byte[] bArr, int i, int i2) {
            char c;
            long j;
            long j2;
            long j3;
            char c2;
            int i3;
            char charAt;
            long j4 = i;
            long j5 = i2 + j4;
            int length = charSequence.length();
            if (length > i2 || bArr.length - i2 < i) {
                throw new java.lang.ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(length - 1) + " at index " + (i + i2));
            }
            int i4 = 0;
            while (true) {
                c = 128;
                j = 1;
                if (i4 >= length || (charAt = charSequence.charAt(i4)) >= 128) {
                    break;
                }
                com.android.framework.protobuf.UnsafeUtil.putByte(bArr, j4, (byte) charAt);
                i4++;
                j4 = 1 + j4;
            }
            if (i4 == length) {
                return (int) j4;
            }
            while (i4 < length) {
                char charAt2 = charSequence.charAt(i4);
                if (charAt2 < c && j4 < j5) {
                    long j6 = j4 + j;
                    com.android.framework.protobuf.UnsafeUtil.putByte(bArr, j4, (byte) charAt2);
                    j3 = j;
                    j2 = j6;
                    c2 = c;
                } else if (charAt2 < 2048 && j4 <= j5 - 2) {
                    long j7 = j4 + j;
                    com.android.framework.protobuf.UnsafeUtil.putByte(bArr, j4, (byte) ((charAt2 >>> 6) | 960));
                    long j8 = j7 + j;
                    com.android.framework.protobuf.UnsafeUtil.putByte(bArr, j7, (byte) ((charAt2 & '?') | 128));
                    long j9 = j;
                    c2 = 128;
                    j2 = j8;
                    j3 = j9;
                } else {
                    if ((charAt2 >= 55296 && 57343 >= charAt2) || j4 > j5 - 3) {
                        if (j4 <= j5 - 4) {
                            int i5 = i4 + 1;
                            if (i5 != length) {
                                char charAt3 = charSequence.charAt(i5);
                                if (!java.lang.Character.isSurrogatePair(charAt2, charAt3)) {
                                    i4 = i5;
                                } else {
                                    int codePoint = java.lang.Character.toCodePoint(charAt2, charAt3);
                                    long j10 = j4 + 1;
                                    com.android.framework.protobuf.UnsafeUtil.putByte(bArr, j4, (byte) ((codePoint >>> 18) | 240));
                                    long j11 = j10 + 1;
                                    c2 = 128;
                                    com.android.framework.protobuf.UnsafeUtil.putByte(bArr, j10, (byte) (((codePoint >>> 12) & 63) | 128));
                                    long j12 = j11 + 1;
                                    com.android.framework.protobuf.UnsafeUtil.putByte(bArr, j11, (byte) (((codePoint >>> 6) & 63) | 128));
                                    j3 = 1;
                                    j2 = j12 + 1;
                                    com.android.framework.protobuf.UnsafeUtil.putByte(bArr, j12, (byte) ((codePoint & 63) | 128));
                                    i4 = i5;
                                }
                            }
                            throw new com.android.framework.protobuf.Utf8.UnpairedSurrogateException(i4 - 1, length);
                        }
                        if (55296 <= charAt2 && charAt2 <= 57343 && ((i3 = i4 + 1) == length || !java.lang.Character.isSurrogatePair(charAt2, charSequence.charAt(i3)))) {
                            throw new com.android.framework.protobuf.Utf8.UnpairedSurrogateException(i4, length);
                        }
                        throw new java.lang.ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + j4);
                    }
                    long j13 = j4 + j;
                    com.android.framework.protobuf.UnsafeUtil.putByte(bArr, j4, (byte) ((charAt2 >>> '\f') | 480));
                    long j14 = j13 + j;
                    com.android.framework.protobuf.UnsafeUtil.putByte(bArr, j13, (byte) (((charAt2 >>> 6) & 63) | 128));
                    com.android.framework.protobuf.UnsafeUtil.putByte(bArr, j14, (byte) ((charAt2 & '?') | 128));
                    j2 = j14 + 1;
                    j3 = 1;
                    c2 = 128;
                }
                i4++;
                c = c2;
                long j15 = j3;
                j4 = j2;
                j = j15;
            }
            return (int) j4;
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        void encodeUtf8Direct(java.lang.CharSequence charSequence, java.nio.ByteBuffer byteBuffer) {
            char c;
            long j;
            long j2;
            int i;
            char c2;
            int i2;
            char charAt;
            long addressOffset = com.android.framework.protobuf.UnsafeUtil.addressOffset(byteBuffer);
            long position = byteBuffer.position() + addressOffset;
            long limit = byteBuffer.limit() + addressOffset;
            int length = charSequence.length();
            if (length > limit - position) {
                throw new java.lang.ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(length - 1) + " at index " + byteBuffer.limit());
            }
            int i3 = 0;
            while (true) {
                c = 128;
                if (i3 >= length || (charAt = charSequence.charAt(i3)) >= 128) {
                    break;
                }
                com.android.framework.protobuf.UnsafeUtil.putByte(position, (byte) charAt);
                i3++;
                position++;
            }
            if (i3 == length) {
                byteBuffer.position((int) (position - addressOffset));
                return;
            }
            while (i3 < length) {
                char charAt2 = charSequence.charAt(i3);
                if (charAt2 < c && position < limit) {
                    com.android.framework.protobuf.UnsafeUtil.putByte(position, (byte) charAt2);
                    j2 = limit;
                    i = i3;
                    c2 = c;
                    position++;
                    j = addressOffset;
                } else if (charAt2 >= 2048 || position > limit - 2) {
                    j = addressOffset;
                    if ((charAt2 >= 55296 && 57343 >= charAt2) || position > limit - 3) {
                        if (position <= limit - 4) {
                            i = i3 + 1;
                            if (i != length) {
                                char charAt3 = charSequence.charAt(i);
                                if (!java.lang.Character.isSurrogatePair(charAt2, charAt3)) {
                                    i3 = i;
                                } else {
                                    int codePoint = java.lang.Character.toCodePoint(charAt2, charAt3);
                                    j2 = limit;
                                    long j3 = position + 1;
                                    com.android.framework.protobuf.UnsafeUtil.putByte(position, (byte) ((codePoint >>> 18) | 240));
                                    long j4 = j3 + 1;
                                    c2 = 128;
                                    com.android.framework.protobuf.UnsafeUtil.putByte(j3, (byte) (((codePoint >>> 12) & 63) | 128));
                                    long j5 = j4 + 1;
                                    com.android.framework.protobuf.UnsafeUtil.putByte(j4, (byte) (((codePoint >>> 6) & 63) | 128));
                                    com.android.framework.protobuf.UnsafeUtil.putByte(j5, (byte) ((codePoint & 63) | 128));
                                    position = j5 + 1;
                                }
                            }
                            throw new com.android.framework.protobuf.Utf8.UnpairedSurrogateException(i3 - 1, length);
                        }
                        if (55296 <= charAt2 && charAt2 <= 57343 && ((i2 = i3 + 1) == length || !java.lang.Character.isSurrogatePair(charAt2, charSequence.charAt(i2)))) {
                            throw new com.android.framework.protobuf.Utf8.UnpairedSurrogateException(i3, length);
                        }
                        throw new java.lang.ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + position);
                    }
                    long j6 = position + 1;
                    com.android.framework.protobuf.UnsafeUtil.putByte(position, (byte) ((charAt2 >>> '\f') | 480));
                    long j7 = j6 + 1;
                    com.android.framework.protobuf.UnsafeUtil.putByte(j6, (byte) (((charAt2 >>> 6) & 63) | 128));
                    com.android.framework.protobuf.UnsafeUtil.putByte(j7, (byte) ((charAt2 & '?') | 128));
                    position = j7 + 1;
                    j2 = limit;
                    i = i3;
                    c2 = 128;
                } else {
                    j = addressOffset;
                    long j8 = position + 1;
                    com.android.framework.protobuf.UnsafeUtil.putByte(position, (byte) ((charAt2 >>> 6) | 960));
                    com.android.framework.protobuf.UnsafeUtil.putByte(j8, (byte) ((charAt2 & '?') | 128));
                    position = j8 + 1;
                    j2 = limit;
                    i = i3;
                    c2 = 128;
                }
                c = c2;
                addressOffset = j;
                limit = j2;
                i3 = i + 1;
            }
            byteBuffer.position((int) (position - addressOffset));
        }

        private static int unsafeEstimateConsecutiveAscii(byte[] bArr, long j, int i) {
            int i2 = 0;
            if (i < 16) {
                return 0;
            }
            int i3 = 8 - (((int) j) & 7);
            while (i2 < i3) {
                long j2 = 1 + j;
                if (com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j) >= 0) {
                    i2++;
                    j = j2;
                } else {
                    return i2;
                }
            }
            while (true) {
                int i4 = i2 + 8;
                if (i4 > i || (com.android.framework.protobuf.UnsafeUtil.getLong((java.lang.Object) bArr, com.android.framework.protobuf.UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + j) & com.android.framework.protobuf.Utf8.ASCII_MASK_LONG) != 0) {
                    break;
                }
                j += 8;
                i2 = i4;
            }
            while (i2 < i) {
                long j3 = j + 1;
                if (com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j) >= 0) {
                    i2++;
                    j = j3;
                } else {
                    return i2;
                }
            }
            return i;
        }

        private static int unsafeEstimateConsecutiveAscii(long j, int i) {
            if (i < 16) {
                return 0;
            }
            int i2 = (int) ((-j) & 7);
            int i3 = i2;
            while (i3 > 0) {
                long j2 = 1 + j;
                if (com.android.framework.protobuf.UnsafeUtil.getByte(j) >= 0) {
                    i3--;
                    j = j2;
                } else {
                    return i2 - i3;
                }
            }
            int i4 = i - i2;
            while (i4 >= 8 && (com.android.framework.protobuf.UnsafeUtil.getLong(j) & com.android.framework.protobuf.Utf8.ASCII_MASK_LONG) == 0) {
                j += 8;
                i4 -= 8;
            }
            return i - i4;
        }

        /* JADX WARN: Code restructure failed: missing block: B:71:0x0039, code lost:
        
            return -1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static int partialIsValidUtf8(byte[] bArr, long j, int i) {
            int unsafeEstimateConsecutiveAscii = unsafeEstimateConsecutiveAscii(bArr, j, i);
            int i2 = i - unsafeEstimateConsecutiveAscii;
            long j2 = j + unsafeEstimateConsecutiveAscii;
            while (true) {
                byte b = 0;
                while (true) {
                    if (i2 <= 0) {
                        break;
                    }
                    long j3 = j2 + 1;
                    b = com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j2);
                    if (b < 0) {
                        j2 = j3;
                        break;
                    }
                    i2--;
                    j2 = j3;
                }
                if (i2 == 0) {
                    return 0;
                }
                int i3 = i2 - 1;
                if (b < -32) {
                    if (i3 == 0) {
                        return b;
                    }
                    i2 = i3 - 1;
                    if (b < -62) {
                        break;
                    }
                    long j4 = 1 + j2;
                    if (com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j2) > -65) {
                        break;
                    }
                    j2 = j4;
                } else if (b < -16) {
                    if (i3 < 2) {
                        return unsafeIncompleteStateFor(bArr, b, j2, i3);
                    }
                    i2 = i3 - 2;
                    long j5 = j2 + 1;
                    byte b2 = com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j2);
                    if (b2 > -65 || ((b == -32 && b2 < -96) || (b == -19 && b2 >= -96))) {
                        break;
                    }
                    long j6 = 1 + j5;
                    if (com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j5) > -65) {
                        break;
                    }
                    j2 = j6;
                } else {
                    if (i3 < 3) {
                        return unsafeIncompleteStateFor(bArr, b, j2, i3);
                    }
                    i2 = i3 - 3;
                    long j7 = j2 + 1;
                    byte b3 = com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j2);
                    if (b3 > -65 || (((b << 28) + (b3 + 112)) >> 30) != 0) {
                        break;
                    }
                    long j8 = j7 + 1;
                    if (com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j7) > -65) {
                        break;
                    }
                    long j9 = 1 + j8;
                    if (com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j8) > -65) {
                        break;
                    }
                    j2 = j9;
                }
            }
            return -1;
        }

        /* JADX WARN: Code restructure failed: missing block: B:71:0x0039, code lost:
        
            return -1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static int partialIsValidUtf8(long j, int i) {
            int unsafeEstimateConsecutiveAscii = unsafeEstimateConsecutiveAscii(j, i);
            long j2 = j + unsafeEstimateConsecutiveAscii;
            int i2 = i - unsafeEstimateConsecutiveAscii;
            while (true) {
                byte b = 0;
                while (true) {
                    if (i2 <= 0) {
                        break;
                    }
                    long j3 = j2 + 1;
                    b = com.android.framework.protobuf.UnsafeUtil.getByte(j2);
                    if (b < 0) {
                        j2 = j3;
                        break;
                    }
                    i2--;
                    j2 = j3;
                }
                if (i2 == 0) {
                    return 0;
                }
                int i3 = i2 - 1;
                if (b < -32) {
                    if (i3 == 0) {
                        return b;
                    }
                    i2 = i3 - 1;
                    if (b < -62) {
                        break;
                    }
                    long j4 = 1 + j2;
                    if (com.android.framework.protobuf.UnsafeUtil.getByte(j2) > -65) {
                        break;
                    }
                    j2 = j4;
                } else if (b < -16) {
                    if (i3 < 2) {
                        return unsafeIncompleteStateFor(j2, b, i3);
                    }
                    i2 = i3 - 2;
                    long j5 = j2 + 1;
                    byte b2 = com.android.framework.protobuf.UnsafeUtil.getByte(j2);
                    if (b2 > -65 || ((b == -32 && b2 < -96) || (b == -19 && b2 >= -96))) {
                        break;
                    }
                    long j6 = 1 + j5;
                    if (com.android.framework.protobuf.UnsafeUtil.getByte(j5) > -65) {
                        break;
                    }
                    j2 = j6;
                } else {
                    if (i3 < 3) {
                        return unsafeIncompleteStateFor(j2, b, i3);
                    }
                    i2 = i3 - 3;
                    long j7 = j2 + 1;
                    byte b3 = com.android.framework.protobuf.UnsafeUtil.getByte(j2);
                    if (b3 > -65 || (((b << 28) + (b3 + 112)) >> 30) != 0) {
                        break;
                    }
                    long j8 = j7 + 1;
                    if (com.android.framework.protobuf.UnsafeUtil.getByte(j7) > -65) {
                        break;
                    }
                    long j9 = 1 + j8;
                    if (com.android.framework.protobuf.UnsafeUtil.getByte(j8) > -65) {
                        break;
                    }
                    j2 = j9;
                }
            }
            return -1;
        }

        private static int unsafeIncompleteStateFor(byte[] bArr, int i, long j, int i2) {
            switch (i2) {
                case 0:
                    return com.android.framework.protobuf.Utf8.incompleteStateFor(i);
                case 1:
                    return com.android.framework.protobuf.Utf8.incompleteStateFor(i, com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j));
                case 2:
                    return com.android.framework.protobuf.Utf8.incompleteStateFor(i, com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j), com.android.framework.protobuf.UnsafeUtil.getByte(bArr, j + 1));
                default:
                    throw new java.lang.AssertionError();
            }
        }

        private static int unsafeIncompleteStateFor(long j, int i, int i2) {
            switch (i2) {
                case 0:
                    return com.android.framework.protobuf.Utf8.incompleteStateFor(i);
                case 1:
                    return com.android.framework.protobuf.Utf8.incompleteStateFor(i, com.android.framework.protobuf.UnsafeUtil.getByte(j));
                case 2:
                    return com.android.framework.protobuf.Utf8.incompleteStateFor(i, com.android.framework.protobuf.UnsafeUtil.getByte(j), com.android.framework.protobuf.UnsafeUtil.getByte(j + 1));
                default:
                    throw new java.lang.AssertionError();
            }
        }
    }

    private static class DecodeUtil {
        private DecodeUtil() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isOneByte(byte b) {
            return b >= 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isTwoBytes(byte b) {
            return b < -32;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isThreeBytes(byte b) {
            return b < -16;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void handleOneByte(byte b, char[] cArr, int i) {
            cArr[i] = (char) b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void handleTwoBytes(byte b, byte b2, char[] cArr, int i) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if (b < -62 || isNotTrailingByte(b2)) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
            }
            cArr[i] = (char) (((b & 31) << 6) | trailingByteValue(b2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void handleThreeBytes(byte b, byte b2, byte b3, char[] cArr, int i) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if (isNotTrailingByte(b2) || ((b == -32 && b2 < -96) || ((b == -19 && b2 >= -96) || isNotTrailingByte(b3)))) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
            }
            cArr[i] = (char) (((b & 15) << 12) | (trailingByteValue(b2) << 6) | trailingByteValue(b3));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void handleFourBytes(byte b, byte b2, byte b3, byte b4, char[] cArr, int i) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if (isNotTrailingByte(b2) || (((b << 28) + (b2 + 112)) >> 30) != 0 || isNotTrailingByte(b3) || isNotTrailingByte(b4)) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
            }
            int trailingByteValue = ((b & 7) << 18) | (trailingByteValue(b2) << 12) | (trailingByteValue(b3) << 6) | trailingByteValue(b4);
            cArr[i] = highSurrogate(trailingByteValue);
            cArr[i + 1] = lowSurrogate(trailingByteValue);
        }

        private static boolean isNotTrailingByte(byte b) {
            return b > -65;
        }

        private static int trailingByteValue(byte b) {
            return b & 63;
        }

        private static char highSurrogate(int i) {
            return (char) ((i >>> 10) + 55232);
        }

        private static char lowSurrogate(int i) {
            return (char) ((i & 1023) + 56320);
        }
    }

    private Utf8() {
    }
}
