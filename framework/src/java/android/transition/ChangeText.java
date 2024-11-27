package android.transition;

/* loaded from: classes3.dex */
public class ChangeText extends android.transition.Transition {
    public static final int CHANGE_BEHAVIOR_IN = 2;
    public static final int CHANGE_BEHAVIOR_KEEP = 0;
    public static final int CHANGE_BEHAVIOR_OUT = 1;
    public static final int CHANGE_BEHAVIOR_OUT_IN = 3;
    private static final java.lang.String LOG_TAG = "TextChange";
    private static final java.lang.String PROPNAME_TEXT_COLOR = "android:textchange:textColor";
    private int mChangeBehavior = 0;
    private static final java.lang.String PROPNAME_TEXT = "android:textchange:text";
    private static final java.lang.String PROPNAME_TEXT_SELECTION_START = "android:textchange:textSelectionStart";
    private static final java.lang.String PROPNAME_TEXT_SELECTION_END = "android:textchange:textSelectionEnd";
    private static final java.lang.String[] sTransitionProperties = {PROPNAME_TEXT, PROPNAME_TEXT_SELECTION_START, PROPNAME_TEXT_SELECTION_END};

    public android.transition.ChangeText setChangeBehavior(int i) {
        if (i >= 0 && i <= 3) {
            this.mChangeBehavior = i;
        }
        return this;
    }

    @Override // android.transition.Transition
    public java.lang.String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public int getChangeBehavior() {
        return this.mChangeBehavior;
    }

    private void captureValues(android.transition.TransitionValues transitionValues) {
        if (transitionValues.view instanceof android.widget.TextView) {
            android.widget.TextView textView = (android.widget.TextView) transitionValues.view;
            transitionValues.values.put(PROPNAME_TEXT, textView.getText());
            if (textView instanceof android.widget.EditText) {
                transitionValues.values.put(PROPNAME_TEXT_SELECTION_START, java.lang.Integer.valueOf(textView.getSelectionStart()));
                transitionValues.values.put(PROPNAME_TEXT_SELECTION_END, java.lang.Integer.valueOf(textView.getSelectionEnd()));
            }
            if (this.mChangeBehavior > 0) {
                transitionValues.values.put(PROPNAME_TEXT_COLOR, java.lang.Integer.valueOf(textView.getCurrentTextColor()));
            }
        }
    }

