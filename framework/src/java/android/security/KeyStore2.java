package android.security;

/* loaded from: classes3.dex */
public class KeyStore2 {
    private static final java.lang.String KEYSTORE2_SERVICE_NAME = "android.system.keystore2.IKeystoreService/default";
    private static final java.lang.String KEYSTORE_ENGINE_GRANT_ALIAS_PREFIX = "ks2_keystore-engine_grant_id:0x";
    static final long KEYSTORE_OPERATION_CREATION_MAY_FAIL = 169897160;
    private static final int RECOVERY_GRACE_PERIOD_MS = 50;
    private static final java.lang.String TAG = "KeyStore";
    private android.system.keystore2.IKeystoreService mBinder = null;

    @java.lang.FunctionalInterface
    interface CheckedRemoteRequest<R> {
        R execute(android.system.keystore2.IKeystoreService iKeystoreService) throws android.os.RemoteException;
    }

    private <R> R handleRemoteExceptionWithRetry(android.security.KeyStore2.CheckedRemoteRequest<R> checkedRemoteRequest) throws android.security.KeyStoreException {
        android.system.keystore2.IKeystoreService service = getService(false);
        boolean z = true;
        while (true) {
            try {
                return checkedRemoteRequest.execute(service);
            } catch (android.os.RemoteException e) {
                if (z) {
                    android.util.Log.w(TAG, "Looks like we may have lost connection to the Keystore daemon.");
                    android.util.Log.w(TAG, "Retrying after giving Keystore 50ms to recover.");
                    interruptedPreservingSleep(50L);
                    service = getService(true);
                    z = false;
                } else {
                    android.util.Log.e(TAG, "Cannot connect to Keystore daemon.", e);
                    throw new android.security.KeyStoreException(4, "", e.getMessage());
                }
            } catch (android.os.ServiceSpecificException e2) {
                throw getKeyStoreException(e2.errorCode, e2.getMessage());
            }
        }
    }

    private KeyStore2() {
    }

    public static android.security.KeyStore2 getInstance() {
        return new android.security.KeyStore2();
    }

    private synchronized android.system.keystore2.IKeystoreService getService(boolean z) {
        if (this.mBinder == null || z) {
            this.mBinder = android.system.keystore2.IKeystoreService.Stub.asInterface(android.os.ServiceManager.getService(KEYSTORE2_SERVICE_NAME));
        }
        if (this.mBinder == null) {
            throw new java.lang.IllegalStateException("Could not connect to Keystore service. Keystore may have crashed or not been initialized");
        }
        android.os.Binder.allowBlocking(this.mBinder.asBinder());
        return this.mBinder;
    }

