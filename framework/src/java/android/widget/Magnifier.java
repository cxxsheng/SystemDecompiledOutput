package android.widget;

/* loaded from: classes4.dex */
public final class Magnifier {
    private static final float FISHEYE_RAMP_WIDTH = 12.0f;
    private static final int NONEXISTENT_PREVIOUS_CONFIG_VALUE = -1;
    public static final int SOURCE_BOUND_MAX_IN_SURFACE = 0;
    public static final int SOURCE_BOUND_MAX_VISIBLE = 1;
    private static final java.lang.String TAG = "Magnifier";
    private static final android.os.HandlerThread sPixelCopyHandlerThread = new android.os.HandlerThread("magnifier pixel copy result handler");
    private int mBottomContentBound;
    private android.widget.Magnifier.Callback mCallback;
    private final android.graphics.Point mClampedCenterZoomCoords;
    private final boolean mClippingEnabled;
    private android.widget.Magnifier.SurfaceInfo mContentCopySurface;
    private android.graphics.drawable.Drawable mCursorDrawable;
    private final int mDefaultHorizontalSourceToMagnifierOffset;
    private final int mDefaultVerticalSourceToMagnifierOffset;
    private boolean mDirtyState;
    private boolean mDrawCursorEnabled;
    private boolean mIsFishEyeStyle;
    private int mLeftContentBound;
    private int mLeftCutWidth;
    private final java.lang.Object mLock;
    private final android.graphics.drawable.Drawable mOverlay;
    private android.widget.Magnifier.SurfaceInfo mParentSurface;
    private final android.graphics.Rect mPixelCopyRequestRect;
    private final android.graphics.PointF mPrevShowSourceCoords;
    private final android.graphics.PointF mPrevShowWindowCoords;
    private final android.graphics.Point mPrevStartCoordsInSurface;
    private final int mRamp;
    private int mRightContentBound;
    private int mRightCutWidth;
    private int mSourceHeight;
    private int mSourceWidth;
    private int mTopContentBound;
    private final android.view.View mView;
    private final int[] mViewCoordinatesInSurface;
    private android.widget.Magnifier.InternalPopupWindow mWindow;
    private final android.graphics.Point mWindowCoords;
    private final float mWindowCornerRadius;
    private final float mWindowElevation;
    private int mWindowHeight;
    private final int mWindowWidth;
    private float mZoom;

    public interface Callback {
        void onOperationComplete();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SourceBound {
    }

    static {
        sPixelCopyHandlerThread.start();
    }

    @java.lang.Deprecated
    public Magnifier(android.view.View view) {
        this(createBuilderWithOldMagnifierDefaults(view));
    }

    static android.widget.Magnifier.Builder createBuilderWithOldMagnifierDefaults(android.view.View view) {
        android.widget.Magnifier.Builder builder = new android.widget.Magnifier.Builder(view);
        android.content.Context context = view.getContext();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, com.android.internal.R.styleable.Magnifier, com.android.internal.R.attr.magnifierStyle, 0);
        builder.mWidth = obtainStyledAttributes.getDimensionPixelSize(5, 0);
        builder.mHeight = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        builder.mElevation = obtainStyledAttributes.getDimension(1, 0.0f);
        builder.mCornerRadius = getDeviceDefaultDialogCornerRadius(context);
        builder.mZoom = obtainStyledAttributes.getFloat(6, 0.0f);
        builder.mHorizontalDefaultSourceToMagnifierOffset = obtainStyledAttributes.getDimensionPixelSize(3, 0);
        builder.mVerticalDefaultSourceToMagnifierOffset = obtainStyledAttributes.getDimensionPixelSize(4, 0);
        builder.mOverlay = new android.graphics.drawable.ColorDrawable(obtainStyledAttributes.getColor(0, 0));
        obtainStyledAttributes.recycle();
        builder.mClippingEnabled = true;
        builder.mLeftContentBound = 1;
        builder.mTopContentBound = 0;
        builder.mRightContentBound = 1;
        builder.mBottomContentBound = 0;
        return builder;
    }

    private static float getDeviceDefaultDialogCornerRadius(android.content.Context context) {
        android.content.res.TypedArray obtainStyledAttributes = new android.view.ContextThemeWrapper(context, 16974120).obtainStyledAttributes(new int[]{16844145});
        float dimension = obtainStyledAttributes.getDimension(0, 0.0f);
        obtainStyledAttributes.recycle();
        return dimension;
    }

    private Magnifier(android.widget.Magnifier.Builder builder) {
        this.mWindowCoords = new android.graphics.Point();
        this.mClampedCenterZoomCoords = new android.graphics.Point();
        this.mPrevStartCoordsInSurface = new android.graphics.Point(-1, -1);
        this.mPrevShowSourceCoords = new android.graphics.PointF(-1.0f, -1.0f);
        this.mPrevShowWindowCoords = new android.graphics.PointF(-1.0f, -1.0f);
        this.mPixelCopyRequestRect = new android.graphics.Rect();
        this.mLock = new java.lang.Object();
        this.mLeftCutWidth = 0;
        this.mRightCutWidth = 0;
        this.mView = builder.mView;
        this.mWindowWidth = builder.mWidth;
        this.mWindowHeight = builder.mHeight;
        this.mZoom = builder.mZoom;
        this.mIsFishEyeStyle = builder.mIsFishEyeStyle;
        if (builder.mSourceWidth > 0 && builder.mSourceHeight > 0) {
            this.mSourceWidth = builder.mSourceWidth;
            this.mSourceHeight = builder.mSourceHeight;
        } else {
            this.mSourceWidth = java.lang.Math.round(this.mWindowWidth / this.mZoom);
            this.mSourceHeight = java.lang.Math.round(this.mWindowHeight / this.mZoom);
        }
        this.mWindowElevation = builder.mElevation;
        this.mWindowCornerRadius = builder.mCornerRadius;
        this.mOverlay = builder.mOverlay;
        this.mDefaultHorizontalSourceToMagnifierOffset = builder.mHorizontalDefaultSourceToMagnifierOffset;
        this.mDefaultVerticalSourceToMagnifierOffset = builder.mVerticalDefaultSourceToMagnifierOffset;
        this.mClippingEnabled = builder.mClippingEnabled;
        this.mLeftContentBound = builder.mLeftContentBound;
        this.mTopContentBound = builder.mTopContentBound;
        this.mRightContentBound = builder.mRightContentBound;
        this.mBottomContentBound = builder.mBottomContentBound;
        this.mViewCoordinatesInSurface = new int[2];
        this.mRamp = (int) android.util.TypedValue.applyDimension(1, FISHEYE_RAMP_WIDTH, this.mView.getContext().getResources().getDisplayMetrics());
    }

