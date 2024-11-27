package com.android.server.autofill.ui;

/* loaded from: classes.dex */
final class InlineContentProviderImpl extends com.android.internal.view.inline.IInlineContentProvider.Stub {
    private static final java.lang.String TAG = com.android.server.autofill.ui.InlineContentProviderImpl.class.getSimpleName();
    private final android.os.Handler mHandler = com.android.server.FgThread.getHandler();
    private boolean mProvideContentCalled = false;

    @android.annotation.Nullable
    private com.android.server.autofill.ui.RemoteInlineSuggestionUi mRemoteInlineSuggestionUi;

    @android.annotation.NonNull
    private final com.android.server.autofill.ui.RemoteInlineSuggestionViewConnector mRemoteInlineSuggestionViewConnector;

    InlineContentProviderImpl(@android.annotation.NonNull com.android.server.autofill.ui.RemoteInlineSuggestionViewConnector remoteInlineSuggestionViewConnector, @android.annotation.Nullable com.android.server.autofill.ui.RemoteInlineSuggestionUi remoteInlineSuggestionUi) {
        this.mRemoteInlineSuggestionViewConnector = remoteInlineSuggestionViewConnector;
        this.mRemoteInlineSuggestionUi = remoteInlineSuggestionUi;
    }

    @android.annotation.NonNull
    public com.android.server.autofill.ui.InlineContentProviderImpl copy() {
        return new com.android.server.autofill.ui.InlineContentProviderImpl(this.mRemoteInlineSuggestionViewConnector, this.mRemoteInlineSuggestionUi);
    }

    public void provideContent(final int i, final int i2, final com.android.internal.view.inline.IInlineContentCallback iInlineContentCallback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.InlineContentProviderImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.InlineContentProviderImpl.this.lambda$provideContent$0(i, i2, iInlineContentCallback);
            }
        });
    }

    public void requestSurfacePackage() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.InlineContentProviderImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.InlineContentProviderImpl.this.handleGetSurfacePackage();
            }
        });
    }

    public void onSurfacePackageReleased() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.InlineContentProviderImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.InlineContentProviderImpl.this.handleOnSurfacePackageReleased();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleProvideContent, reason: merged with bridge method [inline-methods] */
    public void lambda$provideContent$0(int i, int i2, com.android.internal.view.inline.IInlineContentCallback iInlineContentCallback) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "handleProvideContent");
        }
        if (this.mProvideContentCalled) {
            return;
        }
        this.mProvideContentCalled = true;
        if (this.mRemoteInlineSuggestionUi == null || !this.mRemoteInlineSuggestionUi.match(i, i2)) {
            this.mRemoteInlineSuggestionUi = new com.android.server.autofill.ui.RemoteInlineSuggestionUi(this.mRemoteInlineSuggestionViewConnector, i, i2, this.mHandler);
        }
        this.mRemoteInlineSuggestionUi.setInlineContentCallback(iInlineContentCallback);
        this.mRemoteInlineSuggestionUi.requestSurfacePackage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleGetSurfacePackage() {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "handleGetSurfacePackage");
        }
        if (!this.mProvideContentCalled || this.mRemoteInlineSuggestionUi == null) {
            return;
        }
        this.mRemoteInlineSuggestionUi.requestSurfacePackage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnSurfacePackageReleased() {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "handleOnSurfacePackageReleased");
        }
        if (!this.mProvideContentCalled || this.mRemoteInlineSuggestionUi == null) {
            return;
        }
        this.mRemoteInlineSuggestionUi.surfacePackageReleased();
    }
}
