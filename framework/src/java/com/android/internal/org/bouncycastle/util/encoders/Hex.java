package com.android.internal.org.bouncycastle.util.encoders;

/* loaded from: classes4.dex */
public class Hex {
    private static final com.android.internal.org.bouncycastle.util.encoders.HexEncoder encoder = new com.android.internal.org.bouncycastle.util.encoders.HexEncoder();

    public static java.lang.String toHexString(byte[] bArr) {
        return toHexString(bArr, 0, bArr.length);
    }

    public static java.lang.String toHexString(byte[] bArr, int i, int i2) {
        return com.android.internal.org.bouncycastle.util.Strings.fromByteArray(encode(bArr, i, i2));
    }

    public static byte[] encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length);
    }

    public static byte[] encode(byte[] bArr, int i, int i2) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            encoder.encode(bArr, i, i2, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.util.encoders.EncoderException("exception encoding Hex string: " + e.getMessage(), e);
        }
    }

    public static int encode(byte[] bArr, java.io.OutputStream outputStream) throws java.io.IOException {
        return encoder.encode(bArr, 0, bArr.length, outputStream);
    }

    public static int encode(byte[] bArr, int i, int i2, java.io.OutputStream outputStream) throws java.io.IOException {
        return encoder.encode(bArr, i, i2, outputStream);
    }

    public static byte[] decode(byte[] bArr) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            encoder.decode(bArr, 0, bArr.length, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.util.encoders.DecoderException("exception decoding Hex data: " + e.getMessage(), e);
        }
    }

    public static byte[] decode(java.lang.String str) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            encoder.decode(str, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.util.encoders.DecoderException("exception decoding Hex string: " + e.getMessage(), e);
        }
    }

    public static int decode(java.lang.String str, java.io.OutputStream outputStream) throws java.io.IOException {
        return encoder.decode(str, outputStream);
    }

    public static byte[] decodeStrict(java.lang.String str) {
        try {
            return encoder.decodeStrict(str, 0, str.length());
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.util.encoders.DecoderException("exception decoding Hex string: " + e.getMessage(), e);
        }
    }

    public static byte[] decodeStrict(java.lang.String str, int i, int i2) {
        try {
            return encoder.decodeStrict(str, i, i2);
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.util.encoders.DecoderException("exception decoding Hex string: " + e.getMessage(), e);
        }
    }
}
