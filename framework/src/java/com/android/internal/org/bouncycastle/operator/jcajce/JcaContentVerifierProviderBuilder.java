package com.android.internal.org.bouncycastle.operator.jcajce;

/* loaded from: classes4.dex */
public class JcaContentVerifierProviderBuilder {
    private com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper helper = new com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper(new com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper());

    public com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder setProvider(java.security.Provider provider) {
        this.helper = new com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper(new com.android.internal.org.bouncycastle.jcajce.util.ProviderJcaJceHelper(provider));
        return this;
    }

    public com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder setProvider(java.lang.String str) {
        this.helper = new com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper(new com.android.internal.org.bouncycastle.jcajce.util.NamedJcaJceHelper(str));
        return this;
    }

    public com.android.internal.org.bouncycastle.operator.ContentVerifierProvider build(com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException, java.security.cert.CertificateException {
        return build(this.helper.convertCertificate(x509CertificateHolder));
    }

    public com.android.internal.org.bouncycastle.operator.ContentVerifierProvider build(final java.security.cert.X509Certificate x509Certificate) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        try {
            final com.android.internal.org.bouncycastle.cert.jcajce.JcaX509CertificateHolder jcaX509CertificateHolder = new com.android.internal.org.bouncycastle.cert.jcajce.JcaX509CertificateHolder(x509Certificate);
            return new com.android.internal.org.bouncycastle.operator.ContentVerifierProvider() { // from class: com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.1
                @Override // com.android.internal.org.bouncycastle.operator.ContentVerifierProvider
                public boolean hasAssociatedCertificate() {
                    return true;
                }

                @Override // com.android.internal.org.bouncycastle.operator.ContentVerifierProvider
                public com.android.internal.org.bouncycastle.cert.X509CertificateHolder getAssociatedCertificate() {
                    return jcaX509CertificateHolder;
                }

                @Override // com.android.internal.org.bouncycastle.operator.ContentVerifierProvider
                public com.android.internal.org.bouncycastle.operator.ContentVerifier get(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
                    if (algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.id_alg_composite)) {
                        return com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.createCompositeVerifier(algorithmIdentifier, x509Certificate.getPublicKey());
                    }
                    try {
                        java.security.Signature createSignature = com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.helper.createSignature(algorithmIdentifier);
                        createSignature.initVerify(x509Certificate.getPublicKey());
                        java.security.Signature createRawSig = com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.createRawSig(algorithmIdentifier, x509Certificate.getPublicKey());
                        if (createRawSig != null) {
                            return com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.new RawSigVerifier(algorithmIdentifier, createSignature, createRawSig);
                        }
                        return com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.new SigVerifier(algorithmIdentifier, createSignature);
                    } catch (java.security.GeneralSecurityException e) {
                        throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("exception on setup: " + e, e);
                    }
                }
            };
        } catch (java.security.cert.CertificateEncodingException e) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot process certificate: " + e.getMessage(), e);
        }
    }

    public com.android.internal.org.bouncycastle.operator.ContentVerifierProvider build(final java.security.PublicKey publicKey) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        return new com.android.internal.org.bouncycastle.operator.ContentVerifierProvider() { // from class: com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.2
            @Override // com.android.internal.org.bouncycastle.operator.ContentVerifierProvider
            public boolean hasAssociatedCertificate() {
                return false;
            }

            @Override // com.android.internal.org.bouncycastle.operator.ContentVerifierProvider
            public com.android.internal.org.bouncycastle.cert.X509CertificateHolder getAssociatedCertificate() {
                return null;
            }

            @Override // com.android.internal.org.bouncycastle.operator.ContentVerifierProvider
            public com.android.internal.org.bouncycastle.operator.ContentVerifier get(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
                if (algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.id_alg_composite)) {
                    return com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.createCompositeVerifier(algorithmIdentifier, publicKey);
                }
                if (publicKey instanceof com.android.internal.org.bouncycastle.jcajce.CompositePublicKey) {
                    java.util.List<java.security.PublicKey> publicKeys = ((com.android.internal.org.bouncycastle.jcajce.CompositePublicKey) publicKey).getPublicKeys();
                    for (int i = 0; i != publicKeys.size(); i++) {
                        try {
                            java.security.Signature createSignature = com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.createSignature(algorithmIdentifier, publicKeys.get(i));
                            java.security.Signature createRawSig = com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.createRawSig(algorithmIdentifier, publicKeys.get(i));
                            if (createRawSig != null) {
                                return com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.new RawSigVerifier(algorithmIdentifier, createSignature, createRawSig);
                            }
                            return com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.new SigVerifier(algorithmIdentifier, createSignature);
                        } catch (com.android.internal.org.bouncycastle.operator.OperatorCreationException e) {
                        }
                    }
                    throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("no matching algorithm found for key");
                }
                java.security.Signature createSignature2 = com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.createSignature(algorithmIdentifier, publicKey);
                java.security.Signature createRawSig2 = com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.createRawSig(algorithmIdentifier, publicKey);
                if (createRawSig2 != null) {
                    return com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.new RawSigVerifier(algorithmIdentifier, createSignature2, createRawSig2);
                }
                return com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.this.new SigVerifier(algorithmIdentifier, createSignature2);
            }
        };
    }

    public com.android.internal.org.bouncycastle.operator.ContentVerifierProvider build(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        return build(this.helper.convertPublicKey(subjectPublicKeyInfo));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.internal.org.bouncycastle.operator.ContentVerifier createCompositeVerifier(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, java.security.PublicKey publicKey) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        int i = 0;
        if (publicKey instanceof com.android.internal.org.bouncycastle.jcajce.CompositePublicKey) {
            java.util.List<java.security.PublicKey> publicKeys = ((com.android.internal.org.bouncycastle.jcajce.CompositePublicKey) publicKey).getPublicKeys();
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(algorithmIdentifier.getParameters());
            java.security.Signature[] signatureArr = new java.security.Signature[aSN1Sequence.size()];
            while (i != aSN1Sequence.size()) {
                com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2 = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i));
                if (publicKeys.get(i) != null) {
                    signatureArr[i] = createSignature(algorithmIdentifier2, publicKeys.get(i));
                } else {
                    signatureArr[i] = null;
                }
                i++;
            }
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.CompositeVerifier(signatureArr);
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(algorithmIdentifier.getParameters());
        java.security.Signature[] signatureArr2 = new java.security.Signature[aSN1Sequence2.size()];
        while (i != aSN1Sequence2.size()) {
            try {
                signatureArr2[i] = createSignature(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence2.getObjectAt(i)), publicKey);
            } catch (java.lang.Exception e) {
                signatureArr2[i] = null;
            }
            i++;
        }
        return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.CompositeVerifier(signatureArr2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.security.Signature createSignature(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, java.security.PublicKey publicKey) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        try {
            java.security.Signature createSignature = this.helper.createSignature(algorithmIdentifier);
            createSignature.initVerify(publicKey);
            return createSignature;
        } catch (java.security.GeneralSecurityException e) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("exception on setup: " + e, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.security.Signature createRawSig(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, java.security.PublicKey publicKey) {
        try {
            java.security.Signature createRawSignature = this.helper.createRawSignature(algorithmIdentifier);
            if (createRawSignature != null) {
                createRawSignature.initVerify(publicKey);
                return createRawSignature;
            }
            return createRawSignature;
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    private class SigVerifier implements com.android.internal.org.bouncycastle.operator.ContentVerifier {
        private final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithm;
        private final java.security.Signature signature;
        protected final java.io.OutputStream stream;

        SigVerifier(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, java.security.Signature signature) {
            this.algorithm = algorithmIdentifier;
            this.signature = signature;
            this.stream = com.android.internal.org.bouncycastle.jcajce.io.OutputStreamFactory.createStream(signature);
        }

        @Override // com.android.internal.org.bouncycastle.operator.ContentVerifier
        public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmIdentifier() {
            return this.algorithm;
        }

        @Override // com.android.internal.org.bouncycastle.operator.ContentVerifier
        public java.io.OutputStream getOutputStream() {
            if (this.stream == null) {
                throw new java.lang.IllegalStateException("verifier not initialised");
            }
            return this.stream;
        }

        @Override // com.android.internal.org.bouncycastle.operator.ContentVerifier
        public boolean verify(byte[] bArr) {
            try {
                return this.signature.verify(bArr);
            } catch (java.security.SignatureException e) {
                throw new com.android.internal.org.bouncycastle.operator.RuntimeOperatorException("exception obtaining signature: " + e.getMessage(), e);
            }
        }
    }

    private class RawSigVerifier extends com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.SigVerifier implements com.android.internal.org.bouncycastle.operator.RawContentVerifier {
        private java.security.Signature rawSignature;

        RawSigVerifier(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, java.security.Signature signature, java.security.Signature signature2) {
            super(algorithmIdentifier, signature);
            this.rawSignature = signature2;
        }

        @Override // com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.SigVerifier, com.android.internal.org.bouncycastle.operator.ContentVerifier
        public boolean verify(byte[] bArr) {
            try {
                return super.verify(bArr);
            } finally {
                try {
                    this.rawSignature.verify(bArr);
                } catch (java.lang.Exception e) {
                }
            }
        }

        @Override // com.android.internal.org.bouncycastle.operator.RawContentVerifier
        public boolean verify(byte[] bArr, byte[] bArr2) {
            try {
                try {
                    this.rawSignature.update(bArr);
                    return this.rawSignature.verify(bArr2);
                } catch (java.security.SignatureException e) {
                    throw new com.android.internal.org.bouncycastle.operator.RuntimeOperatorException("exception obtaining raw signature: " + e.getMessage(), e);
                }
            } finally {
                try {
                    this.rawSignature.verify(bArr2);
                } catch (java.lang.Exception e2) {
                }
            }
        }
    }

    private class CompositeVerifier implements com.android.internal.org.bouncycastle.operator.ContentVerifier {
        private java.security.Signature[] sigs;
        private java.io.OutputStream stream;

        public CompositeVerifier(java.security.Signature[] signatureArr) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            this.sigs = signatureArr;
            int i = 0;
            while (i < signatureArr.length && signatureArr[i] == null) {
                i++;
            }
            if (i == signatureArr.length) {
                throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("no matching signature found in composite");
            }
            this.stream = com.android.internal.org.bouncycastle.jcajce.io.OutputStreamFactory.createStream(signatureArr[i]);
            while (true) {
                i++;
                if (i != signatureArr.length) {
                    if (signatureArr[i] != null) {
                        this.stream = new com.android.internal.org.bouncycastle.util.io.TeeOutputStream(this.stream, com.android.internal.org.bouncycastle.jcajce.io.OutputStreamFactory.createStream(signatureArr[i]));
                    }
                } else {
                    return;
                }
            }
        }

        @Override // com.android.internal.org.bouncycastle.operator.ContentVerifier
        public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmIdentifier() {
            return new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.id_alg_composite);
        }

        @Override // com.android.internal.org.bouncycastle.operator.ContentVerifier
        public java.io.OutputStream getOutputStream() {
            return this.stream;
        }

        @Override // com.android.internal.org.bouncycastle.operator.ContentVerifier
        public boolean verify(byte[] bArr) {
            try {
                com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(bArr);
                boolean z = false;
                for (int i = 0; i != aSN1Sequence.size(); i++) {
                    if (this.sigs[i] != null && !this.sigs[i].verify(com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1Sequence.getObjectAt(i)).getBytes())) {
                        z = true;
                    }
                }
                return !z;
            } catch (java.security.SignatureException e) {
                throw new com.android.internal.org.bouncycastle.operator.RuntimeOperatorException("exception obtaining signature: " + e.getMessage(), e);
            }
        }
    }
}
