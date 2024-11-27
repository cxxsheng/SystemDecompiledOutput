package android.security;

/* loaded from: classes3.dex */
public final class KeyChain {
    public static final java.lang.String ACCOUNT_TYPE = "com.android.keychain";
    private static final java.lang.String ACTION_CHOOSER = "com.android.keychain.CHOOSER";
    private static final java.lang.String ACTION_INSTALL = "android.credentials.INSTALL";
    public static final java.lang.String ACTION_KEYCHAIN_CHANGED = "android.security.action.KEYCHAIN_CHANGED";
    public static final java.lang.String ACTION_KEY_ACCESS_CHANGED = "android.security.action.KEY_ACCESS_CHANGED";
    public static final java.lang.String ACTION_STORAGE_CHANGED = "android.security.STORAGE_CHANGED";
    public static final java.lang.String ACTION_TRUST_STORE_CHANGED = "android.security.action.TRUST_STORE_CHANGED";
    private static final java.lang.String CERT_INSTALLER_PACKAGE = "com.android.certinstaller";
    public static final java.lang.String EXTRA_ALIAS = "alias";
    public static final java.lang.String EXTRA_AUTHENTICATION_POLICY = "android.security.extra.AUTHENTICATION_POLICY";
    public static final java.lang.String EXTRA_CERTIFICATE = "CERT";
    public static final java.lang.String EXTRA_ISSUERS = "issuers";
    public static final java.lang.String EXTRA_KEY_ACCESSIBLE = "android.security.extra.KEY_ACCESSIBLE";
    public static final java.lang.String EXTRA_KEY_ALIAS = "android.security.extra.KEY_ALIAS";
    public static final java.lang.String EXTRA_KEY_TYPES = "key_types";
    public static final java.lang.String EXTRA_NAME = "name";
    public static final java.lang.String EXTRA_PKCS12 = "PKCS12";
    public static final java.lang.String EXTRA_RESPONSE = "response";
    public static final java.lang.String EXTRA_SENDER = "sender";
    public static final java.lang.String EXTRA_URI = "uri";
    public static final java.lang.String GRANT_ALIAS_PREFIX = "ks2_keychain_grant_id:";
    private static final java.lang.String KEYCHAIN_PACKAGE = "com.android.keychain";
    public static final java.lang.String KEY_ALIAS_SELECTION_DENIED = "android:alias-selection-denied";
    public static final int KEY_ATTESTATION_CANNOT_ATTEST_IDS = 3;
    public static final int KEY_ATTESTATION_CANNOT_COLLECT_DATA = 2;
    public static final int KEY_ATTESTATION_FAILURE = 4;
    public static final int KEY_ATTESTATION_MISSING_CHALLENGE = 1;
    public static final int KEY_ATTESTATION_SUCCESS = 0;
    public static final int KEY_GEN_FAILURE = 7;
    public static final int KEY_GEN_INVALID_ALGORITHM_PARAMETERS = 4;
    public static final int KEY_GEN_MISSING_ALIAS = 1;
    public static final int KEY_GEN_NO_KEYSTORE_PROVIDER = 5;
    public static final int KEY_GEN_NO_SUCH_ALGORITHM = 3;
    public static final int KEY_GEN_STRONGBOX_UNAVAILABLE = 6;
    public static final int KEY_GEN_SUCCESS = 0;
    public static final int KEY_GEN_SUPERFLUOUS_ATTESTATION_CHALLENGE = 2;
    public static final java.lang.String LOG = "KeyChain";
    private static final java.lang.String SETTINGS_PACKAGE = "com.android.settings";

    public static android.content.Intent createInstallIntent() {
        android.content.Intent intent = new android.content.Intent("android.credentials.INSTALL");
        intent.setClassName(CERT_INSTALLER_PACKAGE, "com.android.certinstaller.CertInstallerMain");
        return intent;
    }

    public static android.content.Intent createManageCredentialsIntent(android.security.AppUriAuthenticationPolicy appUriAuthenticationPolicy) {
        android.content.Intent intent = new android.content.Intent(android.security.Credentials.ACTION_MANAGE_CREDENTIALS);
        intent.setComponent(android.content.ComponentName.createRelative(SETTINGS_PACKAGE, ".security.RequestManageCredentials"));
        intent.putExtra(EXTRA_AUTHENTICATION_POLICY, appUriAuthenticationPolicy);
        return intent;
    }

