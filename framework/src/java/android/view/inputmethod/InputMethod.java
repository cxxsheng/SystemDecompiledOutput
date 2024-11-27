package android.view.inputmethod;

/* loaded from: classes4.dex */
public interface InputMethod {
    public static final java.lang.String SERVICE_INTERFACE = "android.view.InputMethod";
    public static final java.lang.String SERVICE_META_DATA = "android.view.im";
    public static final int SHOW_EXPLICIT = 1;

    @java.lang.Deprecated
    public static final int SHOW_FORCED = 2;
    public static final java.lang.String TAG = "InputMethod";

    public interface SessionCallback {
        void sessionCreated(android.view.inputmethod.InputMethodSession inputMethodSession);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShowFlags {
    }

    void attachToken(android.os.IBinder iBinder);

    void bindInput(android.view.inputmethod.InputBinding inputBinding);

    void changeInputMethodSubtype(android.view.inputmethod.InputMethodSubtype inputMethodSubtype);

    void createSession(android.view.inputmethod.InputMethod.SessionCallback sessionCallback);

    void hideSoftInput(int i, android.os.ResultReceiver resultReceiver);

    void restartInput(android.view.inputmethod.InputConnection inputConnection, android.view.inputmethod.EditorInfo editorInfo);

    void revokeSession(android.view.inputmethod.InputMethodSession inputMethodSession);

    void setSessionEnabled(android.view.inputmethod.InputMethodSession inputMethodSession, boolean z);

    void showSoftInput(int i, android.os.ResultReceiver resultReceiver);

    void startInput(android.view.inputmethod.InputConnection inputConnection, android.view.inputmethod.EditorInfo editorInfo);

    void unbindInput();

    default void initializeInternal(com.android.internal.inputmethod.IInputMethod.InitParams initParams) {
        attachToken(initParams.token);
    }

    default void onCreateInlineSuggestionsRequest(com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) {
        try {
            iInlineSuggestionsRequestCallback.onInlineSuggestionsUnsupported();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to call onInlineSuggestionsUnsupported.", e);
        }
    }

    default void dispatchStartInput(android.view.inputmethod.InputConnection inputConnection, com.android.internal.inputmethod.IInputMethod.StartInputParams startInputParams) {
        if (startInputParams.restarting) {
            restartInput(inputConnection, startInputParams.editorInfo);
        } else {
            startInput(inputConnection, startInputParams.editorInfo);
        }
    }

    default void onNavButtonFlagsChanged(int i) {
    }

    default void showSoftInputWithToken(int i, android.os.ResultReceiver resultReceiver, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token) {
        showSoftInput(i, resultReceiver);
    }

    default void hideSoftInputWithToken(int i, android.os.ResultReceiver resultReceiver, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token) {
        hideSoftInput(i, resultReceiver);
    }

    default void canStartStylusHandwriting(int i, com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, boolean z) {
    }

    default void updateEditorToolType(int i) {
    }

    default void startStylusHandwriting(int i, android.view.InputChannel inputChannel, java.util.List<android.view.MotionEvent> list) {
    }

    default void commitHandwritingDelegationTextIfAvailable() {
    }

    default void discardHandwritingDelegationText() {
    }

    default void initInkWindow() {
    }

    default void finishStylusHandwriting() {
    }

    default void removeStylusHandwritingWindow() {
    }

    default void setStylusWindowIdleTimeoutForTest(long j) {
    }
}
