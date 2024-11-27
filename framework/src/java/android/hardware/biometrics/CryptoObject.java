package android.hardware.biometrics;

/* loaded from: classes.dex */
public class CryptoObject {
    private final java.lang.Object mCrypto;

    public CryptoObject(java.security.Signature signature) {
        this.mCrypto = signature;
    }

    public CryptoObject(javax.crypto.Cipher cipher) {
        this.mCrypto = cipher;
    }

    public CryptoObject(javax.crypto.Mac mac) {
        this.mCrypto = mac;
    }

    @java.lang.Deprecated
    public CryptoObject(android.security.identity.IdentityCredential identityCredential) {
        this.mCrypto = identityCredential;
    }

    public CryptoObject(android.security.identity.PresentationSession presentationSession) {
        this.mCrypto = presentationSession;
    }

    public CryptoObject(javax.crypto.KeyAgreement keyAgreement) {
        this.mCrypto = keyAgreement;
    }

    public java.security.Signature getSignature() {
        if (this.mCrypto instanceof java.security.Signature) {
            return (java.security.Signature) this.mCrypto;
        }
        return null;
    }

    public javax.crypto.Cipher getCipher() {
        if (this.mCrypto instanceof javax.crypto.Cipher) {
            return (javax.crypto.Cipher) this.mCrypto;
        }
        return null;
    }

    public javax.crypto.Mac getMac() {
        if (this.mCrypto instanceof javax.crypto.Mac) {
            return (javax.crypto.Mac) this.mCrypto;
        }
        return null;
    }

    @java.lang.Deprecated
    public android.security.identity.IdentityCredential getIdentityCredential() {
        if (this.mCrypto instanceof android.security.identity.IdentityCredential) {
            return (android.security.identity.IdentityCredential) this.mCrypto;
        }
        return null;
    }

    public android.security.identity.PresentationSession getPresentationSession() {
        if (this.mCrypto instanceof android.security.identity.PresentationSession) {
            return (android.security.identity.PresentationSession) this.mCrypto;
        }
        return null;
    }

    public javax.crypto.KeyAgreement getKeyAgreement() {
        if (this.mCrypto instanceof javax.crypto.KeyAgreement) {
            return (javax.crypto.KeyAgreement) this.mCrypto;
        }
        return null;
    }

    public long getOpId() {
        if (this.mCrypto == null) {
            return 0L;
        }
        if (this.mCrypto instanceof android.security.identity.IdentityCredential) {
            return ((android.security.identity.IdentityCredential) this.mCrypto).getCredstoreOperationHandle();
        }
        if (this.mCrypto instanceof android.security.identity.PresentationSession) {
            return ((android.security.identity.PresentationSession) this.mCrypto).getCredstoreOperationHandle();
        }
        return android.security.keystore2.AndroidKeyStoreProvider.getKeyStoreOperationHandle(this.mCrypto);
    }
}
