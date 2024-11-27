package android.view;

/* loaded from: classes4.dex */
public final class WindowMetrics {
    private final android.graphics.Rect mBounds;
    private final float mDensity;
    private android.view.WindowInsets mWindowInsets;
    private java.util.function.Supplier<android.view.WindowInsets> mWindowInsetsSupplier;

    @java.lang.Deprecated
    public WindowMetrics(android.graphics.Rect rect, android.view.WindowInsets windowInsets) {
        this(rect, windowInsets, 1.0f);
    }

    public WindowMetrics(android.graphics.Rect rect, android.view.WindowInsets windowInsets, float f) {
        this.mBounds = rect;
        this.mWindowInsets = windowInsets;
        this.mDensity = f;
    }

    public WindowMetrics(android.graphics.Rect rect, java.util.function.Supplier<android.view.WindowInsets> supplier, float f) {
        this.mBounds = rect;
        this.mWindowInsetsSupplier = supplier;
        this.mDensity = f;
    }

    public android.graphics.Rect getBounds() {
        return this.mBounds;
    }

    public android.view.WindowInsets getWindowInsets() {
        if (this.mWindowInsets != null) {
            return this.mWindowInsets;
        }
        android.view.WindowInsets windowInsets = this.mWindowInsetsSupplier.get();
        this.mWindowInsets = windowInsets;
        return windowInsets;
    }

    public float getDensity() {
        return this.mDensity;
    }

    public java.lang.String toString() {
        return android.view.WindowMetrics.class.getSimpleName() + ":{bounds=" + this.mBounds + ", windowInsets=" + this.mWindowInsets + ", density=" + this.mDensity + "}";
    }
}
