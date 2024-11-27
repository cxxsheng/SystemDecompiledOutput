package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes4.dex */
public interface DigestCalculatorProvider {
    com.android.internal.org.bouncycastle.operator.DigestCalculator get(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException;
}
