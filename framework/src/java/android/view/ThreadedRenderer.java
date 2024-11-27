package android.view;

/* loaded from: classes4.dex */
public final class ThreadedRenderer extends android.graphics.HardwareRenderer {
    public static final java.lang.String DEBUG_DIRTY_REGIONS_PROPERTY = "debug.hwui.show_dirty_regions";
    public static final java.lang.String DEBUG_FORCE_DARK = "debug.hwui.force_dark";
    public static final java.lang.String DEBUG_FPS_DIVISOR = "debug.hwui.fps_divisor";
    public static final java.lang.String DEBUG_OVERDRAW_PROPERTY = "debug.hwui.overdraw";
    public static final java.lang.String DEBUG_SHOW_LAYERS_UPDATES_PROPERTY = "debug.hwui.show_layers_updates";
    public static final java.lang.String DEBUG_SHOW_NON_RECTANGULAR_CLIP_PROPERTY = "debug.hwui.show_non_rect_clip";
    public static final java.lang.String OVERDRAW_PROPERTY_SHOW = "show";
    static final java.lang.String PRINT_CONFIG_PROPERTY = "debug.hwui.print_config";
    static final java.lang.String PROFILE_MAXFRAMES_PROPERTY = "debug.hwui.profile.maxframes";
    public static final java.lang.String PROFILE_PROPERTY = "debug.hwui.profile";
    private boolean mEnabled;
    private int mHeight;
    private int mInsetLeft;
    private int mInsetTop;
    private final float mLightRadius;
    private final float mLightY;
    private final float mLightZ;
    private java.util.ArrayList<android.graphics.HardwareRenderer.FrameDrawingCallback> mNextRtFrameCallbacks;
    private boolean mRootNodeNeedsUpdate;
    private int mSurfaceHeight;
    private int mSurfaceWidth;
    private int mWidth;
    public static int EGL_CONTEXT_PRIORITY_REALTIME_NV = 13143;
    public static int EGL_CONTEXT_PRIORITY_HIGH_IMG = 12545;
    public static int EGL_CONTEXT_PRIORITY_MEDIUM_IMG = 12546;
    public static int EGL_CONTEXT_PRIORITY_LOW_IMG = 12547;
    public static boolean sRendererEnabled = true;
    public static final java.lang.String PROFILE_PROPERTY_VISUALIZE_BARS = "visual_bars";
    private static final java.lang.String[] VISUALIZERS = {PROFILE_PROPERTY_VISUALIZE_BARS};
    private boolean mInitialized = false;
    private boolean mRequested = true;
    private final android.view.ThreadedRenderer.WebViewOverlayProvider mWebViewOverlayProvider = new android.view.ThreadedRenderer.WebViewOverlayProvider();
    private boolean mWebViewOverlaysEnabled = false;

    interface DrawCallbacks {
        void onPostDraw(android.graphics.RecordingCanvas recordingCanvas);

        void onPreDraw(android.graphics.RecordingCanvas recordingCanvas);
    }

    public static void enableForegroundTrimming() {
    }

    public static void initForSystemProcess() {
        if (!android.app.ActivityManager.isHighEndGfx()) {
            sRendererEnabled = false;
        }
        setIsSystemOrPersistent();
    }

    public static android.view.ThreadedRenderer create(android.content.Context context, boolean z, java.lang.String str) {
        return new android.view.ThreadedRenderer(context, z, str);
    }

    private static final class WebViewOverlayProvider implements android.graphics.HardwareRenderer.PrepareSurfaceControlForWebviewCallback, android.graphics.HardwareRenderer.ASurfaceTransactionCallback {
        private static final boolean sOverlaysAreEnabled = android.view.ThreadedRenderer.isWebViewOverlaysEnabled();
        private android.graphics.BLASTBufferQueue mBLASTBufferQueue;
        private boolean mHasWebViewOverlays;
        private android.view.SurfaceControl mSurfaceControl;
        private final android.view.SurfaceControl.Transaction mTransaction;

        private WebViewOverlayProvider() {
            this.mTransaction = new android.view.SurfaceControl.Transaction();
            this.mHasWebViewOverlays = false;
        }

