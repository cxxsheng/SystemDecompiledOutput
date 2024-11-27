package android.window;

/* loaded from: classes4.dex */
public final class WindowMetricsHelper {
    private WindowMetricsHelper() {
    }

    public static android.graphics.Rect getBoundsExcludingNavigationBarAndCutout(android.view.WindowMetrics windowMetrics) {
        android.view.WindowInsets windowInsets = windowMetrics.getWindowInsets();
        android.graphics.Rect rect = new android.graphics.Rect(windowMetrics.getBounds());
        rect.inset(windowInsets.getInsetsIgnoringVisibility(android.view.WindowInsets.Type.navigationBars() | android.view.WindowInsets.Type.displayCutout()));
        return rect;
    }
}
