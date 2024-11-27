package com.android.internal.org.bouncycastle.jcajce.util;

/* loaded from: classes4.dex */
public class AlgorithmParametersUtils {
    private AlgorithmParametersUtils() {
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Encodable extractParameters(java.security.AlgorithmParameters algorithmParameters) throws java.io.IOException {
        try {
            return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(algorithmParameters.getEncoded("ASN.1"));
        } catch (java.lang.Exception e) {
            return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(algorithmParameters.getEncoded());
        }
    }

    public static void loadParameters(java.security.AlgorithmParameters algorithmParameters, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.io.IOException {
        try {
            algorithmParameters.init(aSN1Encodable.toASN1Primitive().getEncoded(), "ASN.1");
        } catch (java.lang.Exception e) {
            algorithmParameters.init(aSN1Encodable.toASN1Primitive().getEncoded());
        }
    }
}