    public void show(float f, float f2) {
        show(f, f2, this.mDefaultHorizontalSourceToMagnifierOffset + f, this.mDefaultVerticalSourceToMagnifierOffset + f2);
    }

    void setDrawCursor(boolean z, android.graphics.drawable.Drawable drawable) {
        this.mDrawCursorEnabled = z;
        this.mCursorDrawable = drawable;
    }

    public void show(float f, float f2, float f3, float f4) {
        float f5;
        float f6;
        int i;
        float f7;
        float f8;
        obtainSurfaces();
        obtainContentCoordinates(f, f2);
        int i2 = this.mClampedCenterZoomCoords.x - (this.mSourceWidth / 2);
        int i3 = this.mClampedCenterZoomCoords.y - (this.mSourceHeight / 2);
        if (this.mIsFishEyeStyle) {
            f5 = this.mClampedCenterZoomCoords.x - this.mViewCoordinatesInSurface[0];
            f6 = this.mClampedCenterZoomCoords.y - this.mViewCoordinatesInSurface[1];
            float f9 = (this.mSourceWidth - ((this.mSourceWidth - (this.mRamp * 2)) / this.mZoom)) / 2.0f;
            float f10 = f - (this.mSourceWidth / 2.0f);
            float f11 = this.mRamp + f10;
            float f12 = 0.0f;
            if (0.0f > f11) {
                f12 = f - ((f - 0.0f) / this.mZoom);
            } else if (0.0f > f10) {
                f12 = (f10 + f9) - (((f11 - 0.0f) * f9) / this.mRamp);
            }
            int min = java.lang.Math.min((int) f12, this.mView.getWidth());
            float f13 = f + (this.mSourceWidth / 2.0f);
            float f14 = f13 - this.mRamp;
            float width = this.mView.getWidth();
            if (width < f14) {
                width = ((width - f) / this.mZoom) + f;
            } else if (width < f13) {
                width = (((width - f14) * f9) / this.mRamp) + (f13 - f9);
            }
            int max = java.lang.Math.max(min, (int) width);
            int max2 = java.lang.Math.max(min + this.mViewCoordinatesInSurface[0], 0);
            int min2 = java.lang.Math.min(max + this.mViewCoordinatesInSurface[0], this.mContentCopySurface.mWidth);
            this.mLeftCutWidth = java.lang.Math.max(0, max2 - i2);
            this.mRightCutWidth = java.lang.Math.max(0, (this.mSourceWidth + i2) - min2);
            i2 = java.lang.Math.max(i2, max2);
        } else {
            f5 = f3;
            f6 = f4;
        }
        obtainWindowCoordinates(f5, f6);
        if (f != this.mPrevShowSourceCoords.x || f2 != this.mPrevShowSourceCoords.y || this.mDirtyState) {
            if (this.mWindow != null) {
                i = i2;
                f7 = f5;
                f8 = f6;
            } else {
                synchronized (this.mLock) {
                    f8 = f6;
                    f7 = f5;
                    i = i2;
                    this.mWindow = new android.widget.Magnifier.InternalPopupWindow(this.mView.getContext(), this.mView.getDisplay(), this.mParentSurface.mSurfaceControl, this.mWindowWidth, this.mWindowHeight, this.mZoom, this.mRamp, this.mWindowElevation, this.mWindowCornerRadius, this.mOverlay != null ? this.mOverlay : new android.graphics.drawable.ColorDrawable(0), android.os.Handler.getMain(), this.mLock, this.mCallback, this.mIsFishEyeStyle);
                }
            }
            performPixelCopy(i, i3, true);
        } else if (f5 == this.mPrevShowWindowCoords.x && f6 == this.mPrevShowWindowCoords.y) {
            f7 = f5;
            f8 = f6;
        } else {
            final android.graphics.Point currentClampedWindowCoordinates = getCurrentClampedWindowCoordinates();
            final android.widget.Magnifier.InternalPopupWindow internalPopupWindow = this.mWindow;
            sPixelCopyHandlerThread.getThreadHandler().post(new java.lang.Runnable() { // from class: android.widget.Magnifier$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.widget.Magnifier.this.lambda$show$0(internalPopupWindow, currentClampedWindowCoordinates);
                }
            });
            f7 = f5;
            f8 = f6;
        }
        this.mPrevShowSourceCoords.x = f;
        this.mPrevShowSourceCoords.y = f2;
        this.mPrevShowWindowCoords.x = f7;
        this.mPrevShowWindowCoords.y = f8;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$0(android.widget.Magnifier.InternalPopupWindow internalPopupWindow, android.graphics.Point point) {
        synchronized (this.mLock) {
            if (this.mWindow != internalPopupWindow) {
                return;
            }
            this.mWindow.setContentPositionForNextDraw(point.x, point.y);
        }
    }

    public void dismiss() {
        if (this.mWindow != null) {
            synchronized (this.mLock) {
                this.mWindow.destroy();
                this.mWindow = null;
            }
            this.mPrevShowSourceCoords.x = -1.0f;
            this.mPrevShowSourceCoords.y = -1.0f;
            this.mPrevShowWindowCoords.x = -1.0f;
            this.mPrevShowWindowCoords.y = -1.0f;
            this.mPrevStartCoordsInSurface.x = -1;
            this.mPrevStartCoordsInSurface.y = -1;
        }
    }

    public void update() {
        if (this.mWindow != null) {
            obtainSurfaces();
            if (!this.mDirtyState) {
                performPixelCopy(this.mPrevStartCoordsInSurface.x, this.mPrevStartCoordsInSurface.y, false);
            } else {
                show(this.mPrevShowSourceCoords.x, this.mPrevShowSourceCoords.y, this.mPrevShowWindowCoords.x, this.mPrevShowWindowCoords.y);
            }
        }
    }

