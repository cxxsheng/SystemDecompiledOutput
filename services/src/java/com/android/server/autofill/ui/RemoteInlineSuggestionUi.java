package com.android.server.autofill.ui;

/* loaded from: classes.dex */
final class RemoteInlineSuggestionUi {
    private static final long RELEASE_REMOTE_VIEW_HOST_DELAY_MS = 200;
    private static final java.lang.String TAG = com.android.server.autofill.ui.RemoteInlineSuggestionUi.class.getSimpleName();
    private int mActualHeight;
    private int mActualWidth;

    @android.annotation.Nullable
    private java.lang.Runnable mDelayedReleaseViewRunnable;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;
    private final int mHeight;

    @android.annotation.Nullable
    private com.android.internal.view.inline.IInlineContentCallback mInlineContentCallback;

    @android.annotation.Nullable
    private android.service.autofill.IInlineSuggestionUi mInlineSuggestionUi;

    @android.annotation.NonNull
    private final com.android.server.autofill.ui.RemoteInlineSuggestionViewConnector mRemoteInlineSuggestionViewConnector;
    private final int mWidth;
    private int mRefCount = 0;
    private boolean mWaitingForUiCreation = false;

    @android.annotation.NonNull
    private final com.android.server.autofill.ui.RemoteInlineSuggestionUi.InlineSuggestionUiCallbackImpl mInlineSuggestionUiCallback = new com.android.server.autofill.ui.RemoteInlineSuggestionUi.InlineSuggestionUiCallbackImpl();

    RemoteInlineSuggestionUi(@android.annotation.NonNull com.android.server.autofill.ui.RemoteInlineSuggestionViewConnector remoteInlineSuggestionViewConnector, int i, int i2, android.os.Handler handler) {
        this.mHandler = handler;
        this.mRemoteInlineSuggestionViewConnector = remoteInlineSuggestionViewConnector;
        this.mWidth = i;
        this.mHeight = i2;
    }