        public boolean setSurfaceControlOpaque(boolean z) {
            synchronized (this) {
                if (this.mHasWebViewOverlays) {
                    return false;
                }
                this.mTransaction.setOpaque(this.mSurfaceControl, z).apply();
                return z;
            }
        }

        public boolean shouldEnableOverlaySupport() {
            return (!sOverlaysAreEnabled || this.mSurfaceControl == null || this.mBLASTBufferQueue == null) ? false : true;
        }

        public void setSurfaceControl(android.view.SurfaceControl surfaceControl) {
            synchronized (this) {
                this.mSurfaceControl = surfaceControl;
                if (this.mSurfaceControl != null && this.mHasWebViewOverlays) {
                    this.mTransaction.setOpaque(surfaceControl, false).apply();
                }
            }
        }

        public void setBLASTBufferQueue(android.graphics.BLASTBufferQueue bLASTBufferQueue) {
            synchronized (this) {
                this.mBLASTBufferQueue = bLASTBufferQueue;
            }
        }

        @Override // android.graphics.HardwareRenderer.PrepareSurfaceControlForWebviewCallback
        public void prepare() {
            synchronized (this) {
                this.mHasWebViewOverlays = true;
                if (this.mSurfaceControl != null) {
                    this.mTransaction.setOpaque(this.mSurfaceControl, false).apply();
                }
            }
        }

        @Override // android.graphics.HardwareRenderer.ASurfaceTransactionCallback
        public boolean onMergeTransaction(long j, long j2, long j3) {
            synchronized (this) {
                if (this.mBLASTBufferQueue == null) {
                    return false;
                }
                this.mBLASTBufferQueue.mergeWithNextTransaction(j, j3);
                return true;
            }
        }
    }