    public int getWidth() {
        return this.mWindowWidth;
    }

    public int getHeight() {
        return this.mWindowHeight;
    }

    public int getSourceWidth() {
        return this.mSourceWidth;
    }

    public int getSourceHeight() {
        return this.mSourceHeight;
    }

    public void setZoom(float f) {
        com.android.internal.util.Preconditions.checkArgumentPositive(f, "Zoom should be positive");
        this.mZoom = f;
        this.mSourceWidth = this.mIsFishEyeStyle ? this.mWindowWidth : java.lang.Math.round(this.mWindowWidth / this.mZoom);
        this.mSourceHeight = java.lang.Math.round(this.mWindowHeight / this.mZoom);
        this.mDirtyState = true;
    }

    void updateSourceFactors(int i, float f) {
        this.mZoom = f;
        this.mSourceHeight = i;
        this.mWindowHeight = (int) (i * f);
        if (this.mWindow != null) {
            this.mWindow.updateContentFactors(this.mWindowHeight, f);
        }
    }

    public float getZoom() {
        return this.mZoom;
    }

    public float getElevation() {
        return this.mWindowElevation;
    }

    public float getCornerRadius() {
        return this.mWindowCornerRadius;
    }

    public int getDefaultHorizontalSourceToMagnifierOffset() {
        return this.mDefaultHorizontalSourceToMagnifierOffset;
    }

    public int getDefaultVerticalSourceToMagnifierOffset() {
        return this.mDefaultVerticalSourceToMagnifierOffset;
    }

    public android.graphics.drawable.Drawable getOverlay() {
        return this.mOverlay;
    }

    public boolean isClippingEnabled() {
        return this.mClippingEnabled;
    }

    public android.graphics.Point getPosition() {
        if (this.mWindow == null) {
            return null;
        }
        android.graphics.Point currentClampedWindowCoordinates = getCurrentClampedWindowCoordinates();
        currentClampedWindowCoordinates.offset(-this.mParentSurface.mInsets.left, -this.mParentSurface.mInsets.top);
        return new android.graphics.Point(currentClampedWindowCoordinates);
    }

    public android.graphics.Point getSourcePosition() {
        if (this.mWindow == null) {
            return null;
        }
        android.graphics.Point point = new android.graphics.Point(this.mPixelCopyRequestRect.left, this.mPixelCopyRequestRect.top);
        point.offset(-this.mContentCopySurface.mInsets.left, -this.mContentCopySurface.mInsets.top);
        return new android.graphics.Point(point);
    }

    private void obtainSurfaces() {
        android.view.ViewRootImpl viewRootImpl;
        android.view.Surface surface;
        android.widget.Magnifier.SurfaceInfo surfaceInfo = android.widget.Magnifier.SurfaceInfo.NULL;
        if (this.mView.getViewRootImpl() != null && (surface = (viewRootImpl = this.mView.getViewRootImpl()).mSurface) != null && surface.isValid()) {
            android.graphics.Rect rect = viewRootImpl.mWindowAttributes.surfaceInsets;
            surfaceInfo = new android.widget.Magnifier.SurfaceInfo(viewRootImpl.getSurfaceControl(), surface, viewRootImpl.getWidth() + rect.left + rect.right, viewRootImpl.getHeight() + rect.top + rect.bottom, rect, true);
        }
        android.widget.Magnifier.SurfaceInfo surfaceInfo2 = android.widget.Magnifier.SurfaceInfo.NULL;
        if (this.mView instanceof android.view.SurfaceView) {
            android.view.SurfaceControl surfaceControl = ((android.view.SurfaceView) this.mView).getSurfaceControl();
            android.view.SurfaceHolder holder = ((android.view.SurfaceView) this.mView).getHolder();
            android.view.Surface surface2 = holder.getSurface();
            if (surfaceControl != null && surfaceControl.isValid()) {
                android.graphics.Rect surfaceFrame = holder.getSurfaceFrame();
                surfaceInfo2 = new android.widget.Magnifier.SurfaceInfo(surfaceControl, surface2, surfaceFrame.right, surfaceFrame.bottom, new android.graphics.Rect(), false);
            }
        }
        this.mParentSurface = surfaceInfo != android.widget.Magnifier.SurfaceInfo.NULL ? surfaceInfo : surfaceInfo2;
        if (this.mView instanceof android.view.SurfaceView) {
            surfaceInfo = surfaceInfo2;
        }
        this.mContentCopySurface = surfaceInfo;
    }

