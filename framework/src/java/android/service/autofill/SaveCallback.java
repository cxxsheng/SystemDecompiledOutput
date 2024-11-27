package android.service.autofill;

/* loaded from: classes3.dex */
public final class SaveCallback {
    private static final java.lang.String TAG = "SaveCallback";
    private final android.service.autofill.ISaveCallback mCallback;
    private boolean mCalled;

    SaveCallback(android.service.autofill.ISaveCallback iSaveCallback) {
        this.mCallback = iSaveCallback;
    }

    public void onSuccess() {
        onSuccessInternal(null);
    }

    public void onSuccess(android.content.IntentSender intentSender) {
        onSuccessInternal((android.content.IntentSender) java.util.Objects.requireNonNull(intentSender));
    }

    private void onSuccessInternal(android.content.IntentSender intentSender) {
        assertNotCalled();
        this.mCalled = true;
        try {
            this.mCallback.onSuccess(intentSender);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void onFailure(java.lang.CharSequence charSequence) {
        android.util.Log.w(TAG, "onFailure(): " + ((java.lang.Object) charSequence));
        assertNotCalled();
        this.mCalled = true;
        try {
            this.mCallback.onFailure(charSequence);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    private void assertNotCalled() {
        if (this.mCalled) {
            throw new java.lang.IllegalStateException("Already called");
        }
    }
}
