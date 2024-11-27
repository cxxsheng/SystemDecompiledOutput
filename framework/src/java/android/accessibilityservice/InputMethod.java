package android.accessibilityservice;

/* loaded from: classes.dex */
public class InputMethod {
    private static final java.lang.String LOG_TAG = "A11yInputMethod";
    private android.view.inputmethod.EditorInfo mInputEditorInfo;
    private boolean mInputStarted;
    private final android.accessibilityservice.AccessibilityService mService;
    private com.android.internal.inputmethod.RemoteAccessibilityInputConnection mStartedInputConnection;

    public InputMethod(android.accessibilityservice.AccessibilityService accessibilityService) {
        this.mService = accessibilityService;
    }

    public final android.accessibilityservice.InputMethod.AccessibilityInputConnection getCurrentInputConnection() {
        if (this.mStartedInputConnection != null) {
            return new android.accessibilityservice.InputMethod.AccessibilityInputConnection(this.mStartedInputConnection);
        }
        return null;
    }

    public final boolean getCurrentInputStarted() {
        return this.mInputStarted;
    }

    public final android.view.inputmethod.EditorInfo getCurrentInputEditorInfo() {
        return this.mInputEditorInfo;
    }

    public void onStartInput(android.view.inputmethod.EditorInfo editorInfo, boolean z) {
    }

    public void onFinishInput() {
    }

    public void onUpdateSelection(int i, int i2, int i3, int i4, int i5, int i6) {
    }

    final void createImeSession(com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) {
        try {
            iAccessibilityInputMethodSessionCallback.sessionCreated(new android.accessibilityservice.AccessibilityInputMethodSessionWrapper(this.mService.getMainLooper(), new android.accessibilityservice.InputMethod.SessionImpl()), this.mService.getConnectionId());
        } catch (android.os.RemoteException e) {
        }
    }

    final void startInput(com.android.internal.inputmethod.RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo) {
        android.util.Log.v(LOG_TAG, "startInput(): editor=" + editorInfo);
        android.os.Trace.traceBegin(32L, "AccessibilityService.startInput");
        doStartInput(remoteAccessibilityInputConnection, editorInfo, false);
        android.os.Trace.traceEnd(32L);
    }

    final void restartInput(com.android.internal.inputmethod.RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo) {
        android.util.Log.v(LOG_TAG, "restartInput(): editor=" + editorInfo);
        android.os.Trace.traceBegin(32L, "AccessibilityService.restartInput");
        doStartInput(remoteAccessibilityInputConnection, editorInfo, true);
        android.os.Trace.traceEnd(32L);
    }

    final void doStartInput(com.android.internal.inputmethod.RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
        if ((remoteAccessibilityInputConnection == null || !z) && this.mInputStarted) {
            doFinishInput();
            if (remoteAccessibilityInputConnection == null) {
                return;
            }
        }
        this.mInputStarted = true;
        this.mStartedInputConnection = remoteAccessibilityInputConnection;
        this.mInputEditorInfo = editorInfo;
        android.util.Log.v(LOG_TAG, "CALL: onStartInput");
        onStartInput(editorInfo, z);
    }

    final void doFinishInput() {
        android.util.Log.v(LOG_TAG, "CALL: doFinishInput");
        if (this.mInputStarted) {
            android.util.Log.v(LOG_TAG, "CALL: onFinishInput");
            onFinishInput();
        }
        this.mInputStarted = false;
        this.mStartedInputConnection = null;
        this.mInputEditorInfo = null;
    }

    public final class AccessibilityInputConnection {
        private final com.android.internal.inputmethod.RemoteAccessibilityInputConnection mIc;

        AccessibilityInputConnection(com.android.internal.inputmethod.RemoteAccessibilityInputConnection remoteAccessibilityInputConnection) {
            this.mIc = remoteAccessibilityInputConnection;
        }

        public void commitText(java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
            if (this.mIc != null) {
                this.mIc.commitText(charSequence, i, textAttribute);
            }
        }

        public void setSelection(int i, int i2) {
            if (this.mIc != null) {
                this.mIc.setSelection(i, i2);
            }
        }

        public android.view.inputmethod.SurroundingText getSurroundingText(int i, int i2, int i3) {
            if (this.mIc != null) {
                return this.mIc.getSurroundingText(i, i2, i3);
            }
            return null;
        }

        public void deleteSurroundingText(int i, int i2) {
            if (this.mIc != null) {
                this.mIc.deleteSurroundingText(i, i2);
            }
        }

        public void sendKeyEvent(android.view.KeyEvent keyEvent) {
            if (this.mIc != null) {
                this.mIc.sendKeyEvent(keyEvent);
            }
        }

        public void performEditorAction(int i) {
            if (this.mIc != null) {
                this.mIc.performEditorAction(i);
            }
        }

        public void performContextMenuAction(int i) {
            if (this.mIc != null) {
                this.mIc.performContextMenuAction(i);
            }
        }

        public int getCursorCapsMode(int i) {
            if (this.mIc != null) {
                return this.mIc.getCursorCapsMode(i);
            }
            return 0;
        }

        public void clearMetaKeyStates(int i) {
            if (this.mIc != null) {
                this.mIc.clearMetaKeyStates(i);
            }
        }
    }

    private final class SessionImpl implements android.accessibilityservice.AccessibilityInputMethodSession {
        boolean mEnabled;

        private SessionImpl() {
            this.mEnabled = true;
        }

        @Override // android.accessibilityservice.AccessibilityInputMethodSession
        public void setEnabled(boolean z) {
            this.mEnabled = z;
        }

        @Override // android.accessibilityservice.AccessibilityInputMethodSession
        public void finishInput() {
            if (this.mEnabled) {
                android.accessibilityservice.InputMethod.this.doFinishInput();
            }
        }

        @Override // android.accessibilityservice.AccessibilityInputMethodSession
        public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) {
            if (this.mEnabled) {
                android.accessibilityservice.InputMethod.this.onUpdateSelection(i2, i2, i3, i4, i5, i6);
            }
        }

        @Override // android.accessibilityservice.AccessibilityInputMethodSession
        public void invalidateInput(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) {
            if (!this.mEnabled || android.accessibilityservice.InputMethod.this.mStartedInputConnection == null || !android.accessibilityservice.InputMethod.this.mStartedInputConnection.isSameConnection(iRemoteAccessibilityInputConnection)) {
                return;
            }
            editorInfo.makeCompatible(android.accessibilityservice.InputMethod.this.mService.getApplicationInfo().targetSdkVersion);
            android.accessibilityservice.InputMethod.this.restartInput(new com.android.internal.inputmethod.RemoteAccessibilityInputConnection(android.accessibilityservice.InputMethod.this.mStartedInputConnection, i), editorInfo);
        }
    }
}
