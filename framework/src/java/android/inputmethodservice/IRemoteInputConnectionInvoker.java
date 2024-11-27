package android.inputmethodservice;

/* loaded from: classes2.dex */
final class IRemoteInputConnectionInvoker {
    private android.os.CancellationSignalBeamer.Sender mBeamer;
    private final com.android.internal.inputmethod.IRemoteInputConnection mConnection;
    private final int mSessionId;

    private IRemoteInputConnectionInvoker(com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) {
        this.mConnection = iRemoteInputConnection;
        this.mSessionId = i;
    }

    private static abstract class OnceResultReceiver<C> extends android.os.ResultReceiver {
        private C mConsumer;
        private java.util.concurrent.Executor mExecutor;

        protected abstract void dispatch(java.util.concurrent.Executor executor, C c, int i, android.os.Bundle bundle);

        protected OnceResultReceiver(java.util.concurrent.Executor executor, C c) {
            super((android.os.Handler) null);
            java.util.Objects.requireNonNull(executor);
            java.util.Objects.requireNonNull(c);
            this.mExecutor = executor;
            this.mConsumer = c;
        }

        @Override // android.os.ResultReceiver
        protected final void onReceiveResult(int i, android.os.Bundle bundle) {
            java.util.concurrent.Executor executor;
            C c;
            synchronized (this) {
                executor = this.mExecutor;
                c = this.mConsumer;
                this.mExecutor = null;
                this.mConsumer = null;
            }
            if (executor != null && c != null) {
                dispatch(executor, c, i, bundle);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class IntResultReceiver extends android.inputmethodservice.IRemoteInputConnectionInvoker.OnceResultReceiver<java.util.function.IntConsumer> {
        IntResultReceiver(java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
            super(executor, intConsumer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.inputmethodservice.IRemoteInputConnectionInvoker.OnceResultReceiver
        public void dispatch(java.util.concurrent.Executor executor, final java.util.function.IntConsumer intConsumer, final int i, android.os.Bundle bundle) {
            executor.execute(new java.lang.Runnable() { // from class: android.inputmethodservice.IRemoteInputConnectionInvoker$IntResultReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    intConsumer.accept(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TextBoundsInfoResultReceiver extends android.inputmethodservice.IRemoteInputConnectionInvoker.OnceResultReceiver<java.util.function.Consumer<android.view.inputmethod.TextBoundsInfoResult>> {
        TextBoundsInfoResultReceiver(java.util.concurrent.Executor executor, java.util.function.Consumer<android.view.inputmethod.TextBoundsInfoResult> consumer) {
            super(executor, consumer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.inputmethodservice.IRemoteInputConnectionInvoker.OnceResultReceiver
        public void dispatch(java.util.concurrent.Executor executor, final java.util.function.Consumer<android.view.inputmethod.TextBoundsInfoResult> consumer, int i, android.os.Bundle bundle) {
            final android.view.inputmethod.TextBoundsInfoResult textBoundsInfoResult = new android.view.inputmethod.TextBoundsInfoResult(i, android.view.inputmethod.TextBoundsInfo.createFromBundle(bundle));
            executor.execute(new java.lang.Runnable() { // from class: android.inputmethodservice.IRemoteInputConnectionInvoker$TextBoundsInfoResultReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(textBoundsInfoResult);
                }
            });
        }
    }

    public static android.inputmethodservice.IRemoteInputConnectionInvoker create(com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection) {
        java.util.Objects.requireNonNull(iRemoteInputConnection);
        return new android.inputmethodservice.IRemoteInputConnectionInvoker(iRemoteInputConnection, 0);
    }

    public android.inputmethodservice.IRemoteInputConnectionInvoker cloneWithSessionId(int i) {
        return new android.inputmethodservice.IRemoteInputConnectionInvoker(this.mConnection, i);
    }

    public boolean isSameConnection(com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection) {
        return iRemoteInputConnection != null && this.mConnection.asBinder() == iRemoteInputConnection.asBinder();
    }

    com.android.internal.inputmethod.InputConnectionCommandHeader createHeader() {
        return new com.android.internal.inputmethod.InputConnectionCommandHeader(this.mSessionId);
    }

    public com.android.internal.infra.AndroidFuture<java.lang.CharSequence> getTextAfterCursor(int i, int i2) {
        com.android.internal.infra.AndroidFuture<java.lang.CharSequence> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        try {
            this.mConnection.getTextAfterCursor(createHeader(), i, i2, androidFuture);
        } catch (android.os.RemoteException e) {
            androidFuture.completeExceptionally(e);
        }
        return androidFuture;
    }

    public com.android.internal.infra.AndroidFuture<java.lang.CharSequence> getTextBeforeCursor(int i, int i2) {
        com.android.internal.infra.AndroidFuture<java.lang.CharSequence> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        try {
            this.mConnection.getTextBeforeCursor(createHeader(), i, i2, androidFuture);
        } catch (android.os.RemoteException e) {
            androidFuture.completeExceptionally(e);
        }
        return androidFuture;
    }

    public com.android.internal.infra.AndroidFuture<java.lang.CharSequence> getSelectedText(int i) {
        com.android.internal.infra.AndroidFuture<java.lang.CharSequence> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        try {
            this.mConnection.getSelectedText(createHeader(), i, androidFuture);
        } catch (android.os.RemoteException e) {
            androidFuture.completeExceptionally(e);
        }
        return androidFuture;
    }

    public com.android.internal.infra.AndroidFuture<android.view.inputmethod.SurroundingText> getSurroundingText(int i, int i2, int i3) {
        com.android.internal.infra.AndroidFuture<android.view.inputmethod.SurroundingText> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        try {
            this.mConnection.getSurroundingText(createHeader(), i, i2, i3, androidFuture);
        } catch (android.os.RemoteException e) {
            androidFuture.completeExceptionally(e);
        }
        return androidFuture;
    }

    public com.android.internal.infra.AndroidFuture<java.lang.Integer> getCursorCapsMode(int i) {
        com.android.internal.infra.AndroidFuture<java.lang.Integer> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        try {
            this.mConnection.getCursorCapsMode(createHeader(), i, androidFuture);
        } catch (android.os.RemoteException e) {
            androidFuture.completeExceptionally(e);
        }
        return androidFuture;
    }

    public com.android.internal.infra.AndroidFuture<android.view.inputmethod.ExtractedText> getExtractedText(android.view.inputmethod.ExtractedTextRequest extractedTextRequest, int i) {
        com.android.internal.infra.AndroidFuture<android.view.inputmethod.ExtractedText> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        try {
            this.mConnection.getExtractedText(createHeader(), extractedTextRequest, i, androidFuture);
        } catch (android.os.RemoteException e) {
            androidFuture.completeExceptionally(e);
        }
        return androidFuture;
    }

    public boolean commitText(java.lang.CharSequence charSequence, int i) {
        try {
            this.mConnection.commitText(createHeader(), charSequence, i);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean commitText(java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
        try {
            this.mConnection.commitTextWithTextAttribute(createHeader(), charSequence, i, textAttribute);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean commitCompletion(android.view.inputmethod.CompletionInfo completionInfo) {
        try {
            this.mConnection.commitCompletion(createHeader(), completionInfo);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean commitCorrection(android.view.inputmethod.CorrectionInfo correctionInfo) {
        try {
            this.mConnection.commitCorrection(createHeader(), correctionInfo);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean setSelection(int i, int i2) {
        try {
            this.mConnection.setSelection(createHeader(), i, i2);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean performEditorAction(int i) {
        try {
            this.mConnection.performEditorAction(createHeader(), i);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean performContextMenuAction(int i) {
        try {
            this.mConnection.performContextMenuAction(createHeader(), i);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean setComposingRegion(int i, int i2) {
        try {
            this.mConnection.setComposingRegion(createHeader(), i, i2);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean setComposingRegion(int i, int i2, android.view.inputmethod.TextAttribute textAttribute) {
        try {
            this.mConnection.setComposingRegionWithTextAttribute(createHeader(), i, i2, textAttribute);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean setComposingText(java.lang.CharSequence charSequence, int i) {
        try {
            this.mConnection.setComposingText(createHeader(), charSequence, i);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean setComposingText(java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
        try {
            this.mConnection.setComposingTextWithTextAttribute(createHeader(), charSequence, i, textAttribute);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean finishComposingText() {
        try {
            this.mConnection.finishComposingText(createHeader());
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean beginBatchEdit() {
        try {
            this.mConnection.beginBatchEdit(createHeader());
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean endBatchEdit() {
        try {
            this.mConnection.endBatchEdit(createHeader());
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean sendKeyEvent(android.view.KeyEvent keyEvent) {
        try {
            this.mConnection.sendKeyEvent(createHeader(), keyEvent);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean clearMetaKeyStates(int i) {
        try {
            this.mConnection.clearMetaKeyStates(createHeader(), i);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean deleteSurroundingText(int i, int i2) {
        try {
            this.mConnection.deleteSurroundingText(createHeader(), i, i2);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        try {
            this.mConnection.deleteSurroundingTextInCodePoints(createHeader(), i, i2);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean performSpellCheck() {
        try {
            this.mConnection.performSpellCheck(createHeader());
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean performPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
        try {
            this.mConnection.performPrivateCommand(createHeader(), str, bundle);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public void performHandwritingGesture(android.view.inputmethod.HandwritingGesture handwritingGesture, java.util.concurrent.Executor executor, final java.util.function.IntConsumer intConsumer) {
        android.inputmethodservice.IRemoteInputConnectionInvoker.IntResultReceiver intResultReceiver;
        if (intConsumer == null) {
            intResultReceiver = null;
        } else {
            java.util.Objects.requireNonNull(executor);
            intResultReceiver = new android.inputmethodservice.IRemoteInputConnectionInvoker.IntResultReceiver(executor, intConsumer);
        }
        try {
            android.os.CancellationSignalBeamer.Sender.MustClose beamScopeIfNeeded = getCancellationSignalBeamer().beamScopeIfNeeded(handwritingGesture);
            try {
                this.mConnection.performHandwritingGesture(createHeader(), android.view.inputmethod.ParcelableHandwritingGesture.of(handwritingGesture), intResultReceiver);
                if (beamScopeIfNeeded != null) {
                    beamScopeIfNeeded.close();
                }
            } finally {
            }
        } catch (android.os.RemoteException e) {
            if (intConsumer != null && executor != null) {
                executor.execute(new java.lang.Runnable() { // from class: android.inputmethodservice.IRemoteInputConnectionInvoker$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        intConsumer.accept(4);
                    }
                });
            }
        }
    }

    public boolean previewHandwritingGesture(android.view.inputmethod.HandwritingGesture handwritingGesture, android.os.CancellationSignal cancellationSignal) {
        try {
            android.os.CancellationSignalBeamer.Sender.CloseableToken beam = beam(cancellationSignal);
            try {
                this.mConnection.previewHandwritingGesture(createHeader(), android.view.inputmethod.ParcelableHandwritingGesture.of(handwritingGesture), beam);
                if (beam != null) {
                    beam.close();
                    return true;
                }
                return true;
            } finally {
            }
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    android.os.CancellationSignalBeamer.Sender.CloseableToken beam(android.os.CancellationSignal cancellationSignal) {
        if (cancellationSignal == null) {
            return null;
        }
        return getCancellationSignalBeamer().beam(cancellationSignal);
    }

    private android.os.CancellationSignalBeamer.Sender getCancellationSignalBeamer() {
        if (this.mBeamer != null) {
            return this.mBeamer;
        }
        this.mBeamer = new android.os.CancellationSignalBeamer.Sender() { // from class: android.inputmethodservice.IRemoteInputConnectionInvoker.1
            @Override // android.os.CancellationSignalBeamer.Sender
            public void onCancel(android.os.IBinder iBinder) {
                try {
                    android.inputmethodservice.IRemoteInputConnectionInvoker.this.mConnection.cancelCancellationSignal(iBinder);
                } catch (android.os.RemoteException e) {
                }
            }

            @Override // android.os.CancellationSignalBeamer.Sender
            public void onForget(android.os.IBinder iBinder) {
                try {
                    android.inputmethodservice.IRemoteInputConnectionInvoker.this.mConnection.forgetCancellationSignal(iBinder);
                } catch (android.os.RemoteException e) {
                }
            }
        };
        return this.mBeamer;
    }

    public com.android.internal.infra.AndroidFuture<java.lang.Boolean> requestCursorUpdates(int i, int i2) {
        com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        try {
            this.mConnection.requestCursorUpdates(createHeader(), i, i2, androidFuture);
        } catch (android.os.RemoteException e) {
            androidFuture.completeExceptionally(e);
        }
        return androidFuture;
    }

    public com.android.internal.infra.AndroidFuture<java.lang.Boolean> requestCursorUpdates(int i, int i2, int i3) {
        com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        try {
            this.mConnection.requestCursorUpdatesWithFilter(createHeader(), i, i2, i3, androidFuture);
        } catch (android.os.RemoteException e) {
            androidFuture.completeExceptionally(e);
        }
        return androidFuture;
    }

    public void requestTextBoundsInfo(android.graphics.RectF rectF, java.util.concurrent.Executor executor, final java.util.function.Consumer<android.view.inputmethod.TextBoundsInfoResult> consumer) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        try {
            this.mConnection.requestTextBoundsInfo(createHeader(), rectF, new android.inputmethodservice.IRemoteInputConnectionInvoker.TextBoundsInfoResultReceiver(executor, consumer));
        } catch (android.os.RemoteException e) {
            executor.execute(new java.lang.Runnable() { // from class: android.inputmethodservice.IRemoteInputConnectionInvoker$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(new android.view.inputmethod.TextBoundsInfoResult(3));
                }
            });
        }
    }

    public com.android.internal.infra.AndroidFuture<java.lang.Boolean> commitContent(android.view.inputmethod.InputContentInfo inputContentInfo, int i, android.os.Bundle bundle) {
        com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        try {
            this.mConnection.commitContent(createHeader(), inputContentInfo, i, bundle, androidFuture);
        } catch (android.os.RemoteException e) {
            androidFuture.completeExceptionally(e);
        }
        return androidFuture;
    }

    public boolean setImeConsumesInput(boolean z) {
        try {
            this.mConnection.setImeConsumesInput(createHeader(), z);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean replaceText(int i, int i2, java.lang.CharSequence charSequence, int i3, android.view.inputmethod.TextAttribute textAttribute) {
        try {
            this.mConnection.replaceText(createHeader(), i, i2, charSequence, i3, textAttribute);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }
}
