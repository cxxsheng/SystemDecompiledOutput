package com.android.internal.org.bouncycastle.util.encoders;

/* loaded from: classes4.dex */
public interface Encoder {
    int decode(java.lang.String str, java.io.OutputStream outputStream) throws java.io.IOException;

    int decode(byte[] bArr, int i, int i2, java.io.OutputStream outputStream) throws java.io.IOException;

    int encode(byte[] bArr, int i, int i2, java.io.OutputStream outputStream) throws java.io.IOException;
}
