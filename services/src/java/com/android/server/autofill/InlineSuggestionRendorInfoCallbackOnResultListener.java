package com.android.server.autofill;

/* loaded from: classes.dex */
final class InlineSuggestionRendorInfoCallbackOnResultListener implements android.os.RemoteCallback.OnResultListener {
    private static final java.lang.String TAG = "InlineSuggestionRendorInfoCallbackOnResultListener";
    private final android.view.autofill.AutofillId mFocusedId;
    private final java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsRequest> mInlineSuggestionsRequestConsumer;
    private final int mRequestIdCopy;
    private final java.lang.ref.WeakReference<com.android.server.autofill.Session> mSessionWeakReference;

    InlineSuggestionRendorInfoCallbackOnResultListener(java.lang.ref.WeakReference<com.android.server.autofill.Session> weakReference, int i, java.util.function.Consumer<android.view.inputmethod.InlineSuggestionsRequest> consumer, android.view.autofill.AutofillId autofillId) {
        this.mRequestIdCopy = i;
        this.mInlineSuggestionsRequestConsumer = consumer;
        this.mSessionWeakReference = weakReference;
        this.mFocusedId = autofillId;
    }

    public void onResult(@android.annotation.Nullable android.os.Bundle bundle) {
        com.android.server.autofill.Session session = this.mSessionWeakReference.get();
        if (session == null) {
            android.util.Slog.wtf(TAG, "Session is null before trying to call onResult");
            return;
        }
        synchronized (session.mLock) {
            try {
                if (session.mDestroyed) {
                    android.util.Slog.wtf(TAG, "Session is destroyed before trying to call onResult");
                } else {
                    session.mInlineSessionController.onCreateInlineSuggestionsRequestLocked(this.mFocusedId, session.inlineSuggestionsRequestCacheDecorator(this.mInlineSuggestionsRequestConsumer, this.mRequestIdCopy), bundle);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
