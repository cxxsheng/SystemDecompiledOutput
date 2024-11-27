package com.android.internal.org.bouncycastle.operator.jcajce;

/* loaded from: classes4.dex */
public class JcaContentSignerBuilder {
    private com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper helper;
    private java.security.SecureRandom random;
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier sigAlgId;
    private java.security.spec.AlgorithmParameterSpec sigAlgSpec;
    private java.lang.String signatureAlgorithm;

    public JcaContentSignerBuilder(java.lang.String str) {
        this.helper = new com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper(new com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper());
        this.signatureAlgorithm = str;
        this.sigAlgId = new com.android.internal.org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder().find(str);
        this.sigAlgSpec = null;
    }

    public JcaContentSignerBuilder(java.lang.String str, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) {
        this.helper = new com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper(new com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper());
        this.signatureAlgorithm = str;
        if (algorithmParameterSpec instanceof java.security.spec.PSSParameterSpec) {
            java.security.spec.PSSParameterSpec pSSParameterSpec = (java.security.spec.PSSParameterSpec) algorithmParameterSpec;
            this.sigAlgSpec = pSSParameterSpec;
            this.sigAlgId = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS, createPSSParams(pSSParameterSpec));
        } else {
            if (algorithmParameterSpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.CompositeAlgorithmSpec) {
                com.android.internal.org.bouncycastle.jcajce.spec.CompositeAlgorithmSpec compositeAlgorithmSpec = (com.android.internal.org.bouncycastle.jcajce.spec.CompositeAlgorithmSpec) algorithmParameterSpec;
                this.sigAlgSpec = compositeAlgorithmSpec;
                this.sigAlgId = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.id_alg_composite, createCompParams(compositeAlgorithmSpec));
                return;
            }
            throw new java.lang.IllegalArgumentException("unknown sigParamSpec: " + (algorithmParameterSpec == null ? "null" : algorithmParameterSpec.getClass().getName()));
        }
    }

    public com.android.internal.org.bouncycastle.operator.jcajce.JcaContentSignerBuilder setProvider(java.security.Provider provider) {
        this.helper = new com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper(new com.android.internal.org.bouncycastle.jcajce.util.ProviderJcaJceHelper(provider));
        return this;
    }

    public com.android.internal.org.bouncycastle.operator.jcajce.JcaContentSignerBuilder setProvider(java.lang.String str) {
        this.helper = new com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper(new com.android.internal.org.bouncycastle.jcajce.util.NamedJcaJceHelper(str));
        return this;
    }

    public com.android.internal.org.bouncycastle.operator.jcajce.JcaContentSignerBuilder setSecureRandom(java.security.SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }

    public com.android.internal.org.bouncycastle.operator.ContentSigner build(java.security.PrivateKey privateKey) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        if (privateKey instanceof com.android.internal.org.bouncycastle.jcajce.CompositePrivateKey) {
            return buildComposite((com.android.internal.org.bouncycastle.jcajce.CompositePrivateKey) privateKey);
        }
        try {
            final java.security.Signature createSignature = this.helper.createSignature(this.sigAlgId);
            final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier = this.sigAlgId;
            if (this.random != null) {
                createSignature.initSign(privateKey, this.random);
            } else {
                createSignature.initSign(privateKey);
            }
            return new com.android.internal.org.bouncycastle.operator.ContentSigner() { // from class: com.android.internal.org.bouncycastle.operator.jcajce.JcaContentSignerBuilder.1
                private java.io.OutputStream stream;

                {
                    this.stream = com.android.internal.org.bouncycastle.jcajce.io.OutputStreamFactory.createStream(createSignature);
                }

                @Override // com.android.internal.org.bouncycastle.operator.ContentSigner
                public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmIdentifier() {
                    return algorithmIdentifier;
                }

                @Override // com.android.internal.org.bouncycastle.operator.ContentSigner
                public java.io.OutputStream getOutputStream() {
                    return this.stream;
                }

                @Override // com.android.internal.org.bouncycastle.operator.ContentSigner
                public byte[] getSignature() {
                    try {
                        return createSignature.sign();
                    } catch (java.security.SignatureException e) {
                        throw new com.android.internal.org.bouncycastle.operator.RuntimeOperatorException("exception obtaining signature: " + e.getMessage(), e);
                    }
                }
            };
        } catch (java.security.GeneralSecurityException e) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot create signer: " + e.getMessage(), e);
        }
    }

    private com.android.internal.org.bouncycastle.operator.ContentSigner buildComposite(com.android.internal.org.bouncycastle.jcajce.CompositePrivateKey compositePrivateKey) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        try {
            java.util.List<java.security.PrivateKey> privateKeys = compositePrivateKey.getPrivateKeys();
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(this.sigAlgId.getParameters());
            int size = aSN1Sequence.size();
            final java.security.Signature[] signatureArr = new java.security.Signature[size];
            for (int i = 0; i != aSN1Sequence.size(); i++) {
                signatureArr[i] = this.helper.createSignature(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i)));
                if (this.random != null) {
                    signatureArr[i].initSign(privateKeys.get(i), this.random);
                } else {
                    signatureArr[i].initSign(privateKeys.get(i));
                }
            }
            final java.io.OutputStream createStream = com.android.internal.org.bouncycastle.jcajce.io.OutputStreamFactory.createStream(signatureArr[0]);
            int i2 = 1;
            while (i2 != size) {
                com.android.internal.org.bouncycastle.util.io.TeeOutputStream teeOutputStream = new com.android.internal.org.bouncycastle.util.io.TeeOutputStream(createStream, com.android.internal.org.bouncycastle.jcajce.io.OutputStreamFactory.createStream(signatureArr[i2]));
                i2++;
                createStream = teeOutputStream;
            }
            return new com.android.internal.org.bouncycastle.operator.ContentSigner() { // from class: com.android.internal.org.bouncycastle.operator.jcajce.JcaContentSignerBuilder.2
                java.io.OutputStream stream;

                {
                    this.stream = createStream;
                }

                @Override // com.android.internal.org.bouncycastle.operator.ContentSigner
                public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmIdentifier() {
                    return com.android.internal.org.bouncycastle.operator.jcajce.JcaContentSignerBuilder.this.sigAlgId;
                }

                @Override // com.android.internal.org.bouncycastle.operator.ContentSigner
                public java.io.OutputStream getOutputStream() {
                    return this.stream;
                }

                @Override // com.android.internal.org.bouncycastle.operator.ContentSigner
                public byte[] getSignature() {
                    try {
                        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                        for (int i3 = 0; i3 != signatureArr.length; i3++) {
                            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERBitString(signatureArr[i3].sign()));
                        }
                        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
                    } catch (java.io.IOException e) {
                        throw new com.android.internal.org.bouncycastle.operator.RuntimeOperatorException("exception encoding signature: " + e.getMessage(), e);
                    } catch (java.security.SignatureException e2) {
                        throw new com.android.internal.org.bouncycastle.operator.RuntimeOperatorException("exception obtaining signature: " + e2.getMessage(), e2);
                    }
                }
            };
        } catch (java.security.GeneralSecurityException e) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot create signer: " + e.getMessage(), e);
        }
    }

    private static com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams createPSSParams(java.security.spec.PSSParameterSpec pSSParameterSpec) {
        com.android.internal.org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder defaultDigestAlgorithmIdentifierFinder = new com.android.internal.org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder();
        return new com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams(defaultDigestAlgorithmIdentifierFinder.find(pSSParameterSpec.getDigestAlgorithm()), new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_mgf1, defaultDigestAlgorithmIdentifierFinder.find(((java.security.spec.MGF1ParameterSpec) pSSParameterSpec.getMGFParameters()).getDigestAlgorithm())), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(pSSParameterSpec.getSaltLength()), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(pSSParameterSpec.getTrailerField()));
    }

    private static com.android.internal.org.bouncycastle.asn1.ASN1Sequence createCompParams(com.android.internal.org.bouncycastle.jcajce.spec.CompositeAlgorithmSpec compositeAlgorithmSpec) {
        com.android.internal.org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder defaultSignatureAlgorithmIdentifierFinder = new com.android.internal.org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder();
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        java.util.List<java.lang.String> algorithmNames = compositeAlgorithmSpec.getAlgorithmNames();
        java.util.List<java.security.spec.AlgorithmParameterSpec> parameterSpecs = compositeAlgorithmSpec.getParameterSpecs();
        for (int i = 0; i != algorithmNames.size(); i++) {
            java.security.spec.AlgorithmParameterSpec algorithmParameterSpec = parameterSpecs.get(i);
            if (algorithmParameterSpec == null) {
                aSN1EncodableVector.add(defaultSignatureAlgorithmIdentifierFinder.find(algorithmNames.get(i)));
            } else if (algorithmParameterSpec instanceof java.security.spec.PSSParameterSpec) {
                aSN1EncodableVector.add(createPSSParams((java.security.spec.PSSParameterSpec) algorithmParameterSpec));
            } else {
                throw new java.lang.IllegalArgumentException("unrecognized parameterSpec");
            }
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
