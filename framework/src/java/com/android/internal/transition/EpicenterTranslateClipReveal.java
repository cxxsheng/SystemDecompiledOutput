package com.android.internal.transition;

/* loaded from: classes5.dex */
public class EpicenterTranslateClipReveal extends android.transition.Visibility {
    private static final java.lang.String PROPNAME_BOUNDS = "android:epicenterReveal:bounds";
    private static final java.lang.String PROPNAME_CLIP = "android:epicenterReveal:clip";
    private static final java.lang.String PROPNAME_TRANSLATE_X = "android:epicenterReveal:translateX";
    private static final java.lang.String PROPNAME_TRANSLATE_Y = "android:epicenterReveal:translateY";
    private static final java.lang.String PROPNAME_TRANSLATE_Z = "android:epicenterReveal:translateZ";
    private static final java.lang.String PROPNAME_Z = "android:epicenterReveal:z";
    private final android.animation.TimeInterpolator mInterpolatorX;
    private final android.animation.TimeInterpolator mInterpolatorY;
    private final android.animation.TimeInterpolator mInterpolatorZ;

    public EpicenterTranslateClipReveal() {
        this.mInterpolatorX = null;
        this.mInterpolatorY = null;
        this.mInterpolatorZ = null;
    }

    public EpicenterTranslateClipReveal(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.EpicenterTranslateClipReveal, 0, 0);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            this.mInterpolatorX = android.view.animation.AnimationUtils.loadInterpolator(context, resourceId);
        } else {
            this.mInterpolatorX = com.android.internal.transition.TransitionConstants.LINEAR_OUT_SLOW_IN;
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(1, 0);
        if (resourceId2 != 0) {
            this.mInterpolatorY = android.view.animation.AnimationUtils.loadInterpolator(context, resourceId2);
        } else {
            this.mInterpolatorY = com.android.internal.transition.TransitionConstants.FAST_OUT_SLOW_IN;
        }
        int resourceId3 = obtainStyledAttributes.getResourceId(2, 0);
        if (resourceId3 != 0) {
            this.mInterpolatorZ = android.view.animation.AnimationUtils.loadInterpolator(context, resourceId3);
        } else {
            this.mInterpolatorZ = com.android.internal.transition.TransitionConstants.FAST_OUT_SLOW_IN;
        }
        obtainStyledAttributes.recycle();
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

    private void captureValues(android.transition.TransitionValues transitionValues) {
        android.view.View view = transitionValues.view;
        if (view.getVisibility() == 8) {
            return;
        }
        transitionValues.values.put(PROPNAME_BOUNDS, new android.graphics.Rect(0, 0, view.getWidth(), view.getHeight()));
        transitionValues.values.put(PROPNAME_TRANSLATE_X, java.lang.Float.valueOf(view.getTranslationX()));
        transitionValues.values.put(PROPNAME_TRANSLATE_Y, java.lang.Float.valueOf(view.getTranslationY()));
        transitionValues.values.put(PROPNAME_TRANSLATE_Z, java.lang.Float.valueOf(view.getTranslationZ()));
        transitionValues.values.put(PROPNAME_Z, java.lang.Float.valueOf(view.getZ()));
        transitionValues.values.put(PROPNAME_CLIP, view.getClipBounds());
    }

    @Override // android.transition.Visibility
    public android.animation.Animator onAppear(android.view.ViewGroup viewGroup, android.view.View view, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        android.graphics.Rect rect = (android.graphics.Rect) transitionValues2.values.get(PROPNAME_BOUNDS);
        android.graphics.Rect epicenterOrCenter = getEpicenterOrCenter(rect);
        float centerX = epicenterOrCenter.centerX() - rect.centerX();
        float centerY = epicenterOrCenter.centerY() - rect.centerY();
        float floatValue = 0.0f - ((java.lang.Float) transitionValues2.values.get(PROPNAME_Z)).floatValue();
        view.setTranslationX(centerX);
        view.setTranslationY(centerY);
        view.setTranslationZ(floatValue);
        float floatValue2 = ((java.lang.Float) transitionValues2.values.get(PROPNAME_TRANSLATE_X)).floatValue();
        float floatValue3 = ((java.lang.Float) transitionValues2.values.get(PROPNAME_TRANSLATE_Y)).floatValue();
        float floatValue4 = ((java.lang.Float) transitionValues2.values.get(PROPNAME_TRANSLATE_Z)).floatValue();
        android.graphics.Rect bestRect = getBestRect(transitionValues2);
        android.graphics.Rect epicenterOrCenter2 = getEpicenterOrCenter(bestRect);
        view.setClipBounds(epicenterOrCenter2);
        return createRectAnimator(view, new com.android.internal.transition.EpicenterTranslateClipReveal.State(epicenterOrCenter2.left, epicenterOrCenter2.right, centerX), new com.android.internal.transition.EpicenterTranslateClipReveal.State(epicenterOrCenter2.top, epicenterOrCenter2.bottom, centerY), floatValue, new com.android.internal.transition.EpicenterTranslateClipReveal.State(bestRect.left, bestRect.right, floatValue2), new com.android.internal.transition.EpicenterTranslateClipReveal.State(bestRect.top, bestRect.bottom, floatValue3), floatValue4, transitionValues2, this.mInterpolatorX, this.mInterpolatorY, this.mInterpolatorZ);
    }

    @Override // android.transition.Visibility
    public android.animation.Animator onDisappear(android.view.ViewGroup viewGroup, android.view.View view, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        if (transitionValues == null) {
            return null;
        }
        android.graphics.Rect rect = (android.graphics.Rect) transitionValues2.values.get(PROPNAME_BOUNDS);
        android.graphics.Rect epicenterOrCenter = getEpicenterOrCenter(rect);
        float centerX = epicenterOrCenter.centerX() - rect.centerX();
        float centerY = epicenterOrCenter.centerY() - rect.centerY();
        float floatValue = 0.0f - ((java.lang.Float) transitionValues.values.get(PROPNAME_Z)).floatValue();
        float floatValue2 = ((java.lang.Float) transitionValues2.values.get(PROPNAME_TRANSLATE_X)).floatValue();
        float floatValue3 = ((java.lang.Float) transitionValues2.values.get(PROPNAME_TRANSLATE_Y)).floatValue();
        float floatValue4 = ((java.lang.Float) transitionValues2.values.get(PROPNAME_TRANSLATE_Z)).floatValue();
        android.graphics.Rect bestRect = getBestRect(transitionValues);
        android.graphics.Rect epicenterOrCenter2 = getEpicenterOrCenter(bestRect);
        view.setClipBounds(bestRect);
        return createRectAnimator(view, new com.android.internal.transition.EpicenterTranslateClipReveal.State(bestRect.left, bestRect.right, floatValue2), new com.android.internal.transition.EpicenterTranslateClipReveal.State(bestRect.top, bestRect.bottom, floatValue3), floatValue4, new com.android.internal.transition.EpicenterTranslateClipReveal.State(epicenterOrCenter2.left, epicenterOrCenter2.right, centerX), new com.android.internal.transition.EpicenterTranslateClipReveal.State(epicenterOrCenter2.top, epicenterOrCenter2.bottom, centerY), floatValue, transitionValues2, this.mInterpolatorX, this.mInterpolatorY, this.mInterpolatorZ);
    }

    private android.graphics.Rect getEpicenterOrCenter(android.graphics.Rect rect) {
        android.graphics.Rect epicenter = getEpicenter();
        if (epicenter != null) {
            return epicenter;
        }
        int centerX = rect.centerX();
        int centerY = rect.centerY();
        return new android.graphics.Rect(centerX, centerY, centerX, centerY);
    }

    private android.graphics.Rect getBestRect(android.transition.TransitionValues transitionValues) {
        android.graphics.Rect rect = (android.graphics.Rect) transitionValues.values.get(PROPNAME_CLIP);
        if (rect == null) {
            return (android.graphics.Rect) transitionValues.values.get(PROPNAME_BOUNDS);
        }
        return rect;
    }

    private static android.animation.Animator createRectAnimator(final android.view.View view, com.android.internal.transition.EpicenterTranslateClipReveal.State state, com.android.internal.transition.EpicenterTranslateClipReveal.State state2, float f, com.android.internal.transition.EpicenterTranslateClipReveal.State state3, com.android.internal.transition.EpicenterTranslateClipReveal.State state4, float f2, android.transition.TransitionValues transitionValues, android.animation.TimeInterpolator timeInterpolator, android.animation.TimeInterpolator timeInterpolator2, android.animation.TimeInterpolator timeInterpolator3) {
        com.android.internal.transition.EpicenterTranslateClipReveal.StateEvaluator stateEvaluator = new com.android.internal.transition.EpicenterTranslateClipReveal.StateEvaluator();
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(view, android.view.View.TRANSLATION_Z, f, f2);
        if (timeInterpolator3 != null) {
            ofFloat.setInterpolator(timeInterpolator3);
        }
        android.animation.ObjectAnimator ofObject = android.animation.ObjectAnimator.ofObject(view, new com.android.internal.transition.EpicenterTranslateClipReveal.StateProperty(com.android.internal.transition.EpicenterTranslateClipReveal.StateProperty.TARGET_X), stateEvaluator, state, state3);
        if (timeInterpolator != null) {
            ofObject.setInterpolator(timeInterpolator);
        }
        android.animation.ObjectAnimator ofObject2 = android.animation.ObjectAnimator.ofObject(view, new com.android.internal.transition.EpicenterTranslateClipReveal.StateProperty('y'), stateEvaluator, state2, state4);
        if (timeInterpolator2 != null) {
            ofObject2.setInterpolator(timeInterpolator2);
        }
        final android.graphics.Rect rect = (android.graphics.Rect) transitionValues.values.get(PROPNAME_CLIP);
        android.animation.AnimatorListenerAdapter animatorListenerAdapter = new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.transition.EpicenterTranslateClipReveal.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                android.view.View.this.setClipBounds(rect);
            }
        };
        android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
        animatorSet.playTogether(ofObject, ofObject2, ofFloat);
        animatorSet.addListener(animatorListenerAdapter);
        return animatorSet;
    }

    private static class State {
        int lower;
        float trans;
        int upper;

        public State() {
        }

        public State(int i, int i2, float f) {
            this.lower = i;
            this.upper = i2;
            this.trans = f;
        }
    }

    private static class StateEvaluator implements android.animation.TypeEvaluator<com.android.internal.transition.EpicenterTranslateClipReveal.State> {
        private final com.android.internal.transition.EpicenterTranslateClipReveal.State mTemp;

        private StateEvaluator() {
            this.mTemp = new com.android.internal.transition.EpicenterTranslateClipReveal.State();
        }

        @Override // android.animation.TypeEvaluator
        public com.android.internal.transition.EpicenterTranslateClipReveal.State evaluate(float f, com.android.internal.transition.EpicenterTranslateClipReveal.State state, com.android.internal.transition.EpicenterTranslateClipReveal.State state2) {
            this.mTemp.upper = state.upper + ((int) ((state2.upper - state.upper) * f));
            this.mTemp.lower = state.lower + ((int) ((state2.lower - state.lower) * f));
            this.mTemp.trans = state.trans + ((int) ((state2.trans - state.trans) * f));
            return this.mTemp;
        }
    }

    private static class StateProperty extends android.util.Property<android.view.View, com.android.internal.transition.EpicenterTranslateClipReveal.State> {
        public static final char TARGET_X = 'x';
        public static final char TARGET_Y = 'y';
        private final int mTargetDimension;
        private final android.graphics.Rect mTempRect;
        private final com.android.internal.transition.EpicenterTranslateClipReveal.State mTempState;

        public StateProperty(char c) {
            super(com.android.internal.transition.EpicenterTranslateClipReveal.State.class, "state_" + c);
            this.mTempRect = new android.graphics.Rect();
            this.mTempState = new com.android.internal.transition.EpicenterTranslateClipReveal.State();
            this.mTargetDimension = c;
        }

        @Override // android.util.Property
        public com.android.internal.transition.EpicenterTranslateClipReveal.State get(android.view.View view) {
            android.graphics.Rect rect = this.mTempRect;
            if (!view.getClipBounds(rect)) {
                rect.setEmpty();
            }
            com.android.internal.transition.EpicenterTranslateClipReveal.State state = this.mTempState;
            if (this.mTargetDimension == 120) {
                state.trans = view.getTranslationX();
                state.lower = rect.left + ((int) state.trans);
                state.upper = rect.right + ((int) state.trans);
            } else {
                state.trans = view.getTranslationY();
                state.lower = rect.top + ((int) state.trans);
                state.upper = rect.bottom + ((int) state.trans);
            }
            return state;
        }

        @Override // android.util.Property
        public void set(android.view.View view, com.android.internal.transition.EpicenterTranslateClipReveal.State state) {
            android.graphics.Rect rect = this.mTempRect;
            if (view.getClipBounds(rect)) {
                if (this.mTargetDimension == 120) {
                    rect.left = state.lower - ((int) state.trans);
                    rect.right = state.upper - ((int) state.trans);
                } else {
                    rect.top = state.lower - ((int) state.trans);
                    rect.bottom = state.upper - ((int) state.trans);
                }
                view.setClipBounds(rect);
            }
            if (this.mTargetDimension == 120) {
                view.setTranslationX(state.trans);
            } else {
                view.setTranslationY(state.trans);
            }
        }
    }
}
