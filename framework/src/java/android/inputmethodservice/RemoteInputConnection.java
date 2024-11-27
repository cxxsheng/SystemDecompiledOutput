package android.inputmethodservice;

/* loaded from: classes2.dex */
final class RemoteInputConnection implements android.view.inputmethod.InputConnection {
    private static final int MAX_WAIT_TIME_MILLIS = 2000;
    private static final java.lang.String TAG = "RemoteInputConnection";
    private final com.android.internal.inputmethod.CancellationGroup mCancellationGroup;
    private final android.inputmethodservice.RemoteInputConnection.InputMethodServiceInternalHolder mImsInternal;
    private final android.inputmethodservice.IRemoteInputConnectionInvoker mInvoker;

    private static final class InputMethodServiceInternalHolder {
        private final java.lang.ref.WeakReference<android.inputmethodservice.InputMethodServiceInternal> mServiceRef;

        private InputMethodServiceInternalHolder(java.lang.ref.WeakReference<android.inputmethodservice.InputMethodServiceInternal> weakReference) {
            this.mServiceRef = weakReference;
        }

        public android.inputmethodservice.InputMethodServiceInternal getAndWarnIfNull() {
            android.inputmethodservice.InputMethodServiceInternal inputMethodServiceInternal = this.mServiceRef.get();
            if (inputMethodServiceInternal == null) {
                android.util.Log.e(android.inputmethodservice.RemoteInputConnection.TAG, "InputMethodService is already destroyed.  InputConnection instances cannot be used beyond InputMethodService lifetime.", new java.lang.Throwable());
            }
            return inputMethodServiceInternal;
        }
    }

    RemoteInputConnection(java.lang.ref.WeakReference<android.inputmethodservice.InputMethodServiceInternal> weakReference, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, com.android.internal.inputmethod.CancellationGroup cancellationGroup) {
        this.mImsInternal = new android.inputmethodservice.RemoteInputConnection.InputMethodServiceInternalHolder(weakReference);
        this.mInvoker = android.inputmethodservice.IRemoteInputConnectionInvoker.create(iRemoteInputConnection);
        this.mCancellationGroup = cancellationGroup;
    }

    public boolean isSameConnection(com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection) {
        return this.mInvoker.isSameConnection(iRemoteInputConnection);
    }

    RemoteInputConnection(android.inputmethodservice.RemoteInputConnection remoteInputConnection, int i) {
        this.mImsInternal = remoteInputConnection.mImsInternal;
        this.mInvoker = remoteInputConnection.mInvoker.cloneWithSessionId(i);
        this.mCancellationGroup = remoteInputConnection.mCancellationGroup;
    }

