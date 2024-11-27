package com.android.server.locksettings.recoverablekeystore.storage;

/* loaded from: classes2.dex */
public class ApplicationKeyStorage {
    private static final java.lang.String APPLICATION_KEY_ALIAS_PREFIX = "com.android.server.locksettings.recoverablekeystore/application/";
    private static final java.lang.String APPLICATION_KEY_GRANT_PREFIX = "recoverable_key:";
    private static final java.lang.String TAG = "RecoverableAppKeyStore";
    private final com.android.server.locksettings.recoverablekeystore.KeyStoreProxy mKeyStore;

    public static com.android.server.locksettings.recoverablekeystore.storage.ApplicationKeyStorage getInstance() throws java.security.KeyStoreException {
        return new com.android.server.locksettings.recoverablekeystore.storage.ApplicationKeyStorage(new com.android.server.locksettings.recoverablekeystore.KeyStoreProxyImpl(com.android.server.locksettings.recoverablekeystore.KeyStoreProxyImpl.getAndLoadAndroidKeyStore()));
    }

    @com.android.internal.annotations.VisibleForTesting
    ApplicationKeyStorage(com.android.server.locksettings.recoverablekeystore.KeyStoreProxy keyStoreProxy) {
        this.mKeyStore = keyStoreProxy;
    }

    @android.annotation.Nullable
    public java.lang.String getGrantAlias(int i, int i2, java.lang.String str) {
        android.util.Log.i(TAG, java.lang.String.format(java.util.Locale.US, "Get %d/%d/%s", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str));
        return makeKeystoreEngineGrantString(i2, getInternalAlias(i, i2, str));
    }

    public void setSymmetricKeyEntry(int i, int i2, java.lang.String str, byte[] bArr) throws java.security.KeyStoreException {
        android.util.Log.i(TAG, java.lang.String.format(java.util.Locale.US, "Set %d/%d/%s: %d bytes of key material", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, java.lang.Integer.valueOf(bArr.length)));
        try {
            this.mKeyStore.setEntry(getInternalAlias(i, i2, str), new java.security.KeyStore.SecretKeyEntry(new javax.crypto.spec.SecretKeySpec(bArr, "AES")), new android.security.keystore.KeyProtection.Builder(3).setBlockModes("GCM").setEncryptionPaddings("NoPadding").build());
        } catch (java.security.KeyStoreException e) {
            throw new android.os.ServiceSpecificException(22, e.getMessage());
        }
    }

    public void deleteEntry(int i, int i2, java.lang.String str) {
        android.util.Log.i(TAG, java.lang.String.format(java.util.Locale.US, "Del %d/%d/%s", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str));
        try {
            this.mKeyStore.deleteEntry(getInternalAlias(i, i2, str));
        } catch (java.security.KeyStoreException e) {
            throw new android.os.ServiceSpecificException(22, e.getMessage());
        }
    }

    private java.lang.String getInternalAlias(int i, int i2, java.lang.String str) {
        return APPLICATION_KEY_ALIAS_PREFIX + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i2 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str;
    }

    private java.lang.String makeKeystoreEngineGrantString(int i, java.lang.String str) {
        if (str == null) {
            return null;
        }
        android.system.keystore2.KeyDescriptor keyDescriptor = new android.system.keystore2.KeyDescriptor();
        keyDescriptor.domain = 0;
        keyDescriptor.nspace = -1L;
        keyDescriptor.alias = str;
        keyDescriptor.blob = null;
        try {
            return java.lang.String.format("%s%016X", APPLICATION_KEY_GRANT_PREFIX, java.lang.Long.valueOf(android.security.KeyStore2.getInstance().grant(keyDescriptor, i, com.android.internal.util.FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__RIGHT_UP).nspace));
        } catch (android.security.KeyStoreException e) {
            if (e.getNumericErrorCode() == 6) {
                android.util.Log.w(TAG, "Failed to get grant for KeyStore key - key not found");
                throw new android.os.ServiceSpecificException(30, e.getMessage());
            }
            android.util.Log.e(TAG, "Failed to get grant for KeyStore key.", e);
            throw new android.os.ServiceSpecificException(22, e.getMessage());
        }
    }
}
