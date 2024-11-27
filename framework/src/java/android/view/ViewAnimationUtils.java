package android.view;

/* loaded from: classes4.dex */
public final class ViewAnimationUtils {
    private ViewAnimationUtils() {
    }

    public static android.animation.Animator createCircularReveal(android.view.View view, int i, int i2, float f, float f2) {
        return new android.animation.RevealAnimator(view, i, i2, f, f2);
    }
}
