package android.view.inputmethod;

/* loaded from: classes4.dex */
final class ViewFocusParameterInfo {
    final android.view.inputmethod.EditorInfo mPreviousEditorInfo;
    final int mPreviousSoftInputMode;
    final int mPreviousStartInputFlags;
    final int mPreviousStartInputReason;
    final int mPreviousWindowFlags;

    ViewFocusParameterInfo(android.view.inputmethod.EditorInfo editorInfo, int i, int i2, int i3, int i4) {
        this.mPreviousEditorInfo = editorInfo;
        this.mPreviousStartInputFlags = i;
        this.mPreviousStartInputReason = i2;
        this.mPreviousSoftInputMode = i3;
        this.mPreviousWindowFlags = i4;
    }

    boolean sameAs(android.view.inputmethod.EditorInfo editorInfo, int i, int i2, int i3, int i4) {
        return this.mPreviousStartInputFlags == i && this.mPreviousStartInputReason == i2 && this.mPreviousSoftInputMode == i3 && this.mPreviousWindowFlags == i4 && (this.mPreviousEditorInfo == editorInfo || (this.mPreviousEditorInfo != null && this.mPreviousEditorInfo.kindofEquals(editorInfo)));
    }
}
