package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
public class MagnificationThumbnail {
    private static final float ASPECT_RATIO = 14.0f;
    private static final float BG_ASPECT_RATIO = 7.0f;
    private static final boolean DEBUG = false;
    private static final int FADE_IN_ANIMATION_DURATION_MS = 200;
    private static final int FADE_OUT_ANIMATION_DURATION_MS = 1000;
    private static final int LINGER_DURATION_MS = 500;
    private static final java.lang.String LOG_TAG = "MagnificationThumbnail";
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private boolean mIsFadingIn;
    private android.animation.ObjectAnimator mThumbnailAnimator;

    @androidx.annotation.VisibleForTesting
    public android.widget.FrameLayout mThumbnailLayout;
    private android.view.View mThumbnailView;
    private android.graphics.Rect mWindowBounds;
    private final android.view.WindowManager mWindowManager;
    private boolean mVisible = false;
    private final android.view.WindowManager.LayoutParams mBackgroundParams = createLayoutParams();
    private int mThumbnailWidth = 0;
    private int mThumbnailHeight = 0;

    public MagnificationThumbnail(android.content.Context context, android.view.WindowManager windowManager, android.os.Handler handler) {
        this.mContext = context;
        this.mWindowManager = windowManager;
        this.mHandler = handler;
        this.mWindowBounds = this.mWindowManager.getCurrentWindowMetrics().getBounds();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.accessibility.magnification.MagnificationThumbnail$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.magnification.MagnificationThumbnail.this.createThumbnailLayout();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createThumbnailLayout() {
        this.mThumbnailLayout = (android.widget.FrameLayout) android.view.LayoutInflater.from(this.mContext).inflate(android.R.layout.text_edit_suggestion_item_material, (android.view.ViewGroup) null);
        this.mThumbnailView = this.mThumbnailLayout.findViewById(android.R.id.accessibility_magnification_thumbnail_view);
    }

    public void setThumbnailBounds(final android.graphics.Rect rect, final float f, final float f2, final float f3) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.accessibility.magnification.MagnificationThumbnail$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.magnification.MagnificationThumbnail.this.lambda$setThumbnailBounds$0(rect, f, f2, f3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setThumbnailBounds$0(android.graphics.Rect rect, float f, float f2, float f3) {
        refreshBackgroundBounds(rect);
        if (this.mVisible) {
            lambda$updateThumbnail$1(f, f2, f3);
        }
    }

    private void refreshBackgroundBounds(android.graphics.Rect rect) {
        this.mWindowBounds = rect;
        android.graphics.Point magnificationThumbnailPadding = getMagnificationThumbnailPadding(this.mContext);
        this.mThumbnailWidth = (int) (this.mWindowBounds.width() / BG_ASPECT_RATIO);
        this.mThumbnailHeight = (int) (this.mWindowBounds.height() / BG_ASPECT_RATIO);
        int i = magnificationThumbnailPadding.x;
        int i2 = magnificationThumbnailPadding.y;
        this.mBackgroundParams.width = this.mThumbnailWidth;
        this.mBackgroundParams.height = this.mThumbnailHeight;
        this.mBackgroundParams.x = i;
        this.mBackgroundParams.y = i2;
        if (this.mVisible) {
            this.mWindowManager.updateViewLayout(this.mThumbnailLayout, this.mBackgroundParams);
        }
    }

    private void showThumbnail() {
        animateThumbnail(true);
    }

    public void hideThumbnail() {
        this.mHandler.post(new com.android.server.accessibility.magnification.MagnificationThumbnail$$ExternalSyntheticLambda0(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideThumbnailMainThread() {
        if (this.mVisible) {
            animateThumbnail(false);
        }
    }

    private void animateThumbnail(final boolean z) {
        this.mHandler.removeCallbacks(new com.android.server.accessibility.magnification.MagnificationThumbnail$$ExternalSyntheticLambda0(this));
        if (z) {
            this.mHandler.postDelayed(new com.android.server.accessibility.magnification.MagnificationThumbnail$$ExternalSyntheticLambda0(this), 500L);
        }
        if (z == this.mIsFadingIn) {
            return;
        }
        this.mIsFadingIn = z;
        if (z && !this.mVisible) {
            this.mWindowManager.addView(this.mThumbnailLayout, this.mBackgroundParams);
            this.mVisible = true;
        }
        if (this.mThumbnailAnimator != null) {
            this.mThumbnailAnimator.cancel();
        }
        this.mThumbnailAnimator = android.animation.ObjectAnimator.ofFloat(this.mThumbnailLayout, "alpha", z ? 1.0f : com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        this.mThumbnailAnimator.setDuration(z ? 200L : 1000L);
        this.mThumbnailAnimator.addListener(new android.animation.Animator.AnimatorListener() { // from class: com.android.server.accessibility.magnification.MagnificationThumbnail.1
            private boolean mIsCancelled;

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(@androidx.annotation.NonNull android.animation.Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(@androidx.annotation.NonNull android.animation.Animator animator) {
                if (!this.mIsCancelled && !z && com.android.server.accessibility.magnification.MagnificationThumbnail.this.mVisible) {
                    com.android.server.accessibility.magnification.MagnificationThumbnail.this.mWindowManager.removeView(com.android.server.accessibility.magnification.MagnificationThumbnail.this.mThumbnailLayout);
                    com.android.server.accessibility.magnification.MagnificationThumbnail.this.mVisible = false;
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(@androidx.annotation.NonNull android.animation.Animator animator) {
                this.mIsCancelled = true;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(@androidx.annotation.NonNull android.animation.Animator animator) {
            }
        });
        this.mThumbnailAnimator.start();
    }

    public void updateThumbnail(final float f, final float f2, final float f3) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.accessibility.magnification.MagnificationThumbnail$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.magnification.MagnificationThumbnail.this.lambda$updateThumbnail$1(f, f2, f3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateThumbnailMainThread, reason: merged with bridge method [inline-methods] */
    public void lambda$updateThumbnail$1(float f, float f2, float f3) {
        showThumbnail();
        float scaleX = java.lang.Float.isNaN(f) ? this.mThumbnailView.getScaleX() : 1.0f / f;
        if (!java.lang.Float.isNaN(f)) {
            this.mThumbnailView.setScaleX(scaleX);
            this.mThumbnailView.setScaleY(scaleX);
        }
        if (!java.lang.Float.isNaN(f2) && !java.lang.Float.isNaN(f3) && this.mThumbnailWidth > 0 && this.mThumbnailHeight > 0) {
            float paddingTop = this.mThumbnailView.getPaddingTop();
            this.mThumbnailView.setTranslationX((f2 * 0.14285715f) - ((this.mThumbnailWidth / 2.0f) + paddingTop));
            this.mThumbnailView.setTranslationY((f3 * 0.14285715f) - ((this.mThumbnailHeight / 2.0f) + paddingTop));
        }
    }

    private android.view.WindowManager.LayoutParams createLayoutParams() {
        android.view.WindowManager.LayoutParams layoutParams = new android.view.WindowManager.LayoutParams(-2, -2, 2027, 24, -2);
        layoutParams.inputFeatures = 1;
        layoutParams.gravity = 83;
        layoutParams.setFitInsetsTypes(android.view.WindowInsets.Type.ime() | android.view.WindowInsets.Type.navigationBars());
        return layoutParams;
    }

    private android.graphics.Point getMagnificationThumbnailPadding(android.content.Context context) {
        android.graphics.Point point = new android.graphics.Point(0, 0);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(android.R.dimen.accessibility_magnification_thumbnail_padding);
        point.x = dimensionPixelSize;
        point.y = dimensionPixelSize;
        return point;
    }
}
