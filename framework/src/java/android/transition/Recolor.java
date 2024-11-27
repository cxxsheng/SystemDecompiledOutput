package android.transition;

/* loaded from: classes3.dex */
public class Recolor extends android.transition.Transition {
    private static final java.lang.String PROPNAME_BACKGROUND = "android:recolor:background";
    private static final java.lang.String PROPNAME_TEXT_COLOR = "android:recolor:textColor";

    public Recolor() {
    }

    public Recolor(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void captureValues(android.transition.TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_BACKGROUND, transitionValues.view.getBackground());
        if (transitionValues.view instanceof android.widget.TextView) {
            transitionValues.values.put(PROPNAME_TEXT_COLOR, java.lang.Integer.valueOf(((android.widget.TextView) transitionValues.view).getCurrentTextColor()));
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
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        android.view.View view = transitionValues2.view;
        android.graphics.drawable.Drawable drawable = (android.graphics.drawable.Drawable) transitionValues.values.get(PROPNAME_BACKGROUND);
        android.graphics.drawable.Drawable drawable2 = (android.graphics.drawable.Drawable) transitionValues2.values.get(PROPNAME_BACKGROUND);
        if ((drawable instanceof android.graphics.drawable.ColorDrawable) && (drawable2 instanceof android.graphics.drawable.ColorDrawable)) {
            android.graphics.drawable.ColorDrawable colorDrawable = (android.graphics.drawable.ColorDrawable) drawable;
            android.graphics.drawable.ColorDrawable colorDrawable2 = (android.graphics.drawable.ColorDrawable) drawable2;
            if (colorDrawable.getColor() != colorDrawable2.getColor()) {
                colorDrawable2.setColor(colorDrawable.getColor());
                return android.animation.ObjectAnimator.ofArgb(drawable2, "color", colorDrawable.getColor(), colorDrawable2.getColor());
            }
        }
        if (view instanceof android.widget.TextView) {
            android.widget.TextView textView = (android.widget.TextView) view;
            int intValue = ((java.lang.Integer) transitionValues.values.get(PROPNAME_TEXT_COLOR)).intValue();
            int intValue2 = ((java.lang.Integer) transitionValues2.values.get(PROPNAME_TEXT_COLOR)).intValue();
            if (intValue != intValue2) {
                textView.setTextColor(intValue2);
                return android.animation.ObjectAnimator.ofArgb(textView, "textColor", intValue, intValue2);
            }
        }
        return null;
    }
}
