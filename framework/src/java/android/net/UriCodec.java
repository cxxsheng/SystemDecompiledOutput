package android.net;

/* loaded from: classes2.dex */
public final class UriCodec {
    private static final char INVALID_INPUT_CHARACTER = 65533;

    private UriCodec() {
    }

    private static int hexCharToValue(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        if ('a' <= c && c <= 'f') {
            return (c + '\n') - 97;
        }
        if ('A' <= c && c <= 'F') {
            return (c + '\n') - 65;
        }
        return -1;
    }

    private static java.net.URISyntaxException unexpectedCharacterException(java.lang.String str, java.lang.String str2, char c, int i) {
        return new java.net.URISyntaxException(str, "Unexpected character" + (str2 == null ? "" : " in [" + str2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END) + ": " + c, i);
    }

    private static char getNextCharacter(java.lang.String str, int i, int i2, java.lang.String str2) throws java.net.URISyntaxException {
        if (i >= i2) {
            throw new java.net.URISyntaxException(str, "Unexpected end of string" + (str2 == null ? "" : " in [" + str2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END), i);
        }
        return str.charAt(i);
    }

    public static java.lang.String decode(java.lang.String str, boolean z, java.nio.charset.Charset charset, boolean z2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str.length());
        appendDecoded(sb, str, z, charset, z2);
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x008b, code lost:
    
        r0.put(r4);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void appendDecoded(java.lang.StringBuilder sb, java.lang.String str, boolean z, java.nio.charset.Charset charset, boolean z2) {
        java.nio.charset.CharsetDecoder onUnmappableCharacter = charset.newDecoder().onMalformedInput(java.nio.charset.CodingErrorAction.REPLACE).replaceWith("�").onUnmappableCharacter(java.nio.charset.CodingErrorAction.REPORT);
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(str.length());
        int i = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            i++;
            switch (charAt) {
                case '%':
                    int i2 = 0;
                    byte b = 0;
                    while (true) {
                        if (i2 >= 2) {
                            break;
                        } else {
                            try {
                                char nextCharacter = getNextCharacter(str, i, str.length(), null);
                                i++;
                                int hexCharToValue = hexCharToValue(nextCharacter);
                                if (hexCharToValue < 0) {
                                    if (z2) {
                                        throw new java.lang.IllegalArgumentException(unexpectedCharacterException(str, null, nextCharacter, i - 1));
                                    }
                                    flushDecodingByteAccumulator(sb, onUnmappableCharacter, allocate, z2);
                                    sb.append(INVALID_INPUT_CHARACTER);
                                    break;
                                } else {
                                    b = (byte) ((b * 16) + hexCharToValue);
                                    i2++;
                                }
                            } catch (java.net.URISyntaxException e) {
                                if (z2) {
                                    throw new java.lang.IllegalArgumentException(e);
                                }
                                flushDecodingByteAccumulator(sb, onUnmappableCharacter, allocate, z2);
                                sb.append(INVALID_INPUT_CHARACTER);
                                return;
                            }
                        }
                    }
                case '+':
                    flushDecodingByteAccumulator(sb, onUnmappableCharacter, allocate, z2);
                    sb.append(z ? ' ' : '+');
                    break;
                default:
                    flushDecodingByteAccumulator(sb, onUnmappableCharacter, allocate, z2);
                    sb.append(charAt);
                    break;
            }
        }
        flushDecodingByteAccumulator(sb, onUnmappableCharacter, allocate, z2);
    }

    private static void flushDecodingByteAccumulator(java.lang.StringBuilder sb, java.nio.charset.CharsetDecoder charsetDecoder, java.nio.ByteBuffer byteBuffer, boolean z) {
        if (byteBuffer.position() == 0) {
            return;
        }
        byteBuffer.flip();
        try {
            try {
                sb.append((java.lang.CharSequence) charsetDecoder.decode(byteBuffer));
            } catch (java.nio.charset.CharacterCodingException e) {
                if (z) {
                    throw new java.lang.IllegalArgumentException(e);
                }
                sb.append(INVALID_INPUT_CHARACTER);
            }
        } finally {
            byteBuffer.flip();
            byteBuffer.limit(byteBuffer.capacity());
        }
    }
}
