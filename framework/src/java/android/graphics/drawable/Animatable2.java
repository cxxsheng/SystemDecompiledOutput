package android.graphics.drawable;

/* loaded from: classes.dex */
public interface Animatable2 extends android.graphics.drawable.Animatable {
    void clearAnimationCallbacks();

    void registerAnimationCallback(android.graphics.drawable.Animatable2.AnimationCallback animationCallback);

    boolean unregisterAnimationCallback(android.graphics.drawable.Animatable2.AnimationCallback animationCallback);

    public static abstract class AnimationCallback {
        public void onAnimationStart(android.graphics.drawable.Drawable drawable) {
        }

        public void onAnimationEnd(android.graphics.drawable.Drawable drawable) {
        }
    }
}