    void setInlineContentCallback(@android.annotation.NonNull final com.android.internal.view.inline.IInlineContentCallback iInlineContentCallback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionUi$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.lambda$setInlineContentCallback$0(iInlineContentCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setInlineContentCallback$0(com.android.internal.view.inline.IInlineContentCallback iInlineContentCallback) {
        this.mInlineContentCallback = iInlineContentCallback;
    }

    void requestSurfacePackage() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionUi$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.handleRequestSurfacePackage();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$surfacePackageReleased$1() {
        handleUpdateRefCount(-1);
    }

    void surfacePackageReleased() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionUi$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.lambda$surfacePackageReleased$1();
            }
        });
    }

    boolean match(int i, int i2) {
        return this.mWidth == i && this.mHeight == i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRequestSurfacePackage() {
        cancelPendingReleaseViewRequest();
        if (this.mInlineSuggestionUi == null) {
            if (this.mWaitingForUiCreation) {
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(TAG, "Inline suggestion ui is not ready");
                    return;
                }
                return;
            } else {
                this.mRemoteInlineSuggestionViewConnector.renderSuggestion(this.mWidth, this.mHeight, this.mInlineSuggestionUiCallback);
                this.mWaitingForUiCreation = true;
                return;
            }
        }
        try {
            this.mInlineSuggestionUi.getSurfacePackage(new com.android.server.autofill.ui.RemoteInlineSuggestionUi.AnonymousClass1());
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "RemoteException calling getSurfacePackage.");
        }
    }

    /* renamed from: com.android.server.autofill.ui.RemoteInlineSuggestionUi$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.autofill.ISurfacePackageResultCallback.Stub {
        AnonymousClass1() {
        }

        public void onResult(final android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
            com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionUi$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.autofill.ui.RemoteInlineSuggestionUi.AnonymousClass1.this.lambda$onResult$0(surfacePackage);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(com.android.server.autofill.ui.RemoteInlineSuggestionUi.TAG, "Sending refreshed SurfacePackage to IME");
            }
            try {
                com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.mInlineContentCallback.onContent(surfacePackage, com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.mActualWidth, com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.mActualHeight);
                com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.handleUpdateRefCount(1);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.autofill.ui.RemoteInlineSuggestionUi.TAG, "RemoteException calling onContent");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUpdateRefCount(int i) {
        cancelPendingReleaseViewRequest();
        this.mRefCount += i;
        if (this.mRefCount <= 0) {
            this.mDelayedReleaseViewRunnable = new java.lang.Runnable() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionUi$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.lambda$handleUpdateRefCount$2();
                }
            };
            this.mHandler.postDelayed(this.mDelayedReleaseViewRunnable, RELEASE_REMOTE_VIEW_HOST_DELAY_MS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleUpdateRefCount$2() {
        if (this.mInlineSuggestionUi != null) {
            try {
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "releasing the host");
                }
                this.mInlineSuggestionUi.releaseSurfaceControlViewHost();
                this.mInlineSuggestionUi = null;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "RemoteException calling releaseSurfaceControlViewHost");
            }
        }
        this.mDelayedReleaseViewRunnable = null;
    }

    private void cancelPendingReleaseViewRequest() {
        if (this.mDelayedReleaseViewRunnable != null) {
            this.mHandler.removeCallbacks(this.mDelayedReleaseViewRunnable);
            this.mDelayedReleaseViewRunnable = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInlineSuggestionUiReady(android.service.autofill.IInlineSuggestionUi iInlineSuggestionUi, android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) {
        this.mInlineSuggestionUi = iInlineSuggestionUi;
        this.mRefCount = 0;
        this.mWaitingForUiCreation = false;
        this.mActualWidth = i;
        this.mActualHeight = i2;
        if (this.mInlineContentCallback != null) {
            try {
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "Sending new UI content to IME");
                }
                handleUpdateRefCount(1);
                this.mInlineContentCallback.onContent(surfacePackage, this.mActualWidth, this.mActualHeight);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "RemoteException calling onContent");
            }
        }
        if (surfacePackage != null) {
            surfacePackage.release();
        }
        this.mRemoteInlineSuggestionViewConnector.onRender();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnClick() {
        this.mRemoteInlineSuggestionViewConnector.onClick();
        if (this.mInlineContentCallback != null) {
            try {
                this.mInlineContentCallback.onClick();
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "RemoteException calling onClick");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnLongClick() {
        if (this.mInlineContentCallback != null) {
            try {
                this.mInlineContentCallback.onLongClick();
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "RemoteException calling onLongClick");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnError() {
        this.mRemoteInlineSuggestionViewConnector.onError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnTransferTouchFocusToImeWindow(android.os.IBinder iBinder, int i) {
        this.mRemoteInlineSuggestionViewConnector.onTransferTouchFocusToImeWindow(iBinder, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnStartIntentSender(android.content.IntentSender intentSender) {
        this.mRemoteInlineSuggestionViewConnector.onStartIntentSender(intentSender);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class InlineSuggestionUiCallbackImpl extends android.service.autofill.IInlineSuggestionUiCallback.Stub {
        private InlineSuggestionUiCallbackImpl() {
        }

        public void onClick() {
            android.os.Handler handler = com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.mHandler;
            final com.android.server.autofill.ui.RemoteInlineSuggestionUi remoteInlineSuggestionUi = com.android.server.autofill.ui.RemoteInlineSuggestionUi.this;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionUi$InlineSuggestionUiCallbackImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.handleOnClick();
                }
            });
        }

        public void onLongClick() {
            android.os.Handler handler = com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.mHandler;
            final com.android.server.autofill.ui.RemoteInlineSuggestionUi remoteInlineSuggestionUi = com.android.server.autofill.ui.RemoteInlineSuggestionUi.this;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionUi$InlineSuggestionUiCallbackImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.handleOnLongClick();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onContent$0(android.service.autofill.IInlineSuggestionUi iInlineSuggestionUi, android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) {
            com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.handleInlineSuggestionUiReady(iInlineSuggestionUi, surfacePackage, i, i2);
        }

        public void onContent(final android.service.autofill.IInlineSuggestionUi iInlineSuggestionUi, final android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, final int i, final int i2) {
            com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionUi$InlineSuggestionUiCallbackImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.autofill.ui.RemoteInlineSuggestionUi.InlineSuggestionUiCallbackImpl.this.lambda$onContent$0(iInlineSuggestionUi, surfacePackage, i, i2);
                }
            });
        }

        public void onError() {
            android.os.Handler handler = com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.mHandler;
            final com.android.server.autofill.ui.RemoteInlineSuggestionUi remoteInlineSuggestionUi = com.android.server.autofill.ui.RemoteInlineSuggestionUi.this;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionUi$InlineSuggestionUiCallbackImpl$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.handleOnError();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTransferTouchFocusToImeWindow$1(android.os.IBinder iBinder, int i) {
            com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.handleOnTransferTouchFocusToImeWindow(iBinder, i);
        }

        public void onTransferTouchFocusToImeWindow(final android.os.IBinder iBinder, final int i) {
            com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionUi$InlineSuggestionUiCallbackImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.autofill.ui.RemoteInlineSuggestionUi.InlineSuggestionUiCallbackImpl.this.lambda$onTransferTouchFocusToImeWindow$1(iBinder, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStartIntentSender$2(android.content.IntentSender intentSender) {
            com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.handleOnStartIntentSender(intentSender);
        }

        public void onStartIntentSender(final android.content.IntentSender intentSender) {
            com.android.server.autofill.ui.RemoteInlineSuggestionUi.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.RemoteInlineSuggestionUi$InlineSuggestionUiCallbackImpl$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.autofill.ui.RemoteInlineSuggestionUi.InlineSuggestionUiCallbackImpl.this.lambda$onStartIntentSender$2(intentSender);
                }
            });
        }
    }
}
