package android.service.autofill;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class InlineSuggestionRenderService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.service.autofill.InlineSuggestionRenderService";
    private static final java.lang.String TAG = "InlineSuggestionRenderService";
    private android.service.autofill.IInlineSuggestionUiCallback mCallback;
    private final android.os.Handler mMainHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
    private final android.util.LruCache<android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl, java.lang.Boolean> mActiveInlineSuggestions = new android.util.LruCache<android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl, java.lang.Boolean>(30) { // from class: android.service.autofill.InlineSuggestionRenderService.1
        @Override // android.util.LruCache
        public void entryRemoved(boolean z, android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl inlineSuggestionUiImpl, java.lang.Boolean bool, java.lang.Boolean bool2) {
            if (z) {
                android.util.Log.w(android.service.autofill.InlineSuggestionRenderService.TAG, "Hit max=30 entries in the cache. Releasing oldest one to make space.");
                inlineSuggestionUiImpl.releaseSurfaceControlViewHost();
            }
        }
    };

    private android.util.Size measuredSize(android.view.View view, int i, int i2, android.util.Size size, android.util.Size size2) {
        int makeMeasureSpec;
        int makeMeasureSpec2;
        if (i == -2 || i2 == -2) {
            if (i == -2) {
                makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(size2.getWidth(), Integer.MIN_VALUE);
            } else {
                makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i, 1073741824);
            }
            if (i2 == -2) {
                makeMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(size2.getHeight(), Integer.MIN_VALUE);
            } else {
                makeMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
            }
            view.measure(makeMeasureSpec, makeMeasureSpec2);
            return new android.util.Size(java.lang.Math.max(view.getMeasuredWidth(), size.getWidth()), java.lang.Math.max(view.getMeasuredHeight(), size.getHeight()));
        }
        return new android.util.Size(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRenderSuggestion(final android.service.autofill.IInlineSuggestionUiCallback iInlineSuggestionUiCallback, android.service.autofill.InlinePresentation inlinePresentation, int i, int i2, android.os.IBinder iBinder, int i3, int i4, int i5) {
        if (iBinder != null) {
            updateDisplay(i3);
            try {
                android.view.View onRenderSuggestion = onRenderSuggestion(inlinePresentation, i, i2);
                if (onRenderSuggestion == null) {
                    android.util.Log.w(TAG, "ExtServices failed to render the inline suggestion view.");
                    try {
                        iInlineSuggestionUiCallback.onError();
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(TAG, "Null suggestion view returned by renderer");
                    }
                    return;
                }
                this.mCallback = iInlineSuggestionUiCallback;
                final android.util.Size measuredSize = measuredSize(onRenderSuggestion, i, i2, inlinePresentation.getInlinePresentationSpec().getMinSize(), inlinePresentation.getInlinePresentationSpec().getMaxSize());
                android.util.Log.v(TAG, "width=" + i + ", height=" + i2 + ", measuredSize=" + measuredSize);
                android.service.autofill.InlineSuggestionRoot inlineSuggestionRoot = new android.service.autofill.InlineSuggestionRoot(this, iInlineSuggestionUiCallback);
                inlineSuggestionRoot.addView(onRenderSuggestion);
                android.view.WindowManager.LayoutParams layoutParams = new android.view.WindowManager.LayoutParams(measuredSize.getWidth(), measuredSize.getHeight(), 2, 0, -2);
                final android.view.SurfaceControlViewHost surfaceControlViewHost = new android.view.SurfaceControlViewHost(this, getDisplay(), new android.window.InputTransferToken(iBinder), TAG);
                surfaceControlViewHost.setView(inlineSuggestionRoot, layoutParams);
                onRenderSuggestion.setFocusable(false);
                onRenderSuggestion.setOnClickListener(new android.view.View.OnClickListener() { // from class: android.service.autofill.InlineSuggestionRenderService$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(android.view.View view) {
                        android.service.autofill.InlineSuggestionRenderService.lambda$handleRenderSuggestion$0(android.service.autofill.IInlineSuggestionUiCallback.this, view);
                    }
                });
                final android.view.View.OnLongClickListener onLongClickListener = onRenderSuggestion.getOnLongClickListener();
                onRenderSuggestion.setOnLongClickListener(new android.view.View.OnLongClickListener() { // from class: android.service.autofill.InlineSuggestionRenderService$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(android.view.View view) {
                        return android.service.autofill.InlineSuggestionRenderService.lambda$handleRenderSuggestion$1(android.view.View.OnLongClickListener.this, iInlineSuggestionUiCallback, view);
                    }
                });
                final android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl inlineSuggestionUiImpl = new android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl(surfaceControlViewHost, this.mMainHandler, i4, i5);
                this.mActiveInlineSuggestions.put(inlineSuggestionUiImpl, true);
                this.mMainHandler.post(new java.lang.Runnable() { // from class: android.service.autofill.InlineSuggestionRenderService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.service.autofill.InlineSuggestionRenderService.lambda$handleRenderSuggestion$2(android.service.autofill.IInlineSuggestionUiCallback.this, inlineSuggestionUiImpl, surfaceControlViewHost, measuredSize);
                    }
                });
                return;
            } finally {
                updateDisplay(0);
            }
        }
        try {
            iInlineSuggestionUiCallback.onError();
        } catch (android.os.RemoteException e2) {
            android.util.Log.w(TAG, "RemoteException calling onError()");
        }
    }

    static /* synthetic */ void lambda$handleRenderSuggestion$0(android.service.autofill.IInlineSuggestionUiCallback iInlineSuggestionUiCallback, android.view.View view) {
        try {
            iInlineSuggestionUiCallback.onClick();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "RemoteException calling onClick()");
        }
    }

    static /* synthetic */ boolean lambda$handleRenderSuggestion$1(android.view.View.OnLongClickListener onLongClickListener, android.service.autofill.IInlineSuggestionUiCallback iInlineSuggestionUiCallback, android.view.View view) {
        if (onLongClickListener != null) {
            onLongClickListener.onLongClick(view);
        }
        try {
            iInlineSuggestionUiCallback.onLongClick();
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "RemoteException calling onLongClick()");
            return true;
        }
    }

    static /* synthetic */ void lambda$handleRenderSuggestion$2(android.service.autofill.IInlineSuggestionUiCallback iInlineSuggestionUiCallback, android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl inlineSuggestionUiImpl, android.view.SurfaceControlViewHost surfaceControlViewHost, android.util.Size size) {
        try {
            iInlineSuggestionUiCallback.onContent(new android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiWrapper(inlineSuggestionUiImpl), surfaceControlViewHost.getSurfacePackage(), size.getWidth(), size.getHeight());
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "RemoteException calling onContent()");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleGetInlineSuggestionsRendererInfo(android.os.RemoteCallback remoteCallback) {
        remoteCallback.sendResult(onGetInlineSuggestionsRendererInfo());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDestroySuggestionViews(int i, int i2) {
        android.util.Log.v(TAG, "handleDestroySuggestionViews called for " + i + ":" + i2);
        for (android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl inlineSuggestionUiImpl : this.mActiveInlineSuggestions.snapshot().keySet()) {
            if (inlineSuggestionUiImpl.mUserId == i && inlineSuggestionUiImpl.mSessionId == i2) {
                android.util.Log.v(TAG, "Destroy " + inlineSuggestionUiImpl);
                inlineSuggestionUiImpl.releaseSurfaceControlViewHost();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class InlineSuggestionUiWrapper extends android.service.autofill.IInlineSuggestionUi.Stub {
        private final java.lang.ref.WeakReference<android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl> mUiImpl;

        InlineSuggestionUiWrapper(android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl inlineSuggestionUiImpl) {
            this.mUiImpl = new java.lang.ref.WeakReference<>(inlineSuggestionUiImpl);
        }

        @Override // android.service.autofill.IInlineSuggestionUi
        public void releaseSurfaceControlViewHost() {
            android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl inlineSuggestionUiImpl = this.mUiImpl.get();
            if (inlineSuggestionUiImpl != null) {
                inlineSuggestionUiImpl.releaseSurfaceControlViewHost();
            }
        }

        @Override // android.service.autofill.IInlineSuggestionUi
        public void getSurfacePackage(android.service.autofill.ISurfacePackageResultCallback iSurfacePackageResultCallback) {
            android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl inlineSuggestionUiImpl = this.mUiImpl.get();
            if (inlineSuggestionUiImpl != null) {
                inlineSuggestionUiImpl.getSurfacePackage(iSurfacePackageResultCallback);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class InlineSuggestionUiImpl {
        private final android.os.Handler mHandler;
        private final int mSessionId;
        private final int mUserId;
        private android.view.SurfaceControlViewHost mViewHost;

        InlineSuggestionUiImpl(android.view.SurfaceControlViewHost surfaceControlViewHost, android.os.Handler handler, int i, int i2) {
            this.mViewHost = surfaceControlViewHost;
            this.mHandler = handler;
            this.mUserId = i;
            this.mSessionId = i2;
        }

        public void releaseSurfaceControlViewHost() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.service.autofill.InlineSuggestionRenderService$InlineSuggestionUiImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl.this.lambda$releaseSurfaceControlViewHost$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$releaseSurfaceControlViewHost$0() {
            if (this.mViewHost == null) {
                return;
            }
            android.util.Log.v(android.service.autofill.InlineSuggestionRenderService.TAG, "Releasing inline suggestion view host");
            this.mViewHost.release();
            this.mViewHost = null;
            android.service.autofill.InlineSuggestionRenderService.this.mActiveInlineSuggestions.remove(this);
            android.util.Log.v(android.service.autofill.InlineSuggestionRenderService.TAG, "Removed the inline suggestion from the cache, current size=" + android.service.autofill.InlineSuggestionRenderService.this.mActiveInlineSuggestions.size());
        }

        public void getSurfacePackage(final android.service.autofill.ISurfacePackageResultCallback iSurfacePackageResultCallback) {
            android.util.Log.d(android.service.autofill.InlineSuggestionRenderService.TAG, "getSurfacePackage");
            this.mHandler.post(new java.lang.Runnable() { // from class: android.service.autofill.InlineSuggestionRenderService$InlineSuggestionUiImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl.this.lambda$getSurfacePackage$1(iSurfacePackageResultCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getSurfacePackage$1(android.service.autofill.ISurfacePackageResultCallback iSurfacePackageResultCallback) {
            try {
                iSurfacePackageResultCallback.onResult(this.mViewHost == null ? null : this.mViewHost.getSurfacePackage());
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.service.autofill.InlineSuggestionRenderService.TAG, "RemoteException calling onSurfacePackage");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public final void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.println("mActiveInlineSuggestions: " + this.mActiveInlineSuggestions.size());
        for (android.service.autofill.InlineSuggestionRenderService.InlineSuggestionUiImpl inlineSuggestionUiImpl : this.mActiveInlineSuggestions.snapshot().keySet()) {
            printWriter.printf("ui: [%s] - [%d]  [%d]\n", inlineSuggestionUiImpl, java.lang.Integer.valueOf(inlineSuggestionUiImpl.mUserId), java.lang.Integer.valueOf(inlineSuggestionUiImpl.mSessionId));
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        android.os.BaseBundle.setShouldDefuse(true);
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new android.service.autofill.InlineSuggestionRenderService.AnonymousClass2().asBinder();
        }
        android.util.Log.w(TAG, "Tried to bind to wrong intent (should be android.service.autofill.InlineSuggestionRenderService: " + intent);
        return null;
    }

    /* renamed from: android.service.autofill.InlineSuggestionRenderService$2, reason: invalid class name */
    class AnonymousClass2 extends android.service.autofill.IInlineSuggestionRenderService.Stub {
        AnonymousClass2() {
        }

        @Override // android.service.autofill.IInlineSuggestionRenderService
        public void renderSuggestion(android.service.autofill.IInlineSuggestionUiCallback iInlineSuggestionUiCallback, android.service.autofill.InlinePresentation inlinePresentation, int i, int i2, android.os.IBinder iBinder, int i3, int i4, int i5) {
            android.service.autofill.InlineSuggestionRenderService.this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.NonaConsumer() { // from class: android.service.autofill.InlineSuggestionRenderService$2$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.NonaConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9) {
                    ((android.service.autofill.InlineSuggestionRenderService) obj).handleRenderSuggestion((android.service.autofill.IInlineSuggestionUiCallback) obj2, (android.service.autofill.InlinePresentation) obj3, ((java.lang.Integer) obj4).intValue(), ((java.lang.Integer) obj5).intValue(), (android.os.IBinder) obj6, ((java.lang.Integer) obj7).intValue(), ((java.lang.Integer) obj8).intValue(), ((java.lang.Integer) obj9).intValue());
                }
            }, android.service.autofill.InlineSuggestionRenderService.this, iInlineSuggestionUiCallback, inlinePresentation, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), iBinder, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5)));
        }

        @Override // android.service.autofill.IInlineSuggestionRenderService
        public void getInlineSuggestionsRendererInfo(android.os.RemoteCallback remoteCallback) {
            android.service.autofill.InlineSuggestionRenderService.this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.autofill.InlineSuggestionRenderService$2$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.autofill.InlineSuggestionRenderService) obj).handleGetInlineSuggestionsRendererInfo((android.os.RemoteCallback) obj2);
                }
            }, android.service.autofill.InlineSuggestionRenderService.this, remoteCallback));
        }

        @Override // android.service.autofill.IInlineSuggestionRenderService
        public void destroySuggestionViews(int i, int i2) {
            android.service.autofill.InlineSuggestionRenderService.this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.autofill.InlineSuggestionRenderService$2$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.autofill.InlineSuggestionRenderService) obj).handleDestroySuggestionViews(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue());
                }
            }, android.service.autofill.InlineSuggestionRenderService.this, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
        }
    }

    public final void startIntentSender(android.content.IntentSender intentSender) {
        if (this.mCallback == null) {
            return;
        }
        try {
            this.mCallback.onStartIntentSender(intentSender);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public android.os.Bundle onGetInlineSuggestionsRendererInfo() {
        return android.os.Bundle.EMPTY;
    }

    public android.view.View onRenderSuggestion(android.service.autofill.InlinePresentation inlinePresentation, int i, int i2) {
        android.util.Log.e(TAG, "service implementation (" + getClass() + " does not implement onRenderSuggestion()");
        return null;
    }
}
