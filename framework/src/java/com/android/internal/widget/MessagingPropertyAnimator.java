package com.android.internal.widget;

/* loaded from: classes5.dex */
public class MessagingPropertyAnimator implements android.view.View.OnLayoutChangeListener {
    private static final long APPEAR_ANIMATION_LENGTH = 210;
    private static final int TAG_ALPHA_ANIMATOR = 16909615;
    private static final int TAG_FIRST_LAYOUT = 16909616;
    private static final int TAG_LAYOUT_TOP = 16909618;
    private static final int TAG_TOP = 16909622;
    private static final int TAG_TOP_ANIMATOR = 16909621;
    public static final android.view.animation.Interpolator ALPHA_IN = new android.view.animation.PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    public static final android.view.animation.Interpolator ALPHA_OUT = new android.view.animation.PathInterpolator(0.0f, 0.0f, 0.8f, 1.0f);
    private static final com.android.internal.widget.ViewClippingUtil.ClippingParameters CLIPPING_PARAMETERS = new com.android.internal.widget.ViewClippingUtil.ClippingParameters() { // from class: com.android.internal.widget.MessagingPropertyAnimator$$ExternalSyntheticLambda0
        @Override // com.android.internal.widget.ViewClippingUtil.ClippingParameters
        public final boolean shouldFinish(android.view.View view) {
            return com.android.internal.widget.MessagingPropertyAnimator.lambda$static$0(view);
        }
    };
    private static final android.util.IntProperty<android.view.View> TOP = new android.util.IntProperty<android.view.View>("top") { // from class: com.android.internal.widget.MessagingPropertyAnimator.1
        @Override // android.util.IntProperty
        public void setValue(android.view.View view, int i) {
            com.android.internal.widget.MessagingPropertyAnimator.setTop(view, i);
        }

        @Override // android.util.Property
        public java.lang.Integer get(android.view.View view) {
            return java.lang.Integer.valueOf(com.android.internal.widget.MessagingPropertyAnimator.getTop(view));
        }
    };

