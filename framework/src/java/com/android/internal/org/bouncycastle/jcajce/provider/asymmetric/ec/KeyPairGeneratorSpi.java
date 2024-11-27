package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec;

/* loaded from: classes4.dex */
public abstract class KeyPairGeneratorSpi extends java.security.KeyPairGenerator {
    public KeyPairGeneratorSpi(java.lang.String str) {
        super(str);
    }

    public static class EC extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi {
        private static java.util.Hashtable ecParameters = new java.util.Hashtable();
        java.lang.String algorithm;
        com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration configuration;
        java.lang.Object ecParams;
        com.android.internal.org.bouncycastle.crypto.generators.ECKeyPairGenerator engine;
        boolean initialised;
        com.android.internal.org.bouncycastle.crypto.params.ECKeyGenerationParameters param;
        java.security.SecureRandom random;
        int strength;

        static {
            ecParameters.put(com.android.internal.org.bouncycastle.util.Integers.valueOf(192), new java.security.spec.ECGenParameterSpec("prime192v1"));
            ecParameters.put(com.android.internal.org.bouncycastle.util.Integers.valueOf(239), new java.security.spec.ECGenParameterSpec("prime239v1"));
            ecParameters.put(com.android.internal.org.bouncycastle.util.Integers.valueOf(256), new java.security.spec.ECGenParameterSpec("prime256v1"));
            ecParameters.put(com.android.internal.org.bouncycastle.util.Integers.valueOf(224), new java.security.spec.ECGenParameterSpec("P-224"));
            ecParameters.put(com.android.internal.org.bouncycastle.util.Integers.valueOf(384), new java.security.spec.ECGenParameterSpec("P-384"));
            ecParameters.put(com.android.internal.org.bouncycastle.util.Integers.valueOf(521), new java.security.spec.ECGenParameterSpec("P-521"));
        }

        public EC() {
            super(android.security.keystore.KeyProperties.KEY_ALGORITHM_EC);
            this.engine = new com.android.internal.org.bouncycastle.crypto.generators.ECKeyPairGenerator();
            this.ecParams = null;
            this.strength = 256;
            this.random = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
            this.initialised = false;
            this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
            this.configuration = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION;
        }

        public EC(java.lang.String str, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
            super(str);
            this.engine = new com.android.internal.org.bouncycastle.crypto.generators.ECKeyPairGenerator();
            this.ecParams = null;
            this.strength = 256;
            this.random = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
            this.initialised = false;
            this.algorithm = str;
            this.configuration = providerConfiguration;
        }

        @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
        public void initialize(int i, java.security.SecureRandom secureRandom) {
            this.strength = i;
            if (secureRandom != null) {
                this.random = secureRandom;
            }
            java.security.spec.ECGenParameterSpec eCGenParameterSpec = (java.security.spec.ECGenParameterSpec) ecParameters.get(com.android.internal.org.bouncycastle.util.Integers.valueOf(i));
            if (eCGenParameterSpec == null) {
                throw new java.security.InvalidParameterException("unknown key size.");
            }
            try {
                initialize(eCGenParameterSpec, secureRandom);
            } catch (java.security.InvalidAlgorithmParameterException e) {
                throw new java.security.InvalidParameterException("key size not configurable.");
            }
        }

