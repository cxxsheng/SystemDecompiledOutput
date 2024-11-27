package android.security;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class KeyStoreParameter implements java.security.KeyStore.ProtectionParameter {
    private KeyStoreParameter(int i) {
    }

    public int getFlags() {
        return 0;
    }

    @java.lang.Deprecated
    public boolean isEncryptionRequired() {
        return false;
    }

    @java.lang.Deprecated
    public static final class Builder {
        public Builder(android.content.Context context) {
            if (context == null) {
                throw new java.lang.NullPointerException("context == null");
            }
        }

        public android.security.KeyStoreParameter.Builder setEncryptionRequired(boolean z) {
            return this;
        }

        public android.security.KeyStoreParameter build() {
            return new android.security.KeyStoreParameter(0);
        }
    }
}