    public static void choosePrivateKeyAlias(android.app.Activity activity, android.security.KeyChainAliasCallback keyChainAliasCallback, java.lang.String[] strArr, java.security.Principal[] principalArr, java.lang.String str, int i, java.lang.String str2) {
        android.net.Uri uri;
        if (str == null) {
            uri = null;
        } else {
            uri = new android.net.Uri.Builder().authority(str + (i != -1 ? ":" + i : "")).build();
        }
        choosePrivateKeyAlias(activity, keyChainAliasCallback, strArr, principalArr, uri, str2);
    }

    public static void choosePrivateKeyAlias(android.app.Activity activity, android.security.KeyChainAliasCallback keyChainAliasCallback, java.lang.String[] strArr, java.security.Principal[] principalArr, android.net.Uri uri, java.lang.String str) {
        if (activity == null) {
            throw new java.lang.NullPointerException("activity == null");
        }
        if (keyChainAliasCallback == null) {
            throw new java.lang.NullPointerException("response == null");
        }
        android.content.Intent intent = new android.content.Intent(ACTION_CHOOSER);
        intent.setPackage("com.android.keychain");
        intent.putExtra("response", new android.security.KeyChain.AliasResponse(keyChainAliasCallback));
        intent.putExtra("uri", uri);
        intent.putExtra("alias", str);
        intent.putExtra(EXTRA_KEY_TYPES, strArr);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (principalArr != null) {
            for (java.security.Principal principal : principalArr) {
                if (!(principal instanceof javax.security.auth.x500.X500Principal)) {
                    throw new java.lang.IllegalArgumentException(java.lang.String.format("Issuer %s is of type %s, not X500Principal", principal.toString(), principal.getClass()));
                }
                arrayList.add(((javax.security.auth.x500.X500Principal) principal).getEncoded());
            }
        }
        intent.putExtra(EXTRA_ISSUERS, arrayList);
        intent.putExtra(EXTRA_SENDER, android.app.PendingIntent.getActivity(activity, 0, new android.content.Intent(), 67108864));
        activity.startActivity(intent);
    }

