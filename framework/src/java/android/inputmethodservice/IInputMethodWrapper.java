package android.inputmethodservice;

/* loaded from: classes2.dex */
class IInputMethodWrapper extends com.android.internal.inputmethod.IInputMethod.Stub implements com.android.internal.os.HandlerCaller.Callback {
    private static final int DO_CAN_START_STYLUS_HANDWRITING = 100;
    private static final int DO_CHANGE_INPUTMETHOD_SUBTYPE = 80;
    private static final int DO_COMMIT_HANDWRITING_DELEGATION_TEXT_IF_AVAILABLE = 170;
    private static final int DO_CREATE_INLINE_SUGGESTIONS_REQUEST = 90;
    private static final int DO_CREATE_SESSION = 40;
    private static final int DO_DISCARD_HANDWRITING_DELEGATION_TEXT = 180;
    private static final int DO_DUMP = 1;
    private static final int DO_FINISH_STYLUS_HANDWRITING = 130;
    private static final int DO_HIDE_SOFT_INPUT = 70;
    private static final int DO_INITIALIZE_INTERNAL = 10;
    private static final int DO_INIT_INK_WINDOW = 120;
    private static final int DO_ON_NAV_BUTTON_FLAGS_CHANGED = 35;
    private static final int DO_REMOVE_STYLUS_HANDWRITING_WINDOW = 150;
    private static final int DO_SET_INPUT_CONTEXT = 20;
    private static final int DO_SET_SESSION_ENABLED = 45;
    private static final int DO_SET_STYLUS_WINDOW_IDLE_TIMEOUT = 160;
    private static final int DO_SHOW_SOFT_INPUT = 60;
    private static final int DO_START_INPUT = 32;
    private static final int DO_START_STYLUS_HANDWRITING = 110;
    private static final int DO_UNSET_INPUT_CONTEXT = 30;
    private static final int DO_UPDATE_TOOL_TYPE = 140;
    private static final java.lang.String TAG = "InputMethodWrapper";
    final com.android.internal.os.HandlerCaller mCaller;
    com.android.internal.inputmethod.CancellationGroup mCancellationGroup = null;
    final android.content.Context mContext;
    final java.lang.ref.WeakReference<android.view.inputmethod.InputMethod> mInputMethod;
    final java.lang.ref.WeakReference<android.inputmethodservice.InputMethodServiceInternal> mTarget;
    final int mTargetSdkVersion;

    static final class InputMethodSessionCallbackWrapper implements android.view.inputmethod.InputMethod.SessionCallback {
        final com.android.internal.inputmethod.IInputMethodSessionCallback mCb;
        final android.view.InputChannel mChannel;
        final android.content.Context mContext;

        InputMethodSessionCallbackWrapper(android.content.Context context, android.view.InputChannel inputChannel, com.android.internal.inputmethod.IInputMethodSessionCallback iInputMethodSessionCallback) {
            this.mContext = context;
            this.mChannel = inputChannel;
            this.mCb = iInputMethodSessionCallback;
        }

        @Override // android.view.inputmethod.InputMethod.SessionCallback
        public void sessionCreated(android.view.inputmethod.InputMethodSession inputMethodSession) {
            try {
                if (inputMethodSession != null) {
                    this.mCb.sessionCreated(new android.inputmethodservice.IInputMethodSessionWrapper(this.mContext, inputMethodSession, this.mChannel));
                } else {
                    if (this.mChannel != null) {
                        this.mChannel.dispose();
                    }
                    this.mCb.sessionCreated(null);
                }
            } catch (android.os.RemoteException e) {
            }
        }
    }

    IInputMethodWrapper(android.inputmethodservice.InputMethodServiceInternal inputMethodServiceInternal, android.view.inputmethod.InputMethod inputMethod) {
        this.mTarget = new java.lang.ref.WeakReference<>(inputMethodServiceInternal);
        this.mContext = inputMethodServiceInternal.getContext().getApplicationContext();
        this.mCaller = new com.android.internal.os.HandlerCaller(this.mContext, null, this, true);
        this.mInputMethod = new java.lang.ref.WeakReference<>(inputMethod);
        this.mTargetSdkVersion = inputMethodServiceInternal.getContext().getApplicationInfo().targetSdkVersion;
    }

