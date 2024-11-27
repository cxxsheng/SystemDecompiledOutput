package com.android.internal.org.bouncycastle.operator.bc;

/* loaded from: classes4.dex */
public class BcDigestCalculatorProvider implements com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider {
    private com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider digestProvider = com.android.internal.org.bouncycastle.operator.bc.BcDefaultDigestProvider.INSTANCE;

    @Override // com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider
    public com.android.internal.org.bouncycastle.operator.DigestCalculator get(final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        final com.android.internal.org.bouncycastle.operator.bc.BcDigestCalculatorProvider.DigestOutputStream digestOutputStream = new com.android.internal.org.bouncycastle.operator.bc.BcDigestCalculatorProvider.DigestOutputStream(this.digestProvider.get(algorithmIdentifier));
        return new com.android.internal.org.bouncycastle.operator.DigestCalculator() { // from class: com.android.internal.org.bouncycastle.operator.bc.BcDigestCalculatorProvider.1
            @Override // com.android.internal.org.bouncycastle.operator.DigestCalculator
            public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmIdentifier() {
                return algorithmIdentifier;
            }

            @Override // com.android.internal.org.bouncycastle.operator.DigestCalculator
            public java.io.OutputStream getOutputStream() {
                return digestOutputStream;
            }

            @Override // com.android.internal.org.bouncycastle.operator.DigestCalculator
            public byte[] getDigest() {
                return digestOutputStream.getDigest();
            }
        };
    }

    private class DigestOutputStream extends java.io.OutputStream {
        private com.android.internal.org.bouncycastle.crypto.Digest dig;

        DigestOutputStream(com.android.internal.org.bouncycastle.crypto.Digest digest) {
            this.dig = digest;
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
            this.dig.update(bArr, i, i2);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws java.io.IOException {
            this.dig.update(bArr, 0, bArr.length);
        }

        @Override // java.io.OutputStream
        public void write(int i) throws java.io.IOException {
            this.dig.update((byte) i);
        }

        byte[] getDigest() {
            byte[] bArr = new byte[this.dig.getDigestSize()];
            this.dig.doFinal(bArr, 0);
            return bArr;
        }
    }
}
