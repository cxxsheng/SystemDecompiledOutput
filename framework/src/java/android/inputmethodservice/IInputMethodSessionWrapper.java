package android.inputmethodservice;

/* loaded from: classes2.dex */
class IInputMethodSessionWrapper extends com.android.internal.inputmethod.IInputMethodSession.Stub implements com.android.internal.os.HandlerCaller.Callback {
    private static final int DO_APP_PRIVATE_COMMAND = 100;
    private static final int DO_DISPLAY_COMPLETIONS = 65;
    private static final int DO_FINISH_INPUT = 140;
    private static final int DO_FINISH_SESSION = 110;
    private static final int DO_INVALIDATE_INPUT = 150;
    private static final int DO_REMOVE_IME_SURFACE = 130;
    private static final int DO_UPDATE_CURSOR = 95;
    private static final int DO_UPDATE_CURSOR_ANCHOR_INFO = 99;
    private static final int DO_UPDATE_EXTRACTED_TEXT = 67;
    private static final int DO_UPDATE_SELECTION = 90;
    private static final int DO_VIEW_CLICKED = 115;
    private static final java.lang.String TAG = "InputMethodWrapper";
    com.android.internal.os.HandlerCaller mCaller;
    android.view.InputChannel mChannel;
    android.view.inputmethod.InputMethodSession mInputMethodSession;
    android.inputmethodservice.IInputMethodSessionWrapper.ImeInputEventReceiver mReceiver;

    public IInputMethodSessionWrapper(android.content.Context context, android.view.inputmethod.InputMethodSession inputMethodSession, android.view.InputChannel inputChannel) {
        this.mCaller = new com.android.internal.os.HandlerCaller(context, null, this, true);
        this.mInputMethodSession = inputMethodSession;
        this.mChannel = inputChannel;
        if (inputChannel != null) {
            this.mReceiver = new android.inputmethodservice.IInputMethodSessionWrapper.ImeInputEventReceiver(inputChannel, context.getMainLooper());
        }
    }

    public android.view.inputmethod.InputMethodSession getInternalInputMethodSession() {
        return this.mInputMethodSession;
    }

    @Override // com.android.internal.os.HandlerCaller.Callback
    public void executeMessage(android.os.Message message) {
        com.android.internal.os.SomeArgs someArgs;
        if (this.mInputMethodSession == null) {
            switch (message.what) {
                case 90:
                case 100:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    return;
                default:
                    return;
            }
        }
        switch (message.what) {
            case 65:
                this.mInputMethodSession.displayCompletions((android.view.inputmethod.CompletionInfo[]) message.obj);
                return;
            case 67:
                this.mInputMethodSession.updateExtractedText(message.arg1, (android.view.inputmethod.ExtractedText) message.obj);
                return;
            case 90:
                someArgs = (com.android.internal.os.SomeArgs) message.obj;
                this.mInputMethodSession.updateSelection(someArgs.argi1, someArgs.argi2, someArgs.argi3, someArgs.argi4, someArgs.argi5, someArgs.argi6);
                return;
            case 95:
                this.mInputMethodSession.updateCursor((android.graphics.Rect) message.obj);
                return;
            case 99:
                this.mInputMethodSession.updateCursorAnchorInfo((android.view.inputmethod.CursorAnchorInfo) message.obj);
                return;
            case 100:
                someArgs = (com.android.internal.os.SomeArgs) message.obj;
                this.mInputMethodSession.appPrivateCommand((java.lang.String) someArgs.arg1, (android.os.Bundle) someArgs.arg2);
                return;
            case 110:
                doFinishSession();
                return;
            case 115:
                this.mInputMethodSession.viewClicked(message.arg1 == 1);
                return;
            case 130:
                this.mInputMethodSession.removeImeSurface();
                return;
            case 140:
                this.mInputMethodSession.finishInput();
                return;
            case 150:
                someArgs = (com.android.internal.os.SomeArgs) message.obj;
                try {
                    this.mInputMethodSession.invalidateInputInternal((android.view.inputmethod.EditorInfo) someArgs.arg1, (com.android.internal.inputmethod.IRemoteInputConnection) someArgs.arg2, message.arg1);
                    return;
                } finally {
                    someArgs.recycle();
                }
            default:
                android.util.Log.w(TAG, "Unhandled message code: " + message.what);
                return;
        }
    }

    private void doFinishSession() {
        this.mInputMethodSession = null;
        if (this.mReceiver != null) {
            this.mReceiver.dispose();
            this.mReceiver = null;
        }
        if (this.mChannel != null) {
            this.mChannel.dispose();
            this.mChannel = null;
        }
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void displayCompletions(android.view.inputmethod.CompletionInfo[] completionInfoArr) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(65, completionInfoArr));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void updateExtractedText(int i, android.view.inputmethod.ExtractedText extractedText) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIO(67, i, extractedText));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIIIIII(90, i, i2, i3, i4, i5, i6));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void viewClicked(boolean z) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageI(115, z ? 1 : 0));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void removeImeSurface() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(130));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void updateCursor(android.graphics.Rect rect) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(95, rect));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void updateCursorAnchorInfo(android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(99, cursorAnchorInfo));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void appPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(100, str, bundle));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void finishSession() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(110));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void invalidateInput(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIOO(150, i, editorInfo, iRemoteInputConnection));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void finishInput() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(140));
    }

    private final class ImeInputEventReceiver extends android.view.InputEventReceiver implements android.view.inputmethod.InputMethodSession.EventCallback {
        private final android.util.SparseArray<android.view.InputEvent> mPendingEvents;

        public ImeInputEventReceiver(android.view.InputChannel inputChannel, android.os.Looper looper) {
            super(inputChannel, looper);
            this.mPendingEvents = new android.util.SparseArray<>();
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(android.view.InputEvent inputEvent) {
            if (android.inputmethodservice.IInputMethodSessionWrapper.this.mInputMethodSession == null) {
                finishInputEvent(inputEvent, false);
                return;
            }
            int sequenceNumber = inputEvent.getSequenceNumber();
            this.mPendingEvents.put(sequenceNumber, inputEvent);
            if (inputEvent instanceof android.view.KeyEvent) {
                android.inputmethodservice.IInputMethodSessionWrapper.this.mInputMethodSession.dispatchKeyEvent(sequenceNumber, (android.view.KeyEvent) inputEvent, this);
                return;
            }
            android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
            if (motionEvent.isFromSource(4)) {
                android.inputmethodservice.IInputMethodSessionWrapper.this.mInputMethodSession.dispatchTrackballEvent(sequenceNumber, motionEvent, this);
            } else {
                android.inputmethodservice.IInputMethodSessionWrapper.this.mInputMethodSession.dispatchGenericMotionEvent(sequenceNumber, motionEvent, this);
            }
        }

        @Override // android.view.inputmethod.InputMethodSession.EventCallback
        public void finishedEvent(int i, boolean z) {
            int indexOfKey = this.mPendingEvents.indexOfKey(i);
            if (indexOfKey >= 0) {
                android.view.InputEvent valueAt = this.mPendingEvents.valueAt(indexOfKey);
                this.mPendingEvents.removeAt(indexOfKey);
                finishInputEvent(valueAt, z);
            }
        }
    }
}
