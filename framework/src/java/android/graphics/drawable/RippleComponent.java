package android.graphics.drawable;

/* loaded from: classes.dex */
abstract class RippleComponent {
    protected final android.graphics.Rect mBounds;
    protected float mDensityScale;
    private boolean mHasMaxRadius;
    protected final android.graphics.drawable.RippleDrawable mOwner;
    protected float mTargetRadius;

    public RippleComponent(android.graphics.drawable.RippleDrawable rippleDrawable, android.graphics.Rect rect) {
        this.mOwner = rippleDrawable;
        this.mBounds = rect;
    }

    public void onBoundsChange() {
        if (!this.mHasMaxRadius) {
            this.mTargetRadius = getTargetRadius(this.mBounds);
            onTargetRadiusChanged(this.mTargetRadius);
        }
    }

    public final void setup(float f, int i) {
        if (f >= 0.0f) {
            this.mHasMaxRadius = true;
            this.mTargetRadius = f;
        } else {
            this.mTargetRadius = getTargetRadius(this.mBounds);
        }
        this.mDensityScale = i * 0.00625f;
        onTargetRadiusChanged(this.mTargetRadius);
    }

    private static float getTargetRadius(android.graphics.Rect rect) {
        float width = rect.width() / 2.0f;
        float height = rect.height() / 2.0f;
        return (float) java.lang.Math.sqrt((width * width) + (height * height));
    }

    public void getBounds(android.graphics.Rect rect) {
        int ceil = (int) java.lang.Math.ceil(this.mTargetRadius);
        int i = -ceil;
        rect.set(i, i, ceil, ceil);
    }

    protected final void invalidateSelf() {
        this.mOwner.invalidateSelf(false);
    }

    protected final void onHotspotBoundsChanged() {
        if (!this.mHasMaxRadius) {
            this.mTargetRadius = getTargetRadius(this.mBounds);
            onTargetRadiusChanged(this.mTargetRadius);
        }
    }

    protected void onTargetRadiusChanged(float f) {
    }
}
