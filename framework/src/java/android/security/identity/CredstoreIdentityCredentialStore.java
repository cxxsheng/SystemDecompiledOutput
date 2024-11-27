package android.security.identity;

/* loaded from: classes3.dex */
class CredstoreIdentityCredentialStore extends android.security.identity.IdentityCredentialStore {
    private static final java.lang.String TAG = "CredstoreIdentityCredentialStore";
    private static android.security.identity.CredstoreIdentityCredentialStore sInstanceDefault = null;
    private static android.security.identity.CredstoreIdentityCredentialStore sInstanceDirectAccess = null;
    private android.content.Context mContext;
    private int mFeatureVersion;
    private android.security.identity.ICredentialStore mStore;

    static int getFeatureVersion(android.content.Context context) {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        if (packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_IDENTITY_CREDENTIAL_HARDWARE)) {
            for (android.content.pm.FeatureInfo featureInfo : packageManager.getSystemAvailableFeatures()) {
                if (featureInfo.name.equals(android.content.pm.PackageManager.FEATURE_IDENTITY_CREDENTIAL_HARDWARE)) {
                    return featureInfo.version;
                }
            }
            return 202009;
        }
        return 202009;
    }

    private CredstoreIdentityCredentialStore(android.content.Context context, android.security.identity.ICredentialStore iCredentialStore) {
        this.mContext = null;
        this.mStore = null;
        this.mContext = context;
        this.mStore = iCredentialStore;
        this.mFeatureVersion = getFeatureVersion(this.mContext);
    }

    static android.security.identity.CredstoreIdentityCredentialStore getInstanceForType(android.content.Context context, int i) {
        android.security.identity.ICredentialStoreFactory asInterface = android.security.identity.ICredentialStoreFactory.Stub.asInterface(android.os.ServiceManager.getService("android.security.identity"));
        if (asInterface == null) {
            return null;
        }
        try {
            android.security.identity.ICredentialStore credentialStore = asInterface.getCredentialStore(i);
            if (credentialStore == null) {
                return null;
            }
            return new android.security.identity.CredstoreIdentityCredentialStore(context, credentialStore);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 1) {
                return null;
            }
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    public static android.security.identity.IdentityCredentialStore getInstance(android.content.Context context) {
        if (sInstanceDefault == null) {
            sInstanceDefault = getInstanceForType(context, 0);
        }
        return sInstanceDefault;
    }

    public static android.security.identity.IdentityCredentialStore getDirectAccessInstance(android.content.Context context) {
        if (sInstanceDirectAccess == null) {
            sInstanceDirectAccess = getInstanceForType(context, 1);
        }
        return sInstanceDirectAccess;
    }

    @Override // android.security.identity.IdentityCredentialStore
    public java.lang.String[] getSupportedDocTypes() {
        try {
            return this.mStore.getSecurityHardwareInfo().supportedDocTypes;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredentialStore
    public android.security.identity.WritableIdentityCredential createCredential(java.lang.String str, java.lang.String str2) throws android.security.identity.AlreadyPersonalizedException, android.security.identity.DocTypeNotSupportedException {
        try {
            return new android.security.identity.CredstoreWritableIdentityCredential(this.mContext, str, str2, this.mStore.createCredential(str, str2));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 2) {
                throw new android.security.identity.AlreadyPersonalizedException(e2.getMessage(), e2);
            }
            if (e2.errorCode == 8) {
                throw new android.security.identity.DocTypeNotSupportedException(e2.getMessage(), e2);
            }
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredentialStore
    public android.security.identity.IdentityCredential getCredentialByName(java.lang.String str, int i) throws android.security.identity.CipherSuiteNotSupportedException {
        try {
            return new android.security.identity.CredstoreIdentityCredential(this.mContext, str, i, this.mStore.getCredentialByName(str, i), null, this.mFeatureVersion);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                return null;
            }
            if (e2.errorCode == 4) {
                throw new android.security.identity.CipherSuiteNotSupportedException(e2.getMessage(), e2);
            }
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredentialStore
    public byte[] deleteCredentialByName(java.lang.String str) {
        android.security.identity.ICredential iCredential;
        try {
            try {
                iCredential = this.mStore.getCredentialByName(str, 1);
            } catch (android.os.ServiceSpecificException e) {
                try {
                    if (e.errorCode == 3) {
                        return null;
                    }
                    iCredential = null;
                } catch (android.os.ServiceSpecificException e2) {
                    throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
                }
            }
            return iCredential.deleteCredential();
        } catch (android.os.RemoteException e3) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e3);
        }
    }

    @Override // android.security.identity.IdentityCredentialStore
    public android.security.identity.PresentationSession createPresentationSession(int i) throws android.security.identity.CipherSuiteNotSupportedException {
        try {
            return new android.security.identity.CredstorePresentationSession(this.mContext, i, this, this.mStore.createPresentationSession(i), this.mFeatureVersion);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 4) {
                throw new android.security.identity.CipherSuiteNotSupportedException(e2.getMessage(), e2);
            }
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }
}
