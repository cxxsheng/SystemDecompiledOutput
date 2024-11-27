package android.security;

/* loaded from: classes3.dex */
public class KeyStoreOperation {
    static final java.lang.String TAG = "KeyStoreOperation";
    private final java.lang.Long mChallenge;
    private final android.system.keystore2.IKeystoreOperation mOperation;
    private final android.hardware.security.keymint.KeyParameter[] mParameters;

    public KeyStoreOperation(android.system.keystore2.IKeystoreOperation iKeystoreOperation, java.lang.Long l, android.hardware.security.keymint.KeyParameter[] keyParameterArr) {
        android.os.Binder.allowBlocking(iKeystoreOperation.asBinder());
        this.mOperation = iKeystoreOperation;
        this.mChallenge = l;
        this.mParameters = keyParameterArr;
    }

    public java.lang.Long getChallenge() {
        return this.mChallenge;
    }

    public android.hardware.security.keymint.KeyParameter[] getParameters() {
        return this.mParameters;
    }

    private <R> R handleExceptions(android.security.CheckedRemoteRequest<R> checkedRemoteRequest) throws android.security.KeyStoreException {
        try {
            return checkedRemoteRequest.execute();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Remote exception while advancing a KeyStoreOperation.", e);
            throw new android.security.KeyStoreException(-28, "", e.getMessage());
        } catch (android.os.ServiceSpecificException e2) {
            switch (e2.errorCode) {
                case 19:
                    throw new java.lang.IllegalThreadStateException("Cannot update the same operation concurrently.");
                default:
                    throw android.security.KeyStore2.getKeyStoreException(e2.errorCode, e2.getMessage());
            }
        }
    }

    public void updateAad(final byte[] bArr) throws android.security.KeyStoreException {
        android.os.StrictMode.noteSlowCall("updateAad");
        handleExceptions(new android.security.CheckedRemoteRequest() { // from class: android.security.KeyStoreOperation$$ExternalSyntheticLambda0
            @Override // android.security.CheckedRemoteRequest
            public final java.lang.Object execute() {
                java.lang.Integer lambda$updateAad$0;
                lambda$updateAad$0 = android.security.KeyStoreOperation.this.lambda$updateAad$0(bArr);
                return lambda$updateAad$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$updateAad$0(byte[] bArr) throws android.os.RemoteException {
        this.mOperation.updateAad(bArr);
        return 0;
    }

    public byte[] update(final byte[] bArr) throws android.security.KeyStoreException {
        android.os.StrictMode.noteSlowCall("update");
        return (byte[]) handleExceptions(new android.security.CheckedRemoteRequest() { // from class: android.security.KeyStoreOperation$$ExternalSyntheticLambda2
            @Override // android.security.CheckedRemoteRequest
            public final java.lang.Object execute() {
                byte[] lambda$update$1;
                lambda$update$1 = android.security.KeyStoreOperation.this.lambda$update$1(bArr);
                return lambda$update$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ byte[] lambda$update$1(byte[] bArr) throws android.os.RemoteException {
        return this.mOperation.update(bArr);
    }

    public byte[] finish(final byte[] bArr, final byte[] bArr2) throws android.security.KeyStoreException {
        android.os.StrictMode.noteSlowCall("finish");
        return (byte[]) handleExceptions(new android.security.CheckedRemoteRequest() { // from class: android.security.KeyStoreOperation$$ExternalSyntheticLambda1
            @Override // android.security.CheckedRemoteRequest
            public final java.lang.Object execute() {
                byte[] lambda$finish$2;
                lambda$finish$2 = android.security.KeyStoreOperation.this.lambda$finish$2(bArr, bArr2);
                return lambda$finish$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ byte[] lambda$finish$2(byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
        return this.mOperation.finish(bArr, bArr2);
    }

    public void abort() throws android.security.KeyStoreException {
        android.os.StrictMode.noteSlowCall("abort");
        handleExceptions(new android.security.CheckedRemoteRequest() { // from class: android.security.KeyStoreOperation$$ExternalSyntheticLambda3
            @Override // android.security.CheckedRemoteRequest
            public final java.lang.Object execute() {
                java.lang.Integer lambda$abort$3;
                lambda$abort$3 = android.security.KeyStoreOperation.this.lambda$abort$3();
                return lambda$abort$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$abort$3() throws android.os.RemoteException {
        this.mOperation.abort();
        return 0;
    }
}
