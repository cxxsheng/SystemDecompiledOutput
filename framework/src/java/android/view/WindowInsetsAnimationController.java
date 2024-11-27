package android.view;

/* loaded from: classes4.dex */
public interface WindowInsetsAnimationController {
    void finish(boolean z);

    float getCurrentAlpha();

    float getCurrentFraction();

    android.graphics.Insets getCurrentInsets();

    android.graphics.Insets getHiddenStateInsets();

    android.graphics.Insets getShownStateInsets();

    int getTypes();

    boolean hasZeroInsetsIme();

    boolean isCancelled();

    boolean isFinished();

    void setInsetsAndAlpha(android.graphics.Insets insets, float f, float f2);

    default boolean isReady() {
        return (isFinished() || isCancelled()) ? false : true;
    }
}