    private void obtainContentCoordinates(float f, float f2) {
        int round;
        int round2;
        int max;
        int i = this.mViewCoordinatesInSurface[0];
        int i2 = this.mViewCoordinatesInSurface[1];
        this.mView.getLocationInSurface(this.mViewCoordinatesInSurface);
        if (this.mViewCoordinatesInSurface[0] != i || this.mViewCoordinatesInSurface[1] != i2) {
            this.mDirtyState = true;
        }
        if (this.mView instanceof android.view.SurfaceView) {
            round = java.lang.Math.round(f);
            round2 = java.lang.Math.round(f2);
        } else {
            round = java.lang.Math.round(f + this.mViewCoordinatesInSurface[0]);
            round2 = java.lang.Math.round(f2 + this.mViewCoordinatesInSurface[1]);
        }
        android.graphics.Rect[] rectArr = new android.graphics.Rect[2];
        rectArr[0] = new android.graphics.Rect(0, 0, this.mContentCopySurface.mWidth, this.mContentCopySurface.mHeight);
        android.graphics.Rect rect = new android.graphics.Rect();
        this.mView.getGlobalVisibleRect(rect);
        if (this.mView.getViewRootImpl() != null) {
            android.graphics.Rect rect2 = this.mView.getViewRootImpl().mWindowAttributes.surfaceInsets;
            rect.offset(rect2.left, rect2.top);
        }
        if (this.mView instanceof android.view.SurfaceView) {
            rect.offset(-this.mViewCoordinatesInSurface[0], -this.mViewCoordinatesInSurface[1]);
        }
        rectArr[1] = rect;
        int i3 = Integer.MIN_VALUE;
        int i4 = Integer.MIN_VALUE;
        for (int i5 = this.mLeftContentBound; i5 >= 0; i5--) {
            i4 = java.lang.Math.max(i4, rectArr[i5].left);
        }
        for (int i6 = this.mTopContentBound; i6 >= 0; i6--) {
            i3 = java.lang.Math.max(i3, rectArr[i6].top);
        }
        int i7 = Integer.MAX_VALUE;
        int i8 = Integer.MAX_VALUE;
        for (int i9 = this.mRightContentBound; i9 >= 0; i9--) {
            i8 = java.lang.Math.min(i8, rectArr[i9].right);
        }
        for (int i10 = this.mBottomContentBound; i10 >= 0; i10--) {
            i7 = java.lang.Math.min(i7, rectArr[i10].bottom);
        }
        int min = java.lang.Math.min(i4, this.mContentCopySurface.mWidth - this.mSourceWidth);
        int min2 = java.lang.Math.min(i3, this.mContentCopySurface.mHeight - this.mSourceHeight);
        if (min < 0 || min2 < 0) {
            android.util.Log.e(TAG, "Magnifier's content is copied from a surface smaller thanthe content requested size. The magnifier will be dismissed.");
        }
        int max2 = java.lang.Math.max(i8, this.mSourceWidth + min);
        int max3 = java.lang.Math.max(i7, this.mSourceHeight + min2);
        android.graphics.Point point = this.mClampedCenterZoomCoords;
        if (!this.mIsFishEyeStyle) {
            max = java.lang.Math.max(min + (this.mSourceWidth / 2), java.lang.Math.min(round, max2 - (this.mSourceWidth / 2)));
        } else {
            max = java.lang.Math.max(min, java.lang.Math.min(round, max2));
        }
        point.x = max;
        this.mClampedCenterZoomCoords.y = java.lang.Math.max(min2 + (this.mSourceHeight / 2), java.lang.Math.min(round2, max3 - (this.mSourceHeight / 2)));
    }

    private void obtainWindowCoordinates(float f, float f2) {
        int round;
        int round2;
        if (this.mView instanceof android.view.SurfaceView) {
            round = java.lang.Math.round(f);
            round2 = java.lang.Math.round(f2);
        } else {
            round = java.lang.Math.round(f + this.mViewCoordinatesInSurface[0]);
            round2 = java.lang.Math.round(f2 + this.mViewCoordinatesInSurface[1]);
        }
        this.mWindowCoords.x = round - (this.mWindowWidth / 2);
        this.mWindowCoords.y = round2 - (this.mWindowHeight / 2);
        if (this.mParentSurface != this.mContentCopySurface) {
            this.mWindowCoords.x += this.mViewCoordinatesInSurface[0];
            this.mWindowCoords.y += this.mViewCoordinatesInSurface[1];
        }
    }

    private void maybeDrawCursor(android.graphics.Canvas canvas) {
        if (this.mDrawCursorEnabled) {
            if (this.mCursorDrawable != null) {
                this.mCursorDrawable.setBounds(this.mSourceWidth / 2, 0, (this.mSourceWidth / 2) + this.mCursorDrawable.getIntrinsicWidth(), this.mSourceHeight);
                this.mCursorDrawable.draw(canvas);
            } else {
                android.graphics.Paint paint = new android.graphics.Paint();
                paint.setColor(-16777216);
                canvas.drawRect(new android.graphics.Rect((this.mSourceWidth / 2) - 1, 0, (this.mSourceWidth / 2) + 1, this.mSourceHeight), paint);
            }
        }
    }

