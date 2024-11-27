package android.view.inputmethod;

/* loaded from: classes4.dex */
public interface InputConnection {
    public static final int CURSOR_UPDATE_FILTER_CHARACTER_BOUNDS = 8;
    public static final int CURSOR_UPDATE_FILTER_EDITOR_BOUNDS = 4;
    public static final int CURSOR_UPDATE_FILTER_INSERTION_MARKER = 16;
    public static final int CURSOR_UPDATE_FILTER_TEXT_APPEARANCE = 64;
    public static final int CURSOR_UPDATE_FILTER_VISIBLE_LINE_BOUNDS = 32;
    public static final int CURSOR_UPDATE_IMMEDIATE = 1;
    public static final int CURSOR_UPDATE_MONITOR = 2;
    public static final int GET_EXTRACTED_TEXT_MONITOR = 1;
    public static final int GET_TEXT_WITH_STYLES = 1;
    public static final int HANDWRITING_GESTURE_RESULT_CANCELLED = 4;
    public static final int HANDWRITING_GESTURE_RESULT_FAILED = 3;
    public static final int HANDWRITING_GESTURE_RESULT_FALLBACK = 5;
    public static final int HANDWRITING_GESTURE_RESULT_SUCCESS = 1;
    public static final int HANDWRITING_GESTURE_RESULT_UNKNOWN = 0;
    public static final int HANDWRITING_GESTURE_RESULT_UNSUPPORTED = 2;
    public static final int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CursorUpdateFilter {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CursorUpdateMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GetTextType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HandwritingGestureResult {
    }

    boolean beginBatchEdit();

    boolean clearMetaKeyStates(int i);

    void closeConnection();

    boolean commitCompletion(android.view.inputmethod.CompletionInfo completionInfo);

    boolean commitContent(android.view.inputmethod.InputContentInfo inputContentInfo, int i, android.os.Bundle bundle);

    boolean commitCorrection(android.view.inputmethod.CorrectionInfo correctionInfo);

    boolean commitText(java.lang.CharSequence charSequence, int i);

    boolean deleteSurroundingText(int i, int i2);

    boolean deleteSurroundingTextInCodePoints(int i, int i2);

    boolean endBatchEdit();

    boolean finishComposingText();

    int getCursorCapsMode(int i);

    android.view.inputmethod.ExtractedText getExtractedText(android.view.inputmethod.ExtractedTextRequest extractedTextRequest, int i);

    android.os.Handler getHandler();

    java.lang.CharSequence getSelectedText(int i);

    java.lang.CharSequence getTextAfterCursor(int i, int i2);

    java.lang.CharSequence getTextBeforeCursor(int i, int i2);

    boolean performContextMenuAction(int i);

    boolean performEditorAction(int i);

    boolean performPrivateCommand(java.lang.String str, android.os.Bundle bundle);

    boolean reportFullscreenMode(boolean z);

    boolean requestCursorUpdates(int i);

    boolean sendKeyEvent(android.view.KeyEvent keyEvent);

    boolean setComposingRegion(int i, int i2);

    boolean setComposingText(java.lang.CharSequence charSequence, int i);

    boolean setSelection(int i, int i2);

    default android.view.inputmethod.SurroundingText getSurroundingText(int i, int i2, int i3) {
        java.lang.CharSequence textAfterCursor;
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i2);
        java.lang.CharSequence textBeforeCursor = getTextBeforeCursor(i, i3);
        if (textBeforeCursor == null || (textAfterCursor = getTextAfterCursor(i2, i3)) == null) {
            return null;
        }
        java.lang.CharSequence selectedText = getSelectedText(i3);
        if (selectedText == null) {
            selectedText = "";
        }
        return new android.view.inputmethod.SurroundingText(android.text.TextUtils.concat(textBeforeCursor, selectedText, textAfterCursor), textBeforeCursor.length(), textBeforeCursor.length() + selectedText.length(), -1);
    }

    default boolean setComposingText(java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
        return setComposingText(charSequence, i);
    }

    default boolean setComposingRegion(int i, int i2, android.view.inputmethod.TextAttribute textAttribute) {
        return setComposingRegion(i, i2);
    }

    default boolean commitText(java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
        return commitText(charSequence, i);
    }

    default boolean performSpellCheck() {
        return false;
    }

    default void performHandwritingGesture(android.view.inputmethod.HandwritingGesture handwritingGesture, java.util.concurrent.Executor executor, final java.util.function.IntConsumer intConsumer) {
        if (executor != null && intConsumer != null) {
            executor.execute(new java.lang.Runnable() { // from class: android.view.inputmethod.InputConnection$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    intConsumer.accept(2);
                }
            });
        }
    }

    default boolean previewHandwritingGesture(android.view.inputmethod.PreviewableHandwritingGesture previewableHandwritingGesture, android.os.CancellationSignal cancellationSignal) {
        return false;
    }

    default boolean requestCursorUpdates(int i, int i2) {
        if (i2 == 0) {
            return requestCursorUpdates(i);
        }
        return false;
    }

    default void requestTextBoundsInfo(android.graphics.RectF rectF, java.util.concurrent.Executor executor, final java.util.function.Consumer<android.view.inputmethod.TextBoundsInfoResult> consumer) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        executor.execute(new java.lang.Runnable() { // from class: android.view.inputmethod.InputConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(new android.view.inputmethod.TextBoundsInfoResult(0));
            }
        });
    }

    default boolean setImeConsumesInput(boolean z) {
        return false;
    }

    default android.view.inputmethod.TextSnapshot takeSnapshot() {
        return null;
    }

    default boolean replaceText(int i, int i2, java.lang.CharSequence charSequence, int i3, android.view.inputmethod.TextAttribute textAttribute) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i2);
        beginBatchEdit();
        finishComposingText();
        setSelection(i, i2);
        commitText(charSequence, i3, textAttribute);
        endBatchEdit();
        return true;
    }
}
