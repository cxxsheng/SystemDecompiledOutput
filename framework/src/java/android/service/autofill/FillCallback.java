package android.service.autofill;

/* loaded from: classes3.dex */
public final class FillCallback {
    private static final java.lang.String TAG = "FillCallback";
    private final android.service.autofill.IFillCallback mCallback;
    private boolean mCalled;
    private final int mRequestId;

    public FillCallback(android.service.autofill.IFillCallback iFillCallback, int i) {
        this.mCallback = iFillCallback;
        this.mRequestId = i;
    }

    public void onSuccess(android.service.autofill.FillResponse fillResponse) {
        assertNotCalled();
        this.mCalled = true;
        if (fillResponse != null) {
            fillResponse.setRequestId(this.mRequestId);
        }
        try {
            this.mCallback.onSuccess(fillResponse);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void onFailure(java.lang.CharSequence charSequence) {
        android.util.Log.w(TAG, "onFailure(): " + ((java.lang.Object) charSequence));
        assertNotCalled();
        this.mCalled = true;
        try {
            this.mCallback.onFailure(this.mRequestId, charSequence);
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
