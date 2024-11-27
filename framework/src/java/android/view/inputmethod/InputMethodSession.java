package android.view.inputmethod;

/* loaded from: classes4.dex */
public interface InputMethodSession {

    public interface EventCallback {
        void finishedEvent(int i, boolean z);
    }

    void appPrivateCommand(java.lang.String str, android.os.Bundle bundle);

    void dispatchGenericMotionEvent(int i, android.view.MotionEvent motionEvent, android.view.inputmethod.InputMethodSession.EventCallback eventCallback);

    void dispatchKeyEvent(int i, android.view.KeyEvent keyEvent, android.view.inputmethod.InputMethodSession.EventCallback eventCallback);

    void dispatchTrackballEvent(int i, android.view.MotionEvent motionEvent, android.view.inputmethod.InputMethodSession.EventCallback eventCallback);

    void displayCompletions(android.view.inputmethod.CompletionInfo[] completionInfoArr);

    void finishInput();

    void removeImeSurface();

    @java.lang.Deprecated
    void toggleSoftInput(int i, int i2);

    void updateCursor(android.graphics.Rect rect);

    void updateCursorAnchorInfo(android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo);

    void updateExtractedText(int i, android.view.inputmethod.ExtractedText extractedText);

    void updateSelection(int i, int i2, int i3, int i4, int i5, int i6);

    void viewClicked(boolean z);

    default void invalidateInputInternal(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) {
    }
}
