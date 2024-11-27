package android.transition;

/* loaded from: classes3.dex */
public class Explode extends android.transition.Visibility {
    private static final java.lang.String PROPNAME_SCREEN_BOUNDS = "android:explode:screenBounds";
    private static final java.lang.String TAG = "Explode";
    private int[] mTempLoc;
    private static final android.animation.TimeInterpolator sDecelerate = new android.view.animation.DecelerateInterpolator();
    private static final android.animation.TimeInterpolator sAccelerate = new android.view.animation.AccelerateInterpolator();

    public Explode() {
        this.mTempLoc = new int[2];
        setPropagation(new android.transition.CircularPropagation());
    }

    public Explode(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTempLoc = new int[2];
        setPropagation(new android.transition.CircularPropagation());
    }

    private void captureValues(android.transition.TransitionValues transitionValues) {
        android.view.View view = transitionValues.view;
        view.getLocationOnScreen(this.mTempLoc);
        int i = this.mTempLoc[0];
        int i2 = this.mTempLoc[1];
        transitionValues.values.put(PROPNAME_SCREEN_BOUNDS, new android.graphics.Rect(i, i2, view.getWidth() + i, view.getHeight() + i2));
    }

    @Override // android.transition.Visibility, android.transition.Transition
    public void captureStartValues(android.transition.TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    @Override // android.transition.Visibility, android.transition.Transition
    public void captureEndValues(android.transition.TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        captureValues(transitionValues);
    }

    @Override // android.transition.Visibility
    public android.animation.Animator onAppear(android.view.ViewGroup viewGroup, android.view.View view, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        android.graphics.Rect rect = (android.graphics.Rect) transitionValues2.values.get(PROPNAME_SCREEN_BOUNDS);
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        calculateOut(viewGroup, rect, this.mTempLoc);
        return android.transition.TranslationAnimationCreator.createAnimation(view, transitionValues2, rect.left, rect.top, translationX + this.mTempLoc[0], translationY + this.mTempLoc[1], translationX, translationY, sDecelerate, this);
    }

    @Override // android.transition.Visibility
    public android.animation.Animator onDisappear(android.view.ViewGroup viewGroup, android.view.View view, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        float f;
        float f2;
        if (transitionValues == null) {
            return null;
        }
        android.graphics.Rect rect = (android.graphics.Rect) transitionValues.values.get(PROPNAME_SCREEN_BOUNDS);
        int i = rect.left;
        int i2 = rect.top;
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        int[] iArr = (int[]) transitionValues.view.getTag(com.android.internal.R.id.transitionPosition);
        if (iArr == null) {
            f = translationX;
            f2 = translationY;
        } else {
            f = (iArr[0] - rect.left) + translationX;
            f2 = (iArr[1] - rect.top) + translationY;
            rect.offsetTo(iArr[0], iArr[1]);
        }
        calculateOut(viewGroup, rect, this.mTempLoc);
        return android.transition.TranslationAnimationCreator.createAnimation(view, transitionValues, i, i2, translationX, translationY, f + this.mTempLoc[0], f2 + this.mTempLoc[1], sAccelerate, this);
    }

    private void calculateOut(android.view.View view, android.graphics.Rect rect, int[] iArr) {
        int centerY;
        int i;
        view.getLocationOnScreen(this.mTempLoc);
        int i2 = this.mTempLoc[0];
        int i3 = this.mTempLoc[1];
        android.graphics.Rect epicenter = getEpicenter();
        if (epicenter == null) {
            i = (view.getWidth() / 2) + i2 + java.lang.Math.round(view.getTranslationX());
            centerY = (view.getHeight() / 2) + i3 + java.lang.Math.round(view.getTranslationY());
        } else {
            int centerX = epicenter.centerX();
            centerY = epicenter.centerY();
            i = centerX;
        }
        double centerX2 = rect.centerX() - i;
        double centerY2 = rect.centerY() - centerY;
        if (centerX2 == 0.0d && centerY2 == 0.0d) {
            double random = (java.lang.Math.random() * 2.0d) - 1.0d;
            centerY2 = (java.lang.Math.random() * 2.0d) - 1.0d;
            centerX2 = random;
        }
        double hypot = java.lang.Math.hypot(centerX2, centerY2);
        double calculateMaxDistance = calculateMaxDistance(view, i - i2, centerY - i3);
        iArr[0] = (int) java.lang.Math.round((centerX2 / hypot) * calculateMaxDistance);
        iArr[1] = (int) java.lang.Math.round(calculateMaxDistance * (centerY2 / hypot));
    }

    private static double calculateMaxDistance(android.view.View view, int i, int i2) {
        return java.lang.Math.hypot(java.lang.Math.max(i, view.getWidth() - i), java.lang.Math.max(i2, view.getHeight() - i2));
    }
}
