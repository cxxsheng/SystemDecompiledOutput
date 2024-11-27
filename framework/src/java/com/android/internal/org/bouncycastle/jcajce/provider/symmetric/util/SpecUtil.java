package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

/* loaded from: classes4.dex */
class SpecUtil {
    SpecUtil() {
    }

    static java.security.spec.AlgorithmParameterSpec extractSpec(java.security.AlgorithmParameters algorithmParameters, java.lang.Class[] clsArr) {
        try {
            return algorithmParameters.getParameterSpec(java.security.spec.AlgorithmParameterSpec.class);
        } catch (java.lang.Exception e) {
            for (int i = 0; i != clsArr.length; i++) {
                if (clsArr[i] != null) {
                    try {
                        return algorithmParameters.getParameterSpec(clsArr[i]);
                    } catch (java.lang.Exception e2) {
                    }
                }
            }
            return null;
        }
    }
}
