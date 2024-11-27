package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreKey implements java.security.Key {
    private final java.lang.String mAlgorithm;
    private final android.system.keystore2.Authorization[] mAuthorizations;
    private final android.system.keystore2.KeyDescriptor mDescriptor;
    private final long mKeyId;
    private final android.security.KeyStoreSecurityLevel mSecurityLevel;

    public AndroidKeyStoreKey(android.system.keystore2.KeyDescriptor keyDescriptor, long j, android.system.keystore2.Authorization[] authorizationArr, java.lang.String str, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel) {
        this.mDescriptor = keyDescriptor;
        this.mKeyId = j;
        this.mAuthorizations = authorizationArr;
        this.mAlgorithm = str;
        this.mSecurityLevel = keyStoreSecurityLevel;
    }

    android.system.keystore2.KeyDescriptor getUserKeyDescriptor() {
        return this.mDescriptor;
    }

    android.system.keystore2.KeyDescriptor getKeyIdDescriptor() {
        android.system.keystore2.KeyDescriptor keyDescriptor = new android.system.keystore2.KeyDescriptor();
        keyDescriptor.nspace = this.mKeyId;
        keyDescriptor.domain = 4;
        keyDescriptor.alias = null;
        keyDescriptor.blob = null;
        return keyDescriptor;
    }

    android.system.keystore2.Authorization[] getAuthorizations() {
        return this.mAuthorizations;
    }

    android.security.KeyStoreSecurityLevel getSecurityLevel() {
        return this.mSecurityLevel;
    }

    @Override // java.security.Key
    public java.lang.String getAlgorithm() {
        return this.mAlgorithm;
    }

    @Override // java.security.Key
    public java.lang.String getFormat() {
        return null;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return null;
    }

    public int hashCode() {
        return ((((getClass().hashCode() + 31) * 31) + ((int) (this.mKeyId >>> 32))) * 31) + ((int) (this.mKeyId & (-1)));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mKeyId == ((android.security.keystore2.AndroidKeyStoreKey) obj).mKeyId) {
            return true;
        }
        return false;
    }
}
