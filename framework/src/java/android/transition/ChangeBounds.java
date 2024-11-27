package android.transition;

/* loaded from: classes3.dex */
public class ChangeBounds extends android.transition.Transition {
    private static final android.util.Property<android.view.View, android.graphics.PointF> BOTTOM_RIGHT_ONLY_PROPERTY;
    private static final android.util.Property<android.transition.ChangeBounds.ViewBounds, android.graphics.PointF> BOTTOM_RIGHT_PROPERTY;
    private static final java.lang.String LOG_TAG = "ChangeBounds";
    private static final android.util.Property<android.view.View, android.graphics.PointF> TOP_LEFT_ONLY_PROPERTY;
    private static final android.util.Property<android.transition.ChangeBounds.ViewBounds, android.graphics.PointF> TOP_LEFT_PROPERTY;
    boolean mReparent;
    boolean mResizeClip;
    int[] tempLocation;
    private static final java.lang.String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final java.lang.String PROPNAME_CLIP = "android:changeBounds:clip";
    private static final java.lang.String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final java.lang.String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final java.lang.String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private static final java.lang.String[] sTransitionProperties = {PROPNAME_BOUNDS, PROPNAME_CLIP, PROPNAME_PARENT, PROPNAME_WINDOW_X, PROPNAME_WINDOW_Y};
    private static final android.util.Property<android.graphics.drawable.Drawable, android.graphics.PointF> DRAWABLE_ORIGIN_PROPERTY = new android.util.Property<android.graphics.drawable.Drawable, android.graphics.PointF>(android.graphics.PointF.class, "boundsOrigin") { // from class: android.transition.ChangeBounds.1
        private android.graphics.Rect mBounds = new android.graphics.Rect();

        @Override // android.util.Property
        public void set(android.graphics.drawable.Drawable drawable, android.graphics.PointF pointF) {
            drawable.copyBounds(this.mBounds);
            this.mBounds.offsetTo(java.lang.Math.round(pointF.x), java.lang.Math.round(pointF.y));
            drawable.setBounds(this.mBounds);
        }

        @Override // android.util.Property
        public android.graphics.PointF get(android.graphics.drawable.Drawable drawable) {
            drawable.copyBounds(this.mBounds);
            return new android.graphics.PointF(this.mBounds.left, this.mBounds.top);
        }
    };
    private static final android.util.Property<android.view.View, android.graphics.PointF> POSITION_PROPERTY = new android.util.Property<android.view.View, android.graphics.PointF>(android.graphics.PointF.class, "position") { // from class: android.transition.ChangeBounds.6
        @Override // android.util.Property
        public void set(android.view.View view, android.graphics.PointF pointF) {
            int round = java.lang.Math.round(pointF.x);
            int round2 = java.lang.Math.round(pointF.y);
            view.setLeftTopRightBottom(round, round2, view.getWidth() + round, view.getHeight() + round2);
        }

        @Override // android.util.Property
        public android.graphics.PointF get(android.view.View view) {
            return null;
        }
    };
    private static android.animation.RectEvaluator sRectEvaluator = new android.animation.RectEvaluator();

    static {
        java.lang.String str = "topLeft";
        TOP_LEFT_PROPERTY = new android.util.Property<android.transition.ChangeBounds.ViewBounds, android.graphics.PointF>(android.graphics.PointF.class, str) { // from class: android.transition.ChangeBounds.2
            @Override // android.util.Property
            public void set(android.transition.ChangeBounds.ViewBounds viewBounds, android.graphics.PointF pointF) {
                viewBounds.setTopLeft(pointF);
            }

            @Override // android.util.Property
            public android.graphics.PointF get(android.transition.ChangeBounds.ViewBounds viewBounds) {
                return null;
            }
        };
        java.lang.String str2 = "bottomRight";
        BOTTOM_RIGHT_PROPERTY = new android.util.Property<android.transition.ChangeBounds.ViewBounds, android.graphics.PointF>(android.graphics.PointF.class, str2) { // from class: android.transition.ChangeBounds.3
            @Override // android.util.Property
            public void set(android.transition.ChangeBounds.ViewBounds viewBounds, android.graphics.PointF pointF) {
                viewBounds.setBottomRight(pointF);
            }

            @Override // android.util.Property
            public android.graphics.PointF get(android.transition.ChangeBounds.ViewBounds viewBounds) {
                return null;
            }
        };
        BOTTOM_RIGHT_ONLY_PROPERTY = new android.util.Property<android.view.View, android.graphics.PointF>(android.graphics.PointF.class, str2) { // from class: android.transition.ChangeBounds.4
            @Override // android.util.Property
            public void set(android.view.View view, android.graphics.PointF pointF) {
                view.setLeftTopRightBottom(view.getLeft(), view.getTop(), java.lang.Math.round(pointF.x), java.lang.Math.round(pointF.y));
            }

            @Override // android.util.Property
            public android.graphics.PointF get(android.view.View view) {
                return null;
            }
        };
        TOP_LEFT_ONLY_PROPERTY = new android.util.Property<android.view.View, android.graphics.PointF>(android.graphics.PointF.class, str) { // from class: android.transition.ChangeBounds.5
            @Override // android.util.Property
            public void set(android.view.View view, android.graphics.PointF pointF) {
                view.setLeftTopRightBottom(java.lang.Math.round(pointF.x), java.lang.Math.round(pointF.y), view.getRight(), view.getBottom());
            }

            @Override // android.util.Property
            public android.graphics.PointF get(android.view.View view) {
                return null;
            }
        };
    }

