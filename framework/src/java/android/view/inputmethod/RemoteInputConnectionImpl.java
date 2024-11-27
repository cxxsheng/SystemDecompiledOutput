package android.view.inputmethod;

/* loaded from: classes4.dex */
final class RemoteInputConnectionImpl extends com.android.internal.inputmethod.IRemoteInputConnection.Stub {
    private static final boolean DEBUG = false;
    private static final int MAX_END_BATCH_EDIT_RETRY = 16;
    private static final java.lang.String TAG = "RemoteInputConnectionImpl";
    private android.os.CancellationSignalBeamer.Receiver mBeamer;
    private final android.os.Handler mH;
    private final java.util.concurrent.atomic.AtomicReference<android.view.inputmethod.InputConnection> mInputConnectionRef;
    private final android.os.Looper mLooper;
    private final android.view.inputmethod.InputMethodManager mParentInputMethodManager;
    private final java.lang.ref.WeakReference<android.view.View> mServedView;
    private final java.util.concurrent.atomic.AtomicBoolean mDeactivateRequested = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final java.util.concurrent.atomic.AtomicInteger mCurrentSessionId = new java.util.concurrent.atomic.AtomicInteger(0);
    private final java.util.concurrent.atomic.AtomicBoolean mHasPendingInvalidation = new java.util.concurrent.atomic.AtomicBoolean();
    private final java.util.concurrent.atomic.AtomicBoolean mIsCursorAnchorInfoMonitoring = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final java.util.concurrent.atomic.AtomicBoolean mHasPendingImmediateCursorAnchorInfoUpdate = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final com.android.internal.inputmethod.IRemoteAccessibilityInputConnection mAccessibilityInputConnection = new android.view.inputmethod.RemoteInputConnectionImpl.AnonymousClass1();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface Dispatching {
        boolean cancellable();
    }

    private static final class KnownAlwaysTrueEndBatchEditCache {
        private static volatile java.lang.Class<?>[] sArray;
        private static volatile java.lang.Class<?> sElement;

        private KnownAlwaysTrueEndBatchEditCache() {
        }

        static boolean contains(java.lang.Class<? extends android.view.inputmethod.InputConnection> cls) {
            if (cls == sElement) {
                return true;
            }
            java.lang.Class<?>[] clsArr = sArray;
            if (clsArr == null) {
                return false;
            }
            for (java.lang.Class<?> cls2 : clsArr) {
                if (cls2 == cls) {
                    return true;
                }
            }
            return false;
        }

        static void add(java.lang.Class<? extends android.view.inputmethod.InputConnection> cls) {
            if (sElement == null) {
                sElement = cls;
                return;
            }
            java.lang.Class<?>[] clsArr = sArray;
            int length = clsArr != null ? clsArr.length : 0;
            java.lang.Class<?>[] clsArr2 = new java.lang.Class[length + 1];
            for (int i = 0; i < length; i++) {
                clsArr2[i] = clsArr[i];
            }
            clsArr2[length] = cls;
            sArray = clsArr2;
        }
    }

    RemoteInputConnectionImpl(android.os.Looper looper, android.view.inputmethod.InputConnection inputConnection, android.view.inputmethod.InputMethodManager inputMethodManager, android.view.View view) {
        this.mInputConnectionRef = new java.util.concurrent.atomic.AtomicReference<>(inputConnection);
        this.mLooper = looper;
        this.mH = new android.os.Handler(this.mLooper);
        this.mParentInputMethodManager = inputMethodManager;
        this.mServedView = new java.lang.ref.WeakReference<>(view);
    }

    public android.view.inputmethod.InputConnection getInputConnection() {
        return this.mInputConnectionRef.get();
    }

    public boolean hasPendingInvalidation() {
        return this.mHasPendingInvalidation.get();
    }

    private boolean isFinished() {
        return this.mInputConnectionRef.get() == null;
    }

    private android.view.View getServedView() {
        return this.mServedView.get();
    }

    public boolean isAssociatedWith(android.view.View view) {
        if (view == null) {
            return false;
        }
        return this.mServedView.refersTo(view);
    }

    public boolean resetHasPendingImmediateCursorAnchorInfoUpdate() {
        return this.mHasPendingImmediateCursorAnchorInfoUpdate.getAndSet(false);
    }

    public boolean isCursorAnchorInfoMonitoring() {
        return this.mIsCursorAnchorInfoMonitoring.get();
    }

