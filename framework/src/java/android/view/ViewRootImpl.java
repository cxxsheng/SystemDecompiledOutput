package android.view;

/* loaded from: classes4.dex */
public final class ViewRootImpl implements android.view.ViewParent, android.view.View.AttachInfo.Callbacks, android.view.ThreadedRenderer.DrawCallbacks, android.view.AttachedSurfaceControl {
    private static final int CONTENT_CAPTURE_ENABLED_FALSE = 2;
    private static final int CONTENT_CAPTURE_ENABLED_NOT_CHECKED = 0;
    private static final int CONTENT_CAPTURE_ENABLED_TRUE = 1;
    private static final boolean DBG = false;
    private static final boolean DEBUG_BLAST = false;
    private static final boolean DEBUG_CONFIGURATION = false;
    private static final boolean DEBUG_CONTENT_CAPTURE = false;
    private static final boolean DEBUG_DIALOG = false;
    private static final boolean DEBUG_DRAW = false;
    private static final boolean DEBUG_FPS = false;
    private static final boolean DEBUG_IMF = false;
    private static final boolean DEBUG_INPUT_RESIZE = false;
    private static final boolean DEBUG_INPUT_STAGES = false;
    private static final boolean DEBUG_KEEP_SCREEN_ON = false;
    private static final boolean DEBUG_LAYOUT = false;
    private static final boolean DEBUG_ORIENTATION = false;
    private static final boolean DEBUG_SCROLL_CAPTURE = false;
    private static final boolean DEBUG_TOUCH_NAVIGATION = false;
    private static final boolean DEBUG_TRACKBALL = false;
    private static final boolean ENABLE_INPUT_LATENCY_TRACKING = true;
    private static final int FRAME_RATE_CATEGORY_COUNT = 5;
    private static final int FRAME_RATE_IDLENESS_CHECK_TIME_MILLIS = 500;
    private static final int FRAME_RATE_IDLENESS_REEVALUATE_TIME = 1000;
    private static final int FRAME_RATE_SETTING_REEVALUATE_TIME = 100;
    private static final int FRAME_RATE_TOUCH_BOOST_TIME = 3000;
    private static final int INVALID_VALUE = Integer.MIN_VALUE;
    private static final int KEEP_CLEAR_AREA_REPORT_RATE_MILLIS = 100;
    private static final boolean LOCAL_LOGV = false;
    private static final int LOGTAG_INPUT_FOCUS = 62001;
    private static final int LOGTAG_VIEWROOT_DRAW_EVENT = 60004;
    private static final int MAX_QUEUED_INPUT_EVENT_POOL_SIZE = 10;
    static final int MAX_TRACKBALL_DELAY = 250;
    private static final int MSG_CHECK_FOCUS = 13;
    private static final int MSG_CHECK_INVALIDATION_IDLE = 40;
    private static final int MSG_CLEAR_ACCESSIBILITY_FOCUS_HOST = 21;
    private static final int MSG_CLOSE_SYSTEM_DIALOGS = 14;
    private static final int MSG_DECOR_VIEW_GESTURE_INTERCEPTION = 38;
    private static final int MSG_DIE = 3;
    private static final int MSG_DISPATCH_APP_VISIBILITY = 8;
    private static final int MSG_DISPATCH_DRAG_EVENT = 15;
    private static final int MSG_DISPATCH_DRAG_LOCATION_EVENT = 16;
    private static final int MSG_DISPATCH_GET_NEW_SURFACE = 9;
    private static final int MSG_DISPATCH_INPUT_EVENT = 7;
    private static final int MSG_DISPATCH_KEY_FROM_AUTOFILL = 12;
    private static final int MSG_DISPATCH_KEY_FROM_IME = 11;
    private static final int MSG_DISPATCH_SYSTEM_UI_VISIBILITY = 17;
    private static final int MSG_DISPATCH_WINDOW_SHOWN = 25;
    private static final int MSG_FRAME_RATE_SETTING = 42;
    private static final int MSG_HIDE_INSETS = 32;
    private static final int MSG_INSETS_CONTROL_CHANGED = 29;
    private static final int MSG_INVALIDATE = 1;
    private static final int MSG_INVALIDATE_RECT = 2;
    private static final int MSG_INVALIDATE_WORLD = 22;
    private static final int MSG_KEEP_CLEAR_RECTS_CHANGED = 35;
    private static final int MSG_PAUSED_FOR_SYNC_TIMEOUT = 37;
    private static final int MSG_POINTER_CAPTURE_CHANGED = 28;
    private static final int MSG_PROCESS_INPUT_EVENTS = 19;
    private static final int MSG_REFRESH_POINTER_ICON = 41;
    private static final int MSG_REPORT_KEEP_CLEAR_RECTS = 36;
    private static final int MSG_REQUEST_KEYBOARD_SHORTCUTS = 26;
    private static final int MSG_REQUEST_SCROLL_CAPTURE = 33;
    private static final int MSG_RESIZED = 4;
    private static final int MSG_RESIZED_REPORT = 5;
    private static final int MSG_SHOW_INSETS = 31;
    private static final int MSG_SYNTHESIZE_INPUT_EVENT = 24;
    private static final int MSG_SYSTEM_GESTURE_EXCLUSION_CHANGED = 30;
    private static final int MSG_TOUCH_BOOST_TIMEOUT = 39;
    private static final int MSG_UPDATE_CONFIGURATION = 18;
    private static final int MSG_UPDATE_POINTER_ICON = 27;
    private static final int MSG_WINDOW_FOCUS_CHANGED = 6;
    private static final int MSG_WINDOW_MOVED = 23;
    private static final int MSG_WINDOW_TOUCH_MODE_CHANGED = 34;
    private static final boolean MT_RENDERER_AVAILABLE = true;
    private static final long NANOS_PER_SEC = 1000000000;
    private static final java.lang.String PROPERTY_PROFILE_RENDERING = "viewroot.profile_rendering";
    private static final int SCROLL_CAPTURE_REQUEST_TIMEOUT_MILLIS = 2500;
    private static final java.lang.String TAG = "ViewRootImpl";
    private static final int UNSET_SYNC_ID = -1;
    private static final boolean USE_ASYNC_PERFORM_HAPTIC_FEEDBACK = true;
    private static final int WMS_SYNC_MERGED = 3;
    private static final int WMS_SYNC_NONE = 0;
    private static final int WMS_SYNC_PENDING = 1;
    private static final int WMS_SYNC_RETURNED = 2;
    private static boolean sAlwaysAssignFocus;
    private android.view.accessibility.IAccessibilityEmbeddedConnection mAccessibilityEmbeddedConnection;
    android.view.View mAccessibilityFocusedHost;
    android.view.accessibility.AccessibilityNodeInfo mAccessibilityFocusedVirtualView;
    final android.view.ViewRootImpl.AccessibilityInteractionConnectionManager mAccessibilityInteractionConnectionManager;
    android.view.AccessibilityInteractionController mAccessibilityInteractionController;
    final android.view.accessibility.AccessibilityManager mAccessibilityManager;
    private android.view.accessibility.AccessibilityWindowAttributes mAccessibilityWindowAttributes;
    private android.window.SurfaceSyncGroup mActiveSurfaceSyncGroup;
    private android.view.ViewRootImpl.ActivityConfigCallback mActivityConfigCallback;
    boolean mAdded;
    boolean mAddedTouchMode;
    private boolean mAppVisibilityChanged;
    boolean mAppVisible;
    boolean mApplyInsetsRequested;
    final android.view.View.AttachInfo mAttachInfo;
    android.media.AudioManager mAudioManager;
    final java.lang.String mBasePackageName;
    private android.graphics.BLASTBufferQueue mBlastBufferQueue;
    private final com.android.internal.graphics.drawable.BackgroundBlurDrawable.Aggregator mBlurRegionAggregator;
    private android.view.SurfaceControl mBoundsLayer;
    private int mCanvasOffsetX;
    private int mCanvasOffsetY;
    private boolean mCheckIfCanDraw;
    private final android.graphics.Rect mChildBoundingInsets;
    private boolean mChildBoundingInsetsChanged;
    final android.view.Choreographer mChoreographer;
    int mClientWindowLayoutFlags;
    private android.window.CompatOnBackInvokedCallback mCompatOnBackInvokedCallback;
    final android.view.ViewRootImpl.SystemUiVisibilityInfo mCompatibleVisibilityInfo;
    final android.view.ViewRootImpl.ConsumeBatchedInputImmediatelyRunnable mConsumeBatchedInputImmediatelyRunnable;
    boolean mConsumeBatchedInputImmediatelyScheduled;
    boolean mConsumeBatchedInputScheduled;
    final android.view.ViewRootImpl.ConsumeBatchedInputRunnable mConsumedBatchedInputRunnable;
    int mContentCaptureEnabled;
    public final android.content.Context mContext;
    int mCurScrollY;
    android.view.View mCurrentDragView;
    private android.view.PointerIcon mCustomPointerIcon;
    private final int mDensity;
    private android.graphics.Rect mDirty;
    int mDispatchedSystemBarAppearance;
    int mDispatchedSystemUiVisibility;
    android.view.Display mDisplay;
    boolean mDisplayDecorationCached;
    private final android.hardware.display.DisplayManager.DisplayListener mDisplayListener;
    android.content.ClipDescription mDragDescription;
    final android.graphics.PointF mDragPoint;
    private boolean mDragResizing;
    boolean mDrawingAllowed;
    private boolean mDrewOnceForSync;
    final java.util.concurrent.Executor mExecutor;
    final boolean mExtraDisplayListenerLogging;
    android.view.FallbackEventHandler mFallbackEventHandler;
    private boolean mFastScrollSoundEffectsEnabled;
    boolean mFirst;
    android.view.ViewRootImpl.InputStage mFirstInputStage;
    android.view.ViewRootImpl.InputStage mFirstPostImeInputStage;
    private boolean mForceDecorViewVisibility;
    private int mForceInvertEnabled;
    private android.database.ContentObserver mForceInvertObserver;
    private boolean mForceNextConfigUpdate;
    boolean mForceNextWindowRelayout;
    private int mFpsNumFrames;
    private long mFpsPrevTime;
    private long mFpsStartTime;
    private java.lang.String mFpsTraceName;
    private int mFrameRateCategoryHighCount;
    private int mFrameRateCategoryHighHintCount;
    private int mFrameRateCategoryLowCount;
    private int mFrameRateCategoryNormalCount;
    int mFrameRateCompatibility;
    private final android.view.SurfaceControl.Transaction mFrameRateTransaction;
    boolean mFullRedrawNeeded;
    private final android.view.ViewRootRectTracker mGestureExclusionTracker;
    final android.view.ViewRootImpl.ViewRootHandler mHandler;
    boolean mHandlingLayoutInLayoutRequest;
    private final android.view.HandwritingInitiator mHandwritingInitiator;
    android.graphics.HardwareRendererObserver mHardwareRendererObserver;
    int mHardwareXOffset;
    int mHardwareYOffset;
    private boolean mHasIdledMessage;
    private boolean mHasInvalidation;
    private boolean mHasPendingKeepClearAreaChange;
    boolean mHasPendingTransactions;
    private final android.view.HdrRenderState mHdrRenderState;
    int mHeight;
    final android.view.ViewRootImpl.HighContrastTextManager mHighContrastTextManager;
    private final android.view.ImeFocusController mImeFocusController;
    private boolean mInLayout;
    private final android.view.InputEventCompatProcessor mInputCompatProcessor;
    private final android.view.InputEventAssigner mInputEventAssigner;
    protected final android.view.InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    private android.view.ViewRootImpl.WindowInputEventReceiver mInputEventReceiver;
    android.view.InputQueue mInputQueue;
    android.view.InputQueue.Callback mInputQueueCallback;
    private boolean mInsetsAnimationRunning;
    private final android.view.InsetsController mInsetsController;
    private float mInvCompatScale;
    final android.view.ViewRootImpl.InvalidateOnAnimationRunnable mInvalidateOnAnimationRunnable;
    private boolean mInvalidateRootRequested;
    boolean mIsAmbientMode;
    public boolean mIsAnimating;
    boolean mIsCreating;
    boolean mIsDrawing;
    private boolean mIsFrameRateBoosting;
    private boolean mIsFrameRateConflicted;
    private boolean mIsFrameRatePowerSavingsBalanced;
    boolean mIsInTraversal;
    private final boolean mIsStylusPointerIconEnabled;
    private boolean mIsSurfaceOpaque;
    private boolean mIsTouchBoosting;
    private android.graphics.Rect mKeepClearAccessibilityFocusRect;
    private final android.view.ViewRootRectTracker mKeepClearRectsTracker;
    private float mLargestChildPercentage;
    private java.lang.String mLargestViewTraceName;
    private int mLastClickToolType;
    private final android.content.res.Configuration mLastConfigurationFromResources;
    private boolean mLastDrawScreenOff;
    final android.view.ViewTreeObserver.InternalInsetsInfo mLastGivenInsets;
    private final android.graphics.Rect mLastLayoutFrame;
    java.lang.String mLastPerformDrawSkippedReason;
    java.lang.String mLastPerformTraversalsSkipDrawReason;
    private float mLastPreferredFrameRate;
    private int mLastPreferredFrameRateCategory;
    java.lang.String mLastReportNextDrawReason;
    private final android.util.MergedConfiguration mLastReportedMergedConfiguration;
    java.lang.ref.WeakReference<android.view.View> mLastScrolledFocus;
    private final android.graphics.Point mLastSurfaceSize;
    int mLastSyncSeqId;
    int mLastSystemUiVisibility;
    int mLastTouchDeviceId;
    final android.graphics.PointF mLastTouchPoint;
    int mLastTouchPointerId;
    int mLastTouchSource;
    private boolean mLastTraversalWasVisible;
    private android.view.WindowInsets mLastWindowInsets;
    boolean mLayoutRequested;
    java.util.ArrayList<android.view.View> mLayoutRequesters;
    final android.os.IBinder mLeashToken;
    volatile java.lang.Object mLocalDragState;
    final android.view.WindowLeaked mLocation;
    private int mMeasuredHeight;
    private int mMeasuredWidth;
    private boolean mNeedsRendererSetup;
    boolean mNewSurfaceNeeded;
    private final int mNoncompatDensity;
    private int mNumPausedForSync;
    private final android.window.WindowOnBackInvokedDispatcher mOnBackInvokedDispatcher;
    int mOrigWindowType;
    android.graphics.Rect mOverrideInsetsFrame;
    boolean mPausedForTransition;
    boolean mPendingAlwaysConsumeSystemBars;
    final android.graphics.Rect mPendingBackDropFrame;
    private boolean mPendingDragResizing;
    int mPendingInputEventCount;
    android.view.ViewRootImpl.QueuedInputEvent mPendingInputEventHead;
    java.lang.String mPendingInputEventQueueLengthCounterName;
    android.view.ViewRootImpl.QueuedInputEvent mPendingInputEventTail;
    private final android.util.MergedConfiguration mPendingMergedConfiguration;
    private android.view.SurfaceControl.Transaction mPendingTransaction;
    private java.util.ArrayList<android.animation.LayoutTransition> mPendingTransitions;
    boolean mPerformContentCapture;
    boolean mPointerCapture;
    private android.view.MotionEvent mPointerIconEvent;
    private java.lang.Integer mPointerIconType;
    private float mPreferredFrameRate;
    private int mPreferredFrameRateCategory;
    private long mPreviousFrameDrawnTime;
    private android.window.SurfaceSyncGroup mPreviousSyncSafeguard;
    private final java.lang.Object mPreviousSyncSafeguardLock;
    android.graphics.Region mPreviousTouchableRegion;
    private int mPreviousTransformHint;
    final android.graphics.Region mPreviousTransparentRegion;
    boolean mProcessInputEventsScheduled;
    private boolean mProcessingBackKey;
    private boolean mProfile;
    private boolean mProfileRendering;
    private android.view.ViewRootImpl.QueuedInputEvent mQueuedInputEventPool;
    private int mQueuedInputEventPoolSize;
    private android.os.Bundle mRelayoutBundle;
    private boolean mRelayoutRequested;
    private int mRelayoutSeq;
    private boolean mRemoved;
    private android.view.Choreographer.FrameCallback mRenderProfiler;
    private boolean mRenderProfilingEnabled;
    boolean mReportNextDraw;
    private android.view.PointerIcon mResolvedPointerIcon;
    private java.util.HashSet<android.view.ScrollCaptureCallback> mRootScrollCaptureCallbacks;
    android.graphics.Paint mRoundDisplayAccessibilityHighlightPaint;
    private long mScrollCaptureRequestTimeout;
    boolean mScrollMayChange;
    int mScrollY;
    android.widget.Scroller mScroller;
    android.view.ViewRootImpl.SendWindowContentChangedAccessibilityEvent mSendWindowContentChangedAccessibilityEvent;
    private final android.view.ISensitiveContentProtectionManager mSensitiveContentProtectionService;
    private final java.util.concurrent.Executor mSimpleExecutor;
    int mSoftInputMode;
    android.view.View mStartedDragViewForA11y;
    boolean mStopped;
    public final android.view.Surface mSurface;
    private final java.util.ArrayList<android.view.ViewRootImpl.SurfaceChangedCallback> mSurfaceChangedCallbacks;
    private final android.view.SurfaceControl mSurfaceControl;
    com.android.internal.view.BaseSurfaceHolder mSurfaceHolder;
    android.view.SurfaceHolder.Callback2 mSurfaceHolderCallback;
    private int mSurfaceSequenceId;
    private final android.view.SurfaceSession mSurfaceSession;
    private final android.graphics.Point mSurfaceSize;
    private boolean mSyncBuffer;
    int mSyncSeqId;
    android.view.ViewRootImpl.InputStage mSyntheticInputStage;
    private java.lang.String mTag;
    final int mTargetSdkVersion;
    private final android.view.InsetsSourceControl.Array mTempControls;
    java.util.HashSet<android.view.View> mTempHashSet;
    private final android.view.InsetsState mTempInsets;
    private final android.graphics.Rect mTempRect;
    private final android.app.WindowConfiguration mTempWinConfig;
    final java.lang.Thread mThread;
    private final android.window.ClientWindowFrames mTmpFrames;
    final int[] mTmpLocation;
    final android.util.TypedValue mTmpValue;
    android.graphics.Region mTouchableRegion;
    private final android.view.SurfaceControl.Transaction mTransaction;
    private java.util.ArrayList<android.view.AttachedSurfaceControl.OnBufferTransformHintChangedListener> mTransformHintListeners;
    android.content.res.CompatibilityInfo.Translator mTranslator;
    final android.graphics.Region mTransparentRegion;
    int mTraversalBarrier;
    final android.view.ViewRootImpl.TraversalRunnable mTraversalRunnable;
    public boolean mTraversalScheduled;
    private int mTypesHiddenByFlags;
    boolean mUnbufferedInputDispatch;
    int mUnbufferedInputSource;
    private final android.view.ViewRootImpl.UnhandledKeyManager mUnhandledKeyManager;
    private final android.view.ViewRootRectTracker mUnrestrictedKeepClearRectsTracker;
    boolean mUpcomingInTouchMode;
    boolean mUpcomingWindowFocus;
    private boolean mUpdateSurfaceNeeded;
    private boolean mUseMTRenderer;
    android.view.View mView;
    private final boolean mViewBoundsSandboxingEnabled;
    final android.view.ViewConfiguration mViewConfiguration;
    protected final android.view.ViewFrameInfo mViewFrameInfo;
    private int mViewLayoutDirectionInitial;
    private boolean mViewMeasureDeferred;
    int mViewVisibility;
    private final android.graphics.Rect mVisRect;
    private boolean mWasLastDrawCanceled;
    int mWidth;
    boolean mWillDrawSoon;
    final android.graphics.Rect mWinFrame;
    private final android.graphics.Rect mWinFrameInScreen;
    final android.view.ViewRootImpl.W mWindow;
    public final android.view.WindowManager.LayoutParams mWindowAttributes;
    boolean mWindowAttributesChanged;
    final java.util.ArrayList<android.view.WindowCallbacks> mWindowCallbacks;
    java.util.concurrent.CountDownLatch mWindowDrawCountDown;
    boolean mWindowFocusChanged;
    private final android.view.WindowLayout mWindowLayout;
    final android.view.IWindowSession mWindowSession;
    private java.util.function.Predicate<android.view.KeyEvent> mWindowlessBackKeyCallback;
    private android.window.SurfaceSyncGroup mWmsRequestSyncGroup;
    int mWmsRequestSyncGroupState;
    public static final boolean CAPTION_ON_SHELL = android.os.SystemProperties.getBoolean("persist.wm.debug.caption_on_shell", true);
    public static final boolean CLIENT_TRANSIENT = android.os.SystemProperties.getBoolean("persist.wm.debug.client_transient", false);
    public static final boolean CLIENT_IMMERSIVE_CONFIRMATION = android.os.SystemProperties.getBoolean("persist.wm.debug.client_immersive_confirmation", false);
    static final java.lang.ThreadLocal<android.view.HandlerActionQueue> sRunQueues = new java.lang.ThreadLocal<>();
    static final java.util.ArrayList<java.lang.Runnable> sFirstDrawHandlers = new java.util.ArrayList<>();
    static boolean sFirstDrawComplete = false;
    private static final java.util.ArrayList<android.view.ViewRootImpl.ConfigChangedCallback> sConfigCallbacks = new java.util.ArrayList<>();
    private static boolean sCompatibilityDone = false;
    static final android.view.animation.Interpolator mResizeInterpolator = new android.view.animation.AccelerateDecelerateInterpolator();
    private static final java.lang.Object sSyncProgressLock = new java.lang.Object();
    private static int sNumSyncsInProgress = 0;
    private static volatile boolean sAnrReported = false;
    static android.graphics.BLASTBufferQueue.TransactionHangCallback sTransactionHangCallback = new android.graphics.BLASTBufferQueue.TransactionHangCallback() { // from class: android.view.ViewRootImpl.1
        @Override // android.graphics.BLASTBufferQueue.TransactionHangCallback
        public void onTransactionHang(java.lang.String str) {
            if (android.view.ViewRootImpl.sAnrReported) {
                return;
            }
            android.view.ViewRootImpl.sAnrReported = true;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.app.ActivityManager.getService().appNotResponding(str);
            } catch (android.os.RemoteException e) {
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    };
    private static boolean sToolkitSetFrameRateReadOnlyFlagValue = android.view.flags.Flags.toolkitSetFrameRateReadOnly();
    private static boolean sToolkitMetricsForFrameRateDecisionFlagValue = android.view.flags.Flags.toolkitMetricsForFrameRateDecision();
    private static boolean sToolkitFrameRateTypingReadOnlyFlagValue = android.view.flags.Flags.toolkitFrameRateTypingReadOnly();

    public interface ActivityConfigCallback {
        void onConfigurationChanged(android.content.res.Configuration configuration, int i);

        void requestCompatCameraControl(boolean z, boolean z2, android.app.ICompatCameraControlCallback iCompatCameraControlCallback);
    }

    public interface ConfigChangedCallback {
        void onConfigurationChanged(android.content.res.Configuration configuration);
    }

    protected android.graphics.FrameInfo getUpdatedFrameInfo() {
        android.graphics.FrameInfo frameInfo = this.mChoreographer.mFrameInfo;
        this.mViewFrameInfo.populateFrameInfo(frameInfo);
        this.mViewFrameInfo.reset();
        this.mInputEventAssigner.notifyFrameProcessed();
        return frameInfo;
    }

    public android.view.ImeFocusController getImeFocusController() {
        return this.mImeFocusController;
    }

    static final class SystemUiVisibilityInfo {
        int globalVisibility;
        int localChanges;
        int localValue;

        SystemUiVisibilityInfo() {
        }
    }

    public android.view.HandwritingInitiator getHandwritingInitiator() {
        return this.mHandwritingInitiator;
    }

    public ViewRootImpl(android.content.Context context, android.view.Display display) {
        this(context, display, android.view.WindowManagerGlobal.getWindowSession(), new android.view.WindowLayout());
    }

    public ViewRootImpl(android.content.Context context, android.view.Display display, android.view.IWindowSession iWindowSession, android.view.WindowLayout windowLayout) {
        boolean z;
        this.mTransformHintListeners = new java.util.ArrayList<>();
        this.mPreviousTransformHint = 0;
        this.mForceInvertEnabled = Integer.MIN_VALUE;
        this.mFastScrollSoundEffectsEnabled = false;
        this.mWindowCallbacks = new java.util.ArrayList<>();
        this.mTmpLocation = new int[2];
        this.mTmpValue = new android.util.TypedValue();
        this.mWindowAttributes = new android.view.WindowManager.LayoutParams();
        this.mAppVisible = true;
        this.mForceDecorViewVisibility = false;
        this.mOrigWindowType = -1;
        this.mStopped = false;
        this.mIsAmbientMode = false;
        this.mPausedForTransition = false;
        this.mViewFrameInfo = new android.view.ViewFrameInfo();
        this.mInputEventAssigner = new android.view.InputEventAssigner();
        this.mDisplayDecorationCached = false;
        this.mSurfaceSize = new android.graphics.Point();
        this.mLastSurfaceSize = new android.graphics.Point();
        this.mVisRect = new android.graphics.Rect();
        this.mTempRect = new android.graphics.Rect();
        this.mContentCaptureEnabled = 0;
        this.mSyncBuffer = false;
        this.mCheckIfCanDraw = false;
        this.mLastTraversalWasVisible = true;
        this.mDrewOnceForSync = false;
        this.mSyncSeqId = 0;
        this.mLastSyncSeqId = 0;
        this.mPendingTransaction = new android.view.SurfaceControl.Transaction();
        this.mUnbufferedInputSource = 0;
        this.mPendingInputEventQueueLengthCounterName = "pq";
        this.mUnhandledKeyManager = new android.view.ViewRootImpl.UnhandledKeyManager();
        this.mWindowAttributesChanged = false;
        this.mSurface = new android.view.Surface();
        this.mSurfaceControl = new android.view.SurfaceControl();
        this.mHdrRenderState = new android.view.HdrRenderState(this);
        this.mSurfaceSession = new android.view.SurfaceSession();
        this.mTransaction = new android.view.SurfaceControl.Transaction();
        this.mFrameRateTransaction = new android.view.SurfaceControl.Transaction();
        this.mTmpFrames = new android.window.ClientWindowFrames();
        this.mPendingBackDropFrame = new android.graphics.Rect();
        this.mWinFrameInScreen = new android.graphics.Rect();
        this.mTempInsets = new android.view.InsetsState();
        this.mTempControls = new android.view.InsetsSourceControl.Array();
        this.mTempWinConfig = new android.app.WindowConfiguration();
        this.mInvCompatScale = 1.0f;
        this.mLastGivenInsets = new android.view.ViewTreeObserver.InternalInsetsInfo();
        this.mTypesHiddenByFlags = 0;
        this.mLastConfigurationFromResources = new android.content.res.Configuration();
        this.mLastReportedMergedConfiguration = new android.util.MergedConfiguration();
        this.mPendingMergedConfiguration = new android.util.MergedConfiguration();
        this.mDragPoint = new android.graphics.PointF();
        this.mLastTouchPoint = new android.graphics.PointF();
        this.mLastTouchDeviceId = -1;
        this.mFpsStartTime = -1L;
        this.mFpsPrevTime = -1L;
        this.mPreviousFrameDrawnTime = -1L;
        this.mLargestChildPercentage = 0.0f;
        this.mPointerIconType = null;
        this.mCustomPointerIcon = null;
        this.mResolvedPointerIcon = null;
        this.mAccessibilityInteractionConnectionManager = new android.view.ViewRootImpl.AccessibilityInteractionConnectionManager();
        this.mInLayout = false;
        this.mLayoutRequesters = new java.util.ArrayList<>();
        this.mHandlingLayoutInLayoutRequest = false;
        this.mInputEventConsistencyVerifier = android.view.InputEventConsistencyVerifier.isInstrumentationEnabled() ? new android.view.InputEventConsistencyVerifier(this, 0) : null;
        this.mBlurRegionAggregator = new com.android.internal.graphics.drawable.BackgroundBlurDrawable.Aggregator(this);
        this.mGestureExclusionTracker = new android.view.ViewRootRectTracker(new java.util.function.Function() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.util.List systemGestureExclusionRects;
                systemGestureExclusionRects = ((android.view.View) obj).getSystemGestureExclusionRects();
                return systemGestureExclusionRects;
            }
        });
        this.mKeepClearRectsTracker = new android.view.ViewRootRectTracker(new java.util.function.Function() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda15
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.util.List collectPreferKeepClearRects;
                collectPreferKeepClearRects = ((android.view.View) obj).collectPreferKeepClearRects();
                return collectPreferKeepClearRects;
            }
        });
        this.mUnrestrictedKeepClearRectsTracker = new android.view.ViewRootRectTracker(new java.util.function.Function() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda16
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.util.List collectUnrestrictedPreferKeepClearRects;
                collectUnrestrictedPreferKeepClearRects = ((android.view.View) obj).collectUnrestrictedPreferKeepClearRects();
                return collectUnrestrictedPreferKeepClearRects;
            }
        });
        this.mPreviousSyncSafeguardLock = new java.lang.Object();
        this.mNumPausedForSync = 0;
        this.mScrollCaptureRequestTimeout = 2500L;
        this.mSurfaceSequenceId = 0;
        this.mPreferredFrameRateCategory = 1;
        this.mLastPreferredFrameRateCategory = 1;
        this.mPreferredFrameRate = 0.0f;
        this.mLastPreferredFrameRate = 0.0f;
        this.mHasInvalidation = false;
        this.mIsFrameRateBoosting = false;
        this.mIsTouchBoosting = false;
        this.mHasIdledMessage = false;
        this.mIsFrameRatePowerSavingsBalanced = true;
        this.mIsFrameRateConflicted = false;
        this.mFrameRateCompatibility = 1;
        this.mFrameRateCategoryHighCount = 0;
        this.mFrameRateCategoryHighHintCount = 0;
        this.mFrameRateCategoryNormalCount = 0;
        this.mFrameRateCategoryLowCount = 0;
        this.mRelayoutBundle = new android.os.Bundle();
        this.mChildBoundingInsets = new android.graphics.Rect();
        this.mChildBoundingInsetsChanged = false;
        this.mTag = TAG;
        this.mPointerIconEvent = null;
        this.mProfile = false;
        this.mDisplayListener = new android.hardware.display.DisplayManager.DisplayListener() { // from class: android.view.ViewRootImpl.4
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
                if (android.view.ViewRootImpl.this.mExtraDisplayListenerLogging) {
                    android.util.Slog.i(android.view.ViewRootImpl.this.mTag, "Received onDisplayChanged - " + android.view.ViewRootImpl.this.mView);
                }
                if (android.view.ViewRootImpl.this.mView != null && android.view.ViewRootImpl.this.mDisplay.getDisplayId() == i) {
                    int i2 = android.view.ViewRootImpl.this.mAttachInfo.mDisplayState;
                    int state = android.view.ViewRootImpl.this.mDisplay.getState();
                    if (android.view.ViewRootImpl.this.mExtraDisplayListenerLogging) {
                        android.util.Slog.i(android.view.ViewRootImpl.this.mTag, "DisplayState - old: " + i2 + ", new: " + state);
                    }
                    if (android.os.Trace.isTagEnabled(32L)) {
                        android.os.Trace.traceCounter(32L, "vri#screenState[" + android.view.ViewRootImpl.this.mTag + "] state=", state);
                    }
                    if (i2 != state) {
                        android.view.ViewRootImpl.this.mAttachInfo.mDisplayState = state;
                        android.view.ViewRootImpl.this.pokeDrawLockIfNeeded();
                        if (i2 != 0) {
                            int viewScreenState = toViewScreenState(i2);
                            int viewScreenState2 = toViewScreenState(state);
                            if (viewScreenState != viewScreenState2) {
                                android.view.ViewRootImpl.this.mView.dispatchScreenStateChanged(viewScreenState2);
                            }
                            if (i2 == 1) {
                                android.view.ViewRootImpl.this.mFullRedrawNeeded = true;
                                android.view.ViewRootImpl.this.scheduleTraversals();
                            }
                        }
                    }
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
            }

            private int toViewScreenState(int i) {
                if (i != 1) {
                    return 1;
                }
                return 0;
            }
        };
        this.mSurfaceChangedCallbacks = new java.util.ArrayList<>();
        this.mHandler = new android.view.ViewRootImpl.ViewRootHandler();
        this.mExecutor = new java.util.concurrent.Executor() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda17
            @Override // java.util.concurrent.Executor
            public final void execute(java.lang.Runnable runnable) {
                android.view.ViewRootImpl.this.lambda$new$8(runnable);
            }
        };
        this.mTraversalRunnable = new android.view.ViewRootImpl.TraversalRunnable();
        this.mConsumedBatchedInputRunnable = new android.view.ViewRootImpl.ConsumeBatchedInputRunnable();
        this.mConsumeBatchedInputImmediatelyRunnable = new android.view.ViewRootImpl.ConsumeBatchedInputImmediatelyRunnable();
        this.mInvalidateOnAnimationRunnable = new android.view.ViewRootImpl.InvalidateOnAnimationRunnable();
        this.mSimpleExecutor = new android.app.PendingIntent$$ExternalSyntheticLambda0();
        this.mContext = context;
        this.mWindowSession = iWindowSession;
        this.mWindowLayout = windowLayout;
        this.mDisplay = display;
        this.mBasePackageName = context.getBasePackageName();
        java.lang.String orElse = android.sysprop.DisplayProperties.debug_vri_package().orElse(null);
        if (android.text.TextUtils.isEmpty(orElse) || !orElse.equals(this.mBasePackageName)) {
            z = false;
        } else {
            z = true;
        }
        this.mExtraDisplayListenerLogging = z;
        this.mThread = java.lang.Thread.currentThread();
        this.mLocation = new android.view.WindowLeaked(null);
        this.mLocation.fillInStackTrace();
        this.mWidth = -1;
        this.mHeight = -1;
        this.mDirty = new android.graphics.Rect();
        this.mWinFrame = new android.graphics.Rect();
        this.mLastLayoutFrame = new android.graphics.Rect();
        this.mWindow = new android.view.ViewRootImpl.W(this);
        this.mLeashToken = new android.os.Binder();
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        this.mViewVisibility = 8;
        this.mTransparentRegion = new android.graphics.Region();
        this.mPreviousTransparentRegion = new android.graphics.Region();
        this.mFirst = true;
        this.mPerformContentCapture = true;
        this.mAdded = false;
        this.mAttachInfo = new android.view.View.AttachInfo(this.mWindowSession, this.mWindow, display, this, this.mHandler, this, context);
        this.mCompatibleVisibilityInfo = new android.view.ViewRootImpl.SystemUiVisibilityInfo();
        this.mAccessibilityManager = android.view.accessibility.AccessibilityManager.getInstance(context);
        this.mHighContrastTextManager = new android.view.ViewRootImpl.HighContrastTextManager();
        this.mViewConfiguration = android.view.ViewConfiguration.get(context);
        this.mDensity = context.getResources().getDisplayMetrics().densityDpi;
        this.mNoncompatDensity = context.getResources().getDisplayMetrics().noncompatDensityDpi;
        this.mFallbackEventHandler = new com.android.internal.policy.PhoneFallbackEventHandler(context);
        this.mChoreographer = android.view.Choreographer.getInstance();
        this.mInsetsController = new android.view.InsetsController(new android.view.ViewRootInsetsControllerHost(this));
        this.mHandwritingInitiator = new android.view.HandwritingInitiator(this.mViewConfiguration, (android.view.inputmethod.InputMethodManager) this.mContext.getSystemService(android.view.inputmethod.InputMethodManager.class));
        this.mViewBoundsSandboxingEnabled = getViewBoundsSandboxingEnabled();
        this.mIsStylusPointerIconEnabled = android.hardware.input.InputSettings.isStylusPointerIconEnabled(this.mContext);
        java.lang.String string = context.getResources().getString(com.android.internal.R.string.config_inputEventCompatProcessorOverrideClassName);
        if (string.isEmpty()) {
            this.mInputCompatProcessor = new android.view.InputEventCompatProcessor(context);
        } else {
            try {
                try {
                    this.mInputCompatProcessor = (android.view.InputEventCompatProcessor) java.lang.Class.forName(string).getConstructor(android.content.Context.class).newInstance(context);
                } catch (java.lang.Exception e) {
                    android.util.Log.e(TAG, "Unable to create the InputEventCompatProcessor. ", e);
                    this.mInputCompatProcessor = null;
                }
            } catch (java.lang.Throwable th) {
                this.mInputCompatProcessor = null;
                throw th;
            }
        }
        if (!sCompatibilityDone) {
            sAlwaysAssignFocus = this.mTargetSdkVersion < 28;
            sCompatibilityDone = true;
        }
        loadSystemProperties();
        this.mImeFocusController = new android.view.ImeFocusController(this);
        this.mScrollCaptureRequestTimeout = 2500L;
        this.mOnBackInvokedDispatcher = new android.window.WindowOnBackInvokedDispatcher(context);
        if (android.view.flags.Flags.sensitiveContentAppProtection()) {
            this.mSensitiveContentProtectionService = android.view.ISensitiveContentProtectionManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.SENSITIVE_CONTENT_PROTECTION_SERVICE));
            if (this.mSensitiveContentProtectionService == null) {
                android.util.Log.e(TAG, "SensitiveContentProtectionService shouldn't be null");
                return;
            }
            return;
        }
        this.mSensitiveContentProtectionService = null;
    }

    public static void addFirstDrawHandler(java.lang.Runnable runnable) {
        synchronized (sFirstDrawHandlers) {
            if (!sFirstDrawComplete) {
                sFirstDrawHandlers.add(runnable);
            }
        }
    }

    public static void addConfigCallback(android.view.ViewRootImpl.ConfigChangedCallback configChangedCallback) {
        synchronized (sConfigCallbacks) {
            sConfigCallbacks.add(configChangedCallback);
        }
    }

    public static void removeConfigCallback(android.view.ViewRootImpl.ConfigChangedCallback configChangedCallback) {
        synchronized (sConfigCallbacks) {
            sConfigCallbacks.remove(configChangedCallback);
        }
    }

    public void setActivityConfigCallback(android.view.ViewRootImpl.ActivityConfigCallback activityConfigCallback) {
        this.mActivityConfigCallback = activityConfigCallback;
    }

    public void setOnContentApplyWindowInsetsListener(android.view.Window.OnContentApplyWindowInsetsListener onContentApplyWindowInsetsListener) {
        this.mAttachInfo.mContentOnApplyWindowInsetsListener = onContentApplyWindowInsetsListener;
        if (!this.mFirst) {
            requestFitSystemWindows();
        }
    }

    public void addWindowCallbacks(android.view.WindowCallbacks windowCallbacks) {
        this.mWindowCallbacks.add(windowCallbacks);
    }

    public void removeWindowCallbacks(android.view.WindowCallbacks windowCallbacks) {
        this.mWindowCallbacks.remove(windowCallbacks);
    }

    public void reportDrawFinish() {
        if (this.mWindowDrawCountDown != null) {
            this.mWindowDrawCountDown.countDown();
        }
    }

    public void profile() {
        this.mProfile = true;
    }

    private boolean isInTouchMode() {
        if (this.mAttachInfo == null) {
            return this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_defaultInTouchMode);
        }
        return this.mAttachInfo.mInTouchMode;
    }

    public void notifyChildRebuilt() {
        if (this.mView instanceof com.android.internal.view.RootViewSurfaceTaker) {
            if (this.mSurfaceHolderCallback != null) {
                this.mSurfaceHolder.removeCallback(this.mSurfaceHolderCallback);
            }
            this.mSurfaceHolderCallback = ((com.android.internal.view.RootViewSurfaceTaker) this.mView).willYouTakeTheSurface();
            if (this.mSurfaceHolderCallback != null) {
                this.mSurfaceHolder = new android.view.ViewRootImpl.TakenSurfaceHolder();
                this.mSurfaceHolder.setFormat(0);
                this.mSurfaceHolder.addCallback(this.mSurfaceHolderCallback);
            } else {
                this.mSurfaceHolder = null;
            }
            this.mInputQueueCallback = ((com.android.internal.view.RootViewSurfaceTaker) this.mView).willYouTakeTheInputQueue();
            if (this.mInputQueueCallback != null) {
                this.mInputQueueCallback.onInputQueueCreated(this.mInputQueue);
            }
        }
        updateLastConfigurationFromResources(getConfiguration());
        reportNextDraw("rebuilt");
        if (this.mStopped) {
            setWindowStopped(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.res.Configuration getConfiguration() {
        return this.mContext.getResources().getConfiguration();
    }

    private android.app.WindowConfiguration getCompatWindowConfiguration() {
        android.app.WindowConfiguration windowConfiguration = getConfiguration().windowConfiguration;
        if (this.mInvCompatScale == 1.0f) {
            return windowConfiguration;
        }
        this.mTempWinConfig.setTo(windowConfiguration);
        this.mTempWinConfig.scale(this.mInvCompatScale);
        return this.mTempWinConfig;
    }

    public void setView(android.view.View view, android.view.WindowManager.LayoutParams layoutParams, android.view.View view2) {
        setView(view, layoutParams, view2, android.os.UserHandle.myUserId());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setView(android.view.View view, android.view.WindowManager.LayoutParams layoutParams, android.view.View view2, int i) {
        boolean z;
        android.view.InputChannel inputChannel;
        android.view.PendingInsetsController providePendingInsetsController;
        synchronized (this) {
            if (this.mView == null) {
                this.mView = view;
                this.mViewLayoutDirectionInitial = this.mView.getRawLayoutDirection();
                this.mFallbackEventHandler.setView(view);
                this.mWindowAttributes.copyFrom(layoutParams);
                if (this.mWindowAttributes.packageName == null) {
                    this.mWindowAttributes.packageName = this.mBasePackageName;
                }
                android.view.WindowManager.LayoutParams layoutParams2 = this.mWindowAttributes;
                setTag();
                this.mFpsTraceName = "FPS of " + ((java.lang.Object) getTitle());
                this.mLargestViewTraceName = "Largest view percentage(per hundred) of " + ((java.lang.Object) getTitle());
                this.mClientWindowLayoutFlags = layoutParams2.flags;
                setAccessibilityFocus(null, null);
                if (view instanceof com.android.internal.view.RootViewSurfaceTaker) {
                    this.mSurfaceHolderCallback = ((com.android.internal.view.RootViewSurfaceTaker) view).willYouTakeTheSurface();
                    if (this.mSurfaceHolderCallback != null) {
                        this.mSurfaceHolder = new android.view.ViewRootImpl.TakenSurfaceHolder();
                        this.mSurfaceHolder.setFormat(0);
                        this.mSurfaceHolder.addCallback(this.mSurfaceHolderCallback);
                    }
                }
                if (!layoutParams2.hasManualSurfaceInsets) {
                    layoutParams2.setSurfaceInsets(view, false, true);
                }
                this.mTranslator = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo().getTranslator();
                if (this.mSurfaceHolder == null) {
                    enableHardwareAcceleration(layoutParams2);
                    boolean z2 = this.mAttachInfo.mThreadedRenderer != null;
                    if (this.mUseMTRenderer != z2) {
                        endDragResizing();
                        this.mUseMTRenderer = z2;
                    }
                }
                if (this.mTranslator != null) {
                    this.mSurface.setCompatibilityTranslator(this.mTranslator);
                    layoutParams2.backup();
                    this.mTranslator.translateWindowLayout(layoutParams2);
                    z = true;
                } else {
                    z = false;
                }
                this.mSoftInputMode = layoutParams2.softInputMode;
                this.mWindowAttributesChanged = true;
                this.mAttachInfo.mRootView = view;
                this.mAttachInfo.mScalingRequired = this.mTranslator != null;
                this.mAttachInfo.mApplicationScale = this.mTranslator == null ? 1.0f : this.mTranslator.applicationScale;
                if (view2 != null) {
                    this.mAttachInfo.mPanelParentWindowToken = view2.getApplicationWindowToken();
                }
                this.mAdded = true;
                requestLayout();
                if ((this.mWindowAttributes.inputFeatures & 1) != 0) {
                    inputChannel = null;
                } else {
                    inputChannel = new android.view.InputChannel();
                }
                this.mForceDecorViewVisibility = (this.mWindowAttributes.privateFlags & 8192) != 0;
                if ((this.mView instanceof com.android.internal.view.RootViewSurfaceTaker) && (providePendingInsetsController = ((com.android.internal.view.RootViewSurfaceTaker) this.mView).providePendingInsetsController()) != null) {
                    providePendingInsetsController.replayAndAttach(this.mInsetsController);
                }
                try {
                    try {
                        this.mOrigWindowType = this.mWindowAttributes.type;
                        this.mAttachInfo.mRecomputeGlobalAttributes = true;
                        collectViewAttributes();
                        adjustLayoutParamsForCompatibility(this.mWindowAttributes);
                        controlInsetsForCompatibility(this.mWindowAttributes);
                        android.graphics.Rect rect = new android.graphics.Rect();
                        float[] fArr = {1.0f};
                        int addToDisplayAsUser = this.mWindowSession.addToDisplayAsUser(this.mWindow, this.mWindowAttributes, getHostVisibility(), this.mDisplay.getDisplayId(), i, this.mInsetsController.getRequestedVisibleTypes(), inputChannel, this.mTempInsets, this.mTempControls, rect, fArr);
                        if (!rect.isValid()) {
                            rect = null;
                        }
                        if (this.mTranslator != null) {
                            this.mTranslator.translateInsetsStateInScreenToAppWindow(this.mTempInsets);
                            this.mTranslator.translateSourceControlsInScreenToAppWindow(this.mTempControls.get());
                            this.mTranslator.translateRectInScreenToAppWindow(rect);
                        }
                        this.mTmpFrames.attachedFrame = rect;
                        this.mTmpFrames.compatScale = fArr[0];
                        this.mInvCompatScale = 1.0f / fArr[0];
                        if (z) {
                            layoutParams2.restore();
                        }
                        this.mAttachInfo.mAlwaysConsumeSystemBars = (addToDisplayAsUser & 4) != 0;
                        this.mPendingAlwaysConsumeSystemBars = this.mAttachInfo.mAlwaysConsumeSystemBars;
                        this.mInsetsController.onStateChanged(this.mTempInsets);
                        this.mInsetsController.onControlsChanged(this.mTempControls.get());
                        android.view.InsetsState state = this.mInsetsController.getState();
                        android.graphics.Rect rect2 = this.mTempRect;
                        state.getDisplayCutoutSafe(rect2);
                        android.app.WindowConfiguration compatWindowConfiguration = getCompatWindowConfiguration();
                        this.mWindowLayout.computeFrames(this.mWindowAttributes, state, rect2, compatWindowConfiguration.getBounds(), compatWindowConfiguration.getWindowingMode(), -1, -1, this.mInsetsController.getRequestedVisibleTypes(), 1.0f, this.mTmpFrames);
                        setFrame(this.mTmpFrames.frame, true);
                        registerBackCallbackOnWindow();
                        if (addToDisplayAsUser < 0) {
                            this.mAttachInfo.mRootView = null;
                            this.mAdded = false;
                            this.mFallbackEventHandler.setView(null);
                            unscheduleTraversals();
                            setAccessibilityFocus(null, null);
                            switch (addToDisplayAsUser) {
                                case -11:
                                    throw new android.view.WindowManager.BadTokenException("Unable to add Window " + this.mWindow + " -- requested userId is not valid");
                                case -10:
                                    throw new android.view.WindowManager.InvalidDisplayException("Unable to add window " + this.mWindow + " -- the specified window type " + this.mWindowAttributes.type + " is not valid");
                                case -9:
                                    throw new android.view.WindowManager.InvalidDisplayException("Unable to add window " + this.mWindow + " -- the specified display can not be found");
                                case -8:
                                    throw new android.view.WindowManager.BadTokenException("Unable to add window " + this.mWindow + " -- permission denied for window type " + this.mWindowAttributes.type);
                                case -7:
                                    throw new android.view.WindowManager.BadTokenException("Unable to add window " + this.mWindow + " -- another window of type " + this.mWindowAttributes.type + " already exists");
                                case -6:
                                    return;
                                case -5:
                                    throw new android.view.WindowManager.BadTokenException("Unable to add window -- window " + this.mWindow + " has already been added");
                                case -4:
                                    throw new android.view.WindowManager.BadTokenException("Unable to add window -- app for token " + layoutParams2.token + " is exiting");
                                case -3:
                                    throw new android.view.WindowManager.BadTokenException("Unable to add window -- token " + layoutParams2.token + " is not for an application");
                                case -2:
                                case -1:
                                    throw new android.view.WindowManager.BadTokenException("Unable to add window -- token " + layoutParams2.token + " is not valid; is your activity running?");
                                default:
                                    throw new java.lang.RuntimeException("Unable to add window -- unknown error code " + addToDisplayAsUser);
                            }
                        }
                        registerListeners();
                        this.mAttachInfo.mDisplayState = this.mDisplay.getState();
                        if (this.mExtraDisplayListenerLogging) {
                            android.util.Slog.i(this.mTag, android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + this.mBasePackageName + ") Initial DisplayState: " + this.mAttachInfo.mDisplayState, new java.lang.Throwable());
                        }
                        if (view instanceof com.android.internal.view.RootViewSurfaceTaker) {
                            this.mInputQueueCallback = ((com.android.internal.view.RootViewSurfaceTaker) view).willYouTakeTheInputQueue();
                        }
                        if (inputChannel != null) {
                            if (this.mInputQueueCallback != null) {
                                this.mInputQueue = new android.view.InputQueue();
                                this.mInputQueueCallback.onInputQueueCreated(this.mInputQueue);
                            }
                            this.mInputEventReceiver = new android.view.ViewRootImpl.WindowInputEventReceiver(inputChannel, android.os.Looper.myLooper());
                            if (this.mAttachInfo.mThreadedRenderer != null) {
                                android.view.ViewRootImpl.InputMetricsListener inputMetricsListener = new android.view.ViewRootImpl.InputMetricsListener();
                                this.mHardwareRendererObserver = new android.graphics.HardwareRendererObserver(inputMetricsListener, inputMetricsListener.data, this.mHandler, true);
                                this.mAttachInfo.mThreadedRenderer.addObserver(this.mHardwareRendererObserver);
                            }
                            this.mUnbufferedInputSource = this.mView.mUnbufferedInputSource;
                        }
                        view.assignParent(this);
                        this.mAddedTouchMode = (addToDisplayAsUser & 1) != 0;
                        this.mAppVisible = (addToDisplayAsUser & 2) != 0;
                        if (this.mAccessibilityManager.isEnabled()) {
                            this.mAccessibilityInteractionConnectionManager.ensureConnection();
                            setAccessibilityWindowAttributesIfNeeded();
                        }
                        if (view.getImportantForAccessibility() == 0) {
                            view.setImportantForAccessibility(1);
                        }
                        java.lang.CharSequence title = layoutParams2.getTitle();
                        this.mSyntheticInputStage = new android.view.ViewRootImpl.SyntheticInputStage();
                        android.view.ViewRootImpl.EarlyPostImeInputStage earlyPostImeInputStage = new android.view.ViewRootImpl.EarlyPostImeInputStage(new android.view.ViewRootImpl.NativePostImeInputStage(new android.view.ViewRootImpl.ViewPostImeInputStage(this.mSyntheticInputStage), "aq:native-post-ime:" + ((java.lang.Object) title)));
                        this.mFirstInputStage = new android.view.ViewRootImpl.NativePreImeInputStage(new android.view.ViewRootImpl.ViewPreImeInputStage(new android.view.ViewRootImpl.ImeInputStage(earlyPostImeInputStage, "aq:ime:" + ((java.lang.Object) title))), "aq:native-pre-ime:" + ((java.lang.Object) title));
                        this.mFirstPostImeInputStage = earlyPostImeInputStage;
                        this.mPendingInputEventQueueLengthCounterName = "aq:pending:" + ((java.lang.Object) title);
                        if (!this.mRemoved || !this.mAppVisible) {
                            android.animation.AnimationHandler.requestAnimatorsEnabled(this.mAppVisible, this);
                        }
                    } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                        this.mAdded = false;
                        this.mView = null;
                        this.mAttachInfo.mRootView = null;
                        this.mFallbackEventHandler.setView(null);
                        unscheduleTraversals();
                        setAccessibilityFocus(null, null);
                        throw new java.lang.RuntimeException("Adding window failed", e);
                    }
                } finally {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAccessibilityWindowAttributesIfNeeded() {
        if (this.mAttachInfo.mAccessibilityWindowId != -1) {
            android.view.accessibility.AccessibilityWindowAttributes accessibilityWindowAttributes = new android.view.accessibility.AccessibilityWindowAttributes(this.mWindowAttributes, this.mContext.getResources().getConfiguration().getLocales());
            if (!accessibilityWindowAttributes.equals(this.mAccessibilityWindowAttributes)) {
                this.mAccessibilityWindowAttributes = accessibilityWindowAttributes;
                this.mAccessibilityManager.setAccessibilityWindowAttributes(getDisplayId(), this.mAttachInfo.mAccessibilityWindowId, accessibilityWindowAttributes);
            }
        }
    }

    private boolean isForceInvertEnabled() {
        if (this.mForceInvertEnabled == Integer.MIN_VALUE) {
            reloadForceInvertEnabled();
        }
        return this.mForceInvertEnabled == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadForceInvertEnabled() {
        if (android.view.accessibility.Flags.forceInvertColor()) {
            this.mForceInvertEnabled = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_FORCE_INVERT_COLOR_ENABLED, 0, android.os.UserHandle.myUserId());
        }
    }

    private void registerListeners() {
        if (this.mExtraDisplayListenerLogging) {
            android.util.Slog.i(this.mTag, "Register listeners: " + this.mBasePackageName);
        }
        this.mAccessibilityManager.addAccessibilityStateChangeListener(this.mAccessibilityInteractionConnectionManager, this.mHandler);
        this.mAccessibilityManager.addHighTextContrastStateChangeListener(this.mHighContrastTextManager, this.mHandler);
        android.hardware.display.DisplayManagerGlobal.getInstance().registerDisplayListener(this.mDisplayListener, this.mHandler, 7L, this.mBasePackageName);
        if (android.view.accessibility.Flags.forceInvertColor() && this.mForceInvertObserver == null) {
            this.mForceInvertObserver = new android.database.ContentObserver(this.mHandler) { // from class: android.view.ViewRootImpl.2
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    android.view.ViewRootImpl.this.reloadForceInvertEnabled();
                    android.view.ViewRootImpl.this.updateForceDarkMode();
                }
            };
            this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.ACCESSIBILITY_FORCE_INVERT_COLOR_ENABLED), false, this.mForceInvertObserver, android.os.UserHandle.myUserId());
        }
    }

    private void unregisterListeners() {
        this.mAccessibilityManager.removeAccessibilityStateChangeListener(this.mAccessibilityInteractionConnectionManager);
        this.mAccessibilityManager.removeHighTextContrastStateChangeListener(this.mHighContrastTextManager);
        android.hardware.display.DisplayManagerGlobal.getInstance().unregisterDisplayListener(this.mDisplayListener);
        if (android.view.accessibility.Flags.forceInvertColor() && this.mForceInvertObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mForceInvertObserver);
            this.mForceInvertObserver = null;
        }
        if (this.mExtraDisplayListenerLogging) {
            android.util.Slog.w(this.mTag, "Unregister listeners: " + this.mBasePackageName, new java.lang.Throwable());
        }
    }

    private void setTag() {
        java.lang.String[] split = this.mWindowAttributes.getTitle().toString().split("\\.");
        if (split.length > 0) {
            this.mTag = "VRI[" + split[split.length - 1] + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public int getWindowFlags() {
        return this.mWindowAttributes.flags;
    }

    public int getDisplayId() {
        return this.mDisplay.getDisplayId();
    }

    public java.lang.CharSequence getTitle() {
        return this.mWindowAttributes.getTitle();
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    void destroyHardwareResources() {
        android.view.ThreadedRenderer threadedRenderer = this.mAttachInfo.mThreadedRenderer;
        if (threadedRenderer != null) {
            if (android.os.Looper.myLooper() != this.mAttachInfo.mHandler.getLooper()) {
                this.mAttachInfo.mHandler.postAtFrontOfQueue(new java.lang.Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.ViewRootImpl.this.destroyHardwareResources();
                    }
                });
            } else {
                threadedRenderer.destroyHardwareResources(this.mView);
                threadedRenderer.destroy();
            }
        }
    }

    public void detachFunctor(long j) {
    }

    public static void invokeFunctor(long j, boolean z) {
    }

    public void registerAnimatingRenderNode(android.graphics.RenderNode renderNode) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerAnimatingRenderNode(renderNode);
            return;
        }
        if (this.mAttachInfo.mPendingAnimatingRenderNodes == null) {
            this.mAttachInfo.mPendingAnimatingRenderNodes = new java.util.ArrayList();
        }
        this.mAttachInfo.mPendingAnimatingRenderNodes.add(renderNode);
    }

    public void registerVectorDrawableAnimator(android.view.NativeVectorDrawableAnimator nativeVectorDrawableAnimator) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerVectorDrawableAnimator(nativeVectorDrawableAnimator);
        }
    }

    public void registerRtFrameCallback(final android.graphics.HardwareRenderer.FrameDrawingCallback frameDrawingCallback) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerRtFrameCallback(new android.graphics.HardwareRenderer.FrameDrawingCallback() { // from class: android.view.ViewRootImpl.3
                @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                public void onFrameDraw(long j) {
                }

                @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                public android.graphics.HardwareRenderer.FrameCommitCallback onFrameDraw(int i, long j) {
                    try {
                        return frameDrawingCallback.onFrameDraw(i, j);
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(android.view.ViewRootImpl.TAG, "Exception while executing onFrameDraw", e);
                        return null;
                    }
                }
            });
        }
    }

    private void enableHardwareAcceleration(android.view.WindowManager.LayoutParams layoutParams) {
        this.mAttachInfo.mHardwareAccelerated = false;
        this.mAttachInfo.mHardwareAccelerationRequested = false;
        if (this.mTranslator != null) {
            return;
        }
        if ((layoutParams.flags & 16777216) != 0) {
            boolean z = (layoutParams.privateFlags & 2) != 0;
            if (android.view.ThreadedRenderer.sRendererEnabled || z) {
                if (this.mAttachInfo.mThreadedRenderer != null) {
                    this.mAttachInfo.mThreadedRenderer.destroy();
                }
                android.graphics.Rect rect = layoutParams.surfaceInsets;
                android.view.ThreadedRenderer create = android.view.ThreadedRenderer.create(this.mContext, layoutParams.format != -1 || (rect.left != 0 || rect.right != 0 || rect.top != 0 || rect.bottom != 0), layoutParams.getTitle().toString());
                this.mAttachInfo.mThreadedRenderer = create;
                create.setSurfaceControl(this.mSurfaceControl, this.mBlastBufferQueue);
                updateColorModeIfNeeded(layoutParams.getColorMode(), layoutParams.getDesiredHdrHeadroom());
                this.mHdrRenderState.forceUpdateHdrSdrRatio();
                updateForceDarkMode();
                this.mAttachInfo.mHardwareAccelerated = true;
                this.mAttachInfo.mHardwareAccelerationRequested = true;
                if (this.mHardwareRendererObserver != null) {
                    create.addObserver(this.mHardwareRendererObserver);
                }
            }
        }
    }

    private int getNightMode() {
        return getConfiguration().uiMode & 48;
    }

    public int determineForceDarkType() {
        if (android.view.accessibility.Flags.forceInvertColor() && isForceInvertEnabled()) {
            return 2;
        }
        int i = getNightMode() == 32 ? 1 : 0;
        if (i != 0) {
            boolean z = android.os.SystemProperties.getBoolean(android.view.ThreadedRenderer.DEBUG_FORCE_DARK, false);
            android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(com.android.internal.R.styleable.Theme);
            int i2 = (obtainStyledAttributes.getBoolean(279, true) && obtainStyledAttributes.getBoolean(278, z)) ? 1 : 0;
            obtainStyledAttributes.recycle();
            return i2;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateForceDarkMode() {
        if (this.mAttachInfo.mThreadedRenderer != null && this.mAttachInfo.mThreadedRenderer.setForceDark(determineForceDarkType())) {
            invalidateWorld(this.mView);
        }
    }

    public android.view.View getView() {
        return this.mView;
    }

    final android.view.WindowLeaked getLocation() {
        return this.mLocation;
    }

    public void setLayoutParams(android.view.WindowManager.LayoutParams layoutParams, boolean z) {
        synchronized (this) {
            int i = this.mWindowAttributes.surfaceInsets.left;
            int i2 = this.mWindowAttributes.surfaceInsets.top;
            int i3 = this.mWindowAttributes.surfaceInsets.right;
            int i4 = this.mWindowAttributes.surfaceInsets.bottom;
            int i5 = this.mWindowAttributes.softInputMode;
            boolean z2 = this.mWindowAttributes.hasManualSurfaceInsets;
            this.mClientWindowLayoutFlags = layoutParams.flags;
            int i6 = this.mWindowAttributes.systemUiVisibility;
            int i7 = this.mWindowAttributes.subtreeSystemUiVisibility;
            int i8 = this.mWindowAttributes.insetsFlags.appearance;
            int i9 = this.mWindowAttributes.insetsFlags.behavior;
            int i10 = this.mWindowAttributes.privateFlags & android.media.audio.Enums.AUDIO_FORMAT_DTS_HD;
            int copyFrom = this.mWindowAttributes.copyFrom(layoutParams);
            if ((524288 & copyFrom) != 0) {
                this.mAttachInfo.mRecomputeGlobalAttributes = true;
            }
            if ((copyFrom & 1) != 0) {
                this.mAttachInfo.mNeedsUpdateLightCenter = true;
            }
            if ((copyFrom & 67108864) != 0) {
                invalidate();
            }
            if (this.mWindowAttributes.packageName == null) {
                this.mWindowAttributes.packageName = this.mBasePackageName;
            }
            this.mWindowAttributes.systemUiVisibility = i6;
            this.mWindowAttributes.subtreeSystemUiVisibility = i7;
            this.mWindowAttributes.insetsFlags.appearance = i8;
            this.mWindowAttributes.insetsFlags.behavior = i9;
            this.mWindowAttributes.privateFlags |= i10;
            if (this.mWindowAttributes.preservePreviousSurfaceInsets) {
                this.mWindowAttributes.surfaceInsets.set(i, i2, i3, i4);
                this.mWindowAttributes.hasManualSurfaceInsets = z2;
            } else if (this.mWindowAttributes.surfaceInsets.left != i || this.mWindowAttributes.surfaceInsets.top != i2 || this.mWindowAttributes.surfaceInsets.right != i3 || this.mWindowAttributes.surfaceInsets.bottom != i4) {
                this.mNeedsRendererSetup = true;
            }
            applyKeepScreenOnFlag(this.mWindowAttributes);
            if (z) {
                this.mSoftInputMode = layoutParams.softInputMode;
                requestLayout();
            }
            if ((layoutParams.softInputMode & 240) == 0) {
                this.mWindowAttributes.softInputMode = (this.mWindowAttributes.softInputMode & (-241)) | (i5 & 240);
            }
            if (this.mWindowAttributes.softInputMode != i5) {
                requestFitSystemWindows();
            }
            this.mWindowAttributesChanged = true;
            scheduleTraversals();
            setAccessibilityWindowAttributesIfNeeded();
        }
    }

    void handleAppVisibility(boolean z) {
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.instant(8L, android.text.TextUtils.formatSimple("%s visibilityChanged oldVisibility=%b newVisibility=%b", this.mTag, java.lang.Boolean.valueOf(this.mAppVisible), java.lang.Boolean.valueOf(z)));
        }
        if (this.mAppVisible != z) {
            boolean z2 = getHostVisibility() == 0;
            this.mAppVisible = z;
            boolean z3 = getHostVisibility() == 0;
            if (z2 != z3) {
                android.util.Log.d(this.mTag, "visibilityChanged oldVisibility=" + z2 + " newVisibility=" + z3);
                this.mAppVisibilityChanged = true;
                scheduleTraversals();
            }
            if (!this.mRemoved || !this.mAppVisible) {
                android.animation.AnimationHandler.requestAnimatorsEnabled(this.mAppVisible, this);
            }
        }
    }

    void handleGetNewSurface() {
        this.mNewSurfaceNeeded = true;
        this.mFullRedrawNeeded = true;
        scheduleTraversals();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleResized(android.window.ClientWindowFrames clientWindowFrames, boolean z, android.util.MergedConfiguration mergedConfiguration, android.view.InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4) {
        if (!this.mAdded) {
            return;
        }
        android.content.res.CompatibilityInfo.applyOverrideScaleIfNeeded(mergedConfiguration);
        android.graphics.Rect rect = clientWindowFrames.frame;
        android.graphics.Rect rect2 = clientWindowFrames.displayFrame;
        android.graphics.Rect rect3 = clientWindowFrames.attachedFrame;
        if (this.mTranslator != null) {
            this.mTranslator.translateInsetsStateInScreenToAppWindow(insetsState);
            this.mTranslator.translateRectInScreenToAppWindow(rect);
            this.mTranslator.translateRectInScreenToAppWindow(rect2);
            this.mTranslator.translateRectInScreenToAppWindow(rect3);
        }
        this.mInsetsController.onStateChanged(insetsState);
        float f = clientWindowFrames.compatScale;
        boolean z5 = !this.mWinFrame.equals(rect);
        boolean z6 = !this.mLastReportedMergedConfiguration.equals(mergedConfiguration);
        boolean z7 = !java.util.Objects.equals(this.mTmpFrames.attachedFrame, rect3);
        boolean z8 = this.mDisplay.getDisplayId() != i;
        boolean z9 = this.mTmpFrames.compatScale != f;
        boolean z10 = this.mPendingDragResizing != z4;
        if (!z && !z5 && !z6 && !z7 && !z8 && !z2 && !z9 && !z10) {
            return;
        }
        this.mPendingDragResizing = z4;
        this.mTmpFrames.compatScale = f;
        this.mInvCompatScale = 1.0f / f;
        if (z6) {
            performConfigurationChange(mergedConfiguration, false, z8 ? i : -1);
        } else if (z8) {
            onMovedToDisplay(i, this.mLastConfigurationFromResources);
        }
        setFrame(rect, false);
        this.mTmpFrames.displayFrame.set(rect2);
        if (this.mTmpFrames.attachedFrame != null && rect3 != null) {
            this.mTmpFrames.attachedFrame.set(rect3);
        }
        if (this.mDragResizing && this.mUseMTRenderer) {
            boolean equals = rect.equals(this.mPendingBackDropFrame);
            for (int size = this.mWindowCallbacks.size() - 1; size >= 0; size--) {
                this.mWindowCallbacks.get(size).onWindowSizeIsChanging(this.mPendingBackDropFrame, equals, this.mAttachInfo.mVisibleInsets, this.mAttachInfo.mStableInsets);
            }
        }
        this.mForceNextWindowRelayout |= z2;
        this.mPendingAlwaysConsumeSystemBars = z3;
        this.mSyncSeqId = i2 > this.mSyncSeqId ? i2 : this.mSyncSeqId;
        if (z) {
            reportNextDraw("resized");
        }
        if (this.mView != null && (z5 || z6)) {
            forceLayout(this.mView);
        }
        requestLayout();
    }

    public void onMovedToDisplay(int i, android.content.res.Configuration configuration) {
        if (this.mDisplay.getDisplayId() == i) {
            return;
        }
        updateInternalDisplay(i, this.mView.getResources());
        this.mImeFocusController.onMovedToDisplay();
        this.mAttachInfo.mDisplayState = this.mDisplay.getState();
        this.mView.dispatchMovedToDisplay(this.mDisplay, configuration);
    }

    private void updateInternalDisplay(int i, android.content.res.Resources resources) {
        android.view.Display adjustedDisplay = android.app.ResourcesManager.getInstance().getAdjustedDisplay(i, resources);
        this.mHdrRenderState.stopListening();
        if (adjustedDisplay == null) {
            android.util.Slog.w(TAG, "Cannot get desired display with Id: " + i);
            this.mDisplay = android.app.ResourcesManager.getInstance().getAdjustedDisplay(0, resources);
        } else {
            this.mDisplay = adjustedDisplay;
        }
        this.mHdrRenderState.startListening();
        this.mContext.updateDisplay(this.mDisplay.getDisplayId());
    }

    void pokeDrawLockIfNeeded() {
        if (android.view.Display.isDozeState(this.mAttachInfo.mDisplayState) && this.mWindowAttributes.type == 1 && this.mAdded && this.mTraversalScheduled && this.mAttachInfo.mHasWindowFocus) {
            try {
                this.mWindowSession.pokeDrawLock(this.mWindow);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @Override // android.view.ViewParent
    public void requestFitSystemWindows() {
        checkThread();
        this.mApplyInsetsRequested = true;
        scheduleTraversals();
    }

    void notifyInsetsChanged() {
        this.mApplyInsetsRequested = true;
        requestLayout();
        if (android.view.View.sForceLayoutWhenInsetsChanged && this.mView != null && (this.mWindowAttributes.softInputMode & 240) == 16) {
            forceLayout(this.mView);
        }
        if (!this.mIsInTraversal) {
            scheduleTraversals();
        }
    }

    public void notifyInsetsAnimationRunningStateChanged(boolean z) {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            this.mInsetsAnimationRunning = z;
        }
    }

    @Override // android.view.ViewParent
    public void requestLayout() {
        if (!this.mHandlingLayoutInLayoutRequest) {
            checkThread();
            this.mLayoutRequested = true;
            scheduleTraversals();
        }
    }

    @Override // android.view.ViewParent
    public boolean isLayoutRequested() {
        return this.mLayoutRequested;
    }

    @Override // android.view.ViewParent
    public void onDescendantInvalidated(android.view.View view, android.view.View view2) {
        if ((view2.mPrivateFlags & 64) != 0) {
            this.mIsAnimating = true;
        }
        invalidate();
    }

    void invalidate() {
        this.mDirty.set(0, 0, this.mWidth, this.mHeight);
        if (!this.mWillDrawSoon) {
            scheduleTraversals();
        }
    }

    void invalidateWorld(android.view.View view) {
        view.invalidate();
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                invalidateWorld(viewGroup.getChildAt(i));
            }
        }
    }

    @Override // android.view.ViewParent
    public void invalidateChild(android.view.View view, android.graphics.Rect rect) {
        invalidateChildInParent(null, rect);
    }

    @Override // android.view.ViewParent
    public android.view.ViewParent invalidateChildInParent(int[] iArr, android.graphics.Rect rect) {
        checkThread();
        if (rect == null) {
            invalidate();
            return null;
        }
        if (rect.isEmpty() && !this.mIsAnimating) {
            return null;
        }
        if (this.mCurScrollY != 0 || this.mTranslator != null) {
            this.mTempRect.set(rect);
            rect = this.mTempRect;
            if (this.mCurScrollY != 0) {
                rect.offset(0, -this.mCurScrollY);
            }
            if (this.mTranslator != null) {
                this.mTranslator.translateRectInAppWindowToScreen(rect);
            }
            if (this.mAttachInfo.mScalingRequired) {
                rect.inset(-1, -1);
            }
        }
        invalidateRectOnScreen(rect);
        return null;
    }

    private void invalidateRectOnScreen(android.graphics.Rect rect) {
        android.graphics.Rect rect2 = this.mDirty;
        rect2.union(rect.left, rect.top, rect.right, rect.bottom);
        float f = this.mAttachInfo.mApplicationScale;
        boolean intersect = rect2.intersect(0, 0, (int) ((this.mWidth * f) + 0.5f), (int) ((this.mHeight * f) + 0.5f));
        if (!intersect) {
            rect2.setEmpty();
        }
        if (this.mWillDrawSoon) {
            return;
        }
        if (intersect || this.mIsAnimating) {
            scheduleTraversals();
        }
    }

    public void setIsAmbientMode(boolean z) {
        this.mIsAmbientMode = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setWindowStopped(boolean z) {
        checkThread();
        if (this.mStopped != z) {
            this.mStopped = z;
            android.view.ThreadedRenderer threadedRenderer = this.mAttachInfo.mThreadedRenderer;
            if (threadedRenderer != null) {
                threadedRenderer.setStopped(this.mStopped);
            }
            if (!this.mStopped) {
                this.mAppVisibilityChanged = true;
                scheduleTraversals();
                return;
            }
            if (threadedRenderer != null) {
                threadedRenderer.destroyHardwareResources(this.mView);
            }
            if (this.mSurface.isValid()) {
                if (this.mSurfaceHolder != null) {
                    notifyHolderSurfaceDestroyed();
                }
                notifySurfaceDestroyed();
            }
            destroySurface();
        }
    }

    public interface SurfaceChangedCallback {
        void surfaceCreated(android.view.SurfaceControl.Transaction transaction);

        void surfaceDestroyed();

        void surfaceReplaced(android.view.SurfaceControl.Transaction transaction);

        default void vriDrawStarted(boolean z) {
        }
    }

    public void addSurfaceChangedCallback(android.view.ViewRootImpl.SurfaceChangedCallback surfaceChangedCallback) {
        this.mSurfaceChangedCallbacks.add(surfaceChangedCallback);
    }

    public void removeSurfaceChangedCallback(android.view.ViewRootImpl.SurfaceChangedCallback surfaceChangedCallback) {
        this.mSurfaceChangedCallbacks.remove(surfaceChangedCallback);
    }

    private void notifySurfaceCreated(android.view.SurfaceControl.Transaction transaction) {
        for (int i = 0; i < this.mSurfaceChangedCallbacks.size(); i++) {
            this.mSurfaceChangedCallbacks.get(i).surfaceCreated(transaction);
        }
    }

    private void notifySurfaceReplaced(android.view.SurfaceControl.Transaction transaction) {
        for (int i = 0; i < this.mSurfaceChangedCallbacks.size(); i++) {
            this.mSurfaceChangedCallbacks.get(i).surfaceReplaced(transaction);
        }
    }

    private void notifySurfaceDestroyed() {
        for (int i = 0; i < this.mSurfaceChangedCallbacks.size(); i++) {
            this.mSurfaceChangedCallbacks.get(i).surfaceDestroyed();
        }
    }

    private void notifyDrawStarted(boolean z) {
        for (int i = 0; i < this.mSurfaceChangedCallbacks.size(); i++) {
            this.mSurfaceChangedCallbacks.get(i).vriDrawStarted(z);
        }
    }

    public android.view.SurfaceControl updateAndGetBoundsLayer(android.view.SurfaceControl.Transaction transaction) {
        if (this.mBoundsLayer == null) {
            this.mBoundsLayer = new android.view.SurfaceControl.Builder(this.mSurfaceSession).setContainerLayer().setName("Bounds for - " + getTitle().toString()).setParent(getSurfaceControl()).setCallsite("ViewRootImpl.getBoundsLayer").build();
            setBoundsLayerCrop(transaction);
            transaction.show(this.mBoundsLayer);
        }
        return this.mBoundsLayer;
    }

    void updateBlastSurfaceIfNeeded() {
        if (!this.mSurfaceControl.isValid()) {
            return;
        }
        if (this.mBlastBufferQueue != null && this.mBlastBufferQueue.isSameSurfaceControl(this.mSurfaceControl)) {
            this.mBlastBufferQueue.update(this.mSurfaceControl, this.mSurfaceSize.x, this.mSurfaceSize.y, this.mWindowAttributes.format);
            return;
        }
        if (this.mBlastBufferQueue != null) {
            this.mBlastBufferQueue.destroy();
        }
        this.mBlastBufferQueue = new android.graphics.BLASTBufferQueue(this.mTag, this.mSurfaceControl, this.mSurfaceSize.x, this.mSurfaceSize.y, this.mWindowAttributes.format);
        this.mBlastBufferQueue.setTransactionHangCallback(sTransactionHangCallback);
        this.mSurface.transferFrom(this.mBlastBufferQueue.createSurface());
    }

    private void setBoundsLayerCrop(android.view.SurfaceControl.Transaction transaction) {
        this.mTempRect.set(0, 0, this.mSurfaceSize.x, this.mSurfaceSize.y);
        this.mTempRect.inset(this.mWindowAttributes.surfaceInsets.left, this.mWindowAttributes.surfaceInsets.top, this.mWindowAttributes.surfaceInsets.right, this.mWindowAttributes.surfaceInsets.bottom);
        this.mTempRect.inset(this.mChildBoundingInsets.left, this.mChildBoundingInsets.top, this.mChildBoundingInsets.right, this.mChildBoundingInsets.bottom);
        transaction.setWindowCrop(this.mBoundsLayer, this.mTempRect);
    }

    private boolean updateBoundsLayer(android.view.SurfaceControl.Transaction transaction) {
        if (this.mBoundsLayer != null) {
            setBoundsLayerCrop(transaction);
            return true;
        }
        return false;
    }

    private void prepareSurfaces() {
        android.view.SurfaceControl.Transaction transaction = this.mTransaction;
        android.view.SurfaceControl surfaceControl = getSurfaceControl();
        if (surfaceControl.isValid()) {
            if (updateBoundsLayer(transaction)) {
                applyTransactionOnDraw(transaction);
            }
            if (shouldEnableDvrr()) {
                try {
                    this.mFrameRateTransaction.setFrameRateSelectionStrategy(surfaceControl, 2).applyAsyncUnsafe();
                } catch (java.lang.Exception e) {
                    android.util.Log.e(this.mTag, "Unable to set frame rate selection strategy ", e);
                }
            }
        }
    }

    private void destroySurface() {
        if (this.mBoundsLayer != null) {
            this.mBoundsLayer.release();
            this.mBoundsLayer = null;
        }
        this.mSurface.release();
        this.mSurfaceControl.release();
        if (this.mBlastBufferQueue != null) {
            this.mBlastBufferQueue.destroy();
            this.mBlastBufferQueue = null;
        }
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.setSurfaceControl(null, null);
        }
    }

    public void setPausedForTransition(boolean z) {
        this.mPausedForTransition = z;
    }

    @Override // android.view.ViewParent
    public android.view.ViewParent getParent() {
        return null;
    }

    @Override // android.view.ViewParent
    public boolean getChildVisibleRect(android.view.View view, android.graphics.Rect rect, android.graphics.Point point) {
        if (view != this.mView) {
            throw new java.lang.RuntimeException("child is not mine, honest!");
        }
        return rect.intersect(0, 0, this.mWidth, this.mHeight);
    }

    @Override // android.view.ViewParent
    public boolean getChildLocalHitRegion(android.view.View view, android.graphics.Region region, android.graphics.Matrix matrix, boolean z) {
        if (view != this.mView) {
            throw new java.lang.IllegalArgumentException("child " + view + " is not the root view " + this.mView + " managed by this ViewRootImpl");
        }
        android.graphics.RectF rectF = new android.graphics.RectF(0.0f, 0.0f, this.mWidth, this.mHeight);
        matrix.mapRect(rectF);
        return region.op(java.lang.Math.round(rectF.left), java.lang.Math.round(rectF.top), java.lang.Math.round(rectF.right), java.lang.Math.round(rectF.bottom), android.graphics.Region.Op.INTERSECT);
    }

    @Override // android.view.ViewParent
    public void bringChildToFront(android.view.View view) {
    }

    int getHostVisibility() {
        if (this.mView == null || !(this.mAppVisible || this.mForceDecorViewVisibility)) {
            return 8;
        }
        return this.mView.getVisibility();
    }

    public void requestTransitionStart(android.animation.LayoutTransition layoutTransition) {
        if (this.mPendingTransitions == null || !this.mPendingTransitions.contains(layoutTransition)) {
            if (this.mPendingTransitions == null) {
                this.mPendingTransitions = new java.util.ArrayList<>();
            }
            this.mPendingTransitions.add(layoutTransition);
        }
    }

    void notifyRendererOfFramePending() {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.notifyFramePending();
        }
    }

    public void notifyRendererOfExpensiveFrame() {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.notifyExpensiveFrame();
        }
    }

    void scheduleTraversals() {
        if (!this.mTraversalScheduled) {
            this.mTraversalScheduled = true;
            this.mTraversalBarrier = this.mHandler.getLooper().getQueue().postSyncBarrier();
            this.mChoreographer.postCallback(3, this.mTraversalRunnable, null);
            notifyRendererOfFramePending();
            pokeDrawLockIfNeeded();
        }
    }

    void unscheduleTraversals() {
        if (this.mTraversalScheduled) {
            this.mTraversalScheduled = false;
            this.mHandler.getLooper().getQueue().removeSyncBarrier(this.mTraversalBarrier);
            this.mChoreographer.removeCallbacks(3, this.mTraversalRunnable, null);
        }
    }

    void doTraversal() {
        if (this.mTraversalScheduled) {
            this.mTraversalScheduled = false;
            this.mHandler.getLooper().getQueue().removeSyncBarrier(this.mTraversalBarrier);
            if (this.mProfile) {
                android.os.Debug.startMethodTracing("ViewAncestor");
            }
            performTraversals();
            if (this.mProfile) {
                android.os.Debug.stopMethodTracing();
                this.mProfile = false;
            }
        }
    }

    private void applyKeepScreenOnFlag(android.view.WindowManager.LayoutParams layoutParams) {
        if (this.mAttachInfo.mKeepScreenOn) {
            layoutParams.flags |= 128;
        } else {
            layoutParams.flags = (layoutParams.flags & android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE) | (this.mClientWindowLayoutFlags & 128);
        }
    }

    private boolean collectViewAttributes() {
        if (this.mAttachInfo.mRecomputeGlobalAttributes) {
            this.mAttachInfo.mRecomputeGlobalAttributes = false;
            boolean z = this.mAttachInfo.mKeepScreenOn;
            this.mAttachInfo.mKeepScreenOn = false;
            this.mAttachInfo.mSystemUiVisibility = 0;
            this.mAttachInfo.mHasSystemUiListeners = false;
            this.mView.dispatchCollectViewAttributes(this.mAttachInfo, 0);
            this.mAttachInfo.mSystemUiVisibility &= ~this.mAttachInfo.mDisabledSystemUiVisibility;
            android.view.WindowManager.LayoutParams layoutParams = this.mWindowAttributes;
            this.mAttachInfo.mSystemUiVisibility |= getImpliedSystemUiVisibility(layoutParams);
            this.mCompatibleVisibilityInfo.globalVisibility = (this.mCompatibleVisibilityInfo.globalVisibility & (-2)) | (this.mAttachInfo.mSystemUiVisibility & 1);
            dispatchDispatchSystemUiVisibilityChanged();
            if (this.mAttachInfo.mKeepScreenOn != z || this.mAttachInfo.mSystemUiVisibility != layoutParams.subtreeSystemUiVisibility || this.mAttachInfo.mHasSystemUiListeners != layoutParams.hasSystemUiListeners) {
                applyKeepScreenOnFlag(layoutParams);
                layoutParams.subtreeSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                layoutParams.hasSystemUiListeners = this.mAttachInfo.mHasSystemUiListeners;
                this.mView.dispatchWindowSystemUiVisiblityChanged(this.mAttachInfo.mSystemUiVisibility);
                return true;
            }
        }
        return false;
    }

    private int getImpliedSystemUiVisibility(android.view.WindowManager.LayoutParams layoutParams) {
        int i;
        if ((layoutParams.flags & 67108864) == 0) {
            i = 0;
        } else {
            i = 1280;
        }
        if ((layoutParams.flags & 134217728) != 0) {
            return i | 768;
        }
        return i;
    }

    void updateCompatSysUiVisibility(int i, int i2, int i3) {
        int i4 = (i & (~i3)) | (i2 & i3);
        updateCompatSystemUiVisibilityInfo(4, android.view.WindowInsets.Type.statusBars(), i4, i3);
        updateCompatSystemUiVisibilityInfo(2, android.view.WindowInsets.Type.navigationBars(), i4, i3);
        dispatchDispatchSystemUiVisibilityChanged();
    }

    private void updateCompatSystemUiVisibilityInfo(int i, int i2, int i3, int i4) {
        android.view.ViewRootImpl.SystemUiVisibilityInfo systemUiVisibilityInfo = this.mCompatibleVisibilityInfo;
        boolean z = (i3 & i2) != 0;
        boolean z2 = (i2 & i4) != 0;
        boolean z3 = (this.mAttachInfo.mSystemUiVisibility & i) != 0;
        if (z) {
            systemUiVisibilityInfo.globalVisibility &= ~i;
            if (z2 && z3) {
                systemUiVisibilityInfo.localChanges = i | systemUiVisibilityInfo.localChanges;
                return;
            }
            return;
        }
        systemUiVisibilityInfo.globalVisibility |= i;
        systemUiVisibilityInfo.localChanges = (~i) & systemUiVisibilityInfo.localChanges;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearLowProfileModeIfNeeded(int i, boolean z) {
        android.view.ViewRootImpl.SystemUiVisibilityInfo systemUiVisibilityInfo = this.mCompatibleVisibilityInfo;
        if ((i & android.view.WindowInsets.Type.systemBars()) != 0 && !z && (systemUiVisibilityInfo.globalVisibility & 1) != 0) {
            systemUiVisibilityInfo.globalVisibility &= -2;
            systemUiVisibilityInfo.localChanges |= 1;
            dispatchDispatchSystemUiVisibilityChanged();
        }
    }

    private void dispatchDispatchSystemUiVisibilityChanged() {
        if (this.mDispatchedSystemUiVisibility != this.mCompatibleVisibilityInfo.globalVisibility) {
            this.mHandler.removeMessages(17);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(17));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDispatchSystemUiVisibilityChanged() {
        if (this.mView == null) {
            return;
        }
        android.view.ViewRootImpl.SystemUiVisibilityInfo systemUiVisibilityInfo = this.mCompatibleVisibilityInfo;
        if (systemUiVisibilityInfo.localChanges != 0) {
            this.mView.updateLocalSystemUiVisibility(systemUiVisibilityInfo.localValue, systemUiVisibilityInfo.localChanges);
            systemUiVisibilityInfo.localChanges = 0;
        }
        int i = systemUiVisibilityInfo.globalVisibility & 7;
        if (this.mDispatchedSystemUiVisibility != i) {
            this.mDispatchedSystemUiVisibility = i;
            this.mView.dispatchSystemUiVisibilityChanged(i);
        }
    }

    public static void adjustLayoutParamsForCompatibility(android.view.WindowManager.LayoutParams layoutParams) {
        int i = layoutParams.systemUiVisibility | layoutParams.subtreeSystemUiVisibility;
        int i2 = layoutParams.flags;
        int i3 = layoutParams.type;
        int i4 = layoutParams.softInputMode & 240;
        if ((layoutParams.privateFlags & 67108864) == 0) {
            layoutParams.insetsFlags.appearance = 0;
            if ((i & 1) != 0) {
                layoutParams.insetsFlags.appearance |= 4;
            }
            if ((i & 8192) != 0) {
                layoutParams.insetsFlags.appearance |= 8;
            }
            if ((i & 16) != 0) {
                layoutParams.insetsFlags.appearance |= 16;
            }
        }
        boolean z = true;
        if ((layoutParams.privateFlags & 134217728) == 0) {
            if ((i & 4096) != 0 || (i2 & 1024) != 0) {
                layoutParams.insetsFlags.behavior = 2;
            } else {
                layoutParams.insetsFlags.behavior = 1;
            }
        }
        layoutParams.privateFlags &= -1073741825;
        if ((layoutParams.privateFlags & 268435456) != 0) {
            return;
        }
        int fitInsetsTypes = layoutParams.getFitInsetsTypes();
        boolean isFitInsetsIgnoringVisibility = layoutParams.isFitInsetsIgnoringVisibility();
        if ((i & 1024) != 0 || (i2 & 256) != 0 || (67108864 & i2) != 0) {
            fitInsetsTypes &= ~android.view.WindowInsets.Type.statusBars();
        }
        if ((i & 512) != 0 || (i2 & 134217728) != 0) {
            fitInsetsTypes &= ~android.view.WindowInsets.Type.systemBars();
        }
        if (i3 != 2005 && i3 != 2003) {
            if ((android.view.WindowInsets.Type.systemBars() & fitInsetsTypes) == android.view.WindowInsets.Type.systemBars()) {
                if (i4 == 16) {
                    fitInsetsTypes |= android.view.WindowInsets.Type.ime();
                    z = isFitInsetsIgnoringVisibility;
                } else {
                    layoutParams.privateFlags |= 1073741824;
                }
            }
            z = isFitInsetsIgnoringVisibility;
        }
        layoutParams.setFitInsetsTypes(fitInsetsTypes);
        layoutParams.setFitInsetsIgnoringVisibility(z);
        layoutParams.privateFlags &= -268435457;
    }

    private void controlInsetsForCompatibility(android.view.WindowManager.LayoutParams layoutParams) {
        int i;
        int i2 = layoutParams.systemUiVisibility | layoutParams.subtreeSystemUiVisibility;
        int i3 = layoutParams.flags;
        int i4 = 0;
        boolean z = layoutParams.width == -1 && layoutParams.height == -1;
        boolean z2 = layoutParams.type >= 1 && layoutParams.type <= 99;
        boolean z3 = (this.mTypesHiddenByFlags & android.view.WindowInsets.Type.statusBars()) != 0;
        boolean z4 = (i2 & 4) != 0 || ((i3 & 1024) != 0 && z && z2);
        boolean z5 = (this.mTypesHiddenByFlags & android.view.WindowInsets.Type.navigationBars()) != 0;
        boolean z6 = (i2 & 2) != 0;
        if (z4 && !z3) {
            i4 = android.view.WindowInsets.Type.statusBars() | 0;
            i = 0;
        } else if (!z4 && z3) {
            i = android.view.WindowInsets.Type.statusBars() | 0;
        } else {
            i = 0;
        }
        if (z6 && !z5) {
            i4 |= android.view.WindowInsets.Type.navigationBars();
        } else if (!z6 && z5) {
            i |= android.view.WindowInsets.Type.navigationBars();
        }
        if (i4 != 0) {
            getInsetsController().hide(i4);
        }
        if (i != 0) {
            getInsetsController().show(i);
        }
        this.mTypesHiddenByFlags |= i4;
        this.mTypesHiddenByFlags = (~i) & this.mTypesHiddenByFlags;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean measureHierarchy(android.view.View view, android.view.WindowManager.LayoutParams layoutParams, android.content.res.Resources resources, int i, int i2, boolean z) {
        boolean z2;
        int i3;
        if (layoutParams.width == -2) {
            android.util.DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            resources.getValue(com.android.internal.R.dimen.config_prefDialogWidth, this.mTmpValue, true);
            if (this.mTmpValue.type != 5) {
                i3 = 0;
            } else {
                i3 = (int) this.mTmpValue.getDimension(displayMetrics);
            }
            if (i3 != 0 && i > i3) {
                int rootMeasureSpec = getRootMeasureSpec(i3, layoutParams.width, layoutParams.privateFlags);
                int rootMeasureSpec2 = getRootMeasureSpec(i2, layoutParams.height, layoutParams.privateFlags);
                performMeasure(rootMeasureSpec, rootMeasureSpec2);
                if ((view.getMeasuredWidthAndState() & 16777216) == 0) {
                    z2 = true;
                } else {
                    performMeasure(getRootMeasureSpec((i3 + i) / 2, layoutParams.width, layoutParams.privateFlags), rootMeasureSpec2);
                    if ((view.getMeasuredWidthAndState() & 16777216) == 0) {
                        z2 = true;
                    }
                }
                if (!z2) {
                    int rootMeasureSpec3 = getRootMeasureSpec(i, layoutParams.width, layoutParams.privateFlags);
                    int rootMeasureSpec4 = getRootMeasureSpec(i2, layoutParams.height, layoutParams.privateFlags);
                    if (!z || !setMeasuredRootSizeFromSpec(rootMeasureSpec3, rootMeasureSpec4)) {
                        performMeasure(rootMeasureSpec3, rootMeasureSpec4);
                    } else {
                        this.mViewMeasureDeferred = true;
                    }
                    if (this.mWidth != view.getMeasuredWidth() || this.mHeight != view.getMeasuredHeight()) {
                        return true;
                    }
                }
                return false;
            }
        }
        z2 = false;
        if (!z2) {
        }
        return false;
    }

    private boolean setMeasuredRootSizeFromSpec(int i, int i2) {
        int mode = android.view.View.MeasureSpec.getMode(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        if (mode != 1073741824 || mode2 != 1073741824) {
            return false;
        }
        this.mMeasuredWidth = android.view.View.MeasureSpec.getSize(i);
        this.mMeasuredHeight = android.view.View.MeasureSpec.getSize(i2);
        return true;
    }

    void transformMatrixToGlobal(android.graphics.Matrix matrix) {
        matrix.preTranslate(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
    }

    void transformMatrixToLocal(android.graphics.Matrix matrix) {
        matrix.postTranslate(-this.mAttachInfo.mWindowLeft, -this.mAttachInfo.mWindowTop);
    }

    android.view.WindowInsets getWindowInsets(boolean z) {
        if (this.mLastWindowInsets == null || z) {
            android.content.res.Configuration configuration = getConfiguration();
            this.mLastWindowInsets = this.mInsetsController.calculateInsets(configuration.isScreenRound(), this.mWindowAttributes.type, configuration.windowConfiguration.getActivityType(), this.mWindowAttributes.softInputMode, this.mWindowAttributes.flags, this.mWindowAttributes.systemUiVisibility | this.mWindowAttributes.subtreeSystemUiVisibility);
            this.mAttachInfo.mContentInsets.set(this.mLastWindowInsets.getSystemWindowInsets().toRect());
            this.mAttachInfo.mStableInsets.set(this.mLastWindowInsets.getStableInsets().toRect());
            this.mAttachInfo.mVisibleInsets.set(this.mInsetsController.calculateVisibleInsets(this.mWindowAttributes.type, configuration.windowConfiguration.getActivityType(), this.mWindowAttributes.softInputMode, this.mWindowAttributes.flags).toRect());
        }
        return this.mLastWindowInsets;
    }

    public void dispatchApplyInsets(android.view.View view) {
        android.os.Trace.traceBegin(8L, "dispatchApplyInsets");
        this.mApplyInsetsRequested = false;
        android.view.WindowInsets windowInsets = getWindowInsets(true);
        if (!shouldDispatchCutout()) {
            windowInsets = windowInsets.consumeDisplayCutout();
        }
        view.dispatchApplyWindowInsets(windowInsets);
        this.mAttachInfo.delayNotifyContentCaptureInsetsEvent(windowInsets.getInsets(android.view.WindowInsets.Type.all()));
        android.os.Trace.traceEnd(8L);
    }

    private boolean updateCaptionInsets() {
        if (CAPTION_ON_SHELL || !(this.mView instanceof com.android.internal.policy.DecorView)) {
            return false;
        }
        int captionInsetsHeight = ((com.android.internal.policy.DecorView) this.mView).getCaptionInsetsHeight();
        android.graphics.Rect rect = new android.graphics.Rect();
        if (captionInsetsHeight != 0) {
            rect.set(this.mWinFrame.left, this.mWinFrame.top, this.mWinFrame.right, this.mWinFrame.top + captionInsetsHeight);
        }
        if (this.mAttachInfo.mCaptionInsets.equals(rect)) {
            return false;
        }
        this.mAttachInfo.mCaptionInsets.set(rect);
        return true;
    }

    private boolean shouldDispatchCutout() {
        return this.mWindowAttributes.layoutInDisplayCutoutMode == 3 || this.mWindowAttributes.layoutInDisplayCutoutMode == 1;
    }

    public android.view.InsetsController getInsetsController() {
        return this.mInsetsController;
    }

    private static boolean shouldUseDisplaySize(android.view.WindowManager.LayoutParams layoutParams) {
        return layoutParams.type == 2041 || layoutParams.type == 2011 || layoutParams.type == 2020;
    }

    private static boolean shouldOptimizeMeasure(android.view.WindowManager.LayoutParams layoutParams) {
        return (layoutParams.privateFlags & 512) != 0;
    }

    private android.graphics.Rect getWindowBoundsInsetSystemBars() {
        android.graphics.Rect rect = new android.graphics.Rect(this.mContext.getResources().getConfiguration().windowConfiguration.getBounds());
        rect.inset(this.mInsetsController.getState().calculateInsets(rect, android.view.WindowInsets.Type.systemBars(), false));
        return rect;
    }

    int dipToPx(int i) {
        return (int) ((this.mContext.getResources().getDisplayMetrics().density * i) + 0.5f);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(134:(4:24|(1:26)(1:841)|(1:28)(1:840)|(176:30|31|(7:33|(1:35)(2:828|(1:833)(1:832))|36|(1:38)|39|(1:43)|44)(2:834|(1:839)(1:838))|45|(5:47|(2:(1:50)(1:52)|51)|(1:60)|56|(1:59))|61|(1:63)|64|(1:66)|67|(1:827)(1:73)|(3:75|(1:825)(2:81|(1:83)(1:824))|84)(1:826)|85|(1:87)(1:823)|88|(1:90)|91|(2:807|(6:809|(3:811|(2:813|814)(1:816)|815)|817|(1:819)|820|(154:822|96|(2:98|(1:100)(1:805))(1:806)|(1:102)|(1:804)(1:121)|122|(1:803)(1:126)|127|(1:802)(1:131)|132|(1:134)(1:801)|135|(1:137)(1:800)|(4:139|(1:143)|144|(1:146))|147|(1:799)(2:152|(1:154)(43:798|243|(1:245)|246|(1:487)(3:250|251|252)|(1:483)|(1:482)(1:264)|(1:481)(1:268)|(2:270|(6:272|(1:274)|275|(1:277)|278|(2:280|(1:282))))|(1:284)(1:(1:477)(2:(1:479)|480))|(1:286)|(1:288)|289|(3:291|(3:470|(1:472)(1:474)|473)(1:295)|296)(1:475)|297|(1:469)(1:301)|302|(7:304|(4:306|(1:308)|309|(1:311))(1:457)|(1:313)(1:456)|(1:315)(1:455)|(1:317)(2:(1:453)|454)|318|319)(2:458|(3:462|463|464))|320|(4:322|(2:334|(1:338))(2:326|(1:330))|331|(1:333))|(1:445)(1:342)|(1:344)|345|(1:443)(1:348)|349|(1:351)|(1:442)(1:354)|355|(1:441)(1:360)|(4:362|(1:364)(1:370)|365|(1:369))|(5:372|(1:374)|375|(4:379|(2:382|380)|383|384)|385)(1:(4:409|(3:411|(1:417)(1:415)|416)|(1:419)(1:421)|420)(8:422|(1:424)|425|(1:427)|428|(4:432|(2:435|433)|436|437)|438|(1:440)))|386|(1:388)|389|(2:391|(1:393))|394|(1:396)(1:407)|397|(1:399)(1:406)|400|(1:402)(1:405)|403|404))|155|(3:157|(1:159)(1:796)|160)(1:797)|161|(1:163)|164|165|166|(126:170|171|172|(1:174)(1:779)|175|176|177|178|179|(3:181|(3:183|184|185)|518)(1:771)|519|(1:521)(1:769)|522|523|(5:742|743|(1:747)|(3:750|751|(5:754|755|756|757|758))(1:764)|753)(1:525)|526|(1:741)(4:530|(1:532)(1:740)|533|534)|535|536|537|538|539|(6:717|718|719|720|721|722)(1:541)|542|543|(1:545)(1:714)|546|547|548|549|(2:551|(95:553|(3:701|702|(91:704|556|557|(2:694|(87:696|(2:692|693)|(2:563|564)(1:691)|565|566|(1:568)|(3:677|678|(1:680))|570|571|(3:573|574|(72:621|622|(3:625|626|(1:628))|624|577|578|579|(1:(10:582|(3:609|610|(8:612|585|(1:587)(1:607)|588|589|590|591|592))|584|585|(0)(0)|588|589|590|591|592)(1:616))(1:617)|593|(1:(1:596)(1:597))|598|(1:600)|194|(1:517)|198|(6:200|(1:202)|203|(2:205|(3:207|(1:209)|210))|(2:504|(3:506|(4:508|(1:510)|511|512)(1:514)|513)(1:515))(1:215)|(4:217|218|219|220))(1:516)|225|(1:236)|237|(6:494|(1:496)(1:503)|497|(1:499)|(1:501)|502)(1:241)|242|243|(0)|246|(1:248)|487|(0)|483|(2:260|262)|482|(1:266)|481|(0)|(0)(0)|(0)|(0)|289|(0)(0)|297|(1:299)|469|302|(0)(0)|320|(0)|(1:340)|445|(0)|345|(0)|443|349|(0)|(0)|442|355|(1:358)|441|(0)|(0)(0)|386|(0)|389|(0)|394|(0)(0)|397|(0)(0)|400|(0)(0)|403|404))(1:(8:644|(1:646)|647|(1:649)|650|(1:652)|653|(1:655))(1:(2:659|(3:665|666|667))))|576|577|578|579|(0)(0)|593|(0)|598|(0)|194|(1:196)|517|198|(0)(0)|225|(2:227|236)|237|(1:239)|488|494|(0)(0)|497|(0)|(0)|502|242|243|(0)|246|(0)|487|(0)|483|(0)|482|(0)|481|(0)|(0)(0)|(0)|(0)|289|(0)(0)|297|(0)|469|302|(0)(0)|320|(0)|(0)|445|(0)|345|(0)|443|349|(0)|(0)|442|355|(0)|441|(0)|(0)(0)|386|(0)|389|(0)|394|(0)(0)|397|(0)(0)|400|(0)(0)|403|404))|560|(0)|(0)(0)|565|566|(0)|(0)|570|571|(0)(0)|576|577|578|579|(0)(0)|593|(0)|598|(0)|194|(0)|517|198|(0)(0)|225|(0)|237|(0)|488|494|(0)(0)|497|(0)|(0)|502|242|243|(0)|246|(0)|487|(0)|483|(0)|482|(0)|481|(0)|(0)(0)|(0)|(0)|289|(0)(0)|297|(0)|469|302|(0)(0)|320|(0)|(0)|445|(0)|345|(0)|443|349|(0)|(0)|442|355|(0)|441|(0)|(0)(0)|386|(0)|389|(0)|394|(0)(0)|397|(0)(0)|400|(0)(0)|403|404))|555|556|557|(0)|694|(0)|560|(0)|(0)(0)|565|566|(0)|(0)|570|571|(0)(0)|576|577|578|579|(0)(0)|593|(0)|598|(0)|194|(0)|517|198|(0)(0)|225|(0)|237|(0)|488|494|(0)(0)|497|(0)|(0)|502|242|243|(0)|246|(0)|487|(0)|483|(0)|482|(0)|481|(0)|(0)(0)|(0)|(0)|289|(0)(0)|297|(0)|469|302|(0)(0)|320|(0)|(0)|445|(0)|345|(0)|443|349|(0)|(0)|442|355|(0)|441|(0)|(0)(0)|386|(0)|389|(0)|394|(0)(0)|397|(0)(0)|400|(0)(0)|403|404))|709|(0)|555|556|557|(0)|694|(0)|560|(0)|(0)(0)|565|566|(0)|(0)|570|571|(0)(0)|576|577|578|579|(0)(0)|593|(0)|598|(0)|194|(0)|517|198|(0)(0)|225|(0)|237|(0)|488|494|(0)(0)|497|(0)|(0)|502|242|243|(0)|246|(0)|487|(0)|483|(0)|482|(0)|481|(0)|(0)(0)|(0)|(0)|289|(0)(0)|297|(0)|469|302|(0)(0)|320|(0)|(0)|445|(0)|345|(0)|443|349|(0)|(0)|442|355|(0)|441|(0)|(0)(0)|386|(0)|389|(0)|394|(0)(0)|397|(0)(0)|400|(0)(0)|403|404)|784|785|786|787|171|172|(0)(0)|175|176|177|178|179|(0)(0)|519|(0)(0)|522|523|(0)(0)|526|(1:528)|741|535|536|537|538|539|(0)(0)|542|543|(0)(0)|546|547|548|549|(0)|709|(0)|555|556|557|(0)|694|(0)|560|(0)|(0)(0)|565|566|(0)|(0)|570|571|(0)(0)|576|577|578|579|(0)(0)|593|(0)|598|(0)|194|(0)|517|198|(0)(0)|225|(0)|237|(0)|488|494|(0)(0)|497|(0)|(0)|502|242|243|(0)|246|(0)|487|(0)|483|(0)|482|(0)|481|(0)|(0)(0)|(0)|(0)|289|(0)(0)|297|(0)|469|302|(0)(0)|320|(0)|(0)|445|(0)|345|(0)|443|349|(0)|(0)|442|355|(0)|441|(0)|(0)(0)|386|(0)|389|(0)|394|(0)(0)|397|(0)(0)|400|(0)(0)|403|404)))|95|96|(0)(0)|(0)|(0)|804|122|(1:124)|803|127|(1:129)|802|132|(0)(0)|135|(0)(0)|(0)|147|(0)|799|155|(0)(0)|161|(0)|164|165|166|(127:170|171|172|(0)(0)|175|176|177|178|179|(0)(0)|519|(0)(0)|522|523|(0)(0)|526|(0)|741|535|536|537|538|539|(0)(0)|542|543|(0)(0)|546|547|548|549|(0)|709|(0)|555|556|557|(0)|694|(0)|560|(0)|(0)(0)|565|566|(0)|(0)|570|571|(0)(0)|576|577|578|579|(0)(0)|593|(0)|598|(0)|194|(0)|517|198|(0)(0)|225|(0)|237|(0)|488|494|(0)(0)|497|(0)|(0)|502|242|243|(0)|246|(0)|487|(0)|483|(0)|482|(0)|481|(0)|(0)(0)|(0)|(0)|289|(0)(0)|297|(0)|469|302|(0)(0)|320|(0)|(0)|445|(0)|345|(0)|443|349|(0)|(0)|442|355|(0)|441|(0)|(0)(0)|386|(0)|389|(0)|394|(0)(0)|397|(0)(0)|400|(0)(0)|403|404)|784|785|786|787|171|172|(0)(0)|175|176|177|178|179|(0)(0)|519|(0)(0)|522|523|(0)(0)|526|(0)|741|535|536|537|538|539|(0)(0)|542|543|(0)(0)|546|547|548|549|(0)|709|(0)|555|556|557|(0)|694|(0)|560|(0)|(0)(0)|565|566|(0)|(0)|570|571|(0)(0)|576|577|578|579|(0)(0)|593|(0)|598|(0)|194|(0)|517|198|(0)(0)|225|(0)|237|(0)|488|494|(0)(0)|497|(0)|(0)|502|242|243|(0)|246|(0)|487|(0)|483|(0)|482|(0)|481|(0)|(0)(0)|(0)|(0)|289|(0)(0)|297|(0)|469|302|(0)(0)|320|(0)|(0)|445|(0)|345|(0)|443|349|(0)|(0)|442|355|(0)|441|(0)|(0)(0)|386|(0)|389|(0)|394|(0)(0)|397|(0)(0)|400|(0)(0)|403|404))|165|166|(0)|784|785|786|787|171|172|(0)(0)|175|176|177|178|179|(0)(0)|519|(0)(0)|522|523|(0)(0)|526|(0)|741|535|536|537|538|539|(0)(0)|542|543|(0)(0)|546|547|548|549|(0)|709|(0)|555|556|557|(0)|694|(0)|560|(0)|(0)(0)|565|566|(0)|(0)|570|571|(0)(0)|576|577|578|579|(0)(0)|593|(0)|598|(0)|194|(0)|517|198|(0)(0)|225|(0)|237|(0)|488|494|(0)(0)|497|(0)|(0)|502|242|243|(0)|246|(0)|487|(0)|483|(0)|482|(0)|481|(0)|(0)(0)|(0)|(0)|289|(0)(0)|297|(0)|469|302|(0)(0)|320|(0)|(0)|445|(0)|345|(0)|443|349|(0)|(0)|442|355|(0)|441|(0)|(0)(0)|386|(0)|389|(0)|394|(0)(0)|397|(0)(0)|400|(0)(0)|403|404) */
    /* JADX WARN: Code restructure failed: missing block: B:620:0x066a, code lost:
    
        r28 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:681:0x0520, code lost:
    
        if (r34.mApplyInsetsRequested == false) goto L342;
     */
    /* JADX WARN: Code restructure failed: missing block: B:687:0x0675, code lost:
    
        r29 = r2;
        r20 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:699:0x0680, code lost:
    
        r29 = r2;
        r1 = 8;
        r5 = false;
        r13 = false;
        r15 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:711:0x068d, code lost:
    
        r1 = 8;
        r5 = false;
        r9 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:716:0x068b, code lost:
    
        r27 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:731:0x0692, code lost:
    
        r27 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:732:0x069a, code lost:
    
        r26 = r15;
        r1 = 8;
        r5 = false;
        r9 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:735:0x0696, code lost:
    
        r27 = r2;
        r25 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:768:0x06ab, code lost:
    
        r27 = r2;
        r22 = "relayout";
        r24 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:773:0x06bf, code lost:
    
        r27 = r2;
        r23 = r5;
        r22 = "relayout";
        r24 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:774:0x06d3, code lost:
    
        r1 = 8;
        r5 = false;
        r9 = false;
        r13 = false;
        r15 = null;
        r20 = null;
        r24 = r24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:775:0x0702, code lost:
    
        r25 = false;
        r26 = false;
        r9 = r9;
        r15 = r15;
        r24 = r24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:778:0x06c9, code lost:
    
        r27 = r2;
        r23 = r5;
        r22 = "relayout";
        r24 = r13;
        r21 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:782:0x06dd, code lost:
    
        r27 = r2;
        r21 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:783:0x06f4, code lost:
    
        r1 = 8;
        r5 = false;
        r9 = false;
        r13 = false;
        r15 = null;
        r20 = null;
        r22 = null;
        r23 = 0;
        r24 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:795:0x06f0, code lost:
    
        r27 = r2;
        r21 = r13;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0276 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02b5  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x02c7  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02de  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0321 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0352  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x038d  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x03a3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03bd  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x03cb A[Catch: RemoteException -> 0x06be, all -> 0x06e2, TRY_LEAVE, TryCatch #2 {all -> 0x06e2, blocks: (B:166:0x039f, B:171:0x03b3, B:176:0x03c1, B:179:0x03c7, B:181:0x03cb, B:185:0x03d9, B:523:0x0400, B:743:0x0408, B:745:0x040f, B:747:0x0413, B:750:0x0418, B:755:0x0422, B:758:0x042a, B:526:0x0444, B:528:0x0448, B:530:0x0452, B:533:0x0460, B:536:0x0467, B:539:0x0471, B:718:0x047a, B:721:0x0482, B:543:0x049b, B:546:0x04a6, B:549:0x04ac, B:551:0x04b5, B:702:0x04c2, B:557:0x04d9, B:693:0x04f0, B:563:0x0504, B:566:0x050d, B:678:0x0516, B:680:0x051e, B:574:0x0537, B:622:0x0544, B:626:0x0550, B:628:0x0556, B:578:0x05f2, B:582:0x05f8, B:610:0x0606, B:585:0x061d, B:590:0x0626, B:592:0x062c, B:593:0x0642, B:596:0x0648, B:597:0x0655, B:616:0x0636, B:631:0x0565, B:644:0x0583, B:646:0x0587, B:647:0x058c, B:649:0x0597, B:650:0x05a0, B:652:0x05a4, B:653:0x05a9, B:655:0x05af, B:659:0x05bd, B:661:0x05c1, B:663:0x05c7, B:665:0x05cf, B:667:0x05d2, B:670:0x05dd, B:570:0x052a, B:694:0x04e3, B:784:0x03a8, B:787:0x03ab), top: B:165:0x039f }] */
    /* JADX WARN: Removed duplicated region for block: B:192:0x070e  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0727  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x073f  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x07f2  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0822  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x089a  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x08b3  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x08cd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:260:0x08df  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x08ec  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x08f8  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x097c  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x0994  */
    /* JADX WARN: Removed duplicated region for block: B:288:0x099b  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x09af  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0a14  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x0a1e  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x0aa2  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x0ae4  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x0aef  */
    /* JADX WARN: Removed duplicated region for block: B:347:0x0b03 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:351:0x0b14  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0b1b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:357:0x0b2d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:362:0x0b39  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0b62  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x0c64  */
    /* JADX WARN: Removed duplicated region for block: B:391:0x0c6e  */
    /* JADX WARN: Removed duplicated region for block: B:396:0x0c97  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x0ca4  */
    /* JADX WARN: Removed duplicated region for block: B:402:0x0cb1  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x0cb6  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x0ca9  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x0c9c  */
    /* JADX WARN: Removed duplicated region for block: B:408:0x0ba6  */
    /* JADX WARN: Removed duplicated region for block: B:458:0x0a85  */
    /* JADX WARN: Removed duplicated region for block: B:475:0x0a04  */
    /* JADX WARN: Removed duplicated region for block: B:476:0x0983  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:496:0x0865  */
    /* JADX WARN: Removed duplicated region for block: B:499:0x087d  */
    /* JADX WARN: Removed duplicated region for block: B:501:0x0890  */
    /* JADX WARN: Removed duplicated region for block: B:503:0x0876  */
    /* JADX WARN: Removed duplicated region for block: B:516:0x07ea  */
    /* JADX WARN: Removed duplicated region for block: B:521:0x03fd  */
    /* JADX WARN: Removed duplicated region for block: B:525:0x0440  */
    /* JADX WARN: Removed duplicated region for block: B:528:0x0448 A[Catch: RemoteException -> 0x06a6, all -> 0x06e2, TryCatch #2 {all -> 0x06e2, blocks: (B:166:0x039f, B:171:0x03b3, B:176:0x03c1, B:179:0x03c7, B:181:0x03cb, B:185:0x03d9, B:523:0x0400, B:743:0x0408, B:745:0x040f, B:747:0x0413, B:750:0x0418, B:755:0x0422, B:758:0x042a, B:526:0x0444, B:528:0x0448, B:530:0x0452, B:533:0x0460, B:536:0x0467, B:539:0x0471, B:718:0x047a, B:721:0x0482, B:543:0x049b, B:546:0x04a6, B:549:0x04ac, B:551:0x04b5, B:702:0x04c2, B:557:0x04d9, B:693:0x04f0, B:563:0x0504, B:566:0x050d, B:678:0x0516, B:680:0x051e, B:574:0x0537, B:622:0x0544, B:626:0x0550, B:628:0x0556, B:578:0x05f2, B:582:0x05f8, B:610:0x0606, B:585:0x061d, B:590:0x0626, B:592:0x062c, B:593:0x0642, B:596:0x0648, B:597:0x0655, B:616:0x0636, B:631:0x0565, B:644:0x0583, B:646:0x0587, B:647:0x058c, B:649:0x0597, B:650:0x05a0, B:652:0x05a4, B:653:0x05a9, B:655:0x05af, B:659:0x05bd, B:661:0x05c1, B:663:0x05c7, B:665:0x05cf, B:667:0x05d2, B:670:0x05dd, B:570:0x052a, B:694:0x04e3, B:784:0x03a8, B:787:0x03ab), top: B:165:0x039f }] */
    /* JADX WARN: Removed duplicated region for block: B:541:0x0498  */
    /* JADX WARN: Removed duplicated region for block: B:545:0x04a3  */
    /* JADX WARN: Removed duplicated region for block: B:551:0x04b5 A[Catch: RemoteException -> 0x0688, all -> 0x06e2, TRY_LEAVE, TryCatch #2 {all -> 0x06e2, blocks: (B:166:0x039f, B:171:0x03b3, B:176:0x03c1, B:179:0x03c7, B:181:0x03cb, B:185:0x03d9, B:523:0x0400, B:743:0x0408, B:745:0x040f, B:747:0x0413, B:750:0x0418, B:755:0x0422, B:758:0x042a, B:526:0x0444, B:528:0x0448, B:530:0x0452, B:533:0x0460, B:536:0x0467, B:539:0x0471, B:718:0x047a, B:721:0x0482, B:543:0x049b, B:546:0x04a6, B:549:0x04ac, B:551:0x04b5, B:702:0x04c2, B:557:0x04d9, B:693:0x04f0, B:563:0x0504, B:566:0x050d, B:678:0x0516, B:680:0x051e, B:574:0x0537, B:622:0x0544, B:626:0x0550, B:628:0x0556, B:578:0x05f2, B:582:0x05f8, B:610:0x0606, B:585:0x061d, B:590:0x0626, B:592:0x062c, B:593:0x0642, B:596:0x0648, B:597:0x0655, B:616:0x0636, B:631:0x0565, B:644:0x0583, B:646:0x0587, B:647:0x058c, B:649:0x0597, B:650:0x05a0, B:652:0x05a4, B:653:0x05a9, B:655:0x05af, B:659:0x05bd, B:661:0x05c1, B:663:0x05c7, B:665:0x05cf, B:667:0x05d2, B:670:0x05dd, B:570:0x052a, B:694:0x04e3, B:784:0x03a8, B:787:0x03ab), top: B:165:0x039f }] */
    /* JADX WARN: Removed duplicated region for block: B:559:0x04e1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:563:0x0504 A[Catch: RemoteException -> 0x04f7, all -> 0x06e2, TRY_LEAVE, TryCatch #2 {all -> 0x06e2, blocks: (B:166:0x039f, B:171:0x03b3, B:176:0x03c1, B:179:0x03c7, B:181:0x03cb, B:185:0x03d9, B:523:0x0400, B:743:0x0408, B:745:0x040f, B:747:0x0413, B:750:0x0418, B:755:0x0422, B:758:0x042a, B:526:0x0444, B:528:0x0448, B:530:0x0452, B:533:0x0460, B:536:0x0467, B:539:0x0471, B:718:0x047a, B:721:0x0482, B:543:0x049b, B:546:0x04a6, B:549:0x04ac, B:551:0x04b5, B:702:0x04c2, B:557:0x04d9, B:693:0x04f0, B:563:0x0504, B:566:0x050d, B:678:0x0516, B:680:0x051e, B:574:0x0537, B:622:0x0544, B:626:0x0550, B:628:0x0556, B:578:0x05f2, B:582:0x05f8, B:610:0x0606, B:585:0x061d, B:590:0x0626, B:592:0x062c, B:593:0x0642, B:596:0x0648, B:597:0x0655, B:616:0x0636, B:631:0x0565, B:644:0x0583, B:646:0x0587, B:647:0x058c, B:649:0x0597, B:650:0x05a0, B:652:0x05a4, B:653:0x05a9, B:655:0x05af, B:659:0x05bd, B:661:0x05c1, B:663:0x05c7, B:665:0x05cf, B:667:0x05d2, B:670:0x05dd, B:570:0x052a, B:694:0x04e3, B:784:0x03a8, B:787:0x03ab), top: B:165:0x039f }] */
    /* JADX WARN: Removed duplicated region for block: B:568:0x0513  */
    /* JADX WARN: Removed duplicated region for block: B:573:0x0536  */
    /* JADX WARN: Removed duplicated region for block: B:581:0x05f6  */
    /* JADX WARN: Removed duplicated region for block: B:587:0x0621  */
    /* JADX WARN: Removed duplicated region for block: B:595:0x0646  */
    /* JADX WARN: Removed duplicated region for block: B:600:0x0662 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:607:0x0623  */
    /* JADX WARN: Removed duplicated region for block: B:617:0x063e  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:643:0x0581  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:677:0x0516 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:691:0x050c  */
    /* JADX WARN: Removed duplicated region for block: B:692:0x04f0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:696:0x04eb  */
    /* JADX WARN: Removed duplicated region for block: B:701:0x04c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:714:0x04a5  */
    /* JADX WARN: Removed duplicated region for block: B:717:0x047a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:742:0x0408 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:769:0x03ff  */
    /* JADX WARN: Removed duplicated region for block: B:771:0x03f5  */
    /* JADX WARN: Removed duplicated region for block: B:779:0x03bf  */
    /* JADX WARN: Removed duplicated region for block: B:797:0x0384  */
    /* JADX WARN: Removed duplicated region for block: B:800:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:801:0x02e0  */
    /* JADX WARN: Removed duplicated region for block: B:806:0x026e  */
    /* JADX WARN: Removed duplicated region for block: B:809:0x0211  */
    /* JADX WARN: Removed duplicated region for block: B:823:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:826:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:834:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x024a  */
    /* JADX WARN: Type inference failed for: r15v22 */
    /* JADX WARN: Type inference failed for: r15v24 */
    /* JADX WARN: Type inference failed for: r15v25 */
    /* JADX WARN: Type inference failed for: r1v166 */
    /* JADX WARN: Type inference failed for: r1v167 */
    /* JADX WARN: Type inference failed for: r1v178 */
    /* JADX WARN: Type inference failed for: r24v0 */
    /* JADX WARN: Type inference failed for: r24v2 */
    /* JADX WARN: Type inference failed for: r24v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r24v34 */
    /* JADX WARN: Type inference failed for: r24v35 */
    /* JADX WARN: Type inference failed for: r2v76 */
    /* JADX WARN: Type inference failed for: r2v77 */
    /* JADX WARN: Type inference failed for: r2v82 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2, types: [int] */
    /* JADX WARN: Type inference failed for: r4v42 */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v58 */
    /* JADX WARN: Type inference failed for: r9v59 */
    /* JADX WARN: Type inference failed for: r9v60 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void performTraversals() {
        boolean z;
        int width;
        int height;
        boolean z2;
        boolean z3;
        int i;
        android.graphics.Rect rect;
        boolean z4;
        int i2;
        boolean z5;
        int i3;
        int i4;
        int i5;
        android.view.WindowManager.LayoutParams layoutParams;
        int i6;
        boolean z6;
        int generationId;
        boolean z7;
        boolean z8;
        android.view.WindowManager.LayoutParams layoutParams2;
        android.graphics.Rect rect2;
        java.lang.Boolean bool;
        java.lang.Boolean bool2;
        java.lang.Boolean bool3;
        int i7;
        boolean z9;
        ?? r24;
        int i8;
        java.lang.String str;
        java.lang.Boolean bool4;
        android.view.WindowManager.LayoutParams layoutParams3;
        java.lang.String str2;
        boolean z10;
        boolean z11;
        boolean z12;
        boolean z13;
        int i9;
        android.graphics.Rect rect3;
        android.graphics.Rect rect4;
        boolean z14;
        android.graphics.Region region;
        boolean z15;
        int i10;
        int i11;
        boolean z16;
        int i12;
        android.graphics.Rect rect5;
        android.graphics.Region region2;
        android.graphics.Region region3;
        boolean isValid;
        long j;
        boolean z17;
        boolean z18;
        java.lang.Boolean bool5;
        boolean z19;
        java.lang.Boolean bool6;
        ?? r9;
        android.view.ThreadedRenderer threadedRenderer;
        boolean z20;
        boolean z21;
        java.lang.Boolean bool7;
        boolean z22;
        boolean z23;
        java.lang.Boolean bool8;
        boolean z24;
        boolean z25;
        boolean z26;
        boolean z27;
        boolean z28;
        boolean z29;
        boolean z30;
        boolean z31;
        ?? r2;
        ?? r15;
        java.lang.Boolean bool9;
        boolean z32;
        boolean z33;
        boolean z34;
        int i13;
        this.mLastPerformTraversalsSkipDrawReason = null;
        android.view.View view = this.mView;
        if (view == null || !this.mAdded) {
            this.mLastPerformTraversalsSkipDrawReason = view == null ? "no_host" : "not_added";
            return;
        }
        if (this.mNumPausedForSync > 0) {
            if (android.os.Trace.isTagEnabled(8L)) {
                android.os.Trace.instant(8L, android.text.TextUtils.formatSimple("performTraversals#mNumPausedForSync=%d", java.lang.Integer.valueOf(this.mNumPausedForSync)));
            }
            this.mLastPerformTraversalsSkipDrawReason = "paused_for_sync";
            return;
        }
        this.mIsInTraversal = true;
        this.mWillDrawSoon = true;
        android.view.WindowManager.LayoutParams layoutParams4 = this.mWindowAttributes;
        int hostVisibility = getHostVisibility();
        boolean z35 = !this.mFirst && (this.mViewVisibility != hostVisibility || this.mNewSurfaceNeeded || this.mAppVisibilityChanged);
        this.mAppVisibilityChanged = false;
        try {
            if (!this.mFirst) {
                if ((this.mViewVisibility == 0) != (hostVisibility == 0)) {
                    z = true;
                    boolean shouldOptimizeMeasure = shouldOptimizeMeasure(layoutParams4);
                    android.graphics.Rect rect6 = this.mWinFrame;
                    if (this.mFirst) {
                        width = rect6.width();
                        height = rect6.height();
                        if (width == this.mWidth && height == this.mHeight) {
                            z2 = false;
                        } else {
                            this.mFullRedrawNeeded = true;
                            this.mLayoutRequested = true;
                            z2 = true;
                        }
                    } else {
                        this.mFullRedrawNeeded = true;
                        this.mLayoutRequested = true;
                        android.content.res.Configuration configuration = getConfiguration();
                        if (shouldUseDisplaySize(layoutParams4)) {
                            android.graphics.Point point = new android.graphics.Point();
                            this.mDisplay.getRealSize(point);
                            width = point.x;
                            height = point.y;
                        } else if (layoutParams4.width == -2 || layoutParams4.height == -2) {
                            android.graphics.Rect windowBoundsInsetSystemBars = getWindowBoundsInsetSystemBars();
                            width = windowBoundsInsetSystemBars.width();
                            height = windowBoundsInsetSystemBars.height();
                        } else {
                            width = rect6.width();
                            height = rect6.height();
                        }
                        this.mAttachInfo.mUse32BitDrawingCache = true;
                        this.mAttachInfo.mWindowVisibility = hostVisibility;
                        this.mAttachInfo.mRecomputeGlobalAttributes = false;
                        this.mLastConfigurationFromResources.setTo(configuration);
                        this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                        if (this.mViewLayoutDirectionInitial == 2) {
                            view.setLayoutDirection(configuration.getLayoutDirection());
                        }
                        view.dispatchAttachedToWindow(this.mAttachInfo, 0);
                        this.mAttachInfo.mTreeObserver.dispatchOnWindowAttachedChange(true);
                        dispatchApplyInsets(view);
                        if (!this.mOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled() && this.mWindowlessBackKeyCallback == null) {
                            registerCompatOnBackInvokedCallback();
                        }
                        z2 = false;
                    }
                    if (z35) {
                        this.mAttachInfo.mWindowVisibility = hostVisibility;
                        view.dispatchWindowVisibilityChanged(hostVisibility);
                        this.mAttachInfo.mTreeObserver.dispatchOnWindowVisibilityChange(hostVisibility);
                        if (z) {
                            view.dispatchVisibilityAggregated(hostVisibility == 0);
                        }
                        if (hostVisibility != 0 || this.mNewSurfaceNeeded) {
                            endDragResizing();
                            destroyHardwareResources();
                        }
                        if (shouldEnableDvrr() && hostVisibility == 0) {
                            boostFrameRate(3000);
                        }
                    }
                    if (this.mAttachInfo.mWindowVisibility != 0) {
                        view.clearAccessibilityFocus();
                    }
                    getRunQueue().executeActions(this.mAttachInfo.mHandler);
                    if (this.mFirst) {
                        this.mAttachInfo.mInTouchMode = !this.mAddedTouchMode;
                        ensureTouchModeLocally(this.mAddedTouchMode);
                    }
                    z3 = !this.mLayoutRequested && (!this.mStopped || this.mReportNextDraw);
                    if (z3) {
                        i = -2;
                        rect = rect6;
                        z4 = false;
                        i2 = hostVisibility;
                        z5 = z2;
                        i3 = width;
                        i4 = height;
                    } else {
                        if (this.mFirst || !(layoutParams4.width == -2 || layoutParams4.height == -2)) {
                            z34 = z2;
                            i13 = width;
                            i4 = height;
                        } else if (shouldUseDisplaySize(layoutParams4)) {
                            android.graphics.Point point2 = new android.graphics.Point();
                            this.mDisplay.getRealSize(point2);
                            int i14 = point2.x;
                            int i15 = point2.y;
                            i13 = i14;
                            z34 = true;
                            i4 = i15;
                        } else {
                            android.graphics.Rect windowBoundsInsetSystemBars2 = getWindowBoundsInsetSystemBars();
                            int width2 = windowBoundsInsetSystemBars2.width();
                            int height2 = windowBoundsInsetSystemBars2.height();
                            i13 = width2;
                            z34 = true;
                            i4 = height2;
                        }
                        i = -2;
                        rect = rect6;
                        z4 = false;
                        i2 = hostVisibility;
                        z5 = measureHierarchy(view, layoutParams4, this.mView.getContext().getResources(), i13, i4, shouldOptimizeMeasure) | z34;
                        i3 = i13;
                    }
                    android.view.WindowManager.LayoutParams layoutParams5 = !collectViewAttributes() ? layoutParams4 : null;
                    if (this.mAttachInfo.mForceReportNewAttributes) {
                        this.mAttachInfo.mForceReportNewAttributes = z4;
                        layoutParams5 = layoutParams4;
                    }
                    if (!this.mFirst || this.mAttachInfo.mViewVisibilityChanged) {
                        this.mAttachInfo.mViewVisibilityChanged = z4;
                        i5 = this.mSoftInputMode & 240;
                        if (i5 == 0) {
                            int size = this.mAttachInfo.mScrollContainers.size();
                            for (?? r4 = z4; r4 < size; r4++) {
                                if (this.mAttachInfo.mScrollContainers.get(r4).isShown()) {
                                    i5 = 16;
                                }
                            }
                            if (i5 == 0) {
                                i5 = 32;
                            }
                            if ((layoutParams4.softInputMode & 240) != i5) {
                                layoutParams4.softInputMode = (layoutParams4.softInputMode & (-241)) | i5;
                                layoutParams = layoutParams4;
                                if (this.mApplyInsetsRequested) {
                                    dispatchApplyInsets(view);
                                    if (this.mLayoutRequested) {
                                        i6 = i3;
                                        z5 |= measureHierarchy(view, layoutParams4, this.mView.getContext().getResources(), i3, i4, shouldOptimizeMeasure);
                                    } else {
                                        i6 = i3;
                                    }
                                } else {
                                    i6 = i3;
                                }
                                if (z3) {
                                    this.mLayoutRequested = z4;
                                }
                                boolean z36 = ((this.mDragResizing || !this.mPendingDragResizing) ? z4 : true) | ((z3 || !z5 || (this.mWidth == view.getMeasuredWidth() && this.mHeight == view.getMeasuredHeight() && ((layoutParams4.width != i || rect.width() >= i6 || rect.width() == this.mWidth) && (layoutParams4.height != i || rect.height() >= i4 || rect.height() == this.mHeight)))) ? z4 : true);
                                z6 = (!this.mAttachInfo.mTreeObserver.hasComputeInternalInsetsListeners() || this.mAttachInfo.mHasNonEmptyGivenInternalInsets) ? true : z4;
                                generationId = this.mSurface.getGenerationId();
                                int i16 = i2;
                                z7 = i16 == 0 ? true : z4;
                                z8 = this.mWindowAttributesChanged;
                                if (z8) {
                                    this.mWindowAttributesChanged = z4;
                                    layoutParams2 = layoutParams4;
                                } else {
                                    layoutParams2 = layoutParams;
                                }
                                if (layoutParams2 != null) {
                                    if ((view.mPrivateFlags & 512) != 0 && !android.graphics.PixelFormat.formatHasAlpha(layoutParams2.format)) {
                                        layoutParams2.format = -3;
                                    }
                                    adjustLayoutParamsForCompatibility(layoutParams2);
                                    controlInsetsForCompatibility(layoutParams2);
                                    if (this.mDispatchedSystemBarAppearance != layoutParams2.insetsFlags.appearance) {
                                        this.mDispatchedSystemBarAppearance = layoutParams2.insetsFlags.appearance;
                                        this.mView.onSystemBarAppearanceChanged(this.mDispatchedSystemBarAppearance);
                                    }
                                }
                                if (!this.mFirst || z36 || z35 || layoutParams2 != null) {
                                    rect2 = rect;
                                } else {
                                    if (!this.mForceNextWindowRelayout) {
                                        rect2 = rect;
                                        maybeHandleWindowMove(rect2);
                                        i7 = i16;
                                        i8 = z4;
                                        int i17 = i8;
                                        z9 = i17 == true ? 1 : 0;
                                        bool3 = z9 ? 1 : 0;
                                        bool2 = bool3;
                                        bool = bool2;
                                        layoutParams3 = layoutParams4;
                                        str = null;
                                        bool4 = bool;
                                        r24 = i17;
                                        if (this.mViewMeasureDeferred) {
                                            performMeasure(android.view.View.MeasureSpec.makeMeasureSpec(rect2.width(), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(rect2.height(), 1073741824));
                                        }
                                        if (this.mRelayoutRequested && this.mCheckIfCanDraw) {
                                            try {
                                                r24 = this.mWindowSession.cancelDraw(this.mWindow);
                                                str2 = "wm_sync";
                                                z10 = r24;
                                            } catch (android.os.RemoteException e) {
                                                str2 = str;
                                                z10 = r24;
                                            }
                                        } else {
                                            str2 = str;
                                            z10 = r24;
                                        }
                                        if (bool == null || bool3 != null || bool2 != null || z8 || this.mChildBoundingInsetsChanged) {
                                            prepareSurfaces();
                                            this.mChildBoundingInsetsChanged = false;
                                        }
                                        z11 = !z3 && (!this.mStopped || this.mReportNextDraw);
                                        z12 = !z11 || this.mAttachInfo.mRecomputeGlobalAttributes;
                                        if (z11) {
                                            performLayout(layoutParams3, this.mWidth, this.mHeight);
                                            if ((view.mPrivateFlags & 512) != 0) {
                                                view.getLocationInWindow(this.mTmpLocation);
                                                this.mTransparentRegion.set(this.mTmpLocation[0], this.mTmpLocation[1], (this.mTmpLocation[0] + view.mRight) - view.mLeft, (this.mTmpLocation[1] + view.mBottom) - view.mTop);
                                                view.gatherTransparentRegion(this.mTransparentRegion);
                                                if (getAccessibilityFocusedRect(this.mAttachInfo.mTmpInvalRect)) {
                                                    view.applyDrawableToTransparentRegion(getAccessibilityFocusedDrawable(), this.mTransparentRegion);
                                                }
                                                if (this.mTranslator != null) {
                                                    this.mTranslator.translateRegionInWindowToScreen(this.mTransparentRegion);
                                                }
                                                if (!this.mTransparentRegion.equals(this.mPreviousTransparentRegion)) {
                                                    this.mPreviousTransparentRegion.set(this.mTransparentRegion);
                                                    this.mFullRedrawNeeded = true;
                                                    android.view.SurfaceControl surfaceControl = getSurfaceControl();
                                                    if (surfaceControl.isValid()) {
                                                        this.mTransaction.setTransparentRegionHint(surfaceControl, this.mTransparentRegion).apply();
                                                    }
                                                }
                                            }
                                        }
                                        if (bool2 == null) {
                                            notifySurfaceCreated(this.mTransaction);
                                            z13 = true;
                                        } else if (bool3 != null) {
                                            notifySurfaceReplaced(this.mTransaction);
                                            z13 = true;
                                        } else {
                                            if (bool4 != null) {
                                                notifySurfaceDestroyed();
                                            }
                                            z13 = false;
                                        }
                                        if (z13) {
                                            applyTransactionOnDraw(this.mTransaction);
                                        }
                                        if (z12) {
                                            this.mAttachInfo.mRecomputeGlobalAttributes = false;
                                            this.mAttachInfo.mTreeObserver.dispatchOnGlobalLayout();
                                        }
                                        if (z6) {
                                            i9 = 3;
                                            rect3 = null;
                                            rect4 = null;
                                            z14 = false;
                                            region = null;
                                        } else {
                                            android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo = this.mAttachInfo.mGivenInternalInsets;
                                            internalInsetsInfo.reset();
                                            this.mAttachInfo.mTreeObserver.dispatchOnComputeInternalInsets(internalInsetsInfo);
                                            this.mAttachInfo.mHasNonEmptyGivenInternalInsets = !internalInsetsInfo.isEmpty();
                                            if (z4 || !this.mLastGivenInsets.equals(internalInsetsInfo)) {
                                                this.mLastGivenInsets.set(internalInsetsInfo);
                                                if (this.mTranslator != null) {
                                                    rect3 = this.mTranslator.getTranslatedContentInsets(internalInsetsInfo.contentInsets);
                                                    rect4 = this.mTranslator.getTranslatedVisibleInsets(internalInsetsInfo.visibleInsets);
                                                    region3 = this.mTranslator.getTranslatedTouchableArea(internalInsetsInfo.touchableRegion);
                                                } else {
                                                    rect3 = internalInsetsInfo.contentInsets;
                                                    rect4 = internalInsetsInfo.visibleInsets;
                                                    region3 = internalInsetsInfo.touchableRegion;
                                                }
                                                region = region3;
                                                z14 = true;
                                            } else {
                                                rect3 = null;
                                                rect4 = null;
                                                z14 = false;
                                                region = null;
                                            }
                                            i9 = internalInsetsInfo.mTouchableInsets;
                                        }
                                        if (!z14 && !(java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                                            if (this.mTouchableRegion != null) {
                                                if (this.mPreviousTouchableRegion == null) {
                                                    this.mPreviousTouchableRegion = new android.graphics.Region();
                                                }
                                                this.mPreviousTouchableRegion.set(this.mTouchableRegion);
                                                if (i9 != 3) {
                                                    android.util.Log.e(this.mTag, "Setting touchableInsetMode to non TOUCHABLE_INSETS_REGION from OnComputeInternalInsets, while also using setTouchableRegion causes setTouchableRegion to be ignored");
                                                }
                                            } else {
                                                this.mPreviousTouchableRegion = null;
                                            }
                                            if (rect3 == null) {
                                                i12 = 0;
                                                rect5 = new android.graphics.Rect(0, 0, 0, 0);
                                            } else {
                                                i12 = 0;
                                                rect5 = rect3;
                                            }
                                            android.graphics.Rect rect7 = rect4 == null ? new android.graphics.Rect(i12, i12, i12, i12) : rect4;
                                            if (region == null) {
                                                region2 = this.mTouchableRegion;
                                            } else {
                                                if (region != null && this.mTouchableRegion != null) {
                                                    region.op(region, this.mTouchableRegion, android.graphics.Region.Op.UNION);
                                                }
                                                region2 = region;
                                            }
                                            try {
                                                this.mWindowSession.setInsets(this.mWindow, i9, rect5, rect7, region2);
                                            } catch (android.os.RemoteException e2) {
                                                throw e2.rethrowFromSystemServer();
                                            }
                                        } else if (this.mTouchableRegion == null && this.mPreviousTouchableRegion != null) {
                                            this.mPreviousTouchableRegion = null;
                                            try {
                                                this.mWindowSession.clearTouchableRegion(this.mWindow);
                                            } catch (android.os.RemoteException e3) {
                                                throw e3.rethrowFromSystemServer();
                                            }
                                        }
                                        if (this.mFirst) {
                                            if (!sAlwaysAssignFocus && isInTouchMode()) {
                                                android.view.View findFocus = this.mView.findFocus();
                                                if ((findFocus instanceof android.view.ViewGroup) && ((android.view.ViewGroup) findFocus).getDescendantFocusability() == 262144) {
                                                    findFocus.restoreDefaultFocus();
                                                }
                                            } else if (this.mView != null && !this.mView.hasFocus()) {
                                                this.mView.restoreDefaultFocus();
                                            }
                                            if (shouldEnableDvrr()) {
                                                boostFrameRate(3000);
                                            }
                                        }
                                        if ((!z35 || this.mFirst) && z7) {
                                            maybeFireAccessibilityWindowStateChangedEvent();
                                        }
                                        this.mFirst = false;
                                        this.mWillDrawSoon = false;
                                        this.mNewSurfaceNeeded = false;
                                        this.mViewVisibility = i7;
                                        this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                        if ((i8 & 1) != 0) {
                                            reportNextDraw("first_relayout");
                                        }
                                        this.mCheckIfCanDraw = !z9 || z10;
                                        boolean dispatchOnPreDraw = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                        z15 = !dispatchOnPreDraw || (z10 && this.mDrewOnceForSync);
                                        if (!z15) {
                                            if (this.mActiveSurfaceSyncGroup != null) {
                                                z16 = true;
                                                this.mSyncBuffer = true;
                                            } else {
                                                z16 = true;
                                            }
                                            createSyncIfNeeded();
                                            notifyDrawStarted(isInWMSRequestedSync());
                                            this.mDrewOnceForSync = z16;
                                            if (this.mActiveSurfaceSyncGroup != null && this.mSyncBuffer) {
                                                updateSyncInProgressCount(this.mActiveSurfaceSyncGroup);
                                                safeguardOverlappingSyncs(this.mActiveSurfaceSyncGroup);
                                            }
                                        }
                                        if (z7) {
                                            if (this.mLastTraversalWasVisible) {
                                                logAndTrace("Not drawing due to not visible");
                                            }
                                            this.mLastPerformTraversalsSkipDrawReason = "view_not_visible";
                                            if (this.mPendingTransitions != null && this.mPendingTransitions.size() > 0) {
                                                for (int i18 = 0; i18 < this.mPendingTransitions.size(); i18++) {
                                                    this.mPendingTransitions.get(i18).endChangingAnimations();
                                                }
                                                this.mPendingTransitions.clear();
                                            }
                                            handleSyncRequestWhenNoAsyncDraw(this.mActiveSurfaceSyncGroup, this.mHasPendingTransactions, this.mPendingTransaction, "view not visible");
                                        } else if (z15) {
                                            if (!this.mWasLastDrawCanceled) {
                                                logAndTrace("Canceling draw. cancelDueToPreDrawListener=" + dispatchOnPreDraw + " cancelDueToSync=" + (z10 && this.mDrewOnceForSync));
                                            }
                                            this.mLastPerformTraversalsSkipDrawReason = dispatchOnPreDraw ? "predraw_" + this.mAttachInfo.mTreeObserver.getLastDispatchOnPreDrawCanceledReason() : "cancel_" + str2;
                                            scheduleTraversals();
                                        } else {
                                            if (this.mWasLastDrawCanceled) {
                                                logAndTrace("Draw frame after cancel");
                                            }
                                            if (!this.mLastTraversalWasVisible) {
                                                logAndTrace("Start draw after previous draw not visible");
                                            }
                                            if (this.mPendingTransitions != null && this.mPendingTransitions.size() > 0) {
                                                for (int i19 = 0; i19 < this.mPendingTransitions.size(); i19++) {
                                                    this.mPendingTransitions.get(i19).startChangingAnimations();
                                                }
                                                this.mPendingTransitions.clear();
                                            }
                                            if (!performDraw(this.mActiveSurfaceSyncGroup)) {
                                                handleSyncRequestWhenNoAsyncDraw(this.mActiveSurfaceSyncGroup, this.mHasPendingTransactions, this.mPendingTransaction, this.mLastPerformDrawSkippedReason);
                                            }
                                        }
                                        this.mWasLastDrawCanceled = z15;
                                        this.mLastTraversalWasVisible = z7;
                                        if (this.mAttachInfo.mContentCaptureEvents != null) {
                                            notifyContentCaptureEvents();
                                        }
                                        this.mIsInTraversal = false;
                                        this.mRelayoutRequested = false;
                                        if (!z15) {
                                            this.mReportNextDraw = false;
                                            this.mLastReportNextDrawReason = null;
                                            this.mActiveSurfaceSyncGroup = null;
                                            this.mHasPendingTransactions = false;
                                            this.mSyncBuffer = false;
                                            if (isInWMSRequestedSync()) {
                                                this.mWmsRequestSyncGroup.markSyncReady();
                                                this.mWmsRequestSyncGroup = null;
                                                this.mWmsRequestSyncGroupState = 0;
                                            }
                                        }
                                        setPreferredFrameRate(this.mPreferredFrameRate);
                                        setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                        this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                        this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                        if (this.mFrameRateCategoryLowCount <= 0) {
                                            i10 = 1;
                                            i11 = this.mFrameRateCategoryLowCount - 1;
                                        } else {
                                            i10 = 1;
                                            i11 = this.mFrameRateCategoryLowCount;
                                        }
                                        this.mFrameRateCategoryLowCount = i11;
                                        this.mPreferredFrameRateCategory = i10;
                                        this.mPreferredFrameRate = -1.0f;
                                        this.mIsFrameRateConflicted = false;
                                        return;
                                    }
                                    rect2 = rect;
                                }
                                if (android.os.Trace.isTagEnabled(8L)) {
                                    java.lang.String str3 = this.mTag;
                                    java.lang.Boolean valueOf = java.lang.Boolean.valueOf(this.mFirst);
                                    java.lang.Boolean valueOf2 = java.lang.Boolean.valueOf(z36);
                                    bool3 = java.lang.Boolean.valueOf(z35);
                                    bool2 = java.lang.Boolean.valueOf(layoutParams2 != null ? true : z4);
                                    layoutParams3 = layoutParams4;
                                    android.os.Trace.traceBegin(8L, android.text.TextUtils.formatSimple("%s-relayoutWindow#first=%b/resize=%b/vis=%b/params=%b/force=%b", str3, valueOf, valueOf2, bool3, bool2, java.lang.Boolean.valueOf(this.mForceNextWindowRelayout)));
                                } else {
                                    layoutParams3 = layoutParams4;
                                }
                                this.mForceNextWindowRelayout = false;
                                if (this.mSurfaceHolder != null) {
                                    this.mSurfaceHolder.mSurfaceLock.lock();
                                    this.mDrawingAllowed = true;
                                }
                                isValid = this.mSurface.isValid();
                                if (!this.mFirst && !z35) {
                                    z25 = z3;
                                    int relayoutWindow = relayoutWindow(layoutParams2, i16, z6);
                                    boolean z37 = (relayoutWindow & 16) == 16;
                                    z26 = this.mPendingDragResizing;
                                    boolean z38 = z25;
                                    if (this.mSyncSeqId > this.mLastSyncSeqId) {
                                        this.mLastSyncSeqId = this.mSyncSeqId;
                                        reportNextDraw("relayout");
                                        this.mSyncBuffer = true;
                                        if (!z37) {
                                            try {
                                                this.mDrewOnceForSync = false;
                                            } catch (android.os.RemoteException e4) {
                                                i7 = i16;
                                                i8 = relayoutWindow;
                                                str = "relayout";
                                                z23 = z37;
                                                j = 8;
                                                z17 = false;
                                                z24 = false;
                                                z18 = false;
                                                bool8 = null;
                                                bool5 = null;
                                                z19 = false;
                                                z9 = true;
                                                bool2 = null;
                                                z22 = z24;
                                                bool7 = bool8;
                                                z21 = z23;
                                                if (android.os.Trace.isTagEnabled(j)) {
                                                }
                                                bool3 = bool5;
                                                r9 = z22;
                                                bool6 = bool7;
                                                boolean z39 = z21;
                                                this.mAttachInfo.mWindowLeft = rect2.left;
                                                this.mAttachInfo.mWindowTop = rect2.top;
                                                if (this.mWidth == rect2.width()) {
                                                }
                                                this.mWidth = rect2.width();
                                                this.mHeight = rect2.height();
                                                if (this.mSurfaceHolder != null) {
                                                }
                                                threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                if (threadedRenderer != null) {
                                                }
                                                if (this.mStopped) {
                                                }
                                                int rootMeasureSpec = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                                int rootMeasureSpec2 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                                performMeasure(rootMeasureSpec, rootMeasureSpec2);
                                                int measuredWidth = view.getMeasuredWidth();
                                                int measuredHeight = view.getMeasuredHeight();
                                                if (layoutParams3.horizontalWeight > 0.0f) {
                                                }
                                                if (layoutParams3.verticalWeight > 0.0f) {
                                                }
                                                if (z20) {
                                                }
                                                z3 = true;
                                                z4 = z6;
                                                bool4 = bool6;
                                                r24 = z39;
                                                if (this.mViewMeasureDeferred) {
                                                }
                                                if (this.mRelayoutRequested) {
                                                }
                                                str2 = str;
                                                z10 = r24;
                                                if (bool == null) {
                                                }
                                                prepareSurfaces();
                                                this.mChildBoundingInsetsChanged = false;
                                                if (z3) {
                                                }
                                                if (z11) {
                                                }
                                                if (z11) {
                                                }
                                                if (bool2 == null) {
                                                }
                                                if (z13) {
                                                }
                                                if (z12) {
                                                }
                                                if (z6) {
                                                }
                                                if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                                }
                                                if (this.mFirst) {
                                                }
                                                if ((!z35 || this.mFirst) && z7) {
                                                }
                                                this.mFirst = false;
                                                this.mWillDrawSoon = false;
                                                this.mNewSurfaceNeeded = false;
                                                this.mViewVisibility = i7;
                                                this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                                if ((i8 & 1) != 0) {
                                                }
                                                this.mCheckIfCanDraw = !z9 || z10;
                                                boolean dispatchOnPreDraw2 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                if (dispatchOnPreDraw2) {
                                                }
                                                if (!z15) {
                                                }
                                                if (z7) {
                                                }
                                                this.mWasLastDrawCanceled = z15;
                                                this.mLastTraversalWasVisible = z7;
                                                if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                }
                                                this.mIsInTraversal = false;
                                                this.mRelayoutRequested = false;
                                                if (!z15) {
                                                }
                                                setPreferredFrameRate(this.mPreferredFrameRate);
                                                setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                                this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                                this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                                if (this.mFrameRateCategoryLowCount <= 0) {
                                                }
                                                this.mFrameRateCategoryLowCount = i11;
                                                this.mPreferredFrameRateCategory = i10;
                                                this.mPreferredFrameRate = -1.0f;
                                                this.mIsFrameRateConflicted = false;
                                                return;
                                            }
                                        }
                                        z27 = true;
                                    } else {
                                        z27 = false;
                                    }
                                    i8 = relayoutWindow;
                                    boolean z40 = (relayoutWindow & 2) == 2;
                                    if (this.mSurfaceControl.isValid()) {
                                        try {
                                            updateOpacity(this.mWindowAttributes, z26, z40);
                                            if (z40 && this.mDisplayDecorationCached) {
                                                updateDisplayDecoration();
                                            }
                                            if (z40) {
                                                str = "relayout";
                                                if (this.mWindowAttributes.type == 2000) {
                                                    try {
                                                        z28 = z37;
                                                    } catch (android.os.RemoteException e5) {
                                                        z28 = z37;
                                                        i7 = i16;
                                                        boolean z41 = z28;
                                                        z9 = z27;
                                                        j = 8;
                                                        z17 = false;
                                                        z24 = false;
                                                        z18 = false;
                                                        bool8 = null;
                                                        bool5 = null;
                                                        z19 = false;
                                                        z23 = z41;
                                                        bool2 = null;
                                                        z22 = z24;
                                                        bool7 = bool8;
                                                        z21 = z23;
                                                        if (android.os.Trace.isTagEnabled(j)) {
                                                        }
                                                        bool3 = bool5;
                                                        r9 = z22;
                                                        bool6 = bool7;
                                                        boolean z392 = z21;
                                                        this.mAttachInfo.mWindowLeft = rect2.left;
                                                        this.mAttachInfo.mWindowTop = rect2.top;
                                                        if (this.mWidth == rect2.width()) {
                                                        }
                                                        this.mWidth = rect2.width();
                                                        this.mHeight = rect2.height();
                                                        if (this.mSurfaceHolder != null) {
                                                        }
                                                        threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                        if (threadedRenderer != null) {
                                                        }
                                                        if (this.mStopped) {
                                                        }
                                                        int rootMeasureSpec3 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                                        int rootMeasureSpec22 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                                        performMeasure(rootMeasureSpec3, rootMeasureSpec22);
                                                        int measuredWidth2 = view.getMeasuredWidth();
                                                        int measuredHeight2 = view.getMeasuredHeight();
                                                        if (layoutParams3.horizontalWeight > 0.0f) {
                                                        }
                                                        if (layoutParams3.verticalWeight > 0.0f) {
                                                        }
                                                        if (z20) {
                                                        }
                                                        z3 = true;
                                                        z4 = z6;
                                                        bool4 = bool6;
                                                        r24 = z392;
                                                        if (this.mViewMeasureDeferred) {
                                                        }
                                                        if (this.mRelayoutRequested) {
                                                        }
                                                        str2 = str;
                                                        z10 = r24;
                                                        if (bool == null) {
                                                        }
                                                        prepareSurfaces();
                                                        this.mChildBoundingInsetsChanged = false;
                                                        if (z3) {
                                                        }
                                                        if (z11) {
                                                        }
                                                        if (z11) {
                                                        }
                                                        if (bool2 == null) {
                                                        }
                                                        if (z13) {
                                                        }
                                                        if (z12) {
                                                        }
                                                        if (z6) {
                                                        }
                                                        if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                                        }
                                                        if (this.mFirst) {
                                                        }
                                                        if ((!z35 || this.mFirst) && z7) {
                                                        }
                                                        this.mFirst = false;
                                                        this.mWillDrawSoon = false;
                                                        this.mNewSurfaceNeeded = false;
                                                        this.mViewVisibility = i7;
                                                        this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                                        if ((i8 & 1) != 0) {
                                                        }
                                                        this.mCheckIfCanDraw = !z9 || z10;
                                                        boolean dispatchOnPreDraw22 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                        if (dispatchOnPreDraw22) {
                                                        }
                                                        if (!z15) {
                                                        }
                                                        if (z7) {
                                                        }
                                                        this.mWasLastDrawCanceled = z15;
                                                        this.mLastTraversalWasVisible = z7;
                                                        if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                        }
                                                        this.mIsInTraversal = false;
                                                        this.mRelayoutRequested = false;
                                                        if (!z15) {
                                                        }
                                                        setPreferredFrameRate(this.mPreferredFrameRate);
                                                        setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                                        this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                                        this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                                        if (this.mFrameRateCategoryLowCount <= 0) {
                                                        }
                                                        this.mFrameRateCategoryLowCount = i11;
                                                        this.mPreferredFrameRateCategory = i10;
                                                        this.mPreferredFrameRate = -1.0f;
                                                        this.mIsFrameRateConflicted = false;
                                                        return;
                                                    }
                                                    try {
                                                        this.mTransaction.setDefaultFrameRateCompatibility(this.mSurfaceControl, 101).apply();
                                                        z28 = z28;
                                                    } catch (android.os.RemoteException e6) {
                                                        i7 = i16;
                                                        boolean z412 = z28;
                                                        z9 = z27;
                                                        j = 8;
                                                        z17 = false;
                                                        z24 = false;
                                                        z18 = false;
                                                        bool8 = null;
                                                        bool5 = null;
                                                        z19 = false;
                                                        z23 = z412;
                                                        bool2 = null;
                                                        z22 = z24;
                                                        bool7 = bool8;
                                                        z21 = z23;
                                                        if (android.os.Trace.isTagEnabled(j)) {
                                                        }
                                                        bool3 = bool5;
                                                        r9 = z22;
                                                        bool6 = bool7;
                                                        boolean z3922 = z21;
                                                        this.mAttachInfo.mWindowLeft = rect2.left;
                                                        this.mAttachInfo.mWindowTop = rect2.top;
                                                        if (this.mWidth == rect2.width()) {
                                                        }
                                                        this.mWidth = rect2.width();
                                                        this.mHeight = rect2.height();
                                                        if (this.mSurfaceHolder != null) {
                                                        }
                                                        threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                        if (threadedRenderer != null) {
                                                        }
                                                        if (this.mStopped) {
                                                        }
                                                        int rootMeasureSpec32 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                                        int rootMeasureSpec222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                                        performMeasure(rootMeasureSpec32, rootMeasureSpec222);
                                                        int measuredWidth22 = view.getMeasuredWidth();
                                                        int measuredHeight22 = view.getMeasuredHeight();
                                                        if (layoutParams3.horizontalWeight > 0.0f) {
                                                        }
                                                        if (layoutParams3.verticalWeight > 0.0f) {
                                                        }
                                                        if (z20) {
                                                        }
                                                        z3 = true;
                                                        z4 = z6;
                                                        bool4 = bool6;
                                                        r24 = z3922;
                                                        if (this.mViewMeasureDeferred) {
                                                        }
                                                        if (this.mRelayoutRequested) {
                                                        }
                                                        str2 = str;
                                                        z10 = r24;
                                                        if (bool == null) {
                                                        }
                                                        prepareSurfaces();
                                                        this.mChildBoundingInsetsChanged = false;
                                                        if (z3) {
                                                        }
                                                        if (z11) {
                                                        }
                                                        if (z11) {
                                                        }
                                                        if (bool2 == null) {
                                                        }
                                                        if (z13) {
                                                        }
                                                        if (z12) {
                                                        }
                                                        if (z6) {
                                                        }
                                                        if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                                        }
                                                        if (this.mFirst) {
                                                        }
                                                        if ((!z35 || this.mFirst) && z7) {
                                                        }
                                                        this.mFirst = false;
                                                        this.mWillDrawSoon = false;
                                                        this.mNewSurfaceNeeded = false;
                                                        this.mViewVisibility = i7;
                                                        this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                                        if ((i8 & 1) != 0) {
                                                        }
                                                        this.mCheckIfCanDraw = !z9 || z10;
                                                        boolean dispatchOnPreDraw222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                        if (dispatchOnPreDraw222) {
                                                        }
                                                        if (!z15) {
                                                        }
                                                        if (z7) {
                                                        }
                                                        this.mWasLastDrawCanceled = z15;
                                                        this.mLastTraversalWasVisible = z7;
                                                        if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                        }
                                                        this.mIsInTraversal = false;
                                                        this.mRelayoutRequested = false;
                                                        if (!z15) {
                                                        }
                                                        setPreferredFrameRate(this.mPreferredFrameRate);
                                                        setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                                        this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                                        this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                                        if (this.mFrameRateCategoryLowCount <= 0) {
                                                        }
                                                        this.mFrameRateCategoryLowCount = i11;
                                                        this.mPreferredFrameRateCategory = i10;
                                                        this.mPreferredFrameRate = -1.0f;
                                                        this.mIsFrameRateConflicted = false;
                                                        return;
                                                    }
                                                }
                                            } else {
                                                str = "relayout";
                                            }
                                            z28 = z37;
                                        } catch (android.os.RemoteException e7) {
                                            str = "relayout";
                                        }
                                    } else {
                                        str = "relayout";
                                        z28 = z37;
                                    }
                                    if (this.mRelayoutRequested || this.mPendingMergedConfiguration.equals(this.mLastReportedMergedConfiguration)) {
                                        z29 = false;
                                    } else {
                                        performConfigurationChange(new android.util.MergedConfiguration(this.mPendingMergedConfiguration), !this.mFirst, -1);
                                        z29 = true;
                                    }
                                    boolean z42 = this.mUpdateSurfaceNeeded;
                                    this.mUpdateSurfaceNeeded = false;
                                    z19 = z29;
                                    if (this.mLastSurfaceSize.equals(this.mSurfaceSize)) {
                                        z9 = z27;
                                        z30 = false;
                                    } else {
                                        try {
                                            z9 = z27;
                                        } catch (android.os.RemoteException e8) {
                                            z9 = z27;
                                        }
                                        try {
                                            this.mLastSurfaceSize.set(this.mSurfaceSize.x, this.mSurfaceSize.y);
                                            z30 = true;
                                        } catch (android.os.RemoteException e9) {
                                            i7 = i16;
                                            j = 8;
                                            z17 = false;
                                            boolean z43 = true;
                                            z18 = false;
                                            bool8 = null;
                                            bool5 = null;
                                            z24 = z43;
                                            z23 = z28;
                                            bool2 = null;
                                            z22 = z24;
                                            bool7 = bool8;
                                            z21 = z23;
                                            if (android.os.Trace.isTagEnabled(j)) {
                                            }
                                            bool3 = bool5;
                                            r9 = z22;
                                            bool6 = bool7;
                                            boolean z39222 = z21;
                                            this.mAttachInfo.mWindowLeft = rect2.left;
                                            this.mAttachInfo.mWindowTop = rect2.top;
                                            if (this.mWidth == rect2.width()) {
                                            }
                                            this.mWidth = rect2.width();
                                            this.mHeight = rect2.height();
                                            if (this.mSurfaceHolder != null) {
                                            }
                                            threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                            if (threadedRenderer != null) {
                                            }
                                            if (this.mStopped) {
                                            }
                                            int rootMeasureSpec322 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                            int rootMeasureSpec2222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                            performMeasure(rootMeasureSpec322, rootMeasureSpec2222);
                                            int measuredWidth222 = view.getMeasuredWidth();
                                            int measuredHeight222 = view.getMeasuredHeight();
                                            if (layoutParams3.horizontalWeight > 0.0f) {
                                            }
                                            if (layoutParams3.verticalWeight > 0.0f) {
                                            }
                                            if (z20) {
                                            }
                                            z3 = true;
                                            z4 = z6;
                                            bool4 = bool6;
                                            r24 = z39222;
                                            if (this.mViewMeasureDeferred) {
                                            }
                                            if (this.mRelayoutRequested) {
                                            }
                                            str2 = str;
                                            z10 = r24;
                                            if (bool == null) {
                                            }
                                            prepareSurfaces();
                                            this.mChildBoundingInsetsChanged = false;
                                            if (z3) {
                                            }
                                            if (z11) {
                                            }
                                            if (z11) {
                                            }
                                            if (bool2 == null) {
                                            }
                                            if (z13) {
                                            }
                                            if (z12) {
                                            }
                                            if (z6) {
                                            }
                                            if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                            }
                                            if (this.mFirst) {
                                            }
                                            if ((!z35 || this.mFirst) && z7) {
                                            }
                                            this.mFirst = false;
                                            this.mWillDrawSoon = false;
                                            this.mNewSurfaceNeeded = false;
                                            this.mViewVisibility = i7;
                                            this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                            if ((i8 & 1) != 0) {
                                            }
                                            this.mCheckIfCanDraw = !z9 || z10;
                                            boolean dispatchOnPreDraw2222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                            if (dispatchOnPreDraw2222) {
                                            }
                                            if (!z15) {
                                            }
                                            if (z7) {
                                            }
                                            this.mWasLastDrawCanceled = z15;
                                            this.mLastTraversalWasVisible = z7;
                                            if (this.mAttachInfo.mContentCaptureEvents != null) {
                                            }
                                            this.mIsInTraversal = false;
                                            this.mRelayoutRequested = false;
                                            if (!z15) {
                                            }
                                            setPreferredFrameRate(this.mPreferredFrameRate);
                                            setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                            this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                            this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                            if (this.mFrameRateCategoryLowCount <= 0) {
                                            }
                                            this.mFrameRateCategoryLowCount = i11;
                                            this.mPreferredFrameRateCategory = i10;
                                            this.mPreferredFrameRate = -1.0f;
                                            this.mIsFrameRateConflicted = false;
                                            return;
                                        }
                                    }
                                    z31 = this.mPendingAlwaysConsumeSystemBars != this.mAttachInfo.mAlwaysConsumeSystemBars;
                                    i7 = i16;
                                    updateColorModeIfNeeded(layoutParams3.getColorMode(), layoutParams3.getDesiredHdrHeadroom());
                                    if (!isValid) {
                                        if (this.mSurface.isValid()) {
                                            r2 = 1;
                                            if (isValid) {
                                                try {
                                                } catch (android.os.RemoteException e10) {
                                                    bool2 = r2;
                                                    j = 8;
                                                    z17 = false;
                                                    z18 = false;
                                                    java.lang.Boolean bool10 = null;
                                                    bool5 = null;
                                                    z22 = z30;
                                                    bool7 = bool10;
                                                    z21 = z28;
                                                    if (android.os.Trace.isTagEnabled(j)) {
                                                    }
                                                    bool3 = bool5;
                                                    r9 = z22;
                                                    bool6 = bool7;
                                                    boolean z392222 = z21;
                                                    this.mAttachInfo.mWindowLeft = rect2.left;
                                                    this.mAttachInfo.mWindowTop = rect2.top;
                                                    if (this.mWidth == rect2.width()) {
                                                    }
                                                    this.mWidth = rect2.width();
                                                    this.mHeight = rect2.height();
                                                    if (this.mSurfaceHolder != null) {
                                                    }
                                                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                    if (threadedRenderer != null) {
                                                    }
                                                    if (this.mStopped) {
                                                    }
                                                    int rootMeasureSpec3222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                                    int rootMeasureSpec22222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                                    performMeasure(rootMeasureSpec3222, rootMeasureSpec22222);
                                                    int measuredWidth2222 = view.getMeasuredWidth();
                                                    int measuredHeight2222 = view.getMeasuredHeight();
                                                    if (layoutParams3.horizontalWeight > 0.0f) {
                                                    }
                                                    if (layoutParams3.verticalWeight > 0.0f) {
                                                    }
                                                    if (z20) {
                                                    }
                                                    z3 = true;
                                                    z4 = z6;
                                                    bool4 = bool6;
                                                    r24 = z392222;
                                                    if (this.mViewMeasureDeferred) {
                                                    }
                                                    if (this.mRelayoutRequested) {
                                                    }
                                                    str2 = str;
                                                    z10 = r24;
                                                    if (bool == null) {
                                                    }
                                                    prepareSurfaces();
                                                    this.mChildBoundingInsetsChanged = false;
                                                    if (z3) {
                                                    }
                                                    if (z11) {
                                                    }
                                                    if (z11) {
                                                    }
                                                    if (bool2 == null) {
                                                    }
                                                    if (z13) {
                                                    }
                                                    if (z12) {
                                                    }
                                                    if (z6) {
                                                    }
                                                    if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                                    }
                                                    if (this.mFirst) {
                                                    }
                                                    if ((!z35 || this.mFirst) && z7) {
                                                    }
                                                    this.mFirst = false;
                                                    this.mWillDrawSoon = false;
                                                    this.mNewSurfaceNeeded = false;
                                                    this.mViewVisibility = i7;
                                                    this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                                    if ((i8 & 1) != 0) {
                                                    }
                                                    this.mCheckIfCanDraw = !z9 || z10;
                                                    boolean dispatchOnPreDraw22222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                    if (dispatchOnPreDraw22222) {
                                                    }
                                                    if (!z15) {
                                                    }
                                                    if (z7) {
                                                    }
                                                    this.mWasLastDrawCanceled = z15;
                                                    this.mLastTraversalWasVisible = z7;
                                                    if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                    }
                                                    this.mIsInTraversal = false;
                                                    this.mRelayoutRequested = false;
                                                    if (!z15) {
                                                    }
                                                    setPreferredFrameRate(this.mPreferredFrameRate);
                                                    setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                                    this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                                    this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                                    if (this.mFrameRateCategoryLowCount <= 0) {
                                                    }
                                                    this.mFrameRateCategoryLowCount = i11;
                                                    this.mPreferredFrameRateCategory = i10;
                                                    this.mPreferredFrameRate = -1.0f;
                                                    this.mIsFrameRateConflicted = false;
                                                    return;
                                                }
                                                if (!this.mSurface.isValid()) {
                                                    r15 = 1;
                                                    if (generationId == this.mSurface.getGenerationId() || z40) {
                                                        if (this.mSurface.isValid()) {
                                                            bool9 = 1;
                                                            if (bool9 != 0) {
                                                                try {
                                                                    this.mSurfaceSequenceId++;
                                                                } catch (android.os.RemoteException e11) {
                                                                    bool5 = bool9;
                                                                    bool2 = r2;
                                                                    j = 8;
                                                                    z17 = false;
                                                                    z18 = false;
                                                                    z22 = z30;
                                                                    bool7 = r15;
                                                                    z21 = z28;
                                                                    if (android.os.Trace.isTagEnabled(j)) {
                                                                    }
                                                                    bool3 = bool5;
                                                                    r9 = z22;
                                                                    bool6 = bool7;
                                                                    boolean z3922222 = z21;
                                                                    this.mAttachInfo.mWindowLeft = rect2.left;
                                                                    this.mAttachInfo.mWindowTop = rect2.top;
                                                                    if (this.mWidth == rect2.width()) {
                                                                    }
                                                                    this.mWidth = rect2.width();
                                                                    this.mHeight = rect2.height();
                                                                    if (this.mSurfaceHolder != null) {
                                                                    }
                                                                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                                    if (threadedRenderer != null) {
                                                                    }
                                                                    if (this.mStopped) {
                                                                    }
                                                                    int rootMeasureSpec32222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                                                    int rootMeasureSpec222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                                                    performMeasure(rootMeasureSpec32222, rootMeasureSpec222222);
                                                                    int measuredWidth22222 = view.getMeasuredWidth();
                                                                    int measuredHeight22222 = view.getMeasuredHeight();
                                                                    if (layoutParams3.horizontalWeight > 0.0f) {
                                                                    }
                                                                    if (layoutParams3.verticalWeight > 0.0f) {
                                                                    }
                                                                    if (z20) {
                                                                    }
                                                                    z3 = true;
                                                                    z4 = z6;
                                                                    bool4 = bool6;
                                                                    r24 = z3922222;
                                                                    if (this.mViewMeasureDeferred) {
                                                                    }
                                                                    if (this.mRelayoutRequested) {
                                                                    }
                                                                    str2 = str;
                                                                    z10 = r24;
                                                                    if (bool == null) {
                                                                    }
                                                                    prepareSurfaces();
                                                                    this.mChildBoundingInsetsChanged = false;
                                                                    if (z3) {
                                                                    }
                                                                    if (z11) {
                                                                    }
                                                                    if (z11) {
                                                                    }
                                                                    if (bool2 == null) {
                                                                    }
                                                                    if (z13) {
                                                                    }
                                                                    if (z12) {
                                                                    }
                                                                    if (z6) {
                                                                    }
                                                                    if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                                                    }
                                                                    if (this.mFirst) {
                                                                    }
                                                                    if ((!z35 || this.mFirst) && z7) {
                                                                    }
                                                                    this.mFirst = false;
                                                                    this.mWillDrawSoon = false;
                                                                    this.mNewSurfaceNeeded = false;
                                                                    this.mViewVisibility = i7;
                                                                    this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                                                    if ((i8 & 1) != 0) {
                                                                    }
                                                                    this.mCheckIfCanDraw = !z9 || z10;
                                                                    boolean dispatchOnPreDraw222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                                    if (dispatchOnPreDraw222222) {
                                                                    }
                                                                    if (!z15) {
                                                                    }
                                                                    if (z7) {
                                                                    }
                                                                    this.mWasLastDrawCanceled = z15;
                                                                    this.mLastTraversalWasVisible = z7;
                                                                    if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                                    }
                                                                    this.mIsInTraversal = false;
                                                                    this.mRelayoutRequested = false;
                                                                    if (!z15) {
                                                                    }
                                                                    setPreferredFrameRate(this.mPreferredFrameRate);
                                                                    setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                                                    this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                                                    this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                                                    if (this.mFrameRateCategoryLowCount <= 0) {
                                                                    }
                                                                    this.mFrameRateCategoryLowCount = i11;
                                                                    this.mPreferredFrameRateCategory = i10;
                                                                    this.mPreferredFrameRate = -1.0f;
                                                                    this.mIsFrameRateConflicted = false;
                                                                    return;
                                                                }
                                                            }
                                                            if (z31) {
                                                                z17 = false;
                                                            } else {
                                                                this.mAttachInfo.mAlwaysConsumeSystemBars = this.mPendingAlwaysConsumeSystemBars;
                                                                z17 = true;
                                                            }
                                                            if (updateCaptionInsets()) {
                                                                z17 = true;
                                                            }
                                                            if (!z17) {
                                                                try {
                                                                    if (this.mLastSystemUiVisibility == this.mAttachInfo.mSystemUiVisibility) {
                                                                    }
                                                                } catch (android.os.RemoteException e12) {
                                                                    bool5 = bool9;
                                                                    bool2 = r2;
                                                                    j = 8;
                                                                    z18 = false;
                                                                    z22 = z30;
                                                                    bool7 = r15;
                                                                    z21 = z28;
                                                                    if (android.os.Trace.isTagEnabled(j)) {
                                                                        android.os.Trace.traceEnd(j);
                                                                    }
                                                                    bool3 = bool5;
                                                                    r9 = z22;
                                                                    bool6 = bool7;
                                                                    boolean z39222222 = z21;
                                                                    this.mAttachInfo.mWindowLeft = rect2.left;
                                                                    this.mAttachInfo.mWindowTop = rect2.top;
                                                                    if (this.mWidth == rect2.width()) {
                                                                    }
                                                                    this.mWidth = rect2.width();
                                                                    this.mHeight = rect2.height();
                                                                    if (this.mSurfaceHolder != null) {
                                                                    }
                                                                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                                    if (threadedRenderer != null) {
                                                                    }
                                                                    if (this.mStopped) {
                                                                    }
                                                                    int rootMeasureSpec322222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                                                    int rootMeasureSpec2222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                                                    performMeasure(rootMeasureSpec322222, rootMeasureSpec2222222);
                                                                    int measuredWidth222222 = view.getMeasuredWidth();
                                                                    int measuredHeight222222 = view.getMeasuredHeight();
                                                                    if (layoutParams3.horizontalWeight > 0.0f) {
                                                                    }
                                                                    if (layoutParams3.verticalWeight > 0.0f) {
                                                                    }
                                                                    if (z20) {
                                                                    }
                                                                    z3 = true;
                                                                    z4 = z6;
                                                                    bool4 = bool6;
                                                                    r24 = z39222222;
                                                                    if (this.mViewMeasureDeferred) {
                                                                    }
                                                                    if (this.mRelayoutRequested) {
                                                                    }
                                                                    str2 = str;
                                                                    z10 = r24;
                                                                    if (bool == null) {
                                                                    }
                                                                    prepareSurfaces();
                                                                    this.mChildBoundingInsetsChanged = false;
                                                                    if (z3) {
                                                                    }
                                                                    if (z11) {
                                                                    }
                                                                    if (z11) {
                                                                    }
                                                                    if (bool2 == null) {
                                                                    }
                                                                    if (z13) {
                                                                    }
                                                                    if (z12) {
                                                                    }
                                                                    if (z6) {
                                                                    }
                                                                    if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                                                    }
                                                                    if (this.mFirst) {
                                                                    }
                                                                    if ((!z35 || this.mFirst) && z7) {
                                                                    }
                                                                    this.mFirst = false;
                                                                    this.mWillDrawSoon = false;
                                                                    this.mNewSurfaceNeeded = false;
                                                                    this.mViewVisibility = i7;
                                                                    this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                                                    if ((i8 & 1) != 0) {
                                                                    }
                                                                    this.mCheckIfCanDraw = !z9 || z10;
                                                                    boolean dispatchOnPreDraw2222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                                    if (dispatchOnPreDraw2222222) {
                                                                    }
                                                                    if (!z15) {
                                                                    }
                                                                    if (z7) {
                                                                    }
                                                                    this.mWasLastDrawCanceled = z15;
                                                                    this.mLastTraversalWasVisible = z7;
                                                                    if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                                    }
                                                                    this.mIsInTraversal = false;
                                                                    this.mRelayoutRequested = false;
                                                                    if (!z15) {
                                                                    }
                                                                    setPreferredFrameRate(this.mPreferredFrameRate);
                                                                    setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                                                    this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                                                    this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                                                    if (this.mFrameRateCategoryLowCount <= 0) {
                                                                    }
                                                                    this.mFrameRateCategoryLowCount = i11;
                                                                    this.mPreferredFrameRateCategory = i10;
                                                                    this.mPreferredFrameRate = -1.0f;
                                                                    this.mIsFrameRateConflicted = false;
                                                                    return;
                                                                }
                                                            }
                                                            this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                                                            dispatchApplyInsets(view);
                                                            z17 = true;
                                                            if (r2 == 0) {
                                                                this.mFullRedrawNeeded = true;
                                                                this.mPreviousTransparentRegion.setEmpty();
                                                                if (this.mAttachInfo.mThreadedRenderer != null) {
                                                                    try {
                                                                        z33 = this.mAttachInfo.mThreadedRenderer.initialize(this.mSurface);
                                                                        if (z33) {
                                                                            try {
                                                                                try {
                                                                                    if ((view.mPrivateFlags & 512) == 0) {
                                                                                        this.mAttachInfo.mThreadedRenderer.allocateBuffers();
                                                                                    }
                                                                                } catch (android.os.RemoteException e13) {
                                                                                    bool5 = bool9;
                                                                                    bool2 = r2;
                                                                                    z18 = z33;
                                                                                    j = 8;
                                                                                    z22 = z30;
                                                                                    bool7 = r15;
                                                                                    z21 = z28;
                                                                                    if (android.os.Trace.isTagEnabled(j)) {
                                                                                    }
                                                                                    bool3 = bool5;
                                                                                    r9 = z22;
                                                                                    bool6 = bool7;
                                                                                    boolean z392222222 = z21;
                                                                                    this.mAttachInfo.mWindowLeft = rect2.left;
                                                                                    this.mAttachInfo.mWindowTop = rect2.top;
                                                                                    if (this.mWidth == rect2.width()) {
                                                                                    }
                                                                                    this.mWidth = rect2.width();
                                                                                    this.mHeight = rect2.height();
                                                                                    if (this.mSurfaceHolder != null) {
                                                                                    }
                                                                                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                                                    if (threadedRenderer != null) {
                                                                                    }
                                                                                    if (this.mStopped) {
                                                                                    }
                                                                                    int rootMeasureSpec3222222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                                                                    int rootMeasureSpec22222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                                                                    performMeasure(rootMeasureSpec3222222, rootMeasureSpec22222222);
                                                                                    int measuredWidth2222222 = view.getMeasuredWidth();
                                                                                    int measuredHeight2222222 = view.getMeasuredHeight();
                                                                                    if (layoutParams3.horizontalWeight > 0.0f) {
                                                                                    }
                                                                                    if (layoutParams3.verticalWeight > 0.0f) {
                                                                                    }
                                                                                    if (z20) {
                                                                                    }
                                                                                    z3 = true;
                                                                                    z4 = z6;
                                                                                    bool4 = bool6;
                                                                                    r24 = z392222222;
                                                                                    if (this.mViewMeasureDeferred) {
                                                                                    }
                                                                                    if (this.mRelayoutRequested) {
                                                                                    }
                                                                                    str2 = str;
                                                                                    z10 = r24;
                                                                                    if (bool == null) {
                                                                                    }
                                                                                    prepareSurfaces();
                                                                                    this.mChildBoundingInsetsChanged = false;
                                                                                    if (z3) {
                                                                                    }
                                                                                    if (z11) {
                                                                                    }
                                                                                    if (z11) {
                                                                                    }
                                                                                    if (bool2 == null) {
                                                                                    }
                                                                                    if (z13) {
                                                                                    }
                                                                                    if (z12) {
                                                                                    }
                                                                                    if (z6) {
                                                                                    }
                                                                                    if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                                                                    }
                                                                                    if (this.mFirst) {
                                                                                    }
                                                                                    if ((!z35 || this.mFirst) && z7) {
                                                                                    }
                                                                                    this.mFirst = false;
                                                                                    this.mWillDrawSoon = false;
                                                                                    this.mNewSurfaceNeeded = false;
                                                                                    this.mViewVisibility = i7;
                                                                                    this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                                                                    if ((i8 & 1) != 0) {
                                                                                    }
                                                                                    this.mCheckIfCanDraw = !z9 || z10;
                                                                                    boolean dispatchOnPreDraw22222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                                                    if (dispatchOnPreDraw22222222) {
                                                                                    }
                                                                                    if (!z15) {
                                                                                    }
                                                                                    if (z7) {
                                                                                    }
                                                                                    this.mWasLastDrawCanceled = z15;
                                                                                    this.mLastTraversalWasVisible = z7;
                                                                                    if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                                                    }
                                                                                    this.mIsInTraversal = false;
                                                                                    this.mRelayoutRequested = false;
                                                                                    if (!z15) {
                                                                                    }
                                                                                    setPreferredFrameRate(this.mPreferredFrameRate);
                                                                                    setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                                                                    this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                                                                    this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                                                                    if (this.mFrameRateCategoryLowCount <= 0) {
                                                                                    }
                                                                                    this.mFrameRateCategoryLowCount = i11;
                                                                                    this.mPreferredFrameRateCategory = i10;
                                                                                    this.mPreferredFrameRate = -1.0f;
                                                                                    this.mIsFrameRateConflicted = false;
                                                                                    return;
                                                                                }
                                                                            } catch (android.view.Surface.OutOfResourcesException e14) {
                                                                                e = e14;
                                                                                handleOutOfResourcesException(e);
                                                                                this.mLastPerformTraversalsSkipDrawReason = "oom_initialize_renderer";
                                                                                if (android.os.Trace.isTagEnabled(8L)) {
                                                                                    android.os.Trace.traceEnd(8L);
                                                                                    return;
                                                                                }
                                                                                return;
                                                                            }
                                                                        }
                                                                        z18 = z33;
                                                                        if (this.mDragResizing == z26) {
                                                                            bool3 = bool9;
                                                                            bool2 = r2;
                                                                        } else if (z26) {
                                                                            try {
                                                                                if (this.mWinFrame.width() == this.mPendingBackDropFrame.width()) {
                                                                                    try {
                                                                                        if (this.mWinFrame.height() == this.mPendingBackDropFrame.height()) {
                                                                                            z32 = true;
                                                                                            bool3 = bool9;
                                                                                            bool2 = r2;
                                                                                            startDragResizing(this.mPendingBackDropFrame, z32, this.mAttachInfo.mContentInsets, this.mAttachInfo.mStableInsets);
                                                                                        }
                                                                                    } catch (android.os.RemoteException e15) {
                                                                                        bool5 = bool9;
                                                                                        bool2 = r2;
                                                                                        j = 8;
                                                                                        z22 = z30;
                                                                                        bool7 = r15;
                                                                                        z21 = z28;
                                                                                        if (android.os.Trace.isTagEnabled(j)) {
                                                                                        }
                                                                                        bool3 = bool5;
                                                                                        r9 = z22;
                                                                                        bool6 = bool7;
                                                                                        boolean z3922222222 = z21;
                                                                                        this.mAttachInfo.mWindowLeft = rect2.left;
                                                                                        this.mAttachInfo.mWindowTop = rect2.top;
                                                                                        if (this.mWidth == rect2.width()) {
                                                                                        }
                                                                                        this.mWidth = rect2.width();
                                                                                        this.mHeight = rect2.height();
                                                                                        if (this.mSurfaceHolder != null) {
                                                                                        }
                                                                                        threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                                                        if (threadedRenderer != null) {
                                                                                        }
                                                                                        if (this.mStopped) {
                                                                                        }
                                                                                        int rootMeasureSpec32222222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                                                                        int rootMeasureSpec222222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                                                                        performMeasure(rootMeasureSpec32222222, rootMeasureSpec222222222);
                                                                                        int measuredWidth22222222 = view.getMeasuredWidth();
                                                                                        int measuredHeight22222222 = view.getMeasuredHeight();
                                                                                        if (layoutParams3.horizontalWeight > 0.0f) {
                                                                                        }
                                                                                        if (layoutParams3.verticalWeight > 0.0f) {
                                                                                        }
                                                                                        if (z20) {
                                                                                        }
                                                                                        z3 = true;
                                                                                        z4 = z6;
                                                                                        bool4 = bool6;
                                                                                        r24 = z3922222222;
                                                                                        if (this.mViewMeasureDeferred) {
                                                                                        }
                                                                                        if (this.mRelayoutRequested) {
                                                                                        }
                                                                                        str2 = str;
                                                                                        z10 = r24;
                                                                                        if (bool == null) {
                                                                                        }
                                                                                        prepareSurfaces();
                                                                                        this.mChildBoundingInsetsChanged = false;
                                                                                        if (z3) {
                                                                                        }
                                                                                        if (z11) {
                                                                                        }
                                                                                        if (z11) {
                                                                                        }
                                                                                        if (bool2 == null) {
                                                                                        }
                                                                                        if (z13) {
                                                                                        }
                                                                                        if (z12) {
                                                                                        }
                                                                                        if (z6) {
                                                                                        }
                                                                                        if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                                                                        }
                                                                                        if (this.mFirst) {
                                                                                        }
                                                                                        if ((!z35 || this.mFirst) && z7) {
                                                                                        }
                                                                                        this.mFirst = false;
                                                                                        this.mWillDrawSoon = false;
                                                                                        this.mNewSurfaceNeeded = false;
                                                                                        this.mViewVisibility = i7;
                                                                                        this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                                                                        if ((i8 & 1) != 0) {
                                                                                        }
                                                                                        this.mCheckIfCanDraw = !z9 || z10;
                                                                                        boolean dispatchOnPreDraw222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                                                        if (dispatchOnPreDraw222222222) {
                                                                                        }
                                                                                        if (!z15) {
                                                                                        }
                                                                                        if (z7) {
                                                                                        }
                                                                                        this.mWasLastDrawCanceled = z15;
                                                                                        this.mLastTraversalWasVisible = z7;
                                                                                        if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                                                        }
                                                                                        this.mIsInTraversal = false;
                                                                                        this.mRelayoutRequested = false;
                                                                                        if (!z15) {
                                                                                        }
                                                                                        setPreferredFrameRate(this.mPreferredFrameRate);
                                                                                        setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                                                                        this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                                                                        this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                                                                        if (this.mFrameRateCategoryLowCount <= 0) {
                                                                                        }
                                                                                        this.mFrameRateCategoryLowCount = i11;
                                                                                        this.mPreferredFrameRateCategory = i10;
                                                                                        this.mPreferredFrameRate = -1.0f;
                                                                                        this.mIsFrameRateConflicted = false;
                                                                                        return;
                                                                                    }
                                                                                }
                                                                                bool2 = r2;
                                                                                startDragResizing(this.mPendingBackDropFrame, z32, this.mAttachInfo.mContentInsets, this.mAttachInfo.mStableInsets);
                                                                            } catch (android.os.RemoteException e16) {
                                                                                bool2 = r2;
                                                                                bool5 = bool3;
                                                                                j = 8;
                                                                                z22 = z30;
                                                                                bool7 = r15;
                                                                                z21 = z28;
                                                                                if (android.os.Trace.isTagEnabled(j)) {
                                                                                }
                                                                                bool3 = bool5;
                                                                                r9 = z22;
                                                                                bool6 = bool7;
                                                                                boolean z39222222222 = z21;
                                                                                this.mAttachInfo.mWindowLeft = rect2.left;
                                                                                this.mAttachInfo.mWindowTop = rect2.top;
                                                                                if (this.mWidth == rect2.width()) {
                                                                                }
                                                                                this.mWidth = rect2.width();
                                                                                this.mHeight = rect2.height();
                                                                                if (this.mSurfaceHolder != null) {
                                                                                }
                                                                                threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                                                if (threadedRenderer != null) {
                                                                                }
                                                                                if (this.mStopped) {
                                                                                }
                                                                                int rootMeasureSpec322222222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                                                                int rootMeasureSpec2222222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                                                                performMeasure(rootMeasureSpec322222222, rootMeasureSpec2222222222);
                                                                                int measuredWidth222222222 = view.getMeasuredWidth();
                                                                                int measuredHeight222222222 = view.getMeasuredHeight();
                                                                                if (layoutParams3.horizontalWeight > 0.0f) {
                                                                                }
                                                                                if (layoutParams3.verticalWeight > 0.0f) {
                                                                                }
                                                                                if (z20) {
                                                                                }
                                                                                z3 = true;
                                                                                z4 = z6;
                                                                                bool4 = bool6;
                                                                                r24 = z39222222222;
                                                                                if (this.mViewMeasureDeferred) {
                                                                                }
                                                                                if (this.mRelayoutRequested) {
                                                                                }
                                                                                str2 = str;
                                                                                z10 = r24;
                                                                                if (bool == null) {
                                                                                }
                                                                                prepareSurfaces();
                                                                                this.mChildBoundingInsetsChanged = false;
                                                                                if (z3) {
                                                                                }
                                                                                if (z11) {
                                                                                }
                                                                                if (z11) {
                                                                                }
                                                                                if (bool2 == null) {
                                                                                }
                                                                                if (z13) {
                                                                                }
                                                                                if (z12) {
                                                                                }
                                                                                if (z6) {
                                                                                }
                                                                                if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                                                                }
                                                                                if (this.mFirst) {
                                                                                }
                                                                                if ((!z35 || this.mFirst) && z7) {
                                                                                }
                                                                                this.mFirst = false;
                                                                                this.mWillDrawSoon = false;
                                                                                this.mNewSurfaceNeeded = false;
                                                                                this.mViewVisibility = i7;
                                                                                this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                                                                if ((i8 & 1) != 0) {
                                                                                }
                                                                                this.mCheckIfCanDraw = !z9 || z10;
                                                                                boolean dispatchOnPreDraw2222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                                                if (dispatchOnPreDraw2222222222) {
                                                                                }
                                                                                if (!z15) {
                                                                                }
                                                                                if (z7) {
                                                                                }
                                                                                this.mWasLastDrawCanceled = z15;
                                                                                this.mLastTraversalWasVisible = z7;
                                                                                if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                                                }
                                                                                this.mIsInTraversal = false;
                                                                                this.mRelayoutRequested = false;
                                                                                if (!z15) {
                                                                                }
                                                                                setPreferredFrameRate(this.mPreferredFrameRate);
                                                                                setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                                                                this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                                                                this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                                                                if (this.mFrameRateCategoryLowCount <= 0) {
                                                                                }
                                                                                this.mFrameRateCategoryLowCount = i11;
                                                                                this.mPreferredFrameRateCategory = i10;
                                                                                this.mPreferredFrameRate = -1.0f;
                                                                                this.mIsFrameRateConflicted = false;
                                                                                return;
                                                                            }
                                                                            z32 = false;
                                                                            bool3 = bool9;
                                                                        } else {
                                                                            bool3 = bool9;
                                                                            bool2 = r2;
                                                                            endDragResizing();
                                                                        }
                                                                        if (!this.mUseMTRenderer) {
                                                                            if (z26) {
                                                                                this.mCanvasOffsetX = this.mWinFrame.left;
                                                                                this.mCanvasOffsetY = this.mWinFrame.top;
                                                                            } else {
                                                                                this.mCanvasOffsetY = 0;
                                                                                this.mCanvasOffsetX = 0;
                                                                            }
                                                                        }
                                                                        this.mAttachInfo.mWindowLeft = rect2.left;
                                                                        this.mAttachInfo.mWindowTop = rect2.top;
                                                                        if (this.mWidth == rect2.width() || this.mHeight != rect2.height()) {
                                                                            this.mWidth = rect2.width();
                                                                            this.mHeight = rect2.height();
                                                                        }
                                                                        if (this.mSurfaceHolder != null) {
                                                                            if (this.mSurface.isValid()) {
                                                                                this.mSurfaceHolder.mSurface = this.mSurface;
                                                                            }
                                                                            this.mSurfaceHolder.setSurfaceFrameSize(this.mWidth, this.mHeight);
                                                                            if (bool2 != null) {
                                                                                this.mSurfaceHolder.ungetCallbacks();
                                                                                this.mIsCreating = true;
                                                                                android.view.SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
                                                                                if (callbacks != null) {
                                                                                    for (android.view.SurfaceHolder.Callback callback : callbacks) {
                                                                                        callback.surfaceCreated(this.mSurfaceHolder);
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (bool2 == null && bool3 == null && r9 == 0 && !z8) {
                                                                                bool = r9 == true ? 1 : 0;
                                                                            } else if (this.mSurface.isValid()) {
                                                                                android.view.SurfaceHolder.Callback[] callbacks2 = this.mSurfaceHolder.getCallbacks();
                                                                                if (callbacks2 != null) {
                                                                                    int length = callbacks2.length;
                                                                                    int i20 = 0;
                                                                                    java.lang.Boolean bool11 = r9;
                                                                                    while (i20 < length) {
                                                                                        callbacks2[i20].surfaceChanged(this.mSurfaceHolder, layoutParams3.format, this.mWidth, this.mHeight);
                                                                                        i20++;
                                                                                        callbacks2 = callbacks2;
                                                                                        length = length;
                                                                                        bool11 = bool11;
                                                                                    }
                                                                                    bool = bool11;
                                                                                } else {
                                                                                    bool = r9 == true ? 1 : 0;
                                                                                }
                                                                                this.mIsCreating = false;
                                                                            } else {
                                                                                bool = r9 == true ? 1 : 0;
                                                                            }
                                                                            if (bool6 != null) {
                                                                                notifyHolderSurfaceDestroyed();
                                                                                this.mSurfaceHolder.mSurfaceLock.lock();
                                                                                try {
                                                                                    this.mSurfaceHolder.mSurface = new android.view.Surface();
                                                                                } finally {
                                                                                    this.mSurfaceHolder.mSurfaceLock.unlock();
                                                                                }
                                                                            }
                                                                        } else {
                                                                            bool = r9 == true ? 1 : 0;
                                                                        }
                                                                        threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                                        if (threadedRenderer != null && threadedRenderer.isEnabled() && (z18 || this.mWidth != threadedRenderer.getWidth() || this.mHeight != threadedRenderer.getHeight() || this.mNeedsRendererSetup)) {
                                                                            threadedRenderer.setup(this.mWidth, this.mHeight, this.mAttachInfo, this.mWindowAttributes.surfaceInsets);
                                                                            this.mNeedsRendererSetup = false;
                                                                        }
                                                                        if ((this.mStopped || this.mReportNextDraw) && (this.mWidth != view.getMeasuredWidth() || this.mHeight != view.getMeasuredHeight() || z17 || z19)) {
                                                                            int rootMeasureSpec3222222222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                                                            int rootMeasureSpec22222222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                                                            performMeasure(rootMeasureSpec3222222222, rootMeasureSpec22222222222);
                                                                            int measuredWidth2222222222 = view.getMeasuredWidth();
                                                                            int measuredHeight2222222222 = view.getMeasuredHeight();
                                                                            if (layoutParams3.horizontalWeight > 0.0f) {
                                                                                rootMeasureSpec3222222222 = android.view.View.MeasureSpec.makeMeasureSpec(measuredWidth2222222222 + ((int) ((this.mWidth - measuredWidth2222222222) * layoutParams3.horizontalWeight)), 1073741824);
                                                                                z20 = true;
                                                                            } else {
                                                                                z20 = false;
                                                                            }
                                                                            if (layoutParams3.verticalWeight > 0.0f) {
                                                                                rootMeasureSpec22222222222 = android.view.View.MeasureSpec.makeMeasureSpec(measuredHeight2222222222 + ((int) ((this.mHeight - measuredHeight2222222222) * layoutParams3.verticalWeight)), 1073741824);
                                                                                z20 = true;
                                                                            }
                                                                            if (z20) {
                                                                                performMeasure(rootMeasureSpec3222222222, rootMeasureSpec22222222222);
                                                                            }
                                                                            z3 = true;
                                                                        } else {
                                                                            z3 = z38;
                                                                        }
                                                                        z4 = z6;
                                                                        bool4 = bool6;
                                                                        r24 = z39222222222;
                                                                        if (this.mViewMeasureDeferred) {
                                                                        }
                                                                        if (this.mRelayoutRequested) {
                                                                        }
                                                                        str2 = str;
                                                                        z10 = r24;
                                                                        if (bool == null) {
                                                                        }
                                                                        prepareSurfaces();
                                                                        this.mChildBoundingInsetsChanged = false;
                                                                        if (z3) {
                                                                        }
                                                                        if (z11) {
                                                                        }
                                                                        if (z11) {
                                                                        }
                                                                        if (bool2 == null) {
                                                                        }
                                                                        if (z13) {
                                                                        }
                                                                        if (z12) {
                                                                        }
                                                                        if (z6) {
                                                                        }
                                                                        if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                                                        }
                                                                        if (this.mFirst) {
                                                                        }
                                                                        if ((!z35 || this.mFirst) && z7) {
                                                                        }
                                                                        this.mFirst = false;
                                                                        this.mWillDrawSoon = false;
                                                                        this.mNewSurfaceNeeded = false;
                                                                        this.mViewVisibility = i7;
                                                                        this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                                                        if ((i8 & 1) != 0) {
                                                                        }
                                                                        this.mCheckIfCanDraw = !z9 || z10;
                                                                        boolean dispatchOnPreDraw22222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                                        if (dispatchOnPreDraw22222222222) {
                                                                        }
                                                                        if (!z15) {
                                                                        }
                                                                        if (z7) {
                                                                        }
                                                                        this.mWasLastDrawCanceled = z15;
                                                                        this.mLastTraversalWasVisible = z7;
                                                                        if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                                        }
                                                                        this.mIsInTraversal = false;
                                                                        this.mRelayoutRequested = false;
                                                                        if (!z15) {
                                                                        }
                                                                        setPreferredFrameRate(this.mPreferredFrameRate);
                                                                        setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                                                        this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                                                        this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                                                        if (this.mFrameRateCategoryLowCount <= 0) {
                                                                        }
                                                                        this.mFrameRateCategoryLowCount = i11;
                                                                        this.mPreferredFrameRateCategory = i10;
                                                                        this.mPreferredFrameRate = -1.0f;
                                                                        this.mIsFrameRateConflicted = false;
                                                                        return;
                                                                    } catch (android.view.Surface.OutOfResourcesException e17) {
                                                                        e = e17;
                                                                        z33 = false;
                                                                    }
                                                                }
                                                            } else if (r15 != 0) {
                                                                if (this.mLastScrolledFocus != null) {
                                                                    this.mLastScrolledFocus.clear();
                                                                }
                                                                this.mCurScrollY = 0;
                                                                this.mScrollY = 0;
                                                                if (this.mView instanceof com.android.internal.view.RootViewSurfaceTaker) {
                                                                    ((com.android.internal.view.RootViewSurfaceTaker) this.mView).onRootViewScrollYChanged(this.mCurScrollY);
                                                                }
                                                                if (this.mScroller != null) {
                                                                    this.mScroller.abortAnimation();
                                                                }
                                                                if (isHardwareEnabled()) {
                                                                    this.mAttachInfo.mThreadedRenderer.destroy();
                                                                }
                                                            } else if (bool9 != 0 || z30 || z42) {
                                                                if (this.mSurfaceHolder == null && this.mAttachInfo.mThreadedRenderer != null && this.mSurface.isValid()) {
                                                                    this.mFullRedrawNeeded = true;
                                                                    try {
                                                                        this.mAttachInfo.mThreadedRenderer.updateSurface(this.mSurface);
                                                                    } catch (android.view.Surface.OutOfResourcesException e18) {
                                                                        handleOutOfResourcesException(e18);
                                                                        this.mLastPerformTraversalsSkipDrawReason = "oom_update_surface";
                                                                        if (android.os.Trace.isTagEnabled(8L)) {
                                                                            android.os.Trace.traceEnd(8L);
                                                                            return;
                                                                        }
                                                                        return;
                                                                    }
                                                                }
                                                            }
                                                            z18 = false;
                                                            if (this.mDragResizing == z26) {
                                                            }
                                                            if (!this.mUseMTRenderer) {
                                                            }
                                                            this.mAttachInfo.mWindowLeft = rect2.left;
                                                            this.mAttachInfo.mWindowTop = rect2.top;
                                                            if (this.mWidth == rect2.width()) {
                                                            }
                                                            this.mWidth = rect2.width();
                                                            this.mHeight = rect2.height();
                                                            if (this.mSurfaceHolder != null) {
                                                            }
                                                            threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                            if (threadedRenderer != null) {
                                                                threadedRenderer.setup(this.mWidth, this.mHeight, this.mAttachInfo, this.mWindowAttributes.surfaceInsets);
                                                                this.mNeedsRendererSetup = false;
                                                            }
                                                            if (this.mStopped) {
                                                            }
                                                            int rootMeasureSpec32222222222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                                            int rootMeasureSpec222222222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                                            performMeasure(rootMeasureSpec32222222222, rootMeasureSpec222222222222);
                                                            int measuredWidth22222222222 = view.getMeasuredWidth();
                                                            int measuredHeight22222222222 = view.getMeasuredHeight();
                                                            if (layoutParams3.horizontalWeight > 0.0f) {
                                                            }
                                                            if (layoutParams3.verticalWeight > 0.0f) {
                                                            }
                                                            if (z20) {
                                                            }
                                                            z3 = true;
                                                            z4 = z6;
                                                            bool4 = bool6;
                                                            r24 = z39222222222;
                                                            if (this.mViewMeasureDeferred) {
                                                            }
                                                            if (this.mRelayoutRequested) {
                                                            }
                                                            str2 = str;
                                                            z10 = r24;
                                                            if (bool == null) {
                                                            }
                                                            prepareSurfaces();
                                                            this.mChildBoundingInsetsChanged = false;
                                                            if (z3) {
                                                            }
                                                            if (z11) {
                                                            }
                                                            if (z11) {
                                                            }
                                                            if (bool2 == null) {
                                                            }
                                                            if (z13) {
                                                            }
                                                            if (z12) {
                                                            }
                                                            if (z6) {
                                                            }
                                                            if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                                            }
                                                            if (this.mFirst) {
                                                            }
                                                            if ((!z35 || this.mFirst) && z7) {
                                                            }
                                                            this.mFirst = false;
                                                            this.mWillDrawSoon = false;
                                                            this.mNewSurfaceNeeded = false;
                                                            this.mViewVisibility = i7;
                                                            this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                                            if ((i8 & 1) != 0) {
                                                            }
                                                            this.mCheckIfCanDraw = !z9 || z10;
                                                            boolean dispatchOnPreDraw222222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                            if (dispatchOnPreDraw222222222222) {
                                                            }
                                                            if (!z15) {
                                                            }
                                                            if (z7) {
                                                            }
                                                            this.mWasLastDrawCanceled = z15;
                                                            this.mLastTraversalWasVisible = z7;
                                                            if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                            }
                                                            this.mIsInTraversal = false;
                                                            this.mRelayoutRequested = false;
                                                            if (!z15) {
                                                            }
                                                            setPreferredFrameRate(this.mPreferredFrameRate);
                                                            setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                                            this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                                            this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                                            if (this.mFrameRateCategoryLowCount <= 0) {
                                                            }
                                                            this.mFrameRateCategoryLowCount = i11;
                                                            this.mPreferredFrameRateCategory = i10;
                                                            this.mPreferredFrameRate = -1.0f;
                                                            this.mIsFrameRateConflicted = false;
                                                            return;
                                                        }
                                                    }
                                                    bool9 = 0;
                                                    if (bool9 != 0) {
                                                    }
                                                    if (z31) {
                                                    }
                                                    if (updateCaptionInsets()) {
                                                    }
                                                    if (!z17) {
                                                    }
                                                    this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                                                    dispatchApplyInsets(view);
                                                    z17 = true;
                                                    if (r2 == 0) {
                                                    }
                                                    z18 = false;
                                                    if (this.mDragResizing == z26) {
                                                    }
                                                    if (!this.mUseMTRenderer) {
                                                    }
                                                    this.mAttachInfo.mWindowLeft = rect2.left;
                                                    this.mAttachInfo.mWindowTop = rect2.top;
                                                    if (this.mWidth == rect2.width()) {
                                                    }
                                                    this.mWidth = rect2.width();
                                                    this.mHeight = rect2.height();
                                                    if (this.mSurfaceHolder != null) {
                                                    }
                                                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                    if (threadedRenderer != null) {
                                                    }
                                                    if (this.mStopped) {
                                                    }
                                                    int rootMeasureSpec322222222222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                                    int rootMeasureSpec2222222222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                                    performMeasure(rootMeasureSpec322222222222, rootMeasureSpec2222222222222);
                                                    int measuredWidth222222222222 = view.getMeasuredWidth();
                                                    int measuredHeight222222222222 = view.getMeasuredHeight();
                                                    if (layoutParams3.horizontalWeight > 0.0f) {
                                                    }
                                                    if (layoutParams3.verticalWeight > 0.0f) {
                                                    }
                                                    if (z20) {
                                                    }
                                                    z3 = true;
                                                    z4 = z6;
                                                    bool4 = bool6;
                                                    r24 = z39222222222;
                                                    if (this.mViewMeasureDeferred) {
                                                    }
                                                    if (this.mRelayoutRequested) {
                                                    }
                                                    str2 = str;
                                                    z10 = r24;
                                                    if (bool == null) {
                                                    }
                                                    prepareSurfaces();
                                                    this.mChildBoundingInsetsChanged = false;
                                                    if (z3) {
                                                    }
                                                    if (z11) {
                                                    }
                                                    if (z11) {
                                                    }
                                                    if (bool2 == null) {
                                                    }
                                                    if (z13) {
                                                    }
                                                    if (z12) {
                                                    }
                                                    if (z6) {
                                                    }
                                                    if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                                    }
                                                    if (this.mFirst) {
                                                    }
                                                    if ((!z35 || this.mFirst) && z7) {
                                                    }
                                                    this.mFirst = false;
                                                    this.mWillDrawSoon = false;
                                                    this.mNewSurfaceNeeded = false;
                                                    this.mViewVisibility = i7;
                                                    this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                                    if ((i8 & 1) != 0) {
                                                    }
                                                    this.mCheckIfCanDraw = !z9 || z10;
                                                    boolean dispatchOnPreDraw2222222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                    if (dispatchOnPreDraw2222222222222) {
                                                    }
                                                    if (!z15) {
                                                    }
                                                    if (z7) {
                                                    }
                                                    this.mWasLastDrawCanceled = z15;
                                                    this.mLastTraversalWasVisible = z7;
                                                    if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                    }
                                                    this.mIsInTraversal = false;
                                                    this.mRelayoutRequested = false;
                                                    if (!z15) {
                                                    }
                                                    setPreferredFrameRate(this.mPreferredFrameRate);
                                                    setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                                    this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                                    this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                                    if (this.mFrameRateCategoryLowCount <= 0) {
                                                    }
                                                    this.mFrameRateCategoryLowCount = i11;
                                                    this.mPreferredFrameRateCategory = i10;
                                                    this.mPreferredFrameRate = -1.0f;
                                                    this.mIsFrameRateConflicted = false;
                                                    return;
                                                }
                                            }
                                            r15 = 0;
                                            if (generationId == this.mSurface.getGenerationId()) {
                                            }
                                            if (this.mSurface.isValid()) {
                                            }
                                            bool9 = 0;
                                            if (bool9 != 0) {
                                            }
                                            if (z31) {
                                            }
                                            if (updateCaptionInsets()) {
                                            }
                                            if (!z17) {
                                            }
                                            this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                                            dispatchApplyInsets(view);
                                            z17 = true;
                                            if (r2 == 0) {
                                            }
                                            z18 = false;
                                            if (this.mDragResizing == z26) {
                                            }
                                            if (!this.mUseMTRenderer) {
                                            }
                                            this.mAttachInfo.mWindowLeft = rect2.left;
                                            this.mAttachInfo.mWindowTop = rect2.top;
                                            if (this.mWidth == rect2.width()) {
                                            }
                                            this.mWidth = rect2.width();
                                            this.mHeight = rect2.height();
                                            if (this.mSurfaceHolder != null) {
                                            }
                                            threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                            if (threadedRenderer != null) {
                                            }
                                            if (this.mStopped) {
                                            }
                                            int rootMeasureSpec3222222222222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                            int rootMeasureSpec22222222222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                            performMeasure(rootMeasureSpec3222222222222, rootMeasureSpec22222222222222);
                                            int measuredWidth2222222222222 = view.getMeasuredWidth();
                                            int measuredHeight2222222222222 = view.getMeasuredHeight();
                                            if (layoutParams3.horizontalWeight > 0.0f) {
                                            }
                                            if (layoutParams3.verticalWeight > 0.0f) {
                                            }
                                            if (z20) {
                                            }
                                            z3 = true;
                                            z4 = z6;
                                            bool4 = bool6;
                                            r24 = z39222222222;
                                            if (this.mViewMeasureDeferred) {
                                            }
                                            if (this.mRelayoutRequested) {
                                            }
                                            str2 = str;
                                            z10 = r24;
                                            if (bool == null) {
                                            }
                                            prepareSurfaces();
                                            this.mChildBoundingInsetsChanged = false;
                                            if (z3) {
                                            }
                                            if (z11) {
                                            }
                                            if (z11) {
                                            }
                                            if (bool2 == null) {
                                            }
                                            if (z13) {
                                            }
                                            if (z12) {
                                            }
                                            if (z6) {
                                            }
                                            if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                            }
                                            if (this.mFirst) {
                                            }
                                            if ((!z35 || this.mFirst) && z7) {
                                            }
                                            this.mFirst = false;
                                            this.mWillDrawSoon = false;
                                            this.mNewSurfaceNeeded = false;
                                            this.mViewVisibility = i7;
                                            this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                            if ((i8 & 1) != 0) {
                                            }
                                            this.mCheckIfCanDraw = !z9 || z10;
                                            boolean dispatchOnPreDraw22222222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                            if (dispatchOnPreDraw22222222222222) {
                                            }
                                            if (!z15) {
                                            }
                                            if (z7) {
                                            }
                                            this.mWasLastDrawCanceled = z15;
                                            this.mLastTraversalWasVisible = z7;
                                            if (this.mAttachInfo.mContentCaptureEvents != null) {
                                            }
                                            this.mIsInTraversal = false;
                                            this.mRelayoutRequested = false;
                                            if (!z15) {
                                            }
                                            setPreferredFrameRate(this.mPreferredFrameRate);
                                            setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                            this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                            this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                            if (this.mFrameRateCategoryLowCount <= 0) {
                                            }
                                            this.mFrameRateCategoryLowCount = i11;
                                            this.mPreferredFrameRateCategory = i10;
                                            this.mPreferredFrameRate = -1.0f;
                                            this.mIsFrameRateConflicted = false;
                                            return;
                                        }
                                    }
                                    r2 = 0;
                                    if (isValid) {
                                    }
                                    r15 = 0;
                                    if (generationId == this.mSurface.getGenerationId()) {
                                    }
                                    if (this.mSurface.isValid()) {
                                    }
                                    bool9 = 0;
                                    if (bool9 != 0) {
                                    }
                                    if (z31) {
                                    }
                                    if (updateCaptionInsets()) {
                                    }
                                    if (!z17) {
                                    }
                                    this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                                    dispatchApplyInsets(view);
                                    z17 = true;
                                    if (r2 == 0) {
                                    }
                                    z18 = false;
                                    if (this.mDragResizing == z26) {
                                    }
                                    if (!this.mUseMTRenderer) {
                                    }
                                    this.mAttachInfo.mWindowLeft = rect2.left;
                                    this.mAttachInfo.mWindowTop = rect2.top;
                                    if (this.mWidth == rect2.width()) {
                                    }
                                    this.mWidth = rect2.width();
                                    this.mHeight = rect2.height();
                                    if (this.mSurfaceHolder != null) {
                                    }
                                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                    if (threadedRenderer != null) {
                                    }
                                    if (this.mStopped) {
                                    }
                                    int rootMeasureSpec32222222222222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                    int rootMeasureSpec222222222222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                    performMeasure(rootMeasureSpec32222222222222, rootMeasureSpec222222222222222);
                                    int measuredWidth22222222222222 = view.getMeasuredWidth();
                                    int measuredHeight22222222222222 = view.getMeasuredHeight();
                                    if (layoutParams3.horizontalWeight > 0.0f) {
                                    }
                                    if (layoutParams3.verticalWeight > 0.0f) {
                                    }
                                    if (z20) {
                                    }
                                    z3 = true;
                                    z4 = z6;
                                    bool4 = bool6;
                                    r24 = z39222222222;
                                    if (this.mViewMeasureDeferred) {
                                    }
                                    if (this.mRelayoutRequested) {
                                    }
                                    str2 = str;
                                    z10 = r24;
                                    if (bool == null) {
                                    }
                                    prepareSurfaces();
                                    this.mChildBoundingInsetsChanged = false;
                                    if (z3) {
                                    }
                                    if (z11) {
                                    }
                                    if (z11) {
                                    }
                                    if (bool2 == null) {
                                    }
                                    if (z13) {
                                    }
                                    if (z12) {
                                    }
                                    if (z6) {
                                    }
                                    if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                    }
                                    if (this.mFirst) {
                                    }
                                    if ((!z35 || this.mFirst) && z7) {
                                    }
                                    this.mFirst = false;
                                    this.mWillDrawSoon = false;
                                    this.mNewSurfaceNeeded = false;
                                    this.mViewVisibility = i7;
                                    this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                    if ((i8 & 1) != 0) {
                                    }
                                    this.mCheckIfCanDraw = !z9 || z10;
                                    boolean dispatchOnPreDraw222222222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                    if (dispatchOnPreDraw222222222222222) {
                                    }
                                    if (!z15) {
                                    }
                                    if (z7) {
                                    }
                                    this.mWasLastDrawCanceled = z15;
                                    this.mLastTraversalWasVisible = z7;
                                    if (this.mAttachInfo.mContentCaptureEvents != null) {
                                    }
                                    this.mIsInTraversal = false;
                                    this.mRelayoutRequested = false;
                                    if (!z15) {
                                    }
                                    setPreferredFrameRate(this.mPreferredFrameRate);
                                    setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                    this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                    this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                    if (this.mFrameRateCategoryLowCount <= 0) {
                                    }
                                    this.mFrameRateCategoryLowCount = i11;
                                    this.mPreferredFrameRateCategory = i10;
                                    this.mPreferredFrameRate = -1.0f;
                                    this.mIsFrameRateConflicted = false;
                                    return;
                                }
                                z25 = z3;
                                this.mViewFrameInfo.flags |= 1;
                                int relayoutWindow2 = relayoutWindow(layoutParams2, i16, z6);
                                if ((relayoutWindow2 & 16) == 16) {
                                }
                                z26 = this.mPendingDragResizing;
                                boolean z382 = z25;
                                if (this.mSyncSeqId > this.mLastSyncSeqId) {
                                }
                                i8 = relayoutWindow2;
                                if ((relayoutWindow2 & 2) == 2) {
                                }
                                if (this.mSurfaceControl.isValid()) {
                                }
                                if (this.mRelayoutRequested) {
                                }
                                z29 = false;
                                boolean z422 = this.mUpdateSurfaceNeeded;
                                this.mUpdateSurfaceNeeded = false;
                                z19 = z29;
                                if (this.mLastSurfaceSize.equals(this.mSurfaceSize)) {
                                }
                                if (this.mPendingAlwaysConsumeSystemBars != this.mAttachInfo.mAlwaysConsumeSystemBars) {
                                }
                                i7 = i16;
                                updateColorModeIfNeeded(layoutParams3.getColorMode(), layoutParams3.getDesiredHdrHeadroom());
                                if (!isValid) {
                                }
                                r2 = 0;
                                if (isValid) {
                                }
                                r15 = 0;
                                if (generationId == this.mSurface.getGenerationId()) {
                                }
                                if (this.mSurface.isValid()) {
                                }
                                bool9 = 0;
                                if (bool9 != 0) {
                                }
                                if (z31) {
                                }
                                if (updateCaptionInsets()) {
                                }
                                if (!z17) {
                                }
                                this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                                dispatchApplyInsets(view);
                                z17 = true;
                                if (r2 == 0) {
                                }
                                z18 = false;
                                if (this.mDragResizing == z26) {
                                }
                                if (!this.mUseMTRenderer) {
                                }
                                this.mAttachInfo.mWindowLeft = rect2.left;
                                this.mAttachInfo.mWindowTop = rect2.top;
                                if (this.mWidth == rect2.width()) {
                                }
                                this.mWidth = rect2.width();
                                this.mHeight = rect2.height();
                                if (this.mSurfaceHolder != null) {
                                }
                                threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                if (threadedRenderer != null) {
                                }
                                if (this.mStopped) {
                                }
                                int rootMeasureSpec322222222222222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                                int rootMeasureSpec2222222222222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                                performMeasure(rootMeasureSpec322222222222222, rootMeasureSpec2222222222222222);
                                int measuredWidth222222222222222 = view.getMeasuredWidth();
                                int measuredHeight222222222222222 = view.getMeasuredHeight();
                                if (layoutParams3.horizontalWeight > 0.0f) {
                                }
                                if (layoutParams3.verticalWeight > 0.0f) {
                                }
                                if (z20) {
                                }
                                z3 = true;
                                z4 = z6;
                                bool4 = bool6;
                                r24 = z39222222222;
                                if (this.mViewMeasureDeferred) {
                                }
                                if (this.mRelayoutRequested) {
                                }
                                str2 = str;
                                z10 = r24;
                                if (bool == null) {
                                }
                                prepareSurfaces();
                                this.mChildBoundingInsetsChanged = false;
                                if (z3) {
                                }
                                if (z11) {
                                }
                                if (z11) {
                                }
                                if (bool2 == null) {
                                }
                                if (z13) {
                                }
                                if (z12) {
                                }
                                if (z6) {
                                }
                                if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                                }
                                if (this.mFirst) {
                                }
                                if ((!z35 || this.mFirst) && z7) {
                                }
                                this.mFirst = false;
                                this.mWillDrawSoon = false;
                                this.mNewSurfaceNeeded = false;
                                this.mViewVisibility = i7;
                                this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                                if ((i8 & 1) != 0) {
                                }
                                this.mCheckIfCanDraw = !z9 || z10;
                                boolean dispatchOnPreDraw2222222222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                if (dispatchOnPreDraw2222222222222222) {
                                }
                                if (!z15) {
                                }
                                if (z7) {
                                }
                                this.mWasLastDrawCanceled = z15;
                                this.mLastTraversalWasVisible = z7;
                                if (this.mAttachInfo.mContentCaptureEvents != null) {
                                }
                                this.mIsInTraversal = false;
                                this.mRelayoutRequested = false;
                                if (!z15) {
                                }
                                setPreferredFrameRate(this.mPreferredFrameRate);
                                setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                                this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                                this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                                if (this.mFrameRateCategoryLowCount <= 0) {
                                }
                                this.mFrameRateCategoryLowCount = i11;
                                this.mPreferredFrameRateCategory = i10;
                                this.mPreferredFrameRate = -1.0f;
                                this.mIsFrameRateConflicted = false;
                                return;
                            }
                        }
                    }
                    layoutParams = layoutParams5;
                    if (this.mApplyInsetsRequested) {
                    }
                    if (z3) {
                    }
                    if (z3) {
                    }
                    boolean z362 = ((this.mDragResizing || !this.mPendingDragResizing) ? z4 : true) | ((z3 || !z5 || (this.mWidth == view.getMeasuredWidth() && this.mHeight == view.getMeasuredHeight() && ((layoutParams4.width != i || rect.width() >= i6 || rect.width() == this.mWidth) && (layoutParams4.height != i || rect.height() >= i4 || rect.height() == this.mHeight)))) ? z4 : true);
                    if (this.mAttachInfo.mTreeObserver.hasComputeInternalInsetsListeners()) {
                    }
                    generationId = this.mSurface.getGenerationId();
                    int i162 = i2;
                    if (i162 == 0) {
                    }
                    z8 = this.mWindowAttributesChanged;
                    if (z8) {
                    }
                    if (layoutParams2 != null) {
                    }
                    if (this.mFirst) {
                    }
                    rect2 = rect;
                    if (android.os.Trace.isTagEnabled(8L)) {
                    }
                    this.mForceNextWindowRelayout = false;
                    if (this.mSurfaceHolder != null) {
                    }
                    isValid = this.mSurface.isValid();
                    if (!this.mFirst) {
                        z25 = z3;
                        int relayoutWindow22 = relayoutWindow(layoutParams2, i162, z6);
                        if ((relayoutWindow22 & 16) == 16) {
                        }
                        z26 = this.mPendingDragResizing;
                        boolean z3822 = z25;
                        if (this.mSyncSeqId > this.mLastSyncSeqId) {
                        }
                        i8 = relayoutWindow22;
                        if ((relayoutWindow22 & 2) == 2) {
                        }
                        if (this.mSurfaceControl.isValid()) {
                        }
                        if (this.mRelayoutRequested) {
                        }
                        z29 = false;
                        boolean z4222 = this.mUpdateSurfaceNeeded;
                        this.mUpdateSurfaceNeeded = false;
                        z19 = z29;
                        if (this.mLastSurfaceSize.equals(this.mSurfaceSize)) {
                        }
                        if (this.mPendingAlwaysConsumeSystemBars != this.mAttachInfo.mAlwaysConsumeSystemBars) {
                        }
                        i7 = i162;
                        updateColorModeIfNeeded(layoutParams3.getColorMode(), layoutParams3.getDesiredHdrHeadroom());
                        if (!isValid) {
                        }
                        r2 = 0;
                        if (isValid) {
                        }
                        r15 = 0;
                        if (generationId == this.mSurface.getGenerationId()) {
                        }
                        if (this.mSurface.isValid()) {
                        }
                        bool9 = 0;
                        if (bool9 != 0) {
                        }
                        if (z31) {
                        }
                        if (updateCaptionInsets()) {
                        }
                        if (!z17) {
                        }
                        this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                        dispatchApplyInsets(view);
                        z17 = true;
                        if (r2 == 0) {
                        }
                        z18 = false;
                        if (this.mDragResizing == z26) {
                        }
                        if (!this.mUseMTRenderer) {
                        }
                        this.mAttachInfo.mWindowLeft = rect2.left;
                        this.mAttachInfo.mWindowTop = rect2.top;
                        if (this.mWidth == rect2.width()) {
                        }
                        this.mWidth = rect2.width();
                        this.mHeight = rect2.height();
                        if (this.mSurfaceHolder != null) {
                        }
                        threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                        if (threadedRenderer != null) {
                        }
                        if (this.mStopped) {
                        }
                        int rootMeasureSpec3222222222222222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                        int rootMeasureSpec22222222222222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                        performMeasure(rootMeasureSpec3222222222222222, rootMeasureSpec22222222222222222);
                        int measuredWidth2222222222222222 = view.getMeasuredWidth();
                        int measuredHeight2222222222222222 = view.getMeasuredHeight();
                        if (layoutParams3.horizontalWeight > 0.0f) {
                        }
                        if (layoutParams3.verticalWeight > 0.0f) {
                        }
                        if (z20) {
                        }
                        z3 = true;
                        z4 = z6;
                        bool4 = bool6;
                        r24 = z39222222222;
                        if (this.mViewMeasureDeferred) {
                        }
                        if (this.mRelayoutRequested) {
                        }
                        str2 = str;
                        z10 = r24;
                        if (bool == null) {
                        }
                        prepareSurfaces();
                        this.mChildBoundingInsetsChanged = false;
                        if (z3) {
                        }
                        if (z11) {
                        }
                        if (z11) {
                        }
                        if (bool2 == null) {
                        }
                        if (z13) {
                        }
                        if (z12) {
                        }
                        if (z6) {
                        }
                        if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                        }
                        if (this.mFirst) {
                        }
                        if ((!z35 || this.mFirst) && z7) {
                        }
                        this.mFirst = false;
                        this.mWillDrawSoon = false;
                        this.mNewSurfaceNeeded = false;
                        this.mViewVisibility = i7;
                        this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                        if ((i8 & 1) != 0) {
                        }
                        this.mCheckIfCanDraw = !z9 || z10;
                        boolean dispatchOnPreDraw22222222222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                        if (dispatchOnPreDraw22222222222222222) {
                        }
                        if (!z15) {
                        }
                        if (z7) {
                        }
                        this.mWasLastDrawCanceled = z15;
                        this.mLastTraversalWasVisible = z7;
                        if (this.mAttachInfo.mContentCaptureEvents != null) {
                        }
                        this.mIsInTraversal = false;
                        this.mRelayoutRequested = false;
                        if (!z15) {
                        }
                        setPreferredFrameRate(this.mPreferredFrameRate);
                        setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                        this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                        this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                        if (this.mFrameRateCategoryLowCount <= 0) {
                        }
                        this.mFrameRateCategoryLowCount = i11;
                        this.mPreferredFrameRateCategory = i10;
                        this.mPreferredFrameRate = -1.0f;
                        this.mIsFrameRateConflicted = false;
                        return;
                    }
                    z25 = z3;
                    this.mViewFrameInfo.flags |= 1;
                    int relayoutWindow222 = relayoutWindow(layoutParams2, i162, z6);
                    if ((relayoutWindow222 & 16) == 16) {
                    }
                    z26 = this.mPendingDragResizing;
                    boolean z38222 = z25;
                    if (this.mSyncSeqId > this.mLastSyncSeqId) {
                    }
                    i8 = relayoutWindow222;
                    if ((relayoutWindow222 & 2) == 2) {
                    }
                    if (this.mSurfaceControl.isValid()) {
                    }
                    if (this.mRelayoutRequested) {
                    }
                    z29 = false;
                    boolean z42222 = this.mUpdateSurfaceNeeded;
                    this.mUpdateSurfaceNeeded = false;
                    z19 = z29;
                    if (this.mLastSurfaceSize.equals(this.mSurfaceSize)) {
                    }
                    if (this.mPendingAlwaysConsumeSystemBars != this.mAttachInfo.mAlwaysConsumeSystemBars) {
                    }
                    i7 = i162;
                    updateColorModeIfNeeded(layoutParams3.getColorMode(), layoutParams3.getDesiredHdrHeadroom());
                    if (!isValid) {
                    }
                    r2 = 0;
                    if (isValid) {
                    }
                    r15 = 0;
                    if (generationId == this.mSurface.getGenerationId()) {
                    }
                    if (this.mSurface.isValid()) {
                    }
                    bool9 = 0;
                    if (bool9 != 0) {
                    }
                    if (z31) {
                    }
                    if (updateCaptionInsets()) {
                    }
                    if (!z17) {
                    }
                    this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                    dispatchApplyInsets(view);
                    z17 = true;
                    if (r2 == 0) {
                    }
                    z18 = false;
                    if (this.mDragResizing == z26) {
                    }
                    if (!this.mUseMTRenderer) {
                    }
                    this.mAttachInfo.mWindowLeft = rect2.left;
                    this.mAttachInfo.mWindowTop = rect2.top;
                    if (this.mWidth == rect2.width()) {
                    }
                    this.mWidth = rect2.width();
                    this.mHeight = rect2.height();
                    if (this.mSurfaceHolder != null) {
                    }
                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                    if (threadedRenderer != null) {
                    }
                    if (this.mStopped) {
                    }
                    int rootMeasureSpec32222222222222222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
                    int rootMeasureSpec222222222222222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
                    performMeasure(rootMeasureSpec32222222222222222, rootMeasureSpec222222222222222222);
                    int measuredWidth22222222222222222 = view.getMeasuredWidth();
                    int measuredHeight22222222222222222 = view.getMeasuredHeight();
                    if (layoutParams3.horizontalWeight > 0.0f) {
                    }
                    if (layoutParams3.verticalWeight > 0.0f) {
                    }
                    if (z20) {
                    }
                    z3 = true;
                    z4 = z6;
                    bool4 = bool6;
                    r24 = z39222222222;
                    if (this.mViewMeasureDeferred) {
                    }
                    if (this.mRelayoutRequested) {
                    }
                    str2 = str;
                    z10 = r24;
                    if (bool == null) {
                    }
                    prepareSurfaces();
                    this.mChildBoundingInsetsChanged = false;
                    if (z3) {
                    }
                    if (z11) {
                    }
                    if (z11) {
                    }
                    if (bool2 == null) {
                    }
                    if (z13) {
                    }
                    if (z12) {
                    }
                    if (z6) {
                    }
                    if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
                    }
                    if (this.mFirst) {
                    }
                    if ((!z35 || this.mFirst) && z7) {
                    }
                    this.mFirst = false;
                    this.mWillDrawSoon = false;
                    this.mNewSurfaceNeeded = false;
                    this.mViewVisibility = i7;
                    this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
                    if ((i8 & 1) != 0) {
                    }
                    this.mCheckIfCanDraw = !z9 || z10;
                    boolean dispatchOnPreDraw222222222222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                    if (dispatchOnPreDraw222222222222222222) {
                    }
                    if (!z15) {
                    }
                    if (z7) {
                    }
                    this.mWasLastDrawCanceled = z15;
                    this.mLastTraversalWasVisible = z7;
                    if (this.mAttachInfo.mContentCaptureEvents != null) {
                    }
                    this.mIsInTraversal = false;
                    this.mRelayoutRequested = false;
                    if (!z15) {
                    }
                    setPreferredFrameRate(this.mPreferredFrameRate);
                    setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
                    this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
                    this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
                    if (this.mFrameRateCategoryLowCount <= 0) {
                    }
                    this.mFrameRateCategoryLowCount = i11;
                    this.mPreferredFrameRateCategory = i10;
                    this.mPreferredFrameRate = -1.0f;
                    this.mIsFrameRateConflicted = false;
                    return;
                }
            }
            if (!this.mFirst) {
            }
            z25 = z3;
            this.mViewFrameInfo.flags |= 1;
            int relayoutWindow2222 = relayoutWindow(layoutParams2, i162, z6);
            if ((relayoutWindow2222 & 16) == 16) {
            }
            z26 = this.mPendingDragResizing;
            boolean z382222 = z25;
            if (this.mSyncSeqId > this.mLastSyncSeqId) {
            }
            i8 = relayoutWindow2222;
            if ((relayoutWindow2222 & 2) == 2) {
            }
            if (this.mSurfaceControl.isValid()) {
            }
            if (this.mRelayoutRequested) {
            }
            z29 = false;
            boolean z422222 = this.mUpdateSurfaceNeeded;
            this.mUpdateSurfaceNeeded = false;
            z19 = z29;
            if (this.mLastSurfaceSize.equals(this.mSurfaceSize)) {
            }
            if (this.mPendingAlwaysConsumeSystemBars != this.mAttachInfo.mAlwaysConsumeSystemBars) {
            }
            i7 = i162;
            updateColorModeIfNeeded(layoutParams3.getColorMode(), layoutParams3.getDesiredHdrHeadroom());
            if (!isValid) {
            }
            r2 = 0;
            if (isValid) {
            }
            r15 = 0;
            if (generationId == this.mSurface.getGenerationId()) {
            }
            if (this.mSurface.isValid()) {
            }
            bool9 = 0;
            if (bool9 != 0) {
            }
            if (z31) {
            }
            if (updateCaptionInsets()) {
            }
            if (!z17) {
            }
            this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
            dispatchApplyInsets(view);
            z17 = true;
            if (r2 == 0) {
            }
            z18 = false;
            if (this.mDragResizing == z26) {
            }
            if (!this.mUseMTRenderer) {
            }
            this.mAttachInfo.mWindowLeft = rect2.left;
            this.mAttachInfo.mWindowTop = rect2.top;
            if (this.mWidth == rect2.width()) {
            }
            this.mWidth = rect2.width();
            this.mHeight = rect2.height();
            if (this.mSurfaceHolder != null) {
            }
            threadedRenderer = this.mAttachInfo.mThreadedRenderer;
            if (threadedRenderer != null) {
            }
            if (this.mStopped) {
            }
            int rootMeasureSpec322222222222222222 = getRootMeasureSpec(this.mWidth, layoutParams3.width, layoutParams3.privateFlags);
            int rootMeasureSpec2222222222222222222 = getRootMeasureSpec(this.mHeight, layoutParams3.height, layoutParams3.privateFlags);
            performMeasure(rootMeasureSpec322222222222222222, rootMeasureSpec2222222222222222222);
            int measuredWidth222222222222222222 = view.getMeasuredWidth();
            int measuredHeight222222222222222222 = view.getMeasuredHeight();
            if (layoutParams3.horizontalWeight > 0.0f) {
            }
            if (layoutParams3.verticalWeight > 0.0f) {
            }
            if (z20) {
            }
            z3 = true;
            z4 = z6;
            bool4 = bool6;
            r24 = z39222222222;
            if (this.mViewMeasureDeferred) {
            }
            if (this.mRelayoutRequested) {
            }
            str2 = str;
            z10 = r24;
            if (bool == null) {
            }
            prepareSurfaces();
            this.mChildBoundingInsetsChanged = false;
            if (z3) {
            }
            if (z11) {
            }
            if (z11) {
            }
            if (bool2 == null) {
            }
            if (z13) {
            }
            if (z12) {
            }
            if (z6) {
            }
            if (!(z14 | (java.util.Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null))) {
            }
            if (this.mFirst) {
            }
            if ((!z35 || this.mFirst) && z7) {
            }
            this.mFirst = false;
            this.mWillDrawSoon = false;
            this.mNewSurfaceNeeded = false;
            this.mViewVisibility = i7;
            this.mImeFocusController.onTraversal(!this.mAttachInfo.mHasWindowFocus && z7, this.mWindowAttributes);
            if ((i8 & 1) != 0) {
            }
            this.mCheckIfCanDraw = !z9 || z10;
            boolean dispatchOnPreDraw2222222222222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
            if (dispatchOnPreDraw2222222222222222222) {
            }
            if (!z15) {
            }
            if (z7) {
            }
            this.mWasLastDrawCanceled = z15;
            this.mLastTraversalWasVisible = z7;
            if (this.mAttachInfo.mContentCaptureEvents != null) {
            }
            this.mIsInTraversal = false;
            this.mRelayoutRequested = false;
            if (!z15) {
            }
            setPreferredFrameRate(this.mPreferredFrameRate);
            setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
            this.mFrameRateCategoryHighCount = this.mFrameRateCategoryHighCount <= 0 ? this.mFrameRateCategoryHighCount - 1 : this.mFrameRateCategoryHighCount;
            this.mFrameRateCategoryNormalCount = this.mFrameRateCategoryNormalCount <= 0 ? this.mFrameRateCategoryNormalCount - 1 : this.mFrameRateCategoryNormalCount;
            if (this.mFrameRateCategoryLowCount <= 0) {
            }
            this.mFrameRateCategoryLowCount = i11;
            this.mPreferredFrameRateCategory = i10;
            this.mPreferredFrameRate = -1.0f;
            this.mIsFrameRateConflicted = false;
            return;
        } finally {
            long j2 = 8;
            if (android.os.Trace.isTagEnabled(j2)) {
                android.os.Trace.traceEnd(j2);
            }
        }
        z = false;
        boolean shouldOptimizeMeasure2 = shouldOptimizeMeasure(layoutParams4);
        android.graphics.Rect rect62 = this.mWinFrame;
        if (this.mFirst) {
        }
        if (z35) {
        }
        if (this.mAttachInfo.mWindowVisibility != 0) {
        }
        getRunQueue().executeActions(this.mAttachInfo.mHandler);
        if (this.mFirst) {
        }
        if (this.mLayoutRequested) {
        }
        if (z3) {
        }
        if (!collectViewAttributes()) {
        }
        if (this.mAttachInfo.mForceReportNewAttributes) {
        }
        if (!this.mFirst) {
        }
        this.mAttachInfo.mViewVisibilityChanged = z4;
        i5 = this.mSoftInputMode & 240;
        if (i5 == 0) {
        }
        layoutParams = layoutParams5;
        if (this.mApplyInsetsRequested) {
        }
        if (z3) {
        }
        if (z3) {
        }
        boolean z3622 = ((this.mDragResizing || !this.mPendingDragResizing) ? z4 : true) | ((z3 || !z5 || (this.mWidth == view.getMeasuredWidth() && this.mHeight == view.getMeasuredHeight() && ((layoutParams4.width != i || rect.width() >= i6 || rect.width() == this.mWidth) && (layoutParams4.height != i || rect.height() >= i4 || rect.height() == this.mHeight)))) ? z4 : true);
        if (this.mAttachInfo.mTreeObserver.hasComputeInternalInsetsListeners()) {
        }
        generationId = this.mSurface.getGenerationId();
        int i1622 = i2;
        if (i1622 == 0) {
        }
        z8 = this.mWindowAttributesChanged;
        if (z8) {
        }
        if (layoutParams2 != null) {
        }
        if (this.mFirst) {
        }
        rect2 = rect;
        if (android.os.Trace.isTagEnabled(8L)) {
        }
        this.mForceNextWindowRelayout = false;
        if (this.mSurfaceHolder != null) {
        }
        isValid = this.mSurface.isValid();
    }

    private void createSyncIfNeeded() {
        if (isInWMSRequestedSync() || !this.mReportNextDraw) {
            return;
        }
        final int i = this.mSyncSeqId;
        this.mWmsRequestSyncGroupState = 1;
        this.mWmsRequestSyncGroup = new android.window.SurfaceSyncGroup("wmsSync-" + this.mTag, new java.util.function.Consumer() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.view.ViewRootImpl.this.lambda$createSyncIfNeeded$4(i, (android.view.SurfaceControl.Transaction) obj);
            }
        });
        this.mWmsRequestSyncGroup.add(this, (java.lang.Runnable) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSyncIfNeeded$4(final int i, android.view.SurfaceControl.Transaction transaction) {
        this.mWmsRequestSyncGroupState = 3;
        if (this.mWindowSession instanceof android.os.Binder) {
            final android.view.SurfaceControl.Transaction transaction2 = new android.view.SurfaceControl.Transaction();
            transaction2.merge(transaction);
            this.mHandler.postAtFrontOfQueue(new java.lang.Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.ViewRootImpl.this.lambda$createSyncIfNeeded$3(transaction2, i);
                }
            });
            return;
        }
        lambda$createSyncIfNeeded$3(transaction, i);
    }

    void notifySensitiveContentAppProtection(boolean z) {
        try {
            if (this.mSensitiveContentProtectionService == null) {
                return;
            }
            this.mSensitiveContentProtectionService.setSensitiveContentProtection(getWindowToken(), this.mContext.getPackageName(), z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Unable to protect sensitive content during screen share", e);
        }
    }

    private void notifyContentCaptureEvents() {
        if (!isContentCaptureEnabled()) {
            this.mAttachInfo.mContentCaptureEvents = null;
            return;
        }
        android.view.contentcapture.ContentCaptureManager contentCaptureManager = this.mAttachInfo.mContentCaptureManager;
        if (contentCaptureManager != null && this.mAttachInfo.mContentCaptureEvents != null) {
            contentCaptureManager.getMainContentCaptureSession().notifyContentCaptureEvents(this.mAttachInfo.mContentCaptureEvents);
        }
        this.mAttachInfo.mContentCaptureEvents = null;
    }

    private void notifyHolderSurfaceDestroyed() {
        this.mSurfaceHolder.ungetCallbacks();
        android.view.SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
        if (callbacks != null) {
            for (android.view.SurfaceHolder.Callback callback : callbacks) {
                callback.surfaceDestroyed(this.mSurfaceHolder);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeHandleWindowMove(android.graphics.Rect rect) {
        boolean z = (this.mAttachInfo.mWindowLeft == rect.left && this.mAttachInfo.mWindowTop == rect.top) ? false : true;
        if (z) {
            this.mAttachInfo.mWindowLeft = rect.left;
            this.mAttachInfo.mWindowTop = rect.top;
        }
        if (z || this.mAttachInfo.mNeedsUpdateLightCenter) {
            if (this.mAttachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.setLightCenter(this.mAttachInfo);
            }
            this.mAttachInfo.mNeedsUpdateLightCenter = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWindowFocusChanged() {
        synchronized (this) {
            if (this.mWindowFocusChanged) {
                this.mWindowFocusChanged = false;
                boolean z = this.mUpcomingWindowFocus;
                if (z) {
                    this.mInsetsController.onWindowFocusGained(getFocusedViewOrNull() != null);
                } else {
                    this.mInsetsController.onWindowFocusLost();
                }
                if (this.mAdded) {
                    dispatchFocusEvent(z, false);
                    this.mImeFocusController.onPostWindowFocus(getFocusedViewOrNull(), z, this.mWindowAttributes);
                    if (z) {
                        this.mWindowAttributes.softInputMode &= -257;
                        ((android.view.WindowManager.LayoutParams) this.mView.getLayoutParams()).softInputMode &= -257;
                        maybeFireAccessibilityWindowStateChangedEvent();
                        fireAccessibilityFocusEventIfHasFocusedNode();
                    } else if (this.mPointerCapture) {
                        handlePointerCaptureChanged(false);
                    }
                }
                this.mFirstInputStage.onWindowFocusChanged(z);
                if (z) {
                    handleContentCaptureFlush();
                }
            }
        }
    }

    public void dispatchCompatFakeFocus() {
        boolean z;
        synchronized (this) {
            z = this.mWindowFocusChanged && this.mUpcomingWindowFocus;
        }
        boolean z2 = this.mAttachInfo.mHasWindowFocus;
        if (z || z2) {
            return;
        }
        android.util.EventLog.writeEvent(LOGTAG_INPUT_FOCUS, "Giving fake focus to " + this.mBasePackageName, "reason=unity bug workaround");
        dispatchFocusEvent(true, true);
        android.util.EventLog.writeEvent(LOGTAG_INPUT_FOCUS, "Removing fake focus from " + this.mBasePackageName, "reason=timeout callback");
        dispatchFocusEvent(false, true);
    }

    private void dispatchFocusEvent(boolean z, boolean z2) {
        profileRendering(z);
        if (z && this.mAttachInfo.mThreadedRenderer != null && this.mSurface.isValid()) {
            this.mFullRedrawNeeded = true;
            try {
                this.mAttachInfo.mThreadedRenderer.initializeIfNeeded(this.mWidth, this.mHeight, this.mAttachInfo, this.mSurface, this.mWindowAttributes.surfaceInsets);
            } catch (android.view.Surface.OutOfResourcesException e) {
                android.util.Log.e(this.mTag, "OutOfResourcesException locking surface", e);
                try {
                    if (!this.mWindowSession.outOfMemory(this.mWindow)) {
                        android.util.Slog.w(this.mTag, "No processes killed for memory; killing self");
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                } catch (android.os.RemoteException e2) {
                }
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(6), 500L);
                return;
            }
        }
        this.mAttachInfo.mHasWindowFocus = z;
        if (!z2) {
            this.mImeFocusController.onPreWindowFocus(z, this.mWindowAttributes);
        }
        if (this.mView != null) {
            this.mAttachInfo.mKeyDispatchState.reset();
            this.mView.dispatchWindowFocusChanged(z);
            this.mAttachInfo.mTreeObserver.dispatchOnWindowFocusChange(z);
            if (this.mAttachInfo.mTooltipHost != null) {
                this.mAttachInfo.mTooltipHost.hideTooltip();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWindowTouchModeChanged() {
        boolean z;
        synchronized (this) {
            z = this.mUpcomingInTouchMode;
        }
        ensureTouchModeLocally(z);
    }

    private void maybeFireAccessibilityWindowStateChangedEvent() {
        if (!(this.mWindowAttributes != null && this.mWindowAttributes.type == 2005) && this.mView != null) {
            this.mView.sendAccessibilityEvent(32);
        }
    }

    private void fireAccessibilityFocusEventIfHasFocusedNode() {
        android.view.View findFocus;
        if (!this.mAccessibilityManager.isEnabled() || (findFocus = this.mView.findFocus()) == null) {
            return;
        }
        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = findFocus.getAccessibilityNodeProvider();
        if (accessibilityNodeProvider == null) {
            findFocus.sendAccessibilityEvent(8);
            return;
        }
        android.view.accessibility.AccessibilityNodeInfo findFocusedVirtualNode = findFocusedVirtualNode(accessibilityNodeProvider);
        if (findFocusedVirtualNode != null) {
            int virtualDescendantId = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(findFocusedVirtualNode.getSourceNodeId());
            android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(8);
            obtain.setSource(findFocus, virtualDescendantId);
            obtain.setPackageName(findFocusedVirtualNode.getPackageName());
            obtain.setChecked(findFocusedVirtualNode.isChecked());
            obtain.setContentDescription(findFocusedVirtualNode.getContentDescription());
            obtain.setPassword(findFocusedVirtualNode.isPassword());
            obtain.getText().add(findFocusedVirtualNode.getText());
            obtain.setEnabled(findFocusedVirtualNode.isEnabled());
            findFocus.getParent().requestSendAccessibilityEvent(findFocus, obtain);
            findFocusedVirtualNode.recycle();
        }
    }

    private android.view.accessibility.AccessibilityNodeInfo findFocusedVirtualNode(android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider) {
        android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo;
        android.view.accessibility.AccessibilityNodeInfo findFocus = accessibilityNodeProvider.findFocus(1);
        if (findFocus != null) {
            return findFocus;
        }
        if (!this.mContext.isAutofillCompatibilityEnabled() || (createAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(-1)) == null) {
            return null;
        }
        if (createAccessibilityNodeInfo.isFocused()) {
            return createAccessibilityNodeInfo;
        }
        java.util.ArrayDeque arrayDeque = new java.util.ArrayDeque();
        arrayDeque.offer(createAccessibilityNodeInfo);
        while (!arrayDeque.isEmpty()) {
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = (android.view.accessibility.AccessibilityNodeInfo) arrayDeque.poll();
            android.util.LongArray childNodeIds = accessibilityNodeInfo.getChildNodeIds();
            if (childNodeIds != null && childNodeIds.size() > 0) {
                int size = childNodeIds.size();
                for (int i = 0; i < size; i++) {
                    android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo2 = accessibilityNodeProvider.createAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(childNodeIds.get(i)));
                    if (createAccessibilityNodeInfo2 != null) {
                        if (createAccessibilityNodeInfo2.isFocused()) {
                            return createAccessibilityNodeInfo2;
                        }
                        arrayDeque.offer(createAccessibilityNodeInfo2);
                    }
                }
                accessibilityNodeInfo.recycle();
            }
        }
        return null;
    }

    private void handleOutOfResourcesException(android.view.Surface.OutOfResourcesException outOfResourcesException) {
        android.util.Log.e(this.mTag, "OutOfResourcesException initializing HW surface", outOfResourcesException);
        try {
            if (!this.mWindowSession.outOfMemory(this.mWindow) && android.os.Process.myUid() != 1000) {
                android.util.Slog.w(this.mTag, "No processes killed for memory; killing self");
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        } catch (android.os.RemoteException e) {
        }
        this.mLayoutRequested = true;
    }

    private void performMeasure(int i, int i2) {
        if (this.mView == null) {
            return;
        }
        android.os.Trace.traceBegin(8L, "measure");
        try {
            this.mView.measure(i, i2);
            android.os.Trace.traceEnd(8L);
            this.mMeasuredWidth = this.mView.getMeasuredWidth();
            this.mMeasuredHeight = this.mView.getMeasuredHeight();
            this.mViewMeasureDeferred = false;
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(8L);
            throw th;
        }
    }

    boolean isInLayout() {
        return this.mInLayout;
    }

    boolean requestLayoutDuringLayout(android.view.View view) {
        if (view.mParent == null || view.mAttachInfo == null) {
            return true;
        }
        if (!this.mLayoutRequesters.contains(view)) {
            this.mLayoutRequesters.add(view);
        }
        return !this.mHandlingLayoutInLayoutRequest;
    }

    private void performLayout(android.view.WindowManager.LayoutParams layoutParams, int i, int i2) {
        java.util.ArrayList<android.view.View> validLayoutRequesters;
        this.mScrollMayChange = true;
        this.mInLayout = true;
        android.view.View view = this.mView;
        if (view == null) {
            return;
        }
        android.os.Trace.traceBegin(8L, android.media.TtmlUtils.TAG_LAYOUT);
        try {
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            this.mInLayout = false;
            if (this.mLayoutRequesters.size() > 0 && (validLayoutRequesters = getValidLayoutRequesters(this.mLayoutRequesters, false)) != null) {
                this.mHandlingLayoutInLayoutRequest = true;
                int size = validLayoutRequesters.size();
                for (int i3 = 0; i3 < size; i3++) {
                    android.view.View view2 = validLayoutRequesters.get(i3);
                    android.util.Log.w("View", "requestLayout() improperly called by " + view2 + " during layout: running second layout pass");
                    view2.requestLayout();
                }
                measureHierarchy(view, layoutParams, this.mView.getContext().getResources(), i, i2, false);
                this.mInLayout = true;
                view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                this.mHandlingLayoutInLayoutRequest = false;
                final java.util.ArrayList<android.view.View> validLayoutRequesters2 = getValidLayoutRequesters(this.mLayoutRequesters, true);
                if (validLayoutRequesters2 != null) {
                    getRunQueue().post(new java.lang.Runnable() { // from class: android.view.ViewRootImpl.5
                        @Override // java.lang.Runnable
                        public void run() {
                            int size2 = validLayoutRequesters2.size();
                            for (int i4 = 0; i4 < size2; i4++) {
                                android.view.View view3 = (android.view.View) validLayoutRequesters2.get(i4);
                                android.util.Log.w("View", "requestLayout() improperly called by " + view3 + " during second layout pass: posting in next frame");
                                view3.requestLayout();
                            }
                        }
                    });
                }
            }
            android.os.Trace.traceEnd(8L);
            this.mInLayout = false;
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(8L);
            throw th;
        }
    }

    private java.util.ArrayList<android.view.View> getValidLayoutRequesters(java.util.ArrayList<android.view.View> arrayList, boolean z) {
        boolean z2;
        int size = arrayList.size();
        java.util.ArrayList<android.view.View> arrayList2 = null;
        for (int i = 0; i < size; i++) {
            android.view.View view = arrayList.get(i);
            if (view != null && view.mAttachInfo != null && view.mParent != null && (z || (view.mPrivateFlags & 4096) == 4096)) {
                android.view.View view2 = view;
                while (true) {
                    if (view2 == null) {
                        z2 = false;
                        break;
                    }
                    if ((view2.mViewFlags & 12) == 8) {
                        z2 = true;
                        break;
                    }
                    if (view2.mParent instanceof android.view.View) {
                        view2 = (android.view.View) view2.mParent;
                    } else {
                        view2 = null;
                    }
                }
                if (!z2) {
                    if (arrayList2 == null) {
                        arrayList2 = new java.util.ArrayList<>();
                    }
                    arrayList2.add(view);
                }
            }
        }
        if (!z) {
            for (int i2 = 0; i2 < size; i2++) {
                android.view.View view3 = arrayList.get(i2);
                while (view3 != null && (view3.mPrivateFlags & 4096) != 0) {
                    view3.mPrivateFlags &= -4097;
                    if (view3.mParent instanceof android.view.View) {
                        view3 = (android.view.View) view3.mParent;
                    } else {
                        view3 = null;
                    }
                }
            }
        }
        arrayList.clear();
        return arrayList2;
    }

    @Override // android.view.ViewParent
    public void requestTransparentRegion(android.view.View view) {
        checkThread();
        if (this.mView != view) {
            return;
        }
        if ((this.mView.mPrivateFlags & 512) == 0) {
            this.mView.mPrivateFlags |= 512;
            this.mWindowAttributesChanged = true;
        }
        requestLayout();
    }

    private static int getRootMeasureSpec(int i, int i2, int i3) {
        if ((i3 & 4096) != 0) {
            i2 = -1;
        }
        switch (i2) {
            case -2:
                return android.view.View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE);
            case -1:
                return android.view.View.MeasureSpec.makeMeasureSpec(i, 1073741824);
            default:
                return android.view.View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
        }
    }

    @Override // android.view.ThreadedRenderer.DrawCallbacks
    public void onPreDraw(android.graphics.RecordingCanvas recordingCanvas) {
        if (this.mCurScrollY != 0 && this.mHardwareYOffset != 0 && this.mAttachInfo.mThreadedRenderer.isOpaque()) {
            recordingCanvas.drawColor(-16777216);
        }
        recordingCanvas.translate(-this.mHardwareXOffset, -this.mHardwareYOffset);
    }

    @Override // android.view.ThreadedRenderer.DrawCallbacks
    public void onPostDraw(android.graphics.RecordingCanvas recordingCanvas) {
        drawAccessibilityFocusedDrawableIfNeeded(recordingCanvas);
        if (this.mUseMTRenderer) {
            for (int size = this.mWindowCallbacks.size() - 1; size >= 0; size--) {
                this.mWindowCallbacks.get(size).onPostDraw(recordingCanvas);
            }
        }
    }

    void outputDisplayList(android.view.View view) {
        view.mRenderNode.output();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void profileRendering(boolean z) {
        if (this.mProfileRendering) {
            this.mRenderProfilingEnabled = z;
            if (this.mRenderProfiler != null) {
                this.mChoreographer.removeFrameCallback(this.mRenderProfiler);
            }
            if (this.mRenderProfilingEnabled) {
                if (this.mRenderProfiler == null) {
                    this.mRenderProfiler = new android.view.Choreographer.FrameCallback() { // from class: android.view.ViewRootImpl.6
                        @Override // android.view.Choreographer.FrameCallback
                        public void doFrame(long j) {
                            android.view.ViewRootImpl.this.mDirty.set(0, 0, android.view.ViewRootImpl.this.mWidth, android.view.ViewRootImpl.this.mHeight);
                            android.view.ViewRootImpl.this.scheduleTraversals();
                            if (android.view.ViewRootImpl.this.mRenderProfilingEnabled) {
                                android.view.ViewRootImpl.this.mChoreographer.postFrameCallback(android.view.ViewRootImpl.this.mRenderProfiler);
                            }
                        }
                    };
                }
                this.mChoreographer.postFrameCallback(this.mRenderProfiler);
                return;
            }
            this.mRenderProfiler = null;
        }
    }

    private void trackFPS() {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (this.mFpsStartTime < 0) {
            this.mFpsPrevTime = currentTimeMillis;
            this.mFpsStartTime = currentTimeMillis;
            this.mFpsNumFrames = 0;
            return;
        }
        this.mFpsNumFrames++;
        java.lang.String hexString = java.lang.Integer.toHexString(java.lang.System.identityHashCode(this));
        long j = currentTimeMillis - this.mFpsPrevTime;
        long j2 = currentTimeMillis - this.mFpsStartTime;
        android.util.Log.v(this.mTag, "0x" + hexString + "\tFrame time:\t" + j);
        this.mFpsPrevTime = currentTimeMillis;
        if (j2 > 1000) {
            android.util.Log.v(this.mTag, "0x" + hexString + "\tFPS:\t" + ((this.mFpsNumFrames * 1000.0f) / j2));
            this.mFpsStartTime = currentTimeMillis;
            this.mFpsNumFrames = 0;
        }
    }

    private void collectFrameRateDecisionMetrics() {
        if (!android.os.Trace.isEnabled()) {
            if (this.mPreviousFrameDrawnTime > 0) {
                this.mPreviousFrameDrawnTime = -1L;
            }
        } else {
            if (this.mPreviousFrameDrawnTime < 0) {
                this.mPreviousFrameDrawnTime = this.mChoreographer.getExpectedPresentationTimeNanos();
                return;
            }
            long expectedPresentationTimeNanos = this.mChoreographer.getExpectedPresentationTimeNanos();
            long j = expectedPresentationTimeNanos - this.mPreviousFrameDrawnTime;
            if (j <= 0) {
                return;
            }
            android.os.Trace.setCounter(this.mFpsTraceName, 1000000000 / j);
            this.mPreviousFrameDrawnTime = expectedPresentationTimeNanos;
            android.os.Trace.setCounter(this.mLargestViewTraceName, (long) (this.mLargestChildPercentage * 100.0f));
            this.mLargestChildPercentage = 0.0f;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: reportDrawFinished, reason: merged with bridge method [inline-methods] */
    public void lambda$createSyncIfNeeded$3(android.view.SurfaceControl.Transaction transaction, int i) {
        logAndTrace("reportDrawFinished seqId=" + i);
        try {
            try {
                this.mWindowSession.finishDrawing(this.mWindow, transaction, i);
                if (transaction == null) {
                    return;
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(this.mTag, "Unable to report draw finished", e);
                if (transaction != null) {
                    transaction.apply();
                }
                if (transaction == null) {
                    return;
                }
            }
            transaction.clear();
        } catch (java.lang.Throwable th) {
            if (transaction != null) {
                transaction.clear();
            }
            throw th;
        }
    }

    public boolean isHardwareEnabled() {
        return this.mAttachInfo.mThreadedRenderer != null && this.mAttachInfo.mThreadedRenderer.isEnabled();
    }

    public boolean isInWMSRequestedSync() {
        return this.mWmsRequestSyncGroup != null;
    }

    private void addFrameCommitCallbackIfNeeded() {
        if (!isHardwareEnabled()) {
            return;
        }
        final java.util.ArrayList<java.lang.Runnable> captureFrameCommitCallbacks = this.mAttachInfo.mTreeObserver.captureFrameCommitCallbacks();
        if (!(captureFrameCommitCallbacks != null && captureFrameCommitCallbacks.size() > 0)) {
            return;
        }
        this.mAttachInfo.mThreadedRenderer.setFrameCommitCallback(new android.graphics.HardwareRenderer.FrameCommitCallback() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda12
            @Override // android.graphics.HardwareRenderer.FrameCommitCallback
            public final void onFrameCommit(boolean z) {
                android.view.ViewRootImpl.this.lambda$addFrameCommitCallbackIfNeeded$6(captureFrameCommitCallbacks, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addFrameCommitCallbackIfNeeded$6(final java.util.ArrayList arrayList, boolean z) {
        this.mHandler.postAtFrontOfQueue(new java.lang.Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.view.ViewRootImpl.lambda$addFrameCommitCallbackIfNeeded$5(arrayList);
            }
        });
    }

    static /* synthetic */ void lambda$addFrameCommitCallbackIfNeeded$5(java.util.ArrayList arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            ((java.lang.Runnable) arrayList.get(i)).run();
        }
    }

    private void registerCallbackForPendingTransactions() {
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        transaction.merge(this.mPendingTransaction);
        registerRtFrameCallback(new android.view.ViewRootImpl.AnonymousClass7(transaction));
    }

    /* renamed from: android.view.ViewRootImpl$7, reason: invalid class name */
    class AnonymousClass7 implements android.graphics.HardwareRenderer.FrameDrawingCallback {
        final /* synthetic */ android.view.SurfaceControl.Transaction val$t;

        AnonymousClass7(android.view.SurfaceControl.Transaction transaction) {
            this.val$t = transaction;
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public android.graphics.HardwareRenderer.FrameCommitCallback onFrameDraw(int i, final long j) {
            android.view.ViewRootImpl.this.mergeWithNextTransaction(this.val$t, j);
            if ((i & 6) != 0) {
                android.view.ViewRootImpl.this.mBlastBufferQueue.applyPendingTransactions(j);
                return null;
            }
            return new android.graphics.HardwareRenderer.FrameCommitCallback() { // from class: android.view.ViewRootImpl$7$$ExternalSyntheticLambda0
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z) {
                    android.view.ViewRootImpl.AnonymousClass7.this.lambda$onFrameDraw$0(j, z);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$0(long j, boolean z) {
            if (!z) {
                android.view.ViewRootImpl.this.logAndTrace("Transaction not synced due to no frame drawn");
                android.view.ViewRootImpl.this.mBlastBufferQueue.applyPendingTransactions(j);
            }
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public void onFrameDraw(long j) {
        }
    }

    private boolean performDraw(final android.window.SurfaceSyncGroup surfaceSyncGroup) {
        final android.view.SurfaceControl.Transaction transaction;
        this.mLastPerformDrawSkippedReason = null;
        if (this.mAttachInfo.mDisplayState == 1 && !this.mReportNextDraw) {
            this.mLastPerformDrawSkippedReason = "screen_off";
            if (!this.mLastDrawScreenOff) {
                logAndTrace("Not drawing due to screen off");
            }
            this.mLastDrawScreenOff = true;
            return false;
        }
        if (this.mView == null) {
            this.mLastPerformDrawSkippedReason = "no_root_view";
            return false;
        }
        if (this.mLastDrawScreenOff) {
            logAndTrace("Resumed drawing after screen turned on");
            this.mLastDrawScreenOff = false;
        }
        boolean z = this.mFullRedrawNeeded || surfaceSyncGroup != null;
        this.mFullRedrawNeeded = false;
        this.mIsDrawing = true;
        android.os.Trace.traceBegin(8L, "draw-" + this.mTag);
        addFrameCommitCallbackIfNeeded();
        try {
            boolean draw = draw(z, surfaceSyncGroup, this.mSyncBuffer);
            if (this.mAttachInfo.mThreadedRenderer != null && !draw) {
                this.mAttachInfo.mThreadedRenderer.setFrameCallback(null);
            }
            this.mIsDrawing = false;
            android.os.Trace.traceEnd(8L);
            if (this.mAttachInfo.mPendingAnimatingRenderNodes != null) {
                int size = this.mAttachInfo.mPendingAnimatingRenderNodes.size();
                for (int i = 0; i < size; i++) {
                    this.mAttachInfo.mPendingAnimatingRenderNodes.get(i).endAllAnimators();
                }
                this.mAttachInfo.mPendingAnimatingRenderNodes.clear();
            }
            if (!draw && this.mHasPendingTransactions) {
                transaction = new android.view.SurfaceControl.Transaction();
                transaction.merge(this.mPendingTransaction);
            } else {
                transaction = null;
            }
            if (this.mReportNextDraw) {
                if (this.mWindowDrawCountDown != null) {
                    try {
                        this.mWindowDrawCountDown.await();
                    } catch (java.lang.InterruptedException e) {
                        android.util.Log.e(this.mTag, "Window redraw count down interrupted!");
                    }
                    this.mWindowDrawCountDown = null;
                }
                if (this.mAttachInfo.mThreadedRenderer != null) {
                    this.mAttachInfo.mThreadedRenderer.setStopped(this.mStopped);
                }
                if (this.mSurfaceHolder != null && this.mSurface.isValid()) {
                    new com.android.internal.view.SurfaceCallbackHelper(new java.lang.Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.view.ViewRootImpl.this.lambda$performDraw$7(surfaceSyncGroup, transaction);
                        }
                    }).dispatchSurfaceRedrawNeededAsync(this.mSurfaceHolder, this.mSurfaceHolder.getCallbacks());
                    draw = true;
                } else if (!draw && this.mAttachInfo.mThreadedRenderer != null) {
                    this.mAttachInfo.mThreadedRenderer.fence();
                }
            }
            if (!draw) {
                handleSyncRequestWhenNoAsyncDraw(surfaceSyncGroup, transaction != null, transaction, "no async report");
            }
            if (this.mPerformContentCapture) {
                performContentCaptureInitialReport();
            }
            return true;
        } catch (java.lang.Throwable th) {
            this.mIsDrawing = false;
            android.os.Trace.traceEnd(8L);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performDraw$7(android.window.SurfaceSyncGroup surfaceSyncGroup, android.view.SurfaceControl.Transaction transaction) {
        handleSyncRequestWhenNoAsyncDraw(surfaceSyncGroup, transaction != null, transaction, "SurfaceHolder");
    }

    private void handleSyncRequestWhenNoAsyncDraw(android.window.SurfaceSyncGroup surfaceSyncGroup, boolean z, android.view.SurfaceControl.Transaction transaction, java.lang.String str) {
        if (surfaceSyncGroup != null) {
            if (z && transaction != null) {
                surfaceSyncGroup.addTransaction(transaction);
            }
            surfaceSyncGroup.markSyncReady();
            return;
        }
        if (z && transaction != null) {
            android.os.Trace.instant(8L, "Transaction not synced due to " + str + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.mTag);
            transaction.apply();
        }
    }

    private boolean isContentCaptureEnabled() {
        switch (this.mContentCaptureEnabled) {
            case 0:
                boolean isContentCaptureReallyEnabled = isContentCaptureReallyEnabled();
                this.mContentCaptureEnabled = isContentCaptureReallyEnabled ? 1 : 2;
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                android.util.Log.w(TAG, "isContentCaptureEnabled(): invalid state " + this.mContentCaptureEnabled);
                break;
        }
        return false;
    }

    private boolean isContentCaptureReallyEnabled() {
        android.view.contentcapture.ContentCaptureManager contentCaptureManager;
        return (this.mContext.getContentCaptureOptions() == null || (contentCaptureManager = this.mAttachInfo.getContentCaptureManager(this.mContext)) == null || !contentCaptureManager.isContentCaptureEnabled()) ? false : true;
    }

    private void performContentCaptureInitialReport() {
        this.mPerformContentCapture = false;
        android.view.View view = this.mView;
        try {
            if (!isContentCaptureEnabled()) {
                return;
            }
            if (android.os.Trace.isTagEnabled(8L)) {
                android.os.Trace.traceBegin(8L, "dispatchContentCapture() for " + getClass().getSimpleName());
            }
            if (this.mAttachInfo.mContentCaptureManager != null) {
                android.view.contentcapture.ContentCaptureSession mainContentCaptureSession = this.mAttachInfo.mContentCaptureManager.getMainContentCaptureSession();
                mainContentCaptureSession.notifyWindowBoundsChanged(mainContentCaptureSession.getId(), getConfiguration().windowConfiguration.getBounds());
            }
            view.dispatchInitialProvideContentCaptureStructure();
        } finally {
            android.os.Trace.traceEnd(8L);
        }
    }

    private void handleContentCaptureFlush() {
        try {
            if (isContentCaptureEnabled()) {
                if (android.os.Trace.isTagEnabled(8L)) {
                    android.os.Trace.traceBegin(8L, "flushContentCapture for " + getClass().getSimpleName());
                }
                android.view.contentcapture.ContentCaptureManager contentCaptureManager = this.mAttachInfo.mContentCaptureManager;
                if (contentCaptureManager == null) {
                    android.util.Log.w(TAG, "No ContentCapture on AttachInfo");
                } else {
                    contentCaptureManager.flush(2);
                }
            }
        } finally {
            android.os.Trace.traceEnd(8L);
        }
    }

    private boolean draw(boolean z, android.window.SurfaceSyncGroup surfaceSyncGroup, boolean z2) {
        int i;
        boolean z3;
        android.view.Surface surface = this.mSurface;
        boolean z4 = false;
        if (!surface.isValid()) {
            return false;
        }
        if (sToolkitMetricsForFrameRateDecisionFlagValue) {
            collectFrameRateDecisionMetrics();
        }
        if (!sFirstDrawComplete) {
            synchronized (sFirstDrawHandlers) {
                sFirstDrawComplete = true;
                int size = sFirstDrawHandlers.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mHandler.post(sFirstDrawHandlers.get(i2));
                }
            }
        }
        scrollToRectOrFocus(null, false);
        if (this.mAttachInfo.mViewScrollChanged) {
            this.mAttachInfo.mViewScrollChanged = false;
            this.mAttachInfo.mTreeObserver.dispatchOnScrollChanged();
        }
        boolean z5 = this.mScroller != null && this.mScroller.computeScrollOffset();
        if (z5) {
            i = this.mScroller.getCurrY();
        } else {
            i = this.mScrollY;
        }
        if (this.mCurScrollY == i) {
            z3 = z;
        } else {
            this.mCurScrollY = i;
            if (this.mView instanceof com.android.internal.view.RootViewSurfaceTaker) {
                ((com.android.internal.view.RootViewSurfaceTaker) this.mView).onRootViewScrollYChanged(this.mCurScrollY);
            }
            z3 = true;
        }
        float f = this.mAttachInfo.mApplicationScale;
        boolean z6 = this.mAttachInfo.mScalingRequired;
        android.graphics.Rect rect = this.mDirty;
        if (this.mSurfaceHolder != null) {
            rect.setEmpty();
            if (z5 && this.mScroller != null) {
                this.mScroller.abortAnimation();
            }
            return false;
        }
        if (z3) {
            rect.set(0, 0, (int) ((this.mWidth * f) + 0.5f), (int) ((this.mHeight * f) + 0.5f));
        }
        this.mAttachInfo.mTreeObserver.dispatchOnDraw();
        int i3 = -this.mCanvasOffsetX;
        int i4 = (-this.mCanvasOffsetY) + i;
        android.view.WindowManager.LayoutParams layoutParams = this.mWindowAttributes;
        android.graphics.Rect rect2 = layoutParams != null ? layoutParams.surfaceInsets : null;
        if (rect2 != null) {
            i3 -= rect2.left;
            i4 -= rect2.top;
            rect.offset(rect2.left, rect2.top);
        }
        boolean isAccessibilityFocusDirty = isAccessibilityFocusDirty();
        if (isAccessibilityFocusDirty && getAccessibilityFocusedRect(this.mAttachInfo.mTmpInvalRect)) {
            requestLayout();
        }
        this.mAttachInfo.mDrawingTime = this.mChoreographer.getFrameTimeNanos() / 1000000;
        if (!rect.isEmpty() || this.mIsAnimating || isAccessibilityFocusDirty) {
            if (isHardwareEnabled()) {
                boolean z7 = isAccessibilityFocusDirty || this.mInvalidateRootRequested;
                this.mInvalidateRootRequested = false;
                this.mIsAnimating = false;
                if (this.mHardwareYOffset != i4 || this.mHardwareXOffset != i3) {
                    this.mHardwareYOffset = i4;
                    this.mHardwareXOffset = i3;
                    z7 = true;
                }
                if (z7) {
                    this.mAttachInfo.mThreadedRenderer.invalidateRoot();
                }
                rect.setEmpty();
                boolean updateContentDrawBounds = updateContentDrawBounds();
                if (this.mReportNextDraw) {
                    this.mAttachInfo.mThreadedRenderer.setStopped(false);
                }
                if (updateContentDrawBounds) {
                    requestDrawWindow();
                }
                if (this.mHdrRenderState.updateForFrame(this.mAttachInfo.mDrawingTime)) {
                    float renderHdrSdrRatio = this.mHdrRenderState.getRenderHdrSdrRatio();
                    applyTransactionOnDraw(this.mTransaction.setExtendedRangeBrightness(getSurfaceControl(), renderHdrSdrRatio, this.mHdrRenderState.getDesiredHdrSdrRatio()));
                    this.mAttachInfo.mThreadedRenderer.setTargetHdrSdrRatio(renderHdrSdrRatio);
                }
                if (surfaceSyncGroup != null) {
                    registerCallbacksForSync(z2, surfaceSyncGroup);
                    if (z2) {
                        this.mAttachInfo.mThreadedRenderer.forceDrawNextFrame();
                    }
                } else if (this.mHasPendingTransactions) {
                    registerCallbackForPendingTransactions();
                }
                this.mAttachInfo.mThreadedRenderer.draw(this.mView, this.mAttachInfo, this);
                z4 = true;
            } else {
                if (this.mAttachInfo.mThreadedRenderer != null && !this.mAttachInfo.mThreadedRenderer.isEnabled() && this.mAttachInfo.mThreadedRenderer.isRequested() && this.mSurface.isValid()) {
                    try {
                        this.mAttachInfo.mThreadedRenderer.initializeIfNeeded(this.mWidth, this.mHeight, this.mAttachInfo, this.mSurface, rect2);
                        this.mFullRedrawNeeded = true;
                        scheduleTraversals();
                        return false;
                    } catch (android.view.Surface.OutOfResourcesException e) {
                        handleOutOfResourcesException(e);
                        return false;
                    }
                }
                if (!drawSoftware(surface, this.mAttachInfo, i3, i4, z6, rect, rect2)) {
                    return false;
                }
            }
        }
        if (z5) {
            this.mFullRedrawNeeded = true;
            scheduleTraversals();
        }
        return z4;
    }

    private boolean drawSoftware(android.view.Surface surface, android.view.View.AttachInfo attachInfo, int i, int i2, boolean z, android.graphics.Rect rect, android.graphics.Rect rect2) {
        try {
            android.graphics.Canvas lockCanvas = this.mSurface.lockCanvas(rect);
            lockCanvas.setDensity(this.mDensity);
            try {
                try {
                    if (!lockCanvas.isOpaque() || i2 != 0 || i != 0) {
                        lockCanvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
                    }
                    rect.setEmpty();
                    this.mIsAnimating = false;
                    this.mView.mPrivateFlags |= 32;
                    lockCanvas.translate(-i, -i2);
                    if (this.mTranslator != null) {
                        this.mTranslator.translateCanvas(lockCanvas);
                    }
                    lockCanvas.setScreenDensity(z ? this.mNoncompatDensity : 0);
                    this.mView.draw(lockCanvas);
                    drawAccessibilityFocusedDrawableIfNeeded(lockCanvas);
                    return true;
                } finally {
                    surface.unlockCanvasAndPost(lockCanvas);
                }
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.e(this.mTag, "Could not unlock surface", e);
                this.mLayoutRequested = true;
                return false;
            }
        } catch (android.view.Surface.OutOfResourcesException e2) {
            handleOutOfResourcesException(e2);
            return false;
        } catch (java.lang.IllegalArgumentException e3) {
            android.util.Log.e(this.mTag, "Could not lock surface", e3);
            this.mLayoutRequested = true;
            return false;
        }
    }

    private void drawAccessibilityFocusedDrawableIfNeeded(android.graphics.Canvas canvas) {
        android.graphics.Rect rect = this.mAttachInfo.mTmpInvalRect;
        boolean z = false;
        if (getAccessibilityFocusedRect(rect)) {
            if (this.mContext.getResources().getConfiguration().isScreenRound() && this.mContext.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_WATCH)) {
                z = true;
            }
            android.graphics.drawable.Drawable accessibilityFocusedDrawable = getAccessibilityFocusedDrawable();
            if (accessibilityFocusedDrawable != null) {
                accessibilityFocusedDrawable.setBounds(rect);
                accessibilityFocusedDrawable.draw(canvas);
                if (this.mDisplay != null && z) {
                    drawAccessibilityFocusedBorderOnRoundDisplay(canvas, rect, getRoundDisplayRadius(), getRoundDisplayAccessibilityHighlightPaint());
                    return;
                }
                return;
            }
            return;
        }
        if (this.mAttachInfo.mAccessibilityFocusDrawable != null) {
            this.mAttachInfo.mAccessibilityFocusDrawable.setBounds(0, 0, 0, 0);
        }
    }

    private int getRoundDisplayRadius() {
        android.graphics.Point point = new android.graphics.Point();
        this.mDisplay.getRealSize(point);
        return point.x / 2;
    }

    private android.graphics.Paint getRoundDisplayAccessibilityHighlightPaint() {
        if (this.mRoundDisplayAccessibilityHighlightPaint == null) {
            this.mRoundDisplayAccessibilityHighlightPaint = new android.graphics.Paint();
            this.mRoundDisplayAccessibilityHighlightPaint.setStyle(android.graphics.Paint.Style.STROKE);
            this.mRoundDisplayAccessibilityHighlightPaint.setAntiAlias(true);
        }
        this.mRoundDisplayAccessibilityHighlightPaint.setStrokeWidth(this.mAccessibilityManager.getAccessibilityFocusStrokeWidth());
        this.mRoundDisplayAccessibilityHighlightPaint.setColor(this.mAccessibilityManager.getAccessibilityFocusColor());
        return this.mRoundDisplayAccessibilityHighlightPaint;
    }

    private void drawAccessibilityFocusedBorderOnRoundDisplay(android.graphics.Canvas canvas, android.graphics.Rect rect, int i, android.graphics.Paint paint) {
        int save = canvas.save();
        canvas.clipRect(rect);
        float f = i;
        canvas.drawCircle(f, f, f - (this.mAccessibilityManager.getAccessibilityFocusStrokeWidth() / 2.0f), paint);
        canvas.restoreToCount(save);
    }

    private boolean getAccessibilityFocusedRect(android.graphics.Rect rect) {
        android.view.View view;
        if (this.mView == null) {
            android.util.Slog.w(TAG, "calling getAccessibilityFocusedRect() while the mView is null");
            return false;
        }
        if (!this.mAccessibilityManager.isEnabled() || !this.mAccessibilityManager.isTouchExplorationEnabled() || (view = this.mAccessibilityFocusedHost) == null || view.mAttachInfo == null) {
            return false;
        }
        if (view.getAccessibilityNodeProvider() == null) {
            view.getBoundsOnScreen(rect, true);
        } else {
            if (this.mAccessibilityFocusedVirtualView == null) {
                return false;
            }
            this.mAccessibilityFocusedVirtualView.getBoundsInScreen(rect);
        }
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        rect.offset(0, attachInfo.mViewRootImpl.mScrollY);
        rect.offset(-attachInfo.mWindowLeft, -attachInfo.mWindowTop);
        if (!rect.intersect(0, 0, attachInfo.mViewRootImpl.mWidth, attachInfo.mViewRootImpl.mHeight)) {
            rect.setEmpty();
        }
        return !rect.isEmpty();
    }

    private android.graphics.drawable.Drawable getAccessibilityFocusedDrawable() {
        if (this.mAttachInfo.mAccessibilityFocusDrawable == null) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            if (this.mView.mContext.getTheme().resolveAttribute(com.android.internal.R.attr.accessibilityFocusedDrawable, typedValue, true)) {
                this.mAttachInfo.mAccessibilityFocusDrawable = this.mView.mContext.getDrawable(typedValue.resourceId);
            }
        }
        if (this.mAttachInfo.mAccessibilityFocusDrawable instanceof android.graphics.drawable.GradientDrawable) {
            ((android.graphics.drawable.GradientDrawable) this.mAttachInfo.mAccessibilityFocusDrawable).setStroke(this.mAccessibilityManager.getAccessibilityFocusStrokeWidth(), this.mAccessibilityManager.getAccessibilityFocusColor());
        }
        return this.mAttachInfo.mAccessibilityFocusDrawable;
    }

    void updateSystemGestureExclusionRectsForView(android.view.View view) {
        this.mGestureExclusionTracker.updateRectsForView(view);
        this.mHandler.sendEmptyMessage(30);
    }

    void systemGestureExclusionChanged() {
        java.util.List<android.graphics.Rect> computeChangedRects = this.mGestureExclusionTracker.computeChangedRects();
        if (computeChangedRects != null && this.mView != null) {
            try {
                this.mWindowSession.reportSystemGestureExclusionChanged(this.mWindow, computeChangedRects);
                this.mAttachInfo.mTreeObserver.dispatchOnSystemGestureExclusionRectsChanged(computeChangedRects);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void updateDecorViewGestureInterception(boolean z) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(38, z ? 1 : 0, 0));
    }

    void decorViewInterceptionChanged(boolean z) {
        if (this.mView != null) {
            try {
                this.mWindowSession.reportDecorViewGestureInterceptionChanged(this.mWindow, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setRootSystemGestureExclusionRects(java.util.List<android.graphics.Rect> list) {
        this.mGestureExclusionTracker.setRootRects(list);
        this.mHandler.sendEmptyMessage(30);
    }

    public java.util.List<android.graphics.Rect> getRootSystemGestureExclusionRects() {
        return this.mGestureExclusionTracker.getRootRects();
    }

    void updateKeepClearRectsForView(android.view.View view) {
        this.mKeepClearRectsTracker.updateRectsForView(view);
        this.mUnrestrictedKeepClearRectsTracker.updateRectsForView(view);
        this.mHandler.sendEmptyMessage(35);
    }

    private void updateKeepClearForAccessibilityFocusRect() {
        if (this.mViewConfiguration.isPreferKeepClearForFocusEnabled()) {
            if (this.mKeepClearAccessibilityFocusRect == null) {
                this.mKeepClearAccessibilityFocusRect = new android.graphics.Rect();
            }
            if (!getAccessibilityFocusedRect(this.mKeepClearAccessibilityFocusRect)) {
                this.mKeepClearAccessibilityFocusRect.setEmpty();
            }
            this.mHandler.obtainMessage(35, 1, 0).sendToTarget();
        }
    }

    void keepClearRectsChanged(boolean z) {
        boolean computeChanges = this.mKeepClearRectsTracker.computeChanges();
        boolean computeChanges2 = this.mUnrestrictedKeepClearRectsTracker.computeChanges();
        if ((computeChanges || computeChanges2 || z) && this.mView != null) {
            this.mHasPendingKeepClearAreaChange = true;
            if (!this.mHandler.hasMessages(36)) {
                this.mHandler.sendEmptyMessageDelayed(36, 100L);
                reportKeepClearAreasChanged();
            }
        }
    }

    void reportKeepClearAreasChanged() {
        if (!this.mHasPendingKeepClearAreaChange || this.mView == null) {
            return;
        }
        this.mHasPendingKeepClearAreaChange = false;
        java.util.List<android.graphics.Rect> lastComputedRects = this.mKeepClearRectsTracker.getLastComputedRects();
        java.util.List<android.graphics.Rect> lastComputedRects2 = this.mUnrestrictedKeepClearRectsTracker.getLastComputedRects();
        if (this.mKeepClearAccessibilityFocusRect != null && !this.mKeepClearAccessibilityFocusRect.isEmpty()) {
            java.util.ArrayList arrayList = new java.util.ArrayList(lastComputedRects);
            arrayList.add(this.mKeepClearAccessibilityFocusRect);
            lastComputedRects = arrayList;
        }
        try {
            this.mWindowSession.reportKeepClearAreasChanged(this.mWindow, lastComputedRects, lastComputedRects2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestInvalidateRootRenderNode() {
        this.mInvalidateRootRequested = true;
    }

    boolean scrollToRectOrFocus(android.graphics.Rect rect, boolean z) {
        int i;
        boolean z2;
        android.graphics.Rect rect2 = this.mAttachInfo.mContentInsets;
        android.graphics.Rect rect3 = this.mAttachInfo.mVisibleInsets;
        if (rect3.left > rect2.left || rect3.top > rect2.top || rect3.right > rect2.right || rect3.bottom > rect2.bottom) {
            i = this.mScrollY;
            android.view.View findFocus = this.mView.findFocus();
            if (findFocus == null) {
                return false;
            }
            android.view.View view = this.mLastScrolledFocus != null ? this.mLastScrolledFocus.get() : null;
            if (findFocus != view) {
                rect = null;
            }
            if (findFocus != view || this.mScrollMayChange || rect != null) {
                this.mLastScrolledFocus = new java.lang.ref.WeakReference<>(findFocus);
                this.mScrollMayChange = false;
                if (findFocus.getGlobalVisibleRect(this.mVisRect, null)) {
                    if (rect == null) {
                        findFocus.getFocusedRect(this.mTempRect);
                        if (this.mView instanceof android.view.ViewGroup) {
                            ((android.view.ViewGroup) this.mView).offsetDescendantRectToMyCoords(findFocus, this.mTempRect);
                        }
                    } else {
                        this.mTempRect.set(rect);
                    }
                    if (this.mTempRect.intersect(this.mVisRect)) {
                        if (this.mTempRect.height() <= (this.mView.getHeight() - rect3.top) - rect3.bottom) {
                            if (this.mTempRect.top < rect3.top) {
                                i = this.mTempRect.top - rect3.top;
                            } else if (this.mTempRect.bottom > this.mView.getHeight() - rect3.bottom) {
                                i = this.mTempRect.bottom - (this.mView.getHeight() - rect3.bottom);
                            } else {
                                i = 0;
                            }
                        }
                        z2 = true;
                    }
                }
            }
            z2 = false;
        } else {
            z2 = false;
            i = 0;
        }
        if (i != this.mScrollY) {
            if (!z) {
                if (this.mScroller == null) {
                    this.mScroller = new android.widget.Scroller(this.mView.getContext());
                }
                this.mScroller.startScroll(0, this.mScrollY, 0, i - this.mScrollY);
            } else if (this.mScroller != null) {
                this.mScroller.abortAnimation();
            }
            this.mScrollY = i;
        }
        return z2;
    }

    public android.view.View getAccessibilityFocusedHost() {
        return this.mAccessibilityFocusedHost;
    }

    public android.view.accessibility.AccessibilityNodeInfo getAccessibilityFocusedVirtualView() {
        return this.mAccessibilityFocusedVirtualView;
    }

    void setAccessibilityFocus(android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        if (this.mAccessibilityFocusedVirtualView != null) {
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo2 = this.mAccessibilityFocusedVirtualView;
            android.view.View view2 = this.mAccessibilityFocusedHost;
            this.mAccessibilityFocusedHost = null;
            this.mAccessibilityFocusedVirtualView = null;
            view2.clearAccessibilityFocusNoCallbacks(64);
            android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = view2.getAccessibilityNodeProvider();
            if (accessibilityNodeProvider != null) {
                accessibilityNodeInfo2.getBoundsInParent(this.mTempRect);
                view2.invalidate(this.mTempRect);
                accessibilityNodeProvider.performAction(android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo2.getSourceNodeId()), 128, null);
            }
            accessibilityNodeInfo2.recycle();
        }
        if (this.mAccessibilityFocusedHost != null && this.mAccessibilityFocusedHost != view) {
            this.mAccessibilityFocusedHost.clearAccessibilityFocusNoCallbacks(64);
        }
        this.mAccessibilityFocusedHost = view;
        this.mAccessibilityFocusedVirtualView = accessibilityNodeInfo;
        updateKeepClearForAccessibilityFocusRect();
        requestInvalidateRootRenderNode();
        if (isAccessibilityFocusDirty()) {
            scheduleTraversals();
        }
    }

    boolean hasPointerCapture() {
        return this.mPointerCapture;
    }

    void requestPointerCapture(boolean z) {
        android.os.IBinder inputToken = getInputToken();
        if (inputToken == null) {
            android.util.Log.e(this.mTag, "No input channel to request Pointer Capture.");
        } else {
            android.hardware.input.InputManagerGlobal.getInstance().requestPointerCapture(inputToken, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePointerCaptureChanged(boolean z) {
        if (this.mPointerCapture == z) {
            return;
        }
        this.mPointerCapture = z;
        if (this.mView != null) {
            this.mView.dispatchPointerCaptureChanged(z);
        }
    }

    private void updateColorModeIfNeeded(int i, float f) {
        if (this.mAttachInfo.mThreadedRenderer == null) {
            return;
        }
        boolean z = i == 2 || i == 3;
        if (z && !this.mDisplay.isHdrSdrRatioAvailable()) {
            i = 1;
            z = false;
        }
        float colorMode = this.mAttachInfo.mThreadedRenderer.setColorMode((i == 4 || getConfiguration().isScreenWideColorGamut()) ? i : 0);
        if (f == 0.0f || f > colorMode) {
            f = colorMode;
        }
        this.mHdrRenderState.setDesiredHdrSdrRatio(z, f);
    }

    @Override // android.view.ViewParent
    public void requestChildFocus(android.view.View view, android.view.View view2) {
        checkThread();
        scheduleTraversals();
    }

    @Override // android.view.ViewParent
    public void clearChildFocus(android.view.View view) {
        checkThread();
        scheduleTraversals();
    }

    @Override // android.view.ViewParent
    public android.view.ViewParent getParentForAccessibility() {
        return null;
    }

    @Override // android.view.ViewParent
    public void focusableViewAvailable(android.view.View view) {
        checkThread();
        if (this.mView != null) {
            if (!this.mView.hasFocus()) {
                if (sAlwaysAssignFocus || !this.mAttachInfo.mInTouchMode) {
                    view.requestFocus();
                    return;
                }
                return;
            }
            android.view.View findFocus = this.mView.findFocus();
            if ((findFocus instanceof android.view.ViewGroup) && ((android.view.ViewGroup) findFocus).getDescendantFocusability() == 262144 && isViewDescendantOf(view, findFocus)) {
                view.requestFocus();
            }
        }
    }

    @Override // android.view.ViewParent
    public void recomputeViewAttributes(android.view.View view) {
        checkThread();
        if (this.mView == view) {
            this.mAttachInfo.mRecomputeGlobalAttributes = true;
            if (!this.mWillDrawSoon) {
                scheduleTraversals();
            }
        }
    }

    void dispatchDetachedFromWindow() {
        this.mInsetsController.onWindowFocusLost();
        this.mFirstInputStage.onDetachedFromWindow();
        if (this.mView != null && this.mView.mAttachInfo != null) {
            this.mAttachInfo.mTreeObserver.dispatchOnWindowAttachedChange(false);
            this.mView.dispatchDetachedFromWindow();
        }
        this.mAccessibilityInteractionConnectionManager.ensureNoConnection();
        this.mAccessibilityInteractionConnectionManager.ensureNoDirectConnection();
        removeSendWindowContentChangedCallback();
        destroyHardwareRenderer();
        setAccessibilityFocus(null, null);
        this.mInsetsController.cancelExistingAnimations();
        this.mView.assignParent(null);
        this.mView = null;
        this.mAttachInfo.mRootView = null;
        destroySurface();
        if (this.mInputQueueCallback != null && this.mInputQueue != null) {
            this.mInputQueueCallback.onInputQueueDestroyed(this.mInputQueue);
            this.mInputQueue.dispose();
            this.mInputQueueCallback = null;
            this.mInputQueue = null;
        }
        try {
            this.mWindowSession.remove(this.mWindow.asBinder());
        } catch (android.os.RemoteException e) {
        }
        if (this.mInputEventReceiver != null) {
            this.mInputEventReceiver.dispose();
            this.mInputEventReceiver = null;
        }
        unregisterListeners();
        unscheduleTraversals();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performConfigurationChange(android.util.MergedConfiguration mergedConfiguration, boolean z, int i) {
        if (mergedConfiguration == null) {
            throw new java.lang.IllegalArgumentException("No merged config provided.");
        }
        if (this.mLastReportedMergedConfiguration.getMergedConfiguration().windowConfiguration.getRotation() != mergedConfiguration.getMergedConfiguration().windowConfiguration.getRotation()) {
            this.mUpdateSurfaceNeeded = true;
            if (!this.mIsInTraversal) {
                this.mForceNextWindowRelayout = true;
            }
        }
        android.content.res.Configuration globalConfiguration = mergedConfiguration.getGlobalConfiguration();
        android.content.res.Configuration overrideConfiguration = mergedConfiguration.getOverrideConfiguration();
        android.content.res.CompatibilityInfo compatibilityInfo = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo();
        if (!compatibilityInfo.equals(android.content.res.CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO)) {
            android.content.res.Configuration configuration = new android.content.res.Configuration(globalConfiguration);
            compatibilityInfo.applyToConfiguration(this.mNoncompatDensity, configuration);
            globalConfiguration = configuration;
        }
        synchronized (sConfigCallbacks) {
            for (int size = sConfigCallbacks.size() - 1; size >= 0; size--) {
                sConfigCallbacks.get(size).onConfigurationChanged(globalConfiguration);
            }
        }
        this.mLastReportedMergedConfiguration.setConfiguration(globalConfiguration, overrideConfiguration);
        this.mForceNextConfigUpdate = z;
        if (this.mActivityConfigCallback != null) {
            this.mActivityConfigCallback.onConfigurationChanged(overrideConfiguration, i);
        } else {
            updateConfiguration(i);
        }
        this.mForceNextConfigUpdate = false;
    }

    public void updateConfiguration(int i) {
        if (this.mView == null) {
            return;
        }
        android.content.res.Resources resources = this.mView.getResources();
        android.content.res.Configuration configuration = resources.getConfiguration();
        if (i != -1) {
            onMovedToDisplay(i, configuration);
        }
        if (this.mForceNextConfigUpdate || this.mLastConfigurationFromResources.diff(configuration) != 0) {
            updateInternalDisplay(this.mDisplay.getDisplayId(), resources);
            updateLastConfigurationFromResources(configuration);
            this.mView.dispatchConfigurationChanged(configuration);
            this.mForceNextWindowRelayout = true;
            requestLayout();
        }
        updateForceDarkMode();
    }

    private void updateLastConfigurationFromResources(android.content.res.Configuration configuration) {
        int layoutDirection = this.mLastConfigurationFromResources.getLayoutDirection();
        int layoutDirection2 = configuration.getLayoutDirection();
        this.mLastConfigurationFromResources.setTo(configuration);
        if (layoutDirection != layoutDirection2 && this.mView != null && this.mViewLayoutDirectionInitial == 2) {
            this.mView.setLayoutDirection(layoutDirection2);
        }
    }

    public static boolean isViewDescendantOf(android.view.View view, android.view.View view2) {
        if (view == view2) {
            return true;
        }
        java.lang.Object parent = view.getParent();
        return (parent instanceof android.view.ViewGroup) && isViewDescendantOf((android.view.View) parent, view2);
    }

    private static void forceLayout(android.view.View view) {
        view.forceLayout();
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                forceLayout(viewGroup.getChildAt(i));
            }
        }
    }

    final class ViewRootHandler extends android.os.Handler {
        ViewRootHandler() {
        }

        @Override // android.os.Handler
        public java.lang.String getMessageName(android.os.Message message) {
            switch (message.what) {
                case 1:
                    return "MSG_INVALIDATE";
                case 2:
                    return "MSG_INVALIDATE_RECT";
                case 3:
                    return "MSG_DIE";
                case 4:
                    return "MSG_RESIZED";
                case 5:
                    return "MSG_RESIZED_REPORT";
                case 6:
                    return "MSG_WINDOW_FOCUS_CHANGED";
                case 7:
                    return "MSG_DISPATCH_INPUT_EVENT";
                case 8:
                    return "MSG_DISPATCH_APP_VISIBILITY";
                case 9:
                    return "MSG_DISPATCH_GET_NEW_SURFACE";
                case 10:
                case 20:
                case 22:
                case 26:
                case 33:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                default:
                    return super.getMessageName(message);
                case 11:
                    return "MSG_DISPATCH_KEY_FROM_IME";
                case 12:
                    return "MSG_DISPATCH_KEY_FROM_AUTOFILL";
                case 13:
                    return "MSG_CHECK_FOCUS";
                case 14:
                    return "MSG_CLOSE_SYSTEM_DIALOGS";
                case 15:
                    return "MSG_DISPATCH_DRAG_EVENT";
                case 16:
                    return "MSG_DISPATCH_DRAG_LOCATION_EVENT";
                case 17:
                    return "MSG_DISPATCH_SYSTEM_UI_VISIBILITY";
                case 18:
                    return "MSG_UPDATE_CONFIGURATION";
                case 19:
                    return "MSG_PROCESS_INPUT_EVENTS";
                case 21:
                    return "MSG_CLEAR_ACCESSIBILITY_FOCUS_HOST";
                case 23:
                    return "MSG_WINDOW_MOVED";
                case 24:
                    return "MSG_SYNTHESIZE_INPUT_EVENT";
                case 25:
                    return "MSG_DISPATCH_WINDOW_SHOWN";
                case 27:
                    return "MSG_UPDATE_POINTER_ICON";
                case 28:
                    return "MSG_POINTER_CAPTURE_CHANGED";
                case 29:
                    return "MSG_INSETS_CONTROL_CHANGED";
                case 30:
                    return "MSG_SYSTEM_GESTURE_EXCLUSION_CHANGED";
                case 31:
                    return "MSG_SHOW_INSETS";
                case 32:
                    return "MSG_HIDE_INSETS";
                case 34:
                    return "MSG_WINDOW_TOUCH_MODE_CHANGED";
                case 35:
                    return "MSG_KEEP_CLEAR_RECTS_CHANGED";
                case 41:
                    return "MSG_REFRESH_POINTER_ICON";
            }
        }

        @Override // android.os.Handler
        public boolean sendMessageAtTime(android.os.Message message, long j) {
            if (message.what == 26 && message.obj == null) {
                throw new java.lang.NullPointerException("Attempted to call MSG_REQUEST_KEYBOARD_SHORTCUTS with null receiver:");
            }
            return super.sendMessageAtTime(message, j);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (android.os.Trace.isTagEnabled(8L)) {
                android.os.Trace.traceBegin(8L, getMessageName(message));
            }
            try {
                handleMessageImpl(message);
            } finally {
                android.os.Trace.traceEnd(8L);
            }
        }

        private void handleMessageImpl(android.os.Message message) {
            switch (message.what) {
                case 1:
                    ((android.view.View) message.obj).invalidate();
                    break;
                case 2:
                    android.view.View.AttachInfo.InvalidateInfo invalidateInfo = (android.view.View.AttachInfo.InvalidateInfo) message.obj;
                    invalidateInfo.target.invalidate(invalidateInfo.left, invalidateInfo.top, invalidateInfo.right, invalidateInfo.bottom);
                    invalidateInfo.recycle();
                    break;
                case 3:
                    android.view.ViewRootImpl.this.doDie();
                    break;
                case 4:
                case 5:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.view.ViewRootImpl.this.handleResized((android.window.ClientWindowFrames) someArgs.arg1, message.what == 5, (android.util.MergedConfiguration) someArgs.arg2, (android.view.InsetsState) someArgs.arg3, someArgs.argi1 != 0, someArgs.argi2 != 0, someArgs.argi3, someArgs.argi4, someArgs.argi5 != 0);
                    someArgs.recycle();
                    break;
                case 6:
                    android.view.ViewRootImpl.this.handleWindowFocusChanged();
                    break;
                case 7:
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    android.view.ViewRootImpl.this.enqueueInputEvent((android.view.InputEvent) someArgs2.arg1, (android.view.InputEventReceiver) someArgs2.arg2, 0, true);
                    someArgs2.recycle();
                    break;
                case 8:
                    android.view.ViewRootImpl.this.handleAppVisibility(message.arg1 != 0);
                    break;
                case 9:
                    android.view.ViewRootImpl.this.handleGetNewSurface();
                    break;
                case 11:
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) message.obj;
                    if ((keyEvent.getFlags() & 8) != 0) {
                        keyEvent = android.view.KeyEvent.changeFlags(keyEvent, keyEvent.getFlags() & (-9));
                    }
                    android.view.ViewRootImpl.this.enqueueInputEvent(keyEvent, null, 1, true);
                    break;
                case 12:
                    android.view.ViewRootImpl.this.enqueueInputEvent((android.view.KeyEvent) message.obj, null, 0, true);
                    break;
                case 13:
                    android.view.ViewRootImpl.this.getImeFocusController().onScheduledCheckFocus();
                    break;
                case 14:
                    if (android.view.ViewRootImpl.this.mView != null) {
                        android.view.ViewRootImpl.this.mView.onCloseSystemDialogs((java.lang.String) message.obj);
                        break;
                    }
                    break;
                case 15:
                case 16:
                    android.view.DragEvent dragEvent = (android.view.DragEvent) message.obj;
                    dragEvent.mLocalState = android.view.ViewRootImpl.this.mLocalDragState;
                    android.view.ViewRootImpl.this.handleDragEvent(dragEvent);
                    break;
                case 17:
                    android.view.ViewRootImpl.this.handleDispatchSystemUiVisibilityChanged();
                    break;
                case 18:
                    android.content.res.Configuration configuration = (android.content.res.Configuration) message.obj;
                    if (configuration.isOtherSeqNewer(android.view.ViewRootImpl.this.mLastReportedMergedConfiguration.getMergedConfiguration())) {
                        configuration = android.view.ViewRootImpl.this.mLastReportedMergedConfiguration.getGlobalConfiguration();
                    }
                    android.view.ViewRootImpl.this.mPendingMergedConfiguration.setConfiguration(configuration, android.view.ViewRootImpl.this.mLastReportedMergedConfiguration.getOverrideConfiguration());
                    android.view.ViewRootImpl.this.performConfigurationChange(new android.util.MergedConfiguration(android.view.ViewRootImpl.this.mPendingMergedConfiguration), false, -1);
                    break;
                case 19:
                    android.view.ViewRootImpl.this.mProcessInputEventsScheduled = false;
                    android.view.ViewRootImpl.this.doProcessInputEvents();
                    break;
                case 21:
                    android.view.ViewRootImpl.this.setAccessibilityFocus(null, null);
                    break;
                case 22:
                    if (android.view.ViewRootImpl.this.mView != null) {
                        android.view.ViewRootImpl.this.invalidateWorld(android.view.ViewRootImpl.this.mView);
                        break;
                    }
                    break;
                case 23:
                    if (android.view.ViewRootImpl.this.mAdded) {
                        int width = android.view.ViewRootImpl.this.mWinFrame.width();
                        int height = android.view.ViewRootImpl.this.mWinFrame.height();
                        int i = message.arg1;
                        int i2 = message.arg2;
                        android.view.ViewRootImpl.this.mTmpFrames.frame.left = i;
                        android.view.ViewRootImpl.this.mTmpFrames.frame.right = i + width;
                        android.view.ViewRootImpl.this.mTmpFrames.frame.top = i2;
                        android.view.ViewRootImpl.this.mTmpFrames.frame.bottom = i2 + height;
                        android.view.ViewRootImpl.this.setFrame(android.view.ViewRootImpl.this.mTmpFrames.frame, false);
                        android.view.ViewRootImpl.this.maybeHandleWindowMove(android.view.ViewRootImpl.this.mWinFrame);
                        break;
                    }
                    break;
                case 24:
                    android.view.ViewRootImpl.this.enqueueInputEvent((android.view.InputEvent) message.obj, null, 32, true);
                    break;
                case 25:
                    android.view.ViewRootImpl.this.handleDispatchWindowShown();
                    break;
                case 26:
                    android.view.ViewRootImpl.this.handleRequestKeyboardShortcuts((com.android.internal.os.IResultReceiver) message.obj, message.arg1);
                    break;
                case 27:
                    android.view.ViewRootImpl.this.resetPointerIcon((android.view.MotionEvent) message.obj);
                    break;
                case 28:
                    android.view.ViewRootImpl.this.handlePointerCaptureChanged(message.arg1 != 0);
                    break;
                case 29:
                    com.android.internal.os.SomeArgs someArgs3 = (com.android.internal.os.SomeArgs) message.obj;
                    android.view.ViewRootImpl.this.mInsetsController.onStateChanged((android.view.InsetsState) someArgs3.arg1);
                    android.view.InsetsSourceControl[] insetsSourceControlArr = (android.view.InsetsSourceControl[]) someArgs3.arg2;
                    if (android.view.ViewRootImpl.this.mAdded) {
                        android.view.ViewRootImpl.this.mInsetsController.onControlsChanged(insetsSourceControlArr);
                    } else if (insetsSourceControlArr != null) {
                        for (android.view.InsetsSourceControl insetsSourceControl : insetsSourceControlArr) {
                            if (insetsSourceControl != null) {
                                insetsSourceControl.release(new android.view.InsetsController$$ExternalSyntheticLambda7());
                            }
                        }
                    }
                    someArgs3.recycle();
                    break;
                case 30:
                    android.view.ViewRootImpl.this.systemGestureExclusionChanged();
                    break;
                case 31:
                    android.view.inputmethod.ImeTracker.Token token = (android.view.inputmethod.ImeTracker.Token) message.obj;
                    android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 30);
                    if (android.view.ViewRootImpl.this.mView == null) {
                        android.util.Log.e(android.view.ViewRootImpl.TAG, java.lang.String.format("Calling showInsets(%d,%b) on window that no longer has views.", java.lang.Integer.valueOf(message.arg1), java.lang.Boolean.valueOf(message.arg2 == 1)));
                    }
                    android.view.ViewRootImpl.this.clearLowProfileModeIfNeeded(message.arg1, message.arg2 == 1);
                    android.view.ViewRootImpl.this.mInsetsController.show(message.arg1, message.arg2 == 1, token);
                    break;
                case 32:
                    android.view.inputmethod.ImeTracker.Token token2 = (android.view.inputmethod.ImeTracker.Token) message.obj;
                    android.view.inputmethod.ImeTracker.forLogging().onProgress(token2, 31);
                    android.view.ViewRootImpl.this.mInsetsController.hide(message.arg1, message.arg2 == 1, token2);
                    break;
                case 33:
                    android.view.ViewRootImpl.this.handleScrollCaptureRequest((android.view.IScrollCaptureResponseListener) message.obj);
                    break;
                case 34:
                    android.view.ViewRootImpl.this.handleWindowTouchModeChanged();
                    break;
                case 35:
                    android.view.ViewRootImpl.this.keepClearRectsChanged(message.arg1 == 1);
                    break;
                case 36:
                    android.view.ViewRootImpl.this.reportKeepClearAreasChanged();
                    break;
                case 37:
                    android.util.Log.e(android.view.ViewRootImpl.this.mTag, "Timedout waiting to unpause for sync");
                    android.view.ViewRootImpl.this.mNumPausedForSync = 0;
                    android.view.ViewRootImpl.this.scheduleTraversals();
                    break;
                case 38:
                    android.view.ViewRootImpl.this.decorViewInterceptionChanged(message.arg1 == 1);
                    break;
                case 39:
                    android.view.ViewRootImpl.this.mIsFrameRateBoosting = false;
                    android.view.ViewRootImpl.this.mIsTouchBoosting = false;
                    android.view.ViewRootImpl.this.setPreferredFrameRateCategory(java.lang.Math.max(android.view.ViewRootImpl.this.mPreferredFrameRateCategory, android.view.ViewRootImpl.this.mLastPreferredFrameRateCategory));
                    break;
                case 40:
                    if (!android.view.ViewRootImpl.this.mHasInvalidation && !android.view.ViewRootImpl.this.mIsFrameRateBoosting && !android.view.ViewRootImpl.this.mIsTouchBoosting) {
                        android.view.ViewRootImpl.this.mPreferredFrameRateCategory = 1;
                        android.view.ViewRootImpl.this.setPreferredFrameRateCategory(android.view.ViewRootImpl.this.mPreferredFrameRateCategory);
                        android.view.ViewRootImpl.this.mHasIdledMessage = false;
                        break;
                    } else {
                        android.view.ViewRootImpl.this.mHasInvalidation = false;
                        android.view.ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(40, 1000L);
                        android.view.ViewRootImpl.this.mHasIdledMessage = true;
                        break;
                    }
                case 41:
                    if (android.view.ViewRootImpl.this.mPointerIconEvent != null) {
                        android.view.ViewRootImpl.this.updatePointerIcon(android.view.ViewRootImpl.this.mPointerIconEvent);
                        break;
                    }
                    break;
                case 42:
                    android.view.ViewRootImpl.this.mPreferredFrameRate = 0.0f;
                    android.view.ViewRootImpl.this.mFrameRateCompatibility = 1;
                    android.view.ViewRootImpl.this.setPreferredFrameRate(android.view.ViewRootImpl.this.mPreferredFrameRate);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$8(java.lang.Runnable runnable) {
        this.mHandler.post(runnable);
    }

    boolean ensureTouchMode(boolean z) {
        if (this.mAttachInfo.mInTouchMode == z) {
            return false;
        }
        try {
            android.view.WindowManagerGlobal.getWindowManagerService().setInTouchMode(z, getDisplayId());
            return ensureTouchModeLocally(z);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private boolean ensureTouchModeLocally(boolean z) {
        if (this.mAttachInfo.mInTouchMode == z) {
            return false;
        }
        this.mAttachInfo.mInTouchMode = z;
        this.mAttachInfo.mTreeObserver.dispatchOnTouchModeChanged(z);
        return z ? enterTouchMode() : leaveTouchMode();
    }

    private boolean enterTouchMode() {
        android.view.View findFocus;
        if (this.mView == null || !this.mView.hasFocus() || (findFocus = this.mView.findFocus()) == null || findFocus.isFocusableInTouchMode()) {
            return false;
        }
        android.view.ViewGroup findAncestorToTakeFocusInTouchMode = findAncestorToTakeFocusInTouchMode(findFocus);
        if (findAncestorToTakeFocusInTouchMode != null) {
            return findAncestorToTakeFocusInTouchMode.requestFocus();
        }
        findFocus.clearFocusInternal(null, true, false);
        return true;
    }

    private static android.view.ViewGroup findAncestorToTakeFocusInTouchMode(android.view.View view) {
        android.view.ViewParent parent = view.getParent();
        while (parent instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) parent;
            if (viewGroup.getDescendantFocusability() == 262144 && viewGroup.isFocusableInTouchMode()) {
                return viewGroup;
            }
            if (viewGroup.isRootNamespace()) {
                return null;
            }
            parent = viewGroup.getParent();
        }
        return null;
    }

    private boolean leaveTouchMode() {
        if (this.mView == null) {
            return false;
        }
        if (this.mView.hasFocus()) {
            android.view.View findFocus = this.mView.findFocus();
            if (!(findFocus instanceof android.view.ViewGroup) || ((android.view.ViewGroup) findFocus).getDescendantFocusability() != 262144) {
                return false;
            }
        }
        return this.mView.restoreDefaultFocus();
    }

    abstract class InputStage {
        protected static final int FINISH_HANDLED = 1;
        protected static final int FINISH_NOT_HANDLED = 2;
        protected static final int FORWARD = 0;
        private final android.view.ViewRootImpl.InputStage mNext;
        private java.lang.String mTracePrefix;

        public InputStage(android.view.ViewRootImpl.InputStage inputStage) {
            this.mNext = inputStage;
        }

        public final void deliver(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            if ((queuedInputEvent.mFlags & 4) != 0) {
                forward(queuedInputEvent);
                return;
            }
            if (shouldDropInputEvent(queuedInputEvent)) {
                finish(queuedInputEvent, false);
                return;
            }
            traceEvent(queuedInputEvent, 8L);
            try {
                int onProcess = onProcess(queuedInputEvent);
                android.os.Trace.traceEnd(8L);
                apply(queuedInputEvent, onProcess);
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(8L);
                throw th;
            }
        }

        protected void finish(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent, boolean z) {
            queuedInputEvent.mFlags |= 4;
            if (z) {
                queuedInputEvent.mFlags |= 8;
            }
            forward(queuedInputEvent);
        }

        protected void forward(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            onDeliverToNext(queuedInputEvent);
        }

        protected void apply(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent, int i) {
            if (i == 0) {
                forward(queuedInputEvent);
            } else if (i == 1) {
                finish(queuedInputEvent, true);
            } else {
                if (i == 2) {
                    finish(queuedInputEvent, false);
                    return;
                }
                throw new java.lang.IllegalArgumentException("Invalid result: " + i);
            }
        }

        protected int onProcess(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            return 0;
        }

        protected void onDeliverToNext(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            if (this.mNext != null) {
                this.mNext.deliver(queuedInputEvent);
            } else {
                android.view.ViewRootImpl.this.finishInputEvent(queuedInputEvent);
            }
        }

        protected void onWindowFocusChanged(boolean z) {
            if (this.mNext != null) {
                this.mNext.onWindowFocusChanged(z);
            }
        }

        protected void onDetachedFromWindow() {
            if (this.mNext != null) {
                this.mNext.onDetachedFromWindow();
            }
        }

        protected boolean shouldDropInputEvent(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            java.lang.String str;
            if (android.view.ViewRootImpl.this.mView == null || !android.view.ViewRootImpl.this.mAdded) {
                android.util.Slog.w(android.view.ViewRootImpl.this.mTag, "Dropping event due to root view being removed: " + queuedInputEvent.mEvent);
                return true;
            }
            if (!android.view.ViewRootImpl.this.mAttachInfo.mHasWindowFocus && ((!android.view.ViewRootImpl.this.mProcessingBackKey || !isBack(queuedInputEvent.mEvent)) && !queuedInputEvent.mEvent.isFromSource(2) && !android.view.ViewRootImpl.this.isAutofillUiShowing())) {
                str = "no window focus";
            } else if (android.view.ViewRootImpl.this.mStopped) {
                str = "window is stopped";
            } else if (android.view.ViewRootImpl.this.mIsAmbientMode && !queuedInputEvent.mEvent.isFromSource(1)) {
                str = "non-button event in ambient mode";
            } else {
                if (!android.view.ViewRootImpl.this.mPausedForTransition || isBack(queuedInputEvent.mEvent)) {
                    return false;
                }
                str = "paused for transition";
            }
            if (android.view.ViewRootImpl.isTerminalInputEvent(queuedInputEvent.mEvent)) {
                queuedInputEvent.mEvent.cancel();
                android.util.Slog.w(android.view.ViewRootImpl.this.mTag, "Cancelling event (" + str + "):" + queuedInputEvent.mEvent);
                return false;
            }
            android.util.Slog.w(android.view.ViewRootImpl.this.mTag, "Dropping event (" + str + "):" + queuedInputEvent.mEvent);
            return true;
        }

        void dump(java.lang.String str, java.io.PrintWriter printWriter) {
            if (this.mNext != null) {
                this.mNext.dump(str, printWriter);
            }
        }

        boolean isBack(android.view.InputEvent inputEvent) {
            return (inputEvent instanceof android.view.KeyEvent) && ((android.view.KeyEvent) inputEvent).getKeyCode() == 4;
        }

        private void traceEvent(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent, long j) {
            if (!android.os.Trace.isTagEnabled(j)) {
                return;
            }
            if (this.mTracePrefix == null) {
                this.mTracePrefix = getClass().getSimpleName();
            }
            android.os.Trace.traceBegin(j, this.mTracePrefix + " id=0x" + java.lang.Integer.toHexString(queuedInputEvent.mEvent.getId()));
        }
    }

    abstract class AsyncInputStage extends android.view.ViewRootImpl.InputStage {
        protected static final int DEFER = 3;
        private android.view.ViewRootImpl.QueuedInputEvent mQueueHead;
        private int mQueueLength;
        private android.view.ViewRootImpl.QueuedInputEvent mQueueTail;
        private final java.lang.String mTraceCounter;

        public AsyncInputStage(android.view.ViewRootImpl.InputStage inputStage, java.lang.String str) {
            super(inputStage);
            this.mTraceCounter = str;
        }

        protected void defer(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            queuedInputEvent.mFlags |= 2;
            enqueue(queuedInputEvent);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void forward(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            queuedInputEvent.mFlags &= -3;
            android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent2 = this.mQueueHead;
            if (queuedInputEvent2 == null) {
                super.forward(queuedInputEvent);
                return;
            }
            int deviceId = queuedInputEvent.mEvent.getDeviceId();
            android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent3 = null;
            boolean z = false;
            while (queuedInputEvent2 != null && queuedInputEvent2 != queuedInputEvent) {
                if (!z && deviceId == queuedInputEvent2.mEvent.getDeviceId()) {
                    z = true;
                }
                queuedInputEvent3 = queuedInputEvent2;
                queuedInputEvent2 = queuedInputEvent2.mNext;
            }
            if (z) {
                if (queuedInputEvent2 == null) {
                    enqueue(queuedInputEvent);
                    return;
                }
                return;
            }
            if (queuedInputEvent2 != null) {
                queuedInputEvent2 = queuedInputEvent2.mNext;
                dequeue(queuedInputEvent, queuedInputEvent3);
            }
            super.forward(queuedInputEvent);
            android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent4 = queuedInputEvent3;
            android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent5 = queuedInputEvent2;
            while (queuedInputEvent5 != null) {
                if (deviceId == queuedInputEvent5.mEvent.getDeviceId()) {
                    if ((queuedInputEvent5.mFlags & 2) == 0) {
                        android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent6 = queuedInputEvent5.mNext;
                        dequeue(queuedInputEvent5, queuedInputEvent4);
                        super.forward(queuedInputEvent5);
                        queuedInputEvent5 = queuedInputEvent6;
                    } else {
                        return;
                    }
                } else {
                    android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent7 = queuedInputEvent5;
                    queuedInputEvent5 = queuedInputEvent5.mNext;
                    queuedInputEvent4 = queuedInputEvent7;
                }
            }
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void apply(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent, int i) {
            if (i == 3) {
                defer(queuedInputEvent);
            } else {
                super.apply(queuedInputEvent, i);
            }
        }

        private void enqueue(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            if (this.mQueueTail == null) {
                this.mQueueHead = queuedInputEvent;
                this.mQueueTail = queuedInputEvent;
            } else {
                this.mQueueTail.mNext = queuedInputEvent;
                this.mQueueTail = queuedInputEvent;
            }
            this.mQueueLength++;
            android.os.Trace.traceCounter(4L, this.mTraceCounter, this.mQueueLength);
        }

        private void dequeue(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent, android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent2) {
            if (queuedInputEvent2 == null) {
                this.mQueueHead = queuedInputEvent.mNext;
            } else {
                queuedInputEvent2.mNext = queuedInputEvent.mNext;
            }
            if (this.mQueueTail == queuedInputEvent) {
                this.mQueueTail = queuedInputEvent2;
            }
            queuedInputEvent.mNext = null;
            this.mQueueLength--;
            android.os.Trace.traceCounter(4L, this.mTraceCounter, this.mQueueLength);
        }

        @Override // android.view.ViewRootImpl.InputStage
        void dump(java.lang.String str, java.io.PrintWriter printWriter) {
            printWriter.print(str);
            printWriter.print(getClass().getName());
            printWriter.print(": mQueueLength=");
            printWriter.println(this.mQueueLength);
            super.dump(str, printWriter);
        }
    }

    final class NativePreImeInputStage extends android.view.ViewRootImpl.AsyncInputStage implements android.view.InputQueue.FinishedInputEventCallback {
        public NativePreImeInputStage(android.view.ViewRootImpl.InputStage inputStage, java.lang.String str) {
            super(inputStage, str);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            if (queuedInputEvent.mEvent instanceof android.view.KeyEvent) {
                android.view.KeyEvent keyEvent = (android.view.KeyEvent) queuedInputEvent.mEvent;
                if (isBack(keyEvent)) {
                    if (android.view.ViewRootImpl.this.mWindowlessBackKeyCallback != null) {
                        if (android.view.ViewRootImpl.this.mWindowlessBackKeyCallback.test(keyEvent)) {
                            return (keyEvent.getAction() != 1 || keyEvent.isCanceled()) ? 2 : 1;
                        }
                        return 0;
                    }
                    if (android.view.ViewRootImpl.this.mContext != null && android.view.ViewRootImpl.this.mOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled()) {
                        return doOnBackKeyEvent(keyEvent);
                    }
                }
                if (android.view.ViewRootImpl.this.mInputQueue != null) {
                    android.view.ViewRootImpl.this.mInputQueue.sendInputEvent(queuedInputEvent.mEvent, queuedInputEvent, true, this);
                    return 3;
                }
            }
            return 0;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private int doOnBackKeyEvent(android.view.KeyEvent keyEvent) {
            android.window.WindowOnBackInvokedDispatcher onBackInvokedDispatcher = android.view.ViewRootImpl.this.getOnBackInvokedDispatcher();
            android.window.OnBackInvokedCallback topCallback = onBackInvokedDispatcher.getTopCallback();
            if (onBackInvokedDispatcher.isDispatching()) {
                return 2;
            }
            if (topCallback instanceof android.window.OnBackAnimationCallback) {
                android.window.OnBackAnimationCallback onBackAnimationCallback = (android.window.OnBackAnimationCallback) topCallback;
                switch (keyEvent.getAction()) {
                    case 0:
                        if (keyEvent.getRepeatCount() == 0) {
                            onBackAnimationCallback.onBackStarted(new android.window.BackEvent(0.0f, 0.0f, 0.0f, 0));
                            break;
                        }
                        break;
                    case 1:
                        if (keyEvent.isCanceled()) {
                            onBackAnimationCallback.onBackCancelled();
                            break;
                        } else {
                            topCallback.onBackInvoked();
                            return 1;
                        }
                }
            } else if (topCallback != null && keyEvent.getAction() == 1) {
                if (!keyEvent.isCanceled()) {
                    topCallback.onBackInvoked();
                    return 1;
                }
                android.util.Log.d(android.view.ViewRootImpl.this.mTag, "Skip onBackInvoked(), reason: keyEvent.isCanceled=true");
            }
            return 2;
        }

        @Override // android.view.InputQueue.FinishedInputEventCallback
        public void onFinishedInputEvent(java.lang.Object obj, boolean z) {
            android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent = (android.view.ViewRootImpl.QueuedInputEvent) obj;
            if (z) {
                finish(queuedInputEvent, true);
            } else {
                forward(queuedInputEvent);
            }
        }
    }

    final class ViewPreImeInputStage extends android.view.ViewRootImpl.InputStage {
        public ViewPreImeInputStage(android.view.ViewRootImpl.InputStage inputStage) {
            super(inputStage);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            if (queuedInputEvent.mEvent instanceof android.view.KeyEvent) {
                return processKeyEvent(queuedInputEvent);
            }
            return 0;
        }

        private int processKeyEvent(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            if (android.view.ViewRootImpl.this.mView.dispatchKeyEventPreIme((android.view.KeyEvent) queuedInputEvent.mEvent)) {
                return 1;
            }
            return 0;
        }
    }

    final class ImeInputStage extends android.view.ViewRootImpl.AsyncInputStage implements android.view.inputmethod.InputMethodManager.FinishedInputEventCallback {
        public ImeInputStage(android.view.ViewRootImpl.InputStage inputStage, java.lang.String str) {
            super(inputStage, str);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            int onProcessImeInputStage = android.view.ViewRootImpl.this.mImeFocusController.onProcessImeInputStage(queuedInputEvent, queuedInputEvent.mEvent, android.view.ViewRootImpl.this.mWindowAttributes, this);
            switch (onProcessImeInputStage) {
                case -1:
                    return 3;
                case 0:
                    return 0;
                case 1:
                    return 1;
                default:
                    throw new java.lang.IllegalStateException("Unexpected result=" + onProcessImeInputStage);
            }
        }

        @Override // android.view.inputmethod.InputMethodManager.FinishedInputEventCallback
        public void onFinishedInputEvent(java.lang.Object obj, boolean z) {
            android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent = (android.view.ViewRootImpl.QueuedInputEvent) obj;
            if (z) {
                finish(queuedInputEvent, true);
            } else {
                forward(queuedInputEvent);
            }
        }
    }

    final class EarlyPostImeInputStage extends android.view.ViewRootImpl.InputStage {
        public EarlyPostImeInputStage(android.view.ViewRootImpl.InputStage inputStage) {
            super(inputStage);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            if (queuedInputEvent.mEvent instanceof android.view.KeyEvent) {
                return processKeyEvent(queuedInputEvent);
            }
            if (queuedInputEvent.mEvent instanceof android.view.MotionEvent) {
                return processMotionEvent(queuedInputEvent);
            }
            return 0;
        }

        private int processKeyEvent(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            android.view.KeyEvent keyEvent = (android.view.KeyEvent) queuedInputEvent.mEvent;
            if (android.view.ViewRootImpl.this.mAttachInfo.mTooltipHost != null) {
                android.view.ViewRootImpl.this.mAttachInfo.mTooltipHost.handleTooltipKey(keyEvent);
            }
            if (android.view.ViewRootImpl.this.checkForLeavingTouchModeAndConsume(keyEvent)) {
                return 1;
            }
            android.view.ViewRootImpl.this.mFallbackEventHandler.preDispatchKeyEvent(keyEvent);
            if (keyEvent.getAction() == 0) {
                android.view.ViewRootImpl.this.mLastClickToolType = 0;
            }
            return 0;
        }

        private int processMotionEvent(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            android.view.MotionEvent motionEvent = (android.view.MotionEvent) queuedInputEvent.mEvent;
            if (motionEvent.isFromSource(2)) {
                return processPointerEvent(queuedInputEvent);
            }
            int actionMasked = motionEvent.getActionMasked();
            if ((actionMasked == 0 || actionMasked == 8) && motionEvent.isFromSource(8)) {
                android.view.ViewRootImpl.this.ensureTouchMode(false);
            }
            return 0;
        }

        private int processPointerEvent(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            android.view.autofill.AutofillManager autofillManager;
            android.view.MotionEvent motionEvent = (android.view.MotionEvent) queuedInputEvent.mEvent;
            if (android.view.ViewRootImpl.this.mTranslator != null) {
                android.view.ViewRootImpl.this.mTranslator.translateEventInScreenToAppWindow(motionEvent);
            }
            int action = motionEvent.getAction();
            if (action == 0 || action == 8) {
                android.view.ViewRootImpl.this.ensureTouchMode(true);
            }
            if (action == 0 && (autofillManager = android.view.ViewRootImpl.this.getAutofillManager()) != null) {
                autofillManager.requestHideFillUi();
            }
            if (action == 0 && android.view.ViewRootImpl.this.mAttachInfo.mTooltipHost != null) {
                android.view.ViewRootImpl.this.mAttachInfo.mTooltipHost.hideTooltip();
            }
            if (android.view.ViewRootImpl.this.mCurScrollY != 0) {
                motionEvent.offsetLocation(0.0f, android.view.ViewRootImpl.this.mCurScrollY);
            }
            if (motionEvent.isTouchEvent()) {
                android.view.ViewRootImpl.this.mLastTouchPoint.x = motionEvent.getRawX();
                android.view.ViewRootImpl.this.mLastTouchPoint.y = motionEvent.getRawY();
                android.view.ViewRootImpl.this.mLastTouchSource = motionEvent.getSource();
                android.view.ViewRootImpl.this.mLastTouchDeviceId = motionEvent.getDeviceId();
                android.view.ViewRootImpl.this.mLastTouchPointerId = motionEvent.getPointerId(0);
                if (motionEvent.getActionMasked() == 1) {
                    android.view.ViewRootImpl.this.mLastClickToolType = motionEvent.getToolType(motionEvent.getActionIndex());
                }
            }
            return 0;
        }
    }

    final class NativePostImeInputStage extends android.view.ViewRootImpl.AsyncInputStage implements android.view.InputQueue.FinishedInputEventCallback {
        public NativePostImeInputStage(android.view.ViewRootImpl.InputStage inputStage, java.lang.String str) {
            super(inputStage, str);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            if (android.view.ViewRootImpl.this.mInputQueue == null) {
                return 0;
            }
            android.view.ViewRootImpl.this.mInputQueue.sendInputEvent(queuedInputEvent.mEvent, queuedInputEvent, false, this);
            return 3;
        }

        @Override // android.view.InputQueue.FinishedInputEventCallback
        public void onFinishedInputEvent(java.lang.Object obj, boolean z) {
            android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent = (android.view.ViewRootImpl.QueuedInputEvent) obj;
            if (z) {
                finish(queuedInputEvent, true);
            } else {
                forward(queuedInputEvent);
            }
        }
    }

    final class ViewPostImeInputStage extends android.view.ViewRootImpl.InputStage {
        public ViewPostImeInputStage(android.view.ViewRootImpl.InputStage inputStage) {
            super(inputStage);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            if (queuedInputEvent.mEvent instanceof android.view.KeyEvent) {
                return processKeyEvent(queuedInputEvent);
            }
            int source = queuedInputEvent.mEvent.getSource();
            if ((source & 2) != 0) {
                return processPointerEvent(queuedInputEvent);
            }
            if ((source & 4) != 0) {
                return processTrackballEvent(queuedInputEvent);
            }
            return processGenericMotionEvent(queuedInputEvent);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onDeliverToNext(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            if (android.view.ViewRootImpl.this.mUnbufferedInputDispatch && (queuedInputEvent.mEvent instanceof android.view.MotionEvent) && ((android.view.MotionEvent) queuedInputEvent.mEvent).isTouchEvent() && android.view.ViewRootImpl.isTerminalInputEvent(queuedInputEvent.mEvent)) {
                android.view.ViewRootImpl.this.mUnbufferedInputDispatch = false;
                android.view.ViewRootImpl.this.scheduleConsumeBatchedInput();
            }
            super.onDeliverToNext(queuedInputEvent);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private boolean performFocusNavigation(android.view.KeyEvent keyEvent) {
            int i;
            switch (keyEvent.getKeyCode()) {
                case 19:
                    if (keyEvent.hasNoModifiers()) {
                        i = 33;
                        break;
                    }
                    i = 0;
                    break;
                case 20:
                    if (keyEvent.hasNoModifiers()) {
                        i = 130;
                        break;
                    }
                    i = 0;
                    break;
                case 21:
                    if (keyEvent.hasNoModifiers()) {
                        i = 17;
                        break;
                    }
                    i = 0;
                    break;
                case 22:
                    if (keyEvent.hasNoModifiers()) {
                        i = 66;
                        break;
                    }
                    i = 0;
                    break;
                case 61:
                    if (keyEvent.hasNoModifiers()) {
                        i = 2;
                        break;
                    } else {
                        if (keyEvent.hasModifiers(1)) {
                            i = 1;
                            break;
                        }
                        i = 0;
                        break;
                    }
                default:
                    i = 0;
                    break;
            }
            if (i != 0) {
                android.view.View findFocus = android.view.ViewRootImpl.this.mView.findFocus();
                if (findFocus != null) {
                    android.view.ViewRootImpl.this.mAttachInfo.mNextFocusLooped = false;
                    android.view.View focusSearch = findFocus.focusSearch(i);
                    if (focusSearch != null && focusSearch != findFocus) {
                        if (android.view.ViewRootImpl.this.mAttachInfo.mNextFocusLooped) {
                            moveFocusToAdjacentWindow(i);
                        }
                        findFocus.getFocusedRect(android.view.ViewRootImpl.this.mTempRect);
                        if (android.view.ViewRootImpl.this.mView instanceof android.view.ViewGroup) {
                            ((android.view.ViewGroup) android.view.ViewRootImpl.this.mView).offsetDescendantRectToMyCoords(findFocus, android.view.ViewRootImpl.this.mTempRect);
                            ((android.view.ViewGroup) android.view.ViewRootImpl.this.mView).offsetRectIntoDescendantCoords(focusSearch, android.view.ViewRootImpl.this.mTempRect);
                        }
                        if (focusSearch.requestFocus(i, android.view.ViewRootImpl.this.mTempRect)) {
                            android.view.ViewRootImpl.this.playSoundEffect(android.view.SoundEffectConstants.getConstantForFocusDirection(i, keyEvent.getRepeatCount() > 0));
                            return true;
                        }
                    } else if (moveFocusToAdjacentWindow(i)) {
                        return true;
                    }
                    if (android.view.ViewRootImpl.this.mView.dispatchUnhandledMove(findFocus, i)) {
                        return true;
                    }
                } else if (android.view.ViewRootImpl.this.mView.restoreDefaultFocus() || moveFocusToAdjacentWindow(i)) {
                    return true;
                }
            }
            return false;
        }

        private boolean moveFocusToAdjacentWindow(int i) {
            if (android.view.ViewRootImpl.this.getConfiguration().windowConfiguration.getWindowingMode() != 6) {
                return false;
            }
            try {
                return android.view.ViewRootImpl.this.mWindowSession.moveFocusToAdjacentWindow(android.view.ViewRootImpl.this.mWindow, i);
            } catch (android.os.RemoteException e) {
                return false;
            }
        }

        private boolean performKeyboardGroupNavigation(int i) {
            android.view.View keyboardNavigationClusterSearch;
            android.view.View findFocus = android.view.ViewRootImpl.this.mView.findFocus();
            if (findFocus == null && android.view.ViewRootImpl.this.mView.restoreDefaultFocus()) {
                return true;
            }
            if (findFocus == null) {
                keyboardNavigationClusterSearch = android.view.ViewRootImpl.this.keyboardNavigationClusterSearch(null, i);
            } else {
                keyboardNavigationClusterSearch = findFocus.keyboardNavigationClusterSearch(null, i);
            }
            int i2 = (i == 2 || i == 1) ? 130 : i;
            if (keyboardNavigationClusterSearch != null && keyboardNavigationClusterSearch.isRootNamespace()) {
                if (!keyboardNavigationClusterSearch.restoreFocusNotInCluster()) {
                    keyboardNavigationClusterSearch = android.view.ViewRootImpl.this.keyboardNavigationClusterSearch(null, i);
                } else {
                    android.view.ViewRootImpl.this.playSoundEffect(android.view.SoundEffectConstants.getContantForFocusDirection(i));
                    return true;
                }
            }
            if (keyboardNavigationClusterSearch != null && keyboardNavigationClusterSearch.restoreFocusInCluster(i2)) {
                android.view.ViewRootImpl.this.playSoundEffect(android.view.SoundEffectConstants.getContantForFocusDirection(i));
                return true;
            }
            return false;
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x008f A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:33:0x0090  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00a1 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00a2  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private int processKeyEvent(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            int i;
            android.view.KeyEvent keyEvent = (android.view.KeyEvent) queuedInputEvent.mEvent;
            if (android.view.ViewRootImpl.this.mUnhandledKeyManager.preViewDispatch(keyEvent) || android.view.ViewRootImpl.this.mView.dispatchKeyEvent(keyEvent)) {
                return 1;
            }
            if (shouldDropInputEvent(queuedInputEvent)) {
                return 2;
            }
            if (android.view.ViewRootImpl.this.mUnhandledKeyManager.dispatch(android.view.ViewRootImpl.this.mView, keyEvent)) {
                return 1;
            }
            if (keyEvent.getAction() == 0 && keyEvent.getKeyCode() == 61) {
                if (android.view.KeyEvent.metaStateHasModifiers(keyEvent.getMetaState(), 4096)) {
                    i = 2;
                } else if (android.view.KeyEvent.metaStateHasModifiers(keyEvent.getMetaState(), 4097)) {
                    i = 1;
                }
                if (keyEvent.getAction() == 0 && !android.view.KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState()) && keyEvent.getRepeatCount() == 0 && !android.view.KeyEvent.isModifierKey(keyEvent.getKeyCode()) && i == 0) {
                    if (!android.view.ViewRootImpl.this.mView.dispatchKeyShortcutEvent(keyEvent)) {
                        return 1;
                    }
                    if (shouldDropInputEvent(queuedInputEvent)) {
                        return 2;
                    }
                }
                if (!android.view.ViewRootImpl.this.mFallbackEventHandler.dispatchKeyEvent(keyEvent)) {
                    return 1;
                }
                if (shouldDropInputEvent(queuedInputEvent)) {
                    return 2;
                }
                if (keyEvent.getAction() == 0) {
                    if (i != 0) {
                        if (performKeyboardGroupNavigation(i)) {
                            return 1;
                        }
                    } else if (performFocusNavigation(keyEvent)) {
                        return 1;
                    }
                }
                return 0;
            }
            i = 0;
            if (keyEvent.getAction() == 0) {
                if (!android.view.ViewRootImpl.this.mView.dispatchKeyShortcutEvent(keyEvent)) {
                }
            }
            if (!android.view.ViewRootImpl.this.mFallbackEventHandler.dispatchKeyEvent(keyEvent)) {
            }
        }

        private int processPointerEvent(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            android.view.MotionEvent motionEvent = (android.view.MotionEvent) queuedInputEvent.mEvent;
            int action = motionEvent.getAction();
            boolean onTouchEvent = android.view.ViewRootImpl.this.mHandwritingInitiator.onTouchEvent(motionEvent);
            if (onTouchEvent) {
                android.view.ViewRootImpl.this.mLastClickToolType = motionEvent.getToolType(motionEvent.getActionIndex());
            }
            android.view.ViewRootImpl.this.mAttachInfo.mUnbufferedDispatchRequested = false;
            android.view.ViewRootImpl.this.mAttachInfo.mHandlingPointerEvent = true;
            int i = (onTouchEvent || android.view.ViewRootImpl.this.mView.dispatchPointerEvent(motionEvent)) ? 1 : 0;
            maybeUpdatePointerIcon(motionEvent);
            android.view.ViewRootImpl.this.maybeUpdateTooltip(motionEvent);
            android.view.ViewRootImpl.this.mAttachInfo.mHandlingPointerEvent = false;
            if (android.view.ViewRootImpl.this.mAttachInfo.mUnbufferedDispatchRequested && !android.view.ViewRootImpl.this.mUnbufferedInputDispatch) {
                android.view.ViewRootImpl.this.mUnbufferedInputDispatch = true;
                if (android.view.ViewRootImpl.this.mConsumeBatchedInputScheduled) {
                    android.view.ViewRootImpl.this.scheduleConsumeBatchedInputImmediately();
                }
            }
            if (i != 0 && android.view.ViewRootImpl.this.shouldTouchBoost(action, android.view.ViewRootImpl.this.mWindowAttributes.type)) {
                android.view.ViewRootImpl.this.mIsTouchBoosting = true;
                android.view.ViewRootImpl.this.setPreferredFrameRateCategory(android.view.ViewRootImpl.this.mPreferredFrameRateCategory);
            }
            if (android.view.ViewRootImpl.this.mIsTouchBoosting && (action == 1 || action == 3)) {
                android.view.ViewRootImpl.this.mHandler.removeMessages(39);
                android.view.ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(39, 3000L);
            }
            return i;
        }

        private void maybeUpdatePointerIcon(android.view.MotionEvent motionEvent) {
            if (motionEvent.getPointerCount() != 1) {
            }
            int actionMasked = motionEvent.getActionMasked();
            if (!(motionEvent.isStylusPointer() && motionEvent.isHoverEvent() && android.view.ViewRootImpl.this.mIsStylusPointerIconEnabled) && !motionEvent.isFromSource(8194)) {
                return;
            }
            if (actionMasked == 9 || actionMasked == 10) {
                android.view.ViewRootImpl.this.mPointerIconType = null;
                android.view.ViewRootImpl.this.mResolvedPointerIcon = null;
            }
            if (actionMasked != 10 && !android.view.ViewRootImpl.this.updatePointerIcon(motionEvent) && actionMasked == 7) {
                android.view.ViewRootImpl.this.mPointerIconType = null;
                android.view.ViewRootImpl.this.mResolvedPointerIcon = null;
            }
            switch (actionMasked) {
                case 1:
                case 3:
                case 6:
                case 10:
                    if (android.view.ViewRootImpl.this.mPointerIconEvent != null) {
                        android.view.ViewRootImpl.this.mPointerIconEvent.recycle();
                    }
                    android.view.ViewRootImpl.this.mPointerIconEvent = null;
                    break;
                default:
                    android.view.ViewRootImpl.this.mPointerIconEvent = android.view.MotionEvent.obtain(motionEvent);
                    break;
            }
        }

        private int processTrackballEvent(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            android.view.MotionEvent motionEvent = (android.view.MotionEvent) queuedInputEvent.mEvent;
            return ((!motionEvent.isFromSource(android.view.InputDevice.SOURCE_MOUSE_RELATIVE) || (android.view.ViewRootImpl.this.hasPointerCapture() && !android.view.ViewRootImpl.this.mView.dispatchCapturedPointerEvent(motionEvent))) && !android.view.ViewRootImpl.this.mView.dispatchTrackballEvent(motionEvent)) ? 0 : 1;
        }

        private int processGenericMotionEvent(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            android.view.MotionEvent motionEvent = (android.view.MotionEvent) queuedInputEvent.mEvent;
            return ((motionEvent.isFromSource(android.view.InputDevice.SOURCE_TOUCHPAD) && android.view.ViewRootImpl.this.hasPointerCapture() && android.view.ViewRootImpl.this.mView.dispatchCapturedPointerEvent(motionEvent)) || android.view.ViewRootImpl.this.mView.dispatchGenericMotionEvent(motionEvent)) ? 1 : 0;
        }
    }

    public boolean isHandlingPointerEvent() {
        return this.mAttachInfo.mHandlingPointerEvent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetPointerIcon(android.view.MotionEvent motionEvent) {
        this.mPointerIconType = null;
        this.mResolvedPointerIcon = null;
        updatePointerIcon(motionEvent);
    }

    public void refreshPointerIcon() {
        this.mHandler.removeMessages(41);
        this.mHandler.sendEmptyMessage(41);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updatePointerIcon(android.view.MotionEvent motionEvent) {
        android.view.PointerIcon pointerIcon;
        android.view.PointerIcon pointerIcon2;
        float x = motionEvent.getX(0);
        float y = motionEvent.getY(0);
        if (this.mView == null) {
            android.util.Slog.d(this.mTag, "updatePointerIcon called after view was removed");
            return false;
        }
        if (x < 0.0f || x >= this.mView.getWidth() || y < 0.0f || y >= this.mView.getHeight()) {
            android.util.Slog.d(this.mTag, "updatePointerIcon called with position out of bounds");
            return false;
        }
        if (motionEvent.isStylusPointer() && this.mIsStylusPointerIconEnabled) {
            pointerIcon = this.mHandwritingInitiator.onResolvePointerIcon(this.mContext, motionEvent);
        } else {
            pointerIcon = null;
        }
        if (pointerIcon == null) {
            pointerIcon = this.mView.onResolvePointerIcon(motionEvent, 0);
        }
        if (com.android.input.flags.Flags.enablePointerChoreographer()) {
            if (pointerIcon != null) {
                pointerIcon2 = pointerIcon;
            } else {
                pointerIcon2 = android.view.PointerIcon.getSystemIcon(this.mContext, 1);
            }
            if (java.util.Objects.equals(this.mResolvedPointerIcon, pointerIcon2)) {
                return true;
            }
            this.mResolvedPointerIcon = pointerIcon2;
            android.hardware.input.InputManagerGlobal.getInstance().setPointerIcon(pointerIcon2, motionEvent.getDisplayId(), motionEvent.getDeviceId(), motionEvent.getPointerId(0), getInputToken());
            return true;
        }
        int type = pointerIcon != null ? pointerIcon.getType() : 1;
        if (this.mPointerIconType == null || this.mPointerIconType.intValue() != type) {
            this.mPointerIconType = java.lang.Integer.valueOf(type);
            this.mCustomPointerIcon = null;
            if (this.mPointerIconType.intValue() != -1) {
                android.hardware.input.InputManagerGlobal.getInstance().setPointerIconType(type);
                return true;
            }
        }
        if (this.mPointerIconType.intValue() == -1 && !pointerIcon.equals(this.mCustomPointerIcon)) {
            this.mCustomPointerIcon = pointerIcon;
            android.hardware.input.InputManagerGlobal.getInstance().setCustomPointerIcon(this.mCustomPointerIcon);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeUpdateTooltip(android.view.MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 1) {
            return;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 9 && actionMasked != 7 && actionMasked != 10) {
            return;
        }
        if (this.mAccessibilityManager.isEnabled() && this.mAccessibilityManager.isTouchExplorationEnabled()) {
            return;
        }
        if (this.mView == null) {
            android.util.Slog.d(this.mTag, "maybeUpdateTooltip called after view was removed");
        } else {
            this.mView.dispatchTooltipHoverEvent(motionEvent);
        }
    }

    private android.view.View getFocusedViewOrNull() {
        if (this.mView != null) {
            return this.mView.findFocus();
        }
        return null;
    }

    final class SyntheticInputStage extends android.view.ViewRootImpl.InputStage {
        private final android.view.ViewRootImpl.SyntheticJoystickHandler mJoystick;
        private final android.view.ViewRootImpl.SyntheticKeyboardHandler mKeyboard;
        private final android.view.ViewRootImpl.SyntheticTouchNavigationHandler mTouchNavigation;
        private final android.view.ViewRootImpl.SyntheticTrackballHandler mTrackball;

        public SyntheticInputStage() {
            super(null);
            this.mTrackball = android.view.ViewRootImpl.this.new SyntheticTrackballHandler();
            this.mJoystick = android.view.ViewRootImpl.this.new SyntheticJoystickHandler();
            this.mTouchNavigation = android.view.ViewRootImpl.this.new SyntheticTouchNavigationHandler();
            this.mKeyboard = android.view.ViewRootImpl.this.new SyntheticKeyboardHandler();
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            queuedInputEvent.mFlags |= 16;
            if (queuedInputEvent.mEvent instanceof android.view.MotionEvent) {
                android.view.MotionEvent motionEvent = (android.view.MotionEvent) queuedInputEvent.mEvent;
                int source = motionEvent.getSource();
                if ((source & 4) != 0) {
                    if (!motionEvent.isFromSource(android.view.InputDevice.SOURCE_MOUSE_RELATIVE)) {
                        this.mTrackball.process(motionEvent);
                    }
                    return 1;
                }
                if ((source & 16) != 0) {
                    this.mJoystick.process(motionEvent);
                    return 1;
                }
                if ((source & 2097152) == 2097152) {
                    this.mTouchNavigation.process(motionEvent);
                    return 1;
                }
                return 0;
            }
            if ((queuedInputEvent.mFlags & 32) != 0) {
                this.mKeyboard.process((android.view.KeyEvent) queuedInputEvent.mEvent);
                return 1;
            }
            return 0;
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onDeliverToNext(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
            if ((queuedInputEvent.mFlags & 16) == 0 && (queuedInputEvent.mEvent instanceof android.view.MotionEvent)) {
                int source = ((android.view.MotionEvent) queuedInputEvent.mEvent).getSource();
                if ((source & 4) != 0) {
                    this.mTrackball.cancel();
                } else if ((source & 16) != 0) {
                    this.mJoystick.cancel();
                }
            }
            super.onDeliverToNext(queuedInputEvent);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onWindowFocusChanged(boolean z) {
            if (!z) {
                this.mJoystick.cancel();
            }
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onDetachedFromWindow() {
            this.mJoystick.cancel();
        }
    }

    final class SyntheticTrackballHandler {
        private long mLastTime;
        private final android.view.ViewRootImpl.TrackballAxis mX = new android.view.ViewRootImpl.TrackballAxis();
        private final android.view.ViewRootImpl.TrackballAxis mY = new android.view.ViewRootImpl.TrackballAxis();

        SyntheticTrackballHandler() {
        }

        public void process(android.view.MotionEvent motionEvent) {
            int i;
            long j;
            int i2;
            int i3;
            int i4;
            long j2;
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (this.mLastTime + 250 < uptimeMillis) {
                this.mX.reset(0);
                this.mY.reset(0);
                this.mLastTime = uptimeMillis;
            }
            int action = motionEvent.getAction();
            int metaState = motionEvent.getMetaState();
            switch (action) {
                case 0:
                    i = 0;
                    this.mX.reset(2);
                    this.mY.reset(2);
                    j = uptimeMillis;
                    i2 = 2;
                    android.view.ViewRootImpl.this.enqueueInputEvent(new android.view.KeyEvent(uptimeMillis, uptimeMillis, 0, 23, 0, metaState, -1, 0, 1024, 257));
                    break;
                case 1:
                    this.mX.reset(2);
                    this.mY.reset(2);
                    i = 0;
                    android.view.ViewRootImpl.this.enqueueInputEvent(new android.view.KeyEvent(uptimeMillis, uptimeMillis, 1, 23, 0, metaState, -1, 0, 1024, 257));
                    j = uptimeMillis;
                    i2 = 2;
                    break;
                default:
                    i = 0;
                    j = uptimeMillis;
                    i2 = 2;
                    break;
            }
            float collect = this.mX.collect(motionEvent.getX(), motionEvent.getEventTime(), android.hardware.gnss.GnssSignalType.CODE_TYPE_X);
            float collect2 = this.mY.collect(motionEvent.getY(), motionEvent.getEventTime(), android.hardware.gnss.GnssSignalType.CODE_TYPE_Y);
            float f = 1.0f;
            if (collect > collect2) {
                i3 = this.mX.generate();
                if (i3 == 0) {
                    i4 = i;
                } else {
                    int i5 = i3 > 0 ? 22 : 21;
                    f = this.mX.acceleration;
                    this.mY.reset(i2);
                    i4 = i5;
                }
            } else if (collect2 <= 0.0f) {
                i3 = i;
                i4 = i3;
            } else {
                i3 = this.mY.generate();
                if (i3 == 0) {
                    i4 = i;
                } else {
                    int i6 = i3 > 0 ? 20 : 19;
                    f = this.mY.acceleration;
                    this.mX.reset(i2);
                    i4 = i6;
                }
            }
            if (i4 != 0) {
                if (i3 < 0) {
                    i3 = -i3;
                }
                int i7 = (int) (i3 * f);
                if (i7 <= i3) {
                    j2 = j;
                } else {
                    int i8 = i3 - 1;
                    android.view.ViewRootImpl.this.enqueueInputEvent(new android.view.KeyEvent(j, j, 2, i4, i7 - i8, metaState, -1, 0, 1024, 257));
                    i3 = i8;
                    j2 = j;
                }
                while (i3 > 0) {
                    i3--;
                    j2 = android.os.SystemClock.uptimeMillis();
                    int i9 = i4;
                    android.view.ViewRootImpl.this.enqueueInputEvent(new android.view.KeyEvent(j2, j2, 0, i9, 0, metaState, -1, 0, 1024, 257));
                    android.view.ViewRootImpl.this.enqueueInputEvent(new android.view.KeyEvent(j2, j2, 1, i9, 0, metaState, -1, 0, 1024, 257));
                }
                this.mLastTime = j2;
            }
        }

        public void cancel() {
            this.mLastTime = -2147483648L;
            if (android.view.ViewRootImpl.this.mView != null && android.view.ViewRootImpl.this.mAdded) {
                android.view.ViewRootImpl.this.ensureTouchMode(false);
            }
        }
    }

    static final class TrackballAxis {
        static final float ACCEL_MOVE_SCALING_FACTOR = 0.025f;
        static final long FAST_MOVE_TIME = 150;
        static final float FIRST_MOVEMENT_THRESHOLD = 0.5f;
        static final float MAX_ACCELERATION = 20.0f;
        static final float SECOND_CUMULATIVE_MOVEMENT_THRESHOLD = 2.0f;
        static final float SUBSEQUENT_INCREMENTAL_MOVEMENT_THRESHOLD = 1.0f;
        int dir;
        int nonAccelMovement;
        float position;
        int step;
        float acceleration = 1.0f;
        long lastMoveTime = 0;

        TrackballAxis() {
        }

        void reset(int i) {
            this.position = 0.0f;
            this.acceleration = 1.0f;
            this.lastMoveTime = 0L;
            this.step = i;
            this.dir = 0;
        }

        float collect(float f, long j, java.lang.String str) {
            long j2;
            if (f > 0.0f) {
                j2 = (long) (150.0f * f);
                if (this.dir < 0) {
                    this.position = 0.0f;
                    this.step = 0;
                    this.acceleration = 1.0f;
                    this.lastMoveTime = 0L;
                }
                this.dir = 1;
            } else if (f < 0.0f) {
                j2 = (long) ((-f) * 150.0f);
                if (this.dir > 0) {
                    this.position = 0.0f;
                    this.step = 0;
                    this.acceleration = 1.0f;
                    this.lastMoveTime = 0L;
                }
                this.dir = -1;
            } else {
                j2 = 0;
            }
            if (j2 > 0) {
                long j3 = j - this.lastMoveTime;
                this.lastMoveTime = j;
                float f2 = this.acceleration;
                if (j3 < j2) {
                    float f3 = (j2 - j3) * ACCEL_MOVE_SCALING_FACTOR;
                    if (f3 > 1.0f) {
                        f2 *= f3;
                    }
                    if (f2 >= MAX_ACCELERATION) {
                        f2 = 20.0f;
                    }
                    this.acceleration = f2;
                } else {
                    float f4 = (j3 - j2) * ACCEL_MOVE_SCALING_FACTOR;
                    if (f4 > 1.0f) {
                        f2 /= f4;
                    }
                    this.acceleration = f2 > 1.0f ? f2 : 1.0f;
                }
            }
            this.position += f;
            return java.lang.Math.abs(this.position);
        }

        int generate() {
            int i = 0;
            this.nonAccelMovement = 0;
            while (true) {
                int i2 = this.position >= 0.0f ? 1 : -1;
                switch (this.step) {
                    case 0:
                        if (java.lang.Math.abs(this.position) < 0.5f) {
                            return i;
                        }
                        i += i2;
                        this.nonAccelMovement += i2;
                        this.step = 1;
                        break;
                    case 1:
                        if (java.lang.Math.abs(this.position) < SECOND_CUMULATIVE_MOVEMENT_THRESHOLD) {
                            return i;
                        }
                        i += i2;
                        this.nonAccelMovement += i2;
                        this.position -= i2 * SECOND_CUMULATIVE_MOVEMENT_THRESHOLD;
                        this.step = 2;
                        break;
                    default:
                        if (java.lang.Math.abs(this.position) < 1.0f) {
                            return i;
                        }
                        i += i2;
                        this.position -= i2 * 1.0f;
                        float f = this.acceleration * 1.1f;
                        if (f >= MAX_ACCELERATION) {
                            f = this.acceleration;
                        }
                        this.acceleration = f;
                        break;
                }
            }
        }
    }

    final class SyntheticJoystickHandler extends android.os.Handler {
        private static final int MSG_ENQUEUE_X_AXIS_KEY_REPEAT = 1;
        private static final int MSG_ENQUEUE_Y_AXIS_KEY_REPEAT = 2;
        private final android.util.SparseArray<android.view.KeyEvent> mDeviceKeyEvents;
        private final android.view.ViewRootImpl.SyntheticJoystickHandler.JoystickAxesState mJoystickAxesState;

        public SyntheticJoystickHandler() {
            super(true);
            this.mJoystickAxesState = new android.view.ViewRootImpl.SyntheticJoystickHandler.JoystickAxesState();
            this.mDeviceKeyEvents = new android.util.SparseArray<>();
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                case 2:
                    if (android.view.ViewRootImpl.this.mAttachInfo.mHasWindowFocus) {
                        android.view.KeyEvent keyEvent = (android.view.KeyEvent) message.obj;
                        android.view.KeyEvent changeTimeRepeat = android.view.KeyEvent.changeTimeRepeat(keyEvent, android.os.SystemClock.uptimeMillis(), keyEvent.getRepeatCount() + 1);
                        android.view.ViewRootImpl.this.enqueueInputEvent(changeTimeRepeat);
                        android.os.Message obtainMessage = obtainMessage(message.what, changeTimeRepeat);
                        obtainMessage.setAsynchronous(true);
                        sendMessageDelayed(obtainMessage, android.view.ViewConfiguration.getKeyRepeatDelay());
                        break;
                    }
                    break;
            }
        }

        public void process(android.view.MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()) {
                case 2:
                    update(motionEvent);
                    break;
                case 3:
                    cancel();
                    break;
                default:
                    android.util.Log.w(android.view.ViewRootImpl.this.mTag, "Unexpected action: " + motionEvent.getActionMasked());
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void cancel() {
            removeMessages(1);
            removeMessages(2);
            for (int i = 0; i < this.mDeviceKeyEvents.size(); i++) {
                android.view.KeyEvent valueAt = this.mDeviceKeyEvents.valueAt(i);
                if (valueAt != null) {
                    android.view.ViewRootImpl.this.enqueueInputEvent(android.view.KeyEvent.changeTimeRepeat(valueAt, android.os.SystemClock.uptimeMillis(), 0));
                }
            }
            this.mDeviceKeyEvents.clear();
            this.mJoystickAxesState.resetState();
        }

        private void update(android.view.MotionEvent motionEvent) {
            int historySize = motionEvent.getHistorySize();
            for (int i = 0; i < historySize; i++) {
                long historicalEventTime = motionEvent.getHistoricalEventTime(i);
                this.mJoystickAxesState.updateStateForAxis(motionEvent, historicalEventTime, 0, motionEvent.getHistoricalAxisValue(0, 0, i));
                this.mJoystickAxesState.updateStateForAxis(motionEvent, historicalEventTime, 1, motionEvent.getHistoricalAxisValue(1, 0, i));
                this.mJoystickAxesState.updateStateForAxis(motionEvent, historicalEventTime, 15, motionEvent.getHistoricalAxisValue(15, 0, i));
                this.mJoystickAxesState.updateStateForAxis(motionEvent, historicalEventTime, 16, motionEvent.getHistoricalAxisValue(16, 0, i));
            }
            long eventTime = motionEvent.getEventTime();
            this.mJoystickAxesState.updateStateForAxis(motionEvent, eventTime, 0, motionEvent.getAxisValue(0));
            this.mJoystickAxesState.updateStateForAxis(motionEvent, eventTime, 1, motionEvent.getAxisValue(1));
            this.mJoystickAxesState.updateStateForAxis(motionEvent, eventTime, 15, motionEvent.getAxisValue(15));
            this.mJoystickAxesState.updateStateForAxis(motionEvent, eventTime, 16, motionEvent.getAxisValue(16));
        }

        final class JoystickAxesState {
            private static final int STATE_DOWN_OR_RIGHT = 1;
            private static final int STATE_NEUTRAL = 0;
            private static final int STATE_UP_OR_LEFT = -1;
            final int[] mAxisStatesHat = {0, 0};
            final int[] mAxisStatesStick = {0, 0};

            JoystickAxesState() {
            }

            void resetState() {
                this.mAxisStatesHat[0] = 0;
                this.mAxisStatesHat[1] = 0;
                this.mAxisStatesStick[0] = 0;
                this.mAxisStatesStick[1] = 0;
            }

            void updateStateForAxis(android.view.MotionEvent motionEvent, long j, int i, float f) {
                int i2;
                char c;
                int i3;
                int joystickAxisAndStateToKeycode;
                if (isXAxis(i)) {
                    c = 0;
                    i2 = 1;
                } else if (isYAxis(i)) {
                    i2 = 2;
                    c = 1;
                } else {
                    android.util.Log.e(android.view.ViewRootImpl.this.mTag, "Unexpected axis " + i + " in updateStateForAxis!");
                    return;
                }
                int joystickAxisValueToState = joystickAxisValueToState(f);
                if (i == 0 || i == 1) {
                    i3 = this.mAxisStatesStick[c];
                } else {
                    i3 = this.mAxisStatesHat[c];
                }
                if (i3 == joystickAxisValueToState) {
                    return;
                }
                int metaState = motionEvent.getMetaState();
                int deviceId = motionEvent.getDeviceId();
                int source = motionEvent.getSource();
                if (i3 == 1 || i3 == -1) {
                    int joystickAxisAndStateToKeycode2 = joystickAxisAndStateToKeycode(i, i3);
                    if (joystickAxisAndStateToKeycode2 != 0) {
                        android.view.ViewRootImpl.this.enqueueInputEvent(new android.view.KeyEvent(j, j, 1, joystickAxisAndStateToKeycode2, 0, metaState, deviceId, 0, 1024, source));
                        deviceId = deviceId;
                        android.view.ViewRootImpl.SyntheticJoystickHandler.this.mDeviceKeyEvents.put(deviceId, null);
                    }
                    android.view.ViewRootImpl.SyntheticJoystickHandler.this.removeMessages(i2);
                }
                if ((joystickAxisValueToState == 1 || joystickAxisValueToState == -1) && (joystickAxisAndStateToKeycode = joystickAxisAndStateToKeycode(i, joystickAxisValueToState)) != 0) {
                    int i4 = deviceId;
                    android.view.KeyEvent keyEvent = new android.view.KeyEvent(j, j, 0, joystickAxisAndStateToKeycode, 0, metaState, i4, 0, 1024, source);
                    android.view.ViewRootImpl.this.enqueueInputEvent(keyEvent);
                    android.os.Message obtainMessage = android.view.ViewRootImpl.SyntheticJoystickHandler.this.obtainMessage(i2, keyEvent);
                    obtainMessage.setAsynchronous(true);
                    android.view.ViewRootImpl.SyntheticJoystickHandler.this.sendMessageDelayed(obtainMessage, android.view.ViewConfiguration.getKeyRepeatTimeout());
                    android.view.ViewRootImpl.SyntheticJoystickHandler.this.mDeviceKeyEvents.put(i4, new android.view.KeyEvent(j, j, 1, joystickAxisAndStateToKeycode, 0, metaState, i4, 0, 1056, source));
                }
                if (i == 0 || i == 1) {
                    this.mAxisStatesStick[c] = joystickAxisValueToState;
                } else {
                    this.mAxisStatesHat[c] = joystickAxisValueToState;
                }
            }

            private boolean isXAxis(int i) {
                return i == 0 || i == 15;
            }

            private boolean isYAxis(int i) {
                return i == 1 || i == 16;
            }

            private int joystickAxisAndStateToKeycode(int i, int i2) {
                if (isXAxis(i) && i2 == -1) {
                    return 21;
                }
                if (isXAxis(i) && i2 == 1) {
                    return 22;
                }
                if (isYAxis(i) && i2 == -1) {
                    return 19;
                }
                if (isYAxis(i) && i2 == 1) {
                    return 20;
                }
                android.util.Log.e(android.view.ViewRootImpl.this.mTag, "Unknown axis " + i + " or direction " + i2);
                return 0;
            }

            private int joystickAxisValueToState(float f) {
                if (f >= 0.5f) {
                    return 1;
                }
                if (f <= -0.5f) {
                    return -1;
                }
                return 0;
            }
        }
    }

    final class SyntheticTouchNavigationHandler extends android.os.Handler {
        private static final java.lang.String LOCAL_TAG = "SyntheticTouchNavigationHandler";
        private int mCurrentDeviceId;
        private int mCurrentSource;
        private final android.view.GestureDetector mGestureDetector;
        private int mPendingKeyMetaState;

        SyntheticTouchNavigationHandler() {
            super(true);
            this.mCurrentDeviceId = -1;
            this.mGestureDetector = new android.view.GestureDetector(android.view.ViewRootImpl.this.mContext, new android.view.GestureDetector.OnGestureListener() { // from class: android.view.ViewRootImpl.SyntheticTouchNavigationHandler.1
                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onDown(android.view.MotionEvent motionEvent) {
                    return true;
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public void onShowPress(android.view.MotionEvent motionEvent) {
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onSingleTapUp(android.view.MotionEvent motionEvent) {
                    android.view.ViewRootImpl.SyntheticTouchNavigationHandler.this.dispatchTap(motionEvent.getEventTime());
                    return true;
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onScroll(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
                    return true;
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public void onLongPress(android.view.MotionEvent motionEvent) {
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onFling(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
                    android.view.ViewRootImpl.SyntheticTouchNavigationHandler.this.dispatchFling(f, f2, motionEvent2.getEventTime());
                    return true;
                }
            });
        }

        public void process(android.view.MotionEvent motionEvent) {
            if (motionEvent.getDevice() == null) {
                return;
            }
            this.mPendingKeyMetaState = motionEvent.getMetaState();
            int deviceId = motionEvent.getDeviceId();
            int source = motionEvent.getSource();
            if (this.mCurrentDeviceId != deviceId || this.mCurrentSource != source) {
                this.mCurrentDeviceId = deviceId;
                this.mCurrentSource = source;
            }
            this.mGestureDetector.onTouchEvent(motionEvent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchTap(long j) {
            dispatchEvent(j, 23);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchFling(float f, float f2, long j) {
            if (java.lang.Math.abs(f) > java.lang.Math.abs(f2)) {
                dispatchEvent(j, f > 0.0f ? 22 : 21);
            } else {
                dispatchEvent(j, f2 > 0.0f ? 20 : 19);
            }
        }

        private void dispatchEvent(long j, int i) {
            android.view.ViewRootImpl.this.enqueueInputEvent(new android.view.KeyEvent(j, j, 0, i, 0, this.mPendingKeyMetaState, this.mCurrentDeviceId, 0, 1024, this.mCurrentSource));
            android.view.ViewRootImpl.this.enqueueInputEvent(new android.view.KeyEvent(j, j, 1, i, 0, this.mPendingKeyMetaState, this.mCurrentDeviceId, 0, 1024, this.mCurrentSource));
        }
    }

    final class SyntheticKeyboardHandler {
        SyntheticKeyboardHandler() {
        }

        public void process(android.view.KeyEvent keyEvent) {
            android.view.KeyCharacterMap.FallbackAction fallbackAction;
            if ((keyEvent.getFlags() & 1024) == 0 && (fallbackAction = keyEvent.getKeyCharacterMap().getFallbackAction(keyEvent.getKeyCode(), keyEvent.getMetaState())) != null) {
                android.view.KeyEvent obtain = android.view.KeyEvent.obtain(keyEvent.getDownTime(), keyEvent.getEventTime(), keyEvent.getAction(), fallbackAction.keyCode, keyEvent.getRepeatCount(), fallbackAction.metaState, keyEvent.getDeviceId(), keyEvent.getScanCode(), keyEvent.getFlags() | 1024, keyEvent.getSource(), null);
                fallbackAction.recycle();
                android.view.ViewRootImpl.this.enqueueInputEvent(obtain);
            }
        }
    }

    private static boolean isNavigationKey(android.view.KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 61:
            case 62:
            case 66:
            case 92:
            case 93:
            case 122:
            case 123:
                return true;
            default:
                return false;
        }
    }

    private static boolean isTypingKey(android.view.KeyEvent keyEvent) {
        return keyEvent.getUnicodeChar() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkForLeavingTouchModeAndConsume(android.view.KeyEvent keyEvent) {
        if (!this.mAttachInfo.mInTouchMode) {
            return false;
        }
        int action = keyEvent.getAction();
        if ((action != 0 && action != 2) || (keyEvent.getFlags() & 4) != 0) {
            return false;
        }
        if (keyEvent.hasNoModifiers() && isNavigationKey(keyEvent)) {
            return ensureTouchMode(false);
        }
        if (!isTypingKey(keyEvent)) {
            return false;
        }
        ensureTouchMode(false);
        return false;
    }

    void setLocalDragState(java.lang.Object obj) {
        this.mLocalDragState = obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDragEvent(android.view.DragEvent dragEvent) {
        if (this.mView != null && this.mAdded) {
            int i = dragEvent.mAction;
            if (i == 1) {
                this.mCurrentDragView = null;
                this.mDragDescription = dragEvent.mClipDescription;
                if (this.mStartedDragViewForA11y != null) {
                    this.mStartedDragViewForA11y.sendWindowContentChangedAccessibilityEvent(128);
                }
            } else {
                if (i == 4) {
                    this.mDragDescription = null;
                }
                dragEvent.mClipDescription = this.mDragDescription;
            }
            if (i == 6) {
                if (android.view.View.sCascadedDragDrop) {
                    this.mView.dispatchDragEnterExitInPreN(dragEvent);
                }
                setDragFocus(null, dragEvent);
            } else {
                if (i == 2 || i == 3) {
                    this.mDragPoint.set(dragEvent.mX, dragEvent.mY);
                    if (this.mTranslator != null) {
                        this.mTranslator.translatePointInScreenToAppWindow(this.mDragPoint);
                    }
                    if (this.mCurScrollY != 0) {
                        this.mDragPoint.offset(0.0f, this.mCurScrollY);
                    }
                    dragEvent.mX = this.mDragPoint.x;
                    dragEvent.mY = this.mDragPoint.y;
                }
                android.view.View view = this.mCurrentDragView;
                if (i == 3 && dragEvent.mClipData != null) {
                    dragEvent.mClipData.prepareToEnterProcess(this.mView.getContext().getAttributionSource());
                }
                boolean dispatchDragEvent = this.mView.dispatchDragEvent(dragEvent);
                if (i == 2 && !dragEvent.mEventHandlerWasCalled) {
                    setDragFocus(null, dragEvent);
                }
                if (view != this.mCurrentDragView) {
                    if (view != null) {
                        try {
                            this.mWindowSession.dragRecipientExited(this.mWindow);
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(this.mTag, "Unable to note drag target change");
                        }
                    }
                    if (this.mCurrentDragView != null) {
                        this.mWindowSession.dragRecipientEntered(this.mWindow);
                    }
                }
                if (i == 3) {
                    try {
                        android.util.Log.i(this.mTag, "Reporting drop result: " + dispatchDragEvent);
                        this.mWindowSession.reportDropResult(this.mWindow, dispatchDragEvent);
                    } catch (android.os.RemoteException e2) {
                        android.util.Log.e(this.mTag, "Unable to report drop result");
                    }
                }
                if (i == 4) {
                    if (this.mStartedDragViewForA11y != null) {
                        if (!dragEvent.getResult()) {
                            this.mStartedDragViewForA11y.sendWindowContentChangedAccessibilityEvent(512);
                        }
                        this.mStartedDragViewForA11y.setAccessibilityDragStarted(false);
                    }
                    this.mStartedDragViewForA11y = null;
                    this.mCurrentDragView = null;
                    setLocalDragState(null);
                    this.mAttachInfo.mDragToken = null;
                    if (this.mAttachInfo.mDragSurface != null) {
                        this.mAttachInfo.mDragSurface.release();
                        this.mAttachInfo.mDragSurface = null;
                    }
                    if (this.mAttachInfo.mDragData != null) {
                        android.view.View.cleanUpPendingIntents(this.mAttachInfo.mDragData);
                        this.mAttachInfo.mDragData = null;
                    }
                }
            }
        }
        dragEvent.recycle();
    }

    public void onWindowTitleChanged() {
        this.mAttachInfo.mForceReportNewAttributes = true;
    }

    public void handleDispatchWindowShown() {
        this.mAttachInfo.mTreeObserver.dispatchOnWindowShown();
    }

    public void handleRequestKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) {
        android.os.Bundle bundle = new android.os.Bundle();
        java.util.ArrayList<? extends android.os.Parcelable> arrayList = new java.util.ArrayList<>();
        if (this.mView != null) {
            this.mView.requestKeyboardShortcuts(arrayList, i);
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((android.view.KeyboardShortcutGroup) arrayList.get(i2)).setPackageName(this.mBasePackageName);
        }
        bundle.putParcelableArrayList(android.view.WindowManager.PARCEL_KEY_SHORTCUTS_ARRAY, arrayList);
        try {
            iResultReceiver.send(0, bundle);
        } catch (android.os.RemoteException e) {
        }
    }

    public void getLastTouchPoint(android.graphics.Point point) {
        point.x = (int) this.mLastTouchPoint.x;
        point.y = (int) this.mLastTouchPoint.y;
    }

    public int getLastTouchSource() {
        return this.mLastTouchSource;
    }

    public int getLastTouchDeviceId() {
        return this.mLastTouchDeviceId;
    }

    public int getLastTouchPointerId() {
        return this.mLastTouchPointerId;
    }

    public int getLastClickToolType() {
        return this.mLastClickToolType;
    }

    public void setDragFocus(android.view.View view, android.view.DragEvent dragEvent) {
        if (this.mCurrentDragView != view && !android.view.View.sCascadedDragDrop) {
            float f = dragEvent.mX;
            float f2 = dragEvent.mY;
            int i = dragEvent.mAction;
            android.content.ClipData clipData = dragEvent.mClipData;
            dragEvent.mX = 0.0f;
            dragEvent.mY = 0.0f;
            dragEvent.mClipData = null;
            if (this.mCurrentDragView != null) {
                dragEvent.mAction = 6;
                this.mCurrentDragView.callDragEventHandler(dragEvent);
            }
            if (view != null) {
                dragEvent.mAction = 5;
                view.callDragEventHandler(dragEvent);
            }
            dragEvent.mAction = i;
            dragEvent.mX = f;
            dragEvent.mY = f2;
            dragEvent.mClipData = clipData;
        }
        this.mCurrentDragView = view;
    }

    void setDragStartedViewForAccessibility(android.view.View view) {
        if (this.mStartedDragViewForA11y == null) {
            this.mStartedDragViewForA11y = view;
        }
    }

    private android.media.AudioManager getAudioManager() {
        if (this.mView == null) {
            throw new java.lang.IllegalStateException("getAudioManager called when there is no mView");
        }
        if (this.mAudioManager == null) {
            this.mAudioManager = (android.media.AudioManager) this.mView.getContext().getSystemService("audio");
            this.mFastScrollSoundEffectsEnabled = this.mAudioManager.areNavigationRepeatSoundEffectsEnabled();
        }
        return this.mAudioManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.autofill.AutofillManager getAutofillManager() {
        if (this.mView instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.mView;
            if (viewGroup.getChildCount() > 0) {
                return (android.view.autofill.AutofillManager) viewGroup.getChildAt(0).getContext().getSystemService(android.view.autofill.AutofillManager.class);
            }
            return null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAutofillUiShowing() {
        android.view.autofill.AutofillManager autofillManager = getAutofillManager();
        if (autofillManager == null) {
            return false;
        }
        return autofillManager.isAutofillUiShowing();
    }

    public android.view.AccessibilityInteractionController getAccessibilityInteractionController() {
        if (this.mView == null) {
            throw new java.lang.IllegalStateException("getAccessibilityInteractionController called when there is no mView");
        }
        if (this.mAccessibilityInteractionController == null) {
            this.mAccessibilityInteractionController = new android.view.AccessibilityInteractionController(this);
        }
        return this.mAccessibilityInteractionController;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x02a3  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x02c4  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0301  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0138  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int relayoutWindow(android.view.WindowManager.LayoutParams layoutParams, int i, boolean z) throws android.os.RemoteException {
        int i2;
        boolean z2;
        boolean z3;
        int i3;
        int i4;
        int relayout;
        boolean z4;
        int i5;
        android.app.WindowConfiguration windowConfiguration = getConfiguration().windowConfiguration;
        android.app.WindowConfiguration windowConfiguration2 = this.mLastReportedMergedConfiguration.getGlobalConfiguration().windowConfiguration;
        android.app.WindowConfiguration compatWindowConfiguration = getCompatWindowConfiguration();
        int i6 = this.mMeasuredWidth;
        int i7 = this.mMeasuredHeight;
        if ((this.mViewFrameInfo.flags & 1) != 0 || this.mWindowAttributes.type == 3 || this.mSyncSeqId > this.mLastSyncSeqId) {
            i2 = i7;
        } else if (windowConfiguration.diff(windowConfiguration2, false) != 0) {
            i2 = i7;
        } else {
            android.view.InsetsState state = this.mInsetsController.getState();
            android.graphics.Rect rect = this.mTempRect;
            state.getDisplayCutoutSafe(rect);
            i2 = i7;
            this.mWindowLayout.computeFrames(this.mWindowAttributes.forRotation(compatWindowConfiguration.getRotation()), state, rect, compatWindowConfiguration.getBounds(), compatWindowConfiguration.getWindowingMode(), i6, i7, this.mInsetsController.getRequestedVisibleTypes(), 1.0f, this.mTmpFrames);
            this.mWinFrameInScreen.set(this.mTmpFrames.frame);
            if (this.mTranslator != null) {
                this.mTranslator.translateRectInAppWindowToScreen(this.mWinFrameInScreen);
            }
            android.graphics.Rect rect2 = this.mLastLayoutFrame;
            android.graphics.Rect rect3 = this.mTmpFrames.frame;
            z2 = ((rect3.top != rect2.top || rect3.left != rect2.left) && (rect3.width() != rect2.width() || rect3.height() != rect2.height())) ? false : true;
            float f = this.mAttachInfo.mApplicationScale;
            if (layoutParams == null && this.mTranslator != null) {
                layoutParams.backup();
                this.mTranslator.translateWindowLayout(layoutParams);
                z3 = true;
            } else {
                z3 = false;
            }
            if (layoutParams != null && this.mOrigWindowType != layoutParams.type && this.mTargetSdkVersion < 14) {
                android.util.Slog.w(this.mTag, "Window type can not be changed after the window is added; ignoring change of " + this.mView);
                layoutParams.type = this.mOrigWindowType;
            }
            int i8 = (int) ((i6 * f) + 0.5f);
            int i9 = (int) ((i2 * f) + 0.5f);
            this.mRelayoutSeq++;
            if (!z2) {
                this.mWindowSession.relayoutAsync(this.mWindow, layoutParams, i8, i9, i, z ? 1 : 0, this.mRelayoutSeq, this.mLastSyncSeqId);
                z4 = true;
                i3 = i9;
                i4 = i8;
                relayout = 0;
            } else {
                i3 = i9;
                i4 = i8;
                relayout = this.mWindowSession.relayout(this.mWindow, layoutParams, i8, i9, i, z ? 1 : 0, this.mRelayoutSeq, this.mLastSyncSeqId, this.mTmpFrames, this.mPendingMergedConfiguration, this.mSurfaceControl, this.mTempInsets, this.mTempControls, this.mRelayoutBundle);
                z4 = true;
                this.mRelayoutRequested = true;
                int i10 = this.mRelayoutBundle.getInt("seqid");
                if (i10 > 0) {
                    this.mSyncSeqId = i10;
                }
                this.mWinFrameInScreen.set(this.mTmpFrames.frame);
                if (this.mTranslator != null) {
                    this.mTranslator.translateRectInScreenToAppWindow(this.mTmpFrames.frame);
                    this.mTranslator.translateRectInScreenToAppWindow(this.mTmpFrames.displayFrame);
                    this.mTranslator.translateRectInScreenToAppWindow(this.mTmpFrames.attachedFrame);
                    this.mTranslator.translateInsetsStateInScreenToAppWindow(this.mTempInsets);
                    this.mTranslator.translateSourceControlsInScreenToAppWindow(this.mTempControls.get());
                }
                this.mInvCompatScale = 1.0f / this.mTmpFrames.compatScale;
                android.content.res.CompatibilityInfo.applyOverrideScaleIfNeeded(this.mPendingMergedConfiguration);
                this.mInsetsController.onStateChanged(this.mTempInsets);
                this.mInsetsController.onControlsChanged(this.mTempControls.get());
                this.mPendingAlwaysConsumeSystemBars = (relayout & 8) != 0;
            }
            int rotationToBufferTransform = android.view.SurfaceControl.rotationToBufferTransform((this.mDisplay.getInstallOrientation() + this.mDisplay.getRotation()) % 4);
            boolean z5 = rotationToBufferTransform == this.mPreviousTransformHint ? z4 : false;
            this.mPreviousTransformHint = rotationToBufferTransform;
            this.mSurfaceControl.setTransformHint(rotationToBufferTransform);
            android.view.WindowLayout.computeSurfaceSize(this.mWindowAttributes, compatWindowConfiguration.getMaxBounds(), i4, i3, this.mWinFrameInScreen, this.mPendingDragResizing, this.mSurfaceSize);
            boolean equals = this.mLastSurfaceSize.equals(this.mSurfaceSize) ^ z4;
            boolean z6 = (relayout & 2) != 2 ? z4 : false;
            if (this.mAttachInfo.mThreadedRenderer != null) {
                i5 = 0;
            } else if (!z5 && !equals && !z6) {
                i5 = 0;
            } else if (this.mAttachInfo.mThreadedRenderer.pause()) {
                i5 = 0;
                this.mDirty.set(0, 0, this.mWidth, this.mHeight);
            } else {
                i5 = 0;
            }
            if (this.mSurfaceControl.isValid()) {
                if (this.mPendingDragResizing && !this.mSurfaceSize.equals(this.mWinFrameInScreen.width(), this.mWinFrameInScreen.height())) {
                    this.mTransaction.setWindowCrop(this.mSurfaceControl, this.mWinFrameInScreen.width(), this.mWinFrameInScreen.height());
                } else if (!android.graphics.HardwareRenderer.isDrawingEnabled()) {
                    this.mTransaction.setWindowCrop(this.mSurfaceControl, this.mSurfaceSize.x, this.mSurfaceSize.y).apply();
                }
            }
            if (this.mAttachInfo.mContentCaptureManager != null) {
                android.view.contentcapture.ContentCaptureSession mainContentCaptureSession = this.mAttachInfo.mContentCaptureManager.getMainContentCaptureSession();
                mainContentCaptureSession.notifyWindowBoundsChanged(mainContentCaptureSession.getId(), getConfiguration().windowConfiguration.getBounds());
            }
            if (!this.mSurfaceControl.isValid()) {
                updateBlastSurfaceIfNeeded();
                if (this.mAttachInfo.mThreadedRenderer != null) {
                    this.mAttachInfo.mThreadedRenderer.setSurfaceControl(this.mSurfaceControl, this.mBlastBufferQueue);
                }
                this.mHdrRenderState.forceUpdateHdrSdrRatio();
                if (z5) {
                    dispatchTransformHintChanged(rotationToBufferTransform);
                }
            } else {
                if (this.mAttachInfo.mThreadedRenderer != null && this.mAttachInfo.mThreadedRenderer.pause()) {
                    this.mDirty.set(i5, i5, this.mWidth, this.mHeight);
                }
                destroySurface();
            }
            if (z3) {
                layoutParams.restore();
            }
            setFrame(this.mTmpFrames.frame, z4);
            return relayout;
        }
        z2 = false;
        float f2 = this.mAttachInfo.mApplicationScale;
        if (layoutParams == null) {
        }
        z3 = false;
        if (layoutParams != null) {
            android.util.Slog.w(this.mTag, "Window type can not be changed after the window is added; ignoring change of " + this.mView);
            layoutParams.type = this.mOrigWindowType;
        }
        int i82 = (int) ((i6 * f2) + 0.5f);
        int i92 = (int) ((i2 * f2) + 0.5f);
        this.mRelayoutSeq++;
        if (!z2) {
        }
        int rotationToBufferTransform2 = android.view.SurfaceControl.rotationToBufferTransform((this.mDisplay.getInstallOrientation() + this.mDisplay.getRotation()) % 4);
        if (rotationToBufferTransform2 == this.mPreviousTransformHint) {
        }
        this.mPreviousTransformHint = rotationToBufferTransform2;
        this.mSurfaceControl.setTransformHint(rotationToBufferTransform2);
        android.view.WindowLayout.computeSurfaceSize(this.mWindowAttributes, compatWindowConfiguration.getMaxBounds(), i4, i3, this.mWinFrameInScreen, this.mPendingDragResizing, this.mSurfaceSize);
        boolean equals2 = this.mLastSurfaceSize.equals(this.mSurfaceSize) ^ z4;
        if ((relayout & 2) != 2) {
        }
        if (this.mAttachInfo.mThreadedRenderer != null) {
        }
        if (this.mSurfaceControl.isValid()) {
        }
        if (this.mAttachInfo.mContentCaptureManager != null) {
        }
        if (!this.mSurfaceControl.isValid()) {
        }
        if (z3) {
        }
        setFrame(this.mTmpFrames.frame, z4);
        return relayout;
    }

    private void updateOpacity(android.view.WindowManager.LayoutParams layoutParams, boolean z, boolean z2) {
        boolean z3;
        if (!android.graphics.PixelFormat.formatHasAlpha(layoutParams.format) && layoutParams.surfaceInsets.left == 0 && layoutParams.surfaceInsets.top == 0 && layoutParams.surfaceInsets.right == 0 && layoutParams.surfaceInsets.bottom == 0 && !z) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (!z2 && this.mIsSurfaceOpaque == z3) {
            return;
        }
        android.view.ThreadedRenderer threadedRenderer = this.mAttachInfo.mThreadedRenderer;
        if (threadedRenderer != null && threadedRenderer.rendererOwnsSurfaceControlOpacity()) {
            z3 = threadedRenderer.setSurfaceControlOpaque(z3);
        } else {
            this.mTransaction.setOpaque(this.mSurfaceControl, z3).apply();
        }
        this.mIsSurfaceOpaque = z3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFrame(android.graphics.Rect rect, boolean z) {
        android.graphics.Rect rect2;
        this.mWinFrame.set(rect);
        if (z) {
            this.mLastLayoutFrame.set(rect);
        }
        android.app.WindowConfiguration compatWindowConfiguration = getCompatWindowConfiguration();
        android.graphics.Rect rect3 = this.mPendingBackDropFrame;
        if (this.mPendingDragResizing && !compatWindowConfiguration.useWindowFrameForBackdrop()) {
            rect2 = compatWindowConfiguration.getMaxBounds();
        } else {
            rect2 = rect;
        }
        rect3.set(rect2);
        this.mPendingBackDropFrame.offsetTo(0, 0);
        android.view.InsetsController insetsController = this.mInsetsController;
        if (this.mOverrideInsetsFrame != null) {
            rect = this.mOverrideInsetsFrame;
        }
        insetsController.onFrameChanged(rect);
    }

    void setOverrideInsetsFrame(android.graphics.Rect rect) {
        this.mOverrideInsetsFrame = new android.graphics.Rect(rect);
        this.mInsetsController.onFrameChanged(this.mOverrideInsetsFrame);
    }

    void getDisplayFrame(android.graphics.Rect rect) {
        rect.set(this.mTmpFrames.displayFrame);
        applyViewBoundsSandboxingIfNeeded(rect);
    }

    void getWindowVisibleDisplayFrame(android.graphics.Rect rect) {
        rect.set(this.mTmpFrames.displayFrame);
        android.graphics.Rect rect2 = this.mAttachInfo.mVisibleInsets;
        rect.left += rect2.left;
        rect.top += rect2.top;
        rect.right -= rect2.right;
        rect.bottom -= rect2.bottom;
        applyViewBoundsSandboxingIfNeeded(rect);
    }

    void applyViewBoundsSandboxingIfNeeded(android.graphics.Rect rect) {
        if (this.mViewBoundsSandboxingEnabled) {
            android.graphics.Rect bounds = getConfiguration().windowConfiguration.getBounds();
            rect.offset(-bounds.left, -bounds.top);
        }
    }

    public void applyViewLocationSandboxingIfNeeded(int[] iArr) {
        if (this.mViewBoundsSandboxingEnabled) {
            android.graphics.Rect bounds = getConfiguration().windowConfiguration.getBounds();
            iArr[0] = iArr[0] - bounds.left;
            iArr[1] = iArr[1] - bounds.top;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0035 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0038 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean getViewBoundsSandboxingEnabled() {
        java.util.List<android.content.pm.PackageManager.Property> queryApplicationProperty;
        boolean z;
        if (android.app.ActivityThread.isSystem() || !android.app.compat.CompatChanges.isChangeEnabled(android.content.pm.ActivityInfo.OVERRIDE_SANDBOX_VIEW_BOUNDS_APIS)) {
            return false;
        }
        try {
            queryApplicationProperty = this.mContext.getPackageManager().queryApplicationProperty(android.view.WindowManager.PROPERTY_COMPAT_ALLOW_SANDBOXING_VIEW_BOUNDS_APIS);
        } catch (java.lang.RuntimeException e) {
        }
        if (!queryApplicationProperty.isEmpty()) {
            if (!queryApplicationProperty.get(0).getBoolean()) {
                z = true;
                return z;
            }
        }
        z = false;
        if (z) {
        }
    }

    @Override // android.view.View.AttachInfo.Callbacks
    public void playSoundEffect(int i) {
        if ((this.mDisplay.getFlags() & 1024) != 0) {
            return;
        }
        checkThread();
        try {
            android.media.AudioManager audioManager = getAudioManager();
            if (this.mFastScrollSoundEffectsEnabled && android.view.SoundEffectConstants.isNavigationRepeat(i)) {
                audioManager.playSoundEffect(android.view.SoundEffectConstants.nextNavigationRepeatSoundEffectId());
                return;
            }
            switch (i) {
                case 0:
                    audioManager.playSoundEffect(0);
                    return;
                case 1:
                case 5:
                    audioManager.playSoundEffect(3);
                    return;
                case 2:
                case 6:
                    audioManager.playSoundEffect(1);
                    return;
                case 3:
                case 7:
                    audioManager.playSoundEffect(4);
                    return;
                case 4:
                case 8:
                    audioManager.playSoundEffect(2);
                    return;
                default:
                    throw new java.lang.IllegalArgumentException("unknown effect id " + i + " not defined in " + android.view.SoundEffectConstants.class.getCanonicalName());
            }
        } catch (java.lang.IllegalStateException e) {
            android.util.Log.e(this.mTag, "FATAL EXCEPTION when attempting to play sound effect: " + e);
            e.printStackTrace();
        }
    }

    @Override // android.view.View.AttachInfo.Callbacks
    public boolean performHapticFeedback(int i, boolean z, boolean z2) {
        if ((this.mDisplay.getFlags() & 1024) != 0) {
            return false;
        }
        try {
            this.mWindowSession.performHapticFeedbackAsync(i, z, z2);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @Override // android.view.ViewParent
    public android.view.View focusSearch(android.view.View view, int i) {
        checkThread();
        if (!(this.mView instanceof android.view.ViewGroup)) {
            return null;
        }
        return android.view.FocusFinder.getInstance().findNextFocus((android.view.ViewGroup) this.mView, view, i);
    }

    @Override // android.view.ViewParent
    public android.view.View keyboardNavigationClusterSearch(android.view.View view, int i) {
        checkThread();
        return android.view.FocusFinder.getInstance().findNextKeyboardNavigationCluster(this.mView, view, i);
    }

    public void debug() {
        this.mView.debug();
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, java.util.Objects.toString(this.mView));
        protoOutputStream.write(1120986464258L, this.mDisplay.getDisplayId());
        protoOutputStream.write(1133871366147L, this.mAppVisible);
        protoOutputStream.write(1120986464261L, this.mHeight);
        protoOutputStream.write(1120986464260L, this.mWidth);
        protoOutputStream.write(1133871366150L, this.mIsAnimating);
        this.mVisRect.dumpDebug(protoOutputStream, 1146756268039L);
        protoOutputStream.write(1133871366152L, this.mIsDrawing);
        protoOutputStream.write(1133871366153L, this.mAdded);
        this.mWinFrame.dumpDebug(protoOutputStream, 1146756268042L);
        protoOutputStream.write(1138166333452L, java.util.Objects.toString(this.mLastWindowInsets));
        protoOutputStream.write(1138166333453L, com.android.internal.inputmethod.InputMethodDebug.softInputModeToString(this.mSoftInputMode));
        protoOutputStream.write(1120986464270L, this.mScrollY);
        protoOutputStream.write(1120986464271L, this.mCurScrollY);
        protoOutputStream.write(1133871366160L, this.mRemoved);
        this.mWindowAttributes.dumpDebug(protoOutputStream, 1146756268049L);
        protoOutputStream.end(start);
        this.mInsetsController.dumpDebug(protoOutputStream, 1146756268036L);
        this.mImeFocusController.dumpDebug(protoOutputStream, 1146756268039L);
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        java.lang.String str2 = str + "  ";
        printWriter.println(str + "ViewRoot:");
        printWriter.println(str2 + "mAdded=" + this.mAdded);
        printWriter.println(str2 + "mRemoved=" + this.mRemoved);
        printWriter.println(str2 + "mStopped=" + this.mStopped);
        printWriter.println(str2 + "mPausedForTransition=" + this.mPausedForTransition);
        printWriter.println(str2 + "mConsumeBatchedInputScheduled=" + this.mConsumeBatchedInputScheduled);
        printWriter.println(str2 + "mConsumeBatchedInputImmediatelyScheduled=" + this.mConsumeBatchedInputImmediatelyScheduled);
        printWriter.println(str2 + "mPendingInputEventCount=" + this.mPendingInputEventCount);
        printWriter.println(str2 + "mProcessInputEventsScheduled=" + this.mProcessInputEventsScheduled);
        printWriter.println(str2 + "mTraversalScheduled=" + this.mTraversalScheduled);
        if (this.mTraversalScheduled) {
            printWriter.println(str2 + " (barrier=" + this.mTraversalBarrier + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        printWriter.println(str2 + "mReportNextDraw=" + this.mReportNextDraw);
        if (this.mReportNextDraw) {
            printWriter.println(str2 + " (reason=" + this.mLastReportNextDrawReason + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        if (this.mLastPerformTraversalsSkipDrawReason != null) {
            printWriter.println(str2 + "mLastPerformTraversalsFailedReason=" + this.mLastPerformTraversalsSkipDrawReason);
        }
        if (this.mLastPerformDrawSkippedReason != null) {
            printWriter.println(str2 + "mLastPerformDrawFailedReason=" + this.mLastPerformDrawSkippedReason);
        }
        if (this.mWmsRequestSyncGroupState != 0) {
            printWriter.println(str2 + "mWmsRequestSyncGroupState=" + this.mWmsRequestSyncGroupState);
        }
        printWriter.println(str2 + "mLastReportedMergedConfiguration=" + this.mLastReportedMergedConfiguration);
        printWriter.println(str2 + "mLastConfigurationFromResources=" + this.mLastConfigurationFromResources);
        printWriter.println(str2 + "mIsAmbientMode=" + this.mIsAmbientMode);
        printWriter.println(str2 + "mUnbufferedInputSource=" + java.lang.Integer.toHexString(this.mUnbufferedInputSource));
        if (this.mAttachInfo != null) {
            printWriter.print(str2 + "mAttachInfo= ");
            this.mAttachInfo.dump(str2, printWriter);
        } else {
            printWriter.println(str2 + "mAttachInfo=<null>");
        }
        this.mFirstInputStage.dump(str2, printWriter);
        if (this.mInputEventReceiver != null) {
            this.mInputEventReceiver.dump(str2, printWriter);
        }
        this.mChoreographer.dump(str, printWriter);
        this.mInsetsController.dump(str, printWriter);
        this.mOnBackInvokedDispatcher.dump(str, printWriter);
        printWriter.println(str + "View Hierarchy:");
        dumpViewHierarchy(str2, printWriter, this.mView);
    }

    private void dumpViewHierarchy(java.lang.String str, java.io.PrintWriter printWriter, android.view.View view) {
        android.view.ViewGroup viewGroup;
        int childCount;
        printWriter.print(str);
        if (view == null) {
            printWriter.println("null");
            return;
        }
        printWriter.println(view.toString());
        if (!(view instanceof android.view.ViewGroup) || (childCount = (viewGroup = (android.view.ViewGroup) view).getChildCount()) <= 0) {
            return;
        }
        java.lang.String str2 = str + "  ";
        for (int i = 0; i < childCount; i++) {
            dumpViewHierarchy(str2, printWriter, viewGroup.getChildAt(i));
        }
    }

    static final class GfxInfo {
        public long renderNodeMemoryAllocated;
        public long renderNodeMemoryUsage;
        public int viewCount;

        GfxInfo() {
        }

        void add(android.view.ViewRootImpl.GfxInfo gfxInfo) {
            this.viewCount += gfxInfo.viewCount;
            this.renderNodeMemoryUsage += gfxInfo.renderNodeMemoryUsage;
            this.renderNodeMemoryAllocated += gfxInfo.renderNodeMemoryAllocated;
        }
    }

    android.view.ViewRootImpl.GfxInfo getGfxInfo() {
        android.view.ViewRootImpl.GfxInfo gfxInfo = new android.view.ViewRootImpl.GfxInfo();
        if (this.mView != null) {
            appendGfxInfo(this.mView, gfxInfo);
        }
        return gfxInfo;
    }

    private static void computeRenderNodeUsage(android.graphics.RenderNode renderNode, android.view.ViewRootImpl.GfxInfo gfxInfo) {
        if (renderNode == null) {
            return;
        }
        gfxInfo.renderNodeMemoryUsage += renderNode.computeApproximateMemoryUsage();
        gfxInfo.renderNodeMemoryAllocated += renderNode.computeApproximateMemoryAllocated();
    }

    private static void appendGfxInfo(android.view.View view, android.view.ViewRootImpl.GfxInfo gfxInfo) {
        gfxInfo.viewCount++;
        computeRenderNodeUsage(view.mRenderNode, gfxInfo);
        computeRenderNodeUsage(view.mBackgroundRenderNode, gfxInfo);
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                appendGfxInfo(viewGroup.getChildAt(i), gfxInfo);
            }
        }
    }

    boolean die(boolean z) {
        if (z && !this.mIsInTraversal) {
            doDie();
            return false;
        }
        if (!this.mIsDrawing) {
            destroyHardwareRenderer();
        } else {
            android.util.Log.e(this.mTag, "Attempting to destroy the window while drawing!\n  window=" + this + ", title=" + ((java.lang.Object) this.mWindowAttributes.getTitle()));
        }
        this.mHandler.sendEmptyMessage(3);
        return true;
    }

    void doDie() {
        checkThread();
        synchronized (this) {
            if (this.mRemoved) {
                return;
            }
            this.mRemoved = true;
            this.mOnBackInvokedDispatcher.detachFromWindow();
            if (this.mAdded) {
                dispatchDetachedFromWindow();
            }
            if (this.mAdded && !this.mFirst) {
                destroyHardwareRenderer();
                if (this.mView != null) {
                    int visibility = this.mView.getVisibility();
                    boolean z = this.mViewVisibility != visibility;
                    if (this.mWindowAttributesChanged || z) {
                        try {
                            if ((1 & relayoutWindow(this.mWindowAttributes, visibility, false)) != 0) {
                                this.mWindowSession.finishDrawing(this.mWindow, null, Integer.MAX_VALUE);
                            }
                        } catch (android.os.RemoteException e) {
                        }
                    }
                    destroySurface();
                }
            }
            this.mInsetsController.onControlsChanged(null);
            this.mAdded = false;
            android.animation.AnimationHandler.removeRequestor(this);
            handleSyncRequestWhenNoAsyncDraw(this.mActiveSurfaceSyncGroup, this.mHasPendingTransactions, this.mPendingTransaction, "shutting down VRI");
            android.view.WindowManagerGlobal.getInstance().doRemoveView(this);
        }
    }

    public void requestUpdateConfiguration(android.content.res.Configuration configuration) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(18, configuration));
    }

    public void loadSystemProperties() {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.ViewRootImpl.8
            @Override // java.lang.Runnable
            public void run() {
                android.view.ViewRootImpl.this.mProfileRendering = android.os.SystemProperties.getBoolean(android.view.ViewRootImpl.PROPERTY_PROFILE_RENDERING, false);
                android.view.ViewRootImpl.this.profileRendering(android.view.ViewRootImpl.this.mAttachInfo.mHasWindowFocus);
                if (android.view.ViewRootImpl.this.mAttachInfo.mThreadedRenderer != null && android.view.ViewRootImpl.this.mAttachInfo.mThreadedRenderer.loadSystemProperties()) {
                    android.view.ViewRootImpl.this.invalidate();
                }
                boolean booleanValue = android.sysprop.DisplayProperties.debug_layout().orElse(false).booleanValue();
                if (booleanValue != android.view.ViewRootImpl.this.mAttachInfo.mDebugLayout) {
                    android.view.ViewRootImpl.this.mAttachInfo.mDebugLayout = booleanValue;
                    if (!android.view.ViewRootImpl.this.mHandler.hasMessages(22)) {
                        android.view.ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(22, 200L);
                    }
                }
            }
        });
    }

    private void destroyHardwareRenderer() {
        android.view.ThreadedRenderer threadedRenderer = this.mAttachInfo.mThreadedRenderer;
        this.mHdrRenderState.stopListening();
        if (threadedRenderer != null) {
            if (this.mHardwareRendererObserver != null) {
                threadedRenderer.removeObserver(this.mHardwareRendererObserver);
            }
            if (this.mView != null) {
                threadedRenderer.destroyHardwareResources(this.mView);
            }
            threadedRenderer.destroy();
            threadedRenderer.setRequested(false);
            this.mAttachInfo.mThreadedRenderer = null;
            this.mAttachInfo.mHardwareAccelerated = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchResized(android.window.ClientWindowFrames clientWindowFrames, boolean z, android.util.MergedConfiguration mergedConfiguration, android.view.InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(z ? 5 : 4);
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = clientWindowFrames;
        obtain.arg2 = mergedConfiguration;
        obtain.arg3 = insetsState;
        obtain.argi1 = z2 ? 1 : 0;
        obtain.argi2 = z3 ? 1 : 0;
        obtain.argi3 = i;
        obtain.argi4 = i2;
        obtain.argi5 = z4 ? 1 : 0;
        obtainMessage.obj = obtain;
        this.mHandler.sendMessage(obtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchInsetsControlChanged(android.view.InsetsState insetsState, android.view.InsetsSourceControl[] insetsSourceControlArr) {
        if (android.os.Binder.getCallingPid() == android.os.Process.myPid()) {
            android.view.InsetsState insetsState2 = new android.view.InsetsState(insetsState, true);
            if (insetsSourceControlArr != null) {
                for (int length = insetsSourceControlArr.length - 1; length >= 0; length--) {
                    insetsSourceControlArr[length] = new android.view.InsetsSourceControl(insetsSourceControlArr[length]);
                }
            }
            insetsState = insetsState2;
        }
        if (this.mTranslator != null) {
            this.mTranslator.translateInsetsStateInScreenToAppWindow(insetsState);
            this.mTranslator.translateSourceControlsInScreenToAppWindow(insetsSourceControlArr);
        }
        if (insetsState != null && insetsState.isSourceOrDefaultVisible(android.view.InsetsSource.ID_IME, android.view.WindowInsets.Type.ime())) {
            com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("ViewRootImpl#dispatchInsetsControlChanged", getInsetsController().getHost().getInputMethodManager(), null);
        }
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = insetsState;
        obtain.arg2 = insetsSourceControlArr;
        this.mHandler.obtainMessage(29, obtain).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) {
        this.mHandler.obtainMessage(31, i, z ? 1 : 0, token).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) {
        this.mHandler.obtainMessage(32, i, z ? 1 : 0, token).sendToTarget();
    }

    public void dispatchMoved(int i, int i2) {
        if (this.mTranslator != null) {
            this.mTranslator.translatePointInScreenToAppWindow(new android.graphics.PointF(i, i2));
            i = (int) (r0.x + 0.5d);
            i2 = (int) (r0.y + 0.5d);
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(23, i, i2));
    }

    private static final class QueuedInputEvent {
        public static final int FLAG_DEFERRED = 2;
        public static final int FLAG_DELIVER_POST_IME = 1;
        public static final int FLAG_FINISHED = 4;
        public static final int FLAG_FINISHED_HANDLED = 8;
        public static final int FLAG_MODIFIED_FOR_COMPATIBILITY = 64;
        public static final int FLAG_RESYNTHESIZED = 16;
        public static final int FLAG_UNHANDLED = 32;
        public android.view.InputEvent mEvent;
        public int mFlags;
        public android.view.ViewRootImpl.QueuedInputEvent mNext;
        public android.view.InputEventReceiver mReceiver;

        private QueuedInputEvent() {
        }

        public boolean shouldSkipIme() {
            if ((this.mFlags & 1) != 0) {
                return true;
            }
            return (this.mEvent instanceof android.view.MotionEvent) && this.mEvent.isFromSource(2);
        }

        public boolean shouldSendToSynthesizer() {
            if ((this.mFlags & 32) != 0) {
                return true;
            }
            return false;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("QueuedInputEvent{flags=");
            if (!flagToString("UNHANDLED", 32, flagToString("RESYNTHESIZED", 16, flagToString("FINISHED_HANDLED", 8, flagToString("FINISHED", 4, flagToString("DEFERRED", 2, flagToString("DELIVER_POST_IME", 1, false, sb), sb), sb), sb), sb), sb)) {
                sb.append(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
            }
            sb.append(", hasNextQueuedEvent=" + (this.mEvent != null ? "true" : "false"));
            sb.append(", hasInputEventReceiver=" + (this.mReceiver == null ? "false" : "true"));
            sb.append(", mEvent=" + this.mEvent + "}");
            return sb.toString();
        }

        private boolean flagToString(java.lang.String str, int i, boolean z, java.lang.StringBuilder sb) {
            if ((i & this.mFlags) != 0) {
                if (z) {
                    sb.append(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                }
                sb.append(str);
                return true;
            }
            return z;
        }
    }

    private android.view.ViewRootImpl.QueuedInputEvent obtainQueuedInputEvent(android.view.InputEvent inputEvent, android.view.InputEventReceiver inputEventReceiver, int i) {
        android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent = this.mQueuedInputEventPool;
        if (queuedInputEvent != null) {
            this.mQueuedInputEventPoolSize--;
            this.mQueuedInputEventPool = queuedInputEvent.mNext;
            queuedInputEvent.mNext = null;
        } else {
            queuedInputEvent = new android.view.ViewRootImpl.QueuedInputEvent();
        }
        queuedInputEvent.mEvent = inputEvent;
        queuedInputEvent.mReceiver = inputEventReceiver;
        queuedInputEvent.mFlags = i;
        return queuedInputEvent;
    }

    private void recycleQueuedInputEvent(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
        queuedInputEvent.mEvent = null;
        queuedInputEvent.mReceiver = null;
        if (this.mQueuedInputEventPoolSize < 10) {
            this.mQueuedInputEventPoolSize++;
            queuedInputEvent.mNext = this.mQueuedInputEventPool;
            this.mQueuedInputEventPool = queuedInputEvent;
        }
    }

    public void enqueueInputEvent(android.view.InputEvent inputEvent) {
        enqueueInputEvent(inputEvent, null, 0, false);
    }

    void enqueueInputEvent(android.view.InputEvent inputEvent, android.view.InputEventReceiver inputEventReceiver, int i, boolean z) {
        android.view.ViewRootImpl.QueuedInputEvent obtainQueuedInputEvent = obtainQueuedInputEvent(inputEvent, inputEventReceiver, i);
        if (inputEvent instanceof android.view.MotionEvent) {
            if (((android.view.MotionEvent) inputEvent).getAction() == 3) {
                android.util.EventLog.writeEvent(android.view.EventLogTags.VIEW_ENQUEUE_INPUT_EVENT, "Motion - Cancel", getTitle().toString());
            }
        } else if ((inputEvent instanceof android.view.KeyEvent) && ((android.view.KeyEvent) inputEvent).isCanceled()) {
            android.util.EventLog.writeEvent(android.view.EventLogTags.VIEW_ENQUEUE_INPUT_EVENT, "Key - Cancel", getTitle().toString());
        }
        android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent = this.mPendingInputEventTail;
        if (queuedInputEvent == null) {
            this.mPendingInputEventHead = obtainQueuedInputEvent;
            this.mPendingInputEventTail = obtainQueuedInputEvent;
        } else {
            queuedInputEvent.mNext = obtainQueuedInputEvent;
            this.mPendingInputEventTail = obtainQueuedInputEvent;
        }
        this.mPendingInputEventCount++;
        android.os.Trace.traceCounter(4L, this.mPendingInputEventQueueLengthCounterName, this.mPendingInputEventCount);
        if (z) {
            doProcessInputEvents();
        } else {
            scheduleProcessInputEvents();
        }
    }

    private void scheduleProcessInputEvents() {
        if (!this.mProcessInputEventsScheduled) {
            this.mProcessInputEventsScheduled = true;
            android.os.Message obtainMessage = this.mHandler.obtainMessage(19);
            obtainMessage.setAsynchronous(true);
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    void doProcessInputEvents() {
        while (this.mPendingInputEventHead != null) {
            android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent = this.mPendingInputEventHead;
            this.mPendingInputEventHead = queuedInputEvent.mNext;
            if (this.mPendingInputEventHead == null) {
                this.mPendingInputEventTail = null;
            }
            queuedInputEvent.mNext = null;
            this.mPendingInputEventCount--;
            android.os.Trace.traceCounter(4L, this.mPendingInputEventQueueLengthCounterName, this.mPendingInputEventCount);
            this.mViewFrameInfo.setInputEvent(this.mInputEventAssigner.processEvent(queuedInputEvent.mEvent));
            deliverInputEvent(queuedInputEvent);
        }
        if (this.mProcessInputEventsScheduled) {
            this.mProcessInputEventsScheduled = false;
            this.mHandler.removeMessages(19);
        }
    }

    private void deliverInputEvent(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
        android.view.ViewRootImpl.InputStage inputStage;
        android.os.Trace.asyncTraceBegin(8L, "deliverInputEvent", queuedInputEvent.mEvent.getId());
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.traceBegin(8L, "deliverInputEvent src=0x" + java.lang.Integer.toHexString(queuedInputEvent.mEvent.getSource()) + " eventTimeNano=" + queuedInputEvent.mEvent.getEventTimeNanos() + " id=0x" + java.lang.Integer.toHexString(queuedInputEvent.mEvent.getId()));
        }
        try {
            if (this.mInputEventConsistencyVerifier != null) {
                android.os.Trace.traceBegin(8L, "verifyEventConsistency");
                this.mInputEventConsistencyVerifier.onInputEvent(queuedInputEvent.mEvent, 0);
                android.os.Trace.traceEnd(8L);
            }
            if (queuedInputEvent.shouldSendToSynthesizer()) {
                inputStage = this.mSyntheticInputStage;
            } else {
                inputStage = queuedInputEvent.shouldSkipIme() ? this.mFirstPostImeInputStage : this.mFirstInputStage;
            }
            if (queuedInputEvent.mEvent instanceof android.view.KeyEvent) {
                android.os.Trace.traceBegin(8L, "preDispatchToUnhandledKeyManager");
                this.mUnhandledKeyManager.preDispatch((android.view.KeyEvent) queuedInputEvent.mEvent);
                android.os.Trace.traceEnd(8L);
            }
            if (inputStage != null) {
                handleWindowFocusChanged();
                inputStage.deliver(queuedInputEvent);
            } else {
                finishInputEvent(queuedInputEvent);
            }
        } catch (java.lang.Throwable th) {
            throw th;
        } finally {
            android.os.Trace.traceEnd(8L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishInputEvent(android.view.ViewRootImpl.QueuedInputEvent queuedInputEvent) {
        android.os.Trace.asyncTraceEnd(8L, "deliverInputEvent", queuedInputEvent.mEvent.getId());
        if (queuedInputEvent.mReceiver != null) {
            boolean z = (queuedInputEvent.mFlags & 8) != 0;
            if ((queuedInputEvent.mFlags & 64) != 0) {
                android.os.Trace.traceBegin(8L, "processInputEventBeforeFinish");
                try {
                    android.view.InputEvent processInputEventBeforeFinish = this.mInputCompatProcessor.processInputEventBeforeFinish(queuedInputEvent.mEvent);
                    if (processInputEventBeforeFinish != null) {
                        queuedInputEvent.mReceiver.finishInputEvent(processInputEventBeforeFinish, z);
                    }
                } finally {
                    android.os.Trace.traceEnd(8L);
                }
            } else {
                queuedInputEvent.mReceiver.finishInputEvent(queuedInputEvent.mEvent, z);
            }
            if (queuedInputEvent.mEvent instanceof android.view.KeyEvent) {
                logHandledSystemKey((android.view.KeyEvent) queuedInputEvent.mEvent, z);
            }
        } else {
            queuedInputEvent.mEvent.recycleIfNeededAfterDispatch();
        }
        recycleQueuedInputEvent(queuedInputEvent);
    }

    private void logHandledSystemKey(android.view.KeyEvent keyEvent, boolean z) {
        if (keyEvent.getKeyCode() == 264 && keyEvent.isDown() && keyEvent.getRepeatCount() == 0 && z) {
            com.android.modules.expresslog.Counter.logIncrementWithUid("input.value_app_handled_stem_primary_key_gestures_count", android.os.Process.myUid());
        }
    }

    static boolean isTerminalInputEvent(android.view.InputEvent inputEvent) {
        if (inputEvent instanceof android.view.KeyEvent) {
            return ((android.view.KeyEvent) inputEvent).getAction() == 1;
        }
        int action = ((android.view.MotionEvent) inputEvent).getAction();
        return action == 1 || action == 3 || action == 10;
    }

    void scheduleConsumeBatchedInput() {
        if (!this.mConsumeBatchedInputScheduled && !this.mConsumeBatchedInputImmediatelyScheduled) {
            this.mConsumeBatchedInputScheduled = true;
            this.mChoreographer.postCallback(0, this.mConsumedBatchedInputRunnable, null);
            if (this.mAttachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.notifyCallbackPending();
            }
        }
    }

    void unscheduleConsumeBatchedInput() {
        if (this.mConsumeBatchedInputScheduled) {
            this.mConsumeBatchedInputScheduled = false;
            this.mChoreographer.removeCallbacks(0, this.mConsumedBatchedInputRunnable, null);
        }
    }

    void scheduleConsumeBatchedInputImmediately() {
        if (!this.mConsumeBatchedInputImmediatelyScheduled) {
            unscheduleConsumeBatchedInput();
            this.mConsumeBatchedInputImmediatelyScheduled = true;
            this.mHandler.post(this.mConsumeBatchedInputImmediatelyRunnable);
        }
    }

    boolean doConsumeBatchedInput(long j) {
        boolean z;
        if (this.mInputEventReceiver != null) {
            z = this.mInputEventReceiver.consumeBatchedInputEvents(j);
        } else {
            z = false;
        }
        doProcessInputEvents();
        return z;
    }

    final class TraversalRunnable implements java.lang.Runnable {
        TraversalRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            android.view.ViewRootImpl.this.doTraversal();
        }
    }

    final class WindowInputEventReceiver extends android.view.InputEventReceiver {
        public WindowInputEventReceiver(android.view.InputChannel inputChannel, android.os.Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(android.view.InputEvent inputEvent) {
            android.os.Trace.traceBegin(8L, "processInputEventForCompatibility");
            try {
                java.util.List<android.view.InputEvent> processInputEventForCompatibility = android.view.ViewRootImpl.this.mInputCompatProcessor.processInputEventForCompatibility(inputEvent);
                android.os.Trace.traceEnd(8L);
                if (processInputEventForCompatibility == null) {
                    android.view.ViewRootImpl.this.enqueueInputEvent(inputEvent, this, 0, true);
                } else {
                    if (processInputEventForCompatibility.isEmpty()) {
                        finishInputEvent(inputEvent, true);
                        return;
                    }
                    for (int i = 0; i < processInputEventForCompatibility.size(); i++) {
                        android.view.ViewRootImpl.this.enqueueInputEvent(processInputEventForCompatibility.get(i), this, 64, true);
                    }
                }
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(8L);
                throw th;
            }
        }

        @Override // android.view.InputEventReceiver
        public void onBatchedInputEventPending(int i) {
            if (android.view.ViewRootImpl.this.mUnbufferedInputDispatch || (i & android.view.ViewRootImpl.this.mUnbufferedInputSource) != 0) {
                if (android.view.ViewRootImpl.this.mConsumeBatchedInputScheduled) {
                    android.view.ViewRootImpl.this.unscheduleConsumeBatchedInput();
                }
                consumeBatchedInputEvents(-1L);
                return;
            }
            android.view.ViewRootImpl.this.scheduleConsumeBatchedInput();
        }

        @Override // android.view.InputEventReceiver
        public void onFocusEvent(boolean z) {
            android.view.ViewRootImpl.this.windowFocusChanged(z);
        }

        @Override // android.view.InputEventReceiver
        public void onTouchModeChanged(boolean z) {
            android.view.ViewRootImpl.this.touchModeChanged(z);
        }

        @Override // android.view.InputEventReceiver
        public void onPointerCaptureEvent(boolean z) {
            android.view.ViewRootImpl.this.dispatchPointerCaptureChanged(z);
        }

        @Override // android.view.InputEventReceiver
        public void onDragEvent(boolean z, float f, float f2) {
            android.view.ViewRootImpl.this.dispatchDragEvent(android.view.DragEvent.obtain(z ? 6 : 2, f, f2, 0.0f, 0.0f, null, null, null, null, null, false));
        }

        @Override // android.view.InputEventReceiver
        public void dispose() {
            android.view.ViewRootImpl.this.unscheduleConsumeBatchedInput();
            super.dispose();
        }
    }

    final class InputMetricsListener implements android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener {
        public long[] data = new long[23];

        InputMetricsListener() {
        }

        @Override // android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener
        public void onFrameMetricsAvailable(int i) {
            int i2 = (int) this.data[4];
            if (i2 == 0) {
                return;
            }
            long j = this.data[21];
            if (j <= 0) {
                return;
            }
            long j2 = this.data[19];
            if (android.view.ViewRootImpl.this.mInputEventReceiver == null) {
                return;
            }
            if (j2 >= j) {
                android.util.Log.w(android.view.ViewRootImpl.TAG, "Not reporting timeline because gpuCompletedTime is " + ((j2 - j) * 1.0E-6d) + "ms ahead of presentTime. FRAME_TIMELINE_VSYNC_ID=" + this.data[1] + ", INPUT_EVENT_ID=" + i2);
                return;
            }
            android.view.ViewRootImpl.this.mInputEventReceiver.reportTimeline(i2, j2, j);
        }
    }

    final class ConsumeBatchedInputRunnable implements java.lang.Runnable {
        ConsumeBatchedInputRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            android.view.ViewRootImpl.this.mConsumeBatchedInputScheduled = false;
            if (android.view.ViewRootImpl.this.doConsumeBatchedInput(android.view.ViewRootImpl.this.mChoreographer.getFrameTimeNanos())) {
                android.view.ViewRootImpl.this.scheduleConsumeBatchedInput();
            }
        }
    }

    final class ConsumeBatchedInputImmediatelyRunnable implements java.lang.Runnable {
        ConsumeBatchedInputImmediatelyRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            android.view.ViewRootImpl.this.mConsumeBatchedInputImmediatelyScheduled = false;
            android.view.ViewRootImpl.this.doConsumeBatchedInput(-1L);
        }
    }

    final class InvalidateOnAnimationRunnable implements java.lang.Runnable {
        private boolean mPosted;
        private android.view.View.AttachInfo.InvalidateInfo[] mTempViewRects;
        private android.view.View[] mTempViews;
        private final java.util.ArrayList<android.view.View> mViews = new java.util.ArrayList<>();
        private final java.util.ArrayList<android.view.View.AttachInfo.InvalidateInfo> mViewRects = new java.util.ArrayList<>();

        InvalidateOnAnimationRunnable() {
        }

        public void addView(android.view.View view) {
            synchronized (this) {
                this.mViews.add(view);
                postIfNeededLocked();
            }
            if (android.view.ViewRootImpl.this.mAttachInfo.mThreadedRenderer != null) {
                android.view.ViewRootImpl.this.mAttachInfo.mThreadedRenderer.notifyCallbackPending();
            }
        }

        public void addViewRect(android.view.View.AttachInfo.InvalidateInfo invalidateInfo) {
            synchronized (this) {
                this.mViewRects.add(invalidateInfo);
                postIfNeededLocked();
            }
            if (android.view.ViewRootImpl.this.mAttachInfo.mThreadedRenderer != null) {
                android.view.ViewRootImpl.this.mAttachInfo.mThreadedRenderer.notifyCallbackPending();
            }
        }

        public void removeView(android.view.View view) {
            synchronized (this) {
                this.mViews.remove(view);
                int size = this.mViewRects.size();
                while (true) {
                    int i = size - 1;
                    if (size <= 0) {
                        break;
                    }
                    android.view.View.AttachInfo.InvalidateInfo invalidateInfo = this.mViewRects.get(i);
                    if (invalidateInfo.target == view) {
                        this.mViewRects.remove(i);
                        invalidateInfo.recycle();
                    }
                    size = i;
                }
                if (this.mPosted && this.mViews.isEmpty() && this.mViewRects.isEmpty()) {
                    android.view.ViewRootImpl.this.mChoreographer.removeCallbacks(1, this, null);
                    this.mPosted = false;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            int i;
            int size;
            int size2;
            synchronized (this) {
                this.mPosted = false;
                size = this.mViews.size();
                if (size != 0) {
                    this.mTempViews = (android.view.View[]) this.mViews.toArray(this.mTempViews != null ? this.mTempViews : new android.view.View[size]);
                    this.mViews.clear();
                }
                size2 = this.mViewRects.size();
                if (size2 != 0) {
                    this.mTempViewRects = (android.view.View.AttachInfo.InvalidateInfo[]) this.mViewRects.toArray(this.mTempViewRects != null ? this.mTempViewRects : new android.view.View.AttachInfo.InvalidateInfo[size2]);
                    this.mViewRects.clear();
                }
            }
            for (int i2 = 0; i2 < size; i2++) {
                this.mTempViews[i2].invalidate();
                this.mTempViews[i2] = null;
            }
            for (i = 0; i < size2; i++) {
                android.view.View.AttachInfo.InvalidateInfo invalidateInfo = this.mTempViewRects[i];
                invalidateInfo.target.invalidate(invalidateInfo.left, invalidateInfo.top, invalidateInfo.right, invalidateInfo.bottom);
                invalidateInfo.recycle();
            }
        }

        private void postIfNeededLocked() {
            if (!this.mPosted) {
                android.view.ViewRootImpl.this.mChoreographer.postCallback(1, this, null);
                this.mPosted = true;
            }
        }
    }

    public void dispatchInvalidateDelayed(android.view.View view, long j) {
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, view), j);
    }

    public void dispatchInvalidateRectDelayed(android.view.View.AttachInfo.InvalidateInfo invalidateInfo, long j) {
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2, invalidateInfo), j);
    }

    public void dispatchInvalidateOnAnimation(android.view.View view) {
        this.mInvalidateOnAnimationRunnable.addView(view);
    }

    public void dispatchInvalidateRectOnAnimation(android.view.View.AttachInfo.InvalidateInfo invalidateInfo) {
        this.mInvalidateOnAnimationRunnable.addViewRect(invalidateInfo);
    }

    public void cancelInvalidate(android.view.View view) {
        this.mHandler.removeMessages(1, view);
        this.mHandler.removeMessages(2, view);
        this.mInvalidateOnAnimationRunnable.removeView(view);
    }

    public void dispatchInputEvent(android.view.InputEvent inputEvent) {
        dispatchInputEvent(inputEvent, null);
    }

    public void dispatchInputEvent(android.view.InputEvent inputEvent, android.view.InputEventReceiver inputEventReceiver) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = inputEvent;
        obtain.arg2 = inputEventReceiver;
        android.os.Message obtainMessage = this.mHandler.obtainMessage(7, obtain);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessage(obtainMessage);
    }

    public void synthesizeInputEvent(android.view.InputEvent inputEvent) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(24, inputEvent);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessage(obtainMessage);
    }

    public void dispatchKeyFromIme(android.view.KeyEvent keyEvent) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(11, keyEvent);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessage(obtainMessage);
    }

    public void dispatchKeyFromAutofill(android.view.KeyEvent keyEvent) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(12, keyEvent);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessage(obtainMessage);
    }

    public void dispatchUnhandledInputEvent(android.view.InputEvent inputEvent) {
        if (inputEvent instanceof android.view.MotionEvent) {
            inputEvent = android.view.MotionEvent.obtain((android.view.MotionEvent) inputEvent);
        }
        synthesizeInputEvent(inputEvent);
    }

    public void dispatchAppVisibility(boolean z) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(8);
        obtainMessage.arg1 = z ? 1 : 0;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void dispatchGetNewSurface() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(9));
    }

    public void windowFocusChanged(boolean z) {
        synchronized (this) {
            this.mWindowFocusChanged = true;
            this.mUpcomingWindowFocus = z;
        }
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = 6;
        this.mHandler.sendMessage(obtain);
    }

    public void touchModeChanged(boolean z) {
        synchronized (this) {
            this.mUpcomingInTouchMode = z;
        }
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = 34;
        this.mHandler.sendMessage(obtain);
    }

    public void dispatchWindowShown() {
        this.mHandler.sendEmptyMessage(25);
    }

    public void dispatchCloseSystemDialogs(java.lang.String str) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = 14;
        obtain.obj = str;
        this.mHandler.sendMessage(obtain);
    }

    public void dispatchDragEvent(android.view.DragEvent dragEvent) {
        int i;
        if (dragEvent.getAction() == 2) {
            i = 16;
            this.mHandler.removeMessages(16);
        } else {
            i = 15;
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(i, dragEvent));
    }

    public void updatePointerIcon(float f, float f2) {
        this.mHandler.removeMessages(27);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(27, android.view.MotionEvent.obtain(0L, android.os.SystemClock.uptimeMillis(), 7, f, f2, 0)));
    }

    public void dispatchCheckFocus() {
        if (!this.mHandler.hasMessages(13)) {
            this.mHandler.sendEmptyMessage(13);
        }
    }

    public void dispatchRequestKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) {
        this.mHandler.obtainMessage(26, i, 0, iResultReceiver).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPointerCaptureChanged(boolean z) {
        this.mHandler.removeMessages(28);
        android.os.Message obtainMessage = this.mHandler.obtainMessage(28);
        obtainMessage.arg1 = z ? 1 : 0;
        this.mHandler.sendMessage(obtainMessage);
    }

    private void postSendWindowContentChangedCallback(android.view.View view, int i) {
        if (this.mSendWindowContentChangedAccessibilityEvent == null) {
            this.mSendWindowContentChangedAccessibilityEvent = new android.view.ViewRootImpl.SendWindowContentChangedAccessibilityEvent();
        }
        this.mSendWindowContentChangedAccessibilityEvent.runOrPost(view, i);
    }

    private void removeSendWindowContentChangedCallback() {
        if (this.mSendWindowContentChangedAccessibilityEvent != null) {
            this.mHandler.removeCallbacks(this.mSendWindowContentChangedAccessibilityEvent);
        }
    }

    public int getDirectAccessibilityConnectionId() {
        return this.mAccessibilityInteractionConnectionManager.ensureDirectConnection();
    }

    @Override // android.view.ViewParent
    public boolean showContextMenuForChild(android.view.View view) {
        return false;
    }

    @Override // android.view.ViewParent
    public boolean showContextMenuForChild(android.view.View view, float f, float f2) {
        return false;
    }

    @Override // android.view.ViewParent
    public android.view.ActionMode startActionModeForChild(android.view.View view, android.view.ActionMode.Callback callback) {
        return null;
    }

    @Override // android.view.ViewParent
    public android.view.ActionMode startActionModeForChild(android.view.View view, android.view.ActionMode.Callback callback, int i) {
        return null;
    }

    @Override // android.view.ViewParent
    public void createContextMenu(android.view.ContextMenu contextMenu) {
    }

    @Override // android.view.ViewParent
    public void childDrawableStateChanged(android.view.View view) {
    }

    @Override // android.view.ViewParent
    public boolean requestSendAccessibilityEvent(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider;
        if (this.mView == null || this.mStopped || this.mPausedForTransition) {
            return false;
        }
        if (accessibilityEvent.getEventType() != 2048 && this.mSendWindowContentChangedAccessibilityEvent != null && this.mSendWindowContentChangedAccessibilityEvent.mSource != null) {
            this.mSendWindowContentChangedAccessibilityEvent.removeCallbacksAndRun();
        }
        int eventType = accessibilityEvent.getEventType();
        android.view.View sourceForAccessibilityEvent = getSourceForAccessibilityEvent(accessibilityEvent);
        switch (eventType) {
            case 2048:
                handleWindowContentChangedEvent(accessibilityEvent);
                break;
            case 32768:
                if (sourceForAccessibilityEvent != null && (accessibilityNodeProvider = sourceForAccessibilityEvent.getAccessibilityNodeProvider()) != null) {
                    setAccessibilityFocus(sourceForAccessibilityEvent, accessibilityNodeProvider.createAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(accessibilityEvent.getSourceNodeId())));
                    break;
                }
                break;
            case 65536:
                if (sourceForAccessibilityEvent != null && sourceForAccessibilityEvent.getAccessibilityNodeProvider() != null) {
                    setAccessibilityFocus(null, null);
                    break;
                }
                break;
        }
        this.mAccessibilityManager.sendAccessibilityEvent(accessibilityEvent);
        return true;
    }

    private android.view.View getSourceForAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        return android.view.accessibility.AccessibilityNodeIdManager.getInstance().findView(android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(accessibilityEvent.getSourceNodeId()));
    }

    private boolean isAccessibilityFocusDirty() {
        android.graphics.drawable.Drawable drawable = this.mAttachInfo.mAccessibilityFocusDrawable;
        if (drawable != null) {
            android.graphics.Rect rect = this.mAttachInfo.mTmpInvalRect;
            if (!getAccessibilityFocusedRect(rect)) {
                rect.setEmpty();
            }
            if (!rect.equals(drawable.getBounds())) {
                return true;
            }
            return false;
        }
        return false;
    }

    private void handleWindowContentChangedEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        android.view.View view = this.mAccessibilityFocusedHost;
        if (view == null || this.mAccessibilityFocusedVirtualView == null) {
            return;
        }
        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
        if (accessibilityNodeProvider == null) {
            this.mAccessibilityFocusedHost = null;
            this.mAccessibilityFocusedVirtualView = null;
            view.clearAccessibilityFocusNoCallbacks(0);
            return;
        }
        int contentChangeTypes = accessibilityEvent.getContentChangeTypes();
        if ((contentChangeTypes & 1) == 0 && contentChangeTypes != 0) {
            return;
        }
        int accessibilityViewId = android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(accessibilityEvent.getSourceNodeId());
        android.view.View view2 = this.mAccessibilityFocusedHost;
        boolean z = false;
        while (view2 != null && !z) {
            if (accessibilityViewId == view2.getAccessibilityViewId()) {
                z = true;
            } else {
                java.lang.Object parent = view2.getParent();
                if (parent instanceof android.view.View) {
                    view2 = (android.view.View) parent;
                } else {
                    view2 = null;
                }
            }
        }
        if (!z) {
            return;
        }
        int virtualDescendantId = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(this.mAccessibilityFocusedVirtualView.getSourceNodeId());
        android.graphics.Rect rect = this.mTempRect;
        this.mAccessibilityFocusedVirtualView.getBoundsInScreen(rect);
        this.mAccessibilityFocusedVirtualView = accessibilityNodeProvider.createAccessibilityNodeInfo(virtualDescendantId);
        if (this.mAccessibilityFocusedVirtualView == null) {
            this.mAccessibilityFocusedHost = null;
            view.clearAccessibilityFocusNoCallbacks(0);
            accessibilityNodeProvider.performAction(virtualDescendantId, android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS.getId(), null);
            invalidateRectOnScreen(rect);
            return;
        }
        android.graphics.Rect boundsInScreen = this.mAccessibilityFocusedVirtualView.getBoundsInScreen();
        if (!rect.equals(boundsInScreen)) {
            rect.union(boundsInScreen);
            invalidateRectOnScreen(rect);
        }
    }

    @Override // android.view.ViewParent
    public void notifySubtreeAccessibilityStateChanged(android.view.View view, android.view.View view2, int i) {
        postSendWindowContentChangedCallback((android.view.View) java.util.Objects.requireNonNull(view2), i);
    }

    @Override // android.view.ViewParent
    public boolean canResolveLayoutDirection() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean isLayoutDirectionResolved() {
        return true;
    }

    @Override // android.view.ViewParent
    public int getLayoutDirection() {
        return 0;
    }

    @Override // android.view.ViewParent
    public boolean canResolveTextDirection() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean isTextDirectionResolved() {
        return true;
    }

    @Override // android.view.ViewParent
    public int getTextDirection() {
        return 1;
    }

    @Override // android.view.ViewParent
    public boolean canResolveTextAlignment() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean isTextAlignmentResolved() {
        return true;
    }

    @Override // android.view.ViewParent
    public int getTextAlignment() {
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.View getCommonPredecessor(android.view.View view, android.view.View view2) {
        if (this.mTempHashSet == null) {
            this.mTempHashSet = new java.util.HashSet<>();
        }
        java.util.HashSet<android.view.View> hashSet = this.mTempHashSet;
        hashSet.clear();
        while (view != null) {
            hashSet.add(view);
            java.lang.Object obj = view.mParent;
            if (obj instanceof android.view.View) {
                view = (android.view.View) obj;
            } else {
                view = null;
            }
        }
        while (view2 != null) {
            if (hashSet.contains(view2)) {
                hashSet.clear();
                return view2;
            }
            java.lang.Object obj2 = view2.mParent;
            if (obj2 instanceof android.view.View) {
                view2 = (android.view.View) obj2;
            } else {
                view2 = null;
            }
        }
        hashSet.clear();
        return null;
    }

    void checkThread() {
        java.lang.Thread currentThread = java.lang.Thread.currentThread();
        if (this.mThread != currentThread) {
            throw new android.view.ViewRootImpl.CalledFromWrongThreadException("Only the original thread that created a view hierarchy can touch its views. Expected: " + this.mThread.getName() + " Calling: " + currentThread.getName());
        }
    }

    @Override // android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
    }

    @Override // android.view.ViewParent
    public boolean requestChildRectangleOnScreen(android.view.View view, android.graphics.Rect rect, boolean z) {
        if (rect == null) {
            return scrollToRectOrFocus(null, z);
        }
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        boolean scrollToRectOrFocus = scrollToRectOrFocus(rect, z);
        this.mTempRect.set(rect);
        this.mTempRect.offset(0, -this.mCurScrollY);
        this.mTempRect.offset(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
        try {
            this.mWindowSession.onRectangleOnScreenRequested(this.mWindow, this.mTempRect);
        } catch (android.os.RemoteException e) {
        }
        return scrollToRectOrFocus;
    }

    @Override // android.view.ViewParent
    public void childHasTransientStateChanged(android.view.View view, boolean z) {
    }

    @Override // android.view.ViewParent
    public boolean onStartNestedScroll(android.view.View view, android.view.View view2, int i) {
        return false;
    }

    @Override // android.view.ViewParent
    public void onStopNestedScroll(android.view.View view) {
    }

    @Override // android.view.ViewParent
    public void onNestedScrollAccepted(android.view.View view, android.view.View view2, int i) {
    }

    @Override // android.view.ViewParent
    public void onNestedScroll(android.view.View view, int i, int i2, int i3, int i4) {
    }

    @Override // android.view.ViewParent
    public void onNestedPreScroll(android.view.View view, int i, int i2, int[] iArr) {
    }

    @Override // android.view.ViewParent
    public boolean onNestedFling(android.view.View view, float f, float f2, boolean z) {
        return false;
    }

    @Override // android.view.ViewParent
    public boolean onNestedPreFling(android.view.View view, float f, float f2) {
        return false;
    }

    @Override // android.view.ViewParent
    public boolean onNestedPrePerformAccessibilityAction(android.view.View view, int i, android.os.Bundle bundle) {
        return false;
    }

    public boolean probablyHasInput() {
        if (this.mInputEventReceiver == null) {
            return false;
        }
        return this.mInputEventReceiver.probablyHasInput();
    }

    public void addScrollCaptureCallback(android.view.ScrollCaptureCallback scrollCaptureCallback) {
        if (this.mRootScrollCaptureCallbacks == null) {
            this.mRootScrollCaptureCallbacks = new java.util.HashSet<>();
        }
        this.mRootScrollCaptureCallbacks.add(scrollCaptureCallback);
    }

    public void removeScrollCaptureCallback(android.view.ScrollCaptureCallback scrollCaptureCallback) {
        if (this.mRootScrollCaptureCallbacks != null) {
            this.mRootScrollCaptureCallbacks.remove(scrollCaptureCallback);
            if (this.mRootScrollCaptureCallbacks.isEmpty()) {
                this.mRootScrollCaptureCallbacks = null;
            }
        }
    }

    public void dispatchScrollCaptureRequest(android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener) {
        this.mHandler.obtainMessage(33, iScrollCaptureResponseListener).sendToTarget();
    }

    void processingBackKey(boolean z) {
        this.mProcessingBackKey = z;
    }

    private void collectRootScrollCaptureTargets(android.view.ScrollCaptureSearchResults scrollCaptureSearchResults) {
        if (this.mRootScrollCaptureCallbacks == null) {
            return;
        }
        java.util.Iterator<android.view.ScrollCaptureCallback> it = this.mRootScrollCaptureCallbacks.iterator();
        while (it.hasNext()) {
            android.view.ScrollCaptureCallback next = it.next();
            android.graphics.Point point = new android.graphics.Point(this.mView.getLeft(), this.mView.getTop());
            scrollCaptureSearchResults.addTarget(new android.view.ScrollCaptureTarget(this.mView, new android.graphics.Rect(0, 0, this.mView.getWidth(), this.mView.getHeight()), point, next));
        }
    }

    public void setScrollCaptureRequestTimeout(int i) {
        this.mScrollCaptureRequestTimeout = i;
    }

    public long getScrollCaptureRequestTimeout() {
        return this.mScrollCaptureRequestTimeout;
    }

    public void handleScrollCaptureRequest(final android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener) {
        final android.view.ScrollCaptureSearchResults scrollCaptureSearchResults = new android.view.ScrollCaptureSearchResults(this.mContext.getMainExecutor());
        collectRootScrollCaptureTargets(scrollCaptureSearchResults);
        android.view.View view = getView();
        if (view != null) {
            android.graphics.Point point = new android.graphics.Point();
            android.graphics.Rect rect = new android.graphics.Rect(0, 0, view.getWidth(), view.getHeight());
            getChildVisibleRect(view, rect, point);
            java.util.Objects.requireNonNull(scrollCaptureSearchResults);
            view.dispatchScrollCaptureSearch(rect, point, new java.util.function.Consumer() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.view.ScrollCaptureSearchResults.this.addTarget((android.view.ScrollCaptureTarget) obj);
                }
            });
        }
        scrollCaptureSearchResults.setOnCompleteListener(new java.lang.Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                android.view.ViewRootImpl.this.lambda$handleScrollCaptureRequest$9(iScrollCaptureResponseListener, scrollCaptureSearchResults);
            }
        });
        if (!scrollCaptureSearchResults.isComplete()) {
            android.view.ViewRootImpl.ViewRootHandler viewRootHandler = this.mHandler;
            java.util.Objects.requireNonNull(scrollCaptureSearchResults);
            viewRootHandler.postDelayed(new java.lang.Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.ScrollCaptureSearchResults.this.finish();
                }
            }, getScrollCaptureRequestTimeout());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dispatchScrollCaptureSearchResponse, reason: merged with bridge method [inline-methods] */
    public void lambda$handleScrollCaptureRequest$9(android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener, android.view.ScrollCaptureSearchResults scrollCaptureSearchResults) {
        android.view.ScrollCaptureTarget topResult = scrollCaptureSearchResults.getTopResult();
        android.view.ScrollCaptureResponse.Builder builder = new android.view.ScrollCaptureResponse.Builder();
        builder.setWindowTitle(getTitle().toString());
        builder.setPackageName(this.mContext.getPackageName());
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(stringWriter);
        scrollCaptureSearchResults.dump(indentingPrintWriter);
        indentingPrintWriter.flush();
        builder.addMessage(stringWriter.toString());
        if (topResult == null) {
            builder.setDescription("No scrollable targets found in window");
            try {
                iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
                return;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to send scroll capture search result", e);
                return;
            }
        }
        builder.setDescription("Connected");
        android.graphics.Rect rect = new android.graphics.Rect();
        topResult.getContainingView().getLocationInWindow(this.mAttachInfo.mTmpLocation);
        rect.set(topResult.getScrollBounds());
        rect.offset(this.mAttachInfo.mTmpLocation[0], this.mAttachInfo.mTmpLocation[1]);
        builder.setBoundsInWindow(rect);
        android.graphics.Rect rect2 = new android.graphics.Rect();
        this.mView.getLocationOnScreen(this.mAttachInfo.mTmpLocation);
        rect2.set(0, 0, this.mView.getWidth(), this.mView.getHeight());
        rect2.offset(this.mAttachInfo.mTmpLocation[0], this.mAttachInfo.mTmpLocation[1]);
        builder.setWindowBounds(rect2);
        android.view.ScrollCaptureConnection scrollCaptureConnection = new android.view.ScrollCaptureConnection(this.mView.getContext().getMainExecutor(), topResult);
        builder.setConnection(scrollCaptureConnection);
        try {
            iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
        } catch (android.os.RemoteException e2) {
            scrollCaptureConnection.close();
        }
    }

    private void reportNextDraw(java.lang.String str) {
        this.mReportNextDraw = true;
        this.mLastReportNextDrawReason = str;
    }

    public void setReportNextDraw(boolean z, java.lang.String str) {
        this.mSyncBuffer = z;
        reportNextDraw(str);
        invalidate();
    }

    void changeCanvasOpacity(boolean z) {
        android.util.Log.d(this.mTag, "changeCanvasOpacity: opaque=" + z);
        boolean z2 = z & ((this.mView.mPrivateFlags & 512) == 0);
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.setOpaque(z2);
        }
    }

    public boolean dispatchUnhandledKeyEvent(android.view.KeyEvent keyEvent) {
        return this.mUnhandledKeyManager.dispatch(this.mView, keyEvent);
    }

    class TakenSurfaceHolder extends com.android.internal.view.BaseSurfaceHolder {
        TakenSurfaceHolder() {
        }

        @Override // com.android.internal.view.BaseSurfaceHolder
        public boolean onAllowLockCanvas() {
            return android.view.ViewRootImpl.this.mDrawingAllowed;
        }

        @Override // com.android.internal.view.BaseSurfaceHolder
        public void onRelayoutContainer() {
        }

        @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
        public void setFormat(int i) {
            ((com.android.internal.view.RootViewSurfaceTaker) android.view.ViewRootImpl.this.mView).setSurfaceFormat(i);
        }

        @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
        public void setType(int i) {
            ((com.android.internal.view.RootViewSurfaceTaker) android.view.ViewRootImpl.this.mView).setSurfaceType(i);
        }

        @Override // com.android.internal.view.BaseSurfaceHolder
        public void onUpdateSurface() {
            throw new java.lang.IllegalStateException("Shouldn't be here");
        }

        @Override // android.view.SurfaceHolder
        public boolean isCreating() {
            return android.view.ViewRootImpl.this.mIsCreating;
        }

        @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
        public void setFixedSize(int i, int i2) {
            throw new java.lang.UnsupportedOperationException("Currently only support sizing from layout");
        }

        @Override // android.view.SurfaceHolder
        public void setKeepScreenOn(boolean z) {
            ((com.android.internal.view.RootViewSurfaceTaker) android.view.ViewRootImpl.this.mView).setSurfaceKeepScreenOn(z);
        }
    }

    static class W extends android.view.IWindow.Stub implements android.app.servertransaction.WindowStateResizeItem.ResizeListener {
        private boolean mIsFromResizeItem;
        private final java.lang.ref.WeakReference<android.view.ViewRootImpl> mViewAncestor;
        private final android.view.IWindowSession mWindowSession;

        W(android.view.ViewRootImpl viewRootImpl) {
            this.mViewAncestor = new java.lang.ref.WeakReference<>(viewRootImpl);
            this.mWindowSession = viewRootImpl.mWindowSession;
        }

        @Override // android.app.servertransaction.WindowStateResizeItem.ResizeListener
        public void onExecutingWindowStateResizeItem() {
            this.mIsFromResizeItem = true;
        }

        @Override // android.view.IWindow
        public void resized(android.window.ClientWindowFrames clientWindowFrames, boolean z, android.util.MergedConfiguration mergedConfiguration, android.view.InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4) {
            android.view.InsetsState insetsState2;
            android.util.MergedConfiguration mergedConfiguration2;
            android.window.ClientWindowFrames clientWindowFrames2;
            boolean z5 = this.mIsFromResizeItem;
            boolean z6 = false;
            this.mIsFromResizeItem = false;
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl == null) {
                return;
            }
            if (insetsState.isSourceOrDefaultVisible(android.view.InsetsSource.ID_IME, android.view.WindowInsets.Type.ime())) {
                com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("ViewRootImpl.W#resized", viewRootImpl.getInsetsController().getHost().getInputMethodManager(), null);
            }
            if (z5 && viewRootImpl.mHandler.getLooper() == android.app.ActivityThread.currentActivityThread().getLooper()) {
                viewRootImpl.handleResized(clientWindowFrames, z, mergedConfiguration, insetsState, z2, z3, i, i2, z4);
                return;
            }
            if (!z5 && android.os.Binder.getCallingPid() == android.os.Process.myPid()) {
                z6 = true;
            }
            if (!z6) {
                insetsState2 = insetsState;
                mergedConfiguration2 = mergedConfiguration;
                clientWindowFrames2 = clientWindowFrames;
            } else {
                android.view.InsetsState insetsState3 = new android.view.InsetsState(insetsState, true);
                clientWindowFrames2 = new android.window.ClientWindowFrames(clientWindowFrames);
                insetsState2 = insetsState3;
                mergedConfiguration2 = new android.util.MergedConfiguration(mergedConfiguration);
            }
            viewRootImpl.dispatchResized(clientWindowFrames2, z, mergedConfiguration2, insetsState2, z2, z3, i, i2, z4);
        }

        @Override // android.view.IWindow
        public void insetsControlChanged(android.view.InsetsState insetsState, android.view.InsetsSourceControl[] insetsSourceControlArr) {
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchInsetsControlChanged(insetsState, insetsSourceControlArr);
            }
        }

        @Override // android.view.IWindow
        public void showInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) {
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (z) {
                com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("ViewRootImpl.W#showInsets", viewRootImpl.getInsetsController().getHost().getInputMethodManager(), null);
            }
            if (viewRootImpl != null) {
                android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 28);
                viewRootImpl.showInsets(i, z, token);
            } else {
                android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 28);
            }
        }

        @Override // android.view.IWindow
        public void hideInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) {
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (z) {
                com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("ViewRootImpl.W#hideInsets", viewRootImpl.getInsetsController().getHost().getInputMethodManager(), null);
            }
            if (viewRootImpl != null) {
                android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 29);
                viewRootImpl.hideInsets(i, z, token);
            } else {
                android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 29);
            }
        }

        @Override // android.view.IWindow
        public void moved(int i, int i2) {
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchMoved(i, i2);
            }
        }

        @Override // android.view.IWindow
        public void dispatchAppVisibility(boolean z) {
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchAppVisibility(z);
            }
        }

        @Override // android.view.IWindow
        public void dispatchGetNewSurface() {
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchGetNewSurface();
            }
        }

        private static int checkCallingPermission(java.lang.String str) {
            try {
                return android.app.ActivityManager.getService().checkPermission(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
            } catch (android.os.RemoteException e) {
                return -1;
            }
        }

        @Override // android.view.IWindow
        public void executeCommand(java.lang.String str, java.lang.String str2, android.os.ParcelFileDescriptor parcelFileDescriptor) {
            android.view.View view;
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl == null || (view = viewRootImpl.mView) == null) {
                return;
            }
            if (checkCallingPermission(android.Manifest.permission.DUMP) != 0) {
                throw new java.lang.SecurityException("Insufficient permissions to invoke executeCommand() from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid());
            }
            android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = null;
            try {
                try {
                    try {
                        android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
                        try {
                            android.view.ViewDebug.dispatchCommand(view, str, str2, autoCloseOutputStream2);
                            autoCloseOutputStream2.close();
                        } catch (java.io.IOException e) {
                            e = e;
                            autoCloseOutputStream = autoCloseOutputStream2;
                            e.printStackTrace();
                            if (autoCloseOutputStream != null) {
                                autoCloseOutputStream.close();
                            }
                        } catch (java.lang.Throwable th) {
                            th = th;
                            autoCloseOutputStream = autoCloseOutputStream2;
                            if (autoCloseOutputStream != null) {
                                try {
                                    autoCloseOutputStream.close();
                                } catch (java.io.IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (java.io.IOException e3) {
                        e = e3;
                    }
                } catch (java.io.IOException e4) {
                    e4.printStackTrace();
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        }

        @Override // android.view.IWindow
        public void closeSystemDialogs(java.lang.String str) {
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchCloseSystemDialogs(str);
            }
        }

        @Override // android.view.IWindow
        public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) {
            if (z) {
                try {
                    this.mWindowSession.wallpaperOffsetsComplete(asBinder());
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // android.view.IWindow
        public void dispatchWallpaperCommand(java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) {
            if (z) {
                try {
                    this.mWindowSession.wallpaperCommandComplete(asBinder(), null);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // android.view.IWindow
        public void dispatchDragEvent(android.view.DragEvent dragEvent) {
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchDragEvent(dragEvent);
            }
        }

        @Override // android.view.IWindow
        public void updatePointerIcon(float f, float f2) {
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.updatePointerIcon(f, f2);
            }
        }

        @Override // android.view.IWindow
        public void dispatchWindowShown() {
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchWindowShown();
            }
        }

        @Override // android.view.IWindow
        public void requestAppKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) {
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchRequestKeyboardShortcuts(iResultReceiver, i);
            }
        }

        @Override // android.view.IWindow
        public void requestScrollCapture(android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener) {
            android.view.ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchScrollCaptureRequest(iScrollCaptureResponseListener);
            }
        }
    }

    public static final class CalledFromWrongThreadException extends android.util.AndroidRuntimeException {
        public CalledFromWrongThreadException(java.lang.String str) {
            super(str);
        }
    }

    static android.view.HandlerActionQueue getRunQueue() {
        android.view.HandlerActionQueue handlerActionQueue = sRunQueues.get();
        if (handlerActionQueue != null) {
            return handlerActionQueue;
        }
        android.view.HandlerActionQueue handlerActionQueue2 = new android.view.HandlerActionQueue();
        sRunQueues.set(handlerActionQueue2);
        return handlerActionQueue2;
    }

    private void startDragResizing(android.graphics.Rect rect, boolean z, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        if (!this.mDragResizing) {
            this.mDragResizing = true;
            if (this.mUseMTRenderer) {
                for (int size = this.mWindowCallbacks.size() - 1; size >= 0; size--) {
                    this.mWindowCallbacks.get(size).onWindowDragResizeStart(rect, z, rect2, rect3);
                }
            }
            this.mFullRedrawNeeded = true;
        }
    }

    private void endDragResizing() {
        if (this.mDragResizing) {
            this.mDragResizing = false;
            if (this.mUseMTRenderer) {
                for (int size = this.mWindowCallbacks.size() - 1; size >= 0; size--) {
                    this.mWindowCallbacks.get(size).onWindowDragResizeEnd();
                }
            }
            this.mFullRedrawNeeded = true;
        }
    }

    private boolean updateContentDrawBounds() {
        boolean z;
        if (!this.mUseMTRenderer) {
            z = false;
        } else {
            z = false;
            for (int size = this.mWindowCallbacks.size() - 1; size >= 0; size--) {
                z |= this.mWindowCallbacks.get(size).onContentDrawn(this.mWindowAttributes.surfaceInsets.left, this.mWindowAttributes.surfaceInsets.top, this.mWidth, this.mHeight);
            }
        }
        return z | (this.mDragResizing && this.mReportNextDraw);
    }

    private void requestDrawWindow() {
        if (!this.mUseMTRenderer) {
            return;
        }
        if (this.mReportNextDraw) {
            this.mWindowDrawCountDown = new java.util.concurrent.CountDownLatch(this.mWindowCallbacks.size());
        }
        for (int size = this.mWindowCallbacks.size() - 1; size >= 0; size--) {
            this.mWindowCallbacks.get(size).onRequestDraw(this.mReportNextDraw);
        }
    }

    public android.view.SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    public android.os.IBinder getInputToken() {
        if (this.mInputEventReceiver == null) {
            return null;
        }
        return this.mInputEventReceiver.getToken();
    }

    @Override // android.view.AttachedSurfaceControl
    public android.window.InputTransferToken getInputTransferToken() {
        android.os.IBinder inputToken = getInputToken();
        if (inputToken == null) {
            throw new java.lang.IllegalStateException("Called getInputTransferToken for Window with no input channel");
        }
        return new android.window.InputTransferToken(inputToken);
    }

    public android.os.IBinder getWindowToken() {
        return this.mAttachInfo.mWindowToken;
    }

    final class AccessibilityInteractionConnectionManager implements android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener {
        private int mDirectConnectionId = -1;

        AccessibilityInteractionConnectionManager() {
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
        public void onAccessibilityStateChanged(boolean z) {
            if (z) {
                ensureConnection();
                android.view.ViewRootImpl.this.setAccessibilityWindowAttributesIfNeeded();
                if (android.view.ViewRootImpl.this.mAttachInfo.mHasWindowFocus && android.view.ViewRootImpl.this.mView != null) {
                    android.view.ViewRootImpl.this.mView.sendAccessibilityEvent(32);
                    android.view.View findFocus = android.view.ViewRootImpl.this.mView.findFocus();
                    if (findFocus != null && findFocus != android.view.ViewRootImpl.this.mView) {
                        findFocus.sendAccessibilityEvent(8);
                    }
                }
                if (android.view.ViewRootImpl.this.mAttachInfo.mLeashedParentToken != null) {
                    android.view.ViewRootImpl.this.mAccessibilityManager.associateEmbeddedHierarchy(android.view.ViewRootImpl.this.mAttachInfo.mLeashedParentToken, android.view.ViewRootImpl.this.mLeashToken);
                    return;
                }
                return;
            }
            ensureNoConnection();
            android.view.ViewRootImpl.this.mHandler.obtainMessage(21).sendToTarget();
        }

        public void ensureConnection() {
            if (!(android.view.ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId != -1)) {
                android.view.ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId = android.view.ViewRootImpl.this.mAccessibilityManager.addAccessibilityInteractionConnection(android.view.ViewRootImpl.this.mWindow, android.view.ViewRootImpl.this.mLeashToken, android.view.ViewRootImpl.this.mContext.getPackageName(), new android.view.ViewRootImpl.AccessibilityInteractionConnection(android.view.ViewRootImpl.this));
            }
        }

        public void ensureNoConnection() {
            if (android.view.ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId != -1) {
                android.view.ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId = -1;
                android.view.ViewRootImpl.this.mAccessibilityWindowAttributes = null;
                android.view.ViewRootImpl.this.mAccessibilityManager.removeAccessibilityInteractionConnection(android.view.ViewRootImpl.this.mWindow);
            }
        }

        public int ensureDirectConnection() {
            if (this.mDirectConnectionId == -1) {
                this.mDirectConnectionId = android.view.accessibility.AccessibilityInteractionClient.addDirectConnection(new android.view.ViewRootImpl.AccessibilityInteractionConnection(android.view.ViewRootImpl.this), android.view.ViewRootImpl.this.mAccessibilityManager);
                android.view.ViewRootImpl.this.mAccessibilityManager.notifyAccessibilityStateChanged();
            }
            return this.mDirectConnectionId;
        }

        public void ensureNoDirectConnection() {
            if (this.mDirectConnectionId != -1) {
                android.view.accessibility.AccessibilityInteractionClient.removeConnection(this.mDirectConnectionId);
                this.mDirectConnectionId = -1;
                android.view.ViewRootImpl.this.mAccessibilityManager.notifyAccessibilityStateChanged();
            }
        }
    }

    final class HighContrastTextManager implements android.view.accessibility.AccessibilityManager.HighTextContrastChangeListener {
        HighContrastTextManager() {
            android.view.ThreadedRenderer.setHighContrastText(android.view.ViewRootImpl.this.mAccessibilityManager.isHighTextContrastEnabled());
        }

        @Override // android.view.accessibility.AccessibilityManager.HighTextContrastChangeListener
        public void onHighTextContrastStateChanged(boolean z) {
            android.view.ThreadedRenderer.setHighContrastText(z);
            android.view.ViewRootImpl.this.destroyHardwareResources();
            android.view.ViewRootImpl.this.invalidate();
        }
    }

    static final class AccessibilityInteractionConnection extends android.view.accessibility.IAccessibilityInteractionConnection.Stub {
        private final java.lang.ref.WeakReference<android.view.ViewRootImpl> mViewRootImpl;

        AccessibilityInteractionConnection(android.view.ViewRootImpl viewRootImpl) {
            this.mViewRootImpl = new java.lang.ref.WeakReference<>(viewRootImpl);
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfoByAccessibilityId(long j, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr, android.os.Bundle bundle) {
            android.view.ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfoByAccessibilityIdClientThread(j, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec, fArr, bundle);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfosResult(null, i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void performAccessibilityAction(long j, int i, android.os.Bundle bundle, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) {
            android.view.ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().performAccessibilityActionClientThread(j, i, bundle, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setPerformAccessibilityActionResult(false, i2);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByViewId(long j, java.lang.String str, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) {
            android.view.ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfosByViewIdClientThread(j, str, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec, fArr);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult(null, i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByText(long j, java.lang.String str, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) {
            android.view.ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfosByTextClientThread(j, str, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec, fArr);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfosResult(null, i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findFocus(long j, int i, android.graphics.Region region, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) {
            android.view.ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findFocusClientThread(j, i, region, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2, magnificationSpec, fArr);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult(null, i2);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void focusSearch(long j, int i, android.graphics.Region region, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) {
            android.view.ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().focusSearchClientThread(j, i, region, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2, magnificationSpec, fArr);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult(null, i2);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void clearAccessibilityFocus() {
            android.view.ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().clearAccessibilityFocusClientThread();
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void notifyOutsideTouch() {
            android.view.ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().notifyOutsideTouchClientThread();
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void takeScreenshotOfWindow(int i, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
            android.view.ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().takeScreenshotOfWindowClientThread(i, screenCaptureListener, iAccessibilityInteractionConnectionCallback);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(1, i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void attachAccessibilityOverlayToWindow(android.view.SurfaceControl surfaceControl, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
            android.view.ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null) {
                viewRootImpl.getAccessibilityInteractionController().attachAccessibilityOverlayToWindowClientThread(surfaceControl, i, iAccessibilityInteractionConnectionCallback);
            }
        }
    }

    public android.view.accessibility.IAccessibilityEmbeddedConnection getAccessibilityEmbeddedConnection() {
        if (this.mAccessibilityEmbeddedConnection == null) {
            this.mAccessibilityEmbeddedConnection = new android.view.AccessibilityEmbeddedConnection(this);
        }
        return this.mAccessibilityEmbeddedConnection;
    }

    private class SendWindowContentChangedAccessibilityEvent implements java.lang.Runnable {
        public java.util.OptionalInt mAction;
        private int mChangeTypes;
        public long mLastEventTimeMillis;
        public java.lang.StackTraceElement[] mOrigin;
        public android.view.View mSource;

        private SendWindowContentChangedAccessibilityEvent() {
            this.mChangeTypes = 0;
            this.mAction = java.util.OptionalInt.empty();
        }

        @Override // java.lang.Runnable
        public void run() {
            android.view.View view = this.mSource;
            this.mSource = null;
            if (view == null) {
                android.util.Log.e(android.view.ViewRootImpl.TAG, "Accessibility content change has no source");
                return;
            }
            if (android.view.ViewRootImpl.this.mAccessibilityManager.isEnabled()) {
                this.mLastEventTimeMillis = android.os.SystemClock.uptimeMillis();
                android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain();
                obtain.setEventType(2048);
                obtain.setContentChangeTypes(this.mChangeTypes);
                if (this.mAction.isPresent()) {
                    obtain.setAction(this.mAction.getAsInt());
                }
                view.sendAccessibilityEventUnchecked(obtain);
            } else {
                this.mLastEventTimeMillis = 0L;
            }
            view.resetSubtreeAccessibilityStateChanged();
            this.mChangeTypes = 0;
            this.mAction = java.util.OptionalInt.empty();
        }

        public void runOrPost(android.view.View view, int i) {
            if (android.view.ViewRootImpl.this.mHandler.getLooper() != android.os.Looper.myLooper()) {
                android.util.Log.e(android.view.ViewRootImpl.TAG, "Accessibility content change on non-UI thread. Future Android versions will throw an exception.", new android.view.ViewRootImpl.CalledFromWrongThreadException("Only the original thread that created a view hierarchy can touch its views."));
                android.view.ViewRootImpl.this.mHandler.removeCallbacks(this);
                if (this.mSource != null) {
                    run();
                }
            }
            if (!canContinueThrottle(view, i)) {
                removeCallbacksAndRun();
            }
            if (this.mSource != null) {
                if (android.view.accessibility.Flags.fixMergedContentChangeEvent()) {
                    android.view.View commonPredecessor = android.view.ViewRootImpl.this.getCommonPredecessor(this.mSource, view);
                    if (commonPredecessor != null) {
                        commonPredecessor = commonPredecessor.getSelfOrParentImportantForA11y();
                    }
                    if (commonPredecessor != null) {
                        view = commonPredecessor;
                    }
                    this.mChangeTypes = i | this.mChangeTypes;
                    if (this.mSource != view) {
                        this.mChangeTypes |= 1;
                        this.mSource = view;
                    }
                } else {
                    android.view.View commonPredecessor2 = android.view.ViewRootImpl.this.getCommonPredecessor(this.mSource, view);
                    if (commonPredecessor2 != null) {
                        commonPredecessor2 = commonPredecessor2.getSelfOrParentImportantForA11y();
                    }
                    if (commonPredecessor2 != null) {
                        view = commonPredecessor2;
                    }
                    this.mSource = view;
                    this.mChangeTypes |= i;
                }
                int performingAction = android.view.ViewRootImpl.this.mAccessibilityManager.getPerformingAction();
                if (performingAction != 0) {
                    if (this.mAction.isEmpty()) {
                        this.mAction = java.util.OptionalInt.of(performingAction);
                        return;
                    } else {
                        if (this.mAction.getAsInt() != performingAction) {
                            this.mAction = java.util.OptionalInt.of(0);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            this.mSource = view;
            this.mChangeTypes = i;
            if (android.view.ViewRootImpl.this.mAccessibilityManager.getPerformingAction() != 0) {
                this.mAction = java.util.OptionalInt.of(android.view.ViewRootImpl.this.mAccessibilityManager.getPerformingAction());
            }
            long uptimeMillis = android.os.SystemClock.uptimeMillis() - this.mLastEventTimeMillis;
            long sendRecurringAccessibilityEventsInterval = android.view.ViewConfiguration.getSendRecurringAccessibilityEventsInterval();
            if (uptimeMillis >= sendRecurringAccessibilityEventsInterval) {
                removeCallbacksAndRun();
            } else {
                android.view.ViewRootImpl.this.mHandler.postDelayed(this, sendRecurringAccessibilityEventsInterval - uptimeMillis);
            }
        }

        public void removeCallbacksAndRun() {
            android.view.ViewRootImpl.this.mHandler.removeCallbacks(this);
            run();
        }

        private boolean canContinueThrottle(android.view.View view, int i) {
            if (!android.view.accessibility.Flags.reduceWindowContentChangedEventThrottle() || this.mSource == null || this.mSource == view) {
                return true;
            }
            return i == 1 && this.mChangeTypes == 1;
        }
    }

    private static class UnhandledKeyManager {
        private final android.util.SparseArray<java.lang.ref.WeakReference<android.view.View>> mCapturedKeys;
        private java.lang.ref.WeakReference<android.view.View> mCurrentReceiver;
        private boolean mDispatched;

        private UnhandledKeyManager() {
            this.mDispatched = true;
            this.mCapturedKeys = new android.util.SparseArray<>();
            this.mCurrentReceiver = null;
        }

        boolean dispatch(android.view.View view, android.view.KeyEvent keyEvent) {
            if (this.mDispatched) {
                return false;
            }
            try {
                android.os.Trace.traceBegin(8L, "UnhandledKeyEvent dispatch");
                this.mDispatched = true;
                android.view.View dispatchUnhandledKeyEvent = view.dispatchUnhandledKeyEvent(keyEvent);
                if (keyEvent.getAction() == 0) {
                    int keyCode = keyEvent.getKeyCode();
                    if (dispatchUnhandledKeyEvent != null && !android.view.KeyEvent.isModifierKey(keyCode)) {
                        this.mCapturedKeys.put(keyCode, new java.lang.ref.WeakReference<>(dispatchUnhandledKeyEvent));
                    }
                }
                return dispatchUnhandledKeyEvent != null;
            } finally {
                android.os.Trace.traceEnd(8L);
            }
        }

        void preDispatch(android.view.KeyEvent keyEvent) {
            int indexOfKey;
            this.mCurrentReceiver = null;
            if (keyEvent.getAction() == 1 && (indexOfKey = this.mCapturedKeys.indexOfKey(keyEvent.getKeyCode())) >= 0) {
                this.mCurrentReceiver = this.mCapturedKeys.valueAt(indexOfKey);
                this.mCapturedKeys.removeAt(indexOfKey);
            }
        }

        boolean preViewDispatch(android.view.KeyEvent keyEvent) {
            this.mDispatched = false;
            if (this.mCurrentReceiver == null) {
                this.mCurrentReceiver = this.mCapturedKeys.get(keyEvent.getKeyCode());
            }
            if (this.mCurrentReceiver == null) {
                return false;
            }
            android.view.View view = this.mCurrentReceiver.get();
            if (keyEvent.getAction() == 1) {
                this.mCurrentReceiver = null;
            }
            if (view != null && view.isAttachedToWindow()) {
                view.onUnhandledKeyEvent(keyEvent);
            }
            return true;
        }
    }

    public void setDisplayDecoration(boolean z) {
        if (z == this.mDisplayDecorationCached) {
            return;
        }
        this.mDisplayDecorationCached = z;
        if (this.mSurfaceControl.isValid()) {
            updateDisplayDecoration();
        }
    }

    private void updateDisplayDecoration() {
        this.mTransaction.setDisplayDecoration(this.mSurfaceControl, this.mDisplayDecorationCached).apply();
    }

    public void dispatchBlurRegions(float[][] fArr, long j) {
        android.view.SurfaceControl surfaceControl = getSurfaceControl();
        if (!surfaceControl.isValid()) {
            return;
        }
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        transaction.setBlurRegions(surfaceControl, fArr);
        if (this.mBlastBufferQueue != null) {
            this.mBlastBufferQueue.mergeWithNextTransaction(transaction, j);
        }
    }

    public com.android.internal.graphics.drawable.BackgroundBlurDrawable createBackgroundBlurDrawable() {
        return this.mBlurRegionAggregator.createBackgroundBlurDrawable(this.mContext);
    }

    @Override // android.view.ViewParent
    public void onDescendantUnbufferedRequested() {
        this.mUnbufferedInputSource = this.mView.mUnbufferedInputSource;
    }

    int getSurfaceSequenceId() {
        return this.mSurfaceSequenceId;
    }

    public void mergeWithNextTransaction(android.view.SurfaceControl.Transaction transaction, long j) {
        if (this.mBlastBufferQueue != null) {
            this.mBlastBufferQueue.mergeWithNextTransaction(transaction, j);
        } else {
            transaction.apply();
        }
    }

    @Override // android.view.AttachedSurfaceControl
    public android.view.SurfaceControl.Transaction buildReparentTransaction(android.view.SurfaceControl surfaceControl) {
        if (this.mSurfaceControl.isValid()) {
            android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
            return transaction.reparent(surfaceControl, updateAndGetBoundsLayer(transaction));
        }
        return null;
    }

    @Override // android.view.AttachedSurfaceControl
    public boolean applyTransactionOnDraw(android.view.SurfaceControl.Transaction transaction) {
        if (this.mRemoved || !isHardwareEnabled()) {
            logAndTrace("applyTransactionOnDraw applyImmediately");
            transaction.apply();
        } else {
            android.os.Trace.instant(8L, "applyTransactionOnDraw-" + this.mTag);
            this.mPendingTransaction.merge(transaction);
            this.mHasPendingTransactions = true;
        }
        return true;
    }

    @Override // android.view.AttachedSurfaceControl
    public int getBufferTransformHint() {
        if (com.android.window.flags.Flags.enableBufferTransformHintFromDisplay()) {
            return this.mPreviousTransformHint;
        }
        if (this.mSurfaceControl.isValid()) {
            return this.mSurfaceControl.getTransformHint();
        }
        return 0;
    }

    @Override // android.view.AttachedSurfaceControl
    public void addOnBufferTransformHintChangedListener(android.view.AttachedSurfaceControl.OnBufferTransformHintChangedListener onBufferTransformHintChangedListener) {
        java.util.Objects.requireNonNull(onBufferTransformHintChangedListener);
        if (this.mTransformHintListeners.contains(onBufferTransformHintChangedListener)) {
            throw new java.lang.IllegalArgumentException("attempt to call addOnBufferTransformHintChangedListener() with a previously registered listener");
        }
        this.mTransformHintListeners.add(onBufferTransformHintChangedListener);
    }

    @Override // android.view.AttachedSurfaceControl
    public void removeOnBufferTransformHintChangedListener(android.view.AttachedSurfaceControl.OnBufferTransformHintChangedListener onBufferTransformHintChangedListener) {
        java.util.Objects.requireNonNull(onBufferTransformHintChangedListener);
        this.mTransformHintListeners.remove(onBufferTransformHintChangedListener);
    }

    private void dispatchTransformHintChanged(int i) {
        if (this.mTransformHintListeners.isEmpty()) {
            return;
        }
        java.util.ArrayList arrayList = (java.util.ArrayList) this.mTransformHintListeners.clone();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ((android.view.AttachedSurfaceControl.OnBufferTransformHintChangedListener) arrayList.get(i2)).onBufferTransformHintChanged(i);
        }
    }

    public void requestCompatCameraControl(boolean z, boolean z2, android.app.ICompatCameraControlCallback iCompatCameraControlCallback) {
        this.mActivityConfigCallback.requestCompatCameraControl(z, z2, iCompatCameraControlCallback);
    }

    boolean wasRelayoutRequested() {
        return this.mRelayoutRequested;
    }

    void forceWmRelayout() {
        this.mForceNextWindowRelayout = true;
        scheduleTraversals();
    }

    public android.window.WindowOnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return this.mOnBackInvokedDispatcher;
    }

    @Override // android.view.ViewParent
    public android.window.OnBackInvokedDispatcher findOnBackInvokedDispatcherForChild(android.view.View view, android.view.View view2) {
        return getOnBackInvokedDispatcher();
    }

    private void registerBackCallbackOnWindow() {
        this.mOnBackInvokedDispatcher.attachToWindow(this.mWindowSession, this.mWindow);
    }

    private void sendBackKeyEvent(int i) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        enqueueInputEvent(new android.view.KeyEvent(uptimeMillis, uptimeMillis, i, 4, 0, 0, -1, 0, 72, 257), null, 0, true);
    }

    private void registerCompatOnBackInvokedCallback() {
        this.mCompatOnBackInvokedCallback = new android.window.CompatOnBackInvokedCallback() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda11
            @Override // android.window.CompatOnBackInvokedCallback, android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                android.view.ViewRootImpl.this.lambda$registerCompatOnBackInvokedCallback$10();
            }
        };
        if (this.mOnBackInvokedDispatcher.hasImeOnBackInvokedDispatcher()) {
            android.util.Log.d(TAG, "Skip registering CompatOnBackInvokedCallback on IME dispatcher");
        } else {
            this.mOnBackInvokedDispatcher.registerOnBackInvokedCallback(0, this.mCompatOnBackInvokedCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerCompatOnBackInvokedCallback$10() {
        try {
            processingBackKey(true);
            sendBackKeyEvent(0);
            sendBackKeyEvent(1);
        } finally {
            processingBackKey(false);
        }
    }

    @Override // android.view.AttachedSurfaceControl
    public void setTouchableRegion(android.graphics.Region region) {
        if (region != null) {
            this.mTouchableRegion = new android.graphics.Region(region);
        } else {
            this.mTouchableRegion = null;
        }
        this.mLastGivenInsets.reset();
        requestLayout();
    }

    android.view.IWindowSession getWindowSession() {
        return this.mWindowSession;
    }

    private void registerCallbacksForSync(boolean z, android.window.SurfaceSyncGroup surfaceSyncGroup) {
        android.view.SurfaceControl.Transaction transaction;
        if (!isHardwareEnabled()) {
            return;
        }
        if (this.mHasPendingTransactions) {
            transaction = new android.view.SurfaceControl.Transaction();
            transaction.merge(this.mPendingTransaction);
        } else {
            transaction = null;
        }
        this.mAttachInfo.mThreadedRenderer.registerRtFrameCallback(new android.view.ViewRootImpl.AnonymousClass9(transaction, surfaceSyncGroup, z));
    }

    /* renamed from: android.view.ViewRootImpl$9, reason: invalid class name */
    class AnonymousClass9 implements android.graphics.HardwareRenderer.FrameDrawingCallback {
        final /* synthetic */ android.window.SurfaceSyncGroup val$surfaceSyncGroup;
        final /* synthetic */ boolean val$syncBuffer;
        final /* synthetic */ android.view.SurfaceControl.Transaction val$t;

        AnonymousClass9(android.view.SurfaceControl.Transaction transaction, android.window.SurfaceSyncGroup surfaceSyncGroup, boolean z) {
            this.val$t = transaction;
            this.val$surfaceSyncGroup = surfaceSyncGroup;
            this.val$syncBuffer = z;
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public void onFrameDraw(long j) {
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public android.graphics.HardwareRenderer.FrameCommitCallback onFrameDraw(int i, final long j) {
            if (this.val$t != null) {
                android.view.ViewRootImpl.this.mergeWithNextTransaction(this.val$t, j);
            }
            if ((i & 6) != 0) {
                this.val$surfaceSyncGroup.addTransaction(android.view.ViewRootImpl.this.mBlastBufferQueue.gatherPendingTransactions(j));
                this.val$surfaceSyncGroup.markSyncReady();
                return null;
            }
            if (this.val$syncBuffer) {
                android.graphics.BLASTBufferQueue bLASTBufferQueue = android.view.ViewRootImpl.this.mBlastBufferQueue;
                final android.window.SurfaceSyncGroup surfaceSyncGroup = this.val$surfaceSyncGroup;
                if (!bLASTBufferQueue.syncNextTransaction(new java.util.function.Consumer() { // from class: android.view.ViewRootImpl$9$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.view.ViewRootImpl.AnonymousClass9.this.lambda$onFrameDraw$2(surfaceSyncGroup, (android.view.SurfaceControl.Transaction) obj);
                    }
                })) {
                    android.util.Log.w(android.view.ViewRootImpl.this.mTag, "Unable to syncNextTransaction. Possibly something else is trying to sync?");
                    this.val$surfaceSyncGroup.markSyncReady();
                }
            }
            final android.window.SurfaceSyncGroup surfaceSyncGroup2 = this.val$surfaceSyncGroup;
            final boolean z = this.val$syncBuffer;
            return new android.graphics.HardwareRenderer.FrameCommitCallback() { // from class: android.view.ViewRootImpl$9$$ExternalSyntheticLambda3
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z2) {
                    android.view.ViewRootImpl.AnonymousClass9.this.lambda$onFrameDraw$3(j, surfaceSyncGroup2, z, z2);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$0() {
            android.util.Log.e(android.view.ViewRootImpl.this.mTag, "Failed to submit the sync transaction after 4s. Likely to ANR soon");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$2(android.window.SurfaceSyncGroup surfaceSyncGroup, android.view.SurfaceControl.Transaction transaction) {
            final java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.view.ViewRootImpl$9$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.ViewRootImpl.AnonymousClass9.this.lambda$onFrameDraw$0();
                }
            };
            android.view.ViewRootImpl.this.mHandler.postDelayed(runnable, android.os.Build.HW_TIMEOUT_MULTIPLIER * 4000);
            transaction.addTransactionCommittedListener(android.view.ViewRootImpl.this.mSimpleExecutor, new android.view.SurfaceControl.TransactionCommittedListener() { // from class: android.view.ViewRootImpl$9$$ExternalSyntheticLambda1
                @Override // android.view.SurfaceControl.TransactionCommittedListener
                public final void onTransactionCommitted() {
                    android.view.ViewRootImpl.AnonymousClass9.this.lambda$onFrameDraw$1(runnable);
                }
            });
            surfaceSyncGroup.addTransaction(transaction);
            surfaceSyncGroup.markSyncReady();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$1(java.lang.Runnable runnable) {
            android.view.ViewRootImpl.this.mHandler.removeCallbacks(runnable);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$3(long j, android.window.SurfaceSyncGroup surfaceSyncGroup, boolean z, boolean z2) {
            if (!z2) {
                android.view.ViewRootImpl.this.mBlastBufferQueue.clearSyncTransaction();
                surfaceSyncGroup.addTransaction(android.view.ViewRootImpl.this.mBlastBufferQueue.gatherPendingTransactions(j));
                surfaceSyncGroup.markSyncReady();
            } else if (!z) {
                surfaceSyncGroup.markSyncReady();
            }
        }
    }

    private void safeguardOverlappingSyncs(final android.window.SurfaceSyncGroup surfaceSyncGroup) {
        final android.window.SurfaceSyncGroup surfaceSyncGroup2 = new android.window.SurfaceSyncGroup("Safeguard-" + this.mTag);
        surfaceSyncGroup2.toggleTimeout(false);
        synchronized (this.mPreviousSyncSafeguardLock) {
            if (this.mPreviousSyncSafeguard != null) {
                surfaceSyncGroup.add(this.mPreviousSyncSafeguard, (java.lang.Runnable) null);
                surfaceSyncGroup.toggleTimeout(false);
                this.mPreviousSyncSafeguard.addSyncCompleteCallback(this.mSimpleExecutor, new java.lang.Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.window.SurfaceSyncGroup.this.toggleTimeout(true);
                    }
                });
            }
            this.mPreviousSyncSafeguard = surfaceSyncGroup2;
        }
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        transaction.addTransactionCommittedListener(this.mSimpleExecutor, new android.view.SurfaceControl.TransactionCommittedListener() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda8
            @Override // android.view.SurfaceControl.TransactionCommittedListener
            public final void onTransactionCommitted() {
                android.view.ViewRootImpl.this.lambda$safeguardOverlappingSyncs$12(surfaceSyncGroup2);
            }
        });
        surfaceSyncGroup.addTransaction(transaction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$safeguardOverlappingSyncs$12(android.window.SurfaceSyncGroup surfaceSyncGroup) {
        surfaceSyncGroup.markSyncReady();
        synchronized (this.mPreviousSyncSafeguardLock) {
            if (this.mPreviousSyncSafeguard == surfaceSyncGroup) {
                this.mPreviousSyncSafeguard = null;
            }
        }
    }

    @Override // android.view.AttachedSurfaceControl
    public android.window.SurfaceSyncGroup getOrCreateSurfaceSyncGroup() {
        boolean z;
        if (this.mActiveSurfaceSyncGroup != null) {
            z = false;
        } else {
            this.mActiveSurfaceSyncGroup = new android.window.SurfaceSyncGroup(this.mTag);
            this.mActiveSurfaceSyncGroup.setAddedToSyncListener(new java.lang.Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.ViewRootImpl.this.lambda$getOrCreateSurfaceSyncGroup$14();
                }
            });
            z = true;
        }
        android.os.Trace.instant(8L, "getOrCreateSurfaceSyncGroup isNew=" + z + " " + this.mTag);
        this.mNumPausedForSync++;
        this.mHandler.removeMessages(37);
        this.mHandler.sendEmptyMessageDelayed(37, android.os.Build.HW_TIMEOUT_MULTIPLIER * 1000);
        return this.mActiveSurfaceSyncGroup;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOrCreateSurfaceSyncGroup$14() {
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.view.ViewRootImpl.this.lambda$getOrCreateSurfaceSyncGroup$13();
            }
        };
        if (java.lang.Thread.currentThread() == this.mThread) {
            runnable.run();
        } else {
            this.mHandler.post(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOrCreateSurfaceSyncGroup$13() {
        if (this.mNumPausedForSync > 0) {
            this.mNumPausedForSync--;
        }
        if (this.mNumPausedForSync == 0) {
            this.mHandler.removeMessages(37);
            if (!this.mIsInTraversal) {
                scheduleTraversals();
            }
        }
    }

    private void updateSyncInProgressCount(android.window.SurfaceSyncGroup surfaceSyncGroup) {
        if (this.mAttachInfo.mThreadedRenderer == null) {
            return;
        }
        synchronized (sSyncProgressLock) {
            int i = sNumSyncsInProgress;
            sNumSyncsInProgress = i + 1;
            if (i == 0) {
                android.graphics.HardwareRenderer.setRtAnimationsEnabled(false);
            }
        }
        surfaceSyncGroup.addSyncCompleteCallback(this.mSimpleExecutor, new java.lang.Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                android.view.ViewRootImpl.lambda$updateSyncInProgressCount$15();
            }
        });
    }

    static /* synthetic */ void lambda$updateSyncInProgressCount$15() {
        synchronized (sSyncProgressLock) {
            int i = sNumSyncsInProgress - 1;
            sNumSyncsInProgress = i;
            if (i == 0) {
                android.graphics.HardwareRenderer.setRtAnimationsEnabled(true);
            }
        }
    }

    void addToSync(android.window.SurfaceSyncGroup surfaceSyncGroup) {
        if (this.mActiveSurfaceSyncGroup == null) {
            return;
        }
        this.mActiveSurfaceSyncGroup.add(surfaceSyncGroup, (java.lang.Runnable) null);
    }

    @Override // android.view.AttachedSurfaceControl
    public void setChildBoundingInsets(android.graphics.Rect rect) {
        if (rect.left < 0 || rect.top < 0 || rect.right < 0 || rect.bottom < 0) {
            throw new java.lang.IllegalArgumentException("Negative insets passed to setChildBoundingInsets.");
        }
        this.mChildBoundingInsets.set(rect);
        this.mChildBoundingInsetsChanged = true;
        scheduleTraversals();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logAndTrace(java.lang.String str) {
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.instant(8L, this.mTag + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + str);
        }
        android.util.EventLog.writeEvent(60004, this.mTag, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPreferredFrameRateCategory(int i) {
        int i2;
        int max;
        if (!shouldSetFrameRateCategory()) {
            return;
        }
        int i3 = 5;
        if (!this.mIsFrameRateConflicted) {
            i2 = 1;
        } else {
            i2 = this.mPreferredFrameRate > 60.0f ? 5 : 3;
        }
        if (this.mIsTouchBoosting) {
            max = 4;
        } else {
            max = java.lang.Math.max(i, i2);
        }
        if (!this.mIsFrameRateBoosting && !this.mInsetsAnimationRunning) {
            i3 = max;
        }
        try {
            try {
                if (this.mLastPreferredFrameRateCategory != i3) {
                    if (android.os.Trace.isTagEnabled(8L)) {
                        android.os.Trace.traceBegin(8L, "ViewRootImpl#setFrameRateCategory " + i3);
                    }
                    this.mFrameRateTransaction.setFrameRateCategory(this.mSurfaceControl, i3, false).applyAsyncUnsafe();
                    this.mLastPreferredFrameRateCategory = i3;
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(this.mTag, "Unable to set frame rate category", e);
            }
        } finally {
            android.os.Trace.traceEnd(8L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPreferredFrameRate(float f) {
        if (!shouldSetFrameRate()) {
            return;
        }
        try {
            try {
                if (this.mLastPreferredFrameRate != f && f >= 0.0f) {
                    if (android.os.Trace.isTagEnabled(8L)) {
                        android.os.Trace.traceBegin(8L, "ViewRootImpl#setFrameRate " + f + " compatibility " + this.mFrameRateCompatibility);
                    }
                    this.mFrameRateTransaction.setFrameRate(this.mSurfaceControl, f, this.mFrameRateCompatibility).applyAsyncUnsafe();
                    this.mLastPreferredFrameRate = f;
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(this.mTag, "Unable to set frame rate", e);
            }
        } finally {
            android.os.Trace.traceEnd(8L);
        }
    }

    private void sendDelayedEmptyMessage(int i, int i2) {
        this.mHandler.removeMessages(i);
        this.mHandler.sendEmptyMessageDelayed(i, i2);
    }

    private boolean shouldSetFrameRateCategory() {
        return this.mSurface.isValid() && shouldEnableDvrr();
    }

    private boolean shouldSetFrameRate() {
        return this.mSurface.isValid() && this.mPreferredFrameRate >= 0.0f && shouldEnableDvrr() && !this.mIsFrameRateConflicted;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldTouchBoost(int i, int i2) {
        return (i == 0 || i == 2 || i == 1) && !(i2 == 2011 && sToolkitFrameRateTypingReadOnlyFlagValue) && shouldEnableDvrr() && getFrameRateBoostOnTouchEnabled();
    }

    public void votePreferredFrameRateCategory(int i) {
        if (i == 5) {
            this.mFrameRateCategoryHighCount = 5;
        } else if (i == 4) {
            this.mFrameRateCategoryHighHintCount = 5;
        } else if (i == 3) {
            this.mFrameRateCategoryNormalCount = 5;
        } else if (i == 2) {
            this.mFrameRateCategoryLowCount = 5;
        }
        if (this.mFrameRateCategoryHighCount > 0) {
            this.mPreferredFrameRateCategory = 5;
        } else if (this.mFrameRateCategoryHighHintCount > 0) {
            this.mPreferredFrameRateCategory = 4;
        } else if (this.mFrameRateCategoryNormalCount > 0) {
            this.mPreferredFrameRateCategory = 3;
        } else if (this.mFrameRateCategoryLowCount > 0) {
            this.mPreferredFrameRateCategory = 2;
        }
        this.mHasInvalidation = true;
        checkIdleness();
    }

    public void votePreferredFrameRate(float f, int i) {
        if (f <= 0.0f) {
            return;
        }
        if (this.mPreferredFrameRate > 0.0f && this.mPreferredFrameRate % f != 0.0f && f % this.mPreferredFrameRate != 0.0f) {
            this.mIsFrameRateConflicted = true;
            this.mFrameRateCompatibility = 1;
        }
        if (f > this.mPreferredFrameRate) {
            this.mFrameRateCompatibility = i;
        }
        this.mPreferredFrameRate = java.lang.Math.max(this.mPreferredFrameRate, f);
        this.mHasInvalidation = true;
        if (!this.mIsFrameRateConflicted) {
            this.mHandler.removeMessages(42);
            this.mHandler.sendEmptyMessageDelayed(42, 100L);
        }
        checkIdleness();
    }

    public int getPreferredFrameRateCategory() {
        return this.mPreferredFrameRateCategory;
    }

    public int getLastPreferredFrameRateCategory() {
        return this.mLastPreferredFrameRateCategory;
    }

    public float getPreferredFrameRate() {
        return this.mPreferredFrameRate >= 0.0f ? this.mPreferredFrameRate : this.mLastPreferredFrameRate;
    }

    public int getFrameRateCompatibility() {
        return this.mFrameRateCompatibility;
    }

    public boolean getIsFrameRateBoosting() {
        return this.mIsFrameRateBoosting;
    }

    public boolean getFrameRateBoostOnTouchEnabled() {
        return this.mWindowAttributes.getFrameRateBoostOnTouchEnabled();
    }

    private void boostFrameRate(int i) {
        this.mIsFrameRateBoosting = true;
        setPreferredFrameRateCategory(this.mPreferredFrameRateCategory);
        this.mHandler.removeMessages(39);
        this.mHandler.sendEmptyMessageDelayed(39, i);
    }

    void setBackKeyCallbackForWindowlessWindow(java.util.function.Predicate<android.view.KeyEvent> predicate) {
        this.mWindowlessBackKeyCallback = predicate;
    }

    void recordViewPercentage(float f) {
        if (android.os.Trace.isEnabled()) {
            this.mLargestChildPercentage = java.lang.Math.max(f, this.mLargestChildPercentage);
        }
    }

    public boolean isFrameRatePowerSavingsBalanced() {
        return this.mIsFrameRatePowerSavingsBalanced;
    }

    public boolean isFrameRateConflicted() {
        return this.mIsFrameRateConflicted;
    }

    public void setFrameRatePowerSavingsBalanced(boolean z) {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            this.mIsFrameRatePowerSavingsBalanced = z;
        }
    }

    private boolean shouldEnableDvrr() {
        return sToolkitSetFrameRateReadOnlyFlagValue && this.mIsFrameRatePowerSavingsBalanced;
    }

    private void checkIdleness() {
        if (!this.mHasIdledMessage) {
            this.mHandler.sendEmptyMessageDelayed(40, 500L);
            this.mHasIdledMessage = true;
        }
    }
}
