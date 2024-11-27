package android.widget;

/* loaded from: classes4.dex */
public class EdgeEffect {
    private static final double DAMPING_RATIO = 0.98d;
    private static final float EPSILON = 0.001f;
    private static final float EXP_STRETCH_INTENSITY = 0.016f;
    private static final float GLOW_ALPHA_START = 0.09f;
    private static final double LINEAR_DISTANCE_TAKE_OVER = 8.0d;
    private static final float LINEAR_STRETCH_INTENSITY = 0.016f;
    private static final float LINEAR_VELOCITY_TAKE_OVER = 200.0f;
    private static final float MAX_ALPHA = 0.15f;
    private static final float MAX_GLOW_SCALE = 2.0f;
    private static final int MAX_VELOCITY = 10000;
    private static final int MIN_VELOCITY = 100;
    private static final double NATURAL_FREQUENCY = 24.657d;
    private static final float ON_ABSORB_VELOCITY_ADJUSTMENT = 13.0f;
    private static final int PULL_DECAY_TIME = 2000;
    private static final float PULL_DISTANCE_ALPHA_GLOW_FACTOR = 0.8f;
    private static final float PULL_GLOW_BEGIN = 0.0f;
    private static final int PULL_TIME = 167;
    private static final float RADIUS_FACTOR = 0.6f;
    private static final int RECEDE_TIME = 600;
    private static final float SCROLL_DIST_AFFECTED_BY_EXP_STRETCH = 0.33f;
    private static final int STATE_ABSORB = 2;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PULL = 1;
    private static final int STATE_PULL_DECAY = 4;
    private static final int STATE_RECEDE = 3;
    private static final java.lang.String TAG = "EdgeEffect";
    private static final int TYPE_GLOW = 0;
    private static final int TYPE_NONE = -1;
    private static final int TYPE_STRETCH = 1;
    public static final long USE_STRETCH_EDGE_EFFECT_BY_DEFAULT = 171228096;
    private static final double VALUE_THRESHOLD = 0.001d;
    private static final int VELOCITY_GLOW_FACTOR = 6;
    private static final double VELOCITY_THRESHOLD = 0.01d;
    private float mBaseGlowScale;
    private final android.graphics.Rect mBounds;
    private float mDisplacement;
    private float mDistance;
    private float mDuration;
    private int mEdgeEffectType;
    private float mGlowAlpha;
    private float mGlowAlphaFinish;
    private float mGlowAlphaStart;
    private float mGlowScaleY;
    private float mGlowScaleYFinish;
    private float mGlowScaleYStart;
    private float mHeight;
    private final android.view.animation.Interpolator mInterpolator;
    private final android.graphics.Paint mPaint;
    private float mPullDistance;
    private float mRadius;
    private long mStartTime;
    private int mState;
    private float mTargetDisplacement;
    private android.graphics.Matrix mTmpMatrix;
    private float[] mTmpPoints;
    private float mVelocity;
    private float mWidth;
    public static final android.graphics.BlendMode DEFAULT_BLEND_MODE = android.graphics.BlendMode.SRC_ATOP;
    private static final double ANGLE = 0.5235987755982988d;
    private static final float SIN = (float) java.lang.Math.sin(ANGLE);
    private static final float COS = (float) java.lang.Math.cos(ANGLE);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EdgeEffectType {
    }

    public EdgeEffect(android.content.Context context) {
        this(context, null);
    }

