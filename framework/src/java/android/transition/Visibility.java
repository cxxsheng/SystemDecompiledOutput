package android.transition;

/* loaded from: classes3.dex */
public abstract class Visibility extends android.transition.Transition {
    public static final int MODE_IN = 1;
    public static final int MODE_OUT = 2;
    private static final java.lang.String PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation";
    private int mMode;
    private boolean mSuppressLayout;
    static final java.lang.String PROPNAME_VISIBILITY = "android:visibility:visibility";
    private static final java.lang.String PROPNAME_PARENT = "android:visibility:parent";
    private static final java.lang.String[] sTransitionProperties = {PROPNAME_VISIBILITY, PROPNAME_PARENT};

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface VisibilityMode {
    }

    private static class VisibilityInfo {
        android.view.ViewGroup endParent;
        int endVisibility;
        boolean fadeIn;
        android.view.ViewGroup startParent;
        int startVisibility;
        boolean visibilityChange;

        private VisibilityInfo() {
        }
    }

    public Visibility() {
        this.mMode = 3;
        this.mSuppressLayout = true;
    }

    public Visibility(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMode = 3;
        this.mSuppressLayout = true;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.VisibilityTransition);
        int i = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
        if (i != 0) {
            setMode(i);
        }
    }

    public void setSuppressLayout(boolean z) {
        this.mSuppressLayout = z;
    }

    public void setMode(int i) {
        if ((i & (-4)) != 0) {
            throw new java.lang.IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
        }
        this.mMode = i;
    }

    public int getMode() {
        return this.mMode;
    }

    @Override // android.transition.Transition
    public java.lang.String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    private void captureValues(android.transition.TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_VISIBILITY, java.lang.Integer.valueOf(transitionValues.view.getVisibility()));
        transitionValues.values.put(PROPNAME_PARENT, transitionValues.view.getParent());
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put(PROPNAME_SCREEN_LOCATION, iArr);
    }

    @Override // android.transition.Transition
    public void captureStartValues(android.transition.TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.transition.Transition
    public void captureEndValues(android.transition.TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public boolean isVisible(android.transition.TransitionValues transitionValues) {
        if (transitionValues == null) {
            return false;
        }
        return ((java.lang.Integer) transitionValues.values.get(PROPNAME_VISIBILITY)).intValue() == 0 && ((android.view.View) transitionValues.values.get(PROPNAME_PARENT)) != null;
    }

    private static android.transition.Visibility.VisibilityInfo getVisibilityChangeInfo(android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        android.transition.Visibility.VisibilityInfo visibilityInfo = new android.transition.Visibility.VisibilityInfo();
        visibilityInfo.visibilityChange = false;
        visibilityInfo.fadeIn = false;
        if (transitionValues != null && transitionValues.values.containsKey(PROPNAME_VISIBILITY)) {
            visibilityInfo.startVisibility = ((java.lang.Integer) transitionValues.values.get(PROPNAME_VISIBILITY)).intValue();
            visibilityInfo.startParent = (android.view.ViewGroup) transitionValues.values.get(PROPNAME_PARENT);
        } else {
            visibilityInfo.startVisibility = -1;
            visibilityInfo.startParent = null;
        }
        if (transitionValues2 != null && transitionValues2.values.containsKey(PROPNAME_VISIBILITY)) {
            visibilityInfo.endVisibility = ((java.lang.Integer) transitionValues2.values.get(PROPNAME_VISIBILITY)).intValue();
            visibilityInfo.endParent = (android.view.ViewGroup) transitionValues2.values.get(PROPNAME_PARENT);
        } else {
            visibilityInfo.endVisibility = -1;
            visibilityInfo.endParent = null;
        }
        if (transitionValues != null && transitionValues2 != null) {
            if (visibilityInfo.startVisibility == visibilityInfo.endVisibility && visibilityInfo.startParent == visibilityInfo.endParent) {
                return visibilityInfo;
            }
            if (visibilityInfo.startVisibility != visibilityInfo.endVisibility) {
                if (visibilityInfo.startVisibility == 0) {
                    visibilityInfo.fadeIn = false;
                    visibilityInfo.visibilityChange = true;
                } else if (visibilityInfo.endVisibility == 0) {
                    visibilityInfo.fadeIn = true;
                    visibilityInfo.visibilityChange = true;
                }
            } else if (visibilityInfo.startParent != visibilityInfo.endParent) {
                if (visibilityInfo.endParent == null) {
                    visibilityInfo.fadeIn = false;
                    visibilityInfo.visibilityChange = true;
                } else if (visibilityInfo.startParent == null) {
                    visibilityInfo.fadeIn = true;
                    visibilityInfo.visibilityChange = true;
                }
            }
        } else if (transitionValues == null && visibilityInfo.endVisibility == 0) {
            visibilityInfo.fadeIn = true;
            visibilityInfo.visibilityChange = true;
        } else if (transitionValues2 == null && visibilityInfo.startVisibility == 0) {
            visibilityInfo.fadeIn = false;
            visibilityInfo.visibilityChange = true;
        }
        return visibilityInfo;
    }

    @Override // android.transition.Transition
    public android.animation.Animator createAnimator(android.view.ViewGroup viewGroup, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        android.transition.Visibility.VisibilityInfo visibilityChangeInfo = getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (!visibilityChangeInfo.visibilityChange) {
            return null;
        }
        if (visibilityChangeInfo.startParent != null || visibilityChangeInfo.endParent != null) {
            if (visibilityChangeInfo.fadeIn) {
                return onAppear(viewGroup, transitionValues, visibilityChangeInfo.startVisibility, transitionValues2, visibilityChangeInfo.endVisibility);
            }
            return onDisappear(viewGroup, transitionValues, visibilityChangeInfo.startVisibility, transitionValues2, visibilityChangeInfo.endVisibility);
        }
        return null;
    }

    public android.animation.Animator onAppear(android.view.ViewGroup viewGroup, android.transition.TransitionValues transitionValues, int i, android.transition.TransitionValues transitionValues2, int i2) {
        if ((this.mMode & 1) != 1 || transitionValues2 == null) {
            return null;
        }
        if (transitionValues == null) {
            android.view.View view = (android.view.View) transitionValues2.view.getParent();
            if (getVisibilityChangeInfo(getMatchedTransitionValues(view, false), getTransitionValues(view, false)).visibilityChange) {
                return null;
            }
        }
        return onAppear(viewGroup, transitionValues2.view, transitionValues, transitionValues2);
    }

    public android.animation.Animator onAppear(android.view.ViewGroup viewGroup, android.view.View view, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x009b, code lost:
    
        if (r17.mCanRemoveViews != false) goto L45;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.animation.Animator onDisappear(android.view.ViewGroup viewGroup, android.transition.TransitionValues transitionValues, int i, android.transition.TransitionValues transitionValues2, int i2) {
        android.view.View view;
        boolean z;
        boolean z2;
        android.view.View view2;
        final android.view.ViewGroupOverlay viewGroupOverlay = null;
        if ((this.mMode & 2) != 2 || transitionValues == null) {
            return null;
        }
        final android.view.View view3 = transitionValues.view;
        android.view.View view4 = transitionValues2 != null ? transitionValues2.view : null;
        final android.view.View view5 = (android.view.View) view3.getTag(com.android.internal.R.id.transition_overlay_view_tag);
        if (view5 != null) {
            view2 = null;
            z2 = true;
        } else {
            if (view4 == null || view4.getParent() == null) {
                if (view4 != null) {
                    view = null;
                    z = false;
                } else {
                    view4 = null;
                    view = null;
                    z = true;
                }
            } else if (i2 != 4 && view3 != view4) {
                view4 = null;
                view = null;
                z = true;
            } else {
                view = view4;
                z = false;
                view4 = null;
            }
            if (z) {
                if (view3.getParent() != null) {
                    if (view3.getParent() instanceof android.view.View) {
                        android.view.View view6 = (android.view.View) view3.getParent();
                        if (!getVisibilityChangeInfo(getTransitionValues(view6, true), getMatchedTransitionValues(view6, true)).visibilityChange) {
                            android.view.View copyViewImage = android.transition.TransitionUtils.copyViewImage(viewGroup, view3, view6);
                            z2 = false;
                            android.view.View view7 = view;
                            view5 = copyViewImage;
                            view2 = view7;
                        } else {
                            int id = view6.getId();
                            if (view6.getParent() == null) {
                                if (id != -1) {
                                    if (viewGroup.findViewById(id) != null) {
                                    }
                                }
                            }
                        }
                    }
                }
                view2 = view;
                z2 = false;
                view5 = view3;
            }
            z2 = false;
            android.view.View view8 = view;
            view5 = view4;
            view2 = view8;
        }
        if (view5 != null) {
            if (!z2) {
                viewGroupOverlay = viewGroup.getOverlay();
                int[] iArr = (int[]) transitionValues.values.get(PROPNAME_SCREEN_LOCATION);
                int i3 = iArr[0];
                int i4 = iArr[1];
                int[] iArr2 = new int[2];
                viewGroup.getLocationOnScreen(iArr2);
                view5.offsetLeftAndRight((i3 - iArr2[0]) - view5.getLeft());
                view5.offsetTopAndBottom((i4 - iArr2[1]) - view5.getTop());
                viewGroupOverlay.add(view5);
            }
            android.animation.Animator onDisappear = onDisappear(viewGroup, view5, transitionValues, transitionValues2);
            if (!z2) {
                if (onDisappear == null) {
                    viewGroupOverlay.remove(view5);
                } else {
                    view3.setTagInternal(com.android.internal.R.id.transition_overlay_view_tag, view5);
                    addListener(new android.transition.TransitionListenerAdapter() { // from class: android.transition.Visibility.1
                        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                        public void onTransitionPause(android.transition.Transition transition) {
                            viewGroupOverlay.remove(view5);
                        }

                        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                        public void onTransitionResume(android.transition.Transition transition) {
                            if (view5.getParent() == null) {
                                viewGroupOverlay.add(view5);
                            } else {
                                android.transition.Visibility.this.cancel();
                            }
                        }

                        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                        public void onTransitionEnd(android.transition.Transition transition) {
                            view3.setTagInternal(com.android.internal.R.id.transition_overlay_view_tag, null);
                            viewGroupOverlay.remove(view5);
                            transition.removeListener(this);
                        }
                    });
                }
            }
            return onDisappear;
        }
        if (view2 == null) {
            return null;
        }
        int visibility = view2.getVisibility();
        view2.setTransitionVisibility(0);
        android.animation.Animator onDisappear2 = onDisappear(viewGroup, view2, transitionValues, transitionValues2);
        if (onDisappear2 != null) {
            android.transition.Visibility.DisappearListener disappearListener = new android.transition.Visibility.DisappearListener(view2, i2, this.mSuppressLayout);
            onDisappear2.addListener(disappearListener);
            onDisappear2.addPauseListener(disappearListener);
            addListener(disappearListener);
        } else {
            view2.setTransitionVisibility(visibility);
        }
        return onDisappear2;
    }

    @Override // android.transition.Transition
    public boolean isTransitionRequired(android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        if (transitionValues == null && transitionValues2 == null) {
            return false;
        }
        if (transitionValues != null && transitionValues2 != null && transitionValues2.values.containsKey(PROPNAME_VISIBILITY) != transitionValues.values.containsKey(PROPNAME_VISIBILITY)) {
            return false;
        }
        android.transition.Visibility.VisibilityInfo visibilityChangeInfo = getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (visibilityChangeInfo.visibilityChange) {
            return visibilityChangeInfo.startVisibility == 0 || visibilityChangeInfo.endVisibility == 0;
        }
        return false;
    }

    public android.animation.Animator onDisappear(android.view.ViewGroup viewGroup, android.view.View view, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        return null;
    }

    private static class DisappearListener extends android.transition.TransitionListenerAdapter implements android.animation.Animator.AnimatorListener, android.animation.Animator.AnimatorPauseListener {
        boolean mCanceled = false;
        private final int mFinalVisibility;
        private boolean mLayoutSuppressed;
        private final android.view.ViewGroup mParent;
        private final boolean mSuppressLayout;
        private final android.view.View mView;

        public DisappearListener(android.view.View view, int i, boolean z) {
            this.mView = view;
            this.mFinalVisibility = i;
            this.mParent = (android.view.ViewGroup) view.getParent();
            this.mSuppressLayout = z;
            suppressLayout(true);
        }

        @Override // android.animation.Animator.AnimatorPauseListener
        public void onAnimationPause(android.animation.Animator animator) {
            if (!this.mCanceled) {
                this.mView.setTransitionVisibility(this.mFinalVisibility);
            }
        }

        @Override // android.animation.Animator.AnimatorPauseListener
        public void onAnimationResume(android.animation.Animator animator) {
            if (!this.mCanceled) {
                this.mView.setTransitionVisibility(0);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
            this.mCanceled = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            hideViewWhenNotCanceled();
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionEnd(android.transition.Transition transition) {
            hideViewWhenNotCanceled();
            transition.removeListener(this);
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionPause(android.transition.Transition transition) {
            suppressLayout(false);
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionResume(android.transition.Transition transition) {
            suppressLayout(true);
        }

        private void hideViewWhenNotCanceled() {
            if (!this.mCanceled) {
                this.mView.setTransitionVisibility(this.mFinalVisibility);
                if (this.mParent != null) {
                    this.mParent.invalidate();
                }
            }
            suppressLayout(false);
        }

        private void suppressLayout(boolean z) {
            if (this.mSuppressLayout && this.mLayoutSuppressed != z && this.mParent != null) {
                this.mLayoutSuppressed = z;
                this.mParent.suppressLayout(z);
            }
        }
    }
}
