package android.service.translation;

/* loaded from: classes3.dex */
final class OnTranslationResultCallbackWrapper implements java.util.function.Consumer<android.view.translation.TranslationResponse> {
    private static final java.lang.String TAG = "OnTranslationResultCallback";
    private final android.service.translation.ITranslationCallback mCallback;
    private final java.util.concurrent.atomic.AtomicBoolean mCalled = new java.util.concurrent.atomic.AtomicBoolean();

    public OnTranslationResultCallbackWrapper(android.service.translation.ITranslationCallback iTranslationCallback) {
        this.mCallback = (android.service.translation.ITranslationCallback) java.util.Objects.requireNonNull(iTranslationCallback);
    }

    @Override // java.util.function.Consumer
    public void accept(android.view.translation.TranslationResponse translationResponse) {
        assertNotCalled();
        if (this.mCalled.getAndSet(translationResponse.isFinalResponse())) {
            throw new java.lang.IllegalStateException("Already called with complete response");
        }
        try {
            this.mCallback.onTranslationResponse(translationResponse);
        } catch (android.os.RemoteException e) {
            if (e instanceof android.os.DeadObjectException) {
                android.util.Log.w(TAG, "Process is dead, ignore.");
                return;
            }
            throw e.rethrowAsRuntimeException();
        }
    }

    private void assertNotCalled() {
        if (this.mCalled.get()) {
            throw new java.lang.IllegalStateException("Already called");
        }
    }
}
