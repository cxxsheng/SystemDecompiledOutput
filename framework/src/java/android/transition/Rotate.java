package android.transition;

/* loaded from: classes3.dex */
public class Rotate extends android.transition.Transition {
    private static final java.lang.String PROPNAME_ROTATION = "android:rotate:rotation";

    @Override // android.transition.Transition
    public void captureStartValues(android.transition.TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_ROTATION, java.lang.Float.valueOf(transitionValues.view.getRotation()));
    }

    @Override // android.transition.Transition
    public void captureEndValues(android.transition.TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_ROTATION, java.lang.Float.valueOf(transitionValues.view.getRotation()));
    }

    @Override // android.transition.Transition
    public android.animation.Animator createAnimator(android.view.ViewGroup viewGroup, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        android.view.View view = transitionValues2.view;
        float floatValue = ((java.lang.Float) transitionValues.values.get(PROPNAME_ROTATION)).floatValue();
        float floatValue2 = ((java.lang.Float) transitionValues2.values.get(PROPNAME_ROTATION)).floatValue();
        if (floatValue == floatValue2) {
            return null;
        }
        view.setRotation(floatValue);
        return android.animation.ObjectAnimator.ofFloat(view, android.view.View.ROTATION, floatValue, floatValue2);
    }
}
