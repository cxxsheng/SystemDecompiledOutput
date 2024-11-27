package com.android.internal.org.bouncycastle.jcajce.provider.symmetric;

/* loaded from: classes4.dex */
class GcmSpecUtil {
    static final java.lang.Class gcmSpecClass = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil.loadClass(com.android.internal.org.bouncycastle.jcajce.provider.symmetric.GcmSpecUtil.class, "javax.crypto.spec.GCMParameterSpec");

    GcmSpecUtil() {
    }

    static boolean gcmSpecExists() {
        return gcmSpecClass != null;
    }

    static boolean isGcmSpec(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) {
        return gcmSpecClass != null && gcmSpecClass.isInstance(algorithmParameterSpec);
    }

    static boolean isGcmSpec(java.lang.Class cls) {
        return gcmSpecClass == cls;
    }

    static java.security.spec.AlgorithmParameterSpec extractGcmSpec(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) throws java.security.spec.InvalidParameterSpecException {
        try {
            com.android.internal.org.bouncycastle.asn1.cms.GCMParameters gCMParameters = com.android.internal.org.bouncycastle.asn1.cms.GCMParameters.getInstance(aSN1Primitive);
            return (java.security.spec.AlgorithmParameterSpec) gcmSpecClass.getConstructor(java.lang.Integer.TYPE, byte[].class).newInstance(com.android.internal.org.bouncycastle.util.Integers.valueOf(gCMParameters.getIcvLen() * 8), gCMParameters.getNonce());
        } catch (java.lang.NoSuchMethodException e) {
            throw new java.security.spec.InvalidParameterSpecException("No constructor found!");
        } catch (java.lang.Exception e2) {
            throw new java.security.spec.InvalidParameterSpecException("Construction failed: " + e2.getMessage());
        }
    }

    static com.android.internal.org.bouncycastle.asn1.cms.GCMParameters extractGcmParameters(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.spec.InvalidParameterSpecException {
        try {
            return new com.android.internal.org.bouncycastle.asn1.cms.GCMParameters((byte[]) gcmSpecClass.getDeclaredMethod("getIV", new java.lang.Class[0]).invoke(algorithmParameterSpec, new java.lang.Object[0]), ((java.lang.Integer) gcmSpecClass.getDeclaredMethod("getTLen", new java.lang.Class[0]).invoke(algorithmParameterSpec, new java.lang.Object[0])).intValue() / 8);
        } catch (java.lang.Exception e) {
            throw new java.security.spec.InvalidParameterSpecException("Cannot process GCMParameterSpec");
        }
    }
}
