package com.android.server.wm;

/* loaded from: classes3.dex */
public class WindowChangeAnimationSpec implements com.android.server.wm.LocalAnimationAdapter.AnimationSpec {
    static final int ANIMATION_DURATION = 336;
    private android.view.animation.Animation mAnimation;
    private final android.graphics.Rect mEndBounds;
    private final boolean mIsAppAnimation;
    private final boolean mIsThumbnail;
    private final android.graphics.Rect mStartBounds;
    private final java.lang.ThreadLocal<com.android.server.wm.WindowChangeAnimationSpec.TmpValues> mThreadLocalTmps = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: com.android.server.wm.WindowChangeAnimationSpec$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return com.android.server.wm.WindowChangeAnimationSpec.$r8$lambda$gy8ZErChYYFfmhoyiQOPrPdJIOA();
        }
    });
    private final android.graphics.Rect mTmpRect = new android.graphics.Rect();

    public static /* synthetic */ com.android.server.wm.WindowChangeAnimationSpec.TmpValues $r8$lambda$gy8ZErChYYFfmhoyiQOPrPdJIOA() {
        return new com.android.server.wm.WindowChangeAnimationSpec.TmpValues();
    }

    public WindowChangeAnimationSpec(android.graphics.Rect rect, android.graphics.Rect rect2, android.view.DisplayInfo displayInfo, float f, boolean z, boolean z2) {
        this.mStartBounds = new android.graphics.Rect(rect);
        this.mEndBounds = new android.graphics.Rect(rect2);
        this.mIsAppAnimation = z;
        this.mIsThumbnail = z2;
        createBoundsInterpolator((int) (f * 336.0f), displayInfo);
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public boolean getShowWallpaper() {
        return false;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public long getDuration() {
        return this.mAnimation.getDuration();
    }

    private void createBoundsInterpolator(long j, android.view.DisplayInfo displayInfo) {
        boolean z = ((this.mEndBounds.width() - this.mStartBounds.width()) + this.mEndBounds.height()) - this.mStartBounds.height() >= 0;
        long j2 = (long) (j * 0.7f);
        float width = ((this.mStartBounds.width() * 0.7f) / this.mEndBounds.width()) + 0.3f;
        float height = ((this.mStartBounds.height() * 0.7f) / this.mEndBounds.height()) + 0.3f;
        if (this.mIsThumbnail) {
            android.view.animation.AnimationSet animationSet = new android.view.animation.AnimationSet(true);
            android.view.animation.AlphaAnimation alphaAnimation = new android.view.animation.AlphaAnimation(1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
            alphaAnimation.setDuration(j2);
            if (!z) {
                alphaAnimation.setStartOffset(j - j2);
            }
            animationSet.addAnimation(alphaAnimation);
            float f = 1.0f / width;
            float f2 = 1.0f / height;
            android.view.animation.ScaleAnimation scaleAnimation = new android.view.animation.ScaleAnimation(f, f, f2, f2);
            scaleAnimation.setDuration(j);
            animationSet.addAnimation(scaleAnimation);
            this.mAnimation = animationSet;
            this.mAnimation.initialize(this.mStartBounds.width(), this.mStartBounds.height(), this.mEndBounds.width(), this.mEndBounds.height());
            return;
        }
        android.view.animation.AnimationSet animationSet2 = new android.view.animation.AnimationSet(true);
        android.view.animation.ScaleAnimation scaleAnimation2 = new android.view.animation.ScaleAnimation(width, 1.0f, height, 1.0f);
        scaleAnimation2.setDuration(j2);
        if (!z) {
            scaleAnimation2.setStartOffset(j - j2);
        }
        animationSet2.addAnimation(scaleAnimation2);
        android.view.animation.TranslateAnimation translateAnimation = new android.view.animation.TranslateAnimation(this.mStartBounds.left, this.mEndBounds.left, this.mStartBounds.top, this.mEndBounds.top);
        translateAnimation.setDuration(j);
        animationSet2.addAnimation(translateAnimation);
        android.graphics.Rect rect = new android.graphics.Rect(this.mStartBounds);
        android.graphics.Rect rect2 = new android.graphics.Rect(this.mEndBounds);
        rect.offsetTo(0, 0);
        rect2.offsetTo(0, 0);
        android.view.animation.ClipRectAnimation clipRectAnimation = new android.view.animation.ClipRectAnimation(rect, rect2);
        clipRectAnimation.setDuration(j);
        animationSet2.addAnimation(clipRectAnimation);
        this.mAnimation = animationSet2;
        this.mAnimation.initialize(this.mStartBounds.width(), this.mStartBounds.height(), displayInfo.appWidth, displayInfo.appHeight);
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public void apply(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, long j) {
        com.android.server.wm.WindowChangeAnimationSpec.TmpValues tmpValues = this.mThreadLocalTmps.get();
        if (this.mIsThumbnail) {
            this.mAnimation.getTransformation(j, tmpValues.mTransformation);
            transaction.setMatrix(surfaceControl, tmpValues.mTransformation.getMatrix(), tmpValues.mFloats);
            transaction.setAlpha(surfaceControl, tmpValues.mTransformation.getAlpha());
            return;
        }
        this.mAnimation.getTransformation(j, tmpValues.mTransformation);
        android.graphics.Matrix matrix = tmpValues.mTransformation.getMatrix();
        transaction.setMatrix(surfaceControl, matrix, tmpValues.mFloats);
        float[] fArr = tmpValues.mVecs;
        tmpValues.mVecs[2] = 0.0f;
        fArr[1] = 0.0f;
        float[] fArr2 = tmpValues.mVecs;
        tmpValues.mVecs[3] = 1.0f;
        fArr2[0] = 1.0f;
        matrix.mapVectors(tmpValues.mVecs);
        tmpValues.mVecs[0] = 1.0f / tmpValues.mVecs[0];
        tmpValues.mVecs[3] = 1.0f / tmpValues.mVecs[3];
        android.graphics.Rect clipRect = tmpValues.mTransformation.getClipRect();
        this.mTmpRect.left = (int) ((clipRect.left * tmpValues.mVecs[0]) + 0.5f);
        this.mTmpRect.right = (int) ((clipRect.right * tmpValues.mVecs[0]) + 0.5f);
        this.mTmpRect.top = (int) ((clipRect.top * tmpValues.mVecs[3]) + 0.5f);
        this.mTmpRect.bottom = (int) ((clipRect.bottom * tmpValues.mVecs[3]) + 0.5f);
        transaction.setWindowCrop(surfaceControl, this.mTmpRect);
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public long calculateStatusBarTransitionStartTime() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        return java.lang.Math.max(uptimeMillis, (((long) (this.mAnimation.getDuration() * 0.99f)) + uptimeMillis) - 120);
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public boolean canSkipFirstFrame() {
        return false;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public boolean needsEarlyWakeup() {
        return this.mIsAppAnimation;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.println(this.mAnimation.getDuration());
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public void dumpDebugInner(android.util.proto.ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1138166333441L, this.mAnimation.toString());
        protoOutputStream.end(start);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TmpValues {
        final float[] mFloats;
        final android.view.animation.Transformation mTransformation;
        final float[] mVecs;

        private TmpValues() {
            this.mTransformation = new android.view.animation.Transformation();
            this.mFloats = new float[9];
            this.mVecs = new float[4];
        }
    }
}
