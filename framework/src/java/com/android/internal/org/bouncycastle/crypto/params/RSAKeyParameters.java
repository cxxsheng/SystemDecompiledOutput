package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class RSAKeyParameters extends com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter {
    private java.math.BigInteger exponent;
    private java.math.BigInteger modulus;
    private static final java.math.BigInteger SMALL_PRIMES_PRODUCT = new java.math.BigInteger("8138e8a0fcf3a4e84a771d40fd305d7f4aa59306d7251de54d98af8fe95729a1f73d893fa424cd2edc8636a6c3285e022b0e3866a565ae8108eed8591cd4fe8d2ce86165a978d719ebf647f362d33fca29cd179fb42401cbaf3df0c614056f9c8f3cfd51e474afb6bc6974f78db8aba8e9e517fded658591ab7502bd41849462f", 16);
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);

    public RSAKeyParameters(boolean z, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        super(z);
        if (!z && (bigInteger2.intValue() & 1) == 0) {
            throw new java.lang.IllegalArgumentException("RSA publicExponent is even");
        }
        this.modulus = validate(bigInteger);
        this.exponent = bigInteger2;
    }

    private java.math.BigInteger validate(java.math.BigInteger bigInteger) {
        if ((bigInteger.intValue() & 1) == 0) {
            throw new java.lang.IllegalArgumentException("RSA modulus is even");
        }
        if (com.android.internal.org.bouncycastle.util.Properties.isOverrideSet("com.android.internal.org.bouncycastle.rsa.allow_unsafe_mod")) {
            return bigInteger;
        }
        if (!bigInteger.gcd(SMALL_PRIMES_PRODUCT).equals(ONE)) {
            throw new java.lang.IllegalArgumentException("RSA modulus has a small prime factor");
        }
        return bigInteger;
    }

    public java.math.BigInteger getModulus() {
        return this.modulus;
    }

    public java.math.BigInteger getExponent() {
        return this.exponent;
    }
}