    ThreadedRenderer(android.content.Context context, boolean z, java.lang.String str) {
        setName(str);
        setOpaque(!z);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, com.android.internal.R.styleable.Lighting, 0, 0);
        this.mLightY = obtainStyledAttributes.getDimension(3, 0.0f);
        this.mLightZ = obtainStyledAttributes.getDimension(4, 0.0f);
        this.mLightRadius = obtainStyledAttributes.getDimension(2, 0.0f);
        float f = obtainStyledAttributes.getFloat(0, 0.0f);
        float f2 = obtainStyledAttributes.getFloat(1, 0.0f);
        obtainStyledAttributes.recycle();
        setLightSourceAlpha(f, f2);
    }

    @Override // android.graphics.HardwareRenderer
    public void destroy() {
        this.mInitialized = false;
        updateEnabledState(null);
        super.destroy();
    }

    boolean isEnabled() {
        return this.mEnabled;
    }

    void setEnabled(boolean z) {
        this.mEnabled = z;
    }

    boolean isRequested() {
        return this.mRequested;
    }

    void setRequested(boolean z) {
        this.mRequested = z;
    }

    private void updateEnabledState(android.view.Surface surface) {
        if (surface == null || !surface.isValid()) {
            setEnabled(false);
        } else {
            setEnabled(this.mInitialized);
        }
    }

    boolean initialize(android.view.Surface surface) throws android.view.Surface.OutOfResourcesException {
        boolean z = !this.mInitialized;
        this.mInitialized = true;
        updateEnabledState(surface);
        setSurface(surface);
        return z;
    }

    boolean initializeIfNeeded(int i, int i2, android.view.View.AttachInfo attachInfo, android.view.Surface surface, android.graphics.Rect rect) throws android.view.Surface.OutOfResourcesException {
        if (isRequested() && !isEnabled() && initialize(surface)) {
            setup(i, i2, attachInfo, rect);
            return true;
        }
        return false;
    }

    void updateSurface(android.view.Surface surface) throws android.view.Surface.OutOfResourcesException {
        updateEnabledState(surface);
        setSurface(surface);
    }

    @Override // android.graphics.HardwareRenderer
    public void setSurface(android.view.Surface surface) {
        if (surface != null && surface.isValid()) {
            super.setSurface(surface);
        } else {
            super.setSurface(null);
        }
    }

    void registerRtFrameCallback(android.graphics.HardwareRenderer.FrameDrawingCallback frameDrawingCallback) {
        if (this.mNextRtFrameCallbacks == null) {
            this.mNextRtFrameCallbacks = new java.util.ArrayList<>();
        }
        this.mNextRtFrameCallbacks.add(frameDrawingCallback);
    }

    void unregisterRtFrameCallback(android.graphics.HardwareRenderer.FrameDrawingCallback frameDrawingCallback) {
        if (this.mNextRtFrameCallbacks == null) {
            return;
        }
        this.mNextRtFrameCallbacks.remove(frameDrawingCallback);
    }

    void destroyHardwareResources(android.view.View view) {
        destroyResources(view);
        clearContent();
    }

    private static void destroyResources(android.view.View view) {
        view.destroyHardwareResources();
    }

    void setup(int i, int i2, android.view.View.AttachInfo attachInfo, android.graphics.Rect rect) {
        this.mWidth = i;
        this.mHeight = i2;
        if (rect != null && (rect.left != 0 || rect.right != 0 || rect.top != 0 || rect.bottom != 0)) {
            this.mInsetLeft = rect.left;
            this.mInsetTop = rect.top;
            this.mSurfaceWidth = i + this.mInsetLeft + rect.right;
            this.mSurfaceHeight = i2 + this.mInsetTop + rect.bottom;
            setOpaque(false);
        } else {
            this.mInsetLeft = 0;
            this.mInsetTop = 0;
            this.mSurfaceWidth = i;
            this.mSurfaceHeight = i2;
        }
        this.mRootNode.setLeftTopRightBottom(-this.mInsetLeft, -this.mInsetTop, this.mSurfaceWidth, this.mSurfaceHeight);
        setLightCenter(attachInfo);
    }

    public boolean rendererOwnsSurfaceControlOpacity() {
        return this.mWebViewOverlayProvider.mSurfaceControl != null;
    }

    public boolean setSurfaceControlOpaque(boolean z) {
        return this.mWebViewOverlayProvider.setSurfaceControlOpaque(z);
    }

    private void updateWebViewOverlayCallbacks() {
        boolean shouldEnableOverlaySupport = this.mWebViewOverlayProvider.shouldEnableOverlaySupport();
        if (shouldEnableOverlaySupport != this.mWebViewOverlaysEnabled) {
            this.mWebViewOverlaysEnabled = shouldEnableOverlaySupport;
            if (shouldEnableOverlaySupport) {
                setASurfaceTransactionCallback(this.mWebViewOverlayProvider);
                setPrepareSurfaceControlForWebviewCallback(this.mWebViewOverlayProvider);
            } else {
                setASurfaceTransactionCallback(null);
                setPrepareSurfaceControlForWebviewCallback(null);
            }
        }
    }

    @Override // android.graphics.HardwareRenderer
    public void setSurfaceControl(android.view.SurfaceControl surfaceControl, android.graphics.BLASTBufferQueue bLASTBufferQueue) {
        super.setSurfaceControl(surfaceControl, bLASTBufferQueue);
        this.mWebViewOverlayProvider.setSurfaceControl(surfaceControl);
        this.mWebViewOverlayProvider.setBLASTBufferQueue(bLASTBufferQueue);
        updateWebViewOverlayCallbacks();
    }

    @Override // android.graphics.HardwareRenderer
    public void notifyCallbackPending() {
        if (isEnabled()) {
            super.notifyCallbackPending();
        }
    }

    @Override // android.graphics.HardwareRenderer
    public void notifyExpensiveFrame() {
        if (isEnabled()) {
            super.notifyExpensiveFrame();
        }
    }

    void setLightCenter(android.view.View.AttachInfo attachInfo) {
        android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
        attachInfo.mDisplay.getRealMetrics(displayMetrics);
        setLightSourceGeometry((displayMetrics.widthPixels / 2.0f) - attachInfo.mWindowLeft, this.mLightY - attachInfo.mWindowTop, this.mLightZ * (((java.lang.Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) / (displayMetrics.density * 450.0f)) + 2.0f) / 3.0f), this.mLightRadius);
    }

    int getWidth() {
        return this.mWidth;
    }

    int getHeight() {
        return this.mHeight;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int dumpArgsToFlags(java.lang.String[] strArr) {
        char c;
        if (strArr == null || strArr.length == 0) {
            return 1;
        }
        int i = 0;
        for (java.lang.String str : strArr) {
            switch (str.hashCode()) {
                case -252053678:
                    if (str.equals("framestats")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1492:
                    if (str.equals("-a")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 108404047:
                    if (str.equals("reset")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    i |= 1;
                    break;
                case 1:
                    i |= 2;
                    break;
                case 2:
                    i = 1;
                    break;
            }
        }
        return i;
    }

    public static void handleDumpGfxInfo(java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) {
        dumpGlobalProfileInfo(fileDescriptor, dumpArgsToFlags(strArr));
        android.view.WindowManagerGlobal.getInstance().dumpGfxInfo(fileDescriptor, strArr);
    }

    void dumpGfxInfo(java.io.PrintWriter printWriter, java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) {
        printWriter.flush();
        dumpProfileInfo(fileDescriptor, dumpArgsToFlags(strArr));
    }

    android.graphics.Picture captureRenderingCommands() {
        return null;
    }

    @Override // android.graphics.HardwareRenderer
    public boolean loadSystemProperties() {
        boolean loadSystemProperties = super.loadSystemProperties();
        if (loadSystemProperties) {
            invalidateRoot();
        }
        return loadSystemProperties;
    }

    private void updateViewTreeDisplayList(android.view.View view) {
        view.mPrivateFlags |= 32;
        view.mRecreateDisplayList = (view.mPrivateFlags & Integer.MIN_VALUE) == Integer.MIN_VALUE;
        view.mPrivateFlags &= Integer.MAX_VALUE;
        view.updateDisplayListIfDirty();
        view.mRecreateDisplayList = false;
    }

    private void updateRootDisplayList(android.view.View view, android.view.ThreadedRenderer.DrawCallbacks drawCallbacks) {
        android.os.Trace.traceBegin(8L, "Record View#draw()");
        updateViewTreeDisplayList(view);
        if (this.mNextRtFrameCallbacks != null) {
            java.util.ArrayList<android.graphics.HardwareRenderer.FrameDrawingCallback> arrayList = this.mNextRtFrameCallbacks;
            this.mNextRtFrameCallbacks = null;
            setFrameCallback(new android.view.ThreadedRenderer.AnonymousClass1(arrayList));
        }
        if (this.mRootNodeNeedsUpdate || !this.mRootNode.hasDisplayList()) {
            android.graphics.RecordingCanvas beginRecording = this.mRootNode.beginRecording(this.mSurfaceWidth, this.mSurfaceHeight);
            try {
                int save = beginRecording.save();
                beginRecording.translate(this.mInsetLeft, this.mInsetTop);
                drawCallbacks.onPreDraw(beginRecording);
                beginRecording.enableZ();
                beginRecording.drawRenderNode(view.updateDisplayListIfDirty());
                beginRecording.disableZ();
                drawCallbacks.onPostDraw(beginRecording);
                beginRecording.restoreToCount(save);
                this.mRootNodeNeedsUpdate = false;
            } finally {
                this.mRootNode.endRecording();
            }
        }
        android.os.Trace.traceEnd(8L);
    }

    /* renamed from: android.view.ThreadedRenderer$1, reason: invalid class name */
    class AnonymousClass1 implements android.graphics.HardwareRenderer.FrameDrawingCallback {
        final /* synthetic */ java.util.ArrayList val$frameCallbacks;

        AnonymousClass1(java.util.ArrayList arrayList) {
            this.val$frameCallbacks = arrayList;
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public void onFrameDraw(long j) {
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public android.graphics.HardwareRenderer.FrameCommitCallback onFrameDraw(int i, long j) {
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i2 = 0; i2 < this.val$frameCallbacks.size(); i2++) {
                android.graphics.HardwareRenderer.FrameCommitCallback onFrameDraw = ((android.graphics.HardwareRenderer.FrameDrawingCallback) this.val$frameCallbacks.get(i2)).onFrameDraw(i, j);
                if (onFrameDraw != null) {
                    arrayList.add(onFrameDraw);
                }
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            return new android.graphics.HardwareRenderer.FrameCommitCallback() { // from class: android.view.ThreadedRenderer$1$$ExternalSyntheticLambda0
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z) {
                    android.view.ThreadedRenderer.AnonymousClass1.lambda$onFrameDraw$0(arrayList, z);
                }
            };
        }

        static /* synthetic */ void lambda$onFrameDraw$0(java.util.ArrayList arrayList, boolean z) {
            for (int i = 0; i < arrayList.size(); i++) {
                ((android.graphics.HardwareRenderer.FrameCommitCallback) arrayList.get(i)).onFrameCommit(z);
            }
        }
    }

    void invalidateRoot() {
        this.mRootNodeNeedsUpdate = true;
    }

    void draw(android.view.View view, android.view.View.AttachInfo attachInfo, android.view.ThreadedRenderer.DrawCallbacks drawCallbacks) {
        attachInfo.mViewRootImpl.mViewFrameInfo.markDrawStart();
        updateRootDisplayList(view, drawCallbacks);
        if (attachInfo.mPendingAnimatingRenderNodes != null) {
            int size = attachInfo.mPendingAnimatingRenderNodes.size();
            for (int i = 0; i < size; i++) {
                registerAnimatingRenderNode(attachInfo.mPendingAnimatingRenderNodes.get(i));
            }
            attachInfo.mPendingAnimatingRenderNodes.clear();
            attachInfo.mPendingAnimatingRenderNodes = null;
        }
        int syncAndDrawFrame = syncAndDrawFrame(attachInfo.mViewRootImpl.getUpdatedFrameInfo());
        if ((syncAndDrawFrame & 2) != 0) {
            android.util.Log.w("HWUI", "Surface lost, forcing relayout");
            attachInfo.mViewRootImpl.mForceNextWindowRelayout = true;
            attachInfo.mViewRootImpl.requestLayout();
        }
        if ((syncAndDrawFrame & 1) != 0) {
            attachInfo.mViewRootImpl.invalidate();
        }
    }

    public android.graphics.RenderNode getRootNode() {
        return this.mRootNode;
    }

    public static class SimpleRenderer extends android.graphics.HardwareRenderer {
        private final float mLightRadius;
        private final float mLightY;
        private final float mLightZ;

        public SimpleRenderer(android.content.Context context, java.lang.String str, android.view.Surface surface) {
            setName(str);
            setOpaque(false);
            setSurface(surface);
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, com.android.internal.R.styleable.Lighting, 0, 0);
            this.mLightY = obtainStyledAttributes.getDimension(3, 0.0f);
            this.mLightZ = obtainStyledAttributes.getDimension(4, 0.0f);
            this.mLightRadius = obtainStyledAttributes.getDimension(2, 0.0f);
            float f = obtainStyledAttributes.getFloat(0, 0.0f);
            float f2 = obtainStyledAttributes.getFloat(1, 0.0f);
            obtainStyledAttributes.recycle();
            setLightSourceAlpha(f, f2);
        }

        public void setLightCenter(android.view.Display display, int i, int i2) {
            android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
            display.getRealMetrics(displayMetrics);
            setLightSourceGeometry((displayMetrics.widthPixels / 2.0f) - i, this.mLightY - i2, this.mLightZ * (((java.lang.Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) / (displayMetrics.density * 450.0f)) + 2.0f) / 3.0f), this.mLightRadius);
        }

        public android.graphics.RenderNode getRootNode() {
            return this.mRootNode;
        }

        public void draw(android.graphics.HardwareRenderer.FrameDrawingCallback frameDrawingCallback) {
            long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis() * 1000000;
            if (frameDrawingCallback != null) {
                setFrameCallback(frameDrawingCallback);
            }
            createRenderRequest().setVsyncTime(currentAnimationTimeMillis).syncAndDraw();
        }
    }
}
