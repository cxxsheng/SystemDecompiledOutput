package android.view;

/* loaded from: classes4.dex */
public class SurfaceView extends android.view.View implements android.view.ViewRootImpl.SurfaceChangedCallback {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_POSITION = false;
    private static final long FORWARD_BACK_KEY_TOLERANCE_MS = 100;
    public static final int SURFACE_LIFECYCLE_DEFAULT = 0;
    public static final int SURFACE_LIFECYCLE_FOLLOWS_ATTACHMENT = 2;
    public static final int SURFACE_LIFECYCLE_FOLLOWS_VISIBILITY = 1;
    private static final java.lang.String TAG = "SurfaceView";
    float mAlpha;
    private boolean mAttachedToWindow;
    int mBackgroundColor;
    android.view.SurfaceControl mBackgroundControl;
    private android.graphics.BLASTBufferQueue mBlastBufferQueue;
    private android.view.SurfaceControl mBlastSurfaceControl;
    final java.util.ArrayList<android.view.SurfaceHolder.Callback> mCallbacks;
    boolean mClipSurfaceToBounds;
    float mCornerRadius;
    private boolean mDisableBackgroundLayer;
    boolean mDrawFinished;
    private final android.view.ViewTreeObserver.OnPreDrawListener mDrawListener;
    boolean mDrawingStopped;
    private final java.util.concurrent.ConcurrentLinkedQueue<android.view.WindowManager.LayoutParams> mEmbeddedWindowParams;
    int mFormat;
    private final android.view.SurfaceControl.Transaction mFrameCallbackTransaction;
    private boolean mGlobalListenersAdded;
    boolean mHaveFrame;
    private float mHdrHeadroom;
    boolean mIsCreating;
    long mLastLockTime;
    int mLastSurfaceHeight;
    int mLastSurfaceWidth;
    boolean mLastWindowVisibility;
    private final boolean mLimitedHdrEnabled;
    final int[] mLocation;
    private int mParentSurfaceSequenceId;
    private android.view.SurfaceView.SurfaceViewPositionUpdateListener mPositionListener;
    private final android.graphics.Rect mRTLastReportedPosition;
    private final android.graphics.Rect mRTLastSetCrop;
    private android.view.RemoteAccessibilityController mRemoteAccessibilityController;
    int mRequestedFormat;
    private float mRequestedHdrHeadroom;
    int mRequestedHeight;
    int mRequestedSubLayer;
    private int mRequestedSurfaceLifecycleStrategy;
    boolean mRequestedVisible;
    int mRequestedWidth;
    android.graphics.Paint mRoundedViewportPaint;
    private final boolean mRtDrivenClipping;
    private final android.view.SurfaceControl.Transaction mRtTransaction;
    final android.graphics.Rect mScreenRect;
    private final android.view.ViewTreeObserver.OnScrollChangedListener mScrollChangedListener;
    int mSubLayer;
    final android.view.Surface mSurface;
    android.view.SurfaceControl mSurfaceControl;
    final java.lang.Object mSurfaceControlLock;
    private final android.view.ISurfaceControlViewHostParent mSurfaceControlViewHostParent;
    boolean mSurfaceCreated;
    private int mSurfaceFlags;
    final android.graphics.Rect mSurfaceFrame;
    int mSurfaceHeight;
    private final android.view.SurfaceHolder mSurfaceHolder;
    private int mSurfaceLifecycleStrategy;
    final java.util.concurrent.locks.ReentrantLock mSurfaceLock;
    android.view.SurfaceControlViewHost.SurfacePackage mSurfacePackage;
    private final android.view.SurfaceSession mSurfaceSession;
    int mSurfaceWidth;
    private final android.util.ArraySet<android.window.SurfaceSyncGroup> mSyncGroups;
    private final android.graphics.Matrix mTmpMatrix;
    final android.graphics.Rect mTmpRect;
    int mTransformHint;
    boolean mViewVisibility;
    boolean mVisible;
    int mWindowSpaceLeft;
    int mWindowSpaceTop;
    boolean mWindowStopped;
    boolean mWindowVisibility;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SurfaceLifecycleStrategy {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0() {
        this.mHaveFrame = getWidth() > 0 && getHeight() > 0;
        updateSurface();
        return true;
    }

    /* renamed from: android.view.SurfaceView$1, reason: invalid class name */
    class AnonymousClass1 extends android.view.ISurfaceControlViewHostParent.Stub {
        AnonymousClass1() {
        }

        @Override // android.view.ISurfaceControlViewHostParent
        public void updateParams(android.view.WindowManager.LayoutParams[] layoutParamsArr) {
            android.view.SurfaceView.this.mEmbeddedWindowParams.clear();
            android.view.SurfaceView.this.mEmbeddedWindowParams.addAll(java.util.Arrays.asList(layoutParamsArr));
            if (android.view.SurfaceView.this.isAttachedToWindow()) {
                android.view.SurfaceView.this.runOnUiThread(new java.lang.Runnable() { // from class: android.view.SurfaceView$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.SurfaceView.AnonymousClass1.this.lambda$updateParams$0();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateParams$0() {
            if (android.view.SurfaceView.this.mParent != null) {
                android.view.SurfaceView.this.mParent.recomputeViewAttributes(android.view.SurfaceView.this);
            }
        }

        @Override // android.view.ISurfaceControlViewHostParent
        public void forwardBackKeyToParent(final android.view.KeyEvent keyEvent) {
            android.view.SurfaceView.this.runOnUiThread(new java.lang.Runnable() { // from class: android.view.SurfaceView$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.SurfaceView.AnonymousClass1.this.lambda$forwardBackKeyToParent$1(keyEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$forwardBackKeyToParent$1(android.view.KeyEvent keyEvent) {
            android.view.ViewRootImpl viewRootImpl;
            android.hardware.input.InputManager inputManager;
            if (!android.view.SurfaceView.this.isAttachedToWindow() || keyEvent.getKeyCode() != 4 || (viewRootImpl = android.view.SurfaceView.this.getViewRootImpl()) == null || (inputManager = (android.hardware.input.InputManager) android.view.SurfaceView.this.mContext.getSystemService(android.hardware.input.InputManager.class)) == null) {
                return;
            }
            long uptimeMillis = android.os.SystemClock.uptimeMillis() - keyEvent.getEventTime();
            if (uptimeMillis > android.view.SurfaceView.FORWARD_BACK_KEY_TOLERANCE_MS) {
                android.util.Log.e(android.view.SurfaceView.TAG, "Ignore the input event that exceed the tolerance time, exceed " + uptimeMillis + "ms");
                return;
            }
            if (inputManager.verifyInputEvent(keyEvent) == null) {
                android.util.Log.e(android.view.SurfaceView.TAG, "Received invalid input event");
                return;
            }
            try {
                viewRootImpl.processingBackKey(true);
                viewRootImpl.enqueueInputEvent(keyEvent, null, 0, true);
            } finally {
                viewRootImpl.processingBackKey(false);
            }
        }
    }

    public SurfaceView(android.content.Context context) {
        this(context, null);
    }

    public SurfaceView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SurfaceView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SurfaceView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        this(context, attributeSet, i, i2, false);
    }

    public SurfaceView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2, boolean z) {
        super(context, attributeSet, i, i2);
        this.mCallbacks = new java.util.ArrayList<>();
        this.mLocation = new int[2];
        this.mSurfaceLock = new java.util.concurrent.locks.ReentrantLock();
        this.mSurface = new android.view.Surface();
        this.mDrawingStopped = true;
        this.mDrawFinished = false;
        this.mScreenRect = new android.graphics.Rect();
        this.mSurfaceSession = new android.view.SurfaceSession();
        this.mLimitedHdrEnabled = com.android.graphics.hwui.flags.Flags.limitedHdr();
        this.mDisableBackgroundLayer = false;
        this.mRequestedSurfaceLifecycleStrategy = 0;
        this.mSurfaceLifecycleStrategy = 0;
        this.mRequestedHdrHeadroom = 0.0f;
        this.mHdrHeadroom = 0.0f;
        this.mSurfaceControlLock = new java.lang.Object();
        this.mTmpRect = new android.graphics.Rect();
        this.mSubLayer = -2;
        this.mRequestedSubLayer = -2;
        this.mIsCreating = false;
        this.mScrollChangedListener = new android.view.ViewTreeObserver.OnScrollChangedListener() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                android.view.SurfaceView.this.updateSurface();
            }
        };
        this.mDrawListener = new android.view.ViewTreeObserver.OnPreDrawListener() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                boolean lambda$new$0;
                lambda$new$0 = android.view.SurfaceView.this.lambda$new$0();
                return lambda$new$0;
            }
        };
        this.mRequestedVisible = false;
        this.mWindowVisibility = false;
        this.mLastWindowVisibility = false;
        this.mViewVisibility = false;
        this.mWindowStopped = false;
        this.mRequestedWidth = -1;
        this.mRequestedHeight = -1;
        this.mRequestedFormat = 4;
        this.mAlpha = 1.0f;
        this.mBackgroundColor = -16777216;
        this.mHaveFrame = false;
        this.mSurfaceCreated = false;
        this.mLastLockTime = 0L;
        this.mVisible = false;
        this.mWindowSpaceLeft = -1;
        this.mWindowSpaceTop = -1;
        this.mSurfaceWidth = -1;
        this.mSurfaceHeight = -1;
        this.mFormat = -1;
        this.mSurfaceFrame = new android.graphics.Rect();
        this.mLastSurfaceWidth = -1;
        this.mLastSurfaceHeight = -1;
        this.mTransformHint = 0;
        this.mSurfaceFlags = 4;
        this.mSyncGroups = new android.util.ArraySet<>();
        this.mRtTransaction = new android.view.SurfaceControl.Transaction();
        this.mFrameCallbackTransaction = new android.view.SurfaceControl.Transaction();
        this.mRemoteAccessibilityController = new android.view.RemoteAccessibilityController(this);
        this.mTmpMatrix = new android.graphics.Matrix();
        this.mEmbeddedWindowParams = new java.util.concurrent.ConcurrentLinkedQueue<>();
        this.mSurfaceControlViewHostParent = new android.view.SurfaceView.AnonymousClass1();
        this.mRtDrivenClipping = com.android.graphics.hwui.flags.Flags.clipSurfaceviews();
        this.mRTLastReportedPosition = new android.graphics.Rect();
        this.mRTLastSetCrop = new android.graphics.Rect();
        this.mPositionListener = null;
        this.mSurfaceHolder = new android.view.SurfaceView.AnonymousClass2();
        setWillNotDraw(true);
        this.mDisableBackgroundLayer = z;
    }

