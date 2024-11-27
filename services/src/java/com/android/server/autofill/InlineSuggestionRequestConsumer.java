package com.android.server.autofill;

/* loaded from: classes.dex */
class InlineSuggestionRequestConsumer implements java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsRequest> {
    static final java.lang.String TAG = "InlineSuggestionRequestConsumer";
    private final java.lang.ref.WeakReference<com.android.server.autofill.Session.AssistDataReceiverImpl> mAssistDataReceiverWeakReference;
    private final java.lang.ref.WeakReference<com.android.server.autofill.ViewState> mViewStateWeakReference;

    InlineSuggestionRequestConsumer(java.lang.ref.WeakReference<com.android.server.autofill.Session.AssistDataReceiverImpl> weakReference, java.lang.ref.WeakReference<com.android.server.autofill.ViewState> weakReference2) {
        this.mAssistDataReceiverWeakReference = weakReference;
        this.mViewStateWeakReference = weakReference2;
    }

    @Override // java.util.function.Consumer
    public void accept(android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest) {
        com.android.server.autofill.Session.AssistDataReceiverImpl assistDataReceiverImpl = this.mAssistDataReceiverWeakReference.get();
        com.android.server.autofill.ViewState viewState = this.mViewStateWeakReference.get();
        if (assistDataReceiverImpl == null) {
            android.util.Slog.wtf(TAG, "assistDataReceiver is null when accepting new inline suggestionrequests");
        } else if (viewState == null) {
            android.util.Slog.wtf(TAG, "view state is null when accepting new inline suggestion requests");
        } else {
            assistDataReceiverImpl.handleInlineSuggestionRequest(inlineSuggestionsRequest, viewState);
        }
    }
}