    @Override // android.view.inputmethod.InputConnection
    public java.lang.CharSequence getTextAfterCursor(int i, int i2) {
        if (i < 0) {
            android.util.Log.e(TAG, "length=" + i + " is invalid and always results in null result.");
        }
        if (this.mCancellationGroup.isCanceled()) {
            return null;
        }
        java.lang.CharSequence charSequence = (java.lang.CharSequence) com.android.internal.inputmethod.CompletableFutureUtil.getResultOrNull(this.mInvoker.getTextAfterCursor(i, i2), TAG, "getTextAfterCursor()", this.mCancellationGroup, 2000L);
        android.inputmethodservice.InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull != null && com.android.internal.inputmethod.ImeTracing.getInstance().isEnabled()) {
            andWarnIfNull.triggerServiceDump("RemoteInputConnection#getTextAfterCursor", com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetTextAfterCursorProto(i, i2, charSequence));
        }
        return charSequence;
    }

    @Override // android.view.inputmethod.InputConnection
    public java.lang.CharSequence getTextBeforeCursor(int i, int i2) {
        if (i < 0) {
            android.util.Log.e(TAG, "length=" + i + " is invalid and always results in null result.");
        }
        if (this.mCancellationGroup.isCanceled()) {
            return null;
        }
        java.lang.CharSequence charSequence = (java.lang.CharSequence) com.android.internal.inputmethod.CompletableFutureUtil.getResultOrNull(this.mInvoker.getTextBeforeCursor(i, i2), TAG, "getTextBeforeCursor()", this.mCancellationGroup, 2000L);
        android.inputmethodservice.InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull != null && com.android.internal.inputmethod.ImeTracing.getInstance().isEnabled()) {
            andWarnIfNull.triggerServiceDump("RemoteInputConnection#getTextBeforeCursor", com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetTextBeforeCursorProto(i, i2, charSequence));
        }
        return charSequence;
    }

    @Override // android.view.inputmethod.InputConnection
    public java.lang.CharSequence getSelectedText(int i) {
        if (this.mCancellationGroup.isCanceled()) {
            return null;
        }
        java.lang.CharSequence charSequence = (java.lang.CharSequence) com.android.internal.inputmethod.CompletableFutureUtil.getResultOrNull(this.mInvoker.getSelectedText(i), TAG, "getSelectedText()", this.mCancellationGroup, 2000L);
        android.inputmethodservice.InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull != null && com.android.internal.inputmethod.ImeTracing.getInstance().isEnabled()) {
            andWarnIfNull.triggerServiceDump("RemoteInputConnection#getSelectedText", com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetSelectedTextProto(i, charSequence));
        }
        return charSequence;
    }

    @Override // android.view.inputmethod.InputConnection
    public android.view.inputmethod.SurroundingText getSurroundingText(int i, int i2, int i3) {
        if (i < 0) {
            android.util.Log.e(TAG, "beforeLength=" + i + " is invalid and always results in null result.");
        }
        if (i2 < 0) {
            android.util.Log.e(TAG, "afterLength=" + i2 + " is invalid and always results in null result.");
        }
        if (this.mCancellationGroup.isCanceled()) {
            return null;
        }
        android.view.inputmethod.SurroundingText surroundingText = (android.view.inputmethod.SurroundingText) com.android.internal.inputmethod.CompletableFutureUtil.getResultOrNull(this.mInvoker.getSurroundingText(i, i2, i3), TAG, "getSurroundingText()", this.mCancellationGroup, 2000L);
        android.inputmethodservice.InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull != null && com.android.internal.inputmethod.ImeTracing.getInstance().isEnabled()) {
            andWarnIfNull.triggerServiceDump("RemoteInputConnection#getSurroundingText", com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetSurroundingTextProto(i, i2, i3, surroundingText));
        }
        return surroundingText;
    }

    @Override // android.view.inputmethod.InputConnection
    public int getCursorCapsMode(int i) {
        if (this.mCancellationGroup.isCanceled()) {
            return 0;
        }
        int resultOrZero = com.android.internal.inputmethod.CompletableFutureUtil.getResultOrZero(this.mInvoker.getCursorCapsMode(i), TAG, "getCursorCapsMode()", this.mCancellationGroup, 2000L);
        android.inputmethodservice.InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull != null && com.android.internal.inputmethod.ImeTracing.getInstance().isEnabled()) {
            andWarnIfNull.triggerServiceDump("RemoteInputConnection#getCursorCapsMode", com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetCursorCapsModeProto(i, resultOrZero));
        }
        return resultOrZero;
    }

    @Override // android.view.inputmethod.InputConnection
    public android.view.inputmethod.ExtractedText getExtractedText(android.view.inputmethod.ExtractedTextRequest extractedTextRequest, int i) {
        if (this.mCancellationGroup.isCanceled()) {
            return null;
        }
        android.view.inputmethod.ExtractedText extractedText = (android.view.inputmethod.ExtractedText) com.android.internal.inputmethod.CompletableFutureUtil.getResultOrNull(this.mInvoker.getExtractedText(extractedTextRequest, i), TAG, "getExtractedText()", this.mCancellationGroup, 2000L);
        android.inputmethodservice.InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull != null && com.android.internal.inputmethod.ImeTracing.getInstance().isEnabled()) {
            andWarnIfNull.triggerServiceDump("RemoteInputConnection#getExtractedText", com.android.internal.inputmethod.InputConnectionProtoDumper.buildGetExtractedTextProto(extractedTextRequest, i, extractedText));
        }
        return extractedText;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitText(java.lang.CharSequence charSequence, int i) {
        boolean commitText = this.mInvoker.commitText(charSequence, i);
        if (commitText) {
            notifyUserActionIfNecessary();
        }
        return commitText;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitText(java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
        boolean commitText = this.mInvoker.commitText(charSequence, i, textAttribute);
        if (commitText) {
            notifyUserActionIfNecessary();
        }
        return commitText;
    }

    private void notifyUserActionIfNecessary() {
        android.inputmethodservice.InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        andWarnIfNull.notifyUserActionIfNecessary();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCompletion(android.view.inputmethod.CompletionInfo completionInfo) {
        return this.mInvoker.commitCompletion(completionInfo);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCorrection(android.view.inputmethod.CorrectionInfo correctionInfo) {
        return this.mInvoker.commitCorrection(correctionInfo);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setSelection(int i, int i2) {
        return this.mInvoker.setSelection(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performEditorAction(int i) {
        return this.mInvoker.performEditorAction(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performContextMenuAction(int i) {
        return this.mInvoker.performContextMenuAction(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int i, int i2) {
        return this.mInvoker.setComposingRegion(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int i, int i2, android.view.inputmethod.TextAttribute textAttribute) {
        return this.mInvoker.setComposingRegion(i, i2, textAttribute);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingText(java.lang.CharSequence charSequence, int i) {
        boolean composingText = this.mInvoker.setComposingText(charSequence, i);
        if (composingText) {
            notifyUserActionIfNecessary();
        }
        return composingText;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingText(java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
        boolean composingText = this.mInvoker.setComposingText(charSequence, i, textAttribute);
        if (composingText) {
            notifyUserActionIfNecessary();
        }
        return composingText;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean finishComposingText() {
        return this.mInvoker.finishComposingText();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean beginBatchEdit() {
        return this.mInvoker.beginBatchEdit();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean endBatchEdit() {
        return this.mInvoker.endBatchEdit();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean sendKeyEvent(android.view.KeyEvent keyEvent) {
        boolean sendKeyEvent = this.mInvoker.sendKeyEvent(keyEvent);
        if (sendKeyEvent) {
            notifyUserActionIfNecessary();
        }
        return sendKeyEvent;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean clearMetaKeyStates(int i) {
        return this.mInvoker.clearMetaKeyStates(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingText(int i, int i2) {
        return this.mInvoker.deleteSurroundingText(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        return this.mInvoker.deleteSurroundingTextInCodePoints(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean reportFullscreenMode(boolean z) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performSpellCheck() {
        return this.mInvoker.performSpellCheck();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
        return this.mInvoker.performPrivateCommand(str, bundle);
    }

    @Override // android.view.inputmethod.InputConnection
    public void performHandwritingGesture(android.view.inputmethod.HandwritingGesture handwritingGesture, java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
        this.mInvoker.performHandwritingGesture(handwritingGesture, executor, intConsumer);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean previewHandwritingGesture(android.view.inputmethod.PreviewableHandwritingGesture previewableHandwritingGesture, android.os.CancellationSignal cancellationSignal) {
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            return false;
        }
        return this.mInvoker.previewHandwritingGesture(previewableHandwritingGesture, cancellationSignal);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i) {
        android.inputmethodservice.InputMethodServiceInternal andWarnIfNull;
        if (this.mCancellationGroup.isCanceled() || (andWarnIfNull = this.mImsInternal.getAndWarnIfNull()) == null) {
            return false;
        }
        return com.android.internal.inputmethod.CompletableFutureUtil.getResultOrFalse(this.mInvoker.requestCursorUpdates(i, andWarnIfNull.getContext().getDisplayId()), TAG, "requestCursorUpdates()", this.mCancellationGroup, 2000L);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i, int i2) {
        android.inputmethodservice.InputMethodServiceInternal andWarnIfNull;
        if (this.mCancellationGroup.isCanceled() || (andWarnIfNull = this.mImsInternal.getAndWarnIfNull()) == null) {
            return false;
        }
        return com.android.internal.inputmethod.CompletableFutureUtil.getResultOrFalse(this.mInvoker.requestCursorUpdates(i, i2, andWarnIfNull.getContext().getDisplayId()), TAG, "requestCursorUpdates()", this.mCancellationGroup, 2000L);
    }

    @Override // android.view.inputmethod.InputConnection
    public void requestTextBoundsInfo(android.graphics.RectF rectF, java.util.concurrent.Executor executor, java.util.function.Consumer<android.view.inputmethod.TextBoundsInfoResult> consumer) {
        this.mInvoker.requestTextBoundsInfo(rectF, executor, consumer);
    }

    @Override // android.view.inputmethod.InputConnection
    public android.os.Handler getHandler() {
        return null;
    }

    @Override // android.view.inputmethod.InputConnection
    public void closeConnection() {
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitContent(android.view.inputmethod.InputContentInfo inputContentInfo, int i, android.os.Bundle bundle) {
        if (this.mCancellationGroup.isCanceled()) {
            return false;
        }
        if ((i & 1) != 0) {
            android.inputmethodservice.InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
            if (andWarnIfNull == null) {
                return false;
            }
            andWarnIfNull.exposeContent(inputContentInfo, this);
        }
        return com.android.internal.inputmethod.CompletableFutureUtil.getResultOrFalse(this.mInvoker.commitContent(inputContentInfo, i, bundle), TAG, "commitContent()", this.mCancellationGroup, 2000L);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setImeConsumesInput(boolean z) {
        return this.mInvoker.setImeConsumesInput(z);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean replaceText(int i, int i2, java.lang.CharSequence charSequence, int i3, android.view.inputmethod.TextAttribute textAttribute) {
        return this.mInvoker.replaceText(i, i2, charSequence, i3, textAttribute);
    }

    public java.lang.String toString() {
        return "RemoteInputConnection{idHash=#" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + "}";
    }
}