    public void scheduleInvalidateInput() {
        if (this.mHasPendingInvalidation.compareAndSet(false, true)) {
            final int incrementAndGet = this.mCurrentSessionId.incrementAndGet();
            this.mH.post(new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda36
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$scheduleInvalidateInput$0(incrementAndGet);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleInvalidateInput$0(int i) {
        android.view.inputmethod.TextSnapshot takeSnapshot;
        try {
            if (isFinished()) {
                return;
            }
            android.view.inputmethod.InputConnection inputConnection = getInputConnection();
            if (inputConnection == null) {
                return;
            }
            android.view.View servedView = getServedView();
            if (servedView == null) {
                return;
            }
            java.lang.Class<?> cls = inputConnection.getClass();
            boolean contains = android.view.inputmethod.RemoteInputConnectionImpl.KnownAlwaysTrueEndBatchEditCache.contains(cls);
            if (!contains) {
                boolean beginBatchEdit = inputConnection.beginBatchEdit();
                inputConnection.finishComposingText();
                if (beginBatchEdit) {
                    int i2 = 0;
                    while (true) {
                        if (!inputConnection.endBatchEdit()) {
                            break;
                        }
                        i2++;
                        if (i2 > 16) {
                            android.util.Log.e(TAG, cls.getTypeName() + "#endBatchEdit() still returns true even after retrying 16 times.  Falling back to InputMethodManager#restartInput(View)");
                            android.view.inputmethod.RemoteInputConnectionImpl.KnownAlwaysTrueEndBatchEditCache.add(cls);
                            contains = true;
                            break;
                        }
                    }
                }
            }
            if (contains || (takeSnapshot = inputConnection.takeSnapshot()) == null || !this.mParentInputMethodManager.doInvalidateInput(this, takeSnapshot, i)) {
                this.mParentInputMethodManager.restartInput(servedView);
            }
        } finally {
            this.mHasPendingInvalidation.set(false);
        }
    }

    public void deactivate() {
        if (this.mDeactivateRequested.getAndSet(true)) {
            return;
        }
        dispatch(new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$deactivate$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deactivate$1() {
        android.os.Trace.traceBegin(4L, "InputConnection#closeConnection");
        try {
            android.view.inputmethod.InputConnection inputConnection = getInputConnection();
            if (inputConnection == null) {
                return;
            }
            try {
                inputConnection.closeConnection();
            } catch (java.lang.AbstractMethodError e) {
            }
            this.mInputConnectionRef.set(null);
            android.os.Trace.traceEnd(4L);
            final android.view.View view = this.mServedView.get();
            if (view != null) {
                android.os.Handler handler = view.getHandler();
                if (handler != null) {
                    if (handler.getLooper().isCurrentThread()) {
                        view.onInputConnectionClosedInternal();
                    } else {
                        java.util.Objects.requireNonNull(view);
                        handler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda37
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.view.View.this.onInputConnectionClosedInternal();
                            }
                        });
                    }
                }
                android.view.ViewRootImpl viewRootImpl = view.getViewRootImpl();
                if (viewRootImpl != null) {
                    viewRootImpl.getHandwritingInitiator().onInputConnectionClosed(view);
                }
            }
        } finally {
            this.mInputConnectionRef.set(null);
            android.os.Trace.traceEnd(4L);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void cancelCancellationSignal(final android.os.IBinder iBinder) {
        if (this.mBeamer == null) {
            return;
        }
        dispatch(new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda45
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$cancelCancellationSignal$2(iBinder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelCancellationSignal$2(android.os.IBinder iBinder) {
        this.mBeamer.cancel(iBinder);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void forgetCancellationSignal(final android.os.IBinder iBinder) {
        if (this.mBeamer == null) {
            return;
        }
        dispatch(new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda38
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$forgetCancellationSignal$3(iBinder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forgetCancellationSignal$3(android.os.IBinder iBinder) {
        this.mBeamer.forget(iBinder);
    }

    public java.lang.String toString() {
        return "RemoteInputConnectionImpl{connection=" + getInputConnection() + " mDeactivateRequested=" + this.mDeactivateRequested.get() + " mServedView=" + this.mServedView.get() + "}";
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        android.view.inputmethod.InputConnection inputConnection = this.mInputConnectionRef.get();
        if ((inputConnection instanceof android.view.inputmethod.DumpableInputConnection) && this.mLooper.isCurrentThread()) {
            ((android.view.inputmethod.DumpableInputConnection) inputConnection).dumpDebug(protoOutputStream, j);
        }
    }

    public void dispatchReportFullscreenMode(final boolean z) {
        dispatch(new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$dispatchReportFullscreenMode$4(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchReportFullscreenMode$4(boolean z) {
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            return;
        }
        inputConnection.reportFullscreenMode(z);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getTextAfterCursor(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, com.android.internal.infra.AndroidFuture androidFuture) {
        dispatchWithTracing("getTextAfterCursor", androidFuture, new java.util.function.Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda23
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.CharSequence lambda$getTextAfterCursor$5;
                lambda$getTextAfterCursor$5 = android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$getTextAfterCursor$5(inputConnectionCommandHeader, i, i2);
                return lambda$getTextAfterCursor$5;
            }
        }, useImeTracing() ? new java.util.function.Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda24
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                byte[] buildGetTextAfterCursorProto;
                buildGetTextAfterCursorProto = com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetTextAfterCursorProto(i, i2, (java.lang.CharSequence) obj);
                return buildGetTextAfterCursorProto;
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.CharSequence lambda$getTextAfterCursor$5(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return null;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "getTextAfterCursor on inactive InputConnection");
            return null;
        }
        if (i < 0) {
            android.util.Log.i(TAG, "Returning null to getTextAfterCursor due to an invalid length=" + i);
            return null;
        }
        return inputConnection.getTextAfterCursor(i, i2);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getTextBeforeCursor(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, com.android.internal.infra.AndroidFuture androidFuture) {
        dispatchWithTracing("getTextBeforeCursor", androidFuture, new java.util.function.Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda33
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.CharSequence lambda$getTextBeforeCursor$7;
                lambda$getTextBeforeCursor$7 = android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$getTextBeforeCursor$7(inputConnectionCommandHeader, i, i2);
                return lambda$getTextBeforeCursor$7;
            }
        }, useImeTracing() ? new java.util.function.Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda34
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                byte[] buildGetTextBeforeCursorProto;
                buildGetTextBeforeCursorProto = com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetTextBeforeCursorProto(i, i2, (java.lang.CharSequence) obj);
                return buildGetTextBeforeCursorProto;
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.CharSequence lambda$getTextBeforeCursor$7(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return null;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "getTextBeforeCursor on inactive InputConnection");
            return null;
        }
        if (i < 0) {
            android.util.Log.i(TAG, "Returning null to getTextBeforeCursor due to an invalid length=" + i);
            return null;
        }
        return inputConnection.getTextBeforeCursor(i, i2);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getSelectedText(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, com.android.internal.infra.AndroidFuture androidFuture) {
        dispatchWithTracing("getSelectedText", androidFuture, new java.util.function.Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda30
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.CharSequence lambda$getSelectedText$9;
                lambda$getSelectedText$9 = android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$getSelectedText$9(inputConnectionCommandHeader, i);
                return lambda$getSelectedText$9;
            }
        }, useImeTracing() ? new java.util.function.Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda31
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                byte[] buildGetSelectedTextProto;
                buildGetSelectedTextProto = com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetSelectedTextProto(i, (java.lang.CharSequence) obj);
                return buildGetSelectedTextProto;
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.CharSequence lambda$getSelectedText$9(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return null;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "getSelectedText on inactive InputConnection");
            return null;
        }
        try {
            return inputConnection.getSelectedText(i);
        } catch (java.lang.AbstractMethodError e) {
            return null;
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getSurroundingText(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, final int i3, com.android.internal.infra.AndroidFuture androidFuture) {
        dispatchWithTracing("getSurroundingText", androidFuture, new java.util.function.Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.view.inputmethod.SurroundingText lambda$getSurroundingText$11;
                lambda$getSurroundingText$11 = android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$getSurroundingText$11(inputConnectionCommandHeader, i, i2, i3);
                return lambda$getSurroundingText$11;
            }
        }, useImeTracing() ? new java.util.function.Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                byte[] buildGetSurroundingTextProto;
                buildGetSurroundingTextProto = com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetSurroundingTextProto(i, i2, i3, (android.view.inputmethod.SurroundingText) obj);
                return buildGetSurroundingTextProto;
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.view.inputmethod.SurroundingText lambda$getSurroundingText$11(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return null;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "getSurroundingText on inactive InputConnection");
            return null;
        }
        if (i < 0) {
            android.util.Log.i(TAG, "Returning null to getSurroundingText due to an invalid beforeLength=" + i);
            return null;
        }
        if (i2 < 0) {
            android.util.Log.i(TAG, "Returning null to getSurroundingText due to an invalid afterLength=" + i2);
            return null;
        }
        return inputConnection.getSurroundingText(i, i2, i3);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getCursorCapsMode(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, com.android.internal.infra.AndroidFuture androidFuture) {
        dispatchWithTracing("getCursorCapsMode", androidFuture, new java.util.function.Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Integer lambda$getCursorCapsMode$13;
                lambda$getCursorCapsMode$13 = android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$getCursorCapsMode$13(inputConnectionCommandHeader, i);
                return lambda$getCursorCapsMode$13;
            }
        }, useImeTracing() ? new java.util.function.Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                byte[] buildGetCursorCapsModeProto;
                buildGetCursorCapsModeProto = com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetCursorCapsModeProto(i, ((java.lang.Integer) obj).intValue());
                return buildGetCursorCapsModeProto;
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$getCursorCapsMode$13(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return 0;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "getCursorCapsMode on inactive InputConnection");
            return 0;
        }
        return java.lang.Integer.valueOf(inputConnection.getCursorCapsMode(i));
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getExtractedText(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final android.view.inputmethod.ExtractedTextRequest extractedTextRequest, final int i, com.android.internal.infra.AndroidFuture androidFuture) {
        dispatchWithTracing("getExtractedText", androidFuture, new java.util.function.Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda21
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.view.inputmethod.ExtractedText lambda$getExtractedText$15;
                lambda$getExtractedText$15 = android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$getExtractedText$15(inputConnectionCommandHeader, extractedTextRequest, i);
                return lambda$getExtractedText$15;
            }
        }, useImeTracing() ? new java.util.function.Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda22
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                byte[] buildGetExtractedTextProto;
                buildGetExtractedTextProto = com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetExtractedTextProto(android.view.inputmethod.ExtractedTextRequest.this, i, (android.view.inputmethod.ExtractedText) obj);
                return buildGetExtractedTextProto;
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.view.inputmethod.ExtractedText lambda$getExtractedText$15(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.ExtractedTextRequest extractedTextRequest, int i) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return null;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "getExtractedText on inactive InputConnection");
            return null;
        }
        return inputConnection.getExtractedText(extractedTextRequest, i);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitText(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final java.lang.CharSequence charSequence, final int i) {
        dispatchWithTracing("commitText", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda46
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$commitText$17(inputConnectionCommandHeader, charSequence, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitText$17(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "commitText on inactive InputConnection");
        } else {
            inputConnection.commitText(charSequence, i);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitTextWithTextAttribute(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final java.lang.CharSequence charSequence, final int i, final android.view.inputmethod.TextAttribute textAttribute) {
        dispatchWithTracing("commitTextWithTextAttribute", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$commitTextWithTextAttribute$18(inputConnectionCommandHeader, charSequence, i, textAttribute);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitTextWithTextAttribute$18(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "commitText on inactive InputConnection");
        } else {
            inputConnection.commitText(charSequence, i, textAttribute);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitCompletion(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final android.view.inputmethod.CompletionInfo completionInfo) {
        dispatchWithTracing("commitCompletion", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$commitCompletion$19(inputConnectionCommandHeader, completionInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitCompletion$19(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.CompletionInfo completionInfo) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "commitCompletion on inactive InputConnection");
        } else {
            inputConnection.commitCompletion(completionInfo);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitCorrection(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final android.view.inputmethod.CorrectionInfo correctionInfo) {
        dispatchWithTracing("commitCorrection", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$commitCorrection$20(inputConnectionCommandHeader, correctionInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitCorrection$20(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.CorrectionInfo correctionInfo) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "commitCorrection on inactive InputConnection");
        } else {
            try {
                inputConnection.commitCorrection(correctionInfo);
            } catch (java.lang.AbstractMethodError e) {
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setSelection(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2) {
        dispatchWithTracing("setSelection", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$setSelection$21(inputConnectionCommandHeader, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSelection$21(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "setSelection on inactive InputConnection");
        } else {
            inputConnection.setSelection(i, i2);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performEditorAction(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i) {
        dispatchWithTracing("performEditorAction", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda32
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$performEditorAction$22(inputConnectionCommandHeader, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performEditorAction$22(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "performEditorAction on inactive InputConnection");
        } else {
            inputConnection.performEditorAction(i);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performContextMenuAction(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i) {
        dispatchWithTracing("performContextMenuAction", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$performContextMenuAction$23(inputConnectionCommandHeader, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performContextMenuAction$23(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "performContextMenuAction on inactive InputConnection");
        } else {
            inputConnection.performContextMenuAction(i);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setComposingRegion(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2) {
        dispatchWithTracing("setComposingRegion", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$setComposingRegion$24(inputConnectionCommandHeader, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setComposingRegion$24(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "setComposingRegion on inactive InputConnection");
        } else {
            try {
                inputConnection.setComposingRegion(i, i2);
            } catch (java.lang.AbstractMethodError e) {
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setComposingRegionWithTextAttribute(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, final android.view.inputmethod.TextAttribute textAttribute) {
        dispatchWithTracing("setComposingRegionWithTextAttribute", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$setComposingRegionWithTextAttribute$25(inputConnectionCommandHeader, i, i2, textAttribute);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setComposingRegionWithTextAttribute$25(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, android.view.inputmethod.TextAttribute textAttribute) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "setComposingRegion on inactive InputConnection");
        } else {
            inputConnection.setComposingRegion(i, i2, textAttribute);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setComposingText(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final java.lang.CharSequence charSequence, final int i) {
        dispatchWithTracing("setComposingText", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$setComposingText$26(inputConnectionCommandHeader, charSequence, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setComposingText$26(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "setComposingText on inactive InputConnection");
        } else {
            inputConnection.setComposingText(charSequence, i);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setComposingTextWithTextAttribute(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final java.lang.CharSequence charSequence, final int i, final android.view.inputmethod.TextAttribute textAttribute) {
        dispatchWithTracing("setComposingTextWithTextAttribute", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda48
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$setComposingTextWithTextAttribute$27(inputConnectionCommandHeader, charSequence, i, textAttribute);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setComposingTextWithTextAttribute$27(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "setComposingText on inactive InputConnection");
        } else {
            inputConnection.setComposingText(charSequence, i, textAttribute);
        }
    }

    public void finishComposingTextFromImm() {
        final int i = this.mCurrentSessionId.get();
        dispatchWithTracing("finishComposingTextFromImm", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$finishComposingTextFromImm$28(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishComposingTextFromImm$28(int i) {
        if (isFinished() || i != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "finishComposingTextFromImm on inactive InputConnection");
        } else {
            inputConnection.finishComposingText();
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void finishComposingText(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) {
        dispatchWithTracing("finishComposingText", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$finishComposingText$29(inputConnectionCommandHeader);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishComposingText$29(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) {
        if (isFinished() || inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null && this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "finishComposingText on inactive InputConnection");
        } else {
            inputConnection.finishComposingText();
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void sendKeyEvent(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final android.view.KeyEvent keyEvent) {
        dispatchWithTracing("sendKeyEvent", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda41
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$sendKeyEvent$30(inputConnectionCommandHeader, keyEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendKeyEvent$30(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.KeyEvent keyEvent) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "sendKeyEvent on inactive InputConnection");
        } else {
            inputConnection.sendKeyEvent(keyEvent);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void clearMetaKeyStates(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i) {
        dispatchWithTracing("clearMetaKeyStates", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$clearMetaKeyStates$31(inputConnectionCommandHeader, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearMetaKeyStates$31(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "clearMetaKeyStates on inactive InputConnection");
        } else {
            inputConnection.clearMetaKeyStates(i);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void deleteSurroundingText(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2) {
        dispatchWithTracing("deleteSurroundingText", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda44
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$deleteSurroundingText$32(inputConnectionCommandHeader, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteSurroundingText$32(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "deleteSurroundingText on inactive InputConnection");
        } else {
            inputConnection.deleteSurroundingText(i, i2);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void deleteSurroundingTextInCodePoints(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2) {
        dispatchWithTracing("deleteSurroundingTextInCodePoints", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$deleteSurroundingTextInCodePoints$33(inputConnectionCommandHeader, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteSurroundingTextInCodePoints$33(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "deleteSurroundingTextInCodePoints on inactive InputConnection");
        } else {
            try {
                inputConnection.deleteSurroundingTextInCodePoints(i, i2);
            } catch (java.lang.AbstractMethodError e) {
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void beginBatchEdit(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) {
        dispatchWithTracing("beginBatchEdit", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$beginBatchEdit$34(inputConnectionCommandHeader);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$beginBatchEdit$34(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "beginBatchEdit on inactive InputConnection");
        } else {
            inputConnection.beginBatchEdit();
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void endBatchEdit(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) {
        dispatchWithTracing("endBatchEdit", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$endBatchEdit$35(inputConnectionCommandHeader);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$endBatchEdit$35(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "endBatchEdit on inactive InputConnection");
        } else {
            inputConnection.endBatchEdit();
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performSpellCheck(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) {
        dispatchWithTracing("performSpellCheck", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda40
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$performSpellCheck$36(inputConnectionCommandHeader);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performSpellCheck$36(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "performSpellCheck on inactive InputConnection");
        } else {
            inputConnection.performSpellCheck();
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performPrivateCommand(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final java.lang.String str, final android.os.Bundle bundle) {
        dispatchWithTracing("performPrivateCommand", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda39
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$performPrivateCommand$37(inputConnectionCommandHeader, str, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performPrivateCommand$37(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.String str, android.os.Bundle bundle) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "performPrivateCommand on inactive InputConnection");
        } else {
            inputConnection.performPrivateCommand(str, bundle);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performHandwritingGesture(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.ParcelableHandwritingGesture parcelableHandwritingGesture, final android.os.ResultReceiver resultReceiver) {
        final android.view.inputmethod.HandwritingGesture handwritingGesture = parcelableHandwritingGesture.get();
        if (handwritingGesture instanceof android.view.inputmethod.CancellableHandwritingGesture) {
            android.view.inputmethod.CancellableHandwritingGesture cancellableHandwritingGesture = (android.view.inputmethod.CancellableHandwritingGesture) handwritingGesture;
            cancellableHandwritingGesture.unbeamCancellationSignal(getCancellationSignalBeamer());
            if (cancellableHandwritingGesture.getCancellationSignal() != null && cancellableHandwritingGesture.getCancellationSignal().isCanceled()) {
                if (resultReceiver != null) {
                    resultReceiver.send(4, null);
                    return;
                }
                return;
            }
        }
        dispatchWithTracing("performHandwritingGesture", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$performHandwritingGesture$39(inputConnectionCommandHeader, resultReceiver, handwritingGesture);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performHandwritingGesture$39(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final android.os.ResultReceiver resultReceiver, android.view.inputmethod.HandwritingGesture handwritingGesture) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            if (resultReceiver != null) {
                resultReceiver.send(4, null);
                return;
            }
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "performHandwritingGesture on inactive InputConnection");
            if (resultReceiver != null) {
                resultReceiver.send(4, null);
                return;
            }
            return;
        }
        inputConnection.performHandwritingGesture(handwritingGesture, resultReceiver != null ? new android.app.PendingIntent$$ExternalSyntheticLambda0() : null, resultReceiver != null ? new java.util.function.IntConsumer() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda26
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                android.os.ResultReceiver.this.send(i, null);
            }
        } : null);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void previewHandwritingGesture(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.ParcelableHandwritingGesture parcelableHandwritingGesture, android.os.IBinder iBinder) {
        final android.os.CancellationSignal unbeam = iBinder != null ? getCancellationSignalBeamer().unbeam(iBinder) : null;
        final android.view.inputmethod.PreviewableHandwritingGesture previewableHandwritingGesture = (android.view.inputmethod.PreviewableHandwritingGesture) parcelableHandwritingGesture.get();
        dispatchWithTracing("previewHandwritingGesture", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$previewHandwritingGesture$40(inputConnectionCommandHeader, unbeam, previewableHandwritingGesture);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$previewHandwritingGesture$40(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.os.CancellationSignal cancellationSignal, android.view.inputmethod.PreviewableHandwritingGesture previewableHandwritingGesture) {
        if (inputConnectionCommandHeader.mSessionId == this.mCurrentSessionId.get()) {
            if (cancellationSignal != null && cancellationSignal.isCanceled()) {
                return;
            }
            android.view.inputmethod.InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                android.util.Log.w(TAG, "previewHandwritingGesture on inactive InputConnection");
            } else {
                inputConnection.previewHandwritingGesture(previewableHandwritingGesture, cancellationSignal);
            }
        }
    }

    private android.os.CancellationSignalBeamer.Receiver getCancellationSignalBeamer() {
        if (this.mBeamer != null) {
            return this.mBeamer;
        }
        this.mBeamer = new android.os.CancellationSignalBeamer.Receiver(true);
        return this.mBeamer;
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void requestCursorUpdates(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, com.android.internal.infra.AndroidFuture androidFuture) {
        dispatchWithTracing("requestCursorUpdates", androidFuture, new java.util.function.Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda47
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Boolean lambda$requestCursorUpdates$41;
                lambda$requestCursorUpdates$41 = android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$requestCursorUpdates$41(inputConnectionCommandHeader, i, i2);
                return lambda$requestCursorUpdates$41;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$requestCursorUpdates$41(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return false;
        }
        return java.lang.Boolean.valueOf(requestCursorUpdatesInternal(i, 0, i2));
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void requestCursorUpdatesWithFilter(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, final int i3, com.android.internal.infra.AndroidFuture androidFuture) {
        dispatchWithTracing("requestCursorUpdates", androidFuture, new java.util.function.Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Boolean lambda$requestCursorUpdatesWithFilter$42;
                lambda$requestCursorUpdatesWithFilter$42 = android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$requestCursorUpdatesWithFilter$42(inputConnectionCommandHeader, i, i2, i3);
                return lambda$requestCursorUpdatesWithFilter$42;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$requestCursorUpdatesWithFilter$42(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return false;
        }
        return java.lang.Boolean.valueOf(requestCursorUpdatesInternal(i, i2, i3));
    }

    private boolean requestCursorUpdatesInternal(int i, int i2, int i3) {
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        boolean z = false;
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "requestCursorUpdates on inactive InputConnection");
            return false;
        }
        if (this.mParentInputMethodManager.mRequestCursorUpdateDisplayIdCheck.get() && this.mParentInputMethodManager.getDisplayId() != i3) {
            return false;
        }
        boolean z2 = (i & 1) != 0;
        boolean z3 = (i & 2) != 0;
        try {
            boolean requestCursorUpdates = inputConnection.requestCursorUpdates(i, i2);
            this.mHasPendingImmediateCursorAnchorInfoUpdate.set(requestCursorUpdates && z2);
            java.util.concurrent.atomic.AtomicBoolean atomicBoolean = this.mIsCursorAnchorInfoMonitoring;
            if (requestCursorUpdates && z3) {
                z = true;
            }
            atomicBoolean.set(z);
            return requestCursorUpdates;
        } catch (java.lang.AbstractMethodError e) {
            this.mHasPendingImmediateCursorAnchorInfoUpdate.set(false);
            this.mIsCursorAnchorInfoMonitoring.set(false);
            return false;
        } catch (java.lang.Throwable th) {
            this.mHasPendingImmediateCursorAnchorInfoUpdate.set(false);
            this.mIsCursorAnchorInfoMonitoring.set(false);
            throw th;
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void requestTextBoundsInfo(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final android.graphics.RectF rectF, final android.os.ResultReceiver resultReceiver) {
        dispatchWithTracing("requestTextBoundsInfo", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda43
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$requestTextBoundsInfo$44(inputConnectionCommandHeader, resultReceiver, rectF);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestTextBoundsInfo$44(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final android.os.ResultReceiver resultReceiver, android.graphics.RectF rectF) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            resultReceiver.send(3, null);
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "requestTextBoundsInfo on inactive InputConnection");
            resultReceiver.send(3, null);
        } else {
            inputConnection.requestTextBoundsInfo(rectF, new android.app.PendingIntent$$ExternalSyntheticLambda0(), new java.util.function.Consumer() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda42
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.view.inputmethod.RemoteInputConnectionImpl.lambda$requestTextBoundsInfo$43(android.os.ResultReceiver.this, (android.view.inputmethod.TextBoundsInfoResult) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$requestTextBoundsInfo$43(android.os.ResultReceiver resultReceiver, android.view.inputmethod.TextBoundsInfoResult textBoundsInfoResult) {
        int resultCode = textBoundsInfoResult.getResultCode();
        android.view.inputmethod.TextBoundsInfo textBoundsInfo = textBoundsInfoResult.getTextBoundsInfo();
        resultReceiver.send(resultCode, textBoundsInfo == null ? null : textBoundsInfo.toBundle());
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitContent(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final android.view.inputmethod.InputContentInfo inputContentInfo, final int i, final android.os.Bundle bundle, com.android.internal.infra.AndroidFuture androidFuture) {
        final int callingUid = android.os.Binder.getCallingUid();
        dispatchWithTracing("commitContent", androidFuture, new java.util.function.Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Boolean lambda$commitContent$45;
                lambda$commitContent$45 = android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$commitContent$45(inputContentInfo, callingUid, inputConnectionCommandHeader, i, bundle);
                return lambda$commitContent$45;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$commitContent$45(android.view.inputmethod.InputContentInfo inputContentInfo, int i, com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i2, android.os.Bundle bundle) {
        try {
            android.app.UriGrantsManager.getService().checkGrantUriPermission_ignoreNonSystem(i, null, android.content.ContentProvider.getUriWithoutUserId(inputContentInfo.getContentUri()), 1, android.content.ContentProvider.getUserIdFromUri(inputContentInfo.getContentUri(), android.os.UserHandle.getUserId(i)));
            if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
                return false;
            }
            android.view.inputmethod.InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                android.util.Log.w(TAG, "commitContent on inactive InputConnection");
                return false;
            }
            if (inputContentInfo == null || !inputContentInfo.validate()) {
                android.util.Log.w(TAG, "commitContent with invalid inputContentInfo=" + inputContentInfo);
                return false;
            }
            try {
                return java.lang.Boolean.valueOf(inputConnection.commitContent(inputContentInfo, i2, bundle));
            } catch (java.lang.AbstractMethodError e) {
                return false;
            }
        } catch (java.lang.Exception e2) {
            android.util.Log.w(TAG, "commitContent with invalid Uri permission from IME:", e2);
            return false;
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setImeConsumesInput(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final boolean z) {
        dispatchWithTracing("setImeConsumesInput", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$setImeConsumesInput$46(inputConnectionCommandHeader, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setImeConsumesInput$46(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, boolean z) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "setImeConsumesInput on inactive InputConnection");
        } else {
            inputConnection.setImeConsumesInput(z);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void replaceText(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, final java.lang.CharSequence charSequence, final int i3, final android.view.inputmethod.TextAttribute textAttribute) {
        dispatchWithTracing("replaceText", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda49
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$replaceText$47(inputConnectionCommandHeader, i, i2, charSequence, i3, textAttribute);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$replaceText$47(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, java.lang.CharSequence charSequence, int i3, android.view.inputmethod.TextAttribute textAttribute) {
        if (inputConnectionCommandHeader.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        android.view.inputmethod.InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            android.util.Log.w(TAG, "replaceText on inactive InputConnection");
        } else {
            inputConnection.replaceText(i, i2, charSequence, i3, textAttribute);
        }
    }

    /* renamed from: android.view.inputmethod.RemoteInputConnectionImpl$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.Stub {
        AnonymousClass1() {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void commitText(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final java.lang.CharSequence charSequence, final int i, final android.view.inputmethod.TextAttribute textAttribute) {
            android.view.inputmethod.RemoteInputConnectionImpl.this.dispatchWithTracing("commitTextFromA11yIme", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.RemoteInputConnectionImpl.AnonymousClass1.this.lambda$commitText$0(inputConnectionCommandHeader, charSequence, i, textAttribute);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commitText$0(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
            if (inputConnectionCommandHeader.mSessionId != android.view.inputmethod.RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            android.view.inputmethod.InputConnection inputConnection = android.view.inputmethod.RemoteInputConnectionImpl.this.getInputConnection();
            if (inputConnection == null || android.view.inputmethod.RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                android.util.Log.w(android.view.inputmethod.RemoteInputConnectionImpl.TAG, "commitText on inactive InputConnection");
                return;
            }
            inputConnection.beginBatchEdit();
            inputConnection.finishComposingText();
            inputConnection.commitText(charSequence, i, textAttribute);
            inputConnection.endBatchEdit();
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void setSelection(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2) {
            android.view.inputmethod.RemoteInputConnectionImpl.this.dispatchWithTracing("setSelectionFromA11yIme", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.RemoteInputConnectionImpl.AnonymousClass1.this.lambda$setSelection$1(inputConnectionCommandHeader, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSelection$1(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
            if (inputConnectionCommandHeader.mSessionId != android.view.inputmethod.RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            android.view.inputmethod.InputConnection inputConnection = android.view.inputmethod.RemoteInputConnectionImpl.this.getInputConnection();
            if (inputConnection == null || android.view.inputmethod.RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                android.util.Log.w(android.view.inputmethod.RemoteInputConnectionImpl.TAG, "setSelection on inactive InputConnection");
            } else {
                inputConnection.setSelection(i, i2);
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void getSurroundingText(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, final int i3, com.android.internal.infra.AndroidFuture androidFuture) {
            android.view.inputmethod.RemoteInputConnectionImpl.this.dispatchWithTracing("getSurroundingTextFromA11yIme", androidFuture, new java.util.function.Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.view.inputmethod.SurroundingText lambda$getSurroundingText$2;
                    lambda$getSurroundingText$2 = android.view.inputmethod.RemoteInputConnectionImpl.AnonymousClass1.this.lambda$getSurroundingText$2(inputConnectionCommandHeader, i, i2, i3);
                    return lambda$getSurroundingText$2;
                }
            }, android.view.inputmethod.RemoteInputConnectionImpl.useImeTracing() ? new java.util.function.Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    byte[] buildGetSurroundingTextProto;
                    buildGetSurroundingTextProto = com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetSurroundingTextProto(i, i2, i3, (android.view.inputmethod.SurroundingText) obj);
                    return buildGetSurroundingTextProto;
                }
            } : null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.view.inputmethod.SurroundingText lambda$getSurroundingText$2(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3) {
            if (inputConnectionCommandHeader.mSessionId != android.view.inputmethod.RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return null;
            }
            android.view.inputmethod.InputConnection inputConnection = android.view.inputmethod.RemoteInputConnectionImpl.this.getInputConnection();
            if (inputConnection == null || android.view.inputmethod.RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                android.util.Log.w(android.view.inputmethod.RemoteInputConnectionImpl.TAG, "getSurroundingText on inactive InputConnection");
                return null;
            }
            if (i < 0) {
                android.util.Log.i(android.view.inputmethod.RemoteInputConnectionImpl.TAG, "Returning null to getSurroundingText due to an invalid beforeLength=" + i);
                return null;
            }
            if (i2 < 0) {
                android.util.Log.i(android.view.inputmethod.RemoteInputConnectionImpl.TAG, "Returning null to getSurroundingText due to an invalid afterLength=" + i2);
                return null;
            }
            return inputConnection.getSurroundingText(i, i2, i3);
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void deleteSurroundingText(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2) {
            android.view.inputmethod.RemoteInputConnectionImpl.this.dispatchWithTracing("deleteSurroundingTextFromA11yIme", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.RemoteInputConnectionImpl.AnonymousClass1.this.lambda$deleteSurroundingText$4(inputConnectionCommandHeader, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$deleteSurroundingText$4(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
            if (inputConnectionCommandHeader.mSessionId != android.view.inputmethod.RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            android.view.inputmethod.InputConnection inputConnection = android.view.inputmethod.RemoteInputConnectionImpl.this.getInputConnection();
            if (inputConnection == null || android.view.inputmethod.RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                android.util.Log.w(android.view.inputmethod.RemoteInputConnectionImpl.TAG, "deleteSurroundingText on inactive InputConnection");
            } else {
                inputConnection.deleteSurroundingText(i, i2);
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void sendKeyEvent(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final android.view.KeyEvent keyEvent) {
            android.view.inputmethod.RemoteInputConnectionImpl.this.dispatchWithTracing("sendKeyEventFromA11yIme", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.RemoteInputConnectionImpl.AnonymousClass1.this.lambda$sendKeyEvent$5(inputConnectionCommandHeader, keyEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendKeyEvent$5(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.KeyEvent keyEvent) {
            if (inputConnectionCommandHeader.mSessionId != android.view.inputmethod.RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            android.view.inputmethod.InputConnection inputConnection = android.view.inputmethod.RemoteInputConnectionImpl.this.getInputConnection();
            if (inputConnection == null || android.view.inputmethod.RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                android.util.Log.w(android.view.inputmethod.RemoteInputConnectionImpl.TAG, "sendKeyEvent on inactive InputConnection");
            } else {
                inputConnection.sendKeyEvent(keyEvent);
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void performEditorAction(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i) {
            android.view.inputmethod.RemoteInputConnectionImpl.this.dispatchWithTracing("performEditorActionFromA11yIme", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.RemoteInputConnectionImpl.AnonymousClass1.this.lambda$performEditorAction$6(inputConnectionCommandHeader, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$performEditorAction$6(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
            if (inputConnectionCommandHeader.mSessionId != android.view.inputmethod.RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            android.view.inputmethod.InputConnection inputConnection = android.view.inputmethod.RemoteInputConnectionImpl.this.getInputConnection();
            if (inputConnection == null || android.view.inputmethod.RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                android.util.Log.w(android.view.inputmethod.RemoteInputConnectionImpl.TAG, "performEditorAction on inactive InputConnection");
            } else {
                inputConnection.performEditorAction(i);
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void performContextMenuAction(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i) {
            android.view.inputmethod.RemoteInputConnectionImpl.this.dispatchWithTracing("performContextMenuActionFromA11yIme", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.RemoteInputConnectionImpl.AnonymousClass1.this.lambda$performContextMenuAction$7(inputConnectionCommandHeader, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$performContextMenuAction$7(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
            if (inputConnectionCommandHeader.mSessionId != android.view.inputmethod.RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            android.view.inputmethod.InputConnection inputConnection = android.view.inputmethod.RemoteInputConnectionImpl.this.getInputConnection();
            if (inputConnection == null || android.view.inputmethod.RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                android.util.Log.w(android.view.inputmethod.RemoteInputConnectionImpl.TAG, "performContextMenuAction on inactive InputConnection");
            } else {
                inputConnection.performContextMenuAction(i);
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void getCursorCapsMode(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i, com.android.internal.infra.AndroidFuture androidFuture) {
            android.view.inputmethod.RemoteInputConnectionImpl.this.dispatchWithTracing("getCursorCapsModeFromA11yIme", androidFuture, new java.util.function.Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$getCursorCapsMode$8;
                    lambda$getCursorCapsMode$8 = android.view.inputmethod.RemoteInputConnectionImpl.AnonymousClass1.this.lambda$getCursorCapsMode$8(inputConnectionCommandHeader, i);
                    return lambda$getCursorCapsMode$8;
                }
            }, android.view.inputmethod.RemoteInputConnectionImpl.useImeTracing() ? new java.util.function.Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda8
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    byte[] buildGetCursorCapsModeProto;
                    buildGetCursorCapsModeProto = com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetCursorCapsModeProto(i, ((java.lang.Integer) obj).intValue());
                    return buildGetCursorCapsModeProto;
                }
            } : null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$getCursorCapsMode$8(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
            if (inputConnectionCommandHeader.mSessionId != android.view.inputmethod.RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return 0;
            }
            android.view.inputmethod.InputConnection inputConnection = android.view.inputmethod.RemoteInputConnectionImpl.this.getInputConnection();
            if (inputConnection == null || android.view.inputmethod.RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                android.util.Log.w(android.view.inputmethod.RemoteInputConnectionImpl.TAG, "getCursorCapsMode on inactive InputConnection");
                return 0;
            }
            return java.lang.Integer.valueOf(inputConnection.getCursorCapsMode(i));
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void clearMetaKeyStates(final com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, final int i) {
            android.view.inputmethod.RemoteInputConnectionImpl.this.dispatchWithTracing("clearMetaKeyStatesFromA11yIme", new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.RemoteInputConnectionImpl.AnonymousClass1.this.lambda$clearMetaKeyStates$10(inputConnectionCommandHeader, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$clearMetaKeyStates$10(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
            if (inputConnectionCommandHeader.mSessionId != android.view.inputmethod.RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            android.view.inputmethod.InputConnection inputConnection = android.view.inputmethod.RemoteInputConnectionImpl.this.getInputConnection();
            if (inputConnection == null || android.view.inputmethod.RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                android.util.Log.w(android.view.inputmethod.RemoteInputConnectionImpl.TAG, "clearMetaKeyStates on inactive InputConnection");
            } else {
                inputConnection.clearMetaKeyStates(i);
            }
        }
    }

    public com.android.internal.inputmethod.IRemoteAccessibilityInputConnection asIRemoteAccessibilityInputConnection() {
        return this.mAccessibilityInputConnection;
    }

    private void dispatch(java.lang.Runnable runnable) {
        if (this.mLooper.isCurrentThread()) {
            runnable.run();
        } else {
            this.mH.post(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchWithTracing(final java.lang.String str, final java.lang.Runnable runnable) {
        if (android.os.Trace.isTagEnabled(4L)) {
            runnable = new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.RemoteInputConnectionImpl.lambda$dispatchWithTracing$48(str, runnable);
                }
            };
        }
        dispatch(runnable);
    }

    static /* synthetic */ void lambda$dispatchWithTracing$48(java.lang.String str, java.lang.Runnable runnable) {
        android.os.Trace.traceBegin(4L, "InputConnection#" + str);
        try {
            runnable.run();
        } finally {
            android.os.Trace.traceEnd(4L);
        }
    }

    private <T> void dispatchWithTracing(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture, java.util.function.Supplier<T> supplier) {
        dispatchWithTracing(str, androidFuture, supplier, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> void dispatchWithTracing(final java.lang.String str, final com.android.internal.infra.AndroidFuture androidFuture, final java.util.function.Supplier<T> supplier, final java.util.function.Function<T, byte[]> function) {
        dispatchWithTracing(str, new java.lang.Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                android.view.inputmethod.RemoteInputConnectionImpl.this.lambda$dispatchWithTracing$49(supplier, androidFuture, function, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchWithTracing$49(java.util.function.Supplier supplier, com.android.internal.infra.AndroidFuture androidFuture, java.util.function.Function function, java.lang.String str) {
        try {
            java.lang.Object obj = supplier.get();
            androidFuture.complete(obj);
            if (function != null) {
                com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("RemoteInputConnectionImpl#" + str, this.mParentInputMethodManager, (byte[]) function.apply(obj));
            }
        } catch (java.lang.Throwable th) {
            androidFuture.completeExceptionally(th);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean useImeTracing() {
        return com.android.internal.inputmethod.ImeTracing.getInstance().isEnabled();
    }
}
