package android.transition;

/* loaded from: classes3.dex */
public class Fade extends android.transition.Visibility {
    private static boolean DBG = false;
    public static final int IN = 1;
    private static final java.lang.String LOG_TAG = "Fade";
    public static final int OUT = 2;
    static final java.lang.String PROPNAME_TRANSITION_ALPHA = "android:fade:transitionAlpha";

    public Fade() {
    }

    public Fade(int i) {
        setMode(i);
    }

    public Fade(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Fade);
        setMode(obtainStyledAttributes.getInt(0, getMode()));
        obtainStyledAttributes.recycle();
    }

    @Override // android.transition.Visibility, android.transition.Transition
    public void captureStartValues(android.transition.TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        transitionValues.values.put(PROPNAME_TRANSITION_ALPHA, java.lang.Float.valueOf(transitionValues.view.getTransitionAlpha()));
    }

    private android.animation.Animator createAnimation(final android.view.View view, float f, float f2) {
        if (f == f2) {
            return null;
        }
        view.setTransitionAlpha(f);
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(view, "transitionAlpha", f2);
        if (DBG) {
            android.util.Log.d(LOG_TAG, "Created animator " + ofFloat);
        }
        ofFloat.addListener(new android.transition.Fade.FadeAnimatorListener(view));
        addListener(new android.transition.TransitionListenerAdapter() { // from class: android.transition.Fade.1
            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionEnd(android.transition.Transition transition) {
                view.setTransitionAlpha(1.0f);
                transition.removeListener(this);
            }
        });
        return ofFloat;
    }

    @Override // android.transition.Visibility
    public android.animation.Animator onAppear(android.view.ViewGroup viewGroup, android.view.View view, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        if (DBG) {
            android.util.Log.d(LOG_TAG, "Fade.onAppear: startView, startVis, endView, endVis = " + (transitionValues != null ? transitionValues.view : null) + ", " + view);
        }
        float f = 0.0f;
        float startAlpha = getStartAlpha(transitionValues, 0.0f);
        if (startAlpha != 1.0f) {
            f = startAlpha;
        }
        return createAnimation(view, f, 1.0f);
    }

    @Override // android.transition.Visibility
    public android.animation.Animator onDisappear(android.view.ViewGroup viewGroup, android.view.View view, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        return createAnimation(view, getStartAlpha(transitionValues, 1.0f), 0.0f);
    }

    private static float getStartAlpha(android.transition.TransitionValues transitionValues, float f) {
        java.lang.Float f2;
        if (transitionValues != null && (f2 = (java.lang.Float) transitionValues.values.get(PROPNAME_TRANSITION_ALPHA)) != null) {
            return f2.floatValue();
        }
        return f;
    }

    private static class FadeAnimatorListener extends android.animation.AnimatorListenerAdapter {
        private boolean mLayerTypeChanged = false;
        private final android.view.View mView;

        public FadeAnimatorListener(android.view.View view) {
            this.mView = view;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
            if (this.mView.hasOverlappingRendering() && this.mView.getLayerType() == 0) {
                this.mLayerTypeChanged = true;
                this.mView.setLayerType(2, null);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            this.mView.setTransitionAlpha(1.0f);
            if (this.mLayerTypeChanged) {
                this.mView.setLayerType(0, null);
            }
        }
    }
}
