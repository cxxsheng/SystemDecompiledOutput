package android.inputmethodservice;

/* loaded from: classes2.dex */
class InlineSuggestionSessionController {
    private static final java.lang.String TAG = "InlineSuggestionSessionController";
    private final java.util.function.Supplier<android.os.IBinder> mHostInputTokenSupplier;
    private android.view.autofill.AutofillId mImeClientFieldId;
    private java.lang.String mImeClientPackageName;
    private boolean mImeInputStarted;
    private boolean mImeInputViewStarted;
    private final android.os.Handler mMainThreadHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
    private final java.util.function.Function<android.os.Bundle, android.view.inputmethod.InlineSuggestionsRequest> mRequestSupplier;
    private final java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsResponse> mResponseConsumer;
    private android.inputmethodservice.InlineSuggestionSession mSession;

    InlineSuggestionSessionController(java.util.function.Function<android.os.Bundle, android.view.inputmethod.InlineSuggestionsRequest> function, java.util.function.Supplier<android.os.IBinder> supplier, java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsResponse> consumer) {
        this.mRequestSupplier = function;
        this.mHostInputTokenSupplier = supplier;
        this.mResponseConsumer = consumer;
    }

    void onMakeInlineSuggestionsRequest(com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) {
        if (this.mSession != null) {
            this.mSession.invalidate();
        }
        this.mSession = new android.inputmethodservice.InlineSuggestionSession(inlineSuggestionsRequestInfo, iInlineSuggestionsRequestCallback, this.mRequestSupplier, this.mHostInputTokenSupplier, this.mResponseConsumer, this, this.mMainThreadHandler);
        if (this.mImeInputStarted && match(this.mSession.getRequestInfo())) {
            this.mSession.makeInlineSuggestionRequestUncheck();
            if (this.mImeInputViewStarted) {
                try {
                    this.mSession.getRequestCallback().onInputMethodStartInputView();
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(TAG, "onInputMethodStartInputView() remote exception:" + e);
                }
            }
        }
    }

    void notifyOnStartInput(java.lang.String str, android.view.autofill.AutofillId autofillId) {
        if (str == null || autofillId == null) {
            return;
        }
        this.mImeInputStarted = true;
        this.mImeClientPackageName = str;
        this.mImeClientFieldId = autofillId;
        if (this.mSession != null) {
            this.mSession.consumeInlineSuggestionsResponse(android.inputmethodservice.InlineSuggestionSession.EMPTY_RESPONSE);
            if (!this.mSession.isCallbackInvoked() && match(this.mSession.getRequestInfo())) {
                this.mSession.makeInlineSuggestionRequestUncheck();
            } else if (this.mSession.shouldSendImeStatus()) {
                try {
                    this.mSession.getRequestCallback().onInputMethodStartInput(this.mImeClientFieldId);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(TAG, "onInputMethodStartInput() remote exception:" + e);
                }
            }
        }
    }

    void notifyOnShowInputRequested(boolean z) {
        if (this.mSession != null && this.mSession.shouldSendImeStatus()) {
            try {
                this.mSession.getRequestCallback().onInputMethodShowInputRequested(z);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "onInputMethodShowInputRequested() remote exception:" + e);
            }
        }
    }

    void notifyOnStartInputView() {
        this.mImeInputViewStarted = true;
        if (this.mSession != null && this.mSession.shouldSendImeStatus()) {
            try {
                this.mSession.getRequestCallback().onInputMethodStartInputView();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "onInputMethodStartInputView() remote exception:" + e);
            }
        }
    }

    void notifyOnFinishInputView() {
        this.mImeInputViewStarted = false;
        if (this.mSession != null && this.mSession.shouldSendImeStatus()) {
            try {
                this.mSession.getRequestCallback().onInputMethodFinishInputView();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "onInputMethodFinishInputView() remote exception:" + e);
            }
        }
    }

    void notifyOnFinishInput() {
        this.mImeClientPackageName = null;
        this.mImeClientFieldId = null;
        this.mImeInputViewStarted = false;
        this.mImeInputStarted = false;
        if (this.mSession != null && this.mSession.shouldSendImeStatus()) {
            try {
                this.mSession.getRequestCallback().onInputMethodFinishInput();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "onInputMethodFinishInput() remote exception:" + e);
            }
        }
    }

    boolean match(com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo) {
        return match(inlineSuggestionsRequestInfo, this.mImeClientPackageName, this.mImeClientFieldId);
    }

    boolean match(android.view.autofill.AutofillId autofillId) {
        return match(autofillId, this.mImeClientFieldId);
    }

    private static boolean match(com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, java.lang.String str, android.view.autofill.AutofillId autofillId) {
        return (inlineSuggestionsRequestInfo == null || str == null || autofillId == null || !inlineSuggestionsRequestInfo.getComponentName().getPackageName().equals(str) || !match(inlineSuggestionsRequestInfo.getAutofillId(), autofillId)) ? false : true;
    }

    private static boolean match(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillId autofillId2) {
        return (autofillId == null || autofillId2 == null || autofillId.getViewId() != autofillId2.getViewId()) ? false : true;
    }
}
