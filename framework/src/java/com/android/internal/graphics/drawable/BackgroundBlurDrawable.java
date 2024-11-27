package com.android.internal.graphics.drawable;

/* loaded from: classes4.dex */
public final class BackgroundBlurDrawable extends android.graphics.drawable.Drawable {
    private final com.android.internal.graphics.drawable.BackgroundBlurDrawable.Aggregator mAggregator;
    private float mAlpha;
    private int mBlurRadius;
    private float mCornerRadiusBL;
    private float mCornerRadiusBR;
    private float mCornerRadiusTL;
    private float mCornerRadiusTR;
    private final android.graphics.Paint mPaint;
    public final android.graphics.RenderNode.PositionUpdateListener mPositionUpdateListener;
    private final android.graphics.Rect mRect;
    private final android.graphics.Path mRectPath;
    private final android.graphics.RenderNode mRenderNode;
    private final float[] mTmpRadii;
    private boolean mVisible;
    private static final java.lang.String TAG = com.android.internal.graphics.drawable.BackgroundBlurDrawable.class.getSimpleName();
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    /* renamed from: com.android.internal.graphics.drawable.BackgroundBlurDrawable$1, reason: invalid class name */
    class AnonymousClass1 implements android.graphics.RenderNode.PositionUpdateListener {
        AnonymousClass1() {
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionChanged(long j, final int i, final int i2, final int i3, final int i4) {
            com.android.internal.graphics.drawable.BackgroundBlurDrawable.this.mAggregator.onRenderNodePositionChanged(j, new java.lang.Runnable() { // from class: com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.graphics.drawable.BackgroundBlurDrawable.AnonymousClass1.this.lambda$positionChanged$0(i, i2, i3, i4);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$positionChanged$0(int i, int i2, int i3, int i4) {
            com.android.internal.graphics.drawable.BackgroundBlurDrawable.this.mRect.set(i, i2, i3, i4);
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionLost(long j) {
            com.android.internal.graphics.drawable.BackgroundBlurDrawable.this.mAggregator.onRenderNodePositionChanged(j, new java.lang.Runnable() { // from class: com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.graphics.drawable.BackgroundBlurDrawable.AnonymousClass1.this.lambda$positionLost$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$positionLost$1() {
            com.android.internal.graphics.drawable.BackgroundBlurDrawable.this.mRect.setEmpty();
        }
    }

    private BackgroundBlurDrawable(com.android.internal.graphics.drawable.BackgroundBlurDrawable.Aggregator aggregator) {
        this.mPaint = new android.graphics.Paint();
        this.mRectPath = new android.graphics.Path();
        this.mTmpRadii = new float[8];
        this.mVisible = true;
        this.mAlpha = 1.0f;
        this.mRect = new android.graphics.Rect();
        this.mPositionUpdateListener = new com.android.internal.graphics.drawable.BackgroundBlurDrawable.AnonymousClass1();
        this.mAggregator = aggregator;
        this.mPaint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC));
        this.mPaint.setColor(0);
        this.mPaint.setAntiAlias(true);
        this.mRenderNode = new android.graphics.RenderNode("BackgroundBlurDrawable");
        this.mRenderNode.addPositionUpdateListener(this.mPositionUpdateListener);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        if (this.mRectPath.isEmpty() || !isVisible() || getAlpha() == 0) {
            return;
        }
        canvas.drawPath(this.mRectPath, this.mPaint);
        canvas.drawRenderNode(this.mRenderNode);
    }

    public void setColor(int i) {
        this.mPaint.setColor(i);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (visible) {
            this.mVisible = z;
            this.mAggregator.onBlurDrawableUpdated(this);
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        float f = i / 255.0f;
        if (this.mAlpha != f) {
            this.mAlpha = f;
            invalidateSelf();
            this.mAggregator.onBlurDrawableUpdated(this);
        }
    }

    public void setBlurRadius(int i) {
        if (this.mBlurRadius != i) {
            this.mBlurRadius = i;
            invalidateSelf();
            this.mAggregator.onBlurDrawableUpdated(this);
        }
    }

    public void setCornerRadius(float f) {
        setCornerRadius(f, f, f, f);
    }

    public void setCornerRadius(float f, float f2, float f3, float f4) {
        if (this.mCornerRadiusTL != f || this.mCornerRadiusTR != f2 || this.mCornerRadiusBL != f3 || this.mCornerRadiusBR != f4) {
            this.mCornerRadiusTL = f;
            this.mCornerRadiusTR = f2;
            this.mCornerRadiusBL = f3;
            this.mCornerRadiusBR = f4;
            updatePath();
            invalidateSelf();
            this.mAggregator.onBlurDrawableUpdated(this);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        this.mRenderNode.setPosition(i, i2, i3, i4);
        updatePath();
    }

    private void updatePath() {
        float[] fArr = this.mTmpRadii;
        float[] fArr2 = this.mTmpRadii;
        float f = this.mCornerRadiusTL;
        fArr2[1] = f;
        fArr[0] = f;
        float[] fArr3 = this.mTmpRadii;
        float[] fArr4 = this.mTmpRadii;
        float f2 = this.mCornerRadiusTR;
        fArr4[3] = f2;
        fArr3[2] = f2;
        float[] fArr5 = this.mTmpRadii;
        float[] fArr6 = this.mTmpRadii;
        float f3 = this.mCornerRadiusBL;
        fArr6[5] = f3;
        fArr5[4] = f3;
        float[] fArr7 = this.mTmpRadii;
        float[] fArr8 = this.mTmpRadii;
        float f4 = this.mCornerRadiusBR;
        fArr8[7] = f4;
        fArr7[6] = f4;
        this.mRectPath.reset();
        if (getAlpha() == 0 || !isVisible()) {
            return;
        }
        android.graphics.Rect bounds = getBounds();
        this.mRectPath.addRoundRect(bounds.left, bounds.top, bounds.right, bounds.bottom, this.mTmpRadii, android.graphics.Path.Direction.CW);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        throw new java.lang.IllegalArgumentException("not implemented");
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public java.lang.String toString() {
        return "BackgroundBlurDrawable{blurRadius=" + this.mBlurRadius + ", corners={" + this.mCornerRadiusTL + "," + this.mCornerRadiusTR + "," + this.mCornerRadiusBL + "," + this.mCornerRadiusBR + "}, alpha=" + this.mAlpha + ", visible=" + this.mVisible + "}";
    }

    public static final class Aggregator {
        private boolean mHasUiUpdates;
        private android.view.ViewTreeObserver.OnPreDrawListener mOnPreDrawListener;
        private final android.view.ViewRootImpl mViewRoot;
        private final java.lang.Object mRtLock = new java.lang.Object();
        private final android.util.ArraySet<com.android.internal.graphics.drawable.BackgroundBlurDrawable> mDrawables = new android.util.ArraySet<>();
        private final android.util.LongSparseArray<android.util.ArraySet<java.lang.Runnable>> mFrameRtUpdates = new android.util.LongSparseArray<>();
        private long mLastFrameNumber = 0;
        private com.android.internal.graphics.drawable.BackgroundBlurDrawable.BlurRegion[] mLastFrameBlurRegions = null;
        private com.android.internal.graphics.drawable.BackgroundBlurDrawable.BlurRegion[] mTmpBlurRegionsForFrame = new com.android.internal.graphics.drawable.BackgroundBlurDrawable.BlurRegion[0];

        public Aggregator(android.view.ViewRootImpl viewRootImpl) {
            this.mViewRoot = viewRootImpl;
        }

        public com.android.internal.graphics.drawable.BackgroundBlurDrawable createBackgroundBlurDrawable(android.content.Context context) {
            com.android.internal.graphics.drawable.BackgroundBlurDrawable backgroundBlurDrawable = new com.android.internal.graphics.drawable.BackgroundBlurDrawable(this);
            backgroundBlurDrawable.setBlurRadius(context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.default_background_blur_radius));
            return backgroundBlurDrawable;
        }

        void onBlurDrawableUpdated(com.android.internal.graphics.drawable.BackgroundBlurDrawable backgroundBlurDrawable) {
            boolean z = backgroundBlurDrawable.mAlpha != 0.0f && backgroundBlurDrawable.mBlurRadius > 0 && backgroundBlurDrawable.mVisible;
            boolean contains = this.mDrawables.contains(backgroundBlurDrawable);
            if (z) {
                this.mHasUiUpdates = true;
                if (!contains) {
                    this.mDrawables.add(backgroundBlurDrawable);
                    if (com.android.internal.graphics.drawable.BackgroundBlurDrawable.DEBUG) {
                        android.util.Log.d(com.android.internal.graphics.drawable.BackgroundBlurDrawable.TAG, "Add " + backgroundBlurDrawable);
                    }
                } else if (com.android.internal.graphics.drawable.BackgroundBlurDrawable.DEBUG) {
                    android.util.Log.d(com.android.internal.graphics.drawable.BackgroundBlurDrawable.TAG, "Update " + backgroundBlurDrawable);
                }
            } else if (!z && contains) {
                this.mHasUiUpdates = true;
                this.mDrawables.remove(backgroundBlurDrawable);
                if (com.android.internal.graphics.drawable.BackgroundBlurDrawable.DEBUG) {
                    android.util.Log.d(com.android.internal.graphics.drawable.BackgroundBlurDrawable.TAG, "Remove " + backgroundBlurDrawable);
                }
            }
            if (this.mOnPreDrawListener == null && this.mViewRoot.getView() != null && hasRegions()) {
                registerPreDrawListener();
            }
        }

        private void registerPreDrawListener() {
            this.mOnPreDrawListener = new android.view.ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.graphics.drawable.BackgroundBlurDrawable$Aggregator$$ExternalSyntheticLambda0
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public final boolean onPreDraw() {
                    boolean lambda$registerPreDrawListener$1;
                    lambda$registerPreDrawListener$1 = com.android.internal.graphics.drawable.BackgroundBlurDrawable.Aggregator.this.lambda$registerPreDrawListener$1();
                    return lambda$registerPreDrawListener$1;
                }
            };
            this.mViewRoot.getView().getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$registerPreDrawListener$1() {
            final boolean hasUpdates = hasUpdates();
            if (hasUpdates || hasRegions()) {
                final com.android.internal.graphics.drawable.BackgroundBlurDrawable.BlurRegion[] blurRegionsCopyForRT = getBlurRegionsCopyForRT();
                this.mViewRoot.registerRtFrameCallback(new android.graphics.HardwareRenderer.FrameDrawingCallback() { // from class: com.android.internal.graphics.drawable.BackgroundBlurDrawable$Aggregator$$ExternalSyntheticLambda1
                    @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                    public final void onFrameDraw(long j) {
                        com.android.internal.graphics.drawable.BackgroundBlurDrawable.Aggregator.this.lambda$registerPreDrawListener$0(blurRegionsCopyForRT, hasUpdates, j);
                    }
                });
            }
            if (!hasRegions() && this.mViewRoot.getView() != null) {
                this.mViewRoot.getView().getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
                this.mOnPreDrawListener = null;
                return true;
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$registerPreDrawListener$0(com.android.internal.graphics.drawable.BackgroundBlurDrawable.BlurRegion[] blurRegionArr, boolean z, long j) {
            synchronized (this.mRtLock) {
                this.mLastFrameNumber = j;
                this.mLastFrameBlurRegions = blurRegionArr;
                handleDispatchBlurTransactionLocked(j, blurRegionArr, z);
            }
        }

        void onRenderNodePositionChanged(long j, java.lang.Runnable runnable) {
            synchronized (this.mRtLock) {
                android.util.ArraySet<java.lang.Runnable> arraySet = this.mFrameRtUpdates.get(j);
                if (arraySet == null) {
                    arraySet = new android.util.ArraySet<>();
                    this.mFrameRtUpdates.put(j, arraySet);
                }
                arraySet.add(runnable);
                if (this.mLastFrameNumber == j) {
                    handleDispatchBlurTransactionLocked(j, this.mLastFrameBlurRegions, true);
                }
            }
        }

        public boolean hasUpdates() {
            return this.mHasUiUpdates;
        }

        public boolean hasRegions() {
            return this.mDrawables.size() > 0;
        }

        public com.android.internal.graphics.drawable.BackgroundBlurDrawable.BlurRegion[] getBlurRegionsCopyForRT() {
            if (this.mHasUiUpdates) {
                this.mTmpBlurRegionsForFrame = new com.android.internal.graphics.drawable.BackgroundBlurDrawable.BlurRegion[this.mDrawables.size()];
                for (int i = 0; i < this.mDrawables.size(); i++) {
                    this.mTmpBlurRegionsForFrame[i] = new com.android.internal.graphics.drawable.BackgroundBlurDrawable.BlurRegion(this.mDrawables.valueAt(i));
                }
                this.mHasUiUpdates = false;
            }
            return this.mTmpBlurRegionsForFrame;
        }

        public float[][] getBlurRegionsForFrameLocked(long j, com.android.internal.graphics.drawable.BackgroundBlurDrawable.BlurRegion[] blurRegionArr, boolean z) {
            if (!z && (this.mFrameRtUpdates.size() == 0 || this.mFrameRtUpdates.keyAt(0) > j)) {
                return null;
            }
            while (this.mFrameRtUpdates.size() != 0 && this.mFrameRtUpdates.keyAt(0) <= j) {
                android.util.ArraySet<java.lang.Runnable> valueAt = this.mFrameRtUpdates.valueAt(0);
                this.mFrameRtUpdates.removeAt(0);
                for (int i = 0; i < valueAt.size(); i++) {
                    valueAt.valueAt(i).run();
                }
            }
            if (com.android.internal.graphics.drawable.BackgroundBlurDrawable.DEBUG) {
                android.util.Log.d(com.android.internal.graphics.drawable.BackgroundBlurDrawable.TAG, "Dispatching " + blurRegionArr.length + " blur regions:");
            }
            int length = blurRegionArr.length;
            float[][] fArr = new float[length][];
            for (int i2 = 0; i2 < length; i2++) {
                fArr[i2] = blurRegionArr[i2].toFloatArray();
                if (com.android.internal.graphics.drawable.BackgroundBlurDrawable.DEBUG) {
                    android.util.Log.d(com.android.internal.graphics.drawable.BackgroundBlurDrawable.TAG, blurRegionArr[i2].toString());
                }
            }
            return fArr;
        }

        private void handleDispatchBlurTransactionLocked(long j, com.android.internal.graphics.drawable.BackgroundBlurDrawable.BlurRegion[] blurRegionArr, boolean z) {
            float[][] blurRegionsForFrameLocked = getBlurRegionsForFrameLocked(j, blurRegionArr, z);
            if (blurRegionsForFrameLocked != null) {
                this.mViewRoot.dispatchBlurRegions(blurRegionsForFrameLocked, j);
            }
        }
    }

    public static final class BlurRegion {
        public final float alpha;
        public final int blurRadius;
        public final float cornerRadiusBL;
        public final float cornerRadiusBR;
        public final float cornerRadiusTL;
        public final float cornerRadiusTR;
        public final android.graphics.Rect rect;

        BlurRegion(com.android.internal.graphics.drawable.BackgroundBlurDrawable backgroundBlurDrawable) {
            this.alpha = backgroundBlurDrawable.mAlpha;
            this.blurRadius = backgroundBlurDrawable.mBlurRadius;
            this.cornerRadiusTL = backgroundBlurDrawable.mCornerRadiusTL;
            this.cornerRadiusTR = backgroundBlurDrawable.mCornerRadiusTR;
            this.cornerRadiusBL = backgroundBlurDrawable.mCornerRadiusBL;
            this.cornerRadiusBR = backgroundBlurDrawable.mCornerRadiusBR;
            this.rect = backgroundBlurDrawable.mRect;
        }

        float[] toFloatArray() {
            return new float[]{this.blurRadius, this.alpha, this.rect.left, this.rect.top, this.rect.right, this.rect.bottom, this.cornerRadiusTL, this.cornerRadiusTR, this.cornerRadiusBL, this.cornerRadiusBR};
        }

        public java.lang.String toString() {
            return "BlurRegion{blurRadius=" + this.blurRadius + ", corners={" + this.cornerRadiusTL + "," + this.cornerRadiusTR + "," + this.cornerRadiusBL + "," + this.cornerRadiusBR + "}, alpha=" + this.alpha + ", rect=" + this.rect + "}";
        }
    }
}
