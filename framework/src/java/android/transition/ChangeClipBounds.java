package android.transition;

/* loaded from: classes3.dex */
public class ChangeClipBounds extends android.transition.Transition {
    private static final java.lang.String PROPNAME_BOUNDS = "android:clipBounds:bounds";
    private static final java.lang.String TAG = "ChangeTransform";
    private static final java.lang.String PROPNAME_CLIP = "android:clipBounds:clip";
    private static final java.lang.String[] sTransitionProperties = {PROPNAME_CLIP};

    public ChangeClipBounds() {
    }

    public ChangeClipBounds(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.transition.Transition
    public java.lang.String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    private void captureValues(android.transition.TransitionValues transitionValues) {
        android.view.View view = transitionValues.view;
        if (view.getVisibility() == 8) {
            return;
        }
        android.graphics.Rect clipBounds = view.getClipBounds();
        transitionValues.values.put(PROPNAME_CLIP, clipBounds);
        if (clipBounds == null) {
            transitionValues.values.put(PROPNAME_BOUNDS, new android.graphics.Rect(0, 0, view.getWidth(), view.getHeight()));
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

    @Override // android.transition.Transition
    public android.animation.Animator createAnimator(android.view.ViewGroup viewGroup, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null || !transitionValues.values.containsKey(PROPNAME_CLIP) || !transitionValues2.values.containsKey(PROPNAME_CLIP)) {
            return null;
        }
        android.graphics.Rect rect = (android.graphics.Rect) transitionValues.values.get(PROPNAME_CLIP);
        android.graphics.Rect rect2 = (android.graphics.Rect) transitionValues2.values.get(PROPNAME_CLIP);
        boolean z = rect2 == null;
        if (rect == null && rect2 == null) {
            return null;
        }
        if (rect == null) {
            rect = (android.graphics.Rect) transitionValues.values.get(PROPNAME_BOUNDS);
        } else if (rect2 == null) {
            rect2 = (android.graphics.Rect) transitionValues2.values.get(PROPNAME_BOUNDS);
        }
        if (rect.equals(rect2)) {
            return null;
        }
        transitionValues2.view.setClipBounds(rect);
        android.animation.ObjectAnimator ofObject = android.animation.ObjectAnimator.ofObject(transitionValues2.view, "clipBounds", new android.animation.RectEvaluator(new android.graphics.Rect()), rect, rect2);
        if (z) {
            final android.view.View view = transitionValues2.view;
            ofObject.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.transition.ChangeClipBounds.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(android.animation.Animator animator) {
                    view.setClipBounds(null);
                }
            });
        }
        return ofObject;
    }
}
