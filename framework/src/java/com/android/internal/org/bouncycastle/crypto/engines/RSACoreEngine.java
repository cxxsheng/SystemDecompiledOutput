package com.android.internal.org.bouncycastle.crypto.engines;

/* loaded from: classes4.dex */
class RSACoreEngine {
    private boolean forEncryption;
    private com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters key;

    RSACoreEngine() {
    }

    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) {
            this.key = (com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters) ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) cipherParameters).getParameters();
        } else {
            this.key = (com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters) cipherParameters;
        }
        this.forEncryption = z;
    }

    public int getInputBlockSize() {
        int bitLength = this.key.getModulus().bitLength();
        if (this.forEncryption) {
            return ((bitLength + 7) / 8) - 1;
        }
        return (bitLength + 7) / 8;
    }

    public int getOutputBlockSize() {
        int bitLength = this.key.getModulus().bitLength();
        if (this.forEncryption) {
            return (bitLength + 7) / 8;
        }
        return ((bitLength + 7) / 8) - 1;
    }

    public java.math.BigInteger convertInput(byte[] bArr, int i, int i2) {
        if (i2 > getInputBlockSize() + 1) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("input too large for RSA cipher.");
        }
        if (i2 == getInputBlockSize() + 1 && !this.forEncryption) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("input too large for RSA cipher.");
        }
        if (i != 0 || i2 != bArr.length) {
            byte[] bArr2 = new byte[i2];
            java.lang.System.arraycopy(bArr, i, bArr2, 0, i2);
            bArr = bArr2;
        }
        java.math.BigInteger bigInteger = new java.math.BigInteger(1, bArr);
        if (bigInteger.compareTo(this.key.getModulus()) >= 0) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("input too large for RSA cipher.");
        }
        return bigInteger;
    }

    public byte[] convertOutput(java.math.BigInteger bigInteger) {
        byte[] bArr;
        byte[] byteArray = bigInteger.toByteArray();
        if (this.forEncryption) {
            if (byteArray[0] == 0 && byteArray.length > getOutputBlockSize()) {
                int length = byteArray.length - 1;
                byte[] bArr2 = new byte[length];
                java.lang.System.arraycopy(byteArray, 1, bArr2, 0, length);
                return bArr2;
            }
            if (byteArray.length < getOutputBlockSize()) {
                int outputBlockSize = getOutputBlockSize();
                byte[] bArr3 = new byte[outputBlockSize];
                java.lang.System.arraycopy(byteArray, 0, bArr3, outputBlockSize - byteArray.length, byteArray.length);
                return bArr3;
            }
            return byteArray;
        }
        if (byteArray[0] == 0) {
            int length2 = byteArray.length - 1;
            bArr = new byte[length2];
            java.lang.System.arraycopy(byteArray, 1, bArr, 0, length2);
        } else {
            int length3 = byteArray.length;
            bArr = new byte[length3];
            java.lang.System.arraycopy(byteArray, 0, bArr, 0, length3);
        }
        com.android.internal.org.bouncycastle.util.Arrays.fill(byteArray, (byte) 0);
        return bArr;
    }

    public java.math.BigInteger processBlock(java.math.BigInteger bigInteger) {
        if (this.key instanceof com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters) {
            com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters rSAPrivateCrtKeyParameters = (com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters) this.key;
            java.math.BigInteger p = rSAPrivateCrtKeyParameters.getP();
            java.math.BigInteger q = rSAPrivateCrtKeyParameters.getQ();
            java.math.BigInteger dp = rSAPrivateCrtKeyParameters.getDP();
            java.math.BigInteger dq = rSAPrivateCrtKeyParameters.getDQ();
            java.math.BigInteger qInv = rSAPrivateCrtKeyParameters.getQInv();
            java.math.BigInteger modPow = bigInteger.remainder(p).modPow(dp, p);
            java.math.BigInteger modPow2 = bigInteger.remainder(q).modPow(dq, q);
            return modPow.subtract(modPow2).multiply(qInv).mod(p).multiply(q).add(modPow2);
        }
        return bigInteger.modPow(this.key.getExponent(), this.key.getModulus());
    }
}
