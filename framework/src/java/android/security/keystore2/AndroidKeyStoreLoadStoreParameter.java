package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreLoadStoreParameter implements java.security.KeyStore.LoadStoreParameter {
    private final int mNamespace;

    public AndroidKeyStoreLoadStoreParameter(int i) {
        this.mNamespace = i;
    }

    @Override // java.security.KeyStore.LoadStoreParameter
    public java.security.KeyStore.ProtectionParameter getProtectionParameter() {
        return null;
    }

    int getNamespace() {
        return this.mNamespace;
    }
}
