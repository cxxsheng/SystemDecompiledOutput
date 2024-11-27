package android.graphics.drawable;

/* loaded from: classes.dex */
public class TransitionDrawable extends android.graphics.drawable.LayerDrawable implements android.graphics.drawable.Drawable.Callback {
    private static final int TRANSITION_NONE = 2;
    private static final int TRANSITION_RUNNING = 1;
    private static final int TRANSITION_STARTING = 0;
    private int mAlpha;
    private boolean mCrossFade;
    private int mDuration;
    private int mFrom;
    private int mOriginalDuration;
    private boolean mReverse;
    private long mStartTimeMillis;
    private int mTo;
    private int mTransitionState;

    public TransitionDrawable(android.graphics.drawable.Drawable[] drawableArr) {
        this(new android.graphics.drawable.TransitionDrawable.TransitionState(null, null, null), drawableArr);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    TransitionDrawable() {
        this(new android.graphics.drawable.TransitionDrawable.TransitionState(null, null, null), (android.content.res.Resources) null);
    }

    private TransitionDrawable(android.graphics.drawable.TransitionDrawable.TransitionState transitionState, android.content.res.Resources resources) {
        super(transitionState, resources);
        this.mTransitionState = 2;
        this.mAlpha = 0;
    }

    private TransitionDrawable(android.graphics.drawable.TransitionDrawable.TransitionState transitionState, android.graphics.drawable.Drawable[] drawableArr) {
        super(drawableArr, transitionState);
        this.mTransitionState = 2;
        this.mAlpha = 0;
    }

    @Override // android.graphics.drawable.LayerDrawable
    android.graphics.drawable.LayerDrawable.LayerState createConstantState(android.graphics.drawable.LayerDrawable.LayerState layerState, android.content.res.Resources resources) {
        return new android.graphics.drawable.TransitionDrawable.TransitionState((android.graphics.drawable.TransitionDrawable.TransitionState) layerState, this, resources);
    }

    public void startTransition(int i) {
        this.mFrom = 0;
        this.mTo = 255;
        this.mAlpha = 0;
        this.mOriginalDuration = i;
        this.mDuration = i;
        this.mReverse = false;
        this.mTransitionState = 0;
        invalidateSelf();
    }

    public void showSecondLayer() {
        this.mAlpha = 255;
        this.mReverse = false;
        this.mTransitionState = 2;
        invalidateSelf();
    }

    public void resetTransition() {
        this.mAlpha = 0;
        this.mTransitionState = 2;
        invalidateSelf();
    }

    public void reverseTransition(int i) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (uptimeMillis - this.mStartTimeMillis > this.mDuration) {
            if (this.mTo == 0) {
                this.mFrom = 0;
                this.mTo = 255;
                this.mAlpha = 0;
                this.mReverse = false;
            } else {
                this.mFrom = 255;
                this.mTo = 0;
                this.mAlpha = 255;
                this.mReverse = true;
            }
            this.mOriginalDuration = i;
            this.mDuration = i;
            this.mTransitionState = 0;
            invalidateSelf();
            return;
        }
        this.mReverse = !this.mReverse;
        this.mFrom = this.mAlpha;
        this.mTo = this.mReverse ? 0 : 255;
        this.mDuration = (int) (this.mReverse ? uptimeMillis - this.mStartTimeMillis : this.mOriginalDuration - (uptimeMillis - this.mStartTimeMillis));
        this.mTransitionState = 0;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        boolean z;
        switch (this.mTransitionState) {
            case 0:
                this.mStartTimeMillis = android.os.SystemClock.uptimeMillis();
                this.mTransitionState = 1;
                z = false;
                break;
            case 1:
                if (this.mStartTimeMillis >= 0) {
                    float uptimeMillis = (android.os.SystemClock.uptimeMillis() - this.mStartTimeMillis) / this.mDuration;
                    z = uptimeMillis >= 1.0f;
                    this.mAlpha = (int) (this.mFrom + ((this.mTo - this.mFrom) * java.lang.Math.min(uptimeMillis, 1.0f)));
                    break;
                }
            default:
                z = true;
                break;
        }
        int i = this.mAlpha;
        boolean z2 = this.mCrossFade;
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        if (z) {
            if (!z2 || i == 0) {
                childDrawableArr[0].mDrawable.draw(canvas);
            }
            if (i == 255) {
                childDrawableArr[1].mDrawable.draw(canvas);
                return;
            }
            return;
        }
        android.graphics.drawable.Drawable drawable = childDrawableArr[0].mDrawable;
        if (z2) {
            drawable.setAlpha(255 - i);
        }
        drawable.draw(canvas);
        if (z2) {
            drawable.setAlpha(255);
        }
        if (i > 0) {
            android.graphics.drawable.Drawable drawable2 = childDrawableArr[1].mDrawable;
            drawable2.setAlpha(i);
            drawable2.draw(canvas);
            drawable2.setAlpha(255);
        }
        if (!z) {
            invalidateSelf();
        }
    }

    public void setCrossFadeEnabled(boolean z) {
        this.mCrossFade = z;
    }

    public boolean isCrossFadeEnabled() {
        return this.mCrossFade;
    }

    static class TransitionState extends android.graphics.drawable.LayerDrawable.LayerState {
        TransitionState(android.graphics.drawable.TransitionDrawable.TransitionState transitionState, android.graphics.drawable.TransitionDrawable transitionDrawable, android.content.res.Resources resources) {
            super(transitionState, transitionDrawable, resources);
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.TransitionDrawable(this, null);
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.TransitionDrawable(this, resources);
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }
    }
}
