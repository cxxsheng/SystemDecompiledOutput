package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class AutofillSuggestionsController {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = com.android.server.inputmethod.AutofillSuggestionsController.class.getSimpleName();

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    private com.android.internal.inputmethod.IInlineSuggestionsRequestCallback mInlineSuggestionsRequestCallback;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    private com.android.server.inputmethod.AutofillSuggestionsController.CreateInlineSuggestionsRequest mPendingInlineSuggestionsRequest;

    @android.annotation.NonNull
    private final com.android.server.inputmethod.InputMethodManagerService mService;

    private static final class CreateInlineSuggestionsRequest {

        @android.annotation.NonNull
        final com.android.internal.inputmethod.IInlineSuggestionsRequestCallback mCallback;

        @android.annotation.NonNull
        final java.lang.String mPackageName;

        @android.annotation.NonNull
        final com.android.internal.inputmethod.InlineSuggestionsRequestInfo mRequestInfo;

        CreateInlineSuggestionsRequest(@android.annotation.NonNull com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, @android.annotation.NonNull com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback, @android.annotation.NonNull java.lang.String str) {
            this.mRequestInfo = inlineSuggestionsRequestInfo;
            this.mCallback = iInlineSuggestionsRequestCallback;
            this.mPackageName = str;
        }
    }

    AutofillSuggestionsController(@android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerService inputMethodManagerService) {
        this.mService = inputMethodManagerService;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void onCreateInlineSuggestionsRequest(int i, com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback, boolean z) {
        clearPendingInlineSuggestionsRequest();
        this.mInlineSuggestionsRequestCallback = iInlineSuggestionsRequestCallback;
        android.view.inputmethod.InputMethodInfo queryInputMethodForCurrentUserLocked = this.mService.queryInputMethodForCurrentUserLocked(this.mService.getSelectedMethodIdLocked());
        try {
            if (i == this.mService.getCurrentImeUserIdLocked() && queryInputMethodForCurrentUserLocked != null && isInlineSuggestionsEnabled(queryInputMethodForCurrentUserLocked, z)) {
                this.mPendingInlineSuggestionsRequest = new com.android.server.inputmethod.AutofillSuggestionsController.CreateInlineSuggestionsRequest(inlineSuggestionsRequestInfo, iInlineSuggestionsRequestCallback, queryInputMethodForCurrentUserLocked.getPackageName());
                if (this.mService.getCurMethodLocked() != null) {
                    performOnCreateInlineSuggestionsRequest();
                }
            } else {
                iInlineSuggestionsRequestCallback.onInlineSuggestionsUnsupported();
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "RemoteException calling onCreateInlineSuggestionsRequest(): " + e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void performOnCreateInlineSuggestionsRequest() {
        if (this.mPendingInlineSuggestionsRequest == null) {
            return;
        }
        com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = this.mService.getCurMethodLocked();
        if (curMethodLocked != null) {
            curMethodLocked.onCreateInlineSuggestionsRequest(this.mPendingInlineSuggestionsRequest.mRequestInfo, new com.android.server.inputmethod.AutofillSuggestionsController.InlineSuggestionsRequestCallbackDecorator(this.mPendingInlineSuggestionsRequest.mCallback, this.mPendingInlineSuggestionsRequest.mPackageName, this.mService.getCurTokenDisplayIdLocked(), this.mService.getCurTokenLocked(), this.mService));
        } else {
            android.util.Slog.w(TAG, "No IME connected! Abandoning inline suggestions creation request.");
        }
        clearPendingInlineSuggestionsRequest();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void clearPendingInlineSuggestionsRequest() {
        this.mPendingInlineSuggestionsRequest = null;
    }

    private static boolean isInlineSuggestionsEnabled(android.view.inputmethod.InputMethodInfo inputMethodInfo, boolean z) {
        return inputMethodInfo.isInlineSuggestionsEnabled() && (!z || inputMethodInfo.supportsInlineSuggestionsWithTouchExploration());
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void invalidateAutofillSession() {
        if (this.mInlineSuggestionsRequestCallback != null) {
            try {
                this.mInlineSuggestionsRequestCallback.onInlineSuggestionsSessionInvalidated();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Cannot invalidate autofill session.", e);
            }
        }
    }

    private static final class InlineSuggestionsRequestCallbackDecorator extends com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.Stub {

        @android.annotation.NonNull
        private final com.android.internal.inputmethod.IInlineSuggestionsRequestCallback mCallback;
        private final int mImeDisplayId;

        @android.annotation.NonNull
        private final java.lang.String mImePackageName;

        @android.annotation.NonNull
        private final android.os.IBinder mImeToken;

        @android.annotation.NonNull
        private final com.android.server.inputmethod.InputMethodManagerService mImms;

        InlineSuggestionsRequestCallbackDecorator(@android.annotation.NonNull com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback, @android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerService inputMethodManagerService) {
            this.mCallback = iInlineSuggestionsRequestCallback;
            this.mImePackageName = str;
            this.mImeDisplayId = i;
            this.mImeToken = iBinder;
            this.mImms = inputMethodManagerService;
        }

        public void onInlineSuggestionsUnsupported() throws android.os.RemoteException {
            this.mCallback.onInlineSuggestionsUnsupported();
        }

        public void onInlineSuggestionsRequest(android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, com.android.internal.inputmethod.IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback) throws android.os.RemoteException {
            if (!this.mImePackageName.equals(inlineSuggestionsRequest.getHostPackageName())) {
                throw new java.lang.SecurityException("Host package name in the provide request=[" + inlineSuggestionsRequest.getHostPackageName() + "] doesn't match the IME package name=[" + this.mImePackageName + "].");
            }
            inlineSuggestionsRequest.setHostDisplayId(this.mImeDisplayId);
            this.mImms.setCurHostInputToken(this.mImeToken, inlineSuggestionsRequest.getHostInputToken());
            this.mCallback.onInlineSuggestionsRequest(inlineSuggestionsRequest, iInlineSuggestionsResponseCallback);
        }

        public void onInputMethodStartInput(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
            this.mCallback.onInputMethodStartInput(autofillId);
        }

        public void onInputMethodShowInputRequested(boolean z) throws android.os.RemoteException {
            this.mCallback.onInputMethodShowInputRequested(z);
        }

        public void onInputMethodStartInputView() throws android.os.RemoteException {
            this.mCallback.onInputMethodStartInputView();
        }

        public void onInputMethodFinishInputView() throws android.os.RemoteException {
            this.mCallback.onInputMethodFinishInputView();
        }

        public void onInputMethodFinishInput() throws android.os.RemoteException {
            this.mCallback.onInputMethodFinishInput();
        }

        public void onInlineSuggestionsSessionInvalidated() throws android.os.RemoteException {
            this.mCallback.onInlineSuggestionsSessionInvalidated();
        }
    }
}
