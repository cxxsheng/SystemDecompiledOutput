package com.android.internal.org.bouncycastle.jcajce.spec;

/* loaded from: classes4.dex */
public class UserKeyingMaterialSpec implements java.security.spec.AlgorithmParameterSpec {
    private final byte[] userKeyingMaterial;

    public UserKeyingMaterialSpec(byte[] bArr) {
        this.userKeyingMaterial = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
    }

    public byte[] getUserKeyingMaterial() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.userKeyingMaterial);
    }
}
