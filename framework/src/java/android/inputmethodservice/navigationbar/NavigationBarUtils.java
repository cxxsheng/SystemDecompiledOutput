package android.inputmethodservice.navigationbar;

/* loaded from: classes2.dex */
final class NavigationBarUtils {
    private NavigationBarUtils() {
    }

    static int dpToPx(float f, android.content.res.Resources resources) {
        return (int) android.util.TypedValue.applyDimension(1, f, resources.getDisplayMetrics());
    }
}
