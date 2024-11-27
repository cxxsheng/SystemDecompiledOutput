package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa;

/* loaded from: classes4.dex */
public abstract class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
    protected abstract java.security.spec.AlgorithmParameterSpec localEngineGetParameterSpec(java.lang.Class cls) throws java.security.spec.InvalidParameterSpecException;

    protected boolean isASN1FormatString(java.lang.String str) {
        return str == null || str.equals("ASN.1");
    }

    @Override // java.security.AlgorithmParametersSpi
    protected java.security.spec.AlgorithmParameterSpec engineGetParameterSpec(java.lang.Class cls) throws java.security.spec.InvalidParameterSpecException {
        if (cls == null) {
            throw new java.lang.NullPointerException("argument to getParameterSpec must not be null");
        }
        return localEngineGetParameterSpec(cls);
    }

    public static class OAEP extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi {
        javax.crypto.spec.OAEPParameterSpec currentSpec;

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded() {
            try {
                return new com.android.internal.org.bouncycastle.asn1.pkcs.RSAESOAEPparams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.jcajce.provider.util.DigestFactory.getOID(this.currentSpec.getDigestAlgorithm()), com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_mgf1, new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.jcajce.provider.util.DigestFactory.getOID(((java.security.spec.MGF1ParameterSpec) this.currentSpec.getMGFParameters()).getDigestAlgorithm()), com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE)), new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_pSpecified, new com.android.internal.org.bouncycastle.asn1.DEROctetString(((javax.crypto.spec.PSource.PSpecified) this.currentSpec.getPSource()).getValue()))).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException("Error encoding OAEPParameters");
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded(java.lang.String str) {
            if (isASN1FormatString(str) || str.equalsIgnoreCase("X.509")) {
                return engineGetEncoded();
            }
            return null;
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi
        protected java.security.spec.AlgorithmParameterSpec localEngineGetParameterSpec(java.lang.Class cls) throws java.security.spec.InvalidParameterSpecException {
            if (cls == javax.crypto.spec.OAEPParameterSpec.class || cls == java.security.spec.AlgorithmParameterSpec.class) {
                return this.currentSpec;
            }
            throw new java.security.spec.InvalidParameterSpecException("unknown parameter spec passed to OAEP parameters object.");
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.spec.InvalidParameterSpecException {
            if (!(algorithmParameterSpec instanceof javax.crypto.spec.OAEPParameterSpec)) {
                throw new java.security.spec.InvalidParameterSpecException("OAEPParameterSpec required to initialise an OAEP algorithm parameters object");
            }
            this.currentSpec = (javax.crypto.spec.OAEPParameterSpec) algorithmParameterSpec;
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr) throws java.io.IOException {
            try {
                com.android.internal.org.bouncycastle.asn1.pkcs.RSAESOAEPparams rSAESOAEPparams = com.android.internal.org.bouncycastle.asn1.pkcs.RSAESOAEPparams.getInstance(bArr);
                if (!rSAESOAEPparams.getMaskGenAlgorithm().getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_mgf1)) {
                    throw new java.io.IOException("unknown mask generation function: " + rSAESOAEPparams.getMaskGenAlgorithm().getAlgorithm());
                }
                this.currentSpec = new javax.crypto.spec.OAEPParameterSpec(com.android.internal.org.bouncycastle.jcajce.util.MessageDigestUtils.getDigestName(rSAESOAEPparams.getHashAlgorithm().getAlgorithm()), javax.crypto.spec.OAEPParameterSpec.DEFAULT.getMGFAlgorithm(), new java.security.spec.MGF1ParameterSpec(com.android.internal.org.bouncycastle.jcajce.util.MessageDigestUtils.getDigestName(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(rSAESOAEPparams.getMaskGenAlgorithm().getParameters()).getAlgorithm())), new javax.crypto.spec.PSource.PSpecified(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(rSAESOAEPparams.getPSourceAlgorithm().getParameters()).getOctets()));
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                throw new java.io.IOException("Not a valid OAEP Parameter encoding.");
            } catch (java.lang.ClassCastException e2) {
                throw new java.io.IOException("Not a valid OAEP Parameter encoding.");
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr, java.lang.String str) throws java.io.IOException {
            if (str.equalsIgnoreCase("X.509") || str.equalsIgnoreCase("ASN.1")) {
                engineInit(bArr);
                return;
            }
            throw new java.io.IOException("Unknown parameter format " + str);
        }

        @Override // java.security.AlgorithmParametersSpi
        protected java.lang.String engineToString() {
            return "OAEP Parameters";
        }
    }

    public static class PSS extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi {
        java.security.spec.PSSParameterSpec currentSpec;

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded() throws java.io.IOException {
            java.security.spec.PSSParameterSpec pSSParameterSpec = this.currentSpec;
            return new com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.jcajce.provider.util.DigestFactory.getOID(pSSParameterSpec.getDigestAlgorithm()), com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_mgf1, new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.jcajce.provider.util.DigestFactory.getOID(((java.security.spec.MGF1ParameterSpec) pSSParameterSpec.getMGFParameters()).getDigestAlgorithm()), com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE)), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(pSSParameterSpec.getSaltLength()), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(pSSParameterSpec.getTrailerField())).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        }

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded(java.lang.String str) throws java.io.IOException {
            if (str.equalsIgnoreCase("X.509") || str.equalsIgnoreCase("ASN.1")) {
                return engineGetEncoded();
            }
            return null;
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi
        protected java.security.spec.AlgorithmParameterSpec localEngineGetParameterSpec(java.lang.Class cls) throws java.security.spec.InvalidParameterSpecException {
            if (cls == java.security.spec.PSSParameterSpec.class || cls == java.security.spec.AlgorithmParameterSpec.class) {
                return this.currentSpec;
            }
            throw new java.security.spec.InvalidParameterSpecException("unknown parameter spec passed to PSS parameters object.");
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.spec.InvalidParameterSpecException {
            if (!(algorithmParameterSpec instanceof java.security.spec.PSSParameterSpec)) {
                throw new java.security.spec.InvalidParameterSpecException("PSSParameterSpec required to initialise an PSS algorithm parameters object");
            }
            this.currentSpec = (java.security.spec.PSSParameterSpec) algorithmParameterSpec;
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr) throws java.io.IOException {
            try {
                com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams rSASSAPSSparams = com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams.getInstance(bArr);
                if (!rSASSAPSSparams.getMaskGenAlgorithm().getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_mgf1)) {
                    throw new java.io.IOException("unknown mask generation function: " + rSASSAPSSparams.getMaskGenAlgorithm().getAlgorithm());
                }
                this.currentSpec = new java.security.spec.PSSParameterSpec(com.android.internal.org.bouncycastle.jcajce.util.MessageDigestUtils.getDigestName(rSASSAPSSparams.getHashAlgorithm().getAlgorithm()), java.security.spec.PSSParameterSpec.DEFAULT.getMGFAlgorithm(), new java.security.spec.MGF1ParameterSpec(com.android.internal.org.bouncycastle.jcajce.util.MessageDigestUtils.getDigestName(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(rSASSAPSSparams.getMaskGenAlgorithm().getParameters()).getAlgorithm())), rSASSAPSSparams.getSaltLength().intValue(), rSASSAPSSparams.getTrailerField().intValue());
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                throw new java.io.IOException("Not a valid PSS Parameter encoding.");
            } catch (java.lang.ClassCastException e2) {
                throw new java.io.IOException("Not a valid PSS Parameter encoding.");
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr, java.lang.String str) throws java.io.IOException {
            if (isASN1FormatString(str) || str.equalsIgnoreCase("X.509")) {
                engineInit(bArr);
                return;
            }
            throw new java.io.IOException("Unknown parameter format " + str);
        }

        @Override // java.security.AlgorithmParametersSpi
        protected java.lang.String engineToString() {
            return "PSS Parameters";
        }
    }
}
