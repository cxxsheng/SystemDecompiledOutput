package android.service.wallpaper;

/* loaded from: classes3.dex */
public abstract class WallpaperService extends android.app.Service {
    static final boolean DEBUG = false;
    private static final long DEFAULT_UPDATE_SCREENSHOT_DURATION = 60000;
    private static final long DIMMING_ANIMATION_DURATION_MS = 300;
    private static final int DO_ATTACH = 10;
    private static final int DO_DETACH = 20;
    private static final int DO_IN_AMBIENT_MODE = 50;
    private static final int DO_SET_DESIRED_SIZE = 30;
    private static final int DO_SET_DISPLAY_PADDING = 40;
    private static final int MIN_BITMAP_SCREENSHOT_WIDTH = 64;
    static final float MIN_PAGE_ALLOWED_MARGIN = 0.05f;
    private static final int MSG_REFRESH_VISIBILITY = 10011;
    private static final int MSG_REPORT_SHOWN = 10150;
    private static final int MSG_REQUEST_WALLPAPER_COLORS = 10050;
    private static final int MSG_RESIZE_PREVIEW = 10110;
    private static final int MSG_TOUCH_EVENT = 10040;
    private static final int MSG_UPDATE_DIMMING = 10200;
    private static final int MSG_UPDATE_SCREEN_TURNING_ON = 10170;
    private static final int MSG_UPDATE_SURFACE = 10000;
    private static final int MSG_VISIBILITY_CHANGED = 10010;
    private static final int MSG_WALLPAPER_COMMAND = 10025;
    private static final int MSG_WALLPAPER_FLAGS_CHANGED = 10210;
    private static final int MSG_WALLPAPER_OFFSETS = 10020;
    private static final int MSG_WINDOW_MOVED = 10035;
    private static final int MSG_WINDOW_RESIZED = 10030;
    private static final int MSG_ZOOM = 10100;
    private static final int NOTIFY_COLORS_RATE_LIMIT_MS = 1000;
    private static final long PRESERVE_VISIBLE_TIMEOUT_MS = 1000;
    private static final int PROCESS_LOCAL_COLORS_INTERVAL_MS = 2000;
    public static final java.lang.String SERVICE_INTERFACE = "android.service.wallpaper.WallpaperService";
    public static final java.lang.String SERVICE_META_DATA = "android.service.wallpaper";
    static final java.lang.String TAG = "WallpaperService";
    public static final long WEAROS_WALLPAPER_HANDLES_SCALING = 272527315;
    private final android.util.ArrayMap<android.os.IBinder, android.service.wallpaper.WallpaperService.IWallpaperEngineWrapper> mActiveEngines = new android.util.ArrayMap<>();
    private android.os.Handler mBackgroundHandler;
    private android.os.HandlerThread mBackgroundThread;
    private boolean mIsWearOs;
    private static final android.graphics.RectF LOCAL_COLOR_BOUNDS = new android.graphics.RectF(0.0f, 0.0f, 1.0f, 1.0f);
    private static final boolean ENABLE_WALLPAPER_DIMMING = android.os.SystemProperties.getBoolean("persist.debug.enable_wallpaper_dimming", true);

    public abstract android.service.wallpaper.WallpaperService.Engine onCreateEngine();

    static final class WallpaperCommand {
        java.lang.String action;
        android.os.Bundle extras;
        boolean sync;
        int x;
        int y;
        int z;

        WallpaperCommand() {
        }
    }

    public class Engine {
        android.view.SurfaceControl mBbqSurfaceControl;
        android.graphics.BLASTBufferQueue mBlastBufferQueue;
        com.android.internal.os.HandlerCaller mCaller;
        private final java.util.function.Supplier<java.lang.Long> mClockFunction;
        android.service.wallpaper.IWallpaperConnection mConnection;
        boolean mCreated;
        int mCurHeight;
        int mCurWidth;
        int mCurWindowFlags;
        int mCurWindowPrivateFlags;
        private float mCustomDimAmount;
        private float mDefaultDimAmount;
        boolean mDestroyed;
        final android.graphics.Rect mDispatchedContentInsets;
        android.view.DisplayCutout mDispatchedDisplayCutout;
        final android.graphics.Rect mDispatchedStableInsets;
        private android.view.Display mDisplay;
        private android.content.Context mDisplayContext;
        private final android.hardware.display.DisplayManager.DisplayListener mDisplayListener;
        private int mDisplayState;
        boolean mDrawingAllowed;
        boolean mFixedSizeAllowed;
        int mFormat;
        private boolean mFrozenRequested;
        private final android.os.Handler mHandler;
        int mHeight;
        android.service.wallpaper.WallpaperService.IWallpaperEngineWrapper mIWallpaperEngine;
        boolean mInitializing;
        android.service.wallpaper.WallpaperService.Engine.WallpaperInputEventReceiver mInputEventReceiver;
        final android.view.InsetsState mInsetsState;
        boolean mIsCreating;
        boolean mIsInAmbientMode;
        private boolean mIsScreenTurningOn;
        private long mLastColorInvalidation;
        private long mLastProcessLocalColorsTimestamp;
        private android.graphics.Bitmap mLastScreenshot;
        private final android.graphics.Point mLastSurfaceSize;
        final android.view.WindowManager.LayoutParams mLayout;
        private final android.util.ArraySet<android.graphics.RectF> mLocalColorAreas;
        private final android.util.ArraySet<android.graphics.RectF> mLocalColorsToAdd;
        final java.lang.Object mLock;
        final android.util.MergedConfiguration mMergedConfiguration;
        private final java.lang.Runnable mNotifyColorsChanged;
        boolean mOffsetMessageEnqueued;
        boolean mOffsetsChanged;
        android.view.MotionEvent mPendingMove;
        boolean mPendingSync;
        private float mPendingXOffset;
        private float mPendingXOffsetStep;
        private float mPendingYOffset;
        private float mPendingYOffsetStep;
        private int mPixelCopyCount;
        boolean mPreserveVisible;
        android.graphics.Rect mPreviewSurfacePosition;
        private float mPreviousWallpaperDimAmount;
        private java.util.concurrent.atomic.AtomicBoolean mProcessLocalColorsPending;
        boolean mReportedVisible;
        private boolean mResetWindowPages;
        private android.graphics.Point mScreenshotSize;
        private android.view.SurfaceControl mScreenshotSurfaceControl;
        android.view.IWindowSession mSession;
        boolean mShouldDimByDefault;
        android.view.SurfaceControl mSurfaceControl;
        boolean mSurfaceCreated;
        final com.android.internal.view.BaseSurfaceHolder mSurfaceHolder;
        private final android.graphics.Point mSurfaceSize;
        final android.os.Bundle mSyncSeqIdBundle;
        final android.view.InsetsSourceControl.Array mTempControls;
        private final android.graphics.Matrix mTmpMatrix;
        private final float[] mTmpValues;
        int mType;
        boolean mVisible;
        private float mWallpaperDimAmount;
        int mWidth;
        final android.window.ClientWindowFrames mWinFrames;
        final com.android.internal.view.BaseIWindow mWindow;
        int mWindowFlags;
        private android.service.wallpaper.EngineWindowPage[] mWindowPages;
        int mWindowPrivateFlags;
        android.os.IBinder mWindowToken;
        float mZoom;

        final class WallpaperInputEventReceiver extends android.view.InputEventReceiver {
            public WallpaperInputEventReceiver(android.view.InputChannel inputChannel, android.os.Looper looper) {
                super(inputChannel, looper);
            }

            @Override // android.view.InputEventReceiver
            public void onInputEvent(android.view.InputEvent inputEvent) {
                boolean z = false;
                try {
                    if ((inputEvent instanceof android.view.MotionEvent) && (inputEvent.getSource() & 2) != 0) {
                        android.service.wallpaper.WallpaperService.Engine.this.dispatchPointer(android.view.MotionEvent.obtainNoHistory((android.view.MotionEvent) inputEvent));
                        z = true;
                    }
                } finally {
                    finishInputEvent(inputEvent, false);
                }
            }
        }