    void delete(final android.system.keystore2.KeyDescriptor keyDescriptor) throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskWrite();
        handleRemoteExceptionWithRetry(new android.security.KeyStore2.CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda6
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final java.lang.Object execute(android.system.keystore2.IKeystoreService iKeystoreService) {
                return android.security.KeyStore2.lambda$delete$0(android.system.keystore2.KeyDescriptor.this, iKeystoreService);
            }
        });
    }

    static /* synthetic */ java.lang.Integer lambda$delete$0(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.IKeystoreService iKeystoreService) throws android.os.RemoteException {
        iKeystoreService.deleteKey(keyDescriptor);
        return 0;
    }

    public android.system.keystore2.KeyDescriptor[] list(final int i, final long j) throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskRead();
        return (android.system.keystore2.KeyDescriptor[]) handleRemoteExceptionWithRetry(new android.security.KeyStore2.CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda3
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final java.lang.Object execute(android.system.keystore2.IKeystoreService iKeystoreService) {
                android.system.keystore2.KeyDescriptor[] listEntries;
                listEntries = iKeystoreService.listEntries(i, j);
                return listEntries;
            }
        });
    }

    public android.system.keystore2.KeyDescriptor[] listBatch(final int i, final long j, final java.lang.String str) throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskRead();
        return (android.system.keystore2.KeyDescriptor[]) handleRemoteExceptionWithRetry(new android.security.KeyStore2.CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda5
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final java.lang.Object execute(android.system.keystore2.IKeystoreService iKeystoreService) {
                android.system.keystore2.KeyDescriptor[] listEntriesBatched;
                listEntriesBatched = iKeystoreService.listEntriesBatched(i, j, str);
                return listEntriesBatched;
            }
        });
    }

    public static java.lang.String makeKeystoreEngineGrantString(long j) {
        return java.lang.String.format("%s%016X", KEYSTORE_ENGINE_GRANT_ALIAS_PREFIX, java.lang.Long.valueOf(j));
    }

    public static android.system.keystore2.KeyDescriptor keystoreEngineGrantString2KeyDescriptor(java.lang.String str) {
        android.system.keystore2.KeyDescriptor keyDescriptor = new android.system.keystore2.KeyDescriptor();
        keyDescriptor.domain = 1;
        keyDescriptor.nspace = java.lang.Long.parseUnsignedLong(str.substring(KEYSTORE_ENGINE_GRANT_ALIAS_PREFIX.length()), 16);
        keyDescriptor.alias = null;
        keyDescriptor.blob = null;
        return keyDescriptor;
    }

    public android.system.keystore2.KeyDescriptor grant(final android.system.keystore2.KeyDescriptor keyDescriptor, final int i, final int i2) throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskWrite();
        return (android.system.keystore2.KeyDescriptor) handleRemoteExceptionWithRetry(new android.security.KeyStore2.CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda9
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final java.lang.Object execute(android.system.keystore2.IKeystoreService iKeystoreService) {
                android.system.keystore2.KeyDescriptor grant;
                grant = iKeystoreService.grant(android.system.keystore2.KeyDescriptor.this, i, i2);
                return grant;
            }
        });
    }

    public void ungrant(final android.system.keystore2.KeyDescriptor keyDescriptor, final int i) throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskWrite();
        handleRemoteExceptionWithRetry(new android.security.KeyStore2.CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda2
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final java.lang.Object execute(android.system.keystore2.IKeystoreService iKeystoreService) {
                return android.security.KeyStore2.lambda$ungrant$4(android.system.keystore2.KeyDescriptor.this, i, iKeystoreService);
            }
        });
    }

    static /* synthetic */ java.lang.Integer lambda$ungrant$4(android.system.keystore2.KeyDescriptor keyDescriptor, int i, android.system.keystore2.IKeystoreService iKeystoreService) throws android.os.RemoteException {
        iKeystoreService.ungrant(keyDescriptor, i);
        return 0;
    }

    public android.system.keystore2.KeyEntryResponse getKeyEntry(final android.system.keystore2.KeyDescriptor keyDescriptor) throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskRead();
        return (android.system.keystore2.KeyEntryResponse) handleRemoteExceptionWithRetry(new android.security.KeyStore2.CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda8
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final java.lang.Object execute(android.system.keystore2.IKeystoreService iKeystoreService) {
                android.system.keystore2.KeyEntryResponse keyEntry;
                keyEntry = iKeystoreService.getKeyEntry(android.system.keystore2.KeyDescriptor.this);
                return keyEntry;
            }
        });
    }

    public android.security.KeyStoreSecurityLevel getSecurityLevel(final int i) throws android.security.KeyStoreException {
        return (android.security.KeyStoreSecurityLevel) handleRemoteExceptionWithRetry(new android.security.KeyStore2.CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda1
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final java.lang.Object execute(android.system.keystore2.IKeystoreService iKeystoreService) {
                return android.security.KeyStore2.lambda$getSecurityLevel$6(i, iKeystoreService);
            }
        });
    }

    static /* synthetic */ android.security.KeyStoreSecurityLevel lambda$getSecurityLevel$6(int i, android.system.keystore2.IKeystoreService iKeystoreService) throws android.os.RemoteException {
        return new android.security.KeyStoreSecurityLevel(iKeystoreService.getSecurityLevel(i));
    }

    public void updateSubcomponents(final android.system.keystore2.KeyDescriptor keyDescriptor, final byte[] bArr, final byte[] bArr2) throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskWrite();
        handleRemoteExceptionWithRetry(new android.security.KeyStore2.CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda7
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final java.lang.Object execute(android.system.keystore2.IKeystoreService iKeystoreService) {
                return android.security.KeyStore2.lambda$updateSubcomponents$7(android.system.keystore2.KeyDescriptor.this, bArr, bArr2, iKeystoreService);
            }
        });
    }

    static /* synthetic */ java.lang.Integer lambda$updateSubcomponents$7(android.system.keystore2.KeyDescriptor keyDescriptor, byte[] bArr, byte[] bArr2, android.system.keystore2.IKeystoreService iKeystoreService) throws android.os.RemoteException {
        iKeystoreService.updateSubcomponent(keyDescriptor, bArr, bArr2);
        return 0;
    }

    public void deleteKey(final android.system.keystore2.KeyDescriptor keyDescriptor) throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskWrite();
        handleRemoteExceptionWithRetry(new android.security.KeyStore2.CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda4
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final java.lang.Object execute(android.system.keystore2.IKeystoreService iKeystoreService) {
                return android.security.KeyStore2.lambda$deleteKey$8(android.system.keystore2.KeyDescriptor.this, iKeystoreService);
            }
        });
    }

    static /* synthetic */ java.lang.Integer lambda$deleteKey$8(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.IKeystoreService iKeystoreService) throws android.os.RemoteException {
        iKeystoreService.deleteKey(keyDescriptor);
        return 0;
    }

    public int getNumberOfEntries(final int i, final long j) throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskRead();
        return ((java.lang.Integer) handleRemoteExceptionWithRetry(new android.security.KeyStore2.CheckedRemoteRequest() { // from class: android.security.KeyStore2$$ExternalSyntheticLambda0
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final java.lang.Object execute(android.system.keystore2.IKeystoreService iKeystoreService) {
                java.lang.Integer valueOf;
                valueOf = java.lang.Integer.valueOf(iKeystoreService.getNumberOfEntries(i, j));
                return valueOf;
            }
        })).intValue();
    }

    protected static void interruptedPreservingSleep(long j) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis() + j;
        boolean z = false;
        while (true) {
            try {
                java.lang.Thread.sleep(timeInMillis - calendar.getTimeInMillis());
                break;
            } catch (java.lang.IllegalArgumentException e) {
            } catch (java.lang.InterruptedException e2) {
                z = true;
            }
        }
        if (z) {
            java.lang.Thread.currentThread().interrupt();
        }
    }

    static android.security.KeyStoreException getKeyStoreException(int i, java.lang.String str) {
        if (i > 0) {
            switch (i) {
                case 2:
                    return new android.security.KeyStoreException(i, "User authentication required", str);
                case 3:
                    return new android.security.KeyStoreException(i, "Keystore not initialized", str);
                case 4:
                    return new android.security.KeyStoreException(i, "System error", str);
                case 6:
                    return new android.security.KeyStoreException(i, "Permission denied", str);
                case 7:
                    return new android.security.KeyStoreException(i, "Key not found", str);
                case 8:
                    return new android.security.KeyStoreException(i, "Key blob corrupted", str);
                case 17:
                    return new android.security.KeyStoreException(i, "Key permanently invalidated", str);
                case 22:
                    return new android.security.KeyStoreException(i, str, 1);
                default:
                    return new android.security.KeyStoreException(i, java.lang.String.valueOf(i), str);
            }
        }
        switch (i) {
            case -16:
                return new android.security.KeyStoreException(i, "Invalid user authentication validity duration", str);
            default:
                return new android.security.KeyStoreException(i, android.security.keymaster.KeymasterDefs.getErrorMessage(i), str);
        }
    }
}
