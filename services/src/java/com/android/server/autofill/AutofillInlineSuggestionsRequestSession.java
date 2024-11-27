package com.android.server.autofill;

/* loaded from: classes.dex */
final class AutofillInlineSuggestionsRequestSession {
    private static final java.lang.String TAG = com.android.server.autofill.AutofillInlineSuggestionsRequestSession.class.getSimpleName();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.view.autofill.AutofillId mAutofillId;

    @android.annotation.NonNull
    private final android.content.ComponentName mComponentName;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.view.autofill.AutofillId mImeCurrentFieldId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mImeInputStarted;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mImeInputViewStarted;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.view.inputmethod.InlineSuggestionsRequest mImeRequest;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsRequest> mImeRequestConsumer;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mImeRequestReceived;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.autofill.ui.InlineFillUi mInlineFillUi;

    @android.annotation.NonNull
    private final com.android.server.inputmethod.InputMethodManagerInternal mInputMethodManagerInternal;

    @android.annotation.NonNull
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mPreviousHasNonPinSuggestionShow;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.internal.inputmethod.IInlineSuggestionsResponseCallback mResponseCallback;

    @android.annotation.NonNull
    private final com.android.server.autofill.ui.InlineFillUi.InlineUiEventCallback mUiCallback;

    @android.annotation.NonNull
    private final android.os.Bundle mUiExtras;
    private final int mUserId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.Boolean mPreviousResponseIsNotEmpty = null;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mDestroyed = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mImeSessionInvalidated = false;
    private boolean mImeShowing = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: -$$Nest$mhandleOnReceiveImeStatusUpdated, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m2032$$Nest$mhandleOnReceiveImeStatusUpdated(com.android.server.autofill.AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession, boolean z, boolean z2) {
        autofillInlineSuggestionsRequestSession.handleOnReceiveImeStatusUpdated(z, z2);
    }

