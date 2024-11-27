package android.inputmethodservice;

/* loaded from: classes2.dex */
class InlineSuggestionSession {
    static final android.view.inputmethod.InlineSuggestionsResponse EMPTY_RESPONSE = new android.view.inputmethod.InlineSuggestionsResponse((java.util.List<android.view.inputmethod.InlineSuggestion>) java.util.Collections.emptyList());
    private static final java.lang.String TAG = "ImsInlineSuggestionSession";
    private final com.android.internal.inputmethod.IInlineSuggestionsRequestCallback mCallback;
    private boolean mCallbackInvoked = false;
    private final java.util.function.Supplier<android.os.IBinder> mHostInputTokenSupplier;
    private final android.inputmethodservice.InlineSuggestionSessionController mInlineSuggestionSessionController;
    private final android.os.Handler mMainThreadHandler;
    private java.lang.Boolean mPreviousResponseIsEmpty;
    private final com.android.internal.inputmethod.InlineSuggestionsRequestInfo mRequestInfo;
    private final java.util.function.Function<android.os.Bundle, android.view.inputmethod.InlineSuggestionsRequest> mRequestSupplier;
    private android.inputmethodservice.InlineSuggestionSession.InlineSuggestionsResponseCallbackImpl mResponseCallback;
    private final java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsResponse> mResponseConsumer;

    InlineSuggestionSession(com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback, java.util.function.Function<android.os.Bundle, android.view.inputmethod.InlineSuggestionsRequest> function, java.util.function.Supplier<android.os.IBinder> supplier, java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsResponse> consumer, android.inputmethodservice.InlineSuggestionSessionController inlineSuggestionSessionController, android.os.Handler handler) {
        this.mRequestInfo = inlineSuggestionsRequestInfo;
        this.mCallback = iInlineSuggestionsRequestCallback;
        this.mRequestSupplier = function;
        this.mHostInputTokenSupplier = supplier;
        this.mResponseConsumer = consumer;
        this.mInlineSuggestionSessionController = inlineSuggestionSessionController;
        this.mMainThreadHandler = handler;
    }

    com.android.internal.inputmethod.InlineSuggestionsRequestInfo getRequestInfo() {
        return this.mRequestInfo;
    }

    com.android.internal.inputmethod.IInlineSuggestionsRequestCallback getRequestCallback() {
        return this.mCallback;
    }

    boolean shouldSendImeStatus() {
        return this.mResponseCallback != null;
    }

    boolean isCallbackInvoked() {
        return this.mCallbackInvoked;
    }

    void invalidate() {
        try {
            this.mCallback.onInlineSuggestionsSessionInvalidated();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "onInlineSuggestionsSessionInvalidated() remote exception", e);
        }
        if (this.mResponseCallback != null) {
            consumeInlineSuggestionsResponse(EMPTY_RESPONSE);
            this.mResponseCallback.invalidate();
            this.mResponseCallback = null;
        }
    }

    void makeInlineSuggestionRequestUncheck() {
        if (this.mCallbackInvoked) {
            return;
        }
        try {
            android.view.inputmethod.InlineSuggestionsRequest apply = this.mRequestSupplier.apply(this.mRequestInfo.getUiExtras());
            if (apply == null) {
                this.mCallback.onInlineSuggestionsUnsupported();
            } else {
                apply.setHostInputToken(this.mHostInputTokenSupplier.get());
                apply.filterContentTypes();
                this.mResponseCallback = new android.inputmethodservice.InlineSuggestionSession.InlineSuggestionsResponseCallbackImpl();
                this.mCallback.onInlineSuggestionsRequest(apply, this.mResponseCallback);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "makeInlinedSuggestionsRequest() remote exception:" + e);
        }
        this.mCallbackInvoked = true;
    }

    void handleOnInlineSuggestionsResponse(android.view.autofill.AutofillId autofillId, android.view.inputmethod.InlineSuggestionsResponse inlineSuggestionsResponse) {
        if (!this.mInlineSuggestionSessionController.match(autofillId)) {
            return;
        }
        consumeInlineSuggestionsResponse(inlineSuggestionsResponse);
    }

    void consumeInlineSuggestionsResponse(android.view.inputmethod.InlineSuggestionsResponse inlineSuggestionsResponse) {
        boolean isEmpty = inlineSuggestionsResponse.getInlineSuggestions().isEmpty();
        if (isEmpty && java.lang.Boolean.TRUE.equals(this.mPreviousResponseIsEmpty)) {
            return;
        }
        this.mPreviousResponseIsEmpty = java.lang.Boolean.valueOf(isEmpty);
        this.mResponseConsumer.accept(inlineSuggestionsResponse);
    }

    private static final class InlineSuggestionsResponseCallbackImpl extends com.android.internal.inputmethod.IInlineSuggestionsResponseCallback.Stub {
        private volatile boolean mInvalid;
        private final java.lang.ref.WeakReference<android.inputmethodservice.InlineSuggestionSession> mSession;

        private InlineSuggestionsResponseCallbackImpl(android.inputmethodservice.InlineSuggestionSession inlineSuggestionSession) {
            this.mInvalid = false;
            this.mSession = new java.lang.ref.WeakReference<>(inlineSuggestionSession);
        }

        void invalidate() {
            this.mInvalid = true;
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsResponseCallback
        public void onInlineSuggestionsResponse(android.view.autofill.AutofillId autofillId, android.view.inputmethod.InlineSuggestionsResponse inlineSuggestionsResponse) {
            android.inputmethodservice.InlineSuggestionSession inlineSuggestionSession;
            if (!this.mInvalid && (inlineSuggestionSession = this.mSession.get()) != null) {
                inlineSuggestionSession.mMainThreadHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.inputmethodservice.InlineSuggestionSession$InlineSuggestionsResponseCallbackImpl$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((android.inputmethodservice.InlineSuggestionSession) obj).handleOnInlineSuggestionsResponse((android.view.autofill.AutofillId) obj2, (android.view.inputmethod.InlineSuggestionsResponse) obj3);
                    }
                }, inlineSuggestionSession, autofillId, inlineSuggestionsResponse));
            }
        }
    }
}