        @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
        public void initialize(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidAlgorithmParameterException {
            if (secureRandom == null) {
                secureRandom = this.random;
            }
            if (algorithmParameterSpec == null) {
                com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec ecImplicitlyCa = this.configuration.getEcImplicitlyCa();
                if (ecImplicitlyCa == null) {
                    throw new java.security.InvalidAlgorithmParameterException("null parameter passed but no implicitCA set");
                }
                this.ecParams = null;
                this.param = createKeyGenParamsBC(ecImplicitlyCa, secureRandom);
            } else if (algorithmParameterSpec instanceof com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec) {
                this.ecParams = algorithmParameterSpec;
                this.param = createKeyGenParamsBC((com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec) algorithmParameterSpec, secureRandom);
            } else if (algorithmParameterSpec instanceof java.security.spec.ECParameterSpec) {
                this.ecParams = algorithmParameterSpec;
                this.param = createKeyGenParamsJCE((java.security.spec.ECParameterSpec) algorithmParameterSpec, secureRandom);
            } else if (algorithmParameterSpec instanceof java.security.spec.ECGenParameterSpec) {
                initializeNamedCurve(((java.security.spec.ECGenParameterSpec) algorithmParameterSpec).getName(), secureRandom);
            } else if (algorithmParameterSpec instanceof com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec) {
                initializeNamedCurve(((com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec) algorithmParameterSpec).getName(), secureRandom);
            } else {
                java.lang.String nameFrom = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNameFrom(algorithmParameterSpec);
                if (nameFrom != null) {
                    initializeNamedCurve(nameFrom, secureRandom);
                } else {
                    throw new java.security.InvalidAlgorithmParameterException("invalid parameterSpec: " + algorithmParameterSpec);
                }
            }
            this.engine.init(this.param);
            this.initialised = true;
        }

        @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
        public java.security.KeyPair generateKeyPair() {
            if (!this.initialised) {
                initialize(this.strength, new java.security.SecureRandom());
            }
            com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair generateKeyPair = this.engine.generateKeyPair();
            com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters eCPublicKeyParameters = (com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters) generateKeyPair.getPublic();
            com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters eCPrivateKeyParameters = (com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters) generateKeyPair.getPrivate();
            if (this.ecParams instanceof com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec) {
                com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec = (com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec) this.ecParams;
                com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey bCECPublicKey = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey(this.algorithm, eCPublicKeyParameters, eCParameterSpec, this.configuration);
                return new java.security.KeyPair(bCECPublicKey, new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey(this.algorithm, eCPrivateKeyParameters, bCECPublicKey, eCParameterSpec, this.configuration));
            }
            if (this.ecParams == null) {
                return new java.security.KeyPair(new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey(this.algorithm, eCPublicKeyParameters, this.configuration), new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey(this.algorithm, eCPrivateKeyParameters, this.configuration));
            }
            java.security.spec.ECParameterSpec eCParameterSpec2 = (java.security.spec.ECParameterSpec) this.ecParams;
            com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey bCECPublicKey2 = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey(this.algorithm, eCPublicKeyParameters, eCParameterSpec2, this.configuration);
            return new java.security.KeyPair(bCECPublicKey2, new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey(this.algorithm, eCPrivateKeyParameters, bCECPublicKey2, eCParameterSpec2, this.configuration));
        }

        protected com.android.internal.org.bouncycastle.crypto.params.ECKeyGenerationParameters createKeyGenParamsBC(com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec, java.security.SecureRandom secureRandom) {
            return new com.android.internal.org.bouncycastle.crypto.params.ECKeyGenerationParameters(new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(eCParameterSpec.getCurve(), eCParameterSpec.getG(), eCParameterSpec.getN(), eCParameterSpec.getH()), secureRandom);
        }

        protected com.android.internal.org.bouncycastle.crypto.params.ECKeyGenerationParameters createKeyGenParamsJCE(java.security.spec.ECParameterSpec eCParameterSpec, java.security.SecureRandom secureRandom) {
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters domainParametersFromName;
            if ((eCParameterSpec instanceof com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec) && (domainParametersFromName = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.ECUtils.getDomainParametersFromName(((com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec) eCParameterSpec).getName())) != null) {
                return new com.android.internal.org.bouncycastle.crypto.params.ECKeyGenerationParameters(new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(domainParametersFromName.getCurve(), domainParametersFromName.getG(), domainParametersFromName.getN(), domainParametersFromName.getH()), secureRandom);
            }
            com.android.internal.org.bouncycastle.math.ec.ECCurve convertCurve = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(eCParameterSpec.getCurve());
            return new com.android.internal.org.bouncycastle.crypto.params.ECKeyGenerationParameters(new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(convertCurve, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(convertCurve, eCParameterSpec.getGenerator()), eCParameterSpec.getOrder(), java.math.BigInteger.valueOf(eCParameterSpec.getCofactor())), secureRandom);
        }

        protected com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec createNamedCurveSpec(java.lang.String str) throws java.security.InvalidAlgorithmParameterException {
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters domainParametersFromName = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.ECUtils.getDomainParametersFromName(str);
            if (domainParametersFromName == null) {
                try {
                    domainParametersFromName = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByOID(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str));
                    if (domainParametersFromName == null && (domainParametersFromName = (com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters) this.configuration.getAdditionalECParameters().get(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str))) == null) {
                        throw new java.security.InvalidAlgorithmParameterException("unknown curve OID: " + str);
                    }
                } catch (java.lang.IllegalArgumentException e) {
                    throw new java.security.InvalidAlgorithmParameterException("unknown curve name: " + str);
                }
            }
            return new com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec(str, domainParametersFromName.getCurve(), domainParametersFromName.getG(), domainParametersFromName.getN(), domainParametersFromName.getH(), null);
        }

        protected void initializeNamedCurve(java.lang.String str, java.security.SecureRandom secureRandom) throws java.security.InvalidAlgorithmParameterException {
            com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec createNamedCurveSpec = createNamedCurveSpec(str);
            this.ecParams = createNamedCurveSpec;
            this.param = createKeyGenParamsJCE(createNamedCurveSpec, secureRandom);
        }
    }

    public static class ECDSA extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi.EC {
        public ECDSA() {
            super("ECDSA", com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDH extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi.EC {
        public ECDH() {
            super("ECDH", com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDHC extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi.EC {
        public ECDHC() {
            super("ECDHC", com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECMQV extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi.EC {
        public ECMQV() {
            super("ECMQV", com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION);
        }
    }
}