    AutofillInlineSuggestionsRequestSession(@android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerInternal inputMethodManagerInternal, int i, @android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull android.view.autofill.AutofillId autofillId, @android.annotation.NonNull java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsRequest> consumer, @android.annotation.NonNull android.os.Bundle bundle, @android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineUiEventCallback inlineUiEventCallback) {
        this.mInputMethodManagerInternal = inputMethodManagerInternal;
        this.mUserId = i;
        this.mComponentName = componentName;
        this.mHandler = handler;
        this.mLock = obj;
        this.mUiExtras = bundle;
        this.mUiCallback = inlineUiEventCallback;
        this.mAutofillId = autofillId;
        this.mImeRequestConsumer = consumer;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    android.view.autofill.AutofillId getAutofillIdLocked() {
        return this.mAutofillId;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    java.util.Optional<android.view.inputmethod.InlineSuggestionsRequest> getInlineSuggestionsRequestLocked() {
        if (this.mDestroyed) {
            return java.util.Optional.empty();
        }
        return java.util.Optional.ofNullable(this.mImeRequest);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean onInlineSuggestionsResponseLocked(@android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi inlineFillUi) {
        if (this.mDestroyed) {
            return false;
        }
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "onInlineSuggestionsResponseLocked called for:" + inlineFillUi.getAutofillId());
        }
        if (this.mImeRequest == null || this.mResponseCallback == null || this.mImeSessionInvalidated) {
            return false;
        }
        this.mAutofillId = inlineFillUi.getAutofillId();
        this.mInlineFillUi = inlineFillUi;
        maybeUpdateResponseToImeLocked();
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void destroySessionLocked() {
        this.mDestroyed = true;
        if (!this.mImeRequestReceived) {
            android.util.Slog.w(TAG, "Never received an InlineSuggestionsRequest from the IME for " + this.mAutofillId);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onCreateInlineSuggestionsRequestLocked() {
        if (this.mDestroyed) {
            return;
        }
        this.mImeSessionInvalidated = false;
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "onCreateInlineSuggestionsRequestLocked called: " + this.mAutofillId);
        }
        this.mInputMethodManagerInternal.onCreateInlineSuggestionsRequest(this.mUserId, new com.android.internal.inputmethod.InlineSuggestionsRequestInfo(this.mComponentName, this.mAutofillId, this.mUiExtras), new com.android.server.autofill.AutofillInlineSuggestionsRequestSession.InlineSuggestionsRequestCallbackImpl());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void resetInlineFillUiLocked() {
        this.mInlineFillUi = null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void maybeUpdateResponseToImeLocked() {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "maybeUpdateResponseToImeLocked called");
        }
        if (!this.mDestroyed && this.mResponseCallback != null && this.mImeInputViewStarted && this.mInlineFillUi != null && match(this.mAutofillId, this.mImeCurrentFieldId)) {
            android.view.inputmethod.InlineSuggestionsResponse inlineSuggestionsResponse = this.mInlineFillUi.getInlineSuggestionsResponse();
            boolean isEmpty = inlineSuggestionsResponse.getInlineSuggestions().isEmpty();
            if (isEmpty && java.lang.Boolean.FALSE.equals(this.mPreviousResponseIsNotEmpty)) {
                return;
            }
            maybeNotifyFillUiEventLocked(inlineSuggestionsResponse.getInlineSuggestions());
            updateResponseToImeUncheckLocked(inlineSuggestionsResponse);
            this.mPreviousResponseIsNotEmpty = java.lang.Boolean.valueOf(!isEmpty);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateResponseToImeUncheckLocked(android.view.inputmethod.InlineSuggestionsResponse inlineSuggestionsResponse) {
        if (this.mDestroyed) {
            return;
        }
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "Send inline response: " + inlineSuggestionsResponse.getInlineSuggestions().size());
        }
        try {
            this.mResponseCallback.onInlineSuggestionsResponse(this.mAutofillId, inlineSuggestionsResponse);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "RemoteException sending InlineSuggestionsResponse to IME");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void maybeNotifyFillUiEventLocked(@android.annotation.NonNull java.util.List<android.view.inputmethod.InlineSuggestion> list) {
        if (this.mDestroyed) {
            return;
        }
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= list.size()) {
                break;
            }
            if (list.get(i).getInfo().isPinned()) {
                i++;
            } else {
                z = true;
                break;
            }
        }
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "maybeNotifyFillUiEventLoked(): hasSuggestionToShow=" + z + ", mPreviousHasNonPinSuggestionShow=" + this.mPreviousHasNonPinSuggestionShow);
        }
        if (z && !this.mPreviousHasNonPinSuggestionShow) {
            this.mUiCallback.notifyInlineUiShown(this.mAutofillId);
        } else if (!z && this.mPreviousHasNonPinSuggestionShow) {
            this.mUiCallback.notifyInlineUiHidden(this.mAutofillId);
        }
        this.mPreviousHasNonPinSuggestionShow = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnReceiveImeRequest(@android.annotation.Nullable android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, @android.annotation.Nullable com.android.internal.inputmethod.IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback) {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed || this.mImeRequestReceived) {
                    return;
                }
                this.mImeRequestReceived = true;
                this.mImeSessionInvalidated = false;
                if (inlineSuggestionsRequest != null && iInlineSuggestionsResponseCallback != null) {
                    this.mImeRequest = inlineSuggestionsRequest;
                    this.mResponseCallback = iInlineSuggestionsResponseCallback;
                    handleOnReceiveImeStatusUpdated(this.mAutofillId, true, false);
                }
                if (this.mImeRequestConsumer != null) {
                    this.mImeRequestConsumer.accept(this.mImeRequest);
                    this.mImeRequestConsumer = null;
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnReceiveImeStatusUpdated(boolean z, boolean z2) {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                this.mImeShowing = z2;
                if (this.mImeCurrentFieldId != null) {
                    boolean z3 = this.mImeInputStarted != z;
                    boolean z4 = this.mImeInputViewStarted != z2;
                    this.mImeInputStarted = z;
                    this.mImeInputViewStarted = z2;
                    if (z3 || z4) {
                        maybeUpdateResponseToImeLocked();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnReceiveImeStatusUpdated(@android.annotation.Nullable android.view.autofill.AutofillId autofillId, boolean z, boolean z2) {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                if (autofillId != null) {
                    this.mImeCurrentFieldId = autofillId;
                }
                handleOnReceiveImeStatusUpdated(z, z2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnReceiveImeSessionInvalidated() {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                this.mImeSessionInvalidated = true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isImeShowing() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = !this.mDestroyed && this.mImeShowing;
            } finally {
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class InlineSuggestionsRequestCallbackImpl extends com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.Stub {
        private final java.lang.ref.WeakReference<com.android.server.autofill.AutofillInlineSuggestionsRequestSession> mSession;

        private InlineSuggestionsRequestCallbackImpl(com.android.server.autofill.AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession) {
            this.mSession = new java.lang.ref.WeakReference<>(autofillInlineSuggestionsRequestSession);
        }

        public void onInlineSuggestionsUnsupported() throws android.os.RemoteException {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(com.android.server.autofill.AutofillInlineSuggestionsRequestSession.TAG, "onInlineSuggestionsUnsupported() called.");
            }
            com.android.server.autofill.AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.autofill.AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1(), autofillInlineSuggestionsRequestSession, (java.lang.Object) null, (java.lang.Object) null));
            }
        }

        public void onInlineSuggestionsRequest(android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, com.android.internal.inputmethod.IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(com.android.server.autofill.AutofillInlineSuggestionsRequestSession.TAG, "onInlineSuggestionsRequest() received: " + inlineSuggestionsRequest);
            }
            com.android.server.autofill.AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.autofill.AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1(), autofillInlineSuggestionsRequestSession, inlineSuggestionsRequest, iInlineSuggestionsResponseCallback));
            }
        }

