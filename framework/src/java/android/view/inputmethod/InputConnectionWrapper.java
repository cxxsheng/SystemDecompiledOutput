package android.view.inputmethod;

/* loaded from: classes4.dex */
public class InputConnectionWrapper implements android.view.inputmethod.InputConnection {
    final boolean mMutable;
    private android.view.inputmethod.InputConnection mTarget;

    public InputConnectionWrapper(android.view.inputmethod.InputConnection inputConnection, boolean z) {
        this.mMutable = z;
        this.mTarget = inputConnection;
    }

    public void setTarget(android.view.inputmethod.InputConnection inputConnection) {
        if (this.mTarget != null && !this.mMutable) {
            throw new java.lang.SecurityException("not mutable");
        }
        this.mTarget = inputConnection;
    }

    @Override // android.view.inputmethod.InputConnection
    public java.lang.CharSequence getTextBeforeCursor(int i, int i2) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        return this.mTarget.getTextBeforeCursor(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public java.lang.CharSequence getTextAfterCursor(int i, int i2) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        return this.mTarget.getTextAfterCursor(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public java.lang.CharSequence getSelectedText(int i) {
        return this.mTarget.getSelectedText(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public android.view.inputmethod.SurroundingText getSurroundingText(int i, int i2, int i3) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i2);
        return this.mTarget.getSurroundingText(i, i2, i3);
    }

    @Override // android.view.inputmethod.InputConnection
    public int getCursorCapsMode(int i) {
        return this.mTarget.getCursorCapsMode(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public android.view.inputmethod.ExtractedText getExtractedText(android.view.inputmethod.ExtractedTextRequest extractedTextRequest, int i) {
        return this.mTarget.getExtractedText(extractedTextRequest, i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        return this.mTarget.deleteSurroundingTextInCodePoints(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingText(int i, int i2) {
        return this.mTarget.deleteSurroundingText(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingText(java.lang.CharSequence charSequence, int i) {
        return this.mTarget.setComposingText(charSequence, i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingText(java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
        return this.mTarget.setComposingText(charSequence, i, textAttribute);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int i, int i2) {
        return this.mTarget.setComposingRegion(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int i, int i2, android.view.inputmethod.TextAttribute textAttribute) {
        return this.mTarget.setComposingRegion(i, i2, textAttribute);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean finishComposingText() {
        return this.mTarget.finishComposingText();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitText(java.lang.CharSequence charSequence, int i) {
        return this.mTarget.commitText(charSequence, i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitText(java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
        return this.mTarget.commitText(charSequence, i, textAttribute);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCompletion(android.view.inputmethod.CompletionInfo completionInfo) {
        return this.mTarget.commitCompletion(completionInfo);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCorrection(android.view.inputmethod.CorrectionInfo correctionInfo) {
        return this.mTarget.commitCorrection(correctionInfo);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setSelection(int i, int i2) {
        return this.mTarget.setSelection(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performEditorAction(int i) {
        return this.mTarget.performEditorAction(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performContextMenuAction(int i) {
        return this.mTarget.performContextMenuAction(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean beginBatchEdit() {
        return this.mTarget.beginBatchEdit();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean endBatchEdit() {
        return this.mTarget.endBatchEdit();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean sendKeyEvent(android.view.KeyEvent keyEvent) {
        return this.mTarget.sendKeyEvent(keyEvent);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean clearMetaKeyStates(int i) {
        return this.mTarget.clearMetaKeyStates(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean reportFullscreenMode(boolean z) {
        return this.mTarget.reportFullscreenMode(z);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performSpellCheck() {
        return this.mTarget.performSpellCheck();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
        return this.mTarget.performPrivateCommand(str, bundle);
    }

    @Override // android.view.inputmethod.InputConnection
    public void performHandwritingGesture(android.view.inputmethod.HandwritingGesture handwritingGesture, java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
        this.mTarget.performHandwritingGesture(handwritingGesture, executor, intConsumer);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean previewHandwritingGesture(android.view.inputmethod.PreviewableHandwritingGesture previewableHandwritingGesture, android.os.CancellationSignal cancellationSignal) {
        return this.mTarget.previewHandwritingGesture(previewableHandwritingGesture, cancellationSignal);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i) {
        return this.mTarget.requestCursorUpdates(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i, int i2) {
        return this.mTarget.requestCursorUpdates(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public void requestTextBoundsInfo(android.graphics.RectF rectF, java.util.concurrent.Executor executor, java.util.function.Consumer<android.view.inputmethod.TextBoundsInfoResult> consumer) {
        this.mTarget.requestTextBoundsInfo(rectF, executor, consumer);
    }

    @Override // android.view.inputmethod.InputConnection
    public android.os.Handler getHandler() {
        return this.mTarget.getHandler();
    }

    @Override // android.view.inputmethod.InputConnection
    public void closeConnection() {
        this.mTarget.closeConnection();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitContent(android.view.inputmethod.InputContentInfo inputContentInfo, int i, android.os.Bundle bundle) {
        return this.mTarget.commitContent(inputContentInfo, i, bundle);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setImeConsumesInput(boolean z) {
        return this.mTarget.setImeConsumesInput(z);
    }

    @Override // android.view.inputmethod.InputConnection
    public android.view.inputmethod.TextSnapshot takeSnapshot() {
        return this.mTarget.takeSnapshot();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean replaceText(int i, int i2, java.lang.CharSequence charSequence, int i3, android.view.inputmethod.TextAttribute textAttribute) {
        return this.mTarget.replaceText(i, i2, charSequence, i3, textAttribute);
    }
}