        public Engine(android.service.wallpaper.WallpaperService wallpaperService) {
            this(new java.util.function.Supplier() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    return java.lang.Long.valueOf(android.os.SystemClock.elapsedRealtime());
                }
            }, android.os.Handler.getMain());
        }

        public Engine(java.util.function.Supplier<java.lang.Long> supplier, android.os.Handler handler) {
            this.mInitializing = true;
            this.mFrozenRequested = false;
            this.mZoom = 0.0f;
            this.mWindowFlags = 16;
            this.mWindowPrivateFlags = 4;
            this.mCurWindowFlags = this.mWindowFlags;
            this.mCurWindowPrivateFlags = this.mWindowPrivateFlags;
            this.mWinFrames = new android.window.ClientWindowFrames();
            this.mDispatchedContentInsets = new android.graphics.Rect();
            this.mDispatchedStableInsets = new android.graphics.Rect();
            this.mDispatchedDisplayCutout = android.view.DisplayCutout.NO_CUTOUT;
            this.mInsetsState = new android.view.InsetsState();
            this.mTempControls = new android.view.InsetsSourceControl.Array();
            this.mMergedConfiguration = new android.util.MergedConfiguration();
            this.mSyncSeqIdBundle = new android.os.Bundle();
            this.mSurfaceSize = new android.graphics.Point();
            this.mLastSurfaceSize = new android.graphics.Point();
            this.mTmpMatrix = new android.graphics.Matrix();
            this.mTmpValues = new float[9];
            this.mLayout = new android.view.WindowManager.LayoutParams();
            this.mLock = new java.lang.Object();
            this.mLocalColorAreas = new android.util.ArraySet<>(4);
            this.mLocalColorsToAdd = new android.util.ArraySet<>(4);
            this.mProcessLocalColorsPending = new java.util.concurrent.atomic.AtomicBoolean(false);
            this.mPixelCopyCount = 0;
            this.mWindowPages = new android.service.wallpaper.EngineWindowPage[0];
            this.mNotifyColorsChanged = new java.lang.Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.wallpaper.WallpaperService.Engine.this.notifyColorsChanged();
                }
            };
            this.mCustomDimAmount = 0.0f;
            this.mWallpaperDimAmount = 0.0f;
            this.mPreviousWallpaperDimAmount = this.mWallpaperDimAmount;
            this.mDefaultDimAmount = android.service.wallpaper.WallpaperService.MIN_PAGE_ALLOWED_MARGIN;
            this.mSurfaceControl = new android.view.SurfaceControl();
            this.mScreenshotSize = new android.graphics.Point();
            this.mSurfaceHolder = new com.android.internal.view.BaseSurfaceHolder() { // from class: android.service.wallpaper.WallpaperService.Engine.1
                {
                    this.mRequestedFormat = 2;
                }

                @Override // com.android.internal.view.BaseSurfaceHolder
                public boolean onAllowLockCanvas() {
                    return android.service.wallpaper.WallpaperService.Engine.this.mDrawingAllowed;
                }

                @Override // com.android.internal.view.BaseSurfaceHolder
                public void onRelayoutContainer() {
                    android.service.wallpaper.WallpaperService.Engine.this.mCaller.sendMessage(android.service.wallpaper.WallpaperService.Engine.this.mCaller.obtainMessage(10000));
                }

                @Override // com.android.internal.view.BaseSurfaceHolder
                public void onUpdateSurface() {
                    android.service.wallpaper.WallpaperService.Engine.this.mCaller.sendMessage(android.service.wallpaper.WallpaperService.Engine.this.mCaller.obtainMessage(10000));
                }

                @Override // android.view.SurfaceHolder
                public boolean isCreating() {
                    return android.service.wallpaper.WallpaperService.Engine.this.mIsCreating;
                }

                @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
                public void setFixedSize(int i, int i2) {
                    if (!android.service.wallpaper.WallpaperService.Engine.this.mFixedSizeAllowed && !android.service.wallpaper.WallpaperService.Engine.this.mIWallpaperEngine.mIsPreview) {
                        throw new java.lang.UnsupportedOperationException("Wallpapers currently only support sizing from layout");
                    }
                    super.setFixedSize(i, i2);
                }

                @Override // android.view.SurfaceHolder
                public void setKeepScreenOn(boolean z) {
                    throw new java.lang.UnsupportedOperationException("Wallpapers do not support keep screen on");
                }

                private void prepareToDraw() {
                    if (android.service.wallpaper.WallpaperService.Engine.this.mDisplayState == 3 || android.service.wallpaper.WallpaperService.Engine.this.mDisplayState == 4) {
                        try {
                            android.service.wallpaper.WallpaperService.Engine.this.mSession.pokeDrawLock(android.service.wallpaper.WallpaperService.Engine.this.mWindow);
                        } catch (android.os.RemoteException e) {
                        }
                    }
                }

                @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
                public android.graphics.Canvas lockCanvas() {
                    prepareToDraw();
                    return super.lockCanvas();
                }

                @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
                public android.graphics.Canvas lockCanvas(android.graphics.Rect rect) {
                    prepareToDraw();
                    return super.lockCanvas(rect);
                }

                @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
                public android.graphics.Canvas lockHardwareCanvas() {
                    prepareToDraw();
                    return super.lockHardwareCanvas();
                }
            };
            this.mWindow = new com.android.internal.view.BaseIWindow() { // from class: android.service.wallpaper.WallpaperService.Engine.2
                @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
                public void resized(android.window.ClientWindowFrames clientWindowFrames, boolean z, android.util.MergedConfiguration mergedConfiguration, android.view.InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4) {
                    android.os.Message obtainMessageIO = android.service.wallpaper.WallpaperService.Engine.this.mCaller.obtainMessageIO(10030, z ? 1 : 0, mergedConfiguration);
                    android.service.wallpaper.WallpaperService.Engine.this.mIWallpaperEngine.mPendingResizeCount.incrementAndGet();
                    android.service.wallpaper.WallpaperService.Engine.this.mCaller.sendMessage(obtainMessageIO);
                }

                @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
                public void moved(int i, int i2) {
                    android.service.wallpaper.WallpaperService.Engine.this.mCaller.sendMessage(android.service.wallpaper.WallpaperService.Engine.this.mCaller.obtainMessageII(10035, i, i2));
                }

                @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
                public void dispatchAppVisibility(boolean z) {
                    if (!android.service.wallpaper.WallpaperService.Engine.this.mIWallpaperEngine.mIsPreview) {
                        android.service.wallpaper.WallpaperService.Engine.this.mCaller.sendMessage(android.service.wallpaper.WallpaperService.Engine.this.mCaller.obtainMessageI(10010, z ? 1 : 0));
                    }
                }

                @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
                public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) {
                    synchronized (android.service.wallpaper.WallpaperService.Engine.this.mLock) {
                        android.service.wallpaper.WallpaperService.Engine.this.mPendingXOffset = f;
                        android.service.wallpaper.WallpaperService.Engine.this.mPendingYOffset = f2;
                        android.service.wallpaper.WallpaperService.Engine.this.mPendingXOffsetStep = f3;
                        android.service.wallpaper.WallpaperService.Engine.this.mPendingYOffsetStep = f4;
                        if (z) {
                            android.service.wallpaper.WallpaperService.Engine.this.mPendingSync = true;
                        }
                        if (!android.service.wallpaper.WallpaperService.Engine.this.mOffsetMessageEnqueued) {
                            android.service.wallpaper.WallpaperService.Engine.this.mOffsetMessageEnqueued = true;
                            android.service.wallpaper.WallpaperService.Engine.this.mCaller.sendMessage(android.service.wallpaper.WallpaperService.Engine.this.mCaller.obtainMessage(10020));
                        }
                        android.service.wallpaper.WallpaperService.Engine.this.mCaller.sendMessage(android.service.wallpaper.WallpaperService.Engine.this.mCaller.obtainMessageI(10100, java.lang.Float.floatToIntBits(f5)));
                    }
                }

                @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
                public void dispatchWallpaperCommand(java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) {
                    synchronized (android.service.wallpaper.WallpaperService.Engine.this.mLock) {
                        android.service.wallpaper.WallpaperService.WallpaperCommand wallpaperCommand = new android.service.wallpaper.WallpaperService.WallpaperCommand();
                        wallpaperCommand.action = str;
                        wallpaperCommand.x = i;
                        wallpaperCommand.y = i2;
                        wallpaperCommand.z = i3;
                        wallpaperCommand.extras = bundle;
                        wallpaperCommand.sync = z;
                        android.os.Message obtainMessage = android.service.wallpaper.WallpaperService.Engine.this.mCaller.obtainMessage(10025);
                        obtainMessage.obj = wallpaperCommand;
                        android.service.wallpaper.WallpaperService.Engine.this.mCaller.sendMessage(obtainMessage);
                    }
                }
            };
            this.mDisplayListener = new android.hardware.display.DisplayManager.DisplayListener() { // from class: android.service.wallpaper.WallpaperService.Engine.4
                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayChanged(int i) {
                    if (android.service.wallpaper.WallpaperService.Engine.this.mDisplay.getDisplayId() == i) {
                        android.service.wallpaper.WallpaperService.Engine.this.reportVisibility(android.service.wallpaper.WallpaperService.this.mIsWearOs && android.service.wallpaper.WallpaperService.Engine.this.mDisplay.getState() != 4);
                    }
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayRemoved(int i) {
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayAdded(int i) {
                }
            };
            this.mClockFunction = supplier;
            this.mHandler = handler;
        }

        public android.view.SurfaceHolder getSurfaceHolder() {
            return this.mSurfaceHolder;
        }

        public int getWallpaperFlags() {
            return this.mIWallpaperEngine.mWhich;
        }

        public int getDesiredMinimumWidth() {
            return this.mIWallpaperEngine.mReqWidth;
        }

        public int getDesiredMinimumHeight() {
            return this.mIWallpaperEngine.mReqHeight;
        }

        public boolean isVisible() {
            return this.mReportedVisible;
        }

        public boolean supportsLocalColorExtraction() {
            return false;
        }

        public boolean isPreview() {
            return this.mIWallpaperEngine.mIsPreview;
        }

        @android.annotation.SystemApi
        public boolean isInAmbientMode() {
            return this.mIsInAmbientMode;
        }

        public boolean shouldZoomOutWallpaper() {
            return android.service.wallpaper.WallpaperService.this.mIsWearOs && !android.app.compat.CompatChanges.isChangeEnabled(android.service.wallpaper.WallpaperService.WEAROS_WALLPAPER_HANDLES_SCALING);
        }

        public boolean shouldWaitForEngineShown() {
            return false;
        }

        public void reportEngineShown(boolean z) {
            if (this.mIWallpaperEngine.mShownReported) {
                return;
            }
            android.os.Trace.beginSection("WPMS.reportEngineShown-" + z);
            android.util.Log.d(android.service.wallpaper.WallpaperService.TAG, "reportEngineShown: shouldWait=" + z);
            if (!z) {
                android.os.Message obtainMessage = this.mCaller.obtainMessage(10150);
                this.mCaller.removeMessages(10150);
                this.mCaller.sendMessage(obtainMessage);
            } else if (!this.mCaller.hasMessages(10150)) {
                this.mCaller.sendMessageDelayed(this.mCaller.obtainMessage(10150), java.util.concurrent.TimeUnit.SECONDS.toMillis(5L));
            }
            android.os.Trace.endSection();
        }

        public void setTouchEventsEnabled(boolean z) {
            int i;
            if (z) {
                i = this.mWindowFlags & (-17);
            } else {
                i = this.mWindowFlags | 16;
            }
            this.mWindowFlags = i;
            if (this.mCreated) {
                updateSurface(false, false, false);
            }
        }

        public void setOffsetNotificationsEnabled(boolean z) {
            int i;
            if (z) {
                i = this.mWindowPrivateFlags | 4;
            } else {
                i = this.mWindowPrivateFlags & (-5);
            }
            this.mWindowPrivateFlags = i;
            if (this.mCreated) {
                updateSurface(false, false, false);
            }
        }

        public void setShowForAllUsers(boolean z) {
            int i;
            if (z) {
                i = this.mWindowPrivateFlags | 16;
            } else {
                i = this.mWindowPrivateFlags & (-17);
            }
            this.mWindowPrivateFlags = i;
            if (this.mCreated) {
                updateSurface(false, false, false);
            }
        }

        public void setFixedSizeAllowed(boolean z) {
            this.mFixedSizeAllowed = z;
        }

        public float getZoom() {
            return this.mZoom;
        }

        public void onCreate(android.view.SurfaceHolder surfaceHolder) {
        }

        public void onDestroy() {
        }

        public void onVisibilityChanged(boolean z) {
        }

        public void onApplyWindowInsets(android.view.WindowInsets windowInsets) {
        }

        public void onTouchEvent(android.view.MotionEvent motionEvent) {
        }

        public void onOffsetsChanged(float f, float f2, float f3, float f4, int i, int i2) {
        }

        public android.os.Bundle onCommand(java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) {
            return null;
        }

        @android.annotation.SystemApi
        public void onAmbientModeChanged(boolean z, long j) {
        }

        public void onDesiredSizeChanged(int i, int i2) {
        }

        public void onSurfaceChanged(android.view.SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        }

        public void onSurfaceRedrawNeeded(android.view.SurfaceHolder surfaceHolder) {
        }

        public void onSurfaceCreated(android.view.SurfaceHolder surfaceHolder) {
        }

        public void onSurfaceDestroyed(android.view.SurfaceHolder surfaceHolder) {
        }

        public void onWallpaperFlagsChanged(int i) {
        }

        public void onZoomChanged(float f) {
        }

        public void notifyColorsChanged() {
            if (this.mDestroyed) {
                android.util.Log.i(android.service.wallpaper.WallpaperService.TAG, "Ignoring notifyColorsChanged(), Engine has already been destroyed.");
                return;
            }
            long longValue = this.mClockFunction.get().longValue();
            if (longValue - this.mLastColorInvalidation < 1000) {
                android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "This call has been deferred. You should only call notifyColorsChanged() once every 1.0 seconds.");
                if (!this.mHandler.hasCallbacks(this.mNotifyColorsChanged)) {
                    this.mHandler.postDelayed(this.mNotifyColorsChanged, 1000L);
                    return;
                }
                return;
            }
            this.mLastColorInvalidation = longValue;
            this.mHandler.removeCallbacks(this.mNotifyColorsChanged);
            try {
                android.app.WallpaperColors onComputeColors = onComputeColors();
                if (this.mConnection != null) {
                    this.mConnection.onWallpaperColorsChanged(onComputeColors, this.mDisplay.getDisplayId());
                } else {
                    android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "Can't notify system because wallpaper connection was not established.");
                }
                this.mResetWindowPages = true;
                processLocalColors();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "Can't notify system because wallpaper connection was lost.", e);
            }
        }

        public android.app.WallpaperColors onComputeColors() {
            return null;
        }

        public void notifyLocalColorsChanged(java.util.List<android.graphics.RectF> list, java.util.List<android.app.WallpaperColors> list2) throws java.lang.RuntimeException {
            for (int i = 0; i < list.size() && i < list2.size(); i++) {
                android.app.WallpaperColors wallpaperColors = list2.get(i);
                android.graphics.RectF rectF = list.get(i);
                if (wallpaperColors != null && rectF != null) {
                    try {
                        this.mConnection.onLocalWallpaperColorsChanged(rectF, wallpaperColors, this.mDisplayContext.getDisplayId());
                    } catch (android.os.RemoteException e) {
                        throw new java.lang.RuntimeException(e);
                    }
                }
            }
            setPrimaryWallpaperColors(this.mIWallpaperEngine.mWallpaperManager.getWallpaperColors(1));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPrimaryWallpaperColors(android.app.WallpaperColors wallpaperColors) {
            if (wallpaperColors == null) {
                return;
            }
            int colorHints = wallpaperColors.getColorHints();
            this.mShouldDimByDefault = (colorHints & 1) == 0 && (colorHints & 2) == 0;
            updateWallpaperDimming(this.mCustomDimAmount);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateWallpaperDimming(float f) {
            this.mCustomDimAmount = java.lang.Math.min(1.0f, f);
            this.mWallpaperDimAmount = !this.mShouldDimByDefault ? this.mCustomDimAmount : java.lang.Math.max(this.mDefaultDimAmount, this.mCustomDimAmount);
            if (!android.service.wallpaper.WallpaperService.ENABLE_WALLPAPER_DIMMING || this.mBbqSurfaceControl == null || this.mWallpaperDimAmount == this.mPreviousWallpaperDimAmount) {
                return;
            }
            final android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
            if (!isPreview()) {
                android.util.Log.v(android.service.wallpaper.WallpaperService.TAG, "Setting wallpaper dimming: " + this.mWallpaperDimAmount);
                android.animation.ValueAnimator ofFloat = android.animation.ValueAnimator.ofFloat(this.mPreviousWallpaperDimAmount, this.mWallpaperDimAmount);
                ofFloat.setDuration(android.service.wallpaper.WallpaperService.DIMMING_ANIMATION_DURATION_MS);
                ofFloat.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda5
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                        android.service.wallpaper.WallpaperService.Engine.this.lambda$updateWallpaperDimming$0(transaction, valueAnimator);
                    }
                });
                ofFloat.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.service.wallpaper.WallpaperService.Engine.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(android.animation.Animator animator) {
                        android.service.wallpaper.WallpaperService.Engine.this.updateSurface(false, false, true);
                    }
                });
                ofFloat.start();
            } else {
                android.util.Log.v(android.service.wallpaper.WallpaperService.TAG, "Setting wallpaper dimming: 0");
                transaction.setAlpha(this.mBbqSurfaceControl, 1.0f).apply();
                updateSurface(false, false, true);
            }
            this.mPreviousWallpaperDimAmount = this.mWallpaperDimAmount;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateWallpaperDimming$0(android.view.SurfaceControl.Transaction transaction, android.animation.ValueAnimator valueAnimator) {
            float floatValue = ((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue();
            if (this.mBbqSurfaceControl != null) {
                transaction.setAlpha(this.mBbqSurfaceControl, 1.0f - floatValue).apply();
            }
        }

        public void setCreated(boolean z) {
            this.mCreated = z;
        }

        protected void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            printWriter.print(str);
            printWriter.print("mInitializing=");
            printWriter.print(this.mInitializing);
            printWriter.print(" mDestroyed=");
            printWriter.println(this.mDestroyed);
            printWriter.print(str);
            printWriter.print("mVisible=");
            printWriter.print(this.mVisible);
            printWriter.print(" mReportedVisible=");
            printWriter.println(this.mReportedVisible);
            printWriter.print(" mIsScreenTurningOn=");
            printWriter.println(this.mIsScreenTurningOn);
            printWriter.print(str);
            printWriter.print("mDisplay=");
            printWriter.println(this.mDisplay);
            printWriter.print(str);
            printWriter.print("mCreated=");
            printWriter.print(this.mCreated);
            printWriter.print(" mSurfaceCreated=");
            printWriter.print(this.mSurfaceCreated);
            printWriter.print(" mIsCreating=");
            printWriter.print(this.mIsCreating);
            printWriter.print(" mDrawingAllowed=");
            printWriter.println(this.mDrawingAllowed);
            printWriter.print(str);
            printWriter.print("mWidth=");
            printWriter.print(this.mWidth);
            printWriter.print(" mCurWidth=");
            printWriter.print(this.mCurWidth);
            printWriter.print(" mHeight=");
            printWriter.print(this.mHeight);
            printWriter.print(" mCurHeight=");
            printWriter.println(this.mCurHeight);
            printWriter.print(str);
            printWriter.print("mType=");
            printWriter.print(this.mType);
            printWriter.print(" mWindowFlags=");
            printWriter.print(this.mWindowFlags);
            printWriter.print(" mCurWindowFlags=");
            printWriter.println(this.mCurWindowFlags);
            printWriter.print(str);
            printWriter.print("mWindowPrivateFlags=");
            printWriter.print(this.mWindowPrivateFlags);
            printWriter.print(" mCurWindowPrivateFlags=");
            printWriter.println(this.mCurWindowPrivateFlags);
            printWriter.print(str);
            printWriter.println("mWinFrames=");
            printWriter.println(this.mWinFrames);
            printWriter.print(str);
            printWriter.print("mConfiguration=");
            printWriter.println(this.mMergedConfiguration.getMergedConfiguration());
            printWriter.print(str);
            printWriter.print("mLayout=");
            printWriter.println(this.mLayout);
            printWriter.print(str);
            printWriter.print("mZoom=");
            printWriter.println(this.mZoom);
            printWriter.print(str);
            printWriter.print("mPreviewSurfacePosition=");
            printWriter.println(this.mPreviewSurfacePosition);
            int i = this.mIWallpaperEngine.mPendingResizeCount.get();
            if (i != 0) {
                printWriter.print(str);
                printWriter.print("mPendingResizeCount=");
                printWriter.println(i);
            }
            if (this.mPreserveVisible) {
                printWriter.print(str);
                printWriter.print("mPreserveVisible=true");
            }
            synchronized (this.mLock) {
                printWriter.print(str);
                printWriter.print("mPendingXOffset=");
                printWriter.print(this.mPendingXOffset);
                printWriter.print(" mPendingXOffset=");
                printWriter.println(this.mPendingXOffset);
                printWriter.print(str);
                printWriter.print("mPendingXOffsetStep=");
                printWriter.print(this.mPendingXOffsetStep);
                printWriter.print(" mPendingXOffsetStep=");
                printWriter.println(this.mPendingXOffsetStep);
                printWriter.print(str);
                printWriter.print("mOffsetMessageEnqueued=");
                printWriter.print(this.mOffsetMessageEnqueued);
                printWriter.print(" mPendingSync=");
                printWriter.println(this.mPendingSync);
                if (this.mPendingMove != null) {
                    printWriter.print(str);
                    printWriter.print("mPendingMove=");
                    printWriter.println(this.mPendingMove);
                }
            }
        }

        public void setZoom(float f) {
            boolean z;
            synchronized (this.mLock) {
                if (this.mIsInAmbientMode) {
                    this.mZoom = 0.0f;
                }
                if (java.lang.Float.compare(f, this.mZoom) == 0) {
                    z = false;
                } else {
                    this.mZoom = f;
                    z = true;
                }
            }
            if (z && !this.mDestroyed) {
                onZoomChanged(this.mZoom);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchPointer(android.view.MotionEvent motionEvent) {
            if (motionEvent.isTouchEvent()) {
                synchronized (this.mLock) {
                    if (motionEvent.getAction() == 2) {
                        this.mPendingMove = motionEvent;
                    } else {
                        this.mPendingMove = null;
                    }
                }
                this.mCaller.sendMessage(this.mCaller.obtainMessageO(10040, motionEvent));
                return;
            }
            motionEvent.recycle();
        }

        void updateSurface(boolean z, boolean z2, boolean z3) {
            boolean z4;
            int i;
            int i2;
            boolean z5;
            boolean z6;
            int i3;
            int i4;
            boolean z7;
            boolean z8;
            boolean z9;
            if (this.mDestroyed) {
                android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "Ignoring updateSurface due to destroyed");
                return;
            }
            int requestedWidth = this.mSurfaceHolder.getRequestedWidth();
            if (requestedWidth <= 0) {
                requestedWidth = -1;
                z4 = false;
            } else {
                z4 = true;
            }
            int requestedHeight = this.mSurfaceHolder.getRequestedHeight();
            if (requestedHeight <= 0) {
                requestedHeight = -1;
            } else {
                z4 = true;
            }
            boolean z10 = !this.mCreated;
            boolean z11 = !this.mSurfaceCreated;
            boolean z12 = this.mFormat != this.mSurfaceHolder.getRequestedFormat();
            boolean z13 = (this.mWidth == requestedWidth && this.mHeight == requestedHeight) ? false : true;
            boolean z14 = !this.mCreated;
            boolean z15 = this.mType != this.mSurfaceHolder.getRequestedType();
            boolean z16 = (this.mCurWindowFlags == this.mWindowFlags && this.mCurWindowPrivateFlags == this.mWindowPrivateFlags) ? false : true;
            if (z || z10 || z11 || z12 || z13 || z15 || z16 || z3 || !this.mIWallpaperEngine.mShownReported) {
                try {
                    this.mWidth = requestedWidth;
                    this.mHeight = requestedHeight;
                    this.mFormat = this.mSurfaceHolder.getRequestedFormat();
                    this.mType = this.mSurfaceHolder.getRequestedType();
                    this.mLayout.x = 0;
                    this.mLayout.y = 0;
                    this.mLayout.format = this.mFormat;
                    this.mCurWindowFlags = this.mWindowFlags;
                    this.mLayout.flags = this.mWindowFlags | 512 | 65536 | 256 | 8;
                    android.content.res.Configuration mergedConfiguration = this.mMergedConfiguration.getMergedConfiguration();
                    android.graphics.Rect rect = new android.graphics.Rect(mergedConfiguration.windowConfiguration.getMaxBounds());
                    if (requestedWidth == -1 && requestedHeight == -1) {
                        this.mLayout.width = requestedWidth;
                        this.mLayout.height = requestedHeight;
                        this.mLayout.flags &= -16385;
                    } else {
                        float f = requestedWidth;
                        float f2 = requestedHeight;
                        float max = java.lang.Math.max(rect.width() / f, rect.height() / f2);
                        this.mLayout.width = (int) ((f * max) + 0.5f);
                        this.mLayout.height = (int) ((f2 * max) + 0.5f);
                        this.mLayout.flags |= 16384;
                    }
                    this.mCurWindowPrivateFlags = this.mWindowPrivateFlags;
                    this.mLayout.privateFlags = this.mWindowPrivateFlags;
                    this.mLayout.memoryType = this.mType;
                    this.mLayout.token = this.mWindowToken;
                    if (!this.mCreated) {
                        this.mLayout.type = this.mIWallpaperEngine.mWindowType;
                        this.mLayout.gravity = 8388659;
                        this.mLayout.setFitInsetsTypes(0);
                        this.mLayout.setTitle(android.service.wallpaper.WallpaperService.this.getClass().getName());
                        this.mLayout.windowAnimations = com.android.internal.R.style.Animation_Wallpaper;
                        android.view.InputChannel inputChannel = new android.view.InputChannel();
                        i = requestedWidth;
                        i2 = requestedHeight;
                        z5 = z12;
                        z6 = z13;
                        if (this.mSession.addToDisplay(this.mWindow, this.mLayout, 0, this.mDisplay.getDisplayId(), android.view.WindowInsets.Type.defaultVisible(), inputChannel, this.mInsetsState, this.mTempControls, new android.graphics.Rect(), new float[1]) >= 0) {
                            this.mSession.setShouldZoomOutWallpaper(this.mWindow, shouldZoomOutWallpaper());
                            this.mCreated = true;
                            this.mInputEventReceiver = new android.service.wallpaper.WallpaperService.Engine.WallpaperInputEventReceiver(inputChannel, android.os.Looper.myLooper());
                        } else {
                            android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "Failed to add window while updating wallpaper surface.");
                            return;
                        }
                    } else {
                        i = requestedWidth;
                        i2 = requestedHeight;
                        z5 = z12;
                        z6 = z13;
                    }
                    this.mSurfaceHolder.mSurfaceLock.lock();
                    this.mDrawingAllowed = true;
                    if (!z4) {
                        this.mLayout.surfaceInsets.set(this.mIWallpaperEngine.mDisplayPadding);
                    } else {
                        this.mLayout.surfaceInsets.set(0, 0, 0, 0);
                    }
                    int relayout = this.mSession.relayout(this.mWindow, this.mLayout, this.mWidth, this.mHeight, 0, 0, 0, 0, this.mWinFrames, this.mMergedConfiguration, this.mSurfaceControl, this.mInsetsState, this.mTempControls, this.mSyncSeqIdBundle);
                    android.graphics.Rect maxBounds = this.mMergedConfiguration.getMergedConfiguration().windowConfiguration.getMaxBounds();
                    if (!maxBounds.equals(rect)) {
                        android.util.Log.i(android.service.wallpaper.WallpaperService.TAG, "Retry updateSurface because bounds changed from relayout: " + rect + " -> " + maxBounds);
                        this.mSurfaceHolder.mSurfaceLock.unlock();
                        this.mDrawingAllowed = false;
                        this.mCaller.sendMessage(this.mCaller.obtainMessageI(10030, z3 ? 1 : 0));
                        return;
                    }
                    int rotationToBufferTransform = android.view.SurfaceControl.rotationToBufferTransform((this.mDisplay.getInstallOrientation() + this.mDisplay.getRotation()) % 4);
                    this.mSurfaceControl.setTransformHint(rotationToBufferTransform);
                    android.view.WindowLayout.computeSurfaceSize(this.mLayout, rect, this.mWidth, this.mHeight, this.mWinFrames.frame, false, this.mSurfaceSize);
                    if (this.mSurfaceControl.isValid()) {
                        if (this.mBbqSurfaceControl == null) {
                            this.mBbqSurfaceControl = new android.view.SurfaceControl.Builder().setName("Wallpaper BBQ wrapper").setHidden(false).setBLASTLayer().setParent(this.mSurfaceControl).setCallsite("Wallpaper#relayout").build();
                            new android.view.SurfaceControl.Transaction().setDefaultFrameRateCompatibility(this.mBbqSurfaceControl, 102).apply();
                        }
                        this.mBbqSurfaceControl.setTransformHint(rotationToBufferTransform);
                        android.view.Surface orCreateBLASTSurface = getOrCreateBLASTSurface(this.mSurfaceSize.x, this.mSurfaceSize.y, this.mFormat);
                        if (orCreateBLASTSurface != null) {
                            this.mSurfaceHolder.mSurface.transferFrom(orCreateBLASTSurface);
                        }
                    }
                    if (!this.mLastSurfaceSize.equals(this.mSurfaceSize)) {
                        this.mLastSurfaceSize.set(this.mSurfaceSize.x, this.mSurfaceSize.y);
                    }
                    int width = this.mWinFrames.frame.width();
                    int height = this.mWinFrames.frame.height();
                    android.view.DisplayCutout displayCutout = this.mInsetsState.getDisplayCutout();
                    android.graphics.Rect rect2 = new android.graphics.Rect(this.mWinFrames.frame);
                    rect2.intersect(this.mInsetsState.getDisplayFrame());
                    android.view.WindowInsets calculateInsets = this.mInsetsState.calculateInsets(rect2, null, mergedConfiguration.isScreenRound(), this.mLayout.softInputMode, this.mLayout.flags, 0, this.mLayout.type, mergedConfiguration.windowConfiguration.getActivityType(), null);
                    if (!z4) {
                        android.graphics.Rect rect3 = this.mIWallpaperEngine.mDisplayPadding;
                        i3 = width + rect3.left + rect3.right;
                        i4 = rect3.top + rect3.bottom + height;
                        calculateInsets = calculateInsets.insetUnchecked(-rect3.left, -rect3.top, -rect3.right, -rect3.bottom);
                    } else {
                        i3 = i;
                        i4 = i2;
                    }
                    if (this.mCurWidth == i3) {
                        z7 = z6;
                    } else {
                        this.mCurWidth = i3;
                        z7 = true;
                    }
                    if (this.mCurHeight != i4) {
                        this.mCurHeight = i4;
                        z7 = true;
                    }
                    android.graphics.Rect rect4 = calculateInsets.getSystemWindowInsets().toRect();
                    android.graphics.Rect rect5 = calculateInsets.getStableInsets().toRect();
                    if (calculateInsets.getDisplayCutout() != null) {
                        displayCutout = calculateInsets.getDisplayCutout();
                    }
                    boolean z17 = z14 | (!this.mDispatchedContentInsets.equals(rect4)) | (!this.mDispatchedStableInsets.equals(rect5)) | (!this.mDispatchedDisplayCutout.equals(displayCutout));
                    this.mSurfaceHolder.setSurfaceFrameSize(i3, i4);
                    this.mSurfaceHolder.mSurfaceLock.unlock();
                    if (!this.mSurfaceHolder.mSurface.isValid()) {
                        reportSurfaceDestroyed();
                        return;
                    }
                    try {
                        this.mSurfaceHolder.ungetCallbacks();
                        if (z11) {
                            this.mIsCreating = true;
                            android.os.Trace.beginSection("WPMS.Engine.onSurfaceCreated");
                            onSurfaceCreated(this.mSurfaceHolder);
                            android.os.Trace.endSection();
                            android.view.SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
                            if (callbacks != null) {
                                for (android.view.SurfaceHolder.Callback callback : callbacks) {
                                    callback.surfaceCreated(this.mSurfaceHolder);
                                }
                            }
                            z9 = true;
                        } else {
                            z9 = false;
                        }
                        z8 = z3 | (z10 || (relayout & 1) != 0);
                        if (z2 || z10 || z11 || z5 || z7) {
                            try {
                                android.os.Trace.beginSection("WPMS.Engine.onSurfaceChanged");
                                onSurfaceChanged(this.mSurfaceHolder, this.mFormat, this.mCurWidth, this.mCurHeight);
                                android.os.Trace.endSection();
                                android.view.SurfaceHolder.Callback[] callbacks2 = this.mSurfaceHolder.getCallbacks();
                                if (callbacks2 != null) {
                                    for (android.view.SurfaceHolder.Callback callback2 : callbacks2) {
                                        callback2.surfaceChanged(this.mSurfaceHolder, this.mFormat, this.mCurWidth, this.mCurHeight);
                                    }
                                }
                                z9 = true;
                            } catch (java.lang.Throwable th) {
                                th = th;
                                this.mIsCreating = false;
                                this.mSurfaceCreated = true;
                                if (z8) {
                                    this.mSession.finishDrawing(this.mWindow, null, Integer.MAX_VALUE);
                                    processLocalColors();
                                }
                                reposition();
                                reportEngineShown(shouldWaitForEngineShown());
                                throw th;
                            }
                        }
                        if (z17) {
                            this.mDispatchedContentInsets.set(rect4);
                            this.mDispatchedStableInsets.set(rect5);
                            this.mDispatchedDisplayCutout = displayCutout;
                            android.os.Trace.beginSection("WPMS.Engine.onApplyWindowInsets");
                            onApplyWindowInsets(calculateInsets);
                            android.os.Trace.endSection();
                        }
                        if (z8 || z7) {
                            android.os.Trace.beginSection("WPMS.Engine.onSurfaceRedrawNeeded");
                            onSurfaceRedrawNeeded(this.mSurfaceHolder);
                            android.os.Trace.endSection();
                            android.view.SurfaceHolder.Callback[] callbacks3 = this.mSurfaceHolder.getCallbacks();
                            if (callbacks3 != null) {
                                for (android.view.SurfaceHolder.Callback callback3 : callbacks3) {
                                    if (callback3 instanceof android.view.SurfaceHolder.Callback2) {
                                        ((android.view.SurfaceHolder.Callback2) callback3).surfaceRedrawNeeded(this.mSurfaceHolder);
                                    }
                                }
                            }
                        }
                        if (z9 && !this.mReportedVisible) {
                            if (this.mIsCreating) {
                                if (com.android.window.flags.Flags.noConsecutiveVisibilityEvents()) {
                                    android.os.Trace.beginSection("WPMS.Engine.onVisibilityChanged-true");
                                    onVisibilityChanged(true);
                                    android.os.Trace.endSection();
                                    android.os.Trace.beginSection("WPMS.Engine.onVisibilityChanged-false");
                                    onVisibilityChanged(false);
                                    android.os.Trace.endSection();
                                } else {
                                    android.os.Trace.beginSection("WPMS.Engine.onVisibilityChanged-true");
                                    onVisibilityChanged(true);
                                    android.os.Trace.endSection();
                                }
                            }
                            if (!com.android.window.flags.Flags.noConsecutiveVisibilityEvents()) {
                                android.os.Trace.beginSection("WPMS.Engine.onVisibilityChanged-false");
                                onVisibilityChanged(false);
                                android.os.Trace.endSection();
                            }
                        }
                        this.mIsCreating = false;
                        this.mSurfaceCreated = true;
                        if (z8) {
                            this.mSession.finishDrawing(this.mWindow, null, Integer.MAX_VALUE);
                            processLocalColors();
                        }
                        reposition();
                        reportEngineShown(shouldWaitForEngineShown());
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        z8 = z3;
                    }
                } catch (android.os.RemoteException e) {
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void resizePreview(android.graphics.Rect rect) {
            if (rect != null) {
                this.mSurfaceHolder.setFixedSize(rect.width(), rect.height());
            }
        }

        private void reposition() {
            if (this.mPreviewSurfacePosition == null) {
                return;
            }
            this.mTmpMatrix.setTranslate(this.mPreviewSurfacePosition.left, this.mPreviewSurfacePosition.top);
            this.mTmpMatrix.postScale(this.mPreviewSurfacePosition.width() / this.mCurWidth, this.mPreviewSurfacePosition.height() / this.mCurHeight);
            this.mTmpMatrix.getValues(this.mTmpValues);
            android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
            transaction.setPosition(this.mSurfaceControl, this.mPreviewSurfacePosition.left, this.mPreviewSurfacePosition.top);
            transaction.setMatrix(this.mSurfaceControl, this.mTmpValues[0], this.mTmpValues[3], this.mTmpValues[1], this.mTmpValues[4]);
            transaction.apply();
        }

        void attach(android.service.wallpaper.WallpaperService.IWallpaperEngineWrapper iWallpaperEngineWrapper) {
            if (this.mDestroyed) {
                return;
            }
            this.mIWallpaperEngine = iWallpaperEngineWrapper;
            this.mCaller = iWallpaperEngineWrapper.mCaller;
            this.mConnection = iWallpaperEngineWrapper.mConnection;
            this.mWindowToken = iWallpaperEngineWrapper.mWindowToken;
            this.mSurfaceHolder.setSizeFromLayout();
            this.mInitializing = true;
            this.mSession = android.view.WindowManagerGlobal.getWindowSession();
            this.mWindow.setSession(this.mSession);
            this.mLayout.packageName = android.service.wallpaper.WallpaperService.this.getPackageName();
            this.mIWallpaperEngine.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mCaller.getHandler());
            this.mDisplay = this.mIWallpaperEngine.mDisplay;
            this.mDisplayContext = android.service.wallpaper.WallpaperService.this.createDisplayContext(this.mDisplay).createWindowContext(2013, null);
            this.mDefaultDimAmount = this.mDisplayContext.getResources().getFloat(com.android.internal.R.dimen.config_wallpaperDimAmount);
            this.mDisplayState = this.mDisplay.getCommittedState();
            this.mMergedConfiguration.setOverrideConfiguration(this.mDisplayContext.getResources().getConfiguration());
            android.os.Trace.beginSection("WPMS.Engine.onCreate");
            onCreate(this.mSurfaceHolder);
            android.os.Trace.endSection();
            this.mInitializing = false;
            this.mReportedVisible = false;
            android.os.Trace.beginSection("WPMS.Engine.updateSurface");
            updateSurface(false, false, false);
            android.os.Trace.endSection();
        }

        public android.content.Context getDisplayContext() {
            return this.mDisplayContext;
        }

        public void doAmbientModeChanged(boolean z, long j) {
            if (!this.mDestroyed) {
                this.mIsInAmbientMode = z;
                if (this.mCreated) {
                    onAmbientModeChanged(z, j);
                }
            }
        }

        void doDesiredSizeChanged(int i, int i2) {
            if (!this.mDestroyed) {
                this.mIWallpaperEngine.mReqWidth = i;
                this.mIWallpaperEngine.mReqHeight = i2;
                onDesiredSizeChanged(i, i2);
                doOffsetsChanged(true);
            }
        }

        void doDisplayPaddingChanged(android.graphics.Rect rect) {
            if (!this.mDestroyed && !this.mIWallpaperEngine.mDisplayPadding.equals(rect)) {
                this.mIWallpaperEngine.mDisplayPadding.set(rect);
                updateSurface(true, false, false);
            }
        }

        void onScreenTurningOnChanged(boolean z) {
            if (!this.mDestroyed) {
                this.mIsScreenTurningOn = z;
                reportVisibility(false);
            }
        }

        void doVisibilityChanged(boolean z) {
            if (!this.mDestroyed) {
                this.mVisible = z;
                reportVisibility(false);
                if (this.mReportedVisible) {
                    processLocalColors();
                    return;
                }
                return;
            }
            android.animation.AnimationHandler.requestAnimatorsEnabled(z, this);
        }

        void reportVisibility(boolean z) {
            boolean supportsAmbientMode;
            if ((this.mScreenshotSurfaceControl == null || !this.mVisible) && !this.mDestroyed) {
                this.mDisplayState = this.mDisplay == null ? 0 : this.mDisplay.getCommittedState();
                boolean z2 = true;
                boolean z3 = android.view.Display.isOnState(this.mDisplayState) && !this.mIsScreenTurningOn;
                if (this.mIWallpaperEngine.mInfo == null) {
                    supportsAmbientMode = false;
                } else {
                    supportsAmbientMode = this.mIWallpaperEngine.mInfo.supportsAmbientMode();
                }
                if ((!this.mVisible || (!z3 && !supportsAmbientMode)) && !this.mPreserveVisible) {
                    z2 = false;
                }
                if (this.mReportedVisible != z2 || z) {
                    this.mReportedVisible = z2;
                    if (z2) {
                        doOffsetsChanged(false);
                        updateSurface(false, false, false);
                    }
                    onVisibilityChanged(z2);
                    if (this.mReportedVisible && this.mFrozenRequested) {
                        freeze();
                    }
                    android.animation.AnimationHandler.requestAnimatorsEnabled(z2, this);
                }
            }
        }

        void doOffsetsChanged(boolean z) {
            float f;
            float f2;
            float f3;
            float f4;
            boolean z2;
            int i;
            int i2;
            if (this.mDestroyed) {
                return;
            }
            if (!z && !this.mOffsetsChanged) {
                return;
            }
            synchronized (this.mLock) {
                f = this.mPendingXOffset;
                f2 = this.mPendingYOffset;
                f3 = this.mPendingXOffsetStep;
                f4 = this.mPendingYOffsetStep;
                z2 = this.mPendingSync;
                i = 0;
                this.mPendingSync = false;
                this.mOffsetMessageEnqueued = false;
            }
            if (this.mSurfaceCreated) {
                if (this.mReportedVisible) {
                    int i3 = this.mIWallpaperEngine.mReqWidth - this.mCurWidth;
                    if (i3 <= 0) {
                        i2 = 0;
                    } else {
                        i2 = -((int) ((i3 * f) + 0.5f));
                    }
                    int i4 = this.mIWallpaperEngine.mReqHeight - this.mCurHeight;
                    if (i4 > 0) {
                        i = -((int) ((i4 * f2) + 0.5f));
                    }
                    onOffsetsChanged(f, f2, f3, f4, i2, i);
                } else {
                    this.mOffsetsChanged = true;
                }
            }
            if (z2) {
                try {
                    this.mSession.wallpaperOffsetsComplete(this.mWindow.asBinder());
                } catch (android.os.RemoteException e) {
                }
            }
            processLocalColors();
        }

        private void processLocalColors() {
            if (this.mProcessLocalColorsPending.compareAndSet(false, true)) {
                final long longValue = this.mClockFunction.get().longValue();
                final long max = java.lang.Math.max(0L, 2000 - (longValue - this.mLastProcessLocalColorsTimestamp));
                this.mHandler.postDelayed(new java.lang.Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.service.wallpaper.WallpaperService.Engine.this.lambda$processLocalColors$1(longValue, max);
                    }
                }, max);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$processLocalColors$1(long j, long j2) {
            this.mLastProcessLocalColorsTimestamp = j + j2;
            this.mProcessLocalColorsPending.set(false);
            processLocalColorsInternal();
        }

        private void processLocalColorsInternal() {
            int round;
            int i;
            int i2;
            if (supportsLocalColorExtraction()) {
                return;
            }
            synchronized (this.mLock) {
                float f = this.mPendingXOffset;
                float f2 = this.mPendingXOffsetStep;
                float f3 = this.mWallpaperDimAmount;
                if (f % f2 <= android.service.wallpaper.WallpaperService.MIN_PAGE_ALLOWED_MARGIN && this.mSurfaceHolder.getSurface().isValid()) {
                    float f4 = 1.0f;
                    if (!validStep(f2)) {
                        round = 0;
                        i = 1;
                    } else {
                        int round2 = java.lang.Math.round(1.0f / f2) + 1;
                        float f5 = round2;
                        f4 = 1.0f / f5;
                        round = java.lang.Math.round((f * ((round2 - 1) / f5)) / f4);
                        i = round2;
                    }
                    resetWindowPages();
                    if (this.mWindowPages.length == 0 || this.mWindowPages.length != i) {
                        this.mWindowPages = new android.service.wallpaper.EngineWindowPage[i];
                        initWindowPages(this.mWindowPages, f4);
                    }
                    if (this.mLocalColorsToAdd.size() != 0) {
                        java.util.Iterator<android.graphics.RectF> it = this.mLocalColorsToAdd.iterator();
                        while (it.hasNext()) {
                            android.graphics.RectF next = it.next();
                            if (android.service.wallpaper.WallpaperService.this.isValid(next)) {
                                this.mLocalColorAreas.add(next);
                                android.service.wallpaper.EngineWindowPage engineWindowPage = this.mWindowPages[getRectFPage(next, f4)];
                                engineWindowPage.setLastUpdateTime(0L);
                                engineWindowPage.removeColor(next);
                            }
                        }
                        this.mLocalColorsToAdd.clear();
                    }
                    if (round < this.mWindowPages.length) {
                        i2 = round;
                    } else {
                        i2 = this.mWindowPages.length - 1;
                    }
                    android.service.wallpaper.EngineWindowPage engineWindowPage2 = this.mWindowPages[i2];
                    updatePage(engineWindowPage2, new java.util.HashSet(engineWindowPage2.getAreas()), i2, i, f3);
                }
            }
        }

        private void initWindowPages(android.service.wallpaper.EngineWindowPage[] engineWindowPageArr, float f) {
            for (int i = 0; i < engineWindowPageArr.length; i++) {
                engineWindowPageArr[i] = new android.service.wallpaper.EngineWindowPage();
            }
            this.mLocalColorAreas.addAll((android.util.ArraySet<? extends android.graphics.RectF>) this.mLocalColorsToAdd);
            this.mLocalColorsToAdd.clear();
            java.util.Iterator<android.graphics.RectF> it = this.mLocalColorAreas.iterator();
            while (it.hasNext()) {
                android.graphics.RectF next = it.next();
                if (!android.service.wallpaper.WallpaperService.this.isValid(next)) {
                    this.mLocalColorAreas.remove(next);
                } else {
                    engineWindowPageArr[getRectFPage(next, f)].addArea(next);
                }
            }
        }

        void updatePage(final android.service.wallpaper.EngineWindowPage engineWindowPage, final java.util.Set<android.graphics.RectF> set, final int i, final int i2, final float f) {
            int i3;
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - 60000;
            if (elapsedRealtime - engineWindowPage.getLastUpdateTime() < 60000) {
                return;
            }
            android.view.Surface surface = this.mSurfaceHolder.getSurface();
            if (surface.isValid()) {
                if (this.mSurfaceSize.x > this.mSurfaceSize.y) {
                    i3 = this.mSurfaceSize.x;
                } else {
                    i3 = this.mSurfaceSize.y;
                }
                float f2 = 64.0f / i3;
                int i4 = (int) (this.mSurfaceSize.x * f2);
                int i5 = (int) (f2 * this.mSurfaceSize.y);
                if (i4 <= 0 || i5 <= 0) {
                    android.util.Log.e(android.service.wallpaper.WallpaperService.TAG, "wrong width and height values of bitmap " + i4 + " " + i5);
                    return;
                }
                final int i6 = this.mPixelCopyCount;
                this.mPixelCopyCount = i6 + 1;
                android.os.Trace.beginAsyncSection("WallpaperService#pixelCopy", i6);
                final android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(i4, i5, android.graphics.Bitmap.Config.ARGB_8888);
                try {
                    android.view.PixelCopy.request(surface, createBitmap, new android.view.PixelCopy.OnPixelCopyFinishedListener() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda1
                        @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                        public final void onPixelCopyFinished(int i7) {
                            android.service.wallpaper.WallpaperService.Engine.this.lambda$updatePage$2(i6, engineWindowPage, set, i, i2, f, createBitmap, elapsedRealtime, i7);
                        }
                    }, android.service.wallpaper.WallpaperService.this.mBackgroundHandler);
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "Cancelling processLocalColors: exception caught during PixelCopy");
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updatePage$2(int i, android.service.wallpaper.EngineWindowPage engineWindowPage, java.util.Set set, int i2, int i3, float f, android.graphics.Bitmap bitmap, long j, int i4) {
            android.os.Trace.endAsyncSection("WallpaperService#pixelCopy", i);
            if (i4 != 0) {
                android.graphics.Bitmap bitmap2 = engineWindowPage.getBitmap();
                engineWindowPage.setBitmap(this.mLastScreenshot);
                android.graphics.Bitmap bitmap3 = this.mLastScreenshot;
                if (bitmap3 != null && !java.util.Objects.equals(bitmap2, bitmap3)) {
                    updatePageColors(engineWindowPage, set, i2, i3, f);
                    return;
                }
                return;
            }
            this.mLastScreenshot = bitmap;
            engineWindowPage.setBitmap(bitmap);
            engineWindowPage.setLastUpdateTime(j);
            updatePageColors(engineWindowPage, set, i2, i3, f);
        }

        private void updatePageColors(android.service.wallpaper.EngineWindowPage engineWindowPage, java.util.Set<android.graphics.RectF> set, int i, int i2, float f) {
            if (engineWindowPage.getBitmap() == null) {
                return;
            }
            if (!android.service.wallpaper.WallpaperService.this.mBackgroundHandler.getLooper().isCurrentThread()) {
                throw new java.lang.IllegalStateException("ProcessLocalColors should be called from the background thread");
            }
            android.os.Trace.beginSection("WallpaperService#updatePageColors");
            for (final android.graphics.RectF rectF : set) {
                if (rectF != null) {
                    android.graphics.RectF generateSubRect = generateSubRect(rectF, i, i2);
                    try {
                        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(engineWindowPage.getBitmap(), java.lang.Math.round(r2.getWidth() * generateSubRect.left), java.lang.Math.round(r2.getHeight() * generateSubRect.top), java.lang.Math.round(r2.getWidth() * generateSubRect.width()), java.lang.Math.round(r2.getHeight() * generateSubRect.height()));
                        final android.app.WallpaperColors fromBitmap = android.app.WallpaperColors.fromBitmap(createBitmap, f);
                        createBitmap.recycle();
                        android.app.WallpaperColors colors = engineWindowPage.getColors(rectF);
                        if (colors == null || !fromBitmap.equals(colors)) {
                            engineWindowPage.addWallpaperColors(rectF, fromBitmap);
                            this.mHandler.post(new java.lang.Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.service.wallpaper.WallpaperService.Engine.this.lambda$updatePageColors$3(rectF, fromBitmap);
                                }
                            });
                        }
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(android.service.wallpaper.WallpaperService.TAG, "Error creating page local color bitmap", e);
                    }
                }
            }
            android.os.Trace.endSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updatePageColors$3(android.graphics.RectF rectF, android.app.WallpaperColors wallpaperColors) {
            try {
                this.mConnection.onLocalWallpaperColorsChanged(rectF, wallpaperColors, this.mDisplayContext.getDisplayId());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.service.wallpaper.WallpaperService.TAG, "Error calling Connection.onLocalWallpaperColorsChanged", e);
            }
        }

        private android.graphics.RectF generateSubRect(android.graphics.RectF rectF, int i, int i2) {
            float f = i2;
            float f2 = i / f;
            float f3 = (i + 1) / f;
            float f4 = rectF.left;
            float f5 = rectF.right;
            if (f4 >= f2) {
                f2 = f4;
            }
            if (f5 <= f3) {
                f3 = f5;
            }
            float f6 = (f3 * f) % 1.0f;
            return new android.graphics.RectF((f2 * f) % 1.0f, rectF.top, f6 != 0.0f ? f6 : 1.0f, rectF.bottom);
        }

        private void resetWindowPages() {
            if (!supportsLocalColorExtraction() && this.mResetWindowPages) {
                this.mResetWindowPages = false;
                for (int i = 0; i < this.mWindowPages.length; i++) {
                    this.mWindowPages[i].setLastUpdateTime(0L);
                }
            }
        }

        private int getRectFPage(android.graphics.RectF rectF, float f) {
            if (!android.service.wallpaper.WallpaperService.this.isValid(rectF) || !validStep(f)) {
                return 0;
            }
            int round = java.lang.Math.round(1.0f / f);
            int round2 = java.lang.Math.round(rectF.centerX() * round);
            return round2 == round ? round - 1 : round2 == this.mWindowPages.length ? this.mWindowPages.length - 1 : round2;
        }

        public void addLocalColorsAreas(final java.util.List<android.graphics.RectF> list) {
            if (supportsLocalColorExtraction()) {
                return;
            }
            android.service.wallpaper.WallpaperService.this.mBackgroundHandler.post(new java.lang.Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.wallpaper.WallpaperService.Engine.this.lambda$addLocalColorsAreas$4(list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addLocalColorsAreas$4(java.util.List list) {
            synchronized (this.mLock) {
                this.mLocalColorsToAdd.addAll(list);
            }
            processLocalColors();
        }

        public void removeLocalColorsAreas(final java.util.List<android.graphics.RectF> list) {
            if (supportsLocalColorExtraction()) {
                return;
            }
            android.service.wallpaper.WallpaperService.this.mBackgroundHandler.post(new java.lang.Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.wallpaper.WallpaperService.Engine.this.lambda$removeLocalColorsAreas$5(list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeLocalColorsAreas$5(java.util.List list) {
            synchronized (this.mLock) {
                float f = this.mPendingXOffsetStep;
                this.mLocalColorsToAdd.removeAll(list);
                this.mLocalColorAreas.removeAll(list);
                if (validStep(f)) {
                    for (int i = 0; i < this.mWindowPages.length; i++) {
                        for (int i2 = 0; i2 < list.size(); i2++) {
                            this.mWindowPages[i].removeArea((android.graphics.RectF) list.get(i2));
                        }
                    }
                }
            }
        }

        private android.graphics.Rect fixRect(android.graphics.Bitmap bitmap, android.graphics.Rect rect) {
            int i;
            int width;
            if (rect.left >= rect.right || rect.left >= bitmap.getWidth() || rect.left > 0) {
                i = 0;
            } else {
                i = rect.left;
            }
            rect.left = i;
            if (rect.left >= rect.right || rect.right > bitmap.getWidth()) {
                width = bitmap.getWidth();
            } else {
                width = rect.right;
            }
            rect.right = width;
            return rect;
        }

        private boolean validStep(float f) {
            return !java.lang.Float.isNaN(f) && f > 0.0f && f <= 1.0f;
        }

        void doCommand(android.service.wallpaper.WallpaperService.WallpaperCommand wallpaperCommand) {
            android.os.Bundle bundle;
            if (!this.mDestroyed) {
                if (android.app.WallpaperManager.COMMAND_FREEZE.equals(wallpaperCommand.action) || android.app.WallpaperManager.COMMAND_UNFREEZE.equals(wallpaperCommand.action)) {
                    updateFrozenState(!android.app.WallpaperManager.COMMAND_UNFREEZE.equals(wallpaperCommand.action));
                } else if (android.app.WallpaperManager.COMMAND_DISPLAY_SWITCH.equals(wallpaperCommand.action)) {
                    handleDisplaySwitch(wallpaperCommand.z == 1);
                    return;
                }
                bundle = onCommand(wallpaperCommand.action, wallpaperCommand.x, wallpaperCommand.y, wallpaperCommand.z, wallpaperCommand.extras, wallpaperCommand.sync);
            } else {
                bundle = null;
            }
            if (wallpaperCommand.sync) {
                try {
                    this.mSession.wallpaperCommandComplete(this.mWindow.asBinder(), bundle);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        private void handleDisplaySwitch(boolean z) {
            if (z && this.mReportedVisible) {
                this.mPreserveVisible = true;
                this.mCaller.removeMessages(10011);
                this.mCaller.sendMessageDelayed(this.mCaller.obtainMessage(10011), 1000L);
            } else if (!z && this.mPreserveVisible) {
                this.mPreserveVisible = false;
                this.mCaller.removeMessages(10011);
                reportVisibility(false);
            }
        }

        private void updateFrozenState(boolean z) {
            if (this.mIWallpaperEngine.mInfo == null && z) {
                return;
            }
            this.mFrozenRequested = z;
            if (this.mFrozenRequested == (this.mScreenshotSurfaceControl != null)) {
                return;
            }
            if (this.mFrozenRequested) {
                freeze();
            } else {
                unfreeze();
            }
        }

        private void freeze() {
            if (!this.mReportedVisible || this.mDestroyed || !showScreenshotOfWallpaper()) {
                return;
            }
            doVisibilityChanged(false);
            this.mVisible = true;
        }

        private void unfreeze() {
            cleanUpScreenshotSurfaceControl();
            if (this.mVisible) {
                doVisibilityChanged(true);
            }
        }

        private void cleanUpScreenshotSurfaceControl() {
            if (this.mScreenshotSurfaceControl != null) {
                new android.view.SurfaceControl.Transaction().remove(this.mScreenshotSurfaceControl).show(this.mBbqSurfaceControl).apply();
                this.mScreenshotSurfaceControl = null;
            }
        }

        void scaleAndCropScreenshot() {
            if (this.mScreenshotSurfaceControl == null) {
                return;
            }
            if (this.mScreenshotSize.x <= 0 || this.mScreenshotSize.y <= 0) {
                android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "Unexpected screenshot size: " + this.mScreenshotSize);
                return;
            }
            float max = java.lang.Math.max(1.0f, java.lang.Math.max(this.mSurfaceSize.x / this.mScreenshotSize.x, this.mSurfaceSize.y / this.mScreenshotSize.y));
            int i = ((int) (this.mScreenshotSize.x * max)) - this.mSurfaceSize.x;
            int i2 = i / 2;
            int i3 = (((int) (this.mScreenshotSize.y * max)) - this.mSurfaceSize.y) / 2;
            new android.view.SurfaceControl.Transaction().setMatrix(this.mScreenshotSurfaceControl, max, 0.0f, 0.0f, max).setWindowCrop(this.mScreenshotSurfaceControl, new android.graphics.Rect(i2, i3, this.mScreenshotSize.x + i2, this.mScreenshotSize.y + i3)).setPosition(this.mScreenshotSurfaceControl, (-i) / 2, (-r1) / 2).apply();
        }

        private boolean showScreenshotOfWallpaper() {
            if (this.mDestroyed || this.mSurfaceControl == null || !this.mSurfaceControl.isValid()) {
                return false;
            }
            android.graphics.Rect rect = new android.graphics.Rect(0, 0, this.mSurfaceSize.x, this.mSurfaceSize.y);
            if (rect.isEmpty()) {
                android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "Failed to screenshot wallpaper: surface bounds are empty");
                return false;
            }
            if (this.mScreenshotSurfaceControl != null) {
                android.util.Log.e(android.service.wallpaper.WallpaperService.TAG, "Screenshot is unexpectedly not null");
                cleanUpScreenshotSurfaceControl();
            }
            android.window.ScreenCapture.ScreenshotHardwareBuffer captureLayers = android.window.ScreenCapture.captureLayers(new android.window.ScreenCapture.LayerCaptureArgs.Builder(this.mSurfaceControl).setUid(android.os.Process.myUid()).setChildrenOnly(false).setSourceCrop(rect).build());
            if (captureLayers == null) {
                android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "Failed to screenshot wallpaper: screenshotBuffer is null");
                return false;
            }
            android.hardware.HardwareBuffer hardwareBuffer = captureLayers.getHardwareBuffer();
            android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
            this.mScreenshotSurfaceControl = new android.view.SurfaceControl.Builder().setName("Wallpaper snapshot for engine " + this).setFormat(hardwareBuffer.getFormat()).setParent(this.mSurfaceControl).setSecure(captureLayers.containsSecureLayers()).setCallsite("WallpaperService.Engine.showScreenshotOfWallpaper").setBLASTLayer().build();
            this.mScreenshotSize.set(this.mSurfaceSize.x, this.mSurfaceSize.y);
            transaction.setBuffer(this.mScreenshotSurfaceControl, hardwareBuffer);
            transaction.setColorSpace(this.mScreenshotSurfaceControl, captureLayers.getColorSpace());
            transaction.setLayer(this.mScreenshotSurfaceControl, Integer.MAX_VALUE);
            transaction.show(this.mScreenshotSurfaceControl);
            transaction.hide(this.mBbqSurfaceControl);
            transaction.apply();
            return true;
        }

        void reportSurfaceDestroyed() {
            if (this.mSurfaceCreated) {
                this.mSurfaceCreated = false;
                this.mSurfaceHolder.ungetCallbacks();
                android.view.SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
                if (callbacks != null) {
                    for (android.view.SurfaceHolder.Callback callback : callbacks) {
                        callback.surfaceDestroyed(this.mSurfaceHolder);
                    }
                }
                onSurfaceDestroyed(this.mSurfaceHolder);
            }
        }

        public void detach() {
            if (this.mDestroyed) {
                return;
            }
            android.animation.AnimationHandler.removeRequestor(this);
            this.mDestroyed = true;
            if (this.mIWallpaperEngine != null && this.mIWallpaperEngine.mDisplayManager != null) {
                this.mIWallpaperEngine.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
            }
            if (this.mVisible) {
                this.mVisible = false;
                onVisibilityChanged(false);
            }
            reportSurfaceDestroyed();
            onDestroy();
            if (this.mCreated) {
                try {
                    if (this.mInputEventReceiver != null) {
                        this.mInputEventReceiver.dispose();
                        this.mInputEventReceiver = null;
                    }
                    this.mSession.remove(this.mWindow.asBinder());
                } catch (android.os.RemoteException e) {
                }
                this.mSurfaceHolder.mSurface.release();
                if (this.mBlastBufferQueue != null) {
                    this.mBlastBufferQueue.destroy();
                    this.mBlastBufferQueue = null;
                }
                if (this.mBbqSurfaceControl != null) {
                    new android.view.SurfaceControl.Transaction().remove(this.mBbqSurfaceControl).apply();
                    this.mBbqSurfaceControl = null;
                }
                this.mCreated = false;
            }
            if (this.mSurfaceControl != null) {
                this.mSurfaceControl.release();
                this.mSurfaceControl = null;
            }
        }

        private android.view.Surface getOrCreateBLASTSurface(int i, int i2, int i3) {
            if (this.mBlastBufferQueue == null) {
                this.mBlastBufferQueue = new android.graphics.BLASTBufferQueue("Wallpaper", this.mBbqSurfaceControl, i, i2, i3);
                return this.mBlastBufferQueue.createSurface();
            }
            this.mBlastBufferQueue.update(this.mBbqSurfaceControl, i, i2, i3);
            return null;
        }
    }

    public android.os.Looper onProvideEngineLooper() {
        return super.getMainLooper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isValid(android.graphics.RectF rectF) {
        return rectF != null && rectF.bottom > rectF.top && rectF.left < rectF.right && LOCAL_COLOR_BOUNDS.contains(rectF);
    }

    private boolean inRectFRange(float f) {
        return f >= 0.0f && f <= 1.0f;
    }

    class IWallpaperEngineWrapper extends android.service.wallpaper.IWallpaperEngine.Stub implements com.android.internal.os.HandlerCaller.Callback {
        private final com.android.internal.os.HandlerCaller mCaller;
        final android.service.wallpaper.IWallpaperConnection mConnection;
        final android.view.Display mDisplay;
        final int mDisplayId;
        final android.hardware.display.DisplayManager mDisplayManager;
        android.service.wallpaper.WallpaperService.Engine mEngine;
        final android.app.WallpaperInfo mInfo;
        final boolean mIsPreview;
        boolean mReportDraw;
        int mReqHeight;
        int mReqWidth;
        boolean mShownReported;
        final android.app.WallpaperManager mWallpaperManager;
        int mWhich;
        final android.os.IBinder mWindowToken;
        final int mWindowType;
        final java.util.concurrent.atomic.AtomicInteger mPendingResizeCount = new java.util.concurrent.atomic.AtomicInteger();
        final android.graphics.Rect mDisplayPadding = new android.graphics.Rect();

        IWallpaperEngineWrapper(android.service.wallpaper.WallpaperService wallpaperService, android.service.wallpaper.IWallpaperConnection iWallpaperConnection, android.os.IBinder iBinder, int i, boolean z, int i2, int i3, android.graphics.Rect rect, int i4, int i5, android.app.WallpaperInfo wallpaperInfo) {
            this.mWallpaperManager = (android.app.WallpaperManager) android.service.wallpaper.WallpaperService.this.getSystemService(android.app.WallpaperManager.class);
            this.mCaller = new com.android.internal.os.HandlerCaller(wallpaperService, wallpaperService.onProvideEngineLooper(), this, true);
            this.mConnection = iWallpaperConnection;
            this.mWindowToken = iBinder;
            this.mWindowType = i;
            this.mIsPreview = z;
            this.mReqWidth = i2;
            this.mReqHeight = i3;
            this.mDisplayPadding.set(rect);
            this.mDisplayId = i4;
            this.mWhich = i5;
            this.mInfo = wallpaperInfo;
            this.mDisplayManager = (android.hardware.display.DisplayManager) android.service.wallpaper.WallpaperService.this.getSystemService(android.hardware.display.DisplayManager.class);
            this.mDisplay = this.mDisplayManager.getDisplay(this.mDisplayId);
            if (this.mDisplay == null) {
                throw new java.lang.IllegalArgumentException("Cannot find display with id" + this.mDisplayId);
            }
            this.mCaller.sendMessage(this.mCaller.obtainMessage(10));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setDesiredSize(int i, int i2) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageII(30, i, i2));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setDisplayPadding(android.graphics.Rect rect) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageO(40, rect));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setVisibility(boolean z) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageI(10010, z ? 1 : 0));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setWallpaperFlags(int i) {
            if (i == this.mWhich) {
                return;
            }
            this.mWhich = i;
            this.mCaller.sendMessage(this.mCaller.obtainMessageI(android.service.wallpaper.WallpaperService.MSG_WALLPAPER_FLAGS_CHANGED, i));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setInAmbientMode(boolean z, long j) throws android.os.RemoteException {
            this.mCaller.sendMessage(this.mCaller.obtainMessageIO(50, z ? 1 : 0, java.lang.Long.valueOf(j)));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void dispatchPointer(android.view.MotionEvent motionEvent) {
            if (this.mEngine != null) {
                this.mEngine.dispatchPointer(motionEvent);
            } else {
                motionEvent.recycle();
            }
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void dispatchWallpaperCommand(java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle) {
            if (this.mEngine != null) {
                this.mEngine.mWindow.dispatchWallpaperCommand(str, i, i2, i3, bundle, false);
            }
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setZoomOut(float f) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageI(10100, java.lang.Float.floatToIntBits(f)));
        }

        public void reportShown() {
            if (this.mEngine == null) {
                android.util.Log.i(android.service.wallpaper.WallpaperService.TAG, "Can't report null engine as shown.");
                return;
            }
            if (this.mEngine.mDestroyed) {
                android.util.Log.i(android.service.wallpaper.WallpaperService.TAG, "Engine was destroyed before we could draw.");
                return;
            }
            if (!this.mShownReported) {
                this.mShownReported = true;
                android.os.Trace.beginSection("WPMS.mConnection.engineShown");
                try {
                    this.mConnection.engineShown(this);
                    android.util.Log.d(android.service.wallpaper.WallpaperService.TAG, "Wallpaper has updated the surface:" + this.mInfo);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "Wallpaper host disappeared", e);
                }
                android.os.Trace.endSection();
            }
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void requestWallpaperColors() {
            this.mCaller.sendMessage(this.mCaller.obtainMessage(10050));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void addLocalColorsAreas(java.util.List<android.graphics.RectF> list) {
            this.mEngine.addLocalColorsAreas(list);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void removeLocalColorsAreas(java.util.List<android.graphics.RectF> list) {
            this.mEngine.removeLocalColorsAreas(list);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void applyDimming(float f) throws android.os.RemoteException {
            this.mCaller.sendMessage(this.mCaller.obtainMessageI(10200, java.lang.Float.floatToIntBits(f)));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void destroy() {
            android.os.Message obtainMessage = this.mCaller.obtainMessage(20);
            this.mCaller.getHandler().removeCallbacksAndMessages(null);
            this.mCaller.sendMessage(obtainMessage);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void resizePreview(android.graphics.Rect rect) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageO(10110, rect));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public android.view.SurfaceControl mirrorSurfaceControl() {
            if (this.mEngine == null) {
                return null;
            }
            return android.view.SurfaceControl.mirrorSurface(this.mEngine.mSurfaceControl);
        }

        private void doAttachEngine() {
            android.os.Trace.beginSection("WPMS.onCreateEngine");
            android.service.wallpaper.WallpaperService.Engine onCreateEngine = android.service.wallpaper.WallpaperService.this.onCreateEngine();
            android.os.Trace.endSection();
            this.mEngine = onCreateEngine;
            android.os.Trace.beginSection("WPMS.mConnection.attachEngine-" + this.mDisplayId);
            try {
                this.mConnection.attachEngine(this, this.mDisplayId);
                android.os.Trace.endSection();
                android.os.Trace.beginSection("WPMS.engine.attach");
                onCreateEngine.attach(this);
            } catch (android.os.RemoteException e) {
                onCreateEngine.detach();
                android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "Wallpaper host disappeared", e);
            } catch (java.lang.IllegalStateException e2) {
                android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "Connector instance already destroyed, can't attach engine to non existing connector", e2);
            } finally {
                android.os.Trace.endSection();
            }
        }

        private void doDetachEngine() {
            if (this.mEngine != null && !this.mEngine.mDestroyed) {
                this.mEngine.detach();
                synchronized (android.service.wallpaper.WallpaperService.this.mActiveEngines) {
                    for (android.service.wallpaper.WallpaperService.IWallpaperEngineWrapper iWallpaperEngineWrapper : android.service.wallpaper.WallpaperService.this.mActiveEngines.values()) {
                        if (iWallpaperEngineWrapper.mEngine != null && iWallpaperEngineWrapper.mEngine.mVisible) {
                            iWallpaperEngineWrapper.mEngine.doVisibilityChanged(false);
                            iWallpaperEngineWrapper.mEngine.doVisibilityChanged(true);
                        }
                    }
                }
            }
        }

        public void updateScreenTurningOn(boolean z) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageBO(10170, z, null));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void onScreenTurningOn() throws android.os.RemoteException {
            updateScreenTurningOn(true);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void onScreenTurnedOn() throws android.os.RemoteException {
            updateScreenTurningOn(false);
        }

        @Override // com.android.internal.os.HandlerCaller.Callback
        public void executeMessage(android.os.Message message) {
            boolean z = false;
            switch (message.what) {
                case 10:
                    android.os.Trace.beginSection("WPMS.DO_ATTACH");
                    doAttachEngine();
                    android.os.Trace.endSection();
                    return;
                case 20:
                    android.os.Trace.beginSection("WPMS.DO_DETACH");
                    doDetachEngine();
                    android.os.Trace.endSection();
                    return;
                case 30:
                    this.mEngine.doDesiredSizeChanged(message.arg1, message.arg2);
                    return;
                case 40:
                    this.mEngine.doDisplayPaddingChanged((android.graphics.Rect) message.obj);
                    return;
                case 50:
                    this.mEngine.doAmbientModeChanged(message.arg1 != 0, ((java.lang.Long) message.obj).longValue());
                    return;
                case 10000:
                    this.mEngine.updateSurface(true, false, false);
                    return;
                case 10010:
                    this.mEngine.doVisibilityChanged(message.arg1 != 0);
                    return;
                case 10011:
                    this.mEngine.mPreserveVisible = false;
                    this.mEngine.reportVisibility(false);
                    return;
                case 10020:
                    this.mEngine.doOffsetsChanged(true);
                    return;
                case 10025:
                    this.mEngine.doCommand((android.service.wallpaper.WallpaperService.WallpaperCommand) message.obj);
                    return;
                case 10030:
                    handleResized((android.util.MergedConfiguration) message.obj, message.arg1 != 0);
                    return;
                case 10035:
                    return;
                case 10040:
                    android.view.MotionEvent motionEvent = (android.view.MotionEvent) message.obj;
                    if (motionEvent.getAction() == 2) {
                        synchronized (this.mEngine.mLock) {
                            if (this.mEngine.mPendingMove == motionEvent) {
                                this.mEngine.mPendingMove = null;
                                r1 = false;
                            }
                        }
                        z = r1;
                    }
                    if (!z) {
                        this.mEngine.onTouchEvent(motionEvent);
                    }
                    motionEvent.recycle();
                    return;
                case 10050:
                    if (this.mConnection != null) {
                        try {
                            android.app.WallpaperColors onComputeColors = this.mEngine.onComputeColors();
                            this.mEngine.setPrimaryWallpaperColors(onComputeColors);
                            this.mConnection.onWallpaperColorsChanged(onComputeColors, this.mDisplayId);
                            return;
                        } catch (android.os.RemoteException e) {
                            return;
                        }
                    }
                    return;
                case 10100:
                    this.mEngine.setZoom(java.lang.Float.intBitsToFloat(message.arg1));
                    return;
                case 10110:
                    this.mEngine.resizePreview((android.graphics.Rect) message.obj);
                    return;
                case 10150:
                    android.os.Trace.beginSection("WPMS.MSG_REPORT_SHOWN");
                    reportShown();
                    android.os.Trace.endSection();
                    return;
                case 10170:
                    this.mEngine.onScreenTurningOnChanged(message.arg1 != 0);
                    return;
                case 10200:
                    this.mEngine.updateWallpaperDimming(java.lang.Float.intBitsToFloat(message.arg1));
                    return;
                case android.service.wallpaper.WallpaperService.MSG_WALLPAPER_FLAGS_CHANGED /* 10210 */:
                    this.mEngine.onWallpaperFlagsChanged(message.arg1);
                    return;
                default:
                    android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "Unknown message type " + message.what);
                    return;
            }
        }

        private void handleResized(android.util.MergedConfiguration mergedConfiguration, boolean z) {
            int decrementAndGet = mergedConfiguration != null ? this.mPendingResizeCount.decrementAndGet() : -1;
            if (z) {
                this.mReportDraw = true;
            }
            if (decrementAndGet > 0) {
                return;
            }
            if (mergedConfiguration != null) {
                this.mEngine.mMergedConfiguration.setTo(mergedConfiguration);
            }
            this.mEngine.updateSurface(true, false, this.mReportDraw);
            this.mReportDraw = false;
            this.mEngine.doOffsetsChanged(true);
            this.mEngine.scaleAndCropScreenshot();
        }
    }

    class IWallpaperServiceWrapper extends android.service.wallpaper.IWallpaperService.Stub {
        private final android.service.wallpaper.WallpaperService mTarget;

        public IWallpaperServiceWrapper(android.service.wallpaper.WallpaperService wallpaperService) {
            this.mTarget = wallpaperService;
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void attach(android.service.wallpaper.IWallpaperConnection iWallpaperConnection, android.os.IBinder iBinder, int i, boolean z, int i2, int i3, android.graphics.Rect rect, int i4, int i5, android.app.WallpaperInfo wallpaperInfo) {
            android.os.Trace.beginSection("WPMS.ServiceWrapper.attach");
            android.service.wallpaper.WallpaperService.IWallpaperEngineWrapper iWallpaperEngineWrapper = android.service.wallpaper.WallpaperService.this.new IWallpaperEngineWrapper(this.mTarget, iWallpaperConnection, iBinder, i, z, i2, i3, rect, i4, i5, wallpaperInfo);
            synchronized (android.service.wallpaper.WallpaperService.this.mActiveEngines) {
                android.service.wallpaper.WallpaperService.this.mActiveEngines.put(iBinder, iWallpaperEngineWrapper);
            }
            android.os.Trace.endSection();
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void detach(android.os.IBinder iBinder) {
            android.service.wallpaper.WallpaperService.IWallpaperEngineWrapper iWallpaperEngineWrapper;
            synchronized (android.service.wallpaper.WallpaperService.this.mActiveEngines) {
                iWallpaperEngineWrapper = (android.service.wallpaper.WallpaperService.IWallpaperEngineWrapper) android.service.wallpaper.WallpaperService.this.mActiveEngines.remove(iBinder);
            }
            if (iWallpaperEngineWrapper == null) {
                android.util.Log.w(android.service.wallpaper.WallpaperService.TAG, "Engine for window token " + iBinder + " already detached");
            } else {
                iWallpaperEngineWrapper.destroy();
            }
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        android.os.Trace.beginSection("WPMS.onCreate");
        this.mBackgroundThread = new android.os.HandlerThread("DefaultWallpaperLocalColorExtractor");
        this.mBackgroundThread.start();
        this.mBackgroundHandler = new android.os.Handler(this.mBackgroundThread.getLooper());
        this.mIsWearOs = getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_WATCH);
        super.onCreate();
        android.os.Trace.endSection();
    }

    @Override // android.app.Service
    public void onDestroy() {
        android.os.Trace.beginSection("WPMS.onDestroy");
        super.onDestroy();
        synchronized (this.mActiveEngines) {
            java.util.Iterator<android.service.wallpaper.WallpaperService.IWallpaperEngineWrapper> it = this.mActiveEngines.values().iterator();
            while (it.hasNext()) {
                it.next().destroy();
            }
            this.mActiveEngines.clear();
        }
        if (this.mBackgroundThread != null) {
            this.mBackgroundThread.quitSafely();
        }
        android.os.Trace.endSection();
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return new android.service.wallpaper.WallpaperService.IWallpaperServiceWrapper(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.print("State of wallpaper ");
        printWriter.print(this);
        printWriter.println(":");
        synchronized (this.mActiveEngines) {
            for (android.service.wallpaper.WallpaperService.IWallpaperEngineWrapper iWallpaperEngineWrapper : this.mActiveEngines.values()) {
                android.service.wallpaper.WallpaperService.Engine engine = iWallpaperEngineWrapper.mEngine;
                if (engine == null) {
                    android.util.Slog.w(TAG, "Engine for wrapper " + iWallpaperEngineWrapper + " not attached");
                } else {
                    printWriter.print("  Engine ");
                    printWriter.print(engine);
                    printWriter.println(":");
                    engine.dump("    ", fileDescriptor, printWriter, strArr);
                }
            }
        }
    }
}
