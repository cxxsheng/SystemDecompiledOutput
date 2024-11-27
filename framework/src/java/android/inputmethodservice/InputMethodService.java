package android.inputmethodservice;

/* loaded from: classes2.dex */
public class InputMethodService extends android.inputmethodservice.AbstractInputMethodService {
    public static final int BACK_DISPOSITION_ADJUST_NOTHING = 3;
    public static final int BACK_DISPOSITION_DEFAULT = 0;
    private static final int BACK_DISPOSITION_MAX = 3;
    private static final int BACK_DISPOSITION_MIN = 0;

    @java.lang.Deprecated
    public static final int BACK_DISPOSITION_WILL_DISMISS = 2;

    @java.lang.Deprecated
    public static final int BACK_DISPOSITION_WILL_NOT_DISMISS = 1;
    static final boolean DEBUG = false;
    public static final long DISALLOW_INPUT_METHOD_INTERFACE_OVERRIDE = 148086656;
    public static final boolean ENABLE_HIDE_IME_CAPTION_BAR = true;
    public static final long FINISH_INPUT_NO_FALLBACK_CONNECTION = 156215187;
    public static final int IME_ACTIVE = 1;
    public static final int IME_INVISIBLE = 4;
    public static final int IME_VISIBLE = 2;
    public static final int IME_VISIBLE_IMPERCEPTIBLE = 8;
    private static final int MAX_EVENTS_BUFFER = 500;
    static final int MOVEMENT_DOWN = -1;
    static final int MOVEMENT_UP = -2;
    private static final java.lang.String PROP_CAN_RENDER_GESTURAL_NAV_BUTTONS = "persist.sys.ime.can_render_gestural_nav_buttons";
    private static final long STYLUS_HANDWRITING_IDLE_TIMEOUT_MAX_MS = 30000;
    private static final long STYLUS_HANDWRITING_IDLE_TIMEOUT_MS = 10000;
    private static final long STYLUS_WINDOW_IDLE_TIMEOUT_MILLIS = 300000;
    static final java.lang.String TAG = "InputMethodService";
    private static final long TIMEOUT_SURFACE_REMOVAL_MILLIS = 500;
    private static final int VOLUME_CURSOR_OFF = 0;
    private static final int VOLUME_CURSOR_ON = 1;
    private static final int VOLUME_CURSOR_ON_REVERSE = 2;
    int mBackDisposition;
    android.widget.FrameLayout mCandidatesFrame;
    boolean mCandidatesViewStarted;
    int mCandidatesVisibility;
    private com.android.internal.inputmethod.IConnectionlessHandwritingCallback mConnectionlessHandwritingCallback;
    android.view.inputmethod.CompletionInfo[] mCurCompletions;
    private android.os.IBinder mCurHideInputToken;
    private android.os.IBinder mCurShowInputToken;
    private android.view.inputmethod.ImeTracker.Token mCurStatsToken;
    boolean mDecorViewVisible;
    boolean mDecorViewWasVisible;
    private boolean mDestroyed;
    android.view.ViewGroup mExtractAccessories;
    android.view.View mExtractAction;
    android.inputmethodservice.ExtractEditText mExtractEditText;
    android.widget.FrameLayout mExtractFrame;
    android.view.View mExtractView;
    boolean mExtractViewHidden;
    android.view.inputmethod.ExtractedText mExtractedText;
    int mExtractedToken;
    private java.lang.Runnable mFinishHwRunnable;
    boolean mFullscreenApplied;
    android.view.ViewGroup mFullscreenArea;
    private android.os.Handler mHandler;
    private java.lang.CharSequence mHandwritingDelegationText;
    private android.view.InputEventReceiver mHandwritingEventReceiver;
    private android.window.ImeOnBackInvokedDispatcher mImeDispatcher;
    private java.lang.Runnable mImeSurfaceRemoverRunnable;
    android.view.inputmethod.InputMethodManager mImm;
    boolean mInShowWindow;
    android.view.LayoutInflater mInflater;
    boolean mInitialized;
    private android.inputmethodservice.InkWindow mInkWindow;
    private android.inputmethodservice.InlineSuggestionSessionController mInlineSuggestionSessionController;
    android.view.inputmethod.InputBinding mInputBinding;
    android.view.inputmethod.InputConnection mInputConnection;
    android.view.inputmethod.EditorInfo mInputEditorInfo;
    android.widget.FrameLayout mInputFrame;
    boolean mInputStarted;
    android.view.View mInputView;
    boolean mInputViewStarted;
    private boolean mIsConnectionlessHandwritingForDelegation;
    boolean mIsFullscreen;
    boolean mIsInputViewShown;
    boolean mLastShowInputRequested;
    private int mLastUsedToolType;
    private boolean mLastWasInFullscreenMode;
    private boolean mNotifyUserActionSent;
    private boolean mOnPreparedStylusHwCalled;
    private com.android.internal.util.RingBuffer<android.view.MotionEvent> mPendingEvents;
    android.view.View mRootView;
    private android.inputmethodservice.InputMethodService.SettingsObserver mSettingsObserver;
    int mShowInputFlags;
    boolean mShowInputRequested;
    android.view.inputmethod.InputConnection mStartedInputConnection;
    int mStatusIcon;
    private long mStylusWindowIdleTimeoutForTest;
    private java.lang.Runnable mStylusWindowIdleTimeoutRunnable;
    android.content.res.TypedArray mThemeAttrs;
    android.os.IBinder mToken;
    boolean mViewsCreated;
    int mVolumeKeyCursorControl;
    android.inputmethodservice.SoftInputWindow mWindow;
    boolean mWindowVisible;
    private boolean mBackCallbackRegistered = false;
    private final android.window.CompatOnBackInvokedCallback mCompatBackCallback = new android.window.CompatOnBackInvokedCallback() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda2
        @Override // android.window.CompatOnBackInvokedCallback, android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            android.inputmethodservice.InputMethodService.this.compatHandleBack();
        }
    };
    private long mStylusHwSessionsTimeout = 10000;
    private com.android.internal.inputmethod.InputMethodPrivilegedOperations mPrivOps = new com.android.internal.inputmethod.InputMethodPrivilegedOperations();
    private final android.inputmethodservice.NavigationBarController mNavigationBarController = new android.inputmethodservice.NavigationBarController(this);
    int mTheme = 0;
    private java.lang.Object mLock = new java.lang.Object();
    final android.inputmethodservice.InputMethodService.Insets mTmpInsets = new android.inputmethodservice.InputMethodService.Insets();
    final int[] mTmpLocation = new int[2];
    private java.util.OptionalInt mHandwritingRequestId = java.util.OptionalInt.empty();
    private android.inputmethodservice.ImsConfigurationTracker mConfigTracker = new android.inputmethodservice.ImsConfigurationTracker();
    final android.view.ViewTreeObserver.OnComputeInternalInsetsListener mInsetsComputer = new android.view.ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda3
        @Override // android.view.ViewTreeObserver.OnComputeInternalInsetsListener
        public final void onComputeInternalInsets(android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
            android.inputmethodservice.InputMethodService.this.lambda$new$0(internalInsetsInfo);
        }
    };
    final android.view.View.OnClickListener mActionClickListener = new android.view.View.OnClickListener() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda4
        @Override // android.view.View.OnClickListener
        public final void onClick(android.view.View view) {
            android.inputmethodservice.InputMethodService.this.lambda$new$1(view);
        }
    };
    private final com.android.internal.inputmethod.ImeTracing.ServiceDumper mDumper = new com.android.internal.inputmethod.ImeTracing.ServiceDumper() { // from class: android.inputmethodservice.InputMethodService.2
        @Override // com.android.internal.inputmethod.ImeTracing.ServiceDumper
        public void dumpToProto(android.util.proto.ProtoOutputStream protoOutputStream, byte[] bArr) {
            long start = protoOutputStream.start(1146756268035L);
            android.inputmethodservice.InputMethodService.this.mWindow.dumpDebug(protoOutputStream, 1146756268033L);
            protoOutputStream.write(1133871366146L, android.inputmethodservice.InputMethodService.this.mViewsCreated);
            protoOutputStream.write(1133871366147L, android.inputmethodservice.InputMethodService.this.mDecorViewVisible);
            protoOutputStream.write(1133871366148L, android.inputmethodservice.InputMethodService.this.mDecorViewWasVisible);
            protoOutputStream.write(1133871366149L, android.inputmethodservice.InputMethodService.this.mWindowVisible);
            protoOutputStream.write(1133871366150L, android.inputmethodservice.InputMethodService.this.mInShowWindow);
            protoOutputStream.write(1138166333447L, android.inputmethodservice.InputMethodService.this.getResources().getConfiguration().toString());
            protoOutputStream.write(1138166333448L, java.util.Objects.toString(android.inputmethodservice.InputMethodService.this.mToken));
            protoOutputStream.write(1138166333449L, java.util.Objects.toString(android.inputmethodservice.InputMethodService.this.mInputBinding));
            protoOutputStream.write(1133871366154L, android.inputmethodservice.InputMethodService.this.mInputStarted);
            protoOutputStream.write(1133871366155L, android.inputmethodservice.InputMethodService.this.mInputViewStarted);
            protoOutputStream.write(1133871366156L, android.inputmethodservice.InputMethodService.this.mCandidatesViewStarted);
            if (android.inputmethodservice.InputMethodService.this.mInputEditorInfo != null) {
                android.inputmethodservice.InputMethodService.this.mInputEditorInfo.dumpDebug(protoOutputStream, 1146756268045L);
            }
            protoOutputStream.write(1133871366158L, android.inputmethodservice.InputMethodService.this.mShowInputRequested);
            protoOutputStream.write(1133871366159L, android.inputmethodservice.InputMethodService.this.mLastShowInputRequested);
            protoOutputStream.write(1120986464274L, android.inputmethodservice.InputMethodService.this.mShowInputFlags);
            protoOutputStream.write(1120986464275L, android.inputmethodservice.InputMethodService.this.mCandidatesVisibility);
            protoOutputStream.write(1133871366164L, android.inputmethodservice.InputMethodService.this.mFullscreenApplied);
            protoOutputStream.write(1133871366165L, android.inputmethodservice.InputMethodService.this.mIsFullscreen);
            protoOutputStream.write(1133871366166L, android.inputmethodservice.InputMethodService.this.mExtractViewHidden);
            protoOutputStream.write(1120986464279L, android.inputmethodservice.InputMethodService.this.mExtractedToken);
            protoOutputStream.write(1133871366168L, android.inputmethodservice.InputMethodService.this.mIsInputViewShown);
            protoOutputStream.write(1120986464281L, android.inputmethodservice.InputMethodService.this.mStatusIcon);
            android.inputmethodservice.InputMethodService.this.mTmpInsets.dumpDebug(protoOutputStream, 1146756268058L);
            protoOutputStream.write(1138166333467L, java.util.Objects.toString(android.inputmethodservice.InputMethodService.this.mSettingsObserver));
            if (bArr != null) {
                protoOutputStream.write(1146756268060L, bArr);
            }
            protoOutputStream.end(start);
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BackDispositionMode {
    }

    public static boolean canImeRenderGesturalNavButtons() {
        return android.os.SystemProperties.getBoolean(PROP_CAN_RENDER_GESTURAL_NAV_BUTTONS, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        onComputeInsets(this.mTmpInsets);
        if (!this.mViewsCreated) {
            this.mTmpInsets.visibleTopInsets = 0;
        }
        if (isExtractViewShown()) {
            android.view.View decorView = getWindow().getWindow().getDecorView();
            android.graphics.Rect rect = internalInsetsInfo.contentInsets;
            android.graphics.Rect rect2 = internalInsetsInfo.visibleInsets;
            int height = decorView.getHeight();
            rect2.top = height;
            rect.top = height;
            internalInsetsInfo.touchableRegion.setEmpty();
            internalInsetsInfo.setTouchableInsets(0);
        } else {
            internalInsetsInfo.contentInsets.top = this.mTmpInsets.contentTopInsets;
            internalInsetsInfo.visibleInsets.top = this.mTmpInsets.visibleTopInsets;
            internalInsetsInfo.touchableRegion.set(this.mTmpInsets.touchableRegion);
            internalInsetsInfo.setTouchableInsets(this.mTmpInsets.touchableInsets);
        }
        this.mNavigationBarController.updateTouchableInsets(this.mTmpInsets, internalInsetsInfo);
        if (this.mInputFrame != null) {
            setImeExclusionRect(this.mTmpInsets.visibleTopInsets);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(android.view.View view) {
        android.view.inputmethod.EditorInfo currentInputEditorInfo = getCurrentInputEditorInfo();
        android.view.inputmethod.InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputEditorInfo != null && currentInputConnection != null) {
            if (currentInputEditorInfo.actionId != 0) {
                currentInputConnection.performEditorAction(currentInputEditorInfo.actionId);
            } else if ((currentInputEditorInfo.imeOptions & 255) != 1) {
                currentInputConnection.performEditorAction(currentInputEditorInfo.imeOptions & 255);
            }
        }
    }

    public class InputMethodImpl extends android.inputmethodservice.AbstractInputMethodService.AbstractInputMethodImpl {
        private boolean mSystemCallingHideSoftInput;
        private boolean mSystemCallingShowSoftInput;

        public InputMethodImpl() {
            super();
        }

        @Override // android.view.inputmethod.InputMethod
        public final void initializeInternal(com.android.internal.inputmethod.IInputMethod.InitParams initParams) {
            android.os.Trace.traceBegin(32L, "IMS.initializeInternal");
            android.inputmethodservice.InputMethodService.this.mPrivOps.set(initParams.privilegedOperations);
            com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry.put(initParams.token, android.inputmethodservice.InputMethodService.this.mPrivOps);
            android.inputmethodservice.InputMethodService.this.mNavigationBarController.onNavButtonFlagsChanged(initParams.navigationBarFlags);
            attachToken(initParams.token);
            android.os.Trace.traceEnd(32L);
        }

        @Override // android.view.inputmethod.InputMethod
        public void onCreateInlineSuggestionsRequest(com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) {
            android.inputmethodservice.InputMethodService.this.mInlineSuggestionSessionController.onMakeInlineSuggestionsRequest(inlineSuggestionsRequestInfo, iInlineSuggestionsRequestCallback);
        }

        @Override // android.view.inputmethod.InputMethod
        public void attachToken(android.os.IBinder iBinder) {
            if (android.inputmethodservice.InputMethodService.this.mToken != null) {
                throw new java.lang.IllegalStateException("attachToken() must be called at most once. token=" + iBinder);
            }
            android.inputmethodservice.InputMethodService.this.attachToWindowToken(iBinder);
            android.inputmethodservice.InputMethodService.this.mToken = iBinder;
            android.inputmethodservice.InputMethodService.this.mWindow.setToken(iBinder);
        }

        @Override // android.view.inputmethod.InputMethod
        public void bindInput(android.view.inputmethod.InputBinding inputBinding) {
            android.os.Trace.traceBegin(32L, "IMS.bindInput");
            android.inputmethodservice.InputMethodService.this.mInputBinding = inputBinding;
            android.inputmethodservice.InputMethodService.this.mInputConnection = inputBinding.getConnection();
            android.inputmethodservice.InputMethodService.this.reportFullscreenMode();
            android.inputmethodservice.InputMethodService.this.initialize();
            android.inputmethodservice.InputMethodService.this.onBindInput();
            android.inputmethodservice.InputMethodService.this.mConfigTracker.onBindInput(android.inputmethodservice.InputMethodService.this.getResources());
            android.os.Trace.traceEnd(32L);
        }

        @Override // android.view.inputmethod.InputMethod
        public void unbindInput() {
            android.inputmethodservice.InputMethodService.this.onUnbindInput();
            android.inputmethodservice.InputMethodService.this.mInputBinding = null;
            android.inputmethodservice.InputMethodService.this.mInputConnection = null;
            if (android.inputmethodservice.InputMethodService.this.mInkWindow != null) {
                finishStylusHandwriting();
                android.inputmethodservice.InputMethodService.this.scheduleStylusWindowIdleTimeout();
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void startInput(android.view.inputmethod.InputConnection inputConnection, android.view.inputmethod.EditorInfo editorInfo) {
            android.os.Trace.traceBegin(32L, "IMS.startInput");
            android.inputmethodservice.InputMethodService.this.doStartInput(inputConnection, editorInfo, false);
            android.os.Trace.traceEnd(32L);
        }

        @Override // android.view.inputmethod.InputMethod
        public void restartInput(android.view.inputmethod.InputConnection inputConnection, android.view.inputmethod.EditorInfo editorInfo) {
            android.os.Trace.traceBegin(32L, "IMS.restartInput");
            android.inputmethodservice.InputMethodService.this.doStartInput(inputConnection, editorInfo, true);
            android.os.Trace.traceEnd(32L);
        }

        @Override // android.view.inputmethod.InputMethod
        public final void dispatchStartInput(android.view.inputmethod.InputConnection inputConnection, com.android.internal.inputmethod.IInputMethod.StartInputParams startInputParams) {
            android.inputmethodservice.InputMethodService.this.mPrivOps.reportStartInputAsync(startInputParams.startInputToken);
            android.inputmethodservice.InputMethodService.this.mNavigationBarController.onNavButtonFlagsChanged(startInputParams.navigationBarFlags);
            if (startInputParams.restarting) {
                restartInput(inputConnection, startInputParams.editorInfo);
            } else {
                startInput(inputConnection, startInputParams.editorInfo);
            }
            android.inputmethodservice.InputMethodService.this.mImeDispatcher = startInputParams.imeDispatcher;
            if (android.inputmethodservice.InputMethodService.this.mWindow != null) {
                android.inputmethodservice.InputMethodService.this.mWindow.getOnBackInvokedDispatcher().setImeOnBackInvokedDispatcher(startInputParams.imeDispatcher);
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void onNavButtonFlagsChanged(int i) {
            android.inputmethodservice.InputMethodService.this.mNavigationBarController.onNavButtonFlagsChanged(i);
        }

        @Override // android.view.inputmethod.InputMethod
        public void hideSoftInputWithToken(int i, android.os.ResultReceiver resultReceiver, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token) {
            this.mSystemCallingHideSoftInput = true;
            android.inputmethodservice.InputMethodService.this.mCurHideInputToken = iBinder;
            android.inputmethodservice.InputMethodService.this.mCurStatsToken = token;
            try {
                hideSoftInput(i, resultReceiver);
            } finally {
                android.inputmethodservice.InputMethodService.this.mCurHideInputToken = null;
                this.mSystemCallingHideSoftInput = false;
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void hideSoftInput(int i, android.os.ResultReceiver resultReceiver) {
            int i2 = 0;
            android.view.inputmethod.ImeTracker.Token createStatsToken = android.inputmethodservice.InputMethodService.this.mCurStatsToken != null ? android.inputmethodservice.InputMethodService.this.mCurStatsToken : android.inputmethodservice.InputMethodService.this.createStatsToken(false, 41, android.view.inputmethod.ImeTracker.isFromUser(android.inputmethodservice.InputMethodService.this.mRootView));
            android.inputmethodservice.InputMethodService.this.mCurStatsToken = null;
            if (android.inputmethodservice.InputMethodService.this.getApplicationInfo().targetSdkVersion >= 30 && !this.mSystemCallingHideSoftInput) {
                android.util.Log.e(android.view.inputmethod.InputMethod.TAG, "IME shouldn't call hideSoftInput on itself. Use requestHideSelf(int) itself");
                android.view.inputmethod.ImeTracker.forLogging().onFailed(createStatsToken, 14);
                return;
            }
            android.view.inputmethod.ImeTracker.forLogging().onProgress(createStatsToken, 14);
            android.os.Trace.traceBegin(32L, "IMS.hideSoftInput");
            com.android.internal.inputmethod.ImeTracing.getInstance().triggerServiceDump("InputMethodService.InputMethodImpl#hideSoftInput", android.inputmethodservice.InputMethodService.this.mDumper, null);
            boolean isInputViewShown = android.inputmethodservice.InputMethodService.this.isInputViewShown();
            android.inputmethodservice.InputMethodService.this.mShowInputFlags = 0;
            android.inputmethodservice.InputMethodService.this.mShowInputRequested = false;
            android.inputmethodservice.InputMethodService.this.mCurStatsToken = createStatsToken;
            android.inputmethodservice.InputMethodService.this.hideWindow();
            boolean z = android.inputmethodservice.InputMethodService.this.isInputViewShown() != isInputViewShown;
            if (resultReceiver != null) {
                if (z) {
                    i2 = 3;
                } else if (!isInputViewShown) {
                    i2 = 1;
                }
                resultReceiver.send(i2, null);
            }
            android.os.Trace.traceEnd(32L);
        }

        @Override // android.view.inputmethod.InputMethod
        public void showSoftInputWithToken(int i, android.os.ResultReceiver resultReceiver, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token) {
            this.mSystemCallingShowSoftInput = true;
            android.inputmethodservice.InputMethodService.this.mCurShowInputToken = iBinder;
            android.inputmethodservice.InputMethodService.this.mCurStatsToken = token;
            try {
                showSoftInput(i, resultReceiver);
            } finally {
                android.inputmethodservice.InputMethodService.this.mCurShowInputToken = null;
                this.mSystemCallingShowSoftInput = false;
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void showSoftInput(int i, android.os.ResultReceiver resultReceiver) {
            int i2 = 1;
            android.view.inputmethod.ImeTracker.Token createStatsToken = android.inputmethodservice.InputMethodService.this.mCurStatsToken != null ? android.inputmethodservice.InputMethodService.this.mCurStatsToken : android.inputmethodservice.InputMethodService.this.createStatsToken(true, 40, android.view.inputmethod.ImeTracker.isFromUser(android.inputmethodservice.InputMethodService.this.mRootView));
            android.inputmethodservice.InputMethodService.this.mCurStatsToken = null;
            if (android.inputmethodservice.InputMethodService.this.getApplicationInfo().targetSdkVersion >= 30 && !this.mSystemCallingShowSoftInput) {
                android.util.Log.e(android.view.inputmethod.InputMethod.TAG, "IME shouldn't call showSoftInput on itself. Use requestShowSelf(int) itself");
                android.view.inputmethod.ImeTracker.forLogging().onFailed(createStatsToken, 13);
                return;
            }
            android.view.inputmethod.ImeTracker.forLogging().onProgress(createStatsToken, 13);
            android.os.Trace.traceBegin(32L, "IMS.showSoftInput");
            com.android.internal.inputmethod.ImeTracing.getInstance().triggerServiceDump("InputMethodService.InputMethodImpl#showSoftInput", android.inputmethodservice.InputMethodService.this.mDumper, null);
            boolean isInputViewShown = android.inputmethodservice.InputMethodService.this.isInputViewShown();
            if (android.inputmethodservice.InputMethodService.this.dispatchOnShowInputRequested(i, false)) {
                android.view.inputmethod.ImeTracker.forLogging().onProgress(createStatsToken, 15);
                android.inputmethodservice.InputMethodService.this.mCurStatsToken = createStatsToken;
                android.inputmethodservice.InputMethodService.this.showWindow(true);
            } else {
                android.view.inputmethod.ImeTracker.forLogging().onFailed(createStatsToken, 15);
            }
            android.inputmethodservice.InputMethodService.this.setImeWindowStatus(android.inputmethodservice.InputMethodService.this.mapToImeWindowStatus(), android.inputmethodservice.InputMethodService.this.mBackDisposition);
            boolean z = android.inputmethodservice.InputMethodService.this.isInputViewShown() != isInputViewShown;
            if (resultReceiver != null) {
                if (z) {
                    i2 = 2;
                } else if (isInputViewShown) {
                    i2 = 0;
                }
                resultReceiver.send(i2, null);
            }
            android.os.Trace.traceEnd(32L);
        }

        @Override // android.view.inputmethod.InputMethod
        public void updateEditorToolType(int i) {
            android.inputmethodservice.InputMethodService.this.updateEditorToolTypeInternal(i);
        }

        @Override // android.view.inputmethod.InputMethod
        public void canStartStylusHandwriting(int i, com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, boolean z) {
            if (android.inputmethodservice.InputMethodService.this.mHandwritingRequestId.isPresent()) {
                android.util.Log.d(android.view.inputmethod.InputMethod.TAG, "There is an ongoing Handwriting session. ignoring.");
                return;
            }
            if (!android.inputmethodservice.InputMethodService.this.mInputStarted) {
                android.util.Log.d(android.view.inputmethod.InputMethod.TAG, "Input should have started before starting Stylus handwriting.");
                return;
            }
            maybeCreateAndInitInkWindow();
            if (!android.inputmethodservice.InputMethodService.this.mOnPreparedStylusHwCalled) {
                android.inputmethodservice.InputMethodService.this.onPrepareStylusHandwriting();
            }
            android.inputmethodservice.InputMethodService.this.mOnPreparedStylusHwCalled = false;
            if (iConnectionlessHandwritingCallback != null) {
                if (android.inputmethodservice.InputMethodService.this.onStartConnectionlessStylusHandwriting(1, cursorAnchorInfo)) {
                    android.inputmethodservice.InputMethodService.this.mConnectionlessHandwritingCallback = iConnectionlessHandwritingCallback;
                    android.inputmethodservice.InputMethodService.this.mIsConnectionlessHandwritingForDelegation = z;
                    android.inputmethodservice.InputMethodService.this.cancelStylusWindowIdleTimeout();
                    android.inputmethodservice.InputMethodService.this.mPrivOps.onStylusHandwritingReady(i, android.os.Process.myPid());
                    return;
                }
                android.util.Log.i(android.view.inputmethod.InputMethod.TAG, "IME is not ready or doesn't currently support connectionless handwriting");
                try {
                    iConnectionlessHandwritingCallback.onError(1);
                    return;
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.view.inputmethod.InputMethod.TAG, "Couldn't send connectionless handwriting error result", e);
                    return;
                }
            }
            if (android.inputmethodservice.InputMethodService.this.onStartStylusHandwriting()) {
                android.inputmethodservice.InputMethodService.this.cancelStylusWindowIdleTimeout();
                android.inputmethodservice.InputMethodService.this.mPrivOps.onStylusHandwritingReady(i, android.os.Process.myPid());
            } else {
                android.util.Log.i(android.view.inputmethod.InputMethod.TAG, "IME is not ready. Can't start Stylus Handwriting");
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void startStylusHandwriting(int i, android.view.InputChannel inputChannel, java.util.List<android.view.MotionEvent> list) {
            java.util.Objects.requireNonNull(inputChannel);
            java.util.Objects.requireNonNull(list);
            if (android.inputmethodservice.InputMethodService.this.mHandwritingRequestId.isPresent()) {
                return;
            }
            android.inputmethodservice.InputMethodService.this.mHandwritingRequestId = java.util.OptionalInt.of(i);
            android.inputmethodservice.InputMethodService.this.mShowInputRequested = false;
            android.inputmethodservice.InputMethodService.this.mInkWindow.show();
            final android.inputmethodservice.InputMethodService inputMethodService = android.inputmethodservice.InputMethodService.this;
            list.forEach(new java.util.function.Consumer() { // from class: android.inputmethodservice.InputMethodService$InputMethodImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.inputmethodservice.InputMethodService.this.onStylusHandwritingMotionEvent((android.view.MotionEvent) obj);
                }
            });
            android.inputmethodservice.InputMethodService.this.mHandwritingEventReceiver = new android.view.InputEventReceiver(inputChannel, android.os.Looper.getMainLooper()) { // from class: android.inputmethodservice.InputMethodService.InputMethodImpl.1
                @Override // android.view.InputEventReceiver
                public void onInputEvent(android.view.InputEvent inputEvent) {
                    try {
                        if (!(inputEvent instanceof android.view.MotionEvent)) {
                            return;
                        }
                        android.inputmethodservice.InputMethodService.this.onStylusHandwritingMotionEvent((android.view.MotionEvent) inputEvent);
                        android.inputmethodservice.InputMethodService.this.scheduleHandwritingSessionTimeout();
                        finishInputEvent(inputEvent, true);
                    } finally {
                        finishInputEvent(inputEvent, false);
                    }
                }
            };
            android.inputmethodservice.InputMethodService.this.scheduleHandwritingSessionTimeout();
        }

        @Override // android.view.inputmethod.InputMethod
        public void commitHandwritingDelegationTextIfAvailable() {
            android.inputmethodservice.InputMethodService.this.commitHandwritingDelegationTextIfAvailable();
        }

        @Override // android.view.inputmethod.InputMethod
        public void discardHandwritingDelegationText() {
            android.inputmethodservice.InputMethodService.this.discardHandwritingDelegationText();
        }

        @Override // android.view.inputmethod.InputMethod
        public void initInkWindow() {
            maybeCreateAndInitInkWindow();
            android.inputmethodservice.InputMethodService.this.onPrepareStylusHandwriting();
            android.inputmethodservice.InputMethodService.this.mOnPreparedStylusHwCalled = true;
        }

        private void maybeCreateAndInitInkWindow() {
            if (android.inputmethodservice.InputMethodService.this.mInkWindow == null) {
                android.inputmethodservice.InputMethodService.this.mInkWindow = new android.inputmethodservice.InkWindow(android.inputmethodservice.InputMethodService.this.mWindow.getContext());
                android.inputmethodservice.InputMethodService.this.mInkWindow.setToken(android.inputmethodservice.InputMethodService.this.mToken);
            }
            android.inputmethodservice.InputMethodService.this.mInkWindow.initOnly();
        }

        @Override // android.view.inputmethod.InputMethod
        public void finishStylusHandwriting() {
            android.inputmethodservice.InputMethodService.this.finishStylusHandwriting();
        }

        @Override // android.view.inputmethod.InputMethod
        public void removeStylusHandwritingWindow() {
            android.inputmethodservice.InputMethodService.this.finishAndRemoveStylusHandwritingWindow();
        }

        @Override // android.view.inputmethod.InputMethod
        public void setStylusWindowIdleTimeoutForTest(long j) {
            android.inputmethodservice.InputMethodService.this.mStylusWindowIdleTimeoutForTest = j;
        }

        @Override // android.view.inputmethod.InputMethod
        public void changeInputMethodSubtype(android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
            android.inputmethodservice.InputMethodService.this.dispatchOnCurrentInputMethodSubtypeChanged(inputMethodSubtype);
        }
    }

    public android.view.inputmethod.InlineSuggestionsRequest onCreateInlineSuggestionsRequest(android.os.Bundle bundle) {
        return null;
    }

    public boolean onInlineSuggestionsResponse(android.view.inputmethod.InlineSuggestionsResponse inlineSuggestionsResponse) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.IBinder getHostInputToken() {
        android.view.ViewRootImpl viewRootImpl;
        if (this.mRootView == null) {
            viewRootImpl = null;
        } else {
            viewRootImpl = this.mRootView.getViewRootImpl();
        }
        if (viewRootImpl == null) {
            return null;
        }
        return viewRootImpl.getInputToken();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleImeSurfaceRemoval() {
        if (this.mShowInputRequested || this.mWindowVisible || this.mWindow == null || this.mImeSurfaceRemoverRunnable != null) {
            return;
        }
        if (this.mHandler == null) {
            this.mHandler = new android.os.Handler(getMainLooper());
        }
        if (this.mLastWasInFullscreenMode) {
            lambda$scheduleImeSurfaceRemoval$2();
        } else {
            this.mImeSurfaceRemoverRunnable = new java.lang.Runnable() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.inputmethodservice.InputMethodService.this.lambda$scheduleImeSurfaceRemoval$2();
                }
            };
            this.mHandler.postDelayed(this.mImeSurfaceRemoverRunnable, TIMEOUT_SURFACE_REMOVAL_MILLIS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: removeImeSurface, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleImeSurfaceRemoval$2() {
        cancelImeSurfaceRemoval();
        if (this.mWindow != null) {
            this.mWindow.hide();
        }
    }

    private void cancelImeSurfaceRemoval() {
        if (this.mHandler != null && this.mImeSurfaceRemoverRunnable != null) {
            this.mHandler.removeCallbacks(this.mImeSurfaceRemoverRunnable);
        }
        this.mImeSurfaceRemoverRunnable = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImeWindowStatus(int i, int i2) {
        this.mPrivOps.setImeWindowStatusAsync(i, i2);
    }

    private void setImeExclusionRect(int i) {
        android.view.View rootView = this.mInputFrame.getRootView();
        android.graphics.Insets insets = rootView.getRootWindowInsets().getInsets(android.view.WindowInsets.Type.systemGestures());
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(new android.graphics.Rect(0, i, insets.left, rootView.getHeight()));
        arrayList.add(new android.graphics.Rect(rootView.getWidth() - insets.right, i, rootView.getWidth(), rootView.getHeight()));
        rootView.setSystemGestureExclusionRects(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEditorToolTypeInternal(int i) {
        if (android.view.inputmethod.Flags.useHandwritingListenerForTooltype()) {
            this.mLastUsedToolType = i;
            if (this.mInputEditorInfo != null) {
                this.mInputEditorInfo.setInitialToolType(i);
            }
        }
        onUpdateEditorToolType(i);
    }

    public class InputMethodSessionImpl extends android.inputmethodservice.AbstractInputMethodService.AbstractInputMethodSessionImpl {
        public InputMethodSessionImpl() {
            super();
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void finishInput() {
            if (!isEnabled()) {
                return;
            }
            android.inputmethodservice.InputMethodService.this.doFinishInput();
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void displayCompletions(android.view.inputmethod.CompletionInfo[] completionInfoArr) {
            if (!isEnabled()) {
                return;
            }
            android.inputmethodservice.InputMethodService.this.mCurCompletions = completionInfoArr;
            android.inputmethodservice.InputMethodService.this.onDisplayCompletions(completionInfoArr);
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void updateExtractedText(int i, android.view.inputmethod.ExtractedText extractedText) {
            if (!isEnabled()) {
                return;
            }
            android.inputmethodservice.InputMethodService.this.onUpdateExtractedText(i, extractedText);
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) {
            if (!isEnabled()) {
                return;
            }
            android.inputmethodservice.InputMethodService.this.onUpdateSelection(i, i2, i3, i4, i5, i6);
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void viewClicked(boolean z) {
            if (!isEnabled()) {
                return;
            }
            android.inputmethodservice.InputMethodService.this.onViewClicked(z);
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void updateCursor(android.graphics.Rect rect) {
            if (!isEnabled()) {
                return;
            }
            android.inputmethodservice.InputMethodService.this.onUpdateCursor(rect);
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void appPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
            if (!isEnabled()) {
                return;
            }
            android.inputmethodservice.InputMethodService.this.onAppPrivateCommand(str, bundle);
        }

        @Override // android.view.inputmethod.InputMethodSession
        @java.lang.Deprecated
        public void toggleSoftInput(int i, int i2) {
            android.inputmethodservice.InputMethodService.this.onToggleSoftInput(i, i2);
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void updateCursorAnchorInfo(android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo) {
            if (!isEnabled()) {
                return;
            }
            android.inputmethodservice.InputMethodService.this.onUpdateCursorAnchorInfo(cursorAnchorInfo);
        }

        @Override // android.view.inputmethod.InputMethodSession
        public final void removeImeSurface() {
            android.inputmethodservice.InputMethodService.this.scheduleImeSurfaceRemoval();
        }

        @Override // android.view.inputmethod.InputMethodSession
        public final void invalidateInputInternal(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) {
            if (android.inputmethodservice.InputMethodService.this.mStartedInputConnection instanceof android.inputmethodservice.RemoteInputConnection) {
                android.inputmethodservice.RemoteInputConnection remoteInputConnection = (android.inputmethodservice.RemoteInputConnection) android.inputmethodservice.InputMethodService.this.mStartedInputConnection;
                if (!remoteInputConnection.isSameConnection(iRemoteInputConnection)) {
                    return;
                }
                editorInfo.makeCompatible(android.inputmethodservice.InputMethodService.this.getApplicationInfo().targetSdkVersion);
                android.inputmethodservice.InputMethodService.this.getInputMethodInternal().restartInput(new android.inputmethodservice.RemoteInputConnection(remoteInputConnection, i), editorInfo);
            }
        }
    }

    public static final class Insets {
        public static final int TOUCHABLE_INSETS_CONTENT = 1;
        public static final int TOUCHABLE_INSETS_FRAME = 0;
        public static final int TOUCHABLE_INSETS_REGION = 3;
        public static final int TOUCHABLE_INSETS_VISIBLE = 2;
        public int contentTopInsets;
        public int touchableInsets;
        public final android.graphics.Region touchableRegion = new android.graphics.Region();
        public int visibleTopInsets;

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.contentTopInsets);
            protoOutputStream.write(1120986464258L, this.visibleTopInsets);
            protoOutputStream.write(1120986464259L, this.touchableInsets);
            protoOutputStream.write(1138166333444L, this.touchableRegion.toString());
            protoOutputStream.end(start);
        }
    }

    private static final class SettingsObserver extends android.database.ContentObserver {
        private final android.inputmethodservice.InputMethodService mService;
        private int mShowImeWithHardKeyboard;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        private @interface ShowImeWithHardKeyboardType {
            public static final int FALSE = 1;
            public static final int TRUE = 2;
            public static final int UNKNOWN = 0;
        }

        private SettingsObserver(android.inputmethodservice.InputMethodService inputMethodService) {
            super(new android.os.Handler(inputMethodService.getMainLooper()));
            this.mShowImeWithHardKeyboard = 0;
            this.mService = inputMethodService;
        }

        public static android.inputmethodservice.InputMethodService.SettingsObserver createAndRegister(android.inputmethodservice.InputMethodService inputMethodService) {
            android.inputmethodservice.InputMethodService.SettingsObserver settingsObserver = new android.inputmethodservice.InputMethodService.SettingsObserver(inputMethodService);
            inputMethodService.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD), false, settingsObserver);
            inputMethodService.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor(android.provider.Settings.System.VOLUME_KEY_CURSOR_CONTROL), false, settingsObserver);
            return settingsObserver;
        }

        void unregister() {
            this.mService.getContentResolver().unregisterContentObserver(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean shouldShowImeWithHardKeyboard() {
            if (this.mShowImeWithHardKeyboard == 0) {
                this.mShowImeWithHardKeyboard = android.provider.Settings.Secure.getInt(this.mService.getContentResolver(), android.provider.Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD, 0) != 0 ? 2 : 1;
            }
            switch (this.mShowImeWithHardKeyboard) {
                case 1:
                    return false;
                case 2:
                    return true;
                default:
                    android.util.Log.e(android.inputmethodservice.InputMethodService.TAG, "Unexpected mShowImeWithHardKeyboard=" + this.mShowImeWithHardKeyboard);
                    return false;
            }
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD).equals(uri)) {
                this.mShowImeWithHardKeyboard = android.provider.Settings.Secure.getInt(this.mService.getContentResolver(), android.provider.Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD, 0) != 0 ? 2 : 1;
                this.mService.resetStateForNewConfiguration();
            }
            this.mService.mVolumeKeyCursorControl = android.provider.Settings.System.getInt(this.mService.getContentResolver(), android.provider.Settings.System.VOLUME_KEY_CURSOR_CONTROL, 0);
        }

        public java.lang.String toString() {
            return "SettingsObserver{mShowImeWithHardKeyboard=" + this.mShowImeWithHardKeyboard + "}";
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void setTheme(int i) {
        if (this.mWindow != null) {
            throw new java.lang.IllegalStateException("Must be called before onCreate()");
        }
        this.mTheme = i;
    }

    @java.lang.Deprecated
    public boolean enableHardwareAcceleration() {
        if (this.mWindow != null) {
            throw new java.lang.IllegalStateException("Must be called before onCreate()");
        }
        return android.app.ActivityManager.isHighEndGfx();
    }

    @Override // android.app.Service
    public void onCreate() {
        if (methodIsOverridden("onCreateInputMethodSessionInterface", new java.lang.Class[0]) && android.app.compat.CompatChanges.isChangeEnabled(DISALLOW_INPUT_METHOD_INTERFACE_OVERRIDE)) {
            throw new java.lang.LinkageError("InputMethodService#onCreateInputMethodSessionInterface() can no longer be overridden!");
        }
        android.os.Trace.traceBegin(32L, "IMS.onCreate");
        this.mTheme = android.content.res.Resources.selectSystemTheme(this.mTheme, getApplicationInfo().targetSdkVersion, 16973908, 16973951, 16974142, 16974142);
        super.setTheme(this.mTheme);
        super.onCreate();
        this.mImm = (android.view.inputmethod.InputMethodManager) getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        this.mSettingsObserver = android.inputmethodservice.InputMethodService.SettingsObserver.createAndRegister(this);
        this.mSettingsObserver.shouldShowImeWithHardKeyboard();
        this.mVolumeKeyCursorControl = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.VOLUME_KEY_CURSOR_CONTROL, 0);
        boolean z = getApplicationContext().getResources().getBoolean(com.android.internal.R.bool.config_hideNavBarForKeyboard);
        initConfigurationTracker();
        this.mInflater = (android.view.LayoutInflater) getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        android.os.Trace.traceBegin(32L, "IMS.initSoftInputWindow");
        this.mWindow = new android.inputmethodservice.SoftInputWindow(this, this.mTheme, this.mDispatcherState);
        if (this.mImeDispatcher != null) {
            this.mWindow.getOnBackInvokedDispatcher().setImeOnBackInvokedDispatcher(this.mImeDispatcher);
        }
        this.mNavigationBarController.onSoftInputWindowCreated(this.mWindow);
        android.view.Window window = this.mWindow.getWindow();
        android.view.WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.setTitle(android.view.inputmethod.InputMethod.TAG);
        attributes.type = 2011;
        attributes.width = -1;
        attributes.height = -2;
        attributes.gravity = 80;
        attributes.setFitInsetsTypes(android.view.WindowInsets.Type.statusBars() | android.view.WindowInsets.Type.navigationBars());
        attributes.setFitInsetsSides(android.view.WindowInsets.Side.all() & (-9));
        attributes.receiveInsetsIgnoringZOrder = true;
        window.setAttributes(attributes);
        window.setFlags(-2147483384, -2147483382);
        if (z) {
            window.setDecorFitsSystemWindows(false);
        }
        initViews();
        android.os.Trace.traceEnd(32L);
        this.mInlineSuggestionSessionController = new android.inputmethodservice.InlineSuggestionSessionController(new java.util.function.Function() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.inputmethodservice.InputMethodService.this.onCreateInlineSuggestionsRequest((android.os.Bundle) obj);
            }
        }, new java.util.function.Supplier() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.os.IBinder hostInputToken;
                hostInputToken = android.inputmethodservice.InputMethodService.this.getHostInputToken();
                return hostInputToken;
            }
        }, new java.util.function.Consumer() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.inputmethodservice.InputMethodService.this.onInlineSuggestionsResponse((android.view.inputmethod.InlineSuggestionsResponse) obj);
            }
        });
        android.os.Trace.traceEnd(32L);
    }

    private void initConfigurationTracker() {
        android.content.ComponentName componentName = new android.content.ComponentName(getPackageName(), getClass().getName());
        java.lang.String flattenToShortString = componentName.flattenToShortString();
        try {
            try {
                android.content.res.XmlResourceParser loadXmlMetaData = getPackageManager().getServiceInfo(componentName, android.content.pm.PackageManager.ComponentInfoFlags.of(32896L)).loadXmlMetaData(getPackageManager(), android.view.inputmethod.InputMethod.SERVICE_META_DATA);
                try {
                    android.content.res.TypedArray obtainAttributes = getResources().obtainAttributes(android.util.Xml.asAttributeSet(loadXmlMetaData), com.android.internal.R.styleable.InputMethod);
                    try {
                        if (loadXmlMetaData == null) {
                            throw new org.xmlpull.v1.XmlPullParserException("No android.view.im meta-data");
                        }
                        this.mConfigTracker.onInitialize(obtainAttributes.getInt(0, 0));
                        if (obtainAttributes != null) {
                            obtainAttributes.close();
                        }
                        if (loadXmlMetaData != null) {
                            loadXmlMetaData.close();
                        }
                    } finally {
                    }
                } finally {
                }
            } catch (java.lang.Exception e) {
                android.util.Log.wtf(TAG, "Unable to load input method " + flattenToShortString, e);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            android.util.Log.wtf(TAG, "Unable to find input method " + flattenToShortString, e2);
        }
    }

    public void onInitializeInterface() {
    }

    void initialize() {
        if (!this.mInitialized) {
            this.mInitialized = true;
            onInitializeInterface();
        }
    }

    void initViews() {
        android.os.Trace.traceBegin(32L, "IMS.initViews");
        this.mInitialized = false;
        this.mViewsCreated = false;
        this.mShowInputRequested = false;
        this.mShowInputFlags = 0;
        this.mThemeAttrs = obtainStyledAttributes(android.R.styleable.InputMethodService);
        this.mRootView = this.mInflater.inflate(com.android.internal.R.layout.input_method, (android.view.ViewGroup) null);
        this.mWindow.setContentView(this.mRootView);
        this.mRootView.getViewTreeObserver().addOnComputeInternalInsetsListener(this.mInsetsComputer);
        this.mFullscreenArea = (android.view.ViewGroup) this.mRootView.findViewById(com.android.internal.R.id.fullscreenArea);
        this.mExtractViewHidden = false;
        this.mExtractFrame = (android.widget.FrameLayout) this.mRootView.findViewById(16908316);
        this.mExtractView = null;
        this.mExtractEditText = null;
        this.mExtractAccessories = null;
        this.mExtractAction = null;
        this.mFullscreenApplied = false;
        this.mCandidatesFrame = (android.widget.FrameLayout) this.mRootView.findViewById(16908317);
        this.mInputFrame = (android.widget.FrameLayout) this.mRootView.findViewById(16908318);
        this.mInputView = null;
        this.mIsInputViewShown = false;
        this.mExtractFrame.setVisibility(8);
        this.mCandidatesVisibility = getCandidatesHiddenVisibility();
        this.mCandidatesFrame.setVisibility(this.mCandidatesVisibility);
        this.mInputFrame.setVisibility(8);
        this.mNavigationBarController.onViewInitialized();
        android.os.Trace.traceEnd(32L);
    }

    @Override // android.window.WindowProviderService, android.app.Service
    public void onDestroy() {
        this.mDestroyed = true;
        super.onDestroy();
        this.mRootView.getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mInsetsComputer);
        doFinishInput();
        this.mNavigationBarController.onDestroy();
        this.mWindow.dismissForDestroyIfNecessary();
        if (this.mSettingsObserver != null) {
            this.mSettingsObserver.unregister();
            this.mSettingsObserver = null;
        }
        if (this.mToken != null) {
            com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry.remove(this.mToken);
        }
        this.mImeDispatcher = null;
    }

    @Override // android.window.WindowProviderService, android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mConfigTracker.onConfigurationChanged(configuration, new java.lang.Runnable() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                android.inputmethodservice.InputMethodService.this.resetStateForNewConfiguration();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetStateForNewConfiguration() {
        android.os.Trace.traceBegin(32L, "IMS.resetStateForNewConfiguration");
        boolean z = this.mDecorViewVisible;
        int i = this.mShowInputFlags;
        boolean z2 = this.mShowInputRequested;
        android.view.inputmethod.CompletionInfo[] completionInfoArr = this.mCurCompletions;
        this.mRootView.getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mInsetsComputer);
        initViews();
        this.mInputViewStarted = false;
        this.mCandidatesViewStarted = false;
        if (this.mInputStarted) {
            doStartInput(getCurrentInputConnection(), getCurrentInputEditorInfo(), true);
        }
        if (z) {
            if (z2) {
                if (dispatchOnShowInputRequested(i, true)) {
                    showWindowWithToken(true, 44);
                    if (completionInfoArr != null) {
                        this.mCurCompletions = completionInfoArr;
                        onDisplayCompletions(completionInfoArr);
                    }
                } else {
                    hideWindowWithToken(44);
                }
            } else if (this.mCandidatesVisibility == 0) {
                showWindowWithToken(false, 44);
            } else {
                hideWindowWithToken(44);
            }
            setImeWindowStatus((onEvaluateInputViewShown() ? 2 : 0) | 1, this.mBackDisposition);
        }
        android.os.Trace.traceEnd(32L);
    }

    @Override // android.inputmethodservice.AbstractInputMethodService
    @java.lang.Deprecated
    public android.inputmethodservice.AbstractInputMethodService.AbstractInputMethodImpl onCreateInputMethodInterface() {
        return new android.inputmethodservice.InputMethodService.InputMethodImpl();
    }

    @Override // android.inputmethodservice.AbstractInputMethodService
    @java.lang.Deprecated
    public android.inputmethodservice.AbstractInputMethodService.AbstractInputMethodSessionImpl onCreateInputMethodSessionInterface() {
        return new android.inputmethodservice.InputMethodService.InputMethodSessionImpl();
    }

    public android.view.LayoutInflater getLayoutInflater() {
        return this.mInflater;
    }

    public android.app.Dialog getWindow() {
        return this.mWindow;
    }

    public void setBackDisposition(int i) {
        if (i == this.mBackDisposition) {
            return;
        }
        if (i > 3 || i < 0) {
            android.util.Log.e(TAG, "Invalid back disposition value (" + i + ") specified.");
        } else {
            this.mBackDisposition = i;
            setImeWindowStatus(mapToImeWindowStatus(), this.mBackDisposition);
        }
    }

    public int getBackDisposition() {
        return this.mBackDisposition;
    }

    public int getMaxWidth() {
        return android.window.WindowMetricsHelper.getBoundsExcludingNavigationBarAndCutout(((android.view.WindowManager) getSystemService(android.view.WindowManager.class)).getCurrentWindowMetrics()).width();
    }

    public android.view.inputmethod.InputBinding getCurrentInputBinding() {
        return this.mInputBinding;
    }

    public android.view.inputmethod.InputConnection getCurrentInputConnection() {
        android.view.inputmethod.InputConnection inputConnection = this.mStartedInputConnection;
        if (inputConnection != null) {
            return inputConnection;
        }
        return this.mInputConnection;
    }

    public final boolean switchToPreviousInputMethod() {
        return this.mPrivOps.switchToPreviousInputMethod();
    }

    public final boolean switchToNextInputMethod(boolean z) {
        return this.mPrivOps.switchToNextInputMethod(z);
    }

    public final boolean shouldOfferSwitchingToNextInputMethod() {
        return this.mPrivOps.shouldOfferSwitchingToNextInputMethod();
    }

    public boolean getCurrentInputStarted() {
        return this.mInputStarted;
    }

    public android.view.inputmethod.EditorInfo getCurrentInputEditorInfo() {
        return this.mInputEditorInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportFullscreenMode() {
        this.mPrivOps.reportFullscreenModeAsync(this.mIsFullscreen);
    }

    public void updateFullscreenMode() {
        android.view.View onCreateExtractTextView;
        android.os.Trace.traceBegin(32L, "IMS.updateFullscreenMode");
        boolean z = this.mShowInputRequested && onEvaluateFullscreenMode();
        boolean z2 = this.mLastShowInputRequested != this.mShowInputRequested;
        if (this.mIsFullscreen != z || !this.mFullscreenApplied) {
            this.mIsFullscreen = z;
            reportFullscreenMode();
            this.mFullscreenApplied = true;
            initialize();
            android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) this.mFullscreenArea.getLayoutParams();
            if (z) {
                this.mFullscreenArea.setBackgroundDrawable(this.mThemeAttrs.getDrawable(0));
                layoutParams.height = 0;
                layoutParams.weight = 1.0f;
            } else {
                this.mFullscreenArea.setBackgroundDrawable(null);
                layoutParams.height = -2;
                layoutParams.weight = 0.0f;
            }
            ((android.view.ViewGroup) this.mFullscreenArea.getParent()).updateViewLayout(this.mFullscreenArea, layoutParams);
            if (z) {
                if (this.mExtractView == null && (onCreateExtractTextView = onCreateExtractTextView()) != null) {
                    setExtractView(onCreateExtractTextView);
                }
                startExtractingText(false);
            }
            updateExtractFrameVisibility();
            z2 = true;
        }
        if (z2) {
            onConfigureWindow(this.mWindow.getWindow(), z, true ^ this.mShowInputRequested);
            this.mLastShowInputRequested = this.mShowInputRequested;
        }
        android.os.Trace.traceEnd(32L);
    }

    public void onConfigureWindow(android.view.Window window, boolean z, boolean z2) {
        int i = this.mWindow.getWindow().getAttributes().height;
        this.mWindow.getWindow().setLayout(-1, z ? -1 : -2);
    }

    public boolean isFullscreenMode() {
        return this.mIsFullscreen;
    }

    public boolean onEvaluateFullscreenMode() {
        if (getResources().getConfiguration().orientation != 2) {
            return false;
        }
        return this.mInputEditorInfo == null || ((this.mInputEditorInfo.imeOptions & 33554432) == 0 && (this.mInputEditorInfo.internalImeOptions & 1) == 0);
    }

    public void setExtractViewShown(boolean z) {
        if (this.mExtractViewHidden == z) {
            this.mExtractViewHidden = !z;
            updateExtractFrameVisibility();
        }
    }

    public boolean isExtractViewShown() {
        return this.mIsFullscreen && !this.mExtractViewHidden;
    }

    void updateExtractFrameVisibility() {
        int i;
        updateCandidatesVisibility(this.mCandidatesVisibility == 0);
        if (isFullscreenMode()) {
            i = this.mExtractViewHidden ? 4 : 0;
            this.mExtractFrame.setVisibility(i);
        } else {
            i = this.mCandidatesVisibility;
            this.mExtractFrame.setVisibility(8);
        }
        if (this.mDecorViewWasVisible && this.mFullscreenArea.getVisibility() != i) {
            int resourceId = this.mThemeAttrs.getResourceId(i != 0 ? 2 : 1, 0);
            if (resourceId != 0) {
                this.mFullscreenArea.startAnimation(android.view.animation.AnimationUtils.loadAnimation(this, resourceId));
            }
        }
        this.mFullscreenArea.setVisibility(i);
    }

    public void onComputeInsets(android.inputmethodservice.InputMethodService.Insets insets) {
        android.os.Trace.traceBegin(32L, "IMS.onComputeInsets");
        int[] iArr = this.mTmpLocation;
        if (this.mInputFrame.getVisibility() == 0) {
            this.mInputFrame.getLocationInWindow(iArr);
        } else {
            iArr[1] = getWindow().getWindow().getDecorView().getHeight();
        }
        if (isFullscreenMode()) {
            insets.contentTopInsets = getWindow().getWindow().getDecorView().getHeight();
        } else {
            insets.contentTopInsets = iArr[1];
        }
        if (this.mCandidatesFrame.getVisibility() == 0) {
            this.mCandidatesFrame.getLocationInWindow(iArr);
        }
        insets.visibleTopInsets = iArr[1];
        insets.touchableInsets = 2;
        insets.touchableRegion.setEmpty();
        android.os.Trace.traceEnd(32L);
    }

    public void updateInputViewShown() {
        boolean z = this.mShowInputRequested && onEvaluateInputViewShown();
        if (this.mIsInputViewShown != z && this.mDecorViewVisible) {
            this.mIsInputViewShown = z;
            this.mInputFrame.setVisibility(z ? 0 : 8);
            if (this.mInputView == null) {
                initialize();
                android.view.View onCreateInputView = onCreateInputView();
                if (onCreateInputView != null) {
                    setInputView(onCreateInputView);
                }
            }
        }
    }

    public boolean isShowInputRequested() {
        return this.mShowInputRequested;
    }

    public boolean isInputViewShown() {
        return this.mDecorViewVisible;
    }

    public boolean onEvaluateInputViewShown() {
        if (this.mSettingsObserver == null) {
            android.util.Log.w(TAG, "onEvaluateInputViewShown: mSettingsObserver must not be null here.");
            return false;
        }
        if (this.mSettingsObserver.shouldShowImeWithHardKeyboard()) {
            return true;
        }
        android.content.res.Configuration configuration = getResources().getConfiguration();
        return configuration.keyboard == 1 || configuration.hardKeyboardHidden == 2;
    }

    public void setCandidatesViewShown(boolean z) {
        updateCandidatesVisibility(z);
        if (this.mShowInputRequested || this.mDecorViewVisible == z) {
            return;
        }
        if (z) {
            showWindowWithToken(false, 45);
        } else {
            hideWindowWithToken(45);
        }
    }

    void updateCandidatesVisibility(boolean z) {
        int candidatesHiddenVisibility = z ? 0 : getCandidatesHiddenVisibility();
        if (this.mCandidatesVisibility != candidatesHiddenVisibility) {
            this.mCandidatesFrame.setVisibility(candidatesHiddenVisibility);
            this.mCandidatesVisibility = candidatesHiddenVisibility;
        }
    }

    public int getCandidatesHiddenVisibility() {
        return isExtractViewShown() ? 8 : 4;
    }

    public void showStatusIcon(int i) {
        this.mStatusIcon = i;
        this.mPrivOps.updateStatusIconAsync(getPackageName(), i);
    }

    public void hideStatusIcon() {
        this.mStatusIcon = 0;
        this.mPrivOps.updateStatusIconAsync(null, 0);
    }

    public void switchInputMethod(java.lang.String str) {
        this.mPrivOps.setInputMethod(str);
    }

    public final void switchInputMethod(java.lang.String str, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        this.mPrivOps.setInputMethodAndSubtype(str, inputMethodSubtype);
    }

    public void setExtractView(android.view.View view) {
        this.mExtractFrame.removeAllViews();
        this.mExtractFrame.addView(view, new android.widget.FrameLayout.LayoutParams(-1, -1));
        this.mExtractView = view;
        if (view != null) {
            this.mExtractEditText = (android.inputmethodservice.ExtractEditText) view.findViewById(16908325);
            this.mExtractEditText.setIME(this);
            this.mExtractAction = view.findViewById(16908377);
            if (this.mExtractAction != null) {
                this.mExtractAccessories = (android.view.ViewGroup) view.findViewById(16908378);
            }
            startExtractingText(false);
            return;
        }
        this.mExtractEditText = null;
        this.mExtractAccessories = null;
        this.mExtractAction = null;
    }

    public void setCandidatesView(android.view.View view) {
        this.mCandidatesFrame.removeAllViews();
        this.mCandidatesFrame.addView(view, new android.widget.FrameLayout.LayoutParams(-1, -2));
    }

    public void setInputView(android.view.View view) {
        this.mInputFrame.removeAllViews();
        this.mInputFrame.addView(view, new android.widget.FrameLayout.LayoutParams(-1, -2));
        this.mInputView = view;
    }

    public android.view.View onCreateExtractTextView() {
        return this.mInflater.inflate(com.android.internal.R.layout.input_method_extract_view, (android.view.ViewGroup) null);
    }

    public android.view.View onCreateCandidatesView() {
        return null;
    }

    public android.view.View onCreateInputView() {
        return null;
    }

    public void onStartInputView(android.view.inputmethod.EditorInfo editorInfo, boolean z) {
    }

    public void onFinishInputView(boolean z) {
        android.view.inputmethod.InputConnection currentInputConnection;
        if (!z && (currentInputConnection = getCurrentInputConnection()) != null) {
            currentInputConnection.finishComposingText();
        }
    }

    public void onStartCandidatesView(android.view.inputmethod.EditorInfo editorInfo, boolean z) {
    }

    public void onFinishCandidatesView(boolean z) {
        android.view.inputmethod.InputConnection currentInputConnection;
        if (!z && (currentInputConnection = getCurrentInputConnection()) != null) {
            currentInputConnection.finishComposingText();
        }
    }

    public void onPrepareStylusHandwriting() {
    }

    public boolean onStartStylusHandwriting() {
        return false;
    }

    public boolean onStartConnectionlessStylusHandwriting(int i, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo) {
        return false;
    }

    public void onStylusHandwritingMotionEvent(android.view.MotionEvent motionEvent) {
        if (this.mInkWindow != null && this.mInkWindow.isInkViewVisible()) {
            this.mInkWindow.dispatchHandwritingEvent(motionEvent);
        } else {
            if (this.mPendingEvents == null) {
                this.mPendingEvents = new com.android.internal.util.RingBuffer<>(android.view.MotionEvent.class, 500);
            }
            this.mPendingEvents.append(motionEvent);
            if (this.mInkWindow != null) {
                this.mInkWindow.setInkViewVisibilityListener(new android.inputmethodservice.InkWindow.InkVisibilityListener() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda1
                    @Override // android.inputmethodservice.InkWindow.InkVisibilityListener
                    public final void onInkViewVisible() {
                        android.inputmethodservice.InputMethodService.this.lambda$onStylusHandwritingMotionEvent$3();
                    }
                });
            }
        }
        if (motionEvent.getAction() == 0) {
            scheduleStylusWindowIdleTimeout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStylusHandwritingMotionEvent$3() {
        if (this.mPendingEvents != null && !this.mPendingEvents.isEmpty()) {
            for (android.view.MotionEvent motionEvent : this.mPendingEvents.toArray()) {
                if (this.mInkWindow == null) {
                    break;
                }
                this.mInkWindow.dispatchHandwritingEvent(motionEvent);
            }
            this.mPendingEvents.clear();
        }
    }

    public void onFinishStylusHandwriting() {
    }

    public final android.view.Window getStylusHandwritingWindow() {
        return this.mInkWindow;
    }

    public final void finishStylusHandwriting() {
        if (this.mInkWindow == null || !this.mHandwritingRequestId.isPresent()) {
            return;
        }
        if (this.mHandler != null && this.mFinishHwRunnable != null) {
            this.mHandler.removeCallbacks(this.mFinishHwRunnable);
        }
        this.mFinishHwRunnable = null;
        int asInt = this.mHandwritingRequestId.getAsInt();
        this.mHandwritingRequestId = java.util.OptionalInt.empty();
        this.mHandwritingEventReceiver.dispose();
        this.mHandwritingEventReceiver = null;
        this.mInkWindow.hide(false);
        if (this.mConnectionlessHandwritingCallback != null) {
            android.util.Log.i(TAG, "Connectionless handwriting session did not complete successfully");
            try {
                this.mConnectionlessHandwritingCallback.onError(2);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Couldn't send connectionless handwriting error result", e);
            }
            this.mConnectionlessHandwritingCallback = null;
        }
        this.mIsConnectionlessHandwritingForDelegation = false;
        this.mPrivOps.resetStylusHandwriting(asInt);
        this.mOnPreparedStylusHwCalled = false;
        onFinishStylusHandwriting();
    }

    public final void finishConnectionlessStylusHandwriting(java.lang.CharSequence charSequence) {
        if (this.mConnectionlessHandwritingCallback != null) {
            try {
                if (!android.text.TextUtils.isEmpty(charSequence)) {
                    this.mConnectionlessHandwritingCallback.onResult(charSequence);
                    if (this.mIsConnectionlessHandwritingForDelegation) {
                        this.mHandwritingDelegationText = charSequence;
                    }
                } else {
                    this.mConnectionlessHandwritingCallback.onError(0);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Couldn't send connectionless handwriting result", e);
            }
            this.mConnectionlessHandwritingCallback = null;
        }
        finishStylusHandwriting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commitHandwritingDelegationTextIfAvailable() {
        android.view.inputmethod.InputConnection currentInputConnection;
        if (!android.text.TextUtils.isEmpty(this.mHandwritingDelegationText) && (currentInputConnection = getCurrentInputConnection()) != null) {
            currentInputConnection.commitText(this.mHandwritingDelegationText, 1);
        }
        this.mHandwritingDelegationText = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void discardHandwritingDelegationText() {
        this.mHandwritingDelegationText = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishAndRemoveStylusHandwritingWindow() {
        cancelStylusWindowIdleTimeout();
        this.mOnPreparedStylusHwCalled = false;
        this.mStylusWindowIdleTimeoutRunnable = null;
        if (this.mInkWindow != null) {
            if (this.mHandwritingRequestId.isPresent()) {
                finishStylusHandwriting();
            }
            this.mInkWindow.hide(true);
            this.mInkWindow.destroy();
            this.mInkWindow = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelStylusWindowIdleTimeout() {
        if (this.mStylusWindowIdleTimeoutRunnable != null && this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mStylusWindowIdleTimeoutRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleStylusWindowIdleTimeout() {
        if (this.mHandler == null) {
            return;
        }
        cancelStylusWindowIdleTimeout();
        this.mHandler.postDelayed(getStylusWindowIdleTimeoutRunnable(), this.mStylusWindowIdleTimeoutForTest > 0 ? this.mStylusWindowIdleTimeoutForTest : 300000L);
    }

    private java.lang.Runnable getStylusWindowIdleTimeoutRunnable() {
        if (this.mStylusWindowIdleTimeoutRunnable == null) {
            this.mStylusWindowIdleTimeoutRunnable = new java.lang.Runnable() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.inputmethodservice.InputMethodService.this.lambda$getStylusWindowIdleTimeoutRunnable$4();
                }
            };
        }
        return this.mStylusWindowIdleTimeoutRunnable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getStylusWindowIdleTimeoutRunnable$4() {
        finishAndRemoveStylusHandwritingWindow();
        this.mStylusWindowIdleTimeoutRunnable = null;
    }

    public final void setStylusHandwritingSessionTimeout(java.time.Duration duration) {
        long millis = duration.toMillis();
        if (millis <= 0) {
            throw new java.lang.IllegalStateException("A positive value should be set for Stylus handwriting session timeout.");
        }
        if (millis > 30000) {
            millis = 30000;
        }
        this.mStylusHwSessionsTimeout = millis;
        scheduleHandwritingSessionTimeout();
    }

    public static final java.time.Duration getStylusHandwritingIdleTimeoutMax() {
        return java.time.Duration.ofMillis(30000L);
    }

    public final java.time.Duration getStylusHandwritingSessionTimeout() {
        return java.time.Duration.ofMillis(this.mStylusHwSessionsTimeout);
    }

    private java.lang.Runnable getFinishHandwritingRunnable() {
        if (this.mFinishHwRunnable != null) {
            return this.mFinishHwRunnable;
        }
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                android.inputmethodservice.InputMethodService.this.lambda$getFinishHandwritingRunnable$5();
            }
        };
        this.mFinishHwRunnable = runnable;
        return runnable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getFinishHandwritingRunnable$5() {
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mFinishHwRunnable);
        }
        android.util.Log.d(TAG, "Stylus handwriting idle timed-out. calling finishStylusHandwriting()");
        this.mFinishHwRunnable = null;
        finishStylusHandwriting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleHandwritingSessionTimeout() {
        if (this.mHandler == null) {
            this.mHandler = new android.os.Handler(getMainLooper());
        }
        if (this.mFinishHwRunnable != null) {
            this.mHandler.removeCallbacks(this.mFinishHwRunnable);
        }
        this.mHandler.postDelayed(getFinishHandwritingRunnable(), this.mStylusHwSessionsTimeout);
    }

    public boolean onShowInputRequested(int i, boolean z) {
        if (!onEvaluateInputViewShown()) {
            return false;
        }
        if ((i & 1) == 0) {
            if (!z && onEvaluateFullscreenMode() && !isInputViewShown()) {
                return false;
            }
            if (!this.mSettingsObserver.shouldShowImeWithHardKeyboard() && getResources().getConfiguration().keyboard != 1) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean dispatchOnShowInputRequested(int i, boolean z) {
        boolean onShowInputRequested = onShowInputRequested(i, z);
        this.mInlineSuggestionSessionController.notifyOnShowInputRequested(onShowInputRequested);
        if (onShowInputRequested) {
            this.mShowInputFlags = i;
        } else {
            this.mShowInputFlags = 0;
        }
        return onShowInputRequested;
    }

    private void showWindowWithToken(boolean z, int i) {
        this.mCurStatsToken = createStatsToken(true, i, android.view.inputmethod.ImeTracker.isFromUser(this.mRootView));
        showWindow(z);
    }

    public void showWindow(boolean z) {
        int i;
        android.view.inputmethod.ImeTracker.Token createStatsToken = this.mCurStatsToken != null ? this.mCurStatsToken : createStatsToken(true, 42, android.view.inputmethod.ImeTracker.isFromUser(this.mRootView));
        this.mCurStatsToken = null;
        if (this.mInShowWindow) {
            android.util.Log.w(TAG, "Re-entrance in to showWindow");
            android.view.inputmethod.ImeTracker.forLogging().onCancelled(createStatsToken, 44);
            return;
        }
        android.view.inputmethod.ImeTracker.forLogging().onProgress(createStatsToken, 44);
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerServiceDump("InputMethodService#showWindow", this.mDumper, null);
        android.os.Trace.traceBegin(32L, "IMS.showWindow");
        this.mDecorViewWasVisible = this.mDecorViewVisible;
        this.mInShowWindow = true;
        boolean z2 = this.mDecorViewVisible;
        if (isInputViewShown()) {
            i = !this.mWindowVisible ? 4 : 2;
        } else {
            i = 0;
        }
        int i2 = (z2 ? 1 : 0) | i;
        startViews(prepareWindow(z));
        int mapToImeWindowStatus = mapToImeWindowStatus();
        if (i2 != mapToImeWindowStatus) {
            setImeWindowStatus(mapToImeWindowStatus, this.mBackDisposition);
        }
        this.mNavigationBarController.onWindowShown();
        onWindowShown();
        this.mWindowVisible = true;
        this.mWindow.show();
        this.mDecorViewWasVisible = true;
        applyVisibilityInInsetsConsumerIfNecessary(true, createStatsToken);
        cancelImeSurfaceRemoval();
        this.mInShowWindow = false;
        android.os.Trace.traceEnd(32L);
        registerCompatOnBackInvokedCallback();
    }

    private void registerCompatOnBackInvokedCallback() {
        if (!this.mBackCallbackRegistered && this.mWindow != null) {
            this.mWindow.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mCompatBackCallback);
            this.mBackCallbackRegistered = true;
        }
    }

    private void unregisterCompatOnBackInvokedCallback() {
        if (this.mBackCallbackRegistered && this.mWindow != null) {
            this.mWindow.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mCompatBackCallback);
            this.mBackCallbackRegistered = false;
        }
    }

    private android.view.KeyEvent createBackKeyEvent(int i, boolean z) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        return new android.view.KeyEvent(uptimeMillis, uptimeMillis, i, 4, 0, 0, -1, 0, (z ? 512 : 0) | 72, 257);
    }

    private boolean prepareWindow(boolean z) {
        boolean z2;
        this.mDecorViewVisible = true;
        if (!this.mShowInputRequested && this.mInputStarted && z) {
            this.mShowInputRequested = true;
            z2 = true;
        } else {
            z2 = false;
        }
        initialize();
        updateFullscreenMode();
        updateInputViewShown();
        if (!this.mViewsCreated) {
            this.mViewsCreated = true;
            initialize();
            android.view.View onCreateCandidatesView = onCreateCandidatesView();
            if (onCreateCandidatesView != null) {
                setCandidatesView(onCreateCandidatesView);
            }
        }
        return z2;
    }

    private void startViews(boolean z) {
        if (this.mShowInputRequested) {
            if (!this.mInputViewStarted) {
                this.mInputViewStarted = true;
                this.mInlineSuggestionSessionController.notifyOnStartInputView();
                onStartInputView(this.mInputEditorInfo, false);
            }
        } else if (!this.mCandidatesViewStarted) {
            this.mCandidatesViewStarted = true;
            onStartCandidatesView(this.mInputEditorInfo, false);
        }
        if (z) {
            startExtractingText(false);
        }
    }

    private void applyVisibilityInInsetsConsumerIfNecessary(boolean z, android.view.inputmethod.ImeTracker.Token token) {
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerServiceDump("InputMethodService#applyVisibilityInInsetsConsumerIfNecessary", this.mDumper, null);
        this.mPrivOps.applyImeVisibilityAsync(z ? this.mCurShowInputToken : this.mCurHideInputToken, z, token);
    }

    private void finishViews(boolean z) {
        if (this.mInputViewStarted) {
            this.mInlineSuggestionSessionController.notifyOnFinishInputView();
            onFinishInputView(z);
        } else if (this.mCandidatesViewStarted) {
            onFinishCandidatesView(z);
        }
        this.mInputViewStarted = false;
        this.mCandidatesViewStarted = false;
    }

    private void hideWindowWithToken(int i) {
        this.mCurStatsToken = createStatsToken(false, i, android.view.inputmethod.ImeTracker.isFromUser(this.mRootView) || i == 29);
        hideWindow();
    }

    public void hideWindow() {
        android.view.inputmethod.ImeTracker.Token createStatsToken = this.mCurStatsToken != null ? this.mCurStatsToken : createStatsToken(false, 43, android.view.inputmethod.ImeTracker.isFromUser(this.mRootView));
        this.mCurStatsToken = null;
        android.view.inputmethod.ImeTracker.forLogging().onProgress(createStatsToken, 45);
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerServiceDump("InputMethodService#hideWindow", this.mDumper, null);
        setImeWindowStatus(0, this.mBackDisposition);
        applyVisibilityInInsetsConsumerIfNecessary(false, createStatsToken);
        this.mWindowVisible = false;
        finishViews(false);
        if (this.mDecorViewVisible) {
            if (this.mInputView != null) {
                this.mInputView.dispatchWindowVisibilityChanged(8);
            }
            this.mDecorViewVisible = false;
            onWindowHidden();
            this.mDecorViewWasVisible = false;
        }
        this.mLastWasInFullscreenMode = this.mIsFullscreen;
        updateFullscreenMode();
        unregisterCompatOnBackInvokedCallback();
    }

    public void onWindowShown() {
    }

    public void onWindowHidden() {
    }

    public void onBindInput() {
    }

    public void onUnbindInput() {
    }

    public void onStartInput(android.view.inputmethod.EditorInfo editorInfo, boolean z) {
    }

    void doFinishInput() {
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerServiceDump("InputMethodService#doFinishInput", this.mDumper, null);
        finishViews(true);
        if (this.mInputStarted) {
            this.mInlineSuggestionSessionController.notifyOnFinishInput();
            onFinishInput();
        }
        this.mInputStarted = false;
        this.mStartedInputConnection = null;
        this.mCurCompletions = null;
        if (!this.mOnPreparedStylusHwCalled) {
            finishStylusHandwriting();
        }
        unregisterCompatOnBackInvokedCallback();
    }

    void doStartInput(android.view.inputmethod.InputConnection inputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
        if (!z && this.mInputStarted) {
            doFinishInput();
        }
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerServiceDump("InputMethodService#doStartInput", this.mDumper, null);
        this.mInputStarted = true;
        this.mStartedInputConnection = inputConnection;
        if (android.view.inputmethod.Flags.useHandwritingListenerForTooltype()) {
            editorInfo.setInitialToolType(this.mLastUsedToolType);
        }
        this.mInputEditorInfo = editorInfo;
        initialize();
        this.mInlineSuggestionSessionController.notifyOnStartInput(editorInfo == null ? null : editorInfo.packageName, editorInfo != null ? editorInfo.autofillId : null);
        onStartInput(editorInfo, z);
        if (this.mDecorViewVisible) {
            if (this.mShowInputRequested) {
                this.mInputViewStarted = true;
                this.mInlineSuggestionSessionController.notifyOnStartInputView();
                onStartInputView(this.mInputEditorInfo, z);
                startExtractingText(true);
                return;
            }
            if (this.mCandidatesVisibility == 0) {
                this.mCandidatesViewStarted = true;
                onStartCandidatesView(this.mInputEditorInfo, z);
            }
        }
    }

    public void onFinishInput() {
        android.view.inputmethod.InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection != null) {
            currentInputConnection.finishComposingText();
        }
    }

    public void onDisplayCompletions(android.view.inputmethod.CompletionInfo[] completionInfoArr) {
    }

    public void onUpdateExtractedText(int i, android.view.inputmethod.ExtractedText extractedText) {
        if (this.mExtractedToken == i && extractedText != null && this.mExtractEditText != null) {
            this.mExtractedText = extractedText;
            this.mExtractEditText.setExtractedText(extractedText);
        }
    }

    public void onUpdateSelection(int i, int i2, int i3, int i4, int i5, int i6) {
        android.inputmethodservice.ExtractEditText extractEditText = this.mExtractEditText;
        if (extractEditText != null && isFullscreenMode() && this.mExtractedText != null) {
            int i7 = this.mExtractedText.startOffset;
            extractEditText.startInternalChanges();
            int i8 = i3 - i7;
            int i9 = i4 - i7;
            int length = extractEditText.getText().length();
            if (i8 < 0) {
                i8 = 0;
            } else if (i8 > length) {
                i8 = length;
            }
            if (i9 < 0) {
                i9 = 0;
            } else if (i9 > length) {
                i9 = length;
            }
            extractEditText.setSelection(i8, i9);
            extractEditText.finishInternalChanges();
        }
    }

    @java.lang.Deprecated
    public void onViewClicked(boolean z) {
    }

    public void onUpdateEditorToolType(int i) {
    }

    @java.lang.Deprecated
    public void onUpdateCursor(android.graphics.Rect rect) {
    }

    public void onUpdateCursorAnchorInfo(android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo) {
    }

    public void requestHideSelf(int i) {
        requestHideSelf(i, 5);
    }

    private void requestHideSelf(int i, int i2) {
        android.view.inputmethod.ImeTracker.Token createStatsToken = createStatsToken(false, i2, android.view.inputmethod.ImeTracker.isFromUser(this.mRootView) || i2 == 29);
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerServiceDump("InputMethodService#requestHideSelf", this.mDumper, null);
        this.mPrivOps.hideMySoftInput(createStatsToken, i, i2);
    }

    public final void requestShowSelf(int i) {
        requestShowSelf(i, 3);
    }

    private void requestShowSelf(int i, int i2) {
        android.view.inputmethod.ImeTracker.Token createStatsToken = createStatsToken(true, i2, android.view.inputmethod.ImeTracker.isFromUser(this.mRootView));
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerServiceDump("InputMethodService#requestShowSelf", this.mDumper, null);
        this.mPrivOps.showMySoftInput(createStatsToken, i, i2);
    }

    private boolean handleBack(boolean z) {
        if (this.mShowInputRequested) {
            if (z) {
                requestHideSelf(0, 29);
            }
            return true;
        }
        if (!this.mDecorViewVisible) {
            return false;
        }
        if (this.mCandidatesVisibility == 0) {
            if (z) {
                setCandidatesViewShown(false);
            }
        } else if (z) {
            hideWindowWithToken(29);
        }
        return true;
    }

    private android.inputmethodservice.ExtractEditText getExtractEditTextIfVisible() {
        if (!isExtractViewShown() || !isInputViewShown()) {
            return null;
        }
        return this.mExtractEditText;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (android.view.inputmethod.Flags.useHandwritingListenerForTooltype()) {
            updateEditorToolTypeInternal(0);
        }
        if (keyEvent.getKeyCode() == 4) {
            android.inputmethodservice.ExtractEditText extractEditTextIfVisible = getExtractEditTextIfVisible();
            if (extractEditTextIfVisible != null && extractEditTextIfVisible.handleBackInTextActionModeIfNeeded(keyEvent)) {
                return true;
            }
            if (!handleBack(false)) {
                return false;
            }
            keyEvent.startTracking();
            return true;
        }
        if (keyEvent.getKeyCode() == 62 && android.view.KeyEvent.metaStateHasModifiers(keyEvent.getMetaState() & (-194), 4096) && this.mDecorViewVisible && this.mWindowVisible) {
            this.mPrivOps.switchKeyboardLayoutAsync((keyEvent.getMetaState() & 193) == 0 ? 1 : -1);
            return true;
        }
        if (keyEvent.getKeyCode() == 24) {
            if (!isInputViewShown() || this.mVolumeKeyCursorControl == 0) {
                return false;
            }
            sendDownUpKeyEvents(this.mVolumeKeyCursorControl != 2 ? 21 : 22);
            return true;
        }
        if (keyEvent.getKeyCode() == 25) {
            if (!isInputViewShown() || this.mVolumeKeyCursorControl == 0) {
                return false;
            }
            sendDownUpKeyEvents(this.mVolumeKeyCursorControl == 2 ? 21 : 22);
            return true;
        }
        return doMovementKey(i, keyEvent, -1);
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, android.view.KeyEvent keyEvent) {
        return doMovementKey(i, keyEvent, i2);
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4) {
            android.inputmethodservice.ExtractEditText extractEditTextIfVisible = getExtractEditTextIfVisible();
            if (extractEditTextIfVisible != null && extractEditTextIfVisible.handleBackInTextActionModeIfNeeded(keyEvent)) {
                return true;
            }
            if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                return handleBack(true);
            }
        }
        if (keyEvent.getKeyCode() == 24 || i == 25) {
            return isInputViewShown() && this.mVolumeKeyCursorControl != 0;
        }
        return doMovementKey(i, keyEvent, -2);
    }

    @Override // android.inputmethodservice.AbstractInputMethodService
    public boolean onTrackballEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    @Override // android.inputmethodservice.AbstractInputMethodService
    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    public void onAppPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onToggleSoftInput(int i, int i2) {
        if (isInputViewShown()) {
            requestHideSelf(i2, 30);
        } else {
            requestShowSelf(i, 53);
        }
    }

    void reportExtractedMovement(int i, int i2) {
        int i3 = 0;
        switch (i) {
            case 19:
                i3 = -i2;
                i2 = 0;
                break;
            case 20:
                i3 = i2;
                i2 = 0;
                break;
            case 21:
                i2 = -i2;
                break;
            case 22:
                break;
            default:
                i2 = 0;
                break;
        }
        onExtractedCursorMovement(i2, i3);
    }

    boolean doMovementKey(int i, android.view.KeyEvent keyEvent, int i2) {
        android.inputmethodservice.ExtractEditText extractEditTextIfVisible = getExtractEditTextIfVisible();
        if (extractEditTextIfVisible != null) {
            android.text.method.MovementMethod movementMethod = extractEditTextIfVisible.getMovementMethod();
            android.text.Layout layout = extractEditTextIfVisible.getLayout();
            if (movementMethod != null && layout != null) {
                if (i2 == -1) {
                    if (movementMethod.onKeyDown(extractEditTextIfVisible, extractEditTextIfVisible.getText(), i, keyEvent)) {
                        reportExtractedMovement(i, 1);
                        return true;
                    }
                } else if (i2 == -2) {
                    if (movementMethod.onKeyUp(extractEditTextIfVisible, extractEditTextIfVisible.getText(), i, keyEvent)) {
                        return true;
                    }
                } else if (movementMethod.onKeyOther(extractEditTextIfVisible, extractEditTextIfVisible.getText(), keyEvent)) {
                    reportExtractedMovement(i, i2);
                } else {
                    android.view.KeyEvent changeAction = android.view.KeyEvent.changeAction(keyEvent, 0);
                    if (movementMethod.onKeyDown(extractEditTextIfVisible, extractEditTextIfVisible.getText(), i, changeAction)) {
                        android.view.KeyEvent changeAction2 = android.view.KeyEvent.changeAction(keyEvent, 1);
                        movementMethod.onKeyUp(extractEditTextIfVisible, extractEditTextIfVisible.getText(), i, changeAction2);
                        while (true) {
                            i2--;
                            if (i2 <= 0) {
                                break;
                            }
                            movementMethod.onKeyDown(extractEditTextIfVisible, extractEditTextIfVisible.getText(), i, changeAction);
                            movementMethod.onKeyUp(extractEditTextIfVisible, extractEditTextIfVisible.getText(), i, changeAction2);
                        }
                        reportExtractedMovement(i, i2);
                    }
                }
            }
            switch (i) {
            }
            return true;
        }
        return false;
    }

    public void sendDownUpKeyEvents(int i) {
        android.view.inputmethod.InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection == null) {
            return;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        currentInputConnection.sendKeyEvent(new android.view.KeyEvent(uptimeMillis, uptimeMillis, 0, i, 0, 0, -1, 0, 6));
        currentInputConnection.sendKeyEvent(new android.view.KeyEvent(uptimeMillis, android.os.SystemClock.uptimeMillis(), 1, i, 0, 0, -1, 0, 6));
    }

    public boolean sendDefaultEditorAction(boolean z) {
        android.view.inputmethod.EditorInfo currentInputEditorInfo = getCurrentInputEditorInfo();
        if (currentInputEditorInfo == null) {
            return false;
        }
        if ((!z || (currentInputEditorInfo.imeOptions & 1073741824) == 0) && (currentInputEditorInfo.imeOptions & 255) != 1) {
            android.view.inputmethod.InputConnection currentInputConnection = getCurrentInputConnection();
            if (currentInputConnection != null) {
                currentInputConnection.performEditorAction(currentInputEditorInfo.imeOptions & 255);
            }
            return true;
        }
        return false;
    }

    public void sendKeyChar(char c) {
        switch (c) {
            case '\n':
                if (!sendDefaultEditorAction(true)) {
                    sendDownUpKeyEvents(66);
                    break;
                }
                break;
            default:
                if (c >= '0' && c <= '9') {
                    sendDownUpKeyEvents((c - '0') + 7);
                    break;
                } else {
                    android.view.inputmethod.InputConnection currentInputConnection = getCurrentInputConnection();
                    if (currentInputConnection != null) {
                        currentInputConnection.commitText(java.lang.String.valueOf(c), 1);
                        break;
                    }
                }
                break;
        }
    }

    public void onExtractedSelectionChanged(int i, int i2) {
        android.view.inputmethod.InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection != null) {
            currentInputConnection.setSelection(i, i2);
        }
    }

    public void onExtractedDeleteText(int i, int i2) {
        android.view.inputmethod.InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection != null) {
            currentInputConnection.finishComposingText();
            currentInputConnection.setSelection(i, i);
            currentInputConnection.deleteSurroundingText(0, i2 - i);
        }
    }

    public void onExtractedReplaceText(int i, int i2, java.lang.CharSequence charSequence) {
        android.view.inputmethod.InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection != null) {
            currentInputConnection.setComposingRegion(i, i2);
            currentInputConnection.commitText(charSequence, 1);
        }
    }

    public void onExtractedSetSpan(java.lang.Object obj, int i, int i2, int i3) {
        android.view.inputmethod.InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection == null || !currentInputConnection.setSelection(i, i2)) {
            return;
        }
        java.lang.CharSequence selectedText = currentInputConnection.getSelectedText(1);
        if (selectedText instanceof android.text.Spannable) {
            ((android.text.Spannable) selectedText).setSpan(obj, 0, selectedText.length(), i3);
            currentInputConnection.setComposingRegion(i, i2);
            currentInputConnection.commitText(selectedText, 1);
        }
    }

    public void onExtractedTextClicked() {
        if (this.mExtractEditText != null && this.mExtractEditText.hasVerticalScrollBar()) {
            setCandidatesViewShown(false);
        }
    }

    public void onExtractedCursorMovement(int i, int i2) {
        if (this.mExtractEditText != null && i2 != 0 && this.mExtractEditText.hasVerticalScrollBar()) {
            setCandidatesViewShown(false);
        }
    }

    public boolean onExtractTextContextMenuItem(int i) {
        android.view.inputmethod.InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection != null) {
            currentInputConnection.performContextMenuAction(i);
            return true;
        }
        return true;
    }

    public java.lang.CharSequence getTextForImeAction(int i) {
        switch (i & 255) {
            case 1:
                return null;
            case 2:
                return getText(com.android.internal.R.string.ime_action_go);
            case 3:
                return getText(com.android.internal.R.string.ime_action_search);
            case 4:
                return getText(com.android.internal.R.string.ime_action_send);
            case 5:
                return getText(com.android.internal.R.string.ime_action_next);
            case 6:
                return getText(com.android.internal.R.string.ime_action_done);
            case 7:
                return getText(com.android.internal.R.string.ime_action_previous);
            default:
                return getText(com.android.internal.R.string.ime_action_default);
        }
    }

    private int getIconForImeAction(int i) {
        switch (i & 255) {
            case 2:
                return com.android.internal.R.drawable.ic_input_extract_action_go;
            case 3:
                return com.android.internal.R.drawable.ic_input_extract_action_search;
            case 4:
                return com.android.internal.R.drawable.ic_input_extract_action_send;
            case 5:
                return com.android.internal.R.drawable.ic_input_extract_action_next;
            case 6:
                return com.android.internal.R.drawable.ic_input_extract_action_done;
            case 7:
                return com.android.internal.R.drawable.ic_input_extract_action_previous;
            default:
                return com.android.internal.R.drawable.ic_input_extract_action_return;
        }
    }

    public void onUpdateExtractingVisibility(android.view.inputmethod.EditorInfo editorInfo) {
        if (editorInfo.inputType == 0 || (editorInfo.imeOptions & 268435456) != 0) {
            setExtractViewShown(false);
        } else {
            setExtractViewShown(true);
        }
    }

    public void onUpdateExtractingViews(android.view.inputmethod.EditorInfo editorInfo) {
        if (!isExtractViewShown() || this.mExtractAccessories == null) {
            return;
        }
        boolean z = true;
        if (editorInfo.actionLabel == null && ((editorInfo.imeOptions & 255) == 1 || (editorInfo.imeOptions & 536870912) != 0 || editorInfo.inputType == 0)) {
            z = false;
        }
        if (z) {
            this.mExtractAccessories.setVisibility(0);
            if (this.mExtractAction != null) {
                if (this.mExtractAction instanceof android.widget.ImageButton) {
                    ((android.widget.ImageButton) this.mExtractAction).setImageResource(getIconForImeAction(editorInfo.imeOptions));
                    if (editorInfo.actionLabel != null) {
                        this.mExtractAction.setContentDescription(editorInfo.actionLabel);
                    } else {
                        this.mExtractAction.setContentDescription(getTextForImeAction(editorInfo.imeOptions));
                    }
                } else if (editorInfo.actionLabel != null) {
                    ((android.widget.TextView) this.mExtractAction).lambda$setTextAsync$0(editorInfo.actionLabel);
                } else {
                    ((android.widget.TextView) this.mExtractAction).lambda$setTextAsync$0(getTextForImeAction(editorInfo.imeOptions));
                }
                this.mExtractAction.setOnClickListener(this.mActionClickListener);
                return;
            }
            return;
        }
        this.mExtractAccessories.setVisibility(8);
        if (this.mExtractAction != null) {
            this.mExtractAction.setOnClickListener(null);
        }
    }

    public void onExtractingInputChanged(android.view.inputmethod.EditorInfo editorInfo) {
        if (editorInfo.inputType == 0) {
            requestHideSelf(2, 31);
        }
    }

    void startExtractingText(boolean z) {
        android.inputmethodservice.ExtractEditText extractEditText = this.mExtractEditText;
        if (extractEditText != null && getCurrentInputStarted() && isFullscreenMode()) {
            this.mExtractedToken++;
            android.view.inputmethod.ExtractedTextRequest extractedTextRequest = new android.view.inputmethod.ExtractedTextRequest();
            extractedTextRequest.token = this.mExtractedToken;
            extractedTextRequest.flags = 1;
            extractedTextRequest.hintMaxLines = 10;
            extractedTextRequest.hintMaxChars = 10000;
            android.view.inputmethod.InputConnection currentInputConnection = getCurrentInputConnection();
            this.mExtractedText = currentInputConnection == null ? null : currentInputConnection.getExtractedText(extractedTextRequest, 1);
            if (this.mExtractedText == null || currentInputConnection == null) {
                android.util.Log.e(TAG, "Unexpected null in startExtractingText : mExtractedText = " + this.mExtractedText + ", input connection = " + currentInputConnection);
            }
            android.view.inputmethod.EditorInfo currentInputEditorInfo = getCurrentInputEditorInfo();
            try {
                extractEditText.startInternalChanges();
                onUpdateExtractingVisibility(currentInputEditorInfo);
                onUpdateExtractingViews(currentInputEditorInfo);
                int i = currentInputEditorInfo.inputType;
                if ((i & 15) == 1 && (262144 & i) != 0) {
                    i |= 131072;
                }
                extractEditText.setInputType(i);
                extractEditText.setHint(currentInputEditorInfo.hintText);
                if (this.mExtractedText != null) {
                    extractEditText.setEnabled(true);
                    extractEditText.setExtractedText(this.mExtractedText);
                } else {
                    extractEditText.setEnabled(false);
                    extractEditText.lambda$setTextAsync$0("");
                }
                if (z) {
                    onExtractingInputChanged(currentInputEditorInfo);
                }
            } finally {
                extractEditText.finishInternalChanges();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnCurrentInputMethodSubtypeChanged(android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        synchronized (this.mLock) {
            this.mNotifyUserActionSent = false;
        }
        onCurrentInputMethodSubtypeChanged(inputMethodSubtype);
    }

    protected void onCurrentInputMethodSubtypeChanged(android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
    }

    @java.lang.Deprecated
    public int getInputMethodWindowRecommendedHeight() {
        android.util.Log.w(TAG, "getInputMethodWindowRecommendedHeight() is deprecated and now always returns 0. Do not use this method.");
        return 0;
    }

    public final boolean isImeNavigationBarShownForTesting() {
        return this.mNavigationBarController.isShown();
    }

    @Override // android.inputmethodservice.AbstractInputMethodService
    final android.inputmethodservice.InputMethodServiceInternal createInputMethodServiceInternal() {
        return new android.inputmethodservice.InputMethodServiceInternal() { // from class: android.inputmethodservice.InputMethodService.1
            @Override // android.inputmethodservice.InputMethodServiceInternal
            public android.content.Context getContext() {
                return android.inputmethodservice.InputMethodService.this;
            }

            @Override // android.inputmethodservice.InputMethodServiceInternal
            public void exposeContent(android.view.inputmethod.InputContentInfo inputContentInfo, android.view.inputmethod.InputConnection inputConnection) {
                if (inputConnection == null || android.inputmethodservice.InputMethodService.this.getCurrentInputConnection() != inputConnection) {
                    return;
                }
                exposeContentInternal(inputContentInfo, android.inputmethodservice.InputMethodService.this.getCurrentInputEditorInfo());
            }

            @Override // android.inputmethodservice.InputMethodServiceInternal
            public void notifyUserActionIfNecessary() {
                synchronized (android.inputmethodservice.InputMethodService.this.mLock) {
                    if (android.inputmethodservice.InputMethodService.this.mNotifyUserActionSent) {
                        return;
                    }
                    android.inputmethodservice.InputMethodService.this.mPrivOps.notifyUserActionAsync();
                    android.inputmethodservice.InputMethodService.this.mNotifyUserActionSent = true;
                }
            }

            private void exposeContentInternal(android.view.inputmethod.InputContentInfo inputContentInfo, android.view.inputmethod.EditorInfo editorInfo) {
                android.net.Uri contentUri = inputContentInfo.getContentUri();
                com.android.internal.inputmethod.IInputContentUriToken createInputContentUriToken = android.inputmethodservice.InputMethodService.this.mPrivOps.createInputContentUriToken(contentUri, editorInfo.packageName);
                if (createInputContentUriToken == null) {
                    android.util.Log.e(android.inputmethodservice.InputMethodService.TAG, "createInputContentAccessToken failed. contentUri=" + contentUri.toString() + " packageName=" + editorInfo.packageName);
                } else {
                    inputContentInfo.setUriToken(createInputContentUriToken);
                }
            }

            @Override // android.inputmethodservice.InputMethodServiceInternal
            public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
                android.inputmethodservice.InputMethodService.this.dump(fileDescriptor, printWriter, strArr);
            }

            @Override // android.inputmethodservice.InputMethodServiceInternal
            public void triggerServiceDump(java.lang.String str, byte[] bArr) {
                com.android.internal.inputmethod.ImeTracing.getInstance().triggerServiceDump(str, android.inputmethodservice.InputMethodService.this.mDumper, bArr);
            }

            @Override // android.inputmethodservice.InputMethodServiceInternal
            public boolean isServiceDestroyed() {
                return android.inputmethodservice.InputMethodService.this.mDestroyed;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int mapToImeWindowStatus() {
        return (isInputViewShown() ? 2 : 0) | 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.inputmethod.ImeTracker.Token createStatsToken(boolean z, int i, boolean z2) {
        return android.view.inputmethod.ImeTracker.forLogging().onStart(z ? 1 : 2, 7, i, z2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.inputmethodservice.AbstractInputMethodService, android.app.Service
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        android.util.PrintWriterPrinter printWriterPrinter = new android.util.PrintWriterPrinter(printWriter);
        printWriterPrinter.println("Input method service state for " + this + ":");
        printWriterPrinter.println("  mViewsCreated=" + this.mViewsCreated);
        printWriterPrinter.println("  mDecorViewVisible=" + this.mDecorViewVisible + " mDecorViewWasVisible=" + this.mDecorViewWasVisible + " mWindowVisible=" + this.mWindowVisible + " mInShowWindow=" + this.mInShowWindow);
        printWriterPrinter.println("  Configuration=" + getResources().getConfiguration());
        printWriterPrinter.println("  mToken=" + this.mToken);
        printWriterPrinter.println("  mInputBinding=" + this.mInputBinding);
        printWriterPrinter.println("  mInputConnection=" + this.mInputConnection);
        printWriterPrinter.println("  mStartedInputConnection=" + this.mStartedInputConnection);
        printWriterPrinter.println("  mInputStarted=" + this.mInputStarted + " mInputViewStarted=" + this.mInputViewStarted + " mCandidatesViewStarted=" + this.mCandidatesViewStarted);
        if (this.mInputEditorInfo != null) {
            printWriterPrinter.println("  mInputEditorInfo:");
            this.mInputEditorInfo.dump(printWriterPrinter, "    ", false);
        } else {
            printWriterPrinter.println("  mInputEditorInfo: null");
        }
        printWriterPrinter.println("  mShowInputRequested=" + this.mShowInputRequested + " mLastShowInputRequested=" + this.mLastShowInputRequested + " mShowInputFlags=0x" + java.lang.Integer.toHexString(this.mShowInputFlags));
        printWriterPrinter.println("  mCandidatesVisibility=" + this.mCandidatesVisibility + " mFullscreenApplied=" + this.mFullscreenApplied + " mIsFullscreen=" + this.mIsFullscreen + " mExtractViewHidden=" + this.mExtractViewHidden);
        if (this.mExtractedText != null) {
            printWriterPrinter.println("  mExtractedText:");
            printWriterPrinter.println("    text=" + this.mExtractedText.text.length() + " chars startOffset=" + this.mExtractedText.startOffset);
            printWriterPrinter.println("    selectionStart=" + this.mExtractedText.selectionStart + " selectionEnd=" + this.mExtractedText.selectionEnd + " flags=0x" + java.lang.Integer.toHexString(this.mExtractedText.flags));
        } else {
            printWriterPrinter.println("  mExtractedText: null");
        }
        printWriterPrinter.println("  mExtractedToken=" + this.mExtractedToken);
        printWriterPrinter.println("  mIsInputViewShown=" + this.mIsInputViewShown + " mStatusIcon=" + this.mStatusIcon);
        printWriterPrinter.println("  Last computed insets:");
        printWriterPrinter.println("    contentTopInsets=" + this.mTmpInsets.contentTopInsets + " visibleTopInsets=" + this.mTmpInsets.visibleTopInsets + " touchableInsets=" + this.mTmpInsets.touchableInsets + " touchableRegion=" + this.mTmpInsets.touchableRegion);
        printWriterPrinter.println("  mSettingsObserver=" + this.mSettingsObserver);
        printWriterPrinter.println("  mNavigationBarController=" + this.mNavigationBarController.toDebugString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void compatHandleBack() {
        if (!this.mDecorViewVisible) {
            android.util.Log.e(TAG, "Back callback invoked on a hidden IME. Removing the callback...");
            unregisterCompatOnBackInvokedCallback();
        } else {
            android.view.KeyEvent createBackKeyEvent = createBackKeyEvent(0, false);
            onKeyDown(4, createBackKeyEvent);
            onKeyUp(4, createBackKeyEvent(1, (createBackKeyEvent.getFlags() & 1073741824) != 0));
        }
    }

    private boolean methodIsOverridden(java.lang.String str, java.lang.Class<?>... clsArr) {
        try {
            return getClass().getMethod(str, clsArr).getDeclaringClass() != android.inputmethodservice.InputMethodService.class;
        } catch (java.lang.NoSuchMethodException e) {
            throw new java.lang.RuntimeException("Method must exist.", e);
        }
    }
}
