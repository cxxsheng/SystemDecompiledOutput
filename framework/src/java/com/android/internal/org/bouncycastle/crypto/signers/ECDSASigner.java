package com.android.internal.org.bouncycastle.crypto.signers;

/* loaded from: classes4.dex */
public class ECDSASigner implements com.android.internal.org.bouncycastle.math.ec.ECConstants, com.android.internal.org.bouncycastle.crypto.DSAExt {
    private final com.android.internal.org.bouncycastle.crypto.signers.DSAKCalculator kCalculator;
    private com.android.internal.org.bouncycastle.crypto.params.ECKeyParameters key;
    private java.security.SecureRandom random;

    public ECDSASigner() {
        this.kCalculator = new com.android.internal.org.bouncycastle.crypto.signers.RandomDSAKCalculator();
    }

    public ECDSASigner(com.android.internal.org.bouncycastle.crypto.signers.DSAKCalculator dSAKCalculator) {
        this.kCalculator = dSAKCalculator;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.DSA
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        java.security.SecureRandom secureRandom;
        if (z) {
            if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) {
                com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom parametersWithRandom = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) cipherParameters;
                this.key = (com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters) parametersWithRandom.getParameters();
                secureRandom = parametersWithRandom.getRandom();
                this.random = initSecureRandom((z || this.kCalculator.isDeterministic()) ? false : true, secureRandom);
            }
            this.key = (com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters) cipherParameters;
        } else {
            this.key = (com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters) cipherParameters;
        }
        secureRandom = null;
        this.random = initSecureRandom((z || this.kCalculator.isDeterministic()) ? false : true, secureRandom);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.DSAExt
    public java.math.BigInteger getOrder() {
        return this.key.getParameters().getN();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.DSA
    public java.math.BigInteger[] generateSignature(byte[] bArr) {
        com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters parameters = this.key.getParameters();
        java.math.BigInteger n = parameters.getN();
        java.math.BigInteger calculateE = calculateE(n, bArr);
        java.math.BigInteger d = ((com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters) this.key).getD();
        if (this.kCalculator.isDeterministic()) {
            this.kCalculator.init(n, d, bArr);
        } else {
            this.kCalculator.init(n, this.random);
        }
        com.android.internal.org.bouncycastle.math.ec.ECMultiplier createBasePointMultiplier = createBasePointMultiplier();
        while (true) {
            java.math.BigInteger nextK = this.kCalculator.nextK();
            java.math.BigInteger mod = createBasePointMultiplier.multiply(parameters.getG(), nextK).normalize().getAffineXCoord().toBigInteger().mod(n);
            if (!mod.equals(ZERO)) {
                java.math.BigInteger mod2 = com.android.internal.org.bouncycastle.util.BigIntegers.modOddInverse(n, nextK).multiply(calculateE.add(d.multiply(mod))).mod(n);
                if (!mod2.equals(ZERO)) {
                    return new java.math.BigInteger[]{mod, mod2};
                }
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.DSA
    public boolean verifySignature(byte[] bArr, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        java.math.BigInteger cofactor;
        com.android.internal.org.bouncycastle.math.ec.ECFieldElement denominator;
        com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters parameters = this.key.getParameters();
        java.math.BigInteger n = parameters.getN();
        java.math.BigInteger calculateE = calculateE(n, bArr);
        if (bigInteger.compareTo(ONE) < 0 || bigInteger.compareTo(n) >= 0 || bigInteger2.compareTo(ONE) < 0 || bigInteger2.compareTo(n) >= 0) {
            return false;
        }
        java.math.BigInteger modOddInverseVar = com.android.internal.org.bouncycastle.util.BigIntegers.modOddInverseVar(n, bigInteger2);
        com.android.internal.org.bouncycastle.math.ec.ECPoint sumOfTwoMultiplies = com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.sumOfTwoMultiplies(parameters.getG(), calculateE.multiply(modOddInverseVar).mod(n), ((com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters) this.key).getQ(), bigInteger.multiply(modOddInverseVar).mod(n));
        if (sumOfTwoMultiplies.isInfinity()) {
            return false;
        }
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = sumOfTwoMultiplies.getCurve();
        if (curve != null && (cofactor = curve.getCofactor()) != null && cofactor.compareTo(EIGHT) <= 0 && (denominator = getDenominator(curve.getCoordinateSystem(), sumOfTwoMultiplies)) != null && !denominator.isZero()) {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement xCoord = sumOfTwoMultiplies.getXCoord();
            while (curve.isValidFieldElement(bigInteger)) {
                if (curve.fromBigInteger(bigInteger).multiply(denominator).equals(xCoord)) {
                    return true;
                }
                bigInteger = bigInteger.add(n);
            }
            return false;
        }
        return sumOfTwoMultiplies.normalize().getAffineXCoord().toBigInteger().mod(n).equals(bigInteger);
    }

    protected java.math.BigInteger calculateE(java.math.BigInteger bigInteger, byte[] bArr) {
        int bitLength = bigInteger.bitLength();
        int length = bArr.length * 8;
        java.math.BigInteger bigInteger2 = new java.math.BigInteger(1, bArr);
        if (bitLength < length) {
            return bigInteger2.shiftRight(length - bitLength);
        }
        return bigInteger2;
    }

    protected com.android.internal.org.bouncycastle.math.ec.ECMultiplier createBasePointMultiplier() {
        return new com.android.internal.org.bouncycastle.math.ec.FixedPointCombMultiplier();
    }

    protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement getDenominator(int i, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        switch (i) {
            case 1:
            case 6:
            case 7:
                return eCPoint.getZCoord(0);
            case 2:
            case 3:
            case 4:
                return eCPoint.getZCoord(0).square();
            case 5:
            default:
                return null;
        }
    }

    protected java.security.SecureRandom initSecureRandom(boolean z, java.security.SecureRandom secureRandom) {
        if (z) {
            return com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom(secureRandom);
        }
        return null;
    }
}