    public android.view.SurfaceHolder getHolder() {
        return this.mSurfaceHolder;
    }

    private void updateRequestedVisibility() {
        this.mRequestedVisible = this.mViewVisibility && this.mWindowVisibility && !this.mWindowStopped;
    }

    private void setWindowStopped(boolean z) {
        this.mWindowStopped = z;
        updateRequestedVisibility();
        updateSurface();
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewRootImpl().addSurfaceChangedCallback(this);
        this.mWindowStopped = false;
        this.mViewVisibility = getVisibility() == 0;
        updateRequestedVisibility();
        this.mAttachedToWindow = true;
        this.mParent.requestTransparentRegion(this);
        if (!this.mGlobalListenersAdded) {
            android.view.ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            viewTreeObserver.addOnScrollChangedListener(this.mScrollChangedListener);
            viewTreeObserver.addOnPreDrawListener(this.mDrawListener);
            this.mGlobalListenersAdded = true;
        }
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.mWindowVisibility = i == 0;
        updateRequestedVisibility();
        updateSurface();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        this.mViewVisibility = i == 0;
        boolean z = this.mWindowVisibility && this.mViewVisibility && !this.mWindowStopped;
        if (z != this.mRequestedVisible) {
            requestLayout();
        }
        this.mRequestedVisible = z;
        updateSurface();
    }

    public void setUseAlpha() {
    }

    @Override // android.view.View
    public void setAlpha(float f) {
        super.setAlpha(f);
    }

    @Override // android.view.View
    protected boolean onSetAlpha(int i) {
        if (java.lang.Math.round(this.mAlpha * 255.0f) != i) {
            updateSurface();
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performDrawFinished() {
        this.mDrawFinished = true;
        if (this.mAttachedToWindow) {
            this.mParent.requestTransparentRegion(this);
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.removeSurfaceChangedCallback(this);
        }
        this.mAttachedToWindow = false;
        if (this.mGlobalListenersAdded) {
            android.view.ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            viewTreeObserver.removeOnScrollChangedListener(this.mScrollChangedListener);
            viewTreeObserver.removeOnPreDrawListener(this.mDrawListener);
            this.mGlobalListenersAdded = false;
        }
        this.mRequestedVisible = false;
        updateSurface();
        releaseSurfaces(true);
        this.mHaveFrame = false;
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int defaultSize;
        int defaultSize2;
        if (this.mRequestedWidth >= 0) {
            defaultSize = resolveSizeAndState(this.mRequestedWidth, i, 0);
        } else {
            defaultSize = getDefaultSize(0, i);
        }
        if (this.mRequestedHeight >= 0) {
            defaultSize2 = resolveSizeAndState(this.mRequestedHeight, i2, 0);
        } else {
            defaultSize2 = getDefaultSize(0, i2);
        }
        setMeasuredDimension(defaultSize, defaultSize2);
    }

    @Override // android.view.View
    protected boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        updateSurface();
        return frame;
    }

    @Override // android.view.View
    public boolean gatherTransparentRegion(android.graphics.Region region) {
        boolean z;
        if (isAboveParent() || !this.mDrawFinished) {
            return super.gatherTransparentRegion(region);
        }
        if ((this.mPrivateFlags & 128) == 0) {
            z = super.gatherTransparentRegion(region);
        } else {
            if (region != null) {
                int width = getWidth();
                int height = getHeight();
                if (width > 0 && height > 0) {
                    getLocationInWindow(this.mLocation);
                    int i = this.mLocation[0];
                    int i2 = this.mLocation[1];
                    region.op(i, i2, i + width, i2 + height, android.graphics.Region.Op.UNION);
                }
            }
            z = true;
        }
        if (android.graphics.PixelFormat.formatHasAlpha(this.mRequestedFormat)) {
            return false;
        }
        return z;
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        if (this.mDrawFinished && !isAboveParent() && (this.mPrivateFlags & 128) == 0) {
            clearSurfaceViewPort(canvas);
        }
        super.draw(canvas);
    }

    @Override // android.view.View
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        if (this.mDrawFinished && !isAboveParent() && (this.mPrivateFlags & 128) == 128) {
            clearSurfaceViewPort(canvas);
        }
        super.dispatchDraw(canvas);
    }

    public void setEnableSurfaceClipping(boolean z) {
        this.mClipSurfaceToBounds = z;
        invalidate();
    }

