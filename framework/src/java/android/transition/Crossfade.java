package android.transition;

/* loaded from: classes3.dex */
public class Crossfade extends android.transition.Transition {
    public static final int FADE_BEHAVIOR_CROSSFADE = 0;
    public static final int FADE_BEHAVIOR_OUT_IN = 2;
    public static final int FADE_BEHAVIOR_REVEAL = 1;
    private static final java.lang.String LOG_TAG = "Crossfade";
    private static final java.lang.String PROPNAME_BITMAP = "android:crossfade:bitmap";
    private static final java.lang.String PROPNAME_BOUNDS = "android:crossfade:bounds";
    private static final java.lang.String PROPNAME_DRAWABLE = "android:crossfade:drawable";
    public static final int RESIZE_BEHAVIOR_NONE = 0;
    public static final int RESIZE_BEHAVIOR_SCALE = 1;
    private static android.animation.RectEvaluator sRectEvaluator = new android.animation.RectEvaluator();
    private int mFadeBehavior = 1;
    private int mResizeBehavior = 1;

    public android.transition.Crossfade setFadeBehavior(int i) {
        if (i >= 0 && i <= 2) {
            this.mFadeBehavior = i;
        }
        return this;
    }

    public int getFadeBehavior() {
        return this.mFadeBehavior;
    }

    public android.transition.Crossfade setResizeBehavior(int i) {
        if (i >= 0 && i <= 1) {
            this.mResizeBehavior = i;
        }
        return this;
    }

    public int getResizeBehavior() {
        return this.mResizeBehavior;
    }

    @Override // android.transition.Transition
    public android.animation.Animator createAnimator(android.view.ViewGroup viewGroup, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        android.animation.ObjectAnimator ofInt;
        android.animation.ObjectAnimator objectAnimator = null;
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        final boolean z = this.mFadeBehavior != 1;
        final android.view.View view = transitionValues2.view;
        java.util.Map<java.lang.String, java.lang.Object> map = transitionValues.values;
        java.util.Map<java.lang.String, java.lang.Object> map2 = transitionValues2.values;
        android.graphics.Rect rect = (android.graphics.Rect) map.get(PROPNAME_BOUNDS);
        android.graphics.Rect rect2 = (android.graphics.Rect) map2.get(PROPNAME_BOUNDS);
        android.graphics.Bitmap bitmap = (android.graphics.Bitmap) map.get(PROPNAME_BITMAP);
        android.graphics.Bitmap bitmap2 = (android.graphics.Bitmap) map2.get(PROPNAME_BITMAP);
        final android.graphics.drawable.BitmapDrawable bitmapDrawable = (android.graphics.drawable.BitmapDrawable) map.get(PROPNAME_DRAWABLE);
        final android.graphics.drawable.BitmapDrawable bitmapDrawable2 = (android.graphics.drawable.BitmapDrawable) map2.get(PROPNAME_DRAWABLE);
        if (bitmapDrawable == null || bitmapDrawable2 == null || bitmap.sameAs(bitmap2)) {
            return null;
        }
        android.view.ViewOverlay overlay = z ? ((android.view.ViewGroup) view.getParent()).getOverlay() : view.getOverlay();
        if (this.mFadeBehavior == 1) {
            overlay.add(bitmapDrawable2);
        }
        overlay.add(bitmapDrawable);
        if (this.mFadeBehavior == 2) {
            ofInt = android.animation.ObjectAnimator.ofInt(bitmapDrawable, "alpha", 255, 0, 0);
        } else {
            ofInt = android.animation.ObjectAnimator.ofInt(bitmapDrawable, "alpha", 0);
        }
        ofInt.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.transition.Crossfade.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                view.invalidate(bitmapDrawable.getBounds());
            }
        });
        if (this.mFadeBehavior == 2) {
            objectAnimator = android.animation.ObjectAnimator.ofFloat(view, android.view.View.ALPHA, 0.0f, 0.0f, 1.0f);
        } else if (this.mFadeBehavior == 0) {
            objectAnimator = android.animation.ObjectAnimator.ofFloat(view, android.view.View.ALPHA, 0.0f, 1.0f);
        }
        ofInt.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.transition.Crossfade.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                android.view.ViewOverlay overlay2 = z ? ((android.view.ViewGroup) view.getParent()).getOverlay() : view.getOverlay();
                overlay2.remove(bitmapDrawable);
                if (android.transition.Crossfade.this.mFadeBehavior == 1) {
                    overlay2.remove(bitmapDrawable2);
                }
            }
        });
        android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
        animatorSet.playTogether(ofInt);
        if (objectAnimator != null) {
            animatorSet.playTogether(objectAnimator);
        }
        if (this.mResizeBehavior == 1 && !rect.equals(rect2)) {
            animatorSet.playTogether(android.animation.ObjectAnimator.ofObject(bitmapDrawable, "bounds", sRectEvaluator, rect, rect2));
            if (this.mResizeBehavior == 1) {
                animatorSet.playTogether(android.animation.ObjectAnimator.ofObject(bitmapDrawable2, "bounds", sRectEvaluator, rect, rect2));
            }
        }
        return animatorSet;
    }

    private void captureValues(android.transition.TransitionValues transitionValues) {
        android.view.View view = transitionValues.view;
        android.graphics.Rect rect = new android.graphics.Rect(0, 0, view.getWidth(), view.getHeight());
        if (this.mFadeBehavior != 1) {
            rect.offset(view.getLeft(), view.getTop());
        }
        transitionValues.values.put(PROPNAME_BOUNDS, rect);
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(view.getWidth(), view.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        if (view instanceof android.view.TextureView) {
            createBitmap = ((android.view.TextureView) view).getBitmap();
        } else {
            view.draw(new android.graphics.Canvas(createBitmap));
        }
        transitionValues.values.put(PROPNAME_BITMAP, createBitmap);
        android.graphics.drawable.BitmapDrawable bitmapDrawable = new android.graphics.drawable.BitmapDrawable(createBitmap);
        bitmapDrawable.setBounds(rect);
        transitionValues.values.put(PROPNAME_DRAWABLE, bitmapDrawable);
    }

    @Override // android.transition.Transition
    public void captureStartValues(android.transition.TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.transition.Transition
    public void captureEndValues(android.transition.TransitionValues transitionValues) {
        captureValues(transitionValues);
    }
}
