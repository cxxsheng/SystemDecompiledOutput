package android.service.autofill;

/* loaded from: classes3.dex */
public final class ConvertCredentialCallback {
    private static final java.lang.String TAG = "ConvertCredentialCallback";
    private final android.service.autofill.IConvertCredentialCallback mCallback;

    public ConvertCredentialCallback(android.service.autofill.IConvertCredentialCallback iConvertCredentialCallback) {
        this.mCallback = iConvertCredentialCallback;
    }

    public void onSuccess(android.service.autofill.ConvertCredentialResponse convertCredentialResponse) {
        try {
            this.mCallback.onSuccess(convertCredentialResponse);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void onFailure(java.lang.CharSequence charSequence) {
        try {
            this.mCallback.onFailure(charSequence);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }
}
