package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public final class EditableInputConnection extends android.view.inputmethod.BaseInputConnection implements android.view.inputmethod.DumpableInputConnection {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "EditableInputConnection";
    private int mBatchEditNesting;
    private final android.widget.TextView mTextView;

    public EditableInputConnection(android.widget.TextView textView) {
        super((android.view.View) textView, true);
        this.mTextView = textView;
    }

    @Override // android.view.inputmethod.BaseInputConnection
    public android.text.Editable getEditable() {
        android.widget.TextView textView = this.mTextView;
        if (textView != null) {
            return textView.getEditableText();
        }
        return null;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean beginBatchEdit() {
        synchronized (this) {
            if (this.mBatchEditNesting >= 0) {
                this.mTextView.beginBatchEdit();
                this.mBatchEditNesting++;
                return true;
            }
            return false;
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean endBatchEdit() {
        synchronized (this) {
            if (this.mBatchEditNesting <= 0) {
                return false;
            }
            this.mTextView.endBatchEdit();
            this.mBatchEditNesting--;
            return this.mBatchEditNesting > 0;
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection
    public void endComposingRegionEditInternal() {
        this.mTextView.notifyContentCaptureTextChanged();
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public void closeConnection() {
        super.closeConnection();
        synchronized (this) {
            while (this.mBatchEditNesting > 0) {
                endBatchEdit();
            }
            this.mBatchEditNesting = -1;
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean clearMetaKeyStates(int i) {
        android.text.Editable editable = getEditable();
        if (editable == null) {
            return false;
        }
        android.text.method.KeyListener keyListener = this.mTextView.getKeyListener();
        if (keyListener != null) {
            try {
                keyListener.clearMetaKeyState(this.mTextView, editable, i);
                return true;
            } catch (java.lang.AbstractMethodError e) {
                return true;
            }
        }
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean commitCompletion(android.view.inputmethod.CompletionInfo completionInfo) {
        this.mTextView.beginBatchEdit();
        this.mTextView.onCommitCompletion(completionInfo);
        this.mTextView.endBatchEdit();
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean commitCorrection(android.view.inputmethod.CorrectionInfo correctionInfo) {
        this.mTextView.beginBatchEdit();
        this.mTextView.onCommitCorrection(correctionInfo);
        this.mTextView.endBatchEdit();
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean performEditorAction(int i) {
        this.mTextView.onEditorAction(i);
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean performContextMenuAction(int i) {
        this.mTextView.beginBatchEdit();
        this.mTextView.onTextContextMenuItem(i);
        this.mTextView.endBatchEdit();
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public android.view.inputmethod.ExtractedText getExtractedText(android.view.inputmethod.ExtractedTextRequest extractedTextRequest, int i) {
        if (this.mTextView != null) {
            android.view.inputmethod.ExtractedText extractedText = new android.view.inputmethod.ExtractedText();
            if (this.mTextView.extractText(extractedTextRequest, extractedText)) {
                if ((i & 1) != 0) {
                    this.mTextView.setExtracting(extractedTextRequest);
                }
                return extractedText;
            }
            return null;
        }
        return null;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performSpellCheck() {
        this.mTextView.onPerformSpellCheck();
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean performPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
        this.mTextView.onPrivateIMECommand(str, bundle);
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean commitText(java.lang.CharSequence charSequence, int i) {
        if (this.mTextView == null) {
            return super.commitText(charSequence, i);
        }
        this.mTextView.resetErrorChangedFlag();
        boolean commitText = super.commitText(charSequence, i);
        this.mTextView.hideErrorIfUnchanged();
        return commitText;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i, int i2) {
        return requestCursorUpdates(i | i2);
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i) {
        if ((i & (-128)) != 0 || this.mIMM == null) {
            return false;
        }
        this.mIMM.setUpdateCursorAnchorInfoMode(i);
        if (this.mTextView != null) {
            this.mTextView.onRequestCursorUpdatesInternal(i & 3, i & 124);
            return true;
        }
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public void requestTextBoundsInfo(android.graphics.RectF rectF, java.util.concurrent.Executor executor, final java.util.function.Consumer<android.view.inputmethod.TextBoundsInfoResult> consumer) {
        int i;
        android.view.inputmethod.TextBoundsInfo textBoundsInfo = this.mTextView.getTextBoundsInfo(rectF);
        if (textBoundsInfo != null) {
            i = 1;
        } else {
            i = 2;
        }
        final android.view.inputmethod.TextBoundsInfoResult textBoundsInfoResult = new android.view.inputmethod.TextBoundsInfoResult(i, textBoundsInfo);
        executor.execute(new java.lang.Runnable() { // from class: com.android.internal.inputmethod.EditableInputConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(textBoundsInfoResult);
            }
        });
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setImeConsumesInput(boolean z) {
        if (this.mTextView == null) {
            return super.setImeConsumesInput(z);
        }
        this.mTextView.setImeConsumesInput(z);
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public void performHandwritingGesture(android.view.inputmethod.HandwritingGesture handwritingGesture, java.util.concurrent.Executor executor, final java.util.function.IntConsumer intConsumer) {
        final int i;
        if (handwritingGesture instanceof android.view.inputmethod.SelectGesture) {
            i = this.mTextView.performHandwritingSelectGesture((android.view.inputmethod.SelectGesture) handwritingGesture);
        } else if (handwritingGesture instanceof android.view.inputmethod.SelectRangeGesture) {
            i = this.mTextView.performHandwritingSelectRangeGesture((android.view.inputmethod.SelectRangeGesture) handwritingGesture);
        } else if (handwritingGesture instanceof android.view.inputmethod.DeleteGesture) {
            i = this.mTextView.performHandwritingDeleteGesture((android.view.inputmethod.DeleteGesture) handwritingGesture);
        } else if (handwritingGesture instanceof android.view.inputmethod.DeleteRangeGesture) {
            i = this.mTextView.performHandwritingDeleteRangeGesture((android.view.inputmethod.DeleteRangeGesture) handwritingGesture);
        } else if (handwritingGesture instanceof android.view.inputmethod.InsertGesture) {
            i = this.mTextView.performHandwritingInsertGesture((android.view.inputmethod.InsertGesture) handwritingGesture);
        } else if (handwritingGesture instanceof android.view.inputmethod.RemoveSpaceGesture) {
            i = this.mTextView.performHandwritingRemoveSpaceGesture((android.view.inputmethod.RemoveSpaceGesture) handwritingGesture);
        } else if (handwritingGesture instanceof android.view.inputmethod.JoinOrSplitGesture) {
            i = this.mTextView.performHandwritingJoinOrSplitGesture((android.view.inputmethod.JoinOrSplitGesture) handwritingGesture);
        } else if (handwritingGesture instanceof android.view.inputmethod.InsertModeGesture) {
            i = this.mTextView.performHandwritingInsertModeGesture((android.view.inputmethod.InsertModeGesture) handwritingGesture);
        } else {
            i = 2;
        }
        if (executor != null && intConsumer != null) {
            executor.execute(new java.lang.Runnable() { // from class: com.android.internal.inputmethod.EditableInputConnection$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    intConsumer.accept(i);
                }
            });
        }
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean previewHandwritingGesture(android.view.inputmethod.PreviewableHandwritingGesture previewableHandwritingGesture, android.os.CancellationSignal cancellationSignal) {
        return this.mTextView.previewHandwritingGesture(previewableHandwritingGesture, cancellationSignal);
    }

    @Override // android.view.inputmethod.DumpableInputConnection
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        this.mTextView.getText();
        getSelectedText(0);
        android.text.Editable editable = getEditable();
        if (editable != null) {
            int selectionStart = android.text.Selection.getSelectionStart(editable);
            int selectionEnd = android.text.Selection.getSelectionEnd(editable);
            protoOutputStream.write(1120986464259L, selectionStart);
            protoOutputStream.write(1120986464260L, selectionEnd);
        }
        protoOutputStream.write(1120986464261L, getCursorCapsMode(0));
        protoOutputStream.end(start);
    }
}
