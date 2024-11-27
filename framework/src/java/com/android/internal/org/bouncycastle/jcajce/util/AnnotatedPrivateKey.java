package com.android.internal.org.bouncycastle.jcajce.util;

/* loaded from: classes4.dex */
public class AnnotatedPrivateKey implements java.security.PrivateKey {
    public static final java.lang.String LABEL = "label";
    private final java.util.Map<java.lang.String, java.lang.Object> annotations;
    private final java.security.PrivateKey key;

    AnnotatedPrivateKey(java.security.PrivateKey privateKey, java.lang.String str) {
        this.key = privateKey;
        this.annotations = java.util.Collections.singletonMap("label", str);
    }

    AnnotatedPrivateKey(java.security.PrivateKey privateKey, java.util.Map<java.lang.String, java.lang.Object> map) {
        this.key = privateKey;
        this.annotations = map;
    }

    public java.security.PrivateKey getKey() {
        return this.key;
    }

    public java.util.Map<java.lang.String, java.lang.Object> getAnnotations() {
        return this.annotations;
    }

    @Override // java.security.Key
    public java.lang.String getAlgorithm() {
        return this.key.getAlgorithm();
    }

    public java.lang.Object getAnnotation(java.lang.String str) {
        return this.annotations.get(str);
    }

    public com.android.internal.org.bouncycastle.jcajce.util.AnnotatedPrivateKey addAnnotation(java.lang.String str, java.lang.Object obj) {
        java.util.HashMap hashMap = new java.util.HashMap(this.annotations);
        hashMap.put(str, obj);
        return new com.android.internal.org.bouncycastle.jcajce.util.AnnotatedPrivateKey(this.key, (java.util.Map<java.lang.String, java.lang.Object>) java.util.Collections.unmodifiableMap(hashMap));
    }

    public com.android.internal.org.bouncycastle.jcajce.util.AnnotatedPrivateKey removeAnnotation(java.lang.String str) {
        java.util.HashMap hashMap = new java.util.HashMap(this.annotations);
        hashMap.remove(str);
        return new com.android.internal.org.bouncycastle.jcajce.util.AnnotatedPrivateKey(this.key, (java.util.Map<java.lang.String, java.lang.Object>) java.util.Collections.unmodifiableMap(hashMap));
    }

    @Override // java.security.Key
    public java.lang.String getFormat() {
        return this.key.getFormat();
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return this.key.getEncoded();
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.jcajce.util.AnnotatedPrivateKey) {
            return this.key.equals(((com.android.internal.org.bouncycastle.jcajce.util.AnnotatedPrivateKey) obj).key);
        }
        return this.key.equals(obj);
    }

    public java.lang.String toString() {
        if (this.annotations.containsKey("label")) {
            return this.annotations.get("label").toString();
        }
        return this.key.toString();
    }
}