    public ChangeBounds() {
        this.tempLocation = new int[2];
        this.mResizeClip = false;
        this.mReparent = false;
    }

    public ChangeBounds(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.tempLocation = new int[2];
        this.mResizeClip = false;
        this.mReparent = false;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ChangeBounds);
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        setResizeClip(z);
    }

    @Override // android.transition.Transition
    public java.lang.String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setResizeClip(boolean z) {
        this.mResizeClip = z;
    }

    public boolean getResizeClip() {
        return this.mResizeClip;
    }

    @java.lang.Deprecated
    public void setReparent(boolean z) {
        this.mReparent = z;
    }

    private void captureValues(android.transition.TransitionValues transitionValues) {
        android.view.View view = transitionValues.view;
        if (view.isLaidOut() || view.getWidth() != 0 || view.getHeight() != 0) {
            transitionValues.values.put(PROPNAME_BOUNDS, new android.graphics.Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            transitionValues.values.put(PROPNAME_PARENT, transitionValues.view.getParent());
            if (this.mReparent) {
                transitionValues.view.getLocationInWindow(this.tempLocation);
                transitionValues.values.put(PROPNAME_WINDOW_X, java.lang.Integer.valueOf(this.tempLocation[0]));
                transitionValues.values.put(PROPNAME_WINDOW_Y, java.lang.Integer.valueOf(this.tempLocation[1]));
            }
            if (this.mResizeClip) {
                transitionValues.values.put(PROPNAME_CLIP, view.getClipBounds());
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

    private boolean parentMatches(android.view.View view, android.view.View view2) {
        if (!this.mReparent) {
            return true;
        }
        android.transition.TransitionValues matchedTransitionValues = getMatchedTransitionValues(view, true);
        return matchedTransitionValues == null ? view == view2 : view2 == matchedTransitionValues.view;
    }

    @Override // android.transition.Transition
    public android.animation.Animator createAnimator(final android.view.ViewGroup viewGroup, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        int i;
        android.graphics.Rect rect;
        int i2;
        android.transition.ChangeBounds changeBounds;
        int i3;
        android.graphics.Rect rect2;
        android.graphics.Rect rect3;
        android.animation.ObjectAnimator objectAnimator;
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        java.util.Map<java.lang.String, java.lang.Object> map = transitionValues.values;
        java.util.Map<java.lang.String, java.lang.Object> map2 = transitionValues2.values;
        android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) map.get(PROPNAME_PARENT);
        android.view.ViewGroup viewGroup3 = (android.view.ViewGroup) map2.get(PROPNAME_PARENT);
        if (viewGroup2 == null || viewGroup3 == null) {
            return null;
        }
        final android.view.View view = transitionValues2.view;
        if (parentMatches(viewGroup2, viewGroup3)) {
            android.graphics.Rect rect4 = (android.graphics.Rect) transitionValues.values.get(PROPNAME_BOUNDS);
            android.graphics.Rect rect5 = (android.graphics.Rect) transitionValues2.values.get(PROPNAME_BOUNDS);
            int i4 = rect4.left;
            final int i5 = rect5.left;
            int i6 = rect4.top;
            final int i7 = rect5.top;
            int i8 = rect4.right;
            final int i9 = rect5.right;
            int i10 = rect4.bottom;
            int i11 = rect5.bottom;
            int i12 = i8 - i4;
            int i13 = i10 - i6;
            int i14 = i9 - i5;
            int i15 = i11 - i7;
            android.graphics.Rect rect6 = (android.graphics.Rect) transitionValues.values.get(PROPNAME_CLIP);
            final android.graphics.Rect rect7 = (android.graphics.Rect) transitionValues2.values.get(PROPNAME_CLIP);
            if ((i12 != 0 && i13 != 0) || (i14 != 0 && i15 != 0)) {
                i = (i4 == i5 && i6 == i7) ? 0 : 1;
                if (i8 != i9 || i10 != i11) {
                    i++;
                }
            } else {
                i = 0;
            }
            if ((rect6 != null && !rect6.equals(rect7)) || (rect6 == null && rect7 != null)) {
                i++;
            }
            if (i > 0) {
                if (!(view.getParent() instanceof android.view.ViewGroup)) {
                    rect = rect6;
                    i2 = i11;
                    changeBounds = this;
                } else {
                    final android.view.ViewGroup viewGroup4 = (android.view.ViewGroup) view.getParent();
                    rect = rect6;
                    viewGroup4.suppressLayout(true);
                    i2 = i11;
                    changeBounds = this;
                    changeBounds.addListener(new android.transition.TransitionListenerAdapter() { // from class: android.transition.ChangeBounds.7
                        boolean mCanceled = false;

                        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                        public void onTransitionCancel(android.transition.Transition transition) {
                            viewGroup4.suppressLayout(false);
                            this.mCanceled = true;
                        }

                        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                        public void onTransitionEnd(android.transition.Transition transition) {
                            if (!this.mCanceled) {
                                viewGroup4.suppressLayout(false);
                            }
                            transition.removeListener(this);
                        }

                        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                        public void onTransitionPause(android.transition.Transition transition) {
                            viewGroup4.suppressLayout(false);
                        }

                        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                        public void onTransitionResume(android.transition.Transition transition) {
                            viewGroup4.suppressLayout(true);
                        }
                    });
                }
                if (!changeBounds.mResizeClip) {
                    view.setLeftTopRightBottom(i4, i6, i8, i10);
                    if (i == 2) {
                        if (i12 == i14 && i13 == i15) {
                            return android.animation.ObjectAnimator.ofObject(view, (android.util.Property<android.view.View, V>) POSITION_PROPERTY, (android.animation.TypeConverter) null, getPathMotion().getPath(i4, i6, i5, i7));
                        }
                        final android.transition.ChangeBounds.ViewBounds viewBounds = new android.transition.ChangeBounds.ViewBounds(view);
                        android.animation.ObjectAnimator ofObject = android.animation.ObjectAnimator.ofObject(viewBounds, (android.util.Property<android.transition.ChangeBounds.ViewBounds, V>) TOP_LEFT_PROPERTY, (android.animation.TypeConverter) null, getPathMotion().getPath(i4, i6, i5, i7));
                        android.animation.ObjectAnimator ofObject2 = android.animation.ObjectAnimator.ofObject(viewBounds, (android.util.Property<android.transition.ChangeBounds.ViewBounds, V>) BOTTOM_RIGHT_PROPERTY, (android.animation.TypeConverter) null, getPathMotion().getPath(i8, i10, i9, i2));
                        android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
                        animatorSet.playTogether(ofObject, ofObject2);
                        animatorSet.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.transition.ChangeBounds.8
                            private android.transition.ChangeBounds.ViewBounds mViewBounds;

                            {
                                this.mViewBounds = viewBounds;
                            }
                        });
                        return animatorSet;
                    }
                    int i16 = i2;
                    if (i4 != i5 || i6 != i7) {
                        return android.animation.ObjectAnimator.ofObject(view, (android.util.Property<android.view.View, V>) TOP_LEFT_ONLY_PROPERTY, (android.animation.TypeConverter) null, getPathMotion().getPath(i4, i6, i5, i7));
                    }
                    return android.animation.ObjectAnimator.ofObject(view, (android.util.Property<android.view.View, V>) BOTTOM_RIGHT_ONLY_PROPERTY, (android.animation.TypeConverter) null, getPathMotion().getPath(i8, i10, i9, i16));
                }
                final int i17 = i2;
                view.setLeftTopRightBottom(i4, i6, java.lang.Math.max(i12, i14) + i4, java.lang.Math.max(i13, i15) + i6);
                android.animation.ObjectAnimator ofObject3 = (i4 == i5 && i6 == i7) ? null : android.animation.ObjectAnimator.ofObject(view, (android.util.Property<android.view.View, V>) POSITION_PROPERTY, (android.animation.TypeConverter) null, getPathMotion().getPath(i4, i6, i5, i7));
                if (rect != null) {
                    i3 = 0;
                    rect2 = rect;
                } else {
                    i3 = 0;
                    rect2 = new android.graphics.Rect(0, 0, i12, i13);
                }
                if (rect7 != null) {
                    rect3 = rect7;
                } else {
                    rect3 = new android.graphics.Rect(i3, i3, i14, i15);
                }
                if (rect2.equals(rect3)) {
                    objectAnimator = null;
                } else {
                    view.setClipBounds(rect2);
                    android.animation.ObjectAnimator ofObject4 = android.animation.ObjectAnimator.ofObject(view, "clipBounds", sRectEvaluator, rect2, rect3);
                    ofObject4.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.transition.ChangeBounds.9
                        private boolean mIsCanceled;

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(android.animation.Animator animator) {
                            this.mIsCanceled = true;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(android.animation.Animator animator) {
                            if (!this.mIsCanceled) {
                                view.setClipBounds(rect7);
                                view.setLeftTopRightBottom(i5, i7, i9, i17);
                            }
                        }
                    });
                    objectAnimator = ofObject4;
                }
                return android.transition.TransitionUtils.mergeAnimators(ofObject3, objectAnimator);
            }
            return null;
        }
        viewGroup.getLocationInWindow(this.tempLocation);
        int intValue = ((java.lang.Integer) transitionValues.values.get(PROPNAME_WINDOW_X)).intValue() - this.tempLocation[0];
        int intValue2 = ((java.lang.Integer) transitionValues.values.get(PROPNAME_WINDOW_Y)).intValue() - this.tempLocation[1];
        int intValue3 = ((java.lang.Integer) transitionValues2.values.get(PROPNAME_WINDOW_X)).intValue() - this.tempLocation[0];
        int intValue4 = ((java.lang.Integer) transitionValues2.values.get(PROPNAME_WINDOW_Y)).intValue() - this.tempLocation[1];
        if (intValue != intValue3 || intValue2 != intValue4) {
            int width = view.getWidth();
            int height = view.getHeight();
            android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888);
            view.draw(new android.graphics.Canvas(createBitmap));
            final android.graphics.drawable.BitmapDrawable bitmapDrawable = new android.graphics.drawable.BitmapDrawable(createBitmap);
            bitmapDrawable.setBounds(intValue, intValue2, width + intValue, height + intValue2);
            final float transitionAlpha = view.getTransitionAlpha();
            view.setTransitionAlpha(0.0f);
            viewGroup.getOverlay().add(bitmapDrawable);
            android.animation.ObjectAnimator ofPropertyValuesHolder = android.animation.ObjectAnimator.ofPropertyValuesHolder(bitmapDrawable, android.animation.PropertyValuesHolder.ofObject(DRAWABLE_ORIGIN_PROPERTY, (android.animation.TypeConverter) null, getPathMotion().getPath(intValue, intValue2, intValue3, intValue4)));
            ofPropertyValuesHolder.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.transition.ChangeBounds.10
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(android.animation.Animator animator) {
                    viewGroup.getOverlay().remove(bitmapDrawable);
                    view.setTransitionAlpha(transitionAlpha);
                }
            });
            return ofPropertyValuesHolder;
        }
        return null;
    }

    private static class ViewBounds {
        private int mBottom;
        private int mBottomRightCalls;
        private int mLeft;
        private int mRight;
        private int mTop;
        private int mTopLeftCalls;
        private android.view.View mView;

        public ViewBounds(android.view.View view) {
            this.mView = view;
        }

        public void setTopLeft(android.graphics.PointF pointF) {
            this.mLeft = java.lang.Math.round(pointF.x);
            this.mTop = java.lang.Math.round(pointF.y);
            this.mTopLeftCalls++;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        public void setBottomRight(android.graphics.PointF pointF) {
            this.mRight = java.lang.Math.round(pointF.x);
            this.mBottom = java.lang.Math.round(pointF.y);
            this.mBottomRightCalls++;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        private void setLeftTopRightBottom() {
            this.mView.setLeftTopRightBottom(this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mTopLeftCalls = 0;
            this.mBottomRightCalls = 0;
        }
    }
}