    private void performPixelCopy(int i, int i2, final boolean z) {
        if (this.mContentCopySurface.mSurface == null || !this.mContentCopySurface.mSurface.isValid()) {
            onPixelCopyFailed();
            return;
        }
        final android.graphics.Point currentClampedWindowCoordinates = getCurrentClampedWindowCoordinates();
        this.mPixelCopyRequestRect.set(i, i2, ((this.mSourceWidth + i) - this.mLeftCutWidth) - this.mRightCutWidth, this.mSourceHeight + i2);
        this.mPrevStartCoordsInSurface.x = i;
        this.mPrevStartCoordsInSurface.y = i2;
        this.mDirtyState = false;
        final android.widget.Magnifier.InternalPopupWindow internalPopupWindow = this.mWindow;
        if (this.mPixelCopyRequestRect.width() == 0) {
            this.mWindow.updateContent(android.graphics.Bitmap.createBitmap(this.mSourceWidth, this.mSourceHeight, android.graphics.Bitmap.Config.ALPHA_8));
        } else {
            final android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap((this.mSourceWidth - this.mLeftCutWidth) - this.mRightCutWidth, this.mSourceHeight, android.graphics.Bitmap.Config.ARGB_8888);
            android.view.PixelCopy.request(this.mContentCopySurface.mSurface, this.mPixelCopyRequestRect, createBitmap, new android.view.PixelCopy.OnPixelCopyFinishedListener() { // from class: android.widget.Magnifier$$ExternalSyntheticLambda0
                @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                public final void onPixelCopyFinished(int i3) {
                    android.widget.Magnifier.this.lambda$performPixelCopy$1(internalPopupWindow, z, currentClampedWindowCoordinates, createBitmap, i3);
                }
            }, sPixelCopyHandlerThread.getThreadHandler());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performPixelCopy$1(android.widget.Magnifier.InternalPopupWindow internalPopupWindow, boolean z, android.graphics.Point point, android.graphics.Bitmap bitmap, int i) {
        if (i != 0) {
            onPixelCopyFailed();
            return;
        }
        synchronized (this.mLock) {
            if (this.mWindow != internalPopupWindow) {
                return;
            }
            if (z) {
                this.mWindow.setContentPositionForNextDraw(point.x, point.y);
            }
            if (bitmap.getWidth() < this.mSourceWidth) {
                android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(this.mSourceWidth, bitmap.getHeight(), bitmap.getConfig());
                android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
                canvas.drawBitmap(bitmap, (android.graphics.Rect) null, new android.graphics.Rect(this.mLeftCutWidth, 0, this.mSourceWidth - this.mRightCutWidth, bitmap.getHeight()), (android.graphics.Paint) null);
                maybeDrawCursor(canvas);
                this.mWindow.updateContent(createBitmap);
            } else {
                maybeDrawCursor(new android.graphics.Canvas(bitmap));
                this.mWindow.updateContent(bitmap);
            }
        }
    }

    private void onPixelCopyFailed() {
        android.util.Log.e(TAG, "Magnifier failed to copy content from the view Surface. It will be dismissed.");
        android.os.Handler.getMain().postAtFrontOfQueue(new java.lang.Runnable() { // from class: android.widget.Magnifier$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.Magnifier.this.lambda$onPixelCopyFailed$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPixelCopyFailed$2() {
        dismiss();
        if (this.mCallback != null) {
            this.mCallback.onOperationComplete();
        }
    }

    private android.graphics.Point getCurrentClampedWindowCoordinates() {
        android.graphics.Rect rect;
        if (!this.mClippingEnabled) {
            return new android.graphics.Point(this.mWindowCoords);
        }
        if (this.mParentSurface.mIsMainWindowSurface) {
            android.graphics.Insets systemWindowInsets = this.mView.getRootWindowInsets().getSystemWindowInsets();
            rect = new android.graphics.Rect(systemWindowInsets.left + this.mParentSurface.mInsets.left, systemWindowInsets.top + this.mParentSurface.mInsets.top, (this.mParentSurface.mWidth - systemWindowInsets.right) - this.mParentSurface.mInsets.right, (this.mParentSurface.mHeight - systemWindowInsets.bottom) - this.mParentSurface.mInsets.bottom);
        } else {
            rect = new android.graphics.Rect(0, 0, this.mParentSurface.mWidth, this.mParentSurface.mHeight);
        }
        return new android.graphics.Point(java.lang.Math.max(rect.left, java.lang.Math.min(rect.right - this.mWindowWidth, this.mWindowCoords.x)), java.lang.Math.max(rect.top, java.lang.Math.min(rect.bottom - this.mWindowHeight, this.mWindowCoords.y)));
    }

    private static class SurfaceInfo {
        public static final android.widget.Magnifier.SurfaceInfo NULL = new android.widget.Magnifier.SurfaceInfo(null, null, 0, 0, null, false);
        private int mHeight;
        private android.graphics.Rect mInsets;
        private boolean mIsMainWindowSurface;
        private android.view.Surface mSurface;
        private android.view.SurfaceControl mSurfaceControl;
        private int mWidth;

        SurfaceInfo(android.view.SurfaceControl surfaceControl, android.view.Surface surface, int i, int i2, android.graphics.Rect rect, boolean z) {
            this.mSurfaceControl = surfaceControl;
            this.mSurface = surface;
            this.mWidth = i;
            this.mHeight = i2;
            this.mInsets = rect;
            this.mIsMainWindowSurface = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class InternalPopupWindow {
        private static final int SURFACE_Z = 5;
        private final android.graphics.BLASTBufferQueue mBBQ;
        private final android.view.SurfaceControl mBbqSurfaceControl;
        private android.graphics.Bitmap mBitmap;
        private final android.graphics.RenderNode mBitmapRenderNode;
        private android.widget.Magnifier.Callback mCallback;
        private int mContentHeight;
        private final int mContentWidth;
        private android.graphics.Bitmap mCurrentContent;
        private final android.view.Display mDisplay;
        private boolean mFrameDrawScheduled;
        private final android.os.Handler mHandler;
        private boolean mIsFishEyeStyle;
        private final java.lang.Object mLock;
        private final java.lang.Runnable mMagnifierUpdater;
        private int mMeshHeight;
        private float[] mMeshLeft;
        private float[] mMeshRight;
        private int mMeshWidth;
        private final int mOffsetX;
        private final int mOffsetY;
        private final android.graphics.drawable.Drawable mOverlay;
        private final android.graphics.RenderNode mOverlayRenderNode;
        private boolean mPendingWindowPositionUpdate;
        private final int mRamp;
        private final android.view.ThreadedRenderer.SimpleRenderer mRenderer;
        private final android.view.Surface mSurface;
        private final android.view.SurfaceControl mSurfaceControl;
        private final android.view.SurfaceSession mSurfaceSession;
        private int mWindowPositionX;
        private int mWindowPositionY;
        private float mZoom;
        private final android.view.SurfaceControl.Transaction mTransaction = new android.view.SurfaceControl.Transaction();
        private boolean mFirstDraw = true;

        InternalPopupWindow(android.content.Context context, android.view.Display display, android.view.SurfaceControl surfaceControl, int i, int i2, float f, int i3, float f2, float f3, android.graphics.drawable.Drawable drawable, android.os.Handler handler, java.lang.Object obj, android.widget.Magnifier.Callback callback, boolean z) {
            this.mDisplay = display;
            this.mOverlay = drawable;
            this.mLock = obj;
            this.mCallback = callback;
            this.mContentWidth = i;
            this.mContentHeight = i2;
            this.mZoom = f;
            this.mRamp = i3;
            int i4 = (int) (1.05f * f2);
            this.mOffsetX = i4;
            this.mOffsetY = i4;
            int i5 = this.mContentWidth + (this.mOffsetX * 2);
            int i6 = this.mContentHeight + (this.mOffsetY * 2);
            this.mSurfaceSession = new android.view.SurfaceSession();
            this.mSurfaceControl = new android.view.SurfaceControl.Builder(this.mSurfaceSession).setName("magnifier surface").setFlags(4).setContainerLayer().setParent(surfaceControl).setCallsite("InternalPopupWindow").build();
            this.mBbqSurfaceControl = new android.view.SurfaceControl.Builder(this.mSurfaceSession).setName("magnifier surface bbq wrapper").setHidden(false).setBLASTLayer().setParent(this.mSurfaceControl).setCallsite("InternalPopupWindow").build();
            this.mBBQ = new android.graphics.BLASTBufferQueue("magnifier surface", this.mBbqSurfaceControl, i5, i6, -3);
            this.mSurface = this.mBBQ.createSurface();
            this.mRenderer = new android.view.ThreadedRenderer.SimpleRenderer(context, "magnifier renderer", this.mSurface);
            this.mBitmapRenderNode = createRenderNodeForBitmap("magnifier content", f2, f3);
            this.mOverlayRenderNode = createRenderNodeForOverlay("magnifier overlay", f3);
            setupOverlay();
            android.graphics.RecordingCanvas beginRecording = this.mRenderer.getRootNode().beginRecording(i, i2);
            try {
                beginRecording.enableZ();
                beginRecording.drawRenderNode(this.mBitmapRenderNode);
                beginRecording.disableZ();
                beginRecording.drawRenderNode(this.mOverlayRenderNode);
                beginRecording.disableZ();
                this.mRenderer.getRootNode().endRecording();
                if (this.mCallback != null) {
                    this.mCurrentContent = android.graphics.Bitmap.createBitmap(this.mContentWidth, this.mContentHeight, android.graphics.Bitmap.Config.ARGB_8888);
                    updateCurrentContentForTesting();
                }
                this.mHandler = handler;
                this.mMagnifierUpdater = new java.lang.Runnable() { // from class: android.widget.Magnifier$InternalPopupWindow$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.widget.Magnifier.InternalPopupWindow.this.doDraw();
                    }
                };
                this.mFrameDrawScheduled = false;
                this.mIsFishEyeStyle = z;
                if (this.mIsFishEyeStyle) {
                    createMeshMatrixForFishEyeEffect();
                }
            } catch (java.lang.Throwable th) {
                this.mRenderer.getRootNode().endRecording();
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateContentFactors(int i, float f) {
            if (this.mContentHeight == i && this.mZoom == f) {
                return;
            }
            if (this.mContentHeight < i) {
                this.mBBQ.update(this.mBbqSurfaceControl, this.mContentWidth, i, -3);
                this.mRenderer.setSurface(this.mSurface);
                android.graphics.Outline outline = new android.graphics.Outline();
                outline.setRoundRect(0, 0, this.mContentWidth, i, 0.0f);
                outline.setAlpha(1.0f);
                this.mBitmapRenderNode.setLeftTopRightBottom(this.mOffsetX, this.mOffsetY, this.mOffsetX + this.mContentWidth, this.mOffsetY + i);
                this.mBitmapRenderNode.setOutline(outline);
                this.mOverlayRenderNode.setLeftTopRightBottom(this.mOffsetX, this.mOffsetY, this.mOffsetX + this.mContentWidth, this.mOffsetY + i);
                this.mOverlayRenderNode.setOutline(outline);
                android.graphics.RecordingCanvas beginRecording = this.mRenderer.getRootNode().beginRecording(this.mContentWidth, i);
                try {
                    beginRecording.enableZ();
                    beginRecording.drawRenderNode(this.mBitmapRenderNode);
                    beginRecording.disableZ();
                    beginRecording.drawRenderNode(this.mOverlayRenderNode);
                    beginRecording.disableZ();
                } finally {
                    this.mRenderer.getRootNode().endRecording();
                }
            }
            this.mContentHeight = i;
            this.mZoom = f;
            fillMeshMatrix();
        }

        private void createMeshMatrixForFishEyeEffect() {
            this.mMeshWidth = 1;
            this.mMeshHeight = 6;
            this.mMeshLeft = new float[(this.mMeshWidth + 1) * 2 * (this.mMeshHeight + 1)];
            this.mMeshRight = new float[(this.mMeshWidth + 1) * 2 * (this.mMeshHeight + 1)];
            fillMeshMatrix();
        }

        private void fillMeshMatrix() {
            this.mMeshWidth = 1;
            this.mMeshHeight = 6;
            float f = this.mContentWidth;
            float f2 = this.mContentHeight;
            float f3 = f2 / this.mZoom;
            float f4 = f2 - f3;
            for (int i = 0; i < (this.mMeshWidth + 1) * 2 * (this.mMeshHeight + 1); i += 2) {
                float f5 = (i % ((this.mMeshWidth + 1) * 2)) / 2;
                this.mMeshLeft[i] = (this.mRamp * f5) / this.mMeshWidth;
                this.mMeshRight[i] = (f - this.mRamp) + ((r6 * this.mRamp) / this.mMeshWidth);
                float f6 = f5 * f4;
                float f7 = (f6 / this.mMeshWidth) + f3;
                int i2 = i + 1;
                float f8 = (i / 2) / (this.mMeshWidth + 1);
                this.mMeshLeft[i2] = ((f2 - f7) / 2.0f) + ((f7 * f8) / this.mMeshHeight);
                float f9 = f2 - (f6 / this.mMeshWidth);
                this.mMeshRight[i2] = ((f2 - f9) / 2.0f) + ((f9 * f8) / this.mMeshHeight);
            }
        }

        private android.graphics.RenderNode createRenderNodeForBitmap(java.lang.String str, float f, float f2) {
            android.graphics.RenderNode create = android.graphics.RenderNode.create(str, null);
            create.setLeftTopRightBottom(this.mOffsetX, this.mOffsetY, this.mOffsetX + this.mContentWidth, this.mOffsetY + this.mContentHeight);
            create.setElevation(f);
            android.graphics.Outline outline = new android.graphics.Outline();
            outline.setRoundRect(0, 0, this.mContentWidth, this.mContentHeight, f2);
            outline.setAlpha(1.0f);
            create.setOutline(outline);
            create.setClipToOutline(true);
            try {
                create.beginRecording(this.mContentWidth, this.mContentHeight).drawColor(android.graphics.Color.GREEN);
                return create;
            } finally {
                create.endRecording();
            }
        }

        private android.graphics.RenderNode createRenderNodeForOverlay(java.lang.String str, float f) {
            android.graphics.RenderNode create = android.graphics.RenderNode.create(str, null);
            create.setLeftTopRightBottom(this.mOffsetX, this.mOffsetY, this.mOffsetX + this.mContentWidth, this.mOffsetY + this.mContentHeight);
            android.graphics.Outline outline = new android.graphics.Outline();
            outline.setRoundRect(0, 0, this.mContentWidth, this.mContentHeight, f);
            outline.setAlpha(1.0f);
            create.setOutline(outline);
            create.setClipToOutline(true);
            return create;
        }

        private void setupOverlay() {
            drawOverlay();
            this.mOverlay.setCallback(new android.graphics.drawable.Drawable.Callback() { // from class: android.widget.Magnifier.InternalPopupWindow.1
                @Override // android.graphics.drawable.Drawable.Callback
                public void invalidateDrawable(android.graphics.drawable.Drawable drawable) {
                    android.widget.Magnifier.InternalPopupWindow.this.drawOverlay();
                    if (android.widget.Magnifier.InternalPopupWindow.this.mCallback != null) {
                        android.widget.Magnifier.InternalPopupWindow.this.updateCurrentContentForTesting();
                    }
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public void scheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable, long j) {
                    android.os.Handler.getMain().postAtTime(runnable, drawable, j);
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public void unscheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable) {
                    android.os.Handler.getMain().removeCallbacks(runnable, drawable);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void drawOverlay() {
            android.graphics.RecordingCanvas beginRecording = this.mOverlayRenderNode.beginRecording(this.mContentWidth, this.mContentHeight);
            try {
                this.mOverlay.setBounds(0, 0, this.mContentWidth, this.mContentHeight);
                this.mOverlay.draw(beginRecording);
            } finally {
                this.mOverlayRenderNode.endRecording();
            }
        }

        public void setContentPositionForNextDraw(int i, int i2) {
            this.mWindowPositionX = i - this.mOffsetX;
            this.mWindowPositionY = i2 - this.mOffsetY;
            this.mPendingWindowPositionUpdate = true;
            requestUpdate();
        }

        public void updateContent(android.graphics.Bitmap bitmap) {
            if (this.mBitmap != null) {
                this.mBitmap.recycle();
            }
            this.mBitmap = bitmap;
            requestUpdate();
        }

        private void requestUpdate() {
            if (this.mFrameDrawScheduled) {
                return;
            }
            android.os.Message obtain = android.os.Message.obtain(this.mHandler, this.mMagnifierUpdater);
            obtain.setAsynchronous(true);
            obtain.sendToTarget();
            this.mFrameDrawScheduled = true;
        }

        public void destroy() {
            this.mRenderer.destroy();
            this.mSurface.destroy();
            this.mBBQ.destroy();
            new android.view.SurfaceControl.Transaction().remove(this.mSurfaceControl).remove(this.mBbqSurfaceControl).apply();
            this.mSurfaceSession.kill();
            this.mHandler.removeCallbacks(this.mMagnifierUpdater);
            if (this.mBitmap != null) {
                this.mBitmap.recycle();
            }
            this.mOverlay.setCallback(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00e1  */
        /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void doDraw() {
            boolean z;
            android.graphics.HardwareRenderer.FrameDrawingCallback frameDrawingCallback;
            synchronized (this.mLock) {
                if (!this.mSurface.isValid()) {
                    return;
                }
                android.graphics.RecordingCanvas beginRecording = this.mBitmapRenderNode.beginRecording(this.mContentWidth, this.mContentHeight);
                try {
                    int width = this.mBitmap.getWidth();
                    int height = this.mBitmap.getHeight();
                    android.graphics.Paint paint = new android.graphics.Paint();
                    paint.setFilterBitmap(true);
                    if (this.mIsFishEyeStyle) {
                        int i = (int) ((this.mContentWidth - ((this.mContentWidth - (this.mRamp * 2)) / this.mZoom)) / 2.0f);
                        int i2 = width - i;
                        beginRecording.drawBitmap(this.mBitmap, new android.graphics.Rect(i, 0, i2, height), new android.graphics.Rect(this.mRamp, 0, this.mContentWidth - this.mRamp, this.mContentHeight), paint);
                        beginRecording.drawBitmapMesh(android.graphics.Bitmap.createBitmap(this.mBitmap, 0, 0, i, height), this.mMeshWidth, this.mMeshHeight, this.mMeshLeft, 0, null, 0, paint);
                        beginRecording.drawBitmapMesh(android.graphics.Bitmap.createBitmap(this.mBitmap, i2, 0, i, height), this.mMeshWidth, this.mMeshHeight, this.mMeshRight, 0, null, 0, paint);
                    } else {
                        beginRecording.drawBitmap(this.mBitmap, new android.graphics.Rect(0, 0, width, height), new android.graphics.Rect(0, 0, this.mContentWidth, this.mContentHeight), paint);
                    }
                    this.mBitmapRenderNode.endRecording();
                    if (!this.mPendingWindowPositionUpdate && !this.mFirstDraw) {
                        frameDrawingCallback = null;
                        z = false;
                        this.mFrameDrawScheduled = z;
                        this.mRenderer.draw(frameDrawingCallback);
                        if (this.mCallback == null) {
                            updateCurrentContentForTesting();
                            this.mCallback.onOperationComplete();
                            return;
                        }
                        return;
                    }
                    final boolean z2 = this.mFirstDraw;
                    this.mFirstDraw = false;
                    final boolean z3 = this.mPendingWindowPositionUpdate;
                    this.mPendingWindowPositionUpdate = false;
                    final int i3 = this.mWindowPositionX;
                    final int i4 = this.mWindowPositionY;
                    z = false;
                    android.graphics.HardwareRenderer.FrameDrawingCallback frameDrawingCallback2 = new android.graphics.HardwareRenderer.FrameDrawingCallback() { // from class: android.widget.Magnifier$InternalPopupWindow$$ExternalSyntheticLambda0
                        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                        public final void onFrameDraw(long j) {
                            android.widget.Magnifier.InternalPopupWindow.this.lambda$doDraw$0(z3, i3, i4, z2, j);
                        }
                    };
                    if (!this.mIsFishEyeStyle) {
                        this.mRenderer.setLightCenter(this.mDisplay, i3, i4);
                    }
                    frameDrawingCallback = frameDrawingCallback2;
                    this.mFrameDrawScheduled = z;
                    this.mRenderer.draw(frameDrawingCallback);
                    if (this.mCallback == null) {
                    }
                } catch (java.lang.Throwable th) {
                    this.mBitmapRenderNode.endRecording();
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$doDraw$0(boolean z, int i, int i2, boolean z2, long j) {
            if (!this.mSurface.isValid()) {
                return;
            }
            if (z) {
                this.mTransaction.setPosition(this.mSurfaceControl, i, i2);
            }
            if (z2) {
                this.mTransaction.setLayer(this.mSurfaceControl, 5).show(this.mSurfaceControl);
            }
            this.mBBQ.mergeWithNextTransaction(this.mTransaction, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateCurrentContentForTesting() {
            android.graphics.Canvas canvas = new android.graphics.Canvas(this.mCurrentContent);
            android.graphics.Rect rect = new android.graphics.Rect(0, 0, this.mContentWidth, this.mContentHeight);
            if (this.mBitmap != null && !this.mBitmap.isRecycled()) {
                canvas.drawBitmap(this.mBitmap, new android.graphics.Rect(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight()), rect, (android.graphics.Paint) null);
            }
            this.mOverlay.setBounds(rect);
            this.mOverlay.draw(canvas);
        }
    }

    public static final class Builder {
        private int mBottomContentBound;
        private boolean mClippingEnabled;
        private float mCornerRadius;
        private float mElevation;
        private int mHeight;
        private int mHorizontalDefaultSourceToMagnifierOffset;
        private boolean mIsFishEyeStyle;
        private int mLeftContentBound;
        private android.graphics.drawable.Drawable mOverlay;
        private int mRightContentBound;
        private int mSourceHeight;
        private int mSourceWidth;
        private int mTopContentBound;
        private int mVerticalDefaultSourceToMagnifierOffset;
        private android.view.View mView;
        private int mWidth;
        private float mZoom;

        public Builder(android.view.View view) {
            this.mView = (android.view.View) java.util.Objects.requireNonNull(view);
            applyDefaults();
        }

        private void applyDefaults() {
            android.content.res.Resources resources = this.mView.getContext().getResources();
            this.mWidth = resources.getDimensionPixelSize(com.android.internal.R.dimen.default_magnifier_width);
            this.mHeight = resources.getDimensionPixelSize(com.android.internal.R.dimen.default_magnifier_height);
            this.mElevation = resources.getDimension(com.android.internal.R.dimen.default_magnifier_elevation);
            this.mCornerRadius = resources.getDimension(com.android.internal.R.dimen.default_magnifier_corner_radius);
            this.mZoom = resources.getFloat(com.android.internal.R.dimen.default_magnifier_zoom);
            this.mHorizontalDefaultSourceToMagnifierOffset = resources.getDimensionPixelSize(com.android.internal.R.dimen.default_magnifier_horizontal_offset);
            this.mVerticalDefaultSourceToMagnifierOffset = resources.getDimensionPixelSize(com.android.internal.R.dimen.default_magnifier_vertical_offset);
            this.mOverlay = new android.graphics.drawable.ColorDrawable(resources.getColor(com.android.internal.R.color.default_magnifier_color_overlay, null));
            this.mClippingEnabled = true;
            this.mLeftContentBound = 1;
            this.mTopContentBound = 1;
            this.mRightContentBound = 1;
            this.mBottomContentBound = 1;
            this.mIsFishEyeStyle = false;
        }

        public android.widget.Magnifier.Builder setSize(int i, int i2) {
            com.android.internal.util.Preconditions.checkArgumentPositive(i, "Width should be positive");
            com.android.internal.util.Preconditions.checkArgumentPositive(i2, "Height should be positive");
            this.mWidth = i;
            this.mHeight = i2;
            return this;
        }

        public android.widget.Magnifier.Builder setInitialZoom(float f) {
            com.android.internal.util.Preconditions.checkArgumentPositive(f, "Zoom should be positive");
            this.mZoom = f;
            return this;
        }

        public android.widget.Magnifier.Builder setElevation(float f) {
            com.android.internal.util.Preconditions.checkArgumentNonNegative(f, "Elevation should be non-negative");
            this.mElevation = f;
            return this;
        }

        public android.widget.Magnifier.Builder setCornerRadius(float f) {
            com.android.internal.util.Preconditions.checkArgumentNonNegative(f, "Corner radius should be non-negative");
            this.mCornerRadius = f;
            return this;
        }

        public android.widget.Magnifier.Builder setOverlay(android.graphics.drawable.Drawable drawable) {
            this.mOverlay = drawable;
            return this;
        }

        public android.widget.Magnifier.Builder setDefaultSourceToMagnifierOffset(int i, int i2) {
            this.mHorizontalDefaultSourceToMagnifierOffset = i;
            this.mVerticalDefaultSourceToMagnifierOffset = i2;
            return this;
        }

        public android.widget.Magnifier.Builder setClippingEnabled(boolean z) {
            this.mClippingEnabled = z;
            return this;
        }

        public android.widget.Magnifier.Builder setSourceBounds(int i, int i2, int i3, int i4) {
            this.mLeftContentBound = i;
            this.mTopContentBound = i2;
            this.mRightContentBound = i3;
            this.mBottomContentBound = i4;
            return this;
        }

        android.widget.Magnifier.Builder setSourceSize(int i, int i2) {
            this.mSourceWidth = i;
            this.mSourceHeight = i2;
            return this;
        }

        android.widget.Magnifier.Builder setFishEyeStyle() {
            this.mIsFishEyeStyle = true;
            return this;
        }

        public android.widget.Magnifier build() {
            return new android.widget.Magnifier(this);
        }
    }

    public void setOnOperationCompleteCallback(android.widget.Magnifier.Callback callback) {
        this.mCallback = callback;
        if (this.mWindow != null) {
            this.mWindow.mCallback = callback;
        }
    }

    public android.graphics.Bitmap getContent() {
        android.graphics.Bitmap bitmap;
        if (this.mWindow == null) {
            return null;
        }
        synchronized (this.mWindow.mLock) {
            bitmap = this.mWindow.mCurrentContent;
        }
        return bitmap;
    }

    public android.graphics.Bitmap getOriginalContent() {
        android.graphics.Bitmap createBitmap;
        if (this.mWindow == null) {
            return null;
        }
        synchronized (this.mWindow.mLock) {
            createBitmap = android.graphics.Bitmap.createBitmap(this.mWindow.mBitmap);
        }
        return createBitmap;
    }

    public static android.graphics.PointF getMagnifierDefaultSize() {
        android.content.res.Resources system = android.content.res.Resources.getSystem();
        float f = system.getDisplayMetrics().density;
        android.graphics.PointF pointF = new android.graphics.PointF();
        pointF.x = system.getDimension(com.android.internal.R.dimen.default_magnifier_width) / f;
        pointF.y = system.getDimension(com.android.internal.R.dimen.default_magnifier_height) / f;
        return pointF;
    }
}