    @Override // android.view.View
    public void setClipBounds(android.graphics.Rect rect) {
        super.setClipBounds(rect);
        if ((this.mRtDrivenClipping && isHardwareAccelerated()) || !this.mClipSurfaceToBounds || this.mSurfaceControl == null) {
            return;
        }
        if (this.mCornerRadius > 0.0f && !isAboveParent()) {
            invalidate();
        }
        if (this.mClipBounds != null) {
            this.mTmpRect.set(this.mClipBounds);
        } else {
            this.mTmpRect.set(0, 0, this.mSurfaceWidth, this.mSurfaceHeight);
        }
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        transaction.setWindowCrop(this.mSurfaceControl, this.mTmpRect);
        applyTransactionOnVriDraw(transaction);
        invalidate();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    private void clearSurfaceViewPort(android.graphics.Canvas canvas) {
        float alpha = getAlpha();
        if (this.mCornerRadius > 0.0f) {
            canvas.getClipBounds(this.mTmpRect);
            if (this.mClipSurfaceToBounds && this.mClipBounds != null) {
                this.mTmpRect.intersect(this.mClipBounds);
            }
            canvas.punchHole(this.mTmpRect.left, this.mTmpRect.top, this.mTmpRect.right, this.mTmpRect.bottom, this.mCornerRadius, this.mCornerRadius, alpha);
            return;
        }
        canvas.punchHole(0.0f, 0.0f, getWidth(), getHeight(), 0.0f, 0.0f, alpha);
    }

    public void setCornerRadius(float f) {
        this.mCornerRadius = f;
        if (this.mCornerRadius > 0.0f && this.mRoundedViewportPaint == null) {
            this.mRoundedViewportPaint = new android.graphics.Paint(1);
            this.mRoundedViewportPaint.setBlendMode(android.graphics.BlendMode.CLEAR);
            this.mRoundedViewportPaint.setColor(0);
        }
        invalidate();
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public void setZOrderMediaOverlay(boolean z) {
        this.mRequestedSubLayer = z ? -1 : -2;
    }

    public void setZOrderOnTop(boolean z) {
        setZOrderedOnTop(z, getContext().getApplicationInfo().targetSdkVersion > 29);
    }

    public boolean isZOrderedOnTop() {
        return this.mRequestedSubLayer > 0;
    }

    public boolean setZOrderedOnTop(boolean z, boolean z2) {
        int i;
        if (z) {
            i = 1;
        } else {
            i = -2;
        }
        if (this.mRequestedSubLayer == i) {
            return false;
        }
        this.mRequestedSubLayer = i;
        if (!z2) {
            return false;
        }
        if (this.mSurfaceControl == null || getViewRootImpl() == null) {
            return true;
        }
        updateSurface();
        invalidate();
        return true;
    }

    public void setSecure(boolean z) {
        if (z) {
            this.mSurfaceFlags |= 128;
        } else {
            this.mSurfaceFlags &= android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        }
    }

    public void setSurfaceLifecycle(int i) {
        this.mRequestedSurfaceLifecycleStrategy = i;
        updateSurface();
    }

    public void setDesiredHdrHeadroom(float f) {
        if (!java.lang.Float.isFinite(f)) {
            throw new java.lang.IllegalArgumentException("desiredHeadroom must be finite: " + f);
        }
        if (f != 0.0f && (f < 1.0f || f > 10000.0f)) {
            throw new java.lang.IllegalArgumentException("desiredHeadroom must be 0.0 or in the range [1.0, 10000.0f], received: " + f);
        }
        this.mRequestedHdrHeadroom = f;
        updateSurface();
        invalidate();
    }

    private void updateOpaqueFlag() {
        if (!android.graphics.PixelFormat.formatHasAlpha(this.mRequestedFormat)) {
            this.mSurfaceFlags |= 1024;
        } else {
            this.mSurfaceFlags &= -1025;
        }
    }

    private void updateBackgroundVisibility(android.view.SurfaceControl.Transaction transaction) {
        if (this.mBackgroundControl == null) {
            return;
        }
        if (this.mSubLayer < 0 && (this.mSurfaceFlags & 1024) != 0 && !this.mDisableBackgroundLayer) {
            transaction.show(this.mBackgroundControl);
        } else {
            transaction.hide(this.mBackgroundControl);
        }
    }

    private android.view.SurfaceControl.Transaction updateBackgroundColor(android.view.SurfaceControl.Transaction transaction) {
        transaction.setColor(this.mBackgroundControl, new float[]{android.graphics.Color.red(this.mBackgroundColor) / 255.0f, android.graphics.Color.green(this.mBackgroundColor) / 255.0f, android.graphics.Color.blue(this.mBackgroundColor) / 255.0f});
        return transaction;
    }

    private void releaseSurfaces(boolean z) {
        this.mAlpha = 1.0f;
        this.mSurface.destroy();
        synchronized (this.mSurfaceControlLock) {
            if (this.mBlastBufferQueue != null) {
                this.mBlastBufferQueue.destroy();
                this.mBlastBufferQueue = null;
            }
            android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
            if (this.mSurfaceControl != null) {
                transaction.remove(this.mSurfaceControl);
                this.mSurfaceControl = null;
            }
            if (this.mBackgroundControl != null) {
                transaction.remove(this.mBackgroundControl);
                this.mBackgroundControl = null;
            }
            if (this.mBlastSurfaceControl != null) {
                transaction.remove(this.mBlastSurfaceControl);
                this.mBlastSurfaceControl = null;
            }
            if (this.mSurfacePackage != null) {
                try {
                    this.mSurfacePackage.getRemoteInterface().attachParentInterface(null);
                    this.mEmbeddedWindowParams.clear();
                } catch (android.os.RemoteException e) {
                    android.util.Log.d(TAG, "Failed to remove parent interface from SCVH. Likely SCVH is already dead");
                }
                if (z) {
                    this.mSurfacePackage.release();
                    this.mSurfacePackage = null;
                }
            }
            applyTransactionOnVriDraw(transaction);
        }
    }

    private void replacePositionUpdateListener(int i, int i2) {
        if (this.mPositionListener != null) {
            this.mRenderNode.removePositionUpdateListener(this.mPositionListener);
        }
        this.mPositionListener = new android.view.SurfaceView.SurfaceViewPositionUpdateListener(i, i2);
        this.mRenderNode.addPositionUpdateListener(this.mPositionListener);
    }

    private boolean performSurfaceTransaction(android.view.ViewRootImpl viewRootImpl, android.content.res.CompatibilityInfo.Translator translator, boolean z, boolean z2, boolean z3, boolean z4, android.view.SurfaceControl.Transaction transaction) {
        this.mSurfaceLock.lock();
        try {
            boolean z5 = true;
            this.mDrawingStopped = !surfaceShouldExist();
            if (z) {
                updateRelativeZ(transaction);
                if (this.mSurfacePackage != null) {
                    reparentSurfacePackage(transaction, this.mSurfacePackage);
                }
            }
            this.mParentSurfaceSequenceId = viewRootImpl.getSurfaceSequenceId();
            if (!isHardwareAccelerated()) {
                if (this.mViewVisibility) {
                    transaction.show(this.mSurfaceControl);
                } else {
                    transaction.hide(this.mSurfaceControl);
                }
            }
            updateBackgroundVisibility(transaction);
            updateBackgroundColor(transaction);
            if (this.mLimitedHdrEnabled) {
                transaction.setDesiredHdrHeadroom(this.mBlastSurfaceControl, this.mHdrHeadroom);
            }
            if (isAboveParent()) {
                transaction.setAlpha(this.mSurfaceControl, getAlpha());
            }
            if (z4) {
                if (!isAboveParent()) {
                    transaction.setAlpha(this.mSurfaceControl, 1.0f);
                }
                updateRelativeZ(transaction);
            }
            transaction.setCornerRadius(this.mSurfaceControl, this.mCornerRadius);
            if ((z2 || z3) && !z) {
                setBufferSize(transaction);
            }
            if (z2 || z || !isHardwareAccelerated()) {
                if (!this.mRtDrivenClipping || !isHardwareAccelerated()) {
                    if (!this.mClipSurfaceToBounds || this.mClipBounds == null) {
                        transaction.setWindowCrop(this.mSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight);
                    } else {
                        transaction.setWindowCrop(this.mSurfaceControl, this.mClipBounds);
                    }
                }
                transaction.setDesintationFrame(this.mBlastSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight);
                if (isHardwareAccelerated()) {
                    replacePositionUpdateListener(this.mSurfaceWidth, this.mSurfaceHeight);
                } else {
                    onSetSurfacePositionAndScale(transaction, this.mSurfaceControl, this.mScreenRect.left, this.mScreenRect.top, this.mScreenRect.width() / this.mSurfaceWidth, this.mScreenRect.height() / this.mSurfaceHeight);
                }
            }
            applyTransactionOnVriDraw(transaction);
            updateEmbeddedAccessibilityMatrix(false);
            this.mSurfaceFrame.left = 0;
            this.mSurfaceFrame.top = 0;
            if (translator == null) {
                this.mSurfaceFrame.right = this.mSurfaceWidth;
                this.mSurfaceFrame.bottom = this.mSurfaceHeight;
            } else {
                float f = translator.applicationInvertedScale;
                this.mSurfaceFrame.right = (int) ((this.mSurfaceWidth * f) + 0.5f);
                this.mSurfaceFrame.bottom = (int) ((this.mSurfaceHeight * f) + 0.5f);
            }
            int i = this.mSurfaceFrame.right;
            int i2 = this.mSurfaceFrame.bottom;
            if (this.mLastSurfaceWidth == i && this.mLastSurfaceHeight == i2) {
                z5 = false;
            }
            this.mLastSurfaceWidth = i;
            this.mLastSurfaceHeight = i2;
            return z5;
        } finally {
            this.mSurfaceLock.unlock();
        }
    }

    private boolean requiresSurfaceControlCreation(boolean z, boolean z2) {
        return this.mSurfaceLifecycleStrategy == 2 ? (this.mSurfaceControl == null || z) && this.mAttachedToWindow : (this.mSurfaceControl == null || z || z2) && this.mRequestedVisible;
    }

    private boolean surfaceShouldExist() {
        boolean z = this.mSurfaceLifecycleStrategy != 2;
        if (this.mVisible) {
            return true;
        }
        return !z && this.mAttachedToWindow;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x01ca A[Catch: Exception -> 0x02bc, TryCatch #0 {Exception -> 0x02bc, blocks: (B:78:0x00f3, B:80:0x014d, B:81:0x0157, B:83:0x016d, B:87:0x01a1, B:89:0x01a5, B:96:0x01b5, B:98:0x01bb, B:103:0x01ca, B:104:0x01e3, B:160:0x029c, B:162:0x02a2, B:164:0x02a6, B:171:0x02ac, B:173:0x02b3, B:175:0x02b7, B:176:0x02bb, B:180:0x0195, B:106:0x01fb, B:114:0x020f, B:118:0x0217, B:121:0x021d, B:125:0x0225, B:126:0x022b, B:128:0x0234, B:130:0x023c, B:135:0x0246, B:137:0x0253, B:148:0x028c, B:150:0x0292, B:151:0x0298, B:153:0x026f, B:154:0x0274, B:156:0x0278), top: B:77:0x00f3, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x028a  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x026f A[Catch: all -> 0x02ab, TryCatch #1 {all -> 0x02ab, blocks: (B:106:0x01fb, B:114:0x020f, B:118:0x0217, B:121:0x021d, B:125:0x0225, B:126:0x022b, B:128:0x0234, B:130:0x023c, B:135:0x0246, B:137:0x0253, B:148:0x028c, B:150:0x0292, B:151:0x0298, B:153:0x026f, B:154:0x0274, B:156:0x0278), top: B:105:0x01fb, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0278 A[Catch: all -> 0x02ab, LOOP:1: B:155:0x0276->B:156:0x0278, LOOP_END, TryCatch #1 {all -> 0x02ab, blocks: (B:106:0x01fb, B:114:0x020f, B:118:0x0217, B:121:0x021d, B:125:0x0225, B:126:0x022b, B:128:0x0234, B:130:0x023c, B:135:0x0246, B:137:0x0253, B:148:0x028c, B:150:0x0292, B:151:0x0298, B:153:0x026f, B:154:0x0274, B:156:0x0278), top: B:105:0x01fb, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0202  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x01e2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void updateSurface() {
        android.view.ViewRootImpl viewRootImpl;
        android.content.res.CompatibilityInfo.Translator translator;
        boolean z;
        boolean z2;
        android.view.SurfaceView.SyncBufferTransactionCallback syncBufferTransactionCallback;
        android.view.SurfaceHolder.Callback[] callbackArr;
        int length;
        int i;
        if (!this.mHaveFrame || (viewRootImpl = getViewRootImpl()) == null) {
            return;
        }
        if (viewRootImpl.mSurface == null || !viewRootImpl.mSurface.isValid()) {
            notifySurfaceDestroyed();
            releaseSurfaces(false);
            return;
        }
        android.content.res.CompatibilityInfo.Translator translator2 = viewRootImpl.mTranslator;
        if (translator2 != null) {
            this.mSurface.setCompatibilityTranslator(translator2);
        }
        int i2 = this.mRequestedWidth;
        if (i2 <= 0) {
            i2 = getWidth();
        }
        int i3 = this.mRequestedHeight;
        if (i3 <= 0) {
            i3 = getHeight();
        }
        int i4 = i3;
        float alpha = getAlpha();
        boolean z3 = this.mFormat != this.mRequestedFormat;
        boolean z4 = this.mVisible != this.mRequestedVisible;
        boolean z5 = this.mAlpha != alpha;
        boolean requiresSurfaceControlCreation = requiresSurfaceControlCreation(z3, z4);
        boolean z6 = (this.mSurfaceWidth == i2 && this.mSurfaceHeight == i4) ? false : true;
        boolean z7 = this.mWindowVisibility != this.mLastWindowVisibility;
        getLocationInWindow(this.mLocation);
        boolean z8 = (this.mWindowSpaceLeft == this.mLocation[0] && this.mWindowSpaceTop == this.mLocation[1]) ? false : true;
        boolean z9 = (getWidth() == this.mScreenRect.width() && getHeight() == this.mScreenRect.height()) ? false : true;
        boolean z10 = viewRootImpl.getBufferTransformHint() != this.mTransformHint && this.mRequestedVisible;
        boolean z11 = this.mSubLayer != this.mRequestedSubLayer;
        boolean z12 = this.mSurfaceLifecycleStrategy != this.mRequestedSurfaceLifecycleStrategy;
        boolean z13 = this.mHdrHeadroom != this.mRequestedHdrHeadroom;
        if (requiresSurfaceControlCreation || z3 || z6 || z4 || z5 || z7 || z8 || z9 || z10 || z11 || !this.mAttachedToWindow || z12 || z13) {
            try {
                this.mVisible = this.mRequestedVisible;
                this.mWindowSpaceLeft = this.mLocation[0];
                this.mWindowSpaceTop = this.mLocation[1];
                this.mSurfaceWidth = i2;
                this.mSurfaceHeight = i4;
                this.mFormat = this.mRequestedFormat;
                this.mAlpha = alpha;
                this.mLastWindowVisibility = this.mWindowVisibility;
                this.mTransformHint = viewRootImpl.getBufferTransformHint();
                this.mSubLayer = this.mRequestedSubLayer;
                int i5 = this.mSurfaceLifecycleStrategy;
                this.mSurfaceLifecycleStrategy = this.mRequestedSurfaceLifecycleStrategy;
                this.mHdrHeadroom = this.mRequestedHdrHeadroom;
                this.mScreenRect.left = this.mWindowSpaceLeft;
                this.mScreenRect.top = this.mWindowSpaceTop;
                this.mScreenRect.right = this.mWindowSpaceLeft + getWidth();
                this.mScreenRect.bottom = this.mWindowSpaceTop + getHeight();
                if (translator2 == null) {
                    translator = translator2;
                } else {
                    translator = translator2;
                    translator.translateRectInAppWindowToScreen(this.mScreenRect);
                }
                android.graphics.Rect rect = viewRootImpl.mWindowAttributes.surfaceInsets;
                this.mScreenRect.offset(rect.left, rect.top);
                android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
                if (requiresSurfaceControlCreation) {
                    updateOpaqueFlag();
                    createBlastSurfaceControls(viewRootImpl, "SurfaceView[" + viewRootImpl.getTitle().toString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END, transaction);
                } else if (this.mSurfaceControl == null) {
                    return;
                }
                try {
                    if (!z6 && !requiresSurfaceControlCreation && !z10 && ((!this.mVisible || this.mDrawFinished) && !z5 && !z11)) {
                        z = false;
                        z2 = !z && viewRootImpl.wasRelayoutRequested() && viewRootImpl.isInWMSRequestedSync();
                        if (z2) {
                            syncBufferTransactionCallback = null;
                        } else {
                            final android.view.SurfaceView.SyncBufferTransactionCallback syncBufferTransactionCallback2 = new android.view.SurfaceView.SyncBufferTransactionCallback();
                            android.graphics.BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
                            java.util.Objects.requireNonNull(syncBufferTransactionCallback2);
                            bLASTBufferQueue.syncNextTransaction(false, new java.util.function.Consumer() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda4
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    android.view.SurfaceView.SyncBufferTransactionCallback.this.onTransactionReady((android.view.SurfaceControl.Transaction) obj);
                                }
                            });
                            syncBufferTransactionCallback = syncBufferTransactionCallback2;
                        }
                        android.view.SurfaceView.SyncBufferTransactionCallback syncBufferTransactionCallback3 = syncBufferTransactionCallback;
                        int i6 = i2;
                        boolean z14 = z11;
                        boolean z15 = z6;
                        boolean performSurfaceTransaction = performSurfaceTransaction(viewRootImpl, translator, requiresSurfaceControlCreation, z6, z10, z14, transaction);
                        boolean z16 = this.mSurfaceLifecycleStrategy == 2;
                        boolean z17 = !z16 && (i5 != 2);
                        if (this.mSurfaceCreated && (requiresSurfaceControlCreation || ((!z16 && !this.mAttachedToWindow) || (z16 && !this.mVisible && (z4 || z17))))) {
                            this.mSurfaceCreated = false;
                            notifySurfaceDestroyed();
                        }
                        copySurface(requiresSurfaceControlCreation, z15);
                        if (surfaceShouldExist() && this.mSurface.isValid()) {
                            if (this.mSurfaceCreated && (requiresSurfaceControlCreation || (z16 && z4))) {
                                this.mSurfaceCreated = true;
                                this.mIsCreating = true;
                                callbackArr = getSurfaceCallbacks();
                                for (android.view.SurfaceHolder.Callback callback : callbackArr) {
                                    callback.surfaceCreated(this.mSurfaceHolder);
                                }
                            } else {
                                callbackArr = null;
                            }
                            if (!requiresSurfaceControlCreation || z3 || z15 || z10 || ((z16 && z4) || performSurfaceTransaction)) {
                                if (callbackArr == null) {
                                    callbackArr = getSurfaceCallbacks();
                                }
                                length = callbackArr.length;
                                i = 0;
                                while (i < length) {
                                    int i7 = i6;
                                    callbackArr[i].surfaceChanged(this.mSurfaceHolder, this.mFormat, i7, i4);
                                    i++;
                                    i6 = i7;
                                }
                            }
                            if (z) {
                                if (callbackArr == null) {
                                    callbackArr = getSurfaceCallbacks();
                                }
                                if (z2) {
                                    handleSyncBufferCallback(callbackArr, syncBufferTransactionCallback3);
                                } else {
                                    handleSyncNoBuffer(callbackArr);
                                }
                            }
                        }
                        this.mIsCreating = false;
                        if (this.mSurfaceControl != null && !this.mSurfaceCreated) {
                            releaseSurfaces(false);
                        }
                        return;
                    }
                    if (this.mSurfaceLifecycleStrategy == 2) {
                    }
                    if (z16) {
                    }
                    if (this.mSurfaceCreated) {
                        this.mSurfaceCreated = false;
                        notifySurfaceDestroyed();
                    }
                    copySurface(requiresSurfaceControlCreation, z15);
                    if (surfaceShouldExist()) {
                        if (this.mSurfaceCreated) {
                        }
                        callbackArr = null;
                        if (!requiresSurfaceControlCreation) {
                        }
                        if (callbackArr == null) {
                        }
                        length = callbackArr.length;
                        i = 0;
                        while (i < length) {
                        }
                        if (z) {
                        }
                    }
                    this.mIsCreating = false;
                    if (this.mSurfaceControl != null) {
                        releaseSurfaces(false);
                    }
                    return;
                } catch (java.lang.Throwable th) {
                    this.mIsCreating = false;
                    if (this.mSurfaceControl != null && !this.mSurfaceCreated) {
                        releaseSurfaces(false);
                    }
                    throw th;
                }
                z = true;
                if (z) {
                }
                if (z2) {
                }
                android.view.SurfaceView.SyncBufferTransactionCallback syncBufferTransactionCallback32 = syncBufferTransactionCallback;
                int i62 = i2;
                boolean z142 = z11;
                boolean z152 = z6;
                boolean performSurfaceTransaction2 = performSurfaceTransaction(viewRootImpl, translator, requiresSurfaceControlCreation, z6, z10, z142, transaction);
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "Exception configuring surface", e);
            }
        }
    }

