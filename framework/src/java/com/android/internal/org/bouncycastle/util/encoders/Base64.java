package com.android.internal.org.bouncycastle.util.encoders;

/* loaded from: classes4.dex */
public class Base64 {
    private static final com.android.internal.org.bouncycastle.util.encoders.Encoder encoder = new com.android.internal.org.bouncycastle.util.encoders.Base64Encoder();

    public static java.lang.String toBase64String(byte[] bArr) {
        return toBase64String(bArr, 0, bArr.length);
    }

    public static java.lang.String toBase64String(byte[] bArr, int i, int i2) {
        return com.android.internal.org.bouncycastle.util.Strings.fromByteArray(encode(bArr, i, i2));
    }

    public static byte[] encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length);
    }

    public static byte[] encode(byte[] bArr, int i, int i2) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(((i2 + 2) / 3) * 4);
        try {
            encoder.encode(bArr, i, i2, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.util.encoders.EncoderException("exception encoding base64 string: " + e.getMessage(), e);
        }
    }

    public static int encode(byte[] bArr, java.io.OutputStream outputStream) throws java.io.IOException {
        return encoder.encode(bArr, 0, bArr.length, outputStream);
    }

    public static int encode(byte[] bArr, int i, int i2, java.io.OutputStream outputStream) throws java.io.IOException {
        return encoder.encode(bArr, i, i2, outputStream);
    }

    public static byte[] decode(byte[] bArr) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream((bArr.length / 4) * 3);
        try {
            encoder.decode(bArr, 0, bArr.length, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.util.encoders.DecoderException("unable to decode base64 data: " + e.getMessage(), e);
        }
    }

    public static byte[] decode(java.lang.String str) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream((str.length() / 4) * 3);
        try {
            encoder.decode(str, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.util.encoders.DecoderException("unable to decode base64 string: " + e.getMessage(), e);
        }
    }

    public static int decode(java.lang.String str, java.io.OutputStream outputStream) throws java.io.IOException {
        return encoder.decode(str, outputStream);
    }

    public static int decode(byte[] bArr, int i, int i2, java.io.OutputStream outputStream) {
        try {
            return encoder.decode(bArr, i, i2, outputStream);
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.util.encoders.DecoderException("unable to decode base64 data: " + e.getMessage(), e);
        }
    }
}
