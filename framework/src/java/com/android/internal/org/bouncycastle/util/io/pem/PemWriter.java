package com.android.internal.org.bouncycastle.util.io.pem;

/* loaded from: classes4.dex */
public class PemWriter extends java.io.BufferedWriter {
    private static final int LINE_LENGTH = 64;
    private char[] buf;
    private final int nlLength;

    public PemWriter(java.io.Writer writer) {
        super(writer);
        this.buf = new char[64];
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        if (lineSeparator != null) {
            this.nlLength = lineSeparator.length();
        } else {
            this.nlLength = 2;
        }
    }

    public int getOutputSize(com.android.internal.org.bouncycastle.util.io.pem.PemObject pemObject) {
        int length = ((pemObject.getType().length() + 10 + this.nlLength) * 2) + 6 + 4;
        if (!pemObject.getHeaders().isEmpty()) {
            for (com.android.internal.org.bouncycastle.util.io.pem.PemHeader pemHeader : pemObject.getHeaders()) {
                length += pemHeader.getName().length() + ": ".length() + pemHeader.getValue().length() + this.nlLength;
            }
            length += this.nlLength;
        }
        return length + (((pemObject.getContent().length + 2) / 3) * 4) + ((((r6 + 64) - 1) / 64) * this.nlLength);
    }

    public void writeObject(com.android.internal.org.bouncycastle.util.io.pem.PemObjectGenerator pemObjectGenerator) throws java.io.IOException {
        com.android.internal.org.bouncycastle.util.io.pem.PemObject generate = pemObjectGenerator.generate();
        writePreEncapsulationBoundary(generate.getType());
        if (!generate.getHeaders().isEmpty()) {
            for (com.android.internal.org.bouncycastle.util.io.pem.PemHeader pemHeader : generate.getHeaders()) {
                write(pemHeader.getName());
                write(": ");
                write(pemHeader.getValue());
                newLine();
            }
            newLine();
        }
        writeEncoded(generate.getContent());
        writePostEncapsulationBoundary(generate.getType());
    }

    private void writeEncoded(byte[] bArr) throws java.io.IOException {
        int i;
        byte[] encode = com.android.internal.org.bouncycastle.util.encoders.Base64.encode(bArr);
        int i2 = 0;
        while (i2 < encode.length) {
            int i3 = 0;
            while (i3 != this.buf.length && (i = i2 + i3) < encode.length) {
                this.buf[i3] = (char) encode[i];
                i3++;
            }
            write(this.buf, 0, i3);
            newLine();
            i2 += this.buf.length;
        }
    }

    private void writePreEncapsulationBoundary(java.lang.String str) throws java.io.IOException {
        write("-----BEGIN " + str + "-----");
        newLine();
    }

    private void writePostEncapsulationBoundary(java.lang.String str) throws java.io.IOException {
        write("-----END " + str + "-----");
        newLine();
    }
}
