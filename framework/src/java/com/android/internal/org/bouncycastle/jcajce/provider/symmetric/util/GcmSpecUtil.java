package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

/* loaded from: classes4.dex */
public class GcmSpecUtil {
    static final java.lang.Class gcmSpecClass = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil.loadClass(com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.class, "javax.crypto.spec.GCMParameterSpec");
    static final java.lang.reflect.Method iv;
    static final java.lang.reflect.Method tLen;

    static {
        if (gcmSpecClass != null) {
            tLen = extractMethod("getTLen");
            iv = extractMethod("getIV");
        } else {
            tLen = null;
            iv = null;
        }
    }

    private static java.lang.reflect.Method extractMethod(final java.lang.String str) {
        try {
            return (java.lang.reflect.Method) java.security.AccessController.doPrivileged(new java.security.PrivilegedExceptionAction() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.1
                @Override // java.security.PrivilegedExceptionAction
                public java.lang.Object run() throws java.lang.Exception {
                    return com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.gcmSpecClass.getDeclaredMethod(str, new java.lang.Class[0]);
                }
            });
        } catch (java.security.PrivilegedActionException e) {
            return null;
        }
    }

    public static boolean gcmSpecExists() {
        return gcmSpecClass != null;
    }

    public static boolean isGcmSpec(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) {
        return gcmSpecClass != null && gcmSpecClass.isInstance(algorithmParameterSpec);
    }

    public static boolean isGcmSpec(java.lang.Class cls) {
        return gcmSpecClass == cls;
    }

    public static java.security.spec.AlgorithmParameterSpec extractGcmSpec(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) throws java.security.spec.InvalidParameterSpecException {
        try {
            com.android.internal.org.bouncycastle.asn1.cms.GCMParameters gCMParameters = com.android.internal.org.bouncycastle.asn1.cms.GCMParameters.getInstance(aSN1Primitive);
            return (java.security.spec.AlgorithmParameterSpec) gcmSpecClass.getConstructor(java.lang.Integer.TYPE, byte[].class).newInstance(com.android.internal.org.bouncycastle.util.Integers.valueOf(gCMParameters.getIcvLen() * 8), gCMParameters.getNonce());
        } catch (java.lang.NoSuchMethodException e) {
            throw new java.security.spec.InvalidParameterSpecException("No constructor found!");
        } catch (java.lang.Exception e2) {
            throw new java.security.spec.InvalidParameterSpecException("Construction failed: " + e2.getMessage());
        }
    }

    static com.android.internal.org.bouncycastle.crypto.params.AEADParameters extractAeadParameters(final com.android.internal.org.bouncycastle.crypto.params.KeyParameter keyParameter, final java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.InvalidAlgorithmParameterException {
        try {
            return (com.android.internal.org.bouncycastle.crypto.params.AEADParameters) java.security.AccessController.doPrivileged(new java.security.PrivilegedExceptionAction() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.2
                @Override // java.security.PrivilegedExceptionAction
                public java.lang.Object run() throws java.lang.Exception {
                    return new com.android.internal.org.bouncycastle.crypto.params.AEADParameters(com.android.internal.org.bouncycastle.crypto.params.KeyParameter.this, ((java.lang.Integer) com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.tLen.invoke(algorithmParameterSpec, new java.lang.Object[0])).intValue(), (byte[]) com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.iv.invoke(algorithmParameterSpec, new java.lang.Object[0]));
                }
            });
        } catch (java.lang.Exception e) {
            throw new java.security.InvalidAlgorithmParameterException("Cannot process GCMParameterSpec.");
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.cms.GCMParameters extractGcmParameters(final java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.spec.InvalidParameterSpecException {
        try {
            return (com.android.internal.org.bouncycastle.asn1.cms.GCMParameters) java.security.AccessController.doPrivileged(new java.security.PrivilegedExceptionAction() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.3
                @Override // java.security.PrivilegedExceptionAction
                public java.lang.Object run() throws java.lang.Exception {
                    return new com.android.internal.org.bouncycastle.asn1.cms.GCMParameters((byte[]) com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.iv.invoke(algorithmParameterSpec, new java.lang.Object[0]), ((java.lang.Integer) com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.tLen.invoke(algorithmParameterSpec, new java.lang.Object[0])).intValue() / 8);
                }
            });
        } catch (java.lang.Exception e) {
            throw new java.security.spec.InvalidParameterSpecException("Cannot process GCMParameterSpec");
        }
    }
}
