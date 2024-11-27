package com.android.internal.org.bouncycastle.jcajce.util;

/* loaded from: classes4.dex */
public class PrivateKeyAnnotator {
    public static com.android.internal.org.bouncycastle.jcajce.util.AnnotatedPrivateKey annotate(java.security.PrivateKey privateKey, java.lang.String str) {
        return new com.android.internal.org.bouncycastle.jcajce.util.AnnotatedPrivateKey(privateKey, str);
    }

    public static com.android.internal.org.bouncycastle.jcajce.util.AnnotatedPrivateKey annotate(java.security.PrivateKey privateKey, java.util.Map<java.lang.String, java.lang.Object> map) {
        return new com.android.internal.org.bouncycastle.jcajce.util.AnnotatedPrivateKey(privateKey, (java.util.Map<java.lang.String, java.lang.Object>) java.util.Collections.unmodifiableMap(new java.util.HashMap(map)));
    }
}