        public void onInputMethodStartInput(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(com.android.server.autofill.AutofillInlineSuggestionsRequestSession.TAG, "onInputMethodStartInput() received on " + autofillId);
            }
            com.android.server.autofill.AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: com.android.server.autofill.AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda2
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                        ((com.android.server.autofill.AutofillInlineSuggestionsRequestSession) obj).handleOnReceiveImeStatusUpdated((android.view.autofill.AutofillId) obj2, ((java.lang.Boolean) obj3).booleanValue(), ((java.lang.Boolean) obj4).booleanValue());
                    }
                }, autofillInlineSuggestionsRequestSession, autofillId, true, false));
            }
        }

        public void onInputMethodShowInputRequested(boolean z) throws android.os.RemoteException {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(com.android.server.autofill.AutofillInlineSuggestionsRequestSession.TAG, "onInputMethodShowInputRequested() received: " + z);
            }
        }

        public void onInputMethodStartInputView() {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(com.android.server.autofill.AutofillInlineSuggestionsRequestSession.TAG, "onInputMethodStartInputView() received");
            }
            com.android.server.autofill.AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.autofill.AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda3(), autofillInlineSuggestionsRequestSession, true, true));
            }
        }

        public void onInputMethodFinishInputView() {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(com.android.server.autofill.AutofillInlineSuggestionsRequestSession.TAG, "onInputMethodFinishInputView() received");
            }
            com.android.server.autofill.AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.autofill.AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda3(), autofillInlineSuggestionsRequestSession, true, false));
            }
        }

        public void onInputMethodFinishInput() throws android.os.RemoteException {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(com.android.server.autofill.AutofillInlineSuggestionsRequestSession.TAG, "onInputMethodFinishInput() received");
            }
            com.android.server.autofill.AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.autofill.AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda3(), autofillInlineSuggestionsRequestSession, false, false));
            }
        }

        public void onInlineSuggestionsSessionInvalidated() throws android.os.RemoteException {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(com.android.server.autofill.AutofillInlineSuggestionsRequestSession.TAG, "onInlineSuggestionsSessionInvalidated() called.");
            }
            com.android.server.autofill.AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.autofill.AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.autofill.AutofillInlineSuggestionsRequestSession) obj).handleOnReceiveImeSessionInvalidated();
                    }
                }, autofillInlineSuggestionsRequestSession));
            }
        }
    }

    private static boolean match(@android.annotation.Nullable android.view.autofill.AutofillId autofillId, @android.annotation.Nullable android.view.autofill.AutofillId autofillId2) {
        return (autofillId == null || autofillId2 == null || autofillId.getViewId() != autofillId2.getViewId()) ? false : true;
    }
}
