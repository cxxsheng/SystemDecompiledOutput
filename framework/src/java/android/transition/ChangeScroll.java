package android.transition;

/* loaded from: classes3.dex */
public class ChangeScroll extends android.transition.Transition {
    private static final java.lang.String PROPNAME_SCROLL_X = "android:changeScroll:x";
    private static final java.lang.String PROPNAME_SCROLL_Y = "android:changeScroll:y";
    private static final java.lang.String[] PROPERTIES = {PROPNAME_SCROLL_X, PROPNAME_SCROLL_Y};

    public ChangeScroll() {
    }

    public ChangeScroll(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.transition.Transition
    public void captureStartValues(android.transition.TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.transition.Transition
    public void captureEndValues(android.transition.TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.transition.Transition
    public java.lang.String[] getTransitionProperties() {
        return PROPERTIES;
    }

    private void captureValues(android.transition.TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_SCROLL_X, java.lang.Integer.valueOf(transitionValues.view.getScrollX()));
        transitionValues.values.put(PROPNAME_SCROLL_Y, java.lang.Integer.valueOf(transitionValues.view.getScrollY()));
    }

    @Override // android.transition.Transition
    public android.animation.Animator createAnimator(android.view.ViewGroup viewGroup, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        android.animation.ObjectAnimator objectAnimator;
        android.animation.ObjectAnimator objectAnimator2 = null;
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        android.view.View view = transitionValues2.view;
        int intValue = ((java.lang.Integer) transitionValues.values.get(PROPNAME_SCROLL_X)).intValue();
        int intValue2 = ((java.lang.Integer) transitionValues2.values.get(PROPNAME_SCROLL_X)).intValue();
        int intValue3 = ((java.lang.Integer) transitionValues.values.get(PROPNAME_SCROLL_Y)).intValue();
        int intValue4 = ((java.lang.Integer) transitionValues2.values.get(PROPNAME_SCROLL_Y)).intValue();
        if (intValue == intValue2) {
            objectAnimator = null;
        } else {
            view.setScrollX(intValue);
            objectAnimator = android.animation.ObjectAnimator.ofInt(view, "scrollX", intValue, intValue2);
        }
        if (intValue3 != intValue4) {
            view.setScrollY(intValue3);
            objectAnimator2 = android.animation.ObjectAnimator.ofInt(view, "scrollY", intValue3, intValue4);
        }
        return android.transition.TransitionUtils.mergeAnimators(objectAnimator, objectAnimator2);
    }
}
