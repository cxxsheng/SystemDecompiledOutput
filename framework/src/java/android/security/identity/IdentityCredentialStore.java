package android.security.identity;

/* loaded from: classes3.dex */
public abstract class IdentityCredentialStore {
    public static final int CIPHERSUITE_ECDHE_HKDF_ECDSA_WITH_AES_256_GCM_SHA256 = 1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Ciphersuite {
    }

    public abstract android.security.identity.WritableIdentityCredential createCredential(java.lang.String str, java.lang.String str2) throws android.security.identity.AlreadyPersonalizedException, android.security.identity.DocTypeNotSupportedException;

    @java.lang.Deprecated
    public abstract byte[] deleteCredentialByName(java.lang.String str);

    public abstract android.security.identity.IdentityCredential getCredentialByName(java.lang.String str, int i) throws android.security.identity.CipherSuiteNotSupportedException;

    public abstract java.lang.String[] getSupportedDocTypes();

    IdentityCredentialStore() {
    }

    public static android.security.identity.IdentityCredentialStore getInstance(android.content.Context context) {
        return android.security.identity.CredstoreIdentityCredentialStore.getInstance(context);
    }

    public static android.security.identity.IdentityCredentialStore getDirectAccessInstance(android.content.Context context) {
        return android.security.identity.CredstoreIdentityCredentialStore.getDirectAccessInstance(context);
    }

    public android.security.identity.PresentationSession createPresentationSession(int i) throws android.security.identity.CipherSuiteNotSupportedException {
        throw new java.lang.UnsupportedOperationException();
    }
}
