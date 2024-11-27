package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class InputMethodManager {
    private static final java.lang.String CACHE_KEY_CONNECTIONLESS_STYLUS_HANDWRITING_PROPERTY = "cache_key.system_server.connectionless_stylus_handwriting";
    private static final java.lang.String CACHE_KEY_STYLUS_HANDWRITING_PROPERTY = "cache_key.system_server.stylus_handwriting";
    public static final long CLEAR_SHOW_FORCED_FLAG_WHEN_LEAVING = 214016041;
    private static final boolean DEBUG = false;
    public static final int DISPATCH_HANDLED = 1;
    public static final int DISPATCH_IN_PROGRESS = -1;
    public static final int DISPATCH_NOT_HANDLED = 0;
    public static final int HANDWRITING_DELEGATE_FLAG_HOME_DELEGATOR_ALLOWED = 1;
    public static final int HIDE_IMPLICIT_ONLY = 1;
    public static final int HIDE_NOT_ALWAYS = 2;
    private static final long INPUT_METHOD_NOT_RESPONDING_TIMEOUT = 2500;
    private static final int MSG_BIND = 2;
    private static final int MSG_BIND_ACCESSIBILITY_SERVICE = 11;
    private static final int MSG_DUMP = 1;
    private static final int MSG_FLUSH_INPUT_EVENT = 7;
    private static final int MSG_ON_SHOW_REQUESTED = 31;
    private static final int MSG_REPORT_FULLSCREEN_MODE = 10;
    private static final int MSG_SEND_INPUT_EVENT = 5;
    private static final int MSG_SET_ACTIVE = 4;
    private static final int MSG_SET_INTERACTIVE = 13;
    private static final int MSG_START_INPUT_RESULT = 40;
    private static final int MSG_TIMEOUT_INPUT_EVENT = 6;
    private static final int MSG_UNBIND = 3;
    private static final int MSG_UNBIND_ACCESSIBILITY_SERVICE = 12;
    private static final int NOT_A_SUBTYPE_ID = -1;
    private static final java.lang.String PENDING_EVENT_COUNTER = "aq:imm";
    private static final int REQUEST_UPDATE_CURSOR_ANCHOR_INFO_NONE = 0;
    public static final int RESULT_HIDDEN = 3;
    public static final int RESULT_SHOWN = 2;
    public static final int RESULT_UNCHANGED_HIDDEN = 1;
    public static final int RESULT_UNCHANGED_SHOWN = 0;

    @java.lang.Deprecated
    public static final int SHOW_FORCED = 2;
    public static final int SHOW_IMPLICIT = 1;
    public static final int SHOW_IM_PICKER_MODE_AUTO = 0;
    public static final int SHOW_IM_PICKER_MODE_EXCLUDE_AUXILIARY_SUBTYPES = 2;
    public static final int SHOW_IM_PICKER_MODE_INCLUDE_AUXILIARY_SUBTYPES = 1;
    private static final java.lang.String SUBTYPE_MODE_VOICE = "voice";
    private static final java.lang.String TAG = "InputMethodManager";

    @java.lang.Deprecated
    static android.view.inputmethod.InputMethodManager sInstance;
    private static boolean sPreventImeStartupUnlessTextEditor;
    private android.view.inputmethod.CompletionInfo[] mCompletions;
    private android.app.PropertyInvalidatedCache<java.lang.Integer, java.lang.Boolean> mConnectionlessStylusHandwritingAvailableCache;
    private android.view.inputmethod.InputMethodManager.BindState mCurBindState;
    private android.view.InputChannel mCurChannel;

    @java.lang.Deprecated
    java.lang.String mCurId;

    @java.lang.Deprecated
    com.android.internal.inputmethod.IInputMethodSession mCurMethod;
    android.view.ViewRootImpl mCurRootView;
    boolean mCurRootViewWindowFocused;
    private android.view.inputmethod.InputMethodManager.ImeInputEventSender mCurSender;
    private android.view.inputmethod.EditorInfo mCurrentEditorInfo;
    private int mCursorCandEnd;
    private int mCursorCandStart;
    private int mCursorSelEnd;
    private int mCursorSelStart;
    private final int mDisplayId;
    private final android.view.inputmethod.RemoteInputConnectionImpl mFallbackInputConnection;
    private boolean mFullscreenMode;
    final android.view.inputmethod.InputMethodManager.H mH;
    private android.view.ImeInsetsSourceConsumer mImeInsetsConsumer;
    private int mInitialSelEnd;
    private int mInitialSelStart;
    private final android.os.Looper mMainLooper;
    private android.view.View mNextServedView;
    private android.view.inputmethod.ViewFocusParameterInfo mPreviousViewFocusParameters;
    private android.view.inputmethod.InputMethodManager.ReportInputConnectionOpenedRunner mReportInputConnectionOpenedRunner;
    private boolean mServedConnecting;
    private android.view.inputmethod.RemoteInputConnectionImpl mServedInputConnection;
    private android.os.Handler mServedInputConnectionHandler;
    private android.view.View mServedView;

    @java.lang.Deprecated
    final com.android.internal.view.IInputMethodManager mService;
    private android.app.PropertyInvalidatedCache<java.lang.Integer, java.lang.Boolean> mStylusHandwritingAvailableCache;
    private static final java.lang.Object sLock = new java.lang.Object();
    private static final android.util.SparseArray<android.view.inputmethod.InputMethodManager> sInstanceMap = new android.util.SparseArray<>();
    private static final boolean OPTIMIZE_NONEDITABLE_VIEWS = android.os.SystemProperties.getBoolean("debug.imm.optimize_noneditable_views", true);
    private final android.window.ImeOnBackInvokedDispatcher mImeDispatcher = new android.window.ImeOnBackInvokedDispatcher(android.os.Handler.getMain()) { // from class: android.view.inputmethod.InputMethodManager.1
        @Override // android.window.ImeOnBackInvokedDispatcher
        public android.window.WindowOnBackInvokedDispatcher getReceivingDispatcher() {
            android.window.WindowOnBackInvokedDispatcher onBackInvokedDispatcher;
            synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                onBackInvokedDispatcher = android.view.inputmethod.InputMethodManager.this.mCurRootView != null ? android.view.inputmethod.InputMethodManager.this.mCurRootView.getOnBackInvokedDispatcher() : null;
            }
            return onBackInvokedDispatcher;
        }
    };
    private boolean mActive = false;
    private boolean mRestartOnNextWindowFocus = true;
    android.graphics.Rect mTmpCursorRect = new android.graphics.Rect();
    android.graphics.Rect mCursorRect = new android.graphics.Rect();
    private android.view.inputmethod.CursorAnchorInfo mCursorAnchorInfo = null;
    private final android.util.SparseArray<android.view.inputmethod.IAccessibilityInputMethodSessionInvoker> mAccessibilityInputMethodSession = new android.util.SparseArray<>();

    @java.lang.Deprecated
    private int mRequestUpdateCursorAnchorInfoMonitorMode = 0;
    private final android.util.Pools.Pool<android.view.inputmethod.InputMethodManager.PendingEvent> mPendingEventPool = new android.util.Pools.SimplePool(20);
    private final android.util.SparseArray<android.view.inputmethod.InputMethodManager.PendingEvent> mPendingEvents = new android.util.SparseArray<>(20);
    private final android.view.inputmethod.InputMethodManager.DelegateImpl mDelegate = new android.view.inputmethod.InputMethodManager.DelegateImpl();
    private final com.android.internal.inputmethod.IInputMethodClient.Stub mClient = new com.android.internal.inputmethod.IInputMethodClient.Stub() { // from class: android.view.inputmethod.InputMethodManager.2
        @Override // android.os.Binder
        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = fileDescriptor;
            obtain.arg2 = printWriter;
            obtain.arg3 = strArr;
            obtain.arg4 = countDownLatch;
            android.view.inputmethod.InputMethodManager.this.mH.sendMessage(android.view.inputmethod.InputMethodManager.this.mH.obtainMessage(1, obtain));
            try {
                if (!countDownLatch.await(5L, java.util.concurrent.TimeUnit.SECONDS)) {
                    printWriter.println("Timeout waiting for dump");
                }
            } catch (java.lang.InterruptedException e) {
                printWriter.println("Interrupted waiting for dump");
            }
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onBindMethod(com.android.internal.inputmethod.InputBindResult inputBindResult) {
            android.view.inputmethod.InputMethodManager.this.mH.obtainMessage(2, inputBindResult).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onStartInputResult(com.android.internal.inputmethod.InputBindResult inputBindResult, int i) {
            android.view.inputmethod.InputMethodManager.this.mH.obtainMessage(40, i, -1, inputBindResult).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onBindAccessibilityService(com.android.internal.inputmethod.InputBindResult inputBindResult, int i) {
            android.view.inputmethod.InputMethodManager.this.mH.obtainMessage(11, i, 0, inputBindResult).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onUnbindMethod(int i, int i2) {
            android.view.inputmethod.InputMethodManager.this.mH.obtainMessage(3, i, i2).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onUnbindAccessibilityService(int i, int i2) {
            android.view.inputmethod.InputMethodManager.this.mH.obtainMessage(12, i, i2).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setActive(boolean z, boolean z2) {
            android.view.inputmethod.InputMethodManager.this.mH.obtainMessage(4, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setInteractive(boolean z, boolean z2) {
            android.view.inputmethod.InputMethodManager.this.mH.obtainMessage(13, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void scheduleStartInputIfNecessary(boolean z) {
            android.view.inputmethod.InputMethodManager.this.mH.obtainMessage(4, 0, z ? 1 : 0).sendToTarget();
            android.view.inputmethod.InputMethodManager.this.mH.obtainMessage(4, 1, z ? 1 : 0).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void reportFullscreenMode(boolean z) {
            android.view.inputmethod.InputMethodManager.this.mH.obtainMessage(10, z ? 1 : 0, 0).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setImeTraceEnabled(boolean z) {
            com.android.internal.inputmethod.ImeTracing.getInstance().setEnabled(z);
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void throwExceptionFromSystem(java.lang.String str) {
            throw new java.lang.RuntimeException(str);
        }
    };
    final java.util.concurrent.atomic.AtomicBoolean mRequestCursorUpdateDisplayIdCheck = new java.util.concurrent.atomic.AtomicBoolean(true);

    public interface FinishedInputEventCallback {
        void onFinishedInputEvent(java.lang.Object obj, boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HandwritingDelegateFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HideFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShowFlags {
    }

    private static abstract class ReportInputConnectionOpenedRunner implements java.lang.Runnable {
        int mSequenceNum;

        ReportInputConnectionOpenedRunner(int i) {
            this.mSequenceNum = i;
        }
    }

    public static void ensureDefaultInstanceForDefaultDisplayIfNecessary() {
        if (!android.app.ActivityThread.isSystem()) {
            forContextInternal(0, android.os.Looper.getMainLooper());
        }
    }

    public static void invalidateLocalStylusHandwritingAvailabilityCaches() {
        android.app.PropertyInvalidatedCache.invalidateCache(CACHE_KEY_STYLUS_HANDWRITING_PROPERTY);
    }

    public static void invalidateLocalConnectionlessStylusHandwritingAvailabilityCaches() {
        android.app.PropertyInvalidatedCache.invalidateCache(CACHE_KEY_CONNECTIONLESS_STYLUS_HANDWRITING_PROPERTY);
    }

    private static boolean isAutofillUIShowing(android.view.View view) {
        android.view.autofill.AutofillManager autofillManager = (android.view.autofill.AutofillManager) view.getContext().getSystemService(android.view.autofill.AutofillManager.class);
        return autofillManager != null && autofillManager.isAutofillUiShowing();
    }

    private android.view.inputmethod.InputMethodManager getFallbackInputMethodManagerIfNecessary(android.view.View view) {
        android.view.ViewRootImpl viewRootImpl;
        int displayId;
        if (view == null || (viewRootImpl = view.getViewRootImpl()) == null || (displayId = viewRootImpl.getDisplayId()) == this.mDisplayId) {
            return null;
        }
        android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) viewRootImpl.mContext.getSystemService(android.view.inputmethod.InputMethodManager.class);
        if (inputMethodManager == null) {
            android.util.Log.v(TAG, "b/117267690: Failed to get non-null fallback IMM. view=" + view);
            return null;
        }
        if (inputMethodManager.mDisplayId != displayId) {
            android.util.Log.v(TAG, "b/117267690: Failed to get fallback IMM with expected displayId=" + displayId + " actual IMM#displayId=" + inputMethodManager.mDisplayId + " view=" + view);
            return null;
        }
        android.util.Log.v(TAG, "b/117267690: Display ID mismatch found. ViewRootImpl displayId=" + displayId + " InputMethodManager displayId=" + this.mDisplayId + ". Use the right InputMethodManager instance to avoid performance overhead.", new java.lang.Throwable());
        return inputMethodManager;
    }

    android.content.Context getFallbackContextFromServedView() {
        synchronized (this.mH) {
            if (this.mCurRootView == null) {
                return null;
            }
            return this.mServedView != null ? this.mServedView.getContext() : null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean canStartInput(android.view.View view) {
        return view.hasWindowFocus() || isAutofillUIShowing(view);
    }

    public void reportPerceptible(android.os.IBinder iBinder, boolean z) {
        android.view.inputmethod.IInputMethodManagerGlobalInvoker.reportPerceptibleAsync(iBinder, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final class DelegateImpl implements android.view.ImeFocusController.InputMethodManagerDelegate {
        private DelegateImpl() {
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onPreWindowGainedFocus(android.view.ViewRootImpl viewRootImpl) {
            synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                setCurrentRootViewLocked(viewRootImpl);
                android.view.inputmethod.InputMethodManager.this.mCurRootViewWindowFocused = true;
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onPostWindowGainedFocus(android.view.View view, android.view.WindowManager.LayoutParams layoutParams) {
            boolean z;
            boolean z2;
            synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                z = true;
                android.view.inputmethod.InputMethodManager.this.onViewFocusChangedInternal(view, true);
                if ((android.view.inputmethod.InputMethodManager.this.mServedView == view) && !android.view.inputmethod.InputMethodManager.this.hasActiveInputConnectionInternal(view)) {
                    z2 = true;
                } else {
                    z2 = false;
                }
            }
            int i = layoutParams.softInputMode;
            int i2 = layoutParams.flags;
            int startInputFlags = android.view.inputmethod.InputMethodManager.this.getStartInputFlags(view, 0) | 8;
            com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("InputMethodManager.DelegateImpl#startInputAsyncOnWindowFocusGain", android.view.inputmethod.InputMethodManager.this, null);
            synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                if (android.view.inputmethod.InputMethodManager.this.mCurRootView == null) {
                    return;
                }
                if (!android.view.inputmethod.InputMethodManager.this.mRestartOnNextWindowFocus) {
                    z = z2;
                } else {
                    android.view.inputmethod.InputMethodManager.this.mRestartOnNextWindowFocus = false;
                }
                boolean checkFocusInternalLocked = android.view.inputmethod.InputMethodManager.this.checkFocusInternalLocked(z, android.view.inputmethod.InputMethodManager.this.mCurRootView);
                if (checkFocusInternalLocked && android.view.inputmethod.InputMethodManager.this.startInputOnWindowFocusGainInternal(1, view, startInputFlags, i, i2)) {
                    return;
                }
                synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                    android.os.Trace.traceBegin(32L, "IMM.startInputOrWindowGainedFocus");
                    android.view.inputmethod.IInputMethodManagerGlobalInvoker.startInputOrWindowGainedFocus(2, android.view.inputmethod.InputMethodManager.this.mClient, view.getWindowToken(), startInputFlags, i, i2, null, null, null, android.view.inputmethod.InputMethodManager.this.mCurRootView.mContext.getApplicationInfo().targetSdkVersion, android.os.UserHandle.myUserId(), android.view.inputmethod.InputMethodManager.this.mImeDispatcher);
                    android.os.Trace.traceEnd(32L);
                }
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onWindowLostFocus(android.view.ViewRootImpl viewRootImpl) {
            synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                if (android.view.inputmethod.InputMethodManager.this.mCurRootView == viewRootImpl) {
                    android.view.inputmethod.InputMethodManager.this.mCurRootViewWindowFocused = false;
                    android.view.inputmethod.InputMethodManager.this.clearCurRootViewIfNeeded();
                }
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onViewFocusChanged(android.view.View view, boolean z) {
            android.view.inputmethod.InputMethodManager.this.onViewFocusChangedInternal(view, z);
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onScheduledCheckFocus(android.view.ViewRootImpl viewRootImpl) {
            synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                if (android.view.inputmethod.InputMethodManager.this.checkFocusInternalLocked(false, viewRootImpl)) {
                    android.view.inputmethod.InputMethodManager.this.startInputOnWindowFocusGainInternal(3, null, 0, 0, 0);
                }
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onViewDetachedFromWindow(android.view.View view, android.view.ViewRootImpl viewRootImpl) {
            synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                if (android.view.inputmethod.InputMethodManager.this.mCurRootView != view.getViewRootImpl()) {
                    return;
                }
                if (android.view.inputmethod.InputMethodManager.this.mNextServedView == view) {
                    android.view.inputmethod.InputMethodManager.this.mNextServedView = null;
                }
                if (android.view.inputmethod.InputMethodManager.this.mServedView == view) {
                    viewRootImpl.dispatchCheckFocus();
                }
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onWindowDismissed(android.view.ViewRootImpl viewRootImpl) {
            synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                if (android.view.inputmethod.InputMethodManager.this.mCurRootView != viewRootImpl) {
                    return;
                }
                if (android.view.inputmethod.InputMethodManager.this.mServedView != null) {
                    android.view.inputmethod.InputMethodManager.this.finishInputLocked();
                }
                setCurrentRootViewLocked(null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCurrentRootViewLocked(android.view.ViewRootImpl viewRootImpl) {
            android.view.inputmethod.InputMethodManager.this.mImeDispatcher.switchRootView(android.view.inputmethod.InputMethodManager.this.mCurRootView, viewRootImpl);
            android.view.inputmethod.InputMethodManager.this.mCurRootView = viewRootImpl;
        }
    }

    public android.view.inputmethod.InputMethodManager.DelegateImpl getDelegate() {
        return this.mDelegate;
    }

    public boolean hasActiveInputConnection(android.view.View view) {
        boolean z;
        synchronized (this.mH) {
            z = this.mCurRootView != null && view != null && this.mServedView == view && this.mServedInputConnection != null && this.mServedInputConnection.isAssociatedWith(view) && isImeSessionAvailableLocked();
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasActiveInputConnectionInternal(android.view.View view) {
        synchronized (this.mH) {
            boolean z = false;
            if (hasServedByInputMethodLocked(view) && isImeSessionAvailableLocked()) {
                if (this.mServedInputConnection != null && this.mServedInputConnection.isAssociatedWith(view)) {
                    z = true;
                }
                return z;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean startInputOnWindowFocusGainInternal(int i, android.view.View view, int i2, int i3, int i4) {
        synchronized (this.mH) {
            this.mCurrentEditorInfo = null;
            this.mCompletions = null;
            this.mServedConnecting = true;
        }
        return startInputInner(i, view != null ? view.getWindowToken() : null, i2, i3, i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.View getServedViewLocked() {
        if (this.mCurRootView != null) {
            return this.mServedView;
        }
        return null;
    }

    private android.view.View getNextServedViewLocked() {
        if (this.mCurRootView != null) {
            return this.mNextServedView;
        }
        return null;
    }

    private boolean hasServedByInputMethodLocked(android.view.View view) {
        android.view.View servedViewLocked = getServedViewLocked();
        return servedViewLocked == view || (servedViewLocked != null && servedViewLocked.checkInputConnectionProxy(view));
    }

    class H extends android.os.Handler {
        H(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.view.inputmethod.IAccessibilityInputMethodSessionInvoker createOrNull;
            android.view.inputmethod.RemoteInputConnectionImpl remoteInputConnectionImpl = null;
            remoteInputConnectionImpl = null;
            switch (message.what) {
                case 1:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.view.inputmethod.InputMethodManager.this.doDump((java.io.FileDescriptor) someArgs.arg1, (java.io.PrintWriter) someArgs.arg2, (java.lang.String[]) someArgs.arg3);
                    } catch (java.lang.RuntimeException e) {
                        ((java.io.PrintWriter) someArgs.arg2).println("Exception: " + e);
                    }
                    synchronized (someArgs.arg4) {
                        ((java.util.concurrent.CountDownLatch) someArgs.arg4).countDown();
                    }
                    someArgs.recycle();
                    return;
                case 2:
                    com.android.internal.inputmethod.InputBindResult inputBindResult = (com.android.internal.inputmethod.InputBindResult) message.obj;
                    synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                        int bindSequenceLocked = android.view.inputmethod.InputMethodManager.this.getBindSequenceLocked();
                        if (bindSequenceLocked >= 0 && bindSequenceLocked == inputBindResult.sequence) {
                            android.view.inputmethod.InputMethodManager.this.mRequestUpdateCursorAnchorInfoMonitorMode = 0;
                            android.view.inputmethod.InputMethodManager.this.updateInputChannelLocked(inputBindResult.channel);
                            android.view.inputmethod.InputMethodManager.this.mCurMethod = inputBindResult.method;
                            android.view.inputmethod.InputMethodManager.this.mCurBindState = new android.view.inputmethod.InputMethodManager.BindState(inputBindResult);
                            android.view.inputmethod.InputMethodManager.this.mCurId = inputBindResult.id;
                            android.view.inputmethod.InputMethodManager.this.startInputInner(6, null, 0, 0, 0);
                            return;
                        }
                        android.util.Log.w(android.view.inputmethod.InputMethodManager.TAG, "Ignoring onBind: cur seq=" + bindSequenceLocked + ", given seq=" + inputBindResult.sequence);
                        if (inputBindResult.channel != null && inputBindResult.channel != android.view.inputmethod.InputMethodManager.this.mCurChannel) {
                            inputBindResult.channel.dispose();
                        }
                        return;
                    }
                case 3:
                    int i = message.arg1;
                    int i2 = message.arg2;
                    synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                        android.view.inputmethod.InputMethodManager.this.mImeDispatcher.clear();
                        if (android.view.inputmethod.InputMethodManager.this.getBindSequenceLocked() != i) {
                            return;
                        }
                        android.view.inputmethod.InputMethodManager.this.clearAllAccessibilityBindingLocked();
                        android.view.inputmethod.InputMethodManager.this.clearBindingLocked();
                        android.view.View servedViewLocked = android.view.inputmethod.InputMethodManager.this.getServedViewLocked();
                        if (servedViewLocked != null && servedViewLocked.isFocused()) {
                            android.view.inputmethod.InputMethodManager.this.mServedConnecting = true;
                        }
                        boolean z = android.view.inputmethod.InputMethodManager.this.mActive;
                        if (z) {
                            android.view.inputmethod.InputMethodManager.this.startInputInner(7, null, 0, 0, 0);
                            return;
                        }
                        return;
                    }
                case 4:
                    boolean z2 = message.arg1 != 0;
                    boolean z3 = message.arg2 != 0;
                    synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                        android.view.inputmethod.InputMethodManager.this.mActive = z2;
                        android.view.inputmethod.InputMethodManager.this.mFullscreenMode = z3;
                        if (!z2) {
                            android.view.inputmethod.InputMethodManager.this.mRestartOnNextWindowFocus = true;
                            android.view.inputmethod.InputMethodManager.this.mFallbackInputConnection.finishComposingTextFromImm();
                            if (android.view.inputmethod.InputMethodManager.this.clearCurRootViewIfNeeded()) {
                                return;
                            }
                        }
                        android.view.View servedViewLocked2 = android.view.inputmethod.InputMethodManager.this.getServedViewLocked();
                        if (servedViewLocked2 != null && android.view.inputmethod.InputMethodManager.canStartInput(servedViewLocked2)) {
                            if (android.view.inputmethod.InputMethodManager.this.mCurRootView == null) {
                                return;
                            }
                            if (android.view.inputmethod.InputMethodManager.this.checkFocusInternalLocked(android.view.inputmethod.InputMethodManager.this.mRestartOnNextWindowFocus, android.view.inputmethod.InputMethodManager.this.mCurRootView)) {
                                android.view.inputmethod.InputMethodManager.this.mCurrentEditorInfo = null;
                                android.view.inputmethod.InputMethodManager.this.mCompletions = null;
                                android.view.inputmethod.InputMethodManager.this.mServedConnecting = true;
                                android.view.inputmethod.InputMethodManager.this.startInputInner(z2 ? 8 : 9, null, 0, 0, 0);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                case 5:
                    android.view.inputmethod.InputMethodManager.this.sendInputEventAndReportResultOnMainLooper((android.view.inputmethod.InputMethodManager.PendingEvent) message.obj);
                    return;
                case 6:
                    android.view.inputmethod.InputMethodManager.this.finishedInputEvent(message.arg1, false, true);
                    return;
                case 7:
                    android.view.inputmethod.InputMethodManager.this.finishedInputEvent(message.arg1, false, false);
                    return;
                case 10:
                    boolean z4 = message.arg1 != 0;
                    synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                        if (android.view.inputmethod.InputMethodManager.this.mFullscreenMode != z4 && android.view.inputmethod.InputMethodManager.this.mServedInputConnection != null) {
                            remoteInputConnectionImpl = android.view.inputmethod.InputMethodManager.this.mServedInputConnection;
                            android.view.inputmethod.InputMethodManager.this.mFullscreenMode = z4;
                        }
                    }
                    if (remoteInputConnectionImpl != null) {
                        remoteInputConnectionImpl.dispatchReportFullscreenMode(z4);
                        return;
                    }
                    return;
                case 11:
                    int i3 = message.arg1;
                    com.android.internal.inputmethod.InputBindResult inputBindResult2 = (com.android.internal.inputmethod.InputBindResult) message.obj;
                    synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                        int bindSequenceLocked2 = android.view.inputmethod.InputMethodManager.this.getBindSequenceLocked();
                        if (bindSequenceLocked2 >= 0 && bindSequenceLocked2 == inputBindResult2.sequence) {
                            if (inputBindResult2.accessibilitySessions != null && (createOrNull = android.view.inputmethod.IAccessibilityInputMethodSessionInvoker.createOrNull(inputBindResult2.accessibilitySessions.get(i3))) != null) {
                                android.view.inputmethod.InputMethodManager.this.mAccessibilityInputMethodSession.put(i3, createOrNull);
                                if (android.view.inputmethod.InputMethodManager.this.mServedInputConnection != null) {
                                    createOrNull.updateSelection(android.view.inputmethod.InputMethodManager.this.mInitialSelStart, android.view.inputmethod.InputMethodManager.this.mInitialSelEnd, android.view.inputmethod.InputMethodManager.this.mCursorSelStart, android.view.inputmethod.InputMethodManager.this.mCursorSelEnd, android.view.inputmethod.InputMethodManager.this.mCursorCandStart, android.view.inputmethod.InputMethodManager.this.mCursorCandEnd);
                                } else {
                                    createOrNull.updateSelection(-1, -1, -1, -1, -1, -1);
                                }
                            }
                            android.view.inputmethod.InputMethodManager.this.startInputInner(12, null, 0, 0, 0);
                            return;
                        }
                        android.util.Log.w(android.view.inputmethod.InputMethodManager.TAG, "Ignoring onBind: cur seq=" + bindSequenceLocked2 + ", given seq=" + inputBindResult2.sequence);
                        if (inputBindResult2.channel != null && inputBindResult2.channel != android.view.inputmethod.InputMethodManager.this.mCurChannel) {
                            inputBindResult2.channel.dispose();
                        }
                        return;
                    }
                case 12:
                    int i4 = message.arg1;
                    int i5 = message.arg2;
                    synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                        if (android.view.inputmethod.InputMethodManager.this.getBindSequenceLocked() != i4) {
                            return;
                        }
                        android.view.inputmethod.InputMethodManager.this.clearAccessibilityBindingLocked(i5);
                        return;
                    }
                case 13:
                    boolean z5 = message.arg1 != 0;
                    boolean z6 = message.arg2 != 0;
                    synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                        android.view.inputmethod.InputMethodManager.this.mActive = z5;
                        android.view.inputmethod.InputMethodManager.this.mFullscreenMode = z6;
                        if (z5) {
                            android.view.View view = android.view.inputmethod.InputMethodManager.this.mCurRootView != null ? android.view.inputmethod.InputMethodManager.this.mCurRootView.getView() : null;
                            if (view == null) {
                                return;
                            }
                            final android.view.ViewRootImpl viewRootImpl = android.view.inputmethod.InputMethodManager.this.mCurRootView;
                            view.post(new java.lang.Runnable() { // from class: android.view.inputmethod.InputMethodManager$H$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.view.inputmethod.InputMethodManager.H.this.lambda$handleMessage$0(viewRootImpl);
                                }
                            });
                        } else {
                            android.view.inputmethod.InputMethodManager.this.finishInputLocked();
                            if (android.view.inputmethod.InputMethodManager.this.isImeSessionAvailableLocked()) {
                                android.view.inputmethod.InputMethodManager.this.mCurBindState.mImeSession.finishInput();
                            }
                            android.view.inputmethod.InputMethodManager.this.forAccessibilitySessionsLocked(new java.util.function.Consumer() { // from class: android.view.inputmethod.InputMethodManager$H$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    ((android.view.inputmethod.IAccessibilityInputMethodSessionInvoker) obj).finishInput();
                                }
                            });
                        }
                        return;
                    }
                case 31:
                    synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                        if (android.view.inputmethod.InputMethodManager.this.mImeInsetsConsumer != null) {
                            android.view.inputmethod.InputMethodManager.this.mImeInsetsConsumer.onShowRequested();
                        }
                    }
                    return;
                case 40:
                    com.android.internal.inputmethod.InputBindResult inputBindResult3 = (com.android.internal.inputmethod.InputBindResult) message.obj;
                    int i6 = message.arg1;
                    if (inputBindResult3 == null) {
                        return;
                    }
                    synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                        if (inputBindResult3.id != null) {
                            android.view.inputmethod.InputMethodManager.this.updateInputChannelLocked(inputBindResult3.channel);
                            android.view.inputmethod.InputMethodManager.this.mCurMethod = inputBindResult3.method;
                            android.view.inputmethod.InputMethodManager.this.mCurBindState = new android.view.inputmethod.InputMethodManager.BindState(inputBindResult3);
                            android.view.inputmethod.InputMethodManager.this.mAccessibilityInputMethodSession.clear();
                            if (inputBindResult3.accessibilitySessions != null) {
                                for (int i7 = 0; i7 < inputBindResult3.accessibilitySessions.size(); i7++) {
                                    android.view.inputmethod.IAccessibilityInputMethodSessionInvoker createOrNull2 = android.view.inputmethod.IAccessibilityInputMethodSessionInvoker.createOrNull(inputBindResult3.accessibilitySessions.valueAt(i7));
                                    if (createOrNull2 != null) {
                                        android.view.inputmethod.InputMethodManager.this.mAccessibilityInputMethodSession.append(inputBindResult3.accessibilitySessions.keyAt(i7), createOrNull2);
                                    }
                                }
                            }
                            android.view.inputmethod.InputMethodManager.this.mCurId = inputBindResult3.id;
                        } else if (inputBindResult3.channel != null && inputBindResult3.channel != android.view.inputmethod.InputMethodManager.this.mCurChannel) {
                            inputBindResult3.channel.dispose();
                        }
                        switch (inputBindResult3.result) {
                            case 12:
                                android.view.inputmethod.InputMethodManager.this.mRestartOnNextWindowFocus = true;
                                android.view.inputmethod.InputMethodManager.this.mServedView = null;
                                break;
                        }
                        if (android.view.inputmethod.InputMethodManager.this.mCompletions != null && android.view.inputmethod.InputMethodManager.this.isImeSessionAvailableLocked()) {
                            android.view.inputmethod.InputMethodManager.this.mCurBindState.mImeSession.displayCompletions(android.view.inputmethod.InputMethodManager.this.mCompletions);
                        }
                        if (inputBindResult3 != null && inputBindResult3.method != null && android.view.inputmethod.InputMethodManager.this.mServedView != null && android.view.inputmethod.InputMethodManager.this.mReportInputConnectionOpenedRunner != null && android.view.inputmethod.InputMethodManager.this.mReportInputConnectionOpenedRunner.mSequenceNum == i6) {
                            android.view.inputmethod.InputMethodManager.this.mReportInputConnectionOpenedRunner.run();
                        }
                        android.view.inputmethod.InputMethodManager.this.mReportInputConnectionOpenedRunner = null;
                    }
                    return;
                default:
                    return;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(android.view.ViewRootImpl viewRootImpl) {
            synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                if (android.view.inputmethod.InputMethodManager.this.mCurRootView != viewRootImpl) {
                    return;
                }
                android.view.View view = viewRootImpl.getView();
                if (view == null) {
                    return;
                }
                android.view.View findFocus = view.findFocus();
                android.view.inputmethod.InputMethodManager.this.onViewFocusChangedInternal(findFocus, findFocus != null);
            }
        }
    }

    static void tearDownEditMode() {
        if (!isInEditMode()) {
            throw new java.lang.UnsupportedOperationException("This method must be called only from layoutlib");
        }
        synchronized (sLock) {
            sInstance = null;
        }
    }

    private static boolean isInEditMode() {
        return false;
    }

    static boolean isInEditModeInternal() {
        return isInEditMode();
    }

    private static android.view.inputmethod.InputMethodManager createInstance(int i, android.os.Looper looper) {
        return isInEditMode() ? createStubInstance(i, looper) : createRealInstance(i, looper);
    }

    private static android.view.inputmethod.InputMethodManager createRealInstance(int i, android.os.Looper looper) {
        com.android.internal.view.IInputMethodManager service = android.view.inputmethod.IInputMethodManagerGlobalInvoker.getService();
        if (service == null) {
            throw new java.lang.IllegalStateException("IInputMethodManager is not available");
        }
        android.view.inputmethod.InputMethodManager inputMethodManager = new android.view.inputmethod.InputMethodManager(service, i, looper);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.view.inputmethod.IInputMethodManagerGlobalInvoker.addClient(inputMethodManager.mClient, inputMethodManager.mFallbackInputConnection, i);
            return inputMethodManager;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static android.view.inputmethod.InputMethodManager createStubInstance(int i, android.os.Looper looper) {
        return new android.view.inputmethod.InputMethodManager((com.android.internal.view.IInputMethodManager) java.lang.reflect.Proxy.newProxyInstance(com.android.internal.view.IInputMethodManager.class.getClassLoader(), new java.lang.Class[]{com.android.internal.view.IInputMethodManager.class}, new java.lang.reflect.InvocationHandler() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda4
            @Override // java.lang.reflect.InvocationHandler
            public final java.lang.Object invoke(java.lang.Object obj, java.lang.reflect.Method method, java.lang.Object[] objArr) {
                return android.view.inputmethod.InputMethodManager.lambda$createStubInstance$0(obj, method, objArr);
            }
        }), i, looper);
    }

    static /* synthetic */ java.lang.Object lambda$createStubInstance$0(java.lang.Object obj, java.lang.reflect.Method method, java.lang.Object[] objArr) throws java.lang.Throwable {
        java.lang.Class<?> returnType = method.getReturnType();
        if (returnType == java.lang.Boolean.TYPE) {
            return false;
        }
        if (returnType == java.lang.Integer.TYPE) {
            return 0;
        }
        if (returnType == java.lang.Long.TYPE) {
            return 0L;
        }
        if (returnType == java.lang.Short.TYPE || returnType == java.lang.Character.TYPE || returnType == java.lang.Byte.TYPE) {
            return 0;
        }
        if (returnType == java.lang.Float.TYPE) {
            return java.lang.Float.valueOf(0.0f);
        }
        if (returnType == java.lang.Double.TYPE) {
            return java.lang.Double.valueOf(0.0d);
        }
        return null;
    }

    private InputMethodManager(com.android.internal.view.IInputMethodManager iInputMethodManager, int i, android.os.Looper looper) {
        this.mService = iInputMethodManager;
        this.mMainLooper = looper;
        this.mH = new android.view.inputmethod.InputMethodManager.H(looper);
        this.mDisplayId = i;
        this.mFallbackInputConnection = new android.view.inputmethod.RemoteInputConnectionImpl(looper, new android.view.inputmethod.BaseInputConnection(this, false), this, null);
    }

    public static android.view.inputmethod.InputMethodManager forContext(android.content.Context context) {
        int displayId = context.getDisplayId();
        android.os.Looper mainLooper = displayId == 0 ? android.os.Looper.getMainLooper() : context.getMainLooper();
        sPreventImeStartupUnlessTextEditor = context.getResources().getBoolean(17891335);
        return forContextInternal(displayId, mainLooper);
    }

    private static android.view.inputmethod.InputMethodManager forContextInternal(int i, android.os.Looper looper) {
        boolean z = i == 0;
        synchronized (sLock) {
            android.view.inputmethod.InputMethodManager inputMethodManager = sInstanceMap.get(i);
            if (inputMethodManager != null) {
                return inputMethodManager;
            }
            android.view.inputmethod.InputMethodManager createInstance = createInstance(i, looper);
            if (sInstance == null && z) {
                sInstance = createInstance;
            }
            sInstanceMap.put(i, createInstance);
            return createInstance;
        }
    }

    @java.lang.Deprecated
    public static android.view.inputmethod.InputMethodManager getInstance() {
        android.util.Log.w(TAG, "InputMethodManager.getInstance() is deprecated because it cannot be compatible with multi-display. Use context.getSystemService(InputMethodManager.class) instead.", new java.lang.Throwable());
        ensureDefaultInstanceForDefaultDisplayIfNecessary();
        return peekInstance();
    }

    @java.lang.Deprecated
    public static android.view.inputmethod.InputMethodManager peekInstance() {
        android.view.inputmethod.InputMethodManager inputMethodManager;
        android.util.Log.w(TAG, "InputMethodManager.peekInstance() is deprecated because it cannot be compatible with multi-display. Use context.getSystemService(InputMethodManager.class) instead.", new java.lang.Throwable());
        synchronized (sLock) {
            inputMethodManager = sInstance;
        }
        return inputMethodManager;
    }

    public java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodList() {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.getInputMethodList(android.os.UserHandle.myUserId(), 0);
    }

    public boolean isStylusHandwritingAvailable() {
        return isStylusHandwritingAvailableAsUser(android.os.UserHandle.of(android.os.UserHandle.myUserId()));
    }

    public boolean isStylusHandwritingAvailableAsUser(android.os.UserHandle userHandle) {
        boolean booleanValue;
        if (android.app.ActivityThread.currentApplication() == null) {
            return false;
        }
        synchronized (this.mH) {
            if (this.mStylusHandwritingAvailableCache == null) {
                this.mStylusHandwritingAvailableCache = new android.app.PropertyInvalidatedCache<java.lang.Integer, java.lang.Boolean>(4, CACHE_KEY_STYLUS_HANDWRITING_PROPERTY) { // from class: android.view.inputmethod.InputMethodManager.3
                    @Override // android.app.PropertyInvalidatedCache
                    public java.lang.Boolean recompute(java.lang.Integer num) {
                        return java.lang.Boolean.valueOf(android.view.inputmethod.IInputMethodManagerGlobalInvoker.isStylusHandwritingAvailableAsUser(num.intValue(), false));
                    }
                };
            }
            booleanValue = this.mStylusHandwritingAvailableCache.query(java.lang.Integer.valueOf(userHandle.getIdentifier())).booleanValue();
        }
        return booleanValue;
    }

    public boolean isConnectionlessStylusHandwritingAvailable() {
        boolean booleanValue;
        if (android.app.ActivityThread.currentApplication() == null) {
            return false;
        }
        synchronized (this.mH) {
            if (this.mConnectionlessStylusHandwritingAvailableCache == null) {
                this.mConnectionlessStylusHandwritingAvailableCache = new android.app.PropertyInvalidatedCache<java.lang.Integer, java.lang.Boolean>(4, CACHE_KEY_CONNECTIONLESS_STYLUS_HANDWRITING_PROPERTY) { // from class: android.view.inputmethod.InputMethodManager.4
                    @Override // android.app.PropertyInvalidatedCache
                    public java.lang.Boolean recompute(java.lang.Integer num) {
                        return java.lang.Boolean.valueOf(android.view.inputmethod.IInputMethodManagerGlobalInvoker.isStylusHandwritingAvailableAsUser(num.intValue(), true));
                    }
                };
            }
            booleanValue = this.mConnectionlessStylusHandwritingAvailableCache.query(java.lang.Integer.valueOf(android.os.UserHandle.myUserId())).booleanValue();
        }
        return booleanValue;
    }

    public java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodListAsUser(int i) {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.getInputMethodList(i, 0);
    }

    public java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodListAsUser(int i, int i2) {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.getInputMethodList(i, i2);
    }

    public android.view.inputmethod.InputMethodInfo getCurrentInputMethodInfo() {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.getCurrentInputMethodInfoAsUser(android.os.UserHandle.myUserId());
    }

    @android.annotation.SystemApi
    public android.view.inputmethod.InputMethodInfo getCurrentInputMethodInfoAsUser(android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(userHandle);
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.getCurrentInputMethodInfoAsUser(userHandle.getIdentifier());
    }

    public java.util.List<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodList() {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.getEnabledInputMethodList(android.os.UserHandle.myUserId());
    }

    public java.util.List<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodListAsUser(android.os.UserHandle userHandle) {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.getEnabledInputMethodList(userHandle.getIdentifier());
    }

    public java.util.List<android.view.inputmethod.InputMethodSubtype> getEnabledInputMethodSubtypeList(android.view.inputmethod.InputMethodInfo inputMethodInfo, boolean z) {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.getEnabledInputMethodSubtypeList(inputMethodInfo == null ? null : inputMethodInfo.getId(), z, android.os.UserHandle.myUserId());
    }

    public java.util.List<android.view.inputmethod.InputMethodSubtype> getEnabledInputMethodSubtypeListAsUser(java.lang.String str, boolean z, android.os.UserHandle userHandle) {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.getEnabledInputMethodSubtypeList((java.lang.String) java.util.Objects.requireNonNull(str), z, userHandle.getIdentifier());
    }

    @java.lang.Deprecated
    public void showStatusIcon(android.os.IBinder iBinder, java.lang.String str, int i) {
        com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry.get(iBinder).updateStatusIconAsync(str, i);
    }

    @java.lang.Deprecated
    public void hideStatusIcon(android.os.IBinder iBinder) {
        com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry.get(iBinder).updateStatusIconAsync(null, 0);
    }

    @java.lang.Deprecated
    public void registerSuggestionSpansForNotification(android.text.style.SuggestionSpan[] suggestionSpanArr) {
        android.util.Log.w(TAG, "registerSuggestionSpansForNotification() is deprecated.  Does nothing.");
    }

    @java.lang.Deprecated
    public void notifySuggestionPicked(android.text.style.SuggestionSpan suggestionSpan, java.lang.String str, int i) {
        android.util.Log.w(TAG, "notifySuggestionPicked() is deprecated.  Does nothing.");
    }

    public boolean isFullscreenMode() {
        boolean z;
        synchronized (this.mH) {
            z = this.mFullscreenMode;
        }
        return z;
    }

    public boolean isActive(android.view.View view) {
        boolean z;
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            return fallbackInputMethodManagerIfNecessary.isActive(view);
        }
        checkFocus();
        synchronized (this.mH) {
            z = hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null;
        }
        return z;
    }

    public boolean isActive() {
        boolean z;
        checkFocus();
        synchronized (this.mH) {
            z = (getServedViewLocked() == null || this.mCurrentEditorInfo == null) ? false : true;
        }
        return z;
    }

    public boolean isCurrentRootView(android.view.View view) {
        boolean z;
        synchronized (this.mH) {
            z = this.mCurRootView == view.getViewRootImpl();
        }
        return z;
    }

    public boolean isAcceptingText() {
        boolean z;
        checkFocus();
        synchronized (this.mH) {
            z = this.mServedInputConnection != null;
        }
        return z;
    }

    public boolean isInputMethodSuppressingSpellChecker() {
        boolean z;
        synchronized (this.mH) {
            z = this.mCurBindState != null && this.mCurBindState.mIsInputMethodSuppressingSpellChecker;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearBindingLocked() {
        clearConnectionLocked();
        updateInputChannelLocked(null);
        this.mCurId = null;
        this.mCurMethod = null;
        this.mCurBindState = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAccessibilityBindingLocked(int i) {
        this.mAccessibilityInputMethodSession.remove(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAllAccessibilityBindingLocked() {
        this.mAccessibilityInputMethodSession.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInputChannelLocked(android.view.InputChannel inputChannel) {
        if (areSameInputChannel(this.mCurChannel, inputChannel)) {
            return;
        }
        if (this.mCurSender != null) {
            flushPendingEventsLocked();
            this.mCurSender.dispose();
            this.mCurSender = null;
        }
        if (this.mCurChannel != null) {
            this.mCurChannel.dispose();
        }
        this.mCurChannel = inputChannel;
    }

    private static boolean areSameInputChannel(android.view.InputChannel inputChannel, android.view.InputChannel inputChannel2) {
        if (inputChannel == inputChannel2) {
            return true;
        }
        if (inputChannel != null && inputChannel2 != null && inputChannel.getToken() == inputChannel2.getToken()) {
            return true;
        }
        return false;
    }

    private void clearConnectionLocked() {
        this.mCurrentEditorInfo = null;
        this.mPreviousViewFocusParameters = null;
        if (this.mServedInputConnection != null) {
            this.mServedInputConnection.deactivate();
            this.mServedInputConnection = null;
            this.mServedInputConnectionHandler = null;
        }
    }

    void finishInputLocked() {
        android.view.View view;
        this.mNextServedView = null;
        if (this.mServedView == null) {
            view = null;
        } else {
            view = this.mServedView;
            this.mServedView = null;
            if (android.view.inputmethod.Flags.initiationWithoutInputConnection() && view.getViewRootImpl() != null) {
                view.getViewRootImpl().getHandwritingInitiator().clearFocusedView(view);
            }
        }
        if (view != null) {
            this.mCompletions = null;
            this.mServedConnecting = false;
            clearConnectionLocked();
        }
        this.mReportInputConnectionOpenedRunner = null;
        this.mImeDispatcher.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean clearCurRootViewIfNeeded() {
        if (!this.mActive && !this.mCurRootViewWindowFocused) {
            finishInputLocked();
            this.mDelegate.setCurrentRootViewLocked(null);
            return true;
        }
        return false;
    }

    public void displayCompletions(android.view.View view, android.view.inputmethod.CompletionInfo[] completionInfoArr) {
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.displayCompletions(view, completionInfoArr);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view)) {
                this.mCompletions = completionInfoArr;
                if (isImeSessionAvailableLocked()) {
                    this.mCurBindState.mImeSession.displayCompletions(this.mCompletions);
                }
            }
        }
    }

    public void updateExtractedText(android.view.View view, int i, android.view.inputmethod.ExtractedText extractedText) {
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.updateExtractedText(view, i, extractedText);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view)) {
                if (isImeSessionAvailableLocked()) {
                    this.mCurBindState.mImeSession.updateExtractedText(i, extractedText);
                }
            }
        }
    }

    public boolean showSoftInput(android.view.View view, int i) {
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            return fallbackInputMethodManagerIfNecessary.showSoftInput(view, i);
        }
        return showSoftInput(view, i, null);
    }

    public boolean showSoftInput(android.view.View view, int i, android.os.ResultReceiver resultReceiver) {
        return showSoftInput(view, i, resultReceiver, 1);
    }

    private boolean showSoftInput(android.view.View view, int i, android.os.ResultReceiver resultReceiver, int i2) {
        return showSoftInput(view, android.view.inputmethod.ImeTracker.forLogging().onStart(1, 5, i2, android.view.inputmethod.ImeTracker.isFromUser(view)), i, resultReceiver, i2);
    }

    private boolean showSoftInput(android.view.View view, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver, int i2) {
        android.view.inputmethod.ImeTracker.forLatency().onRequestShow(token, 5, i2, new android.view.InsetsController$$ExternalSyntheticLambda2());
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("InputMethodManager#showSoftInput", this, null);
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            return fallbackInputMethodManagerIfNecessary.showSoftInput(view, token, i, resultReceiver, i2);
        }
        checkFocus();
        synchronized (this.mH) {
            if (!hasServedByInputMethodLocked(view)) {
                android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 1);
                android.view.inputmethod.ImeTracker.forLatency().onShowFailed(token, 1, new android.view.InsetsController$$ExternalSyntheticLambda2());
                android.util.Log.w(TAG, "Ignoring showSoftInput() as view=" + view + " is not served.");
                return false;
            }
            android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 1);
            this.mH.executeOrSendMessage(android.os.Message.obtain(this.mH, 31));
            android.util.Log.d(TAG, "showSoftInput() view=" + view + " flags=" + i + " reason=" + com.android.internal.inputmethod.InputMethodDebug.softInputDisplayReasonToString(i2));
            return android.view.inputmethod.IInputMethodManagerGlobalInvoker.showSoftInput(this.mClient, view.getWindowToken(), token, i, this.mCurRootView.getLastClickToolType(), resultReceiver, i2);
        }
    }

    @java.lang.Deprecated
    public void showSoftInputUnchecked(int i, android.os.ResultReceiver resultReceiver) {
        synchronized (this.mH) {
            android.view.inputmethod.ImeTracker.Token onStart = android.view.inputmethod.ImeTracker.forLogging().onStart(1, 5, 1, false);
            android.util.Log.w(TAG, "showSoftInputUnchecked() is a hidden method, which will be removed soon. If you are using androidx.appcompat.widget.SearchView, please update to version 26.0 or newer version.");
            android.view.View view = this.mCurRootView != null ? this.mCurRootView.getView() : null;
            if (view == null) {
                android.view.inputmethod.ImeTracker.forLogging().onFailed(onStart, 1);
                android.util.Log.w(TAG, "No current root view, ignoring showSoftInputUnchecked()");
            } else {
                android.view.inputmethod.ImeTracker.forLogging().onProgress(onStart, 1);
                this.mH.executeOrSendMessage(android.os.Message.obtain(this.mH, 31));
                android.view.inputmethod.IInputMethodManagerGlobalInvoker.showSoftInput(this.mClient, view.getWindowToken(), onStart, i, this.mCurRootView.getLastClickToolType(), resultReceiver, 1);
            }
        }
    }

    public boolean hideSoftInputFromWindow(android.os.IBinder iBinder, int i) {
        return hideSoftInputFromWindow(iBinder, i, null);
    }

    public boolean hideSoftInputFromWindow(android.os.IBinder iBinder, int i, android.os.ResultReceiver resultReceiver) {
        return hideSoftInputFromWindow(iBinder, i, resultReceiver, 4);
    }

    private boolean hideSoftInputFromWindow(android.os.IBinder iBinder, int i, android.os.ResultReceiver resultReceiver, int i2) {
        android.view.View servedViewLocked;
        synchronized (this.mH) {
            servedViewLocked = getServedViewLocked();
        }
        android.view.inputmethod.ImeTracker.Token onStart = android.view.inputmethod.ImeTracker.forLogging().onStart(2, 5, i2, android.view.inputmethod.ImeTracker.isFromUser(servedViewLocked));
        android.view.inputmethod.ImeTracker.forLatency().onRequestHide(onStart, 5, i2, new android.view.InsetsController$$ExternalSyntheticLambda2());
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("InputMethodManager#hideSoftInputFromWindow", this, null);
        checkFocus();
        synchronized (this.mH) {
            android.view.View servedViewLocked2 = getServedViewLocked();
            if (servedViewLocked2 != null && servedViewLocked2.getWindowToken() == iBinder) {
                android.view.inputmethod.ImeTracker.forLogging().onProgress(onStart, 1);
                return android.view.inputmethod.IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, iBinder, onStart, i, resultReceiver, i2);
            }
            android.view.inputmethod.ImeTracker.forLogging().onFailed(onStart, 1);
            android.view.inputmethod.ImeTracker.forLatency().onHideFailed(onStart, 1, new android.view.InsetsController$$ExternalSyntheticLambda2());
            return false;
        }
    }

    public boolean hideSoftInputFromView(android.view.View view, int i) {
        boolean z = view.hasWindowFocus() && view.isFocused();
        synchronized (this.mH) {
            if (!z) {
                if (!hasServedByInputMethodLocked(view)) {
                    return false;
                }
            }
            android.view.inputmethod.ImeTracker.Token onStart = android.view.inputmethod.ImeTracker.forLogging().onStart(2, 5, 39, android.view.inputmethod.ImeTracker.isFromUser(view));
            android.view.inputmethod.ImeTracker.forLatency().onRequestHide(onStart, 5, 39, new android.view.InsetsController$$ExternalSyntheticLambda2());
            com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("InputMethodManager#hideSoftInputFromView", this, null);
            synchronized (this.mH) {
                if (!hasServedByInputMethodLocked(view)) {
                    android.view.inputmethod.ImeTracker.forLogging().onFailed(onStart, 1);
                    android.view.inputmethod.ImeTracker.forLatency().onShowFailed(onStart, 1, new android.view.InsetsController$$ExternalSyntheticLambda2());
                    android.util.Log.w(TAG, "Ignoring hideSoftInputFromView() as view=" + view + " is not served.");
                    return false;
                }
                android.view.inputmethod.ImeTracker.forLogging().onProgress(onStart, 1);
                return android.view.inputmethod.IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, view.getWindowToken(), onStart, i, null, 39);
            }
        }
    }

    public void hideSoftInputFromServerForTest() {
        android.view.inputmethod.IInputMethodManagerGlobalInvoker.hideSoftInputFromServerForTest();
    }

    public void startStylusHandwriting(android.view.View view) {
        startStylusHandwritingInternal(view, null, 0);
    }

    private void startStylusHandwritingInternalAsync(android.view.View view, java.lang.String str, int i, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
        java.util.Objects.requireNonNull(view);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        startStylusHandwritingInternal(view, str, i, executor, consumer);
    }

    private void sendFailureCallback(java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Boolean> consumer) {
        if (executor == null || consumer == null) {
            return;
        }
        executor.execute(new java.lang.Runnable() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(false);
            }
        });
    }

    private boolean startStylusHandwritingInternal(android.view.View view, java.lang.String str, int i) {
        return startStylusHandwritingInternal(view, str, i, null, null);
    }

    private boolean startStylusHandwritingInternal(android.view.View view, java.lang.String str, int i, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
        java.util.Objects.requireNonNull(view);
        boolean z = consumer != null;
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.startStylusHandwritingInternal(view, str, i, executor, consumer);
        }
        boolean z2 = !android.text.TextUtils.isEmpty(str);
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view)) {
                if (view.getViewRootImpl() != this.mCurRootView) {
                    android.util.Log.w(TAG, "Ignoring startStylusHandwriting: View's window does not have focus.");
                    sendFailureCallback(executor, consumer);
                    return false;
                }
                if (z2) {
                    if (z) {
                        if (!android.view.inputmethod.IInputMethodManagerGlobalInvoker.acceptStylusHandwritingDelegationAsync(this.mClient, android.os.UserHandle.myUserId(), view.getContext().getOpPackageName(), str, i, new android.view.inputmethod.InputMethodManager.AnonymousClass5(executor, consumer))) {
                            sendFailureCallback(executor, consumer);
                        }
                        return true;
                    }
                    return android.view.inputmethod.IInputMethodManagerGlobalInvoker.acceptStylusHandwritingDelegation(this.mClient, android.os.UserHandle.myUserId(), view.getContext().getOpPackageName(), str, i);
                }
                android.view.inputmethod.IInputMethodManagerGlobalInvoker.startStylusHandwriting(this.mClient);
                return false;
            }
            android.util.Log.w(TAG, "Ignoring startStylusHandwriting as view=" + view + " is not served.");
            sendFailureCallback(executor, consumer);
            return false;
        }
    }

    /* renamed from: android.view.inputmethod.InputMethodManager$5, reason: invalid class name */
    class AnonymousClass5 extends com.android.internal.inputmethod.IBooleanListener.Stub {
        final /* synthetic */ java.util.function.Consumer val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass5(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.inputmethod.IBooleanListener
        public void onResult(final boolean z) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.view.inputmethod.InputMethodManager$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(java.lang.Boolean.valueOf(z));
                }
            });
        }
    }

    public void startConnectionlessStylusHandwriting(android.view.View view, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, java.util.concurrent.Executor executor, android.view.inputmethod.ConnectionlessHandwritingCallback connectionlessHandwritingCallback) {
        startConnectionlessStylusHandwritingInternal(view, cursorAnchorInfo, null, null, executor, connectionlessHandwritingCallback);
    }

    public void startConnectionlessStylusHandwritingForDelegation(android.view.View view, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, java.util.concurrent.Executor executor, android.view.inputmethod.ConnectionlessHandwritingCallback connectionlessHandwritingCallback) {
        java.lang.String opPackageName = view.getContext().getOpPackageName();
        startConnectionlessStylusHandwritingInternal(view, cursorAnchorInfo, opPackageName, opPackageName, executor, connectionlessHandwritingCallback);
    }

    public void startConnectionlessStylusHandwritingForDelegation(android.view.View view, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, java.lang.String str, java.util.concurrent.Executor executor, android.view.inputmethod.ConnectionlessHandwritingCallback connectionlessHandwritingCallback) {
        java.util.Objects.requireNonNull(str);
        startConnectionlessStylusHandwritingInternal(view, cursorAnchorInfo, view.getContext().getOpPackageName(), str, executor, connectionlessHandwritingCallback);
    }

    private void startConnectionlessStylusHandwritingInternal(android.view.View view, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, java.lang.String str, java.lang.String str2, java.util.concurrent.Executor executor, android.view.inputmethod.ConnectionlessHandwritingCallback connectionlessHandwritingCallback) {
        java.util.Objects.requireNonNull(view);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(connectionlessHandwritingCallback);
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.startConnectionlessStylusHandwritingInternal(view, cursorAnchorInfo, str, str2, executor, connectionlessHandwritingCallback);
        }
        checkFocus();
        synchronized (this.mH) {
            if (view.getViewRootImpl() != this.mCurRootView) {
                android.util.Log.w(TAG, "Ignoring startConnectionlessStylusHandwriting: View's window does not have focus.");
            } else {
                android.view.inputmethod.IInputMethodManagerGlobalInvoker.startConnectionlessStylusHandwriting(this.mClient, android.os.UserHandle.myUserId(), cursorAnchorInfo, str2, str, new android.view.inputmethod.InputMethodManager.ConnectionlessHandwritingCallbackProxy(executor, connectionlessHandwritingCallback));
            }
        }
    }

    public void prepareStylusHandwritingDelegation(android.view.View view) {
        prepareStylusHandwritingDelegation(view, view.getContext().getOpPackageName());
    }

    public void prepareStylusHandwritingDelegation(android.view.View view, java.lang.String str) {
        java.util.Objects.requireNonNull(view);
        java.util.Objects.requireNonNull(str);
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.prepareStylusHandwritingDelegation(view, str);
        }
        android.view.inputmethod.IInputMethodManagerGlobalInvoker.prepareStylusHandwritingDelegation(this.mClient, android.os.UserHandle.myUserId(), str, view.getContext().getOpPackageName());
    }

    public boolean acceptStylusHandwritingDelegation(android.view.View view) {
        return startStylusHandwritingInternal(view, view.getContext().getOpPackageName(), view.getHandwritingDelegateFlags());
    }

    public boolean acceptStylusHandwritingDelegation(android.view.View view, java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        return startStylusHandwritingInternal(view, str, view.getHandwritingDelegateFlags());
    }

    public void acceptStylusHandwritingDelegation(android.view.View view, java.lang.String str, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
        int i;
        java.util.Objects.requireNonNull(str);
        if (!android.view.inputmethod.Flags.homeScreenHandwritingDelegator()) {
            i = 0;
        } else {
            i = view.getHandwritingDelegateFlags();
        }
        startStylusHandwritingInternalAsync(view, str, i, executor, consumer);
    }

    public void acceptStylusHandwritingDelegation(android.view.View view, java.lang.String str, int i, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
        java.util.Objects.requireNonNull(str);
        startStylusHandwritingInternal(view, str, i, executor, consumer);
    }

    @java.lang.Deprecated
    public void toggleSoftInputFromWindow(android.os.IBinder iBinder, int i, int i2) {
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("InputMethodManager#toggleSoftInputFromWindow", this, null);
        synchronized (this.mH) {
            android.view.View servedViewLocked = getServedViewLocked();
            if (servedViewLocked != null && servedViewLocked.getWindowToken() == iBinder) {
                toggleSoftInput(i, i2);
            }
        }
    }

    @java.lang.Deprecated
    public void toggleSoftInput(int i, int i2) {
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("InputMethodManager#toggleSoftInput", this, null);
        synchronized (this.mH) {
            android.view.View servedViewLocked = getServedViewLocked();
            if (servedViewLocked != null) {
                android.view.WindowInsets rootWindowInsets = servedViewLocked.getRootWindowInsets();
                if (rootWindowInsets != null && rootWindowInsets.isVisible(android.view.WindowInsets.Type.ime())) {
                    hideSoftInputFromWindow(servedViewLocked.getWindowToken(), i2, null, 25);
                } else {
                    showSoftInput(servedViewLocked, i, null, 24);
                }
            }
        }
    }

    public void restartInput(android.view.View view) {
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.restartInput(view);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view)) {
                this.mServedConnecting = true;
                startInputInner(4, null, 0, 0, 0);
            }
        }
    }

    public boolean doInvalidateInput(android.view.inputmethod.RemoteInputConnectionImpl remoteInputConnectionImpl, android.view.inputmethod.TextSnapshot textSnapshot, final int i) {
        synchronized (this.mH) {
            if (this.mServedInputConnection == remoteInputConnectionImpl && this.mCurrentEditorInfo != null) {
                if (!isImeSessionAvailableLocked()) {
                    return false;
                }
                final android.view.inputmethod.EditorInfo createCopyInternal = this.mCurrentEditorInfo.createCopyInternal();
                int selectionStart = textSnapshot.getSelectionStart();
                this.mCursorSelStart = selectionStart;
                createCopyInternal.initialSelStart = selectionStart;
                int selectionEnd = textSnapshot.getSelectionEnd();
                this.mCursorSelEnd = selectionEnd;
                createCopyInternal.initialSelEnd = selectionEnd;
                this.mCursorCandStart = textSnapshot.getCompositionStart();
                this.mCursorCandEnd = textSnapshot.getCompositionEnd();
                createCopyInternal.initialCapsMode = textSnapshot.getCursorCapsMode();
                createCopyInternal.setInitialSurroundingTextInternal(textSnapshot.getSurroundingText());
                this.mCurBindState.mImeSession.invalidateInput(createCopyInternal, this.mServedInputConnection, i);
                final com.android.internal.inputmethod.IRemoteAccessibilityInputConnection asIRemoteAccessibilityInputConnection = this.mServedInputConnection.asIRemoteAccessibilityInputConnection();
                forAccessibilitySessionsLocked(new java.util.function.Consumer() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((android.view.inputmethod.IAccessibilityInputMethodSessionInvoker) obj).invalidateInput(android.view.inputmethod.EditorInfo.this, asIRemoteAccessibilityInputConnection, i);
                    }
                });
                return true;
            }
            return true;
        }
    }

    public void invalidateInput(android.view.View view) {
        java.util.Objects.requireNonNull(view);
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.invalidateInput(view);
            return;
        }
        synchronized (this.mH) {
            if (this.mServedInputConnection != null && getServedViewLocked() == view) {
                this.mServedInputConnection.scheduleInvalidateInput();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:63:0x012c A[Catch: all -> 0x02ce, DONT_GENERATE, TryCatch #3 {all -> 0x02ce, blocks: (B:27:0x0073, B:29:0x0079, B:39:0x0082, B:41:0x0086, B:42:0x008d, B:44:0x00a5, B:47:0x00b1, B:49:0x00cd, B:50:0x00d5, B:52:0x00db, B:53:0x00e4, B:54:0x00ef, B:58:0x00fa, B:61:0x0118, B:63:0x012c, B:65:0x012f, B:67:0x0134, B:68:0x013f, B:70:0x014f, B:73:0x015c, B:132:0x0156, B:138:0x013b, B:141:0x00e0, B:145:0x00e9), top: B:26:0x0073 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x012f A[Catch: all -> 0x02ce, TryCatch #3 {all -> 0x02ce, blocks: (B:27:0x0073, B:29:0x0079, B:39:0x0082, B:41:0x0086, B:42:0x008d, B:44:0x00a5, B:47:0x00b1, B:49:0x00cd, B:50:0x00d5, B:52:0x00db, B:53:0x00e4, B:54:0x00ef, B:58:0x00fa, B:61:0x0118, B:63:0x012c, B:65:0x012f, B:67:0x0134, B:68:0x013f, B:70:0x014f, B:73:0x015c, B:132:0x0156, B:138:0x013b, B:141:0x00e0, B:145:0x00e9), top: B:26:0x0073 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean startInputInner(final int i, android.os.IBinder iBinder, int i2, int i3, int i4) {
        int i5;
        android.os.IBinder iBinder2;
        int i6;
        int i7;
        android.view.inputmethod.InputMethodManager.H h;
        boolean z;
        int i8;
        android.os.Handler handler;
        android.view.inputmethod.RemoteInputConnectionImpl remoteInputConnectionImpl;
        android.view.inputmethod.RemoteInputConnectionImpl remoteInputConnectionImpl2;
        int i9;
        boolean z2;
        android.view.inputmethod.InputMethodManager.H h2;
        android.view.inputmethod.EditorInfo editorInfo;
        android.view.inputmethod.InputConnection inputConnection;
        com.android.internal.inputmethod.InputBindResult startInputOrWindowGainedFocus;
        synchronized (this.mH) {
            final android.view.View servedViewLocked = getServedViewLocked();
            if (servedViewLocked == null) {
                return false;
            }
            android.os.Handler handler2 = servedViewLocked.getHandler();
            if (handler2 == null) {
                closeCurrentInput();
                return false;
            }
            if (handler2.getLooper() != android.os.Looper.myLooper()) {
                handler2.post(new java.lang.Runnable() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.inputmethod.InputMethodManager.this.lambda$startInputInner$3(i);
                    }
                });
                return false;
            }
            if (iBinder != null) {
                i5 = i2;
                iBinder2 = iBinder;
                i6 = i3;
                i7 = i4;
            } else {
                android.os.IBinder windowToken = servedViewLocked.getWindowToken();
                if (windowToken != null) {
                    i5 = getStartInputFlags(servedViewLocked, i2);
                    iBinder2 = windowToken;
                    i6 = servedViewLocked.getViewRootImpl().mWindowAttributes.softInputMode;
                    i7 = servedViewLocked.getViewRootImpl().mWindowAttributes.flags;
                } else {
                    android.util.Log.e(TAG, "ABORT input: ServedView must be attached to a Window");
                    return false;
                }
            }
            android.util.Pair<android.view.inputmethod.InputConnection, android.view.inputmethod.EditorInfo> createInputConnection = createInputConnection(servedViewLocked);
            android.view.inputmethod.InputConnection inputConnection2 = createInputConnection.first;
            android.view.inputmethod.EditorInfo editorInfo2 = createInputConnection.second;
            android.view.inputmethod.InputMethodManager.H h3 = this.mH;
            synchronized (h3) {
                try {
                    try {
                        if (getServedViewLocked() == servedViewLocked) {
                            if (!this.mServedConnecting) {
                                h = h3;
                                z = false;
                            } else {
                                if (this.mCurrentEditorInfo != null) {
                                    i8 = i5;
                                } else {
                                    i8 = i5 | 4;
                                }
                                editorInfo2.setInitialToolType(this.mCurRootView.getLastClickToolType());
                                this.mCurrentEditorInfo = editorInfo2.createCopyInternal();
                                android.view.inputmethod.RemoteInputConnectionImpl remoteInputConnectionImpl3 = this.mServedInputConnection;
                                this.mServedConnecting = false;
                                if (this.mServedInputConnection != null) {
                                    this.mServedInputConnection.deactivate();
                                    this.mServedInputConnection = null;
                                    this.mServedInputConnectionHandler = null;
                                }
                                if (inputConnection2 != null) {
                                    this.mCursorSelStart = editorInfo2.initialSelStart;
                                    this.mCursorSelEnd = editorInfo2.initialSelEnd;
                                    this.mInitialSelStart = this.mCursorSelStart;
                                    this.mInitialSelEnd = this.mCursorSelEnd;
                                    this.mCursorCandStart = -1;
                                    this.mCursorCandEnd = -1;
                                    this.mCursorRect.setEmpty();
                                    this.mCursorAnchorInfo = null;
                                    try {
                                        handler = inputConnection2.getHandler();
                                    } catch (java.lang.AbstractMethodError e) {
                                        handler = null;
                                    }
                                    this.mServedInputConnectionHandler = handler;
                                    remoteInputConnectionImpl = new android.view.inputmethod.RemoteInputConnectionImpl(handler != null ? handler.getLooper() : handler2.getLooper(), inputConnection2, this, servedViewLocked);
                                } else {
                                    this.mServedInputConnectionHandler = null;
                                    handler = null;
                                    remoteInputConnectionImpl = null;
                                }
                                this.mServedInputConnection = remoteInputConnectionImpl;
                                if (OPTIMIZE_NONEDITABLE_VIEWS && remoteInputConnectionImpl3 == null && inputConnection2 == null) {
                                    remoteInputConnectionImpl2 = remoteInputConnectionImpl;
                                    i9 = -1;
                                    if (isSwitchingBetweenEquivalentNonEditableViews(this.mPreviousViewFocusParameters, i8, i, i6, i7)) {
                                        z2 = true;
                                        this.mPreviousViewFocusParameters = new android.view.inputmethod.ViewFocusParameterInfo(this.mCurrentEditorInfo, i8, i, i6, i7);
                                        if (!z2) {
                                            return false;
                                        }
                                        int identifier = editorInfo2.targetInputMethodUser != null ? editorInfo2.targetInputMethodUser.getIdentifier() : android.os.UserHandle.myUserId();
                                        android.os.Trace.traceBegin(32L, "IMM.startInputOrWindowGainedFocus");
                                        if (android.view.inputmethod.Flags.useZeroJankProxy()) {
                                            h2 = h3;
                                            inputConnection = inputConnection2;
                                            editorInfo = editorInfo2;
                                            i9 = android.view.inputmethod.IInputMethodManagerGlobalInvoker.startInputOrWindowGainedFocusAsync(i, this.mClient, iBinder2, i8, i6, i7, editorInfo, remoteInputConnectionImpl2, remoteInputConnectionImpl2 == null ? null : remoteInputConnectionImpl2.asIRemoteAccessibilityInputConnection(), servedViewLocked.getContext().getApplicationInfo().targetSdkVersion, identifier, this.mImeDispatcher);
                                            startInputOrWindowGainedFocus = null;
                                        } else {
                                            h2 = h3;
                                            editorInfo = editorInfo2;
                                            inputConnection = inputConnection2;
                                            startInputOrWindowGainedFocus = android.view.inputmethod.IInputMethodManagerGlobalInvoker.startInputOrWindowGainedFocus(i, this.mClient, iBinder2, i8, i6, i7, editorInfo, remoteInputConnectionImpl2, remoteInputConnectionImpl2 == null ? null : remoteInputConnectionImpl2.asIRemoteAccessibilityInputConnection(), servedViewLocked.getContext().getApplicationInfo().targetSdkVersion, identifier, this.mImeDispatcher);
                                        }
                                        android.os.Trace.traceEnd(32L);
                                        if (android.view.inputmethod.Flags.useZeroJankProxy()) {
                                            if (inputConnection != null) {
                                                final android.view.inputmethod.InputConnection inputConnection3 = inputConnection;
                                                final android.view.inputmethod.EditorInfo editorInfo3 = editorInfo;
                                                final android.os.Handler handler3 = handler;
                                                final int i10 = i9;
                                                this.mReportInputConnectionOpenedRunner = new android.view.inputmethod.InputMethodManager.ReportInputConnectionOpenedRunner(i9) { // from class: android.view.inputmethod.InputMethodManager.6
                                                    @Override // java.lang.Runnable
                                                    public void run() {
                                                        android.view.inputmethod.InputMethodManager.this.reportInputConnectionOpened(inputConnection3, editorInfo3, handler3, servedViewLocked);
                                                    }
                                                };
                                            } else {
                                                this.mReportInputConnectionOpenedRunner = null;
                                            }
                                            return true;
                                        }
                                        android.view.inputmethod.InputConnection inputConnection4 = inputConnection;
                                        if (startInputOrWindowGainedFocus == null) {
                                            android.util.Log.wtf(TAG, "startInputOrWindowGainedFocus must not return null. startInputReason=" + com.android.internal.inputmethod.InputMethodDebug.startInputReasonToString(i) + " editorInfo=" + editorInfo + " startInputFlags=" + com.android.internal.inputmethod.InputMethodDebug.startInputFlagsToString(i8));
                                            return false;
                                        }
                                        android.view.inputmethod.EditorInfo editorInfo4 = editorInfo;
                                        if (startInputOrWindowGainedFocus.id != null) {
                                            updateInputChannelLocked(startInputOrWindowGainedFocus.channel);
                                            this.mCurMethod = startInputOrWindowGainedFocus.method;
                                            this.mCurBindState = new android.view.inputmethod.InputMethodManager.BindState(startInputOrWindowGainedFocus);
                                            this.mAccessibilityInputMethodSession.clear();
                                            if (startInputOrWindowGainedFocus.accessibilitySessions != null) {
                                                for (int i11 = 0; i11 < startInputOrWindowGainedFocus.accessibilitySessions.size(); i11++) {
                                                    android.view.inputmethod.IAccessibilityInputMethodSessionInvoker createOrNull = android.view.inputmethod.IAccessibilityInputMethodSessionInvoker.createOrNull(startInputOrWindowGainedFocus.accessibilitySessions.valueAt(i11));
                                                    if (createOrNull != null) {
                                                        this.mAccessibilityInputMethodSession.append(startInputOrWindowGainedFocus.accessibilitySessions.keyAt(i11), createOrNull);
                                                    }
                                                }
                                            }
                                            this.mCurId = startInputOrWindowGainedFocus.id;
                                        } else if (startInputOrWindowGainedFocus.channel != null && startInputOrWindowGainedFocus.channel != this.mCurChannel) {
                                            startInputOrWindowGainedFocus.channel.dispose();
                                        }
                                        switch (startInputOrWindowGainedFocus.result) {
                                            case 12:
                                                this.mRestartOnNextWindowFocus = true;
                                                if (android.view.inputmethod.Flags.initiationWithoutInputConnection()) {
                                                    this.mServedView.getViewRootImpl().getHandwritingInitiator().clearFocusedView(this.mServedView);
                                                }
                                                this.mServedView = null;
                                                break;
                                        }
                                        if (this.mCompletions != null && isImeSessionAvailableLocked()) {
                                            this.mCurBindState.mImeSession.displayCompletions(this.mCompletions);
                                        }
                                        boolean z3 = this.mServedView != null;
                                        if (inputConnection4 != null && startInputOrWindowGainedFocus != null && startInputOrWindowGainedFocus.method != null && z3) {
                                            reportInputConnectionOpened(inputConnection4, editorInfo4, handler, servedViewLocked);
                                        }
                                        return true;
                                    }
                                } else {
                                    remoteInputConnectionImpl2 = remoteInputConnectionImpl;
                                    i9 = -1;
                                }
                                z2 = false;
                                this.mPreviousViewFocusParameters = new android.view.inputmethod.ViewFocusParameterInfo(this.mCurrentEditorInfo, i8, i, i6, i7);
                                if (!z2) {
                                }
                            }
                        } else {
                            h = h3;
                            z = false;
                        }
                        if (this.mServedInputConnection != null && i == 6) {
                            reportInputConnectionOpened(this.mServedInputConnection.getInputConnection(), this.mCurrentEditorInfo, this.mServedInputConnectionHandler, servedViewLocked);
                        }
                        return z;
                    } catch (java.lang.Throwable th) {
                        th = th;
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startInputInner$3(int i) {
        startInputOnWindowFocusGainInternal(i, null, 0, 0, 0);
    }

    private boolean isSwitchingBetweenEquivalentNonEditableViews(android.view.inputmethod.ViewFocusParameterInfo viewFocusParameterInfo, int i, int i2, int i3, int i4) {
        return (i & 8) == 0 && (i & 2) == 0 && viewFocusParameterInfo != null && viewFocusParameterInfo.sameAs(this.mCurrentEditorInfo, i, i2, i3, i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportInputConnectionOpened(android.view.inputmethod.InputConnection inputConnection, android.view.inputmethod.EditorInfo editorInfo, android.os.Handler handler, android.view.View view) {
        view.onInputConnectionOpenedInternal(inputConnection, editorInfo, handler);
        android.view.ViewRootImpl viewRootImpl = view.getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.getHandwritingInitiator().onInputConnectionCreated(view);
        }
    }

    public void addVirtualStylusIdForTestSession() {
        synchronized (this.mH) {
            android.view.inputmethod.IInputMethodManagerGlobalInvoker.addVirtualStylusIdForTestSession(this.mClient);
        }
    }

    public void setStylusWindowIdleTimeoutForTest(long j) {
        synchronized (this.mH) {
            android.view.inputmethod.IInputMethodManagerGlobalInvoker.setStylusWindowIdleTimeoutForTest(this.mClient, j);
        }
    }

    @java.lang.Deprecated
    public void windowDismissed(android.os.IBinder iBinder) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getStartInputFlags(android.view.View view, int i) {
        int i2 = i | 1;
        if (view.onCheckIsTextEditor()) {
            return i2 | 2;
        }
        return i2;
    }

    public void checkFocus() {
        synchronized (this.mH) {
            if (this.mCurRootView == null) {
                return;
            }
            if (checkFocusInternalLocked(false, this.mCurRootView)) {
                startInputOnWindowFocusGainInternal(5, null, 0, 0, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkFocusInternalLocked(boolean z, android.view.ViewRootImpl viewRootImpl) {
        if (this.mCurRootView != viewRootImpl) {
            return false;
        }
        if (this.mServedView == this.mNextServedView && !z) {
            return false;
        }
        if (this.mNextServedView == null) {
            finishInputLocked();
            closeCurrentInput();
            return false;
        }
        this.mServedView = this.mNextServedView;
        if (android.view.inputmethod.Flags.initiationWithoutInputConnection() && this.mServedView.isHandwritingDelegate()) {
            this.mServedView.getViewRootImpl().getHandwritingInitiator().onDelegateViewFocused(this.mServedView);
        }
        if (this.mServedInputConnection != null) {
            this.mServedInputConnection.finishComposingTextFromImm();
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onViewFocusChangedInternal(android.view.View view, boolean z) {
        if (view == null || view.isTemporarilyDetached()) {
            return;
        }
        android.view.ViewRootImpl viewRootImpl = view.getViewRootImpl();
        synchronized (this.mH) {
            if (this.mCurRootView != viewRootImpl) {
                return;
            }
            if (view.hasImeFocus() && view.hasWindowFocus()) {
                if (z) {
                    this.mNextServedView = view;
                }
                viewRootImpl.dispatchCheckFocus();
            }
        }
    }

    void closeCurrentInput() {
        android.view.inputmethod.ImeTracker.Token onStart = android.view.inputmethod.ImeTracker.forLogging().onStart(2, 5, 38, false);
        android.view.inputmethod.ImeTracker.forLatency().onRequestHide(onStart, 5, 38, new android.view.InsetsController$$ExternalSyntheticLambda2());
        synchronized (this.mH) {
            android.view.View view = this.mCurRootView != null ? this.mCurRootView.getView() : null;
            if (view == null) {
                android.view.inputmethod.ImeTracker.forLogging().onFailed(onStart, 1);
                android.view.inputmethod.ImeTracker.forLatency().onHideFailed(onStart, 1, new android.view.InsetsController$$ExternalSyntheticLambda2());
                android.util.Log.w(TAG, "No current root view, ignoring closeCurrentInput()");
            } else {
                android.view.inputmethod.ImeTracker.forLogging().onProgress(onStart, 1);
                android.view.inputmethod.IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, view.getWindowToken(), onStart, 2, null, 38);
            }
        }
    }

    public void registerImeConsumer(android.view.ImeInsetsSourceConsumer imeInsetsSourceConsumer) {
        if (imeInsetsSourceConsumer == null) {
            throw new java.lang.IllegalStateException("ImeInsetsSourceConsumer cannot be null.");
        }
        synchronized (this.mH) {
            this.mImeInsetsConsumer = imeInsetsSourceConsumer;
        }
    }

    public void unregisterImeConsumer(android.view.ImeInsetsSourceConsumer imeInsetsSourceConsumer) {
        if (imeInsetsSourceConsumer == null) {
            throw new java.lang.IllegalStateException("ImeInsetsSourceConsumer cannot be null.");
        }
        synchronized (this.mH) {
            if (this.mImeInsetsConsumer == imeInsetsSourceConsumer) {
                this.mImeInsetsConsumer = null;
            }
        }
    }

    public boolean requestImeShow(android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token) {
        checkFocus();
        synchronized (this.mH) {
            android.view.View servedViewLocked = getServedViewLocked();
            if (servedViewLocked != null && servedViewLocked.getWindowToken() == iBinder) {
                android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 37);
                showSoftInput(servedViewLocked, token, 0, null, 26);
                return true;
            }
            android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 37);
            return false;
        }
    }

    public void notifyImeHidden(android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token) {
        android.view.inputmethod.ImeTracker.forLatency().onRequestHide(token, 5, 28, new android.view.InsetsController$$ExternalSyntheticLambda2());
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("InputMethodManager#notifyImeHidden", this, null);
        synchronized (this.mH) {
            if (isImeSessionAvailableLocked() && this.mCurRootView != null && this.mCurRootView.getWindowToken() == iBinder) {
                android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 1);
                android.view.inputmethod.IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, iBinder, token, 0, null, 28);
                return;
            }
            android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 1);
            android.view.inputmethod.ImeTracker.forLatency().onHideFailed(token, 1, new android.view.InsetsController$$ExternalSyntheticLambda2());
        }
    }

    public void removeImeSurface(android.os.IBinder iBinder) {
        synchronized (this.mH) {
            android.view.inputmethod.IInputMethodManagerGlobalInvoker.removeImeSurfaceFromWindowAsync(iBinder);
        }
    }

    public void updateSelection(android.view.View view, final int i, final int i2, final int i3, final int i4) {
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.updateSelection(view, i, i2, i3, i4);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                if (this.mServedInputConnection == null || !this.mServedInputConnection.hasPendingInvalidation()) {
                    if (this.mCursorSelStart != i || this.mCursorSelEnd != i2 || this.mCursorCandStart != i3 || this.mCursorCandEnd != i4) {
                        this.mCurBindState.mImeSession.updateSelection(this.mCursorSelStart, this.mCursorSelEnd, i, i2, i3, i4);
                        forAccessibilitySessionsLocked(new java.util.function.Consumer() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                android.view.inputmethod.InputMethodManager.this.lambda$updateSelection$4(i, i2, i3, i4, (android.view.inputmethod.IAccessibilityInputMethodSessionInvoker) obj);
                            }
                        });
                        this.mCursorSelStart = i;
                        this.mCursorSelEnd = i2;
                        this.mCursorCandStart = i3;
                        this.mCursorCandEnd = i4;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSelection$4(int i, int i2, int i3, int i4, android.view.inputmethod.IAccessibilityInputMethodSessionInvoker iAccessibilityInputMethodSessionInvoker) {
        iAccessibilityInputMethodSessionInvoker.updateSelection(this.mCursorSelStart, this.mCursorSelEnd, i, i2, i3, i4);
    }

    @java.lang.Deprecated
    public void viewClicked(android.view.View view) {
        android.view.View servedViewLocked;
        android.view.View nextServedViewLocked;
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.viewClicked(view);
            return;
        }
        synchronized (this.mH) {
            servedViewLocked = getServedViewLocked();
            nextServedViewLocked = getNextServedViewLocked();
        }
        boolean z = servedViewLocked != nextServedViewLocked;
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                this.mCurBindState.mImeSession.viewClicked(z);
            }
        }
    }

    @java.lang.Deprecated
    public boolean isWatchingCursor(android.view.View view) {
        return false;
    }

    @java.lang.Deprecated
    public boolean isCursorAnchorInfoEnabled() {
        boolean z;
        synchronized (this.mH) {
            z = true;
            boolean z2 = (this.mRequestUpdateCursorAnchorInfoMonitorMode & 1) != 0;
            boolean z3 = (this.mRequestUpdateCursorAnchorInfoMonitorMode & 2) != 0;
            if (!z2 && !z3) {
                z = false;
            }
        }
        return z;
    }

    @java.lang.Deprecated
    public void setUpdateCursorAnchorInfoMode(int i) {
        synchronized (this.mH) {
            this.mRequestUpdateCursorAnchorInfoMonitorMode = i;
        }
    }

    @java.lang.Deprecated
    public void updateCursor(android.view.View view, int i, int i2, int i3, int i4) {
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.updateCursor(view, i, i2, i3, i4);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                this.mTmpCursorRect.set(i, i2, i3, i4);
                if (!this.mCursorRect.equals(this.mTmpCursorRect)) {
                    this.mCurBindState.mImeSession.updateCursor(this.mTmpCursorRect);
                    this.mCursorRect.set(this.mTmpCursorRect);
                }
            }
        }
    }

    public void updateCursorAnchorInfo(android.view.View view, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo) {
        if (view == null || cursorAnchorInfo == null) {
            return;
        }
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.updateCursorAnchorInfo(view, cursorAnchorInfo);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                if ((this.mServedInputConnection != null && this.mServedInputConnection.resetHasPendingImmediateCursorAnchorInfoUpdate()) || !java.util.Objects.equals(this.mCursorAnchorInfo, cursorAnchorInfo)) {
                    this.mCurBindState.mImeSession.updateCursorAnchorInfo(cursorAnchorInfo);
                    this.mCursorAnchorInfo = cursorAnchorInfo;
                }
            }
        }
    }

    public void sendAppPrivateCommand(android.view.View view, java.lang.String str, android.os.Bundle bundle) {
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.sendAppPrivateCommand(view, str, bundle);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                this.mCurBindState.mImeSession.appPrivateCommand(str, bundle);
            }
        }
    }

    @java.lang.Deprecated
    public void setInputMethod(android.os.IBinder iBinder, java.lang.String str) {
        if (iBinder == null) {
            if (str == null) {
                return;
            }
            if (android.os.Process.myUid() == 1000) {
                android.util.Log.w(TAG, "System process should not be calling setInputMethod() because almost always it is a bug under multi-user / multi-profile environment. Consider interacting with InputMethodManagerService directly via LocalServices.");
                return;
            }
            android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
            if (currentApplication == null || currentApplication.checkSelfPermission(android.Manifest.permission.WRITE_SECURE_SETTINGS) != 0) {
                return;
            }
            java.util.List<android.view.inputmethod.InputMethodInfo> enabledInputMethodList = getEnabledInputMethodList();
            int size = enabledInputMethodList.size();
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (!str.equals(enabledInputMethodList.get(i).getId())) {
                    i++;
                } else {
                    z = true;
                    break;
                }
            }
            if (!z) {
                android.util.Log.e(TAG, "Ignoring setInputMethod(null, " + str + ") because the specified id not found in enabled IMEs.");
                return;
            }
            android.util.Log.w(TAG, "The undocumented behavior that setInputMethod() accepts null token when the caller has WRITE_SECURE_SETTINGS is deprecated. This behavior may be completely removed in a future version.  Update secure settings directly instead.");
            android.content.ContentResolver contentResolver = currentApplication.getContentResolver();
            android.provider.Settings.Secure.putInt(contentResolver, android.provider.Settings.Secure.SELECTED_INPUT_METHOD_SUBTYPE, -1);
            android.provider.Settings.Secure.putString(contentResolver, android.provider.Settings.Secure.DEFAULT_INPUT_METHOD, str);
            return;
        }
        com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry.get(iBinder).setInputMethod(str);
    }

    @java.lang.Deprecated
    public void setInputMethodAndSubtype(android.os.IBinder iBinder, java.lang.String str, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        if (iBinder == null) {
            android.util.Log.e(TAG, "setInputMethodAndSubtype() does not accept null token on Android Q and later.");
        } else {
            com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry.get(iBinder).setInputMethodAndSubtype(str, inputMethodSubtype);
        }
    }

    @java.lang.Deprecated
    public void hideSoftInputFromInputMethod(android.os.IBinder iBinder, int i) {
        com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry.get(iBinder).hideMySoftInput(android.view.inputmethod.ImeTracker.forLogging().onStart(2, 5, 32, false), i, 32);
    }

    @java.lang.Deprecated
    public void showSoftInputFromInputMethod(android.os.IBinder iBinder, int i) {
        com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry.get(iBinder).showMySoftInput(android.view.inputmethod.ImeTracker.forLogging().onStart(1, 5, 54, false), i, 54);
    }

    public int dispatchInputEvent(android.view.InputEvent inputEvent, java.lang.Object obj, android.view.inputmethod.InputMethodManager.FinishedInputEventCallback finishedInputEventCallback, android.os.Handler handler) {
        synchronized (this.mH) {
            if (isImeSessionAvailableLocked()) {
                if (inputEvent instanceof android.view.KeyEvent) {
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) inputEvent;
                    if (keyEvent.getAction() == 0 && keyEvent.getKeyCode() == 63 && keyEvent.getRepeatCount() == 0) {
                        showInputMethodPickerLocked();
                        return 1;
                    }
                }
                android.view.inputmethod.InputMethodManager.PendingEvent obtainPendingEventLocked = obtainPendingEventLocked(inputEvent, obj, this.mCurBindState.mImeId, finishedInputEventCallback, handler);
                if (this.mMainLooper.isCurrentThread()) {
                    return sendInputEventOnMainLooperLocked(obtainPendingEventLocked);
                }
                android.os.Message obtainMessage = this.mH.obtainMessage(5, obtainPendingEventLocked);
                obtainMessage.setAsynchronous(true);
                this.mH.sendMessage(obtainMessage);
                return -1;
            }
            return 0;
        }
    }

    public void dispatchKeyEventFromInputMethod(android.view.View view, android.view.KeyEvent keyEvent) {
        android.view.ViewRootImpl viewRootImpl;
        android.view.View servedViewLocked;
        android.view.inputmethod.InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.dispatchKeyEventFromInputMethod(view, keyEvent);
            return;
        }
        synchronized (this.mH) {
            if (view == null) {
                viewRootImpl = null;
            } else {
                try {
                    viewRootImpl = view.getViewRootImpl();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (viewRootImpl == null && (servedViewLocked = getServedViewLocked()) != null) {
                viewRootImpl = servedViewLocked.getViewRootImpl();
            }
            if (viewRootImpl != null) {
                viewRootImpl.dispatchKeyFromIme(keyEvent);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendInputEventAndReportResultOnMainLooper(android.view.inputmethod.InputMethodManager.PendingEvent pendingEvent) {
        synchronized (this.mH) {
            int sendInputEventOnMainLooperLocked = sendInputEventOnMainLooperLocked(pendingEvent);
            if (sendInputEventOnMainLooperLocked == -1) {
                return;
            }
            boolean z = true;
            if (sendInputEventOnMainLooperLocked != 1) {
                z = false;
            }
            invokeFinishedInputEventCallback(pendingEvent, z);
        }
    }

    private int sendInputEventOnMainLooperLocked(android.view.inputmethod.InputMethodManager.PendingEvent pendingEvent) {
        if (this.mCurChannel != null) {
            if (this.mCurSender == null) {
                this.mCurSender = new android.view.inputmethod.InputMethodManager.ImeInputEventSender(this.mCurChannel, this.mH.getLooper());
            }
            android.view.InputEvent inputEvent = pendingEvent.mEvent;
            int sequenceNumber = inputEvent.getSequenceNumber();
            if (this.mCurSender.sendInputEvent(sequenceNumber, inputEvent)) {
                this.mPendingEvents.put(sequenceNumber, pendingEvent);
                android.os.Trace.traceCounter(4L, PENDING_EVENT_COUNTER, this.mPendingEvents.size());
                android.os.Message obtainMessage = this.mH.obtainMessage(6, sequenceNumber, 0, pendingEvent);
                obtainMessage.setAsynchronous(true);
                this.mH.sendMessageDelayed(obtainMessage, INPUT_METHOD_NOT_RESPONDING_TIMEOUT);
                return -1;
            }
            if (sPreventImeStartupUnlessTextEditor) {
                android.util.Log.d(TAG, "Dropping event because IME is evicted: " + inputEvent);
            } else {
                android.util.Log.w(TAG, "Unable to send input event to IME: " + getImeIdLocked() + " dropping: " + inputEvent);
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishedInputEvent(int i, boolean z, boolean z2) {
        synchronized (this.mH) {
            int indexOfKey = this.mPendingEvents.indexOfKey(i);
            if (indexOfKey < 0) {
                return;
            }
            android.view.inputmethod.InputMethodManager.PendingEvent valueAt = this.mPendingEvents.valueAt(indexOfKey);
            this.mPendingEvents.removeAt(indexOfKey);
            android.os.Trace.traceCounter(4L, PENDING_EVENT_COUNTER, this.mPendingEvents.size());
            if (z2) {
                android.util.Log.w(TAG, "Timeout waiting for IME to handle input event after 2500 ms: " + valueAt.mInputMethodId);
            } else {
                this.mH.removeMessages(6, valueAt);
            }
            invokeFinishedInputEventCallback(valueAt, z);
        }
    }

    private void invokeFinishedInputEventCallback(android.view.inputmethod.InputMethodManager.PendingEvent pendingEvent, boolean z) {
        pendingEvent.mHandled = z;
        if (pendingEvent.mHandler.getLooper().isCurrentThread()) {
            pendingEvent.run();
            return;
        }
        android.os.Message obtain = android.os.Message.obtain(pendingEvent.mHandler, pendingEvent);
        obtain.setAsynchronous(true);
        obtain.sendToTarget();
    }

    private void flushPendingEventsLocked() {
        this.mH.removeMessages(7);
        int size = this.mPendingEvents.size();
        for (int i = 0; i < size; i++) {
            android.os.Message obtainMessage = this.mH.obtainMessage(7, this.mPendingEvents.keyAt(i), 0);
            obtainMessage.setAsynchronous(true);
            obtainMessage.sendToTarget();
        }
    }

    private android.view.inputmethod.InputMethodManager.PendingEvent obtainPendingEventLocked(android.view.InputEvent inputEvent, java.lang.Object obj, java.lang.String str, android.view.inputmethod.InputMethodManager.FinishedInputEventCallback finishedInputEventCallback, android.os.Handler handler) {
        android.view.inputmethod.InputMethodManager.PendingEvent acquire = this.mPendingEventPool.acquire();
        if (acquire == null) {
            acquire = new android.view.inputmethod.InputMethodManager.PendingEvent();
        }
        acquire.mEvent = inputEvent;
        acquire.mToken = obj;
        acquire.mInputMethodId = str;
        acquire.mCallback = finishedInputEventCallback;
        acquire.mHandler = handler;
        return acquire;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recyclePendingEventLocked(android.view.inputmethod.InputMethodManager.PendingEvent pendingEvent) {
        pendingEvent.recycle();
        this.mPendingEventPool.release(pendingEvent);
    }

    public void showInputMethodPicker() {
        synchronized (this.mH) {
            showInputMethodPickerLocked();
        }
    }

    public void showInputMethodPickerFromSystem(boolean z, int i) {
        int i2;
        if (z) {
            i2 = 1;
        } else {
            i2 = 2;
        }
        android.view.inputmethod.IInputMethodManagerGlobalInvoker.showInputMethodPickerFromSystem(i2, i);
    }

    private void showInputMethodPickerLocked() {
        android.view.inputmethod.IInputMethodManagerGlobalInvoker.showInputMethodPickerFromClient(this.mClient, 0);
    }

    public boolean isInputMethodPickerShown() {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.isInputMethodPickerShownForTest();
    }

    public boolean hasPendingImeVisibilityRequests() {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.hasPendingImeVisibilityRequests();
    }

    public void showInputMethodAndSubtypeEnabler(java.lang.String str) {
        android.content.Context context;
        synchronized (this.mH) {
            if (this.mCurRootView == null) {
                context = null;
            } else {
                context = this.mCurRootView.mContext;
            }
        }
        if (context == null) {
            android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
            context = currentApplication.createDisplayContext(((android.hardware.display.DisplayManager) currentApplication.getSystemService(android.hardware.display.DisplayManager.class)).getDisplay(this.mDisplayId));
        }
        android.content.Intent intent = new android.content.Intent(android.provider.Settings.ACTION_INPUT_METHOD_SUBTYPE_SETTINGS);
        intent.setFlags(android.app.tvsettings.TvSettingsEnums.PRIVACY_DIAGNOSTICS);
        if (!android.text.TextUtils.isEmpty(str)) {
            intent.putExtra(android.provider.Settings.EXTRA_INPUT_METHOD_ID, str);
        }
        context.startActivity(intent);
    }

    public android.view.inputmethod.InputMethodSubtype getCurrentInputMethodSubtype() {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.getCurrentInputMethodSubtype(android.os.UserHandle.myUserId());
    }

    @java.lang.Deprecated
    public boolean setCurrentInputMethodSubtype(android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        android.app.Application currentApplication;
        if (android.os.Process.myUid() == 1000) {
            android.util.Log.w(TAG, "System process should not call setCurrentInputMethodSubtype() because almost always it is a bug under multi-user / multi-profile environment. Consider directly interacting with InputMethodManagerService via LocalServices.");
            return false;
        }
        if (inputMethodSubtype == null || (currentApplication = android.app.ActivityThread.currentApplication()) == null || currentApplication.checkSelfPermission(android.Manifest.permission.WRITE_SECURE_SETTINGS) != 0) {
            return false;
        }
        android.content.ContentResolver contentResolver = currentApplication.getContentResolver();
        java.lang.String string = android.provider.Settings.Secure.getString(contentResolver, android.provider.Settings.Secure.DEFAULT_INPUT_METHOD);
        if (android.content.ComponentName.unflattenFromString(string) == null) {
            return false;
        }
        java.util.List<android.view.inputmethod.InputMethodSubtype> enabledInputMethodSubtypeList = android.view.inputmethod.IInputMethodManagerGlobalInvoker.getEnabledInputMethodSubtypeList(string, true, android.os.UserHandle.myUserId());
        int size = enabledInputMethodSubtypeList.size();
        for (int i = 0; i < size; i++) {
            android.view.inputmethod.InputMethodSubtype inputMethodSubtype2 = enabledInputMethodSubtypeList.get(i);
            if (inputMethodSubtype2.equals(inputMethodSubtype)) {
                android.provider.Settings.Secure.putInt(contentResolver, android.provider.Settings.Secure.SELECTED_INPUT_METHOD_SUBTYPE, inputMethodSubtype2.hashCode());
                return true;
            }
        }
        return false;
    }

    @java.lang.Deprecated
    public void notifyUserAction() {
        android.util.Log.w(TAG, "notifyUserAction() is a hidden method, which is now just a stub method that does nothing.  Leave comments in b.android.com/114740982 if your  application still depends on the previous behavior of this method.");
    }

    public java.util.Map<android.view.inputmethod.InputMethodInfo, java.util.List<android.view.inputmethod.InputMethodSubtype>> getShortcutInputMethodsAndSubtypes() {
        java.util.List<android.view.inputmethod.InputMethodInfo> enabledInputMethodList = getEnabledInputMethodList();
        enabledInputMethodList.sort(java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                return android.view.inputmethod.InputMethodManager.lambda$getShortcutInputMethodsAndSubtypes$5((android.view.inputmethod.InputMethodInfo) obj);
            }
        }));
        int size = enabledInputMethodList.size();
        for (int i = 0; i < size; i++) {
            android.view.inputmethod.InputMethodInfo inputMethodInfo = enabledInputMethodList.get(i);
            int size2 = getEnabledInputMethodSubtypeList(inputMethodInfo, true).size();
            for (int i2 = 0; i2 < size2; i2++) {
                android.view.inputmethod.InputMethodSubtype subtypeAt = inputMethodInfo.getSubtypeAt(i2);
                if (SUBTYPE_MODE_VOICE.equals(subtypeAt.getMode())) {
                    return java.util.Collections.singletonMap(inputMethodInfo, java.util.Collections.singletonList(subtypeAt));
                }
            }
        }
        return java.util.Collections.emptyMap();
    }

    static /* synthetic */ int lambda$getShortcutInputMethodsAndSubtypes$5(android.view.inputmethod.InputMethodInfo inputMethodInfo) {
        return !inputMethodInfo.isSystem() ? 1 : 0;
    }

    public int getInputMethodWindowVisibleHeight() {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.getInputMethodWindowVisibleHeight(this.mClient);
    }

    public void setRequestCursorUpdateDisplayIdCheck(boolean z) {
        this.mRequestCursorUpdateDisplayIdCheck.set(z);
    }

    @java.lang.Deprecated
    public boolean switchToLastInputMethod(android.os.IBinder iBinder) {
        return com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry.get(iBinder).switchToPreviousInputMethod();
    }

    @java.lang.Deprecated
    public boolean switchToNextInputMethod(android.os.IBinder iBinder, boolean z) {
        return com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry.get(iBinder).switchToNextInputMethod(z);
    }

    @java.lang.Deprecated
    public boolean shouldOfferSwitchingToNextInputMethod(android.os.IBinder iBinder) {
        return com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry.get(iBinder).shouldOfferSwitchingToNextInputMethod();
    }

    @java.lang.Deprecated
    public void setAdditionalInputMethodSubtypes(java.lang.String str, android.view.inputmethod.InputMethodSubtype[] inputMethodSubtypeArr) {
        android.view.inputmethod.IInputMethodManagerGlobalInvoker.setAdditionalInputMethodSubtypes(str, inputMethodSubtypeArr, android.os.UserHandle.myUserId());
    }

    public void setExplicitlyEnabledInputMethodSubtypes(java.lang.String str, int[] iArr) {
        android.view.inputmethod.IInputMethodManagerGlobalInvoker.setExplicitlyEnabledInputMethodSubtypes(str, iArr, android.os.UserHandle.myUserId());
    }

    public android.view.inputmethod.InputMethodSubtype getLastInputMethodSubtype() {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.getLastInputMethodSubtype(android.os.UserHandle.myUserId());
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (processDump(fileDescriptor, strArr)) {
            return;
        }
        android.util.PrintWriterPrinter printWriterPrinter = new android.util.PrintWriterPrinter(printWriter);
        printWriterPrinter.println("Input method client state for " + this + ":");
        printWriterPrinter.println("  mFallbackInputConnection=" + this.mFallbackInputConnection);
        printWriterPrinter.println("  mActive=" + this.mActive + " mRestartOnNextWindowFocus=" + this.mRestartOnNextWindowFocus + " mBindSequence=" + getBindSequenceLocked() + " mCurImeId=" + getImeIdLocked());
        printWriterPrinter.println("  mFullscreenMode=" + this.mFullscreenMode);
        if (isImeSessionAvailableLocked()) {
            printWriterPrinter.println("  mCurMethod=" + this.mCurBindState.mImeSession);
        } else {
            printWriterPrinter.println("  mCurMethod= null");
        }
        for (int i = 0; i < this.mAccessibilityInputMethodSession.size(); i++) {
            printWriterPrinter.println("  mAccessibilityInputMethodSession(" + this.mAccessibilityInputMethodSession.keyAt(i) + ")=" + this.mAccessibilityInputMethodSession.valueAt(i));
        }
        printWriterPrinter.println("  mCurRootView=" + this.mCurRootView);
        printWriterPrinter.println("  mServedView=" + getServedViewLocked());
        printWriterPrinter.println("  mNextServedView=" + getNextServedViewLocked());
        printWriterPrinter.println("  mServedConnecting=" + this.mServedConnecting);
        if (this.mCurrentEditorInfo != null) {
            printWriterPrinter.println("  mCurrentEditorInfo:");
            this.mCurrentEditorInfo.dump(printWriterPrinter, "    ", false);
        } else {
            printWriterPrinter.println("  mCurrentEditorInfo: null");
        }
        printWriterPrinter.println("  mServedInputConnection=" + this.mServedInputConnection);
        printWriterPrinter.println("  mServedInputConnectionHandler=" + this.mServedInputConnectionHandler);
        printWriterPrinter.println("  mCompletions=" + java.util.Arrays.toString(this.mCompletions));
        printWriterPrinter.println("  mCursorRect=" + this.mCursorRect);
        printWriterPrinter.println("  mCursorSelStart=" + this.mCursorSelStart + " mCursorSelEnd=" + this.mCursorSelEnd + " mCursorCandStart=" + this.mCursorCandStart + " mCursorCandEnd=" + this.mCursorCandEnd);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ConnectionlessHandwritingCallbackProxy extends com.android.internal.inputmethod.IConnectionlessHandwritingCallback.Stub {
        private android.view.inputmethod.ConnectionlessHandwritingCallback mCallback;
        private java.util.concurrent.Executor mExecutor;
        private final java.lang.Object mLock = new java.lang.Object();

        ConnectionlessHandwritingCallbackProxy(java.util.concurrent.Executor executor, android.view.inputmethod.ConnectionlessHandwritingCallback connectionlessHandwritingCallback) {
            this.mExecutor = executor;
            this.mCallback = connectionlessHandwritingCallback;
        }

        @Override // com.android.internal.inputmethod.IConnectionlessHandwritingCallback
        public void onResult(final java.lang.CharSequence charSequence) {
            synchronized (this.mLock) {
                if (this.mExecutor != null && this.mCallback != null) {
                    java.util.concurrent.Executor executor = this.mExecutor;
                    final android.view.inputmethod.ConnectionlessHandwritingCallback connectionlessHandwritingCallback = this.mCallback;
                    this.mExecutor = null;
                    this.mCallback = null;
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (android.text.TextUtils.isEmpty(charSequence)) {
                            executor.execute(new java.lang.Runnable() { // from class: android.view.inputmethod.InputMethodManager$ConnectionlessHandwritingCallbackProxy$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.view.inputmethod.ConnectionlessHandwritingCallback.this.onError(0);
                                }
                            });
                        } else {
                            executor.execute(new java.lang.Runnable() { // from class: android.view.inputmethod.InputMethodManager$ConnectionlessHandwritingCallbackProxy$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.view.inputmethod.ConnectionlessHandwritingCallback.this.onResult(charSequence);
                                }
                            });
                        }
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        }

        @Override // com.android.internal.inputmethod.IConnectionlessHandwritingCallback
        public void onError(final int i) {
            synchronized (this.mLock) {
                if (this.mExecutor != null && this.mCallback != null) {
                    java.util.concurrent.Executor executor = this.mExecutor;
                    final android.view.inputmethod.ConnectionlessHandwritingCallback connectionlessHandwritingCallback = this.mCallback;
                    this.mExecutor = null;
                    this.mCallback = null;
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        executor.execute(new java.lang.Runnable() { // from class: android.view.inputmethod.InputMethodManager$ConnectionlessHandwritingCallbackProxy$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.view.inputmethod.ConnectionlessHandwritingCallback.this.onError(i);
                            }
                        });
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        }
    }

    private final class ImeInputEventSender extends android.view.InputEventSender {
        public ImeInputEventSender(android.view.InputChannel inputChannel, android.os.Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventSender
        public void onInputEventFinished(int i, boolean z) {
            android.view.inputmethod.InputMethodManager.this.finishedInputEvent(i, z, false);
        }
    }

    private final class PendingEvent implements java.lang.Runnable {
        public android.view.inputmethod.InputMethodManager.FinishedInputEventCallback mCallback;
        public android.view.InputEvent mEvent;
        public boolean mHandled;
        public android.os.Handler mHandler;
        public java.lang.String mInputMethodId;
        public java.lang.Object mToken;

        private PendingEvent() {
        }

        public void recycle() {
            this.mEvent = null;
            this.mToken = null;
            this.mInputMethodId = null;
            this.mCallback = null;
            this.mHandler = null;
            this.mHandled = false;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mCallback.onFinishedInputEvent(this.mToken, this.mHandled);
            synchronized (android.view.inputmethod.InputMethodManager.this.mH) {
                android.view.inputmethod.InputMethodManager.this.recyclePendingEventLocked(this);
            }
        }
    }

    private static final class BindState {
        final int mBindSequence;
        final java.lang.String mImeId;
        final android.view.inputmethod.IInputMethodSessionInvoker mImeSession;
        final boolean mIsInputMethodSuppressingSpellChecker;

        BindState(com.android.internal.inputmethod.InputBindResult inputBindResult) {
            this.mImeSession = android.view.inputmethod.IInputMethodSessionInvoker.createOrNull(inputBindResult.method);
            this.mIsInputMethodSuppressingSpellChecker = inputBindResult.isInputMethodSuppressingSpellChecker;
            this.mImeId = inputBindResult.id;
            this.mBindSequence = inputBindResult.sequence;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isImeSessionAvailableLocked() {
        return (this.mCurBindState == null || this.mCurBindState.mImeSession == null) ? false : true;
    }

    private java.lang.String getImeIdLocked() {
        if (this.mCurBindState != null) {
            return this.mCurBindState.mImeId;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getBindSequenceLocked() {
        if (this.mCurBindState != null) {
            return this.mCurBindState.mBindSequence;
        }
        return -1;
    }

    private boolean processDump(java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) {
        if (strArr == null) {
            return false;
        }
        for (java.lang.String str : strArr) {
            if (str.equals(com.android.internal.inputmethod.ImeTracing.PROTO_ARG)) {
                android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
                dumpDebug(protoOutputStream, null);
                protoOutputStream.flush();
                return true;
            }
        }
        return false;
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, byte[] bArr) {
        synchronized (this.mH) {
            if (isImeSessionAvailableLocked()) {
                protoOutputStream.write(1120986464257L, this.mDisplayId);
                long start = protoOutputStream.start(1146756268034L);
                protoOutputStream.write(1138166333441L, this.mCurBindState.mImeId);
                protoOutputStream.write(1133871366146L, this.mFullscreenMode);
                protoOutputStream.write(1133871366148L, this.mActive);
                protoOutputStream.write(1133871366149L, this.mServedConnecting);
                protoOutputStream.write(1138166333446L, java.util.Objects.toString(this.mServedView));
                protoOutputStream.write(1138166333447L, java.util.Objects.toString(this.mNextServedView));
                protoOutputStream.end(start);
                if (this.mCurRootView != null) {
                    this.mCurRootView.dumpDebug(protoOutputStream, 1146756268035L);
                }
                if (this.mCurrentEditorInfo != null) {
                    this.mCurrentEditorInfo.dumpDebug(protoOutputStream, 1146756268038L);
                }
                if (this.mImeInsetsConsumer != null) {
                    this.mImeInsetsConsumer.dumpDebug(protoOutputStream, 1146756268037L);
                }
                if (this.mServedInputConnection != null) {
                    this.mServedInputConnection.dumpDebug(protoOutputStream, 1146756268040L);
                }
                if (bArr != null) {
                    protoOutputStream.write(1146756268041L, bArr);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forAccessibilitySessionsLocked(java.util.function.Consumer<android.view.inputmethod.IAccessibilityInputMethodSessionInvoker> consumer) {
        for (int i = 0; i < this.mAccessibilityInputMethodSession.size(); i++) {
            consumer.accept(this.mAccessibilityInputMethodSession.valueAt(i));
        }
    }

    private static android.util.Pair<android.view.inputmethod.InputConnection, android.view.inputmethod.EditorInfo> createInputConnection(android.view.View view) {
        android.view.inputmethod.EditorInfo editorInfo = new android.view.inputmethod.EditorInfo();
        editorInfo.packageName = view.getContext().getOpPackageName();
        editorInfo.autofillId = view.getAutofillId();
        editorInfo.fieldId = view.getId();
        android.view.inputmethod.InputConnection onCreateInputConnection = view.onCreateInputConnection(editorInfo);
        if (onCreateInputConnection == null) {
            editorInfo.autofillId = android.view.autofill.AutofillId.NO_AUTOFILL_ID;
            editorInfo.fieldId = 0;
        }
        return new android.util.Pair<>(onCreateInputConnection, editorInfo);
    }
}