    static /* synthetic */ boolean lambda$static$0(android.view.View view) {
        return view.getId() == 16909325;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public void onLayoutChange(android.view.View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        setLayoutTop(view, i2);
        if (isFirstLayout(view)) {
            setFirstLayout(view, false);
            setTop(view, i2);
        } else {
            startTopAnimation(view, getTop(view), i2, com.android.internal.widget.MessagingLayout.FAST_OUT_SLOW_IN);
        }
    }

    private static boolean isFirstLayout(android.view.View view) {
        java.lang.Boolean bool = (java.lang.Boolean) view.getTag(16909616);
        if (bool == null) {
            return true;
        }
        return bool.booleanValue();
    }

    public static void recycle(android.view.View view) {
        setFirstLayout(view, true);
    }

    private static void setFirstLayout(android.view.View view, boolean z) {
        view.setTagInternal(16909616, java.lang.Boolean.valueOf(z));
    }

    private static void setLayoutTop(android.view.View view, int i) {
        view.setTagInternal(16909618, java.lang.Integer.valueOf(i));
    }

    public static int getLayoutTop(android.view.View view) {
        java.lang.Integer num = (java.lang.Integer) view.getTag(16909618);
        if (num == null) {
            return getTop(view);
        }
        return num.intValue();
    }

    public static void startLocalTranslationFrom(android.view.View view, int i, android.view.animation.Interpolator interpolator) {
        startTopAnimation(view, getTop(view) + i, getLayoutTop(view), interpolator);
    }

    public static void startLocalTranslationTo(android.view.View view, int i, android.view.animation.Interpolator interpolator) {
        int top = getTop(view);
        startTopAnimation(view, top, i + top, interpolator);
    }

    public static int getTop(android.view.View view) {
        java.lang.Integer num = (java.lang.Integer) view.getTag(16909622);
        if (num == null) {
            return view.getTop();
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setTop(android.view.View view, int i) {
        view.setTagInternal(16909622, java.lang.Integer.valueOf(i));
        updateTopAndBottom(view);
    }

    private static void updateTopAndBottom(android.view.View view) {
        int top = getTop(view);
        int height = view.getHeight();
        view.setTop(top);
        view.setBottom(height + top);
    }

    private static void startTopAnimation(final android.view.View view, int i, int i2, android.view.animation.Interpolator interpolator) {
        android.animation.ObjectAnimator objectAnimator = (android.animation.ObjectAnimator) view.getTag(16909621);
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (!view.isShown() || i == i2 || (com.android.internal.widget.MessagingLinearLayout.isGone(view) && !isHidingAnimated(view))) {
            setTop(view, i2);
            return;
        }
        android.animation.ObjectAnimator ofInt = android.animation.ObjectAnimator.ofInt(view, TOP, i, i2);
        setTop(view, i);
        ofInt.setInterpolator(interpolator);
        ofInt.setDuration(APPEAR_ANIMATION_LENGTH);
        ofInt.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.MessagingPropertyAnimator.2
            public boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                android.view.View.this.setTagInternal(16909621, null);
                com.android.internal.widget.MessagingPropertyAnimator.setClippingDeactivated(android.view.View.this, false);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(android.animation.Animator animator) {
                this.mCancelled = true;
            }
        });
        setClippingDeactivated(view, true);
        view.setTagInternal(16909621, ofInt);
        ofInt.start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isHidingAnimated(android.view.View view) {
        if (view instanceof com.android.internal.widget.MessagingLinearLayout.MessagingChild) {
            return ((com.android.internal.widget.MessagingLinearLayout.MessagingChild) view).isHidingAnimated();
        }
        return false;
    }

    public static void fadeIn(final android.view.View view) {
        android.animation.ObjectAnimator objectAnimator = (android.animation.ObjectAnimator) view.getTag(16909615);
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (view.getVisibility() == 4) {
            view.setVisibility(0);
        }
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(view, android.view.View.ALPHA, 0.0f, 1.0f);
        view.setAlpha(0.0f);
        ofFloat.setInterpolator(ALPHA_IN);
        ofFloat.setDuration(APPEAR_ANIMATION_LENGTH);
        ofFloat.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.MessagingPropertyAnimator.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                android.view.View.this.setTagInternal(16909615, null);
                com.android.internal.widget.MessagingPropertyAnimator.updateLayerType(android.view.View.this, false);
            }
        });
        updateLayerType(view, true);
        view.setTagInternal(16909615, ofFloat);
        ofFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateLayerType(android.view.View view, boolean z) {
        if (view.hasOverlappingRendering() && z) {
            view.setLayerType(2, null);
        } else if (view.getLayerType() == 2) {
            view.setLayerType(0, null);
        }
    }

    public static void fadeOut(final android.view.View view, final java.lang.Runnable runnable) {
        android.animation.ObjectAnimator objectAnimator = (android.animation.ObjectAnimator) view.getTag(16909615);
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (!view.isShown() || (com.android.internal.widget.MessagingLinearLayout.isGone(view) && !isHidingAnimated(view))) {
            view.setAlpha(0.0f);
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(view, android.view.View.ALPHA, view.getAlpha(), 0.0f);
        ofFloat.setInterpolator(ALPHA_OUT);
        ofFloat.setDuration(APPEAR_ANIMATION_LENGTH);
        ofFloat.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.MessagingPropertyAnimator.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                android.view.View.this.setTagInternal(16909615, null);
                com.android.internal.widget.MessagingPropertyAnimator.updateLayerType(android.view.View.this, false);
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        updateLayerType(view, true);
        view.setTagInternal(16909615, ofFloat);
        ofFloat.start();
    }

    public static void setClippingDeactivated(android.view.View view, boolean z) {
        com.android.internal.widget.ViewClippingUtil.setClippingDeactivated(view, z, CLIPPING_PARAMETERS);
    }

    public static boolean isAnimatingTranslation(android.view.View view) {
        return view.getTag(16909621) != null;
    }

    public static boolean isAnimatingAlpha(android.view.View view) {
        return view.getTag(16909615) != null;
    }

    public static void setToLaidOutPosition(android.view.View view) {
        setTop(view, getLayoutTop(view));
    }
}