    public EdgeEffect(android.content.Context context, android.util.AttributeSet attributeSet) {
        this.mInterpolator = new android.view.animation.DecelerateInterpolator();
        this.mState = 0;
        this.mBounds = new android.graphics.Rect();
        this.mPaint = new android.graphics.Paint();
        this.mDisplacement = 0.5f;
        this.mTargetDisplacement = 0.5f;
        this.mEdgeEffectType = 0;
        this.mTmpMatrix = null;
        this.mTmpPoints = null;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.EdgeEffect);
        int color = obtainStyledAttributes.getColor(0, -10066330);
        this.mEdgeEffectType = android.compat.Compatibility.isChangeEnabled(USE_STRETCH_EDGE_EFFECT_BY_DEFAULT) ? 1 : 0;
        obtainStyledAttributes.recycle();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor((color & 16777215) | android.media.audio.Enums.AUDIO_FORMAT_DTS_UHD_P2);
        this.mPaint.setStyle(android.graphics.Paint.Style.FILL);
        this.mPaint.setBlendMode(DEFAULT_BLEND_MODE);
    }

    private int getCurrentEdgeEffectBehavior() {
        if (!android.animation.ValueAnimator.areAnimatorsEnabled()) {
            return -1;
        }
        return this.mEdgeEffectType;
    }

    public void setSize(int i, int i2) {
        float f = i;
        float f2 = (f * 0.6f) / SIN;
        float f3 = f2 - (COS * f2);
        float f4 = i2;
        float f5 = (0.6f * f4) / SIN;
        float f6 = f5 - (COS * f5);
        this.mRadius = f2;
        this.mBaseGlowScale = f3 > 0.0f ? java.lang.Math.min(f6 / f3, 1.0f) : 1.0f;
        this.mBounds.set(this.mBounds.left, this.mBounds.top, i, (int) java.lang.Math.min(f4, f3));
        this.mWidth = f;
        this.mHeight = f4;
    }

    public boolean isFinished() {
        return this.mState == 0;
    }

    public void finish() {
        this.mState = 0;
        this.mDistance = 0.0f;
        this.mVelocity = 0.0f;
    }

    public void onPull(float f) {
        onPull(f, 0.5f);
    }

    public void onPull(float f, float f2) {
        int currentEdgeEffectBehavior = getCurrentEdgeEffectBehavior();
        if (currentEdgeEffectBehavior == -1) {
            finish();
            return;
        }
        long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
        this.mTargetDisplacement = f2;
        if (this.mState == 4 && currentAnimationTimeMillis - this.mStartTime < this.mDuration && currentEdgeEffectBehavior == 0) {
            return;
        }
        if (this.mState != 1) {
            if (currentEdgeEffectBehavior == 1) {
                this.mPullDistance = this.mDistance;
            } else {
                this.mGlowScaleY = java.lang.Math.max(0.0f, this.mGlowScaleY);
            }
        }
        this.mState = 1;
        this.mStartTime = currentAnimationTimeMillis;
        this.mDuration = 167.0f;
        this.mPullDistance += f;
        if (currentEdgeEffectBehavior == 1) {
            this.mPullDistance = java.lang.Math.min(1.0f, this.mPullDistance);
        }
        this.mDistance = java.lang.Math.max(0.0f, this.mPullDistance);
        this.mVelocity = 0.0f;
        if (this.mPullDistance == 0.0f) {
            this.mGlowScaleYStart = 0.0f;
            this.mGlowScaleY = 0.0f;
            this.mGlowAlphaStart = 0.0f;
            this.mGlowAlpha = 0.0f;
        } else {
            float min = java.lang.Math.min(MAX_ALPHA, this.mGlowAlpha + (java.lang.Math.abs(f) * 0.8f));
            this.mGlowAlphaStart = min;
            this.mGlowAlpha = min;
            float max = (float) (java.lang.Math.max(0.0d, (1.0d - (1.0d / java.lang.Math.sqrt(java.lang.Math.abs(this.mPullDistance) * this.mBounds.height()))) - 0.3d) / 0.7d);
            this.mGlowScaleYStart = max;
            this.mGlowScaleY = max;
        }
        this.mGlowAlphaFinish = this.mGlowAlpha;
        this.mGlowScaleYFinish = this.mGlowScaleY;
        if (currentEdgeEffectBehavior == 1 && this.mDistance == 0.0f) {
            this.mState = 0;
        }
    }

    public float onPullDistance(float f, float f2) {
        int currentEdgeEffectBehavior = getCurrentEdgeEffectBehavior();
        if (currentEdgeEffectBehavior == -1) {
            return 0.0f;
        }
        float max = java.lang.Math.max(0.0f, f + this.mDistance) - this.mDistance;
        if (max == 0.0f && this.mDistance == 0.0f) {
            return 0.0f;
        }
        if (this.mState != 1 && this.mState != 4 && currentEdgeEffectBehavior == 0) {
            this.mPullDistance = this.mDistance;
            this.mState = 1;
        }
        onPull(max, f2);
        return max;
    }

    public float getDistance() {
        return this.mDistance;
    }

    public void onRelease() {
        this.mPullDistance = 0.0f;
        if (this.mState != 1 && this.mState != 4) {
            return;
        }
        this.mState = 3;
        this.mGlowAlphaStart = this.mGlowAlpha;
        this.mGlowScaleYStart = this.mGlowScaleY;
        this.mGlowAlphaFinish = 0.0f;
        this.mGlowScaleYFinish = 0.0f;
        this.mVelocity = 0.0f;
        this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
        this.mDuration = 600.0f;
    }

    public void onAbsorb(int i) {
        int currentEdgeEffectBehavior = getCurrentEdgeEffectBehavior();
        if (currentEdgeEffectBehavior == 1) {
            this.mState = 3;
            this.mVelocity = i * ON_ABSORB_VELOCITY_ADJUSTMENT;
            this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
        } else {
            if (currentEdgeEffectBehavior == 0) {
                this.mState = 2;
                this.mVelocity = 0.0f;
                int min = java.lang.Math.min(java.lang.Math.max(100, java.lang.Math.abs(i)), 10000);
                this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
                this.mDuration = (min * 0.02f) + MAX_ALPHA;
                this.mGlowAlphaStart = GLOW_ALPHA_START;
                this.mGlowScaleYStart = java.lang.Math.max(this.mGlowScaleY, 0.0f);
                this.mGlowScaleYFinish = java.lang.Math.min(((((min / 100) * min) * 1.5E-4f) / MAX_GLOW_SCALE) + 0.025f, 1.0f);
                this.mGlowAlphaFinish = java.lang.Math.max(this.mGlowAlphaStart, java.lang.Math.min(min * 6 * 1.0E-5f, MAX_ALPHA));
                this.mTargetDisplacement = 0.5f;
                return;
            }
            finish();
        }
    }

    public void setColor(int i) {
        this.mPaint.setColor(i);
    }

    public void setBlendMode(android.graphics.BlendMode blendMode) {
        this.mPaint.setBlendMode(blendMode);
    }

    public int getColor() {
        return this.mPaint.getColor();
    }

    public android.graphics.BlendMode getBlendMode() {
        return this.mPaint.getBlendMode();
    }

    public boolean draw(android.graphics.Canvas canvas) {
        float f;
        boolean z;
        int currentEdgeEffectBehavior = getCurrentEdgeEffectBehavior();
        if (currentEdgeEffectBehavior == 0) {
            update();
            int save = canvas.save();
            float centerX = this.mBounds.centerX();
            float height = this.mBounds.height() - this.mRadius;
            canvas.scale(1.0f, java.lang.Math.min(this.mGlowScaleY, 1.0f) * this.mBaseGlowScale, centerX, 0.0f);
            float width = (this.mBounds.width() * (java.lang.Math.max(0.0f, java.lang.Math.min(this.mDisplacement, 1.0f)) - 0.5f)) / MAX_GLOW_SCALE;
            canvas.clipRect(this.mBounds);
            canvas.translate(width, 0.0f);
            this.mPaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
            canvas.drawCircle(centerX, height, this.mRadius, this.mPaint);
            canvas.restoreToCount(save);
            f = 0.0f;
        } else if (currentEdgeEffectBehavior == 1 && (canvas instanceof android.graphics.RecordingCanvas)) {
            if (this.mState == 3) {
                updateSpring();
            }
            if (this.mDistance == 0.0f) {
                f = 0.0f;
            } else {
                android.graphics.RecordingCanvas recordingCanvas = (android.graphics.RecordingCanvas) canvas;
                if (this.mTmpMatrix == null) {
                    this.mTmpMatrix = new android.graphics.Matrix();
                    this.mTmpPoints = new float[12];
                }
                recordingCanvas.getMatrix(this.mTmpMatrix);
                this.mTmpPoints[0] = 0.0f;
                this.mTmpPoints[1] = 0.0f;
                this.mTmpPoints[2] = this.mWidth;
                this.mTmpPoints[3] = 0.0f;
                this.mTmpPoints[4] = this.mWidth;
                this.mTmpPoints[5] = this.mHeight;
                this.mTmpPoints[6] = 0.0f;
                this.mTmpPoints[7] = this.mHeight;
                this.mTmpPoints[8] = this.mWidth * this.mDisplacement;
                this.mTmpPoints[9] = 0.0f;
                this.mTmpPoints[10] = this.mWidth * this.mDisplacement;
                this.mTmpPoints[11] = this.mHeight * this.mDistance;
                this.mTmpMatrix.mapPoints(this.mTmpPoints);
                android.graphics.RenderNode renderNode = recordingCanvas.mNode;
                float left = renderNode.getLeft() + min(this.mTmpPoints[0], this.mTmpPoints[2], this.mTmpPoints[4], this.mTmpPoints[6]);
                float top = renderNode.getTop() + min(this.mTmpPoints[1], this.mTmpPoints[3], this.mTmpPoints[5], this.mTmpPoints[7]);
                float left2 = renderNode.getLeft() + max(this.mTmpPoints[0], this.mTmpPoints[2], this.mTmpPoints[4], this.mTmpPoints[6]);
                float top2 = renderNode.getTop() + max(this.mTmpPoints[1], this.mTmpPoints[3], this.mTmpPoints[5], this.mTmpPoints[7]);
                float dampStretchVector = dampStretchVector(java.lang.Math.max(-1.0f, java.lang.Math.min(1.0f, (this.mTmpPoints[10] - this.mTmpPoints[8]) / (left2 - left))));
                float dampStretchVector2 = dampStretchVector(java.lang.Math.max(-1.0f, java.lang.Math.min(1.0f, (this.mTmpPoints[11] - this.mTmpPoints[9]) / (top2 - top))));
                boolean z2 = java.lang.Float.isFinite(dampStretchVector) && java.lang.Float.isFinite(dampStretchVector2);
                if (left2 > left && top2 > top && this.mWidth > 0.0f && this.mHeight > 0.0f && z2) {
                    renderNode.stretch(dampStretchVector, dampStretchVector2, this.mWidth, this.mHeight);
                }
                f = 0.0f;
            }
        } else {
            this.mState = 0;
            f = 0.0f;
            this.mDistance = 0.0f;
            this.mVelocity = 0.0f;
        }
        if (this.mState == 3 && this.mDistance == f && this.mVelocity == f) {
            this.mState = 0;
            z = true;
        } else {
            z = false;
        }
        return this.mState != 0 || z;
    }

    private float min(float f, float f2, float f3, float f4) {
        return java.lang.Math.min(java.lang.Math.min(java.lang.Math.min(f, f2), f3), f4);
    }

    private float max(float f, float f2, float f3, float f4) {
        return java.lang.Math.max(java.lang.Math.max(java.lang.Math.max(f, f2), f3), f4);
    }

    public int getMaxHeight() {
        return (int) this.mHeight;
    }

    private void update() {
        float min = java.lang.Math.min((android.view.animation.AnimationUtils.currentAnimationTimeMillis() - this.mStartTime) / this.mDuration, 1.0f);
        float interpolation = this.mInterpolator.getInterpolation(min);
        this.mGlowAlpha = this.mGlowAlphaStart + ((this.mGlowAlphaFinish - this.mGlowAlphaStart) * interpolation);
        this.mGlowScaleY = this.mGlowScaleYStart + ((this.mGlowScaleYFinish - this.mGlowScaleYStart) * interpolation);
        if (this.mState != 1) {
            this.mDistance = calculateDistanceFromGlowValues(this.mGlowScaleY, this.mGlowAlpha);
        }
        this.mDisplacement = (this.mDisplacement + this.mTargetDisplacement) / MAX_GLOW_SCALE;
        if (min >= 0.999f) {
            switch (this.mState) {
                case 1:
                    this.mState = 4;
                    this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
                    this.mDuration = 2000.0f;
                    this.mGlowAlphaStart = this.mGlowAlpha;
                    this.mGlowScaleYStart = this.mGlowScaleY;
                    this.mGlowAlphaFinish = 0.0f;
                    this.mGlowScaleYFinish = 0.0f;
                    break;
                case 2:
                    this.mState = 3;
                    this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
                    this.mDuration = 600.0f;
                    this.mGlowAlphaStart = this.mGlowAlpha;
                    this.mGlowScaleYStart = this.mGlowScaleY;
                    this.mGlowAlphaFinish = 0.0f;
                    this.mGlowScaleYFinish = 0.0f;
                    break;
                case 3:
                    this.mState = 0;
                    break;
                case 4:
                    this.mState = 3;
                    break;
            }
        }
    }

    private void updateSpring() {
        float f;
        long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
        float f2 = (currentAnimationTimeMillis - this.mStartTime) / 1000.0f;
        if (f2 < 0.001f) {
            return;
        }
        this.mStartTime = currentAnimationTimeMillis;
        if (java.lang.Math.abs(this.mVelocity) <= 200.0f && java.lang.Math.abs(this.mDistance * this.mHeight) < LINEAR_DISTANCE_TAKE_OVER && java.lang.Math.signum(this.mVelocity) == (-java.lang.Math.signum(this.mDistance))) {
            this.mVelocity = java.lang.Math.signum(this.mVelocity) * 200.0f;
            float f3 = this.mDistance + ((this.mVelocity * f2) / this.mHeight);
            if (java.lang.Math.signum(f3) != java.lang.Math.signum(this.mDistance)) {
                this.mDistance = 0.0f;
                this.mVelocity = 0.0f;
                return;
            } else {
                this.mDistance = f3;
                return;
            }
        }
        double sqrt = java.lang.Math.sqrt(0.03960000000000008d) * NATURAL_FREQUENCY;
        double d = this.mDistance * this.mHeight;
        double d2 = (1.0d / sqrt) * ((this.mDistance * 24.16386d * this.mHeight) + this.mVelocity);
        double d3 = f2;
        double d4 = (-24.16386d) * d3;
        double d5 = d3 * sqrt;
        double pow = java.lang.Math.pow(2.718281828459045d, d4) * ((java.lang.Math.cos(d5) * d) + (java.lang.Math.sin(d5) * d2));
        double pow2 = ((-24.657d) * pow * DAMPING_RATIO) + (java.lang.Math.pow(2.718281828459045d, d4) * (((-sqrt) * d * java.lang.Math.sin(d5)) + (sqrt * d2 * java.lang.Math.cos(d5))));
        this.mDistance = ((float) pow) / this.mHeight;
        this.mVelocity = (float) pow2;
        if (this.mDistance <= 1.0f) {
            f = 0.0f;
        } else {
            this.mDistance = 1.0f;
            f = 0.0f;
            this.mVelocity = 0.0f;
        }
        if (isAtEquilibrium()) {
            this.mDistance = f;
            this.mVelocity = f;
        }
    }

    private float calculateDistanceFromGlowValues(float f, float f2) {
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f > 0.0f) {
            float f3 = 1.4285715f / (this.mGlowScaleY - 1.0f);
            return (f3 * f3) / this.mBounds.height();
        }
        return f2 / 0.8f;
    }

    private boolean isAtEquilibrium() {
        double d = this.mDistance * this.mHeight;
        return d < 0.0d || (java.lang.Math.abs((double) this.mVelocity) < VELOCITY_THRESHOLD && d < VALUE_THRESHOLD);
    }

    private float dampStretchVector(float f) {
        float f2 = f > 0.0f ? 1.0f : -1.0f;
        float abs = java.lang.Math.abs(f);
        return f2 * ((float) ((0.016f * abs) + ((1.0d - java.lang.Math.exp((-abs) * 8.237217334679498d)) * 0.01600000075995922d)));
    }
}
