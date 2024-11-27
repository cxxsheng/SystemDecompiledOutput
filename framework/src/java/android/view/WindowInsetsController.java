package android.view;

/* loaded from: classes4.dex */
public interface WindowInsetsController {
    public static final int APPEARANCE_LIGHT_CAPTION_BARS = 256;
    public static final int APPEARANCE_LIGHT_NAVIGATION_BARS = 16;
    public static final int APPEARANCE_LIGHT_STATUS_BARS = 8;
    public static final int APPEARANCE_LOW_PROFILE_BARS = 4;
    public static final int APPEARANCE_OPAQUE_NAVIGATION_BARS = 2;
    public static final int APPEARANCE_OPAQUE_STATUS_BARS = 1;
    public static final int APPEARANCE_SEMI_TRANSPARENT_NAVIGATION_BARS = 64;
    public static final int APPEARANCE_SEMI_TRANSPARENT_STATUS_BARS = 32;
    public static final int APPEARANCE_TRANSPARENT_CAPTION_BAR_BACKGROUND = 128;
    public static final int BEHAVIOR_DEFAULT = 1;

    @java.lang.Deprecated
    public static final int BEHAVIOR_SHOW_BARS_BY_SWIPE = 1;

    @java.lang.Deprecated
    public static final int BEHAVIOR_SHOW_BARS_BY_TOUCH = 0;
    public static final int BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE = 2;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Appearance {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Behavior {
    }

    public interface OnControllableInsetsChangedListener {
        void onControllableInsetsChanged(android.view.WindowInsetsController windowInsetsController, int i);
    }

    void addOnControllableInsetsChangedListener(android.view.WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListener);

    void controlWindowInsetsAnimation(int i, long j, android.view.animation.Interpolator interpolator, android.os.CancellationSignal cancellationSignal, android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener);

    int getRequestedVisibleTypes();

    android.view.InsetsState getState();

    int getSystemBarsAppearance();

    int getSystemBarsBehavior();

    void hide(int i);

    void removeOnControllableInsetsChangedListener(android.view.WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListener);

    void setAnimationsDisabled(boolean z);

    void setCaptionInsetsHeight(int i);

    void setSystemBarsAppearance(int i, int i2);

    void setSystemBarsBehavior(int i);

    void setSystemDrivenInsetsAnimationLoggingListener(android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener);

    void show(int i);

    default void setImeCaptionBarInsetsHeight(int i) {
    }
}