    @Override // android.transition.Transition
    public void captureStartValues(android.transition.TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.transition.Transition
    public void captureEndValues(android.transition.TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x018b  */
    @Override // android.transition.Transition
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.animation.Animator createAnimator(android.view.ViewGroup viewGroup, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        java.lang.CharSequence charSequence;
        android.widget.TextView textView;
        int i6;
        char c;
        int i7;
        android.animation.Animator animator;
        int i8;
        android.animation.ValueAnimator ofInt;
        final android.widget.TextView textView2;
        android.animation.Animator animator2;
        final int i9;
        if (transitionValues == null || transitionValues2 == null || !(transitionValues.view instanceof android.widget.TextView) || !(transitionValues2.view instanceof android.widget.TextView)) {
            return null;
        }
        final android.widget.TextView textView3 = (android.widget.TextView) transitionValues2.view;
        java.util.Map<java.lang.String, java.lang.Object> map = transitionValues.values;
        java.util.Map<java.lang.String, java.lang.Object> map2 = transitionValues2.values;
        java.lang.String str = map.get(PROPNAME_TEXT) != null ? (java.lang.CharSequence) map.get(PROPNAME_TEXT) : "";
        java.lang.String str2 = map2.get(PROPNAME_TEXT) != null ? (java.lang.CharSequence) map2.get(PROPNAME_TEXT) : "";
        boolean z = textView3 instanceof android.widget.EditText;
        if (z) {
            int intValue = map.get(PROPNAME_TEXT_SELECTION_START) != null ? ((java.lang.Integer) map.get(PROPNAME_TEXT_SELECTION_START)).intValue() : -1;
            i = map.get(PROPNAME_TEXT_SELECTION_END) != null ? ((java.lang.Integer) map.get(PROPNAME_TEXT_SELECTION_END)).intValue() : intValue;
            int intValue2 = map2.get(PROPNAME_TEXT_SELECTION_START) != null ? ((java.lang.Integer) map2.get(PROPNAME_TEXT_SELECTION_START)).intValue() : -1;
            i2 = intValue2;
            i3 = map2.get(PROPNAME_TEXT_SELECTION_END) != null ? ((java.lang.Integer) map2.get(PROPNAME_TEXT_SELECTION_END)).intValue() : intValue2;
            i4 = intValue;
        } else {
            i = -1;
            i2 = -1;
            i3 = -1;
            i4 = -1;
        }
        if (!str.equals(str2)) {
            if (this.mChangeBehavior != 2) {
                textView3.lambda$setTextAsync$0(str);
                if (z) {
                    setSelection((android.widget.EditText) textView3, i4, i);
                }
            }
            if (this.mChangeBehavior == 0) {
                android.animation.Animator ofFloat = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
                final java.lang.CharSequence charSequence2 = str;
                textView2 = textView3;
                final java.lang.CharSequence charSequence3 = str2;
                i5 = i4;
                final int i10 = i2;
                final int i11 = i3;
                ofFloat.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.transition.ChangeText.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(android.animation.Animator animator3) {
                        if (charSequence2.equals(textView2.getText())) {
                            textView2.lambda$setTextAsync$0(charSequence3);
                            if (textView2 instanceof android.widget.EditText) {
                                android.transition.ChangeText.this.setSelection((android.widget.EditText) textView2, i10, i11);
                            }
                        }
                    }
                });
                i7 = i;
                charSequence = str;
                i9 = 0;
                animator2 = ofFloat;
            } else {
                i5 = i4;
                final int intValue3 = ((java.lang.Integer) map.get(PROPNAME_TEXT_COLOR)).intValue();
                final int intValue4 = ((java.lang.Integer) map2.get(PROPNAME_TEXT_COLOR)).intValue();
                if (this.mChangeBehavior == 3 || this.mChangeBehavior == 1) {
                    android.animation.ValueAnimator ofInt2 = android.animation.ValueAnimator.ofInt(android.graphics.Color.alpha(intValue3), 0);
                    ofInt2.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.transition.ChangeText.2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                            textView3.setTextColor((((java.lang.Integer) valueAnimator.getAnimatedValue()).intValue() << 24) | (intValue3 & 16777215));
                        }
                    });
                    final java.lang.CharSequence charSequence4 = str;
                    charSequence = str;
                    textView = textView3;
                    i6 = 3;
                    final java.lang.CharSequence charSequence5 = str2;
                    c = 1;
                    final int i12 = i2;
                    final int i13 = i3;
                    i7 = i;
                    ofInt2.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.transition.ChangeText.3
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(android.animation.Animator animator3) {
                            if (charSequence4.equals(textView3.getText())) {
                                textView3.lambda$setTextAsync$0(charSequence5);
                                if (textView3 instanceof android.widget.EditText) {
                                    android.transition.ChangeText.this.setSelection((android.widget.EditText) textView3, i12, i13);
                                }
                            }
                            textView3.setTextColor(intValue4);
                        }
                    });
                    animator = ofInt2;
                } else {
                    c = 1;
                    i7 = i;
                    textView = textView3;
                    charSequence = str;
                    animator = null;
                    i6 = 3;
                }
                if (this.mChangeBehavior != i6) {
                    i8 = 2;
                    if (this.mChangeBehavior != 2) {
                        textView2 = textView;
                        ofInt = null;
                        if (animator == null && ofInt != null) {
                            android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
                            android.animation.Animator[] animatorArr = new android.animation.Animator[i8];
                            animatorArr[0] = animator;
                            animatorArr[c] = ofInt;
                            animatorSet.playSequentially(animatorArr);
                            animator2 = animatorSet;
                            i9 = intValue4;
                        } else if (animator == null) {
                            animator2 = animator;
                            i9 = intValue4;
                        } else {
                            animator2 = ofInt;
                            i9 = intValue4;
                        }
                    }
                } else {
                    i8 = 2;
                }
                ofInt = android.animation.ValueAnimator.ofInt(0, android.graphics.Color.alpha(intValue4));
                textView2 = textView;
                ofInt.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.transition.ChangeText.4
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                        textView2.setTextColor((((java.lang.Integer) valueAnimator.getAnimatedValue()).intValue() << 24) | (intValue4 & 16777215));
                    }
                });
                ofInt.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.transition.ChangeText.5
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(android.animation.Animator animator3) {
                        textView2.setTextColor(intValue4);
                    }
                });
                if (animator == null) {
                }
                if (animator == null) {
                }
            }
            final android.widget.TextView textView4 = textView2;
            final java.lang.CharSequence charSequence6 = str2;
            final int i14 = i2;
            final int i15 = i3;
            final java.lang.CharSequence charSequence7 = charSequence;
            final int i16 = i5;
            final int i17 = i7;
            addListener(new android.transition.TransitionListenerAdapter() { // from class: android.transition.ChangeText.6
                int mPausedColor = 0;

                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionPause(android.transition.Transition transition) {
                    if (android.transition.ChangeText.this.mChangeBehavior != 2) {
                        textView4.lambda$setTextAsync$0(charSequence6);
                        if (textView4 instanceof android.widget.EditText) {
                            android.transition.ChangeText.this.setSelection((android.widget.EditText) textView4, i14, i15);
                        }
                    }
                    if (android.transition.ChangeText.this.mChangeBehavior > 0) {
                        this.mPausedColor = textView4.getCurrentTextColor();
                        textView4.setTextColor(i9);
                    }
                }

                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionResume(android.transition.Transition transition) {
                    if (android.transition.ChangeText.this.mChangeBehavior != 2) {
                        textView4.lambda$setTextAsync$0(charSequence7);
                        if (textView4 instanceof android.widget.EditText) {
                            android.transition.ChangeText.this.setSelection((android.widget.EditText) textView4, i16, i17);
                        }
                    }
                    if (android.transition.ChangeText.this.mChangeBehavior > 0) {
                        textView4.setTextColor(this.mPausedColor);
                    }
                }

                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionEnd(android.transition.Transition transition) {
                    transition.removeListener(this);
                }
            });
            return animator2;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSelection(android.widget.EditText editText, int i, int i2) {
        if (i >= 0 && i2 >= 0) {
            editText.setSelection(i, i2);
        }
    }
}