    public static boolean isCredentialManagementApp(android.content.Context context) {
        android.os.RemoteException e;
        boolean z;
        try {
            try {
                android.security.KeyChain.KeyChainConnection bind = bind(context);
                try {
                    z = bind.getService().isCredentialManagementApp(context.getPackageName());
                    if (bind != null) {
                        try {
                            bind.close();
                        } catch (android.os.RemoteException e2) {
                            e = e2;
                            e.rethrowAsRuntimeException();
                            return z;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    if (bind != null) {
                        try {
                            bind.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.lang.InterruptedException e3) {
                throw new java.lang.RuntimeException("Interrupted while checking whether the caller is the credential management app.", e3);
            } catch (java.lang.SecurityException e4) {
                return false;
            }
        } catch (android.os.RemoteException e5) {
            e = e5;
            z = false;
        }
        return z;
    }

    public static android.security.AppUriAuthenticationPolicy getCredentialManagementAppPolicy(android.content.Context context) throws java.lang.SecurityException {
        android.security.AppUriAuthenticationPolicy appUriAuthenticationPolicy = null;
        try {
            android.security.KeyChain.KeyChainConnection bind = bind(context);
            try {
                appUriAuthenticationPolicy = bind.getService().getCredentialManagementAppPolicy();
                if (bind != null) {
                    bind.close();
                }
            } finally {
            }
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        } catch (java.lang.InterruptedException e2) {
            throw new java.lang.RuntimeException("Interrupted while getting credential management app policy.", e2);
        }
        return appUriAuthenticationPolicy;
    }

    public static boolean setCredentialManagementApp(android.content.Context context, java.lang.String str, android.security.AppUriAuthenticationPolicy appUriAuthenticationPolicy) {
        try {
            android.security.KeyChain.KeyChainConnection bind = bind(context);
            try {
                bind.getService().setCredentialManagementApp(str, appUriAuthenticationPolicy);
                if (bind != null) {
                    bind.close();
                    return true;
                }
                return true;
            } catch (java.lang.Throwable th) {
                if (bind != null) {
                    try {
                        bind.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (android.os.RemoteException | java.lang.InterruptedException e) {
            android.util.Log.w(LOG, "Set credential management app failed", e);
            java.lang.Thread.currentThread().interrupt();
            return false;
        }
    }

    public static boolean removeCredentialManagementApp(android.content.Context context) {
        try {
            android.security.KeyChain.KeyChainConnection bind = bind(context);
            try {
                bind.getService().removeCredentialManagementApp();
                if (bind != null) {
                    bind.close();
                    return true;
                }
                return true;
            } catch (java.lang.Throwable th) {
                if (bind != null) {
                    try {
                        bind.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (android.os.RemoteException | java.lang.InterruptedException e) {
            android.util.Log.w(LOG, "Remove credential management app failed", e);
            java.lang.Thread.currentThread().interrupt();
            return false;
        }
    }

    private static class AliasResponse extends android.security.IKeyChainAliasCallback.Stub {
        private final android.security.KeyChainAliasCallback keyChainAliasResponse;

        private AliasResponse(android.security.KeyChainAliasCallback keyChainAliasCallback) {
            this.keyChainAliasResponse = keyChainAliasCallback;
        }

        @Override // android.security.IKeyChainAliasCallback
        public void alias(java.lang.String str) {
            this.keyChainAliasResponse.alias(str);
        }
    }

    public static java.security.PrivateKey getPrivateKey(android.content.Context context, java.lang.String str) throws android.security.KeyChainException, java.lang.InterruptedException {
        java.security.KeyPair keyPair = getKeyPair(context, str);
        if (keyPair != null) {
            return keyPair.getPrivate();
        }
        return null;
    }

    private static android.system.keystore2.KeyDescriptor getGrantDescriptor(java.lang.String str) {
        android.system.keystore2.KeyDescriptor keyDescriptor = new android.system.keystore2.KeyDescriptor();
        keyDescriptor.domain = 1;
        keyDescriptor.blob = null;
        keyDescriptor.alias = null;
        try {
            keyDescriptor.nspace = java.lang.Long.parseUnsignedLong(str.substring(GRANT_ALIAS_PREFIX.length()), 16);
            return keyDescriptor;
        } catch (java.lang.NumberFormatException e) {
            return null;
        }
    }

    public static java.lang.String getGrantString(android.system.keystore2.KeyDescriptor keyDescriptor) {
        return java.lang.String.format("ks2_keychain_grant_id:%016X", java.lang.Long.valueOf(keyDescriptor.nspace));
    }

    public static java.security.KeyPair getKeyPair(android.content.Context context, java.lang.String str) throws android.security.KeyChainException, java.lang.InterruptedException {
        if (str == null) {
            throw new java.lang.NullPointerException("alias == null");
        }
        if (context == null) {
            throw new java.lang.NullPointerException("context == null");
        }
        try {
            android.security.KeyChain.KeyChainConnection bind = bind(context.getApplicationContext());
            try {
                java.lang.String requestPrivateKey = bind.getService().requestPrivateKey(str);
                if (bind != null) {
                    bind.close();
                }
                if (requestPrivateKey == null) {
                    return null;
                }
                try {
                    return android.security.keystore2.AndroidKeyStoreProvider.loadAndroidKeyStoreKeyPairFromKeystore(android.security.KeyStore2.getInstance(), getGrantDescriptor(requestPrivateKey));
                } catch (android.security.keystore.KeyPermanentlyInvalidatedException | java.security.UnrecoverableKeyException e) {
                    throw new android.security.KeyChainException(e);
                }
            } finally {
            }
        } catch (android.os.RemoteException e2) {
            throw new android.security.KeyChainException(e2);
        } catch (java.lang.RuntimeException e3) {
            throw new android.security.KeyChainException(e3);
        }
    }

    public static java.security.cert.X509Certificate[] getCertificateChain(android.content.Context context, java.lang.String str) throws android.security.KeyChainException, java.lang.InterruptedException {
        if (str == null) {
            throw new java.lang.NullPointerException("alias == null");
        }
        try {
            android.security.KeyChain.KeyChainConnection bind = bind(context.getApplicationContext());
            try {
                android.security.IKeyChainService service = bind.getService();
                byte[] certificate = service.getCertificate(str);
                if (certificate != null) {
                    byte[] caCertificates = service.getCaCertificates(str);
                    if (bind != null) {
                        bind.close();
                    }
                    try {
                        java.security.cert.X509Certificate certificate2 = toCertificate(certificate);
                        if (caCertificates != null && caCertificates.length != 0) {
                            java.util.Collection<java.security.cert.X509Certificate> certificates = toCertificates(caCertificates);
                            java.util.ArrayList arrayList = new java.util.ArrayList(certificates.size() + 1);
                            arrayList.add(certificate2);
                            arrayList.addAll(certificates);
                            return (java.security.cert.X509Certificate[]) arrayList.toArray(new java.security.cert.X509Certificate[arrayList.size()]);
                        }
                        java.util.List certificateChain = new com.android.org.conscrypt.TrustedCertificateStore().getCertificateChain(certificate2);
                        return (java.security.cert.X509Certificate[]) certificateChain.toArray(new java.security.cert.X509Certificate[certificateChain.size()]);
                    } catch (java.lang.RuntimeException | java.security.cert.CertificateException e) {
                        throw new android.security.KeyChainException(e);
                    }
                }
                if (bind != null) {
                    bind.close();
                    return null;
                }
                return null;
            } catch (java.lang.Throwable th) {
                if (bind != null) {
                    try {
                        bind.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (android.os.RemoteException e2) {
            throw new android.security.KeyChainException(e2);
        } catch (java.lang.RuntimeException e3) {
            throw new android.security.KeyChainException(e3);
        }
    }

    public static boolean isKeyAlgorithmSupported(java.lang.String str) {
        java.lang.String upperCase = str.toUpperCase(java.util.Locale.US);
        return android.security.keystore.KeyProperties.KEY_ALGORITHM_EC.equals(upperCase) || android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA.equals(upperCase);
    }

    @java.lang.Deprecated
    public static boolean isBoundKeyAlgorithm(java.lang.String str) {
        return true;
    }

    public static java.security.cert.X509Certificate toCertificate(byte[] bArr) {
        if (bArr == null) {
            throw new java.lang.IllegalArgumentException("bytes == null");
        }
        try {
            return (java.security.cert.X509Certificate) java.security.cert.CertificateFactory.getInstance("X.509").generateCertificate(new java.io.ByteArrayInputStream(bArr));
        } catch (java.security.cert.CertificateException e) {
            throw new java.lang.AssertionError(e);
        }
    }

    public static java.util.Collection<java.security.cert.X509Certificate> toCertificates(byte[] bArr) {
        if (bArr == null) {
            throw new java.lang.IllegalArgumentException("bytes == null");
        }
        try {
            return java.security.cert.CertificateFactory.getInstance("X.509").generateCertificates(new java.io.ByteArrayInputStream(bArr));
        } catch (java.security.cert.CertificateException e) {
            throw new java.lang.AssertionError(e);
        }
    }

    public static class KeyChainConnection implements java.io.Closeable {
        private final android.content.Context mContext;
        private final android.security.IKeyChainService mService;
        private final android.content.ServiceConnection mServiceConnection;

        protected KeyChainConnection(android.content.Context context, android.content.ServiceConnection serviceConnection, android.security.IKeyChainService iKeyChainService) {
            this.mContext = context;
            this.mServiceConnection = serviceConnection;
            this.mService = iKeyChainService;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.mContext.unbindService(this.mServiceConnection);
        }

        public android.security.IKeyChainService getService() {
            return this.mService;
        }
    }

    public static android.security.KeyChain.KeyChainConnection bind(android.content.Context context) throws java.lang.InterruptedException {
        return bindAsUser(context, android.os.Process.myUserHandle());
    }

    public static android.security.KeyChain.KeyChainConnection bindAsUser(android.content.Context context, android.os.UserHandle userHandle) throws java.lang.InterruptedException {
        return bindAsUser(context, null, userHandle);
    }

    @android.annotation.SystemApi
    public static java.lang.String getWifiKeyGrantAsUser(android.content.Context context, android.os.UserHandle userHandle, java.lang.String str) {
        try {
            try {
                android.security.KeyChain.KeyChainConnection bindAsUser = bindAsUser(context.getApplicationContext(), userHandle);
                try {
                    java.lang.String wifiKeyGrantAsUser = bindAsUser.getService().getWifiKeyGrantAsUser(str);
                    if (bindAsUser != null) {
                        bindAsUser.close();
                    }
                    return wifiKeyGrantAsUser;
                } catch (java.lang.Throwable th) {
                    if (bindAsUser != null) {
                        try {
                            bindAsUser.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                android.util.Log.i(LOG, "Couldn't get grant for wifi", e);
                return null;
            }
        } catch (java.lang.InterruptedException e2) {
            java.lang.Thread.currentThread().interrupt();
            android.util.Log.i(LOG, "Interrupted while getting grant for wifi", e2);
            return null;
        }
    }

    @android.annotation.SystemApi
    public static boolean hasWifiKeyGrantAsUser(android.content.Context context, android.os.UserHandle userHandle, java.lang.String str) {
        try {
            try {
                android.security.KeyChain.KeyChainConnection bindAsUser = bindAsUser(context.getApplicationContext(), userHandle);
                try {
                    boolean hasGrant = bindAsUser.getService().hasGrant(1010, str);
                    if (bindAsUser != null) {
                        bindAsUser.close();
                    }
                    return hasGrant;
                } catch (java.lang.Throwable th) {
                    if (bindAsUser != null) {
                        try {
                            bindAsUser.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.lang.InterruptedException e) {
                java.lang.Thread.currentThread().interrupt();
                android.util.Log.i(LOG, "Interrupted while querying grant for wifi", e);
                return false;
            }
        } catch (android.os.RemoteException | java.lang.RuntimeException e2) {
            android.util.Log.i(LOG, "Couldn't query grant for wifi", e2);
            return false;
        }
    }

    public static android.security.KeyChain.KeyChainConnection bindAsUser(android.content.Context context, android.os.Handler handler, android.os.UserHandle userHandle) throws java.lang.InterruptedException {
        boolean bindServiceAsUser;
        if (context == null) {
            throw new java.lang.NullPointerException("context == null");
        }
        if (handler == null) {
            ensureNotOnMainThread(context);
        }
        if (!android.os.UserManager.get(context).isUserUnlocked(userHandle)) {
            throw new java.lang.IllegalStateException("User must be unlocked");
        }
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
        android.content.ServiceConnection serviceConnection = new android.content.ServiceConnection() { // from class: android.security.KeyChain.1
            volatile boolean mConnectedAtLeastOnce = false;

            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                if (!this.mConnectedAtLeastOnce) {
                    this.mConnectedAtLeastOnce = true;
                    atomicReference.set(android.security.IKeyChainService.Stub.asInterface(android.os.Binder.allowBlocking(iBinder)));
                    countDownLatch.countDown();
                }
            }

            @Override // android.content.ServiceConnection
            public void onBindingDied(android.content.ComponentName componentName) {
                if (!this.mConnectedAtLeastOnce) {
                    this.mConnectedAtLeastOnce = true;
                    countDownLatch.countDown();
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
            }
        };
        android.content.Intent intent = new android.content.Intent(android.security.IKeyChainService.class.getName());
        android.content.ComponentName resolveSystemService = intent.resolveSystemService(context.getPackageManager(), 0);
        if (resolveSystemService == null) {
            throw new java.lang.AssertionError("could not resolve KeyChainService");
        }
        intent.setComponent(resolveSystemService);
        if (handler != null) {
            bindServiceAsUser = context.bindServiceAsUser(intent, serviceConnection, 1, handler, userHandle);
        } else {
            bindServiceAsUser = context.bindServiceAsUser(intent, serviceConnection, 1, userHandle);
        }
        if (!bindServiceAsUser) {
            context.unbindService(serviceConnection);
            throw new java.lang.AssertionError("could not bind to KeyChainService");
        }
        countDownLatch.await();
        android.security.IKeyChainService iKeyChainService = (android.security.IKeyChainService) atomicReference.get();
        if (iKeyChainService != null) {
            return new android.security.KeyChain.KeyChainConnection(context, serviceConnection, iKeyChainService);
        }
        context.unbindService(serviceConnection);
        throw new java.lang.AssertionError("KeyChainService died while binding");
    }

    private static void ensureNotOnMainThread(android.content.Context context) {
        android.os.Looper myLooper = android.os.Looper.myLooper();
        if (myLooper != null && myLooper == context.getMainLooper()) {
            throw new java.lang.IllegalStateException("calling this from your main thread can lead to deadlock");
        }
    }
}
