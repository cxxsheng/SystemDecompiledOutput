package com.android.internal.org.bouncycastle.operator.jcajce;

/* loaded from: classes4.dex */
public class JcaDigestCalculatorProviderBuilder {
    private com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper helper = new com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper(new com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper());

    public com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder setProvider(java.security.Provider provider) {
        this.helper = new com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper(new com.android.internal.org.bouncycastle.jcajce.util.ProviderJcaJceHelper(provider));
        return this;
    }

    public com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder setProvider(java.lang.String str) {
        this.helper = new com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper(new com.android.internal.org.bouncycastle.jcajce.util.NamedJcaJceHelper(str));
        return this;
    }

    public com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider build() throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        return new com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider() { // from class: com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder.1
            @Override // com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider
            public com.android.internal.org.bouncycastle.operator.DigestCalculator get(final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
                try {
                    final com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder.DigestOutputStream digestOutputStream = com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder.this.new DigestOutputStream(com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder.this.helper.createDigest(algorithmIdentifier));
                    return new com.android.internal.org.bouncycastle.operator.DigestCalculator() { // from class: com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder.1.1
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
                } catch (java.security.GeneralSecurityException e) {
                    throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("exception on setup: " + e, e);
                }
            }
        };
    }

    private class DigestOutputStream extends java.io.OutputStream {
        private java.security.MessageDigest dig;

        DigestOutputStream(java.security.MessageDigest messageDigest) {
            this.dig = messageDigest;
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
            this.dig.update(bArr, i, i2);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws java.io.IOException {
            this.dig.update(bArr);
        }

        @Override // java.io.OutputStream
        public void write(int i) throws java.io.IOException {
            this.dig.update((byte) i);
        }

        byte[] getDigest() {
            return this.dig.digest();
        }
    }
}