    public java.lang.String getName() {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        return "SurfaceView[" + (viewRootImpl == null ? "detached" : viewRootImpl.getTitle().toString()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    private void handleSyncBufferCallback(android.view.SurfaceHolder.Callback[] callbackArr, final android.view.SurfaceView.SyncBufferTransactionCallback syncBufferTransactionCallback) {
        final android.window.SurfaceSyncGroup surfaceSyncGroup = new android.window.SurfaceSyncGroup(getName());
        getViewRootImpl().addToSync(surfaceSyncGroup);
        redrawNeededAsync(callbackArr, new java.lang.Runnable() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                android.view.SurfaceView.this.lambda$handleSyncBufferCallback$1(syncBufferTransactionCallback, surfaceSyncGroup);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSyncBufferCallback$1(android.view.SurfaceView.SyncBufferTransactionCallback syncBufferTransactionCallback, android.window.SurfaceSyncGroup surfaceSyncGroup) {
        android.view.SurfaceControl.Transaction transaction;
        if (this.mBlastBufferQueue == null) {
            transaction = null;
        } else {
            this.mBlastBufferQueue.stopContinuousSyncTransaction();
            transaction = syncBufferTransactionCallback.waitForTransaction();
        }
        surfaceSyncGroup.addTransaction(transaction);
        surfaceSyncGroup.markSyncReady();
        onDrawFinished();
    }

    private void handleSyncNoBuffer(android.view.SurfaceHolder.Callback[] callbackArr) {
        final android.window.SurfaceSyncGroup surfaceSyncGroup = new android.window.SurfaceSyncGroup(getName());
        synchronized (this.mSyncGroups) {
            this.mSyncGroups.add(surfaceSyncGroup);
        }
        redrawNeededAsync(callbackArr, new java.lang.Runnable() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                android.view.SurfaceView.this.lambda$handleSyncNoBuffer$2(surfaceSyncGroup);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSyncNoBuffer$2(android.window.SurfaceSyncGroup surfaceSyncGroup) {
        synchronized (this.mSyncGroups) {
            this.mSyncGroups.remove(surfaceSyncGroup);
        }
        surfaceSyncGroup.markSyncReady();
        onDrawFinished();
    }

    private void redrawNeededAsync(android.view.SurfaceHolder.Callback[] callbackArr, java.lang.Runnable runnable) {
        new com.android.internal.view.SurfaceCallbackHelper(runnable).dispatchSurfaceRedrawNeededAsync(this.mSurfaceHolder, callbackArr);
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void vriDrawStarted(boolean z) {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        synchronized (this.mSyncGroups) {
            if (z && viewRootImpl != null) {
                java.util.Iterator<android.window.SurfaceSyncGroup> it = this.mSyncGroups.iterator();
                while (it.hasNext()) {
                    viewRootImpl.addToSync(it.next());
                }
            }
            this.mSyncGroups.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SyncBufferTransactionCallback {
        private final java.util.concurrent.CountDownLatch mCountDownLatch;
        private android.view.SurfaceControl.Transaction mTransaction;

        private SyncBufferTransactionCallback() {
            this.mCountDownLatch = new java.util.concurrent.CountDownLatch(1);
        }

        android.view.SurfaceControl.Transaction waitForTransaction() {
            try {
                this.mCountDownLatch.await();
            } catch (java.lang.InterruptedException e) {
            }
            return this.mTransaction;
        }

        void onTransactionReady(android.view.SurfaceControl.Transaction transaction) {
            this.mTransaction = transaction;
            this.mCountDownLatch.countDown();
        }
    }

    private void copySurface(boolean z, boolean z2) {
        if (z) {
            this.mSurface.copyFrom(this.mBlastBufferQueue);
        }
        if (z2 && getContext().getApplicationInfo().targetSdkVersion < 26 && this.mBlastBufferQueue != null) {
            this.mSurface.transferFrom(this.mBlastBufferQueue.createSurfaceWithHandle());
        }
    }

    private void setBufferSize(android.view.SurfaceControl.Transaction transaction) {
        this.mBlastSurfaceControl.setTransformHint(this.mTransformHint);
        if (this.mBlastBufferQueue != null) {
            this.mBlastBufferQueue.update(this.mBlastSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight, this.mFormat);
        }
    }

    private void createBlastSurfaceControls(android.view.ViewRootImpl viewRootImpl, java.lang.String str, android.view.SurfaceControl.Transaction transaction) {
        if (this.mSurfaceControl == null) {
            this.mSurfaceControl = new android.view.SurfaceControl.Builder(this.mSurfaceSession).setName(str).setLocalOwnerView(this).setParent(viewRootImpl.updateAndGetBoundsLayer(transaction)).setCallsite("SurfaceView.updateSurface").setContainerLayer().build();
        }
        if (this.mBlastSurfaceControl == null) {
            this.mBlastSurfaceControl = new android.view.SurfaceControl.Builder(this.mSurfaceSession).setName(str + "(BLAST)").setLocalOwnerView(this).setParent(this.mSurfaceControl).setFlags(this.mSurfaceFlags).setHidden(false).setBLASTLayer().setCallsite("SurfaceView.updateSurface").build();
        } else {
            transaction.setOpaque(this.mBlastSurfaceControl, (this.mSurfaceFlags & 1024) != 0).setSecure(this.mBlastSurfaceControl, (this.mSurfaceFlags & 128) != 0).show(this.mBlastSurfaceControl);
        }
        if (this.mBackgroundControl == null) {
            this.mBackgroundControl = new android.view.SurfaceControl.Builder(this.mSurfaceSession).setName("Background for " + str).setLocalOwnerView(this).setOpaque(true).setColorLayer().setParent(this.mSurfaceControl).setCallsite("SurfaceView.updateSurface").build();
        }
        if (this.mBlastBufferQueue != null) {
            this.mBlastBufferQueue.destroy();
        }
        this.mTransformHint = viewRootImpl.getBufferTransformHint();
        this.mBlastSurfaceControl.setTransformHint(this.mTransformHint);
        this.mBlastBufferQueue = new android.graphics.BLASTBufferQueue(str, false);
        this.mBlastBufferQueue.update(this.mBlastSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight, this.mFormat);
        this.mBlastBufferQueue.setTransactionHangCallback(android.view.ViewRootImpl.sTransactionHangCallback);
    }

    private void onDrawFinished() {
        runOnUiThread(new java.lang.Runnable() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                android.view.SurfaceView.this.performDrawFinished();
            }
        });
    }

    protected void onSetSurfacePositionAndScale(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, int i, int i2, float f, float f2) {
        transaction.setPosition(surfaceControl, i, i2);
        transaction.setMatrix(surfaceControl, f, 0.0f, 0.0f, f2);
    }

    public void requestUpdateSurfacePositionAndScale() {
        if (this.mSurfaceControl == null) {
            return;
        }
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        onSetSurfacePositionAndScale(transaction, this.mSurfaceControl, this.mScreenRect.left, this.mScreenRect.top, this.mScreenRect.width() / this.mSurfaceWidth, this.mScreenRect.height() / this.mSurfaceHeight);
        applyTransactionOnVriDraw(transaction);
        invalidate();
    }

    public android.graphics.Rect getSurfaceRenderPosition() {
        return this.mRTLastReportedPosition;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyOrMergeTransaction(android.view.SurfaceControl.Transaction transaction, long j) {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.mergeWithNextTransaction(transaction, j);
        } else {
            transaction.apply();
        }
    }

    private class SurfaceViewPositionUpdateListener implements android.graphics.RenderNode.PositionUpdateListener {
        private final android.view.SurfaceControl.Transaction mPositionChangedTransaction = new android.view.SurfaceControl.Transaction();
        private final int mRtSurfaceHeight;
        private final int mRtSurfaceWidth;

        SurfaceViewPositionUpdateListener(int i, int i2) {
            this.mRtSurfaceWidth = i;
            this.mRtSurfaceHeight = i2;
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionChanged(long j, int i, int i2, int i3, int i4) {
            try {
                synchronized (android.view.SurfaceView.this.mSurfaceControlLock) {
                    if (android.view.SurfaceView.this.mSurfaceControl == null) {
                        return;
                    }
                    android.view.SurfaceView.this.mRTLastReportedPosition.set(i, i2, i3, i4);
                    android.view.SurfaceView.this.onSetSurfacePositionAndScale(this.mPositionChangedTransaction, android.view.SurfaceView.this.mSurfaceControl, android.view.SurfaceView.this.mRTLastReportedPosition.left, android.view.SurfaceView.this.mRTLastReportedPosition.top, android.view.SurfaceView.this.mRTLastReportedPosition.width() / this.mRtSurfaceWidth, android.view.SurfaceView.this.mRTLastReportedPosition.height() / this.mRtSurfaceHeight);
                    this.mPositionChangedTransaction.show(android.view.SurfaceView.this.mSurfaceControl);
                    android.view.SurfaceView.this.applyOrMergeTransaction(this.mPositionChangedTransaction, j);
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(android.view.SurfaceView.TAG, "Exception from repositionChild", e);
            }
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionChanged(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            try {
                synchronized (android.view.SurfaceView.this.mSurfaceControlLock) {
                    if (android.view.SurfaceView.this.mSurfaceControl == null) {
                        return;
                    }
                    android.view.SurfaceView.this.mRTLastReportedPosition.set(i, i2, i3, i4);
                    float width = android.view.SurfaceView.this.mRTLastReportedPosition.width() / this.mRtSurfaceWidth;
                    float height = android.view.SurfaceView.this.mRTLastReportedPosition.height() / this.mRtSurfaceHeight;
                    android.view.SurfaceView.this.onSetSurfacePositionAndScale(this.mPositionChangedTransaction, android.view.SurfaceView.this.mSurfaceControl, android.view.SurfaceView.this.mRTLastReportedPosition.left, android.view.SurfaceView.this.mRTLastReportedPosition.top, width, height);
                    android.view.SurfaceView.this.mRTLastSetCrop.set((int) (i5 / width), (int) (i6 / height), (int) java.lang.Math.ceil(i7 / width), (int) java.lang.Math.ceil(i8 / height));
                    this.mPositionChangedTransaction.setCrop(android.view.SurfaceView.this.mSurfaceControl, android.view.SurfaceView.this.mRTLastSetCrop);
                    if (android.view.SurfaceView.this.mRTLastSetCrop.isEmpty()) {
                        this.mPositionChangedTransaction.hide(android.view.SurfaceView.this.mSurfaceControl);
                    } else {
                        this.mPositionChangedTransaction.show(android.view.SurfaceView.this.mSurfaceControl);
                    }
                    android.view.SurfaceView.this.applyOrMergeTransaction(this.mPositionChangedTransaction, j);
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(android.view.SurfaceView.TAG, "Exception from repositionChild", e);
            }
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void applyStretch(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            android.view.SurfaceView.this.mRtTransaction.setStretchEffect(android.view.SurfaceView.this.mSurfaceControl, f, f2, f3, f4, f5, f6, f7, f8, f9, f10);
            android.view.SurfaceView.this.applyOrMergeTransaction(android.view.SurfaceView.this.mRtTransaction, j);
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionLost(long j) {
            android.view.SurfaceView.this.mRTLastReportedPosition.setEmpty();
            synchronized (android.view.SurfaceView.this.mSurfaceControlLock) {
                if (android.view.SurfaceView.this.mSurfaceControl == null) {
                    return;
                }
                android.view.SurfaceView.this.mRtTransaction.hide(android.view.SurfaceView.this.mSurfaceControl);
                android.view.SurfaceView.this.applyOrMergeTransaction(android.view.SurfaceView.this.mRtTransaction, j);
            }
        }
    }

    private android.view.SurfaceHolder.Callback[] getSurfaceCallbacks() {
        android.view.SurfaceHolder.Callback[] callbackArr;
        synchronized (this.mCallbacks) {
            callbackArr = new android.view.SurfaceHolder.Callback[this.mCallbacks.size()];
            this.mCallbacks.toArray(callbackArr);
        }
        return callbackArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runOnUiThread(java.lang.Runnable runnable) {
        android.os.Handler handler = getHandler();
        if (handler != null && handler.getLooper() != android.os.Looper.myLooper()) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public boolean isFixedSize() {
        return (this.mRequestedWidth == -1 && this.mRequestedHeight == -1) ? false : true;
    }

    private boolean isAboveParent() {
        return this.mSubLayer >= 0;
    }

    public void setResizeBackgroundColor(int i) {
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        setResizeBackgroundColor(transaction, i);
        applyTransactionOnVriDraw(transaction);
        invalidate();
    }

    public void setResizeBackgroundColor(android.view.SurfaceControl.Transaction transaction, int i) {
        if (this.mBackgroundControl == null) {
            return;
        }
        this.mBackgroundColor = i;
        updateBackgroundColor(transaction);
    }

    /* renamed from: android.view.SurfaceView$2, reason: invalid class name */
    class AnonymousClass2 implements android.view.SurfaceHolder {
        private static final java.lang.String LOG_TAG = "SurfaceHolder";

        AnonymousClass2() {
        }

        @Override // android.view.SurfaceHolder
        public boolean isCreating() {
            return android.view.SurfaceView.this.mIsCreating;
        }

        @Override // android.view.SurfaceHolder
        public void addCallback(android.view.SurfaceHolder.Callback callback) {
            synchronized (android.view.SurfaceView.this.mCallbacks) {
                if (!android.view.SurfaceView.this.mCallbacks.contains(callback)) {
                    android.view.SurfaceView.this.mCallbacks.add(callback);
                }
            }
        }

        @Override // android.view.SurfaceHolder
        public void removeCallback(android.view.SurfaceHolder.Callback callback) {
            synchronized (android.view.SurfaceView.this.mCallbacks) {
                android.view.SurfaceView.this.mCallbacks.remove(callback);
            }
        }

        @Override // android.view.SurfaceHolder
        public void setFixedSize(int i, int i2) {
            if (android.view.SurfaceView.this.mRequestedWidth != i || android.view.SurfaceView.this.mRequestedHeight != i2) {
                android.view.SurfaceView.this.mRequestedWidth = i;
                android.view.SurfaceView.this.mRequestedHeight = i2;
                android.view.SurfaceView.this.requestLayout();
            }
        }

        @Override // android.view.SurfaceHolder
        public void setSizeFromLayout() {
            if (android.view.SurfaceView.this.mRequestedWidth != -1 || android.view.SurfaceView.this.mRequestedHeight != -1) {
                android.view.SurfaceView surfaceView = android.view.SurfaceView.this;
                android.view.SurfaceView.this.mRequestedHeight = -1;
                surfaceView.mRequestedWidth = -1;
                android.view.SurfaceView.this.requestLayout();
            }
        }

        @Override // android.view.SurfaceHolder
        public void setFormat(int i) {
            if (i == -1) {
                i = 4;
            }
            android.view.SurfaceView.this.mRequestedFormat = i;
            if (android.view.SurfaceView.this.mSurfaceControl != null) {
                android.view.SurfaceView.this.updateSurface();
            }
        }

        @Override // android.view.SurfaceHolder
        @java.lang.Deprecated
        public void setType(int i) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setKeepScreenOn$0(boolean z) {
            android.view.SurfaceView.this.setKeepScreenOn(z);
        }

        @Override // android.view.SurfaceHolder
        public void setKeepScreenOn(final boolean z) {
            android.view.SurfaceView.this.runOnUiThread(new java.lang.Runnable() { // from class: android.view.SurfaceView$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.SurfaceView.AnonymousClass2.this.lambda$setKeepScreenOn$0(z);
                }
            });
        }

        @Override // android.view.SurfaceHolder
        public android.graphics.Canvas lockCanvas() {
            return internalLockCanvas(null, false);
        }

        @Override // android.view.SurfaceHolder
        public android.graphics.Canvas lockCanvas(android.graphics.Rect rect) {
            return internalLockCanvas(rect, false);
        }

        @Override // android.view.SurfaceHolder
        public android.graphics.Canvas lockHardwareCanvas() {
            return internalLockCanvas(null, true);
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0034  */
        /* JADX WARN: Removed duplicated region for block: B:13:0x003d  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private android.graphics.Canvas internalLockCanvas(android.graphics.Rect rect, boolean z) {
            android.graphics.Canvas canvas;
            android.view.SurfaceView.this.mSurfaceLock.lock();
            if (!android.view.SurfaceView.this.mDrawingStopped && android.view.SurfaceView.this.mSurfaceControl != null) {
                try {
                    if (z) {
                        canvas = android.view.SurfaceView.this.mSurface.lockHardwareCanvas();
                    } else {
                        canvas = android.view.SurfaceView.this.mSurface.lockCanvas(rect);
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.e(LOG_TAG, "Exception locking surface", e);
                }
                if (canvas == null) {
                    android.view.SurfaceView.this.mLastLockTime = android.os.SystemClock.uptimeMillis();
                    return canvas;
                }
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                long j = android.view.SurfaceView.this.mLastLockTime + android.view.SurfaceView.FORWARD_BACK_KEY_TOLERANCE_MS;
                if (j > uptimeMillis) {
                    try {
                        java.lang.Thread.sleep(j - uptimeMillis);
                    } catch (java.lang.InterruptedException e2) {
                    }
                    uptimeMillis = android.os.SystemClock.uptimeMillis();
                }
                android.view.SurfaceView.this.mLastLockTime = uptimeMillis;
                android.view.SurfaceView.this.mSurfaceLock.unlock();
                return null;
            }
            canvas = null;
            if (canvas == null) {
            }
        }

        @Override // android.view.SurfaceHolder
        public void unlockCanvasAndPost(android.graphics.Canvas canvas) {
            try {
                android.view.SurfaceView.this.mSurface.unlockCanvasAndPost(canvas);
            } finally {
                android.view.SurfaceView.this.mSurfaceLock.unlock();
            }
        }

        @Override // android.view.SurfaceHolder
        public android.view.Surface getSurface() {
            return android.view.SurfaceView.this.mSurface;
        }

        @Override // android.view.SurfaceHolder
        public android.graphics.Rect getSurfaceFrame() {
            return android.view.SurfaceView.this.mSurfaceFrame;
        }
    }

    public android.view.SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    public android.os.IBinder getHostToken() {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return null;
        }
        return viewRootImpl.getInputToken();
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void surfaceCreated(android.view.SurfaceControl.Transaction transaction) {
        setWindowStopped(false);
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void surfaceDestroyed() {
        setWindowStopped(true);
        this.mRemoteAccessibilityController.disassosciateHierarchy();
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void surfaceReplaced(android.view.SurfaceControl.Transaction transaction) {
        if (this.mSurfaceControl != null && this.mBackgroundControl != null) {
            updateRelativeZ(transaction);
        }
    }

    private void updateRelativeZ(android.view.SurfaceControl.Transaction transaction) {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return;
        }
        android.view.SurfaceControl surfaceControl = viewRootImpl.getSurfaceControl();
        transaction.setRelativeLayer(this.mBackgroundControl, surfaceControl, Integer.MIN_VALUE);
        transaction.setRelativeLayer(this.mSurfaceControl, surfaceControl, this.mSubLayer);
    }

    public void setChildSurfacePackage(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
        android.view.SurfaceControl surfaceControl = this.mSurfacePackage != null ? this.mSurfacePackage.getSurfaceControl() : null;
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        if (this.mSurfaceControl != null) {
            if (surfaceControl != null) {
                transaction.reparent(surfaceControl, null);
                this.mSurfacePackage.release();
            }
            reparentSurfacePackage(transaction, surfacePackage);
            applyTransactionOnVriDraw(transaction);
        }
        this.mSurfacePackage = surfacePackage;
        try {
            this.mSurfacePackage.getRemoteInterface().attachParentInterface(this.mSurfaceControlViewHostParent);
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "Failed to attach parent interface to SCVH. Likely SCVH is already dead.");
        }
        if (isFocused()) {
            requestEmbeddedFocus(true);
        }
        invalidate();
    }

    private void reparentSurfacePackage(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
        android.view.SurfaceControl surfaceControl = surfacePackage.getSurfaceControl();
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        initEmbeddedHierarchyForAccessibility(surfacePackage);
        transaction.reparent(surfaceControl, this.mBlastSurfaceControl).show(surfaceControl);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (!this.mRemoteAccessibilityController.connected()) {
            return;
        }
        accessibilityNodeInfo.addChild(this.mRemoteAccessibilityController.getLeashToken());
    }

    @Override // android.view.View
    public int getImportantForAccessibility() {
        int importantForAccessibility = super.getImportantForAccessibility();
        if ((this.mRemoteAccessibilityController != null && !this.mRemoteAccessibilityController.connected()) || importantForAccessibility != 0) {
            return importantForAccessibility;
        }
        return 1;
    }

    private void initEmbeddedHierarchyForAccessibility(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
        android.view.accessibility.IAccessibilityEmbeddedConnection accessibilityEmbeddedConnection = surfacePackage.getAccessibilityEmbeddedConnection();
        if (this.mRemoteAccessibilityController.alreadyAssociated(accessibilityEmbeddedConnection)) {
            return;
        }
        this.mRemoteAccessibilityController.assosciateHierarchy(accessibilityEmbeddedConnection, getViewRootImpl().mLeashToken, getAccessibilityViewId());
        updateEmbeddedAccessibilityMatrix(true);
    }

    private void notifySurfaceDestroyed() {
        if (this.mSurface.isValid()) {
            for (android.view.SurfaceHolder.Callback callback : getSurfaceCallbacks()) {
                callback.surfaceDestroyed(this.mSurfaceHolder);
            }
            if (this.mSurface.isValid()) {
                this.mSurface.forceScopedDisconnect();
            }
        }
    }

    void updateEmbeddedAccessibilityMatrix(boolean z) {
        if (!this.mRemoteAccessibilityController.connected()) {
            return;
        }
        getBoundsOnScreen(this.mTmpRect);
        this.mTmpRect.offset(-this.mAttachInfo.mWindowLeft, -this.mAttachInfo.mWindowTop);
        this.mTmpMatrix.reset();
        this.mTmpMatrix.setTranslate(this.mTmpRect.left, this.mTmpRect.top);
        this.mTmpMatrix.postScale(this.mScreenRect.width() / this.mSurfaceWidth, this.mScreenRect.height() / this.mSurfaceHeight);
        this.mRemoteAccessibilityController.setWindowMatrix(this.mTmpMatrix, z);
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
        super.onFocusChanged(z, i, rect);
        requestEmbeddedFocus(z);
    }

    private void requestEmbeddedFocus(boolean z) {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (this.mSurfacePackage == null || viewRootImpl == null) {
            return;
        }
        try {
            viewRootImpl.mWindowSession.grantEmbeddedWindowFocus(viewRootImpl.mWindow, this.mSurfacePackage.getInputTransferToken(), z);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, java.lang.System.identityHashCode(this) + "Exception requesting focus on embedded window", e);
        }
    }

    private void applyTransactionOnVriDraw(android.view.SurfaceControl.Transaction transaction) {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.applyTransactionOnDraw(transaction);
        } else {
            transaction.apply();
        }
    }

    public void syncNextFrame(java.util.function.Consumer<android.view.SurfaceControl.Transaction> consumer) {
        this.mBlastBufferQueue.syncNextTransaction(consumer);
    }

    public void applyTransactionToFrame(android.view.SurfaceControl.Transaction transaction) {
        synchronized (this.mSurfaceControlLock) {
            if (this.mBlastBufferQueue == null) {
                throw new java.lang.IllegalStateException("Surface does not exist!");
            }
            this.mBlastBufferQueue.mergeWithNextTransaction(transaction, this.mBlastBufferQueue.getLastAcquiredFrameNum() + 1);
        }
    }

    @Override // android.view.View
    void performCollectViewAttributes(android.view.View.AttachInfo attachInfo, int i) {
        super.performCollectViewAttributes(attachInfo, i);
        if (this.mEmbeddedWindowParams.isEmpty()) {
            return;
        }
        java.util.Iterator<android.view.WindowManager.LayoutParams> it = this.mEmbeddedWindowParams.iterator();
        while (it.hasNext()) {
            if ((it.next().flags & 128) == 128) {
                attachInfo.mKeepScreenOn = true;
                return;
            }
        }
    }
}
