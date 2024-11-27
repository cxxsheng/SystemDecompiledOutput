package com.android.server.autofill;

/* loaded from: classes.dex */
final class AutofillInlineSessionController {

    @android.annotation.NonNull
    private final android.content.ComponentName mComponentName;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.autofill.ui.InlineFillUi mInlineFillUi;

    @android.annotation.NonNull
    private final com.android.server.inputmethod.InputMethodManagerInternal mInputMethodManagerInternal;

    @android.annotation.NonNull
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.autofill.AutofillInlineSuggestionsRequestSession mSession;

    @android.annotation.NonNull
    private final com.android.server.autofill.ui.InlineFillUi.InlineUiEventCallback mUiCallback;
    private final int mUserId;

    AutofillInlineSessionController(com.android.server.inputmethod.InputMethodManagerInternal inputMethodManagerInternal, int i, android.content.ComponentName componentName, android.os.Handler handler, java.lang.Object obj, com.android.server.autofill.ui.InlineFillUi.InlineUiEventCallback inlineUiEventCallback) {
        this.mInputMethodManagerInternal = inputMethodManagerInternal;
        this.mUserId = i;
        this.mComponentName = componentName;
        this.mHandler = handler;
        this.mLock = obj;
        this.mUiCallback = inlineUiEventCallback;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onCreateInlineSuggestionsRequestLocked(@android.annotation.NonNull android.view.autofill.AutofillId autofillId, @android.annotation.NonNull java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsRequest> consumer, @android.annotation.NonNull android.os.Bundle bundle) {
        if (this.mSession != null) {
            this.mSession.destroySessionLocked();
        }
        this.mInlineFillUi = null;
        this.mSession = new com.android.server.autofill.AutofillInlineSuggestionsRequestSession(this.mInputMethodManagerInternal, this.mUserId, this.mComponentName, this.mHandler, this.mLock, autofillId, consumer, bundle, this.mUiCallback);
        this.mSession.onCreateInlineSuggestionsRequestLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void destroyLocked(@android.annotation.NonNull android.view.autofill.AutofillId autofillId) {
        if (this.mSession != null) {
            this.mSession.onInlineSuggestionsResponseLocked(com.android.server.autofill.ui.InlineFillUi.emptyUi(autofillId));
            this.mSession.destroySessionLocked();
            this.mSession = null;
        }
        this.mInlineFillUi = null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    java.util.Optional<android.view.inputmethod.InlineSuggestionsRequest> getInlineSuggestionsRequestLocked() {
        if (this.mSession != null) {
            return this.mSession.getInlineSuggestionsRequestLocked();
        }
        return java.util.Optional.empty();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean hideInlineSuggestionsUiLocked(@android.annotation.NonNull android.view.autofill.AutofillId autofillId) {
        if (this.mSession != null) {
            return this.mSession.onInlineSuggestionsResponseLocked(com.android.server.autofill.ui.InlineFillUi.emptyUi(autofillId));
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void disableFilterMatching(@android.annotation.NonNull android.view.autofill.AutofillId autofillId) {
        if (this.mInlineFillUi != null && this.mInlineFillUi.getAutofillId().equals(autofillId)) {
            this.mInlineFillUi.disableFilterMatching();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void resetInlineFillUiLocked() {
        this.mInlineFillUi = null;
        if (this.mSession != null) {
            this.mSession.resetInlineFillUiLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean filterInlineFillUiLocked(@android.annotation.NonNull android.view.autofill.AutofillId autofillId, @android.annotation.Nullable java.lang.String str) {
        if (this.mInlineFillUi != null && this.mInlineFillUi.getAutofillId().equals(autofillId)) {
            this.mInlineFillUi.setFilterText(str);
            return requestImeToShowInlineSuggestionsLocked();
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean setInlineFillUiLocked(@android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi inlineFillUi) {
        this.mInlineFillUi = inlineFillUi;
        return requestImeToShowInlineSuggestionsLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean requestImeToShowInlineSuggestionsLocked() {
        if (this.mSession != null && this.mInlineFillUi != null) {
            return this.mSession.onInlineSuggestionsResponseLocked(this.mInlineFillUi);
        }
        return false;
    }

    boolean isImeShowing() {
        if (this.mSession != null) {
            return this.mSession.isImeShowing();
        }
        return false;
    }
}
