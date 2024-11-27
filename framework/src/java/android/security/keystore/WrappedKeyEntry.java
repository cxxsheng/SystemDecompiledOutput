package android.security.keystore;

/* loaded from: classes3.dex */
public class WrappedKeyEntry implements java.security.KeyStore.Entry {
    private final java.security.spec.AlgorithmParameterSpec mAlgorithmParameterSpec;
    private final java.lang.String mTransformation;
    private final byte[] mWrappedKeyBytes;
    private final java.lang.String mWrappingKeyAlias;

    public WrappedKeyEntry(byte[] bArr, java.lang.String str, java.lang.String str2, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) {
        this.mWrappedKeyBytes = bArr;
        this.mWrappingKeyAlias = str;
        this.mTransformation = str2;
        this.mAlgorithmParameterSpec = algorithmParameterSpec;
    }

    public byte[] getWrappedKeyBytes() {
        return this.mWrappedKeyBytes;
    }

    public java.lang.String getWrappingKeyAlias() {
        return this.mWrappingKeyAlias;
    }

    public java.lang.String getTransformation() {
        return this.mTransformation;
    }

    public java.security.spec.AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return this.mAlgorithmParameterSpec;
    }
}