    @Override // com.android.internal.os.HandlerCaller.Callback
    public void executeMessage(android.os.Message message) {
        android.view.inputmethod.InputMethod inputMethod = this.mInputMethod.get();
        android.inputmethodservice.InputMethodServiceInternal inputMethodServiceInternal = this.mTarget.get();
        switch (message.what) {
            case 1:
                com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_DUMP")) {
                    java.io.FileDescriptor fileDescriptor = (java.io.FileDescriptor) someArgs.arg1;
                    java.io.PrintWriter printWriter = (java.io.PrintWriter) someArgs.arg2;
                    java.lang.String[] strArr = (java.lang.String[]) someArgs.arg3;
                    java.util.concurrent.CountDownLatch countDownLatch = (java.util.concurrent.CountDownLatch) someArgs.arg4;
                    try {
                        try {
                            inputMethodServiceInternal.dump(fileDescriptor, printWriter, strArr);
                        } catch (java.lang.RuntimeException e) {
                            printWriter.println("Exception: " + e);
                        }
                    } finally {
                        countDownLatch.countDown();
                    }
                }
                someArgs.recycle();
                return;
            case 10:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_INITIALIZE_INTERNAL")) {
                    inputMethod.initializeInternal((com.android.internal.inputmethod.IInputMethod.InitParams) message.obj);
                    return;
                }
                return;
            case 20:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_SET_INPUT_CONTEXT")) {
                    inputMethod.bindInput((android.view.inputmethod.InputBinding) message.obj);
                    return;
                }
                return;
            case 30:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_UNSET_INPUT_CONTEXT")) {
                    inputMethod.unbindInput();
                    return;
                }
                return;
            case 32:
                com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_START_INPUT")) {
                    inputMethod.dispatchStartInput((android.view.inputmethod.InputConnection) someArgs2.arg1, (com.android.internal.inputmethod.IInputMethod.StartInputParams) someArgs2.arg2);
                }
                someArgs2.recycle();
                return;
            case 35:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_ON_NAV_BUTTON_FLAGS_CHANGED")) {
                    inputMethod.onNavButtonFlagsChanged(message.arg1);
                    return;
                }
                return;
            case 40:
                com.android.internal.os.SomeArgs someArgs3 = (com.android.internal.os.SomeArgs) message.obj;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_CREATE_SESSION")) {
                    inputMethod.createSession(new android.inputmethodservice.IInputMethodWrapper.InputMethodSessionCallbackWrapper(this.mContext, (android.view.InputChannel) someArgs3.arg1, (com.android.internal.inputmethod.IInputMethodSessionCallback) someArgs3.arg2));
                }
                someArgs3.recycle();
                return;
            case 45:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_SET_SESSION_ENABLED")) {
                    inputMethod.setSessionEnabled((android.view.inputmethod.InputMethodSession) message.obj, message.arg1 != 0);
                    return;
                }
                return;
            case 60:
                com.android.internal.os.SomeArgs someArgs4 = (com.android.internal.os.SomeArgs) message.obj;
                android.view.inputmethod.ImeTracker.Token token = (android.view.inputmethod.ImeTracker.Token) someArgs4.arg3;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_SHOW_SOFT_INPUT")) {
                    android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 12);
                    inputMethod.showSoftInputWithToken(message.arg1, (android.os.ResultReceiver) someArgs4.arg2, (android.os.IBinder) someArgs4.arg1, token);
                } else {
                    android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 12);
                }
                someArgs4.recycle();
                return;
            case 70:
                com.android.internal.os.SomeArgs someArgs5 = (com.android.internal.os.SomeArgs) message.obj;
                android.view.inputmethod.ImeTracker.Token token2 = (android.view.inputmethod.ImeTracker.Token) someArgs5.arg3;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_HIDE_SOFT_INPUT")) {
                    android.view.inputmethod.ImeTracker.forLogging().onProgress(token2, 12);
                    inputMethod.hideSoftInputWithToken(message.arg1, (android.os.ResultReceiver) someArgs5.arg2, (android.os.IBinder) someArgs5.arg1, token2);
                } else {
                    android.view.inputmethod.ImeTracker.forLogging().onFailed(token2, 12);
                }
                someArgs5.recycle();
                return;
            case 80:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_CHANGE_INPUTMETHOD_SUBTYPE")) {
                    inputMethod.changeInputMethodSubtype((android.view.inputmethod.InputMethodSubtype) message.obj);
                    return;
                }
                return;
            case 90:
                com.android.internal.os.SomeArgs someArgs6 = (com.android.internal.os.SomeArgs) message.obj;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_CREATE_INLINE_SUGGESTIONS_REQUEST")) {
                    inputMethod.onCreateInlineSuggestionsRequest((com.android.internal.inputmethod.InlineSuggestionsRequestInfo) someArgs6.arg1, (com.android.internal.inputmethod.IInlineSuggestionsRequestCallback) someArgs6.arg2);
                }
                someArgs6.recycle();
                return;
            case 100:
                com.android.internal.os.SomeArgs someArgs7 = (com.android.internal.os.SomeArgs) message.obj;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_CAN_START_STYLUS_HANDWRITING")) {
                    inputMethod.canStartStylusHandwriting(message.arg1, (com.android.internal.inputmethod.IConnectionlessHandwritingCallback) someArgs7.arg1, (android.view.inputmethod.CursorAnchorInfo) someArgs7.arg2, message.arg2 != 0);
                }
                someArgs7.recycle();
                return;
            case 110:
                com.android.internal.os.SomeArgs someArgs8 = (com.android.internal.os.SomeArgs) message.obj;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_START_STYLUS_HANDWRITING")) {
                    inputMethod.startStylusHandwriting(message.arg1, (android.view.InputChannel) someArgs8.arg1, (java.util.List) someArgs8.arg2);
                }
                someArgs8.recycle();
                return;
            case 120:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_INIT_INK_WINDOW")) {
                    inputMethod.initInkWindow();
                    return;
                }
                return;
            case 130:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_FINISH_STYLUS_HANDWRITING")) {
                    inputMethod.finishStylusHandwriting();
                    return;
                }
                return;
            case 140:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_UPDATE_TOOL_TYPE")) {
                    inputMethod.updateEditorToolType(message.arg1);
                    return;
                }
                return;
            case 150:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_REMOVE_STYLUS_HANDWRITING_WINDOW")) {
                    inputMethod.removeStylusHandwritingWindow();
                    return;
                }
                return;
            case 160:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_SET_STYLUS_WINDOW_IDLE_TIMEOUT")) {
                    inputMethod.setStylusWindowIdleTimeoutForTest(((java.lang.Long) message.obj).longValue());
                    return;
                }
                return;
            case 170:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_COMMIT_HANDWRITING_DELEGATION_TEXT_IF_AVAILABLE")) {
                    inputMethod.commitHandwritingDelegationTextIfAvailable();
                    return;
                }
                return;
            case 180:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_DISCARD_HANDWRITING_DELEGATION_TEXT")) {
                    inputMethod.discardHandwritingDelegationText();
                    return;
                }
                return;
            default:
                android.util.Log.w(TAG, "Unhandled message code: " + message.what);
                return;
        }
    }

    @Override // android.os.Binder
    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        android.inputmethodservice.InputMethodServiceInternal inputMethodServiceInternal = this.mTarget.get();
        if (inputMethodServiceInternal == null) {
            return;
        }
        if (inputMethodServiceInternal.getContext().checkCallingOrSelfPermission(android.Manifest.permission.DUMP) != 0) {
            printWriter.println("Permission Denial: can't dump InputMethodManager from from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid());
            return;
        }
        java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        this.mCaller.getHandler().sendMessageAtFrontOfQueue(this.mCaller.obtainMessageOOOO(1, fileDescriptor, printWriter, strArr, countDownLatch));
        try {
            if (!countDownLatch.await(5L, java.util.concurrent.TimeUnit.SECONDS)) {
                printWriter.println("Timeout waiting for dump");
            }
        } catch (java.lang.InterruptedException e) {
            printWriter.println("Interrupted waiting for dump");
        }
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void initializeInternal(com.android.internal.inputmethod.IInputMethod.InitParams initParams) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(10, initParams));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void onCreateInlineSuggestionsRequest(com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(90, inlineSuggestionsRequestInfo, iInlineSuggestionsRequestCallback));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void bindInput(android.view.inputmethod.InputBinding inputBinding) {
        if (this.mCancellationGroup != null) {
            android.util.Log.e(TAG, "bindInput must be paired with unbindInput.");
        }
        this.mCancellationGroup = new com.android.internal.inputmethod.CancellationGroup();
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(20, new android.view.inputmethod.InputBinding(new android.inputmethodservice.RemoteInputConnection(this.mTarget, com.android.internal.inputmethod.IRemoteInputConnection.Stub.asInterface(inputBinding.getConnectionToken()), this.mCancellationGroup), inputBinding)));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void unbindInput() {
        if (this.mCancellationGroup != null) {
            this.mCancellationGroup.cancelAll();
            this.mCancellationGroup = null;
        } else {
            android.util.Log.e(TAG, "unbindInput must be paired with bindInput.");
        }
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(30));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void startInput(com.android.internal.inputmethod.IInputMethod.StartInputParams startInputParams) {
        if (this.mCancellationGroup == null) {
            android.util.Log.e(TAG, "startInput must be called after bindInput.");
            this.mCancellationGroup = new com.android.internal.inputmethod.CancellationGroup();
        }
        startInputParams.editorInfo.makeCompatible(this.mTargetSdkVersion);
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(32, startInputParams.remoteInputConnection == null ? null : new android.inputmethodservice.RemoteInputConnection(this.mTarget, startInputParams.remoteInputConnection, this.mCancellationGroup), startInputParams));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void onNavButtonFlagsChanged(int i) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageI(35, i));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void createSession(android.view.InputChannel inputChannel, com.android.internal.inputmethod.IInputMethodSessionCallback iInputMethodSessionCallback) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(40, inputChannel, iInputMethodSessionCallback));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void setSessionEnabled(com.android.internal.inputmethod.IInputMethodSession iInputMethodSession, boolean z) {
        try {
            android.view.inputmethod.InputMethodSession internalInputMethodSession = ((android.inputmethodservice.IInputMethodSessionWrapper) iInputMethodSession).getInternalInputMethodSession();
            if (internalInputMethodSession == null) {
                android.util.Log.w(TAG, "Session is already finished: " + iInputMethodSession);
            } else {
                this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIO(45, z ? 1 : 0, internalInputMethodSession));
            }
        } catch (java.lang.ClassCastException e) {
            android.util.Log.w(TAG, "Incoming session not of correct type: " + iInputMethodSession, e);
        }
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void showSoftInput(android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver) {
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 11);
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIOOO(60, i, iBinder, resultReceiver, token));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void hideSoftInput(android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver) {
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 11);
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIOOO(70, i, iBinder, resultReceiver, token));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void changeInputMethodSubtype(android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(80, inputMethodSubtype));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void canStartStylusHandwriting(int i, com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, boolean z) throws android.os.RemoteException {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIIOO(100, i, z ? 1 : 0, iConnectionlessHandwritingCallback, cursorAnchorInfo));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void updateEditorToolType(int i) throws android.os.RemoteException {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageI(140, i));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void startStylusHandwriting(int i, android.view.InputChannel inputChannel, java.util.List<android.view.MotionEvent> list) throws android.os.RemoteException {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIOO(110, i, inputChannel, list));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void commitHandwritingDelegationTextIfAvailable() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(170));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void discardHandwritingDelegationText() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(180));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void initInkWindow() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(120));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void finishStylusHandwriting() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(130));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void removeStylusHandwritingWindow() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(150));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void setStylusWindowIdleTimeoutForTest(long j) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(160, java.lang.Long.valueOf(j)));
    }

    private static boolean isValid(android.view.inputmethod.InputMethod inputMethod, android.inputmethodservice.InputMethodServiceInternal inputMethodServiceInternal, java.lang.String str) {
        if (inputMethod != null && inputMethodServiceInternal != null && !inputMethodServiceInternal.isServiceDestroyed()) {
            return true;
        }
        android.util.Log.w(TAG, "Ignoring " + str + ", InputMethod:" + inputMethod + ", InputMethodServiceInternal:" + inputMethodServiceInternal);
        return false;
    }
}
