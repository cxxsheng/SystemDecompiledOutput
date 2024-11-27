package com.android.server.autofill.ui;

/* loaded from: classes.dex */
final class RemoteInlineSuggestionViewConnector {
    private static final java.lang.String TAG = com.android.server.autofill.ui.RemoteInlineSuggestionViewConnector.class.getSimpleName();
    private final int mDisplayId;

    @android.annotation.Nullable
    private final android.os.IBinder mHostInputToken;

    @android.annotation.NonNull
    private final android.service.autofill.InlinePresentation mInlinePresentation;

    @android.annotation.NonNull
    private final java.lang.Runnable mOnAutofillCallback;

    @android.annotation.NonNull
    private final java.lang.Runnable mOnErrorCallback;

    @android.annotation.NonNull
    private final java.lang.Runnable mOnInflateCallback;

    @android.annotation.Nullable
    private final com.android.server.autofill.RemoteInlineSuggestionRenderService mRemoteRenderService;
    private final int mSessionId;

    @android.annotation.NonNull
    private final java.util.function.Consumer<android.content.IntentSender> mStartIntentSenderFromClientApp;
    private final int mUserId;

    RemoteInlineSuggestionViewConnector(@android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineFillUiInfo inlineFillUiInfo, @android.annotation.NonNull android.service.autofill.InlinePresentation inlinePresentation, @android.annotation.NonNull java.lang.Runnable runnable, @android.annotation.NonNull final com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback inlineSuggestionUiCallback) {
        this.mRemoteRenderService = inlineFillUiInfo.mRemoteRenderService;
        this.mInlinePresentation = inlinePresentation;
        this.mHostInputToken = inlineFillUiInfo.mInlineRequest.getHostInputToken();
        this.mDisplayId = inlineFillUiInfo.mInlineRequest.getHostDisplayId();
        this.mUserId = inlineFillUiInfo.mUserId;
        this.mSessionId = inlineFillUiInfo.mSessionId;
        this.mOnAutofillCallback = runnable;
        java.util.Objects.requireNonNull(inlineSuggestionUiCallback);
        this.mOnErrorCallback = new java.lang.Runnable() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionViewConnector$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback.this.onError();
            }
        };
        java.util.Objects.requireNonNull(inlineSuggestionUiCallback);
        this.mOnInflateCallback = new java.lang.Runnable() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionViewConnector$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback.this.onInflate();
            }
        };
        java.util.Objects.requireNonNull(inlineSuggestionUiCallback);
        this.mStartIntentSenderFromClientApp = new java.util.function.Consumer() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionViewConnector$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback.this.startIntentSender((android.content.IntentSender) obj);
            }
        };
    }

    public boolean renderSuggestion(int i, int i2, @android.annotation.NonNull android.service.autofill.IInlineSuggestionUiCallback iInlineSuggestionUiCallback) {
        if (this.mRemoteRenderService != null) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "Request to recreate the UI");
            }
            this.mRemoteRenderService.renderSuggestion(iInlineSuggestionUiCallback, this.mInlinePresentation, i, i2, this.mHostInputToken, this.mDisplayId, this.mUserId, this.mSessionId);
            return true;
        }
        return false;
    }

    public void onClick() {
        this.mOnAutofillCallback.run();
    }

    public void onError() {
        this.mOnErrorCallback.run();
    }

    public void onRender() {
        this.mOnInflateCallback.run();
    }

    public void onTransferTouchFocusToImeWindow(android.os.IBinder iBinder, int i) {
        if (!((com.android.server.inputmethod.InputMethodManagerInternal) com.android.server.LocalServices.getService(com.android.server.inputmethod.InputMethodManagerInternal.class)).transferTouchFocusToImeWindow(iBinder, i)) {
            android.util.Slog.e(TAG, "Cannot transfer touch focus from suggestion to IME");
            this.mOnErrorCallback.run();
        }
    }

    public void onStartIntentSender(android.content.IntentSender intentSender) {
        this.mStartIntentSenderFromClientApp.accept(intentSender);
    }
}
